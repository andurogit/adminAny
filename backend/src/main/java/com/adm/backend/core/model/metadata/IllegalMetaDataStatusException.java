package com.adm.backend.core.model.metadata;

public class IllegalMetaDataStatusException extends RuntimeException {
    private static final long serialVersionUID = -9200808381096999987L;
    private MetaData data;

    public IllegalMetaDataStatusException(String message, MetaData data) {
        super(message);
        this.data = data;
    }

    public void setData(MetaData data) {
        this.data = data;
    }

    public MetaData getData() {
        return this.data;
    }
}