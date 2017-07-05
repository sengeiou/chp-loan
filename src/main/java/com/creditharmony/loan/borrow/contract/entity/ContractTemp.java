package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 
·* 2017年2月21日
·* by Huowenlong
 */
public class ContractTemp extends DataEntity<ContractTemp> {
    private String id;

    private String applyId;

    private String contractCode;

    private String loanCode;

    private Date contractDueDay;
    
    // 月还款总额
    private BigDecimal monthPayTotalAmount;

    private Date contractFactDay;

    private Date contractReplayDay;

    private Date contractEndDay;

    private String classType;

    private String productType;

    private BigDecimal auditAmount;

    private BigDecimal contractMonths;

    private String midId;

    private String dictRepayMethod;

    private BigDecimal contractAmount;

    private BigDecimal contractExpectAmount;

    private BigDecimal contractMonthRepayAmount;

    private String dictCheckStatus;

    private String contractBackResult;

    private String contractVersion;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String docId;

    private Integer auditCount;

    private String backFlag;

    private String isobsolete;

    private String coboName;

    private String coboCertNum;

    private String loanName;

    private String loanCertNum;

    private String channelFlag;

    private BigDecimal contractMonthRepayTotal;

    private String companyName;

    private String legalMan;

    private String maddress;

    private String model;

    private String isRegister;

    private String paperLessFlag;

    private String settleCause;

    private String settleCauseElse;

    private String protocolId;

    private String dictSourceType;

    private String dictSourceTypePcl;

    private String creditId;

    private String contractHistoryVersion;

    private String revisitStatus;

    private String revisitReason;

    private Date pushTime;

    private Integer pushNumber;

    private String auditingBy;

    private Date auditingTime;

    private String lender;

    private String newFlag;
    
    private BigDecimal feePaymentAmount;
    
    private String isreceive;
    
    private String feeAllRaio;
    
    private String signUpFlag;

    public String getFeeAllRaio() {
		return feeAllRaio;
	}

	public void setFeeAllRaio(String feeAllRaio) {
		this.feeAllRaio = feeAllRaio;
	}


	public BigDecimal getFeePaymentAmount() {
		return feePaymentAmount;
	}

	public void setFeePaymentAmount(BigDecimal feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}

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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public Date getContractDueDay() {
        return contractDueDay;
    }

    public void setContractDueDay(Date contractDueDay) {
        this.contractDueDay = contractDueDay;
    }

    public Date getContractFactDay() {
        return contractFactDay;
    }

    public void setContractFactDay(Date contractFactDay) {
        this.contractFactDay = contractFactDay;
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

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

   

    public BigDecimal getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(BigDecimal contractMonths) {
		this.contractMonths = contractMonths;
	}

	public String getMidId() {
        return midId;
    }

    public void setMidId(String midId) {
        this.midId = midId == null ? null : midId.trim();
    }

    public String getDictRepayMethod() {
        return dictRepayMethod;
    }

    public void setDictRepayMethod(String dictRepayMethod) {
        this.dictRepayMethod = dictRepayMethod == null ? null : dictRepayMethod.trim();
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getContractExpectAmount() {
        return contractExpectAmount;
    }

    public void setContractExpectAmount(BigDecimal contractExpectAmount) {
        this.contractExpectAmount = contractExpectAmount;
    }

    public BigDecimal getContractMonthRepayAmount() {
        return contractMonthRepayAmount;
    }

    public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
        this.contractMonthRepayAmount = contractMonthRepayAmount;
    }

    public String getDictCheckStatus() {
        return dictCheckStatus;
    }

    public void setDictCheckStatus(String dictCheckStatus) {
        this.dictCheckStatus = dictCheckStatus == null ? null : dictCheckStatus.trim();
    }

    public String getContractBackResult() {
        return contractBackResult;
    }

    public void setContractBackResult(String contractBackResult) {
        this.contractBackResult = contractBackResult == null ? null : contractBackResult.trim();
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion == null ? null : contractVersion.trim();
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId == null ? null : docId.trim();
    }

    public Integer getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(Integer auditCount) {
        this.auditCount = auditCount;
    }

    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag == null ? null : backFlag.trim();
    }

    public String getIsobsolete() {
        return isobsolete;
    }

    public void setIsobsolete(String isobsolete) {
        this.isobsolete = isobsolete == null ? null : isobsolete.trim();
    }

