package com.creditharmony.loan.borrow.payback.facade;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;
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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;
import com.creditharmony.loan.borrow.payback.service.DeductPaybackService;
import com.creditharmony.loan.borrow.payback.web.ExportCenterDeductHelper;


/**
 * 中金待还款划扣拆分转换层
 * @Class Name FuYouDeductSplitFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class PendZhongJinDeductSplitFacade {
	
	protected Logger logger = LoggerFactory.getLogger(PendZhongJinDeductSplitFacade.class);
	
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	
	@Autowired
	private DeductPaybackService deductPaybackService;
	
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
			
			List<PaybackSplitZjEx> splitList = null;
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					totalAmount = totalAmount.add(returnApply.getApplyAmount());
					splitList = deductPaybackService.getDeductPaybackListZj(returnApply);
					rowNum = setZhongjinDataList(splitList, dataSheet, rowNum);
				}
			}
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
			accountTypeCell.setCellValue(e.getAccountType());
			
			bankAccountNameCell = dataRow.createCell(4);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			
			bankAccountCell = dataRow.createCell(5);
			bankAccountCell.setCellValue(e.getBankAccount());
			
			bankBranchCell = dataRow.createCell(6);
			bankBranchCell.setCellValue(e.getBankBranch());
			
			bankProvinceCell = dataRow.createCell(7);
			bankProvinceCell.setCellValue(e.getBankBranch());
			
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
	 	BufferedOutputStream buff = null;  
	    ServletOutputStream outSTr = null;  
		try {
			List<PaybackSplitZjEx> splitList = null;
		    StringBuffer write = new StringBuffer(); 
	        int count = 1;
	        outSTr = response.getOutputStream();// 建立  
            buff = new BufferedOutputStream(outSTr);  
            response.setContentType("text/plain");// 一下两行关键的设置  
	        response.addHeader("Content-Disposition",  
	                "attachment;filename="+fileName+".txt");
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					splitList = deductPaybackService.getDeductPaybackListZj(returnApply);
					for (PaybackSplitZjEx zj : splitList){
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
					write.append("|"+(zj.getCustomerPhoneFirst() == null ? "" : zj.getCustomerPhoneFirst()));
					write.append("|"+(zj.getMailbox() == null ? "" : zj.getMailbox()));
					write.append("|");
					write.append("|0001");
					write.append("\r\n");
					count++;
					}
			    }
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
	
	/**
	 * 讲系统的证件类型转为上传的证件类型
	 * 2016年3月14日
	 * By 翁私
	 * @param cum
	 * @return
	 */
	public String changeNum(String cum){
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
}
