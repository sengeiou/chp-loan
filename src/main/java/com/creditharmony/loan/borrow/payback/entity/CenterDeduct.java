package com.creditharmony.loan.borrow.payback.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
public class CenterDeduct extends DataEntity<CenterDeduct>{

	/**
	 * 集中划扣申请列表数据显示(DB待还款列表)
	 */
	private static final long serialVersionUID = 1L;
	private String ID;  //主键ID
	
	private String customerName;//客户姓名
	
	private String contractCode;//合同编号
	
	private Date monthPayDay;//还款日
	
	private String borrowState;//借款状态
	
    private String dictMonthStatus;//期供状态
    
    private String status;//借款状态
    
    private String applyBankName;//开户行名称
    
    private String tel;//手机号
    
    private String storesName;//门店名称
    
    private int months;//期数
    
    private float payMoney;//应还金额(月还期供金额)
    
    private float completeMoney;//已还金额
    
    private float buleAmont;//蓝补金额
    
    private String dictDealType;//划扣平台
    
    private String logo;//标识
    
    private String remindLogo;//提醒标识
    
    private String storeRemark;//门店备注
    
    private String storeRemarkUserid;//门店备注人
    
    private String orgId;//风控备注
    
    private String riskcontrolRemarkUserid;//风控备注人
    
    private String createBy;//创建人
    
    private Date createTime;//创建时间
    
    private String modifyBy;//最后修改人
    
    private Date modifyTime;//最后修改时间
    
    private Payback payback;//关联的还款主表的对象
    
    private Contract contract;//关联的合同表的对象
    
    private LoanInfo loanInfo;//关联的借款信息表对象
    
    
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public Payback getPayback() {
		return payback;
	}
	public void setPayback(Payback payback) {
		this.payback = payback;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
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
	public Date getMonthPayDay() {
		return monthPayDay;
	}
	public void setMonthPayDay(Date monthPayDay) {
		this.monthPayDay = monthPayDay;
	}
	public String getBorrowState() {
		return borrowState;
	}
	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}
	public String getDictMonthStatus() {
		return dictMonthStatus;
	}
	public void setDictMonthStatus(String dictMonthStatus) {
		this.dictMonthStatus = dictMonthStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApplyBankName() {
		return applyBankName;
	}
	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}
	public float getCompleteMoney() {
		return completeMoney;
	}
	public void setCompleteMoney(float completeMoney) {
		this.completeMoney = completeMoney;
	}
	public float getBuleAmont() {
		return buleAmont;
	}
	public void setBuleAmont(float buleAmont) {
		this.buleAmont = buleAmont;
	}
	public String getDictDealType() {
		return dictDealType;
	}
	public void setDictDealType(String dictDealType) {
		this.dictDealType = dictDealType;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getRemindLogo() {
		return remindLogo;
	}
	public void setRemindLogo(String remindLogo) {
		this.remindLogo = remindLogo;
	}
	public String getStoreRemark() {
		return storeRemark;
	}
	public void setStoreRemark(String storeRemark) {
		this.storeRemark = storeRemark;
	}
	public String getStoreRemarkUserid() {
		return storeRemarkUserid;
	}
	public void setStoreRemarkUserid(String storeRemarkUserid) {
		this.storeRemarkUserid = storeRemarkUserid;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRiskcontrolRemarkUserid() {
		return riskcontrolRemarkUserid;
	}
	public void setRiskcontrolRemarkUserid(String riskcontrolRemarkUserid) {
		this.riskcontrolRemarkUserid = riskcontrolRemarkUserid;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public LoanInfo getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	
	
	
    
}
