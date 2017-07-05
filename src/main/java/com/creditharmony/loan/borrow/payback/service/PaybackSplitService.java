package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.fortune.type.OpenBank;
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
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.CenterDeductDao;
import com.creditharmony.loan.borrow.payback.dao.DeductPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.PaybackSplitDao;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackTrustEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductImportEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.common.dao.DeductUpdateDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.sync.data.remote.FyMoneyAccountService;
import com.google.common.collect.Maps;

/**
 * 集中拆分列表service
 * @Class Name PaybackSplitService
 * @author zhaojinping
 * @Create In 2015年12月11日
 */
@Service
public class PaybackSplitService {

	@Autowired
	private PaybackSplitDao paybackSplitDao;
	
	@Autowired
	private CenterDeductDao centerDeductDao;
	@Autowired
	private ProvinceCityManager cityManager;
	@Autowired
	private FyMoneyAccountService fyMoneyAccountService;
	@Autowired
	private DeductUpdateService deductUpdateService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PaymentSplitService paymentSplitService;
	@Autowired
	private DeductPaybackService deductPaybackService;
	@Autowired
	private DeductPaybackDao deductPaybackDao;
	@Autowired
	private SystemSetMaterDao systemSetMaterDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private DeductUpdateDao deductUpdateDao;

