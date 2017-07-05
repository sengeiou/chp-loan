package com.creditharmony.loan.car.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 面审信息
 * @Class Name CarApplicationInterviewInfo
 * @author 安子帅
 * @Create In 2016年1月21日
 */
public class CarApplicationInterviewInfo extends DataEntity<CarApplicationInterviewInfo> {
	 
	 private static final long serialVersionUID = 2784930538657217257L;
	 private String loanCode;         //借款编码
	 private String dictIdIstrue;         // 身份证真伪
	 private String queryResult;         //客户人法查询结果
	 private String queryResultPhone;         //114电话查询情况
	 private String customerJobReview;         //- 客户工作审核情况
	 private String creditReport;         //征信报告显示情况
	 private String createBy;         // 创建人
	 private Date createTime;         //创建时间
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	
	public String getDictIdIstrue() {
		return dictIdIstrue;
	}
	public void setDictIdIstrue(String dictIdIstrue) {
		this.dictIdIstrue = dictIdIstrue;
	}
	
	public String getQueryResult() {
		return queryResult;
	}
	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	public String getQueryResultPhone() {
		return queryResultPhone;
	}
	public void setQueryResultPhone(String queryResultPhone) {
		this.queryResultPhone = queryResultPhone;
	}
	public String getCustomerJobReview() {
		return customerJobReview;
	}
	public void setCustomerJobReview(String customerJobReview) {
		this.customerJobReview = customerJobReview;
	}
	public String getCreditReport() {
		return creditReport;
	}
	public void setCreditReport(String creditReport) {
		this.creditReport = creditReport;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
