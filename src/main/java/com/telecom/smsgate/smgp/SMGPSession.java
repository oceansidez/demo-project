package com.telecom.smsgate.smgp;

import com.telecom.smsgate.base.Message;
import com.telecom.smsgate.base.Session;
import com.telecom.smsgate.smgp.message.*;
import com.telecom.smsgate.smgp.tlv.TLVByte;
import com.telecom.smsgate.smgp.util.MD5;
import com.telecom.smsgate.smgp.util.SequenceGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 * 电信SMGP登陆会话
 */
public class SMGPSession implements Session {

    private static final Logger log = LogManager.getLogger(SMGPSession.class);

    private SMGPConnection connection;
    private String sessionId;
    private boolean authenticated;
    private Object lock = new Object();

    public SMGPSession(SMGPConnection connection, boolean authenticated){
        super();
        this.connection = connection;
        this.sessionId = java.util.UUID.randomUUID().toString();
        this.authenticated = authenticated;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void submit(String content, String spNumber, String userNumber){
        SMGPSubmitMessage submit = new SMGPSubmitMessage();
        submit.setSrcTermId(spNumber);
        submit.setDestTermIdArray(new String[] { userNumber });
        submit.setMsgFmt((byte) 8);

        byte[] bContent = null;
        try {
            bContent = content.getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {}

        // 一般短信
        if (bContent != null && bContent.length <= 140) {
            submit.setBMsgContent(bContent);
            submit.setMsgFmt((byte) 8);
            submit.setNeedReport((byte) 1);
            submit.setServiceId("");
            submit.setAtTime("");
            submit.setNeedReport((byte) 1);
            submit.setSequenceNumber(SequenceGenerator.nextSequence());
            send(submit);
        }
        // 长短信
        else {
			Vector<byte[]> splitContent = null;
			try {
				splitContent = splitContent(content.getBytes("iso-10646-ucs-2"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < splitContent.size(); i++) {
				byte tpUdhi = (byte)1;
				byte pkTotal = (byte)(i + 1);
				byte pkNumber = (byte)splitContent.size();
				
				SMGPSubmitMessage tmpSubmit = new SMGPSubmitMessage(
						new TLVByte(SMGPConstants.OPT_TP_UDHI, tpUdhi),
						new TLVByte(SMGPConstants.OPT_PK_TOTAL, pkTotal),
						new TLVByte(SMGPConstants.OPT_PK_NUMBER, pkNumber));
				tmpSubmit.setSrcTermId(spNumber);
				tmpSubmit.setDestTermIdArray(new String[] { userNumber });
				tmpSubmit.setMsgFmt((byte) 8);
				tmpSubmit.setBMsgContent(addContentHeader(splitContent.get(i),
						splitContent.size(), i + 1));
				tmpSubmit.setMsgFmt((byte) 8);
				tmpSubmit.setNeedReport((byte) 1);
				tmpSubmit.setServiceId("");
				tmpSubmit.setAtTime("");
				tmpSubmit.setNeedReport((byte) 1);
				tmpSubmit.setSequenceNumber(SequenceGenerator.nextSequence());
				send(tmpSubmit);

			}
		}
    }
    
    public void submit(String content, String spNumber, String userNumber, Integer sequenceNumber){
        SMGPSubmitMessage submit = new SMGPSubmitMessage();
        submit.setSrcTermId(spNumber);
        submit.setDestTermIdArray(new String[] { userNumber });
        submit.setMsgFmt((byte) 8);

        byte[] bContent = null;
        try {
            bContent = content.getBytes("iso-10646-ucs-2");
        } catch (UnsupportedEncodingException e) {}

        // 一般短信
        if (bContent != null && bContent.length <= 140) {
            submit.setBMsgContent(bContent);
            submit.setMsgFmt((byte) 8);
            submit.setNeedReport((byte) 1);
            submit.setServiceId("");
            submit.setAtTime("");
            submit.setNeedReport((byte) 1);
            submit.setSequenceNumber(sequenceNumber);
            send(submit);
        }
        // 长短信
        else {
        	Vector<byte[]> splitContent = null;
			try {
				splitContent = splitContent(content.getBytes("iso-10646-ucs-2"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < splitContent.size(); i++) {
				byte tpUdhi = (byte)1;
				byte pkTotal = (byte)(i + 1);
				byte pkNumber = (byte)splitContent.size();
				
				SMGPSubmitMessage tmpSubmit = new SMGPSubmitMessage(
						new TLVByte(SMGPConstants.OPT_TP_UDHI, tpUdhi),
						new TLVByte(SMGPConstants.OPT_PK_TOTAL, pkTotal),
						new TLVByte(SMGPConstants.OPT_PK_NUMBER, pkNumber));
				tmpSubmit.setSrcTermId(spNumber);
				tmpSubmit.setDestTermIdArray(new String[] { userNumber });
				tmpSubmit.setMsgFmt((byte) 8);
				tmpSubmit.setBMsgContent(addContentHeader(splitContent.get(i),
						splitContent.size(), i + 1));
				tmpSubmit.setSequenceNumber(sequenceNumber);
				send(tmpSubmit);

			}
		}
    }

    public void heartbeat(){
        if(isAuthenticated()) {
            SMGPActiveTestMessage activeTest=new SMGPActiveTestMessage();
            activeTest.setSequenceNumber(SequenceGenerator.nextSequence());
            send(activeTest);
        }
    }

    public boolean authenticate() {

        SMGPLoginMessage loginMsg=new SMGPLoginMessage();
        loginMsg.setClientId(connection.getClientId());
        loginMsg.setLoginMode(connection.getLoginMode());
        loginMsg.setVersion(connection.getVersion());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
        String tmp=dateFormat.format(calendar.getTime());
        loginMsg.setTimestamp(Integer.parseInt(tmp));
        loginMsg.setClientAuth(MD5.md5(connection.getClientId(), connection.getPassword(), tmp));
        loginMsg.setSequenceNumber(SequenceGenerator.nextSequence());
        send(loginMsg);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex){
                setAuthenticated(false);
            }
        }
        return isAuthenticated();
    }

    public void close() throws IOException {
        //保存数据
        if(isAuthenticated() ) {
            SMGPExitMessage exit = new SMGPExitMessage();
            exit.setSequenceNumber(SequenceGenerator.nextSequence());
            send(exit);
            synchronized (lock) {
                try {
                    lock.wait(6000);
                } catch (InterruptedException ex){
                    setAuthenticated(false);
                }
            }
        }
        connection.close();
    }

    public void send(Message message){
        connection.send(message);
    }
    
    public void process(Message message) throws IOException {
        if(message instanceof SMGPBaseMessage){
            SMGPBaseMessage baseMsg = (SMGPBaseMessage)message;
            if(isAuthenticated()){
                if (baseMsg instanceof SMGPActiveTestMessage) {
                    process((SMGPActiveTestMessage)baseMsg);
                } else if (baseMsg instanceof SMGPActiveTestRespMessage) {
                    // do nothing
                } else if(baseMsg instanceof SMGPExitRespMessage){
                    process((SMGPExitRespMessage)baseMsg);
                } else if(message instanceof SMGPSubmitRespMessage) {
                    process((SMGPSubmitRespMessage)message);
                } else if(message instanceof SMGPDeliverMessage) {
                    process((SMGPDeliverMessage)message);
                }
            } else if(baseMsg instanceof SMGPLoginRespMessage){
                process((SMGPLoginRespMessage)baseMsg);
            } else {
                throw new IOException("the first packet was not SMGPBindRespMessage:" + baseMsg);
            }
        }
    }

    private void process(SMGPActiveTestMessage msg) throws IOException {
        SMGPActiveTestRespMessage resp = new SMGPActiveTestRespMessage();
        resp.setSequenceNumber(msg.getSequenceNumber());
        send(resp);
    }

    private void process(SMGPLoginRespMessage rsp) throws IOException {
        synchronized (lock) {
            if(rsp.getStatus()==0){
                setAuthenticated(true);
                log.info("smgp login success host=" + connection.getHost() + ",port=" + connection.getPort() + ",clientId=" + connection.getClientId());
            } else {
                setAuthenticated(false);
                log.error("smgp login failure, host=" + connection.getHost() + ",port=" + connection.getPort() + ",clientId=" + connection.getClientId() + ",status=" + rsp.getStatus());
            }
            lock.notifyAll();
        }
    }

    private void process(SMGPExitRespMessage msg) throws IOException {
        synchronized (lock) {
            setAuthenticated(false);
            lock.notifyAll();
        }
        log.info("smgp exist success host=" + connection.getHost() + ",port=" + connection.getPort() + ",clientId=" + connection.getClientId());
    }

    private void process(SMGPSubmitRespMessage rsp) throws IOException {
        switch (rsp.getStatus())   {
            case 0:{   //发送成功

            } break;
            case 103:{  //平台流控,发送速度过快

            } break;
            default: break;
        }
    }

    private void process(SMGPDeliverMessage msg) throws IOException {
        if (msg.getIsReport() == 1) {
            //下行信息(平台送达报告)
            //SMGPDeliverMessage:[
            // sequenceNumber=0,
            // msgId=42010619162037055400,
            // isReport=1,
            // msgFmt=0,
            // recvTime=20140619162037,
            // srcTermId=18917768619,
            // destTermId=1065902100612,
            // msgLength=122,
            // msgContent={
            //  msgId=42010619162030053100,
            //  sub=001,dlvrd=001,
            //  subTime=1406191620,
            //  doneTime=1406191620,
            //  stat=DELIVRD,
            //  err=000,
            //  text=076?????̡��??�
            // }]
        	System.out.println("接受到送达信息");
            SMGPReportData report = msg.getReport();
        } else {
            //下行信息(用户回复短信)
            //SMGPDeliverMessage:[
            // sequenceNumber=0,
            // msgId=42010619162054061000,
            // isReport=0,
            // msgFmt=15,
            // recvTime=20140619162054,
            // srcTermId=18917768619,
            // destTermId=1065902100612,
            // msgLength=10,
            // msgContent=哈哈哈哈哈
            // ]
        	System.out.println("接受到用户回复");
        }
        System.out.println("可以处理点事儿");
        
        SMGPDeliverRespMessage rsp = new SMGPDeliverRespMessage();
        rsp.setSequenceNumber(msg.getSequenceNumber());
        rsp.setMsgId(msg.getMsgId());
        rsp.setStatus(0);
        send(rsp);
    }

    private void setAuthenticated(boolean value) {
        this.authenticated = value;
    }
    
    private static Vector<byte[]> splitContent(byte[] content) {
		ByteArrayInputStream buf = new ByteArrayInputStream(content);
		Vector<byte[]> tmpv = new Vector<byte[]>();

		int msgCount = (int) (content.length / (140 - 6) + 1);
		int LeftLen = content.length;
		for (int i = 0; i < msgCount; i++) {
			byte[] tmp = new byte[140 - 6];
			if (LeftLen < (140 - 6))
				tmp = new byte[LeftLen];
			try {
				buf.read(tmp);
				tmpv.add(tmp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			LeftLen = LeftLen - tmp.length;
		}
		return tmpv;
	}
    
    private static byte[] addContentHeader(byte[] content, int total, int num) {
		byte[] newcontent = new byte[content.length + 6];
		newcontent[0] = 0x05;
		newcontent[1] = 0x00;
		newcontent[2] = 0x03;
		newcontent[4] = (byte) total;
		newcontent[5] = (byte) num;
		System.arraycopy(content, 0, newcontent, 6, content.length);
		return newcontent;
	}
}

