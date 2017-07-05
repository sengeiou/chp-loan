package com.creditharmony.loan.borrow.applyinfo.view;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 变更记录表
 * @Class Name ChangerInfo
 */
public class ChangerInfo extends DataEntity<ChangerInfo> {

	private static final long serialVersionUID = -7707667923173843334L;
    //共借人ID
    private String cobId;    
    //applyId
    private String applyId;    
    //变更编号
    private String changeCode; 
    //变更类型
    private String changeType; 
    //审批结果
    private String changeResult;
    //变更前
    private String changeBegin;
    //变更后
    private String changeAfter; 
    //备注
    private String remark;   
    //创建人
    private String createBy;    
    //创建时间
    private Date createTime; 
    // 修改类型(0:修改，1:删除)
    private String dealFlag;
    // 查询类型
    private String selectFlag;
    // 修改对象Id
    private String updateId;
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getChangeCode() {
		return changeCode;
	}
	public void setChangeCode(String changeCode) {
		this.changeCode = changeCode;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getChangeResult() {
		return changeResult;
	}
	public void setChangeResult(String changeResult) {
		this.changeResult = changeResult;
	}
	public String getChangeBegin() {
		return changeBegin;
	}
	public void setChangeBegin(String changeBegin) {
		this.changeBegin = changeBegin;
	}
	public String getChangeAfter() {
		return changeAfter;
	}
	public void setChangeAfter(String changeAfter) {
		this.changeAfter = changeAfter;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCobId() {
		return cobId;
	}
	public void setCobId(String cobId) {
		this.cobId = cobId;
	}
    /**
     * @return the dealFlag
     */
    public String getDealFlag() {
        return dealFlag;
    }
    /**
     * @param dealFlag the String dealFlag to set
     */
    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }
    /**
     * @return the selectFlag
     */
    public String getSelectFlag() {
        return selectFlag;
    }
    /**
     * @param selectFlag the String selectFlag to set
     */
    public void setSelectFlag(String selectFlag) {
        this.selectFlag = selectFlag;
    }
    /**
     * @return the updateId
     */
    public String getUpdateId() {
        return updateId;
    }
    /**
     * @param updateId the String updateId to set
     */
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}