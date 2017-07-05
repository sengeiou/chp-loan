package com.creditharmony.loan.borrow.payback.dao;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;

/**
 * 提前结清待办业务处理Dao
 * @Class Name DoAdvanceSettledDao
 * @author zhangfeng
 * @Create In 2015年11月27日
 */
@LoanBatisDao
public interface DoAdvanceSettledDao extends CrudDao<PaybackCharge>{
   
	/**
	 * 更新提前结清门店待办提交的提前结清申请资料
	 * 2016年3月1日
	 * By zhaojinping
	 * @param paybackCharge
	 */
	public void updatePaybackCharge (PaybackCharge paybackCharge);
	
	/**
	 * 更新还款主表中的还款状态
	 * 2016年3月2日
	 * By zhaojinping
	 * @param payback
	 * @return none
	 */
	public void updatePaybackStatus(Payback payback);

	/**
	 * 门店提前结清待办获取任务
	 * 2016年3月10日
	 * By zhangfeng
	 * @param paybackCharge
	 * @param pageBounds
	 * @return page
	 */
	public PageList<PaybackCharge> findPaybackCharge(PaybackCharge paybackCharge, PageBounds pageBounds);
    
	/**
	 * 门店提前结清待办列表点击办理查询详情信息
	 * 2016年5月5日
	 * By 赵金平
	 * @param paybackCharge
	 * @return
	 */
	public PaybackCharge findPaybackCharge(PaybackCharge paybackCharge);
	
	/**
	 * 更新冲抵申请表中的冲抵状态为‘冲抵失败’
	 * 2016年5月6日
	 * By 赵金平
	 * @param paybackCharge
	 */
	public void giveUpStatus(PaybackCharge paybackCharge);
}
