package com.creditharmony.loan.yunwei.query.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.yunwei.query.entity.QueryObj;

@LoanBatisDao
public interface QueryDataBySqlDao extends CrudDao<QueryObj>  {
	
	/**
	 * 根据sql查询需要的数据
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryDataBySql(Map<String,Object> params);
	
	/**
	 * 查询记录数
	 * @param sqlStr
	 * @return
	 */
	public long queryDataCount(String sqlStr);
	
}
