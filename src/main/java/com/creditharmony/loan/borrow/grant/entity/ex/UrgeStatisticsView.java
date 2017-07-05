package com.creditharmony.loan.borrow.grant.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name UrgeStatisticsView
 * @author 尚军伟
 * @Create In 2016年4月21日
 */
@SuppressWarnings("serial")
public class UrgeStatisticsView  extends DataEntity<UrgeStatisticsView>{
	//合同编号
	private String contractCode;
	private String contractCodes;
	//客户姓名
	private String customerName;
	// 合同金额
    private BigDecimal contractAmount;
    // 应收催收服务费
 	private BigDecimal receivableFeeUrgedService;
    // 已收催收服务费
 	private BigDecimal receivedfeeUrgedService;
 	// 征信费
 	private BigDecimal feeCredit;
 	// 信访费
 	private BigDecimal feePetition;
 	// 费用总计
 	private BigDecimal feeSum;
 	// 借款产品
 	private String productName;
 	//收取结果
 	private String colResult;
    // 放款时间
 	private Date lendingTime;
    // 最后划扣结束日期
 	private Date lastDeductDate;
 	//标识
 	private String loanFlag;
 	//最后划扣开始时间
 	private String deductBeginTime;
 	//最后划扣结束时间
 	private String deductEndTime;
 	//放款日期开始时间
 	private String lendingTimeBegin;
 	//放款日期结束时间
 	private String lendingTimeEnd;
 	//返款状态
 	private String dictPayStatus;
 	//是否返还
 	private String dictPayStatusLabel;
	//最大预期天数
 	private int monthOverdueDaysMax;
 	//是否符合退款条件的描述：是或否
 	private String backMoneyDesc;
 	//还款状态
 	private String payStatus;
	   // 数据查询权限
	private String queryRight;
 	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	//还款状态名称
 	private String payStatusLabel;
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
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getReceivableFeeUrgedService() {
		return receivableFeeUrgedService;
	}
	public void setReceivableFeeUrgedService(BigDecimal receivableFeeUrgedService) {
		this.receivableFeeUrgedService = receivableFeeUrgedService;
	}
	public BigDecimal getReceivedfeeUrgedService() {
		return receivedfeeUrgedService;
	}
	public void setReceivedfeeUrgedService(BigDecimal receivedfeeUrgedService) {
		this.receivedfeeUrgedService = receivedfeeUrgedService;
	}
	public String getColResult() {
		return colResult;
	}
	public void setColResult(String colResult) {
		this.colResult = colResult;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Date getLastDeductDate() {
		return lastDeductDate;
	}
	public void setLastDeductDate(Date lastDeductDate) {
		this.lastDeductDate = lastDeductDate;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getDeductBeginTime() {
		return deductBeginTime;
	}
	public void setDeductBeginTime(String deductBeginTime) {
		this.deductBeginTime = deductBeginTime;
	}
	public String getDeductEndTime() {
		return deductEndTime;
	}
	public void setDeductEndTime(String deductEndTime) {
		this.deductEndTime = deductEndTime;
	}
	public String getLendingTimeBegin() {
		return lendingTimeBegin;
	}
	public void setLendingTimeBegin(String lendingTimeBegin) {
		this.lendingTimeBegin = lendingTimeBegin;
	}
	public String getLendingTimeEnd() {
		return lendingTimeEnd;
	}
	public void setLendingTimeEnd(String lendingTimeEnd) {
		this.lendingTimeEnd = lendingTimeEnd;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}
	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}
	public int getMonthOverdueDaysMax() {
		return monthOverdueDaysMax;
	}
	public void setMonthOverdueDaysMax(int monthOverdueDaysMax) {
		this.monthOverdueDaysMax = monthOverdueDaysMax;
	}
	public String getBackMoneyDesc() {
		return backMoneyDesc;
	}
	public void setBackMoneyDesc(String backMoneyDesc) {
		this.backMoneyDesc = backMoneyDesc;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayStatusLabel() {
		return payStatusLabel;
	}
	public void setPayStatusLabel(String payStatusLabel) {
		this.payStatusLabel = payStatusLabel;
	}
	public String getContractCodes() {
		return contractCodes;
	}
	public void setContractCodes(String contractCodes) {
		this.contractCodes = contractCodes;
	}
	public BigDecimal getFeeCredit() {
		return feeCredit;
	}
	public void setFeeCredit(BigDecimal feeCredit) {
		this.feeCredit = feeCredit;
	}
	public BigDecimal getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}
	public BigDecimal getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(BigDecimal feeSum) {
		this.feeSum = feeSum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
