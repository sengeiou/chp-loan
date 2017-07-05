package com.creditharmony.loan.borrow.payback.dao;


import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.BankPlantPort;

/**
 * 银行平台接口
 * 
 * @Class Name BankPlantPortController
 * @author 翁私
 * @Create In 2016年4月20日
 */
@LoanBatisDao
public interface BankPlantPortDao extends CrudDao<BankPlantPort> {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table jk.t_jk_bank_plant_port
	 * @mbggenerated  Wed Apr 20 10:41:44 CST 2016
	 */
	public int deleteByPrimaryKey(BankPlantPort record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table jk.t_jk_bank_plant_port
	 * @mbggenerated  Wed Apr 20 10:41:44 CST 2016
	 */
	public int insert(BankPlantPort record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table jk.t_jk_bank_plant_port
	 * @mbggenerated  Wed Apr 20 10:41:44 CST 2016
	 */
	public int insertSelective(BankPlantPort record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table jk.t_jk_bank_plant_port
	 * @mbggenerated  Wed Apr 20 10:41:44 CST 2016
	 */
	public BankPlantPort selectByPrimaryKey(BankPlantPort record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table jk.t_jk_bank_plant_port
	 * @mbggenerated  Wed Apr 20 10:41:44 CST 2016
	 */
	public int updateByPrimaryKeySelective(BankPlantPort record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table jk.t_jk_bank_plant_port
	 * @mbggenerated  Wed Apr 20 10:41:44 CST 2016
	 */
	public int updateByPrimaryKey(BankPlantPort record);
	
	/**
	 * 分页查询
	 * @param pageBounds
	 * @param paramMap
	 * @return list
	 */
	public PageList<BankPlantPort> queryPage(PageBounds pageBounds,
			BankPlantPort record);

	
	public PageList<BankPlantPort> findPlantList(BankPlantPort record);
	
	/*
	 * 检索出除修改数据外的其他数据
	 */
	public BankPlantPort selectByPrimaryKeyNotIn(BankPlantPort record);
}