package com.creditharmony.loan.borrow.applyinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;

/**
 * 
 * @Class Name LoanService
 * @author zhangerwei
 * @Create In 2015年12月25日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class LoanService extends CoreManager<LoanDao, LoanCoborrower> {
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;

	
	/**
	 * 
	 * 2015年12月25日
	 * By zhangerwei
	 * @param loanCode
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<LoanCoborrower> selectByLoanCode(String loanCode){
		
		return loanCoborrowerDao.selectByLoanCode(loanCode);
	}

	

	
	
}
