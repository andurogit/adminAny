/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.ddlutils.Platform
 *  org.apache.ddlutils.PlatformInfo
 *  org.apache.ddlutils.platform.JdbcModelReader
 *  org.apache.ddlutils.platform.SqlBuilder
 *  org.apache.ddlutils.platform.oracle.Oracle10Platform
 */
package com.adm.backend.core.ddlutils.platform.altibase;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformInfo;
import org.apache.ddlutils.platform.JdbcModelReader;
import org.apache.ddlutils.platform.SqlBuilder;
import org.apache.ddlutils.platform.oracle.Oracle10Platform;

public class AltibasePlatform
extends Oracle10Platform {
    public static final String DATABASE_NAME = "Altibase";
    public static final String JDBC_DRIVER = "Altibase.jdbc.driver.AltibaseDriver";
    public static final String JDBC_SUB_PROTOCOL = "altibase";

    public AltibasePlatform() {
        this.setModelReader((JdbcModelReader)new AltibaseModelReader((Platform)this));
        this.setSqlBuilder((SqlBuilder)new AltibaseBuilder((Platform)this));
        PlatformInfo info = this.getPlatformInfo();
        info.addNativeTypeMapping(-5, "BIGINT");
        info.addNativeTypeMapping(-2, "BYTE");
        info.addNativeTypeMapping(-7, "CHAR(1)");
        info.addNativeTypeMapping(91, "DATE");
        info.addNativeTypeMapping(3, "DECIMAL");
        info.addNativeTypeMapping(2001, "BLOB", 2004);
        info.addNativeTypeMapping(8, "DOUBLE");
        info.addNativeTypeMapping(6, "FLOAT");
        info.addNativeTypeMapping(2000, "BLOB", 2004);
        info.addNativeTypeMapping(-4, "BLOB", 2004);
        info.addNativeTypeMapping(-1, "CLOB", 2005);
        info.addNativeTypeMapping(0, "BLOB", 2004);
        info.addNativeTypeMapping(2, "NUMERIC");
        info.addNativeTypeMapping(1111, "BLOB", 2004);
        info.addNativeTypeMapping(2006, "BLOB", 2004);
        info.addNativeTypeMapping(5, "SMALLINT");
        info.addNativeTypeMapping(2002, "BLOB", 2004);
        info.addNativeTypeMapping(92, "DATE");
        info.addNativeTypeMapping(93, "DATE");
        info.addNativeTypeMapping(-6, "SMALLINT");
        info.addNativeTypeMapping(-3, "BLOB");
        info.addNativeTypeMapping(12, "VARCHAR");
        info.addNativeTypeMapping("BOOLEAN", "CHAR(1)");
        info.addNativeTypeMapping("DATALINK", "BLOB");
    }

    public String getName() {
        return DATABASE_NAME;
    }
}

