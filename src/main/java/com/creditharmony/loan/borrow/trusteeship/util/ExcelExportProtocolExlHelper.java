/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.trusteeship.utilExcelExportProtocolExlHelper.java
 * @Create By 尚军伟
 * @Create In 2016年4月25日 下午6:38:42
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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.ProtocolLibraryAccountType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.consts.FileExtension;


@SuppressWarnings("deprecation")
public class ExcelExportProtocolExlHelper {

	private static Logger logger = LoggerFactory.getLogger(ExcelExportProtocolExlHelper.class);

	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 8;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.TRUSTEESHIP_PROTOCOL + System.currentTimeMillis() ;
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
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell businessTypeCell = dataRow.createCell(0);
			businessTypeCell.setCellStyle(style);
			businessTypeCell.setCellValue("AC01(" + DeductFlagType.COLLECTION.getName() + ")");
			Cell customerNameCell = dataRow.createCell(1);
			customerNameCell.setCellStyle(style);
			customerNameCell.setCellValue(resultSet.getString("customer_name"));
			Cell customerPhoneFirstCell = dataRow.createCell(2);
			customerPhoneFirstCell.setCellStyle(style);
			// 解密手机号
			customerPhoneFirstCell.setCellValue(GrantUtil.getNum(resultSet.getString("customer_phone_first")));
			Cell dictCertTypeCell = dataRow.createCell(3);
			dictCertTypeCell.setCellStyle(style);
			dictCertTypeCell.setCellValue(CertificateType.parseByCode(resultSet.getString("dict_cert_type")).getName());
			Cell customerCertNumCell = dataRow.createCell(4);
			customerCertNumCell.setCellStyle(style);
			customerCertNumCell.setCellValue(resultSet.getString("customer_cert_num"));
			Cell bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellStyle(style);
			bankAccountCell.setCellValue(resultSet.getString("bank_account"));
			Cell bankAccountTypeCell = dataRow.createCell(6);
			bankAccountTypeCell.setCellStyle(style);
			bankAccountTypeCell.setCellValue(ProtocolLibraryAccountType.JJK.getName());
			Cell bankNameStrCell = dataRow.createCell(7);
			bankNameStrCell.setCellStyle(style);
			String bn=DictCache.getInstance().getDictLabel("jk_open_bank",resultSet.getString("bankName"));
			bankNameStrCell.setCellValue(bn);
			Cell backCallCell = dataRow.createCell(8);
			backCallCell.setCellStyle(style);
			backCallCell.setCellValue(YESNO.NO.getName());
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
		Cell businessTypeCellHeader = headerRow.createCell(0);
		businessTypeCellHeader.setCellStyle(style);
		businessTypeCellHeader.setCellValue("业务类型");
		Cell customerNameCellHeader = headerRow.createCell(1);
		customerNameCellHeader.setCellStyle(style);
		customerNameCellHeader.setCellValue("客户姓名");
		Cell customerPhoneFirstCellHeader = headerRow.createCell(2);
		customerPhoneFirstCellHeader.setCellStyle(style);
		customerPhoneFirstCellHeader.setCellValue("手机号码");
		Cell dictCertTypeCellHeader = headerRow.createCell(3);
		dictCertTypeCellHeader.setCellStyle(style);
		dictCertTypeCellHeader.setCellValue("证件类型");
		Cell customerCertNumCellHeader = headerRow.createCell(4);
		customerCertNumCellHeader.setCellStyle(style);
		customerCertNumCellHeader.setCellValue("证件号码");
		Cell bankAccountCellHeader = headerRow.createCell(5);
		bankAccountCellHeader.setCellStyle(style);
		bankAccountCellHeader.setCellValue("帐号");
		Cell bankAccountTypeCellHeader = headerRow.createCell(6);
		bankAccountTypeCellHeader.setCellStyle(style);
		bankAccountTypeCellHeader.setCellValue("账户属性");
		Cell bankNameStrCellHeader = headerRow.createCell(7);
		bankNameStrCellHeader.setCellStyle(style);
		bankNameStrCellHeader.setCellValue("帐号行别");
		Cell backCallCellHeader = headerRow.createCell(8);
		backCallCellHeader.setCellStyle(style);
		backCallCellHeader.setCellValue("是否需要语音回拨");
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