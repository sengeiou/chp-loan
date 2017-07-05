package com.creditharmony.loan.car.carConsultation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.common.dao.CarApplicationInterviewInfoDao;
import com.creditharmony.loan.car.common.dao.CarCustomerBaseDao;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.dao.CarVehicleInfoDao;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.entity.ex.CarLoanCheckTabEx;
import com.creditharmony.loan.car.common.service.CarHistoryService;

/**
 * 借款信息
 * @Class Name CarLoanInfoService
 * @author 安子帅
 * @Create In 2016年1月29日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarLoanInfoService extends CoreManager<CarLoanInfoDao, CarLoanInfo> {
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	@Autowired
	private CarHistoryService carHistoryService;
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	@Autowired
    private CarCustomerConsultationService carCustomerConsultationService;
	@Autowired
	private CarCustomerService carCustomerService;
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	@Autowired
	private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
	@Autowired
	private CarApplicationInterviewInfoDao carApplicationInterviewInfoDao;
	@Autowired
	private CarCustomerDao carCustomerDao;
	
	@Autowired
	private CarCustomerBaseDao carCustomerBaseDao;
	@Autowired
	private CarVehicleInfoDao  carVehicleInfoDao;
	@Autowired
	private CarCustomerConsultationDao carCustomerConsultationDao;
	



	/**
	 * 增加借款信息 2016年1月29日 
	 * By 安子帅
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarLoanInfo(CarLoanInfo carLoanInfo) {
		carLoanInfoDao.insertCarLoanInfo(carLoanInfo);
	}/**
	 * 查询借款信息 2016年1月29日 
	 * By 安子帅
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo selectByLoanCode(String loanCode){
		return carLoanInfoDao.selectByLoanCode(loanCode);
	}
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo selectByApplyId(String applyId){
		return carLoanInfoDao.selectByApplyId(applyId);
	}
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo get(String id){
		return carLoanInfoDao.get(id);
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo selectByLoanCodeExtend(String loanCode){
		return carLoanInfoDao.selectByLoanCodeExtend(loanCode);
	}
	
	/**
	 * 修改借款信息 2016年2月2日 
	 * By ganquan
	 * @param CarCustomerBase
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCarLoanInfo(CarLoanInfo carLoanInfo){
		carLoanInfoDao.update(carLoanInfo);
	}
	/**
     *通过原借款编码得到借款信息
     *@param String 
     *@return   List<CarLoanInfo>
     */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public List<CarLoanInfo> selectByLoanRawCode(String loanRawcode){
    	return carLoanInfoDao.selectByLoanRawCode(loanRawcode);
    }
	/**
	 * 
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo selectLoanCodeByLoanRaw(String loanCode){
		return carLoanInfoDao.selectLoanCodeByLoanRaw(loanCode);
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanCheckTabEx checkAllTab(String loanCode) {
		return carLoanInfoDao.checkAllTab(loanCode);
	}
	
	/**
	 * 通过原车借loancode得到已完成的借款信息
	 * 2016年5月10日
	 * By 申诗阔
	 * @param loanCode
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo selectNearestByLoanCode(String loanCode){
		return carLoanInfoDao.selectNearestByLoanCode(loanCode);
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarLoanInfoAndHis(CarLoanInfo carLoanInfo,CarCustomerConsultation carCustomerConsultation,CarCustomerBase carCustomerBase,CarCustomer carCustomer,CarVehicleInfo carVehicleInfo) {
		String dictOperStatus = carCustomerConsultation.getDictOperStatus();
		carCustomerBaseService.saveCarCustomerBase(carCustomerBase);
		carCustomerConsultationService.saveCarCustomerConsultation(carCustomerConsultation);
		carCustomerService.saveCarCustomer(carCustomer);
		carVehicleInfoService.savecarVehicleInfo(carVehicleInfo);
		if (NextStep.CONTINUE_CONFIRM.getCode().equals(dictOperStatus)) {
			carLoanInfo.setDictLoanStatus(CarLoanStatus.CONTINUE_TRACKING.getCode());
			carLoanInfoDao.insertCarLoanInfo(carLoanInfo);
			carHistoryService.saveCarLoanStatusHis(carLoanInfo.getLoanCode(), CarLoanSteps.CONSULTATION.getCode(), CarLoanOperateResult.CONSULTATION_CONTINUE_TRACKING.getCode(),
					carCustomerConsultation.getConsLoanRemarks(), CarLoanStatus.CONTINUE_TRACKING.getCode());
		}else if(NextStep.CUSTOMER_GIVEUP.getCode().equals(dictOperStatus)){
			carLoanInfo.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			carLoanInfoDao.insertCarLoanInfo(carLoanInfo);
			carHistoryService.saveCarLoanStatusHis(carLoanInfo.getLoanCode(), CarLoanSteps.CONSULTATION.getCode(),CarLoanOperateResult.CONSULTATION_CUSTOMER_GIVE_UP.getCode(),
					carCustomerConsultation.getConsLoanRemarks(), CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		}else if(NextStep.INCONFORMITY.getCode().equals(dictOperStatus)){
			carLoanInfo.setDictLoanStatus(CarLoanStatus.STORE__GIVE_UP.getCode());
			carLoanInfoDao.insertCarLoanInfo(carLoanInfo);
			carHistoryService.saveCarLoanStatusHis(carLoanInfo.getLoanCode(), CarLoanSteps.CONSULTATION.getCode(), CarLoanOperateResult.CONSULTATION_STORE__GIVE_UP.getCode(),
					carCustomerConsultation.getConsLoanRemarks(), CarLoanStatus.STORE__GIVE_UP.getCode());
		}
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void borrowSubmit(CarLoanInfo carLoanInfo,CarApplicationInterviewInfo carApplicationInterviewInfo) {
		//保存借款信息
		updateCarLoanInfo(carLoanInfo);
		//保存面审信息
		carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
	}	
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void customerSave(CarCustomer carCustomer,CarCustomerBase carCustomerBase) {
		carCustomerBaseService.updateCustomer(carCustomerBase, carCustomer);
		carCustomerService.update(carCustomer);
	}	
    /**
     * 保存借款信息（事物）
     * @param carLoanInfo
     * @param carApplicationInterviewInfo
     * By 高远
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void saveCarLoanInfoAndInterviewInfo(CarLoanInfo carLoanInfo,
            CarApplicationInterviewInfo carApplicationInterviewInfo) {
		carApplicationInterviewInfoDao.insert(carApplicationInterviewInfo);
        carLoanInfoDao.insertCarLoanInfo(carLoanInfo);
    }
    /**
     * 修改借款信息（事物）
     * @param carLoanInfo
     * @param carApplicationInterviewInfo
     * By 高远
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void updateCarLoanInfoAndInterviewInfo(CarLoanInfo carLoanInfo,
            CarApplicationInterviewInfo carApplicationInterviewInfo) {
		carApplicationInterviewInfoDao.update(carApplicationInterviewInfo);
        carLoanInfoDao.update(carLoanInfo);

    }
    /**
     * 修改客户开户及管辖信息（事物）
     * @param carLoanInfo
     * @param carCustomer
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
    public void updateCarLoanInfoAndCustomer(CarLoanInfo carLoanInfo,
            CarCustomer carCustomer) {
		carCustomerDao.update(carCustomer);
        carLoanInfoDao.update(carLoanInfo);
    }
	/**
	 * 查询批借金额
	 * @param loanAdditionalApplyid2
	 * @return
	 * By 高远
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Double selectAuditAmount(String loanAdditionalApplyid2) {
		return carLoanInfoDao.selectAuditAmount(loanAdditionalApplyid2);
	}
	
	/**
	 * 修改车借p2p标识（事物）
	 * 
	 * @param carLoanInfo
	 * @param carCustomer
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCarP2PStatu(String type,String[] loanApplyIds) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("loanFlag", type);
		param.put("loanApplyIds", loanApplyIds);
		carLoanInfoDao.updateCarP2PStatu(param);
	}
	
	/**
	 * 修改车借展期p2p标识（事物）
	 * 
	 * @param carLoanInfo
	 * @param carCustomer
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCarExtendP2PStatu(String type,String[] loanApplyIds) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("loanFlag", type);
		param.put("loanApplyId", loanApplyIds);
		carLoanInfoDao.updateCarExtendP2PStatu(param);
	}
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCarLoanInfoDealUser(String dealUser,String loanCode) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dealUser", dealUser);
		param.put("loanCode", loanCode);
		carLoanInfoDao.updateCarLoanInfoDealUser(param);
	}

	/**
	 * 历史展期补录使用，根据车借借款编码和本次借款编码获取最近的含有各种信息的 那条 借款信息
	 * 2016年6月23日
	 * By 申诗阔
	 * @param loanCode 车借loanCode
	 * @param newLoanCode 本次展期loanCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarLoanInfo getRichCarLoanInfo(String loanCode, String newLoanCode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loanCode", loanCode);
		param.put("newLoanCode", newLoanCode);
		return carLoanInfoDao.getRichCarLoanInfo(param);
	}

	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo selectExtendHistoryByLoanRaw(String loanCode){
		return carLoanInfoDao.selectExtendHistoryByLoanRaw(loanCode);
	}
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public CarLoanInfo checkExtendHistoryEntry(String contractCode){
		return carLoanInfoDao.checkExtendHistoryEntry(contractCode);
	}
	
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCarLoanInfoAndHis(CarLoanInfo carLoanInfo,CarCustomerConsultation carCustomerConsultation,CarCustomerBase carCustomerBase,CarCustomer carCustomer,CarVehicleInfo carVehicleInfo) {
		String dictOperStatus = carCustomerConsultation.getDictOperStatus();
		carCustomerBaseDao.updateById(carCustomerBase);
		carCustomerConsultationDao.updateById(carCustomerConsultation);
		carCustomerDao.updateById(carCustomer);
		carVehicleInfoDao.updateById(carVehicleInfo);
		if (NextStep.CONTINUE_CONFIRM.getCode().equals(dictOperStatus)) {
			carLoanInfo.setDictLoanStatus(CarLoanStatus.CONTINUE_TRACKING.getCode());
			carLoanInfoDao.updateById(carLoanInfo);
			carHistoryService.saveCarLoanStatusHis(carLoanInfo.getLoanCode(), CarLoanSteps.CONSULTATION.getCode(), CarLoanOperateResult.CONSULTATION_CONTINUE_TRACKING.getCode(),
					carCustomerConsultation.getConsLoanRemarks(), CarLoanStatus.CONTINUE_TRACKING.getCode());
		}else if(NextStep.CUSTOMER_GIVEUP.getCode().equals(dictOperStatus)){
			carLoanInfo.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			carLoanInfoDao.updateById(carLoanInfo);
			carHistoryService.saveCarLoanStatusHis(carLoanInfo.getLoanCode(), CarLoanSteps.CONSULTATION.getCode(),CarLoanOperateResult.CONSULTATION_CUSTOMER_GIVE_UP.getCode(),
					carCustomerConsultation.getConsLoanRemarks(), CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		}else if(NextStep.INCONFORMITY.getCode().equals(dictOperStatus)){
			carLoanInfo.setDictLoanStatus(CarLoanStatus.STORE__GIVE_UP.getCode());
			carLoanInfoDao.updateById(carLoanInfo);
			carHistoryService.saveCarLoanStatusHis(carLoanInfo.getLoanCode(), CarLoanSteps.CONSULTATION.getCode(), CarLoanOperateResult.CONSULTATION_STORE__GIVE_UP.getCode(),
					carCustomerConsultation.getConsLoanRemarks(), CarLoanStatus.STORE__GIVE_UP.getCode());
		}
	}
	
	/**
     *通过借款信息ID查找展期放弃的数据,升序取最早的一条记录
     *@param String 
     *@return   List<CarLoanInfo>
     */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public List<CarLoanInfo> selectByLoanAddtionAppid(String id){
    	return carLoanInfoDao.selectByLoanAddtionAppid(id);
    }
	
	/**
	 * 通过身份证号、车牌号查找以往的借款信息
	 * @param certNum
	 * @param vehicleNum
	 * @return List<CarLoanInfo>
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public List<CarLoanInfo> selectByCertNumAndVehicleNum(String certNum,String vehicleNum){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("certNum", certNum);
		param.put("vehicleNum", vehicleNum);
    	return carLoanInfoDao.selectByCertNumAndVehicleNum(param);
    }
	
}
