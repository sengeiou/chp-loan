package com.creditharmony.loan.borrow.borrowlist.view;

import java.util.Date;

import com.creditharmony.bpm.frame.view.BaseTaskItemView;
/**
 * 信借待办列表bean
 * @Class Name 
 * @author zhangping
 * @Create In 2015年11月24日
 */
public class BorrowListItems extends BaseTaskItemView {
    // 合同版本号
	private String contractVersion;
	// 合同编号
	private String contractCode;
	// 客户姓名
	private String customerName;
	// 共借人姓名
	private String coborrowerName;
	// 省份   
	private String addrProvince;
	// 城市
	private String addrCity;
	// 门店Id
	private String contStoresId;
	// 门店名称
	private String contStoresName;
	// 借款产品
	private String borrowProduct; 
	// 状态
	private String dictStatus; 
	// 状态码
	private String dictStatusCode;
	// 批复金额 
	private Double  money;
	// 批复分期
	private Integer  hisAmountMonths;	
	// 是否电销 
	private String  loanIsPhone;
	//进件时间
	private Date customerIntoTime;	
	// 上调标识
	private String   loanRaise;	
	// 加急标识
	private String loanIsUrgent;
	// 标识
	private String borrowTrusteeFlag;
	// 团队经理
	private String loanTeamEmpcode;
	// 销售人员
	private String offendSalesName;
	// 客户经理
	private String loanApplySell;
	// 客户编号
	private String customerCode;
	// 咨询ID
	private String consultID;
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getConsultID() {
        return consultID;
    }
    public void setConsultID(String consultID) {
        this.consultID = consultID;
    }
    public String getContractVersion() {
		return contractVersion;
	}
	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCoborrowerName() {
        return coborrowerName;
    }
    public void setCoborrowerName(String coborrowerName) {
        this.coborrowerName = coborrowerName;
    }
    public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getContStoresId() {
		return contStoresId;
	}
	public void setContStoresId(String contStoresId) {
		this.contStoresId = contStoresId;
	}
	public String getContStoresName() {
        return contStoresName;
    }
    public void setContStoresName(String contStoresName) {
        this.contStoresName = contStoresName;
    }
    public String getBorrowProduct() {
		return borrowProduct;
	}
	public void setBorrowProduct(String borrowProduct) {
		this.borrowProduct = borrowProduct;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	
	public String getDictStatusCode() {
        return dictStatusCode;
    }
    public void setDictStatusCode(String dictStatusCode) {
        this.dictStatusCode = dictStatusCode;
    }
    public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getHisAmountMonths() {
		return hisAmountMonths;
	}
	public void setHisAmountMonths(Integer hisAmountMonths) {
		this.hisAmountMonths = hisAmountMonths;
	}
	public String getLoanIsPhone() {
		return loanIsPhone;
	}
	public void setLoanIsPhone(String loanIsPhone) {
		this.loanIsPhone = loanIsPhone;
	}
	
	public Date getCustomerIntoTime() {
		return customerIntoTime;
	}
	public void setCustomerIntoTime(Date customerIntoTime) {
		this.customerIntoTime = customerIntoTime;
	}
	public String getLoanRaise() {
		return loanRaise;
	}
	public void setLoanRaise(String loanRaise) {
		this.loanRaise = loanRaise;
	}
	public String getLoanIsUrgent() {
		return loanIsUrgent;
	}
	public void setLoanIsUrgent(String loanIsUrgent) {
		this.loanIsUrgent = loanIsUrgent;
	}
	public String getBorrowTrusteeFlag() {
		return borrowTrusteeFlag;
	}
	public void setBorrowTrusteeFlag(String borrowTrusteeFlag) {
		this.borrowTrusteeFlag = borrowTrusteeFlag;
	}
	public String getLoanTeamEmpcode() {
		return loanTeamEmpcode;
	}
	public void setLoanTeamEmpcode(String loanTeamEmpcode) {
		this.loanTeamEmpcode = loanTeamEmpcode;
	}
	public String getOffendSalesName() {
		return offendSalesName;
	}
	public void setOffendSalesName(String offendSalesName) {
		this.offendSalesName = offendSalesName;
	}
    public String getLoanApplySell() {
        return loanApplySell;
    }
    public void setLoanApplySell(String loanApplySell) {
        this.loanApplySell = loanApplySell;
    }
	
	
}
