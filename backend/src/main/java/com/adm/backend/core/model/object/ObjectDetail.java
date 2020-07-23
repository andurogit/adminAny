package com.adm.backend.core.model.object;

public class ObjectDetail extends AbstractCode {
    private static final long serialVersionUID = -6496238855439687275L;
    public static final String CODE_ORGANIZATION = "C001";
    public static final String CODE_CHANNEL = "C002";
    public static final String CODE_PRODUCT = "C003";
    private ObjectMaster objectMaster;
    private String userKey1 = "";
    private String stringInitValue = "";
    private int numberInitValue = 0;
    private String filePath = "";

    public void setObjectMaster(ObjectMaster objectMaster) {
        this.objectMaster = objectMaster;
    }

    public void setUserKey1(String userKey1) {
        this.userKey1 = userKey1;
    }

    public void setStringInitValue(String stringInitValue) {
        this.stringInitValue = stringInitValue;
    }

    public void setNumberInitValue(int numberInitValue) {
        this.numberInitValue = numberInitValue;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ObjectMaster getObjectMaster() {
        return this.objectMaster;
    }

    public String getUserKey1() {
        return this.userKey1;
    }

    public String getStringInitValue() {
        return this.stringInitValue;
    }

    public int getNumberInitValue() {
        return this.numberInitValue;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ObjectDetail)) {
            return false;
        }
        ObjectDetail other = (ObjectDetail)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ObjectMaster this$objectMaster = this.getObjectMaster();
        ObjectMaster other$objectMaster = other.getObjectMaster();
        if (this$objectMaster == null ? other$objectMaster != null : !((Object)this$objectMaster).equals(other$objectMaster)) {
            return false;
        }
        String this$userKey1 = this.getUserKey1();
        String other$userKey1 = other.getUserKey1();
        if (this$userKey1 == null ? other$userKey1 != null : !this$userKey1.equals(other$userKey1)) {
            return false;
        }
        String this$stringInitValue = this.getStringInitValue();
        String other$stringInitValue = other.getStringInitValue();
        if (this$stringInitValue == null ? other$stringInitValue != null : !this$stringInitValue.equals(other$stringInitValue)) {
            return false;
        }
        if (this.getNumberInitValue() != other.getNumberInitValue()) {
            return false;
        }
        String this$filePath = this.getFilePath();
        String other$filePath = other.getFilePath();
        return !(this$filePath == null ? other$filePath != null : !this$filePath.equals(other$filePath));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof ObjectDetail;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ObjectMaster $objectMaster = this.getObjectMaster();
        result = result * PRIME + ($objectMaster == null ? 43 : ((Object)$objectMaster).hashCode());
        String $userKey1 = this.getUserKey1();
        result = result * PRIME + ($userKey1 == null ? 43 : $userKey1.hashCode());
        String $stringInitValue = this.getStringInitValue();
        result = result * PRIME + ($stringInitValue == null ? 43 : $stringInitValue.hashCode());
        result = result * PRIME + this.getNumberInitValue();
        String $filePath = this.getFilePath();
        result = result * PRIME + ($filePath == null ? 43 : $filePath.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ObjectDetail(objectMaster=" + this.getObjectMaster() + ", userKey1=" + this.getUserKey1() + ", stringInitValue=" + this.getStringInitValue() + ", numberInitValue=" + this.getNumberInitValue() + ", filePath=" + this.getFilePath() + ")";
    }
}