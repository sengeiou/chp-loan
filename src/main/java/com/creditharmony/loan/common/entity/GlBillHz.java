/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityGlBill.java
 * @Create By 张灏
 * @Create In 2015年12月30日 下午8:55:13
 */
package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name GlBillHz
 * @author 申阿伟
 * @Create In 2017年03月31日
 */
@SuppressWarnings("serial")
public class GlBillHz extends DataEntity<GlBillHz> {
    // 起始日
    private Integer signDay;
    // 账单日
    private Integer billDay;
    //类型
    private String type;
   
    public Integer getSignDay() {
        return signDay;
    }

    public void setSignDay(Integer signDay) {
        this.signDay = signDay;
    }

    public Integer getBillDay() {
        return billDay;
    }

    public void setBillDay(Integer billDay) {
        this.billDay = billDay;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
 
  
}
