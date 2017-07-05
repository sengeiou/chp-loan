package com.creditharmony.loan.car.common.entity;

import com.creditharmony.core.persistence.DataEntity;

import java.util.Date;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 变更记录表
 * @Class Name ChangerInfo
 */
public class CarChangerInfo extends DataEntity<CarChangerInfo> {
	private static final long serialVersionUID = -7707667923173843334L;
    //applyId
    private String applyId;    
    //变更编号
    private String changeCode; 
    //变更类型
    private String changeType; 
    //审批结果
    private String changeResult;
    //手机变更前
    private String mobileChangeBegin;
    private String mobileChangeBeginEnc;
    //手机变更后
    private String mobileChangeAfter; 
    private String mobileChangeAfterEnc;
    //邮箱变更前
    private String emailChangeBegin;
    //邮箱变更后
    private String meailChangeAfter; 
    //备注
    private String remark;   
    //创建人
    private String createBy;    
    //创建时间
    private Date createTime; 
    // 修改类型(0:修改，1:删除)
    private String dealFlag;
    // 修改对象Id
    private String updateId;
    
    public String getMobileChangeBeginEnc() {
    	return mobileChangeBeginEnc;
    }
    
    public String getMobileChangeAfterEnc() {
    	return mobileChangeAfterEnc;
    }
    
	public String getMobileChangeBegin() {
        if(mobileChangeBegin != null && mobileChangeBegin.length()!=11){
            mobileChangeBegin = decryptPhones(mobileChangeBegin,"t_cj_changer_info","mobile_change_begin");
        }
		return mobileChangeBegin;
	}
	public void setMobileChangeBegin(String mobileChangeBegin) {
		if(mobileChangeBegin != null && mobileChangeBegin.length() == 11){
			mobileChangeBegin = encryptPhones(mobileChangeBegin,"t_cj_changer_info","mobile_change_begin");
		}
		this.mobileChangeBegin = mobileChangeBegin;
		this.mobileChangeBeginEnc = mobileChangeBegin;
	}
	public String getMobileChangeAfter() {
        if(mobileChangeAfter != null && mobileChangeAfter.length()!=11){
            mobileChangeAfter = decryptPhones(mobileChangeAfter,"t_cj_changer_info","mobile_change_after");
        }
		return mobileChangeAfter;
	}
	public void setMobileChangeAfter(String mobileChangeAfter) {
		if(mobileChangeAfter != null && mobileChangeAfter.length() == 11){
			mobileChangeAfter = encryptPhones(mobileChangeAfter,"t_cj_changer_info","mobile_change_after");
		}
		this.mobileChangeAfter = mobileChangeAfter;
		this.mobileChangeAfterEnc = mobileChangeAfter;
	}
	public String getEmailChangeBegin() {
		return emailChangeBegin;
	}
	public void setEmailChangeBegin(String emailChangeBegin) {
		this.emailChangeBegin = emailChangeBegin;
	}
	public String getMeailChangeAfter() {
		return meailChangeAfter;
	}
	public void setMeailChangeAfter(String meailChangeAfter) {
		this.meailChangeAfter = meailChangeAfter;
	}

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