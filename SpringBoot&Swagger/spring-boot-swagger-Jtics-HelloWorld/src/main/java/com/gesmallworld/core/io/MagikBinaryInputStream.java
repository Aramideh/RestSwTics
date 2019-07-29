//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MagikBinaryInputStream extends DataInputStream {
    public MagikBinaryInputStream(InputStream in) {
        super(in);
    }

    public long readUnsignedInt() throws IOException {
        long l = (long)this.readInt();
        return l & 4294967295L;
    }

    public String readString() throws IOException {
        int count = this.readUnsignedShort();
        char[] charBuffer = new char[count];

        for(int i = 0; i < count; ++i) {
            charBuffer[i] = (char)this.readUnsignedShort();
        }

        return new String(charBuffer);
    }

    public String readString8() throws IOException {
        int count = this.readUnsignedShort();
        byte[] vec = new byte[count];
        this.readFully(vec);
        return new String(vec);
    }
}
