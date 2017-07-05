package com.creditharmony.loan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;

public class ExportHelper {

	/**
	 * 富有组装数据 2016年5月3日 by 翁私
	 * @param row
	 * @param map
	 * @return none
	 */
	public void assignDeductPayback(Row row,Map<String, List<ImportEntity>> map) {
		if(row != null){
			ImportEntity entity = new ImportEntity();	
			String qiyeno  = row.getCell(11)!=null ? row.getCell(11).toString() : "";
		if(qiyeno != null && !"".equals(qiyeno)){
			Object amount =row.getCell(5) == null ? "0" : row.getCell(5);
			entity.setSplitAmount(BigDecimal.valueOf(Double.valueOf(amount.toString().replaceAll(",", ""))));
			entity.setTradingStatus(row.getCell(9)!=null ? row.getCell(9).toString() : "");
			entity.setId(qiyeno); //企业流水号
			String pch = qiyeno.split("_")[2];
			String splitPch = pch.substring(0,pch.length()-2);
			entity.setFailReason(row.getCell(10)!=null ? row.getCell(10).toString() : "");
			if (!map.containsKey(splitPch)) {
				List<ImportEntity> list = new ArrayList<ImportEntity>();
				list.add(entity);
				map.put(splitPch, list);
			 } else {
				map.get(splitPch).add(entity);
			 }
		   }
		}
	}
	
	/**
	 * 好易联组装数据 2016年5月3日 by 翁私
	 * @param row
	 * @param map
	 * @return none
	 */
	public void assembleHylData(Row row,Map<String, List<ImportEntity>> map) {
		if(row != null){
			ImportEntity hyl = new ImportEntity();
			String qiyeno  = row.getCell(17)!=null ? row.getCell(17).toString() : "";
			if(qiyeno != null && !"".equals(qiyeno)){
			Object amount = row.getCell(13) == null ? "0" : row.getCell(13);
			hyl.setSplitAmount(BigDecimal.valueOf(Double.valueOf(amount.toString().replaceAll(",", ""))));
			hyl.setTradingStatus(row.getCell(15)!=null ? row.getCell(15).toString() : "");
			hyl.setId(qiyeno);
			hyl.setFailReason(row.getCell(16)!=null ? row.getCell(16).toString() : "");
			String pch = qiyeno.split("_")[2];
			String splitPch = pch.substring(0,pch.length()-2);
			if (!map.containsKey(splitPch)) {
				List<ImportEntity> list = new ArrayList<ImportEntity>();
				list.add(hyl);
				map.put(splitPch, list);
			} else {
				map.get(splitPch).add(hyl);
			}
			}
		}
	}
	
	/**
	 * 通联组装数据 2016年5月3日 by 翁私
	 * @param row
	 * @param map
	 * @return none
	 */
	public void assembleTlData(Row row, Map<String, List<ImportEntity>> map) {
		if(row != null){
		   ImportEntity tl = new ImportEntity();
		   String qiyeno  = row.getCell(21)!=null ? row.getCell(21).toString() : "";
		   if(qiyeno != null && !"".equals(qiyeno)){
		   tl.setId(qiyeno);
		   String pch = qiyeno.split("_")[2];
		   String splitPch = pch.substring(0,pch.length()-2);
		   Object amount = row.getCell(10) == null ? "0" : row.getCell(10);
		   tl.setSplitAmount(BigDecimal.valueOf(Double.valueOf(amount.toString().replaceAll(",", ""))));
		   tl.setTradingStatus(row.getCell(4).toString());
		   tl.setFailReason(row.getCell(19)!=null ? row.getCell(19).toString() : "");
		   if (!map.containsKey(splitPch)) {
				List<ImportEntity> list = new ArrayList<ImportEntity>();
				list.add(tl);
				map.put(splitPch, list);
			} else {
				map.get(splitPch).add(tl);
			}
		   }
		}
	}
	
	/**
	 * 中金组装数据 2016年5月3日 by 翁私
	 * @param row
	 * @param map
	 * @return none
	 */
	public void assembleZjData(Row row, Map<String, List<ImportEntity>> map) {
		 ImportEntity zj = new ImportEntity();
		if(row != null){
	    String qiyeno  = row.getCell(13)!=null ? row.getCell(13).toString() : "";
		    if(qiyeno != null && !"".equals(qiyeno)){
		    String pch = qiyeno.split("_")[2];
		    String splitPch = pch.substring(0,pch.length()-2);
		    zj.setId(qiyeno);
		    Object amount = row.getCell(5) == null ? "0" : row.getCell(5);
		    zj.setSplitAmount(BigDecimal.valueOf(Double.valueOf(amount.toString().replaceAll(",", ""))));
		    zj.setTradingStatus(row.getCell(23).toString());
		    zj.setFailReason(row.getCell(23).toString());
		    if (!map.containsKey(splitPch)) {
				List<ImportEntity> list = new ArrayList<ImportEntity>();
				list.add(zj);
				map.put(splitPch, list);
			} else {
				map.get(splitPch).add(zj);
			}
	      }
		}
	
	}
	
	/**
	 * 取得Excel的一个sheet 2016年5月3日
	 * @param file
	 * @return
	 * @throws Exception
	 * @return Sheet
	 */
	@SuppressWarnings("resource")
	public Sheet getSheet(MultipartFile file,int num) throws Exception{
		File targetFile = LoanFileUtils.multipartFile2File(file);
		FileInputStream is = new FileInputStream(targetFile);
		String fileName =  targetFile.getName();
		Workbook workbook = null;
		if (StringUtils.isBlank(fileName)){
			throw new RuntimeException("导入文档为空!");
		}else if(fileName.toLowerCase().endsWith("xls")){    
			workbook = new HSSFWorkbook(is);    
        }else if(fileName.toLowerCase().endsWith("xlsx")){  
        	workbook = new XSSFWorkbook(is);
        }else{  
        	throw new RuntimeException("文档格式不正确!");
        }  
		if (workbook.getNumberOfSheets()<0){
			throw new RuntimeException("文档中没有工作表!");
		}
		Sheet sheet = workbook.getSheetAt(num);
		return sheet;
	}
	
}
