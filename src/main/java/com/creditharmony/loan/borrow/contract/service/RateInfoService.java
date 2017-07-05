/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceRateInfoService.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午10:34:00
 */
package com.creditharmony.loan.borrow.contract.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.RateInfo;

/**
 * 利率Service层
 * @Class Name RateInfoService
 * @author 张灏
 * @Create In 2016年4月13日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class RateInfoService extends CoreManager<RateInfoDao, RateInfo>{
  
    /**
     *更新利率有效时间 
     *@author zhanghao
     *@Create In 2016年04月13日
     *@param rateInfo
     *@return none 
     * 
     */
    @Transactional(readOnly = false,value = "loanTransactionManager")
    public void updEffctiveDateById(RateInfo rateInfo){
            rateInfo.preUpdate();
            dao.updEffctiveDateById(rateInfo);
    }
}
