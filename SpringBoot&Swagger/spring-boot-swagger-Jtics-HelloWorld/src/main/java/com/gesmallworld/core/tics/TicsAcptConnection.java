//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.tics;

import com.gesmallworld.core.acpt.AcptException;
import com.gesmallworld.core.acpt.AcptInputStream;
import com.gesmallworld.core.acpt.AcptOutputStream;
import com.gesmallworld.core.acpt.AcptType;
import com.gesmallworld.core.tics.TicsConnection;
import com.gesmallworld.core.tics.TicsException;
import java.io.IOException;

public class TicsAcptConnection extends TicsConnection {
    private AcptInputStream acptInput;
    private AcptOutputStream acptOutput;

    public TicsAcptConnection() {
    }

    void connect() throws TicsException, IOException {
        super.connect();
        this.acptInput = new AcptInputStream(super.inStream);
        this.acptOutput = new AcptOutputStream(super.outStream);
    }

    public Object getObject(AcptType type) throws AcptException, IOException {
        if(super.autoFlush) {
            this.flush();
        }

        return this.acptInput.readObject(type);
    }

    public Object[] getObjectVector(AcptType type) throws AcptException, IOException {
        if(super.autoFlush) {
            this.flush();
        }

        return this.acptInput.readObjectVector(type);
    }

    public void skipObject() throws AcptException, IOException {
        if(super.autoFlush) {
            this.flush();
        }

        this.acptInput.skipObject();
    }

    public AcptType peekObject() throws AcptException, IOException {
        if(super.autoFlush) {
            this.flush();
        }

        return this.acptInput.peekObject();
    }

    public void putObject(AcptType type, Object data) throws AcptException {
        this.acptOutput.writeObject(type, data);
    }

    public void putObjectVector(AcptType type, Object[] data) throws AcptException {
        this.acptOutput.writeObjectVector(type, data);
    }
}
