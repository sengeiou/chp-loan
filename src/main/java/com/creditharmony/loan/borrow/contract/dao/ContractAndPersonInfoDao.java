package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ContractAndPersonInfo;

@LoanBatisDao
public interface ContractAndPersonInfoDao extends CrudDao<ContractAndPersonInfo>{
	
	/**
	 * 查询借款人已制作的合同信息
	 * 2016年3月3日
	 * By 尚军伟
	 * @param 
	 * @return ContractAndPersonInfo
	 * 
	 */
    
//    public List<ContractAndPersonInfo> findContractAndPerson(ContractAndPersonInfo ctrPersonInfo);
	
	//查找借款申请状态
	@SuppressWarnings("rawtypes")
	public List findLabel();
	
	//根据合同编号和文件名字查询文件docId
	public String findDocIdByContractCode(String contractCode,String contractFileName);

}
