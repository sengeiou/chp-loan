/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.workFlow.constsLoanFlowQueue.java
 * @Create By 王彬彬
 * @Create In 2016年1月22日 下午8:36:55
 */
package com.creditharmony.loan.common.workFlow.consts;

/**
 * 信借流程工作队列
 * @Class Name LoanFlowQueue
 * @author 王彬彬
 * @Create In 2016年1月22日
 */
public class LoanFlowQueue {
	
	/**
	 * 数据管理部（结算专员）
	 */
	public static String BALANCE_COMMISSIONER ="HJ_BALANCE_COMMISSIONER";
	
	/**
	 * 合同审核
	 */
	public static String CONTRACT_CHECK ="HJ_CONTRACT_CHECK";

	/**
	 * 借款人服务部（合同制作专员）
	 */
	public static String CONTRACT_COMMISSIONER = "HJ_CONTRACT_COMMISSIONER";

	/**
	 * 门店客服 
	 */
	public static String CUSTOMER_AGENT ="HJ_CUSTOMER_AGENT";

	/**
	 * 数据管理部（划扣专员）
	 */
	public static String DEDUCTION_COMMISSIONER ="HJ_DEDUCTION_COMMISSIONER";

	/**
	 * 数据管理部（放款结算专员）
	 */
	public static String LOAN_BALANCE_COMMISSIONER ="HJ_LOAN_BALANCE_COMMISSIONER";

	/**
	 * 数据管理部（放款结算主管）
	 */
	public static String LOAN_BALANCE_MANAGER ="HJ_LOAN_BALANCE_MANAGER";

	/**
	 * 利率审核
	 */
	public static String RATE_CHECK ="HJ_RATE_CHECK";

	/**
	 * 借款人服务部（还款专员）
	 */
	public static String REPAYMENT_COMMISSIONER ="HJ_REPAYMENT_COMMISSIONER";

	/**
	 * 借款人服务部（款项统计专员)
	 */
	public static String STATISTICS_COMMISSIONER ="HJ_STATISTICS_COMMISSIONER";
	
	/**
	 * 门店（副理）
	 */
	public static String SUB_MANAGER ="HJ_SUB_MANAGER";

	/**
	 * 门店（外访专员）
	 */
	public static String VISIT_COMMISSIONER ="HJ_VISIT_COMMISSIONER";
	/**
	 * 金信债权退回
	 */
	public static String LIABILITIES_RETURN ="HJ_LIABILITIES_RETURN";
	/**
	 * 金账户开户
	 */
	public static String KING_OPEN ="HJ_KING_OPEN";
}
