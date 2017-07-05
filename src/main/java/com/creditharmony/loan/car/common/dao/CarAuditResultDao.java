package com.creditharmony.loan.car.common.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
@LoanBatisDao
public interface CarAuditResultDao  extends CrudDao<CarAuditResult>{
    CarAuditResult selectById(String id);
    
    /**
     * 根据借款编号获取 最终终审 通过的记录
     * 2016年2月18日
     * By 申诗阔
     * @param map
     * @return 最终 终审通过 的记录
     */
    public CarAuditResult getLastThroughRecord(Map<String, Object> map);
}