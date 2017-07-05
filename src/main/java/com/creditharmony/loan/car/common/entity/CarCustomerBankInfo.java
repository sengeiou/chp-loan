package com.creditharmony.loan.car.common.entity;

import com.creditharmony.core.persistence.DataEntity;

import java.util.Date;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;
import static com.creditharmony.loan.car.common.util.CryptoUtils.encryptPhones;

/**
 * 银行卡信息
 * @Class Name CarCustomerBankInfo
 * @author 安子帅
 * @Create In 2016年1月22日
 */
public class CarCustomerBankInfo extends DataEntity<CarCustomerBankInfo> {

	private static final long serialVersionUID = 2861034470842156417L;

	private String id; // ID
	private String loanCode; // 借款编码 
	private String bankAccountName; // 银行卡(或存折)户名
	private String bankProvince; // 开卡省
	private String bankProvinceCity; // 开卡省市
	private String bankCity; // 开卡市
	private String cardBank; // 开卡行
	private String applyBankName; // 支行
	private String bankCardNo; // 银行帐号
	private String createBy; // 创建人
	private Date createTime; // 创建时间
	private String top; // TOP
	private String bankSigningPlatform; // 签约平台
	private String israre; // 是否生僻字
    private String cardBankCode;//开卡行号
    private String accountAuthorizerName;//账户名称、授权人
    private String dictSourceType;//来源版本(1:chp1.0 ;2:chp2. 0;3:chp3.0)
    private String dictSourceTypePcl;//账户名称、授权人
    private String dictMaintainType;//维护类型：0:新增、1:修改 
    private String dictMaintainStatus;//维护状态：0.待审核、1.拒绝、2.已维护
    private String bankCheckResult;//审查结果
    private String bankCheckDesc;//审查意见
    private String oldBankAccountId;//老账户ID
    private String fileId;//文件ID
    private String fileName;//文件名称
    private String newCustomerPhone;//新手机号
    private String newCustomerPhoneEnc;//新手机号
    private String modifyBy;//修改人
    private Date modifyTime;//修改时间
    private String updateType;//修改类型1、手机号码 2、银行卡号 3、邮箱地址
    private String newEmail;//修改Email
    
    
    
	public String getCardBankCode() {
		return cardBankCode;
	}

	public void setCardBankCode(String cardBankCode) {
		this.cardBankCode = cardBankCode;
	}

	public String getBankProvinceCity() {
		return bankProvinceCity;
	}

	public void setBankProvinceCity(String bankProvinceCity) {
		this.bankProvinceCity = bankProvinceCity;
	}

	public String getIsrare() {
		return israre;
	}

	public void setIsrare(String israre) {
		this.israre = israre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getApplyBankName() {
		return applyBankName;
	}

	public void setApplyBankName(String applyBankName) {
		this.applyBankName = applyBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}

	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}

	public String getAccountAuthorizerName() {
		return accountAuthorizerName;
	}

	public void setAccountAuthorizerName(String accountAuthorizerName) {
		this.accountAuthorizerName = accountAuthorizerName;
	}

	public String getDictMaintainType() {
		return dictMaintainType;
	}

	public void setDictMaintainType(String dictMaintainType) {
		this.dictMaintainType = dictMaintainType;
	}

	public String getDictMaintainStatus() {
		return dictMaintainStatus;
	}

	public void setDictMaintainStatus(String dictMaintainStatus) {
		this.dictMaintainStatus = dictMaintainStatus;
	}

	public String getBankCheckResult() {
		return bankCheckResult;
	}

	public void setBankCheckResult(String bankCheckResult) {
		this.bankCheckResult = bankCheckResult;
	}

	public String getBankCheckDesc() {
		return bankCheckDesc;
	}

	public void setBankCheckDesc(String bankCheckDesc) {
		this.bankCheckDesc = bankCheckDesc;
	}

	public String getOldBankAccountId() {
		return oldBankAccountId;
	}

	public void setOldBankAccountId(String oldBankAccountId) {
		this.oldBankAccountId = oldBankAccountId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getNewCustomerPhone() {
        if(newCustomerPhone != null){
            newCustomerPhone = decryptPhones(newCustomerPhone,"t_cj_customer_bank_info","new_customer_phone");
        }
        return newCustomerPhone;
	}

	public String getNewCustomerPhoneEnc() {
		return newCustomerPhoneEnc;
	}

	public void setNewCustomerPhone(String newCustomerPhone) {
		if(newCustomerPhone != null && newCustomerPhone.length() == 11){
			newCustomerPhone = encryptPhones(newCustomerPhone,"t_cj_customer_bank_info","new_customer_phone");
		}
		this.newCustomerPhone = newCustomerPhone;
		this.newCustomerPhoneEnc = newCustomerPhone;
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

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getDictSourceType() {
		return dictSourceType;
	}

	public void setDictSourceType(String dictSourceType) {
		this.dictSourceType = dictSourceType;
	}

	public String getDictSourceTypePcl() {
		return dictSourceTypePcl;
	}

	public void setDictSourceTypePcl(String dictSourceTypePcl) {
		this.dictSourceTypePcl = dictSourceTypePcl;
	}
}
