package com.adm.backend.core.model.resource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.adm.backend.core.util.SerializeUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JDBCResource extends AbstractResource {

    private static final long serialVersionUID = 4318749609671730597L;
    private DatabaseType databaseType;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private int initialSize = 3;
    private int maxActive = 10;
    private int maxIdle = 3;
    private int maxWait = 60000;
    private Boolean logAbandoned = false;
    private Boolean removeAbandoned = false;
    private int removeAbandonedTimeout = 300;
    private String validationQuery;

    @Override
    public final ResourceType getType() {
        return ResourceType.JDBC;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JDBCResource)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        JDBCResource resource = (JDBCResource)obj;
        return new EqualsBuilder().append((Object)this.getId(), (Object)resource.getId()).append((Object)this.getName(), (Object)resource.getName()).append((Object)this.url, (Object)resource.getUrl()).append((Object)this.databaseType, (Object)resource.getDatabaseType()).append((Object)this.driverClassName, (Object)resource.getDriverClassName()).append((Object)this.userName, (Object)resource.getUserName()).append((Object)this.password, (Object)resource.getPassword()).append(this.maxActive, resource.getMaxActive()).append(this.maxIdle, resource.getMaxIdle()).append(this.maxWait, resource.getMaxWait()).append((Object)this.logAbandoned, (Object)resource.getLogAbandoned()).append((Object)this.removeAbandoned, (Object)resource.getRemoveAbandoned()).append(this.removeAbandonedTimeout, resource.getRemoveAbandonedTimeout()).append(this.initialSize, resource.getInitialSize()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append((Object)this.getId()).append((Object)this.getName()).append((Object)this.url).append((Object)this.databaseType).append((Object)this.driverClassName).append((Object)this.userName).append((Object)this.password).append(this.maxActive).append(this.maxIdle).append(this.initialSize).append(this.maxWait).append((Object)this.logAbandoned).append((Object)this.removeAbandoned).append(this.removeAbandonedTimeout).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder((Object)this).append("id", (Object)this.getId()).append("name", (Object)this.getName()).append("databaseType", (Object)this.databaseType).append("url", (Object)this.url).toString();
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        SerializeUtils.writeNullSafeUTF(this.getId(), os);
        SerializeUtils.writeNullSafeUTF(this.getName(), os);
        if (this.databaseType == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.databaseType.name(), os);
        }
        SerializeUtils.writeNullSafeUTF(this.driverClassName, os);
        SerializeUtils.writeNullSafeUTF(this.url, os);
        SerializeUtils.writeNullSafeUTF(this.userName, os);
        SerializeUtils.writeNullSafeUTF(this.password, os);
        os.writeInt(this.initialSize);
        os.writeInt(this.maxActive);
        os.writeInt(this.maxIdle);
        os.writeInt(this.maxWait);
        SerializeUtils.writeNullSafeBoolean(this.logAbandoned, os);
        SerializeUtils.writeNullSafeBoolean(this.removeAbandoned, os);
        os.writeInt(this.removeAbandonedTimeout);
        SerializeUtils.writeNullSafeUTF(this.validationQuery, os);
    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        this.setId(SerializeUtils.readNullSafeUTF(is));
        this.setName(SerializeUtils.readNullSafeUTF(is));
        String value = SerializeUtils.readNullSafeUTF(is);
        if (value != null) {
            this.databaseType = DatabaseType.valueOf(value);
        }
        this.driverClassName = SerializeUtils.readNullSafeUTF(is);
        this.url = SerializeUtils.readNullSafeUTF(is);
        this.userName = SerializeUtils.readNullSafeUTF(is);
        this.password = SerializeUtils.readNullSafeUTF(is);
        this.initialSize = is.readInt();
        this.maxActive = is.readInt();
        this.maxIdle = is.readInt();
        this.maxWait = is.readInt();
        this.logAbandoned = SerializeUtils.readNullSafeBoolean(is);
        this.removeAbandoned = SerializeUtils.readNullSafeBoolean(is);
        this.removeAbandonedTimeout = is.readInt();
        this.validationQuery = SerializeUtils.readNullSafeUTF(is);
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public void setLogAbandoned(Boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public void setRemoveAbandoned(Boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public int getInitialSize() {
        return this.initialSize;
    }

    public int getMaxActive() {
        return this.maxActive;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public int getMaxWait() {
        return this.maxWait;
    }

    public Boolean getLogAbandoned() {
        return this.logAbandoned;
    }

    public Boolean getRemoveAbandoned() {
        return this.removeAbandoned;
    }

    public int getRemoveAbandonedTimeout() {
        return this.removeAbandonedTimeout;
    }

    public String getValidationQuery() {
        return this.validationQuery;
    }
    
}