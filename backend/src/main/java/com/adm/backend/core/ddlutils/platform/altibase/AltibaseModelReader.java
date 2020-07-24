/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.ddlutils.Platform
 *  org.apache.ddlutils.platform.JdbcModelReader
 */
package com.adm.backend.core.ddlutils.platform.altibase;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.platform.JdbcModelReader;

public class AltibaseModelReader
extends JdbcModelReader {
    public AltibaseModelReader(Platform platform) {
        super(platform);
        this.setDefaultCatalogPattern(null);
        this.setDefaultSchemaPattern(null);
        this.setDefaultTablePattern("%");
    }
}

