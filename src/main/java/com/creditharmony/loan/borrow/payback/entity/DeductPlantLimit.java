package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.creditharmony.core.persistence.DataEntity;
@SuppressWarnings("serial")
public class DeductPlantLimit extends DataEntity<DeductPlantLimit>{
    private String id;

    private String plantCode;

    private String plantName;

    private BigDecimal notEnoughProportion; // 余额不足比例

    private Integer notEnoughBase; // 余额不足基数

    private BigDecimal failureRate;  // 失败率

    private Integer failureBase;// 失败基数

    private Integer failureNumber; // 失败条数

    private String moneySymbol1;

    private String moneySymbol2;

    private BigDecimal deductMoney1;

    private BigDecimal deductMoney2;

    private String deductType1;

    private String deductType2;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;
    
    private Integer  baseNumber; // 基数
    
    private String orgId; // 门店id
    
    private List<String> orgIds;
    
    private String orgName; // 门店名称
    
    private Date  putDate; // 设置时间
    
    private String createDate;
    
    private Integer notEnoughNumber;
    
    private Integer deductNumber; // 划扣条数
    
    private Date statisticsDate;

    public Integer getNotEnoughNumber() {
		return notEnoughNumber;
	}

	public void setNotEnoughNumber(Integer notEnoughNumber) {
		this.notEnoughNumber = notEnoughNumber;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode == null ? null : plantCode.trim();
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName == null ? null : plantName.trim();
    }

  

    public String getMoneySymbol1() {
        return moneySymbol1;
    }

    public void setMoneySymbol1(String moneySymbol1) {
        this.moneySymbol1 = moneySymbol1 == null ? null : moneySymbol1.trim();
    }

    public String getMoneySymbol2() {
        return moneySymbol2;
    }

    public void setMoneySymbol2(String moneySymbol2) {
        this.moneySymbol2 = moneySymbol2 == null ? null : moneySymbol2.trim();
    }


    public String getDeductType1() {
        return deductType1;
    }

    public void setDeductType1(String deductType1) {
        this.deductType1 = deductType1 == null ? null : deductType1.trim();
    }

    public String getDeductType2() {
        return deductType2;
    }

    public void setDeductType2(String deductType2) {
        this.deductType2 = deductType2 == null ? null : deductType2.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

	public BigDecimal getNotEnoughProportion() {
		return notEnoughProportion;
	}

	public Integer getNotEnoughBase() {
		return notEnoughBase;
	}

	public BigDecimal getFailureRate() {
		return failureRate;
	}

	public Integer getFailureBase() {
		return failureBase;
	}

	public Integer getFailureNumber() {
		return failureNumber;
	}

	public BigDecimal getDeductMoney1() {
		return deductMoney1;
	}

	public BigDecimal getDeductMoney2() {
		return deductMoney2;
	}

	public void setNotEnoughProportion(BigDecimal notEnoughProportion) {
		this.notEnoughProportion = notEnoughProportion;
	}

	public void setNotEnoughBase(Integer notEnoughBase) {
		this.notEnoughBase = notEnoughBase;
	}

	public void setFailureRate(BigDecimal failureRate) {
		this.failureRate = failureRate;
	}

	public void setFailureBase(Integer failureBase) {
		this.failureBase = failureBase;
	}

	public void setFailureNumber(Integer failureNumber) {
		this.failureNumber = failureNumber;
	}

	public void setDeductMoney1(BigDecimal deductMoney1) {
		this.deductMoney1 = deductMoney1;
	}

	public void setDeductMoney2(BigDecimal deductMoney2) {
		this.deductMoney2 = deductMoney2;
	}

	public Integer getBaseNumber() {
		return baseNumber;
	}

	public void setBaseNumber(Integer baseNumber) {
		this.baseNumber = baseNumber;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getPutDate() {
		return putDate;
	}

	public void setPutDate(Date putDate) {
		this.putDate = putDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<String> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getDeductNumber() {
		return deductNumber;
	}

	public void setDeductNumber(Integer deductNumber) {
		this.deductNumber = deductNumber;
	}

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

    
    
}