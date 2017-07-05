package com.creditharmony.loan.borrow.grant.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name LoanGrant
 * @author 朱静越
 * @Create In 2015年11月28日
 * 借款记录表实体类
 */
public class LoanGrant  extends DataEntity<LoanGrant>  {
	
  
	private static final long serialVersionUID = -5430079954844331150L;

	private String contractCode;

    private String loanCode;

    private String midId;

    private String dictLoanType;

    private String dictLoanWay;

    private BigDecimal grantAmount;
    // 放款失败金额
    private BigDecimal grantFailAmount;
    //放款时间
    private Date lendingTime;
    //放款人员编号
    private String lendingUserId;

    private String grantRecepicResult;

    private String grantFailResult;

    private String grantBackMes;

    private String checkEmpId;
    //审核结果
    private String checkResult;
    // 批次号
    private String grantPch;
    // 放款时的放款批次号
    private String grantBatch;
    // 批次提交时间
    private Date submissionsDate;
    // 企业流水号
    private String enterpriseSerialno;
    // 放款次数
    private Integer grantCount;
    // 提交划扣日期
    private Date submitDeductTime;
    // 回盘时间
    private Date grantBackDate;
    // 财富债权发送标识
 	private String cfSendFlag;
 	// 金账户文件导出状态
 	private String trustGrantOutputStatus;
 	//委托提现回盘状态
    private String trustCashRtn;
    //委托提现失败原因
    private String trustCashFailure;

    private Date checkTime;

    private String createBy;

    private Date createTime;

    private String modifyId;

    private Date modifyTime;
    //金信处理状态
    private String goldCreditStatus;
    // 大金融拒绝借款状态区分标识
    private String zcjRejectFlag;
    // 简易借首次和尾次放款的字段区别
    private String grantFlag;
    
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
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

	public String getDictLoanType() {
		return dictLoanType;
	}

	public void setDictLoanType(String dictLoanType) {
		this.dictLoanType = dictLoanType;
	}

	public String getDictLoanWay() {
		return dictLoanWay;
	}

	public void setDictLoanWay(String dictLoanWay) {
		this.dictLoanWay = dictLoanWay;
	}
	

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	
	public BigDecimal getGrantFailAmount() {
		return grantFailAmount;
	}

	public void setGrantFailAmount(BigDecimal grantFailAmount) {
		this.grantFailAmount = grantFailAmount;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getLendingUserId() {
		return lendingUserId;
	}

	public void setLendingUserId(String lendingUserId) {
		this.lendingUserId = lendingUserId;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getGrantPch() {
		return grantPch;
	}

	public void setGrantPch(String grantPch) {
		this.grantPch = grantPch;
	}

	public Date getSubmissionsDate() {
        return submissionsDate;
    }

    public void setSubmissionsDate(Date submissionsDate) {
        this.submissionsDate = submissionsDate;
    }

    public String getEnterpriseSerialno() {
		return enterpriseSerialno;
	}

	public void setEnterpriseSerialno(String enterpriseSerialno) {
		this.enterpriseSerialno = enterpriseSerialno;
	}

	public Integer getGrantCount() {
		return grantCount;
	}

	public void setGrantCount(Integer grantCount) {
		this.grantCount = grantCount;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getGrantBatch() {
		return grantBatch;
	}

	public void setGrantBatch(String grantBatch) {
		this.grantBatch = grantBatch;
	}

	public Date getSubmitDeductTime() {
		return submitDeductTime;
	}

	public void setSubmitDeductTime(Date submitDeductTime) {
		this.submitDeductTime = submitDeductTime;
	}

	public Date getGrantBackDate() {
		return grantBackDate;
	}

	public void setGrantBackDate(Date grantBackDate) {
		this.grantBackDate = grantBackDate;
	}

	public String getCfSendFlag() {
		return cfSendFlag;
	}

	public void setCfSendFlag(String cfSendFlag) {
		this.cfSendFlag = cfSendFlag;
	}

	public String getTrustGrantOutputStatus() {
		return trustGrantOutputStatus;
	}

	public void setTrustGrantOutputStatus(String trustGrantOutputStatus) {
		this.trustGrantOutputStatus = trustGrantOutputStatus;
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

	public String getGoldCreditStatus() {
		return goldCreditStatus;
	}

	public void setGoldCreditStatus(String goldCreditStatus) {
		this.goldCreditStatus = goldCreditStatus;
	}

	public String getZcjRejectFlag() {
		return zcjRejectFlag;
	}

	public void setZcjRejectFlag(String zcjRejectFlag) {
		this.zcjRejectFlag = zcjRejectFlag;
	}

	public String getGrantFlag() {
		return grantFlag;
	}

	public void setGrantFlag(String grantFlag) {
		this.grantFlag = grantFlag;
	}
}