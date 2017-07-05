package com.creditharmony.loan.channel.goldcredit.excel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.channel.goldcredit.service.SummaryService;
import com.creditharmony.loan.channel.goldcredit.view.SummaryCount;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 导出回息数据帮助类
 * @Class Name ExportBackInterestHelper
 * @author 张永生
 * @Create In 2016年4月14日
 */
@SuppressWarnings("deprecation")
@Component 
public class ExportSummaryHelper {
	 SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");
	private  Logger logger = LoggerFactory.getLogger(ExportSummaryHelper.class);
    @Autowired 
    private  SummaryService service;
	public  void exportData(Map<String, Object> queryMap,
			Map<String,Object> totalDeducts, HttpServletResponse response) {
		final int MAXCOLUMN = 32;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			
			
			SummaryCount parm = new SummaryCount();
			parm.setCreateDate(format.format(new Date()));
			parm = service.querySummaryExport(parm);
			String fileName = "";
			if(parm  == null){
				 fileName = FileExtension.SUMMARY_LIST + format.format(new Date())+"-"+1 ;
				 parm = new SummaryCount();
				 parm.setCreateDate(format.format(new Date()));
				 parm.setExportNumber(1);
				 parm.preInsert();
				 service.saveSummaryExport(parm);
			}else{
				 fileName = FileExtension.SUMMARY_LIST + format.format(new Date())+"-"+(parm.getExportNumber()+1) ;
				 parm.setCreateDate(format.format(new Date()));
				 parm.setExportNumber(parm.getExportNumber()+1);
				 parm.preUpdate();
				 service.updateSummaryExport(parm);
			}
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.channel.goldcredit.dao.SummaryDao.getCeilingListExcel",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(wb,resultSet, dataSheet,MAXCOLUMN,totalDeducts);
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

	private  void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN, Map<String, Object> totalDeducts)
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
		int i = 1;
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();

		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell lendCodeCell = dataRow.createCell(0);
			lendCodeCell.setCellStyle(style);
			lendCodeCell.setCellValue(i);

			Cell contractCodeCell = dataRow.createCell(1);
			contractCodeCell.setCellStyle(style);
			contractCodeCell.setCellValue(resultSet.getString("contractCode"));
			
			Cell customerNameCell = dataRow.createCell(2);
			customerNameCell.setCellStyle(style);
			customerNameCell.setCellValue(resultSet.getString("customerName"));
			
			Cell customerCertNumCell = dataRow.createCell(3);
			customerCertNumCell.setCellStyle(style);
			customerCertNumCell.setCellValue(resultSet.getString("customerCertNum"));
			
			String bestCoborrower = resultSet.getString("bestCoborrower");
			Cell bestCoborrowerCell = dataRow.createCell(4);
			bestCoborrowerCell.setCellStyle(style);
			bestCoborrowerCell.setCellValue(resultSet.getString("bestCoborrower"));
			
			Cell farenCoborrowerCell = dataRow.createCell(5);
			farenCoborrowerCell.setCellStyle(style);
			farenCoborrowerCell.setCellValue(resultSet.getString("farenCoborrower"));
			
			Cell loanAuditAmountCell = dataRow.createCell(6);
			loanAuditAmountCell.setCellStyle(style);
			String loanAuditAmount = resultSet.getString("loanAuditAmount");
			if(!ObjectHelper.isEmpty(loanAuditAmount)){
				loanAuditAmount = getDecimalFormat(loanAuditAmount);
			}
			loanAuditAmountCell.setCellValue(loanAuditAmount);
			
			Cell contractAmountCell = dataRow.createCell(7);
			contractAmountCell.setCellStyle(style);
			
			String contractAmount = resultSet.getString("contractAmount");
			if(!ObjectHelper.isEmpty(contractAmount)){
				contractAmount = getDecimalFormat(contractAmount);
			}	
			contractAmountCell.setCellValue(contractAmount);
			
			Cell grantAmountCell = dataRow.createCell(8);
			grantAmountCell.setCellStyle(style);
			String grantAmount  = resultSet.getString("grantAmount");
			if(!ObjectHelper.isEmpty(grantAmount)){
				grantAmount = getDecimalFormat(grantAmount);
			}	
			grantAmountCell.setCellValue(grantAmount);
			
			Cell feeConsultCell = dataRow.createCell(9);
			feeConsultCell.setCellStyle(style);
			String feeConsult = resultSet.getString("feeConsult");
			if(!ObjectHelper.isEmpty(feeConsult)){
				feeConsult = getDecimalFormat(feeConsult);
			}	
			feeConsultCell.setCellValue(feeConsult);
			
