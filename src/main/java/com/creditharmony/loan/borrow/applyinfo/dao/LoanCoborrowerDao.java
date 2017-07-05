package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;

/**
 * 操作共借人信息的dao层
 * @Class Name LoanCoborrowerDao
 * @author zhanghao
 * @Create In 2015年12月25日
 */
@LoanBatisDao
public interface LoanCoborrowerDao extends CrudDao<LoanCoborrower> {
	
	/**
	 *添加记录 
	 *@author zhanghao
	 *@Create In 2015年12月25日
	 *@param record
	 *@return int
	 */
	public int insert(LoanCoborrower record);
	
	/**
	 *选择新增 共借人信息 
	 *@author zhanghao
     *@Create In 2015年12月25日
	 *@param record
	 *@return int
	 */
	public int insertSelective(LoanCoborrower record);
	
	/**
	 *通过LoanCode查询共借人 
	 *@author zhanghao
     *@Create In 2015年12月25日
	 *@param loanCode
	 *@return List<LoanCoborrower>
	 */
	public List<LoanCoborrower> selectByLoanCode(String loanCode);
	
	/**
	 * 通过loanCode查询自然人保证人信息，在签章的时候使用
	 * 2016年10月27日
	 * By 朱静越
	 * @param loanCode
	 * @return
	 */
	public LoanCoborrower getSecurityByLoanCode(String loanCode);

	/**
	 *通过关联ID查询共借人 
	 *@author zhanghao
     *@Create In 2015年12月25日
	 *@param rcustomerCoborrowerId
	 *@return List<LoanCoborrower>
	 */
	public List<LoanCoborrower> findAllByRcustomerCoborrowerId(String rcustomerCoborrowerId);

	/**
     *更新借款编号 
     *@author zhanghao
     *@Create In 2015年12月25日
     *@param param key oldLoanCode key newLoanCode
     *@return none 
     * 
     */
    public void updateLoanCode(Map<String,Object> param);
    
    /**
     *通过ID删除共借人人 
     *@author zhanghao
     *@Create In 2015年1月28日  
     *@param param key id
     *@return none 
     */
    public void deleteById(Map<String,Object> param);

    /**
     * 根据ID，更新无纸化相关信息
     * 2016年4月222日
     * By zhanghao
     * @param param
     * @return none
     */
    public void updatePaperlessMessage(Map<String,Object> param);

    /**
     * 查询共借人名称和身份证号
     */
    public Map<String,Object> selectCoboNameAndCertNum(String coborrowerId);
    /**
	 *通过LoanCode查询最优自然人
	 *@param loanCode
	 *@return List<LoanCoborrower>
	 */
	public List<LoanCoborrower> selectByLoanCodeOne(String loanCode);
    
   
}