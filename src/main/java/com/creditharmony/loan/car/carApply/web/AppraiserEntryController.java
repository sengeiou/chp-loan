package com.creditharmony.loan.car.carApply.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.ApplyIdUtils;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CustomerValidation;
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
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 流程发起界面
 * @Class Name AppraiserEntryController
 * @author ganquan
 * @Create In 2016年1月22日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/appraiserEntry")
public class AppraiserEntryController extends BaseController {
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	
	@Autowired
	CustomerValidation customerValidationService;
	
	//客户基本信息service
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
			
	//客户信息service
	@Autowired
	private CarCustomerService carCustomerService;
			
	//车辆详细信息service
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
			
	//客户咨询信息
	@Autowired
	private CarCustomerConsultationService carCustomerConsultationService;
	
	//车借借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
			
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	
	/**
	 * 流程发起和上传资料退回评估师发起 2015年1月26日 By 甘泉
	 * 
	 * @param CarCustomerBase
	 * @param CarVehicleInfo
	 * @return redirect CarLoanAdvisoryBacklog
	 */
	@RequestMapping(value = "launchFlow")
	public String launchAndBackAppraiser(WorkItemView workItemView,
			CarCustomer carCustomer, CarCustomerBase carCustomerBase,
			CarVehicleInfo carVehicleInfo, CarLoanInfo carLoanInfo,
			Double loanApplyAmount, Double storeAssessAmount,
			String dictOperStatus,FlowProperties flowproperties,HttpServletRequest request) {
		
		workItemView.setFlowProperties(flowproperties);
		String customerName = request.getParameter("customerName");
		carCustomer.setCustomerName(customerName);
		carCustomerBase.setCustomerName(customerName);
		String customerCode = carCustomer.getCustomerCode();
		String loanCode = carLoanInfo.getLoanCode();
		CarLaunchView bv = new CarLaunchView();
		if (NextStep.TO_APPLY.getCode().equals(dictOperStatus)) {
			//...............................待审请..............................
			// 添加车借信息门店相关
			Org org = UserUtils.getUser().getDepartment();
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.TEAM.key.equals(orgType)){
				org = OrgCache.getInstance().get(org.getParentId());
			}
			String orgCode = org.getId();
			String orgName = org.getName();
			carLoanInfo.setStoreCode(orgCode);
			carLoanInfo.setStoreName(orgName);
			// 添加车借经理相关
			String consTeamManagercode = carLoanInfoService.selectByLoanCode(
					carVehicleInfo.getLoanCode()).getConsTeamManagercode();
			String managerCode = carLoanInfoService.selectByLoanCode(
					carVehicleInfo.getLoanCode()).getManagerCode();
			carLoanInfo.setConsTeamManagercode(consTeamManagercode);
			carLoanInfo.setManagerCode(managerCode);
			// 由于状态不确定，所以就选择了待上传资料-----以后修改
			carLoanInfo.setDictLoanStatus(CarLoanStatus.PENDING_APPLICATION
					.getCode());
			// 获得当前时间，做为申请时间
			Date date = new Date();
			carLoanInfo.setLoanApplyTime(date);
			CarCustomerConsultation CarCustomerConsultation = carCustomerConsultationService
					.selectByLoanCode(loanCode);
			CarCustomerConsultation.setDictOperStatus(dictOperStatus);
			// 将数据源加入到bv中去
			ReflectHandle.copy(carCustomer, bv);
			ReflectHandle.copy(carCustomerBase, bv);
			ReflectHandle.copy(carVehicleInfo, bv);
			bv.setMileage(carVehicleInfo.getMileage()+"");
			ReflectHandle.copy(carLoanInfo, bv);
			ReflectHandle.copy(CarCustomerConsultation, bv);
			// 客户经理姓名和团队经理姓名
			
			if (!StringUtils.isEmpty(consTeamManagercode)) {
				if(UserUtils.get(consTeamManagercode)!=null)
				{
					String loanTeamEmpName = UserUtils.get(consTeamManagercode)
							.getName();
					bv.setLoanTeamEmpName(loanTeamEmpName);
				}
			}
			if (!StringUtils.isEmpty(managerCode)) {
				if(UserUtils.get(managerCode)!=null)
				{
					String offendSalesName = UserUtils.get(managerCode).getName();
					bv.setOffendSalesName(offendSalesName);
				}
			}
			// 预计借款金额
			bv.setLoanApplyAmount(loanApplyAmount);
			// 门店评估金额
			bv.setStoreAssessAmount(storeAssessAmount);
			// 加入门店姓名，门店编号
			bv.setStoreName(orgName);
			bv.setStoreCode(orgCode);
			// 加入进件时间
			bv.setCustomerIntoTime(date);
			workItemView.setBv(bv);
			String stepName = workItemView.getStepName();
			if ("@launch".equals(stepName)) {
				// 修改车借_借款信息表
				String applyId = ApplyIdUtils.builderApplyId(workItemView.getFlowType());
				//carLoanInfo.setApplyId(applyId);
				bv.setApplyId(applyId);
				workItemView.setBv(bv);
				flowService.launchFlow(workItemView);
			} else {
				workItemView.setResponse(CarLoanResponses.TO_LOAN_APPLY
						.getCode());
				flowService.dispatch(workItemView);
			}
		} else if (NextStep.INCONFORMITY.getCode().equals(dictOperStatus)) {
			//...............................不符合进件条件..............................
			// 不符合进件条件
			// 如果是评估师录入节点不符合进件条件
			if (workItemView.getStepName().equals(CarLoanSteps.APPRAISER.getName())) {
				bv.setCustomerCode(customerCode);
				bv.setLoanCode(loanCode);
				bv.setDictLoanStatus(CarLoanStatus.STORE__GIVE_UP
						.getCode());
				bv.setDictOperStatus(dictOperStatus);
				workItemView.setBv(bv);
				workItemView.setResponse(CarLoanResponses.NOT_INTO.getCode());
				flowService.dispatch(workItemView);
			}else{
				//流程发起前的不符合进件调价
				// 修改客户咨询信息
				if (customerCode != null) {
					CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
					carCustomerConsultation.setCustomerCode(customerCode);
					carCustomerConsultation.setDictOperStatus(dictOperStatus);
					carCustomerConsultation.setLoanCode(loanCode);
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
					carVehicle.setDictOperStatus(dictOperStatus);
					carVehicle.preUpdate();
					carVehicleInfoService.update(carVehicle);
					carLoan.preUpdate();
					carLoanInfoService.updateCarLoanInfo(carLoan);
				}
				// 添加历史记录
				carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.APPRAISER.getCode(), CarLoanOperateResult.APPRAISER_STORE__GIVE_UP.getCode(),
						"门店放弃",CarLoanStatus.STORE__GIVE_UP.getCode());
			}
		} else if (NextStep.CUSTOMER_GIVEUP.getCode().equals(dictOperStatus)) {
			//...............................客户放弃..............................
			String stepName = workItemView.getStepName();
			if ("@launch".equals(stepName)) {
				// 流程发起前的客户放弃
				// 修改客户咨询信息
				if (customerCode != null) {
					CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
					carCustomerConsultation.setCustomerCode(customerCode);
					carCustomerConsultation.setLoanCode(loanCode);
					carCustomerConsultation
							.setDictOperStatus(NextStep.CUSTOMER_GIVEUP
									.getCode());
					carCustomerConsultation.preUpdate();
					carCustomerConsultationService
							.updateCarCustomerConsultation(carCustomerConsultation);
				}
				// 修改借款信息借款状态和车辆信息的下一步状态
				if (loanCode != null) {
					CarLoanInfo carLoan = new CarLoanInfo();
					CarVehicleInfo carVehicle = new CarVehicleInfo();
					carLoan.setLoanCode(loanCode);
					carLoan.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP
							.getCode());
					carVehicle.setDictOperStatus(NextStep.CUSTOMER_GIVEUP
							.getCode());
					carVehicle.preUpdate();
					carLoan.preUpdate();
					carVehicleInfoService.update(carVehicle);
					carLoanInfoService.updateCarLoanInfo(carLoan);
				}
				// 添加历史记录
				carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.APPRAISER.getCode(),CarLoanOperateResult.APPRAISER_CUSTOMER__GIVE_UP.getCode(),
						"评估师客户放弃",CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			} else {
				// 评估师录入的客户放弃
				workItemView.setResponse(CarLoanResponses.TO_LOAN_APPLY
						.getCode());
				bv.setLoanCode(loanCode);
				bv.setCustomerCode(customerCode);
				// 下一步借款状态为客户放弃
				bv.setDictOperStatus(NextStep.CUSTOMER_GIVEUP.getCode());
				bv.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
				workItemView.setBv(bv);
				workItemView.setResponse(CarLoanResponses.CUSTOMER_GIVE_UP
						.getCode());
				flowService.dispatch(workItemView);
			}
		}
		return "redirect:" + adminPath
				+ "/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog";
	}
	
	@RequestMapping(value = "giveUp")
	public String giveUp(WorkItemView workItemView,String loanCode,String customerCode){
		CarLaunchView bv = new CarLaunchView();
		bv.setLoanCode(loanCode);
		bv.setCustomerCode(customerCode);
		//下一步借款状态为客户放弃
		bv.setDictOperStatus(NextStep.CUSTOMER_GIVEUP.getCode());
		bv.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		workItemView.setBv(bv);
		workItemView.setResponse(CarLoanResponses.CUSTOMER_GIVE_UP.getCode());
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath+"/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog";
	}
	
	/**
     * 客户放弃(发起流程前)
     * 2015年1月28日 By 甘泉
     * @param CarCustomerBase
     * @param CarVehicleInfo
     * @return redirect CarLoanAdvisoryBacklog 
     */
	@RequestMapping(value = "giveUpBeforeLanuch")
	public String giveUpBeforeLanuch(String customerCode,String loanCode){
		//修改客户咨询信息
		if(customerCode != null){
			CarCustomerConsultation carCustomerConsultation = new CarCustomerConsultation();
			carCustomerConsultation.setCustomerCode(customerCode);
			carCustomerConsultation.setLoanCode(loanCode);
			carCustomerConsultation.setDictOperStatus(NextStep.CUSTOMER_GIVEUP.getCode());
			carCustomerConsultation.preUpdate();
			carCustomerConsultationService.updateCarCustomerConsultation(carCustomerConsultation);
		}
		//修改借款信息借款状态和车辆信息的下一步状态
		if(loanCode != null){
			CarLoanInfo carLoanInfo = new CarLoanInfo();
			CarVehicleInfo carVehicleInfo = new CarVehicleInfo();
			carLoanInfo.setLoanCode(loanCode);
			carLoanInfo.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			carVehicleInfo.setDictOperStatus(NextStep.CUSTOMER_GIVEUP.getCode());
			carVehicleInfo.setLoanCode(loanCode);
			carVehicleInfo.preUpdate();
			carLoanInfo.preUpdate();
			carVehicleInfoService.update(carVehicleInfo);
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
		}
		//添加历史记录
		carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.APPRAISER.getCode(), CarLoanOperateResult.APPRAISER_CUSTOMER__GIVE_UP.getCode(), 
				"评估师客户放弃",CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		
		return "redirect:" + adminPath+"/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog";
	}
	
	
	//一个身份证同时车借的个数
	@ResponseBody
	@RequestMapping(value = "vehicleCeiling")
	private String vehicleCeiling(String customerCertNum,String hasFirst){
		if(StringUtils.isNotEmpty(customerCertNum)){
			int num = 2;//1.4版合同更改为 同一身份证客户借款车辆不能超过2辆
			if("1".equals(hasFirst)){
				num = 1;
			}
			if(customerValidationService.vehicleCeiling(customerCertNum) > num){
				return "您抵押车辆已达到上限！";
			}
			if(customerValidationService.notRejectTime(customerCertNum) > 0){
				return "因距上次申请拒绝不足90天 ，现在暂时无法申请!";
			}
		}
		return "success";
	}
	
	//同一个车辆在车借流程结束前不能重复提交申请
	@ResponseBody
	@RequestMapping(value = "notRepeatSubmit",method =RequestMethod.POST)
	private String notRepeatSubmit(String plateNumbers){
		if(customerValidationService.notRepeatSubmit(plateNumbers) > 0 && customerValidationService.uploadAndTrialBack(plateNumbers) >1){
			return "false";
		}
		return "true";
	}
	
	/**
     * 待申请已办
     * 2015年2月27日 By 甘泉
     * @param CarCustomerBase
     * @param CarVehicleInfo
     * @return redirect CarLoanAdvisoryBacklog 
     */
	@RequestMapping(value = "appraiserEntryDone")
	private String appraiserEntryDone(String loanCode , Model model){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode); // 借款信息
    	String customerCode = carLoanInfo.getCustomerCode();
    	CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode); // 客户信息
    	CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
    	CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode); // 车辆信息
    	CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(loanCode);
    	model.addAttribute("carLoanInfo", carLoanInfo);
    	model.addAttribute("carCustomer", carCustomer);
    	model.addAttribute("carCustomerBase", carCustomerBase);
    	model.addAttribute("carVehicleInfo", carVehicleInfo);
    	model.addAttribute("carCustomerConsultation", carCustomerConsultation);
		return "car/done/stayApply_done";
	}
}
