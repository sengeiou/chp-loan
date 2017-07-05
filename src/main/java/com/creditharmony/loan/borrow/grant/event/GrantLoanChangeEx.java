package com.creditharmony.loan.borrow.grant.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.SendFlag;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFileDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.GrantUrgeBackDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantHisDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantUrgeBackEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.trusteeship.service.GoldAccountCeilingService;
import com.creditharmony.loan.channel.finance.dao.FinancialBusinessDao;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.channel.goldcredit.service.GCCeilingService;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowStepName;
import com.creditharmony.loan.sms.service.ExpectCheatSmsService;
import com.google.common.collect.Maps;


/**
 * 
 * @Class Name GrantLoanChangeEx
 * @author 朱静越
 * @Create In 2015年12月6日
 */
@Service("ex_hj_loanflow_grantLoanChange")
public class GrantLoanChangeEx extends BaseService implements ExEvent {
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private GrantUrgeBackDao grantUrgeBackDao;
	@Autowired
	private UrgeServicesMoneyDao urgeServicesMoneyDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private FinancialBusinessDao businessDao;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private LoanGrantHisDao loanGrantHisDao;
	@Autowired
	private LoanBankDao loanBankDao;
	@Autowired
	private ContractFileDao contractFileDao;
	@Autowired
	private GCCeilingService gcCeilingService;//进行业务查询列表
	@Autowired
	private GoldAccountCeilingService goldAccountCeilingService;//金账户
	@Autowired
	private ExpectCheatSmsService expectCheatSmsService;
	
