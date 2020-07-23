package com.adm.backend.core.persistence.dialect;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adm.backend.core.model.metadata.TableColumn;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import oracle.jdbc.OracleCallableStatement;
import org.apache.commons.dbcp.DelegatingCallableStatement;

public class OracleDialect extends AbstractSQLDialect {
    @Override
    public String getName() {
        return "Oracle";
    }

    @Override
    public String getNullCheckFunction(String columnName, String defaultValue) {
        return String.format("nvl(%s, %s)", columnName, defaultValue);
    }

    @Override
    protected <T extends TableColumn> String getCreateTableSyntax(String tableName, List<T> columns) {
        if (tableName == null) {
            throw new NullArgumentException("tableName");
        }
        if (columns == null) {
            throw new NullArgumentException("columns");
        }
        if (columns.isEmpty()) {
            String msg = "The specified schema does not have any columns : " + tableName;
            throw new IllegalStateException(msg);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("create table ");
        buffer.append(tableName);
        buffer.append(" (");
        boolean isFirst = true;
        for (TableColumn column : columns) {
            if (isFirst) {
                isFirst = false;
            } else {
                buffer.append(", ");
            }
            buffer.append(column.getName());
            buffer.append(" ");
            switch (column.getDataType()) {
                case DATE: {
                    buffer.append("date");
                    break;
                }
                case NUMERIC: {
                    buffer.append("number(");
                    buffer.append(column.getLength());
                    buffer.append(", 0)");
                    break;
                }
                case VARCHAR: {
                    buffer.append("varchar2(");
                    buffer.append(column.getLength());
                    buffer.append(")");
                }
				default:
					break;
            }
            buffer.append(" NULL");
        }
        buffer.append(")");
        return buffer.toString();
    }

    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.OracleDriver";
    }

    @Override
    public String getUrl() {
        return "jdbc:oracle:thin://@[address]:[port]:[sid]";
    }

    @Override
    public String getValidationQuery() {
        return "select 0 from dual";
    }

    @Override
    public LobHandler getLobHandler(DatabaseMetaData info) throws SQLException {
        return new DefaultLobHandler();
        // if (info.getDatabaseMajorVersion() > 9) {
        //     return new DefaultLobHandler();
        // }
        // OracleLobHandler handler = new OracleLobHandler();
        // handler.setNativeJdbcExtractor((NativeJdbcExtractor)new CommonsDbcpNativeJdbcExtractor());
        // return handler;
    }

    @Override
    public String getSQLOverridePath() {
        return "com/eyeq/kona/persistence/dialect/OracleOverrides.xml";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        return info.getDatabaseProductName().toLowerCase().contains("oracle");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("oracle");
    }

    @Override
    public int getCallableStatementResultSetType() {
        return -10;
    }

    @Override
    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement cs, int index) throws SQLException {
        cs.execute();
        if (cs instanceof DelegatingCallableStatement) {
            cs = (CallableStatement)((DelegatingCallableStatement)cs).getDelegate();
        }
        if (cs instanceof OracleCallableStatement) {
            return ((OracleCallableStatement)cs).getCursor(index);
        }
        return super.getExecuteCallableStatementWithResultSet(cs, index);
    }
    
}