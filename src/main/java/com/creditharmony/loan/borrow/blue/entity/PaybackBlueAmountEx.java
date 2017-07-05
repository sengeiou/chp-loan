package com.creditharmony.loan.borrow.blue.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;



/**
 * 蓝补明细表
 * @Class Name PaybackBlueAmountEx
 * @author 侯志斌
 * @Create In 2016年3月4日
 */
@SuppressWarnings("serial")
public class PaybackBlueAmountEx extends DataEntity<PaybackBlueAmountEx>{
	//序号
	private String rownum;
	// 合同编号
    private String contractCode;
    // 客户编码
	private String customerCode;
	//客户名称
	private String customerName;
	
	private String startDate;
	
	private String endDate;
	
	private String rMonthId; // 关联ID(期供ID)
	@ExcelField(title = "交易时间", type = 0, align = 2, sort = 80)
	private Date dealTime; // 交易时间
	@ExcelField(title = "交易动作", type = 0, align = 2, sort = 90)
	private String tradeType; // 交易类型
	@ExcelField(title = "操作人", type = 0, align = 2, sort = 100)
	private String operator; // 操作人
	@ExcelField(title = "交易用途", type = 0, align = 2, sort = 100)
	private String dictDealUse; // 交易用途
	@ExcelField(title = "交易金额", type = 0, align = 2, sort = 100)
	private BigDecimal tradeAmount; // 交易金额
	@ExcelField(title = "蓝补余额", type = 0, align = 2, sort = 100)
	private BigDecimal surplusBuleAmount; // 蓝补余额
	private String dictOffsetType; // 冲抵类型
	// 冲抵期数
	private String offsetRepaymentDate;
	
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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getOffsetRepaymentDate() {
		return offsetRepaymentDate;
	}

	public void setOffsetRepaymentDate(String offsetRepaymentDate) {
		this.offsetRepaymentDate = offsetRepaymentDate;
	}

	
}
