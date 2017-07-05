package com.creditharmony.loan.borrow.payback.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.RemindRepaymentAgencyDao;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;

/**
 * @Class Service支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年11月30日
 */

@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class RemindRepaymentAgencyService {
	@Autowired
	private RemindRepaymentAgencyDao remindRepaymentAgencyDao;

	/**
	 * 提醒还款代办列表
	 * 2015年12月17日
	 * By 李强
	 * @param page
	 * @param repaymentReminder
	 * @return 提醒还款待办信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<RepaymentReminderEx> allRemindRepaymentAgencyList(Page<RepaymentReminderEx> page,
			RepaymentReminderEx repaymentReminder) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<RepaymentReminderEx> pageList = (PageList<RepaymentReminderEx>)remindRepaymentAgencyDao.allRemindRepaymentAgencyList(pageBounds,repaymentReminder);
		PageUtil.convertPage(pageList, page);		
		return page;
	};
	
	/**
	 * 添加门店备注
	 * 2015年12月30日
	 * By lirui
	 * @param rre 门店备注信息
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void addRemark(RepaymentReminderEx rre) {
		// 获取当前登录人Code作为门店备注人
		rre.setStoreRemarkUserid(UserUtils.getUser().getUserCode());
		remindRepaymentAgencyDao.addRemark(rre);
	}
}
