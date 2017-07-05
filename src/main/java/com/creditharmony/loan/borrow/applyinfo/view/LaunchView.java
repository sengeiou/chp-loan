/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.viewLaunchView.java
 * @Create By 张灏
 * @Create In 2015年12月15日 下午1:39:56
 */
package com.creditharmony.loan.borrow.applyinfo.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.CustomerLivings;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompManage;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanPersonalCertificate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanRemark;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;

/**
 * @Class Name LaunchView
 * @author 张灏
 * @Create In 2015年12月15日
 */
public class LaunchView extends BaseBusinessView {

	//电销组织机构ID
    private String consTelesalesOrgcode;
	private String id;
	//借款编号
	private String loanCode;
	// 客户信息
	private LoanCustomer loanCustomer;
	// 客户居住情况
	private CustomerLivings customerLivings;
	// 账户信息
	private LoanBank loanBank;
	// 共借人基本信息
	private List<LoanCoborrower> loanCoborrower = new ArrayList<LoanCoborrower>();
	// 职业信息
	private LoanCompany customerLoanCompany;
	// 信用资料信息
	private List<LoanCreditInfo> loanCreditInfoList;
	// 客户房产信息
	private List<LoanHouse> customerLoanHouseList;
	// 借款信息主信息
	private LoanInfo loanInfo;
	// 配偶信息
	private LoanMate loanMate;
	// 借款备注信息
	private LoanRemark loanRemark;
	// 联系人信息
	private List<Contact> customerContactList;
	// 客户编码
	private String customerCode;
	// 客户姓名
	private String customerName;
	// 性别
	private String customerSex;
	// 出生日期
	private Date customerBirthday;
	// 证件类型
	private String dictCertType;
	// 证件号码
	private String mateCertNum;
	// 发证机关
	private String customerCertOrg;
	// 身份证有效期开始时间
	private Date idStartDay;
	// 身份证有效期结束时间
	private Date idEndDay;
	// 手机号
	private String customerMobilePhone;
	// 行业类别
	private String dictCompIndustry;
	// 客户姓名ocr地址
	private String customerNameOcr;
	// 客户身份证ocr地址
	private String customerCretOcr;
	// 标识
	private String flag;
	// 门店所在省
	private String orgProvince;
	// 门店所在省Code
	private String orgProvinceCode;
	// 门店所在市Code
	private String orgCityCode;
	// 门店所在市
	private String orgCity;
	// 门店编号
	private String orgCode;
	// 门店名字
	private String orgName;
	// 外访员员工编号
	private String outSideUserCode;
	// 外访员员
	private String outSideUserName;
	// 外访员距离
	private BigDecimal itemDistance;
	// 共借人
	private String coborrowerNames;
	// 借款标识
	private String loanFlag;
	// 借款标识
	private String loanFlagCode;
	// 产品名称
	private String productName;
	// 产品Code
	private String productCode;
	// 咨询ID
	private String consultId;
	// 审核状态
	private String dictLoanStatus;
	//
	private String dictLoanStatusCode;
	//上一个节点状态
	private String lastLoanStatus;
	// 预设定响应
	private String preResponse;
	// 门店组织机构ID
	private String storeOrgId;
	// 团队经理Code
	private String loanTeamManagerCode;
	// 团队经理
	private String loanTeamManagerName;
	// 客户经理Code
	private String loanManagerCode;
	// 客户经理 
	private String loanManagerName;
	// 外访完成时间
	private String visitFinishTime;
	// 初始化省份显示
	private List<CityInfo> provinceList = new ArrayList<CityInfo>();
	// 产品列表
	private List<LoanPrdMngEntity> productList = new ArrayList<LoanPrdMngEntity>();
	// 签约平台
	private String signingPlatformName;
	// 区号
	private String areaNo;
	// 座机号
	private String telephoneNo;
	// 客服经理
	private String agentName;
	// 客服经理
	private String agentCode;
	// 处理人
	private String dealUser;
	// 影像地址
	private String imageUrl;
	// 出汇金时间
	private Date outtoLoanTime;
	// 超时标识
	private String timeOutFlag;
	// 超时截至日期
	private Date timeOutPoint;
    // 结清再贷/首次贷款
	private String dictLoanType;
	// 结清再贷/首次贷款 名字
	private String dictLoanTypeName;
	// 门店拒绝原因
	private String rejectReason;
	// 是否生僻字
	private String bankIsRareword;
	// 排序字段
	private String orderField;
	// 是否为门店副理
	private String isStoreAssistant;
	// 自定义令牌ID
	private String defTokenId;
	// 自定义令牌
	private String defToken;
	// 重复提交MESSAGE
	private String message;
	// 操作类型 saveDate ： 0
	private String operType;
	//咨询时间
	private Date consultTime;
	// 是否借么APP
	private String isBorrow;
	//版本
	private String  oneedition;
	
	//页签名称
	private String  tab;
	
