package com.creditharmony.loan.channel.jyj.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 查账已办列表
 * @Class Name UrgeCheckDoneEx
 * @author 朱静越
 * @Create In 2016年3月2日
 */
@SuppressWarnings("serial")
public class UrgeCheckJYJDoneEx extends DataEntity<UrgeCheckJYJDoneEx> {

	// 首次放款金额
	@ExcelField(title = "首次放款金额", type = 0, align = 2, sort = 72)
	private BigDecimal firstGrantAmount;
	// 费用总金额
	@ExcelField(title = "费用总金额", type = 0, align = 2, sort = 74)
	private BigDecimal allFee;
	// 前期综合服务费
	@ExcelField(title = "前期综合服务费", type = 0, align = 2, sort = 76)
	private BigDecimal feeCount;
	// 加急费
	@ExcelField(title = "加急费", type = 0, align = 2, sort = 78)
	private BigDecimal feeExpedited;
	// 信访费10，新
	@ExcelField(title = "外访费", type = 0, align = 2, sort = 77)
	private BigDecimal feePetition;
	// 信访费10，新
	@ExcelField(title = "待查账金额", type = 0, align = 2, sort = 100)
	private BigDecimal urgeAmount;
	
	
	
	
	
	
	
	public BigDecimal getFirstGrantAmount() {
		return firstGrantAmount;
	}

	public void setFirstGrantAmount(BigDecimal firstGrantAmount) {
		this.firstGrantAmount = firstGrantAmount;
	}

	public BigDecimal getAllFee() {
		return allFee;
	}

	public void setAllFee(BigDecimal allFee) {
		this.allFee = allFee;
	}

	public BigDecimal getFeeCount() {
		return feeCount;
	}

	public void setFeeCount(BigDecimal feeCount) {
		this.feeCount = feeCount;
	}

	public BigDecimal getFeeExpedited() {
		return feeExpedited;
	}

	public void setFeeExpedited(BigDecimal feeExpedited) {
		this.feeExpedited = feeExpedited;
	}

	public BigDecimal getFeePetition() {
		return feePetition;
	}

	public void setFeePetition(BigDecimal feePetition) {
		this.feePetition = feePetition;
	}

	public BigDecimal getUrgeAmount() {
		return urgeAmount;
	}

	public void setUrgeAmount(BigDecimal urgeAmount) {
		this.urgeAmount = urgeAmount;
	}

	// 关联类型,催收服务费
	public static final String DEDUCT_TYPE="1";
	// 查账申请id
	private String checkApplyId;
	// 关联催收服务费id
	private String rServiceChargeId;
	
