package com.creditharmony.loan.borrow.consult.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.consult.groups.DoAllotGroup;

/**
 * 客户咨询信息
 * @Class Name Consult
 * @author 张永生
 * @Create In 2015年11月9日
 */
public class Consult extends DataEntity<Consult> {

	private static final long serialVersionUID = -4826593454690408310L;
	// 客户编码
	private String customerCode;
    // 客户基本信息
	private CustomerBaseInfo customerBaseInfo;
    // 客户沟通日志表
	private ConsultRecord consultRecord; 
    // 客户经理ID
	@NotEmpty(groups=DoAllotGroup.class, message="请选择客户经理")
	private String managerCode; 
    // 借款金额
	private BigDecimal loanApplyMoney;  
    // 借款用途
	private String dictLoanUse;
	// 借款用途备注
	private String dictLoanUseRemark;
	// 借款类型         
	private String dictLoanType;
	// 团队经理编号
	@NotEmpty(groups=DoAllotGroup.class, message="请选择团队经理")
	private String loanTeamEmpcode;	
    // 团队经理名字
	private String consTeamEmpName;
	// 团队组织机构ID
	private String loanTeamOrgId;
	// 电销组织机构ID(电销用)
	private String teleSalesOrgid;
	// 上级员工编码
	private String consPhoneSource;
	// 是否电销(0:否；1:是)
	private String consTelesalesFlag;
	// 客服人员(电销用)
	private String consCustomerService;
    // 长期(1:长期;0：非长期)
	private boolean longTerm;
	// 门店ID
	private String storeOrgid;
	// 沟通记录  
	private String consLoanRecord;
	// 下一步操作状态   
    private String consOperStatus;
    // 沟通时间
    private Date consCommunicateDate;		
	// 电销来源
    private String consTelesalesSource;
    // 产品Code
    private String productCode;
    // 借款期限
    private Integer loanMonth;
    // 银行卡号
    private String accountId;
    // 姓名图片
    private String namepic;
    // 身份证号图片
    private String certNumPic;
    //银行卡号图片
    private String accountIdPic;
    // 姓名图片名称
    private String namePicName;
    // 身份证号图片名称
    private String certNumPicName;
    // 银行卡号图片名称
    private String accountidPicName;
    // 咨询类型0：信审，1：OCR，2:APP
    private String consultationType;
    // 开户行名称
	private String accountBank;
	// 支行
	private String branch;
	// 银行卡所在城市省
	private String bankProvince;
	// 银行卡所在城市市
	private String bankCity;
	// 银行卡主键ID
	private String bankId;
	// 是否借么APP
	private String isBorrow;
	/**
	 * 数据来源 对应枚举
	 */
	private String consultDataSource;
	
    public String getConsLoanRecord() {
		return consLoanRecord;
	}
	
	public String getDictLoanUseRemark() {
		return dictLoanUseRemark;
	}

	public void setDictLoanUseRemark(String dictLoanUseRemark) {
		this.dictLoanUseRemark = dictLoanUseRemark;
	}

	public void setConsLoanRecord(String consLoanRecord) {
		this.consLoanRecord = consLoanRecord;
	}
    public String getConsOperStatus() {
		return consOperStatus;
	}
	public void setConsOperStatus(String consOperStatus) {
		this.consOperStatus = consOperStatus;
	}
	
	public Date getConsCommunicateDate() {
		return consCommunicateDate;
	}
	public void setConsCommunicateDate(Date consCommunicateDate) {
		this.consCommunicateDate = consCommunicateDate;
	}

	/**
     * @return the consTelesalesSource
     */
    public String getConsTelesalesSource() {
        return consTelesalesSource;
    }
    /**
     * @param consTelesalesSource the String consTelesalesSource to set
     */
    public void setConsTelesalesSource(String consTelesalesSource) {
        this.consTelesalesSource = consTelesalesSource;
    }

    private Integer idd;
	
	
	
