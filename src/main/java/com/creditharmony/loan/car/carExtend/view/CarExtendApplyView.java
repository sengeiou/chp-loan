package com.creditharmony.loan.car.carExtend.view;


import java.util.Date;
/**
 * 车借展期发起
 * @Class Name Consult
 * @author 安子帅
 * @Create In 2016年3月10日
 */
public class CarExtendApplyView extends CarExtendBaseBusinessView {
	
	private String applyId;					//流程id
	private String originalContractCode; 	//原合同编号
	private String customerName;			//用户姓名
	private String customerCode;			//用户编码
	private String storeName;				//门店名称
	private String storeId;					//门店Code
	private double loanApplyAmount;  		//申请金额
	private Integer loanMonths;				//借款期限
	private String borrowProductName;		//产品类型
	private String borrowProductCode;		//产品类型编码
	private Date loanApplyTime;			//申请日期
	private String applyStatusCode;			//申请状态编码
	private String extensionFlag;			//是否展期
	private String cycleBorrowingFlag;			//是否循环借
	private int extendNumber;			//已展期次数
    private String coborrowerName;     //共借人姓名
    private String addrProvince;        //管辖省份
    private double originalAuditAmount;  //批借金额
    private String loanIsPhone;			// 是否电销
    private String contractCode;        //展期合同编号
    private String plateNumbers;        //车牌号码
    private Date customerIntoTime;		//  进件时间
    private String addrCity; //所属城市
    private String offendSalesName; // 客户经历
    private String managerCode; // 
    
    private Date contractExpirationDate;       //合同到期提醒
    private String loanFlag;       //渠道
    
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
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getCycleBorrowingFlag() {
		return cycleBorrowingFlag;
	}
	public void setCycleBorrowingFlag(String cycleBorrowingFlag) {
		this.cycleBorrowingFlag = cycleBorrowingFlag;
	}
	public double getOriginalAuditAmount() {
		return originalAuditAmount;
	}
	public void setOriginalAuditAmount(double originalAuditAmount) {
		this.originalAuditAmount = originalAuditAmount;
	}
	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}
	public String getCoborrowerName() {
		return coborrowerName;
	}
	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getOriginalContractCode() {
		return originalContractCode;
	}
	public void setOriginalContractCode(String originalContractCode) {
		this.originalContractCode = originalContractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public double getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(double loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}
	public Integer getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}
	public String getBorrowProductName() {
		return borrowProductName;
	}
	public void setBorrowProductName(String borrowProductName) {
		this.borrowProductName = borrowProductName;
	}
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public String getExtensionFlag() {
		return extensionFlag;
	}
	public void setExtensionFlag(String extensionFlag) {
		this.extensionFlag = extensionFlag;
	}
	
	public void setExtendNumber(int extendNumber) {
		this.extendNumber = extendNumber;
	}
	public int getExtendNumber() {
		return extendNumber;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}
	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getOffendSalesName() {
		return offendSalesName;
	}
	public void setOffendSalesName(String offendSalesName) {
		this.offendSalesName = offendSalesName;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	
}

