/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.eventLoanFlowUpdateCtrOperEx.java
 * @Create By 张灏
 * @Create In 2015年12月11日 上午11:38:55
 */
package com.creditharmony.loan.borrow.contract.event;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.trusteeship.view.TrusteeshipView;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;

/**
 * 审核人、审核时间回调方法
 * 
 * @Class Name LoanFlowUpdateAuditingEx
 * @author 申阿伟
 * @Create In 2016年11月4日
 */
@Service("ex_hj_loanflow_updateAuditing")
@Transactional(readOnly = false, value = "loanTransactionManager")
public class LoanFlowUpdateAuditingEx extends BaseService implements ExEvent {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoanFlowUpdateAuditingEx.class);

	@Autowired
	private ContractService contractService;

	@Override
	public void invoke(WorkItemView workItem) {
		String stepName = workItem.getStepName();
		Contract contract = new Contract();
		String response = workItem.getResponse();
		log.info(stepName + "节点获取response时错误。--------------invoke response is : " + response);
		if (ContractConstant.CTR_AUDIT.equals(stepName)) { // 合同审核
			ContractBusiView contractBusiView = (ContractBusiView) workItem.getBv();
			String operType = contractBusiView.getOperType();
			/**
			 * 正常提交、退回时operType 为空 更新渠道标识时 operType为0 更新冻结标识时operType为1
			 * 正常提交、退回才走下面的逻辑
			 */
			if (!YESNO.NO.getCode().equals(operType) && !YESNO.YES.getCode().equals(operType)) {
				contract = contractBusiView.getContract();
				User user = UserUtils.getUser();
				if (LoanFlowRoute.PAYCONFIRM.equals(response) || LoanFlowRoute.KING_OPEN.equals(response)) { // 放款确认、金帐户开户
					contract.setAuditingTime(new Date());
					contract.setAuditingBy(user.getLoginName());
					contractService.updateContractAuditing(contract);
				}
			}
		}else if(ContractConstant.KIND_ACCOUNT_OPEN.equals(stepName)){
			TrusteeshipView view = (TrusteeshipView)workItem.getBv();
			String operType = view.getOperateType();
			if (!YESNO.NO.getCode().equals(operType) && !YESNO.YES.getCode().equals(operType)) {
				if (LoanFlowRoute.CONTRACTCHECK.equals(response)) {
					contract.setLoanCode(view.getLoanCode());
					contract.setApplyId(view.getApplyId());
					contract.setAuditingTime(null);
					contract.setAuditingBy("");
					contractService.updateContractAuditing(contract);
				}
			}
		} else {
			GrantDealView gdv = (GrantDealView) workItem.getBv();
			String operType = gdv.getOperateType();
			if (!YESNO.NO.getCode().equals(operType) && !YESNO.YES.getCode().equals(operType)) {
				contract.setLoanCode(gdv.getLoanCode());
				contract.setContractCode(gdv.getContractCode());
				if (LoanFlowRoute.CONTRACTCHECK.equals(response) || LoanFlowRoute.GOLDCREDIT_RETURN.equals(response)) {
					contract.setAuditingTime(null);
					contract.setAuditingBy("");
					contractService.updateContractAuditing(contract);
				}
			}
		}

	}
}
