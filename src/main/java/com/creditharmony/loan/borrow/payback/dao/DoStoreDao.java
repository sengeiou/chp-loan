package com.creditharmony.loan.borrow.payback.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
/**
 * 门店待办业务处理Dao
 * @Class Name DoStoreDao
 * @author zhangfeng
 * @Create In 2016年1月6日
 */
@LoanBatisDao
public interface DoStoreDao extends CrudDao<PaybackApply>{
	
	/**
	 * 门店待办更新还款申请信息
	 * 2015年12月29日
	 * By zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	public int updateApplyPayback(PaybackApply paybackApply);

	/**
	 * 门店待办更新汇款信息
	 * 2015年12月29日
	 * By zhangfeng
	 * @param payBackTransferInfo
	 * @return none
	 */
	public void deletePaybackTransferInfo(PaybackTransferInfo payBackTransferInfo);

	/**
	 * 更改账户信息(删除在插入)
	 * 2016年1月6日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 * @return none
	 */
	public void insertPayBackTransferInfo(PaybackTransferInfo paybackTransferInfo);

	/***
	 * 获取退回提前结清冲抵申请任务
	 * 2016年1月11日
	 * By zhangfeng
	 * @param paybackCharge
	 * @return PaybackCharge
	 */
	public PaybackCharge getPaybackChargeList(PaybackCharge paybackCharge);
}
