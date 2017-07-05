package com.creditharmony.loan.car.carGrant.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;

/**
 * 放款成功已办导出帮助类
 * @Class Name ExportBackInterestHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportLoanSuccessHelper {
	private static Logger logger = LoggerFactory.getLogger(ExportLoanSuccessHelper.class);
	 private static SXSSFWorkbook wb;
    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体
    private static CellStyle dateStyle; // 日期行样式
    private static Font dateFont; // 日期行字体
    private static CellStyle headStyle; // 表头行样式
    private static Font headFont; // 表头行字体
    private static CellStyle contentStyle; // 内容行样式
    private static Font contentFont; // 内容行字体
	
	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao.findDoneList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(resultSet, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("放款已办列表.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("放款已办列表.xlsx"));
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
		String loanCustomerName;  //客户姓名
		String teamManagerName; //团队经理
		String costumerManagerName; // 客户经理
		String contractCode;  //合同编号
		String finalAuditAmount; //合同金额
		String creditBankCardNo; //放款账号
		String creditMiDBankName; //开户行
		String middleName; //账号姓名
		String storeName; //门店名称
		String lendingTime; //放款时间
		String lendingUserId; //操作人员
		String borrowTursteeFlag; //标识
		String telesalesFlag; //是否电销
		
		
		
		Row dataRow;
		Cell numCell;
		Cell loanCustomerNameCell;  //客户姓名
		Cell teamManagerNameCell; //团队经理
		Cell costumerManagerNameCell; // 客户经理
		Cell contractCodeCell;  //合同编号
		Cell finalAuditAmountCell; //合同金额
		Cell creditBankCardNoCell; //放款账号
		Cell creditMiDBankNameCell; //开户行
		Cell middleNameCell; //账号姓名
		Cell storeNameCell; //门店名称
		Cell lendingTimeCell; //放款时间
		Cell lendingUserIdCell; //操作人员
		Cell borrowTursteeFlagCell; //标识
		Cell telesalesFlagCell; //是否电销
		int num =1;
		while (resultSet.next()) {
			loanCustomerName = resultSet.getString("loan_customer_name");
			teamManagerName = resultSet.getString("team_manager_name");
			costumerManagerName = resultSet.getString("costumer_manager_name");
			contractCode = resultSet.getString("contract_code");
			finalAuditAmount = resultSet.getString("final_audit_amount");
			creditBankCardNo = resultSet.getString("creditBankCardNo");
			creditMiDBankName = resultSet.getString("creditMiDBankName");
			middleName = resultSet.getString("MIDDLE_NAME");
			storeName = resultSet.getString("store_name");
			lendingTime = resultSet.getString("LENDING_TIME");
			lendingUserId = resultSet.getString("lendingUserName");
			borrowTursteeFlag = resultSet.getString("dict_loan_flag");
			telesalesFlag = DictCache.getInstance().getDictLabel("jk_telemarketing",resultSet.getString("cons_telesales_flag"));
			

			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			loanCustomerNameCell = dataRow.createCell(1);
			loanCustomerNameCell.setCellValue(loanCustomerName);
			loanCustomerNameCell.setCellStyle(contentStyle);
			
			teamManagerNameCell = dataRow.createCell(2);
			teamManagerNameCell.setCellValue(teamManagerName);
			teamManagerNameCell.setCellStyle(contentStyle);
			
			costumerManagerNameCell = dataRow.createCell(3);
			costumerManagerNameCell.setCellValue(costumerManagerName);
			costumerManagerNameCell.setCellStyle(contentStyle);
			
			contractCodeCell = dataRow.createCell(4);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			finalAuditAmountCell = dataRow.createCell(5);
			finalAuditAmountCell.setCellValue(finalAuditAmount);
			finalAuditAmountCell.setCellStyle(contentStyle);
			
			creditBankCardNoCell = dataRow.createCell(6);
			creditBankCardNoCell.setCellValue(creditBankCardNo);
			creditBankCardNoCell.setCellStyle(contentStyle);
			
			creditMiDBankNameCell = dataRow.createCell(7);
			creditMiDBankNameCell.setCellValue(creditMiDBankName);
			creditMiDBankNameCell.setCellStyle(dateStyle);
			
			middleNameCell = dataRow.createCell(8);
			middleNameCell.setCellValue(middleName);
			middleNameCell.setCellStyle(contentStyle);
			
			storeNameCell = dataRow.createCell(9);
			storeNameCell.setCellValue(storeName);
			storeNameCell.setCellStyle(contentStyle);
			
			lendingTimeCell = dataRow.createCell(10);
			lendingTimeCell.setCellValue(lendingTime);
			lendingTimeCell.setCellStyle(contentStyle);
			
			
			lendingUserIdCell = dataRow.createCell(11);
			lendingUserIdCell.setCellValue(lendingUserId);
			lendingUserIdCell.setCellStyle(contentStyle);
			
			borrowTursteeFlagCell = dataRow.createCell(12);
			borrowTursteeFlagCell.setCellValue(borrowTursteeFlag);
			borrowTursteeFlagCell.setCellStyle(contentStyle);
			
			telesalesFlagCell = dataRow.createCell(13);
			telesalesFlagCell.setCellValue(telesalesFlag);
			telesalesFlagCell.setCellStyle(contentStyle);
			num++;
			row = row + 1;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("放款已办列表",dataSheet,13);
		createTableDateRow(dataSheet,13);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell lendCodeHeader = headerRow.createCell(1);
		lendCodeHeader.setCellValue("客户姓名");
		lendCodeHeader.setCellStyle(dateStyle);
		Cell accountNoHeader = headerRow.createCell(2);
		accountNoHeader.setCellStyle(dateStyle);
		accountNoHeader.setCellValue("团队经理");
		Cell accountNameHeader = headerRow.createCell(3);
		accountNameHeader.setCellStyle(dateStyle);
		accountNameHeader.setCellValue("客户经理");
		Cell backMoneyHeader = headerRow.createCell(4);
		backMoneyHeader.setCellStyle(dateStyle);
		backMoneyHeader.setCellValue("合同编号");
		Cell bankCodeHeader = headerRow.createCell(5);
		bankCodeHeader.setCellStyle(dateStyle);
		bankCodeHeader.setCellValue("合同金额");
		Cell accountBankHeader = headerRow.createCell(6);
		accountBankHeader.setCellStyle(dateStyle);
		accountBankHeader.setCellValue("放款账号");
		Cell accountBranchHeader = headerRow.createCell(7);
		accountBranchHeader.setCellStyle(dateStyle);
		accountBranchHeader.setCellValue("开户行");
		Cell cardOrBookletHeader = headerRow.createCell(8);
		cardOrBookletHeader.setCellStyle(dateStyle);
		cardOrBookletHeader.setCellValue("账号姓名");
		Cell provinceHeader = headerRow.createCell(9);
		provinceHeader.setCellStyle(dateStyle);
		provinceHeader.setCellValue("门店名称");
		Cell cityHeader = headerRow.createCell(10);
		cityHeader.setCellStyle(dateStyle);
		cityHeader.setCellValue("放款时间");
		Cell headerCell = headerRow.createCell(11);
		headerCell.setCellStyle(dateStyle);
		headerCell.setCellValue("操作人员");
		Cell operationCell = headerRow.createCell(12);
		operationCell.setCellStyle(dateStyle);
		operationCell.setCellValue("操作标识");
		Cell electricityCell = headerRow.createCell(13);
		electricityCell.setCellStyle(dateStyle);
		electricityCell.setCellValue("是否电销");
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
       CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,16);
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
   
   

	

}
