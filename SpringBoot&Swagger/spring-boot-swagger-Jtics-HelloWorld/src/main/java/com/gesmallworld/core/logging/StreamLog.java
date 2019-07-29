//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.logging;

import com.gesmallworld.core.logging.Log;
import java.io.OutputStream;
import java.io.PrintStream;

public class StreamLog extends Log {
    private int logLevel = 1;
    private int lastLevel = 0;
    private boolean lineStart = true;
    private PrintStream stream;

    public StreamLog(OutputStream out) {
        this.stream = new PrintStream(out);
    }

    public StreamLog(OutputStream out, boolean autoFlush) {
        this.stream = new PrintStream(out, autoFlush);
    }

    protected void logln() {
        this.stream.println();
    }

    protected void log(String x) {
        this.stream.print(x);
    }
}
