package com.creditharmony.loan.car.carGrant.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesMoneyEx;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.dao.CarLoanGrantDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.dao.CarPaybackSplitDao;
import com.creditharmony.loan.car.common.dao.CarUrgeServicesMoneyDao;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.car.common.util.BPMDispatchService;

/**
 * 车借放款回执更新放款记录、提交流程服务
 *
 */


@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarGrantRecepicService {
	
	@Autowired
	BPMDispatchService bpmService;
	@Autowired
	private CarUrgeServicesMoneyDao carUrgeServicesMoneyDao;
	@Autowired
	CarPaybackSplitDao paybackSplitDao;
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;
	@Autowired
	private CarLoanGrantDao carLoanGrantDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	
	
	/*-- 成功（借款状态为“已处理”的申请才可以更新）
	    -- 更改借款状态为“放款审核”
	    -- 更新放款记录表回盘结果等内容
	    -- 插入催收服务费信息表（T_JK_URGE_SERVICES_AMOUNT）
	    -- 提交流程
	-- 失败（借款状态为“已处理”的申请才可以更新）
	    -- 更改借款状态为“待放款”
	    -- 更新放款记录表回盘结果等内容*/
	/**
	 * 
	 * @param businessId  合同编码
	 * @param deductFailMoney  失败金额
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void grantReceic(String businessId,String deductFailMoney){
		
		double grantFailAmount = Double.parseDouble(deductFailMoney);
		if(grantFailAmount == 0){ //成功（借款状态为“已处理”的申请才可以更新）
			
			CarGrantDealView gqp = new CarGrantDealView();
			
			String applyId = carLoanGrantDao.selectCarApplyId(businessId);
			CarLoanInfo carLoanInfo = carLoanInfoDao.selectByApplyId(applyId);
			gqp.setApplyId(applyId);
			// 设置合同编号
			gqp.setContractCode(businessId);
			// 设置借款编码，插入历史的时候用
			gqp.setLoanCode(carLoanInfo.getLoanCode());
			// 设置单子状态，待放款审核
			gqp.setDictLoanStatus(CarLoanStatus.LOAN_AUDIT.getCode());
			// 放款回执结果，直接从字典表中取值
			gqp.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
			//设置放款时间
			gqp.setLendingTime(new Date());
			
			bpmService.dispatch(applyId, BPMDispatchService.NON, gqp);
		}else{ //失败（借款状态为“已处理”的申请才可以更新）
			
			CarGrantDealView gqp = new CarGrantDealView();
			
			String applyId = carLoanGrantDao.selectCarApplyId(businessId);
			CarLoanInfo carLoanInfo = carLoanInfoDao.selectByApplyId(applyId);
			gqp.setApplyId(applyId);
			// 设置合同编号
			gqp.setContractCode(businessId);
			// 设置借款编码，插入历史的时候用
			gqp.setLoanCode(carLoanInfo.getLoanCode());
			// 设置单子状态，待放款
			gqp.setDictLoanStatus(CarLoanStatus.LENDING_FAILURE.getCode());
			// 放款回执结果，直接从字典表中取值
			gqp.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
			// 设置放款失败原因
			gqp.setGrantFailResult(LoansendResult.LOAN_SENDED_FAILED.getName());
			// 设置放款失败金额
			gqp.setGrantFailAmount(grantFailAmount);
			// 设置放款时间
			gqp.setLendingTime(new Date());
			// 
			bpmService.dispatch(applyId, BPMDispatchService.NON, gqp);
			
	
		}
	}
	
	/**
	 * 线上划扣回调函数
	 * @param loanDeductEntity  
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void deductReceic(LoanDeductEntity loanDeductEntity){

		
		List<PaybackSplitEntityEx> paybackSplitList= loanDeductEntity.getSplitData();
		if (ArrayHelper.isNotEmpty(paybackSplitList)) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("paybackSplitList", paybackSplitList);
			paybackSplitDao.batchInsertDeductSplit(map);
			
			// 更新划扣主表 t_cj_urge_services_amount
			
			// 催收主表id
			String urgeId = paybackSplitList.get(0).getPaybackApplyId();
			CarUrgeServicesMoneyEx queryCarUrge = new CarUrgeServicesMoneyEx();
			queryCarUrge.setUrgeId(urgeId);
			List<CarUrgeServicesMoneyEx> queryUrgeList = carUrgeServicesMoneyDao.selectDeductsList(queryCarUrge);

			 // 更新催收服务费主表
			 
			 CarUrgeServicesMoney carUrgeServicesMoney = new CarUrgeServicesMoney();
			 carUrgeServicesMoney.setId("'"+urgeId+"'");
			 carUrgeServicesMoney.setModifyTime(new Date());
			 
			 if (ArrayHelper.isNotEmpty(queryUrgeList) && queryUrgeList.size() > 0) {
				  
				 // 设置划扣成功金额 
				 BigDecimal totalSucceedMoney = new BigDecimal(loanDeductEntity.getDeductSucceedMoney()).add(queryUrgeList.get(0).getUrgeDecuteMoeny());
				 carUrgeServicesMoney.setUrgeDecuteMoeny(totalSucceedMoney);
				 
				 if ( new Double(loanDeductEntity.getDeductFailMoney()).doubleValue() > 0) {
						carUrgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_FAILED.getCode());
						carUrgeServicesMoney.setSplitFailResult(loanDeductEntity.getFailReason());
					 for (int i = 0; i < paybackSplitList.size(); i++) {
							if (paybackSplitList.get(i).getSplitBackResult().equals(CounterofferResult.PAYMENT_FAILED.getCode())) {
								carUrgeServicesMoney.setDictDealType(paybackSplitList.get(i).getDealType());
							}
					 }
				}else{
					for (int i = 0; i < paybackSplitList.size(); i++) {
						if (paybackSplitList.get(i).getSplitBackResult().equals(CounterofferResult.PAYMENT_SUCCEED.getCode())) {
							carUrgeServicesMoney.setDictDealType(paybackSplitList.get(i).getDealType());
						}
					}
					carUrgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED.getCode());
				}
				 
			}
//			 
//			 carUrgeServicesMoney.setDictDealType(paybackSplitList.get(0).getDealType());
			 // 设置划扣日期
			 carUrgeServicesMoney.setUrgeDecuteDate(new Date());
			 
			carUrgeServicesMoneyDao.updateUrge(carUrgeServicesMoney);
					
					// 插入历史
					if (ArrayHelper.isNotEmpty(queryUrgeList)) {
						if (YESNO.NO.getCode().equals(queryUrgeList.get(0).getTimeFlag()) && new Double(loanDeductEntity.getDeductFailMoney()).doubleValue() == 0) {
							// 当日待划扣，划扣成功
							saveCarLoanStatusHis(queryUrgeList.get(0).getLoanCode(), CarLoanSteps.CURRENT_DEDUCTS.getCode(), 
									CarLoanOperateResult.DAY_DRAW_SUCCESS.getCode(), "", "");
						}else if (YESNO.NO.getCode().equals(queryUrgeList.get(0).getTimeFlag()) && new Double(loanDeductEntity.getDeductFailMoney()).doubleValue() > 0) {
							// 当日待划扣，划扣失败
							saveCarLoanStatusHis(queryUrgeList.get(0).getLoanCode(), CarLoanSteps.CURRENT_DEDUCTS.getCode(), 
									CarLoanOperateResult.PAST_DRAW_DEFEATED.getCode(), "", "");
						}
						else if (YESNO.YES.getCode().equals(queryUrgeList.get(0).getTimeFlag()) && new Double(loanDeductEntity.getDeductFailMoney()).doubleValue() == 0 ) {
							// 以往待划扣，划扣成功
							saveCarLoanStatusHis(queryUrgeList.get(0).getLoanCode(), CarLoanSteps.PAST_DEDUCTS.getCode(), 
									CarLoanOperateResult.PAST_DRAW_SUCCESS.getCode(), "", "");
						}
						else if (YESNO.YES.getCode().equals(queryUrgeList.get(0).getTimeFlag()) && new Double(loanDeductEntity.getDeductFailMoney()).doubleValue() > 0 ) {
							// 以往待划扣，划扣失败
							saveCarLoanStatusHis(queryUrgeList.get(0).getLoanCode(), CarLoanSteps.PAST_DEDUCTS.getCode(), 
									CarLoanOperateResult.PAST_DRAW_DEFEATED.getCode(), "", "");
						}else{
							if(YESNO.NO.getCode().equals(queryUrgeList.get(0).getTimeFlag())){
								saveCarLoanStatusHis(queryUrgeList.get(0).getLoanCode(), CarLoanSteps.CURRENT_DEDUCTS.getCode(), 
										CarLoanOperateResult.DAY_DRAW_DEFEATED.getCode(), "", "");
							}else{
								saveCarLoanStatusHis(queryUrgeList.get(0).getLoanCode(), CarLoanSteps.PAST_DEDUCTS.getCode(), 
										CarLoanOperateResult.PAST_DRAW_DEFEATED.getCode(), "", "");
							}
						}
						
					}
		}
		
	}
	
	/**
	 * 添加操作历史
	 * 
	 * @param loanCode
	 *            申请编号
	 * @param operateStep
	 *            操作步骤
	 * @param operateResult
	 *            操作结果(成功/失败)
	 * @param remark
	 *            备注
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveCarLoanStatusHis(String loanCode, String operateStep,
			String operateResult, String remark,String dictLoanStatus) {
		CarLoanStatusHis record = new CarLoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setLoanCode(loanCode);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 借款状态
		record.setDictLoanStatus(dictLoanStatus);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperatorRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
	   carLoanStatusHisDao.insert(record);
	}
	
	
}

