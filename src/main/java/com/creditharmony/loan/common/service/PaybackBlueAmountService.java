package com.creditharmony.loan.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.common.dao.PaybackBlueAmountDao;

/**
 * 蓝补和蓝补操作历史service
 * @Class Name PaybackBlueAmountService
 * @author zhangfeng
 * @Create In 2015年12月22日
 */
@Service("PaybackBlueAmountService")
public class PaybackBlueAmountService extends CoreManager<PaybackBlueAmountDao, PaybackBuleAmont> {

	 /**
     * 划扣汇款冲抵修改蓝补金额 
     * 2015年12月26日
     * By zhaojinping
     * @param payback
     * @return none
     */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateBuleAmount(Payback payback) {
		dao.updateBuleAmount(payback);
	}
	
    /**
     * 蓝补交易明细表添加记录
     * 2015年12月30日
     * By zhangfeng
     * @param paybackBuleAmount
     * @return none
     */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertPaybackBuleAmount(PaybackBuleAmont paybackBuleAmount) {
		dao.insertPaybackBuleAmount(paybackBuleAmount);
	}
}
