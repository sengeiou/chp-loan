/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoGrantExportDao.java
 * @Create By 张灏
 * @Create In 2016年2月25日 下午4:12:11
 */
package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantExportZJEx;

/**
 * 导出Dao
 * @Class Name GrantExportDao
 * @author 张灏
 * @Create In 2016年2月25日
 */
@LoanBatisDao
public interface GrantExportZJDao  extends CrudDao<GrantExportZJEx>{
  
    /**
     *通过指定参数查询需要导出的放款数据 
     *@author zhanghao
     *@Create In 2016年02月25日
     *@param grtQryParam
     *@return List<GrantExportZJEx>
     *
     */
    public List<GrantExportZJEx> findGrantInfo(Map<String,Object> param);
}
