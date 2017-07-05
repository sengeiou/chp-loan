package com.creditharmony.loan.common.entity;


import java.util.Date;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class LoanBank extends DataEntity<LoanBank>{

	// newId;更新账户临时ID
	private String newId;
    // 借款编码
    private String loanCode; 
    //开户行(name)
    private String bankCode;
    //开户行(name)
    private String bankName;
    // 开户行名称(文字)
    private String bankNameLabel;
    // 银行卡所在城市省(Code)
    private String bankProvince; 
    // 银行所在城市省（名称）
    private String bankProvinceName;
    // 银行卡所在城市市(Code)
    private String bankCity; 
    // 银行卡所在城市市(名称)
    private String bankCityName; 
    // 银行卡ocr地址
    private String bankOrc;         
    // 卡或折
    private String dictCreaType;     
    // 具体支行
    private String bankBranch;        
    // 开户姓名
    private String bankAccountName;    
    // 账户
    private String bankAccount;         
    // 签约平台
    private String bankSigningPlatform;  
    // 签约平台 文字
    private String bankSigningPlatformName;
    // 维护状态：0待审核、1拒绝 2已维护
    private String dictMaintainStatus;    
    // 置顶
    private Integer bankTopFlag ;            
    // 维护类型：0新增、1修改
    private String dictMaintainType;       
    // 审查结果
    private Integer bankCheckResult;     
    // 审查意见
    private String bankCheckDesc;           
    // 原账号ID
    private String bankOldAccount;         
    // 是否生僻字（0：否 1：是）
    private Integer bankIsRareword;       
    // 金账户开户行省市
    private String bankJzhKhhss;             
    // 金账户开户行区县
    private String bankJzhKhhqx;              
    // 授权人
    private String bankAuthorizer;  
    // 还款标识
    private String repaymentFlag = "0";
    
    private List<LoanBank> loanBack;
    
    private String bankNo;
    private String idType;
    private String IdNo;
    private String mobile;
    
    private String trSerialNo;
    
    private Date transDate; 
    
    private String realAuthen;
    
    private String cardType;
    
    private String  klSign;
    
    private String singleSerialNo;
    
    
    private String cjSmNo;
    private String   cjQyNo;
    private String  protocolNumber;
    private String cjAuthen;
    private String cjSign;
    private String cjSignFailure;
    private String cjAuthenFailure;
    
	public String getBankNameLabel() {
		return bankNameLabel;
	}

	public void setBankNameLabel(String bankNameLabel) {
		this.bankNameLabel = bankNameLabel;
	}

	public String getBankProvinceName() {
        return bankProvinceName;
    }

    public void setBankProvinceName(String bankProvinceName) {
        this.bankProvinceName = bankProvinceName;
    }

    public String getBankCityName() {
        return bankCityName;
    }

    public void setBankCityName(String bankCityName) {
        this.bankCityName = bankCityName;
    }

    public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
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

	public String getBankOrc() {
		return bankOrc;
	}

	public void setBankOrc(String bankOrc) {
		this.bankOrc = bankOrc;
	}

	public String CardType() {
		return dictCreaType;
	}

	public void setDictCreaType(String dictCreaType) {
		this.dictCreaType = dictCreaType;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankSigningPlatform() {
		return bankSigningPlatform;
	}

	public void setBankSigningPlatform(String bankSigningPlatform) {
		this.bankSigningPlatform = bankSigningPlatform;
	}

	public String getBankSigningPlatformName() {
		return bankSigningPlatformName;
	}

	public void setBankSigningPlatformName(String bankSigningPlatformName) {
		this.bankSigningPlatformName = bankSigningPlatformName;
	}

	public String getDictMaintainStatus() {
		return dictMaintainStatus;
	}

	public void setDictMaintainStatus(String dictMaintainStatus) {
		this.dictMaintainStatus = dictMaintainStatus;
	}

	public Integer getBankTopFlag() {
		return bankTopFlag;
	}

	public void setBankTopFlag(Integer bankTopFlag) {
		this.bankTopFlag = bankTopFlag;
	}

	public String getDictMaintainType() {
		return dictMaintainType;
	}

	public void setDictMaintainType(String dictMaintainType) {
		this.dictMaintainType = dictMaintainType;
	}

	public Integer getBankCheckResult() {
		return bankCheckResult;
	}

	public void setBankCheckResult(Integer bankCheckResult) {
		this.bankCheckResult = bankCheckResult;
	}

	public String getBankCheckDesc() {
		return bankCheckDesc;
	}

	public void setBankCheckDesc(String bankCheckDesc) {
		this.bankCheckDesc = bankCheckDesc;
	}

	public String getBankOldAccount() {
		return bankOldAccount;
	}

	public void setBankOldAccount(String bankOldAccount) {
		this.bankOldAccount = bankOldAccount;
	}

	public Integer getBankIsRareword() {
		return bankIsRareword;
	}

	public void setBankIsRareword(Integer bankIsRareword) {
		this.bankIsRareword = bankIsRareword;
	}

	public String getBankJzhKhhss() {
		return bankJzhKhhss;
	}

	public void setBankJzhKhhss(String bankJzhKhhss) {
		this.bankJzhKhhss = bankJzhKhhss;
	}

	public String getBankJzhKhhqx() {
		return bankJzhKhhqx;
	}

	public void setBankJzhKhhqx(String bankJzhKhhqx) {
		this.bankJzhKhhqx = bankJzhKhhqx;
	}

	public String getBankAuthorizer() {
		return bankAuthorizer;
	}

	public void setBankAuthorizer(String bankAuthorizer) {
		this.bankAuthorizer = bankAuthorizer;
	}

	public List<LoanBank> getLoanBack() {
		return loanBack;
	}

	public String getRepaymentFlag() {
		return repaymentFlag;
	}

	public void setRepaymentFlag(String repaymentFlag) {
		this.repaymentFlag = repaymentFlag;
	}

	public void setLoanBack(List<LoanBank> loanBack) {
		this.loanBack = loanBack;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTrSerialNo() {
		return trSerialNo;
	}

	public void setTrSerialNo(String trSerialNo) {
		this.trSerialNo = trSerialNo;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getRealAuthen() {
		return realAuthen;
	}

	public void setRealAuthen(String realAuthen) {
		this.realAuthen = realAuthen;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getKlSign() {
		return klSign;
	}

	public void setKlSign(String klSign) {
		this.klSign = klSign;
	}

	public String getDictCreaType() {
		return dictCreaType;
	}

	public String getSingleSerialNo() {
		return singleSerialNo;
	}

	public void setSingleSerialNo(String singleSerialNo) {
		this.singleSerialNo = singleSerialNo;
	}

	public String getCjAuthen() {
		return cjAuthen;
	}

	public void setCjAuthen(String cjAuthen) {
		this.cjAuthen = cjAuthen;
	}

	public String getCjSmNo() {
		return cjSmNo;
	}

	public String getCjQyNo() {
		return cjQyNo;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public String getCjSign() {
		return cjSign;
	}

	public String getCjSignFailure() {
		return cjSignFailure;
	}

	public String getCjAuthenFailure() {
		return cjAuthenFailure;
	}

	public void setCjSmNo(String cjSmNo) {
		this.cjSmNo = cjSmNo;
	}

	public void setCjQyNo(String cjQyNo) {
		this.cjQyNo = cjQyNo;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public void setCjSign(String cjSign) {
		this.cjSign = cjSign;
	}

	public void setCjSignFailure(String cjSignFailure) {
		this.cjSignFailure = cjSignFailure;
	}

	public void setCjAuthenFailure(String cjAuthenFailure) {
		this.cjAuthenFailure = cjAuthenFailure;
	}

	
}