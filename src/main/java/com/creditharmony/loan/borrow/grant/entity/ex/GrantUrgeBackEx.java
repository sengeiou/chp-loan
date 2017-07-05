
package com.creditharmony.loan.borrow.grant.entity.ex;


import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.borrow.payback.entity.Payback;
/**
 * 放款失败催收服务费退回
 * @Class Name GrantUrgeBackEx
 * @author 朱静越
 * @Create In 2016年1月21日
 */
public class GrantUrgeBackEx extends DataEntity<GrantUrgeBackEx> {
	private static final long serialVersionUID = 3962172465594535962L;
	// 放款id,
	private String id;
	// 催收服务费主表id
	private String urgeId;
	// 退回中间人id
	private String returnIntermediaryId;
	// 合同编号1
	@ExcelField(title = "合同编号", type = 0, align = 2, sort = 1)
	private String contractCode; 
	// 客户姓名3
	@ExcelField(title = "客户姓名", type = 0, align = 2, sort = 10)
	private String  customerName;
	// 产品名称
	@ExcelField(title = "借款产品", type = 0, align = 2, sort = 40)
	private String productName;
	// 证件号码4
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 20)
	private String customerCertNum;
	// 划扣金额,为拆分表中的划扣金额
	private float deductAmount;
	// 查账金额
	private float auditAmount;
	// 期数11
	@ExcelField(title = "批复期限", type = 0, align = 2, sort = 90)
	private int  contractMonths;
	// 合同金额7
	@ExcelField(title = "合同金额", type = 0, align = 2, sort = 50)
	private String contractAmount;
	// 放款金额8
	@ExcelField(title = "放款金额", type = 0, align = 2, sort = 60)
	private String grantAmount;
	// 催收金额9，为合同金额的1%
	@ExcelField(title = "催收服务费金额", type = 0, align = 2, sort = 70)
	private String urgeMoeny;
	// 已催收服务费金额10，     催收服务费退回表中的退回金额
	@ExcelField(title = "已催收服务费金额", type = 0, align = 2, sort = 80)
	private BigDecimal returnAmount;
	// 标识15，从字典表中取值
	@ExcelField(title = "标识", type = 0, align = 2, sort = 130,dictType = "jk_deduct_plat")
	private String loanFlag;
	// 退款状态12，从字典表中取值
	@ExcelField(title = "退款状态", type = 0, align = 2, sort = 100,dictType = "jk_fee_return")
	private String returnStatus;
	// 放款日期开始13
	private Date lendingTimeBegin;
	// 放款日期
	@ExcelField(title = "放款日期", type = 0, align = 2, sort = 110)
	private Date lendingTime;
	// 放款日期结束
	private Date lendingTimeEnd;
	// 放款审核退回日期开始14
	private Date checkTimeBegin;
	// 审核日期
	@ExcelField(title = "放款审核退回日期", type = 0, align = 2, sort = 120)
	private Date checkTime;
	// 放款审核退回日期结束
	private Date checkTimeEnd;
	// 借款类型5，需要从字典表中取值
	@ExcelField(title = "借款类型", type = 0, align = 2, sort = 30,dictType = "jk_loan_type")
	private String classType;
	// 门店名称2
	@ExcelField(title = "门店名称", type = 0, align = 2, sort = 90)
	private String name;
	//门店ID 查询
	private String[] storeOrgIds;
	// 省份
	private String province;
	// 城市
	private String city;
	// 退回时间
	private Date returnTime;
	// 账户
	private String midName;
	// 划扣日期,直接从拆分表中取值
	private Date deductDate;
	// 拆分表中的回盘结果
	private String splitBackResult;
	// 进入蓝补金额
	private BigDecimal intoBlue;
	// 
	private Payback payback;
	// 查看历史
	private Date operateTime;
	private String operator;
	private String operateStep;
	private String operateResult;
	private String remark;
	private String appStatus;
	private String appStatusTmp;
	private String refundId;
	// 退款标识
	private String refundFlag;
	
	public String getUrgeId() {
		return urgeId;
	}
	public void setUrgeId(String urgeId) {
		this.urgeId = urgeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReturnIntermediaryId() {
		return returnIntermediaryId;
	}
	public void setReturnIntermediaryId(String returnIntermediaryId) {
		this.returnIntermediaryId = returnIntermediaryId;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
		
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getGrantAmount() {
		return grantAmount;
	}
	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getUrgeMoeny() {
		return urgeMoeny;
	}
	public void setUrgeMoeny(String urgeMoeny) {
		this.urgeMoeny = urgeMoeny;
	}
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLendingTimeBegin() {
		return lendingTimeBegin;
	}
	public void setLendingTimeBegin(Date lendingTimeBegin) {
		this.lendingTimeBegin = lendingTimeBegin;
	}
	public Date getLendingTimeEnd() {
		return lendingTimeEnd;
	}
	public void setLendingTimeEnd(Date lendingTimeEnd) {
		this.lendingTimeEnd = lendingTimeEnd;
	}
	public Date getCheckTimeBegin() {
		return checkTimeBegin;
	}
	public void setCheckTimeBegin(Date checkTimeBegin) {
		this.checkTimeBegin = checkTimeBegin;
	}
	public Date getCheckTimeEnd() {
		return checkTimeEnd;
	}
	public void setCheckTimeEnd(Date checkTimeEnd) {
		this.checkTimeEnd = checkTimeEnd;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperateStep() {
		return operateStep;
	}
	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
	}
	public String getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public float getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(float deductAmount) {
		this.deductAmount = deductAmount;
	}
	public float getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(float auditAmount) {
		this.auditAmount = auditAmount;
	}
	public String getMidName() {
		return midName;
	}
	public void setMidName(String midName) {
		this.midName = midName;
	}
	public Date getDeductDate() {
		return deductDate;
	}
	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}
	public String getSplitBackResult() {
		return splitBackResult;
	}
	public void setSplitBackResult(String splitBackResult) {
		this.splitBackResult = splitBackResult;
	}
	public BigDecimal getIntoBlue() {
		return intoBlue;
	}
	public void setIntoBlue(BigDecimal intoBlue) {
		this.intoBlue = intoBlue;
	}
	public Payback getPayback() {
		return payback;
	}
	public void setPayback(Payback payback) {
		this.payback = payback;
	}
	public String getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getAppStatusTmp() {
		return appStatusTmp;
	}
	public void setAppStatusTmp(String appStatusTmp) {
		this.appStatusTmp = appStatusTmp;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
    /**
     * @return the storeOrgIds
     */
    public String[] getStoreOrgIds() {
        return storeOrgIds;
    }
    /**
     * @param storeOrgIds the String [] storeOrgIds to set
     */
    public void setStoreOrgIds(String[] storeOrgIds) {
        this.storeOrgIds = storeOrgIds;
    }
}