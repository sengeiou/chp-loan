package com.creditharmony.loan.car.carExtend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.utils.ApplyIdUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.car.carApply.ex.CreditJson;
import com.creditharmony.loan.car.carApply.ex.carCreditJson;
import com.creditharmony.loan.car.common.dao.CarApplicationInterviewInfoDao;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarCustomerBaseDao;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;
import com.creditharmony.loan.car.common.dao.CarCustomerContactPersonDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.ex.CarLoanContractEx;
import com.creditharmony.loan.common.service.NumberMasterService;

/**
 * 展期补录
 * @Class Name CarExtendHistoryApplyService
 * @author 申诗阔
 * @Create In 2016年5月12日
 */
@Service
public class CarExtendHistoryApplyService {
   
	@Autowired
	private CarContractDao carContractDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	@Autowired
	private CarApplicationInterviewInfoDao carApplicationInterviewInfoDao;
	@Autowired
	private CarCustomerDao carCustomerDao;
	@Autowired
	private CarCustomerBaseDao carCustomerBaseDao;
	@Autowired
	private CarCustomerContactPersonDao carCustomerContactPersonDao;
	@Autowired
	private CarLoanCoborrowerDao carLoanCoborrowerDao;
	@Autowired
	private CarCustomerConsultationDao carCustomerConsultationDao;
	
	@Autowired
	private NumberMasterService numberService;
	
