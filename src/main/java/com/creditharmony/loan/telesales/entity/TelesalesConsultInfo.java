package com.creditharmony.loan.telesales.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;

/**
 * 电销客户咨询信息
 * @Class Name TelesalesConsultInfo
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@SuppressWarnings("serial")
public class TelesalesConsultInfo extends DataEntity<TelesalesConsultInfo> {

	private String id;
	private String customerCode; // 客户编码
	private CustomerBaseInfo customerBaseInfo; // 客户基本信息
	private ConsultRecord consultRecord; // 客户沟通日志表
	private String managerCode; // 客户经理ID
	private String managerName; //客户经理名称
	private BigDecimal loanApplyMoney; // 借款金额
	private String dictLoanUse; // 借款用途
	private String dictLoanType; // 借款类型
	private String loanTeamEmpcode; // 团队经理编号
	private String consTeamEmpName; // 团队经理名字
	private String loanTeamOrgId; // 团队组织机构ID
	private String teleSalesOrgid; // 电销组织机构ID(电销用)
	private String consPhoneSource; // 上级员工编码
	private String consTelesalesFlag; // 是否电销(0:否；1:是)
	private String consCustomerService; // 客服人员(电销用)
	private boolean longTerm; // 长期(1:长期;0：非长期)
	private String consLoanRecord; // 沟通记录
	private String consOperStatus; // 下一步操作状态
	private Date consCommunicateDate; // 沟通时间
	private String storeCode; // 门店编码
	private String storeName; // 门店名称
	private String consTelesalesSource; // 电销来源
	private String sceneManagerCode;  //电销现场经理
	private String roleId; // 角色ID
	private String isBorrow; //是否借么APP
	
	private String dictLoanUseRemark;//借款类型备注
	
	public String getDictLoanUseRemark() {
		return dictLoanUseRemark;
	}

	public void setDictLoanUseRemark(String dictLoanUseRemark) {
		this.dictLoanUseRemark = dictLoanUseRemark;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}

	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}

	public String getConsCustomerService() {
		return consCustomerService;
	}

	public void setConsCustomerService(String consCustomerService) {
		this.consCustomerService = consCustomerService;
	}

	public boolean isLongTerm() {
		return longTerm;
	}

	public void setLongTerm(boolean longTerm) {
		this.longTerm = longTerm;
	}

	public String getConsLoanRecord() {
		return consLoanRecord;
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

	public String getConsTelesalesSource() {
		return consTelesalesSource;
	}

	public void setConsTelesalesSource(String consTelesalesSource) {
		this.consTelesalesSource = consTelesalesSource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getSceneManagerCode() {
		return sceneManagerCode;
	}

	public void setSceneManagerCode(String sceneManagerCode) {
		this.sceneManagerCode = sceneManagerCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getIsBorrow() {
		return isBorrow;
	}

	public void setIsBorrow(String isBorrow) {
		this.isBorrow = isBorrow;
	}

}