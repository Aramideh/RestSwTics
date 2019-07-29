//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.logging;

import java.util.StringTokenizer;

public abstract class Log {
    private int logLevel = 1;

    public Log() {
    }

    protected abstract void log(String var1);

    public void setLogLevel(int level) {
        this.logLevel = level;
        this.log(0, "set log level to #", level);
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void log(int level, String message) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[0]);
        }

    }

    public void log(int level, String message, boolean arg1) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{new Boolean(arg1)});
        }

    }

    public void log(int level, String message, int arg1) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{new Integer(arg1)});
        }

    }

    public void log(int level, String message, int arg1, int arg2) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{new Integer(arg1), new Integer(arg2)});
        }

    }

    public void log(int level, String message, int arg1, int arg2, int arg3) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{new Integer(arg1), new Integer(arg2), new Integer(arg3)});
        }

    }

    public void log(int level, String message, Object arg1) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{arg1});
        }

    }

    public void log(int level, String message, Object arg1, Object arg2) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{arg1, arg2});
        }

    }

    public void log(int level, String message, Object arg1, Object arg2, Object arg3) {
        if(level <= this.logLevel) {
            this.do_log(level, message, new Object[]{arg1, arg2, arg3});
        }

    }

    public void log(int level, String message, Object[] args) {
        if(level <= this.logLevel) {
            this.do_log(level, message, args);
        }

    }

    private void do_log(int level, String message, Object[] args) {
        String result = "";
        int i = 0;
        if(message == null) {
            message = "";
        }

        result = "Level " + level + ": ";
        StringTokenizer st = new StringTokenizer(message, "#", true);

        while(st.hasMoreTokens()) {
            String s = st.nextToken();
            if(s.equals("#")) {
                if(i < args.length) {
                    result = result + args[i++];
                } else {
                    result = result + "?";
                }
            } else {
                result = result + s;
            }
        }

        this.log(result);
    }
}
