package com.creditharmony.loan.car.carConsultation.ex;

import java.util.Date;

/**
 * 车借咨询待办
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年1月22日
 */
public class CarLoanAdvisoryBacklogEx {
	private String id;
	private String customerCode;       //客户编码
	private String loanCode;           //借款编码
	private String customerName;       //客户姓名
	private String customerManager;    //客户经理
	private String teamManager;        //团队经理
	private String mingDiang;          //名店名称
	private Date planArrivalTime;      //预计到店时间
	private Date planArrivalTimeend;   //预计到店时间结束
	private String vehicleBrandModel;  //车牌型号
	private String dictOperStatus;     //咨询状态
	private String consTelesalesFlag;  //是否电销
	private String applyId;            //流程id
	private String dictLoanStatus; 		//借款状态
	private String storeId;				// 门店id
	//排序字段
	private String orderField;
	private String wobNum; 		//评估师待办使用
	private String token; 		//评估师待办使用
	
	private String customerCertNum;
	private Date loanApplyTime;
	
	public String getWobNum() {
		return wobNum;
	}
	public void setWobNum(String wobNum) {
		this.wobNum = wobNum;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	public String getTeamManager() {
		return teamManager;
	}
	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}
	public String getMingDiang() {
		return mingDiang;
	}
	public void setMingDiang(String mingDiang) {
		this.mingDiang = mingDiang;
	}
	public Date getPlanArrivalTime() {
		return planArrivalTime;
	}
	public void setPlanArrivalTime(Date planArrivalTime) {
		this.planArrivalTime = planArrivalTime;
	}
	public String getVehicleBrandModel() {
		return vehicleBrandModel;
	}
	public void setVehicleBrandModel(String vehicleBrandModel) {
		this.vehicleBrandModel = vehicleBrandModel;
	}
	public String getDictOperStatus() {
		return dictOperStatus;
	}
	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}
	public String getConsTelesalesFlag() {
		return consTelesalesFlag;
	}
	public void setConsTelesalesFlag(String consTelesalesFlag) {
		this.consTelesalesFlag = consTelesalesFlag;
	}
	public Date getPlanArrivalTimeend() {
		return planArrivalTimeend;
	}
	public void setPlanArrivalTimeend(Date planArrivalTimeend) {
		this.planArrivalTimeend = planArrivalTimeend;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public Date getLoanApplyTime() {
		return loanApplyTime;
	}
	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
