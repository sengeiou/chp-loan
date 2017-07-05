package com.creditharmony.loan.car.carConsultation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.dao.CarVehicleInfoDao;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;

/**
 * 客户咨询_车辆信息
 * @Class Name CarCustomerService
 * @author 安子帅
 * @Create In 2015年1月22日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarVehicleInfoService extends CoreManager<CarVehicleInfoDao, CarVehicleInfo> {
   
	@Autowired
	private CarVehicleInfoDao carVehicleInfoDao;
	
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;
	/**
	 * 保存客户车辆信息 2015年1月22日 
	 * By 安子帅
	 * @param consult
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void savecarVehicleInfo(CarVehicleInfo carVehicleInfo) {
		carVehicleInfoDao.insertCarVehicleInfo(carVehicleInfo);
	}
	/**
	 * 查询客户车辆信息 2015年1月22日 
	 * By 甘泉
	 * @param String
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
    public CarVehicleInfo selectCarVehicleInfo(String loanCode){
    	return carVehicleInfoDao.selectByLoanCode(loanCode);
    }
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public  void update(CarVehicleInfo carVehicleInfo) {
		carVehicleInfoDao.update(carVehicleInfo);
		
	};
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public int selectPlnum(String plateNumbers) {
		return carVehicleInfoDao.selectPlnum(plateNumbers);
	};
	/**
	 * 根据车牌号，检查是否可以再次申请
	 * 默认：7天内不能申请
	 * 初审拒绝、签约上传门店拒绝     7天内不能申请
	 * 复审拒绝、终审拒绝    	  90天内不能再申请
	 * @param plateNumbers
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public int checkApply(String plateNumbers) {
		int num = 0;
		//默认7天不能申请
//		int default7Day = carVehicleInfoDao.selectPlnum(plateNumbers);
//		if(default7Day<=0){
			CarVehicleInfo carInfo = carVehicleInfoDao.selectByPlateNumbers(plateNumbers);
			if(carInfo!=null){
				CarLoanStatusHis carLoanStatusHis = null;
				List<CarLoanStatusHis> carLoanStatusHis7 = carLoanStatusHisDao.getCarLoanStatusHis7(carInfo.getLoanCode());
				if(carLoanStatusHis7.size()>0){
					carLoanStatusHis = carLoanStatusHis7.get(0);
					int check7Day = carLoanStatusHisDao.checkCarLoanStatusHis7(carLoanStatusHis.getId());
					if(check7Day>0){
						num = 7;
					}
				}else{
					List<CarLoanStatusHis> carLoanStatusHis90 = carLoanStatusHisDao.getCarLoanStatusHis90(carInfo.getLoanCode());
					if(carLoanStatusHis90.size()>0){
						carLoanStatusHis = carLoanStatusHis90.get(0);
						int check90Day = carLoanStatusHisDao.checkCarLoanStatusHis90(carLoanStatusHis.getId());
						if(check90Day>0){
							num = 90;
						}
					}
				}
				
			}
//		}else{
//			num = default7Day;
//		}
		return num;
	};
}
