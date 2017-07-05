package com.creditharmony.loan.borrow.consult.view;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 客户咨询查询VIEW
 * @Class Name ConsultSearchView
 * @author zhangping
 * @Create In 2015年11月24日
 */
@SuppressWarnings("serial")
public class ConsultSearchView extends DataEntity<ConsultSearchView> {
    // 客户姓名 
	private String customerName;
	// 证件号码
	private String mateCertNum;
	// 上次沟通时间
	private Date lastTimeConsCommunicateDate;   
	// 最早的沟通时间
	private Date earlyConsCommunicateTime;
	
	private Date createTime;
	// 沟通记录
	private String consLoanRecord;
	// 团队经理编号  
	private String loanTeamEmpcode; 
	// 团队经理姓名
	private String loanTeamEmpName;
	// 客户经理编号
	private String managerCode;  
	// 客户经理姓名
	private String custManagerName;
	// 下一步状态
	private String dictOperStatus;
	private String dictOperStatusName;
	// 客户编号
	private String customerCode;
	// 咨询ID
	private String consultID;
	
	private Integer idd; 
	// 客户联系电话
	private String customerMobilePhone;
	// 本次沟通时间
	private Date consCommunicateDate;
	
	// 历史沟通创建人
	private String createName; 
	
	private String consTeamManagercode;       
	// 是否电销
	private String consTelesalesFlag;
	// 客服人员（电销用）
	private String consServiceUserCode;
	// 门店ID 
	private String storeOrgId;
	// 信借下一步咨询状态
	private String dictCreditOperStatus;
	// 信借非电销标识
	private String consCreditNotTelFlag;
	// 电销下一步状态
	private String dictTelOperStatus;
	// 电销标识
	private String dictTelFlag;
    // 数据查询权限
	private String queryRight;
	// 门店名字
	private String storeOrgName;
	//在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String loanInfoOldOrNewFlag;
    /**
     * 客户经理在职状态
     */
    private String userStatus;
    /**
     * 客户经理在职状态名称
     */
    private String userStatusName;
    /**
     * 咨询数据来源
     */
    private String consultDataSource;
    
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}

	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}

	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Date getConsCommunicateDate() {
		return consCommunicateDate;
	}

	public void setConsCommunicateDate(Date consCommunicateDate) {
		this.consCommunicateDate = consCommunicateDate;
	}

	public String getConsTeamManagercode() {
        return consTeamManagercode;
    }

    public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setConsTeamManagercode(String consTeamManagercode) {
        this.consTeamManagercode = consTeamManagercode;
    }

    public String getConsTelesalesFlag() {
        return consTelesalesFlag;
    }

    public void setConsTelesalesFlag(String consTelesalesFlag) {
        this.consTelesalesFlag = consTelesalesFlag;
    }

    public String getStoreOrgId() {
        return storeOrgId;
    }

    public void setStoreOrgId(String storeOrgId) {
        this.storeOrgId = storeOrgId;
    }

    /**
     * @return the dictCreditOperStatus
     */
    public String getDictCreditOperStatus() {
        return dictCreditOperStatus;
    }

    /**
     * @param dictCreditOperStatus the String dictCreditOperStatus to set
     */
    public void setDictCreditOperStatus(String dictCreditOperStatus) {
        this.dictCreditOperStatus = dictCreditOperStatus;
    }

    /**
     * @return the consCreditNotTelFlag
     */
    public String getConsCreditNotTelFlag() {
        return consCreditNotTelFlag;
    }

    /**
     * @param consCreditNotTelFlag the String consCreditNotTelFlag to set
     */
    public void setConsCreditNotTelFlag(String consCreditNotTelFlag) {
        this.consCreditNotTelFlag = consCreditNotTelFlag;
    }

    /**
     * @return the dictTelOperStatus
     */
    public String getDictTelOperStatus() {
        return dictTelOperStatus;
    }

    /**
     * @param dictTelOperStatus the String dictTelOperStatus to set
     */
    public void setDictTelOperStatus(String dictTelOperStatus) {
        this.dictTelOperStatus = dictTelOperStatus;
    }

    /**
     * @return the dictTelFlag
     */
    public String getDictTelFlag() {
        return dictTelFlag;
    }

    /**
     * @param dictTelFlag the String dictTelFlag to set
     */
    public void setDictTelFlag(String dictTelFlag) {
        this.dictTelFlag = dictTelFlag;
    }

    /**
     * @return the queryRight
     */
    public String getQueryRight() {
        return queryRight;
    }

    /**
     * @param queryRight the String queryRight to set
     */
    public void setQueryRight(String queryRight) {
        this.queryRight = queryRight;
    }

    public Integer getIdd() {
		return idd;
	}

	public void setIdd(Integer idd) {
		this.idd = idd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMateCertNum() {
		return mateCertNum;
	}

	public void setMateCertNum(String mateCertNum) {
		this.mateCertNum = mateCertNum;
	}

	public Date getLastTimeConsCommunicateDate() {
		return lastTimeConsCommunicateDate;
	}

	public void setLastTimeConsCommunicateDate(Date lastTimeConsCommunicateDate) {
		this.lastTimeConsCommunicateDate = lastTimeConsCommunicateDate;
	}

	public Date getEarlyConsCommunicateTime() {
        return earlyConsCommunicateTime;
    }

    public void setEarlyConsCommunicateTime(Date earlyConsCommunicateTime) {
        this.earlyConsCommunicateTime = earlyConsCommunicateTime;
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConsLoanRecord() {
		return consLoanRecord;
	}

	public void setConsLoanRecord(String consLoanRecord) {
		this.consLoanRecord = consLoanRecord;
	}

	public String getLoanTeamEmpcode() {
		return loanTeamEmpcode;
	}

	public void setLoanTeamEmpcode(String loanTeamEmpcode) {
		this.loanTeamEmpcode = loanTeamEmpcode;
	}

	public String getLoanTeamEmpName() {
        return loanTeamEmpName;
    }

    public void setLoanTeamEmpName(String loanTeamEmpName) {
        this.loanTeamEmpName = loanTeamEmpName;
    }

    public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getDictOperStatus() {
		return dictOperStatus;
	}

	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

    /**
     * @return the consultID
     */
    public String getConsultID() {
        return consultID;
    }

    /**
     * @param consultID the String consultID to set
     */
    public void setConsultID(String consultID) {
        this.consultID = consultID;
    }

    public String getCustManagerName() {
        return custManagerName;
    }

    public void setCustManagerName(String custManagerName) {
        this.custManagerName = custManagerName;
    }

	public String getDictOperStatusName() {
		return dictOperStatusName;
	}

	public void setDictOperStatusName(String dictOperStatusName) {
		this.dictOperStatusName = dictOperStatusName;
	}

    /**
     * @return the storeOrgName
     */
    public String getStoreOrgName() {
        return storeOrgName;
    }

    /**
     * @param storeOrgName the String storeOrgName to set
     */
    public void setStoreOrgName(String storeOrgName) {
        this.storeOrgName = storeOrgName;
    }

    /**
     * @return the consServiceUserCode
     */
    public String getConsServiceUserCode() {
        return consServiceUserCode;
    }

    /**
     * @param consServiceUserCode the String consServiceUserCode to set
     */
    public void setConsServiceUserCode(String consServiceUserCode) {
        this.consServiceUserCode = consServiceUserCode;
    }

	public String getUserStatusName() {
		return userStatusName;
	}

	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}

	public String getConsultDataSource() {
		return consultDataSource;
	}

	public void setConsultDataSource(String consultDataSource) {
		this.consultDataSource = consultDataSource;
	}
	
}
