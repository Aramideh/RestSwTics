//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.acp;

import com.gesmallworld.core.io.MagikBinaryInputStream;
import com.gesmallworld.core.io.MagikBinaryOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Acp {
    protected MagikBinaryInputStream input;
    protected MagikBinaryOutputStream output;
    public boolean autoFlush;
    public int protocol;

    public boolean getBoolean() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readBoolean();
    }

    public byte getByte() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readByte();
    }

    public int getUByte() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readUnsignedByte();
    }

    public short getShort() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readShort();
    }

    public int getUShort() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readUnsignedShort();
    }

    public int getInt() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readInt();
    }

    public long getUInt() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readUnsignedInt();
    }

    public long getLong() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readLong();
    }

    public long getULong() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readLong();
    }

    public float getFloat() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readFloat();
    }

    public double getDouble() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readDouble();
    }

    public String getString() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readString();
    }

    public String getString8() throws IOException {
        if(this.autoFlush) {
            this.flush();
        }

        return this.input.readString8();
    }

    public byte[] getBytes(int n) throws IOException {
        byte[] result = new byte[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readByte();
        }

        return result;
    }

    public short[] getUBytes(int n) throws IOException {
        short[] result = new short[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = (short)this.input.readUnsignedByte();
        }

        return result;
    }

    public short[] getShorts(int n) throws IOException {
        short[] result = new short[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readShort();
        }

        return result;
    }

    public int[] getUShorts(int n) throws IOException {
        int[] result = new int[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readUnsignedShort();
        }

        return result;
    }

    public int[] getInts(int n) throws IOException {
        int[] result = new int[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readInt();
        }

        return result;
    }

    public long[] getUInts(int n) throws IOException {
        long[] result = new long[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readUnsignedInt();
        }

        return result;
    }

    public long[] getLongs(int n) throws IOException {
        long[] result = new long[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readLong();
        }

        return result;
    }

    public long[] getULongs(int n) throws IOException {
        long[] result = new long[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readLong();
        }

        return result;
    }

    public float[] getFloats(int n) throws IOException {
        float[] result = new float[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readFloat();
        }

        return result;
    }

    public double[] getDoubles(int n) throws IOException {
        double[] result = new double[n];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < n; ++i) {
            result[i] = this.input.readDouble();
        }

        return result;
    }

    public void putBoolean(boolean value) throws IOException {
        this.output.writeBoolean(value);
    }

    public void putByte(int value) throws IOException {
        this.output.write(value);
    }

    public void putUByte(int value) throws IOException {
        this.output.writeUnsignedByte(value);
    }

    public void putShort(int value) throws IOException {
        this.output.writeShort(value);
    }

    public void putUShort(int value) throws IOException {
        this.output.writeUnsignedShort(value);
    }

    public void putInt(int value) throws IOException {
        this.output.writeInt(value);
    }

    public void putUInt(long value) throws IOException {
        this.output.writeUnsignedInt(value);
    }

    public void putLong(long value) throws IOException {
        this.output.writeLong(value);
    }

    public void putULong(long value) throws IOException {
        this.output.writeLong(value);
    }

    public void putFloat(float value) throws IOException {
        this.output.writeFloat(value);
    }

    public void putDouble(double value) throws IOException {
        this.output.writeDouble(value);
    }

    public void putString(String value) throws IOException {
        this.output.writeString(value);
    }

    public void putString8(String value) throws IOException {
        this.output.writeString8(value);
    }

    public void putBytes(byte[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.write(value[i]);
        }

    }

    public void putUBytes(short[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeUnsignedByte(value[i]);
        }

    }

    public void putShorts(short[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeShort(value[i]);
        }

    }

    public void putUShorts(int[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeUnsignedShort(value[i]);
        }

    }

    public void putInts(int[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeInt(value[i]);
        }

    }

    public void putUInts(long[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeUnsignedInt(value[i]);
        }

    }

    public void putLongs(long[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeLong(value[i]);
        }

    }

    public void putULongs(long[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeLong(value[i]);
        }

    }

    public void putFloats(float[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeFloat(value[i]);
        }

    }

    public void putDoubles(double[] value) throws IOException {
        for(int i = 0; i < value.length; ++i) {
            this.output.writeDouble(value[i]);
        }

    }

    public void flush() throws IOException {
        this.output.flush();
    }

    protected void initInstance() throws Exception {
    }

    protected void resetChannels(InputStream input, OutputStream output) {
        this.input = new MagikBinaryInputStream(new BufferedInputStream(input));
        this.output = new MagikBinaryOutputStream(new BufferedOutputStream(output));
    }

    public boolean setAutoFlush(boolean flag) {
        boolean result = this.autoFlush;
        this.autoFlush = flag;
        return result;
    }

    protected int getMaxProtocol() {
        return 0;
    }

    protected int getMinProtocol() {
        return 0;
    }

    protected String getProgramId() {
        return "";
    }

    protected void verifyConnection(String name) throws Exception {
        boolean endSwap = false;
        int count = this.getUShort();
        if(count > 255) {
            count >>= 8;
            endSwap = true;
        }

        byte[] bytes = new byte[count];
        this.input.readFully(bytes);
        String got = new String(bytes);
        boolean failed = !got.equals(name);
        this.putByte(failed?1:(endSwap?2:0));
        this.flush();
        if(failed) {
            throw new Exception("Bad program id: wanted " + name + " got " + got);
        }
    }

    protected int establishProtocol(int min, int max) throws Exception {
        while(true) {
            int in = this.getUShort();
            if(in >= min && in <= max) {
                this.putBoolean(false);
                this.flush();
                return in;
            }

            this.putBoolean(true);
            this.putUShort(min);
            this.putUShort(max);
            this.flush();
        }
    }

    protected Acp() {
        this.input = new MagikBinaryInputStream(new BufferedInputStream(System.in));
        this.output = new MagikBinaryOutputStream(new BufferedOutputStream(System.out));
        this.autoFlush = true;
        this.connect();
    }

    protected Acp(boolean autoStart) {
        this.input = new MagikBinaryInputStream(new BufferedInputStream(System.in));
        this.output = new MagikBinaryOutputStream(new BufferedOutputStream(System.out));
        this.autoFlush = true;
        if(autoStart) {
            this.connect();
        }

    }

    protected void connect() {
        this.connect(this.getProgramId(), this.getMinProtocol(), this.getMaxProtocol());
    }

    protected void connect(String programId, int minProtocol, int maxProtocol) {
        try {
            this.initInstance();
            this.verifyConnection(programId);
            this.protocol = this.establishProtocol(minProtocol, maxProtocol);
        } catch (Throwable var5) {
            System.err.println("Error from ACP - disconnecting... " + var5.toString());
            System.exit(1);
        }

    }
}
