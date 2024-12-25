package com.telecom.pay;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付通知结果
 *
 */
public class PayResult implements Serializable {

	private static final long serialVersionUID = -436416873734189750L;
	
	private String tradeNo; // 商家流水号
	private String paymentSn; // 接口提供方唯一交易标识
	private String payer; // 付款人（支付宝账户/微信openid）
	private Date payTime; // 支付时间
	private Boolean payFlag; // 支付是否成功
	private String desc; // 交易描述

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPaymentSn() {
		return paymentSn;
	}

	public void setPaymentSn(String paymentSn) {
		this.paymentSn = paymentSn;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Boolean getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(Boolean payFlag) {
		this.payFlag = payFlag;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
