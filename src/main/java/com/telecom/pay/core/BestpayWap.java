package com.telecom.pay.core;

import com.telecom.pay.PayBean;
import com.telecom.pay.PayResult;
import com.telecom.pay.bestpay.wap.BestpaySubmit;
import com.telecom.pay.bestpay.wap.CryptTool;
import com.telecom.pay.bestpay.wap.OtherTool;
import com.telecom.util.DateOperateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BestpayWap {

	public static String pay(PayBean bean, String merchantId, String key) {
		String MERCHANTID = merchantId;//商户ID
		String SUBMERCHANTID = "";//商户子商户ID
		String ORDERSEQ = bean.getTradeNo();//订单号
		String ORDERREQTRANSEQ = OtherTool.getCurrentDate()+"000001";//订单请求交易流水号
		String ORDERDATE = OtherTool.getTodayDate2();//订单日期
		String PRODUCTAMOUNT = bean.getFee();//产品金额(元)
		String ATTACHAMOUNT = "0.00";//附加金额(元)
		String ORDERAMOUNT = bean.getFee();//订单总金额(元)
		String CURTYPE = "RMB";//币种
		String ENCODETYPE = "1";//加密方式
		String MERCHANTURL = bean.getReturnUrl();//交易返回的url地址（同步通知）
		String BACKMERCHANTURL = bean.getNotifyUrl();//交易后台返回的url地址（异步回调）
		String ATTACH = ORDERSEQ;//商家附加信息
		String BUSICODE = "0001";//业务类型
		String PRODUCTID = "";//业务标识
		String TMNUM = "";//终端号码
		String CUSTOMERID = "";//客户标识
		String PRODUCTDESC = bean.getBody();//产品描述
		
		// 生成签名
		String commkey = key;// 密钥
		String sourceStr="MERCHANTID="+MERCHANTID+"&ORDERSEQ="+ORDERSEQ+"&ORDERDATE="+ORDERDATE+"&ORDERAMOUNT="+ORDERAMOUNT+"&KEY="+commkey;
		String mac = "";
		try {
			mac = CryptTool.md5Digest(sourceStr);
		} catch (Exception e) {
			System.out.println("============翼支付WAP端支付生成签名异常===========");
			e.printStackTrace();
		}
		
		// 参数处理
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("MERCHANTID", MERCHANTID);
		parameterMap.put("SUBMERCHANTID", SUBMERCHANTID);
		parameterMap.put("ORDERSEQ", ORDERSEQ);
		parameterMap.put("ORDERREQTRANSEQ", ORDERREQTRANSEQ);
		parameterMap.put("ORDERDATE", ORDERDATE);
		parameterMap.put("PRODUCTAMOUNT", PRODUCTAMOUNT);
		parameterMap.put("ATTACHAMOUNT", ATTACHAMOUNT);
		parameterMap.put("ORDERAMOUNT", ORDERAMOUNT);
		parameterMap.put("CURTYPE", CURTYPE);
		parameterMap.put("ENCODETYPE", ENCODETYPE);
		parameterMap.put("MERCHANTURL", MERCHANTURL);
		parameterMap.put("BACKMERCHANTURL", BACKMERCHANTURL);
		parameterMap.put("ATTACH", ATTACH);
		parameterMap.put("BUSICODE", BUSICODE);
		parameterMap.put("PRODUCTID", PRODUCTID);
		parameterMap.put("TMNUM", TMNUM);
		parameterMap.put("CUSTOMERID", CUSTOMERID);
		parameterMap.put("PRODUCTDESC", PRODUCTDESC);
		parameterMap.put("mac", mac);
		
		// 建立请求
		String submitHtml = BestpaySubmit.buildRequest(parameterMap,"post","确认", key);
		return submitHtml;
	}
	
	public static PayResult returnResult(HttpServletRequest request) {
		try {
			PayResult result = new PayResult();
			NativeWebRequest webRequest = new ServletWebRequest(request);
			Map<String, String> map = (Map<String, String>) webRequest.getAttribute(
							HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
							RequestAttributes.SCOPE_REQUEST);
			result.setTradeNo(map.get("payNo"));
			result.setPayFlag(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			PayResult result = new PayResult();
			result.setPayFlag(false);
			result.setDesc("翼支付WAP端处理异常");
			return result;
		}
	}
	
	public static PayResult notifyResult(HttpServletRequest request, String key) {
		String nowTime = OtherTool.now();
		
		StringBuffer logInfoStringBuffer = new StringBuffer("WAP端翼支付异步通知: ");
		logInfoStringBuffer.append("当前时间：    " + nowTime + "\r\n");
		logInfoStringBuffer.append("支付订单号：    " + request.getParameter("ORDERSEQ") + "\r\n");
		logInfoStringBuffer.append("翼支付平台端交易流水号：    " + request.getParameter("UPTRANSEQ") + "\r\n");
		System.out.println(logInfoStringBuffer);
		
		String uptranSeq = request.getParameter("UPTRANSEQ");// 翼支付网关平台交易流水号
		String trandate = request.getParameter("TRANDATE");// 翼支付网关平台交易日期
		String retncode = request.getParameter("RETNCODE");// 处理结果码
		String retninfo = request.getParameter("RETNINFO");// 处理结果解释码
		String orderseq = request.getParameter("ORDERSEQ");// 订单号
		String orderamount = request.getParameter("ORDERAMOUNT");// 订单总金额
		String sign = request.getParameter("SIGN");// 签名密文
		String merchantId = request.getParameter("MERCHANTID");
		
		String commkey = key;
		String checkMac = "";
		String check = "UPTRANSEQ="+uptranSeq+"&MERCHANTID="+merchantId+"&ORDERSEQ="+orderseq+"&ORDERAMOUNT="+orderamount+"&RETNCODE="+retncode+"&RETNINFO="+retninfo+"&TRANDATE="+trandate+"&KEY="+commkey;
		
		// 校验
		try {
			checkMac = CryptTool.md5Digest(check);
			if (sign != null && sign.length() > 0) {
				if(sign.equals(checkMac)){
					// 验证回调返回状态是否成功
					PayResult result = new PayResult();
					String payTime = trandate;
					if(retncode.trim().equals("0000")){
						result.setPayFlag(true);
					}
					else{
						result.setPayFlag(false);
					}
					result.setTradeNo(orderseq);
					result.setPaymentSn(uptranSeq);
					if(StringUtils.isNotEmpty(payTime)){
						result.setPayTime(DateOperateUtil.stringToDateWithFormat(payTime, "yyyy-MM-dd HH:mm:ss"));
					}
					result.setPayer(null);
					return result;
				} 
				else {
					PayResult result = new PayResult();
					result.setPayFlag(false);
					result.setDesc("翼支付WAP端Validate验证未通过");
					return result;
				}
			}
			else {
				PayResult result = new PayResult();
				result.setPayFlag(false);
				result.setDesc("翼支付WAP端参数缺失sign");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			PayResult result = new PayResult();
			result.setPayFlag(false);
			result.setDesc("翼支付WAP端处理异常");
			return result;
		}
	}
	
}
