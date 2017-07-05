package com.creditharmony.loan.borrow.contractAudit.service;

import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditBanli;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditDatas;

@Service
public interface IContractAuditModuleService{
 
	/**
	 * 合同审核列表 
	 */
	public Page<ContractAuditDatas> searchContractAuditDatas(ContractAuditDatas ctrQryParam,Page<ContractAuditDatas> page);

	/**
	 * 合同审核办理页面
	 * @return
	 */
	public ContractAuditBanli getOneContractAudit(ContractAuditDatas ctrQryParam);

	public BaseBusinessView commitContractAudit( WorkItemView workItem,ContractBusiView busiView);

	public void updateFrozenStatus(ContractBusiView ctrView);
	 
}
