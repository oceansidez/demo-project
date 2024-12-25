package com.telecom.util;

import com.telecom.config.WebConfig;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
@Resource(name="sendSmsUtil")
public class SendSmsUtil {

	@Autowired	
	private WebConfig webConfig;

    public Map<String , Object> sendSms(String mobile, String param) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject aesJson = new JSONObject();
        aesJson.put("member_id", webConfig.getSmsMemberId());
        aesJson.put("sms_template_sign", webConfig.getSmsTemplateId());
        aesJson.put("sms_signature_sign", webConfig.getSmsSignatureSign());
        aesJson.put("params", param);
        aesJson.put("mobile", mobile);

        String data = AESUtil.Encrypt(aesJson.toString(), webConfig.getSmsKey(), webConfig.getSmsVector());
        JSONObject rsaJson = new JSONObject();
        rsaJson.put("key", webConfig.getSmsKey());
        rsaJson.put("vector", webConfig.getSmsVector());
        rsaJson.put("member_id", webConfig.getSmsMemberId());

        // 公钥加密
        byte[] encWord = RSAUtil.encryptByPublicKey(rsaJson.toString().getBytes(), webConfig.getSmsAkey());
        JSONObject json = new JSONObject();
        json.put("data", data);
        json.put("code", RSAUtil.encodeBytes(encWord));
        String result = HttpClientUtil.sendPostInfo(webConfig.getSmsUrl(), json.toString(), "UTF-8");
        if (result != null) {
            map = JsonUtil.parseJSON2Map(result);
        }
        return map;
    }
    
}
