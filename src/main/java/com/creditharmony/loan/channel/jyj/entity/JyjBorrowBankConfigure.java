package com.creditharmony.loan.channel.jyj.entity;

import java.math.BigDecimal;

import com.creditharmony.core.persistence.DataEntity;

@SuppressWarnings("serial")
public class JyjBorrowBankConfigure extends DataEntity<JyjBorrowBankConfigure> {

	private String id; // id

	private String bankCode; // 银行code

	private String bankName; // 银行name

	private BigDecimal firstLoanProportion; // 首次放款比例

	private BigDecimal endLoanProportion; // 尾款放款比例

	private String bankId;

	private int flag; // 停用启用

	private String loanCode;

	/**
	 * 产品编码
	 */
	private String productCode;

	public String getId() {
		return id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public BigDecimal getFirstLoanProportion() {
		return firstLoanProportion;
	}

	public BigDecimal getEndLoanProportion() {
		return endLoanProportion;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setFirstLoanProportion(BigDecimal firstLoanProportion) {
		this.firstLoanProportion = firstLoanProportion;
	}

	public void setEndLoanProportion(BigDecimal endLoanProportion) {
		this.endLoanProportion = endLoanProportion;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
