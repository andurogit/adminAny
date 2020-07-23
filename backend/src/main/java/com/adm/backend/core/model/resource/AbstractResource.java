package com.adm.backend.core.model.resource;

public abstract class AbstractResource implements Resource {
    private static final long serialVersionUID = -8196210351927958138L;
    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String toString() {
        return "AbstractResource(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}