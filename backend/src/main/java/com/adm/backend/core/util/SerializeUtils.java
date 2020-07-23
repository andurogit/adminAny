package com.adm.backend.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
    public static final String NULL_LITERAL = "_admin_null";

    public static Object getDeserializedData(String data) throws IOException, ClassNotFoundException {
        byte[] bArr = new Base64().decode(data);
        ByteArrayInputStream arrIn = new ByteArrayInputStream(bArr);
        ObjectInputStream in = new ObjectInputStream(arrIn);
        Object object = in.readObject();
        in.close();
        return object;
    }

    public static String getSerializedData(Object data) throws IOException {
        ByteArrayOutputStream arrOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(arrOut);
        out.writeObject(data);
        return new Base64().encode(arrOut.toByteArray());
    }

    public static void writeNullSafeUTF(String str, ObjectOutputStream os) throws IOException {
        if (str == null) {
            os.writeUTF(NULL_LITERAL);
        } else {
            os.writeUTF(str);
        }
    }

    public static String readNullSafeUTF(ObjectInputStream in) throws IOException {
        String value = in.readUTF();
        if (NULL_LITERAL.equals(value)) {
            return null;
        }
        return value;
    }

    public static void writeNullSafeLongString(String str, ObjectOutputStream os) throws IOException {
        if (str == null) {
            os.writeInt(-1);
        } else {
            os.writeInt(str.length());
            os.writeChars(str);
        }
    }

    public static String readNullSafeLongString(ObjectInputStream in) throws IOException {
        int len = in.readInt();
        if (len < 0) {
            return null;
        }
        char[] chars = new char[len];
        for (int i = 0; i < len; ++i) {
            chars[i] = in.readChar();
        }
        return new String(chars);
    }

    public static void writeNullSafeBoolean(Boolean bool, ObjectOutputStream os) throws IOException {
        if (bool == null) {
            os.writeUTF(NULL_LITERAL);
        } else {
            os.writeUTF(bool.toString());
        }
    }

    public static Boolean readNullSafeBoolean(ObjectInputStream in) throws IOException {
        String value = in.readUTF();
        if (NULL_LITERAL.equals(value)) {
            return null;
        }
        return Boolean.valueOf(value);
    }

    private SerializeUtils() {
    }
}