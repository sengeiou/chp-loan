/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.account.servicekingExportHelper.java
 * @Create By 张灏
 * @Create In 2016年6月11日 下午4:26:52
 */
package com.creditharmony.loan.borrow.account.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.loan.borrow.account.dao.RepayAccountDao;
import com.creditharmony.loan.borrow.account.entity.KingAccountChangeExport;
import com.creditharmony.loan.common.consts.FileExtension;

/**
 * 金账户变更信息下载
 * @Class Name kingExportHelper
 * @author 张灏
 * @Create In 2016年6月11日
 */
@Service
public class kingExportHelper {
    @Autowired
    RepayAccountDao  repayAccountDao;
    
    @Autowired
    RepayAccountService repayAccountService;
    /**
     * 下载当前显示的金账户信息
     * by zhanghao
     * 2016年06月11日
     * @param param
     * @return Map<String,Object>
     * */
    public  Map<String,InputStream> excelCreate(List<KingAccountChangeExport> srcList){
           Map<String,InputStream> result = new HashMap<String,InputStream>();
           InputStream input = null;
           String updateType = null;
           for(KingAccountChangeExport s:srcList){
               input = this.excelInput(s);
               updateType = s.getUpdateType()==null?"":s.getUpdateType();
               result.put(s.getBankAccountName()+"_"+updateType+FileExtension.XLSX, input);
           }
           return result;
    }
    
    private InputStream excelInput(KingAccountChangeExport target){
        String[] header={"","所在省","所在城市","金账户账号","接受验证手机号码","开户行支行名称","户名","卡号","证件类型","证件号码"};
         SXSSFWorkbook wb = new SXSSFWorkbook();

         Sheet dataSheet = wb.createSheet("ExportList");
         Row headerRow = dataSheet.createRow(0);
         //创建样式
         CellStyle style = wb.createCellStyle(); 
         //创建字体
         Font font = wb.createFont();
         style = wb.createCellStyle();
         style.setBorderBottom((short) 1);   
         style.setBorderTop((short) 1);
         style.setBorderLeft((short) 1);
         style.setBorderRight((short) 1);
         style.setFillPattern(CellStyle.FINE_DOTS);
         style.setFillBackgroundColor(new HSSFColor.GREY_80_PERCENT().getIndex());
         font = wb.createFont();
         font.setBoldweight(Font.BOLDWEIGHT_BOLD);
         font.setColor(new HSSFColor.WHITE().getIndex());
         font.setFontHeightInPoints((short)10);
         style.setAlignment(CellStyle.ALIGN_CENTER);
         style.setFont(font);
         for (int i = 0; i < header.length; i++) {
             Cell headerCell = headerRow.createCell(i);
             headerCell.setCellStyle(style);
             headerCell.setCellValue(header[i]);
         }

         String updateType = target.getUpdateType();
         if("0".equals(updateType)){
             this.phoneChanged(wb, dataSheet, target);
         }else{
             KingAccountChangeExport beforeKingAccount = null;
             if("1".equals(target.getDictMaintainType())){
                 String ids = target.getOldBankAccountId();
                 Map<String,Object> map = new HashMap<String,Object>();
                 map.put("ids", "'"+ids+"'");
                 beforeKingAccount = repayAccountDao.getTGdownload(map).get(0);
                 String mobile = repayAccountService.mobileDisDecrypt(beforeKingAccount.getLoanCode());
                 beforeKingAccount.setOldCustomerPhone(mobile);
             }else{
                 beforeKingAccount = new KingAccountChangeExport();
             }   
             Row curRow = null;
             curRow = dataSheet.createRow(1);
             kindAccountChanged(wb, dataSheet, curRow, "0", beforeKingAccount);  
             curRow = dataSheet.createRow(2);
             kindAccountChanged(wb, dataSheet, curRow, "1", target); 
         }
         setAutoColumn(header.length, dataSheet);
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
         InputStream input = new ByteArrayInputStream(out.toByteArray());
         
         return input;
    }
    
