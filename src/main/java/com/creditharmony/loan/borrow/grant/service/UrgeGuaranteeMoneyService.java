package com.creditharmony.loan.borrow.grant.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.BigDecimalTools;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.OperateMatching;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.loan.borrow.grant.dao.UrgeGuaranteeMoneyDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesCheckApply;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.dao.DealPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.PaybackTransferOutDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackService;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.common.constants.PaybackConstants;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackBlueAmountDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.CeUtils;

/**
 * 催收保证金处理
 * @Class Name UrgeGuaranteeMoneyService
 * @author 朱静越
 * @Create In 2016年1月11日
 */
@Service("urgeGuaranteeMoneyService")
public class UrgeGuaranteeMoneyService extends
		CoreManager<UrgeGuaranteeMoneyDao, UrgeServicesMoneyEx> {

	@Autowired
	private ApplyPaybackService applyPaybackService;
	@Autowired
	private UrgeServicesMoneyDao urgeServiceDao;
	@Autowired
	private PaybackTransferOutDao paybackTransferOutDao;
	@Autowired
	private UrgeServicesMoneyDao urgeServicesMoneyDao;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired
	private DealPaybackDao dealPaybackDao;
	@Autowired
	private PaybackBlueAmountDao paybackBlueAmountDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private DealPaybackService dealPaybackService;
	@Autowired
	private HistoryService historyService;

	/**
	 * 催收服务费带催收列表查询 2015年12月15日 By 张振强
	 * 
	 * @param urgeServicesMoneyEx
	 * @param page
	 * @return 分页对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<UrgeServicesMoneyEx> selectGuaranteeMoneyList(
			Page<UrgeServicesMoneyEx> page,
			UrgeServicesMoneyEx urgeServicesMoneyEx) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<UrgeServicesMoneyEx> pageList = (PageList<UrgeServicesMoneyEx>) dao
				.selectGuaranteeMoneyList(pageBounds, urgeServicesMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询催收保证金待催收列表 
	 * 2016年3月11日 By 朱静越
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UrgeServicesMoneyEx> selGuaranteeList(
			UrgeServicesMoneyEx urgeServicesMoneyEx) {
		return dao.selectGuaranteeMoneyList(urgeServicesMoneyEx);
	}

	/**
	 * 根据催收服务费id查询失败的拆分数据。 
	 * 2015年12月15日 By 张振强
	 * @param paybackSplit
	 * @return 拆分集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackSplit> selectpaybackSplit(PaybackSplit paybackSplit) {

		return dao.selectpaybackSplit(paybackSplit);
	}

	/**
	 * 根据催收申请表中的申请状态或者查询条件进行查账匹配列表的查询显示 
	 * 2016年3月1日 By 朱静越
	 * @param page
	 * @param urgeServicesMoneyEx
	 * 查询条件
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<UrgeServicesMoneyEx> selCheckInfo(
			Page<UrgeServicesMoneyEx> page,
			UrgeServicesMoneyEx urgeServicesMoneyEx) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<UrgeServicesMoneyEx> pageList = (PageList<UrgeServicesMoneyEx>) dao
				.selCheckInfo(pageBounds, urgeServicesMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询催收服务费查账匹配列表
	 * 2016年5月24日
	 * By 朱静越
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	public List<UrgeServicesMoneyEx> getUrgeList(UrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selCheckInfo(urgeServicesMoneyEx);
	}

	/**
	 * 更改拆分表的的发送状态为是（"0"） 
	 * 2015年12月15日 By 张振强
	 * @param paybackSplit
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatepaybackSplit(PaybackSplit paybackSplit) {
		paybackSplit.preUpdate();
		dao.updatepaybackSplit(paybackSplit);

	}

	/**
	 * 更新info表中的内容 2015年12月11日 By zhujignyue
	 * 
	 * @param urgeServicesMoneyEx
	 * @param files
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void savePayBackTransferInfo(
			UrgeServicesMoneyEx urgeServicesMoneyEx, MultipartFile[] files) {
		String dictDeposit = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getDictDeposit();
		String tranDepositTime = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getTranDepositTimeStr();
		String storesInAccount = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getStoresInAccount();
		String reallyAmount = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getReallyAmountStr();
		String depositName = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getDepositName();
		String[] dictDeposits = dictDeposit.split(",");
		String[] tranDepositTimes = tranDepositTime.split(",");
		String[] reallyAmounts = reallyAmount.split(",");
		String[] depositNames = depositName.split(",");
		for (int i = 0; i < reallyAmounts.length; i++) {
			if (dictDeposits.length != 0 && dictDeposits != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDictDeposit(
						dictDeposits[i].trim());
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDictDeposit(
						null);
			}
			if (tranDepositTimes.length != 0 && tranDepositTimes != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setTranDepositTime(
								DateUtils.parseDate(tranDepositTimes[i].trim()));
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setTranDepositTime(null);
			}
			if (!StringUtils.isEmpty(storesInAccount)
					&& storesInAccount != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setStoresInAccount(storesInAccount);
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setStoresInAccount(null);
			}
			if (depositNames.length != 0 && depositNames != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDepositName(
						depositNames[i].trim());
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDepositName(
						null);
			}
			if (reallyAmounts.length != 0 && reallyAmounts != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo().setReallyAmount(
						new BigDecimal(reallyAmounts[i].trim()));
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDepositName(
						null);
			}
			// 上传新文件
			if (files[i].getSize() > 0) {
				DocumentBean db = CeUtils.uploadFile(files[i],
						TargetWay.SERVICE_FEE.getCode(),
						CeFolderType.URGE_UPLOAD);
				
				if (!ObjectHelper.isEmpty(db)) {
					urgeServicesMoneyEx.getPaybackTransferInfo().setUploadPath(db.getDocId());
					urgeServicesMoneyEx.getPaybackTransferInfo().setUploadFilename(db.getDocTitle());
				}
			}
			urgeServicesMoneyEx.getPaybackTransferInfo().setAuditStatus(
					BankSerialCheck.CHECKE_SUCCEED.getCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setContractCode(
					urgeServicesMoneyEx.getContractCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setUploadName(
					UserUtils.getUser().getId());
			urgeServicesMoneyEx.getPaybackTransferInfo().setModifyBy(
					UserUtils.getUser().getUserCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setUploadDate(
					new Date());
			urgeServicesMoneyEx.getPaybackTransferInfo()
					.setrPaybackApplyId(urgeServicesMoneyEx.getId());
			urgeServicesMoneyEx.getPaybackTransferInfo().setRelationType(
					TargetWay.SERVICE_FEE.getCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setIsNewRecord(
					false);
			urgeServicesMoneyEx.getPaybackTransferInfo().preInsert();
			dao.savePayBackTransferInfo(urgeServicesMoneyEx);
		}
	}

	/**
	 * 查询查账账款列表 
	 * 2015年12月18日 By zhangfeng
	 * @param paybackTransferInfo
	 * @return list
	 */
	public List<PaybackTransferInfo> findUrgeTransfer(
			PaybackTransferInfo paybackTransferInfo) {
		return dao.findUrgeTransfer(paybackTransferInfo);
	}

	/**
	 * 更新汇款表状态 
	 * 2015年12月25日 By zhangfeng
	 * @param paybackTransferInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateInfoStatus(PaybackTransferInfo paybackTransferInfo) {
		dao.updateInfoStatus(paybackTransferInfo);
	}

	/**
	 * 更新汇款流水表状态(applyId) 
	 * 2015年12月25日 By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutStatuByApplyId(PaybackTransferOut paybackTransferOut) {
		dao.updateOutStatuByApplyId(paybackTransferOut);
	}

	/**
	 * 更新汇款流水表状态(id) 
	 * 2015年12月25日 By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutStatuById(PaybackTransferOut paybackTransferOut) {
		dao.updateOutStatuById(paybackTransferOut);
	}

	/**
	 * 更新催收服务费查账申请表的实际到账金额 
	 * 2016年3月4日 By zhangfeng
	 * @param urgeApply
	 * @retrun none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateUrgeApply(UrgeServicesCheckApply urgeApply) {
		dao.updateUrgeApply(urgeApply);
	}

	/**
	 * 匹配规则
	 * 2016年4月26日
	 * By 朱静越
	 * @param urgeId 催收服务费主表ID
	 * @param applyId 催收服务费申请表ID
	 * @param contractCode 合同编号
	 * @param blueAmount 蓝补金额
	 * @return boolean 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean matchingRule(String urgeId, String applyId, String contractCode, BigDecimal blueAmount) {
		
		// 取该申请表中的info表中的数据，未查账和查账失败的状态进行匹配
		List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferInfo pi = new PaybackTransferInfo();
		BigDecimal auditAmount = BigDecimal.ZERO; // 查账金额
		int sum = 0; // 汇款单匹配次数
		// 通过id和状态进行查询，进行mofity_time的控制
		UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", applyId);
		map.put("reqStatus", UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeApply = dao.getApplyUrgeReq(map);
		
		if (ObjectHelper.isNotEmpty(urgeApply)) {
			pi.setrPaybackApplyId(applyId);
			pi.setAuditStatus("'"+ BankSerialCheck.CHECKE_SUCCEED.getCode() + "','"+BankSerialCheck.CHECKE_FAILED.getCode()+"'");
			pi.setRelationType(TargetWay.SERVICE_FEE.getCode());
			infoList = dao.findUrgeTransfer(pi);
			if(ArrayHelper.isNotEmpty(infoList)){
				for(PaybackTransferInfo info: infoList){
					// 实际存款人如果是（存现、现金、转帐、转款、支付宝、无 ），则不参加匹配
					if (!validationDepositName(info.getDepositName())) {
						// 查询流水（存入日期，存入银行，存入金额，存入人）匹配成功返回流水ID
						String urgeMatchingOutId = urgeMatchingSuccess(info);
						if(StringUtils.isNotEmpty(urgeMatchingOutId)){
							updateMatching(info, urgeMatchingOutId);
							auditAmount = auditAmount.add(info.getReallyAmount());
							sum++;
							continue;
						}
					}
					// 存款人长度大于2才参加模糊匹配
					if (validationDepositNameLength(info.getDepositName())) {
						// 查询流水（存入日期，存入银行，存入金额，备注包含存款人）匹配成功返回流水ID
						String matchingRemarkOutId = urgeMatchingRemarkSuccess(info);
						if(StringUtils.isNotEmpty(matchingRemarkOutId)){
							updateMatching(info, matchingRemarkOutId);
							auditAmount = auditAmount.add(info.getReallyAmount());
							sum++;
							continue;
						}
					}
				}
				
				// 汇款条数和汇款条数想等，该申请匹配成功
				if (StringUtils.equals(String.valueOf(infoList.size()), String.valueOf(sum))) {
					updateUrgeMatchingTaskSuccess(contractCode, applyId, urgeId, blueAmount,auditAmount,urgeApply.getUrgeReallyAmount(),urgeApply.getReqTime());
					return true;
				} else {
					// 成功查账一条更新实际到账金额到申请表,只更新催收服务费查账申请表
					if(auditAmount.compareTo(BigDecimal.ZERO) == 1){
						updateSingUrgeMatchingTaskSuccess(applyId, auditAmount, urgeApply.getUrgeReallyAmount(), urgeApply.getReqTime());
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 汇款匹配成功一条,更新实际到账金额
	 * @param applyId
	 * @param auditAmount
	 * @param urgeReallyAmount
	 * @param reqTime
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateSingUrgeMatchingTaskSuccess(String applyId,BigDecimal auditAmount,BigDecimal urgeReallyAmount,String reqTime) {
		UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
		urgeApply.setId(applyId);
		urgeApply.setReqTime(reqTime);
		urgeApply.setUrgeReallyAmount(urgeReallyAmount.add(auditAmount));
		urgeApply.preUpdate();
		int sum = dao.updateUrgeApply(urgeApply);
		if (sum == 0) {
			throw new ServiceException("批量匹配发生异常");
		}
	}

	/**
	 * 匹配成功操作
	 * @param contractCode
	 * @param applyId
	 * @param urgeId
	 * @param blueAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateUrgeMatchingTaskSuccess(String contractCode, String applyId,
			String urgeId, BigDecimal blueAmount,BigDecimal auditAmount,BigDecimal urgeReallyAmount,String reqTime) {
		// 更新催收服务费查账申请表的实际到账金额
		UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
		urgeApply.setId(applyId);
		urgeApply.setReqTime(reqTime);
		urgeApply.setUrgeReallyAmount(urgeReallyAmount.add(auditAmount));
		urgeApply.setUrgeBackReason("");
		urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
		urgeApply.preUpdate();
		int updSum = dao.updateUrgeApply(urgeApply);
		
		if (updSum > 0) {
			BigDecimal applyAmount = urgeReallyAmount.add(auditAmount); 
			// 全部匹配成功修改导入流水状态，查账历史
			updateTransferOutStatus(applyId, contractCode);
			// 修改催收服务费主表
			updateUrgeServicesMoney(urgeId, applyAmount);
			// 修改蓝补 ：蓝补金额 = 已划扣金额 + 查账金额 - 催收金额
			UrgeServicesMoney um = new UrgeServicesMoney();
			um = urgeServicesMoneyDao.find(urgeId);
			BigDecimal bg = um.getUrgeDecuteMoeny().add(applyAmount).subtract(um.getUrgeMoeny());
			// 查账成功,剩下金额进入蓝补,并且审核结果为审核通过之后，剩余金额进入蓝补
			String checkResult = urgeServicesMoneyDao.selGrant(um.getrGrantId()).getCheckResult();
			if (bg.compareTo(BigDecimal.ZERO) > 0 && VerityStatus.PASS.getCode().equals(checkResult)) {
				updatePayback(contractCode, blueAmount, bg);
				// 插入蓝补交易记录表
				savePaybackBlueAmount(contractCode, blueAmount, applyAmount);
			}

			// 匹配成功操作历史
			PaybackOpeEx paybackOpes = new PaybackOpeEx(applyId, urgeId,
					RepaymentProcess.MATCHING, TargetWay.SERVICE_FEE,
					PaybackOperate.MATCH_SUCCEED.getCode(), "批量匹配成功");
			historyService.insertPaybackOpe(paybackOpes);
		}else {
			throw new ServiceException("批量匹配异常！");
		}
	}

	/**
	 * 查询流水（存入日期，存入银行，存入金额） 存款人不一样匹配失败
	 * @param info
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private boolean urgeMatchingOfflineSuccess(PaybackTransferInfo info) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut out = new PaybackTransferOut();
		// 存入日期
		out.setOutDepositTime(info.getTranDepositTime());
		// 存入银行
		out.setOutEnterBankAccount(info.getStoresInAccount());
		// 存入金额
		out.setOutReallyAmount(info.getReallyAmount());
		// 查账状态为查账
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		
		outList = paybackTransferOutDao.getAutoNoAuditedList(out);
		if(ArrayHelper.isNotEmpty(outList)){
			return true;
		}
		return false;
	}
	
	/**
	 * （存入日期，存入银行，存入金额）匹配三项，备注包含存款人匹配成功
	 * @param info
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private String urgeMatchingRemarkSuccess(PaybackTransferInfo info) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut out = new PaybackTransferOut();
		// 存入日期
		out.setOutDepositTime(info.getTranDepositTime());
		// 存入银行
		out.setOutEnterBankAccount(info.getStoresInAccount());
		// 存入金额
		out.setOutReallyAmount(info.getReallyAmount());
		// 备注模糊存入人前3个字
		if(StringUtils.isNotEmpty(info.getDepositName())){
			out.setOutRemark(info.getDepositName().substring(0, 3));
		}
		// 查账状态为查账
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		
		outList = paybackTransferOutDao.getAutoNoAuditedList(out);
		if(ArrayHelper.isNotEmpty(outList)){
			// 匹配成功返回outId，多条返回第一条outId
			return outList.get(0).getId();
		}
		return null;
	}
	
	/**
	 * 验证实际存款人长度
	 * 2016年06月02日
	 * By zhangfeng
	 * @param depositName 
	 * @return boolean
	 */
	private boolean validationDepositNameLength(String depositName) {
		if (StringUtils.isNotEmpty(depositName)) {
			if (depositName.length() > 2) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 匹配成功更新信息
	 * @param info
	 * @param matchingOutId 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateMatching(PaybackTransferInfo info, String outId) {
		
		// 匹配成功更新汇款表
		info.setAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		info.setRelationType(TargetWay.SERVICE_FEE.getCode());
		info.preUpdate();
		dao.updateInfoStatus(info);
		
		// 银行导入流水存入匹配成功的申请ID,不修改查账状态,多条统一修改
		PaybackTransferOut out = new PaybackTransferOut();
		out.setId(outId);
		out.setTransferAccountsId(info.getId());
		out.setrPaybackApplyId(info.getrPaybackApplyId());
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		out.setRelationType(TargetWay.SERVICE_FEE.getCode());
		out.preUpdate();
		paybackTransferOutDao.updateOutStatuById(out);
	}

	/**
	 * （存入日期，存入银行，存入金额，存入人）匹配成功 by zhangfeng
	 * @param info
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private String urgeMatchingSuccess(PaybackTransferInfo info) {
		
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut out = new PaybackTransferOut();
		// 存入日期
		out.setOutDepositTime(info.getTranDepositTime());
		// 存入银行
		out.setOutEnterBankAccount(info.getStoresInAccount());
		// 存入金额
		out.setOutReallyAmount(info.getReallyAmount());
		// 存入人
		out.setOutDepositName(info.getDepositName());
		// 查账状态为未查账
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		
		outList = paybackTransferOutDao.getAutoNoAuditedList(out);
		if(ArrayHelper.isNotEmpty(outList)){
			// 匹配成功返回outId，多条返回第一条outId
			return outList.get(0).getId();
		}
		return null;
	}

	/**
	 * 验证实际存款人（存现、现金、转帐、转款、支付宝、无）
	 * 2016年06月02日
	 * By zhangfeng
	 * @param depositName 
	 * @return boolean
	 */
	private boolean validationDepositName(String depositName) {
		if (StringUtils.isNotEmpty(depositName)) {
			for (int i = 0; i < PaybackConstants.VALIDATION_DEPOSITNAME.length; i++) {
				if (depositName.contains(PaybackConstants.VALIDATION_DEPOSITNAME[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 插入蓝补交易历史
	 * 2016年4月26日
	 * By 朱静越
	 * @param payback
	 * @param paybackBuleAmount
	 * @param applyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void savePaybackBlueAmount(String contractCode, BigDecimal blueAmount, BigDecimal applyAmount) {
		PaybackBuleAmont pba = new PaybackBuleAmont();
		pba.setContractCode(contractCode);
		pba.setTradeType(TradeType.TRANSFERRED.getCode());
		pba.setTradeAmount(applyAmount);
		pba.setSurplusBuleAmount(blueAmount.add(applyAmount));
		pba.setDictDealUse(AgainstContent.URGE_CHARGE.getCode());
		paybackBlueAmountDao.insertPaybackBuleAmount(pba);
	}
	
	/**
	 * 更新还款主表的蓝补金额
	 * 2016年4月26日
	 * By 朱静越
	 * @param payback
	 * @param bg
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePayback(String contractCode, BigDecimal blueAmount, BigDecimal bg) {
		Payback p = new Payback();
		p.setPaybackBuleAmount(blueAmount.add(bg));
		p.preUpdate();
		paybackDao.updatePayback(p);
	}

	/**
	 * 更新催收主表的状态和金额
	 * 2016年4月26日
	 * By 朱静越
	 * @param urgeMoney
	 * @param urgeSerMoney
	 * @param applyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateUrgeServicesMoney(String urgeId, BigDecimal applyAmount) {
		UrgeServicesMoney um = new UrgeServicesMoney();
		um.setId("'" + urgeId + "'");
		um.setAuditAmount(applyAmount);
		um.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
		um.preUpdate();
		urgeServicesMoneyDao.updateUrge(um);
	}
	
	/**
	 * 更新导入银行流水表的查账状态
	 * 2016年4月26日
	 * By 朱静越
	 * @param paybackTransferInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateTransferOutStatus(String applyId, String contractCode) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut puts = new PaybackTransferOut();
		puts.setrPaybackApplyId(applyId);
		puts.setContractCode(contractCode);
		puts.setOutTimeCheckAccount(new Date());
		puts.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		puts.setRelationType(TargetWay.SERVICE_FEE.getCode());
		paybackTransferOutDao.updateOutStatuByApplyId(puts);
		
		// 查账流水历史
		outList = paybackTransferOutDao.findList(puts);
		for(PaybackTransferOut out:outList){
			// 记录匹配成功流水的历史
			PaybackOpeEx paybackOpes = null;
			paybackOpes = new PaybackOpeEx(out.getId(), null,
					RepaymentProcess.MATCHING, TargetWay.SERVICE_FEE,
					PaybackOperate.MATCH_SUCCEED.getCode(), "批量匹配，合同编号:"
							+ contractCode + "匹配成功！");
			historyService.insertPaybackOpe(paybackOpes);
		}
	}

	/**
	 * 保存匹配审核结果 
	 * 2016年4月25日 By 张永生
	 * @param urgeServicesMoneyEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String saveMatchAuditResult(UrgeServicesMoneyEx urgeServicesMoneyEx) {
		UrgeServicesMoney urgeSerMoney = new UrgeServicesMoney();
		UrgeServicesMoney urgeMoney = new UrgeServicesMoney();
		String msg = "";
		UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
		// 使用加锁控制更新申请表
		UrgeServicesCheckApply urgeApplyUp = new UrgeServicesCheckApply();
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
		map.put("reqStatus", UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeApplyUp = dao.getApplyUrgeReq(map);
		if (ObjectHelper.isNotEmpty(urgeApplyUp)) {
			Payback payback = new Payback();
			PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
			payback.setContractCode(urgeServicesMoneyEx.getContractCode());
			if (StringUtils.equals(urgeServicesMoneyEx.getDictPayResult(),OperateMatching.SUCCESS_MATCHING.getCode())) {
				// 更新催收服务费查账申请表的实际到账金额,申请状态更新为查账成功,退回原因更新为空
				urgeApply.setId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
				urgeApply.setUrgeReallyAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeReallyAmount());
				urgeApply.setUrgeBackReason("");
				urgeApply.setReqTime(urgeApplyUp.getReqTime());
				urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
				urgeApply.preUpdate();
				int sum = dao.updateUrgeApply(urgeApply);
				if (sum > 0) {
					// 修改导入流水表状态
					PaybackTransferOut out = new PaybackTransferOut();
					out.setrPaybackApplyId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
					out.setContractCode(urgeServicesMoneyEx.getContractCode());
					out.setOutTimeCheckAccount(new Date());
					out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
					paybackTransferOutDao.updateOutStatuByApplyId(out);
					
					urgeMoney = urgeServicesMoneyDao.find(urgeServicesMoneyEx.getUrgeId());
					
					// 修改催收服务费主表
					urgeSerMoney.setId("'" + urgeServicesMoneyEx.getUrgeId() + "'");
					BigDecimal auditAmount = urgeMoney.getAuditAmount();
					BigDecimal urgeAmount = urgeMoney.getUrgeMoeny();
					auditAmount = auditAmount == null ? BigDecimal.ZERO : auditAmount;
					urgeAmount = urgeAmount == null ? BigDecimal.ZERO : urgeAmount;
					urgeSerMoney.setAuditAmount(urgeServicesMoneyEx
							.getUrgeServicesCheckApply().getUrgeReallyAmount()
							.add(auditAmount));
					urgeSerMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
					urgeServicesMoneyDao.updateUrge(urgeSerMoney);
					// 修改蓝补 ：蓝补金额 = 已划扣金额 + 查账金额 - 催收金额
					BigDecimal bg = urgeMoney.getUrgeDecuteMoeny().add(urgeSerMoney.getAuditAmount())
							.subtract(urgeAmount);
					String checkResult = urgeServicesMoneyDao.selGrant(
							urgeMoney.getrGrantId()).getCheckResult();
					// 如果金额大于0，同时放款审核结果为审核通过之后，多余金额才能进入蓝补
					if (bg.compareTo(BigDecimal.ZERO) > 0
							&& VerityStatus.PASS.getCode().equals(checkResult)) {
						payback.setContractCode(urgeServicesMoneyEx.getContractCode());
						payback.setPaybackBuleAmount(urgeServicesMoneyEx.getPayback()
								.getPaybackBuleAmount().add(bg));
						payback.preUpdate();
						paybackDao.updatePayback(payback);
						// 蓝补历史
						paybackBuleAmount.setTradeType(TradeType.TRANSFERRED
								.getCode());
						paybackBuleAmount.setTradeAmount(bg);
						paybackBuleAmount.setSurplusBuleAmount(urgeServicesMoneyEx
								.getPayback().getPaybackBuleAmount().add(bg));
						paybackBuleAmount.setIsNewRecord(false);
						paybackBuleAmount.preInsert();
						paybackBlueAmountDao.insertPaybackBuleAmount(paybackBuleAmount);
						payback.preUpdate();
						paybackDao.updatePayback(payback);
					}
					
					// 操作历史
					PaybackOpeEx paybackOpes = new PaybackOpeEx(urgeApply.getId(),
							urgeServicesMoneyEx.getUrgeId(), RepaymentProcess.MATCHING,
							TargetWay.SERVICE_FEE,
							PaybackOperate.MATCH_SUCCEED.getCode(), "手动匹配，合同编号:"
									+ urgeApply.getContractCode());
					historyService.insertPaybackOpe(paybackOpes);
					urgeSerMoney.preUpdate();
					urgeServicesMoneyDao.updateUrge(urgeSerMoney);
					msg = "保存成功！";
				}else {
					msg = "数据已经被处理，请刷新页面！";
				}
			} else {
				// 更新催收服务费查账状态为【查账退回】
				urgeApply.setId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
				urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				urgeApply.setUrgeReallyAmount(new BigDecimal(0.00));
				urgeApply.setReqTime(urgeApplyUp.getReqTime());
				urgeApply.preUpdate();
				int sum = dao.updateUrgeApply(urgeApply);
				if (sum > 0) {
					// 查询是否存在手动匹配的汇款 更新info由【已查账】到【未查账】
					List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
					PaybackTransferInfo info = new PaybackTransferInfo();
					info.setrPaybackApplyId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
					info.setAuditStatus("'" + BankSerialCheck.CHECKE_OVER.getCode() + "'");
					infoList = dealPaybackDao.findTransfer(info);
					if(ArrayHelper.isNotEmpty(infoList)){
						for(PaybackTransferInfo pi:infoList){
							pi.setAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
							dealPaybackDao.updateInfoStatus(pi);
						}
					}
					
					// 查询是否存在手动匹配的还款流水，根据申请id查询out表，将状态改为【未查账】，同时将表关联的infoId和申请id更新为空，释放该流水
					List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
					PaybackTransferOut out = new PaybackTransferOut();
					out.setrPaybackApplyId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
					outList = paybackTransferOutDao.findList(out);
					if(ArrayHelper.isNotEmpty(outList)){
						for(PaybackTransferOut po:outList){
							po.setrPaybackApplyId(null);
							po.setTransferAccountsId(null);
							po.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
							paybackTransferOutDao.updateOutStatuById(po);
						}
					}
					
					// 更新催收主表
					urgeSerMoney.setId("'" + urgeServicesMoneyEx.getUrgeId() + "'");
					urgeSerMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
					
					// 退回操作历史
					PaybackOpeEx paybackOpes = new PaybackOpeEx(urgeApply.getId(), urgeServicesMoneyEx.getUrgeId(),
							RepaymentProcess.RETURN, TargetWay.SERVICE_FEE, PaybackOperate.RETURN_SUCCESS.getCode(), "手动退回，退回原因:"
									+ urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeBackReason());
					historyService.insertPaybackOpe(paybackOpes);
					urgeSerMoney.preUpdate();
					urgeServicesMoneyDao.updateUrge(urgeSerMoney);
					msg = "退回成功！";
				}else {
					msg = "数据已经被处理，请刷新页面！";
				}
			}
		}else {
			msg = "数据已经被处理，请刷新页面！";
		}
		return msg;
	}

	/**
	 * 单笔处理提交匹配，更新info表；首先先对info表进行查询，手动匹配的时候使用
	 * 2016年4月26日
	 * By 朱静越
	 * @param applyId
	 * @param infoId
	 * @param outId
	 * @param contractCode
	 * @param outReallyAmount
	 * @param applyReallyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean saveSingleAutoMatch(String applyId, String infoId,
			String outId, String contractCode, String outReallyAmount,
			String applyReallyAmount) {
		// 查询出需要进行更新的info的数据
		Map<String,String>  map = new HashMap<String, String>();
		boolean flag = true;
		map.put("reqId", infoId);
		map.put("reqStatus",BankSerialCheck.CHECKE_SUCCEED.getCode());
		PaybackTransferInfo pi = dealPaybackDao.getstransferInfoReq(map);
		if (ObjectHelper.isNotEmpty(pi)) {
			// 更新info汇款单的查账状态 ，使用锁控制更新
			PaybackTransferInfo info = new PaybackTransferInfo();
			info.setId(infoId);
			info.setReqTime(pi.getReqTime());
			info.setAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
			info.preUpdate();
			int sum = dao.updateInfoStatus(info);
			if (sum > 0) {
				PaybackTransferOut out = new PaybackTransferOut();
				out.setId(outId);
				out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
				out.setrPaybackApplyId(applyId);
				out.setTransferAccountsId(infoId);
				out.preUpdate();
				paybackTransferOutDao.updateOutStatuById(out);
				
				// 更新催收服务费查账申请表的实际到账金额
				UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
				urgeApply.setId(applyId);
				urgeApply.setUrgeReallyAmount(BigDecimalTools.add(new BigDecimal(
						outReallyAmount), new BigDecimal(applyReallyAmount)));
				urgeApply.preUpdate();
				updateUrgeApply(urgeApply);
			}
		}else {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 批量退回,
	 * 2016年4月26日
	 * By 朱静越
	 * @param id 申请id
	 * @param contractCode 合同编号
	 * @param urgeId 催收id
	 * @return 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean urgeMatchingBack(String id, String contractCode, String urgeId) {
			// 退回标识  false退回数据
			Boolean isExistFlag = this.isUrgeExist(id);
			// 批量退回,匹配加锁
			UrgeServicesCheckApply urgeApplyUp = new UrgeServicesCheckApply();
			Map<String,String>  map = new HashMap<String, String>();
			map.put("reqId", id);
			map.put("reqStatus", UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
			urgeApplyUp = dao.getApplyUrgeReq(map);
			if (ObjectHelper.isNotEmpty(urgeApplyUp)) {
				if (!isExistFlag) {
					UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
					UrgeServicesMoney urgeMoney = new UrgeServicesMoney();
					
					// 修改申请表
					urgeApply.setId(id);
					urgeApply.setContractCode(contractCode);
					urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
					urgeApply.setUrgeReallyAmount(new BigDecimal(0.00));
					urgeApply.setReqTime(urgeApplyUp.getReqTime());
					urgeApply.preUpdate();
					int sum = dao.updateUrgeApply(urgeApply);
					
					if (sum > 0) {
						// 修改催收主表
						urgeMoney.setId("'"+urgeId+"'");
						urgeMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
						urgeMoney.preUpdate();
						urgeServiceDao.updateUrge(urgeMoney);
						
						// 退回操作历史
						PaybackOpeEx paybackOpes = new PaybackOpeEx(urgeApply.getId(), urgeId,
								RepaymentProcess.RETURN, TargetWay.SERVICE_FEE, PaybackOperate.RETURN_SUCCESS.getCode(), "批量匹配，退回原因：款项尚未到账，或者存入日期/存入账户/存入金额有误，请核实！");
						historyService.insertPaybackOpe(paybackOpes);
					}
				}
			}
			return isExistFlag;
	}

	/**
	 * 查询所有未查账的催收服务费 by zhangfeng
	 * @param urgeApply
	 * @return list
	 */
	public List<UrgeServicesCheckApply> findUrgeApplyList(
			UrgeServicesCheckApply urgeApply) {
		return dao.findUrgeApplyList(urgeApply);
	}

	/**
	 * 发起催收服务费申请 by zhangfeng
	 * @param files
	 * @param urgeServicesMoneyEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveFirstApply(MultipartFile[] files, UrgeServicesMoneyEx urgeServicesMoneyEx) {
		
		// 判断，如果是处理中导出的数据提交查账，需要将拆分表中的该单子的处理状态为处理中导出的删除，根据催收主表id
		UrgeServicesMoney urge = urgeServiceDao.find(urgeServicesMoneyEx.getUrgeId());
		if (UrgeCounterofferResult.PROCESSED.getCode().equals(urge.getDictDealStatus())) {
			UrgeServicesMoneyEx urgeServices = new UrgeServicesMoneyEx();
			urgeServices.setUrgeId("'" + urgeServicesMoneyEx.getUrgeId() + "'");
			urgeServices.setSplitResult(UrgeCounterofferResult.PROCESSED.getCode());
			List<UrgeServicesMoneyEx> delList = urgeServiceDao.selProcess(urgeServices);
			if (delList.size()>0) {
				for (int i = 0; i < delList.size(); i++) {
					urgeServiceDao.delProcess("'"+delList.get(i).getId()+"'");
				}
			}			
		}
		
		// 保存催收服务费申请表
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeApplyStatus(
				UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeMethod(
				RepayChannel.NETBANK_CHARGE.getCode());
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeReallyAmount(BigDecimal.ZERO);
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setIsNewRecord(false);
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setDictDepositAccount(
				urgeServicesMoneyEx.getPaybackTransferInfo().getStoresInAccount());
		urgeServicesMoneyEx.getUrgeServicesCheckApply().preInsert();
		dao.saveUrgeApply(urgeServicesMoneyEx.getUrgeServicesCheckApply());
		
		// 保存催收服务费汇款数据
		this.savePayBackTransferInfo(urgeServicesMoneyEx, files);
		
		// 更新催收服务费主表
		saveUrgeServiceAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getrServiceChargeId());
		
		// 催收服务费操作历史（申请）
		PaybackOpeEx paybackOpes = new PaybackOpeEx(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId(),
				urgeServicesMoneyEx.getUrgeId(),
				RepaymentProcess.SEND_CHECK, TargetWay.SERVICE_FEE,
				PaybackOperate.APPLY_SUCEED.getCode(), "催收服务费发起查账申请，申请金额："
						+ urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeApplyAmount());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 待办发起催收服务费申请 by zhangfeng
	 * @param files
	 * @param urgeServicesMoneyEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateFirstApply(MultipartFile[] files,
			UrgeServicesMoneyEx urgeServicesMoneyEx) {

		UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
		urgeApply.setrServiceChargeId(urgeServicesMoneyEx.getUrgeId());
		urgeApply.setUrgeApplyAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeApplyAmount());
		urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeApply.setDictDepositAccount(urgeServicesMoneyEx.getPaybackTransferInfo().getStoresInAccount());
		urgeApply.preUpdate();
		dao.updateUrgeApply(urgeApply);

		// 删除旧文件和数据
		this.deleteTransferInfo(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
		
		// 新增汇款数据
		this.savePayBackTransferInfo(urgeServicesMoneyEx, files);
		
		// 更新催收服务费主表
		this.saveUrgeServiceAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getrServiceChargeId());
		
		// 催收服务费申请操作历史（重新发起）
		PaybackOpeEx paybackOpes = new PaybackOpeEx(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId(),
				urgeServicesMoneyEx.getUrgeId(),
				RepaymentProcess.SEND_CHECK, TargetWay.SERVICE_FEE,
				PaybackOperate.APPLY_SUCEED.getCode(), "催收服务费重新发起查账申请，申请金额："
						+ urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeApplyAmount());
		historyService.insertPaybackOpe(paybackOpes);
	}
	
	/**
	 * 更新催收服务费主表 by zhangfeng
	 * @param applyId
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveUrgeServiceAmount(String applyId) {
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		urgeServicesMoney.setId("'" + applyId + "'");
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeServicesMoney.preUpdate();
		urgeServiceDao.updateUrge(urgeServicesMoney);
	}
	
	/**
	 * 删除汇款表数据 by zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void deleteTransferInfo(String applyId) {
		
		// 删除旧文件
		PaybackTransferInfo info = new PaybackTransferInfo();
		info.setrPaybackApplyId(applyId);
		List<PaybackTransferInfo> infoList = dealPaybackService.findTransfer(info);
		if (ArrayHelper.isNotEmpty(infoList)) {
			for (PaybackTransferInfo in : infoList) {
				CeUtils.deleteFile(in.getUploadPath());
			}
		}
		
		// 删除汇款单
		dao.deletePaybackTransferInfo(info);
	}
	
	/**
	 * 退回验证流水是否存在
	 * @param id 申请表id
	 * @return boolean
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	private Boolean isUrgeExist(String id) {
		int isExistCount = 0;
		List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferInfo info = new PaybackTransferInfo();
		info.setrPaybackApplyId(id);
		// 查询没有进行匹配或者匹配失败的汇款
		info.setAuditStatus("'" + BankSerialCheck.CHECKE_SUCCEED.getCode()+ "','" + BankSerialCheck.CHECKE_FAILED.getCode() + "','"
				+ BankSerialCheck.OFFLINE_CHECK.getCode() + "'");
		info.setRelationType(TargetWay.SERVICE_FEE.getCode());
		infoList = dealPaybackDao.findTransfer(info); // 1条
		if (ArrayHelper.isNotEmpty(infoList)) {
			for (PaybackTransferInfo pInfo : infoList) {
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				PaybackTransferOut out = new PaybackTransferOut();
				out.setOutDepositTime(pInfo.getTranDepositTime());
				out.setOutEnterBankAccount(pInfo.getStoresInAccount());
				out.setOutReallyAmount(pInfo.getReallyAmount());
				out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
				outList = paybackTransferOutDao.findAuditedList(out);  // 没有流水
				if (ArrayHelper.isNotEmpty(outList)) {
					isExistCount++;
				}else{
					break;  // 该汇款没有流水，直接更新申请
				}
			}
			
			// 汇款单流水不存在
			if(infoList.size() != isExistCount){
				// 查询是否存在手动匹配的还款流水
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				PaybackTransferOut out = new PaybackTransferOut();
				out.setrPaybackApplyId(id);
				outList = paybackTransferOutDao.findList(out);
				if(ArrayHelper.isNotEmpty(outList)){ // 将已经存在过的，匹配过的流水修改为未匹配的流水，释放流水
					for(PaybackTransferOut po:outList){
						po.setrPaybackApplyId(null);
						po.setTransferAccountsId(null);
						po.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
						paybackTransferOutDao.updateOutStatuById(po);
					}
				}
			}else{
				// 汇款单和流水一样的，不需要进行修改
				return true;
			}
		}else{
			// 没有汇款单的不需要进行修改
			return true;
		}
		
		return false;
	}
}
