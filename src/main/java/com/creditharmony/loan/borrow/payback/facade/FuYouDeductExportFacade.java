package com.creditharmony.loan.borrow.payback.facade;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.thread.MessagerProcessor;
import com.creditharmony.core.thread.ProcessorHandler;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;

/**
 *  富友集中划扣拆分后导出excel转换层
 * @Class Name FuYouDeductExportFacade
 * @author 张永生
 * @Create In 2016年5月4日
 */
@Component
public class FuYouDeductExportFacade implements ProcessorHandler<PaybackApply>{
	
	protected Logger logger = LoggerFactory.getLogger(FuYouDeductExportFacade.class);

	private LinkedBlockingQueue<PaybackApply> queue = new LinkedBlockingQueue<PaybackApply>();
	private MessagerProcessor<PaybackApply> processor = new MessagerProcessor<PaybackApply>(
			queue, this, Executors.newFixedThreadPool(1));
	private boolean start = true;
	@Autowired
	private PaybackSplitService paybackSplitService;
	
	public void addData2Queue(PaybackApply paybackApply) {
		if(start){
			processor.start();
			start = false;
		}
		queue.add(paybackApply);
	}
	
	public void run(PaybackApply paramT) {
		
	}
	
	public void splitPaybackApply(PaybackApply paybackApply) {
		boolean success = false;
		try {
			paybackSplitService.splitApply(paybackApply,
					TargetWay.PAYMENT.getCode(), DeductTime.RIGHTNOW,
					DeductPlat.FUYOU);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("拆分富有划扣发生异常, paybackApplyId={}", new Object[]{paybackApply.getId()});
		} finally{
			if(success){
				addData2Queue(paybackApply);
			}
		}
	}

}
