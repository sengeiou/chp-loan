
package com.creditharmony.loan.borrow.grant.entity.ex;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 放款明细确认，客户信息表导出
 * @Class Name GrantAuditEx
 * @author 朱静越
 * @Create In 2015年12月21日
 */
public class GrantCustomerEx extends DataEntity<GrantCustomerEx> {
	private static final long serialVersionUID = 3962172465594535962L;
	// applyId
	private String applyId;
	// 放款id,
	private String id;
	// 合同编号6
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1)
	private String contractCode; 
	// 客户姓名1
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10)
	private String  customerName;
	// 产品名称4
	@ExcelField(title = "产品名称", type = 0, align = 2, sort = 40)
	private String productName;
	// 证件号码2
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 20)
	private String customerCertNum;
	// 性别3
	@ExcelField(title = "性别", type = 0, align = 2, sort = 30 , dictType = "jk_sex")
	private String customerSex;
	// 所在地7
	@ExcelField(title = "所在地", type = 0, align = 2, sort = 70)
	private String storesName;
	// 信访费
	@ExcelField(title = "信访费", type = 0, align = 2, sort = 290)
	private String feePetition;
	// 审核次数
	@ExcelField(title = "审核次数", type = 0, align = 2, sort = 290)
	private Integer checkCount;
	// 最后一次退回原因
	@ExcelField(title = "最后一次退回原因", type = 0, align = 2, sort = 290,dictType="jk_chk_back_reason")
	private String backReason;
	// 审核人
	@ExcelField(title = "审核人", type = 0, align = 2, sort = 290)
	private String checkId;
	// 期数13
	// 审核日期暂时未定。。。。。。。。。。。14
	@ExcelField(title = "期数", type = 0, align = 2, sort = 130)
	private int  contractMonths;
	@ExcelField(title = "审核日期", type = 0, align = 2, sort = 140)
	private Date contractAuditDate;
	// 首期还款日15
	@ExcelField(title = "首期还款日", type = 0, align = 2, sort = 150)
	private Date contractReplayDay;
	// 合同到期日16
	@ExcelField(title = "合同到期日", type = 0, align = 2, sort = 160)
	private Date contractEndDay;
	// 账单日未定17。。。。。。。。。。。。。
	// 客户经理18
	@ExcelField(title = "客户经理", type = 0, align = 2, sort = 180)
	private String managerName;
	// 团队经理19
	@ExcelField(title = "团队经理", type = 0, align = 2, sort = 190)	
	private String teamManagerName;
	// 客服人员20
	@ExcelField(title = "客服人员", type = 0, align = 2, sort = 200)		
	private String serviceUserName;
	// 外访人员21
	@ExcelField(title = "外访人员", type = 0, align = 2, sort = 210)		
	private String surveyEmpId;
	// 合同签订日期23
	@ExcelField(title = "合同签订日期", type = 0, align = 2, sort = 230)		
	private Date contractFactDay;
	// 合同金额11
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 110)
	private BigDecimal contractAmount;
	// 实放金额10
	@ExcelField(title = "实放金额", type = 0, align = 2, sort = 100)
	private BigDecimal feePaymentAmount;
	// 月还款金额12
	@ExcelField(title = "月还款金额", type = 0, align = 2, sort = 120)
	private BigDecimal contractMonthRepayAmount;
	// 标识22
	@ExcelField(title = "标识", type = 0, align = 2, sort = 220 , dictType = "jk_channel_flag")
	private String loanFlag;
	// 是否电销24
	@ExcelField(title = "是否电销", type = 0, align = 2, sort = 240,dictType = "jk_telemarketing")
	private String customerTelesalesFlag;
	// 客户身份证有效期25
	@ExcelField(title = "客户身份证开始日期", type = 0, align = 2, sort = 250)
	private Date idStartDay;
	// 客户身份证有效期26
	@ExcelField(title = "客户身份证结束日期", type = 0, align = 2, sort = 260)
	private Date idEndDay;
	// 划扣平台27
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 270 , dictType = "jk_deduct_plat")
	private String bankSigningPlatform;
	// 合同版本号28
	@ExcelField(title = "合同版本号", type = 0, align = 2, sort = 280)
	private String contractVersion;
	// 催收服务费29
	@ExcelField(title = "催收服务费", type = 0, align = 2, sort = 290)
	private BigDecimal feeUrgedService;
	// 用户银行卡号8
	@ExcelField(title = "还款银行账号", type = 0, align = 2, sort = 80)
	private String bankAccount;
	// 用户开户行9
	@ExcelField(title = "开户行", type = 0, align = 2, sort = 90 , dictType = "jk_open_bank")
	private String bankName;
	// 是否加急
	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 240,dictType="jk_urgent_flag")
	private String loanUrgentFlag;
	
	private String riskLevel;
	
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
		
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public String getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(String feePetition) {
		this.feePetition = feePetition;
	}
	public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
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
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getTeamManagerName() {
		return teamManagerName;
	}
	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}
	public String getServiceUserName() {
		return serviceUserName;
	}
	public void setServiceUserName(String serviceUserName) {
		this.serviceUserName = serviceUserName;
	}
	public String getSurveyEmpId() {
		return surveyEmpId;
	}
	public void setSurveyEmpId(String surveyEmpId) {
		this.surveyEmpId = surveyEmpId;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getFeePaymentAmount() {
		return feePaymentAmount;
	}
	public void setFeePaymentAmount(BigDecimal feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}
	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}
	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}
	public Date getIdStartDay() {
		return idStartDay;
	}
	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}
	public Date getIdEndDay() {
		return idEndDay;
	}
	public void setIdEndDay(Date idEndDay) {
		this.idEndDay = idEndDay;
	}
	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}
	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}
	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}
	public Date getContractAuditDate() {
		return contractAuditDate;
	}
	public void setContractAuditDate(Date contractAuditDate) {
		this.contractAuditDate = contractAuditDate;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
}