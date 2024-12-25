package com.telecom.pay.bestpay.wap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OtherTool {

	// 取得现在的日期，格式yyyymmddhhmmss
	@SuppressWarnings("static-access")
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH) + 1;
		int day = cal.get(cal.DAY_OF_MONTH);
		int hour = cal.get(cal.HOUR_OF_DAY);
		int minute = cal.get(cal.MINUTE);
		int second = cal.get(cal.SECOND);
		String cDate = Integer.toString(year);
		if (month < 10) {
			cDate = cDate + "0" + Integer.toString(month);
		} else {
			cDate = cDate + Integer.toString(month);
		}
		if (day < 10) {
			cDate = cDate + "0" + Integer.toString(day);
		} else {
			cDate = cDate + Integer.toString(day);
		}
		if (hour < 10) {
			cDate = cDate + "0" + Integer.toString(hour);
		} else {
			cDate = cDate + Integer.toString(hour);
		}
		if (minute < 10) {
			cDate = cDate + "0" + Integer.toString(minute);
		} else {
			cDate = cDate + Integer.toString(minute);
		}
		if (second < 10) {
			cDate = cDate + "0" + Integer.toString(second);
		} else {
			cDate = cDate + Integer.toString(second);
		}
		return cDate.trim();
	}

	// 取得当前日期,格式yyyymmdd
	public static String getTodayDate2() {
		// 初始化时间
		Calendar RightNow = Calendar.getInstance();
		return changeDatetoString2(RightNow);
	}

	// 将日期转换成字符串,格式yyyymmdd
	public static String changeDatetoString2(Calendar cDate) {
		int Year;
		int Month;
		int Day;
		String sDate = "";

		// 初始化时间
		Year = cDate.get(Calendar.YEAR);
		Month = cDate.get(Calendar.MONTH) + 1;
		Day = cDate.get(Calendar.DAY_OF_MONTH);

		sDate = Integer.toString(Year);
		if (Month >= 10) {
			sDate = sDate + Integer.toString(Month);
		} else {
			sDate = sDate + "0" + Integer.toString(Month);
		}
		if (Day >= 10) {
			sDate = sDate + Integer.toString(Day);
		} else {
			sDate = sDate + "0" + Integer.toString(Day);
		}
		return sDate;
	}
	
	public static String changeY2F(String fee) {
		float sessionmoney = Float.parseFloat(fee);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		int intMoney = Integer.parseInt(finalmoney);
		return String.valueOf(intMoney);
	}
	
	/**
	 * 获取当前时间
	 *
	 * @param pattern
	 * 		yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String now(String... pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		String format = "yyyy-MM-dd HH:mm:ss";
		sdf.applyPattern(format);
		if (pattern.length > 1) {
			throw new RuntimeException("args length more than 1");
		}
		if (pattern.length == 1) {
			sdf.applyPattern(pattern[0]);
		}
		return sdf.format(new Date());
	}
}
