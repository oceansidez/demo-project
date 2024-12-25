package com.telecom.util.csv;

import java.util.List;

public class TestReader implements CsvReader {

	@Override
	public void getRows(int curRow, List<String> rowList, boolean isLast) {
		if(curRow == 0){
			// 跳过标题栏
			return;
		}
		try {
			for (String data : rowList) {
				System.out.print(data);
				System.out.print(" | ");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
