package com.creditharmony.loan.borrow.grant.entity.ex;

import java.io.Serializable;
import java.util.List;

public class ParamEx implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<DistachParamEx>  list;
	public List<DistachParamEx> getList() {
		return list;
	}
	public void setList(List<DistachParamEx> list) {
		this.list = list;
	}
}
