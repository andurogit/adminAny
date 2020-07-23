package com.adm.backend.core.model.equation;

public enum ClauseType {
    AND("AND"),
    OR("OR");
    
    private String code;

    private ClauseType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}