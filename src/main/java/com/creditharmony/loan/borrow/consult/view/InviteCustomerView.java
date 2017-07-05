package com.creditharmony.loan.borrow.consult.view;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.consult.groups.DoAllotGroup;
import com.creditharmony.loan.borrow.consult.groups.UpdateStatusGroup;

/**
 * 邀请客户列表
 * 
 * @author 任志远
 * @date 2017年5月5日
 */
public class InviteCustomerView extends DataEntity<InviteCustomerView> {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务部组织机构ID
	 */
	private String businessOrgId;
	/**
	 * 业务部组织机构名称
	 */
	@ExcelField(title = "业务部", type = 1, sort = 5, align = 2, groups = { 1 })
	private String businessOrgName;
	/**
	 * 客户姓名
	 */
	@ExcelField(title = "客户姓名", sort = 0, type = 1, align = 2, groups = { 1 })
	private String customerName;
	/**
	 * 手机号码
	 */
	private String phoneNum;
	/**
	 * 状态
	 */
	@NotNull(groups = UpdateStatusGroup.class, message = "请选择状态")
	private String status;
	/**
	 * 状态名称
	 */
	@ExcelField(title = "保存状态", sort = 4, type = 1, align = 2, groups = { 1 })
	private String statusName;
	/**
	 * 客户来源
	 */
	private String sourceType;
	/**
	 * 客户来源名称
	 */
	@ExcelField(title = "来源", type = 1, sort = 12, align = 2, groups = { 1 })
	private String sourceTypeName;
	/**
	 * 大金融标示
	 */
	private String systemFlag;
	/**
	 * 大金融标示名称
	 */
	@ExcelField(title = "标识", type = 1, sort = 13, align = 2, groups = { 1 })
	private String systemFlagName;
	/**
	 * 开始时间
	 */
	private Date beginDate;
	/**
	 * 结束时间
	 */
	private Date endDate;

	/**
	 * 数据权限控制
	 */
	private String queryRight;
	/**
	 * 省份Code
	 */
	private String provinceCode;
	/**
	 * 省份名称
	 */
	@ExcelField(title = "客户省市", sort = 2, type = 1, align = 2, groups = { 1 })
	private String provinceName;
	/**
	 * 城市Code
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	@ExcelField(title = "客户地区", sort = 3, type = 1, align = 2, groups = { 1 })
	private String cityName;
	/**
	 * 客户经理ID
	 */
	@ExcelField(title = "客户经理ID", type = 1, sort = 9, align = 2, groups = { 1 })
	@NotNull(groups = DoAllotGroup.class, message = "请选择客户经理")
	private String financingId;
	/**
	 * 客户经理名称
	 */
	@ExcelField(title = "客户经理", sort = 8, type = 1, align = 2, groups = { 1 })
	private String financingName;
	/**
	 * 区域ID
	 */
	@NotNull(groups = DoAllotGroup.class, message = "请选择区域")
	private String areaOrgId;
	/**
	 * 区域名称
	 */
	@ExcelField(title = "业务区域", sort = 6, type = 1, align = 2, groups = { 1 })
	private String areaOrgName;
	/**
	 * 门店ID
	 */
	@NotNull(groups = DoAllotGroup.class, message = "请选择门店")
	private String storeOrgId;
	/**
	 * 门店名称
	 */
	@ExcelField(title = "所在门店", sort = 7, type = 1, align = 2, groups = { 1 })
	private String storeOrgName;
	/**
	 * 团队ID
	 */
	private String teamOrgId;
	/**
	 * 团队名称
	 */
	@ExcelField(title = "业务团队", type = 1, sort = 10, align = 2, groups = { 1 })
	private String teamOrgName;

	@ExcelField(title = "提交时间", type = 1, sort = 11, align = 2, groups = { 1 })
	private Date createTime;

	public String getBusinessOrgId() {
		return businessOrgId;
	}

	public void setBusinessOrgId(String businessOrgId) {
		this.businessOrgId = businessOrgId;
	}

	public String getBusinessOrgName() {
		return businessOrgName;
	}

	public void setBusinessOrgName(String businessOrgName) {
		this.businessOrgName = businessOrgName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getQueryRight() {
		return queryRight;
	}

	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getFinancingId() {
		return financingId;
	}

	public void setFinancingId(String financingId) {
		this.financingId = financingId;
	}

	public String getFinancingName() {
		return financingName;
	}

	public void setFinancingName(String financingName) {
		this.financingName = financingName;
	}

	public String getAreaOrgId() {
		return areaOrgId;
	}

	public void setAreaOrgId(String areaOrgId) {
		this.areaOrgId = areaOrgId;
	}

	public String getAreaOrgName() {
		return areaOrgName;
	}

	public void setAreaOrgName(String areaOrgName) {
		this.areaOrgName = areaOrgName;
	}

	public String getStoreOrgId() {
		return storeOrgId;
	}

	public void setStoreOrgId(String storeOrgId) {
		this.storeOrgId = storeOrgId;
	}

	public String getStoreOrgName() {
		return storeOrgName;
	}

	public void setStoreOrgName(String storeOrgName) {
		this.storeOrgName = storeOrgName;
	}

	public String getTeamOrgId() {
		return teamOrgId;
	}

	public void setTeamOrgId(String teamOrgId) {
		this.teamOrgId = teamOrgId;
	}

	public String getTeamOrgName() {
		return teamOrgName;
	}

	public void setTeamOrgName(String teamOrgName) {
		this.teamOrgName = teamOrgName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}

	public String getSystemFlagName() {
		return systemFlagName;
	}

	public void setSystemFlagName(String systemFlagName) {
		this.systemFlagName = systemFlagName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
