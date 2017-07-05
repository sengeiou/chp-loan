package com.creditharmony.loan.borrow.delivery.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.delivery.dao.DeliveryTaskNewDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;

@Service
@Transactional(value="loanTransactionManager")
public class DeliveryTaskNewService extends CoreManager<DeliveryTaskNewDao,DeliveryViewExNew> {					 
	/**
	* @Title: taskList
	* @Description: TODO(匹配列表查询)
	* @param @param page
	* @param @param params
	* @param @return    设定文件
	* @return Page<DeliveryViewEx>    返回类型
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<DeliveryViewExNew> queryDeliveryList(Page<DeliveryViewExNew> page,DeliveryReq params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("loan_code");
		PageList<DeliveryViewExNew> pageList = (PageList<DeliveryViewExNew>)dao.queryDeliveryList(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;		
	}
	
	/**
	* @Title: exportList
	* @Description: TODO(导出交割数据列表查询)
	* @param @param params
	* @param @return    设定文件
	* @return List<DeliveryViewExNew>    返回类型
	* @throws
	 */
	public List<DeliveryViewExNew> exportList(DeliveryReq params) {
		return dao.exportList(params);
	}
	/**
	* @Title: queryDeliveryListCount
	* @Description: TODO(查询交割记录)
	* @param @param deliveryResult
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 */
	public int queryDeliveryListCount(String deliveryResult) {
		return dao.queryDeliveryListCount(deliveryResult);
	}
	/**
	* @Title: deleteDelivery
	* @Description: TODO(删除交割记录)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void deleteDelivery(){
		dao.deleteDelivery();
	}
}
