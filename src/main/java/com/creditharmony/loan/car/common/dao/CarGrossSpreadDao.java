
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
import com.creditharmony.loan.car.carTotalRate.ex.CarGrossSpreadEx;
import com.creditharmony.loan.car.common.entity.CarGrossSpread;

/**
 *车借_总费率dao层 
 *@create In 2016年1月21日
 *@author 任亮杰
 */

@LoanBatisDao
public interface CarGrossSpreadDao extends CrudDao<CarGrossSpread>{

    CarGrossSpread selectByRateId(String rateId);
    
    /**
     * 		查询总费率列表
     * @param carGrossSpread
     * @return
     */
    public List<CarGrossSpread> selectCarGrossSpreadList(CarGrossSpread carGrossSpread);
   
    /**
     * 根据借款期限、产品类型查询总费率
     * 2016年2月25日
     * By 陈伟东
     * @param carGrossSpread
     * @return
     */
    public List<CarGrossSpread> selectCarGross(CarGrossSpread carGrossSpread);
	
	/**
	 * 		新增一条总费率
	 * @param carGrossSpread
	 */
    public void insertCarGrossSpread(CarGrossSpread carGrossSpread);
    
    /**
     * 		查看一条总费率
     * @param rateId
     * @return
     */
    public CarGrossSpread findByRateId(String rateId);
    
   /**
    * 		修改一条总费率
    * @param carGrossSpread
    */
    public void updateCarGrossSpread(CarGrossSpread carGrossSpread);
    
   /**
    * 		启用 一条或者多条车借总费率
    * @param id :在service层中遍历过来
    */
    public void updateDictInitiate1(String id);
    
    /**
     * 		停用  一条或者多条车借总费率
     * @param id :在service层中遍历过来
     */
    public void updateDictInitiate0(String id);

	/**
	 * 		进入分配城市页面，带回参数
	 * @return
	 */
    public CarGrossSpread showSpreadProvinceCity(String rateId);
    
    /**
     * 查询复核条件的 CarGrossSpread 数量
     * @param carGrossSpread 总费率查询
     * @return 复核条件的 CarGrossSpread 数量
     */
    public int findCarGrossSpreadCount(CarGrossSpread carGrossSpread);


    /**
	 * 	 查找关联城市信息总费率信息
	 * @param grossSpreadEx 总费率 
	 * @return 关联城市信息总费率信息
	 */
	public CarGrossSpread getCarGrossSpreadByCarGrossSpreadEx(CarGrossSpreadEx grossSpreadEx);
}