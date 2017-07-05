package com.creditharmony.loan.borrow.grant.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCoborrowerService;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.DisCardDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;

/**
 * 分配卡号处理
 * @Class Name DisCardService
 * @author 朱静越
 * @Create In 2017年1月24日
 */
@Service("DisCardService")
public class DisCardService {
	@Autowired
	private DisCardDao disCardDao;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private LoanCoborrowerService loanCoborrowerService;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private AssistService assistService;
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DisCardService.class);
	
	/**
	 * 查询分配卡号页面数据显示
	 * 2017年1月17日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<DisCardEx> getDisCardList(Page<DisCardEx> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DisCardEx> pageList = (PageList<DisCardEx>)disCardDao.getDisCardList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询分配卡号列表，不分页
	 * 2017年1月18日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getDisCardList(LoanFlowQueryParam loanFlowQueryParam){
		return disCardDao.getDisCardList(loanFlowQueryParam);
	}
	
	/**
	 * 分配卡号提交操作:1.ca签章 2.更新借款状态 ，签章的状态 3.更新放款表，指定放款人员 4.添加历史
	 * 注意：ca签章失败，不能进行分配卡号
	 * 2016年4月22日
	 * By 朱静越
	 * @param userCode 放款用户code
	 * @param distachParamItem 传过来的参数
	 * @param middleId 中间人id
	 * @return
	 */
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String disCardDeal(String userCode,DistachParamEx distachParamItem,String middleId){
		String failedContractCode = null;
		try {
			Contract contract = contractDao.findByContractCode(distachParamItem.getContractCode());
			String channelFlag = contract.getChannelFlag();
			boolean result = false;
			if((ChannelFlag.CHP.getCode().equals(channelFlag) || ChannelFlag.P2P.getCode().equals(channelFlag))
					&&(!LoanProductCode.PRO_NONG_XIN_JIE.equals(contract.getProductType()))){
				// 查询自然人保证人
				LoanCoborrower coborrower = loanCoborrowerService
						.getSecurityByLoanCode(distachParamItem.getLoanCode());
				// 老版申请表 或者自然人保证人为空的情况下，走的是以前的签章
	             if (YESNO.NO.getCode().equals(distachParamItem.getLoanInfoOldOrNewFlag()) || ObjectHelper.isEmpty(coborrower)) {
	            	 result = grantCAService.signUpCA(distachParamItem.getLoanCode());
				}else {
					result = grantCAService.signUpCANew(distachParamItem.getLoanCode(),coborrower);
				}
			}
			Contract contractUpd = new Contract();
			contractUpd.setContractCode(distachParamItem.getContractCode());
			if(result || LoanProductCode.PRO_NONG_XIN_JIE.equals(contract.getProductType())){ //签章成功，修改借款状态等
				if (result) {
					contractUpd.setSignUpFlag(YESNO.YES.getCode());
					contractDao.updateContract(contractUpd);
				}
			    LoanInfo loanInfo = new LoanInfo();
			    loanInfo.setApplyId(distachParamItem.getApplyId());
			    loanInfo.setLoanCode(distachParamItem.getLoanCode());
			    loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_TO_SEND
						.getCode());
			    loanInfo.preUpdate();
			    applyLoanInfoDao.updateLoanInfo(loanInfo);
			    
			    // 更新放款表
			    LoanGrant loanGrant = new LoanGrant();
			    loanGrant.setContractCode(distachParamItem.getContractCode());
			    loanGrant.setMidId(middleId);
			    loanGrant.setLendingUserId(loanGrantService.selUserName(userCode));
				MiddlePerson middlePerson = middlePersonService.selectById(middleId); // 根据中间人id查询中间人姓名，获得放款途径
				if (GrantCommon.ZHONG_JING.equals(middlePerson.getMiddleName())) {
					loanGrant.setDictLoanWay(PaymentWay.ZHONGJIN.getCode());
				} else if (GrantCommon.TONG_LIAN.equals(middlePerson.getMiddleName())){
					loanGrant.setDictLoanWay(PaymentWay.TONG_LIAN.getCode());
				}else {
					loanGrant.setDictLoanWay(PaymentWay.NET_BANK.getCode());
				}
				loanGrantDao.updateLoanGrant(loanGrant);
				
				// 添加历史
				historyService.saveLoanStatusHis(loanInfo,GrantCommon.DIS_CARD_NAME, GrantCommon.SUCCESS, "");
			}else{
				contractUpd.setSignUpFlag(YESNO.NO.getCode());
				// 更新合同表
				contractDao.updateContract(contractUpd);
			    failedContractCode = distachParamItem.getContractCode();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分配卡号失败，发生异常",e);
			failedContractCode = distachParamItem.getContractCode();
			throw new ServiceException("分配卡号失败，发生异常");
		}finally{
			return failedContractCode;
		}
	}
	
	/**
	 * 分配卡号退回:1.更新借款状态 2.更新合同表的退回原因  3.插入历史
	 * 2016年4月22日
	 * By 朱静越
	 * @param applyId
	 * @param backReason 退回原因
	 * @param backReasonCode
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String disCardBack(String applyId,String backReason,String backReasonCode){
		Contract contract = contractDao.findByApplyId(applyId);
		
		// 设置状态为   放款退回
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(applyId);
		loanInfo.setLoanCode(contract.getLoanCode());
		loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_RETURN.getCode());
		//分配卡号退回到合同审核   修改orderField 用于排序
		LoanInfo loanInfoOrder=new LoanInfo();
		loanInfoOrder.setApplyId(applyId);
		loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_RETURN.getCode());
		String orderField=createOrderFileldService.backCheckContractByDiscard(loanInfoOrder);
		loanInfo.setOrderField(orderField);
		
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		// 更新合同表的退回原因
		contract.setContractBackResult(backReason);
		contract.setBackFlag(YESNO.YES.getCode());
		contractDao.updateContract(contract);
		//退回到合同审核 添加分单功能
		assistService.updateAssistAddAuditOperator(contract.getLoanCode()); 
		historyService.saveLoanStatusHis(loanInfo,GrantCommon.DIS_CARD_BACK, GrantCommon.SUCCESS,backReason);
		return contract.getContractCode();
	}
	
	/**
	 * 分配卡号驳回申请：1.更新借款表的驳回申请的字段 2.插入历史
	 * 2017年2月7日
	 * By 朱静越
	 * @param applyParam
	 * @param remark
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void backFlozen(String[] applyParam,String remark) {
		
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(applyParam[0]);
		loanInfo.setLoanCode(applyParam[2]);
		loanInfo.setFrozenReason(" ");
        loanInfo.setFrozenCode(" ");
        loanInfo.setFrozenFlag(YESNO.NO.getCode());
        loanInfo.setFrozenLastApplyTime(new Date());
        //分配卡号 驳回申请 修改orderField 用于排序
        LoanInfo loanInfoOrder=new LoanInfo();
		loanInfoOrder.setApplyId(applyParam[0]);
		String orderField=createOrderFileldService.backFrozenCodeByDiscard(loanInfoOrder);
		loanInfo.setOrderField(orderField);
        applyLoanInfoDao.updateLoanInfo(loanInfo);
        
        historyService.saveLoanStatusHis(loanInfo, ContractConstant.REJECT_FROZEN, GrantCommon.SUCCESS, remark);
	}
}
