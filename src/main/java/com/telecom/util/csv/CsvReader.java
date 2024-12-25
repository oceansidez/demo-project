package com.telecom.util.csv;

import java.util.List;

public interface CsvReader {
	
	/**
	 * 业务逻辑实现方法
	 * 
	 * @param curRow
	 * @param rowlist
	 */
	public void getRows(int curRow, List<String> rowList, boolean isLast);

}
