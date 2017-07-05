package com.creditharmony.loan.borrow.account.view;

import java.util.Date;
import java.util.List;

import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.loan.borrow.account.entity.RepayAccountApply;
import com.creditharmony.loan.common.entity.CityInfo;

public class RepayAccountApplyView extends RepayAccountApply {

	private static final long serialVersionUID = 1L;

	private String ids;
	private String bankIds;
	private String bankIdarray[];
	private String orgCode;
	private String newAccountFlag = "0";	//新增还款账号及操作的标识(0表示不允许，1表示允许，默认为0)
	private String operateStep;		//操作步骤
	private String oldData;			//原数据
	private String newData;			//新数据
	private String phone = "0";	//用来标记是否是修改手机号(0表示否，1表示是)
	private String operator;	//操作人
	private Date operateTime;	//操作时间
	private List<CityInfo> cityList;
	private String changeType;	//修改类型(新增：0，修改：1)
	private String refId;		//关联jk_loan_bank或jk_loan_customer的ID
	private String customerId;	//客户姓名
	private String uptedaType;	//变更类型(手机变更：0；账户修改：1)
	private String queryRight;  // 数据权限

	private String maintainStatusName;
	private String bankNames;
	private String flagName;
	private String loanStatusName;
	private String maintainTypeName;
	private String deductPlatName;
	private List<Dict> bankList;
	private String status;	//公用状态
	private String msg;		//失败/退回原因
	private String provinceName;
	private String cityName;
	private String repaymentFlag; // 是否还款账号
	// token
	private String repayAccountTokenId;
	private String repayAccountToken;
	private String customerEmail;
	private String updatecontent;
	
	private String bankNo;
	
	private String phoneSaleSign;//是否电销
	
	public String getPhoneSaleSign() {
		return phoneSaleSign;
	}
	public void setPhoneSaleSign(String phoneSaleSign) {
		this.phoneSaleSign = phoneSaleSign;
	}
	public String getRepayAccountTokenId() {
		return repayAccountTokenId;
	}
	public void setRepayAccountTokenId(String repayAccountTokenId) {
		this.repayAccountTokenId = repayAccountTokenId;
	}
	public String getRepayAccountToken() {
		return repayAccountToken;
	}
	public void setRepayAccountToken(String repayAccountToken) {
		this.repayAccountToken = repayAccountToken;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOrgCode() {
		if (ids != null){
			String[] strArray = ids.split(",");
			StringBuffer sb = new StringBuffer();
			for (String str : strArray){
				sb.append("'" + str + "',");
			}
			this.orgCode = sb.toString().substring(0, sb.toString().length() - 1);
		}
		return orgCode;
	}
	
	public String getRepaymentFlag() {
		return repaymentFlag;
	}
	public void setRepaymentFlag(String repaymentFlag) {
		this.repaymentFlag = repaymentFlag;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getNewAccountFlag() {
		return newAccountFlag;
	}
	public void setNewAccountFlag(String newAccountFlag) {
		this.newAccountFlag = newAccountFlag;
	}
	public String getOperateStep() {
		return operateStep;
	}
	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
	}
	public String getOldData() {
		return oldData;
	}
	public void setOldData(String oldData) {
		this.oldData = oldData;
	}
	public String getNewData() {
		return newData;
	}
	public void setNewData(String newData) {
		this.newData = newData;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public List<CityInfo> getCityList() {
		return cityList;
	}
	public void setCityList(List<CityInfo> cityList) {
		this.cityList = cityList;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getUptedaType() {
		return uptedaType;
	}
	public void setUptedaType(String uptedaType) {
		this.uptedaType = uptedaType;
	}
    /**
     * @return the queryRight
     */
    public String getQueryRight() {
        return queryRight;
    }
    /**
     * @param queryRight the String queryRight to set
     */
    public void setQueryRight(String queryRight) {
        this.queryRight = queryRight;
    }
	public String getMaintainStatusName() {
		return maintainStatusName;
	}
	public void setMaintainStatusName(String maintainStatusName) {
		this.maintainStatusName = maintainStatusName;
	}
	public String getBankNames() {
		return bankNames;
	}
	public void setBankNames(String bankNames) {
		this.bankNames = bankNames;
	}
	public String getFlagName() {
		return flagName;
	}
	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}
	public String getLoanStatusName() {
		return loanStatusName;
	}
	public void setLoanStatusName(String loanStatusName) {
		this.loanStatusName = loanStatusName;
	}
	public String getMaintainTypeName() {
		return maintainTypeName;
	}
	public void setMaintainTypeName(String maintainTypeName) {
		this.maintainTypeName = maintainTypeName;
	}
	public String getDeductPlatName() {
		return deductPlatName;
	}
	public void setDeductPlatName(String deductPlatName) {
		this.deductPlatName = deductPlatName;
	}
	public List<Dict> getBankList() {
		return bankList;
	}
	public void setBankList(List<Dict> bankList) {
		this.bankList = bankList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankIds() {
		return bankIds;
	}
	public void setBankIds(String bankIds) {
		this.bankIds = bankIds;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getUpdatecontent() {
		return updatecontent;
	}
	public void setUpdatecontent(String updatecontent) {
		this.updatecontent = updatecontent;
	}
	public String[] getBankIdarray() {
		return bankIdarray;
	}
	public void setBankIdarray(String[] bankIdarray) {
		this.bankIdarray = bankIdarray;
	}
	
	
}
