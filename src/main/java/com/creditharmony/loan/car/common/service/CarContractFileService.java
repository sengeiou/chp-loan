package com.creditharmony.loan.car.common.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarContractFileDao;
import com.creditharmony.loan.car.common.entity.CarContractFile;


/**
 * 合同相关文件Service 
 * @Class Name ContractPersonService
 * @create In 2016年5月7日
 * @author 葛志超
 */

@Service
public class CarContractFileService extends CoreManager<CarContractFileDao,CarContractFile>{
	
	@Autowired
	private CarContractFileDao cfDao;
	
	
	/**
	 * 查询合同相关的文件信息
	 * 2016年3月15日
	 * By 葛志超
	 * @param 
	 * @return ContractAndPersonInfo
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
   public List<CarContractFile> findContractFileByContractCode(String contractCode){
		return this.cfDao.findContractFileByContractCode(contractCode);
	}
	
	
	/**
	 * 通过docId查询合同相关的文件信息
	 * 2016年5月7日
	 * By 葛志超
	 * @param 
	 * @return ContractAndPersonInfo
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
   public CarContractFile findContractFileByDocId(String docId){
		return this.cfDao.findContractFileByDocId(docId);
	}
	
	 /**
     *使用param查询合同文件 
     *@author  
     *@create in 2016年5月7日
     *@param param 
     *@return List<ContractFile>
     */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public List<CarContractFile> getContractFileByParam(Map<String,Object> param){
	    return cfDao.getContractFileByParam(param);
	}
	 /**
     *更新合同文件 
     *@author  
     *@create in 2016年5月7日
     *@param contractFile 
     *@return none
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCtrFile(CarContractFile contractFile){
		contractFile.preUpdate();
	    cfDao.updateCtrFile(contractFile);
	}

	 /**
     *插入新合同文件 
     *@author  
     *@create in 2016年5月7日
     *@param contractFile 
     *@return none
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertCtrFile(CarContractFile contractFile){
	    cfDao.insertSelective(contractFile);
	}
}
