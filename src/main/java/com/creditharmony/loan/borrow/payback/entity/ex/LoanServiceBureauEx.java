package com.creditharmony.loan.borrow.payback.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 	
 * @Class Name LoanServiceBureau Entity
 * @author 李强
 * @Create 2015年12月9日
 */
@SuppressWarnings("serial")
public class LoanServiceBureauEx extends DataEntity<LoanServiceBureauEx> {

	private String ids;
	private String monthId;                      // 期供ID
	private String contractCode;				 // 合同编号
	private BigDecimal applyMoneyPayback;		 // 申请还款金额
	private BigDecimal applyAmountPayback;		 // 申请还期总额
	private BigDecimal applyAmountViolate;		 // 申请违约金总额
	private BigDecimal applyAmountPunish;		 // 申请还罚息总额
	private String dictPayResult;				 // 还款结果
	private String applyBackMmes;				 // 失败原因
	private String dictDealType; 				 // 划扣平台
	private String dictDealTypeLabel; 				 // 划扣平台名称
	private String customerName;				 // 客户名称
	private Date contractEndDay;				 // 合同到期日
	private String orgName;						 // 门店名称
	private String dictLoanStatus;				 // 借款状态
	private String dictLoanStatusLabel;				 // 借款状态名称
	private BigDecimal monthPayAmount;			 // 应还款金额
	private BigDecimal monthPayMoney;			 // 应还本金
	private BigDecimal monthInterestBackshould;	 // 应还利息
	private BigDecimal monthInterestPunishshould;// 应还罚息
	private BigDecimal monthPenaltyShould;		 // 应还违约金
	private String dictPayStatus;				 // 还款状态
	private String dictPayStatusLabel;				 // 还款状态名称
	private String dictPaybackStatus;            // 还款申请状态
	private String dictPaybackStatusLabel;       // 还款申请状态名称
	private Date monthPayDay;					 // 期供还款日
	private String loanMark;					 // 标识
	private String loanMarkLabel;					 // 标识名称
	private BigDecimal applyReallyAmount;		 // 实际还款金额
	private String dictPayUse;					 // 还款类型
	private String dictPayUseLabel;					 // 还款类型名称
	private String customerTelesalesFlag;		 // 是否电销
	private String customerTelesalesFlagLabel;		 // 是否电销名称
	private String dictSourceType;				 // 来源系统
	private String customerCertNum;				 // 证件号码
	private String productType;					 // 产品类型
	private String productLabel;					 // 产品类型
	private BigDecimal contractAmount;			 // 合同金额 
	private BigDecimal paybackMonthAmount;		 // 期供金额
	private int contractMonths;					 // 借款期限
	private BigDecimal notPunishViolate;		 // 未还违约金及罚息总金额
	private BigDecimal monthBeforeFinishAmount;  // 提前结清金额
	private BigDecimal interestforeFinishAmount; // 提前结清应还总金额
	private BigDecimal paybackBuleAmount;		 // 蓝补金额
	private BigDecimal reductionAmount;			 // 减免金额
	private BigDecimal monthPenaltyActual;		 // 实还违约金
	private BigDecimal monthInterestPunishactual;// 实还罚息
	private BigDecimal monthLateFee;              // 滞纳金
	private BigDecimal actualMonthLateFee;        // 实还滞纳金
	private BigDecimal monthLateFeeReduction;     // 减免滞纳金
	private BigDecimal monthPenaltyReduction;	 // 减免违约金
	private BigDecimal monthPunishReduction;	 // 减免罚息
	private String splitBackResult;			 	 // 回盘结果
	private String splitBackResultLabel;			 	 // 回盘结果名称
	private String splitFailResult;				 // 失败原因
	
