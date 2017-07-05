package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;

/**
 * 还款用途申请service
 * @Class Name ApplyPayBackMethodDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface ApplyPaybackUseDao extends CrudDao<Payback>{	

	/**
	 * 还款用途申请列表计算当前期供信息
	 * 2015年12月20日
	 * By zhangfeng
	 * @param payback
	 * @return PaybackMonth
	 */
	public PaybackMonth findCurrentmonthAmount(Payback payback);

	/**
	 * 还款用途申请详细信息计算逾期期供信息
	 * 2015年12月21日
	 * By zhangfeng
	 * @param paybackApply
	 * @return PaybackMonth
	 */
	public PaybackMonth findOverdueMonthAmount(Payback payback);

	/**
	 * 保存冲抵信息
	 * 2016年1月6日
	 * By zhangfeng
	 * @param paybackCharge
	 * @return none
	 */
	public void insertOffset(PaybackCharge paybackCharge);

	/**
	 * 冲抵期供
	 * 2016年1月6日
	 * By zhangfeng
	 * @param paybackMonth
	 * @return int
	 */
	public int updateMonthsAmount(PaybackMonth paybackMonth);

	/**
	 * 查询期供（没有逾期）
	 * 2016年1月7日
	 * By zhangfeng
	 * @param payback
	 * @return list
	 */
	public List<PaybackMonth> getPaybackMonth(Payback payback);
	
	/**
	 * 查询期供（逾期）
	 * 2016年1月7日
	 * By zhangfeng
	 * @param paybackMonth
	 * @return list
	 */
	public List<PaybackMonth> getOverduePaybackMonth(PaybackMonth paybackMonth);

	/**
	 * 冲抵加锁
	 * @param map
	 * @return Payback
	 */
	public Payback getPaybackReq(Map<String, String> map);
}
