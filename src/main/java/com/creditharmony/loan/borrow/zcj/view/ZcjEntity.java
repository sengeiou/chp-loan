package com.creditharmony.loan.borrow.zcj.view;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 大金融资产家
 * 
 * @Class Name ZcjEntity
 * @author wujj
 * @Create In 2016年8月24日
 */
public class ZcjEntity extends DataEntity<ZcjEntity> {
	private static final long serialVersionUID = 2994811734409500770L;
	
	private String id;//id
	private String storeId;//门店ID
	private String[] orgIds;
	private String orgName;//门店
    private String pushDate;//推介日期
    private String loanCustomerName;//客户姓名
    private String customerCertNum;//身份证号
    private String bankName;//开户行
    private String bankAccount;//银行账号
    private BigDecimal loanAuditAmount;//批借金额
    private BigDecimal contractAmount;//合同金额
    private BigDecimal feePetition;//外访费
    private BigDecimal feeExpedited;//加急费
    private BigDecimal grantAmount;//放款金额
    private String contractVersion;//合同版本号
    private String contractMonths;//期限
    private String loanStatus;//状态
    private String lendingTime;//放款日期
    private String loanFlag;//渠道
    private String model;//模式
    private String contractCode;//合同编号
    private String loanCode;
    private String coboName;//共借人
    private String productType;//产品
    private String bankBranch;//支行
    private String loanUrgentFlag;//加急标识
    private String paperlessFlag;//是否无纸化
    private String legalMan;//是否有保证人
    private String isRegister;//是否登记失败
    private String isStamp;//是否加盖失败
    private String isAdditional;//是否追加借
    private String isTelesales;//是否电销
    private BigDecimal contractAmountSum;//合同总金额
    private BigDecimal grantAmountSum;//放款总金额
    private BigDecimal loanAuditAmountSum;//批借总金额
    private String frozenFlag;//是否冻结
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String[] getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPushDate() {
		return pushDate;
	}
	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public BigDecimal getLoanAuditAmount() {
		return loanAuditAmount;
	}
	public void setLoanAuditAmount(BigDecimal loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}
	public BigDecimal getFeeExpedited() {
		return feeExpedited;
	}
	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = feeExpedited;
	}
	public BigDecimal getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(String lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}
	public String getPaperlessFlag() {
		return paperlessFlag;
	}
	public void setPaperlessFlag(String paperlessFlag) {
		this.paperlessFlag = paperlessFlag;
	}
	public String getLegalMan() {
		return legalMan;
	}
	public void setLegalMan(String legalMan) {
		this.legalMan = legalMan;
	}
	public String getIsRegister() {
		return isRegister;
	}
	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}
	public String getIsStamp() {
		return isStamp;
	}
	public void setIsStamp(String isStamp) {
		this.isStamp = isStamp;
	}
	public String getIsAdditional() {
		return isAdditional;
	}
	public void setIsAdditional(String isAdditional) {
		this.isAdditional = isAdditional;
	}
	public String getIsTelesales() {
		return isTelesales;
	}
	public void setIsTelesales(String isTelesales) {
		this.isTelesales = isTelesales;
	}
	public BigDecimal getContractAmountSum() {
		return contractAmountSum;
	}
	public void setContractAmountSum(BigDecimal contractAmountSum) {
		this.contractAmountSum = contractAmountSum;
	}
	public BigDecimal getGrantAmountSum() {
		return grantAmountSum;
	}
	public void setGrantAmountSum(BigDecimal grantAmountSum) {
		this.grantAmountSum = grantAmountSum;
	}
	public BigDecimal getLoanAuditAmountSum() {
		return loanAuditAmountSum;
	}
	public void setLoanAuditAmountSum(BigDecimal loanAuditAmountSum) {
		this.loanAuditAmountSum = loanAuditAmountSum;
	}
	public String getFrozenFlag() {
		return frozenFlag;
	}
	public void setFrozenFlag(String frozenFlag) {
		this.frozenFlag = frozenFlag;
	}
    
}
