package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 
·* 2017年2月22日
·* by Huowenlong
 */
public class LoanInfoAll extends DataEntity<LoanInfoAll> {
    private String id;

    private String applyId;

    private String loanCode;

    private String loanCustomerName;

    private Date loanApplyTime;

    private Integer loanMonths;

    private BigDecimal loanApplyAmount;

    private String realyUse;

    private String loanCommonRepaymentFlag;

    private String dictRepayMethod;

    private String dictLoanStatus;

    private String classType;

    private String productType;

    private String loanUrgentFlag;

    private String loanDeptType;

    private String dictLoanUse;

    private String loanApplyTop;

    private String loanBackTopStatus;

    private String dictSourceType;

    private String rId;

    private String dictIsCycle;

    private String dictIsAdditional;

    private Date customerIntoTime;

    private BigDecimal loanAuditAmount;

    private Date loanAuditTime;

    private String loanAuditProduct;

    private Integer loanAuditMonths;

    private Date loanSecondFinishtime;

    private String loanAdditionalApplyid;

    private String loanDecisionmakingCode;

    private String loanManagercode;

    private String loanTeamManagercode;

    private String loanTeamOrgid;

    private String loanSurveyEmpId;

    private String loanLastApplyId;

    private String dictLoanaType;

    private String loanRaiseFlag;

    private BigDecimal loanRaiseAmount;

    private Integer loanNum;

    private String loanFlag;

    private String loanCustomerService;

    private String remark;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String outsideFlag;

    private String loanStoreOrgid;

    private String visitFlag;

    private String customerSource;

    private String auditId;

    private String loanCodeOld;

    private String timeInterval;

    private String storeCode;

    private Date settledDate;

    private Integer frozenApplyTimes;

    private Date frozenLastApplyTime;

    private String kinnobuQuotaFlag;

    private String frozenCode;

    private String frozenReason;

    private String abandoningFlag;

    private String trustCash;

    private String trustRecharge;

    private String dictProfType;

    private Date outtoLoanTime;

    private String approveStep;

    private String approveResult;

    private String usingFlag;

    private String resultId;

    private String recordId;

    private String model;

    private String consTelesalesOrgcode;

    private String dictSourceTypePcl;

    private String orderField;

    private String mainPaybackResource;

    private String mainPaybackResourceRemark;

    private String otherIncomeResource;

    private String otherIncomeResourceRemark;

    private Integer comPaybackCount;

    private Date recommendTime;

    private String batch;

    private String loaninfoOldornewFlag;

    private Long highPaybackMonthMoney;

    private String dictLoanUseNewOther;

    private String dictLoanSource;

    private String dictLoanSourceOther;

    private String dictLoanSourceElse;

    private String dictLoanSourceElseOther;

    private Long otherMonthIncome;

    private String otherCompanyPaybackCount;

    private Long otherCompanyPaybackTotalmoney;

    private String quotaId;

    private String nodeFlag;

    private String sendFlag;

    private String lastDealUser;

    private String trackState;

    private String oldLoanCode;

    private String issplit;

    private Long zcj;

    private Long jinxin;

    private String backflag;

    private String outboundFlag;

    private String checkType;
    
    private String oldId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getLoanCustomerName() {
        return loanCustomerName;
    }

    public void setLoanCustomerName(String loanCustomerName) {
        this.loanCustomerName = loanCustomerName == null ? null : loanCustomerName.trim();
    }

    public Date getLoanApplyTime() {
        return loanApplyTime;
    }

    public void setLoanApplyTime(Date loanApplyTime) {
        this.loanApplyTime = loanApplyTime;
    }

    public Integer getLoanMonths() {
        return loanMonths;
    }

    public void setLoanMonths(Integer loanMonths) {
        this.loanMonths = loanMonths;
    }

    public BigDecimal getLoanApplyAmount() {
        return loanApplyAmount;
    }

    public void setLoanApplyAmount(BigDecimal loanApplyAmount) {
        this.loanApplyAmount = loanApplyAmount;
    }

    public String getRealyUse() {
        return realyUse;
    }

    public void setRealyUse(String realyUse) {
        this.realyUse = realyUse == null ? null : realyUse.trim();
    }

    public String getLoanCommonRepaymentFlag() {
        return loanCommonRepaymentFlag;
    }

