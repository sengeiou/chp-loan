package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.dao.ApplyPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.DealPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.PaybackTransferOutDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackService;

/**
 * 待还款匹配业务处理service
 * 
 * @Class Name MatchingPaybackService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("matchingPaybackService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class MatchingPaybackService extends CoreManager<ApplyPaybackDao, PaybackApply> {

	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private DealPaybackDao dealPaybackDao;
	@Autowired
	private HistoryService historyService;
	@Autowired 
	private PaybackTransferOutDao paybackTransferOutDao;

	/**
	 * 批量退回
	 * 2016年5月15日
	 * By zhangfeng
	 * @param applyId
	 * return none
	 * @return  boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean matchingSingleBack(PaybackApply pa) {
		// 退回标识  false退回数据
		boolean isExistFlag = this.isExist(pa.getId());
		try{
			// 匹配加锁
			Map<String,String>  map = new HashMap<String, String>();
			map.put("reqId", pa.getId());
			map.put("reqStatus", RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
			PaybackApply pApply = applyPayService.getApplyPaybackReq(map);
			if (ObjectHelper.isNotEmpty(pApply)) {
				if (!isExistFlag) {
					// 更新还款申请表退回状态
					pa.setId(pa.getId());
					pa.setApplyReallyAmount(BigDecimal.ZERO);
					pa.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_RETURN.getCode());
					pa.setSplitBackResult(CounterofferResult.RETURN.getCode());
					pa.setReqTime(pApply.getReqTime());
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
						// 保存批量退回操作历史
						PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(), pa.getPayback().getId(), RepaymentProcess.RETURN,
								TargetWay.REPAYMENT,
								PaybackOperate.RETURN_SUCCESS.getCode(),
								"批量退回，失败原因：款项尚未到账，或者存入日期/存入账户/存入金额有误，请核实！");
	
						historyService.insertPaybackOpe(paybackOpes);
					}else{
						throw new ServiceException("批量匹配异常！");
					}
				}
			}
			isExistFlag = false;
		}catch(Exception e){
			isExistFlag = true;
		}
		return isExistFlag;
	}

	/**
	 * 退回验证流水是否存在
	 * @param id
	 * @return boolean
	 */
	private Boolean isExist(String id) {
		List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferInfo info = new PaybackTransferInfo();
		int isExistCount = 0;
		info.setrPaybackApplyId(id);
		info.setAuditStatus("'" + BankSerialCheck.CHECKE_SUCCEED.getCode()
				+ "','" + BankSerialCheck.CHECKE_FAILED.getCode() + "','"
				+ BankSerialCheck.OFFLINE_CHECK.getCode() + "'");
		info.setRelationType(TargetWay.REPAYMENT.getCode());
		infoList = dealPaybackDao.findTransfer(info);
		if (ArrayHelper.isNotEmpty(infoList)) {
			for (PaybackTransferInfo pInfo : infoList) {
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				PaybackTransferOut out = new PaybackTransferOut();
				out.setOutDepositTime(pInfo.getTranDepositTime());
				out.setOutEnterBankAccount(pInfo.getStoresInAccount());
				out.setOutReallyAmount(pInfo.getReallyAmount());
				out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
				outList = paybackTransferOutDao.findAuditedList(out);
				if (ArrayHelper.isNotEmpty(outList)) {
					isExistCount++;
				}else{
					break;
				}
			}
			
			// 汇款单流水不存在
			if(infoList.size() != isExistCount){
				// 查询是否存在手动匹配的还款流水
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				PaybackTransferOut out = new PaybackTransferOut();
				out.setrPaybackApplyId(id);
				outList = paybackTransferOutDao.findList(out);
				if(ArrayHelper.isNotEmpty(outList)){
					for(PaybackTransferOut po:outList){
						po.setrPaybackApplyId(null);
						po.setTransferAccountsId(null);
						po.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
						po.setContractCode(null);
						paybackTransferOutDao.updateOutStatuById(po);
					}
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
		return false;
	}
}
