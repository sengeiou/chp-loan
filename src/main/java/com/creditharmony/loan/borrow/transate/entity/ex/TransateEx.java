package com.creditharmony.loan.borrow.transate.entity.ex;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 信借已办列表实体类
 * @Class Name TransateEntity
 * @author lirui
 * @Create In 2015年12月2日
 */
@SuppressWarnings("serial")
public class TransateEx extends DataEntity<TransateEx>{
	private String loanCode;				// 借款编码
	private String loanCustomerService;		// 客服编号
	private String contractVersion;			// 合同版本号
	private String contractCode;			// 合同编号
	private String loanCustomerName;		// 客户姓名
	private String coroName;				// 共借人
	private String productName;				// 产品名称
	private String dictLoanStatus;			// 申请状态
	private String dictLoanStatusLabel;		// 申请信息名
	private String dictPayStatus;			// 还款状态
	private BigDecimal money;				// 批复金额
	private BigDecimal contractMonths;		// 批复分期
	private String loanTeamManagercode;		// 团队经理编号
	private String teamManagerName;			// 团队经理姓名
	private String userName;				// 客户经理姓名
	private String loanManageCode;			// 客户经理编号
	private Date customerIntoTime;			// 进件时间
	private String loanIsRaise;				// 上调标识
	private String loanIsRaiseLable;		// 上调标识名
	private String loanIsPhone;				// 是否电销(0:否,1:是)
	private String paperless;                // 是否纸质化(0:否,1:是)
	private String loanIsPhoneLabel;		// 是否电销名
	private String loanIsUrgent;			// 是否加急
	private String loanMarking;				// 标识
	private String loanMarkingLable;		// 标识名
	private String model;					// 模式
	private String provinceId;				// 省份编号
	private String cityId;					// 城市编号
	private String loanTeamOrgid;			// 团队机构ID
	private String storeId;					// 门店ID
	private String customerCertNum;			// 证件号码
	private String dictSourceType;			// 版本来源
	private String dictIsAdditional;		// 是否追加借
	private String storeCode;				// 门店code
	private String applyId;					// 流程id
	private Integer frozenApplyTimes;		// 申请冻结次数
	private Date frozenLastApplyTime;		// 申请冻结时间
	
	private String loanCustomerServiceName;	// 客服姓名
	private String loanSurveyEmpName;		// 外访人员姓名
	private String loanSurveyEmpId;			// 外访人员编号
	private Date loanApplyTime;		// 申请冻结时间
	private String frozenCode;			//是否冻结
    private String backFlag;           // 退回标识
	private String servicename;            //客服名称	
	private String empname;            // 外访人员	
    private String applicationProductName;//申请产品类型
    private String auditProductName;//批复产品名称
    private String loanInfoOldOrNewFlag; //在信借申请列表点击办理根据此标识判断是跳转到旧版申请表页面还是新版申请表页面
    private String revisitStatus;//回访状态
    
    private String provinceName;	//省份名称
    private String cityName;		//城市名称
    private String storeName;		//门店名称
    private String customerPhoneFirstTransate;    //手机号
    private String loanStoreOrgId;
    private String loanStoreOrgName;
    private String bestCoborrower;      //最优自然保证人
	
    private String sendStatus;//合同寄送状态
    private String sendEmailStatus;//合同Email寄送状态
    