	/**
	 * 更新放款记录表，添加中间人id和放款人员编号,根据合同编号，放款记录表中没有applyId，放款通过，不通过都要更新状态，更新放款记录表
	 * 修改数据库中的字段信息
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		GrantDealView gdv = (GrantDealView) workItem.getBv();
		LoanGrant loanGrant = new LoanGrant();
		LoanGrantEx loanGrantEx = new LoanGrantEx();
		LoanInfo loanInfo = new LoanInfo();
		String applyId = gdv.getApplyId();
		String dictStatus = gdv.getDictLoanStatus();
		// 获得借款申请状态的code，存入数据库中，更新借款状态
		for (LoanApplyStatus s : LoanApplyStatus.values()) {
			if (s.getName().equals(dictStatus)) {
				dictStatus = s.getCode();
				loanInfo.setDictLoanStatus(dictStatus);
			}
		}
		loanInfo.setApplyId(applyId);
		loanInfo.setLoanCode(loanGrantDao.selLoanCode(applyId));
		// 调用方法更新借款状态
		if (StringUtils.isNotEmpty(dictStatus)) {
			loanInfo.preUpdate();
			loanGrantDao.updateStatus(loanInfo);
		}
		if (gdv.getContractCode() != null) {
			loanGrant.setContractCode(gdv.getContractCode());
			loanGrantEx = loanGrantDao.findGrant(loanGrant);
			// copy
			BeanUtils.copyProperties(loanGrantEx, loanGrant);
		}
		if (gdv.getMidId() != null) {
			loanGrant.setMidId(gdv.getMidId());
		}
		if (gdv.getLendingUserId() != null) {
			loanGrant.setLendingUserId(gdv.getLendingUserId());
		}
		// 放款途径
		if (gdv.getDictLoanWay() != null) {
			String dictLoanWay = gdv.getDictLoanWay();
			PaymentWay paymentWay = PaymentWay.parseByName(dictLoanWay);
			if(!ObjectHelper.isEmpty(paymentWay)){
				loanGrant.setDictLoanWay(paymentWay.getCode());
			}			
		}
		// 放款时的放款批次
		if (gdv.getGrantBatchCode() !=null) {
			loanGrant.setGrantBatch(gdv.getGrantBatchCode());
		}
		// 放款方式：线上线下
		if (gdv.getDictLoanType() != null) {
			loanGrant.setDictLoanType(gdv.getDictLoanType());
		}
		
		// 放款失败金额
		if (gdv.getGrantFailAmount() !=null) {
			loanGrant.setGrantFailAmount(new BigDecimal(gdv.getGrantFailAmount()));
		}
		// 审核人员
		if (gdv.getCheckEmpId() != null) {
			loanGrant.setCheckEmpId(gdv.getCheckEmpId());
		}
		// 放款审核结果
		if (gdv.getCheckResult() != null) {
			loanGrant.setCheckResult(gdv.getCheckResult());
		}
		// 放款审核退回原因
		if (gdv.getGrantBackMes() != null) {
			String grantBackMes = gdv.getGrantBackMes();
			loanGrant.setGrantBackMes(grantBackMes);
		}
		// 放款时间，在放款过程中进行更新，放款审核时，
		if (gdv.getLendingTime() != null) {
			loanGrant.setLendingTime(gdv.getLendingTime());
		}
		// 审核时间
		if (gdv.getCheckTime() != null) {
			loanGrant.setCheckTime(gdv.getCheckTime());
		}
		// 线上放款回盘时间
		if (gdv.getGrantBackDate() != null) {
			loanGrant.setGrantBackDate(gdv.getGrantBackDate());
		}
		// 中金，通联线上放款的提交划扣时间
		if (gdv.getSubmitDeductTime() != null) {
			loanGrant.setSubmitDeductTime(gdv.getSubmitDeductTime());
		}
		
		// 放款确认时的批次
		if (gdv.getGrantPch()!=null) {
			loanGrant.setGrantPch(gdv.getGrantPch());
		}
		// 线上放款失败原因，直接使用name
		if (gdv.getGrantFailResult() != null) {
			loanGrant.setGrantFailResult(gdv.getGrantFailResult());
		}
		// 回执结果，放款结果,获得code
		if (gdv.getGrantRecepicResult() != null) {
			String grantRecepicResult = gdv.getGrantRecepicResult();
			loanGrant.setGrantRecepicResult(grantRecepicResult);
			gdv.setGrantRecepicResult(grantRecepicResult);
		}		
		if(gdv.getEnterpriseSerialno()!=null){
		    loanGrant.setEnterpriseSerialno(gdv.getEnterpriseSerialno());
		}
		// 调用方法直接更新,因为是根据合同编号更新，所以判断合同编号不为空
		if (StringUtils.isNotEmpty(gdv.getContractCode())) {
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		// 如果标识有值，对导入或者上传回执结果的数据进行更新放款历史记录表
		if (StringUtils.isNotEmpty(gdv.getUpdGrantHisFlag())) {
			insertGrantHis(gdv);
		}
		
		// 分配卡号成功,节点的步骤名称为分配卡号，并且状态为放款
		if (LoanFlowStepName.DISTRIBUTION_CARD_NUMBER.equals(workItem.getStepName()) && 
				LoanApplyStatus.LOAN_TO_SEND.getName().equals(gdv.getDictLoanStatus())) {
			saveLoanStatusHis(loanInfo,workItem.getStepName(), GrantCommon.SUCCESS, "");
		}
		// 分配卡号退回，节点的步骤名称为分配卡号，并且状态为放款退回，更新合同
		if (LoanFlowStepName.DISTRIBUTION_CARD_NUMBER.equals(workItem.getStepName()) && 
				LoanApplyStatus.LOAN_SEND_RETURN.getName().equals(gdv.getDictLoanStatus())) {
			updBackReason(workItem);
			saveLoanStatusHis(loanInfo,GrantCommon.DIS_CARD_BACK, GrantCommon.SUCCESS,gdv.getGrantSureBackReason());
		}
		// 放款成功，历史插入,线下的和线上发送的时候
		if (YESNO.YES.getCode().equals(gdv.getGrantExportFlag())) {
			saveLoanStatusHis(loanInfo,workItem.getStepName(), GrantCommon.SUCCESS,"");
		}
		//TODO 大金融放款中，插入历史
		if (LoanApplyStatus.BIGFINANCE_GRANTING.getCode().equals(gdv.getDictLoanStatusCode())) {
			saveLoanStatusHis(loanInfo,gdv.getDictLoanStatus(), GrantCommon.SUCCESS,"大金融发送放款中的处理状态");
		}
		// 放款退回，历史插入，更新合同
		if (LoanFlowStepName.REMIT_MONEY.equals(workItem.getStepName()) && 
				LoanApplyStatus.LOAN_SEND_RETURN.getName().equals(gdv.getDictLoanStatus())) {
			updBackReason(workItem);
			saveLoanStatusHis(loanInfo,gdv.getDictLoanStatus(), GrantCommon.SUCCESS,gdv.getGrantSureBackReason());
		}
		if (StringUtils.isNotEmpty(workItem.getResponse())) {
			
			// 放款审核成功之后，对还款主表进行标识更新
			if (workItem.getResponse().equals(LoanFlowRoute.END)||workItem.getResponse().equals(LoanFlowRoute.GOLDCREDIT_END)) {
				loanGrantDao.updFlag(loanGrant);
				
				loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT.getCode());
				loanInfo.preUpdate();
				loanGrantDao.updateStatus(loanInfo);
				// 插入放款审核历史
				if (!ChannelFlag.JINXIN.getCode().equals(gdv.getLoanFlag())&&!ChannelFlag.ZCJ.getCode().equals(gdv.getLoanFlag())) {
					saveLoanStatusHis(loanInfo,GrantCommon.GRANT_AUDIT, VerityStatus.PASS.getName(),"");
				}
				// 大金融放款成功之后，记录历史，直接到【大金融已放款】
				if (ChannelFlag.ZCJ.getCode().equals(gdv.getLoanFlag())) {
					saveLoanStatusHis(loanInfo,GrantCommon.BIG_FIN_GRANT, GrantCommon.SUCCESS,"");
					if(gdv.getLender()!=null){
						Map map = new HashMap();
						map.put("lender", gdv.getLender());
						map.put("applyId", applyId);
						contractDao.updateLender(map);
					}
					// 如果借款协议id不为空的情况下，将借款协议insert到合同文件表中
					if (StringUtils.isNotEmpty(gdv.getDocId())) {
						insertContractFile(gdv);
					}
				}
				if (ChannelFlag.P2P.getCode().equals(gdv.getLoanFlag())) {
					// 调用方法
					FinancialBusiness finance =new FinancialBusiness();
					finance.preInsert();
					finance.setLoanCode(gdv.getLoanCode());
					businessDao.insertFinancialBusiness(finance);
				}
				
				if (LoanModel.TG.getCode().equals(gdv.getModel())) {
					// 调用方法 插入金账户列表 
					goldAccountCeilingService.insertGoldAccount(gdv.getLoanCode());
				}
				
				//审核通过后更新已还款为新增状态，可以使用
				if (StringUtils.isNotEmpty(gdv.getLoanCode())) {
				    
					LoanBank record = new LoanBank();
					record.setModifyBy("admin");
					record.setModifyTime(new Date());
					record.setDictMaintainType(MaintainType.ADD.getCode());
					record.setLoanCode(gdv.getLoanCode());
					loanBankDao.updateMaintainType(record);
					
					// 资产家不在chp3.0里面放款，所以不这个标识的数据不发送短信
					/*if (!ChannelFlag.ZCJ.getCode().equals(gdv.getLoanFlag())) {
						 expectCheatSmsService.sendMsm(gdv.getLoanCode());
					}*/
				}
				
				
			}
			
			// 放款审核退回成功之后，同步进行的操作，根据合同编号查询拆分表中回盘结果为成功的单子，进行合并,插入催收服务费退回表，同时将退回标识设置为是；
			if (LoanFlowRoute.PAYMENT.equals(workItem.getResponse())
					&& LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode().equals(dictStatus)) {
				// 审核退回历史
				saveLoanStatusHis(loanInfo,gdv.getDictLoanStatus(), GrantCommon.SUCCESS,gdv.getGrantBackMesName());
				// 放款审核退回之后，催收服务费的处理
				String listFlag = gdv.getListFlag();
				if (LoanModel.TG.getName().equals(listFlag)) {
					deleteUrgeByContract(gdv.getContractCode());
				}else {
					urgeDeal(workItem);
				}
			}
			
			// 大金融拒绝
			if (LoanFlowRoute.TO_ZCJ_REJECT.equals(workItem.getResponse())) {
				saveLoanStatusHis(loanInfo, gdv.getDictLoanStatus()+"(大金融发起)", "成功", gdv.getBigFinanceRejectReason());
			}
			// 从放款发出大金融退回到合同审核
			if (LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_CHECK.equals(workItem.getResponse())&&LoanApplyStatus.BIGFINANCE_RETURN.getCode().equals(gdv.getDictLoanStatusCode())) {
				saveLoanStatusHis(loanInfo, gdv.getDictLoanStatus()+"(大金融发起)", "成功", gdv.getGrantSureBackReason());
				updateContract(applyId, gdv.getGrantSureBackReason());
			}
			
			loanInfo.setLoanCode(gdv.getLoanCode());
			// 金信拒绝
			if (workItem.getResponse().equals(LoanFlowRoute.PAYCONFIRM) 
					&& LoanApplyStatus.GOLDCREDIT_REJECT.getCode().equals(gdv.getDictLoanStatusCode())) {
				//插入历史表
				saveLoanStatusHis(loanInfo, gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
				//更新借款信息表的状态
				Map<String,Object> loanStatusByLoanCode = Maps.newHashMap();
				loanStatusByLoanCode.put("dictLoanStatus", LoanApplyStatus.GOLDCREDIT_REJECT.getCode());
				loanStatusByLoanCode.put("loanCode", gdv.getLoanCode());
				loanInfoDao.updateLoanStatus(loanStatusByLoanCode);
				//修改标识
				loanInfo.setLoanFlag(ChannelFlag.CHP.getCode());
				loanGrantDao.updateFlag(loanInfo);
			}
			//从分配卡号发出金信退回到待放款确认中
			if (LoanFlowRoute.PAYCONFIRM.equals(workItem.getResponse())
					&& LoanApplyStatus.GOLDCREDIT_RETURN.getCode().equals(gdv.getDictLoanStatusCode())) {
				//插入历史表
				saveLoanStatusHis(loanInfo, "分配卡号发出"+gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
				updateContract(applyId, gdv.getGrantSureBackReason());
				//更新借款信息表的状态
				Map<String,Object> loanStatusByLoanCode = Maps.newHashMap();
				loanStatusByLoanCode.put("dictLoanStatus", LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
				loanStatusByLoanCode.put("loanCode", gdv.getLoanCode());
				loanInfoDao.updateLoanStatus(loanStatusByLoanCode);
			}
			// 金信退回
			if (workItem.getResponse().equals(LoanFlowRoute.GOLDCREDIT_RETURN)) {
				//插入历史表
				saveLoanStatusHis(loanInfo, gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
				updateContract(applyId, gdv.getGrantSureBackReason());
				//更新借款信息表的状态
				Map<String,Object> loanStatusByLoanCode = Maps.newHashMap();
				loanStatusByLoanCode.put("dictLoanStatus", LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
				loanStatusByLoanCode.put("loanCode", gdv.getLoanCode());
				loanInfoDao.updateLoanStatus(loanStatusByLoanCode);
			}
			
			// 从放款发出金信退回
			if (workItem.getResponse().equals(LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_FROM_PAY)) {
				//插入历史表
				saveLoanStatusHis(loanInfo, "放款发出"+gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
				updateContract(applyId, gdv.getGrantSureBackReason());
				//更新借款信息表的状态
				Map<String,Object> loanStatusByLoanCode = Maps.newHashMap();
				loanStatusByLoanCode.put("dictLoanStatus", LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
				loanStatusByLoanCode.put("loanCode", gdv.getLoanCode());
				loanInfoDao.updateLoanStatus(loanStatusByLoanCode);
			}			
		}
		// 驳回冻结插入历史
		if(YESNO.YES.getCode().equals(gdv.getOperateType())){
	           if(StringUtils.isNotEmpty(gdv.getFrozenFlag()) && "0".equals(gdv.getFrozenFlag())){
	               LoanInfo loaninfo = new LoanInfo();
	               loaninfo.setApplyId(gdv.getApplyId());
	               loaninfo.setLoanCode(gdv.getLoanCode());
	               saveLoanStatusHis(loaninfo, ContractConstant.REJECT_FROZEN, GrantCommon.SUCCESS, gdv.getRejectFrozenReason());
	           }
	    }
	}
	private  void updateContract(String applyId,String returnReson){
		Contract contract = contractDao.findByApplyId(applyId);
        if (StringUtils.isNotEmpty(returnReson)) {
            contract.setContractBackResult(returnReson);
            contract.setBackFlag(YESNO.YES.getCode());
            contractDao.updateContract(contract);
        }  
	}

	/**
	 * 放款审核退回成功关于催收服务费退回处理 2016年1月27日 By 朱静越
	 * 
	 * @param workItem 查询条件
	 * @return null
	 */
	public void urgeDeal(WorkItemView workItem) {
		GrantDealView gdv = (GrantDealView) workItem.getBv();
		String contractCode = gdv.getContractCode();
		// 根据合同编号查询需要进行退回标识更改的催收id,有两种结果，为划扣失败，或者划扣成功,该id的查询为通过returnLogo查询，一定是正在处理的单子
		String urgeId = grantUrgeBackDao.selUrgeId(contractCode);
		// 催收主表中进行查找,如果已划金额为0，已查账金额为0的单子的个数>0,表示该单子完全失败，可以先删除拆分表，然后删除主表
		int successCount = grantUrgeBackDao.selUrgeFail(urgeId);
		if (successCount > 0) {
			// 删除拆分表
			List<UrgeServicesMoneyEx> deleteList = grantUrgeBackDao.selSplitDelete(urgeId);
			if (ArrayHelper.isNotEmpty(deleteList)) {
				grantUrgeBackDao.delGrantFail(deleteList);
			}
			// 删除催收主表
			grantUrgeBackDao.delUrge(urgeId);
		}else {
			// 说明有划扣成功或者查账成功的金额
			UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
			// 根据查询出来的催收id进行退回标识的更新
			urgeServicesMoney.setId("'"+urgeId+"'");
			urgeServicesMoney.setReturnLogo(YESNO.YES.getCode());
			urgeServicesMoney.preUpdate();
			urgeServicesMoneyDao.updateUrge(urgeServicesMoney);
			// 合并回盘结果为成功的单子
			GrantUrgeBackEx grantUrgeBackEx = grantUrgeBackDao
					.getUrgeBack(urgeId);
			// 删除拆分表中划扣回盘结果为失败的单子，根据催收主表id
			List<UrgeServicesMoneyEx> list = grantUrgeBackDao.selSplitDelete(urgeId);
			if (ArrayHelper.isNotEmpty(list)) {
				grantUrgeBackDao.delGrantFail(list);
			}
			if (grantUrgeBackEx != null) {
				// 插入催收服务费退回表
				grantUrgeBackEx.setReturnStatus(FeeReturn.RETURNING.getCode());
				grantUrgeBackEx.preInsert();
				grantUrgeBackDao.insertUrgeBack(grantUrgeBackEx);
			}
		}
	}
	
	/**
	 * 资金托管放款审核退回之后，对催收服务费进行删除。
	 * 2016年6月2日
	 * By 朱静越
	 * @param contractCode
	 */
	public void deleteUrgeByContract(String contractCode){
		UrgeServicesMoney urge = new UrgeServicesMoney();
		if (StringUtils.isNotEmpty(contractCode)) {
			urge.setContractCode(contractCode);
			urge.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			urgeServicesMoneyDao.deleteByContract(urge);
		}
	}

	/**
	 * 放款退回，分配卡号退回时，更新合同表的退回原因 2016年1月27日 By 朱静越
	 * 
	 * @param workItem
	 *            工作流
	 */
	public void updBackReason(WorkItemView workItem) {
		GrantDealView gdv = (GrantDealView) workItem.getBv();
		String applyId = gdv.getApplyId();
		Contract contract = contractDao.findByApplyId(applyId);
		if (gdv.getGrantSureBackReason() != null) {
			contract.setContractBackResult(gdv.getGrantSureBackReason());
			contractDao.updateContract(contract);
		}
	}
	
	/**
	 * 更新放款历史记录表
	 * 2016年3月31日
	 * By 朱静越
	 * @param gdv
	 * @return
	 */
	public int insertGrantHis(GrantDealView gdv){
		LoanGrantHis loanGrantHis = gdv.getLoanGrantHis();
		loanGrantHis.preInsert();
		return loanGrantHisDao.insertGrantHis(loanGrantHis);
	}
	
	/**
	 * 添加操作历史 2016年2月17日 By 王彬彬
	 * 
	 * @param loaninfo
	 *            借款信息
	 * @param operateStep
	 *            操作步骤
	 * @param operateResult
	 *            操作结果
	 * @param remark
	 *            备注
	 * @return
	 */
	private int saveLoanStatusHis(LoanInfo loaninfo, String operateStep,
			String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(loaninfo.getApplyId());
		// 借款编号
		record.setLoanCode(loaninfo.getLoanCode());
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		return loanStatusHisDao.insertSelective(record);
	}
	
	/**
	 * 插入合同文件，大金融放款成功之后，同步借款协议到3.0系统
	 * 2016年10月10日
	 * By 朱静越
	 * @param gdv
	 */
	public void insertContractFile(GrantDealView gdv){
		ContractFile contractFile = new ContractFile();
		contractFile.setContractCode(gdv.getContractCode());
		contractFile.setFileName(ContractType.CONTRACT_ZCJ_PROTOCOL.getName());
		contractFile.setContractFileName(ContractType.CONTRACT_ZCJ_PROTOCOL.getName());
		contractFile.setSignDocId(gdv.getDocId());
		contractFile.setSendFlag(SendFlag.NO.getCode());
		contractFile.setDownloadFlag(ContractType.CONTRACT_ZCJ_PROTOCOL.getFlag()); // 默认为0，
		contractFile.setFileShowOrder(ContractType.CONTRACT_ZCJ_PROTOCOL.getCode()); // 合同文件排序
		contractFile.preInsert();
		contractFileDao.insert(contractFile);
	}
}