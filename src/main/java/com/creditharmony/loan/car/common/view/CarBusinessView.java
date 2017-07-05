package com.creditharmony.loan.car.common.view;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
/**
 * 客户信息
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年2月1日
 */
public class CarBusinessView extends BaseBusinessView {
	private String dictLoanStatus;
	private String grantRecepicResult;
	private String grantFailResult;
	//排序字段
	private String orderField;
	//第一次退回的源节点名称--退回标红置顶业务所需
	private String firstBackSourceStep;
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getGrantRecepicResult() {
		return grantRecepicResult;
	}
	public void setGrantRecepicResult(String grantRecepicResult) {
		this.grantRecepicResult = grantRecepicResult;
	}
	public String getGrantFailResult() {
		return grantFailResult;
	}
	public void setGrantFailResult(String grantFailResult) {
		this.grantFailResult = grantFailResult;
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

