package com.adm.backend.core.model.resource;

import com.adm.backend.core.util.JCOUtils;


public enum ResourceType {
    JDBC("J", "jdbcResourceConnector", "\ub370\uc774\ud130\ubca0\uc774\uc2a4"),
    XMLA("X", "xmlaResourceConnector", "OLAP(MDX/XMLA)"),
    Mondrian("M", "mondrianResourceConnector", "OLAP(Mondrian)"),
    JCO("C", "sapJCOResourceConnector", "SAP BW(JCO)"){

        @Override
        public boolean isSupported() {
            return JCOUtils.isJCOLibraryAvailable();
        }
    };
    
    private String code;
    private String connectorName;
    private String description;

    private ResourceType(String code, String connectorName, String description) {
        this.code = code;
        this.connectorName = connectorName;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isSupported() {
        return true;
    }

    public String toString() {
        return this.connectorName;
    }

}