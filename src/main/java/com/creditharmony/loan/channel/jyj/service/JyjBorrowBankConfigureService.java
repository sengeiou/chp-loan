package com.creditharmony.loan.channel.jyj.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.channel.jyj.dao.JyjBorrowBankConfigureDao;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

@Service
public class JyjBorrowBankConfigureService extends
CoreManager<JyjBorrowBankConfigureDao, JyjBorrowBankConfigure>{

	@Autowired
	JyjBorrowBankConfigureDao dao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private LoanBankDao loanBankDao;
	@Autowired
	private AssistService assistService;
	@Autowired
	private JYJGrantAuditService jyjGrantAuditService;
	
	public void insert(JyjBorrowBankConfigure record) {
		
		if (record.getIsNewRecord()){
			record.preInsert();
			dao.insert(record);
		    }else{
			record.preUpdate();
			dao.update(record);
	   }
	}

	public void updateByBankCode(JyjBorrowBankConfigure record){
		record.preUpdate();
		dao.updateByBankCode(record);
	}
	
	public List<JyjBorrowBankConfigure> queryList(JyjBorrowBankConfigure record) {
		if(record.getProductCode() == null){
			record.setProductCode("");
		}
		return dao.queryList(record);
	}
	
	/**
	 * 查询简易借开户行
	 * By 任志远 2017年5月6日
	 *
	 * @param flag 1启用，0未启用
	 * @return
	 */
	public List<JyjBorrowBankConfigure> queryList(int flag, String productCode) {
		JyjBorrowBankConfigure jyjBorrowBankConfigure = new JyjBorrowBankConfigure();
		jyjBorrowBankConfigure.setFlag(flag);
		jyjBorrowBankConfigure.setProductCode(productCode);
		return dao.queryList(jyjBorrowBankConfigure);
	}

	// 更新所有的数据
	public void updateAllinvalid(JyjBorrowBankConfigure record) {
		dao.updateAllinvalid(record);
		
	}
	
	/**
	 * 查询金信简易借待分配卡号列表信息
	 * @author wjj
	 * @Create 2017年5月6日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<DisCardEx> getGCDiscardList(Page<DisCardEx> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<DisCardEx> pageList = (PageList<DisCardEx>)dao.getGCDiscardList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询金信待分配卡号列表信息-不分页
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getGCDiscardList(LoanFlowQueryParam loanFlowQueryParam){
		return dao.getGCDiscardList(loanFlowQueryParam);
	}
	
	
	
	/**
	 * 查询金信简易借待放款列表信息
	 * @author wjj
	 * @Create 2017年5月6日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getGCGrantList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)dao.getGCGrantList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询金信简易借待放款列表信息-不分页
	 * @author  wjj
	 * @Create 2017年5月6日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantList(LoanFlowQueryParam loanFlowQueryParam){
		return dao.getGCGrantList(loanFlowQueryParam);
	}
	
	/**
	 * 放款确认 操作数据库相关信息 更新放款表  借款表中借款状态  历史表   催收服务费
	 * @author songfeng 
	 * @Create 2017年2月21日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void grantSureInfo(LoanInfo loanInfo,LoanGrant loanGrant){
		//更新借款表借款状态  并加历史
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,"尾款放款","成功","");
		}
		// 更新还款主表
		Payback payback = new Payback();
		payback.setContractCode(loanGrant.getContractCode());
		payback.setDictPayStatus(YESNO.NO.getCode());
		payback.setEffectiveFlag(YESNO.YES.getCode());
		paybackDao.updatePayback(payback);
		
		// 更新已还款为新增状态，可以使用
		LoanBank record = new LoanBank();
		record.setModifyBy("admin");
		record.setModifyTime(new Date());
		record.setDictMaintainType(MaintainType.ADD.getCode());
		record.setLoanCode(loanInfo.getLoanCode());
		loanBankDao.updateMaintainType(record);
		//更新放款表  
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			// 添加档案数据
			jyjGrantAuditService.addArchives(loanGrant.getContractCode());
		}
	}
	
	/**
	 * 尾款待放款退回到合同审核  操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月21日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void sendBackGrantInfo(LoanInfo loanInfo,LoanGrant loanGrant,String result){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			//退回到合同审核  添加分单功能
			assistService.updateAssistAddAuditOperator(loanInfo.getLoanCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_RETURN.getCode());
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.LOAN_SEND_RETURN.getName(),"成功",result);
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			// 更新合同表中的退回标识
			Contract contract = new Contract();
			contract.setContractCode(loanGrant.getContractCode());
			contract.setBackFlag(YESNO.YES.getCode());
			contractDao.updateContract(contract);
			// 催收服务费的处理
			jyjGrantAuditService.urgeDeal(loanGrant.getContractCode());
		}
	}

	/** 
	 * 修改简易借比例配置
	 * By 任志远 2017年5月22日
	 *
	 * @param firstLoanProportion
	 * @param endLoanProportion
	 */
	public void updateProportion(BigDecimal firstLoanProportion, BigDecimal endLoanProportion) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("firstLoanProportion", firstLoanProportion);
		params.put("endLoanProportion", endLoanProportion);
		dao.updateProportion(params);
	}
	
}
