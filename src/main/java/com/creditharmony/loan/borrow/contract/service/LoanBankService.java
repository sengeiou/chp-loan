/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceLoanBankService.java
 * @Create By 张灏
 * @Create In 2015年11月27日 下午2:33:01
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.entity.LoanBank;

/**
 * 银行信息Service
 * @Class Name LoanBankService
 * @author 张灏
 * @Create In 2015年11月27日
 */
@Service("loanBankService")
@Transactional(readOnly=true,value = "loanTransactionManager")
public class LoanBankService extends CoreManager<LoanBankDao, LoanBank>{
   
	@Autowired
	private LoanBankDao loanBankDao;
	
    /**
	 *通过主键查询  
	 *@author zhanghao
	 *@Create In 2016年03月11日
	 *@param id 
	 *@return LoanBank 
	 */
	@Transactional(readOnly=true,value = "loanTransactionManager")
	public LoanBank selectByPrimaryKey(String id){
		  
	   return loanBankDao.selectByPrimaryKey(id);
	}
	    
	/**
	 *通过开户行查找银行信息 
	 *@author zhanghao
	 *@Create In 2016年2月1日
	 *@param depositBank
	 *@param loanCode  
	 *@return List<LoanBank> 
	 */
	@Transactional(readOnly=true,value = "loanTransactionManager")
	public List<LoanBank> selectByDepositBank(String depositBank,String loanCode){
	   LoanBank loanBank = new LoanBank(); 
	   loanBank.setBankName(depositBank);
	   loanBank.setLoanCode(loanCode);
	   return loanBankDao.findAllList(loanBank);
	   
	}
	/**
	 *通过 借款编号查询开户行 
	 *@author zhanghao
	 *@Create In 2016年2月1日
	 *@param loanCode
	 *@return List<LoanBank>
	 */
	@Transactional(readOnly=true,value = "loanTransactionManager")
	public List<LoanBank> selectByLoanCode(String loanCode,Integer topFlag){
	   LoanBank loanBank = new LoanBank(); 
	   loanBank.setLoanCode(loanCode);
	   loanBank.setBankTopFlag(topFlag);
	   return loanBankDao.findAllList(loanBank);
	}

	/**
	 *更新借款账户信息表 
	 *@author zhanghao
	 *@Create In 2016年2月1日
	 *@param record
	 *@return none
	 */
	@Transactional(readOnly=false,value = "loanTransactionManager")
	public void updateByPrimaryKeySelective(LoanBank record){
	  loanBankDao.updateByPrimaryKeySelective(record);
	}
}
