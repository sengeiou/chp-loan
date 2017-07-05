package com.creditharmony.loan.credit.entity.ex;

import java.util.List;

import com.creditharmony.loan.credit.entity.CreditLoanDetailedOne;
import com.creditharmony.loan.credit.entity.CreditLoanDetailedTwo;

public class CreditLoanDetailedOneEx {
	private String loanCode; // 借款编号
	private String type ; //借款人类型 主借人/共借人
	private String checkType; // 审批步骤 初审 复审等
	private String relId; // 关联id（客户id）
	private List<CreditLoanDetailedOne> creditLoanDetailedOneList;
	private List<CreditLoanDetailedTwo> creditLoanDetailedTwoList;
	private List<CreditCycleRecordEx> creditCycleRecordExList;
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public List<CreditLoanDetailedOne> getCreditLoanDetailedOneList() {
		return creditLoanDetailedOneList;
	}
	public void setCreditLoanDetailedOneList(
			List<CreditLoanDetailedOne> creditLoanDetailedOneList) {
		this.creditLoanDetailedOneList = creditLoanDetailedOneList;
	}
	public List<CreditCycleRecordEx> getCreditCycleRecordExList() {
		return creditCycleRecordExList;
	}
	public void setCreditCycleRecordExList(
			List<CreditCycleRecordEx> creditCycleRecordExList) {
		this.creditCycleRecordExList = creditCycleRecordExList;
	}
	public List<CreditLoanDetailedTwo> getCreditLoanDetailedTwoList() {
		return creditLoanDetailedTwoList;
	}
	public void setCreditLoanDetailedTwoList(
			List<CreditLoanDetailedTwo> creditLoanDetailedTwoList) {
		this.creditLoanDetailedTwoList = creditLoanDetailedTwoList;
	}
}
