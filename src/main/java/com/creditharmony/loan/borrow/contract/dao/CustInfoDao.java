package com.creditharmony.loan.borrow.contract.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ex.CustInfo;


/**
 * 查询合同用户信息
 * @Class Name CustInfoDao
 * @author 张灏
 * @Create In 2015年11月28日
 */
@LoanBatisDao
public interface CustInfoDao extends CrudDao<CustInfo> {

	/**
	 * 查询门店信息 
	 * 2015年11月28日
	 * By 张灏
	 * @param applyId
	 * @return CustInfo 
	 */
    public CustInfo findStoreInfo(String applyId);
		
    /**
     * 查询客户信息 
     * 2015年11月28日
     * By 张灏
     * @param applyId
     * @return CustInfo
     */
    public CustInfo findCustInfo(String applyId);
		
    /**
     * 查询审批信息
     * 2015年11月28日
     * By 张灏
     * @param applyId
     * @return CustInfo
     */
    public CustInfo findAuditInfo(String applyId);
		
    /**
     * 更改标识 
     * 2015年11月28日
     * By 张灏
     * @param param
     * @return none
     */
    public void updateFlag(Map<String,Object> param);
    
    /**
     * 查询复议的客户信息 
     * 2015年11月28日
     * By 张灏
     * @param param
     * @return CustInfo
     */
    public CustInfo findReconsiderCustInfo(Map<String,Object> param);
    /**
     * 查询外访距离，外访标志
     * @param loanCode
     * @return
     */
	public Map<String, String> findOutSide(String loanCode);
}
