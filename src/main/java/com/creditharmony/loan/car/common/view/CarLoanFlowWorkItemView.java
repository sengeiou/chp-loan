package com.creditharmony.loan.car.common.view;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;

/**
 * 车借待办列表view
 * 
 * @Class Name CarLoanFlowWorkItemView
 * @author 陈伟东
 * @Create In 2016年2月2日
 */
public class CarLoanFlowWorkItemView  extends BaseTaskItemView{
	//合同编号
	private String contractCode;
	//客户姓名
	private String customerName;
	//客户编码
	private String customerCode;
	//借款期限（申请）
	private int loanMonths;
	//产品类型名称（申请）
	private String borrowProductName;
	//身份证号
	private String certNum;
	//门店评估金额
	private double storeAssessAmount;
	//是否电销（code）
	private String loanIsPhone;
	//门店名称
	private String storeName;
	//门店id
	private String storeId;
	//共借人姓名（多个共借人时使用，隔开）
	private String coborrowerName;
	//管辖省份
	private String addrProvince;
	//所属城市
	private String addrCity;
	//申请金额
	private double loanApplyAmount;
	//门店code
	private String storeCode;
	//产品类型编码（申请）
	private String borrowProductCode;
	//合同金额
	private double contractAmount;
	//放款金额
	private double grantAmount;
	//放款失败金额
	private double grantFailAmount;
	//签约日期
	private Date contractFactDay;
	//车牌号码
	private String plateNumbers;
	//合同版本号
	private String contractVersion;
	//回退原因（合同）
	private String contractBackResult;
	//申请id
	private String applyId;
	//借款状态
	private String dictStatus;
	//银行信息（客户银行卡或存折户名）
	private String bankAccountName;
	//银行账号（客户）
	private String bankCardNo;
	//中间人账号（放款账号）
	private String midBankCardNo;
	//开户行(客户)
	private String cardBank;
	//放款银行、中间银行（银行信息）
	private String midBankName;
	//应划扣金额
	private double deductsAmount;
	//总费率
	private double grossRate;
	//放款途径
	private String dictLoanWay;
	//回盘结果
	private String grantRecepicResult;
	//回盘原因
	private String grantFailResult;
	//账号姓名（客户）
	private String bankCardName;
	//放款日期
	private Date lendingTime;
	//申请日期
	private Date loanApplyTime;
	//预计到店时间
	private Date planArrivalTime;
	//车辆品牌型号
	private String vehicleBrandModel;
	//客户经理姓名
	private String offendSalesName;
	//客户经理编码
	private String offendSalesCode;
	//团队经理(name)
	private String loanTeamEmpName;
	//团队经理（code）
	private String loanTeamEmpCode;
	//咨询状态（code)
	private String dictOperStatus;
	//借款编码
	private String loanCode;
	
	
	//产品类型编码（批复）
	private String auditBorrowProductCode;
	//产品类型名称（批复）
	private String auditBorrowProductName;
	//批借金额(审批金额）
	private double auditAmount;
	//批借时间、终审日期
	private Date auditTime;
	//借款期限（批复）、批借期限
	private int auditLoanMonths;
	//标识
	private String borrowTursteeFlag;
	//附条件标识
	private String conditionalThroughFlag;
	//进件时间
	private Date customerIntoTime;
	//初审姓名（面审人员）
	private String firstCheckName;
	//复审姓名
	private String recheckName;
	//是否循环借
	private String cycleBorrowingFlag;
	//是否展期
	private String extensionFlag;
	//合同到期提醒日期（根据利率审批时间计算而来）
	private Date contractExpirationDate;
	//放款账号姓名（账号姓名）
	private String midBankCardName;
	//退回原因（放款）
	private String grantBackResultCode;
	//退回数据置顶标识
	private String backTop;
	// 放款人员编号
	private String grantPersons;
	//综合服务费
	private Double comprehensiveServiceFee;
	//退回原因（放款确认）
	private String contractBackResultCode;
	//展期次数
	private int extendNumber;
	//展期借款状态
	private String applyStatusCode;
	//展期批借金额
	private Double originalAuditAmount;
	//排序字段
	private String orderField;
	//是否可办理，0：可办理，1：不可办理
	private Integer canHandle;
	//p2p标识
	private String loanFlag;
	
	private String backReason;
	
