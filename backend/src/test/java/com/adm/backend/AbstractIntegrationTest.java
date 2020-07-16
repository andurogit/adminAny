package com.adm.backend;

import javax.annotation.PostConstruct;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
public abstract class AbstractIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    private void dataSourceInfo() {
        DataSource ds = context.getBean(DataSource.class);
        PoolConfiguration poolProperties = ds.getPoolProperties();
        String url = poolProperties.getUrl();
        String driverClassName = poolProperties.getDriverClassName();
        String username = poolProperties.getUsername();
        String password = poolProperties.getPassword();
        log.debug("DataSource info -> URL: " + url + ", driver: " + driverClassName + ", username: " + username + ", password: " + password);
    }

}