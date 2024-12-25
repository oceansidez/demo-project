package com.telecom.pay.wechat.wap;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import java.net.URLEncoder;

public class BuildHtmlUtil {

	public static String buildMwebHtml(String mobileWebUrl, String returnUrl,
			String queryUrl, String domain, String projectName, String path)
			throws Exception {
		StringBuffer sbHtml = new StringBuffer();
		JSONObject resultMap = new JSONObject();
		resultMap.put("mobileWebUrl", mobileWebUrl);
		resultMap.put("returnUrl", returnUrl);
		resultMap.put("queryUrl", queryUrl);
		if (StringUtils.isNotEmpty(projectName)) {
			resultMap.put("redirectUrl", domain + "/" + projectName
					+ "/" + path + "mweb_page.html");
		} else {
			resultMap.put("redirectUrl", domain + "/"
					+ path + "mweb_page.html");
		}

		String param = URLEncoder.encode(
				new BASE64Encoder().encode(resultMap.toString().getBytes()),
				"UTF-8");

		if (StringUtils.isNotEmpty(projectName)) {
			sbHtml.append("<script>window.location.href='/" + projectName + "/"
					+ path
					+ "mweb_page.html?urlParam=" + param + "';</script>");
		} else {
			sbHtml.append("<script>window.location.href='/"
					+ path
					+ "mweb_page.html?urlParam=" + param + "';</script>");
		}
		return sbHtml.toString();
	}

	public static String buildErrorHtml(String errorMsg, String domain,
			String projectName, String path) throws Exception {
		StringBuffer sbHtml = new StringBuffer();
		if (StringUtils.isNotEmpty(projectName)) {
			sbHtml.append("<script>window.location.href='/" + projectName + "/"
					+ path
					+ "error_wap_page.html?errorMsg="
					+ URLEncoder.encode(errorMsg, "UTF-8") + "';</script>");
		} else {
			sbHtml.append("<script>window.location.href='/"
					+ path
					+ "error_wap_page.html?errorMsg="
					+ URLEncoder.encode(errorMsg, "UTF-8") + "';</script>");
		}

		return sbHtml.toString();
	}

}
