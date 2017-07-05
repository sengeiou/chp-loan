package com.creditharmony.loan.common.constants;

/**
 * 
 * 查账类型
 * 
 * @Class Name
 * @author 翁私
 * @Create In 2017年03月01日
 *
 */
public enum AuditType {
	
	ZHONGHE("1"), //中和东方类型
	DIANCUI("2"), // 电催类型
	DIANXIAO("3"); // 电销类型
	
	public final String value;

	private AuditType(String value) {
		this.value = value;
	}


}
