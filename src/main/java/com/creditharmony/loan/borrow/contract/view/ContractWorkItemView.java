package com.creditharmony.loan.borrow.contract.view;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;
/**
 *封装待办列表 
 *@version 1.0
 *@author 张灏
 * 
 */
public class ContractWorkItemView extends BaseTaskItemView {
    // 合同版本号
    private String contractVersion; 
    // 合同编号
    private String contractCode;    
    // 客户姓名
    private String customerName;  
    // 共借人暂缺
    private String coborrowerName;
    // 省份
    private String addrProvince;
    // 城市
    private String addrCity;  
    // 门店
    private String contStoresId;
    // 门店名字
    private String contStoresName;
    // 借款产品
    private String borrowProduct;
    // 批借产品
    private String replyProductName;
    // 状态
    private String dictStatus; 
    // 批复金额
    private Double auditAmount;
    // 批复分期 
    private Integer hisAmountMonths;
    // 是否电销  
    private String loanIsPhone;
    // 进件时间
    private Date customerIntoTime;
    // 上调标识
    private String loanRaise;
    // 加急标识
    private String loanIsUrgent;
    // 标识
    private String borrowTrusteeFlag;
    
    // 签约平台
    private String signPlatform;
    // 是否为第一单（1：不是第一单 0：第一单） 
    private String isOld ; 
    // 上一个借款状态
    private String lastStatus;
    // 是否有退回的情况
    private String hasBack;
    
    private double money;
    
	public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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

    public String getAddrProvince() {
		return addrProvince;
	}

	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getContStoresId() {
		return contStoresId;
	}

	public void setContStoresId(String contStoresId) {
		this.contStoresId = contStoresId;
	}

	public String getContStoresName() {
        return contStoresName;
    }

    public void setContStoresName(String contStoresName) {
        this.contStoresName = contStoresName;
    }

    public String getBorrowProduct() {
		return borrowProduct;
	}

	public void setBorrowProduct(String borrowProduct) {
		this.borrowProduct = borrowProduct;
	}

	public String getDictStatus() {
		return dictStatus;
	}

	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}

	public Double getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}

	public Integer getHisAmountMonths() {
		return hisAmountMonths;
	}

	public void setHisAmountMonths(Integer hisAmountMonths) {
		this.hisAmountMonths = hisAmountMonths;
	}

	public String getReplyProductName() {
        return replyProductName;
    }

    public void setReplyProductName(String replyProductName) {
        this.replyProductName = replyProductName;
    }

    public String getLoanIsPhone() {
		return loanIsPhone;
	}

	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}

	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}

	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}

	public String getLoanRaise() {
		return loanRaise;
	}

	public void setLoanRaise(String loanRaise) {
		this.loanRaise = loanRaise;
	}

	public String getLoanIsUrgent() {
		return loanIsUrgent;
	}

	public void setLoanIsUrgent(String loanIsUrgent) {
		this.loanIsUrgent = loanIsUrgent;
	}

	public String getBorrowTrusteeFlag() {
		return borrowTrusteeFlag;
	}

	public void setBorrowTrusteeFlag(String borrowTrusteeFlag) {
		this.borrowTrusteeFlag = borrowTrusteeFlag;
	}

	public String getSignPlatform() {
		return signPlatform;
	}

	public void setSignPlatform(String signPlatform) {
		this.signPlatform = signPlatform;
	}

	public String getIsOld() {
		return isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getHasBack() {
		return hasBack;
	}

	public void setHasBack(String hasBack) {
		this.hasBack = hasBack;
	}

	
}
