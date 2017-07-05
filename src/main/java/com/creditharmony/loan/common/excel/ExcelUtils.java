package com.creditharmony.loan.common.excel;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 
 * @author dongchen
 * 
 */
@SuppressWarnings("deprecation")
public class ExcelUtils {
	private static HSSFWorkbook wb;

	private static CellStyle titleStyle; // 标题行样式
	private static Font titleFont; // 标题行字体
	private static CellStyle dateStyle; // 日期行样式
	private static Font dateFont; // 日期行字体
	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体
	private static CellStyle doubleContextStyle;

	/**
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ParseException 
	 * @Description: 将Map里的集合对象数据输出Excel数据流
	 */
	public static HSSFWorkbook export2Excel(ExportSetInfo setInfo)
			throws IOException, IllegalArgumentException,
			IllegalAccessException {
		init();
		List<String[]> set = setInfo.getObjsMap();
		String[] sheetNames = new String[setInfo.getTitles().length];
		int sheetNameNum = 0;
		for (int i = 0; i < setInfo.getTitles().length; i++) {
			sheetNames[sheetNameNum] = setInfo.getTitles()[sheetNameNum];
			sheetNameNum++;
		}
		HSSFSheet[] sheets = getSheets(setInfo.getTitles().length, sheetNames);
		int sheetNum = 0;
		// Sheet

		// 标题行
		createTableTitleRow(setInfo, sheets, sheetNum);
		// 日期行
		createTableDateRow(setInfo, sheets, sheetNum);

		int rowNum = 3;
		for (int i = 0; i < set.size(); i++) {
			creatTableHeadRow(setInfo, sheets, sheetNum);
			String[] objs = set.get(i);
			HSSFRow contentRow = sheets[setInfo.getTitles().length - 1]
					.createRow(rowNum);
			contentRow.setHeight((short) 450);
			HSSFCell[] cells = getCells(contentRow,
					setInfo.getObjsMap().get(sheetNum).length);
			int cellNum = 1; // 去掉一列序号，因此从1开始
			if (objs != null) {
				//Pattern p = Pattern.compile("^[0-9]+\\.{1}[0-9]+$");
				// Pattern p1 = Pattern.compile("^([0-9]){1,9}$");
				Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
				
				for (int num = 0; num < objs.length; num++) {
					String value = objs[num];
					if(value!=null){
						value = value.trim();
					}
					boolean flag2 = p2.matcher(value).matches();
					if (flag2) {
						cells[cellNum].setCellStyle(doubleContextStyle);
						cells[cellNum]
								.setCellValue(Double.parseDouble(value));
					}
					else {
						cells[cellNum].setCellStyle(contentStyle);
						cells[cellNum].setCellValue(value == null ? "" : value
								.toString());
					}
					cellNum++;
				}
			}
			rowNum++;
			adjustColumnSize(sheets, setInfo.getTitles().length - 1, objs); // 自动调整列宽
			sheetNum++;
		}

		return wb;
	}
	
	
	
	/**
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ParseException 
	 * @Description: 将Map里的集合对象数据输出Excel数据流
	 */
	public static HSSFWorkbook export2Excel2(List<ExportSetInfo>  setInfoss)
			throws IOException, IllegalArgumentException,
			IllegalAccessException {
		init();
		for (ExportSetInfo setInfo : setInfoss) {
			List<String[]> set = setInfo.getObjsMap();
			String[] sheetNames = new String[setInfo.getTitles().length];
			int sheetNameNum = 0;
			for (int i = 0; i < setInfo.getTitles().length; i++) {
				sheetNames[sheetNameNum] = setInfo.getTitles()[sheetNameNum];
				sheetNameNum++;
			}
			HSSFSheet[] sheets = getSheets(setInfo.getTitles().length, sheetNames);
			int sheetNum = 0;
			// Sheet

			// 标题行
			createTableTitleRow(setInfo, sheets, sheetNum);
			// 日期行
			createTableDateRow(setInfo, sheets, sheetNum);

			int rowNum = 3;
			for (int i = 0; i < set.size(); i++) {
				creatTableHeadRow(setInfo, sheets, sheetNum);
				String[] objs = set.get(i);
				HSSFRow contentRow = sheets[setInfo.getTitles().length - 1]
						.createRow(rowNum);
				contentRow.setHeight((short) 450);
				HSSFCell[] cells = getCells(contentRow,
						setInfo.getObjsMap().get(sheetNum).length);
				int cellNum = 1; // 去掉一列序号，因此从1开始
				if (objs != null) {
					//Pattern p = Pattern.compile("^[0-9]+\\.{1}[0-9]+$");
					//Pattern p1 = Pattern.compile("^([0-9]){1,9}$");
					Pattern p2=Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{2})+$");
					
					for (int num = 0; num < objs.length; num++) {
						String value = objs[num];
						if(value!=null){
							value = value.trim();
						}
						boolean flag2 = p2.matcher(value).matches();
						if (flag2) {
							cells[cellNum].setCellStyle(doubleContextStyle);
							cells[cellNum]
									.setCellValue(Double.parseDouble(value));
						}
						else {
							cells[cellNum].setCellValue(value == null ? "" : value
									.toString());
						}
						cellNum++;
					}
				}
				rowNum++;
				adjustColumnSize(sheets, setInfo.getTitles().length - 1, objs); // 自动调整列宽
				sheetNum++;
//				System.out.println(sheetNum);
			}
		}