	public Integer getIdd() {
		return idd;
	}
	public void setIdd(Integer idd) {
		this.idd = idd;
	}
	public CustomerBaseInfo getCustomerBaseInfo() {
		return customerBaseInfo;
	}
	public void setCustomerBaseInfo(CustomerBaseInfo customerBaseInfo) {
		this.customerBaseInfo = customerBaseInfo;
	}
	public ConsultRecord getConsultRecord() {
		return consultRecord;
	}
	public void setConsultRecord(ConsultRecord consultRecord) {
		this.consultRecord = consultRecord;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	
	public BigDecimal getLoanApplyMoney() {
		return loanApplyMoney;
	}
	public void setLoanApplyMoney(BigDecimal loanApplyMoney) {
		this.loanApplyMoney = loanApplyMoney;
	}
	public String getDictLoanUse() {
		return dictLoanUse;
	}
	public void setDictLoanUse(String dictLoanUse) {
		this.dictLoanUse = dictLoanUse;
	}
	public String getDictLoanType() {
		return dictLoanType;
	}
	public void setDictLoanType(String dictLoanType) {
		this.dictLoanType = dictLoanType;
	}
	
	public String getLoanTeamEmpcode() {
		return loanTeamEmpcode;
	}
	public void setLoanTeamEmpcode(String loanTeamEmpcode) {
		this.loanTeamEmpcode = loanTeamEmpcode;
	}
	public String getConsTeamEmpName() {
        return consTeamEmpName;
    }
    public void setConsTeamEmpName(String consTeamEmpName) {
        this.consTeamEmpName = consTeamEmpName;
    }
    public String getLoanTeamOrgId() {
		return loanTeamOrgId;
	}
	public void setLoanTeamOrgId(String loanTeamOrgId) {
		this.loanTeamOrgId = loanTeamOrgId;
	}
	public String getTeleSalesOrgid() {
		return teleSalesOrgid;
	}
	public void setTeleSalesOrgid(String teleSalesOrgid) {
		this.teleSalesOrgid = teleSalesOrgid;
	}
	public String getConsPhoneSource() {
		return consPhoneSource;
	}
	public void setConsPhoneSource(String consPhoneSource) {
		this.consPhoneSource = consPhoneSource;
	}
	public String getConsCustomerService() {
		return consCustomerService;
	}
	public void setConsCustomerService(String consCustomerService) {
		this.consCustomerService = consCustomerService;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}
	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}
	public boolean isLongTerm() {
		return longTerm;
	}
	public void setLongTerm(boolean longTerm) {
		this.longTerm = longTerm;
	}
	public String getStoreOrgid() {
        return storeOrgid;
    }
    public void setStoreOrgid(String storeOrgid) {
        this.storeOrgid = storeOrgid;
    }
    /**
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }
    /**
     * @param productCode the String productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    /**
     * @return the loanMonth
     */
    public Integer getLoanMonth() {
        return loanMonth;
    }
    /**
     * @param loanMonth the Integer loanMonth to set
     */
    public void setLoanMonth(Integer loanMonth) {
        this.loanMonth = loanMonth;
    }
    /**
     * @return the accountId
     */
    public String getAccountId() {
        return accountId;
    }
    /**
     * @param accountId the String accountId to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    /**
     * @return the namepic
     */
    public String getNamepic() {
        return namepic;
    }
    /**
     * @param namepic the String namepic to set
     */
    public void setNamepic(String namepic) {
        this.namepic = namepic;
    }
    /**
     * @return the certNumPic
     */
    public String getCertNumPic() {
        return certNumPic;
    }
    /**
     * @param certNumPic the String certNumPic to set
     */
    public void setCertNumPic(String certNumPic) {
        this.certNumPic = certNumPic;
    }
    /**
     * @return the accountIdPic
     */
    public String getAccountIdPic() {
        return accountIdPic;
    }
    /**
     * @param accountIdPic the String accountIdPic to set
     */
    public void setAccountIdPic(String accountIdPic) {
        this.accountIdPic = accountIdPic;
    }
    /**
     * @return the namePicName
     */
    public String getNamePicName() {
        return namePicName;
    }
    /**
     * @param namePicName the String namePicName to set
     */
    public void setNamePicName(String namePicName) {
        this.namePicName = namePicName;
    }
    /**
     * @return the certNumPicName
     */
    public String getCertNumPicName() {
        return certNumPicName;
    }
    /**
     * @param certNumPicName the String certNumPicName to set
     */
    public void setCertNumPicName(String certNumPicName) {
        this.certNumPicName = certNumPicName;
    }
    /**
     * @return the accountidPicName
     */
    public String getAccountidPicName() {
        return accountidPicName;
    }
    /**
     * @param accountidPicName the String accountidPicName to set
     */
    public void setAccountidPicName(String accountidPicName) {
        this.accountidPicName = accountidPicName;
    }
    /**
     * @return the consultationType
     */
    public String getConsultationType() {
        return consultationType;
    }
    /**
     * @param consultationType the String consultationType to set
     */
    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }
    /**
     * @return the accountBank
     */
    public String getAccountBank() {
        return accountBank;
    }
    /**
     * @param accountBank the String accountBank to set
     */
    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }
    /**
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }
    /**
     * @param branch the String branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
    /**
     * @return the bankProvince
     */
    public String getBankProvince() {
        return bankProvince;
    }
    /**
     * @param bankProvince the String bankProvince to set
     */
    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }
    /**
     * @return the bankCity
     */
    public String getBankCity() {
        return bankCity;
    }
    /**
     * @param bankCity the String bankCity to set
     */
    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }
    /**
     * @return the bankId
     */
    public String getBankId() {
        return bankId;
    }
    /**
     * @param bankId the String bankId to set
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
    public String getIsBorrow() {
		return isBorrow;
	}
	public void setIsBorrow(String isBorrow) {
		this.isBorrow = isBorrow;
	}
	@Override
	public String toString() {
		return "Consult [customerCode=" +  ", managerCode="
				+ managerCode + ", loanApplyMoney=" + loanApplyMoney
				+ ", dictLoanUse=" + dictLoanUse + ", dictLoanType="
				
				+ ", loanTeamEmpcode=" + loanTeamEmpcode + ", loanTeamOrgId="
				+ loanTeamOrgId + ", teleSalesOrgid=" + teleSalesOrgid
				+ ", consPhoneSource=" + consPhoneSource + ", loanIsPhone="
				+ consTelesalesFlag + ", consCustomerService=" + consCustomerService
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result
				+ ((consPhoneSource == null) ? 0 : consPhoneSource.hashCode());
		
		result = prime * result
				+ ((dictLoanType == null) ? 0 : dictLoanType.hashCode());
		result = prime * result
				+ ((dictLoanUse == null) ? 0 : dictLoanUse.hashCode());
		result = prime * result
				+ ((managerCode == null) ? 0 : managerCode.hashCode());
		result = prime * result
				+ ((loanApplyMoney == null) ? 0 : loanApplyMoney.hashCode());
		result = prime * result
				+ ((consTelesalesFlag == null) ? 0 : consTelesalesFlag.hashCode());
		result = prime * result
				+ ((loanTeamEmpcode == null) ? 0 : loanTeamEmpcode.hashCode());
		result = prime * result
				+ ((loanTeamOrgId == null) ? 0 : loanTeamOrgId.hashCode());
		result = prime * result
				+ ((teleSalesOrgid == null) ? 0 : teleSalesOrgid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consult other = (Consult) obj;
		
		if (consCustomerService == null) {
			if (other.consCustomerService != null)
				return false;
		} else if (!consCustomerService.equals(other.consCustomerService))
			return false;
		
		if (consPhoneSource == null) {
			if (other.consPhoneSource != null)
				return false;
		} else if (!consPhoneSource.equals(other.consPhoneSource))
			return false;
		
		if (dictLoanType == null) {
			if (other.dictLoanType != null)
				return false;
		} else if (!dictLoanType.equals(other.dictLoanType))
			return false;
		if (dictLoanUse == null) {
			if (other.dictLoanUse != null)
				return false;
		} else if (!dictLoanUse.equals(other.dictLoanUse))
			return false;
		if (managerCode == null) {
			if (other.managerCode != null)
				return false;
		} else if (!managerCode.equals(other.managerCode))
			return false;
		if (loanApplyMoney == null) {
			if (other.loanApplyMoney != null)
				return false;
		} else if (!loanApplyMoney.equals(other.loanApplyMoney))
			return false;
		if (consTelesalesFlag == null) {
			if (other.consTelesalesFlag != null)
				return false;
		} else if (!consTelesalesFlag.equals(other.consTelesalesFlag))
			return false;
		if (loanTeamEmpcode == null) {
			if (other.loanTeamEmpcode != null)
				return false;
		} else if (!loanTeamEmpcode.equals(other.loanTeamEmpcode))
			return false;
		if (loanTeamOrgId == null) {
			if (other.loanTeamOrgId != null)
				return false;
		} else if (!loanTeamOrgId.equals(other.loanTeamOrgId))
			return false;
		if (teleSalesOrgid == null) {
			if (other.teleSalesOrgid != null)
				return false;
		} else if (!teleSalesOrgid.equals(other.teleSalesOrgid))
			return false;
		return true;
	}

	public String getConsultDataSource() {
		return consultDataSource;
	}

	public void setConsultDataSource(String consultDataSource) {
		this.consultDataSource = consultDataSource;
	}
	
}
