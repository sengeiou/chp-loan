package com.creditharmony.loan.yunwei.operate.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.yunwei.operate.entity.OperateObj;

@LoanBatisDao
public interface OperateDataDao extends CrudDao<OperateObj> {
	
	public String selectLoanSystemLog();

	public void updateLoanSystemLog(Map<String, Object> loanSystemLog);

	public void insertLoanSystemLog(String uid);
}
