package com.creditharmony.loan.yunwei.query.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.yunwei.query.dao.QueryDataBySqlDao;
import com.creditharmony.loan.yunwei.query.entity.QueryObj;

@Service("queryDataBySqlService")
public class QueryDataBySqlService extends CoreManager<QueryDataBySqlDao, QueryObj> {
	
	@Autowired
	private QueryDataBySqlDao queryDataBySqlDao;
	
	/**
	 * 根据sql查询需要的数据
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryDataBySql(Map<String,Object> params) {
		return queryDataBySqlDao.queryDataBySql(params);
	}
	
	/**
	 * 查询记录数
	 * @param sqlStr
	 * @return
	 */
	public long queryDataCount(String sqlStr) {
		return queryDataBySqlDao.queryDataCount(sqlStr);
	}
	
}
