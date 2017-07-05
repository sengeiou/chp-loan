package com.creditharmony.loan.channel.finance.excel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.loan.type.LoanType;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.CommonDateUtils;
import com.creditharmony.loan.common.utils.DateUtil;
import com.sun.star.lang.NullPointerException;

/**
 * Excel工具类
 * @author 张建雄
 *
 */
@SuppressWarnings("deprecation")
public class Excelutils {
	private static Logger logger = LoggerFactory.getLogger(Excelutils.class);
	/**
	 * 导出Excel
	 * @param queryMap 封装的要查询的查询条件,
	 * @param response 响应
	 * @param sqlPath 获取mybatis配置下的sql的命名空间+id
	 * @param fileName 导出Excel的文件名
	 * @param header 导出的文件头信息
	 * @param body 文件体内容,和sql中的字段名称相对应
	 */
	public static void exportExcel(Map<String, Object> queryMap,
			HttpServletResponse response,String sqlPath,String fileName,String[]header,String[]body) {
		//列数
		final int MAXCOLUMN = header.length;
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet(fileName);
			wrapperHeader(wb,dataSheet,fileName,header);
			MyBatisSql batisSql = MyBatisSqlUtil.getMyBatisSql(sqlPath,queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql.toString());
			ResultSet resultSet = ps.executeQuery();
			assembleExcelCell(wb,resultSet, dataSheet,MAXCOLUMN,body);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition","attachment; filename="
							+ Encodes.urlEncode(fileName+ FileExtension.XLSX)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+ FileExtension.XLSX));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exportData()导出数据出现异常:"+e.toString());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("关闭数据库连接出现错误:"+e.toString());
			}
		}
	}
	private static void assembleExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN,String[]body)
			throws SQLException {
		//获取最后一行
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
		
		while (resultSet.next()) {
			Row dataRow = dataSheet.createRow(row);
			for (int i = 0; i < body.length; i++) {
				Cell bodyCell = dataRow.createCell(i);
				bodyCell.setCellStyle(style);
				String bodyStr = body[i];
				
				boolean flag = bodyStr.equals("product_type");//表示借款类型
				if(flag){
					String name = LoanType.HONOUR_LOAN.getName();
					bodyCell.setCellValue(name);
				}else{
					if("contract_end_day".equals(bodyStr))
					{
						Date d = CommonDateUtils.monthsAndDayLater(
								resultSet.getDate("contract_fact_day"),
								resultSet.getInt("contract_months"), -1);
						bodyCell.setCellValue(DateUtil.DateToString(d, "yyyy-MM-dd"));
					}
					else
					{
						bodyCell.setCellValue(resultSet.getString(bodyStr));
					}
				}
			}
			row = row + 1;
		}
		autoColumnSize(MAXCOLUMN, dataSheet);
	}

	private static void wrapperHeader(SXSSFWorkbook wb,Sheet sheet,String fileName,String[]header) throws NullPointerException {
		if (header.length == 0)
			throw new NullPointerException("表头信息不能够为空!");
		Row titleRow = sheet.createRow(0);
		titleRow.setHeight((short) (15.625*40)); 
		Cell titleCell = titleRow.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,header.length-1));
		//创建样式
		CellStyle style = wb.createCellStyle(); 
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平
		
        //创建字体
        Font font = wb.createFont();
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
        
		Row headerRow = sheet.createRow(1);
		for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
	}
	/**
	 * 设置Excel表格的自适应功能(由于poi中的自动适应除英文、数字外的其他字符不支持自动适应功能)
	 * @param maxColumn 列数
	 * @param sheet Sheet表格
	 */
	private static void autoColumnSize(int maxColumn,Sheet sheet) {
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
	private static String format(String data,String pattern) {
		String formatData = "";
		NumberFormat nf = new DecimalFormat(pattern);
		if (StringUtils.isNotEmpty(data)) 
			formatData = nf.format(Double.parseDouble(data));
		return formatData;
	}
}
