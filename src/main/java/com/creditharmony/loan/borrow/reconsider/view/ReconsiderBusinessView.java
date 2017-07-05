/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.viewReconsiderBusinessView.java
 * @Create By 张灏
 * @Create In 2015年12月24日 下午3:05:09
 */
package com.creditharmony.loan.borrow.reconsider.view;

import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.reconsider.entity.ex.ReconsiderApplyEx;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * @Class Name ReconsiderBusinessView
 * @author 张灏
 * @Create In 2015年12月24日
 */
public class ReconsiderBusinessView extends BaseBusinessView {

    // 借款客户信息
    private LoanCustomer loanCustomer;
    // 借款信息
    private LoanInfo loanInfo;
    // 共借人信息
    private List<LoanCoborrower> coborrowers;
    // 复议结果
    private ReconsiderApply reconsiderApply;
    // 复议结果
    private ReconsiderApplyEx reconsiderApplyEx;
    // 信借ApplyId
    private String oldApplyId;
    // 借款状态
    private String dictLoanStatus;
    // 借款状态Code
    private String dictLoanStatusCode;
    // 标识
    private String loanFlag;
    // 标识 Code
    private String loanFlagCode;
    // 签约平台
    private String signingPlatformName;
    // 影像插件地址
    private String imageUrl;
    //
    private String dictLoanType;
    //
    private String dictLoanTypeName;
    // 门店拒绝原因
    private String rejectReason;
    // 是否生僻字
    private String bankIsRareword;
    // 门店Id
    private String storeOrgId;
    // 门店Code
    private String orgCode;
    // 门店名称
    private String orgName;
    // 客服Code
    private String agentCode;
    // 客服名称 
    private String agentName;
    // 团队经理Code
    private String loanTeamManagerCode;
    // 团队经理
    private String loanTeamManagerName;
    // 客户经理Code
    private String loanManagerCode;
    // 客户经理
    private String loanManagerName;
    // 模式
    private String model;
    // 电销组织机构
    private String consTelesalesOrgcode;
    // 操作类型 0 saveData
    private String operType;
    // 超时标识
    private String timeOutFlag;
    //合同版本号
    private String contractVersion;
    //节点标识
    private String nodeFlag;
    
    
    
    public String getNodeFlag() {
		return nodeFlag;
	}

	public void setNodeFlag(String nodeFlag) {
		this.nodeFlag = nodeFlag;
	}
    
    public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * @return the dictLoanType
     */
    public String getDictLoanType() {
        return dictLoanType;
    }

    /**
     * @param dictLoanType
     *            the String dictLoanType to set
     */
    public void setDictLoanType(String dictLoanType) {
        this.dictLoanType = dictLoanType;
    }

    /**
     * @return the dictLoanTypeName
     */
    public String getDictLoanTypeName() {
        return dictLoanTypeName;
    }

    /**
     * @param dictLoanTypeName
     *            the String dictLoanTypeName to set
     */
    public void setDictLoanTypeName(String dictLoanTypeName) {
        this.dictLoanTypeName = dictLoanTypeName;
    }

    public String getSigningPlatformName() {
        return signingPlatformName;
    }

    public void setSigningPlatformName(String signingPlatformName) {
        this.signingPlatformName = signingPlatformName;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     *            the String imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LoanCustomer getLoanCustomer() {
        return loanCustomer;
    }

    public void setLoanCustomer(LoanCustomer loanCustomer) {
        this.loanCustomer = loanCustomer;
    }

    public LoanInfo getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(LoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }

    public List<LoanCoborrower> getCoborrowers() {
        return coborrowers;
    }

    public void setCoborrowers(List<LoanCoborrower> coborrowers) {
        this.coborrowers = coborrowers;
    }

    public ReconsiderApply getReconsiderApply() {

        return reconsiderApply;
    }

    public void setReconsiderApply(ReconsiderApply reconsiderApply) {

        this.reconsiderApply = reconsiderApply;
    }

    public ReconsiderApplyEx getReconsiderApplyEx() {

        return reconsiderApplyEx;
    }

    public void setReconsiderApplyEx(ReconsiderApplyEx reconsiderApplyEx) {

        this.reconsiderApplyEx = reconsiderApplyEx;
    }

    public String getOldApplyId() {

        return oldApplyId;
    }

    public void setOldApplyId(String oldApplyId) {

        this.oldApplyId = oldApplyId;
    }

    public String getDictLoanStatus() {
        return dictLoanStatus;
    }

    public void setDictLoanStatus(String dictLoanStatus) {
        this.dictLoanStatus = dictLoanStatus;
    }

    public String getDictLoanStatusCode() {
        return dictLoanStatusCode;
    }

    public void setDictLoanStatusCode(String dictLoanStatusCode) {
        this.dictLoanStatusCode = dictLoanStatusCode;
    }

    public String getLoanFlag() {
        return loanFlag;
    }

    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag;
    }

    public String getLoanFlagCode() {
        return loanFlagCode;
    }

    public void setLoanFlagCode(String loanFlagCode) {
        this.loanFlagCode = loanFlagCode;
    }

    /**
     * @return the bankIsRareword
     */
    public String getBankIsRareword() {
        return bankIsRareword;
    }

    /**
     * @param bankIsRareword
     *            the String bankIsRareword to set
     */
    public void setBankIsRareword(String bankIsRareword) {
        this.bankIsRareword = bankIsRareword;
    }

    /**
     * @return the storeOrgId
     */
    public String getStoreOrgId() {
        return storeOrgId;
    }

    /**
     * @param storeOrgId
     *            the String storeOrgId to set
     */
    public void setStoreOrgId(String storeOrgId) {
        this.storeOrgId = storeOrgId;
    }

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode
     *            the String orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName
     *            the String orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the loanTeamManagerCode
     */
    public String getLoanTeamManagerCode() {
        return loanTeamManagerCode;
    }

    /**
     * @param loanTeamManagerCode the String loanTeamManagerCode to set
     */
    public void setLoanTeamManagerCode(String loanTeamManagerCode) {
        this.loanTeamManagerCode = loanTeamManagerCode;
    }

    /**
     * @return the loanTeamManagerName
     */
    public String getLoanTeamManagerName() {
        return loanTeamManagerName;
    }

    /**
     * @param loanTeamManagerName the String loanTeamManagerName to set
     */
    public void setLoanTeamManagerName(String loanTeamManagerName) {
        this.loanTeamManagerName = loanTeamManagerName;
    }

    /**
     * @return the loanManagerCode
     */
    public String getLoanManagerCode() {
        return loanManagerCode;
    }

    /**
     * @param loanManagerCode the String loanManagerCode to set
     */
    public void setLoanManagerCode(String loanManagerCode) {
        this.loanManagerCode = loanManagerCode;
    }

    /**
     * @return the loanManagerName
     */
    public String getLoanManagerName() {
        return loanManagerName;
    }

    /**
     * @param loanManagerName the String loanManagerName to set
     */
    public void setLoanManagerName(String loanManagerName) {
        this.loanManagerName = loanManagerName;
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

    /**
     * @return the operType
     */
    public String getOperType() {
        return operType;
    }

    /**
     * @param operType the String operType to set
     */
    public void setOperType(String operType) {
        this.operType = operType;
    }

    /**
     * @return the timeOutFlag
     */
    public String getTimeOutFlag() {
        return timeOutFlag;
    }

    /**
     * @param timeOutFlag the String timeOutFlag to set
     */
    public void setTimeOutFlag(String timeOutFlag) {
        this.timeOutFlag = timeOutFlag;
    }
}
