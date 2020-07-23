package com.adm.backend.core.persistence.dialect;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adm.backend.core.model.mart.IllegalMartStatusException;
import com.adm.backend.core.model.mart.ItemType;
import com.adm.backend.core.model.mart.MartStatus;
import com.adm.backend.core.model.mart.MartTable;
import com.adm.backend.core.model.mart.MartTableItem;
import com.adm.backend.core.model.metadata.IllegalMetaDataStatusException;
import com.adm.backend.core.model.metadata.MetaData;
import com.adm.backend.core.model.metadata.MetaDataItem;
import com.adm.backend.core.model.metadata.MetaDataStatus;
import com.adm.backend.core.model.metadata.TableColumn;

import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;


public abstract class AbstractSQLDialect implements SQLDialect {
    @Override
    public String getPreferredPlatformName() {
        return null;
    }

    @Override
    public String createSqlSyntaxByMartData(MartTable table, List<MartTableItem> items) {
        if (MartStatus.COMPLETED.equals((Object)table.getStatus())) {
            String msg = "Cannot modify mart table in COMPLETED status : " + table;
            throw new IllegalMartStatusException(msg, table);
        }
        ArrayList<MartTableItem> columns = new ArrayList<MartTableItem>(items.size());
        for (MartTableItem item : items) {
            if (ItemType.CONDITION.equals((Object)item.getType())) continue;
            columns.add(item);
        }
        return this.getCreateTableSyntax(table.getUserKey1(), columns);
    }

    @Override
    public String createSqlSyntaxByMetaData(MetaData data, List<MetaDataItem> items) {
        if (MetaDataStatus.IN_USE.equals((Object)data.getStatus())) {
            String msg = "The target table has been already created for the meta data : " + data;
            throw new IllegalMetaDataStatusException(msg, data);
        }
        return this.getCreateTableSyntax(data.getUserKey1(), items);
    }

    protected abstract <T extends TableColumn> String getCreateTableSyntax(String var1, List<T> var2);

    @Override
    public LobHandler getLobHandler(DatabaseMetaData info) throws SQLException {
        return new DefaultLobHandler();
    }

    @Override
    public int getCallableStatementResultSetType() {
        return 1111;
    }

    @Override
    public ResultSet getExecuteCallableStatementWithResultSet(CallableStatement cs, int index) throws SQLException {
        cs.execute();
        return (ResultSet)cs.getObject(index);
    }
}