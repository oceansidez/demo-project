package com.telecom.manage.controller.web;

import com.telecom.base.BaseController;
import com.telecom.util.HttpClientUtil;
import org.apache.commons.httpclient.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Web端 门户首页
 *
 */
@Controller
@RequestMapping(value = "/web")
public class WebController extends BaseController {

	// 主页
	@GetMapping
	public String index() {
		return "redirect:/static/web/website/index.html";
	}

	// 测试公共页
	@GetMapping("test")
	public String test() {
		return "/web/test";
	}
	
	// 测试携带cookie重定向单点登录
	// 必须同域地址，跨域cookie失效
	@ResponseBody
	@PostMapping("submit")
	public String submit(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="usernameParam", required=true) String usernameParam,
			@RequestParam(value="passwordParam", required=true) String passwordParam,
			@RequestParam(value="loginUrl", required=true) String loginUrl,
			@RequestParam(value="loginRedirect", required=true) String loginRedirect,
			@RequestParam(value="loginDomain", required=true) String loginDomain,
			ModelMap model) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(usernameParam, username);
		params.put(passwordParam, password);
		// params.put("rememberMe", "true");
		
		try {
			Cookie[] cookies = HttpClientUtil.httpPostForLogin(loginUrl,params);
			
			for(Cookie cookie : cookies){
				
				javax.servlet.http.Cookie c = 
						new javax.servlet.http.Cookie(cookie.getName(),cookie.getValue());
				c.setMaxAge(-1);
				c.setDomain(cookie.getDomain());
				c.setPath(cookie.getPath());
				c.setSecure(cookie.getSecure());
				c.setVersion(cookie.getVersion());
				response.addCookie(c);
			}
			
			response.sendRedirect(loginRedirect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
