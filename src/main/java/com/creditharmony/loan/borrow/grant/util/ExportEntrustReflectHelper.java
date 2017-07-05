/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.webExportEntrustReflectHelper.java
 * @Create By 尚军伟
 * @Create In 2016年4月20日 下午5:55:52
 */
package com.creditharmony.loan.borrow.grant.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.common.consts.FileExtension;


@SuppressWarnings("deprecation")
public class ExportEntrustReflectHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportEntrustReflectHelper.class);

	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 4;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = "PWTX_"+ DateUtils.getDate("yyyyMMdd")+"_0000";
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.trusteeship.dao.LoanExcelDao.getDataRows2",
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
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN)
			throws SQLException {
		int row = 1;
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
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell indexCell = dataRow.createCell(0);
			indexCell.setCellStyle(style);
			indexCell.setCellValue(k);
			Cell loginNameCell = dataRow.createCell(1);
			loginNameCell.setCellStyle(style);
			loginNameCell.setCellValue(resultSet.getString("payLoginName"));
			Cell chianNameCell = dataRow.createCell(2);
			chianNameCell.setCellStyle(style);
			chianNameCell.setCellValue(resultSet.getString("payChinaName"));
			Cell moneyCell = dataRow.createCell(3);
			moneyCell.setCellStyle(style);
			String grantMoney=resultSet.getString("grantMoney");
			String urgedServiceFee=resultSet.getString("urgedServiceFee");
			BigDecimal grantMoneyBiDecimal = (null != grantMoney
					&& !"".equals(grantMoney) ? new BigDecimal(grantMoney)
					: new BigDecimal("0"));
			BigDecimal urgedServiceFeeBiDecimal = (null != urgedServiceFee
					&& !"".equals(urgedServiceFee) ? new BigDecimal(
					urgedServiceFee) : new BigDecimal("0"));
			moneyCell.setCellValue(grantMoneyBiDecimal.subtract(
					urgedServiceFeeBiDecimal).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			Cell markCell = dataRow.createCell(4);
			markCell.setCellStyle(style);
			markCell.setCellValue(resultSet.getString("contractCode") + "_委托提现_"+resultSet.getString("id"));
			k++;
			row = row + 1;
		}
		setAutoColumn(MAXCOLUMN, dataSheet);
	}

	private static void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
//		Row titleRow = dataSheet.createRow(0);
//		titleRow.setHeight((short) (15.625*40)); 
//		Cell titleCell = titleRow.createCell(0);
//		dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,MAXCOLUMN));
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
//        titleCell.setCellValue(fileName);
//        titleCell.setCellStyle(style);
        
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
        
		Row headerRow = dataSheet.createRow(0);
		Cell lendCodeHeader = headerRow.createCell(0);
		lendCodeHeader.setCellStyle(style);
		lendCodeHeader.setCellValue("序号");
		Cell accountNoHeader = headerRow.createCell(1);
		accountNoHeader.setCellStyle(style);
		accountNoHeader.setCellValue("委托提现目标登录名");
		Cell accountNameHeader = headerRow.createCell(2);
		accountNameHeader.setCellStyle(style);
		accountNameHeader.setCellValue("委托提现目标中文名称");
		Cell backMoneyHeader = headerRow.createCell(3);
		backMoneyHeader.setCellStyle(style);
		backMoneyHeader.setCellValue("委托提现金额");
		Cell bankCodeHeader = headerRow.createCell(4);
		bankCodeHeader.setCellStyle(style);
		bankCodeHeader.setCellValue("备注信息");
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
