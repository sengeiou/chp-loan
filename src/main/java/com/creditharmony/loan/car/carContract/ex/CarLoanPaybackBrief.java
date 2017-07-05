package  com.creditharmony.loan.car.carContract.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;


/**
 * 车借还款明细表
 */
public class CarLoanPaybackBrief extends DataEntity<CarLoanPaybackBrief> {
	
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 3674592352326436934L;
    
    // 主键ID
    private String loanPaybackBriefId;
    // 还款记录表ID
    private String loanPaybackId;
    // 当期期数
    private Integer currentLimitTimme;
    // 当起应还款日期
    private Date repaymentDate;
    // 当期实际还款日期
    private Date repaymentActualDate;
    // 实还本金
    private BigDecimal realInterest;
    // 应还本金
    private Double currentInterest;
    // 实还利息
    private BigDecimal realRate;
    // 应还利息
    private BigDecimal currentRate;
    // 减免金额
    private BigDecimal interestlRelief;
    // 利息减免金额
    private BigDecimal rateRelief;
    // 实还管理费
    private BigDecimal realFee;
    // 应还管理费
    private BigDecimal currentManagementRate;
    // 管理费减免金额
    private BigDecimal feeRelief;
    // 当期罚息
    private BigDecimal curretnLateCharge;
    // 实还罚息
    private BigDecimal realLateCharge;
    // 罚息减免
    private BigDecimal lateChargeRelief;
    // 实还滞纳金
    private BigDecimal realOverdueFine;
    // 应还滞纳金
    private BigDecimal currentOverdueFine;
    // 滞纳金减免金额
    private BigDecimal overdueFineRelief;
    // 当期应还违约金
    private BigDecimal currentDefaultFine;
    // 实还违约金
    private BigDecimal realDefaultFine;
    // 减免滞纳金金额
    private BigDecimal defaultFineRelief;
    // 还款状态，包括：1:还款待审核、2:还款待确认、0:待还款、3:还款退回、4:已还款、5:还款失败、6逾期、7追回
    private String repaymentFlag;
    // 创建人
    private String createBy;
    // 创建时间
    private Date createTime;
    // 修改人
    private String lastmodifyBy;
    // 修改时间
    private Date lastmodifyTime;
    // 一次性提前还款金额
    private Double prepayment;
    // 还款类型：0：集中划扣  1：提前还款 2：逾期还款 3：提前结清
    private String payType;
    // 当期全部利息
    private Short currentTotalInterest;
    // 期供状态1：还款中 2：逾期，3：追回，4:已还款
    private String periodType;
    // 逾期天数
    private Short yqts;
    // 减免人
    private String jianmianren;
    // 0:未申请或者申请退回 1：已申请
    private String status;
    // 已经还过的金额
    private Double yihuanqigongjin;
    // 停车费
    private Double tingchefei;
    
    private Double xiaoji;

   
    
    
	public String getLoanPaybackBriefId() {
		return loanPaybackBriefId;
	}

	public void setLoanPaybackBriefId(String loanPaybackBriefId) {
		this.loanPaybackBriefId = loanPaybackBriefId;
	}

	public String getLoanPaybackId() {
		return loanPaybackId;
	}

	public void setLoanPaybackId(String loanPaybackId) {
		this.loanPaybackId = loanPaybackId;
	}

	public Integer getCurrentLimitTimme() {
        return currentLimitTimme;
    }

