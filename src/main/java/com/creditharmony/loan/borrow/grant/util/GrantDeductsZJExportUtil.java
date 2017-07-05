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
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceZJEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;


public class GrantDeductsZJExportUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(GrantDeductsZJExportUtil.class);

	public static List<UrgeServiceZJEx> exportData(UrgeServicesMoneyEx urgeMoneyEx,
			HttpServletResponse response, String fileName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		List<UrgeServiceZJEx> list = new ArrayList();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao.getDeductsZJ",
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
							+ Encodes.urlEncode(fileName+"ExportList.xlsx"));
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
		indexHeader.setCellValue("明细流水号");
		Cell splitAmountHeader = headerRow.createCell(1);
		splitAmountHeader.setCellValue("金额(元)");
		Cell bankNameHeader = headerRow.createCell(2);
		bankNameHeader.setCellValue("银行名称");
		Cell accountTypeHeader = headerRow.createCell(3);
		accountTypeHeader.setCellValue("账号类型");
		Cell bankAccountNameHeader = headerRow.createCell(4);
		bankAccountNameHeader.setCellValue("账户姓名");
		Cell bankAccountHeader = headerRow.createCell(5);
		bankAccountHeader.setCellValue("账户号码");
		Cell branchHeader = headerRow.createCell(6);
		branchHeader.setCellValue("分支行");
		Cell bankProvinceHeader = headerRow.createCell(7);
		bankProvinceHeader.setCellValue("省份");
		Cell bankCityHeader = headerRow.createCell(8);
		bankCityHeader.setCellValue("城市");
		Cell clearingFlagHeader = headerRow.createCell(9);
		clearingFlagHeader.setCellValue("结算标识");
		Cell enterpriseSerialnoHeader = headerRow.createCell(10);
		enterpriseSerialnoHeader.setCellValue("备注");
		Cell dictertTypeHeader = headerRow.createCell(11);
		dictertTypeHeader.setCellValue("证件类型");
		Cell customerCertNumHeader = headerRow.createCell(12);
		customerCertNumHeader.setCellValue("证件号码");
		Cell customerPhoneFirstHeader = headerRow.createCell(13);
		customerPhoneFirstHeader.setCellValue("手机号");
		Cell emailHeader = headerRow.createCell(14);
		emailHeader.setCellValue("电子邮箱");
		Cell protocolNumberCodeHeader = headerRow.createCell(15);
		protocolNumberCodeHeader.setCellValue("协议用户编号");
		
	}

	private static List<UrgeServiceZJEx> assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		List<UrgeServiceZJEx> list = new ArrayList();
		int row = 1;
		String id;
		String index;
		String splitAmount;
		String bankName;
		String accountType = "";
		String bankAccountName;
		String bankAccount;
		String branch;
		String bankProvince;
		String bankCity;
		String clearingFlag = "";
		String enterpriseSerialno;
		String dictertType;
		String customerCertNum;
		String customerPhoneFirst;
		String email;
		String protocolNumberCode;
		Row dataRow;
		Cell indexCell;
		Cell splitAmountCell;
		Cell bankNameCell;
		Cell accountTypeCell;
		Cell bankAccountNameCell;
		Cell bankAccountCell;
		Cell branchCell;
		Cell bankProvinceCell;
		Cell bankCityCell;
		Cell clearingFlagCell;
		Cell enterpriseSerialnoCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		Cell customerPhoneFirstCell;
		Cell emailCell;
		Cell protocolNumberCodeCell;
		while (resultSet.next()) {
			UrgeServiceZJEx zj = new UrgeServiceZJEx();
			id = resultSet.getString("id");
			index = String.format("%05d", row);
			
			BigDecimal splitAmount1 = new BigDecimal(resultSet.getString("splitAmount")).setScale(2, BigDecimal.ROUND_HALF_UP); 
			splitAmount =splitAmount1.toString();
			bankName = resultSet.getString("bankName");
			bankAccountName = resultSet.getString("bankAccountName");
			bankAccount = resultSet.getString("bankAccount");
			branch = resultSet.getString("branch");
			bankProvince = resultSet.getString("bankProvince");
			bankCity = resultSet.getString("bankCity");
			enterpriseSerialno = resultSet.getString("enterpriseSerialno");
			dictertType = resultSet.getString("dictertType");
			customerCertNum = resultSet.getString("customerCertNum");
			// 解密手机号
			customerPhoneFirst = GrantUtil.getNum(resultSet.getString("customerPhoneFirst"));
			email = resultSet.getString("email");
			protocolNumberCode = resultSet.getString("protocolNumberCode");

			zj.setId(id);
			zj.setIndex(index);
			zj.setSplitAmount(new BigDecimal(splitAmount));
			zj.setBankName(bankName);
			zj.setAccountType(accountType);
			zj.setBankAccountName(bankAccountName);
			zj.setBankAccount(bankAccount);
			zj.setBranch(branch);
			zj.setBankProvince(bankProvince);
			zj.setBankCity(bankCity);
			zj.setClearingFlag(clearingFlag);
			zj.setEnterpriseSerialno(enterpriseSerialno);
			zj.setDictertType(dictertType);
			zj.setCustomerCertNum(customerCertNum);
			zj.setCustomerPhoneFirst(customerPhoneFirst);
			zj.setEmail(email);
			zj.setProtocolNumberCode(protocolNumberCode);
			list.add(zj);
			
			dataRow = dataSheet.createRow(row);
			indexCell = dataRow.createCell(0);
			indexCell.setCellValue(index);
			splitAmountCell = dataRow.createCell(1);
			splitAmountCell.setCellValue(splitAmount);
			bankNameCell = dataRow.createCell(2);
			bankNameCell.setCellValue(bankName);
			accountTypeCell = dataRow.createCell(3);
			accountTypeCell.setCellValue(accountType);
			bankAccountNameCell = dataRow.createCell(4);
			bankAccountNameCell.setCellValue(bankAccountName);
			bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellValue(bankAccount);
			branchCell = dataRow.createCell(6);
			branchCell.setCellValue(branch);
			bankProvinceCell = dataRow.createCell(7);
			bankProvinceCell.setCellValue(bankProvince);
			bankCityCell = dataRow.createCell(8);
			bankCityCell.setCellValue(bankCity);
			clearingFlagCell = dataRow.createCell(9);
			clearingFlagCell.setCellValue(clearingFlag);
			enterpriseSerialnoCell = dataRow.createCell(10);
			enterpriseSerialnoCell.setCellValue(enterpriseSerialno);
			dictertTypeCell = dataRow.createCell(11);
			dictertTypeCell.setCellValue(dictertType);
			customerCertNumCell = dataRow.createCell(12);
			customerCertNumCell.setCellValue(customerCertNum);
			customerPhoneFirstCell = dataRow.createCell(13);
			customerPhoneFirstCell.setCellValue(customerPhoneFirst);
			emailCell = dataRow.createCell(14);
			emailCell.setCellValue(email);
			protocolNumberCodeCell = dataRow.createCell(15);
			protocolNumberCodeCell.setCellValue(protocolNumberCode);
			row = row + 1;
		}
		return list;
	}

}
