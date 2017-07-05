/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityNumberMaster.java
 * @Create By 王彬彬
 * @Create In 2015年12月29日 上午10:07:48
 */
package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 汇金编号实体
 * 
 * @Class Name NumberMaster
 * @author 王彬彬
 * @Create In 2015年12月29日
 */
@SuppressWarnings("serial")
public class NumberMaster extends DataEntity<NumberMaster> {

	// 编号类型
	private String dealPart;
	// 编号周期
	private String dealCyc;
	// 周期起始时间
	private String dealDate;
	// 编号
	private Integer SerialNo;
	// 有效标识
	private String effective;

	public String getDealPart() {
		return dealPart;
	}

	public String getDealCyc() {
		return dealCyc;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public Integer getSerialNo() {
		return SerialNo;
	}


	public void setDealPart(String dealPart) {
		this.dealPart = dealPart;
	}

	public void setDealCyc(String dealCyc) {
		this.dealCyc = dealCyc;
	}

	public void setSerialNo(Integer serialNo) {
		SerialNo = serialNo;
	}

	public String getEffective() {
		return effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}

}
