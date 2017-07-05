package com.creditharmony.loan.car.carExtend.web;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carExtend.view.CarExtendSigningSubmitView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;

/**
 * 待确认签署
 * @Class Name CarExtendSigningController
 * @author 张振强
 * @Create In 2016年3月12日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtendSigning")
public class CarExtendSigningController extends BaseController {

	//FlowService 查询流程待办列表、提交流程
	@Resource(name="appFrame_flowServiceImpl")
	protected FlowService flowService;
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	/**
	 * 确认签署提交
	 * 2016年3月12日
	 * By 张振强
	 * @param workItemView 流程工作项
	 * @param view 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="carSigningCheckHandle")
	public String carSigningCheckHandle(WorkItemView workItemView,CarExtendSigningSubmitView view){
		
		try {
			//得到实际签约日期
			Date contractDueDay = view.getContractDueDay();
			if(contractDueDay != null){
				view.setContractFactDay(contractDueDay);
			}
			view.setApplyStatusCode(CarLoanStatus.PENDING_PRODUCED_CONTRACT.getCode());
			workItemView.setResponse(CarLoanResponses.TO_MAKE_CONTRACT.getCode());
			workItemView.setBv(view);
			
			CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(view.getLoanCode());
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (!carCustomerBankInfo.getBankCardNo().equals(view.getBankCardNo())) {
				map.put("bankCardNo", view.getBankCardNo());
			}
			if (null!=carCustomerBankInfo.getCardBank() && !carCustomerBankInfo.getCardBank().equals(view.getCardBank())) {
				map.put("cardBank", view.getCardBank());
			}
			if (!map.isEmpty()) {
				CarLoanInfo caLI = carLoanInfoService.selectByLoanCode(view.getLoanCode());
				flowService.saveDataByApplyId(caLI.getApplyId(), map);
			}
			
			//获取标红置顶属性内容
			WorkItemView workItem = flowService.loadWorkItemView(view.getApplyId(), workItemView.getWobNum(), workItemView.getToken());
			workItemView.setFlowProperties(workItem.getFlowProperties());
			
		    flowService.dispatch(workItemView);
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return BooleanType.TRUE;
	}
	
	/**
	 * 确认签署 退回
	 * 2016年3月12日
	 * By 张振强
	 * @param workItemView 流程工作项
	 * @param view 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="backTo")
	public String backTo(WorkItemView workItemView,CarExtendSigningSubmitView view){
		
		try {
			view.setApplyStatusCode(CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode());
			workItemView.setResponse(CarLoanResponses.TO_FRIST_AUDIT.getCode());
			workItemView.setBv(view);
			//获取标红置顶属性内容
			WorkItemView workItem = flowService.loadWorkItemView(view.getApplyId(), workItemView.getWobNum(), workItemView.getToken());
			workItemView.setFlowProperties(workItem.getFlowProperties());
		    flowService.dispatch(workItemView);
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return BooleanType.TRUE;
	}
	
	/**
	 * 确认签署 展期放弃
	 * 2016年3月12日
	 * By 张振强
	 * @param workItemView 流程工作项
	 * @param view 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="giveUp")
	public String giveUp(WorkItemView workItemView,CarExtendSigningSubmitView view){
		
		try {
			view.setApplyStatusCode(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
			workItemView.setResponse(CarLoanResponses.EXTEND_GIVE_UP.getCode());
			workItemView.setBv(view);
		    flowService.dispatch(workItemView);
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return BooleanType.TRUE;
	}
	
	
	
	

}
