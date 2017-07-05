package com.creditharmony.loan.car.carExtend.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 * @Class Name CarExportCustomerDataExColumn
 * @author 何军
 * @Create In 2016年5月24日
 */
public class CarExportCustomerDataExColumn implements Serializable  {
	/**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	//序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 1)
	private Integer number;
	//客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 2)
	private String customerName;
	//共借人姓名
	@ExcelField(title = "共借人姓名", type = 0, align = 2, sort = 3)
	private String coborrowerName;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 4)
	private String contractCode;
	//门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 5)
	private String storeName;
	//主借人身份证号
	@ExcelField(title = "主借人身份证号", type = 0, align = 2, sort = 6)
	private String certNum;
	//产品类型
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 7)
	private String auditBorrowProductName;
	//开户行名称
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 8)
	private String cardBank;
	
	@ExcelField(title = "还款银行帐号", type = 0, align = 2, sort = 9)
	private String bankCardNo;
	//合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 10)
	private double contractAmount;
	//实放金额
	@ExcelField(title = "实放金额", type = 0, align = 2, sort = 11)
	private double grantAmount;
	
	@ExcelField(title = "月还款金额", type = 0, align = 2, sort = 12)
	private double contractMonthRepayAmount;
	
	@ExcelField(title = "借款期限", type = 0, align = 2, sort = 13)
	private int auditLoanMonths;
	
	@ExcelField(title = "综合费用", type = 0, align = 2, sort = 14)
	private double comprehensiveServiceFee;

	@ExcelField(title = "首期服务费", type = 0, align = 2, sort = 15)
	private double firstServiceTariffing;

	@ExcelField(title = "停车费", type = 0, align = 2, sort = 16)
	private double parkingFee;
	
	@ExcelField(title = "设备费", type = 0, align = 2, sort = 17)
	private double facilityCharge;
	
	@ExcelField(title = "平台流量费", type = 0, align = 2, sort = 18)
	private double flowFee;

	@ExcelField(title = "设备使用费", type = 0, align = 2, sort = 19)
	private double deviceUsedFee;

	@ExcelField(title = "外访费", type = 0, align = 2, sort = 20)
	private double outVisitFee;
	
	@ExcelField(title = "首期还款日", type = 0, align = 2, sort = 21)
	private Date contractReplayDay;
	
	@ExcelField(title = "合同到期日", type = 0, align = 2, sort = 22)
	private Date contractEndDay;
	
	@ExcelField(title = "合同签订日期", type = 0, align = 2, sort = 23)
	private Date contractFactDay;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCoborrowerName() {
		return coborrowerName;
	}

	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
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

	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}

	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public double getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}

	public void setContractMonthRepayAmount(double contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}

	public int getAuditLoanMonths() {
		return auditLoanMonths;
	}

	public void setAuditLoanMonths(int auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}

	public double getComprehensiveServiceFee() {
		return comprehensiveServiceFee;
	}

	public void setComprehensiveServiceFee(double comprehensiveServiceFee) {
		this.comprehensiveServiceFee = comprehensiveServiceFee;
	}

	public double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}

	public double getFacilityCharge() {
		return facilityCharge;
	}

	public void setFacilityCharge(double facilityCharge) {
		this.facilityCharge = facilityCharge;
	}

	public double getFlowFee() {
		return flowFee;
	}

	public void setFlowFee(double flowFee) {
		this.flowFee = flowFee;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}

	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}

	public Date getContractEndDay() {
		return contractEndDay;
	}

	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}

	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}

	public double getFirstServiceTariffing() {
		return firstServiceTariffing;
	}

	public void setFirstServiceTariffing(double firstServiceTariffing) {
		this.firstServiceTariffing = firstServiceTariffing;
	}

	public double getDeviceUsedFee() {
		return deviceUsedFee;
	}

	public void setDeviceUsedFee(double deviceUsedFee) {
		this.deviceUsedFee = deviceUsedFee;
	}

	public double getOutVisitFee() {
		return outVisitFee;
	}

	public void setOutVisitFee(double outVisitFee) {
		this.outVisitFee = outVisitFee;
	}
}
