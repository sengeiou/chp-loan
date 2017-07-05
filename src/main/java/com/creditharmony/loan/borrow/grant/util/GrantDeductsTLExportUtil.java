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
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceTlEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.common.consts.FileExtension;


public class GrantDeductsTLExportUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(GrantDeductsTLExportUtil.class);

	public static List<UrgeServiceTlEx> exportData(UrgeServicesMoneyEx urgeMoneyEx,
			HttpServletResponse response,String fileName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		List<UrgeServiceTlEx> list = new ArrayList();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(dataSheet);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao.getDeductsTl",
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
	private static void wrapperHeader(Sheet dataSheet,List<UrgeServiceTlEx> list) {
		double allMoney = 0;
		for (UrgeServiceTlEx splitdata : list) {
			allMoney += Double.parseDouble(splitdata.getSplitAmount());
		}
		String [] title = {"S","200604000000445",DateUtils.getDate("yyyyMMdd"),list.size()+"",((Math.round(allMoney*10000)/100))+"","10401"};
		Row headerRow = dataSheet.createRow(0);
		Cell title1Header = headerRow.createCell(0);
		title1Header.setCellValue(title[0]);
		Cell title2Header = headerRow.createCell(1);
		title2Header.setCellValue(title[1]);
		Cell title3Header = headerRow.createCell(2);
		title3Header.setCellValue(title[2]);
		Cell title4Header = headerRow.createCell(3);
		title4Header.setCellValue(title[3]);
		Cell title5Header = headerRow.createCell(4);
		title5Header.setCellValue(title[4]);
		Cell title6Header = headerRow.createCell(5);
		title6Header.setCellValue(title[5]);

	}
	
	private static void wrapperHeader(Sheet dataSheet) {
		Row headerRow = dataSheet.createRow(1);
		Cell indexHeader = headerRow.createCell(0);
		indexHeader.setCellValue("序号");
		Cell bankNetworkUserCodeHeader = headerRow.createCell(1);
		bankNetworkUserCodeHeader.setCellValue("用户编号");
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
		customerPhoneFirstHeader.setCellValue("手机号/小灵通");
		Cell customUserNameHeader = headerRow.createCell(16);
		customUserNameHeader.setCellValue("自定义用户名");
		Cell enterpriseSerialnoHeader = headerRow.createCell(17);
		enterpriseSerialnoHeader.setCellValue("备注");
		Cell feedbackCodeHeader = headerRow.createCell(18);
		feedbackCodeHeader.setCellValue("反馈码");
		Cell reasonHeader = headerRow.createCell(19);
		reasonHeader.setCellValue("原因");
	}

	private static List<UrgeServiceTlEx> assembleExcelCell(ResultSet resultSet, Sheet dataSheet)
			throws SQLException {
		List<UrgeServiceTlEx> list = new ArrayList<UrgeServiceTlEx>();
		int row = 2;
		String id;
		String index;
		String userNo;
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
		String enterpriseSerialno;
		String feedbackCode;
		String reason;
		
		Row dataRow;
		Cell indexCell;
		Cell userNoCell;
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
		Cell enterpriseSerialnoCell;
		Cell feedbackCodeCell;
		Cell reasonCell;
		int hanghao = 1;
		while (resultSet.next()) {
			UrgeServiceTlEx tl = new UrgeServiceTlEx();
			id = resultSet.getString("id");
			index = String.format("%05d",hanghao);
			userNo = "";
			bankCode = resultSet.getString("bankCode");
			bankAccount = resultSet.getString("bankAccount");
			bankAccountName = resultSet.getString("bankAccountName");
			bankProvince = resultSet.getString("bankProvince");
			bankCity = resultSet.getString("bankCity");
			bankName = resultSet.getString("bankName");
			accountType = "CNY";
			splitAmount = resultSet.getString("splitAmount");
			currencyType = "";
			protocolNumber = "";
			protocolNumberCode = "";
			dictertType = resultSet.getString("dictertType");
			customerCertNum = resultSet.getString("customerCertNum");
			// 解密手机号
			customerPhoneFirst = GrantUtil.getNum(resultSet.getString("customerPhoneFirst"));
			customUserName = resultSet.getString("customUserName");
			enterpriseSerialno = resultSet.getString("enterpriseSerialno");
			feedbackCode = "";
			reason = "";
			tl.setId(id);
			tl.setIndex(index);
			tl.setUserNo(userNo);
			tl.setBankCode(bankCode);
			tl.setBankAccount(bankAccount);
			tl.setBankAccountName(bankAccountName);
			tl.setBankProvince(bankProvince);
			tl.setBankCity(bankCity);
			tl.setBankName(bankName);
			tl.setAccountType(accountType);
			String amonth = new BigDecimal(splitAmount).multiply(new BigDecimal(100)).toString();
			String[] months = amonth.split("\\.");
			tl.setSplitAmount(months[0]);
			tl.setCurrencyType(currencyType);
			tl.setProtocolNumber(protocolNumberCode);
			tl.setProtocolNumberCode(protocolNumberCode);
			tl.setDictertType(dictertType);
			tl.setCustomerCertNum(customerCertNum);
			tl.setCustomerPhoneFirst(customerPhoneFirst);
			tl.setCustomUserName(customUserName);
			tl.setEnterpriseSerialno(enterpriseSerialno);
			tl.setFeedbackCode(feedbackCode);
			tl.setReason(reason);
			list.add(tl);
			hanghao++;
		}
		double allMoney = 0;
		for (UrgeServiceTlEx splitdata : list) {
			allMoney += Double.parseDouble(splitdata.getSplitAmount());
		}
		String [] title = {"S","200604000000445",DateUtils.getDate("yyyyMMdd"),list.size()+"",((Math.round(allMoney)))+"","10401"};
		Row headerRow = dataSheet.createRow(0);
		Cell title1Header = headerRow.createCell(0);
		title1Header.setCellValue(title[0]);
		Cell title2Header = headerRow.createCell(1);
		title2Header.setCellValue(title[1]);
		Cell title3Header = headerRow.createCell(2);
		title3Header.setCellValue(title[2]);
		Cell title4Header = headerRow.createCell(3);
		title4Header.setCellValue(title[3]);
		Cell title5Header = headerRow.createCell(4);
		title5Header.setCellValue(title[4]);
		Cell title6Header = headerRow.createCell(5);
		title6Header.setCellValue(title[5]);
		wrapperHeader(dataSheet,list);
		for(UrgeServiceTlEx tl: list){
			dataRow = dataSheet.createRow(row);
			indexCell = dataRow.createCell(0);
			indexCell.setCellValue(tl.getIndex());
			userNoCell = dataRow.createCell(1);
			userNoCell.setCellValue(tl.getUserNo());
			bankCodeCell = dataRow.createCell(2);
			bankCodeCell.setCellValue(tl.getBankCode());
			bankAccountCell = dataRow.createCell(3);
			bankAccountCell.setCellValue(tl.getBankAccount());
			bankAccountNameCell = dataRow.createCell(4);
			bankAccountNameCell.setCellValue(tl.getBankAccountName());
			bankProvinceCell = dataRow.createCell(5);
			bankProvinceCell.setCellValue(tl.getBankProvince());
			bankCityCell = dataRow.createCell(6);
			bankCityCell.setCellValue(tl.getBankCity());
			bankNameCell = dataRow.createCell(7);
			bankNameCell.setCellValue(tl.getBankName());
			accountTypeCell = dataRow.createCell(8);
			accountTypeCell.setCellValue("");
			splitAmountCell = dataRow.createCell(9);
			splitAmountCell.setCellValue(tl.getSplitAmount());
			currencyTypeCell = dataRow.createCell(10);
			currencyTypeCell.setCellValue(tl.getAccountType());
			protocolNumberCell = dataRow.createCell(11);
			protocolNumberCell.setCellValue(tl.getProtocolNumber());
			protocolNumberCodeCell = dataRow.createCell(12);
			protocolNumberCodeCell.setCellValue(tl.getProtocolNumberCode());
			dictertTypeCell = dataRow.createCell(13);
			dictertTypeCell.setCellValue(tl.getDictertType());
			customerCertNumCell = dataRow.createCell(14);
			customerCertNumCell.setCellValue(tl.getCustomerCertNum());
			customerPhoneFirstCell = dataRow.createCell(15);
			customerPhoneFirstCell.setCellValue(tl.getCustomerPhoneFirst());
			customUserNameCell = dataRow.createCell(16);
			customUserNameCell.setCellValue(tl.getCustomUserName());
			enterpriseSerialnoCell = dataRow.createCell(17);
			enterpriseSerialnoCell.setCellValue(tl.getEnterpriseSerialno());
			feedbackCodeCell = dataRow.createCell(18);
			feedbackCodeCell.setCellValue(tl.getFeedbackCode());
			reasonCell = dataRow.createCell(19);
			reasonCell.setCellValue("");
			row = row + 1;
		}
		return list;
	}

}
