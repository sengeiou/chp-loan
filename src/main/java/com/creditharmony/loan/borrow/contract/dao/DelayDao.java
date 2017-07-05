package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.DelayEntity;

@LoanBatisDao
public interface DelayDao extends CrudDao<DelayEntity> {

	public List<DelayEntity> postPoneListExport(DelayEntity delayEntity);
}