	//将联系人字段分为亲属联系人 工作证明人 其他联系人 单独列出
	private Map<String,List<Contact>> contactMap;
	//经营信息
	private LoanCompManage loanCompManage;
	
	//证件信息
	private LoanPersonalCertificate loanPersonalCertificate;

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getOneedition() {
		return oneedition;
	}

	public void setOneedition(String oneedition) {
		this.oneedition = oneedition;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public String getConsTelesalesOrgcode() {
		return consTelesalesOrgcode;
	}

	public void setConsTelesalesOrgcode(String consTelesalesOrgcode) {
		this.consTelesalesOrgcode = consTelesalesOrgcode;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getOuttoLoanTime() {
		return outtoLoanTime;
	}

	public void setOuttoLoanTime(Date outtoLoanTime) {
		this.outtoLoanTime = outtoLoanTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public String getPreResponse() {
		return preResponse;
	}

	public void setPreResponse(String preResponse) {
		this.preResponse = preResponse;
	}

	public String getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LoanCustomer getLoanCustomer() {
		return loanCustomer;
	}

	public void setLoanCustomer(LoanCustomer loanCustomer) {
		this.loanCustomer = loanCustomer;
	}

	public CustomerLivings getCustomerLivings() {
		return customerLivings;
	}

	public void setCustomerLivings(CustomerLivings customerLivings) {
		this.customerLivings = customerLivings;
	}

	public LoanBank getLoanBank() {
		return loanBank;
	}

	public void setLoanBank(LoanBank loanBank) {
		this.loanBank = loanBank;
	}

	public List<LoanCoborrower> getLoanCoborrower() {
		return loanCoborrower;
	}

	public void setLoanCoborrower(List<LoanCoborrower> loanCoborrower) {
		this.loanCoborrower = loanCoborrower;
	}

	public LoanCompany getCustomerLoanCompany() {
		return customerLoanCompany;
	}

	public void setCustomerLoanCompany(LoanCompany customerLoanCompany) {
		this.customerLoanCompany = customerLoanCompany;
	}

	public List<LoanCreditInfo> getLoanCreditInfoList() {
		return loanCreditInfoList;
	}

	public void setLoanCreditInfoList(List<LoanCreditInfo> loanCreditInfoList) {
		this.loanCreditInfoList = loanCreditInfoList;
	}

	public List<LoanHouse> getCustomerLoanHouseList() {
		return customerLoanHouseList;
	}

	public void setCustomerLoanHouseList(List<LoanHouse> customerLoanHouseList) {
		this.customerLoanHouseList = customerLoanHouseList;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}

	public LoanMate getLoanMate() {
		return loanMate;
	}

	public void setLoanMate(LoanMate loanMate) {
		this.loanMate = loanMate;
	}

	public LoanRemark getLoanRemark() {
		return loanRemark;
	}

	public void setLoanRemark(LoanRemark loanRemark) {
		this.loanRemark = loanRemark;
	}

	public List<Contact> getCustomerContactList() {
		return customerContactList;
	}

	public void setCustomerContactList(List<Contact> customerContactList) {
		this.customerContactList = customerContactList;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	public Date getCustomerBirthday() {
		return customerBirthday;
	}

	public void setCustomerBirthday(Date customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getDictCertType() {
		return dictCertType;
	}

	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}

	public String getMateCertNum() {
		return mateCertNum;
	}

	public void setMateCertNum(String mateCertNum) {
		this.mateCertNum = mateCertNum;
	}

	public String getCustomerCertOrg() {
		return customerCertOrg;
	}

	public void setCustomerCertOrg(String customerCertOrg) {
		this.customerCertOrg = customerCertOrg;
	}

	public Date getIdStartDay() {
		return idStartDay;
	}

	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}

	public Date getIdEndDay() {
		return idEndDay;
	}

	public void setIdEndDay(Date idEndDay) {
		this.idEndDay = idEndDay;
	}

	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	public String getDictCompIndustry() {
		return dictCompIndustry;
	}

	public void setDictCompIndustry(String dictCompIndustry) {
		this.dictCompIndustry = dictCompIndustry;
	}

	public String getCustomerNameOcr() {
		return customerNameOcr;
	}

	public void setCustomerNameOcr(String customerNameOcr) {
		this.customerNameOcr = customerNameOcr;
	}

	public String getCustomerCretOcr() {
		return customerCretOcr;
	}

	public void setCustomerCretOcr(String customerCretOcr) {
		this.customerCretOcr = customerCretOcr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrgProvince() {
		return orgProvince;
	}

	public void setOrgProvince(String orgProvince) {
		this.orgProvince = orgProvince;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getOutSideUserCode() {
		return outSideUserCode;
	}

	public void setOutSideUserCode(String outSideUserCode) {
		this.outSideUserCode = outSideUserCode;
	}

	public BigDecimal getItemDistance() {
		return itemDistance;
	}

	public void setItemDistance(BigDecimal itemDistance) {
		this.itemDistance = itemDistance;
	}

	public String getCoborrowerNames() {
		return coborrowerNames;
	}

	public void setCoborrowerNames(String coborrowerNames) {
		this.coborrowerNames = coborrowerNames;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	/**
	 * @return the loanFlagCode
	 */
	public String getLoanFlagCode() {
		return loanFlagCode;
	}

	/**
	 * @param loanFlagCode
	 *            the String loanFlagCode to set
	 */
	public void setLoanFlagCode(String loanFlagCode) {
		this.loanFlagCode = loanFlagCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getConsultId() {
		return consultId;
	}

	public void setConsultId(String consultId) {
		this.consultId = consultId;
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

	/**
     * @return the lastLoanStatus
     */
    public String getLastLoanStatus() {
        return lastLoanStatus;
    }

    /**
     * @param lastLoanStatus the String lastLoanStatus to set
     */
    public void setLastLoanStatus(String lastLoanStatus) {
        this.lastLoanStatus = lastLoanStatus;
    }

    public List<CityInfo> getProvinceList() {
		return provinceList;
	}

	public String getLoanTeamManagerName() {
		return loanTeamManagerName;
	}

	public void setLoanTeamManagerName(String loanTeamManagerName) {
		this.loanTeamManagerName = loanTeamManagerName;
	}

	public void setProvinceList(List<CityInfo> provinceList) {
		this.provinceList = provinceList;
	}

	public List<LoanPrdMngEntity> getProductList() {
		return productList;
	}

	public void setProductList(List<LoanPrdMngEntity> productList) {
		this.productList = productList;
	}

	public String getSigningPlatformName() {
		return signingPlatformName;
	}

	public void setSigningPlatformName(String signingPlatformName) {
		this.signingPlatformName = signingPlatformName;
	}

	public String getOrgProvinceCode() {
		return orgProvinceCode;
	}

	public void setOrgProvinceCode(String orgProvinceCode) {
		this.orgProvinceCode = orgProvinceCode;
	}

	public String getOrgCityCode() {
		return orgCityCode;
	}

	public void setOrgCityCode(String orgCityCode) {
		this.orgCityCode = orgCityCode;
	}

	public String getVisitFinishTime() {
		return visitFinishTime;
	}

	public void setVisitFinishTime(String visitFinishTime) {
		this.visitFinishTime = visitFinishTime;
	}

	public String getOutSideUserName() {
		return outSideUserName;
	}

	public void setOutSideUserName(String outSideUserName) {
		this.outSideUserName = outSideUserName;
	}

	public String getTimeOutFlag() {
		return timeOutFlag;
	}

	public void setTimeOutFlag(String timeOutFlag) {
		this.timeOutFlag = timeOutFlag;
	}

	public Date getTimeOutPoint() {
		return timeOutPoint;
	}

	public void setTimeOutPoint(Date timeOutPoint) {
		this.timeOutPoint = timeOutPoint;
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
     * @return the dictLoanTypeName
     */
    public String getDictLoanTypeName() {
        return dictLoanTypeName;
    }

    /**
     * @param dictLoanTypeName the String dictLoanTypeName to set
     */
    public void setDictLoanTypeName(String dictLoanTypeName) {
        this.dictLoanTypeName = dictLoanTypeName;
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
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @return the defTokenId
     */
    public String getDefTokenId() {
        return defTokenId;
    }

    /**
     * @param defTokenId the String defTokenId to set
     */
    public void setDefTokenId(String defTokenId) {
        this.defTokenId = defTokenId;
    }

    /**
     * @return the defToken
     */
    public String getDefToken() {
        return defToken;
    }

    /**
     * @param defToken the String defToken to set
     */
    public void setDefToken(String defToken) {
        this.defToken = defToken;
    }

    /**
     * @param orgCode the String orgCode to set
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
     * @param orgName the String orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the String message to set
     */
    public void setMessage(String message) {
        this.message = message;
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

	public Date getConsultTime() {
		return consultTime;
	}

	public void setConsultTime(Date consultTime) {
		this.consultTime = consultTime;
	}

	public Map<String, List<Contact>> getContactMap() {
		return contactMap;
	}

	public void setContactMap(Map<String, List<Contact>> contactMap) {
		this.contactMap = contactMap;
	}

	public LoanCompManage getLoanCompManage() {
		return loanCompManage;
	}

	public void setLoanCompManage(LoanCompManage loanCompManage) {
		this.loanCompManage = loanCompManage;
	}

	public LoanPersonalCertificate getLoanPersonalCertificate() {
		return loanPersonalCertificate;
	}

	public void setLoanPersonalCertificate(LoanPersonalCertificate loanPersonalCertificate) {
		this.loanPersonalCertificate = loanPersonalCertificate;
	}
	
	public String getIsBorrow() {
		return isBorrow;
	}

	public void setIsBorrow(String isBorrow) {
		this.isBorrow = isBorrow;
	}

}
