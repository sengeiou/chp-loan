/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.service
 * @Create By zhangfeng
 * @Create In 2015年12月11日 下午1:07:24
 */
package com.creditharmony.loan.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.entity.LoanBank;

/**
 * 更新还款划扣账户公共service
 * @Class Name LoanDeductService
 * @author zhangfeng
 * @Create In 2015年12月22日
 */
@Service("LoanBankService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class LoanDeductService extends CoreManager<LoanBankDao, LoanBank> {

	@Autowired
	LoanBankDao loanBankDao;
	
	/**
	 * 更新借款账户信息表 
	 * 2016年1月6日
	 * By zhangfeng
	 * @param loanBank
	 * return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateTopFlag(LoanBank loanBank) {
		dao.updateTopFlag(loanBank);
	}

	/**
	 * 分页查询支行
	 * @param page
	 * @param bank
	 * @return
	 */
	public Page<LoanBank> findBankPage(Page<LoanBank> page, LoanBank bank) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<LoanBank> pageList = (PageList<LoanBank>) loanBankDao.findBanklist(bank, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}
