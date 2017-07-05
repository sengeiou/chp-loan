/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceContractOperateInfoService.java
 * @Create By 张灏
 * @Create In 2015年12月4日 下午2:43:32
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.ContractOperateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.ContractOperateInfo;

/**
 * @Class Name ContractOperateInfoService
 * @author 张灏
 * @Create In 2015年12月4日
 */
@Service
@Transactional(readOnly=true,value = "loanTransactionManager")
public class ContractOperateInfoService extends
        CoreManager<ContractOperateInfoDao, ContractOperateInfo> {

    @Autowired
    private ContractOperateInfoDao contractOperateInfoDao;
    
    /**
     *插入合同审核、制作操作信息 
     * @author 张灏
     * @Create In 2015年12月4日
     * @param ZhangYan
     * @return none 
     */
    @Transactional(readOnly=false,value = "loanTransactionManager")
    public void insertContractOperateInfo(ContractOperateInfo contractOperateInfo){
        contractOperateInfoDao.insertContractOperation(contractOperateInfo);
    }
   
    /**
     *查找最大的合同审核退回节点  
     * @author 张灏
     * @Create In 2015年12月25日
     * @param  param  loanCode 借款编号  dictOperateResult 操作结果
     * @return  
     */
    @Transactional(readOnly=true,value = "loanTransactionManager")
    public ContractOperateInfo findBackNode(Map<String,Object> param){
       return  contractOperateInfoDao.findBackNode(param); 
    }
}
