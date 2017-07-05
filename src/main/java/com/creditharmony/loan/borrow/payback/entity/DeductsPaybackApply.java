package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 集中划扣DeductsPaybackApply
 * @author ws
 * @Create In 2016年3月28日
 */
@SuppressWarnings("serial")
public class DeductsPaybackApply extends DataEntity<DeductsPaybackApply>{

	private String id;
	// 合同编号
	private String contractCode;
	// 借款人
	private String  loanCustomerName;
	// 证件类型
	private String  dictLoanCertType;
	// 证件号码
	private String  loanCustomerCertNum;
	// 移动电话
	private String customerPhoneFirst;
	// 期供ID
	private String monthId;
	// 申请划扣金额
	private BigDecimal applyDeductAmount; 
	//  划扣平台
	private String dictDealType;
	// 还款申请日期
	private Date  applyPayDay; 
	// 还款账号姓名
	private String repayAccountName;
	// 还款划扣账号
	private String repayDeductAccount;
	// 开户行名称
	private String applyBankName;
	//  银行code
	private String bankCode;
	// 实际到账金额
	private BigDecimal applyReallyAmount; 
	// 回盘结果(0:待划扣，1:划扣失败、2:划扣成功、3处理中)
	private String dictBackResult;
	// 退回标识
	private String  returnLogo;
	//  退回原因
	private String applyBackMes;
	// 发起人
	private String lanuchBy; 
	// 发起人机构ID
	private String orgCode;
	
	// 待还款列表 id
	private String rlistId;
	
    // 还款主表id
	private String paybackId;
	
	private boolean success;
	
	private String storesMark;
	
	private String paybackDay;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getDictLoanCertType() {
		return dictLoanCertType;
	}
	public void setDictLoanCertType(String dictLoanCertType) {
		this.dictLoanCertType = dictLoanCertType;
	}
	public String getLoanCustomerCertNum() {
		return loanCustomerCertNum;
	}
	public void setLoanCustomerCertNum(String loanCustomerCertNum) {
		this.loanCustomerCertNum = loanCustomerCertNum;
	}
	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}
	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	public BigDecimal getApplyDeductAmount() {
		return applyDeductAmount;
	}
	public void setApplyDeductAmount(BigDecimal applyDeductAmount) {
		this.applyDeductAmount = applyDeductAmount;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public Date getApplyPayDay() {
		return applyPayDay;
	}
	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}
	public String getRepayAccountName() {
		return repayAccountName;
	}
	public void setRepayAccountName(String repayAccountName) {
		this.repayAccountName = repayAccountName;
	}
	public String getRepayDeductAccount() {
		return repayDeductAccount;
	}
	public void setRepayDeductAccount(String repayDeductAccount) {
		this.repayDeductAccount = repayDeductAccount;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	public String getDictBackResult() {
		return dictBackResult;
	}
	public void setDictBackResult(String dictBackResult) {
		this.dictBackResult = dictBackResult;
	}
	public String getReturnLogo() {
		return returnLogo;
	}
	public void setReturnLogo(String returnLogo) {
		this.returnLogo = returnLogo;
	}
	public String getApplyBackMes() {
		return applyBackMes;
	}
	public void setApplyBackMes(String applyBackMes) {
		this.applyBackMes = applyBackMes;
	}
	public String getLanuchBy() {
		return lanuchBy;
	}
	public void setLanuchBy(String lanuchBy) {
		this.lanuchBy = lanuchBy;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRlistId() {
		return rlistId;
	}
	public void setRlistId(String rlistId) {
		this.rlistId = rlistId;
	}
	public String getPaybackId() {
		return paybackId;
	}
	public void setPaybackId(String paybackId) {
		this.paybackId = paybackId;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getStoresMark() {
		return storesMark;
	}
	public void setStoresMark(String storesMark) {
		this.storesMark = storesMark;
	}
	public String getPaybackDay() {
		return paybackDay;
	}
	public void setPaybackDay(String paybackDay) {
		this.paybackDay = paybackDay;
	}
	
}
