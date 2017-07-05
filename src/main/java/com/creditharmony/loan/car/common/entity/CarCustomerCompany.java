package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 工作信息
 * @Class Name CartCustomerCompany
 * @author 安子帅
 * @Create In 2016年1月22日
 */
public class CarCustomerCompany extends DataEntity<CarCustomerCompany> {
	 
	private static final long serialVersionUID = -4652592945732322671L;
	
	 private String id;   // ID
	 private String loanCode ;   //借款编码
	 private String rCustomerCoborrowerCode;   //关联CODE(主借人，共借人)
	 private String dictCustomerType;   // 类型（主借人0\共借人1）
	 private String companyName;   // 单位名称
	 private String dictCompanyProvince;   //单位省
	 private String dictCompanyCity;   //单位市
	 private String dictCompanyArea;   // 单位区
	 private String companyAddress;   //单位地址
	 private String dictDepartment;   //所属部门
	 private Date establishedTime;   //成立日期
	 private String workTelephone;   // 单位电话
	 private String dictPositionLevel;   // 职位级别
	 private BigDecimal monthlyPay;   //月均薪资
	 private String isOtherRevenue;   // 其他收入
	 private String dictUnitNature;   //单位性质
	 private String dictEnterpriseNature;   // 企业性质
	 private Date firstServiceDate;   //起始服务日期
	 private String createBy;   //  创建人
	 private Date createTime ;   // 创建时间
	 private String modifyBy;   // 修改人
	 private Date modifyTime;   // 最后修改时间
	 private BigDecimal incomMoney;//收入金额
	 
	 
	public BigDecimal getIncomMoney() {
		return incomMoney;
	}
	public void setIncomMoney(BigDecimal incomMoney) {
		this.incomMoney = incomMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getrCustomerCoborrowerCode() {
		return rCustomerCoborrowerCode;
	}
	public void setrCustomerCoborrowerCode(String rCustomerCoborrowerCode) {
		this.rCustomerCoborrowerCode = rCustomerCoborrowerCode;
	}
	public String getDictCustomerType() {
		return dictCustomerType;
	}
	public void setDictCustomerType(String dictCustomerType) {
		this.dictCustomerType = dictCustomerType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDictCompanyProvince() {
		return dictCompanyProvince;
	}
	public void setDictCompanyProvince(String dictCompanyProvince) {
		this.dictCompanyProvince = dictCompanyProvince;
	}
	public String getDictCompanyCity() {
		return dictCompanyCity;
	}
	public void setDictCompanyCity(String dictCompanyCity) {
		this.dictCompanyCity = dictCompanyCity;
	}
	public String getDictCompanyArea() {
		return dictCompanyArea;
	}
	public void setDictCompanyArea(String dictCompanyArea) {
		this.dictCompanyArea = dictCompanyArea;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getDictDepartment() {
		return dictDepartment;
	}
	public void setDictDepartment(String dictDepartment) {
		this.dictDepartment = dictDepartment;
	}
	public Date getEstablishedTime() {
		return establishedTime;
	}
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	public String getWorkTelephone() {
		return workTelephone;
	}
	public void setWorkTelephone(String workTelephone) {
		this.workTelephone = workTelephone;
	}
	public String getDictPositionLevel() {
		return dictPositionLevel;
	}
	public void setDictPositionLevel(String dictPositionLevel) {
		this.dictPositionLevel = dictPositionLevel;
	}
	public BigDecimal getMonthlyPay() {
		return monthlyPay;
	}
	public void setMonthlyPay(BigDecimal monthlyPay) {
		this.monthlyPay = monthlyPay;
	}
	public String getIsOtherRevenue() {
		return isOtherRevenue;
	}
	public void setIsOtherRevenue(String isOtherRevenue) {
		this.isOtherRevenue = isOtherRevenue;
	}
	public String getDictUnitNature() {
		return dictUnitNature;
	}
	public void setDictUnitNature(String dictUnitNature) {
		this.dictUnitNature = dictUnitNature;
	}
	public String getDictEnterpriseNature() {
		return dictEnterpriseNature;
	}
	public void setDictEnterpriseNature(String dictEnterpriseNature) {
		this.dictEnterpriseNature = dictEnterpriseNature;
	}
	public Date getFirstServiceDate() {
		return firstServiceDate;
	}
	public void setFirstServiceDate(Date firstServiceDate) {
		this.firstServiceDate = firstServiceDate;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
}
