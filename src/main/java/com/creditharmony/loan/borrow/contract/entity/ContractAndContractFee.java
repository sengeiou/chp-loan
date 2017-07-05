package com.creditharmony.loan.borrow.contract.entity;

public class ContractAndContractFee {

    private static final long serialVersionUID = 1L;
	
	private String contractCode;
	
	private Double contractAmount;
	
	private Double feePaymentAmount;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Double getFeePaymentAmount() {
		return feePaymentAmount;
	}

	public void setFeePaymentAmount(Double feePaymentAmount) {
		this.feePaymentAmount = feePaymentAmount;
	}
}
