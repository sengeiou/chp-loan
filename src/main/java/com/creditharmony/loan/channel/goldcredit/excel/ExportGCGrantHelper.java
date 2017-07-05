package com.creditharmony.loan.channel.goldcredit.excel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
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
public class ExportGCGrantHelper {

	private static Logger logger = LoggerFactory.getLogger(ExportGCGrantHelper.class);
	
	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response) {
		final int MAXCOLUMN = 16;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.GC_CHANNEL_GRANT + DateUtils.getDate("yyyyMMddmmss") ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) queryMap.get("list");
			if(list!=null&&list.size()>0){
				MyBatisSql batisSql = MyBatisSqlUtil
						.getMyBatisSql(
								"com.creditharmony.loan.channel.goldcredit.dao.GCGrantDao.exportGrantList",
								queryMap, sqlSessionFactory);
				PreparedStatement ps = connection.prepareStatement(batisSql
						.toString());
				ResultSet resultSet = ps.executeQuery();
				assembleExcelCell(wb,resultSet, dataSheet,MAXCOLUMN);
			}
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+ FileExtension.XLS)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+ FileExtension.XLS));
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
			lendCodeCell.setCellValue(resultSet.getString("sequenceNumber"));
			Cell accountNoCell = dataRow.createCell(1);
			accountNoCell.setCellStyle(style);
			accountNoCell.setCellValue(resultSet.getString("STORESNAME"));
			Cell accountNameCell = dataRow.createCell(2);
			accountNameCell.setCellStyle(style);
			accountNameCell.setCellValue(resultSet.getString("CONTRACT_CODE"));
			Cell backMoneyCell = dataRow.createCell(3);
			backMoneyCell.setCellStyle(style);
			backMoneyCell.setCellValue(resultSet.getString("CUSTOMER_NAME"));
			Cell accountBankCell = dataRow.createCell(4);
			accountBankCell.setCellStyle(style);
			accountBankCell.setCellValue(resultSet.getString("CONTRACT_MONTHS"));
			Cell accountBranchCell = dataRow.createCell(5);
			accountBranchCell.setCellStyle(style);
			accountBranchCell.setCellValue(resultSet.getString("CONTRACT_AMOUNT"));
			Cell accountCardOrBookletCell = dataRow.createCell(6);
			accountCardOrBookletCell.setCellStyle(style);
			accountCardOrBookletCell.setCellValue(resultSet.getString("GRANT_AMOUNT"));
			Cell provinceCell = dataRow.createCell(7);
			provinceCell.setCellStyle(style);
			provinceCell.setCellValue(resultSet.getString("BANK_ACCOUNT"));
			//添加客户银行信息
			Cell bankNameCell = dataRow.createCell(8);
			bankNameCell.setCellStyle(style);
			String bankName = resultSet.getString("BANK_NAME");
			bankName = changeBankName(bankName);
			bankNameCell.setCellValue(bankName);
			Cell bankBranchCell = dataRow.createCell(9);
			bankBranchCell.setCellStyle(style);
			bankBranchCell.setCellValue(resultSet.getString("BANK_BRANCH"));
			
			Cell cityCell = dataRow.createCell(10);
			cityCell.setCellStyle(style);
			cityCell.setCellValue(resultSet.getString("CHANNEL_NAME"));
			Cell productNameCell = dataRow.createCell(11);
			productNameCell.setCellStyle(style);
			productNameCell.setCellValue(resultSet.getString("BANK_PROVINCE_NAME"));
			Cell applyLendDayCell = dataRow.createCell(12);
			applyLendDayCell.setCellStyle(style);
			applyLendDayCell.setCellValue(resultSet.getString("BANK_CITY_NAME"));
			Cell applyLendMoneyCell = dataRow.createCell(13);
			applyLendMoneyCell.setCellStyle(style);
			String label =  DictCache.getInstance().getDictLabel("jk_contract_ver", resultSet.getString("CONTRACT_VERSION"));
			applyLendMoneyCell.setCellValue(label);
			Cell applyPayCell = dataRow.createCell(14);
			applyPayCell.setCellStyle(style);
			applyPayCell.setCellValue(resultSet.getString("FEE_URGED_SERVICE"));
			Cell lendingTimeCell = dataRow.createCell(15);
			lendingTimeCell.setCellStyle(style);
			lendingTimeCell.setCellValue(resultSet.getString("LABEL"));
			Cell usingFlagCell = dataRow.createCell(16);
			usingFlagCell.setCellStyle(style);
			usingFlagCell.setCellValue(resultSet.getString("GRANT_BATCH"));
			
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
		lendCodeHeader.setCellValue("序号");
		Cell accountNoHeader = headerRow.createCell(1);
		accountNoHeader.setCellStyle(style);
		accountNoHeader.setCellValue("门店名称");
		Cell accountNameHeader = headerRow.createCell(2);
		accountNameHeader.setCellStyle(style);
		accountNameHeader.setCellValue("合同编号");
		Cell backMoneyHeader = headerRow.createCell(3);
		backMoneyHeader.setCellStyle(style);
		backMoneyHeader.setCellValue("客户姓名");
		Cell bankCodeHeader = headerRow.createCell(4);
		bankCodeHeader.setCellStyle(style);
		bankCodeHeader.setCellValue("期数");
		Cell accountBankHeader = headerRow.createCell(5);
		accountBankHeader.setCellStyle(style);
		accountBankHeader.setCellValue("合同金额");
		Cell accountBranchHeader = headerRow.createCell(6);
		accountBranchHeader.setCellStyle(style);
		accountBranchHeader.setCellValue("放款金额");
		Cell cardOrBookletHeader = headerRow.createCell(7);
		cardOrBookletHeader.setCellStyle(style);
		cardOrBookletHeader.setCellValue("客户账号");
		//添加客户银行名称
		Cell bankNameHeader = headerRow.createCell(8);
		bankNameHeader.setCellStyle(style);
		bankNameHeader.setCellValue("客户银行名称");
		
		Cell bankBranchHeader = headerRow.createCell(9);
		bankBranchHeader.setCellStyle(style);
		bankBranchHeader.setCellValue("客户银行支行名称");
		
		Cell provinceHeader = headerRow.createCell(10);
		provinceHeader.setCellStyle(style);
		provinceHeader.setCellValue("标识");
		Cell cityHeader = headerRow.createCell(11);
		cityHeader.setCellStyle(style);
		cityHeader.setCellValue("客户开卡省");
		Cell headerCell = headerRow.createCell(12);
		headerCell.setCellStyle(style);
		headerCell.setCellValue("客户开卡市");
		Cell backiIdHeader = headerRow.createCell(13);
		backiIdHeader.setCellStyle(style);
		backiIdHeader.setCellValue("合同版本号");
		Cell memoHeader = headerRow.createCell(14);
		memoHeader.setCellStyle(style);
		memoHeader.setCellValue("催收服务费");
		Cell applyLendDayHeader = headerRow.createCell(15);
		applyLendDayHeader.setCellStyle(style);
		applyLendDayHeader.setCellValue("加急标识");
		Cell applyLendMoneyHeader = headerRow.createCell(16);
		applyLendMoneyHeader.setCellStyle(style);
		applyLendMoneyHeader.setCellValue("放款批次");
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
	
	/**
	 * 修改银行名称
	 * @param bankName
	 * @return
	 */
	public static String changeBankName(String bankName){
		if(bankName != null && !"".equals(bankName)){
			if("中国光大银行".equals(bankName)){
				bankName = "光大银行";
			}else if("平安银行股份有限公司".equals(bankName)){
				bankName = "平安银行";
			}else if("中国邮政储蓄银行股份有限公司".equals(bankName)){
				bankName = "中国邮政储蓄银行";
			}
		}
		return bankName;
	}
}