	/**
	 * 获取集中划扣已拆分列表中的数据列表
	 * 2015年12月11日
	 * By zhaojinping
	 * @param page
	 * @param map
	 * @return 分页对象
	 */
	public Page<PaybackSplit> getAllList(Page<PaybackSplit> page,Map<String, Object> map) {
		String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
				+ CounterofferResult.PROCESS.getCode() + "','" //处理中
				+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
		
		map.put("counteroffer", counteroffer);
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackSplit> pageList = (PageList<PaybackSplit>) paybackSplitDao.getAllList(pageBounds,map);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		
		PaybackSplit split = new PaybackSplit();
		for (PaybackSplit paybackSplit : pageList) {
			if(paybackSplit.getLoanBank() != null){
				paybackSplit.getLoanBank().setBankNameLabel(DictUtils.getLabel(dictMap,"jk_open_bank", paybackSplit.getLoanBank().getBankName()));
			}
			if(paybackSplit.getPayback() != null){
				paybackSplit.getPayback().setDictPayStatusLabel(DictUtils.getLabel(dictMap,"jk_repay_status", paybackSplit.getPayback().getDictPayStatus()));
			}
			if(paybackSplit.getLoanInfo() != null){
				paybackSplit.getLoanInfo().setDictLoanStatusLabel(DictUtils.getLabel(dictMap,"jk_loan_apply_status", paybackSplit.getLoanInfo().getDictLoanStatus()));
				paybackSplit.getLoanInfo().setLoanFlagLabel(DictUtils.getLabel(dictMap,"jk_channel_flag", paybackSplit.getLoanInfo().getLoanFlag()));
			}
			paybackSplit.setSplitBackResultLabel(DictUtils.getLabel(dictMap,"jk_counteroffer_result", paybackSplit.getSplitBackResult()));
			paybackSplit.setTrustRechargeResultLabel(DictUtils.getLabel(dictMap,"jk_counteroffer_result", paybackSplit.getTrustRechargeResult()));			
			paybackSplit.setDictDealTypeLabel(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, paybackSplit.getDictDealType()));
			paybackSplit.setModelLabel(DictUtils.getLabel(dictMap,"jk_loan_model", paybackSplit.getModel()));
			int months = 0;
			if(paybackSplit.getPaybackMonth() != null){
				 months = paybackSplit.getPaybackMonth().getMonths();
			}
			String contractCode = "";
			if(paybackSplit.getContract() != null){
				contractCode = paybackSplit.getContract().getContractCode();
			}
			split.setMonths(months);
			split.setContractCode(contractCode);
			/*int overdueDays =  paybackSplitDao.queryOverdueDays(split);
			paybackSplit.setOverdueDays(overdueDays);*/
			
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 获取集中划扣已拆分列表中的数据列表 非分页
	 * 2016年3月13日
	 * By 朱杰
	 * @param map
	 * @return
	 */
	public List<PaybackSplit> getAllList(Map<String, Object> map) {
		return paybackSplitDao.getAllList(map);
	}

	/**
	 * 批量退回调用 翁私
	 * @param map
	 * @param split										
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void operateBackApply(Map<String, Object> map, PaybackSplit split){
		String msg = (String)map.get("backmsg");
		// 删除申请表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", split.getId());
		paybackSplitDao.backApply(paramMap);
		// 向还款操作流水记录表中插入记录
		PaybackOpeEx paybackOpes = new PaybackOpeEx(split.getId(),
				   split.getrPaybackId(), RepaymentProcess.DEDECT,
					TargetWay.PAYMENT,
					PaybackOperate.REBACK.getCode(),msg);
		//insertPaybackOpe(paybackOpes);
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(paybackOpes.getrPaybackApplyId());
		paybackOpe.setrPaybackId(paybackOpes.getrPaybackId());
		paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpes.getRemark());
		paybackOpe.setOperateResult(paybackOpes.getOperateResult());
		paybackOpe.preInsert();

		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		loanStatusHisDao.insertPaybackOpe(paybackOpe);
		
		PaybackApply paybackApply = new PaybackApply();
		paybackApply.setId(split.getrId());
		paybackApply.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_RETURN.getCode());
		paybackApply.setApplyBackMes(msg);
		paybackApply.preUpdate();
		paybackSplitDao.updateStatus(paybackApply);
		paybackApply.setDictPaybackStatus(CounterofferResult.RETURN.getCode());
		// 修改还款_待还款归档列表的状态
		paybackSplitDao.updateHisStatus(paybackApply);
    }
	
	/**
	 * 根据还款申请id获取还款主表id 
	 * 2015年12月11日 
	 * By zhaojinping
	 * @param id
	 * @return 主键
	 */
	public String getMainId(String id) {
		return paybackSplitDao.getMainId(id);
	}

	/**
	 * 根据还款主表Id,查询历史 
	 * 2015年12月11日
	 * By zhaojinping
	 * @param page
	 * @param mainId
	 * @return 分页对象
	 */
	public Page<PaybackOpe> getAllHirstory(Page<PaybackOpe> page,String mainId) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackOpe> pageList = (PageList<PaybackOpe>) paybackSplitDao.getAllHirstory(pageBounds,mainId);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 批量插入拆分数据
	 * 2015年12月23日
	 * By 王彬彬
	 * @param splitList
	 * @return 批量处理的数量
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int batchInsertSplitList(List<PaybackSplit> splitList) {
		return paybackSplitDao.batchInsertSplitData(splitList);
	}
	
	/**
	 * 将申请表的数据改为 划扣中
	 * 2015年12月24日
	 * By wengsi
	 * @param eductReqList
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatePaybackStatus(List<DeductReq> eductReqList) {
		paybackSplitDao.updatePaybackStatus(eductReqList);
	}
	
	/**
	 * 集中划扣已拆分列表 导出
	 * 2015年12月26日
	 * By wengsi
	 * @param idVal
	 * @return 富有导出列表
	 */
	public List<PaybackSplitFyEx> getPaybackSplitList(PaybackApply apply) {
		return paybackSplitDao.getPaybackSplitList(apply);
	}
	
	/**
	 * 查询要划扣的数据
     * 2015年12月30日
	 * By wengsi
	 * @param paybackSplit
	 * @return 借款申请列表
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public List<DeductReq> queryDeductReqList(Map<String,Object> map) {
		//　取得规则
		String rule = (String)map.get("rule");
		List<DeductReq> list =  paybackSplitDao.queryDeductReqList(map);
		for(DeductReq deductReq:list){
			// 设置划扣标志
			deductReq.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			// 设置划扣规则
			deductReq.setRule(rule);
			// 设置省
			/*if(!StringUtils.isEmpty(deductReq.getBankProv())){
			deductReq.setBankProv(cityManager.get(deductReq.getBankProv()).getAreaName());
			}
			// 设置市
			if(!StringUtils.isEmpty(deductReq.getBankCity())){
			deductReq.setBankCity(cityManager.get(deductReq.getBankCity()).getAreaName());
			}*/
			//  系统处理ID
			deductReq.setSysId(DeductWays.HJ_01.getCode());
		}
		return list;
		
	}
	
	
    /**
     * 查询要拆分的数据
	 * 2015年1月8日
	 * By wengsi
     * @param map
     * @return 申请列表
     */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public List<PaybackApply> queryApplyList(Map<String, Object> map) {
		return  paybackSplitDao.queryApplyList(map);
	}
	
	/**
	 * 导出数据时，从一个对象中copy属性
	 * 2016年3月10日
	 * By 王浩
	 * @param splitTrust
	 * @param paybackSplit
	 * @return 
	 */
	public PaybackTrustEx copyProperties(PaybackTrustEx splitTrust,
			PaybackSplit paybackSplit) {
		// 当前应还金额
		splitTrust.setTrustAmount(paybackSplit.getSplitAmount());
		splitTrust.setCustomerName(paybackSplit.getLoanCustomer()
				.getCustomerName());
		splitTrust.setTrusteeshipNo(paybackSplit.getLoanCustomer().getTrusteeshipNo());
		// 合同编号 + "委托充值" + 还款申请ID
		splitTrust.setRemarks(paybackSplit.getContract().getContractCode() 
				+ "_"	+ DeductedConstantEx.PAYBACK_TRUST 
				+ "_" + paybackSplit.getId());
		return splitTrust;
	}

	/**
	 * 更新委托提现信息
	 * 2016年3月10日
	 * By 王浩
	 * @param trusteeImport 
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateTrustRecharge(TrusteeImportEx trusteeImport) {
		paybackSplitDao.updateTrustRecharge(trusteeImport);
	}

	/**
	 * 导出线下划扣的时候，复制属性
	 * 2016年3月12日
	 * By 王浩
	 * @param trustDeduct
	 * @param paybackDeduct
	 * @return 
	 */
	public TrustDeductEx copyDeductProperties(TrustDeductEx trustDeduct,
			PaybackSplit paybackSplit) {		
		// 付款方登录名		
		trustDeduct.setTrusteeshipNo(paybackSplit.getLoanCustomer()
				.getTrusteeshipNo());
		// 付款方中文名称		
		trustDeduct.setCustomerName(paybackSplit.getLoanCustomer()
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
		trustDeduct.setTrustAmount(paybackSplit.getSplitAmount());
		// 合同编号 + "委托充值" + 还款申请id
		trustDeduct.setRemarks(paybackSplit.getContract().getContractCode() 
						+ "_"	+ DeductedConstantEx.PAYBACK_TRUST_SPLIT 
						+ "_" + paybackSplit.getId());
		return trustDeduct;
	}

	/**
	 * 设置更新回盘结果所需的实体
	 * 2016年3月13日
	 * By 王浩
	 * @param deductImport
	 * @return 
	 */
	public LoanDeductEntity setLoanDeductEntity(TrustDeductImportEx deductImport) {		
		Map<String, Object> map = Maps.newHashMap();
		map.put("ids", deductImport.getPaybackApplyId().split(","));
		map.put("dictOptType",OperateType.COLLECT_DEDUCT.getCode());
		map.put("dictPaybackStatus",RepayApplyStatus.PRE_PAYMENT.getCode());
		List<PaybackSplit> list = paybackSplitDao.getAllList(map);
		if(list.size() == 0){
			return null;
		}
		PaybackSplit paybackSplit = list.get(0);		
		
		LoanDeductEntity loanDeductentity = new LoanDeductEntity();
		// 汇金集中回款
		loanDeductentity.setDeductSysIdType(DeductWays.HJ_01.getCode());
		loanDeductentity.setSysId(DeductWays.HJ_01.getCode()); 
		// 申请扣款金额
		BigDecimal applyAmount = paybackSplit.getSplitAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
		loanDeductentity.setApplyAmount(String.valueOf(applyAmount));
		// 实际扣款金额
		loanDeductentity.setDeductSucceedMoney(deductImport.getTrustAmount());
		// 还款申请id
		loanDeductentity.setBatId(deductImport.getPaybackApplyId());
		// 合同编号
		// loanDeductentity.setBusinessId(deductImport.getContractCode());
		Map<String, String> contractMap = new HashMap<String, String>();
		contractMap.put("contractCode", deductImport.getContractCode());
		List<Payback> paybackList = deductUpdateDao.getPayback(contractMap); // 获取还款主表
		Payback payback = paybackList.get(0);
		loanDeductentity.setBusinessId(payback.getId());
		// 期供编号
		loanDeductentity.setRefId(paybackSplit.getPaybackMonth().getId());
		return loanDeductentity;
		
	}
	
	/**
	 * 线上委托充值
	 * 2016年3月13日
	 * By 朱杰
	 * @param paybackSplit
	 * @return
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public String trustRecharge(PaybackSplit paybackSplit){
		PaybackSplit split = paybackSplitDao.queryPaybackSplit(paybackSplit);
		if(split != null){
			ClientPoxy service = new ClientPoxy(ServiceType.Type.JZH_WTRECHARGE);
			JzhWtrechargeInfo Info = new JzhWtrechargeInfo();
			//流水号
			Info.setMchntTxnSsn(IdGen.uuid().substring(5));
			//委托充值用户
			Info.setLoginId(paybackSplit.getLoanCustomer().getTrusteeshipNo());
			//委托充值金额
			Info.setAmt(paybackSplit.getSplitAmount().multiply(new BigDecimal("100")).toBigInteger().toString());
			//备注
			Info.setRem(paybackSplit.getContract().getContractCode()+"_委托充值_"+paybackSplit.getId());
			//后台通知地址
			Info.setBackNotifyUrl("http");
			JzhWtrechargeOutInfo outInfo = (JzhWtrechargeOutInfo)service.callService(Info);
			
			//委托充值成功，更新数据库
			TrusteeImportEx trusteeImport = new TrusteeImportEx();
			//申请id
			trusteeImport.setPaybackApplyId(paybackSplit.getId());
			//合同编号
			trusteeImport.setContractCode(paybackSplit.getContract().getContractCode());
			//委托充值金额
			trusteeImport.setTrustAmount(paybackSplit.getSplitAmount().toString());
			if("0000".equals(outInfo.getRetCode())){
				//委托充值结果
				trusteeImport.setReturnCode(CounterofferResult.PAYMENT_SUCCEED.getCode());
				//委托充值失败原因
				trusteeImport.setReturnMsg("");
				paybackSplitDao.updateTrustRecharge(trusteeImport);
			}else{
				//委托充值结果
				trusteeImport.setReturnCode(CounterofferResult.PAYMENT_FAILED.getCode());
				//委托充值失败原因
				trusteeImport.setReturnMsg(outInfo.getRetCode() + ":" + outInfo.getRetMsg());
				paybackSplitDao.updateTrustRecharge(trusteeImport);
				return "【"+paybackSplit.getContract().getContractCode() + "】委托充值失败："
						+ outInfo.getRetCode() + ":" + outInfo.getRetMsg()+"<br>";
			}
		}
		return "";
	}
	
	/**
	 * 线上划拨处理
	 * 2016年3月15日
	 * By 朱杰
	 * @param paybackSplit
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public String transferOnline(PaybackSplit paybackSplit){
		String rtnMsg="";
		PaybackSplit split = paybackSplitDao.queryPaybackSplit(paybackSplit);
		if(split != null){
			// 获取划扣金额与付款帐户 
			MoneyAccountInfo moneyAccountInfo = new MoneyAccountInfo();
			// 流水号
			moneyAccountInfo.setMchntTxnSsn(IdGen.uuid().substring(5));
			// 付款登录账户
			moneyAccountInfo.setOutCustNo(paybackSplit.getLoanCustomer().getTrusteeshipNo());
			// 收款人帐户 inCustNo
			moneyAccountInfo.setInCustNo(Global.getConfig("jzh_fk_account"));
			// 划拨金额
			moneyAccountInfo.setAmt(paybackSplit.getSplitAmount().multiply(new BigDecimal("100")).toBigInteger().toString());
			// 接口标识
			moneyAccountInfo.setFlag("JzhTransferBuInfo");
			//备注信息
			moneyAccountInfo.setRem(paybackSplit.getContract().getContractCode()+"_集中_"+paybackSplit.getId());
			JzhTransferBuOutInfo jzht = (JzhTransferBuOutInfo) fyMoneyAccountService.chooseInterface(moneyAccountInfo);
			
			TrustDeductImportEx elem = new TrustDeductImportEx();
			elem.setPaybackApplyId(paybackSplit.getId());
			elem.setContractCode(paybackSplit.getContract().getContractCode());
			
			PaybackOpeEx paybackOpes = null;
			if(jzht.getRetCode().equals("0000")){
				//返回成功，则成功金额为申请金额
				elem.setTrustAmount(paybackSplit.getSplitAmount().toString());	
			    paybackOpes = new PaybackOpeEx(paybackSplit.getId(),
			    		paybackSplit.getrPaybackId(), RepaymentProcess.ONLINE_TRANSFER,
							TargetWay.PAYMENT,
							PaybackOperate.ONLINE_TRANSFER_SUCCESS.getCode(),"划拨" + ":" + paybackSplit.getSplitAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}else{
				//返回失败，则成功金额为0
				elem.setTrustAmount("0");
				rtnMsg = "【"+paybackSplit.getContract().getContractCode()+"】划扣失败:"
						+ jzht.getRetCode() + jzht.getRetMsg() + "<br>";
			    paybackOpes = new PaybackOpeEx(paybackSplit.getId(),
			    		paybackSplit.getrPaybackId(), RepaymentProcess.ONLINE_TRANSFER,
							TargetWay.PAYMENT,
							PaybackOperate.ONLINE_TRANSFER_FAILED.getCode(),rtnMsg);
			}
			insertPaybackOpe(paybackOpes);
			LoanDeductEntity iteratorSplit = this.setLoanDeductEntity(elem);
			iteratorSplit.setBusinessId(paybackSplit.getrPaybackId());
			// 更新回款信息
			if (iteratorSplit != null) {
				deductUpdateService.updateDeductInfo(iteratorSplit);
			}
		}
		return rtnMsg;
	}
	
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public PaybackApply splitApply(PaybackApply paybackApply,
			String code, DeductTime rightnow, DeductPlat plat) {
		// deductPaybackService.deleteSplit(paybackApply);
		PaybackSplit split = paymentSplitService.splitList(paybackApply,code, rightnow, plat);
		paybackApply.setSplitPch(split.getSplitPch());
		paybackApply.preUpdate();
		paybackApply.setSplitBackResult(CounterofferResult.PROCESSED.getCode());
     	paybackSplitDao.updateApplyStatusSigle(paybackApply);
     	PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApply.getId(),
     			paybackApply.getPaybackId(), RepaymentProcess.LINE_EXPORT,
					TargetWay.PAYMENT,
					PaybackOperate.EXPORT_SUCCESS.getCode(), "");
     	 // 插入历史信息
     	insertPaybackOpe(paybackOpes);
		return paybackApply;
	}
	
	/**
	 * 查询要划扣的数据(一键发送)
     * 2015年12月30日
	 * By wengsi
	 * @param paybackSplit
	 * @return 借款申请列表
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public List<DeductReq> queryDeductOneKeyList(Map<String,Object> map) {
		//　取得规则
		List<DeductReq> list =  paybackSplitDao.queryDeductReqList(map);
		return list;
		
	}

	/**
	 * 启动或者停止滚动划扣(集中划扣)
	 * 2016年4月22日
	 * By 翁私
	 * @param sys
	 * @return success
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void startOrstopRolls(SystemSetting sys) {
		sys.setSysFlag("SYS_COLLECT_DEDUCT");
		systemSetMaterDao.updateBySysFlag(sys);
		
	}
	
	
	/**
	 * 插入还款操作流水历史 
	 * 2016年1月6日 By zhangfeng
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
	
	/**
	 * 导入处理  2016年5月20日 By 翁私
	 * @param deductPayback
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateDeductResult(List<ImportEntity> deductList,String plant){
		PaybackApply apply = null;
		if(deductList != null){
	 for(ImportEntity deductPayback:deductList){
		if(deductPayback.getId() != null && !"".equals(deductPayback.getId())){
			PaybackSplit querySplit = new PaybackSplit();
			querySplit.setCounteroffer(CounterofferResult.PAYMENT_SUCCEED.getCode());
			querySplit.setId(deductPayback.getId());
			PaybackSplit split = paybackSplitDao.querySplitByno(querySplit);
			// 判断这条数据是否已经更新过了
			if(split != null){
				String tradingStatus = deductPayback.getTradingStatus();
				// 去除空格
				tradingStatus = tradingStatus.replaceAll(" ", ""); 
				if(tradingStatus.indexOf("成功") > -1 || tradingStatus.equals("0000")){
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
					// 如果成功更新蓝补
					split.preUpdate();
					split.setSplitAmount(deductPayback.getSplitAmount());
					paybackSplitDao.updatePaybackBlue(split);
				    apply = paybackSplitDao.queryPaybackApply(split);
				    apply.setDictDealType(plant);
				    addPaybackBuleAmont(apply.getMonthId(),deductPayback.getSplitAmount(),
				    		apply.getPaybackBuleAmount(), TradeType.TRANSFERRED,
							AgainstContent.CENTERDEDUCT, ChargeType.CHARGE_PRESETTLE,apply.getContractCode());
				    
					// 更新 实际划扣金额
					if(apply.getApplyDeductAmount() != null && deductPayback.getSplitAmount() != null){
						if(apply.getApplyReallyAmount() == null){
							apply.setApplyReallyAmount(new BigDecimal(0));
						}
						if(apply.getApplyDeductAmount().compareTo(apply.getApplyReallyAmount().add(deductPayback.getSplitAmount()))==0){
							apply.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
							apply.setApplyReallyAmount(deductPayback.getSplitAmount());
							paybackSplitDao.updatePaybackApply(apply);
							deductUpdateDao.insertDeductsPaybackApplyHis(apply);
							deductUpdateDao.deleteDeductsPaybackApply(apply);
						}else{
							apply.setApplyReallyAmount(deductPayback.getSplitAmount());
							apply.setSplitBackResult(CounterofferResult.PAYMENT_CONTINUE.getCode());
							paybackSplitDao.updatePaybackApply(apply);
						}
						PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
								apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
									TargetWay.PAYMENT,
									PaybackOperate.IMPORT_SUCCESS.getCode(),"导入："+deductPayback.getSplitAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				     	 // 插入历史信息
				     	insertPaybackOpe(paybackOpes);
					}
				}else{
				    
				    // 更新失败
					split.setSplitFailResult(deductPayback.getFailReason());
					split.setSplitBackResult(CounterofferResult.PAYMENT_CONTINUE.getCode());
					split.setSplitAmount(new BigDecimal(0)); 
				    apply = paybackSplitDao.queryPaybackApply(split);
					apply.setApplyReallyAmount(new BigDecimal(0));
					apply.setSplitBackResult(CounterofferResult.PAYMENT_CONTINUE.getCode());
					apply.setFailReason(deductPayback.getFailReason());
					apply.setDictDealType(plant);
					paybackSplitDao.updatePaybackApply(apply);
					PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
							apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
								TargetWay.PAYMENT,
								PaybackOperate.IMPORT_SUCCESS.getCode(),"导入：0.00");
			     	 // 插入历史信息
			     	insertPaybackOpe(paybackOpes);
				}
				deductPayback.setSuccess(true);
				split.setSplitBackDate(new Date());
				split.preUpdate();
			    paybackSplitDao.updateSplitLineStatus(split);
			}
	       }
	     }
	            // 最后导入完成后 删除 以前导出的数据
				apply.setSplitBackResult(CounterofferResult.PROCESSED.getCode());
				deductPaybackDao.deleteSplitByApply(apply);
	   }
    }

	public SystemSetting getSystemSetting(SystemSetting sys) {
		return paybackSplitDao.getSystemSetting(sys);
	}

	/**
	 * 线上划扣
	 * @param deductReq
	 * @param deductType
	 * @param code
	 * @return
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
   public boolean sendBatch(DeductReq req,String deductType, String flag) {
		DeResult t = new DeResult();
		boolean isSuccess =false;
	try {
		    PaybackSplit split =  paybackSplitDao.getApply(req);
			// 如果等于 null 的时候 说明该条数据已经被更新过了。
			if(split == null){
				return isSuccess;
			}
			// 中金有俩次余额不足的情况 不进行发送
		     String  rule = getTlRule(req);
			 if("".equals(rule)){
				 return isSuccess;
			}
		    req.setRule(rule);
	     	t = TaskService.addTask(req);
		    req.setModifyby(UserUtils.getUser().getId());
		    req.setCounterofferResult(CounterofferResult.PROCESS.getCode());
		if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
				int i = deductPaybackDao.updateApplyHisStatus(req);
				if (i < 1) {
							TaskService.rollBack(t.getDeductReq());
							isSuccess =  false;
						}
			   if (req.getStatus().equals(CounterofferResult.PROCESSED.getCode())) {
						deductPaybackDao.deleteDeductReq(req);
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
		}else if(DeductPlat.KALIAN.getCode().equals(deductType)){
			dictLoanStatus = RepaymentProcess.SEND_KALIAN.getCode();
		}else if(DeductPlat.CHANGJIE.getCode().equals(deductType)){
			dictLoanStatus = RepaymentProcess.SEND_CHANGJIE.getCode();
		}
		paybackOpe.setrPaybackApplyId(deductReq.getBatId());
		paybackOpe.setrPaybackId(deductReq.getRequestId());
		paybackOpe.setDictLoanStatus(dictLoanStatus);
		paybackOpe.setDictRDeductType(TargetWay.PAYMENT.getCode());
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
	 * 一键发送
	 * @param deductReq
	 * @param deductType
	 * @param code
	 * @return
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public boolean oneKeysendDeductor(DeductReq req,
			String deductType, String flag,Map<String,String> map) {
	 	 boolean isSuccess = false;
		 // 集中划扣设置划扣标志
		 req.setDeductFlag(DeductFlagType.COLLECTION.getCode());
		 // 集中划扣系统处理ID
		 req.setSysId(DeductWays.HJ_01.getCode());
		 // 集中划扣设置划扣跳转规则]
		 String rule = map.get(req.getBankId()+req.getSignPlate());
		 // 中金有俩次余额不足的情况 不进行发送
		 rule = getRule(req.getCpcnCount(),req.getOverdueDays(),rule);
		 if("".equals(rule) || rule == null){
			 return isSuccess;
		 }
		 req.setRule(rule);
	     DeResult t = new DeResult();
	try{
		PaybackSplit split =  paybackSplitDao.getApply(req);
		// 如果等于 null 的时候 说明该条数据已经被更新过了。
		if(split == null){
			return isSuccess;
		}
		t = TaskService.addTask(req);
		req.setModifyby(UserUtils.getUser().getId());
		req.setCounterofferResult(CounterofferResult.PROCESS.getCode());
	    if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
			int i = deductPaybackDao.updateApplyHisStatus(req);
			if(i<1){
				TaskService.rollBack(t.getDeductReq());
				isSuccess =  false;
			}
			if(req.getStatus().equals(CounterofferResult.PROCESSED.getCode())){
				deductPaybackDao.deleteDeductReq(req);
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
	 *通联二次余额不足 不进行推送
	 * @param newRule
	 * @return
	 */
	public String getTlRule(DeductReq req){
		int tlcount = req.getTlCount();
		String newRule = req.getRule();
		String bankCode = req.getBankId();
		String openBank = OpenBank.ZGYH.value;
		if (!StringUtils.isEmpty(newRule)) {
		    String[] rules = newRule.split(",");
			StringBuffer sb = new StringBuffer();
			for (String rule : rules) {
				String[] ru = rule.split(":");
				String platId = ru[0];
				    // 如果平台是通联  ，有俩次余额不足，还是中国银行就停止发送  。     
					if(DeductPlat.TONGLIAN.getCode().equals(platId) && openBank.equals(bankCode)){
						if(tlcount >=2){
							
						 }else{
						  sb.append(rule + ",");
						}
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
	
	/**
	 * 中金一次余额不足 不进行推送
	 * @param newRule
	 * @return
	 */
	public String getRule(int cpcnCount,int overdueDays,String newRule){
		if (!StringUtils.isEmpty(newRule)) {
		    String[] rules = newRule.split(",");
			StringBuffer sb = new StringBuffer();
			for (String rule : rules) {
				String[] ru = rule.split(":");
				String platId = ru[0];
					if(DeductPlat.ZHONGJIN.getCode().equals(platId)){
						if(overdueDays >=120){
						 }else{
						  sb.append(rule + ",");
						}
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
	
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public Page<PaybackSplit> getDeductPage(
			Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) {
		Page<PaybackSplit> page = new Page<PaybackSplit>(request, response);
		paramMap.put("limit", page.getPageSize());
		//overdueManageEx.setLimit(page.getPageSize());
		String counteroffer = "'"+CounterofferResult.PREPAYMENT.getCode() + "','" //待划扣
				+ CounterofferResult.PROCESS.getCode() + "','" //处理中
				+ CounterofferResult.PROCESSED.getCode() + "','" //处理中（导出）
				+ CounterofferResult.PAYMENT_CONTINUE.getCode() +"'";//失败继续处理
		
		paramMap.put("counteroffer", counteroffer);
		if (page.getPageNo() <= 1) {
			paramMap.put("offset",0);
		} else {
			paramMap.put("offset",(page.getPageNo() - 1) * page.getPageSize());
		}
		List<PaybackSplit> pageList = null;
		long cnt = paybackSplitDao.getCnt(paramMap);
		PaybackSplit split = new PaybackSplit();
		if(cnt>0){
			pageList = paybackSplitDao.getAllList(paramMap);
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			for (PaybackSplit paybackSplit : pageList) {
				if(paybackSplit.getLoanBank() != null){
					paybackSplit.getLoanBank().setBankNameLabel(DictUtils.getLabel(dictMap,"jk_open_bank", paybackSplit.getLoanBank().getBankName()));
				}
				if(paybackSplit.getPayback() != null){
					paybackSplit.getPayback().setDictPayStatusLabel(DictUtils.getLabel(dictMap,"jk_repay_status", paybackSplit.getPayback().getDictPayStatus()));
				}
				if(paybackSplit.getLoanInfo() != null){
					paybackSplit.getLoanInfo().setDictLoanStatusLabel(DictUtils.getLabel(dictMap,"jk_loan_apply_status", paybackSplit.getLoanInfo().getDictLoanStatus()));
					paybackSplit.getLoanInfo().setLoanFlagLabel(DictUtils.getLabel(dictMap,"jk_channel_flag", paybackSplit.getLoanInfo().getLoanFlag()));
				}
				paybackSplit.setSplitBackResultLabel(DictUtils.getLabel(dictMap,"jk_counteroffer_result", paybackSplit.getSplitBackResult()));
				paybackSplit.setTrustRechargeResultLabel(DictUtils.getLabel(dictMap,"jk_counteroffer_result", paybackSplit.getTrustRechargeResult()));			
				paybackSplit.setDictDealTypeLabel(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, paybackSplit.getDictDealType()));
				paybackSplit.setModelLabel(DictUtils.getLabel(dictMap,"jk_loan_model", paybackSplit.getModel()));
				paybackSplit.setTlSignLabel(DictUtils.getLabel(dictMap,"yes_no", paybackSplit.getTlSign()));
				int months = 0;
				if(paybackSplit.getPaybackMonth() != null){
					 months = paybackSplit.getPaybackMonth().getMonths();
					 split.setId(paybackSplit.getPaybackMonth().getId());
					 PaybackMonth month = paybackSplitDao.queryPaybackMonth(split);
					 paybackSplit.setPaybackMonth(month);
					 
				}
				String contractCode = "";
				if(paybackSplit.getContract() != null){
					contractCode = paybackSplit.getContract().getContractCode();
				}
				split.setMonths(months);
				split.setContractCode(contractCode);
				// 逾期天数 和逾期次数
				PaybackSplit over =  paybackSplitDao.queryOverdueDays(split);
				PaybackSplit overcount =  paybackSplitDao.queryOverdueCount(split);
				if(over != null){
					paybackSplit.setOverdueDays(over.getOverdueDays());
				}
				if(overcount != null){
					paybackSplit.setOverCount(overcount.getOverCount());
				}
				
			}
			page.setCount(cnt);
			page.setList(pageList);
		}else{
			page.setCount(0);
			pageList = new ArrayList<PaybackSplit>();
			page.setList(pageList);
		}
		return page;
	 }
	
	/**
	 * 增加蓝补信息
	 * @param rMonthId
	 * @param tradeAmount
	 * @param surplusBuleAmount
	 * @param tradeType
	 * @param againstContent
	 * @param chargeType
	 * @param contractCode
	 */
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

		deductPaybackDao.addBackBuleAmont(paybackBuleAmont);
	}

	/**
	 * 查询当前用户的角色
	 * @param userId
	 * @return
	 */
	public int selectRoleCount(String userId) {
		return deductPaybackDao.selectRoleCount(userId);
	}
}
