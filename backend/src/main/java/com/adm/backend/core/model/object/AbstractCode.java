package com.adm.backend.core.model.object;

import com.adm.backend.core.model.DataModel;

public abstract class AbstractCode implements DataModel {
    private static final long serialVersionUID = 4919234950112795418L;
    private String code;
    private String name;
    private Boolean enabled = true;

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public AbstractCode() {
    }

    public AbstractCode(String code, String name, Boolean enabled) {
        this.code = code;
        this.name = name;
        this.enabled = enabled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractCode)) {
            return false;
        }
        AbstractCode other = (AbstractCode)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$code = this.getCode();
        String other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Boolean this$enabled = this.getEnabled();
        Boolean other$enabled = other.getEnabled();
        return !(this$enabled == null ? other$enabled != null : !((Object)this$enabled).equals(other$enabled));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AbstractCode;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Boolean $enabled = this.getEnabled();
        result = result * 59 + ($enabled == null ? 43 : ((Object)$enabled).hashCode());
        return result;
    }

    public String toString() {
        return "AbstractCode(code=" + this.getCode() + ", name=" + this.getName() + ", enabled=" + this.getEnabled() + ")";
    }
}