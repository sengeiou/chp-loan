package com.creditharmony.loan.deduct.handler;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;

/**
 * 划扣结果处理类
 * @Class Name DeductResultHandler
 * @author 张永生
 * @Create In 2016年5月21日
 */
public abstract class DeductResultHandler {

	/**
	 * 处理业务
	 * 2016年5月21日
	 * By 张永生
	 * @param deduct
	 */
	public abstract void handleBusiness(LoanDeductEntity deduct);
	
}
