package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;

/**
 * 联系人信息Dao
 * @Class Name ContactDao
 * @author 张平
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface ContactDao extends CrudDao<Contact> {
    
	/**
	 * 通过主键删除联系人信息
	 * 2015年12月3日
	 * By 张灏
	 * @param id
	 * @return int
	 */
    public int deleteByPrimaryKey(String id);
   
    /**
	 * 新增联系人信息
	 * 2015年12月3日
	 * By 张灏
	 * @param record
	 * @return int
	 */
    public int insert(Contact record);

    /**
     * 选择新增联系人信息
     * 2015年12月3日
	 * By 张灏
     * @param record
     * @return int
     */
    public int insertSelective(Contact record);

    /**
     * 通过主键查询联系人信息
     * 2015年12月3日
	 * By 张灏
     * @param id
     * @return Contact
     */
    public Contact selectByPrimaryKey(String id);

    /**
     * 通过主键选择更新联系人信息
     * 2015年12月3日
	 * By 张灏
     * @param record
     * @return int
     */
    public int updateByPrimaryKeySelective(Contact record);

    /**
     * 通过主键更新联系人信息
     * 2015年12月3日
	 * By 张灏
     * @param record
     * @return int
     */
    public int updateByPrimaryKey(Contact record);

    /**
     * 通过借款编号跟客户类型查询联系人
     * 2015年12月3日
	 * By 张灏
     * @param loanCode
     * @param loanCustomerType0
     * @return List<Contact>
     */
    public List<Contact> findListByLoanCode(String loanCode, String loanCustomerType0);
	
    /**
     * 通过关联ID查询联系人
     * 2015年12月3日
	 * By 张灏
     * @param loanCode
     * @param loanCustomerType0
     * @param coborrowId
     * @return List<Contact>
     */
    public List<Contact> findListByLinkId(String loanCode,String loanCustomerType0,String coborrowId);
	
    /**
     * 通过传入参数删除联系人信息
     * 2016年1月27日
     * By 李强
     * @param param
     * @return none
     */
	public void deleteByCondition(Map<String,Object> param);
	
	/**
	 *通过关联ID删除联系人 
	 *2016年1月28日 
	 *By 张灏
	 *@param param key rid 
	 *@return none 
	 */
	public void deleteByRid(Map<String,Object> param);
}