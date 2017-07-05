/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceContractFlagService.java
 * @Create By 张灏
 * @Create In 2015年11月30日 下午2:21:43
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.ContractFlagExDao;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractFlagEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 合同标识操作
 * @Class Name ContractFlagService
 * @author 张灏
 * @Create In 2015年11月30日
 */
@Service("contractFlagService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ContractFlagService extends
		CoreManager<ContractFlagExDao, ContractFlagEx> {
	
	@Autowired
	private ContractFlagExDao ctrFlagDao;
	
	/**
	 *查询合同审核阶段行的标记参数 
	 *@author zhanghao
     *@Create In 2015年12月20日
	 *@param workItems 
	 *@return  none
	 */
	public void setCtrFlag(List<LoanFlowWorkItemView> workItems){
		
		String stepName = workItems.get(0).getStepName();
		Map<String,Object> queryParam = new HashMap<String,Object>();
		queryParam.put("loanStatus", ContractConstant.LOAN_STATUS);
		if(ContractConstant.RATE_AUDIT.equals(stepName)){
		 queryParam.put("operateSteps",ContractConstant.OPERATE_STEP_TERMILATE);
		 this.setContractRateAuditFlag(workItems, queryParam);
		 
		}
		
	}

	/**
	 *利率审核阶段设置待办列表标记 
	 *@author zhanghao
	 *@Create In 2015年12月20日
	 *@param workItems
	 *@param param 
	 *@return none 
	 */
	@Transactional(readOnly=true,value = "loanTransactionManager")
	private void setContractRateAuditFlag(List<LoanFlowWorkItemView> workItems,Map<String,Object> param){
		String loanCode = null;
		ContractFlagEx contractFlagEx = null;
		List<String> loanStatusList = new ArrayList<String>();
		// 待审核利率
		loanStatusList.add(LoanApplyStatus.RATE_TO_VERIFY.getCode());  
		// 合同作废
		loanStatusList.add(LoanApplyStatus.CONTRACT_NULLIFY.getCode());
		// 待确认签署
		loanStatusList.add(LoanApplyStatus.SIGN_CONFIRM.getCode());
		// 待制作合同
		loanStatusList.add(LoanApplyStatus.CONTRACT_MAKE.getCode());
		// 合同制作中
		loanStatusList.add(LoanApplyStatus.CONTRACT_MAKING.getCode());
		// 待签订上传合同
		loanStatusList.add(LoanApplyStatus.CONTRACT_UPLOAD.getCode());
		// 待审核合同
		loanStatusList.add(LoanApplyStatus.CONTRACT_AUDIFY.getCode());
		// 待放款确认
		loanStatusList.add(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		// 待分配卡号
		loanStatusList.add(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
		// 待放款
		loanStatusList.add(LoanApplyStatus.LOAN_TO_SEND.getCode());
		// 待放款审核
		loanStatusList.add(LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
		// 已放款
		loanStatusList.add(LoanApplyStatus.LOAN_SENDED.getCode());
		// 放款审核退回
		loanStatusList.add(LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode());
		// 放款退回
		loanStatusList.add(LoanApplyStatus.LOAN_SEND_RETURN.getCode());
		// 放款确认退回
		loanStatusList.add(LoanApplyStatus.PAYMENT_BACK.getCode());
		// 还款中
		loanStatusList.add(LoanApplyStatus.REPAYMENT.getCode());
		// 逾期
		loanStatusList.add(LoanApplyStatus.OVERDUE.getCode());
		// 结清
		loanStatusList.add(LoanApplyStatus.SETTLE.getCode());
		// 提前结清
		loanStatusList.add(LoanApplyStatus.EARLY_SETTLE.getCode());
		// 提前结清待审核
		loanStatusList.add(LoanApplyStatus.EARLY_SETTLE_VERIFY.getCode());
		// 结清待确认
		loanStatusList.add(LoanApplyStatus.SETTLE_CONFIRM.getCode());
		param.put("targetStatus",LoanApplyStatus.RATE_TO_VERIFY.getCode());
		param.put("loanStatusList", loanStatusList);
		for(LoanFlowWorkItemView workItem: workItems){
			loanCode = workItem.getLoanCode();
			param.put("loanCode", loanCode);
			contractFlagEx = ctrFlagDao.isOld(param);   
			if(!ObjectHelper.isEmpty(contractFlagEx)){
			    if(StringUtils.isNotEmpty(contractFlagEx.getLoanCode()) && loanCode.equals(contractFlagEx.getLoanCode())){
			        workItem.setIsOld(YESNO.NO.getCode());  
			    }else{
			        workItem.setIsOld(YESNO.YES.getCode());
			    }
			}else{
			    workItem.setIsOld(YESNO.NO.getCode());
			}
		}
	}
	
}
