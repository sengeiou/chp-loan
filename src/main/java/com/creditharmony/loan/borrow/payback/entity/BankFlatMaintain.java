package com.creditharmony.loan.borrow.payback.entity;

import com.creditharmony.core.persistence.DataEntity;
@SuppressWarnings("serial")
public class BankFlatMaintain extends DataEntity<BankFlatMaintain>{
	
   private String id; // id
   private String bankCode; //预约银行编码
   private String platCode; //划扣平台编码
   private String bankName; //银行名
   private String platName; //平台名
   private String deductTime; //批量实时标识
   private String deductTimeLabel; //批量实时标识(名称)
   private String bankId;
   
	public String getDeductTimeLabel() {
		return deductTimeLabel;
	}
	public void setDeductTimeLabel(String deductTimeLabel) {
		this.deductTimeLabel = deductTimeLabel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPlatName() {
		return platName;
	}
	public void setPlatName(String platName) {
		this.platName = platName;
	}
	public String getDeductTime() {
		return deductTime;
	}
	public void setDeductTime(String deductTime) {
		this.deductTime = deductTime;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	   

}
