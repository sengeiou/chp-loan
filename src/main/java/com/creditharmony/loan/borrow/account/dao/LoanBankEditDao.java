package com.creditharmony.loan.borrow.account.dao;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.account.entity.LoanBankEditEntity;

/**
 * 
·* 2016年11月19日
·* by Huowenlong
 */
@LoanBatisDao
public interface LoanBankEditDao {
    public int deleteByPrimaryKey(String id);

    public int insert(LoanBankEditEntity record);

    public int insertSelective(LoanBankEditEntity record);

    public LoanBankEditEntity selectByPrimaryKey(String id);

    public int updateByPrimaryKeySelective(LoanBankEditEntity record);

    public int updateByPrimaryKey(LoanBankEditEntity record);
    
    public LoanBankEditEntity searchByLoanCode(String loanCode);
}