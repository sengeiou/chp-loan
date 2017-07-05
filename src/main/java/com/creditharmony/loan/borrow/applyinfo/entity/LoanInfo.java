package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.payback.entity.JkProducts;
/**
 * 借款信息主信息
 * @Class Name LoanInfo
 * @author zhangping
 * @Create In 2015年11月29日
 */
@SuppressWarnings("serial")
public class LoanInfo extends DataEntity<LoanInfo> {

	// 流程ID
	private String applyId;   
	// 借款编码   
	private String loanCode;
	// 客户姓名
    private String loanCustomerName;  
    // 申请日期   
    private Date loanApplyTime;
    // 借款期限
    private Integer loanMonths;
    // 借款金额
    private BigDecimal loanApplyAmount;
    // 实际用途
    private String realyUse;
    // 组织机构ID  
    private String loanStoreOrgId;
    //组织机构类型
    private String loanStoreOrgType;
    // 组织机构NAME  
    private String loanStoreOrgName;
    // 是否有共同还款人
    private String loanCommonRepaymentFlag;
    // 还款方式
    private String dictRepayMethod;
    // 借款状态
    private String dictLoanStatus;
    // 借款状态名称
    private String dictLoanStatusLabel;
    // 借款类型(产品表的业务类型)
    private String dictClassType;
    // 产品类型
    private String productType;
    // 是否加急
    private String loanUrgentFlag;
    // 是否加急名称
    private String loanUrgentFlagLabel;
    // 债权类型
    private String loanDeptType;
    // 借款用途
    private String dictLoanUse;
    // 借款用途名称
    private String dictLoanUserLabel;
    // 是否标红置顶
    private String loanApplyTop;
    // 退回时状态标识
    private String loanBackTopStatus;
    // 关联ID(客户咨询表)
    private String rid;
    // 循环借标识:字典201
    private String dictIsCycle;
    // 是否追加
    private String dictIsAdditional;
    // 来源版本 1.0 2.0 3.0
    private String dictSourceType;
    // 进件时间
    private Date customerIntoTime;
    // 批复金额
    private BigDecimal loanAuditAmount;
    // 批复时间 
    private Date auditTime;
    // 批复产品     
    private String auditProduct;
    // 复议批复时间
    private Date loanSecondFinishtime;
    // 追加借款ID
    private BigDecimal loanAdditionalApplyid;
    // 决策返回码
    private String  loanDecisionMakingCode;
    // 客户经理编号
    private String loanManagerCode;
    // 客户经理名称
    private String loanManagerName; 
    // 团队经理编号
    private String loanTeamManagerCode;
    // 团队经理
    private String loanTeamManagerName;
    // 团队组织机构ID
    private String loanTeamOrgId;
    // 外访人员
    private String loanSurveyEmpId;
    // 上一笔结清的申请id
    private BigDecimal loanLastApplyId;
    // 借款账户类型(追加贷款/结清再贷)
    private String dictLoanaType;
    // 是否上调
    private String loanRaiseFlag;
    // 上调金额
    private BigDecimal loanRaiseAmount;
    // 结清再贷次数
    private Integer loanNum;
    // 标示
    private String loanFlag;
    // 标示
    private String loanFlagLabel;
    // 客服编号
    private String loanCustomerService;
    // 客服姓名
    private String loanCustomerServiceName;
    // 备注
    private String remark;
    // 产品表
    private JkProducts jkProducts;
    // 批复金额 浮点类型
    private Float loanApplyAmountf;    
    // 门店所在省编号  
    private String storeProviceCode;    
    // 门店所在省名称  
    private String storeProviceName;    
    // 门店所在市名称  
    private String storeCityName;       
    // 门店所在市编号 
    private String storeCityCode; 
    // 门店编号
    private String storeCode;
    // 冻结原因
    private String frozenCode;
    // 冻结原因name
    private String frozenName;
    // 原因描述
    private String frozenReason;
    // 申请冻结标识
    private String frozenFlag;
    // 申请冻结次数
    private Integer frozenApplyTimes;
    // 申请冻结时间
    private Date frozenLastApplyTime;
    // 结清时间
    private Date settledDate;
    // 委托充值
    private String trustRecharge;
    // 访问放弃标识
    private String visitFlag;
    // 客户职业类型
    private String dictProfType;
    // 模式
    private String model;
    // 模式枚举
    private String modelLabel;
    // 出汇金时间
    private Date outtoLoanTime;
    //合同编号
    private String contractCode;
    //是否借么APP产品
    private String isBorrow;
	//在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String loanInfoOldOrNewFlag;
    //主要借款用途选择其他时的备注信息
    private String dictLoanUseNewOther;
    //最高可承受月还
    private BigDecimal highPaybackMonthMoney;
    //主要还款来源
    private String dictLoanSource;
    //主要还款来源选择其他时的备注信息
    private String dictLoanSourceOther;
    //其他收入来源(接收页面数据)
    private String[] dictLoanSourceElseStr;
    //其他收入来源
    private String dictLoanSourceElse;
    //其他收入来源选择其他时的备注信息
    private String dictLoanSourceElseOther;
    //其它月收入
    private BigDecimal otherMonthIncome;
    //其他公司在还借款总笔数
    private String otherCompanyPaybackCount;
    //其他公司在还月还款总额
    private BigDecimal otherCompanyPaybackTotalmoney;
    //节点标识
    private String nodeFlag;
    //推送标识
    private String sendFlag;
    //借款意愿客户来源
    private String loanCustomerSource;
    //电销标识(0:否；1:是)
    private String consTelesalesFlag;
    //建议拒绝标识
    private String refuseFlag;
    //建议放弃标识
    private String outFlag;

