package com.creditharmony.loan.borrow.delivery.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.delivery.dao.DeliveryDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryCountReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.entity.UserInfo;


@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class DeliveryService extends CoreManager<DeliveryDao,DeliveryViewExNew> {
	@Autowired
    private ApplyLoanInfoDao loanInfoDao;
	
	/**
	* @Title: deliveryList
	* @Description: TODO(待交割列表查询)
	* @param @param page
	* @param @param params
	* @param @return    设定文件
	* @return Page<DeliveryViewEx>    返回类型
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<DeliveryViewExNew> deliveryList(Page<DeliveryViewExNew> page,DeliveryReq params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("loan_code"); 
		PageList<DeliveryViewExNew> pageList = (PageList<DeliveryViewExNew>)dao.deliveryList(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;
	}
	/**
	* @Title: deliveryListSingle
	* @Description: TODO(交割列表查询)
	* @param @param page
	* @param @param params
	* @param @return    设定文件
	* @return Page<DeliveryViewExNew>    返回类型
	* @throws
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<DeliveryViewExNew> deliveryListSingle(Page<DeliveryViewExNew> page, DeliveryReq params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("loan_code"); 
		PageList<DeliveryViewExNew> pageList = (PageList<DeliveryViewExNew>)dao.deliveryListSingle(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;
	}
	/**
	* @Title: updateLoanInfoDelivery
	* @Description: TODO(单条交割更新loanInfo表)
	* @param @param deliveryViewExNew    设定文件
	* @return int    返回类型
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int updateLoanInfoDelivery(DeliveryViewExNew deliveryViewExNew) {
		//deliveryViewExNew.preUpdate();
	    deliveryViewExNew.setDeliveryResult("1");
	    deliveryViewExNew.setDeliveryTime(new Date());
		return loanInfoDao.updateLoanInfoDelivery(deliveryViewExNew);
	}
	
	/**
	* @Title: updateLoanInfoDelBatch
	* @Description: TODO(匹配交割更新loanInfo表)
	* @param @param dv
	* @param @param loanCodes    设定文件
	* @return void    返回类型
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int updateLoanInfoDelBatch(DeliveryViewExNew dv, String[] loanCodes) {
		int status=1;
		if (loanCodes.length != 0) {
			for (int i = 0; i < loanCodes.length; i++) {
				dv.preUpdate();
				dv.setLoanCode(loanCodes[i]);
				dv.setDeliveryResult("1");
				int updateLoanInfoDelivery = loanInfoDao.updateLoanInfoDelivery(dv);
				if(updateLoanInfoDelivery<0){
					status=2;
					break;
				}
			}
		}
		return status;
	}

	/**
	* @Title: orgs
	* @Description: TODO(获得所有汇金业务部)
	* @param @return    设定文件
	* @return List<OrgView>    返回类型
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<OrgView> orgs() {	
		return dao.orgs(LoanOrgType.BUISNESS_DEPT.key);
	}
	/**
	* @Title: loanInfoByStoreId
	* @Description: TODO(交割成功个数)
	* @param @param prams
	* @param @return    设定文件
	* @return int    返回类型
	 */
	public int loanInfoByStoreId(DeliveryCountReq param){
		return dao.loanInfoByStoreId(param);
	}
	/**
	* @Title: insertDelivery
	* @Description: TODO(导入交割记录)
	* @param @param dv    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void insertDelivery(DeliveryViewExNew dv) {
		// 插入基础数据
		dv.preInsert();
		dao.insertDelivery(dv);
	}
	
	/**
	* @Title: loanInfoByLoanCode
	* @Description: TODO(通过loanCode 查询借款信息)
	* @param @param loanCode
	* @param @return    设定文件
	* @return DeliveryViewExNew    返回类型
	* @throws
	 */
	public DeliveryViewExNew loanInfoByLoanCode(String loanCode){
		return dao.loanInfoByLoanCode(loanCode);
	}
	
	/**
    * @Title: userByUserCode
    * @Description: TODO(查询用户信息是否正确)
    * @param @param userCode
    * @param @return    设定文件
    * @return UserInfo    返回类型
    * @throws
     */
	public UserInfo userByUserCode(String userCode){
		return dao.userByUserCode(userCode);
	}
	
	/**
	* @Title: orgByStoreName
	* @Description: TODO(查询门店id)
	* @param @param storeName
	* @param @return    设定文件
	* @return OrgInfo    返回类型
	* @throws
	 */
	public OrgInfo orgByStoreName(String storeName){
		return dao.orgByStoreName(storeName);
	}
	/**
	* @Title: findByContractCode
	* @Description: TODO(查询合同号)
	* @param @param contractCode
	* @param @return    设定文件
	* @return Contract    返回类型
	* @throws
	 */
	public Contract findByContractCode(String contractCode){
		return dao.findByContractCode(contractCode);
	}
}
