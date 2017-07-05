package com.creditharmony.loan.car.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.entity.CityInfo;

/**
 * 省市县服务类
 * @Class Name CityInfoService
 * @author 王彬彬
 * @Create In 2015年12月4日
 */
@Service
public class CarCityInfoService extends CoreManager<CityInfoDao, CityInfo> {
	
	/**
	 * 获取城市
	 * 2016年2月22日
	 * By 王彬彬
	 * @param provinceId 省份ID
	 * @return 城市信息
	 */
	public List<CityInfo> findCity(String provinceId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", provinceId);
		return dao.findByParams(params);
	}
	
	/**
	 * 获取省份
	 * 2016年2月22日
	 * By 王彬彬
	 * @return 省份列表
	 */
	public List<CityInfo> findProvince(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", CityInfoConstant.ROOT_ID);
		return dao.findByParams(params);
	}
	
	/**
	 * 获取区县
	 * 2016年2月22日
	 * By 王彬彬
	 * @param cityId 城市ID
	 * @return 城市列表
	 */
	public List<CityInfo> findDistrict(String cityId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", cityId);
		return dao.findByParams(params);
	}

	/**
	 * 获取省份(招商银行)
	 * 2016年2月22日
	 * By 管洪昌
	 * @return 省份列表
	 */
	public List<CityInfo> findProvinceCmb() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", CityInfoConstant.ROOT_ID);
		return dao.findByParamsCmb(params);
	}

	/**
	 * 获取城市
	 * 2016年2月22日
	 * By 王彬彬
	 * @param provinceId 省份ID
	 * @return 城市信息
	 */
	public List<CityInfo> findCityCmb(String provinceId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", provinceId);
		return dao.findByParamsCmb(params);
	}
	
	/**
	 * 银行所在名称获取
	 * 2016年3月6日
	 * By 王彬彬
	 * @param areaId
	 * @return
	 */
	public  CityInfo  findAreaName(String areaId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("areaCode", areaId);
		return dao.findAreaName(params);
	}
}
