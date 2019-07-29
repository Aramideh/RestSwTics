//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.acp;

import com.gesmallworld.core.acp.Acp;
import com.gesmallworld.core.acpt.AcptException;
import com.gesmallworld.core.acpt.AcptInputStream;
import com.gesmallworld.core.acpt.AcptOutputStream;
import com.gesmallworld.core.acpt.AcptType;

public abstract class AcptAcp extends Acp {
    private AcptInputStream acptInput;
    private AcptOutputStream acptOutput;

    protected AcptAcp(boolean autoStart) {
        super(autoStart);
    }

    protected AcptAcp() {
    }

    public Object getObject(AcptType type) throws AcptException {
        if(super.autoFlush) {
            this.flushInternal();
        }

        return this.acptInput.readObject(type);
    }

    public Object[] getObjectVector(AcptType type) throws AcptException {
        if(super.autoFlush) {
            this.flushInternal();
        }

        return this.acptInput.readObjectVector(type);
    }

    public void skipObject() throws AcptException {
        if(super.autoFlush) {
            this.flushInternal();
        }

        this.acptInput.skipObject();
    }

    public AcptType peekObject() throws AcptException {
        if(super.autoFlush) {
            this.flushInternal();
        }

        return this.acptInput.peekObject();
    }

    public void putObject(AcptType type, Object data) throws AcptException {
        this.acptOutput.writeObject(type, data);
    }

    public void putObjectVector(AcptType type, Object[] data) throws AcptException {
        this.acptOutput.writeObjectVector(type, data);
    }

    protected void initInstance() throws Exception {
        this.acptInput = new AcptInputStream(super.input);
        this.acptOutput = new AcptOutputStream(super.output);
        super.initInstance();
    }

    private void flushInternal() throws AcptException {
        this.acptOutput.flush();
    }
}
