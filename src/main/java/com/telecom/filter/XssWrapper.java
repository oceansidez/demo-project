package com.telecom.filter;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class XssWrapper extends HttpServletRequestWrapper {
	
	private Set<String> xssRtfList;
	private HttpServletRequest orgRequest;

	public XssWrapper(HttpServletRequest request, Set<String> rtfList) {
		super(request);
		orgRequest = request;
		xssRtfList = rtfList;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss & sql过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (value != null) {
			// 针对富文本，使用Jsoup过滤
			if(isRTF(name)){
				value = filterRTF(value);
			}
			// 针对其他普通情况，则直接过滤
			else{
				value = xssEncode(value);
			}
		}
		return value;
	}
	
	@Override  
    public String[] getParameterValues(String name) {  
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] result = new String[length];
			for (int i = 0; i < length; i++) {
				// 针对富文本，使用Jsoup过滤
				if(isRTF(name)){
					result[i] = filterRTF(result[i]);
				}
				// 针对其他普通情况，则直接过滤
				else{
					result[i] = xssEncode(values[i]);
				}
			}
			return result;
		}
		return super.getParameterValues(name);
    }
	
	@SuppressWarnings("rawtypes")
	@Override  
    public Map<String, String[]> getParameterMap() {  
		 // 获取当前参数集  
        Map<String, String[]> map = super.getParameterMap();  
        Map<String, String[]> result = new HashMap<String, String[]>();
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			String key = element.getKey().toString();
			String[] values = (String[]) element.getValue();
			if (values != null) {
				int length = values.length;
				String[] array = new String[length];
				for (int i = 0; i < length; i++) {
					array[i] = xssEncode(values[i]);
				}
				result.put(key, array);
			}
			else{
				result.put(key, values);
			}
		}
		return result;
    }

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss & sql过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof XssWrapper) {
			return ((XssWrapper) req).getOrgRequest();
		}

		return req;
	}

	/**
	 * 
	 * 防止xss跨脚本攻击（替换，根据实际情况调整）
	 */

	public static String stripXSSAndSql(String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			/** value = value.replaceAll("", ""); ***/
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern
					.compile(
							"<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>",
							Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid anything in a
			// src="http://www.yihaomen.com/article/java/..." type of
			// e-xpression
			scriptPattern = Pattern.compile(
					"src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid e-xpression(...) expressions
			scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile(
					"javascript[\r\n| | ]*:[\r\n| | ]*",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}
	
	// 针对富文本的XSS过滤处理
	private String filterRTF(String value){
		Whitelist whitelist = Whitelist.basic();
		whitelist.addTags("style");
		whitelist.addAttributes("span", "style");
		whitelist.addAttributes("p", "style");
		whitelist.addTags("img");
		whitelist.addAttributes("img", "src", "width", "height");
		whitelist.addTags("video");
		whitelist.addAttributes("video", "src", "width", "height", "autostart", "loop", "controls");
		whitelist.addTags("table");
		whitelist.addAttributes("table", "width", "height", "style", "class");
		whitelist.addTags("tbody");
		whitelist.addTags("tr");
		whitelist.addAttributes("tr", "width", "height", "style", "class");
		whitelist.addTags("td");
		whitelist.addAttributes("td", "width", "height", "style", "class");
		value = Jsoup.clean(value, whitelist);
		return value;
	}
	
	// 判断是否为富文本
	private boolean isRTF(String name){
		if(xssRtfList.contains(name)){
			return true;
		}
		for (String rtfName : xssRtfList) {
			if(rtfName.contains("##")){
				boolean flag = true;
				for (String s : rtfName.split("##")){
					if(!name.contains(s)){
						flag = false;
					}
				}
				if(flag){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 将容易引起xss & sql漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		} else {
			s = stripXSSAndSql(s);
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append("＞");// 转义大于号
				break;
			case '<':
				sb.append("＜");// 转义小于号
				break;
			case '\'':
				sb.append("＇");// 转义单引号
				break;
			case '\"':
				sb.append("＂");// 转义双引号
				break;
			case '&':
				sb.append("＆");// 转义&
				break;
			case '#':
				sb.append("＃");// 转义#
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

}