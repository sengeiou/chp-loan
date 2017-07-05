package com.creditharmony.loan.borrow.zhongjin.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 还款汇账外部导入数据表
 * 
 * @Class Name PaybackCpcnIn
 * @author WJJ
 * @Create In 2016年3月16日
 */
@SuppressWarnings("paybackCpcnIn")
public class PaybackCpcnIn extends DataEntity<PaybackCpcnIn> {

	// 序列号
	@ExcelField(title = "序列号", type = 2, align = 2, sort = 10)
	private String serialNum;
	// 银行账号
	@ExcelField(title = "银行账号", type = 2, align = 2, sort = 20)
	private String accountNum;
	// 户名
	@ExcelField(title = "户名", type = 2, align = 2, sort = 30)
	private String accountName;
	// 金额
	@ExcelField(title = "金额", type = 0, align = 2, sort = 40)
	private String dealMoneyStr;
	private BigDecimal dealMoney;
	// 银行名称
	@ExcelField(title = "银行名称", type = 2, align = 2, sort = 50)
	private String bankName;
	private String bankNum;
	// 账户类型
	@ExcelField(title = "账户类型", type = 2, align = 2, sort = 60)
	private String accountType;
	// 分支行省份
	@ExcelField(title = "分支行省份", type = 2, align = 2, sort = 70)
	private String accounProvice;
	// 分支行城市
	@ExcelField(title = "分支行城市", type = 2, align = 2, sort = 80)
	private String accounCity;
	// 证件类型
	@ExcelField(title = "证件类型", type = 2, align = 2, sort = 90)
	private String certType;
	// 证件号码
	@ExcelField(title = "证件号码", type = 2, align = 2, sort = 100)
	private String certNum;
	// 合同编号
	@ExcelField(title = "合同编号", type = 2, align = 2, sort = 110)
	private String contractCode;
	// 备注
	@ExcelField(title = "备注", type = 2, align = 2, sort = 120)
	private String note;
	
	private String appoint;
	private String status;
	private String creatuserId;
	private Date   createTime;
	
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public BigDecimal getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccounProvice() {
		return accounProvice;
	}
	public void setAccounProvice(String accounProvice) {
		this.accounProvice = accounProvice;
	}
	public String getAccounCity() {
		return accounCity;
	}
	public void setAccounCity(String accounCity) {
		this.accounCity = accounCity;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getDealMoneyStr() {
		return dealMoneyStr;
	}
	public void setDealMoneyStr(String dealMoneyStr) {
		this.dealMoneyStr = dealMoneyStr;
	}
	public String getAppoint() {
		return appoint;
	}
	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatuserId() {
		return creatuserId;
	}
	public void setCreatuserId(String creatuserId) {
		this.creatuserId = creatuserId;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
}