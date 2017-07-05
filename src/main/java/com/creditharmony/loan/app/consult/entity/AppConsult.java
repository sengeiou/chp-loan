package com.creditharmony.loan.app.consult.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * APP客户咨询实体
 * @Class Name Customer
 * @author fan
 * @Create In 2016年6月6日
 */
public class AppConsult extends DataEntity<AppConsult> implements Serializable {
	
	private static final long serialVersionUID = 2817240905680537811L;
	
	private String  id; // 主键
	private String  customerId;  // 客户id(关联客户信息基础表)
	private String  financingId;  // 客户经理id(关联组织机构表员工Id)
	private BigDecimal  loanPosition;  // 借款金额
	private String  loanUse;  // 借款用途
	private String  loanType;  // 借款类型
	private String  loanRecord;  // 沟通记录
	private String  loanStatus;  // 下一步操作状态
	private String  createBy;  // 创建人
	private Date  createTime;  // 创建时间
	private String  lastModifyBy;  // 最后修改人
	private Date  lastModifyTime;  // 最后修改时间
	private Date  communicateDate;  // 沟通时间
	private String  teamEmpcode;  // 团队经理编号
	private String  teamOrgId;  // 团队组织机构ID
	private String  phoneOrgId;  // 电销组织机构ID(电销用)
	private String  phoneSource;  // 电销来源（电销用）
	private String  isPhone;  // 是否电销（0：否1：是）
	private String  customerService;  // 客服人员（电销用）
	private String  consultationSource;  // ocr客户银行卡图片目录
	private String  isDxQd;  // 是否是电销客服取单【0:是 1:否】
	private String  applyType;  // 申请类型【0：来源于chp1：来源于ocr2：来源于app】
	private Integer  loanMonth;  // 借款期限
	private String  productCode;  // 产品编码
	private String  isNew;  // 0-代表最新数据1-代表历史数据
	private Date  firstCreateTime;  // 客户首次咨询时间

	private String  certNum;  // 身份证号 
	private String  customerName;  // 姓名
	private String  sex;  // 性别
	private String  hukouAddress;  // 地址
	private String  certOrg;  // 签发机关
	private String  idStartDate;  // 身份证有效期开始时间
	private String  idEndDate;  // 身份证有效期结束时间
	private String  mobilephone;  // 手机号码
	private String  source;  // 客户来源
	private String  industry;  // 行业类型
	private String  accountBank;  // 开户行名称
	private String  branch;  // 支行
	private String  accountId;  // 银行卡号
	private String  namepic;  // 姓名图片
	private String  certNumPic;  // 身份证号图片
	private String  accountIdPic;  // 银行卡号图片
	private String  namePicName;  // 姓名图片名称
	private String  certNumPicName;  // 身份证号图片名称
	private String  accountidPicName;  // 银行卡号图片名称

	private String  productName;  // 产品名称	
	private String  certType;  // 证件类型	
	private String  industryName;  // 行业名称	
	private String  loanUseName;  // 借款用途名称
	private String  storeId;  // 门店id
	private String  storeName; // 门店名称

	private Date   birthday;  // 出生日期
	private String   bankId;  // 银行卡主键id
	private String   bankProvince;  // 银行卡所在城市省
	private String   bankCity;  // 银行卡所在城市市 
	private String   sourceType;  // 来源类型:数据取值为:同申请类型【1：来源于ocr2：来源于app】
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFinancingId() {
		return financingId;
	}
	public void setFinancingId(String financingId) {
		this.financingId = financingId;
	}
	public BigDecimal getLoanPosition() {
		return loanPosition;
	}
	public void setLoanPosition(BigDecimal loanPosition) {
		this.loanPosition = loanPosition;
	}
	public String getLoanUse() {
		return loanUse;
	}
	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanRecord() {
		return loanRecord;
	}
	public void setLoanRecord(String loanRecord) {
		this.loanRecord = loanRecord;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public Date getCommunicateDate() {
		return communicateDate;
	}
	public void setCommunicateDate(Date communicateDate) {
		this.communicateDate = communicateDate;
	}
	public String getTeamEmpcode() {
		return teamEmpcode;
	}
	public void setTeamEmpcode(String teamEmpcode) {
		this.teamEmpcode = teamEmpcode;
	}
	public String getTeamOrgId() {
		return teamOrgId;
	}
	public void setTeamOrgId(String teamOrgId) {
		this.teamOrgId = teamOrgId;
	}
	public String getPhoneOrgId() {
		return phoneOrgId;
	}
	public void setPhoneOrgId(String phoneOrgId) {
		this.phoneOrgId = phoneOrgId;
	}
	public String getPhoneSource() {
		return phoneSource;
	}
	public void setPhoneSource(String phoneSource) {
		this.phoneSource = phoneSource;
	}
	public String getIsPhone() {
		return isPhone;
	}
	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
	}
	public String getCustomerService() {
		return customerService;
	}
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	public String getConsultationSource() {
		return consultationSource;
	}
	public void setConsultationSource(String consultationSource) {
		this.consultationSource = consultationSource;
	}
	public String getIsDxQd() {
		return isDxQd;
	}
	public void setIsDxQd(String isDxQd) {
		this.isDxQd = isDxQd;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public Date getFirstCreateTime() {
		return firstCreateTime;
	}
	public void setFirstCreateTime(Date firstCreateTime) {
		this.firstCreateTime = firstCreateTime;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHukouAddress() {
		return hukouAddress;
	}
	public void setHukouAddress(String hukouAddress) {
		this.hukouAddress = hukouAddress;
	}
	public String getCertOrg() {
		return certOrg;
	}
	public void setCertOrg(String certOrg) {
		this.certOrg = certOrg;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAccountBank() {
		return accountBank;
	}
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getNamepic() {
		return namepic;
	}
	public void setNamepic(String namepic) {
		this.namepic = namepic;
	}
	public String getCertNumPic() {
		return certNumPic;
	}
	public void setCertNumPic(String certNumPic) {
		this.certNumPic = certNumPic;
	}
	public String getAccountIdPic() {
		return accountIdPic;
	}
	public void setAccountIdPic(String accountIdPic) {
		this.accountIdPic = accountIdPic;
	}
	public String getNamePicName() {
		return namePicName;
	}
	public void setNamePicName(String namePicName) {
		this.namePicName = namePicName;
	}
	public String getCertNumPicName() {
		return certNumPicName;
	}
	public void setCertNumPicName(String certNumPicName) {
		this.certNumPicName = certNumPicName;
	}
	public String getAccountidPicName() {
		return accountidPicName;
	}
	public void setAccountidPicName(String accountidPicName) {
		this.accountidPicName = accountidPicName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getLoanUseName() {
		return loanUseName;
	}
	public void setLoanUseName(String loanUseName) {
		this.loanUseName = loanUseName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getLoanMonth() {
		return loanMonth;
	}
	public void setLoanMonth(Integer loanMonth) {
		this.loanMonth = loanMonth;
	}
	public String getIdStartDate() {
		return idStartDate;
	}
	public void setIdStartDate(String idStartDate) {
		this.idStartDate = idStartDate;
	}
	public String getIdEndDate() {
		return idEndDate;
	}
	public void setIdEndDate(String idEndDate) {
		this.idEndDate = idEndDate;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
}