	private BigDecimal applyDeductAmount;		 // 划扣金额
	private Date applyPayDay;				 	 // 还款申请日期
	private String applyAccountName;			 // 账号名称
	private String applyDeductAccount;			 // 划扣账号
	private String applyBankName;				 // 开户行全称
	private BigDecimal shouldLoanAmount;		 // 本次应还款金额
	private Date contractReplayDay;				 // 起始还款日
	private String rPaybackId;					 // 还款主表ID
	private String dictRepayMethod;				 // 还款方式
	private String dictRepayMethodLabel;				 // 还款方式名称
	private String enumOne;
	private String enumTwo;
	private String repaymentDate;				 // 页面搜索框还款日
	private String dictOffsetType;				 // 冲抵方式
	private String uploadFilename;               // 上传提前结清申请资料的文件名
	private String chargeStatus;                 // 冲抵状态
	private String urgeManage;                   // 催收管辖
	private String posBillCode;                  // POS订单号
	private String dictPosType;                  // POS平台
	private String dictPosTypeLabel;                  // POS平台名称
	private String posAccount;                   //存款账户
	private String dictDepositAccount;         // 选择汇款方式的存入账户
	private String store;
	private String storeId;
	private String storeName;
	private BigDecimal settleTotalAmount;     // 提前结清应还款总额
	private BigDecimal applyBuleAmount;       // 申请蓝补金额
    private String queryRight;                    // 查询权限
    private String model;                      // 模式
    private String modelLabel;   
    
    private String failReason;  // 失败原因
    
    private Date modifyTime;   // 划扣时间
    
    private Date beginDate;
	private Date endDate;
	private String   phoneSaleSign;  // 是否电销
	
