package com.creditharmony.loan.borrow.consult.constats;

/**
 * 咨询规则返回错误信息
 * 
 * @author 任志远
 * @date 2017年3月16日
 */
public enum ConsultRuleResultCode {

	/**
	 * 身份证和系统中存在的尚未结清的最优自然人保证人相同,请结清后进行借款
	 */
	CUSTOMER_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA("409", "身份证和系统中存在的尚未结清的最优自然人保证人相同,请结清后进行借款"),
	/**
	 * 身份证和系统中存在的尚未结清的共借人相同,请结清后进行借款
	 */
	CUSTOMER_IN_UNSETTLE_COBO_DATA("408", "身份证和系统中存在的尚未结清的共借人相同,请结清后进行借款"),
	/**
	 * 身份证和系统中存在的尚未结清的主借人配偶相同,请结清后进行借款
	 */
	CUSTOMER_IN_UNSETTLE_MATE_DATA("407", "身份证和系统中存在的尚未结清的主借人配偶相同,请结清后进行借款"),
	/**
	 * 电销已取单,请录入申请
	 */
	GET_ORDER_MESSAGE("406", "电销已取单,请录入申请"),
	/**
	 * 汇金门店未取单,请取单
	 */
	NO_GET_ORDER_MESSAGE("405", "汇金门店未取单,请取单"),
	/**
	 * 黑名单客户，不允许进件
	 */
	BLACK_LOG_MESSAGE("404", "黑名单客户，不允许进件"),
	/**
	 * 该客户被门店拒绝，1个月内不允许进件
	 */
	STORE_REJECT_MESSAGE("403", "该客户被门店拒绝，1个月内不允许进件"),
	/**
	 * 该客户在信审阶段被拒绝，6个月内不允许进件
	 */
	AUDIT_REJECT_MESSAGE("402", "该客户在信审阶段被拒绝，6个月内不允许进件"),
	/**
	 * 该客户的借款没有结束，不允许重新进件
	 */
	PROCESSING_MESSAGE("401", "该客户的借款没有结束，不允许重新进件"),
	/**
	 * 该客户已经咨询，不允许再次咨询
	 */
	CONSULT_MESSAGE("400", "该客户已经咨询，不允许再次咨询"),
	/**
	 * 成功
	 */
	SUCCESS("201", "");

	ConsultRuleResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 返回code
	 */
	private String code;
	/**
	 * 返回信息
	 */
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