    //旧loan_code
    private String oldLoanCode;
    //联合放款标识
    private String issplit;
    //资产家占比
    private BigDecimal zcj;
    //金信占比
    private BigDecimal jinxin;
    //费率审核标识
    private String flFlag;
    //大金融拒绝标记
    private String zcjRejectFlag;
    //超时时间
    private Date timeOut;
    
    // 金账户开户状态
    private String kingStatus;
    //金账户开户失败原因
    private String kingOpenRespReason;
    // 金账户协议库返回
    private String kingProctolRespReason;
    // 紧急诉讼状态
    private String emergencyStatus;
    // 紧急诉讼状态标签
    private String emergencyStatusLabel;
    // 是否紧急诉讼
    private String yesOrNoEmergency;
    //排序
    private String orderField;
// 黑灰名单标记
    private String blackType;    
    //交割状态
    private String deliveryResult;
    //交割时间
    private Date deliveryTime;
    //征信费
    private BigDecimal feeCredit;
 
	public String getDeliveryResult() {
		return deliveryResult;
	}

	public void setDeliveryResult(String deliveryResult) {
		this.deliveryResult = deliveryResult;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getRefuseFlag() {
		return refuseFlag;
	}

	public void setRefuseFlag(String refuseFlag) {
		this.refuseFlag = refuseFlag;
	}

	public String getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}

    public String getZcjRejectFlag() {
		return zcjRejectFlag;
	}

	public void setZcjRejectFlag(String zcjRejectFlag) {
		this.zcjRejectFlag = zcjRejectFlag;
	}

	public String getNodeFlag() {
		return nodeFlag;
	}

	public void setNodeFlag(String nodeFlag) {
		this.nodeFlag = nodeFlag;
	}

    
	public BigDecimal getOtherMonthIncome() {
		return otherMonthIncome;
	}

	public void setOtherMonthIncome(BigDecimal otherMonthIncome) {
		this.otherMonthIncome = otherMonthIncome;
	}

	public String getOtherCompanyPaybackCount() {
		return otherCompanyPaybackCount;
	}

	public void setOtherCompanyPaybackCount(String otherCompanyPaybackCount) {
		this.otherCompanyPaybackCount = otherCompanyPaybackCount;
	}

	public BigDecimal getOtherCompanyPaybackTotalmoney() {
		return otherCompanyPaybackTotalmoney;
	}

	public void setOtherCompanyPaybackTotalmoney(
			BigDecimal otherCompanyPaybackTotalmoney) {
		this.otherCompanyPaybackTotalmoney = otherCompanyPaybackTotalmoney;
	}

	public String getDictLoanSource() {
		return dictLoanSource;
	}

