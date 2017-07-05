package com.creditharmony.loan.channel.goldcredit.view;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 放款已办列表的
 * 
 * @author 张建雄
 *
 */
public class GCGrantDoneView extends DataEntity<GCGrantDoneView> {

	private static final long serialVersionUID = 5366339211752113306L;
	// 借款 applyId
	private String applyId;
	private String loanCode;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1, groups = 1)
	private Integer sequenceNumber;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 2, groups = 1)
	private String customerName;
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 3, groups = 1)
	private String customerCertNum;
	// 共借人
	private String coBorrowing;
	@ExcelField(title = "客户经理", type = 0, align = 2, sort = 4, groups = 1)
	private String loanManagerName;
	@ExcelField(title = "客户经理编号", type = 0, align = 2, sort = 4, groups = 1)
	private String loanManagerCode;
	@ExcelField(title = "团队经理", type = 0, align = 2, sort = 4, groups = 1)
	private String loanTeamManagerName;
	@ExcelField(title = "团队经理编号", type = 0, align = 2, sort = 4, groups = 1)
	private String loanTeamManagerCode;
	@ExcelField(title = "销售人员", type = 0, align = 2, sort = 5, groups = 1)
	private String loanCustomerServiceName;
	// @ExcelField(title = "借款类型", type = 0, align = 2, sort = 6, groups = 1)
	private String classType;
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 7, groups = 1)
	private String contractCode;
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 8, groups = 1)
	private BigDecimal contractAmount;
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 9, groups = 1)
	private BigDecimal grantAmount;
	@ExcelField(title = "批借金额", type = 0, align = 2, sort = 10, groups = 1)
	private BigDecimal auditAmount;
	@ExcelField(title = "信访费", type = 0, align = 2, sort = 11, groups = 1)
	private BigDecimal feePetition;
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 12, groups = 1)
	private String productType;
	// 催收服务费
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 13, groups = 1)
	private BigDecimal urgeMoney;
	// 未划金额
	@ExcelField(title = "未划金额", type = 0, align = 2, sort = 14, groups = 1)
	private BigDecimal urgeDecuteMoeny;
	@ExcelField(title = "期数", type = 0, align = 2, sort = 15, groups = 1)
	private String contractMonths;
	@ExcelField(title = "放款账户", type = 0, align = 2, sort = 16, groups = 1)
	private String middleName;

	@ExcelField(title = "开户行", type = 0, align = 2, sort = 17, groups = 1)
	private String midBankName;

	@ExcelField(title = "账号", type = 0, align = 2, sort = 18, groups = 1)
	private String bankCardNo;
	@ExcelField(title = "机构", type = 0, align = 2, sort = 19, groups = 1)
	private String storesCode;
	// 放款时间17
	@ExcelField(title = "放款时间", type = 0, align = 2, sort = 20, groups = 1)
	private Date lendingTime;
	// 放款批次
	@ExcelField(title = "放款批次", type = 0, align = 2, sort = 21, groups = 1)
	private String grantPch;

	@ExcelField(title = "操作人员", type = 0, align = 2, sort = 22, groups = 1)
	private String checkEmpName;
	@ExcelField(title = "标识", type = 0, align = 2, sort = 23, dictType = "jk_channel_flag", groups = 1)
	private String loanFlag;
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 24, dictType = "jk_urgent_flag", groups = 1)
	private String urgentFlag;
	// 提交批次
	private String submissionBatch;
	// 提交时间
	private Date submissionDate;
	// 放款人员
	private String loanOfficerName;
	// 自然人保证人
	private String sercurityNames;
	// 新老字段
	private String loanInfoOldOrNewFlag;
	// 放款总金额
	private BigDecimal totalGrantMoney;

	public String getSercurityNames() {
		return sercurityNames;
	}

	public void setSercurityNames(String sercurityNames) {
		this.sercurityNames = sercurityNames;
	}

	private String telesalesFlag;

	private Date lendingTimeStrat;
	private Date lendingTimeEnd;

	private String dictLoanStatus;

	private String storeCode;
	private String storeName;

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public BigDecimal getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}

	public BigDecimal getFeePetition() {
		return feePetition;
	}

	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getUrgeMoney() {
		return urgeMoney;
	}

	public void setUrgeMoney(BigDecimal urgeMoney) {
		this.urgeMoney = urgeMoney;
	}

	public BigDecimal getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}

	public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}

	public String getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMidBankName() {
		return midBankName;
	}

	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getStoresCode() {
		return storesCode;
	}

	public void setStoresCode(String storesCode) {
		this.storesCode = storesCode;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getGrantPch() {
		return grantPch;
	}

	public void setGrantPch(String grantPch) {
		this.grantPch = grantPch;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public String getUrgentFlag() {
		return urgentFlag;
	}

	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getCoBorrowing() {
		return coBorrowing;
	}

	public void setCoBorrowing(String coBorrowing) {
		this.coBorrowing = coBorrowing;
	}

	public String getSubmissionBatch() {
		return submissionBatch;
	}

	public void setSubmissionBatch(String submissionBatch) {
		this.submissionBatch = submissionBatch;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}

	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}

	public String getLoanCustomerServiceName() {
		return loanCustomerServiceName;
	}

	public void setLoanCustomerServiceName(String loanCustomerServiceName) {
		this.loanCustomerServiceName = loanCustomerServiceName;
	}

	public String getCheckEmpName() {
		return checkEmpName;
	}

	public void setCheckEmpName(String checkEmpName) {
		this.checkEmpName = checkEmpName;
	}

	public String getLoanOfficerName() {
		return loanOfficerName;
	}

	public void setLoanOfficerName(String loanOfficerName) {
		this.loanOfficerName = loanOfficerName;
	}

	public String getTelesalesFlag() {
		return telesalesFlag;
	}

	public void setTelesalesFlag(String telesalesFlag) {
		this.telesalesFlag = telesalesFlag;
	}

	public Date getLendingTimeStrat() {
		return lendingTimeStrat;
	}

	public void setLendingTimeStrat(Date lendingTimeStrat) {
		this.lendingTimeStrat = lendingTimeStrat;
	}

	public Date getLendingTimeEnd() {
		return lendingTimeEnd;
	}

	public void setLendingTimeEnd(Date lendingTimeEnd) {
		this.lendingTimeEnd = lendingTimeEnd;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getLoanManagerName() {
		return loanManagerName;
	}

	public void setLoanManagerName(String loanManagerName) {
		this.loanManagerName = loanManagerName;
	}

	public String getLoanManagerCode() {
		return loanManagerCode;
	}

	public void setLoanManagerCode(String loanManagerCode) {
		this.loanManagerCode = loanManagerCode;
	}

	public String getLoanTeamManagerCode() {
		return loanTeamManagerCode;
	}

	public void setLoanTeamManagerCode(String loanTeamManagerCode) {
		this.loanTeamManagerCode = loanTeamManagerCode;
	}

	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}

	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}

	public BigDecimal getTotalGrantMoney() {
		return totalGrantMoney;
	}

	public void setTotalGrantMoney(BigDecimal totalGrantMoney) {
		this.totalGrantMoney = totalGrantMoney;
	}
}
