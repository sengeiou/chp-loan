package com.creditharmony.loan.car.carExtend.view;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;

/**
 * 车借展期待办列表view
 * 
 * @Class Name CarExtendFlowWorkItemView
 * @author 陈伟东
 * @Create In 2016年3月1日
 */
public class CarExtendFlowWorkItemView  extends BaseTaskItemView{
	//原合同编号
	private String originalContractCode;
	//展期合同编号
	private String contractCode;
	//客户姓名
	private String customerName;
	//客户编码
	private String customerCode;
	//借款期限（申请）
	private Integer loanMonths;
	//产品类型名称（申请）
	private String borrowProductName;
	//产品类型编码（申请）
	private String borrowProductCode;
	//门店名称
	private String storeName;
	//门店id
	private String storeId;
	//共借人姓名（多个共借人时使用，隔开）
	private String coborrowerName;
	//管辖省份
	private String addrProvince;
	//申请金额
	private double loanApplyAmount;
	//合同金额
	private double contractAmount;
	//签约日期
	private Date contractFactDay;
	//车牌号码
	private String plateNumbers;
	//合同版本号
	private String contractVersion;
	//申请id
	private String applyId;
	//申请日期
	private Date loanApplyTime;
	//产品类型编码（批复）
	private String auditBorrowProductCode;
	//产品类型名称（批复）
	private String auditBorrowProductName;
	//批借金额(审批金额）
	private double auditAmount;
	//终审日期
	private Date finalCheckTime;
	//借款期限（批复）、批借期限
	private Integer auditLoanMonths;
	//是否电销（code）
	private String loanIsPhone;
	//是否展期
	private String extensionFlag;
	//申请状态
	private String applyStatusCode;
	//已展期次数
	private int extendNumber;
	//展期费用
	private double extensionFee;
	//总费用
	private double totalCost;
	//降额
	private double derate;
	//原批借金额
	private double originalAuditAmount;
	//合同到期日期
	private Date contractEndDay;
	//合同到期提醒日期
	private Date contractExpirationDate;
	//退回数据置顶标识
	private String backTop;
	//综合服务费
	private Double comprehensiveServiceFee;
	//排序字段
	private String orderField;
	//渠道
	private String loanFlag;
	
	public Double getComprehensiveServiceFee() {
		return comprehensiveServiceFee;
	}
	public void setComprehensiveServiceFee(Double comprehensiveServiceFee) {
		this.comprehensiveServiceFee = comprehensiveServiceFee;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public Date getContractExpirationDate() {
		return contractExpirationDate;
	}
	public void setContractExpirationDate(Date contractExpirationDate) {
		this.contractExpirationDate = contractExpirationDate;
	}
	public String getOriginalContractCode() {
		return originalContractCode;
	}
	public void setOriginalContractCode(String originalContractCode) {
		this.originalContractCode = originalContractCode;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
	public String getBorrowProductCode() {
		return borrowProductCode;
	}
	public void setBorrowProductCode(String borrowProductCode) {
		this.borrowProductCode = borrowProductCode;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
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
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
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
	public Date getFinalCheckTime() {
		return finalCheckTime;
	}
	public void setFinalCheckTime(Date finalCheckTime) {
		this.finalCheckTime = finalCheckTime;
	}
	public Integer getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(Integer auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public String getExtensionFlag() {
		return extensionFlag;
	}
	public void setExtensionFlag(String extensionFlag) {
		this.extensionFlag = extensionFlag;
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
	public double getExtensionFee() {
		return extensionFee;
	}
	public void setExtensionFee(double extensionFee) {
		this.extensionFee = extensionFee;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public double getDerate() {
		return derate;
	}
	public void setDerate(double derate) {
		this.derate = derate;
	}
	public double getOriginalAuditAmount() {
		return originalAuditAmount;
	}
	public void setOriginalAuditAmount(double originalAuditAmount) {
		this.originalAuditAmount = originalAuditAmount;
	}
	public String getBackTop() {
		return backTop;
	}
	public void setBackTop(String backTop) {
		this.backTop = backTop;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	
}
