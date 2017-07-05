package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;


/**
 * 借款人和合同信息
 * @Class Name ContractAndPersonInfo
 * @author shangjunwei
 * @Create In 2016年3月3日
 */
@SuppressWarnings("serial")
public class ContractAndPersonInfo  extends DataEntity<ContractAndPersonInfo>{
		// 借款编码
    	private String loanCode;
	
    	//合同编号 contract
    	private String contractCode;
		//合同版本号 contract
	 	private String contractVersion;
	 	//合同版本号名称
	 	private String contractVersionLabel;
		//共借人loancoborrower
	 	private String coboName;
	 	//客户姓名 loaninfo
	 	private String loanCustomerName;
	 	//省份  customer
	 	private String storeProviceName;
	 	//城市 customer
	 	private String storeCityName; 
	 	// 门店 loaninfo
	 	private String storeCode;
	 	// 门店
	 	private String[] storeName;
	 
	 	private String[] storeOrgId;
	 	//状态 loaninfo
	 	private String dictLoanStatus;
		//状态 loaninfo
	 	private String loanStatus;
	 	//产品 loaninfo
	 	private String productType;
	
	 	//批复金额 loaninfo
	 	private BigDecimal loanAuditAmount;
	 	//批复分期 loaninfo
	 	private Integer loanAuditMonth;
	 	//加急标识 loaninfo
	 	private String loanUrgentFlag;
	 	//团队经理 loaninfo
	 	private String loanTeamManagerName;
	 	//销售人员 loaninfo
	 	private String loanManagerName;
	 	//进件时间 loaninfo
	 	private Date customerIntoTime;
	 	//标识是否电销 customer
	 	private String loanIsPhone;
	 
	 
	 	// 标示loaninfo
	    private String loanFlag;
		// 是否追加 loaninfo
	    private String dictIsAdditional;
	    // 来源版本 1.0 2.0 3.0 loaninfo
	    private String dictSourceType;
	    // 证件号码 customer
	    private String customerCertNum;
	    // 文档ID
	    private String docId;
	    
	    //借款模式
	    private String model;
	    //借款模式名称
	    private String modelLabel;
	 
	    //门店申请冻结标识
	    private String frozenCode;
	    // 签订合同数量
	    private String signCount;
	    
	    private String riskLevel;
	    	    
	    //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面   loaninfo 
	    private String loanInfoOldOrNewFlag;
	    
	    //是否冻结标识
	    private String loanFrozenFlag;
	    
	    //回访状态
	    private String[] revisitStatuss;
	    //回访状态
	    private String revisitStatus;
	    
	    private String revisitStatusName;
	    
	    private String revisitStatussParm;
	    //最优自然保证人
	    private String bestCoborrower;
	    //放款时间
	    private Date lendingTime;
	    
