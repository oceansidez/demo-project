package com.telecom.base;

import com.telecom.bean.CommonConstant;
import com.telecom.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BaseController {
	@Autowired
	public HttpServletRequest request;

	
	@Autowired
	public HttpServletResponse response;
	
	@Autowired
	public MessageSource messageSource; // i18n资源国际化 
	
	private static final String HEADER_ENCODING = "UTF-8";
	private static final boolean HEADER_NO_CACHE = true;
	private static final String HEADER_TEXT_CONTENT_TYPE = "text/plain";
	private static final String HEADER_JSON_CONTENT_TYPE = "text/plain";
	
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String SUCCESS = "/manage/success_view";
	public static final String ERROR = "/manage/error_view";
	public static final String REDIRECT = "redirect";
	
	public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
	public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称
	
	public static final String ACTION_MESSAGE = "actionMessage";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String REDIRECT_URL = "redirectUrl";
	
	public static final String BASE = "base";
	public static final String FULL_PATH = "fullPath";
	public static final String ADMIN_KEY = "adminKey";
	
	// 操作状态（警告、错误、成功）
	public enum Status {
		warn, error, success
	}

	//在其他方法前执行，初始化获得站点根目录、以及全部的完整路径和登录密文传输AES加密字符串加入model中
	@ModelAttribute
	public void initPath(ModelMap model) {
		//返回站点的根目录
		String base = request.getContextPath();
		//getScheme() 协议名称，默认http  getServerName() 在服务器的配置文件中配置的服务器名称
		String fullPath = request.getScheme() + "://" + request.getServerName() + base;//相当于访问的地址栏
		model.addAttribute(BASE, base);
		model.addAttribute(FULL_PATH, fullPath);
		model.addAttribute(ADMIN_KEY, CommonConstant.AdminKey);//将A1B2c3d4X9Y8m7n6加入model
	}
	
	// 中文显示i18n资源国际化
	protected String getMessageForI18n(String messageName) {
		return messageSource.getMessage(messageName, null, Locale.CHINA);
	}
	
	// 成功跳转
	protected void setError(ModelMap model, String errorMsg, String redirectUrl) {
		//可以使用null的value
		model.put(ERROR_MESSAGE, errorMsg);
		model.put(REDIRECT_URL, redirectUrl);
	}
	
	// 失败跳转
	protected void setSuccess(ModelMap model, String successMsg, String redirectUrl) {
		model.put(ACTION_MESSAGE, successMsg);
		model.put(REDIRECT_URL, redirectUrl);
	}
	
	// AJAX输出
	protected String ajax(String content, String contentType) {
		try {
			//设置响应方式，使用服务器端控制AJAX页面缓存
			HttpServletResponse response = initResponse(contentType);
			//将content写入浏览器
			response.getWriter().write(content);
			//强制将缓冲区中的数据发送出去,不必等到缓冲区满.
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 根据文本内容输出AJAX,将text/plain这种方式响应给浏览器
	protected String ajax(String text) {
		return ajax(text, HEADER_TEXT_CONTENT_TYPE);
	}
	
	// 根据操作状态输出AJAX
	protected String ajax(Status status) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		JsonUtil.toJson(response, jsonMap);
		return null;
	}
	
	// 根据操作状态、消息内容输出AJAX
	protected String ajax(Status status, String message) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		JsonUtil.toJson(response, jsonMap);
		return null;
	}
	
	// 根据Object输出AJAX
	protected String ajax(Object object) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
			JsonUtil.toJson(response, object);
		return null;
	}
	
	// 根据boolean状态输出AJAX
	protected String ajax(boolean booleanStatus) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, booleanStatus);
		JsonUtil.toJson(response, jsonMap);
		return null;
	}

	/**
	 * 由上面调用传过来的contentType设置响应给浏览器的相应方式，并设置字符集为utf-8
	 * 当向服务器发送请求时不使用缓存，发送请求，并检查该资源是否有修改
	 * @param contentType 设置相应给浏览器的相应方式
	 * @return
	 */
	private HttpServletResponse initResponse(String contentType) {
		response.setContentType(contentType + ";charset=" + HEADER_ENCODING);
		if (HEADER_NO_CACHE) {
			//把指定的头名称以及日期设置为响应头信息
			response.setDateHeader("Expires", 1L);
			//去掉缓存，然后刷新页面
			response.addHeader("Pragma", "no-cache");
			//每次请求直接发送给源服务器不进本地缓存版本的校验、防止重要的信息被无意的发布、向server 发送http 请求确认 ,该资源是否有修改
			//有的话 返回200 ,无的话 返回304.
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		return response;
	}

}
