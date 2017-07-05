package com.creditharmony.loan.borrow.restrictedInlet.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 
 * 限制进件 高危线设置
 * @Class Name RestrictedInlet
 * @author 管洪昌
 * @Create In 2016年4月20日
 */
@SuppressWarnings("serial")
public class RestrictedInlet extends DataEntity<RestrictedInlet>{
   //ID    
  private String id;
  //省分公司编码
  private String provinceCode;
  //省分公司名称
  private String provinceName;
  //业务大区编码
  private String lgareCode;
  //业务大区名称
  private String lgareName;
  //客户逾期高危线
  private BigDecimal customerNum;
  //客户逾期高危线
  private String customerNumLable;
  //团队经理M1逾期高危线
  private BigDecimal termNum;
  private String termNumLable;
  //门店M1逾期高危线路
  private BigDecimal storeNum;
  //门店M1逾期高危线路
  private String storeNumLable;
  //门店编号
  private String orgCode;
  //门店名称
  private String orgName;
  //是否限制进件
  private String sfJj; 
  //当前M1逾期率
  private BigDecimal m1YqlCurrent;
  //当前M1逾期率
  private String m1YqlCurrentLable;

//高危线标准
  private String highStandard;
  //高危线标准
  private String highStandardLable;
//高危线标准
  private String areaName;
//自定义M1门店逾期率高危线
  
  private BigDecimal m1Yql;
  //当前M1逾期率

//团队经理/客服经理区分
  private String zkbjType;
//团队经理/客服经理区分
  private String zkbjTypeLable;
  
//姓名
  private String teamName;
//是否被限制进件
  private String sfJjLable;
  private String m1YqlLable;
  

public String getZkbjTypeLable() {
	return zkbjTypeLable;
}
public void setZkbjTypeLable(String zkbjTypeLable) {
	this.zkbjTypeLable = zkbjTypeLable;
}
public String getCustomerNumLable() {
	return customerNumLable;
}
public void setCustomerNumLable(String customerNumLable) {
	this.customerNumLable = customerNumLable;
}
public String getTermNumLable() {
	return termNumLable;
}
public void setTermNumLable(String termNumLable) {
	this.termNumLable = termNumLable;
}
public String getStoreNumLable() {
	return storeNumLable;
}
public void setStoreNumLable(String storeNumLable) {
	this.storeNumLable = storeNumLable;
}
public String getM1YqlCurrentLable() {
	return m1YqlCurrentLable;
}
public void setM1YqlCurrentLable(String m1YqlCurrentLable) {
	this.m1YqlCurrentLable = m1YqlCurrentLable;
}
  public String getHighStandardLable() {
		return highStandardLable;
	}
	public void setHighStandardLable(String highStandardLable) {
		this.highStandardLable = highStandardLable;
	}
  public String getM1YqlLable() {
	return m1YqlLable;
}
public void setM1YqlLable(String m1YqlLable) {
	this.m1YqlLable = m1YqlLable;
}
public String getSfJjLable() {
	return sfJjLable;
}
public void setSfJjLable(String sfJjLable) {
	this.sfJjLable = sfJjLable;
}
public String getZkbjType() {
	return zkbjType;
}
public void setZkbjType(String zkbjType) {
	this.zkbjType = zkbjType;
}
public String getTeamName() {
	return teamName;
}
public void setTeamName(String teamName) {
	this.teamName = teamName;
}

public BigDecimal getM1Yql() {
	return m1Yql;
}
public void setM1Yql(BigDecimal m1Yql) {
	this.m1Yql = m1Yql;
}
public String getAreaName() {
	return areaName;
}
public void setAreaName(String areaName) {
	this.areaName = areaName;
}
public String getHighStandard() {
	return highStandard;
}
public void setHighStandard(String highStandard) {
	this.highStandard = highStandard;
}
public String getOrgCode() {
	return orgCode;
}
public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
}
public String getOrgName() {
	return orgName;
}
public void setOrgName(String orgName) {
	this.orgName = orgName;
}
public String getSfJj() {
	return sfJj;
}
public void setSfJj(String sfJj) {
	this.sfJj = sfJj;
}
public BigDecimal getM1YqlCurrent() {
	return m1YqlCurrent;
}
public void setM1YqlCurrent(BigDecimal m1YqlCurrent) {
	this.m1YqlCurrent = m1YqlCurrent;
}

//------------------------------
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getProvinceCode() {
	return provinceCode;
}
public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
}
public String getProvinceName() {
	return provinceName;
}
public void setProvinceName(String provinceName) {
	this.provinceName = provinceName;
}
public String getLgareCode() {
	return lgareCode;
}
public void setLgareCode(String lgareCode) {
	this.lgareCode = lgareCode;
}
public String getLgareName() {
	return lgareName;
}
public void setLgareName(String lgareName) {
	this.lgareName = lgareName;
}
public BigDecimal getCustomerNum() {
	return customerNum;
}
public void setCustomerNum(BigDecimal customerNum) {
	this.customerNum = customerNum;
}
public BigDecimal getTermNum() {
	return termNum;
}
public void setTermNum(BigDecimal termNum) {
	this.termNum = termNum;
}
public BigDecimal getStoreNum() {
	return storeNum;
}
public void setStoreNum(BigDecimal storeNum) {
	this.storeNum = storeNum;
}
  
  
  
  
	
	
	
}
