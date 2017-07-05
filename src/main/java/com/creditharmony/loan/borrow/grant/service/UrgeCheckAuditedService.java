package com.creditharmony.loan.borrow.grant.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.UrgeCheckAuditedDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 查账账款列表业务处理service
 * 
 * @Class Name AuditedService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("UrgeCheckAuditedService")
public class UrgeCheckAuditedService extends CoreManager<UrgeCheckAuditedDao, PaybackTransferOut> {
	
	/**
	 * 催收服务费跳转列表 
	 * 2016年1月5日 By 
	 * zhangfeng
	 * @param page
	 * @param payBackTransferOut
	 * @return page
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackTransferOut> getUrgeAuditedList(Page<PaybackTransferOut> page, PaybackTransferOut payBackTransferOut) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackTransferOut> pageList = (PageList<PaybackTransferOut>) dao.getUrgeAuditedList(payBackTransferOut, pageBounds);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (PaybackTransferOut paybackTrans : pageList) {
			paybackTrans.setOutAuditStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.BANKSERIAL_CHECK, paybackTrans.getOutAuditStatus()));
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 更新汇款流水表状态(applyId) 
	 * 2015年12月25日 
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutStatuByApplyId(PaybackTransferOut paybackTransferOut) {
		dao.updateOutStatuByApplyId(paybackTransferOut);
	}
	
	/**
	 * 更新汇款流水表状态(id)
	 * 2015年12月25日 
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutStatuById(PaybackTransferOut paybackTransferOut) {
		dao.updateOutStatuById(paybackTransferOut);
	}
	
	/**
	 * 查询账款信息 
	 * 2016年1月5日 
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackTransferOut> getAuditedList(PaybackTransferOut payBackTransferOut) {
		return dao.getAuditedList(payBackTransferOut);
	}

	/**
	 * 导入银行账款数据
	 * 2016年1月27日
	 * By zhangfeng
	 * @param lst
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insert(List<PaybackTransferOut> lst) {
		dao.insert(lst);
	}

	/**
	 * 查询未匹配银行流水
	 * 2016年2月29日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackTransferOut> getNoAuditedList(PaybackTransferOut paybackTransferOut) {
		return dao.getNoAuditedList(paybackTransferOut);
	}
}
