package com.creditharmony.loan.borrow.serve.excel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 导出
 * @Class Name ExportHelper
 * @author WJJ
 * @Create In 2016年12月8日
 */
@SuppressWarnings("deprecation")
public class ExportHelper {
	private static Logger logger = LoggerFactory.getLogger(ExportHelper.class);

	public static void export(Map<String, Object> queryMap,
			HttpServletResponse response,String fileName) {
		//列数
		final int MAXCOLUMN = 6;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			//String fileSheetName = threePartFileName.getGoldCreditExportFileName();
			Sheet dataSheet = wb.createSheet(fileName);
			title(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.serve.dao.CustomerServeDao.export",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			bodyExcelCell(wb,resultSet, dataSheet,MAXCOLUMN);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName + FileExtension.XLSX)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName + FileExtension.XLSX));
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
	
	private static void title(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
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
        
        String[] header = {"客户姓名","合同编号","门店名称","结清日期","邮箱","发送状态","发送日期"};
		Row headerRow = dataSheet.createRow(1);
		for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
	}
	private static void bodyExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN)
			throws SQLException {
		int row = 2;
		
		Row dataRow;
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell customerNameCell = dataRow.createCell(0);
			customerNameCell.setCellValue(resultSet.getString("customer_name"));
			customerNameCell.setCellStyle(textStyle(wb));
			
			Cell contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellValue(resultSet.getString("contract_code"));
			contractCodeCell.setCellStyle(textStyle(wb));
			
			Cell orgNameCell = dataRow.createCell(2);
			orgNameCell.setCellValue(resultSet.getString("name"));
			orgNameCell.setCellStyle(textStyle(wb));
			
			Cell settleDayCell = dataRow.createCell(3);
			settleDayCell.setCellValue(resultSet.getString("settled_date"));
			settleDayCell.setCellStyle(textStyle(wb));
			
			Cell emailCell = dataRow.createCell(4);
			emailCell.setCellValue(resultSet.getString("recipient_email"));
			emailCell.setCellStyle(textStyle(wb));
			
			//String status = DictCache.getInstance().getDictLabel("jk_cm_admin_email_status", resultSet.getString("dict_mail_status"));
			Cell statusCell = dataRow.createCell(5);

			statusCell.setCellValue("已发送");
			statusCell.setCellStyle(textStyle(wb));
			
			Cell modifyTimeCell = dataRow.createCell(6);
			modifyTimeCell.setCellValue(resultSet.getString("modify_time"));
			modifyTimeCell.setCellStyle(textStyle(wb));
			row ++;
		}

		setAutoColumn(MAXCOLUMN, dataSheet);
	}
	
	
	
	private static Double praseDoble(String num){
		Double number = 0.00;
		 String reg = "^\\d+(\\.\\d+)?$";
		 Pattern p = Pattern.compile(reg);  
		if (StringUtils.isNotBlank(num) && p.matcher(num).matches()) {
			number = Double.valueOf(num);
		}
		return number;
	}
	
	private static CellStyle decimal3Style(SXSSFWorkbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom((short) 1);   
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
        DataFormat hssfDataFormat = wb.createDataFormat(); 
        cellStyle.setDataFormat(hssfDataFormat.getFormat("#,##0.000_);(#,##0.000)"));
        return cellStyle;
	}
	
	//保留两位小数格式
		private static CellStyle decimal2Style(SXSSFWorkbook wb){
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);   
	        cellStyle.setBorderTop((short) 1);
	        cellStyle.setBorderLeft((short) 1);
	        cellStyle.setBorderRight((short) 1);
	        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        cellStyle.setFont(font);
	        
	        DataFormat hssfDataFormat = wb.createDataFormat(); 
	        cellStyle.setDataFormat(hssfDataFormat.getFormat("#,##0.00_);(#,##0.00)"));
	        /*newCell.setCellStyle(cellStyle);
	        newCell.setCellValue(new Double(cellVal));
	        newCell.setCellType(Cell.CELL_TYPE_NUMERIC);*/
	        
	        return cellStyle;
		}
		
	//文本格式
		private static CellStyle textStyle(SXSSFWorkbook wb) {
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);   
	        cellStyle.setBorderTop((short) 1);
	        cellStyle.setBorderLeft((short) 1);
	        cellStyle.setBorderRight((short) 1);
	        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        cellStyle.setFont(font);
			DataFormat format = wb.createDataFormat();  
	        cellStyle.setDataFormat(format.getFormat("@"));
	        return cellStyle;
		}
		
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
			            if(StringUtils.isNotEmpty(currentCell.toString())) {
			            	int length = currentCell.toString().getBytes().length; 
				            if (columnWidth < length) {  
				                columnWidth = length;  
				            } 
			            }
			        }  
			        sheet.setColumnWidth(columnNum, columnWidth * 256);
			    }  
			}  
		}
		
		private static String format (String data,String pattern) {
			String formatData = "";
			NumberFormat nf = new DecimalFormat(pattern);
			if (StringUtils.isNotEmpty(data)) 
				formatData = nf.format(Double.parseDouble(data));
			return formatData;
		}
}
