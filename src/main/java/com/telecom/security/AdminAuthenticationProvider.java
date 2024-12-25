package com.telecom.security;

import com.telecom.bean.CommonConstant;
import com.telecom.config.SecurityConfig;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.service.AdminService;
import com.telecom.manage.service.AuthCodeService;
import com.telecom.util.AESUtil;
import com.telecom.util.DateOperateUtil;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminAuthenticationProvider.class);

	// AES加密填充变量
	// 时间作为AES加密向量时位数不足，需填充3位
	private final static String IV_PACK = "000";
	
	@Autowired
	AdminDetailsService adminDetailsService;

	@Autowired
	AdminService adminService;
	
	@Autowired
	AuthCodeService authCodeService;

	@Autowired
	SecurityConfig securityConfig;

	/**
	 * 自定义验证方式
	 */
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(securityConfig.getIsUseAuthCode()){
			return doAuthenticationForMobileAndAuthCode(authentication);
		}
		else{
			return doAuthenticationForUsernameAndPassword(authentication);
		}
    }

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	private Authentication doAuthenticationForUsernameAndPassword(Authentication authentication)
			throws AuthenticationException {
		// AES以时间戳作为向量加密后的用户名与MD5加密后的密码
		String encryptedUsername = authentication.getName();
		String encryptedPassword = (String) authentication.getCredentials();
		
		// AES解密用户名
		// 获取当前时间（秒针设定为0秒）时间戳值
		// 由于可能存在误差，则分别取当前时间与下一分钟（秒针设定为0秒）时间戳值
	    Calendar c = Calendar.getInstance();
	    Date nowTime = DateOperateUtil.setDayTime(c.getTime(), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
	    Date nowTimePlusOneMin = DateOperateUtil.plusMinute(1, nowTime);
		String key = CommonConstant.AdminKey;
		String iv1 = IV_PACK + nowTime.getTime();
		String iv2 = IV_PACK + nowTimePlusOneMin.getTime();
		
		Boolean flag = true;
		String username = null;
		try {
			username = AESUtil.DecryptForBase64(encryptedUsername,key, iv1);
		} catch (Exception e) {
			flag = false;
		}
	    if(!flag){
	    	try {
				username = AESUtil.DecryptForBase64(encryptedUsername,key, iv2);
			} catch (Exception e) {
				logger.error("解密用户名异常");
				throw new BadCredentialsException("处理异常");
			}
	    }
        
        Admin user = (Admin) adminDetailsService.loadUserByUsername(username);
        if(user == null){
        	logger.error("用户名" + username + "不存在");
        	throw new BadCredentialsException("用户名或密码错误");
        }

        // 加密过程在这里体现
        if (!encryptedPassword.equals(user.getPassword())) {
        	// 若开启了自动锁定与自动解除配置
        	// 则判定是否触发锁定
        	if(securityConfig.getIsAutoLock()){
        		int loginFailureCount = user.getLoginFailureCount();
        		if(loginFailureCount >= securityConfig.getLoginFailureCount() - 1){
        			user.setIsLock(true);
        			user.setLockDate(new Date());
        			adminService.update(user);
        			throw new BadCredentialsException("用户" + username + "已锁定");
        		}
        		else{
        			user.setLoginFailureCount(user.getLoginFailureCount() + 1);
        			Integer restCount = securityConfig.getLoginFailureCount() - user.getLoginFailureCount();
        			adminService.update(user);
        			throw new BadCredentialsException("用户名或密码错误，还能进行" + restCount.toString() + "次操作");
        		}
        	}
        	else{
        		logger.error("密码错误");
        		throw new BadCredentialsException("用户名或密码错误");
        	}
        }
        
        if(user.getIsLock()){
        	// 若开启了自动锁定与自动解除配置
        	// 则判定是否解除锁定
        	if(securityConfig.getIsAutoLock()){
        		int unlockTime = securityConfig.getUnlockTime();
				if (unlockTime > 0) {
					Date lockedDate = user.getLockDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, unlockTime);
					if (new Date().after(unlockDate)) {
						user.setLoginFailureCount(0);
						user.setIsLock(false);
						user.setLockDate(null);
						adminService.update(user);
					}
				}
        	}
        	else{ 
        		throw new BadCredentialsException("用户" + username + "已锁定");
        	}
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, encryptedPassword, authorities);
	}
	
	private Authentication doAuthenticationForMobileAndAuthCode(Authentication authentication)
			throws AuthenticationException {
		// AES以时间戳作为向量加密后的手机号与验证码
		String encryptedMobile = authentication.getName();
		String encryptedAuthCode = (String) authentication.getCredentials();
		
		// AES解密用户名
		// 获取当前时间（秒针设定为0秒）时间戳值
		// 由于可能存在误差，则分别取当前时间与下一分钟（秒针设定为0秒）时间戳值
	    Calendar c = Calendar.getInstance();
	    Date nowTime = DateOperateUtil.setDayTime(c.getTime(), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
	    Date nowTimePlusOneMin = DateOperateUtil.plusMinute(1, nowTime);
		String key = CommonConstant.AdminKey;
		String iv1 = IV_PACK + nowTime.getTime();
		String iv2 = IV_PACK + nowTimePlusOneMin.getTime();
		
		Boolean flagForMobile = true;
		String mobile = null;
		try {
			mobile = AESUtil.DecryptForBase64(encryptedMobile, key, iv1);
		} catch (Exception e) {
			flagForMobile = false;
		}
	    if(!flagForMobile){
	    	try {
	    		mobile = AESUtil.DecryptForBase64(encryptedMobile, key, iv2);
			} catch (Exception e) {
				logger.error("解密手机号异常");
				throw new BadCredentialsException("处理异常");
			}
	    }
	    
	    Boolean flagForAuthCode = true;
	    String authCode = null;
		try {
			authCode = AESUtil.DecryptForBase64(encryptedAuthCode, key, iv1);
		} catch (Exception e) {
			flagForAuthCode = false;
		}
	    if(!flagForAuthCode){
	    	try {
	    		authCode = AESUtil.DecryptForBase64(encryptedAuthCode, key, iv2);
			} catch (Exception e) {
				logger.error("解密验证码异常");
				throw new BadCredentialsException("处理异常");
			}
	    }
        
	    Admin admin = adminService.getByMobile(mobile);
	    if(admin == null){
	    	logger.error("手机号" + mobile + "不存在");
       	    throw new BadCredentialsException("手机号或验证码错误");
        }
        Admin user = (Admin) adminDetailsService.loadUserByUsername(admin.getUsername());
        
        // 验证短信验证码
        String result = authCodeService.checkAuthCode(mobile, authCode);
        if (!result.equals(CommonConstant.AuthCodeResult.CHECK_SUCCESS)) {
        	if (result.equals(CommonConstant.AuthCodeResult.CHECK_FAILURE)) {
        		throw new BadCredentialsException(CommonConstant.AuthCodeResult.CHECK_FAILURE_MESSAGE);
			} else if (result.equals(CommonConstant.AuthCodeResult.TIMEOUT)) {
				throw new BadCredentialsException(CommonConstant.AuthCodeResult.TIMEOUT_MESSAGE);
			} else {
				throw new BadCredentialsException(CommonConstant.AuthCodeResult.NONE_MESSAGE);
			}
        }
        
        if(user.getIsLock()){
        	// 若开启了自动锁定与自动解除配置
        	// 则判定是否解除锁定
        	if(securityConfig.getIsAutoLock()){
        		int unlockTime = securityConfig.getUnlockTime();
				if (unlockTime > 0) {
					Date lockedDate = user.getLockDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, unlockTime);
					if (new Date().after(unlockDate)) {
						user.setLoginFailureCount(0);
						user.setIsLock(false);
						user.setLockDate(null);
						adminService.update(user);
					}
				}
        	}
        	else{ 
        		throw new BadCredentialsException("手机号" + mobile + "的用户已锁定");
        	}
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
	}
}