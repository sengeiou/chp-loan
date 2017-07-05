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

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceHylEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;


public class GrantDeductsHYLExportUtil {
	protected static Logger logger = LoggerFactory
			.getLogger(GrantDeductsHYLExportUtil.class);

	public static List<UrgeServiceHylEx> exportData(UrgeServicesMoneyEx urgeMoneyEx,
			HttpServletResponse response,String fileName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		List<UrgeServiceHylEx> list = new ArrayList<UrgeServiceHylEx>();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao.getDeductsHyl",
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
		return list;
	}

	private static void wrapperHeader(Sheet dataSheet,String[][] title) {
		Row headerRow = dataSheet.createRow(0);
		Cell title1Header = headerRow.createCell(0);
		title1Header.setCellValue(title[0][0]);
		Cell title2Header = headerRow.createCell(1);
		title2Header.setCellValue(title[0][1]);
		Cell title3Header = headerRow.createCell(2);
		title3Header.setCellValue(title[0][2]);
		Cell title4Header = headerRow.createCell(3);
		title4Header.setCellValue(title[0][3]);
		Cell title5Header = headerRow.createCell(4);
		title5Header.setCellValue(title[0][4]);
		Cell title6Header = headerRow.createCell(5);
		title6Header.setCellValue(title[0][5]);
		
		headerRow = dataSheet.createRow(1);
		title1Header = headerRow.createCell(0);
		title1Header.setCellValue(title[1][0]);
		title2Header = headerRow.createCell(1);
		title2Header.setCellValue(title[1][1]);
		title3Header = headerRow.createCell(2);
		title3Header.setCellValue(title[1][2]);
		title4Header = headerRow.createCell(3);
		title4Header.setCellValue(title[1][3]);
		title5Header = headerRow.createCell(4);
		title5Header.setCellValue(title[1][4]);
		title6Header = headerRow.createCell(5);
		title6Header.setCellValue(title[1][5]);
	}
	
	private static void wrapperHeader(Sheet dataSheet) {
		Row headerRow = dataSheet.createRow(2);
		Cell indexHeader = headerRow.createCell(0);
		indexHeader.setCellValue("序号");
		Cell bankNetworkUserCodeHeader = headerRow.createCell(1);
		bankNetworkUserCodeHeader.setCellValue("银联网络用户编号");
		Cell bankCodeHeader = headerRow.createCell(2);
		bankCodeHeader.setCellValue("银行代码");
		Cell bankAccountHeader = headerRow.createCell(3);
		bankAccountHeader.setCellValue("账号");
		Cell bankAccountNameHeader = headerRow.createCell(4);
		bankAccountNameHeader.setCellValue("账户名");
		Cell bankProvinceHeader = headerRow.createCell(5);
		bankProvinceHeader.setCellValue("开户行所在省");
		Cell bankCityHeader = headerRow.createCell(6);
		bankCityHeader.setCellValue("开户行所在市");
		Cell bankNameHeader = headerRow.createCell(7);
		bankNameHeader.setCellValue("开户行名称");
		Cell accountTypeHeader = headerRow.createCell(8);
		accountTypeHeader.setCellValue("账号类型");
		Cell splitAmountHeader = headerRow.createCell(9);
		splitAmountHeader.setCellValue("金额");
		Cell currencyTypeHeader = headerRow.createCell(10);
		currencyTypeHeader.setCellValue("货币类型");
		Cell protocolNumberHeader = headerRow.createCell(11);
		protocolNumberHeader.setCellValue("协议号");
		Cell protocolNumberCodeHeader = headerRow.createCell(12);
		protocolNumberCodeHeader.setCellValue("协议用户编号");
		Cell dictertTypeHeader = headerRow.createCell(13);
		dictertTypeHeader.setCellValue("开户证件类型");
		Cell customerCertNumHeader = headerRow.createCell(14);
		customerCertNumHeader.setCellValue("证件号");
		Cell customerPhoneFirstHeader = headerRow.createCell(15);
		customerPhoneFirstHeader.setCellValue("手机号");
		Cell customUserNameHeader = headerRow.createCell(16);
		customUserNameHeader.setCellValue("自定义用户名");
		Cell remarkOneHeader = headerRow.createCell(17);
		remarkOneHeader.setCellValue("备注1");
		Cell remarkTwoHeader = headerRow.createCell(18);
		remarkTwoHeader.setCellValue("备注2");
		Cell enterpriseSerialnoHeader = headerRow.createCell(19);
		enterpriseSerialnoHeader.setCellValue("备注");
		Cell feedbackCodeHeader = headerRow.createCell(20);
		feedbackCodeHeader.setCellValue("反馈码");
		Cell reasonHeader = headerRow.createCell(21);
		reasonHeader.setCellValue("原因");
	}

