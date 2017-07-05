/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.daoRateInfoDao.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午9:56:01
 */
package com.creditharmony.loan.borrow.contract.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.RateInfo;

/**
 * 利率操作dao层
 * @Class Name RateInfoDao
 * @author 张灏
 * @Create In 2016年4月13日
 */
@LoanBatisDao
public interface RateInfoDao extends CrudDao<RateInfo> {
    
    /**
     *更新利率有效时间 
     *@author zhanghao
     *@Create In 2016年04月13日
     *@param rateInfo
     *@return none 
     * 
     */
    public void updEffctiveDateById(RateInfo rateInfo);
    
    public List<Map<String,String>> getRiskLevel(String loanCode);
    
    public RateInfo findRateInfoByMonths(BigDecimal loanMonths);
}
