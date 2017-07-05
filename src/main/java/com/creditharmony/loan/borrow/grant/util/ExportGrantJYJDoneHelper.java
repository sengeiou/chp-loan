
package com.creditharmony.loan.borrow.grant.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.channel.jyj.entity.LoanGrantDone;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 放款已办列表的导出处理
 * @Class Name ExportGrantDoneHelper
 * @author 朱静越
 * @Create In 2016年4月29日
 */
@SuppressWarnings("deprecation")
public class ExportGrantJYJDoneHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportGrantJYJDoneHelper.class);

	 public static void exportData(LoanGrantDone loanGrantEx,String[] header,
				HttpServletResponse response) {
			final int MAXCOLUMN = 26;
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
					.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			try {
				String fileName = FileExtension.GRANT_DONE + System.currentTimeMillis() ;
				SXSSFWorkbook wb = new SXSSFWorkbook();
				Sheet dataSheet = wb.createSheet("ExportList");
				wrapperHeader(wb,dataSheet,fileName,header);
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.channel.jyj.dao.LoanGrantJYJDao.findGrantDoneJYJExcel",
								loanGrantEx, sqlSessionFactory);
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
				customerNameCell.setCellValue(resultSet.getString("customer_name"));
				Cell customerCertNumCell = dataRow.createCell(1);
				customerCertNumCell.setCellStyle(style);
				customerCertNumCell.setCellValue(resultSet.getString("customerCertNum"));
				Cell loanTeamManagerNameCell = dataRow.createCell(2);
				loanTeamManagerNameCell.setCellStyle(style);
				loanTeamManagerNameCell.setCellValue(resultSet.getString("loanTeamManagerName"));
				Cell loanManagercodeCell = dataRow.createCell(3);
				loanManagercodeCell.setCellStyle(style);
				loanManagercodeCell.setCellValue(resultSet.getString("loan_team_managercode"));
				Cell loanManagerNameCell = dataRow.createCell(4);
				loanManagerNameCell.setCellStyle(style);
				loanManagerNameCell.setCellValue(resultSet.getString("loanManagerName"));
				Cell loanCustomerServiceCell = dataRow.createCell(5);
				loanCustomerServiceCell.setCellStyle(style);
				loanCustomerServiceCell.setCellValue(resultSet.getString("loan_managercode"));
				Cell classTypeCell = dataRow.createCell(6);
				classTypeCell.setCellStyle(style);
				classTypeCell.setCellValue("信借");
				Cell contractCodeCell = dataRow.createCell(7);
				contractCodeCell.setCellStyle(style);
				contractCodeCell.setCellValue(resultSet.getString("contract_code"));
				Cell contractAmountCell = dataRow.createCell(8);
				contractAmountCell.setCellStyle(style); 
				contractAmountCell.setCellValue(format(resultSet.getString("contract_amount"),"##0.00"));
				
				//"首次放款金额","费用总金额","前期综合服务费","加急费"
				Cell firstGrantAmountCell = dataRow.createCell(9);
				firstGrantAmountCell.setCellStyle(style);
				firstGrantAmountCell.setCellValue(resultSet.getString("FIRST_GRANT_AMOUNT"));
				Cell allFeeCell = dataRow.createCell(10);
				allFeeCell.setCellStyle(style);
				allFeeCell.setCellValue(resultSet.getString("ALL_FEE"));
				Cell feeCountCell = dataRow.createCell(11);
				feeCountCell.setCellStyle(style);
				feeCountCell.setCellValue(resultSet.getString("FEE_COUNT"));
				Cell feeExpeditedCell = dataRow.createCell(12);
				feeExpeditedCell.setCellStyle(style);
				feeExpeditedCell.setCellValue(resultSet.getString("FEE_EXPEDITED"));
	
				
				
//				Cell grantAmountCell = dataRow.createCell(14);
//				grantAmountCell.setCellStyle(style);
//				grantAmountCell.setCellValue(format(resultSet.getString("grant_amount"),"##0.00"));
				Cell auditAmountCell = dataRow.createCell(13);
				auditAmountCell.setCellStyle(style);
				auditAmountCell.setCellValue(format(resultSet.getString("audit_amount"),"##0.00"));
				Cell feePetitionCell = dataRow.createCell(14);
				feePetitionCell.setCellStyle(style);
				feePetitionCell.setCellValue(format(resultSet.getString("fee_petition"),"##0.00"));
				
				
				
				Cell lastGrantAmountCell = dataRow.createCell(15);
				lastGrantAmountCell.setCellStyle(style);
				lastGrantAmountCell.setCellValue(format(resultSet.getString("urge_moeny"),"##0.00"));
				Cell urgeCell = dataRow.createCell(16);
				urgeCell.setCellStyle(style);
				urgeCell.setCellValue(resultSet.getString("LAST_GRANT_AMOUNT"));
				
				
				Cell productTypeCell = dataRow.createCell(17);
				productTypeCell.setCellStyle(style);
				productTypeCell.setCellValue(resultSet.getString("productType"));
				Cell contractMonthsCell = dataRow.createCell(18);
				contractMonthsCell.setCellStyle(style);
				contractMonthsCell.setCellValue(resultSet.getString("contract_months"));
				Cell middleNameCell = dataRow.createCell(19);
				middleNameCell.setCellStyle(style);
				middleNameCell.setCellValue(resultSet.getString("middle_name"));
				Cell midBankNameCell = dataRow.createCell(20);
				midBankNameCell.setCellStyle(style);
				midBankNameCell.setCellValue(resultSet.getString("bankName"));
				Cell bankCardNoCell = dataRow.createCell(21);
				bankCardNoCell.setCellStyle(style);
				bankCardNoCell.setCellValue(resultSet.getString("bankAccountNumber"));
				Cell storesCodeCell = dataRow.createCell(22);
				storesCodeCell.setCellStyle(style);
				storesCodeCell.setCellValue(resultSet.getString("storesCode"));
				Cell lendingTimeCell = dataRow.createCell(23);
				lendingTimeCell.setCellStyle(style);
				lendingTimeCell.setCellValue(resultSet.getString("lending_time"));
				Cell checkEmpIdCell = dataRow.createCell(24);
				checkEmpIdCell.setCellStyle(style);
				checkEmpIdCell.setCellValue(resultSet.getString("check_emp_id"));
				Cell loanFlagCell = dataRow.createCell(25);
				loanFlagCell.setCellStyle(style);  
				loanFlagCell.setCellValue(resultSet.getString("loan_flag"));
				Cell customerTelesalesFlagCell = dataRow.createCell(26);
				customerTelesalesFlagCell.setCellStyle(style);
				customerTelesalesFlagCell.setCellValue(resultSet.getString("customer_telesales_flag"));
				Cell urgentFlagCell = dataRow.createCell(27);
				urgentFlagCell.setCellStyle(style);
				urgentFlagCell.setCellValue(resultSet.getString("loan_urgent_flag"));
				Cell grantPchCell = dataRow.createCell(28);
				grantPchCell.setCellStyle(style);
				grantPchCell.setCellValue(resultSet.getString("grant_batch"));  // 放款批次
				Cell grantBatchCell = dataRow.createCell(29);
				grantBatchCell.setCellStyle(style);
				grantBatchCell.setCellValue(resultSet.getString("grant_pch")); // 提交 批次
				Cell subDateCell = dataRow.createCell(30);
				subDateCell.setCellStyle(style);
				subDateCell.setCellValue(resultSet.getString("submissiondate")); // 提交 日期
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
