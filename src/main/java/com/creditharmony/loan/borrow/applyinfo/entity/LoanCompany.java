package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 职业信息
 * @Class Name LoanCompany
 * @author 张平
 * @Create In 2015年12月3日
 */
public class LoanCompany extends DataEntity<LoanCompany> {

	private static final long serialVersionUID = 2314970289900532466L;

	private String rid; // 关联ID(主借人，共借人，配偶表)

	private String loanCode; // 借款编码

	private String compName; // 单位名称

	private String compPostCode; // 邮编

	private String compProvince; // 单位省

	private String comProvinceName; // 省名
	
	private String compCity; // 单位市
	
	private String comCityName; // 市名

	private String compArer; // 单位区
	
	private String comArerName; // 区名

	private String compAddress; // 详细地址

	private String dictCompType; // 单位类型
	
	private String dictCompTypeLabel; // 单位类型名称

	private String compTel; // 单位电话
	
	private String compTelExtension; //单位电话分机

	private String compWebsite; // 单位网址

	private String compWorkExperience; // 工作年限

	private BigDecimal compUnitScale; // 单位规模

	private String dictCompIndustry; // 行业类别

	private String compPost; // 职务
	
	private String compPostLabel; // 职务名称
	
	private String compPostLevel; //职务级别

	private String compDepartment; // 部门

	private BigDecimal compSalary; // 每月收入

	private Integer compSalaryDay; // 支薪日

	private String dictSalaryPay; // 工资支付方式

	private Date compWorkyear;

	private String compOtherAmount; // 其他收入

	private String dictrCustomterType; // 关联类型(0 主借人，1共借人，2配偶)

	private Date compEntryDay; // 入职时间
	
	private String previousCompName; // 前单位名称 
	
	private String dictCompTypeRemarks;	//单位性质备注
	
	//法人姓名
	private String comLegalMan;
	// 法人身份证号
	private String comLegalManNum;
	// 法人手机号
	private String comLegalManMoblie;
	// 法人邮箱
	private String comEmail;

	public String getDictCompTypeRemarks() {
		return dictCompTypeRemarks;
	}

	public void setDictCompTypeRemarks(String dictCompTypeRemarks) {
		this.dictCompTypeRemarks = dictCompTypeRemarks;
	}

	public String getComLegalMan() {
		return comLegalMan;
	}

	public void setComLegalMan(String comLegalMan) {
		this.comLegalMan = comLegalMan;
	}

	public String getComLegalManNum() {
		return comLegalManNum;
	}

	public void setComLegalManNum(String comLegalManNum) {
		this.comLegalManNum = comLegalManNum;
	}

	public String getComLegalManMoblie() {
		return comLegalManMoblie;
	}

	public void setComLegalManMoblie(String comLegalManMoblie) {
		this.comLegalManMoblie = comLegalManMoblie;
	}

	public String getComEmail() {
		return comEmail;
	}

	public void setComEmail(String comEmail) {
		this.comEmail = comEmail;
	}
	
	public String getDictCompTypeLabel() {
		return dictCompTypeLabel;
	}

	public void setDictCompTypeLabel(String dictCompTypeLabel) {
		this.dictCompTypeLabel = dictCompTypeLabel;
	}

	public String getCompPostLabel() {
		return compPostLabel;
	}

	public void setCompPostLabel(String compPostLabel) {
		this.compPostLabel = compPostLabel;
	}

	public String getComProvinceName() {
		return comProvinceName;
	}

	public void setComProvinceName(String comProvinceName) {
		this.comProvinceName = comProvinceName;
	}

	public String getComCityName() {
		return comCityName;
	}

	public void setComCityName(String comCityName) {
		this.comCityName = comCityName;
	}

	

	public String getComArerName() {
		return comArerName;
	}

	public void setComArerName(String comArerName) {
		this.comArerName = comArerName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName == null ? null : compName.trim();
	}

	public String getCompPostCode() {
		return compPostCode;
	}

	public void setCompPostCode(String compPostCode) {
		this.compPostCode = compPostCode == null ? null : compPostCode.trim();
	}

