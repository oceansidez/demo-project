package com.telecom.interceptor;

import com.telecom.annotation.CustomSecured;
import com.telecom.manage.entity.Admin;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect 
@Component
@Order(0) // 控制多个Aspect的执行顺序，越小越先执行
public class AuthAspect {

	@Around("@annotation(security)")
	public Object validate(ProceedingJoinPoint  point, CustomSecured security) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		
		// 判断admin是否存在
		Admin admin = Admin.getLoginAdmin(request.getSession());
		if(admin == null){
			response.sendRedirect("authError");
			return null;
		}
		
		String role = security.authorities()[0];
		// 判断是否包含权限
		// 若拥有权限则继续执行
		if(admin.getAuthorities().contains(new SimpleGrantedAuthority(role))){
			return point.proceed();
		}
		// 若没有权限，则重定向到错误页面
		else{
			response.sendRedirect("authError");
			return null;
		}
	}
}
