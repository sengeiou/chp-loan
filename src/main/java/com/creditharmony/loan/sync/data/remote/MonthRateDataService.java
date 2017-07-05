/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.webRateInfoController.java
 * @Create By 张灏
 * @Create In 2016年4月13日 下午1:38:44
 */
package com.creditharmony.loan.sync.data.remote;

import com.creditharmony.core.common.entity.AuditResult;

/**
 * 产品费率
 * @Class Name RateInfoController
 * @author 申阿伟
 * @Create In 2017年5月6日
 */
public interface MonthRateDataService{
	
    /**
     * 返回给汇诚期供金额以及总费率
     * 2017年5月6日
     * By 申阿伟
     * @param auditResult
     * @return List<ProductRate>
     */
	 public AuditResult getProductRateList(AuditResult auditResult);
   
}
