package com.creditharmony.loan.borrow.applyinfo.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 借款备注信息
 * @Class Name LoanRemark
 * @author 张平
 * @Create In 2015年12月3日
 */
public class LoanRemark extends DataEntity<LoanRemark> {

	private static final long serialVersionUID = -1477414092941893490L;
	// 借款编码
	private String loanCode;
	// 备注类型(码表)
	private String dictRemarkType;
	// 备注
	private String remark;
	// 备注时间
	private Date remarkTime;

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getDictRemarkType() {
		return dictRemarkType;
	}

	public void setDictRemarkType(String dictRemarkType) {
		this.dictRemarkType = dictRemarkType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getRemarkTime() {
		return remarkTime;
	}

	public void setRemarkTime(Date remarkTime) {
		this.remarkTime = remarkTime;
	}
}