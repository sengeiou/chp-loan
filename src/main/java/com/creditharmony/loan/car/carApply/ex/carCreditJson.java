package com.creditharmony.loan.car.carApply.ex;

import java.util.List;

import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;

/**
 * 
 * @Class Name ReportJason
 * @author 安子帅
 * @Create In 2015年12月12日
 */
public class carCreditJson {
	
	private List<CarCustomerContactPerson> carCustomerContactPerson;
	
	private List<String> delArray;

	public List<CarCustomerContactPerson> getCarCustomerContactPerson() {
		return carCustomerContactPerson;
	}

	public void setCarCustomerContactPerson(
			List<CarCustomerContactPerson> carCustomerContactPerson) {
		this.carCustomerContactPerson = carCustomerContactPerson;
	}

	public List<String> getDelArray() {
		return delArray;
	}

	public void setDelArray(List<String> delArray) {
		this.delArray = delArray;
	}  

}
