package com.creditharmony.loan.borrow.contract.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
/**
 * 费率临时表Dao
·* 2017年2月21日
·* by Huowenlong
 */
@LoanBatisDao
public interface ContractFeeTempDao extends CrudDao<ContractFeeTemp>{
	
    public int deleteByPrimaryKey(String id);

    public int insert(ContractFeeTemp record);

    public int insertSelective(ContractFeeTemp record);
    
    public int insertContractFeeSelective(ContractFeeTemp record);

    public ContractFeeTemp selectByPrimaryKey(String id);

    public int updateByPrimaryKeySelective(ContractFeeTemp record);

    public int updateByPrimaryKey(ContractFeeTemp record);

    
    public ContractFeeTemp searchContractFeeTempByContractCode(Map map);

    
    public ContractFeeTemp selectByContractCode(String contractCode);

    public void deleteByContractCode(String contractCode);
    
    public void updateByBankCode(ContractFeeTemp contractFeeTemp);
}