    public void setCurrentLimitTimme(Integer currentLimitTimme) {
        this.currentLimitTimme = currentLimitTimme;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Date getRepaymentActualDate() {
        return repaymentActualDate;
    }

    public void setRepaymentActualDate(Date repaymentActualDate) {
        this.repaymentActualDate = repaymentActualDate;
    }

    public BigDecimal getRealInterest() {
        return realInterest;
    }

    public void setRealInterest(BigDecimal realInterest) {
        this.realInterest = realInterest;
    }

     
    public Double getCurrentInterest() {
		return currentInterest;
	}

	public void setCurrentInterest(Double currentInterest) {
		this.currentInterest = currentInterest;
	}

	public BigDecimal getRealRate() {
        return realRate;
    }

    public void setRealRate(BigDecimal realRate) {
        this.realRate = realRate;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

    public BigDecimal getInterestlRelief() {
        return interestlRelief;
    }

    public void setInterestlRelief(BigDecimal interestlRelief) {
        this.interestlRelief = interestlRelief;
    }

    public BigDecimal getRateRelief() {
        return rateRelief;
    }

    public void setRateRelief(BigDecimal rateRelief) {
        this.rateRelief = rateRelief;
    }

    public BigDecimal getRealFee() {
        return realFee;
    }

    public void setRealFee(BigDecimal realFee) {
        this.realFee = realFee;
    }

    public BigDecimal getCurrentManagementRate() {
        return currentManagementRate;
    }

    public void setCurrentManagementRate(BigDecimal currentManagementRate) {
        this.currentManagementRate = currentManagementRate;
    }

    public BigDecimal getFeeRelief() {
        return feeRelief;
    }

    public void setFeeRelief(BigDecimal feeRelief) {
        this.feeRelief = feeRelief;
    }

    public BigDecimal getCurretnLateCharge() {
        return curretnLateCharge;
    }

    public void setCurretnLateCharge(BigDecimal curretnLateCharge) {
        this.curretnLateCharge = curretnLateCharge;
    }

    public BigDecimal getRealLateCharge() {
        return realLateCharge;
    }

    public void setRealLateCharge(BigDecimal realLateCharge) {
        this.realLateCharge = realLateCharge;
    }

    public BigDecimal getLateChargeRelief() {
        return lateChargeRelief;
    }

    public void setLateChargeRelief(BigDecimal lateChargeRelief) {
        this.lateChargeRelief = lateChargeRelief;
    }

    public BigDecimal getRealOverdueFine() {
        return realOverdueFine;
    }

    public void setRealOverdueFine(BigDecimal realOverdueFine) {
        this.realOverdueFine = realOverdueFine;
    }

    public BigDecimal getCurrentOverdueFine() {
        return currentOverdueFine;
    }

    public void setCurrentOverdueFine(BigDecimal currentOverdueFine) {
        this.currentOverdueFine = currentOverdueFine;
    }

    public BigDecimal getOverdueFineRelief() {
        return overdueFineRelief;
    }

    public void setOverdueFineRelief(BigDecimal overdueFineRelief) {
        this.overdueFineRelief = overdueFineRelief;
    }

    public BigDecimal getCurrentDefaultFine() {
        return currentDefaultFine;
    }

    public void setCurrentDefaultFine(BigDecimal currentDefaultFine) {
        this.currentDefaultFine = currentDefaultFine;
    }

    public BigDecimal getRealDefaultFine() {
        return realDefaultFine;
    }

    public void setRealDefaultFine(BigDecimal realDefaultFine) {
        this.realDefaultFine = realDefaultFine;
    }

    public BigDecimal getDefaultFineRelief() {
        return defaultFineRelief;
    }

    public void setDefaultFineRelief(BigDecimal defaultFineRelief) {
        this.defaultFineRelief = defaultFineRelief;
    }

    public String getRepaymentFlag() {
        return repaymentFlag;
    }

    public void setRepaymentFlag(String repaymentFlag) {
        this.repaymentFlag = repaymentFlag;
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

    public String getLastmodifyBy() {
        return lastmodifyBy;
    }

    public void setLastmodifyBy(String lastmodifyBy) {
        this.lastmodifyBy = lastmodifyBy;
    }

    public Date getLastmodifyTime() {
        return lastmodifyTime;
    }

    public void setLastmodifyTime(Date lastmodifyTime) {
        this.lastmodifyTime = lastmodifyTime;
    }

    
	public Double getPrepayment() {
		return prepayment;
	}

	public void setPrepayment(Double prepayment) {
		this.prepayment = prepayment;
	}

	public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Short getCurrentTotalInterest() {
        return currentTotalInterest;
    }

    public void setCurrentTotalInterest(Short currentTotalInterest) {
        this.currentTotalInterest = currentTotalInterest;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Short getYqts() {
        return yqts;
    }

    public void setYqts(Short yqts) {
        this.yqts = yqts;
    }

    public String getJianmianren() {
        return jianmianren;
    }

    public void setJianmianren(String jianmianren) {
        this.jianmianren = jianmianren;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Double getYihuanqigongjin() {
		return yihuanqigongjin;
	}

	public void setYihuanqigongjin(Double yihuanqigongjin) {
		this.yihuanqigongjin = yihuanqigongjin;
	}

	public Double getTingchefei() {
		return tingchefei;
	}

	public void setTingchefei(Double tingchefei) {
		this.tingchefei = tingchefei;
	}

	public Double getXiaoji() {
		return xiaoji;
	}

	public void setXiaoji(Double xiaoji) {
		this.xiaoji = xiaoji;
	}

        
}