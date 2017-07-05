package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carContract.ex.CarFirstDeferEx;
import com.creditharmony.loan.car.carExtend.ex.CarExportCustomerDataExColumn;
import com.creditharmony.loan.car.common.entity.CarContract;
@LoanBatisDao
public interface CarContractDao  extends CrudDao<CarContract>{
	/**
	 * 根据借款编号获取合同
	 * 2016年2月18日
	 * By 申诗阔
	 * @param loanCode
	 * @return 合同
	 */
    public CarContract selectByLoanCode(String loanCode);
    /**
	 * 根据查询条件获得首次展期列表
	 * 2016年3月4日
	 * By 甘泉
	 * @param CarFirstDeferEx
	 * @return CarFirstDeferEx
	 */
    public List<CarFirstDeferEx> selectDefer(PageBounds pageBounds,CarFirstDeferEx carFirstDeferEx);
    
    /**
     * 通过车借借款编码生成展期合同编号
     * 2016年3月9日
     * By 申诗阔
     * @param loanCode
     * @return
     */
    public String getExtendContractCode(Map<String, String> map);
    
    /**
     * 根据借款编码获取历史展期信息
	 * 2016年3月9日
	 * By 申诗阔
	 * @param loanCode
	 * @return 
	 */
    public List<CarContract> getExtendContractByLoanCode(String loanCode);
    /**
     * 根据合同编号查询合同有无
	 * 2016年3月11日
	 * By 安子帅
	 * @param loanCode
	 * @return 
	 */
    public List<CarContract> getExtendByContractCode(String contractCode);
    /**
     * 通过展期loancode得到本次展期和紧邻上次 这两次借款的合同金额的差值，即降额
     * 2016年4月29日
     * By 申诗阔
     * @param loanCode
     * @return
     */
    public Double calculateSubContractAmount(String loanCode);
    
    /**
     * 通过展期loancode得到紧邻上次合同金额
     * 2016年5月3日
     * By 申诗阔
     * @param loanCode
     * @return
     */
    public CarContract calculateLastContractAmount(String loanCode);
    
    /**
     * 通过展期loancode得到原车借合同总费率
     * 2016年5月8日
     * By 申诗阔
     * @param loanCode
     * @return
     */
    public Double selectOriginalGrossRate(String loanCode);
    
    /**
     * 通过合同编号获取合同信息
     * 2016年5月11日
     * By 申诗阔
     * @param contractCode
     * @return
     */
    public CarContract selectByContractCode(String contractCode);
    
    public List<CarExportCustomerDataExColumn> getContractCustomList(List<String> contractCodeList);
    
    /**
     * 获取已展期的汇诚审批金额
     * 2016年5月26日
     * By 申诗阔
     * @return
     */
    public CarContract selectExByLoanCode(String loanCode);
    
    /**
     * 通过合同编号获取合同审核导出所需字段
     * 2016年5月28日
     * By 申诗阔
     * @param contractCode
     * @return
     */
    public CarExportCustomerDataExColumn getContractCustomColumnByContractCode(String contractCode);
    
    public int selectExtendNumByContractCode(String contractCode);
    
    /**
     * 通过loancode 获取展期次数
     * 2016年6月16日
     * By gezhichao
     * @param loancode
     * @return
     */
    public int  getExtendCountByLoanCode(String loanCode);
    
    /**
     * 通过loancode 获取展期次数(包括未签章)
     * 2016年6月16日
     * By gezhichao
     * @param loancode
     * @return
     */
    public int  getExtendCountByLoanCodeSign(String loanCode);
    
	public Double getContractAmountByLoanCode(String loanCode);
	
	/**
	 * 通过 合同id list 删除合同信息
	 * 2016年6月22日
	 * By 申诗阔
	 * @param list id 的 list
	 */
	public void deleteCarContractBatchByIds(List<String> list);
    
}