package com.telecom.util.database;

/**
 * 工具类
 * @author Administrator
 *
 */
public class DataBaseUtil {

	// 表名转化为实体类名
	public static String tableToEntityName(String table, String prefix) {
		String entityName = table.replaceFirst(prefix, "");
		return entityName;
	}

	// 首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    // 首字母转大写
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
		else if(mysqlType.contains("time")){
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

