package com.creditharmony.loan.borrow.applyinfo.dao;


import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 
 * @Class Name LoanDao
 * @author zhangerwei
 * @Create In 2015年12月25日
 */
@LoanBatisDao
public interface LoanDao extends CrudDao<LoanCoborrower> {

	/**
	 * 
	 * 2015年12月29日
	 * By zhangerwei
	 * @param loanCode
	 * @return
	 */
	
	public LoanCustomer selectByLoanCode(String loanCode);
	
	/**
	 * 
	 * 2015年12月25日
	 * By zhangerwei
	 * @param rcustomerCoborrowerId
	 * @return
	 */
	
	public LoanCoborrower findAllByRcustomerCoborrowerId(String rcustomerCoborrowerId);
}