	public String getCompProvince() {
		return compProvince;
	}

	public void setCompProvince(String compProvince) {
		this.compProvince = compProvince == null ? null : compProvince.trim();
	}

	public String getCompCity() {
		return compCity;
	}

	public void setCompCity(String compCity) {
		this.compCity = compCity == null ? null : compCity.trim();
	}

	public String getCompArer() {
		return compArer;
	}

	public void setCompArer(String compArer) {
		this.compArer = compArer == null ? null : compArer.trim();
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress == null ? null : compAddress.trim();
	}

	public String getDictCompType() {
		return dictCompType;
	}

	public void setDictCompType(String dictCompType) {
		this.dictCompType = dictCompType == null ? null : dictCompType.trim();
	}

	public String getCompTel() {
		return compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel == null ? null : compTel.trim();
	}

	public String getCompWebsite() {
		return compWebsite;
	}

	public void setCompWebsite(String compWebsite) {
		this.compWebsite = compWebsite == null ? null : compWebsite.trim();
	}

	public String getCompWorkExperience() {
		return compWorkExperience;
	}

	public void setCompWorkExperience(String compWorkExperience) {
		this.compWorkExperience = compWorkExperience == null ? null
				: compWorkExperience.trim();
	}

	public BigDecimal getCompUnitScale() {
		return compUnitScale;
	}

	public void setCompUnitScale(BigDecimal compUnitScale) {
		this.compUnitScale = compUnitScale;
	}

	public String getDictCompIndustry() {
		return dictCompIndustry;
	}

	public void setDictCompIndustry(String dictCompIndustry) {
		this.dictCompIndustry = dictCompIndustry == null ? null
				: dictCompIndustry.trim();
	}

	public String getCompPost() {
		return compPost;
	}

	public void setCompPost(String compPost) {
		this.compPost = compPost == null ? null : compPost.trim();
	}

	public String getCompDepartment() {
		return compDepartment;
	}

	public void setCompDepartment(String compDepartment) {
		this.compDepartment = compDepartment == null ? null : compDepartment
				.trim();
	}

	public BigDecimal getCompSalary() {
		return compSalary;
	}

	public void setCompSalary(BigDecimal compSalary) {
		this.compSalary = compSalary;
	}

	public Integer getCompSalaryDay() {
		return compSalaryDay;
	}

	public void setCompSalaryDay(Integer compSalaryDay) {
		this.compSalaryDay = compSalaryDay;
	}

	public String getDictSalaryPay() {
		return dictSalaryPay;
	}

	public void setDictSalaryPay(String dictSalaryPay) {
		this.dictSalaryPay = dictSalaryPay == null ? null : dictSalaryPay
				.trim();
	}

	public Date getCompWorkyear() {
		return compWorkyear;
	}

	public void setCompWorkyear(Date compWorkyear) {
		this.compWorkyear = compWorkyear;
	}

	public String getCompOtherAmount() {
        return compOtherAmount;
    }

    public void setCompOtherAmount(String compOtherAmount) {
        this.compOtherAmount = compOtherAmount;
    }

   public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getDictrCustomterType() {
		return dictrCustomterType;
	}

	public void setDictrCustomterType(String dictrCustomterType) {
		this.dictrCustomterType = dictrCustomterType;
	}

	public Date getCompEntryDay() {
		return compEntryDay;
	}

	public void setCompEntryDay(Date compEntryDay) {
		this.compEntryDay = compEntryDay;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public String getCompTelExtension() {
		return compTelExtension;
	}

	public void setCompTelExtension(String compTelExtension) {
		this.compTelExtension = compTelExtension;
	}

	public String getPreviousCompName() {
		return previousCompName;
	}

	public void setPreviousCompName(String previousCompName) {
		this.previousCompName = previousCompName;
	}

	public String getCompPostLevel() {
		return compPostLevel;
	}

	public void setCompPostLevel(String compPostLevel) {
		this.compPostLevel = compPostLevel;
	}

}