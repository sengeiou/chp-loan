package com.creditharmony.loan.car.carApply.ex;

import java.util.List;

import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;

/**
 * 
 * @Class Name ReportJason
 * @author 安子帅
 * @Create In 2015年12月12日
 */
public class CreditJson {
	
	private List<CarLoanCoborrower> carLoanCoborrower;  
	
	private List<String> delArray;

	
	public List<CarLoanCoborrower> getCarLoanCoborrower() {
		return carLoanCoborrower;
	}
	public void setCarLoanCoborrower(List<CarLoanCoborrower> carLoanCoborrower) {
		this.carLoanCoborrower = carLoanCoborrower;
	}
	public List<String> getDelArray() {
		return delArray;
	}
	public void setDelArray(List<String> delArray) {
		this.delArray = delArray;
	}
	
}
