package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 	
 * @Class Name RepaymentReminder Entity
 * @author liqiang
 * @Create 2015年11月23日
 */
@SuppressWarnings("serial")
public class RepaymentReminderEx extends DataEntity<RepaymentReminderEx> {

	private String id;						//期供ID
	private String monthId;
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 20)
	private String contractCode;			//合同编号
	@ExcelField(title = "客户名称", type = 0, align = 2, sort = 10)
	private String customerName;			//客户姓名
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 30)
	private String stroeName;				//门店名称
	@ExcelField(title = "划扣银行", type = 0, align = 2, sort = 40,dictType="jk_open_bank")
	private String applyBankName;			//开户行名称
	private String applyBankNameLabel;			//开户行名字
	@ExcelField(title = "划扣账号", type = 0, align = 2, sort = 50)
	private String bankAccount;             //账户
	private String dictBankId;
    private Integer bankTopFlag ;           //置顶
	private String offendTel;				//手机号码
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 100)
	private Integer monthPayDay;				//还款日
	private int months;						//期数
	@ExcelField(title = "当期应还金额", type = 0, align = 2, sort = 90)
	private BigDecimal payMoney;					//应还金额
	@ExcelField(title = "月还期供", type = 0, align = 2, sort = 70)
	private float completeMoney;			//月(应)还金额
	private float buleAmont;				//蓝补金额
	private String dictMonthStatus;			//期供状态
	private String dictMonthStatusLabel;	//期供状态名
	private String status;					//状态（	确认是否待还款）
	@ExcelField(title = "标识", type = 0, align = 2, sort = 110,dictType="jk_new_old_sys_flag")
	private String logo;					//标识
	private String logoLabel;				//标识名
	private String remindLogo;				//提醒标识
	private String dictDealType;			//划扣平台
	private String dictDealTypeLabel;		//划扣平台名称
	private String storeRemark;				//门店备注
	private String storeRemarkUserid;		//门店备注人
	private String orgId;					//风控备注
	private String riskcontrolRemarkUserid;	//风控备注人
	private Date paybackMonthMoneyDate;		//期供还款日期
	private String paybackMonthMoneyDateStr;
	private Date contractEndTimestamp;		//合同到期日
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 60)
	private BigDecimal contractMoney;				//合同金额
	@ExcelField(title = "已还金额", type = 0, align = 2, sort = 80) 
    private BigDecimal completeAmount;      //已还金额
	private String customerSex;				//性别
	private float grantAmount;				//放款金额
	private String customerCode;			//客户编码
	private String dictLoanStatus;			//借款状态
	private String dictLoanStatusLabel;		//借款状态名称
	private String repaymentDate;			// 页面搜索框还款日
	private Date monthPaybackDate;              //期供还款日
	private String loanStoreOrgId;
	private String contractVersion;         // 合同版本号
	private BigDecimal monthFeeService;          //分期服务费
	private BigDecimal actualMonthFeeService;    //分期服务费实还
	private BigDecimal monthPayAmount;// 应还本金
	private BigDecimal monthInterestBackshould;// 应还利息
	private BigDecimal monthCapitalPayactual;// 实还本金
	private BigDecimal monthInterestPayactual;	// 实还利息
	private String sumNumber; // 划扣总笔数
	private BigDecimal sumAmont;  // 划扣总金额
	private String model;
	private String modelLabel;
	// 客户经理
	private String loanManagerName;
	// 团队经理
	private String loanTeamManagerName;
	// 外访人员
	private String loanSurveyEmpName;
	// 客服
	private String loanCustomerService;
	   // 数据查询权限
	private String queryRight;
	
	private boolean success;
	
	
	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	public String getLoanStoreOrgId() {
		return loanStoreOrgId;
	}
	public void setLoanStoreOrgId(String loanStoreOrgId) {
		this.loanStoreOrgId = loanStoreOrgId;
	}
	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}
	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}
	public String getLogoLabel() {
		return logoLabel;
	}
	public void setLogoLabel(String logoLabel) {
		this.logoLabel = logoLabel;
	}
	public String getDictMonthStatusLabel() {
		return dictMonthStatusLabel;
	}
	public void setDictMonthStatusLabel(String dictMonthStatusLabel) {
		this.dictMonthStatusLabel = dictMonthStatusLabel;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
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
	public String getStroeName() {
		return stroeName;
	}
	public void setStroeName(String stroeName) {
		this.stroeName = stroeName;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getOffendTel() {
		return offendTel;
	}
	public void setOffendTel(String offendTel) {
		this.offendTel = offendTel;
	}
	
	
	public Integer getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Integer monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public float getCompleteMoney() {
		return completeMoney;
	}
	public void setCompleteMoney(float completeMoney) {
		this.completeMoney = completeMoney;
	}
	public float getBuleAmont() {
		return buleAmont;
	}
	public void setBuleAmont(float buleAmont) {
		this.buleAmont = buleAmont;
	}
	public String getDictMonthStatus() {
		return dictMonthStatus;
	}
	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getRemindLogo() {
		return remindLogo;
	}
	public void setRemindLogo(String remindLogo) {
		this.remindLogo = remindLogo;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public String getStoreRemark() {
		return storeRemark;
	}
	public void setStoreRemark(String storeRemark) {
		this.storeRemark = storeRemark;
	}
	public String getStoreRemarkUserid() {
		return storeRemarkUserid;
	}
	public void setStoreRemarkUserid(String storeRemarkUserid) {
		this.storeRemarkUserid = storeRemarkUserid;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRiskcontrolRemarkUserid() {
		return riskcontrolRemarkUserid;
	}
	public void setRiskcontrolRemarkUserid(String riskcontrolRemarkUserid) {
		this.riskcontrolRemarkUserid = riskcontrolRemarkUserid;
	}
	public Date getPaybackMonthMoneyDate() {
		return paybackMonthMoneyDate;
	}
	public void setPaybackMonthMoneyDate(Date paybackMonthMoneyDate) {
		this.paybackMonthMoneyDate = paybackMonthMoneyDate;
	}
	public Date getContractEndTimestamp() {
		return contractEndTimestamp;
	}
	public void setContractEndTimestamp(Date contractEndTimestamp) {
		this.contractEndTimestamp = contractEndTimestamp;
	}
	
	public BigDecimal getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public float getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(float grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Integer getBankTopFlag() {
		return bankTopFlag;
	}
	public void setBankTopFlag(Integer bankTopFlag) {
		this.bankTopFlag = bankTopFlag;
	}
	public BigDecimal getCompleteAmount() {
		return completeAmount;
	}
	public void setCompleteAmount(BigDecimal completeAmount) {
		this.completeAmount = completeAmount;
	}
	public Date getMonthPaybackDate() {
		return monthPaybackDate;
	}
	public void setMonthPaybackDate(Date monthPaybackDate) {
		this.monthPaybackDate = monthPaybackDate;
	}
	public String getApplyBankNameLabel() {
		return applyBankNameLabel;
	}
	public void setApplyBankNameLabel(String applyBankNameLabel) {
		this.applyBankNameLabel = applyBankNameLabel;
	}
	public String getPaybackMonthMoneyDateStr() {
		return paybackMonthMoneyDateStr;
	}
	public void setPaybackMonthMoneyDateStr(String paybackMonthMoneyDateStr) {
		this.paybackMonthMoneyDateStr = paybackMonthMoneyDateStr;
	}
	public String getDictBankId() {
		return dictBankId;
	}
	public void setDictBankId(String dictBankId) {
		this.dictBankId = dictBankId;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public BigDecimal getMonthFeeService() {
		return monthFeeService;
	}
	public void setMonthFeeService(BigDecimal monthFeeService) {
		this.monthFeeService = monthFeeService;
	}
	public BigDecimal getActualMonthFeeService() {
		return actualMonthFeeService;
	}
	public void setActualMonthFeeService(BigDecimal actualMonthFeeService) {
		this.actualMonthFeeService = actualMonthFeeService;
	}
	public BigDecimal getMonthPayAmount() {
		return monthPayAmount;
	}
	public void setMonthPayAmount(BigDecimal monthPayAmount) {
		this.monthPayAmount = monthPayAmount;
	}
	public BigDecimal getMonthInterestBackshould() {
		return monthInterestBackshould;
	}
	public void setMonthInterestBackshould(BigDecimal monthInterestBackshould) {
		this.monthInterestBackshould = monthInterestBackshould;
	}
	public BigDecimal getMonthCapitalPayactual() {
		return monthCapitalPayactual;
	}
	public void setMonthCapitalPayactual(BigDecimal monthCapitalPayactual) {
		this.monthCapitalPayactual = monthCapitalPayactual;
	}
	public BigDecimal getMonthInterestPayactual() {
		return monthInterestPayactual;
	}
	public void setMonthInterestPayactual(BigDecimal monthInterestPayactual) {
		this.monthInterestPayactual = monthInterestPayactual;
	}
	public String getLoanManagerName() {
		return loanManagerName;
	}
	public void setLoanManagerName(String loanManagerName) {
		this.loanManagerName = loanManagerName;
	}
	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}
	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}
	public String getLoanSurveyEmpName() {
		return loanSurveyEmpName;
	}
	public void setLoanSurveyEmpName(String loanSurveyEmpName) {
		this.loanSurveyEmpName = loanSurveyEmpName;
	}
	public String getLoanCustomerService() {
		return loanCustomerService;
	}
	public void setLoanCustomerService(String loanCustomerService) {
		this.loanCustomerService = loanCustomerService;
	}
	public String getSumNumber() {
		return sumNumber;
	}
	public void setSumNumber(String sumNumber) {
		this.sumNumber = sumNumber;
	}
	public BigDecimal getSumAmont() {
		return sumAmont;
	}
	public void setSumAmont(BigDecimal sumAmont) {
		this.sumAmont = sumAmont;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getModelLabel() {
		return modelLabel;
	}
	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}
	
}
