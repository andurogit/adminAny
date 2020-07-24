/*
 * Decompiled with CFR 0.145.
 */
package com.adm.backend.core.persistence.dialect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import com.adm.backend.core.model.metadata.TableColumn;

public class NetezzaDialect
extends AbstractSQLDialect {
    @Override
    public String getPreferredPlatformName() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "Netezza";
    }

    @Override
    public String getNullCheckFunction(String columnName, String defaultValue) {
        return null;
    }

    @Override
    public String getDriverClassName() {
        return "org.netezza.Driver";
    }

    @Override
    public String getUrl() {
        return "jdbc:netezza://[address]:[port]/[DBName]";
    }

    @Override
    public String getValidationQuery() {
        return "select 0";
    }

    @Override
    public String getSQLOverridePath() {
        return null;
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        return info.getDatabaseProductName().toLowerCase().contains("netezza");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("netezza");
    }

    @Override
    protected <T extends TableColumn> String getCreateTableSyntax(String tableName, List<T> columns) {
        return null;
    }
}

