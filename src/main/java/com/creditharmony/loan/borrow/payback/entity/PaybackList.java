/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entityPaybackList.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午1:25:19
 */

package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;

@SuppressWarnings("serial")
public class PaybackList extends DataEntity<PaybackList>{
	// 主键ID
	private String ID; 
	// 期供id
	private String monthId;
	// 客户姓名
	private String customerName;
	// 合同编号
	private String contractCode;
	// 还款日
	private Date monthPayDay;
	// 期供状态
    private String dictMonthStatus;
    // 期供状态(名称)
    private String dictMonthStatusLabel;
    // 是否提醒状态
    private String status;
    // 开户行名称
    private String applyBankName;
    // 开户行名称
    private String applyBankNameLabel;
    // 手机号
    private String tel;
    // 门店名称
    private String storesName;
    // 期数
    private int months;
    // 应还金额(月还期供金额)
    private BigDecimal repayAmount;
    // 已还金额
    private BigDecimal completeAmount;
    // 蓝补金额
    private BigDecimal buleAmount;
    // 划扣平台
    private String dictDealType;
    // 划扣平台
    private String dictDealTypeLabel;
    // 标识
    private String mark;
    // 标识
    private String markLabel;
    // 提醒标识
    private String remindLogo;
    // 门店备注
    private String storesRemark;
    // 门店备注人
    private String storesRemarkUserid;
    // 风控备注
    private String riskcontrolMark;
    // 风控备注人
    private String riskcontrolRemarkUserid;
    // 关联的还款主表的对象
    private Payback payback;
    // 关联的合同表的对象 
    private Contract contract;
    // 关联的借款信息表对象
    private LoanInfo loanInfo;
    // 月期供金额
    private BigDecimal monthlySupplyAmount;
    
    //总金额
    private BigDecimal  sumAmont;
    // 总数量
    private String sumNumber;
    
    private String monthDay;
    private String model;
    private String modelLabel;
    
    private String loanCustomerName;
    
    private String dictLoanCertType;
    
    private String loanCustomerCertNum;
    
    private String customerPhoneFirst;
    
    private String applyDeductAmount;
    
    private String repayAccountName;
    
    private String repayDeductAccount;
    
    private String bankCode;
    
    private String rlistId;
    
    private String paybackId;
    
    
 // 还款申请日期
 	private Date  applyPayDay; 
 	
 	private String storesMark;
 	
 	private boolean success;
 	
 	
    
	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}

	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}

	public String getMarkLabel() {
		return markLabel;
	}

	public void setMarkLabel(String markLabel) {
		this.markLabel = markLabel;
	}

	public String getDictMonthStatusLabel() {
		return dictMonthStatusLabel;
	}

	public void setDictMonthStatusLabel(String dictMonthStatusLabel) {
		this.dictMonthStatusLabel = dictMonthStatusLabel;
	}

	public String getApplyBankNameLabel() {
		return applyBankNameLabel;
	}

	public void setApplyBankNameLabel(String applyBankNameLabel) {
		this.applyBankNameLabel = applyBankNameLabel;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getMonthPayDay() {
		return monthPayDay;
	}

	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}


	public String getDictMonthStatus() {
		return dictMonthStatus;
	}

	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyBankName() {
		return applyBankName;
	}

	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public BigDecimal getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(BigDecimal completeAmount) {
		this.completeAmount = completeAmount;
	}

	
	public BigDecimal getBuleAmount() {
		return buleAmount;
	}

	public void setBuleAmount(BigDecimal buleAmount) {
		this.buleAmount = buleAmount;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getRemindLogo() {
		return remindLogo;
	}

	public void setRemindLogo(String remindLogo) {
		this.remindLogo = remindLogo;
	}

	public String getStoresRemark() {
		return storesRemark;
	}

	public void setStoresRemark(String storesRemark) {
		this.storesRemark = storesRemark;
	}

	public String getStoresRemarkUserid() {
		return storesRemarkUserid;
	}

	public void setStoresRemarkUserid(String storesRemarkUserid) {
		this.storesRemarkUserid = storesRemarkUserid;
	}

	public String getRiskcontrolMark() {
		return riskcontrolMark;
	}

	public void setRiskcontrolMark(String riskcontrolMark) {
		this.riskcontrolMark = riskcontrolMark;
	}

	public String getRiskcontrolRemarkUserid() {
		return riskcontrolRemarkUserid;
	}

	public void setRiskcontrolRemarkUserid(String riskcontrolRemarkUserid) {
		this.riskcontrolRemarkUserid = riskcontrolRemarkUserid;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Payback getPayback() {
		return payback;
	}

	public void setPayback(Payback payback) {
		this.payback = payback;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}

	public BigDecimal getMonthlySupplyAmount() {
		return monthlySupplyAmount;
	}

	public void setMonthlySupplyAmount(BigDecimal monthlySupplyAmount) {
		this.monthlySupplyAmount = monthlySupplyAmount;
	}

	public BigDecimal getSumAmont() {
		return sumAmont;
	}

	public void setSumAmont(BigDecimal sumAmont) {
		this.sumAmont = sumAmont;
	}

	public String getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(String sumNumber) {
		this.sumNumber = sumNumber;
	}

	public String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelLabel() {
		return modelLabel;
	}

	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
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

	public String getApplyDeductAmount() {
		return applyDeductAmount;
	}

	public void setApplyDeductAmount(String applyDeductAmount) {
		this.applyDeductAmount = applyDeductAmount;
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public Date getApplyPayDay() {
		return applyPayDay;
	}

	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}

	public String getStoresMark() {
		return storesMark;
	}

	public void setStoresMark(String storesMark) {
		this.storesMark = storesMark;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
