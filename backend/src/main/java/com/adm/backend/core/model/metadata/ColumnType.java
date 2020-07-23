package com.adm.backend.core.model.metadata;

public enum ColumnType {
    NUMERIC("NUM"),
    VARCHAR("CHA"),
    DATE("DAT"),
    BLOB("BLO"),
    CLOB("CLO");
    
    private String code;

    private ColumnType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }   
}