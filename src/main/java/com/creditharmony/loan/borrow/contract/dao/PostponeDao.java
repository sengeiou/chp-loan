package com.creditharmony.loan.borrow.contract.dao;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.PostponeEntity;
@LoanBatisDao
public interface PostponeDao {
	
    int deleteByPrimaryKey(String id);

    int insert(PostponeEntity record);

    int insertSelective(PostponeEntity record);

    PostponeEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostponeEntity record);

    int updateByPrimaryKey(PostponeEntity record);
}