package com.telecom.pay.wechat.pc;

import org.apache.commons.lang.StringUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BuildHtmlUtil {
	
	public static String buildCodeHtml(String qrCodeUrl, String returnUrl,
			String tradeNo, String name, String fee, String projectName, String path) throws Exception {
        StringBuffer sbHtml = new StringBuffer();
        Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("qrCodeUrl", URLEncoder.encode(qrCodeUrl, "UTF-8"));
		resultMap.put("returnUrl", URLEncoder.encode(returnUrl, "UTF-8"));
		resultMap.put("tradeNo", URLEncoder.encode(tradeNo, "UTF-8"));
		resultMap.put("name", URLEncoder.encode(name, "UTF-8"));
		resultMap.put("fee", URLEncoder.encode(fee, "UTF-8"));
		String param = PublicMd5Utils.createLinkString(resultMap);
		if(StringUtils.isNotEmpty(projectName)){
			sbHtml.append("<script>window.location.href='/"+ projectName + "/" + path  + "code_page.html?" + param +"';</script>");
	    }
		else{
			sbHtml.append("<script>window.location.href='/"+ path  + "code_page.html?" + param +"';</script>");
	    }
        return sbHtml.toString();
	}
   
	
	public static String buildErrorHtml(String errorMsg, String projectName, String path) throws Exception {
		StringBuffer sbHtml = new StringBuffer();
		if(StringUtils.isNotEmpty(projectName)){
			sbHtml.append("<script>window.location.href='/"+ projectName + "/" + path 
					  + "error_pc_page.html?errorMsg="+ URLEncoder.encode(errorMsg, "UTF-8") +"';</script>");
		}
		else{
			sbHtml.append("<script>window.location.href='/"+ path 
					  + "error_pc_page.html?errorMsg="+ URLEncoder.encode(errorMsg, "UTF-8") +"';</script>");
		}
		
	    return sbHtml.toString();
	}
}
