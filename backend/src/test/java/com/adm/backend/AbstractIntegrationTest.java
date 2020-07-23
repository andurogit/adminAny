package com.adm.backend;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import javax.sql.DataSource;

// @RunWith(SpringRunner.class)
// @Transactional
@SpringBootTest
class AbstractIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    private void dataSourceInfo() throws SQLException {
        DataSource ds = (DataSource) context.getBean("dataSource");
        log.debug("Test DataSource : " + ds.toString());
        
        DatabaseMetaData md = null;
        ResultSet rs = null;
        
        

        md = ds.getConnection().getMetaData();
        rs = md.getCatalogs();

        
        while(rs.next()){
            log.debug(rs.getString(1));
        }   


        // DataSource ds = context.getBean(DataSource.class);
        // PoolConfiguration poolProperties = ds.getPoolProperties();
        // String url = poolProperties.getUrl();
        // String driverClassName = poolProperties.getDriverClassName();
        // String username = poolProperties.getUsername();
        // String password = poolProperties.getPassword();
        // log.debug("DataSource info -> URL: " + url + ", driver: " + driverClassName + ", username: " + username + ", password: " + password);
    }

    @Test
    void dataSourceTest(){}

}