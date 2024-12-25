package com.telecom.bean;


/**
 * Bean类 - 公共字典常量
 */
public class CommonConstant {
	
	/* 登录密文传输AES加密用 */
	public static final String AdminKey = "A1B2c3d4X9Y8m7n6";
	public static final String AdminPassword = "X@1Y#2Z$3a%4b^5c&6d*e7a(8d)9m!0i~.n";
	
	/* 环境类型 */
	public static final class Environment {
		public static final String LOCAL = "local";
		public static final String ONLINE = "online";
	}
	
	/* 拦截器页面错误类型 */
	public static final class InterceptorErrorType {
		public static final String FILE = "fileUploadError";
		public static final String LOGIN = "loginError";
	}
	
	/* 消息队列 */
	public static final class MsgQueue {
		public static final String TEST = "test";
		public static final String RESULT = "result";
	}
	
	/* 防止暴力破解 */
	public static final class DefendForce {
		// 登录IP在缓存中的前缀名
		public static final String LOGIN_IP_PERFIX = "LOGIN_IP_";
		// 登录手机号在缓存中的前缀名
		public static final String LOGIN_MOBILE_PERFIX = "LOGIN_MOBILE_";
	}
	
	/* WebSocket协议前缀 */
	public static final class WebSocketPerfix {
		public static final String PAY = "PAY_WEBSOCKET_";
	}
	
	/* 验证码结果 */
	public static final class AuthCodeResult {
		public static final String EXECUTE = "999";
		public static final String EXECUTE_MESSAGE = "正在处理";
		
		public static final String SUCCESS = "000";
		public static final String SUCCESS_MESSAGE = "发送成功";
		
		public static final String FAILURE = "001";
		public static final String FAILURE_MESSAGE = "发送失败";
		
		public static final String EXCEPTION = "002";
		public static final String EXCEPTION_MESSAGE = "发送异常";
		
		public static final String TIMELIMIT = "003";
		public static final String TIMELIMIT_MESSAGE = "2分钟之内无法重复获取";
		
		public static final String CHECK_SUCCESS = "010";
		public static final String CHECK_SUCCESS_MESSAGE = "验证成功";
		
		public static final String CHECK_FAILURE = "011";
		public static final String CHECK_FAILURE_MESSAGE = "验证失败，错误的验证码";
		
		public static final String NONE = "012";
		public static final String NONE_MESSAGE = "无可用验证码";
		
		public static final String TIMEOUT = "013";
		public static final String TIMEOUT_MESSAGE = "验证码超过30分钟，已失效";
	}
	
	/* 响应结果相关常量  */
	public static final class Response {
		public static final String CODE_1000 = "1000";
		public static final String CODE_1000_MESSAGE = "成功";
		
		public static final String CODE_1001 = "1001";
		public static final String CODE_1001_MESSAGE = "失败";
		
		public static final String CODE_1002 = "1002";
		public static final String CODE_1002_MESSAGE = "异常";
		
		public static final String CODE_1003 = "1003";
		public static final String CODE_1003_MESSAGE = "数据解密失败";
		
		public static final String CODE_1004 = "1004";
		public static final String CODE_1004_MESSAGE = "账户ID与密钥无效或不匹配";
		
		public static final String CODE_1005 = "1005";
		public static final String CODE_1005_MESSAGE = "无效的模板id";
		
		public static final String CODE_1006 = "1006";
		public static final String CODE_1006_MESSAGE = "无效的签名id";
		
		public static final String CODE_1007 = "1007";
		public static final String CODE_1007_MESSAGE = "缺少模板匹配参数";
		
		public static final String CODE_1008 = "1008";
		public static final String CODE_1008_MESSAGE = "参数无效";
		
		public static final String CODE_1009 = "1009";
		public static final String CODE_1009_MESSAGE = "正在处理中...";
		
		public static final String CODE_1010 = "1010";
		public static final String CODE_1010_MESSAGE = "账户信息不存在";
		
		public static final String CODE_1011 = "1011";
		public static final String CODE_1011_MESSAGE = "模板配置参数缺失";
		
		public static final String CODE_1012 = "1012";
		public static final String CODE_1012_MESSAGE = "请求手机号黑名单限制";
		
		public static final String CODE_1014 = "1014";
		public static final String CODE_1014_MESSAGE = "该模板产品不可用";
		
		public static final String CODE_9997 = "9997";
		public static final String CODE_9997_MESSAGE = "系统正在维护升级中";
		
		public static final String CODE_9998 = "9998";
		public static final String CODE_9998_MESSAGE = "没有任何参数输入";
		
		public static final String CODE_9999 = "9999";
		public static final String CODE_9999_MESSAGE = "没有权限访问的IP";
	}
	
}
