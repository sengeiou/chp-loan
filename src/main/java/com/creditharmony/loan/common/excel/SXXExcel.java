package com.creditharmony.loan.common.excel;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.creditharmony.loan.common.excel.ExcelUtils.ExportSetInfo;

//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Pattern;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDataFormat;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.streaming.SXSSFCell;
//import org.apache.poi.xssf.streaming.SXSSFRow;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class SXXExcel {
	List<ExportSetInfo> setInfoss = new ArrayList<ExportSetInfo>();
	private ExportSetInfo setInfo ;
//	private  SXSSFWorkbook workbook;
//	private  CellStyle titleStyle; // 标题行样式
//	private  Font titleFont; // 标题行字体
//	private  CellStyle dateStyle; // 日期行样式
//	private  Font dateFont; // 日期行字体
//	private  CellStyle headStyle; // 表头行样式
//	private  Font headFont; // 表头行字体
//	private  CellStyle contentStyle; // 内容行样式
//	private  Font contentFont; // 内容行字体
//	private  CellStyle doubleContextStyle;
//	
//	/**
//	 * @Description: 初始化
//	 */
//	private void init() {
//		workbook = new SXSSFWorkbook(1000);
//		titleFont = workbook.createFont();
//		titleStyle = workbook.createCellStyle();
//		dateStyle = workbook.createCellStyle();
//		dateFont = workbook.createFont();
//		headStyle = workbook.createCellStyle();
//		headFont = workbook.createFont();
//		contentStyle = workbook.createCellStyle();
//		contentFont = workbook.createFont();
//		doubleContextStyle= workbook.createCellStyle();
//		initTitleCellStyle();
//		initTitleFont();
//		initDateCellStyle();
//		initDateFont();
//		initHeadCellStyle();
//		initHeadFont();
//		initContentCellStyle();
//		initContentFont();
//		initDoubleContextStyle();
//	}
//	
//	/**
//	 * @Description: 初始化标题行样式
//	 */
//	private void initTitleCellStyle() {
//		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		titleStyle.setFont(titleFont);
//		titleStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
//
//	}
//
//	/**
//	 * @Description: 初始化日期行样式
//	 */
//	private void initDateCellStyle() {
//		dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
//		dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		dateStyle.setFont(dateFont);
//		dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
//	}
//
//	/**
//	 * @Description: 初始化表头行样式
//	 */
//	private void initHeadCellStyle() {
//
//		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		headStyle.setFont(headFont);
//		headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
//		headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
//		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		headStyle.setBorderRight(CellStyle.BORDER_THIN);
//		headStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
//		headStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
//		headStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
//		headStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
//	}
//
//	/**
//	 * @Description: 初始化内容行样式
//	 */
//	private void initContentCellStyle() {
//		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		contentStyle.setFont(contentFont);
//		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
//		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
//		contentStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
//		contentStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
//		contentStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
//		contentStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
//		contentStyle.setWrapText(true); // 字段换行
//	}
//	
//	private void initDoubleContextStyle(){
//		doubleContextStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		doubleContextStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		doubleContextStyle.setFont(contentFont);
//		doubleContextStyle.setBorderTop(CellStyle.BORDER_THIN);
//		doubleContextStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		doubleContextStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		doubleContextStyle.setBorderRight(CellStyle.BORDER_THIN);
//		doubleContextStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
//		doubleContextStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
//		doubleContextStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
//		doubleContextStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
//		doubleContextStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
//		doubleContextStyle.setWrapText(true); // 字段换行
//	}
//
//	/**
//	 * @Description: 初始化标题行字体
//	 */
//	private void initTitleFont() {
//		titleFont.setFontName("华文楷体");
//		titleFont.setFontHeightInPoints((short) 20);
//		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		titleFont.setCharSet(Font.DEFAULT_CHARSET);
//		titleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
//	}
//
//	/**
//	 * @Description: 初始化日期行字体
//	 */
//	private void initDateFont() {
//		dateFont.setFontName("隶书");
//		dateFont.setFontHeightInPoints((short) 10);
//		dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		dateFont.setCharSet(Font.DEFAULT_CHARSET);
//		dateFont.setColor(IndexedColors.BLUE_GREY.getIndex());
//	}
//
//	/**
//	 * @Description: 初始化表头行字体
//	 */
//	private void initHeadFont() {
//		headFont.setFontName("宋体");
//		headFont.setFontHeightInPoints((short) 10);
//		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		headFont.setCharSet(Font.DEFAULT_CHARSET);
//		headFont.setColor(IndexedColors.BLUE_GREY.getIndex());
//	}
//
//	/**
//	 * @Description: 初始化内容行字体
//	 */
//	private void initContentFont() {
//		contentFont.setFontName("宋体");
//		contentFont.setFontHeightInPoints((short) 10);
//		contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
//		contentFont.setCharSet(Font.DEFAULT_CHARSET);
//		contentFont.setColor(IndexedColors.BLUE_GREY.getIndex());
//	}
	
	public SXXExcel(String[] headers){
		this("", headers);
	}
		
	public SXXExcel(String title, String[] headers){
		List<String[]> heads = new ArrayList<String[]>();
		heads.add(headers);
		setInfo = new ExportSetInfo();
		setInfo.setObjsMap(new ArrayList<String[]>());
		setInfo.setTitles(new String[] { title });
		setInfo.setHeadNames(heads);
	}
	
	
