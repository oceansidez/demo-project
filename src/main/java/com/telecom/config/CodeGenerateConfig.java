package com.telecom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 代码生成配置
 *
 */

@Component
@RefreshScope
@PropertySource(value = "classpath:/application-code.properties")
@ConfigurationProperties(prefix = "code-config")
public class CodeGenerateConfig {

	// DB——JDBC连接URL
	private String jdbcUrl;
	// DB——username用户名
	private String username;
	// DB——password密码
	private String password;
	// Table表名前缀
	private String tablePrefix;

	// 总包名
	private String packageName;
	// Controller包名
	private String controllerPackage;
	// Controller页面前缀
	private String controllerPrefix;
	// Entity包名
	private String entityPackage;
	// Service包名
	private String servicePackage;
	// ServiceImpl包名
	private String serviceImplPackage;
	// Mapper包名
	private String mapperPackage;

	// 代码生成路径
	private String createPath;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public String getControllerPrefix() {
		return controllerPrefix;
	}

	public void setControllerPrefix(String controllerPrefix) {
		this.controllerPrefix = controllerPrefix;
	}

	public String getCreatePath() {
		return createPath;
	}

	public void setCreatePath(String createPath) {
		this.createPath = createPath;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

}
