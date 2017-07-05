package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeStatisticsView;


/**
 * 催收统计VIEW Dao
 * @Class Name UrgeStatisticsViewDao
 * @author 张永生
 * @Create In 2016年4月25日
 */
@LoanBatisDao
public interface UrgeStatisticsViewDao extends CrudDao<UrgeStatisticsView>{
	
   /**
    * 通过合同编号查询催收统计
    * 2016年4月25日
    * By 张永生
    * @param contractCode
    * @return
    */
   public List<UrgeStatisticsView> findByContractCode(Map<String ,Object> params);
}
