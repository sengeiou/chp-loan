package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeductsDue;

/** 
 * 预约划扣列表业务Dao
 * @Class Name PaybackDeductsDueDao
 * @author zhaojinping
 * @Create In 2015年12月16日
 */
@LoanBatisDao
public interface PaybackDeductsDueDao extends CrudDao<PaybackDeductsDue> {
	
	/**
	 * 查询预约划扣时间列表
	 * 2015年11月30日
	 * By zhaojinping
	 * @param pageBounds
	 * @param paramMap
	 * @return list
	 */
	public List<PaybackDeductsDue> getDeductsDue(PageBounds pageBounds,PaybackDeductsDue paybackDeductsDue);

	/**
	 * 将预约划扣时间置为有效
	 * 2016年1月5日
	 * By zhaojinping
	 * @param paybackDeductsDue
	 * @return none
	 */
	public void updateUse(PaybackDeductsDue paybackDeductsDue);
	
	/**
	 * 将预约划扣时间置为无效
	 * 2016年1月5日
	 * By zhaojinping
	 * @param paybackDeductsDue
	 * @return none
	 */
	public void updateUnUse(PaybackDeductsDue paybackDeductsDue);
	
	/**
	 * 新增预约划扣时间
	 * 2015年12月12日
	 * By zhaojinping
	 * @param paybackDeductsDue
	 * @return none
	 */
	public void addDue(PaybackDeductsDue paybackDeductsDue);
	
	/**
	 * 查询预约划扣时间列表
	 * 2015年11月30日
	 * By zhaojinping
	 * @param pageBounds
	 * @param paramMap
	 * @return list
	 */
	public List<PaybackDeductsDue> getDeductsDue(PaybackDeductsDue paybackDeductsDue);
}
