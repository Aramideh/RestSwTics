//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.tics;

import com.gesmallworld.core.io.MagikBinaryInputStream;
import com.gesmallworld.core.io.MagikBinaryOutputStream;
import com.gesmallworld.core.logging.Log;
import com.gesmallworld.core.tics.TicsException;
import com.gesmallworld.core.utils.BufferUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TicsConnection {
    String host;
    int port;
    boolean autoFlush = false;
    int status = 8;
    boolean typeCheck;
    String dispatcherService = null;
    Socket socket;
    MagikBinaryInputStream inStream;
    MagikBinaryOutputStream outStream;
    int protocol;
    byte format;
    private int bufferSize;
    Log log;
    boolean logging = false;
    static final int MAX_BUFFER_SIZE = 16384;
    static final int IS_BIG_ENDIAN = 0;
    static final int IS_LITTLE_ENDIAN = 1;
    static final int IS_PDP_11_INTEGER = 2;
    static final int IS_IEEE_FLOAT = 0;
    static final int IS_VAX_FLOAT = 1;
    static final int IS_UNKNOWN = 99;
    static final int MIN_PROTOCOL = 0;
    static final int MAX_PROTOCOL = 1;
    static final int TICS_OK = 0;
    static final int TICS_NETWORK_DOWN = 1;
    static final int TICS_HOST_UNKNOWN = 2;
    static final int TICS_SERVICE_UNKNOWN = 3;
    static final int TICS_SERVICE_UNAVAILABLE = 4;
    static final int TICS_TRY_AGAIN = 5;
    static final int TICS_ACCESS_DENIED = 6;
    static final int TICS_PROTOCOL_MISMATCH = 7;
    static final int TICS_DISCONNECTED = 8;
    static final int TICS_NO_MEMORY = 9;
    static final int TICS_EXIT_SUCCESS = 10;
    static final int TICS_EXIT_FAILURE = 11;
    static final int TICS_BYTE = 0;
    static final int TICS_SHORT = 1;
    static final int TICS_USHORT = 2;
    static final int TICS_INT = 3;
    static final int TICS_UINT = 4;
    static final int TICS_SFLOAT = 5;
    static final int TICS_FLOAT = 6;
    static final int TICS_STRING = 7;
    static final int TICS_STRING16 = 8;
    static final int TICS_LONG = 9;
    static final int TICS_ULONG = 10;
    static final int TICS_MULTIPLE = 65280;
    static final int TICS_SHORTS = 65281;
    static final int TICS_USHORTS = 65282;
    static final int TICS_INTS = 65283;
    static final int TICS_UINTS = 65284;
    static final int TICS_SFLOATS = 65285;
    static final int TICS_FLOATS = 65286;
    static final int TICS_LONGS = 65287;
    static final int TICS_ULONGS = 65288;
    static final int TICS_USHORT_MAX = 32768;
    static final long TICS_UINT_MAX = -2147483648L;
    static final String DISPATCHER_NAME = "swdp";
    static final byte DISPATCHER_NAME_LENGTH = 4;
    static final String DISPATCHER_CLIENT_SERVICE = "swdp-client";
    static final int DISPATCHER_CLIENT_DEFAULT_PORT = 30001;
    static final String DISPATCHER_SERVER_SERVICE = "swdp-server";
    static final int DISPATCHER_SERVER_DEFAULT_PORT = 30002;
    static final int DISPATCHER_PROTOCOL = -128;
    static final int DISPATCHER_PRIORITY_DEFAULT = 0;
    static final int DISPATCHER_PRIORITY_LOW = 1;
    static final int DISPATCHER_PRIORITY_NORMAL = 2;
    static final int DISPATCHER_PRIORITY_HIGH = 3;
    static final byte DISPATCHER_REDIRECTION_OK = 0;
    static final byte DISPATCHER_REDIRECTION_FAILED = 1;
    static final int DISPATCHER_CONNECTION_OK = 0;
    static final int DISPATCHER_CONNECTION_REDIRECT = 1;
    static final int DISPATCHER_CONNECTION_NO_SERVICE = 2;
    static final int DISPATCHER_CONNECTION_PROTOCOL_ERROR = 3;
    static final int DISPATCHER_CONNECTION_ERROR = 4;

    public TicsConnection() {
    }

    public void setLog(Log aLog) {
        this.log = aLog;
        this.logging = this.log != null;
    }

    public Log getLog() {
        return this.log;
    }

    public int getProtocol() {
        return this.protocol;
    }

    public void setAutoFlush(boolean state) {
        this.autoFlush = state;
    }

    public boolean getAutoFlush() {
        return this.autoFlush;
    }

    public int getStatus() {
        return this.status;
    }

    public void connect(String remoteHost, String remotePort) throws TicsException, IOException {
        this.host = remoteHost;
        String portString;
        if(remotePort.indexOf(47) != -1) {
            portString = remotePort.substring(0, remotePort.indexOf(47));
            this.dispatcherService = remotePort.substring(remotePort.indexOf(47) + 1);
            if(this.logging) {
                this.log.log(1, "Tics.connect(): seeking dispatcher on host # on port # serving #", this.host, portString, this.dispatcherService);
            }
        } else {
            portString = remotePort;
        }

        try {
            this.port = (new Integer(portString)).intValue();
        } catch (NumberFormatException var5) {
            this.bailOut(var5, "bad remote port specified");
        }

        this.connect();
    }

    public void connect(String remoteHost, int remotePort) throws TicsException, IOException {
        this.host = remoteHost;
        this.port = remotePort;
        this.connect();
    }

    void connect() throws TicsException, IOException {
        if(this.status != 8) {
            this.bailOut("Already connected");
        }

        Object rawOutputStream = null;

        try {
            this.host = InetAddress.getByName(this.host).getHostAddress();
        } catch (UnknownHostException var9) {
            this.bailOut(var9, "could not resolve hostname: " + this.host);
        }

        try {
            this.socket = new Socket(this.host, this.port);
        } catch (Exception var8) {
            this.bailOut(var8, "could not connect to remote socket");
        }

        this.status = 0;

        try {
            this.socket.setTcpNoDelay(true);
        } catch (SocketException var7) {
            this.bailOut(var7, "could not disable Nagle algorithm for socket");
        }

        try {
            this.inStream = new MagikBinaryInputStream(new BufferedInputStream(this.socket.getInputStream()));
            this.outStream = new MagikBinaryOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
        } catch (IOException var6) {
            this.bailOut(var6, "could not get streams from socket");
        }

        if(this.dispatcherService != null) {
            this.handleDispatcher();
            if(this.status != 0) {
                this.bailOut("Tics.connect(): dispatcher not handled properly");
            }
        }

        this.protocol = this.inStream.readByte();
        this.format = this.inStream.readByte();
        boolean isBigEndian = (this.format & 1) == 0;
        if(this.protocol < 0 || this.protocol > 1) {
            this.status = 7;
        }

        byte len = this.inStream.readByte();
        ++len;
        byte[] data = this.getRawBytes(len);
        if(len > 1) {
            this.typeCheck = data[0] > 0;
        } else {
            this.typeCheck = false;
        }

        len = (byte)(data[len - 1] + 2);
        data = this.getRawBytes(len);
        this.bufferSize = BufferUtil.extract2(data, len - 2, isBigEndian) & '\uffff';
        if(this.bufferSize > 16384) {
            this.bufferSize = 16384;
        }

        if(this.status != 0) {
            this.bufferSize = 0;
        }

        this.outStream = new MagikBinaryOutputStream(new BufferedOutputStream(this.socket.getOutputStream(), this.bufferSize));
        this.bufferSize |= this.format << 15;
        this.putRawBytes(BufferUtil.deposit2(new byte[2], (short)(this.bufferSize | this.format << 15), 0, isBigEndian));
        this.flush();
        short flag = (short)this.inStream.readByte();
        if(flag != 0) {
            this.status = 6;
            this.disconnect();
            if(this.logging) {
                this.log.log(1, "status was not TICS_OK, disconnected.");
            }

        } else {
            this.autoFlush = true;
            if(this.logging) {
                this.log.log(1, "Tics.connect(): connection successful");
            }

        }
    }

    private void handleDispatcher() throws TicsException, IOException {
        byte[] expectedDispatcherInitial = new byte[]{-128, 4, 115, 119, 100, 112};
        byte[] dispatcherInitial = this.getRawBytes(expectedDispatcherInitial.length);
        if(!Arrays.equals(expectedDispatcherInitial, dispatcherInitial)) {
            this.bailOut("Tics.handleDispatcher(): bad dispatcherInitial");
        }

        this.sendDispatcherConnectMessage();
        byte[] buffer = this.getRawBytes(2);
        int msgLength = (buffer[0] & 255) << 8 | buffer[1] & 255;
        buffer = this.getRawBytes(msgLength - 2);
        switch(buffer[0]) {
            case 0:
                break;
            case 1:
                if(msgLength < 10) {
                    this.bailOut("Tics.handleDispatcher(): message too short for redirectio request");
                }

                int serverPort = (buffer[2] & 255) << 8 | buffer[3] & 255;
                String serverName = "" + (buffer[4] & 255) + "." + (buffer[5] & 255) + "." + (buffer[6] & 255) + "." + (buffer[7] & 255);
                if(this.logging) {
                    this.log.log(1, "Tics.handleDispatcher(): Server: # Port: #", serverName, new Integer(serverPort));
                }

                byte[] reply = new byte[]{0, 3, 0};
                Socket serverSocket = null;

                try {
                    serverSocket = new Socket(serverName, serverPort);
                } catch (IOException var11) {
                    reply[2] = 1;
                    this.putRawBytes(reply);
                    this.disconnect();
                    this.bailOut(var11, "Tics.handleDispatcher():could not connect to real remote socket");
                }

                reply[2] = 0;
                this.putRawBytes(reply);
                this.disconnect();
                this.socket = serverSocket;

                try {
                    this.inStream = new MagikBinaryInputStream(new BufferedInputStream(this.socket.getInputStream()));
                    this.outStream = new MagikBinaryOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                } catch (IOException var10) {
                    this.bailOut(var10, "could not get streams from socket");
                }

                this.status = 0;
                break;
            case 2:
                if(this.logging) {
                    this.log.log(1, "Tics.handleDispatcher(): requested service not registered - please check service name: #", this.dispatcherService);
                }

                this.disconnect();
                this.status = 6;
                break;
            case 3:
                if(this.logging) {
                    this.log.log(1, "Tics.handleDispatcher(): Dispatcher protocol error - max protocol is #", buffer[4]);
                }

                this.disconnect();
                this.status = 7;
                break;
            case 4:
                if(this.logging) {
                    this.log.log(1, "Tics.handleDispatcher(): Dispatcher has detected an error");
                }

                this.disconnect();
                this.status = 6;
                break;
            default:
                if(this.logging) {
                    this.log.log(1, "Tics.handleDispatcher(): Dispatcher has returned an unknown status.");
                }

                this.disconnect();
                this.status = 6;
        }

    }

    private void sendDispatcherConnectMessage() throws TicsException, IOException {
        byte[] byteService = this.dispatcherService.getBytes();
        if(byteService.length > 255) {
            this.bailOut("Tics.getDispatcherConnectMessage(): service name too long: " + this.dispatcherService);
        }

        int connectMessageLength = 12 + byteService.length;
        byte[] connectMsg = new byte[connectMessageLength];
        connectMsg[0] = 0;
        connectMsg[1] = (byte)((connectMessageLength & '\uff00') >> 8);
        connectMsg[2] = (byte)(connectMessageLength & 255);
        connectMsg[3] = 1;
        connectMsg[4] = 0;
        connectMsg[5] = 0;
        connectMsg[6] = 0;
        connectMsg[7] = 0;
        connectMsg[8] = 0;
        connectMsg[9] = 0;
        connectMsg[10] = 0;
        connectMsg[11] = (byte)byteService.length;

        for(int i = 0; i < byteService.length; ++i) {
            connectMsg[i + 12] = byteService[i];
        }

        this.putRawBytes(connectMsg);
        this.flush();
    }

    private void checkConnection() throws TicsException {
        switch(this.status) {
            case 1:
                this.bailOut(new TicsException("Network software on local host not working"));
            case 2:
                this.bailOut(new TicsException("Unrecognised server machine name"));
            case 9:
                this.bailOut(new TicsException("Not enough memory"));
            case 3:
                this.bailOut(new TicsException("Unrecognised service name, is SWCGI_TCP_PORT set?"));
            case 6:
                this.bailOut(new TicsException("Access to service denied"));
            case 4:
                this.bailOut(new TicsException("Service not running or unreachable"));
            case 5:
            case 7:
            case 8:
            default:
                this.bailOut(new TicsException("Unknown error"));
            case 0:
        }
    }

    public void disconnect() throws TicsException {
        try {
            this.flush();
            this.socket.close();
        } catch (IOException var2) {
            this.bailOut(var2, "disconnect: unable to close socket");
        }

        this.status = 8;
    }

    public int establishProtocol(int uMin, int uMax) throws TicsException, IOException {
        while(true) {
            int usProtocol = this.getUShort();
            if(this.logging) {
                this.log.log(3, "establishProtocol(short, short): Protocol server: #", usProtocol);
                this.log.log(3, "establishProtocol: Protocol client min and max: # #", uMin, uMax);
            }

            this.checkConnection();
            if(usProtocol >= uMin && usProtocol <= uMax) {
                this.putBoolean(true);
                this.flush();
                if(this.logging) {
                    this.log.log(1, "Established protocol");
                }

                return usProtocol;
            }

            this.putBoolean(false);
            this.putUShort(uMin);
            this.putUShort(uMax);
            if(this.logging) {
                this.log.log(1, "Rejected protocol");
            }

            this.flush();
        }
    }

    public boolean getBoolean() throws IOException, TicsException {
        this.housekeep(0);
        boolean result = this.inStream.readBoolean();
        if(this.logging) {
            this.log.log(2, "getBoolean: " + result);
        }

        return result;
    }

    public int getByte() throws IOException, TicsException {
        this.housekeep(0);
        int result = this.inStream.readUnsignedByte();
        if(this.logging) {
            this.log.log(2, "getByte: " + result);
        }

        return result;
    }

    public short getShort() throws IOException, TicsException {
        this.housekeep(1);
        short result = this.inStream.readShort();
        if(this.logging) {
            this.log.log(2, "getShort: " + result);
        }

        return result;
    }

    public int getUShort() throws IOException, TicsException {
        this.housekeep(2);
        int result = this.inStream.readUnsignedShort();
        if(this.logging) {
            this.log.log(2, "getUShort: " + result);
        }

        return result;
    }

    public int getInt() throws IOException, TicsException {
        this.housekeep(3);
        int result = this.inStream.readInt();
        if(this.logging) {
            this.log.log(2, "getInt: " + result);
        }

        return result;
    }

    public long getUInt() throws IOException, TicsException {
        this.housekeep(4);
        long result = this.inStream.readUnsignedInt();
        if(this.logging) {
            this.log.log(2, "getUInt: " + result);
        }

        return result;
    }

    public long getLong() throws IOException, TicsException {
        this.housekeep(9);
        long result = this.inStream.readLong();
        if(this.logging) {
            this.log.log(2, "getLong: " + result);
        }

        return result;
    }

    public long getULong() throws IOException, TicsException {
        this.housekeep(10);
        long result = this.inStream.readLong();
        if(this.logging) {
            this.log.log(2, "getULong: " + result);
        }

        return result;
    }

    public float getShortFloat() throws IOException, TicsException {
        this.housekeep(5);
        float result = this.inStream.readFloat();
        if(this.logging) {
            this.log.log(2, "getShortFloat: " + result);
        }

        return result;
    }

    public double getFloat() throws IOException, TicsException {
        this.housekeep(6);
        double result = this.inStream.readDouble();
        if(this.logging) {
            this.log.log(2, "getFloat: " + result);
        }

        return result;
    }

    public String getString16() throws IOException, TicsException {
        this.housekeep(8);
        String result = this.inStream.readString();
        if(this.logging) {
            this.log.log(2, "getString16: " + result);
        }

        return result;
    }

    public String getString() throws IOException, TicsException {
        this.housekeep(7);
        String result = this.inStream.readString8();
        if(this.logging) {
            this.log.log(2, "getString: " + result);
        }

        return result;
    }

    public short[] getBytes(int count) throws IOException, TicsException {
        short[] result = new short[count];
        if(this.autoFlush) {
            this.flush();
        }

        for(int i = 0; i < count; ++i) {
            result[i] = (short)this.inStream.readUnsignedByte();
        }

        if(this.logging) {
            this.log.log(2, "getBytes: count=", count);
        }

        return result;
    }

    public byte[] getRawBytes(int count) throws IOException, TicsException {
        byte[] result = new byte[count];
        if(this.autoFlush) {
            this.flush();
        }

        this.inStream.readFully(result);
        if(this.logging) {
            this.log.log(2, "getBytes: count=", count);
        }

        return result;
    }

    public short[] getShorts(int count) throws IOException, TicsException {
        short[] result = new short[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readShort();
        }

        if(this.logging) {
            this.log.log(2, "getShorts: count=", count);
        }

        return result;
    }

    public int[] getUShorts(int count) throws IOException, TicsException {
        int[] result = new int[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readUnsignedShort();
        }

        if(this.logging) {
            this.log.log(2, "getUShorts: count=", count);
        }

        return result;
    }

    public int[] getInts(int count) throws IOException, TicsException {
        int[] result = new int[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readInt();
        }

        if(this.logging) {
            this.log.log(2, "getInts: count=", count);
        }

        return result;
    }

    public long[] getUInts(int count) throws IOException, TicsException {
        long[] result = new long[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readUnsignedInt();
        }

        if(this.logging) {
            this.log.log(2, "getUInts: count=", count);
        }

        return result;
    }

    public long[] getLongs(int count) throws IOException, TicsException {
        long[] result = new long[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readLong();
        }

        if(this.logging) {
            this.log.log(2, "getLongs: count=", count);
        }

        return result;
    }

    public long[] getULongs(int count) throws IOException, TicsException {
        long[] result = new long[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readLong();
        }

        if(this.logging) {
            this.log.log(2, "getULongs: count=", count);
        }

        return result;
    }

    public float[] getShortFloats(int count) throws IOException, TicsException {
        float[] result = new float[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readFloat();
        }

        if(this.logging) {
            this.log.log(2, "getShortFloats: count=", count);
        }

        return result;
    }

    public double[] getFloats(int count) throws IOException, TicsException {
        double[] result = new double[count];
        this.housekeep(' ', count);

        for(int i = 0; i < count; ++i) {
            result[i] = this.inStream.readDouble();
        }

        if(this.logging) {
            this.log.log(2, "getFloats: count=", count);
        }

        return result;
    }

    private void housekeep(int type) throws IOException, TicsException {
        if(this.autoFlush) {
            this.flush();
        }

        if(this.typeCheck) {
            int read_type = this.inStream.readInt();
            if(read_type != type) {
                this.bailOut("Type mismatch - wanted type number " + type + " got type number " + read_type);
            }
        }

    }

    private void housekeep(int type, int count) throws IOException, TicsException {
        this.housekeep(type);
        if(this.typeCheck) {
            if((type & '\uff00') != 0) {
                int read_count = this.inStream.readInt();
                if(read_count != count) {
                    this.bailOut("Multiple read count mismatch - wanted " + count + " got " + read_count);
                }

            }
        }
    }

    public void putBoolean(boolean value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putBoolean: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(0);
        }

        this.outStream.writeBoolean(value);
    }

    public void putByte(int value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putByte: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(0);
        }

        this.outStream.writeUnsignedByte(value);
    }

    public void putShort(int value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putShort: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(1);
        }

        this.outStream.writeShort(value);
    }

    public void putUShort(int value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putUShort: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(2);
        }

        this.outStream.writeUnsignedShort(value);
    }

    public void putInt(int value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putInt: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(3);
        }

        this.outStream.writeInt(value);
    }

    public void putUInt(long value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putUInt: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(4);
        }

        this.outStream.writeUnsignedInt(value);
    }

    public void putLong(long value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putLong: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(9);
        }

        this.outStream.writeLong(value);
    }

    public void putULong(long value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putULong: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(10);
        }

        this.outStream.writeLong(value);
    }

    public void putShortFloat(float value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putShortFloat: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(5);
        }

        this.outStream.writeFloat(value);
    }

    public void putFloat(double value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putDouble: " + value);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(6);
        }

        this.outStream.writeDouble(value);
    }

    public void putString16(String value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putString16: " + value);
        }

        int count = value.length();
        if(this.typeCheck) {
            this.outStream.writeInt(8);
        }

        this.outStream.writeString(value);
    }

    public void putString(String value) throws IOException, TicsException {
        if(this.logging) {
            this.log.log(2, "putString: " + value);
        }

        int count = value.length();
        if(this.typeCheck) {
            this.outStream.writeInt(7);
        }

        this.outStream.writeString8(value);
    }

    public void putBytes(short[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putBytes: count=" + count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeUnsignedByte(values[i]);
        }

    }

    public void putRawBytes(byte[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putBytes: count=" + count);
        }

        this.outStream.write(values);
    }

    public void putShorts(short[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putShorts: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeShort(values[i]);
        }

    }

    public void putUShorts(int[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putUShorts: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeUnsignedShort(values[i]);
        }

    }

    public void putInts(int[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putInts: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeInt(values[i]);
        }

    }

    public void putUInts(long[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putUInts: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeUnsignedInt(values[i]);
        }

    }

    public void putLongs(long[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putLongs: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeLong(values[i]);
        }

    }

    public void putULongs(long[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putULongs: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeLong(values[i]);
        }

    }

    public void putShortFloats(float[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putShortFloats: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeFloat(values[i]);
        }

    }

    public void putFloats(double[] values) throws IOException, TicsException {
        int count = values.length;
        if(this.logging) {
            this.log.log(2, "putDoubles: count=" + count);
        }

        if(this.typeCheck) {
            this.outStream.writeInt(' ');
            this.outStream.writeInt(count);
        }

        for(int i = 0; i < count; ++i) {
            this.outStream.writeDouble(values[i]);
        }

    }

    public void flush() throws IOException {
        this.outStream.flush();
    }

    private void trace(String message) {
        if(this.logging) {
            this.log.log(1, "Tics Error: #! connect parameters: host #  port: #", message, this.host, new Integer(this.port));
        }

    }

    private void bailOut(String message) throws TicsException {
        if(this.logging) {
            this.log.log(0, "Creating Tics exception: " + message);
        }

        this.trace(message);
        TicsException e = new TicsException(message);
        throw e;
    }

    private void bailOut(TicsException e) throws TicsException {
        if(this.logging) {
            this.log.log(0, "Tics.bailOut(): General exception caught: #", e.getMessage());
            this.log.log(0, "Tics.bailOut(): Exception class         : #", this.getClass().getName());
        }

        this.trace(e.getMessage());
        throw e;
    }

    private void bailOut(Exception e, String message) throws TicsException {
        if(this.logging) {
            this.log.log(0, "General exception caught: #", e.getMessage());
        }

        this.trace(message);
        throw new TicsException(message);
    }
}
