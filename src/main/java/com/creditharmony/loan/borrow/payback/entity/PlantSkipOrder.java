package com.creditharmony.loan.borrow.payback.entity;

import java.util.List;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.common.entity.CodeName;

@SuppressWarnings("serial")
public class PlantSkipOrder extends DataEntity<PlantSkipOrder> {
    /**
     * 银行code
     * 
     */
    private String bankCode;

    /**
     * 平台规则名称
     */
    private String platformRuleName;
    
    /**
     * 平台规则
     */
    private String  platformRule;   
    /**
     * 是否启用  
     */
    private String status;

    private String isConcentrate;
    
    private List<String>  deductTypeList;  
    private List<String>  platIdList;  
    
    private List<CodeName>  platDeductTypeList;


	public String getIsConcentrate() {
		return isConcentrate;
	}

	public void setIsConcentrate(String isConcentrate) {
		this.isConcentrate = isConcentrate;
	}

	public String getBankCode() {
		return bankCode;
	}


	public String getPlatformRuleName() {
		return platformRuleName;
	}

	public String getPlatformRule() {
		return platformRule;
	}

	public String getStatus() {
		return status;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public void setPlatformRuleName(String platformRuleName) {
		this.platformRuleName = platformRuleName;
	}

	public void setPlatformRule(String platformRule) {
		this.platformRule = platformRule;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getDeductTypeList() {
		return deductTypeList;
	}

	public List<String> getPlatIdList() {
		return platIdList;
	}

	public void setDeductTypeList(List<String> deductTypeList) {
		this.deductTypeList = deductTypeList;
	}

	public void setPlatIdList(List<String> platIdList) {
		this.platIdList = platIdList;
	}

	public List<CodeName> getPlatDeductTypeList() {
		return platDeductTypeList;
	}

	public void setPlatDeductTypeList(List<CodeName> platDeductTypeList) {
		this.platDeductTypeList = platDeductTypeList;
	}
}

