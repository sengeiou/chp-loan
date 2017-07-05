/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exGrantImportZJEx.java
 * @Create By 张灏
 * @Create In 2016年2月28日 下午4:29:47
 */
package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 中金导入BEAN
 * @Class Name GrantImportZJEx
 * @author 张灏
 * @Create In 2016年2月28日
 */
public class GrantImportZJEx {
   // 流水号
    @ExcelField(title = "序号", type = 0, align = 2, sort = 10)
    private String serialNum;
    // 交易时间
    @ExcelField(title = "交易时间", type = 0, align = 2, sort = 20)
    private String  exchangeHour;
    // 机构名称
    @ExcelField(title = "机构名称", type = 0, align = 2, sort = 30)
    private String  orgName;
    // 批次号
    @ExcelField(title = "批次号", type = 0, align = 2, sort = 40)
    private String grantPch;
    // 明细号
    @ExcelField(title = "明细号", type = 0, align = 2, sort = 50)
    private String listNo;
    // 交易金额
    @ExcelField(title = "交易金额", type = 0, align = 2, sort = 60)
    private String sum;
    // 结算金额
    @ExcelField(title = "结算金额", type = 0, align = 2, sort = 70)
    private String settlementAmount;
    // 银行代码
    @ExcelField(title = "银行ID", type = 0, align = 2, sort = 80)
    private String bankID;
    // 账户类型
    @ExcelField(title = "帐户类型", type = 0, align = 2, sort = 90)
    private String accountType;
    // 账户号码
    @ExcelField(title = "账户号", type = 0, align = 2, sort = 100)
    private String accountNumber;
    // 账户名称
    @ExcelField(title = "账户名", type = 0, align = 2, sort = 110)
    private String accountName;
    // 分支行名称
    @ExcelField(title = "分支行名称", type = 0, align = 2, sort = 120)
    private String bankBranch;
    // 分支行省名字
    @ExcelField(title = "分支行省份", type = 0, align = 2, sort = 130)
    private String provinceName;
    // 城市名称
    @ExcelField(title = "分支行城市", type = 0, align = 2, sort = 140)
    private String cityName;
    // 备注
    @ExcelField(title = "备注信息", type = 0, align = 2, sort = 150)
    private String remark;
    // 手机号码
    @ExcelField(title = "手机号码", type = 0, align = 2, sort = 160)
    private String telePhone;
    // 电子邮箱
    @ExcelField(title = "电子邮件", type = 0, align = 2, sort = 170)
    private String email;
    //证件类型（code）
    @ExcelField(title = "证件类型", type = 0, align = 2, sort = 180, dictType = "dict_cert_type")
    private String certType;
    // 证件号码
    @ExcelField(title = "证件号码", type = 0, align = 2, sort = 190)
    private String identityCode;
    // 银行支付的时间
    @ExcelField(title = "银行支付的时间", type = 0, align = 2, sort = 200)
    private String bankPayTime;
    // 交易状态
    @ExcelField(title = "交易状态", type = 0, align = 2, sort = 210)
    private String tradeStatus;
    // 银行响应代码
    @ExcelField(title = "银行响应代码", type = 0, align = 2, sort = 220)
    private String bankResponseCode;
    // 银行响应消息
    @ExcelField(title = "银行响应消息", type = 0, align = 2, sort = 230)
    private String bankResponseMsg;
    // 征信费
    @ExcelField(title = "征信费", type = 0, align = 2, sort = 240)
    private String feeCredit;
    // 信访费
    @ExcelField(title = "信访费", type = 0, align = 2, sort = 250)
    private String feePetition;
    // 催收服务费
    @ExcelField(title = "催收服务费", type = 0, align = 2, sort = 260)
    private String feeUrgeService;
    // 费用总计
    @ExcelField(title = "费用总计", type = 0, align = 2, sort = 270)
    private String feeSum;
    // 放款批次
    @ExcelField(title = "放款批次", type = 0, align = 2, sort = 280)
    private String grantBatchCode;
    
    public String getSerialNum() {
        return serialNum;
    }
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
    public String getExchangeHour() {
        return exchangeHour;
    }
    public void setExchangeHour(String exchangeHour) {
        this.exchangeHour = exchangeHour;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getGrantPch() {
        return grantPch;
    }
    public void setGrantPch(String grantPch) {
        this.grantPch = grantPch;
    }
    public String getListNo() {
        return listNo;
    }
    public void setListNo(String listNo) {
        this.listNo = listNo;
    }
    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }
    public String getSettlementAmount() {
        return settlementAmount;
    }
    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }
    public String getBankID() {
        return bankID;
    }
    public void setBankID(String bankID) {
        this.bankID = bankID;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getBankBranch() {
        return bankBranch;
    }
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getTelePhone() {
        return telePhone;
    }
    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCertType() {
        return certType;
    }
    public void setCertType(String certType) {
        this.certType = certType;
    }
    public String getIdentityCode() {
        return identityCode;
    }
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }
    public String getBankPayTime() {
        return bankPayTime;
    }
    public void setBankPayTime(String bankPayTime) {
        this.bankPayTime = bankPayTime;
    }
    public String getTradeStatus() {
        return tradeStatus;
    }
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    public String getBankResponseCode() {
        return bankResponseCode;
    }
    public void setBankResponseCode(String bankResponseCode) {
        this.bankResponseCode = bankResponseCode;
    }
    public String getBankResponseMsg() {
        return bankResponseMsg;
    }
    public void setBankResponseMsg(String bankResponseMsg) {
        this.bankResponseMsg = bankResponseMsg;
    }
	public String getGrantBatchCode() {
		return grantBatchCode;
	}
	public void setGrantBatchCode(String grantBatchCode) {
		this.grantBatchCode = grantBatchCode;
	}
	public String getFeeCredit() {
		return feeCredit;
	}
	public void setFeeCredit(String feeCredit) {
		this.feeCredit = feeCredit;
	}
	public String getFeePetition() {
		return feePetition;
	}
	public void setFeePetition(String feePetition) {
		this.feePetition = feePetition;
	}
	public String getFeeUrgeService() {
		return feeUrgeService;
	}
	public void setFeeUrgeService(String feeUrgeService) {
		this.feeUrgeService = feeUrgeService;
	}
	public String getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(String feeSum) {
		this.feeSum = feeSum;
	}
}
