package com.creditharmony.loan.borrow.blue.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.blue.dao.PaybackBlueDao;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlue;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlueAmountEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;

/**
 * 蓝补管理列表service
 * @Class Name PaybackBlueService
 * @author 侯志斌
 * @Create In 2016年3月1日
 */
@Service("PaybackBlueService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PaybackBlueService  extends CoreManager<PaybackBlueDao, PaybackBlue>{
	
	@Autowired
	private PaybackService applyPayService;
	
	@Autowired 
	private PaybackBlueAmountService blusAmountService;
	
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 查询蓝补信息(页面)
	 * 2016年3月1日
	 * By 侯志斌
	 * @param page
	 * @param payback
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackBlue> findPayback(Page<PaybackBlue> page, PaybackBlue paybackBlue) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackBlue> pageList = (PageList<PaybackBlue>) dao.findPayback(paybackBlue, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询蓝补信息
	 * 2016年3月3日
	 * By 侯志斌
	 * @param customerCode
	 * @param contractCode
	 * @return Payback
	 */
	public List<Payback> findPaybackByCustomer(String customerCode, String contractCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerCode", customerCode);
		params.put("contractCode", contractCode);
		String contractCodeOther = dao.findPaybackByCustomer(params);
		if (StringUtils.isNotEmpty(contractCodeOther)) {
			Payback payback = new Payback();
			payback.setContractCode(contractCodeOther);
			payback.setPayStatus(new String[] { "0", "1" });
			List<Payback> paybackList = new ArrayList<Payback>();
			paybackList = applyPayService.findPayback(payback);
			return paybackList;
		}
		return null;
	}
	
	/**
	 * 蓝补转账
	 * 2016年3月3日
	 * By 侯志斌
	 * @param paybackList
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean transPaybackBlue(Payback payback) {
		// 修改转出方蓝补
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		BigDecimal blueAmount = payback.getTransmitBuleAmount();

		
		paybackBuleAmount.setId(IdGen.uuid());
		paybackBuleAmount.setContractCode(payback.getContractCodeLast());
    	paybackBuleAmount.setTradeType(TradeType.TURN_OUT.getCode());
    	paybackBuleAmount.setTradeAmount(blueAmount);
    	paybackBuleAmount.setDictDealUse(payback.getTransAmountFor()); // 退款内容
    	paybackBuleAmount.setSurplusBuleAmount(payback.getPaybackBuleAmountLast());
    	paybackBuleAmount.setCreateBy(UserUtils.getUser().getId());
    	paybackBuleAmount.setCreateTime(new Date());
    	paybackBuleAmount.setModifyBy(UserUtils.getUser().getId());
    	paybackBuleAmount.setModifyTime(new Date());
	    blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
	    
	    //修改转入方蓝补
	    payback.setContractCode(payback.getContractCode());
		payback.setPaybackBuleAmount(payback.getTransmitBuleAmount().add(payback.getPaybackBuleAmount()));
		payback.preUpdate();
	    applyPayService.updatePayback(payback);
	    paybackBuleAmount.setId(IdGen.uuid());
	    paybackBuleAmount.setContractCode(payback.getContractCode());
	    paybackBuleAmount.setSurplusBuleAmount(payback.getTransmitBuleAmount().add(payback.getPaybackBuleAmount()));
	    paybackBuleAmount.setDictDealUse(payback.getTransAmountFor()); // 退款内容
	    blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
	    
	    String paybackApplyId = null;
	    if (payback.getPaybackApply() != null) {
	    	paybackApplyId = payback.getPaybackApply().getId();
	    }

	    PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
			   payback.getId(), RepaymentProcess.CONFIRM,
			   TargetWay.PAYMENT, "成功", "");
	
	    historyService.insertPaybackOpe(paybackOpes);
			
		payback.setContractCode(payback.getContractCodeLast());
		payback.setPaybackBuleAmount(payback.getPaybackBuleAmountLast());
		payback.preUpdate();
		applyPayService.updatePayback(payback);
		return true;
		
	}
	
	/**
	 * 蓝补退款
	 * 2016年3月3日
	 * By 侯志斌
	 * @param payback
	 * @param paybackBuleReson 
	 * @param paybackBuleAmountLast 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean refundPaybackBlue(Payback payback, BigDecimal paybackBuleAmountLast, String paybackBuleReson) {
		// 修改蓝补
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		BigDecimal blueAmount = payback.getPaybackBuleAmount();
		
		payback.setContractCode(payback.getContractCode());
//		payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
		payback.setPaybackBuleAmount(paybackBuleAmountLast);
		payback.preUpdate();
		applyPayService.updatePayback(payback);
		paybackBuleAmount.setContractCode(payback.getContractCode());
    	paybackBuleAmount.setTradeType(TradeType.TRANSFERRED.getCode());
    	paybackBuleAmount.setTradeAmount(blueAmount);
    	paybackBuleAmount.setDictDealUse(paybackBuleReson); // 退款内容
    	paybackBuleAmount.setSurplusBuleAmount(payback.getPaybackBuleAmount());
		blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
		
		String paybackApplyId = null;
		if (payback.getPaybackApply() != null) {
			paybackApplyId = payback.getPaybackApply().getId();
		}

		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApplyId,
				payback.getId(), RepaymentProcess.CONFIRM,
				TargetWay.PAYMENT, "成功", "");
		
		historyService.insertPaybackOpe(paybackOpes);
	
			
		
		return true;
		
	}

	/**
	 * 根据查询条件查询进行导出的单子
	 * 2016年3月1日
	 * By 侯志斌
	 * @param page
	 * @param payback
	 * @return page
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public List<PaybackBlueAmountEx> selectPaybackBlueAmoun(
			PaybackBlueAmountEx paybackBlueAmountEx) {
		return dao.selectPaybackBlueAmoun(paybackBlueAmountEx);
	}
	
	/**
	 * 查询蓝补对账单信息(页面)
	 * 2016年3月1日
	 * By 侯志斌
	 * @param page
	 * @param payback
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackBlueAmountEx> findPaybackBlueAmoun(Page<PaybackBlueAmountEx> page, PaybackBlueAmountEx paybackBlueAmountEx) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackBlueAmountEx> pageList = (PageList<PaybackBlueAmountEx>) dao.selectPaybackBlueAmoun(paybackBlueAmountEx, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	public List<Map<String, Object>> getCustomer(PaybackBlue paybackBlue) {
		return dao.getCustomer(paybackBlue);
	}
	
	/**
	 * 修改蓝补金额
	 * 2016年4月18日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleReson 
	 * @param paybackBuleAmountLast 
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean updatePaybackBlue(Payback payback, String paybackBuleReson) {
		// 修改蓝补
		User user = UserUtils.getUser();
		PaybackBuleAmont paybackBuleAmount = new PaybackBuleAmont();
		payback.setContractCode(payback.getContractCode());
		payback.setPaybackBuleAmount(payback.getPaybackBuleAmount());
		payback.preUpdate();
		applyPayService.updatePayback(payback);
		paybackBuleAmount.setId(IdGen.uuid());
		paybackBuleAmount.setContractCode(payback.getContractCode());
    	paybackBuleAmount.setTradeType(TradeType.TRANSFERRED.getCode());
    	paybackBuleAmount.setTradeAmount(payback.getPaybackBuleAmount());
    	paybackBuleAmount.setDictDealUse("修改蓝补金额"); // 退款内容
    	paybackBuleAmount.setSurplusBuleAmount(payback.getPaybackBuleAmount());
    	paybackBuleAmount.setCreateBy(user.getId());
    	paybackBuleAmount.setCreateTime(new Date());
    	paybackBuleAmount.setModifyBy(user.getId());
    	paybackBuleAmount.setModifyTime(new Date());
		blusAmountService.insertPaybackBuleAmount(paybackBuleAmount);
		return true;
		
	}

	/**
	 *get payback
	 * @param pb
	 * @return paybackBlue 
	 */
	public PaybackBlue getPayback(PaybackBlue pb) {
		return dao.findPayback(pb);
	}
}