	public String getProductLabel() {
		return productLabel;
	}
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	public String getPosAccount() {
		return posAccount;
	}
	public void setPosAccount(String posAccount) {
		this.posAccount = posAccount;
	}
	public String getPosBillCode() {
		return posBillCode;
	}
	public void setPosBillCode(String posBillCode) {
		this.posBillCode = posBillCode;
	}
	public String getDictPosType() {
		return dictPosType;
	}
	public void setDictPosType(String dictPosType) {
		this.dictPosType = dictPosType;
	}
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public String getUrgeManage() {
		return urgeManage;
	}
	public void setUrgeManage(String urgeManage) {
		this.urgeManage = urgeManage;
	}
	public String getEnumOne() {
		return enumOne;
	}
	public void setEnumOne(String enumOne) {
		this.enumOne = enumOne;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getContractCode() {
		return contractCode;
	}
	
	public String getEnumTwo() {
		return enumTwo;
	}
	public void setEnumTwo(String enumTwo) {
		this.enumTwo = enumTwo;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getDictRepayMethod() {
		return dictRepayMethod;
	}
	public void setDictRepayMethod(String dictRepayMethod) {
		this.dictRepayMethod = dictRepayMethod;
	}
	public String getSplitBackResult() {
		return splitBackResult;
	}
	public void setSplitBackResult(String splitBackResult) {
		this.splitBackResult = splitBackResult;
	}
	
	public String getDictOffsetType() {
		return dictOffsetType;
	}
	public void setDictOffsetType(String dictOffsetType) {
		this.dictOffsetType = dictOffsetType;
	}
	public String getSplitFailResult() {
		return splitFailResult;
	}
	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}
	public String getrPaybackId() {
		return rPaybackId;
	}
	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}
	public Date getContractReplayDay() {
		return contractReplayDay;
	}
	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public BigDecimal getApplyMoneyPayback() {
		return applyMoneyPayback;
	}
	public void setApplyMoneyPayback(BigDecimal applyMoneyPayback) {
		this.applyMoneyPayback = applyMoneyPayback;
	}
	public BigDecimal getApplyAmountPayback() {
		return applyAmountPayback;
	}
	public void setApplyAmountPayback(BigDecimal applyAmountPayback) {
		this.applyAmountPayback = applyAmountPayback;
	}
	public BigDecimal getApplyAmountViolate() {
		return applyAmountViolate;
	}
	public void setApplyAmountViolate(BigDecimal applyAmountViolate) {
		this.applyAmountViolate = applyAmountViolate;
	}
	public BigDecimal getApplyAmountPunish() {
		return applyAmountPunish;
	}
	public void setApplyAmountPunish(BigDecimal applyAmountPunish) {
		this.applyAmountPunish = applyAmountPunish;
	}
	public String getDictPayResult() {
		return dictPayResult;
	}
	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}
	public String getApplyBackMmes() {
		return applyBackMmes;
	}
	public void setApplyBackMmes(String applyBackMmes) {
		this.applyBackMmes = applyBackMmes;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public BigDecimal getMonthPayMoney() {
		return monthPayMoney;
	}
	public void setMonthPayMoney(BigDecimal monthPayMoney) {
		this.monthPayMoney = monthPayMoney;
	}
	public BigDecimal getMonthInterestBackshould() {
		return monthInterestBackshould;
	}
	public void setMonthInterestBackshould(BigDecimal monthInterestBackshould) {
		this.monthInterestBackshould = monthInterestBackshould;
	}
	public BigDecimal getMonthInterestPunishshould() {
		return monthInterestPunishshould;
	}
	public void setMonthInterestPunishshould(BigDecimal monthInterestPunishshould) {
		this.monthInterestPunishshould = monthInterestPunishshould;
	}
	public BigDecimal getMonthPenaltyShould() {
		return monthPenaltyShould;
	}
	public void setMonthPenaltyShould(BigDecimal monthPenaltyShould) {
		this.monthPenaltyShould = monthPenaltyShould;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public String getLoanMark() {
		return loanMark;
	}
	public void setLoanMark(String loanMark) {
		this.loanMark = loanMark;
	}
	public BigDecimal getMonthPayAmount() {
		return monthPayAmount;
	}
	public void setMonthPayAmount(BigDecimal monthPayAmount) {
		this.monthPayAmount = monthPayAmount;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	public String getDictPayUse() {
		return dictPayUse;
	}
	public void setDictPayUse(String dictPayUse) {
		this.dictPayUse = dictPayUse;
	}
	public String getCustomerTelesalesFlag() {
		return customerTelesalesFlag;
	}
	public void setCustomerTelesalesFlag(String customerTelesalesFlag) {
		this.customerTelesalesFlag = customerTelesalesFlag;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getPaybackMonthAmount() {
		return paybackMonthAmount;
	}
	public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
		this.paybackMonthAmount = paybackMonthAmount;
	}
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	public BigDecimal getNotPunishViolate() {
		return notPunishViolate;
	}
	public void setNotPunishViolate(BigDecimal notPunishViolate) {
		this.notPunishViolate = notPunishViolate;
	}
	public BigDecimal getMonthBeforeFinishAmount() {
		return monthBeforeFinishAmount;
	}
	public void setMonthBeforeFinishAmount(BigDecimal monthBeforeFinishAmount) {
		this.monthBeforeFinishAmount = monthBeforeFinishAmount;
	}
	
	public BigDecimal getInterestforeFinishAmount() {
		return interestforeFinishAmount;
	}
	public void setInterestforeFinishAmount(BigDecimal interestforeFinishAmount) {
		this.interestforeFinishAmount = interestforeFinishAmount;
	}
	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}
	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}
	public BigDecimal getReductionAmount() {
		return reductionAmount;
	}
	public void setReductionAmount(BigDecimal reductionAmount) {
		this.reductionAmount = reductionAmount;
	}
	public BigDecimal getMonthPenaltyActual() {
		return monthPenaltyActual;
	}
	public void setMonthPenaltyActual(BigDecimal monthPenaltyActual) {
		this.monthPenaltyActual = monthPenaltyActual;
	}
	public BigDecimal getMonthInterestPunishactual() {
		return monthInterestPunishactual;
	}
	public void setMonthInterestPunishactual(BigDecimal monthInterestPunishactual) {
		this.monthInterestPunishactual = monthInterestPunishactual;
	}
	public BigDecimal getMonthPenaltyReduction() {
		return monthPenaltyReduction;
	}
	public void setMonthPenaltyReduction(BigDecimal monthPenaltyReduction) {
		this.monthPenaltyReduction = monthPenaltyReduction;
	}
	public BigDecimal getMonthPunishReduction() {
		return monthPunishReduction;
	}
	public void setMonthPunishReduction(BigDecimal monthPunishReduction) {
		this.monthPunishReduction = monthPunishReduction;
	}
	public BigDecimal getApplyDeductAmount() {
		return applyDeductAmount;
	}
	public void setApplyDeductAmount(BigDecimal applyDeductAmount) {
		this.applyDeductAmount = applyDeductAmount;
	}
	public Date getApplyPayDay() {
		return applyPayDay;
	}
	public void setApplyPayDay(Date applyPayDay) {
		this.applyPayDay = applyPayDay;
	}
	public String getApplyAccountName() {
		return applyAccountName;
	}
	public void setApplyAccountName(String applyAccountName) {
		this.applyAccountName = applyAccountName;
	}
	public String getApplyDeductAccount() {
		return applyDeductAccount;
	}
	public void setApplyDeductAccount(String applyDeductAccount) {
		this.applyDeductAccount = applyDeductAccount;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public BigDecimal getShouldLoanAmount() {
		return shouldLoanAmount;
	}
	public void setShouldLoanAmount(BigDecimal shouldLoanAmount) {
		this.shouldLoanAmount = shouldLoanAmount;
	}
	public String getUploadFilename() {
		return uploadFilename;
	}
	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	/**
     * @return the queryRight
     */
    public String getQueryRight() {
        return queryRight;
    }
    /**
     * @param queryRight the String queryRight to set
     */
    public void setQueryRight(String queryRight) {
        this.queryRight = queryRight;
    }
    public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	public BigDecimal getMonthLateFee() {
		return monthLateFee;
	}
	public void setMonthLateFee(BigDecimal monthLateFee) {
		this.monthLateFee = monthLateFee;
	}
	public BigDecimal getActualMonthLateFee() {
		return actualMonthLateFee;
	}
	public void setActualMonthLateFee(BigDecimal actualMonthLateFee) {
		this.actualMonthLateFee = actualMonthLateFee;
	}
	public BigDecimal getMonthLateFeeReduction() {
		return monthLateFeeReduction;
	}
	public void setMonthLateFeeReduction(BigDecimal monthLateFeeReduction) {
		this.monthLateFeeReduction = monthLateFeeReduction;
	}
	public String getDictPaybackStatus() {
		return dictPaybackStatus;
	}
	public void setDictPaybackStatus(String dictPaybackStatus) {
		this.dictPaybackStatus = dictPaybackStatus;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getDictPayStatusLabel() {
		return dictPayStatusLabel;
	}
	public void setDictPayStatusLabel(String dictPayStatusLabel) {
		this.dictPayStatusLabel = dictPayStatusLabel;
	}
	public String getLoanMarkLabel() {
		return loanMarkLabel;
	}
	public void setLoanMarkLabel(String loanMarkLabel) {
		this.loanMarkLabel = loanMarkLabel;
	}
	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}
	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}
	public String getDictPosTypeLabel() {
		return dictPosTypeLabel;
	}
	public void setDictPosTypeLabel(String dictPosTypeLabel) {
		this.dictPosTypeLabel = dictPosTypeLabel;
	}
	public String getDictPayUseLabel() {
		return dictPayUseLabel;
	}
	public void setDictPayUseLabel(String dictPayUseLabel) {
		this.dictPayUseLabel = dictPayUseLabel;
	}
	public String getCustomerTelesalesFlagLabel() {
		return customerTelesalesFlagLabel;
	}
	public void setCustomerTelesalesFlagLabel(String customerTelesalesFlagLabel) {
		this.customerTelesalesFlagLabel = customerTelesalesFlagLabel;
	}
	public String getSplitBackResultLabel() {
		return splitBackResultLabel;
	}
	public void setSplitBackResultLabel(String splitBackResultLabel) {
		this.splitBackResultLabel = splitBackResultLabel;
	}
	public String getDictRepayMethodLabel() {
		return dictRepayMethodLabel;
	}
	public void setDictRepayMethodLabel(String dictRepayMethodLabel) {
		this.dictRepayMethodLabel = dictRepayMethodLabel;
	}
	public String getDictDepositAccount() {
		return dictDepositAccount;
	}
	public void setDictDepositAccount(String dictDepositAccount) {
		this.dictDepositAccount = dictDepositAccount;
	}
	public BigDecimal getSettleTotalAmount() {
		return settleTotalAmount;
	}
	public void setSettleTotalAmount(BigDecimal settleTotalAmount) {
		this.settleTotalAmount = settleTotalAmount;
	}
	public BigDecimal getApplyBuleAmount() {
		return applyBuleAmount;
	}
	public void setApplyBuleAmount(BigDecimal applyBuleAmount) {
		this.applyBuleAmount = applyBuleAmount;
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
	public String getDictPaybackStatusLabel() {
		return dictPaybackStatusLabel;
	}
	public void setDictPaybackStatusLabel(String dictPaybackStatusLabel) {
		this.dictPaybackStatusLabel = dictPaybackStatusLabel;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPhoneSaleSign() {
		return phoneSaleSign;
	}
	public void setPhoneSaleSign(String phoneSaleSign) {
		this.phoneSaleSign = phoneSaleSign;
	}
	
}
