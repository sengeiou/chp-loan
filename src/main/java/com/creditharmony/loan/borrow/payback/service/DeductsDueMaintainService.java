package com.creditharmony.loan.borrow.payback.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.DeductsDueMaintainDao;
import com.creditharmony.loan.borrow.payback.entity.BankFlatMaintain;

/**
 * 预约银行及时间列表维护业务Service
 * @Class Name DeductsDueMaintainService
 * @author zhaojinping
 * @Create In 2015年12月16日
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class DeductsDueMaintainService {

	@Autowired
	private DeductsDueMaintainDao deductsDueMaintainDao;
	
	/**
	 * 获取预约划扣列表
	 * 2015年12月12日
	 * @param page
	 * @param pabackDeductsDue
	 * By zhaojinping
	 * @return page
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public Page<BankFlatMaintain> getDeductsDue(Page<BankFlatMaintain> page,BankFlatMaintain pabackDeductsDue){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<BankFlatMaintain> pageList = (PageList<BankFlatMaintain>)deductsDueMaintainDao.getDeductsDue(pageBounds,pabackDeductsDue);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 将划扣方式由实时改为批量
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void realBatch(String id){
		deductsDueMaintainDao.realBatch(id);
	}
	
	/**
	 * 将划扣方式由批量改为实时
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void batchReal(String id){
		deductsDueMaintainDao.batchReal(id);
	}

	/**
	 * 批量更新划扣方式
	 * 2015年3月8日
	 * By wengsi
	 * @param id
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void batchUpdate(BankFlatMaintain bank) {
		bank.preUpdate();
		deductsDueMaintainDao.batchUpdate(bank);
	}
	
}
