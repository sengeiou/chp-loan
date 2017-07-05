/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.utilExcelExportExcel1Helper.java
 * @Create By 尚军伟
 * @Create In 2016年4月26日 下午2:46:19
 */
package com.creditharmony.loan.borrow.trusteeship.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * @Class Name ExcelExportExcel1Helper
 * @author 尚军伟
 * @Create In 2016年4月26日
 */
@SuppressWarnings("deprecation")
public class ExcelExportAccountBusinessHelper {

	private static Logger logger = LoggerFactory.getLogger(ExcelExportAccountBusinessHelper.class);

	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 13;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.TRUSTEESHIP_BUSIENSS + System.currentTimeMillis() ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.trusteeship.dao.GoldAccountBusinessDao.getGoldAccountBusinessList",
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
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell storesNameCell = dataRow.createCell(0);
			storesNameCell.setCellStyle(style);
			storesNameCell.setCellValue(resultSet.getString("name"));
			Cell referralsDateCell = dataRow.createCell(1);
			referralsDateCell.setCellStyle(style);
			referralsDateCell.setCellValue(resultSet.getString("introduction_time"));
			Cell customerNameCell = dataRow.createCell(2);
			customerNameCell.setCellStyle(style);
			customerNameCell.setCellValue(resultSet.getString("customer_name"));
			Cell customerNumCell = dataRow.createCell(3);
			customerNumCell.setCellStyle(style);
			customerNumCell.setCellValue(resultSet.getString("customer_cert_num"));
			Cell bankNameCell = dataRow.createCell(4);
			bankNameCell.setCellStyle(style);
			bankNameCell.setCellValue(resultSet.getString("BANK_NAME"));
			Cell bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellStyle(style);
			bankAccountCell.setCellValue(resultSet.getString("bank_account"));
			Cell loanAuditAmountCell = dataRow.createCell(6);
			loanAuditAmountCell.setCellStyle(style);
			loanAuditAmountCell.setCellValue(resultSet.getString("LOAN_AUDIT_AMOUNT"));
			Cell feePetitionCell = dataRow.createCell(7);
			feePetitionCell.setCellStyle(style);
			feePetitionCell.setCellValue(resultSet.getString("FEE_CONSULT"));
			Cell feeExpeditedCell = dataRow.createCell(8);
			feeExpeditedCell.setCellStyle(style);
			feeExpeditedCell.setCellValue(resultSet.getString("FEE_EXPEDITED"));
			Cell grantAmountCell = dataRow.createCell(9);
			grantAmountCell.setCellStyle(style);
			grantAmountCell.setCellValue(resultSet.getString("grant_amount"));
			Cell loanMonthsCell = dataRow.createCell(10);
			loanMonthsCell.setCellStyle(style);
			loanMonthsCell.setCellValue(resultSet.getString("loan_months"));
			Cell channelCell = dataRow.createCell(11);
			channelCell.setCellStyle(style);
			channelCell.setCellValue(resultSet.getString("loan_flag"));
			Cell loanStatusCell = dataRow.createCell(12);
			loanStatusCell.setCellStyle(style);
			loanStatusCell.setCellValue(resultSet.getString("dict_loan_status"));
			Cell grantDateCell = dataRow.createCell(13);
			grantDateCell.setCellStyle(style);
			grantDateCell.setCellValue(resultSet.getString("lending_time"));
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
		Cell storesNameCellHeader = headerRow.createCell(0);
		storesNameCellHeader.setCellStyle(style);
		storesNameCellHeader.setCellValue("门店名称");
		Cell referralsDateCellHeader = headerRow.createCell(1);
		referralsDateCellHeader.setCellStyle(style);
		referralsDateCellHeader.setCellValue("推介日期");
		Cell customerNameCellHeader = headerRow.createCell(2);
		customerNameCellHeader.setCellStyle(style);
		customerNameCellHeader.setCellValue("客户姓名");
		Cell customerNumCellHeader = headerRow.createCell(3);
		customerNumCellHeader.setCellStyle(style);
		customerNumCellHeader.setCellValue("身份证号");
		Cell bankNameCellHeader = headerRow.createCell(4);
		bankNameCellHeader.setCellStyle(style);
		bankNameCellHeader.setCellValue("开户行");
		Cell bankAccountCellHeader = headerRow.createCell(5);
		bankAccountCellHeader.setCellStyle(style);
		bankAccountCellHeader.setCellValue("银行账号");
		Cell loanAuditAmountCellHeader = headerRow.createCell(6);
		loanAuditAmountCellHeader.setCellStyle(style);
		loanAuditAmountCellHeader.setCellValue("批借金额");
		Cell feePetitionCellHeader = headerRow.createCell(7);
		feePetitionCellHeader.setCellStyle(style);
		feePetitionCellHeader.setCellValue("外访费");
		Cell feeExpeditedCellHeader = headerRow.createCell(8);
		feeExpeditedCellHeader.setCellStyle(style);
		feeExpeditedCellHeader.setCellValue("加急费");
		Cell grantAmountCellHeader = headerRow.createCell(9);
		grantAmountCellHeader.setCellStyle(style);
		grantAmountCellHeader.setCellValue("放款金额");
		Cell loanMonthsCellHeader = headerRow.createCell(10);
		loanMonthsCellHeader.setCellStyle(style);
		loanMonthsCellHeader.setCellValue("期限");
		Cell channelCellHeader = headerRow.createCell(11);
		channelCellHeader.setCellStyle(style);
		channelCellHeader.setCellValue("渠道");
		Cell loanStatusCellHeader = headerRow.createCell(12);
		loanStatusCellHeader.setCellStyle(style);
		loanStatusCellHeader.setCellValue("借款状态");
		Cell grantDateCellHeader = headerRow.createCell(13);
		grantDateCellHeader.setCellStyle(style);
		grantDateCellHeader.setCellValue("放款日期");
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
