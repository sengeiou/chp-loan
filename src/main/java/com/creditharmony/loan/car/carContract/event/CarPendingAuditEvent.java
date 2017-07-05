package com.creditharmony.loan.car.carContract.event;

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
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carContract.view.CarPendingAuditView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 款项确认事件
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年2月22日
 */
@Service("ex_hj_pendingAudit_CarLoanFlow")
public class CarPendingAuditEvent extends BaseService implements ExEvent  {
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	@Override
	public void invoke(WorkItemView workItem) {
		CarPendingAuditView bv = (CarPendingAuditView)workItem.getBv();
		String dictLoanStatus = bv.getDictLoanStatus();
		String applyId = bv.getApplyId();
		//根据applyId得到借款信息
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		String respose = workItem.getResponse();
		if(CarLoanResponses.BACK_AUDIT_CONTRACT.getCode().equals(respose)){//退回到合同审核
			//修改车借借款信息
			String remark = bv.getRemark();
			String dictBackMestype = bv.getDictBackMestype();
			carLoanInfo.setRemark(remark);
			carLoanInfo.setDictLoanStatus(dictLoanStatus);
			carLoanInfo.setDictBackMestype(dictBackMestype);
			if("6".equals(dictBackMestype)){
				remark = "客户主动放弃";
			}else if("7".equals(dictBackMestype)){
				remark = "风险客户";
			}
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.GRANT_CONFIRMED.getCode(), 
					CarLoanOperateResult.LOAN_CONFIRMATION_BACK.getCode(),
					remark,CarLoanStatus.PENDING_LOAN_CONFIRMATION_BACK.getCode());
			
			 //标红置顶退回相关业务
			redTopBack(workItem, bv);
		}else if(CarLoanResponses.TO_ALLOT_CARD.getCode().equals(respose)){
			carLoanInfo.setDictLoanStatus(dictLoanStatus);
			carLoanInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			//添加历史信息
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.GRANT_CONFIRMED.getCode(), 
					CarLoanOperateResult.LOAN_CONFIRMATION_PASS.getCode(), 
					null,CarLoanStatus.PENDING_ASSIGNED_CARD_NUMBER.getCode());
			// 标红置顶提交下一步相关业务
			redTopCommit(workItem, bv);

		}
	}
	private void redTopCommit(WorkItemView workItem, CarPendingAuditView bv) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(
					CarLoanSteps.GRANT_CONFIRMED.getName(),
					CarLoanResponses.TO_ALLOT_CARD.getCode(),
					flowProperties.getFirstBackSourceStep())) {
				bv.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
				bv.setOrderField("1," + sdf.format(new Date()));
			} else {
				if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
						|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
								.equals(flowProperties
										.getFirstBackSourceStep())) {
					bv.setOrderField("1," + sdf.format(new Date()));
				} else {
					bv.setOrderField("0," + sdf.format(new Date()));
				}
			}
		}
	}
	private void redTopBack(WorkItemView workItem, CarPendingAuditView bv) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
					|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
							.equals(flowProperties.getFirstBackSourceStep())) {
				bv.setFirstBackSourceStep(CarLoanSteps.GRANT_CONFIRMED
						.getName());
			}
			bv.setOrderField("0," + sdf.format(new Date()));
		}
	}

}
