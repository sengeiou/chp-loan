package com.creditharmony.loan.borrow.payback.dao;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PlantSkipOrder;

@LoanBatisDao
public interface PlantSkipOrderDao extends CrudDao<PlantSkipOrder>{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jk.t_jk_plant_skip_order
     *
     * @mbggenerated Wed Apr 20 19:34:53 CST 2016
     */
    int deleteByPrimaryKey(PlantSkipOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jk.t_jk_plant_skip_order
     *
     * @mbggenerated Wed Apr 20 19:34:53 CST 2016
     */
    int insert(PlantSkipOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jk.t_jk_plant_skip_order
     *
     * @mbggenerated Wed Apr 20 19:34:53 CST 2016
     */
    int insertSelective(PlantSkipOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jk.t_jk_plant_skip_order
     *
     * @mbggenerated Wed Apr 20 19:34:53 CST 2016
     */
    PlantSkipOrder selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jk.t_jk_plant_skip_order
     *
     * @mbggenerated Wed Apr 20 19:34:53 CST 2016
     */
    int updateByPrimaryKeySelective(PlantSkipOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jk.t_jk_plant_skip_order
     *
     * @mbggenerated Wed Apr 20 19:34:53 CST 2016
     */
    int updateByPrimaryKey(PlantSkipOrder record);


	/**
	 * 分页查询
	 * @param pageBounds
	 * @param paramMap
	 * @return list
	 */
	PageList<PlantSkipOrder> queryPage(PageBounds pageBounds,
			PlantSkipOrder record);

	/**
	 * 查询平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return list
	 */
	PageList<PlantSkipOrder> findSkipList(PlantSkipOrder record);
}