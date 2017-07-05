/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsFeeRateUtils.java
 * @Create By 张灏
 * @Create In 2016年3月23日 上午9:39:00
 */
package com.creditharmony.loan.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取前期综合服务费率
 * @Class Name FeeRateUtils
 * @author 张灏
 * @Create In 2016年3月23日
 */
public class FeeRateUtils {
    private static final Map<String,Double> hashFeeRate = new HashMap<String,Double>();
    private static final double[] FEE_RATE_ARRAY = {2.56,2.46,2.33,1.89};
   // private static final double[] PRE_SERVICE_FEE_ARRAY_0={17.10,16.00,14.60,9.80};
    private static final double[] PRE_SERVICE_FEE_ARRAY_1={24.40,22.80,20.80,14.00};
    private static final double[] PRE_SERVICE_FEE_ARRAY_2={30.70,28.70,26.20,17.70};
    private static final double[] PRE_SERVICE_FEE_ARRAY_3={41.00,38.30,35.00,23.60};
    private static final double STAGE_SERVICE_FEE_RATE = 0.50;
    static{
        hashFeeRate.put("3-3.9", 8.30);
        hashFeeRate.put("6-3.2", 12.60);
        hashFeeRate.put("9-2.9", 16.00);
        hashFeeRate.put("12-2.72", 18.90);
    }
  
    /**
     *根据总费率、借款期限选择前期综合服务费率 
     *@author zhanghao
     *@Create In 2016年03月23日 
     *@param feeRate
     *@param loanLimit
     *@return double 
     * 
     */
    public static double getPreServiceFeeRate(double feeRate,Integer loanLimit) throws Exception{
        double preServiceFee = 0.00;
        if(loanLimit<=12){
            preServiceFee = hashFeeRate.get(loanLimit+"-"+feeRate);
        }else if(loanLimit>12 &&loanLimit<=18){
            for(int i=0;i<FEE_RATE_ARRAY.length;i++){
                if(feeRate==FEE_RATE_ARRAY[i]){
                    preServiceFee = PRE_SERVICE_FEE_ARRAY_1[i];
                    break;
                }
            } 
        }else if(loanLimit>18 &&loanLimit<=24){
            for(int i=0;i<FEE_RATE_ARRAY.length;i++){
                if(feeRate==FEE_RATE_ARRAY[i]){
                    preServiceFee = PRE_SERVICE_FEE_ARRAY_2[i];
                    break;
                }
            } 
        }else if(loanLimit>24 &&loanLimit<=36){
            for(int i=0;i<FEE_RATE_ARRAY.length;i++){
                if(feeRate==FEE_RATE_ARRAY[i]){
                    preServiceFee = PRE_SERVICE_FEE_ARRAY_3[i];
                    break;
                }
            } 
        }
        return preServiceFee;
    }
   
    /**
     *获取分期服务费率 
     *@author zhanghao
     *@Create In 2016年03月23日
     *@param  loanLimit
     *@return  Double
     * 
     */
    public static Double getStageServiceFeeRate(Integer loanLimit){
        Double stageServiceFeeRate = null;
        if(loanLimit<=12){
            stageServiceFeeRate = STAGE_SERVICE_FEE_RATE;
        }else if(loanLimit>12 &&loanLimit<=18){
            stageServiceFeeRate = STAGE_SERVICE_FEE_RATE;
        }else if(loanLimit>18 &&loanLimit<=24){
            stageServiceFeeRate = STAGE_SERVICE_FEE_RATE;
        }else if(loanLimit>24 &&loanLimit<=36){
            stageServiceFeeRate = STAGE_SERVICE_FEE_RATE; 
        }
        return stageServiceFeeRate;
    } 
    
    /**
     * 获取违约金系数
     * @author zhanghao
     * @Create In 2016年03月24日
     * @param curLimit
     * 
     */
    public static Double getDefaultFactor(Integer curLimit){
    	Double defaultFactor =null;
    	if(1<=curLimit && curLimit<=6){
    		defaultFactor = 2.50;
    	}else if(7<=curLimit && curLimit<=12){
    		defaultFactor = 1.50;
    	}else if(curLimit>12){
    		defaultFactor = 0.50;
    	}
    	return defaultFactor;
    }
}
