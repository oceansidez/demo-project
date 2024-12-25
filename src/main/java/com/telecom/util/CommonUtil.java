package com.telecom.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 - 公用
 */

public class CommonUtil {

	public static final String PATH_PREPARED_STATEMENT_UUID = "\\{uuid\\}";// UUID路径占位符
	public static final String PATH_PREPARED_STATEMENT_DATE = "\\{date(\\(\\w+\\))?\\}";// 日期路径占位符

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 16);
	}

	/**
	 * 获取实际路径
	 * 
	 * @param path
	 *            路径
	 */
	public static String getPreparedStatementPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		StringBuffer uuidStringBuffer = new StringBuffer();
		Matcher uuidMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_UUID)
				.matcher(path);
		while (uuidMatcher.find()) {
			uuidMatcher.appendReplacement(uuidStringBuffer,
					CommonUtil.getUUID());
		}
		uuidMatcher.appendTail(uuidStringBuffer);

		StringBuffer dateStringBuffer = new StringBuffer();
		Matcher dateMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_DATE)
				.matcher(uuidStringBuffer.toString());
		while (dateMatcher.find()) {
			String dateFormate = "yyyyMM";
			Matcher dateFormatMatcher = Pattern.compile("\\(\\w+\\)").matcher(
					dateMatcher.group());
			if (dateFormatMatcher.find()) {
				String dateFormatMatcherGroup = dateFormatMatcher.group();
				dateFormate = dateFormatMatcherGroup.substring(1,
						dateFormatMatcherGroup.length() - 1);
			}
			dateMatcher.appendReplacement(dateStringBuffer,
					new SimpleDateFormat(dateFormate).format(new Date()));
		}
		dateMatcher.appendTail(dateStringBuffer);

		return dateStringBuffer.toString();
	}

	/**
	 * 判断是否为数字
	 * 
	 * @return 真假
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 将第一个字母大写
	 * 
	 * @return 字符串
	 */
	public static String upperFirstChar(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer strBuffer = new StringBuffer();
		for (int index = 0; index < str.length(); index++) {
			String c = str.substring(index, index + 1);
			if (index == 0) {
				strBuffer.append(c.toUpperCase());
			} else {
				strBuffer.append(c);
			}
		}
		return strBuffer.toString();
	}

	/**
	 * request获取真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(
			javax.servlet.http.HttpServletRequest request) {
		// X-Forwarded-For：Squid 服务代理
		// X-Forwarded-For可伪造故废弃
		// String ip = request.getHeader("X-Forwarded-For");
		String ip = null;
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// Proxy-Client-IP：Apache 服务代理
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// WL-Proxy-Client-IP：Weblogic 服务代理
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// X-Real-IP： Nginx 服务代理
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// HTTP_CLIENT_IP：其他代理服务器
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// HTTP_X_FORWARDED_FOR： 部分代理服务器需要使用该参数才能获取
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// 不使用代理直连时，使用RemoteAddress获取TCP层连接IP
			ip = request.getRemoteAddr();
		}
		
		// 多次反向代理后会有多个ip值，第一个ip才是真实ip
		if (StringUtils.isNotEmpty(ip) && ip.split(",").length > 1) {
			String[] ips = ip.split(",");
			for (String tmpIp : ips) {
				if (!"unknown".equals(tmpIp)) {
					ip = tmpIp;
					break;
				}
			}
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * 将字符串位数补足
	 * 
	 * @return 字符串
	 */
	public static String fillStr(int sign, int length) {
		String value = String.valueOf(sign);
		int len = value.length();
		for (int index = 0; index < (length - len); index++) {
			value = "0" + value;
		}
		return value;
	}

	public static String fillStr(String sign, int length) {
		String value = String.valueOf(sign);
		int len = value.length();
		for (int index = 0; index < (length - len); index++) {
			value = "0" + value;
		}
		return value;
	}

	/**
	 * 随机生成digCount位数
	 * 
	 * @param digCount
	 *            位数
	 * @return 随机数
	 */
	public static String getRandomNumber(int digCount) {
		StringBuilder sb = new StringBuilder(digCount);
		Random rnd = new Random();
		for (int i = 0; i < digCount; i++)
			sb.append((char) ('0' + rnd.nextInt(10)));
		return sb.toString();
	}
	
	/**
	 * 检查密码
	 * 
	 * @return
	 */
	public static int checkPass(String pass) {
		int ls = 0;
		Pattern p = Pattern.compile("([a-zA-Z])+");
		Matcher m = p.matcher(pass);
		if (m.find()) {
			ls++;
		}
		p = Pattern.compile("([0-9])+");
		m = p.matcher(pass);
		if (m.find()) {
			ls++;
		}
		return ls;
	}

	/**
	 * 检查密码
	 * 
	 * @return
	 */
	public static int checkTradePass(String pass) {
		int ls = 0;
		Pattern p = Pattern.compile("([a-zA-Z])+");
		Matcher m = p.matcher(pass);
		if (m.find()) {
			ls++;
		}
		p = Pattern.compile("([0-9])+");
		m = p.matcher(pass);
		if (m.find()) {
			ls++;
		}
		p = Pattern.compile("[^a-zA-Z0-9]+");
		m = p.matcher(pass);
		if (m.find()) {
			ls++;
		}
		return ls;
	}

	/**
	 * 获取魔品生成待签名的字符串
	 * 
	 * @param 字符串数组
	 * @return 代签名的字符串
	 */
	public static String getDigitalSignature(String[] strArray) {
		Arrays.sort(strArray, Collections.reverseOrder());
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < strArray.length; i++) {
			result.append(strArray[i]);
		}
		return result.toString();
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> bean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					if (null != value) {
						ret.put((K) field, (V) (null == value ? "" : value));
					}
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}
	
	//随机生成字母数字混合的字符串
	public static String getStringRandom(int lengths) {  
        String val = "";  
        Random random = new Random();  
        //参数lengths，表示生成几位随机数  
        for (int i = 0; i < lengths; i++) {  
            String strOrNum = random.nextInt(2) % 2 == 0 ? "str":"num";  
            //随机输出是字母还是数字  
            if ("str".equalsIgnoreCase(strOrNum)) {  
                //随机输出是大写字母还是小写字母  
                int temp = random.nextInt(2)%2 == 0 ? 65:97;  
                val += (char)(random.nextInt(26)+temp);  
            }else if("num".equalsIgnoreCase(strOrNum)){  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }
	
	public static String createData(int length) {  
        StringBuilder sb=new StringBuilder();  
        Random rand=new Random();  
        for(int i=0;i<length;i++)  
        {  
            sb.append(rand.nextInt(10));  
        }  
        String data=sb.toString();  
        return data;
    }
	
	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}


	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}


	/**
	 * 驼峰命名(TestMethod)
	 * @param s
	 * @return
	 */
	public static String toUpper(String s) {
		if(s.contains("_")) {
			String[] strArray = s.split("_");
			String temp = "";
			for (String str : strArray) {
				temp = temp + str.substring(0, 1).toUpperCase() + str.substring(1);
			}
			return temp;
		} else {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
	}
}