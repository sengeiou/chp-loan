package com.creditharmony.loan.car.carGrant.event;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.CardOrBookType;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.fortune.type.CreditSrc;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.ProfType;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.constants.PlatNameConst;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeMoneyInfoEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesMoneyEx;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.consts.CarDeductWays;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarRefundStatus;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.dao.CarLoanGrantDao;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.dao.CarRefundAuditDao;
import com.creditharmony.loan.car.common.dao.CarUrgeServicesMoneyDao;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanGrant;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarRefundInfo;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.car.creditorRights.dao.CreditorRightsDao;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.channel.finance.dao.CarFinancialBusinessDao;
import com.creditharmony.loan.common.consts.SystemSetFlag;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.sync.data.fortune.ForuneSyncCreditorService;

/**
 * 
 * @Class Name CarGrantLoanChangeEx
 */
@Service("ex_cj_loanflow_carGrantLoanChange")
public class CarGrantLoanChangeEx extends BaseService implements ExEvent {
	@Autowired
	private CarLoanGrantDao loanGrantDao;	
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;
	@Autowired
	CarUrgeServicesMoneyDao carUrgeServicesMoneyDao;
	@Autowired
	CarRefundAuditDao carRefundAuditDao;
	@Autowired
	CarCustomerBankInfoDao carCustomerBankInfoDao;
	@Autowired
	private CreditorRightsDao creditorRightsDao;	
	@Autowired
	private ForuneSyncCreditorService foruneSyncCreditorService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	@Autowired
	private CarFinancialBusinessDao carBusinessDao;
	@Autowired
	private SystemSetMaterDao sysDao;
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;

	
	
