package com.telecom.security;

import com.telecom.config.SecurityConfig;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.Resource;
import com.telecom.manage.entity.Resource.AllowType;
import com.telecom.filter.BeforeCsrfFilter;
import com.telecom.manage.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import java.util.List;

/**
 * Spring Security配置
 *
 */

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private AdminDetailsService adminDetailsService;
	
	@Autowired
    private AdminAuthenticationProvider adminAuthenticationProvider;
	
	@Autowired
	public WebConfig webConfig;
	
	@Autowired
	public SecurityConfig securityConfig;
	
	@Bean
    public MyLoginSuccessHandler loginSuccessHandler(String url) {  
        return new MyLoginSuccessHandler(url);  
    }  
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(adminAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 放行资源
		List<Resource> allowList = resourceService.selectAllowResource(AllowType.permit.toString());
		if(allowList != null && allowList.size() > 0){
			for(Resource resource : allowList){
				http.authorizeRequests().antMatchers(resource.getPath()).permitAll();
			}
		}
		
		// 权限拦截
		List<Resource> interceptList = resourceService.selectAllowResource(AllowType.intercept.toString());
		if(interceptList != null && interceptList.size() > 0){
			for(Resource resource : interceptList){
				List<String> list = resource.getAuthorityList();
				String[] array = list.toArray(new String[list.size()]);
				http.authorizeRequests().antMatchers(resource.getPath()).hasAnyAuthority(array);
			}
		}
		
		// 其余路径均需通过登录认证
		http.authorizeRequests().anyRequest().authenticated();
		
		// X-Frame设置禁用，防止登录跳转空白
		http.headers().frameOptions().disable();
		
		// 登录处理
		http.formLogin()
		    .loginProcessingUrl(securityConfig.getLoginCheck()) // 登录处理
		    .successHandler(loginSuccessHandler(securityConfig.getDefaultSuccessUrl())) // 登录成功后的自定义处理
		    .loginPage(securityConfig.getLoginPage()).permitAll() // 登录页
		    .failureUrl(securityConfig.getFailureUrl()).permitAll() // 失败页
		    .usernameParameter(securityConfig.getUsername()) // 登录页username对应标签的name值
		    .passwordParameter(securityConfig.getPasswd()); // 登录页password对应标签的name值
		
		// 未在Security中放行的路径资源，必须优先于Security所有内部Filter发起Csrf拦截，否则存在Host请求头攻击漏洞
		// WebAsyncManagerIntegrationFilter理论上是Security最先执行的Filter
		http.addFilterBefore(new BeforeCsrfFilter(), WebAsyncManagerIntegrationFilter.class);
		
		// CSRF攻击防护禁用
		http.csrf().disable();
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 忽略资源
		List<Resource> ignoreList = resourceService.selectAllowResource(AllowType.ignore.toString());
		if(ignoreList != null && ignoreList.size() > 0){
			for(Resource resource : ignoreList){
				web.ignoring().antMatchers(resource.getPath());
			}
		}
		
	}
	
}