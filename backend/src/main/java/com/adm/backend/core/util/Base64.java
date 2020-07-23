package com.adm.backend.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.zip.GZIPInputStream;

public class Base64 {
    protected static final boolean DEBUGGING = false;
    protected static final int IGNORE = -1;
    protected static final int PAD = -2;
    protected static char[] vc;
    protected static int[] cv;
    protected String lineSeparator = "\t";
    protected char[] valueToChar;
    protected char spec1 = (char)43;
    protected char spec2 = (char)47;
    protected char spec3 = (char)61;
    protected int[] charToValue;
    protected int lineLength = 0;

    public static void show(byte[] b) {
        for (byte aB : b) {
            System.out.print(Integer.toHexString(aB & 255) + " ");
        }
        System.out.println();
    }

    public Base64() {
        this.initTables();
    }

    public byte[] decode(String s) {
        byte[] b = new byte[s.length() / 4 * 3];
        int cycle = 0;
        int combined = 0;
        int j = 0;
        int len = s.length();
        int dummies = 0;
        block10 : for (int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            int value = c <= '\u00ff' ? this.charToValue[c] : -1;
            switch (value) {
                case -1: {
                    continue block10;
                }
                case -2: {
                    value = 0;
                    ++dummies;
                }
                default: {
                    switch (cycle) {
                        case 0: {
                            combined = value;
                            cycle = 1;
                            continue block10;
                        }
                        case 1: {
                            combined <<= 6;
                            combined |= value;
                            cycle = 2;
                            continue block10;
                        }
                        case 2: {
                            combined <<= 6;
                            combined |= value;
                            cycle = 3;
                            continue block10;
                        }
                        case 3: {
                            combined <<= 6;
                            b[j + 2] = (byte)(combined |= value);
                            b[j + 1] = (byte)(combined >>>= 8);
                            b[j] = (byte)(combined >>>= 8);
                            j += 3;
                            cycle = 0;
                        }
                    }
                }
            }
        }
        if (cycle != 0) {
            throw new ArrayIndexOutOfBoundsException("Input to decode not an even multiple of 4 characters; pad with " + this.spec3);
        }
        if (b.length != (j -= dummies)) {
            byte[] b2 = new byte[j];
            System.arraycopy(b, 0, b2, 0, j);
            b = b2;
        }
        return b;
    }

    public String encode(byte[] b) {
        int lines;
        int outputLength = (b.length + 2) / 3 * 4;
        if (this.lineLength != 0 && (lines = (outputLength + this.lineLength - 1) / this.lineLength - 1) > 0) {
            outputLength += lines * this.lineSeparator.length();
        }
        StringBuffer sb = new StringBuffer(outputLength);
        int linePos = 0;
        int len = b.length / 3 * 3;
        int leftover = b.length - len;
        for (int i = 0; i < len; i += 3) {
            if ((linePos += 4) > this.lineLength && this.lineLength != 0) {
                sb.append(this.lineSeparator);
            }
            int combined = b[i] & 255;
            combined <<= 8;
            combined |= b[i + 1] & 255;
            combined <<= 8;
            int c3 = (combined |= b[i + 2] & 255) & 63;
            int c2 = (combined >>>= 6) & 63;
            int c1 = (combined >>>= 6) & 63;
            int c0 = (combined >>>= 6) & 63;
            sb.append(this.valueToChar[c0]);
            sb.append(this.valueToChar[c1]);
            sb.append(this.valueToChar[c2]);
            sb.append(this.valueToChar[c3]);
        }
        switch (leftover) {
            default: {
                break;
            }
            case 1: {
                if ((linePos += 4) > this.lineLength && this.lineLength != 0) {
                    sb.append(this.lineSeparator);
                }
                sb.append(this.encode(new byte[]{b[len], 0, 0}), 0, 2);
                sb.append(this.spec3);
                sb.append(this.spec3);
                break;
            }
            case 2: {
                if ((linePos += 4) > this.lineLength && this.lineLength != 0) {
                    sb.append(this.lineSeparator);
                }
                sb.append(this.encode(new byte[]{b[len], b[len + 1], 0}), 0, 3);
                sb.append(this.spec3);
            }
        }
        if (outputLength != sb.length()) {
            System.out.println("oops: minor program flaw: output length mis-estimated");
            System.out.println("estimate:" + outputLength);
            System.out.println("actual:" + sb.length());
        }
        return sb.toString();
    }

    public final void setLineLength(int length) {
        this.lineLength = length / 4 * 4;
    }

    public final void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    private void initTables() {
        if (vc == null) {
            int i;
            vc = new char[64];
            cv = new int[256];
            for (i = 0; i <= 25; ++i) {
                Base64.vc[i] = (char)(65 + i);
            }
            for (i = 0; i <= 25; ++i) {
                Base64.vc[i + 26] = (char)(97 + i);
            }
            for (i = 0; i <= 9; ++i) {
                Base64.vc[i + 52] = (char)(48 + i);
            }
            Base64.vc[62] = this.spec1;
            Base64.vc[63] = this.spec2;
            for (i = 0; i < 256; ++i) {
                Base64.cv[i] = -1;
            }
            for (i = 0; i < 64; ++i) {
                Base64.cv[Base64.vc[i]] = i;
            }
            Base64.cv[this.spec3] = -2;
        }
        this.valueToChar = vc;
        this.charToValue = cv;
    }

    /*
     * Exception decompiling
     */
    public static String compress(String data) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static String decompress(String data) {
        if (data == null) {
            return "";
        }
        StringWriter writer = new StringWriter();
        String value = data;
        try {
            try (PrintWriter out = new PrintWriter(writer);
                 GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(new Base64().decode(data)));){
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                    } else {
                        out.println();
                    }
                    out.print(line);
                }
                out.flush();
                value = writer.toString();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }    
}