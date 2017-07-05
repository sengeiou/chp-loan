package com.creditharmony.loan.borrow.account.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

public class RepayAccountApply extends DataEntity<RepayAccountApply> {

	private static final long serialVersionUID = 1L;

	private String contractCode;	//合同编号
	private String loanCode;		//借款编号
	private String customerName;	//客户姓名
	private String account;			//账号
	private String oldAccount;		//原账户
	private String bankName;		//开户行字典值
	private String openBankName;	//开户行名称
	private String accountName;		//账号名称
	private Date contractEndDay;	//合同到期日
	private Date firstRepayDay;		//首期还款日
	private String storeName;		//门店名称
	private String loanStatus;		//借款状态(0:还款中; 1:逾期; 2:结清; 3:提前结清; 4:提前结清待审核; 5:结清待确认)
	private String maintainType;	//维护类型(新增/修改)
	private Integer repayDay;		//还款日
	private String flag;			//标识
	private String model;           // 模式
	private String modelName;       // 模式名称
	private String maintainStatus;	//维护状态(待审核、拒绝、已维护)
	private String maintainStatusArray; // 维护状态（数组格式）
	private String jzhMaintainStatus;	//金账户维护状态(待审核、已审核待变更)
	private String version;			//合同版本号
	private String versionLabel;    //合同版本号名称
	private String fileId;			//账号附件ID
	private String fileName;		//账号附件名称
	private String phoneFileId;		//手机号附件ID
	private String phoneFileName;	//手机号附件名称
	private Integer topFlag;		//置顶标识
	
	private String customerCard;	//客户身份证号
	private String customerPhone;	//客户手机号
	private String newCustomerPhone;//客户新手机号
	private String provinceId;		//省份ID
	private String provinceName;	//省份名称
	private String cityId;			//城市Id
	private String cityName;		//城市名称
	private String bankBranch;		//开户行支行
	private String hdloanBankbrId;  // 开户行支行code
	private String deductPlat;		//划扣平台(0:富友; 1:好易联; 2:中金; 3:通联)
	private String deductPlatName;	//划扣平台名称
	private Date applyTime;			//申请时间
	private Date maintainTime;		//维护时间
	private String oldAccountId;	//老账户ID
	private String notTG;           // 非TG账户

	private String pMsgAccount;			//账号
	private String pMsgBankName;		//开户行字典值
	private String pMsgBankBranch;		//开户行支行
	private String pMsgProvinceId;		//省份ID
	private String pMsgCityId;			//城市Id
	private String pMsgCustomerPhone;	//客户手机号
	
	
	
	
	public String getVersionLabel() {
		return versionLabel;
	}
	public void setVersionLabel(String versionLabel) {
		this.versionLabel = versionLabel;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOldAccount() {
		return oldAccount;
	}
	public void setOldAccount(String oldAccount) {
		this.oldAccount = oldAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public Date getFirstRepayDay() {
		return firstRepayDay;
	}
	public void setFirstRepayDay(Date firstRepayDay) {
		this.firstRepayDay = firstRepayDay;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public Integer getRepayDay() {
		return repayDay;
	}
	public void setRepayDay(Integer repayDay) {
		this.repayDay = repayDay;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMaintainStatus() {
		return maintainStatus;
	}
	public void setMaintainStatus(String maintainStatus) {
		this.maintainStatus = maintainStatus;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getPhoneFileId() {
		return phoneFileId;
	}
	public void setPhoneFileId(String phoneFileId) {
		this.phoneFileId = phoneFileId;
	}
	public String getPhoneFileName() {
		return phoneFileName;
	}
	public void setPhoneFileName(String phoneFileName) {
		this.phoneFileName = phoneFileName;
	}
	public Integer getTopFlag() {
		return topFlag;
	}
	public void setTopFlag(Integer topFlag) {
		this.topFlag = topFlag;
	}
	public String getCustomerCard() {
		return customerCard;
	}
	public void setCustomerCard(String customerCard) {
		this.customerCard = customerCard;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getNewCustomerPhone() {
		return newCustomerPhone;
	}
	public void setNewCustomerPhone(String newCustomerPhone) {
		this.newCustomerPhone = newCustomerPhone;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getHdloanBankbrId() {
		return hdloanBankbrId;
	}
	public void setHdloanBankbrId(String hdloanBankbrId) {
		this.hdloanBankbrId = hdloanBankbrId;
	}
	public String getDeductPlat() {
		return deductPlat;
	}
	public void setDeductPlat(String deductPlat) {
		this.deductPlat = deductPlat;
	}
	public String getDeductPlatName() {
		return deductPlatName;
	}
	public void setDeductPlatName(String deductPlatName) {
		this.deductPlatName = deductPlatName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getMaintainTime() {
		return maintainTime;
	}
	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
	}
	public String getOldAccountId() {
		return oldAccountId;
	}
	public void setOldAccountId(String oldAccountId) {
		this.oldAccountId = oldAccountId;
	}
	public String getpMsgBankName() {
		return pMsgBankName;
	}
	public void setpMsgBankName(String pMsgBankName) {
		this.pMsgBankName = pMsgBankName;
	}
	public String getpMsgProvinceId() {
		return pMsgProvinceId;
	}
	public void setpMsgProvinceId(String pMsgProvinceId) {
		this.pMsgProvinceId = pMsgProvinceId;
	}
	public String getpMsgCityId() {
		return pMsgCityId;
	}
	public void setpMsgCityId(String pMsgCityId) {
		this.pMsgCityId = pMsgCityId;
	}
	public String getpMsgCustomerPhone() {
		return pMsgCustomerPhone;
	}
	public void setpMsgCustomerPhone(String pMsgCustomerPhone) {
		this.pMsgCustomerPhone = pMsgCustomerPhone;
	}
	public String getpMsgAccount() {
		return pMsgAccount;
	}
	public void setpMsgAccount(String pMsgAccount) {
		this.pMsgAccount = pMsgAccount;
	}
	public String getpMsgBankBranch() {
		return pMsgBankBranch;
	}
	public void setpMsgBankBranch(String pMsgBankBranch) {
		this.pMsgBankBranch = pMsgBankBranch;
	}
	public String getJzhMaintainStatus() {
		return jzhMaintainStatus;
	}
	public void setJzhMaintainStatus(String jzhMaintainStatus) {
		this.jzhMaintainStatus = jzhMaintainStatus;
	}
    /**
     * @return the maintainStatusArray
     */
    public String getMaintainStatusArray() {
        return maintainStatusArray;
    }
    /**
     * @param maintainStatusArray the String maintainStatusArray to set
     */
    public void setMaintainStatusArray(String maintainStatusArray) {
        this.maintainStatusArray = maintainStatusArray;
    }
    /**
     * @return the notTG
     */
    public String getNotTG() {
        return notTG;
    }
    /**
     * @param notTG the String notTG to set
     */
    public void setNotTG(String notTG) {
        this.notTG = notTG;
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
	
}