			Cell feeCountCell = dataRow.createCell(10);
			feeCountCell.setCellStyle(style);
			String feeCount = resultSet.getString("feeCount");
			if(!ObjectHelper.isEmpty(feeCount)){
				feeCount = getDecimalFormat(feeCount);
			}	
			feeCountCell.setCellValue(feeCount);
			
			
			Cell feeUrgedServiceCell = dataRow.createCell(11);
			feeUrgedServiceCell.setCellStyle(style);
			String feeUrgedService = resultSet.getString("feeUrgedService");
			if(!ObjectHelper.isEmpty(feeUrgedService)){
				feeUrgedService = getDecimalFormat(feeUrgedService);
			}	
			feeUrgedServiceCell.setCellValue(feeUrgedService);
			
			Cell monthFeeServiceCell = dataRow.createCell(12);
			monthFeeServiceCell.setCellStyle(style);
			String monthFeeService = resultSet.getString("monthFeeService");
			if(!ObjectHelper.isEmpty(monthFeeService)){
				monthFeeService = getDecimalFormat(monthFeeService);
			}		
			monthFeeServiceCell.setCellValue(monthFeeService);
			
			Cell contractMonthRepayTotalCell = dataRow.createCell(13);
			contractMonthRepayTotalCell.setCellStyle(style);
			String contractMonthRepayTotal = resultSet.getString("contractMonthRepayTotal");
			if(!ObjectHelper.isEmpty(contractMonthRepayTotal)){
				contractMonthRepayTotal = getDecimalFormat(contractMonthRepayTotal);
			}	
			contractMonthRepayTotalCell.setCellValue(contractMonthRepayTotal);
		
			
			Cell productNameCell = dataRow.createCell(14);
			productNameCell.setCellStyle(style);
			productNameCell.setCellValue(resultSet.getString("productName"));
			
			Cell loanMonthsCell = dataRow.createCell(15);
			loanMonthsCell.setCellStyle(style);
			loanMonthsCell.setCellValue(resultSet.getString("loanMonths"));
			
			Cell riskLevelCell = dataRow.createCell(16);
			riskLevelCell.setCellStyle(style);
			riskLevelCell.setCellValue(resultSet.getString("riskLevel"));
			
			Cell fenxianChengduCell = dataRow.createCell(17);
			fenxianChengduCell.setCellStyle(style);
			fenxianChengduCell.setCellValue(resultSet.getString("fenxianChengdu"));
			Cell feeAllRaioCell = dataRow.createCell(18);
			feeAllRaioCell.setCellStyle(style);
			feeAllRaioCell.setCellValue(resultSet.getString("feeAllRaio"));

			Cell feeLoanRateCell = dataRow.createCell(19);
			feeLoanRateCell.setCellStyle(style);
			feeLoanRateCell.setCellValue(resultSet.getString("feeLoanRate"));
			
			Cell contractFactDayCell = dataRow.createCell(20);
			contractFactDayCell.setCellStyle(style);
			contractFactDayCell.setCellValue(resultSet.getString("contractFactDay"));
			
			Cell contractReplayDayCell = dataRow.createCell(21);
			contractReplayDayCell.setCellStyle(style);
			contractReplayDayCell.setCellValue(resultSet.getString("fenxianChengdu"));
			
			Cell feeExpeditedCell = dataRow.createCell(22);
			feeExpeditedCell.setCellStyle(style);
			String feeExpedited = resultSet.getString("feeExpedited");
			if(!ObjectHelper.isEmpty(contractMonthRepayTotal)){
				feeExpedited = getDecimalFormat(feeExpedited);
			}	
			feeExpeditedCell.setCellValue(feeExpedited);
			
			Cell loanFlagCell = dataRow.createCell(23);
			loanFlagCell.setCellStyle(style);
			loanFlagCell.setCellValue(resultSet.getString("loanFlag"));
			
			Cell contractVersionCell = dataRow.createCell(24);
			contractVersionCell.setCellStyle(style);
			contractVersionCell.setCellValue(resultSet.getString("contractVersion"));
			
			
			Cell  loanUrgentFlagCell = dataRow.createCell(25);
			loanUrgentFlagCell.setCellStyle(style);
			String loanUrgentFlag = resultSet.getString("loanUrgentFlag");
			if("1".equals(loanUrgentFlag)){
				loanUrgentFlag = "是";
			}else{
				loanUrgentFlag = "否";

			}
			loanUrgentFlagCell.setCellValue(loanUrgentFlag);
			
			
		    String bestCoborrowerFlag = "";
			if(ObjectHelper.isEmpty(bestCoborrower)){
				 bestCoborrowerFlag = "否";
			 }else{
				 bestCoborrowerFlag = "是";

			 }
			 Cell bestCoborrowerFlagCell = dataRow.createCell(26);
			 bestCoborrowerFlagCell.setCellStyle(style);
			 bestCoborrowerFlagCell.setCellValue(bestCoborrowerFlag);
			
