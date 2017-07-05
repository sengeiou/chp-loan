/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.utilExcelExportAccountExlHelper.java
 * @Create By 尚军伟
 * @Create In 2016年4月25日 下午3:01:35
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
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.consts.FileExtension;


@SuppressWarnings("deprecation")
public class ExcelExportAccountExlHelper {
	private static Logger logger = LoggerFactory.getLogger(ExcelExportAccountExlHelper.class);

	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 12;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = "PW10_"+DateUtils.getDate("yyyyMMdd")+"_0000";
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.common.dao.LoanCustomerDao.selectAllTrusteeAccount",
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
			Cell customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellStyle(style);
			customerNameCell.setCellValue(resultSet.getString("customer_name"));
			Cell customerCertNumCell = dataRow.createCell(2);
			customerCertNumCell.setCellStyle(style);
			customerCertNumCell.setCellValue(resultSet.getString("customer_cert_num"));
			Cell customerPhoneFirstCell = dataRow.createCell(3);
			customerPhoneFirstCell.setCellStyle(style);
			// 解密手机号
			customerPhoneFirstCell.setCellValue(GrantUtil.getNum(resultSet.getString("customer_phone_first")));
			Cell customerEmailCell = dataRow.createCell(4);
			customerEmailCell.setCellStyle(style);
			customerEmailCell.setCellValue(resultSet.getString("customer_email"));
			Cell bankJzhKhhssCell = dataRow.createCell(5);
			bankJzhKhhssCell.setCellStyle(style);
			bankJzhKhhssCell.setCellValue(resultSet.getString("bankJzhKhhss"));
			Cell bankJzhKhhqxCell = dataRow.createCell(6);
			bankJzhKhhqxCell.setCellStyle(style);
			bankJzhKhhqxCell.setCellValue(resultSet.getString("bankJzhKhhqx"));
			Cell bankNameCell = dataRow.createCell(7);
			bankNameCell.setCellStyle(style);
			String bn=DictCache.getInstance().getDictLabel("jk_open_bank",resultSet.getString("bankName"));
			bankNameCell.setCellValue(bn);
			Cell bankBranchCell = dataRow.createCell(8);
			bankBranchCell.setCellStyle(style);
			bankBranchCell.setCellValue(resultSet.getString("bank_branch"));
			Cell bankAccountNameCell = dataRow.createCell(9);
			bankAccountNameCell.setCellStyle(style);
			bankAccountNameCell.setCellValue(resultSet.getString("bank_account_name"));
			Cell bankAccountCell = dataRow.createCell(10);
			bankAccountCell.setCellStyle(style);
			bankAccountCell.setCellValue(resultSet.getString("bank_account"));
			Cell passCell = dataRow.createCell(11);
			passCell.setCellStyle(style);
			passCell.setCellValue("");
			Cell remarkCell = dataRow.createCell(12);
			remarkCell.setCellStyle(style);
			remarkCell.setCellValue("");
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
		Cell indexCellHeader = headerRow.createCell(0);
		indexCellHeader.setCellStyle(style);
		indexCellHeader.setCellValue("序号");
		Cell customerNameCellHeader = headerRow.createCell(1);
		customerNameCellHeader.setCellStyle(style);
		customerNameCellHeader.setCellValue("客户姓名");
		Cell customerCertNumCellHeader = headerRow.createCell(2);
		customerCertNumCellHeader.setCellStyle(style);
		customerCertNumCellHeader.setCellValue("身份证号码");
		Cell customerPhoneFirstCellHeader = headerRow.createCell(3);
		customerPhoneFirstCellHeader.setCellStyle(style);
		customerPhoneFirstCellHeader.setCellValue("手机号码");
		Cell customerEmailCellHeader = headerRow.createCell(4);
		customerEmailCellHeader.setCellStyle(style);
		customerEmailCellHeader.setCellValue("邮箱地址");
		Cell bankJzhKhhssCellHeader = headerRow.createCell(5);
		bankJzhKhhssCellHeader.setCellStyle(style);
		bankJzhKhhssCellHeader.setCellValue("开户行省市");
		Cell bankJzhKhhqxCellHeader = headerRow.createCell(6);
		bankJzhKhhqxCellHeader.setCellStyle(style);
		bankJzhKhhqxCellHeader.setCellValue("开户行区县");
		Cell bankNameCellHeader = headerRow.createCell(7);
		bankNameCellHeader.setCellStyle(style);
		bankNameCellHeader.setCellValue("开户行行别");
		Cell bankBranchCellHeader = headerRow.createCell(8);
		bankBranchCellHeader.setCellStyle(style);
		bankBranchCellHeader.setCellValue("开户行支行名称");
		Cell bankAccountNameCellHeader = headerRow.createCell(9);
		bankAccountNameCellHeader.setCellStyle(style);
		bankAccountNameCellHeader.setCellValue("户名");
		Cell bankAccountCellHeader = headerRow.createCell(10);
		bankAccountCellHeader.setCellStyle(style);
		bankAccountCellHeader.setCellValue("帐号");
		Cell passCellHeader = headerRow.createCell(11);
		passCellHeader.setCellStyle(style);
		passCellHeader.setCellValue("初始密码");
		Cell remarkCellHeader = headerRow.createCell(12);
		remarkCellHeader.setCellStyle(style);
		remarkCellHeader.setCellValue("备注");
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
		    for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {  
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