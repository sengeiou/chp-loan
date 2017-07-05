package com.creditharmony.loan.borrow.contract.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.RateInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.FeeInfoEx;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.FyAreaCode;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.MiddlePerson;

public class ContractBusiView extends BaseBusinessView {
	// 合同编号
    private String contractCode;
	// 主借款人
    private String mainLoaner;
    // 主借人客户编号mainLoaner
    private String customerCode;
    //客户验证码
    private String customerPin;
    // 主借人性别
    private String mainCertSex;  
    // 主借人性别 文字
    private String mainCertSexName;
    // 证件类型（主借人）
    private String mainCertType; 
    // 证件类型名称（主借人）
    private String mainCertTypeName; 
    // 证件号码（主借人）    
    private String mainCertNum; 
    // 账单日
    private Integer billDay;
    //借款申请实体对象
    private LoanInfo loanInfo;
    
    private List<LoanCoborrower> coborrowers = new ArrayList<LoanCoborrower>();
    
    private List<MiddlePerson> middlePersons = new ArrayList<MiddlePerson>();
    // 保证人姓名
    private String auditEnsureName; 
    // 法定代表人
    private String auditLegalMan;
    // 保证人实际经营场所
    private String ensuremanBusinessPlace; 
    // 资金托管标识，1托管，空非托管
    private String loanFlag; 
    //
    private String loanFlagCode;
    // 属性名称
    private String attrName;
    // 退回原因 
    private String grantBackMes;  
    // 当前用户Code
    private String currUserCode;
    // 费用信息 
    private ContractFee ctrFee;  
    // 费用信息 
    private ContractFeeTemp ctrFeeZCJ;
    // 费用信息 
    private ContractFeeTemp ctrFeeJINXIN;
    //zcj费用   拆分时使用
    private ContractFeeTemp zcjFeeTemp;
    //金信费用  拆分时使用
    private ContractFeeTemp jinXinFeeTemp;
    // 合同信息
    private Contract contract ;  
    // 合同信息
    private ContractTemp contractZCJ ;  
    // 合同信息
    private ContractTemp contractJINXIN ;  
    // 费用的字符串表示形式
    private FeeInfoEx feeInfo;
 // 费用的字符串表示形式
    private FeeInfoEx feeInfoZCJ;
 // 费用的字符串表示形式
    private FeeInfoEx feeInfoJINXIN;
    // 开户行    
    private LoanBank loanBank = new LoanBank();  
    // 移动电话    
    private String customerPhoneFirst; 
    // 移动电话 
    private String customerPhoneSecond; 
    // 固定电话   
    private String customerTel;  
    // 借款编号   
    private String loanCode;      
    // 产品类型    
    private String productType;  
    // 产品类型名称
    private String productName;    
    // 批复期限    
    private String contractMonths; 
    // 备注
    private String remarks;
    // 操作结果
    private String dictOperateResult;
    //手动验证结果
    private String verification;
    //手动验证不通过的原因
    private String returnReason;
    