			 Cell customerTelesalesFlagCell = dataRow.createCell(27);
			 customerTelesalesFlagCell.setCellStyle(style);
				
			 String customerTelesalesFlag = resultSet.getString("customerTelesalesFlag");
			 if("1".equals(customerTelesalesFlag))
			 {
			 	customerTelesalesFlag = "是";
			 }else{
				customerTelesalesFlag = "否";
	
			 }
			customerTelesalesFlagCell.setCellValue(customerTelesalesFlag);
		
			Cell issplitCell = dataRow.createCell(28);
			issplitCell.setCellStyle(style);
			String issplit = resultSet.getString("issplit");
			if("1".equals(issplit)){
				issplit = "是";
			}else{
				issplit = "否";
			}
			issplitCell.setCellValue(issplit);
			
			Cell bankSigningPlatformCell = dataRow.createCell(29);
			bankSigningPlatformCell.setCellStyle(style);
			
			bankSigningPlatformCell.setCellValue(
					DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT,resultSet.getString("bankSigningPlatform"))
					);
			
			Cell introductionTimeCell = dataRow.createCell(30);
			introductionTimeCell.setCellStyle(style);
			introductionTimeCell.setCellValue(resultSet.getString("introductionTime"));
			
			Cell lendingTimeCell = dataRow.createCell(31);
			lendingTimeCell.setCellStyle(style);
			lendingTimeCell.setCellValue(resultSet.getString("lendingTime"));
			
			Cell dictLoanStatusCell = dataRow.createCell(32);
			dictLoanStatusCell.setCellStyle(style);
			dictLoanStatusCell.setCellValue(resultSet.getString("dictLoanStatus"));
			
			row = row + 1;
			i++;
		}
		dataRow = dataSheet.createRow(row+1);
		long accountNum = (Long)totalDeducts.get("account");
		BigDecimal audictAmontNum = (BigDecimal) totalDeducts.get("ceilingsum");//批借总金额
		BigDecimal contractAmountNum = (BigDecimal) totalDeducts.get("contractamount");//  合同金额
		BigDecimal grantNum =  (BigDecimal)totalDeducts.get("grantamount"); // 实放金额
		String audictAmontStr  = "";
		String contractAmountStr = "";
		String grantNumStr = "";
		
		if(audictAmontNum == null ){
			audictAmontStr = "0";
		}else{
			audictAmontStr = getDecimalFormat(audictAmontNum.toString());
		}
		
		if(contractAmountNum == null ){
			contractAmountStr = "0";
		}else{
			contractAmountStr = getDecimalFormat(contractAmountNum.toString());
		}
		if(grantNum == null ){
			grantNumStr = "0";
		}else{
			grantNumStr = getDecimalFormat(grantNum.toString());
		}
		
		Cell accountNumCell = dataRow.createCell(1);
		accountNumCell.setCellStyle(style);
		accountNumCell.setCellValue(accountNum);
		
		Cell audictAmontNumCell = dataRow.createCell(6);
		audictAmontNumCell.setCellStyle(style);
		audictAmontNumCell.setCellValue(audictAmontStr);
		
		Cell contractAmountNumCell = dataRow.createCell(7);
		contractAmountNumCell.setCellStyle(style);
		contractAmountNumCell.setCellValue(contractAmountStr);
		
		Cell grantNumCell = dataRow.createCell(8);
		grantNumCell.setCellStyle(style);
		grantNumCell.setCellValue(grantNumStr);



		setAutoColumn(MAXCOLUMN, dataSheet);
	}

	private  void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
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
        String[] header = {"序号","合同编号","借款人姓名","身份证号","自然人保证人","法人保证人","批借金额","合同金额",
	            "实放金额","外访费","前期综合服务费","催收服务费","分期服务费","月还金额","产品种类","期数","风险等级",
	             "风险程度","总费率（%）","月利率（%）","合同日期","首期还款日","加急费","渠道","合同版本号","是否加急",
	             "是否有保证人","是否电销","是否联合","划扣平台","推送日期","放款日期","借款状态"};
        
		Row headerRow = dataSheet.createRow(1);
		for(int i = 0; i< header.length; i++){
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
	private  void setAutoColumn (int maxColumn,Sheet sheet) {
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
	    * 
	    * @Description:(转化为 金额类的用数值格式，保留两位小数，三位一分隔  123,456,789.23)  
	    * @date 2017年6月7日下午5:37:03 
	    * @author wengsi
	    * @param @param b
	    * @param @return    参数  
	    * @return String    返回类型
	 */
	public  String getDecimalFormat(String b){
		NumberFormat nf = new DecimalFormat(",###.##");
		  double money = Double.valueOf(b);
		  String testStr = nf.format(money);
		  return testStr;
	} 
}
