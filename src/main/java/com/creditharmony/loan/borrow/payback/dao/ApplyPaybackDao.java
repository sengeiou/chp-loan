package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantLimit;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.common.entity.LoanBank;

/**
 * 发起还款申请业务处理Dao
 * @Class Name ApplyPaybackDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface ApplyPaybackDao extends CrudDao<PaybackApply>{

	/**
	 * 根据借款编码查询客户账户信息
	 * 2015年12月2日
	 * By zhangfeng
	 * @param loanCode
	 * @return list
	 */
	public List<LoanBank> findCustomerByLoanCode(@Param("loanCode")String loanCode,@Param("id")String id);

	/**
	 * 保存还款申请信息(账户信息)
	 * 2015年12月2日
	 * By zhangfeng
	 * @param info
	 */
	public void savePayBackTransferInfo(PaybackTransferInfo info);

	/**
	 * 保存还款申请信息
	 * 2015年12月2日
	 * By zhangfeng
	 * @param paybackApply
	 */
	public void saveApplyPayback(PaybackApply paybackApply);

	/**
	 * 查询该合同是否有POS机合同编号
	 * 2015年12月2日
	 * By guanhongchang
	 * @param paybackApply
	 */
	public List<PaybackApply> selectPosOrder(PaybackApply paybackApply);

	
	/**
	 * 保存POS申请信息
	 * 2016年2月26日
	 * By zhangfeng
	 * @param paybackApply
	 */
	public void savePayPosCardInfo(Payback payback);

	/**
	 * 修改POS后台数据列表信息
	 * 2016年2月26日
	 * By zhangfeng
	 * @param paybackApply
	 */
	public void updateAppPos(Payback payback);

	/**
	 * 查询门店限制条件
	 * @param org
	 * @return
	 */
	public List<DeductPlantLimit> queryDeductCondition(Org org);

	/**
	 * 查询统计信息
	 * @param org
	 * @return
	 */
	public List<DeductStatistics> queryDeductStatistics(DeductStatistics ts);
}
