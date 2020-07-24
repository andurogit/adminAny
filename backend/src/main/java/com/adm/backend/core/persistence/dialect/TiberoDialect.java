package com.adm.backend.core.persistence.dialect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class TiberoDialect
extends OracleDialect {
    @Override
    public String getName() {
        return "Tibero";
    }

    @Override
    public String getDriverClassName() {
        return "com.tmax.tibero.jdbc.TbDriver";
    }

    @Override
    public String getUrl() {
        return "jdbc:tibero:thin:@[address]:[port]:[sid]";
    }

    @Override
    public String getValidationQuery() {
        return "select 0 from dual";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        return info.getDatabaseProductName().toLowerCase().contains("tibero");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("tibero");
    }
}

