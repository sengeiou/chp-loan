package com.creditharmony.loan.borrow.payback.facade;

import java.util.List;
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

import com.creditharmony.common.util.Encodes;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductPlatBank;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.service.DeductPaybackService;
import com.creditharmony.loan.borrow.payback.web.ExportCenterDeductHelper;


/**
 * 富友待还款划扣拆分转换层
 * @Class Name FuYouDeductSplitFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class PendFuYouDeductSplitFacade {
	
	protected Logger logger = LoggerFactory.getLogger(PendFuYouDeductSplitFacade.class);
	
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	
	@Autowired
	private DeductPaybackService deductPaybackService;
	
	public void submitSplitData(List<PaybackApply> paybackApplyList,
			String fileName, String[] header, HttpServletResponse response) {
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
			for (int j=0; j<header.length;j++){
				Cell cellHeader = headerRow.createCell(j);
				cellHeader.setCellValue(header[j]);
			}
			int rowNum = 1;
			List<PaybackSplitFyEx> splitList = null;
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					splitList = deductPaybackService.getDeductPaybackList(returnApply);
					rowNum = setFuYouDataList(splitList, dataSheet, rowNum);
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
					DeductPlat.FUYOU);
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
	
	public static int setFuYouDataList(List<PaybackSplitFyEx> splitList,
			Sheet dataSheet, int rowNum) {
		Row dataRow;
		Cell numCell;
		Cell bankNameCell;
		Cell bankAccountCell;
		Cell bankAccountNameCell;
		Cell splitAmountCell;
		Cell enterpriseSerialnoCell;
		Cell remarksCell;
		Cell customerPhoneFirstCell;
		Cell dictertTypeCell;
		Cell customerCertNumCell;
		for (PaybackSplitFyEx e : splitList){
			dataRow = dataSheet.createRow(rowNum);
			numCell = dataRow.createCell(0);
			numCell.setCellValue(rowNum);
			bankNameCell = dataRow.createCell(1);
			bankNameCell.setCellValue(e.getBankName());
			bankAccountCell = dataRow.createCell(2);
			bankAccountCell.setCellValue(e.getBankAccount());
			bankAccountNameCell = dataRow.createCell(3);
			bankAccountNameCell.setCellValue(e.getBankAccountName());
			splitAmountCell = dataRow.createCell(4);
			splitAmountCell.setCellValue(e.getSplitAmount().toString());
			enterpriseSerialnoCell = dataRow.createCell(5);
			enterpriseSerialnoCell.setCellValue(e.getEnterpriseSerialno());
			remarksCell = dataRow.createCell(6);
			remarksCell.setCellValue(e.getRemarks());
			customerPhoneFirstCell = dataRow.createCell(7);
			customerPhoneFirstCell.setCellValue(e.getCustomerPhoneFirst());
			String bankCode = e.getBankCode();
			if(DeductPlatBank.ICBC.getCode().equals(bankCode) || DeductPlatBank.ABC.getCode().equals(bankCode)){
				dictertTypeCell = dataRow.createCell(8);
				dictertTypeCell.setCellValue(e.getDictertType());
				customerCertNumCell = dataRow.createCell(9);
				customerCertNumCell.setCellValue(e.getCustomerCertNum());
			}else{
				dictertTypeCell = dataRow.createCell(8);
				dictertTypeCell.setCellValue("");
				customerCertNumCell = dataRow.createCell(9);
				customerCertNumCell.setCellValue("");
			}
			rowNum++;
		}
		return rowNum;
	}
	
	
}
