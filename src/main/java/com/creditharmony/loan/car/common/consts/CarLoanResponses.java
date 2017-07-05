package com.creditharmony.loan.car.common.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 车借流程Response值
 * @Class Name CarLoanResponses
 * @author 陈伟东
 * @Create In 2016年2月18日
 */
public enum CarLoanResponses {

	TO_UPLOAD_FILE("TO_UPLOAD_FILE","到上传资料节点"),														
	TO_LOAN_APPLY("TO_LOAN_APPLY","到申请节点"),														
	CUSTOMER_GIVE_UP("CUSTOMER_GIVE_UP","客户放弃"), //评估师录入报告客户放弃、上传资料客户放弃														
	FIRST_AUDIT_CUSTOMER_GIVE_UP("FIRST_AUDIT_CUSTOMER_GIVE_UP","初审客户放弃"), //初审客户放弃														
	TO_FRIST_AUDIT("TO_FRIST_AUDIT","到初审节点"),					
	BACK_ASSESS_ENTER("BACK_ASSESS_ENTER","返回评估师录入报告"),											
	NOT_INTO("NOT_INTO","不符合进件条件"),											
	BACK_LOAN_APPLY("BACK_LOAN_APPLY","退回到申请"),				
	FIRST_AUDIT_REFUSED("FIRST_AUDIT_REFUSED","初审拒绝"),				
	TO_SEC_AUDIT("TO_SEC_AUDIT","到复审节点"),			
	RECHECK_ABANDON("RECHECK_ABANDON","复审客户放弃"),					
	TO_FINAL_CHECK_CONDICTION("TO_FINAL_CHECK_CONDICTION","复审附条件通过"),					
	TO_FINAL_CHECK("TO_FINAL_CHECK","复审通过"),							
	RECHECK_REFUSED("RECHECK_REFUSED","复审拒绝"),							
	FINAL_CHECK_ABANDON("FINAL_CHECK_ABANDON","终审客户放弃"),							
	BACK_RECHECK("BACK_RECHECK","终审退回"),							
	FINAL_CHECK_PASS("FINAL_CHECK_PASS","终审通过"),							
	FINAL_CHECK_PASS_CONDICTION("FINAL_CHECK_PASS_CONDICTION","终审附条件通过"),							
	FINAL_CHECK_REFUSED("FINAL_CHECK_REFUSED","终审拒绝"),							
	BACK_END_AUDIT("BACK_END_AUDIT","审核费率退回"),							
	TO_SIGN("TO_SIGN","到签署"),							
	TO_MAKE_CONTRACT("TO_MAKE_CONTRACT","到合同制作"),							
	BACK_SIGN("BACK_SIGN","退回到签署"),							
	BACK_AUDIT_RATE("BACK_AUDIT_RATE","退回到审核费率"),				
	TO_UPLOAD_CONTRACT("TO_UPLOAD_CONTRACT","到合同签约上传"),
	TO_AUDIT_CONTRACT("TO_AUDIT_CONTRACT", "到合同审核"),
	BACK_UPLOAD_CONTRACT("BACK_UPLOAD_CONTRACT","退回到合同签约上传"),				
	TO_GRANT_CONFIRM("TO_GRANT_CONFIRM","到放款确认"),				
	BACK_AUDIT_CONTRACT("BACK_AUDIT_CONTRACT","退回到合同审核"),				
	TO_ALLOT_CARD("TO_ALLOT_CARD","到分配卡号"),				
	BACK_GRANT_CONFIRM("BACK_GRANT_CONFIRM","退回放款确认"),				
	TO_GRANT("TO_GRANT","到放款"),				
	TO_GRANT_AUDIT("TO_GRANT_AUDIT","到放款审核"),
	TO_CREDIT_SEND("TO_CREDIT_SEND","车借债权推送"),	
	TO_END("TO_END","结束"),
	BACK_UPLOAD_FILE("BACK_UPLOAD_FILE","退回到上传资料节点"),
	EXTEND_GIVE_UP("EXTEND_GIVE_UP","展期放弃"),
	UPLOAD_CONTRACT_ABANDON("UPLOAD_CONTRACT_ABANDON","合同签约上传客户放弃"),
	BACK_SIGN_CONTRACT("BACK_SIGN_CONTRACT","退回到签署"),
	SIGN_GIVE_UP("SIGN_GIVE_UP","签署客户放弃"),
	SUPPLY_SEC_AUDIT("SUPPLY_SEC_AUDIT","补传资料待复审"),
	UPLOAD_CONTRACT_STORE_REFUSE("UPLOAD_CONTRACT_STORE_REFUSE", "签约上传门店拒绝");
	
	private static Map<String, CarLoanResponses> nameMap = new HashMap<String, CarLoanResponses>(
			100);
	private static Map<String, CarLoanResponses> codeMap = new HashMap<String, CarLoanResponses>(
			100);

	static {
		CarLoanResponses[] allValues = CarLoanResponses.values();
		for (CarLoanResponses obj : allValues) {
			nameMap.put(obj.getname(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private CarLoanResponses(String code, String name) {
		this.name = name;
		this.code = code;
	}

	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public static CarLoanResponses parseByName(String name) {
		return nameMap.get(name);
	}

	public static CarLoanResponses parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