    // 合同金额
    private Double contractAmount;
    // 实放金额
    private Double feePaymentAmount;
    // 放款失败金额
    private Double grantFailAmount;
    // 催收服务费
    private Double feeUrgedService;
    // 状态
    private String dictLoanStatus;
    // 状态Code
    private String dictLoanStatusCode;
    // 退回标识
    private String backFlag;
    // 门店ID
    private String loanStoreOrgId;
    // 门店名称
    private String loanStoreOrgName;
    // 退回节点
    private String backResponse;
    // 开户行
    private String applyBankName;
    // 支行名称
    private String applyBankBranch;
    // 用户账号 
    private String applyBankAccount;
    // 签署开始日
    private Date signStartDay;
    // 签署结束日
    private Date signEndDay;
    // 签约平台
    private String signingPlatformName;
    // 操作类型，用于区分更新属性和提交流程用的
    private String operType;
    // 未划金额
    private double unUrgeService;   
    // 是否冻结 1 冻结 0 未冻结
    private String frozenFlag;
    // 冻结原因 Code
    private String frozenCode;
    // 原因描述
    private String frozenReason;
    // 排序字段
    private String orderField;
    // 金账户开户行省市名
    private String kingBankProvinceName;
    // 金账户开户行区县名 
    private String kingBankCityName;
    // 金账户开户行省市 
    private String kingBankProvinceCode;
    // 金账户开户行区县
    private String kingBankCityCode;
    // 初始化省份显示
    private List<CityInfo> provinceList = new ArrayList<CityInfo>();   
    // 初始化富友省份显示
    private List<FyAreaCode> fyProvinceList = new ArrayList<FyAreaCode>();
    // 利率列表
    private List<RateInfo> rateInfoList = new ArrayList<RateInfo>();
    // 合同文件列表
    private List<ContractFile> files = new ArrayList<ContractFile>();
    // 当前利率生效标识
    private String rateEffectiveFlag;
    // 
    private String curRate;
    // 影像地址
    private String imageUrl;
    // 大额查看 
    private String largeAmountImageUrl;
    // 大额标识 0非大额   1大额
    private String largeAmountFlag;
    // 协议查看
    private String protocolImgUrl;
    // 上一节点处理时间
    private String lastDealTime;
    // 金账户标识 （0 表示金账户账号为空，1 表示金账户账号有值）
    private  String trusteeshipFlag;
    // 委托标识 0 委托提现 1 委托充值
    private String  trustFlag;
    // 委托提现
    private String trustCash;
    // 委托充值状态
    private String trustRecharge;
    // 超时标识
    private String  timeOutFlag;
    // 超时截至日期
    private Date timeOutPoint;
    // 金账户开户状态
    private String kingStatus;
    // 账户名称
    private String custBankAccountName;
    // 用户邮件
    private String email;
    // 手机
    private String mobilePhone;
    // 无纸化合同标识
    private String paperLessFlag;
    // 借款利率
    private String monthRate;
    
    private String grantSureBackReasonCode;
    // 验证码确认标识
    private String captchaIfConfirm;
    // 确认超时结束时间
    private Date confirmTimeout;
    // app签字标识
    private String appSignFlag;
    // 身份证验证标识
    private String idValidFlag;
    // 合成图片ID
    private String composePhotoId;
    // 客户编号
    private String customerId;
    // 是否有保证人
    private String ensureManFlag;
    // 是否生僻字
    private String bankIsRareword;
    // 模式
    private String model;
    // 模式名字
    private String modelName;
    // 金信上限超额标识
    private String upLimit;
    // 金信版本
    private String jxVersion;
    // 金信ID
    private String jxId;
    // 退回原因
    private String grantSureBackReason;
    // 是否登记失败
    private String registFlag;
    // 是否加盖成功
    private String signUpFlag;
    // 是否门店副理
    private String isStoreAssistant;
    // 保证人关联ID 
    private String businessProveId;
    // 风险等级
    private String riskLevel;
    // 确认签署时间
    private String confirmSignDate;
    // 标识操作 类型 1表示增加 0表示取消
    private String channelFlagAdd;
    // 旧有标识
    private String oldChannelFlag;
    // 身份证验证分数
    private Float idValidScore;
    // 身份验证提示信息
    private String idValidMessage;
    private String hasOnlineTime;


    // 畅捷实名认证失败原因
    private String cjAuthenFailure;
    // 畅捷实名认证结果
    private String cjAuthen;
    
    private Double kinnobuQuota;//额度上限
    private Double kinnobuUsed;//已使用额度
    private String limitId;//额度id
    
    //经营地址 带省市区
    private String maddressName;
    
    //合同版本号
    private String contractVersion;
    private String backTimeFlag;
    
    //外访距离
    private String item_distance;
    //外访标识
    private String outside_flag;
    
    //审核日期
    private Date checkTime;
    //复议标识
    private String reconsiderFlag;
    //建议放弃按钮 显示标识
    private String proposeFlag="1";

    private List<ContractTemp> contractTemps=new ArrayList<ContractTemp>();
    private String issplit;
    private String isreceive;
    
