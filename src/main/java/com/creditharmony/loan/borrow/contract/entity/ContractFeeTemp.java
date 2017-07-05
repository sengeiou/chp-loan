package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 
·* 2017年2月21日
·* by Huowenlong
 */
public class ContractFeeTemp extends DataEntity<ContractFeeTemp> {
    private String id;

    private String contractCode;

    private BigDecimal feeAllRaio;

    private BigDecimal feePetition;
    
    private BigDecimal feePetitionTemp;
    
    private BigDecimal feePetitionTotal;

    private BigDecimal feeExpedited;

    private BigDecimal feeConsult;

    private BigDecimal feeAuditAmount;

    private BigDecimal feeService;

    private BigDecimal feeUrgedService;

    private BigDecimal feeInfoService;

    private BigDecimal feeCount;

    private BigDecimal feePaymentAmount;

    private BigDecimal feeLoanRate;

    private BigDecimal feeMonthRate;

    private Date createTime;

    private String createBy;

    private String modifyBy;

    private Date modifyTime;

    private BigDecimal monthFeeConsult;

    private BigDecimal monthMidFeeService;

    private BigDecimal monthFeeService;
    
    private BigDecimal monthFeeServiceTotal;
    
    // 月利率
    private String feeMonthRateS;
    
    // 借款利率 
    private Float feeLoanRatef;


    private BigDecimal monthLateFee;

    private BigDecimal comprehensiveServiceRate;

    private BigDecimal monthRateService;

    private String dictSourceType;

    private String dictSourceTypePcl;
    // 渠道标识
    private String channelFlag;
    // 渠道名称
    private String channelFlagName;
    // 批借金额
    private BigDecimal auditAmount;
    // 合同金额
    private BigDecimal contractAmount;
    // 月还款本息
    private BigDecimal contractMonthRepayAmount;
    // 预计还款总额
    private BigDecimal contractExpectAmount;
    // 月还款总额
    private BigDecimal contractMonthRepayTotal;
    //期数
    private BigDecimal contractMonths;
    //起始还款日期
    private Date contractReplayDay;
    //合同签订日期
    private Date contractDueDay;
    
    private String isreceive;
    
    // 首次放款金额
    private BigDecimal firstGrantAmount;
    // 尾次放款金额
    private BigDecimal lastGrantAmount;
    

    private static final long serialVersionUID = 1L;
    
 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public BigDecimal getFeeAllRaio() {
        return feeAllRaio;
    }

    public void setFeeAllRaio(BigDecimal feeAllRaio) {
        this.feeAllRaio = feeAllRaio;
    }

    public BigDecimal getFeePetition() {
        return feePetition;
    }

    public void setFeePetition(BigDecimal feePetition) {
        this.feePetition = feePetition;
    }

    public BigDecimal getFeeExpedited() {
        return feeExpedited;
    }

    public void setFeeExpedited(BigDecimal feeExpedited) {
        this.feeExpedited = feeExpedited;
    }

    public BigDecimal getFeeConsult() {
        return feeConsult;
    }

    public void setFeeConsult(BigDecimal feeConsult) {
        this.feeConsult = feeConsult;
    }

    public BigDecimal getFeeAuditAmount() {
        return feeAuditAmount;
    }

    public void setFeeAuditAmount(BigDecimal feeAuditAmount) {
        this.feeAuditAmount = feeAuditAmount;
    }

    public BigDecimal getFeeService() {
        return feeService;
    }

    public void setFeeService(BigDecimal feeService) {
        this.feeService = feeService;
    }

    public BigDecimal getFeeUrgedService() {
        return feeUrgedService;
    }

    public void setFeeUrgedService(BigDecimal feeUrgedService) {
        this.feeUrgedService = feeUrgedService;
    }

    public BigDecimal getFeeInfoService() {
        return feeInfoService;
    }

    public void setFeeInfoService(BigDecimal feeInfoService) {
        this.feeInfoService = feeInfoService;
    }

