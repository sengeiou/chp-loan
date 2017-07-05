/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoGrantExportTLDao.java
 * @Create By 张灏
 * @Create In 2016年2月26日 上午11:22:42
 */
package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantExportTLEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 导出通联Dao
 * @Class Name GrantExportTLDao
 * @author 张灏
 * @Create In 2016年2月26日
 */
@LoanBatisDao
public interface GrantExportTLDao extends CrudDao<GrantExportTLEx>{

    /**
     *通过指定参数查询需要导出的放款数据 
     *@author zhanghao
     *@Create In 2016年02月25日
     *@param grtQryParam
     *@return List<GrantExportTLEx>
     *
     */
    public List<GrantExportTLEx> findGrantInfo(Map<String,Object> param);
    
    /**
     * 查找要进行拆分的list
     * 2016年5月10日
     * By 朱静越
     * @param param
     * @return
     */
    public List<PaybackApply> getSplitTl(Map<String, Object> param);
    
    /**
     * 删除拆分表中处理状态为【处理中导出】的单子
     * 2016年5月13日
     * By 朱静越
     */
    public void deleteSplitDeal(PaybackApply paybackApply);
}
