package com.adm.backend.core.ddlutils.platform;

import java.io.IOException;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.platform.SqlBuilder;

public class SAPHanaDBBuilder
extends SqlBuilder {
    public SAPHanaDBBuilder(Platform platform) {
        super(platform);
        this.addEscapedCharSequence("'", "''");
    }

    protected void writeCastExpression(Column sourceColumn, Column targetColumn) throws IOException {
        boolean typeEqual;
        //boolean bl = typeEqual = sourceColumn.getTypeCode() == targetColumn.getTypeCode();
        typeEqual = sourceColumn.getTypeCode() == targetColumn.getTypeCode();
        if (!typeEqual) {
            switch (targetColumn.getTypeCode()) {
                case 2011: {
                    this.print("TO_NCLOB(");
                    break;
                }
                case -15: {
                    this.print("TO_NCHAR(");
                    break;
                }
                case -9: {
                    this.print("TO_NVARCHAR(");
                    break;
                }
            }
        }
        this.printIdentifier(this.getColumnName(sourceColumn));
        if (!typeEqual) {
            this.print(")");
            this.print(" AS ");
            this.printIdentifier(this.getColumnName(sourceColumn));
        }
    }

    public void dropPrimaryKey(Table table) throws IOException {
        this.print("ALTER TABLE ");
        this.printlnIdentifier(this.getTableName(table));
        this.printIndent();
        this.print("DROP PRIMARY KEY");
        this.printEndOfStatement();
    }

    public void createPrimaryKey(Table table, Column[] primaryKeyColumns) throws IOException {
        if (primaryKeyColumns.length <= 0 || !this.shouldGeneratePrimaryKeys(primaryKeyColumns)) {
            return;
        }
        this.print("ALTER TABLE ");
        this.printlnIdentifier(this.getTableName(table));
        this.printIndent();
        this.print("ADD ");
        this.writePrimaryKeyStmt(table, primaryKeyColumns);
        this.printEndOfStatement();
    }

    public void addColumn(Table table, Column newColumn) throws IOException {
        this.print("ALTER TABLE ");
        this.printlnIdentifier(this.getTableName(table));
        this.printIndent();
        this.print("ADD ");
        this.print("(");
        this.writeColumn(table, newColumn);
        this.print(")");
        this.printEndOfStatement();
    }

    public void dropColumn(Table table, Column column) throws IOException {
        this.print("ALTER TABLE ");
        this.printlnIdentifier(this.getTableName(table));
        this.printIndent();
        this.print("DROP ");
        this.print("(");
        this.printIdentifier(this.getColumnName(column));
        this.print(")");
        this.printEndOfStatement();
    }
}

