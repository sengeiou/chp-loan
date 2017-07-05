package com.creditharmony.loan.borrow.payback.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylExport;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;

/**
 * 集中划扣申请导出帮助类
 * @Class Name ExportBackInterestHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportCenterDeductHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportCenterDeductHelper.class);
	 private static SXSSFWorkbook wb;
     private static CellStyle titleStyle; // 标题行样式
     private static Font titleFont; // 标题行字体
     private static CellStyle dateStyle; // 日期行样式
     private static Font dateFont; // 日期行字体
     private static CellStyle headStyle; // 表头行样式
     private static Font headFont; // 表头行字体
     private static CellStyle contentStyle; // 内容行样式
     private static Font contentFont; // 内容行字体
     private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
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
							"com.creditharmony.loan.borrow.payback.dao.CenterDeductDao.getCenterDeductList",
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
							+ Encodes.urlEncode("集中划扣申请.xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("集中划扣申请.xls"));
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
		String customerName;
		String contractCode;
		String storesName;
		/*String tel;*/
		String dictDealType;
		String contractEndDay;
		String monthPayDay;
		String applyBankName;
		String bankAccount;
		String contractMoney;
		String repayAmount;
		String completeAmount;
		String currentCompleteAmount;
		String customerStaff;
		String teamName;
		String mark;
		String model;
		
		Row dataRow;
		Cell numCell;
		Cell customerNameCell;
		Cell contractCodeCell;
		Cell storesNameCell;
		//Cell telCell;
		Cell dictDealTypeCell;
		Cell contractEndDayCell;
		Cell monthPayDayCell;
		Cell applyBankNameCell;
		Cell bankAccountCell;
		Cell contractMoneyCell;
		Cell repayAmountCell;
		Cell completeAmountCell;
		Cell currentCompleteAmountCell;
		Cell customerStaffCell;
		Cell teamNameCell;
		Cell  markCell;
		Cell  modelCell;
		int num =1;
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		while (resultSet.next()) {
			customerName = resultSet.getString("customerName");
			contractCode = resultSet.getString("contractCode");
			storesName = resultSet.getString("storesName");
			/*tel = resultSet.getString("tel");*/
			dictDealType = resultSet.getString("dictDealType");
			contractEndDay = resultSet.getString("contractEndDay");
			monthPayDay = resultSet.getString("monthPayDay");
			applyBankName = resultSet.getString("applyBankName");
			bankAccount = resultSet.getString("bankAccount");
			contractMoney = resultSet.getString("contractMoney");
			repayAmount = resultSet.getString("repayAmount");
			completeAmount = resultSet.getString("completeAmount");
			currentCompleteAmount = resultSet.getString("currentCompleteAmount");
			customerStaff = resultSet.getString("customerStaff");
			teamName = resultSet.getString("teamName");
			mark = resultSet.getString("mark");
			model =  resultSet.getString("model");
			model = DictUtils.getLabel(dictMap,"jk_loan_model",model);
			
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
			
			storesNameCell = dataRow.createCell(3);
			storesNameCell.setCellValue(storesName);
			storesNameCell.setCellStyle(contentStyle);
			
			/*telCell = dataRow.createCell(4);
			telCell.setCellValue(tel);
			telCell.setCellStyle(contentStyle);*/
			
			dictDealTypeCell = dataRow.createCell(4);
			dictDealTypeCell.setCellValue(dictDealType);
			dictDealTypeCell.setCellStyle(contentStyle);
			
			contractEndDayCell = dataRow.createCell(5);
			contractEndDayCell.setCellValue(contractEndDay);
			contractEndDayCell.setCellStyle(contentStyle);
			
			monthPayDayCell = dataRow.createCell(6);
			monthPayDayCell.setCellValue(monthPayDay);
			monthPayDayCell.setCellStyle(contentStyle);
			
			applyBankNameCell = dataRow.createCell(7);
			applyBankNameCell.setCellValue(applyBankName);
			applyBankNameCell.setCellStyle(contentStyle);
			
			bankAccountCell = dataRow.createCell(8);
			bankAccountCell.setCellValue(bankAccount);
			bankAccountCell.setCellStyle(contentStyle);
			
			contractMoneyCell = dataRow.createCell(9);
			contractMoneyCell.setCellValue(contractMoney);
			contractMoneyCell.setCellStyle(contentStyle);
			
			repayAmountCell = dataRow.createCell(10);
			repayAmountCell.setCellValue(repayAmount);
			repayAmountCell.setCellStyle(contentStyle);
			
			completeAmountCell = dataRow.createCell(11);
			completeAmountCell.setCellValue(completeAmount);
			completeAmountCell.setCellStyle(contentStyle);
			
			currentCompleteAmountCell = dataRow.createCell(12);
			currentCompleteAmountCell.setCellValue(currentCompleteAmount);
			currentCompleteAmountCell.setCellStyle(contentStyle);
			
			customerStaffCell = dataRow.createCell(13);
			customerStaffCell.setCellValue(customerStaff);
			customerStaffCell.setCellStyle(contentStyle);
			
			teamNameCell = dataRow.createCell(14);
			teamNameCell.setCellValue(teamName);
			teamNameCell.setCellStyle(contentStyle);
			
			markCell = dataRow.createCell(15);
			markCell.setCellValue(mark);
			markCell.setCellStyle(contentStyle);
			
			modelCell = dataRow.createCell(16);
			modelCell.setCellValue(model);
			modelCell.setCellStyle(contentStyle);
			num++;
			row = row + 1;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("集中划扣申请",dataSheet,16);
		createTableDateRow(dataSheet,15);
		Row headerRow = dataSheet.createRow(2);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		Cell lendCodeHeader = headerRow.createCell(1);
		lendCodeHeader.setCellValue("客户姓名");
		lendCodeHeader.setCellStyle(dateStyle);
		Cell accountNoHeader = headerRow.createCell(2);
		accountNoHeader.setCellStyle(dateStyle);
		accountNoHeader.setCellValue("合同编号");
		Cell accountNameHeader = headerRow.createCell(3);
		accountNameHeader.setCellStyle(dateStyle);
		accountNameHeader.setCellValue("门店");
		/*Cell backMoneyHeader = headerRow.createCell(4);
		backMoneyHeader.setCellStyle(dateStyle);
		backMoneyHeader.setCellValue("手机号码");*/
		Cell bankCodeHeader = headerRow.createCell(4);
		bankCodeHeader.setCellStyle(dateStyle);
		bankCodeHeader.setCellValue("划扣平台");
		Cell accountBankHeader = headerRow.createCell(5);
		accountBankHeader.setCellStyle(dateStyle);
		accountBankHeader.setCellValue("合同到期日");
		Cell accountBranchHeader = headerRow.createCell(6);
		accountBranchHeader.setCellStyle(dateStyle);
		accountBranchHeader.setCellValue("还款日");
		Cell cardOrBookletHeader = headerRow.createCell(7);
		cardOrBookletHeader.setCellStyle(dateStyle);
		cardOrBookletHeader.setCellValue("划扣银行");
		Cell provinceHeader = headerRow.createCell(8);
		provinceHeader.setCellStyle(dateStyle);
		provinceHeader.setCellValue("划扣账号");
		Cell cityHeader = headerRow.createCell(9);
		cityHeader.setCellStyle(dateStyle);
		cityHeader.setCellValue("合同金额");
		Cell headerCell = headerRow.createCell(10);
		headerCell.setCellStyle(dateStyle);
		headerCell.setCellValue("月还期供");
		Cell backiIdHeader = headerRow.createCell(11);
		backiIdHeader.setCellStyle(dateStyle);
		backiIdHeader.setCellValue("已还金额");
		Cell memoHeader = headerRow.createCell(12);
		memoHeader.setCellStyle(dateStyle);
		memoHeader.setCellValue("当期应还金额");
		Cell applyLendDayHeader = headerRow.createCell(13);
		applyLendDayHeader.setCellStyle(dateStyle);
		applyLendDayHeader.setCellValue("客服人员");
		Cell applyLendMoneyHeader = headerRow.createCell(14);
		applyLendMoneyHeader.setCellStyle(dateStyle);
		applyLendMoneyHeader.setCellValue("团队名称");
		Cell applyPayHeader = headerRow.createCell(15);
		applyPayHeader.setCellStyle(dateStyle);
		applyPayHeader.setCellValue("渠道");
		Cell ModelHeader = headerRow.createCell(16);
		ModelHeader.setCellStyle(dateStyle);
		ModelHeader.setCellValue("模式");
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
    
	/**
	 * 导出富有 2015年12月26日 By wengsi
	 * @param dataList
	 * @param fileName
	 * @param header
	 * @param response
	 */
	public static void exportExcels(List<PaybackSplitFyEx> dataList, String fileName, String[] header,
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
							+ Encodes.urlEncode(fileName+".xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+".xls"));
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
	
	/**
	 * 导出好易联 2015年4月26日 By wengsi
	 * @param dataList
	 * @param fileName
	 * @param header
	 * @param response
	 */
	public static void exportHYLExcels(List<PaybackSplitHylExport> dataList, String fileName, String[][] header,
			HttpServletResponse response) {
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			Row row1 = dataSheet.createRow(0);
			String[] header1 = header[0];
			for (int i=0; i<header1.length;i++){
				Cell cellHeader = row1.createCell(i);
				cellHeader.setCellValue(header1[i]);
			}
			Row row2 = dataSheet.createRow(1);
			String[] headerDate = header[1];
			for (int i=0; i<headerDate.length;i++){
				Cell cellHeader = row2.createCell(i);
				cellHeader.setCellValue(headerDate[i]);
			}
			String[]  header2= {"序号","银联网络用户编号","银行代码","账号类型","账号","账户名","开户行所在省","开户行所在市","开户行名称","帐户类型","金额","货币类型","协议号","协议用户编号","开户证件类型","证件号","手机号","自定义用户名","备注1","备注2","备注","反馈码","原因"};
			Row rows3 = dataSheet.createRow(2);
			for (int i=0; i<header2.length;i++){
				Cell cellHeader = rows3.createCell(i);
				cellHeader.setCellValue(header2[i]);
			}
			setHYLDataList(dataList,dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+".xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+".xls"));
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
	public static  void setHYLDataList(List<PaybackSplitHylExport> list,Sheet dataSheet){
		Row dataRow;
		int row = 3;
		Cell numCell;
		Cell bankNetworkUserCodeCell;
		
		Cell bankCodeCell;
		
		Cell accountTypeCell;
		
		Cell bankAccountCell;
		
		Cell bankAccountNameCell;
		
		Cell bankProvinceCell;
		
		Cell bankCityCell;
		
		Cell bankNameCell;
		
		Cell accountTypeNoCell;
		
		Cell splitAmountCell;
		
		Cell currencyTypeCell;
		
		Cell protocolNumberCell;
		
		Cell protocolNumberCodeCell;
		
		Cell dictertTypeCell;
		
		Cell customerCertNumCell;
		
		Cell customerPhoneFirstCell;
		
		Cell customUserNameCell;
		Cell remarkOneCell;
		Cell remarkTwoCell;
		Cell enterpriseSerialnoCell;
		Cell feedbackCodeCell;
		Cell reasonCell;
		for (PaybackSplitHylExport e : list){
			
			dataRow = dataSheet.createRow(row);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(String.format("%5d", row-2).trim());
			
			bankNetworkUserCodeCell = dataRow.createCell(1);
			bankNetworkUserCodeCell.setCellValue(e.getBankNetworkUserCode());
			
			bankCodeCell = dataRow.createCell(2);
			bankCodeCell.setCellValue(e.getBankCode());
			
			
			accountTypeCell = dataRow.createCell(3);
			accountTypeCell.setCellValue(e.getAccountType());
			
			
			bankAccountCell = dataRow.createCell(4);
			bankAccountCell.setCellValue(e.getBankAccount());
			
			
			bankAccountNameCell = dataRow.createCell(5);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			
			
			bankProvinceCell = dataRow.createCell(6);
			bankProvinceCell.setCellValue(e.getBankProvince());
			
			
			bankCityCell = dataRow.createCell(7);
			bankCityCell.setCellValue(e.getBankCity());
			
			bankNameCell = dataRow.createCell(8);
			bankNameCell.setCellValue(e.getBankName());
			
			accountTypeNoCell = dataRow.createCell(9);
			accountTypeNoCell.setCellValue(e.getAccountType());
			
			splitAmountCell = dataRow.createCell(10);
			splitAmountCell.setCellValue(e.getSplitAmount().toString());
			
			currencyTypeCell = dataRow.createCell(11);
			currencyTypeCell.setCellValue(e.getCurrencyType());
			
			
			protocolNumberCell = dataRow.createCell(12);
			protocolNumberCell.setCellValue(e.getProtocolNumber());
			
			protocolNumberCodeCell = dataRow.createCell(13);
			protocolNumberCodeCell.setCellValue(e.getProtocolNumberCode());
			
			dictertTypeCell = dataRow.createCell(14);
			dictertTypeCell.setCellValue(e.getDictertType());
			
			customerCertNumCell = dataRow.createCell(15);
			customerCertNumCell.setCellValue(e.getCustomerCertNum());
			
			customerPhoneFirstCell = dataRow.createCell(16);
			customerPhoneFirstCell.setCellValue(e.getCustomerPhoneFirst());
			
			customUserNameCell = dataRow.createCell(17);
			customUserNameCell.setCellValue(e.getCustomUserName());
			
			remarkOneCell = dataRow.createCell(18);
			remarkOneCell.setCellValue(e.getRemarkOne());
			
			remarkTwoCell = dataRow.createCell(19);
			remarkTwoCell.setCellValue(e.getRemarkTwo());
			
			enterpriseSerialnoCell = dataRow.createCell(20);
			enterpriseSerialnoCell.setCellValue(e.getEnterpriseSerialno());
			
			feedbackCodeCell = dataRow.createCell(21);
			feedbackCodeCell.setCellValue(e.getFeedbackCode());
			
			reasonCell = dataRow.createCell(22);
			reasonCell.setCellValue(e.getReason());
			row++;
		}
	}

	/**
	 * 导出中金 2015年4月26日 By wengsi
	 * @param alllist
	 * @param fyDsExportFileName
	 * @param header
	 * @param response
	 */
	public static void exportZJExcels(List<PaybackSplitZjEx> alllist,
			String fyDsExportFileName, String[] header,HttpServletResponse response) {
		
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			Row headerRow = dataSheet.createRow(0);
			for (int i=0; i<header.length;i++){
				Cell cellHeader = headerRow.createCell(i);
				cellHeader.setCellValue(header[i]);
			}
			setZjDataList(alllist,dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fyDsExportFileName+".xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fyDsExportFileName+".xls"));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("exportData()导出数据出现异常");
		} 
	}

	/**
	 * 设置中金数据
	 * @param alllist
	 * @param dataSheet
	 */
	private static void setZjDataList(List<PaybackSplitZjEx> alllist,
			Sheet dataSheet) {
		Row dataRow;
		int row = 1;
		Cell enterpriseSerialnoCell;
		Cell splitAmountCell;
		Cell bankNameCell;
		Cell accountTypeCell;
		Cell bankAccountNameCell;
		Cell bankAccountCell;
		Cell bankBranchCell;
		Cell bankProvinceCell;
		Cell bankCityCell;
		Cell settlementIndicatorCell;
		Cell remarkCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		Cell customerPhoneFirstCell;
		Cell mailboxCell;
		Cell protocolNumberCodeCell;
	
		for (PaybackSplitZjEx e : alllist){
			
			dataRow = dataSheet.createRow(row);
			enterpriseSerialnoCell = dataRow.createCell(0);
			enterpriseSerialnoCell.setCellValue(String.format("%05d",row));
			
			splitAmountCell = dataRow.createCell(1);
			splitAmountCell.setCellValue(e.getSplitAmount());
			
			bankNameCell = dataRow.createCell(2);
			bankNameCell.setCellValue(e.getBankName());
			
			
			accountTypeCell = dataRow.createCell(3);
			accountTypeCell.setCellValue(e.getAccountType());
			
			bankAccountNameCell = dataRow.createCell(4);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			
			bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellValue(e.getBankAccount());
			
			bankBranchCell = dataRow.createCell(6);
			bankBranchCell.setCellValue(e.getBankBranch());
			
			bankProvinceCell = dataRow.createCell(7);
			bankProvinceCell.setCellValue(e.getBankBranch());
			
			bankCityCell = dataRow.createCell(8);
			bankCityCell.setCellValue(e.getBankCity());
			
			
			settlementIndicatorCell = dataRow.createCell(9);
			settlementIndicatorCell.setCellValue("0001");
			
			remarkCell = dataRow.createCell(10);
			remarkCell.setCellValue(e.getRemark());
			
			
			dictertTypeCell = dataRow.createCell(11);
			dictertTypeCell.setCellValue(e.getDictertType());
			
			customerCertNumCell = dataRow.createCell(12);
			customerCertNumCell.setCellValue(e.getCustomerCertNum());
			
			customerPhoneFirstCell = dataRow.createCell(13);
			customerPhoneFirstCell.setCellValue(e.getCustomerPhoneFirst());
			
			mailboxCell = dataRow.createCell(14);
			mailboxCell.setCellValue(e.getMailbox());
			
			protocolNumberCodeCell = dataRow.createCell(15);
			protocolNumberCodeCell.setCellValue(e.getProtocolNumberCode());
			row++;
		}
		
	}

	/**
	 * 导出通联 2015年4月26日 By wengsi
	 * @param alllist
	 * @param zjDsExportFileName
	 * @param header
	 * @param response
	 */
	public static void exportTLExcels(List<PaybackSplitTlEx> alllist,
			String zjDsExportFileName, String[] header,
			HttpServletResponse response) {
   try {
	    init();
		Sheet dataSheet = wb.createSheet("ExportList");
		Row row1 = dataSheet.createRow(0);
		String[] header1 = header;
		for (int i=0; i<header1.length;i++){
			Cell cellHeader = row1.createCell(i);
			cellHeader.setCellValue(header1[i]);
		}
		Row row2 = dataSheet.createRow(1);
		String[] header2 = {"序号","用户编号","银行代码","账号类型","账号","户名","省","市","开户行名称","账户类型","金额","货币类型","协议号","协议用户编号","开户证件类型","证件号","手机号/小灵通","自定义用户号","备注","反馈码","原因"};
		for (int i=0; i<header2.length;i++){
			Cell cellHeader = row2.createCell(i);
			cellHeader.setCellValue(header2[i]);
		}
		setTLDataList(alllist,dataSheet);
		response.reset();
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader(
				"Content-Disposition",
				"attachment; filename="
						+ Encodes.urlEncode(zjDsExportFileName+".xls")
						+ ";filename*=UTF-8''"
						+ Encodes.urlEncode(zjDsExportFileName+".xls"));
		wb.write(response.getOutputStream());
		wb.dispose();
	} catch (Exception e) {
		e.printStackTrace();
		logger.debug("exportData()导出数据出现异常");
	}
}

	/**
	 * 填充数据
	 * @param alllist
	 * @param dataSheet
	 */
	private static void setTLDataList(List<PaybackSplitTlEx> alllist,
			Sheet dataSheet) {
		Row dataRow;
		int row = 2;
		Cell serialNumberCell;
		Cell userCodeCell;
		Cell backCodeCell;
		Cell accountTypeCell;
		Cell  bankAccountCell;
		Cell  bankAccountNameCell;
		Cell  bankProvinceCell;
		Cell bankCityCell;
		Cell bankNameCell;
		Cell accountType1Cell;
		Cell splitAmountCell;
		Cell currencyCell;
		Cell protocolNoCell;
		Cell protocolNoUserCodeCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		Cell customerPhoneFirstCell;
		Cell enterpriseSerialnoCell;
		Cell remarkCell;
		Cell feedbackCodeCell;
		Cell reasonCell;
		
	for (PaybackSplitTlEx e : alllist){
			
			dataRow = dataSheet.createRow(row);
			serialNumberCell = dataRow.createCell(0);
			serialNumberCell.setCellValue(String.format("%05d",row-1));
			
			userCodeCell = dataRow.createCell(1);
			userCodeCell.setCellValue(e.getUserCode());
			
			backCodeCell = dataRow.createCell(2);
			backCodeCell.setCellValue(e.getBackCode());
			
			accountTypeCell = dataRow.createCell(3);
			accountTypeCell.setCellValue(e.getAccountType());
			
			bankAccountCell = dataRow.createCell(4);
			bankAccountCell.setCellValue(e.getBankAccount());
			
			
			
			bankAccountNameCell = dataRow.createCell(5);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			
			bankProvinceCell = dataRow.createCell(6);
			bankProvinceCell.setCellValue(e.getBankProvince());
			
			bankCityCell = dataRow.createCell(7);
			bankCityCell.setCellValue(e.getBankCity());
			
			bankNameCell = dataRow.createCell(8);
			bankNameCell.setCellValue(e.getBankName());
			
			accountType1Cell = dataRow.createCell(9);
			accountType1Cell.setCellValue(e.getAccountType1());
			
			splitAmountCell = dataRow.createCell(10);
			splitAmountCell.setCellValue(e.getSplitAmount());
			
			
			currencyCell = dataRow.createCell(11);
			currencyCell.setCellValue(e.getCurrency());
			
			protocolNoCell = dataRow.createCell(12);
			protocolNoCell.setCellValue(e.getProtocolNo());
			
			protocolNoUserCodeCell = dataRow.createCell(13);
			protocolNoUserCodeCell.setCellValue(e.getProtocolNoUserCode());
			
			dictertTypeCell = dataRow.createCell(14);
			dictertTypeCell.setCellValue(e.getDictertType());
			
			customerCertNumCell = dataRow.createCell(15);
			customerCertNumCell.setCellValue(e.getCustomerCertNum());
			
			customerPhoneFirstCell = dataRow.createCell(16);
			customerPhoneFirstCell.setCellValue(e.getCustomerPhoneFirst());
			
			enterpriseSerialnoCell = dataRow.createCell(17);
			enterpriseSerialnoCell.setCellValue(e.getEnterpriseSerialno());
			
			remarkCell = dataRow.createCell(18);
			remarkCell.setCellValue(e.getRemark());
			
			feedbackCodeCell = dataRow.createCell(19);
			feedbackCodeCell.setCellValue(e.getFeedbackCode());
			
			reasonCell = dataRow.createCell(20);
			reasonCell.setCellValue(e.getReason());
			
			row++;
		}
		
	}
	
	/**
	 * 集中划扣已办
	 * @param queryMap
	 * @param response
	 */
	public static void exportCenterTodo(PaybackApply queryMap,
			HttpServletResponse response) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			toDoHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.payback.dao.CentralizedDeductionDao.centerDeductionAgencyList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			ToDoExcelCell(resultSet, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("集中划扣已办.xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("集中划扣已办.xls"));
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
	
	private static String formatDate(Date date){
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
		return format.format(d);
	}
	
	/**
	 * 集中划扣已办
	 * @param resultSet
	 * @param dataSheet
	 * @throws SQLException
	 */
	private static void ToDoExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		int row = 2;
		String contractCode;
		String customerName;
		String orgName;
		String applyBankName;
		String contractMonths;
		String contractReplayDay;
		String applyReallyAmount;
		String paybackMonthAmount;
		String notPaybackMonthAmount;
		String alsoPaybackMonthAmount;
		String applyDeductAmount;
		String dictPayStatus;
		String huankType;
		String dictLoanStatus;
		String modifyTime;
		String billDay;
		String paybackDay;
		String paybackMaxOverduedays;
		String loanMark;
		String paybackBuleAmount;
		String bankAccount;
		Date huaDate;
		String huipan;
		String shibei;
		String huaPing;
		Row dataRow;
		Cell contractCodeCell;
		Cell customerNameCell;
		Cell orgNameCell;
		Cell applyBankNameCell;
		Cell contractMonthsCell;
		Cell contractReplayDayCell;
		Cell applyReallyAmountCell;
		Cell paybackMonthAmountCell;
		Cell notPaybackMonthAmountCell;
		Cell alsoPaybackMonthAmountCell;
		Cell applyDeductAmountCell;
		Cell dictPayStatusCell;
		Cell huankTypeCell;
		Cell dictLoanStatusCell;
		Cell modifyTimeCell;
		Cell billDayCell;
		Cell paybackDayCell;
		Cell whetherOverdueCell;
		Cell loanMarkCell;
		Cell paybackBuleAmountCell;
		Cell bankAccountCell;
		
		Cell huaDateCell;
		Cell huipanCell;
		Cell shibeiCell;
		Cell huaPingCell;
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		while (resultSet.next()) {
			contractCode = resultSet.getString("contractCode");
			customerName = resultSet.getString("customerName");
			orgName = resultSet.getString("orgName");
			applyBankName = resultSet.getString("applyBankName");
			applyBankName = DictUtils.getLabel(dictMap,"jk_open_bank",applyBankName);
			contractMonths = resultSet.getString("contractMonths");
			contractReplayDay = resultSet.getString("contractReplayDay");
			applyReallyAmount = resultSet.getString("applyReallyAmount");
			paybackMonthAmount = resultSet.getString("paybackMonthAmount");
			notPaybackMonthAmount = resultSet.getString("notPaybackMonthAmount");
			alsoPaybackMonthAmount = resultSet.getString("alsoPaybackMonthAmount");
			applyDeductAmount = resultSet.getString("applyAmount");
			dictPayStatus = resultSet.getString("dictPayStatus");
			dictPayStatus=DictUtils.getLabel(dictMap,"jk_repay_status",dictPayStatus);
			
			huankType = "划扣";
			dictLoanStatus = resultSet.getString("dictLoanStatus");
			dictLoanStatus = DictUtils.getLabel(dictMap,"jk_loan_status",dictLoanStatus);
			modifyTime = resultSet.getString("modifyTime");
			billDay = resultSet.getString("billDay");
			paybackDay = resultSet.getString("monthPayDay");
			paybackMaxOverduedays = resultSet.getString("paybackMaxOverduedays");
			loanMark = resultSet.getString("loanMark");
		    loanMark = DictUtils.getLabel(dictMap,"jk_channel_flag",loanMark);
			paybackBuleAmount = resultSet.getString("paybackBuleAmount");
			bankAccount = resultSet.getString("bankAccount");
			dataRow = dataSheet.createRow(row);
			contractCodeCell = dataRow.createCell(0);
			contractCodeCell.setCellValue(contractCode);
			contractCodeCell.setCellStyle(contentStyle);
			
			customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellValue(customerName);
			customerNameCell.setCellStyle(contentStyle);
			
			orgNameCell = dataRow.createCell(2);
			orgNameCell.setCellValue(orgName);
			orgNameCell.setCellStyle(contentStyle);
			
			applyBankNameCell = dataRow.createCell(3);
			applyBankNameCell.setCellValue(applyBankName);
			applyBankNameCell.setCellStyle(contentStyle);
			
			contractMonthsCell = dataRow.createCell(4);
			contractMonthsCell.setCellValue(contractMonths);
			contractMonthsCell.setCellStyle(contentStyle);
			contractReplayDayCell = dataRow.createCell(5);
			contractReplayDayCell.setCellValue(contractReplayDay);
			contractReplayDayCell.setCellStyle(contentStyle);
			
			applyReallyAmountCell = dataRow.createCell(6);
			applyReallyAmountCell.setCellValue(applyReallyAmount);
			applyReallyAmountCell.setCellStyle(contentStyle);
			
			
			paybackMonthAmountCell = dataRow.createCell(7);
			paybackMonthAmountCell.setCellValue(paybackMonthAmount);
			paybackMonthAmountCell.setCellStyle(contentStyle);
			
			notPaybackMonthAmountCell = dataRow.createCell(8);
			notPaybackMonthAmountCell.setCellValue(notPaybackMonthAmount);
			notPaybackMonthAmountCell.setCellStyle(contentStyle);
			
			alsoPaybackMonthAmountCell = dataRow.createCell(9);
			alsoPaybackMonthAmountCell.setCellValue(alsoPaybackMonthAmount);
			alsoPaybackMonthAmountCell.setCellStyle(contentStyle);
			
	
			
			applyDeductAmountCell = dataRow.createCell(10);
			applyDeductAmountCell.setCellValue(applyDeductAmount);
			applyDeductAmountCell.setCellStyle(contentStyle);
			
			dictPayStatusCell = dataRow.createCell(11);
			dictPayStatusCell.setCellValue(dictPayStatus);
			dictPayStatusCell.setCellStyle(contentStyle);
			
			huankTypeCell = dataRow.createCell(12);
			huankTypeCell.setCellValue(huankType);
			huankTypeCell.setCellStyle(contentStyle);
			
			dictLoanStatusCell = dataRow.createCell(13);
			dictLoanStatusCell.setCellValue(dictLoanStatus);
			dictLoanStatusCell.setCellStyle(contentStyle);
			
			modifyTimeCell = dataRow.createCell(14);
			modifyTimeCell.setCellValue(modifyTime);
			modifyTimeCell.setCellStyle(contentStyle);
			
			billDayCell = dataRow.createCell(14);
			billDayCell.setCellValue(billDay);
			billDayCell.setCellStyle(contentStyle);
			
			paybackDayCell = dataRow.createCell(15);
			paybackDayCell.setCellValue(paybackDay);
			paybackDayCell.setCellStyle(contentStyle);
			
			whetherOverdueCell = dataRow.createCell(16);
			whetherOverdueCell.setCellValue(Integer.parseInt(paybackMaxOverduedays)>0 ? "是":"否");
			whetherOverdueCell.setCellStyle(contentStyle);
			
			loanMarkCell = dataRow.createCell(17);
			loanMarkCell.setCellValue(loanMark);
			loanMarkCell.setCellStyle(contentStyle);
			
			paybackBuleAmountCell = dataRow.createCell(18);
			paybackBuleAmountCell.setCellValue(paybackBuleAmount);
			paybackBuleAmountCell.setCellStyle(contentStyle);
			
			bankAccountCell = dataRow.createCell(19);
			bankAccountCell.setCellValue(bankAccount);
			bankAccountCell.setCellStyle(contentStyle);
			
			 huaDateCell = dataRow.createCell(20);
			 huaDate =  resultSet.getDate("modifyTime");
			 String hy = "";
			 if(huaDate != null){
				 hy = sdf.format(huaDate);
			 }
			 huaDateCell.setCellValue(hy);
			 huaDateCell.setCellStyle(contentStyle);
			 
			 huipanCell = dataRow.createCell(21);
			 huipan =  resultSet.getString("splitBackResult");
			 huipan=DictUtils.getLabel(dictMap,"jk_counteroffer_result",huipan);
			 huipanCell.setCellValue(huipan);
			 huipanCell.setCellStyle(contentStyle);
			 
			 shibeiCell = dataRow.createCell(22);
			 shibei =  resultSet.getString("failReason");
			 shibeiCell.setCellValue(shibei);
			 shibeiCell.setCellStyle(contentStyle);
			
			 huaPingCell = dataRow.createCell(23);
			 huaPing =  resultSet.getString("dictDealType");
			 huaPing=DictUtils.getLabel(dictMap,"jk_deduct_plat",huaPing);
			 huaPingCell.setCellValue(huaPing);
			 huaPingCell.setCellStyle(contentStyle);
			
			row = row + 1;
		}
	}

	private static void toDoHeader(Sheet dataSheet) {
		
		createTableTitleRow(DeductedConstantEx.CENTRALIZED+formatDate(new Date()),dataSheet,23);
		Row headerRow = dataSheet.createRow(1);
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("合同编号");
		Cell lendCodeHeader = headerRow.createCell(1);
		lendCodeHeader.setCellValue("客户姓名");
		lendCodeHeader.setCellStyle(dateStyle);
		Cell accountNoHeader = headerRow.createCell(2);
		accountNoHeader.setCellStyle(dateStyle);
		accountNoHeader.setCellValue("门店名称");
		Cell accountNameHeader = headerRow.createCell(3);
		accountNameHeader.setCellStyle(dateStyle);
		accountNameHeader.setCellValue("开户行名称");
		Cell bankCodeHeader = headerRow.createCell(4);
		bankCodeHeader.setCellStyle(dateStyle);
		bankCodeHeader.setCellValue("批借期数");
		Cell accountBankHeader = headerRow.createCell(5);
		accountBankHeader.setCellStyle(dateStyle);
		accountBankHeader.setCellValue("首期还款期");
		Cell accountBranchHeader = headerRow.createCell(6);
		accountBranchHeader.setCellStyle(dateStyle);
		accountBranchHeader.setCellValue("实还金额");
		Cell cardOrBookletHeader = headerRow.createCell(7);
		cardOrBookletHeader.setCellStyle(dateStyle);
		cardOrBookletHeader.setCellValue("期供");
		Cell provinceHeader = headerRow.createCell(8);
		provinceHeader.setCellStyle(dateStyle);
		provinceHeader.setCellValue("当期未还期供");
		Cell cityHeader = headerRow.createCell(9);
		cityHeader.setCellStyle(dateStyle);
		cityHeader.setCellValue("当期已还期供");
		Cell headerCell = headerRow.createCell(10);
		headerCell.setCellStyle(dateStyle);
		headerCell.setCellValue("划扣金额");
		Cell backiIdHeader = headerRow.createCell(11);
		backiIdHeader.setCellStyle(dateStyle);
		backiIdHeader.setCellValue("还款状态");
		Cell memoHeader = headerRow.createCell(12);
		memoHeader.setCellStyle(dateStyle);
		memoHeader.setCellValue("还款类型");
		Cell applyLendDayHeader = headerRow.createCell(13);
		applyLendDayHeader.setCellStyle(dateStyle);
		applyLendDayHeader.setCellValue("借款状态");
		Cell applyLendMoneyHeader = headerRow.createCell(14);
		applyLendMoneyHeader.setCellStyle(dateStyle);
		applyLendMoneyHeader.setCellValue("划扣日期");
		Cell applyPayHeader = headerRow.createCell(15);
		applyPayHeader.setCellStyle(dateStyle);
		applyPayHeader.setCellValue("还款日");
		
		Cell yuqiHeader = headerRow.createCell(16);
		yuqiHeader.setCellStyle(dateStyle);
		yuqiHeader.setCellValue("往期是否逾期");
		
		Cell biaoshiHeader = headerRow.createCell(17);
		biaoshiHeader.setCellStyle(dateStyle);
		biaoshiHeader.setCellValue("标识");
		
		Cell lanbuHeader = headerRow.createCell(18);
		lanbuHeader.setCellStyle(dateStyle);
		lanbuHeader.setCellValue("蓝补金额");
		
		Cell kahaoHeader = headerRow.createCell(19);
		kahaoHeader.setCellStyle(dateStyle);
		kahaoHeader.setCellValue("卡号");
		
		 Cell huaDateHeader = headerRow.createCell(20);
         huaDateHeader.setCellStyle(dateStyle);
         huaDateHeader.setCellValue("划扣日期");
		     
	     Cell huipanHeader = headerRow.createCell(21);
	     huipanHeader.setCellStyle(dateStyle);
	     huipanHeader.setCellValue("回盘结果");
	     
	     Cell shibeiHeader = headerRow.createCell(22);
	     shibeiHeader.setCellStyle(dateStyle);
	     shibeiHeader.setCellValue("失败原因");
	     
	     Cell huaPingHeader = headerRow.createCell(23);
	     huaPingHeader.setCellStyle(dateStyle);
	     huaPingHeader.setCellValue("划扣平台");
	}
}