package com.creditharmony.loan.borrow.transate.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 信借审批信息详情实体类
 * @Class Name LoanMinuteEntity
 * @author lirui
 * @Create In 2015年12月3日
 */
@SuppressWarnings("serial")
public class LoanMinuteEx extends DataEntity<LoanMinuteEx> {
	private String	loanCode;					// 借款编码
	private String	contractCode;				// 借款合同编号
	private String	dictLoanUse;				// 组织机构ID
	private String	customerName;				// 借款人姓名
	private String	customerCertType;			// 借款人证件类型
	private String  customerCertTypeLabel;		// 借款人证件类型名称
	private String	customerCertNum;			// 借款人证件号码
	private String	coroName;					// 共借人姓名
	private String	coroCertType;				// 共借人证件类型
	private String  coroCertTypeLabel;			// 共借人证件类型名称
	private String	coroCertNum;				// 共借人证件号码
	private String	auditEnsureName;			// 保证人
	private String	auditLegalMan;				// 法定代表人
	private String	ensuremanBusinessPlace;		// 经营场所
	private String 	middleName;					// 中间人
	private Date	contractReplayDate;			// 起始还款日期
	private String 	productName;				// 批复产品
	private String	dictRepayMethod;			// 还款付息方式
	private String  dictRepayMethodLabel;		// 还款付息方式名称
	private BigDecimal	contractMonths;			// 还款期数
	private BigDecimal	feeAllRaio;				// 产品总费率
	private String	feeLoanRate;				// 借款利率
	private BigDecimal	feePetitionFee;			// 信访费
	private BigDecimal	feeExpeditedFee;		// 加急费
	private BigDecimal	money;					// 批借金额
	private String	loanMarking;				// 标识
	private BigDecimal	feeUrgedService;		// 催收服务费
	private BigDecimal	feeRealput;				// 实放金额
	private BigDecimal	feeConsult;				// 咨询费
	private BigDecimal	contractMoney;			// 合同金额
	private BigDecimal	feeCredit;				// 审核费
	private BigDecimal	contractBackMonthMoney;	// 月还款
	private BigDecimal	feeService;				// 居间服务费
	private BigDecimal	contractExpectCount;	// 预计还款总额
	private BigDecimal	feeInfoService;			// 信息服务费
	private Date	contractFactDate;			// 合同签署日期
	private BigDecimal	feeCount;				// 综合费用
	private String  loanId;						// 借款信息表主键
	private String applyId;						// 流程id
	private String trustCash;					// 委托提现
	private String trustRecharge;				// 委托充值
	private BigDecimal monthFeeService;			// 分期服务费
	private BigDecimal contractMonthRepayTotal;	// 月还款总额
	private BigDecimal monthFeeConsult;			// 分期咨询费
	private BigDecimal monthMidFeeService;		// 分期居间服务费
	private String model;   //借款模式
	private String riskLevel;
	private BigDecimal comprehensiveServiceRate;	//前期综合费率
    private BigDecimal monthRateService;	//分期服务费率

    private String loanInfoOldOrNewFlag;    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面

    private String	companyname;			// 合同表保证人
	private String	legalman;				// 合同表法定代表人
	private String	maddress;		// 合同表经营场所
		
    private String item_distance;    //外访距离
    private String outside_flag;     //外访标识
	private String  maddressName;	//省市区合同经营
	private String  contractVersion;	//版本号
	
	private String paperless;                // 是否纸质化(0:否,1:是)
	private String new_flag;//
	private String oldLoanCode;
	//月利息
    private BigDecimal monthFee;
    //月服务费
    private BigDecimal monthServiceFee;
    
