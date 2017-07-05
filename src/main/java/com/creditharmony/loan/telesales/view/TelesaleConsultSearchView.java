package com.creditharmony.loan.telesales.view;

import java.util.Date;
import java.util.Map;
import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 信借电销客户咨询列表查询
 * @Class Name TelesaleConsultSearchView
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@SuppressWarnings("serial")
public class TelesaleConsultSearchView extends
		DataEntity<TelesaleConsultSearchView> {

	private String id; // ID

	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 5)
	private String customerName; // 客户姓名

	private String customerCode; // 客户编号
	private String customerMobilePhone; // 客户的手机号
	private String mateCertNum; // 身份证号
	private String dictOperStatus; // 咨询状态
	private String dictOperStatusName; // 咨询状态名称
	private String storeCode; // 门店编码

	@ExcelField(title = "门店", type = 0, align = 2, sort = 8)
	private String storeName; // 门店名称

	@ExcelField(title = "电销专员", type = 0, align = 2, sort = 17)
	private String telesaleManName; // 电销专员

	@ExcelField(title = "电销专员编号", type = 0, align = 2, sort = 18)
	private String telesaleManCode; // 电销专员编号

	private String telesaleManRole; // 电销专员角色

	@ExcelField(title = "电销团队主管", type = 0, align = 2, sort = 19)
	private String telesaleTeamLeaderName; // 电销团队主管

	private String telesaleTeamLeaderRole; // 电销团队主管角色

	@ExcelField(title = "电销现场经理", type = 0, align = 2, sort = 20)
	private String telesaleSiteManagerName; // 电销现场经理

	private String telesaleSiteManagerRole; // 电销现场经理角色
	private Date firstConsultDateStart; // 首次咨询时间起始
	private Date firstConsultDateEnd; // 首次咨询时间截止
	private String consTelesalesSource; // 电销来源

	@ExcelField(title = "电销来源", type = 0, align = 2, sort = 16)
	private String consTelesalesSourceName; // 电销来源名称
	private Date consCommunicateDate; // 咨询时间
	private Date createTime; // 创建时间
	private String consLoanRecord; // 沟通记录
	private String consServiceUserCode; // 客服人员编号
	private String consServiceUserName; // 客服人员名称
	private String isFirstConsult; // 是否首次咨询

	@ExcelField(title = "借款编号", type = 0, align = 2, sort = 2)
	private String loanCode; // 借款编码

	@ExcelField(title = "状态", type = 0, align = 2, sort = 9)
	private String dictLoanStatusName; // 借款状态名称

	private String dictLoanStatusCode; // 借款状态编码

	@ExcelField(title = "共借人", type = 0, align = 2, sort = 4)
	private String coboName; // 共借人姓名

	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 3)
	private String contractCode; // 合同编号

	@ExcelField(title = "省份", type = 0, align = 2, sort = 6)
	private String storeProviceName; // 门店所在省名称

	@ExcelField(title = "城市", type = 0, align = 2, sort = 7)
	private String storeCityName; // 门店所在市名称

	@ExcelField(title = "产品", type = 0, align = 2, sort = 10)
	private String productName; // 产品名称

	private String productCode; // 产品编码

	@ExcelField(title = "产品期限", type = 0, align = 2, sort = 15)
	private String loanMoths; // 产品期限

	@ExcelField(title = "是否加急", type = 0, align = 2, sort = 11)
	private String loanUrgentFlag; // 是否加急

	@ExcelField(title = "申请金额", type = 0, align = 2, sort = 12)
	private String loanApplyAmount; // 申请金额

	@ExcelField(title = "批复金额", type = 0, align = 2, sort = 13)
	private String loanAuditAmount; // 批复金额

	@ExcelField(title = "发放金额", type = 0, align = 2, sort = 14)
	private String grantAmount; // 发放金额

	private String loanTeamManagerCode; // 团队经理编号
	private String loanManagerCode; // 客户经理编号

	@ExcelField(title = "录单人员", type = 0, align = 2, sort = 21)
	private String creater; // 录单人员

	@ExcelField(title = "外访人员", type = 0, align = 2, sort = 22)
	private String loanSurveyEmpId; // 外访人员

	private Date loanApplyTime; // 申请日期

	@ExcelField(title = "进件时间", type = 0, align = 2, sort = 23)
	private Date customerIntoTime; // 进件时间

	@ExcelField(title = "标示", type = 0, align = 2, sort = 24)
	private String loanFlagName; // 标示名称

	private String loanFlagCode; // 标示编码

	@ExcelField(title = "是否循环借", type = 0, align = 2, sort = 25)
	private String dictIsCycleName; // 循环借标识名称

	private String dictIsCycleCode; // 循环借标示编码
	private String consTelesalesFlagCode; // 是否电销编码(0:否；1:是)
	private String consTelesalesFlagName; // 是否电销名称(0:否；1:是)
	private String dictIsAdditional; // 是否追加借
	
    private String tableName; //biaotou 
    private String consTelesalesOrgcode;  //电销组织机构ID
    
    private Date lastTimeConsCommunicateDate;
    private String loanInfoOldOrNewFlag;
    //最优自然保证人
    private String bestCoborrower;

	public Date getLastTimeConsCommunicateDate() {
		return lastTimeConsCommunicateDate;
	}

	public void setLastTimeConsCommunicateDate(Date lastTimeConsCommunicateDate) {
		this.lastTimeConsCommunicateDate = lastTimeConsCommunicateDate;
	}

	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}

	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}

	public String getConsTelesalesOrgcode() {
		return consTelesalesOrgcode;
	}

	public void setConsTelesalesOrgcode(String consTelesalesOrgcode) {
		this.consTelesalesOrgcode = consTelesalesOrgcode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	private Map<String, Object> applyMap;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	public String getMateCertNum() {
		return mateCertNum;
	}

	public void setMateCertNum(String mateCertNum) {
		this.mateCertNum = mateCertNum;
	}

	public String getDictOperStatus() {
		return dictOperStatus;
	}

	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}

	public String getDictOperStatusName() {
		return dictOperStatusName;
	}

	public void setDictOperStatusName(String dictOperStatusName) {
		this.dictOperStatusName = dictOperStatusName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTelesaleManName() {
		return telesaleManName;
	}

	public void setTelesaleManName(String telesaleManName) {
		this.telesaleManName = telesaleManName;
	}

	public String getTelesaleManCode() {
		return telesaleManCode;
	}

	public void setTelesaleManCode(String telesaleManCode) {
		this.telesaleManCode = telesaleManCode;
	}

	public String getTelesaleManRole() {
		return telesaleManRole;
	}

	public void setTelesaleManRole(String telesaleManRole) {
		this.telesaleManRole = telesaleManRole;
	}

	public String getTelesaleTeamLeaderName() {
		return telesaleTeamLeaderName;
	}

	public void setTelesaleTeamLeaderName(String telesaleTeamLeaderName) {
		this.telesaleTeamLeaderName = telesaleTeamLeaderName;
	}

	public String getTelesaleTeamLeaderRole() {
		return telesaleTeamLeaderRole;
	}

	public void setTelesaleTeamLeaderRole(String telesaleTeamLeaderRole) {
		this.telesaleTeamLeaderRole = telesaleTeamLeaderRole;
	}

	public String getTelesaleSiteManagerName() {
		return telesaleSiteManagerName;
	}

	public void setTelesaleSiteManagerName(String telesaleSiteManagerName) {
		this.telesaleSiteManagerName = telesaleSiteManagerName;
	}

	public String getTelesaleSiteManagerRole() {
		return telesaleSiteManagerRole;
	}

	public void setTelesaleSiteManagerRole(String telesaleSiteManagerRole) {
		this.telesaleSiteManagerRole = telesaleSiteManagerRole;
	}

	public Date getFirstConsultDateStart() {
		return firstConsultDateStart;
	}

	public void setFirstConsultDateStart(Date firstConsultDateStart) {
		this.firstConsultDateStart = firstConsultDateStart;
	}

	public Date getFirstConsultDateEnd() {
		return firstConsultDateEnd;
	}

	public void setFirstConsultDateEnd(Date firstConsultDateEnd) {
		this.firstConsultDateEnd = firstConsultDateEnd;
	}

	public String getConsTelesalesSource() {
		return consTelesalesSource;
	}

	public void setConsTelesalesSource(String consTelesalesSource) {
		this.consTelesalesSource = consTelesalesSource;
	}

	public String getConsTelesalesSourceName() {
		return consTelesalesSourceName;
	}

	public void setConsTelesalesSourceName(String consTelesalesSourceName) {
		this.consTelesalesSourceName = consTelesalesSourceName;
	}

	public Date getConsCommunicateDate() {
		return consCommunicateDate;
	}

	public void setConsCommunicateDate(Date consCommunicateDate) {
		this.consCommunicateDate = consCommunicateDate;
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

	public String getConsServiceUserCode() {
		return consServiceUserCode;
	}

	public void setConsServiceUserCode(String consServiceUserCode) {
		this.consServiceUserCode = consServiceUserCode;
	}

	public String getConsServiceUserName() {
		return consServiceUserName;
	}

	public void setConsServiceUserName(String consServiceUserName) {
		this.consServiceUserName = consServiceUserName;
	}

	public String getIsFirstConsult() {
		return isFirstConsult;
	}

	public void setIsFirstConsult(String isFirstConsult) {
		this.isFirstConsult = isFirstConsult;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getCoboName() {
		return coboName;
	}

	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLoanUrgentFlag() {
		return loanUrgentFlag;
	}

	public void setLoanUrgentFlag(String loanUrgentFlag) {
		this.loanUrgentFlag = loanUrgentFlag;
	}

	public String getLoanApplyAmount() {
		return loanApplyAmount;
	}

	public void setLoanApplyAmount(String loanApplyAmount) {
		this.loanApplyAmount = loanApplyAmount;
	}

	public String getLoanAuditAmount() {
		return loanAuditAmount;
	}

	public void setLoanAuditAmount(String loanAuditAmount) {
		this.loanAuditAmount = loanAuditAmount;
	}

	public String getLoanTeamManagerCode() {
		return loanTeamManagerCode;
	}

	public void setLoanTeamManagerCode(String loanTeamManagerCode) {
		this.loanTeamManagerCode = loanTeamManagerCode;
	}

	public String getLoanManagerCode() {
		return loanManagerCode;
	}

	public void setLoanManagerCode(String loanManagerCode) {
		this.loanManagerCode = loanManagerCode;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getLoanSurveyEmpId() {
		return loanSurveyEmpId;
	}

	public void setLoanSurveyEmpId(String loanSurveyEmpId) {
		this.loanSurveyEmpId = loanSurveyEmpId;
	}

	public Date getLoanApplyTime() {
		return loanApplyTime;
	}

	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}

	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}

	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}

	public String getDictLoanStatusName() {
		return dictLoanStatusName;
	}

	public void setDictLoanStatusName(String dictLoanStatusName) {
		this.dictLoanStatusName = dictLoanStatusName;
	}

	public String getDictLoanStatusCode() {
		return dictLoanStatusCode;
	}

	public void setDictLoanStatusCode(String dictLoanStatusCode) {
		this.dictLoanStatusCode = dictLoanStatusCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getLoanFlagName() {
		return loanFlagName;
	}

	public void setLoanFlagName(String loanFlagName) {
		this.loanFlagName = loanFlagName;
	}

	public String getLoanFlagCode() {
		return loanFlagCode;
	}

	public void setLoanFlagCode(String loanFlagCode) {
		this.loanFlagCode = loanFlagCode;
	}

	public String getDictIsCycleName() {
		return dictIsCycleName;
	}

	public void setDictIsCycleName(String dictIsCycleName) {
		this.dictIsCycleName = dictIsCycleName;
	}

	public String getDictIsCycleCode() {
		return dictIsCycleCode;
	}

	public void setDictIsCycleCode(String dictIsCycleCode) {
		this.dictIsCycleCode = dictIsCycleCode;
	}

	public String getDictIsAdditional() {
		return dictIsAdditional;
	}

	public void setDictIsAdditional(String dictIsAdditional) {
		this.dictIsAdditional = dictIsAdditional;
	}

	public String getConsTelesalesFlagCode() {
		return consTelesalesFlagCode;
	}

	public void setConsTelesalesFlagCode(String consTelesalesFlagCode) {
		this.consTelesalesFlagCode = consTelesalesFlagCode;
	}

	public String getConsTelesalesFlagName() {
		return consTelesalesFlagName;
	}

	public void setConsTelesalesFlagName(String consTelesalesFlagName) {
		this.consTelesalesFlagName = consTelesalesFlagName;
	}

	public String getLoanMoths() {
		return loanMoths;
	}

	public void setLoanMoths(String loanMoths) {
		this.loanMoths = loanMoths;
	}

	public String getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}

	public Map<String, Object> getApplyMap() {
		return applyMap;
	}

	public void setApplyMap(Map<String, Object> applyMap) {
		this.applyMap = applyMap;
	}

	public String getBestCoborrower() {
		return bestCoborrower;
	}

	public void setBestCoborrower(String bestCoborrower) {
		this.bestCoborrower = bestCoborrower;
	}

}