//	public SXXExcel(String title, String[] headers,String title1,String[] headers1,String[]headers2){
//		init();
//		SXSSFSheet sheet = workbook.createSheet(title);
//		SXSSFRow fristRow=sheet.createRow(0);
//		SXSSFCell titleCell =fristRow.createCell(0);
//		titleCell.setCellStyle(titleStyle);
//		titleCell.setCellValue(title);
//		sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));
//		SXSSFRow dateRow=sheet.createRow(1);
//		SXSSFCell dateCell = dateRow.createCell(0);
//		dateCell.setCellStyle(dateStyle);
//		dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		sheet.addMergedRegion(new CellRangeAddress(1,1,0,headers.length-1));
//		
//		SXSSFRow headerRow=sheet.createRow(2);
//		for(int i=0;i<headers.length;i++){
//			SXSSFCell cell = headerRow.createCell(i);
//			cell.setCellStyle(headStyle);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(headers[i]);
//		}
//		
//		SXSSFSheet sheet1 = workbook.createSheet(title1);
//		SXSSFRow fristRow1=sheet1.createRow(0);
//		SXSSFCell titleCell1 =fristRow1.createCell(0);
//		titleCell1.setCellStyle(titleStyle);
//		titleCell1.setCellValue(title1);
//		sheet1.addMergedRegion(new CellRangeAddress(0,0,0,headers2.length-1));
//		SXSSFRow dateRow1=sheet1.createRow(1);
//		SXSSFCell dateCell1 = dateRow1.createCell(0);
//		dateCell1.setCellStyle(dateStyle);
//		dateCell1.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		sheet1.addMergedRegion(new CellRangeAddress(1,1,0,headers2.length-1));
//		
//		SXSSFRow headerRow1=sheet1.createRow(2);
//		for(int i=0;i<headers1.length;i++){
//			SXSSFCell cell = headerRow1.createCell(i);
//			cell.setCellStyle(dateStyle);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(headers1[i]);
//			if(i != 0){
//				sheet1.addMergedRegion(new CellRangeAddress(2,2,i,(i+1)));
//				i=i+1;
//			}
//			
//		}
//		SXSSFRow headerRow2=sheet1.createRow(3);
//		for(int i=0;i<headers2.length;i++){
//			SXSSFCell cell = headerRow2.createCell(i);
//			cell.setCellStyle(headStyle);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(headers2[i]);
//		}
//	}
	
	
//	public SXXExcel(String[] headers){
//		init();
//		SXSSFSheet sheet = workbook.createSheet();
//		SXSSFRow headerRow=sheet.createRow(0);
//		for(int i=0;i<headers.length;i++)
//		{
//			SXSSFCell cell = headerRow.createCell(i);
//			cell.setCellStyle(headStyle);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue(headers[i]);
//		}
//	}
//	public SXXExcel(){
//	}
//	
//	public SXXExcel(List<String> headers){
//		init();
//		SXSSFSheet sheet = workbook.createSheet();
//		SXSSFRow headerRow=sheet.createRow(0);
//		for(int i=0; i<headers.size(); i++)
//		{
//			SXSSFCell cell = headerRow.createCell(i);
//			cell.setCellValue(headers.get(i));
//		}
//	}
	
