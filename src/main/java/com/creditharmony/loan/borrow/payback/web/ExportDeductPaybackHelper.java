package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Format;
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
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 集中划扣申请导出帮助类
 * @Class Name ExportBackInterestHelper
 * @author wengsi
 * @Create In 2016年4月14日
 */
public class ExportDeductPaybackHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportDeductPaybackHelper.class);
	 private static SXSSFWorkbook wb;
     private static CellStyle titleStyle; // 标题行样式
     private static Font titleFont; // 标题行字体
     private static CellStyle dateStyle; // 日期行样式
     private static Font dateFont; // 日期行字体
     private static CellStyle headStyle; // 表头行样式
     private static Font headFont; // 表头行字体
     private static CellStyle contentStyle; // 内容行样式
     private static Font contentFont; // 内容行字体
     
     static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void exportData(List<PaybackApply> apply,
			HttpServletResponse response) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			init();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			assembleExcelCell(apply, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("待还款划扣导出.xls")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("待还款划扣导出.xls"));
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

	private static void assembleExcelCell(List<PaybackApply> applyList, Sheet dataSheet)
			throws SQLException {
		int row = 3;
		int num =1;
		Row dataRow = null;
		String contractCode;
		String customerName;
		String  orgName;
		String  bankNameLabel;
		BigDecimal  contractMonths;
		String    contractReplayDay ="";
		String     applyPayDay ="";
		String  dictPayStatusLabel;
		BigDecimal contractAmount;
		Integer paybackDay;
		BigDecimal paybackMonthAmount;
		BigDecimal applyAmount;
		String dictLoanStatusLabel;
		String dictDealTypeLabel;
		BigDecimal paybackBuleAmount ;
		String splitBackResultLabel;
		String loanFlagLabel;
		String modelLabel;
		String tlSignLabel;
		String trustRechargeResultLabel;         
		String trustRechargeFailReason;
		String klSign;
		String cjSign;
		
        Cell                contractCodeCell;
        Cell                customerNameCell;
        Cell                     orgNameCell;
        Cell               bankNameLabelCell;
        Cell              contractMonthsCell;
        Cell           contractReplayDayCell;
        Cell                 applyPayDayCell;
        Cell          dictPayStatusLabelCell;
        Cell              contractAmountCell;
        Cell                  paybackDayCell;
        Cell          paybackMonthAmountCell;
        Cell            applyAmountCell;
        Cell         dictLoanStatusLabelCell;
        Cell           dictDealTypeLabelCell;
        Cell           paybackBuleAmountCell; 
        Cell        splitBackResultLabelCell;
        Cell               loanFlagLabelCell;
        Cell                  modelLabelCell;
        Cell                 tlSignLabelCell;
        Cell    trustRechargeResultLabelCell;          
        Cell     trustRechargeFailReasonCell;
        Cell    numCell;
        Cell    klsignCell;
        Cell   cjSignCell;
		
		for(PaybackApply apply : applyList){
		dataRow = dataSheet.createRow(row);
		contractCode  = apply.getContractCode();
		customerName = apply.getLoanCustomer().getCustomerName();
		orgName = apply.getOrgName();
		bankNameLabel = apply.getLoanBank().getBankNameLabel();
		contractMonths = apply.getContract().getContractMonths();
		if(apply.getContract().getContractReplayDay() != null){
			contractReplayDay  =formatter.format(apply.getContract().getContractReplayDay());
		}  
		if(apply.getApplyPayDay() != null){
			applyPayDay = formatter.format(apply.getApplyPayDay());
		}
		
		dictPayStatusLabel = apply.getPayback().getDictPayStatusLabel();
		contractAmount = apply.getContract().getContractAmount();
		paybackDay = apply.getPayback().getPaybackDay();
		paybackMonthAmount = apply.getPayback().getPaybackMonthAmount();
		applyAmount = apply.getApplyAmount();
		dictLoanStatusLabel = apply.getLoanInfo().getDictLoanStatusLabel();
		dictDealTypeLabel = apply.getDictDealTypeLabel();
		paybackBuleAmount  = apply.getPayback().getPaybackBuleAmount();
		splitBackResultLabel = apply.getSplitBackResultLabel();

		loanFlagLabel = apply.getLoanInfo().getLoanFlagLabel();
		modelLabel = apply.getModelLabel();
		tlSignLabel = apply.getTlSignLabel();
		trustRechargeResultLabel  =    apply.getTrustRechargeResultLabel();      
		trustRechargeFailReason = apply.getTrustRechargeFailReason();
		klSign  = apply.getKlSign();
		cjSign = apply.getCjSign();
		
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
		
				
	    orgNameCell = dataRow.createCell(3);
		orgNameCell.setCellValue(orgName);
		orgNameCell.setCellStyle(contentStyle);
		
		bankNameLabelCell = dataRow.createCell(4);
		bankNameLabelCell.setCellValue(bankNameLabel);
		bankNameLabelCell.setCellStyle(contentStyle);
		
		contractMonthsCell = dataRow.createCell(5);
		contractMonthsCell.setCellValue(contractMonths == null ? 0 :  contractMonths.doubleValue());
		contractMonthsCell.setCellStyle(contentStyle);
		
			
		contractReplayDayCell = dataRow.createCell(6);
		contractReplayDayCell.setCellValue(contractReplayDay);
		contractReplayDayCell.setCellStyle(contentStyle);

		applyPayDayCell = dataRow.createCell(7);
		applyPayDayCell.setCellValue(applyPayDay);
		applyPayDayCell.setCellStyle(contentStyle);
		
		dictPayStatusLabelCell = dataRow.createCell(8);
		dictPayStatusLabelCell.setCellValue(dictPayStatusLabel);
		dictPayStatusLabelCell.setCellStyle(contentStyle);
		
			
		contractAmountCell = dataRow.createCell(9);
		contractAmountCell.setCellValue(contractAmount == null ? 0 :  contractAmount.doubleValue());
		contractAmountCell.setCellStyle(contentStyle);
		
			
		paybackDayCell = dataRow.createCell(10);
		paybackDayCell.setCellValue(paybackDay);
		paybackDayCell.setCellStyle(contentStyle);
	
		paybackMonthAmountCell = dataRow.createCell(11);
		paybackMonthAmountCell.setCellValue(paybackMonthAmount == null ? 0 :  paybackMonthAmount.doubleValue());
		paybackMonthAmountCell.setCellStyle(contentStyle);
		
		applyAmountCell = dataRow.createCell(12);
		applyAmountCell.setCellValue(applyAmount == null ? 0 :  applyAmount.doubleValue());
		applyAmountCell.setCellStyle(contentStyle);
		
		
		
		dictLoanStatusLabelCell = dataRow.createCell(13);
		dictLoanStatusLabelCell.setCellValue(dictLoanStatusLabel);
		dictLoanStatusLabelCell.setCellStyle(contentStyle);
			
		dictDealTypeLabelCell = dataRow.createCell(14);
		dictDealTypeLabelCell.setCellValue(dictDealTypeLabel);
		dictDealTypeLabelCell.setCellStyle(contentStyle);
		
		paybackBuleAmountCell = dataRow.createCell(15);
		paybackBuleAmountCell.setCellValue(paybackBuleAmount == null ? 0 :  paybackBuleAmount.doubleValue());
		paybackBuleAmountCell.setCellStyle(contentStyle);
		
		splitBackResultLabelCell = dataRow.createCell(16);
		splitBackResultLabelCell.setCellValue(splitBackResultLabel);
		splitBackResultLabelCell.setCellStyle(contentStyle);
				
		
	    loanFlagLabelCell = dataRow.createCell(17);
		loanFlagLabelCell.setCellValue(loanFlagLabel);
		loanFlagLabelCell.setCellStyle(contentStyle);
		
		modelLabelCell = dataRow.createCell(18);
		modelLabelCell.setCellValue(modelLabel);
		modelLabelCell.setCellStyle(contentStyle);
				
		tlSignLabelCell = dataRow.createCell(19);
		tlSignLabelCell.setCellValue(tlSignLabel);
		tlSignLabelCell.setCellStyle(contentStyle);
		
	    trustRechargeResultLabelCell = dataRow.createCell(20);
		trustRechargeResultLabelCell.setCellValue(trustRechargeResultLabel);
		trustRechargeResultLabelCell.setCellStyle(contentStyle);
		
		trustRechargeFailReasonCell = dataRow.createCell(21);
		trustRechargeFailReasonCell.setCellValue(trustRechargeFailReason);
		trustRechargeFailReasonCell.setCellStyle(contentStyle);
		
	    trustRechargeResultLabelCell = dataRow.createCell(22);
		trustRechargeResultLabelCell.setCellValue(trustRechargeResultLabel);
		trustRechargeResultLabelCell.setCellStyle(contentStyle);
		
		klsignCell = dataRow.createCell(22);
		 klsignCell.setCellValue(klSign);
	    klsignCell.setCellStyle(contentStyle);
		
	    
	    cjSignCell = dataRow.createCell(23);
	    cjSignCell.setCellValue(cjSign);
	    cjSignCell.setCellStyle(contentStyle);
	    
		
			row = row + 1;
			num++;
		}
	}

	private static void wrapperHeader(Sheet dataSheet) {
		
		createTableTitleRow("待还款划扣",dataSheet,22);
		createTableDateRow(dataSheet,15);
		Row headerRow = dataSheet.createRow(2);
		
		Cell numHeader = headerRow.createCell(0);
		numHeader.setCellStyle(dateStyle);
		numHeader.setCellValue("序号");
		
        Cell htHeader = headerRow.createCell(1);
		htHeader.setCellStyle(dateStyle);
		htHeader.setCellValue("合同编号");              
	
	   Cell kexmHeader = headerRow.createCell(2);
		kexmHeader.setCellStyle(dateStyle);
		kexmHeader.setCellValue("客户姓名");
		
		Cell mdmcHeader = headerRow.createCell(3);
		mdmcHeader.setCellStyle(dateStyle);
		mdmcHeader.setCellValue("门店名称");
		
		Cell khhmcHeader = headerRow.createCell(4);
		khhmcHeader.setCellStyle(dateStyle);
		khhmcHeader.setCellValue(" 开户行名称");
	 
	   Cell pjqsHeader = headerRow.createCell(5);
	   pjqsHeader.setCellStyle(dateStyle);
	   pjqsHeader.setCellValue("批借期数");
	    
	   Cell sqhkrHeader = headerRow.createCell(6);
	   sqhkrHeader.setCellStyle(dateStyle);
	   sqhkrHeader.setCellValue("首期还款日");
	  
	  Cell sqhjrqHeader = headerRow.createCell(7);
		sqhjrqHeader.setCellStyle(dateStyle);
		sqhjrqHeader.setCellValue("申请还款日期");
    
	   Cell hkztHeader = headerRow.createCell(8);
		hkztHeader.setCellStyle(dateStyle);
		hkztHeader.setCellValue("还款状态");
	    
		Cell htjeHeader = headerRow.createCell(9);
		htjeHeader.setCellStyle(dateStyle);
		htjeHeader.setCellValue("合同金额");
	    
		Cell hkrHeader = headerRow.createCell(10);
		hkrHeader.setCellStyle(dateStyle);
		hkrHeader.setCellValue("还款日");
		  
		 Cell qgjeHeader = headerRow.createCell(11);
		 qgjeHeader.setCellStyle(dateStyle);
		 qgjeHeader.setCellValue("期供金额");
		
		Cell sqhkjeHeader = headerRow.createCell(12);
		sqhkjeHeader.setCellStyle(dateStyle);
		sqhkjeHeader.setCellValue("申请还款金额");
    
	    Cell jkztHeader = headerRow.createCell(13);
		jkztHeader.setCellStyle(dateStyle);
		jkztHeader.setCellValue("借款状态");
	    
		Cell qyptHeader = headerRow.createCell(14);
		qyptHeader.setCellStyle(dateStyle);
		qyptHeader.setCellValue("签约平台");
		
		Cell lbjeHeader = headerRow.createCell(15);
		lbjeHeader.setCellStyle(dateStyle);
		lbjeHeader.setCellValue("蓝补金额");
		
		Cell hpjgHeader = headerRow.createCell(16);
		hpjgHeader.setCellStyle(dateStyle);
		hpjgHeader.setCellValue("回盘结果");
		
        Cell qdHeader = headerRow.createCell(17);
		qdHeader.setCellStyle(dateStyle);
		qdHeader.setCellValue("渠道");		
			
	    Cell msHeader = headerRow.createCell(18);
		msHeader.setCellStyle(dateStyle);
		msHeader.setCellValue("模式");
			
		Cell tlplqyHeader = headerRow.createCell(19);
		tlplqyHeader.setCellStyle(dateStyle);
		tlplqyHeader.setCellValue("通联批量签约");
	
	    Cell wtczjgHeader = headerRow.createCell(20);
		wtczjgHeader.setCellStyle(dateStyle);
		wtczjgHeader.setCellValue("委托充值结果");
	
	    Cell wtczsbyyHeader = headerRow.createCell(21);
		wtczsbyyHeader.setCellStyle(dateStyle);
		wtczsbyyHeader.setCellValue("委托充值失败原因 ");
  
        Cell klsfqyHeader = headerRow.createCell(22);
		klsfqyHeader.setCellStyle(dateStyle);
		klsfqyHeader.setCellValue("卡联是否签约"); 
		
	    Cell  cjsfqyHeader = headerRow.createCell(23);
	    cjsfqyHeader.setCellStyle(dateStyle);
	    cjsfqyHeader.setCellValue("畅捷是否签约"); 
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
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,23);
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