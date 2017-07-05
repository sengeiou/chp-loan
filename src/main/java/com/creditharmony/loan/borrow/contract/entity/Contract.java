package com.creditharmony.loan.borrow.contract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 *封装合同表信息 
 * @author 张灏
 */
public class Contract extends DataEntity<Contract>{
	
    private static final long serialVersionUID = -1202465351429063526L;
	// 合同编号
	private String contractCode; 
	// 还款ID
    private String applyId;
    // 借款编码
    private String loanCode; 
    // 业务类型
    private String classType;  
    // 预约签署日期
    private Date contractDueDay; 
    // 实际签署日期
    private Date contractFactDay; 
    // 起始还款日期
    private Date contractReplayDay;
    // 合同到期日期
    private Date contractEndDay;
    // 合同到期日期-用于最高担保函
    private Date contractEndDayTemp;
    
    private String contractEndDayStr;
    // 产品类型
    private String productType;
    // 批借金额
    private BigDecimal auditAmount;
    // 批借期限
    private BigDecimal contractMonths;
    // 中间人主键
    private String midId;
    // 中间人姓名
    private String midName;
    // 还款付息方式
    private String dictRepayMethod;
    // 还款付息方式 文字
    private String dictRepayMethodName;
    // 合同金额
    private BigDecimal contractAmount;
    // 预计还款总额
    private BigDecimal contractExpectAmount;
    // 月还款本息和   
    private BigDecimal contractMonthRepayAmount;
    // 月还款总额
    private BigDecimal monthPayTotalAmount;
    // 审核状态
    private String dictCheckStatus;
    // 待放款确认退回原因
    private String contractBackResult;
    // 合同版本号
    private String contractVersion;
    // 原始合同版本号
    private String contractHistoryVersion;
 // 合同版本号名称
    private String contractVersionLabel;
    // 文档ID
    private String docId;
    // 审批次数
    private Integer auditCount;
    // 退回标识
    private String backFlag;
    // 合同作废
    private String isobsolete;
    // 共借人姓名
    private String coboName;
    // 共借人身份证号
    private String coboCertNum;
    // 主借人姓名
    private String loanName;
    // 主借人身份证号
    private String loanCertNum;
    // 渠道标识
    private String channelFlag;
    // 无纸化标识
    private String paperLessFlag;
    // 汇诚批复时间
    private Date auditTime;
    // 公司名称
    private String companyName;
    // 法人代表
    private String legalMan;
    // 经营地址
    private String maddress;
    // 模型
    private String model;
    // 保证人是否注册
    private String isRegister;
    //回访状态
    private String revisitStatus;
    //推送时间
    private Date pushTime;
    //推送次数
    private Integer pushNumber;
    //回访失败原因
    private String revisitReason;
    //拆单标记
    private String issplit;
    // 是否加盖
    private String signUpFlag;
    
    private String contractCodeTemp;
    
    private String newFlag;
    
    private String lender;
    
    private String isreceive;
    
    private String feeAllRaio;
    
    public String getContractCodeTemp() {
		return contractCodeTemp;
	}

	public void setContractCodeTemp(String contractCodeTemp) {
		this.contractCodeTemp = contractCodeTemp;
	}

