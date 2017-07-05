/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.dao
 * @Create By zhangfeng
 * @Create In 2015年12月11日 下午1:07:24
 */
package com.creditharmony.loan.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;

/**
 * 蓝补和蓝补操作历史Dao
 * @Class Name PaybackBlueAmountDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface PaybackBlueAmountDao extends CrudDao<PaybackBuleAmont>{

	/**
	 * 划扣汇款冲抵修改蓝补金额 
	 * 2015年12月29日
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */
	public void updateBuleAmount(Payback payback);

	/**
	 * 
	 * 2015年12月30日
	 * By zhangfeng
	 * @param paybackBuleAmount
	 * @return none
	 */
	public void insertPaybackBuleAmount(PaybackBuleAmont paybackBuleAmount);

}
