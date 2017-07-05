package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;


 /**
  * 结清已办列表
  * @Class Name PaybackDoneDao
  * @author 赵金平
  * @Create In 2016年5月30日
  */
@LoanBatisDao
public interface PaybackDoneDao extends CrudDao<Payback> {
	
	/**
	 * 结清已办列表
	 * 2016年5月30日
	 * By 赵金平
	 * @param payback
	 * @param pageBounds
	 * @return
	 */
	public List<Payback> findPayback(Payback payback,PageBounds pageBounds);
	
    /**
     * 结清已办列表
     * 2016年5月30日
     * By 赵金平
     * @param payback
     * @return
     */
	public List<Payback> findPayback(Payback payback);
	
	/**
	 * 查找期供信息
	 * @author 于飞
	 * @Create 2017年1月12日
	 * @param paybackMonth
	 * @return
	 */
	public PaybackMonth findPaybackMonthSum(PaybackMonth paybackMonth);
}
