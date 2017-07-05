package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 客户居住情况信息
 * 
 * @Class Name CustomerLivings
 * @author 张平
 * @Create In 2015年12月3日
 */
public class CustomerLivings extends DataEntity<CustomerLivings> {

	private static final long serialVersionUID = -6094924060583668195L;

	private String loanCode; // 借款编码

	private String rcustomerCoborrowerId; // 关联ID

	private String loanCustomerType; // 关联类型(主借人/共借人)

	private String customerFirtArriveYear; // 初来本市年份

	private String customerResidential; // 住址

	private Date customerFirtLivingDay; // 起始居住时间

	private String customerFamilySupport; // 供养亲属人数

	private String customerHousingSituation; // 房产状况

	private BigDecimal customerMonthRepayAmount;  //月还款额
	
    private BigDecimal customerRentMonth;  // 月租金
    
	private String customerHouseHoldProperty; // 住房性质
	
	private String customerHouseHoldPropertyNewOther; //住宅类别备注

	private String customerHaveLive; // 与谁居住

	private Integer customerLivingNum; // 居住人数

	private String dictHouseType; // 住宅类型(关联字典表)

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode == null ? null : loanCode.trim();
	}

	

	public String getRcustomerCoborrowerId() {
        return rcustomerCoborrowerId;
    }

    public void setRcustomerCoborrowerId(String rcustomerCoborrowerId) {
        this.rcustomerCoborrowerId = rcustomerCoborrowerId;
    }

  	public String getLoanCustomerType() {
		return loanCustomerType;
	}

	public void setLoanCustomerType(String loanCustomerType) {
		this.loanCustomerType = loanCustomerType;
	}

	public String getCustomerFirtArriveYear() {
		return customerFirtArriveYear;
	}

	public void setCustomerFirtArriveYear(String customerFirtArriveYear) {
		this.customerFirtArriveYear = customerFirtArriveYear;
	}

	public String getCustomerResidential() {
		return customerResidential;
	}

	public void setCustomerResidential(String customerResidential) {
		this.customerResidential = customerResidential == null ? null
				: customerResidential.trim();
	}

	public String getCustomerFamilySupport() {
		return customerFamilySupport;
	}

	public void setCustomerFamilySupport(String customerFamilySupport) {
		this.customerFamilySupport = customerFamilySupport == null ? null
				: customerFamilySupport.trim();
	}

	public String getCustomerHousingSituation() {
		return customerHousingSituation;
	}

	public void setCustomerHousingSituation(String customerHousingSituation) {
		this.customerHousingSituation = customerHousingSituation == null ? null
				: customerHousingSituation.trim();
	}

	public String getCustomerHouseHoldProperty() {
		return customerHouseHoldProperty;
	}

	public Date getCustomerFirtLivingDay() {
        return customerFirtLivingDay;
    }

	public String getCustomerHouseHoldPropertyNewOther() {
		return customerHouseHoldPropertyNewOther;
	}

	public void setCustomerHouseHoldPropertyNewOther(
			String customerHouseHoldPropertyNewOther) {
		this.customerHouseHoldPropertyNewOther = customerHouseHoldPropertyNewOther;
	}

	public void setCustomerFirtLivingDay(Date customerFirtLivingDay) {
        this.customerFirtLivingDay = customerFirtLivingDay;
    }

    public BigDecimal getCustomerMonthRepayAmount() {
        return customerMonthRepayAmount;
    }

    public void setCustomerMonthRepayAmount(BigDecimal customerMonthRepayAmount) {
        this.customerMonthRepayAmount = customerMonthRepayAmount;
    }

    public BigDecimal getCustomerRentMonth() {
        return customerRentMonth;
    }

    public void setCustomerRentMonth(BigDecimal customerRentMonth) {
        this.customerRentMonth = customerRentMonth;
    }

    public void setCustomerHouseHoldProperty(String customerHouseHoldProperty) {
		this.customerHouseHoldProperty = customerHouseHoldProperty == null ? null
				: customerHouseHoldProperty.trim();
	}

	public String getCustomerHaveLive() {
		return customerHaveLive;
	}

	public void setCustomerHaveLive(String customerHaveLive) {
		this.customerHaveLive = customerHaveLive == null ? null
				: customerHaveLive.trim();
	}

	public Integer getCustomerLivingNum() {
		return customerLivingNum;
	}

	public void setCustomerLivingNum(Integer customerLivingNum) {
		this.customerLivingNum = customerLivingNum;
	}

	public String getDictHouseType() {
		return dictHouseType;
	}

	public void setDictHouseType(String dictHouseType) {
		this.dictHouseType = dictHouseType == null ? null : dictHouseType
				.trim();
	}
}