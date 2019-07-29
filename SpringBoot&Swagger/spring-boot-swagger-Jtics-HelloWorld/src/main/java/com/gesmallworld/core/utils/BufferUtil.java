//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.utils;

public class BufferUtil {
    public BufferUtil() {
    }

    public static byte extract1(byte[] buffer, int start, boolean bigEndian) {
        return buffer[start];
    }

    public static short extract2(byte[] buffer, int start, boolean bigEndian) {
        short value = 0;
        int var10000;
        int p;
        if(bigEndian) {
            for(p = start; p < start + 2; value = (short)(value << 8 | (short)buffer[p++] & 255)) {
                ;
            }
        } else {
            for(p = start + 2; p > start; value = (short)(var10000 | (short)buffer[p] & 255)) {
                var10000 = value << 8;
                --p;
            }
        }

        return value;
    }

    public static int extract4(byte[] buffer, int start, boolean bigEndian) {
        int value = 0;
        int var10000;
        int p;
        if(bigEndian) {
            for(p = start; p < start + 4; value = value << 8 | (short)buffer[p++] & 255) {
                ;
            }
        } else {
            for(p = start + 4; p > start; value = var10000 | (short)buffer[p] & 255) {
                var10000 = value << 8;
                --p;
            }
        }

        return value;
    }

    public static long extract8(byte[] buffer, int start, boolean bigEndian) {
        long value = 0L;
        long var10000;
        int p;
        if(bigEndian) {
            for(p = start; p < start + 8; value = value << 8 | (long)((short)buffer[p++] & 255)) {
                ;
            }
        } else {
            for(p = start + 8; p > start; value = var10000 | (long)((short)buffer[p] & 255)) {
                var10000 = value << 8;
                --p;
            }
        }

        return value;
    }

    public static String extractString(byte[] buffer, int start, int length, boolean bigEndian) {
        char[] charBuffer = new char[length];
        int i;
        if(bigEndian) {
            for(i = 0; i < length; start += 2) {
                charBuffer[i] = (char)(((short)buffer[start] & 255) << 8 | (short)buffer[start + 1] & 255);
                ++i;
            }
        } else {
            for(i = 0; i < length; start += 2) {
                charBuffer[i] = (char)(((short)buffer[start + 1] & 255) << 8 | (short)buffer[start] & 255);
                ++i;
            }
        }

        return new String(charBuffer);
    }

    public static byte[] deposit1(byte[] buffer, byte value, int start, boolean bigEndian) {
        buffer[start] = value;
        return buffer;
    }

    public static byte[] deposit2(byte[] buffer, short value, int start, boolean bigEndian) {
        int p;
        if(bigEndian) {
            for(p = start + 2; p > start; value = (short)(value >> 8)) {
                --p;
                buffer[p] = (byte)(value & 255);
            }
        } else {
            for(p = start; p < start + 2; value = (short)(value >> 8)) {
                buffer[p++] = (byte)(value & 255);
            }
        }

        return buffer;
    }

    public static byte[] deposit4(byte[] buffer, int value, int start, boolean bigEndian) {
        int p;
        if(bigEndian) {
            for(p = start + 4; p > start; value >>= 8) {
                --p;
                buffer[p] = (byte)(value & 255);
            }
        } else {
            for(p = start; p < start + 4; value >>= 8) {
                buffer[p++] = (byte)(value & 255);
            }
        }

        return buffer;
    }

    public static byte[] deposit8(byte[] buffer, long value, int start, boolean bigEndian) {
        int p;
        if(bigEndian) {
            for(p = start + 8; p > start; value >>= 8) {
                --p;
                buffer[p] = (byte)((int)(value & 255L));
            }
        } else {
            for(p = start; p < start + 8; value >>= 8) {
                buffer[p++] = (byte)((int)(value & 255L));
            }
        }

        return buffer;
    }

    public static byte[] depositString(byte[] buffer, String s, int start, boolean bigEndian) {
        int len = s.length();
        char[] charBuffer = new char[len];
        s.getChars(0, len, charBuffer, 0);
        int i;
        if(bigEndian) {
            for(i = 0; i < len; ++i) {
                buffer[start++] = (byte)(charBuffer[i] >> 8 & 255);
                buffer[start++] = (byte)(charBuffer[i] & 255);
            }
        } else {
            for(i = 0; i < len; ++i) {
                buffer[start++] = (byte)(charBuffer[i] & 255);
                buffer[start++] = (byte)(charBuffer[i] >> 8 & 255);
            }
        }

        return buffer;
    }
}
