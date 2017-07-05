package com.creditharmony.loan.borrow.applyinfo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 客户房产信息
 * @Class Name LoanHouse
 * @author 张平
 * @Create In 2015年12月3日
 */
public class LoanHouse extends DataEntity<LoanHouse> {

	private static final long serialVersionUID = 4803015898775983943L;

	private String rcustomerCoborrowerId; // 关联ID

	private String loanCustomterType; // 关联类型(主借人/共借人)

	private String loanCode; // 借款编码

	private String houseProvince; // 房产所在省
	
	private String houseProvinceName; // 省名 

	private String houseCity; // 房产所在市
	
	private String houseCityName; // 市名

	private String houseArea; // 房产所在区
	
	private String houseAreaName; // 区名

	private String houseAddress; // 房产地址
	
	private Date houseCreateDay; //建筑日期

	private String houseBuyway; // 购买方式(0，全额，1按揭)
	
	private String houseBuywayLabel; // 购买方式名称

	private Date houseBuyday; // 购买日期
	
	private String housePropertyRight;  //产权人
	
	private String dictHouseCommon;  //房屋共有情况
	
	private String housePropertyRelation;//与共有人关系
	
	private String housePledgeMark;  //抵押情况
	
	private String housePledgeFlag;  //抵押标识
	
	private String housePledgeFlagLabel; // 是否抵押(名称)

	private Date propertyGetDay; // 产权取得日期

	private BigDecimal houseBuilingArea; // 房屋面积

	private String dictHouseBank; // 按揭

	private BigDecimal houseLoanAmount; // 按揭总金额

	private BigDecimal houseLoanYear; // 按揭年限

	private BigDecimal houseAmount; // 房屋购买价格

	private BigDecimal houseLessAmount; // 房屋贷款尚欠余额

	private BigDecimal houseMonthRepayAmount; // 月还款额

	private String houseBaseInfo; // 月还款额

	private String dictHouseType; // 房屋规划类型(商住)
	
	private String dictHouseTypeRemark; // 房产类型备注
	
    public String getHouseBuywayLabel() {
		return houseBuywayLabel;
	}

	public void setHouseBuywayLabel(String houseBuywayLabel) {
		this.houseBuywayLabel = houseBuywayLabel;
	}

	public String getHousePledgeFlagLabel() {
		return housePledgeFlagLabel;
	}

	public void setHousePledgeFlagLabel(String housePledgeFlagLabel) {
		this.housePledgeFlagLabel = housePledgeFlagLabel;
	}

	public String getHouseProvinceName() {
		return houseProvinceName;
	}

	public void setHouseProvinceName(String houseProvinceName) {
		this.houseProvinceName = houseProvinceName;
	}

	public String getHouseCityName() {
		return houseCityName;
	}

	public void setHouseCityName(String houseCityName) {
		this.houseCityName = houseCityName;
	}

	public String getHouseAreaName() {
		return houseAreaName;
	}

	public void setHouseAreaName(String houseAreaName) {
		this.houseAreaName = houseAreaName;
	}

	public String getRcustomerCoborrowerId() {
        return rcustomerCoborrowerId;
    }

    public void setRcustomerCoborrowerId(String rcustomerCoborrowerId) {
        this.rcustomerCoborrowerId = rcustomerCoborrowerId;
    }

    public String getLoanCustomterType() {
        return loanCustomterType;
    }

    public void setLoanCustomterType(String loanCustomterType) {
        this.loanCustomterType = loanCustomterType;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getHouseProvince() {
        return houseProvince;
    }

    public void setHouseProvince(String houseProvince) {
        this.houseProvince = houseProvince;
    }

    public String getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(String houseCity) {
        this.houseCity = houseCity;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public Date getHouseCreateDay() {
        return houseCreateDay;
    }

    public void setHouseCreateDay(Date houseCreateDay) {
        this.houseCreateDay = houseCreateDay;
    }

    public String getHouseBuyway() {
        return houseBuyway;
    }

    public void setHouseBuyway(String houseBuyway) {
        this.houseBuyway = houseBuyway;
    }

    public Date getHouseBuyday() {
        return houseBuyday;
    }

    public void setHouseBuyday(Date houseBuyday) {
        this.houseBuyday = houseBuyday;
    }

    public String getHousePropertyRight() {
        return housePropertyRight;
    }

    public void setHousePropertyRight(String housePropertyRight) {
        this.housePropertyRight = housePropertyRight;
    }

    public String getDictHouseCommon() {
        return dictHouseCommon;
    }

    public void setDictHouseCommon(String dictHouseCommon) {
        this.dictHouseCommon = dictHouseCommon;
    }

    public String getHousePropertyRelation() {
        return housePropertyRelation;
    }

    public void setHousePropertyRelation(String housePropertyRelation) {
        this.housePropertyRelation = housePropertyRelation;
    }

    public String getHousePledgeMark() {
        return housePledgeMark;
    }

    public void setHousePledgeMark(String housePledgeMark) {
        this.housePledgeMark = housePledgeMark;
    }

    public String getHousePledgeFlag() {
        return housePledgeFlag;
    }

    public void setHousePledgeFlag(String housePledgeFlag) {
        this.housePledgeFlag = housePledgeFlag;
    }

    public Date getPropertyGetDay() {
        return propertyGetDay;
    }

    public void setPropertyGetDay(Date propertyGetDay) {
        this.propertyGetDay = propertyGetDay;
    }

    public BigDecimal getHouseBuilingArea() {
        return houseBuilingArea;
    }

    public void setHouseBuilingArea(BigDecimal houseBuilingArea) {
        this.houseBuilingArea = houseBuilingArea;
    }

    public String getDictHouseBank() {
        return dictHouseBank;
    }

    public void setDictHouseBank(String dictHouseBank) {
        this.dictHouseBank = dictHouseBank;
    }

    public BigDecimal getHouseLoanAmount() {
        return houseLoanAmount;
    }

    public void setHouseLoanAmount(BigDecimal houseLoanAmount) {
        this.houseLoanAmount = houseLoanAmount;
    }

    public BigDecimal getHouseLoanYear() {
		return houseLoanYear;
	}

	public void setHouseLoanYear(BigDecimal houseLoanYear) {
		this.houseLoanYear = houseLoanYear;
	}

	public BigDecimal getHouseAmount() {
        return houseAmount;
    }

    public void setHouseAmount(BigDecimal houseAmount) {
        this.houseAmount = houseAmount;
    }

    public BigDecimal getHouseLessAmount() {
        return houseLessAmount;
    }

    public void setHouseLessAmount(BigDecimal houseLessAmount) {
        this.houseLessAmount = houseLessAmount;
    }

    public BigDecimal getHouseMonthRepayAmount() {
        return houseMonthRepayAmount;
    }

    public void setHouseMonthRepayAmount(BigDecimal houseMonthRepayAmount) {
        this.houseMonthRepayAmount = houseMonthRepayAmount;
    }

    public String getHouseBaseInfo() {
        return houseBaseInfo;
    }

    public void setHouseBaseInfo(String houseBaseInfo) {
        this.houseBaseInfo = houseBaseInfo;
    }

    public String getDictHouseType() {
        return dictHouseType;
    }

    public void setDictHouseType(String dictHouseType) {
        this.dictHouseType = dictHouseType;
    }

	public String getDictHouseTypeRemark() {
		return dictHouseTypeRemark;
	}

	public void setDictHouseTypeRemark(String dictHouseTypeRemark) {
		this.dictHouseTypeRemark = dictHouseTypeRemark;
	}
}