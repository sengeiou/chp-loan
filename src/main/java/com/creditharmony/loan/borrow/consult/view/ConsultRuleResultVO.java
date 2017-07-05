package com.creditharmony.loan.borrow.consult.view;

import com.creditharmony.loan.common.vo.AbstractServiceVO;

/**
 * 咨询规则返回对象
 * 
 * @author 任志远
 * @date 2017年3月16日
 */
public class ConsultRuleResultVO extends AbstractServiceVO<ConsultRuleResultVO> {

	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 证件有效期起始日期
	 */
	private String idStartDayStr;
	/**
	 * 证件有效期结束日期
	 */
	private String idEndDayStr;
	/**
	 * 所属行业
	 */
	private String dictCompIndustry;
	/**
	 * 客户性别
	 */
	private String customerSex;
	

	public ConsultRuleResultVO() {
	}
	
	public ConsultRuleResultVO(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public ConsultRuleResultVO(String code, String msg) {
		this.code = code;
		this.msg = msg;
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

	public String getIdStartDayStr() {
		return idStartDayStr;
	}

	public void setIdStartDayStr(String idStartDayStr) {
		this.idStartDayStr = idStartDayStr;
	}

	public String getIdEndDayStr() {
		return idEndDayStr;
	}

	public void setIdEndDayStr(String idEndDayStr) {
		this.idEndDayStr = idEndDayStr;
	}

	public String getDictCompIndustry() {
		return dictCompIndustry;
	}

	public void setDictCompIndustry(String dictCompIndustry) {
		this.dictCompIndustry = dictCompIndustry;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	
}
