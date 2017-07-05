package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PhoneSale;

/**
 * 电销还款提醒待办dao
 * @Create In 2017年3月3日
 * @author 翁私
 *
 */

@LoanBatisDao
public interface PhoneSaleHandleDao extends CrudDao<PhoneSale>{

	/**
	 * 查询分页查询电销还款待办数据
	 * @param page
	 * @param sale
	 * @return
	 */
	PageList<PhoneSale> queryPage(PageBounds pageBounds, PhoneSale sale);
	
	/**
	 * 查询提醒待办列表
	 * @param sale
	 * @return
	 */
	List<PhoneSale> queryPage(PhoneSale sale);

	/**
	 * 
	 * @param sale
	 */
	void signRemindupdate(PhoneSale sale);

	/**
	 * 插入历史
	 * @param paybackOpe
	 */
	void insertRemindOpe(PaybackOpe paybackOpe);

	/**
	 * 查询提醒历史
	 * @param filter
	 * @param pageBounds
	 * @return
	 */
	List<PaybackOpe> getPaybackRemindOpe(Map<String, String> filter,
			PageBounds pageBounds);

	/**
	 * 查询提醒 list
	 * @param bean
	 * @return
	 */
	List<PhoneSale> queryList(PhoneSale bean);

}
