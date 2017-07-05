package com.creditharmony.loan.borrow.consult.constats;
/**
 *
 * @author 任志远
 * @date 2017年5月10日
 */
public enum ConsultDataSource {

	APP("0", "信和业务助手"),
	
	PC("1", "CHP3.0");
	
	ConsultDataSource(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private String code;
	
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