//	
//	/**
//	 * 往EXCEL追加数据
//	 * @param filePath
//	 * @param rows
//	 */
//	public void addSXSSFRow(List<Object[]> rows)
//	{
//		SXSSFSheet sheet = workbook.getSheetAt(0);
//		for(Object[] cells : rows)
//		{
//			SXSSFRow excelRow=sheet.createRow(sheet.getLastRowNum()+1);
//			for(int i=0;i<cells.length;i++)
//			{
//				if(cells[i] instanceof Number)
//				{
//					SXSSFCell cell = excelRow.createCell(i);
//					cell.setCellStyle(contentStyle);
//					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//					cell.setCellValue(((Number)cells[i]).doubleValue());
//				}
//				else
//				{
//					SXSSFCell cell = excelRow.createCell(i);
//					cell.setCellStyle(contentStyle);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(this.toString(cells[i]));
//				}
//			}
//		}
//	}
	
	
	/**
	 * 往EXCEL追加数据
	 * @param filePath
	 * @param rows
	 */
	public void addSXSSFRow(Object[] cells)
	{
		String[] values = new String[cells.length];
		setInfo.getObjsMap().add(values);
		DecimalFormat decimalF = new DecimalFormat("0.00");
		
		for(int i=0;i<cells.length;i++)
		{
			if(cells[i] instanceof BigDecimal)
			{
				values[i] = decimalF.format(cells[i]);
			}
			else
			{
				values[i] = this.toString(cells[i]);
			}
		}
	}
