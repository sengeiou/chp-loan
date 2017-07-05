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
public enum WechatDeletFlag {
	
	NORMAL("0", "正常"),
	DELETE("1", "删除");
	
	
	private String code;
	
	private String name;
	
	private WechatDeletFlag(String code, String name){
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
		
		for(WechatDeletFlag loanStatus: WechatDeletFlag.values()){
			
			if(loanStatus.getName().contains(loanStatusName)){
				codeList.add(loanStatus.getCode());
			}
			
		}
		
		return codeList;
	}
	
	public static String getLoanStatusName(String code){
		
		String name = null;
		for(WechatDeletFlag loanStatus : WechatDeletFlag.values()){
			if(loanStatus.getCode().equals(code)){
				name = loanStatus.getName();
				break;
			}
		}
		
		return name;
	}
}
