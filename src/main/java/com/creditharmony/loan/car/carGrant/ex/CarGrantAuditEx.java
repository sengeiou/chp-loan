/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.workFlow.viewLoanFlowWorkItemView.java
 * @Create By 张灏
 * @Create In 2016年1月23日 下午4:52:05
 */
package com.creditharmony.loan.car.carGrant.ex;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * @Class Name CarGrantAuditEx
 * @Create In 2016年1月23日
 */
public class CarGrantAuditEx  extends BaseTaskItemView{
	
	// 客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;
	//门店名称
	private String storeName;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	//开户行
	private String cardBank;
	//是否电销
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 140)
	private String loanIsPhone;
	//借款产品
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 50)
	private String auditBorrowProductName;
	//合同金额
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 60)
	private double contractAmount;
	//实放金额
	@ExcelField(title = "放款失败金额", type = 0, align = 2, sort = 70)
	private double grantFailAmount;
	//批借期限(天)
	@ExcelField(title = "批借期限(天)", type = 0, align = 2, sort = 80)
	private int auditLoanMonths;
	//划扣金额
	private Double deductsAmount;
	//总费率
	private Double grossRate;
	//借款状态
	private String dictStatus;
    //标识
    @ExcelField(title = "标识", type = 0, align = 2, sort = 130)
    private String borrowTrusteeFlag;
    //共借人
    @ExcelField(title = "共借人", type = 0, align = 2, sort = 30)
    private  String coborrowerName;
    // 放款日期
    @ExcelField(title = "放款日期", type = 0, align = 2, sort = 90)
 	private Date lendingTime;
	// 中间人放款账号
    @ExcelField(title = "放款账号", type = 0, align = 2, sort = 100)
	private String midBankCardNo;
	// 放款开户行
    @ExcelField(title = "开户行", type = 0, align = 2, sort = 110)
	private String midBankName;
	// 中间人姓名
    @ExcelField(title = "账号姓名", type = 0, align = 2, sort = 120)
	private String midBankCardName;
	// 回执结果
    @ExcelField(title = "回盘结果", type = 0, align = 2, sort = 150)
	private String grantRecepicResult;
	// 中金放款失败，回盘原因
    @ExcelField(title = "回盘原因", type = 0, align = 2, sort = 160)
	private String grantFailResult;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}
	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public double getGrantFailAmount() {
		return grantFailAmount;
	}
	public void setGrantFailAmount(double grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}
	public int getAuditLoanMonths() {
		return auditLoanMonths;
	}
	public void setAuditLoanMonths(int auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}
	public Double getDeductsAmount() {
		return deductsAmount;
	}
	public void setDeductsAmount(Double deductsAmount) {
		this.deductsAmount = deductsAmount;
	}
	public Double getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(Double grossRate) {
		this.grossRate = grossRate;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	public String getBorrowTrusteeFlag() {
		return borrowTrusteeFlag;
	}
	public void setBorrowTrusteeFlag(String borrowTrusteeFlag) {
		this.borrowTrusteeFlag = borrowTrusteeFlag;
	}
	public String getCoborrowerName() {
		return coborrowerName;
	}
	public void setCoborrowerName(String coborrowerName) {
		this.coborrowerName = coborrowerName;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getMidBankCardNo() {
		return midBankCardNo;
	}
	public void setMidBankCardNo(String midBankCardNo) {
		this.midBankCardNo = midBankCardNo;
	}
	public String getMidBankName() {
		return midBankName;
	}
	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}
	public String getMidBankCardName() {
		return midBankCardName;
	}
	public void setMidBankCardName(String midBankCardName) {
		this.midBankCardName = midBankCardName;
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
    

	
	

    
}
