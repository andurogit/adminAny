package com.adm.backend.core.model.metadata;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.adm.backend.core.model.object.ObjectDetail;
import com.adm.backend.core.util.SerializeUtils;

public class MetaDataItem extends TableColumn {
    private static final long serialVersionUID = 1404559008234341766L;
    private ObjectDetail codeLink;
    private MetaData parent;
    private int seq;

    public MetaDataItem clone() throws CloneNotSupportedException {
    	return (MetaDataItem) super.clone();
    }

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
        os.writeObject(this.codeLink);
        os.writeObject(this.parent);
        os.writeInt(this.seq);
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
            this.codeLink = (ObjectDetail)object;
        }
        if ((object = is.readObject()) != null) {
            this.parent = (MetaData)object;
        }
        this.seq = is.readInt();
    }

    public void setCodeLink(ObjectDetail codeLink) {
        this.codeLink = codeLink;
    }

    public void setParent(MetaData parent) {
        this.parent = parent;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public ObjectDetail getCodeLink() {
        return this.codeLink;
    }

    public MetaData getParent() {
        return this.parent;
    }

    public int getSeq() {
        return this.seq;
    }
}