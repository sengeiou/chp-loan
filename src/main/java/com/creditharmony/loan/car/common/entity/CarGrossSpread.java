/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.common.entity
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 	  车借总费率
 *  @Class Name CartGrossSpread
 * 	@author 任亮杰
 * 	@Create In 2016年1月21日
 * 
 */
public class CarGrossSpread extends DataEntity<CarGrossSpread> {

	private static final long serialVersionUID = 7318218060616748587L;
	//  总费率ID
	private String rateId;
	//  产品类型   		1: GPS   	2: 移交		3: 质押
	private String dictProductType;
	//  总费率期限   	1: 30		2:90		3:180		4:270		5:360
	private String dictDeadline;
	//  总费率
	private BigDecimal grossRate;
	//  是否启用   	  	0: 停用    		1: 启用
	private String dictInitiate;
	//  创建人
	private String createBy;
	//  创建时间
	private Date createTime;
	//  修改人
	private String  modifyBy;
	//  修改时间
	private Date  modifyTime;
	
	private String rateType;//费率类型
	
	
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public String getDictProductType() {
		return dictProductType;
	}
	public void setDictProductType(String dictProductType) {
		this.dictProductType = dictProductType;
	}
	public String getDictDeadline() {
		return dictDeadline;
	}
	public void setDictDeadline(String dictDeadline) {
		this.dictDeadline = dictDeadline;
	}
	public BigDecimal getGrossRate() {
		return grossRate;
	}
	public void setGrossRate(BigDecimal grossRate) {
		this.grossRate = grossRate;
	}
	public String getDictInitiate() {
		return dictInitiate;
	}
	public void setDictInitiate(String dictInitiate) {
		this.dictInitiate = dictInitiate;
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
