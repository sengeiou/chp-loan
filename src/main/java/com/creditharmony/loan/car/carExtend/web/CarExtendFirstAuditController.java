package com.creditharmony.loan.car.carExtend.web;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carExtend.view.CarExtendFirstAuditView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 展期初审
 * @Class Name CarExtendFirstAuditController
 * @author ganquan
 * @Create In 2016年3月9日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtend/carExtendFirstAudit")
public class CarExtendFirstAuditController extends BaseController {
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	
	//车借借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	/**
	 * 展期初审提交  
	 * 2016年3月9日 
	 * By 甘泉
	 * @param 
	 * @param 
	 * @return redirect
	 */
	@RequestMapping(value = "firstAuditSubmit")
	public String firstAuditSubmit(WorkItemView workItemView,CarAuditResult carAuditResult,
			CarApplicationInterviewInfo carApplicationInterviewInfo,CarExtendFirstAuditView bv,
			CarCustomer carCustomer,CarLoanInfo carLoanInfo,
			String applyId,String backNode){
		//向bv中添加审核信息
		bv.setCarAuditResult(carAuditResult);
		//向bv中添加面审信息
		bv.setCarApplicationInterviewInfo(carApplicationInterviewInfo);
		//向bv中添加客户信息
		bv.setCarCustomer(carCustomer);
		//向bv中添加借款信息
		bv.setCarLoanInfo(carLoanInfo);
		if(CarLoanResult.THROUGH.getCode().equals(bv.getAuditResult())){
			bv.setFirstCheckName(UserUtils.getUser().getName());
			bv.setGrossRate(carAuditResult.getGrossRate().doubleValue());
			bv.setAuditTime(new Date());
			ReflectHandle.copy(carAuditResult, bv);
			//初审通过的操作
			String auditBorrowProductCode = bv.getAuditBorrowProductCode();
			//向流中添加产品名称
			if(CarLoanProductType.GPS.getCode().equals(auditBorrowProductCode)){
				bv.setAuditBorrowProductName(CarLoanProductType.GPS.getName());
			}
			if(CarLoanProductType.PLEDGE.getCode().equals(auditBorrowProductCode)){
				bv.setAuditBorrowProductName(CarLoanProductType.PLEDGE.getName());
			}
			if(CarLoanProductType.TRANSFER.getCode().equals(auditBorrowProductCode)){
				bv.setAuditBorrowProductName(CarLoanProductType.TRANSFER.getName());
			}
			//添加response
			workItemView.setResponse(CarLoanResponses.TO_SEC_AUDIT.getCode());
			//修改借款状态
			bv.setApplyStatusCode(CarLoanStatus.PENDING_REVIEW.getCode());
			//更新进入汇成时间
			carLoanInfo = carLoanInfoService.selectByLoanCode(carLoanInfo.getLoanCode());
			carLoanInfo.setFirstEntryApprove(new Date());
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
		}else if(CarLoanResult.BACK.getCode().equals(bv.getAuditResult())){
			bv.setAuditBorrowProductCode("");
			bv.setAuditBorrowProductName("");
			bv.setAuditLoanMonths(null);
			bv.setAuditAmount(0d);
			//初审退回的操作
			if("0".equals(backNode)){
				//退回到评估师录入
				//添加response
				workItemView.setResponse(CarLoanResponses.BACK_ASSESS_ENTER.getCode());
				//修改借款状态
				bv.setApplyStatusCode(CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			}else if("1".equals(backNode)){
				//退回到待上传资料
				//添加response
				workItemView.setResponse(CarLoanResponses.BACK_UPLOAD_FILE.getCode());
				//修改借款状态
				bv.setApplyStatusCode(CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			}
		}else if (CarLoanResult.REFUSED.getCode().equals(bv.getAuditResult())){
			//添加response
			workItemView.setResponse(CarLoanResponses.FIRST_AUDIT_REFUSED.getCode());
			//修改借款状态
			bv.setApplyStatusCode(CarLoanStatus.FIRST_INSTANCE_REJECT.getCode());
		}
		workItemView.setBv(bv);
		//获取标红置顶属性内容
		WorkItemView workItem = flowService.loadWorkItemView(applyId, workItemView.getWobNum(), workItemView.getToken());
		workItemView.setFlowProperties(workItem.getFlowProperties());
		
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carLoanWorkItems/fetchTaskItems/faceFirstAudit";
	}
	
	/**
	 * 展期初审放弃 
	 * 2016年3月9日 
	 * By 甘泉
	 * @param 
	 * @param 
	 * @return redirect
	 */
	@RequestMapping(value = "firstAuditGiveUp")
	public String firstAuditGiveUp(WorkItemView workItemView,CarExtendFirstAuditView bv){
		workItemView.setResponse(CarLoanResponses.FIRST_AUDIT_CUSTOMER_GIVE_UP.getCode());
		//修改借款状态到初审拒绝
		bv.setApplyStatusCode(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		workItemView.setBv(bv);
		
		flowService.dispatch(workItemView);
		//return "redirect:" + adminPath + "/car/carExtendWorkItems/fetchTaskItems/extendReceived";
		return "redirect:" + adminPath + "/car/carLoanWorkItems/fetchTaskItems/faceFirstAudit";
		
	}
	/**
	 * 展期初审验证审批金额 
	 * 2016年3月10日 
	 * By 甘泉
	 * @param String loanCode
	 * @param double auditAmount
	 * @return String
	 */
	@RequestMapping(value = "auditAmountCheck")
	@ResponseBody
	public String auditAmountCheck(String loanCode,double auditAmount){
		CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		double amount = carVehicleInfo.getExtensionSuggestAmount().doubleValue();
		if(auditAmount > amount){
			return "true";
		}else{
			return "false";
		}
	}
	
}
