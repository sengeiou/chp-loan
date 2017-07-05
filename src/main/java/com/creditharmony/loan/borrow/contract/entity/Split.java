package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 信息占比表
 * @author 申阿伟
 */
public class Split extends DataEntity<Split>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal zcj;
	private BigDecimal jinxin;
	private Date createTime;
	private String createby;
	private String effective;
	
	
	
	
	
	public BigDecimal getZcj() {
		return zcj;
	}
	public void setZcj(BigDecimal zcj) {
		this.zcj = zcj;
	}
	public BigDecimal getJinxin() {
		return jinxin;
	}
	public void setJinxin(BigDecimal jinxin) {
		this.jinxin = jinxin;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getEffective() {
		return effective;
	}
	public void setEffective(String effective) {
		this.effective = effective;
	}
	
	

}