	/**
	 * 更新放款记录表，添加中间人id和放款人员编号,根据合同编号，放款记录表中没有applyId，放款通过，不通过都要更新状态，更新放款记录表
	 * 修改数据库中的字段信息
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		
		
	   CarGrantDealView gdv=(CarGrantDealView)workItem.getBv();
	   CarLoanGrant loanGrant=new CarLoanGrant();
	   CarLoanInfo loanInfo=new CarLoanInfo();
	   String applyId=gdv.getApplyId();
	   String dictStatus=gdv.getDictLoanStatus();
	   // 获得借款申请状态的code，存入数据库中，更新借款状态
	   loanInfo.setDictLoanStatus(dictStatus);
	
	   loanInfo.setApplyId(applyId);
	   
	   if (gdv.getContractCode()!=null) {
		   loanGrant.setContractCode(gdv.getContractCode());
	
	   }
	   // 插入中间人id
	   if (gdv.getMidId()!=null) {
		   loanGrant.setMidId(gdv.getMidId());
	   }
	   // 插入放款人员编号
	   if (gdv.getGrantPersons()!=null) {
		   loanGrant.setLendingUserId(gdv.getGrantPersons());
	   }
	   	// 放款途径
	   if (gdv.getDictLoanWay()!=null) {

	        loanGrant.setDictLoanWay(gdv.getDictLoanWay());
	   }

	  //放款记录退回原因记录
	  if(gdv.getDictBackMestype()!=null && gdv.getRemark() != null ){
		  loanGrant.setGrantBackMes(gdv.getDictBackMestype()+gdv.getRemark());
	  }
	  
	  
	  // 退回原因
	   if (gdv.getDictBackMestype()!=null) {
		   String grantBackMes=gdv.getDictBackMestype();
		   loanGrant.setGrantBackMes(grantBackMes);
		   loanInfo.setDictBackMestype(grantBackMes);
	   }
	   
	   //借款信息表中的 备注
	   if (gdv.getRemark() != null) {
		   String remark = gdv.getRemark();
		   loanInfo.setRemark(remark);
	   }
	  
	   

	   // 放款时间，在放款过程中进行更新，放款审核时，
	   if (gdv.getLendingTime()!=null) {
		   loanGrant.setLendingTime(gdv.getLendingTime());
	   }
	
	   // 回执结果，放款结果
	   if (gdv.getGrantRecepicResult()!=null) {
		   String grantRecepicResult=gdv.getGrantRecepicResult();
	       loanGrant.setGrantRecepicResult(grantRecepicResult);
	   }
	
	   // 线上放款失败原因
	   if (gdv.getGrantFailResult() != null) {
		   loanGrant.setGrantFailResult(gdv.getGrantFailResult());
	   }
	   // 审核人员
	   if (gdv.getCheckEmpId()!=null) {
		   loanGrant.setCheckEmpId(gdv.getCheckEmpId());
	   }
	   // 审核结果
	  if (gdv.getCheckResult()!=null) {
		 
	     loanGrant.setCheckResult(gdv.getCheckResult());
	  	} 
	   // 审核时间
	   if (gdv.getCheckTime()!=null) {
		loanGrant.setCheckTime(gdv.getCheckTime());
	   }
	   // 设置放款失败金额
	   if (gdv.getGrantFailAmount() != 0) {
		   loanGrant.setGrantFailAmount(new BigDecimal(gdv.getGrantFailAmount()));
	   }
	   
	   if (gdv.getContractCode()!=null) {
		   // 调用方法直接更新
		   loanGrantDao.updateLoanGrant(loanGrant);
	
	   }

	   // 调用方法更新借款状态
	   loanGrantDao.updateCarStatus(loanInfo);
	   
		// 标红置顶业务处理
		if (CarLoanSteps.CARD_NUMBER.getName().equals(workItem.getStepName())) { // 分配卡号节点
			if (CarLoanResponses.TO_GRANT.getCode().equals(
					workItem.getResponse())) { // 分配卡号通过
				// 标红置顶提交下一步相关业务
				redTopCommit(workItem, gdv, CarLoanSteps.CARD_NUMBER.getName(),
						CarLoanResponses.TO_GRANT.getCode());
			} else if (CarLoanResponses.BACK_GRANT_CONFIRM.getCode().equals(
					workItem.getResponse())) { // 退回款项确认
				gdv.setDictLoanStatus(CarLoanStatus.PENDING_LOAN_CONFIRMATION.getCode());
				//如果退回原因是风险客户或者为客户主动放弃，则退回”款项确认“节点时，不能进行办理
				if("6".equals(gdv.getDictBackMestype()) || "7".equals(gdv.getDictBackMestype())){
					gdv.setCanHandle(1);
				}
				// 标红置顶退回相关业务
				redTopBack(workItem, gdv, CarLoanSteps.CARD_NUMBER.getName());
			}
		} else if (CarLoanSteps.GRANT.getName().equals(workItem.getStepName())) { // 放款节点
			if (CarLoanResponses.TO_GRANT_AUDIT.getCode().equals(
					workItem.getResponse())) { // 放款通过
				// 标红置顶提交下一步相关业务
				redTopCommit(workItem, gdv, CarLoanSteps.GRANT.getName(),
						CarLoanResponses.TO_GRANT_AUDIT.getCode());
			} else if (CarLoanResponses.BACK_GRANT_CONFIRM.getCode().equals(
					workItem.getResponse())) { // 放款退回
				// 标红置顶退回相关业务
				redTopBack(workItem, gdv, CarLoanSteps.GRANT.getName());
			}
		} else if (CarLoanSteps.GRANT_AUDIT.getName().equals(
				workItem.getStepName())) { // 放款审核节点
			if (CarLoanResponses.TO_CREDIT_SEND.getCode().equals(
					workItem.getResponse())) { // 放款审核 通过
				// 标红置顶提交下一步相关业务
				redTopCommit(workItem, gdv, CarLoanSteps.GRANT_AUDIT.getName(),
						CarLoanResponses.TO_CREDIT_SEND.getCode());
			} else if (CarLoanResponses.BACK_GRANT_CONFIRM.getCode().equals(
					workItem.getResponse())) { // 放款审核退回
				// 标红置顶退回相关业务
				redTopBack(workItem, gdv, CarLoanSteps.GRANT_AUDIT.getName());
			}
		}

	   //对历史记录进行操作
		if (StringUtils.isNotEmpty(workItem.getResponse())) {
			// 分配卡号提交 添加历史
			if (workItem.getResponse().equals(
					CarLoanResponses.TO_GRANT.getCode())) {
				if (null != gdv.getSignUpFlag() && !"".equals(gdv.getSignUpFlag())
						&& YESNO.NO.getCode().equals(gdv.getSignUpFlag())) {
					saveCarLoanStatusHis(gdv.getLoanCode(),
							CarLoanSteps.CARD_NUMBER.getCode(),
							CarLoanOperateResult.DISCART_FAIL.getCode(), "",
							CarLoanStatus.PENDING_LOAN.getCode());
				} else{
					saveCarLoanStatusHis(gdv.getLoanCode(),
							CarLoanSteps.CARD_NUMBER.getCode(),
							CarLoanOperateResult.SUCCESS.getCode(), "",
							CarLoanStatus.PENDING_LOAN.getCode());
				}
			}
			// 分配卡号退回 添加历史
			if (workItem.getResponse().equals(
					CarLoanResponses.BACK_GRANT_CONFIRM.getCode()) && CarLoanSteps.CARD_NUMBER.getName().equals(
							workItem.getStepName())) {
				saveCarLoanStatusHis(gdv.getLoanCode(),
						CarLoanSteps.CARD_NUMBER.getCode(),
						CarLoanOperateResult.BACK_TO_LOAN_CONFIRMATION.getCode(), "",
						CarLoanStatus.PENDING_LOAN_CONFIRMATION.getCode());
				// 添加放款退回原因code ----------------------
				gdv.setGrantBackResultCode(gdv.getDictBackMestype());
			}
			
			
			// 放款成功 添加历史
			if (workItem.getResponse().equals(
					CarLoanResponses.TO_GRANT_AUDIT.getCode())) {
				CarUrgeMoneyInfoEx urgeMoneyInfoEx = loanGrantDao
						.selectUrgeMoney(gdv.getContractCode());
				CarUrgeServicesMoney carUrgeServicesMoney = new CarUrgeServicesMoney();
				carUrgeServicesMoney.setrGrantId(urgeMoneyInfoEx
						.getrGrantId());
				CarUrgeServicesMoney isCusm = carUrgeServicesMoneyDao
						.findCurrentCarUrgeMoneyByGrantId(carUrgeServicesMoney);
				// 开始自动划扣
				CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoDao
						.selectCarCustomerBankInfo(gdv.getLoanCode());
				// 默认的签约平台
				String bankSigningPlatform = carCustomerBankInfo
						.getBankSigningPlatform();
				HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
				// 车借服务费自动划扣标识
				hashMap.put("sysFlag",
						SystemSetFlag.CAR_SERVICES_SETTING_FLAG);
				String sysValue = carUrgeServicesMoneyDao
						.selectSystemSetting(hashMap);

				// 发送时的规则
				String ruleString = "";
				// 跳转时的规则
				String deductJumpRule = "";
				SystemSetting rules = new SystemSetting();
				RepaymentProcess dealStep = null;
				// 根据签约平台获得跳转规则,
				if (DeductPlat.ZHONGJIN.getCode().equals(
						bankSigningPlatform)) {
					rules.setSysFlag(PlatNameConst.ZJ);
					rules = sysDao.get(rules);
					ruleString = rules.getSysValue();
					deductJumpRule = rules.getSysValue();

					dealStep = RepaymentProcess.SEND_ZHONGJIN;
				} else if (DeductPlat.FUYOU.getCode().equals(
						bankSigningPlatform)) { // 签约平台为富友

					rules.setSysFlag(PlatNameConst.FY);
					rules = sysDao.get(rules);
					ruleString = rules.getSysValue();
					deductJumpRule = rules.getSysValue();

					dealStep = RepaymentProcess.SEND_FUYOU;
				} else if (DeductPlat.TONGLIAN.getCode().equals(
						bankSigningPlatform)) { // 签约平台为通联

					rules.setSysFlag(PlatNameConst.TL);
					rules = sysDao.get(rules);
					ruleString = rules.getSysValue();
					deductJumpRule = rules.getSysValue();

					dealStep = RepaymentProcess.SEND_TONGLIAN;
				} else {
					rules.setSysFlag(PlatNameConst.HYL);
					rules = sysDao.get(rules);
					ruleString = rules.getSysValue();
					deductJumpRule = rules.getSysValue();
					dealStep = RepaymentProcess.SEND_HYL;
				}
				SystemSetting param = new SystemSetting();
				param.setSysFlag(GrantCommon.CAR_AUTO_DEDUCTS);
				param = sysDao.get(param);
				CarUrgeServicesMoney cusm;
				urgeMoneyInfoEx.setDictDealType(bankSigningPlatform);
				urgeMoneyInfoEx.setRuleString(ruleString);
				if (YESNO.YES.getCode().equals(param.getSysValue())) {
					// 设置催收主表中自动划扣标识为是
					urgeMoneyInfoEx.setAutoDeductFlag(YESNO.YES.getCode());
				}else{
					urgeMoneyInfoEx.setAutoDeductFlag(YESNO.NO.getCode());
				}
			   if (isCusm !=null) {// 如果插入过催收服务费，更新
				   urgeMoneyInfoEx.setUrgeId("'"+isCusm.getId()+"'");
			       cusm = updateUrge(urgeMoneyInfoEx);
			   }else{// 第一次插入催收服务费
			       cusm = insertUrge(urgeMoneyInfoEx);
			   }
				// 如果自动划扣标识为是，更新催收主表中该单子的自动划扣标识
				HashMap<Object, Object> deductsDeducts = new HashMap<Object, Object>();
				deductsDeducts.put("loanCode", gdv.getLoanCode());
				List<DeductReq> deductReqList = queryAutomaticDeductReq(
						deductsDeducts, ruleString);
				// 5.29 暂时没有滚动划扣
				if (ArrayHelper.isNotEmpty(deductReqList) && cusm != null
						&& deductReqList.size() > 0 && YESNO.YES.getCode().equals(param.getSysValue())) {
					BigDecimal amount = (cusm.getUrgeMoeny().subtract(cusm
							.getUrgeDecuteMoeny())).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					// 设置自动划扣的金额
					deductReqList.get(0).setAmount(amount);
					// 设置催收主表id
					deductReqList.get(0).setBatId(cusm.getId());
					// 发送到批处理
					DeResult t = TaskService.addTask(deductReqList.get(0));
					try {
						if (t.getReCode().equals(
								ResultType.ADD_SUCCESS.getCode())) {
							// 将单子的划扣平台更新为要发送的平台，更新催收主表的划扣平台
							// 将催收主表中回盘结果设置为处理中
							CarUrgeServicesMoney urge = new CarUrgeServicesMoney();
							urge.setId("'" + cusm.getId() + "'");
							urge.setDictDealStatus(UrgeCounterofferResult.PROCESS
									.getCode());
							urge.setSplitFailResult("");
							urge.setDictDealType(bankSigningPlatform);
							urge.setUrgeDecuteDate(new Date());
							CarUrgeServicesMoneyEx delurgeEx = new CarUrgeServicesMoneyEx();
							// 删除拆分表中已经存在的单子,拆分表中处理状态为划扣失败的单子

							delurgeEx.setUrgeId("'" + cusm.getId() + "'");
							delurgeEx
									.setSplitResult(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
							List<CarUrgeServicesMoneyEx> delList = carUrgeServicesMoneyDao
									.selProcess(delurgeEx);
							if (ArrayHelper.isNotEmpty(delList)) { // 如果拆分表中有失败的数据，进行删除
								delSplit(delList);
							}
							TaskService.commit(t.getDeductReq());
							carUrgeServicesMoneyDao.updateUrge(urge);
						} else {
							CarUrgeServicesMoney urge = new CarUrgeServicesMoney();
							urge.setId("'" + cusm.getId() + "'");
							// 将催收主表中回盘结果设置为划扣失败
							urge.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED
									.getCode());
							urge.setDictDealType(bankSigningPlatform);
							urge.setUrgeDecuteDate(new Date());
							urge.setReturnLogo(YESNO.NO.getCode());
							carUrgeServicesMoneyDao.updateUrge(urge);
							TaskService.rollBack(t.getDeductReq());
						}
					} catch (Exception e) {
						e.printStackTrace();
						TaskService.rollBack(t.getDeductReq());
					}
				}
				saveCarLoanStatusHis(gdv.getLoanCode(),
						CarLoanSteps.GRANT.getCode(),
						CarLoanOperateResult.SUCCESS.getCode(),
						gdv.getDictBackMestype(),
						CarLoanStatus.LOAN_AUDIT.getCode());

			}
			
			// 放款退回 添加历史
			if (workItem.getResponse().equals(
					CarLoanResponses.BACK_GRANT_CONFIRM.getCode()) && CarLoanSteps.GRANT.getName().equals(
							workItem.getStepName())) {
				saveCarLoanStatusHis(gdv.getLoanCode(),
						CarLoanSteps.GRANT.getCode(),
						CarLoanOperateResult.BACK_TO_LOAN_CONFIRMATION.getCode(),
						gdv.getRemark(), CarLoanStatus.PENDING_LOAN_CONFIRMATION.getCode());
	
				// 添加放款退回原因code ----------------------
				gdv.setGrantBackResultCode(gdv.getDictBackMestype());
			
			}
						
			// 放款审核 添加历史
			if (workItem.getResponse().equals(
					CarLoanResponses.TO_CREDIT_SEND.getCode())) {
				// 向债权表中插入数据
				CarLoanInfo info = carLoanInfoService.selectByApplyId(gdv
						.getApplyId());
				if (info != null && 
						 CarLoanThroughFlag.P2P.getCode().equals(
								info.getLoanFlag())) {
						CreditorRights creditorRights = loanGrantDao
								.selectCreditorRights(gdv.getApplyId());
						if (creditorRights != null) {
							creditorRights.preInsert();
							creditorRights.setIssendWealth(YESNO.NO.getCode());
							// 抵押车
							creditorRights.setOccupationCase(ProfType.PLEDGE_CAR
									.getCode());
							creditorRights.setCreditorRigthSource(CreditSrc.CJ.value);
						}
						creditorRights.setRightsType(CreditorRights.RIGHTS_TYPE_SYS);
						creditorRights.setCreditType(CreditorRights.CREDIT_TYPE_HASLOAD);
						String customerName = creditorRights.getLoanCustomerName();
						String customerCert = creditorRights.getCustomerCertNum();
						List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectByLoanCodeNoConvers(gdv.getLoanCode());
						for(CarLoanCoborrower carLoanCoborrower:carLoanCoborrowers){
							customerName+=";" + carLoanCoborrower.getCoboName();
							customerCert+=";" + carLoanCoborrower.getCertNum();
						}
						if(customerName.contains(";")){
							creditorRights.setCustomerCobo(Global.YES);
						}
						creditorRights.setLoanCustomerName(customerName);
						creditorRights.setCustomerCertNum(customerCert);
						creditorRightsDao.insert(creditorRights);
				}
				saveCarLoanStatusHis(gdv.getLoanCode(),
						CarLoanSteps.GRANT_AUDIT.getCode(),
						CarLoanOperateResult.GRANT_AUDIT_SUCCESS.getCode(),
						gdv.getDictBackMestype(), null);
			}
			
			// 放款审核退回 添加历史
			if (workItem.getResponse().equals(
					CarLoanResponses.BACK_GRANT_CONFIRM.getCode()) && CarLoanSteps.GRANT_AUDIT.getName().equals(
							workItem.getStepName())) {
				saveCarLoanStatusHis(gdv.getLoanCode(),
						CarLoanSteps.GRANT_AUDIT.getCode(),
						CarLoanOperateResult.BACK_TO_LOAN_CONFIRMATION.getCode(),
						gdv.getRemark(), CarLoanStatus.PENDING_LOAN_CONFIRMATION.getCode());

				// 添加放款退回原因code ----------------------
				gdv.setGrantBackResultCode(gdv.getDictBackMestype());

				// 放款审核退回时，插入催收服务费退款表
				CarLoanGrant carLoanGran = new CarLoanGrant();
				carLoanGran.setLoanCode(gdv.getLoanCode());
				CarLoanGrant carLoanGrantEx = loanGrantDao
						.findGrantByLoanCode(carLoanGran);
				if (carLoanGrantEx == null) {
					throw new RuntimeException(
							"放款审核退回异常，无法查找相应借款编码的放款记录，合同编码LoanCode="
									+ gdv.getLoanCode());
				}
				CarUrgeServicesMoney urg = new CarUrgeServicesMoney();
				urg.setrGrantId(carLoanGrantEx.getId());
				CarUrgeServicesMoney carUrgeServicesMoney = carUrgeServicesMoneyDao
						.findCurrentCarUrgeMoneyByGrantId(urg);
				if (carUrgeServicesMoney == null) {
					throw new RuntimeException(
							"放款审核退回异常，无法查找相应放款记录的催收服务费记录，放款记录id，GrantId="
									+ carLoanGrantEx.getId());
				}
				int r = carUrgeServicesMoney.getUrgeDecuteMoeny()
						.compareTo(BigDecimal.ZERO);
				// 放款回执为失败，但是已经划扣了费用，向退款表中插入数据
				if (r == 1) {
					CarRefundInfo refundInfo = new CarRefundInfo();
					refundInfo.preInsert();
					refundInfo.setrChargeId(carUrgeServicesMoney
							.getId());
					refundInfo.setReturnAmount(carUrgeServicesMoney
							.getUrgeDecuteMoeny().doubleValue());
					refundInfo
							.setReturnStatus(CarRefundStatus.CAR_RETURN_STATUS_W
									.getCode());
					refundInfo.setReturnIntermediaryId(carLoanGrantEx
							.getMidId());
					refundInfo.setContractCode(carLoanGrantEx
							.getContractCode());
					refundInfo
							.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_W
									.getCode());
					carRefundAuditDao.insertCarRefundInfo(refundInfo);
				}
			}
		}
		   
	}

	private void redTopBack(WorkItemView workItem, CarGrantDealView gdv,
			String cureentStep) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		   Object object = workItem.getFlowProperties();
		   if(object != null){
		       FlowProperties flowProperties = (FlowProperties)object;
		       if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
		    		   flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
		           gdv.setFirstBackSourceStep(cureentStep);
		        }
		       gdv.setOrderField("0," + sdf.format(new Date()));
		   }
	}

	private void redTopCommit(WorkItemView workItem, CarGrantDealView gdv,
			String currentStep, String currentResponse) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		    Object object = workItem.getFlowProperties();
		    if(object != null){
		        FlowProperties flowProperties = (FlowProperties)object;
		        if(BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(currentStep, 
		        		currentResponse, flowProperties.getFirstBackSourceStep())){
		        	gdv.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
		        	gdv.setOrderField("1," + sdf.format(new Date()));
		        }else{
		            if(StringUtils.isEmpty(flowProperties.getFirstBackSourceStep()) || 
		            		 flowProperties.getFirstBackSourceStep().contains(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE)){
		            	gdv.setOrderField("1," + sdf.format(new Date()));
		            }else{
		            	gdv.setOrderField("0," + sdf.format(new Date()));
		            }
		        }
		    }
	}
	
	/**
	 * 对要进行导出的单子，将拆分表中划扣失败的单子重新合并成要拆分的集合，同时删除拆分表中划扣失败的单子
	 * 2016年3月1日
	 * @param delList 拆分表中存在的
	 * @return none
	 */
	public void delSplit(List<CarUrgeServicesMoneyEx> delList){

		if (delList.size() >0) {
			// 获得拆分list
			String id = null;
			StringBuilder parameterSplit = new StringBuilder();
			for (int i = 0; i < delList.size(); i++) {
				parameterSplit.append("'" + delList.get(i).getId() + "',");
			}
			id = parameterSplit.toString().substring(0,parameterSplit.lastIndexOf(","));
	
			// 删除存在的list
			carUrgeServicesMoneyDao.delProcess(id);
			}

		}
	
