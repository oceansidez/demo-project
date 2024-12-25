package com.telecom.util.excel;



public class DemoExcelWriter extends ExcelWriter {

	/*
	 * 可根据需求重写此方法，对于单元格的小数或者日期格式
	 * 会出现精度问题或者日期格式转化问题，建议使用字符串插入方法
	 * PS：该方法可直接生成新的文件
	 * 
	 */
	@Override
	public void generate() throws Exception {
		
		// 电子表格开始
		beginSheet();

		for (int i = 0; i < 100 ; i++) {
			System.out.println(i);
			// 插入新行
			insertRow(i);

			createCell(0,"123");
			createCell(1,"abc");

			// 结束行
			endRow();
		}

		// 电子表格结束
		endSheet();
		
	}

	/** 用法
	public static void main(String[] args) {
		ExcelWriter writer = new DemoExcelWriter();
		try {
			writer.process("file path");// 参数为文件路径
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	*/
}