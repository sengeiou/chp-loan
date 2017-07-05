package com.creditharmony.loan.borrow.grant.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.common.consts.FileExtension;


public class GrantDeductsFYExportUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(GrantDeductsFYExportUtil.class);

	public static List<UrgeServiceFyEx> exportData(UrgeServicesMoneyEx urgeMoneyEx,
			HttpServletResponse response,String fileName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		List<UrgeServiceFyEx> list = new ArrayList();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao.getDeductsFy",
							 urgeMoneyEx, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			list = assembleExcelCell(resultSet, dataSheet);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+".xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+FileExtension.XLSX));
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
		return list;
	}

	private static void wrapperHeader(Sheet dataSheet) {
		Row headerRow = dataSheet.createRow(0);
		Cell indexHeader = headerRow.createCell(0);
		indexHeader.setCellValue("序号");
		Cell bankNameHeader = headerRow.createCell(1);
		bankNameHeader.setCellValue("开户行");
		Cell bankAccountHeader = headerRow.createCell(2);
		bankAccountHeader.setCellValue("扣款人银行账号");
		Cell bankAccountNameHeader = headerRow.createCell(3);
		bankAccountNameHeader.setCellValue("户名");
		Cell splitAmountHeader = headerRow.createCell(4);
		splitAmountHeader.setCellValue("金额(单位:元)");
		Cell enterpriseSerialnoHeader = headerRow.createCell(5);
		enterpriseSerialnoHeader.setCellValue("企业流水账号");
		Cell remarksHeader = headerRow.createCell(6);
		remarksHeader.setCellValue("备注");
		Cell customerPhoneFirstHeader = headerRow.createCell(7);
		customerPhoneFirstHeader.setCellValue("手机号");
		Cell dictertTypeHeader = headerRow.createCell(8);
		dictertTypeHeader.setCellValue("证件类型");
		Cell customerCertNumHeader = headerRow.createCell(9);
		customerCertNumHeader.setCellValue("证件号");
	}

	private static List<UrgeServiceFyEx> assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		List<UrgeServiceFyEx> list = new ArrayList();
		int row = 1;
		String id;
		String index;
		String bankName;
		String bankAccount;
		String bankAccountName;
		String splitAmount;
		String enterpriseSerialno;
		String remarks;
		String customerPhoneFirst;
		String dictertType;
		String customerCertNum;

		Row dataRow;
		Cell indexCell;
		Cell bankNameCell;
		Cell bankAccountCell;
		Cell bankAccountNameCell;
		Cell splitAmountCell;
		Cell enterpriseSerialnoCell;
		Cell remarksCell;
		Cell customerPhoneFirstCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		while (resultSet.next()) {
			UrgeServiceFyEx fy = new UrgeServiceFyEx();
			id = resultSet.getString("id");
			index = String.format("%05d", row);
			
			BigDecimal splitAmount1 = new BigDecimal(resultSet.getString("splitAmount")).setScale(2, BigDecimal.ROUND_HALF_UP); 
			splitAmount = splitAmount1.toString();
			bankName = resultSet.getString("bankName");
			bankAccount = resultSet.getString("bankAccount");
			bankAccountName = resultSet.getString("bankAccountName");
			enterpriseSerialno = resultSet.getString("enterpriseSerialno");
			remarks = "代收";
			// 解密手机号
			customerPhoneFirst = GrantUtil.getNum(resultSet.getString("customerPhoneFirst"));
			dictertType=DictCache.getInstance().getDictLabel("jk_certificate_type",resultSet.getString("dictertType"));
			customerCertNum = resultSet.getString("customerCertNum");
			
			fy.setId(id);
			fy.setIndex(index);
			fy.setSplitAmount(splitAmount);
			fy.setBankName(bankName);
			fy.setBankAccount(bankAccountName);
			fy.setBankAccountName(bankAccountName);
			fy.setEnterpriseSerialno(enterpriseSerialno);
			fy.setRemarks(remarks);
			
			fy.setCustomerPhoneFirst(customerPhoneFirst);
			fy.setDictertType(dictertType);
			fy.setCustomerCertNum(customerCertNum);
			list.add(fy);
			
			dataRow = dataSheet.createRow(row);
			indexCell = dataRow.createCell(0);
			indexCell.setCellValue(index);
			bankNameCell = dataRow.createCell(1);
			bankNameCell.setCellValue(bankName);
			bankAccountCell = dataRow.createCell(2);
			bankAccountCell.setCellValue(bankAccount);
			bankAccountNameCell = dataRow.createCell(3);
			bankAccountNameCell.setCellValue(bankAccountName);
			splitAmountCell = dataRow.createCell(4);
			splitAmountCell.setCellValue(splitAmount);
			enterpriseSerialnoCell = dataRow.createCell(5);
			enterpriseSerialnoCell.setCellValue(enterpriseSerialno);
			remarksCell = dataRow.createCell(6);
			remarksCell.setCellValue(remarks);
			customerPhoneFirstCell = dataRow.createCell(7);
			customerPhoneFirstCell.setCellValue(customerPhoneFirst);
			dictertTypeCell = dataRow.createCell(8);
			dictertTypeCell.setCellValue(dictertType);
			customerCertNumCell = dataRow.createCell(9);
			customerCertNumCell.setCellValue(customerCertNum);
			
			row = row + 1;
		}
		return list;
	}

}