		public String getFrozenCode() {
			return frozenCode;
		}
		public void setFrozenCode(String frozenCode) {
			this.frozenCode = frozenCode;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public String getModelLabel() {
			return modelLabel;
		}
		public void setModelLabel(String modelLabel) {
			this.modelLabel = modelLabel;
		}
		public String getContractVersionLabel() {
			return contractVersionLabel;
		}
		public void setContractVersionLabel(String contractVersionLabel) {
			this.contractVersionLabel = contractVersionLabel;
		}
		public String getDocId() {
			return docId;
		}
		public void setDocId(String docId) {
			this.docId = docId;
		}
		public String getLoanCode() {
			return loanCode;
		}
		public void setLoanCode(String loanCode) {
			this.loanCode = loanCode;
		}
		public String getLoanFlag() {
			return loanFlag;
		}
		public void setLoanFlag(String loanFlag) {
			this.loanFlag = loanFlag;
		}
		public String getDictIsAdditional() {
			return dictIsAdditional;
		}
		public void setDictIsAdditional(String dictIsAdditional) {
			this.dictIsAdditional = dictIsAdditional;
		}
		public String getDictSourceType() {
			return dictSourceType;
		}
		public void setDictSourceType(String dictSourceType) {
			this.dictSourceType = dictSourceType;
		}
		public String getCustomerCertNum() {
			return customerCertNum;
		}
		public void setCustomerCertNum(String customerCertNum) {
			this.customerCertNum = customerCertNum;
		}
		
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getStoreProviceName() {
		return storeProviceName;
	}
	public void setStoreProviceName(String storeProviceName) {
		this.storeProviceName = storeProviceName;
	}
	public String getStoreCityName() {
		return storeCityName;
	}
	public void setStoreCityName(String storeCityName) {
		this.storeCityName = storeCityName;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	/**
     * @return the storeName
     */
    public String[] getStoreName() {
        return storeName;
    }
    /**
     * @param storeName the String storeName to set
     */
    public void setStoreName(String[] storeName) {
        this.storeName = storeName;
    }
    /**
     * @return the storeOrgId
     */
    public String[] getStoreOrgId() {
        return storeOrgId;
    }
    /**
     * @param storeOrgId the String storeOrgId to set
     */
    public void setStoreOrgId(String[] storeOrgId) {
        this.storeOrgId = storeOrgId;
    }
    public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getLoanAuditAmount() {
		return loanAuditAmount;
	}
	public void setLoanAuditAmount(BigDecimal loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}
	public Integer getLoanAuditMonth() {
		return loanAuditMonth;
	}
	public void setLoanAuditMonth(Integer loanAuditMonth) {
		this.loanAuditMonth = loanAuditMonth;
	}
	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}
	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}
	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}
	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}
	public String getLoanManagerName() {
		return loanManagerName;
	}
	public void setLoanManagerName(String loanManagerName) {
		this.loanManagerName = loanManagerName;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	/**
     * @return the signCount
     */
    public String getSignCount() {
        return signCount;
    }
    /**
     * @param signCount the String signCount to set
     */
    public void setSignCount(String signCount) {
        this.signCount = signCount;
    }
    
    
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	@Override
	public String toString() {
		return "ContractAndPersonInfo [contractCode=" + contractCode
				+ ", contractVersion=" + contractVersion + ", coboName="
				+ coboName + ", loanCustomerName=" + loanCustomerName
				+ ", storeProviceName=" + storeProviceName + ", storeCityName="
				+ storeCityName + ", storeCode=" + storeCode
				+ ", dictLoanStatus=" + dictLoanStatus + ", productType="
				+ productType + ", loanAuditAmount=" + loanAuditAmount
				+ ", loanAuditMonth=" + loanAuditMonth + ", loanUrgentFlag="
				+ loanUrgentFlag + ", loanTeamManagerName="
				+ loanTeamManagerName + ", loanManagerName=" + loanManagerName
				+ ", customerIntoTime=" + customerIntoTime + ", loanIsPhone="
				+ loanIsPhone + ", loanFlag=" + loanFlag
				+ ", dictIsAdditional=" + dictIsAdditional
				+ ", dictSourceType=" + dictSourceType + ", customerCertNum="
				+ customerCertNum +",loanFrozenFlag="+loanFrozenFlag+ ", loanStatus=" + loanStatus+ "]";
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getLoanFrozenFlag() {
		return loanFrozenFlag;
	}
	public void setLoanFrozenFlag(String loanFrozenFlag) {
		this.loanFrozenFlag = loanFrozenFlag;
	}
	public String[] getRevisitStatuss() {
		return revisitStatuss;
	}
	public void setRevisitStatuss(String[] revisitStatuss) {
		this.revisitStatuss = revisitStatuss;
	}
	public String getRevisitStatus() {
		return revisitStatus;
	}
	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}
	public String getRevisitStatusName() {
		return revisitStatusName;
	}
	public void setRevisitStatusName(String revisitStatusName) {
		this.revisitStatusName = revisitStatusName;
	}
	public String getRevisitStatussParm() {
		return revisitStatussParm;
	}
	public void setRevisitStatussParm(String revisitStatussParm) {
		this.revisitStatussParm = revisitStatussParm;
	}
	public String getBestCoborrower() {
		return bestCoborrower;
	}
	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}


	
}
