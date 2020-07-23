package com.adm.backend.core.persistence.dialect;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adm.backend.core.model.mart.MartTable;

import org.springframework.jdbc.support.lob.LobHandler;

import com.adm.backend.core.model.mart.MartTableItem;
import com.adm.backend.core.model.metadata.MetaData;
import com.adm.backend.core.model.metadata.MetaDataItem;


/**
 * SQLDialect
 */
public interface SQLDialect {
    public String getPreferredPlatformName();

    public String getName();

    public String createSqlSyntaxByMartData(MartTable var1, List<MartTableItem> var2);

    public String createSqlSyntaxByMetaData(MetaData var1, List<MetaDataItem> var2);

    public String getNullCheckFunction(String var1, String var2);

    public String getDriverClassName();

    public String getUrl();

    public String getValidationQuery();

    public String getSQLOverridePath();

    public int getCallableStatementResultSetType();

    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement var1, int var2) throws SQLException;

    public boolean supports(DatabaseMetaData var1) throws SQLException;

    public boolean supports(String var1) throws SQLException;

    public LobHandler getLobHandler(DatabaseMetaData var1) throws SQLException;
    
}