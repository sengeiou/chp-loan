/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.workFlow.viewLoanFlowWorkItemView.java
 * @Create By 张灏
 * @Create In 2016年1月23日 下午4:52:05
 */
package com.creditharmony.loan.car.carGrant.ex;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * @Class Name CarDisCardEx
 * @Create In 2016年1月23日
 */
public class CarDisCardEx  extends BaseTaskItemView{
	
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	//门店名称
	private String storeName;
	//证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 30)
	private String certNum;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	//开户行  
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 80,dictType="jk_open_bank")
	private String cardBank;
	//是否电销
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 120,dictType="jk_telemarketing")
	private String loanIsPhone;
	//借款产品
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 40)
	private String auditBorrowProductName;
	//合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 50)
	private String contractAmount;
	//批借期限(天)
	@ExcelField(title = "批借期限(天)", type = 0, align = 2, sort = 70)
	private String auditLoanMonths;
	//划扣金额
	@ExcelField(title = "划扣金额", type = 0, align = 2, sort = 90)
	private String deductsAmount;
	//总费率
	@ExcelField(title = "总费率", type = 0, align = 2, sort = 100)
	private String grossRate;
	//借款状态   
	@ExcelField(title = "状态", type = 0, align = 2, sort = 110,dictType="jk_car_loan_status")
	private String dictStatus;

    //标识
    private String borrowTrusteeFlag;
    

	public String getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(String auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public String getBorrowTrusteeFlag() {
		return borrowTrusteeFlag;
	}
	public void setBorrowTrusteeFlag(String borrowTrusteeFlag) {
		this.borrowTrusteeFlag = borrowTrusteeFlag;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}

	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}


	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getDeductsAmount() {
		return deductsAmount;
	}
	public void setDeductsAmount(String deductsAmount) {
		this.deductsAmount = deductsAmount;
	}

	public String getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(String grossRate) {
		this.grossRate = grossRate;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	
	
	
	

    
}
