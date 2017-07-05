package com.creditharmony.loan.car.carExtend.view;

import java.util.Date;


/**
 * @Class Name CarExtendSigningSubmitView
 * @author 张振强
 * @Create In 2016年3月12日
 */
public class CarExtendSigningSubmitView extends CarExtendBaseBusinessView{

	  // 借款编码
	  private String loanCode;    
	  // 银行卡(或存折)户名
	  private String bankAccountName;  
	  // 开卡省
	  private String bankProvince;    
	  // 开卡市
	  private String bankCity; 
	  // 开卡行
	  private String cardBank;    
	  // 支行
	  private String applyBankName;
	  // 银行帐号
	  private String bankCardNo;  
	  // 签约平台
	  private String bankSigningPlatform; 
	  // 预约签署日期
	  private Date contractDueDay;
	  // 借款状态编码
	  private String applyStatusCode;
	  // 退回原因(存入借款信息主表中的备注)
	  private String remark;
	  //是否生僻字
	  private String israre;
	  //实际签署日期
	  private Date contractFactDay;
	//产品类型编码（批复）
	private String auditBorrowProductCode;
	//产品类型名称（批复）
	private String auditBorrowProductName;
	//借款期限（批复）
	private Integer auditLoanMonths;
	//批借金额
	private Double auditAmount;
	
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}
	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}
	public Date getContractDueDay() {
		return contractDueDay;
	}
	public void setContractDueDay(Date contractDueDay) {
		this.contractDueDay = contractDueDay;
	}
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsrare() {
		return israre;
	}
	public void setIsrare(String israre) {
		this.israre = israre;
	}
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	public Integer getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(Integer auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public Double getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}
	  

}
