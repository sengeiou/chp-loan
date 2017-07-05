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
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * @Class Name ExcelExportExcel1Helper
 * @author 尚军伟
 * @Create In 2016年4月26日
 */
@SuppressWarnings("deprecation")
public class ExcelExportExcel1Helper {

	private static Logger logger = LoggerFactory.getLogger(ExcelExportExcel1Helper.class);

	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 11;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.TRUSTEESHIP_PENDING_LOAN1 + System.currentTimeMillis() ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.trusteeship.dao.LoanExcelDao.getDataRows",
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
		int k=1;
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell indexCell = dataRow.createCell(0);
			indexCell.setCellStyle(style);
			indexCell.setCellValue(k);
			Cell payLoginNameCell = dataRow.createCell(1);
			payLoginNameCell.setCellStyle(style);
			payLoginNameCell.setCellValue(Global.getConfig("jzh_fk_account"));
			Cell payChinaNameCell = dataRow.createCell(2);
			payChinaNameCell.setCellStyle(style);
			payChinaNameCell.setCellValue(Global.getConfig("jzh_fk_name"));
			Cell payFundFreezeCell = dataRow.createCell(3);
			payFundFreezeCell.setCellStyle(style);
			payFundFreezeCell.setCellValue(YESNO.NO.getName());
			Cell receiveLoginNameCell = dataRow.createCell(4);
			receiveLoginNameCell.setCellStyle(style);
			receiveLoginNameCell.setCellValue(resultSet.getString("receiveLoginName"));
			Cell receiveChinaNameCell = dataRow.createCell(5);
			receiveChinaNameCell.setCellStyle(style);
			receiveChinaNameCell.setCellValue(resultSet.getString("receiveChinaName"));
			Cell receiveFundFreezeCell = dataRow.createCell(6);
			receiveFundFreezeCell.setCellStyle(style);
			receiveFundFreezeCell.setCellValue(YESNO.YES.getName());
			Cell tradeMoneyCell = dataRow.createCell(7);
			tradeMoneyCell.setCellStyle(style);
			tradeMoneyCell.setCellValue(resultSet.getString("tradeMoney"));
			Cell markCell = dataRow.createCell(8);
			markCell.setCellStyle(style);
			markCell.setCellValue(resultSet.getString("mark") + "_" + IdGen.uuid());
			Cell preAuthorizationContractNumCell = dataRow.createCell(9);
			preAuthorizationContractNumCell.setCellStyle(style);
			preAuthorizationContractNumCell.setCellValue("");
			Cell contractVersionCell = dataRow.createCell(10);
			contractVersionCell.setCellStyle(style);
			contractVersionCell.setCellValue(resultSet.getString("contractVersion"));
			Cell urgedServiceFeeCell = dataRow.createCell(11);
			urgedServiceFeeCell.setCellStyle(style);
			urgedServiceFeeCell.setCellValue(resultSet.getString("urgedServiceFee"));
			k++;
			row = row + 1;
		}
		for (int i = 0; i <= MAXCOLUMN; i++) {
			dataSheet.autoSizeColumn((short)i,true); //调整第一列宽度自适应
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
		Cell customerNameCellHeader = headerRow.createCell(1);
		customerNameCellHeader.setCellStyle(style);
		customerNameCellHeader.setCellValue("付款方登录名");
		Cell customerCertNumCellHeader = headerRow.createCell(2);
		customerCertNumCellHeader.setCellStyle(style);
		customerCertNumCellHeader.setCellValue("付款方中文名");
		Cell customerPhoneFirstCellHeader = headerRow.createCell(3);
		customerPhoneFirstCellHeader.setCellStyle(style);
		customerPhoneFirstCellHeader.setCellValue("付款金额来自冻结");
		Cell customerEmailCellHeader = headerRow.createCell(4);
		customerEmailCellHeader.setCellStyle(style);
		customerEmailCellHeader.setCellValue("收款方登录名");
		Cell bankJzhKhhssCellHeader = headerRow.createCell(5);
		bankJzhKhhssCellHeader.setCellStyle(style);
		bankJzhKhhssCellHeader.setCellValue("收款方中文名");
		Cell bankJzhKhhqxCellHeader = headerRow.createCell(6);
		bankJzhKhhqxCellHeader.setCellStyle(style);
		bankJzhKhhqxCellHeader.setCellValue("首款后立即冻结");
		Cell bankNameCellHeader = headerRow.createCell(7);
		bankNameCellHeader.setCellStyle(style);
		bankNameCellHeader.setCellValue("交易金额");
		Cell bankBranchCellHeader = headerRow.createCell(8);
		bankBranchCellHeader.setCellStyle(style);
		bankBranchCellHeader.setCellValue("备注信息");
		Cell bankAccountNameCellHeader = headerRow.createCell(9);
		bankAccountNameCellHeader.setCellStyle(style);
		bankAccountNameCellHeader.setCellValue("预授权合同号");
		Cell bankAccountCellHeader = headerRow.createCell(10);
		bankAccountCellHeader.setCellStyle(style);
		bankAccountCellHeader.setCellValue("合同版本号");
		Cell passCellHeader = headerRow.createCell(11);
		passCellHeader.setCellStyle(style);
		passCellHeader.setCellValue("催收服务费");
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