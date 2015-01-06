package com.wong.core.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月17日 下午2:23:46
 *
 */
public class ExcelUtil {
	
	private HSSFWorkbook excel;
	private HSSFSheet sheet;
	private HSSFRow row;
	
	public ExcelUtil(){
		excel = new HSSFWorkbook();
	}

	/**
	 * 
	 * @param title excel标题名
	 * @param headers excel列名
	 * @param infos excel每列内容
	 * @param path  生成的excel保存的路径
	 * @return
	 */
	public boolean create(String title, String[] headers, String[][] infos, String path){
		
		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
			create(title, headers, infos, out);
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void create(String title, String[] headers, String[][] infos, OutputStream out) throws IOException{
		sheet = excel.createSheet(title);
		
		//生成列名
		row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		//生成内容  循环每一行
		for (int i = 1; i <= infos.length; i++) {
			row = sheet.createRow(i);
			
			//循环每一列
			for (int j = 0; j < infos[i].length; j++) {
				HSSFCell cell = row.createCell(j);
				HSSFRichTextString text = new HSSFRichTextString(infos[i][j]);
				cell.setCellValue(text);
			}
			
		}
		
		excel.write(out);
		
	}
	
}
