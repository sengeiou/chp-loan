package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;

/**
 * 操作配偶信息
 * @Class Name LoanMateDao
 * @author 张灏
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface LoanMateDao extends CrudDao<LoanMate> {
	
	/**
	 * 通过主键删除配偶信息
	 * 2015年12月19日
	 * By 张灏
	 * @param param
	 * @return int
	 */
    public int deleteById(Map<String,Object> param);

    /**
     * 新增查询配偶信息表
     * 2015年12月19日
	 * By 张灏
     * @param record
     * @return int 
     */
    public int insert(LoanMate record);

    /**
     * 选择新增查询配偶信息表
     * 2015年12月19日
	 * By 张灏
     * @param record
     * @return int 
     */
    public int insertSelective(LoanMate record);

    /**
     * 通过ID查询配偶信息表
     * 2015年12月19日
	 * By 张灏
     * @param id
     * @return LoanMate 
     */
    public LoanMate selectByPrimaryKey(String id);

    /**
     * 通过ID选择更新配偶信息表
     * 2015年12月19日
	 * By 张灏
     * @param  record
     * @return int 
     */
    public int updateByPrimaryKeySelective(LoanMate record);

    /**
     * 通过ID更新配偶信息表
     * 2015年12月19日
	 * By 张灏
     * @param  record
     * @return int 
     */
    public int updateByPrimaryKey(LoanMate record);
	
    /**
     * 通过借款编号查询配偶信息
     * 2015年12月19日
	 * By 张灏
     * @param loanCode
     * @param loanCustomerType1
     * @return
     */
    public LoanMate selectByLoanCode(String loanCode, String loanCustomerType1);
    
    /**
     *通过指定的关联ID，关联类型删除配偶信息 
     *2016年07月01日
     *@param param rid 关联ID, loanCustomterType 客户类型 
     *@return none 
     */
    public void deleteByRId(Map<String,Object> param);
}