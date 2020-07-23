/*
 * Decompiled with CFR 0.145.
 */
package com.eyeq.kona.persistence.dialect;

import com.eyeq.kona.model.metadata.TableColumn;
import com.eyeq.kona.persistence.dialect.AbstractSQLDialect;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public class TeraDataDialect
extends AbstractSQLDialect {
    @Override
    public String getPreferredPlatformName() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "TeraData";
    }

    @Override
    public String getNullCheckFunction(String columnName, String defaultValue) {
        return null;
    }

    @Override
    public String getDriverClassName() {
        return "com.teradata.jdbc.TeraDriver";
    }

    @Override
    public String getUrl() {
        return "jdbc:teradata://[host]/DATABASE=[DBName],DBS_PORT=[port]";
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
        return info.getDatabaseProductName().toLowerCase().contains("teradata");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("teradata");
    }

    @Override
    protected <T extends TableColumn> String getCreateTableSyntax(String tableName, List<T> columns) {
        return null;
    }
}

