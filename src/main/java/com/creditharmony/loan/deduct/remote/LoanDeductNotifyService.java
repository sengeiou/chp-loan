package com.creditharmony.loan.deduct.remote;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;

/**
 * 划扣通知服务接口
 * 用途：提供给remote端的清算系统chp-finance调用
 * chp-finance调用该接口，发送划扣结果至汇金系统
 * @Class Name DeductNotifyService
 * @author 张永生
 * @Create In 2016年5月20日
 */
public interface LoanDeductNotifyService {

	
	/**
	 * 通知划扣结果
	 * 2016年5月20日
	 * By 张永生
	 * @param deduct
	 * @return
	 */
	public boolean notifyDeductResult(LoanDeductEntity deduct);
	
}
