
package com.creditharmony.loan.borrow.grant.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.common.consts.FileExtension;

import freemarker.template.SimpleDate;

/**
 * 打款表的导出处理
 * @Class Name ExportGrantDoneHelper
 * @author 朱静越
 * @Create In 2016年4月29日
 */
@SuppressWarnings("deprecation")
public class ExportGrantSureHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportGrantSureHelper.class);

	/**
	 * 打款表导出
	 * 2016年5月6日
	 * By 朱静越
	 * @param ids
	 * @param header
	 * @param fileName
	 * @param response
	 */
	 public static void exportData(Map<String, Object> ids,String[] header,String fileName,
				HttpServletResponse response) {
			final int MAXCOLUMN = 19;
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
					.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			try {
				SXSSFWorkbook wb = new SXSSFWorkbook();
				Sheet dataSheet = wb.createSheet("ExportList");
				wrapperHeader(wb,dataSheet,fileName,header);
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.borrow.grant.dao.SendMoneyDao.getMoneyList",
								ids, sqlSessionFactory);
				PreparedStatement ps = connection.prepareStatement(batisSql
						.toString());
				ResultSet resultSet = ps.executeQuery();
				assembleExcelCell(wb,resultSet, dataSheet,MAXCOLUMN);
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
				try {if(connection!=null)
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	 
	 /**
	  * 客户信息表导出
	  * 2016年5月6日
	  * By 朱静越
	  * @param queryMap
	  * @param header
	  * @param fileName
	  * @param response
	  * @param userManager
	  */
	 public static void customerExport(Map<String, Object> queryMap,String[] header,String fileName,
				HttpServletResponse response,UserManager userManager) {
			//列数
			final int MAXCOLUMN = 32;
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
					.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			try {
				SXSSFWorkbook wb = new SXSSFWorkbook();
				Sheet dataSheet = wb.createSheet("ExportList");
				wrapperHeader(wb,dataSheet,fileName,header);
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.borrow.grant.dao.GrantCustomerDao.getCustomerList",
								queryMap, sqlSessionFactory);
				PreparedStatement ps = connection.prepareStatement(batisSql
						.toString());
				ResultSet resultSet = ps.executeQuery();
				customerExcelCell(wb,resultSet, dataSheet,MAXCOLUMN,userManager);
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
	 
	 /**
	  * 汇总表导出
	  * 2016年5月6日
	  * By 朱静越
	  * @param ids
	  * @param response
	  * @param userManager
	  * @param header
	  * @param fileName
	  */
	 public static void sumExport(Map<String, Object> ids,
				HttpServletResponse response,String[] header,String fileName) {
			//列数
			final int MAXCOLUMN = 24;
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
					.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			try {
				SXSSFWorkbook wb = new SXSSFWorkbook();
				Sheet dataSheet = wb.createSheet("ExportList");
				wrapperHeader(wb,dataSheet,fileName,header);
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.borrow.grant.dao.GrantSumDao.getSumList",
								ids, sqlSessionFactory);
				PreparedStatement ps = connection.prepareStatement(batisSql
						.toString());
				ResultSet resultSet = ps.executeQuery();
				summarySheetExcelCell(wb,resultSet, dataSheet,MAXCOLUMN);
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
	 
	 /**
	  * 汇总表导出设置
	  * 2016年5月6日
	  * By 朱静越
	  * @param wb
	  * @param resultSet
	  * @param dataSheet
	  * @param MAXCOLUMN
	  * @param userManager
	  * @throws SQLException
	  */
	 private static void summarySheetExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN)
				throws SQLException {
		
			int row = 2;
			CellStyle styleText = wb.createCellStyle();
			    styleText.setBorderBottom((short) 1);   
			    styleText.setBorderTop((short) 1);
			    styleText.setBorderLeft((short) 1);
			    styleText.setBorderRight((short) 1);
			    styleText.setAlignment(CellStyle.ALIGN_CENTER);
		        Font font = wb.createFont();
		        font.setFontHeightInPoints((short)10);
		        styleText.setFont(font);
		        DataFormat formatText = wb.createDataFormat();  
		        styleText.setDataFormat(formatText.getFormat("@"));
		    CellStyle styleNumeric = wb.createCellStyle();
		        styleNumeric.setBorderBottom((short) 1);   
		        styleNumeric.setBorderTop((short) 1);
		        styleNumeric.setBorderLeft((short) 1);
		        styleNumeric.setBorderRight((short) 1);
		        styleNumeric.setAlignment(CellStyle.ALIGN_CENTER);
                font.setFontHeightInPoints((short)10);
                styleNumeric.setFont(font);
                DataFormat formatNumeric = wb.createDataFormat();  
                styleNumeric.setDataFormat(formatNumeric.getFormat("#,##0.00_);(#,##0.00)"));
			Row dataRow;
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			while (resultSet.next()) {
				dataRow = dataSheet.createRow(row);
				Cell lendCodeCell = dataRow.createCell(0);//序号
				lendCodeCell.setCellType(Cell.CELL_TYPE_STRING);
				lendCodeCell.setCellStyle(styleText);
				lendCodeCell.setCellValue(resultSet.getString("sequenceNumber"));
				Cell accountNoCell = dataRow.createCell(1);//"合同编号"
				accountNoCell.setCellType(Cell.CELL_TYPE_STRING);
				accountNoCell.setCellStyle(styleText);
				accountNoCell.setCellValue(resultSet.getString("contract_code"));
				Cell accountNameCell = dataRow.createCell(2);//  "借款人姓名(共借人)"
				accountNameCell.setCellType(Cell.CELL_TYPE_STRING);
				accountNameCell.setCellStyle(styleText);
				accountNameCell.setCellValue(resultSet.getString("customerName"));
				Cell accountBankCell = dataRow.createCell(3);// "批借金额", 
				//accountBankCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				accountBankCell.setCellStyle(styleNumeric);
				accountBankCell.setCellValue(Double.valueOf(resultSet.getString("auditAmount")));//批借
     			Cell accountBranchCell = dataRow.createCell(4);//合同金额
     			//accountBranchCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				accountBranchCell.setCellStyle(styleNumeric);
				accountBranchCell.setCellValue(Double.valueOf(resultSet.getString("contractAmount")));//合同金额
				Cell accountCardOrBookletCell = dataRow.createCell(5);//实放金额
				//accountCardOrBookletCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				accountCardOrBookletCell.setCellStyle(styleNumeric);
				accountCardOrBookletCell.setCellValue(Double.valueOf(resultSet.getString("feePaymentAmount")));//实放
				Cell cityCell = dataRow.createCell(6);//外访费
				//cityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cityCell.setCellStyle(styleNumeric);
				cityCell.setCellValue(Double.valueOf(resultSet.getString("feePetition")));//外访费
//				Cell productNameCell = dataRow.createCell(7);
//				//productNameCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//				productNameCell.setCellStyle(styleNumeric);
//				productNameCell.setCellValue(Double.valueOf(resultSet.getString("feeConsult")));// 咨询费
//				Cell applyLendDayCell = dataRow.createCell(8);
//				//applyLendDayCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//				applyLendDayCell.setCellStyle(styleNumeric);
//				applyLendDayCell.setCellValue(Double.valueOf(resultSet.getString("feeAuditAmount")));// 审核费
//				Cell applyLendMoneyCell = dataRow.createCell(9);
//				//applyLendMoneyCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//				applyLendMoneyCell.setCellStyle(styleNumeric);
//				applyLendMoneyCell.setCellValue(Double.valueOf(resultSet.getString("feeService")));//服务费
//				Cell applyPayCell = dataRow.createCell(10);
//				//applyPayCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//				applyPayCell.setCellStyle(styleNumeric);
//				applyPayCell.setCellValue(Double.valueOf(resultSet.getString("feeInfoService")));//居间服务费
//				Cell lendingTimeCell = dataRow.createCell(11);
//				//lendingTimeCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//				lendingTimeCell.setCellStyle(styleNumeric);
//				lendingTimeCell.setCellValue(Double.valueOf(resultSet.getString("feeCount")));//综合服务费
				Cell CONTRACT_END_DAY = dataRow.createCell(7);//催收服务费
				CONTRACT_END_DAY.setCellStyle(styleNumeric);
				CONTRACT_END_DAY.setCellValue(Double.valueOf(resultSet.getString("feeUrgedService")));//催收服务费
			
			    Cell monthServiceFeeNxd = dataRow.createCell(8);//分期服务费
                monthServiceFeeNxd.setCellStyle(styleNumeric);
                monthServiceFeeNxd.setCellValue(Double.valueOf(resultSet.getString("MONTH_FEE_SERVICE")));//月服务费
                
                Cell monthinterest = dataRow.createCell(9);//月利息	
                monthinterest.setCellStyle(styleNumeric);
                monthinterest.setCellValue(Double.valueOf(resultSet.getString("MONTH_FEE_NXD")));
				
				Cell monthpay = dataRow.createCell(10);//月还金额	
				monthpay.setCellStyle(styleNumeric);
				monthpay.setCellValue(Double.valueOf(resultSet.getString("CONTRACT_MONTH_REPAY_TOTAL")));
				
				Cell adf = dataRow.createCell(11);//产品种类
				adf.setCellType(Cell.CELL_TYPE_STRING);
				adf.setCellStyle(styleText);
				adf.setCellValue(resultSet.getString("product_name"));
				
				Cell contract_months = dataRow.createCell(12);//	期数	
				contract_months.setCellType(Cell.CELL_TYPE_STRING);
				contract_months.setCellStyle(styleText);
				contract_months.setCellValue(resultSet.getString("contract_months"));
				 //总费率（%）	月利率（%）	
				Cell fee_all_raio = dataRow.createCell(13);//总费率（%）
				fee_all_raio.setCellType(Cell.CELL_TYPE_STRING);
				fee_all_raio.setCellStyle(styleText);
				fee_all_raio.setCellValue(resultSet.getString("fee_all_raio"));//费率
				Cell fee_month_rate = dataRow.createCell(14);
				fee_month_rate.setCellType(Cell.CELL_TYPE_STRING);
				fee_month_rate.setCellStyle(styleText);
				fee_month_rate.setCellValue(resultSet.getString("fee_month_rate"));//月利率

				Cell contract_fact_day = dataRow.createCell(15);//合同日期
				contract_fact_day.setCellType(Cell.CELL_TYPE_STRING);
				contract_fact_day.setCellStyle(styleText);
				contract_fact_day.setCellValue(resultSet.getString("contract_fact_day"));
				Cell contract_replay_day = dataRow.createCell(16);//首期还款日
				contract_replay_day.setCellType(Cell.CELL_TYPE_STRING);
				contract_replay_day.setCellStyle(styleText);
				contract_replay_day.setCellValue(resultSet.getString("CONTRACTREPLAYDAY"));
				
				Cell feeExpedited = dataRow.createCell(17);//加急费
//				feeExpedited.setCellType(Cell.CELL_TYPE_NUMERIC);
				feeExpedited.setCellStyle(styleNumeric);
				feeExpedited.setCellValue(Double.valueOf(resultSet.getString("feeExpedited")));//
				Cell loan_flag = dataRow.createCell(18);//渠道	
				loan_flag.setCellType(Cell.CELL_TYPE_STRING);
				loan_flag.setCellStyle(styleText);
				loan_flag.setCellValue(DictCache.getInstance().getDictLabel("jk_channel_flag", resultSet.getString("loan_flag")));
				Cell contractVersion = dataRow.createCell(19);//合同版本号
				contractVersion.setCellType(Cell.CELL_TYPE_STRING);
				contractVersion.setCellStyle(styleText);
				contractVersion.setCellValue(resultSet.getString("contract_version"));
//				Cell loan_urgent_flag = dataRow.createCell(23);//是否加急
//				loan_urgent_flag.setCellType(Cell.CELL_TYPE_STRING);
//				loan_urgent_flag.setCellStyle(styleText);
//				String urgeFlag = resultSet.getString("loan_urgent_flag");
//				if (YESNO.YES.getCode().equals(urgeFlag)) {
//					urgeFlag = "是";
//				}else {
//					urgeFlag = "否";
//				}
//				loan_urgent_flag.setCellValue(urgeFlag);
				Cell platform = dataRow.createCell(20);//划扣平台
				platform.setCellType(Cell.CELL_TYPE_STRING);
				platform.setCellStyle(styleText);
				platform.setCellValue(resultSet.getString("BANK_SIGNING_PLATFORM_STRING"));
				Cell telesales = dataRow.createCell(21);//"是否电销
				telesales.setCellType(Cell.CELL_TYPE_STRING);
				telesales.setCellStyle(styleText);
				telesales.setCellValue(resultSet.getString("CUSTOMER_TELESALES_FLAG_STRING"));
				Cell paperlessFlag = dataRow.createCell(22);//"是否无纸化
				paperlessFlag.setCellType(Cell.CELL_TYPE_STRING);
				paperlessFlag.setCellStyle(styleText);
				String paperless = resultSet.getString("paperless_flag");
			    YESNO yesno = YESNO.parseByCode(paperless);
			    if(!ObjectHelper.isEmpty(yesno)){
			        paperlessFlag.setCellValue( yesno.getName());
			    }else{
			        paperlessFlag.setCellValue(YESNO.NO.getName()); 
			    }
//                Cell ensureFlag = dataRow.createCell(25);//是否有保证人
//                ensureFlag.setCellType(Cell.CELL_TYPE_STRING);
//                ensureFlag.setCellStyle(styleText);
//                ensureFlag.setCellValue( resultSet.getString("ensure_flag"));
                //customer_into_time
			    Cell into = dataRow.createCell(23);//推送日期
			    into.setCellType(Cell.CELL_TYPE_STRING);
			    into.setCellStyle(styleText);
			    into.setCellValue(dateFormat.format(new Date()));
                
           
				row = row + 1;
			}
			setAutoColumn(MAXCOLUMN, dataSheet);
		}
		
	 /**
	  * 打款表导出设置
	  * 2016年5月6日
	  * By 朱静越
	  * @param wb
	  * @param resultSet
	  * @param dataSheet
	  * @param MAXCOLUMN
	  * @throws SQLException
	  */
		private static void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN)
				throws SQLException {
			int row = 2;
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#,##0.000");  // 费率保留三位小数
			CellStyle styleText = wb.createCellStyle();
            styleText.setBorderBottom((short) 1);   
            styleText.setBorderTop((short) 1);
            styleText.setBorderLeft((short) 1);
            styleText.setBorderRight((short) 1);
            styleText.setAlignment(CellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short)10);
            styleText.setFont(font);
            DataFormat formatText = wb.createDataFormat();  
            styleText.setDataFormat(formatText.getFormat("@"));
        CellStyle styleNumeric = wb.createCellStyle();
            styleNumeric.setBorderBottom((short) 1);   
            styleNumeric.setBorderTop((short) 1);
            styleNumeric.setBorderLeft((short) 1);
            styleNumeric.setBorderRight((short) 1);
            styleNumeric.setAlignment(CellStyle.ALIGN_CENTER);
            font.setFontHeightInPoints((short)10);
            styleNumeric.setFont(font);
            DataFormat formatNumeric = wb.createDataFormat();  
            styleNumeric.setDataFormat(formatNumeric.getFormat("#,##0.00_);(#,##0.00)"));
			Row dataRow;
			BigDecimal grantAmountSum = new BigDecimal(0);
			while (resultSet.next()) {
				dataRow = dataSheet.createRow(row);
				Cell customerNameCell = dataRow.createCell(0);
				customerNameCell.setCellStyle(styleText);
				customerNameCell.setCellValue("");
				Cell customerCertNumCell = dataRow.createCell(1);
				customerCertNumCell.setCellType(Cell.CELL_TYPE_STRING);
				customerCertNumCell.setCellStyle(styleText);
				customerCertNumCell.setCellValue(resultSet.getString("indexs"));
				Cell accountNameCell = dataRow.createCell(2);
				accountNameCell.setCellType(Cell.CELL_TYPE_STRING);
				accountNameCell.setCellStyle(styleText);
				accountNameCell.setCellValue(resultSet.getString("storesName"));
				Cell backMoneyCell = dataRow.createCell(3);
				backMoneyCell.setCellType(Cell.CELL_TYPE_STRING);
				backMoneyCell.setCellStyle(styleText);
				backMoneyCell.setCellValue(resultSet.getString("contract_code"));
				Cell accountBankCell = dataRow.createCell(4);
				accountBankCell.setCellType(Cell.CELL_TYPE_STRING);
				accountBankCell.setCellStyle(styleText);
				accountBankCell.setCellValue(resultSet.getString("customer_name"));
				Cell accountBranchCell = dataRow.createCell(5);
				accountBranchCell.setCellType(Cell.CELL_TYPE_STRING);
				accountBranchCell.setCellStyle(styleText);
				accountBranchCell.setCellValue(resultSet.getString("customer_cert_num"));
				Cell accountCardOrBookletCell = dataRow.createCell(6);
				accountCardOrBookletCell.setCellType(Cell.CELL_TYPE_STRING);
				accountCardOrBookletCell.setCellStyle(styleText);
				accountCardOrBookletCell.setCellValue(resultSet.getString("contract_months"));
				Cell monthRateCell = dataRow.createCell(7);
				monthRateCell.setCellType(Cell.CELL_TYPE_STRING);
				monthRateCell.setCellStyle(styleText);
				monthRateCell.setCellValue(df.format(Double.valueOf(resultSet.getString("fee_month_rate"))));
				Cell provinceCell = dataRow.createCell(8);
				//provinceCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				provinceCell.setCellStyle(styleNumeric);
				provinceCell.setCellValue(Double.valueOf(resultSet.getString("contractAmount")));
				String grantAmount = resultSet.getString("grantAmount");
				Cell cityCell = dataRow.createCell(9);
				//cityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cityCell.setCellStyle(styleNumeric);
				cityCell.setCellValue(Double.valueOf(grantAmount));
				Cell productNameCell = dataRow.createCell(10);
				//productNameCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				productNameCell.setCellStyle(styleNumeric);
				productNameCell.setCellValue(Double.valueOf(resultSet.getString("feeUrgedService")));
				Cell applyLendDayCell = dataRow.createCell(11);
				applyLendDayCell.setCellType(Cell.CELL_TYPE_STRING);
				applyLendDayCell.setCellStyle(styleText);
				applyLendDayCell.setCellValue(resultSet.getString("bank_account"));
				Cell applyLendMoneyCell = dataRow.createCell(12);
				applyLendMoneyCell.setCellType(Cell.CELL_TYPE_STRING);
				applyLendMoneyCell.setCellStyle(styleText);
				String bankName=DictCache.getInstance().getDictLabel("jk_open_bank",resultSet.getString("bank_name"));
				applyLendMoneyCell.setCellValue(bankName);
				Cell applyPayCell = dataRow.createCell(13);
				applyPayCell.setCellType(Cell.CELL_TYPE_STRING);
				applyPayCell.setCellStyle(styleText);
				applyPayCell.setCellValue(resultSet.getString("bank_branch"));
				Cell productTypeCell = dataRow.createCell(14);
				productTypeCell.setCellType(Cell.CELL_TYPE_STRING);
				productTypeCell.setCellStyle(styleText);
				productTypeCell.setCellValue(resultSet.getString("loan_flag"));
				Cell contractMonthsCell = dataRow.createCell(15);//合同版本号
				contractMonthsCell.setCellType(Cell.CELL_TYPE_STRING);
				contractMonthsCell.setCellStyle(styleText);
				contractMonthsCell.setCellValue(DictCache.getInstance().getDictLabel("jk_contract_ver", resultSet.getString("contract_version")));
//				Cell middleNameCell = dataRow.createCell(16); //是否加急
//				middleNameCell.setCellType(Cell.CELL_TYPE_STRING);
//				middleNameCell.setCellStyle(styleText);
//				String urgeFlag = resultSet.getString("loan_urgent_flag");
//				if (YESNO.YES.getCode().equals(urgeFlag)) {
//					urgeFlag = "加急";
//				}else {
//					urgeFlag = "";
//				}
//				middleNameCell.setCellValue(urgeFlag);
//				Cell paperlessFlag = dataRow.createCell(17); //是否无纸化
//				paperlessFlag.setCellType(Cell.CELL_TYPE_STRING);
//				paperlessFlag.setCellStyle(styleText);
//				String paperless = resultSet.getString("paperless_flag");
//                YESNO yesno = YESNO.parseByCode(paperless);
//                if(!ObjectHelper.isEmpty(yesno)){
//                    paperlessFlag.setCellValue(yesno.getName());
//                }else{
//                    paperlessFlag.setCellValue(YESNO.NO.getName()); 
//                }
                Cell telesales = dataRow.createCell(16);//是否电销
                telesales.setCellType(Cell.CELL_TYPE_STRING);
                telesales.setCellStyle(styleText);
                telesales.setCellValue(resultSet.getString("CUSTOMER_TELESALES_FLAG_STRING"));
                Cell ensureFlag = dataRow.createCell(17);//是否有保证人
                ensureFlag.setCellType(Cell.CELL_TYPE_STRING);
                ensureFlag.setCellStyle(styleText);
                ensureFlag.setCellValue(resultSet.getString("ensure_flag"));
                Cell revisitStatusCell = dataRow.createCell(18);//回访状态
                String revisitStatus = resultSet.getString("revisit_status");
                String revisitStatusName = "";
                if(revisitStatus!= null && !"".equals(revisitStatus)){
                	if(GrantCommon.REVISIT_STATUS_SUCCESS_CODE.equals(revisitStatus)){
                		revisitStatusName = GrantCommon.REVISIT_STATUS_SUCCESS;
                	}else if(GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(revisitStatus)){
                		revisitStatusName = GrantCommon.REVISIT_STATUS_WAIT;
                	}else if(GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(revisitStatus)){
                		revisitStatusName = GrantCommon.REVISIT_STATUS_FAIL;
                	}
                }
                revisitStatusCell.setCellType(Cell.CELL_TYPE_STRING);
                revisitStatusCell.setCellStyle(styleText);
                revisitStatusCell.setCellValue(revisitStatusName);
				row = row + 1;
				
				grantAmountSum = grantAmountSum.add(BigDecimal.valueOf(Double.parseDouble(grantAmount)));
			}
			//添加页尾
			//获取总行数
			int rowNum = row;
			Row footerRow = dataSheet.createRow(rowNum);
			for (int i = 0; i <= MAXCOLUMN; i++) {
				Cell footerCell = footerRow.createCell(i);
				footerCell.setCellStyle(styleNumeric);
				if (i == 9) {
					footerCell.setCellValue("实放金额合计:" + format(grantAmountSum+"", "#,##0.00"));
				}
			}
			setAutoColumn(MAXCOLUMN, dataSheet);
		}
		
		/**
		 * 客户信息表导出设置
		 * 2016年5月6日
		 * By 朱静越
		 * @param wb
		 * @param resultSet
		 * @param dataSheet
		 * @param MAXCOLUMN
		 * @throws SQLException
		 */
		private static void customerExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN,UserManager userManager)
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
		    CellStyle styleNumeric = wb.createCellStyle();
	            styleNumeric.setBorderBottom((short) 1);   
	            styleNumeric.setBorderTop((short) 1);
	            styleNumeric.setBorderLeft((short) 1);
	            styleNumeric.setBorderRight((short) 1);
	            styleNumeric.setAlignment(CellStyle.ALIGN_CENTER);
	            font.setFontHeightInPoints((short)10);
	            styleNumeric.setFont(font);
	            DataFormat formatNumeric = wb.createDataFormat();  
	            styleNumeric.setDataFormat(formatNumeric.getFormat("#,##0.00_);(#,##0.00)"));
			Row dataRow;
			while (resultSet.next()) {
				dataRow = dataSheet.createRow(row);
				Cell accountNoCell = dataRow.createCell(0);
				accountNoCell.setCellStyle(style);
				accountNoCell.setCellValue(resultSet.getString("customer_name"));
				Cell accountNameCell = dataRow.createCell(1);
				accountNameCell.setCellStyle(style);
				accountNameCell.setCellValue(resultSet.getString("customer_cert_num"));
				Cell backMoneyCell = dataRow.createCell(2);
				backMoneyCell.setCellStyle(style);
				backMoneyCell.setCellValue(DictCache.getInstance().getDictLabel("jk_sex", resultSet.getString("customer_sex")));
				Cell accountBankCell = dataRow.createCell(3);
				accountBankCell.setCellStyle(style);
				accountBankCell.setCellValue(resultSet.getString("product_name"));
				Cell lendCodeCell = dataRow.createCell(4);
                lendCodeCell.setCellStyle(style);
                lendCodeCell.setCellValue(resultSet.getString("contract_code"));
				Cell accountBranchCell = dataRow.createCell(5);
				accountBranchCell.setCellStyle(style);
				accountBranchCell.setCellValue(resultSet.getString("stores_name"));
				Cell accountCardOrBookletCell = dataRow.createCell(6);
				accountCardOrBookletCell.setCellStyle(style);
				accountCardOrBookletCell.setCellValue(resultSet.getString("bank_account"));
				Cell provinceCell = dataRow.createCell(7);
				provinceCell.setCellStyle(style);
				provinceCell.setCellValue(DictCache.getInstance().getDictLabel("jk_open_bank", resultSet.getString("bank_name")));
				Cell cityCell = dataRow.createCell(8);
				cityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cityCell.setCellStyle(styleNumeric);
				cityCell.setCellValue(Double.valueOf(resultSet.getString("feePaymentAmount")));// 实放金额
	    		Cell productNameCell = dataRow.createCell(9);
	    		productNameCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				productNameCell.setCellStyle(styleNumeric);
				productNameCell.setCellValue(Double.valueOf(resultSet.getString("contractAmount")));// 合同金额
				Cell applyLendDayCell = dataRow.createCell(10);
				applyLendDayCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				applyLendDayCell.setCellStyle(styleNumeric);
				applyLendDayCell.setCellValue(Double.valueOf(resultSet.getString("contractMonthRepayAmount")));//月还款额
				Cell applyLendMoneyCell = dataRow.createCell(11);
				applyLendMoneyCell.setCellStyle(style);
				applyLendMoneyCell.setCellValue(resultSet.getString("contract_months"));
				Cell applyPayCell = dataRow.createCell(12);
				applyPayCell.setCellStyle(style);
				String strApplyPay = resultSet.getString("contractAuditDate");
				if (StringUtils.isNotEmpty(strApplyPay)) {
					strApplyPay = strApplyPay.substring(0, 10);
				}
				applyPayCell.setCellValue(strApplyPay);//审核日期
				Cell lendingTimeCell = dataRow.createCell(13);
				lendingTimeCell.setCellStyle(style);
				lendingTimeCell.setCellValue(resultSet.getString("contract_replay_day"));
				Cell CONTRACT_END_DAY = dataRow.createCell(14);
				CONTRACT_END_DAY.setCellStyle(style);
				CONTRACT_END_DAY.setCellValue(resultSet.getString("contract_end_day"));
				Cell billDay = dataRow.createCell(15);
				billDay.setCellStyle(style);
				billDay.setCellValue(resultSet.getString("billDay"));
				Cell model = dataRow.createCell(16);
				model.setCellStyle(style);
				model.setCellValue(DictCache.getInstance().getDictLabel("jk_loan_model",resultSet.getString("model")));
				Cell jx = dataRow.createCell(17);
				jx.setCellStyle(style);
				jx.setCellValue(DictCache.getInstance().getDictLabel("jk_channel_flag",resultSet.getString("loan_flag")));//标识
				Cell contractAuditDate = dataRow.createCell(18);
				contractAuditDate.setCellStyle(style);
				contractAuditDate.setCellValue(resultSet.getString("contract_fact_day"));//合同签署日
				Cell CUSTOMER_TELESALES_FLAG = dataRow.createCell(19);
				CUSTOMER_TELESALES_FLAG.setCellStyle(style);
				CUSTOMER_TELESALES_FLAG.setCellValue(DictCache.getInstance().getDictLabel("jk_telemarketing",resultSet.getString("customer_telesales_flag")));//是否电销
				Cell urgeFlag = dataRow.createCell(20);
				urgeFlag.setCellStyle(style);
				if(YESNO.YES.getCode().equals(resultSet.getString("loan_urgent_flag"))){
				    urgeFlag.setCellValue(YESNO.YES.getName());//是否加急
				}else{
				    urgeFlag.setCellValue(YESNO.NO.getName());//是否加急
				}
				Cell bankSigningPlatform = dataRow.createCell(21);
				bankSigningPlatform.setCellStyle(style);
				bankSigningPlatform.setCellValue(DictCache.getInstance().getDictLabel("jk_deduct_plat",resultSet.getString("bank_signing_platform")));//划扣平台
				Cell CONTRACT_VERSION = dataRow.createCell(22);
				CONTRACT_VERSION.setCellStyle(style);
				CONTRACT_VERSION.setCellValue(DictCache.getInstance().getDictLabel("jk_contract_ver", resultSet.getString("contract_version")));//合同版本号
				Cell FEEURGEDSERVICE = dataRow.createCell(23);
				FEEURGEDSERVICE.setCellType(Cell.CELL_TYPE_NUMERIC);
                FEEURGEDSERVICE.setCellStyle(styleNumeric);
                FEEURGEDSERVICE.setCellValue(Double.valueOf(resultSet.getString("feeUrgedService")));//催收服务费
                
				Cell FEEPETITION = dataRow.createCell(24);
				FEEPETITION.setCellType(Cell.CELL_TYPE_NUMERIC);
				FEEPETITION.setCellStyle(styleNumeric);
				FEEPETITION.setCellValue(Double.valueOf(resultSet.getString("feePetition")));//外访费
				Cell AUDIT_COUNT = dataRow.createCell(25);
				AUDIT_COUNT.setCellStyle(style);
				AUDIT_COUNT.setCellValue(resultSet.getString("audit_count"));//审核次数
				Cell CONTRACT_BACK_RESULT = dataRow.createCell(26);
				CONTRACT_BACK_RESULT.setCellStyle(style);
				CONTRACT_BACK_RESULT.setCellValue(resultSet.getString("contract_back_result"));//最后一次退回原因
				Cell check_id = dataRow.createCell(27);
				check_id.setCellStyle(style);
				check_id.setCellValue(getName(resultSet.getString("check_id"),userManager));//审核人
				Cell riskLevel = dataRow.createCell(28);
				riskLevel.setCellStyle(style);
				riskLevel.setCellValue(resultSet.getString("riskLevel"));//风险等级
				Cell revisitStatusCell = dataRow.createCell(29);
				String revisitStatus = resultSet.getString("revisit_status");
                String revisitStatusName = "";
                if(revisitStatus!= null && !"".equals(revisitStatus)){
                	if(GrantCommon.REVISIT_STATUS_SUCCESS_CODE.equals(revisitStatus)){
                		revisitStatusName = GrantCommon.REVISIT_STATUS_SUCCESS;
                	}else if(GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(revisitStatus)){
                		revisitStatusName = GrantCommon.REVISIT_STATUS_WAIT;
                	}else if(GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(revisitStatus)){
                		revisitStatusName = GrantCommon.REVISIT_STATUS_FAIL;
                	}
                }
                revisitStatusCell.setCellStyle(style);
                revisitStatusCell.setCellValue(revisitStatusName);//回访状态
				row = row + 1;
			}
			setAutoColumn(MAXCOLUMN, dataSheet);
		}
		
		
		/**
		 * 获取制定的用户信息
		 * @param uerId 要获取的的用户的id
		 * @param userManager 缓存用户实体
		 * @return
		 */
		private static String getName(String uerId,UserManager userManager) {
			String userName = "";
			if (StringUtils.isNotEmpty(uerId)) {
				User comUser = userManager.get(uerId);
				userName  = !ObjectHelper.isEmpty(comUser) ? comUser.getName() : "";
			} 
			return userName;
		}

		// 导出标题设置
		private static void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,String[] header) {
			Row titleRow = dataSheet.createRow(0);
			titleRow.setHeight((short) (15.625*40)); 
			Cell titleCell = titleRow.createCell(0);
			dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,header.length-1));
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
	        
			Row headerRow = dataSheet.createRow(1); //为标题创建一行
			for (int i = 0; i < header.length; i++) {
				Cell headerCell = headerRow.createCell(i);
				headerCell.setCellStyle(style);
				headerCell.setCellValue(header[i]);
			}
			
		}
		/**
		 * 设置Excel表格的自适应功能(由于poi中的自动适应除英文、数字外的其他字符不支持自动适应功能)
		 * @param maxColumn 列数
		 * @param sheet Sheet表格
		 */
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
			             if(currentCell !=null){
			            	 String cellStr = currentCell.toString();
			            	 if (StringUtils.isNotEmpty(cellStr)) {
			            		 int length = cellStr.getBytes().length; 
				                  if (columnWidth < length) {  
				                    columnWidth = length;  
				                   }  
			            	 }
			             }
			        }  
			        sheet.setColumnWidth(columnNum, columnWidth * 256);
			    }  
			}  
		}
		/**
		 * 格式化数据信息到指定的格式(只支持金额数据信息的格式化) ,数据信息为空的话直接返回空字符串
		 * @param data 待格式化的数据信息
		 * @param pattern 格式
		 * @return 格式化后的数据信息
		 */
		private static String format(String data,String pattern) {
			String formatData = "";
			NumberFormat nf = new DecimalFormat(pattern);
			if (StringUtils.isNotEmpty(data)) 
				formatData = nf.format(Double.parseDouble(data));
			return formatData;
		}
}
