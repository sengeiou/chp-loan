package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 还款_还款蓝补交易明细表
 * 
 * @Class Name PaybackBuleAmont
 * @author 李强
 * @Create In 2015年12月21日
 */
@SuppressWarnings("serial")
public class PaybackBuleAmont extends DataEntity<PaybackBuleAmont> {

	private String rMonthId; // 关联ID(期供ID)
	private Date dealTime; // 交易时间
	private String tradeType; // 交易类型
	private String operator; // 操作人
	private String dictDealUse; // 交易用途
	private BigDecimal tradeAmount; // 交易金额
	private BigDecimal surplusBuleAmount; // 蓝补余额
	private String dictOffsetType; // 冲抵类型
    private String contractCode;// 合同编号   

	public String getrMonthId() {
		return rMonthId;
	}

	public void setrMonthId(String rMonthId) {
		this.rMonthId = rMonthId;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDictDealUse() {
		return dictDealUse;
	}

	public void setDictDealUse(String dictDealUse) {
		this.dictDealUse = dictDealUse;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getSurplusBuleAmount() {
		return surplusBuleAmount;
	}

	public void setSurplusBuleAmount(BigDecimal surplusBuleAmount) {
		this.surplusBuleAmount = surplusBuleAmount;
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

	public String getDictOffsetType() {
		return dictOffsetType;
	}

	public void setDictOffsetType(String dictOffsetType) {
		this.dictOffsetType = dictOffsetType;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
}
