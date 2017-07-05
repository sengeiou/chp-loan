package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.OpenBank;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.common.constants.LoanStatus;

@SuppressWarnings("serial")
public class AppointmentTemplate extends DataEntity<AppointmentTemplate>{
	
	private String   id ;  //ｉｄ
	@NotBlank(message = "签约平台不能为空")
	private String   signPlat;  //签约平台
	private String   signPlatLabel;
	@NotBlank(message = "银行不能为空")
	private String   bank; // 银行
	private String   bankLabel;
	@NotNull(message = "预约时间不能为空")
	@DateTimeFormat(pattern="HH:mm")  
	private Date   appointmentDay;  // 预约时间
	private String appointmentDayLabel;
	@NotBlank(message = "划扣平台不能为空")
	private String   deductPlat; 
	private String   deductPlatLabel ; 
	private String   tlSign;   // 通联是否签约      
	private String   klSign;   // 卡联是否签约        
	private String   cjSign;   // 畅捷是否签约
	@NotBlank(message = "借款状态不能为空")
	private String   loanStatus; 
	private String   loanStatusLabel;
	private String   overCount;    // 累计逾期次数
	private Integer   overdueDays;  // 逾期天数    
	private String   lateMark;      // 标示  
	private String   deducttype;    // 接口 实时批量     
	private String   status;     // 生效 失效
	private String   signStatus; // 修改生效 失效比较字段
	
	private BigDecimal  applyReallyAmount; //实还金额
	
	private String amountMark; // 大于或者小于号
	
	private String ruleCode; //规则
	
	private String ids;
	public String getId() {
		return id;
	}
	public String getSignPlat() {
		return signPlat;
	}
	public String getBank() {
		return bank;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setSignPlat(String signPlat) {
		this.signPlat = signPlat;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSignPlatLabel() {
		if(signPlat != null && !"".equals(signPlat)){
			String[] deductPlatform = signPlat.split(",");
			StringBuffer str = new StringBuffer();
			for (String string : deductPlatform) {
				DeductPlat plant =  DeductPlat.parseByCode(string);
				if(plant != null){
					str.append(plant.getName()).append(",");
				}
			}
			signPlatLabel = str.substring(0, str.length()-1);
		}
		return signPlatLabel;
	}
	public void setSignPlatLabel(String signPlatLabel) {
		this.signPlatLabel = signPlatLabel;
	}
	public String getBankLabel() {
		if(bank != null && !"".equals(bank)){
			String[] bankform = bank.split(",");
			StringBuffer str = new StringBuffer();
			for (String string : bankform) {
				str.append(OpenBank.getOpenBank(string)).append(",");
			}
			bankLabel = str.substring(0, str.length()-1);
		}
		return bankLabel;
	}
	public void setBankLabel(String bankLabel) {
		this.bankLabel = bankLabel;
	}
	public String getLoanStatusLabel() {
		if(loanStatus != null && !"".equals(loanStatus)){
			String[] loanStatusform = loanStatus.split(",");
			StringBuffer str = new StringBuffer();
			for (String string : loanStatusform) {
				str.append(LoanStatus.getLoanStatus(string)).append(",");
			}
			loanStatusLabel = str.substring(0, str.length()-1);
		}
		return loanStatusLabel;
	}
	public void setLoanStatusLabel(String loanStatusLabel) {
		this.loanStatusLabel = loanStatusLabel;
	}
	public String getLateMark() {
		return lateMark;
	}
	public void setLateMark(String lateMark) {
		this.lateMark = lateMark;
	}
	public String getDeducttype() {
		return deducttype;
	}
	public void setDeducttype(String deducttype) {
		this.deducttype = deducttype;
	}
	
	public Date getAppointmentDay() {
		return appointmentDay;
	}
	public void setAppointmentDay(Date appointmentDay) {
		this.appointmentDay = appointmentDay;
	}
	public Integer getOverdueDays() {
		return overdueDays;
	}
	
	public String getOverCount() {
		return overCount;
	}
	public void setOverCount(String overCount) {
		this.overCount = overCount;
	}
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getDeductPlatLabel() {
		StringBuffer str = new StringBuffer();
		if(deductPlat != null){
				DeductPlat plant =  DeductPlat.parseByCode(deductPlat);
				if(plant != null){
					deductPlatLabel  =  str.append(plant.getName()).toString();
				}
			}
		return deductPlatLabel;
	}
	public void setDeductPlatLabel(String deductPlatLabel) {
		this.deductPlatLabel = deductPlatLabel;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getAppointmentDayLabel() {
		return appointmentDayLabel;
	}
	public void setAppointmentDayLabel(String appointmentDayLabel) {
		this.appointmentDayLabel = appointmentDayLabel;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public String getAmountMark() {
		return amountMark;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
	public void setAmountMark(String amountMark) {
		this.amountMark = amountMark;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	
}
