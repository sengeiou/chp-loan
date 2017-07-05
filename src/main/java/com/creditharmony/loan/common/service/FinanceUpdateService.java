package com.creditharmony.loan.common.service;

import java.util.List;

import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;

/**
 * 划扣放款使用服务
 * 
 * @Class Name DeductUpdateService
 * @author 王彬彬
 * @Create In 2016年2月2日
 */
public interface FinanceUpdateService {


	/**
	 * 集中划扣更新 2016年2月2日 By 王彬彬
	 * 
	 * @param deductList
	 *            划扣返回结果
	 */
	public void updateSplit(List<LoanDeductEntity> deductList);
}
