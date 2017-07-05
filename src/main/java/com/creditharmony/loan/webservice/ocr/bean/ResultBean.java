package com.creditharmony.loan.webservice.ocr.bean;

public class ResultBean {

	private Boolean result;
	private String memo;
	
	public ResultBean() {
	}
	
	public ResultBean(Boolean result, String memo) {
		this.result = result;
		this.memo = memo;
	}

	public Boolean getResult() {
		return result;
	}
	
	public void setResult(Boolean result) {
		this.result = result;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
