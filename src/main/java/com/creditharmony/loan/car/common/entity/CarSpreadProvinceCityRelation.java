/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.common.entity
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 	  车借_省市费率关系表
 *  @Class Name CarSpreadProvinceCityRelation
 * 	@author 任亮杰
 * 	@Create In 2016年1月21日
 * 
 */
public class CarSpreadProvinceCityRelation extends DataEntity<CarSpreadProvinceCityRelation> {

	private static final long serialVersionUID = 2087169639758411956L;
	//  ID
	private String ID;
	//  区域编码
	private String provinceCityId;
	//  费率ID
	private String rateId;
	//  创建人
	private String createBy;
	//  创建时间
	private Date createTime;
	//  修改人
	private String  modifyBy;
	//  修改时间
	private Date  modifyTime;
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getProvinceCityId() {
		return provinceCityId;
	}
	public void setProvinceCityId(String provinceCityId) {
		this.provinceCityId = provinceCityId;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
