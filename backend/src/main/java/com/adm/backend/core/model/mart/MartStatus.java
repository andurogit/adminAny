package com.adm.backend.core.model.mart;

public enum MartStatus {
    PENDING("Y"),
    COMPLETED("E");
    
    private String flag;

    private MartStatus(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return this.flag;
    }
}