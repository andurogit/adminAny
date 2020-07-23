package com.adm.backend.core.persistence.dialect;

import java.sql.SQLException;

public class SybaseDialect
extends SQLServerDialect {
    @Override
    public String getDriverClassName() {
        return "net.sourceforge.jtds.jdbc.Driver";
    }

    @Override
    public String getUrl() {
        return "jdbc:jtds:sybase://[address]:[port]/[DBName];tds=5.0;lastupdatecount=true;charset=UTF-8";
    }

    @Override
    public boolean supports(String databaseType) throws SQLException {
        return databaseType.toLowerCase().contains("sybase");
    }
}

