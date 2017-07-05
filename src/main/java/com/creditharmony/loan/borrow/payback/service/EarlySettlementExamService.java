package com.creditharmony.loan.borrow.payback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.ConfirmPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.EarlySettlementExamDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * 提前结清待审核列表业务Service
 * @Class Name EarlySettlementExamService
 * @author zhaojinping
 * @Create In 2016年1月6日
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class EarlySettlementExamService {
    
	public final static String MONTH_ZERO = "0.00";
	@Autowired
	private EarlySettlementExamDao earlySettlementExamDao;
	@Autowired
	private ConfirmPaybackDao confirmPaybackDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PaybackDao paybackDao;
	/**
	 * 获取提前结清待审核列表
	 * 2016年1月6日
	 * By zhaojinping
	 * @param page
	 * @param paybackCharge
	 * @return page
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public Page<PaybackCharge> getAllList(Page<PaybackCharge> page,PaybackCharge paybackCharge){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackCharge> pageList = (PageList<PaybackCharge>)earlySettlementExamDao.getAllList(pageBounds,paybackCharge);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 获取该借款人的应还违约金及罚息总额
	 * 2016年1月6日
	 * By zhaojinping
	 * @param contractCode
	 * @return list
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public List<PaybackMonth> getMonthInfo(String contractCode){
		return earlySettlementExamDao.getMonthInfo(contractCode);
	}
	
	/**
	 * 获取该借款人的申请还款总额
	 * 2016年1月6日
	 * By zhaojinping
	 * @param id
	 * @return paybackApply
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	 public PaybackApply getApplyAmount(String id){
		return earlySettlementExamDao.getApplyAmount(id);
	}
	
	/**
	 * 根据id更改冲抵申请表中的冲抵申请状态为 “还款退回”
	 * 2016年1月13日
	 * By zhaojinping
	 * @param paybackCharge
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updatePaybackChargeStatus2(PaybackCharge paybackCharge){
		earlySettlementExamDao.updatePaybackChargeStatus(paybackCharge);
	}
	
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public boolean updatePaybackChargeStatusAll(PaybackCharge paybackCharge,PaybackOpeEx paybackOpes){
		Payback payback = new Payback();
		payback.setContractCode(paybackCharge.getContractCode());
		payback.setDictPayStatus(RepayStatus.PAYBACK_RETURN.getCode());
		historyService.insertPaybackOpe(paybackOpes);
		earlySettlementExamDao.updatePaybackChargeStatus(paybackCharge);
		earlySettlementExamDao.updatePaybackStatus(payback);
		return true;
	}
	
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public boolean updatePaybackChargeStatus(PaybackCharge paybackCharge,PaybackOpeEx paybackOpes,Payback payback){
		historyService.insertPaybackOpe(paybackOpes);
		earlySettlementExamDao.updatePaybackChargeStatus(paybackCharge);
		earlySettlementExamDao.updatePaybackStatus(payback);
		return true;
	}
	
	/**
	 * 根据id,查询提前结清的申请信息进行审核
	 * 2016年1月13日
	 * By zhaojinping
	 * @param id
	 * @return paybackCharge
	 */
	@Transactional(value = "loanTransactionManager",readOnly=false)
	public PaybackCharge getEarlyBackApply(PaybackCharge paybackCharge){
		return earlySettlementExamDao.getEarlyBackApply(paybackCharge);
	}
	
	/**
	 * 获取提前结清的减免金额总和
	 * 2016年1月9日
	 * By zhaojinping
	 * @param contractCode
	 * @return paybackMonth
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public PaybackMonth getPenaltyPunishSum(String contractCode){
		return earlySettlementExamDao.getPenaltyPunishSum(contractCode);
	}
	
	/**
	 * 更改还款主表中的还款状态为  ‘提前结清待确认’
	 * 2016年1月6日
	 * By zhaojinping
	 * @param paramMap
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager",readOnly=false)
	public void updatePaybackStatus(Payback payback){
		earlySettlementExamDao.updatePaybackStatus(payback);
	}
	
	
	/**
	 * 根据主表ID查询当前期数
	 * 2016年3月4日
	 * By zhaojinping
	 * @param id
	 * @return
	 */
//	public Payback getPaybackCurrentMonth(String id){
//		return earlySettlementExamDao.getPaybackCurrentMonth(id);
//	}
	/**
	 * 判断客户是否有未还的违约金罚息
	 * 2016年3月4日
	 * By zhaojinping
	 * @param paybackMonth
	 * @return
	 */
	/*public boolean PenaltyInterest(PaybackMonth paybackMonth){
		boolean flag = true;
		BigDecimal bgSum = new BigDecimal(MONTH_ZERO);
		PaybackMonth paybackMonthInfo = confirmPaybackDao.findPaybackMonth(paybackMonth);
		if(!ObjectHelper.isEmpty(paybackMonthInfo)){
			// 应还罚息总额(sum)
			BigDecimal  monthInterestPunishshouldSum = paybackMonthInfo.getMonthInterestPunishshouldSum();
			monthInterestPunishshouldSum = (monthInterestPunishshouldSum == null) ? bgSum : monthInterestPunishshouldSum; 
			// 实还罚息总额(sum)
			BigDecimal monthInterestPunishactualSum = paybackMonthInfo.getMonthInterestPunishactualSum();
			monthInterestPunishactualSum = (monthInterestPunishactualSum == null) ? bgSum : monthInterestPunishactualSum; 
			// 应还违约金总额(sum) monthPenaltyShouldSum
			BigDecimal monthPenaltyShouldSum = paybackMonthInfo.getMonthPenaltyShouldSum();
			monthPenaltyShouldSum = (monthPenaltyShouldSum == null) ? bgSum : monthPenaltyShouldSum; 
			// 实还违约金总额(sum) monthPenaltyActualSum
			BigDecimal monthPenaltyActualSum = paybackMonthInfo.getMonthPenaltyActualSum();
			monthPenaltyActualSum = (monthPenaltyActualSum == null) ? bgSum : monthPenaltyActualSum; 
			if((monthInterestPunishshouldSum.add(monthPenaltyShouldSum).subtract(monthInterestPunishactualSum).subtract(monthInterestPunishshouldSum)).compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())){
				// 有未还的违约金罚息
				flag = false;
			}
			
		}
		return flag;
	}
	*/
}
