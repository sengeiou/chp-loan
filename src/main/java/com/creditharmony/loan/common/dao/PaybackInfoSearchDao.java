/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.dao
 * @Create By zhangfeng
 * @Create In 2015年12月11日 下午1:07:24
 */
package com.creditharmony.loan.common.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 还款业务查询列表公共Dao
 * @Class Name PaybackDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface PaybackInfoSearchDao extends CrudDao<PaybackApply>{
	
	/**
	 * 检索还款查询信息列表
	 * 
	 * @param payback
	 * @param pageBounds
	 * @return list
	 */
	public List<Payback> selApplyPaybackUse(Payback payback, PageBounds pageBounds);
	
	/**
	 * 检索还款查询信息列表的count
	 * 2017年4月14日
	 * By 朱静越
	 * @param payback
	 * @return
	 */
	public int selApplyPaybackUseCnt(Payback payback);
}
