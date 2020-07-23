package com.adm.backend.core.persistence.dialect;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adm.backend.core.ddlutils.platform.SAPHanaDBPlatform;
import com.adm.backend.core.model.metadata.TableColumn;

import org.apache.commons.lang.NullArgumentException;
import org.apache.ddlutils.PlatformFactory;

public class HDBDialect
extends AbstractSQLDialect {
    @Override
    public String getPreferredPlatformName() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "HDB";
    }

    @Override
    public String getNullCheckFunction(String columnName, String defaultValue) {
        return String.format("ifnull(max(%s), %s)", columnName, defaultValue);
    }

    @Override
    public String getDriverClassName() {
        return "com.sap.db.jdbc.Driver";
    }

    @Override
    public String getUrl() {
        return "jdbc:sap://[address]:[port]?reconnect=true";
    }

    @Override
    public String getValidationQuery() {
        return "select 10 from dummy";
    }

    @Override
    public String getSQLOverridePath() {
        return "com/eyeq/kona/persistence/dialect/HDBOverrides.xml";
    }

    @Override
    public boolean supports(DatabaseMetaData info) throws SQLException {
        return info.getDatabaseProductName().toLowerCase().contains("hdb");
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("hdb");
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
    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement cs, int index) throws SQLException {
        cs.execute();
        return cs.executeQuery();
    }

    static {
        PlatformFactory.registerPlatform((String)"HDB", SAPHanaDBPlatform.class);
    }

}

