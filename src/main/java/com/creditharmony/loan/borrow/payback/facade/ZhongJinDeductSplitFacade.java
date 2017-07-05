package com.creditharmony.loan.borrow.payback.facade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.creditharmony.common.util.Encodes;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.loan.borrow.payback.dao.PaybackSplitDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;
import com.creditharmony.loan.borrow.payback.web.ExportCenterDeductHelper;
import com.creditharmony.loan.common.utils.FileZip;


/**
 * 中金集中划扣拆分转换层
 * @Class Name FuYouDeductSplitFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class ZhongJinDeductSplitFacade {
	
	protected Logger logger = LoggerFactory.getLogger(ZhongJinDeductSplitFacade.class);
	
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	
	@Autowired
	private PaybackSplitService paybackSplitService;
	@Autowired
	private PaybackSplitDao paybackSplitDao;
	
	private static int zhongjin = 1001;
	
	// 文件流集合
	private Map<String,InputStream> mapFile;
	
	public void submitSplitData(List<PaybackApply> paybackApplyList,
			String fileName,String[] header, HttpServletResponse response) {
		CompletionService<PaybackApply> completionService = new ExecutorCompletionService<PaybackApply>(
				executor);
		for (final PaybackApply applyItem : paybackApplyList) {
			completionService.submit(new Callable<PaybackApply>() {
				public PaybackApply call() {
					return splitPaybackApply(applyItem);
				}
			});
		}
		try {
			SXSSFWorkbook workbook = ExportCenterDeductHelper.getWorkbook();
			Sheet dataSheet = workbook.createSheet("ExportList");
			Row headerRow = dataSheet.createRow(0);
			for (int i=0; i<header.length;i++){
				Cell cellHeader = headerRow.createCell(i);
				cellHeader.setCellValue(header[i]);
			}
			int rowNum = 1;
			BigDecimal totalAmount = new BigDecimal(0);
			mapFile = new HashMap<String,InputStream>();
			List<PaybackSplitZjEx> splitList = null;
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					totalAmount = totalAmount.add(returnApply.getApplyAmount());
					splitList = paybackSplitDao.getDeductPaybackListZj(returnApply);
					int count = splitList.size()+rowNum;
					if(count > zhongjin){
						addInputStream(workbook,mapFile,getExcel());
						rowNum  = 1 ;
						workbook = ExportCenterDeductHelper.getWorkbook();
						dataSheet = workbook.createSheet("ExportList");
						headerRow = dataSheet.createRow(0);
						for (int j = 0; j < header.length; j++){
							Cell cellHeader = headerRow.createCell(j);
							cellHeader.setCellValue(header[j]);
						}
						
					}
					rowNum = setZhongjinDataList(splitList, dataSheet, rowNum);
				}
			}
			addInputStream(workbook,mapFile,getExcel());
			download(response,getZip(),getExcel());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出异常", e.getMessage());
		} 
	}
	
	@SuppressWarnings("finally")
	public PaybackApply splitPaybackApply(final PaybackApply paybackApply) {
		try {
			paybackSplitService.splitApply(paybackApply,
					TargetWay.PAYMENT.getCode(), DeductTime.RIGHTNOW,
					DeductPlat.ZHONGJIN);
			paybackApply.setSuccess(true);
		} catch (Exception e) {
			paybackApply.setSuccess(false);
			e.printStackTrace();
			logger.error("拆分富有划扣申请发生异常,applyId={}",
					new Object[] { paybackApply.getId() });
			logger.error("拆分富有划扣申请发生异常",e.getMessage());
		}finally{
			return paybackApply;
		}
	}
	
	public static int setZhongjinDataList(List<PaybackSplitZjEx> splitList,
			Sheet dataSheet, int rowNum) {
		Row dataRow;
		Cell enterpriseSerialnoCell;
		Cell splitAmountCell;
		Cell bankNameCell;
		Cell accountTypeCell;
		Cell bankAccountNameCell;
		Cell bankAccountCell;
		Cell bankBranchCell;
		Cell bankProvinceCell;
		Cell bankCityCell;
		Cell settlementIndicatorCell;
		Cell remarkCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		Cell customerPhoneFirstCell;
		Cell mailboxCell;
		Cell protocolNumberCodeCell;
	
		for (PaybackSplitZjEx e : splitList){
			
			dataRow = dataSheet.createRow(rowNum);
			enterpriseSerialnoCell = dataRow.createCell(0);
			enterpriseSerialnoCell.setCellValue(String.format("%05d",rowNum));
			
			splitAmountCell = dataRow.createCell(1);
			splitAmountCell.setCellValue(e.getSplitAmount());
			
			bankNameCell = dataRow.createCell(2);
			bankNameCell.setCellValue(e.getBankName());
			
			
			accountTypeCell = dataRow.createCell(3);
			accountTypeCell.setCellValue("个人");
			
			bankAccountNameCell = dataRow.createCell(4);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			
			bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellValue(e.getBankAccount());
			
			bankBranchCell = dataRow.createCell(6);
			bankBranchCell.setCellValue(e.getBankBranch());
			
			bankProvinceCell = dataRow.createCell(7);
			bankProvinceCell.setCellValue(e.getBankProvince());
			
			bankCityCell = dataRow.createCell(8);
			bankCityCell.setCellValue(e.getBankCity());
			
			
			settlementIndicatorCell = dataRow.createCell(9);
			settlementIndicatorCell.setCellValue("0001");
			
			remarkCell = dataRow.createCell(10);
			remarkCell.setCellValue(e.getRemark());
			
			
			dictertTypeCell = dataRow.createCell(11);
			dictertTypeCell.setCellValue(e.getDictertType());
			
			customerCertNumCell = dataRow.createCell(12);
			customerCertNumCell.setCellValue(e.getCustomerCertNum());
			
			customerPhoneFirstCell = dataRow.createCell(13);
			customerPhoneFirstCell.setCellValue("");
			
			mailboxCell = dataRow.createCell(14);
			mailboxCell.setCellValue("");
			
			protocolNumberCodeCell = dataRow.createCell(15);
			protocolNumberCodeCell.setCellValue(e.getProtocolNumberCode());
			rowNum++;
		}
		return rowNum;
	}
	
	/**
	 * 中金导出 导出txt
	 * @param paybackApplyList
	 * @param fileName
	 * @param header
	 * @param response
	 */
	public void submitSplitText(List<PaybackApply> paybackApplyList,
			String fileName,HttpServletResponse response) {
		CompletionService<PaybackApply> completionService = new ExecutorCompletionService<PaybackApply>(
				executor);
		for (final PaybackApply applyItem : paybackApplyList) {
			completionService.submit(new Callable<PaybackApply>() {
				public PaybackApply call() {
					return splitPaybackApply(applyItem);
				}
			});
		}
		try {
			List<PaybackSplitZjEx> splitList = null;
		    StringBuffer write = new StringBuffer(); 
	        int count = 1;
	        mapFile = new HashMap<String,InputStream>();
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					splitList = paybackSplitDao.getDeductPaybackListZj(returnApply);
					int countNum = splitList.size() + count;
					if(countNum > zhongjin){
						addInputStreamTxt(write.toString(),getTxt());
						count = 1 ;
						write = new StringBuffer();
					}
					count = setZhongjinDataTxt(splitList,write,count);
			    }
			}
			addInputStreamTxt(write.toString(),getTxt());
			download(response,getZip(),getTxt());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出异常", e.getMessage());
		}
	}
	
	/**
	 * 封装压缩excel对象
	 * 2016年3月3日
	 * By 翁私
	 * @param ee
	 */
	private void addInputStream(SXSSFWorkbook wb,Map<String,InputStream> mapFile,String fileName){
		try {
			// 初始化内存流对象
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			wb.write(out);
			wb.dispose();
			// 转换成内存输出流
			InputStream input = new ByteArrayInputStream(out.toByteArray());
			mapFile.put(fileName, input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static int setZhongjinDataTxt(List<PaybackSplitZjEx> splitList, StringBuffer write,int count) {
		for(PaybackSplitZjEx zj : splitList){
			if(zj.getSplitAmount() == null){
				zj.setSplitAmount("0");
			}
			BigDecimal totalAmount = new BigDecimal(zj.getSplitAmount()).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
			write.append(String.format("%05d", count));
			write.append("|"+totalAmount);
			write.append("|"+zj.getBankName());
			write.append("|11");
			write.append("|"+(zj.getBankAccountName() == null ? "" : zj.getBankAccountName()));
			write.append("|"+(zj.getBankAccount() == null ? "" : zj.getBankAccount()));
			write.append("|"+(zj.getBankBranch() == null ? "" : zj.getBankBranch()));
			write.append("|"+(zj.getBankProvince() == null ? "" : zj.getBankProvince()));
			write.append("|"+(zj.getBankCity() == null ? "" : zj.getBankCity()));
			write.append("|"+(zj.getRemark() == null ? "" : zj.getRemark()));
			write.append("|"+(zj.getDictertType() == null ? "" : changeNum(zj.getDictertType())));
			write.append("|"+(zj.getCustomerCertNum() == null ? "" : zj.getCustomerCertNum()));
			write.append("|");
			write.append("|");
			write.append("|");
			write.append("|0001");
			write.append("\r\n");
			count++;
		}
		return count;
	}
	
	/**
	 * 生成txt流
	 * 2016年4月22日
	 * By 韩龙
	 * @param string
	 * @param mapFile2
	 * @param reFileName
	 * @throws UnsupportedEncodingException 
	 */
	private void addInputStreamTxt(String string, String reFileName) throws UnsupportedEncodingException {
		InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"));
		mapFile.put(reFileName, input);
	}

	/**
	 * Excel 名字
	 * @return
	 */
	public  String getExcel(){
		String pchNo = String.valueOf(System.currentTimeMillis());
		return "中金线下导出Excel"+pchNo+".xlsx";
	}
	
	/**
	 * txt 名字
	 * @return
	 */
	public  String getTxt(){
		String pchNo = String.valueOf(System.currentTimeMillis());
		return "中金线下导出"+pchNo+".txt";
	}
	
	/**
	 * zip 名字
	 * @return
	 */
	public  String getZip(){
		String pchNo = String.valueOf(System.currentTimeMillis());
		return "中金线下导出"+pchNo;
	}
	
	/**
	 * 讲系统的证件类型转为上传的证件类型
	 * 2016年3月14日
	 * By 翁私
	 * @param cum
	 * @return
	 */
	public static String changeNum(String cum){
		//0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
		if(cum.equals(CertificateType.SFZ.getCode())){
			return "0";
		}else if(cum.equals(CertificateType.JGZ.getCode())){
			return "3";
		}else if(cum.equals(CertificateType.HZ.getCode())){
			return "2";
		}else if(cum.equals(CertificateType.HKB.getCode())){
			return "1";
		}else if(cum.equals(CertificateType.GAJMLWNDTXZ.getCode())){
			return "5";
		}
		return "";
	}
	
	/**
	 * 下载
	 * @param response
	 * @param zipName
	 * @param fileName
	 */
	private void download(HttpServletResponse response,String zipName,String fileName) {
		OutputStream out = null;
		// 下载到客户端
		if (mapFile.size() > 1) {
			try {
				// 设置Header信息
				response.setHeader("Content-Disposition",
						"attachment; filename=" + Encodes.urlEncode(zipName) + ".zip"
								+ ";filename*=UTF-8''" +Encodes.urlEncode(zipName)+ ".zip");
				out  = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			   FileZip.zipFiles(out, mapFile);
		} else {
			try {
				// 设置Header信息
				response.setHeader("Content-Disposition",
						"attachment; filename=" + Encodes.urlEncode(fileName)
								+ fileName + ";filename*=UTF-8''"
								+ Encodes.urlEncode(fileName));
				 out = response.getOutputStream();
				for (String key : mapFile.keySet()) {
					FileCopyUtils.copy(mapFile.get(key), out);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
