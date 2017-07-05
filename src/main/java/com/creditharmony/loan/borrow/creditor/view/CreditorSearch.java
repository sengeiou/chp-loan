package com.creditharmony.loan.borrow.creditor.view;

/**
 * 债权录入管理搜索
 * @Class Name CreditorService
 * @author WJJ
 * @Create In 2016年3月11日
 */

public class CreditorSearch {
	private String loanCode;//借款编号
	private String loanName;//借款人姓名
	private String cerNum;//证件号码
	private String type;//产品类型
	
	private int pageNo;
	private int pageSize;
	
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getCerNum() {
		return cerNum;
	}
	public void setCerNum(String cerNum) {
		this.cerNum = cerNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
