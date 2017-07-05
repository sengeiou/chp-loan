package com.creditharmony.loan.car.carApply.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.config.Global;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carApply.ex.CreditJson;
import com.creditharmony.loan.car.carApply.ex.carCreditJson;
import com.creditharmony.loan.car.common.dao.CarCustomerContactPersonDao;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.google.common.collect.Lists;
/**
 * 联系人信息
 * @Class Name CarCustomerContactPersonService
 * @author 安子帅
 * @Create In 2016年2月16日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCustomerContactPersonService extends CoreManager<CarCustomerContactPersonDao, CarCustomerContactPerson> {
	@Autowired
	private CarCustomerContactPersonDao carCustomerContactPersonDao;
	
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	
	@Autowired
	private CarLoanCoborrowerDao carLoanCoborrowerDao;

	/**
	 * 保存联系人信息 2016年2月16日
	 * By 安子帅
	 * @param CarCustomerContactPerson
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarCustomerContactPerson(CarCustomerContactPerson carCustomerContactPerson) {
		carCustomerContactPersonDao.insert(carCustomerContactPerson);
	}
	/**
	 * 查询主借人联系人信息 2016年2月16日 
	 * By 安子帅
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public List<CarCustomerContactPerson> selectCarContactPerson(String loanCode) {
		return carCustomerContactPersonDao.selectCarCustomerContactPerson(loanCode);
	}
	/**
	 * 查询共借人联系人信息 2016年2月17日 
	 * By 甘泉
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public List<CarCustomerContactPerson> selectByCoborrower(String rCustomerCoborrowerCode) {
		return carCustomerContactPersonDao.selectByCoborrower(rCustomerCoborrowerCode);
	}
	/**
	 * 修改联系人信息 2016年2月16日 
	 * By 安子帅
	 * @param CarLoanCoborrower
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(CarCustomerContactPerson carCustomerContactPerson) {
		carCustomerContactPersonDao.update(carCustomerContactPerson);
	}
	/**
	 * 根据ids删除联系人信息 2016年3月15日 
	 * By 甘泉
	 * @param CarLoanCoborrower
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteByIds(Map<String, List<String>> map){
		carCustomerContactPersonDao.deleteByIds(map);
	}
	/**
	 * 根据loancode删除主借人联系人 2016年4月27日
	 * By 高远
	 * @param loanCode
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteMainContractPerson(String loanCode) {
		carCustomerContactPersonDao.deleteMainContractPerson(loanCode);
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void contactSave(carCreditJson creditJson,String loanCode) {
/*		List<String> list = creditJson.getDelArray();
		if(list != null && list.size() > 0){
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("delList", list);
			deleteByIds(map);
		}
		for (CarCustomerContactPerson carCustomerContactPerson : creditJson.getCarCustomerContactPerson()) {
			carCustomerContactPerson.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
			carCustomerContactPerson.setLoanCode(loanCode);
			carCustomerContactPerson.preInsert();
			saveCarCustomerContactPerson(carCustomerContactPerson);
		}*/
		carCustomerContactPersonDao.deleteMainContractPerson(loanCode);
		for (CarCustomerContactPerson carCustomerContactPerson : creditJson.getCarCustomerContactPerson()) {
			
			carCustomerContactPerson.setIsNewRecord(false);
			carCustomerContactPerson.setLoanCode(loanCode);
			carCustomerContactPerson.preInsert();
			carCustomerContactPerson.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
			carCustomerContactPersonDao.insert(carCustomerContactPerson);
		}
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public List<CarLoanCoborrower> coborrowerSave(CreditJson creditJson,String myLoanCode) {
		
		for (CarLoanCoborrower carLoanCoborrower : creditJson.getCarLoanCoborrower()) {
			if(Global.NO.equals(carLoanCoborrower.getHaveOtherIncome())){
				carLoanCoborrower.setOtherIncome(BigDecimal.ZERO);
			}
			if(carLoanCoborrower != null){
				carLoanCoborrower.preUpdate();
				carLoanCoborrowerService.update(carLoanCoborrower);
				List<CarCustomerContactPerson> carCustomerContactPer =  carLoanCoborrower.getCarCustomerContactPerson();
				if(null!=carCustomerContactPer){
					carCustomerContactPersonDao.deleteContractPersonByCoboCode(carLoanCoborrower.getId());
					for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPer) {
						carCustomerContactPerson.setrCustomerCoborrowerCode(carLoanCoborrower.getId());
						carCustomerContactPerson.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
						carCustomerContactPerson.setLoanCode(myLoanCode);
						carCustomerContactPerson.preInsert();
						saveCarCustomerContactPerson(carCustomerContactPerson);
					}	
			     }
			}
		}
		 List<CarLoanCoborrower> newCobos = carLoanCoborrowerDao.selectByLoanCode(myLoanCode);
		return newCobos;
	}
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public List<CarCustomerContactPerson> selectCarContactPersonByLoanCode(
			String centerLoanCode) {
		return carCustomerContactPersonDao.selectCarContactPersonByLoanCode(centerLoanCode);
	}
}
