/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceContractCustInfoService.java
 * @Create By 张灏
 * @Create In 2015年12月1日 下午5:54:15
 */
package com.creditharmony.loan.borrow.contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 查询申请的客户信息
 * @Class Name ContractCustInfoService
 * @author 张灏
 * @Create In 2015年12月1日
 */
@Service("contractCustInfoService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ContractCustInfoService extends
		CoreManager<LoanCustomerDao, LoanCustomer> {

	@Autowired
	private LoanCustomerDao loanCustDao;

	/**
	 *根据ApplyId查询借款客户
	 *@author zhanghao
	 *@Create In 2016年12月20日
	 *@param applyId
	 *@return LoanCustomer 
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public LoanCustomer selectByApplyId(String applyId) {

		return loanCustDao.selectByApplyId(applyId);
	}
	 
	/**
     * 通过复议ApplyId 
     * @author zhanghao 
     * @Create In 2016年02月20日
     * @param applyId 
     * @return LoanCustomer
     * 
     */
    public LoanCustomer selectByReconsiderApplyId(String applyId){
       
        return loanCustDao.selectByReconsiderApplyId(applyId);
    }
}
