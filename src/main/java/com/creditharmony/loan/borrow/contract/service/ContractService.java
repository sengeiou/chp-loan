package com.creditharmony.loan.borrow.contract.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.account.dao.LoanBankEditDao;
import com.creditharmony.loan.borrow.account.entity.LoanBankEditEntity;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanInfoAllDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfoAll;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeTempDao;
import com.creditharmony.loan.borrow.contract.dao.ContractTempDao;
import com.creditharmony.loan.borrow.contract.dao.HCAuditResultDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractAndContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.HCAuditResultEntity;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractAmountSummaryEx;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;

/**
 * 合同Service 
 * @Class Name ContractService
 * @create In 2015年12月1日
 * @author 张灏 
 */
@Service("contractService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ContractService extends CoreManager<ContractDao, Contract>{

	@Autowired
	private ContractDao contractDao;
	
	@Autowired
	private ContractTempDao contractTempDao;
	
	@Autowired
	private ContractFeeTempDao contractFeeTempDao;
	
	@Autowired
	private LoanInfoAllDao loanInfoAllDao;
	
	@Autowired
	private LoanBankEditDao loanBankEditDao;
	
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	@Autowired
	private HCAuditResultDao hcAuditResultDao;
	
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private ReconsiderApplyDao reconsiderApplyDao;
	
	/**
	 * 更新合同表
	 * 2015年12月1日
	 * By 张灏 
	 * @param contract
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateContract(Contract contract){
	    contract.preUpdate();
	  	contractDao.updateContract(contract);
	}
	
	/**
	 * 使用合同编号查询合同信息 
	 * 2015年12月1日
	 * By 张灏 
	 * @param contractCode
	 * @return Contract
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Contract findByContractCode(String contractCode){
		return contractDao.findByContractCode(contractCode);
	}
	
	/**
	 * 通过loanCode查询合同信息 
	 * 2015年12月1日
	 * By 张灏 
	 * @param loanCode
	 * @return Contract
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Contract findByLoanCode(String loanCode){
		return contractDao.findByLoanCode(loanCode);
	}
	
	/**
	 * 通过applyId查询合同信息 
	 * 2015年12月1日
	 * By 张灏 
	 * @param applyId
	 * @return Contract
	 */
    @Transactional(readOnly = true,value = "loanTransactionManager")
    public Contract findByApplyId(String applyId){
        return contractDao.findByApplyId(applyId);
    }
    
    /**
     * 新增合同信息 
     * 2015年12月1日
	 * By 张灏 
     * @param contract
     * @return none
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void insertSelective(Contract contract){
	    contract.preInsert();
        contractDao.insertSelective(contract);
    }
	
	/**
	 * 删除合同信息 
	 * 2015年12月1日
	 * By 张灏 
	 * @param loanCode
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void deleteByLoanCode(String loanCode){
       contractDao.deleteByLoanCode(loanCode);
    }
	
	/**
	 * 根据合同编号修改借款信息的出借标识
	 * 2016年2月25日
	 * By 董超
	 * @param contractCode
	 * @return Contract
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateLoanByContractCode(String contractCode){
		contractDao.updateLoanFlagByContractCode(contractCode,"");
		contractDao.updateLoanFlagByContractCode(contractCode,"");
	}
	

    /**
     *查询合同版本号 
     *@author zhanghao
     *@create in 2016年03月25日
     *@param map status  dictFlag
     *@return List<String> 
     * 
     */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public List<String> getContractVersion(Map<String,Object> map){
		
		return contractDao.getContractVersion(map);
	}

	/**
	 *查询特定节点状态下 各个利率下的合同总金额占比情况 
	 *@author zhanghao
	 *@Create In 2016-04-15
	 *@param checkStatus status 
	 *@return List<ContractAmountSummaryEx> 
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<ContractAmountSummaryEx> getSummary(Map<String,String> checkStatus){
	    
	    return contractDao.getSummary(checkStatus);
	}
	/**
	 * 更新借款状态
	 * @param ctrView
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateLoanStatus(ContractBusiView ctrView) {
		contractDao.updateLoanStatus(ctrView);
	}

	/**
	 * 合同退回时修改
	 * @param contract
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateContractForBack(Contract contract) {
		  contract.preUpdate();
		  contractDao.updateContractForBack(contract);
	}
	/**
	 * 合同提交时修改审核人、审核时间
	 * 2016年11月4日
	 * By 申阿伟
	 * @param contract
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateContractAuditing(Contract contract){
	    contract.preUpdate();
	  	contractDao.updateContractAuditing(contract);
	}
	
	/**
	 * 查询合同金额和实放金额
	·* 2017年1月6日
	·* by Huowenlong
	 * @param cac
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public ContractAndContractFee selectContractAmountAndfeePaymentAmount(ContractAndContractFee cac) {
		 return contractDao.selectContractAmountAndfeePaymentAmount(cac);
	}
	
	/**
	 * 联合放款，合同拆分
	·* 2017年2月22日
	·* by Huowenlong
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void splitContract(String contractCode,String loanCode,String backFlag){
		String[] fixs = ContractConstant.SPLIT_FIX;
		for(String fix : fixs){
			ContractTemp temp = new ContractTemp();
			temp.setContractCode(contractCode+fix);
			temp.setIsreceive(YESNO.YES.getCode());
			//判断    确认签署时 是否选择
			ContractTemp contractTemp = contractTempDao.searchByContractCodeOrIsreceive(temp);
			ContractFeeTemp contractFeeTemp = contractFeeTempDao.selectByContractCode(contractCode+fix);
			if(ObjectHelper.isNotEmpty(contractTemp) && ObjectHelper.isNotEmpty(contractFeeTemp)){
				//合同
				Map<String,String> params = new HashMap<String,String>();
				params.put("loan_code", loanCode+fix);
				params.put("apply_id", contractTemp.getApplyId()+fix);
				params.put("dict_repay_method", "0");
				if(StringHelper.isNotEmpty(backFlag)){
					params.put("back_flag", backFlag);
				}
				String[] removeColumns = {"isreceive"};
				copyTeabelToTable("jk","t_jk_contract_temp","t_jk_contract",params,removeColumns,contractTemp.getId());
				//费率
				String[] removeColumns1 = {"isreceive","fee_petition_temp"};
				copyTeabelToTable("jk","t_jk_contract_fee_temp","t_jk_contract_fee",new HashMap<String,String>(),removeColumns1,contractFeeTemp.getId());
				//客户信息
				HashMap applyMap = new HashMap();
				applyMap.put("loanCode", loanCode);
				LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(applyMap);
				Map<String,String> loanCustomerParams = new HashMap<String,String>();
				loanCustomerParams.put("loan_code", loanCode+fix);
				loanCustomerParams.put("apply_id",loanCustomer.getApplyId()+fix);
				copyTeabelToTable("jk","t_jk_loan_customer","t_jk_loan_customer",loanCustomerParams,null,loanCustomer.getId());
				
				//借款信息
				LoanInfo loanInfo = applyLoanInfoDao.selectByLoanCode(applyMap);
				Map<String,String> loanInfoParams = new HashMap<String,String>();
				loanInfoParams.put("loan_code", loanCode + fix);
				loanInfoParams.put("apply_id", loanInfo.getApplyId()+fix);
				loanInfoParams.put("old_loan_code", loanCode);
				loanInfoParams.put("issplit", ContractConstant.ISSPLIT_1);
				//loanInfoParams.put("old_id", loanInfoAll.getId());
				loanInfoParams.put("loan_flag", contractTemp.getChannelFlag());
				loanInfoParams.put("loan_audit_amount", String.valueOf(contractTemp.getAuditAmount()));
				copyTeabelToTable("jk","t_jk_loan_info","t_jk_loan_info",loanInfoParams,null,loanInfo.getId());
				LoanInfoAll updateInfo = new LoanInfoAll();
				updateInfo.setId(loanInfo.getId());
				updateInfo.setIssplit(ContractConstant.ISSPLIT_2);
				loanInfoAllDao.updateByPrimaryKeySelective(updateInfo);
				//复议申请表
				Map raMap = new HashMap();
				raMap.put("loanCode", loanCode);
				ReconsiderApply ra = reconsiderApplyDao.selectByParam(raMap);
				if(ObjectHelper.isNotEmpty(ra)){
					Map<String,String> raParams = new HashMap<String,String>();
					raParams.put("loan_code", loanCode + fix);
					raParams.put("apply_id", ra.getApplyId()+fix);
					copyTeabelToTable("jk","t_jk_reconsider_apply","t_jk_reconsider_apply",raParams,null,ra.getId());
				}
				//账号  
				LoanBankEditEntity lbe = loanBankEditDao.searchByLoanCode(loanCode);
				Map<String,String> loanBankParams = new HashMap<String,String>();
				loanBankParams.put("loan_code", loanCode+fix);
				copyTeabelToTable("jk","t_jk_loan_bank","t_jk_loan_bank",loanBankParams,null,lbe.getId());
				//历史
				List<LoanStatusHis> hisList = loanStatusHisDao.searchAllFieldByLoanCode(loanCode);
				for(LoanStatusHis his : hisList){
					Map<String,String> loanHisParams = new HashMap<String,String>();
					loanHisParams.put("loan_code", loanCode+fix);
					loanHisParams.put("apply_id", his.getApplyId()+fix);
					copyTeabelToTable("jk","t_jk_loan_status_his","t_jk_loan_status_his",loanHisParams,null,his.getId());
				}
				//汇诚审核结果
				HCAuditResultEntity hcr = hcAuditResultDao.searchOneByLoanCode(loanCode);
				Map<String,String> HCAuditParams = new HashMap<String,String>();
				HCAuditParams.put("loan_code", hcr.getLoanCode()+fix);
				HCAuditParams.put("apply_id", hcr.getApplyId()+fix);
				copyTeabelToTable("jk","t_jk_audit_result","t_jk_audit_result",HCAuditParams,null,hcr.getId());
			}
		}
	}
	
	/**
	 * 
	·* 2017年3月15日
	·* by Huowenlong
	 * @param tableSchema
	 * @param sourceTableName
	 * @param targetTableName
	 * @param params
	 * @param removeColumns
	 * @param sourceId
	 */
	public void copyTeabelToTable(String tableSchema,String sourceTableName,String targetTableName,Map<String,String> params,String[] removeColumns ,String sourceId){
		HashMap<String, String> tableParam = new HashMap<String, String>();
		tableParam.put("tableSchema", tableSchema);
		tableParam.put("tableName", sourceTableName);
		String talbeColumns = contractDao.selectTableColumnName(tableParam);
		StringBuffer tableColumnBf = new StringBuffer();
		StringBuffer tableColumnVale = new StringBuffer();
		params.put("id", IdGen.uuid());
		String[] talbeColumnsArray = talbeColumns.split(",");
		if(ObjectHelper.isNotEmpty(removeColumns)){
			for(String  tc: talbeColumnsArray){
				for(String rc : removeColumns){
					if(tc.equals(rc)){
						tc = "";
						continue;
					}
				}
				if(StringHelper.isNotEmpty(tc)){
					tableColumnBf.append(tc);
					tableColumnBf.append(",");
				}
			}
			tableColumnBf.deleteCharAt(tableColumnBf.length()-1);
			talbeColumns = tableColumnBf.toString();
			talbeColumnsArray = talbeColumns.split(",");
		}
		for(String tc : talbeColumnsArray){
			for(String key : params.keySet()){
				if(key.equals(tc)){
					tc = "'" + params.get(key) + "' " + tc;
					continue;
				}
			}
			tableColumnVale.append(tc);
			tableColumnVale.append(",");
		}
		tableColumnVale.deleteCharAt(tableColumnVale.length()-1);
		//insert into ${param1} select ${param2} where id=#{id}
		String sqlColums = targetTableName + " ("+talbeColumns + ") ";
		String sqlValues = tableColumnVale.toString() + " from " + sourceTableName;
		Map<String, String> sqlParam = new HashMap<String, String>();
		sqlParam.put("sqlColums", sqlColums);
		sqlParam.put("sqlValues", sqlValues);
		sqlParam.put("id", sourceId);
		contractDao.insertTableTOTable(sqlParam);
	}
}
