package com.adm.backend.core.model.metadata;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.adm.backend.core.model.DataModel;
import com.adm.backend.core.model.resource.JDBCResource;
import com.adm.backend.core.util.SerializeUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class MetaData implements DataModel, Cloneable, Comparable<MetaData> {
    
    private static final long serialVersionUID = -8320491777548730046L;
    private String id;
    private String name;
    private int recordCount = 0;
    private String userKey1;
    private MetaDataStatus status = MetaDataStatus.CREATED;
    private JDBCResource resource;

    public MetaData(String id) {
        this.id = id;
    }

    public MetaData clone() throws CloneNotSupportedException {
        return (MetaData)super.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MetaData)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return EqualsBuilder.reflectionEquals((Object)this, (Object)obj, (String[])new String[0]);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode((Object)this, (String[])new String[0]);
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("id", (Object)this.id).append("name", (Object)this.name).append("status", (Object)this.status).toString();
    }

    @Override
    public int compareTo(MetaData o) {
        return this.getName().compareTo(o.getName());
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        SerializeUtils.writeNullSafeUTF(this.id, os);
        SerializeUtils.writeNullSafeUTF(this.name, os);
        os.writeInt(this.recordCount);
        SerializeUtils.writeNullSafeUTF(this.userKey1, os);
        if (this.status == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.status.name(), os);
        }
        os.writeObject(this.resource);
    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        Object object;
        this.id = SerializeUtils.readNullSafeUTF(is);
        this.name = SerializeUtils.readNullSafeUTF(is);
        this.recordCount = is.readInt();
        this.userKey1 = SerializeUtils.readNullSafeUTF(is);
        String value = SerializeUtils.readNullSafeUTF(is);
        if (value != null) {
            this.status = MetaDataStatus.valueOf(value);
        }
        if ((object = is.readObject()) != null) {
            this.resource = (JDBCResource)object;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public void setUserKey1(String userKey1) {
        this.userKey1 = userKey1;
    }

    public void setStatus(MetaDataStatus status) {
        this.status = status;
    }

    public void setResource(JDBCResource resource) {
        this.resource = resource;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public String getUserKey1() {
        return this.userKey1;
    }

    public MetaDataStatus getStatus() {
        return this.status;
    }

    public JDBCResource getResource() {
        return this.resource;
    }

    public MetaData() {
    }
}