package com.creditharmony.loan.borrow.contractAudit.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditBanli;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditDatas;

@LoanBatisDao
public interface ContractAuditModuleDao extends CrudDao<ContractAuditDatas>{

	ContractAuditBanli getContractAudit(ContractAuditDatas ctrQryParam);
}
