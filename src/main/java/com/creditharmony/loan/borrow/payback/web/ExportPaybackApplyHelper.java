package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;

/**
 * 集中划扣导出帮助类
 * 
 * @Class Name ExportOverDueHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportPaybackApplyHelper {

	private static Logger logger = LoggerFactory
			.getLogger(ExportPaybackApplyHelper.class);
	private static SXSSFWorkbook wb;
	private static CellStyle titleStyle; // 标题行样式
	private static Font titleFont; // 标题行字体
	private static CellStyle dateStyle; // 日期行样式
	private static Font dateFont; // 日期行字体
	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体

	public static void exportData(PaybackApply apply,
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
							"com.creditharmony.loan.common.dao.PaybackDao.findApplyPaybackList",
							apply, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(resultSet, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ Encodes.urlEncode("中和东方还款明细表.xlsx") + ";filename*=UTF-8''"
					+ Encodes.urlEncode("中和东方还款明细表.xlsx"));
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
		wb = new SXSSFWorkbook(5000);
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
		SimpleDateFormat sdf = new SimpleDateFormat(DeductedConstantEx.DATES);
		int row = 3;
		String orgName;
		String contractCode = null;
		String customerName;
		String applyReallyAmount;
		String dictRepayMethod;
		String midBankName = null;
		String modifyTime;
		String paybackDay = null;
		String contractReplayDay;
		String contractMonths;
		String createName;
		String createBy;
		String channelFlag;

		Row dataRow;
		Cell numCell;
		Cell orgNameCell;
		Cell contractCodeCell;
		Cell customerNameCell;
		Cell applyReallyAmountCell;
		Cell dictRepayMethodCell;
		Cell midBankNameCell;
		Cell modifyTimeCell;
		Cell paybackDayCell;
		Cell contractReplayDayCell;
		Cell contractMonthsCell;
		Cell createNameCell;
		Cell createByCell;
		Cell channelFlagCell;
		int num = 1;
		System.out.println("开始循环时间"+new Date());
		while (resultSet.next()) {
			orgName = resultSet.getString("orgName");
			contractCode = resultSet.getString("contract_code");
			customerName = resultSet.getString("customer_name");
			applyReallyAmount = resultSet.getString("apply_really_amount");
			dictRepayMethod = resultSet.getString("dict_repay_method");
			midBankName = resultSet.getString("mid_bank_name");
			modifyTime = resultSet.getString("modify_time");
			paybackDay = resultSet.getString("payback_day");
			contractReplayDay = resultSet.getString("contract_replay_day");
			contractMonths = resultSet.getString("contract_months");
			createName = resultSet.getString("create_name");
			createBy = resultSet.getString("create_by");
			channelFlag = resultSet.getString("channel_flag");
			

			/*String outDepositTime = resultSet.getString("outDepositTime");
			if (StringUtils.isNotEmpty(outDepositTime)) {
				try {
					outDepositTimeStr = sdf.format(sdf.parse(outDepositTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}*/
			

			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			orgNameCell = dataRow.createCell(1);
			orgNameCell.setCellValue(orgName);
			orgNameCell.setCellStyle(contentStyle);

			contractCodeCell = dataRow.createCell(2);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);

			customerNameCell = dataRow.createCell(3);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);

			applyReallyAmountCell = dataRow.createCell(4);
			applyReallyAmountCell.setCellValue(applyReallyAmount);
			applyReallyAmountCell.setCellStyle(contentStyle);

			dictRepayMethodCell = dataRow.createCell(5);
			dictRepayMethodCell.setCellValue(dictRepayMethod);
			dictRepayMethodCell.setCellStyle(contentStyle);

			midBankNameCell = dataRow.createCell(6);
			midBankNameCell.setCellValue(midBankName);
			midBankNameCell.setCellStyle(contentStyle);

			modifyTimeCell = dataRow.createCell(7);
			modifyTimeCell.setCellValue(modifyTime);
			modifyTimeCell.setCellStyle(contentStyle);

			paybackDayCell = dataRow.createCell(8);
			paybackDayCell.setCellValue(paybackDay);
			paybackDayCell.setCellStyle(contentStyle);

			contractReplayDayCell = dataRow.createCell(9);
			contractReplayDayCell.setCellValue(contractReplayDay);
			contractReplayDayCell.setCellStyle(contentStyle);
			
			contractMonthsCell = dataRow.createCell(10);
			contractMonthsCell.setCellValue(contractMonths);
			contractMonthsCell.setCellStyle(contentStyle);
			
			createNameCell = dataRow.createCell(11);
			createNameCell.setCellValue(createName);
			createNameCell.setCellStyle(contentStyle);
			
			createByCell = dataRow.createCell(12);
			createByCell.setCellValue(createBy);
			createByCell.setCellStyle(contentStyle);
			
			channelFlagCell = dataRow.createCell(13);
			channelFlagCell.setCellValue(channelFlag);
			channelFlagCell.setCellStyle(contentStyle);
			num++;
			row++;
		}
		System.out.println("结束循环时间"+new Date());
	}

	private static void wrapperHeader(Sheet dataSheet) {

		createTableTitleRow("中和东方还款明细表", dataSheet, 19);
		createTableDateRow(dataSheet, 19);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell storeNameHeader = headerRow.createCell(1);
		storeNameHeader.setCellValue("门店名称");
		storeNameHeader.setCellStyle(dateStyle);
		Cell conHeader = headerRow.createCell(2);
		conHeader.setCellValue("合同编号");
		conHeader.setCellStyle(dateStyle);
		Cell customerNameHeader = headerRow.createCell(3);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("客户名称");
		Cell contractCodeHeader = headerRow.createCell(4);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("实还金额");
		Cell bankHeader = headerRow.createCell(5);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("还款方式");
		Cell overDateHeader = headerRow.createCell(6);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("到账账户");
		Cell payAmountMoneyHeader = headerRow.createCell(7);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("划扣日期");

		Cell overDayHeader = headerRow.createCell(8);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("账单日");

		Cell penaltyInterestHeader = headerRow.createCell(9);
		penaltyInterestHeader.setCellStyle(dateStyle);
		penaltyInterestHeader.setCellValue("首期还款日");

		Cell payAmountActualHeader = headerRow.createCell(10);
		payAmountActualHeader.setCellStyle(dateStyle);
		payAmountActualHeader.setCellValue("借款期数");
		
		Cell userNameHeader = headerRow.createCell(11);
		userNameHeader.setCellStyle(dateStyle);
		userNameHeader.setCellValue("查账/划扣申请人");
		
		Cell userCodeHeader = headerRow.createCell(12);
		userCodeHeader.setCellStyle(dateStyle);
		userCodeHeader.setCellValue("申请人员工编号");
		
		Cell channelFlagHeader = headerRow.createCell(13);
		channelFlagHeader.setCellStyle(dateStyle);
		channelFlagHeader.setCellValue("渠道");
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
	private static void createTableTitleRow(String title, Sheet dataSheet,
			int sheetNum) {
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
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, 19);
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
	 * 
	 * @param list
	 * @param dataSheet
	 */
	public static void setFyDataList(List<PaybackSplitFyEx> list,
			Sheet dataSheet) {
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

}
