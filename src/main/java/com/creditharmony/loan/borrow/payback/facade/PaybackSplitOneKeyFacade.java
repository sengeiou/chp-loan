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

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;

/**
 * 集中划扣一键发送 Facade
 * @author ws
 */
@Service
public class PaybackSplitOneKeyFacade{
	
	protected Logger logger = LoggerFactory.getLogger(PaybackSplitOneKeyFacade.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	@Autowired
	private PaybackSplitService paybackSplitService;
	@SuppressWarnings({ "finally", "unused" })
	public String submitData(List<DeductReq> deductApplyList,final String deductType, final Map<String, String> map) {
		StringBuffer message = new StringBuffer();
		CompletionService<DeductReq> completionService = new ExecutorCompletionService<DeductReq>(
				executor);
		for ( final DeductReq apply :deductApplyList) {
			completionService.submit(new Callable<DeductReq>() {
				public DeductReq call() {
					return oneKeysendDeductor(apply,deductType,map);
				}
			});
		}
		int successNum = 0;
		int failNum = 0;
		BigDecimal totalAmount = new BigDecimal(0);
		try {
			for (DeductReq apply : deductApplyList) {
				Future<DeductReq> future = completionService.take();
				DeductReq fapply = future.get();
					if(fapply.getSuccess()){
						successNum++;
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
				message.append("成功发送条数：");
				message.append(successNum);
				message.append(",");
			}
			if (failNum > 0) {
				message.append("  失败条数：");
				message.append(failNum);
				message.append("。");
			}
			return message.toString();
		}
	}
	
	public DeductReq oneKeysendDeductor(
			final DeductReq deductApply,final String deductType,final  Map<String, String> map) {
			try {
				Boolean flag = paybackSplitService.oneKeysendDeductor(deductApply,deductType,OperateType.COLLECT_DEDUCT.getCode(),map);
				if(flag){
				    deductApply.setSuccess(true);
				}else{
					deductApply.setSuccess(false);
				}
			} catch (Exception e) {
				    deductApply.setSuccess(false);
					e.printStackTrace();
					logger.error("集中划扣：合同号为"+deductApply.getRemarks()+"出错");
			}
		return deductApply;
	}

}
