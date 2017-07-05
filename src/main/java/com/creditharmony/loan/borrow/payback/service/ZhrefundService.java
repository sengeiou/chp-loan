package com.creditharmony.loan.borrow.payback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.payback.dao.ZhrefundDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.Zhrefund;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.PaybackMonthDao;

/**
 * 合同Service 
 * @Class Name ContractService
 * @create In 2015年12月1日
 * @author 张灏 
 */
@Service("zhrefundService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ZhrefundService extends CoreManager<ZhrefundDao, Zhrefund>{

	@Autowired
	private ZhrefundDao zhrefundDao;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private PaybackMonthDao paybackMonthDao;
	/**
	 * 查询中和东方不可退款列表
	 * @author 于飞
	 * @Create 2017年2月7日
	 * @param zhrefund
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<Zhrefund> getZhrefundList(Page<Zhrefund> page,Zhrefund zhrefund) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("contractCode");
		PageList<Zhrefund> pageList = (PageList<Zhrefund>) zhrefundDao
				.getZhrefundList(zhrefund, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 修改中和东方退款状态
	 * @author 于飞
	 * @Create 2017年2月7日
	 * @param zhrefund
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateZhrefundStatus(Zhrefund zhrefund) {
		zhrefundDao.updateZhrefundStatus(zhrefund);
	}
	
	/**
	 * 插入中和东方不可退款数据
	 * @author 于飞
	 * @Create 2017年2月7日
	 * @param zhrefund
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertZhrefund(List<Zhrefund> zhrefund) {
		/*zhrefund.setId(IdGen.uuid());
		zhrefund.setZhrefundStatus(ZhrefundStatus.YES.getCode());
		zhrefund.setCreateBy(UserUtils.getUser().getId());
		zhrefund.setModifyBy(UserUtils.getUser().getId());*/
		zhrefundDao.insertZhrefund(zhrefund);
	}
	
	/**
	 * 根据合同编号查找对应的数据
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param zhrefund
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Zhrefund findByContractCode(Zhrefund zhrefund){
		return zhrefundDao.findByContractCode(zhrefund);
	}
	
	/**
	 * 结清申请更新相关状态
	 * @author 于飞
	 * @Create 2017年4月26日
	 * @param contractCode
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void settleApply(String contractCode){
		//更新还款状态
		Payback payback = new Payback();
		payback.setDictPayStatus(RepayStatus.SETTLE_CONFIRM.getCode());
		payback.setContractCode(contractCode);
		payback.preUpdate();
		paybackDao.updatePayback(payback);
		
		//更新借款状态
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setDictLoanStatus(LoanApplyStatus.SETTLE_CONFIRM.getCode());
		loanInfo.setContractCode(contractCode);
		loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfoStatusByContractCode(loanInfo);
		
		//更新期供状态
		PaybackMonth paybackMonth = new PaybackMonth();
		paybackMonth.setContractCode(contractCode);
		paybackMonth.setDictMonthStatus(PeriodStatus.OVERDUE.getCode());
		paybackMonth.preUpdate();
		paybackMonthDao.updateStatusByContractCode(paybackMonth);
		
		paybackMonth.setDictMonthStatus(PeriodStatus.REPAYMENT.getCode());
		paybackMonth.preUpdate();
		paybackMonthDao.updateStatusByContractCode(paybackMonth);
		
	}

}
