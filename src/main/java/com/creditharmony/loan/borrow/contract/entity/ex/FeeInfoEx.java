/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.exFeeInfo.java
 * @Create By 张灏
 * @Create In 2015年12月3日 上午9:18:06
 */
package com.creditharmony.loan.borrow.contract.entity.ex;


/**
 * @Class Name FeeInfo
 * @author 张灏
 * @Create In 2015年12月3日
 */
public class FeeInfoEx {
    // 批借金额
    private String auditAmount;
    // 合同金额
    private String contractAmount;
    // 预计还款总额
    private String contractExpectAmount;
    // 月还款本息和
    private String contractMonthRepayAmount; 
    // 月还款总额
    private String monthPayTotalAmount;
    // 总费率
    private String feeAllRaio;  
    // 外访费 
    private String feePetition;
    // 加急费
    private String feeExpedited;  
    // 咨询费
    private String feeConsult;    
    // 审核费
    private String feeAuditAmount;   
    // 居间服务费
    private String feeService;    
    // 催收服务费
    private String feeUrgedService;
    // 信息服务费
    private String feeInfoService;  
    // 综合费用
    private String feeCount;        
    // 实放金额
    private String feePaymentAmount;     
    // 借款利率
    private String feeLoanRate;      
    // 月利率
    private String feeMonthRate;    
    // 分期咨询费
    private String monthFeeConsult;
    // 分期居间服务费
    private String monthMidFeeService;
    // 分期服务费
    private String monthFeeService;
    public String getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(String auditAmount) {
        this.auditAmount = auditAmount;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractExpectAmount() {
        return contractExpectAmount;
    }

    public void setContractExpectAmount(String contractExpectAmount) {
        this.contractExpectAmount = contractExpectAmount;
    }

    public String getContractMonthRepayAmount() {
        return contractMonthRepayAmount;
    }

    public void setContractMonthRepayAmount(String contractMonthRepayAmount) {
        this.contractMonthRepayAmount = contractMonthRepayAmount;
    }

    public String getMonthPayTotalAmount() {
		return monthPayTotalAmount;
	}

	public void setMonthPayTotalAmount(String monthPayTotalAmount) {
		this.monthPayTotalAmount = monthPayTotalAmount;
	}

	public String getFeeAllRaio() {
        return feeAllRaio;
    }

    public void setFeeAllRaio(String feeAllRaio) {
        this.feeAllRaio = feeAllRaio;
    }

    public String getFeePetitionFee() {
        return feePetition;
    }

    public void setFeePetition(String feePetition) {
        this.feePetition = feePetition;
    }

    public String getFeeExpedited() {
        return feeExpedited;
    }

    public void setFeeExpedited(String feeExpedited) {
        this.feeExpedited = feeExpedited;
    }

    public String getFeeConsult() {
        return feeConsult;
    }

    public void setFeeConsult(String feeConsult) {
        this.feeConsult = feeConsult;
    }

    public String getFeeAuditAmount() {
        return feeAuditAmount;
    }

    public void setFeeAuditAmount(String feeAuditAmount) {
        this.feeAuditAmount = feeAuditAmount;
    }

    public String getFeeService() {
        return feeService;
    }

    public void setFeeService(String feeService) {
        this.feeService = feeService;
    }

    public String getFeeUrgedService() {
        return feeUrgedService;
    }

    public void setFeeUrgedService(String feeUrgedService) {
        this.feeUrgedService = feeUrgedService;
    }

    public String getFeeInfoService() {
        return feeInfoService;
    }

    public void setFeeInfoService(String feeInfoService) {
        this.feeInfoService = feeInfoService;
    }

    public String getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(String feeCount) {
        this.feeCount = feeCount;
    }

    public String getFeePaymentAmount() {
        return feePaymentAmount;
    }

    public void setFeePaymentAmount(String feePaymentAmount) {
        this.feePaymentAmount = feePaymentAmount;
    }

    public String getFeeLoanRate() {
        return feeLoanRate;
    }

    public void setFeeLoanRate(String feeLoanRate) {
        this.feeLoanRate = feeLoanRate;
    }

    public String getFeeMonthRate() {
        return feeMonthRate;
    }

    public void setFeeMonthRate(String feeMonthRate) {
        this.feeMonthRate = feeMonthRate;
    }

	public String getMonthFeeConsult() {
		return monthFeeConsult;
	}

	public void setMonthFeeConsult(String monthFeeConsult) {
		this.monthFeeConsult = monthFeeConsult;
	}

	public String getMonthMidFeeService() {
		return monthMidFeeService;
	}

	public void setMonthMidFeeService(String monthMidFeeService) {
		this.monthMidFeeService = monthMidFeeService;
	}

	public String getMonthFeeService() {
		return monthFeeService;
	}

	public void setMonthFeeService(String monthFeeService) {
		this.monthFeeService = monthFeeService;
	}

	public String getFeePetition() {
		return feePetition;
	}

}