	public void setDictLoanSource(String dictLoanSource) {
		this.dictLoanSource = dictLoanSource;
	}

	public String getDictLoanSourceOther() {
		return dictLoanSourceOther;
	}

	public void setDictLoanSourceOther(String dictLoanSourceOther) {
		this.dictLoanSourceOther = dictLoanSourceOther;
	}

	public String[] getDictLoanSourceElseStr() {
		return dictLoanSourceElseStr;
	}

	public void setDictLoanSourceElseStr(String[] dictLoanSourceElseStr) {
		this.dictLoanSourceElseStr = dictLoanSourceElseStr;
	}

	public String getDictLoanSourceElse() {
		return dictLoanSourceElse;
	}

	public void setDictLoanSourceElse(String dictLoanSourceElse) {
		this.dictLoanSourceElse = dictLoanSourceElse;
	}

	public String getDictLoanSourceElseOther() {
		return dictLoanSourceElseOther;
	}

	public void setDictLoanSourceElseOther(String dictLoanSourceElseOther) {
		this.dictLoanSourceElseOther = dictLoanSourceElseOther;
	}

	public String getDictLoanUseNewOther() {
		return dictLoanUseNewOther;
	}

	public void setDictLoanUseNewOther(String dictLoanUseNewOther) {
		this.dictLoanUseNewOther = dictLoanUseNewOther;
	}

	public BigDecimal getHighPaybackMonthMoney() {
		return highPaybackMonthMoney;
	}

