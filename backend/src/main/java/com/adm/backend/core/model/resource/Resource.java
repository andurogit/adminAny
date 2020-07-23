package com.adm.backend.core.model.resource;

import com.adm.backend.core.model.DataModel;

public interface Resource extends DataModel {
    public String getId();

    public String getName();

    public ResourceType getType();
}