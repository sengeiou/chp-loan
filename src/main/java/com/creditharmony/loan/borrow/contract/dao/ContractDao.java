package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractAndContractFee;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractAmountSummaryEx;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;

/**
 * 合同dao层
 * @Class Name ContractDao
 * @author 张灏 
 * @Create In 2015年12月1日
 */
@LoanBatisDao
public interface ContractDao extends CrudDao<Contract>{
   
	/**
	 * 费率查询 
	 * 2015年12月1日
	 * By 张灏 
	 * @param param
	 * @return List
	 */
   public List<Map<String,String>> findProductInfo(Map<String,Object> param);
   
   /**
    * 根据合同编号查询 
    * 2015年12月1日
    * By 张灏 
    * @param contarctCode 合同编号
    * @return List<Contract>
    */
   public List<Contract> findApplyBycontractCode(String contarctCode);

   /**
    * 更新合同表
    * 2015年12月1日
    * By 张灏 
    * @param contract
    * @return none
    */
    public void updateContract(Contract contract);
    
    /**
     * 查询合同信息
     * 2015年12月1日
     * By 张灏 
     * @param contractCode
     * @return Contract
     */
    public Contract findByContractCode(String contractCode);
    
    /**
     * 查询合同信息 
     * 2015年12月1日
     * By 张灏 
     * @param loanCode
     * @return Contract
     */
    public Contract findByLoanCode(String loanCode);

    /**
     * 查询合同信息 
     * 2015年12月1日
     * By 张灏 
     * @param applyId
     * @return Contract
     */
    public Contract findByApplyId(String applyId);
    
    /**
     * 新增合同信息 
     * 2015年12月1日
     * By 张灏 
     * @param contract
     * @return none
     */
    public void insertSelective(Contract contract);
    
    /**
     * 删除合同信息 
     * 2015年12月1日
     * By 张灏 
     * @param loanCode
     * @return none
     */
    public void deleteByLoanCode(String loanCode);
    
    /**
     *复议流程发起时将信借流程ID更改为复议流程ID 
     *@Create In 2016年02月22日
     *@author zhanghao
     *@param  param
     *@return none 
     * 
     */
    public void updateApplyIdByOldApplyId(Map<String,String> param);
    /**
	 * 根据合同编号修改借款信息的出借标识
	 * 2016年2月25日
	 * By 董超
	 * @param contractCode,loanFlag
	 */
    public void updateLoanFlagByContractCode(String contractCode,String loanFlag);
    
    /**
	 * 根据合同编号修改借款信息的状态
	 * 2016年2月25日
	 * By 董超
	 * @param contractCode,status
	 */
    public void updateLoanStatusByContractCode(String contractCode,String status);
    
    /**
     *查询合同版本号 
     *@author zhanghao
     *@create in 2016年03月25日
     *@param map status  dictFlag
     *@return List<String> 
     * 
     */
    public List<String> getContractVersion(Map<String,Object> map);
    
    /**
     *查询特定节点状态下 各个利率下的合同总金额占比情况 
     *@author zhanghao
     *@Create In 2016-04-15
     *@param checkStatus status 
     *@return List<ContractAmountSummaryEx> 
     */
    public List<ContractAmountSummaryEx> getSummary(Map<String,String> checkStatus);

    /**
     * 更新借款状态
     * @param ctrView
     */
	public void updateLoanStatus(ContractBusiView ctrView);

	public void updateContractForBack(Contract contract);

	public int getHiscontract(String loanCode);
	
	/**
	 * 合同提交时修改审核人、审核时间
	 * By 申阿伟
	 * @param contract
	 */
	public void updateContractAuditing(Contract contract);
	
	/**
	 * 查询合同金额和实放金额
	·* 2017年1月6日
	·* by Huowenlong
	 * @param cac
	 * @return
	 */
	public ContractAndContractFee selectContractAmountAndfeePaymentAmount(ContractAndContractFee cac);
   
	/**
	 * 修改出借人
	 * By WJJ
	 * @param map
	 */
	public void updateLender(Map Map);
	
	/**
	 * 根据oldloancode查询Contract
	·* 2017年2月23日
	·* by Huowenlong
	 * @param oldLoanCode
	 * @return
	 */
	public List<Contract> searchContractByoldLoanCodeAndIssplit(Map map);
	
	 /**
     * 根据合同状态查询信息
     * 2017年02月22日
     * By 申阿伟
     * @param contractCode
     * @return List<Contract>
     */
    public List<Contract> findByContractStatus(Contract contract);

	public void updateContractTemp(Contract contract);
	
	/**
	 * 查询列名
	·* 2017年3月14日
	·* by Huowenlong
	 * @param map
	 * @return
	 */
	public String selectTableColumnName(Map map);
	
	public int insertTableTOTable(Map<String,String> map);

	/**
	 * 添加推送档案数据
	 * By WJJ
	 * @param map
	 */
	public void addArchives(Map Map);
	
}
