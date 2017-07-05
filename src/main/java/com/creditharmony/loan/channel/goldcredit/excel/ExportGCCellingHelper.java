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
public class ExportGCCellingHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportGCCellingHelper.class);
	
	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 15;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.GC_CHANNEL + System.currentTimeMillis() ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.channel.goldcredit.dao.GCCeilingDao.getCeilingList",
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
			lendCodeCell.setCellValue(resultSet.getString("name"));
			Cell accountNoCell = dataRow.createCell(1);
			accountNoCell.setCellStyle(style);
			accountNoCell.setCellValue(resultSet.getString("introduction_time"));
			Cell accountNameCell = dataRow.createCell(2);
			accountNameCell.setCellStyle(style);
			accountNameCell.setCellValue(resultSet.getString("customer_name"));
			Cell backMoneyCell = dataRow.createCell(3);
			backMoneyCell.setCellStyle(style);
			backMoneyCell.setCellValue(resultSet.getString("customer_cert_num"));
			Cell accountBankCell = dataRow.createCell(4);
			accountBankCell.setCellStyle(style);
			accountBankCell.setCellValue(resultSet.getString("bank_name"));
			Cell accountBranchCell = dataRow.createCell(5);
			accountBranchCell.setCellStyle(style);
			accountBranchCell.setCellValue(resultSet.getString("bank_account"));
			Cell accountCardOrBookletCell = dataRow.createCell(6);
			accountCardOrBookletCell.setCellStyle(style);
			accountCardOrBookletCell.setCellValue(resultSet.getString("loan_audit_amount"));
			Cell provinceCell = dataRow.createCell(7);
			provinceCell.setCellStyle(style);
			provinceCell.setCellValue(resultSet.getString("contract_amount"));
			Cell cityCell = dataRow.createCell(8);
			cityCell.setCellStyle(style);
			cityCell.setCellValue(resultSet.getString("fee_consult"));
			Cell productNameCell = dataRow.createCell(9);
			productNameCell.setCellStyle(style);
			productNameCell.setCellValue(resultSet.getString("fee_expedited"));
			Cell applyLendDayCell = dataRow.createCell(10);
			applyLendDayCell.setCellStyle(style);
			applyLendDayCell.setCellValue(resultSet.getString("loan_months"));
			Cell applyLendMoneyCell = dataRow.createCell(11);
			applyLendMoneyCell.setCellStyle(style);
			applyLendMoneyCell.setCellValue(resultSet.getString("dict_loan_status"));
			Cell applyPayCell = dataRow.createCell(12);
			applyPayCell.setCellStyle(style);
			applyPayCell.setCellValue(resultSet.getString("grant_amount"));
			Cell lendingTimeCell = dataRow.createCell(13);
			lendingTimeCell.setCellStyle(style);
			lendingTimeCell.setCellValue(resultSet.getString("lending_time"));
			Cell usingFlagCell = dataRow.createCell(14);
			usingFlagCell.setCellStyle(style);
			usingFlagCell.setCellValue(resultSet.getString("using_flag"));
			Cell loanFlagCell = dataRow.createCell(15);
			loanFlagCell.setCellStyle(style);
			loanFlagCell.setCellValue(resultSet.getString("loan_flag"));
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
		Cell lendCodeHeader = headerRow.createCell(0);
		lendCodeHeader.setCellStyle(style);
		lendCodeHeader.setCellValue("门店名称");
		Cell accountNoHeader = headerRow.createCell(1);
		accountNoHeader.setCellStyle(style);
		accountNoHeader.setCellValue("推介日期");
		Cell accountNameHeader = headerRow.createCell(2);
		accountNameHeader.setCellStyle(style);
		accountNameHeader.setCellValue("客户姓名");
		Cell backMoneyHeader = headerRow.createCell(3);
		backMoneyHeader.setCellStyle(style);
		backMoneyHeader.setCellValue("身份证号");
		Cell bankCodeHeader = headerRow.createCell(4);
		bankCodeHeader.setCellStyle(style);
		bankCodeHeader.setCellValue("开户行");
		Cell accountBankHeader = headerRow.createCell(5);
		accountBankHeader.setCellStyle(style);
		accountBankHeader.setCellValue("银行账号");
		Cell accountBranchHeader = headerRow.createCell(6);
		accountBranchHeader.setCellStyle(style);
		accountBranchHeader.setCellValue("批借金额");
		Cell cardOrBookletHeader = headerRow.createCell(7);
		cardOrBookletHeader.setCellStyle(style);
		cardOrBookletHeader.setCellValue("合同金额");
		Cell provinceHeader = headerRow.createCell(8);
		provinceHeader.setCellStyle(style);
		provinceHeader.setCellValue("外访费");
		Cell cityHeader = headerRow.createCell(9);
		cityHeader.setCellStyle(style);
		cityHeader.setCellValue("加急费");
		Cell headerCell = headerRow.createCell(10);
		headerCell.setCellStyle(style);
		headerCell.setCellValue("放款金额");
		Cell backiIdHeader = headerRow.createCell(11);
		backiIdHeader.setCellStyle(style);
		backiIdHeader.setCellValue("期限");
		Cell memoHeader = headerRow.createCell(12);
		memoHeader.setCellStyle(style);
		memoHeader.setCellValue("借款状态");
		Cell applyLendDayHeader = headerRow.createCell(13);
		applyLendDayHeader.setCellStyle(style);
		applyLendDayHeader.setCellValue("放款日期");
		Cell applyLendMoneyHeader = headerRow.createCell(14);
		applyLendMoneyHeader.setCellStyle(style);
		applyLendMoneyHeader.setCellValue("数据状态");
		Cell applyPayHeader = headerRow.createCell(15);
		applyPayHeader.setCellStyle(style);
		applyPayHeader.setCellValue("渠道");
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
