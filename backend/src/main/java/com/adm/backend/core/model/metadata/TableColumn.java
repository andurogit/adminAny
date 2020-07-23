package com.adm.backend.core.model.metadata;

import com.adm.backend.core.model.DataModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class TableColumn implements DataModel, Cloneable {
    private static final long serialVersionUID = 3431381470619061520L;
    private String id;
    private String name;
    private String localizedName;
    private int length;
    private String description;
    private ColumnType dataType;

    public boolean equals(Object obj) {
        if (!(obj instanceof TableColumn)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TableColumn column = (TableColumn)obj;
        return new EqualsBuilder().append((Object)this.id, (Object)column.id).append((Object)this.name, (Object)column.name).append((Object)this.dataType, (Object)column.dataType).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append((Object)this.id).append((Object)this.name).append((Object)this.dataType).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("id", (Object)this.id).append("name", (Object)this.name).append("localizedName", (Object)this.localizedName).toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDataType(ColumnType dataType) {
        this.dataType = dataType;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public int getLength() {
        return this.length;
    }

    public String getDescription() {
        return this.description;
    }

    public ColumnType getDataType() {
        return this.dataType;
    }
}