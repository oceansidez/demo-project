package com.telecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Websocket配置
 *
 */

@Configuration
public class WebSocketConfig {

	@Autowired
	WebConfig webConfig;
	
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
		// Jar包启动SpringBoot，表示使用了内置servlet容器（tomcat）
		// 内置容器需要手动注入ServerEndpointExporter
		if("jar".equals(webConfig.getStartUpType())){
			return new ServerEndpointExporter();
		}
		// War包启动SpringBoot，表示一般会部署在独立的servlet容器（tomcat）
		// 外置容器会独立管理，无需注入
		else if("war".equals(webConfig.getStartUpType())){
			return null;
		}
		else return null;
    }
}