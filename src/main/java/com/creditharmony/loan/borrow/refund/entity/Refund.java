package com.creditharmony.loan.borrow.refund.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 退款表
 * 
 * @Class Name Refund
 * @author WJJ
 * @Create In 2016年4月19日
 */
@SuppressWarnings("serial")
public class Refund extends DataEntity<Refund> {

	private String id;//
	private String ids;//
	private String idsA;//
	private String[] idArray;
	private String mendianId;//门店ID
	private String mendianName;//门店名称
	private String storeId;//门店ID(查询所用)
	private String name; //门店名称(查询所用)
	private String contractCode;//合同编号
	private String contractCodeA;//合同编号(退票)
	private BigDecimal contractAmount;//合同金额
	private String customerName;//客户姓名
	private BigDecimal loanMoney;//借款金额
	private String loanStatus;//借款状态
	private String loanStatusName;//借款状态中文
	private BigDecimal money;//蓝补金额/放款金额
	private BigDecimal refundMoney;//申请退款金额
	private String appType;//退款类别（0：蓝补退款，1：放款失败催收服务费退款）
	private String appTypeName;//退款类别中文
	private String appTypeA;	//退款类别（退票）
	private String bankCode;//开户行
	private String BankName;
	private String refundBank;//退款银行
	private String refundTime;//退款时间
	private String appStatus;//申请状态（0：待初审，1：初审退回，2：待终审，3：终审退回，4：待退款，5：已退款，6：退款失败，7：数据管理部退回）
	private String appStatusName;//申请状态中文
	private String certNum;//证件号
	private String remark;//备注
	private String remarkA;//备注
	private String fkResult;//风控审批结果
	private String fkReason;//风控审批原因
	private String fkRemark;//风控审批意见
	private String zcResult;//总载审批结果
	private String zcReason;//总载审批原因
	private String zcRemark;//总载审批意见
	private String createBy;//创建人
	private Date createTimes;//创建时间
	private String modifyBy;//修改人
	private Date modifyTimes;//修改时间
	private String startTime;//开始日期
	private String endTime;//开始日期
	private String backStartDate;//退款开始日期
	private String backEndDate;//退款开始日期
	private String mode;//方式(用于处理 查看/修改 页面显示)
	
	private String incomeAccount;//收款账户
	private String incomeName;//收款户名
	private String incomeBank;//收款银行
	private String incomeBranch;//收款银行支行
	private String incomeCity;//收款直辖市
	private String incomeCounty;//收款县
	private String incomeCityName;//收款直辖市
	private String incomeCountyName;//收款县
	private Date backDate;//退款日期
	private String urgeid;//催收服务费退款ID
	private String chargeId;
	private String refundTotalNum;
	private String refundTotalMony = "0";
	
	private String queryRight;
	
	private String mt;
	// 标识
	private String loanFlag;
	private String loanFlagLabel;
	// 模式
	private String model;
	private String modelLabel;
	
