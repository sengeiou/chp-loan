package com.creditharmony.loan.borrow.contract.dao;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.HCAuditResultEntity;

@LoanBatisDao
public interface HCAuditResultDao {
	
    public int deleteByPrimaryKey(String id);

    public int insert(HCAuditResultEntity record);

    public int insertSelective(HCAuditResultEntity record);

    public HCAuditResultEntity selectByPrimaryKey(String id);

    public int updateByPrimaryKeySelective(HCAuditResultEntity record);

    public int updateByPrimaryKey(HCAuditResultEntity record);
    
    public HCAuditResultEntity searchOneByLoanCode(String loanCode);
}