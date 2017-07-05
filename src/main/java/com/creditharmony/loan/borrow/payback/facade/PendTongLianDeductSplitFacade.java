package com.creditharmony.loan.borrow.payback.facade;

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
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.service.DeductPaybackService;
import com.creditharmony.loan.borrow.payback.web.ExportCenterDeductHelper;


/**
 * 通联待还款划扣拆分转换层
 * @Class Name FuYouDeductSplitFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class PendTongLianDeductSplitFacade {
	
	protected Logger logger = LoggerFactory.getLogger(PendTongLianDeductSplitFacade.class);
	
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
			int rowNum = 2;
			int count = 0;
			BigDecimal totalAmount = new BigDecimal(0);
			List<PaybackSplitTlEx> splitList = null;
			List<PaybackSplitTlEx> alllist = new ArrayList<PaybackSplitTlEx>();
			for (int i = 0; i < paybackApplyList.size(); i++) {
				Future<PaybackApply> future = completionService.take();
				PaybackApply returnApply = future.get();
				if(returnApply.isSuccess()){
					totalAmount = totalAmount.add(returnApply.getApplyAmount());
					splitList = deductPaybackService.getDeductPaybackListTl(returnApply);
					for(PaybackSplitTlEx split : splitList){
						alllist.add(split);
						count++;
					}
					
				}
			}
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
			rowNum = setTongLianDataList(alllist, dataSheet, rowNum);
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
					DeductPlat.TONGLIAN);
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
	
	public static int setTongLianDataList(List<PaybackSplitTlEx> splitList,
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
			//如果银行是中国银行则将证件号码写入excel
			if(e.getBackCode().equals("104")){
				customerCertNumCell.setCellValue(e.getCustomerCertNum());
			}else{
				customerCertNumCell.setCellValue("");
			}
			
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
	
}
