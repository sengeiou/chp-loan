package com.creditharmony.loan.car.common.entity.ex;

import java.util.List;

import com.creditharmony.loan.car.common.entity.CarContract;

/**
 * 
 * @Class Name CarLoanContractEx
 * @author 安子帅
 * @Create In 2016年3月11日
 */
public class CarLoanContractEx {
	
	private List<CarContract> carContract;
	private List<String> delArray;

	public List<CarContract> getCarContract() {
		return carContract;
	}

	public void setCarContract(List<CarContract> carContract) {
		this.carContract = carContract;
	}

	public List<String> getDelArray() {
		return delArray;
	}

	public void setDelArray(List<String> delArray) {
		this.delArray = delArray;
	}

	
}
