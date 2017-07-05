/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entity.PlatformRule.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:28:25
 */
package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 划扣平台规则
 * 
 * @Class Name PlatformRule
 * @author 王彬彬
 * @Create In 2015年12月21日
 */
public class PlatformRule extends DataEntity<PlatformRule> {

	private static final long serialVersionUID = 5295307586996661227L;

	// 平台ID
	private String dictDeductPlatformId;

	// 平台对应银行
	private String dictBankId;

	// 划扣接口方式（1实时 2批量）
	private String dictDeductInterfaceType;

	// 单笔限额
	private String singleLimitMoney;

	// 日限额
	private String dayLimitMoney;

	public String getDictDeductPlatformId() {
		return dictDeductPlatformId;
	}

	public String getDictBankId() {
		return dictBankId;
	}

	public String getDictDeductInterfaceType() {
		return dictDeductInterfaceType;
	}

	public String getSingleLimitMoney() {
		return singleLimitMoney;
	}

	public String getDayLimitMoney() {
		return dayLimitMoney;
	}

	public void setDictDeductPlatformId(String dictDeductPlatformId) {
		this.dictDeductPlatformId = dictDeductPlatformId;
	}

	public void setDictBankId(String dictBankId) {
		this.dictBankId = dictBankId;
	}

	public void setDictDeductInterfaceType(String dictDeductInterfaceType) {
		this.dictDeductInterfaceType = dictDeductInterfaceType;
	}

	public void setSingleLimitMoney(String singleLimitMoney) {
		this.singleLimitMoney = singleLimitMoney;
	}

	public void setDayLimitMoney(String dayLimitMoney) {
		this.dayLimitMoney = dayLimitMoney;
	}

	public PlatformRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlatformRule(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public PlatformRule(String dictDeductPlatformId, String dictBankId,
			String dictDeductInterfaceType) {
		super();
		this.dictDeductPlatformId = dictDeductPlatformId;
		this.dictBankId = dictBankId;
		this.dictDeductInterfaceType = dictDeductInterfaceType;
	}

}