package com.creditharmony.loan.borrow.consult.constats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信客户状态
 * @Class Name WechatCustomerStatus
 * @author 武文涛
 * @Create In 2017年4月25日
 */
public enum WechatCustomerStatus {
	
	NOT_CONTACT("0", "未联系"),
	CONTACT("1", "已联系"),
	NO_INTENTION("2", "无意向"),
	ALREADY_COMPLETED("3", "已成功");
	
	private String code;
	private String name;
	
	private WechatCustomerStatus(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static Map<String, String> loanStatusCodeAndName = new HashMap<String, String>();

	/**
	 * By 任志远  2016年8月16日
	 * 
	 * @param loanStatus
	 * @return
	 */
	public static List<String> getCodeList(String loanStatusName) {
		
		List<String> codeList = new ArrayList<String>();
		
		for(WechatCustomerStatus loanStatus: WechatCustomerStatus.values()){
			
			if(loanStatus.getName().contains(loanStatusName)){
				codeList.add(loanStatus.getCode());
			}
			
		}
		
		return codeList;
	}
	
	public static String getLoanStatusName(String code){
		
		String name = null;
		for(WechatCustomerStatus loanStatus : WechatCustomerStatus.values()){
			if(loanStatus.getCode().equals(code)){
				name = loanStatus.getName();
				break;
			}
		}
		
		return name;
	}
}
