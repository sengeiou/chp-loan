package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ContractOperateInfo;

/**
 *合同操作记录表 
 * @Class Name ContractOperateInfoDao
 * @author 张灏
 * @Create In 2015年12月25日
 * @version 1.0
 */
@LoanBatisDao
public interface ContractOperateInfoDao extends CrudDao<ContractOperateInfo>{
   
    /**
     *新增合同审核阶段操作记录 
     * @author 张灏
     * @Create In 2015年12月25日
     *@param  record
     *@return
     */
    public void insertContractOperation(ContractOperateInfo record);
  
    /**
     *部分新增合同审核阶段操作记录  
     * @author 张灏
     * @Create In 2015年12月25日
     * @param  record
     * @return  
     */
    public void insertSelective(ContractOperateInfo record);
    
    /**
     *通过指定参数查询合同操作记录  
     * @author 张灏
     * @Create In 2015年12月25日
     * @param  record
     * @return  
     */
    public ContractOperateInfo findByParam(Map<String,Object> param);
   
    /**
     *通过指定参数查询合同操作记录  
     * @author 宋锋
     * @Create In 2016年11月26日
     * @param  record
     * @return  
     */
    public List<ContractOperateInfo> findInfoByLoanCode(Map<String,Object> param);
    /**
     *查找最大的合同审核退回节点  
     * @author 张灏
     * @Create In 2015年12月25日
     * @param  param  loanCode 借款编号  dictOperateResult 操作结果
     * @return  
     */
    public ContractOperateInfo findBackNode(Map<String,Object> param);
}