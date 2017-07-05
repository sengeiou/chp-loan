package com.creditharmony.loan.borrow.poscard.web;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * Pos后台数据列表导出帮助类
 * @Class Name ExportPosHelper
 * @author ghc
 * @Create In 2016年4月29日
 */
public class ExportPosHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportPosHelper.class);
	 private static SXSSFWorkbook wb;
     private static CellStyle titleStyle; // 标题行样式
     private static Font titleFont; // 标题行字体
     private static CellStyle dateStyle; // 日期行样式
     private static Font dateFont; // 日期行字体
     private static CellStyle headStyle; // 表头行样式
     private static Font headFont; // 表头行字体
     private static CellStyle contentStyle; // 内容行样式
     private static Font contentFont; // 内容行字体
	
	public static void  exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 7;
 		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.TRUSTEESHIP_PENDING_LOAN1 + System.currentTimeMillis() ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.poscard.dao.PosBacktageDao.selectPosList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(wb,resultSet, dataSheet,MAXCOLUMN);
			/*init();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.poscard.dao.PosBacktageDao.selectPosList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(resultSet, dataSheet);*/
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("Pos后台数据列表.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("Pos后台数据列表.xlsx"));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("exportData()导出数据出现异常");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private static void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN)
			throws SQLException {
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		int row = 2;
		CellStyle style = wb.createCellStyle();
	        style.setBorderBottom((short) 1);   
	        style.setBorderTop((short) 1);
	        style.setBorderLeft((short) 1);
	        style.setBorderRight((short) 1);
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        style.setFont(font);
		
		Row dataRow;
		
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell referCodeCell = dataRow.createCell(0);
			referCodeCell.setCellStyle(style);
			referCodeCell.setCellValue(resultSet.getString("referCode"));
			Cell posOrderNumberCell = dataRow.createCell(1);
			posOrderNumberCell.setCellStyle(style);
			posOrderNumberCell.setCellValue(resultSet.getString("posOrderNumber"));
			Cell paybackDateCell = dataRow.createCell(2);
			paybackDateCell.setCellStyle(style);
			paybackDateCell.setCellValue(resultSet.getString("paybackDate"));
			Cell depositedAccountCell = dataRow.createCell(3);
			depositedAccountCell.setCellStyle(style);
			depositedAccountCell.setCellValue(resultSet.getString("depositedAccount"));
			Cell applyReallyAmountCell = dataRow.createCell(4);
			applyReallyAmountCell.setCellStyle(style);
			
			String grant = resultSet.getString("applyReallyAmount");
			applyReallyAmountCell.setCellValue(df.format(Double.valueOf(grant)));
			Cell matchingStateCell = dataRow.createCell(5);
			matchingStateCell.setCellStyle(style);
			matchingStateCell.setCellValue(resultSet.getString("matchingState"));
			Cell contractCodeCell = dataRow.createCell(6);
			contractCodeCell.setCellStyle(style);
			contractCodeCell.setCellValue(resultSet.getString("contractCode"));
			Cell auditDateCell = dataRow.createCell(7);
			auditDateCell.setCellStyle(style);
			auditDateCell.setCellValue(resultSet.getString("auditDate"));
			
			row = row + 1;
		}
		for (int i = 0; i <= MAXCOLUMN; i++) {
			//dataSheet.autoSizeColumn((short)i,true); //调整第一列宽度自适应
		}
		setAutoColumn(MAXCOLUMN, dataSheet);
	}

	
	private static void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
		Row titleRow = dataSheet.createRow(0);
		titleRow.setHeight((short) (15.625*40)); 
		Cell titleCell = titleRow.createCell(0);
		dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,MAXCOLUMN));
		//创建样式
		CellStyle style = wb.createCellStyle(); 
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平
		
        //创建字体
        Font font = wb.createFont();
        //字体位置  上 下 左 右
        //font.setTypeOffset((short)0);
        //字体宽度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //字体高度
        font.setFontHeightInPoints((short)16);
        style.setFont(font);
        titleCell.setCellValue(fileName);
        titleCell.setCellStyle(style);
        
        style = wb.createCellStyle();
        style.setBorderBottom((short) 1);   
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setFillPattern(CellStyle.FINE_DOTS);
        style.setFillBackgroundColor(new HSSFColor.DARK_BLUE().getIndex());
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(new HSSFColor.WHITE().getIndex());
        font.setFontHeightInPoints((short)10);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        
		Row headerRow = dataSheet.createRow(1);
		Cell referCodeCellHeader = headerRow.createCell(0);
		referCodeCellHeader.setCellStyle(style);
		referCodeCellHeader.setCellValue("参考号");
		Cell posOrderNumberCellHeader = headerRow.createCell(1);
		posOrderNumberCellHeader.setCellStyle(style);
		posOrderNumberCellHeader.setCellValue("POS机订单号");
		Cell paybackDateCellHeader = headerRow.createCell(2);
		paybackDateCellHeader.setCellStyle(style);
		paybackDateCellHeader.setCellValue("到账日期");
		Cell depositedAccountCellHeader = headerRow.createCell(3);
		depositedAccountCellHeader.setCellStyle(style);
		depositedAccountCellHeader.setCellValue("存入账户");
		Cell applyReallyAmountCellHeader = headerRow.createCell(4);
		applyReallyAmountCellHeader.setCellStyle(style);
		applyReallyAmountCellHeader.setCellValue("金额");
		Cell matchingStateCellHeader = headerRow.createCell(5);
		matchingStateCellHeader.setCellStyle(style);
		matchingStateCellHeader.setCellValue("匹配状态");
		Cell contractCodeCellHeader = headerRow.createCell(6);
		contractCodeCellHeader.setCellStyle(style);
		contractCodeCellHeader.setCellValue("合同编号");
		Cell auditDateCellHeader = headerRow.createCell(7);
		auditDateCellHeader.setCellStyle(style);
		auditDateCellHeader.setCellValue("查账日期");
	}
	/**
	 * 设置Excel表格的自适应功能(由于poi中的自动适应除英文、数字外的其他字符不支持自动适应功能)
	 * @param maxColumn 列数
	 * @param sheet Sheet表格
	 */
	private static void setAutoColumn (int maxColumn,Sheet sheet) {
		//获取当前列的宽度，然后对比本列的长度，取最大值  
		for (int columnNum = 0; columnNum <= maxColumn; columnNum++) {  
		    int columnWidth = sheet.getColumnWidth(columnNum) / 256;  
		    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {  
		        Row currentRow;  
		        //当前行未被使用过  
		        if (sheet.getRow(rowNum) == null) {  
		            currentRow = sheet.createRow(rowNum);  
		        } else {  
		            currentRow = sheet.getRow(rowNum);  
		        }  
		        if(currentRow.getCell(columnNum) != null) {  
		            Cell currentCell = currentRow.getCell(columnNum);  
		             if(currentCell !=null){
		            	 String cellStr = currentCell.toString();
		            	 if (StringUtils.isNotEmpty(cellStr)) {
		            		 int length = cellStr.getBytes().length; 
			                  if (columnWidth < length) {  
			                    columnWidth = length;  
			                   }  
		            	 }
		             }
		        }  
		        sheet.setColumnWidth(columnNum, columnWidth * 256);
		    }  
		}  
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void init(){
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

	private static void assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		
		int row = 3;
		
		//参考号
	    String    referCode;
	    //POS订单号
        String    posOrderNumber;
        //到账日期
        String    paybackDate;
        //存入账户
        String    depositedAccount;
        //金额
        String    applyReallyAmount;
        //匹配状态
        String    matchingState;
		//合同编号
	    String    contractCode;
        //查账日期
        String    auditDate;
		
		Row dataRow;
		
		//参考号0
	    Cell    referCodeCell;
	    //POS订单号1
        Cell    posOrderNumberCell;
        //到账日期2
        Cell    paybackDateCell;
        //存入账户3
        Cell    depositedAccountCell;
        //金额4
        Cell    applyReallyAmountCell;
        //匹配状态5
        Cell    matchingStateCell;
		//合同编号6
        Cell    contractCodeCell;
        //查账日期7
        Cell    auditDateCell;
  
        while (resultSet.next()) {
    	//参考号
	        referCode =resultSet.getString("referCode");
	    //POS订单号
            posOrderNumber =resultSet.getString("posOrderNumber");
        //到账日期
            paybackDate =resultSet.getString("paybackDate");
        //存入账户
            depositedAccount =resultSet.getString("depositedAccount");
        //金额
            applyReallyAmount =resultSet.getString("applyReallyAmount");
        //匹配状态
            matchingState =resultSet.getString("matchingState");
		//合同编号
	        contractCode =resultSet.getString("contractCode");
        //查账日期
            auditDate =resultSet.getString("auditDate");
          //
			dataRow = dataSheet.createRow(row);
			referCodeCell = dataRow.createCell(0);
			referCodeCell.setCellValue(referCode);
			referCodeCell.setCellStyle(contentStyle);
			
			posOrderNumberCell = dataRow.createCell(1);
			posOrderNumberCell.setCellValue(posOrderNumber);
			posOrderNumberCell.setCellStyle(contentStyle);
			
			paybackDateCell = dataRow.createCell(2);
			paybackDateCell.setCellValue(paybackDate);
			paybackDateCell.setCellStyle(contentStyle);
			
			depositedAccountCell = dataRow.createCell(3);
			depositedAccountCell.setCellValue(depositedAccount);
			depositedAccountCell.setCellStyle(contentStyle);
			
			applyReallyAmountCell = dataRow.createCell(4);
			applyReallyAmountCell.setCellValue(applyReallyAmount);
			applyReallyAmountCell.setCellStyle(contentStyle);
			
			matchingStateCell = dataRow.createCell(5);
			matchingStateCell.setCellValue(matchingState);
			matchingStateCell.setCellStyle(contentStyle);
			
			contractCodeCell = dataRow.createCell(6);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			auditDateCell = dataRow.createCell(7);
			auditDateCell.setCellValue(auditDate);
			auditDateCell.setCellStyle(contentStyle);
		
		
			row++;
        }
		
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("POS后台数据列表",dataSheet,19);
		createTableDateRow(dataSheet,19);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("参考号");
		Cell storeNameHeader = headerRow.createCell(1);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("POS机订单号");
		Cell customerNameHeader = headerRow.createCell(2);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("到账日期");
		Cell contractCodeHeader = headerRow.createCell(3);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("存入账户");
		Cell bankHeader = headerRow.createCell(4);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("金额");
		Cell overDateHeader = headerRow.createCell(5);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("匹配状态");
		Cell payAmountMoneyHeader = headerRow.createCell(6);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("合同编号");
		Cell overDayHeader = headerRow.createCell(7);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("查账日期");
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
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        dateFont.setCharSet(Font.DEFAULT_CHARSET);
        dateFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
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
     * @Description: 创建标题行(需合并单元格)
     */
    private static void createTableTitleRow(String title,Sheet dataSheet, int sheetNum) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0,sheetNum);
        dataSheet.addMergedRegion(titleRange);
        Row  titleRow = dataSheet.createRow(0);
        titleRow.setHeight((short) 800);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(title);
    }
    
    /**
     * @Description: 创建日期行(需合并单元格)
     */
    private static void createTableDateRow(Sheet sheets, int sheetNum) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,19);
        sheets.addMergedRegion(dateRange);
        Row dateRow = sheets.createRow(1);
        dateRow.setHeight((short) 350);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellStyle(dateStyle);
        // dateCell.setCellValue("导出时间：" + new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        // .format(new Date()));
        dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date()));
    }
    
	/**
	 * 设置导出数据 
	 * @param list
	 * @param dataSheet
	 */
	public static  void setFyDataList(List<PaybackSplitFyEx> list,Sheet dataSheet){
		Row dataRow;
		int row = 1;
		Cell numCell;
		Cell bankNameCell;
		Cell bankAccountCell;
		Cell bankAccountNameCell;
		Cell splitAmountCell;
		Cell enterpriseSerialnoCell;
		Cell remarksCell;
		Cell customerPhoneFirstCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		
		for (PaybackSplitFyEx e : list){
			
			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(row);
			bankNameCell = dataRow.createCell(1);
			bankNameCell.setCellValue(e.getBankName());
			bankAccountCell = dataRow.createCell(2);
			bankAccountCell.setCellValue(e.getBankAccount());
			bankAccountNameCell = dataRow.createCell(3);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			splitAmountCell = dataRow.createCell(4);
			splitAmountCell.setCellValue(e.getSplitAmount().toString());
			enterpriseSerialnoCell = dataRow.createCell(5);
			enterpriseSerialnoCell.setCellValue(e.getEnterpriseSerialno());
			remarksCell = dataRow.createCell(6);
			remarksCell.setCellValue(e.getRemarks());
			customerPhoneFirstCell = dataRow.createCell(7);
			customerPhoneFirstCell.setCellValue(e.getCustomerPhoneFirst());
			dictertTypeCell = dataRow.createCell(8);
			dictertTypeCell.setCellValue(e.getDictertType());
			customerCertNumCell = dataRow.createCell(9);
			customerCertNumCell.setCellValue(e.getCustomerCertNum());
			row++;
		}
	}
	
	
	public static BigDecimal stringForBig(String monthMoney){
		BigDecimal bgSum = new BigDecimal("0.00");
		if(StringUtils.isNotEmpty(monthMoney)){
			BigDecimal bigMoney = new BigDecimal(monthMoney);
			return bigMoney;
		}else{
			return bgSum;
		}
	}

}
