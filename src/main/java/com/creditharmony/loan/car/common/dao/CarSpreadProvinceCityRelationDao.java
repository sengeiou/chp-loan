/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.common.dao
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carTotalRate.ex.CarSpreadProvinceCityRelationEx;
import com.creditharmony.loan.car.common.entity.CarSpreadProvinceCityRelation;
@LoanBatisDao
public interface CarSpreadProvinceCityRelationDao extends CrudDao<CarSpreadProvinceCityRelation>{

	/**
	 * 	根据rateId查找关联城市信息
	 * @param rateId 总费率ID
	 * @return 关联城市信息
	 */
	public List<CarSpreadProvinceCityRelationEx> getCarSpreadProvinceCityRelationByRateId(String rateId);
	
	/**
	 * 根据ID删除
	 * @param rateId
	 * @return
	 */
	public int deleteById(String rateId);
	
	/**
	 * 	查找关联城市信息
	 * @param carSpreadProvinceCityRelation  总费率
	 * @return 关联城市信息
	 */
	public  List<CarSpreadProvinceCityRelation> selectCarSpreadProvinceCityRelation(CarSpreadProvinceCityRelation carSpreadProvinceCityRelation);

}