package com.telecom.util.excel;

import java.util.List;

public class DemoExcelReader implements ExcelReader {
	
	// 可定义变量作为Reader中的全局变量
	private String sign;

	/*
	 * 业务逻辑实现方法
	 * 
	 * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
	 */
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
		// TODO Auto-generated method stub
		System.out.print(curRow + " ");
		for (int i = 0; i < rowlist.size(); i++) {
			System.out.print(rowlist.get(i) + " ");
		}
		System.out.print(sign);
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/** 用法
	public static void main(String[] args) throws Exception {
		ExcelReader reader = new DemoExcelReader();
		ExcelReaderUtil.readExcel(reader, "D:\\a.xls");// 第二个参数为文件路径
	}
	*/
}
