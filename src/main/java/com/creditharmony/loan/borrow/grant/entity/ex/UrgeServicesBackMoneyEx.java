package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 返款申请扩展实体
 * @Class Name UrgeServicesBackMoneyEx
 * @author 张永生
 * @Create In 2016年4月21日
 */
public class UrgeServicesBackMoneyEx extends DataEntity<UrgeServicesBackMoneyEx> {

	private static final long serialVersionUID = -1145286316210572130L;
	// 催收服务费返款ID
	private String id;
	// 序号
	private String index;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 3)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 2)
	private String customerName;
	// 证件号码
	private String customerCertNum;
	//户籍省
	private String customerRegisterProvince;
	//户籍市
	private String customerRegisterCity;
	// 用户银行账号
	@ExcelField(title = "银行账号", type = 0, align = 2, sort = 7)
	private String bankAccount;
	// 借款状态：结清或者提前结清
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 11, groups = 1, dictType = "jk_loan_status")
	private String dictLoanStatus;
	// 借款状态：结清或者提前结清(名称)
	private String dictLoanStatusLabel;
	// 借款产品
	private String productName;
	// 开户行
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 6, dictType="jk_open_bank")
	private String bankName;
	// 开户行(名称)
	private String bankNameLabel;
	// 开户支行
	@ExcelField(title = "开户行支行", type = 0, align = 2, sort = 6)
	private String bankBranch;
	// 开户行省
	@ExcelField(title = "开户行省", type = 0, align = 2, sort = 8)
	private String bankProvince;
	// 开户行市
	@ExcelField(title = "开户行市", type = 0, align = 2, sort = 9)
	private String bankCity;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 4)
	private BigDecimal contractAmount;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 5)
	private BigDecimal grantAmount;
	// 催收服务费
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 12)
	private BigDecimal feeUrgedService;
	// 结清日期
	@ExcelField(title = "结清日期", type = 0, align = 2, sort = 10)
	private Date settlementTime;
	// 最长逾期天数
	@ExcelField(title = "最长逾期天数", type = 0, align = 2, sort = 13)
	private int paybackMaxOverduedays;
	// 申请返款金额
	@ExcelField(title = "申请返款金额", type = 0, align = 2, sort = 14)
	private BigDecimal paybackBackAmount;
	// 返款状态
	@ExcelField(title = "返款状态", type = 0, align = 2, sort = 15, dictType = "jk_urge_repay_status")
	private String dictPayStatus;
	// 返款状态
	private String dictPayStatusLabel;
	// 返款结果
	@ExcelField(title = "返款结果", type = 0, align = 2, sort = 16, dictType = "jk_payback_fee_result")
	private String dictPayResult;
	// 返款结果(名称)	
	private String dictPayResultLabel;
	// 原因
	@ExcelField(title = "原因", type = 0, align = 2, sort = 17)
	private String remark;
	// 返款申请时间
	@ExcelField(title = "申请日期", type = 0, align = 2, sort = 18)
	private Date backApplyPayTime;
	// 返款时间
	@ExcelField(title = "返款日期", type = 0, align = 2, sort = 19)
	private Date backTime;
	// 门店
	private String storesCode;
	// 门店id
	private String storesId;
	// 返款申请部门
	private String backApplyDepartment;
	// 返款办理人
	private String  backTransactor;
	// 返款办理部门
	private String backTransactorTeam;
	// 返款申请人
	private String backApplyBy;
	private String queryRight;
	// 模式
	private String loanFlag;
	private String loanFlagLabel;
	// 渠道
	private String model;
	private String modelLabel;
	
	//合同标识
	private String channelFlag;
	
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

	public String getLoanFlag() {
		return loanFlag;
	}

	public String getLoanFlagLabel() {
		return loanFlagLabel;
	}


	public void setLoanFlagLabel(String loanFlagLabel) {
		this.loanFlagLabel = loanFlagLabel;
	}


	public String getModelLabel() {
		return modelLabel;
	}


	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
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

	public String getQueryRight() {
		return queryRight;
	}

	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public int getPaybackMaxOverduedays() {
		return paybackMaxOverduedays;
	}

	public void setPaybackMaxOverduedays(int paybackMaxOverduedays) {
		this.paybackMaxOverduedays = paybackMaxOverduedays;
	}

	public BigDecimal getPaybackBackAmount() {
		return paybackBackAmount;
	}

	public void setPaybackBackAmount(BigDecimal paybackBackAmount) {
		this.paybackBackAmount = paybackBackAmount;
	}

	public String getDictPayStatus() {
		return dictPayStatus;
	}

	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}

	public String getDictPayResult() {
		return dictPayResult;
	}

	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBackApplyPayTime() {
		return backApplyPayTime;
	}

	public void setBackApplyPayTime(Date backApplyPayTime) {
		this.backApplyPayTime = backApplyPayTime;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	
	public String getBackApplyDepartment() {
		return backApplyDepartment;
	}

	public void setBackApplyDepartment(String backApplyDepartment) {
		this.backApplyDepartment = backApplyDepartment;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}

	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}

	public String getStoresCode() {
		return storesCode;
	}

	public void setStoresCode(String storesCode) {
		this.storesCode = storesCode;
	}

	public String getBackTransactor() {
		return backTransactor;
	}

	public void setBackTransactor(String backTransactor) {
		this.backTransactor = backTransactor;
	}

	public String getBackTransactorTeam() {
		return backTransactorTeam;
	}

	public void setBackTransactorTeam(String backTransactorTeam) {
		this.backTransactorTeam = backTransactorTeam;
	}

	public String getBackApplyBy() {
		return backApplyBy;
	}

	public void setBackApplyBy(String backApplyBy) {
		this.backApplyBy = backApplyBy;
	}

	public String getCustomerRegisterProvince() {
		return customerRegisterProvince;
	}

	public void setCustomerRegisterProvince(String customerRegisterProvince) {
		this.customerRegisterProvince = customerRegisterProvince;
	}

	public String getCustomerRegisterCity() {
		return customerRegisterCity;
	}

	public void setCustomerRegisterCity(String customerRegisterCity) {
		this.customerRegisterCity = customerRegisterCity;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getStoresId() {
		return storesId;
	}

	public void setStoresId(String storesId) {
		this.storesId = storesId;
	}

	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public String getBankNameLabel() {
		return bankNameLabel;
	}

	public void setBankNameLabel(String bankNameLabel) {
		this.bankNameLabel = bankNameLabel;
	}

	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}

	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}

	public String getDictPayResultLabel() {
		return dictPayResultLabel;
	}

	public void setDictPayResultLabel(String dictPayResultLabel) {
		this.dictPayResultLabel = dictPayResultLabel;
	}

	public String getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}
	
	
	
}