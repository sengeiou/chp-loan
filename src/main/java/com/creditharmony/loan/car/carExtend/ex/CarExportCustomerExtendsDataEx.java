package com.creditharmony.loan.car.carExtend.ex;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 客户信息
 * 
 * @Class Name Consult
 * @author gaoyuan
 * @Create In 2016年3月14日
 */
public class CarExportCustomerExtendsDataEx implements Serializable {
	private static final long serialVersionUID = 1L;
	// 序号
	@ExcelField(title = "序号", type = 1, align = 2, sort = 10)
	private Integer number;
	// 门店名称
	@ExcelField(title = "门店名称", type = 1, align = 2, sort = 20)
	private String storeName;
	// 展期合同编号
	@ExcelField(title = "展期合同编号", type = 1, align = 2, sort = 30)
	private String contractCode;
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 1, align = 2, sort = 40)
	private String customerName;
	// 共借人姓名
	@ExcelField(title = "共借人", type = 1, align = 2, sort = 50)
	private String coborrowerName;
	// 产品类型
	@ExcelField(title = "借款类型", type = 1, align = 2, sort = 60)
	private String auditBorrowProductName;
	// 借款期限
	@ExcelField(title = "借款期限(天)", type = 1, align = 2, sort = 70)
	private int auditLoanMonths;
	// 总费率
	@ExcelField(title = "总费率(%)", type = 1, align = 2, sort = 80)
	private Double grossRate;
	// 月利率
	@ExcelField(title = "利息率(%)", type = 1, align = 2, sort = 90)
	private Double interestRate;
	// 合同金额
	@ExcelField(title = "合同金额(元)", type = 2, align = 2, sort = 100)
	private Double contractAmount;
	// 批结金额
	@ExcelField(title = "批借金额", type = 1, align = 2, sort = 110)
	private Double extendAmount;
	// 开户行
	@ExcelField(title = "划扣银行", type = 1, align = 2, sort = 120)
	private String cardBank;
	// 还款银行账号
	@ExcelField(title = "账号", type = 1, align = 2, sort = 130)
	private String bankCardNo;
	// 展期费用
	@ExcelField(title = "展期费用", type = 1, align = 2, sort = 140)
	private Double extensionFee;
	// 合同金额
	@ExcelField(title = "降额(元)", type = 1, align = 2, sort = 150)
	private Double derate;
	// 停车费
	@ExcelField(title = "停车费(元)", type = 1, align = 2, sort = 160)
	private Double parkFee;
	// 设备费
	@ExcelField(title = "设备费(元)", type = 1, align = 2, sort = 165)
	private Double facilityFee;
	// 设备使用费
	@ExcelField(title = "设备使用费(元)", type = 1, align = 2, sort = 170)
	private Double deviceUsedFee;
	// 平台流量费
	@ExcelField(title = "平台流量费(元)", type = 1, align = 2, sort = 180)
	private Double flowFee;
	
	// 身份证号码
	@ExcelField(title = "身份证号码", type = 2, align = 2, sort = 190)
	private String certNum;
	// 是否展期
	@ExcelField(title = "是否展期", type = 2, align = 2, sort = 200)
	private String isextension;
	// 已展期次数
	@ExcelField(title = "已展期次数", type = 2, align = 2, sort = 210)
	private String extendNumber;
	// 车牌号码
	@ExcelField(title = "车牌号码", type = 2, align = 2, sort = 152)
	private String plateNumbers;
	// 申请状态
	@ExcelField(title = "申请状态", type = 2, align = 2, sort = 154)
	private String applyStatusCode;
	// 合同版本号
	@ExcelField(title = "合同版本号", type = 2, align = 2, sort = 156)
	private String contractVersion;
	// 实放金额
	@ExcelField(title = "实放金额(元)", type = 2, align = 2, sort = 220)
	private Double feePaymentAmount;
	// 月还款金额
	@ExcelField(title = "月还款金额(元)", type = 2, align = 2, sort = 230)
	private Double monthRepayAmount;
	// 综合费用
	@ExcelField(title = "综合费用(元)", type = 2, align = 2, sort = 240)
	private Double serviceFee;
	
	// 合同签订日期
	@ExcelField(title = "展期起始日期", type = 1, align = 2, sort = 260)
	private Date contractFactDay;
	// 首期还款日
	@ExcelField(title = "首期还款日", type = 2, align = 2, sort = 250)
	private Date contractReplayDay;
	// 合同到期日
	@ExcelField(title = "展期截止期日", type = 1, align = 2, sort = 280)
	private Date contractEndDay;
	// 账单日
	@ExcelField(title = "账单日", type = 2, align = 2, sort = 270)
	private Date replayDay;
	// 渠道
	@ExcelField(title = "渠道", type = 1, align = 2, sort = 290)
	private String loanFlag;
	
	

	public Double getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(Double extendAmount) {
		this.extendAmount = extendAmount;
	}

	public Double getExtensionFee() {
		return extensionFee;
	}

	public void setExtensionFee(Double extensionFee) {
		this.extensionFee = extensionFee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public String getCoborrowerName() {
		return coborrowerName;
	}

	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Double getDerate() {
		return derate;
	}

	public void setDerate(Double derate) {
		this.derate = derate;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
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

	public int getAuditLoanMonths() {
		return auditLoanMonths;
	}

	public void setAuditLoanMonths(int auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}

	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}

	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}

	public String getIsextension() {
		return isextension;
	}

	public void setIsextension(String isextension) {
		this.isextension = isextension;
	}

	public String getExtendNumber() {
		return extendNumber;
	}

	public void setExtendNumber(String extendNumber) {
		this.extendNumber = extendNumber;
	}

	public Double getFeePaymentAmount() {
		return feePaymentAmount;
	}

	public void setFeePaymentAmount(Double feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}

	public Double getMonthRepayAmount() {
		return monthRepayAmount;
	}

	public void setMonthRepayAmount(Double monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getParkFee() {
		return parkFee;
	}

	public void setParkFee(Double parkFee) {
		this.parkFee = parkFee;
	}

	public Double getFacilityFee() {
		return facilityFee;
	}

	public void setFacilityFee(Double facilityFee) {
		this.facilityFee = facilityFee;
	}

	public Double getDeviceUsedFee() {
		return deviceUsedFee;
	}

	public void setDeviceUsedFee(Double deviceUsedFee) {
		this.deviceUsedFee = deviceUsedFee;
	}
	
	public Double getFlowFee() {
		return flowFee;
	}

	public void setFlowFee(Double flowFee) {
		this.flowFee = flowFee;
	}
	

	public Double getGrossRate() {
		return grossRate;
	}

	public void setGrossRate(Double grossRate) {
		this.grossRate = grossRate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
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

	public Date getReplayDay() {
		return replayDay;
	}

	public void setReplayDay(Date replayDay) {
		this.replayDay = replayDay;
	}

	public String getPlateNumbers() {
		return plateNumbers;
	}

	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}

	public String getApplyStatusCode() {
		return applyStatusCode;
	}

	public void setApplyStatusCode(String applyStatusCode) {
		this.applyStatusCode = applyStatusCode;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

}
