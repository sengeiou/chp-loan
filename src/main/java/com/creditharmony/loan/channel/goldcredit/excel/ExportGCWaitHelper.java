package com.creditharmony.loan.channel.goldcredit.excel;

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
 * 导出回息数据帮助类
 * @Class Name ExportBackInterestHelper
 * @author 张永生
 * @Create In 2016年4月14日
 */
@SuppressWarnings("deprecation")
public class ExportGCWaitHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportGCWaitHelper.class);
	
	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 18;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.JINXIN_PENDING_LOAN_AUDIT + System.currentTimeMillis() ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.channel.goldcredit.dao.GCGrantDao.exportGrantList",
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
			Cell lendCodeCell = dataRow.createCell(0);
			lendCodeCell.setCellStyle(style);
			lendCodeCell.setCellValue(resultSet.getString("SEQUENCENUMBER"));
			Cell accountNoCell = dataRow.createCell(1);
			accountNoCell.setCellStyle(style);
			accountNoCell.setCellValue(resultSet.getString("STORESNAME"));
			Cell accountNameCell = dataRow.createCell(2);
			accountNameCell.setCellStyle(style);
			accountNameCell.setCellValue(resultSet.getString("CONTRACT_CODE"));
			Cell backMoneyCell = dataRow.createCell(3);
			backMoneyCell.setCellStyle(style);
			backMoneyCell.setCellValue(resultSet.getString("CUSTOMER_NAME"));
			Cell accountBranchCell = dataRow.createCell(4);
			accountBranchCell.setCellStyle(style);
			accountBranchCell.setCellValue(resultSet.getString("CONTRACT_AMOUNT"));
			Cell accountCardOrBookletCell = dataRow.createCell(5);
			accountCardOrBookletCell.setCellStyle(style);
			accountCardOrBookletCell.setCellValue(resultSet.getString("GRANT_AMOUNT"));
			Cell provinceCell = dataRow.createCell(6);
			provinceCell.setCellStyle(style);
			provinceCell.setCellValue(resultSet.getString("BANK_ACCOUNT"));
			Cell cityCell = dataRow.createCell(7);
			cityCell.setCellStyle(style);
			cityCell.setCellValue(resultSet.getString("BANK_NAME"));
			Cell productNameCell = dataRow.createCell(8);
			productNameCell.setCellStyle(style);
			productNameCell.setCellValue(resultSet.getString("CHANNEL_NAME"));
			Cell applyLendDayCell = dataRow.createCell(9);
			applyLendDayCell.setCellStyle(style);
			applyLendDayCell.setCellValue(resultSet.getString("FEE_URGED_SERVICE"));
			Cell applyLendMoneyCell = dataRow.createCell(10);
			applyLendMoneyCell.setCellStyle(style);
			applyLendMoneyCell.setCellValue(resultSet.getString("LABEL"));
			Cell applyPayCell = dataRow.createCell(11);
			applyPayCell.setCellStyle(style);
			applyPayCell.setCellValue(resultSet.getString("GRANT_BATCH"));
			Cell lendingTimeCell = dataRow.createCell(12);
			lendingTimeCell.setCellStyle(style);
			lendingTimeCell.setCellValue(resultSet.getString("LOAN_TYPE"));
			Cell usingFlagCell = dataRow.createCell(13);
			usingFlagCell.setCellStyle(style);
			usingFlagCell.setCellValue(resultSet.getString("PRODUCT_NAME"));
			Cell loanFlagCell = dataRow.createCell(14);
			loanFlagCell.setCellStyle(style);
			loanFlagCell.setCellValue(resultSet.getString("UNDEDUCT_MONEY"));
			Cell monthCell = dataRow.createCell(15);
			monthCell.setCellStyle(style);
			monthCell.setCellValue(resultSet.getString("REPLY_MONTH"));
			Cell BANK_ACCOUNT_NAME = dataRow.createCell(16);
			BANK_ACCOUNT_NAME.setCellStyle(style);
			BANK_ACCOUNT_NAME.setCellValue(resultSet.getString("BANK_ACCOUNT_NAME"));
			Cell LENDING_TIME = dataRow.createCell(17);
			LENDING_TIME.setCellStyle(style);
			LENDING_TIME.setCellValue(resultSet.getString("LENDING_TIME"));
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
        String [] header = {"序号","门店名称","合同编号","客户姓名","合同金额","放款金额","客户账号","开户行","渠道","催收服务费","加急标识","放款批次","借款类型","借款产品","未划金额","批复期限","账号姓名","放款时间"};
        Row headerRow = dataSheet.createRow(1);
        for (int i = 0; i < header.length; i++) {
			Cell lendCodeHeader = headerRow.createCell(i);
			lendCodeHeader.setCellStyle(style);
			lendCodeHeader.setCellValue(header[i]);
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
}
