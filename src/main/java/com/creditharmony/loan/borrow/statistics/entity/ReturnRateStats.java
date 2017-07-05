package com.creditharmony.loan.borrow.statistics.entity;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 退回率统计实体
 * 
 * @author 任志远
 * @date 2016年11月16日
 */
public class ReturnRateStats implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 业务部
	 */
	@ExcelField(title = "业务部", type = 1, align = 2, groups={1})
	private String businessDepartmentName;
	/**
	 * 省份
	 */
	private String provinceName;
	/**
	 * 省分公司
	 */
	@ExcelField(title = "省分", type = 1, align = 2, groups={1})
	private String provinceBranchName;
	/**
	 * 城市支公司
	 */
	@ExcelField(title = "城支", type = 1, align = 2, groups={1})
	private String cityBranchName;
	/**
	 * 门店
	 */
	@ExcelField(title = "门店", type = 1, align = 2, groups={1})
	private String storeName;
	/**
	 * 员工岗位(角色)
	 */
	@ExcelField(title = "员工岗位", type = 1, align = 2, groups={1})
	private String roleName;
	/**
	 * 员工姓名
	 */
	@ExcelField(title = "员工姓名", type = 1, align = 2, groups={1})
	private String userName;
	/**
	 * 统计的时间段
	 */
	private Date statsDate;
	@ExcelField(title = "时间", type = 1, align = 2, groups={1})
	private String statsDateExport;
	/**
	 * 录入次数
	 */
	private int enteringTimes;
	/**
	 * 退回次数
	 */
	@ExcelField(title = "退回次数", type = 1, align = 2, groups={1})
	private int returnTimes;
	/**
	 * 退回率
	 */
	private double returnRate;
	@ExcelField(title = "退回率", type = 1, align = 2, groups={1})
	private String returnRatePercent;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessDepartmentName() {
		return businessDepartmentName;
	}
	public void setBusinessDepartmentName(String businessDepartmentName) {
		this.businessDepartmentName = businessDepartmentName;
	}
	public void setReturnRate(double returnRate) {
		this.returnRate = returnRate;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceBranchName() {
		return provinceBranchName;
	}
	public void setProvinceBranchName(String provinceBranchName) {
		this.provinceBranchName = provinceBranchName;
	}
	public String getCityBranchName() {
		return cityBranchName;
	}
	public void setCityBranchName(String cityBranchName) {
		this.cityBranchName = cityBranchName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getEnteringTimes() {
		return enteringTimes;
	}
	public void setEnteringTimes(int enteringTimes) {
		this.enteringTimes = enteringTimes;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getReturnTimes() {
		return returnTimes;
	}
	public void setReturnTimes(int returnTimes) {
		this.returnTimes = returnTimes;
	}
	public double getReturnRate() {
		return returnRate;
	}
	public Date getStatsDate() {
		return statsDate;
	}
	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}
	public String getStatsDateExport() {
		return DateUtils.formatDate(statsDate, "yyyy-MM");
	}
	public void setStatsDateExport(String statsDateExport) {
		this.statsDateExport = statsDateExport;
	}
	public String getReturnRatePercent() {
		return Math.round(returnRate*100) + "%";
	}
	public void setReturnRatePercent(String returnRatePercent) {
		this.returnRatePercent = returnRatePercent;
	}

}
