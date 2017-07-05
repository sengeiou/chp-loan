package com.creditharmony.loan.borrow.payback.facade;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.thread.ProcessorHandler;
import com.creditharmony.loan.borrow.payback.entity.DeductsPaybackApply;
import com.creditharmony.loan.borrow.payback.service.CenterDeductService;

/**
 * 集中划扣申请 Facade
 * @author ws
 */
@Service
public class CenterPaybackFacade implements ProcessorHandler<DeductsPaybackApply>{
	
	protected Logger logger = LoggerFactory.getLogger(CenterPaybackFacade.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	@Autowired
	private CenterDeductService centerDeductService;
	@SuppressWarnings({ "finally", "unused" })
	public String submitData(List<DeductsPaybackApply> deductApplyList) {
		StringBuffer message = new StringBuffer();
		CompletionService<DeductsPaybackApply> completionService = new ExecutorCompletionService<DeductsPaybackApply>(
				executor);
		for ( final DeductsPaybackApply apply :deductApplyList) {
			completionService.submit(new Callable<DeductsPaybackApply>() {
				public DeductsPaybackApply call() {
					return centerApply(apply);
				}
			});
		}
		int successNum = 0;
		int failNum = 0;
		BigDecimal totalAmount = new BigDecimal(0);
		try {
			for (DeductsPaybackApply apply : deductApplyList) {
				Future<DeductsPaybackApply> future = completionService.take();
				DeductsPaybackApply fapply = future.get();
					if(fapply.isSuccess()){
						successNum++;
						totalAmount = totalAmount.add(fapply.getApplyDeductAmount());
					}else{
						failNum++;
				}
			}
			totalAmount = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} finally{
			if (successNum > 0) {
				message.append("申请成功条数：");
				message.append(successNum);
				message.append(",");
				message.append("申请总金额："+totalAmount.toString());
			}
			if (failNum > 0) {
				message.append("  失败条数：");
				message.append(failNum);
				message.append("。");
			}
			return message.toString();
		}
	}
	
	@Override
	public void run(DeductsPaybackApply deductApply) {
		centerApply(deductApply);
	}
	public DeductsPaybackApply centerApply(
			final DeductsPaybackApply deductApply) {
			try {
				centerDeductService.deductionApply(deductApply);
				deductApply.setSuccess(true);
			} catch (Exception e) {
				deductApply.setSuccess(false);
				e.printStackTrace();
				logger.error("集中划扣申请：合同号为"+deductApply.getContractCode()+"出错");
			}
		return deductApply;
	}
}
