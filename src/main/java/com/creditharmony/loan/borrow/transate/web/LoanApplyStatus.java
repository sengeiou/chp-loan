/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsLoanStatus.java
 * @Create By 王彬彬
 * @Create In 2015年12月17日 下午2:31:17
 */
package com.creditharmony.loan.borrow.transate.web;

import java.util.HashMap;
import java.util.Map;

/**
 * （借款申请状态）
 * 
 * @Class Name LoanApplyStatus
 * @author 王彬彬
 * @Create In 2015年12月17日
 */
public enum LoanApplyStatus {
	INFORMATION_UPLOAD("0", "待上传资料",""),

	STORE_REVERIFY("1", "待门店复核",""),

	RULE_ENGINE("2", "待汇诚审核",""),

	STORE_ALLOT_VISIT("17", "待门店分配外访",""),

	STORE_VISIT("18", "待门店外访",""),

	BACK_STORE("21", "退回门店",""),

	RECONSIDER_BACK_STORE("41", "复议退回门店",""),

	STORE_VISIT_TIMEOUT("54", "门店外访超时",""),

	RECONSIDER_STORE_VISIT_TIMEOUT("56", "复议门店外访超时",""),

	RATE_TO_VERIFY("58", "待审核费率",""),

	CONTRACT_NULLIFY("59", "合同作废",""),

	SIGN_CONFIRM("60", "待确认签署",""),

	CONTRACT_MAKE("61", "待制作合同",""),

	CONTRACT_MAKING("62", "合同制作中","1"),

	CONTRACT_UPLOAD("63", "待上传合同","1"),

	CONTRACT_AUDIFY("64", "待审核合同","1"),

	LOAN_SEND_CONFIRM("65", "待款项确认","1"),

	LOAN_CARD_DISTRIBUTE("66", "待分配卡号","1"),

	LOAN_TO_SEND("67", "待放款","1"),

	LOAN_SEND_AUDITY("69", "放款中","1"),

	LOAN_SENDED("70", "已放款","1"),

	LOAN_SEND_AUDITYRETURN("71", "放款失败",""),

	LOAN_SEND_RETURN("72", "放款退回",""),

	STORE_REJECT("73", "门店拒绝",""),

	CUSTOMER_GIVEUP("74", "客户放弃",""),

	PAYMENT_BACK("75", "待款项确认退回",""),

	APPLY_ENGINE_REFUSE("81", "规则引擎拒借",""),

	APPLY_ENGINE_BACK("82", "规则引擎退回",""),
	
	APPLY_ENGINE_ABANDON("811", "规则引擎放弃",""),

	CONTRACT_BACK("83", "合同审核退回",""),

	RARE_BACK("84", "审核费率退回",""),

	MAKE_BACK("85", "合同制作退回",""),

	SIGN_BACK("86", "合同签订退回",""),
	
	REPAYMENT("87","还款中","1"),
	
	OVERDUE("88","逾期","1"),
	
	SETTLE("89","结清","1"),
	
	EARLY_SETTLE("90","提前结清","1"),
	
	EARLY_SETTLE_VERIFY("91","提前结清待审核","1"),
	
	SETTLE_CONFIRM("92","结清待确认","1"),
	
	KING_RETURN("93","金账户退回","1"),
	
	GOLDCREDIT_REJECT("94","金信拒绝",""),
	
	GOLDCREDIT_RETURN("95","金信退回",""),
	
	KING_TO_OPEN("96","金账户待开户","1"),
	
	SIGN_TIMEOUT("97","签约超时",""),
	
	GOLDCREDIT_RIGHT_RETURN("98","金信债权退回","1"),
	
	GOLDCREDIT_FIRST_REJECT("99","金信初审拒绝","1"),
	
	GOLDCREDIT_REVIEW_REJECT("100","金信复审拒绝","1"),
	
	BIGFINANCE_REJECT("104","大金融拒绝",""),
	
	BIGFINANCE_TO_SNED("107","大金融待放款","1"),
	
	BIGFINANCE_GRANTING("109","大金融放款中",""),
	
	BIGFINANCE_RETURN("105","大金融退回",""),
	
	LANUCH("900","信借申请",""),
	
	LANUCH_RE("901","发起复议",""),
	
	PROPOSE_OUT("111", "放弃待初审",""),
	
	PROPOSE_REFUSE("112", "拒绝待初审","");

	private static Map<String, LoanApplyStatus> nameMap = new HashMap<String, LoanApplyStatus>(
			200);
	private static Map<String, LoanApplyStatus> codeMap = new HashMap<String, LoanApplyStatus>(
			200);
	private static Map<String, LoanApplyStatus> sysFlagMap = new HashMap<String, LoanApplyStatus>(
			200);


	static {
		LoanApplyStatus[] allValues = LoanApplyStatus.values();
		for (LoanApplyStatus obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
			sysFlagMap.put(obj.getSysFlag(), obj);
		}
	}

	private String name;
	private String code;
	private String sysFlag;

	private LoanApplyStatus(String code, String name,String sysFlag) {
		this.name = name;
		this.code = code;
		this.sysFlag = sysFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public static LoanApplyStatus parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanApplyStatus parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
