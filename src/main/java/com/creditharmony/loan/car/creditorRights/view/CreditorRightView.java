package com.creditharmony.loan.car.creditorRights.view;

import java.util.Date;

public class CreditorRightView {
	
	//主键
	private String id;
	//借款人姓名
	private String loanCustomerName;
	//证件类型
	private String certType;
	// 证件号码
    private String customerCertNum;
    //职权来源
    private String creditorRigthSource;
    //车牌号码
    private String plateNumbers;
	// 合同编号
	private String contractCode; 
	// 产品类型
    private String productType;
	// 渠道类型
    private String channelType;    
    //职业情况
    private String job;
    //借款总额
    private double loanAmount;
    //借款编码
    private String loanCode;
    //借款用途
    private String usageOfLoan;
    //借款月利率
    private double monthlyInterestRate;
    //合同签约日期
    private Date contractDay; 
    //首期还款日
    private Date downPaymentDay;
    //合同截止期日
    private Date contractEndDay;
    //借款天数
    private int borrowingDays;
    //债权人
    private String creditor;
    //中间人 
    private String middleName;
    //借款状态
    private String loanStatus;
    //债权状态
    private String creditType;
    //批借期限
    private int contractMonths;
    public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	//录入日期
    private Date entryDay;
    //起始录入日期
    private Date entryDayStart;
    //结束录入日期
    private Date entryDayEnd;
    //原始债权价值
    private double primevalWorth;
    
    // 是否有共借人
    private String customerCobo;  
    //还款方式
    private String contractReplayWay;
    //还款金额
    private double contractReplayAmount;
    
    //P2P标识
    private String loanFlag;
    
    private String rightsType;
    
    /**
     * 处理状态 
     * 0: 未发送
     * 1：发送中、已发送、处理中
     * 2：成功发送
     * 3：发送失败
     * 4: 结清
     * 5：提前结清
     */
    private String status;
    
	protected String createBy;	// 创建者
	protected Date createTime;	// 创建时间
	protected String modifyBy;	// 更新者
	protected Date modifyTime;	// 更新时间
    
	
	
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getUsageOfLoan() {
		return usageOfLoan;
	}
	public void setUsageOfLoan(String usageOfLoan) {
		this.usageOfLoan = usageOfLoan;
	}
	public double getMonthlyInterestRate() {
		return monthlyInterestRate;
	}
	public void setMonthlyInterestRate(double monthlyInterestRate) {
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
	public int getBorrowingDays() {
		return borrowingDays;
	}
	public void setBorrowingDays(int borrowingDays) {
		this.borrowingDays = borrowingDays;
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
	public double getPrimevalWorth() {
		return primevalWorth;
	}
	public void setPrimevalWorth(double primevalWorth) {
		this.primevalWorth = primevalWorth;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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
	public double getContractReplayAmount() {
		return contractReplayAmount;
	}
	public void setContractReplayAmount(double contractReplayAmount) {
		this.contractReplayAmount = contractReplayAmount;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
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
	
}
