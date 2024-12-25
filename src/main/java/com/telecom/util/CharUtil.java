package com.telecom.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符工具类
 * @author Administrator
 *
 */
public class CharUtil {

	// 首字母小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    // 首字母大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    // 替换字符串并让它的下一个字母为大写
    public static String replaceUnderlineAndfirstToUpper(String srcStr) {
		String newString = "";
		String org = "_";
		String ob = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr
						.substring(first + org.length(), srcStr.length());
				srcStr = toUpperCaseFirstOne(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	} 
    
    // 字符串大写字母转下划线
	public static String upperCharToUnderLine(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_"
					+ mc.group().toLowerCase());
			i++;
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	
    // MysqlType映射到JavaType
    public static String mysqlTypeToClassType(String mysqlType) {
    	if(mysqlType.contains("varchar")){
    		return "String";
    	}
    	else if(mysqlType.contains("int") && mysqlType.indexOf("int") == 0){
    		return "Integer";
    	}
    	else if(mysqlType.contains("bigint")){
    		return "Long";
    	}
    	else if(mysqlType.contains("double")){
    		return "Double";
    	}
    	else if(mysqlType.contains("float")){
    		return "Float";
    	}
    	else if(mysqlType.contains("decimal")){
    		return "BigDecimal";
    	}
    	else if(mysqlType.contains("datetime")){
    		return "Date";
    	}
    	else if(mysqlType.contains("tinyint")){
    		return "Boolean";
    	}
    	else if(mysqlType.contains("bit")){
    		return "Boolean";
    	}
    	else if(mysqlType.contains("text")){
    		return "String";
    	}
    	else return null;
    }
    
}

