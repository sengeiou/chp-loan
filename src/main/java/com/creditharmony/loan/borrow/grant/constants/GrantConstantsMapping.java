package com.creditharmony.loan.borrow.grant.constants;

import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.filenet.apiimpl.smm.Statistic;


/**
 * 放款流程节点映射
 * @Class Name GrantFlowNodeMapping
 * @author 张永生
 * @Create In 2016年4月22日
 */
public interface GrantConstantsMapping {
	/**
	 * 放款审核的状态值
	 */
	public static final String GRANT_AUDIT = LoanApplyStatus.LOAN_SEND_AUDITY.getCode();
	
	/**
	 * 放款的状态值
	 */
	public static final String GRANT_NAME = LoanApplyStatus.LOAN_TO_SEND.getCode();
	
	/**
	 * 分配卡号的状态值
	 */
	public static final String DIS_CARD = LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode();
}
