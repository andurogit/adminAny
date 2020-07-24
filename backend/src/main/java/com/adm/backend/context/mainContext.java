package com.adm.backend.context;

import com.adm.backend.core.persistence.dialect.ConfigurableSqlMapClientFactoryBean;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 메인 컨텍스트 
 * 사용 할 빈들 등록
 * 
 * 빈
 * 1. customEditorConfigurer ( 스프링 프로퍼티 에디터 )
 * 2. propertyConfigurer ( 프로퍼티 파일 찾기 )
 * 3. dataSource ( dabase 연결 소스 )
 */
/* 
<bean id="customEditorConfigurer"
class="org.springframework.beans.factory.config.CustomEditorConfigurer">
<property name="propertyEditorRegistrars">
  <list>
      <bean class="...AdminPropertyEditorRegistrar"/>
  </list>
</property>
</bean> */

@Configuration
@EnableTransactionManagement // <tx:annotation-driven transaction-manager="transactionManager"/>
public class mainContext {
    private static final Logger log = LoggerFactory.getLogger(mainContext.class);
    
    /**
     * 스프링 에디터
     * property를 넘길 떄는 이렇게
     * @return
     */
    @Bean(name = "customEditorConfigurer")
    public CustomEditorConfigurer customEditorConfigurer() {

        log.debug("%s", "CustomEditor context");
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        
        customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{new AdminPropertyEditorRegistrar()});

        return customEditorConfigurer;
        //customEditorConfigurer.setPropertyEditorRegistrars((AdminPropertyEditorRegistrar[]) ArrayUtils.add( new AdminPropertyEditorRegistrar[],new AdminPropertyEditorRegistrar()));
        //customEditorConfigurer.setPropertyEditorRegistrars(propEditorRegList);

        /**
         * 리턴클래스를 인스턴스화 하고 CustomEditorConfigurer
         * 받을 배열을 앞에 파라미터에 넣고
         * PropertyEditorRegistrar 는 인터페이스고 구현체를 가져와야 한다.
         */
        
        //ArrayUtils.add((Object[]) new PropertyEditorRegistrar(), (Object) new AdminPropertyEditorRegistrar());

        // return customEditorConfigurer;

        // log.debug("%s", "CustomEditor context");
        // CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        // PropertyEditorRegistrar[] propEditorRegList = new PropertyEditorRegistrar[1];
        // propEditorRegList[0] = new AdminPropertyEditorRegistrar();
        
        // customEditorConfigurer.setPropertyEditorRegistrars(propEditorRegList);
        // return new CustomEditorConfigurer();
    }

    
    /**
     * 프로퍼티 파일 찾기
     * 해당 프로퍼티 컨피거에 admin.properties ( classpath 에 있는 것 )
     * 정보를 생성자로 던진다 생성자는 Resource[] 형태이다.
     * 생성자에 파라미터로 넣을 때는 이렇게
     * 프로퍼티가 더 있어서 변경 함
     */
    @Bean(name = "propertyConfigurer")
    public AdminPropertyOverrideConfigurer propertyConfigurer(Resource[] resource) {
        AdminPropertyOverrideConfigurer adminPropertyOverrideConfigurer = new AdminPropertyOverrideConfigurer((Resource[]) ArrayUtils.add((Object[]) resource, (Object) new ClassPathResource("admin.properties")));

        adminPropertyOverrideConfigurer.setIgnoreResourceNotFound(true);

        adminPropertyOverrideConfigurer.setIgnoreInvalidKeys(true);

        return adminPropertyOverrideConfigurer;
        
        //return new AdminPropertyOverrideConfigurer((Resource[]) ArrayUtils.add((Object[]) resource, (Object) new ClassPathResource("admin.properties")));
    }

    /**
     * Data Source
     * ??? 프로퍼티로 만들어서 던저야 되나?
     * ClientDriver 가 없네 
     */
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        //Properties props = new Properties();
        //props.setProperty("driverClassName", "org.apache.derby.jdbc.ClientDriver");
        dataSource.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        dataSource.setJdbcUrl("jdbc:derby://localhost:1527/admin;create=true");
        dataSource.setMinimumIdle(3);
        dataSource.setConnectionTestQuery("values (1)");
        dataSource.setMaximumPoolSize(30);
        dataSource.setRegisterMbeans(true);
        return dataSource;
    }

    /**
     * Transaction manager 
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {

        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * as-is ibatis SqlMap Support to-be mybatis
     * 
     * @throws Exception
     */
    @Bean(name = "sqlMapClinet")
    @DependsOn(value = {"dataSource"})
    public ConfigurableSqlMapClientFactoryBean sqlMapClinet() throws Exception {
        ConfigurableSqlMapClientFactoryBean configurableSqlMapClientFactoryBean = new ConfigurableSqlMapClientFactoryBean();
        //Resource clientConfig;
        
        // 캐쉬 사용
        // 이렇게 하니까 dataSource 를 못 찾네...
        //configurableSqlMapClientFactoryBean.getObject().getConfiguration().setCacheEnabled(true);
        
        // typehandler 들인데 일단 무시 하자
        //configurableSqlMapClientFactoryBean.setConfigLocation(clientConfig); // xml 있음...
        configurableSqlMapClientFactoryBean.setDataSource(dataSource());
        //configurableSqlMapClientFactoryBean.setLobHandler(lobHandler); // 이거 implements 있음...
        return configurableSqlMapClientFactoryBean;
    }
} 
