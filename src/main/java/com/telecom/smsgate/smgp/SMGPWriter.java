package com.telecom.smsgate.smgp;

import com.telecom.smsgate.base.Message;
import com.telecom.smsgate.base.Writer;
import com.telecom.smsgate.smgp.message.SMGPBaseMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 电信SMGP写入类
 */
public class SMGPWriter implements Writer {

    protected DataOutputStream out;

    public SMGPWriter(OutputStream os) {
        this.out = new DataOutputStream(os);
    }

    public void write(Message message) throws IOException {
       if(message instanceof SMGPBaseMessage){
            byte[] bytes = null;
            try {
                bytes = ((SMGPBaseMessage) message).toBytes();
            } catch (Exception ex){ }
            if(bytes != null){
                writeBytes(bytes);
            }
       }
    }

    private void writeBytes(byte[] bytes) throws IOException {
        out.write(bytes);
        out.flush();
    }
}
