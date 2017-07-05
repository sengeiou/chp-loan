package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 信用资料信息
 * @Class Name LoanCreditInfo
 * @author 张平
 * @Create In 2015年12月3日
 */
public class LoanCreditInfo extends DataEntity<LoanCreditInfo> {

	private static final long serialVersionUID = 3633821504669244368L;

	private String loanCode; // 借款编码

	private String creditAuthorizer; // 授权人

	private String dictMortgageType; // 抵押类型
	
	private String dictMortgageTypeLabel; // 抵押类型名称
	
	private String creditMortgageGoods; // 抵押物品

	private String orgCode; // 机构名称

	private BigDecimal creditLoanLimit; // 贷款额度

	private BigDecimal creditMonthsAmount; // 每月共额度

	private BigDecimal creditLoanBlance; // 贷款余额

	private Integer creditCardNum; // 信用卡总数
	
    public String getDictMortgageTypeLabel() {
		return dictMortgageTypeLabel;
	}

	public void setDictMortgageTypeLabel(String dictMortgageTypeLabel) {
		this.dictMortgageTypeLabel = dictMortgageTypeLabel;
	}

	public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getCreditAuthorizer() {
        return creditAuthorizer;
    }

    public void setCreditAuthorizer(String creditAuthorizer) {
        this.creditAuthorizer = creditAuthorizer;
    }

    public String getDictMortgageType() {
        return dictMortgageType;
    }

    public void setDictMortgageType(String dictMortgageType) {
        this.dictMortgageType = dictMortgageType;
    }

    public String getCreditMortgageGoods() {
        return creditMortgageGoods;
    }

    public void setCreditMortgageGoods(String creditMortgageGoods) {
        this.creditMortgageGoods = creditMortgageGoods;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public BigDecimal getCreditLoanLimit() {
        return creditLoanLimit;
    }

    public void setCreditLoanLimit(BigDecimal creditLoanLimit) {
        this.creditLoanLimit = creditLoanLimit;
    }

    public BigDecimal getCreditMonthsAmount() {
        return creditMonthsAmount;
    }

    public void setCreditMonthsAmount(BigDecimal creditMonthsAmount) {
        this.creditMonthsAmount = creditMonthsAmount;
    }

    public BigDecimal getCreditLoanBlance() {
        return creditLoanBlance;
    }

    public void setCreditLoanBlance(BigDecimal creditLoanBlance) {
        this.creditLoanBlance = creditLoanBlance;
    }

    public Integer getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(Integer creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

	
}