package com.creditharmony.loan.borrow.contract.entity;

import com.creditharmony.core.persistence.DataEntity;

public class VerificationInfo extends DataEntity<VerificationInfo>{
 
    private static final long serialVersionUID = -8007906125810022487L;
 
    private String customerId;
    
    
    public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	// 借款编码
    private String loanCode;        
    // 客户编码
    private String customerCode;  
    // 客户姓名
    private String customerName;       
    // 客户身份证号
    private String customerCertNum; 
    // 验证次数
    private Integer verifyNumber;
    // 验证失败编码
    private String failCode;    
    // 客户类型
    private String customerType;
    //上传文件id
    private String docId;
    
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public Integer getVerifyNumber() {
		return verifyNumber;
	}
	public void setVerifyNumber(Integer verifyNumber) {
		this.verifyNumber = verifyNumber;
	}
	public String getFailCode() {
		return failCode;
	}
	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}    

}