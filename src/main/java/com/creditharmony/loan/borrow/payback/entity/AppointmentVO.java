package com.creditharmony.loan.borrow.payback.entity;

import java.util.Date;

import com.creditharmony.core.fortune.type.DeductPlat;
import com.creditharmony.core.fortune.type.OpenBank;
import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class AppointmentVO extends DataEntity<AppointmentVO>{
	
	private String   id ;               
	private String   signPlat;  
	private String   signPlatLabel;
	private String   bank; 
	private String   bankLabel;
	private Date   appointmentDay  ; 
	private String   deductPlat ;      
	private String   tlSign;        
	private String   klSign ;          
	private String   cjSign ;          
	private String   loanStatus ; 
	private String   loanStatusLabel;
	private String   overCount ;       
	private String   overdueDays;      
	private String   lateMark  ;       
	private String   deductType;        
	private String   status    ;
	public String getId() {
		return id;
	}
	public String getSignPlat() {
		return signPlat;
	}
	public String getSignPlatLabel() {
		return signPlatLabel;
	}
	public String getBank() {
		return bank;
	}
	public String getBankLabel() {
		return bankLabel;
	}
	public Date getAppointmentDay() {
		return appointmentDay;
	}
	public String getDeductPlat() {
		return deductPlat;
	}
	public String getTlSign() {
		return tlSign;
	}
	public String getKlSign() {
		return klSign;
	}
	public String getCjSign() {
		return cjSign;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public String getLoanStatusLabel() {
		return loanStatusLabel;
	}
	public String getOverCount() {
		return overCount;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public String getLateMark() {
		return lateMark;
	}
	public String getDeductType() {
		return deductType;
	}
	public String getStatus() {
		return status;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSignPlat(String signPlat) {
		this.signPlat = signPlat;
	}
	public void setSignPlatLabel(String signPlatLabel) {
		this.signPlatLabel = signPlatLabel;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public void setBankLabel(String bankLabel) {
		this.bankLabel = bankLabel;
	}
	public void setAppointmentDay(Date appointmentDay) {
		this.appointmentDay = appointmentDay;
	}
	public void setDeductPlat(String deductPlat) {
		this.deductPlat = deductPlat;
	}
	public void setTlSign(String tlSign) {
		this.tlSign = tlSign;
	}
	public void setKlSign(String klSign) {
		this.klSign = klSign;
	}
	public void setCjSign(String cjSign) {
		this.cjSign = cjSign;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public void setLoanStatusLabel(String loanStatusLabel) {
		this.loanStatusLabel = loanStatusLabel;
	}
	public void setOverCount(String overCount) {
		this.overCount = overCount;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	public void setLateMark(String lateMark) {
		this.lateMark = lateMark;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}
	public void setStatus(String status) {
		this.status = status;
	}        
	
	
}
