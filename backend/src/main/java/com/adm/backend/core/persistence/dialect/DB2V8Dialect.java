
package com.adm.backend.core.persistence.dialect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.commons.lang3.math.NumberUtils;

public class DB2V8Dialect
extends DB2Dialect {
    @Override
    public String getPreferredPlatformName() {
        return "DB2V8";
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
        return databaseType.toLowerCase().contains("db2v8");
    }
}

