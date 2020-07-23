package com.adm.backend.core.model.mart;

public class IllegalMartStatusException extends RuntimeException {
    private static final long serialVersionUID = -6718242286867209398L;
    private MartTable table;

    public IllegalMartStatusException(String message, MartTable table) {
        super(message);
        this.table = table;
    }

    public void setTable(MartTable table) {
        this.table = table;
    }

    public MartTable getTable() {
        return this.table;
    }
}