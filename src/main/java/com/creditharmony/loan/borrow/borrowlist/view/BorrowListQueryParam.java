package com.creditharmony.loan.borrow.borrowlist.view;

/**
 * 信借待办列表查询条件bean
 * @Class Name 
 * @author zhangping
 * @Create In 2015年11月24日
 */
public class BorrowListQueryParam{

	private String customerName; //客户姓名
	private String contStoresId; //门店
	private String productType; //产品
	private String loanTeamEmpcode;//团队经理
	private String offendSalesName;//客户经理 
	private String certNum;  //身份证号
	private String borrowTrusteeFlag ; //资金托管标识，1托管，空非托管
	private String loanIsUrgent;  //是否加急
	private String dictAccountType;//账户类型(追加贷款/结清再贷)
	private String isAddLoan; //是否追加借（0：否，1：是）
	private String dictSource; //来源
	private String loanIsPhone;//是否电销（0：否，1：是）
	private String flag; //标识（参数名待定，选项值待定）

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContStoresId() {
		return contStoresId;
	}

	public void setContStoresId(String contStoresId) {
		this.contStoresId = contStoresId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getLoanTeamEmpcode() {
		return loanTeamEmpcode;
	}

	public void setLoanTeamEmpcode(String loanTeamEmpcode) {
		this.loanTeamEmpcode = loanTeamEmpcode;
	}

	public String getOffendSalesName() {
		return offendSalesName;
	}

	public void setOffendSalesName(String offendSalesName) {
		this.offendSalesName = offendSalesName;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getBorrowTrusteeFlag() {
		return borrowTrusteeFlag;
	}

	public void setBorrowTrusteeFlag(String borrowTrusteeFlag) {
		this.borrowTrusteeFlag = borrowTrusteeFlag;
	}

	public String getLoanIsUrgent() {
		return loanIsUrgent;
	}

	public void setLoanIsUrgent(String loanIsUrgent) {
		this.loanIsUrgent = loanIsUrgent;
	}

	public String getDictAccountType() {
		return dictAccountType;
	}

	public void setDictAccountType(String dictAccountType) {
		this.dictAccountType = dictAccountType;
	}

	public String getIsAddLoan() {
		return isAddLoan;
	}

	public void setIsAddLoan(String isAddLoan) {
		this.isAddLoan = isAddLoan;
	}

	public String getDictSource() {
		return dictSource;
	}

	public void setDictSource(String dictSource) {
		this.dictSource = dictSource;
	}

	public String getLoanIsPhone() {
		return loanIsPhone;
	}

	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}
