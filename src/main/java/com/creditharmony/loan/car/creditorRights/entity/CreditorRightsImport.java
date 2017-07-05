package com.creditharmony.loan.car.creditorRights.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.common.type.LoanDictType;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
 * @Class Name CreditorRights
 * @author 陈伟东
 * @Create In 2016年3月5日
 */
public class CreditorRightsImport  extends DataEntity<CreditorRightsImport>{

	private static final long serialVersionUID = 7500120246309735352L;
	//借款编码
	private String loanCode;
	//序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1)
	private String rm;
	// 合同编号
	@NotNull 
    @ExcelField(title = "合同编号", type = 0, align = 2, sort = 2, hasNull=1)
	private String contractCode; 
	//借款人姓名
	@NotNull 
	@ExcelField(title = "借款人", type = 0, align = 2, sort = 3, hasNull=1 )
	private String loanCustomerName;
	// 证件类型
    private String certType;
	// 证件号码
	@ExcelField(title = "借款人身份证号", type = 0, align = 2, sort = 4, hasNull=1)
    private String customerCertNum;
    //债权来源
	@ExcelField(title = "债权来源", type = 0, align = 2, sort = 5, hasNull=1)
    private String creditorRigthSource;
	//借款产品
    @ExcelField(title = "借款产品", type = 0, align = 2, sort = 6, hasNull=1)
    private String productType;
    //借款用途
    @ExcelField(title = "借款用途", type = 0, align = 2, sort = 7, hasNull=1, dictType=LoanDictType.JK_LOAN_USE)
    private String usageOfLoan;
    //职业情况
    @ExcelField(title = "职业情况", type = 0, align = 2, sort = 8, hasNull=1, dictType=LoanDictType.CAR_OCCUPATION_CASE)
    private String occupationCase;
    //车牌号码
	@ExcelField(title = "车牌号码", type = 0, align = 2, sort = 9, hasNull=1)
    private String plateNumbers;
    //首期还款日
    @ExcelField(title = "首次还款日", type = 0, align = 2, sort = 10, hasNull=1)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date downPaymentDay;
    //合同签约日期
    @ExcelField(title = "合同签约日期", type = 0, align = 2, sort = 11, hasNull=1)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractDay; 
    //借款天数
    @ExcelField(title = "借款天数", type = 0, align = 2, sort = 12, hasNull=1)
    private Integer loanDays;
    //截止还款日期
    @ExcelField(title = "截止还款日期", type = 0, align = 2, sort = 13, hasNull=1)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractEndDay;
    //月利率
    @ExcelField(title = "月利率", type = 0, align = 2, sort = 14, hasNull=1)
    private Double monthlyInterestRate;
    //原始债权价值
    @ExcelField(title = "原始债权价值", type = 0, align = 2, sort = 15, hasNull=1)
    private Double primevalWorth;
    //还款金额
    @ExcelField(title = "还款金额", type = 0, align = 2, sort = 16, hasNull=1)
    private Double contractReplayAmount;
    //还款方式
    @ExcelField(title = "还款方式", type = 0, align = 2, sort = 17, hasNull=1, dictType="jk_car_repay_interest_way")
    private String contractReplayWay;
    //标识
    @ExcelField(title = "标识", type = 0, align = 2, sort = 18, hasNull=1, dictType=LoanDictType.JK_CAR_THROUTH_FLAG)
    private String channelType;
    //债权人
    @ExcelField(title = "债权人", type = 0, align = 2, sort = 19, hasNull=1)
    private String creditor;
    
    private String rightsType;

    private String creditType;
    //借款状态
    private String loanStatus;
    //是否发送财富
    private String issendWealth;
    private String customerCobo;
    private int loanMonths;
    
    
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
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
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getCreditorRigthSource() {
		return creditorRigthSource;
	}
	public void setCreditorRigthSource(String creditorRigthSource) {
		this.creditorRigthSource = creditorRigthSource;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getUsageOfLoan() {
		return usageOfLoan;
	}
	public void setUsageOfLoan(String usageOfLoan) {
		this.usageOfLoan = usageOfLoan;
	}
	public String getOccupationCase() {
		return occupationCase;
	}
	public void setOccupationCase(String occupationCase) {
		this.occupationCase = occupationCase;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public Date getDownPaymentDay() {
		return downPaymentDay;
	}
	public void setDownPaymentDay(Date downPaymentDay) {
		this.downPaymentDay = downPaymentDay;
	}
	public Date getContractDay() {
		return contractDay;
	}
	public void setContractDay(Date contractDay) {
		this.contractDay = contractDay;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public String getCreditor() {
		return creditor;
	}
	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}
	public String getContractReplayWay() {
		return contractReplayWay;
	}
	public void setContractReplayWay(String contractReplayWay) {
		this.contractReplayWay = contractReplayWay;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public Double getMonthlyInterestRate() {
		return monthlyInterestRate;
	}
	public void setMonthlyInterestRate(Double monthlyInterestRate) {
		this.monthlyInterestRate = monthlyInterestRate;
	}
	public Double getContractReplayAmount() {
		return contractReplayAmount;
	}
	public void setContractReplayAmount(Double contractReplayAmount) {
		this.contractReplayAmount = contractReplayAmount;
	}
	public Integer getLoanDays() {
		return loanDays;
	}
	public void setLoanDays(Integer loanDays) {
		this.loanDays = loanDays;
	}
	public Double getPrimevalWorth() {
		return primevalWorth;
	}
	public void setPrimevalWorth(Double primevalWorth) {
		this.primevalWorth = primevalWorth;
	}
	public String getRightsType() {
		return rightsType;
	}
	public void setRightsType(String rightsType) {
		this.rightsType = rightsType;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getIssendWealth() {
		return issendWealth;
	}
	public void setIssendWealth(String issendWealth) {
		this.issendWealth = issendWealth;
	}
	public String getCustomerCobo() {
		return customerCobo;
	}
	public void setCustomerCobo(String customerCobo) {
		this.customerCobo = customerCobo;
	}
	public int getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(int loanMonths) {
		this.loanMonths = loanMonths;
	}
    
}
