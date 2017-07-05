package com.creditharmony.loan.deduct.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.deduct.remote.LoanDeductNotifyService;

/**
 * 划扣通知代理类
 * 用途：用于实现划扣通知接口，接收清算系统chp-finance发送的划扣结果
 * @Class Name DeductNotifyProxy
 * @author 张永生
 * @Create In 2016年5月20日
 */
@Component
public class DeductNotifyProxy implements LoanDeductNotifyService {

	private static final Logger logger = LoggerFactory.getLogger(DeductNotifyProxy.class);

	@Autowired
	private DeductResultService deductResultService;
	
	public boolean notifyDeductResult(LoanDeductEntity deduct) {
		try {
			//插入更新划扣回盘信息(拆分划扣详细更新,集中、非集中划扣使用)
			logger.info(
					"【汇金系统】划扣结果返回,业务更新开始,requestId={},businessId={},batId={}",
					new Object[] { deduct.getRequestId(),
							deduct.getBusinessId(), deduct.getBatId() });
			deductResultService.saveDeductResult(deduct);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入批处理返回数据报错！");
		}
		logger.info("插入批处理返回数据结束 ");
		return false;
	}

}
