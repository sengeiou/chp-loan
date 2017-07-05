package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.jzh.JzhWtrechargeInfo;
import com.creditharmony.adapter.bean.out.jzh.JzhTransferBuOutInfo;
import com.creditharmony.adapter.bean.out.jzh.JzhWtrechargeOutInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cms.constants.YesNo;
import com.creditharmony.core.common.type.UseFlag;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductPlatType;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.moneyaccount.entity.MoneyAccountInfo;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.DeductPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.PaybackSplitDao;
import com.creditharmony.loan.borrow.payback.entity.BankPlantPort;
import com.creditharmony.loan.borrow.payback.entity.BankRule;
import com.creditharmony.loan.borrow.payback.entity.DeductCondition;
import com.creditharmony.loan.borrow.payback.entity.DeductPayback;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylExport;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackTrustEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductImportEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.common.constants.TongLianBankBatch;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.sync.data.remote.FyMoneyAccountService;
import com.google.common.collect.Maps;

/**
 * 待还款划扣service
 * @Class Name DeductPaybackService
 * @author 翁私
 * @Create In 2015年12月29日
 */
@Service("deductPaybackService")
public class DeductPaybackService extends
		CoreManager<DeductPaybackDao, DeductPayback> {

	@Autowired
	private PaybackSplitDao paybackSplitDao;
	@Autowired
	private PaybackBlueAmountService blusAmountService;
	@Autowired
	private PaybackService paybackService;
	@Autowired
	private ProvinceCityManager cityManager;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private FyMoneyAccountService fyMoneyAccountService;
	@Autowired
	private DeductUpdateService deductUpdateService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PlantSkipOrderService plantSkipOrderService;
	@Autowired
	private BankPlantPortService bankPlantPortService;
	@Autowired
	private SystemSetMaterDao systemSetMaterDao;
	@Autowired
	private PaymentSplitService paymentSplitService;
	@Autowired
	private PlatformGotoRuleManager platformGotoRuleManager;
	@Autowired
	private DeductUpdateDao  deductUpdateDao;
	
	private static final Logger log = LoggerFactory.getLogger(DeductPaybackService.class);
	SimpleDateFormat fomat1 = new SimpleDateFormat("yyyy-MM-dd HHmmss");

	 
	/**
	 * 查询待还款划扣列表 
	 * 2016年1月6日 By 翁私
	 * @param apply
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackSplitFyEx> getDeductPaybackList(PaybackApply apply) {
		return dao.getDeductPaybackList(apply);
	}

	
	/**
	 * 好易联导出方法 
	 * 2015年1月6日 By 翁私
	 * @param apply
	 * @return 导出列表
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackSplitHylExport> getDeductPaybackListHyl(
			PaybackApply apply) {
		return dao.getDeductPaybackListHyl(apply);
	}

	/**
	 * 查询要划扣的记录
	 * @author 翁私
	 * @Create In 2015年2月3日
	 * @param paramMap
	 * @return 划扣记录
	 */
	public List<DeductReq> queryDeductReqList(Map<String, Object> map) {
		// 　取得规则
		String rule = (String) map.get("rule");
		List<DeductReq> list = dao.queryDeductReqList(map);
		for (DeductReq deductReq : list) {
			/*deductReq.setCreatTime(new Date());
			BalanceInfo info = dao.queryBalanceInfo(deductReq);
			if(info != null){
				if(info.getTotal()>=2){
					rule = getRule(rule);
				}
			}*/
			// 设置划扣标志
			deductReq.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			// 设置划扣规则
			deductReq.setRule(rule);
			// 系统处理ID
			deductReq.setSysId(DeductWays.HJ_02.getCode());
			
			
		}
		return list;
	}


	/**
	 * 查询要拆分的数据 
	 * 2015年1月8日 By wengsi
	 * @param map
	 * @return 申请列表
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public List<PaybackApply> queryApplyList(Map<String, Object> map) {
		return dao.queryApplyList(map);
	}

	/**
	 * 跳转待还款划扣页面 
	 * 2016年2月24日 By 翁私
	 * @param page
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackApply> findApplyPayback(Page<PaybackApply> page,
			Map<String, Object> map) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<PaybackApply> pageList = (PageList<PaybackApply>) dao
				.findApplyPayback(map, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 待划款列表导出数据，非分页 
	 * 2016年3月13日 By 王浩
	 * @param map
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackApply> findApplyPayback(Map<String, Object> map) {
		return dao.findApplyPayback(map);
	}

	/**
	 * 查询中金导出数据 
	 * 2016年1月6日 By 翁私
	 * @param apply
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackSplitZjEx> getDeductPaybackListZj(PaybackApply apply) {
		List<PaybackSplitZjEx> list = dao.getDeductPaybackListZj(apply);
		return list;
	}

	/**
	 * 查询通联导出数据 
	 * 2016年1月6日 By 翁私
	 * @param apply
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackSplitTlEx> getDeductPaybackListTl(PaybackApply apply) {
		List<PaybackSplitTlEx> list = dao.getDeductPaybackListTl(apply);
		return list;
	}

	/**
	 * 插入操作历史 
	 * 2015年1月6日 By 翁私
	 * @param deductReqList
	 * @param deductType
	 * @param b
	 * @param string
	 * @return none
	 */
	public void insertPaybackOpe(DeductReq deductReq, String deductType,
			boolean b, String string) {
		String dictLoanStatus = "";
		PaybackOpe paybackOpe = new PaybackOpe();
		if ("key".equals(deductType)) {
			dictLoanStatus = RepaymentProcess.KEY_SEND.getCode(); // 一键发送
		} else if (DeductPlat.FUYOU.getCode().equals(deductType)) {
			dictLoanStatus = RepaymentProcess.SEND_FUYOU.getCode(); // 发送富有
		} else if (DeductPlat.HAOYILIAN.getCode().equals(deductType)) {
			dictLoanStatus = RepaymentProcess.SEND_HYL.getCode(); // 发送 好易联
		} else if (DeductPlat.ZHONGJIN.getCode().equals(deductType)) {
			dictLoanStatus = RepaymentProcess.SEND_ZHONGJIN.getCode(); // 发送中金
		} else if (DeductPlat.TONGLIAN.getCode().equals(deductType)) {
			dictLoanStatus = RepaymentProcess.SEND_TONGLIAN.getCode(); // 发送通联
		}else if(DeductPlat.KALIAN.getCode().equals(deductType)){  // 发送卡联
			dictLoanStatus = RepaymentProcess.SEND_KALIAN.getCode();
		}else if(DeductPlat.CHANGJIE.getCode().equals(deductType)){ // 发送畅捷
			dictLoanStatus = RepaymentProcess.SEND_CHANGJIE.getCode();
		}
		paybackOpe.setrPaybackApplyId(deductReq.getBatId());
		paybackOpe.setrPaybackId(deductReq.getRequestId());
		paybackOpe.setDictLoanStatus(dictLoanStatus);
		paybackOpe.setDictRDeductType(TargetWay.REPAYMENT.getCode());
		if(deductReq.getAmount() != null){
			paybackOpe.setRemarks("发送给批处理" + ":" + deductReq.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		if (b) {
			paybackOpe.setOperateResult(PaybackOperate.SEND_SUCCESS.getCode());
		} else {
			paybackOpe.setOperateResult(PaybackOperate.SEND_FAILED.getCode());
			paybackOpe.setRemarks(string);
		}
		paybackOpe.preInsert();
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}

	/**
	 * 发送批处理
	 * 2015年3月10日 By 翁私
	 * @param deductReqList
	 * @param deductType
	 * @param deductReqListExcle
	 * @param flag
	 * @return list
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean sendBatch(DeductReq req,
			String deductType, String flag) {
			DeResult t = new DeResult();
			boolean isSuccess =false;
		try {
				PaybackApply apply = dao.queryDeductReq(req);
				if(apply == null){
					return isSuccess;
				}
				t = TaskService.addTask(req);
				req.setModifyby(UserUtils.getUser().getId());
				req.setCounterofferResult(CounterofferResult.PROCESS.getCode());
				if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
						int i = dao.updateApplyStatus(req);
						if (i < 1) {
							TaskService.rollBack(t.getDeductReq());
							isSuccess =  false;
						}
					if (req.getStatus().equals(CounterofferResult.PROCESSED.getCode())) {
						dao.deleteDeductReq(req);
					}
					insertPaybackOpe(req, deductType, true, "");
					TaskService.commit(t.getDeductReq());
					isSuccess = true;
				} else {
					insertPaybackOpe(req, deductType, false, t.getReMge());
					TaskService.rollBack(t.getDeductReq());
					isSuccess =  false;
					//throw new ServiceException("发送批处理异常！" + msg);
				}
		} catch (Exception e) {
			TaskService.rollBack(t.getDeductReq());
			isSuccess =false;
			throw new ServiceException("发送批处理异常！" + e.getMessage());
		}
		return isSuccess;
	}

	/**
	 * 导出委托充值数据时，从一个对象中copy属性 
	 * 2016年3月10日 By 王浩
	 * @param splitTrust
	 * @param paybackDeduct
	 * @return
	 */
	public PaybackTrustEx copyRechargeProperties(PaybackTrustEx splitTrust,
			PaybackApply paybackDeduct) {
		// 当前应还金额
		splitTrust.setTrustAmount(paybackDeduct.getApplyAmount());
		splitTrust.setCustomerName(paybackDeduct.getLoanCustomer()
				.getCustomerName());
		splitTrust.setTrusteeshipNo(paybackDeduct.getLoanCustomer()
				.getTrusteeshipNo());
		// 合同编号 + "委托充值" + 申请id
		splitTrust.setRemarks(paybackDeduct.getContract().getContractCode()
				+ "_" + DeductedConstantEx.PAYBACK_TRUST + "_"
				+ paybackDeduct.getId());
		return splitTrust;
	}

	/**
	 * 更新委托提现信息 
	 * 2016年3月10日 By 王浩
	 * @param trusteeImport
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateTrustRecharge(TrusteeImportEx trusteeImport) {
		dao.updateTrustRecharge(trusteeImport);
	}

	/**
	 * 导出线下划扣的时候，复制属性 
	 * 2016年3月12日 By 王浩
	 * @param trustDeduct
	 * @param paybackDeduct
	 * @return
	 */
	public TrustDeductEx copyDeductProperties(TrustDeductEx trustDeduct,
			PaybackApply paybackDeduct) {
		// 付款方登录名
		trustDeduct.setTrusteeshipNo(paybackDeduct.getLoanCustomer()
				.getTrusteeshipNo());
		// 付款方中文名称
		trustDeduct.setCustomerName(paybackDeduct.getLoanCustomer()
				.getCustomerName());
		// 付款资金来自冻结
		trustDeduct.setFundFromFrozen(YESNO.NO.getName());
		// 收款方登录名
		trustDeduct.setPayeeLoginName(Global.getConfig("jzh_fk_account"));
		// 收款方中文名
		trustDeduct.setPayeeName(Global.getConfig("jzh_fk_name"));
		// 收款后是否立刻冻结
		trustDeduct.setFundFrozen(YESNO.NO.getName());
		// 交易金额
		trustDeduct.setTrustAmount(paybackDeduct.getApplyAmount());
		// 合同编号 + "委托充值" + 还款申请id
		trustDeduct.setRemarks(paybackDeduct.getContractCode()
				+ "_" + DeductedConstantEx.PAYBACK_TRUST_DEDUCT + "_"
				+ paybackDeduct.getId());
		return trustDeduct;
	}

	/**
	 * 设置更新回盘结果所需的实体 2016年3月13日 By 王浩
	 * 
	 * @param deductImport
	 * @return
	 */
	public LoanDeductEntity setLoanDeductEntity(TrustDeductImportEx deductImport) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("ids", deductImport.getPaybackApplyId().split(","));
		map.put("dictPaybackStatus", RepayApplyStatus.PRE_PAYMENT.getCode());
		map.put("dictDeductType", OperateType.PAYMENT_DEDUCT.getCode());
		map.put("splitFlag", RepayApplyStatus.PRE_PAYMENT.getCode());
		List<PaybackApply> list = dao.findApplyPayback(map);
		if (list.size() == 0) {
			return null;
		}
		PaybackApply PaybackApply = list.get(0);

		LoanDeductEntity loanDeductentity = new LoanDeductEntity();
		// 汇金非集中回款
		loanDeductentity.setDeductSysIdType(DeductWays.HJ_02.getCode());
		loanDeductentity.setSysId(DeductWays.HJ_02.getCode());
		// 申请扣款金额
		BigDecimal applyAmount = PaybackApply.getApplyAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
		loanDeductentity.setApplyAmount(String.valueOf(applyAmount));
		// 实际扣款金额
		loanDeductentity.setDeductSucceedMoney(deductImport.getTrustAmount() == null ? "0" : deductImport.getTrustAmount());
		// 还款申请id
		loanDeductentity.setBatId(deductImport.getPaybackApplyId());
		// 合同编号
		// loanDeductentity.setBusinessId(deductImport.getContractCode());
		Map<String, String> contractMap = new HashMap<String, String>();
		contractMap.put("contractCode", deductImport.getContractCode());
		List<Payback> paybackList = deductUpdateDao.getPayback(contractMap); // 获取还款主表
		Payback payback = paybackList.get(0);
		loanDeductentity.setBusinessId(payback.getId());
		//失败原因
		if(deductImport.getReturnMsg() !=null){
		 loanDeductentity.setFailReason(deductImport.getReturnMsg());
		}
		return loanDeductentity;
	}

	/**
	 * 线上委托充值 
	 * 2016年3月13日 By 朱杰
	 * @param paybackSplit
	 * @return
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public String trustRecharge(PaybackApply paybackApply) {
		ClientPoxy service = new ClientPoxy(ServiceType.Type.JZH_WTRECHARGE);
		JzhWtrechargeInfo Info = new JzhWtrechargeInfo();
		// 流水号
		Info.setMchntTxnSsn(IdGen.uuid().substring(5));
		// 委托充值用户
		Info.setLoginId(paybackApply.getLoanCustomer().getTrusteeshipNo());
		// 委托充值金额
		Info.setAmt(paybackApply.getApplyAmount()
				.multiply(new BigDecimal("100")).toBigInteger().toString());
		// 备注
		Info.setRem(paybackApply.getContract().getContractCode() + "_委托充值_"
				+ paybackApply.getId());
		// 后台通知地址
		Info.setBackNotifyUrl("http");

		JzhWtrechargeOutInfo outInfo = (JzhWtrechargeOutInfo) service
				.callService(Info);

		// 委托充值成功，更新数据库
		TrusteeImportEx trusteeImport = new TrusteeImportEx();
		// 申请id
		trusteeImport.setPaybackApplyId(paybackApply.getId());
		// 合同编号
		trusteeImport.setContractCode(paybackApply.getContractCode());
		// 委托充值金额
		trusteeImport.setTrustAmount(paybackApply.getApplyAmount().toString());

		if ("0000".equals(outInfo.getRetCode())) {

			// 委托充值结果
			trusteeImport.setReturnCode(CounterofferResult.PAYMENT_SUCCEED
					.getCode());
			// 委托充值失败原因
			trusteeImport.setReturnMsg("");
			dao.updateTrustRecharge(trusteeImport);
		} else {
			// 委托充值结果
			trusteeImport.setReturnCode(CounterofferResult.PAYMENT_FAILED
					.getCode());
			// 委托充值失败原因
			trusteeImport.setReturnMsg(outInfo.getRetCode() + ":"
					+ outInfo.getRetMsg());
			dao.updateTrustRecharge(trusteeImport);
			return "【"+paybackApply.getContractCode()+"】委托充值失败："
					+outInfo.getRetCode() + ":" + outInfo.getRetMsg()+"<br>";
		}
		return "";
	}

	/**
	 * 线上划拨处理 2016年3月15日 By 朱杰
	 * 
	 * @param paybackSplit
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public String transferOnline(PaybackApply paybackApply) {
		String rtnMsg = "";
		// 获取划扣金额与付款帐户
		MoneyAccountInfo moneyAccountInfo = new MoneyAccountInfo();
		// 流水号
		moneyAccountInfo.setMchntTxnSsn(IdGen.uuid().substring(5));
		// 付款登录账户
		moneyAccountInfo.setOutCustNo(paybackApply.getLoanCustomer()
				.getTrusteeshipNo());
		// 收款人帐户 inCustNo
		moneyAccountInfo.setInCustNo(Global.getConfig("jzh_fk_account"));
		// 划拨金额
		moneyAccountInfo.setAmt(paybackApply.getApplyAmount()
				.multiply(new BigDecimal("100")).toBigInteger().toString());
		// 接口标识
		moneyAccountInfo.setFlag("JzhTransferBuInfo");
         
		//备注
		moneyAccountInfo.setRemark(paybackApply.getContractCode()+"_非集中_");
		JzhTransferBuOutInfo jzht = (JzhTransferBuOutInfo) fyMoneyAccountService
				.chooseInterface(moneyAccountInfo);

		TrustDeductImportEx elem = new TrustDeductImportEx();
		elem.setPaybackApplyId(paybackApply.getId());
		elem.setContractCode(paybackApply.getContractCode());
		LoanDeductEntity iteratorSplit = this.setLoanDeductEntity(elem);
		PaybackOpeEx paybackOpes = null;
		if (jzht.getRetCode().equals("0000")) {
			// 返回成功，则成功金额为申请金额
			iteratorSplit.setDeductSucceedMoney(paybackApply.getApplyAmount()
					.toString());
			paybackOpes = new PaybackOpeEx(paybackApply.getId(), paybackApply
					.getPayback().getId(), RepaymentProcess.ONLINE_TRANSFER,
					TargetWay.REPAYMENT,
					PaybackOperate.ONLINE_TRANSFER_SUCCESS.getCode(), "划拨"
							+ ":"
							+ paybackApply.getApplyAmount()
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.toString());
		} else {
			// 返回失败，则成功金额为0
//			elem.setTrustAmount("0");
//			elem.setReturnMsg(jzht.getRetMsg());
			iteratorSplit.setDeductFailMoney(paybackApply.getApplyAmount()
					.toString());
			iteratorSplit.setDeductSucceedMoney("0");
			rtnMsg = "【" + paybackApply.getContractCode() + "】线上划拨失败："
					+ jzht.getRetCode() + jzht.getRetMsg() + ";";
			paybackOpes = new PaybackOpeEx(paybackApply.getId(), paybackApply
					.getPayback().getId(), RepaymentProcess.ONLINE_TRANSFER,
					TargetWay.REPAYMENT,
					PaybackOperate.ONLINE_TRANSFER_FAILED.getCode(), rtnMsg);
		}
		insertPaybackOpe(paybackOpes);
		iteratorSplit.setDeductSysIdType(paybackApply.getDictDealType());
		iteratorSplit.setBusinessId(paybackApply.getPayback().getId());
		// 更新回款信息
		if (iteratorSplit != null) {
			deductUpdateService.updateDeductInfo(iteratorSplit);
		}
		return rtnMsg;
	}

	/**
	 * 批量退回 2016年4月27日 By 翁私
	 * @param map
	 * @return 退回信息
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void backApply(Map<String, Object> map,PaybackApply apply) {
		    String applyBackMes = (String) map.get("backmsg");
			apply.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_RETURN.getCode());
			apply.setApplyBackMes(applyBackMes);
			apply.setSplitBackResult(CounterofferResult.RETURN.getCode());
			paybackService.updatePaybackApply(apply);
			PaybackOpeEx paybackOpeEx = new PaybackOpeEx(apply.getId(), apply.getPayback().getId(), RepaymentProcess.DEDECT,
					TargetWay.REPAYMENT, PaybackOperate.REBACK.getCode(),applyBackMes);
			PaybackOpe paybackOpe = new PaybackOpe();
			paybackOpe.setrPaybackApplyId(paybackOpeEx.getrPaybackApplyId());
			paybackOpe.setrPaybackId(paybackOpeEx.getrPaybackId());
			paybackOpe.setDictLoanStatus(paybackOpeEx.getDictLoanStatus());
			paybackOpe.setDictRDeductType(paybackOpeEx.getDictRDeductType());
			paybackOpe.setRemarks(paybackOpeEx.getRemark());
			paybackOpe.setOperateResult(paybackOpeEx.getOperateResult());
			paybackOpe.preInsert();
			paybackOpe.setOperator(UserUtils.getUser().getId());
			paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
			paybackOpe.setOperateTime(new Date());
			loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}

	/**
	 * 发送批处理 2015年3月10日 By 翁私（一键发送）
	 * 
	 * @param deductReqList
	 * @param deductType
	 * @param deductReqListExcle
	 * @param flag
	 * @param statisticsMap   划扣统计信息
	 * @param conditionMap  划扣条件
	 * @param ruleMap  划扣跳转规则
	 * @return list
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean oneKeysendDeductor(DeductReq req,String deductType, String flag, 
			Map<String, BankRule> ruleMap, Map<String, DeductCondition> conditionMap, 
			Map<String, DeductStatistics> statisticsMap) {
		    boolean isSuccess = false;
			String bankCode = req.getBankId();
			BankRule rule = ruleMap.get(bankCode);
			if(ObjectHelper.isEmpty(rule)){
				return isSuccess;
			}
			String ruleCode = rule.getPlatformRule();
			log.info("【非集中一键发送】平台验证开始：跳转规则为"+ruleCode+"==="+fomat1.format(new Date()));
			ruleCode = planRuleVerification(ruleCode,req);
			log.info("【非集中一键发送】平台验证结束：跳转规则为"+ruleCode+"==="+fomat1.format(new Date()));
			if(ObjectHelper.isEmpty(ruleCode)){
				dao.updateLimitFlag(req);
				return isSuccess;
			}
			log.info("【非集中一键发送】业务规则验证开始：跳转规则为"+ruleCode+"==="+fomat1.format(new Date()));
			ruleCode = serviceRuleVerification(ruleCode,req,conditionMap,statisticsMap);
			log.info("【非集中一键发送】业务规则验证结束：跳转规则为"+ruleCode+"==="+fomat1.format(new Date()));
			if(ObjectHelper.isEmpty(ruleCode)){
				dao.updateLimitFlag(req);
				return isSuccess;
			}
			
			req.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			req.setRule(ruleCode);
			req.setSysId(DeductWays.HJ_02.getCode());	
		    DeResult t = new DeResult();
	try{
		PaybackApply apply = dao.queryDeductReq(req);
		if(apply == null){
			return isSuccess;
		}
		t = TaskService.addTask(req);
		req.setModifyby(UserUtils.getUser().getId());
		req.setCounterofferResult(CounterofferResult.PROCESS.getCode());
			if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
					int i = dao.updateApplyStatus(req);
					if(i<1){
						TaskService.rollBack(t.getDeductReq());
						isSuccess =  false;
					}
					if(req.getStatus().equals(CounterofferResult.PROCESSED.getCode())){
						dao.deleteDeductReq(req);
					}
					insertPaybackOpe(req, deductType, true,"");
					TaskService.commit(t.getDeductReq());
					isSuccess = true;
				} else {
					insertPaybackOpe(req, deductType, false, t.getReMge());
					TaskService.rollBack(t.getDeductReq());
					isSuccess =  false;
				}
			 }catch(Exception e){
			     TaskService.rollBack(t.getDeductReq());
			     isSuccess =  false;
			     throw new  ServiceException("发送批处理异常！"+e.getMessage());
		}
		return isSuccess;
			   
	}

	/**
	 *  将银行和平台组装在一起  2015年4月21日 By 翁私
	 * @return map
	 */
	public Map<String, String> bankPlantData(String flag) {
		Map<String,String>  map = new HashMap<String,String>();
		PlatformGotoRule entity = new PlatformGotoRule();
		BankPlantPort port = new BankPlantPort();
		
	    if(OperateType.COLLECT_DEDUCT.getCode().equals(flag)){
	    	entity.setIsConcentrate(YESNO.YES.getCode());
			port.setIsConcentrate(YESNO.YES.getCode());
			entity.setStatus(YESNO.YES.getCode());
		}else{
			entity.setIsConcentrate(YESNO.NO.getCode());
			entity.setStatus(YESNO.YES.getCode());
			port.setIsConcentrate(YESNO.NO.getCode());
		}
	    // 银行接口
	 	List<BankPlantPort>  pantlist   =   bankPlantPortService.findPlantList(port);
        // 跳转顺序
		List<PlatformGotoRule>  skilist   =  platformGotoRuleManager.findList(entity);
			
		for(BankPlantPort bankplant:pantlist){
			for(PlatformGotoRule plantSkip:skilist){
				StringBuffer rule = new StringBuffer();
				String[]  plants = bankplant.getPlantCode().split(",");
				String[]  ports = bankplant.getBatchFlag().split(",");
				String[]  skipplants = plantSkip.getPlatformRule().split(",");
				
				for(int i=0 ; i<skipplants.length ; i++){
					for(int j=0 ; j<plants.length ; j++){
						if(skipplants[i].equals(plants[j])){
								rule.append(plants[j]+":"+ports[j]+",");
						}
					}
				}
				String rulestring =rule.toString();
				if(!"".equals(rulestring) && rulestring != null){
					rulestring=rulestring.substring(0,rulestring.length()-1);
					map.put(bankplant.getBankCode()+""+plantSkip.getPlatformId(), rulestring);
				}
			}
		}
		return map;
	}
	
	/**
	 * 查询要划扣的记录
	 * 
	 * @author 翁私
	 * @Create In 2016年4月22日
	 * @param paramMap
	 * @return 划扣记录
	 */
	public List<DeductReq> queryDeductOneKeyList(Map<String, Object> map) {
		// 　取得规则
		List<DeductReq> list = dao.queryDeductReqList(map);
		return list;
	}

	/**
	 * 导入 单条事物 一条失败别的继续执行  2016年5月4日 By 翁私
	 * @param deductPayback
	 * @author none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateDeductResult(List<ImportEntity> deductList,String plant) {
		PaybackApply apply = null;
		if(deductList != null){
		 for(ImportEntity deductItem:deductList){
			if(deductItem.getId() != null && !"".equals(deductItem.getId())){
				String counteroffer =  CounterofferResult.PAYMENT_SUCCEED.getCode() ;// 待划扣
				PaybackSplit querySplit = new PaybackSplit();
				querySplit.setCounteroffer(counteroffer);
				querySplit.setId(deductItem.getId());
				PaybackSplit split = paybackSplitDao.querySplitByno(querySplit);
				// 判断这条数据是否已经更新过了
				if(split != null){
					String tradingStatus = deductItem.getTradingStatus();
					// 去除空格
					tradingStatus = tradingStatus.replaceAll(" ", ""); 
					if(tradingStatus.indexOf("成功") > -1 || tradingStatus.equals("0000")){
						split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
						// 如果成功更新蓝补
						split.preUpdate();
						split.setSplitAmount(deductItem.getSplitAmount());
						dao.updatePaybackBlue(split);
					    apply = dao.queryPaybackApply(split);
					    apply.setDictDealType(plant);
					    addPaybackBuleAmont("",deductItem.getSplitAmount(),
					    		apply.getPaybackBuleAmount(), TradeType.TRANSFERRED,
								AgainstContent.PAYBACK_DEDUCT, ChargeType.CHARGE_PRESETTLE,apply.getContractCode());
						}
					    
						// 更新 实际划扣金额
						if(apply.getApplyDeductAmount() != null && deductItem.getSplitAmount() != null){
							if(apply.getApplyReallyAmount() == null){
								apply.setApplyReallyAmount(new BigDecimal(0));
							}
							if(apply.getApplyDeductAmount().compareTo(apply.getApplyReallyAmount().add(deductItem.getSplitAmount()))==0){
								apply.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
								apply.setApplyReallyAmount(deductItem.getSplitAmount());
								apply.preUpdate();
								dao.updatePaybackApplyById(apply);
							}else{
								apply.setApplyReallyAmount(deductItem.getSplitAmount());
								apply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
								apply.preUpdate();
								dao.updatePaybackApplyById(apply);
							}
							if(deductItem.getSplitAmount() == null){
								deductItem.setSplitAmount(new BigDecimal(0));
							}
							PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
									apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
										TargetWay.REPAYMENT,
										PaybackOperate.IMPORT_SUCCESS.getCode(),"导入："+deductItem.getSplitAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					     	 // 插入历史信息
					     	insertPaybackOpe(paybackOpes);
						}
						
					}else{
						// 更新失败
						split.setSplitFailResult(deductItem.getFailReason());
						split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
						split.setSplitAmount(new BigDecimal(0)); 
					    apply = dao.queryPaybackApply(split);
					    apply.setDictDealType(plant);
						apply.setApplyReallyAmount(new BigDecimal(0));
						apply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
						apply.setFailReason(deductItem.getFailReason());
						apply.preUpdate();
					    dao.updatePaybackApplyById(apply);
						PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
								apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
									TargetWay.REPAYMENT,
									PaybackOperate.IMPORT_SUCCESS.getCode(),"导入：0.00");
				     	 // 插入历史信息
				     	insertPaybackOpe(paybackOpes);
					}
					deductItem.setSuccess(true);
					split.setSplitBackDate(new Date());
					split.preUpdate();
				    paybackSplitDao.updateSplitLineStatus(split);
				 }
		     } 
		  }
			// 最后导入完成后 删除 以前导出的数据
			apply.setSplitBackResult(CounterofferResult.PROCESSED.getCode());
	    	dao.deleteSplitByApply(apply);
	  }

	/**
	 * 单条拆分  2016年5月4日 By 翁私
	 * @param paybackApply
	 * @param code
	 * @param rightnow
	 * @param plat
	 * @return PaybackApply 
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public PaybackApply splitApply(PaybackApply paybackApply,
			String code, DeductTime rightnow, DeductPlat plat) {
		//dao.deleteSplit(paybackApply);
		PaybackSplit split = paymentSplitService.splitList(paybackApply,code, rightnow, plat);
		if(split != null){
			paybackApply.setSplitPch(split.getSplitPch());
		}
		paybackApply.preUpdate();
		paybackApply.setSplitBackResult(CounterofferResult.PROCESSED.getCode());
		dao.updateApplyStatusSigle(paybackApply);
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApply.getId(),
     			paybackApply.getPaybackId(), RepaymentProcess.LINE_EXPORT,
					TargetWay.REPAYMENT,
					PaybackOperate.EXPORT_SUCCESS.getCode(), "");
     	 // 插入历史信息
     	insertPaybackOpe(paybackOpes);
		return paybackApply;
	}
	
	/**
	 * 插入还款操作流水历史 
	 * 2016年1月6日 By 翁私
	 * @param PaybackOpeEx
	 * @return none
	 */
	public void insertPaybackOpe(PaybackOpeEx paybackOpeEx) {
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(paybackOpeEx.getrPaybackApplyId());
		paybackOpe.setrPaybackId(paybackOpeEx.getrPaybackId());
		paybackOpe.setDictLoanStatus(paybackOpeEx.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpeEx.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpeEx.getRemark());
		paybackOpe.setOperateResult(paybackOpeEx.getOperateResult());
		paybackOpe.preInsert();
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}
	
   /*
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateSystemSetting(){
		// 启动自动划扣
		SystemSetting sys = new SystemSetting();
		sys.setSysFlag("SYS_COLLECT_DEDUCT");
		sys.setSysValue(YESNO.YES.getCode());
		systemSetMaterDao.updateBySysFlag(sys);
	}
	*/
	
	
	/**
	 * 非集中开启滚动
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void startSystemSetting(){
		// 启动自动划扣
		SystemSetting sys = new SystemSetting();
		sys.setSysFlag("SYS_REPAYMENT_DEDUCT");
		sys.setSysValue(YESNO.YES.getCode());
		systemSetMaterDao.updateBySysFlag(sys);
	}
	
	/**
	 * 非集中停止滚动
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void stopSystemSetting(){
		// 启动自动划扣
		SystemSetting sys = new SystemSetting();
		sys.setSysFlag("SYS_REPAYMENT_DEDUCT");
		sys.setSysValue(YESNO.NO.getCode());
		systemSetMaterDao.updateBySysFlag(sys);
	}

	/**
	 * 锁住该条数据
	 * @param deductReq
	 * @return
	 */
	public PaybackApply queryDeductReq(DeductReq deductReq) {
		return  dao.queryDeductReq(deductReq);
	}
	
	/**
	 * 中金一次余额不足 不进行推送
	 * @param newRule
	 * @return
	 */
	public String getRule(String newRule){
		if (!StringUtils.isEmpty(newRule)) {
		    String[] rules = newRule.split(",");
			StringBuffer sb = new StringBuffer();
			for (String rule : rules) {
				String[] ru = rule.split(":");
				String platId = ru[0];
					if(DeductPlat.ZHONGJIN.getCode().equals(platId)){
					}else{
						sb.append(rule + ",");
					}
			 }
			newRule = sb.toString();
			if (newRule.endsWith(",")) {
				newRule = newRule.substring(0, newRule.length() - 1);
			}
			return newRule;
		}
		return newRule;
	}
	
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addPaybackBuleAmont(String rMonthId, BigDecimal tradeAmount,
			BigDecimal surplusBuleAmount, TradeType tradeType,
			AgainstContent againstContent, ChargeType chargeType,String contractCode) {
		PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
		paybackBuleAmont.preInsert();
		paybackBuleAmont.setrMonthId(rMonthId);// 关联ID（期供ID）
		paybackBuleAmont.setTradeType(tradeType.getCode());// 交易类型
		paybackBuleAmont.setTradeAmount(tradeAmount);// 交易金额
		paybackBuleAmont.setSurplusBuleAmount(surplusBuleAmount);// 蓝补余额
		paybackBuleAmont.setOperator(UserUtils.getUser().getId());// 操作人
		if (againstContent != null) {
			paybackBuleAmont.setDictDealUse(againstContent.getCode());// 冲抵内容
		}
		paybackBuleAmont.setDictOffsetType(chargeType.getCode());// 冲抵类型

		paybackBuleAmont.setDealTime(new Date());// 交易时间
		paybackBuleAmont.setContractCode(contractCode);

		dao.addBackBuleAmont(paybackBuleAmont);
	}

   /**
    * 查询是否有没有发送的数据
    * @return
    */
	public String queryDeductLimit() {
		
		return dao.queryDeductLimit();
	}

   /**
    * 查询自动划扣规则
    * @return
    */
   public List<BankRule> queryBankRule() {
	   Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("isConcentrate", YesNo.NO); //查询非集中的规则
		paramMap.put("status",UseFlag.QY.value); // 状态为启用的
	return dao.queryBankRule(paramMap);
   }

   /**
	 * 查询 划扣条件
	 * @return
	 */
	public List<DeductCondition> queryDeductCondition() {
		return dao.queryDeductCondition();
	}

    /**
     * 查询划扣统计信息
     * @param map
     * @return
     */
   public List<DeductStatistics> queryDeductStatistics(Map<String,String> map) {
		return dao.queryDeductStatistics(map);
	}
   
   
	/**
	 *  根据当前数据的银行取得跳转规则
	 *  然后判断当前跳转规则里面的平台是否符合业务规则
	 *  
	 *  1 当前平台的签约平台是富有 才能发送给富有
	 *  2 当前平台签约通联 才能发送给通联
	 *  3 当前平台签约卡联 才能发送卡联
	 *  4 当前平台签约畅捷 才能发送畅捷
	 *  如果不符合业务规则，则该平台从跳转规则中去掉
	 * @param ruleCode
	 * @param req
	 * @return rule
	 */
	 public String planRuleVerification(String ruleCode,DeductReq req){
		 StringBuilder skipRule = new StringBuilder();
		 if(ObjectHelper.isEmpty(ruleCode)){
			 return null;
		 }
		 String[] rules = ruleCode.split(",");
		 for(String rule : rules){
			String[]  plants =   rule.split(":");
			// 富有
			if(DeductPlatType.FY.getCode().equals(plants[0])){
				if(!DeductPlatType.FY.getCode().equals(req.getFuYouSign())){
					log.info("【非集中一键发送】该数据签约平台不是富有平台，规则中富有平台去掉batid="+req.getBatId()+"===="+fomat1.format(new Date()));
					continue;
				}
			}
			// 通联
			if(DeductPlatType.TL.getCode().equals(plants[0])){
				String tongLianSign = req.getTongLianSign();
				if(ObjectHelper.isEmpty(tongLianSign)){
					tongLianSign  = YesNo.NO;
				 }
				if(YesNo.NO.equals(tongLianSign)){
					log.info("【非集中一键发送】该数据没有签约通联平台，规则中通联平台去掉batid="+req.getBatId()+"===="+fomat1.format(new Date()));
					continue;
				}
			}
			// 畅捷
		    if(DeductPlatType.CJT.getCode().equals(plants[0])){
		    	String changJieSign = req.getChangJieSign();
				if(ObjectHelper.isEmpty(changJieSign)){
					changJieSign  = YesNo.NO;
				 }
				if(YesNo.NO.equals(changJieSign)){
					log.info("【非集中一键发送】该数据没有签约畅捷平台，规则中畅捷平台去掉batid="+req.getBatId()+"===="+fomat1.format(new Date()));
					continue;
				}
			}
		    // 卡联
		    if(DeductPlatType.KL.getCode().equals(plants[0])){
		    	String kaLianSign = req.getKaLianSign();
				if(ObjectHelper.isEmpty(kaLianSign)){
					kaLianSign  = YesNo.NO;
				 }
				if(YesNo.NO.equals(kaLianSign)){
					log.info("【非集中一键发送】该数据没有签约卡联平台，规则中卡联平台去掉batid="+req.getBatId()+"===="+fomat1.format(new Date()));
					continue;
				}
			}
		    skipRule.append(rule+",");
		   }
		    
		   if(skipRule.length() == 0){
			   return null;
		   }
		   return skipRule.substring(0, skipRule.length()-1);
		 
	 }
	 
	 /**
	  * 根据当前数据的银行取得跳转规则
	  * 然后判断当前跳转规则里面的平台是否符合业务规则
	  * 
	  * 1 在一定的基数下当前平台的余额不足比例是否超过业务配置的余额不足比例，如果超过了该平台从跳转规则中去掉
	  *
	  * 2 在一定的基数下当前平台的失败率是否超过业务配置的失败率，如果超过了该平台从跳转规则中去掉
	  * 
	  * 3  在一定的基数下当前平台的失败笔数是否超过业务配置的失败笔数，如果超过了该平台从跳转规则中去掉
	  * 
	  * 4  如果当前平台是通联，划扣金额大于业务配置的金额，则划扣方式为配置的（实时或者批量）
	  *   如果当前平台是通联，划扣金额小于等于业务配置的金额，则划扣方式为配置的（实时或者批量）
	  * 
	  * @param ruleCode
	  * @param conditionMap
	  * @return rule
	  */
	 public String  serviceRuleVerification(String ruleCode,DeductReq req,Map<String,DeductCondition> conditionMap,
			 Map<String,DeductStatistics> statisticsMap){
		 StringBuilder skipRule = new StringBuilder();
		 if(ObjectHelper.isEmpty(ruleCode)){
			 return null;
		 }
		 String[] rules = ruleCode.split(",");
		 for(String rule : rules){
				String[]  plants =   rule.split(":");
				// 统计信息
				DeductStatistics sta = statisticsMap.get(plants[0]);
				// 业务配置信息
				DeductCondition  con = conditionMap.get(plants[0]);
				if(!ObjectHelper.isEmpty(sta) && !ObjectHelper.isEmpty(con)){
					// 判断余额不足基数,余额不足率
					
			    if(!ObjectHelper.isEmpty(sta.getDeductNumber()) && !ObjectHelper.isEmpty(con.getNotEnoughBase())){
					if( sta.getDeductNumber() > con.getNotEnoughBase()){
						 if(!ObjectHelper.isEmpty(sta.getNotEnoughProportion()) && !ObjectHelper.isEmpty(con.getNotEnoughProportion())){
							 if(sta.getNotEnoughProportion().compareTo(con.getNotEnoughProportion()) > 0){
									log.info("【非集中一键发送】该数据要发送的平台余额不足比例已经超过配置，规则中去掉该平台"
											+ "batid="+req.getBatId()+"平台为"+plants[0]+"===="+fomat1.format(new Date()));
									continue;
								 }
						 }
						
					}
			   }

				if(!ObjectHelper.isEmpty(sta.getFailureNumber()) && !ObjectHelper.isEmpty(con.getFailureNumber())){
						// 判断失败条数
						if( sta.getFailureNumber() > con.getFailureNumber()){
							log.info("【非集中一键发送】该数据要发送的平台失败条数已经超过配置，规则中去掉该平台"
									+ "batid="+req.getBatId()+"平台为"+plants[0]+"===="+fomat1.format(new Date()));
							    continue;
						}
					}
					
					// 判断失败率基数，失败率
				if(!ObjectHelper.isEmpty(sta.getDeductNumber()) && !ObjectHelper.isEmpty(con.getFailureBase())){
					if( sta.getDeductNumber() > con.getFailureBase()){
						 if(!ObjectHelper.isEmpty(sta.getFailureRate()) && !ObjectHelper.isEmpty(con.getFailureRate())){
							 if(sta.getFailureRate().compareTo(con.getFailureRate()) > 0){
								 log.info("【非集中一键发送】该数据要发送的平台失败率已经超过配置，规则中去掉该平台"
											+ "batid="+req.getBatId()+"平台为"+plants[0]+"===="+fomat1.format(new Date()));
								 continue;
							 }
						 }
					}
				 }
		 }
				// 通联要根据划扣金额来选择不同的划扣方式
				if(DeductPlatType.TL.getCode().equals(plants[0])){
					// 1是大于,2 是小于等于
						if(req.getAmount().compareTo(con.getDeductMoney1())<=0){
							if("2".equals(con.getMoneySymbol1())){
								rule = plants[0]+":"+con.getDeductType1();
							}else{
								rule = plants[0]+":"+con.getDeductType2();
							}
						}else{
							if("1".equals(con.getMoneySymbol1())){
								rule = plants[0]+":"+con.getDeductType1();
							}else{
								rule = plants[0]+":"+con.getDeductType2();
							}
						}
			 	  }
				// 通联平台 如果是：邮储、光大、交通 则发送批量接口
				 if(DeductPlatType.TL.getCode().equals(plants[0])){
					String[] bankCodes = TongLianBankBatch.batchBank;
					for(String bankCode : bankCodes){
						if(bankCode.equals(req.getBankId())){
							rule = plants[0]+":"+DeductType.BATCH.getCode();
							 continue;
						}
					}
				}
				skipRule.append(rule+",");
		  }
		  if(skipRule.length() == 0){
			   return null;
		  }
		 return skipRule.substring(0, skipRule.length()-1);
	 }


}
