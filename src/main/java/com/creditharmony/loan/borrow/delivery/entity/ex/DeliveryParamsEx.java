package com.creditharmony.loan.borrow.delivery.entity.ex;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 交割数据实体类
 * @Class Name TaskDeliveryParams
 * @author lirui
 * @Create In 2015年12月7日
 */
@SuppressWarnings("serial")
public class DeliveryParamsEx extends DataEntity<DeliveryParamsEx> {
	private String custName;		// 客户姓名
	private String contractCode;	// 合同编号
	private String startTime;		// 开始时间(搜索用)
	private Date startDate;			// 开始时间
	private String endTime;			// 结束时间(搜索用)
	private Date endDate;			// 结束时间
	private String strote;			// 门店
	private String teamManager;		// 团队经理
	private String manager;			// 客户经理	
	private String newStrote;		// 新门店
	private String newTeamManager;	// 新团队经理
	private String newManager;		// 新客户经理
	private String loanStatus;		// 借款状态
	   // 数据查询权限
	private String queryRight;
		
	public String getQueryRight() {
		return queryRight;
	}
	public void setQueryRight(String queryRight) {
		this.queryRight = queryRight;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}		
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStrote() {
		return strote;
	}
	public void setStrote(String strote) {
		this.strote = strote;
	}
	public String getTeamManager() {
		return teamManager;
	}
	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;		
	}
	public String getNewStrote() {
		return newStrote;
	}
	public void setNewStrote(String newStrote) {
		this.newStrote = newStrote;
	}
	public String getNewTeamManager() {
		return newTeamManager;
	}
	public void setNewTeamManager(String newTeamManager) {
		this.newTeamManager = newTeamManager;
	}
	public String getNewManager() {
		return newManager;
	}
	public void setNewManager(String newManager) {
		this.newManager = newManager;
	}
	
}
