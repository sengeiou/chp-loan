package com.creditharmony.loan.borrow.grant.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.constants.ResultConstants;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 导出催收统计帮助类
 * @Probject Name: chp-loan
 * @Create By 尚军伟
 * @Create In 2016年4月20日 下午5:55:52
 */
@SuppressWarnings("deprecation")
public class ExportUrgeStatisticsHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportUrgeStatisticsHelper.class);
	 private static DecimalFormat df = new DecimalFormat("####.00");

	 
	 public static void exportData(Map<String, Object> queryMap,
				HttpServletResponse response) {
			final int MAXCOLUMN = 12;
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
					.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			try {
				String fileName = FileExtension.URGE_STATISTICS_VIEW + System.currentTimeMillis() ;
				SXSSFWorkbook wb = new SXSSFWorkbook();
				Sheet dataSheet = wb.createSheet("ExportList");
				wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.borrow.grant.dao.UrgeStatisticsViewDao.findByContractCode",
								queryMap, sqlSessionFactory);
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
			int k=1;
			String colResult=null;
			Map<String,Dict> dictMap=DictCache.getInstance().getMap();
			String dictPayStatusLabel=null;
			String backMoneyDesc=null;
			String payStatusLabel=null;
			while (resultSet.next()) {
				dataRow = dataSheet.createRow(row);
				Cell indexCell = dataRow.createCell(0);
				indexCell.setCellStyle(style);
				indexCell.setCellValue(k);
				Cell contractCodeCell = dataRow.createCell(1);
				contractCodeCell.setCellStyle(style);
				contractCodeCell.setCellValue(resultSet.getString("contractCode"));
				Cell customerNameCell = dataRow.createCell(2);
				customerNameCell.setCellStyle(style);
				customerNameCell.setCellValue(resultSet.getString("customerName"));
				Cell contractAmountCell = dataRow.createCell(3);
				contractAmountCell.setCellStyle(style);
				contractAmountCell.setCellValue(df.format(new BigDecimal(resultSet.getString("contractAmount"))));
				Cell receivableFeeUrgedServiceCell = dataRow.createCell(4);
				receivableFeeUrgedServiceCell.setCellStyle(style);
				receivableFeeUrgedServiceCell.setCellValue(df.format(new BigDecimal(resultSet.getString("receivableFeeUrgedService"))));
				Cell receivedfeeUrgedServiceCell = dataRow.createCell(5);
				receivedfeeUrgedServiceCell.setCellStyle(style);
				receivedfeeUrgedServiceCell.setCellValue(df.format(new BigDecimal(resultSet.getString("receivedfeeUrgedService"))));
				
				BigDecimal receivableFeeUrgedService = new BigDecimal(resultSet.getString("receivableFeeUrgedService")).setScale(2, BigDecimal.ROUND_HALF_UP); 
				BigDecimal receivedfeeUrgedService = new BigDecimal(resultSet.getString("receivedfeeUrgedService")).setScale(2, BigDecimal.ROUND_HALF_UP); 
				if (receivableFeeUrgedService.compareTo(receivedfeeUrgedService) == -1
						|| receivableFeeUrgedService.compareTo(receivedfeeUrgedService) == 0) {
					colResult=ResultConstants.SUCCESS_DESC;
				} else if (receivedfeeUrgedService.compareTo(BigDecimal.ZERO) == 0) {
					colResult=ResultConstants.FAIL_DESC;
				} else if (receivedfeeUrgedService.compareTo(BigDecimal.ZERO) == 1
						&& receivedfeeUrgedService.compareTo(receivableFeeUrgedService) == -1) {
					colResult=ResultConstants.PART_SUCCESS_DESC;
				}
				
				Cell colResultCell = dataRow.createCell(6);
				colResultCell.setCellStyle(style);
				colResultCell.setCellValue(colResult);

				Cell lendingTimeCell = dataRow.createCell(7);
				lendingTimeCell.setCellStyle(style);
				lendingTimeCell.setCellValue(resultSet.getString("lendingTime").substring(0,10));
				Cell lastDeductDateCell = dataRow.createCell(8);
				lastDeductDateCell.setCellStyle(style);
				if(resultSet.getString("lastDeductDate") !=null){
				  lastDeductDateCell.setCellValue(resultSet.getString("lastDeductDate").substring(0,10));
				}else{
					lastDeductDateCell.setCellValue("");
				}
				
				String repayLabel = DictUtils.getLabel(dictMap, LoanDictType.URGE_REPAY_STATUS,resultSet.getString("dictPayStatus"));
				if (UrgeRepay.REPAIED.getName().equals(repayLabel)) {
					dictPayStatusLabel=YESNO.YES.getName();
				} else {
					dictPayStatusLabel=YESNO.NO.getName();
				}
				
				int days=0;
				if(resultSet.getString("monthOverdueDaysMax")!=null){
				   days = Integer.parseInt(resultSet.getString("monthOverdueDaysMax"));
				}
				if (days > 29) {
					backMoneyDesc=YESNO.NO.getName();
				} else {
					backMoneyDesc=YESNO.YES.getName();
				}
				
				Cell dictPayStatusLabelCell = dataRow.createCell(9);
				dictPayStatusLabelCell.setCellStyle(style);
				dictPayStatusLabelCell.setCellValue(dictPayStatusLabel);
				Cell backMoneyDescCell = dataRow.createCell(10);
				backMoneyDescCell.setCellStyle(style);
				backMoneyDescCell.setCellValue(backMoneyDesc);
				
				String payStatus = DictUtils.getLabel(dictMap, LoanDictType.PAY_STATUS, resultSet.getString("payStatus"));
				if(LoanApplyStatus.SETTLE.getName().equals(payStatus)){
					payStatusLabel=LoanApplyStatus.SETTLE.getName();
				}else if(LoanApplyStatus.EARLY_SETTLE.getName().equals(payStatus)){
					payStatusLabel=LoanApplyStatus.EARLY_SETTLE.getName();
				}else if(LoanApplyStatus.REPAYMENT.getName().equals(payStatus)){
					payStatusLabel=LoanApplyStatus.REPAYMENT.getName();
				}else if(LoanApplyStatus.OVERDUE.getName().equals(payStatus)){
					payStatusLabel=LoanApplyStatus.OVERDUE.getName();
				}else if(LoanApplyStatus.SETTLE_CONFIRM.getName().equals(payStatus)){
					payStatusLabel=LoanApplyStatus.SETTLE_CONFIRM.getName();
				}
				
				Cell payStatusLabelCell = dataRow.createCell(11);
				payStatusLabelCell.setCellStyle(style);
				payStatusLabelCell.setCellValue(payStatusLabel);
				
				
				Cell loanFlagCell = dataRow.createCell(12);
				loanFlagCell.setCellStyle(style);
				loanFlagCell.setCellValue(resultSet.getString("loanFlag"));
				
				Cell productNameCell = dataRow.createCell(13);
				productNameCell.setCellStyle(style);
				productNameCell.setCellValue(resultSet.getString("product_name"));
				
				Cell feeCreditCell = dataRow.createCell(14);
				feeCreditCell.setCellStyle(style);
				feeCreditCell.setCellValue(resultSet.getString("feeCredit")); // 征信费
				
				Cell feePetitionCell = dataRow.createCell(15);
				feePetitionCell.setCellStyle(style);
				feePetitionCell.setCellValue(resultSet.getString("feePetition"));
				
				Cell feeSumCell = dataRow.createCell(16);
				feeSumCell.setCellStyle(style);
				feeSumCell.setCellValue(resultSet.getString("feeSum"));
				
				Cell urgeMoneyCell = dataRow.createCell(17);
				urgeMoneyCell.setCellStyle(style);
				urgeMoneyCell.setCellValue(resultSet.getString("urgeMoney")); //催收服务费
				k++;
				row = row + 1;
			}
			
			setAutoColumn(MAXCOLUMN, dataSheet);
		}

		private static void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
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
	        
			Row headerRow = dataSheet.createRow(1);
			Cell indexCellHeader = headerRow.createCell(0);
			indexCellHeader.setCellStyle(style);
			indexCellHeader.setCellValue("序号");
			Cell contractCodeCellHeader = headerRow.createCell(1);
			contractCodeCellHeader.setCellStyle(style);
			contractCodeCellHeader.setCellValue("合同编号");
			Cell customerNameCellHeader = headerRow.createCell(2);
			customerNameCellHeader.setCellStyle(style);
			customerNameCellHeader.setCellValue("客户姓名");
			Cell contractAmountCellHeader = headerRow.createCell(3);
			contractAmountCellHeader.setCellStyle(style);
			contractAmountCellHeader.setCellValue("合同金额");
			Cell receivableFeeUrgedServiceCellHeader = headerRow.createCell(4);
			receivableFeeUrgedServiceCellHeader.setCellStyle(style);
			receivableFeeUrgedServiceCellHeader.setCellValue("应收催收服务费");
			Cell receivedfeeUrgedServiceCellHeader = headerRow.createCell(5);
			receivedfeeUrgedServiceCellHeader.setCellStyle(style);
			receivedfeeUrgedServiceCellHeader.setCellValue("已收催收服务费");
			Cell colResultCellHeader = headerRow.createCell(6);
			colResultCellHeader.setCellStyle(style);
			colResultCellHeader.setCellValue("收取结果");
			Cell lendingTimeCellHeader = headerRow.createCell(7);
			lendingTimeCellHeader.setCellStyle(style);
			lendingTimeCellHeader.setCellValue("放款时间");
			Cell lastDeductDateCellHeader = headerRow.createCell(8);
			lastDeductDateCellHeader.setCellStyle(style);
			lastDeductDateCellHeader.setCellValue("最后划扣日期");
			Cell dictPayStatusLabelCellHeader = headerRow.createCell(9);
			dictPayStatusLabelCellHeader.setCellStyle(style);
			dictPayStatusLabelCellHeader.setCellValue("是否返还");
			Cell backMoneyDescCellHeader = headerRow.createCell(10);
			backMoneyDescCellHeader.setCellStyle(style);
			backMoneyDescCellHeader.setCellValue("是否符合退款条件");
			Cell payStatusLabelCellHeader = headerRow.createCell(11);
			payStatusLabelCellHeader.setCellStyle(style);
			payStatusLabelCellHeader.setCellValue("还款状态");
			Cell loanFlagCellHeader = headerRow.createCell(12);
			loanFlagCellHeader.setCellStyle(style);
			loanFlagCellHeader.setCellValue("标识");
			Cell productNameHeader = headerRow.createCell(13);
			productNameHeader.setCellStyle(style);
			productNameHeader.setCellValue("借款产品");
			Cell feeCreditHeader = headerRow.createCell(14);
			feeCreditHeader.setCellStyle(style);
			feeCreditHeader.setCellValue("征信费");
			Cell feePetitionHeader = headerRow.createCell(15);
			feePetitionHeader.setCellStyle(style);
			feePetitionHeader.setCellValue("信访费");
			Cell feeSumCellHeader = headerRow.createCell(16);
			feeSumCellHeader.setCellStyle(style);
			feeSumCellHeader.setCellValue("费用总计");
			Cell urgeMoneyCellHeader = headerRow.createCell(17);
			urgeMoneyCellHeader.setCellStyle(style);
			urgeMoneyCellHeader.setCellValue("催收服务费");
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
}