	/**
	 *  查询要划扣的数据  DeductReq  车借
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return List<DeductReq> 要划扣的数据 
	 */
	public List<DeductReq> queryAutomaticDeductReq(HashMap<Object, Object> hashMap,String rule){
		
        //　取得规则
		List<DeductReq> deductReqList =  carUrgeServicesMoneyDao.queryAutomaticDeductReq(hashMap);
		
		if (ArrayHelper.isNotEmpty(deductReqList)) {
		
			for (int i = 0; i < deductReqList.size(); i++) {
				deductReqList.get(i).setDeductFlag(DeductFlagType.COLLECTION.getCode());
				// 设置划扣规则
				deductReqList.get(i).setRule(rule);
				//  系统处理ID
				deductReqList.get(i).setSysId(CarDeductWays.CJ_02.getCode());
				// 设置账户类型
				deductReqList.get(i).setAccountType(CardOrBookType.BANKCARD.getCode());
			}
		}
		return deductReqList;
		
	}
	
	  /**
	   * 插入催收服务费表,放款成功之后对催收服务费进行插入
	   * 2016年2月23日
	   * @param CarUrgeMoneyInfoEx 
	   * @return 是否插入成功
	   */
	public CarUrgeServicesMoney insertUrge(CarUrgeMoneyInfoEx urgeMoneyInfoEx){

		CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
		urgeServicesMoney.setrGrantId(urgeMoneyInfoEx.getrGrantId());
		urgeServicesMoney.preInsert();
		urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
		urgeServicesMoney.setTimeFlag(YESNO.NO.getCode());
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PREPAYMENT.getCode());
		urgeServicesMoney.setUrgeDecuteMoeny(new BigDecimal(0));
		urgeServicesMoney.setUrgeMoeny(new BigDecimal(urgeMoneyInfoEx.getDeductAmount()));
		urgeServicesMoney.setDeductJumpRule(urgeMoneyInfoEx.getRuleString());
		urgeServicesMoney.setAutoDeductFlag(urgeMoneyInfoEx.getAutoDeductFlag());
		urgeServicesMoney.setDictDealType(urgeMoneyInfoEx.getDictDealType());
	    carUrgeServicesMoneyDao.insertUrge(urgeServicesMoney);
	    
