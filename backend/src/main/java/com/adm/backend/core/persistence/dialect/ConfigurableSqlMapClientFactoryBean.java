package com.adm.backend.core.persistence.dialect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.adm.backend.core.extension.SqlMapClientExtension;
import com.adm.backend.core.persistence.dialect.lobhandler.DelegatingLobHandler;

import org.apache.commons.lang.NullArgumentException;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.session.SqlSession;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.support.lob.LobHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class ConfigurableSqlMapClientFactoryBean extends SqlSessionFactoryBean  {
    private static final Logger log = LoggerFactory.getLogger(ConfigurableSqlMapClientFactoryBean.class);
    private DataSource dataSource;
    private DelegatingLobHandler lobHandler;

    public ConfigurableSqlMapClientFactoryBean(){}

    // @Autowired
    // private GeneralCacheAdministrator cacheAdministrator;
    @Autowired
    private ApplicationContext applicationContext;

    public void setDataSource(DataSource dataSource){
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    public void setLobHandler(LobHandler lobHandler) throws Exception {
        super.getObject().getConfiguration().getTypeHandlerRegistry().register(LobHandler.class);
        this.lobHandler = (DelegatingLobHandler) lobHandler;
    }

    protected List<Document> getConfigurationFragments() {
        ArrayList<Document> documents = new ArrayList<Document>();
        Map<String, SqlMapClientExtension> extensions = this.applicationContext.getBeansOfType(SqlMapClientExtension.class);
        for (String name : extensions.keySet()) {
            SqlMapClientExtension extension = (SqlMapClientExtension)extensions.get(name);
            try {
                String override;
                Document document = extension.getConfigurationFragment();
                if (extension.getOverrides() != null && (override = extension.getOverrides().get(this.lobHandler.getDialect().getName())) != null) {
                    Node parent = document.getElementsByTagName("sqlMapConfig").item(0);
                    NodeList nodes = document.getElementsByTagName("sqlMap");
                    Element node = document.createElement("sqlMap");
                    node.setAttribute("resource", override);
                    if (nodes.getLength() == 0) {
                        parent.appendChild(node);
                    } else {
                        parent.insertBefore(node, nodes.item(0));
                    }
                }
                if (document == null) continue;
                log.info("Custom SqlMapClient configuration loaded from : " + (Object)extension.getConfigLocation());
                documents.add(document);
            }
            catch (Exception e) {
                log.error("Failed to load custom SqlMapClient configuration from : {}", (Object)extension.getConfigLocation(), (Object)e);
            }
        }
        return documents;
    }

    protected void mergeCustomConfigurationFragments(Document document, List<Document> fragments) {
        if (document == null) {
            throw new NullArgumentException("document");
        }
        Node parent = document.getElementsByTagName("sqlMapConfig").item(0);
        NodeList nodes = document.getElementsByTagName("sqlMap");
        Node firstNode = nodes.item(0);
        if (fragments != null) {
            for (Document fragment : fragments) {
                NodeList sqlMaps;
                NodeList typeHandlers = fragment.getElementsByTagName("typeHandler");
                if (typeHandlers.getLength() > 0) {
                    for (int i = 0; i < typeHandlers.getLength(); ++i) {
                        Node node = document.importNode(typeHandlers.item(i), true);
                        parent.insertBefore(node, firstNode);
                    }
                }
                if ((sqlMaps = fragment.getElementsByTagName("sqlMap")).getLength() <= 0) continue;
                for (int i = 0; i < sqlMaps.getLength(); ++i) {
                    Node node = document.importNode(sqlMaps.item(i), true);
                    parent.appendChild(node);
                }
            }
        }
    }

    protected SqlSession buildSqlMapClient(Resource[] configLocations, Resource[] mappingLocations, Properties properties) throws Exception {
        if (configLocations != null && this.dataSource != null && this.lobHandler != null) {
            try {
                String dbmsType = "oracle"; // 
                if (!this.lobHandler.getDialect().supports(dbmsType)) {
                    // empty if block
                }
                // mybatis 는 cache가 자동처리되므로 제거 처리
                //OSCacheController.setCacheAdministrator(this.cacheAdministrator);
            
                for (int i = 0; i < configLocations.length; ++i) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    builder.setEntityResolver((EntityResolver)new XMLMapperEntityResolver());
                    Document document = builder.parse(new InputSource(configLocations[i].getInputStream()));
                    Node parent = document.getElementsByTagName("sqlMapConfig").item(0);
                    NodeList nodes = document.getElementsByTagName("sqlMap");
                    Element node = document.createElement("sqlMap");
                    // 이부분이 핵심 repository clone 처리 snop는 derby 구동이 안되어 있는데 어찌 되는고지...
                    // oracle Overrid.xml 파일 로 가는 것 뿐 접속정보는 없음.
                    node.setAttribute("resource", this.lobHandler.getDialect().getSQLOverridePath()); 
                    if (nodes.getLength() == 0) {
                        parent.appendChild(node);
                    } else {
                        parent.insertBefore(node, nodes.item(0));
                    }
                    List<Document> fragments = this.getConfigurationFragments();
                    this.mergeCustomConfigurationFragments(document, fragments);
                    DOMSource source = new DOMSource(document);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    StreamResult result = new StreamResult(out);
                    TransformerFactoryImpl tfactory = (TransformerFactoryImpl)TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl", ((Object)((Object)this)).getClass().getClassLoader());
                    Transformer transformer = tfactory.newTransformer();
                    transformer.setOutputProperty("encoding", document.getXmlEncoding());
                    transformer.setOutputProperty("doctype-public", document.getDoctype().getPublicId());
                    transformer.setOutputProperty("doctype-system", document.getDoctype().getSystemId());
                    transformer.transform(source, result);
                    out.flush();
                    out.close();
                    configLocations[i] = new ByteArrayResource(out.toByteArray());
                }
            }
            catch (RuntimeException re) {
                throw re;
            }
            catch (Exception e) {
                String msg = "Failed to autodetect database type : " + e;
                log.error(msg, (Throwable)e);
                throw new IOException(msg);
            }
        }
        if (configLocations != null) {

            for(Resource rs : configLocations) {
                super.setConfigLocation(rs);
            }            
            super.setConfigurationProperties(properties);
            return (SqlSession) super.buildSqlSessionFactory();
            //return super.buildSqlMapClient(configLocations, mappingLocations, properties);
        }
        return null;
    }

}