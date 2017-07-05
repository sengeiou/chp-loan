package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.payback.entity.ex.OverdueManageEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;

/**
 * 集中划扣导出帮助类
 * @Class Name ExportOverDueHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportOverDueHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportOverDueHelper.class);
	 private static SXSSFWorkbook wb;
     private static CellStyle titleStyle; // 标题行样式
     private static Font titleFont; // 标题行字体
     private static CellStyle dateStyle; // 日期行样式
     private static Font dateFont; // 日期行字体
     private static CellStyle headStyle; // 表头行样式
     private static Font headFont; // 表头行字体
     private static CellStyle contentStyle; // 内容行样式
     private static Font contentFont; // 内容行字体
	
	public static void  exportData(OverdueManageEx overdueManageEx,
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
							"com.creditharmony.loan.borrow.payback.dao.OverdueManageDao.allOverdueManageList",
							overdueManageEx, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(resultSet, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("逾期管理列表.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("逾期管理列表.xlsx"));
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
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal bgSum = new BigDecimal("0.00");
		int row = 3;
		String contractVersion;
		String orgName;
		String customerName;
		String contractCode;
		String bankNameLabel;
		String monthPayDay;
		String contractMonthRepayAmountLate;
		String monthOverdueDays;
		String penaltyAndShould;
		String alsocontractMonthRepay;
		String alsoPenaltyInterest;
		String paybackBuleAmount;
		String dictLoanStatus;
		String dictMonthStatus;
		String reductionBy;
		String monthReductionDay;
		String reductionMoney;
		String teleFlag;
		String mark;
		
		
		Row dataRow;
		Cell numCell;
		Cell orgNameCell;
		Cell customerNameCell;
		Cell contractCodeCell;
		Cell bankNameCell;
		Cell monthPayDayCell;
		Cell contractMonthRepayAmountLateCell;
		Cell monthOverdueDaysCell;
		Cell penaltyAndShouldCell;
		Cell alsocontractMonthRepayCell;
		Cell alsoPenaltyInterestCell;
		Cell paybackBuleAmountCell;
		Cell dictLoanStatusCell;
		Cell dictMonthStatusCell;
		Cell reductionByCell;
		Cell monthReductionDayCell;
		Cell reductionMoneyCell;
		Cell teleFlagCell;
		Cell  markCell;
		int num =1;
		while ( resultSet.next()) {
			contractVersion = resultSet.getString("contractVersion");
			if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4){
				// 新违约金(滞纳金)及罚息总额(应还滞纳金 + 应还罚息)
				BigDecimal monthLateFee = stringForBig(resultSet.getString("monthLateFee"));
				BigDecimal monthInterestPunishshould = stringForBig(resultSet.getString("monthInterestPunishshould"));
			    penaltyAndShould = df.format(monthLateFee.add(monthInterestPunishshould)); 
			    // 实还期供金额=实还分期服务费+实还本金 + 实还利息
			    BigDecimal actualMonthFeeService =  stringForBig(resultSet.getString("actualMonthFeeService"));
				BigDecimal monthCapitalPayactual = stringForBig(resultSet.getString("monthCapitalPayactual"));
				BigDecimal monthInterestPayactual = stringForBig(resultSet.getString("monthInterestPayactual"));
				BigDecimal alsocontractMonthRepayNum = actualMonthFeeService.add(monthCapitalPayactual).add(monthInterestPayactual); 
				alsocontractMonthRepay = df.format(alsocontractMonthRepayNum);
				// 逾期期供金额(期供金额-实还期供金额)
				BigDecimal contractMonthRepayAmount = stringForBig(resultSet.getString("contractMonthRepayAmount"));
				 BigDecimal contractMonthRepayAmountLateNum = contractMonthRepayAmount.subtract(alsocontractMonthRepayNum);
				if(contractMonthRepayAmountLateNum.compareTo(bgSum) < 0){
					contractMonthRepayAmountLateNum = bgSum;
				}
				contractMonthRepayAmountLate = df.format(contractMonthRepayAmountLateNum);
				// 已还违约金(滞纳金)及罚息金额(实还滞纳金 + 实还罚息)
				 BigDecimal actualMonthLateFee =  stringForBig(resultSet.getString("actualMonthLateFee"));
			     BigDecimal monthInterestPunishactual = stringForBig(resultSet.getString("monthInterestPunishactual"));
				 alsoPenaltyInterest = df.format(actualMonthLateFee.add(monthInterestPunishactual));
				// 新减免违约金(滞纳金)罚息(减免滞纳金+减免罚息)
				 BigDecimal MonthLateFeeReduction =  stringForBig(resultSet.getString("MonthLateFeeReduction"));
			     BigDecimal monthPunishReduction = stringForBig(resultSet.getString("monthPunishReduction"));
				 reductionMoney = df.format(MonthLateFeeReduction.add(monthPunishReduction));
			}else{
				// 违约金及罚息总额=应还违约金 + 应还罚息
				BigDecimal monthPenaltyShould =  stringForBig(resultSet.getString("monthPenaltyShould"));
				BigDecimal monthInterestPunishshould = stringForBig(resultSet.getString("monthInterestPunishshould"));
				 penaltyAndShould = df.format(monthPenaltyShould.add(monthInterestPunishshould)); 
				// 实还期供金额=实还本金 + 实还利息
				BigDecimal monthCapitalPayactual =  stringForBig(resultSet.getString("monthCapitalPayactual"));
				BigDecimal monthInterestPayactual = stringForBig(resultSet.getString("monthInterestPayactual"));
				BigDecimal alsocontractMonthRepayNum = monthCapitalPayactual.add(monthInterestPayactual); 
				alsocontractMonthRepay = df.format(alsocontractMonthRepayNum);
				// 逾期期供金额(期供金额-实还期供金额)
				BigDecimal contractMonthRepayAmount = stringForBig(resultSet.getString("contractMonthRepayAmount"));
				BigDecimal contractMonthRepayAmountLateNum = contractMonthRepayAmount.subtract(alsocontractMonthRepayNum);
				if(contractMonthRepayAmountLateNum.compareTo(bgSum) < 0){
					contractMonthRepayAmountLateNum = bgSum;
				}
				contractMonthRepayAmountLate = df.format(contractMonthRepayAmountLateNum);
				// 已还违约金(滞纳金)及罚息金额(实还违约金 + 实还罚息)
				BigDecimal monthPenaltyActual =  stringForBig(resultSet.getString("monthPenaltyActual"));
				BigDecimal monthInterestPunishactual = stringForBig(resultSet.getString("monthInterestPunishactual"));
				alsoPenaltyInterest = df.format((monthPenaltyActual).add(monthInterestPunishactual));
				// 新减免违约金(滞纳金)罚息(减免违约金+减免罚息)
				BigDecimal monthPenaltyReduction =  stringForBig(resultSet.getString("monthPenaltyReduction"));
				BigDecimal monthPunishReduction = stringForBig(resultSet.getString("monthPunishReduction"));
				reductionMoney = df.format(monthPenaltyReduction.add(monthPunishReduction));
			}
			// 蓝补金额
			BigDecimal paybackBuleAmountBak = stringForBig(resultSet.getString("paybackBuleAmount"));
			paybackBuleAmount = df.format(paybackBuleAmountBak);
			
			orgName = resultSet.getString("orgName");
			customerName = resultSet.getString("customerName");
			contractCode = resultSet.getString("contractCode");
			bankNameLabel = resultSet.getString("bankNameLabel");
			monthPayDay = resultSet.getString("monthPayDay");
			monthOverdueDays = resultSet.getString("monthOverdueDays");
			
//			dictLoanStatus = DictCache.getInstance().getDictLabel("jk_loan_apply_status", resultSet.getString("dictLoanStatus"));
//			dictMonthStatus = DictCache.getInstance().getDictLabel("jk_period_status", resultSet.getString("dictMonthStatus"));
			dictLoanStatus = resultSet.getString("dictLoanStatusLabel");
			dictMonthStatus = resultSet.getString("dictMonthStatusLabel");
			reductionBy = resultSet.getString("reductionBy");
			monthReductionDay = resultSet.getString("monthReductionDay");
			teleFlag = resultSet.getString("customerTelesalesFlagLabel");
			mark = resultSet.getString("loanMarkLabel");
			
			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			orgNameCell = dataRow.createCell(1);
			orgNameCell.setCellValue(orgName);
			orgNameCell.setCellStyle(contentStyle);
			
			customerNameCell = dataRow.createCell(2);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			
			contractCodeCell = dataRow.createCell(3);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			bankNameCell = dataRow.createCell(4);
			bankNameCell.setCellValue(bankNameLabel);
			bankNameCell.setCellStyle(contentStyle);
			
			monthPayDayCell = dataRow.createCell(5);
			monthPayDayCell.setCellValue(monthPayDay);
			monthPayDayCell.setCellStyle(contentStyle);
			
			contractMonthRepayAmountLateCell = dataRow.createCell(6);
			contractMonthRepayAmountLateCell.setCellValue(contractMonthRepayAmountLate);
			contractMonthRepayAmountLateCell.setCellStyle(contentStyle);
			
			monthOverdueDaysCell = dataRow.createCell(7);
			monthOverdueDaysCell.setCellValue(monthOverdueDays);
			monthOverdueDaysCell.setCellStyle(contentStyle);
			
			penaltyAndShouldCell = dataRow.createCell(8);
			penaltyAndShouldCell.setCellValue(penaltyAndShould);
			penaltyAndShouldCell.setCellStyle(contentStyle);
			
			alsocontractMonthRepayCell = dataRow.createCell(9);
			alsocontractMonthRepayCell.setCellValue(alsocontractMonthRepay);
			alsocontractMonthRepayCell.setCellStyle(contentStyle);
			
			alsoPenaltyInterestCell = dataRow.createCell(10);
			alsoPenaltyInterestCell.setCellValue(alsoPenaltyInterest);
			alsoPenaltyInterestCell.setCellStyle(contentStyle);
			
			
			paybackBuleAmountCell = dataRow.createCell(11);
			paybackBuleAmountCell.setCellValue(paybackBuleAmount);
			paybackBuleAmountCell.setCellStyle(contentStyle);
			
			dictLoanStatusCell = dataRow.createCell(12);
			dictLoanStatusCell.setCellValue(dictLoanStatus);
			dictLoanStatusCell.setCellStyle(contentStyle);
			
			dictMonthStatusCell = dataRow.createCell(13);
			dictMonthStatusCell.setCellValue(dictMonthStatus);
			dictMonthStatusCell.setCellStyle(contentStyle);
			
			reductionByCell = dataRow.createCell(14);
			reductionByCell.setCellValue(reductionBy);
			reductionByCell.setCellStyle(contentStyle);
			
			monthReductionDayCell = dataRow.createCell(15);
			monthReductionDayCell.setCellValue(monthReductionDay);
			monthReductionDayCell.setCellStyle(contentStyle);
			
			reductionMoneyCell = dataRow.createCell(16);
			reductionMoneyCell.setCellValue(reductionMoney);
			reductionMoneyCell.setCellStyle(contentStyle);
			
			teleFlagCell = dataRow.createCell(17);
			teleFlagCell.setCellValue(teleFlag);
			teleFlagCell.setCellStyle(contentStyle);
			
			markCell = dataRow.createCell(18);
			markCell.setCellValue(mark);
			markCell.setCellStyle(contentStyle);
			num++;
			row++;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("逾期管理",dataSheet,19);
		createTableDateRow(dataSheet,19);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell storeNameHeader = headerRow.createCell(1);
		storeNameHeader.setCellValue("门店名称");
		storeNameHeader.setCellStyle(dateStyle);
		Cell customerNameHeader = headerRow.createCell(2);
		customerNameHeader.setCellStyle(dateStyle);
		customerNameHeader.setCellValue("客户姓名");
		Cell contractCodeHeader = headerRow.createCell(3);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("合同编号");
		Cell bankHeader = headerRow.createCell(4);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("银行");
		Cell overDateHeader = headerRow.createCell(5);
		overDateHeader.setCellStyle(dateStyle);
		overDateHeader.setCellValue("逾期日期");
		Cell payAmountMoneyHeader = headerRow.createCell(6);
		payAmountMoneyHeader.setCellStyle(dateStyle);
		payAmountMoneyHeader.setCellValue("逾期期供金额");
		Cell overDayHeader = headerRow.createCell(7);
		overDayHeader.setCellStyle(dateStyle);
		overDayHeader.setCellValue("逾期天数");
		Cell penaltyInterestHeader = headerRow.createCell(8);
		penaltyInterestHeader.setCellStyle(dateStyle);
		penaltyInterestHeader.setCellValue("违约金罚息总额");
		Cell payAmountActualHeader = headerRow.createCell(9);
		payAmountActualHeader.setCellStyle(dateStyle);
		payAmountActualHeader.setCellValue("实还期供金额");
		Cell penaltyInterestActualHeader = headerRow.createCell(10);
		penaltyInterestActualHeader.setCellStyle(dateStyle);
		penaltyInterestActualHeader.setCellValue("实还违约金罚息金额");
		Cell buleMoneyHeader = headerRow.createCell(11);
		buleMoneyHeader.setCellStyle(dateStyle);
		buleMoneyHeader.setCellValue("蓝补金额");
		Cell dictLoanStatusHeader = headerRow.createCell(12);
		dictLoanStatusHeader.setCellStyle(dateStyle);
		dictLoanStatusHeader.setCellValue("借款状态");
		Cell dictMonthStatusHeader = headerRow.createCell(13);
		dictMonthStatusHeader.setCellStyle(dateStyle);
		dictMonthStatusHeader.setCellValue("期供状态");
		Cell reductionerHeader = headerRow.createCell(14);
		reductionerHeader.setCellStyle(dateStyle);
		reductionerHeader.setCellValue("减免人");
		Cell reductionDayHeader = headerRow.createCell(15);
		reductionDayHeader.setCellStyle(dateStyle);
		reductionDayHeader.setCellValue("减免天数");
		Cell reductionMoneyHeader = headerRow.createCell(16);
		reductionMoneyHeader.setCellStyle(dateStyle);
		reductionMoneyHeader.setCellValue("减免金额");
		Cell teleFlagHeader = headerRow.createCell(17);
		teleFlagHeader.setCellStyle(dateStyle);
		teleFlagHeader.setCellValue("是否电销");
		Cell markHeader = headerRow.createCell(18);
		markHeader.setCellStyle(dateStyle);
		markHeader.setCellValue("标识");
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
