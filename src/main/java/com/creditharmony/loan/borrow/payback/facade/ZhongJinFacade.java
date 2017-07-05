package com.creditharmony.loan.borrow.payback.facade;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.thread.MessagerProcessor;
import com.creditharmony.core.thread.ProcessorHandler;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;
import com.creditharmony.loan.borrow.payback.service.PaybackSplitService;
/**
 * 中金集中划扣导入 facade
 * @author ws
 *
 */
@Component
public class ZhongJinFacade implements ProcessorHandler<List<ImportEntity>>{
protected Logger logger = LoggerFactory.getLogger(ZhongJinFacade.class);
	
	@Autowired
	private PaybackSplitService paybackSplitService;
	
	private LinkedBlockingQueue<List<ImportEntity>> queue = new LinkedBlockingQueue<List<ImportEntity>>();
	private MessagerProcessor<List<ImportEntity>> processor = new MessagerProcessor<List<ImportEntity>>(
			queue, this, Executors.newFixedThreadPool(8));
	private boolean start = true;
	private final ExecutorService executor = Executors.newFixedThreadPool(8);
	

	public void addData2Queue(List<ImportEntity> deductPaybackList) {
		if(start){
			processor.start();
			start = false;
		}
		queue.add(deductPaybackList);
	}
	
	@Override
	public void run(List<ImportEntity> deductPaybackList) {
		updateZjResult(deductPaybackList);
	}
	
	@SuppressWarnings({ "finally", "unused" })
	public String submitData(Map<String, List<ImportEntity>> deductMap) {
		StringBuffer message = new StringBuffer();
		CompletionService<List<ImportEntity>> completionService = new ExecutorCompletionService<List<ImportEntity>>(
				executor);
		for (String key : deductMap.keySet()) {
			final List<ImportEntity> deductList = deductMap.get(key);
			completionService.submit(new Callable<List<ImportEntity>>() {
				public List<ImportEntity> call() {
					return updateZjResult(deductList);
				}
			});
		}
		int successNum = 0;
		int failNum = 0;
		try {
			for (String key : deductMap.keySet()) {
				Future<List<ImportEntity>> future = completionService.take();
				List<ImportEntity> list = future.get();
				for(ImportEntity deduct : list){
					if(deduct.isSuccess()){
						successNum++;
					}else{
						failNum++;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} finally{
			if (successNum > 0) {
				message.append("成功条数：");
				message.append(successNum);
				message.append("。");
			}
			if (failNum > 0) {
				message.append("失败条数：");
				message.append(failNum);
				message.append("。");
			}
			return message.toString();
		}
		
	}
	public List<ImportEntity> updateZjResult(
			final List<ImportEntity> deductList) {
		//for (ImportEntity deductItem : deductList) {
			try {
				paybackSplitService.updateDeductResult(deductList,DeductPlat.ZHONGJIN.getCode());
				//deductItem.setSuccess(true);
			} catch (Exception e) {
				//deductItem.setSuccess(false);
				e.printStackTrace();
				logger.error("");
		//	}
		}
		return deductList;
	}

}
