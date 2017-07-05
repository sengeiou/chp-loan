package com.creditharmony.loan.borrow.trusteeship.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.event.GrantInsertUrgeService;
import com.creditharmony.loan.borrow.trusteeship.dao.LoanExcelDao;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel1;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel2;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel5;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class TrusteeshipService {

	@Autowired
	private LoanExcelDao loanExcelDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantInsertUrgeService grantInsertUrgeService;
	
	public Page<GrantExcel1> getExcel1(Map<String, Object> param,
			Page<GrantExcel1> page) {
		PageBounds pageBounds = null;
		if (null != page) {
			pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		}

		PageList<GrantExcel1> pageList = (PageList<GrantExcel1>) loanExcelDao
				.getDataRows(param, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	public List<GrantExcel1> getExcel1(Map<String, Object> param) {
		List<GrantExcel1> pageList = loanExcelDao.getDataRows(param);
		return pageList;
	}
	
	public List<GrantExcel2> getExcel2(Map<String, Object> param) {
		List<GrantExcel2> pageList = loanExcelDao.getDataRows2(param);
		return pageList;
	}
	
	/**
	 * 放款审核 线下委托提现导入：1.修改委托提现的状态，如果失败，修改失败原因
	 * 2016年6月4日
	 * By 朱杰
	 * @param dataList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void importExcel(GrantExcel5 item) {
		String mark = item.getMark();
		String infoId = mark.substring(mark.lastIndexOf("_") + 1);
		HashMap<String, Object> resultMap = loanExcelDao.getLoanInfo(infoId);
		String loanCode =  (String) resultMap.get("loan_code");

		String result = null;
		if ("0000".equals(item.getReturnCode().trim())) {
			result = "提现成功";
			loanExcelDao.updateTrustCash(loanCode, item.getMoney());
		} else {
			result = "提现失败";
		}
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setLoanCode(loanCode);
		loanGrant.setTrustCashRtn(result);
		loanGrant.setTrustCashFailure(item.getReturnMsg());
		loanGrant.preUpdate();
		loanGrantDao.updateLoanGrant(loanGrant);
	}
	
	/**
	 * 资金托管批量放款完成：1.更新借款状态 2.更新借款表 3.插入催收服务费 4.插入历史
	 * 2017年2月16日
	 * By 朱静越
	 * @param item
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void trueeshipGrant(LoanFlowWorkItemView item){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(item.getApplyId());
		loanInfo.setLoanCode(item.getLoanCode());
		loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY
				.getCode());
		loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setContractCode(item.getContractCode());
		loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
				.getCode());
		loanGrant.setLendingTime(new Date());
		loanGrant.setDictLoanWay(PaymentWay.KING_ACCOUNT.getCode());
		loanGrant.preUpdate();
		loanGrantDao.updateLoanGrant(loanGrant);
		
		// 放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", item.getApplyId());
		map.put("model", LoanModel.TG.getName());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
		
		historyService.saveLoanStatusHis(loanInfo,"放款", GrantCommon.SUCCESS,"");
	}

}
