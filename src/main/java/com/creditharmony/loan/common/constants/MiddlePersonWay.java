package com.creditharmony.loan.common.constants;

/**
 * 中间人账户类型
 * @Class Name NumberConstants
 * @author 王彬彬
 * @Create In 2016年1月5日
 */
public enum MiddlePersonWay {
	
	MIDDLE_CARD("0", "中间人账户（银行卡）"),
	ZJ_CARD("1", "中金"),
	TL_CARD("2", "通联"),
	GC_CARD("9", "金信");
	
	private String name;
	private String code;
	private MiddlePersonWay(String code, String name) {
		this.name = name;
		this.code = code;
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
	
}
