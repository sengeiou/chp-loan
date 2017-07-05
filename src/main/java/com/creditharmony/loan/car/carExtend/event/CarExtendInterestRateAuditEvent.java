package com.creditharmony.loan.car.carExtend.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carExtend.event.common.BackOrCommitUtil;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;

/**
 * 车借展期利率审核  处理流程回调
 * @Class Name CarExtendInterestRateAudit
 * @author 申诗阔
 * @Create In 2016年3月9日
 */
@Service("ex_hj_carExtendInterestRateAudit_CarLoanFlow")
public class CarExtendInterestRateAuditEvent extends CarExtendWorkFlowCommonService implements ExEvent{
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	@Autowired
	private CarContractDao carContractDao;
	
	@Override
	public String getBeanName() {
		return null;
	}

	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		String response = workItem.getResponse();
		// 取出在controller 中放入的业务数据，需要向下转换
		CarCheckRateBusinessView flowView = (CarCheckRateBusinessView) workItem.getBv();
		String stepName = workItem.getStepName();
		if (response.equals(CarLoanResponses.TO_SIGN.getCode())) { // 展期审核费率 通过
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode());	// 设置借款状态
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_INTEREST_RATE_PASS.getCode());
			//设置轮询检测的超时日期。第一次通过费率审核时，以上一次的合同到期日的第8个自然日为超时日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			//流程当前超时日期
			Integer timeoutCheckStage = flowView.getTimeoutCheckStage();
			try {
				//第一费率审核通过,设置超时日期，并开启轮询检测
				if(timeoutCheckStage == null || timeoutCheckStage == 0 ){
					String loanCode = flowView.getLoanCode();
					CarLoanInfo thisLI = carLoanInfoDao.selectByLoanCode(loanCode);
					CarLoanInfo lastLI = carLoanInfoDao.get(thisLI.getLoanAdditionalApplyid());
					CarContract lastCon = carContractDao.selectByLoanCode(lastLI.getLoanCode());
					Date currDate = sdf.parse(sdf.format(lastCon.getContractEndDay()));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currDate);
					calendar.add(Calendar.DAY_OF_MONTH, 8);
					flowView.setTimeOutFlag("1");	//开启合同超时chp-roll轮询检测
					flowView.setTimeOutPoint(calendar.getTime());
					flowView.setTimeoutCheckStage(1);
				}
				//标红置顶处理(提交)
				BackOrCommitUtil.redTopCommit(workItem, flowView, CarLoanSteps.RATE_CHECK.getName());
			
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else if (response.equals(CarLoanResponses.BACK_END_AUDIT.getCode())){ // 展期审核费率 退回
			flowView.setApplyStatusCode(CarLoanStatus.PENDING_AUDIT_INTEREST_RATE_BACK.getCode());
			flowView.setOperResultName(CarLoanOperateResult.EXTEND_INTEREST_RATE_BACK.getCode());
			//标红置顶处理(退回)
			BackOrCommitUtil.redTopBack(workItem, flowView, CarLoanSteps.RATE_CHECK.getName());
		}
		carContractHandle(flowView, stepName);
	}

}
