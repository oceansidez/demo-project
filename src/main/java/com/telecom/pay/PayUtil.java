package com.telecom.pay;

import com.telecom.config.PayConfig;
import com.telecom.pay.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付核心工具类
 */
@Component
public class PayUtil {

	// 相关常量
	public final static String ALIPAY_PC = "alipayPC";
	public final static String WECHAT_PC = "wechatPC";
	public final static String BESTPAY_PC = "bestpayPC";
	public final static String ALIPAY_WAP = "alipayWap";
	public final static String WECHAT_WAP = "wechatWap";
	public final static String BESTPAY_WAP = "bestpayWap";
	
	@Autowired
	PayConfig payConfig;

	// 支付
	public String pay(PayBean bean, String payType) {
		String result = null;
		switch (payType) {
		case ALIPAY_PC:
			result = AlipayPC.pay(bean, payConfig.getAlipayPartner(),
					payConfig.getAlipayKey(), payConfig.getAlipaySeller());
			break;
		case WECHAT_PC:
			result = WechatPayPC.pay(bean, payConfig.getWechatAppId(),
					payConfig.getWechatPartner(),
					payConfig.getWechatPartnerKey(),
					payConfig.getProjectName(),
					payConfig.getWechatStaticPath());
			break;
		case BESTPAY_PC:
			result = BestpayPC.pay(bean, payConfig.getBestpayMerchantId(),
					payConfig.getBestpayKey());
			break;
		case ALIPAY_WAP:
			result = AlipayWap.pay(bean, payConfig.getAlipayPartner(),
					payConfig.getAlipayKey(), payConfig.getAlipaySeller());
			break;
		case WECHAT_WAP:
			result = WechatPayWap.pay(bean, payConfig.getWechatAppId(),
					payConfig.getWechatPartner(),
					payConfig.getWechatPartnerKey(),
					payConfig.getWechatQueryUrl(),
					payConfig.getDomain(),
					payConfig.getProjectName(),
					payConfig.getWechatStaticPath());
			break;
		case BESTPAY_WAP:
			result = BestpayWap.pay(bean, payConfig.getBestpayMerchantId(),
					payConfig.getBestpayKey());
			break;
		default:
			break;
		}
		return result;
	}

	// 同步跳转
	public PayResult returnResult(HttpServletRequest request, String payType) {
		switch (payType) {
		case ALIPAY_PC:
			return AlipayPC.returnResult(request);
		case WECHAT_PC:
			return WechatPayPC.returnResult(request);
		case BESTPAY_PC:
			return BestpayPC.returnResult(request);
		case ALIPAY_WAP:
			return AlipayWap.returnResult(request);
		case WECHAT_WAP:
			return WechatPayWap.returnResult(request);
		case BESTPAY_WAP:
			return BestpayWap.returnResult(request);
		default:
			return null;
		}
	}

	// 异步通知
	public PayResult notifyResult(HttpServletRequest request, String payType) {
		switch (payType) {
		case ALIPAY_PC:
			return AlipayPC.notifyResult(request, payConfig.getAlipayKey(),
					payConfig.getAlipayPartner());
		case WECHAT_PC:
			return WechatPayPC.notifyResult(request,
					payConfig.getWechatAppId(), payConfig.getWechatAppSecret(),
					payConfig.getWechatPartner(),
					payConfig.getWechatPartnerKey());
		case BESTPAY_PC:
			return BestpayPC.notifyResult(request, payConfig.getBestpayKey());
		case ALIPAY_WAP:
			return AlipayWap.notifyResult(request, payConfig.getAlipayKey(),
					payConfig.getAlipayPartner());
		case WECHAT_WAP:
			return WechatPayWap.notifyResult(request,
					payConfig.getWechatAppId(), payConfig.getWechatAppSecret(),
					payConfig.getWechatPartner(),
					payConfig.getWechatPartnerKey());
		case BESTPAY_WAP:
			return BestpayWap.notifyResult(request, payConfig.getBestpayKey());
		default:
			return null;
		}
	}

	// 异步通知响应给上游结果
	public String notifyResponse(boolean status, String payType) {
		String responseMsg = null;
		switch (payType) {
		case ALIPAY_PC:
			if (status) {
				responseMsg = "success";
			} else {
				responseMsg = "fail";
			}
			break;
		case WECHAT_PC:
			if (status) {
				StringBuffer buf = new StringBuffer();
				buf.append("<xml>");
				buf.append("<return_code>" + "<![CDATA[SUCCESS]]>"
						+ "</return_code>");
				buf.append("<return_msg>" + "<![CDATA[OK]]>" + "</return_msg>");
				buf.append("</xml>");
				responseMsg = buf.toString();
			} else {
				StringBuffer buf = new StringBuffer();
				buf.append("<xml>");
				buf.append("<return_code>" + "<![CDATA[FAIL]]>"
						+ "</return_code>");
				buf.append("<return_msg>" + "<![CDATA[FAIL]]>"
						+ "</return_msg>");
				buf.append("</xml>");
				responseMsg = buf.toString();
			}
			break;
		case BESTPAY_PC:
			if (status) {
				responseMsg = "SUCCESS";
			} else {
				responseMsg = "FAILURE";
			}
			break;
		case ALIPAY_WAP:
			if (status) {
				responseMsg = "success";
			} else {
				responseMsg = "fail";
			}
			break;
		case WECHAT_WAP:
			if (status) {
				StringBuffer buf = new StringBuffer();
				buf.append("<xml>");
				buf.append("<return_code>" + "<![CDATA[SUCCESS]]>"
						+ "</return_code>");
				buf.append("<return_msg>" + "<![CDATA[OK]]>" + "</return_msg>");
				buf.append("</xml>");
				responseMsg = buf.toString();
			} else {
				StringBuffer buf = new StringBuffer();
				buf.append("<xml>");
				buf.append("<return_code>" + "<![CDATA[FAIL]]>"
						+ "</return_code>");
				buf.append("<return_msg>" + "<![CDATA[FAIL]]>"
						+ "</return_msg>");
				buf.append("</xml>");
				responseMsg = buf.toString();
			}
			break;
		case BESTPAY_WAP:
			if (status) {
				responseMsg = "SUCCESS";
			} else {
				responseMsg = "FAILURE";
			}
			break;
		default:
			break;
		}
		return responseMsg;
	}

}
