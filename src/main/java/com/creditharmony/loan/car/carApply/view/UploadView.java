package com.creditharmony.loan.car.carApply.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;

/**
 * 客户信息
 * @Class Name uploadView
 * @author ganquan
 * @Create In 2016年2月15日
 */
public class UploadView extends BaseBusinessView {
	//流程id
	private String applyId;
	//借款编码
	private String loanCode;
	//客户编码
	private String customerCode;
	//客户姓名
	private String customerName;
	//证件类型
	private String dictCertType;
	//证件号码
	private String customerCertNum;
	//是否长期
	private String isLongTerm;
	//证件有效期
	private Date idEndDay;
	//证件开始时间
	private Date idStartDay;
	//性别
	private String customerSex;
	//婚姻状况
	private String dictMarryStatus;
	//学历
	private String dictEducation;
	//共借人姓名
	private List<String> coboNames = new ArrayList<String>();
    // 共借人基本信息  2016-05-21 WangJ 
	private List<CarLoanCoborrower> carLoanCoborrowers = new ArrayList<CarLoanCoborrower>();
	//借款状态
	private String dictLoanStatus;
	//咨询状态
	private String dictOperStatus;
	//退回原因
	private String remark;
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	
	private String imageUrl;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDictOperStatus() {
		return dictOperStatus;
	}
	public void setDictOperStatus(String dictOperStatus) {
		this.dictOperStatus = dictOperStatus;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public Date getIdStartDay() {
		return idStartDay;
	}
	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public Date getIdEndDay() {
		return idEndDay;
	}
	public void setIdEndDay(Date idEndDay) {
		this.idEndDay = idEndDay;
	}
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public String getDictMarryStatus() {
		return dictMarryStatus;
	}
	public void setDictMarryStatus(String dictMarryStatus) {
		this.dictMarryStatus = dictMarryStatus;
	}
	public String getDictEducation() {
		return dictEducation;
	}
	public void setDictEducation(String dictEducation) {
		this.dictEducation = dictEducation;
	}
	public String getIsLongTerm() {
		return isLongTerm;
	}
	public void setIsLongTerm(String isLongTerm) {
		this.isLongTerm = isLongTerm;
	}
	public List<String> getCoboNames() {
		return coboNames;
	}
	public void setCoboNames(List<String> coboNames) {
		this.coboNames = coboNames;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getFirstBackSourceStep() {
		return firstBackSourceStep;
	}
	public void setFirstBackSourceStep(String firstBackSourceStep) {
		this.firstBackSourceStep = firstBackSourceStep;
	}
	public List<CarLoanCoborrower> getCarLoanCoborrowers() {
		return carLoanCoborrowers;
	}
	public void setCarLoanCoborrowers(List<CarLoanCoborrower> carLoanCoborrowers) {
		this.carLoanCoborrowers = carLoanCoborrowers;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
