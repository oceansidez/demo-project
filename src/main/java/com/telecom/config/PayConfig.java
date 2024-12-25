package com.telecom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 第三方支付配置
 *
 */
@Component
@RefreshScope
@PropertySource(value = "classpath:/application-payconfig.properties")
@ConfigurationProperties(prefix = "pay-config")
public class PayConfig {

	// 是否免费（使用0.01元支付）
	private Boolean isFree;
	// 网站域名
	private String domain;
	// 项目名，若配置nginx等导致映射路径改变可为空，依具体情况而定
	private String projectName;
	// 服务器IP
	private String serverIp;

	/* 支付宝参数 */
	private String alipayPartner;
	private String alipayKey;
	private String alipaySeller;

	/* 微信支付参数 */
	private String wechatAppId;
	private String wechatAppSecret;
	private String wechatPartner;
	private String wechatPartnerKey;

	// 微信支付静态页相对路径
	// 该路径真实存在于本系统，其中包含：扫码支付中间页、H5支付中间页、PC错误页、WAP错误页
	// PS: 路径请以"/"结尾
	private String wechatStaticPath;

	// 微信H5支付专用查询接口相对路径
	// 用于本地模拟同步通知的效果
	private String wechatQueryUrl;

	// 微信公众号支付获取openid接口地址
	private String wechatGetOpenIdUrl;

	/* 翼支付参数 */
	private String bestpayMerchantId;
	private String bestpayKey;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getAlipayPartner() {
		return alipayPartner;
	}

	public void setAlipayPartner(String alipayPartner) {
		this.alipayPartner = alipayPartner;
	}

	public String getAlipayKey() {
		return alipayKey;
	}

	public void setAlipayKey(String alipayKey) {
		this.alipayKey = alipayKey;
	}

	public String getAlipaySeller() {
		return alipaySeller;
	}

	public void setAlipaySeller(String alipaySeller) {
		this.alipaySeller = alipaySeller;
	}

	public String getWechatAppId() {
		return wechatAppId;
	}

	public void setWechatAppId(String wechatAppId) {
		this.wechatAppId = wechatAppId;
	}

	public String getWechatAppSecret() {
		return wechatAppSecret;
	}

	public void setWechatAppSecret(String wechatAppSecret) {
		this.wechatAppSecret = wechatAppSecret;
	}

	public String getWechatPartner() {
		return wechatPartner;
	}

	public void setWechatPartner(String wechatPartner) {
		this.wechatPartner = wechatPartner;
	}

	public String getWechatPartnerKey() {
		return wechatPartnerKey;
	}

	public void setWechatPartnerKey(String wechatPartnerKey) {
		this.wechatPartnerKey = wechatPartnerKey;
	}

	public String getWechatStaticPath() {
		return wechatStaticPath;
	}

	public void setWechatStaticPath(String wechatStaticPath) {
		this.wechatStaticPath = wechatStaticPath;
	}

	public String getWechatQueryUrl() {
		return wechatQueryUrl;
	}

	public void setWechatQueryUrl(String wechatQueryUrl) {
		this.wechatQueryUrl = wechatQueryUrl;
	}

	public String getWechatGetOpenIdUrl() {
		return wechatGetOpenIdUrl;
	}

	public void setWechatGetOpenIdUrl(String wechatGetOpenIdUrl) {
		this.wechatGetOpenIdUrl = wechatGetOpenIdUrl;
	}

	public String getBestpayMerchantId() {
		return bestpayMerchantId;
	}

	public void setBestpayMerchantId(String bestpayMerchantId) {
		this.bestpayMerchantId = bestpayMerchantId;
	}

	public String getBestpayKey() {
		return bestpayKey;
	}

	public void setBestpayKey(String bestpayKey) {
		this.bestpayKey = bestpayKey;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

}
