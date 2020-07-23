package com.adm.backend.core.persistence.dialect;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adm.backend.core.model.metadata.TableColumn;

import org.apache.commons.lang.NullArgumentException;

public class SQLServerDialect extends AbstractSQLDialect {
    @Override
    public final String getName() {
        return "SQLServer";
    }

    @Override
    public String getNullCheckFunction(String columnName, String defaultValue) {
        return String.format("isnull(%s, %s)", columnName, defaultValue);
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
        buffer.append("create table [");
        buffer.append(tableName);
        buffer.append("] (");
        boolean isFirst = true;
        for (TableColumn column : columns) {
            if (isFirst) {
                isFirst = false;
            } else {
                buffer.append(", ");
            }
            buffer.append(" [");
            buffer.append(column.getName());
            buffer.append("] ");
            switch (column.getDataType()) {
                case DATE: {
                    buffer.append("datetime");
                    break;
                }
                case NUMERIC: {
                    buffer.append("numeric(");
                    buffer.append(column.getLength());
                    buffer.append(", 2)");
                    break;
                }
                case VARCHAR: {
                    buffer.append("varchar(");
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
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    @Override
    public String getUrl() {
        return "jdbc:sqlserver://[address]:[port]/databaseName=[dbName]";
    }

    @Override
    public String getValidationQuery() {
        return "select 0";
    }

    @Override
    public String getSQLOverridePath() {
        return "com/eyeq/kona/persistence/dialect/SQLServerOverrides.xml";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        String name = info.getDatabaseProductName();
        return name.toLowerCase().contains("sql server") || name.toLowerCase().contains("sybase") || name.toLowerCase().startsWith("ase");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("sqlserver");
    }

    @Override
    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement cs, int index) throws SQLException {
        return cs.executeQuery();
    }

}