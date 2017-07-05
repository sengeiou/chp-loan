package com.creditharmony.loan.borrow.payback.entity;
import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;


/**
* Description:划扣统计报表实体类
* @class DeductStatisticsReport
* @author wengsi 
* @date 2017年5月24日下午2:43:24
*/
@SuppressWarnings("serial") 
public class DeductStatisticsReport extends DataEntity<DeductStatisticsReport>{
	
	private String id;
	
	private BigDecimal tgReallyAmount; //tg实还金额
	
	private BigDecimal tgSumAmount;  // tg划扣总金额
	
	private Integer tgSumNumber; //tg划扣总笔数

	private Integer tgSuccessNumber; //tg成功笔数
	
	
    private BigDecimal notTgReallyAmount; //非tg实还金额
	
	private BigDecimal notTgSumAmount;  // 非tg划扣总金额
	
	private Integer notTgSumNumber; // 非tg划扣总笔数

	private Integer notTgSuccessNumber; // 非tg成功笔数

	
	private Integer   chongDiTiChuNumber;  // 冲抵剔除笔数
	
	private BigDecimal   chongDiTiChuAmount;  // 冲抵剔除金额
	
	private  Integer  chongDiTiChuDaiNumber; // 冲抵剔除待划扣笔数
	
	private  BigDecimal  chongDiTiChuDaiAmount; // 冲抵剔除待划扣金额
	
	private Date beginDate;
	private Date endDate;
	
	private String createDate;
	

	public String getId() {
		return id;
	}

	public BigDecimal getTgReallyAmount() {
		return tgReallyAmount;
	}

	
	public Integer getChongDiTiChuNumber() {
		return chongDiTiChuNumber;
	}

	public BigDecimal getChongDiTiChuAmount() {
		return chongDiTiChuAmount;
	}

	public Integer getChongDiTiChuDaiNumber() {
		return chongDiTiChuDaiNumber;
	}

	public BigDecimal getChongDiTiChuDaiAmount() {
		return chongDiTiChuDaiAmount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTgReallyAmount(BigDecimal tgReallyAmount) {
		this.tgReallyAmount = tgReallyAmount;
	}

	
	public void setChongDiTiChuNumber(Integer chongDiTiChuNumber) {
		this.chongDiTiChuNumber = chongDiTiChuNumber;
	}

	public void setChongDiTiChuAmount(BigDecimal chongDiTiChuAmount) {
		this.chongDiTiChuAmount = chongDiTiChuAmount;
	}

	public void setChongDiTiChuDaiNumber(Integer chongDiTiChuDaiNumber) {
		this.chongDiTiChuDaiNumber = chongDiTiChuDaiNumber;
	}

	public void setChongDiTiChuDaiAmount(BigDecimal chongDiTiChuDaiAmount) {
		this.chongDiTiChuDaiAmount = chongDiTiChuDaiAmount;
	}

	public BigDecimal getTgSumAmount() {
		return tgSumAmount;
	}

	public Integer getTgSumNumber() {
		return tgSumNumber;
	}

	public Integer getTgSuccessNumber() {
		return tgSuccessNumber;
	}

	public BigDecimal getNotTgReallyAmount() {
		return notTgReallyAmount;
	}

	public BigDecimal getNotTgSumAmount() {
		return notTgSumAmount;
	}

	public Integer getNotTgSumNumber() {
		return notTgSumNumber;
	}

	public Integer getNotTgSuccessNumber() {
		return notTgSuccessNumber;
	}

	public void setTgSumAmount(BigDecimal tgSumAmount) {
		this.tgSumAmount = tgSumAmount;
	}

	public void setTgSumNumber(Integer tgSumNumber) {
		this.tgSumNumber = tgSumNumber;
	}

	public void setTgSuccessNumber(Integer tgSuccessNumber) {
		this.tgSuccessNumber = tgSuccessNumber;
	}

	public void setNotTgReallyAmount(BigDecimal notTgReallyAmount) {
		this.notTgReallyAmount = notTgReallyAmount;
	}

	public void setNotTgSumAmount(BigDecimal notTgSumAmount) {
		this.notTgSumAmount = notTgSumAmount;
	}

	public void setNotTgSumNumber(Integer notTgSumNumber) {
		this.notTgSumNumber = notTgSumNumber;
	}

	public void setNotTgSuccessNumber(Integer notTgSuccessNumber) {
		this.notTgSuccessNumber = notTgSuccessNumber;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	
}
