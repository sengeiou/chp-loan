/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityLoanNos.java
 * @Create By 王彬彬
 * @Create In 2016年4月19日 下午7:46:56
 */
package com.creditharmony.loan.common.entity;

/**
 * 合同编号记录表
 * @Class Name LoanNos
 * @author 王彬彬
 * @Create In 2016年4月19日
 */
public class LoanNos  {
	private String noType; // 合同编号类型
	
	private String noKeys; // 合同编号标识（信借门店编号）
	
	private Integer noCount;// 合同号码计数
	
	public String getNoType() {
		return noType;
	}
	public void setNoType(String noType) {
		this.noType = noType;
	}
	public String getNoKeys() {
		return noKeys;
	}
	public void setNoKeys(String noKeys) {
		this.noKeys = noKeys;
	}
	public Integer getNoCount() {
		return noCount;
	}
	public void setNoCount(Integer noCount) {
		this.noCount = noCount;
	}
	
	
}
