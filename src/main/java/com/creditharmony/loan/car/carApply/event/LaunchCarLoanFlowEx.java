package com.creditharmony.loan.car.carApply.event;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carApply.view.CarLaunchView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.service.backredtop.BackRedTopService;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * @Class Name LaunchCarLoanFlowEx
 * @author ganquan
 * @Create In 2016-2-1
 */
@Service("ex_hj_Launch_Car_Loan_Flow")
public class LaunchCarLoanFlowEx extends BaseService implements ExEvent {
	// 客户基本信息service
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	// 客户信息service
	@Autowired
	private CarCustomerService carCustomerService;
	// 车辆详细信息service
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	// 客户咨询信息
	@Autowired
	private CarCustomerConsultationService carCustomerConsultationService;
	//借款信息service
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;

	@Override
	public void invoke(WorkItemView workItemView) {
		CarLaunchView bv = (CarLaunchView)workItemView.getBv();
		String response = workItemView.getResponse();
		String customerCode = bv.getCustomerCode();
		String loanCode = bv.getLoanCode();
		if(CarLoanResponses.CUSTOMER_GIVE_UP.getCode().equals(response)){//客户放弃
			//修改客户咨询信息
			if(customerCode != null){
				CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
				carCustomerConsultation.setCustomerCode(customerCode);
				carCustomerConsultation.setDictOperStatus(bv.getDictOperStatus());
				carCustomerConsultation.preUpdate();
				carCustomerConsultationService.updateCarCustomerConsultation(carCustomerConsultation);
			}
			//修改借款信息借款状态和车辆信息的下一步状态
			if(loanCode != null){
				CarLoanInfo carLoanInfo = new CarLoanInfo();
				CarVehicleInfo carVehicleInfo = new CarVehicleInfo();
				carLoanInfo.setLoanCode(loanCode);
				carLoanInfo.setDictLoanStatus(bv.getDictLoanStatus());
				carVehicleInfo.setDictOperStatus(bv.getDictOperStatus());
				carVehicleInfo.preUpdate();
				carLoanInfo.preUpdate();
				carVehicleInfoService.update(carVehicleInfo);
				carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			}
			//添加历史记录
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.APPRAISER.getCode(), CarLoanOperateResult.APPRAISER_CUSTOMER__GIVE_UP.getCode(), 
					null,CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		}else if(CarLoanResponses.NOT_INTO.getCode().equals(response)){
			//不符合进件条件
			// 修改客户咨询信息
			if (customerCode != null) {
				CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
				carCustomerConsultation.setCustomerCode(customerCode);
				carCustomerConsultation.setDictOperStatus(bv.getDictOperStatus());
				carCustomerConsultation.preUpdate();
				carCustomerConsultationService
						.updateCarCustomerConsultation(carCustomerConsultation);
			}
			// 修改借款信息借款状态和车辆信息的下一步状态
			if (loanCode != null) {
				CarLoanInfo carLoan = new CarLoanInfo();
				CarVehicleInfo carVehicle = new CarVehicleInfo();
				carLoan.setLoanCode(loanCode);
				carLoan.setDictLoanStatus(CarLoanStatus.STORE__GIVE_UP
						.getCode());
				carVehicle.setLoanCode(loanCode);
				carVehicle.setDictOperStatus(bv.getDictOperStatus());
				carVehicle.preUpdate();
				carVehicleInfoService.update(carVehicle);
				carLoan.preUpdate();
				carLoanInfoService.updateCarLoanInfo(carLoan);
			}
			// 添加历史记录
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.APPRAISER.getCode() , CarLoanOperateResult.APPRAISER_STORE__GIVE_UP.getCode(),
					null,CarLoanStatus.STORE__GIVE_UP.getCode());
		}else {//到申请节点
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Object object = workItemView.getFlowProperties();
			if(object != null){
				FlowProperties flowProperties = (FlowProperties)object;
				if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(CarLoanSteps.APPRAISER.getName(), 
						CarLoanResponses.TO_LOAN_APPLY.getCode(), flowProperties.getFirstBackSourceStep())){
					bv.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
					bv.setOrderField("1," + sdf.format(new Date()));
				}else{
					if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
							 flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
						bv.setOrderField("1," + sdf.format(new Date()));
					}else{
						bv.setOrderField("0," + sdf.format(new Date()));
					}
				}
			}
			
			CarCustomer carCustomer = new CarCustomer();
			CarCustomerBase carCustomerBase = new CarCustomerBase();
			CarVehicleInfo carVehicleInfo = new CarVehicleInfo();
			CarLoanInfo carLoanInfo = new CarLoanInfo();
			CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
			ReflectHandle.copy(bv, carCustomer);
			ReflectHandle.copy(bv, carCustomerBase);
			ReflectHandle.copy(bv, carVehicleInfo);
			ReflectHandle.copy(bv, carLoanInfo);
			carVehicleInfo.setMileage(Double.parseDouble(bv.getMileage()));
			ReflectHandle.copy(bv, carCustomerConsultation);
			//修改客户详细信息
			carCustomer.setCustomerPhoneFirst(carCustomerBase.getCustomerMobilePhone());
			carCustomer.preUpdate();
			carCustomerService.update(carCustomer);
			//修改客户基础信息
			carCustomerBase.preUpdate();
			carCustomerBaseService.update(carCustomerBase);
			//修改车辆信息storeAssessAmount
			BigDecimal storeAssessAmount = new BigDecimal(Double.toString(bv.getStoreAssessAmount()));
			carVehicleInfo.setStoreAssessAmount(storeAssessAmount);
			carVehicleInfo.preUpdate();
			carVehicleInfoService.update(carVehicleInfo);
			//车借借款信息
			BigDecimal loanApplyAmount = new BigDecimal(Double.toString(bv.getLoanApplyAmount()));
			carLoanInfo.setLoanApplyAmount(loanApplyAmount);
			//添加车借_借款信息_客户姓名
			carLoanInfo.setLoanCustomerName(carCustomer.getCustomerName());
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//修改客户咨询信息
			carCustomerConsultation.preUpdate();
			carCustomerConsultationService.updateCarCustomerConsultation(carCustomerConsultation);
			//添加历史记录
			String remark = bv.getRemark();
			if(carVehicleInfo.getLoanCode() != null){
				carHistoryService.saveCarLoanStatusHis(carVehicleInfo.getLoanCode(),CarLoanSteps.APPRAISER.getCode(), 
						CarLoanOperateResult.APPRAISER_TO_APPLICATION.getCode(), remark,CarLoanStatus.PENDING_APPLICATION.getCode());
			}
		}
		
	}
}
