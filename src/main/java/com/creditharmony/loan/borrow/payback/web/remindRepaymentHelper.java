package com.creditharmony.loan.borrow.payback.web;

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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;

/**
 * 发起还款提醒导出帮助类
 * @Class Name ExportOverDueHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class remindRepaymentHelper {

	private static Logger logger = LoggerFactory.getLogger(remindRepaymentHelper.class);
	 private static SXSSFWorkbook wb;
     private static CellStyle titleStyle; // 标题行样式
     private static Font titleFont; // 标题行字体
     private static CellStyle dateStyle; // 日期行样式
     private static Font dateFont; // 日期行字体
     private static CellStyle headStyle; // 表头行样式
     private static Font headFont; // 表头行字体
     private static CellStyle contentStyle; // 内容行样式
     private static Font contentFont; // 内容行字体
	
	public static void  exportData(RepaymentReminderEx repaymentReminderEx,
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
							"com.creditharmony.loan.borrow.payback.dao.RepaymentReminderDao.exportRemindExcel",
							repaymentReminderEx, sqlSessionFactory);
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
							+ Encodes.urlEncode("汇金还款客户提醒短信列表.xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("汇金还款客户提醒短信列表.xlsx"));
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
		String contractVersion;
		String customerName;
		String contractCode;
		String stroeName;
		String applyBankName;
		String bankAccount;
		String contractMoney;// 合同金额
		String completeMoney;// 月还期供
		String completeAmount; // 已还金额
		String payMoney; // 当期应还金额
		String monthPayDay;
		String logo;
		String modelLabel;
		
		
		Row dataRow;
		Cell numCell;
		Cell customerNameCell;
		Cell contractCodeCell;
		Cell stroeNameCell;
		Cell applyBankNameCell;
		Cell bankAccountCell;
		Cell contractMoneyCell;
		Cell completeMoneyCell;
		Cell completeAmountCell;
		Cell payMoneyCell;
		Cell monthPayDayCell;
		Cell logoCell;
		Cell modelLabelCell;
	
		int num =1;
		while ( resultSet.next()) {
			contractVersion = resultSet.getString("contractVersion");
			
			BigDecimal monthFeeService =  stringForBig(resultSet.getString("monthFeeService")).setScale(2,   BigDecimal.ROUND_HALF_UP);
			BigDecimal monthPayAmount =  stringForBig(resultSet.getString("monthPayAmount")).setScale(2,   BigDecimal.ROUND_HALF_UP);
			BigDecimal monthInterestBackshould =  stringForBig(resultSet.getString("monthInterestBackshould")).setScale(2,   BigDecimal.ROUND_HALF_UP);
			
			BigDecimal actualMonthFeeService =  stringForBig(resultSet.getString("actualMonthFeeService")).setScale(2,   BigDecimal.ROUND_HALF_UP);
			BigDecimal monthCapitalPayactual = stringForBig(resultSet.getString("monthCapitalPayactual")).setScale(2,   BigDecimal.ROUND_HALF_UP);
			BigDecimal monthInterestPayactual = stringForBig(resultSet.getString("monthInterestPayactual")).setScale(2,   BigDecimal.ROUND_HALF_UP);
			if(StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4){
				// 已还金额(实还分期服务费+实还本金+实还利息)
				completeAmount = actualMonthFeeService.add(monthCapitalPayactual).add(monthInterestPayactual).toString();
				// 当期应还金额(应还分期服务费+应还本金+应还利息)
				payMoney = monthFeeService.add(monthPayAmount).add(monthInterestBackshould).subtract(new BigDecimal(completeAmount)).toString();
			}else{
				// 已还金额(实还本金+实还利息)
				completeAmount = monthCapitalPayactual.add(monthInterestPayactual).toString();
				// 当期应还金额(应还本金+应还利息)
				payMoney = monthPayAmount.add(monthInterestBackshould).subtract(new BigDecimal(completeAmount)).toString();
			}
			
			customerName = resultSet.getString("customerName");
			contractCode = resultSet.getString("contractCode");
			stroeName = resultSet.getString("stroeName");
			applyBankName = resultSet.getString("applyBankName");
			bankAccount = resultSet.getString("bankAccount");
			contractMoney = stringForBig(resultSet.getString("contractMoney")).setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
			completeMoney = stringForBig(resultSet.getString("completeMoney")).setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
			monthPayDay = resultSet.getString("monthPayDay");
			logo = resultSet.getString("logo");
			modelLabel = resultSet.getString("modelLabel");
			
			//logo=DictCache.getInstance().getDictLabel("jk_channel_flag", resultSet.getString("logo"));
			
			
			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(num);
			numCell.setCellStyle(contentStyle);
			customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			
			contractCodeCell = dataRow.createCell(2);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			stroeNameCell = dataRow.createCell(3);
			stroeNameCell.setCellValue(stroeName);
			stroeNameCell.setCellStyle(contentStyle);
			
			applyBankNameCell = dataRow.createCell(4);
		 	applyBankNameCell.setCellValue(applyBankName);
			applyBankNameCell.setCellStyle(contentStyle);
			
			bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellValue(bankAccount);
			bankAccountCell.setCellStyle(contentStyle);
			
			contractMoneyCell = dataRow.createCell(6);
			contractMoneyCell.setCellValue(contractMoney);
			contractMoneyCell.setCellStyle(contentStyle);
			
			completeMoneyCell = dataRow.createCell(7);
			completeMoneyCell.setCellValue(completeMoney);
			completeMoneyCell.setCellStyle(contentStyle);
			
			completeAmountCell = dataRow.createCell(8);
			completeAmountCell.setCellValue(completeAmount);
			completeAmountCell.setCellStyle(contentStyle);
			
			payMoneyCell = dataRow.createCell(9);
			payMoneyCell.setCellValue(payMoney);
			payMoneyCell.setCellStyle(contentStyle);
			
			monthPayDayCell = dataRow.createCell(10);
			monthPayDayCell.setCellValue(monthPayDay);
			monthPayDayCell.setCellStyle(contentStyle);
			
			logoCell = dataRow.createCell(11);
			logoCell.setCellValue(logo);
			logoCell.setCellStyle(contentStyle);
			
			modelLabelCell = dataRow.createCell(12);
			modelLabelCell.setCellValue(modelLabel);
			modelLabelCell.setCellStyle(contentStyle);
			num++;
			row++;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("汇金还款客户提醒短信列表",dataSheet,12);
		createTableDateRow(dataSheet,12);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell customerNameHeader = headerRow.createCell(1);
		customerNameHeader.setCellValue("客户名称");
		customerNameHeader.setCellStyle(dateStyle);
		Cell contractCodeHeader = headerRow.createCell(2);
		contractCodeHeader.setCellStyle(dateStyle);
		contractCodeHeader.setCellValue("合同编号");
		Cell storeNameHeader = headerRow.createCell(3);
		storeNameHeader.setCellStyle(dateStyle);
		storeNameHeader.setCellValue("门店名称");
		Cell bankHeader = headerRow.createCell(4);
		bankHeader.setCellStyle(dateStyle);
		bankHeader.setCellValue("划扣银行");
		Cell bankAccountHeader = headerRow.createCell(5);
		bankAccountHeader.setCellStyle(dateStyle);
		bankAccountHeader.setCellValue("划扣账号");
		Cell contractMoneyHeader = headerRow.createCell(6);
		contractMoneyHeader.setCellStyle(dateStyle);
		contractMoneyHeader.setCellValue("合同金额");
		Cell completeMoneyHeader = headerRow.createCell(7);
		completeMoneyHeader.setCellStyle(dateStyle);
		completeMoneyHeader.setCellValue("月还期供");
		Cell completeAmountHeader = headerRow.createCell(8);
		completeAmountHeader.setCellStyle(dateStyle);
		completeAmountHeader.setCellValue("已还金额");
		Cell payMoneyHeader = headerRow.createCell(9);
		payMoneyHeader.setCellStyle(dateStyle);
		payMoneyHeader.setCellValue("当期应还金额");
		Cell monthPayDayHeader = headerRow.createCell(10);
		monthPayDayHeader.setCellStyle(dateStyle);
		monthPayDayHeader.setCellValue("还款日");
		Cell logoHeader = headerRow.createCell(11);
		logoHeader.setCellStyle(dateStyle);
		logoHeader.setCellValue("渠道");
		Cell modelHeader = headerRow.createCell(12);
		modelHeader.setCellStyle(dateStyle);
		modelHeader.setCellValue("模式");
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
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,sheetNum);
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
