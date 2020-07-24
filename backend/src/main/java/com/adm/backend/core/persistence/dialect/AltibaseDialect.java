package com.adm.backend.core.persistence.dialect;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.adm.backend.core.ddlutils.platform.altibase.AltibasePlatform;
import com.adm.backend.core.persistence.dialect.lobhandler.AltibaseLobHandler;

import org.apache.commons.dbcp.DelegatingCallableStatement;
import org.apache.ddlutils.PlatformFactory;
import org.springframework.jdbc.support.lob.LobHandler;

import Altibase.jdbc.driver.ABCallableStatement;

public class AltibaseDialect
extends OracleDialect {
    @Override
    public String getPreferredPlatformName() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "Altibase";
    }

    @Override
    public String getDriverClassName() {
        return "Altibase.jdbc.driver.AltibaseDriver";
    }

    @Override
    public String getUrl() {
        return "jdbc:Altibase://[host]:[port]/[DBName]";
    }

    @Override
    public String getSQLOverridePath() {
        return "com/eyeq/kona/persistence/dialect/AltibaseOverrides.xml";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        return info.getDatabaseProductName().toLowerCase().contains("altibase");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("altibase");
    }

    @Override
    public int getCallableStatementResultSetType() {
        return 1003;
    }

    @Override
    public LobHandler getLobHandler(DatabaseMetaData info) {
        return new AltibaseLobHandler();
    }

    @Override
    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement cs, int index) throws SQLException {
        cs.execute();
        if (cs instanceof DelegatingCallableStatement) {
            cs = (CallableStatement)((DelegatingCallableStatement)cs).getDelegate();
        }
        if (cs instanceof ABCallableStatement) {
            return cs.getResultSet();
        }
        return super.getExecuteCallableStatementWithResultSet(cs, index);
    }

    static {
        PlatformFactory.registerPlatform((String)"Altibase", AltibasePlatform.class);
    }
}