		return wb;
	}

	/**
	 * @Description: 初始化
	 */
	private static void init() {
		wb = new HSSFWorkbook();

		titleFont = wb.createFont();
		titleStyle = wb.createCellStyle();
		dateStyle = wb.createCellStyle();
		dateFont = wb.createFont();
		headStyle = wb.createCellStyle();
		headFont = wb.createFont();
		contentStyle = wb.createCellStyle();
		contentFont = wb.createFont();
		doubleContextStyle= wb.createCellStyle();
		initTitleCellStyle();
		initTitleFont();
		initDateCellStyle();
		initDateFont();
		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
		initDoubleContextStyle();
	}

	/**
	 * @Description: 自动调整列宽
	 */
	@SuppressWarnings("unused")
	private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum,
			String[] fieldNames) {
		for (int i = 0; i < fieldNames.length + 1; i++) {
			sheets[sheetNum].autoSizeColumn(i, true);
		}
	}

	/**
	 * @Description: 创建标题行(需合并单元格)
	 */
	private static void createTableTitleRow(ExportSetInfo setInfo,
			HSSFSheet[] sheets, int sheetNum) {
		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, setInfo
				.getHeadNames().get(sheetNum).length);
		sheets[sheetNum].addMergedRegion(titleRange);
		HSSFRow titleRow = sheets[sheetNum].createRow(0);
		titleRow.setHeight((short) 800);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
	}

	/**
	 * @Description: 创建日期行(需合并单元格)
	 */
	private static void createTableDateRow(ExportSetInfo setInfo,
			HSSFSheet[] sheets, int sheetNum) {
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, setInfo
				.getHeadNames().get(sheetNum).length);
		sheets[sheetNum].addMergedRegion(dateRange);
		HSSFRow dateRow = sheets[sheetNum].createRow(1);
		dateRow.setHeight((short) 350);
		HSSFCell dateCell = dateRow.createCell(0);
		dateCell.setCellStyle(dateStyle);
		dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
	}

	/**
	 * @Description: 创建表头行(需合并单元格)
	 */
	private static void creatTableHeadRow(ExportSetInfo setInfo,
			HSSFSheet[] sheets, int sheetNum) {
		// 表头
		HSSFRow headRow = sheets[setInfo.getTitles().length - 1].createRow(2);
		headRow.setHeight((short) 550);
		// 序号列
		HSSFCell snCell = headRow.createCell(0);
		snCell.setCellStyle(headStyle);
		snCell.setCellValue("序号");
		// 列头名称
		for (int num = 1, len = setInfo.getHeadNames().get(0).length; num <= len; num++) {
			HSSFCell headCell = headRow.createCell(num);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue(setInfo.getHeadNames().get(0)[num - 1]);
		}
	}

	/**
	 * @Description: 创建所有的Sheet
	 */
	private static HSSFSheet[] getSheets(int num, String[] names) {
		HSSFSheet[] sheets = new HSSFSheet[num];
		for (int i = 0; i < num; i++) {
			sheets[i] = wb.createSheet(names[i]);
		}
		return sheets;
	}

	/**
	 * @Description: 创建内容行的每一列(附加一列序号)
	 */
	private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
		HSSFCell[] cells = new HSSFCell[num + 1];

		for (int i = 0, len = cells.length; i < len; i++) {
			cells[i] = contentRow.createCell(i);
			cells[i].setCellStyle(contentStyle);
		}
		// 设置序号列值，因为出去标题行和日期行，所有-2
		cells[0].setCellValue(contentRow.getRowNum() - 2);

		return cells;
	}

	/**
	 * @Description: 初始化标题行样式
	 */
	private static void initTitleCellStyle() {
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleStyle.setFont(titleFont);
		titleStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());

	}

	/**
	 * @Description: 初始化日期行样式
	 */
	private static void initDateCellStyle() {
		dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateStyle.setFont(dateFont);
		dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
	}

	/**
	 * @Description: 初始化表头行样式
	 */
	private static void initHeadCellStyle() {

		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headStyle.setFont(headFont);
		headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headStyle.setBorderRight(CellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
		headStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		headStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		headStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
	}

	/**
	 * @Description: 初始化内容行样式
	 */
	private static void initContentCellStyle() {
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
		contentStyle.setWrapText(true); // 字段换行
	}
	private static void initDoubleContextStyle(){
		doubleContextStyle.setAlignment(CellStyle.ALIGN_CENTER);
		doubleContextStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		doubleContextStyle.setFont(contentFont);
		doubleContextStyle.setBorderTop(CellStyle.BORDER_THIN);
		doubleContextStyle.setBorderBottom(CellStyle.BORDER_THIN);
		doubleContextStyle.setBorderLeft(CellStyle.BORDER_THIN);
		doubleContextStyle.setBorderRight(CellStyle.BORDER_THIN);
		doubleContextStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
		doubleContextStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		doubleContextStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		doubleContextStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
		doubleContextStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		doubleContextStyle.setWrapText(true); // 字段换行
	}

	/**
	 * @Description: 初始化标题行字体
	 */
	private static void initTitleFont() {
		titleFont.setFontName("华文楷体");
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setCharSet(Font.DEFAULT_CHARSET);
		titleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}

	/**
	 * @Description: 初始化日期行字体
	 */
	private static void initDateFont() {
		dateFont.setFontName("隶书");
		dateFont.setFontHeightInPoints((short) 10);
		dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		dateFont.setCharSet(Font.DEFAULT_CHARSET);
		dateFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}

	/**
	 * @Description: 初始化表头行字体
	 */
	private static void initHeadFont() {
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 10);
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setCharSet(Font.DEFAULT_CHARSET);
		headFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}

	/**
	 * @Description: 初始化内容行字体
	 */
	private static void initContentFont() {
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 10);
		contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		contentFont.setCharSet(Font.DEFAULT_CHARSET);
		contentFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}

	/**
	 * @Description: 封装Excel导出的设置信息
	 * 
	 */
	public static class ExportSetInfo {
		private List<String[]> objsMap;

		private String[] titles;

		private List<String[]> headNames;

		private List<String[]> fieldNames;

		private OutputStream out;

		public List<String[]> getObjsMap() {
			return objsMap;
		}

		public void setObjsMap(List<String[]> objsMap) {
			this.objsMap = objsMap;
		}

		public List<String[]> getFieldNames() {
			return fieldNames;
		}

		/**
		 * @param clazz
		 *            对应每个sheet里的每行数据的对象的属性名称
		 */
		public void setFieldNames(List<String[]> fieldNames) {
			this.fieldNames = fieldNames;
		}

		public String[] getTitles() {
			return titles;
		}

		/**
		 * @param titles
		 *            对应每个sheet里的标题，即顶部大字
		 */
		public void setTitles(String[] titles) {
			this.titles = titles;
		}

		public List<String[]> getHeadNames() {
			return headNames;
		}

		/**
		 * @param headNames
		 *            对应每个页签的表头的每一列的名称
		 */
		public void setHeadNames(List<String[]> headNames) {
			this.headNames = headNames;
		}

		public OutputStream getOut() {
			return out;
		}

		/**
		 * @param out
		 *            Excel数据将输出到该输出流
		 */
		public void setOut(OutputStream out) {
			this.out = out;
		}
	}

	public static void main(String[] args) {
		File file=new File("D:/test.xls");
		List<String[]> list = new ArrayList<String[]>();
		List<String[]> headNames = new ArrayList<String[]>();
		StringBuffer sb = null;
		headNames.add(new String[] { "客户姓名", "身份证号码", "性别", "产品种类", "联系方式" });
		for (int i = 0; i < 20000; i++) {
			System.out.println("---------------------"+i);
			sb = new StringBuffer();
			sb.append("1")
			.append(",")
			.append("2")
			.append(",")
			.append("3")
			.append(",")
			.append("4")
			.append(",")	
			.append("5");
			list.add(StringUtils.split(sb.toString(), ","));
			
		}
		ExportSetInfo setInfo = new ExportSetInfo();
		setInfo.setObjsMap(list);
		setInfo.setTitles(new String[] { "提前结清报表" });
		setInfo.setHeadNames(headNames);
		HSSFWorkbook workbook;
		OutputStream os = null;
		try {
			workbook = export2Excel(setInfo);
			 os = new FileOutputStream(file); //是否追加
			workbook.write(os);
			System.out.println("++++++++++++++++++++finish--------------------------");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} 
			
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}


