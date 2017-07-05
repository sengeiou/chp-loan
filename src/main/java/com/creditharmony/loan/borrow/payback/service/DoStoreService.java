package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.SystemFromFlag;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.loan.borrow.payback.dao.DoStoreDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanDeductService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.utils.CeUtils;

/**
 * 门店待办业务处理Service
 * @Class Name DoStoreService
 * @author zhangfeng
 * @Create In 2016年1月6日
 */
@Service("doStoreService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class DoStoreService extends CoreManager<DoStoreDao, PaybackApply> {
	
	@Autowired
	private DoAdvanceSettledService doAdvanceSettledService;
	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private DealPaybackService dealPaybackService;
	@Autowired
	private LoanDeductService loanDeductService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	
	/**
	 *  还款放弃（汇款和划扣）
	 * 2016年5月16日
	 * By zhangfeng
	 * @param pa 
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String giveUpPayback(PaybackApply pa) {
		String msg = null;
		// 加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", pa.getId());
		map.put("reqStatus", RepayApplyStatus.REPAYMENT_RETURN.getCode());
		PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
		if (ObjectHelper.isNotEmpty(pApply)) {
			// 更新还款申请表状态 还款放弃
			pa.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_GIVEUP.getCode());
			pa.setSplitBackResult(CounterofferResult.STORE_GIVEUP.getCode() );
			pa.setModifyTime(new Date());
			pa.preUpdate();
			applyPayService.updatePaybackApply(pa);
			
			// 放弃操作历史
			PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(), pa.getPayback().getId(),
					RepaymentProcess.GIVE_UP, TargetWay.REPAYMENT, PaybackOperate.GIVE_UP_SUCCESS.getCode(), "还款放弃，合同编号:"
							+ pa.getContractCode());
			historyService.insertPaybackOpe(paybackOpes);
			msg = "客户放弃！";
		}else{
			msg = "数据已经被处理，请刷新页面！";
		}
		return msg;
	}
	
	/**
	 * 汇款转账
	 * 2016年5月16日
	 * By zhangfeng
	 * @param pa
	 * @param files 
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String transferPayback(PaybackApply pa, MultipartFile[] files) {
		String msg = null;
		// 加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", pa.getId());
		map.put("reqStatus", RepayApplyStatus.REPAYMENT_RETURN.getCode());
		PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
		if (ObjectHelper.isNotEmpty(pApply)) {
			pa.setReqTime(pApply.getReqTime());
			pa.setDictPaybackStatus(RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
			LoanBank loanBank = new LoanBank();
			pa.setLoanBank(loanBank);
			pa.setDictDealType(null);
			pa.setDictRepayMethod(RepayChannel.NETBANK_CHARGE.getCode());
			pa.setApplyAmount(pa.getTransferAmount());
			pa.setDictPaybackStatus(RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
			pa.setDictDepositAccount(pa.getPaybackTransferInfo().getStoresInAccount());
			pa.setSplitBackResult(null);
			// 更新还款申请表
			pa.setApplyBackMes(null);
			pa.preUpdate();
			int sum = dao.updateApplyPayback(pa);
			if(sum > 0){
				// 删除旧文件和数据
				this.deleteTransferInfo(pa);
				// 上传新文件和数据
				this.updateTransferInfo(pa,files);

				// 操作历史
				PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(),
						pa.getPayback().getId(),
						RepaymentProcess.REPAYMENT_APPLY, TargetWay.REPAYMENT,
						PaybackOperate.APPLY_SUCEED.getCode(), "重新发起还款，申请金额："
								+ pa.getApplyAmount());
				historyService.insertPaybackOpe(paybackOpes);
				msg = "重新发起还款成功！";
			}else{
				msg = "数据已经被处理，请刷新页面！";
			}
		}else{
			msg = "数据已经被处理，请刷新页面！";
		}
		return msg;
	}
	

	/**
	 * 划扣
	 * 2016年5月16日
	 * By zhangfeng
	 * @param pa
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String deductPayback(PaybackApply pa) {
		String msg = null;
		// 加锁
		Map<String,String>  map = new HashMap<String, String>();
		map.put("reqId", pa.getId());
		map.put("reqStatus", RepayApplyStatus.REPAYMENT_RETURN.getCode());
		PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
		if (ObjectHelper.isNotEmpty(pApply)) {
			pa.setReqTime(pApply.getReqTime());
			// 更新还款申请表
			pa.setDictPaybackStatus(RepayApplyStatus.PRE_PAYMENT.getCode());
			pa.setPaybackTransferInfo(null);
			pa.setApplyAmount(pa.getDeductAmount());
			pa.setDictRepayMethod(RepayChannel.DEDUCT.getCode());
			pa.setDictPaybackStatus(RepayApplyStatus.PRE_PAYMENT.getCode());
			pa.setSplitBackResult(CounterofferResult.PREPAYMENT.getCode());
			pa.setDictDeductType(DeductWays.HJ_02.getCode());
			pa.setDictDepositAccount(null);
			pa.setApplyBackMes(null);
			pa.preUpdate();
			int sum = dao.updateApplyPayback(pa);
			if(sum > 0){
				// 判断是否修改划扣账号
				if (StringUtils.isNotEmpty(pa.getLoanBank().getNewId())) {
					pa.getLoanBank().setBankTopFlag(PaybackApply.TOP_FLAG_NO);
					pa.getLoanBank().preUpdate();
					LoanBank loanBank = new LoanBank();
					loanBank.setId(pa.getLoanBank().getNewId());
					loanBank.setBankTopFlag(PaybackApply.TOP_FLAG);
					loanBank.preUpdate();
					loanDeductService.updateTopFlag(pa.getLoanBank());
					loanDeductService.updateTopFlag(loanBank);
				}

				// 操作历史
				PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(),
						pa.getPayback().getId(),
						RepaymentProcess.REPAYMENT_APPLY, TargetWay.REPAYMENT,
						PaybackOperate.APPLY_SUCEED.getCode(), "重新发起还款，申请金额："
								+ pa.getApplyAmount());
				historyService.insertPaybackOpe(paybackOpes);
				msg = "重新发起还款成功！";
			}else{
				msg = "数据已经被处理，请刷新页面！";
			}
		}else{
			msg = "数据已经被处理，请刷新页面！";
		}
		return msg;
	}

	/**
	 * 删除文件汇款表数据
	 * 2016年5月11日
	 * By zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void deleteTransferInfo(PaybackApply pa) {

		PaybackTransferInfo info = new PaybackTransferInfo();
		info.setrPaybackApplyId(pa.getId());
		List<PaybackTransferInfo> infoList = dealPaybackService.findTransfer(info);
		if (ArrayHelper.isNotEmpty(infoList)) {

			// 删除旧文件 (只有3.0系统的UE删除旧的文件，迁移2.0数据UE没有文件)
			if (ObjectHelper.isNotEmpty(pa) && StringUtils.isNotEmpty(pa.getDictSourceType())
					&& StringUtils.equals(pa.getDictSourceType(), SystemFromFlag.THREE.getCode())) {
				for (PaybackTransferInfo in : infoList) {
					if (StringUtils.isNotEmpty(in.getUploadPath())) {
						CeUtils.deleteFile(in.getUploadPath());
					}
				}
			}
		}
		// 删除汇款单
		dao.deletePaybackTransferInfo(info);

	}
	
	/**
	 * 更新还款申请信息(账户信息) 
	 * 2015年12月11日 By zhangfeng
	 * 
	 * @param paybackApply
	 * @param files 
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateTransferInfo(PaybackApply paybackApply, MultipartFile[] files) {
		if (StringUtils.equals(paybackApply.getDictRepayMethod(),
				RepayChannel.NETBANK_CHARGE.getCode())) {
			String id = paybackApply.getPaybackTransferInfo().getId();
			String dictDeposit = paybackApply.getPaybackTransferInfo().getDictDeposit();
			String tranDepositTime = paybackApply.getPaybackTransferInfo().getTranDepositTimeStr();
			String storesInAccount = paybackApply.getPaybackTransferInfo().getStoresInAccount();
			String reallyAmount = paybackApply.getPaybackTransferInfo().getReallyAmountStr();
			String depositName = paybackApply.getPaybackTransferInfo().getDepositName();
			String uploadPath = paybackApply.getPaybackTransferInfo().getUploadPath();
			String uploadFileName = paybackApply.getPaybackTransferInfo().getUploadFilename();
			String[] ids = id.split(",");
			String[] dictDeposits = dictDeposit.split(",");
			String[] tranDepositTimes = tranDepositTime.split(",");
			String[] reallyAmounts = reallyAmount.split(",");
			String[] depositNames = depositName.split(",");
			String[] uploadPaths = uploadPath.split(",");
			String[] uploadFileNames = uploadFileName.split(",");
			for (int i = 0; i < reallyAmounts.length; i++) {
				if (StringUtils.isNotEmpty(dictDeposits[i])) {
					paybackApply.getPaybackTransferInfo().setDictDeposit(dictDeposits[i].trim());
				} else {
					paybackApply.getPaybackTransferInfo().setDictDeposit(null);
				}
				if (StringUtils.isNotEmpty(tranDepositTimes[i])) {
					paybackApply.getPaybackTransferInfo().setTranDepositTime(DateUtils.parseDate(tranDepositTimes[i].trim()));
				} else {
					paybackApply.getPaybackTransferInfo().setTranDepositTime(null);
				}
				if (StringUtils.isNotEmpty(storesInAccount)) {
					paybackApply.getPaybackTransferInfo().setStoresInAccount(storesInAccount);
				} else {
					paybackApply.getPaybackTransferInfo().setStoresInAccount(null);
				}
				if (StringUtils.isNotEmpty(depositNames[i])) {
					paybackApply.getPaybackTransferInfo().setDepositName(depositNames[i].trim());
				} else {
					paybackApply.getPaybackTransferInfo().setDepositName(null);
				}
				if (StringUtils.isNotEmpty(reallyAmounts[i])) {
					paybackApply.getPaybackTransferInfo().setReallyAmount(new BigDecimal(reallyAmounts[i].trim()));
				} else {
					paybackApply.getPaybackTransferInfo().setDepositName(null);
				}

				// 上传新文件
				if (files[i].getSize() > 0) {
					if (StringUtils.isNotEmpty(paybackApply.getPaybackTransferInfo().getUploadPath())) {
						DocumentBean db = CeUtils.uploadFile(files[i],
								uploadPaths[i], paybackApply.getContractCode(),
								CeFolderType.PAYMENT_UPLOAD);
						
						if (!ObjectHelper.isEmpty(db)) {
							paybackApply.getPaybackTransferInfo().setUploadPath(db.getDocId());
							paybackApply.getPaybackTransferInfo().setUploadFilename(db.getDocTitle());
						}
					}
				} else {
					paybackApply.getPaybackTransferInfo().setUploadPath(uploadPaths[i]);
					paybackApply.getPaybackTransferInfo().setUploadFilename(uploadFileNames[i]);
				}
			
				// 新增汇款单
				if (ids.length != 0 && ids != null) {
					paybackApply.getPaybackTransferInfo().setAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
					paybackApply.getPaybackTransferInfo().setContractCode(paybackApply.getContractCode());
					paybackApply.getPaybackTransferInfo().setUploadName(UserUtils.getUser().getId());
					paybackApply.getPaybackTransferInfo().setModifyBy(UserUtils.getUser().getId());
					paybackApply.getPaybackTransferInfo().setUploadDate(new Date());
					paybackApply.getPaybackTransferInfo().setrPaybackApplyId(paybackApply.getId());
					paybackApply.getPaybackTransferInfo().setRelationType(TargetWay.REPAYMENT.getCode());
					paybackApply.getPaybackTransferInfo().setIsNewRecord(false);
					paybackApply.getPaybackTransferInfo().preInsert();
					dao.insertPayBackTransferInfo(paybackApply.getPaybackTransferInfo());
				}
			}
		}
	}

	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateApplyPayback(PaybackApply paybackApply) {
		dao.updateApplyPayback(paybackApply);
	}

	/**
	 * 同一合同编号，存入银行重复验证
	 * @param pa
	 * @return
	 */
	public String findTransferInfoByStoresInAccount(PaybackApply pa) {
		String msg = null;
		List<PaybackApply> paList = new ArrayList<PaybackApply>();
		PaybackApply apply = new PaybackApply();
		apply.setContractCode(pa.getContractCode());
		apply.setDictDepositAccount(pa.getPaybackTransferInfo().getStoresInAccount());
		apply.setDictPaybackStatus("'"+RepayApplyStatus.MATCH_FAILEN.getCode()+"','"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"','"+RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode()+"'");
		paList = applyPayService.findApplyPayback(apply);
		if (ArrayHelper.isNotEmpty(paList)) {
			// 过滤本条申请
			if(!StringUtils.equals(String.valueOf(pa.getId()), paList.get(0).getId())){
				msg = "您录入的汇款单存入银行已经重复，请确认数据是否正确！";
			}
		}
		return msg;
	}

	/**
	 * 不是同一合同编号 汇款单重复验证
	 * @param paybackTransferInfo
	 * @return
	 */
	public String findPayBackTransferOut(PaybackTransferInfo info) {
		String msg = null;
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		// 存款时间
		String tranDepositTime = info.getTranDepositTimeStr();
		// 存入账号
		String storesInAccount = info.getStoresInAccount();
		// 实际到账金额
		String reallyAmount = info.getReallyAmountStr();
		// 存款人
		String depositName = info.getDepositName();
		
		String[] tranDepositTimes = tranDepositTime.split(",");
		String[] reallyAmounts = reallyAmount.split(",");
		String[] depositNames = depositName.split(",");
		for (int i = 0; i < reallyAmounts.length; i++) {
			PaybackTransferOut out = new PaybackTransferOut();
			out.setOutDepositTime(DateUtils.parseDate(tranDepositTimes[i]));
			out.setOutEnterBankAccount(storesInAccount);
			out.setOutReallyAmount(new BigDecimal(reallyAmounts[i]));
			out.setOutDepositName(depositNames[i]);
			out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
			outList = paybackTransferOutService.findAuditedList(out);
			if (ArrayHelper.isNotEmpty(outList)) {
				msg = "系统已经存在查账成功相同的汇款单，请检查数据是否确认提交？";
			}
		}
		return msg;
	}
}
