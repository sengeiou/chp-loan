/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceCustInfoService.java
 * @Create By 张灏
 * @Create In 2015年11月28日 下午5:53:47
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.entity.ex.CustInfo;

/**
 * 联表查询用户信息
 * @Class Name CustInfoService
 * @author 张灏
 * @Create In 2015年11月28日
 */
@Service("custInfoService")
@Transactional(readOnly=true,value = "loanTransactionManager")
public class CustInfoService extends CoreManager<CustInfoDao, CustInfo> {
  
   @Autowired
   private CustInfoDao custInfoDao;
	
    /**
     *查询门店信息 
     *@author zhanghao
     *@Create In 2015年12月20日 
     *@param applyId
     *@return CustInfo 
     */
	public CustInfo findStoreInfo(String applyId){
		
		return custInfoDao.findStoreInfo(applyId);
	
	}
	
	/**
	 *查询客户信息 
	 *@author zhanghao
     *@Create In 2015年12月20日 
	 *@param applyId 
	 *@return CustInfo 
	 */
	public CustInfo findCustInfo(String applyId){
		
		return custInfoDao.findCustInfo(applyId);
	}
	
	/**
	 *查询审批信息 
	 *@author zhanghao
     *@Create In 2015年12月20日 
	 *@param applyId
	 *@return CustInfo 
	 * 
	 */
	@Transactional(readOnly=true,value = "loanTransactionManager")
	public CustInfo findAuditInfo(String applyId){
		
		return custInfoDao.findAuditInfo(applyId);
	}
	
	/**
	 *更新标识 
	 *@author zhanghao
     *@Create In 2015年12月20日 
	 *@param applyIds
	 *@param attr
	 *@param value
	 *@param userCode
	 *@return none
	 */
	@Transactional(readOnly=false,value = "loanTransactionManager")
	public void changeFlag(String[] applyIds,String attr,String value,String userCode){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("applyIds", applyIds);
		param.put("attributName", attr);
		param.put("val", value);
		param.put("userCode",userCode);
		custInfoDao.updateFlag(param);
	}
	
    /**
     *查询复议的客户信息 
     *@author zhanghao
     *@Create In 2015年12月20日 
     *@param key applyId
     *@return CustInfo 
     */
    public CustInfo findReconsiderCustInfo(Map<String,Object> param){
        
        return custInfoDao.findReconsiderCustInfo(param);
    }
    /**
     * 查询外访距离，外访标志
     * @param loanCode
     * @return
     */
	public Map<String, String> findOutSide(String loanCode) {
		return custInfoDao.findOutSide(loanCode);
	}
}
