
package com.creditharmony.loan.car.carGrant.ex;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 待放款列表中的待放款列表导出，关于用户,type=2，仅导入
 * @Class Name CarGrantEx
 * @Create In 2016年2月5日
 */
public class CarGrantEx extends DataEntity<CarGrantEx> {
	private static final long serialVersionUID = 7783081129793432292L;
	// 放款id,
	private String id;
	// 款项匹配时间
	@ExcelField(title = "款项匹配时间", type = 0, align = 2, sort = 10)
	private Date lendingTime;
	// 序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 20)
	private String index;
	// 合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 30)
	private String contractCode; 
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 30)
	private String loanCustomerName; 
	// 用户卡号2
	@ExcelField(title = "收款账户 ", type = 0, align = 2, sort = 40)
	private String bankCardNo;
	// 客户姓名3
	@ExcelField(title = "收款户名", type = 0, align = 2, sort = 50)
	private String  bankAccountName;
	//转账金额
	@ExcelField(title = "转账金额", type = 0, align = 2, sort = 60)
	private String grantAmount;
	// 备注列
	@ExcelField(title = "门店备注", type = 0, align = 2, sort = 70)
	private String storeName;
	// 开户行
	@ExcelField(title = "收款银行", type = 0, align = 2, sort = 80 ,dictType="jk_open_bank")
	private String  cardBank;
	// 具体支行
	@ExcelField(title = "收款银行支行", type = 0, align = 2, sort = 90)
	private String  applyBankName;
	// 收款直省
	@ExcelField(title = "收款省", type = 0, align = 2, sort = 100)
	private String  bankProvince;
	// 收款市
	@ExcelField(title = "收款市", type = 0, align = 2, sort = 110)
	private String  bankCity;	
	// 批借期限(天)
	@ExcelField(title = "批借期限(天)", type = 0, align = 2, sort = 120)
	private Integer  contractMonths;
	// 实际到手金额
	@ExcelField(title = "实际到手金额", type = 0, align = 2, sort = 130)
	private String contractAmount;
	// 放款金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 140)
	private String loanGrantAmount;
	// 车辆品牌型号
	@ExcelField(title = "车辆品牌型号", type = 0, align = 2, sort = 150)
	private String vehicleBrandModel;
	// 车牌号
	@ExcelField(title = "车牌号", type = 0, align = 2, sort = 150)
	private String plateNumbers;
	// 产品类型
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 150)
	private String auditBorrowProductName;
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	//流程id
	private String applyId;
	// 催收服务费
	private BigDecimal feeUrgedService;

	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getLoanGrantAmount() {
		return loanGrantAmount;
	}
	public void setLoanGrantAmount(String loanGrantAmount) {
		this.loanGrantAmount = loanGrantAmount;
	}
	public String getVehicleBrandModel() {
		return vehicleBrandModel;
	}
	public void setVehicleBrandModel(String vehicleBrandModel) {
		this.vehicleBrandModel = vehicleBrandModel;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
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

		
	public Integer getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(Integer contractMonths) {
		this.contractMonths = contractMonths;
	}
	
	public String getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}
	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

}