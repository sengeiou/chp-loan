package com.creditharmony.loan.car.carExtend.event;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.InitViewData;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carExtend.view.CarExtendLaunchView;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 
 * @Class Name InitCarExtendLoanFlow
 * @author 陈伟东
 * @Create In 2016年2月1日
 */
@Service("init_hj_carExtendFlow")
public class InitCarExtendLoanFlow extends BaseService implements InitViewData {
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
	
	@Autowired
	private CarLoanInfoService carLoanInfoService;
		
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;

	@SuppressWarnings("rawtypes")
	@Override
	public BaseBusinessView initViewData(Map parameterMap) {
		String[] applyIds = (String[]) parameterMap.get("applyId");
		CarLoanInfo carLoanInfo=carLoanInfoService.selectByApplyId(applyIds[0]);
		String customerCode = carLoanInfo.getCustomerCode();
		String loanCode = carLoanInfo.getLoanCode();
		CarExtendLaunchView carExtendLaunchView = new CarExtendLaunchView();
		//查询客户基本信息
		CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
		//查询客户信息
		CarCustomer carCustomer = carCustomerService.selectByCustomerCodeNew(customerCode);
		carCustomer.setDictCertType(DictCache.getInstance().getDictLabel("jk_certificate_type", carCustomer.getDictCertTypeCode()));
		carCustomer.setDictEducation(DictCache.getInstance().getDictLabel("jk_degree", carCustomer.getDictEducationCode()));
		carCustomer.setCustomerSex(DictCache.getInstance().getDictLabel("jk_sex", carCustomer.getCustomerSexCode()));
		carCustomer.setDictMarryStatus(DictCache.getInstance().getDictLabel("jk_marriage", carCustomer.getDictMarryStatusCode()));
		//查询客户咨询信息
		CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(loanCode);
		ReflectHandle.copy(carCustomerBase, carExtendLaunchView);
		ReflectHandle.copy(carCustomer, carExtendLaunchView);
		ReflectHandle.copy(carCustomerConsultation, carExtendLaunchView);
		ReflectHandle.copy(carLoanInfo, carExtendLaunchView);

		return carExtendLaunchView;
	}

}
