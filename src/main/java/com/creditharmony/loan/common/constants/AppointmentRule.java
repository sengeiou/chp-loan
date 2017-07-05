package com.creditharmony.loan.common.constants;

import java.util.Hashtable;
import java.util.Map;

/***
 * 预约规则
 * 
 * @Class Name
 * @author 翁私
 * @Create In 2017年03月21日
 */

public enum AppointmentRule {
	RULEONE("1"), // 规则1
	RULETWO("2"), // 规则2
	RULETHREE("3"), // 规则3
	RULEFOUR("4"),// 规则4
	RULEFIVE("5"),// 规则5 
	RULESIX("6");//  规则6

	public static Map<String, String> appointmentRuleMap = new Hashtable<String, String>();

	static {
		
		appointmentRuleMap.put(AppointmentRule.RULESIX.value,"规则六");
		appointmentRuleMap.put(AppointmentRule.RULEFIVE.value,"规则五");
		appointmentRuleMap.put(AppointmentRule.RULEFOUR.value,"规则四");
		appointmentRuleMap.put(AppointmentRule.RULETHREE.value,"规则三");
		appointmentRuleMap.put(AppointmentRule.RULETWO.value,"规则二");
		appointmentRuleMap.put(AppointmentRule.RULEONE.value,"规则一");
	}
	public final String value;

	private AppointmentRule(String value) {
		this.value = value;
	}

	public static String getAppointmentRule(String value) {
		return appointmentRuleMap.get(value);
	}
	
	public static Map<String, String> getAppointmentRuleMap() {
		return appointmentRuleMap;
	}
}