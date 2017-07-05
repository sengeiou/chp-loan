package com.creditharmony.loan.borrow.zcj.excel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 导出回息数据帮助类
 * @Class Name ExportGCBusinessHelper
 * @author 张建雄
 * @Create In 2016年4月25日
 */
@SuppressWarnings("deprecation")
public class ExportZcjHelper {
	private static Logger logger = LoggerFactory.getLogger(ExportZcjHelper.class);

	public static void customerCallTableExport(Map<String, Object> queryMap,
			HttpServletResponse response,UserManager userManager,String fileName) {
		//列数
		final int MAXCOLUMN = 19;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			//String fileSheetName = threePartFileName.getGoldCreditExportFileName();
			Sheet dataSheet = wb.createSheet(fileName);
			title(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.SendMoneyDao.getAllMoneyList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			bodyExcelCell(wb,resultSet, dataSheet,MAXCOLUMN,userManager);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName + FileExtension.XLSX)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName + FileExtension.XLSX));
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
	 * 汇总表导出
	 * @param queryMap 查询条件
	 * @param response 相应
	 */
	public static void summarySheetExport(Map<String, Object> queryMap,
			HttpServletResponse response,UserManager userManager,String fileName) {
		//列数
		final int MAXCOLUMN = 31;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			//String fileSheetName = threePartFileName.getGoldCreditSumExportFileName();
			Sheet dataSheet = wb.createSheet(fileName);
			summarySheet(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.GrantSumDao.getSumListByloanCodes",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			summarySheetExcelCell(wb,resultSet, dataSheet,MAXCOLUMN,userManager);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+ FileExtension.XLSX)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+ FileExtension.XLSX));
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
	
	private static void title(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
		Row titleRow = dataSheet.createRow(0);
		titleRow.setHeight((short) (15.625*40)); 
		Cell titleCell = titleRow.createCell(0);
		dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,MAXCOLUMN));
		//创建样式
		CellStyle style = wb.createCellStyle(); 
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平
		
        //创建字体
        Font font = wb.createFont();
        //字体位置  上 下 左 右
        //font.setTypeOffset((short)0);
        //字体宽度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //字体高度
        font.setFontHeightInPoints((short)16);
        style.setFont(font);
        titleCell.setCellValue(fileName);
        titleCell.setCellStyle(style);
        
        style = wb.createCellStyle();
        style.setBorderBottom((short) 1);   
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setFillPattern(CellStyle.FINE_DOTS);
        style.setFillBackgroundColor(new HSSFColor.DARK_BLUE().getIndex());
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(new HSSFColor.WHITE().getIndex());
        font.setFontHeightInPoints((short)10);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        
        String[] header = {"放款时间","序号","地区","合同编号","账户名","证件号码","期数","借款利率","合同金额","实放金额","催收服务费","账户","开户行","支行名称","渠道",
        		"合同版本号","是否加急","是否无纸化","是否有保证人","回访状态"};
		Row headerRow = dataSheet.createRow(1);
		for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
	}
	private static void bodyExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN,UserManager userManager)
			throws SQLException {
		int row = 2;
		
		Row dataRow;
		BigDecimal grantAmount = new BigDecimal(0);
		BigDecimal contractAmount = new BigDecimal(0);
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell lendCodeCell = dataRow.createCell(0);
			lendCodeCell.setCellValue("");
			lendCodeCell.setCellStyle(textStyle(wb));
			
			Cell accountNoCell = dataRow.createCell(1);
			accountNoCell.setCellValue(resultSet.getString("indexs"));
			accountNoCell.setCellStyle(textStyle(wb));
			
			Cell accountNameCell = dataRow.createCell(2);
			accountNameCell.setCellValue(resultSet.getString("storesName"));
			accountNameCell.setCellStyle(textStyle(wb));
			
			Cell backMoneyCell = dataRow.createCell(3);
			backMoneyCell.setCellValue(resultSet.getString("contract_code"));
			backMoneyCell.setCellStyle(textStyle(wb));
			
			Cell accountBankCell = dataRow.createCell(4);
			accountBankCell.setCellValue(resultSet.getString("customer_name"));
			accountBankCell.setCellStyle(textStyle(wb));
			
			Cell accountBranchCell = dataRow.createCell(5);
			accountBranchCell.setCellValue(resultSet.getString("customer_cert_num"));
			accountBranchCell.setCellStyle(textStyle(wb));
			
			Cell accountCardOrBookletCell = dataRow.createCell(6);
			accountCardOrBookletCell.setCellValue(resultSet.getString("contract_months"));
			/*accountCardOrBookletCell.setCellValue(Double.valueOf(33));*/
			accountCardOrBookletCell.setCellStyle(textStyle(wb));
			
			Cell monthRate = dataRow.createCell(7);
			monthRate.setCellValue(praseDoble(resultSet.getString("monthRate")));
			monthRate.setCellType(Cell.CELL_TYPE_NUMERIC);
			monthRate.setCellStyle(decimal3Style(wb));
			
			
			String contractAmount1 = resultSet.getString("contractAmount");
			Cell provinceCell = dataRow.createCell(8);
			provinceCell.setCellValue(praseDoble(contractAmount1));
			provinceCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			provinceCell.setCellStyle(decimal2Style(wb));
			
			
			String grantAmount1 = resultSet.getString("grantAmount");
			Cell cityCell = dataRow.createCell(9);
			cityCell.setCellValue(praseDoble(grantAmount1));
			cityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cityCell.setCellStyle(decimal2Style(wb));
			
			
			Cell productNameCell = dataRow.createCell(10);
			productNameCell.setCellValue(praseDoble(resultSet.getString("feeUrgedService")));
			productNameCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			productNameCell.setCellStyle(decimal2Style(wb));
			
			Cell applyLendDayCell = dataRow.createCell(11);
			applyLendDayCell.setCellValue(resultSet.getString("bank_account"));
			applyLendDayCell.setCellStyle(textStyle(wb));
			
			Cell applyLendMoneyCell = dataRow.createCell(12);
			applyLendMoneyCell.setCellValue(resultSet.getString("bank_name"));
			applyLendMoneyCell.setCellStyle(textStyle(wb));
			
			Cell applyPayCell = dataRow.createCell(13);
			applyPayCell.setCellValue(resultSet.getString("bank_branch"));
			applyPayCell.setCellStyle(textStyle(wb));
			
			Cell lendingTimeCell = dataRow.createCell(14);
			lendingTimeCell.setCellValue(resultSet.getString("CHANELL_FLAG"));
			lendingTimeCell.setCellStyle(textStyle(wb));
			
			Cell contract_version = dataRow.createCell(15);
			String version = DictCache.getInstance().getDictLabel("jk_contract_ver", resultSet.getString("contract_version"));
			if(StringHelper.isEmpty(version))
			{
				version = resultSet.getString("contract_version");
			}
			contract_version.setCellValue(version);
			contract_version.setCellStyle(textStyle(wb));
			
			Cell adf = dataRow.createCell(16);
			adf.setCellValue(resultSet.getString("LABEL"));
			adf.setCellStyle(textStyle(wb));
			
			Cell paperLess = dataRow.createCell(17);
			paperLess.setCellValue(resultSet.getString("paperless"));
			paperLess.setCellStyle(textStyle(wb));
			
			Cell ensureMan = dataRow.createCell(18);
			ensureMan.setCellValue(resultSet.getString("ensureman"));
			ensureMan.setCellStyle(textStyle(wb));
			
			Cell revisitStatusCell = dataRow.createCell(19);
			String revisitStatus = resultSet.getString("revisit_status");
			String revisitStatusName = "";
			if (revisitStatus != null && !"".equals(revisitStatus)) {
				if (GrantCommon.REVISIT_STATUS_SUCCESS_CODE.equals(revisitStatus)) {
					revisitStatusName = GrantCommon.REVISIT_STATUS_SUCCESS;
				} else if (GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(revisitStatus)) {
					revisitStatusName = GrantCommon.REVISIT_STATUS_WAIT;
				} else if (GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(revisitStatus)) {
					revisitStatusName = GrantCommon.REVISIT_STATUS_FAIL;
				}
			}
			revisitStatusCell.setCellValue(revisitStatusName);
			revisitStatusCell.setCellStyle(textStyle(wb));
			row = row + 1;
			
			grantAmount = grantAmount.add(BigDecimal.valueOf(Double.parseDouble(grantAmount1)));
			contractAmount = contractAmount.add(BigDecimal.valueOf(Double.parseDouble(contractAmount1)));
		}
		//添加页尾
		//获取总行数
		int rowNum = row;
		Row footerRow = dataSheet.createRow(rowNum);
		for (int i = 0; i <= MAXCOLUMN; i++) {
			Cell footerCell = footerRow.createCell(i);
			footerCell.setCellStyle(textStyle(wb));
			if (i == 8) {
				footerCell.setCellValue("合同总金额:" + format(contractAmount+"", "#,##0.00"));
			}
			if (i == 9) {
				footerCell.setCellValue("放款总金额:" + format(grantAmount+"", "#,##0.00"));
			}
		}
		setAutoColumn(MAXCOLUMN, dataSheet);
	}
	
	private static void summarySheet(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
		Row titleRow = dataSheet.createRow(0);
		titleRow.setHeight((short) (15.625*40)); 
		Cell titleCell = titleRow.createCell(0);
		dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,MAXCOLUMN));
		//创建样式
		CellStyle style = wb.createCellStyle(); 
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平
		
        //创建字体
        Font font = wb.createFont();
        //字体位置  上 下 左 右
        //font.setTypeOffset((short)0);
        //字体宽度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //字体高度
        font.setFontHeightInPoints((short)16);
        style.setFont(font);
        titleCell.setCellValue(fileName);
        titleCell.setCellStyle(style);
        
        style = wb.createCellStyle();
        style.setBorderBottom((short) 1);   
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setFillPattern(CellStyle.FINE_DOTS);
        style.setFillBackgroundColor(new HSSFColor.DARK_BLUE().getIndex());
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(new HSSFColor.WHITE().getIndex());
        font.setFontHeightInPoints((short)10);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        
        String[] header = {"序号","合同编号","借款人姓名(共借人)","自然人保证人","批借金额","合同金额","实放金额","外访费","前期咨询费","前期审核费","前期居间服务费","前期信息服务费","前期综合服务费",
        		"催收服务费","分期服务费","月还金额","产品种类","期数","风险等级","总费率（%）","月利率（%）","合同日期","首期还款日","加急费","渠道","合同版本号","是否加急","是否无纸化","是否有保证人",
        		"是否电销","划扣平台","推送日期"};
		Row headerRow = dataSheet.createRow(1);
		for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
	}
	private static void summarySheetExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN,UserManager userManager)
			throws SQLException {
		int row = 2;
		CellStyle style = wb.createCellStyle();
	        style.setBorderBottom((short) 1);   
	        style.setBorderTop((short) 1);
	        style.setBorderLeft((short) 1);
	        style.setBorderRight((short) 1);
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        style.setFont(font);
		
		Row dataRow;
		while (resultSet.next()) {
			int i = 0;
			dataRow = dataSheet.createRow(row);
			Cell lendCodeCell = dataRow.createCell(i++);
			lendCodeCell.setCellStyle(textStyle(wb));
			lendCodeCell.setCellValue(resultSet.getString("sequenceNumber"));
			
			Cell accountNoCell = dataRow.createCell(i++);
			accountNoCell.setCellStyle(textStyle(wb));
			accountNoCell.setCellValue(resultSet.getString("contract_code"));
			
			Cell accountNameCell = dataRow.createCell(i++);
			accountNameCell.setCellStyle(textStyle(wb));
			accountNameCell.setCellValue(resultSet.getString("customerName"));
			
			Cell naturalPersonNameCell = dataRow.createCell(i++);
			naturalPersonNameCell.setCellStyle(textStyle(wb));
			naturalPersonNameCell.setCellValue(resultSet.getString("naturalPersonName"));
			
			
			Cell accountBankCell = dataRow.createCell(i++);
			accountBankCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			accountBankCell.setCellStyle(decimal2Style(wb));
			accountBankCell.setCellValue(praseDoble(resultSet.getString("auditAmount")));//批借
			
			Cell accountBranchCell = dataRow.createCell(i++);
			accountBranchCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			accountBranchCell.setCellStyle(decimal2Style(wb));
			accountBranchCell.setCellValue(praseDoble(resultSet.getString("contractAmount")));//合同金额
			
			Cell accountCardOrBookletCell = dataRow.createCell(i++);
			accountCardOrBookletCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			accountCardOrBookletCell.setCellStyle(decimal2Style(wb));
			accountCardOrBookletCell.setCellValue(praseDoble(resultSet.getString("feePaymentAmount")));//实放
			
			Cell cityCell = dataRow.createCell(i++);
			cityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cityCell.setCellStyle(decimal2Style(wb));
			cityCell.setCellValue(praseDoble(resultSet.getString("feePetition")));//外访费
			
			Cell productNameCell = dataRow.createCell(i++);
			productNameCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			productNameCell.setCellStyle(decimal2Style(wb));
			productNameCell.setCellValue(praseDoble(resultSet.getString("feeConsult")));
			
			Cell applyLendDayCell = dataRow.createCell(i++);
			applyLendDayCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			applyLendDayCell.setCellStyle(decimal2Style(wb));
			applyLendDayCell.setCellValue(praseDoble(resultSet.getString("feeAuditAmount")));
			
			Cell applyLendMoneyCell = dataRow.createCell(i++);
			applyLendMoneyCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			applyLendMoneyCell.setCellStyle(decimal2Style(wb));
			applyLendMoneyCell.setCellValue(praseDoble(resultSet.getString("feeService")));
			
			Cell applyPayCell = dataRow.createCell(i++);
			applyPayCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			applyPayCell.setCellStyle(decimal2Style(wb));
			applyPayCell.setCellValue(praseDoble(resultSet.getString("feeInfoService")));
			
			Cell lendingTimeCell = dataRow.createCell(i++);
			lendingTimeCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			lendingTimeCell.setCellStyle(decimal2Style(wb));
			lendingTimeCell.setCellValue(praseDoble(resultSet.getString("feeCount")));
			
			Cell CONTRACT_END_DAY = dataRow.createCell(i++);
			CONTRACT_END_DAY.setCellType(Cell.CELL_TYPE_NUMERIC);
			CONTRACT_END_DAY.setCellStyle(decimal2Style(wb));
			CONTRACT_END_DAY.setCellValue(praseDoble(resultSet.getString("feeUrgedService")));
			
			Cell month_fee_service = dataRow.createCell(i++);
			month_fee_service.setCellType(Cell.CELL_TYPE_NUMERIC);
			month_fee_service.setCellStyle(decimal2Style(wb));
			month_fee_service.setCellValue(praseDoble(resultSet.getString("month_fee_service")));
			
			Cell contract_month_repay_total = dataRow.createCell(i++);
			contract_month_repay_total.setCellType(Cell.CELL_TYPE_NUMERIC);
			contract_month_repay_total.setCellStyle(decimal2Style(wb));
			contract_month_repay_total.setCellValue(praseDoble(resultSet.getString("contract_month_repay_total")));
			
			Cell adf = dataRow.createCell(i++);
			adf.setCellStyle(textStyle(wb));
			adf.setCellValue(resultSet.getString("product_name"));
			
			Cell contract_months = dataRow.createCell(i++);
			contract_months.setCellStyle(textStyle(wb));
			contract_months.setCellValue( resultSet.getString("contract_months"));
			
			Cell risk_level = dataRow.createCell(i++);
			risk_level.setCellStyle(textStyle(wb));
			risk_level.setCellValue(resultSet.getString("riskLevel"));//风险定价
			
			Cell fee_all_raio = dataRow.createCell(i++);
			fee_all_raio.setCellType(Cell.CELL_TYPE_NUMERIC);
			fee_all_raio.setCellStyle(decimal3Style(wb));
			fee_all_raio.setCellValue(praseDoble(resultSet.getString("fee_all_raio")));
			
			Cell fee_month_rate = dataRow.createCell(i++);
			fee_month_rate.setCellType(Cell.CELL_TYPE_NUMERIC);
			fee_month_rate.setCellStyle(decimal3Style(wb));
			fee_month_rate.setCellValue(praseDoble(resultSet.getString("fee_month_rate")));
			
			Cell contract_fact_day = dataRow.createCell(i++);
			contract_fact_day.setCellStyle(dateStyle(wb));
			contract_fact_day.setCellValue(resultSet.getString("contract_fact_day"));
			
			Cell contract_replay_day = dataRow.createCell(i++);
			contract_replay_day.setCellStyle(dateStyle(wb));
			contract_replay_day.setCellValue(resultSet.getString("CONTRACTREPLAYDAY"));
			
			Cell feeExpedited = dataRow.createCell(i++);
			feeExpedited.setCellType(Cell.CELL_TYPE_NUMERIC);
			feeExpedited.setCellStyle(decimal2Style(wb));
			feeExpedited.setCellValue(praseDoble(resultSet.getString("feeExpedited")));
			
			Cell loan_flag = dataRow.createCell(i++);
			loan_flag.setCellStyle(textStyle(wb));
			//loan_flag.setCellValue(DictCache.getInstance().getDictLabel("jk_channel_flag", resultSet.getString("loan_flag")));
			loan_flag.setCellValue(resultSet.getString("CHANELL_FLAG"));
			
			Cell contractVersion = dataRow.createCell(i++);
			String version = DictCache.getInstance().getDictLabel("jk_contract_ver", resultSet.getString("contract_version"));
			if(StringHelper.isEmpty(version))
			{
				version = resultSet.getString("contract_version");
			}
			contractVersion.setCellStyle(textStyle(wb));
			contractVersion.setCellValue(version);
			
			Cell loan_urgent_flag = dataRow.createCell(i++);
			loan_urgent_flag.setCellStyle(textStyle(wb));
			loan_urgent_flag.setCellValue(DictCache.getInstance().getDictLabel("jk_urgent_flag", resultSet.getString("loan_urgent_flag")));
			
			Cell paperless = dataRow.createCell(i++);
			paperless.setCellStyle(textStyle(wb));
			paperless.setCellValue(resultSet.getString("PAPERLESS"));
			
			Cell ensureman = dataRow.createCell(i++);
			ensureman.setCellStyle(textStyle(wb));
			ensureman.setCellValue(resultSet.getString("ENSUREMAN"));
			
			Cell customer_telesales_flag = dataRow.createCell(i++);
			customer_telesales_flag.setCellStyle(textStyle(wb));
			customer_telesales_flag.setCellValue(DictCache.getInstance().getDictLabel("jk_telemarketing", resultSet.getString("customer_telesales_flag")));
			
			Cell BANKSIGNINGPLATFORM = dataRow.createCell(i++);
			BANKSIGNINGPLATFORM.setCellStyle(textStyle(wb));
			BANKSIGNINGPLATFORM.setCellValue(resultSet.getString("BANKSIGNINGPLATFORM"));
			
			Cell nowDate = dataRow.createCell(i++);
			nowDate.setCellStyle(dateStyle(wb));
			nowDate.setCellValue(DateUtils.getDate("yyyy-MM-dd"));
			row = row + 1;
		}
		setAutoColumn(MAXCOLUMN, dataSheet);
	}
	
	private static Double praseDoble(String num){
		Double number = 0.00;
		 String reg = "^\\d+(\\.\\d+)?$";
		 Pattern p = Pattern.compile(reg);  
		if (StringUtils.isNotBlank(num) && p.matcher(num).matches()) {
			number = Double.valueOf(num);
		}
		return number;
	}
	
	private static CellStyle decimal3Style(SXSSFWorkbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom((short) 1);   
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
        DataFormat hssfDataFormat = wb.createDataFormat(); 
        cellStyle.setDataFormat(hssfDataFormat.getFormat("#,##0.000_);(#,##0.000)"));
        return cellStyle;
	}
	
	
	private static CellStyle dateStyle(SXSSFWorkbook wb){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom((short) 1);   
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
        DataFormat format= wb.createDataFormat();

        cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));

        return cellStyle;
	}
	
	//保留两位小数格式
		private static CellStyle decimal2Style(SXSSFWorkbook wb){
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);   
	        cellStyle.setBorderTop((short) 1);
	        cellStyle.setBorderLeft((short) 1);
	        cellStyle.setBorderRight((short) 1);
	        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        cellStyle.setFont(font);
	        
	        DataFormat hssfDataFormat = wb.createDataFormat(); 
	        cellStyle.setDataFormat(hssfDataFormat.getFormat("#,##0.00_);(#,##0.00)"));
	        /*newCell.setCellStyle(cellStyle);
	        newCell.setCellValue(new Double(cellVal));
	        newCell.setCellType(Cell.CELL_TYPE_NUMERIC);*/
	        
	        return cellStyle;
		}
		
	//文本格式
		private static CellStyle textStyle(SXSSFWorkbook wb) {
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);   
	        cellStyle.setBorderTop((short) 1);
	        cellStyle.setBorderLeft((short) 1);
	        cellStyle.setBorderRight((short) 1);
	        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        cellStyle.setFont(font);
			DataFormat format = wb.createDataFormat();  
	        cellStyle.setDataFormat(format.getFormat("@"));
	        return cellStyle;
		}
		
		private static void setAutoColumn (int maxColumn,Sheet sheet) {
			//获取当前列的宽度，然后对比本列的长度，取最大值  
			for (int columnNum = 0; columnNum <= maxColumn; columnNum++) {  
			    int columnWidth = sheet.getColumnWidth(columnNum) / 256;  
			    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {  
			        Row currentRow;  
			        //当前行未被使用过  
			        if (sheet.getRow(rowNum) == null) {  
			            currentRow = sheet.createRow(rowNum);  
			        } else {  
			            currentRow = sheet.getRow(rowNum);  
			        }  
			        if(currentRow.getCell(columnNum) != null) {  
			            Cell currentCell = currentRow.getCell(columnNum); 
			            if(StringUtils.isNotEmpty(currentCell.toString())) {
			            	int length = currentCell.toString().getBytes().length; 
				            if (columnWidth < length) {  
				                columnWidth = length;  
				            } 
			            }
			        }  
			        sheet.setColumnWidth(columnNum, columnWidth * 256);
			    }  
			}  
		}
		
		private static String format (String data,String pattern) {
			String formatData = "";
			NumberFormat nf = new DecimalFormat(pattern);
			if (StringUtils.isNotEmpty(data)) 
				formatData = nf.format(Double.parseDouble(data));
			return formatData;
		}
}
