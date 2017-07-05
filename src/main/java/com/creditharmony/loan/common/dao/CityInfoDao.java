package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.CityInfo;

/**
 * 省市区管理-Dao
 * 
 * @Class Name CityDao
 * @author 汇金
 * @Create In 2015年12月4日
 */
@LoanBatisDao
public interface CityInfoDao extends CrudDao<CityInfo> {

	public List<CityInfo> findByParams(Map<String,Object> params);
	
	/**
	 * 检索省市区及下辖地区
	 * 
	 * @param params
	 * @return List<City>
	 */
	public List<CityInfo> selCitySub(CityInfo key);
	/**
	 *通过areaCode检索具体的地区信息 
	 * 
	 *@param key areaCode 
	 *@return CityInfo 
	 */
	public CityInfo findOneArea(Map<String,Object> params);

	/**
	 * 检索省市区及下辖地区
	 * 
	 * @param params
	 * @return List<City>
	 */
	public List<CityInfo> findByParamsCmb(Map<String, Object> params);
	
	/**
	 * 获取具体省市
	 * 2016年3月6日
	 * By 王彬彬
	 * @param params 查询条件
	 * @return
	 */
	public CityInfo findAreaName(Map<String,Object> params);
}
