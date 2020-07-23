package com.adm.backend.core.model.mart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.adm.backend.core.model.equation.ClauseType;
import com.adm.backend.core.model.equation.Criteria;
import com.adm.backend.core.model.metadata.ColumnType;
import com.adm.backend.core.model.metadata.TableColumn;
import com.adm.backend.core.util.SerializeUtils;

public class MartTableItem extends TableColumn {
    private static final long serialVersionUID = -8652420377529930975L;
    private MartTable parent;
    private String itemLink;
    private ItemType type = ItemType.GROUP_BY;
    private Criteria criteria = Criteria.EQUALS;
    private ClauseType clause = ClauseType.AND;
    private String conditionValue;
    private String expression;

    private void writeObject(ObjectOutputStream os) throws IOException {
        SerializeUtils.writeNullSafeUTF(this.getId(), os);
        SerializeUtils.writeNullSafeUTF(this.getName(), os);
        SerializeUtils.writeNullSafeUTF(this.getLocalizedName(), os);
        os.writeInt(this.getLength());
        SerializeUtils.writeNullSafeUTF(this.getDescription(), os);
        if (this.getDataType() == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.getDataType().name(), os);
        }
        os.writeObject(this.parent);
        SerializeUtils.writeNullSafeUTF(this.itemLink, os);
        if (this.type == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.type.name(), os);
        }
        if (this.criteria == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.criteria.name(), os);
        }
        if (this.clause == null) {
            SerializeUtils.writeNullSafeUTF(null, os);
        } else {
            SerializeUtils.writeNullSafeUTF(this.clause.name(), os);
        }
        SerializeUtils.writeNullSafeUTF(this.conditionValue, os);
        SerializeUtils.writeNullSafeUTF(this.expression, os);
    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        Object object;
        this.setId(SerializeUtils.readNullSafeUTF(is));
        this.setName(SerializeUtils.readNullSafeUTF(is));
        this.setLocalizedName(SerializeUtils.readNullSafeUTF(is));
        this.setLength(is.readInt());
        this.setDescription(SerializeUtils.readNullSafeUTF(is));
        String value = SerializeUtils.readNullSafeUTF(is);
        if (value != null) {
            this.setDataType(ColumnType.valueOf(value));
        }
        if ((object = is.readObject()) != null) {
            this.parent = (MartTable)object;
        }
        this.itemLink = SerializeUtils.readNullSafeUTF(is);
        value = SerializeUtils.readNullSafeUTF(is);
        if (value != null) {
            this.type = ItemType.valueOf(value);
        }
        if ((value = SerializeUtils.readNullSafeUTF(is)) != null) {
            this.criteria = Criteria.valueOf(value);
        }
        if ((value = SerializeUtils.readNullSafeUTF(is)) != null) {
            this.clause = ClauseType.valueOf(value);
        }
        this.conditionValue = SerializeUtils.readNullSafeUTF(is);
        this.expression = SerializeUtils.readNullSafeUTF(is);
    }

    public void setParent(MartTable parent) {
        this.parent = parent;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public void setClause(ClauseType clause) {
        this.clause = clause;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public MartTable getParent() {
        return this.parent;
    }

    public String getItemLink() {
        return this.itemLink;
    }

    public ItemType getType() {
        return this.type;
    }

    public Criteria getCriteria() {
        return this.criteria;
    }

    public ClauseType getClause() {
        return this.clause;
    }

    public String getConditionValue() {
        return this.conditionValue;
    }

    public String getExpression() {
        return this.expression;
    }

    @Override
    public String toString() {
        return "MartTableItem(parent=" + this.getParent() + ", itemLink=" + this.getItemLink() + ", type=" + (Object)((Object)this.getType()) + ", criteria=" + (Object)((Object)this.getCriteria()) + ", clause=" + (Object)((Object)this.getClause()) + ", conditionValue=" + this.getConditionValue() + ", expression=" + this.getExpression() + ")";
    }
}