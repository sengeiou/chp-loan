/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exGrantExportTLEx.java
 * @Create By 张灏
 * @Create In 2016年2月26日 上午11:21:37
 */
package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 通联JAVABEAN
 * 
 * @Class Name GrantExportTLEx
 * @author 张灏
 * @Create In 2016年2月26日
 */
public class GrantExportTLEx extends DataEntity<GrantExportTLEx> {

    private static final long serialVersionUID = 3479077883300556954L;

    // 序号
    @ExcelField(title = "序号*", type = 0, align = 2, sort = 10)
    private String serialNum;
    // 用户编号
    @ExcelField(title = "用户编号", type = 0, align = 2, sort = 15)
    private String customerCode;
    // 银行代码
    @ExcelField(title = "银行代码*", type = 0, align = 2, sort = 20)
    private String bankCode;
    // 账户类型
    @ExcelField(title = "帐号类型", type = 0, align = 2, sort = 30)
    private String accountNumberType;
    // 账户号码
    @ExcelField(title = "账号*", type = 0, align = 2, sort = 40)
    private String accountNumber;
    // 账户名称
    @ExcelField(title = "户名*", type = 0, align = 2, sort = 50)
    private String accountName;
    // 省名字
    @ExcelField(title = "省", type = 0, align = 2, sort = 60)
    private String provinceName;
    // 省Code
    private String provinceCode;
    // 城市Code
    private String cityCode;
    // 城市名称
    @ExcelField(title = "市", type = 0, align = 2, sort = 70)
    private String cityName;
    // 开户行名称
    @ExcelField(title = "开户行名称", type = 0, align = 2, sort = 80)
    private String bankBranch;
    // 账户类型
    @ExcelField(title = "账户类型", type = 0, align = 2, sort = 90)
    private String accountType;
    // 金额
    @ExcelField(title = "金额*", type = 0, align = 2, sort = 100)
    private Double grantAmount;
    // 货币类型
    @ExcelField(title = "货币类型", type = 0, align = 2, sort = 110)
    private String currencyType;
    // 协议号
    @ExcelField(title = "协议号", type = 0, align = 2, sort = 120)
    private String agreementNumber;
    // 协议用户编号
    @ExcelField(title = "协议用户编号", type = 0, align = 2, sort = 130)
    private String agreementCustNum;
    // 证件类型（Code）
    private String certType;
    // 证件类型（name）
    @ExcelField(title = "开户证件类型", type = 0, align = 2, sort = 140, dictType = "dict_cert_type")
    private String certTypeName;
    // 证件号码
    @ExcelField(title = "证件号", type = 0, align = 2, sort = 150)
    private String identityCode;
    // 手机号码
    @ExcelField(title = "手机号/小灵通", type = 0, align = 2, sort = 160)
    private String telePhone;
    // 手机号码
    @ExcelField(title = "自定义用户号", type = 0, align = 2, sort = 170)
    private String userDefinedNum;
    // 备注
    @ExcelField(title = "备注", type = 0, align = 2, sort = 180)
    private String remark;
    // 电子邮箱
    @ExcelField(title = "反馈码", type = 0, align = 2, sort = 190)
    private String feedbackCode;
    // 电子邮箱
    @ExcelField(title = "原因", type = 0, align = 2, sort = 200)
    private String reason;
    // 征信费
    @ExcelField(title = "征信费", type = 0, align = 2, sort = 210)
    private Double feeCredit;
    // 信访费
    @ExcelField(title = "信访费", type = 0, align = 2, sort = 220)
    private Double feePetiton;
    // 催收服务费
    @ExcelField(title = "催收服务费", type = 0, align = 2, sort = 230)
    private Double feeUrgeService;
    // 费用总计:催收费+信访费+征信费
    @ExcelField(title = "费用总计", type = 0, align = 2, sort = 240)
    private Double feeSum;    
    // 合同编号
    private String contractCode;
    // 产品Code
    private String productCode;
    // 批次号
    private String grantPch;
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountNumberType() {
        return accountNumberType;
    }

    public void setAccountNumberType(String accountNumberType) {
        this.accountNumberType = accountNumberType;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(Double grantAmount) {
        this.grantAmount = grantAmount;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getAgreementCustNum() {
        return agreementCustNum;
    }

    public void setAgreementCustNum(String agreementCustNum) {
        this.agreementCustNum = agreementCustNum;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getUserDefinedNum() {
        return userDefinedNum;
    }

    public void setUserDefinedNum(String userDefinedNum) {
        this.userDefinedNum = userDefinedNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFeedbackCode() {
        return feedbackCode;
    }

    public void setFeedbackCode(String feedbackCode) {
        this.feedbackCode = feedbackCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getGrantPch() {
        return grantPch;
    }

    public void setGrantPch(String grantPch) {
        this.grantPch = grantPch;
    }

	public Double getFeeCredit() {
		return feeCredit;
	}

	public void setFeeCredit(Double feeCredit) {
		this.feeCredit = feeCredit;
	}

	public Double getFeePetiton() {
		return feePetiton;
	}

	public void setFeePetiton(Double feePetiton) {
		this.feePetiton = feePetiton;
	}

	public Double getFeeUrgeService() {
		return feeUrgeService;
	}

	public void setFeeUrgeService(Double feeUrgeService) {
		this.feeUrgeService = feeUrgeService;
	}

	public Double getFeeSum() {
		return feeSum;
	}

	public void setFeeSum(Double feeSum) {
		this.feeSum = feeSum;
	}
}
