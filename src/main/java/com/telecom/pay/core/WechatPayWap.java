package com.telecom.pay.core;

import com.telecom.pay.PayBean;
import com.telecom.pay.PayResult;
import com.telecom.pay.wechat.wap.*;
import com.telecom.util.DateOperateUtil;
import com.telecom.util.RedisUtil;
import com.telecom.util.SpringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 微信支付-Wap端（H5支付）
 * 
 */
public class WechatPayWap {
	private static final Log log = LogFactory.getLog(WechatPayWap.class);

	// 请求地址
	public static final String WX_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String WX_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	public static String pay(PayBean bean, String appId, String partner, String partnerKey, String queryUrl,
			String domain, String projectName, String path) {
		PrintWriter out = null;
		BufferedReader reader = null;
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String result = null;
		try {
			log.info("【微信支付】获取支付码开始");
			String appid = appId;
			String mch_id = partner;
			String nonce_str = create_nonce_str();
			String body = bean.getBody();
			String out_trade_no = bean.getTradeNo();
			String total_fee = changeY2F(bean.getFee());
			String notify_url = bean.getNotifyUrl();
			String sign = "";

			// 请求参数信息
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("appid", appid);
			paraMap.put("mch_id", mch_id);
			paraMap.put("nonce_str", nonce_str);
			paraMap.put("body", body);
			paraMap.put("out_trade_no", out_trade_no);
			paraMap.put("total_fee", total_fee);
			paraMap.put("spbill_create_ip", bean. getClientIp());
			paraMap.put("notify_url", notify_url);
			paraMap.put("trade_type", "MWEB");
			paraMap.put("product_id", bean.getTradeNo());
			JSONObject infoJson = new JSONObject();
			JSONObject h5_info = new JSONObject();
			h5_info.put("type", "Wap");
			h5_info.put("wap_url", domain);
			h5_info.put("wap_name", projectName);
			infoJson.put("h5_info", h5_info.toString());
			paraMap.put("scene_info", infoJson.toString());
			sign = PublicMd5Utils.buildMysign(paraMap, partnerKey);
			paraMap.put("sign", sign);

			log.info("【微信支付】组装请求参数成功");

			// 组装xml
			StringBuffer buf = new StringBuffer();
			buf.append("<xml>");
			buf.append("<appid>" + appid + "</appid>");
			buf.append("<mch_id>" + mch_id + "</mch_id>");
			buf.append("<nonce_str>" + nonce_str + "</nonce_str>");
			buf.append("<body>" + body + "</body>");
			buf.append("<out_trade_no>" + out_trade_no + "</out_trade_no>");
			buf.append("<total_fee>" + total_fee + "</total_fee>");
			buf.append("<spbill_create_ip>" + bean.getClientIp() + "</spbill_create_ip>");
			buf.append("<notify_url>" + notify_url + "</notify_url>");
			buf.append("<trade_type>" + "MWEB" + "</trade_type>");
			buf.append("<product_id>" + bean.getTradeNo() + "</product_id>");
			buf.append("<scene_info>" + infoJson.toString() + "</scene_info>");
			buf.append("<sign>" + sign + "</sign>");
			buf.append("</xml>");

			log.info("xml信息："+buf.toString());
			log.info("【微信支付】调用接口获取支付码");
			URL url = new URL(WX_ORDER_URL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(), "utf-8"));
			out.write(buf.toString());
			out.flush();
			httpConn.connect();

			log.info("【微信支付】开始获取响应信息");

			// 得到响应信息
			reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
			StringBuffer wxBuf = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				wxBuf.append(line);
			}

			// 关闭
			httpConn.disconnect();

			log.info("【微信支付】获取到服务器响应信息：" + wxBuf.toString());

			// 可能返回的报文信息
			String return_code = null;
			String return_msg = null;
			appid = null;
			mch_id = null;
			String device_info = null;
			nonce_str = null;
			sign = null;
			String result_code = null;
			String err_code = null;
			String err_code_des = null;
			String trade_type = null;
			String prepay_id = null;
			String mweb_url = null;