    private String productType;
	
	
	public String getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(String new_flag) {
		this.new_flag = new_flag;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public BigDecimal getMonthFeeService() {
		return monthFeeService;
	}
	public void setMonthFeeService(BigDecimal monthFeeService) {
		this.monthFeeService = monthFeeService;
	}
	public BigDecimal getContractMonthRepayTotal() {
		return contractMonthRepayTotal;
	}
	public void setContractMonthRepayTotal(BigDecimal contractMonthRepayTotal) {
		this.contractMonthRepayTotal = contractMonthRepayTotal;
	}
	public BigDecimal getMonthFeeConsult() {
		return monthFeeConsult;
	}
	public void setMonthFeeConsult(BigDecimal monthFeeConsult) {
		this.monthFeeConsult = monthFeeConsult;
	}
	public BigDecimal getMonthMidFeeService() {
		return monthMidFeeService;
	}
	public void setMonthMidFeeService(BigDecimal monthMidFeeService) {
		this.monthMidFeeService = monthMidFeeService;
	}
	public String getCustomerCertTypeLabel() {
		return customerCertTypeLabel;
	}
	public void setCustomerCertTypeLabel(String customerCertTypeLabel) {
		this.customerCertTypeLabel = customerCertTypeLabel;
	}
	public String getCoroCertTypeLabel() {
		return coroCertTypeLabel;
	}
	public void setCoroCertTypeLabel(String coroCertTypeLabel) {
		this.coroCertTypeLabel = coroCertTypeLabel;
	}
	public String getDictRepayMethodLabel() {
		return dictRepayMethodLabel;
	}
	public void setDictRepayMethodLabel(String dictRepayMethodLabel) {
		this.dictRepayMethodLabel = dictRepayMethodLabel;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getDictLoanUse() {
		return dictLoanUse;
	}
	public void setDictLoanUse(String dictLoanUse) {
		this.dictLoanUse = dictLoanUse;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCertType() {
		return customerCertType;
	}
	public void setCustomerCertType(String customerCertType) {
		this.customerCertType = customerCertType;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getCoroName() {
		return coroName;
	}
	public void setCoroName(String coroName) {
		this.coroName = coroName;
	}
	public String getCoroCertType() {
		return coroCertType;
	}
	public void setCoroCertType(String coroCertType) {
		this.coroCertType = coroCertType;
	}
	public String getCoroCertNum() {
		return coroCertNum;
	}
	public void setCoroCertNum(String coroCertNum) {
		this.coroCertNum = coroCertNum;
	}
	public String getAuditEnsureName() {
		return auditEnsureName;
	}
	public void setAuditEnsureName(String auditEnsureName) {
		this.auditEnsureName = auditEnsureName;
	}
	public String getAuditLegalMan() {
		return auditLegalMan;
	}
	public void setAuditLegalMan(String auditLegalMan) {
		this.auditLegalMan = auditLegalMan;
	}
	public String getEnsuremanBusinessPlace() {
		return ensuremanBusinessPlace;
	}
	public void setEnsuremanBusinessPlace(String ensuremanBusinessPlace) {
		this.ensuremanBusinessPlace = ensuremanBusinessPlace;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Date getContractReplayDate() {
		return contractReplayDate;
	}
	public void setContractReplayDate(Date contractReplayDate) {
		this.contractReplayDate = contractReplayDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDictRepayMethod() {
		return dictRepayMethod;
	}
	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}
	public BigDecimal getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(BigDecimal contractMonths) {
		this.contractMonths = contractMonths;
	}
	public BigDecimal getFeeAllRaio() {
		return feeAllRaio;
	}
	public void setFeeAllRaio(BigDecimal feeAllRaio) {
		this.feeAllRaio = feeAllRaio;
	}
	public String getFeeLoanRate() {
		return feeLoanRate;
	}
	public void setFeeLoanRate(String feeLoanRate) {
		this.feeLoanRate = feeLoanRate;
	}
	public BigDecimal getFeePetitionFee() {
		return feePetitionFee;
	}
	public void setFeePetitionFee(BigDecimal feePetitionFee) {
		this.feePetitionFee = feePetitionFee;
	}
	public BigDecimal getFeeExpeditedFee() {
		return feeExpeditedFee;
	}
	public void setFeeExpeditedFee(BigDecimal feeExpeditedFee) {
		this.feeExpeditedFee = feeExpeditedFee;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getLoanMarking() {
		return loanMarking;
	}
	public void setLoanMarking(String loanMarking) {
		this.loanMarking = loanMarking;
	}
	public BigDecimal getFeeUrgedService() {
		return feeUrgedService;
	}
	public void setFeeUrgedService(BigDecimal feeUrgedService) {
		this.feeUrgedService = feeUrgedService;
	}
	public BigDecimal getFeeRealput() {
		return feeRealput;
	}
	public void setFeeRealput(BigDecimal feeRealput) {
		this.feeRealput = feeRealput;
	}
	public BigDecimal getFeeConsult() {
		return feeConsult;
	}
	public void setFeeConsult(BigDecimal feeConsult) {
		this.feeConsult = feeConsult;
	}
	public BigDecimal getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}
	public BigDecimal getFeeCredit() {
		return feeCredit;
	}
	public void setFeeCredit(BigDecimal feeCredit) {
		this.feeCredit = feeCredit;
	}
	public BigDecimal getContractBackMonthMoney() {
		return contractBackMonthMoney;
	}
	public void setContractBackMonthMoney(BigDecimal contractBackMonthMoney) {
		this.contractBackMonthMoney = contractBackMonthMoney;
	}
	public BigDecimal getFeeService() {
		return feeService;
	}
	public void setFeeService(BigDecimal feeService) {
		this.feeService = feeService;
	}
	public BigDecimal getContractExpectCount() {
		return contractExpectCount;
	}
	public void setContractExpectCount(BigDecimal contractExpectCount) {
		this.contractExpectCount = contractExpectCount;
	}
	public BigDecimal getFeeInfoService() {
		return feeInfoService;
	}
	public void setFeeInfoService(BigDecimal feeInfoService) {
		this.feeInfoService = feeInfoService;
	}
	public Date getContractFactDate() {
		return contractFactDate;
	}
	public void setContractFactDate(Date contractFactDate) {
		this.contractFactDate = contractFactDate;
	}
	public BigDecimal getFeeCount() {
		return feeCount;
	}
	public void setFeeCount(BigDecimal feeCount) {
		this.feeCount = feeCount;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getTrustCash() {
		return trustCash;
	}
	public void setTrustCash(String trustCash) {
		this.trustCash = trustCash;
	}
	public String getTrustRecharge() {
		return trustRecharge;
	}
	public void setTrustRecharge(String trustRecharge) {
		this.trustRecharge = trustRecharge;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public BigDecimal getComprehensiveServiceRate() {
		return comprehensiveServiceRate;
	}
	public void setComprehensiveServiceRate(BigDecimal comprehensiveServiceRate) {
		this.comprehensiveServiceRate = comprehensiveServiceRate;
	}
	public BigDecimal getMonthRateService() {
		return monthRateService;
	}
	public void setMonthRateService(BigDecimal monthRateService) {
		this.monthRateService = monthRateService;
	}	

	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}	
	
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getLegalman() {
		return legalman;
	}
	public void setLegalman(String legalman) {
		this.legalman = legalman;
	}
	 
	 
	public String getMaddress() {
		return maddress;
	}
	public void setMaddress(String maddress) {
		this.maddress = maddress;
	}
	public String getMaddressName() {
		return maddressName;
	}
	public void setMaddressName(String maddressName) {
		this.maddressName = maddressName;
	}
	public String getItem_distance() {
		return item_distance;
	}
	public void setItem_distance(String item_distance) {
		this.item_distance = item_distance;
	}
	public String getOutside_flag() {
		return outside_flag;
	}
	public void setOutside_flag(String outside_flag) {
		this.outside_flag = outside_flag;
	}
	public String getPaperless() {
		return paperless;
	}
	public void setPaperless(String paperless) {
		this.paperless = paperless;
	}
	public String getOldLoanCode() {
		return oldLoanCode;
	}
	public void setOldLoanCode(String oldLoanCode) {
		this.oldLoanCode = oldLoanCode;
	}
	public BigDecimal getMonthFee() {
		return monthFee;
	}
	public void setMonthFee(BigDecimal monthFee) {
		this.monthFee = monthFee;
	}
	public BigDecimal getMonthServiceFee() {
		return monthServiceFee;
	}
	public void setMonthServiceFee(BigDecimal monthServiceFee) {
		this.monthServiceFee = monthServiceFee;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	
	
}
