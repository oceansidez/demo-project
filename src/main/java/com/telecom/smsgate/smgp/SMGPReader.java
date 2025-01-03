package com.telecom.smsgate.smgp;

import com.telecom.smsgate.base.Message;
import com.telecom.smsgate.base.Reader;
import com.telecom.smsgate.smgp.message.SMGPBaseMessage;
import com.telecom.smsgate.smgp.message.SMGPConstants;
import com.telecom.smsgate.smgp.util.ByteUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 电信SMGP读取类
 */
public class SMGPReader implements Reader {

    protected DataInputStream in;

    public SMGPReader(InputStream is) {
        this.in = new DataInputStream(is);
    }

    public Message read() throws IOException {
        byte[] header = new byte[SMGPBaseMessage.SZ_HEADER];
        byte[] cmdBytes = null;
        try {
            readBytes(header, 0, SMGPBaseMessage.SZ_HEADER);
            int cmdLen = ByteUtil.byte2int(header, 0);

            if (cmdLen > 8096 || cmdLen < SMGPBaseMessage.SZ_HEADER) {
                throw new IOException("read stream error,cmdLen="+ cmdLen + ",close connection");
            }
            cmdBytes = new byte[cmdLen];
            System.arraycopy(header, 0, cmdBytes, 0, SMGPBaseMessage.SZ_HEADER);
            readBytes(cmdBytes, SMGPBaseMessage.SZ_HEADER, cmdLen - SMGPBaseMessage.SZ_HEADER);
        } catch (IOException e) {
            throw e;
        }

        try {
            SMGPBaseMessage baseMsg = SMGPConstants.fromBytes(cmdBytes);
            return baseMsg;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("build SMGPBaseMessage error:"+e.getMessage());
        }
    }

    private void readBytes(byte[] bytes,int offset, int len) throws IOException{
        in.readFully(bytes, offset,  len);
    }
}
