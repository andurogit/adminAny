/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.ddlutils.Platform
 *  org.apache.ddlutils.model.Column
 *  org.apache.ddlutils.model.Database
 *  org.apache.ddlutils.model.Table
 *  org.apache.ddlutils.platform.oracle.Oracle10Builder
 */
package com.adm.backend.core.ddlutils.platform.altibase;

import java.io.IOException;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.platform.oracle.Oracle10Builder;

public class AltibaseBuilder
extends Oracle10Builder {
    public AltibaseBuilder(Platform platform) {
        super(platform);
    }

    public void addColumn(Database model, Table table, Column newColumn) throws IOException {
        this.print("ALTER TABLE ");
        this.printlnIdentifier(this.getTableName(table));
        this.printIndent();
        this.print("ADD COLUMN ");
        this.print("(");
        this.writeColumn(table, newColumn);
        this.print(")");
        this.printEndOfStatement();
    }

    public void dropTable(Table table) throws IOException {
        for (Column column : table.getAutoIncrementColumns()) {
            this.dropAutoIncrementTrigger(table, column);
            this.dropAutoIncrementSequence(table, column);
        }
        this.print("DROP TABLE ");
        this.printIdentifier(this.getTableName(table));
        this.print(" CASCADE CONSTRAINTS");
        this.printEndOfStatement();
    }
}

