package com.creditharmony.loan.borrow.payback.facade;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylExport;
import com.creditharmony.loan.borrow.payback.service.DeductPaybackService;
import com.creditharmony.loan.borrow.payback.web.ExportCenterDeductHelper;


/**
 * 好易联待还款划扣拆分转换层
 * @Class Name FuYouDeductSplitFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class PendHaoYiLianDeductSplitFacade {
	
	protected Logger logger = LoggerFactory.getLogger(PendHaoYiLianDeductSplitFacade.class);
	
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	
	@Autowired
	private DeductPaybackService deductPaybackService;
	
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
		    int count = 0;
			int rowNum = 3;
			BigDecimal totalAmount = new BigDecimal(0);
			
			List<PaybackSplitHylExport> splitList = null;
			List<PaybackSplitHylExport> alllist = new ArrayList<PaybackSplitHylExport>();
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					totalAmount = totalAmount.add(returnApply.getApplyAmount());
					splitList = deductPaybackService.getDeductPaybackListHyl(returnApply);
					for(PaybackSplitHylExport split : splitList){
						alllist.add(split);
						count++;
					}
				}
			}
			totalAmount = totalAmount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
			String[][] title = {
					{ "代收付类型", "商户ID", "提交日期", "总记录数", "总金额", "业务类型" },
					{ "S", Global.getConfig("creditharmony.haoyilian.business.code"), DateUtils.getDate("yyyyMMdd"),
							String.valueOf(count),totalAmount.toString(),
							"14900" } };
			Row row1 = dataSheet.createRow(0);
			String[] header1 = title[0];
			for (int i=0; i<header1.length;i++){
				Cell cellHeader = row1.createCell(i);
				cellHeader.setCellValue(header1[i]);
			}
			
			Row row2 = dataSheet.createRow(1);
			String[] headerDate = title[1];
			for (int i=0; i<headerDate.length;i++){
				Cell cellHeader = row2.createCell(i);
				cellHeader.setCellValue(headerDate[i]);
			}
			
			Row rows3 = dataSheet.createRow(2);
			String[]  title2= {"序号","银联网络用户编号","银行代码","账号类型","账号","账户名","开户行所在省","开户行所在市","开户行名称","帐户类型","金额","货币类型","协议号","协议用户编号","开户证件类型","证件号","手机号","自定义用户名","备注1","备注2","备注","反馈码","原因"};
			for (int i=0; i<title2.length;i++){
				Cell cellHeader = rows3.createCell(i);
				cellHeader.setCellValue(title2[i]);
			}
		    setHaoYiLianDataList(alllist, dataSheet, rowNum);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+".xlsx")
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+".xlsx"));
			workbook.write(response.getOutputStream());
			workbook.dispose();
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
			deductPaybackService.splitApply(paybackApply,
					TargetWay.PAYMENT.getCode(), DeductTime.RIGHTNOW,
					DeductPlat.HAOYILIAN);
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
	public static int setHaoYiLianDataList(List<PaybackSplitHylExport> splitList,
			Sheet dataSheet, int rowNum) {
		Row dataRow;
		//int row = 3;
		Cell numCell;
		Cell bankNetworkUserCodeCell;
		Cell bankCodeCell;
		Cell accountTypeCell;
		Cell bankAccountCell;
		Cell bankAccountNameCell;
		Cell bankProvinceCell;
		Cell bankCityCell;
		Cell bankNameCell;
		Cell accountTypeNoCell;
		Cell splitAmountCell;
		Cell currencyTypeCell;
		Cell protocolNumberCell;
		Cell protocolNumberCodeCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		Cell customerPhoneFirstCell;
		Cell customUserNameCell;
		Cell remarkOneCell;
		Cell remarkTwoCell;
		Cell enterpriseSerialnoCell;
		Cell feedbackCodeCell;
		Cell reasonCell;
		for (PaybackSplitHylExport e : splitList){
			
			dataRow = dataSheet.createRow(rowNum);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(String.format("%5d", rowNum-2).trim());
			
			bankNetworkUserCodeCell = dataRow.createCell(1);
			bankNetworkUserCodeCell.setCellValue(e.getBankNetworkUserCode());
			
			bankCodeCell = dataRow.createCell(2);
			bankCodeCell.setCellValue(e.getBankCode());
			
			
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
			
			accountTypeNoCell = dataRow.createCell(9);
			accountTypeNoCell.setCellValue(e.getAccountType());
			
			splitAmountCell = dataRow.createCell(10);
			splitAmountCell.setCellValue(e.getSplitAmount().toString());
			
			currencyTypeCell = dataRow.createCell(11);
			currencyTypeCell.setCellValue(e.getCurrencyType());
			
			
			protocolNumberCell = dataRow.createCell(12);
			protocolNumberCell.setCellValue(e.getProtocolNumber());
			
			protocolNumberCodeCell = dataRow.createCell(13);
			protocolNumberCodeCell.setCellValue(e.getProtocolNumberCode());
			
			dictertTypeCell = dataRow.createCell(14);
			dictertTypeCell.setCellValue(e.getDictertType());
			
			customerCertNumCell = dataRow.createCell(15);
			customerCertNumCell.setCellValue(e.getCustomerCertNum());
			
			customerPhoneFirstCell = dataRow.createCell(16);
			customerPhoneFirstCell.setCellValue(e.getCustomerPhoneFirst());
			
			customUserNameCell = dataRow.createCell(17);
			customUserNameCell.setCellValue(e.getCustomUserName());
			
			remarkOneCell = dataRow.createCell(18);
			remarkOneCell.setCellValue(e.getRemarkOne());
			
			remarkTwoCell = dataRow.createCell(19);
			remarkTwoCell.setCellValue(e.getRemarkTwo());
			
			enterpriseSerialnoCell = dataRow.createCell(20);
			enterpriseSerialnoCell.setCellValue(e.getEnterpriseSerialno());
			
			feedbackCodeCell = dataRow.createCell(21);
			feedbackCodeCell.setCellValue(e.getFeedbackCode());
			
			reasonCell = dataRow.createCell(22);
			reasonCell.setCellValue(e.getReason());
			rowNum++;
		}
		return rowNum;
	}
	
	/**
	 * 好易联Txt
	 * @param paybackApplyList
	 * @param fileName
	 * @param response
	 */
	public void submitSplitTxt(List<PaybackApply> paybackApplyList,
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
		BufferedOutputStream buff = null;  
	    ServletOutputStream outSTr = null;  
		try {
			outSTr = response.getOutputStream();// 建立  
	        buff = new BufferedOutputStream(outSTr);  
			BigDecimal totalAmount = new BigDecimal(0);
			  StringBuffer write = new StringBuffer(); 
			int count = 0;
			response.setContentType("text/plain");// 一下两行关键的设置  
		    response.addHeader("Content-Disposition",  
		                "attachment;filename="+fileName+".txt");
			List<PaybackSplitHylExport> splitList = null;
			List<PaybackSplitHylExport> alllist = new ArrayList<PaybackSplitHylExport>();
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					totalAmount = totalAmount.add(returnApply.getApplyAmount());
					splitList = deductPaybackService.getDeductPaybackListHyl(returnApply);
					for(PaybackSplitHylExport split : splitList){
						alllist.add(split);
						count++;
					}
				}
			}
			totalAmount = totalAmount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
			write.append("S,");
			write.append(Global.getConfig("creditharmony.haoyilian.business.code")+",");
			write.append(DateUtils.getDate("yyyyMMdd")+",");
			write.append(String.valueOf(count)+",");
			write.append(totalAmount.toString()+",");
			write.append("14900");
			write.append("\r\n");
			int xuhao = 1;
			for(PaybackSplitHylExport ex:alllist){
				write.append(xuhao+",");
				write.append(getNullToString(ex.getBankNetworkUserCode())+",");
				write.append(getNullToString(ex.getBankCode())+",");
				write.append(getNullToString(ex.getAccountType())+",");
				write.append(getNullToString(ex.getBankAccount())+",");
				write.append(getNullToString(ex.getBankAccountName())+",");
				write.append(getNullToString(ex.getBankProvince())+",");
				write.append(getNullToString(ex.getBankCity())+",");
				write.append(getNullToString(ex.getBankName())+",");
				write.append(getNullToString(ex.getAccountTypeNo())+",");
				write.append(getNullToString(ex.getSplitAmount())+",");
				write.append(getNullToString(ex.getCurrencyType())+",");
				write.append(getNullToString(ex.getProtocolNumber())+",");
				write.append(getNullToString(ex.getProtocolNumberCode())+",");
				write.append(getNullToString(ex.getDictertType())+",");
				write.append(getNullToString(ex.getCustomerCertNum())+",");
				write.append(getNullToString(ex.getCustomerPhoneFirst())+",");
				write.append(getNullToString(ex.getCustomUserName())+",");
				write.append(getNullToString(ex.getRemarkOne())+",");
				write.append(getNullToString(ex.getRemarkTwo())+",");
				write.append(getNullToString(ex.getEnterpriseSerialno())+",");
				write.append(getNullToString(ex.getFeedbackCode())+",");
				write.append(getNullToString(ex.getReason())+",");
				write.append("\r\n");
				xuhao++;
			}
			buff.write(write.toString().getBytes("UTF-8"));  
            buff.flush();  
            buff.close();  
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出异常", e.getMessage());
		} finally {  
            try {  
                buff.close();  
                outSTr.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
	}
	
	public String getNullToString(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
}