	// 合同编号
	@ExcelField(title = "", type = 0, align = 2, sort = 10)
	private String num;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	// 门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String storeName;
	// 门店id
	private String orgId;
	// 证件号码
	private String customerCertNum;
	// 存入账户,指的是哪个银行
	private String storesInAccountname;
	// 存入卡号，指的是存入哪个卡号
	private String storesInAccount;
	// 批借期限
	@ExcelField(title = "批借期限", type = 0, align = 2, sort = 50)
	private int contractMonths;
	// 借款状态
	//@ExcelField(title = "借款状态", type = 0, align = 2, sort = 60,dictType="jk_loan_apply_status")
	private String dictLoanStatus;
	// 借款状态(名称)
	private String dictLoanStatusLabel;
	// 合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 70)
	private BigDecimal contractAmount;
	// 放款金额
	//@ExcelField(title = "放款金额", type = 0, align = 2, sort = 80)
	private BigDecimal grantAmount;
	// 催收金额
	@ExcelField(title = "催收服务费总金额", type = 0, align = 2, sort = 90)
	private BigDecimal urgeMoeny;
	// 待催收金额
	//@ExcelField(title = "待催收金额", type = 0, align = 2, sort = 100)
	private BigDecimal waitUrgeMoeny;
	// 申请金额/待查账金额
	@ExcelField(title = "申请查账金额", type = 0, align = 2, sort = 110)
	private BigDecimal urgeApplyAmount;
	// 实际到账金额
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 120)
	private BigDecimal urgeReallyAmount;
	// 实还金额最小值
	private BigDecimal urgeReallyAmountMin;
	// 实还金额最大值
	private BigDecimal urgeReallyAmountMax;
	// 查账日期不知道是否为申请表中的申请日期
	@ExcelField(title = "查账日期", type = 0, align = 2, sort = 130)
	private Date urgeApplyDate;
	// 查账开始日期
	private Date urgeApplyBeginDate;
	// 查账结束日期
	private Date urgeApplyEndDate;
	// 回盘结果
	@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 140,dictType="jk_urge_counteroffer_result")
	private String urgeApplyStatus;
	// 回盘结果(名称)
	private String urgeApplyStatusLabel;
	private String urgeBackReason;
	// 标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 160,dictType = "jk_channel_flag")
	private String loanFlag;
	// 标识(名称)		
	private String loanFlagLabel;
	// 产品名称
	private String productName;
	// 中间人存入银行
	@ExcelField(title = "存入账户", type = 0, align = 2, sort = 40)
	private String midBankName;
	// 中间人账号
	private String bankCardNo;
	// 退款标识
	private String refundFlag;
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCheckApplyId() {
		return checkApplyId;
	}

	public void setCheckApplyId(String checkApplyId) {
		this.checkApplyId = checkApplyId;
	}
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public BigDecimal getUrgeReallyAmountMin() {
		return urgeReallyAmountMin;
	}

	public void setUrgeReallyAmountMin(BigDecimal urgeReallyAmountMin) {
		this.urgeReallyAmountMin = urgeReallyAmountMin;
	}

	public BigDecimal getUrgeReallyAmountMax() {
		return urgeReallyAmountMax;
	}

	public void setUrgeReallyAmountMax(BigDecimal urgeReallyAmountMax) {
		this.urgeReallyAmountMax = urgeReallyAmountMax;
	}

	public void setWaitUrgeMoeny(BigDecimal waitUrgeMoeny) {
		this.waitUrgeMoeny = waitUrgeMoeny;
	}

	public BigDecimal getWaitUrgeMoeny() {
		return waitUrgeMoeny;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public int getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}

	public BigDecimal getUrgeMoeny() {
		return urgeMoeny;
	}

	public void setUrgeMoeny(BigDecimal urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
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

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getStoresInAccountname() {
		return storesInAccountname;
	}

	public void setStoresInAccountname(String storesInAccountname) {
		this.storesInAccountname = storesInAccountname;
	}

	public BigDecimal getUrgeApplyAmount() {
		return urgeApplyAmount;
	}

	public void setUrgeApplyAmount(BigDecimal urgeApplyAmount) {
		this.urgeApplyAmount = urgeApplyAmount;
	}

	public BigDecimal getUrgeReallyAmount() {
		return urgeReallyAmount;
	}

	public void setUrgeReallyAmount(BigDecimal urgeReallyAmount) {
		this.urgeReallyAmount = urgeReallyAmount;
	}

	public Date getUrgeApplyDate() {
		return urgeApplyDate;
	}

	public void setUrgeApplyDate(Date urgeApplyDate) {
		this.urgeApplyDate = urgeApplyDate;
	}

	public String getUrgeApplyStatus() {
		return urgeApplyStatus;
	}

	public void setUrgeApplyStatus(String urgeApplyStatus) {
		this.urgeApplyStatus = urgeApplyStatus;
	}

	public String getUrgeBackReason() {
		return urgeBackReason;
	}

	public void setUrgeBackReason(String urgeBackReason) {
		this.urgeBackReason = urgeBackReason;
	}

	public Date getUrgeApplyBeginDate() {
		return urgeApplyBeginDate;
	}

	public void setUrgeApplyBeginDate(Date urgeApplyBeginDate) {
		this.urgeApplyBeginDate = urgeApplyBeginDate;
	}

	public Date getUrgeApplyEndDate() {
		return urgeApplyEndDate;
	}

	public void setUrgeApplyEndDate(Date urgeApplyEndDate) {
		this.urgeApplyEndDate = urgeApplyEndDate;
	}

	public String getCustomerCertNum() {
		return customerCertNum;
	}

	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStoresInAccount() {
		return storesInAccount;
	}

	public void setStoresInAccount(String storesInAccount) {
		this.storesInAccount = storesInAccount;
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

	public String getrServiceChargeId() {
		return rServiceChargeId;
	}

	public void setrServiceChargeId(String rServiceChargeId) {
		this.rServiceChargeId = rServiceChargeId;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public String getUrgeApplyStatusLabel() {
		return urgeApplyStatusLabel;
	}

	public void setUrgeApplyStatusLabel(String urgeApplyStatusLabel) {
		this.urgeApplyStatusLabel = urgeApplyStatusLabel;
	}

	public String getLoanFlagLabel() {
		return loanFlagLabel;
	}

	public void setLoanFlagLabel(String loanFlagLabel) {
		this.loanFlagLabel = loanFlagLabel;
	}
	
	
}