    public String getCoboName() {
        return coboName;
    }

    public void setCoboName(String coboName) {
        this.coboName = coboName == null ? null : coboName.trim();
    }

    public String getCoboCertNum() {
        return coboCertNum;
    }

    public void setCoboCertNum(String coboCertNum) {
        this.coboCertNum = coboCertNum == null ? null : coboCertNum.trim();
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName == null ? null : loanName.trim();
    }

    public String getLoanCertNum() {
        return loanCertNum;
    }

    public void setLoanCertNum(String loanCertNum) {
        this.loanCertNum = loanCertNum == null ? null : loanCertNum.trim();
    }

    public String getChannelFlag() {
        return channelFlag;
    }

    public void setChannelFlag(String channelFlag) {
        this.channelFlag = channelFlag == null ? null : channelFlag.trim();
    }

    public BigDecimal getContractMonthRepayTotal() {
        return contractMonthRepayTotal;
    }

    public void setContractMonthRepayTotal(BigDecimal contractMonthRepayTotal) {
        this.contractMonthRepayTotal = contractMonthRepayTotal;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getLegalMan() {
        return legalMan;
    }

    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan == null ? null : legalMan.trim();
    }

    public String getMaddress() {
        return maddress;
    }

    public void setMaddress(String maddress) {
        this.maddress = maddress == null ? null : maddress.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister == null ? null : isRegister.trim();
    }

   

    public String getPaperLessFlag() {
		return paperLessFlag;
	}

	public void setPaperLessFlag(String paperLessFlag) {
		this.paperLessFlag = paperLessFlag;
	}

	public String getSettleCause() {
        return settleCause;
    }

    public void setSettleCause(String settleCause) {
        this.settleCause = settleCause == null ? null : settleCause.trim();
    }

    public String getSettleCauseElse() {
        return settleCauseElse;
    }

    public void setSettleCauseElse(String settleCauseElse) {
        this.settleCauseElse = settleCauseElse == null ? null : settleCauseElse.trim();
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId == null ? null : protocolId.trim();
    }

    public String getDictSourceType() {
        return dictSourceType;
    }

    public void setDictSourceType(String dictSourceType) {
        this.dictSourceType = dictSourceType == null ? null : dictSourceType.trim();
    }

    public String getDictSourceTypePcl() {
        return dictSourceTypePcl;
    }

    public void setDictSourceTypePcl(String dictSourceTypePcl) {
        this.dictSourceTypePcl = dictSourceTypePcl == null ? null : dictSourceTypePcl.trim();
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId == null ? null : creditId.trim();
    }

    public String getContractHistoryVersion() {
        return contractHistoryVersion;
    }

    public void setContractHistoryVersion(String contractHistoryVersion) {
        this.contractHistoryVersion = contractHistoryVersion == null ? null : contractHistoryVersion.trim();
    }

    public String getRevisitStatus() {
        return revisitStatus;
    }

    public void setRevisitStatus(String revisitStatus) {
        this.revisitStatus = revisitStatus == null ? null : revisitStatus.trim();
    }

    public String getRevisitReason() {
        return revisitReason;
    }

    public void setRevisitReason(String revisitReason) {
        this.revisitReason = revisitReason == null ? null : revisitReason.trim();
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Integer pushNumber) {
        this.pushNumber = pushNumber;
    }

    public String getAuditingBy() {
        return auditingBy;
    }

    public void setAuditingBy(String auditingBy) {
        this.auditingBy = auditingBy == null ? null : auditingBy.trim();
    }

    public Date getAuditingTime() {
        return auditingTime;
    }

    public void setAuditingTime(Date auditingTime) {
        this.auditingTime = auditingTime;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender == null ? null : lender.trim();
    }

    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag == null ? null : newFlag.trim();
    }

	public BigDecimal getMonthPayTotalAmount() {
		return monthPayTotalAmount;
	}

	public void setMonthPayTotalAmount(BigDecimal monthPayTotalAmount) {
		this.monthPayTotalAmount = monthPayTotalAmount;
	}

	public String getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(String isreceive) {
		this.isreceive = isreceive;
	}

	public String getSignUpFlag() {
		return signUpFlag;
	}

	public void setSignUpFlag(String signUpFlag) {
		this.signUpFlag = signUpFlag;
	}
    
}