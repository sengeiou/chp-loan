package com.creditharmony.loan.car.common.dao;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.OperateRecord;

@LoanBatisDao
public interface OperateRecordMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(OperateRecord record);

    int insertSelective(OperateRecord record);

    OperateRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperateRecord record);

    int updateByPrimaryKey(OperateRecord record);
}