    public void setLoanCommonRepaymentFlag(String loanCommonRepaymentFlag) {
        this.loanCommonRepaymentFlag = loanCommonRepaymentFlag == null ? null : loanCommonRepaymentFlag.trim();
    }

    public String getDictRepayMethod() {
        return dictRepayMethod;
    }

    public void setDictRepayMethod(String dictRepayMethod) {
        this.dictRepayMethod = dictRepayMethod == null ? null : dictRepayMethod.trim();
    }

    public String getDictLoanStatus() {
        return dictLoanStatus;
    }

    public void setDictLoanStatus(String dictLoanStatus) {
        this.dictLoanStatus = dictLoanStatus == null ? null : dictLoanStatus.trim();
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType == null ? null : classType.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getLoanUrgentFlag() {
        return loanUrgentFlag;
    }

    public void setLoanUrgentFlag(String loanUrgentFlag) {
        this.loanUrgentFlag = loanUrgentFlag == null ? null : loanUrgentFlag.trim();
    }

    public String getLoanDeptType() {
        return loanDeptType;
    }

    public void setLoanDeptType(String loanDeptType) {
        this.loanDeptType = loanDeptType == null ? null : loanDeptType.trim();
    }

    public String getDictLoanUse() {
        return dictLoanUse;
    }

    public void setDictLoanUse(String dictLoanUse) {
        this.dictLoanUse = dictLoanUse == null ? null : dictLoanUse.trim();
    }

    public String getLoanApplyTop() {
        return loanApplyTop;
    }

    public void setLoanApplyTop(String loanApplyTop) {
        this.loanApplyTop = loanApplyTop == null ? null : loanApplyTop.trim();
    }

    public String getLoanBackTopStatus() {
        return loanBackTopStatus;
    }

    public void setLoanBackTopStatus(String loanBackTopStatus) {
        this.loanBackTopStatus = loanBackTopStatus == null ? null : loanBackTopStatus.trim();
    }

    public String getDictSourceType() {
        return dictSourceType;
    }

    public void setDictSourceType(String dictSourceType) {
        this.dictSourceType = dictSourceType == null ? null : dictSourceType.trim();
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId == null ? null : rId.trim();
    }

    public String getDictIsCycle() {
        return dictIsCycle;
    }

    public void setDictIsCycle(String dictIsCycle) {
        this.dictIsCycle = dictIsCycle == null ? null : dictIsCycle.trim();
    }

    public String getDictIsAdditional() {
        return dictIsAdditional;
    }

    public void setDictIsAdditional(String dictIsAdditional) {
        this.dictIsAdditional = dictIsAdditional == null ? null : dictIsAdditional.trim();
    }

    public Date getCustomerIntoTime() {
        return customerIntoTime;
    }

    public void setCustomerIntoTime(Date customerIntoTime) {
        this.customerIntoTime = customerIntoTime;
    }

    public BigDecimal getLoanAuditAmount() {
        return loanAuditAmount;
    }

    public void setLoanAuditAmount(BigDecimal loanAuditAmount) {
        this.loanAuditAmount = loanAuditAmount;
    }

    public Date getLoanAuditTime() {
        return loanAuditTime;
    }

    public void setLoanAuditTime(Date loanAuditTime) {
        this.loanAuditTime = loanAuditTime;
    }

    public String getLoanAuditProduct() {
        return loanAuditProduct;
    }

    public void setLoanAuditProduct(String loanAuditProduct) {
        this.loanAuditProduct = loanAuditProduct == null ? null : loanAuditProduct.trim();
    }

    public Integer getLoanAuditMonths() {
        return loanAuditMonths;
    }

    public void setLoanAuditMonths(Integer loanAuditMonths) {
        this.loanAuditMonths = loanAuditMonths;
    }

    public Date getLoanSecondFinishtime() {
        return loanSecondFinishtime;
    }

    public void setLoanSecondFinishtime(Date loanSecondFinishtime) {
        this.loanSecondFinishtime = loanSecondFinishtime;
    }

    public String getLoanAdditionalApplyid() {
        return loanAdditionalApplyid;
    }

    public void setLoanAdditionalApplyid(String loanAdditionalApplyid) {
        this.loanAdditionalApplyid = loanAdditionalApplyid == null ? null : loanAdditionalApplyid.trim();
    }

    public String getLoanDecisionmakingCode() {
        return loanDecisionmakingCode;
    }

    public void setLoanDecisionmakingCode(String loanDecisionmakingCode) {
        this.loanDecisionmakingCode = loanDecisionmakingCode == null ? null : loanDecisionmakingCode.trim();
    }

    public String getLoanManagercode() {
        return loanManagercode;
    }

    public void setLoanManagercode(String loanManagercode) {
        this.loanManagercode = loanManagercode == null ? null : loanManagercode.trim();
    }

    public String getLoanTeamManagercode() {
        return loanTeamManagercode;
    }

    public void setLoanTeamManagercode(String loanTeamManagercode) {
        this.loanTeamManagercode = loanTeamManagercode == null ? null : loanTeamManagercode.trim();
    }

    public String getLoanTeamOrgid() {
        return loanTeamOrgid;
    }

    public void setLoanTeamOrgid(String loanTeamOrgid) {
        this.loanTeamOrgid = loanTeamOrgid == null ? null : loanTeamOrgid.trim();
    }

    public String getLoanSurveyEmpId() {
        return loanSurveyEmpId;
    }

    public void setLoanSurveyEmpId(String loanSurveyEmpId) {
        this.loanSurveyEmpId = loanSurveyEmpId == null ? null : loanSurveyEmpId.trim();
    }

    public String getLoanLastApplyId() {
        return loanLastApplyId;
    }

    public void setLoanLastApplyId(String loanLastApplyId) {
        this.loanLastApplyId = loanLastApplyId == null ? null : loanLastApplyId.trim();
    }

    public String getDictLoanaType() {
        return dictLoanaType;
    }

    public void setDictLoanaType(String dictLoanaType) {
        this.dictLoanaType = dictLoanaType == null ? null : dictLoanaType.trim();
    }

    public String getLoanRaiseFlag() {
        return loanRaiseFlag;
    }

    public void setLoanRaiseFlag(String loanRaiseFlag) {
        this.loanRaiseFlag = loanRaiseFlag == null ? null : loanRaiseFlag.trim();
    }

    public BigDecimal getLoanRaiseAmount() {
        return loanRaiseAmount;
    }

    public void setLoanRaiseAmount(BigDecimal loanRaiseAmount) {
        this.loanRaiseAmount = loanRaiseAmount;
    }

    public Integer getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(Integer loanNum) {
        this.loanNum = loanNum;
    }

    public String getLoanFlag() {
        return loanFlag;
    }

    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag == null ? null : loanFlag.trim();
    }

