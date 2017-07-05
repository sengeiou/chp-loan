package com.creditharmony.loan.borrow.transate.entity.ex;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 信借信息详情检索参数类
 * @Class Name LoanParams
 * @author lirui
 * @Create In 2015年12月3日
 */
@SuppressWarnings("serial")
public class LoanParamsEx extends DataEntity<TraParamsEx> {	
	private String products;			// 产品名称
	private String auditProducts; //批复产品名称
	
 
	public String getAuditProducts() {
		return auditProducts;
	}
	public void setAuditProducts(String auditProducts) {
		this.auditProducts = auditProducts;
	}
	private String loanCustomerName;	// 客户姓名
	private String teamManageName;		// 团队经理
	private String loanManageName;		// 客户经理
	private String loanIsUrgent;		// 是否加急
	private String loanMarking;			// 标识
	private String model;				// 模式
	private String certNum;				// 证件号码
	private String dictIsAdd;			// 是否追加借
	private String system;				// 来源系统
	private String loanIsPhone;			// 是否电销
	private String orgCode;				// 门店编码
	private String orgName;				// 门店编码
	private String userCode;			// 登录人code
	private String dictPayStatus;		// 还款状态
	private String paperless;           // 是否无纸化
	
	private String loanCustomerServiceName;	// 客服姓名
	private String loanSurveyEmpName;		// 外访人员姓名
	private String revisitStatus;			//回访状态
	
	private String customerPhoneFirstTransate;    //手机号
	private String coroName;
	
	// 数据查询权限
	private String queryRight;
	   //进件时间
	private Date customerIntoTime;

	// 是否冻结
	private String frozenCode;
	
	// 借款状态
	private String dictLoanStatus;
	
	// 电销组织机构ID
	private String consTelesalesOrgcode;
	
	//合同邮寄状态
	private String sendStatus;

	private Integer limit;
	private Integer offset;
	private Integer cnt;
	//拆单标记
    private String issplit;
    //大金融拒绝标记
    private String zcjRejectFlag;
    
    private String emailFlag;
    
    
		
	public String getZcjRejectFlag() {
		return zcjRejectFlag;
	}
	public void setZcjRejectFlag(String zcjRejectFlag) {
		this.zcjRejectFlag = zcjRejectFlag;
	}
	public String getIssplit() {
		return issplit;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}
	public String getCustomerPhoneFirstTransate() {
		return customerPhoneFirstTransate;
	}
	public void setCustomerPhoneFirstTransate(String customerPhoneFirstTransate) {
		this.customerPhoneFirstTransate = customerPhoneFirstTransate;
	}
	public String getCoroName() {
		return coroName;
	}
	public void setCoroName(String coroName) {
		this.coroName = coroName;
	}
	
	public String getConsTelesalesOrgcode() {
		return consTelesalesOrgcode;
	}
	public void setConsTelesalesOrgcode(String consTelesalesOrgcode) {
		this.consTelesalesOrgcode = consTelesalesOrgcode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getFrozenCode() {
		return frozenCode;
	}
	public void setFrozenCode(String frozenCode) {
		this.frozenCode = frozenCode;
	}
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
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
	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	public String getDictPayStatus() {
		return dictPayStatus;
	}
	public void setDictPayStatus(String dictPayStatus) {
		this.dictPayStatus = dictPayStatus;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTeamManageName() {
		return teamManageName;
	}
	public void setTeamManageName(String teamManageName) {
		this.teamManageName = teamManageName;
	}
	public String getLoanManageName() {
		return loanManageName;
	}
	public void setLoanManageName(String loanManageName) {
		this.loanManageName = loanManageName;
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
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getDictIsAdd() {
		return dictIsAdd;
	}
	public void setDictIsAdd(String dictIsAdd) {
		this.dictIsAdd = dictIsAdd;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
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
	public String getEmailFlag() {
		return emailFlag;
	}
	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}	
	
}
