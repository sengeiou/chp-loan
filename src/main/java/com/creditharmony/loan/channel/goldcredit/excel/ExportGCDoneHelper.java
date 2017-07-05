package com.creditharmony.loan.channel.goldcredit.excel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 导出回息数据帮助类
 * @Class Name ExportGCBusinessHelper
 * @author 张建雄
 * @Create In 2016年4月25日
 */
@SuppressWarnings("deprecation")
public class ExportGCDoneHelper {
	private static Logger logger = LoggerFactory.getLogger(ExportGCDoneHelper.class);
	
	public static void exportData(Map<String, Object> queryMap,
			HttpServletResponse response,UserManager userManager) {
		final int MAXCOLUMN = 24;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			String fileName = FileExtension.JINXIN_YFK_LOAN + System.currentTimeMillis() ;
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");
			wrapperHeader(wb,dataSheet,fileName,MAXCOLUMN);
			MyBatisSql batisSql = MyBatisSqlUtil
					.getMyBatisSql(
							"com.creditharmony.loan.borrow.grant.dao.LoanGrantDao.findGrantDoneList",
							queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(wb,resultSet, dataSheet,MAXCOLUMN,userManager);
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
        String[] header = {"序号","客户姓名","客户经理","客户经理编号","团队经理","团队经理编号","合同编号","合同金额","放款金额","批借金额",
        		"信访费","产品类型","催收服务费","未划金额","期数","放款账户","开户行","账号","机构","放款时间","放款批次","操作人员","渠道","是否加急"};
		Row headerRow = dataSheet.createRow(1);
		for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
	}
	private static void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN,UserManager userManager)
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
		BigDecimal bigGrantAmount = new BigDecimal(0);
		int j=0;
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			Cell lendCodeCell = dataRow.createCell(0);
			lendCodeCell.setCellStyle(style);
			lendCodeCell.setCellValue((++j)+"");
			Cell accountNoCell = dataRow.createCell(1);
			accountNoCell.setCellStyle(style);
			accountNoCell.setCellValue(resultSet.getString("customerName"));
			Cell ManagerNameCell = dataRow.createCell(2);
			ManagerNameCell.setCellStyle(style);
			ManagerNameCell.setCellValue(resultSet.getString("MANAGERNAME"));
			Cell loanManagerCodeCell = dataRow.createCell(3);
			loanManagerCodeCell.setCellStyle(style);
			loanManagerCodeCell.setCellValue(resultSet.getString("loanManagerCode"));
			Cell backMoneyCell = dataRow.createCell(4);
			backMoneyCell.setCellStyle(style);
			backMoneyCell.setCellValue(resultSet.getString("TEAMNAME"));
			Cell loanTeamManagerCodeCell = dataRow.createCell(5);
			loanTeamManagerCodeCell.setCellStyle(style);
			loanTeamManagerCodeCell.setCellValue(resultSet.getString("loanTeamManagerCode"));
			Cell accountBranchCell = dataRow.createCell(6);
			accountBranchCell.setCellStyle(style);
			accountBranchCell.setCellValue(resultSet.getString("contractCode"));
			Cell accountCardOrBookletCell = dataRow.createCell(7);
			accountCardOrBookletCell.setCellStyle(style);
			accountCardOrBookletCell.setCellValue(resultSet.getString("contractAmount"));
			Cell provinceCell = dataRow.createCell(8);
			provinceCell.setCellStyle(style);
			provinceCell.setCellValue(resultSet.getString("grantAmount"));
			Cell cityCell = dataRow.createCell(9);
			cityCell.setCellStyle(style);
			cityCell.setCellValue(resultSet.getString("auditAmount"));
			Cell productNameCell = dataRow.createCell(10);
			productNameCell.setCellStyle(style);
			productNameCell.setCellValue(resultSet.getString("feePetition"));
			Cell applyLendDayCell = dataRow.createCell(11);
			applyLendDayCell.setCellStyle(style);
			applyLendDayCell.setCellValue(resultSet.getString("productType"));
			Cell applyLendMoneyCell = dataRow.createCell(12);
			applyLendMoneyCell.setCellStyle(style);
			applyLendMoneyCell.setCellValue(resultSet.getString("urgeMoney"));
			Cell applyPayCell = dataRow.createCell(13);
			applyPayCell.setCellStyle(style);
			applyPayCell.setCellValue(resultSet.getString("urgeDecuteMoeny"));
			Cell lendingTimeCell = dataRow.createCell(14);
			lendingTimeCell.setCellStyle(style);
			lendingTimeCell.setCellValue(resultSet.getString("contractMonths"));
			Cell CONTRACT_END_DAY = dataRow.createCell(15);
			CONTRACT_END_DAY.setCellStyle(style);
			CONTRACT_END_DAY.setCellValue(resultSet.getString("middleName"));
			Cell MANAGERNAME = dataRow.createCell(16);
			MANAGERNAME.setCellStyle(style);
			MANAGERNAME.setCellValue(resultSet.getString("midBankName"));
			Cell TEAMMANAGERNAME = dataRow.createCell(17);
			TEAMMANAGERNAME.setCellStyle(style);
			TEAMMANAGERNAME.setCellValue(resultSet.getString("bankCardNo"));
			Cell SERVICEUSERNAME = dataRow.createCell(18);
			SERVICEUSERNAME.setCellStyle(style);
			SERVICEUSERNAME.setCellValue(resultSet.getString("storesCode"));
			Cell SURVEYEMPID = dataRow.createCell(19);
			SURVEYEMPID.setCellStyle(style);
			SURVEYEMPID.setCellValue(formatDate(resultSet.getString("lendingTime")));
			Cell jx = dataRow.createCell(20);
			jx.setCellStyle(style);
			jx.setCellValue(resultSet.getString("submissionBatch"));
			Cell contractAuditDate = dataRow.createCell(21);
			contractAuditDate.setCellStyle(style);
			contractAuditDate.setCellValue(resultSet.getString("CHECKNAME"));//操作人员
			Cell CUSTOMER_TELESALES_FLAG = dataRow.createCell(22);
			CUSTOMER_TELESALES_FLAG.setCellStyle(style);
			CUSTOMER_TELESALES_FLAG.setCellValue(resultSet.getString("CHANNEL_NAME"));//标识
			Cell loan_urgent_flag = dataRow.createCell(23);
			loan_urgent_flag.setCellStyle(style);
			loan_urgent_flag.setCellValue(resultSet.getString("LABEL"));//是否加急
			
