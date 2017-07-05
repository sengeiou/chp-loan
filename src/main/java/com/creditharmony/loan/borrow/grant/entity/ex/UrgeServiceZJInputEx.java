package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款当日待划扣列表通联平台导出
 * @Class Name UrgeServiceTlEx
 * @author
 * @Create In 2016年3月2日
 */
@SuppressWarnings("serial")
public class UrgeServiceZJInputEx extends DataEntity<UrgeServiceZJInputEx> {
    //序号
    @ExcelField(title = "序号", type = 2, align = 2, sort = 1)
    private String index;
    //交易时间
    @ExcelField(title = "交易时间", type = 2, align = 2, sort = 2)
    private String transactionTime;
    //机构名称
    @ExcelField(title = "机构名称", type = 2, align = 2, sort = 3)
    private String institutionName;
    //批次号
    @ExcelField(title = "批次号", type = 2, align = 2, sort = 4)
    private String batchNumber;
    //明细号
    @ExcelField(title = "明细号", type = 2, align = 2, sort = 5)
    private String detailNo;
	//金额
	@ExcelField(title = "金额", type = 2, align = 2, sort = 6)
	private String transactionAmount;
    //银行ID
	@ExcelField(title = "银行ID", type = 2, align = 2, sort = 7)
	private String bankId;
    //账户类型
	@ExcelField(title = "账户类型", type = 2, align = 2, sort = 8)
	private String accountType;
    //账户号码
	@ExcelField(title = "账户号码", type = 2, align = 2, sort = 9)
	private String bankAccount;
    //账户名称
	@ExcelField(title = "账户名称", type = 2, align = 2, sort = 10)
	private String bankAccountName;
    //分支行名称
	@ExcelField(title = "分支行名称", type = 2, align = 2, sort = 11)
	private String branchName;
    //分支行省份
	@ExcelField(title = "分支行省份", type = 2, align = 2, sort = 12)
	private String bankProvince;
    //分支行城市
	@ExcelField(title = "分支行城市", type = 2, align = 2, sort = 13)
	private String bankCity;
    //备注信息
	@ExcelField(title = "备注信息", type = 2, align = 2, sort = 14)
	private String enterpriseSerialno;
    //协议用户编号
	@ExcelField(title = "协议用户编号", type = 2, align = 2, sort = 15)
	private String customUserName;
    //协议号
	@ExcelField(title = "协议号", type = 2, align = 2, sort = 16)
	private String xyNo;
    //手机号码
	@ExcelField(title = "手机号码", type = 2, align = 2, sort = 17)
	private String customerPhoneFirst;
    //电子邮件
	@ExcelField(title = "电子邮件", type = 2, align = 2, sort = 18)
	private String email;
    //证件类型
	@ExcelField(title = "证件类型", type = 2, align = 2, sort = 19)
	private String dictertType;
    //证件号码
	@ExcelField(title = "证件号码", type = 2, align = 2, sort = 20)
	private String customerCertNum;
    //银行代收的时间
	@ExcelField(title = "银行代收的时间", type = 2, align = 2, sort = 21)
	private String bankCollectionTime;
    //交易状态
	@ExcelField(title = "交易状态", type = 2, align = 2, sort = 22)
	private String transactionStauts;
    //银行响应代码
	@ExcelField(title = "银行响应代码", type = 2, align = 2, sort = 23)
	private String bankRespond;
    //银行响应消息
	@ExcelField(title = "银行响应消息", type = 2, align = 2, sort = 24)
	private String bankRespondMsg;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
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
	public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}
	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}
	public String getCustomUserName() {
		return customUserName;
	}
	public void setCustomUserName(String customUserName) {
		this.customUserName = customUserName;
	}
	public String getXyNo() {
		return xyNo;
	}
	public void setXyNo(String xyNo) {
		this.xyNo = xyNo;
	}
	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}
	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDictertType() {
		return dictertType;
	}
	public void setDictertType(String dictertType) {
		this.dictertType = dictertType;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getBankCollectionTime() {
		return bankCollectionTime;
	}
	public void setBankCollectionTime(String bankCollectionTime) {
		this.bankCollectionTime = bankCollectionTime;
	}
	public String getTransactionStauts() {
		return transactionStauts;
	}
	public void setTransactionStauts(String transactionStauts) {
		this.transactionStauts = transactionStauts;
	}
	public String getBankRespond() {
		return bankRespond;
	}
	public void setBankRespond(String bankRespond) {
		this.bankRespond = bankRespond;
	}
	public String getBankRespondMsg() {
		return bankRespondMsg;
	}
	public void setBankRespondMsg(String bankRespondMsg) {
		this.bankRespondMsg = bankRespondMsg;
	}
	public String getDetailNo() {
		return detailNo;
	}
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}
}