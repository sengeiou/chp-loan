package com.creditharmony.loan.borrow.payback.facade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.loan.borrow.payback.dao.PaybackSplitDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;
import com.creditharmony.loan.borrow.payback.web.ExportCenterDeductHelper;
import com.creditharmony.loan.common.utils.FileZip;


/**
 * 富友集中划扣拆分转换层
 * @Class Name FuYouDeductSplitFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class TongLianDeductSplitFacade {
	
	protected Logger logger = LoggerFactory.getLogger(TongLianDeductSplitFacade.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	
	@Autowired
	private PaybackSplitService paybackSplitService;
	@Autowired
	private PaybackSplitDao paybackSplitDao;
	
    private static int tonglian = 500;
	// 文件流集合
	private Map<String,InputStream> mapFile;
	
	public void submitSplitData(List<PaybackApply> paybackApplyList,
			String fileName, HttpServletResponse response) {
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
			int rowNum = 2;
			int count = 0;
			mapFile = new HashMap<String,InputStream>();
			BigDecimal totalAmount = new BigDecimal(0);
			List<PaybackSplitTlEx> alllist = new ArrayList<PaybackSplitTlEx>();
			List<PaybackSplitTlEx> splitList = null;
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					splitList = paybackSplitDao.getDeductPaybackListTl(returnApply);
					int cnt = count + splitList.size();
					if(cnt > tonglian){
						setTitle(totalAmount,dataSheet,count);
						setTonglianDataList(alllist, dataSheet, rowNum);
						alllist =  new ArrayList<PaybackSplitTlEx>();
						addInputStream(workbook,mapFile,getExcel());
						totalAmount = new  BigDecimal(0);
						count = 0;
						workbook = ExportCenterDeductHelper.getWorkbook();
						dataSheet = workbook.createSheet("ExportList");
					}
					for(PaybackSplitTlEx split : splitList){
						alllist.add(split);
						count++;
					}
					totalAmount = totalAmount.add(returnApply.getApplyAmount());
				}
			}
			if(alllist.size()>0){
				setTitle(totalAmount,dataSheet,count);
				setTonglianDataList(alllist, dataSheet, rowNum);
				addInputStream(workbook,mapFile,getExcel());
			}
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
					DeductPlat.TONGLIAN);
			paybackApply.setSuccess(true);
		} catch (Exception e) {
			paybackApply.setSuccess(false);
			e.printStackTrace();
			logger.error("拆分通联划扣申请发生异常,applyId={}",
					new Object[] { paybackApply.getId() });
			logger.error("拆分通联划扣申请发生异常",e.getMessage());
		}finally{
			return paybackApply;
		}
	}
	
	public static int setTonglianDataList(List<PaybackSplitTlEx> splitList,
			Sheet dataSheet, int rowNum) {
		Row dataRow;
		Cell serialNumberCell;
		Cell userCodeCell;
		Cell backCodeCell;
		Cell accountTypeCell;
		Cell  bankAccountCell;
		Cell  bankAccountNameCell;
		Cell  bankProvinceCell;
		Cell bankCityCell;
		Cell bankNameCell;
		Cell accountType1Cell;
		Cell splitAmountCell;
		Cell currencyCell;
		Cell protocolNoCell;
		Cell protocolNoUserCodeCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		Cell customerPhoneFirstCell;
		Cell enterpriseSerialnoCell;
		Cell remarkCell;
		Cell feedbackCodeCell;
		Cell reasonCell;
		
	for (PaybackSplitTlEx e : splitList){
			
			dataRow = dataSheet.createRow(rowNum);
			serialNumberCell = dataRow.createCell(0);
			serialNumberCell.setCellValue(String.format("%05d",rowNum-1));
			
			userCodeCell = dataRow.createCell(1);
			userCodeCell.setCellValue(e.getUserCode());
			
			backCodeCell = dataRow.createCell(2);
			backCodeCell.setCellValue(e.getBackCode());
			
			accountTypeCell = dataRow.createCell(3);
			accountTypeCell.setCellValue(e.getAccountType());
			
			bankAccountCell = dataRow.createCell(4);
			bankAccountCell.setCellValue(e.getBankAccount());
			
			
			
			bankAccountNameCell = dataRow.createCell(5);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			
			bankProvinceCell = dataRow.createCell(6);
			bankProvinceCell.setCellValue(e.getBankProvince());
			
			bankCityCell = dataRow.createCell(7);
			bankCityCell.setCellValue(e.getBankCity());
			
			bankNameCell = dataRow.createCell(8);
			bankNameCell.setCellValue(e.getBankName());
			
			accountType1Cell = dataRow.createCell(9);
			accountType1Cell.setCellValue(e.getAccountType1());
			
			splitAmountCell = dataRow.createCell(10);
			splitAmountCell.setCellValue(e.getSplitAmount());
			
			
			currencyCell = dataRow.createCell(11);
			currencyCell.setCellValue(e.getCurrency());
			
			protocolNoCell = dataRow.createCell(12);
			protocolNoCell.setCellValue(e.getProtocolNo());
			
			protocolNoUserCodeCell = dataRow.createCell(13);
			protocolNoUserCodeCell.setCellValue(e.getProtocolNoUserCode());
			
			dictertTypeCell = dataRow.createCell(14);
			dictertTypeCell.setCellValue(e.getDictertType());
			
			customerCertNumCell = dataRow.createCell(15);
			customerCertNumCell.setCellValue("");
			
			customerPhoneFirstCell = dataRow.createCell(16);
			customerPhoneFirstCell.setCellValue("");
			
			enterpriseSerialnoCell = dataRow.createCell(17);
			enterpriseSerialnoCell.setCellValue(e.getEnterpriseSerialno());
			
			remarkCell = dataRow.createCell(18);
			remarkCell.setCellValue(e.getRemark());
			
			feedbackCodeCell = dataRow.createCell(19);
			feedbackCodeCell.setCellValue(e.getFeedbackCode());
			
			reasonCell = dataRow.createCell(20);
			reasonCell.setCellValue(e.getReason());
			
			rowNum++;
		}
		return rowNum;
	}
	
	/**
	 * Excel 名字
	 * @return
	 */
	public  String getExcel(){
		String pchNo = String.valueOf(System.currentTimeMillis());
		return "通联线下导出Excel"+pchNo+".xlsx";
	}
	
	/**
	 * zip 名字
	 * @return
	 */
	public  String getZip(){
		String pchNo = String.valueOf(System.currentTimeMillis());
		return "通联线下导出"+pchNo;
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
	
	public void setTitle(BigDecimal totalAmount,Sheet dataSheet,int count){
		totalAmount = totalAmount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
		String[] header = {"S",Global.getConfig("creditharmony.tonglian.business.code"),DateUtils.getDate("yyyyMMdd"),String.valueOf(count),totalAmount.toString(),"19900"};
		Row headerRow = dataSheet.createRow(0);
		for (int i=0; i<header.length;i++){
			Cell cellHeader = headerRow.createCell(i);
			cellHeader.setCellValue(header[i]);
		}
		Row row2 = dataSheet.createRow(1);
		String[] header2 = {"序号","用户编号","银行代码","账号类型","账号","户名","省","市","开户行名称","账户类型","金额","货币类型","协议号","协议用户编号","开户证件类型","证件号","手机号/小灵通","自定义用户号","备注","反馈码","原因"};
		for (int i=0; i<header2.length;i++){
			Cell cellHeader = row2.createCell(i);
			cellHeader.setCellValue(header2[i]);
		}
		
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