	    return urgeServicesMoney;
	}
	
	  /**
	   * 插入催收服务费表,放款成功之后对催收服务费进行插入
	   * 2016年2月23日
	   * @param CarUrgeMoneyInfoEx 
	   * @return 是否插入成功
	   */
	public CarUrgeServicesMoney updateUrge(CarUrgeMoneyInfoEx urgeMoneyInfoEx){

		CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
		urgeServicesMoney.setrGrantId(urgeMoneyInfoEx.getrGrantId());
		urgeServicesMoney.setTimeFlag(YESNO.NO.getCode()) ;
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PREPAYMENT.getCode());
		urgeServicesMoney.preUpdate();
		urgeServicesMoney.setId(urgeMoneyInfoEx.getUrgeId());
		urgeServicesMoney.setUrgeDecuteMoeny(new BigDecimal(0));
		urgeServicesMoney.setUrgeMoeny(new BigDecimal(urgeMoneyInfoEx.getDeductAmount()));
		urgeServicesMoney.setDeductJumpRule(urgeMoneyInfoEx.getRuleString());
		urgeServicesMoney.setAutoDeductFlag(urgeMoneyInfoEx.getAutoDeductFlag());
		urgeServicesMoney.setDictDealType(urgeMoneyInfoEx.getDictDealType());
		carUrgeServicesMoneyDao.updateUrge(urgeServicesMoney);
		urgeServicesMoney.setId(urgeServicesMoney.getId().replaceAll("'", ""));
	    return urgeServicesMoney;
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