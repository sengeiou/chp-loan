package com.creditharmony.loan.channel.goldcredit.view;

import com.creditharmony.bpm.frame.view.BaseBusinessView;

/**
 * 用于存放金信网退回的applyId数据信息，来更新工作流
 * @Class Name JxSendView
 * @author 张建雄
 * @Create In 2016年3月8日
 */
public class JxSendView extends BaseBusinessView{
	//借款状态编码
	private String dictLoanStatusCode;
	//借款状态名字
	private String dictLoanStatus;
	// 排序字段
    private String orderField;
	public String getDictLoanStatusCode() {
		return dictLoanStatusCode;
	}
	public void setDictLoanStatusCode(String dictLoanStatusCode) {
		this.dictLoanStatusCode = dictLoanStatusCode;
	}
	public String getDictLoanStatus() {
		return dictLoanStatus;
	}
	public void setDictLoanStatus(String dictLoanStatus) {
		this.dictLoanStatus = dictLoanStatus;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	
	
}
