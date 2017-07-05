package com.creditharmony.loan.borrow.delivery.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
/**
 * 交割已办Dao
 * @Class Name DeliveryTaskDao
 * @author lirui
 * @Create In 2015年12月8日
 */
@LoanBatisDao
public interface DeliveryTaskDao extends CrudDao<DeliveryViewEx> {
	
	/**
	 * 获得交割已办列表
	 * 2015年12月8日
	 * By lirui
	 * @param params 检索参数
	 * @param pageBounds 分页信息
	 * @return 交割已办列表集合
	 */
	public List<DeliveryViewEx> taskList(PageBounds pageBounds,DeliveryParamsEx params);

}
