/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.daoPaybackMonthDao.java
 * @Create By 张灏
 * @Create In 2015年12月29日 上午9:46:27
 */
package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;

/**
 * 期供表操作Dao
 * @Class Name PaybackMonthDao
 * @author 张灏
 * @Create In 2015年12月29日
 */
@LoanBatisDao
public interface PaybackMonthDao extends CrudDao<PaybackMonth> {

    /**
     *通过contractCode查询期供 
     *@author zhanghao
     *@Create In 2016年03月11日
     *@param key contractCode
     *@return List<PaybackMonth> 
     */
    public List<PaybackMonth> findByContractCode(Map<String,Object> param);
   
    /**
     *删除期供 
     *@author zhanghao
     *@Create In 2016年03月11日
     *@param key contractCode
     *@return 
     */
    public void deleteByContractCode(Map<String,Object> param);  
    /**
     *新增期供 
     *@author zhanghao
     *@Create In 2016年03月11日
     *@param PaybackMonth
     *@return 
     */
    public void insertPaybackMonth(PaybackMonth paybackMonth); 
    
    /**
     *通过contractCode查询期供 
     *@author zhanghao
     *@Create In 2016年03月11日
     *@param key contractCode
     *@return List<PaybackMonth> 
     */
    public PaybackMonth getPaybackMonthByContractCode(Map<String,Object> param);
    /**
     *通过contractCode最后一期期供状态 
     *@author zhanghao
     *@Create In 2016年10月10日
     *@param key contractCode 
     *order by months desc limit 1
     *@return PaybackMonth
     */
    public PaybackMonth findLastPaybackInfo(Map<String,Object> param);
    
    /**
     * 结清申请时修改期供状态
     * @author 于飞
     * @Create 2017年4月26日
     * @param month
     */
    public void updateStatusByContractCode(PaybackMonth month);
}
