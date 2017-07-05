package com.creditharmony.loan.borrow.statistics.view;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.alibaba.druid.util.StringUtils;
import com.creditharmony.loan.common.utils.DateUtil;

/**
 * 退回率统计请求参数
 *
 * @author 任志远
 * @date 2016年11月16日
 */
public class ReturnRateStatsParams {

	/**
	 * 员工姓名
	 */
	private String userName;
	
	/**
	 * 岗位(角色)ID
	 */
	private String roleId;
	
	/**
	 * 查询月份
	 */
	private Date month;
	
	/**
	 * 当前时间
	 */
	private Date now = new Date();
	
	/**
	 * 查询开始时间
	 */
	private Date startDate;
	
	/**
	 * 查询截止时间
	 */
	private Date endDate;
	
	/**
	 * 当前时间前一天
	 */
	private Date previousDay;
	
	/**
	 * 月的结束时间
	 */
	private Date getMonthEnd;
	
	/**
	 * 业务部
	 */
	private String businessDepartmentId;
	
	/**
	 * 省分公司
	 */
	private String provinceBranchId;
	
	/**
	 * 城市支公司
	 */
	private String cityBranchId;
	
	/**
	 * 门店
	 */
	private String storeId;
	
	/**
	 * 导出记录id
	 */
	private String exportId;
	
	private String[] exportIdArray;

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		
		if(month != null){
			int m = DateUtil.getMonth(month);
			int n = DateUtil.getMonth(now);
			
			if(m <= n){
				this.month = month;
			}
		}else{
			this.month = month;
		}
	}

	public String getBusinessDepartmentId() {
		return businessDepartmentId;
	}

	public void setBusinessDepartmentId(String businessDepartmentId) {
		this.businessDepartmentId = businessDepartmentId;
	}

	public String getProvinceBranchId() {
		return provinceBranchId;
	}

	public void setProvinceBranchId(String provinceBranchId) {
		this.provinceBranchId = provinceBranchId;
	}

	public String getCityBranchId() {
		return cityBranchId;
	}

	public void setCityBranchId(String cityBranchId) {
		this.cityBranchId = cityBranchId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getExportId() {
		return exportId;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	public String[] getExportIdArray() {
		
		if(exportId == null){
			return null;
		}else{
			return exportId.split(",");
		}
	}

	public void setExportIdArray(String[] exportIdArray) {
		this.exportIdArray = exportIdArray;
	}

	public Date getStartDate() {
		
		//查询时间不为空，起始时间取查询月份的起始时间
		if(month != null){
			return DateUtil.getMonthBegin(month);
		//查询时间为空，并且其他查询条件都为空，起始时间取当前时间月份的起始时间	
		}else if(  StringUtils.isEmpty(userName) 
				&& StringUtils.isEmpty(roleId) 
				&& StringUtils.isEmpty(businessDepartmentId)
				&& StringUtils.isEmpty(provinceBranchId)
				&& StringUtils.isEmpty(cityBranchId)
				&& StringUtils.isEmpty(storeId)){
			
			return DateUtil.getMonthBegin(now);
		//查询时间为空，并且其他查询条件不为空, 则起始时间设为空，查以前所有数据
		}else{
			return null;
		}
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		
		//查询时间不为空，结束时间取下个月的起始时间
		if(month != null){
			
			int queryMonth = DateUtil.getMonth(month);
			int currentMonth = DateUtil.getMonth(now);
			//如果查询的是本月，取查询时间当天的起始时间作为查询条件的结束时间
			if(queryMonth == currentMonth){
				return DateUtil.getDayBegin(now);
			}else{//如果查询的不是本月，取下个月的起始时间作为查询的结束时间
				return DateUtil.getMonthBegin(DateUtils.addMonths(month, 1));
			}
		}else {//查询时间为空，取查询时间当天的起始时间作为查询条件的结束时间
			return DateUtil.getDayBegin(now);
		}
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getPreviousDay() {
		
		return DateUtil.getDayBegin(DateUtils.addDays(now, -1));
	}

	public void setPreviousDay(Date previousDay) {
		this.previousDay = previousDay;
	}

	public Date getGetMonthEnd() {
		
		if(month != null){
			return DateUtil.getMonthLastDay(month);
		}else{
			return null;
		}
	}

	public void setGetMonthEnd(Date getMonthEnd) {
		this.getMonthEnd = getMonthEnd;
	}
}
