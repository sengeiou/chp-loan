package com.creditharmony.loan.borrow.grant.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ChkBackReason;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.constants.ResultConstants;
import com.creditharmony.loan.borrow.grant.dao.GrantAuditDao;
import com.creditharmony.loan.borrow.grant.dao.GrantUrgeBackDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantAuditEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLFirstEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLSecondEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantUrgeBackEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.trusteeship.dao.GoldAccountCeilingDao;
import com.creditharmony.loan.channel.finance.dao.FinancialBusinessDao;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.PaybackMonthDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.entity.RepayPlanNew;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ReckonFeeNew;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;

/**
 * 放款审核处理
 * 
 * @Class Name GrantAuditService
 * @author 朱静越
 * @Create In 2015年12月8日
 */
@Service("grantAuditService")
public class GrantAuditService extends CoreManager<LoanGrantDao, LoanGrantEx> {
	
	@Autowired
	private GrantAuditDao grantAuditDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private LoanBankDao loanBankDao;
	@Autowired
	private FinancialBusinessDao businessDao;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired
	private GrantUrgeBackDao grantUrgeBackDao;
	@Autowired
	private UrgeServicesMoneyDao urgeServicesMoneyDao;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GoldAccountCeilingDao goldAccountCeilingDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private PaybackMonthDao paybackMonthDao;
	@Autowired
	private ContractFeeDao contractFeeDao;
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	/**
	 * 查询放款审核列表页面数据显示
	 * 2017年1月17日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<GrantAuditEx> getGrantAuditList(Page<GrantAuditEx> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<GrantAuditEx> pageList = (PageList<GrantAuditEx>)grantAuditDao.getGrantAuditList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询放款审核列表，不分页
	 * 2017年1月18日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<GrantAuditEx> getGrantAuditList(LoanFlowQueryParam loanFlowQueryParam){
		return grantAuditDao.getGrantAuditList(loanFlowQueryParam);
	}

	/**
	 * 放款审核退回处理:1.判断：处理中和待查账的数据不允许退回 2.更新放款表 3.更新借款状态 
	 * 4.审核退回，催收服务费的处理 5.插入历史
	 * 2016年4月23日
	 * By 朱静越
	 * @param distachParamItem
	 * @param result
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String backDeal(DistachParamEx distachParamItem,String result,String listFlag){
		
		// 判断是否可以退回
		String resultMes = this.judgeIsBack(distachParamItem.getContractCode());
		if (StringUtils.isEmpty(resultMes)) {
			// 更新放款表
			LoanGrant loanGrant = new LoanGrant();
			loanGrant.setContractCode(distachParamItem.getContractCode());
			loanGrant.setGrantBackMes(result); // 更新退回原因
			loanGrant.setCheckTime(new Date());
			loanGrant.setCheckEmpId(UserUtils.getUser().getId());
			loanGrant.setCheckResult(VerityStatus.RETURN.getCode()); // 审核结果为审核退回
			loanGrant.setGrantFailResult(""); // 放款审核退回，将失败原因更新为空
			loanGrant.setGrantRecepicResult(""); // 放款审核退回，将回盘结果更新为空
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			
			// 指定借款状态
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setApplyId(distachParamItem.getApplyId());
			loanInfo.setLoanCode(distachParamItem.getLoanCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITYRETURN
					.getCode());
			loanInfo.preUpdate();
			loanGrantDao.updateStatus(loanInfo);
			
			// 放款审核退回，催收服务费的处理
			if (LoanModel.TG.getName().equals(listFlag)) {
				deleteUrgeByContract(distachParamItem.getContractCode());
			}else {
				urgeDeal(distachParamItem);
			}
			
			// 获得放款审核退回原因name,插入历史
			String resultName = null;
			for (ChkBackReason s : ChkBackReason.values()) {
				if (s.getCode().equals(result)) {
					resultName = s.getName();
				}
			}
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getName(), GrantCommon.SUCCESS,resultName);
		}
		return resultMes;
	}
	
	/**
	 * 判断是否可以进行放款审核退回
	 * 2017年1月18日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public String judgeIsBack(String contractCode){
		String returnMes = null;
		String dealStatus = grantUrgeBackDao.getDealCount(contractCode);
		if (UrgeCounterofferResult.PROCESS.getCode().equals(
				dealStatus)
				|| UrgeCounterofferResult.PROCESSED.getCode()
						.equals(dealStatus)) {
			returnMes = "deduct";
		}
		if (UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()
				.equals(dealStatus)) {
			returnMes = "check";
		}
		return returnMes;
	}
	
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void urgeDeal(DistachParamEx distachParamItem) {
		String contractCode = distachParamItem.getContractCode();
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
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void deleteUrgeByContract(String contractCode){
		UrgeServicesMoney urge = new UrgeServicesMoney();
		if (StringUtils.isNotEmpty(contractCode)) {
			urge.setContractCode(contractCode);
			urge.setDictDealStatus(UrgeCounterofferResult.PAYMENT_SUCCEED.getCode());
			urgeServicesMoneyDao.deleteByContract(urge);
		}
	}
	
	/**
	 * 放款审核处理:1.更新放款表 ,同时CHP标识需要更新财富债权为0，表示可以推送债权；
	 * 2.更新还款主表的有效标识  3.审核通过后将bank表更新已还款为新增状态，可以使用  4.插入历史
	 * 5.如果是p2p的放款，需要将数据插入到大金融表中
	 * 6.如果是TG的数据，将放款审核成功的数据插入到金账户的表中
	 * 2016年4月23日
	 * By 朱静越
	 * @param distach 参数
	 * @param listFlag 区分标识
	 * @param result 审核时间
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void grantAuditDeal(DistachParamEx distach,Date result,String listFlag){
		// 更新放款表信息
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setContractCode(distach.getContractCode());
		loanGrant.setCheckEmpId(UserUtils.getUser().getId()); // 设置回盘结果
		loanGrant.setCheckResult(VerityStatus.PASS.getCode());
		loanGrant.setCheckTime(new Date());
		loanGrant.setLendingTime(result);
		if (ChannelFlag.CHP.getName().equals(distach.getChannelCode())) {
			loanGrant.setCfSendFlag(YESNO.NO.getCode());
		}
		loanGrant.preUpdate();
		loanGrantDao.updateLoanGrant(loanGrant);
		
		// 更新还款主表
		Payback payback = new Payback();
		payback.setContractCode(distach.getContractCode());
		payback.setDictPayStatus(YESNO.NO.getCode());
		payback.setEffectiveFlag(YESNO.YES.getCode());
		paybackDao.updatePayback(payback);
		
		// 更新借款主表 借款状态
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(distach.getApplyId());
		loanInfo.setLoanCode(distach.getLoanCode());
		// 指定借款状态，需要改变,直接从字典表中取值
		loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT.getCode());
		loanInfo.preUpdate();
		loanGrantDao.updateStatus(loanInfo);
		
		// 更新合同表中的借款状态为还款中
		Contract contract = new Contract();
		contract.setContractCode(distach.getContractCode());
		contract.setDictCheckStatus(LoanApplyStatus.REPAYMENT.getCode());
		contractDao.updateContract(contract);
		
		// 更新已还款为新增状态，可以使用
		LoanBank record = new LoanBank();
		record.setModifyBy("admin");
		record.setModifyTime(new Date());
		record.setDictMaintainType(MaintainType.ADD.getCode());
		record.setLoanCode(distach.getLoanCode());
		loanBankDao.updateMaintainType(record);
		
		this.addArchives(distach.getContractCode());
		// 插入历史
		historyService.saveLoanStatusHis(loanInfo,GrantCommon.GRANT_AUDIT, VerityStatus.PASS.getName(),"");
		// 放款审核完成之后，不同标识的处理
		this.flagDeal(distach, listFlag);
		//放款成功后重新制作合同
		//makeContractBefor(distach.getContractCode(),distach.getLoanCode());
	}
	
	/**
	 * 不同的标识放款审核完成之后的处理
	 * 2017年1月18日
	 * By 朱静越
	 * @param distach
	 * @param listFlag
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void flagDeal(DistachParamEx distach,String listFlag){
		if (ChannelFlag.P2P.getName().equals(distach.getChannelCode())) {
			FinancialBusiness finance =new FinancialBusiness();
			finance.preInsert();
			finance.setLoanCode(distach.getLoanCode());
			businessDao.insertFinancialBusiness(finance);
		}else if(LoanModel.TG.getName().equals(listFlag)) {
			if (StringUtils.isEmpty(goldAccountCeilingDao.selectLoanCode(distach.getLoanCode()))){
				goldAccountCeilingDao.insertGoldAccountData(distach.getLoanCode());
			}
		}
	}
	
	/**
	 * 修改放款银行：1.修改放款途径，修改中间人id
	 * 2016年4月23日
	 * By 朱静越
	 * @param distachParamItem 合同编号
	 * @param middleId 中间人id
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updBankDeal(DistachParamEx distachParamItem,String middleId){
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setContractCode(distachParamItem.getContractCode());
		// 设置单子进行卡号的更改
		loanGrant.setMidId(middleId);
		// 根据中间人id查找中间人信息
		MiddlePerson middlePerson = middlePersonService
				.selectById(middleId);
		if (StringUtils.isNotEmpty(middlePerson.getMiddleName())) {
			// 根据中间人姓名，设置放款途径
			if (GrantCommon.ZHONG_JING.equals(middlePerson.getMiddleName())) {
				loanGrant.setDictLoanWay(PaymentWay.ZHONGJIN.getCode());
			} else if (GrantCommon.TONG_LIAN.equals(middlePerson.getMiddleName())) {
				loanGrant.setDictLoanWay(PaymentWay.TONG_LIAN.getCode());
			} else {
				loanGrant.setDictLoanWay(PaymentWay.NET_BANK.getCode());
			}
		}
		loanGrant.preUpdate();
		loanGrantDao.updateLoanGrant(loanGrant);
	}
	
	/**
	 * 通联模板1的导入,对list进行遍历，获得合同编号的分组
	 * 2016年5月11日
	 * By 朱静越
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void getInfo(List<?> datalist){
		List<GrantImportTLFirstEx> grantImportTLFirst = (List<GrantImportTLFirstEx>)datalist;
		List<String> contractsList = new ArrayList<String>();
		LoanGrant loanGrant = new LoanGrant();
		String remark = null;
		int success = 0;
		int fail = 0;
		int deal = 0;
		int sum = 0;
		String contractCode = null;
		String[] lendingTimes = new String[datalist.size()];
		String[] grantPch = new String[datalist.size()];
		// 失败原因
		String [] failReason = new String[datalist.size()];
		// 成功金额
		BigDecimal[] tradeAmount = new BigDecimal[datalist.size()];
		// 失败金额
		BigDecimal[] failAmount = new BigDecimal[datalist.size()];
		// 获取合同编号
		for (int i = 0; i < grantImportTLFirst.size()-1; i++) {
			remark = grantImportTLFirst.get(i).getRemark();
            contractCode = remark.split("_")[0];
            if (!contractsList.contains(contractCode)) {
            	contractsList.add(contractCode);
			}
		}
		for (int j = 0; j < contractsList.size(); j++) {
			loanGrant.setContractCode(contractsList.get(j));
			tradeAmount[j] = new BigDecimal(0.00);
			failAmount[j] = new BigDecimal(0.00);
			for (int k = 0; k < grantImportTLFirst.size()-1; k++) {
				remark = grantImportTLFirst.get(k).getRemark();
	            contractCode = remark.split("_")[0];
	            if (contractsList.get(j).equals(contractCode)) {
	            	sum ++;
	            	grantPch[j] = grantImportTLFirst.get(k).getGrantBatchCode();
	            	lendingTimes[j] = grantImportTLFirst.get(k).getFinishTime();
					// 处理状态为成功
					if (GrantUtil.isLentMoneySuccess(grantImportTLFirst.get(k).getDealStatusName())) {
						BigDecimal tradeAmountItem = new BigDecimal(grantImportTLFirst.get(k).getTradeAmount());
						tradeAmount[j] = tradeAmount[j].add(tradeAmountItem.divide(new BigDecimal(100)));
						success ++;
						// 处理状态为失败
					}else{
						BigDecimal failAmountItem = new BigDecimal(grantImportTLFirst.get(k).getTradeAmount());
						failAmount[j] = failAmount[j].add(failAmountItem.divide(new BigDecimal(100)));
						failReason[j] = grantImportTLFirst.get(k).getReason();
						fail++;
					}
					if (ResultConstants.DEAL_ING_DESC.equals(grantImportTLFirst.get(j).getDealStatusName())) {
						deal++;
					}
				}
			}
			// 设置该合同编号单子的页面显示状态,如果没有进行拆分，只有一条数据
            if (sum == 1) {
				if (success > 0) { // 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
				}else if (deal > 0) { // 处理中
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
				}else{ // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}
			}else{
				if (fail > 0 || deal > 0) { // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}else{// 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
				}
			}
            loanGrant.setGrantFailResult(failReason[j]);
			loanGrant.setGrantFailAmount(failAmount[j]);
			loanGrant.setLendingTime(DateUtils.parseDate(lendingTimes[j]));
			loanGrant.setGrantBatch(grantPch[j]);
			loanGrantDao.updateLoanGrant(loanGrant);
		}		
	}
	
	/**
	 * 通联模板2的导入，根据合同编号,通联2导入的数据，金额为分
	 * 2016年5月12日
	 * By 朱静越
	 * @param datalist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void getInfoTL2(List<?> datalist){
		List<GrantImportTLSecondEx> grantImportTLSecondExs = (List<GrantImportTLSecondEx>)datalist;
		String remark = null;
		int success = 0;
		int fail = 0;
		int sum = 0;
		int deal = 0;
		LoanGrant loanGrant = new LoanGrant();
		String contractCode = null;
		List<String> contractsList = new ArrayList<String>();
		String[] grantPch = new String [datalist.size()];
		// 失败原因
		String [] failReason = new String [datalist.size()];
		// 成功金额
		BigDecimal[] tradeAmount = new BigDecimal[datalist.size()];
		// 失败金额
		BigDecimal[] failAmount = new BigDecimal[datalist.size()];
		// 获取合同编号
		for (int i = 0; i < grantImportTLSecondExs.size()-1; i++) {
			remark = grantImportTLSecondExs.get(i).getRemark();
            contractCode = remark.split("_")[0];
            if (!contractsList.contains(contractCode)) {
            	contractsList.add(contractCode);
			}
		}
		// 处理
		for (int j = 0; j < contractsList.size(); j++) {
			loanGrant.setContractCode(contractsList.get(j));
			tradeAmount[j] = new BigDecimal(0.00);
			failAmount[j] = new BigDecimal(0.00);
			for (int k = 0; k < grantImportTLSecondExs.size()-1; k++) {
				remark = grantImportTLSecondExs.get(k).getRemark();
	            contractCode = remark.split("_")[0];
	            if (contractsList.get(j).equals(contractCode)) {
	            	sum ++;
	            	grantPch[j] = grantImportTLSecondExs.get(k).getGrantBatchCode();
					// 处理状态为成功
					if (GrantUtil.isLentMoneySuccess(grantImportTLSecondExs.get(k).getFeedbackCode())) {
						BigDecimal tradeAmountItem = new BigDecimal(grantImportTLSecondExs.get(k).getGrantAmount());
						tradeAmount[j] = tradeAmount[j].add(tradeAmountItem.divide(new BigDecimal(100)));
						success ++;
						// 处理状态为失败
					}else{
						BigDecimal failAmountItem = new BigDecimal(grantImportTLSecondExs.get(k).getGrantAmount());
						failAmount[j] = failAmount[j].add(failAmountItem.divide(new BigDecimal(100)));
						failReason[j] = grantImportTLSecondExs.get(k).getReason();
						fail++;
					}
					if (ResultConstants.DEAL_ING_DESC.equals(grantImportTLSecondExs.get(k).getReason())) {
						deal++;
					}
				}
			}
			// 设置该合同编号单子的页面显示状态,如果没有进行拆分，只有一条数据
            if (sum == 1) {
				if (success > 0) { // 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
				}else if (deal > 0) { // 处理中
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
				}else{ // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}
			}else{
				if (fail > 0 || deal > 0) { // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}else{// 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
				}
			}
            loanGrant.setGrantFailResult(failReason[j]);
			loanGrant.setGrantFailAmount(failAmount[j]);
			loanGrant.setLendingTime(new Date());
			loanGrant.setGrantBatch(grantPch[j]);
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		}
	
	public void addArchives(String contractCode){
		Map map1 = new HashMap();
		map1.put("contractCode", contractCode);
		map1.put("fileNum", "309110122|309110125");
		map1.put("fileName", "委托划扣|其它材料");
		contractDao.addArchives(map1);
	}
	
	public void makeContractBefor(String contractCode, String loanCode) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("contractCode", contractCode);
		// 删除旧有的还款期供信息
		List<PaybackMonth> paybackMonths = paybackMonthDao.findByContractCode(param);
		if (!ObjectHelper.isEmpty(paybackMonths)) {
			paybackMonthDao.deleteByContractCode(param);
		}
		Payback payback = new Payback();
		Map<String, Object> paybackParam = new HashMap<String, Object>();
		paybackParam.put("contractCode", contractCode);
		Payback apply = paybackDao.selectpayBack(paybackParam);
		// 删除旧有的还款信息
		if (!ObjectHelper.isEmpty(apply)) {
			paybackDao.deletePayback(contractCode);
		}
		Contract tempContract = contractDao.findByContractCode(contractCode);
		ContractFee tempContractFee = contractFeeDao.findByContractCode(contractCode);
		Contract contractTemp=new Contract();
		//计算起始还款日期-实际放款日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if(calendar.get(Calendar.DATE)  == 31){
        	calendar.set(Calendar.DATE, 30);
	    }
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		tempContract.setContractReplayDay(calendar.getTime());
		Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.MONTH, tempContract.getContractMonths().intValue() - 1);
		Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Integer billDay = 0;
		if (lastDayOfMonth < signDay) {
			calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			tempContract.setContractEndDay(calendar.getTime());
			billDay = lastDayOfMonth;
		} else {
			tempContract.setContractEndDay(calendar.getTime());
			billDay = signDay;
		}
		//更新起始还款日期
		contractTemp.setContractReplayDay(tempContract.getContractReplayDay());
		contractTemp.setContractEndDay(tempContract.getContractEndDay());
		contractTemp.setLoanCode(loanCode);
		contractTemp.preUpdate();
		contractDao.updateContract(contractTemp);
		
		List<RepayPlanNew> repayPlan = ReckonFeeNew.createNXDRepayPlanNew(tempContract, tempContractFee);
		PaybackMonth paybackMonth = null;
		param.put("loanCode", loanCode);
		LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
		String customerCode = loanCustomer.getCustomerCode();
		RepayPlanNew curPlan = repayPlan.get(0);
		payback.setContractCode(contractCode);
		payback.setCustomerCode(customerCode);
		payback.setPaybackCurrentMonth(curPlan.getPayBackCurrentMonth());
		payback.setPaybackMonthAmount(curPlan.getMonthPaySum());
		payback.setEffectiveFlag(YESNO.NO.getCode());
		payback.setPaybackBuleAmount(BigDecimal.ZERO);
		payback.setPaybackDay(billDay);
		payback.preInsert();
		paybackDao.insertPayback(payback);
		for (RepayPlanNew curr : repayPlan) {
			paybackMonth = new PaybackMonth();
			// 合同编号
			paybackMonth.setContractCode(contractCode);
			// 总期数
			paybackMonth.setMonths(curr.getPayBackCurrentMonth());
			// 每期还款日
			paybackMonth.setMonthPayDay(curr.getMonthPayDay());
			// 每月还款总额
			paybackMonth.setMonthPayTotal(curr.getMonthPaySum());
			// 分期咨询费
			paybackMonth.setMonthFeeConsult(curr.getStageFeeConsult());
			// 分期居间服务费
			paybackMonth.setMonthMidFeeService(curr.getStageFeeService());
			// 分期服务费
			paybackMonth.setMonthFeeService(curr.getStageServiceFee());
			// 应还违约金(一次性还款违约罚金)
			paybackMonth.setMonthPenaltyShould(curr.getOncePayPenalty());
			// 应还利息
			paybackMonth.setMonthInterestBackshould(curr.getMonthInterestBackshould());
			// 应还本金
			paybackMonth.setMonthPayAmount(curr.getMonthPayAmount());
			// 一次性应还总额
			paybackMonth.setMonthBeforeFinishAmount(curr.getOncePaySum());
			// month_residue_payactual 还款后剩余本金
			paybackMonth.setMonthResiduePayactual(curr.getResidualPrincipal());
			paybackMonth.setDictMonthStatus(PeriodStatus.REPAYMENT.getCode());
			paybackMonth.preInsert();
			paybackMonthDao.insertPaybackMonth(paybackMonth);
		}

	}
	
}
