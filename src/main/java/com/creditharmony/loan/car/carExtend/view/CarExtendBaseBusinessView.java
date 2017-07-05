package com.creditharmony.loan.car.carExtend.view;

import com.creditharmony.bpm.frame.view.BaseBusinessView;

/**
 * 展期基础业务bean
 * 
 * @author chenwd
 *
 */
public class CarExtendBaseBusinessView extends BaseBusinessView {
	
	//排序字段
  	private String orderField;
  	//第一次退回的源节点名称--退回标红置顶业务所需
  	private String firstBackSourceStep;
  	
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
