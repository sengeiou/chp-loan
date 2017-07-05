package com.creditharmony.loan.borrow.payback.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 省市平台跳转限制
 * @Class Name PlatformProvinceLimit
 * @author 周俊
 * @Create In 2016年3月4日
 */
public class PlatformProvinceLimit extends DataEntity<PlatformProvinceLimit>{
	/**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	private String id; // 主键
	
	private String platformLimit; // 限制平台 ,在这个省下别的平台不可以跳到这个平台下
	
	private String province; // 省份
	
	private String status; // 状态
	
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

	public String getPlatformLimit() {
		return platformLimit;
	}

	public void setPlatformLimit(String platformLimit) {
		this.platformLimit = platformLimit;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
