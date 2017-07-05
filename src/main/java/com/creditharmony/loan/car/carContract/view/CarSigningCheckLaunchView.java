package com.creditharmony.loan.car.carContract.view;

import java.util.ArrayList;
import java.util.List;

import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.common.entity.CityInfo;


/**
 * 签署页面 加载数据
 * @Class Name CarSigningCheckLaunchView
 * @author 李静辉
 * @Create In 2016年2月18日
 */
public class CarSigningCheckLaunchView extends CarRateCheckLaunchView{

		
		//银行账户信息
		private CarCustomerBankInfo carCustomerBankInfo;
		
		private String isConCheckBack;
		// 初始化省份显示
	    private List<CityInfo> provinceList = new ArrayList<CityInfo>();
		//排序字段
		private String orderField;
		//第一次退回的源节点名称--退回标红置顶业务所需
		private String firstBackSourceStep;
		
		private CarCheckRate carCheckRate;
	    
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
		public String getIsConCheckBack() {
			return isConCheckBack;
		}
		public void setIsConCheckBack(String isConCheckBack) {
			this.isConCheckBack = isConCheckBack;
		}
		public CarCheckRate getCarCheckRate() {
			return carCheckRate;
		}
		public void setCarCheckRate(CarCheckRate carCheckRate) {
			this.carCheckRate = carCheckRate;
		}
		
}
