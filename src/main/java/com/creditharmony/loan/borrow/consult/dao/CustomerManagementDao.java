package com.creditharmony.loan.borrow.consult.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.borrow.consult.view.InviteCustomerDetailView;
import com.creditharmony.loan.borrow.consult.view.InviteCustomerView;

/**
 * 客户咨询DAO
 * 
 * @Class Name CustomerManagementDao
 * @author 张平
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface CustomerManagementDao extends CrudDao<ConsultSearchView> {

    /**
     *查找历史咨询信息 
     *@author zhangping 
     *@Create In 2016年2月18日 
     *@param consultSearchView
     *@return List<ConsultSearchView>   
     */
	public List<ConsultSearchView> findHisPage(ConsultSearchView consultSearchView);

	/**
	 *通过指定Id更新咨询状态 
	 *@author zhangping
	 *@Create In 2016年2月18日
	 *@param id 
	 *@return none 
	 * 
	 */
	public void updateStatus(String id);
   
	/**
     *通过指定参数分页查询 
     *@author zhanghao
     *@create In 2016年1月29日
     *@param consultSearchView 
     *@return List<ConsultSearchView>
     */
    public List<ConsultSearchView> findPageList(ConsultSearchView consultSearchView);
    
    /**
     *通过指定下一步为“继续跟踪”查询最早建立咨询的时间 
     *@author zhanghao
     *@Create In 2016年2月1日 
     *@param consultSearchView
     *@Return List<ConsultSearchView> 
     */
    public List<ConsultSearchView> findEarliestLog(ConsultSearchView consultSearchView);
    
    /**
     *通过指定下一步为“门店放弃”查询最近建立咨询的时间  
     *@author zhanghao
     *@Create In 2016年2月1日 
     *@param consultSearchView
     *@Return List<ConsultSearchView>  
     *
     */
    public List<ConsultSearchView> findLastestLog(ConsultSearchView consultSearchView);
    
    /**
     *查询当前客户是否已经咨询且没有进件  
     *@author zhanghao
     *@Create In 2016年05月03日 
     *@param param 
     *@Return Consult  
     *
     */
    public Consult findConsultMess(Map<String,Object> param);


    /**
     *查询当前的首次咨询时间是多少
     *@author zhanghao
     *@Create In 2016年05月03日 
     *@param param 
     *@Return Consult  
     *
     */
	public ConsultSearchView getConsultationTime(String string);

	 /**
     *查询咨询ID是多少
     *@author zhanghao
     *@Create In 2016年05月03日 
     *@param param 
     *@Return Consult  
     *
     */
	public ConsultSearchView selectByCustId(String consultId);
	
	/**
	 * 查询客户咨询列表
	 * By 任志远 2017年4月27日
	 *
	 * @param consultSearchView
	 * @return
	 */
	public List<ConsultSearchView> findCustomerConsultionList(ConsultSearchView consultSearchView);

	/**
	 * 查询邀请客户列表 
	 * By 任志远 2017年5月5日
	 *
	 * @param inviteCustomerQuery
	 * @return
	 */
	public List<InviteCustomerView> findInviteCustomerList(InviteCustomerView inviteCustomerView);

	/**
	 * 查询邀请客户详细 
	 * By 任志远 2017年5月8日
	 *
	 * @return
	 */
	public List<InviteCustomerDetailView> findInviteCustomerDetailList(Map<String, Object> params);

	/** 
	 * 修改邀请客户信息（人工分配客户经理）（修改状态）
	 * By 任志远 2017年5月9日
	 *
	 * @param inviteCustomerView
	 */
	public void updateAppCustomerInfo(InviteCustomerView inviteCustomerView);
}
