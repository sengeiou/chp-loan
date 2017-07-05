/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entity.PaybackSplitCount.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:28:25
 */
package com.creditharmony.loan.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class PaybackSplitCount extends DataEntity<PaybackSplitCount> {

	// 批次号
	private String splitPch;
	// 拆分金额
	private BigDecimal splitAmount;
	// 拆分的笔数
	private Integer splitBishu;

	// 拆分时间
	private Date splitDate;

	public String getId() {
		return id;
	}

	public String getSplitPch() {
		return splitPch;
	}

	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public Integer getSplitBishu() {
		return splitBishu;
	}

	public Date getSplitDate() {
		return splitDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSplitPch(String splitPch) {
		this.splitPch = splitPch;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public void setSplitBishu(Integer splitBishu) {
		this.splitBishu = splitBishu;
	}

	public void setSplitDate(Date splitDate) {
		this.splitDate = splitDate;
	}

}