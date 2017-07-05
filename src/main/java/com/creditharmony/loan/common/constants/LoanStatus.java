package com.creditharmony.loan.common.constants;

import java.util.Hashtable;
import java.util.Map;

/***
 * 借款状态
 * 
 * @Class Name
 * @author 翁私
 * @Create In 2016年10月27日
 */

public enum LoanStatus {
	HKZ("87"), // 还款中
	YQ("88"), // 逾期
	JQ("89"), // 结清
	QTJQ("90"),// 提前结清
	TQJQSH("91"),// 提前结清待审核
	JCDQR("92");//  结清待确认

	public static Map<String, String> loanStatusMap = new Hashtable<String, String>();
	public static Map<String, String> loanStatusCodeMap = new Hashtable<String, String>();


	static {
		loanStatusMap.put(LoanStatus.HKZ.value, "还款中");
		loanStatusMap.put(LoanStatus.YQ.value, "逾期");
		loanStatusMap.put(LoanStatus.JQ.value, "结清");
		loanStatusMap.put(LoanStatus.QTJQ.value, "提前结清");
		loanStatusMap.put(LoanStatus.TQJQSH.value, "提前结清待审核");
		loanStatusMap.put(LoanStatus.JCDQR.value, "结清待确认");
		
		loanStatusCodeMap.put("还款中",LoanStatus.HKZ.value);
		loanStatusCodeMap.put("逾期",LoanStatus.YQ.value);
		loanStatusCodeMap.put("结清",LoanStatus.JQ.value);
		loanStatusCodeMap.put("提前结清",LoanStatus.QTJQ.value);
		loanStatusCodeMap.put("提前结清待审核",LoanStatus.TQJQSH.value);
		loanStatusCodeMap.put("结清待确认",LoanStatus.JCDQR.value);
	}
	public final String value;

	private LoanStatus(String value) {
		this.value = value;
	}

	public static String getLoanStatus(String value) {
		return loanStatusMap.get(value);
	}
	
	public static String getLoanStatusNameToCode(String name) {
		return loanStatusCodeMap.get(name);
	}
	
	
	public static Map<String, String> getLoanStatusMap() {
		return loanStatusMap;
	}
}