	public Double getOriginalAuditAmount() {
		return originalAuditAmount;
	}
	public void setOriginalAuditAmount(Double originalAuditAmount) {
		this.originalAuditAmount = originalAuditAmount;
	}
	public String getApplyStatusCode() {
		return applyStatusCode;
	}
	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}
	public int getExtendNumber() {
		return extendNumber;
	}
	public void setExtendNumber(int extendNumber) {
		this.extendNumber = extendNumber;
	}
	public String getContractBackResultCode() {
		return contractBackResultCode;
	}
	public void setContractBackResultCode(String contractBackResultCode) {
		this.contractBackResultCode = contractBackResultCode;
	}
	public Double getComprehensiveServiceFee() {
		return comprehensiveServiceFee;
	}
	public void setComprehensiveServiceFee(Double comprehensiveServiceFee) {
		this.comprehensiveServiceFee = comprehensiveServiceFee;
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
	
	public int getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(int loanMonths) {
		this.loanMonths = loanMonths;
	}
	public String getBorrowProductName() {
		return borrowProductName;
	}
	public void setBorrowProductName(String borrowProductName) {
		this.borrowProductName = borrowProductName;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public double getStoreAssessAmount() {
		return storeAssessAmount;
	}
	public void setStoreAssessAmount(double storeAssessAmount) {
		this.storeAssessAmount = storeAssessAmount;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
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
	public String getCoborrowerName() {
		return coborrowerName;
	}
	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}
	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public double getLoanApplyAmount() {
		return loanApplyAmount;
	}
	public void setLoanApplyAmount(double loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public double getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(double grantAmount) {
		this.grantAmount = grantAmount;
	}
	
	public double getGrantFailAmount() {
		return grantFailAmount;
	}
	public void setGrantFailAmount(double grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getContractBackResult() {
		return contractBackResult;
	}
	public void setContractBackResult(String contractBackResult) {
		this.contractBackResult = contractBackResult;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getMidBankCardNo() {
		return midBankCardNo;
	}
	public void setMidBankCardNo(String midBankCardNo) {
		this.midBankCardNo = midBankCardNo;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getMidBankName() {
		return midBankName;
	}
	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}
	public double getDeductsAmount() {
		return deductsAmount;
	}
	public void setDeductsAmount(double deductsAmount) {
		this.deductsAmount = deductsAmount;
	}
	public double getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(double grossRate) {
		this.grossRate = grossRate;
	}
	public String getDictLoanWay() {
		return dictLoanWay;
	}
	public void setDictLoanWay(String dictLoanWay) {
		this.dictLoanWay = dictLoanWay;
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
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public Date getPlanArrivalTime() {
		return planArrivalTime;
	}
	public void setPlanArrivalTime(Date planArrivalTime) {
		this.planArrivalTime = planArrivalTime;
	}
	public String getVehicleBrandModel() {
		return vehicleBrandModel;
	}
	public void setVehicleBrandModel(String vehicleBrandModel) {
		this.vehicleBrandModel = vehicleBrandModel;
	}
	public String getOffendSalesName() {
		return offendSalesName;
	}
	public void setOffendSalesName(String offendSalesName) {
		this.offendSalesName = offendSalesName;
	}
	public String getLoanTeamEmpName() {
		return loanTeamEmpName;
	}
	public void setLoanTeamEmpName(String loanTeamEmpName) {
		this.loanTeamEmpName = loanTeamEmpName;
	}
	public String getDictOperStatus() {
		return dictOperStatus;
	}
	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getOffendSalesCode() {
		return offendSalesCode;
	}
	public void setOffendSalesCode(String offendSalesCode) {
		this.offendSalesCode = offendSalesCode;
	}
	public String getLoanTeamEmpCode() {
		return loanTeamEmpCode;
	}
	public void setLoanTeamEmpCode(String loanTeamEmpCode) {
		this.loanTeamEmpCode = loanTeamEmpCode;
	}
	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}
	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	public double getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(double auditAmount) {
		this.auditAmount = auditAmount;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public int getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(int auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public String getBorrowTursteeFlag() {
		return borrowTursteeFlag;
	}
	public void setBorrowTursteeFlag(String borrowTursteeFlag) {
		this.borrowTursteeFlag = borrowTursteeFlag;
	}
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getFirstCheckName() {
		return firstCheckName;
	}
	public void setFirstCheckName(String firstCheckName) {
		this.firstCheckName = firstCheckName;
	}
	public String getRecheckName() {
		return recheckName;
	}
	public void setRecheckName(String recheckName) {
		this.recheckName = recheckName;
	}
	public String getCycleBorrowingFlag() {
		return cycleBorrowingFlag;
	}
	public void setCycleBorrowingFlag(String cycleBorrowingFlag) {
		this.cycleBorrowingFlag = cycleBorrowingFlag;
	}
	public String getExtensionFlag() {
		return extensionFlag;
	}
	public void setExtensionFlag(String extensionFlag) {
		this.extensionFlag = extensionFlag;
	}
	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}
	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}
	public String getMidBankCardName() {
		return midBankCardName;
	}
	public void setMidBankCardName(String midBankCardName) {
		this.midBankCardName = midBankCardName;
	}
	public String getGrantBackResultCode() {
		return grantBackResultCode;
	}
	public void setGrantBackResultCode(String grantBackResultCode) {
		this.grantBackResultCode = grantBackResultCode;
	}
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}
	public String getBackTop() {
		return backTop;
	}
	public void setBackTop(String backTop) {
		this.backTop = backTop;
	}
	public String getGrantPersons() {
		return grantPersons;
	}
	public void setGrantPersons(String grantPersons) {
		this.grantPersons = grantPersons;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public Integer getCanHandle() {
		return canHandle;
	}
	public void setCanHandle(Integer canHandle) {
		this.canHandle = canHandle;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	
}
