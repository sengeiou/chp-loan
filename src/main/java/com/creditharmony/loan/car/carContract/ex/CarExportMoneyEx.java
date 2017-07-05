package com.creditharmony.loan.car.carContract.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年2月23日
 */
public class CarExportMoneyEx implements Serializable  {
	private static final long serialVersionUID = 1L;
	//序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	private Integer number;
	//门店
	@ExcelField(title = "门店", type = 0, align = 2, sort = 20)
	private String storeName;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 30)
	private String contractCode;
	//合同日期
	@ExcelField(title = "合同日期", type = 0, align = 2, sort = 40)
	private Date contractFactDay;
	//姓名
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 50)
	private String customerName;
	//身份证号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 60)
	private String certNum;
	//开卡（省/市）
	@ExcelField(title = "开卡（省/市）", type = 0, align = 2, sort = 70)
	private String bankProvinceCity;
	//开卡行
	@ExcelField(title = "开卡行", type = 0, align = 2, sort = 75)
	private String cardBank;
	//银行账号
	@ExcelField(title = "银行账号", type = 0, align = 2, sort = 80)
	private String bankCardNo;
	//借款总额
	@ExcelField(title = "借款总额", type = 0, align = 2, sort = 90)
	private Double borrowAmount;
	//本金（合同金额）
	@ExcelField(title = "本金（合同金额）", type = 0, align = 2, sort = 100)
	private Double contractAmount;

	//首期服务费
	@ExcelField(title = "首期服务费", type = 0, align = 2, sort = 110)
	private Double firstServiceTariffing;

	//外访费
	@ExcelField(title = "外访费", type = 0, align = 2, sort = 120)
	private Double outVisitFee;

	//实放金额
	@ExcelField(title = "实放金额", type = 0, align = 2, sort = 130)
	private Double  feePaymentAmount;
	//借款期限
	@ExcelField(title = "借款期限", type = 0, align = 2, sort = 140)
	private Integer loanMonths;
	//借款类型
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 150)
	private String borrowProductName;
	//总费率
	@ExcelField(title = "总费率", type = 0, align = 2, sort = 160)
	private String grossRate;
	//利息率
	@ExcelField(title = "利息率", type = 0, align = 2, sort = 170)
	private String interestRate;
	//车牌号
	@ExcelField(title = "车牌号", type = 0, align = 2, sort = 180)
	private String plateNumbers;
	//渠道
	@ExcelField(title = "渠道", type = 0, align = 2, sort = 190)
	private String loanFlag;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getBankProvinceCity() {
		return bankProvinceCity;
	}
	public void setBankProvinceCity(String bankProvinceCity) {
		this.bankProvinceCity = bankProvinceCity;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public Double getBorrowAmount() {
		return borrowAmount;
	}
	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	public Double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Double getFeePaymentAmount() {
		return feePaymentAmount;
	}
	public void setFeePaymentAmount(Double feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}
	public String getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(String grossRate) {
		this.grossRate = grossRate;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public Double getFirstServiceTariffing() {
		return firstServiceTariffing;
	}
	public void setFirstServiceTariffing(Double firstServiceTariffing) {
		this.firstServiceTariffing = firstServiceTariffing;
	}
	public Double getOutVisitFee() {
		return outVisitFee;
	}
	public void setOutVisitFee(Double outVisitFee) {
		this.outVisitFee = outVisitFee;
	}

}