//	public void addSXSSFRow1(Object[] cells,HSSFSheet sheet,int num) throws IOException
//	{
//		HSSFRow excelRow=sheet.createRow(num+1);
//		Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
//		
//		for(int i=0;i<cells.length;i++)
//		{
//			if(cells[i] instanceof BigDecimal)
//			{
//				HSSFCell cell = excelRow.createCell(i);
//				cell.setCellStyle(contentStyle);
//				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//				cell.setCellValue(((BigDecimal)cells[i]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			}
//			else
//			{
//				if(null !=cells[i] && !"".equals(cells[i].toString())){
//					boolean flag2 = p2.matcher(cells[i].toString()).matches();
//					if (flag2) {
//						HSSFCell cell = excelRow.createCell(i);
//						cell.setCellStyle(contentStyle);
//						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//						cell.setCellValue(Double.parseDouble(cells[i].toString()));
//					}else {
//						HSSFCell cell = excelRow.createCell(i);
//						cell.setCellStyle(contentStyle);
//						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue(this.toString(cells[i]));
//					}
//				}else {
//					HSSFCell cell = excelRow.createCell(i);
//					cell.setCellStyle(contentStyle);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(this.toString(cells[i]));
//				}
//			}
//		}
//		// sheet.autoSizeColumn(6);
//	}
//	
//	/**
//	 * 往EXCEL追加数据
//	 * @param filePath
//	 * @param rows
//	 */
//	public void addSXSSFRow2(Object[] cells)
//	{
//		SXSSFSheet sheet = workbook.getSheetAt(0);
//		SXSSFRow excelRow=sheet.createRow(sheet.getLastRowNum()+1);
//		Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
//		
//		for(int i=0;i<cells.length;i++)
//		{
//			if(cells[i] instanceof BigDecimal)
//			{
//				SXSSFCell cell = excelRow.createCell(i);
//				cell.setCellStyle(contentStyle);
//				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//				cell.setCellValue(((BigDecimal)cells[i]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			}
//			else
//			{
//				if(null !=cells[i] && !"".equals(cells[i].toString())){
//					boolean flag2 = p2.matcher(cells[i].toString()).matches();
//					if (flag2) {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellStyle(contentStyle);
//						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//						cell.setCellValue(Double.parseDouble(cells[i].toString()));
//					}else {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellStyle(contentStyle);
//						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue(this.toString(cells[i]));
//					}
//				}else {
//					SXSSFCell cell = excelRow.createCell(i);
//					cell.setCellStyle(contentStyle);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(this.toString(cells[i]));
//				}
//			}
//			sheet.autoSizeColumn(i);
//		}
//	}
//	
//	/**
//	 * 往EXCEL追加数据
//	 * @param filePath
//	 * @param rows
//	 */
//	public void addSXSSFRow1(Object[] cells,int num)
//	{
//		SXSSFSheet sheet = workbook.getSheetAt(num);
//		SXSSFRow excelRow=sheet.createRow(sheet.getLastRowNum()+1);
//		Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
//		
//		for(int i=0;i<cells.length;i++)
//		{
//			if(cells[i] instanceof BigDecimal)
//			{
//				SXSSFCell cell = excelRow.createCell(i);
//				cell.setCellStyle(contentStyle);
//				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//				cell.setCellValue(((BigDecimal)cells[i]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			}
//			else
//			{
//				if(null !=cells[i] && !"".equals(cells[i].toString())){
//					boolean flag2 = p2.matcher(cells[i].toString()).matches();
//					if (flag2) {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellStyle(contentStyle);
//						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//						cell.setCellValue(Double.parseDouble(cells[i].toString()));
//					}else {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellStyle(contentStyle);
//						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue(this.toString(cells[i]));
//					}
//				}else {
//					SXSSFCell cell = excelRow.createCell(i);
//					cell.setCellStyle(contentStyle);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(this.toString(cells[i]));
//				}
//			}
//		}
//	}
//	
//	
//	
//	/**
//	 * 往EXCEL追加数据,不添加任何样式
//	 * @param filePath
//	 * @param rows
//	 * 
//	 * 保留两位小数
//	 * CellStyle cellStyle = workBook.createCellStyle();
//	 *	cellStyle.setDataFormat(DataFormat.getBuiltinFormat("0.00"));
//	 */
//	public void addSXSSFRowUnstyleDouble(Object[] cells)
//	{
//		CellStyle cellStyleDouble = workbook.createCellStyle();
//		cellStyleDouble.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
//		SXSSFSheet sheet = workbook.getSheetAt(0);
//		SXSSFRow excelRow=sheet.createRow(sheet.getLastRowNum()+1);
//		Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
//		
//		for(int i=0;i<cells.length;i++)
//		{
//			if(cells[i] instanceof BigDecimal)
//			{
//				SXSSFCell cell = excelRow.createCell(i);
//				cell.setCellValue(((BigDecimal)cells[i]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			}
//			else
//			{
//				if(null !=cells[i] && !"".equals(cells[i].toString())){
//					boolean flag2 = p2.matcher(cells[i].toString()).matches();
//					if (flag2) {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellValue(Double.parseDouble(cells[i].toString()));
//						cell.setCellStyle(cellStyleDouble);
//					}else {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellValue(this.toString(cells[i]));
//					}
//				}else {
//					SXSSFCell cell = excelRow.createCell(i);
//					cell.setCellValue(this.toString(cells[i]));
//				}
//			}
//		}
//	}
//	
//	
//	/**
//	 * 往EXCEL追加数据,不添加任何样式
//	 * @param filePath
//	 * @param rows
//	 */
//	public void addSXSSFRowUnstyle(Object[] cells)
//	{
//		SXSSFSheet sheet = workbook.getSheetAt(0);
//		SXSSFRow excelRow=sheet.createRow(sheet.getLastRowNum()+1);
//		Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
//		
//		for(int i=0;i<cells.length;i++)
//		{
//			if(cells[i] instanceof BigDecimal)
//			{
//				SXSSFCell cell = excelRow.createCell(i);
//				cell.setCellValue(((BigDecimal)cells[i]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			}
//			else
//			{
//				if(null !=cells[i] && !"".equals(cells[i].toString())){
//					boolean flag2 = p2.matcher(cells[i].toString()).matches();
//					if (flag2) {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellValue(Double.parseDouble(cells[i].toString()));
//					}else {
//						SXSSFCell cell = excelRow.createCell(i);
//						cell.setCellValue(this.toString(cells[i]));
//					}
//				}else {
//					SXSSFCell cell = excelRow.createCell(i);
//					cell.setCellValue(this.toString(cells[i]));
//				}
//			}
//		}
//	}
//	
	
