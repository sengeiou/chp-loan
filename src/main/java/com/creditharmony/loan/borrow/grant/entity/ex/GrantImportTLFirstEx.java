/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exGrantImportTLEx.java
 * @Create By 张灏
 * @Create In 2016年2月28日 下午4:33:29
 */
package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;

/**
 * 通联导入BEAN
 * @Class Name GrantImportTLFirstEx
 * @author 张灏
 * @Create In 2016年2月28日
 */
public class GrantImportTLFirstEx {
    // 序号
    @ExcelField(title = "文件序号", type = 0, align = 2, sort = 10)
    private String serialNum;
    // 文件名
    @ExcelField(title = "文件名", type = 0, align = 2, sort = 20)
    private String fileName;
    // 记录序号
    @ExcelField(title = "记录序号", type = 0, align = 2, sort = 30)
    private String logNo;
    // 交易类型
    @ExcelField(title = "交易类型", type = 0, align = 2, sort = 40)
    private String tradeType;
    // 处理状态
    @ExcelField(title = "处理状态", type = 0, align = 2, sort = 50)
    private String dealStatusName;
    // 商户
    @ExcelField(title = "商户", type = 0, align = 2, sort = 60)
    private String groceryRetailer;
    // 业务名称
    @ExcelField(title = "业务名称", type = 0, align = 2, sort = 70)
    private String businessName;
    // 银行代码
    @ExcelField(title = "开户银行", type = 0, align = 2, sort = 80)
    private String depositBank;
    // 账户号码
    @ExcelField(title = "账号", type = 0, align = 2, sort = 90)
    private String accountNumber;
    // 账户名称
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 100)
    private String accountName;
    // 交易金额
    @ExcelField(title = "交易金额", type = 0, align = 2, sort = 110)
    private String tradeAmount;    
    // 手续费
    @ExcelField(title = "手续费", type = 0, align = 2, sort = 120)
    private String poundage;
    // 省名字
    @ExcelField(title = "开户省", type = 0, align = 2, sort = 130)
    private String provinceName;
    // 城市名称
    @ExcelField(title = "开户市", type = 0, align = 2, sort = 140)
    private String cityName;
    // 支付行号
    @ExcelField(title = "支付行号", type = 0, align = 2, sort = 150)
    private String payForNumber;
    // 终端号
    @ExcelField(title = "终端号", type = 0, align = 2, sort = 160)
    private String terminateNo;
    // 提交时间
    @ExcelField(title = "提交时间", type = 0, align = 2, sort = 170)
    private String submitTime;
    // 商户审核时间
    @ExcelField(title = "商户审核时间", type = 0, align = 2, sort = 180)
    private String auditTime;
    // 完成时间
    @ExcelField(title = "完成时间", type = 0, align = 2, sort = 190)
    private String finishTime;
    // 原因
    @ExcelField(title = "原因", type = 0, align = 2, sort = 200)
    private String reason;
    // 保留字段
    @ExcelField(title = "保留字段", type = 0, align = 2, sort = 210)
    private String ReservedColumn;
    // 备注
    @ExcelField(title = "备注", type = 0, align = 2, sort = 220)
    private String remark;
    // 征信费
    @ExcelField(title = "征信费", type = 0, align = 2, sort = 230)
    private String feeCredit;
    // 信访费
    @ExcelField(title = "信访费", type = 0, align = 2, sort = 240)
    private String feePetition;
    // 催收服务费
    @ExcelField(title = "催收服务费", type = 0, align = 2, sort = 250)
    private String feeUrgeService;
    // 费用总计
    @ExcelField(title = "费用总计", type = 0, align = 2, sort = 260)
    private String feeSum;
    // 放款批次
    @ExcelField(title = "批次号", type = 0, align = 2, sort = 270)
    private String grantBatchCode;
     
    public String getSerialNum() {
        return serialNum;
    }
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getLogNo() {
        return logNo;
    }
    public void setLogNo(String logNo) {
        this.logNo = logNo;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public String getDealStatusName() {
        return dealStatusName;
    }
    public void setDealStatusName(String dealStatusName) {
        this.dealStatusName = dealStatusName;
    }
    public String getGroceryRetailer() {
        return groceryRetailer;
    }
    public void setGroceryRetailer(String groceryRetailer) {
        this.groceryRetailer = groceryRetailer;
    }
    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public String getDepositBank() {
        return depositBank;
    }
    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
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
    public String getTradeAmount() {
        return tradeAmount;
    }
    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
    public String getPoundage() {
        return poundage;
    }
    public void setPoundage(String poundage) {
        this.poundage = poundage;
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
    public String getPayForNumber() {
        return payForNumber;
    }
    public void setPayForNumber(String payForNumber) {
        this.payForNumber = payForNumber;
    }
    public String getTerminateNo() {
        return terminateNo;
    }
    public void setTerminateNo(String terminateNo) {
        this.terminateNo = terminateNo;
    }
    public String getSubmitTime() {
        return submitTime;
    }
    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
    public String getAuditTime() {
        return auditTime;
    }
    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }
    public String getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReservedColumn() {
        return ReservedColumn;
    }
    public void setReservedColumn(String reservedColumn) {
        ReservedColumn = reservedColumn;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