	public void setHighPaybackMonthMoney(BigDecimal highPaybackMonthMoney) {
		this.highPaybackMonthMoney = highPaybackMonthMoney;
	}

	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}

	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}


    
    public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	//电销组织机构编码
    private String consTelesalesOrgcode;
    
    
    public String getConsTelesalesOrgcode() {
		return consTelesalesOrgcode;
	}

	public void setConsTelesalesOrgcode(String consTelesalesOrgcode) {
		this.consTelesalesOrgcode = consTelesalesOrgcode;
	}

	public Date getOuttoLoanTime() {
		return outtoLoanTime;
	}

	public void setOuttoLoanTime(Date outtoLoanTime) {
		this.outtoLoanTime = outtoLoanTime;
	}

	public String getLoanUrgentFlagLabel() {
		return loanUrgentFlagLabel;
	}

	public void setLoanUrgentFlagLabel(String loanUrgentFlagLabel) {
		this.loanUrgentFlagLabel = loanUrgentFlagLabel;
	}

	public String getDictLoanUserLabel() {
		return dictLoanUserLabel;
	}

	public void setDictLoanUserLabel(String dictLoanUserLabel) {
		this.dictLoanUserLabel = dictLoanUserLabel;
	}

	public Date getSettledDate() {
		return settledDate;
	}
   
	public void setSettledDate(Date settledDate) {
		this.settledDate = settledDate;
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

    public String getStoreProviceName() {
        return storeProviceName;
    }

    public void setStoreProviceName(String storeProviceName) {
        this.storeProviceName = storeProviceName;
    }

    public String getStoreCityName() {
        return storeCityName;
    }

    public void setStoreCityName(String storeCityName) {
        this.storeCityName = storeCityName;
    }
 
    public String getStoreProviceCode() {
        return storeProviceCode;
    }

    public void setStoreProviceCode(String storeProviceCode) {
        this.storeProviceCode = storeProviceCode;
    }

    public String getStoreCityCode() {
        return storeCityCode;
    }

    public void setStoreCityCode(String storeCityCode) {
        this.storeCityCode = storeCityCode;
    }

    public JkProducts getJkProducts() {
		return jkProducts;
	}

	public void setJkProducts(JkProducts jkProducts) {
		this.jkProducts = jkProducts;
	}

	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

	public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
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
        this.realyUse = realyUse;
    }

    public String getLoanStoreOrgId() {
        return loanStoreOrgId;
    }

    public void setLoanStoreOrgId(String loanStoreOrgId) {
        this.loanStoreOrgId = loanStoreOrgId;
    }

    public String getLoanCommonRepaymentFlag() {
        return loanCommonRepaymentFlag;
    }

    public void setLoanCommonRepaymentFlag(String loanCommonRepaymentFlag) {
        this.loanCommonRepaymentFlag=loanCommonRepaymentFlag;
    }

    public String getDictRepayMethod() {
        return dictRepayMethod;
    }

    public void setDictRepayMethod(String dictRepayMethod) {
        this.dictRepayMethod = dictRepayMethod == null ? null : dictRepayMethod.trim();
    }

    public String getDictClassType() {
        return dictClassType;
    }

    public void setDictClassType(String dictClassType) {
        this.dictClassType = dictClassType;
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
        this.loanUrgentFlag = loanUrgentFlag;
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
        this.loanBackTopStatus = loanBackTopStatus;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getDictIsCycle() {
        return dictIsCycle;
    }

    public void setDictIsCycle(String dictIsCycle) {
        this.dictIsCycle = dictIsCycle;
    }

    public String getDictIsAdditional() {
        return dictIsAdditional;
    }

    public void setDictIsAdditional(String dictIsAdditional) {
        this.dictIsAdditional = dictIsAdditional;
    }

    public String getDictSourceType() {
		return dictSourceType;
	}

	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
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

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditProduct() {
        return auditProduct;
    }

    public void setAuditProduct(String auditProduct) {
        this.auditProduct = auditProduct;
    }

    public Date getLoanSecondFinishtime() {
        return loanSecondFinishtime;
    }

    public void setLoanSecondFinishtime(Date loanSecondFinishtime) {
        this.loanSecondFinishtime = loanSecondFinishtime;
    }

    public BigDecimal getLoanAdditionalApplyid() {
        return loanAdditionalApplyid;
    }

    public void setLoanAdditionalApplyid(BigDecimal loanAdditionalApplyid) {
        this.loanAdditionalApplyid = loanAdditionalApplyid;
    }

	public String getLoanDecisionMakingCode() {
		return loanDecisionMakingCode;
	}

	public void setLoanDecisionMakingCode(String loanDecisionMakingCode) {
		this.loanDecisionMakingCode = loanDecisionMakingCode;
	}

	public String getLoanManagerCode() {
		return loanManagerCode;
	}

	public void setLoanManagerCode(String loanManagerCode) {
		this.loanManagerCode = loanManagerCode;
	}

	public String getLoanTeamManagerCode() {
		return loanTeamManagerCode;
	}

	public void setLoanTeamManagerCode(String loanTeamManagerCode) {
		this.loanTeamManagerCode = loanTeamManagerCode;
	}

	public String getLoanTeamOrgId() {
		return loanTeamOrgId;
	}

	public void setLoanTeamOrgId(String loanTeamOrgId) {
		this.loanTeamOrgId = loanTeamOrgId;
	}

	public String getLoanSurveyEmpId() {
        return loanSurveyEmpId;
    }

    public void setLoanSurveyEmpId(String loanSurveyEmpId) {
        this.loanSurveyEmpId = loanSurveyEmpId == null ? null : loanSurveyEmpId.trim();
    }

    public BigDecimal getLoanLastApplyId() {
        return loanLastApplyId;
    }

    public void setLoanLastApplyId(BigDecimal loanLastApplyId) {
        this.loanLastApplyId = loanLastApplyId;
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
        this.loanRaiseFlag = loanRaiseFlag;
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
		this.loanFlag = loanFlag;
	}

	public String getLoanCustomerService() {
        return loanCustomerService;
    }

    public void setLoanCustomerService(String loanCustomerService) {
        this.loanCustomerService = loanCustomerService == null ? null : loanCustomerService.trim();
    }

    public String getLoanCustomerServiceName() {
        return loanCustomerServiceName;
    }

    public void setLoanCustomerServiceName(String loanCustomerServiceName) {
        this.loanCustomerServiceName = loanCustomerServiceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    
    public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

    public Float getLoanApplyAmountf() {
        return loanApplyAmountf;
    }

    public void setLoanApplyAmountf(Float loanApplyAmountf) {
        this.loanApplyAmountf = loanApplyAmountf;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

	public String getFrozenCode() {
        return frozenCode;
    }

    public void setFrozenCode(String frozenCode) {
        this.frozenCode = frozenCode;
    }

    public String getFrozenName() {
        return frozenName;
    }

    public void setFrozenName(String frozenName) {
        this.frozenName = frozenName;
    }

    public String getFrozenReason() {
        return frozenReason;
    }

    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason;
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

    public String getLoanStoreOrgName() {
		return loanStoreOrgName;
	}

	public void setLoanStoreOrgName(String loanStoreOrgName) {
		this.loanStoreOrgName = loanStoreOrgName;
	}

	public String getTrustRecharge() {
		return trustRecharge;
	}

	public void setTrustRecharge(String trustRecharge) {
		this.trustRecharge = trustRecharge;
	}

	public String getVisitFlag() {
		return visitFlag;
	}

	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}

    /**
     * @return the dictProfType
     */
    public String getDictProfType() {
        return dictProfType;
    }

    /**
     * @param dictProfType the String dictProfType to set
     */
    public void setDictProfType(String dictProfType) {
        this.dictProfType = dictProfType;
    }

	/**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the String model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public String getLoanFlagLabel() {
		return loanFlagLabel;
	}

	public void setLoanFlagLabel(String loanFlagLabel) {
		this.loanFlagLabel = loanFlagLabel;
	}

	public String getModelLabel() {
		return modelLabel;
	}

	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}

	public String getLoanStoreOrgType() {
		return loanStoreOrgType;
	}

	public void setLoanStoreOrgType(String loanStoreOrgType) {
		this.loanStoreOrgType = loanStoreOrgType;
	}
	

	public String getIsBorrow() {
		return isBorrow;
	}

	public void setIsBorrow(String isBorrow) {
		this.isBorrow = isBorrow;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getLoanCustomerSource() {
		return loanCustomerSource;
	}

	public void setLoanCustomerSource(String loanCustomerSource) {
		this.loanCustomerSource = loanCustomerSource;
	}

	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}

	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}

	public String getOldLoanCode() {
		return oldLoanCode;
	}

	public void setOldLoanCode(String oldLoanCode) {
		this.oldLoanCode = oldLoanCode;
	}

	public String getIssplit() {
		return issplit;
	}

	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}

	

	public BigDecimal getZcj() {
		return zcj;
	}

	public void setZcj(BigDecimal zcj) {
		this.zcj = zcj;
	}

	public BigDecimal getJinxin() {
		return jinxin;
	}

	public void setJinxin(BigDecimal jinxin) {
		this.jinxin = jinxin;
	}

	public String getFlFlag() {
		return flFlag;
	}

	public void setFlFlag(String flFlag) {
		this.flFlag = flFlag;
	}

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}
	
	public String getFrozenFlag() {
		return frozenFlag;
	}

	public void setFrozenFlag(String frozenFlag) {
		this.frozenFlag = frozenFlag;
	}

	public String getKingStatus() {
		return kingStatus;
	}

	public void setKingStatus(String kingStatus) {
		this.kingStatus = kingStatus;
	}

	public String getKingOpenRespReason() {
		return kingOpenRespReason;
	}

	public void setKingOpenRespReason(String kingOpenRespReason) {
		this.kingOpenRespReason = kingOpenRespReason;
	}

	public String getKingProctolRespReason() {
		return kingProctolRespReason;
	}

	public void setKingProctolRespReason(String kingProctolRespReason) {
		this.kingProctolRespReason = kingProctolRespReason;
	}

	public String getEmergencyStatus() {
		return emergencyStatus;
	}

	public void setEmergencyStatus(String emergencyStatus) {
		this.emergencyStatus = emergencyStatus;
	}

	public String getYesOrNoEmergency() {
		return yesOrNoEmergency;
	}

	public void setYesOrNoEmergency(String yesOrNoEmergency) {
		this.yesOrNoEmergency = yesOrNoEmergency;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getBlackType() {
		return blackType;
	}

	public void setBlackType(String blackType) {
		this.blackType = blackType;
	}

	public String getEmergencyStatusLabel() {
		return emergencyStatusLabel;
	}

	public void setEmergencyStatusLabel(String emergencyStatusLabel) {
		this.emergencyStatusLabel = emergencyStatusLabel;
	}

	public BigDecimal getFeeCredit() {
		return feeCredit;
	}

	public void setFeeCredit(BigDecimal feeCredit) {
		this.feeCredit = feeCredit;
	}
	
	
}