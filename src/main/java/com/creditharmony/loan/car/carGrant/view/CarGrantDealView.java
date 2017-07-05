package com.creditharmony.loan.car.carGrant.view;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseBusinessView;

/**
 * 页面初始化，修改字段的信息
 * @Class Name CarGrantDealView
 * @Create In 2016年2月1日
 */
public class CarGrantDealView extends BaseBusinessView{
	
	//借款编码
	private String loanCode;
	// 客户姓名
	private String customerName;
	//门店名称
	private String storeName;
	//证件号码
	private String certNum;
	//合同编号
	private String contractCode;
	//开户行
	private String cardBank;
	//是否电销
	private String loanIsPhone;
	//借款产品
	private String borrowProductCode;
	//合同金额
	private Double contractAmount;
	//放款金额
	private BigDecimal grantAmount;
    // 放款失败金额
    private double grantFailAmount;
	//批借期限(天)
	private Integer loanMonths;
	//划扣金额
	private Double deductsAmount;
	//总费率
	private Double grossRate;
	//借款状态
	private String dictLoanStatus;

	//放款途径
    private String dictLoanWay;

    //标识
    private String dictLoanFlag;
	// 中间人id
	private String midId;
	// 放款开户行
	private String midBankName;
	// 中间人姓名
	private String midBankCardName;
	// 中间人放款账号
	private String midBankCardNo;
	// 放款人员编号
	private String grantPersons;
	// 审核专员
	private String checkEmpId;
	// 审核结果
	private String checkResult;
	// 退回原因 (更改车借信息表)
	private String dictBackMestype;
	// 放款审核退回原因
	private String grantBackMes;
	//备注(更改车借信息表)
	private String remark;
	// 放款时间
	private Date lendingTime;
	// 审核时间
	private Date checkTime;
	// 放款回执结果
	private String grantRecepicResult;
	// 线上放款失败，失败原因
	private String grantFailResult;
	// 是否加盖失败
    private String signUpFlag;
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	//是否可办理，0：可办理，1：不可办理
	private Integer canHandle;
	
	//放款退回 原因 code
	private String grantBackResultCode;

	//合同版本号
	private String contractVersion;

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDictBackMestype() {
		return dictBackMestype;
	}
	public void setDictBackMestype(String dictBackMestype) {
		this.dictBackMestype = dictBackMestype;
	}
	public String getGrantBackMes() {
		return grantBackMes;
	}
	public void setGrantBackMes(String grantBackMes) {
		this.grantBackMes = grantBackMes;
	}
	public Integer getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}
	public String getCheckEmpId() {
		return checkEmpId;
	}
	public void setCheckEmpId(String checkEmpId) {
		this.checkEmpId = checkEmpId;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}
	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}
	public String getGrantFailResult() {
		return grantFailResult;
	}
	public void setGrantFailResult(String grantFailResult) {
		this.grantFailResult = grantFailResult;
	}

	public String getMidId() {
		return midId;
	}
	public void setMidId(String midId) {
		this.midId = midId;
	}
	public String getMidBankName() {
		return midBankName;
	}
	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getGrantPersons() {
		return grantPersons;
	}
	public void setGrantPersons(String grantPersons) {
		this.grantPersons = grantPersons;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}

	public Double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Double getDeductsAmount() {
		return deductsAmount;
	}
	public void setDeductsAmount(Double deductsAmount) {
		this.deductsAmount = deductsAmount;
	}
	public Double getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(Double grossRate) {
		this.grossRate = grossRate;
	}

	public String getDictLoanWay() {
		return dictLoanWay;
	}
	public void setDictLoanWay(String dictLoanWay) {
		this.dictLoanWay = dictLoanWay;
	}

	public String getDictLoanFlag() {
		return dictLoanFlag;
	}
	public void setDictLoanFlag(String dictLoanFlag) {
		this.dictLoanFlag = dictLoanFlag;
	}
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	
	public double getGrantFailAmount() {
		return grantFailAmount;
	}
	public void setGrantFailAmount(double grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}

	public String getMidBankCardName() {
		return midBankCardName;
	}
	public void setMidBankCardName(String midBankCardName) {
		this.midBankCardName = midBankCardName;
	}
	public String getMidBankCardNo() {
		return midBankCardNo;
	}
	public void setMidBankCardNo(String midBankCardNo) {
		this.midBankCardNo = midBankCardNo;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getSignUpFlag() {
		return signUpFlag;
	}
	public void setSignUpFlag(String signUpFlag) {
		this.signUpFlag = signUpFlag;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getFirstBackSourceStep() {
		return firstBackSourceStep;
	}
	public void setFirstBackSourceStep(String firstBackSourceStep) {
		this.firstBackSourceStep = firstBackSourceStep;
	}
	public Integer getCanHandle() {
		return canHandle;
	}
	public void setCanHandle(Integer canHandle) {
		this.canHandle = canHandle;
	}
	public String getGrantBackResultCode() {
		return grantBackResultCode;
	}
	public void setGrantBackResultCode(String grantBackResultCode) {
		this.grantBackResultCode = grantBackResultCode;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
}

