package com.creditharmony.loan.borrow.delivery.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.delivery.dao.DeliveryTaskDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
/**
 * 交割已办列表
 * @Class Name DeliveryTaskService
 * @author lirui
 * @Create In 2015年12月7日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class DeliveryTaskService extends CoreManager<DeliveryTaskDao,DeliveryViewEx> {					 
	
	/**
	 * 获得所有已办列表
	 * 2015年12月14日
	 * By lirui
	 * @param params 检索参数
	 * @param page 分页信息
	 * @return 已办列表
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<DeliveryViewEx> taskList(Page<DeliveryViewEx> page,DeliveryParamsEx params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("loan_code");
		PageList<DeliveryViewEx> pageList = (PageList<DeliveryViewEx>)dao.taskList(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;		
	}
}
