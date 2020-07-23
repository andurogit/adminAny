package com.adm.backend.core.model.object;

public class ObjectMaster extends AbstractCode {
    private static final long serialVersionUID = 239078543203191674L;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectMaster)) {
            return false;
        }
        return super.equals(obj);
    }
}