package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;

/**
 * 客户房产信息Dao
 * @Class Name LoanHouseDao
 * @author 张灏
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface LoanHouseDao extends CrudDao<LoanHouse> {
    
	/**
	 * 通过主键删除房产信息
	 * 2015年12月3日 
	 * By 张灏
	 * @param id
	 * @return int
	 */
    public int deleteByPrimaryKey(String id);
   
    /**
	 * 新增房产信息
	 * 2015年12月3日 
	 * By 张灏
	 * @param record
	 * @return int
	 */
    public int insert(LoanHouse record);

    /**
     * 选择插入房产信息
     * 2015年12月3日 
	 * By 张灏
     * @param record
     * @return int
     */
    public int insertSelective(LoanHouse record);

    /**
     * 通过主键查询房产信息
     * 2015年12月3日 
	 * By 张灏
     * @param id
     * @return LoanHouse
     */
    public LoanHouse selectByPrimaryKey(String id);

    /**
     * 通过主键选择更新房产信息
     * 2015年12月3日 
	 * By 张灏
     * @param record
     * @return int
     */
    public int updateByPrimaryKeySelective(LoanHouse record);

    /**
     * 通过主键更新房产信息
     * 2015年12月3日 
	 * By 张灏
     * @param record
     * @return int
     */
    public int updateByPrimaryKey(LoanHouse record);

    /**
     * 2015年12月3日 
	 * By 张灏
     * @param loanCode
     * @param loanCustomerType1
     * @return List<LoanHouse>
     */
    public List<LoanHouse> findListByLoanCode(String loanCode, String loanCustomerType1);
    
    /**
     *根据指定参数删除房产信息 
     *2016年1月28号
     *By 张灏 
     *@param param key id 
     *@return none
     */
    public void deleteByCondition(Map<String,Object> param);
}