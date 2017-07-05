package com.creditharmony.loan.dict.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.dict.entity.DictInfo;

/**
 * 字典DAO接口
 * @Class Name DictInfoDao
 * @author 陈伟东
 * @Create In 2015年12月28日
 */
@LoanBatisDao
public interface DictInfoDao extends CrudDao<DictInfo> {

	public List<DictInfo> getByType (DictInfo dictInfo);
}
