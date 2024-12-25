package com.telecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	WebConfig webConfig;

	/**
	 * @Description: swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.pathMapping("/")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(webConfig.getSwaggerBasePackage()))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * @Description: 构建 api文档的信息
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact(
				webConfig.getSwaggerName(),
				webConfig.getSwaggerUrl(),
				webConfig.getSwaggerEmail());
		return new ApiInfoBuilder()
				// 设置页面标题
				.title(webConfig.getSwaggerTitle())
				// 设置联系人
				.contact(contact)
				// 描述
				.description(webConfig.getSwaggerDesc())
				// 定义版本号
				.version(webConfig.getSwaggerVersion())
				// 创建
				.build();
	}
	
}
