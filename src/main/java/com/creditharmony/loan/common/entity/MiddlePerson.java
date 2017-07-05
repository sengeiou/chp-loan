package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 中间人实体类
 * @Class Name MiddlePerson
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@SuppressWarnings("serial")
public class MiddlePerson extends DataEntity<MiddlePerson>{
	// 中间人id
	private String id;
	// 中间人姓名
    private String middleName; 
    // 中间人证件号码
    private String midCertNo; 
    // 开户行
    private String midBankName;  
    // 银行账号
    private String bankCardNo;  
    // 地址
    private String address; 
    // 启用标识
    private String status;
    // 开户行名称
    private String accountBank;
    // 存入标识
    private String depositFlag;
    // 存入方式  网银 线上 
    private String wayFlag;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMidCertNo() {
		return midCertNo;
	}

	public void setMidCertNo(String midCertNo) {
		this.midCertNo = midCertNo;
	}

	public String getMidBankName() {
		return midBankName;
	}

	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getDepositFlag() {
		return depositFlag;
	}

	public void setDepositFlag(String depositFlag) {
		this.depositFlag = depositFlag;
	}

	public String getWayFlag() {
		return wayFlag;
	}

	public void setWayFlag(String wayFlag) {
		this.wayFlag = wayFlag;
	}
	
	// 首次查询标识 
	private String firstFlag = "1";

	public String getFirstFlag() {
		return firstFlag;
	}

	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	
	
}