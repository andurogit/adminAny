package com.adm.backend.core.persistence.dialect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.commons.lang3.math.NumberUtils;

public class DB2V8As400Dialect
extends DB2Dialect {
    @Override
    public String getPreferredPlatformName() {
        return "DB2V8As400";
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
    public boolean supports(DatabaseMetaData info) throws SQLException {
        String version;
        String productVersion = info.getDatabaseProductVersion();
        int index = productVersion.indexOf("SQL");
        if (index >= 0 && NumberUtils.isCreatable((String)(version = productVersion.substring(index + 4, index + 6)))) {
            return Integer.parseInt(version) >= 80;
        }
        return false;
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("db2v8as400");
    }
}

