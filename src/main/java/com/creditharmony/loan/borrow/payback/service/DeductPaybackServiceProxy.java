package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.proxy.DeductTaskProxy;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.DeductWay;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
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
import com.creditharmony.loan.borrow.payback.entity.DeductPayback;
import com.creditharmony.loan.borrow.payback.entity.ImportEntity;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeducts;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitFyEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitHylExport;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitTlEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackSplitZjEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TlImport;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrustDeductImportEx;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;
import com.creditharmony.loan.borrow.payback.entity.ex.ZjImport;
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
//TODO 此类是从DeductPaybackService拷贝过来,以备DeductPaybackService做恢复
@Service("deductPaybackServiceProxyTemp")
public class DeductPaybackServiceProxy extends CoreManager<DeductPaybackDao, DeductPayback> {

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
	private DeductTaskProxy deductTaskProxy;
	
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
	 * 保存还款拆分表
	 * @author 翁私
	 * @Create In 2015年12月29日
	 * @param dataList
	 * @param plat
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void addSplitLineList(List<DeductPayback> dataList, String plat) {
		for (DeductPayback deductPayback : dataList) {
			if (StringUtils.isNotEmpty(deductPayback.getId())) {
				PaybackSplit paybackSplit = new PaybackSplit();
				paybackSplit.setrId(deductPayback.getId());// 申请表id
				paybackSplit.setIsNewRecord(false);
				paybackSplit.preInsert();
				paybackSplit.setSplitAmount(deductPayback.getSplitAmount());// 申请金额
				paybackSplit.setDictDealType(plat);// 划扣平台
				paybackSplit.setDictRDeductType(TargetWay.PAYMENT.getCode());
				paybackSplit.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());// 回盘结果
				paybackSplit.setSplitBackDate(new Date());// 划扣时间
				paybackSplitDao.insert(paybackSplit);
			}
		}
	}

	/**
	 * 保存还款划扣记录
	 * @author 翁私
	 * @Create In 2015年12月29日
	 * @param dataList
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void addPaybackDeductsList(List<DeductPayback> dataList) {
		for (DeductPayback deductPayback : dataList) {
			if (StringUtils.isNotEmpty(deductPayback.getEnterpriseSerialno())) {
				PaybackSplit paySplit = new PaybackSplit();
				paySplit.setEnterpriseSerialno(deductPayback.getEnterpriseSerialno());
				// 根据拆分表的id查询要保持的划扣记录数据
				PaybackDeducts paybackDeducts = paybackSplitDao
						.queryPaybackDeductsBean(paySplit);
				if (paybackDeducts != null) {
					paybackDeducts.setIsNewRecord(false);
					paybackDeducts.preInsert();
					paybackDeducts.setDictDecuctFlag(DeductWay.OFFLINE.getCode());
					paybackDeducts.setDictDeductsType(DeductTime.BATCH.getCode());
					String tradingStatus = deductPayback.getTradingStatus();
					// 去除空格
					tradingStatus = tradingStatus.replaceAll(" ", "");
					if ("商户已复核,交易成功".equals(tradingStatus)) {
						paybackDeducts.setDictBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
					} else {  // 失败
						paybackDeducts.setDictBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					}
					paybackDeducts.setDecuctTime(new Date());
					paybackDeducts.setDecuctUser(UserUtils.getUser()
							.getUserCode());
					paybackSplitDao.addPaybackDeducts(paybackDeducts);
				}
			}
		}

	}

	/**
	 * 更新 还款申请表的状态
	 * @author 翁私
	 * @Create In 2015年12月29日
	 * @param dataList
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackStatus(List<DeductPayback> dataList) {
		for (DeductPayback deductPayback : dataList) {
			if (StringUtils.isNotEmpty(deductPayback.getEnterpriseSerialno())) {
				String tradingStatus = deductPayback.getTradingStatus();
				// 去除空格
				tradingStatus = tradingStatus.replaceAll(" ", "");
				PaybackApply apply = new PaybackApply();
				if ("商户已复核,交易成功".equals(tradingStatus)) {
					apply.setDictPaybackStatus(RepayApplyStatus.PRE_CHARGE.getCode());
				} else { // 失败
					apply.setDictPaybackStatus(RepayApplyStatus.DEDUCTT_FAILED.getCode());
				}
				apply.setId(deductPayback.getEnterpriseSerialno());
				dao.updatePaybackStatus(apply);
			}
		}
	}

	/**
	 * 待还款划扣列表： 线上划扣 
	 * 2015年12月29日 By 李强
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertBuckleOnLine(PaybackSplit paybackSplit) {
		dao.insertBuckleOnLine(paybackSplit);
	}

	/**
	 * 待还款划扣已办：线上批量划扣后修改申请状态为"已拆分" 
	 * 2015年12月30日 By 李强
	 * @param id
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateRepayApplyStatus(PaybackSplit paybackSplits) {
		dao.updateRepayApplyStatus(paybackSplits);
	}

	/**
	 * 更新拆分标的划扣标示 2015年1月6日 By 翁私
	 * 
	 * @param map
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackSplit(Map<String, String> map) {
		dao.updatePaybackSplit(map);
	}

	/**
	 * 更新申请表的申请状态为已拆分 
	 * 2015年1月6日 By 翁私
	 * @param map
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackApply(Map<String, String> map) {
		dao.updatePaybackApply(map);

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
	 * 根据交易状态 更新还款拆分表 的回盘结果 (富有) 
	 * 2015年1月6日 By 翁私
	 * @param dataList
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplitResultList(List<DeductPayback> dataList) {
		PaybackSplit split = new PaybackSplit();
		for (DeductPayback back : dataList) {
			String id = back.getEnterpriseSerialno();
			if (id != null && !"".equals(id)) {

				String tradingStatus = back.getTradingStatus();
				// 去除空格
				tradingStatus = tradingStatus.replaceAll(" ", "");
				// 成功
				if ("商户已复核,交易成功".equals(tradingStatus)) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getRemarks())));
				} else {
					// 更新失败
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getRemarks())));
				}
				split.preUpdate();
				dao.updateSplitResultList(split);
			}
		}
	}

	/**
	 * 根据交易状态 更新还款拆分表 的回盘结果 (好易联) 2015年1月6日 By 翁私
	 * 
	 * @param dataList
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplitResultListHyl(List<PaybackSplitHylEx> dataList) {
		PaybackSplit split = new PaybackSplit();
		for (PaybackSplitHylEx back : dataList) {
			String id = back.getRemarkTwo();
			if (id != null && !"".equals(id)) {
				String tradingResults = back.getCustomUserName();
				// 去除空格
				tradingResults = tradingResults.replaceAll(" ", "");
				// 成功
				if (tradingResults.indexOf("成功") > -1
						|| tradingResults.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getTransactionAmount())));

				} else {
					// 更新失败
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getTransactionAmount())));
				}
				split.preUpdate();
				dao.updateSplitResultList(split);
			}
		}

	}

	/**
	 * 增加蓝补流水
	 * 
	 * @author 翁私
	 * @Create In 2015年1月14日
	 * @param paybackBuleAmont
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void addPaybackBuleAmont(PaybackBuleAmont paybackBuleAmont) {
		paybackBuleAmont.setDealTime(new Date());
		paybackBuleAmont.setIsNewRecord(false);
		paybackBuleAmont.preInsert();
		dao.addBackBuleAmont(paybackBuleAmont);
	}

	/**
	 * 查询要划扣的记录
	 * 
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
	 * 将申请表的数据改为 划扣中 (线下) 
	 * 2015年2月3日 By wengsi
	 * @param apply
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateApplyStatusOffline(List<PaybackApply> apply) {
		dao.updateApplyStatusOffline(apply);
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
	 * 删除拆分表的数据 
	 * 2016年2月4日 By wengsi
	 * @param paybackListExcle
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void deleteSplit(PaybackApply paybackListExcle) {
		dao.deleteSplit(paybackListExcle);
	}

	/**
	 * 删除拆分表的数据 
	 * 2016年2月4日 By wengsi
	 * @param deductReqListExcel
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void deleteSplitDeductReq(List<DeductReq> deductReqListExcel) {
		dao.deleteSplitDeductReq(deductReqListExcel);
	}

	/**
	 * 查询导出的数据 把数据整合成划扣实体 2016年2月17日 By wengsi
	 * 
	 * @param none
	 * @return List<LoanDeductEntity>
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<LoanDeductEntity> queryLoanDeductList(Map<String, String> map) {
		return dao.queryLoanDeductList(map);
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
		return dao.getDeductPaybackListTl(apply);
	}

	/**
	 * 根据交易状态 更新还款拆分表 的回盘结果 (通联) 
	 * 2015年3月1日 By 翁私
	 * @param dataList
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplitResultListTl(List<TlImport> dataList) {
		PaybackSplit split = new PaybackSplit();
		for (TlImport back : dataList) {
			String remark = back.getRemark();
			if (remark != null && !"".equals(remark)) {
				String returnCode = back.getDealStatus();
				// 去除空格
				returnCode = returnCode.replaceAll(" ", "");
				// 成功
				if (returnCode.indexOf("成功") > -1 || returnCode.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(remark);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getTradeAmount())));

				} else {
					// 更新失败
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(remark);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getTradeAmount())));
					split.setDictDealStatus("2");
				}
				split.preUpdate();
				dao.updateSplitResultList(split);
			}
		}
	}

	/**
	 * 将回盘结果改为最终的结果（导入时候更新通联） 
	 * 2015年1月6日 By 翁私
	 * @param dataList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateResultTlEnd(List<TlImport> dataList) {
		PaybackSplit split = new PaybackSplit();
		for (TlImport back : dataList) {
			String remark = back.getRemark();
			if (remark != null && !"".equals(remark)) {
				String returnCode = back.getDealStatus();
				// 去除空格
				returnCode = returnCode.replaceAll(" ", "");
				// 成功
				if (returnCode.indexOf("成功") > -1 || returnCode.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PROCESSED
							.getCode());
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED
							.getCode());
				} else {
					// 更新失败
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
				}
				split.setSplitBackDate(new Date());
				split.setEnterpriseSerialno(remark);
				split.preUpdate();
				dao.updateSplitResultList(split);
			}
		}

	}

	/**
	 * 根据交易状态 更新还款拆分表 的回盘结果 (中金) 
	 * 2015年3月1日 By 翁私
	 * @param dataList
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSplitResultListZj(List<ZjImport> dataList) {
		PaybackSplit split = new PaybackSplit();
		for (ZjImport back : dataList) {
			String id = back.getRemark();
			if (id != null && !"".equals(id)) {
				String returnCode = back.getTradeStatus();
				// 去除空格
				returnCode = returnCode.replaceAll(" ", "");
				// 成功
				if (returnCode.indexOf("成功") > -1 || returnCode.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getAmount())));

				} else {
					// 更新失败
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getAmount())));
					split.setDictDealStatus("2");
				}
				split.preUpdate();
				dao.updateSplitResultList(split);
			}
		}
	}

	/**
	 * 将回盘结果改为最终的结果（导入时候更新中金）
	 * 2015年1月6日 By 翁私
	 * @param dataList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateResultZjEnd(List<ZjImport> dataList) {
		PaybackSplit split = new PaybackSplit();
		for (ZjImport back : dataList) {
			String id = back.getRemark();
			if (id != null && !"".equals(id)) {
				String returnCode = back.getTradeStatus();
				// 去除空格
				returnCode = returnCode.replaceAll(" ", "");
				// 成功
				if (returnCode.indexOf("成功") > -1 || returnCode.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PROCESSED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getAmount())));
				} else {
					// 更新失败
					split.setSplitBackResult(CounterofferResult.PROCESSED
							.getCode());
					split.setSplitBackDate(new Date());
					split.setEnterpriseSerialno(id);
					split.setSplitAmount(new BigDecimal(Double.parseDouble(back
							.getAmount())));
					split.setDictDealStatus("2");
				}
				split.preUpdate();
				dao.updateSplitResultList(split);
			}
		}

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
	public void insertPaybackOperate(DeductReq deductReq, String deductType,
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
	 * 发送批处理
	 * 2015年3月10日 By 翁私
	 * @param deductReqList
	 * @param deductType
	 * @param deductReqListExcle
	 * @param flag
	 * @return list
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String sendBatch(DeductReq deductRequest, String deductType, String flag) {
		String message = "";
		DeResult deductResult = new DeResult();
		try{
			deductResult = deductTaskProxy.saveDeductRequest(deductRequest);
			deductRequest.setModifyby(UserUtils.getUser().getId());
			deductRequest.setCounterofferResult(CounterofferResult.PROCESS.getCode());
			if (deductResult.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
				if (OperateType.COLLECT_DEDUCT.getCode().equals(flag)) {
					int i = dao.updateApplyHisStatus(deductRequest);
					if (i < 1) {
						deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
						return null;
					}
				} else {
					int i = dao.updateApplyStatus(deductRequest);
					if (i < 1) {
						deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
						return null;
					}
				}
				if (deductRequest.getCounterofferResult().equals(
						CounterofferResult.PROCESSED.getCode())) {
					dao.deleteDeductReq(deductRequest);
				}
				insertPaybackOperate(deductRequest, deductType, true, "");
				deductTaskProxy.commitDeductRequest(deductResult.getDeductReq());
			} else {
				message = deductResult.getReMge();
				insertPaybackOperate(deductRequest, deductType, false, deductResult.getReMge());
				deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
				throw new ServiceException("发送批处理异常！");
			}

		} catch (Exception e) {
			deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
			throw new ServiceException("发送批处理异常！" + e.getMessage() + message);
		}
		return message;
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
		trustDeduct.setPayeeLoginName(Global.getConfig("jzh_fk_name"));
		// 收款方中文名
		trustDeduct.setPayeeName(Global.getConfig("jzh_fk_account"));
		// 收款后是否立刻冻结
		trustDeduct.setFundFrozen(YESNO.NO.getName());
		// 交易金额
		trustDeduct.setTrustAmount(paybackDeduct.getApplyAmount());
		// 合同编号 + "委托充值" + 还款申请id
		trustDeduct.setRemarks(paybackDeduct.getContract().getContractCode()
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
		loanDeductentity.setApplyAmount(String.valueOf(PaybackApply
				.getApplyAmount()));
		// 实际扣款金额
		loanDeductentity.setDeductSucceedMoney(deductImport.getTrustAmount());
		// 还款申请id
		loanDeductentity.setBatId(deductImport.getPaybackApplyId());
		// 合同编号
		loanDeductentity.setBusinessId(deductImport.getContractCode());
		return loanDeductentity;
	}

	/**
	 * 线上委托充值 2016年3月13日 By 朱杰
	 * 
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
		trusteeImport.setContractCode(paybackApply.getContract()
				.getContractCode());
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
			return outInfo.getRetCode() + ":" + outInfo.getRetMsg();
		}
		return "";
	}

	/**
	 * 线上划拨处理 2016年3月15日 By 朱杰
	 * 
	 * @param paybackSplit
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void transferOnline(PaybackApply paybackApply) {
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

		JzhTransferBuOutInfo jzht = (JzhTransferBuOutInfo) fyMoneyAccountService
				.chooseInterface(moneyAccountInfo);

		TrustDeductImportEx elem = new TrustDeductImportEx();
		elem.setPaybackApplyId(paybackApply.getId());
		elem.setContractCode(paybackApply.getContract().getContractCode());
		if (jzht.getRetCode().equals("0000")) {
			// 返回成功，则成功金额为申请金额
			elem.setTrustAmount(paybackApply.getApplyAmount().toString());
		} else {
			// 返回失败，则成功金额为0
			elem.setTrustAmount("0");
		}
		LoanDeductEntity iteratorSplit = this.setLoanDeductEntity(elem);
		// 更新回款信息
		if (iteratorSplit != null) {
			deductUpdateService.updateDeductInfo(iteratorSplit);
		}
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
	 * @return list
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String oneKeysendDeductor(DeductReq deductRequest,
			String deductType, String flag) {
		String message = "";
		// 将银行和平台组装在一起
		Map<String,String>  map = bankPlantData(flag);
			//判断集中划扣代还款划扣
		if(OperateType.COLLECT_DEDUCT.getCode().equals(flag)){
			// 集中划扣设置划扣标志
			deductRequest.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			//  集中划扣系统处理ID
			deductRequest.setSysId(DeductWays.HJ_01.getCode());
			// 集中划扣设置划扣跳转规则]
			String rule = map.get(deductRequest.getBankId() + deductRequest.getSignPlate());
			deductRequest.setRule(rule);
		}else{
			// 代还款设置
			deductRequest.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			String rule = map.get(deductRequest.getBankId() + deductRequest.getSignPlate());
			// 设置划扣规则
			deductRequest.setRule(rule);
			deductRequest.setSysId(DeductWays.HJ_02.getCode());	
				
		}
		DeResult deductResult = new DeResult();
		try{
			deductResult = deductTaskProxy.saveDeductRequest(deductRequest);
			deductRequest.setModifyby(UserUtils.getUser().getId());
			deductRequest.setCounterofferResult(CounterofferResult.PROCESS.getCode());
			if (deductResult.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
					if(OperateType.COLLECT_DEDUCT.getCode().equals(flag)){
						int i = dao.updateApplyHisStatus(deductRequest);
					if (i < 1) {
							deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
							return null;
						}
						// 启动自动划扣
						SystemSetting sys = new SystemSetting();
						sys.setSysFlag("SYS_COLLECT_DEDUCT");
						sys.setSysValue(YESNO.YES.getCode());
						systemSetMaterDao.updateBySysFlag(sys);
						
					}else{
						int i = dao.updateApplyStatus(deductRequest);
						if(i<1){
							deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
							return null;
						}
					}
					if(deductRequest.getCounterofferResult().equals(CounterofferResult.PROCESSED.getCode())){
						dao.deleteDeductReq(deductRequest);
					}
					insertPaybackOperate(deductRequest, deductType, true, "");
					deductTaskProxy.commitDeductRequest(deductResult.getDeductReq());
				} else {
					message = deductResult.getReMge();
					insertPaybackOperate(deductRequest, deductType, false, deductResult.getReMge());
					deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
					throw new  ServiceException("发送批处理异常！");
				}
			 }catch(Exception e){
			     deductTaskProxy.cancelDeductRequest(deductResult.getDeductReq());
			     throw new  ServiceException("发送批处理异常！"+e.getMessage());
		}
		return message;
			   
	}

	/**
	 *  将银行和平台组装在一起  
	 *  2015年4月21日 By 翁私
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
	 * 富有导入 单条事物 一条失败别的继续执行  2016年5月4日 By 翁私
	 * @param deductPayback
	 * @author none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateDeductResult(List<ImportEntity> deductList) {
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
						apply.setApplyReallyAmount(deductItem.getSplitAmount());
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
	}

	/**
	 * 好易联导入 单条事物 一条失败别的继续执行  2016年5月4日 By 翁私
	 * @param deductPayback
	 * @author none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatehylResult(PaybackSplitHylEx deductItem) {
		if(deductItem.getId() != null && !"".equals(deductItem.getId())){
			String counteroffer =  CounterofferResult.PAYMENT_SUCCEED.getCode() ;// 待划扣
			PaybackSplit querySplit = new PaybackSplit();
			querySplit.setCounteroffer(counteroffer);
			querySplit.setId(deductItem.getId());
			PaybackSplit split = paybackSplitDao.querySplitByno(querySplit);
			// 判断这条数据是否已经更新过了
			if(split != null){
				String tradingStatus = deductItem.getTradingResults();
				// 去除空格
				tradingStatus = tradingStatus.replaceAll(" ", ""); 
				if (tradingStatus.indexOf("成功") > -1 || tradingStatus.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
					// 如果成功更新蓝补
					split.preUpdate();
					split.setSplitAmount(deductItem.getSplitAmount());
					dao.updatePaybackBlue(split);
					PaybackApply apply = dao.queryPaybackApply(split);
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
					split.setSplitFailResult(deductItem.getReturnPost());
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					split.setSplitAmount(deductItem.getSplitAmount()); 
					PaybackApply apply = dao.queryPaybackApply(split);
					apply.setApplyReallyAmount(new BigDecimal(0));
					apply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					apply.preUpdate(); 
					apply.setFailReason(deductItem.getReturnPost());
					dao.updatePaybackApplyById(apply);
					PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
							apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
								TargetWay.REPAYMENT,
								PaybackOperate.IMPORT_SUCCESS.getCode(),"导入：0.00");
			     	 // 插入历史信息
			     	insertPaybackOpe(paybackOpes);
				}
				split.setSplitBackDate(new Date());
				split.preUpdate();
			    paybackSplitDao.updateSplitLineStatus(split);
			}
	    }
		
		
	}

	/**
	 * 通联导入 单条事物 一条失败别的继续执行  2016年5月4日 By 翁私
	 * @param deductPayback
	 * @author none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateTlResult(TlImport deductItem) {
		if(deductItem.getId() != null && !"".equals(deductItem.getId())){
			String counteroffer =  CounterofferResult.PAYMENT_SUCCEED.getCode() ;// 待划扣
			PaybackSplit querySplit = new PaybackSplit();
			querySplit.setCounteroffer(counteroffer);
			querySplit.setId(deductItem.getId());
			PaybackSplit split = paybackSplitDao.querySplitByno(querySplit);
			// 判断这条数据是否已经更新过了
			if(split != null){
				String tradingStatus = deductItem.getDealStatus();
				// 去除空格
				tradingStatus = tradingStatus.replaceAll(" ", ""); 
				if (tradingStatus.indexOf("成功") > -1 || tradingStatus.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
					// 如果成功更新蓝补
					split.preUpdate();
					split.setSplitAmount(deductItem.getSplitAmount());
					dao.updatePaybackBlue(split);
					PaybackApply apply = dao.queryPaybackApply(split);
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
					split.setSplitFailResult(deductItem.getReturnPost());
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					split.setSplitAmount(new BigDecimal(0));  
					PaybackApply apply = dao.queryPaybackApply(split);
					apply.setApplyReallyAmount(deductItem.getSplitAmount());
					apply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					apply.preUpdate();  
					apply.setFailReason(deductItem.getReturnPost());
					dao.updatePaybackApplyById(apply);
						PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
								apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
									TargetWay.REPAYMENT,
									PaybackOperate.IMPORT_SUCCESS.getCode(),"导入：0.00");
				     	 // 插入历史信息
				     	insertPaybackOpe(paybackOpes);              
				}
				split.setSplitBackDate(new Date());
				split.preUpdate();
			    paybackSplitDao.updateSplitLineStatus(split);
			}
	    }
		
	}

	/**
	 * 中金导入 单条事物 一条失败别的继续执行  2016年5月4日 By 翁私
	 * @param deductPayback
	 * @author none
	 */
	public void updateZjResult(ZjImport deductItem) {
		if(deductItem.getId() != null && !"".equals(deductItem.getId())){
			String counteroffer =  CounterofferResult.PAYMENT_SUCCEED.getCode() ;// 待划扣
			PaybackSplit querySplit = new PaybackSplit();
			querySplit.setCounteroffer(counteroffer);
			querySplit.setId(deductItem.getId());
			PaybackSplit split = paybackSplitDao.querySplitByno(querySplit);
			// 判断这条数据是否已经更新过了
			if(split != null){
				String tradingStatus = deductItem.getTradeStatus();
				// 去除空格
				tradingStatus = tradingStatus.replaceAll(" ", ""); 
				if (tradingStatus.indexOf("成功") > -1 || tradingStatus.equals("0000")) {
					split.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
					// 如果成功更新蓝补
					split.preUpdate();
					split.setSplitAmount(deductItem.getSplitAmount());
					dao.updatePaybackBlue(split);
					PaybackApply apply = dao.queryPaybackApply(split);
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
					split.setSplitFailResult(deductItem.getReturnPost());	
					split.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					split.setSplitAmount(new BigDecimal(0)); 
					PaybackApply apply = dao.queryPaybackApply(split);
					apply.setApplyReallyAmount(deductItem.getSplitAmount());
					apply.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
					apply.setFailReason(deductItem.getReturnPost());
					apply.preUpdate();
					       dao.updatePaybackApplyById(apply);
					   	PaybackOpeEx paybackOpes = new PaybackOpeEx(apply.getId(),
								apply.getPaybackId(), RepaymentProcess.DATA_IMPORT,
									TargetWay.REPAYMENT,
									PaybackOperate.IMPORT_SUCCESS.getCode(),"导入：0.00");
				     	 // 插入历史信息
				     	insertPaybackOpe(paybackOpes);
				}
				split.setSplitBackDate(new Date());
				split.preUpdate();
			    paybackSplitDao.updateSplitLineStatus(split);
			}
	    }
		
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

}
