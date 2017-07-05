package com.creditharmony.loan.car.carExtend.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;


/**
 * 签署页面 加载数据
 * @Class Name CarSigningCheckLaunchView
 * @author 张振强
 * @Create In 2016年3月12日
 */
public class CarExtendSigningView extends CarExtendBaseBusinessView{

		// 借款信息
		private CarLoanInfo carLoanInfo;
		// 客户信息
		private CarCustomer carCustomer;
		// 客户基本信息
		private CarCustomerBase carCustomerBase;
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
		//银行账户信息
		private CarCustomerBankInfo carCustomerBankInfo;
		// 历史信息
		private CarLoanStatusHis carLoanStatusHis;
		// 初始化省份显示
	    private List<CityInfo> provinceList = new ArrayList<CityInfo>();
	    // 上次合同到期日的第二天
	    private Date lastContractEndDateSecond;
	    //费率信息
	    private CarCheckRate carCheckRate;
	    
	    
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
		public CarCustomerBase getCarCustomerBase() {
			return carCustomerBase;
		}
		public void setCarCustomerBase(CarCustomerBase carCustomerBase) {
			this.carCustomerBase = carCustomerBase;
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
		public CarCustomerBankInfo getCarCustomerBankInfo() {
			return carCustomerBankInfo;
		}
		public void setCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
			this.carCustomerBankInfo = carCustomerBankInfo;
		}
		public List<CityInfo> getProvinceList() {
			return provinceList;
		}
		public void setProvinceList(List<CityInfo> provinceList) {
			this.provinceList = provinceList;
		}
		public CarLoanStatusHis getCarLoanStatusHis() {
			return carLoanStatusHis;
		}
		public void setCarLoanStatusHis(CarLoanStatusHis carLoanStatusHis) {
			this.carLoanStatusHis = carLoanStatusHis;
		}
		public Date getLastContractEndDateSecond() {
			return lastContractEndDateSecond;
		}
		public void setLastContractEndDateSecond(Date lastContractEndDateSecond) {
			this.lastContractEndDateSecond = lastContractEndDateSecond;
		}
		public CarCheckRate getCarCheckRate() {
			return carCheckRate;
		}
		public void setCarCheckRate(CarCheckRate carCheckRate) {
			this.carCheckRate = carCheckRate;
		}
		
}