    private String contractVersionShow;
    private String issplit;
    private String zcjRejectFlag;
    private String customerEmail;//邮箱
    private String emailIfConfirm;
    
    
	public String getIssplit() {
		return issplit;
	}

	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}

	public String getZcjRejectFlag() {
		return zcjRejectFlag;
	}

	public void setZcjRejectFlag(String zcjRejectFlag) {
		this.zcjRejectFlag = zcjRejectFlag;
	}

	public String getContractVersionShow() {
		return contractVersionShow;
	}

	public void setContractVersionShow(String contractVersionShow) {
		this.contractVersionShow = contractVersionShow;
	}

	public String getBestCoborrower() {
		return bestCoborrower;
	}

	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
	}

	public String getLoanStoreOrgName() {
		return loanStoreOrgName;
	}

	public void setLoanStoreOrgName(String loanStoreOrgName) {
		this.loanStoreOrgName = loanStoreOrgName;
	}

	public String getLoanStoreOrgId() {
		return loanStoreOrgId;
	}

	public void setLoanStoreOrgId(String loanStoreOrgId) {
		this.loanStoreOrgId = loanStoreOrgId;
	}

	public String getCustomerPhoneFirstTransate() {
		return customerPhoneFirstTransate;
	}

	public void setCustomerPhoneFirstTransate(String customerPhoneFirstTransate) {
		this.customerPhoneFirstTransate = customerPhoneFirstTransate;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getStoreName() {
		return storeName;
	}
	
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getAuditProductName() {
		return auditProductName;
	}
	public void setAuditProductName(String auditProductName) {
		this.auditProductName = auditProductName;
	}
	public String getApplicationProductName() {
		return applicationProductName;
	}
	public void setApplicationProductName(String applicationProductName) {
		this.applicationProductName = applicationProductName;
	}
    
	

	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getFrozenCode() {
		return frozenCode;
	}
	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getDictLoanStatusLabel() {
		return dictLoanStatusLabel;
	}
	public void setDictLoanStatusLabel(String dictLoanStatusLabel) {
		this.dictLoanStatusLabel = dictLoanStatusLabel;
	}
	public String getLoanIsRaiseLable() {
		return loanIsRaiseLable;
	}
	public void setLoanIsRaiseLable(String loanIsRaiseLable) {
		this.loanIsRaiseLable = loanIsRaiseLable;
	}
	public String getLoanIsPhoneLabel() {
		return loanIsPhoneLabel;
	}
	public void setLoanIsPhoneLabel(String loanIsPhoneLabel) {
		this.loanIsPhoneLabel = loanIsPhoneLabel;
	}
	public String getLoanMarkingLable() {
		return loanMarkingLable;
	}
	public void setLoanMarkingLable(String loanMarkingLable) {
		this.loanMarkingLable = loanMarkingLable;
	}
	public Integer getFrozenApplyTimes() {
		return frozenApplyTimes;
	}
	public void setFrozenApplyTimes(Integer frozenApplyTimes) {
		this.frozenApplyTimes = frozenApplyTimes;
	}
	public Date getFrozenLastApplyTime() {
		return frozenLastApplyTime;
	}
	public void setFrozenLastApplyTime(Date frozenLastApplyTime) {
		this.frozenLastApplyTime = frozenLastApplyTime;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getTeamManagerName() {
		return teamManagerName;
	}
	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}
	public String getLoanManageCode() {
		return loanManageCode;
	}
	public void setLoanManageCode(String loanManageCode) {
		this.loanManageCode = loanManageCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanTeamOrgid() {
		return loanTeamOrgid;
	}
	public void setLoanTeamOrgid(String loanTeamOrgid) {
		this.loanTeamOrgid = loanTeamOrgid;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getDictSourceType() {
		return dictSourceType;
	}
	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}
	public String getDictIsAdditional() {
		return dictIsAdditional;
	}
	public void setDictIsAdditional(String dictIsAdditional) {
		this.dictIsAdditional = dictIsAdditional;
	}
	public String getLoanCustomerService() {
		return loanCustomerService;
	}
	public void setLoanCustomerService(String loanCustomerService) {
		this.loanCustomerService = loanCustomerService;
	}
	public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}		
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getCoroName() {
		return coroName;
	}
	public void setCoroName(String coroName) {
		this.coroName = coroName;
	}			
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public BigDecimal getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(BigDecimal contractMonths) {
		this.contractMonths = contractMonths;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getLoanIsRaise() {
		return loanIsRaise;
	}
	public void setLoanIsRaise(String loanIsRaise) {
		this.loanIsRaise = loanIsRaise;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getLoanIsUrgent() {
		return loanIsUrgent;
	}
	public void setLoanIsUrgent(String loanIsUrgent) {
		this.loanIsUrgent = loanIsUrgent;
	}
	public String getLoanMarking() {
		return loanMarking;
	}
	public void setLoanMarking(String loanMarking) {
		this.loanMarking = loanMarking;
	}
	public String getLoanTeamManagercode() {
		return loanTeamManagercode;
	}
	public void setLoanTeamManagercode(String loanTeamManagercode) {
		this.loanTeamManagercode = loanTeamManagercode;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getLoanCustomerServiceName() {
		return loanCustomerServiceName;
	}
	public void setLoanCustomerServiceName(String loanCustomerServiceName) {
		this.loanCustomerServiceName = loanCustomerServiceName;
	}
	public String getLoanSurveyEmpName() {
		return loanSurveyEmpName;
	}
	public void setLoanSurveyEmpName(String loanSurveyEmpName) {
		this.loanSurveyEmpName = loanSurveyEmpName;
	}
	public String getLoanSurveyEmpId() {
		return loanSurveyEmpId;
	}
	public void setLoanSurveyEmpId(String loanSurveyEmpId) {
		this.loanSurveyEmpId = loanSurveyEmpId;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRevisitStatus() {
		return revisitStatus;
	}
	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}

	public String getPaperless() {
		return paperless;
	}

	public void setPaperless(String paperless) {
		this.paperless = paperless;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getSendEmailStatus() {
		return sendEmailStatus;
	}

	public void setSendEmailStatus(String sendEmailStatus) {
		this.sendEmailStatus = sendEmailStatus;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getEmailIfConfirm() {
		return emailIfConfirm;
	}

	public void setEmailIfConfirm(String emailIfConfirm) {
		this.emailIfConfirm = emailIfConfirm;
	}	
}
