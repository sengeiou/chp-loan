package com.creditharmony.loan.borrow.delivery.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;

/**
 * 交割待办列表
 * @Class Name DeliveryWaitDao
 * @author lirui
 * @Create In 2015年12月7日
 */
@LoanBatisDao
public interface DeliveryWaitDao extends CrudDao<DeliveryViewEx> {
	/**
	 * 获得交割待办列表
	 * 2015年12月7日
	 * By lirui
	 * @param params 检索参数
	 * @param pageBounds 分页信息
	 * @return 交割待办数据集合
	 */
	public List<DeliveryViewEx> deliveryWait(PageBounds pageBounds,DeliveryParamsEx params);
	
	/**
	 * 根据借款编码获得当前要办理的交割信息
	 * 2015年12月11日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 交割信息
	 */
	public List<DeliveryViewEx> deliveryInfo(String loanCode);
	
	/**
	 * 交割办理结果提交
	 * 2015年12月15日
	 * By lirui
	 * @param dv 结果参数容器
	 */
	public void deliveryResult(DeliveryViewEx dv);
	
	/**
	 * 交割审核通过(批量用)
	 * 2015年12月17日
	 * By lirui 
	 * @param lioanCode 通过的借款编码
	 */
	public void passDelivery(DeliveryViewEx dv);
	
	/**
	 * 交割申请驳回(批量用)
	 * 2015年12月17日
	 * By lirui
	 * @param dv 驳回参数
	 */
	public void regected(DeliveryViewEx dv);
}
