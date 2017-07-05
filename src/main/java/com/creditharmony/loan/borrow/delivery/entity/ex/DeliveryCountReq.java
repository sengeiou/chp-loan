package com.creditharmony.loan.borrow.delivery.entity.ex;

import java.util.Date;

/**
* @ClassName: DeliveryCountReq
* @Description: TODO(交割成功个数参数)
* @author meiqingzhang
* @date 2017年3月27日
 */
public class DeliveryCountReq{
	
	private String loanStoreOrgid; //门店机构id
	private String orgCode;      //业务编号
	private String orgName;     //业务名称
	private String startTime;		// 开始时间(搜索用)
	private Date startDate;			// 开始时间
	private String endTime;			// 结束时间(搜索用)
	private Date endDate;			// 结束时间
	public String getLoanStoreOrgid() {
		return loanStoreOrgid;
	}
	public void setLoanStoreOrgid(String loanStoreOrgid) {
		this.loanStoreOrgid = loanStoreOrgid;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
