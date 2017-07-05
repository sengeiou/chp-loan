package com.creditharmony.loan.borrow.consult.constats;
/**
 *
 * @author 任志远
 * @date 2017年5月7日
 */
public enum AllotResultCode {

	SUCCESS("200", "成功"),
	PARAM_ERROR("400", "参数异常"),
	EXCEPTION("500", "服务器异常");
	
	AllotResultCode(String code, String desc){
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
