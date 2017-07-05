package com.creditharmony.loan.borrow.contract.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeTempDao;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;

/**
 *合同费率信息业务层  
 *ContractFeeService
 *@create In 2015年12月1日
 *@author zhanghao 
 * 
 */
@Service("contractFeeService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ContractFeeService extends CoreManager<ContractFeeDao, ContractFee> {
	
     @Autowired
     private ContractFeeDao contractFeeDao;
     @Autowired
     private ContractFeeTempDao contractFeeTempDao;
     
     /**
      *新增合同费用表 
      *@author zhanghao
      *@Create In 2016年12月20日
      *@param ContractFee 
      *@return none
      */
     @Transactional(readOnly = false,value = "loanTransactionManager")
	 public void insertContractFee(ContractFee contractFee){
        contractFee.preInsert();
	    contractFeeDao.insertContractFee(contractFee);
	 }
     
     /**
      *查询合同费用信息
      *@author zhanghao
      *@Create In 2016年12月20日 
      *@param contractCode 
      *@return ContractFee
      */
     @Transactional(readOnly = true,value = "loanTransactionManager")
     public ContractFee findByContractCode(String contractCode){
    	  
    	 return contractFeeDao.findByContractCode(contractCode);
     }
     /**
      * 查询合同临时费用
     ·* 2017年2月22日
     ·* by Huowenlong
      * @param contractCode
      * @return
      */
     @Transactional(readOnly = true,value = "loanTransactionManager")
     public ContractFeeTemp searchContractFeeTempByContractCode(Map map){
    	 return contractFeeTempDao.searchContractFeeTempByContractCode(map);
     }
     
     /**
      *删除合同费率信息 
      *@author zhanghao
      *@Create In 2016年12月20日
      *@param contractCode 
      *@return none 
      */
     @Transactional(readOnly = true,value = "loanTransactionManager")
     public void deleteByContractCode(String contractCode){
         
         contractFeeDao.deleteByContractCode(contractCode);
     }
     
     /**
      *删除合同费率信息 
      *@author zhanghao
      *@Create In 2016年12月20日
      *@param loanCode
      *@return none
      */
     @Transactional(readOnly = false,value = "loanTransactionManager")
     public void deleteByLoanCode(String loanCode){
        
         contractFeeDao.deleteByLoanCode(loanCode);
     }
     /**
      * 校验金信额度
      * @param contractBusiView
      * @return
      */
     @Transactional(readOnly = false,value = "loanTransactionManager")
	public ContractBusiView checkLimit(ContractBusiView contractBusiView) {
		return contractFeeDao.checkLimit(contractBusiView);
	}
}
