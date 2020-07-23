package com.adm.backend.core.extension;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SqlMapClientExtension implements ExtensionSupport {
    private Resource configLocation;
    private Map<String, String> overrides;

    public Document getConfigurationFragment() throws ParserConfigurationException, SAXException, IOException {
        Document document = null;
        if (this.configLocation != null) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver((EntityResolver)new XMLMapperEntityResolver());
            document = builder.parse(new InputSource(this.configLocation.getInputStream()));
        }
        return document;
    }

    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    public void setOverrides(Map<String, String> overrides) {
        this.overrides = overrides;
    }

    public Resource getConfigLocation() {
        return this.configLocation;
    }

    public Map<String, String> getOverrides() {
        return this.overrides;
    }
}