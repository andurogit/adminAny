package com.adm.backend.core.ddlutils.platform;

import java.io.IOException;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.alteration.AddColumnChange;
import org.apache.ddlutils.alteration.AddPrimaryKeyChange;
import org.apache.ddlutils.alteration.ColumnDefinitionChange;
import org.apache.ddlutils.alteration.ModelComparator;
import org.apache.ddlutils.alteration.PrimaryKeyChange;
import org.apache.ddlutils.alteration.RemoveColumnChange;
import org.apache.ddlutils.alteration.RemovePrimaryKeyChange;
import org.apache.ddlutils.alteration.TableChange;
import org.apache.ddlutils.alteration.TableDefinitionChangesPredicate;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.platform.CreationParameters;
import org.apache.ddlutils.platform.DefaultTableDefinitionChangesPredicate;
import org.apache.ddlutils.platform.JdbcModelReader;
import org.apache.ddlutils.platform.PlatformImplBase;
import org.apache.ddlutils.platform.SqlBuilder;
import org.apache.ddlutils.util.StringUtilsExt;

public class SAPHanaDBPlatform
extends PlatformImplBase {
    public static String DATABASE_NAME = "HANADB";
    public static String JDBC_DRIVER = "com.sap.db.jdbc.Driver";
    public static String JDBC_SUB_PROTOCOL = "hanadb";

    public SAPHanaDBPlatform() {
        this.setModelReader((JdbcModelReader)new SAPHanaDBModelReader((Platform)this));
        this.setSqlBuilder((SqlBuilder)new SAPHanaDBBuilder((Platform)this));
    }

    public String getName() {
        return DATABASE_NAME;
    }

    protected ModelComparator getModelComparator() {
        ModelComparator comparator = super.getModelComparator();
        comparator.setCanDropPrimaryKeyColumns(false);
        comparator.setGeneratePrimaryKeyChanges(false);
        return comparator;
    }

    protected TableDefinitionChangesPredicate getTableDefinitionChangesPredicate() {
        return new DefaultTableDefinitionChangesPredicate(){

            protected boolean isSupported(Table intermediateTable, TableChange change) {
                if (change instanceof RemoveColumnChange || change instanceof AddPrimaryKeyChange || change instanceof PrimaryKeyChange || change instanceof RemovePrimaryKeyChange) {
                    return true;
                }
                if (change instanceof AddColumnChange) {
                    AddColumnChange addColumnChange = (AddColumnChange)change;
                    return addColumnChange.getNextColumn() == null && (!addColumnChange.getNewColumn().isRequired() || !StringUtilsExt.isEmpty((String)addColumnChange.getNewColumn().getDefaultValue()));
                }
                if (change instanceof ColumnDefinitionChange) {
                    ColumnDefinitionChange colChange = (ColumnDefinitionChange)change;
                    Column curColumn = intermediateTable.findColumn(colChange.getChangedColumn(), SAPHanaDBPlatform.this.isDelimitedIdentifierModeOn());
                    Column newColumn = colChange.getNewColumn();
                    return curColumn.getTypeCode() == newColumn.getTypeCode() && (!SAPHanaDBPlatform.this.getPlatformInfo().hasSize(curColumn.getTypeCode()) || StringUtilsExt.equals((String)curColumn.getSize(), (String)newColumn.getSize())) && curColumn.isAutoIncrement() == newColumn.isAutoIncrement();
                }
                return false;
            }
        };
    }

    public void processChange(Database currentModel, CreationParameters params, RemoveColumnChange change) throws IOException {
        Table changedTable = this.findChangedTable(currentModel, (TableChange)change);
        Column removedColumn = changedTable.findColumn(change.getChangedColumn(), this.isDelimitedIdentifierModeOn());
        ((SAPHanaDBBuilder)this.getSqlBuilder()).dropColumn(changedTable, removedColumn);
        change.apply(currentModel, this.isDelimitedIdentifierModeOn());
    }

    public void processChange(Database currentModel, CreationParameters params, RemovePrimaryKeyChange change) throws IOException {
        Table changedTable = this.findChangedTable(currentModel, (TableChange)change);
        ((SAPHanaDBBuilder)this.getSqlBuilder()).dropPrimaryKey(changedTable);
        change.apply(currentModel, this.isDelimitedIdentifierModeOn());
    }

    public void processChange(Database currentModel, CreationParameters params, PrimaryKeyChange change) throws IOException {
        Table changedTable = this.findChangedTable(currentModel, (TableChange)change);
        String[] newPKColumnNames = change.getNewPrimaryKeyColumns();
        Column[] newPKColumns = new Column[newPKColumnNames.length];
        for (int colIdx = 0; colIdx < newPKColumnNames.length; ++colIdx) {
            newPKColumns[colIdx] = changedTable.findColumn(newPKColumnNames[colIdx], this.isDelimitedIdentifierModeOn());
        }
        ((SAPHanaDBBuilder)this.getSqlBuilder()).dropPrimaryKey(changedTable);
        this.getSqlBuilder().createPrimaryKey(changedTable, newPKColumns);
        change.apply(currentModel, this.isDelimitedIdentifierModeOn());
    }

    public void processChange(Database currentModel, CreationParameters params, ColumnDefinitionChange change) throws IOException {
        Table changedTable = this.findChangedTable(currentModel, (TableChange)change);
        Column changedColumn = changedTable.findColumn(change.getChangedColumn(), this.isDelimitedIdentifierModeOn());
        ((SAPHanaDBBuilder)this.getSqlBuilder()).dropColumn(changedTable, changedColumn);
        ((SAPHanaDBBuilder)this.getSqlBuilder()).addColumn(changedTable, change.getNewColumn());
        change.apply(currentModel, this.isDelimitedIdentifierModeOn());
    }

    public void processChange(Database currentModel, CreationParameters params, AddColumnChange change) throws IOException {
        Table changedTable = this.findChangedTable(currentModel, (TableChange)change);
        Column newColumn = change.getNewColumn();
        ((SAPHanaDBBuilder)this.getSqlBuilder()).addColumn(changedTable, newColumn);
    }

}

