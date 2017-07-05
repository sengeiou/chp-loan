package com.creditharmony.loan.borrow.grant.entity;
import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 催收服务费返款实体
 * @Class Name UrgeServicesBackMoney
 * @author 朱静越
 * @Create In 2015年12月15日
 */
public class UrgeServicesBackMoney extends DataEntity<UrgeServicesBackMoney>{
	private static final long serialVersionUID = -1937366004949542911L;
	// 返款id
    private String id;
    // 关联ID，催收服务费id
    private String rPaybackId;
    // 合同编号
    private String contractCode;
    // 返款状态
    private String dictPayStatus;
    // 返款结果
    private String dictPayResult;
    // 失败原因
    private String remark;
    // 催收返还，结清时间，从还款操作流水表中的
    private Date settlementTime;
    // 返款金额
    private BigDecimal paybackBackMoney;
    // 返款申请时间
    private Date backApplyPayTime;
    // 返款申请人（风控）
    private String backApplyBy;
    // 返款申请部门（风控）
    private String backApplyDepartment;
    // 返款办理人（汇金事业部）
    private String backTransactor;
    // 返款办理部门
    private String backTransactorTeam;
    // 返款时间
    private Date backTime;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getrPaybackId() {
		return rPaybackId;
	}

	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getBackApplyPayTime() {
		return backApplyPayTime;
	}

	public void setBackApplyPayTime(Date backApplyPayTime) {
		this.backApplyPayTime = backApplyPayTime;
	}

	public String getBackApplyDepartment() {
		return backApplyDepartment;
	}

	public void setBackApplyDepartment(String backApplyDepartment) {
		this.backApplyDepartment = backApplyDepartment;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public String getDictPayStatus() {
        return dictPayStatus;
    }

    public void setDictPayStatus(String dictPayStatus) {
        this.dictPayStatus = dictPayStatus == null ? null : dictPayStatus.trim();
    }

    public String getDictPayResult() {
		return dictPayResult;
	}

	public void setDictPayResult(String dictPayResult) {
		this.dictPayResult = dictPayResult;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public BigDecimal getPaybackBackMoney() {
        return paybackBackMoney;
    }

    public void setPaybackBackMoney(BigDecimal paybackBackMoney) {
        this.paybackBackMoney = paybackBackMoney;
    }

    public String getBackApplyBy() {
        return backApplyBy;
    }

    public void setBackApplyBy(String backApplyBy) {
        this.backApplyBy = backApplyBy == null ? null : backApplyBy.trim();
    }

    public String getBackTransactor() {
        return backTransactor;
    }

    public void setBackTransactor(String backTransactor) {
        this.backTransactor = backTransactor == null ? null : backTransactor.trim();
    }

    public String getBackTransactorTeam() {
        return backTransactorTeam;
    }

    public void setBackTransactorTeam(String backTransactorTeam) {
        this.backTransactorTeam = backTransactorTeam == null ? null : backTransactorTeam.trim();
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
}