    public String getLoanCustomerService() {
        return loanCustomerService;
    }

    public void setLoanCustomerService(String loanCustomerService) {
        this.loanCustomerService = loanCustomerService == null ? null : loanCustomerService.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOutsideFlag() {
        return outsideFlag;
    }

    public void setOutsideFlag(String outsideFlag) {
        this.outsideFlag = outsideFlag == null ? null : outsideFlag.trim();
    }

    public String getLoanStoreOrgid() {
        return loanStoreOrgid;
    }

    public void setLoanStoreOrgid(String loanStoreOrgid) {
        this.loanStoreOrgid = loanStoreOrgid == null ? null : loanStoreOrgid.trim();
    }

    public String getVisitFlag() {
        return visitFlag;
    }

    public void setVisitFlag(String visitFlag) {
        this.visitFlag = visitFlag == null ? null : visitFlag.trim();
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource == null ? null : customerSource.trim();
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId == null ? null : auditId.trim();
    }

    public String getLoanCodeOld() {
        return loanCodeOld;
    }

    public void setLoanCodeOld(String loanCodeOld) {
        this.loanCodeOld = loanCodeOld == null ? null : loanCodeOld.trim();
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval == null ? null : timeInterval.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public Date getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(Date settledDate) {
        this.settledDate = settledDate;
    }

    public Integer getFrozenApplyTimes() {
        return frozenApplyTimes;
    }

    public void setFrozenApplyTimes(Integer frozenApplyTimes) {
        this.frozenApplyTimes = frozenApplyTimes;
    }

    public Date getFrozenLastApplyTime() {
        return frozenLastApplyTime;
    }

    public void setFrozenLastApplyTime(Date frozenLastApplyTime) {
        this.frozenLastApplyTime = frozenLastApplyTime;
    }

    public String getKinnobuQuotaFlag() {
        return kinnobuQuotaFlag;
    }

    public void setKinnobuQuotaFlag(String kinnobuQuotaFlag) {
        this.kinnobuQuotaFlag = kinnobuQuotaFlag == null ? null : kinnobuQuotaFlag.trim();
    }

    public String getFrozenCode() {
        return frozenCode;
    }

    public void setFrozenCode(String frozenCode) {
        this.frozenCode = frozenCode == null ? null : frozenCode.trim();
    }

    public String getFrozenReason() {
        return frozenReason;
    }

    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason == null ? null : frozenReason.trim();
    }

    public String getAbandoningFlag() {
        return abandoningFlag;
    }

    public void setAbandoningFlag(String abandoningFlag) {
        this.abandoningFlag = abandoningFlag == null ? null : abandoningFlag.trim();
    }

    public String getTrustCash() {
        return trustCash;
    }

    public void setTrustCash(String trustCash) {
        this.trustCash = trustCash == null ? null : trustCash.trim();
    }

    public String getTrustRecharge() {
        return trustRecharge;
    }

    public void setTrustRecharge(String trustRecharge) {
        this.trustRecharge = trustRecharge == null ? null : trustRecharge.trim();
    }

    public String getDictProfType() {
        return dictProfType;
    }

    public void setDictProfType(String dictProfType) {
        this.dictProfType = dictProfType == null ? null : dictProfType.trim();
    }

    public Date getOuttoLoanTime() {
        return outtoLoanTime;
    }

    public void setOuttoLoanTime(Date outtoLoanTime) {
        this.outtoLoanTime = outtoLoanTime;
    }

    public String getApproveStep() {
        return approveStep;
    }

    public void setApproveStep(String approveStep) {
        this.approveStep = approveStep == null ? null : approveStep.trim();
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult == null ? null : approveResult.trim();
    }

    public String getUsingFlag() {
        return usingFlag;
    }

    public void setUsingFlag(String usingFlag) {
        this.usingFlag = usingFlag == null ? null : usingFlag.trim();
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId == null ? null : resultId.trim();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getConsTelesalesOrgcode() {
        return consTelesalesOrgcode;
    }

    public void setConsTelesalesOrgcode(String consTelesalesOrgcode) {
        this.consTelesalesOrgcode = consTelesalesOrgcode == null ? null : consTelesalesOrgcode.trim();
    }

    public String getDictSourceTypePcl() {
        return dictSourceTypePcl;
    }

    public void setDictSourceTypePcl(String dictSourceTypePcl) {
        this.dictSourceTypePcl = dictSourceTypePcl == null ? null : dictSourceTypePcl.trim();
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField == null ? null : orderField.trim();
    }

    public String getMainPaybackResource() {
        return mainPaybackResource;
    }

    public void setMainPaybackResource(String mainPaybackResource) {
        this.mainPaybackResource = mainPaybackResource == null ? null : mainPaybackResource.trim();
    }

    public String getMainPaybackResourceRemark() {
        return mainPaybackResourceRemark;
    }

    public void setMainPaybackResourceRemark(String mainPaybackResourceRemark) {
        this.mainPaybackResourceRemark = mainPaybackResourceRemark == null ? null : mainPaybackResourceRemark.trim();
    }

    public String getOtherIncomeResource() {
        return otherIncomeResource;
    }

    public void setOtherIncomeResource(String otherIncomeResource) {
        this.otherIncomeResource = otherIncomeResource == null ? null : otherIncomeResource.trim();
    }

    public String getOtherIncomeResourceRemark() {
        return otherIncomeResourceRemark;
    }

    public void setOtherIncomeResourceRemark(String otherIncomeResourceRemark) {
        this.otherIncomeResourceRemark = otherIncomeResourceRemark == null ? null : otherIncomeResourceRemark.trim();
    }

    public Integer getComPaybackCount() {
        return comPaybackCount;
    }

    public void setComPaybackCount(Integer comPaybackCount) {
        this.comPaybackCount = comPaybackCount;
    }

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public String getLoaninfoOldornewFlag() {
        return loaninfoOldornewFlag;
    }

    public void setLoaninfoOldornewFlag(String loaninfoOldornewFlag) {
        this.loaninfoOldornewFlag = loaninfoOldornewFlag == null ? null : loaninfoOldornewFlag.trim();
    }

    public Long getHighPaybackMonthMoney() {
        return highPaybackMonthMoney;
    }

    public void setHighPaybackMonthMoney(Long highPaybackMonthMoney) {
        this.highPaybackMonthMoney = highPaybackMonthMoney;
    }

    public String getDictLoanUseNewOther() {
        return dictLoanUseNewOther;
    }

    public void setDictLoanUseNewOther(String dictLoanUseNewOther) {
        this.dictLoanUseNewOther = dictLoanUseNewOther == null ? null : dictLoanUseNewOther.trim();
    }

    public String getDictLoanSource() {
        return dictLoanSource;
    }

    public void setDictLoanSource(String dictLoanSource) {
        this.dictLoanSource = dictLoanSource == null ? null : dictLoanSource.trim();
    }

    public String getDictLoanSourceOther() {
        return dictLoanSourceOther;
    }

    public void setDictLoanSourceOther(String dictLoanSourceOther) {
        this.dictLoanSourceOther = dictLoanSourceOther == null ? null : dictLoanSourceOther.trim();
    }

    public String getDictLoanSourceElse() {
        return dictLoanSourceElse;
    }

    public void setDictLoanSourceElse(String dictLoanSourceElse) {
        this.dictLoanSourceElse = dictLoanSourceElse == null ? null : dictLoanSourceElse.trim();
    }

    public String getDictLoanSourceElseOther() {
        return dictLoanSourceElseOther;
    }

    public void setDictLoanSourceElseOther(String dictLoanSourceElseOther) {
        this.dictLoanSourceElseOther = dictLoanSourceElseOther == null ? null : dictLoanSourceElseOther.trim();
    }

    public Long getOtherMonthIncome() {
        return otherMonthIncome;
    }

    public void setOtherMonthIncome(Long otherMonthIncome) {
        this.otherMonthIncome = otherMonthIncome;
    }

    public String getOtherCompanyPaybackCount() {
        return otherCompanyPaybackCount;
    }

    public void setOtherCompanyPaybackCount(String otherCompanyPaybackCount) {
        this.otherCompanyPaybackCount = otherCompanyPaybackCount == null ? null : otherCompanyPaybackCount.trim();
    }

    public Long getOtherCompanyPaybackTotalmoney() {
        return otherCompanyPaybackTotalmoney;
    }

    public void setOtherCompanyPaybackTotalmoney(Long otherCompanyPaybackTotalmoney) {
        this.otherCompanyPaybackTotalmoney = otherCompanyPaybackTotalmoney;
    }

    public String getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(String quotaId) {
        this.quotaId = quotaId == null ? null : quotaId.trim();
    }

    public String getNodeFlag() {
        return nodeFlag;
    }

    public void setNodeFlag(String nodeFlag) {
        this.nodeFlag = nodeFlag == null ? null : nodeFlag.trim();
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag == null ? null : sendFlag.trim();
    }

    public String getLastDealUser() {
        return lastDealUser;
    }

    public void setLastDealUser(String lastDealUser) {
        this.lastDealUser = lastDealUser == null ? null : lastDealUser.trim();
    }

    public String getTrackState() {
        return trackState;
    }

    public void setTrackState(String trackState) {
        this.trackState = trackState == null ? null : trackState.trim();
    }

    public String getOldLoanCode() {
        return oldLoanCode;
    }

    public void setOldLoanCode(String oldLoanCode) {
        this.oldLoanCode = oldLoanCode == null ? null : oldLoanCode.trim();
    }

    public String getIssplit() {
        return issplit;
    }

    public void setIssplit(String issplit) {
        this.issplit = issplit == null ? null : issplit.trim();
    }

    public Long getZcj() {
        return zcj;
    }

    public void setZcj(Long zcj) {
        this.zcj = zcj;
    }

    public Long getJinxin() {
        return jinxin;
    }

    public void setJinxin(Long jinxin) {
        this.jinxin = jinxin;
    }

    public String getBackflag() {
        return backflag;
    }

    public void setBackflag(String backflag) {
        this.backflag = backflag == null ? null : backflag.trim();
    }

    public String getOutboundFlag() {
        return outboundFlag;
    }

    public void setOutboundFlag(String outboundFlag) {
        this.outboundFlag = outboundFlag == null ? null : outboundFlag.trim();
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
}