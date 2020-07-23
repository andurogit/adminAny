package com.adm.backend.core.model.mart;

public enum ItemType {
    GROUP_BY("GI"),
    SUM("SI"),
    CONDITION("CI");
    
    private String code;

    private ItemType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}