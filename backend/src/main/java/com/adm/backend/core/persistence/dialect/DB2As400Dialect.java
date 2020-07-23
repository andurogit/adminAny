package com.adm.backend.core.persistence.dialect;

import java.sql.SQLException;

public class DB2As400Dialect
extends DB2Dialect {
    @Override
    public String getPreferredPlatformName() {
        return "DB2As400";
    }

    @Override
    public String getDriverClassName() {
        return "com.ibm.as400.access.AS400JDBCDriver";
    }

    @Override
    public String getUrl() {
        return "jdbc:as400://@[address]";
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("db2as400");
    }
}

