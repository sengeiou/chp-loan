package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;

/**
 * 职业信息Dao
 * @Class Name LoanCompanyDao
 * @author 张平
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface LoanCompanyDao extends CrudDao<LoanCompany> {
    
	/**
	 * 通过主键删除主借人的公司信息
	 * 2015年12月3日 
	 * By 张灏
	 * @param id
	 * @return int
	 */
    public int deleteByPrimaryKey(String id);
    
    /**
     * 通过关联主键删除公司信息
     * 2015年12月3日 
     * By 张灏
     * @param param rid
     * @return int
     */
    public int deleteByRid(Map<String,Object> param);
    
    /**
	 * 新增主借人公司信息
	 * 2015年12月3日 
	 * By 张灏
	 * @param record
	 * @return int
	 */
    public int insert(LoanCompany record);
    
    /**
     * 选择新增主借人公司信息
     * 2015年12月3日 
     * By 张灏
     * @param record
     * @return int
     */
    public int insertSelective(LoanCompany record);
    
    /**
     * 通过id查询主借人公司信息
     * 2015年12月3日 
     * By 张灏
     * @param id
     * @return LoanCompany
     */
    public LoanCompany selectByPrimaryKey(String id);
    
    /**
     * 通过主键选择更新主借人公司信息
     * 2015年12月3日 
     * By 张灏
     * @param record
     * @return int
     */
    public int updateByPrimaryKeySelective(LoanCompany record);
    
    /**
     * 通过主键更新主借人公司信息
     * 2015年12月3日 
     * By 张灏
     * @param record
     * @return int
     */
    public int updateByPrimaryKey(LoanCompany record);
    
    /**
     * 通过LoanCode查询主借人公司信息
     * 2015年12月3日 
     * By 张灏
     * @param loanCode 
     * @param loanCustomerType2
     * @return LoanCompany
     */
    public LoanCompany selectByLoanCode(String loanCode, String loanCustomerType2);
	
    /**
     * 通过指定参数查询公司信息
     * 2015年12月3日 
     * By 张灏
     * @param param
     * @return LoanCompany
     */
    public LoanCompany findByParam(Map<String,Object> param);
}