    private String oldLoanCode;
    
    private List<JyjBorrowBankConfigure> jyjBank;
    
    
    public String getOldLoanCode() {
		return oldLoanCode;
	}

	public void setOldLoanCode(String oldLoanCode) {
		this.oldLoanCode = oldLoanCode;
	}

	public String getProposeFlag() {
		return proposeFlag;
	}

	public void setProposeFlag(String proposeFlag) {
		this.proposeFlag = proposeFlag;
	}

	public String getReconsiderFlag() {
		return reconsiderFlag;
	}

	public void setReconsiderFlag(String reconsiderFlag) {
		this.reconsiderFlag = reconsiderFlag;
	}

    public String getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(String isreceive) {
		this.isreceive = isreceive;
	}

	public List<ContractTemp> getContractTemps() {
		return contractTemps;
	}

	public void setContractTemps(List<ContractTemp> contractTemps) {
		this.contractTemps = contractTemps;
	}

    public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
    /**
     * @return the ensureManFlag
     */
    public String getEnsureManFlag() {
        return ensureManFlag;
    }

    /**
     * @param ensureManFlag the String ensureManFlag to set
     */
    public void setEnsureManFlag(String ensureManFlag) {
        this.ensureManFlag = ensureManFlag;
    }

    public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}

	public String getContractCode() {
        return contractCode;
	}

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getMainLoaner() {
        return mainLoaner;
    }

    public String getMainCertSex() {
        return mainCertSex;
    }

    public void setMainCertSex(String mainCertSex) {
        this.mainCertSex = mainCertSex;
    }

    public String getMainCertSexName() {
		return mainCertSexName;
	}

	public void setMainCertSexName(String mainCertSexName) {
		this.mainCertSexName = mainCertSexName;
	}

	public void setMainLoaner(String mainLoaner) {
        this.mainLoaner = mainLoaner;
    }

    public String getMainCertType() {
        return mainCertType;
	}

    public void setMainCertType(String mainCertType) {
        this.mainCertType = mainCertType;
    }

    public String getMainCertTypeName() {
        return mainCertTypeName;
    }

    public void setMainCertTypeName(String mainCertTypeName) {
        this.mainCertTypeName = mainCertTypeName;
    }

    public String getMainCertNum() {
        return mainCertNum;
    }

    public void setMainCertNum(String mainCertNum) {
        this.mainCertNum = mainCertNum;
    }

    public Integer getBillDay() {
        return billDay;
    }

    public void setBillDay(Integer billDay) {
        this.billDay = billDay;
    }

    public String getAuditEnsureName() {
        return auditEnsureName;
    }

    public void setAuditEnsureName(String auditEnsureName) {
        this.auditEnsureName = auditEnsureName;
    }

    public String getAuditLegalMan() {
        return auditLegalMan;
    }

    public void setAuditLegalMan(String auditLegalMan) {
        this.auditLegalMan = auditLegalMan;
    }

    public String getEnsuremanBusinessPlace() {
        return ensuremanBusinessPlace;
    }

    public void setEnsuremanBusinessPlace(String ensuremanBusinessPlace) {
        this.ensuremanBusinessPlace = ensuremanBusinessPlace;
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

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getGrantBackMes() {
        return grantBackMes;
    }

    public void setGrantBackMes(String grantBackMes) {
        this.grantBackMes = grantBackMes;
    }

    public String getCurrUserCode() {
        return currUserCode;
    }

    public void setCurrUserCode(String currUserCode) {
        this.currUserCode = currUserCode;
    }

    public ContractFee getCtrFee() {
        return ctrFee;
    }

    public void setCtrFee(ContractFee ctrFee) {
        this.ctrFee = ctrFee;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public FeeInfoEx getFeeInfo() {
        return feeInfo;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getFeePaymentAmount() {
        return feePaymentAmount;
    }

    public void setFeePaymentAmount(Double feePaymentAmount) {
        this.feePaymentAmount = feePaymentAmount;
    }

    public Double getGrantFailAmount() {
        return grantFailAmount;
    }

    public void setGrantFailAmount(Double grantFailAmount) {
        this.grantFailAmount = grantFailAmount;
    }

    public Double getFeeUrgedService() {
        return feeUrgedService;
    }

    public void setFeeUrgedService(Double feeUrgedService) {
        this.feeUrgedService = feeUrgedService;
    }

    public void setFeeInfo(FeeInfoEx feeInfoEx) {
        this.feeInfo = feeInfoEx;
    }

    public LoanBank getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(LoanBank loanBank) {
        this.loanBank = loanBank;
    }

    public String getCustomerPhoneFirst() {
        return customerPhoneFirst;
    }

    public void setCustomerPhoneFirst(String customerPhoneFirst) {
        this.customerPhoneFirst = customerPhoneFirst;
    }

    public String getCustomerPhoneSecond() {
        return customerPhoneSecond;
    }

    public void setCustomerPhoneSecond(String customerPhoneSecond) {
        this.customerPhoneSecond = customerPhoneSecond;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContractMonths() {
        return contractMonths;
    }

    public void setContractMonths(String contractMonths) {
        this.contractMonths = contractMonths;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDictOperateResult() {
        return dictOperateResult;
    }

    public void setDictOperateResult(String dictOperateResult) {
        this.dictOperateResult = dictOperateResult;
    }

    public List<LoanCoborrower> getCoborrowers() {
        return coborrowers;
    }

    public void setCoborrowers(List<LoanCoborrower> coborrowers) {
        this.coborrowers = coborrowers;
    }

    public List<MiddlePerson> getMiddlePersons() {
        return middlePersons;
    }

    public void setMiddlePersons(List<MiddlePerson> middlePersons) {
        this.middlePersons = middlePersons;
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

    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }

    public String getLoanStoreOrgId() {
        return loanStoreOrgId;
    }

    public void setLoanStoreOrgId(String loanStoreOrgId) {
        this.loanStoreOrgId = loanStoreOrgId;
    }

    public String getLoanStoreOrgName() {
        return loanStoreOrgName;
    }

    public void setLoanStoreOrgName(String loanStoreOrgName) {
        this.loanStoreOrgName = loanStoreOrgName;
    }

    public String getBackResponse() {
        return backResponse;
    }

    public void setBackResponse(String backResponse) {
        this.backResponse = backResponse;
    }

    public String getApplyBankName() {
        return applyBankName;
    }

    public void setApplyBankName(String applyBankName) {
        this.applyBankName = applyBankName;
    }

    public String getApplyBankBranch() {
        return applyBankBranch;
    }

    public void setApplyBankBranch(String applyBankBranch) {
        this.applyBankBranch = applyBankBranch;
    }

    public String getApplyBankAccount() {
        return applyBankAccount;
    }

    public void setApplyBankAccount(String applyBankAccount) {
        this.applyBankAccount = applyBankAccount;
    }

    public Date getSignEndDay() {
        return signEndDay;
    }

    public void setSignEndDay(Date signEndDay) {
        this.signEndDay = signEndDay;
    }

    public String getSigningPlatformName() {
        return signingPlatformName;
    }

    public void setSigningPlatformName(String signingPlatformName) {
        this.signingPlatformName = signingPlatformName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public double getUnUrgeService() {
        return unUrgeService;
    }

    public void setUnUrgeService(double unUrgeService) {
        this.unUrgeService = unUrgeService;
    }

    public String getFrozenFlag() {
        return frozenFlag;
    }

    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getFrozenCode() {
        return frozenCode;
    }

    public void setFrozenCode(String frozenCode) {
        this.frozenCode = frozenCode;
    }

    public String getFrozenReason() {
        return frozenReason;
    }

    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason;
    }

    /**
     * @return the orderField
     */
    public String getOrderField() {
        return orderField;
    }

    /**
     * @param orderField the String orderField to set
     */
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getGrantSureBackReason() {
        return grantSureBackReason;
    }

    public void setGrantSureBackReason(String grantSureBackReason) {
        this.grantSureBackReason = grantSureBackReason;
    }

    public String getKingBankProvinceName() {
        return kingBankProvinceName;
    }

    public void setKingBankProvinceName(String kingBankProvinceName) {
        this.kingBankProvinceName = kingBankProvinceName;
    }

    public String getKingBankCityName() {
        return kingBankCityName;
    }

    public void setKingBankCityName(String kingBankCityName) {
        this.kingBankCityName = kingBankCityName;
    }

    public String getKingBankProvinceCode() {
        return kingBankProvinceCode;
    }

    public void setKingBankProvinceCode(String kingBankProvinceCode) {
        this.kingBankProvinceCode = kingBankProvinceCode;
    }

    public String getKingBankCityCode() {
        return kingBankCityCode;
    }

    public void setKingBankCityCode(String kingBankCityCode) {
        this.kingBankCityCode = kingBankCityCode;
    }

    public List<CityInfo> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<CityInfo> provinceList) {
        this.provinceList = provinceList;
    }

    public List<FyAreaCode> getFyProvinceList() {
        return fyProvinceList;
    }

    public void setFyProvinceList(List<FyAreaCode> fyProvinceList) {
        this.fyProvinceList = fyProvinceList;
    }

    /**
     * @return the rateInfoList
     */
    public List<RateInfo> getRateInfoList() {
        return rateInfoList;
    }

    /**
     * @param rateInfoList the List<RateInfo> rateInfoList to set
     */
    public void setRateInfoList(List<RateInfo> rateInfoList) {
        this.rateInfoList = rateInfoList;
    }

    /**
     * @return the files
     */
    public List<ContractFile> getFiles() {
        return files;
    }

    /**
     * @param files the List<ContractFile> files to set
     */
    public void setFiles(List<ContractFile> files) {
        this.files = files;
    }

    /**
     * @return the rateEffectiveFlag
     */
    public String getRateEffectiveFlag() {
        return rateEffectiveFlag;
    }

    /**
     * @param rateEffectiveFlag the String rateEffectiveFlag to set
     */
    public void setRateEffectiveFlag(String rateEffectiveFlag) {
        this.rateEffectiveFlag = rateEffectiveFlag;
    }

    /**
     * @return the curRate
     */
    public String getCurRate() {
        return curRate;
    }

    /**
     * @param curRate the String curRate to set
     */
    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the String imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return the largeAmountImageUrl
     */
    public String getLargeAmountImageUrl() {
        return largeAmountImageUrl;
    }

    /**
     * @param largeAmountImageUrl the String largeAmountImageUrl to set
     */
    public void setLargeAmountImageUrl(String largeAmountImageUrl) {
        this.largeAmountImageUrl = largeAmountImageUrl;
    }

    /**
     * @return the largeAmountFlag
     */
    public String getLargeAmountFlag() {
        return largeAmountFlag;
    }

    /**
     * @param largeAmountFlag the String largeAmountFlag to set
     */
    public void setLargeAmountFlag(String largeAmountFlag) {
        this.largeAmountFlag = largeAmountFlag;
    }

    /**
     * @return the protocolImgUrl
     */
    public String getProtocolImgUrl() {
        return protocolImgUrl;
    }

    /**
     * @param protocolImgUrl the String protocolImgUrl to set
     */
    public void setProtocolImgUrl(String protocolImgUrl) {
        this.protocolImgUrl = protocolImgUrl;
    }

    /**
     * @return the lastDealTime
     */
    public String getLastDealTime() {
        return lastDealTime;
    }

    /**
     * @param lastDealTime the String lastDealTime to set
     */
    public void setLastDealTime(String lastDealTime) {
        this.lastDealTime = lastDealTime;
    }

    /**
     * @return the trusteeshipFlag
     */
    public String getTrusteeshipFlag() {
        return trusteeshipFlag;
    }

    /**
     * @param trusteeshipFlag the String trusteeshipFlag to set
     */
    public void setTrusteeshipFlag(String trusteeshipFlag) {
        this.trusteeshipFlag = trusteeshipFlag;
    }

    /**
     * @return the trustFlag
     */
    public String getTrustFlag() {
        return trustFlag;
    }

    /**
     * @param trustFlag the String trustFlag to set
     */
    public void setTrustFlag(String trustFlag) {
        this.trustFlag = trustFlag;
    }

    /**
     * @return the trustCash
     */
    public String getTrustCash() {
        return trustCash;
    }

    /**
     * @param trustCash the String trustCash to set
     */
    public void setTrustCash(String trustCash) {
        this.trustCash = trustCash;
    }

    /**
     * @return the trustRecharge
     */
    public String getTrustRecharge() {
        return trustRecharge;
    }

    /**
     * @param trustRecharge the String trustRecharge to set
     */
    public void setTrustRecharge(String trustRecharge) {
        this.trustRecharge = trustRecharge;
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

    /**
     * @return the timeOutPoint
     */
    public Date getTimeOutPoint() {
        return timeOutPoint;
    }

    /**
     * @param timeOutPoint the Date timeOutPoint to set
     */
    public void setTimeOutPoint(Date timeOutPoint) {
        this.timeOutPoint = timeOutPoint;
    }

    /**
     * @return the kingStatus
     */
    public String getKingStatus() {
        return kingStatus;
    }

    /**
     * @param kingStatus the String kingStatus to set
     */
    public void setKingStatus(String kingStatus) {
        this.kingStatus = kingStatus;
    }

   /**
     * @return the custBankAccountName
     */
    public String getCustBankAccountName() {
        return custBankAccountName;
    }

    /**
     * @param custBankAccountName the String custBankAccountName to set
     */
    public void setCustBankAccountName(String custBankAccountName) {
        this.custBankAccountName = custBankAccountName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the String email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone the String mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

	/**
     * @return the monthRate
     */
    public String getMonthRate() {
        return monthRate;
    }

    /**
     * @param monthRate the String monthRate to set
     */
    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public String getGrantSureBackReasonCode() {
		return grantSureBackReasonCode;
	}

	public void setGrantSureBackReasonCode(String grantSureBackReasonCode) {
		this.grantSureBackReasonCode = grantSureBackReasonCode;
	}

	/**
     * @return the captchaIfConfirm
     */
    public String getCaptchaIfConfirm() {
        return captchaIfConfirm;
    }

    /**
     * @param captchaIfConfirm the String captchaIfConfirm to set
     */
    public void setCaptchaIfConfirm(String captchaIfConfirm) {
        this.captchaIfConfirm = captchaIfConfirm;
    }

    /**
     * @return the confirmTimeout
     */
    public Date getConfirmTimeout() {
        return confirmTimeout;
    }

    /**
     * @param confirmTimeout the Date confirmTimeout to set
     */
    public void setConfirmTimeout(Date confirmTimeout) {
        this.confirmTimeout = confirmTimeout;
    }

    public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerPin() {
		return customerPin;
	}

	public void setCustomerPin(String customerPin) {
		this.customerPin = customerPin;
	}

    /**
     * @return the appSignFlag
     */
    public String getAppSignFlag() {
        return appSignFlag;
    }

    /**
     * @param appSignFlag the String appSignFlag to set
     */
    public void setAppSignFlag(String appSignFlag) {
        this.appSignFlag = appSignFlag;
    }

    /**
     * @return the idValidFlag
     */
    public String getIdValidFlag() {
        return idValidFlag;
    }

    /**
     * @param idValidFlag the String idValidFlag to set
     */
    public void setIdValidFlag(String idValidFlag) {
        this.idValidFlag = idValidFlag;
    }

    /**
     * @return the composePhotoId
     */
    public String getComposePhotoId() {
        return composePhotoId;
    }

    /**
     * @param composePhotoId the String composePhotoId to set
     */
    public void setComposePhotoId(String composePhotoId) {
        this.composePhotoId = composePhotoId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the String customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the bankIsRareword
     */
    public String getBankIsRareword() {
        return bankIsRareword;
    }

    /**
     * @param bankIsRareword the String bankIsRareword to set
     */
    public void setBankIsRareword(String bankIsRareword) {
        this.bankIsRareword = bankIsRareword;
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
     * @return the upLimit
     */
    public String getUpLimit() {
        return upLimit;
    }

    /**
     * @param upLimit the String upLimit to set
     */
    public void setUpLimit(String upLimit) {
        this.upLimit = upLimit;
    }

    /**
     * @return the jxVersion
     */
    public String getJxVersion() {
        return jxVersion;
    }

    /**
     * @param jxVersion the String jxVersion to set
     */
    public void setJxVersion(String jxVersion) {
        this.jxVersion = jxVersion;
    }

    /**
     * @return the jxId
     */
    public String getJxId() {
        return jxId;
    }

    /**
     * @param jxId the String jxId to set
     */
    public void setJxId(String jxId) {
        this.jxId = jxId;
    }

    /**
     * @return the registFlag
     */
    public String getRegistFlag() {
        return registFlag;
    }

    /**
     * @param registFlag the String registFlag to set
     */
    public void setRegistFlag(String registFlag) {
        this.registFlag = registFlag;
    }

    /**
     * @return the signUpFlag
     */
    public String getSignUpFlag() {
        return signUpFlag;
    }

    /**
     * @param signUpFlag the String signUpFlag to set
     */
    public void setSignUpFlag(String signUpFlag) {
        this.signUpFlag = signUpFlag;
    }

    /**
     * @return the isStoreAssistant
     */
    public String getIsStoreAssistant() {
        return isStoreAssistant;
    }

    /**
     * @param isStoreAssistant the String isStoreAssistant to set
     */
    public void setIsStoreAssistant(String isStoreAssistant) {
        this.isStoreAssistant = isStoreAssistant;
    }

    /**
     * @return the businessProveId
     */
    public String getBusinessProveId() {
        return businessProveId;
    }

    /**
     * @param businessProveId the String businessProveId to set
     */
    public void setBusinessProveId(String businessProveId) {
        this.businessProveId = businessProveId;
    }

    /**
     * @return the riskLevel
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * @param riskLevel the String riskLevel to set
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * @return the confirmSignDate
     */
    public String getConfirmSignDate() {
        return confirmSignDate;
    }

    /**
     * @param confirmSignDate the String confirmSignDate to set
     */
    public void setConfirmSignDate(String confirmSignDate) {
        this.confirmSignDate = confirmSignDate;
    }

    /**
     * @return the signStartDay
     */
    public Date getSignStartDay() {
        return signStartDay;
    }

    /**
     * @param signStartDay the Date signStartDay to set
     */
    public void setSignStartDay(Date signStartDay) {
        this.signStartDay = signStartDay;
    }

    /**
     * @return the channelFlagAdd
     */
    public String getChannelFlagAdd() {
        return channelFlagAdd;
    }

    /**
     * @param channelFlagAdd the String channelFlagAdd to set
     */
    public void setChannelFlagAdd(String channelFlagAdd) {
        this.channelFlagAdd = channelFlagAdd;
    }

    /**
     * @return the oldChannelFlag
     */
    public String getOldChannelFlag() {
        return oldChannelFlag;
    }

    /**
     * @param oldChannelFlag the String oldChannelFlag to set
     */
    public void setOldChannelFlag(String oldChannelFlag) {
        this.oldChannelFlag = oldChannelFlag;
    }

    /**
     * @return the idValidScore
     */
    public Float getIdValidScore() {
        return idValidScore;
    }

    /**
     * @param idValidScore the Float idValidScore to set
     */
    public void setIdValidScore(Float idValidScore) {
        this.idValidScore = idValidScore;
    }

    /**
     * @return the idValidMessage
     */
    public String getIdValidMessage() {
        return idValidMessage;
    }

    /**
     * @param idValidMessage the String idValidMessage to set
     */
    public void setIdValidMessage(String idValidMessage) {
        this.idValidMessage = idValidMessage;
    }

	public String getHasOnlineTime() {
		return hasOnlineTime;
	}

	public void setHasOnlineTime(String hasOnlineTime) {
		this.hasOnlineTime = hasOnlineTime;
	}


	public String getCjAuthenFailure() {
		return cjAuthenFailure;
	}

	public void setCjAuthenFailure(String cjAuthenFailure) {
		this.cjAuthenFailure = cjAuthenFailure;
	}

	public String getCjAuthen() {
		return cjAuthen;
	}

	public void setCjAuthen(String cjAuthen) {
		this.cjAuthen = cjAuthen;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public Double getKinnobuQuota() {
		return kinnobuQuota;
	}

	public void setKinnobuQuota(Double kinnobuQuota) {
		this.kinnobuQuota = kinnobuQuota;
	}

	public Double getKinnobuUsed() {
		return kinnobuUsed;
	}

	public void setKinnobuUsed(Double kinnobuUsed) {
		this.kinnobuUsed = kinnobuUsed;
	}

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getMaddressName() {
		return maddressName;
	}

	public void setMaddressName(String maddressName) {
		this.maddressName = maddressName;
	}

	public String getBackTimeFlag() {
		return backTimeFlag;
	}

	public void setBackTimeFlag(String backTimeFlag) {
		this.backTimeFlag = backTimeFlag;
	}

	public String getItem_distance() {
		return item_distance;
	}

	public void setItem_distance(String item_distance) {
		this.item_distance = item_distance;
	}

	public String getOutside_flag() {
		return outside_flag;
	}

	public void setOutside_flag(String outside_flag) {
		this.outside_flag = outside_flag;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getIssplit() {
		return issplit;
	}

	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}

	public ContractFeeTemp getCtrFeeZCJ() {
		return ctrFeeZCJ;
	}

	public void setCtrFeeZCJ(ContractFeeTemp ctrFeeZCJ) {
		this.ctrFeeZCJ = ctrFeeZCJ;
	}

	public ContractFeeTemp getCtrFeeJINXIN() {
		return ctrFeeJINXIN;
	}

	public void setCtrFeeJINXIN(ContractFeeTemp ctrFeeJINXIN) {
		this.ctrFeeJINXIN = ctrFeeJINXIN;
	}

	

	public ContractTemp getContractZCJ() {
		return contractZCJ;
	}

	public void setContractZCJ(ContractTemp contractZCJ) {
		this.contractZCJ = contractZCJ;
	}

	public ContractTemp getContractJINXIN() {
		return contractJINXIN;
	}

	public void setContractJINXIN(ContractTemp contractJINXIN) {
		this.contractJINXIN = contractJINXIN;
	}

	public FeeInfoEx getFeeInfoZCJ() {
		return feeInfoZCJ;
	}

	public void setFeeInfoZCJ(FeeInfoEx feeInfoZCJ) {
		this.feeInfoZCJ = feeInfoZCJ;
	}

	public FeeInfoEx getFeeInfoJINXIN() {
		return feeInfoJINXIN;
	}

	public void setFeeInfoJINXIN(FeeInfoEx feeInfoJINXIN) {
		this.feeInfoJINXIN = feeInfoJINXIN;
	}

	public ContractFeeTemp getZcjFeeTemp() {
		return zcjFeeTemp;
	}

	public void setZcjFeeTemp(ContractFeeTemp zcjFeeTemp) {
		this.zcjFeeTemp = zcjFeeTemp;
	}

	public ContractFeeTemp getJinXinFeeTemp() {
		return jinXinFeeTemp;
	}

	public void setJinXinFeeTemp(ContractFeeTemp jinXinFeeTemp) {
		this.jinXinFeeTemp = jinXinFeeTemp;
	}

	public List<JyjBorrowBankConfigure> getJyjBank() {
		return jyjBank;
	}

	public void setJyjBank(List<JyjBorrowBankConfigure> jyjBank) {
		this.jyjBank = jyjBank;
	}

	
}
