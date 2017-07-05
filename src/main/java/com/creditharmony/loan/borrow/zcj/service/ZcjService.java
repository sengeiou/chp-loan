package com.creditharmony.loan.borrow.zcj.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.SendFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFileDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.event.GrantInsertUrgeService;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.zcj.dao.ZcjDao;
import com.creditharmony.loan.borrow.zcj.view.ZcjEntity;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * 退款
 * @Class Name ZcjService
 * @author wujj
 * @Create In 2016年8月24日
 */
@Service("zcjService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ZcjService extends CoreManager<ZcjDao, ZcjEntity>{
	
	@Autowired
	private ZcjDao zcjDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ContractFileDao contractFileDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private PaybackDao paybackDao;
	@Autowired
	private LoanBankDao loanBankDao;
	@Autowired
	private GrantInsertUrgeService grantInsertUrgeService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantSureService grantSureService;
	@Autowired
	private AssistService assistService;
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<ZcjEntity> getFinanceInfo(Page<ZcjEntity> page,ZcjEntity zcj){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<ZcjEntity> pageList = (PageList<ZcjEntity>)zcjDao.getFinanceInfo(pageBounds, zcj);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<ZcjEntity> getConfirmInfo(ZcjEntity zcj){
		List<ZcjEntity> list = zcjDao.getConfirmInfo(zcj);
		return list;
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<ZcjEntity> getAmountSum(ZcjEntity zcj){
		return zcjDao.getAmountSum(zcj);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String,Integer>> getRecommend(Map map){
		return zcjDao.getRecommend(map);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String,String>> getIsFrozenInfo(String contractCode){
		return zcjDao.getIsFrozenInfo(contractCode);
	}
	
	/**
	 * 大金融接收放款成功的状态处理：1.更新借款状态,更新loanBank表中的新增状态，  同时 更新还款表
	 *  2.更新放款表 
	 *  3.更新合同表，出借人信息及合同id 
	 *  4.插入催收服务费信息
	 *  5.插入历史
	 * 2017年2月21日
	 * By 朱静越
	 * @param loanInfo
	 * @param loanGrant
	 * @param lender
	 * @param docId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void djrReceiveFileDeal(LoanInfo loanInfo,LoanGrant loanGrant,String lender,String docId){
		loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		//审核通过后更新已还款为新增状态，可以使用
		LoanBank record = new LoanBank();
		record.setModifyBy("admin");
		record.setModifyTime(new Date());
		record.setDictMaintainType(MaintainType.ADD.getCode());
		record.setLoanCode(loanInfo.getLoanCode());
		loanBankDao.updateMaintainType(record);
		
		Payback payback = new Payback();
		payback.setContractCode(loanGrant.getContractCode());
		payback.setDictPayStatus(YESNO.NO.getCode());
		payback.setEffectiveFlag(YESNO.YES.getCode());
		paybackDao.updatePayback(payback);
		
		loanGrant.preUpdate();
		loanGrantDao.updateLoanGrant(loanGrant);
		
		// 更新合同表的审核状态为还款中
		Contract contract1 = new Contract();
		contract1.setContractCode(loanGrant.getContractCode());
		contract1.setDictCheckStatus(LoanApplyStatus.REPAYMENT.getCode());
		contractDao.updateContract(contract1);
		
		// 更新出借人姓名 合同文件id
		if(lender!=null){
			Map map = new HashMap();
			map.put("lender", lender);
			map.put("applyId", loanInfo.getApplyId());
			contractDao.updateLender(map);
		}
		this.insertContractFile(loanGrant.getContractCode(),docId);
		
		// 放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", loanInfo.getApplyId());
		map.put("loanFlag", ChannelFlag.ZCJ.getCode());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
		
		historyService.saveLoanStatusHis(loanInfo,GrantCommon.BIG_FIN_GRANT, GrantCommon.SUCCESS,"");
	}
	
	/**
	 * 插入合同文件，大金融放款成功之后，同步借款协议到3.0系统
	 * 2016年10月10日
	 * By 朱静越
	 * @param gdv
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertContractFile(String contractCode,String docId){
		ContractFile contractFile = new ContractFile();
		contractFile.setContractCode(contractCode);
		contractFile.setFileName(ContractType.CONTRACT_ZCJ_PROTOCOL.getName());
		contractFile.setContractFileName(ContractType.CONTRACT_ZCJ_PROTOCOL.getName());
		contractFile.setSignDocId(docId);
		contractFile.setSendFlag(SendFlag.NO.getCode());
		contractFile.setDownloadFlag(ContractType.CONTRACT_ZCJ_PROTOCOL.getFlag()); // 默认为0，
		contractFile.setFileShowOrder(ContractType.CONTRACT_ZCJ_PROTOCOL.getCode()); // 合同文件排序
		contractFile.preInsert();
		contractFileDao.insert(contractFile);
	}
	
	/**
	 * 大金融拒绝处理，数据流转到大金融债券退回列表：1.更新借款状态 2.插入历史
	 * 2017年2月21日
	 * By 朱静越
	 * @param contract 参数包括 applyId loanCode
	 * @param rejectReason 拒绝原因
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void djrRejectDeal(Contract contract,String rejectReason){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(contract.getApplyId());
		loanInfo.setLoanCode(contract.getLoanCode());
		loanInfo.setDictLoanStatus(LoanApplyStatus.BIGFINANCE_REJECT.getCode());
		loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		historyService.saveLoanStatusHis(loanInfo, LoanApplyStatus.BIGFINANCE_REJECT.getName()+"(大金融发起)",
				"成功", rejectReason);
		// 排序
		grantSureService.orderFileIdDeal(contract.getLoanCode());
	}
	
	/**
	 * 大金融待放款退回到合同审核，1.更新借款状态  2.更新合同表的退回标识同时将门店申请冻结原因更新到退回原因中 3.插入历史
	 * 2017年2月21日
	 * By 朱静越
	 * @param contract 参数包括 loanCode applyId contractCode
	 * @param frozenReason 门店申请冻结原因
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void djrReturnDeal(Contract contract,String frozenReason){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(contract.getApplyId());
		loanInfo.setLoanCode(contract.getLoanCode());
		loanInfo.setDictLoanStatus(LoanApplyStatus.BIGFINANCE_RETURN.getCode());
		loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		Contract updContract = new Contract();
		updContract.setContractCode(contract.getContractCode());
		updContract.setBackFlag(YESNO.YES.getCode());
		updContract.setContractBackResult(frozenReason);
		updContract.preUpdate();
		contractDao.updateContract(updContract);
		
		historyService.saveLoanStatusHis(loanInfo, LoanApplyStatus.BIGFINANCE_RETURN.getName()+"(大金融发起)", 
				"成功", frozenReason);
		// 排序
		grantSureService.orderFileIdDeal(contract.getLoanCode());
		// 退回到合同审核需要分单
		assistService.updateAssistAddAuditOperator(contract.getLoanCode()); 
	}
	
}
