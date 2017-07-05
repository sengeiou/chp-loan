/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityGlBill.java
 * @Create By 张灏
 * @Create In 2015年12月30日 下午8:55:13
 */
package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name GlBill
 * @author 张灏
 * @Create In 2015年12月30日
 */
@SuppressWarnings("serial")
public class GlBill extends DataEntity<GlBill> {
    // 起始日
    private Integer signDay;
    // 账单日
    private Integer billDay;
   
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

  
}
