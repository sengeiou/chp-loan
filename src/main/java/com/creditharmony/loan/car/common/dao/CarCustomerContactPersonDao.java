package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
@LoanBatisDao
public interface CarCustomerContactPersonDao extends CrudDao<CarCustomerContactPerson>{
	/**
	 * 新增联系人基本信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carCustomerContactPerson
	 */
    public void insertCarCustomerContactPerson(CarCustomerContactPerson carCustomerContactPerson);
    /**
   	 * 查询主借人联系人信息
   	 * 2016年2月17日
   	 * By 甘泉   
   	 * @param CarCustomerContactPerson
   	 */
   	public List<CarCustomerContactPerson> selectCarCustomerContactPerson(String loanCode);
   	/**
   	 * 查询共借人联系人信息
   	 * 2016年2月17日
   	 * By 甘泉   
   	 * @param CarCustomerContactPerson
   	 */
   	public List<CarCustomerContactPerson> selectByCoborrower(String rCustomerCoborrowerCode);
   	/**
	 * 修改联系人信息
   	 * 2016年2月16日
   	 * By 安子帅
   	 * @param CarCustomerContactPerson
   	 */
	public int update(CarCustomerContactPerson carCustomerContactPerson);
	
	/**
	 * 根據傳入id list刪除联系人信息
   	 * 2016年3月15日
   	 * By 甘泉
   	 * @param map
   	 */
	public void deleteByIds(Map<String, List<String>> map);
	
	/**
	 * 根据loanCode删除全部联系人（主借人、共借人的联系人）
	 * 2016年5月13日
	 * By 申诗阔
	 * @param loanCode
	 */
	public void deleteContractPerson(String loanCode);
	
	/**
	 * 删除主借人联系人
	 * 2016年5月13日
	 * By 申诗阔
	 * @param loanCode
	 */
	public void deleteMainContractPerson(String loanCode);
	
	/**
	 * 根据传入借款编码和借款人类型（主借人、共借人）来删除联系人
	 * 2016年5月13日
	 * By 申诗阔
	 * @param carCustomerContactPerson
	 */
	public void deleteContractPersonByLoanCode(CarCustomerContactPerson carCustomerContactPerson);
	public void deleteContractPersonByCoboCode(String coboCode);
	
	/**
	 * 删除共借人联系人
	 * 2016年5月13日
	 * By 申诗阔
	 * @param loanCode
	 */
	public void deleteCoborrowerContractPerson(String loanCode);
	/**
	 * 删除展期共借人联系人
	 * 2016年6月17日
	 * By 高远
	 * @param loanCode
	 */
	public void deleteByContactPersonId(String getrCustomerCoborrowerCode);
	public List<CarCustomerContactPerson> selectCarContactPersonByLoanCode(
			String centerLoanCode);
	
}