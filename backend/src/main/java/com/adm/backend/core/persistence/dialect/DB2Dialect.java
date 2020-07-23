package com.adm.backend.core.persistence.dialect;



import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adm.backend.core.model.metadata.TableColumn;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.math.NumberUtils;

public class DB2Dialect
extends AbstractSQLDialect {
    @Override
    public final String getName() {
        return "DB2";
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
                    buffer.append("numeric(");
                    buffer.append(column.getLength());
                    buffer.append(", 0)");
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
        return "com.ibm.db2.jcc.DB2Driver";
    }

    @Override
    public String getUrl() {
        return "jdbc:db2://@[address]:[port]/[DBName]";
    }

    @Override
    public String getValidationQuery() {
        return "SELECT 1 FROM SYSIBM.SYSDUMMY1";
    }

    @Override
    public String getSQLOverridePath() {
        return "com/eyeq/kona/persistence/dialect/DB2Overrides.xml";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        String version;
        String productVersion = info.getDatabaseProductVersion();
        int index = productVersion.indexOf("SQL");
        if (index >= 0 && NumberUtils.isCreatable((String)(version = productVersion.substring(index + 4, index + 6)))) {
            return Integer.parseInt(version) < 80;
        }
        return false;
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("db2");
    }

    @Override
    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement cs, int index) throws SQLException {
        cs.execute();
        return cs.executeQuery();
    }

}

