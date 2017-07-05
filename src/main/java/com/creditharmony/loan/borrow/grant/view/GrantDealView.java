package com.creditharmony.loan.borrow.grant.view;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;

/**
 * 页面初始化，修改字段的信息
 * @Class Name GrantQueryParam
 * @author 朱静越
 * @Create In 2015年11月27日
 */
public class GrantDealView extends BaseBusinessView{
	// 借款编号
    private String loanCode;
	// 合同编号
	private String contractCode;
	// 客户名称
	private String  customerName;
	// 证件号码
	private String customerCertNum;
	// 放款金额
	private BigDecimal grantAmount; 
	// 申请金额
	private BigDecimal applyGrantAmount;
	// 借款状态
	private String dictLoanStatus;
	// 借款code
	private String dictLoanStatusCode;
	// 标识
	private String loanFlag; 
	// 标识code
	private String loanFlagCode;
	// 中间人id
	private String midId;
	// 放款人员编号
	private String lendingUserId;
	// 放款途径
	private String dictLoanWay;
	// 放款途径Code
	private String dictLoanWayCode;
	// 放款时间
	private Date lendingTime;
	// 共借人
	private String coboName;
	// 放款开户行
	private String midBankName;
	// 中间人姓名
	private String middleName;
	// 中间人放款账号
	private String bankCardNo;
	// 放款审核退回原因,数据库
	private String grantBackMes;
	// 驳回冻结原因
	private String rejectFrozenReason;
	// 大金融拒绝的原因
	private String bigFinanceRejectReason;
	// 放款审核退回原因，name 
	private String grantBackMesName;
	// 回执结果
	private String grantRecepicResult;
	// 中金放款失败，失败原因
	private String grantFailResult;
	// 审核专员
	private String checkEmpId;
	// 审核结果
	private String checkResult;
	// 审核时间
	private Date checkTime;
	// 线上放款回盘时间
	private Date grantBackDate;
	// 放款次数
	private Integer grantCount;
	// 待放款确认退回原因，放款退回原因，分配卡号，需要更新到流程中，同时更新到数据库中
	private String grantSureBackReason;
	// 待放款确认退回原因，放款退回原因，分配卡号，Code 需要更新到流程中，同时更新到数据库中
	private String grantSureBackReasonCode;
	// 未划金额
	private Double unUrgeService;
	// 催收已划金额
	private BigDecimal doneUrgeServiceMoney;
	// 放款失败金额
	private Double grantFailAmount;
	// 放款成功金额
	private BigDecimal successAmount;
	// 放款确认时的批次
	private String grantPch;
	// 批次提交时间
	private String submissionDate;
	// 放款时的放款批次
	private String grantBatchCode;
	// 是否冻结 1 冻结 0 未冻结
    private String frozenFlag;
    // 原因描述
    private String frozenReason;
	// 操作类型（0：saveData 1或者空:dispatch） 用于区分是调用saveData方法还是调用dispatch方法
    private String operateType;
    // 加急标识
    private String urgentFlag;
    // 控制上传或者有回执结果的时候进行更新放款历史记录表的信息
    private String updGrantHisFlag;
    // 排序字段
    private String orderField;
    // 委托提现回盘状态
    private String trustCashRtn;
    // 委托提现失败原因
	private String trustCashFailure;
	// 委托提现状态
	private String trustCash;
	// 委托充值状态
	private String trustRecharge;
	
	private String goldCreditStatus;
	// 资金托管待放款文件导出状态
	private String trustGrantOutputStatus;
	// 企业流水号
	private String enterpriseSerialno;
	// 提交划扣日期
    private Date submitDeductTime;
    // 委托标识 0 委托提现 1 委托充值
    private String  trustFlag;
    // 处理人id
    private String dealUser;
    // listFlag
    private String listFlag;
    // 退回标识
    private String backFlag;
    //自动放款结果
    private String autoGrantResult;
    // 是否登记失败
    private String registFlag;
    // 是否加盖失败
    private String signUpFlag;
    // 线上线下放款记录历史
    private String grantExportFlag;
    // 放款线上线下的处理
    private String dictLoanType;
    // 模式
    private String model;
    
    private LoanGrantHis loanGrantHis;
    // 标识记录历史
    private String flagStatus;
    
    // 不加急最大值
    private int urgentNo;
    // 加急最大值
    private int urgentYes;
    // 合同文件id，借款协议id
    private String docId;
    // 新老标识
    private String loanInfoOldOrNewFlag;
    // 出借人
    private String lender;
 
