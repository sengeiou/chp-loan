package com.creditharmony.loan.car.carApply.event;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.InitViewData;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.carApply.view.CarLaunchView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 
 * @Class Name InitCarLoanFlow
 * @author ganquan
 * @Create In 2016年2月1日
 */
@Service("init_hj_carLoanFlow")
public class InitCarLoanFlow extends BaseService implements InitViewData {
	//查看客户基本信息service
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
		
	//查看客户信息service
	@Autowired
	private CarCustomerService carCustomerService;
		
	//车辆详细信息service
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
		
	//客户咨询信息
	@Autowired
	private CarCustomerConsultationService carCustomerConsultationService;
		
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;

	@SuppressWarnings("rawtypes")
	@Override
	public BaseBusinessView initViewData(Map parameterMap) {
		String[] customerCodes =(String[]) parameterMap.get("customerCode");
		String[] loanCodes = (String[]) parameterMap.get("loanCode");
		CarLaunchView carLaunchView = new CarLaunchView();
		if(customerCodes != null && customerCodes.length>0){
			//查询客户基本信息
			CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCodes[0]);
			//查询客户信息
			CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCodes[0]);
			//查询客户咨询信息
			CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(loanCodes[0]);
			//获得评估师姓名
			User user = UserUtils.getUser();
			String appraiserName = user.getName();
			carLaunchView.setAppraiserName(appraiserName);
			ReflectHandle.copy(carCustomerBase, carLaunchView);
			ReflectHandle.copy(carCustomer, carLaunchView);
			ReflectHandle.copy(carCustomerConsultation, carLaunchView);
		}
		if(loanCodes != null && loanCodes.length>0){
			//查询车辆信息
			CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCodes[0]);
			ReflectHandle.copy(carVehicleInfo, carLaunchView);
			if(carVehicleInfo != null){
				BigDecimal storeAssessAmount = carVehicleInfo.getStoreAssessAmount();
				if(storeAssessAmount != null){
					Double storeAssessAmount2 = storeAssessAmount.doubleValue();
					carLaunchView.setStoreAssessAmount(storeAssessAmount2);
				}
			}
		}
		return carLaunchView;
	}

}
