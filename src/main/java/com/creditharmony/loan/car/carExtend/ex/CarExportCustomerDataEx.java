package com.creditharmony.loan.car.carExtend.ex;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 客户信息
 * @Class Name CarExportCustomerDataEx
 * @author 申诗阔
 * @Create In 2016年4月1日
 */
public class CarExportCustomerDataEx implements Serializable  {
	private static final long serialVersionUID = 8451042915229973669L;
	//序号
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	private Integer number;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 20)
	private String contractCode;
	//客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 30)
	private String customerName;
	//共借人姓名
	@ExcelField(title = "共借人姓名", type = 0, align = 2, sort = 40)
	private String coborrowerName;
	//门店名称
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 50)
	private String storeName;
	//门店名称
	@ExcelField(title = "管辖省份", type = 0, align = 2, sort = 60)
	private String addrProvince;
	//合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 70)
	private Double contractAmount;
	//实放金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 80)
	private Double grantAmount;
	//借款期限
	@ExcelField(title = "借款期限", type = 0, align = 2, sort = 90)
	private int auditLoanMonths;
	//产品类型
	@ExcelField(title = "产品类型", type = 0, align = 2, sort = 100)
	private String auditBorrowProductName;
	//开户行
	@ExcelField(title = "车牌号码", type = 0, align = 2, sort = 110)
	private String plateNumbers;
	//签约日期
	@ExcelField(title = "签约日期", type = 0, align = 2, sort = 120)
	private Date contractFactDay;
	//停车费
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 130)
	private String contractVersion;
	//设备费
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 140)
	private String dictStatus;
	//平台流量费
	@ExcelField(title = "退回原因", type = 0, align = 2, sort = 150)
	private String contractBackResultCode;
	//首期还款日
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 160)
	private String loanIsPhone;
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
	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}
	public Double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Double getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(Double grantAmount) {
		this.grantAmount = grantAmount;
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
	public String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	public String getContractBackResultCode() {
		return contractBackResultCode;
	}
	public void setContractBackResultCode(String contractBackResultCode) {
		this.contractBackResultCode = contractBackResultCode;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	
}
