package com.creditharmony.loan.borrow.contractAudit.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contractAudit.entity.Assist;

@LoanBatisDao
public interface AssistDao extends CrudDao<Assist>{ 
	
	void updateAssist(Assist assist);

	void updateAuditOperator(Assist assist);
}
