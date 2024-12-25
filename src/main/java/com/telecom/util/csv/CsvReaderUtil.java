package com.telecom.util.csv;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderUtil {

	public static void readCsv(CsvReader reader, String filePath) {
		BufferedReader br = null;
		InputStreamReader fReader = null;
		String line = "";
		int index = 0;
		try {
			fReader = new InputStreamReader(new FileInputStream(filePath), EncodeUtil.getEncode(filePath, true));
			br = new BufferedReader(fReader);
			while ((line = br.readLine()) != null) {
				try {
					String everyLine = line;
					if(StringUtils.isEmpty(everyLine)){
						continue;
					}
					List<String> rowList = analyseCsv(everyLine);
					reader.getRows(index, rowList, false);
					index++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.getRows(index, null, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fReader.close();
			} catch (Exception e2) {
			}
			try {
				br.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public static List<String> analyseCsv(String t){
		t = t.trim();
		// 是否跳过下一个字符
		boolean isBreak = false;
		// 当前是否为""包含
		boolean d = false;
		// 是否为开始字符
		boolean st = true;
		String[] ts = t.split("|");
		String row = "";
		List<String> rowList = new ArrayList<String>();
		for (int i = 0; i < ts.length; i++) {
			if(isBreak){
				isBreak = false;
				continue;
			}
			// 最后一个字符
			boolean last = i+1 == ts.length;
			String s = ts[i];
			if(st && "=".equals(s)){
				continue;
			}
			// 当前是否为"符号
			boolean ss = "\"".equals(s);
			boolean sd = ",".equals(s);
			// 最后一个字符
			if(!d && last){
				row += s;
				rowList.add(row);
				break;
			}
			// 最后一个字符，包含在""中
			if(d && ss && last){
				rowList.add(row);
				// 全部结束
				break;
			}
			// 开始字符为"
			if(st && ss){
				st = false;
				d = true;
				continue;
			}
			// 没有包含在""中，并且当前为,号，记录单元格
			if(!d && sd){
				rowList.add(row);
				
				row = "";
				st = true;
				d = false;
				continue;
			}else{
				st = false;
			}
			if(ss && !last && "\"".equals(ts[i+1])){
				isBreak = true;

				row += s;
				continue;
			}
			// 包含特殊符号，并且当前为"号，下一个字符为,号，记录单元格
			if(d && ss && !last && ",".equals(ts[i+1])){
				isBreak = true;
				rowList.add(row);
				
				row = "";
				st = true;
				d = false;
				continue;
			}else{
				st = false;
			}
			row += s;
		}
		return rowList;
	}

}
