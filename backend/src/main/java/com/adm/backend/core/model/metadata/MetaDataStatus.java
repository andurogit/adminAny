package com.adm.backend.core.model.metadata;

public enum MetaDataStatus {
    CREATED("Y"),
    IN_USE("E");
    
    private String flag;

    private MetaDataStatus(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return this.flag;
    }
}