/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.entity.exReconsiderApplyEx.java
 * @Create By 张灏
 * @Create In 2015年12月24日 下午6:28:15
 */
package com.creditharmony.loan.borrow.reconsider.entity.ex;

import java.util.Date;


/**
 * @Class Name ReconsiderApplyEx
 * @author 张灏
 * @Create In 2015年12月24日
 */
public class ReconsiderApplyEx{
  
    // 借款编号
    private String loanCode;
    // 证件号码
    private String identityCode;
    // 客户姓名 
    private String customerName;
    // 共借人
    private String coborrowerName;
    // 省份Code
    private String provinceCode;
    // 省份名称
    private String provinceName;
    // 城市Code
    private String cityCode;
    // 城市名称
    private String cityName;
    // 门店Id
    private String storeOrgId;
    // 门店Code
    private String storeCode;
    // 门店名称
    private String storeName;
    // 申请产品Code
    private String applyProductCode;
    // 申请产品名称
    private String applyProductName;
    // 申请金额
    private Double applyMoney;
    // 申请期限
    private Integer applyMonth;
    // 团队经理(Id)
    private String teamManagerCode;
    // 团队经理（名称）
    private String teamManagerName;
    // 客户经理(Name)
    private String customerManagerName;
    // 客户经理(Code)
    private String customerManagerCode;
    // 进件时间
    private Date intoLoanTime;
    // 上调标识
    private String raiseFlag;
    // 加急标识
    private String urgentFlag;
    // 是否电销
    private String telesalesFlag;
    // 追加借标识
    private String additionalFlag;
    // 签约平台
    private String signPlatform;
    // 签约平台(Name)
    private String signPlatformName;
    //新旧申请表标识
    private String loanInfoOldOrNewFlag;
    //出汇金时间
    private Date outtoLoanTime;
    
    public Date getOuttoLoanTime() {
		return outtoLoanTime;
	}
	public void setOuttoLoanTime(Date outtoLoanTime) {
		this.outtoLoanTime = outtoLoanTime;
	}
    
    public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
   
    public String getLoanCode() {
        return loanCode;
    }
    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }
    public String getIdentityCode() {
        return identityCode;
    }
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCoborrowerName() {
        return coborrowerName;
    }
    public void setCoborrowerName(String coborrowerName) {
        this.coborrowerName = coborrowerName;
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
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getStoreOrgId() {
        return storeOrgId;
    }
    public void setStoreOrgId(String storeOrgId) {
        this.storeOrgId = storeOrgId;
    }
    public String getStoreCode() {
        return storeCode;
    }
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getApplyProductCode() {
        return applyProductCode;
    }
    public void setApplyProductCode(String applyProductCode) {
        this.applyProductCode = applyProductCode;
    }
    public String getApplyProductName() {
        return applyProductName;
    }
    public void setApplyProductName(String applyProductName) {
        this.applyProductName = applyProductName;
    }
    public Double getApplyMoney() {
        return applyMoney;
    }
    public void setApplyMoney(Double applyMoney) {
        this.applyMoney = applyMoney;
    }
    public Integer getApplyMonth() {
        return applyMonth;
    }
    public void setApplyMonth(Integer applyMonth) {
        this.applyMonth = applyMonth;
    }
    public String getTeamManagerCode() {
        return teamManagerCode;
    }
    public void setTeamManagerCode(String teamManagerCode) {
        this.teamManagerCode = teamManagerCode;
    }
    public String getTeamManagerName() {
        return teamManagerName;
    }
    public void setTeamManagerName(String teamManagerName) {
        this.teamManagerName = teamManagerName;
    }
    public String getCustomerManagerName() {
        return customerManagerName;
    }
    public void setCustomerManagerName(String customerManagerName) {
        this.customerManagerName = customerManagerName;
    }
    public String getCustomerManagerCode() {
        return customerManagerCode;
    }
    public void setCustomerManagerCode(String customerManagerCode) {
        this.customerManagerCode = customerManagerCode;
    }
    public Date getIntoLoanTime() {
        return intoLoanTime;
    }
    public void setIntoLoanTime(Date intoLoanTime) {
        this.intoLoanTime = intoLoanTime;
    }
    public String getRaiseFlag() {
        return raiseFlag;
    }
    public void setRaiseFlag(String raiseFlag) {
        this.raiseFlag = raiseFlag;
    }
    public String getUrgentFlag() {
        return urgentFlag;
    }
    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag;
    }
    public String getTelesalesFlag() {
        return telesalesFlag;
    }
    public void setTelesalesFlag(String telesalesFlag) {
        this.telesalesFlag = telesalesFlag;
    }
    public String getAdditionalFlag() {
        return additionalFlag;
    }
    public void setAdditionalFlag(String additionalFlag) {
        this.additionalFlag = additionalFlag;
    }
    public String getSignPlatform() {
        return signPlatform;
    }
    public void setSignPlatform(String signPlatform) {
        this.signPlatform = signPlatform;
    }
    public String getSignPlatformName() {
        return signPlatformName;
    }
    public void setSignPlatformName(String signPlatformName) {
        this.signPlatformName = signPlatformName;
    }
}
