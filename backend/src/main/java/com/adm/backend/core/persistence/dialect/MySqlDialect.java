/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.eyeq.kona.persistence.dialect;

import com.eyeq.kona.persistence.dialect.OracleDialect;
import com.eyeq.kona.persistence.dialect.lobhandler.MysqlLobHandler;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.springframework.jdbc.support.lob.LobHandler;

public class MySqlDialect
extends OracleDialect {
    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public String getDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getUrl() {
        return "jdbc:mysql://[address]:[port]/[DBName]";
    }

    @Override
    public String getValidationQuery() {
        return "select 1";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        return info.getDatabaseProductName().toLowerCase().contains("mysql");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("mysql");
    }

    @Override
    public String getSQLOverridePath() {
        return "com/eyeq/kona/persistence/dialect/MySqlOverrides.xml";
    }

    @Override
    public LobHandler getLobHandler(DatabaseMetaData info) throws SQLException {
        return new MysqlLobHandler();
    }
}

