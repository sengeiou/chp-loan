package com.creditharmony.loan.car.carGrant.ex;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 还款匹配列表
 * @Class Name Consult
 * @author ChenWeili
 * @Create In 2016年5月13日
 */
public class CheckDoneEx implements Serializable {
	//流程id
	private String applyId;
	//划扣日期开始
	private Date urgeDecuteDateStart;
	//划扣日期结束
	private Date urgeDecuteDateEnd;
	//合同编号
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 10)
	private String contractCode;
	//客户姓名
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String loanCustomerName;
	//开户行
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String storeName;
	//银行卡号
	@ExcelField(title = "存入账户", type = 0, align = 2, sort = 40)
	private String creditMiDBankName;
	//借款产品
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 50)
	private String finalProductType;
	//批借期限
	@ExcelField(title = "批借期限", type = 0, align = 2, sort = 50)
	private String contractMonths;
	//借款状态
	@ExcelField(title = "借款状态", type = 0, align = 2, sort = 60)
	private String  loanStatusCode;
	//合同金额
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 70)
	private Double  finalAuditAmount;
	//放款金额
	/*@ExcelField(title = "放款金额", type = 0, align = 2, sort = 80)
	private String finalAuditAmount;*/
	//划扣费用
	@ExcelField(title = "划扣费用", type = 0, align = 2, sort = 90)
	private Double  urgeMoeny;
	//未划金额
	@ExcelField(title = "未划金额", type = 0, align = 2, sort = 100)
	private Double  unUrgeDecuteMoeny;
	//申请查账金额
	@ExcelField(title = "申请查账金额", type = 0, align = 2, sort = 100)
	private Double  urgeDecuteMoeny;
	//实还金额
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 100)
	private Double  reallyAmount;
	//查账日期
	@ExcelField(title = "查账日期", type = 0, align = 2, sort = 100)
	private Date applyPayDay;
	//回盘结果
	/*@ExcelField(title = "回盘结果", type = 0, align = 2, sort = 100)
	private String dictDealType4;*/
	//标识
	@ExcelField(title = "标识", type = 0, align = 2, sort = 110)
	private String conditionalThroughFlag;
	//退款标识
	/*@ExcelField(title = "退款标识", type = 0, align = 2, sort = 110)
	private String conditionalThroughFlag1;*/
	
	
	public String getApplyId() {
		return applyId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getCreditMiDBankName() {
		return creditMiDBankName;
	}
	public void setCreditMiDBankName(String creditMiDBankName) {
		this.creditMiDBankName = creditMiDBankName;
	}
	public String getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(String contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getLoanStatusCode() {
		return loanStatusCode;
	}
	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}
	public Double getFinalAuditAmount() {
		return finalAuditAmount;
	}
	public void setFinalAuditAmount(Double finalAuditAmount) {
		this.finalAuditAmount = finalAuditAmount;
	}
	public Double getUnUrgeDecuteMoeny() {
		return unUrgeDecuteMoeny;
	}
	public void setUnUrgeDecuteMoeny(Double unUrgeDecuteMoeny) {
		this.unUrgeDecuteMoeny = unUrgeDecuteMoeny;
	}
	public Double getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}
	public void setUrgeDecuteMoeny(Double urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}
	public Double getReallyAmount() {
		return reallyAmount;
	}
	public void setReallyAmount(Double reallyAmount) {
		this.reallyAmount = reallyAmount;
	}
	public Date getApplyPayDay() {
		return applyPayDay;
	}
	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public Date getUrgeDecuteDateStart() {
		return urgeDecuteDateStart;
	}
	public void setUrgeDecuteDateStart(Date urgeDecuteDateStart) {
		this.urgeDecuteDateStart = urgeDecuteDateStart;
	}
	public Date getUrgeDecuteDateEnd() {
		return urgeDecuteDateEnd;
	}
	public void setUrgeDecuteDateEnd(Date urgeDecuteDateEnd) {
		this.urgeDecuteDateEnd = urgeDecuteDateEnd;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	
	public String getFinalProductType() {
		return finalProductType;
	}
	public void setFinalProductType(String finalProductType) {
		this.finalProductType = finalProductType;
	}
	public Double getUrgeMoeny() {
		return urgeMoeny;
	}
	public void setUrgeMoeny(Double urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}
	
	
	public String getConditionalThroughFlag() {
		return conditionalThroughFlag;
	}
	public void setConditionalThroughFlag(String conditionalThroughFlag) {
		this.conditionalThroughFlag = conditionalThroughFlag;
	}
	
}
