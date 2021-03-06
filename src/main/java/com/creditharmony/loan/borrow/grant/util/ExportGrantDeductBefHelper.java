
package com.creditharmony.loan.borrow.grant.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductPlatBank;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 放款以往待划扣导出excel,导出的字段和列表的字段顺序一致
 * @Class Name ExportGrantDeductBefHelper
 * @author 朱静越
 * @Create In 2016年10月24日
 */
@SuppressWarnings("deprecation")
public class ExportGrantDeductBefHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportGrantDeductBefHelper.class);
	
	 public static void exportData(UrgeServicesMoneyEx urgeEx,String[] header,
				HttpServletResponse response) {
			final int MAXCOLUMN = 21;
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
					.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			try {
				String fileName = DateUtils.getDate("yyyyMMdd")+FileExtension.DEDUCT_BEF;
				SXSSFWorkbook wb = new SXSSFWorkbook();
				Sheet dataSheet = wb.createSheet("ExportList");
				wrapperHeader(wb,dataSheet,fileName,header);
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao.selectDeductsList",
								urgeEx, sqlSessionFactory);
				PreparedStatement ps = connection.prepareStatement(batisSql.toString());
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
		
		private static void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN)
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
				dataRow = dataSheet.createRow(row);
				Cell customerNameCell = dataRow.createCell(0);
				customerNameCell.setCellStyle(style);
				customerNameCell.setCellValue(resultSet.getString("contract_code"));
				Cell checkEmpIdCell = dataRow.createCell(1);
				checkEmpIdCell.setCellStyle(style);
				checkEmpIdCell.setCellValue(resultSet.getString("name")); // 门店名称
				Cell customerCertNumCell = dataRow.createCell(2);
				customerCertNumCell.setCellStyle(style);
				customerCertNumCell.setCellValue(resultSet.getString("customer_name"));
				Cell bankAccountNameCell = dataRow.createCell(3);
				bankAccountNameCell.setCellStyle(style);
				bankAccountNameCell.setCellValue(resultSet.getString("bank_account_name"));
				Cell loanTeamManagercodeCell = dataRow.createCell(4);
				loanTeamManagercodeCell.setCellStyle(style);
				loanTeamManagercodeCell.setCellValue(resultSet.getString("product_name"));
				Cell contractAmountCell = dataRow.createCell(5);
				contractAmountCell.setCellStyle(style); 
				contractAmountCell.setCellValue(format(resultSet.getString("contractAmount"),"##0.00"));
				Cell grantAmountCell = dataRow.createCell(6);
				grantAmountCell.setCellStyle(style);
				grantAmountCell.setCellValue(format(resultSet.getString("grantAmount"),"##0.00"));
				Cell loanCustomerServiceCell = dataRow.createCell(7);
				loanCustomerServiceCell.setCellStyle(style);
				loanCustomerServiceCell.setCellValue(format(resultSet.getString("urgeMoeny"), "##0.00"));
				Cell contractCodeCell = dataRow.createCell(8);
				contractCodeCell.setCellStyle(style);
				contractCodeCell.setCellValue(format(resultSet.getString("waitUrgeMoeny"), "##0.00"));
				
				Cell auditAmountCell = dataRow.createCell(9);
				auditAmountCell.setCellStyle(style);
				auditAmountCell.setCellValue(format(resultSet.getString("waitUrgeMoeny"),"##0.00"));
				Cell feePetitionCell = dataRow.createCell(10);
				feePetitionCell.setCellStyle(style);
				// 划扣平台
				String deductPlat = "";
				DeductPlat deduct = DeductPlat.parseByCode(resultSet.getString("dictDealType"));
				if (ObjectHelper.isNotEmpty(deduct)) {
					deductPlat = deduct.getName();
				}
				feePetitionCell.setCellValue(deductPlat);
				Cell productTypeCell = dataRow.createCell(11);
				productTypeCell.setCellStyle(style);
				productTypeCell.setCellValue(resultSet.getString("contract_months"));
				Cell contractMonthsCell = dataRow.createCell(12);
				contractMonthsCell.setCellStyle(style);
				// 开户行
				String deductBank = "";
				DeductPlatBank deductPlatBank = DeductPlatBank.parseByCode(resultSet.getString("bank_name"));
				if (ObjectHelper.isNotEmpty(deductPlatBank)) {
					deductBank = deductPlatBank.getName();
				}
				contractMonthsCell.setCellValue(deductBank);
				Cell middleNameCell = dataRow.createCell(13);
				middleNameCell.setCellStyle(style);
				String lendingTime = "";
				if (null != resultSet.getTimestamp("lending_time")) {
					Date date = resultSet.getTimestamp("lending_time");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					lendingTime = sdf.format(date);
				}
				middleNameCell.setCellValue(lendingTime);
				Cell midBankNameCell = dataRow.createCell(14);
				midBankNameCell.setCellStyle(style);
				String deductTime = "";
				if (null != resultSet.getTimestamp("decuctTime")) {
					Date date = resultSet.getTimestamp("decuctTime");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					deductTime = sdf.format(date);
				}
				midBankNameCell.setCellValue(deductTime);
				Cell bankCardNoCell = dataRow.createCell(15);
				bankCardNoCell.setCellStyle(style);
				// 划扣回盘结果
				UrgeCounterofferResult result = UrgeCounterofferResult.parseByCode(resultSet.getString("splitBackResult"));
				String urgeResult = "";
				if (ObjectHelper.isNotEmpty(result)) {
					urgeResult = result.getName();
				}
				bankCardNoCell.setCellValue(urgeResult);
				// 失败原因
				Cell deductFailReasonCell = dataRow.createCell(16);
				deductFailReasonCell.setCellStyle(style);
				deductFailReasonCell.setCellValue(resultSet.getString("deduct_fail_reason"));
				
				Cell telFlagCell = dataRow.createCell(17);
				telFlagCell.setCellStyle(style);
				String telFlag = resultSet.getString("customer_telesales_flag");
				if (YESNO.YES.getCode().equals(telFlag)) {
					telFlag = YESNO.YES.getName();
				}else {
					telFlag = YESNO.NO.getName();
				}
				telFlagCell.setCellValue(telFlag);
				Cell storesCodeCell = dataRow.createCell(18);
				storesCodeCell.setCellStyle(style);
				ChannelFlag channelFlag = ChannelFlag.parseByCode(resultSet.getString("loan_flag"));
				String loanFlag = "";
				if (ObjectHelper.isNotEmpty(channelFlag)) {
					loanFlag = channelFlag.getName();
				}
				storesCodeCell.setCellValue(loanFlag);
				
				Cell feeCreditCell = dataRow.createCell(19);
				feeCreditCell.setCellStyle(style);
				feeCreditCell.setCellValue(format(resultSet.getString("feeCredit"), "##0.00"));// 征信费
				
				Cell feePetitonCell = dataRow.createCell(20);
				feePetitonCell.setCellStyle(style);
				feePetitonCell.setCellValue(format(resultSet.getString("feePetition"), "##0.00"));// 信访费
				
				Cell feeSumCell = dataRow.createCell(21);
				feeSumCell.setCellStyle(style);
				feeSumCell.setCellValue(format(resultSet.getString("feeSum"), "##0.00"));// 费用总计
				
				row = row + 1;
			}
			setAutoColumn(MAXCOLUMN, dataSheet);
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