			bigGrantAmount = bigGrantAmount.add(BigDecimal.valueOf(Double.parseDouble(resultSet.getString("grantAmount"))));
			row = row + 1;
		}
		//添加页尾
		//获取总行数
		int rowNum = dataSheet.getLastRowNum()+1;
		Row footerRow = dataSheet.createRow(rowNum);
		for (int i = 0; i <= MAXCOLUMN; i++) {
			Cell footerCell = footerRow.createCell(i);
			footerCell.setCellStyle(style);
			if (i == 9) {
				footerCell.setCellValue("放款总金额:" + format(bigGrantAmount+"", "#,##0.00"));
			}
		}
		setAutoColumn(MAXCOLUMN, dataSheet);
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
	 * 获取制定的用户信息
	 * @param uerId 要获取的的用户的id
	 * @param userManager 缓存用户实体
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getName(String uerId,UserManager userManager) {
		String userName = "";
		if (StringUtils.isNotEmpty(uerId)) {
			User comUser = userManager.get(uerId);
			userName  = !ObjectHelper.isEmpty(comUser) ? comUser.getName() : "";
		} 
		return userName;
	}
	/**
	 * 格式化数据信息到指定的格式(只支持金额数据信息的格式化) ,数据信息为空的话直接返回空字符串
	 * @param data 待格式化的数据信息
	 * @param pattern 格式
	 * @return 格式化后的数据信息
	 */
	private static String format (String data,String pattern) {
		String formatData = "";
		NumberFormat nf = new DecimalFormat(pattern);
		if (StringUtils.isNotEmpty(data)) 
			formatData = nf.format(Double.parseDouble(data));
		return formatData;
	}
	/**
	 * 日期为null时默认转换为空字符串，否则sql报错
	 * @return
	 */
	private static String formatDate(String time) {
		if(time!=null || !"".equals(time)){
			return time;
		}else{
			return " ";
		}
	}
}
