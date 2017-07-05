package com.creditharmony.loan.borrow.zcj.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.zcj.dao.ZcjGrantSureDao;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认service，用来声明放款确认过程中的各种方法
 * @Class Name GrantSureService
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@Service("ZcjGrantSureService")
public class ZcjGrantSureService{

	@Autowired
	private ZcjGrantSureDao zcjGrantSureDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private HistoryService historyService;
	
	private final Logger logger = LoggerFactory.getLogger(ZcjGrantSureService.class);
	
	/**
	 * 获得大金融待款项确认列表数据
	 * 2017年2月8日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getZcjGrantSureList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)zcjGrantSureDao.getZcjGrantSureList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 获取大金融待款项确认列表，不进行分页
	 * 2017年2月8日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getZcjGrantSureList(LoanFlowQueryParam loanFlowQueryParam){
		return zcjGrantSureDao.getZcjGrantSureList(loanFlowQueryParam);
	}
}
