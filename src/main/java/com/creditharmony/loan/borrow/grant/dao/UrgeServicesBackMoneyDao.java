package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeHistory;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesBackMoneyEx;

/**
 * 催收服务费返款各种操作
 * @Class Name UrgeServicesBackMoneyDao
 * @author 朱静越
 * @Create In 2016年1月11日
 */
@LoanBatisDao
public interface UrgeServicesBackMoneyDao extends CrudDao<UrgeServicesBackMoneyEx>{
	
	/**
	 * 插入催收返款表
	 * 2015年12月16日
	 * By 朱静越
	 * @param record
	 * @return 插入操作结果
	 */
    public int insertUrgeBack(UrgeServicesBackMoney record);
    
    /**
     * 根据催收返款主表的id查询实体
     * 2016年2月23日
     * By 朱静越
     * @param id 催收返款主表id
     * @return 查询实体
     */
    public UrgeServicesBackMoneyEx selSendApply(String id);
    
    /**
     * 催收服务费返款申请列表查询
     * 2016年1月6日
     * By 朱静越
     * @param pageBounds
     * @param urgeServicesBackMoneyEx
     * @return 分页list
     */
    public List<UrgeServicesBackMoneyEx> selectBackMoneyApply(PageBounds pageBounds,UrgeServicesBackMoneyEx urgeServicesBackMoneyEx);
    
    /**
     * 根据查询条件查询需要导出的数据，不带分页
     * 2016年2月23日
     * By 朱静越
     * @param urgeServicesBackMoneyEx 查询条件
     * @return 列表
     */
    public List<UrgeServicesBackMoneyEx> selectBackMoneyApply(UrgeServicesBackMoneyEx urgeServicesBackMoneyEx);
    
    /**
     * 更新催收返款表
     * 2015年12月16日
     * By 朱静越
     * @param urgeBack
     * @return 更新结果
     */
    public int updateUrgeBack(UrgeServicesBackMoney urgeBack);
    
    /**
     * 查询催收服务费返款操作历史列表
     * 2016年1月6日
     * By 朱静越
     * @param pageBounds
     * @param urgeHistory
     * @return 操作历史实体集合
     */
    public List<UrgeHistory> selectUrgeHistory(PageBounds pageBounds,UrgeHistory urgeHistory);
    
	/**
	 * 操作历史
	 * 2015年12月22日
	 * By 张振强
	 * @param urgeHistory
	 * @return none
	 */
	public void insertUrgeHistory(UrgeHistory urgeHistory);
	
	/**
	 * 根据id获取返款信息
	 * @author 于飞
	 * @Create 2016年11月18日
	 * @param id
	 * @return
	 */
	public UrgeServicesBackMoneyEx getObjectById(String id);
}