	public String getAutoGrantResult() {
		return autoGrantResult;
	}
	public void setAutoGrantResult(String autoGrantResult) {
		this.autoGrantResult = autoGrantResult;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getMidId() {
		return midId;
	}
	public void setMidId(String midId) {
		this.midId = midId;
	}
	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}
	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}
	public String getLendingUserId() {
		return lendingUserId;
	}
	public void setLendingUserId(String lendingUserId) {
		this.lendingUserId = lendingUserId;
	}
	public String getGrantFailResult() {
		return grantFailResult;
	}
	public void setGrantFailResult(String grantFailResult) {
		this.grantFailResult = grantFailResult;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getLoanFlagCode() {
		return loanFlagCode;
	}
	public void setLoanFlagCode(String loanFlagCode) {
		this.loanFlagCode = loanFlagCode;
	}
	public String getDictLoanWayCode() {
		return dictLoanWayCode;
	}
	public void setDictLoanWayCode(String dictLoanWayCode) {
		this.dictLoanWayCode = dictLoanWayCode;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public BigDecimal getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getDictLoanWay() {
		return dictLoanWay;
	}
	public void setDictLoanWay(String dictLoanWay) {
		this.dictLoanWay = dictLoanWay;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getMidBankName() {
		return midBankName;
	}
	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}
	public String getGrantBackMes() {
		return grantBackMes;
	}
	public void setGrantBackMes(String grantBackMes) {
		this.grantBackMes = grantBackMes;
	}
	public String getCheckEmpId() {
		return checkEmpId;
	}
	public void setCheckEmpId(String checkEmpId) {
		this.checkEmpId = checkEmpId;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getGrantSureBackReason() {
		return grantSureBackReason;
	}
	public void setGrantSureBackReason(String grantSureBackReason) {
		this.grantSureBackReason = grantSureBackReason;
	}
	public String getGrantSureBackReasonCode() {
        return grantSureBackReasonCode;
    }
    public void setGrantSureBackReasonCode(String grantSureBackReasonCode) {
        this.grantSureBackReasonCode = grantSureBackReasonCode;
    }
    public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getGrantCount() {
		return grantCount;
	}
	public void setGrantCount(Integer grantCount) {
		this.grantCount = grantCount;
	}
	public String getDictLoanStatusCode() {
		return dictLoanStatusCode;
	}
	public void setDictLoanStatusCode(String dictLoanStatusCode) {
		this.dictLoanStatusCode = dictLoanStatusCode;
	}
	public Double getUnUrgeService() {
		return unUrgeService;
	}
	public void setUnUrgeService(Double unUrgeService) {
		this.unUrgeService = unUrgeService;
	}
	public Double getGrantFailAmount() {
		return grantFailAmount;
	}
	public void setGrantFailAmount(Double grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}
	public String getGrantPch() {
		return grantPch;
	}
	public void setGrantPch(String grantPch) {
		this.grantPch = grantPch;
	}
    public String getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }
	public BigDecimal getDoneUrgeServiceMoney() {
		return doneUrgeServiceMoney;
	}
	public void setDoneUrgeServiceMoney(BigDecimal doneUrgeServiceMoney) {
		this.doneUrgeServiceMoney = doneUrgeServiceMoney;
	}
	public String getFrozenFlag() {
        return frozenFlag;
    }
    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }
    public String getFrozenReason() {
        return frozenReason;
    }
    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason;
    }
    public String getBigFinanceRejectReason() {
		return bigFinanceRejectReason;
	}
	public void setBigFinanceRejectReason(String bigFinanceRejectReason) {
		this.bigFinanceRejectReason = bigFinanceRejectReason;
	}
	public String getOperateType() {
        return operateType;
    }
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    public String getUrgentFlag() {
        return urgentFlag;
    }
    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag;
    }
	/**
     * @return the orderField
     */
    public String getOrderField() {
        return orderField;
    }
    /**
     * @param orderField the String orderField to set
     */
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
    public String getGrantBatchCode() {
		return grantBatchCode;
	}
	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}
	public String getTrustCashRtn() {
		return trustCashRtn;
	}
	public void setTrustCashRtn(String trustCashRtn) {
		this.trustCashRtn = trustCashRtn;
	}
	public String getTrustCashFailure() {
		return trustCashFailure;
	}
	public void setTrustCashFailure(String trustCashFailure) {
		this.trustCashFailure = trustCashFailure;
	}
	public String getTrustCash() {
		return trustCash;
	}
	public void setTrustCash(String trustCash) {
		this.trustCash = trustCash;
	}
	/**
     * @return the trustRecharge
     */
    public String getTrustRecharge() {
        return trustRecharge;
    }
    /**
     * @param trustRecharge the String trustRecharge to set
     */
    public void setTrustRecharge(String trustRecharge) {
        this.trustRecharge = trustRecharge;
    }
    public String getGoldCreditStatus() {
		return goldCreditStatus;
	}
	public void setGoldCreditStatus(String goldCreditStatus) {
		this.goldCreditStatus = goldCreditStatus;
	}
	public String getTrustGrantOutputStatus() {
		return trustGrantOutputStatus;
	}
	public void setTrustGrantOutputStatus(String trustGrantOutputStatus) {
		this.trustGrantOutputStatus = trustGrantOutputStatus;
	}
	public String getUpdGrantHisFlag() {
		return updGrantHisFlag;
	}
	public void setUpdGrantHisFlag(String updGrantHisFlag) {
		this.updGrantHisFlag = updGrantHisFlag;
	}
	public BigDecimal getApplyGrantAmount() {
		return applyGrantAmount;
	}
	public void setApplyGrantAmount(BigDecimal applyGrantAmount) {
		this.applyGrantAmount = applyGrantAmount;
	}
	public BigDecimal getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}
    public Date getSubmitDeductTime() {
		return submitDeductTime;
	}
	public void setSubmitDeductTime(Date submitDeductTime) {
		this.submitDeductTime = submitDeductTime;
	}
	/**
     * @return the trustFlag
     */
    public String getTrustFlag() {
        return trustFlag;
    }
    /**
     * @param trustFlag the String trustFlag to set
     */
    public void setTrustFlag(String trustFlag) {
        this.trustFlag = trustFlag;
    }
    /**
     * @return the enterpriseSerialno
     */
    public String getEnterpriseSerialno() {
        return enterpriseSerialno;
    }
    /**
     * @param enterpriseSerialno the String enterpriseSerialno to set
     */
    public void setEnterpriseSerialno(String enterpriseSerialno) {
        this.enterpriseSerialno = enterpriseSerialno;
    }
	public String getDealUser() {
		return dealUser;
	}
	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}
	public String getListFlag() {
		return listFlag;
	}
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}
	public Date getGrantBackDate() {
		return grantBackDate;
	}
	public void setGrantBackDate(Date grantBackDate) {
		this.grantBackDate = grantBackDate;
	}
	public String getBackFlag() {
		return backFlag;
	}
	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}
	public String getGrantBackMesName() {
		return grantBackMesName;
	}
	public void setGrantBackMesName(String grantBackMesName) {
		this.grantBackMesName = grantBackMesName;
	}
    public String getRegistFlag() {
        return registFlag;
    }
    public void setRegistFlag(String registFlag) {
        this.registFlag = registFlag;
    }
    public String getSignUpFlag() {
        return signUpFlag;
    }
    public void setSignUpFlag(String signUpFlag) {
        this.signUpFlag = signUpFlag;
    }
	public String getGrantExportFlag() {
		return grantExportFlag;
	}
	public void setGrantExportFlag(String grantExportFlag) {
		this.grantExportFlag = grantExportFlag;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDictLoanType() {
		return dictLoanType;
	}
	public void setDictLoanType(String dictLoanType) {
		this.dictLoanType = dictLoanType;
	}
	public LoanGrantHis getLoanGrantHis() {
		return loanGrantHis;
	}
	public void setLoanGrantHis(LoanGrantHis loanGrantHis) {
		this.loanGrantHis = loanGrantHis;
	}
	public String getFlagStatus() {
		return flagStatus;
	}
	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}
    /**
     * @return the rejectFrozenReason
     */
    public String getRejectFrozenReason() {
        return rejectFrozenReason;
    }
    /**
     * @param rejectFrozenReason the String rejectFrozenReason to set
     */
    public void setRejectFrozenReason(String rejectFrozenReason) {
        this.rejectFrozenReason = rejectFrozenReason;
    }
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getUrgentNo() {
		return urgentNo;
	}
	public void setUrgentNo(int urgentNo) {
		this.urgentNo = urgentNo;
	}
	public int getUrgentYes() {
		return urgentYes;
	}
	public void setUrgentYes(int urgentYes) {
		this.urgentYes = urgentYes;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getLender() {
		return lender;
	}
	public void setLender(String lender) {
		this.lender = lender;
	}
}

