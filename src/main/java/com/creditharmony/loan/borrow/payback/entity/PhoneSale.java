/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entityPaybackList.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午1:25:19
 */

package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class PhoneSale extends DataEntity<PhoneSale>{
	// 主键ID
	private String id; 
	// 客户姓名
	private String customerName;
	// 合同编号
	private String contractCode;
	// 还款日
	private String monthPayDay;
	// 期供状态
    private String dictLoanStatus;
    // 期供状态(名称)
    private String dictLoanStatusLabel;
    // 是否提醒状态
    private String status;
    // 开户行名称
    private String applyBankName;
    // 开户行名称
    private String applyBankNameLabel;
    // 手机号
    private String tel;
    // 门店名称
    private String storesName;
    // 期数
    private int months;
    // 应还金额(月还期供金额)
    private BigDecimal repayAmount;
    // 已还金额
    private BigDecimal completeAmount;
    // 蓝补金额
    private BigDecimal buleAmount;
    // 划扣平台
    private String dictDealType;
    // 划扣平台
    private String dictDealTypeLabel;
    // 标识
    private String mark;
    private String markLabel;
    private String monthDay;
    //电销专员
    private String commissioner;
    
    // id集合
    private String ids;
    
    private List idList;
    
    private String remindStatusFlag;
    
	public List getIdList() {
		return idList;
	}

	public void setIdList(List idList) {
		this.idList = idList;
	}

	public String getDictDealTypeLabel() {
		return dictDealTypeLabel;
	}

	public void setDictDealTypeLabel(String dictDealTypeLabel) {
		this.dictDealTypeLabel = dictDealTypeLabel;
	}


	public String getApplyBankNameLabel() {
		return applyBankNameLabel;
	}

	public void setApplyBankNameLabel(String applyBankNameLabel) {
		this.applyBankNameLabel = applyBankNameLabel;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	

	public String getMonthPayDay() {
		return monthPayDay;
	}

	public void setMonthPayDay(String monthPayDay) {
		this.monthPayDay = monthPayDay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyBankName() {
		return applyBankName;
	}

	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public BigDecimal getCompleteAmount() {
		return completeAmount;
	}

	public void setCompleteAmount(BigDecimal completeAmount) {
		this.completeAmount = completeAmount;
	}

	
	public BigDecimal getBuleAmount() {
		return buleAmount;
	}

	public void setBuleAmount(BigDecimal buleAmount) {
		this.buleAmount = buleAmount;
	}

	public String getDictDealType() {
		return dictDealType;
	}

	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}

	public String getMarkLabel() {
		return markLabel;
	}

	public String getMonthDay() {
		return monthDay;
	}

	public String getCommissioner() {
		return commissioner;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}

	public void setMarkLabel(String markLabel) {
		this.markLabel = markLabel;
	}

	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}

	public void setCommissioner(String commissioner) {
		this.commissioner = commissioner;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getRemindStatusFlag() {
		return remindStatusFlag;
	}

	public void setRemindStatusFlag(String remindStatusFlag) {
		this.remindStatusFlag = remindStatusFlag;
	}

	
	
}
