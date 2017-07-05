package com.creditharmony.loan.borrow.delivery.entity.ex;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
* @ClassName: DeliveryReq
* @Description: TODO(交割请求参数)
* @author meiqingzhang
* @date 2017年3月27日
 */
public class DeliveryReq extends DataEntity<DeliveryReq>{
	private static final long serialVersionUID = -8962303371912869134L;
	//待交割列表参数
	private String custName;	   // 员工姓名
	private String custCode;	   // 员工编号
	private String deliveryResult; //交割状态
	private String typeRole;       //角色类型
	//单条交割参数
	private String custNameOne;	   // 员工姓名
	private String custCodeOne;	   // 员工编号
	private String typeRoleOne;       //角色类型
	//隐藏参数重定向时传过去
	private String typeRoleP;//页面隐藏角色进行对比判断
	private String custNameP;
	private String custCodeP;
	private String deliveryResultP;
	
	private String loanCode;  //借款编号
	private String loanCodes;  //多个借款编号
	private String message;
	
	private String[] checkIds; //选择复选框

	public String[] getCheckIds() {
		return checkIds;
	}
	public void setCheckIds(String[] checkIds) {
		this.checkIds = checkIds;
	}
	public String getCustNameP() {
		return custNameP;
	}
	public void setCustNameP(String custNameP) {
		this.custNameP = custNameP;
	}
	public String getCustCodeP() {
		return custCodeP;
	}
	public void setCustCodeP(String custCodeP) {
		this.custCodeP = custCodeP;
	}
	public String getDeliveryResultP() {
		return deliveryResultP;
	}
	public void setDeliveryResultP(String deliveryResultP) {
		this.deliveryResultP = deliveryResultP;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getLoanCodes() {
		return loanCodes;
	}
	public void setLoanCodes(String loanCodes) {
		this.loanCodes = loanCodes;
	}
	public String getTypeRoleP() {
		return typeRoleP;
	}
	public void setTypeRoleP(String typeRoleP) {
		this.typeRoleP = typeRoleP;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCustNameOne() {
		return custNameOne;
	}
	public void setCustNameOne(String custNameOne) {
		this.custNameOne = custNameOne;
	}
	public String getCustCodeOne() {
		return custCodeOne;
	}
	public void setCustCodeOne(String custCodeOne) {
		this.custCodeOne = custCodeOne;
	}
	public String getTypeRoleOne() {
		return typeRoleOne;
	}
	public void setTypeRoleOne(String typeRoleOne) {
		this.typeRoleOne = typeRoleOne;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getDeliveryResult() {
		return deliveryResult;
	}
	public void setDeliveryResult(String deliveryResult) {
		this.deliveryResult = deliveryResult;
	}
	public String getTypeRole() {
		return typeRole;
	}
	public void setTypeRole(String typeRole) {
		this.typeRole = typeRole;
	}
	
}
