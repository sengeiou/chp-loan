/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.Payback.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 中和东方不可退款主表
 * @author 于飞
 *
 */
@SuppressWarnings("serial")
public class Zhrefund extends DataEntity<Zhrefund> {
	private String id;
	//合同编号
	@ExcelField(title = "合同编号", type = 2, align = 2, sort = 70)
	private String contractCode;
	//客户名称
	private String customerName;
	//门店名称
	private String storeName;
	//门店id
	private String storeId;
	//借款状态
	private String dictLoanStatus;
	//借款状态名称
	private String dictLoanStatusLabel;
	//蓝补金额
	private BigDecimal paybackBuleAmount;
	//月还期供金额
	private BigDecimal paybackMonthAmount;
	//状态
	private String zhrefundStatus;
	//状态名称
	private String zhrefundStatusLabel;
	//创建者
	private String createBy;
	//创建时间
	private Date createTime;
	//修改者
	private String modifyBy;
	//修改时间
	private Date modifyTime;
	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public BigDecimal getPaybackBuleAmount() {
		return paybackBuleAmount;
	}
	public void setPaybackBuleAmount(BigDecimal paybackBuleAmount) {
		this.paybackBuleAmount = paybackBuleAmount;
	}
	public BigDecimal getPaybackMonthAmount() {
		return paybackMonthAmount;
	}
	public void setPaybackMonthAmount(BigDecimal paybackMonthAmount) {
		this.paybackMonthAmount = paybackMonthAmount;
	}
	public String getZhrefundStatus() {
		return zhrefundStatus;
	}
	public void setZhrefundStatus(String zhrefundStatus) {
		this.zhrefundStatus = zhrefundStatus;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getZhrefundStatusLabel() {
		return zhrefundStatusLabel;
	}
	public void setZhrefundStatusLabel(String zhrefundStatusLabel) {
		this.zhrefundStatusLabel = zhrefundStatusLabel;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
