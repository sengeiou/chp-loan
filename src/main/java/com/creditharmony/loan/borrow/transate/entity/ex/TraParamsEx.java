package com.creditharmony.loan.borrow.transate.entity.ex;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 信借已办列表检索参数类
 * @Class Name TraParams
 * @author lirui
 * @Create In 2015年12月2日
 */
@SuppressWarnings("serial")
public class TraParamsEx extends DataEntity<TraParamsEx>{
	private String loanCustomerName;	// 客户姓名
	private String customerCertNum;		// 证件号码
	private String isUrgent;			// 是否加急
	private String userName;			// 销售人员姓名
	private String userCode;			// 当前登录人编码
	private String products;			// 产品
	private String markings;			// 标识
	private String sources;				// 系统来源
	private String loanIsPhone;			// 是否电销
	private String dictIsAdditional;	// 是否追加
	private String orgCode; 			// 门店编码
	private String orgName; 			// 门店名称
	private String[] orgId;
	private String dictLoanStatus;		// 申请状态
	private String dictPayStatus;		// 还款状态
	private String backFlag;            // 退回标识	
	
	private String servicename;            //客服名称	
	private String empname;            // 外访人员	
	private String applicationProduct;
	private String frozenCode;
	private String revisitStatus;       //回访状态
	 
	public String getApplicationProduct() {
		return applicationProduct;
	}
	public void setApplicationProduct(String applicationProduct) {
		this.applicationProduct = applicationProduct;
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
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getDictIsAdditional() {
		return dictIsAdditional;
	}
	public void setDictIsAdditional(String dictIsAdditional) {
		this.dictIsAdditional = dictIsAdditional;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getSources() {
		return sources;
	}
	public void setSources(String sources) {
		this.sources = sources;
	}
	public String getMarkings() {
		return markings;
	}
	public void setMarkings(String markings) {
		this.markings = markings;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
		public String getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String[] getOrgId() {
		return orgId;
	}
	public void setOrgId(String[] orgId) {
		this.orgId = orgId;
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
	public String getFrozenCode() {
		return frozenCode;
	}
	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}	
	public String getRevisitStatus() {
		return revisitStatus;
	}
	public void setRevisitStatus(String revisitStatus) {
		this.revisitStatus = revisitStatus;
	}	
	
}
