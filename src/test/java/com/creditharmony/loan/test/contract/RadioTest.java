/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.contractRadioTest.java
 * @Create By 张灏
 * @Create In 2016年4月22日 上午10:14:56
 */
package com.creditharmony.loan.test.contract;

import java.util.List;

import org.junit.Test;

import com.creditharmony.loan.common.utils.FeeRateUtils;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name RadioTest
 * @author 张灏
 * @Create In 2016年4月22日
 */
public class RadioTest extends AbstractTestCase {
 
    
    @Test
    public void getPreServiceFeeRate(){
        double feeRate=4.90;
        Integer loanLimit=3;        
       try {
        Double PreServiceFeeRate = FeeRateUtils.getPreServiceFeeRate(feeRate, loanLimit);
        System.out.println(PreServiceFeeRate);
        List<String> arrayList = null;
        for(String s:arrayList){
            System.out.println(s);
        }
    } catch (Exception e) {
        
        e.printStackTrace();
    }
    }
}
