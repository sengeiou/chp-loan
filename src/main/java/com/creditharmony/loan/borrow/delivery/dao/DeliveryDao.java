package com.creditharmony.loan.borrow.delivery.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryCountReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.entity.UserInfo;

@LoanBatisDao
public interface DeliveryDao extends CrudDao<DeliveryViewExNew> {
	/**
	* @Title: deliveryList
	* @Description: TODO(待交割列表查询)
	* @param @param pageBounds
	* @param @param params
	* @param @return    设定文件
	* @return List<DeliveryViewEx>    返回类型
	 */
	public List<DeliveryViewExNew> deliveryList(PageBounds pageBounds,DeliveryReq params);
	/**
	* @Title: deliveryListSingle
	* @Description: TODO(交割列表查询)
	* @param @param pageBounds
	* @param @param params
	* @param @return    设定文件
	* @return PageList<DeliveryViewExNew>    返回类型
	* @throws
	 */
	public PageList<DeliveryViewExNew> deliveryListSingle(PageBounds pageBounds, DeliveryReq params);
	/**
	* @Title: orgs
	* @Description: TODO(获得所有汇金业务部)
	* @param @param orgKey
	* @param @return    设定文件
	* @return List<OrgView>    返回类型
	 */
	public List<OrgView> orgs(String orgKey);
	/**
	* @Title: insertDelivery
	* @Description: TODO(导入交割记录)
	* @param @param dv    设定文件
	* @return void    返回类型
	 */
	public void insertDelivery(DeliveryViewExNew dv);
	/**
	* @Title: loanInfoByLoanCode
	* @Description: TODO(通过loanCode 查询借款信息)
	* @param @param loanCode
	* @param @return    设定文件
	* @return DeliveryViewExNew    返回类型
	* @throws
	 */
	public DeliveryViewExNew loanInfoByLoanCode(String loanCode);
    /**
    * @Title: userByUserCode
    * @Description: TODO(查询用户信息是否正确)
    * @param @param userCode
    * @param @return    设定文件
    * @return UserInfo    返回类型
    * @throws
     */
	public UserInfo userByUserCode(String userCode);
	/**
	* @Title: orgByStoreName
	* @Description: TODO(查询门店id)
	* @param @param storeName
	* @param @return    设定文件
	* @return OrgInfo    返回类型
	* @throws
	 */
	public OrgInfo orgByStoreName(String storeName);
	/**
	* @Title: loanInfoByStoreId
	* @Description: TODO(交割成功个数)
	* @param @param prams
	* @param @return    设定文件
	* @return int    返回类型
	 */
	public int loanInfoByStoreId(DeliveryCountReq param);
	/**
	* @Title: findByContractCode
	* @Description: TODO(查询合同号)
	* @param @param contractCode
	* @param @return    设定文件
	* @return Contract    返回类型
	* @throws
	 */
	public Contract findByContractCode(String contractCode);
}
