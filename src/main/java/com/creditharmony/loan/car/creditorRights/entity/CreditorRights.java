package com.creditharmony.loan.car.creditorRights.entity;

import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 
 * @Class Name CreditorRights
 * @author 陈伟东
 * @Create In 2016年3月5日
 */
public class CreditorRights extends DataEntity<CreditorRights> {
	
	
	public static final String RIGHTS_TYPE_INPUT  = "1"; //债权录入过来的
	public static final String RIGHTS_TYPE_SYS = "2";//系统放款过来的
	
	public static final String CREDIT_TYPE_HASLOAD = "1";//己放款
	public static final String CREDIT_TYPE_HASCONFIRM = "2";//债权己确认
	public static final String CREDIT_TYPE_EARLYSETTLE = "3";//提前结清
	public static final String CREDIT_TYPE_HASSETTLE = "4";//结清己确认
	
	
	private static final long serialVersionUID = 7500120246309735352L;
	//主键
	private String id;
	//借款编码
	private String loanCode;
	//借款人姓名
	@ExcelField(title = "借款人", type = 0, align = 2, sort = 10, hasNull=1)
	private String loanCustomerName;
	//证件类型
	private String certType;
	// 证件号码
	@ExcelField(title = "借款人身份证号", type = 0, align = 2, sort = 10)
    private String customerCertNum;
    //债权来源
	@ExcelField(title = "借债权来源", type = 0, align = 2, sort = 10)
    private String creditorRigthSource;
    //车牌号码
	@ExcelField(title = "车牌号码", type = 0, align = 2, sort = 10)
    private String plateNumbers;
	// 合同编号
    @ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode; 
	// 产品类型
    @ExcelField(title = "借款产品", type = 0, align = 2, sort = 10)
    private String productType;
    //职业情况
    @ExcelField(title = "职业情况", type = 0, align = 2, sort = 10)
    private String occupationCase;
    //借款总额
    private Double loanAmount;
    //借款用途
    @ExcelField(title = "借款用途", type = 0, align = 2, sort = 10)
    private String usageOfLoan;
    //借款月利率
    @ExcelField(title = "月利率", type = 0, align = 2, sort = 10)
    private Double monthlyInterestRate;
    //合同签约日期
    @ExcelField(title = "合同签约日期", type = 0, align = 2, sort = 10)
    private Date contractDay; 
    //首期还款日
    @ExcelField(title = "首次还款日", type = 0, align = 2, sort = 10)
    private Date downPaymentDay;
    //合同截止期日
    @ExcelField(title = "借截止还款日期", type = 0, align = 2, sort = 10)
    private Date contractEndDay;
    //借款天数
    @ExcelField(title = "借款天数", type = 0, align = 2, sort = 10)
    private Integer loanDays;
    //债权人
    @ExcelField(title = "债权人", type = 0, align = 2, sort = 10)
    private String creditor;
    //借款状态
    private String loanStatus;
    //录入日期
    private Date entryDay;
    //起始录入日期
    private Date entryDayStart;
    //结束录入日期
    private Date entryDayEnd;
    //原始债权价值
    @ExcelField(title = "原始债权价值", type = 0, align = 2, sort = 10)
    private Double primevalWorth;
    private String issendWealth;
    
    // 渠道类型
    private String channelType;
    
    // 是否有共借人
    private String customerCobo;  
    //还款方式
    private String contractReplayWay;
    //还款金额
    private Double contractReplayAmount;
    //申请借款期限
    private int loanMonths; 
    
    private String rightsType;
    
    private String creditType;
    private Date creditConfirmDate;
    private Date settleConfirmDate;
    private Date settledDate;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public Integer getLoanDays() {
		return loanDays;
	}
	public void setLoanDays(Integer loanDays) {
		this.loanDays = loanDays;
	}
	public String getCreditorRigthSource() {
		return creditorRigthSource;
	}
	public void setCreditorRigthSource(String creditorRigthSource) {
		this.creditorRigthSource = creditorRigthSource;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getOccupationCase() {
		return occupationCase;
	}
	public void setOccupationCase(String occupationCase) {
		this.occupationCase = occupationCase;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getUsageOfLoan() {
		return usageOfLoan;
	}
	public void setUsageOfLoan(String usageOfLoan) {
		this.usageOfLoan = usageOfLoan;
	}
	public Double getMonthlyInterestRate() {
		return monthlyInterestRate;
	}
	public void setMonthlyInterestRate(Double monthlyInterestRate) {
		this.monthlyInterestRate = monthlyInterestRate;
	}
	public Date getContractDay() {
		return contractDay;
	}
	public void setContractDay(Date contractDay) {
		this.contractDay = contractDay;
	}
	public Date getDownPaymentDay() {
		return downPaymentDay;
	}
	public void setDownPaymentDay(Date downPaymentDay) {
		this.downPaymentDay = downPaymentDay;
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
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public Date getEntryDay() {
		return entryDay;
	}
	public void setEntryDay(Date entryDay) {
		this.entryDay = entryDay;
	}
	
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public Double getPrimevalWorth() {
		return primevalWorth;
	}
	public void setPrimevalWorth(Double primevalWorth) {
		this.primevalWorth = primevalWorth;
	}
	
	public String getIssendWealth() {
		return issendWealth;
	}
	public void setIssendWealth(String issendWealth) {
		this.issendWealth = issendWealth;
	}
	public Date getEntryDayStart() {
		return entryDayStart;
	}
	public void setEntryDayStart(Date entryDayStart) {
		this.entryDayStart = entryDayStart;
	}
	public Date getEntryDayEnd() {
		return entryDayEnd;
	}
	public void setEntryDayEnd(Date entryDayEnd) {
		this.entryDayEnd = entryDayEnd;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getCustomerCobo() {
		return customerCobo;
	}
	public void setCustomerCobo(String customerCobo) {
		this.customerCobo = customerCobo;
	}
	public String getContractReplayWay() {
		return contractReplayWay;
	}
	public void setContractReplayWay(String contractReplayWay) {
		this.contractReplayWay = contractReplayWay;
	}
	public Double getContractReplayAmount() {
		return contractReplayAmount;
	}
	public void setContractReplayAmount(Double contractReplayAmount) {
		this.contractReplayAmount = contractReplayAmount;
	}
	public int getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(int loanMonths) {
		this.loanMonths = loanMonths;
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
	public Date getCreditConfirmDate() {
		return creditConfirmDate;
	}
	public void setCreditConfirmDate(Date creditConfirmDate) {
		this.creditConfirmDate = creditConfirmDate;
	}
	public Date getSettleConfirmDate() {
		return settleConfirmDate;
	}
	public void setSettleConfirmDate(Date settleConfirmDate) {
		this.settleConfirmDate = settleConfirmDate;
	}
	public Date getSettledDate() {
		return settledDate;
	}
	public void setSettledDate(Date settledDate) {
		this.settledDate = settledDate;
	}
	
}
