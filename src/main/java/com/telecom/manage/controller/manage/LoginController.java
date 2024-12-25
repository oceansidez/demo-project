package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import com.telecom.bean.CommonConstant;
import com.telecom.config.SecurityConfig;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.Admin;
import com.telecom.security.AdminDetailsService;
import com.telecom.manage.service.AdminService;
import com.telecom.manage.service.AuthCodeService;
import com.telecom.manage.service.RoleService;
import com.telecom.util.AESUtil;
import com.telecom.util.CommonUtil;
import com.telecom.util.DateOperateUtil;
import com.telecom.util.RedisUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "login")
public class LoginController extends BaseController {
	//创建日志对象
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	AdminDetailsService adminDetailService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AuthCodeService authCodeService;
	
	@Autowired
	SecurityConfig securityConfig;
	
	@Autowired
	WebConfig webConfig;
	
	@Autowired
	RedisUtil redisUtil;

	//获得get请求
	@GetMapping
    public String login(
    		@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            ModelMap model) {
		
		if (error != null) {
			//request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") 获取session中的数据
			if(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null){
				 model.put("error", "未知错误，无法登录");
			}
			else{
				//其它对象转化成JSON对象
				JSONObject jsonObj = JSONObject.fromObject(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
				String message = jsonObj.getString("message");
				model.put("error", message);
			}
        }

        if (logout != null) {
            model.put("msg", "您已登出！");
        }
        
        // 账号密码登录
		if(!securityConfig.getIsUseAuthCode()){
			return "/manage/login";
		}
		// 手机验证码登录
		else{
			return "/manage/login_with_mobile";
		}
    }
	
	@GetMapping("logout")
	public String logout() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}
	
	// 获取验证码
	@GetMapping("getAuthCode")
	@ResponseBody
	public String getAuthcode(@RequestParam(value = "hiddenMobile") String hiddenMobile){
		// AES解密手机号
		// 获取当前时间（秒针设定为0秒）时间戳值
		// 由于可能存在误差，则分别取当前时间与下一分钟（秒针设定为0秒）时间戳值
	    Calendar c = Calendar.getInstance();
	    Date nowTime = DateOperateUtil.setDayTime(c.getTime(), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 0);
	    Date nowTimePlusOneMin = DateOperateUtil.plusMinute(1, nowTime);
		String key = CommonConstant.AdminKey;
		String iv1 = "000" + nowTime.getTime();
		String iv2 = "000" + nowTimePlusOneMin.getTime();
		
		String mobile = null;
		Boolean flag = true;
		try {
			mobile = AESUtil.DecryptForBase64(hiddenMobile,key, iv1);
		} catch (Exception e) {
			flag = false;
		}
	    if(!flag){
	    	try {
	    		mobile = AESUtil.DecryptForBase64(hiddenMobile,key, iv2);
			} catch (Exception e) {
				logger.error("解密手机号异常");
				String msg = "处理异常";
				Status status = Status.error;
				return ajax(status, msg);
			}
	    }
		
		try {
			String ip = CommonUtil.getRemoteHost(request);
			String loginIp = CommonConstant.DefendForce.LOGIN_IP_PERFIX + ip;
			
			// 获取针对同一IP的尝试次数
			if(redisUtil.exists(loginIp)){
				Integer numberCount = Integer.valueOf(redisUtil.get(loginIp));
				if(numberCount >= securityConfig.getLoginTryLimit()){
					logger.error("IP:"+ loginIp +"尝试次数频繁，请等待"+ securityConfig.getLoginExpireTime().toString() +"秒以后重试");
					String msg = "尝试次数频繁，请等待"+ securityConfig.getLoginExpireTime().toString() +"秒以后重试";
					Status status = Status.error;
					return ajax(status, msg);
				}
			}
			else{
				redisUtil.setex(loginIp, "0", securityConfig.getLoginExpireTime());
			}
			
			// 将尝试视为一次处理存入缓存
			redisUtil.setex(loginIp, new Integer(Integer.valueOf(redisUtil.get(loginIp)) + 1 ).toString(), 
					securityConfig.getLoginExpireTime());
			
			
			// 获取针对同一号码的发送尝试次数
			String loginMobile =  CommonConstant.DefendForce.LOGIN_MOBILE_PERFIX + mobile;
			if(redisUtil.exists(loginMobile)){
				Integer numberCount = Integer.valueOf(redisUtil.get(loginMobile));
				if(numberCount >= securityConfig.getLoginTryLimit()){
					logger.error("手机号:"+ loginMobile +"尝试次数频繁，请等待"+ securityConfig.getLoginExpireTime().toString() +"秒以后重试");
					String msg = "尝试次数频繁，请等待"+ securityConfig.getLoginExpireTime().toString() +"秒以后重试";
					Status status = Status.error;
					return ajax(status, msg);
				}
			}
			else{
				redisUtil.setex(loginMobile, "0", securityConfig.getLoginExpireTime());
			}
			
			// 将尝试视为一次处理存入缓存
			redisUtil.setex(loginMobile, new Integer(Integer.valueOf(redisUtil.get(loginMobile)) + 1).toString(),
					securityConfig.getLoginExpireTime());
			
			Admin admin = adminService.getByMobile(mobile);
			if(admin == null){
				logger.error("手机号" + mobile + "用户不存在");
				String msg = "手机号或验证码错误";
				Status status = Status.error;
				return ajax(status, msg);
			}
			else{
				String result = authCodeService.sendAuthCode(mobile);
				// 若开启模糊提示则统一提示
				if(securityConfig.getIsUseAuthCodeVagueHint()){
					return ajax(Status.success, CommonConstant.AuthCodeResult.EXECUTE_MESSAGE);
				}
				// 若未开启模糊提示则精确返回结果
				else{
					if (result.equals(CommonConstant.AuthCodeResult.SUCCESS)) {
						return ajax(Status.success, CommonConstant.AuthCodeResult.SUCCESS_MESSAGE);
					} else if (result.equals(CommonConstant.AuthCodeResult.FAILURE)) {
						return ajax(Status.error, CommonConstant.AuthCodeResult.FAILURE_MESSAGE);
					} else if (result.equals(CommonConstant.AuthCodeResult.EXCEPTION)) {
						return ajax(Status.error, CommonConstant.AuthCodeResult.EXCEPTION_MESSAGE);
					} else {
						return ajax(Status.warn, CommonConstant.AuthCodeResult.TIMELIMIT_MESSAGE);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	// 获取ManageName
	@ModelAttribute
	public void getManageName(ModelMap model) {
		String manageName = webConfig.getManageName();
		model.addAttribute("manageName", manageName);
	}
}
