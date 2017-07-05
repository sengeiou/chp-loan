/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut.java
 * @Create By zhangfeng
 * @Create In 2015年12月11日 上午9:41:04
 */
package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;

/**
 * 还款汇账外部导入数据表
 * @Class Name PaybackTransferOut
 * @author zhangfeng
 * @Create In 2015年11月30日
 */
@SuppressWarnings("serial")
public class PaybackTransferOut extends DataEntity<PaybackTransferOut> {

	@ExcelField(title = "序号（唯一标识）", type = 2, align = 2, sort = 10)
	private String orderNumber;
	// 存款日期
	@ExcelField(title = "存款日期", type = 2, align = 2, sort = 20)
	private	String outDepositTimeStr;
	
	private Date outDepositTime;
	// 入账银行
	@ExcelField(title = "存入银行", type = 2, align = 2, sort = 30)
	private String outEnterBankAccount;
	// 实际到账金额
	@ExcelField(title = "实际到账金额", type = 2, align = 2, sort = 40)
	private String outReallyAmountStr;
	private BigDecimal outReallyAmount;
	// 存款人
	@ExcelField(title = "存款人", type = 2, align = 2, sort = 50)
	private String outDepositName;
	// 查账状态
	@ExcelField(title = "查账状态", type = 2, align = 2, sort = 60, dictType = "jk_bankserial_check")
	private String outAuditStatus;
	// 查账状态(名称)
	private String outAuditStatusLabel;
	// 合同编号
	@ExcelField(title = "合同编号", type = 2, align = 2, sort = 70)
	private String contractCode;
	// 修改查账状态临时合同编号
	private String contractCodeTemp;
	// 查账日期
	@ExcelField(title = "查账日期", type = 2, align = 2, sort = 80)
	private String outTimeCheckAccountStr;
	private Date outTimeCheckAccount;
	// 备注
	@ExcelField(title = "备注", type = 2, align = 2, sort = 90)
	private String outRemark;
	
	// 申请ID
	private String rPaybackApplyId;
	// 流水号
	private String outFlow;
	// 转账信息ID
	private String transferAccountsId;
	// 序号
	// 关联类型
	private String relationType;
	// 存入银行
	private MiddlePerson middlePerson;
	
	//操作角色，1时为中和查账
	private String operateRole;
	
	// 以下为辅助字段
	
	// 查账开始时间(列表查询)
	private Date startAuditedDate;
	// 查账结束时间(列表查询)
	private Date endAuditedDate;
	// 存款开始时间(列表查询)
	private Date startDepositdDate;
	// 存款结束时间(列表查询)
	private Date endDepositDate;
	// 金额(列表查询)
	private BigDecimal beginOutReallyAmount;
	// 金额(列表查询)
	private BigDecimal endOutReallyAmount;

	public BigDecimal getBeginOutReallyAmount() {
		return beginOutReallyAmount;
	}

	public void setBeginOutReallyAmount(BigDecimal beginOutReallyAmount) {
		this.beginOutReallyAmount = beginOutReallyAmount;
	}

	public BigDecimal getEndOutReallyAmount() {
		return endOutReallyAmount;
	}

	public void setEndOutReallyAmount(BigDecimal endOutReallyAmount) {
		this.endOutReallyAmount = endOutReallyAmount;
	}

	public String getOutDepositTimeStr() {
		return outDepositTimeStr;
	}

	public void setOutDepositTimeStr(String outDepositTimeStr) {
		this.outDepositTimeStr = outDepositTimeStr;
	}

	private List<PaybackTransferOut> paybackTransferOut;

	public String getTransferAccountsId() {
		return transferAccountsId;
	}

	public void setTransferAccountsId(String transferAccountsId) {
		this.transferAccountsId = transferAccountsId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractCodeTemp() {
		return contractCodeTemp;
	}

	public void setContractCodeTemp(String contractCodeTemp) {
		this.contractCodeTemp = contractCodeTemp;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOutFlow() {
		return outFlow;
	}

	public void setOutFlow(String outFlow) {
		this.outFlow = outFlow;
	}

	public Date getOutDepositTime() {
		return outDepositTime;
	}

	public void setOutDepositTime(Date outDepositTime) {
		this.outDepositTime = outDepositTime;
	}

	public String getOutEnterBankAccount() {
		return outEnterBankAccount;
	}

	public void setOutEnterBankAccount(String outEnterBankAccount) {
		this.outEnterBankAccount = outEnterBankAccount;
	}
	
	public String getOutReallyAmountStr() {
		return outReallyAmountStr;
	}

	public void setOutReallyAmountStr(String outReallyAmountStr) {
		this.outReallyAmountStr = outReallyAmountStr;
	}

	public BigDecimal getOutReallyAmount() {
		return outReallyAmount;
	}

	public void setOutReallyAmount(BigDecimal outReallyAmount) {
		this.outReallyAmount = outReallyAmount;
	}

	public String getOutDepositName() {
		return outDepositName;
	}

	public void setOutDepositName(String outDepositName) {
		this.outDepositName = outDepositName;
	}

	public String getOutAuditStatus() {
		return outAuditStatus;
	}

	public void setOutAuditStatus(String outAuditStatus) {
		this.outAuditStatus = outAuditStatus;
	}

	public Date getOutTimeCheckAccount() {
		return outTimeCheckAccount;
	}

	public void setOutTimeCheckAccount(Date outTimeCheckAccount) {
		this.outTimeCheckAccount = outTimeCheckAccount;
	}

	public String getOutRemark() {
		return outRemark;
	}

	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}

	public List<PaybackTransferOut> getPaybackTransferOut() {
		return paybackTransferOut;
	}

	public void setPaybackTransferOut(List<PaybackTransferOut> paybackTransferOut) {
		this.paybackTransferOut = paybackTransferOut;
	}

	public String getrPaybackApplyId() {
		return rPaybackApplyId;
	}

	public void setrPaybackApplyId(String rPaybackApplyId) {
		this.rPaybackApplyId = rPaybackApplyId;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public MiddlePerson getMiddlePerson() {
		return middlePerson;
	}

	public void setMiddlePerson(MiddlePerson middlePerson) {
		this.middlePerson = middlePerson;
	}

	public Date getStartAuditedDate() {
		return startAuditedDate;
	}

	public void setStartAuditedDate(Date startAuditedDate) {
		this.startAuditedDate = startAuditedDate;
	}

	public Date getEndAuditedDate() {
		return endAuditedDate;
	}

	public void setEndAuditedDate(Date endAuditedDate) {
		this.endAuditedDate = endAuditedDate;
	}

	public Date getStartDepositdDate() {
		return startDepositdDate;
	}

	public void setStartDepositdDate(Date startDepositdDate) {
		this.startDepositdDate = startDepositdDate;
	}

	public Date getEndDepositDate() {
		return endDepositDate;
	}

	public void setEndDepositDate(Date endDepositDate) {
		this.endDepositDate = endDepositDate;
	}

	public String getOutAuditStatusLabel() {
		return outAuditStatusLabel;
	}

	public void setOutAuditStatusLabel(String outAuditStatusLabel) {
		this.outAuditStatusLabel = outAuditStatusLabel;
	}

	public String getOperateRole() {
		return operateRole;
	}

	public void setOperateRole(String operateRole) {
		this.operateRole = operateRole;
	}

	public String getOutTimeCheckAccountStr() {
		return outTimeCheckAccountStr;
	}
	public void setOutTimeCheckAccountStr(String outTimeCheckAccountStr) {
		this.outTimeCheckAccountStr = outTimeCheckAccountStr;
	}
	
}