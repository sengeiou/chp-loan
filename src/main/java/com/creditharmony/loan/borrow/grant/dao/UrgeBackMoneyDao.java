package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeBackMoneyEx;

/**
 * 催收服务费返款操作
 * @Class Name UrgeBackMoneyDao
 * @author 朱静越
 * @Create In 2016年1月11日
 */
@LoanBatisDao
public interface UrgeBackMoneyDao extends CrudDao<UrgeBackMoneyEx> {

	/**
     * 催收服务费返款列表
     * 2015年12月30日
     * By 张振强
     * @param pageBounds
     * @param urgeBackMoneyEx
     * @return List
     */
    public List<UrgeBackMoneyEx> selectBackMoneyList(PageBounds pageBounds,UrgeBackMoneyEx urgeBackMoneyEx);
    
    /**
     * 根据查询条件查询，不需要分页
     * 2016年2月23日
     * By 朱静越
     * @param urgeBackMoneyEx 查询条件
     * @return 查询集合
     */
    public List<UrgeBackMoneyEx> selectBackMoneyList(UrgeBackMoneyEx urgeBackMoneyEx);
    
	/**
     * 更新状态
     * 2015年12月30日
     * By 张振强
     * @param urgeBackMoneyEx
     * @return none
     */
	public void updateUrgeBack(UrgeBackMoneyEx urgeBackMoneyEx);
	
	/**
	 * 根据合同编号查询数据
	 * 2016年7月21日
	 * By 朱静越
	 * @param urgeBackMoneyEx
	 * @return
	 */
	public UrgeBackMoneyEx getByContract(UrgeBackMoneyEx urgeBackMoneyEx);

}