	public String getIssplit() {
		return issplit;
	}

	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}

	public String getRevisitReason() {
		return revisitReason;
	}

	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getPushNumber() {
		return pushNumber;
	}

	public void setPushNumber(Integer pushNumber) {
		this.pushNumber = pushNumber;
	}

	public String getRevisitStatus() {
		return revisitStatus;
	}

	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}
    //审核人
    private String auditingBy;
    //审核时间
    private Date auditingTime;
    
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();

    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode == null ? null : loanCode.trim();
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Date getContractDueDay() {
		return contractDueDay;
	}

	public void setContractDueDay(Date contractDueDay) {
		this.contractDueDay = contractDueDay;
	}

	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}

	public Date getContractReplayDay() {
		return contractReplayDay;
	}

	public void setContractReplayDay(Date contractReplayDay) {
		this.contractReplayDay = contractReplayDay;
	}

	public Date getContractEndDay() {
		return contractEndDay;
	}

	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}

	public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public BigDecimal getContractMonths() {
        return contractMonths;
    }

    public void setContractMonths(BigDecimal contractMonths) {
        this.contractMonths = contractMonths;
    }

    public String getMidId() {
        return midId;
    }

    public void setMidId(String midId) {
        this.midId = midId == null ? null : midId.trim();
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getDictRepayMethod() {
        return dictRepayMethod;
    }

    public void setDictRepayMethod(String dictRepayMethod) {
        this.dictRepayMethod = dictRepayMethod == null ? null : dictRepayMethod.trim();
    }

    public String getDictRepayMethodName() {
		return dictRepayMethodName;
	}

	public void setDictRepayMethodName(String dictRepayMethodName) {
		this.dictRepayMethodName = dictRepayMethodName;
	}

	public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
         this.contractAmount = contractAmount;
    }

    public BigDecimal getContractExpectAmount() {
        return contractExpectAmount;
    }

    public void setContractExpectAmount(BigDecimal contractExpectAmount) {
        this.contractExpectAmount = contractExpectAmount;
    }

	public BigDecimal getContractMonthRepayAmount() {
		return contractMonthRepayAmount;
	}

	public void setContractMonthRepayAmount(BigDecimal contractMonthRepayAmount) {
		this.contractMonthRepayAmount = contractMonthRepayAmount;
	}

	public BigDecimal getMonthPayTotalAmount() {
		return monthPayTotalAmount;
	}

	public void setMonthPayTotalAmount(BigDecimal monthPayTotalAmount) {
		this.monthPayTotalAmount = monthPayTotalAmount;
	}

	public String getDictCheckStatus() {
        return dictCheckStatus;
    }

    public void setDictCheckStatus(String dictCheckStatus) {
        this.dictCheckStatus = dictCheckStatus == null ? null : dictCheckStatus.trim();
    }

    public String getContractBackResult() {
        return contractBackResult;
    }

    public void setContractBackResult(String contractBackResult) {
        this.contractBackResult = contractBackResult == null ? null : contractBackResult.trim();
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion == null ? null : contractVersion.trim();
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * @return the auditCount
     */
    public Integer getAuditCount() {
        return auditCount;
    }

    /**
     * @param auditCount the Integer auditCount to set
     */
    public void setAuditCount(Integer auditCount) {
        this.auditCount = auditCount;
    }

    /**
     * @return the backFlag
     */
    public String getBackFlag() {
        return backFlag;
    }

    /**
     * @param backFlag the String backFlag to set
     */
    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }

	public String getIsobsolete() {
		return isobsolete;
	}

	public void setIsobsolete(String isobsolete) {
		this.isobsolete = isobsolete;
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}

	public String getCoboCertNum() {
		return coboCertNum;
	}

	public void setCoboCertNum(String coboCertNum) {
		this.coboCertNum = coboCertNum;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanCertNum() {
		return loanCertNum;
	}

	public void setLoanCertNum(String loanCertNum) {
		this.loanCertNum = loanCertNum;
	}

	public String getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}

	/**
     * @return the paperLessFlag
     */
    public String getPaperLessFlag() {
        return paperLessFlag;
    }

    /**
     * @param paperLessFlag the String paperLessFlag to set
     */
    public void setPaperLessFlag(String paperLessFlag) {
        this.paperLessFlag = paperLessFlag;
    }

    public String getContractEndDayStr() {
		return contractEndDayStr;
	}

	public void setContractEndDayStr(String contractEndDayStr) {
		this.contractEndDayStr = contractEndDayStr;
	}

	public String getContractVersionLabel() {
		return contractVersionLabel;
	}

	public void setContractVersionLabel(String contractVersionLabel) {
		this.contractVersionLabel = contractVersionLabel;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the String companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the legalMan
     */
    public String getLegalMan() {
        return legalMan;
    }

    /**
     * @param legalMan the String legalMan to set
     */
    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan;
    }

    /**
     * @return the maddress
     */
    public String getMaddress() {
        return maddress;
    }

    /**
     * @param maddress the String maddress to set
     */
    public void setMaddress(String maddress) {
        this.maddress = maddress;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

	public String getContractHistoryVersion() {
		return contractHistoryVersion;
	}

	public void setContractHistoryVersion(String contractHistoryVersion) {
		this.contractHistoryVersion = contractHistoryVersion;
	}

	public String getAuditingBy() {
		return auditingBy;
	}

	public void setAuditingBy(String auditingBy) {
		this.auditingBy = auditingBy;
	}

	public Date getAuditingTime() {
		return auditingTime;
	}

	public void setAuditingTime(Date auditingTime) {
		this.auditingTime = auditingTime;
	}

	public String getSignUpFlag() {
		return signUpFlag;
	}

	public void setSignUpFlag(String signUpFlag) {
		this.signUpFlag = signUpFlag;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getLender() {
		return lender;
	}

	public void setLender(String lender) {
		this.lender = lender;
	}

	public String getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(String isreceive) {
		this.isreceive = isreceive;
	}

	public String getFeeAllRaio() {
		return feeAllRaio;
	}

	public void setFeeAllRaio(String feeAllRaio) {
		this.feeAllRaio = feeAllRaio;
	}

	public Date getContractEndDayTemp() {
		return contractEndDayTemp;
	}

	public void setContractEndDayTemp(Date contractEndDayTemp) {
		this.contractEndDayTemp = contractEndDayTemp;
	}
	

}