package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;

/**
 * 合同临时表
·* 2017年2月21日
·* by Huowenlong
 */
@LoanBatisDao
public interface ContractTempDao extends CrudDao<ContractTemp>{
	
    public int deleteByContractCode(String contractCode);

    public int insert(ContractTemp record);

    public int insertSelective(ContractTemp record);
    
    public int insertContractSelective(ContractTemp record);
    
    public ContractTemp searchByContractCodeOrIsreceive(ContractTemp contractTemp);

    public ContractTemp selectByContractCode(String contractCode);

    public int updateByPrimaryKeySelective(ContractTemp record);

    public int updateByPrimaryKey(ContractTemp record);
    
    public List<ContractTemp> selectByLoanCode(String loanCode);

	public List<ContractTemp> selectConfirmContract(String loanCode);

	public void updateContractTemp(Map<String, String> param);

	public void updateContractTemp1(Map<String, String> param);

	public void updateContractFeeTemp(Map<String, String> param);

	public void updateContractFeeTemp1(Map<String, String> param);
	
	public void updateContractFeeTempPetition(Map<String, Object> param);
}