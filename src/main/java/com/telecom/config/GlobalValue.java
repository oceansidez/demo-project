package com.telecom.config;

import com.telecom.bean.RolesBean;
import com.telecom.websocket.PayWebSocket;
import com.telecom.websocket.TestWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 全局变量（可变）
 * PS: 生命周期即为应用生命周期
 * 
 */
@Component
public class GlobalValue {

	public static String appName;
	public static String appId;
	public static List<RolesBean> authRoles = new ArrayList<RolesBean>();
	public static HashMap<String, TestWebSocket> testWebSocketSet = new HashMap<String, TestWebSocket>();
	public static HashMap<String, PayWebSocket> payWebSocketSet = new HashMap<String, PayWebSocket>();

	@Autowired
	public static void setAppName(String appName) {
		GlobalValue.appName = appName;
	}

	@Autowired
	public static void setAppId(String appId) {
		GlobalValue.appId = appId;
	}
	
	@Autowired
	public static void setTestWebSocketSet(
			HashMap<String, TestWebSocket> testWebSocketSet) {
		GlobalValue.testWebSocketSet = testWebSocketSet;
	}

	@Autowired
	public static void setPayWebSocketSet(
			HashMap<String, PayWebSocket> payWebSocketSet) {
		GlobalValue.payWebSocketSet = payWebSocketSet;
	}

	@Autowired
	public static void setAuthRoles(List<RolesBean> authRoles) {
		GlobalValue.authRoles = authRoles;
	}

}
