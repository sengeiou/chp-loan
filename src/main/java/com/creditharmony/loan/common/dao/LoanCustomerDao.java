package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipAccount;
import com.creditharmony.loan.borrow.trusteeship.view.TrusteeshipView;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 客户详细信息Dao
 * @Class Name LoanCustomerDao
 * @author 张平
 * @Create In 2015年11月29日
 */
@LoanBatisDao
public interface LoanCustomerDao extends CrudDao<LoanCustomer> {
	
	/**
	 * 2015年11月29日
	 * @author zhanghao
	 * @param customerCode
	 * @return int
	 */
    public int deleteByPrimaryKey(String customerCode);
    
    /**
	 * 2015年11月29日
	 * @author zhanghao
	 * @param record
	 * @return int
	 */
    public int insert(LoanCustomer record);
    
    /**
     * 2015年12月25日
     * @author zhanghao
     * @param record
     * @return int
     */
    public int insertSelective(LoanCustomer record);
    
    /**
     * 2015年12月25日
     * @author zhanghao
     * @param customerCode
     * @return LoanCustomer
     */
    public LoanCustomer selectByPrimaryKey(String customerCode);
    
    /**
     * 2015年12月25日
     * @author zhanghao
     * @param record
     * @return int
     */
    public int updateByPrimaryKeySelective(LoanCustomer record);
    
    /**
     * 2015年12月25日
     * @author zhanghao
     * @param record
     * @return int
     */
    public int updateByPrimaryKey(LoanCustomer record);
    
    /**
     * 2015年12月25日
     * @author zhanghao
     * @param applyId
     * @return LoanCustomer
     */
    public LoanCustomer selectByApplyId(String applyId);
    
    /**
     * 通过咨询ID查询借款客户 
     * 2015年12月25日
     * @author zhanghao
     * @param consultId
     * @return LoanCustomer 
     */
    public LoanCustomer selectByConsultId(String consultId);
    
    /**
     * 跟据借款编号查询借款用户 
     * 2015年12月25日
     * @author zhanghao
     * @param param 借款编号
     * @return LoanCustomer  
     */
    public LoanCustomer selectByLoanCode(Map<String,Object> param); 
    
    /**
     * 插入applyId 
     * 2015年12月25日
     * @author zhanghao
     * @param param 借款编码、流程Id
     * @return none
     */
    public void updateApplyId(Map<String,Object> param);
    
    /**
     *复议流程发起时将信借流程ID更改为复议流程ID 
     *@Create In 2016年02月22日
     *@author zhanghao
     *@param  param
     *@return none 
     * 
     */
    public void updateApplyIdByOldApplyId(Map<String,String> param);
    
    /**
     * 通过复议ApplyId 
     * @author zhanghao 
     * @Create In 2016年02月20日
     * @param applyId 
     * @return LoanCustomer
     * 
     */
    public LoanCustomer selectByReconsiderApplyId(String applyId);
    
    /**
     * 根据借款编码，查询金账户开户时所需要的数据
     * 2016年3月3日
     * By 王浩
     * @param loanCode
     * @return 
     */
    public TrusteeshipAccount selectTrusteeshipAccount(String loanCode);
    
    /**
     * 根据借款编码，查询金账户更新协议库所需要的数据
     * 2016年3月3日
     * By 王浩
     * @param loanCode
     * @return 
     */
    public List<TrusteeshipAccount> selectAllTrusteeAccount(List<String> loanCode);
    
    /**
     * 根据身份证号和姓名，查询applyId及loanCode
     * 2016年3月7日
     * By 王浩
     * @param param
     * @return 
     */
    public TrusteeshipView getApplyIdByIdentity(Map<String,Object> param);

	/**
	 * 根据借款编码，更新金账户相关信息
	 * 2016年3月29日
	 * By 王浩
	 * @param loanCustomer
	 * @return 
	 */
	public int updateGoldFlag(LoanCustomer loanCustomer);
    
	/**
     * 根据ID，更新无纸化相关信息
     * 2016年4月222日
     * By zhanghao
     * @param param
     * @return none
     */
	public void updatePaperlessMessage(Map<String,Object> param);
	
	/**
	 * 根据ID获取主借人数据
	 * 2016年5月26日
	 * By 李文勇
	 * @param id
	 * @return
	 */
	public LoanCustomer getById(String id);
	
	/**
	 * 根据id修改以上可知晓本次借款的联系人
	 * By 任志远 2016年10月28日
	 *
	 * @param loanCustomer
	 */
	public void updateWhoCanKnowBorrow(LoanCustomer loanCustomer);
	
	/**
	 * 根据id查询用户的邮箱是否验证过
	 * @author 于飞
	 * @Create 2017年1月6日
	 * @param id
	 * @return
	 */
	public LoanCustomer checkIfEmailConfirm(LoanCustomer loanCustome);
	
	/**
	 * 将customer_base邮箱设置为未验证过
	 * @author 于飞
	 * @Create 2017年1月10日
	 * @param id 客户id
	 */
	public void updateEmailConfirm(LoanCustomer customer);
	
	public void updateCustomerEmailConfirm(LoanCustomer customer);
	
	public LoanCustomer checkEmailConfirm(String id);
	/**
	 * 根据证件号查询客户
	 * @param certNum
	 * @return
	 */
	public LoanCustomer selectByCertNum(String certNum);
 }