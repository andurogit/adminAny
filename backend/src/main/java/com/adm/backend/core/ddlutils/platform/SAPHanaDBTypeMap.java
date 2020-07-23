/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.ddlutils.model.JdbcTypeCategoryEnum
 *  org.apache.ddlutils.model.TypeMap
 */
package com.adm.backend.core.ddlutils.platform;

import org.apache.ddlutils.model.JdbcTypeCategoryEnum;
import org.apache.ddlutils.model.TypeMap;

public class SAPHanaDBTypeMap
extends TypeMap {
    public static final String NVARCHAR = "NVARCHAR";
    public static final String NCHAR = "NCHAR";
    public static final String NCLOB = "NCLOB";

    static {
        SAPHanaDBTypeMap.registerJdbcType((int)2011, (String)NCLOB, (JdbcTypeCategoryEnum)JdbcTypeCategoryEnum.TEXTUAL);
        SAPHanaDBTypeMap.registerJdbcType((int)-9, (String)NVARCHAR, (JdbcTypeCategoryEnum)JdbcTypeCategoryEnum.TEXTUAL);
        SAPHanaDBTypeMap.registerJdbcType((int)-15, (String)NCHAR, (JdbcTypeCategoryEnum)JdbcTypeCategoryEnum.TEXTUAL);
    }
}

