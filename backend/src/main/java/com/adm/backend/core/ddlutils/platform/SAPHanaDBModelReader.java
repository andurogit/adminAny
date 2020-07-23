
package com.adm.backend.core.ddlutils.platform;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.platform.DatabaseMetaDataWrapper;
import org.apache.ddlutils.platform.JdbcModelReader;

public class SAPHanaDBModelReader
extends JdbcModelReader {
    public SAPHanaDBModelReader(Platform platform) {
        super(platform);
        this.setDefaultCatalogPattern(null);
        this.setDefaultSchemaPattern(null);
        this.setDefaultTablePattern("%");
        new SAPHanaDBTypeMap();
    }

    protected Column readColumn(DatabaseMetaDataWrapper metaData, HashMap<String,Object> values) throws SQLException {
        if ((Integer)values.get("DATA_TYPE") == 2005) {
            values.put("DATA_TYPE", 2011);
        }
        return super.readColumn(metaData, values);
    }
}