//	public void outSXSSFFile(String filePath)
//	{
//		FileOutputStream outFile = null;
//		java.io.BufferedOutputStream outStream = null;
//		try {
//			File file = new File(filePath);
//			file.deleteOnExit();
//			file.createNewFile();
//			outFile = new FileOutputStream(filePath);
//			outStream = new BufferedOutputStream(outFile);
//			workbook.write(outStream);
//			outStream.flush();
//			workbook.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			IOUtils.closeQuietly(outFile);
//			IOUtils.closeQuietly(outStream);
//		}
//	}
	
	public void outputExcel(HttpServletResponse response, String filename){
		OutputStream outStream = null;
		
		try {
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			// 解决https导出问题
			response.setHeader("Pragma", "public"); 
			response.setHeader("Cache-Control", "public");
			
			response.setHeader("Content-Disposition", "attachment;fileName="+ filename);
			outStream = response.getOutputStream();
			
			HSSFWorkbook workbook = ExcelUtils.export2Excel(this.setInfo);
			workbook.write(outStream);
			outStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(outStream);
		}
	}
	
	public void outSXSSFFile(HttpServletResponse response, String filename)
	{
		outputExcel(response, filename);
	}
	
//	public void outSXSSFFile(HttpServletResponse response, String filename)
//	{
//		ServletOutputStream outStream = null;
//		try {
//			filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("multipart/form-data");
//			response.setHeader("Content-Disposition", "attachment;fileName="+ filename);
//			outStream = response.getOutputStream();
//			workbook.write(outStream);
//			outStream.flush();
//			workbook.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			IOUtils.closeQuietly(outStream);
//		}
//	}
//	public void outSXSSFFile1(HttpServletResponse response, String filename,HSSFWorkbook work)
//	{
//		ServletOutputStream outStream = null;
//		try {
//			filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("multipart/form-data");
//			response.setHeader("Content-Disposition", "attachment;fileName="+ filename);
//			outStream = response.getOutputStream();
//			work.write(outStream);
//			outStream.flush();
//			outStream.close();
////			work.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			IOUtils.closeQuietly(outStream);
//		}
//	}
	


	
	private  String toString(Object val){
		if(val==null)
			return "";
		if(val instanceof String)
			return (String)val;
		else if(val instanceof Integer)
			return String.valueOf(val);
		else if(val instanceof Long)
			return String.valueOf(val);
		else if(val instanceof java.math.BigDecimal){
			NumberFormat format=NumberFormat.getNumberInstance() ;
			format.setMaximumFractionDigits(2);
			format.setGroupingUsed(false);
			return format.format(val) ; 
		}else if(val instanceof java.util.Date){
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			return format.format(val) ; 
		}else{
			return val.toString();
		}
	}
}
