/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.utilExportPaybackAuditHelper.java
 * @Create By 张灏
 * @Create In 2016年5月28日 下午6:56:39
 */
package com.creditharmony.loan.borrow.payback.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackAuditEx;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * @Class Name ExportPaybackAuditHelper
 * @author 张灏
 * @Create In 2016年5月28日
 */
public class ExportPaybackAuditHelper {

     /**
      * 导出还款查账已办数据表
      * 
      * 
      */
    public static void exportPayback(PaybackAuditEx paybackAuditEx,String[] header,String fileName,HttpServletResponse response){
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
                .getBean("sqlSessionFactory");
        Integer MAXCOLUMN = 17;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection connection = sqlSession.getConnection();
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet dataSheet = wb.createSheet("ExportList");
        wrapperHeader(wb,dataSheet,fileName,header);
        MyBatisSql batisSql;
        try {
            batisSql = MyBatisSqlUtil
                    .getMyBatisSql(
                            "com.creditharmony.loan.borrow.payback.dao.PaybackAuditDao.allPaybackAuditHavaTodoList",
                            paybackAuditEx, sqlSessionFactory);
       
        PreparedStatement ps = connection.prepareStatement(batisSql
                .toString());
        ResultSet resultSet = ps.executeQuery();
        paybackSheetExcelCell(wb,resultSet, dataSheet,MAXCOLUMN);
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
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (IOException e) {
             e.printStackTrace();
        }
    }
    
    // 导出标题设置
    private static void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,String[] header) {
        Row titleRow = dataSheet.createRow(0);
        titleRow.setHeight((short) (15.625*40)); 
        Cell titleCell = titleRow.createCell(0);
        dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,header.length-1));
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
        
        Row headerRow = dataSheet.createRow(1); //为标题创建一行
        for (int i = 0; i < header.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellStyle(style);
            headerCell.setCellValue(header[i]);
        }
     }
    private static void paybackSheetExcelCell(SXSSFWorkbook wb,ResultSet resultSet, Sheet dataSheet,int MAXCOLUMN) throws NumberFormatException, SQLException{
        int row = 2;
        CellStyle styleText = wb.createCellStyle();
            styleText.setBorderBottom((short) 1);   
            styleText.setBorderTop((short) 1);
            styleText.setBorderLeft((short) 1);
            styleText.setBorderRight((short) 1);
            styleText.setAlignment(CellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short)10);
            styleText.setFont(font);
            DataFormat formatText = wb.createDataFormat();  
            styleText.setDataFormat(formatText.getFormat("@"));
        CellStyle styleNumeric = wb.createCellStyle();
            styleNumeric.setBorderBottom((short) 1);   
            styleNumeric.setBorderTop((short) 1);
            styleNumeric.setBorderLeft((short) 1);
            styleNumeric.setBorderRight((short) 1);
            styleNumeric.setAlignment(CellStyle.ALIGN_CENTER);
            font.setFontHeightInPoints((short)10);
            styleNumeric.setFont(font);
            DataFormat formatNumeric = wb.createDataFormat();  
            styleNumeric.setDataFormat(formatNumeric.getFormat("#,##0.00_);(#,##0.00)"));
        Row dataRow; 
        DictCache dictCache = DictCache.getInstance();
        while (resultSet.next()) {
            dataRow = dataSheet.createRow(row);
            Cell index = dataRow.createCell(0);
            index.setCellType(Cell.CELL_TYPE_STRING);
            index.setCellStyle(styleText);
            index.setCellValue(row-1);
            Cell contractCode = dataRow.createCell(1);
            contractCode.setCellType(Cell.CELL_TYPE_STRING);
            contractCode.setCellStyle(styleText);
            contractCode.setCellValue(resultSet.getString("contractCode"));
            Cell customerName = dataRow.createCell(2);
            customerName.setCellType(Cell.CELL_TYPE_STRING);
            customerName.setCellStyle(styleText);
            customerName.setCellValue(resultSet.getString("customerName"));
            Cell orgName = dataRow.createCell(3);
            //orgName.setCellType(Cell.CELL_TYPE_NUMERIC);
            orgName.setCellStyle(styleText);
            orgName.setCellValue(resultSet.getString("orgName"));//门店名称
            Cell contractMonths = dataRow.createCell(4);
            //contractMonths.setCellType(Cell.CELL_TYPE_NUMERIC);
            contractMonths.setCellStyle(styleText);
            contractMonths.setCellValue(resultSet.getString("contractMonths"));//批借期数
            Cell contractReplayDay = dataRow.createCell(5);
            //contractReplayDay.setCellType(Cell.CELL_TYPE_NUMERIC);
            contractReplayDay.setCellStyle(styleText);
            contractReplayDay.setCellValue(resultSet.getString("contractReplayDay"));//首期还款日
            Cell dictPayStatus = dataRow.createCell(6);
            //dictPayStatus.setCellType(Cell.CELL_TYPE_NUMERIC);
            dictPayStatus.setCellStyle(styleText);
            dictPayStatus.setCellValue(dictCache.getDictLabel("jk_repay_apply_status",resultSet.getString("dictPayStatus")));//还款申请状态
            Cell storesInAccountname = dataRow.createCell(7);
            //storesInAccountname.setCellType(Cell.CELL_TYPE_NUMERIC);
            storesInAccountname.setCellStyle(styleText);
            storesInAccountname.setCellValue(resultSet.getString("storesInAccountname"));// 存入银行名称
            Cell dictRepayMethod = dataRow.createCell(8);
            //dictRepayMethod.setCellType(Cell.CELL_TYPE_NUMERIC);
            dictRepayMethod.setCellStyle(styleText);
            dictRepayMethod.setCellValue(dictCache.getDictLabel("jk_repay_way",resultSet.getString("dictRepayMethod")));// 还款方式
            Cell applyMoneyPayback = dataRow.createCell(9);
            //applyMoneyPayback.setCellType(Cell.CELL_TYPE_NUMERIC);
            applyMoneyPayback.setCellStyle(styleNumeric);
            applyMoneyPayback.setCellValue(Double.valueOf(resultSet.getString("applyMoneyPayback")));// 申请还款金额
            Cell reallyAmount = dataRow.createCell(10);
            //reallyAmount.setCellType(Cell.CELL_TYPE_NUMERIC);
            reallyAmount.setCellStyle(styleNumeric);
            reallyAmount.setCellValue(Double.valueOf(resultSet.getString("reallyAmount")));//实际到账金额
            Cell dictPayUse = dataRow.createCell(11);
            //dictPayUse.setCellType(Cell.CELL_TYPE_NUMERIC);
            dictPayUse.setCellStyle(styleText);
            dictPayUse.setCellValue(dictCache.getDictLabel("jk_repay_type",resultSet.getString("dictPayUse")));//还款类型
            Cell modifyTime = dataRow.createCell(12);
            //modifyTime.setCellType(Cell.CELL_TYPE_NUMERIC);
            modifyTime.setCellStyle(styleText);
            modifyTime.setCellValue(resultSet.getString("modifyTime"));//查账日期
            Cell createTime = dataRow.createCell(13);
            createTime.setCellType(Cell.CELL_TYPE_STRING);
            createTime.setCellStyle(styleText);
            createTime.setCellValue(resultSet.getString("createTime")); //还款日
            Cell dictLoanStatus = dataRow.createCell(14);
            dictLoanStatus.setCellType(Cell.CELL_TYPE_STRING);
            dictLoanStatus.setCellStyle(styleText);
            dictLoanStatus.setCellValue(dictCache.getDictLabel("jk_loan_status",resultSet.getString("dictLoanStatus")));
            Cell dictPayResult = dataRow.createCell(15);
            dictPayResult.setCellType(Cell.CELL_TYPE_STRING);
            dictPayResult.setCellStyle(styleText);
            dictPayResult.setCellValue(dictCache.getDictLabel("jk_counteroffer_result",resultSet.getString("dictPayResult")));//回盘结果
            Cell paybackBuleAmount = dataRow.createCell(16);
            paybackBuleAmount.setCellType(Cell.CELL_TYPE_NUMERIC);
            paybackBuleAmount.setCellStyle(styleNumeric);
            paybackBuleAmount.setCellValue(Double.valueOf(resultSet.getString("paybackBuleAmount")));//蓝补金额
            row = row + 1;
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
