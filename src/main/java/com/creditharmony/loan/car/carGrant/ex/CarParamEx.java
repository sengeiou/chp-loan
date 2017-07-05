package com.creditharmony.loan.car.carGrant.ex;

import java.io.Serializable;
import java.util.List;

public class CarParamEx implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CarDistachParamEx>  list;
	public List<CarDistachParamEx> getList() {
		return list;
	}
	public void setList(List<CarDistachParamEx> list) {
		this.list = list;
	}
}
