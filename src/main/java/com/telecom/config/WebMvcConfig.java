package com.telecom.config;

import com.telecom.interceptor.FileUploadInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Web Mvc配置
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	WebConfig webConfig;

	@Autowired
	FileUploadInterceptor fileUploadInterceptor;
	
	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(fileUploadInterceptor)
				.addPathPatterns("/**").excludePathPatterns("/login");
		super.addInterceptors(registry);
	}
	
	/**
	 * 配置默认页
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName(
				"forward:" + webConfig.getDefaultIndex());
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	/**
	 * 配置路径映射
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 静态资源映射
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");

		// Swagger2专属映射
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/resources/")
				.addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/public/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations(
				"classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations(
				"classpath:/META-INF/resources/webjars/");

		super.addResourceHandlers(registry);

	}
}