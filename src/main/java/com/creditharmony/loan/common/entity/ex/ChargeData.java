/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entity.exChargeData.java
 * @Create By 王彬彬
 * @Create In 2016年2月3日 下午9:46:02
 */
package com.creditharmony.loan.common.entity.ex;

import java.math.BigDecimal;

/**
 * 冲抵暂存数据用
 * 
 * @Class Name ChargeData
 * @author 王彬彬
 * @Create In 2016年2月3日
 */
public class ChargeData {
	
	/**
	 * 还款主表ID
	 */
	private String rPaybackId;

	/**
	 * 期供表ID
	 */
	private String monthId;

	/**
	 * 冲抵后剩余可冲抵金额
	 */
	private BigDecimal remainderAmount;

	/**
	 * 冲抵开始前蓝补金额
	 */
	private BigDecimal oldRRemainderAmount;

	/**
	 * 冲抵到第几期
	 */
	private Integer currentMonth;

	/**
	 * 期供最大期
	 */
	private Integer maxMonth;

	/**
	 * 当月第几期
	 */
	private Integer thisMonth;

	/**
	 * 当前还款期是否预期
	 */
	private String isOverdue;

	/**
	 * 合同号
	 */
	private String contractCode;

	/**
	 * 关联期供表ID
	 */
	public String getMonthId() {
		return monthId;
	}

	/**
	 * 关联期供表ID
	 */
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	/**
	 * 冲抵后剩余可冲抵金额 2016年2月3日 By 王彬彬
	 * 
	 * @return 冲抵后剩余可冲抵金额
	 */
	public BigDecimal getRemainderAmount() {
		return remainderAmount;
	}

	/**
	 * 冲抵后剩余可冲抵金额 2016年2月3日 By 王彬彬
	 * 
	 * @param remainderAmount冲抵后剩余可冲抵金额
	 */
	public void setRemainderAmount(BigDecimal remainderAmount) {
		this.remainderAmount = remainderAmount;
	}

	/**
	 * 冲抵到第几期
	 */
	public Integer getCurrentMonth() {
		return currentMonth;
	}

	/**
	 * 期供最大期
	 */
	public Integer getMaxMonth() {
		return maxMonth;
	}

	/**
	 * 当月第几期
	 */
	public Integer getThisMonth() {
		return thisMonth;
	}

	/**
	 * 冲抵到第几期
	 */
	public void setCurrentMonth(Integer currentMonth) {
		this.currentMonth = currentMonth;
	}

	/**
	 * 期供最大期
	 */
	public void setMaxMonth(Integer maxMonth) {
		this.maxMonth = maxMonth;
	}

	/**
	 * 当月第几期
	 */
	public void setThisMonth(Integer thisMonth) {
		this.thisMonth = thisMonth;
	}

	/**
	 * 当前还款期是否预期
	 */
	public String getIsOverdue() {
		return isOverdue;
	}

	/**
	 * 当前还款期是否预期
	 */
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	/**
	 * 冲抵开始前金额
	 */
	public BigDecimal getOldRRemainderAmount() {
		return oldRRemainderAmount;
	}

	/**
	 * 冲抵开始前金额
	 */
	public void setOldRRemainderAmount(BigDecimal oldRRemainderAmount) {
		this.oldRRemainderAmount = oldRRemainderAmount;
	}

	/**
	 * 还款主表ID
	 * 2016年3月6日
	 * By 王彬彬
	 * @return  还款主表ID
	 */
	public String getrPaybackId() {
		return rPaybackId;
	}

	/**
	 *  还款主表ID
	 * 2016年3月6日
	 * By 王彬彬
	 * @param rPaybackId  还款主表ID
	 */
	public void setrPaybackId(String rPaybackId) {
		this.rPaybackId = rPaybackId;
	}
}
