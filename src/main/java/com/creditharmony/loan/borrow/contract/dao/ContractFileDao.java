package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;

@LoanBatisDao
public interface ContractFileDao extends CrudDao<ContractFile>{
    
    /**
     *使用contractCode查询合同文件 
     *@author  
     *@create in 2016年04月29日
     *@param contractCode 
     *@return List<ContractFile>
     */
	public List<ContractFile> findContractFileByContractCode(String contractCode);
	
	 /**
     *使用docId查询合同文件 
     *@author  
     *@create in 2016年04月29日
     *@param docId 
     *@return List<ContractFile>
     */
	public ContractFile findContractFileByDocId(String docId);
	
	 /**
     *使用param查询合同文件 
     *@author  
     *@create in 2016年04月29日
     *@param param 
     *@return List<ContractFile>
     */
	public List<ContractFile> getContractFileByParam(Map<String,Object> param);
	
	 /**
     *使用docId更新合同文件信息 
     *@author  zhanghao
     *@create in 2016年04月29日
     *@param param 
     *@return none
     */
	public void updateCtrFile(ContractFile contractFile);
	 
	/**
     *通过ID清除签字的文件
     *@author  zhanghao
     *@create in 2016年04月29日
     *@param contractFile 
     *@return none
     */
	public void clearSignDocId(ContractFile contractFile);

	public ContractFile getContractFile(ContractFile file);
	
	/**
	 * 获取文件类型
	 * @param docIdOld
	 * @return
	 * @author FuLiXin
	 * @date 2016年12月23日 上午10:28:15
	 */
	public String getFileType(String docIdOld);
}
