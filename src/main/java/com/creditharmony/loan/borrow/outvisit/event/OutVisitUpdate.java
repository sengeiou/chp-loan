/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.outvisit.eventOutVisitUpdate.java
 * @Create By 王彬彬
 * @Create In 2016年1月23日 下午1:51:58
 */
package com.creditharmony.loan.borrow.outvisit.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.loan.type.RejectDepartment;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.outvisit.enity.OutsideTaskList;
import com.creditharmony.loan.borrow.outvisit.service.OutVisitService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowStepName;

/**
 * @Class Name OutVisitUpdate
 * @author 王彬彬
 * @Create In 2016年1月23日
 */
@Service("ex_hj_loanFlow_AllocationOutTask")
public class OutVisitUpdate extends BaseService implements ExEvent {

	@Autowired
	private HistoryService historyService;
	@Autowired
	private OutVisitService outVisitService;	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 外访更新借款状态
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		LaunchView uploadView = (LaunchView) workItem.getBv();

		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(workItem.getBv().getApplyId());
		loanInfo.setDictLoanStatus(uploadView.getLoanInfo().getDictLoanStatus());
		loanInfo.setLoanCode(uploadView.getLoanInfo().getLoanCode());
		loanInfo.setLoanSurveyEmpId(uploadView.getLoanInfo().getLoanSurveyEmpId());
		loanInfo.setVisitFlag(uploadView.getLoanInfo().getVisitFlag());
		
		if (LoanFlowStepName.OUT_VISIT_ALLOCATE.equals(workItem.getStepName())) {
			historyService.saveLoanStatus(loanInfo, "分配外访任务", "成功", "");
			logger.info("world 分配外访任务，外访专员："+uploadView.getLoanInfo().getLoanSurveyEmpId());
			loanInfoDao.updateLoanInfo(loanInfo);
		} else if (LoanFlowStepName.OUT_VISIT_TODO.equals(workItem
				.getStepName())) {
			if (RejectDepartment.LOAN_GIVE.getCode().equals(
					loanInfo.getVisitFlag())) {
				historyService.saveLoanStatus(loanInfo, "客户放弃", "成功", "");
			} else if (RejectDepartment.LOAN_REJECT.getCode().equals(
					loanInfo.getVisitFlag())) {
				historyService.saveLoanStatus(loanInfo, "门店拒绝", "成功", loanInfo.getRemark());
			} else {
				OutsideTaskList outsideTaskList = new OutsideTaskList();
				outsideTaskList.setLoanCode(uploadView.getLoanInfo().getLoanCode());
				outsideTaskList.setItemDistance(uploadView.getItemDistance());
				outsideTaskList.setSurveyEmpId(uploadView.getOutSideUserCode());
				outsideTaskList.setSurveyEndTime(DateUtils.parseDate(uploadView.getVisitFinishTime()));
				outVisitService.updateOutVisit(outsideTaskList);
				
				historyService.saveLoanStatus(loanInfo, "外访任务完成", "成功", "");
				logger.info("world 外访任务完成，外访专员："+uploadView.getOutSideUserCode());
			}
			
		}

	}

}
