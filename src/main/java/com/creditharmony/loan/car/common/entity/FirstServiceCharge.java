package com.creditharmony.loan.car.common.entity;

public class FirstServiceCharge {
    private String id;

    private String ninetyBelowRate;

    private String ninetyAboveRate;
    
    private String contractVersion;
    
    

    public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNinetyBelowRate() {
        return ninetyBelowRate;
    }

    public void setNinetyBelowRate(String ninetyBelowRate) {
        this.ninetyBelowRate = ninetyBelowRate == null ? null : ninetyBelowRate.trim();
    }

    public String getNinetyAboveRate() {
        return ninetyAboveRate;
    }

    public void setNinetyAboveRate(String ninetyAboveRate) {
        this.ninetyAboveRate = ninetyAboveRate == null ? null : ninetyAboveRate.trim();
    }
}