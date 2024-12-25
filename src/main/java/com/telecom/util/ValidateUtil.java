package com.telecom.util;

import com.telecom.bean.ValidateResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	/**
	 * 验证邮箱是否格式正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean tag = true;
		final String pattern1 = "^[\\w-]+(\\.[\\w-]+)*\\@([\\.\\w-]+)+$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}
	
	/**
	 * 验证邮编格式
	 * 
	 * @param post
	 * @return
	 */
	public static boolean checkPost(String post) {
		if (post.matches("[1-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^(?:13\\d|15\\d|18\\d|17\\d)\\d{5}(\\d{3}|\\*{3})$"); // 验证手机号
		m = p.matcher(mobile);
		b = m.matches();
		return b;
	}
	
	/**
	 * 判断是否为整数
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	/**
	 * 检验网址
	 * @return
	 */
	public static boolean checkUrl(String str) { 
        Pattern p = Pattern.compile("http(s)?://([\\w-]+.)+[\\w-]+(/[\\w- ./?%&=]*)?"); 
        Matcher m = p.matcher(str); 
        boolean flg = m.matches(); 
        return flg; 
    } 
	
	/**
	 * 跨站点脚本验证
	 * @return
	 */
	public static boolean xssValidate(String str) { 
        Pattern p = Pattern.compile("^[-“”！？：、，；。\\n\\w\\u4e00-\\u9fa5]*$"); 
        Matcher m = p.matcher(str); 
        boolean flg = m.matches(); 
        return flg; 
    } 
	
	/**
	 * 验证密码格式
	 */
	public static ValidateResult checkPassword(String new_password) {
		if (new_password.length() < 8) {
			// 密码格式错误
			return new ValidateResult(false, "密码长度不能小于8位");
		} else {
			int passNumber = 0;
			Pattern pattern1 = Pattern.compile("(?=.*[-._/!@&#$%^*]).{8,}");
			Matcher m1 = pattern1.matcher(new_password);
			if (m1.matches())
				passNumber++;
			Pattern pattern2 = Pattern.compile("(?=.*\\d).{8,}");
			Matcher m2 = pattern2.matcher(new_password);
			if (m2.matches())
				passNumber++;
			Pattern pattern3 = Pattern.compile("(?=.*[a-z]).{8,}");
			Matcher m3 = pattern3.matcher(new_password);
			if (m3.matches())
				passNumber++;
			Pattern pattern4 = Pattern.compile("(?=.*[A-Z]).{8,}");
			Matcher m4 = pattern4.matcher(new_password);
			if (m4.matches())
				passNumber++;
			if (passNumber < 3) {
				// 密码格式错误
				return new ValidateResult(false, "密码应至少包含大写字母、小写字母、数字、特殊字符的三种");
			}
			if (!new_password.matches("^[0-9a-zA-Z-._/!@&#$%^*]{8,}$")) {
				return new ValidateResult(false,
						"输入错误，特殊字符只能包含-._/!@&#$%^*等半角特殊字符");
			}
		}
		return new ValidateResult(true, "正确");
	}
	
	/**
	 * 判断是否为数字（包含正负小数）
	 */
    public static boolean isNumericAll(String str){
        Pattern pattern = Pattern.compile("-([1-9]+[0-9]*|0)(\\.[\\d]+)?|([1-9]+[0-9]*|0)(\\.[\\d]+)?");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
