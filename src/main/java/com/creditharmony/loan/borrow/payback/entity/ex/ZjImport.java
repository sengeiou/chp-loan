/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exZjImportExport.java
 * @Create By 王彬彬
 * @Create In 2016年3月1日 上午11:52:10
 */
package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name ZjImportExport
 * @author 王彬彬
 * @Create In 2016年3月1日
 */
@SuppressWarnings("serial")
public class ZjImport extends DataEntity<ZjImport> {
	// 序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10, groups = { 1 })
	private String num;
	// 交易时间
	@ExcelField(title = "交易时间", type = 0, align = 2, sort = 20, groups = { 1 })
	private String tradeTime;
	// 机构名称
	@ExcelField(title = "机构名称", type = 0, align = 2, sort = 30, groups = { 1 })
	private String orgName;
	// 批次号
	@ExcelField(title = "批次号", type = 0, align = 2, sort = 40, groups = { 1 })
	private String batchNum;
	// 明细号
	@ExcelField(title = "明细号", type = 0, align = 2, sort = 50, groups = { 1 })
	private String detailNum;
	// 金额
	@ExcelField(title = "金额", type = 0, align = 2, sort = 60, groups = { 1 })
	private String amount;
	// 银行ID
	@ExcelField(title = "银行ID", type = 0, align = 2, sort = 70, groups = { 1 })
	private String bankCode;
	// 账户类型
	@ExcelField(title = "账户类型", type = 0, align = 2, sort = 80, groups = { 1 })
	private String accountType;
	// 账户号码
	@ExcelField(title = "账户号码", type = 0, align = 2, sort = 90, groups = { 1 })
	private String accountCode;
	// 账户名称
	@ExcelField(title = "账户名称", type = 0, align = 2, sort = 100, groups = { 1 })
	private String accountName;
	// 分支行名称
	@ExcelField(title = "分支行名称", type = 0, align = 2, sort = 110, groups = { 1 })
	private String branchBank;
	// 分支行省份
	@ExcelField(title = "分支行省份", type = 0, align = 2, sort = 120, groups = { 1 })
	private String province;
	// 分支行城市
	@ExcelField(title = "分支行城市", type = 0, align = 2, sort = 130, groups = { 1 })
	private String city;
	// 备注信息
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 140, groups = { 1 })
	private String remark;
	// 协议用户编号
	@ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 150, groups = { 1 })
	private String protocolUserCode;
	// 协议号
	@ExcelField(title = "协议号", type = 0, align = 2, sort = 160, groups = { 1 })
	private String protoclCode;
	// 手机号码
	@ExcelField(title = "手机号码", type = 0, align = 2, sort = 170, groups = { 1 })
	private String mobile;
	// 电子邮件
	@ExcelField(title = "电子邮件", type = 0, align = 2, sort = 180, groups = { 1 })
	private String email;
	// 证件类型
	@ExcelField(title = "证件类型", type = 0, align = 2, sort = 190, groups = { 1 })
	private String certType;
	// 证件号码
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 200, groups = { 1 })
	private String certNum;
	// 银行代收的时间
	@ExcelField(title = "银行代收的时间", type = 0, align = 2, sort = 210, groups = { 1 })
	private String bankTradeTime;
	// 交易状态
	@ExcelField(title = "交易状态", type = 0, align = 2, sort = 220, groups = { 1 })
	private String tradeStatus;
	// 银行响应代码
	@ExcelField(title = "银行响应代码", type = 0, align = 2, sort = 230, groups = { 1 })
	private String bankResponseCode;
	// 银行响应消息
	@ExcelField(title = "银行响应消息", type = 0, align = 2, sort = 240, groups = { 1 })
	private String bankResponseMsg;
	
	private boolean success;
	
	private BigDecimal splitAmount;
	
	private String returnPost;

	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getDetailNum() {
		return detailNum;
	}

	public void setDetailNum(String detailNum) {
		this.detailNum = detailNum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProtocolUserCode() {
		return protocolUserCode;
	}

	public void setProtocolUserCode(String protocolUserCode) {
		this.protocolUserCode = protocolUserCode;
	}

	public String getProtoclCode() {
		return protoclCode;
	}

	public void setProtoclCode(String protoclCode) {
		this.protoclCode = protoclCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getBankTradeTime() {
		return bankTradeTime;
	}

	public void setBankTradeTime(String bankTradeTime) {
		this.bankTradeTime = bankTradeTime;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getBankResponseCode() {
		return bankResponseCode;
	}

	public void setBankResponseCode(String bankResponseCode) {
		this.bankResponseCode = bankResponseCode;
	}

	public String getBankResponseMsg() {
		return bankResponseMsg;
	}

	public void setBankResponseMsg(String bankResponseMsg) {
		this.bankResponseMsg = bankResponseMsg;
	}

	public String getReturnPost() {
		return returnPost;
	}

	public void setReturnPost(String returnPost) {
		this.returnPost = returnPost;
	}
	
}
