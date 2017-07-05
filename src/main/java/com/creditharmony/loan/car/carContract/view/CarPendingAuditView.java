package com.creditharmony.loan.car.carContract.view;

import com.creditharmony.bpm.frame.view.BaseBusinessView;

/**
 * 确认放款view
 * @Class Name carPendingAuditView
 * @author ganquan
 * @Create In 2016年2月22日
 */
public class CarPendingAuditView extends BaseBusinessView {
	//借款状态
	private String dictLoanStatus;
	//P2P标识
	private String loanFlag;
	//退回原因（数据库）
	private String dictBackMestype;
	//退回原因（流）
	private String contractBackResultCode;
	//其他退回原因
	private String remark;
	//流程id
	private String applyId;
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

	public String getDictBackMestype() {
		return dictBackMestype;
	}

	public void setDictBackMestype(String dictBackMestype) {
		this.dictBackMestype = dictBackMestype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getContractBackResultCode() {
		return contractBackResultCode;
	}

	public void setContractBackResultCode(String contractBackResultCode) {
		this.contractBackResultCode = contractBackResultCode;
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

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
}
