package com.creditharmony.loan.borrow.payback.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.loan.borrow.payback.entity.AppointmentTemplate;
import com.creditharmony.loan.common.constants.AppointmentRule;

/**
 * 集中划扣申请导出帮助类
 * @Class Name ExportBackInterestHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportAppointmentDeductHelper {
	private static Logger logger = LoggerFactory.getLogger(ExportCenterListDeductHelper.class);
	 private static SXSSFWorkbook wb;
    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体
    private static CellStyle dateStyle; // 日期行样式
    private static Font dateFont; // 日期行字体
    private static CellStyle headStyle; // 表头行样式
    private static Font headFont; // 表头行字体
    private static CellStyle contentStyle; // 内容行样式
    private static Font contentFont; // 内容行字体
	
	
	public static SXSSFWorkbook getWorkbook() {
		wb = new SXSSFWorkbook();
		titleFont = wb.createFont();
		titleStyle = wb.createCellStyle();
		dateStyle = wb.createCellStyle();
		dateFont = wb.createFont();
		headStyle = wb.createCellStyle();
		headFont = wb.createFont();
		contentStyle = wb.createCellStyle();
		contentFont = wb.createFont();
		initTitleCellStyle();
		initTitleFont();
		initDateCellStyle();
		initDateFont();
		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
		return wb;
	}
	
	public static void init() {
		wb = new SXSSFWorkbook();
		titleFont = wb.createFont();
		titleStyle = wb.createCellStyle();
		dateStyle = wb.createCellStyle();
		dateFont = wb.createFont();
		headStyle = wb.createCellStyle();
		headFont = wb.createFont();
		contentStyle = wb.createCellStyle();
		contentFont = wb.createFont();
		initTitleCellStyle();
		initTitleFont();
		initDateCellStyle();
		initDateFont();
		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
	}
	
   /**
    * @Description: 初始化标题行样式
    */
   private static void initTitleCellStyle() {
       titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
       titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       titleStyle.setFont(titleFont);
       titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
   }

   /**
    * @Description: 初始化日期行样式
    */
   private static void initDateCellStyle() {
       dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
       dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       dateStyle.setFont(dateFont);
       dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
   }

   /**
    * @Description: 初始化表头行样式
    */
   private static void initHeadCellStyle() {
       headStyle.setAlignment(CellStyle.ALIGN_CENTER);
       headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       headStyle.setFont(headFont);
       headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
       headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
       headStyle.setBorderBottom(CellStyle.BORDER_THIN);
       headStyle.setBorderLeft(CellStyle.BORDER_THIN);
       headStyle.setBorderRight(CellStyle.BORDER_THIN);
       headStyle.setTopBorderColor(IndexedColors.BLUE.index);
       headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
       headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
       headStyle.setRightBorderColor(IndexedColors.BLUE.index);
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
       contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
       contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
       contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
       contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
       contentStyle.setWrapText(true); // 字段换行
   }

   /**
    * @Description: 初始化标题行字体
    */
   private static void initTitleFont() {
       titleFont.setFontName("华文楷体");
       titleFont.setFontHeightInPoints((short) 20);
       titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
       titleFont.setCharSet(Font.DEFAULT_CHARSET);
       titleFont.setColor(IndexedColors.BLUE_GREY.index);
   }

   /**
    * @Description: 初始化日期行字体
    */
   private static void initDateFont() {
       dateFont.setFontName("隶书");
       dateFont.setFontHeightInPoints((short) 11);
       dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
       dateFont.setCharSet(Font.DEFAULT_CHARSET);
       dateFont.setColor(IndexedColors.BLUE_GREY.index);
   }

   /**
    * @Description: 初始化表头行字体
    */
   private static void initHeadFont() {
       headFont.setFontName("宋体");
       headFont.setFontHeightInPoints((short) 13);
       headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
       headFont.setCharSet(Font.DEFAULT_CHARSET);
       headFont.setColor(IndexedColors.BLUE_GREY.index);
   }

   /**
    * @Description: 初始化内容行字体
    */
   private static void initContentFont() {
       contentFont.setFontName("宋体");
       contentFont.setFontHeightInPoints((short) 10);
       contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
       contentFont.setCharSet(Font.DEFAULT_CHARSET);
       contentFont.setColor(IndexedColors.BLUE_GREY.index);
   }
   
	/**
	 * 导出 2017年03月24日 By wengsi
	 * @param dataList
	 * @param fileName
	 * @param header
	 * @param response
	 */
	public static void exportExcels(List<AppointmentTemplate> dataList,String[] header,
			HttpServletResponse response) {
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			Row headerRow = dataSheet.createRow(0);
			for (int i=0; i<header.length;i++){
				Cell cellHeader = headerRow.createCell(i);
				cellHeader.setCellValue(header[i]);
			}
			setFyDataList(dataList,dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("预约规则数据.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("预约规则数据.xlsx"));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("exportData()导出数据出现异常");
		} 

	}
	
	/**
	 * 设置导出数据 
	 * @param list
	 * @param dataSheet
	 */
	public static  void setFyDataList(List<AppointmentTemplate> list,Sheet dataSheet){
		Row dataRow;
		int row = 1;
		Cell signPlatCell;
		Cell bankCell;
		Cell appointmentDayCell;
		Cell deductPlatCell;
		Cell tlSignCell;
		Cell klSignCell;
		Cell cjSignCell;
		Cell loanStatusCell;
		Cell overCountCell;
		Cell lateMarkCell;
		Cell overdueDaysCell;
		Cell deducttypeCell;
		Cell amountMarkCell;
		Cell applyReallyAmountCell;
		Cell ruleCodeCell;
		
		for (AppointmentTemplate e : list){
			dataRow = dataSheet.createRow(row);
			signPlatCell = dataRow.createCell(0);
			if(e.getSignPlatLabel() != null){
				signPlatCell.setCellValue(e.getSignPlatLabel());
			}
			
			bankCell = dataRow.createCell(1);
			if(e.getBank() != null){
				bankCell.setCellValue(e.getBankLabel());	
			}
			appointmentDayCell = dataRow.createCell(2);
			if(e.getAppointmentDayLabel() != null){
				appointmentDayCell.setCellValue(e.getAppointmentDayLabel());
			}
			deductPlatCell = dataRow.createCell(3);
			if(e.getDeductPlatLabel() != null){
				deductPlatCell.setCellValue(e.getDeductPlatLabel());
			}
		
			tlSignCell = dataRow.createCell(4);
			
			if(e.getTlSign() != null){
				tlSignCell.setCellValue(signCodeToName(e.getTlSign()));
			}
			
			klSignCell = dataRow.createCell(5);
			
			if(e.getKlSign() != null){
				klSignCell.setCellValue(signCodeToName(e.getKlSign()));
			}
			
			cjSignCell = dataRow.createCell(6);
			
			if(e.getCjSign() != null){
				cjSignCell.setCellValue(signCodeToName(e.getCjSign()));
			}
			
			loanStatusCell = dataRow.createCell(7);
			
			if(e.getLoanStatusLabel() != null){
				loanStatusCell.setCellValue(e.getLoanStatusLabel());
			}
			
			overCountCell = dataRow.createCell(8);
			
			if(e.getOverCount() != null){
				overCountCell.setCellValue(e.getOverCount());
			}
			
			lateMarkCell = dataRow.createCell(9);
			
			if(e.getLateMark() != null){
				lateMarkCell.setCellValue(gtltToName(e.getLateMark()));
			}
			
			overdueDaysCell = dataRow.createCell(10);
			if(e.getOverdueDays() != null){
				overdueDaysCell.setCellValue(e.getOverdueDays());
			}
			
			deducttypeCell = dataRow.createCell(11);
			if(e.getDeducttype() != null){
				deducttypeCell.setCellValue(deductTypeCodeToName(e.getDeducttype()));
			}
			
			amountMarkCell = dataRow.createCell(12);
			if(e.getAmountMark() != null){
				amountMarkCell.setCellValue(gtltToName(e.getAmountMark()));
			}
			
			applyReallyAmountCell = dataRow.createCell(13);
			if(e.getApplyReallyAmount() != null){
				applyReallyAmountCell.setCellValue(e.getApplyReallyAmount().doubleValue());
			}
			ruleCodeCell = dataRow.createCell(14);
			if(e.getRuleCode() != null){
				ruleCodeCell.setCellValue(e.getRuleCode());
			}
			row++;
		}
	}
	
	
    /**
     * 通联/卡联/畅捷是否签约
     * @param args
     */
    
    public static String signCodeToName(String name){
   	 String  code = "";
   	 if(!ObjectHelper.isEmpty(name)){
   		 if("0".equals(name)){
   			 code = "否";
   		 }
            if("1".equals(name)){
   			 code = "是";
   		 }
   	 }
   	 return code;
    }
    
    
    
    /**
     * 实时批量转化
     * @param args
     */
    
    public static String deductTypeCodeToName(String strName){
   	 String codes = "";
   	 if(!ObjectHelper.isEmpty(strName)){
   			 DeductType type = DeductType.parseByCode(strName);
   			 if(type != null){
   				codes  = type.getName();
   			 }
   	 }
   	 return codes;
    }
    
    /**
     * 将大于小于转化为code
     * @param str
     * @return
     */
    public static String gtltToName(String str){
   	       String code = "";
   	  if(!ObjectHelper.isEmpty(str)){
   		  if("0".equals(str)){
   			  code = "<";
   		  }
   		  if("1".equals(str)){
   			  code = ">=";
   		  }
   		   
   	  }
   	  return code;
    }
    
}