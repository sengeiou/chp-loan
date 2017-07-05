package com.creditharmony.loan.car.carApply.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.config.Global;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carApply.ex.CreditJson;
import com.creditharmony.loan.car.common.dao.CarChangerInfoDao;
import com.creditharmony.loan.car.common.dao.CarCustomerContactPersonDao;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;

/**
 * 共借人基本信息
 * @Class Name CarLoanCoborrowerService
 * @author 安子帅
 * @Create In 2016年2月16日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarLoanCoborrowerService extends CoreManager<CarLoanCoborrowerDao, CarLoanCoborrower> {
	@Autowired
	private CarLoanCoborrowerDao carLoanCoborrowerDao;
	@Autowired
	private CarCustomerContactPersonDao carCustomerContactPersonDao;
	
	@Autowired
	private CarChangerInfoDao carChangerInfoDao;

	/**
	 * 保存共借人基本信息 2016年2月16日 
	 * By 安子帅
	 * @param CarLoanCoborrower
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarLoanCoborrower(CarLoanCoborrower carLoanCoborrower) {
		carLoanCoborrowerDao.insert(carLoanCoborrower);
	}
	/**
	 * 根据借款编码和类型（这里特指共借人）查询共借人信息，包含共借人联系人信息 2016年2月16日 
	 * By 安子帅
	 * @param String
	 */
	public List<CarLoanCoborrower> selectCarLoanCoborrower(String loanCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("loanCode", loanCode);
		map.put("loanType", LoanManFlag.COBORROWE_LOAN.getCode());
		return carLoanCoborrowerDao.selectByLoanCodeAndLoanType(map);
	}
	/**
	 * 根据借款编码查询共借人信息（不包括共借人联系人信息） 2016年3月15日 
	 * By 申诗阔
	 * @param String
	 */
	public List<CarLoanCoborrower> selectCoborrowerByLoanCode(String loanCode) {
		return carLoanCoborrowerDao.selectByLoanCode(loanCode);
	}
	/**
	 * 修改共借人基本信息 2016年2月16日 
	 * By 安子帅
	 * @param CarLoanCoborrower
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(CarLoanCoborrower carLoanCoborrower) {
		carLoanCoborrowerDao.update(carLoanCoborrower);
	}
	
	public CarLoanCoborrower selectName(String loanCode){
		return carLoanCoborrowerDao.selectName(loanCode);
	}
	/**
	 * 根据借款编码查询共借人信息（不包括共借人联系人信息） 2016年3月15日 ，不加转换
	 * By 申诗阔
	 * @param String
	 */
	public List<CarLoanCoborrower> selectByLoanCodeNoConvers(String loanCode) {
		return carLoanCoborrowerDao.selectByLoanCodeNoConvers(loanCode);
	}
	/**
	 * 根据借款编码删除共借人信息（包括共借人联系人信息） 2016年4月13日 
	 * By 高远
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteCoBorrowAndContractPerson(String coboCode) {
		 carCustomerContactPersonDao.deleteContractPersonByCoboCode(coboCode);
	}
	/**
	 * 根据loancode删除共借人信息 2016年5月3日
	 * @param loanCode
	 * By 高远
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteCoboByLoanCode(String loanCode) {
		carLoanCoborrowerDao.deleteCoboByLoanCode(loanCode);
	}
	/**
	 * 删除共借人 信息
	 * @param map
	 * By 高远
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteByIds(Map<String, List<String>> map) {
		carLoanCoborrowerDao.deleteByIds(map);
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public List<CarLoanCoborrower> carLoanCoborrower(CreditJson creditJson) {
		CarLoanCoborrower carLoanCoborrower1 = creditJson.getCarLoanCoborrower().get(0);
		String newLoanCode = carLoanCoborrower1.getNewLoanCode();
		List<CarLoanCoborrower> newCobos = carLoanCoborrowerDao.selectByLoanCode(newLoanCode);
		if(null==newCobos||newCobos.size()<=0){
			for (CarLoanCoborrower carLoanCoborrower : creditJson.getCarLoanCoborrower()) {
				carLoanCoborrower.setLoanCode(newLoanCode);
				carLoanCoborrower.setCaptchaIfConfirm("0");
				carLoanCoborrower.setAppSignFlag(null);
				carLoanCoborrower.setComposePhotoId(null);
				carLoanCoborrower.setConfirmTimeout(null);
				carLoanCoborrower.setCurPlotId(null);
				carLoanCoborrower.setComposePhotoId(null);
				carLoanCoborrower.setIdValidFlag(null);
				carLoanCoborrower.setCustomerPin(null);
				if(Global.NO.equals(carLoanCoborrower.getHaveOtherIncome())){
					carLoanCoborrower.setOtherIncome(BigDecimal.ZERO);
				}
				UUID uuid = UUID.randomUUID();
				String idg=uuid.toString().replace("-", ""); 
				carLoanCoborrower.setId(idg);
				saveCarLoanCoborrower(carLoanCoborrower);//update(carLoanCoborrower);
				List<CarCustomerContactPerson> carCustomerContactPer =  carLoanCoborrower.getCarCustomerContactPerson();
				CarCustomerContactPerson carCustomerContactPersonParam = new CarCustomerContactPerson();
				carCustomerContactPersonParam.setLoanCode(carLoanCoborrower.getLoanCode());
				carCustomerContactPersonParam.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
				//carCustomerContactPersonDao.deleteContractPersonByLoanCode(carCustomerContactPersonParam);
				carCustomerContactPersonDao.deleteByContactPersonId(carLoanCoborrower.getId());
				if(null != carCustomerContactPer ){
					for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPer) {
						carCustomerContactPerson.setrCustomerCoborrowerCode(idg);
						carCustomerContactPerson.setLoanCode(newLoanCode);
						carCustomerContactPerson.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
						carCustomerContactPerson.preInsert();
						carCustomerContactPersonDao.insert(carCustomerContactPerson); 
					}
				}
			}
		}else{
			for (CarLoanCoborrower carLoanCoborrower : creditJson.getCarLoanCoborrower()) {
				String id = carLoanCoborrower.getId();
				if ("".equals(id) || id == null) {
					UUID uuid = UUID.randomUUID();
					String idg=uuid.toString().replace("-", ""); 
					carLoanCoborrower.setId(idg);
					saveCarLoanCoborrower(carLoanCoborrower);//update(carLoanCoborrower);
					List<CarCustomerContactPerson> carCustomerContactPer =  carLoanCoborrower.getCarCustomerContactPerson();
					CarCustomerContactPerson carCustomerContactPersonParam = new CarCustomerContactPerson();
					carCustomerContactPersonParam.setLoanCode(carLoanCoborrower.getLoanCode());
					carCustomerContactPersonParam.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
					//carCustomerContactPersonDao.deleteContractPersonByLoanCode(carCustomerContactPersonParam);
					carCustomerContactPersonDao.deleteByContactPersonId(carLoanCoborrower.getId());
					if(null != carCustomerContactPer ){
						for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPer) {
							carCustomerContactPerson.setrCustomerCoborrowerCode(idg);
							carCustomerContactPerson.setLoanCode(newLoanCode);
							carCustomerContactPerson.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
							carCustomerContactPerson.preInsert();
							carCustomerContactPersonDao.insert(carCustomerContactPerson); 
						}
					}
				} else {
					update(carLoanCoborrower);
					String idg = carLoanCoborrower.getId();
					List<CarCustomerContactPerson> carCustomerContactPerX =  carLoanCoborrower.getCarCustomerContactPerson();
					CarCustomerContactPerson carCustomerContactPersonParam = new CarCustomerContactPerson();
					carCustomerContactPersonParam.setLoanCode(carLoanCoborrower.getLoanCode());
					carCustomerContactPersonParam.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
					carCustomerContactPersonDao.deleteByContactPersonId(carLoanCoborrower.getId());
					if(null != carCustomerContactPerX){
						for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPerX) {
							carCustomerContactPerson.setrCustomerCoborrowerCode(idg);
							carCustomerContactPerson.setLoanCode(newLoanCode);
							carCustomerContactPerson.preInsert();
							carCustomerContactPerson.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
							carCustomerContactPersonDao.insert(carCustomerContactPerson);
						}
					}	
				}
				
			}
		}
		newCobos = carLoanCoborrowerDao.selectByLoanCode(newLoanCode);
		return newCobos;
	}
}
