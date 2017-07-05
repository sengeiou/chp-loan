package com.creditharmony.loan.car.common.entity;
import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 催收服务费实体类
 * @Class Name UrgeServicesMoney
 * @author 张振强
 * @Create In 2016年3月1日
 */
public class CarUrgeServicesMoney extends DataEntity<CarUrgeServicesMoney>{
    
	private static final long serialVersionUID = 396962871464681555L;
    // 主键,催收服务费，
	private String id;
	// 关联放款id
	private String rGrantId;
    // 催收服务费
    private BigDecimal urgeMoeny;
    // 查账金额
    private BigDecimal auditAmount;
    // 已划金额
    private BigDecimal urgeDecuteMoeny;
    // 最新划扣日期
    private Date urgeDecuteDate;
    // 处理状态
    private String dictDealStatus;
    // 划扣状态
    private String deductStatus;
    // 失败原因
    private String splitFailResult;
    // 时间标识,用来区分以往和当日划扣列表，默认初始状态为0
    private String timeFlag;
    // 退回标识,默认为 0,表示未退回
    private String returnLogo;
    // 划扣平台
    private String dictDealType;
    // 拆分表中回盘结果为成功的单子的个数
    private Integer successAmount;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;
    private String deductJumpRule;
    private String autoDeductFlag;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getrGrantId() {
		return rGrantId;
	}

	public void setrGrantId(String rGrantId) {
		this.rGrantId = rGrantId;
	}

	public BigDecimal getUrgeMoeny() {
        return urgeMoeny;
    }

    public void setUrgeMoeny(BigDecimal urgeMoeny) {
        this.urgeMoeny = urgeMoeny;
    }

    public BigDecimal getUrgeDecuteMoeny() {
		return urgeDecuteMoeny;
	}

	public void setUrgeDecuteMoeny(BigDecimal urgeDecuteMoeny) {
		this.urgeDecuteMoeny = urgeDecuteMoeny;
	}

	public Date getUrgeDecuteDate() {
		return urgeDecuteDate;
	}

	public void setUrgeDecuteDate(Date urgeDecuteDate) {
		this.urgeDecuteDate = urgeDecuteDate;
	}

	public String getDictDealStatus() {
        return dictDealStatus;
    }

    public void setDictDealStatus(String dictDealStatus) {
        this.dictDealStatus = dictDealStatus == null ? null : dictDealStatus.trim();
    }
    
    public String getSplitFailResult() {
		return splitFailResult;
	}

	public void setSplitFailResult(String splitFailResult) {
		this.splitFailResult = splitFailResult;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getReturnLogo() {
		return returnLogo;
	}

	public void setReturnLogo(String returnLogo) {
		this.returnLogo = returnLogo;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public Integer getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(Integer successAmount) {
		this.successAmount = successAmount;
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

	public String getDeductJumpRule() {
		return deductJumpRule;
	}

	public void setDeductJumpRule(String deductJumpRule) {
		this.deductJumpRule = deductJumpRule;
	}

	public String getAutoDeductFlag() {
		return autoDeductFlag;
	}

	public void setAutoDeductFlag(String autoDeductFlag) {
		this.autoDeductFlag = autoDeductFlag;
	}

	public String getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(String deductStatus) {
		this.deductStatus = deductStatus;
	}

	public BigDecimal getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(BigDecimal auditAmount) {
		this.auditAmount = auditAmount;
	}
}