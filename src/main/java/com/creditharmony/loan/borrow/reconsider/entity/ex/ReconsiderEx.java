/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.entity.exReconsiderEx.java
 * @Create By 张灏
 * @Create In 2015年12月25日 下午5:32:05
 */
package com.creditharmony.loan.borrow.reconsider.entity.ex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name ReconsiderEx
 * @author 张灏
 * @Create In 2015年12月25日
 */
@SuppressWarnings("serial")
public class ReconsiderEx extends DataEntity<ReconsiderEx> {
  
    // 查询状态
    private List<String> dictLoanStatusList = new ArrayList<String>();
    // 查询状态
    private String[] queryDictStatus={};
    // 流程Id
    private String applyId;
    // 借款编号
    private String loanCode;
    // 证件号码
    private String identityCode;
    // 客户姓名
    private String customerName;
    // 共借人
    private String coborrowerName;
    // 省份(Code)
    private String provinceCode;
    // 省份(名称)
    private String provinceName;
    // 城市(Code)
    private String cityCode;
    // 城市(名称)
    private String cityName;
    // 门店Id
    private String storeOrgId;
    // 门店Code
    private String storeCode;
    // 门店(名称)
    private String storeName;
    // 申请产品(Code)
    private String applyProductCode;
    // 申请产品(名称)
    private String applyProductName;
    // 申请金额
    private Double applyMoney;
    // 申请期限
    private Integer applyMonth;
    // 批复产品（Code）
    private String replyProductCode;
    // 批复产品
    private String replyProductName;
    // 借款状态
    private String loanStatusName;
    // 借款状态(Code)
    private String loanStatusCode;
    // 批复金额
    private Double replyMoney;
    // 批复分期(批借期限)
    private Integer replyMonth;
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
    // 渠道标识
    private String channelCode;
    // 渠道标识Name
    private String channelName;
    // 合同版本号
    private String contractVersion;
    // 合同编号
    private String contractCode;
    // 签约平台
    private String signPlatform;
    // 签约平台(Name)
    private String signPlatformName;
    // 查询起始日期
    private Date queryDay;
    // 确认签署查询起始日期
    private Date confirmQueryDay;
    // 确认签署节点
    private String confirmCode;
    // 结清再贷/首次借款
    private String dictLoanType;
    // 产品类别
    private String productType;
    // 产品启用状态
    private String productStatus;
    // 客服Code
    private String agentCode;
    // 客服姓名
    private String agentName;
    // 查询类型
    private String queryType;
    // 模式
    private String model;
    // 模式名字
    private String modelName;
    // 电销组织
    private String consTelesalesOrgcode;
    //新旧申请表标识
    private String loanInfoOldOrNewFlag;
    //客服人员（电销用）
    private String consServiceUserCode;
    
    //出汇金时间
    private Date outtoLoanTime;
    //最优自然保证人
    private String bestCoborrower;
    
    public Date getOuttoLoanTime() {
		return outtoLoanTime;
	}
	public void setOuttoLoanTime(Date outtoLoanTime) {
		this.outtoLoanTime = outtoLoanTime;
	}
    public String getConsServiceUserCode() {
		return consServiceUserCode;
	}
	public void setConsServiceUserCode(String consServiceUserCode) {
		this.consServiceUserCode = consServiceUserCode;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public List<String> getDictLoanStatusList() {
        return dictLoanStatusList;
    }
    public void setDictLoanStatusList(List<String> dictLoanStatusList) {
        this.dictLoanStatusList = dictLoanStatusList;
    }
    public String getApplyId() {
        return applyId;
    }
    public void setApplyId(String applyId) {
        this.applyId = applyId;
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
    public String getReplyProductCode() {
        return replyProductCode;
    }
    public void setReplyProductCode(String replyProductCode) {
        this.replyProductCode = replyProductCode;
    }
    public String getReplyProductName() {
        return replyProductName;
    }
    public void setReplyProductName(String replyProductName) {
        this.replyProductName = replyProductName;
    }
    public String getLoanStatusName() {
        return loanStatusName;
    }
    public void setLoanStatusName(String loanStatusName) {
        this.loanStatusName = loanStatusName;
    }
    public String getLoanStatusCode() {
        return loanStatusCode;
    }
    public void setLoanStatusCode(String loanStatusCode) {
        this.loanStatusCode = loanStatusCode;
    }
    public Double getReplyMoney() {
        return replyMoney;
    }
    public void setReplyMoney(Double replyMoney) {
        this.replyMoney = replyMoney;
    }
    public Integer getReplyMonth() {
        return replyMonth;
    }
    public void setReplyMonth(Integer replyMonth) {
        this.replyMonth = replyMonth;
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
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getContractVersion() {
        return contractVersion;
    }
    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
    }
    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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
    public Date getQueryDay() {
        return queryDay;
    }
    public void setQueryDay(Date queryDay) {
        this.queryDay = queryDay;
    }
    public String getConfirmCode() {
        return confirmCode;
    }
    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }
    /**
     * @return the dictLoanType
     */
    public String getDictLoanType() {
        return dictLoanType;
    }
    /**
     * @param dictLoanType the String dictLoanType to set
     */
    public void setDictLoanType(String dictLoanType) {
        this.dictLoanType = dictLoanType;
    }
    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }
    /**
     * @param productType the String productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }
    /**
     * @return the productStatus
     */
    public String getProductStatus() {
        return productStatus;
    }
    /**
     * @param productStatus the String productStatus to set
     */
    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
    /**
     * @return the agentCode
     */
    public String getAgentCode() {
        return agentCode;
    }
    /**
     * @param agentCode the String agentCode to set
     */
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    /**
     * @return the agentName
     */
    public String getAgentName() {
        return agentName;
    }
    /**
     * @param agentName the String agentName to set
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    /**
     * @return the queryDictStatus
     */
    public String[] getQueryDictStatus() {
        return queryDictStatus;
    }
    /**
     * @param queryDictStatus the String [] queryDictStatus to set
     */
    public void setQueryDictStatus(String[] queryDictStatus) {
        this.queryDictStatus = queryDictStatus;
    }
    /**
     * @return the queryType
     */
    public String getQueryType() {
        return queryType;
    }
    /**
     * @param queryType the String queryType to set
     */
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }
    /**
     * @param model the String model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }
    /**
     * @param modelName the String modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    /**
     * @return the confirmQueryDay
     */
    public Date getConfirmQueryDay() {
        return confirmQueryDay;
    }
    /**
     * @param confirmQueryDay the Date confirmQueryDay to set
     */
    public void setConfirmQueryDay(Date confirmQueryDay) {
        this.confirmQueryDay = confirmQueryDay;
    }
    /**
     * @return the consTelesalesOrgcode
     */
    public String getConsTelesalesOrgcode() {
        return consTelesalesOrgcode;
    }
    /**
     * @param consTelesalesOrgcode the String consTelesalesOrgcode to set
     */
    public void setConsTelesalesOrgcode(String consTelesalesOrgcode) {
        this.consTelesalesOrgcode = consTelesalesOrgcode;
    }
	public String getBestCoborrower() {
		return bestCoborrower;
	}
	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
	}
    
}
