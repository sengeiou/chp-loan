package com.creditharmony.loan.borrow.contractAudit.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contractAudit.dao.AssistDao;
import com.creditharmony.loan.borrow.contractAudit.entity.Assist;

@Service
public class AssistService extends CoreManager<AssistDao, Assist> {
	@Resource
	private AssistDao assistDao;
	/**
	 * 设置为待分单状态
	 * @param loanCode
	 */
	public void updateAssistAddAuditOperator(String loanCode){
		Assist assist = new Assist();
		assist.setLoan_code(loanCode);
		assist.setIf_dispatch("0");
		assistDao.updateAuditOperator(assist);
	};
	
	public void updateLoanInfoAssist(Assist assist) {
		assistDao.updateAssist(assist);
	};
}
