package com.creditharmony.loan.car.carContract.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.carContract.view.CarSigningCheckBusinessView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 车借签署  处理流程回调
 * @Class Name CarSigningCheckEvent
 * @author 李静辉
 * @Create In 2016年2月18日
 */
@Service("ex_hj_carSigningCheck_CarLoanFlow")
public class CarSigningCheckEvent implements ExEvent{

	@Autowired
	private CarCustomerBankInfoDao carCustomerBankInfoDao;
	@Autowired
	private CarContractDao carContractDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao; 
	@Autowired
	private CarHistoryService carHistoryService;
	
	@Override
	public String getBeanName() {
		return null;
	}

	/**
	 * 流程转向前  回调   更新业务数据  设置工作流程转向路由
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		// 流程图路由
		// 取出在controller 中放入的业务数据，需要向下转换
		CarSigningCheckBusinessView  flowView = (CarSigningCheckBusinessView)workItem.getBv();
		//更新借款信息表状态
		CarLoanInfo carLoanInfo = new CarLoanInfo();
		carLoanInfo.setLoanCode(flowView.getLoanCode());
		if(CarLoanResponses.SIGN_GIVE_UP.getCode().equals(workItem.getResponse())){
			//设置工作流 状态为 客户放弃
			flowView.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			carLoanInfo.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			carHistoryService.saveCarLoanStatusHis(flowView.getLoanCode(), CarLoanSteps.SIGN.getCode(), 
					CarLoanOperateResult.CONFIRMED_SIGN_CUSTOMER__GIVE_UP.getCode(), "", CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		}else if(CarLoanResponses.TO_MAKE_CONTRACT.getCode().equals(workItem.getResponse())){
			CarContract carContract = flowView.getCarContract();
			//设置工作流 状态为 待制作合同
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_PRODUCED_CONTRACT.getCode());
			flowView.setContractAmount(carContract.getContractAmount().doubleValue());
			//TODO 保存银行卡信息
			flowView.getCarCustomerBankInfo().setLoanCode(flowView.getLoanCode());
			carCustomerBankInfoDao.update(flowView.getCarCustomerBankInfo());
			//更新合同表信息
			Date conDate = carContract.getContractDueDay();
			flowView.setContractFactDay(conDate);
			carContract.setContractFactDay(conDate);
			carContract.setContractReplayDay(DateUtils.addDays(conDate, 29));
			carContract.setContractEndDay(DateUtils.addDays(conDate, carContract.getContractMonths() - 1));
			carContract.setLoanCode(flowView.getLoanCode());
			carContractDao.update(carContract);
			carLoanInfo.setDictLoanStatus(CarLoanStatus.PENDING_PRODUCED_CONTRACT.getCode());
			carHistoryService.saveCarLoanStatusHis(flowView.getLoanCode(), CarLoanSteps.SIGN.getCode(),
					CarLoanOperateResult.CONFIRMED_SIGN_SUCCESS.getCode(), "", CarLoanStatus.PENDING_PRODUCED_CONTRACT.getCode());
			
		   //标红置顶提交下一步相关业务
           redTopCommit(workItem, flowView); 
		}else if(CarLoanResponses.TO_FRIST_AUDIT.getCode().equals(workItem.getResponse())){
			//待确认签署退回
			flowView.setDictLoanStatus(CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode());
			flowView.setAuditAmount(0d);
			flowView.setAuditBorrowProductCode("");
			flowView.setAuditBorrowProductName("");
			flowView.setAuditLoanMonths(0);
			carLoanInfo.setDictLoanStatus(CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode());
			
			carLoanInfo.setRemark(flowView.getRemark());
			carHistoryService.saveCarLoanStatusHis(flowView.getLoanCode(), CarLoanSteps.SIGN.getCode(), 
					CarLoanOperateResult.CONFIRMED_SIGN_BACK.getCode(), "", CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode());
		}
		
		carLoanInfoDao.update(carLoanInfo);
		
	}

	private void redTopCommit(WorkItemView workItem,
			CarSigningCheckBusinessView flowView) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
           Object object = workItem.getFlowProperties();
           if(object != null){
                FlowProperties flowProperties = (FlowProperties)object;
                if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(CarLoanSteps.SIGN.getName(), 
                        CarLoanResponses.TO_MAKE_CONTRACT.getCode(), flowProperties.getFirstBackSourceStep())){
                	flowView.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
                	flowView.setOrderField("1," + sdf.format(new Date()));
                }else{
                    if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
                    	flowView.setOrderField("1," + sdf.format(new Date()));
                    }else{
                    	flowView.setOrderField("0," + sdf.format(new Date()));
                    }
                }
            }
	}

}
