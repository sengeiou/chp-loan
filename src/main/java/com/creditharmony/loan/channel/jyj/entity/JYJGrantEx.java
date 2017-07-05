
package com.creditharmony.loan.channel.jyj.entity;


import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 待放款列表中的待放款列表导出，关于用户,type=2，仅导入
 * @Class Name GrantEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class JYJGrantEx extends DataEntity<JYJGrantEx> {
	private static final long serialVersionUID = 7783081129793432292L;
	// 流程id
	private String applyId;
	// 放款id,
	private String id;
	// 借款编号
	private String loanCode;
	// 合同编号1
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode; 
	// 用户卡号2
	@ExcelField(title = "收款账户 ", type = 0, align = 2, sort = 20)
	private String bankAccount;	
	// 客户姓名3
	@ExcelField(title = "收款户名", type = 0, align = 2, sort = 30)
	private String  customerName;
	// 期数10
	@ExcelField(title = "期数", type = 0, align = 2, sort = 100)
	private Integer  contractMonths;
	// 放款金额4
	@ExcelField(title = "首次放款金额", type = 0, align = 2, sort = 40)
	private String grantAmount;
	// 尾次放款金额
	private String lastGrantAmount;
	// 首次放款金额
	private String lendingMoney;
	// 开户行6
	@ExcelField(title = "收款银行", type = 0, align = 2, sort = 60)
	private String  bankName;
	// 备注列5
	@ExcelField(title = "备注", type = 0, align = 2, sort = 50)
	private String storesName;
	// 具体支行7
	@ExcelField(title = "收款银行支行", type = 0, align = 2, sort = 70)
	private String  bankBranch;	
	// 收款直省8
	@ExcelField(title = "收款省", type = 0, align = 2, sort = 80)
	private String  bankProvince;
	// 收款市9
	@ExcelField(title = "收款市", type = 0, align = 2, sort = 90)
	private String  bankCity;	
	// 合同金额11
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 110)
	private String contractAmount;
	// 是否电销
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 120 , dictType = "jk_telemarketing")
	private String customerTelesalesFlag;
	// 合同版本号
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 130)
	private String contractVersion;	
	// 催收服务费
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 140)
	private String feeUrgedServiceStr;
	// 划扣金额
	@ExcelField(title = "划扣金额", type = 0, align = 2, sort = 150)
	private String urgeMoney;
	// 是否加急
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 160,dictType="jk_urgent_flag")
	private String loanUrgentFlag;
	// 放款批次
	@ExcelField(title = "放款批次", type = 0, align = 2, sort = 170)
	private String grantBatchCode;
	// 放款途径
	private String dictLoanWay;
	// 催收服务费
	private BigDecimal feeUrgedService;
	//拆分标记
    private String issplit;
    // 回执结果
    private String grantRecepicResult;
	
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		
	public Integer getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}
	
	public String getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
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
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getFeeUrgedServiceStr() {
		return feeUrgedServiceStr;
	}
	public void setFeeUrgedServiceStr(String feeUrgedServiceStr) {
		this.feeUrgedServiceStr = feeUrgedServiceStr;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}
	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public String getGrantBatchCode() {
		return grantBatchCode;
	}
	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}
	public String getDictLoanWay() {
		return dictLoanWay;
	}
	public void setDictLoanWay(String dictLoanWay) {
		this.dictLoanWay = dictLoanWay;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getIssplit() {
		return issplit;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}
	public String getUrgeMoney() {
		return urgeMoney;
	}
	public void setUrgeMoney(String urgeMoney) {
		this.urgeMoney = urgeMoney;
	}
	public String getLendingMoney() {
		return lendingMoney;
	}
	public void setLendingMoney(String lendingMoney) {
		this.lendingMoney = lendingMoney;
	}
	public String getLastGrantAmount() {
		return lastGrantAmount;
	}
	public void setLastGrantAmount(String lastGrantAmount) {
		this.lastGrantAmount = lastGrantAmount;
	}
	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}
	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}
}