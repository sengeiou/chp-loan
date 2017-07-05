package com.creditharmony.loan.utils;

import java.util.Date;

 


public class InnerBean {
	/**
	 *  @param loanCode loan_code
	 * @param customerCode t_jk_loan_customer(customer_code)
	 * @param createBy 创建人t_jk_loan_customer(create_By)
	 * @param createTime 创建时间t_jk_loan_customer(create_Time.getTime())
	 * @param createTime 创建时间t_jk_loan_customer(customer_name）
	 * @param mobileNums 手机号   格式"num,num,......"
	 * @param 身份证号
	 * @param tphoneNum 固定电话  t_jk_loan_customer(customer_tel)
	 * @param tableName 数据库表名 
	 * 
	 * @param col 字段名称  格式"col,col......."
	 */
	private String loanCode;
	private String customerCode;
	private String createBy;
	private String createTime;
	private String customerName;
	private String mobileNums;
	private String certNum;
	private String tphoneNum;
	private String tableName;
	private String col;
 
	
	private String objsig;
	
	/**
	 * 客户附加字段
	 * @return
	 */
	public String getObjsig() {
		return objsig;
	}
	public void setObjsig(String objsig) {
		this.objsig = objsig;
	}
 
	
	public InnerBean(){}
	
	public InnerBean(String mobileNums, String tableName, String col){
		this.mobileNums = mobileNums;
		this.tableName = tableName;
		this.col = col;
	}
 
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = String.valueOf(createTime!=null?createTime.getTime():"");
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobileNums() {
		return mobileNums;
	}
	public void setMobileNums(String mobileNums) {
		this.mobileNums = mobileNums;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getTphoneNum() {
		return tphoneNum;
	}
	public void setTphoneNum(String tphoneNum) {
		this.tphoneNum = tphoneNum;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	
	
}
