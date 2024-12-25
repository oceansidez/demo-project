package com.telecom.smsgate.base;

import com.telecom.smsgate.smgp.SMGPConnection;

public class SmgpTester {
	
	public static void main(String[] args) {
        SMGPConnection conn = new SMGPConnection();
        
        // SMGP配置可写入配置文件
        conn.setClientId("zzgaoyh");
        conn.setPassword("hbtel_20210512!");
        conn.setVersion((byte) 0);
        conn.setAutoReconnect(true);
        conn.setSendInterval(200);
        String spid = "1065926779";

        conn.connect("127.0.0.1", 8891);

        if(conn.isConnected()){
            Session session = conn.getSession();


            try {
                for(int i = 0; i < 1; i++) {
                    String content = "尊敬的纳税人，近期涉税优惠：1网领发票继续免费邮寄。2小微扶持政策继续实行：社保费减免、增值税优惠延到年底；所得税缓到明年；个体户社保费可享受单位缴费减免和缓缴。3纳税服务满意度调查继续进行：如您收到短信，请您积极参与，告知我们哈哈哈哈哈哈哈哈。【东西湖区税务局】";
                    session.submit(content, spid, "15972200949", 1234);
                }
            }
            finally {
//                try {
//                    session.close();
//                    conn.close();
//                }
//                catch (Exception ex){
//                	ex.printStackTrace();
//                }
            }
        }
    }
    
}
