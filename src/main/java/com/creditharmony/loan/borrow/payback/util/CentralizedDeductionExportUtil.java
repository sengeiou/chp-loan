package com.creditharmony.loan.borrow.payback.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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


public class CentralizedDeductionExportUtil {
    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体

	protected static Logger logger = LoggerFactory
			.getLogger(CentralizedDeductionExportUtil.class);

	public static void exportData(PaybackApply paybackApply,
			HttpServletResponse response, String fileName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			titleStyle = wb.createCellStyle();
			titleFont = wb.createFont();
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet,fileName);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.payback.dao.CentralizedDeductionDao.redlineExportExcel",
							paybackApply, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(resultSet, dataSheet,dictMap);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+".xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+".xlsx"));
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

	private static void wrapperHeader(Sheet dataSheet,String title) {
		Row headerRow = dataSheet.createRow(0);
		Cell titleHeader = headerRow.createCell(0);
		titleHeader.setCellValue(title);
		createTableTitleRow(title,dataSheet,20);
		headerRow = dataSheet.createRow(1);
		Cell contractCodeHeader = headerRow.createCell(0);
		contractCodeHeader.setCellValue("合同编号");
		
		Cell customerNameHeader = headerRow.createCell(1);
		customerNameHeader.setCellValue("客户姓名");
		
		Cell orgNameHeader = headerRow.createCell(2);
		orgNameHeader.setCellValue("门店名称");
		
		Cell contractMonthsHeader = headerRow.createCell(3);
		contractMonthsHeader.setCellValue("批借期数");
		
		Cell contractReplayDayHeader = headerRow.createCell(4);
		contractReplayDayHeader.setCellValue("首期还款期");
		
		Cell applyBankNameHeader = headerRow.createCell(5);
		applyBankNameHeader.setCellValue("开户行名称");
		
		Cell dictPayStatusHeader = headerRow.createCell(6);
		dictPayStatusHeader.setCellValue("还款状态");
		
		Cell huankLxHeader = headerRow.createCell(7);
		huankLxHeader.setCellValue("还款类型");
		
		Cell applyDeductAmountHeader = headerRow.createCell(8);
		applyDeductAmountHeader.setCellValue("划扣金额");
		
		Cell applyReallyAmountHeader = headerRow.createCell(9);
		applyReallyAmountHeader.setCellValue("实还金额");
		
		Cell huankTypeHeader = headerRow.createCell(10);
		huankTypeHeader.setCellValue("还款方式");
		
		Cell createTimeiHeader = headerRow.createCell(11);
		createTimeiHeader.setCellValue("划扣日期");
		
		Cell monthPayDayHeader = headerRow.createCell(12);
		monthPayDayHeader.setCellValue("还款日");
	
		Cell dictLoanStatusHeader = headerRow.createCell(13);
		dictLoanStatusHeader.setCellValue("借款状态");
		
		Cell plantHeader = headerRow.createCell(14);
		plantHeader.setCellValue("划扣平台");
		
		Cell splitBackResultHeader = headerRow.createCell(15);
		splitBackResultHeader.setCellValue("回盘结果");
		
		Cell failReasonHeader = headerRow.createCell(16);
		failReasonHeader.setCellValue("失败原因");
		
		Cell paybackBuleAmountHeader = headerRow.createCell(17);
		paybackBuleAmountHeader.setCellValue("蓝补金额");
		
		Cell modelHeader = headerRow.createCell(18);
		modelHeader.setCellValue("模式");
		
		Cell loanMarkHeader = headerRow.createCell(19);
		loanMarkHeader.setCellValue("渠道");
		
		Cell accountHeader = headerRow.createCell(20);
		accountHeader.setCellValue("账户");
	}

