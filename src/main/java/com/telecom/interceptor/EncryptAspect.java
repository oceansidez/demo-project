package com.telecom.interceptor;

import com.telecom.bean.CommonConstant;
import com.telecom.config.WebConfig;
import com.telecom.util.AESUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect 
@Component
@Order(0) // 控制多个Aspect的执行顺序，越小越先执行
public class EncryptAspect {

	@Autowired
	WebConfig webConfig;
	
	@Around("execution(* com.telecom.controller.api..*.*(..))")
	public Object encrypt(ProceedingJoinPoint  point) throws Throwable {
		try {
			
			// 线上环境启用API加密
			if(CommonConstant.Environment.ONLINE.equals(webConfig.getEnvironment())){
				// 获取接口数据
				Object jsonData = point.proceed();
				String secretKey = webConfig.getApiEncryptSecretKey();
				String vector = webConfig.getApiEncryptVector();
				String encryptData = AESUtil.EncryptForBase64(jsonData.toString(), secretKey, vector);
				return encryptData;
			}
			else{
				return point.proceed();
			}
			
		} catch (Exception e) {
			
			// 此处自定义统一的加密错误结果
			e.printStackTrace();
			return "ERROR";
		}
	}
}
