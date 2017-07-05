package com.creditharmony.loan.car.carContract.view;

import java.io.Serializable;
import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;

/**
 * 确认签署保存属性对象
 * @Class Name CarSigningCheckBusinessView
 * @author 李静辉
 * @Create In 2016年2月19日
 */
public class CarSigningCheckBusinessView extends BaseBusinessView implements
		Serializable {
	/**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 8514662258754776365L;
	// 银行账户信息
	private CarCustomerBankInfo carCustomerBankInfo;
	// 借款编号
	private String loanCode;
	// 借款状态
	private String dictLoanStatus;
	// 合同信息
	private CarContract carContract;

	// 工作流更新属性
	private Double contractAmount; // 合同金额

	// 退回原因(存入借款信息主表中的备注)
	private String remark;

	private String auditBorrowProductCode; // 产品类型编码（批复）
	private String auditBorrowProductName; // 产品类型（批复）
	private Date auditTime; // 批借时间
	private Integer auditLoanMonths; // 借款期限（批复）
	private Double auditAmount; // 批借金额

	private Date contractFactDay; // 实际签约日期
	
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;

	public CarCustomerBankInfo getCarCustomerBankInfo() {
		return carCustomerBankInfo;
	}

	public void setCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
		this.carCustomerBankInfo = carCustomerBankInfo;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getDictLoanStatus() {
		return dictLoanStatus;
	}

	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}

	public CarContract getCarContract() {
		return carContract;
	}

	public void setCarContract(CarContract carContract) {
		this.carContract = carContract;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditBorrowProductCode() {
		return auditBorrowProductCode;
	}

	public void setAuditBorrowProductCode(String auditBorrowProductCode) {
		this.auditBorrowProductCode = auditBorrowProductCode;
	}

	public String getAuditBorrowProductName() {
		return auditBorrowProductName;
	}

	public void setAuditBorrowProductName(String auditBorrowProductName) {
		this.auditBorrowProductName = auditBorrowProductName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getAuditLoanMonths() {
		return auditLoanMonths;
	}

	public void setAuditLoanMonths(Integer auditLoanMonths) {
		this.auditLoanMonths = auditLoanMonths;
	}

	public Double getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
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

	public Date getContractFactDay() {
		return contractFactDay;
	}

	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	
}
