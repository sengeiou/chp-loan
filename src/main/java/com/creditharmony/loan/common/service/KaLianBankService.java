package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.entity.LoanBank;

@Service
public class KaLianBankService {
	  @Autowired
	  private 	LoanBankDao  loanBankDao;
	  
	  public List<LoanBank> getBankList(String loanCode){
		 return  loanBankDao.queryCertification(loanCode);
	  }
  
	  /**
	   * 修改流水号，实名认证状态
	   * @param loanCode
	   */
	  public void updateBankByLoanCode(LoanBank bank) {
		  loanBankDao.updateBankByLoanCode(bank);
	 }

	public List<LoanBank> queryCertificationById(String oldAccountId) {
		// TODO Auto-generated method stub
		return loanBankDao.queryCertificationById(oldAccountId);
	}

	public void updateBankById(LoanBank bankquery) {
		loanBankDao.updateBankById(bankquery);
		
	}
}
