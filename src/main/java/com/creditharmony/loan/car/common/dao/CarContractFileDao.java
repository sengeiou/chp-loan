package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarContractFile;

@LoanBatisDao
public interface CarContractFileDao extends CrudDao<CarContractFile>{
	
	public int insert(CarContractFile record);

	public int insertSelective(CarContractFile record);
    
    /**
     *使用contractCode查询合同文件 
     *@author  
     *@create in 2016年5月7日
     *@param contractCode 
     *@return List<ContractFile>
     */
	public List<CarContractFile> findContractFileByContractCode(String contractCode);
	
	 /**
     *使用docId查询合同文件 
     *@author  
     *@create in 2016年5月7日
     *@param docId 
     *@return List<ContractFile>
     */
	public CarContractFile findContractFileByDocId(String docId);
	
	 /**
     *使用param查询合同文件 
     *@author  
     *@create in 2016年5月7日
     *@param param 
     *@return List<ContractFile>
     */
	public List<CarContractFile> getContractFileByParam(Map<String,Object> param);
	
	 /**
     *使用docId更新合同文件信息 
     *@author  zhanghao
     *@create in 2016年5月7日
     *@param param 
     *@return none
     */
	public void updateCtrFile(CarContractFile contractFile);
	
	
}
