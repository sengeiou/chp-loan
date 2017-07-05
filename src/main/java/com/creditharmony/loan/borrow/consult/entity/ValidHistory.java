package com.creditharmony.loan.borrow.consult.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 身份验证历史表
 * @Class Name ValidHistory
 * @author 宋锋
 * @Create In 2016年10月20日
 */
public class ValidHistory extends DataEntity<ValidHistory> {

	private static final long serialVersionUID = -4826593454690408310L;
	// 客户姓名
	private String loanName;
    // 合同编号
	private String contractCode; 
    // 修改类型
	private String updateType;
	// 修改类型
	private String certNum;
	
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}  
	
    
}
