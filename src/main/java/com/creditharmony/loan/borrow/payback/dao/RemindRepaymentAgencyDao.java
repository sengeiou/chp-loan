package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;

/**
 * @Class Dao支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年11月30日
 */

@LoanBatisDao
public interface RemindRepaymentAgencyDao extends
		CrudDao<RemindRepaymentAgencyDao> {

	/**
	 * 查询 提醒还款代办列表信息
	 * 2015年12月17日
	 * By 李强
	 * @param pageBounds
	 * @param repaymentReminder
	 * @return 提醒还款待办集合
	 */
	public List<RepaymentReminderEx> allRemindRepaymentAgencyList(PageBounds pageBounds,
			RepaymentReminderEx repaymentReminder);

	/**
	 * 添加门店备注
	 * 2015年12月30日
	 * By lirui
	 * @param rre 门店备注存放容器
	 * @return none
	 */
	public void addRemark(RepaymentReminderEx rre);
}
