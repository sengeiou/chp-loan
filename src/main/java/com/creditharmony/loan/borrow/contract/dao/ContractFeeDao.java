package com.creditharmony.loan.borrow.contract.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;

/**
 * 操作合同费率表 
 * @Class Name ContractFeeDao
 * @author 张灏
 * @Create In 2015年12月1日
 */
@LoanBatisDao
public interface ContractFeeDao extends CrudDao<ContractFee> {
       
    /**
     * 插入部分合同费率信息 
     * 2015年12月1日
     * By 张灏
     * @param contractFee
     * @return int
     */
    public int insertSelectiveContractFee(ContractFee contractFee);
       
    /**
     * 查询合同费率信息 
     * 2015年12月1日
     * By 张灏
     * @param contractCode
     * @return ContractFee
     */
    public ContractFee findByContractCode(String contractCode);
	   
    /**
     * 删除合同费率信息 
     * 2015年12月1日
     * By 张灏
     * @param contractCode
     * @return none   
     */
    public void deleteByContractCode(String contractCode);
       
    /**
     * 更新部分合同费率表信息 
     * 2015年12月1日
     * By 张灏
     * @param contractFee
     * @return none
     */
    public void updateByCtrCodeSelective(ContractFee contractFee);
       
    /**
     * 根据合同编号全部更新合同费率表 
     * 2015年12月1日
     * By 张灏
     * @param contractFee
     * @return none 
	    */
    public void updateByCtrCode(ContractFee contractFee);
       
    /**
     * 新增合同费率信息 
     * 2015年12月1日
     * By 张灏
     * @param contractFee
     * @return none
     */
    public void insertContractFee(ContractFee contractFee);
	   
    /**
     * 删除合同费率信息 
     * 2015年12月1日
     * By 张灏
     * @param loanCode
     * @return none
     */
    public void deleteByLoanCode(String loanCode);
    /**
     * 校验金信额度
     * @param contractBusiView
     * @return
     */
	public ContractBusiView checkLimit(ContractBusiView contractBusiView);
	
	  /**
     * 存入首次以及尾次放款金额
     * 2015年12月1日
     * By 张灏
     * @param loanCode
     * @return none
     */
    public void updateByBankCode(ContractFee contractFee);
}
