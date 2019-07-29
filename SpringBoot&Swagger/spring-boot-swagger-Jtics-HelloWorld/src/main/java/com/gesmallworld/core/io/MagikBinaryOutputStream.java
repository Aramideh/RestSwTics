//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MagikBinaryOutputStream extends DataOutputStream {
    public MagikBinaryOutputStream(OutputStream out) {
        super(out);
    }

    public void writeUnsignedByte(int value) throws IOException {
        this.write((byte)value);
    }

    public void writeUnsignedShort(int value) throws IOException {
        this.writeShort((short)value);
    }

    public void writeUnsignedInt(long value) throws IOException {
        this.writeInt((int)value);
    }

    public void writeString(String value) throws IOException {
        int count = value.length();
        this.writeUnsignedShort(count);

        for(int i = 0; i < count; ++i) {
            this.writeUnsignedShort(value.charAt(i));
        }

    }

    public void writeString8(String value) throws IOException {
        int count = value.length();
        this.writeUnsignedShort(count);
        this.write(value.getBytes());
    }
}
