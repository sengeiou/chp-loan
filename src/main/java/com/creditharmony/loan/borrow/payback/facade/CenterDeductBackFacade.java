package com.creditharmony.loan.borrow.payback.facade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;

/**
 * 集中划扣退回 Facade
 * @author ws
 */
@Service
public class CenterDeductBackFacade {	
	protected Logger logger = LoggerFactory.getLogger(CenterDeductBackFacade.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	@Autowired
	private PaybackSplitService paybackSplitService;
	@SuppressWarnings({ "finally", "unused" })
	public String submitData(final List<PaybackSplit> deductApplyList,final Map<String, Object> paramMap) {
		StringBuffer message = new StringBuffer();
		CompletionService<PaybackSplit> completionService = new ExecutorCompletionService<PaybackSplit>(
				executor);
		for ( final PaybackSplit apply :deductApplyList) {
			completionService.submit(new Callable<PaybackSplit>() {
				public PaybackSplit call() {
					return centerApply(apply,paramMap);
				}
			});
		}
		int successNum = 0;
		int failNum = 0;
		try {
			for (PaybackSplit apply : deductApplyList) {
				Future<PaybackSplit> future = completionService.take();
				PaybackSplit fapply = future.get();
					if(fapply.isSuccess()){
						successNum++;
					}else{
						failNum++;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} finally{
			if (successNum > 0) {
				message.append("成功退回：");
				message.append(successNum);
				message.append("！");
			}
			if (failNum > 0) {
				message.append("  失败：");
				message.append(failNum);
				message.append("！");
			}
			return message.toString();
		}
	}
	
	public PaybackSplit centerApply(
			final PaybackSplit deductApply,final Map<String, Object> paramMap) {
			try {
				paybackSplitService.operateBackApply(paramMap,deductApply);
				deductApply.setSuccess(true);
			} catch (Exception e) {
				deductApply.setSuccess(false);
				e.printStackTrace();
				logger.error("集中划扣退回：合同号为"+deductApply.getContractCode()+"出错");
			}
		return deductApply;
	}
}
