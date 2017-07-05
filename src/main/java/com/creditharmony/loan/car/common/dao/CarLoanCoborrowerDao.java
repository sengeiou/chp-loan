package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
@LoanBatisDao
public interface CarLoanCoborrowerDao extends CrudDao<CarLoanCoborrower>{

	/**
	 * 新增共借人基本信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carLoanCoborrower
	 */
    public void insertCarLoanCoborrower(CarLoanCoborrower carLoanCoborrower);
    
    
    /**
	 * 根据借款编码和类型（这里特指共借人）查询共借人信息，包含共借人联系人信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param map
	 */
	public List<CarLoanCoborrower> selectByLoanCodeAndLoanType(Map<String, String> map);
	
	 /**
	 * 根据借款编码查询共借人信息（不包括共借人联系人信息）
	 * 2016年3月15日
	 * By 申诗阔
	 * @param loanCode
	 */
	public List<CarLoanCoborrower> selectByLoanCode(String loanCode);
	/**
	 * 根据借款编码查询共借人信息（不包括共借人联系人信息），不加转换
	 * 2016年3月15日
	 * By 申诗阔
	 * @param loanCode
	 */
	public List<CarLoanCoborrower> selectByLoanCodeNoConvers(String loanCode);
	
	/**
	 * 修改共借人信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carLoanCoborrower
	 */
	public int update(CarLoanCoborrower carLoanCoborrower);
	
	/**
	 * 查询共借人姓名
	 */
	public CarLoanCoborrower selectName(String loanCode);


	public void deleteCoBorrowAndContractPerson(String loanCode);

	/**
	 * 删除共借人 2016/5/3
	 * @param loanCode
	 * By 高远
	 */
	public void deleteCoboByLoanCode(String loanCode);

	/**
	 * 删除共借人 2016/5/3
	 * @param loanCode
	 * By 高远
	 */
	public void deleteByIds(Map<String, List<String>> map);
	
	/**
     * 根据ID，更新无纸化相关信息
     * 2016年5月9日
     * By gezhichao
     * @param param
     * @return none
     */
    public void updatePaperlessMessage(Map<String,Object> param);
	
	
}