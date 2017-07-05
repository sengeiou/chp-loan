package com.creditharmony.loan.borrow.payback.web;

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
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 集中划扣申请导出帮助类
 * @Class Name ExportBackInterestHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportCenterDeductSplitHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportCenterDeductSplitHelper.class);
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
							"com.creditharmony.loan.borrow.payback.dao.PaybackSplitDao.ExportCenterSplit",
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
							+ Encodes.urlEncode("集中划扣.xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("集中划扣.xls"));
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

	private static void assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		int row = 3;
		String contractCode;
		String customerName;
		String storesName;
		String bankName;
		String contractMonths;
		String contractReplayDay;
		String applyReallyAmount;
		String paybackMonthAmount;
		String currentNotYet;  //当前未还
		String currentAlreadyRepaid; //当期以还
		String splitAmount; 
		String dictPayType;
		String dictPayStatus;
		String monthPayDay;
		String dictLoanStatus;
		String overdueDays;
		String dictDealType;
		String  splitBackResult;
		String  failReason;
		String loanFlag;
		String  model;
		String trustRechargeResult;
		String trustRechargeFailReason;
		String  cpcnCount;
		String  tlSign;
		String tlCount;
		String bankAccount;
		String overCount;
		String  klSign;
		String  realAuthen;
		Row dataRow;
		Cell numCell;
		Cell contractCodeCell;
		Cell customerNameCell;
		Cell storesNameCell;
		Cell bankNameCell;
		Cell contractMonthsCell;
		Cell contractReplayDayCell;
		Cell applyReallyAmountCell;
		Cell paybackMonthAmountCell;
		Cell currentNotYetCell;  //当前未还
		Cell currentAlreadyRepaidCell; //当期以还
		Cell splitAmountCell; 
		Cell dictPayTypeCell;
		Cell dictPayStatusCell;
		Cell monthPayDayCell;
		Cell dictLoanStatusCell;
		Cell overdueDaysCell;
		Cell dictDealTypeCell;
		Cell  splitBackResultCell;
		Cell  failReasonCell;
		Cell loanFlagCell;
		Cell  modelCell;
		Cell trustRechargeResultCell;
		Cell trustRechargeFailReasonCell;
		Cell cpcnCountCell;
		Cell tlSignCell;
		Cell tlCountCell;
		Cell bankAccounCell;
		Cell overCountCell;
		Cell klSignCell;
		Cell realAuthenCell;
		int num =1;
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		while (resultSet.next()) {
			
			contractCode = resultSet.getString("contractCode");
			customerName = resultSet.getString("customerName");
			storesName = resultSet.getString("storesName");
			bankName = resultSet.getString("bankName");
			bankName = DictUtils.getLabel(dictMap,"jk_open_bank",bankName);
			contractMonths = resultSet.getString("contractMonths");
			contractReplayDay = resultSet.getString("contractReplayDay");
			applyReallyAmount = resultSet.getString("applyReallyAmount");
			paybackMonthAmount = resultSet.getString("paybackMonthAmount");
			currentNotYet = resultSet.getString("currentNotYet");
			currentAlreadyRepaid = resultSet.getString("currentAlreadyRepaid");
			splitAmount = resultSet.getString("splitAmount");
			dictPayType = "集中划扣";
			dictPayStatus = resultSet.getString("dictPayStatus");
			
			dictPayStatus = DictUtils.getLabel(dictMap,"jk_repay_status",dictPayStatus);
			monthPayDay = resultSet.getString("monthPayDay");
			dictLoanStatus = resultSet.getString("dictLoanStatus");
			dictLoanStatus = DictUtils.getLabel(dictMap,"jk_loan_apply_status",dictLoanStatus);
			
			overdueDays =  resultSet.getString("overdueDays");
			dictDealType =  resultSet.getString("dictDealType");
			dictDealType = DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, dictDealType);
			
			splitBackResult =  resultSet.getString("splitBackResult");
			splitBackResult = DictUtils.getLabel(dictMap,"jk_counteroffer_result",splitBackResult);
			
			failReason =  resultSet.getString("failReason");
			loanFlag =  resultSet.getString("loanFlag");
			loanFlag = DictUtils.getLabel(dictMap,"jk_channel_flag",loanFlag);
			model =  resultSet.getString("model");
			model = DictUtils.getLabel(dictMap,"jk_loan_model",model);
			trustRechargeResult =  resultSet.getString("trustRechargeResult");
			trustRechargeResult = DictUtils.getLabel(dictMap,"jk_counteroffer_result",trustRechargeResult);
			trustRechargeFailReason =  resultSet.getString("trustRechargeFailReason");
			
			cpcnCount = resultSet.getString("cpcnCount");
			tlSign = resultSet.getString("tlSign");
			tlSign = DictUtils.getLabel(dictMap,"yes_no",tlSign);
			
			tlCount = resultSet.getString("tlCount");
			
			bankAccount = resultSet.getString("bankAccount");
			overCount = resultSet.getString("overCount");
			klSign = resultSet.getString("klSign");
			realAuthen = resultSet.getString("cjSign");
			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			
			contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			customerNameCell = dataRow.createCell(2);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			
			storesNameCell = dataRow.createCell(3);
			storesNameCell.setCellValue(storesName);
			storesNameCell.setCellStyle(contentStyle);
			
			bankNameCell = dataRow.createCell(4);
			bankNameCell.setCellValue(bankName);
			bankNameCell.setCellStyle(contentStyle);
			
			contractMonthsCell = dataRow.createCell(5);
			contractMonthsCell.setCellValue(contractMonths);
			contractMonthsCell.setCellStyle(contentStyle);
			
			contractReplayDayCell = dataRow.createCell(6);
			contractReplayDayCell.setCellValue(contractReplayDay);
			contractReplayDayCell.setCellStyle(contentStyle);
			
			applyReallyAmountCell = dataRow.createCell(7);
			applyReallyAmountCell.setCellValue(applyReallyAmount);
			applyReallyAmountCell.setCellStyle(contentStyle);
			
			paybackMonthAmountCell = dataRow.createCell(8);
			paybackMonthAmountCell.setCellValue(paybackMonthAmount);
			paybackMonthAmountCell.setCellStyle(contentStyle);
			
			currentNotYetCell = dataRow.createCell(9);
			currentNotYetCell.setCellValue(currentNotYet);
			currentNotYetCell.setCellStyle(contentStyle);
			
			currentAlreadyRepaidCell = dataRow.createCell(10);
			currentAlreadyRepaidCell.setCellValue(currentAlreadyRepaid);
			currentAlreadyRepaidCell.setCellStyle(contentStyle);
			
			splitAmountCell = dataRow.createCell(11);
			splitAmountCell.setCellValue(splitAmount);
			splitAmountCell.setCellStyle(contentStyle);
			
			dictPayTypeCell = dataRow.createCell(12);
			dictPayTypeCell.setCellValue(dictPayType);
			dictPayTypeCell.setCellStyle(contentStyle);
			
			dictPayStatusCell = dataRow.createCell(13);
			dictPayStatusCell.setCellValue(dictPayStatus);
			dictPayStatusCell.setCellStyle(contentStyle);
			
			monthPayDayCell = dataRow.createCell(14);
			monthPayDayCell.setCellValue(monthPayDay);
			monthPayDayCell.setCellStyle(contentStyle);
			
			dictLoanStatusCell = dataRow.createCell(15);
			dictLoanStatusCell.setCellValue(dictLoanStatus);
			dictLoanStatusCell.setCellStyle(contentStyle);
			
			overdueDaysCell = dataRow.createCell(16);
			overdueDaysCell.setCellValue(overdueDays == null ? "0" : overdueDays);
			overdueDaysCell.setCellStyle(contentStyle);
			
			dictDealTypeCell = dataRow.createCell(17);
			dictDealTypeCell.setCellValue(dictDealType);
			dictDealTypeCell.setCellStyle(contentStyle);
			
			splitBackResultCell = dataRow.createCell(18);
			splitBackResultCell.setCellValue(splitBackResult);
			splitBackResultCell.setCellStyle(contentStyle);
			
			failReasonCell = dataRow.createCell(19);
			failReasonCell.setCellValue(failReason);
			failReasonCell.setCellStyle(contentStyle);
			
			loanFlagCell = dataRow.createCell(20);
			loanFlagCell.setCellValue(loanFlag);
			loanFlagCell.setCellStyle(contentStyle);
			
			modelCell = dataRow.createCell(21);
			modelCell.setCellValue(model);
			modelCell.setCellStyle(contentStyle);
			
			trustRechargeResultCell = dataRow.createCell(22);
			trustRechargeResultCell.setCellValue(trustRechargeResult);
			trustRechargeResultCell.setCellStyle(contentStyle);
			
			trustRechargeFailReasonCell = dataRow.createCell(23);
			trustRechargeFailReasonCell.setCellValue(trustRechargeFailReason);
			trustRechargeFailReasonCell.setCellStyle(contentStyle);
			
			cpcnCountCell = dataRow.createCell(24);
			cpcnCountCell.setCellValue(cpcnCount);
			cpcnCountCell.setCellStyle(contentStyle);
			
			tlCountCell = dataRow.createCell(25);
			tlCountCell.setCellValue(tlCount);
			tlCountCell.setCellStyle(contentStyle);
			
			
			
			
			tlSignCell = dataRow.createCell(26);
			tlSignCell.setCellValue(tlSign);
			tlSignCell.setCellStyle(contentStyle);
			
			
			bankAccounCell = dataRow.createCell(27);
			bankAccounCell.setCellValue(bankAccount);
			bankAccounCell.setCellStyle(contentStyle);
		    overCountCell = dataRow.createCell(28);
		    overCountCell.setCellValue(overCount);
		    overCountCell.setCellStyle(contentStyle);
			realAuthenCell = dataRow.createCell(29);
			realAuthenCell.setCellValue(realAuthen);
			realAuthenCell.setCellStyle(contentStyle);
			klSignCell = dataRow.createCell(30);
			klSignCell.setCellValue(klSign);
			klSignCell.setCellStyle(contentStyle);
			num++;
			row = row + 1;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("集中划扣",dataSheet,30);
		createTableDateRow(dataSheet,15);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		
		Cell accountNoHeader = headerRow.createCell(1);
		accountNoHeader.setCellStyle(dateStyle);
		accountNoHeader.setCellValue("合同编号");
		
		Cell lendCodeHeader = headerRow.createCell(2);
		lendCodeHeader.setCellValue("客户姓名");
		lendCodeHeader.setCellStyle(dateStyle);
		
		Cell accountNameHeader = headerRow.createCell(3);
		accountNameHeader.setCellStyle(dateStyle);
		accountNameHeader.setCellValue("门店名称");
		
		Cell backMoneyHeader = headerRow.createCell(4);
		backMoneyHeader.setCellStyle(dateStyle);
		backMoneyHeader.setCellValue("开户行名称");
		
		Cell bankCodeHeader = headerRow.createCell(5);
		bankCodeHeader.setCellStyle(dateStyle);
		bankCodeHeader.setCellValue("批借期数");
		Cell accountBankHeader = headerRow.createCell(6);
		accountBankHeader.setCellStyle(dateStyle);
		accountBankHeader.setCellValue("首期还款期");
		
		Cell accountBranchHeader = headerRow.createCell(7);
		accountBranchHeader.setCellStyle(dateStyle);
		accountBranchHeader.setCellValue("实还金额");
		
		Cell cardOrBookletHeader = headerRow.createCell(8);
		cardOrBookletHeader.setCellStyle(dateStyle);
		cardOrBookletHeader.setCellValue("期供");
		
		Cell provinceHeader = headerRow.createCell(9);
		provinceHeader.setCellStyle(dateStyle);
		provinceHeader.setCellValue("当期未还期供");
		
		Cell cityHeader = headerRow.createCell(10);
		cityHeader.setCellStyle(dateStyle);
		cityHeader.setCellValue("当期已还期供");
		
		Cell headerCell = headerRow.createCell(11);
		headerCell.setCellStyle(dateStyle);
		headerCell.setCellValue("划扣金额");
		
		Cell backiIdHeader = headerRow.createCell(12);
		backiIdHeader.setCellStyle(dateStyle);
		backiIdHeader.setCellValue("还款类型");
		
		Cell memoHeader = headerRow.createCell(13);
		memoHeader.setCellStyle(dateStyle);
		memoHeader.setCellValue("还款状态");
		
		Cell applyLendDayHeader = headerRow.createCell(14);
		applyLendDayHeader.setCellStyle(dateStyle);
		applyLendDayHeader.setCellValue("还款日");
		
		Cell applyLendMoneyHeader = headerRow.createCell(15);
		applyLendMoneyHeader.setCellStyle(dateStyle);
		applyLendMoneyHeader.setCellValue("借款状态");
		
		Cell overdueDaysHeader = headerRow.createCell(16);
		overdueDaysHeader.setCellStyle(dateStyle);
		overdueDaysHeader.setCellValue("逾期天数");
		
		Cell applyPayHeader = headerRow.createCell(17);
		applyPayHeader.setCellStyle(dateStyle);
		applyPayHeader.setCellValue("签约平台");
		
		Cell splitBackResultHeader = headerRow.createCell(18);
		splitBackResultHeader.setCellStyle(dateStyle);
		splitBackResultHeader.setCellValue("回盘结果");
		
		Cell failReasonHeader = headerRow.createCell(19);
		failReasonHeader.setCellStyle(dateStyle);
		failReasonHeader.setCellValue("失败原因");
		
		Cell channelHeader = headerRow.createCell(20);
		channelHeader.setCellStyle(dateStyle);
		channelHeader.setCellValue("渠道");
		
		Cell ModelHeader = headerRow.createCell(21);
		ModelHeader.setCellStyle(dateStyle);
		ModelHeader.setCellValue("模式");
		
		Cell trustRechargeResultLabelHeader = headerRow.createCell(22);
		trustRechargeResultLabelHeader.setCellStyle(dateStyle);
		trustRechargeResultLabelHeader.setCellValue("委托充值结果");
		
		Cell trustRechargeFailReasonHeader = headerRow.createCell(23);
		trustRechargeFailReasonHeader.setCellStyle(dateStyle);
		trustRechargeFailReasonHeader.setCellValue("委托充值失败原因");
		
		Cell cpcnCountHeader = headerRow.createCell(24);
		cpcnCountHeader.setCellStyle(dateStyle);
		cpcnCountHeader.setCellValue("中金余额不足次数");
		
		Cell tlCountHeader = headerRow.createCell(25);
		tlCountHeader.setCellStyle(dateStyle);
		tlCountHeader.setCellValue("通联余额不足次数");
		
		Cell tlSignHeader = headerRow.createCell(26);
		tlSignHeader.setCellStyle(dateStyle);
		tlSignHeader.setCellValue("通联批量签约");
		
		Cell bankAccountHeader = headerRow.createCell(27);
		bankAccountHeader.setCellStyle(dateStyle);
		bankAccountHeader.setCellValue("卡号");
		
		Cell overCountHeader = headerRow.createCell(28);
		overCountHeader.setCellStyle(dateStyle);
		overCountHeader.setCellValue("累计逾期期数");
		
		Cell realAuthenHeader = headerRow.createCell(29);
		realAuthenHeader.setCellStyle(dateStyle);
		realAuthenHeader.setCellValue("畅捷是否签约");
		
		Cell klSignHeader = headerRow.createCell(30);
		klSignHeader.setCellStyle(dateStyle);
		klSignHeader.setCellValue("卡联是否签约");
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
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,29);
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