	//合同渠道
	private String channelFlag;
	//能否导出标识
	private String ifCanExport;
	//借款实际标识
	private String realLoanStatus;
	
	
	public String getRealLoanStatus() {
		return realLoanStatus;
	}
	public void setRealLoanStatus(String realLoanStatus) {
		this.realLoanStatus = realLoanStatus;
	}
	public String getIfCanExport() {
		return ifCanExport;
	}
	public void setIfCanExport(String ifCanExport) {
		this.ifCanExport = ifCanExport;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getLoanFlagLabel() {
		return loanFlagLabel;
	}
	public void setLoanFlagLabel(String loanFlagLabel) {
		this.loanFlagLabel = loanFlagLabel;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMendianId() {
		return mendianId;
	}
	public void setMendianId(String mendianId) {
		this.mendianId = mendianId;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public BigDecimal getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getRefundBank() {
		return refundBank;
	}
	public void setRefundBank(String refundBank) {
		this.refundBank = refundBank;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFkResult() {
		return fkResult;
	}
	public void setFkResult(String fkResult) {
		this.fkResult = fkResult;
	}
	public String getFkReason() {
		return fkReason;
	}
	public void setFkReason(String fkReason) {
		this.fkReason = fkReason;
	}
	public String getFkRemark() {
		return fkRemark;
	}
	public void setFkRemark(String fkRemark) {
		this.fkRemark = fkRemark;
	}
	public String getZcResult() {
		return zcResult;
	}
	public void setZcResult(String zcResult) {
		this.zcResult = zcResult;
	}
	public String getZcReason() {
		return zcReason;
	}
	public void setZcReason(String zcReason) {
		this.zcReason = zcReason;
	}
	public String getZcRemark() {
		return zcRemark;
	}
	public void setZcRemark(String zcRemark) {
		this.zcRemark = zcRemark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMendianName() {
		return mendianName;
	}
	public void setMendianName(String mendianName) {
		this.mendianName = mendianName;
	}
	public Date getCreateTimes() {
		return createTimes;
	}
	public void setCreateTimes(Date createTimes) {
		this.createTimes = createTimes;
	}
	public Date getModifyTimes() {
		return modifyTimes;
	}
	public void setModifyTimes(Date modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getAppTypeName() {
		return appTypeName;
	}
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}
	public String getAppStatusName() {
		return appStatusName;
	}
	public void setAppStatusName(String appStatusName) {
		this.appStatusName = appStatusName;
	}
	public String getLoanStatusName() {
		return loanStatusName;
	}
	public void setLoanStatusName(String loanStatusName) {
		this.loanStatusName = loanStatusName;
	}
	public String getContractCodeA() {
		return contractCodeA;
	}
	public void setContractCodeA(String contractCodeA) {
		this.contractCodeA = contractCodeA;
	}
	public String getAppTypeA() {
		return appTypeA;
	}
	public void setAppTypeA(String appTypeA) {
		this.appTypeA = appTypeA;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getRemarkA() {
		return remarkA;
	}
	public void setRemarkA(String remarkA) {
		this.remarkA = remarkA;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	public String[] getIdArray() {
		return idArray;
	}
	public void setIdArray(String[] idArray) {
		this.idArray = idArray;
	}
	public String getIdsA() {
		return idsA;
	}
	public void setIdsA(String idsA) {
		this.idsA = idsA;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getIncomeAccount() {
		return incomeAccount;
	}
	public void setIncomeAccount(String incomeAccount) {
		this.incomeAccount = incomeAccount;
	}
	public String getIncomeName() {
		return incomeName;
	}
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}
	public String getIncomeBank() {
		return incomeBank;
	}
	public void setIncomeBank(String incomeBank) {
		this.incomeBank = incomeBank;
	}
	public String getIncomeBranch() {
		return incomeBranch;
	}
	public void setIncomeBranch(String incomeBranch) {
		this.incomeBranch = incomeBranch;
	}
	public String getIncomeCity() {
		return incomeCity;
	}
	public void setIncomeCity(String incomeCity) {
		this.incomeCity = incomeCity;
	}
	public String getIncomeCounty() {
		return incomeCounty;
	}
	public void setIncomeCounty(String incomeCounty) {
		this.incomeCounty = incomeCounty;
	}
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public String getUrgeid() {
		return urgeid;
	}
	public void setUrgeid(String urgeid) {
		this.urgeid = urgeid;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getIncomeCityName() {
		return incomeCityName;
	}
	public void setIncomeCityName(String incomeCityName) {
		this.incomeCityName = incomeCityName;
	}
	public String getIncomeCountyName() {
		return incomeCountyName;
	}
	public void setIncomeCountyName(String incomeCountyName) {
		this.incomeCountyName = incomeCountyName;
	}
	public String getMt() {
		return mt;
	}
	public void setMt(String mt) {
		this.mt = mt;
	}
	public String getBackStartDate() {
		return backStartDate;
	}
	public void setBackStartDate(String backStartDate) {
		this.backStartDate = backStartDate;
	}
	public String getBackEndDate() {
		return backEndDate;
	}
	public void setBackEndDate(String backEndDate) {
		this.backEndDate = backEndDate;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getRefundTotalNum() {
		return refundTotalNum;
	}
	public void setRefundTotalNum(String refundTotalNum) {
		this.refundTotalNum = refundTotalNum;
	}
	public String getRefundTotalMony() {
		return refundTotalMony;
	}
	public void setRefundTotalMony(String refundTotalMony) {
		this.refundTotalMony = refundTotalMony;
	}
	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	public String getChannelFlag() {
		return channelFlag;
	}
	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}
	
	
}

