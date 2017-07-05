package com.creditharmony.loan.telesales.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanDepand;
import com.creditharmony.loan.telesales.view.TelesaleConsultSearchView;

/**
 * 电销数据导出列表确认
 * 
 * @Class Name ExportPosHelper
 * @author ghc
 * @Create In 2016年4月29日
 */
public class TelesalesHelper {

	private static Logger logger = LoggerFactory.getLogger(TelesalesHelper.class);
	private static SXSSFWorkbook wb;
	private static CellStyle titleStyle; // 标题行样式
	private static Font titleFont; // 标题行字体
	private static CellStyle dateStyle; // 日期行样式
	private static Font dateFont; // 日期行字体
	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体

	public static void exportData(TelesaleConsultSearchView consultView, HttpServletResponse response, String coutrollerStr) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			// 数据权限0-------------------------
			/*
			 * User user = UserUtils.getUser(); List<Role> roleList =
			 * user.getRoleList(); boolean isManager = false;
			 * //如果登录人是电销总监//或者电销现场经理的角色 则能看见所有的数据 if(!isManager){ isManager =
			 * LoanDepand.electricData(isManager,roleList); } if (!isManager)
			 * //如果是电销售团队主管的角色 则只能看见本团队的数据 { isManager =
			 * LoanDepand.electricDataMobile(isManager,roleList); if(isManager){
			 * //查询登陆人团队所有的单子
			 * consultView.setConsTelesalesOrgcode(user.getDepartment
			 * ().getId()); } } if (!isManager) //如果是电销录单专员则只能看自己的数据 { isManager
			 * = LoanDepand.electDataMobile(isManager,roleList); if (user !=
			 * null) { consultView.setTelesaleManCode(user.getId()); } }
			 */
			if ("exportLoanCleanExcel".equals(coutrollerStr)) {
				wrapperHeader_exportLoanCleanExcel(dataSheet, consultView.getTableName());
				String sqlStr = makeSQL(null, sqlSessionFactory, coutrollerStr);
				ResultSet resultSet = executeSQL(connection, sqlStr);
				assembleExcelCell_exportLoanCleanExcel(resultSet, dataSheet);
			} else if ("exportLoanNoSignExcel".equals(coutrollerStr)) {
				wrapperHeader_exportLoanNoSignExcel(dataSheet, consultView.getTableName());
				String sqlStr = makeSQL(null, sqlSessionFactory, coutrollerStr);
				ResultSet resultSet = executeSQL(connection, sqlStr);
				assembleExcelCell_exportLoanNoSignExcel(resultSet, dataSheet);
			} else if ("exportLoanAgainExcel".equals(coutrollerStr)) {
				// 同结清资源，相同字段，所以可共用方法
				wrapperHeader_exportLoanCleanExcel(dataSheet, consultView.getTableName());
				String sqlStr = makeSQL(null, sqlSessionFactory, coutrollerStr);
				ResultSet resultSet = executeSQL(connection, sqlStr);
				assembleExcelCell_exportLoanCleanExcel(resultSet, dataSheet);
			} else if ("exportLoanNoAuditExcel".equals(coutrollerStr)) {
				wrapperHeader_exportLoanNoAuditExcel(dataSheet, consultView.getTableName());
				String sqlStr = makeSQL(null, sqlSessionFactory, coutrollerStr);
				ResultSet resultSet = executeSQL(connection, sqlStr);
				assembleExcelCell_exportLoanNoAuditExcel(resultSet, dataSheet);
			} else if ("exportTelesaleCustomerListExcel".equals(coutrollerStr)) {
				wrapperHeader_exportTelesalesCustomerListExcel(dataSheet, consultView.getTableName());
				String sqlStr = makeSQL(consultView, sqlSessionFactory, coutrollerStr);
				ResultSet resultSet = executeSQL(connection, sqlStr);
				assembleExcelCell_TelesalesCustomerList(resultSet, dataSheet);
			} else {
				wrapperHeader(dataSheet, consultView.getTableName());
				String sqlStr = makeSQL(consultView, sqlSessionFactory, "exportTelesaleApplyLoanInfoList");
				ResultSet resultSet = executeSQL(connection, sqlStr);
				assembleExcelCellAll(resultSet, dataSheet);
			}
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(consultView.getTableName()) + ";filename*=UTF-8''" + Encodes.urlEncode(consultView.getTableName() + DateUtils.getDate("yyyyMMdd") + ".xlsx"));
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

	private static void assembleExcelCell(ResultSet resultSet, Sheet dataSheet) throws SQLException {

		int row = 3;

		String customerName; // 客户姓名
		String telesaleManName; // 电销专员
		String telesaleManCode; // 电销专员编号
		String telesaleTeamLeaderName; // 电销团队主管
		String telesaleSiteManagerName; // 电销现场经理
		String consTelesalesSourceName; // 电销来源名称
		String loanCode; // 借款编码
		String dictLoanStatusName; // 借款状态名称
		String coboName; // 共借人姓名
		String contractCode; // 合同编号
		String storeProviceName; // 门店所在省名称
		String storeCityName; // 门店所在市名称
		String productName; // 产品名称
		String loanUrgentFlag; // 是否加急
		String loanApplyAmount; // 申请金额
		String loanAuditAmount; // 批复金额
		String grantAmount; // 发放金额
		String creater; // 录单人员
		String customerIntoTime; // 进件时间
		String loanFlagName; // 标示名称
		String dictIsCycleName; // 循环借标识名称

		Row dataRow;

		Cell customerNameCell; // 客户姓名
		Cell storeNameCell; // 门店名称
		Cell telesaleManNameCell; // 电销专员
		Cell telesaleManCodeCell; // 电销专员编号
		Cell telesaleTeamLeaderNameCell; // 电销团队主管
		Cell telesaleSiteManagerNameCell; // 电销现场经理
		Cell consTelesalesSourceNameCell; // 电销来源名称
		Cell loanCodeCell; // 借款编码
		Cell dictLoanStatusNameCell; // 借款状态名称
		Cell coboNameCell; // 共借人姓名
		Cell contractCodeCell; // 合同编号
		Cell storeProviceNameCell; // 门店所在省名称
		Cell storeCityNameCell; // 门店所在市名称
		Cell productNameCell; // 产品名称
		Cell loanMothsCell; // 产品期限
		Cell loanUrgentFlagCell; // 是否加急
		Cell loanApplyAmountCell; // 申请金额
		Cell loanAuditAmountCell; // 批复金额
		Cell grantAmountCell; // 发放金额
		Cell createrCell; // 录单人员
		Cell loanSurveyEmpIdCell; // 外访人员
		Cell customerIntoTimeCell; // 进件时间
		Cell loanFlagNameCell; // 标示名称
		Cell dictIsCycleNameCell; // 循环借标识名称

		while (resultSet.next()) {

			customerName = resultSet.getString("customerName"); // 客户姓名
			telesaleManName = resultSet.getString("telesaleManName"); // 电销专员
			telesaleManCode = resultSet.getString("telesaleManCode"); // 电销专员编号
			telesaleTeamLeaderName = resultSet.getString("telesaleTeamLeaderName"); // 电销团队主管
			telesaleSiteManagerName = resultSet.getString("telesaleSiteManagerName"); // 电销现场经理
			consTelesalesSourceName = resultSet.getString("consTelesalesSourceName"); // 电销来源名称
			loanCode = resultSet.getString("loanCode"); // 借款编码
			dictLoanStatusName = resultSet.getString("dictLoanStatusName"); // 借款状态名称
			coboName = resultSet.getString("coboName"); // 共借人姓名
			contractCode = resultSet.getString("contractCode"); // 合同编号
			storeProviceName = resultSet.getString("storeProviceName"); // 门店所在省名称
			storeCityName = resultSet.getString("storeCityName"); // 门店所在市名称
			productName = resultSet.getString("productName"); // 产品名称
			loanUrgentFlag = resultSet.getString("loanUrgentFlag"); // 是否加急
			loanApplyAmount = resultSet.getString("loanApplyAmount"); // 申请金额
			loanAuditAmount = resultSet.getString("loanAuditAmount"); // 批复金额
			grantAmount = resultSet.getString("grantAmount"); // 发放金额
			creater = resultSet.getString("creater"); // 录单人员
			customerIntoTime = resultSet.getString("customerIntoTime"); // 进件时间
			loanFlagName = resultSet.getString("loanFlagName"); // 标示名称
			dictIsCycleName = resultSet.getString("dictIsCycleName"); // 循环借标识名称

			dataRow = dataSheet.createRow(row);
			// 借款编号
			loanCodeCell = dataRow.createCell(0);
			loanCodeCell.setCellValue(loanCode);
			loanCodeCell.setCellStyle(contentStyle);
			// 合同编号
			contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			// 共借人
			coboNameCell = dataRow.createCell(2);
			coboNameCell.setCellValue(coboName);
			coboNameCell.setCellStyle(contentStyle);
			// 客户姓名
			customerNameCell = dataRow.createCell(3);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			// 省份
			storeProviceNameCell = dataRow.createCell(4);
			storeProviceNameCell.setCellValue(storeProviceName);
			storeProviceNameCell.setCellStyle(contentStyle);
			// 所在市
			storeCityNameCell = dataRow.createCell(5);
			storeCityNameCell.setCellValue(storeCityName);
			storeCityNameCell.setCellStyle(contentStyle);
			// 门店
			storeNameCell = dataRow.createCell(6);
			storeNameCell.setCellValue(contractCode);
			storeNameCell.setCellStyle(contentStyle);
			// 状态
			dictLoanStatusNameCell = dataRow.createCell(7);
			dictLoanStatusNameCell.setCellValue(dictLoanStatusName);
			dictLoanStatusNameCell.setCellStyle(contentStyle);
			// 产品
			productNameCell = dataRow.createCell(8);
			productNameCell.setCellValue(productName);
			productNameCell.setCellStyle(contentStyle);
			// 是否加急
			loanUrgentFlagCell = dataRow.createCell(9);
			loanUrgentFlagCell.setCellValue(loanUrgentFlag);
			loanUrgentFlagCell.setCellStyle(contentStyle);
			// 申请金额
			loanApplyAmountCell = dataRow.createCell(10);
			loanApplyAmountCell.setCellValue(loanApplyAmount);
			loanApplyAmountCell.setCellStyle(contentStyle);
			// 批复金额
			loanAuditAmountCell = dataRow.createCell(11);
			loanAuditAmountCell.setCellValue(loanAuditAmount);
			loanAuditAmountCell.setCellStyle(contentStyle);
			// 发放金额
			grantAmountCell = dataRow.createCell(12);
			grantAmountCell.setCellValue(grantAmount);
			grantAmountCell.setCellStyle(contentStyle);
			// 产品期限
			loanMothsCell = dataRow.createCell(13);
			loanMothsCell.setCellValue(grantAmount);
			loanMothsCell.setCellStyle(contentStyle);
			// 电销来源
			consTelesalesSourceNameCell = dataRow.createCell(14);
			consTelesalesSourceNameCell.setCellValue(consTelesalesSourceName);
			consTelesalesSourceNameCell.setCellStyle(contentStyle);
			// 电销专员
			telesaleManNameCell = dataRow.createCell(15);
			telesaleManNameCell.setCellValue(telesaleManName);
			telesaleManNameCell.setCellStyle(contentStyle);
			// 电销专员编号
			telesaleManCodeCell = dataRow.createCell(16);
			telesaleManCodeCell.setCellValue(telesaleManCode);
			telesaleManCodeCell.setCellStyle(contentStyle);
			// 电销团队主管
			telesaleTeamLeaderNameCell = dataRow.createCell(17);
			telesaleTeamLeaderNameCell.setCellValue(telesaleTeamLeaderName);
			telesaleTeamLeaderNameCell.setCellStyle(contentStyle);
			// 电销售现场经理
			telesaleSiteManagerNameCell = dataRow.createCell(18);
			telesaleSiteManagerNameCell.setCellValue(telesaleSiteManagerName);
			telesaleSiteManagerNameCell.setCellStyle(contentStyle);
			// 录单人员
			createrCell = dataRow.createCell(19);
			createrCell.setCellValue(creater);
			createrCell.setCellStyle(contentStyle);
			// 外访人员
			loanSurveyEmpIdCell = dataRow.createCell(20);
			loanSurveyEmpIdCell.setCellValue(telesaleManName);
			loanSurveyEmpIdCell.setCellStyle(contentStyle);
			// 进件时间
			customerIntoTimeCell = dataRow.createCell(21);
			customerIntoTimeCell.setCellValue(customerIntoTime);
			customerIntoTimeCell.setCellStyle(contentStyle);
			// 标识
			loanFlagNameCell = dataRow.createCell(22);
			loanFlagNameCell.setCellValue(loanFlagName);
			loanFlagNameCell.setCellStyle(contentStyle);
			// 是否循环借
			dictIsCycleNameCell = dataRow.createCell(23);
			dictIsCycleNameCell.setCellValue(dictIsCycleName);
			dictIsCycleNameCell.setCellStyle(contentStyle);

			row++;
		}

	}

	private static void wrapperHeader(Sheet dataSheet, String tableName) {

		createTableTitleRow(tableName, dataSheet, 24);
		createTableDateRow(dataSheet, 24);
		Row headerRow = dataSheet.createRow(2);

		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("借款编号");
		Cell storeNameHeader = headerRow.createCell(1);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("合同编号");
		Cell customerNameHeader = headerRow.createCell(2);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("共借人/自然人保证人");
		Cell contractCodeHeader = headerRow.createCell(3);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("客户姓名");
		Cell bankHeader = headerRow.createCell(4);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("省份");
		Cell overDateHeader = headerRow.createCell(5);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("城市");
		Cell payAmountMoneyHeader = headerRow.createCell(6);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("门店");
		Cell customerMobilePhoneHeader = headerRow.createCell(7);
		customerMobilePhoneHeader.setCellStyle(dateStyle);
		customerMobilePhoneHeader.setCellValue("手机号");
		Cell overDayHeader = headerRow.createCell(8);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("状态");
		Cell overDayHeaderS = headerRow.createCell(9);
		overDayHeaderS.setCellStyle(dateStyle);
		overDayHeaderS.setCellValue("产品");
		Cell overDayHeaderY = headerRow.createCell(10);
		overDayHeaderY.setCellStyle(dateStyle);
		overDayHeaderY.setCellValue("是否加急");
		Cell overDayHeaderJe = headerRow.createCell(11);
		overDayHeaderJe.setCellStyle(dateStyle);
		overDayHeaderJe.setCellValue("申请金额");
		Cell overDayHeaderPf = headerRow.createCell(12);
		overDayHeaderPf.setCellStyle(dateStyle);
		overDayHeaderPf.setCellValue("批复金额");
		Cell overDayHeaderFy = headerRow.createCell(13);
		overDayHeaderFy.setCellStyle(dateStyle);
		overDayHeaderFy.setCellValue("发放金额");
		Cell overDayHeaderCp = headerRow.createCell(14);
		overDayHeaderCp.setCellStyle(dateStyle);
		overDayHeaderCp.setCellValue("产品期限");
		Cell overDayHeaderLy = headerRow.createCell(15);
		overDayHeaderLy.setCellStyle(dateStyle);
		overDayHeaderLy.setCellValue("电销来源");
		Cell overDayHeaderZy = headerRow.createCell(16);
		overDayHeaderZy.setCellStyle(dateStyle);
		overDayHeaderZy.setCellValue("电销专员");
		Cell overDayHeaderBh = headerRow.createCell(17);
		overDayHeaderBh.setCellStyle(dateStyle);
		overDayHeaderBh.setCellValue("电销专员编号");
		Cell overDayHeaderZg = headerRow.createCell(18);
		overDayHeaderZg.setCellStyle(dateStyle);
		overDayHeaderZg.setCellValue("电销团队主管");
		Cell overDayHeaderXc = headerRow.createCell(19);
		overDayHeaderXc.setCellStyle(dateStyle);
		overDayHeaderXc.setCellValue("电销现场经理");
		Cell overDayHeaderLd = headerRow.createCell(20);
		overDayHeaderLd.setCellStyle(dateStyle);
		overDayHeaderLd.setCellValue("录单人员");
		Cell overDayHeaderRy = headerRow.createCell(21);
		overDayHeaderRy.setCellStyle(dateStyle);
		overDayHeaderRy.setCellValue("外访人员");
		Cell overDayHeaderSj = headerRow.createCell(22);
		overDayHeaderSj.setCellStyle(dateStyle);
		overDayHeaderSj.setCellValue("进件时间");
		Cell overDayHeaderEe = headerRow.createCell(23);
		overDayHeaderEe.setCellStyle(dateStyle);
		overDayHeaderEe.setCellValue("标识");
		Cell overDayHeaderSf = headerRow.createCell(24);
		overDayHeaderSf.setCellStyle(dateStyle);
		overDayHeaderSf.setCellValue("是否循环借");
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
	private static void createTableTitleRow(String title, Sheet dataSheet, int sheetNum) {
		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, sheetNum);
		dataSheet.addMergedRegion(titleRange);
		Row titleRow = dataSheet.createRow(0);
		titleRow.setHeight((short) 800);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(title);
	}

	/**
	 * @Description: 创建日期行(需合并单元格)
	 */
	private static void createTableDateRow(Sheet sheets, int sheetNum) {
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, sheetNum);
		sheets.addMergedRegion(dateRange);
		Row dateRow = sheets.createRow(1);
		dateRow.setHeight((short) 350);
		Cell dateCell = dateRow.createCell(0);
		dateCell.setCellStyle(dateStyle);
		// dateCell.setCellValue("导出时间：" + new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		// .format(new Date()));
		dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}

	/**
	 * 设置导出数据
	 * 
	 * @param list
	 * @param dataSheet
	 */
	public static void setFyDataList(List<PaybackSplitFyEx> list, Sheet dataSheet) {
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

		for (PaybackSplitFyEx e : list) {

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

	public static BigDecimal stringForBig(String monthMoney) {
		BigDecimal bgSum = new BigDecimal("0.00");
		if (StringUtils.isNotEmpty(monthMoney)) {
			BigDecimal bigMoney = new BigDecimal(monthMoney);
			return bigMoney;
		} else {
			return bgSum;
		}
	}

	private static void assembleExcelCellAll(ResultSet resultSet, Sheet dataSheet) throws SQLException {

		int row = 3;

		String customerName; // 客户姓名
		String telesaleManName; // 电销专员
		String telesaleManCode; // 电销专员编号
		String telesaleTeamLeaderName; // 电销团队主管
		String telesaleSiteManagerName; // 电销现场经理
		String consTelesalesSourceName; // 电销来源名称
		String loanCode; // 借款编码
		String dictLoanStatusName; // 借款状态名称
		String coboName; // 共借人姓名
		String contractCode; // 合同编号
		String storeProviceName; // 门店所在省名称
		String storeCityName; // 门店所在市名称
		String storeName;// 门店名称
		String customerMobilePhone;// 手机号
		String productName; // 产品名称
		String loanUrgentFlag; // 是否加急
		String loanApplyAmount; // 申请金额
		String loanAuditAmount; // 批复金额
		String grantAmount; // 发放金额
		String creater; // 录单人员
		String customerIntoTime; // 进件时间
		String loanFlagName; // 标示名称
		String dictIsCycleName; // 循环借标识名称

		Row dataRow;

		Cell customerNameCell; // 客户姓名
		Cell storeNameCell; // 门店名称
		Cell telesaleManNameCell; // 电销专员
		Cell telesaleManCodeCell; // 电销专员编号
		Cell telesaleTeamLeaderNameCell; // 电销团队主管
		Cell telesaleSiteManagerNameCell; // 电销现场经理
		Cell consTelesalesSourceNameCell; // 电销来源名称
		Cell loanCodeCell; // 借款编码
		Cell dictLoanStatusNameCell; // 借款状态名称
		Cell coboNameCell; // 共借人姓名
		Cell contractCodeCell; // 合同编号
		Cell storeProviceNameCell; // 门店所在省名称
		Cell storeCityNameCell; // 门店所在市名称
		Cell productNameCell; // 产品名称
		Cell loanMothsCell; // 产品期限
		Cell loanUrgentFlagCell; // 是否加急
		Cell loanApplyAmountCell; // 申请金额
		Cell loanAuditAmountCell; // 批复金额
		Cell grantAmountCell; // 发放金额
		Cell createrCell; // 录单人员
		Cell loanSurveyEmpIdCell; // 外访人员
		Cell customerIntoTimeCell; // 进件时间
		Cell loanFlagNameCell; // 标示名称
		Cell dictIsCycleNameCell; // 循环借标识名称
		Cell customerMobilePhoneCell;// 手机号
		while (resultSet.next()) {

			customerName = resultSet.getString("customerName"); // 客户姓名
			telesaleManName = resultSet.getString("telesaleManName"); // 电销专员
			telesaleManCode = resultSet.getString("telesaleManCode"); // 电销专员编号
			telesaleTeamLeaderName = resultSet.getString("telesaleTeamLeaderName"); // 电销团队主管
			telesaleSiteManagerName = resultSet.getString("telesaleSiteManagerName"); // 电销现场经理
			consTelesalesSourceName = resultSet.getString("consTelesalesSourceName"); // 电销来源名称
			loanCode = resultSet.getString("loanCode"); // 借款编码
			dictLoanStatusName = resultSet.getString("dictLoanStatusName"); // 借款状态名称
			coboName = resultSet.getString("coboName"); // 共借人姓名
			contractCode = resultSet.getString("contractCode"); // 合同编号
			storeProviceName = resultSet.getString("storeProviceName"); // 门店所在省名称
			storeCityName = resultSet.getString("storeCityName"); // 门店所在市名称
			productName = resultSet.getString("productName"); // 产品名称
			loanUrgentFlag = resultSet.getString("loanUrgentFlag"); // 是否加急
			loanApplyAmount = resultSet.getString("loanApplyAmount"); // 申请金额
			loanAuditAmount = resultSet.getString("loanAuditAmount"); // 批复金额
			grantAmount = resultSet.getString("grantAmount"); // 发放金额
			creater = resultSet.getString("creater"); // 录单人员
			customerIntoTime = resultSet.getString("customerIntoTime"); // 进件时间
			loanFlagName = resultSet.getString("loanFlagName"); // 标示名称
			dictIsCycleName = resultSet.getString("dictIsCycleName"); // 循环借标识名称
			storeName = resultSet.getString("storeName");
			customerMobilePhone = resultSet.getString("customerMobilePhone");
			dataRow = dataSheet.createRow(row);
			// 借款编号
			loanCodeCell = dataRow.createCell(0);
			loanCodeCell.setCellValue(loanCode);
			loanCodeCell.setCellStyle(contentStyle);
			// 合同编号
			contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			// 共借人
			coboNameCell = dataRow.createCell(2);
			coboNameCell.setCellValue(coboName);
			coboNameCell.setCellStyle(contentStyle);
			// 客户姓名
			customerNameCell = dataRow.createCell(3);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			// 省份
			storeProviceNameCell = dataRow.createCell(4);
			storeProviceNameCell.setCellValue(storeProviceName);
			storeProviceNameCell.setCellStyle(contentStyle);
			// 所在市
			storeCityNameCell = dataRow.createCell(5);
			storeCityNameCell.setCellValue(storeCityName);
			storeCityNameCell.setCellStyle(contentStyle);
			// 门店
			storeNameCell = dataRow.createCell(6);
			storeNameCell.setCellValue(storeName);
			storeNameCell.setCellStyle(contentStyle);
			// 手机号
			customerMobilePhoneCell = dataRow.createCell(7);
			// customerMobilePhoneCell.setCellValue(customerMobilePhone);
			customerMobilePhoneCell.setCellStyle(contentStyle);
			// 状态
			dictLoanStatusNameCell = dataRow.createCell(8);
			dictLoanStatusNameCell.setCellValue(dictLoanStatusName);
			dictLoanStatusNameCell.setCellStyle(contentStyle);
			// 产品
			productNameCell = dataRow.createCell(9);
			productNameCell.setCellValue(productName);
			productNameCell.setCellStyle(contentStyle);
			// 是否加急
			loanUrgentFlagCell = dataRow.createCell(10);
			loanUrgentFlagCell.setCellValue(loanUrgentFlag);
			loanUrgentFlagCell.setCellStyle(contentStyle);
			// 申请金额
			loanApplyAmountCell = dataRow.createCell(11);
			loanApplyAmountCell.setCellValue(loanApplyAmount);
			loanApplyAmountCell.setCellStyle(contentStyle);
			// 批复金额
			loanAuditAmountCell = dataRow.createCell(12);
			loanAuditAmountCell.setCellValue(loanAuditAmount);
			loanAuditAmountCell.setCellStyle(contentStyle);
			// 发放金额
			grantAmountCell = dataRow.createCell(13);
			grantAmountCell.setCellValue(grantAmount);
			grantAmountCell.setCellStyle(contentStyle);
			// 产品期限
			loanMothsCell = dataRow.createCell(14);
			loanMothsCell.setCellValue(grantAmount);
			loanMothsCell.setCellStyle(contentStyle);
			// 电销来源
			consTelesalesSourceNameCell = dataRow.createCell(15);
			consTelesalesSourceNameCell.setCellValue(consTelesalesSourceName);
			consTelesalesSourceNameCell.setCellStyle(contentStyle);
			// 电销专员
			telesaleManNameCell = dataRow.createCell(16);
			telesaleManNameCell.setCellValue(telesaleManName);
			telesaleManNameCell.setCellStyle(contentStyle);
			// 电销专员编号
			telesaleManCodeCell = dataRow.createCell(17);
			telesaleManCodeCell.setCellValue(telesaleManCode);
			telesaleManCodeCell.setCellStyle(contentStyle);
			// 电销团队主管
			telesaleTeamLeaderNameCell = dataRow.createCell(18);
			telesaleTeamLeaderNameCell.setCellValue(telesaleTeamLeaderName);
			telesaleTeamLeaderNameCell.setCellStyle(contentStyle);
			// 电销售现场经理
			telesaleSiteManagerNameCell = dataRow.createCell(19);
			telesaleSiteManagerNameCell.setCellValue(telesaleSiteManagerName);
			telesaleSiteManagerNameCell.setCellStyle(contentStyle);
			// 录单人员
			createrCell = dataRow.createCell(20);
			createrCell.setCellValue(creater);
			createrCell.setCellStyle(contentStyle);
			// 外访人员
			loanSurveyEmpIdCell = dataRow.createCell(21);
			loanSurveyEmpIdCell.setCellValue(telesaleManName);
			loanSurveyEmpIdCell.setCellStyle(contentStyle);
			// 进件时间
			customerIntoTimeCell = dataRow.createCell(22);
			customerIntoTimeCell.setCellValue(customerIntoTime);
			customerIntoTimeCell.setCellStyle(contentStyle);
			// 标识
			loanFlagNameCell = dataRow.createCell(23);
			loanFlagNameCell.setCellValue(loanFlagName);
			loanFlagNameCell.setCellStyle(contentStyle);
			// 是否循环借
			dictIsCycleNameCell = dataRow.createCell(24);
			dictIsCycleNameCell.setCellValue(dictIsCycleName);
			dictIsCycleNameCell.setCellStyle(contentStyle);
			row++;
		}

	}

	private static void assembleExcelCell_exportLoanCleanExcel(ResultSet resultSet, Sheet dataSheet) throws SQLException {
		int row = 3;
		String id;
		String customerName; // 客户姓名
		String customerMobilePhone; // 手机号
		String customerMobilePhone2; // 手机号
		String mateCertNum; // 身份证号
		String gender; // 性别
		String repayStatus; // 当前状态
		String settlementDate; // 结清时间
		String auditAmount;// 最近的历史借款金额,
		String storeProviceName; // 门店所在省名称
		String storeCityName; // 门店所在市名称
		String storeName;
		Row dataRow;
		Cell idCell;
		Cell customerNameCell; // 客户姓名
		Cell customerMobilePhoneCell; // 手机号
		Cell customerMobilePhone2Cell; // 手机号
		Cell mateCertNumCell; // 身份证号
		Cell genderCell; // 性别
		Cell repayStatusCell; // 当前状态
		Cell settlementDateCell; // 结清时间
		Cell auditAmountCell;// 最近的历史借款金额,
		Cell storeProviceNameCell; // 门店所在省名称
		Cell storeCityNameCell; // 门店所在市名称
		Cell storeNameCell;// 门店名称
		while (resultSet.next()) {
			id = resultSet.getString("id");
			customerName = resultSet.getString("customerName");
			customerMobilePhone = resultSet.getString("customerMobilePhone");
			customerMobilePhone2 = resultSet.getString("customerMobilePhone2");
			mateCertNum = resultSet.getString("mateCertNum");
			gender = resultSet.getString("gender");
			repayStatus = resultSet.getString("repayStatus");
			settlementDate = resultSet.getString("settlementDate");
			auditAmount = resultSet.getString("auditAmount");
			storeProviceName = resultSet.getString("storeProviceName");
			storeCityName = resultSet.getString("storeCityName");
			storeCityName = resultSet.getString("storeCityName");
			storeName = resultSet.getString("storeName");
			dataRow = dataSheet.createRow(row);
			idCell = dataRow.createCell(0);
			idCell.setCellValue(id);
			idCell.setCellStyle(contentStyle);
			// 客户姓名
			customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			// 手机号
			customerMobilePhoneCell = dataRow.createCell(2);
			// customerMobilePhoneCell.setCellValue(customerMobilePhone);
			customerMobilePhoneCell.setCellStyle(contentStyle);
			// 身份证号
			mateCertNumCell = dataRow.createCell(3);
			// mateCertNumCell.setCellValue(mateCertNum);
			mateCertNumCell.setCellStyle(contentStyle);
			// 性别
			genderCell = dataRow.createCell(4);
			genderCell.setCellValue(gender);
			genderCell.setCellStyle(contentStyle);
			// 当前状态
			repayStatusCell = dataRow.createCell(5);
			repayStatusCell.setCellValue(repayStatus);
			repayStatusCell.setCellStyle(contentStyle);
			// 结清时间
			settlementDateCell = dataRow.createCell(6);
			settlementDateCell.setCellValue(settlementDate);
			settlementDateCell.setCellStyle(contentStyle);
			// 最近的历史借款金额,
			auditAmountCell = dataRow.createCell(7);
			auditAmountCell.setCellValue(auditAmount);
			auditAmountCell.setCellStyle(contentStyle);
			// 省份
			storeProviceNameCell = dataRow.createCell(8);
			storeProviceNameCell.setCellValue(storeProviceName);
			storeProviceNameCell.setCellStyle(contentStyle);
			// 所在市
			storeCityNameCell = dataRow.createCell(9);
			storeCityNameCell.setCellValue(storeCityName);
			storeCityNameCell.setCellStyle(contentStyle);
			// 门店名称
			storeNameCell = dataRow.createCell(10);
			storeNameCell.setCellValue(storeName);
			storeNameCell.setCellStyle(contentStyle);
			row++;
		}

	}

	public static String makeSQL(TelesaleConsultSearchView consultView, SqlSessionFactory sqlSessionFactory, String mappingSql) {
		String sqlStr = "";
		try {
			MyBatisSql batisSql = MyBatisSqlUtil.getMyBatisSql("com.creditharmony.loan.telesales.dao.TelesalesCustomerManagementDAO." + mappingSql, consultView, sqlSessionFactory);
			sqlStr = batisSql.toString();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlStr;
	}

	public static ResultSet executeSQL(Connection connection, String sqlStr) {
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			ps = connection.prepareStatement(sqlStr);
			resultSet = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	private static void wrapperHeader_exportLoanCleanExcel(Sheet dataSheet, String tableName) {

		createTableTitleRow(tableName, dataSheet, 10);
		createTableDateRow(dataSheet, 10);
		Row headerRow = dataSheet.createRow(2);
		
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell customerHeader = headerRow.createCell(1);
		customerHeader.setCellStyle(dateStyle);
		customerHeader.setCellValue("客户名称");
		Cell storeNameHeader = headerRow.createCell(2);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("手机号");
		Cell customerNameHeader = headerRow.createCell(3);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("身份证号");
		Cell contractCodeHeader = headerRow.createCell(4);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("性别");
		Cell bankHeader = headerRow.createCell(5);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("当前状态");
		Cell overDateHeader = headerRow.createCell(6);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("结清时间");
		Cell payAmountMoneyHeader = headerRow.createCell(7);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("历史借款金额");
		Cell overDayHeader = headerRow.createCell(8);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("省份");
		Cell overDayHeaderS = headerRow.createCell(9);
		overDayHeaderS.setCellStyle(dateStyle);
		overDayHeaderS.setCellValue("城市");
		Cell overDayHeaderY = headerRow.createCell(10);
		overDayHeaderY.setCellStyle(dateStyle);
		overDayHeaderY.setCellValue("门店");
	}

	private static void wrapperHeader_exportLoanNoSignExcel(Sheet dataSheet, String tableName) {

		createTableTitleRow(tableName, dataSheet, 11);
		createTableDateRow(dataSheet, 11);
		Row headerRow = dataSheet.createRow(2);
		Cell customerHeader = headerRow.createCell(0);
		customerHeader.setCellStyle(dateStyle);
		customerHeader.setCellValue("序号");
		Cell numHeader = headerRow.createCell(1);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("客户姓名");
		Cell storeNameHeader = headerRow.createCell(2);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("手机号");
		Cell customerNameHeader = headerRow.createCell(3);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("身份证号");
		Cell contractCodeHeader = headerRow.createCell(4);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("性别");
		Cell bankHeader = headerRow.createCell(5);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("当前状态");
		Cell overDateHeader = headerRow.createCell(6);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("结清时间");
		Cell payAmountMoneyHeader = headerRow.createCell(7);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("历史借款金额");
		Cell overDayHeader = headerRow.createCell(8);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("省份");
		Cell overDayHeaderS = headerRow.createCell(9);
		overDayHeaderS.setCellStyle(dateStyle);
		overDayHeaderS.setCellValue("城市");
		Cell overDayHeaderY = headerRow.createCell(10);
		overDayHeaderY.setCellStyle(dateStyle);
		overDayHeaderY.setCellValue("门店");
		Cell noSignTime = headerRow.createCell(11);
		noSignTime.setCellStyle(dateStyle);
		noSignTime.setCellValue("不签约时间");

	}

	private static void assembleExcelCell_exportLoanNoSignExcel(ResultSet resultSet, Sheet dataSheet) throws SQLException {
		int row = 3;
		String id;
		String customerName; // 客户姓名
		String customerMobilePhone; // 手机号
		String customerMobilePhone2; // 手机号
		String mateCertNum; // 身份证号
		String gender; // 性别
		String repayStatus; // 当前状态
		String settlementDate; // 结清时间
		String auditAmount;// 最近的历史借款金额,
		String storeProviceName; // 门店所在省名称
		String storeCityName; // 门店所在市名称
		String storeName;// 门店名称
		String noSignTime;// 不签约时间
		Row dataRow;
		Cell idCell;
		Cell customerNameCell; // 客户姓名
		Cell customerMobilePhoneCell; // 手机号
		Cell customerMobilePhone2Cell; // 手机号
		Cell mateCertNumCell; // 身份证号
		Cell genderCell; // 性别
		Cell repayStatusCell; // 当前状态
		Cell settlementDateCell; // 结清时间
		Cell auditAmountCell;// 最近的历史借款金额,
		Cell storeProviceNameCell; // 门店所在省名称
		Cell storeCityNameCell; // 门店所在市名称
		Cell storeNameCell;// 门店名称
		Cell noSignTimeCell;
		while (resultSet.next()) {
			id = resultSet.getString("id");
			customerName = resultSet.getString("customerName");
			customerMobilePhone = resultSet.getString("customerMobilePhone");
			customerMobilePhone2 = resultSet.getString("customerMobilePhone2");
			mateCertNum = resultSet.getString("mateCertNum");
			gender = resultSet.getString("gender");
			repayStatus = resultSet.getString("repayStatus");
			settlementDate = resultSet.getString("settlementDate");
			auditAmount = resultSet.getString("auditAmount");
			storeProviceName = resultSet.getString("storeProviceName");
			storeCityName = resultSet.getString("storeCityName");
			storeProviceName = resultSet.getString("storeProviceName");
			storeCityName = resultSet.getString("storeCityName");
			storeName = resultSet.getString("storeName");
			noSignTime = resultSet.getString("noSignTime");
			dataRow = dataSheet.createRow(row);
			idCell = dataRow.createCell(0);
			idCell.setCellValue(id);
			idCell.setCellStyle(contentStyle);
			// 客户姓名
			customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			// 手机号
			customerMobilePhoneCell = dataRow.createCell(2);
			// customerMobilePhoneCell.setCellValue(customerMobilePhone);
			customerMobilePhoneCell.setCellStyle(contentStyle);
			// 身份证号
			mateCertNumCell = dataRow.createCell(3);
			// mateCertNumCell.setCellValue(mateCertNum);
			mateCertNumCell.setCellStyle(contentStyle);
			// 性别
			genderCell = dataRow.createCell(4);
			genderCell.setCellValue(gender);
			genderCell.setCellStyle(contentStyle);
			// 当前状态
			repayStatusCell = dataRow.createCell(5);
			repayStatusCell.setCellValue(repayStatus);
			repayStatusCell.setCellStyle(contentStyle);
			// 结清时间
			settlementDateCell = dataRow.createCell(6);
			settlementDateCell.setCellValue(settlementDate);
			settlementDateCell.setCellStyle(contentStyle);
			// 最近的历史借款金额,
			auditAmountCell = dataRow.createCell(7);
			auditAmountCell.setCellValue(auditAmount);
			auditAmountCell.setCellStyle(contentStyle);
			// 省份
			storeProviceNameCell = dataRow.createCell(8);
			storeProviceNameCell.setCellValue(storeProviceName);
			storeProviceNameCell.setCellStyle(contentStyle);
			// 所在市
			storeCityNameCell = dataRow.createCell(9);
			storeCityNameCell.setCellValue(storeCityName);
			storeCityNameCell.setCellStyle(contentStyle);
			// 门店名称
			storeNameCell = dataRow.createCell(10);
			storeNameCell.setCellValue(storeName);
			storeNameCell.setCellStyle(contentStyle);
			// 不签约时间
			noSignTimeCell = dataRow.createCell(11);
			noSignTimeCell.setCellValue(noSignTime);
			noSignTimeCell.setCellStyle(contentStyle);
			row++;
		}
	}

	private static void wrapperHeader_exportLoanNoAuditExcel(Sheet dataSheet, String tableName) {

		createTableTitleRow(tableName, dataSheet, 6);
		createTableDateRow(dataSheet, 6);
		Row headerRow = dataSheet.createRow(2);
		/*Cell idHeader = headerRow.createCell(0);
		idHeader.setCellStyle(dateStyle);
		idHeader.setCellValue("序号");*/
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("客户姓名");
		Cell storeNameHeader = headerRow.createCell(1);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("手机号");
		Cell customerNameHeader = headerRow.createCell(2);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("身份证号");
		Cell contractCodeHeader = headerRow.createCell(3);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("性别");
		Cell bankHeader = headerRow.createCell(4);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("当前状态");
		Cell overDateHeader = headerRow.createCell(5);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("结清时间");
		Cell payAmountMoneyHeader = headerRow.createCell(6);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("历史借款金额");
	}

	private static void assembleExcelCell_exportLoanNoAuditExcel(ResultSet resultSet, Sheet dataSheet) throws SQLException {
		int row = 3;
		String customerName; // 客户姓名
		String customerMobilePhone; // 手机号
		String customerMobilePhone2; // 手机号
		String mateCertNum; // 身份证号
		String gender; // 性别
		String repayStatus; // 当前状态
		String settlementDate; // 结清时间
		String auditAmount;// 最近的历史借款金额,
		Row dataRow;
		Cell customerNameCell; // 客户姓名
		Cell customerMobilePhoneCell; // 手机号
		Cell customerMobilePhone2Cell; // 手机号
		Cell mateCertNumCell; // 身份证号
		Cell genderCell; // 性别
		Cell repayStatusCell; // 当前状态
		Cell settlementDateCell; // 结清时间
		Cell auditAmountCell;// 最近的历史借款金额,
		while (resultSet.next()) {
			customerName = resultSet.getString("customerName");
			customerMobilePhone = resultSet.getString("customerMobilePhone");
			customerMobilePhone2 = resultSet.getString("customerMobilePhone2");
			mateCertNum = resultSet.getString("mateCertNum");
			gender = resultSet.getString("gender");
			repayStatus = resultSet.getString("repayStatus");
			settlementDate = resultSet.getString("settlementDate");
			auditAmount = resultSet.getString("auditAmount");
			dataRow = dataSheet.createRow(row);
			// 客户姓名
			customerNameCell = dataRow.createCell(0);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			// 手机号
			customerMobilePhoneCell = dataRow.createCell(1);
			// customerMobilePhoneCell.setCellValue(customerMobilePhone);
			customerMobilePhoneCell.setCellStyle(contentStyle);
			// 身份证号
			mateCertNumCell = dataRow.createCell(2);
			//mateCertNumCell.setCellValue(mateCertNum);
			mateCertNumCell.setCellStyle(contentStyle);
			// 性别
			genderCell = dataRow.createCell(3);
			genderCell.setCellValue(gender);
			genderCell.setCellStyle(contentStyle);
			// 当前状态
			repayStatusCell = dataRow.createCell(4);
			repayStatusCell.setCellValue(repayStatus);
			repayStatusCell.setCellStyle(contentStyle);
			// 结清时间
			settlementDateCell = dataRow.createCell(5);
			settlementDateCell.setCellValue(settlementDate);
			settlementDateCell.setCellStyle(contentStyle);
			// 最近的历史借款金额,
			auditAmountCell = dataRow.createCell(6);
			auditAmountCell.setCellValue(auditAmount);
			auditAmountCell.setCellStyle(contentStyle);
			row++;
		}
	}

	private static void wrapperHeader_exportTelesalesCustomerListExcel(Sheet dataSheet, String tableName) {

		createTableTitleRow(tableName, dataSheet, 14);
		createTableDateRow(dataSheet, 14);
		Row headerRow = dataSheet.createRow(2);

		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell storeNameHeader = headerRow.createCell(1);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("电销来源");
		Cell customerCodeHeader = headerRow.createCell(2);
		customerCodeHeader.setCellStyle(dateStyle);
		customerCodeHeader.setCellValue("客户编号");
		Cell customerNameHeader = headerRow.createCell(3);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("客户姓名");
		Cell contractCodeHeader = headerRow.createCell(4);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("手机号");
		Cell bankHeader = headerRow.createCell(5);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("身份证号");
		Cell overDateHeader = headerRow.createCell(6);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("门店名称");
		Cell payAmountMoneyHeader = headerRow.createCell(7);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("咨询创始时间");
		Cell customerMobilePhoneHeader = headerRow.createCell(8);
		customerMobilePhoneHeader.setCellStyle(dateStyle);
		customerMobilePhoneHeader.setCellValue("沟通记录");
		Cell overDayHeader = headerRow.createCell(9);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("客服人员");
		Cell overDayHeaderS = headerRow.createCell(10);
		overDayHeaderS.setCellStyle(dateStyle);
		overDayHeaderS.setCellValue("电销专员");
		Cell overDayHeaderY = headerRow.createCell(11);
		overDayHeaderY.setCellStyle(dateStyle);
		overDayHeaderY.setCellValue("电销编号");
		Cell overDayHeaderJe = headerRow.createCell(12);
		overDayHeaderJe.setCellStyle(dateStyle);
		overDayHeaderJe.setCellValue("电销团队主管");
		Cell overDayHeaderPf = headerRow.createCell(13);
		overDayHeaderPf.setCellStyle(dateStyle);
		overDayHeaderPf.setCellValue("电销现场经理");
		Cell overDayHeaderFy = headerRow.createCell(14);
		overDayHeaderFy.setCellStyle(dateStyle);
		overDayHeaderFy.setCellValue("咨询状态");
	}

	private static void assembleExcelCell_TelesalesCustomerList(ResultSet resultSet, Sheet dataSheet) throws SQLException {
		int row = 3;
		String id; // 序号
		String consTelesalesSourceName; // 电销来源
		String customerCode; // 客户编号
		String customerName;// 客户姓名
		String customerMobilePhone; // 手机号
		String mateCertNum; // 身份证号
		String storeName; // 门店名称
		String createTime; // 咨询创始时间
		String consLoanRecord; // 沟通记录
		String consServiceUserName; // 客服人员
		String telesaleManName; // 电销专员
		String telesaleManCode; // 电销编号
		String telesaleTeamLeaderName; // 电销团队主管
		String telesaleSiteManagerName;// 电销现场经理
		String dictOperStatusName;// 咨询状态
		Row dataRow;
		Cell idCell;
		Cell consTelesalesSourceNameCell; // 电销来源
		Cell customerCodeCell; // 客户编号
		Cell customerNameCell;// 客户姓名
		Cell customerMobilePhoneCell; // 手机号
		Cell mateCertNumCell; // 身份证号
		Cell storeNameCell; // 门店名称
		Cell createTimeCell; // 咨询创始时间
		Cell consLoanRecordCell; // 沟通记录
		Cell consServiceUserNameCell; // 客服人员
		Cell telesaleManNameCell; // 电销专员
		Cell telesaleManCodeCell; // 电销编号;
		Cell telesaleTeamLeaderNameCell; // 电销团队主管
		Cell telesaleSiteManagerNameCell;// 电销现场经理
		Cell dictOperStatusNameCell;// 咨询状态
		while (resultSet.next()) {
			id = resultSet.getString("id");
			consTelesalesSourceName = resultSet.getString("consTelesalesSourceName");
			customerCode = resultSet.getString("customerCode");
			customerName = resultSet.getString("customerName");
			customerMobilePhone = resultSet.getString("customerMobilePhone");
			mateCertNum = resultSet.getString("mateCertNum");
			storeName = resultSet.getString("storeName");
			createTime = resultSet.getString("createTime");
			consLoanRecord = resultSet.getString("consLoanRecord");
			consServiceUserName = resultSet.getString("consServiceUserName");
			telesaleManName = resultSet.getString("telesaleManName");
			telesaleManCode = resultSet.getString("telesaleManCode");
			telesaleTeamLeaderName = resultSet.getString("telesaleTeamLeaderName");
			telesaleSiteManagerName = resultSet.getString("telesaleSiteManagerName");
			dictOperStatusName = resultSet.getString("dictOperStatusName");
			dataRow = dataSheet.createRow(row);

			idCell = dataRow.createCell(0);
			idCell.setCellValue(id);
			idCell.setCellStyle(contentStyle);

			consTelesalesSourceNameCell = dataRow.createCell(1);
			consTelesalesSourceNameCell.setCellValue(consTelesalesSourceName);
			consTelesalesSourceNameCell.setCellStyle(contentStyle);

			customerCodeCell = dataRow.createCell(2);
			customerCodeCell.setCellValue(customerCode);
			customerCodeCell.setCellStyle(contentStyle);

			customerNameCell = dataRow.createCell(3);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);

			customerMobilePhoneCell = dataRow.createCell(4);
			//customerMobilePhoneCell.setCellValue(customerMobilePhone);
			customerMobilePhoneCell.setCellStyle(contentStyle);

			mateCertNumCell = dataRow.createCell(5);
			//mateCertNumCell.setCellValue(mateCertNum);
			mateCertNumCell.setCellStyle(contentStyle);

			storeNameCell = dataRow.createCell(6);
			storeNameCell.setCellValue(storeName);
			storeNameCell.setCellStyle(contentStyle);

			createTimeCell = dataRow.createCell(7);
			createTimeCell.setCellValue(createTime);
			createTimeCell.setCellStyle(contentStyle);

			consLoanRecordCell = dataRow.createCell(8);
			consLoanRecordCell.setCellValue(consLoanRecord);
			consLoanRecordCell.setCellStyle(contentStyle);

			consServiceUserNameCell = dataRow.createCell(9);
			consServiceUserNameCell.setCellValue(consServiceUserName);
			consServiceUserNameCell.setCellStyle(contentStyle);

			telesaleManNameCell = dataRow.createCell(10);
			telesaleManNameCell.setCellValue(telesaleManName);
			telesaleManNameCell.setCellStyle(contentStyle);

			telesaleManCodeCell = dataRow.createCell(11);
			telesaleManCodeCell.setCellValue(telesaleManCode);
			telesaleManCodeCell.setCellStyle(contentStyle);

			telesaleTeamLeaderNameCell = dataRow.createCell(12);
			telesaleTeamLeaderNameCell.setCellValue(telesaleTeamLeaderName);
			telesaleTeamLeaderNameCell.setCellStyle(contentStyle);

			telesaleSiteManagerNameCell = dataRow.createCell(13);
			telesaleSiteManagerNameCell.setCellValue(telesaleSiteManagerName);
			telesaleSiteManagerNameCell.setCellStyle(contentStyle);

			dictOperStatusNameCell = dataRow.createCell(14);
			dictOperStatusNameCell.setCellValue(dictOperStatusName);
			dictOperStatusNameCell.setCellStyle(contentStyle);
			row++;
		}

	}

}
