package com.creditharmony.loan.borrow.delivery.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;

@LoanBatisDao
public interface DeliveryTaskNewDao extends CrudDao<DeliveryViewExNew> {
	
   /**
    * @Title: queryDeliveryList
    * @Description: TODO(匹配列表查询)
    * @param @param pageBounds
    * @param @param params
    * @param @return    设定文件
    * @return List<DeliveryViewEx>    返回类型
    */
	public List<DeliveryViewExNew> queryDeliveryList(PageBounds pageBounds,DeliveryReq params);
	
	/**
	* @Title: exportList
	* @Description: TODO(导出交割数据列表查询)
	* @param @param params
	* @param @return    设定文件
	* @return List<DeliveryViewExNew>    返回类型
	* @throws
	 */
	public List<DeliveryViewExNew> exportList(DeliveryReq params);
	/**
	* @Title: queryDeliveryListCount
	* @Description: TODO(查询交割记录数)
	* @param @param deliveryResult
	* @param @return    设定文件
	* @return int    返回类型
	* @throws
	 */
	public int queryDeliveryListCount(String deliveryResult);
	/**
	* @Title: deleteDelivery
	* @Description: TODO(删除交割记录)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void deleteDelivery();
	

}
