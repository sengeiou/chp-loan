package com.creditharmony.loan.borrow.blue.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlueAmountEx;


public class PaybackBlueExportUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(PaybackBlueExportUtil.class);

	/**
	 * 导出蓝补基本信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param paybackBlueAmountEx
	 * @param response
	 * @param fileName
	 */
	public static void exportData(PaybackBlueAmountEx paybackBlueAmountEx,
			HttpServletResponse response, String fileName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.blue.dao.PaybackBlueDao.selectPaybackBlueAmoun",
							paybackBlueAmountEx, sqlSessionFactory);
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

	/**
	 * 文件标题
	 * 2016年5月19日
	 * By 王彬彬
	 * @param dataSheet
	 */
	private static void wrapperHeader(Sheet dataSheet) {
		Row headerRow = dataSheet.createRow(0);
		Cell lendCodeHeader = headerRow.createCell(0);
		lendCodeHeader.setCellValue("序号");
		Cell accountNoHeader = headerRow.createCell(1);
		accountNoHeader.setCellValue("交易时间");
		Cell accountNameHeader = headerRow.createCell(2);
		accountNameHeader.setCellValue("交易动作");
		Cell backMoneyHeader = headerRow.createCell(3);
		backMoneyHeader.setCellValue("操作人");
		Cell bankCodeHeader = headerRow.createCell(4);
		bankCodeHeader.setCellValue("交易用途");
		Cell offsetRepaymentDateHeader = headerRow.createCell(5);
		offsetRepaymentDateHeader.setCellValue("冲抵期数");
		Cell accountBankHeader = headerRow.createCell(6);
		accountBankHeader.setCellValue("交易金额");
		Cell accountBranchHeader = headerRow.createCell(7);
		accountBranchHeader.setCellValue("蓝补金额");
	}

	private static void assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		int row = 1;
		String dealTime;
		String tradeType;
		String operator;
		String dictDealUse;
		String tradeAmount = "0.00";
		String surplusBuleAmount = "0.00";
		String offsetRepaymentDate;
		Row dataRow;
		Cell rowCell;
		Cell dealTimeCell;
		Cell tradeTypeCell;
		Cell operatorCell;
		Cell dictDealUseCell;
		Cell offsetRepaymentDateCell;
		Cell tradeAmountCell;
		Cell surplusBuleAmountCell;
		DecimalFormat df = new DecimalFormat("0.00");
		while (resultSet.next()) {
			dealTime = resultSet.getString("dealTime");
			tradeType = resultSet.getString("tradeType");
			operator = resultSet.getString("operator");
			dictDealUse = resultSet.getString("dictDealUse");
			offsetRepaymentDate = resultSet.getString("offsetRepaymentDate");
			String tmpDictDealUse ="";
			if(StringUtils.isNotEmpty(dictDealUse)){
				tmpDictDealUse = String.valueOf(AgainstContent.parseByCode(dictDealUse));
			}
			if(StringUtils.isEmpty(tmpDictDealUse) || tmpDictDealUse.equals("null"))
			{
				tmpDictDealUse = dictDealUse;
			}
			tradeAmount = resultSet.getString("tradeAmount");
			surplusBuleAmount = resultSet.getString("surplusBuleAmount");
			
			dataRow = dataSheet.createRow(row);
			rowCell = dataRow.createCell(0);
			rowCell.setCellValue(row);
			dealTimeCell = dataRow.createCell(1);
			dealTimeCell.setCellValue(dealTime);
			tradeTypeCell = dataRow.createCell(2);
			tradeTypeCell.setCellValue(tradeType);
			operatorCell = dataRow.createCell(3);
			operatorCell.setCellValue(operator);
			dictDealUseCell = dataRow.createCell(4);
			dictDealUseCell.setCellValue(tmpDictDealUse);
			offsetRepaymentDateCell = dataRow.createCell(5);
			if(StringUtils.isEmpty(offsetRepaymentDate)) {
				offsetRepaymentDate = " ";
			}
			offsetRepaymentDateCell.setCellValue(offsetRepaymentDate);
			
			tradeAmountCell = dataRow.createCell(6);
			if (StringUtils.isNotEmpty(tradeAmount)) {
				tradeAmountCell.setCellValue(df.format(Double.parseDouble(tradeAmount)));
			} else {
				tradeAmountCell.setCellValue(tradeAmount);
			}
			
			surplusBuleAmountCell = dataRow.createCell(7);
			if(StringUtils.isNotEmpty(surplusBuleAmount)){
				surplusBuleAmountCell.setCellValue(df.format(Double.parseDouble(surplusBuleAmount)));
			}else{
				surplusBuleAmountCell.setCellValue(surplusBuleAmount);
			}
			row = row + 1;
		}
	}

}
