package com.adm.backend.core.model.mart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.adm.backend.core.model.DataModel;
import com.adm.backend.core.model.metadata.MetaData;
import com.adm.backend.core.util.SerializeUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * MartTable
 */
public class MartTable implements DataModel {

    /**
     *
     */

    private static final long serialVersionUID = -6476050144011583125L;
    private String id;
    private String name;
    private String userKey1;
    private MetaData metaData;
    private MartStatus status = MartStatus.PENDING;
    private int recordCount = 0;
    private String scheduleingSyntax;
    private Boolean clearYn;
    private Boolean scheduleingYn;

    public boolean equals(Object obj) {
        if (!(obj instanceof MartTable)) {
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
        return new ToStringBuilder((Object)this).append("id", (Object)this.id).append("userKey1", (Object)this.userKey1).append("name", (Object)this.name).append("status", (Object)this.status).toString();
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        SerializeUtils.writeNullSafeUTF(this.id, os);
        SerializeUtils.writeNullSafeUTF(this.name, os);
        SerializeUtils.writeNullSafeUTF(this.userKey1, os);
        os.writeObject(this.metaData);
        if (this.status == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.status.name(), os);
        }
        os.writeInt(this.recordCount);
        SerializeUtils.writeNullSafeUTF(this.scheduleingSyntax, os);
        SerializeUtils.writeNullSafeBoolean(this.clearYn, os);
        SerializeUtils.writeNullSafeBoolean(this.scheduleingYn, os);
    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        String value;
        this.id = SerializeUtils.readNullSafeUTF(is);
        this.name = SerializeUtils.readNullSafeUTF(is);
        this.userKey1 = SerializeUtils.readNullSafeUTF(is);
        Object object = is.readObject();
        if (object != null) {
            this.metaData = (MetaData)object;
        }
        if ((value = SerializeUtils.readNullSafeUTF(is)) != null) {
            this.status = MartStatus.valueOf(value);
        }
        this.recordCount = is.readInt();
        this.scheduleingSyntax = SerializeUtils.readNullSafeUTF(is);
        this.clearYn = SerializeUtils.readNullSafeBoolean(is);
        this.scheduleingYn = SerializeUtils.readNullSafeBoolean(is);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserKey1(String userKey1) {
        this.userKey1 = userKey1;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public void setStatus(MartStatus status) {
        this.status = status;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public void setScheduleingSyntax(String scheduleingSyntax) {
        this.scheduleingSyntax = scheduleingSyntax;
    }

    public void setClearYn(Boolean clearYn) {
        this.clearYn = clearYn;
    }

    public void setScheduleingYn(Boolean scheduleingYn) {
        this.scheduleingYn = scheduleingYn;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUserKey1() {
        return this.userKey1;
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    public MartStatus getStatus() {
        return this.status;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public String getScheduleingSyntax() {
        return this.scheduleingSyntax;
    }

    public Boolean getClearYn() {
        return this.clearYn;
    }

    public Boolean getScheduleingYn() {
        return this.scheduleingYn;
    }
}