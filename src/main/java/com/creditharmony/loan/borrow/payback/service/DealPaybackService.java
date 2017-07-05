package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.DealPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.PaybackTransferOutDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.constants.PaybackConstants;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.SystemSetMaterService;

/**
 * 待还款匹配列表业务处理service
 * @Class Name DealPayBackService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("DealPayBackService")

public class DealPaybackService extends CoreManager<DealPaybackDao, PaybackApply>{
	

	@Autowired
	private SystemSetMaterService sys;
	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	@Autowired
	private DeductUpdateService deductUpdateService;
	@Autowired 
	private PaybackBlueAmountService blusAmountService;
	@Autowired
	private HistoryService  historyService;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired 
	private PaybackTransferOutDao paybackTransferOutDao;
	@Autowired
	private DealPaybackDao dealPaybackDao;
 	
	/**
	 * 查询查账账款列表
	 * 2015年12月18日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackTransferInfo> findTransfer(PaybackTransferInfo paybackTransferInfo) {
		return dao.findTransfer(paybackTransferInfo);
	}

	/**
	 * 匹配规则
	 * 2015年12月25日
	 * By zhangfeng
	 * @param contractCode
	 * @param applyId
	 * @param paybackId 
	 * @param modifyTime 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean matchingRule(String contractCode, String applyId, String paybackId) throws Exception{
		// 匹配加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", applyId);
		map.put("reqStatus", RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
		PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
		if (ObjectHelper.isNotEmpty(pApply)) {
			List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
			PaybackTransferInfo pi = new PaybackTransferInfo();
			
			int sum = 0; // 汇款单匹配次数
			BigDecimal auditAmount = BigDecimal.ZERO; // 查账金额
			
			// 查询当前申请表汇款数据（匹配失败，未匹配，未完全匹配）
			pi.setrPaybackApplyId(applyId);
			pi.setAuditStatus("'" + BankSerialCheck.CHECKE_SUCCEED.getCode()
					+ "','" + BankSerialCheck.CHECKE_FAILED.getCode() + "','"
					+ BankSerialCheck.OFFLINE_CHECK.getCode() + "'");
			pi.setRelationType(TargetWay.REPAYMENT.getCode());
			infoList = dao.findTransfer(pi);
			if(ArrayHelper.isNotEmpty(infoList)){
				for(PaybackTransferInfo info: infoList){
					
					// 实际存款人如果是（存现、现金、转帐、转款、支付宝、无 ），则不参加匹配
					if (!validationDepositName(info.getDepositName())) {
						// 查询流水（存入日期，存入银行，存入金额，存入人）匹配成功返回流水ID
						String matchingOutId = matchingSuccess(info);
						if(StringUtils.isNotEmpty(matchingOutId)){
							updateMatching(info, matchingOutId);
							auditAmount = auditAmount.add(info.getReallyAmount());
							sum++;
							continue;
						}
						// 存款人长度大于2才参加模糊匹配
						if (validationDepositNameLength(info.getDepositName())) {
							// 查询流水（存入日期，存入银行，存入金额，备注包含存款人）匹配成功返回流水ID
							String matchingRemarkOutId = matchingRemarkSuccess(info);
							if(StringUtils.isNotEmpty(matchingRemarkOutId)){
								updateMatching(info, matchingRemarkOutId);
								auditAmount = auditAmount.add(info.getReallyAmount());
								sum++;
								continue;
							}
						}
					}
				}
				
				// 汇款条数和汇款条数相等，匹配成功
				if(StringUtils.equals(String.valueOf(infoList.size()), String.valueOf(sum))){
					updateMatchingTaskSuccess(contractCode, applyId, paybackId, auditAmount, pApply.getApplyReallyAmount(), pApply.getReqTime());
					return true;
				}else{
					// 成功查账一条更新实际到账金额到申请表
					if(auditAmount.compareTo(BigDecimal.ZERO) == 1){
						updateSingMatchingTaskSuccess(applyId, auditAmount, pApply.getApplyReallyAmount(), pApply.getReqTime());
					}
				}
			}
		}
		return false;
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
	 * 任务全部匹配成功
	 * @param contractCode
	 * @param applyId
	 * @param paybackId
	 * @param applyReallyAmount 
	 * @param bigDecimal 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateMatchingTaskSuccess(String contractCode, String applyId, String paybackId, BigDecimal auditAmount, BigDecimal applyReallyAmount, String reqTime) {
		// 修改还款申请表数据 已还款,回盘结果为成功，加锁更新
		PaybackApply pa = new PaybackApply();
		pa.setId(applyId);
		pa.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());
		pa.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
		pa.setReqTime(reqTime);
		pa.setApplyReallyAmount(applyReallyAmount.add(auditAmount));
		pa.preUpdate();
		int sum = applyPayService.updatePaybackApplyReq(pa);
		if (sum > 0) {
			//修改导入流水状态
			PaybackTransferOut puts = new PaybackTransferOut();
			puts.setrPaybackApplyId(applyId);
			puts.setContractCode(contractCode);
			puts.setOutTimeCheckAccount(new Date());
			puts.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
			puts.preUpdate();
			paybackTransferOutService.updateOutStatuByApplyId(puts);
			
			// 流水历史
			List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
			outList = paybackTransferOutService.findList(puts);
			for(PaybackTransferOut out:outList){
				// 记录匹配成功流水历史
				PaybackOpeEx paybackOpes = null;
		        paybackOpes = new PaybackOpeEx(out.getId(), null, 
		    			RepaymentProcess.MATCHING, TargetWay.REPAYMENT, PaybackOperate.MATCH_SUCCEED.getCode(),"批量匹配，合同编号:"+ contractCode + "匹配成功！" );
			    historyService.insertPaybackOpe(paybackOpes);
			}

			// 查账成功
			Map<String,Object> paybackParam = new HashMap<String,Object>();
	        paybackParam.put("contractCode", contractCode);
	        Payback p = paybackDao.selectpayBack(paybackParam);
			if(!ObjectHelper.isEmpty(p)){
				BigDecimal blueAmount = p.getPaybackBuleAmount();
				if(blueAmount == null){
					blueAmount = BigDecimal.ZERO;
				}
				// 修改蓝补
				p.setPaybackBuleAmount(blueAmount.add(pa.getApplyReallyAmount()));
				p.preUpdate();
				applyPayService.updatePayback(p);
				
				// 蓝补历史
				PaybackBuleAmont pba = new PaybackBuleAmont();
				pba.setContractCode(contractCode);
				pba.setTradeType(TradeType.TRANSFERRED.getCode());
				pba.setTradeAmount(pa.getApplyReallyAmount());
				pba.setSurplusBuleAmount(p.getPaybackBuleAmount());
				pba.setOperator(UserUtils.getUser().getName());
				pba.setDictDealUse(AgainstContent.PAYBACK_CHARGE.getCode());
				pba.preInsert();
				blusAmountService.insertPaybackBuleAmount(pba);
	
				// 记录匹配成功操作历史
				PaybackOpeEx paybackOpes = null;
		        paybackOpes = new PaybackOpeEx(applyId, paybackId, 
		    			RepaymentProcess.MATCHING, TargetWay.REPAYMENT, PaybackOperate.MATCH_SUCCEED.getCode(),"合同编号:"+ contractCode + "匹配成功！" );
			    historyService.insertPaybackOpe(paybackOpes);
			}
		}else{
			throw new ServiceException("批量匹配异常！");
		}
	}
	
	/**
	 * 任务单条匹配成功
	 * @param contractCode
	 * @param applyId
	 * @param paybackId
	 * @param applyReallyAmount 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateSingMatchingTaskSuccess(String applyId, BigDecimal auditAmount, BigDecimal applyReallyAmount, String reqTime) {
		// 修改还款申请表数据，加锁更新
		PaybackApply pa = new PaybackApply();
		pa.setId(applyId);
		pa.setReqTime(reqTime);
		pa.setApplyReallyAmount(applyReallyAmount.add(auditAmount));
		pa.preUpdate();
		int sum = applyPayService.updatePaybackApplyReq(pa);
		if(sum == 0){
			throw new ServiceException("批量匹配异常！");
		}
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
		dao.updateInfoStatus(info);
		
		// 匹配成功更新流水表（存入匹配成功的汇款ID和申请ID,不修改查账状态,多条统一修改）
		PaybackTransferOut po = new PaybackTransferOut();
		po.setId(outId);
		po.setTransferAccountsId(info.getId());
		po.setrPaybackApplyId(info.getrPaybackApplyId());
		po.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		po.preUpdate();
		paybackTransferOutDao.updateOutStatuById(po);
	}

	/**
	 * 查询流水（存入日期，存入银行，存入金额） 存款人不一样匹配失败
	 * @param info
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private boolean matchingOfflineSuccess(PaybackTransferInfo info) {
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
		
		outList = paybackTransferOutDao.getNoAuditedList(out);
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
	private String matchingRemarkSuccess(PaybackTransferInfo info) {
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
	 * （存入日期，存入银行，存入金额，存入人）匹配成功
	 * @param info
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private String matchingSuccess(PaybackTransferInfo info) {
		
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
	 * 手动匹配保存 2016年5月16日 By zhangfeng
	 * 
	 * @param applyId
	 * @param paybackId
	 * @param infoId
	 * @param outId
	 * @param contractCode
	 * @param blueAmount
	 * @param outReallyAmount
	 * @param applyReallyAmount
	 * @return amount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public BigDecimal saveMatchingSingle(String applyId, String paybackId, String infoId, String outId, 
			String contractCode, String blueAmount, String outReallyAmount, String applyReallyAmount) {
		// 加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", infoId);
		map.put("reqStatus",BankSerialCheck.CHECKE_SUCCEED.getCode());
		PaybackTransferInfo pi = dealPaybackDao.getstransferInfoReq(map);
		if (ObjectHelper.isNotEmpty(pi)) {
			PaybackTransferInfo info = new PaybackTransferInfo();
			info.setId(infoId);
			info.setReqTime(pi.getReqTime());
			info.setAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
			info.preUpdate();
			int sum = dao.updateInfoStatusReq(info);
			if(sum > 0){
				PaybackTransferOut out = new PaybackTransferOut();
				out.setId(outId);
				out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
				out.setrPaybackApplyId(applyId);
				out.setTransferAccountsId(infoId);
				out.preUpdate();
				paybackTransferOutService.updateOutStatuById(out);
				
				PaybackApply pa = new PaybackApply();
				pa.setId(applyId);
				pa.setApplyReallyAmount(new BigDecimal(applyReallyAmount).add(new BigDecimal(outReallyAmount)));
				pa.preUpdate();
				applyPayService.updatePaybackApply(pa);
			}
		}else{
			return BigDecimal.ZERO;
		}
		return new BigDecimal(applyReallyAmount).add(new BigDecimal(outReallyAmount));
	}
	
	/**
	 * 修改汇款信息状态
	 * 2016年5月16日
	 * By zhangfeng
	 * @param paybackTransferInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateInfoStatus(PaybackTransferInfo paybackTransferInfo) {
		dao.updateInfoStatus(paybackTransferInfo);
	}

	/**
	 * 匹配成功手动保存 by zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String saveMatching(PaybackApply pa) {
		String msg = "";
		// 加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", pa.getId());
		map.put("reqStatus", RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
		PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
		if (ObjectHelper.isNotEmpty(pApply)) {
			// 匹配成功修改还款申请表，加锁更新申请表
			pa.setReqTime(pApply.getReqTime());
			pa.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());
			pa.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());// 更新回盘结果为退回
			pa.preUpdate();
			int sum = applyPayService.updatePaybackApplyReq(pa);
			if(sum > 0){
				//修改导入流水表状态
				PaybackTransferOut out = new PaybackTransferOut();
				out.setrPaybackApplyId(pa.getId());
				out.setContractCode(pa.getContractCode());
				out.setOutTimeCheckAccount(new Date());
				out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
				out.preUpdate();
				paybackTransferOutService.updateOutStatuByApplyId(out);
				
				// 流水历史
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				outList = paybackTransferOutService.findList(out);
				for(PaybackTransferOut po:outList){
					// 记录匹配成功流水历史
					PaybackOpeEx paybackOpes = null;
			        paybackOpes = new PaybackOpeEx(po.getId(), null, 
			    			RepaymentProcess.MATCHING, TargetWay.REPAYMENT, PaybackOperate.MATCH_SUCCEED.getCode(),"手动匹配，合同编号:"+ po.getContractCode() + "匹配成功！" );
				    historyService.insertPaybackOpe(paybackOpes);
				}
				
				// 修改蓝补
				Payback payback = new Payback();
				//查询当前蓝补
				Map<String,Object> paraMap = new HashMap<String,Object>();
				paraMap.put("contractCode", pa.getContractCode());
				Payback paybacks = applyPayService.findPaybackByContract(paraMap);
				payback.setContractCode(pa.getContractCode());
				payback.setPaybackBuleAmount(pa.getApplyReallyAmount().add(paybacks.getPaybackBuleAmount()));
				//payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
				payback.preUpdate();
				applyPayService.updatePayback(payback);
				
				// 蓝补历史
				PaybackBuleAmont blueAmount = new PaybackBuleAmont();
				blueAmount.setContractCode(pa.getContractCode());
				blueAmount.setTradeType(TradeType.TRANSFERRED.getCode());
				blueAmount.setTradeAmount(pa.getApplyReallyAmount());
				blueAmount.setSurplusBuleAmount(paybacks.getPaybackBuleAmount().add(pa.getApplyReallyAmount()));
				blueAmount.setDictDealUse("手动匹配成功");
				blueAmount.setIsNewRecord(false);
				blueAmount.preInsert();
				blusAmountService.insertPaybackBuleAmount(blueAmount);
				
				// 操作历史
				PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(),
						pa.getPayback().getId(),
						RepaymentProcess.MATCHING, TargetWay.REPAYMENT,
						PaybackOperate.MATCH_SUCCEED.getCode(), "保存匹配结果，合同编号：" + pa.getContractCode());
				historyService.insertPaybackOpe(paybackOpes);
				msg = "保存成功！";
			}else{
				msg = "数据已经被处理，请刷新页面！";
			}
		}else{
			msg = "数据已经被处理，请刷新页面！";
		}
		return msg;
	}

	/**
	 * 匹配失败手动保存 by zhangfeng
	 * @param pa
	 * @return none 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String returnMatching(PaybackApply pa) {
		String msg = "";
		// 加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", pa.getId());
		map.put("reqStatus", RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
		PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
		if (ObjectHelper.isNotEmpty(pApply)) {
			//修改申请表为还款退回
			pa.setReqTime(pApply.getReqTime());
			pa.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_RETURN.getCode());
			pa.setApplyReallyAmount(BigDecimal.ZERO);
			pa.setSplitBackResult(CounterofferResult.RETURN.getCode());// 更新回盘结果为退回
			//将退回原因赋值
			if(pa.getFailReason()!=null && !pa.getFailReason().equals("") && !pa.getFailReason().equals("其它"))
				pa.setApplyBackMes(pa.getFailReason());
			pa.preUpdate();
			int sum = applyPayService.updatePaybackApplyReq(pa);
			if(sum > 0){
				// 查询是否存在手动匹配的汇款
				List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
				PaybackTransferInfo info = new PaybackTransferInfo();
				info.setrPaybackApplyId(pa.getId());
				info.setAuditStatus("'" + BankSerialCheck.CHECKE_OVER.getCode() + "'");
				infoList = dealPaybackDao.findTransfer(info);
				if(ArrayHelper.isNotEmpty(infoList)){
					for(PaybackTransferInfo pi:infoList){
						pi.setAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
						dealPaybackDao.updateInfoStatus(pi);
					}
				}
				
				// 查询是否存在手动匹配的还款流水
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				PaybackTransferOut out = new PaybackTransferOut();
				out.setrPaybackApplyId(pa.getId());
				outList = paybackTransferOutDao.findList(out);
				if(ArrayHelper.isNotEmpty(outList)){
					for(PaybackTransferOut po:outList){
						po.setrPaybackApplyId(null);
						po.setTransferAccountsId(null);
						po.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
						po.setContractCode(null);
						po.setOutTimeCheckAccount(null);
						paybackTransferOutDao.updateOutStatuById(po);
					}
				}
				String returnReason = pa.getApplyBackMes();
				if(returnReason==null || "".equals(returnReason)){
					returnReason = pa.getFailReason();
				}
				// 操作历史
				PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(),
						pa.getPayback().getId(),
						RepaymentProcess.RETURN, TargetWay.REPAYMENT,
						PaybackOperate.RETURN_SUCCESS.getCode(),"手动匹配退回，"  + "退回原因：" + returnReason);

				historyService.insertPaybackOpe(paybackOpes);

				msg = "退回成功！";
			}else{
				msg = "数据已经被处理，请刷新页面！";
			}
		}else{
			msg = "数据已经被处理，请刷新页面！";
		}
		return msg;
	}
}