	private static List<UrgeServiceHylEx> assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		List<UrgeServiceHylEx> list = new ArrayList<UrgeServiceHylEx>();
		int row = 3;
		String id;
		String index;
		String bankNetworkUserCode;
		String bankCode;
		String bankAccount;
		String bankAccountName;
		String bankProvince;
		String bankCity;
		String bankName;
		String accountType;
		String splitAmount;
		String currencyType;
		String protocolNumber;
		String protocolNumberCode;
		String dictertType;
		String customerCertNum;
		String customerPhoneFirst;
		String customUserName;
		String remarkOne;
		String remarkTwo;
		String enterpriseSerialno;
		String feedbackCode;
		String reason;
		
		Row dataRow;
		Cell indexCell;
		Cell bankNetworkUserCodeCell;
		Cell bankCodeCell;
		Cell bankAccountCell;
		Cell bankAccountNameCell;
		Cell bankProvinceCell;
		Cell bankCityCell;
		Cell bankNameCell;
		Cell accountTypeCell;
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
		int count = 0;
		double total = 0;
		while (resultSet.next()) {
			UrgeServiceHylEx hyl = new UrgeServiceHylEx();
			id = resultSet.getString("id");
			index = String.format("%05d", row);
			bankNetworkUserCode = "";
			bankCode = resultSet.getString("bankCode");
			bankAccount = resultSet.getString("bankAccount");
			bankAccountName = resultSet.getString("bankAccountName");
			bankProvince = resultSet.getString("bankProvince");
			bankCity = resultSet.getString("bankCity");
			bankName = resultSet.getString("bankName");
			accountType = "";
			
			
			BigDecimal splitAmount1 = new BigDecimal(resultSet.getString("splitAmount")).setScale(2, BigDecimal.ROUND_HALF_UP); 
			splitAmount =splitAmount1.toString();
			currencyType = "";
			protocolNumber = "";
			protocolNumberCode = "";
			dictertType = resultSet.getString("dictertType");
			customerCertNum = resultSet.getString("customerCertNum");
			// 解密手机号
			customerPhoneFirst = GrantUtil.getNum(resultSet.getString("customerPhoneFirst"));
			customUserName = "";
			remarkOne = "";
			remarkTwo = "";
			enterpriseSerialno = resultSet.getString("enterpriseSerialno");
			feedbackCode = "";
			reason = "";
			hyl.setId(id);
			hyl.setIndex(index);
			hyl.setBankNetworkUserCode(bankNetworkUserCode);
			hyl.setBankCode(bankCode);
			hyl.setBankAccount(bankAccount);
			hyl.setBankAccountName(bankAccountName);
			hyl.setBankProvince(bankProvince);
			hyl.setBankCity(bankCity);
			hyl.setBankName(bankName);
			hyl.setAccountType(accountType);
			hyl.setSplitAmount(new BigDecimal(splitAmount).multiply(new BigDecimal(100)));
			hyl.setCurrencyType(currencyType);
			hyl.setProtocolNumber(protocolNumberCode);
			hyl.setProtocolNumberCode(protocolNumberCode);
			hyl.setDictertType(dictertType);
			hyl.setCustomerCertNum(customerCertNum);
			hyl.setCustomerPhoneFirst(customerPhoneFirst);
			hyl.setCustomUserName(customUserName);
			hyl.setRemarkOne(remarkOne);
			hyl.setRemarkTwo(remarkTwo);
			hyl.setEnterpriseSerialno(enterpriseSerialno);
			hyl.setFeedbackCode(feedbackCode);
			hyl.setReason(reason);
			list.add(hyl);
			count++;
			if(splitAmount == null || "".equals(splitAmount)){
				splitAmount = "0";
			}
			    total += Double.parseDouble(splitAmount);
		    }
		        total = total * 100; 
		String[][] title = {{"代收付类型","商户ID","提交日期","总记录数","总金额","业务类型"},
				{"S",Global.getConfig("creditharmony.haoyilian.business.code"),
			DateUtils.getDate("yyyyMMdd"),String.valueOf(count),String.valueOf(total),"14900"}};
		wrapperHeader(dataSheet,title);
		wrapperHeader(dataSheet);
		for(UrgeServiceHylEx hyl : list){
			id = hyl.getId();
			index = String.format("%05d", row-2);
			bankNetworkUserCode = "";
			bankCode = hyl.getBankCode();
			bankAccount = hyl.getBankAccount();
			bankAccountName = hyl.getBankAccountName();
			bankProvince = hyl.getBankProvince();
			bankCity = hyl.getBankCity();
			bankName = hyl.getBankName();
			accountType = "";
			splitAmount = hyl.getSplitAmount().setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			currencyType = "";
			protocolNumber = "";
			protocolNumberCode = "";
			dictertType = hyl.getDictertType();
			customerCertNum = hyl.getCustomerCertNum();
			customerPhoneFirst = hyl.getCustomerPhoneFirst();
			customUserName = "";
			remarkOne = "";
			remarkTwo = "";
			enterpriseSerialno = hyl.getEnterpriseSerialno();
			feedbackCode = "";
			reason = "";
			dataRow = dataSheet.createRow(row);
			indexCell = dataRow.createCell(0);
			indexCell.setCellValue(index);
			bankNetworkUserCodeCell = dataRow.createCell(1);
			bankNetworkUserCodeCell.setCellValue(bankNetworkUserCode);
			bankCodeCell = dataRow.createCell(2);
			bankCodeCell.setCellValue(bankCode);
			bankAccountCell = dataRow.createCell(3);
			bankAccountCell.setCellValue(bankAccount);
			bankAccountNameCell = dataRow.createCell(4);
			bankAccountNameCell.setCellValue(bankAccountName);
			bankProvinceCell = dataRow.createCell(5);
			bankProvinceCell.setCellValue(bankProvince);
			bankCityCell = dataRow.createCell(6);
			bankCityCell.setCellValue(bankCity);
			bankNameCell = dataRow.createCell(7);
			bankNameCell.setCellValue(bankName);
			accountTypeCell = dataRow.createCell(8);
			accountTypeCell.setCellValue(accountType);
			splitAmountCell = dataRow.createCell(9);
			splitAmountCell.setCellValue(splitAmount);
			currencyTypeCell = dataRow.createCell(10);
			currencyTypeCell.setCellValue(currencyType);
			protocolNumberCell = dataRow.createCell(11);
			protocolNumberCell.setCellValue(protocolNumber);
			protocolNumberCodeCell = dataRow.createCell(12);
			protocolNumberCodeCell.setCellValue(protocolNumberCode);
			dictertTypeCell = dataRow.createCell(13);
			dictertTypeCell.setCellValue(dictertType);
			customerCertNumCell = dataRow.createCell(14);
			customerCertNumCell.setCellValue(customerCertNum);
			customerPhoneFirstCell = dataRow.createCell(15);
			customerPhoneFirstCell.setCellValue(customerPhoneFirst);
			customUserNameCell = dataRow.createCell(16);
			customUserNameCell.setCellValue(customUserName);
			remarkOneCell = dataRow.createCell(17);
			remarkOneCell.setCellValue(remarkOne);
			remarkTwoCell = dataRow.createCell(18);
			remarkTwoCell.setCellValue(remarkTwo);
			enterpriseSerialnoCell = dataRow.createCell(19);
			enterpriseSerialnoCell.setCellValue(enterpriseSerialno);
			feedbackCodeCell = dataRow.createCell(20);
			feedbackCodeCell.setCellValue(feedbackCode);
			reasonCell = dataRow.createCell(21);
			reasonCell.setCellValue(reason);
			row = row + 1;
		}
		return list;
	}
}