    // 保存手机变更
    private void phoneChanged(SXSSFWorkbook wb,Sheet dataSheet,KingAccountChangeExport target){
          CellStyle style = wb.createCellStyle();
          style.setAlignment(CellStyle.ALIGN_CENTER);
          DataFormat text = wb.createDataFormat();  
          style.setDataFormat(text.getFormat("@"));
          CellStyle background = wb.createCellStyle();
          background.setDataFormat(text.getFormat("@"));
          background.setAlignment(CellStyle.ALIGN_CENTER);
          background.setFillPattern(CellStyle.SOLID_FOREGROUND);
          background.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
          Row changeBefore = dataSheet.createRow(1);
          Row changeAfter = dataSheet.createRow(2);
          Cell tcell1 = changeBefore.createCell(0);  
          Cell tcell2 = changeAfter.createCell(0);
          tcell1.setCellValue("变更前");
          tcell1.setCellStyle(style);
          tcell2.setCellValue("变更后");
          tcell2.setCellStyle(style);
          // 设置所在省
          tcell1 = changeBefore.createCell(1);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(1);
          tcell2.setCellStyle(style);
          tcell1.setCellValue(target.getKindProvinceName()==null?"":target.getKindProvinceName());
          tcell2.setCellValue(target.getKindProvinceName()==null?"":target.getKindProvinceName());
          
          // 设置所在城市
          tcell1 = changeBefore.createCell(2);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(2);
          tcell2.setCellStyle(style);
          tcell1.setCellValue(target.getKindCityName()==null?"":target.getKindCityName());
          tcell2.setCellValue(target.getKindCityName()==null?"":target.getKindCityName());
          
          // 设置金账户账号
          tcell1 = changeBefore.createCell(3);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(3);
          tcell2.setCellStyle(style);
          tcell1.setCellValue(target.getTrusteeshipNo()==null?"":target.getTrusteeshipNo());
          tcell2.setCellValue(target.getTrusteeshipNo()==null?"":target.getTrusteeshipNo());
          // 设置接收验证手机号码
          tcell1 = changeBefore.createCell(4);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(4);
          tcell2.setCellStyle(background);
          tcell1.setCellValue(target.getOldCustomerPhone()==null?"":target.getOldCustomerPhone());
          tcell2.setCellValue(target.getNewCustomerPhone()==null?"":target.getNewCustomerPhone());
          
          // 设置开户行支行名称
          tcell1 = changeBefore.createCell(5);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(5);
          tcell2.setCellStyle(style);
          String bankName = DictCache.getInstance().getDictLabel("jk_open_bank", target.getBankName());
          tcell1.setCellValue(bankName+(target.getBankBranch()==null?"":target.getBankBranch()));
          tcell2.setCellValue(bankName+(target.getBankBranch()==null?"":target.getBankBranch()));
          
          // 户名
          tcell1 = changeBefore.createCell(6);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(6);
          tcell2.setCellStyle(style);
          tcell1.setCellValue(target.getBankAccountName()==null?"":target.getBankAccountName());
          tcell2.setCellValue(target.getBankAccountName()==null?"":target.getBankAccountName());
          
          // 卡号
          tcell1 = changeBefore.createCell(7);
          tcell1.setCellStyle(style);
          tcell2 = changeAfter.createCell(7);
          tcell2.setCellStyle(style);
          tcell1.setCellValue(target.getBankAccount()==null?"":target.getBankAccount());
          tcell2.setCellValue(target.getBankAccount()==null?"":target.getBankAccount());
          
          // 证件类型
           String dictCertType = target.getDictCertType();
           CertificateType cetType = null;
           if(StringUtils.isNotEmpty(dictCertType)){
               cetType = CertificateType.parseByCode(dictCertType);
               tcell1 = changeBefore.createCell(8);
               tcell1.setCellStyle(style);
               tcell2 = changeAfter.createCell(8);
               tcell2.setCellStyle(style);
               tcell1.setCellValue(cetType.getName()==null?"":cetType.getName());
               tcell2.setCellValue(cetType.getName()==null?"":cetType.getName());
           }
           
           // 证件号码
           tcell1 = changeBefore.createCell(9);
           tcell1.setCellStyle(style);
           tcell2 = changeAfter.createCell(9);
           tcell2.setCellStyle(style);
           tcell1.setCellValue(target.getCustomerCertNum()==null?"":target.getCustomerCertNum());
           tcell2.setCellValue(target.getCustomerCertNum()==null?"":target.getCustomerCertNum());
    }
    private void kindAccountChanged(SXSSFWorkbook wb,Sheet dataSheet,Row curRow,String type,KingAccountChangeExport target){
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        DataFormat text = wb.createDataFormat();  
        style.setDataFormat(text.getFormat("@"));
        CellStyle background = wb.createCellStyle();
        background.setDataFormat(text.getFormat("@"));
        background.setFillPattern(CellStyle.SOLID_FOREGROUND);
        background.setAlignment(CellStyle.ALIGN_CENTER);
        background.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
        Cell tcell1 = curRow.createCell(0); 
        if("0".equals(type)){
            tcell1.setCellValue("变更前");
            tcell1.setCellStyle(style);
        }else{
            tcell1.setCellValue("变更后");
            tcell1.setCellStyle(style); 
        }
        // 设置所在省
        tcell1 = curRow.createCell(1);
        tcell1.setCellStyle(style);
        tcell1.setCellValue(target.getKindProvinceName()==null?"":target.getKindProvinceName());
        
        // 设置所在城市
        tcell1 = curRow.createCell(2);
        tcell1.setCellStyle(style);
        tcell1.setCellValue(target.getKindCityName()==null?"":target.getKindCityName());
         
        // 设置金账户账号
        tcell1 = curRow.createCell(3);
        tcell1.setCellStyle(style);
        tcell1.setCellValue(target.getTrusteeshipNo()==null?"":target.getTrusteeshipNo());
        // 设置接收验证手机号码
        tcell1 = curRow.createCell(4);
        tcell1.setCellStyle(style);
        tcell1.setCellValue(target.getOldCustomerPhone()==null?"":target.getOldCustomerPhone());
         
        // 设置开户行支行名称
        tcell1 = curRow.createCell(5);
        if("1".equals(type)){
            tcell1.setCellStyle(background);
        }else{
            tcell1.setCellStyle(style);    
        }
        String bankName = DictCache.getInstance().getDictLabel("jk_open_bank", target.getBankName());
        tcell1.setCellValue(bankName+(target.getBankBranch()==null?"":target.getBankBranch()));
          
        // 户名
        tcell1 = curRow.createCell(6);
        tcell1.setCellStyle(style);
        tcell1.setCellValue(target.getBankAccountName()==null?"":target.getBankAccountName());
        // 卡号
        tcell1 = curRow.createCell(7);
        if("1".equals(type)){
            tcell1.setCellStyle(background);
        }else{
            tcell1.setCellStyle(style);    
        }
        tcell1.setCellValue(target.getBankAccount()==null?"":target.getBankAccount());
       
        // 证件类型
         String dictCertType = target.getDictCertType();
         CertificateType cetType = null;
         if(StringUtils.isNotEmpty(dictCertType)){
             cetType = CertificateType.parseByCode(dictCertType);
             tcell1 = curRow.createCell(8);
             tcell1.setCellStyle(style);
             tcell1.setCellValue(cetType.getName());
          }
         
         // 证件号码
         tcell1 = curRow.createCell(9);
         tcell1.setCellStyle(style);
         tcell1.setCellValue(target.getCustomerCertNum()==null?"":target.getCustomerCertNum());
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
