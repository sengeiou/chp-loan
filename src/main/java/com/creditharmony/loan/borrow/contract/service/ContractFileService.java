package com.creditharmony.loan.borrow.contract.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.ContractFileDao;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;


/**
 * 合同相关文件Service 
 * @Class Name ContractPersonService
 * @create In 2016年3月4日
 * @author 尚军伟
 */

@Service
public class ContractFileService extends CoreManager<ContractFileDao,ContractFile>{
	
	@Autowired
	private ContractFileDao cfDao;
	
	
	/**
	 * 查询合同相关的文件信息
	 * 2016年3月15日
	 * By 尚军伟
	 * @param 
	 * @return ContractAndPersonInfo
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
   public List<ContractFile> findContractFileByContractCode(String contractCode){
		return this.cfDao.findContractFileByContractCode(contractCode);
	}
	
	
	/**
	 * 通过docId查询合同相关的文件信息
	 * 2016年3月15日
	 * By 尚军伟
	 * @param 
	 * @return ContractAndPersonInfo
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
   public ContractFile findContractFileByDocId(String docId){
		return this.cfDao.findContractFileByDocId(docId);
	}
	
	 /**
     *使用param查询合同文件 
     *@author  
     *@create in 2016年04月29日
     *@param param 
     *@return List<ContractFile>
     */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public List<ContractFile> getContractFileByParam(Map<String,Object> param){
	    return cfDao.getContractFileByParam(param);
	}
	 /**
     *更新合同文件 
     *@author  
     *@create in 2016年04月29日
     *@param contractFile 
     *@return none
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCtrFile(ContractFile contractFile){
		contractFile.preUpdate();
	    cfDao.updateCtrFile(contractFile);
	}

	/**
     *通过ID清除签字的文件
     *@author  zhanghao
     *@create in 2016年04月29日
     *@param contractFile 
     *@return none
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void clearSignDocId(ContractFile contractFile){
        cfDao.clearSignDocId(contractFile);
    }


	public ContractFile getContractFile(ContractFile file) {
		return cfDao.getContractFile(file);
	}
	/**
	 * 获取文件类型
	 * @param docIdOld
	 * @return
	 * @author FuLiXin
	 * @date 2016年12月23日 上午10:27:11
	 */
	public String getFileType(String docIdOld) {
		return cfDao.getFileType(docIdOld);
	}
}
