package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 待还款匹配列表信息
 * @Class Name CarRefundView
 * @author 蒋力
 * @Create In 2016年3月3日
 */
public class CarPendingRepayMatchInfo extends DataEntity<CarPendingRepayMatchInfo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5748359001901925438L;
	
	//关联催收服务费ID
	private String urgeId;
	//合同编号
	private String contractCode;
	//客户姓名
	private String loanCustomerName;
	//门店名称
	private String storeName;
	//存入账户
	private String storesInAccount;
	//合同金额
	private double contractAmount;
	//申请还款金额
	private BigDecimal applyRepayAmount;
	//追回金额
	private double reallyAmount;
	//借款状态
	private String dictLoanStatus;
	//蓝补金额
	private double blueAmount;
	//查账类型
	private String checkType;
	
	//申请还款金额条件 大于(查询条件)
	private String applyRepayAmountUp;
	//申请还款金额条件 小于(查询条件)
	private String applyRepayAmountDown;
	//申请还款金额条件 大于(查询条件)
	private double amountUp;
	//申请还款金额条件 小于(查询条件)
	private double amountDown;
	//标识(查询条件)
	private String mark;
	// 还款日(查询条件)
	private String paybackDay;
	// 来源系统
	private String dictSourceType;
	
	private String customerCertNum;//证件号码
	private String productType;//生产类型
	private String contractMonths;//借款期限
	private String dictRepayMethod;//还款方式
	private Date applyPayDay;//还款申请日期
	
	private String matchingResult;//匹配结果
	private String auditCheckExamine;//审批意见
	
	public double getAmountUp() {
		return amountUp;
	}
	public void setAmountUp(double amountUp) {
		this.amountUp = amountUp;
	}
	public double getAmountDown() {
		return amountDown;
	}
	public void setAmountDown(double amountDown) {
		this.amountDown = amountDown;
	}
	public String getApplyRepayAmountUp() {
		return applyRepayAmountUp;
	}
	public void setApplyRepayAmountUp(String applyRepayAmountUp) {
		this.applyRepayAmountUp = applyRepayAmountUp;
	}
	public String getApplyRepayAmountDown() {
		return applyRepayAmountDown;
	}
	public void setApplyRepayAmountDown(String applyRepayAmountDown) {
		this.applyRepayAmountDown = applyRepayAmountDown;
	}
	public String getMatchingResult() {
		return matchingResult;
	}
	public void setMatchingResult(String matchingResult) {
		this.matchingResult = matchingResult;
	}
	public String getAuditCheckExamine() {
		return auditCheckExamine;
	}
	public void setAuditCheckExamine(String auditCheckExamine) {
		this.auditCheckExamine = auditCheckExamine;
	}
	public Date getApplyPayDay() {
		return applyPayDay;
	}
	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getDictRepayMethod() {
		return dictRepayMethod;
	}
	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getPaybackDay() {
		return paybackDay;
	}
	public void setPaybackDay(String paybackDay) {
		this.paybackDay = paybackDay;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoresInAccount() {
		return storesInAccount;
	}
	public void setStoresInAccount(String storesInAccount) {
		this.storesInAccount = storesInAccount;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getApplyRepayAmount() {
		return applyRepayAmount;
	}
	public void setApplyRepayAmount(BigDecimal applyRepayAmount) {
		this.applyRepayAmount = applyRepayAmount;
	}
	public double getReallyAmount() {
		return reallyAmount;
	}
	public void setReallyAmount(double reallyAmount) {
		this.reallyAmount = reallyAmount;
	}
	public double getBlueAmount() {
		return blueAmount;
	}
	public void setBlueAmount(double blueAmount) {
		this.blueAmount = blueAmount;
	}
	public String getUrgeId() {
		return urgeId;
	}
	public void setUrgeId(String urgeId) {
		this.urgeId = urgeId;
	}

}