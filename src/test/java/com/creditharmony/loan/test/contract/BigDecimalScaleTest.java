/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.contractBigDecimalScaleTest.java
 * @Create By 张灏
 * @Create In 2016年4月6日 下午6:32:14
 */
package com.creditharmony.loan.test.contract;

import java.math.BigDecimal;

/**
 * @Class Name BigDecimalScaleTest
 * @author 张灏
 * @Create In 2016年4月6日
 */
public class BigDecimalScaleTest {

    /**
     * 2016年4月6日
     * By 张灏
     * @param args 
     */
    public static void main(String[] args) {
       BigDecimal b1 = new BigDecimal("11.000");
       BigDecimal b2 = new BigDecimal("1.083001");
       System.out.println(b1.setScale(3,BigDecimal.ROUND_UNNECESSARY).floatValue());
       System.out.println(b2.setScale(4,BigDecimal.ROUND_HALF_UP).floatValue());
       System.out.println(b2.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

}
