package com.creditharmony.loan.borrow.serve.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 汇金邮件管理列表
 * 
 * @Class Name PaybackMonthSendEmail
 * @author 于飞
 * @Create In 2017年3月6日
 */
public class PaybackMonthSendEmail extends DataEntity<PaybackMonthSendEmail> {

	
	private static final long serialVersionUID = 1L;
	//期供id
	private String paybackMonthId;
	//客户姓名
	@ExcelField(title = "客户名称", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String customerName;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String contractCode;
	//门店名称
	@ExcelField(title = "门店", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String storeName;
	//门店ID
	private String storeId;
	//借款状态
	private String loanStatus;
	//借款状态名称
	@ExcelField(title = "借款状态", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String loanStatusLabel;
	//放款金额
	@ExcelField(title = "放款金额", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private BigDecimal auditAmount;
	//月还金额
	@ExcelField(title = "月还金额", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private BigDecimal contractMonthRepayAmount;
	//合同金额
	@ExcelField(title = "合同金额", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private BigDecimal contractAmount;
	//还款日
	@ExcelField(title = "还款日", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private int paybackDay;
	//邮箱
	@ExcelField(title = "邮箱", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String customerEmail;
	//发送状态
	private String sendEmailStatus;
	//发送状态名称
	@ExcelField(title = "发送状态", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String sendEmailStatusLabel;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifyTime;
	//是否无纸化
	private String paperlessFlag;
	//邮件类型
	private String emailType;
	//邮件类型名称
	private String emailTypeLabel;
	//开户行
	private String bankName;
	//开户账户
	private String bankAccount;
	//发送时间字符串
	private String modifyTimeStr;
	//邮件导出类型
	private String exportType;//1:还款提醒  2：已发送
	//排序
	private String orderBy;
	//起始时间
	private String startTime;
	//截止时间
	private String endTime;
	//邮箱是否验证通过
	private String emailIfConfirm;
	private String customerCode;
	public String getPaybackMonthId() {
		return paybackMonthId;
	}
	public void setPaybackMonthId(String paybackMonthId) {
		this.paybackMonthId = paybackMonthId;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public BigDecimal getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}
	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}
	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public int getPaybackDay() {
		return paybackDay;
	}
	public void setPaybackDay(int paybackDay) {
		this.paybackDay = paybackDay;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getSendEmailStatus() {
		return sendEmailStatus;
	}
	public void setSendEmailStatus(String sendEmailStatus) {
		this.sendEmailStatus = sendEmailStatus;
	}
	public String getSendEmailStatusLabel() {
		return sendEmailStatusLabel;
	}
	public void setSendEmailStatusLabel(String sendEmailStatusLabel) {
		this.sendEmailStatusLabel = sendEmailStatusLabel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPaperlessFlag() {
		return paperlessFlag;
	}
	public void setPaperlessFlag(String paperlessFlag) {
		this.paperlessFlag = paperlessFlag;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getEmailTypeLabel() {
		return emailTypeLabel;
	}
	public void setEmailTypeLabel(String emailTypeLabel) {
		this.emailTypeLabel = emailTypeLabel;
	}
	public String getLoanStatusLabel() {
		return loanStatusLabel;
	}
	public void setLoanStatusLabel(String loanStatusLabel) {
		this.loanStatusLabel = loanStatusLabel;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyTimeStr() {
		return modifyTimeStr;
	}
	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
	public String getEmailIfConfirm() {
		return emailIfConfirm;
	}
	public void setEmailIfConfirm(String emailIfConfirm) {
		this.emailIfConfirm = emailIfConfirm;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
}
