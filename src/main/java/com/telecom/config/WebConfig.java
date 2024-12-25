package com.telecom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件 Web-Config
 *
 */

@Component
@RefreshScope
@PropertySource(value = "classpath:/application-myconfig.properties")
@ConfigurationProperties(prefix = "my-config")
public class WebConfig {

	// 环境配置
	private String environment;
	// SpringBoot启动形式
	private String startUpType;
	// 系统地址
	private String systemUrl;
	// 系统默认跳转
	private String defaultIndex;
	// Host地址
	private String host;
	// 管理后台项目名
	private String manageName;
	// 是否使用后台iframe多标签
	private Boolean isUseIframe;

	// CSRF防护放行路径
	private String csrfAllowPath;
	// XSS防护需要额外处理的富文本框Name列表
	private String xssRtfNameList;
	// 应用tld路径
	private String tldPath;
	// 应用tld
	private String tlds;
	// 文件上传允许类型
	private String fileUploadAllowTypes;
	// 文件上传允许后缀
	private String fileUploadAllowExtensions;
	// 文件上传路径
	private String uploadPath;
	// 文件展示路径
	private String viewPath;

	// 短信接口参数
	private String smsAkey;
	private String smsMemberId;
	private String smsKey;
	private String smsVector;
	private String smsSignatureSign;
	private String smsTemplateId;
	private String smsUrl;

	// FTP相关参数
	private String ftp;
	private String ftpHost;
	private Integer ftpPort;
	private String ftpUserName;
	private String ftpPassword;

	// Swagger相关参数
	private String swaggerBasePackage;
	private String swaggerName;
	private String swaggerUrl;
	private String swaggerEmail;
	private String swaggerTitle;
	private String swaggerDesc;
	private String swaggerVersion;

	// Api-Encrypt参数
	private String ApiEncryptSecretKey;
	private String ApiEncryptVector;

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getTldPath() {
		return tldPath;
	}

	public void setTldPath(String tldPath) {
		this.tldPath = tldPath;
	}

	public String getTlds() {
		return tlds;
	}

	public void setTlds(String tlds) {
		this.tlds = tlds;
	}

	public String getFileUploadAllowTypes() {
		return fileUploadAllowTypes;
	}

	public void setFileUploadAllowTypes(String fileUploadAllowTypes) {
		this.fileUploadAllowTypes = fileUploadAllowTypes;
	}

	public String getFileUploadAllowExtensions() {
		return fileUploadAllowExtensions;
	}

	public void setFileUploadAllowExtensions(String fileUploadAllowExtensions) {
		this.fileUploadAllowExtensions = fileUploadAllowExtensions;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}

	public String getDefaultIndex() {
		return defaultIndex;
	}

	public void setDefaultIndex(String defaultIndex) {
		this.defaultIndex = defaultIndex;
	}

	public String getManageName() {
		return manageName;
	}

	public void setManageName(String manageName) {
		this.manageName = manageName;
	}

	public Boolean getIsUseIframe() {
		return isUseIframe;
	}

	public void setIsUseIframe(Boolean isUseIframe) {
		this.isUseIframe = isUseIframe;
	}

	public String getStartUpType() {
		return startUpType;
	}

	public void setStartUpType(String startUpType) {
		this.startUpType = startUpType;
	}

	public String getSmsAkey() {
		return smsAkey;
	}

	public void setSmsAkey(String smsAkey) {
		this.smsAkey = smsAkey;
	}

	public String getSmsMemberId() {
		return smsMemberId;
	}

	public void setSmsMemberId(String smsMemberId) {
		this.smsMemberId = smsMemberId;
	}

	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	public String getSmsVector() {
		return smsVector;
	}

	public void setSmsVector(String smsVector) {
		this.smsVector = smsVector;
	}

	public String getSmsSignatureSign() {
		return smsSignatureSign;
	}

	public void setSmsSignatureSign(String smsSignatureSign) {
		this.smsSignatureSign = smsSignatureSign;
	}

	public String getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getFtp() {
		return ftp;
	}

	public void setFtp(String ftp) {
		this.ftp = ftp;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getSwaggerBasePackage() {
		return swaggerBasePackage;
	}

	public void setSwaggerBasePackage(String swaggerBasePackage) {
		this.swaggerBasePackage = swaggerBasePackage;
	}

	public String getSwaggerName() {
		return swaggerName;
	}

	public void setSwaggerName(String swaggerName) {
		this.swaggerName = swaggerName;
	}

	public String getSwaggerUrl() {
		return swaggerUrl;
	}

	public void setSwaggerUrl(String swaggerUrl) {
		this.swaggerUrl = swaggerUrl;
	}

	public String getSwaggerEmail() {
		return swaggerEmail;
	}

	public void setSwaggerEmail(String swaggerEmail) {
		this.swaggerEmail = swaggerEmail;
	}

	public String getSwaggerTitle() {
		return swaggerTitle;
	}

	public void setSwaggerTitle(String swaggerTitle) {
		this.swaggerTitle = swaggerTitle;
	}

	public String getSwaggerDesc() {
		return swaggerDesc;
	}

	public void setSwaggerDesc(String swaggerDesc) {
		this.swaggerDesc = swaggerDesc;
	}

	public String getSwaggerVersion() {
		return swaggerVersion;
	}

	public void setSwaggerVersion(String swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
	}

	public String getCsrfAllowPath() {
		return csrfAllowPath;
	}

	public void setCsrfAllowPath(String csrfAllowPath) {
		this.csrfAllowPath = csrfAllowPath;
	}

	public String getXssRtfNameList() {
		return xssRtfNameList;
	}

	public void setXssRtfNameList(String xssRtfNameList) {
		this.xssRtfNameList = xssRtfNameList;
	}

	public String getApiEncryptSecretKey() {
		return ApiEncryptSecretKey;
	}

	public void setApiEncryptSecretKey(String apiEncryptSecretKey) {
		ApiEncryptSecretKey = apiEncryptSecretKey;
	}

	public String getApiEncryptVector() {
		return ApiEncryptVector;
	}

	public void setApiEncryptVector(String apiEncryptVector) {
		ApiEncryptVector = apiEncryptVector;
	}

}
