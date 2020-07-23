package com.adm.backend.core.model.resource;

import com.adm.backend.core.persistence.dialect.AltibaseDialect;
import com.adm.backend.core.persistence.dialect.DB2As400Dialect;
import com.adm.backend.core.persistence.dialect.DB2Dialect;
import com.adm.backend.core.persistence.dialect.DB2V8As400Dialect;
import com.adm.backend.core.persistence.dialect.DB2V8Dialect;
import com.adm.backend.core.persistence.dialect.DerbyDialect;
import com.adm.backend.core.persistence.dialect.HDBDialect;
import com.adm.backend.core.persistence.dialect.MySqlDialect;
import com.adm.backend.core.persistence.dialect.NetezzaDialect;
import com.adm.backend.core.persistence.dialect.OracleDialect;
import com.adm.backend.core.persistence.dialect.SQLDialect;
import com.adm.backend.core.persistence.dialect.SQLServerDialect;
import com.adm.backend.core.persistence.dialect.SybaseDialect;
import com.adm.backend.core.persistence.dialect.TeraDataDialect;
import com.adm.backend.core.persistence.dialect.TiberoDialect;
import com.adm.backend.core.persistence.dialect.VectorWiseDialect;



public enum DatabaseType {
    ORACLE("OR", "Oracle", new OracleDialect()),
    SQLSERVER("SS", "Microsoft SQLServer", new SQLServerDialect()),
    SYBASE("SY", "Sybase ASE", new SybaseDialect()),
    DB2("D2", "DB2", new DB2Dialect()),
    DB2V8("D8", "DB2V8", new DB2V8Dialect()),
    DB2As400("DA", "DB2As400", new DB2As400Dialect()),
    DB2V8As400("A8", "DB2V8As400", new DB2V8As400Dialect()),
    DERBY("DB", "Apache Derby", new DerbyDialect()),
    HANADB("HD", "SAP HANADB", new HDBDialect()),
    VectorWiseDB("VW", "VectorWiseDB", new VectorWiseDialect()),
    TeraData("TR", "TeraData", new TeraDataDialect()),
    Netezza("NT", "Netezza", new NetezzaDialect()),
    Altibase("AL", "Altibase", new AltibaseDialect()),
    Tibero("TI", "Tibero", new TiberoDialect()),
    MySql("MS", "MySql", new MySqlDialect());
    
    private String code;
    private String description;
    private SQLDialect dialect;

    private DatabaseType(String code, String description, SQLDialect dialect) {
        this.code = code;
        this.description = description;
        this.dialect = dialect;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public SQLDialect getDialect() {
        return this.dialect;
    }
}