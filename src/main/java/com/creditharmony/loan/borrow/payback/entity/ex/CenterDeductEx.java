package com.creditharmony.loan.borrow.payback.entity.ex;
/**
 * 集中划扣申请导出列表字段
 * @Class Name CenterDeductEx
 * @author wengsi
 * @Create In 2015年12月26日
 */
import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantEx;
@SuppressWarnings("serial")
public class CenterDeductEx extends DataEntity<GrantEx>{
	//id,
	private String id;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	private String num;
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 20)
	private String customerName;//客户姓名
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 30)
	private String contractCode;//合同编号
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 40)
	private String storesName;//门店名称
	@ExcelField(title = "手机号码", type = 0, align = 2, sort = 50)
    private String tel;//手机号
	@ExcelField(title = "划扣平台", type = 0, align = 2, sort = 60 , dictType="jk_deduct_plat")  
    private String dictDealType;
	@ExcelField(title = "合同到期日期", type = 0, align = 2, sort = 70)
    private Date contractEndDay;
	@ExcelField(title = "还款日", type = 0, align = 2, sort = 80)
	private Date monthPayDay;
	@ExcelField(title = "开户行名称", type = 0, align = 2, sort = 90,dictType="jk_open_bank")
	private String applyBankName;//开户行名称
	@ExcelField(title = "划扣账号", type = 0, align = 2, sort = 100)
	private String bankAccount;//划扣账号
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 110)
	private String contractMoney;//开户行名称
	@ExcelField(title = "月还期供金额", type = 0, align = 2, sort = 120)
    private BigDecimal repayAmount;
	@ExcelField(title = "已还金额", type = 0, align = 2, sort = 130) 
    private BigDecimal completeAmount;
	@ExcelField(title = "当期应还金额", type = 0, align = 2, sort = 140) 
	private BigDecimal currentCompleteAmount;
	@ExcelField(title = "客服人员", type = 0, align = 2, sort = 150)
    private String customerStaff;
	@ExcelField(title = "团队名称", type = 0, align = 2, sort = 160)  
    private String teamName;
	@ExcelField(title = "标识", type = 0, align = 2, sort = 170, dictType="jk_new_old_sys_flag")  
	private String mark;
	private String model;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
	public BigDecimal getCompleteAmount() {
		return completeAmount;
	}
	public void setCompleteAmount(BigDecimal completeAmount) {
		this.completeAmount = completeAmount;
	}
	
	public BigDecimal getCurrentCompleteAmount() {
		return currentCompleteAmount;
	}
	public void setCurrentCompleteAmount(BigDecimal currentCompleteAmount) {
		this.currentCompleteAmount = currentCompleteAmount;
	}
	public String getCustomerStaff() {
		return customerStaff;
	}
	public void setCustomerStaff(String customerStaff) {
		this.customerStaff = customerStaff;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
}
