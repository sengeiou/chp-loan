package com.creditharmony.loan.borrow.consult.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.consult.dao.ValidHisDao;
import com.creditharmony.loan.borrow.consult.entity.ValidHistory;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 主借人，共借人身份验证Service
 * 
 * @Class Name ValidStatusService
 * @author 宋锋
 * @Create In 2016年10月20日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ValidStatusService extends CoreManager<ValidHisDao, ValidHistory> {

	@Autowired
	private ValidHisDao validHisDao;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;
	
	@Autowired
	private ContractDao contractDao;

	/**
	 * 修改共借人身份验证信息 
	 * @param param
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCoborrowerStatus(Map<String,Object> param) {
		validHisDao.updateCoborrowerStatus(param);
	}
	
	/**
	 * 修改主借人身份验证信息 
	 * @param param
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateLoanCustomerStatus(Map<String,Object> param) {
		validHisDao.updateLoanCustomerStatus(param);
	}
	
	/**
	 * 添加身份验证历史信息 
	 * @param param
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertValidHis(ValidHistory validHistory) {
		validHisDao.insertValidHis(validHistory);
	}
	
	
	/**
	 * 依据条件修改身份验证信息并保存历史记录
	 * @param param
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String updateStatus(ValidHistory validHistory) {
		try{
			if(validHistory!=null){
				//获取主借人或共借人的姓名 并判断数据存在性
				String loanName=null;
	        	Contract contract=contractDao.findByContractCode(validHistory.getContractCode());
	        	if(contract!=null){
	        		List<LoanCoborrower>  list=null;
	        		LoanCustomer loanCustomer=null;
	        		//主借人
	        		if("0".equals(validHistory.getUpdateType())){
		        		if(contract.getApplyId()!=null){
		        			loanCustomer=loanCustomerDao.selectByApplyId(contract.getApplyId());
		        			if(loanCustomer!=null){
		        				loanName=loanCustomer.getCustomerName();
		        			}
		        		}
		        	}else if("1".equals(validHistory.getUpdateType())){//共借人信息
		        		if(contract.getLoanCode()!=null){
		        			list=loanCoborrowerDao.selectByLoanCode(contract.getLoanCode());
		        			for(LoanCoborrower cob:list){
		        				if(validHistory.getCertNum()!=null&&validHistory.getCertNum().equals(cob.getCoboCertNum())){
		        					loanName=cob.getCoboName();
		        					break;
		        				}
		        			}
		        			
		        		}
		        	}
	        		if(loanCustomer==null&&list.isEmpty()){
	        			return "noinfo";
	        		}
	        	}else{
	        		return "noContract";
	        	}
	    		Map<String,Object> param=new HashMap<String,Object>();
	        	param.put("contractCode", validHistory.getContractCode());
	        	param.put("certNum", validHistory.getCertNum());
	        	//修改类型为0 表示主借人  为1表示共借人 修改对应表的身份验证信息
	        	if("0".equals(validHistory.getUpdateType())){
	        		this.updateLoanCustomerStatus(param);
	        	}else if("1".equals(validHistory.getUpdateType())){
	        		this.updateCoborrowerStatus(param);
	        	}
	        	//添加身份验证的历史记录`
	        	ValidHistory validHistory2=new ValidHistory();
	        	validHistory2.preInsert();
	        	validHistory2.setCertNum(validHistory.getCertNum());
	        	validHistory2.setContractCode(validHistory.getContractCode());
	        	validHistory2.setUpdateType(validHistory.getUpdateType());
	        	validHistory2.setLoanName(loanName);
	        	this.insertValidHis(validHistory2);
	        	
	    	}
		}catch(Exception e){
			e.printStackTrace();
			return "false";
		}
		return "success";
	}
	
	
	/**
	 * 查询数据
	 */
	public Page<ValidHistory> findPage(Page<ValidHistory> page,
			ValidHistory validHistory) {
		return super.findPage(page, validHistory);
	}
}
