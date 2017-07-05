package com.creditharmony.loan.car.carExtend.web;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carExtend.view.CarExtendAppraiserView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;

/**
 * 展期评估师录入
 * @Class Name CarExtendAppraiserController
 * @author ganquan
 * @Create In 2016年3月7日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtend/carExtendAppraiser")
public class CarExtendAppraiserController extends BaseController  {
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	
	@Autowired
	private CarContractService carContractService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	/**
	 * 展期评估师提交  2016年3月7日 By 甘泉
	 * 
	 * @param CarCustomerBase
	 * @param CarVehicleInfo
	 * @return redirect CarLoanAdvisoryBacklog
	 */
	@RequestMapping(value = "appraiserSubmit")
	public String appraiserSubmit(WorkItemView workItemView,CarExtendAppraiserView bv){
		bv.setApplyStatusCode(CarLoanStatus.PENDING_UPLOADED_FILE.getCode());
		workItemView.setBv(bv);
		
		//获取标红置顶属性内容
		WorkItemView workItem = flowService.loadWorkItemView(bv.getApplyId(), workItemView.getWobNum(), workItemView.getToken());
		workItemView.setFlowProperties(workItem.getFlowProperties());
		
		bv.setStoreAssessAmount(bv.getExtensionAssessAmount().doubleValue());
		workItemView.setResponse(CarLoanResponses.TO_UPLOAD_FILE.getCode());
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carExtendWorkItems/fetchTaskItems/extendAppraiser";
	}
	
	/**
	 * 展期评估师放弃  2016年3月7日 By 甘泉
	 * 
	 * @param CarCustomerBase
	 * @param CarVehicleInfo
	 * @return redirect CarLoanAdvisoryBacklog
	 */
	@RequestMapping(value = "appraiserGiveUp")
	public String appraiserGiveUp(WorkItemView workItemView,String applyId){
		CarExtendAppraiserView bv = new CarExtendAppraiserView();
		bv.setApplyId(applyId);
		bv.setApplyStatusCode(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		workItemView.setBv(bv);
		
		workItemView.setResponse(CarLoanResponses.CUSTOMER_GIVE_UP.getCode());
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carExtendWorkItems/fetchTaskItems/extendAppraiser";
	}
	/**
	 * 展期评估师评估金额验证  
	 * 2016年3月7日 By 甘泉
	 * @param CarCustomerBase
	 * @param CarVehicleInfo
	 * @return redirect CarLoanAdvisoryBacklog
	 */
	@RequestMapping(value = "amountCheck")
	@ResponseBody
	public String amountCheck(double extensionAssessAmount,String applyId){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanAdditionalApplyid = carLoanInfo.getLoanRawcode();
		//得到上一次流程的借款信息
		CarLoanInfo carLoanInfo2 = carLoanInfoService.get(loanAdditionalApplyid);
		String loanCode2 = carLoanInfo2.getLoanCode();
		CarContract carContract = carContractService.getByLoanCode(loanCode2);
		double contractAmount = carContract.getContractAmount().doubleValue();
		if(contractAmount > extensionAssessAmount && extensionAssessAmount >0){
			return "true";
		}
		return "展期评估金额大于前次汇诚终审金额";
	}
}
