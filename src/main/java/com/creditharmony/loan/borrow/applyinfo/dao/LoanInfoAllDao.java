package com.creditharmony.loan.borrow.applyinfo.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfoAll;
/**
 * 
·* 2017年2月22日
·* by Huowenlong
 */
@LoanBatisDao
public interface LoanInfoAllDao extends CrudDao<LoanInfoAll>{
	
    public int deleteByPrimaryKey(String id);

    public int insert(LoanInfoAll record);

    public int insertSelective(LoanInfoAll record);

    public LoanInfoAll selectByPrimaryKey(String id);
    
    public LoanInfoAll searchLoanInfoByLoanCode(String loanCode);

    public int updateByPrimaryKeySelective(LoanInfoAll record);

    public int updateByPrimaryKey(LoanInfoAll record);
}