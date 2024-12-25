package com.telecom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Spring Security配置文件
 *
 */
@Component
@RefreshScope
@PropertySource(value = "classpath:/application-mysecurity.properties")
@ConfigurationProperties(prefix = "my-security")//一次性获取多个前缀为"my-security"值
public class SecurityConfig {

	// 登录页
	private String loginPage;
	// 登录鉴权处理（与登录页Form Action属性保持一致）
	private String loginCheck;
	// 默认成功跳转
	private String defaultSuccessUrl;
	// 默认失败跳转
	private String failureUrl;
	// 登录页username对应标签的name值
	private String username;
	// 登录页password对应标签的name值
	private String passwd;

	// 是否开启登录失败【x】次自动锁定，【y】分钟后自动解除锁定
	private Boolean isAutoLock;
	// 登录失败次数触发锁定（即【x】的值）
	private Integer loginFailureCount;
	// 账号锁定后解除时间（即【y】的值）
	private Integer unlockTime;
	// 是否开启短信验证登录代替账号密码登录（短信接口默认，若要使用其他接口请自行修改）
	private Boolean isUseAuthCode;
	// 是否手机登录验证码处理的模糊提示（无论发送结果如何，统一提示【正在处理】）
	private Boolean isUseAuthCodeVagueHint;
	// 手机验证码登录记录IP及手机号次数过期时间
	private Integer loginExpireTime;
	// 在过期时间内IP及手机号尝试次数限制
	private Integer loginTryLimit;

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getLoginCheck() {
		return loginCheck;
	}

	public void setLoginCheck(String loginCheck) {
		this.loginCheck = loginCheck;
	}

	public String getDefaultSuccessUrl() {
		return defaultSuccessUrl;
	}

	public void setDefaultSuccessUrl(String defaultSuccessUrl) {
		this.defaultSuccessUrl = defaultSuccessUrl;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Boolean getIsAutoLock() {
		return isAutoLock;
	}

	public void setIsAutoLock(Boolean isAutoLock) {
		this.isAutoLock = isAutoLock;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Integer getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Integer unlockTime) {
		this.unlockTime = unlockTime;
	}

	public Boolean getIsUseAuthCode() {
		return isUseAuthCode;
	}

	public void setIsUseAuthCode(Boolean isUseAuthCode) {
		this.isUseAuthCode = isUseAuthCode;
	}

	public Integer getLoginExpireTime() {
		return loginExpireTime;
	}

	public void setLoginExpireTime(Integer loginExpireTime) {
		this.loginExpireTime = loginExpireTime;
	}

	public Integer getLoginTryLimit() {
		return loginTryLimit;
	}

	public void setLoginTryLimit(Integer loginTryLimit) {
		this.loginTryLimit = loginTryLimit;
	}

	public Boolean getIsUseAuthCodeVagueHint() {
		return isUseAuthCodeVagueHint;
	}

	public void setIsUseAuthCodeVagueHint(Boolean isUseAuthCodeVagueHint) {
		this.isUseAuthCodeVagueHint = isUseAuthCodeVagueHint;
	}

}
