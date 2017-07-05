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
 * 划扣已办导出帮助类
 * @Class Name ExportBackInterestHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportDrawHelper {
	private static Logger logger = LoggerFactory.getLogger(ExportDrawHelper.class);
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
							+ Encodes.urlEncode("车借划扣已办列表.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("车借划扣已办列表.xlsx"));
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
	/**
	 * 导出还款匹配列表所有数据
	 *  @Create In 2016年5月13日
	 * @author 陈伟丽
	 * @param queryMap
	 * @param response
	 */
	public static void exportDataCheck(Map<String, Object> queryMap,
			HttpServletResponse response) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeaderCheckDone(dataSheet);
			
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao.findCheckDoneList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCellCheckDone(resultSet, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("还款匹配已办列表.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("还款匹配已办列表.xlsx"));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("exportDataCheck()导出数据出现异常");
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
		String contractCode;
		String loanCustomerName;
		String midBankName;
		String bankCardNo;
		String finalProductType;
		String urgeMoeny;
		String urgeDecuteDate;
		String dictDealStatus;
		String splitFailResult;
		String dictDealType;
		String conditionalThroughFlag;
		
		Row dataRow;
		Cell numCell;
		Cell contractCodeCell;
		Cell loanCustomerNameCell;
		Cell midBankNameCell;
		Cell bankCardNoCell;
		Cell finalProductTypeCell;
		Cell urgeMoenyCell;
		Cell urgeDecuteDateCell;
		Cell dictDealStatusCell;
		Cell splitFailResultCell;
		Cell dictDealTypeCell;
		Cell conditionalThroughFlagCell;
		int num =1;
		while (resultSet.next()) {
			contractCode = resultSet.getString("contract_code");
			loanCustomerName = resultSet.getString("loan_customer_name");
			midBankName = DictCache.getInstance().getDictLabel("jk_open_bank",resultSet.getString("card_bank"));
			bankCardNo = resultSet.getString("bank_card_no");
			finalProductType = resultSet.getString("final_product_type");
			urgeMoeny = resultSet.getString("urge_moeny");
			urgeDecuteDate = resultSet.getString("urge_decute_date");
			dictDealStatus = DictCache.getInstance().getDictLabel("jk_counteroffer_result",resultSet.getString("dict_deal_status"));
			splitFailResult = resultSet.getString("split_fail_result");
			dictDealType = DictCache.getInstance().getDictLabel("jk_deduct_plat",resultSet.getString("dict_deal_type"));
			conditionalThroughFlag = DictCache.getInstance().getDictLabel("jk_car_loan_result",resultSet.getString("conditional_through_flag"));
			

			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			loanCustomerNameCell = dataRow.createCell(2);
			loanCustomerNameCell.setCellValue(loanCustomerName);
			loanCustomerNameCell.setCellStyle(contentStyle);
			
			midBankNameCell = dataRow.createCell(3);
			midBankNameCell.setCellValue(midBankName);
			midBankNameCell.setCellStyle(contentStyle);
			
			bankCardNoCell = dataRow.createCell(4);
			bankCardNoCell.setCellValue(bankCardNo);
			bankCardNoCell.setCellStyle(contentStyle);
			
			finalProductTypeCell = dataRow.createCell(5);
			finalProductTypeCell.setCellValue(finalProductType);
			finalProductTypeCell.setCellStyle(contentStyle);
			
			urgeMoenyCell = dataRow.createCell(6);
			urgeMoenyCell.setCellValue(urgeMoeny);
			urgeMoenyCell.setCellStyle(contentStyle);
			
			urgeDecuteDateCell = dataRow.createCell(7);
			urgeDecuteDateCell.setCellValue(urgeDecuteDate);
			urgeDecuteDateCell.setCellStyle(dateStyle);
			
			dictDealStatusCell = dataRow.createCell(8);
			dictDealStatusCell.setCellValue(dictDealStatus);
			dictDealStatusCell.setCellStyle(contentStyle);
			
			splitFailResultCell = dataRow.createCell(9);
			splitFailResultCell.setCellValue(splitFailResult);
			splitFailResultCell.setCellStyle(contentStyle);
			
			dictDealTypeCell = dataRow.createCell(10);
			dictDealTypeCell.setCellValue(dictDealType);
			dictDealTypeCell.setCellStyle(contentStyle);
			
			conditionalThroughFlagCell = dataRow.createCell(11);
			conditionalThroughFlagCell.setCellValue(conditionalThroughFlag);
			conditionalThroughFlagCell.setCellStyle(contentStyle);
			num++;
			row = row + 1;
		}
	}
	/**
	 * @@Create In  2016-5-13
	 * @author 陈伟丽
	 * @param resultSet
	 * @param dataSheet
	 * @throws SQLException
	 */
	private static void assembleExcelCellCheckDone(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		int row = 3;
		String contractCode;
		String loanCustomerName;
		String storeName;
		String creditMiDBankName;
		String finalProductType;
		String contractMonths;
		String loanStatusCode;
		double  finalAuditAmount;
		double urgeMoeny;
		double urgeDecuteMoeny;
		double unUrgeDecuteMoeny;
		double unUrgeDecuteMoenyTwo;
		double reallyAmount;
		String applyPayDay;
		String matchingResult;
		String conditionalThroughFlag;
		
		Row dataRow;
		Cell numCell;
		Cell contractCodeCell;
		Cell loanCustomerNameCell;
		Cell storeNameCell;
		Cell creditMiDBankNameCell;
		Cell finalProductTypeCell;
		Cell contractMonthsCell;
		Cell loanStatusCodeCell;
		Cell finalAuditAmountCell;
		Cell urgeMoenyCell;
		Cell urgeDecuteMoenyCell;
		Cell unUrgeDecuteMoenyCell;
		Cell unUrgeDecuteMoenyTwoCell;
		Cell reallyAmountCell;
		Cell applyPayDayCell;
		Cell matchingResultCell;
		Cell conditionalThroughFlagCell;
		int num =1;
		while (resultSet.next()) {
			contractCode = resultSet.getString("contract_code");//合同编号
			loanCustomerName = resultSet.getString("loan_customer_name");//客户姓名
			storeName = resultSet.getString("store_name");//门店名称
			creditMiDBankName = resultSet.getString("creditMiDBankName");//存入账户
			finalProductType = resultSet.getString("final_product_type");//产品类型
			contractMonths = resultSet.getString("contract_months");//批借期限
			loanStatusCode = resultSet.getString("dict_loan_status");//借款状态
			finalAuditAmount = resultSet.getDouble("final_audit_amount");//合同金额
			urgeMoeny = resultSet.getDouble("urge_moeny");//放款金额
			urgeDecuteMoeny = resultSet.getDouble("urge_decute_moeny");//划扣费用
			unUrgeDecuteMoeny = resultSet.getDouble("unUrgeDecuteMoeny");//未划金额
			unUrgeDecuteMoenyTwo = resultSet.getDouble("urge_decute_moeny");//申请查账金额
			reallyAmount = resultSet.getDouble("reallyAmount");//实还金额
			applyPayDay = resultSet.getString("applyPayDay");//查账日期
			matchingResult = resultSet.getString("matching_result");//回盘结果
			conditionalThroughFlag = DictCache.getInstance().getDictLabel("jk_car_loan_result",resultSet.getString("conditional_through_flag"));//附条件通过标识
			

			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			
			contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			loanCustomerNameCell = dataRow.createCell(2);
			loanCustomerNameCell.setCellValue(loanCustomerName);
			loanCustomerNameCell.setCellStyle(contentStyle);
			
			storeNameCell = dataRow.createCell(3);
			storeNameCell.setCellValue(storeName);
			storeNameCell.setCellStyle(contentStyle);
			
			creditMiDBankNameCell = dataRow.createCell(4);
			creditMiDBankNameCell.setCellValue(creditMiDBankName);
			creditMiDBankNameCell.setCellStyle(contentStyle);
			
			finalProductTypeCell = dataRow.createCell(5);
			finalProductTypeCell.setCellValue(finalProductType);
			finalProductTypeCell.setCellStyle(contentStyle);
			
			contractMonthsCell = dataRow.createCell(6);
			contractMonthsCell.setCellValue(contractMonths);
			contractMonthsCell.setCellStyle(contentStyle);
			
			loanStatusCodeCell = dataRow.createCell(7);
			loanStatusCodeCell.setCellValue(loanStatusCode);
			loanStatusCodeCell.setCellStyle(dateStyle);
			
			finalAuditAmountCell = dataRow.createCell(8);
			finalAuditAmountCell.setCellValue(finalAuditAmount);
			finalAuditAmountCell.setCellStyle(contentStyle);
			
			urgeMoenyCell = dataRow.createCell(9);
			urgeMoenyCell.setCellValue(urgeMoeny);
			urgeMoenyCell.setCellStyle(contentStyle);
			
			urgeDecuteMoenyCell = dataRow.createCell(10);
			urgeDecuteMoenyCell.setCellValue(urgeDecuteMoeny);
			urgeDecuteMoenyCell.setCellStyle(contentStyle);
			
			unUrgeDecuteMoenyCell = dataRow.createCell(11);
			unUrgeDecuteMoenyCell.setCellValue(unUrgeDecuteMoeny);
			unUrgeDecuteMoenyCell.setCellStyle(contentStyle);
			
			unUrgeDecuteMoenyTwoCell = dataRow.createCell(12);
			unUrgeDecuteMoenyTwoCell.setCellValue(unUrgeDecuteMoeny);
			unUrgeDecuteMoenyTwoCell.setCellStyle(contentStyle);
			
			
			reallyAmountCell = dataRow.createCell(13);
			reallyAmountCell.setCellValue(reallyAmount);
			reallyAmountCell.setCellStyle(contentStyle);
			
			applyPayDayCell = dataRow.createCell(14);
			applyPayDayCell.setCellValue(applyPayDay);
			applyPayDayCell.setCellStyle(contentStyle);
			
			matchingResultCell = dataRow.createCell(15);
			matchingResultCell.setCellValue(matchingResult);
			matchingResultCell.setCellStyle(contentStyle);
			
			conditionalThroughFlagCell = dataRow.createCell(16);
			conditionalThroughFlagCell.setCellValue(conditionalThroughFlag);
			conditionalThroughFlagCell.setCellStyle(contentStyle);
			num++;
			row = row + 1;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("催收服务费划扣成功列表",dataSheet,11);
		createTableDateRow(dataSheet,11);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell lendCodeHeader = headerRow.createCell(1);
		lendCodeHeader.setCellValue("合同编号");
		lendCodeHeader.setCellStyle(dateStyle);
		Cell accountNoHeader = headerRow.createCell(2);
		accountNoHeader.setCellStyle(dateStyle);
		accountNoHeader.setCellValue("客户姓名");
		Cell accountNameHeader = headerRow.createCell(3);
		accountNameHeader.setCellStyle(dateStyle);
		accountNameHeader.setCellValue("开户行");
		Cell backMoneyHeader = headerRow.createCell(4);
		backMoneyHeader.setCellStyle(dateStyle);
		backMoneyHeader.setCellValue("银行卡号");
		Cell bankCodeHeader = headerRow.createCell(5);
		bankCodeHeader.setCellStyle(dateStyle);
		bankCodeHeader.setCellValue("借款产品");
		Cell accountBankHeader = headerRow.createCell(6);
		accountBankHeader.setCellStyle(dateStyle);
		accountBankHeader.setCellValue("应划扣金额");
		Cell accountBranchHeader = headerRow.createCell(7);
		accountBranchHeader.setCellStyle(dateStyle);
		accountBranchHeader.setCellValue("划扣日期");
		Cell cardOrBookletHeader = headerRow.createCell(8);
		cardOrBookletHeader.setCellStyle(dateStyle);
		cardOrBookletHeader.setCellValue("回盘结果");
		Cell provinceHeader = headerRow.createCell(9);
		provinceHeader.setCellStyle(dateStyle);
		provinceHeader.setCellValue("回盘原因");
		Cell cityHeader = headerRow.createCell(10);
		cityHeader.setCellStyle(dateStyle);
		cityHeader.setCellValue("划扣平台");
		Cell headerCell = headerRow.createCell(11);
		headerCell.setCellStyle(dateStyle);
		headerCell.setCellValue("标识");
	}
	/**
	 * @author 陈伟丽
	 * @param dataSheet
	 */
private static void wrapperHeaderCheckDone(Sheet dataSheet) {
		
		createTableTitleRow("还款匹配已办列表",dataSheet,16);
		createTableDateRow(dataSheet,16);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		
		Cell lendCodeHeader = headerRow.createCell(1);
		lendCodeHeader.setCellValue("合同编号");
		lendCodeHeader.setCellStyle(dateStyle);
		Cell accountNoHeader = headerRow.createCell(2);
		accountNoHeader.setCellStyle(dateStyle);
		accountNoHeader.setCellValue("客户姓名");
		Cell accountNameHeader = headerRow.createCell(3);
		accountNameHeader.setCellStyle(dateStyle);
		accountNameHeader.setCellValue("门店名称");
		Cell backMoneyHeader = headerRow.createCell(4);
		backMoneyHeader.setCellStyle(dateStyle);
		backMoneyHeader.setCellValue("存入账户");
		Cell bankCodeHeader = headerRow.createCell(5);
		bankCodeHeader.setCellStyle(dateStyle);
		bankCodeHeader.setCellValue("借款产品");
		Cell accountBankHeader = headerRow.createCell(6);
		accountBankHeader.setCellStyle(dateStyle);
		accountBankHeader.setCellValue("批借期限");
		Cell accountBranchHeader = headerRow.createCell(7);
		accountBranchHeader.setCellStyle(dateStyle);
		accountBranchHeader.setCellValue("借款状态");
		Cell cardOrBookletHeader = headerRow.createCell(8);
		cardOrBookletHeader.setCellStyle(dateStyle);
		cardOrBookletHeader.setCellValue("合同金额");
		Cell provinceHeader = headerRow.createCell(9);
		provinceHeader.setCellStyle(dateStyle);
		provinceHeader.setCellValue("放款金额");
		Cell cityHeader = headerRow.createCell(10);
		cityHeader.setCellStyle(dateStyle);
		cityHeader.setCellValue("划扣费用");
		Cell headerCell = headerRow.createCell(11);
		headerCell.setCellStyle(dateStyle);
		headerCell.setCellValue("未划金额");
		Cell headerCellMoney = headerRow.createCell(12);
		headerCellMoney.setCellStyle(dateStyle);
		headerCellMoney.setCellValue("申请查账金额");
		Cell headerCellNo = headerRow.createCell(13);
		headerCellNo.setCellStyle(dateStyle);
		headerCellNo.setCellValue("实还金额");
		Cell headerCellDate = headerRow.createCell(14);
		headerCellDate.setCellStyle(dateStyle);
		headerCellDate.setCellValue("查账日期");
		Cell matchingCellResult = headerRow.createCell(15);
		matchingCellResult.setCellStyle(dateStyle);
		matchingCellResult.setCellValue("回盘结果");
		Cell headerCellDateFlag = headerRow.createCell(16);
		headerCellDateFlag.setCellStyle(dateStyle);
		headerCellDateFlag.setCellValue("标识");
		
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
