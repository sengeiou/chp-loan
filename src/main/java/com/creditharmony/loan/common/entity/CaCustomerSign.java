package com.creditharmony.loan.common.entity;

/**
 * 代理签章需要参数
 * 
 * @Class Name CaCustomerSign
 * @author  
 * @Create In 2016年3月31日
 */
public class CaCustomerSign {
	
	public CaCustomerSign(String custName,String keyword,String contractCode,String certNum,String phoneNum){
		this.custName=custName;
		this.contractCode=contractCode;
		this.keyword=keyword;
		this.certNum=certNum;
		this.phoneNum= phoneNum;
	}

	//关键字
	private String keyword;
	//(合同号)证件号
	private String contractCode;
	//客户姓名
	private String custName;
	//身份证
	private String certNum;
	//电话号码
	private String phoneNum;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}