			// 解析报文
			XmlUtil xmlUtil = new XmlUtil();
			List<Map<String, Object>> list = xmlUtil.getList(wxBuf.toString());
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "return_code")) {
					if (map.get("value") != null) {
						return_code = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "return_msg")) {
					if (map.get("value") != null) {
						return_msg = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "appid")) {
					if (map.get("value") != null) {
						appid = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "mch_id")) {
					if (map.get("value") != null) {
						mch_id = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "device_info")) {
					if (map.get("value") != null) {
						device_info = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "nonce_str")) {
					if (map.get("value") != null) {
						nonce_str = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "sign")) {
					if (map.get("value") != null) {
						sign = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "result_code")) {
					if (map.get("value") != null) {
						result_code = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "err_code")) {
					if (map.get("value") != null) {
						err_code = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "err_code_des")) {
					if (map.get("value") != null) {
						err_code_des = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "prepay_id")) {
					if (map.get("value") != null) {
						prepay_id = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "mweb_url")) {
					if (map.get("value") != null) {
						mweb_url = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "trade_type")) {
					if (map.get("value") != null) {
						trade_type = map.get("value").toString();
					}
				}
			}

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("return_code", return_code);
			paramMap.put("return_msg", return_msg);
			paramMap.put("appid", appid);
			paramMap.put("mch_id", mch_id);
			paramMap.put("device_info", device_info);
			paramMap.put("nonce_str", nonce_str);
			paramMap.put("result_code", result_code);
			paramMap.put("err_code", err_code);
			paramMap.put("err_code_des", err_code_des);
			paramMap.put("trade_type", trade_type);
			paramMap.put("prepay_id", prepay_id);
			paramMap.put("mweb_url", mweb_url);
			String hmac = PublicMd5Utils.buildMysign(paramMap, partnerKey);
			if (StringUtils.equals(hmac, sign)) {
				// 签名相符
				log.info("【微信支付】微信下单成功");

				String timeStamp = System.currentTimeMillis() / 1000 + "";
				String nonce = create_nonce_str();
				// 产生签名
				paramMap = new HashMap<String, String>();
				paramMap.put("appId", appId);
				paramMap.put("timeStamp", timeStamp);
				paramMap.put("nonceStr", nonce);
				paramMap.put("package", "prepay_id=" + prepay_id);
				paramMap.put("signType", "MD5");
				hmac = PublicMd5Utils.buildMysign(paramMap, partnerKey);
				// 返回到页面
				rtnMap.put("status", "success");
				rtnMap.put("appId", appId);
				rtnMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
				rtnMap.put("nonceStr", nonce);
				rtnMap.put("package", "prepay_id=" + prepay_id);
				rtnMap.put("signType", "MD5");
				rtnMap.put("paySign", hmac);
				rtnMap.put("mweb_url", mweb_url);
				result = BuildHtmlUtil.buildMwebHtml(mweb_url, bean.getReturnUrl(),
						queryUrl + "?tradeNo=" + bean.getTradeNo(), domain, projectName, path);
				return result;
			} else {
				log.info("【微信支付】微信下单异常，签名不符");

				result = BuildHtmlUtil.buildErrorHtml("微信支付签名不符", domain, projectName, path);
				return result;
			}
		} catch (Exception e) {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					log.error("【微信支付】支付发生异常：" + e.getMessage());
				}
			}
			if (out != null) {
				out.flush();
				out.close();
			}

			try {
				result = BuildHtmlUtil.buildErrorHtml("微信支付发生异常！请联系系统管理员", domain, projectName, path);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return result;
		}
	}
	
	public static PayResult returnResult(HttpServletRequest request) {
		try {
			PayResult result = new PayResult();
			String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String tradeNo = new String(request.getParameter("transaction_id").getBytes("ISO-8859-1"),"UTF-8");
			String tradeStatus = new String(request.getParameter("trade_state").getBytes("ISO-8859-1"),"UTF-8");
			// 判断结果
			if(tradeStatus.equals("true")){
				result.setPayFlag(true);
			}
			else{
				result.setPayFlag(false);
			}
			
			result.setTradeNo(outTradeNo);
			result.setPaymentSn(tradeNo);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			PayResult result = new PayResult();
			result.setPayFlag(false);
			result.setDesc("微信WAP端处理异常");
			return result;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static PayResult notifyResult(HttpServletRequest request, String appId, String appSecret, String partner, String partnerKey) {
		try {
			// 网页授权后获取传递的参数
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			if (validate(partnerKey, sb.toString())) {
				PayResult payResult = new PayResult();
				
		        // 获取相关参数
		    	String appid = appId;
				String appsecret = appSecret;
				String mch_id = partner;
				String pkey = partnerKey;
		        
		        // 打印回调通知结果
		        log.info("通知结果: "+sb);
				Map result = GetWxOrderno.doXMLParse(sb.toString());
		    	String out_trade_no =(String) result.get("out_trade_no");
		    	String transaction_id =(String) result.get("transaction_id");
		    	String time_end =(String) result.get("time_end");
		    	String openid =(String) result.get("openid");
		    	String return_code =(String) result.get("return_code");
		    	String return_msg =(String) result.get("return_msg");
		    	String return_sign =(String) result.get("sign");
		    	log.info("支付成功回调: "+out_trade_no);
		    	log.info("return_code: "+return_code);
		    	log.info("return_msg: "+return_msg);
		    	log.info("return_sign: "+return_sign);
		    	
		    	// 调用查询接口查询订单，返回支付成功后进行数据库操作
		    	Map<String, Object> map = new HashMap<String, Object>();
				map = query(appid, appsecret, pkey, mch_id, out_trade_no);
				
				// 若交易结果为成功才进入执行，若不成功则直接跳过
				log.info("微信订单查询接口结果【trade_state】: "+ map.get("trade_state").toString());
				if (map.get("trade_state") != null && map.get("trade_state").toString().equals("SUCCESS")) {
					payResult.setPayFlag(true);
				}
				else {
					payResult.setPayFlag(false);
				}
				payResult.setTradeNo(out_trade_no);
				payResult.setPaymentSn(transaction_id);
				payResult.setPayTime(DateOperateUtil.stringToDateWithFormat(time_end, "yyyyMMddHHmmss"));
				payResult.setPayer(openid);
				
				RedisUtil redisUtil = (RedisUtil) SpringUtil.getBean("redisUtil");
				redisUtil.setosex(payResult.getTradeNo(), payResult, 300);
				
				return payResult;
			}
			else {
				PayResult result = new PayResult();
				result.setPayFlag(false);
				result.setDesc("微信WAP端Validate验证未通过");
				return result;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			PayResult result = new PayResult();
			result.setPayFlag(false);
			result.setDesc("微信WAP端处理异常");
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> query(String appid, String appsecret, String pkey, String mch_id, String out_trade_no) {
		Map<String, Object> map = new HashMap<String, Object>();
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String nonce_str = strTime + strRandom;
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, pkey);
		String sign = reqHandler.createSign(packageParams);
		String xmlParam = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign><![CDATA[" + sign
				+ "]]></sign>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" + "</xml>";
		map = GetWxOrderno.doXML(WX_QUERY_URL, xmlParam);
		return map;
	}
	
	/**
	 * 特别方法 queryForPage
	 * 提供于本系统的H5支付页面查询及后续跳转使用
	 * @return
	 */
	public static Map<String, Object> queryForPage(HttpServletRequest request) {
		RedisUtil redisUtil = (RedisUtil) SpringUtil.getBean("redisUtil");
		Map<String, Object> result = new HashMap<String, Object>();
		String tradeNo = request.getParameter("tradeNo");
		if(redisUtil.exists(tradeNo) && redisUtil.getos(tradeNo) != null){
			PayResult payResult = (PayResult) redisUtil.getos(tradeNo);
			if(payResult.getPayFlag()){
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("out_trade_no", payResult.getTradeNo());
				resultMap.put("transaction_id", payResult.getPaymentSn());
				resultMap.put("trade_state", payResult.getPayFlag().toString());
				String param = PublicMd5Utils.createLinkString(resultMap);
				result.put("param", param);
				result.put("status", true);
			}
			else {
				result.put("status", false);
			}
		}
		else {
			result.put("status", false);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean validate(String partnerKey, String strReader){
		try {
			// xml报文信息
			String xmlInfo = strReader;
			
			System.out.println("====================");
			System.out.println("微信支付WAP端validate验证:");
			System.out.println(xmlInfo);
			System.out.println("====================");

			// 可能返回的报文信息
			String return_code = null;
			String return_msg = null;
			String appid = null;
			String mch_id = null;
			String device_info = null;
			String nonce_str = null;
			String sign = null;
			String result_code = null;
			String err_code = null;
			String err_code_des = null;
			String openid = null;
			String is_subscribe = null;
			String trade_type = null;
			String bank_type = null;
			String total_fee = null;
			String fee_type = null;
			String cash_fee = null;
			String cash_fee_type = null;
			String coupon_fee = null;
			String coupon_count = null;
			String[] coupon_id_arr = null;
			String[] coupon_fee_arr = null;
			String transaction_id = null;
			String out_trade_no = null;
			String attach = null;
			String time_end = null;

			// 解析报文
			XmlUtil xmlUtil = new XmlUtil();
			List<Map<String, Object>> list = xmlUtil.getList(xmlInfo);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);

				if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "return_code")) {
					if (map.get("value") != null) {
						return_code = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "return_msg")) {
					if (map.get("value") != null) {
						return_msg = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "appid")) {
					if (map.get("value") != null) {
						appid = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "mch_id")) {
					if (map.get("value") != null) {
						mch_id = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "device_info")) {
					if (map.get("value") != null) {
						device_info = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "nonce_str")) {
					if (map.get("value") != null) {
						nonce_str = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "sign")) {
					if (map.get("value") != null) {
						sign = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "result_code")) {
					if (map.get("value") != null) {
						result_code = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "err_code")) {
					if (map.get("value") != null) {
						err_code = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "err_code_des")) {
					if (map.get("value") != null) {
						err_code_des = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "openid")) {
					if (map.get("value") != null) {
						openid = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "is_subscribe")) {
					if (map.get("value") != null) {
						is_subscribe = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "trade_type")) {
					if (map.get("value") != null) {
						trade_type = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "bank_type")) {
					if (map.get("value") != null) {
						bank_type = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "total_fee")) {
					if (map.get("value") != null) {
						total_fee = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "fee_type")) {
					if (map.get("value") != null) {
						fee_type = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "cash_fee")) {
					if (map.get("value") != null) {
						cash_fee = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "cash_fee_type")) {
					if (map.get("value") != null) {
						cash_fee_type = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "coupon_fee")) {
					if (map.get("value") != null) {
						coupon_fee = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "coupon_count")) {
					if (map.get("value") != null) {
						coupon_count = map.get("value").toString();
					}

					// 得到子信息
					if (StringUtils.isNotBlank(coupon_count)) {
						coupon_id_arr = new String[Integer.valueOf(coupon_count)];
						coupon_fee_arr = new String[Integer.valueOf(coupon_count)];
						for (int k = 0; k < Integer.valueOf(coupon_count); k++) {
							for (int j = 0; j < list.size(); j++) {
								Map tempMap = (Map) list.get(j);
								if (tempMap.get("key") != null && StringUtils.equals(tempMap.get("key").toString(), "coupon_id_" + k)) {
									if (map.get("value") != null) {
										coupon_id_arr[k] = map.get("value").toString();
									}
								} else if (tempMap.get("key") != null && StringUtils.equals(tempMap.get("key").toString(), "coupon_fee_" + k)) {
									if (map.get("value") != null) {
										coupon_fee_arr[k] = map.get("value").toString();
									}
								}
							}
						}
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "transaction_id")) {
					if (map.get("value") != null) {
						transaction_id = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "out_trade_no")) {
					if (map.get("value") != null) {
						out_trade_no = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "attach")) {
					if (map.get("value") != null) {
						attach = map.get("value").toString();
					}
				} else if (map.get("key") != null && StringUtils.equals(map.get("key").toString(), "time_end")) {
					if (map.get("value") != null) {
						time_end = map.get("value").toString();
					}
				}
			}
			// 先判断
			if (StringUtils.isNotBlank(return_code)) {
				if (StringUtils.equals(return_code, "SUCCESS")) {
					// 通信标志成功
					log.info("【微信通知】获取通信标志为成功，下面开始验证签名");

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("return_code", return_code);
					paramMap.put("return_msg", return_msg);
					paramMap.put("appid", appid);
					paramMap.put("mch_id", mch_id);
					paramMap.put("device_info", device_info);
					paramMap.put("nonce_str", nonce_str);
					paramMap.put("result_code", result_code);
					paramMap.put("err_code", err_code);
					paramMap.put("err_code_des", err_code_des);
					paramMap.put("openid", openid);
					paramMap.put("is_subscribe", is_subscribe);
					paramMap.put("trade_type", trade_type);
					paramMap.put("bank_type", bank_type);
					paramMap.put("total_fee", total_fee);
					paramMap.put("fee_type", fee_type);
					paramMap.put("cash_fee", cash_fee);
					paramMap.put("cash_fee_type", cash_fee_type);
					paramMap.put("coupon_fee", coupon_fee);
					paramMap.put("coupon_count", coupon_count);
					if(null != coupon_id_arr){
						for (int i = 0; i < coupon_id_arr.length; i++) {
							paramMap.put("coupon_id_" + i, coupon_id_arr[i]);
						}
					}
					if(null != coupon_fee_arr){
						for (int i = 0; i < coupon_fee_arr.length; i++) {
							paramMap.put("coupon_fee_" + i, coupon_fee_arr[i]);
						}
					}
					paramMap.put("transaction_id", transaction_id);
					paramMap.put("out_trade_no", out_trade_no);
					paramMap.put("attach", attach);
					paramMap.put("time_end", time_end);

					String hmac = PublicMd5Utils.buildMysign(paramMap, partnerKey);

					if (StringUtils.equals(hmac, sign)) {
						return true;
					} else {
						log.info("【微信通知】签名不相符");
						return false;
					}
				} else {
					// 通信标志失败
					if (return_msg == null) {
						log.info("【微信通知】获取通信标志为失败");
						return false;
					} else {
						log.info("【微信通知】获取通信标志为失败：" + return_msg);
						return false;
					}
				}
			} else {
				log.info("【微信通知】无法获取到微信支付结果信息");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static String create_nonce_str() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	
	private static String changeY2F(String fee) {
		float sessionmoney = Float.parseFloat(fee);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		int intMoney = Integer.parseInt(finalmoney);
		return String.valueOf(intMoney);
	}
}
