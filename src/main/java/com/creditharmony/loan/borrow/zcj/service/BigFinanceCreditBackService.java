package com.creditharmony.loan.borrow.zcj.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.zcj.dao.BigFinanceCreditBackDao;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认service，用来声明放款确认过程中的各种方法
 * @Class Name GrantSureService
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@Service("bigFinanceCreditBackService")
public class BigFinanceCreditBackService{

	@Autowired
	private BigFinanceCreditBackDao bigFinanceCreditBackDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantSureService grantSureService;
	@Autowired
	private AssistService assistService;
	
	/**
	 * 获得大金融债权退回列表数据
	 * 2017年2月8日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getCreditBackList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)bigFinanceCreditBackDao.getCreditBackList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 获取大金融债券退回列表，不进行分页
	 * 2017年2月8日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getCreditBackList(LoanFlowQueryParam loanFlowQueryParam){
		return bigFinanceCreditBackDao.getCreditBackList(loanFlowQueryParam);
	}
	
	/**
	 * 大金融债权退回列表返回到大金融待款项确认：1.更新借款状态 2.插入历史
	 * 2017年2月21日
	 * By 朱静越
	 * @param contract 参数包括：applyId loanCode
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void backToGrantSure(Contract contract){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(contract.getApplyId());
		loanInfo.setLoanCode(contract.getLoanCode());
		loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		historyService.saveLoanStatusHis(loanInfo, 
				"大金融返回到待款项确认", "成功", ExportFlagConstants.GOLD_RETURN_TO_COMFIRM);
		// 排序
		grantSureService.orderFileIdDeal(contract.getLoanCode());
	}
	
	/**
	 * 大金融债权退回到合同审核:1.更新借款状态 2.更新合同表 3.插入历史
	 * 2017年2月22日
	 * By 朱静越
	 * @param loanCode
	 * @param applyId
	 * @param grantBackReason
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void backToContractAudit(String loanCode,String applyId,String grantBackReason){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setLoanCode(loanCode);
		loanInfo.setApplyId(applyId);
		loanInfo.setDictLoanStatus(LoanApplyStatus.BIGFINANCE_REJECT.getCode());
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		Contract contract = new Contract();
		contract.setLoanCode(loanCode);
		contract.setBackFlag(YESNO.YES.getCode());
		contract.setContractBackResult(grantBackReason);
		contractDao.updateContract(contract);
		
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setLoanCode(loanCode);
		loanGrant.setZcjRejectFlag(YESNO.YES.getCode());
		loanGrantDao.updateLoanGrant(loanGrant);
		
		historyService.saveLoanStatusHis(loanInfo,"大金融债权退回到合同审核", "成功", grantBackReason);
		// 排序
		grantSureService.orderFileIdDeal(loanCode);
		// 退回到合同审核需要分单
		assistService.updateAssistAddAuditOperator(loanCode); 
	}
	
	/**
	 * 大金融债权退回列表驳回申请：1.更新借款主表 2.插入历史  3.进行排序
	 * 2017年2月22日
	 * By 朱静越
	 * @param applyId
	 * @param loanCode 借款编号
	 * @param rejectReason 拒绝原因
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void bigFinanceBackFrozen(String applyId,String loanCode,String rejectReason){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(applyId);
		loanInfo.setLoanCode(loanCode);
		loanInfo.setFrozenReason(" ");
        loanInfo.setFrozenCode(" ");
        loanInfo.setFrozenFlag(YESNO.NO.getCode());
        loanInfo.setFrozenLastApplyTime(new Date());
        applyLoanInfoDao.updateLoanInfo(loanInfo);
        
        historyService.saveLoanStatusHis(loanInfo, ContractConstant.REJECT_FROZEN, "驳回成功", rejectReason);
	}
}
