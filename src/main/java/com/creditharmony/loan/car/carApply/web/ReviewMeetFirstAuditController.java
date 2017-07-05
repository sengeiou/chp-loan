package com.creditharmony.loan.car.carApply.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.view.ReviewMeetFirstAuditBusinessView;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carTotalRate.service.CarGrossSpreadService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.FirstServiceCharge;
import com.creditharmony.loan.car.common.service.FirstServiceChargeService;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 面审初审controller
 * @Class Name ReviewMeetFirstAuditController
 * @author 陈伟东
 * @Create In 2016年2月22日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/apply/reviewMeetFirstAudit")
public class ReviewMeetFirstAuditController extends BaseController {
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	
	//审核结果service
	@Autowired
	private CarGrossSpreadService carGrossSpreadService;
	
	//车借借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	@Autowired
	private FirstServiceChargeService firstServiceChargeService;
	
	//初审通过，拒绝和退回
	@RequestMapping(value="reviewMeetCommit")
	public String reviewMeetCommit(WorkItemView workItem,ReviewMeetFirstAuditBusinessView bv,String backNode,FlowProperties flowProperties){
		//标红置顶参数
		workItem.setFlowProperties(flowProperties);
		//想流程中添加
		if(CarLoanResult.THROUGH.getCode().equals(bv.getAuditResult())){
			//向流中添加产品类型
			String auditBorrowProductCode =bv.getDictProductType();
			if(!"".equals(auditBorrowProductCode)){
				bv.setAuditBorrowProductCode(auditBorrowProductCode);
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
			}
			//添加批借时间
			Date date = new Date();
			bv.setAuditTime(date);
			//批借借款期限
			bv.setAuditLoanMonths(Integer.parseInt(bv.getDictAuditMonths()));
			//初审通过修改response
			workItem.setResponse(CarLoanResponses.TO_SEC_AUDIT.getCode());
			//修改借款状态
			bv.setDictLoanStatus(CarLoanStatus.PENDING_REVIEW.getCode());
		}else if(CarLoanResult.REFUSED.getCode().equals(bv.getAuditResult())) {
			//初审拒绝
			workItem.setResponse(CarLoanResponses.FIRST_AUDIT_REFUSED.getCode());
			//修改借款状态到初审拒绝
			bv.setDictLoanStatus(CarLoanStatus.FIRST_INSTANCE_REJECT.getCode());
		}else if (CarLoanResult.BACK.getCode().equals(bv.getAuditResult())) {
			bv.setAuditAmount(0d);
			bv.setAuditBorrowProductCode("");
			bv.setAuditBorrowProductName("");
			bv.setAuditLoanMonths(null);
			//初审退回
			if("0".equals(backNode)){
				//退回到评估师录入
				workItem.setResponse(CarLoanResponses.BACK_ASSESS_ENTER.getCode());
				bv.setDictLoanStatus(CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
				bv.setDictOperStatus(NextStep.CONTINUE_CONFIRM.getCode());
			}else if ("1".equals(backNode)) {
				//退回到车借申请
				workItem.setResponse(CarLoanResponses.BACK_LOAN_APPLY.getCode());
				bv.setDictLoanStatus(CarLoanStatus.FIRST_INSTANCE_BACK.getCode());
			}
			
		}
		workItem.setBv(bv);
		flowService.dispatch(workItem);
		return "redirect:" + adminPath
				+ " /car/carLoanWorkItems/fetchTaskItems/faceFirstAudit";
	}
	//客户放弃
	@RequestMapping(value="customerGiveUp")
	public String customerGiveUp(WorkItemView workItem, String applyId,String loanCode){
		ReviewMeetFirstAuditBusinessView bv = new ReviewMeetFirstAuditBusinessView();
		bv.setApplyId(applyId);
		bv.setLoanCode(loanCode);
		workItem.setResponse(CarLoanResponses.FIRST_AUDIT_CUSTOMER_GIVE_UP.getCode());
		bv.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		workItem.setBv(bv);
		flowService.dispatch(workItem);
		return "redirect:" + adminPath
				+ " /car/carLoanWorkItems/fetchTaskItems/faceFirstAudit";
	}
	
	/**
	 * 根据loanCode判断是否存在展期放弃的数据
	 * By zqa
	 * @param loanCode 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isExtendGiveUp", method = RequestMethod.POST)
	public String isExtendGiveUp(String loanCode){
		CarLoanInfo extendCarInfo = carLoanInfoService.selectByLoanCode(loanCode);
		if(extendCarInfo!=null){
			String appId = extendCarInfo.getLoanAdditionalApplyid();
			List<CarLoanInfo> infos = carLoanInfoService.selectByLoanAddtionAppid(appId);
			if(infos.size()>0){
				return infos.get(0).getLoanCode();
			}
		}
		return loanCode;
	}
	
	/**
	 * 根据产品类型和产品期限获取总费率
	 * 2016年2月25日
	 * By 李静辉
	 * @param productTypeName  产品类型
	 * @param productTypeMonths产品期限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "asynLoadGrossRate", method = RequestMethod.POST)
	public Double asynLoadGrossRate(String productTypeName,String productTypeMonths,String loanCode){
		Double grossSpread = carGrossSpreadService.getCarGrossSpread(productTypeMonths, productTypeName,loanCode);
		
		return ObjectHelper.isEmpty(grossSpread)?null: grossSpread;
	}
	
	/**
	 * 根据借款编码获取费率对应的关系表
	 * By zqa
	 * @param loanCode  借款编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "asynLoadFirstServiceRate", method = RequestMethod.POST)
	public String asynLoadFirstServiceRate(String loanCode){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode); // 借款信息
		if(null!=carLoanInfo){
			if(null!=carLoanInfo.getFirstServiceChargeId()){
				FirstServiceCharge f = firstServiceChargeService.findFirstServiceChargeById(carLoanInfo.getFirstServiceChargeId());
				return jsonMapper.toJson(f);
			}else{
				FirstServiceCharge f = new FirstServiceCharge();
				if(carLoanInfo.getFirstServiceTariffingRate().compareTo(new BigDecimal("0"))==0){
					f.setNinetyAboveRate("2");
					f.setNinetyBelowRate("0");
				}else if (carLoanInfo.getFirstServiceTariffingRate().compareTo(new BigDecimal("4"))==0){
					f.setNinetyAboveRate("4");
					f.setNinetyBelowRate("2");
				}else{
					if(carLoanInfo.getLoanMonths().compareTo(new BigDecimal(100))>0){
						f.setNinetyAboveRate("2");
						f.setNinetyBelowRate("0");
					}else{
						f.setNinetyAboveRate("4");
						f.setNinetyBelowRate("2");
					}
				}
				return jsonMapper.toJson(f);
			}
		}
		return null;
	}
}