	/**
	 * 合同保存
	 * 2016年5月12日
	 * By 申诗阔
	 * @param carLoanContractEx
	 * @param loanCode
	 * @param newLoanCode
	 * @return map
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Map<String, String> saveExtendHistoryContracts(CarLoanContractEx carLoanContractEx, String loanCode, String newLoanCode) {
		Map<String, String> map = new HashMap<String, String>();
		List<CarContract> carContracts = carLoanContractEx.getCarContract();
		List<String> delList = carLoanContractEx.getDelArray();
		if (null != delList && delList.size() > 0) {
			carLoanInfoDao.deleteCarLoanInfoBatchByContractIds(delList);
			carContractDao.deleteCarContractBatchByIds(delList);
		}
		String lastLoanInfoId = null;
		CarLoanInfo carLoanInfocar = null;
		if (StringUtils.isNotEmpty(loanCode)) {
			carLoanInfocar = carLoanInfoDao.selectByLoanCode(loanCode);
		}
		for (int i = 0; i < carContracts.size(); i++) {
			if (i == 0) {
				// 第一条记录保存，需要判断是否有保存车借
				//即：loanCode不为空，且通过loanCode获取记录不为空，则为update；若loanCode为空，或者loanCode不为空，通过loanCode获取为空(即第一条保存进去，再切换过来又删除)，则都是新增
				if (carLoanInfocar != null && carLoanInfocar.getLoanApplyAmount() != null) { // 有车借
					if (StringUtils.isNotEmpty(carContracts.get(i).getId())) { // id不为空，则为update
						carContracts.get(i).preUpdate();
						carContracts.get(i).setContractCode(carContracts.get(i).getContractCode() + "-" + carContracts.get(i).getNumCount());
						carContractDao.update(carContracts.get(i));
						lastLoanInfoId = carContracts.get(i).getLoanInfoId();
					} else {
						String loanCodeGe2 = numberService.getLoanNumber(SerialNoType.LOAN);
						CarLoanInfo carLoanInfo2 = new CarLoanInfo();
						carLoanInfo2.preInsert();
						carLoanInfo2.setLoanCode(loanCodeGe2);
						carLoanInfo2.setLoanAdditionalApplyid(carLoanInfoDao.selectNearestByLoanCode(loanCode).getId());
						carLoanInfo2.setIsExtension(YESNO.YES.getCode());
						carLoanInfo2.setLoanRawcode(loanCode);
						Date da2 = new Date();
						carLoanInfo2.setCustomerIntoTime(da2);
						carLoanInfo2.setDictLoanStatus(CarLoanStatus.REPAYMENT_APPLICATION.getCode());
						String applyId2 = ApplyIdUtils.builderApplyId("HJ0004");
						carLoanInfo2.setApplyId(applyId2);
						carLoanInfoDao.insertCarLoanInfo(carLoanInfo2);
						lastLoanInfoId = carLoanInfo2.getId();
						
						CarContract carContract = carContracts.get(i);
						String firstLetter = carContract.getContractCode().substring(0, 1);
						if ("车".equals(firstLetter)) {
							firstLetter = CarLoanProductType.TRANSFER.getCode();
						} else if ("G".equals(firstLetter)) {
							firstLetter = CarLoanProductType.GPS.getCode();
						} else if ("质".equals(firstLetter)) {
							firstLetter = CarLoanProductType.PLEDGE.getCode();
						} else {
							firstLetter = "";
						}
						carContract.setProductType(firstLetter);
						carContract.setSignFlag(YESNO.YES.getCode());
						carContract.setAuditAmount(carContract.getContractAmount());
						carContracts.get(i).setContractCode(carContracts.get(i).getContractCode() + "-" + carContracts.get(i).getNumCount());
						carContract.setLoanCode(loanCodeGe2);
						carContract.preInsert();
						carContractDao.insert(carContract);
					}
				} else { // 无车借
					if (StringUtils.isNotEmpty(carContracts.get(i).getId())) {
						carContracts.get(i).preUpdate();
						carContractDao.update(carContracts.get(i));
						lastLoanInfoId = carContracts.get(i).getLoanInfoId();
					} else { // 新增车借
						String loanCodeGe2 = numberService.getLoanNumber(SerialNoType.LOAN);
						CarLoanInfo carLoanInfo2 = new CarLoanInfo();
						carLoanInfo2.preInsert();
						carLoanInfo2.setLoanCode(loanCodeGe2);
						Date da2 = new Date();
						carLoanInfo2.setCustomerIntoTime(da2);
						carLoanInfo2.setDictLoanStatus(CarLoanStatus.REPAYMENT_IN.getCode());
						String applyId2 = ApplyIdUtils.builderApplyId("HJ0004");
						carLoanInfo2.setApplyId(applyId2);
						carLoanInfoDao.insertCarLoanInfo(carLoanInfo2);
						lastLoanInfoId = carLoanInfo2.getId();
						loanCode = loanCodeGe2;
						
						CarContract carContract = carContracts.get(i);
						String firstLetter = carContract.getContractCode().substring(0, 1);
						if ("车".equals(firstLetter)) {
							firstLetter = CarLoanProductType.TRANSFER.getCode();
						} else if ("G".equals(firstLetter)) {
							firstLetter = CarLoanProductType.GPS.getCode();
						} else if ("质".equals(firstLetter)) {
							firstLetter = CarLoanProductType.PLEDGE.getCode();
						} else {
							firstLetter = "";
						}
						carContract.setProductType(firstLetter);
						carContract.setSignFlag(YESNO.YES.getCode());
						carContract.setAuditAmount(carContract.getContractAmount());
						carContract.setLoanCode(loanCodeGe2);
						carContract.preInsert();
						carContractDao.insert(carContract);
					}
				}
			} else {
				if (StringUtils.isNotEmpty(carContracts.get(i).getId())) {
					carContracts.get(i).preUpdate();
					carContracts.get(i).setContractCode(carContracts.get(i).getContractCode() + "-" + carContracts.get(i).getNumCount());
					carContractDao.update(carContracts.get(i));
					lastLoanInfoId = carContracts.get(i).getLoanInfoId();
				} else { // 新增
					String loanCodeGe2 = numberService.getLoanNumber(SerialNoType.LOAN);
					CarLoanInfo carLoanInfo2 = new CarLoanInfo();
					carLoanInfo2.preInsert();
					carLoanInfo2.setLoanCode(loanCodeGe2);
					Date da2 = new Date();
					carLoanInfo2.setCustomerIntoTime(da2);
					carLoanInfo2.setLoanAdditionalApplyid(lastLoanInfoId);
					carLoanInfo2.setLoanRawcode(loanCode);
					carLoanInfo2.setIsExtension(YESNO.YES.getCode());
					carLoanInfo2.setDictLoanStatus(CarLoanStatus.REPAYMENT_APPLICATION.getCode());
					String applyId2 = ApplyIdUtils.builderApplyId("HJ0004");
					carLoanInfo2.setApplyId(applyId2);
					carLoanInfoDao.insertCarLoanInfo(carLoanInfo2);
					lastLoanInfoId = carLoanInfo2.getId();
					
					CarContract carContract = carContracts.get(i);
					String firstLetter = carContract.getContractCode().substring(0, 1);
					if ("车".equals(firstLetter)) {
						firstLetter = CarLoanProductType.TRANSFER.getCode();
					} else if ("G".equals(firstLetter)) {
						firstLetter = CarLoanProductType.GPS.getCode();
					} else if ("质".equals(firstLetter)) {
						firstLetter = CarLoanProductType.PLEDGE.getCode();
					} else {
						firstLetter = "";
					}
					carContract.setProductType(firstLetter);
					carContract.setAuditAmount(carContract.getContractAmount());
					carContract.setSignFlag(YESNO.YES.getCode());
					carContract.setContractCode(carContract.getContractCode() + "-" + carContract.getNumCount());
					carContract.setLoanCode(loanCodeGe2);
					carContract.preInsert();
					carContractDao.insert(carContract);
				}
			}
		}
		CarLoanInfo carLoanInfo = carLoanInfoDao.selectExtendHistoryByLoanRaw(loanCode);
		if (carLoanInfo != null) {
			newLoanCode = carLoanInfo.getLoanCode();
			CarLoanInfo car = new CarLoanInfo();
			car.setLoanCode(newLoanCode);
			car.preUpdate();
			car.setLoanAdditionalApplyid(lastLoanInfoId);
			carLoanInfoDao.update(car);
		} else {
			String loanCodeLa = numberService.getLoanNumber(SerialNoType.LOAN);
			newLoanCode = loanCodeLa;
			CarLoanInfo carLoanInfo2 = new CarLoanInfo();
			carLoanInfo2.setLoanCode(loanCodeLa);
			carLoanInfo2.setLoanAdditionalApplyid(lastLoanInfoId);
			carLoanInfo2.setLoanRawcode(loanCode);
			carLoanInfo2.setIsExtension(YESNO.YES.getCode());
			carLoanInfo2.setCustomerIntoTime(new Date());
			carLoanInfo2.setDictLoanStatus("0");
			carLoanInfo2.preInsert();
			carLoanInfoDao.insertCarLoanInfo(carLoanInfo2);
		}
		
		map.put("newLoanCode", newLoanCode);
		map.put("loanCode", loanCode);
		return map;
	}

	/**
	 * 借款信息
	 * 2016年5月12日
	 * By 申诗阔
	 * @param carLoanInfo
	 * @param newLoanCode
	 * @param carApplicationInterviewInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveExtendHistoryLoanInfo(CarLoanInfo carLoanInfo, String newLoanCode, CarApplicationInterviewInfo carApplicationInterviewInfo){
		carLoanInfoDao.update(carLoanInfo);
		if (carApplicationInterviewInfoDao.selectByLoanCode(newLoanCode) == null) {
			carApplicationInterviewInfoDao.insert(carApplicationInterviewInfo);
		} else {
			carApplicationInterviewInfoDao.update(carApplicationInterviewInfo);
		}
	}
	
	/**
	 * 个人资料
	 * 2016年5月12日
	 * By 申诗阔
	 * @param carLoanInfo
	 * @param loanCode
	 * @param newLoanCode
	 * @param carCustomer
	 * @param carCustomerBase
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveExtendHistoryCarCustomer(CarLoanInfo carLoanInfo, String loanCode, String newLoanCode, CarCustomer carCustomer, CarCustomerBase carCustomerBase) {
		if (carCustomerDao.selectByloanCode(newLoanCode) == null) {
			// 生成客户编码
		 	String customerCode = numberService.getCustomerNumber(SerialNoType.CUSTOMER);
			carCustomerBase.setCustomerCode(customerCode);
			carLoanInfo.setCustomerCode(customerCode);
			carCustomer.setCustomerCode(customerCode);
			carCustomer.setLoanCode(newLoanCode);
			carCustomer.setCustomerTelesalesFlag(YESNO.NO.getCode());
			carCustomer.preInsert();
			CarCustomerConsultation carCons = new CarCustomerConsultation();
			carCons.setCustomerCode(customerCode);
			carCons.preInsert();
			carCons.setLoanCode(newLoanCode);
			carCons.setConsTelesalesFlag(YESNO.NO.getCode());
			carCustomerBase.preInsert();
			carCustomerConsultationDao.insertCarCustomerConsultation(carCons);
			carCustomerDao.insertCarCustomer(carCustomer);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanCode", newLoanCode);
			map.put("customerCode", customerCode);
			map.put("customerName", carCustomer.getCustomerName());
			carLoanInfoDao.updateCarExtendLoanInfoCusCode(map);
			carCustomerBaseDao.insertCarCustomerBase(carCustomerBase);
		} else {
			carCustomerDao.update(carCustomer);
			carCustomerBaseDao.update(carCustomerBase);
		}
		carLoanInfoDao.update(carLoanInfo);
	}
	
	/**
	 * 联系人
	 * 2016年5月12日
	 * By 申诗阔
	 * @param creditJson
	 * @param newLoanCode
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveExtendHistoryContact(carCreditJson creditJson, String newLoanCode) {
		carCustomerContactPersonDao.deleteMainContractPerson(newLoanCode);
		for (CarCustomerContactPerson carCustomerContactPerson : creditJson.getCarCustomerContactPerson()) {
			carCustomerContactPerson.setIsNewRecord(false);
			carCustomerContactPerson.setLoanCode(newLoanCode);
			carCustomerContactPerson.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
			carCustomerContactPerson.preInsert();
			carCustomerContactPersonDao.insert(carCustomerContactPerson); 
		}
	}
	
	/**
	 * 共借人
	 * 2016年5月12日
	 * By 申诗阔
	 * @param creditJson
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveExtendHistoryCoborrower(CreditJson creditJson) {
		List<String> list = creditJson.getDelArray();
		//根据ids删除共借人数据
		if (list != null && list.size() > 0) {
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("delList", list);
			carLoanCoborrowerDao.deleteByIds(map);
		}
		List<CarLoanCoborrower> carLoanCoborrower2 = creditJson.getCarLoanCoborrower();
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrower2) {
			if(null != carLoanCoborrower.getId()){
				carCustomerContactPersonDao.deleteByContactPersonId(carLoanCoborrower.getId());
			}
		}
		for (CarLoanCoborrower carLoanCoborrower : creditJson.getCarLoanCoborrower()) {
			String loanCode = carLoanCoborrower.getLoanCode();
			String id = carLoanCoborrower.getId();
			if (id == null || "".equals(id)) {
				UUID uuid = UUID.randomUUID();
				String idg = uuid.toString().replace("-", ""); 
				carLoanCoborrower.setId(idg);
				carLoanCoborrower.setLoanCode(loanCode);
				carLoanCoborrower.preInsert();
				carLoanCoborrower.setCaptchaIfConfirm("0");
				carLoanCoborrower.setAppSignFlag(null);
				carLoanCoborrower.setComposePhotoId(null);
				carLoanCoborrower.setConfirmTimeout(null);
				carLoanCoborrower.setCurPlotId(null);
				carLoanCoborrower.setComposePhotoId(null);
				carLoanCoborrower.setIdValidFlag(null);
				carLoanCoborrower.setCustomerPin(null);
				carLoanCoborrowerDao.insert(carLoanCoborrower);
				List<CarCustomerContactPerson> carCustomerContactPer = carLoanCoborrower.getCarCustomerContactPerson();
				if (null != carCustomerContactPer) {
					for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPer) {
						UUID uuide = UUID.randomUUID();
						String ide = uuide.toString().replace("-", "");
						carCustomerContactPerson.setId(ide);
						carCustomerContactPerson.setrCustomerCoborrowerCode(carLoanCoborrower.getId());
						carCustomerContactPerson.setLoanCode(loanCode);
						carCustomerContactPerson.preInsert();
						carCustomerContactPerson.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
						carCustomerContactPersonDao.insert(carCustomerContactPerson); 
					}
				}
			} else {
				carLoanCoborrower.setLoanCode(loanCode);
				carLoanCoborrower.preUpdate();
				carLoanCoborrowerDao.update(carLoanCoborrower);
				String idg = carLoanCoborrower.getId();
				if (null != carLoanCoborrower.getCarCustomerContactPerson()) {
					List<CarCustomerContactPerson> carCustomerContactPerX =  carLoanCoborrower.getCarCustomerContactPerson();
					for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPerX) {
						String PersonId = carCustomerContactPerson.getId();
						if (PersonId == null || "".equals(PersonId)) {
							carCustomerContactPerson.setrCustomerCoborrowerCode(idg);
							carCustomerContactPerson.setLoanCode(loanCode);
							carCustomerContactPerson.preInsert();
							carCustomerContactPerson.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
							carCustomerContactPersonDao.insert(carCustomerContactPerson);
						} else {
							carCustomerContactPerson.setLoanCode(loanCode);
							carCustomerContactPerson.preUpdate();
							carCustomerContactPersonDao.update(carCustomerContactPerson);
						}
					}
				}
			}
		}
	}
}