	private static void assembleExcelCell(ResultSet resultSet, Sheet dataSheet,Map<String,Dict> dictMap)
			throws SQLException {
		int row = 2;
		String contractCode;
		String customerName;
		String orgName;
		String applyBankName;
		String contractMonths;
		String contractReplayDay;
		String applyReallyAmount;
		String applyDeductAmount;
		String dictPayStatus;
		String huankType;
		String dictLoanStatus;
		String createTimei;
		String monthPayDay;
		String splitBackResult;
		String loanMark;
		String paybackBuleAmount;
		String bankAccount;
		String plant;
		String failReason;
		String model;
		String dictRepayMethod;
		
		Row dataRow;
		Cell contractCell;
		Cell customerNameCell;
		Cell orgNameCell;
		Cell applyBankNameCell;
		Cell contractMonthsCell;
		Cell contractReplayDayCell;
		Cell applyReallyAmountCell;
		/*Cell paybackMonthAmountCell;
		Cell notPaybackMonthAmountCell;
		Cell alsoPaybackMonthAmountCell;*/
		Cell applyDeductAmountCell;
		Cell dictPayStatusCell;
		Cell huankTypeCell;
		Cell dictLoanStatusCell;
		Cell createTimeiCell;
		Cell monthPayDayCell;
		Cell loanMarkCell;
		Cell splitBackResultCell ;
		Cell paybackBuleAmountCell;
		Cell bankAccountCell;
		Cell plantCell;
		Cell failReasonCell;
		Cell modelCell;
		Cell dictRepayMethodCell;
		while (resultSet.next()) {
			contractCode = resultSet.getString("contractCode");
			customerName = resultSet.getString("customerName");
			orgName = resultSet.getString("orgName");
			applyBankName =  DictUtils.getLabel(dictMap,"jk_open_bank",resultSet.getString("applyBankName"));
			contractMonths = resultSet.getString("contractMonths");
			contractReplayDay = resultSet.getString("contractReplayDay");
			applyReallyAmount = resultSet.getString("applyReallyAmount");
			/*paybackMonthAmount = resultSet.getString("paybackMonthAmount");
			notPaybackMonthAmount = resultSet.getString("applyAmount");
			alsoPaybackMonthAmount = resultSet.getString("alsoPaybackMonthAmount");*/
			/*if(resultSet.getString("contractMonthRepayAmount").equals(0)){
				alsoPaybackMonthAmount = DeductedConstantEx.ALSO_AMOUNT;
			}else{
				alsoPaybackMonthAmount = new BigDecimal(resultSet.getString("contractMonthRepayAmount")).subtract(new BigDecimal(notPaybackMonthAmount)).toString();
			}*/
			applyDeductAmount = resultSet.getString("applyDeductAmount");
			dictPayStatus =  DictUtils.getLabel(dictMap,"jk_repay_status",resultSet.getString("dictPayStatus"));
			huankType = "";//resultSet.getString("huankType");
			dictLoanStatus =  DictUtils.getLabel(dictMap,"jk_loan_status",resultSet.getString("dictLoanStatus"));
			createTimei = resultSet.getString("modifyTime");
			monthPayDay = resultSet.getString("paybackDay");
			loanMark = 	 DictUtils.getLabel(dictMap,"jk_channel_flag",resultSet.getString("loanMark"));
			paybackBuleAmount = resultSet.getString("paybackBuleAmount");
			bankAccount = resultSet.getString("bankAccount");
			splitBackResult = resultSet.getString("splitBackResult");
			if(splitBackResult != null){
				splitBackResult =  DictUtils.getLabel(dictMap,"jk_counteroffer_result",splitBackResult);
			}
			plant = resultSet.getString("dictDealType");
			if(plant != null){
				plant =  DictUtils.getLabel(dictMap,"jk_deduct_plat",plant);
				
			}
			
			failReason = resultSet.getString("failReason");
			model =  resultSet.getString("model");
			if(model != null){
				 model =  DictUtils.getLabel(dictMap,"jk_loan_model",model);
			}
			
			dictRepayMethod  = resultSet.getString("dictRepayMethod");
			if(dictRepayMethod != null){
				dictRepayMethod =  DictUtils.getLabel(dictMap,"jk_repay_way",dictRepayMethod);
			}
			
			dataRow = dataSheet.createRow(row);
			contractCell = dataRow.createCell(0);
			contractCell.setCellValue(contractCode);
			
			customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellValue(customerName);
			
			orgNameCell = dataRow.createCell(2);
			orgNameCell.setCellValue(orgName);
			
			contractMonthsCell = dataRow.createCell(3);
			contractMonthsCell.setCellValue(contractMonths);
			
			contractReplayDayCell = dataRow.createCell(4);
			contractReplayDayCell.setCellValue(contractReplayDay);
			
			applyBankNameCell = dataRow.createCell(5);
			applyBankNameCell.setCellValue(applyBankName);
			
			dictPayStatusCell = dataRow.createCell(6);
			dictPayStatusCell.setCellValue(dictPayStatus);
			
			dictRepayMethodCell = dataRow.createCell(7);
			dictRepayMethodCell.setCellValue(dictRepayMethod);
			applyDeductAmountCell = dataRow.createCell(8);
			applyDeductAmountCell.setCellValue(applyDeductAmount);
			
			applyReallyAmountCell = dataRow.createCell(9);
			applyReallyAmountCell.setCellValue(applyReallyAmount);
			
			huankTypeCell = dataRow.createCell(10);
			huankTypeCell.setCellValue("划扣");
			
			createTimeiCell = dataRow.createCell(11);
			createTimeiCell.setCellValue(createTimei);

			monthPayDayCell = dataRow.createCell(12);
			monthPayDayCell.setCellValue(monthPayDay);
		
			dictLoanStatusCell = dataRow.createCell(13);
			dictLoanStatusCell.setCellValue(dictLoanStatus);
			
			plantCell = dataRow.createCell(14);
			plantCell.setCellValue(plant);
			
			splitBackResultCell = dataRow.createCell(15);
			splitBackResultCell.setCellValue(splitBackResult);
			
			failReasonCell = dataRow.createCell(16);
			failReasonCell.setCellValue(failReason);
			
			paybackBuleAmountCell = dataRow.createCell(17);
			paybackBuleAmountCell.setCellValue(paybackBuleAmount);
			
			modelCell = dataRow.createCell(18);
			modelCell.setCellValue(model);
			
			loanMarkCell = dataRow.createCell(19);
			loanMarkCell.setCellValue(loanMark);
			
			bankAccountCell = dataRow.createCell(20);
			bankAccountCell.setCellValue(bankAccount);
		
			row = row + 1;
		}
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
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(title);
    }

}