    public BigDecimal getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(BigDecimal feeCount) {
        this.feeCount = feeCount;
    }

    public BigDecimal getFeePaymentAmount() {
        return feePaymentAmount;
    }

    public void setFeePaymentAmount(BigDecimal feePaymentAmount) {
        this.feePaymentAmount = feePaymentAmount;
    }

    public BigDecimal getFeeLoanRate() {
        return feeLoanRate;
    }

    public void setFeeLoanRate(BigDecimal feeLoanRate) {
        this.feeLoanRate = feeLoanRate;
    }

    public BigDecimal getFeeMonthRate() {
        return feeMonthRate;
    }

    public void setFeeMonthRate(BigDecimal feeMonthRate) {
        this.feeMonthRate = feeMonthRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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

    public BigDecimal getMonthFeeService() {
        return monthFeeService;
    }

    public void setMonthFeeService(BigDecimal monthFeeService) {
        this.monthFeeService = monthFeeService;
    }

    public BigDecimal getMonthLateFee() {
        return monthLateFee;
    }

    public void setMonthLateFee(BigDecimal monthLateFee) {
        this.monthLateFee = monthLateFee;
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


	public String getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}

	public BigDecimal getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}

	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}

	public BigDecimal getContractExpectAmount() {
		return contractExpectAmount;
	}

	public void setContractExpectAmount(BigDecimal contractExpectAmount) {
		this.contractExpectAmount = contractExpectAmount;
	}

	public BigDecimal getContractMonthRepayTotal() {
		return contractMonthRepayTotal;
	}

	public void setContractMonthRepayTotal(BigDecimal contractMonthRepayTotal) {
		this.contractMonthRepayTotal = contractMonthRepayTotal;
	}

	public String getChannelFlagName() {
		return channelFlagName;
	}

	public void setChannelFlagName(String channelFlagName) {
		this.channelFlagName = channelFlagName;
	}

	public BigDecimal getMonthFeeServiceTotal() {
		return monthFeeServiceTotal;
	}

	public void setMonthFeeServiceTotal(BigDecimal monthFeeServiceTotal) {
		this.monthFeeServiceTotal = monthFeeServiceTotal;
	}

	public BigDecimal getFeePetitionTotal() {
		return feePetitionTotal;
	}

	public void setFeePetitionTotal(BigDecimal feePetitionTotal) {
		this.feePetitionTotal = feePetitionTotal;
	}

	public BigDecimal getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(BigDecimal contractMonths) {
		this.contractMonths = contractMonths;
	}


	public String getFeeMonthRateS() {
		return feeMonthRateS;
	}

	public void setFeeMonthRateS(String feeMonthRateS) {
		this.feeMonthRateS = feeMonthRateS;
	}

	public Float getFeeLoanRatef() {
		return feeLoanRatef;
	}

	public void setFeeLoanRatef(Float feeLoanRatef) {
		this.feeLoanRatef = feeLoanRatef;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}

	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}

	public Date getContractDueDay() {
		return contractDueDay;
	}

	public void setContractDueDay(Date contractDueDay) {
		this.contractDueDay = contractDueDay;
	}

	public String getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(String isreceive) {
		this.isreceive = isreceive;
	}

	public BigDecimal getFeePetitionTemp() {
		return feePetitionTemp;
	}

	public void setFeePetitionTemp(BigDecimal feePetitionTemp) {
		this.feePetitionTemp = feePetitionTemp;
	}

	public BigDecimal getFirstGrantAmount() {
		return firstGrantAmount;
	}

	public void setFirstGrantAmount(BigDecimal firstGrantAmount) {
		this.firstGrantAmount = firstGrantAmount;
	}

	public BigDecimal getLastGrantAmount() {
		return lastGrantAmount;
	}

	public void setLastGrantAmount(BigDecimal lastGrantAmount) {
		this.lastGrantAmount = lastGrantAmount;
	}
	
}