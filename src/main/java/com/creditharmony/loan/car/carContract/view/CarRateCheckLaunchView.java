package com.creditharmony.loan.car.carContract.view;

import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 审核费率详情 加载数据
 * @Class Name CarRateCheckLaunchView
 * @author 李静辉
 * @Create In 2016年2月18日
 */
public class CarRateCheckLaunchView extends BaseBusinessView{

	//借款编码
	private String loanCode;
	//客户编码
	private String customerCode;
	//客户姓名
	private String customerName;
	//证件类型
	private String dictCertType;
	//身份证号码
	private String customerCertNum;
	//手机号码
	private String customerMobilePhone;
	// 借款信息
	private CarLoanInfo carLoanInfo;
	// 客户信息
	private CarCustomer carCustomer;
	// 车辆信息
	private CarVehicleInfo carVehicleInfo;
	// 审核记录信息
	private CarAuditResult carAuditResult;
	// 合同信息
	private CarContract carContract;
	// 共借人信息
	private List<CarLoanCoborrower> carLoanCoborrowers;
	// 中间人
	private List<MiddlePerson> middlePersons;
	// 历史展期合同信息
	private List<CarContract> carContracts;
	// 是否大额
	private String isLargeAmount;
	// 是否大额
	private int customerAge;
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	
	private CarCustomerBankInfo carCustomerBankInfo;
	
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
	public String getDictCertType() {
		return dictCertType;
	}
	public void setDictCertType(String dictCertType) {
		this.dictCertType = dictCertType;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}
	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}
	public CarLoanInfo getCarLoanInfo() {
		return carLoanInfo;
	}
	public void setCarLoanInfo(CarLoanInfo carLoanInfo) {
		this.carLoanInfo = carLoanInfo;
	}
	public CarCustomer getCarCustomer() {
		return carCustomer;
	}
	public void setCarCustomer(CarCustomer carCustomer) {
		this.carCustomer = carCustomer;
	}
	public CarVehicleInfo getCarVehicleInfo() {
		return carVehicleInfo;
	}
	public void setCarVehicleInfo(CarVehicleInfo carVehicleInfo) {
		this.carVehicleInfo = carVehicleInfo;
	}
	public CarAuditResult getCarAuditResult() {
		return carAuditResult;
	}
	public void setCarAuditResult(CarAuditResult carAuditResult) {
		this.carAuditResult = carAuditResult;
	}
	public CarContract getCarContract() {
		return carContract;
	}
	public void setCarContract(CarContract carContract) {
		this.carContract = carContract;
	}
	public List<CarLoanCoborrower> getCarLoanCoborrowers() {
		return carLoanCoborrowers;
	}
	public void setCarLoanCoborrowers(List<CarLoanCoborrower> carLoanCoborrowers) {
		this.carLoanCoborrowers = carLoanCoborrowers;
	}
	public List<MiddlePerson> getMiddlePersons() {
		return middlePersons;
	}
	public void setMiddlePersons(List<MiddlePerson> middlePersons) {
		this.middlePersons = middlePersons;
	}
	public List<CarContract> getCarContracts() {
		return carContracts;
	}
	public void setCarContracts(List<CarContract> carContracts) {
		this.carContracts = carContracts;
	}
	public String getIsLargeAmount() {
		return isLargeAmount;
	}
	public void setIsLargeAmount(String isLargeAmount) {
		this.isLargeAmount = isLargeAmount;
	}
	public int getCustomerAge() {
		return customerAge;
	}
	public void setCustomerAge(int customerAge) {
		this.customerAge = customerAge;
	}
	public CarCustomerBankInfo getCarCustomerBankInfo() {
		return carCustomerBankInfo;
	}
	public void setCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
		this.carCustomerBankInfo = carCustomerBankInfo;
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
	
}
