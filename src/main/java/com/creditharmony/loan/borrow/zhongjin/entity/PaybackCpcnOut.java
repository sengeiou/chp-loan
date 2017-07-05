package com.creditharmony.loan.borrow.zhongjin.entity;

import java.math.BigDecimal;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 还款汇账外部导入数据表
 * 
 * @Class Name PaybackCpcnOut
 * @author WJJ
 * @Create In 2016年3月16日
 */
@SuppressWarnings("paybackCpcnOut")
public class PaybackCpcnOut extends DataEntity<PaybackCpcnOut> {

	private String cpcnId;
	// 序列号
	@ExcelField(title = "序列号", type = 2, align = 2, sort = 10)
	private String serialNum;
	// 银行账号
	@ExcelField(title = "银行账号", type = 2, align = 2, sort = 20)
	private String accountNum;
	// 户名
	@ExcelField(title = "户名", type = 2, align = 2, sort = 30)
	private String accountName;
	// 金额
	@ExcelField(title = "金额", type = 0, align = 2, sort = 40)
	private BigDecimal dealMoney;
	// 金额
	@ExcelField(title = "实还金额", type = 0, align = 2, sort = 50)
	private BigDecimal applyReallyAmount;
	// 银行名称
	@ExcelField(title = "银行名称", type = 2, align = 2, sort = 60)
	private String bankName;
	// 账户类型
	@ExcelField(title = "账户类型", type = 2, align = 2, sort = 70)
	private String accountType;
	// 合同编号
	@ExcelField(title = "合同编号", type = 2, align = 2, sort = 80)
	private String contractCode;
	// 操作日期
	@ExcelField(title = "操作日期", type = 2, align = 2, sort = 90)
	private String operateTime;
	// 回盘时间
	@ExcelField(title = "回盘时间", type = 2, align = 2, sort = 100)
	private String backTime;
	// 回盘结果
	@ExcelField(title = "回盘结果", type = 2, align = 2, sort = 110)
	private String backResult;
	// 回盘原因
	@ExcelField(title = "回盘原因", type = 2, align = 2, sort = 120)
	private String backReason;
	
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public BigDecimal getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountType() {
		if("11".equals(this.accountType))
			return "个人账户";
		else if("12".equals(this.accountType))
			return "企业账户";
		else
			return "";
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getBackTime() {
		return backTime;
	}
	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}
	public String getBackResult() {
		return backResult;
	}
	public void setBackResult(String backResult) {
		this.backResult = backResult;
	}
	public String getBackReason() {
		return backReason;
	}
	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}
	public String getCpcnId() {
		return cpcnId;
	}
	public void setCpcnId(String cpcnId) {
		this.cpcnId = cpcnId;
	}
	public BigDecimal getApplyReallyAmount() {
		return applyReallyAmount;
	}
	public void setApplyReallyAmount(BigDecimal applyReallyAmount) {
		this.applyReallyAmount = applyReallyAmount;
	}
		
}