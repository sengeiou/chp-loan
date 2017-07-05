package com.creditharmony.loan.channel.jyj.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.GrantAuditDao;
import com.creditharmony.loan.borrow.grant.dao.GrantUrgeBackDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantUrgeBackEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.trusteeship.dao.GoldAccountCeilingDao;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.finance.dao.FinancialBusinessDao;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.channel.jyj.dao.JYJGrantAuditDao;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantAuditEx;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;

/**
 * 放款审核处理
 * 
 * @Class Name GrantAuditService
 * @author 朱静越
 * @Create In 2015年12月8日
 */
@Service("JYJGrantAuditService")
public class JYJGrantAuditService extends CoreManager<JYJGrantAuditDao, JYJGrantAuditEx> {
	
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
	private AssistService assistService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	
	/**
	 * 查询放款审核列表页面数据显示
	 * 2017年1月17日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<JYJGrantAuditEx> getGrantAuditList(Page<JYJGrantAuditEx> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<JYJGrantAuditEx> pageList = (PageList<JYJGrantAuditEx>)dao.getGrantAuditList(pageBounds, loanFlowQueryParam);
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
	public List<JYJGrantAuditEx> getGrantAuditList(LoanFlowQueryParam loanFlowQueryParam){
		return dao.getGrantAuditList(loanFlowQueryParam);
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
	public void urgeDeal(String contractCode) {
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
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void addArchives(String contractCode){
		Map map1 = new HashMap();
		map1.put("contractCode", contractCode);
		map1.put("fileNum", "309110122|309110125");
		map1.put("fileName", "委托划扣|其它材料");
		contractDao.addArchives(map1);
	}
	
	//修改借款状态
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanStatus(String loanStatus,String contractCode){
		return dao.updateLoanStatus(loanStatus, contractCode);
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateGrantFlag(String contractCode){
		return dao.updateGrantFlag(contractCode);
	}
	
	/**
	 * 首次放款成功之后直接到已放款，修改payback表
	 * 2017年5月27日
	 * By 朱静越
	 * @param loanInfo
	 * @param loanGrant
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateGrantDone(LoanInfo loanInfo,LoanGrant loanGrant){
		//更新借款表借款状态  并加历史
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,"已放款","成功","");
		}
		// 更新还款主表
		Payback payback = new Payback();
		payback.setContractCode(loanGrant.getContractCode());
		payback.setDictPayStatus(YESNO.NO.getCode());
		payback.setEffectiveFlag(YESNO.YES.getCode());
		paybackDao.updatePayback(payback);
		
		// 更新已还款为新增状态，可以使用
		LoanBank record = new LoanBank();
		record.setModifyBy("admin");
		record.setModifyTime(new Date());
		record.setDictMaintainType(MaintainType.ADD.getCode());
		record.setLoanCode(loanInfo.getLoanCode());
		loanBankDao.updateMaintainType(record);
		//更新放款表  
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			// 添加档案数据
			this.addArchives(loanGrant.getContractCode());
		}
	}
	
	/**
	 * 修改退回原因他借款状态,如果催收服务费的状态不允许退回，不能进行退回
	 * 退回到合同审核，需要进行重新分单
	 * 2017年5月8日
	 * By 朱静越
	 * @param loanStatus
	 * @param backResult
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public String updateBackResultAndLoanStatus(String loanStatus,String backResult,JYJGrantAuditEx jyjGrantAuditEx){
		// 判断是否可以退回
		String resultMes = this.judgeIsBack(jyjGrantAuditEx.getContractCode());
		if (StringUtils.isEmpty(resultMes)) {
			LoanGrant loanGrant = new LoanGrant();
			loanGrant.setContractCode(jyjGrantAuditEx.getContractCode());
			loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
			loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED
    				.getCode());
			loanGrant.setGrantFlag(YESNO.NO.getCode());
			loanGrantDao.updateLoanGrant(loanGrant);
			dao.updateLoanStatus(loanStatus, jyjGrantAuditEx.getContractCode());
			dao.updateBackResult(backResult, jyjGrantAuditEx.getContractCode());
			// 更新合同表中的退回字段
			Contract contract = new Contract();
			contract.setContractCode(jyjGrantAuditEx.getContractCode());
			contract.setBackFlag(YESNO.YES.getCode());
			contractDao.updateContract(contract);
			// 催收服务费的处理
			urgeDeal(jyjGrantAuditEx.getContractCode());
			// 重新分单
	    	assistService.updateAssistAddAuditOperator(jyjGrantAuditEx.getLoanCode());
	    	// 历史
	    	LoanInfo loanInfo = new LoanInfo();
	    	loanInfo.setApplyId(jyjGrantAuditEx.getApplyId());
			loanInfo.setLoanCode(jyjGrantAuditEx.getLoanCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
			historyService.saveLoanStatusHis(loanInfo,GrantCommon.GRANT_BACK, VerityStatus.RETURN.getName(),backResult);
			// 更新排序字段
			createOrderFileldService.backContractCheckByGrant(loanInfo);
		}
		return resultMes;
	}
	
	//查询首次放款比例
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<Map<String,String>> getFirstLoanProportion(String contractCode){
		return dao.getFirstLoanProportion(contractCode);
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<Map<String,String>> getApplyId(String contractCode){
		return dao.getApplyId(contractCode);
	}
}
