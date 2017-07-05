package com.creditharmony.loan.borrow.grant.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoansendResult;
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
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.GrantSureDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认service，用来声明放款确认过程中的各种方法
 * @Class Name GrantSureService
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@Service("GrantSureService")
public class GrantSureService{

	@Autowired
	private GrantSureDao grantSureDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private AssistService assistService;
	
	private final Logger logger = LoggerFactory.getLogger(GrantSureService.class);
	
	/**
	 * 获得待款项确认列表数据
	 * 2017年2月8日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getGrantSureList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)grantSureDao.getGrantSureList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 获取待款项确认列表，不进行分页
	 * 2017年2月8日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGrantSureList(LoanFlowQueryParam loanFlowQueryParam){
		return grantSureDao.getGrantSureList(loanFlowQueryParam);
	}
	
	/**
	 * 更新单子的借款标识：1.更新借款主表 2.更新合同表中的借款标识 3.插入历史
	 * 2015年12月3日 By 朱静越
	 * @param apply 流程需要的参数
	 * @param loanMarking 借款标识
	 * @return String
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateFlagParam(String[] applyParam, String loanMarking) {
		LoanInfo loanInfo = new LoanInfo();
		Contract contract = new Contract();
		String stepName = "";
		loanInfo.setApplyId(applyParam[0]);
		loanInfo.setLoanCode(applyParam[2]);
		contract.setContractCode(applyParam[1]);
		// 更新流程中的借款标识的code
		ChannelFlag channelFlag = ChannelFlag.parseByName(loanMarking);
		if (ObjectHelper.isNotEmpty(channelFlag)) {
			loanInfo.setLoanFlag(channelFlag.getCode());
			contract.setChannelFlag(channelFlag.getCode());
		}
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		contractDao.updateContract(contract);
		// 需要进行判断，记录历史
		if (ChannelFlag.CHP.getName().equals(loanMarking)) {
			stepName = "取消P2P标识";
		}else{
			stepName = "添加"+loanMarking+"标识";
		}
		historyService.saveLoanStatusHis(loanInfo,stepName, GrantCommon.SUCCESS,"");
	}
	
	/**
	 * 标识上传的处理：1.更新借款表标识，2.同时更新合同借款表标识,3.需要添加历史
	 * 2016年5月17日
	 * By 朱静越
	 * @param contract
	 * @param loanFlag 标识的name
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveUpFlag(Contract contract,String loanFlag){
		LoanInfo loanInfo = new LoanInfo();
		Contract updContract = new Contract();
		loanInfo.setApplyId(contract.getApplyId());
		loanInfo.setLoanCode(contract.getLoanCode());
		
		updContract.setContractCode(contract.getContractCode());
		ChannelFlag channelFlag = ChannelFlag.parseByName(loanFlag);
		if (!ObjectHelper.isEmpty(channelFlag)) {
			loanInfo.setLoanFlag(channelFlag.getCode());
			updContract.setChannelFlag(channelFlag.getCode());
		}
		
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		contractDao.updateContract(updContract);
		// 从数据库中取值，获得该单子的标识
		String beforeFlag = contract.getChannelFlag();
		if (ChannelFlag.CHP.getCode().equals(beforeFlag)&&ChannelFlag.P2P.getName().equals(loanFlag)) {
			historyService.saveLoanStatusHis(loanInfo,"添加P2P标识", GrantCommon.SUCCESS,"");
		}
		if (ChannelFlag.P2P.getCode().equals(beforeFlag)&&ChannelFlag.CHP.getName().equals(loanFlag)) {
			historyService.saveLoanStatusHis(loanInfo,"取消P2P标识", GrantCommon.SUCCESS,"");
		}
		
	}
	
	/**
	 * 上传打款表单笔处理：1.对于TG的数据，进行ca签章的加盖，非TG的数据，有保证人的时候，进行保证人注册
	 * 2.对结果进行更新  3.更新完成之后，进行借款状态的更新  4.更新放款表的提交时间和提交批次  5.添加历史
	 * 2016年5月5日
	 * By 朱静越
	 * @param baseView
	 * @param listFlag
	 * @param curLetter
	 * @param curUrgentFlag
	 * @param submitStrTime
	 * @return 返回提示信息
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String saveImportSend(Contract contract, String listFlag,
			String curLetter, String curUrgentFlag, Date submitTime,String loanUrgeFlag) {
		String message = null;
		String dictLoanStatus = null;
		LoanInfo loanInfo = new LoanInfo();
		LoanGrant loanGrant = new LoanGrant();
		Contract updContract = new Contract();
		loanInfo.setApplyId(contract.getApplyId());
		loanInfo.setLoanCode(contract.getLoanCode());
		loanGrant.setContractCode(contract.getContractCode());
		updContract.setContractCode(contract.getContractCode());
		boolean caResult = false;
		/**
		 * TG的单子需要进行CA签章
		 */
		if (LoanModel.TG.getName().equals(listFlag)) {
			logger.debug("方法：saveImportSend：待款项确认TG的单子进行盖章，处理开始");
			caResult = grantCAService.signUpCA(contract.getLoanCode());
			logger.debug("方法：saveImportSend：待款项确认TG的单子进行盖章，处理结束，结果为："+caResult);
			if (caResult) {
				updContract.setSignUpFlag(YESNO.YES.getCode());
				dictLoanStatus = LoanApplyStatus.LOAN_TO_SEND.getCode();
			}else{
				updContract.setSignUpFlag(YESNO.NO.getCode());
		        message=GrantCommon.SIGN_FAILED;
			}
		} else {
			/**
			 * 非TG的单子进行CA注册
			 */
			if (StringUtils.isNotEmpty(contract.getLegalMan())) {
				if (!YESNO.YES.getCode().equals(contract.getIsRegister())) { // 没有进行注册的
					logger.debug("方法：saveImportSend：待款项确认非TG的单子进行注册，处理开始");
				    Map<String,Object> resultMap = 
					grantCAService.registCA(contract.getApplyId(),
							contract.getLoanCode());
				    caResult = (boolean) resultMap.get("registResult");
				    logger.debug("方法：saveImportSend：待款项确认非TG的单子进行注册，处理结束，"
				    		+ "结果为："+(String) resultMap.get("message"));
					if (caResult) {
						dictLoanStatus = LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode();
						updContract.setIsRegister(YESNO.YES.getCode());
					} else {
						updContract.setIsRegister(YESNO.NO.getCode());
						// 记录注册失败原因
						message = (String) resultMap.get("message");
						if (message.contains("companyRegisteredNo")) {
							message = message.replace("companyRegisteredNo",
									GrantCommon.COMPANY_REGISTER_NO);
						}
					}
				}
			} else {
				dictLoanStatus = LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode();
			}
			//修改借款表orderField用于页面排序
			LoanInfo loanInfoOrder=new LoanInfo();
			String applyid="";
			if(contract!=null&&contract.getApplyId()!=null){
				applyid=contract.getApplyId();
			}else if(contract!=null&&contract.getLoanCode()!=null){
				Contract contractOrder=contractDao.findByLoanCode(contract.getLoanCode());
				applyid=contractOrder.getApplyId();
			}
			loanInfoOrder.setApplyId(applyid);
			loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
			String orderField=createOrderFileldService.sendDisCardByGrantSure(loanInfoOrder);
			loanInfo.setOrderField(orderField);
		}
		
		if (StringUtils.isNotEmpty(dictLoanStatus)) {
			loanInfo.setDictLoanStatus(dictLoanStatus);
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,"待款项确认","成功","");
		}
		if (StringUtils.isNotEmpty(updContract.getIsRegister())||StringUtils.isNotEmpty(updContract.getSignUpFlag())) {
			contractDao.updateContract(updContract);
		}
		// 更新单子为认证通过
		loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
				.getCode());
		loanGrantDao.updateLoanGrant(loanGrant);
		updGrantPch(contract, curLetter, curUrgentFlag, submitTime,
				loanUrgeFlag);
		
		return message;
	}

	/**
	 * 待款项确认提交的时候，更新放款批次
	 * 2017年2月9日
	 * By 朱静越
	 * @param contract 合同编号
	 * @param curLetter 信借
	 * @param curUrgentFlag 加急
	 * @param submitTime 提交时间
	 * @param loanUrgeFlag 是否加急的标识
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updGrantPch(Contract contract, String curLetter,
			String curUrgentFlag, Date submitTime, String loanUrgeFlag) {
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setContractCode(contract.getContractCode());
		// 如果不是加急的，则批次为正常信借的，否则为加急的
		if ("加急".equals(loanUrgeFlag)) {
			loanGrant.setGrantPch(curUrgentFlag);
		} else {
			loanGrant.setGrantPch(curLetter);
		}
		loanGrant.setSubmissionsDate(submitTime);
		loanGrantDao.updateLoanGrant(loanGrant);
	}
	
	/**
	 * 待款项确认退回处理:1.借款状态  2.合同页面显示的退回原因，退回标识  3.历史
	 * 2017年2月9日
	 * By 朱静越
	 * @param workItemView 参数
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updGrantSureBack(LoanFlowWorkItemView workItemView){
		LoanInfo loanInfo = new LoanInfo();
		Contract contract = new Contract();
		loanInfo.setApplyId(workItemView.getApplyId());
		loanInfo.setLoanCode(workItemView.getLoanCode());
		loanInfo.setDictLoanStatus(LoanApplyStatus.PAYMENT_BACK.getCode());
		contract.setContractCode(workItemView.getContractCode());
		contract.setContractBackResult(workItemView.getBackReason());
		contract.setBackFlag(YESNO.YES.getCode());
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		contractDao.updateContract(contract);
		//退回到合同审核  添加分单功能
		assistService.updateAssistAddAuditOperator(workItemView.getLoanCode()); 
		historyService.saveLoanStatusHis(loanInfo,"待款项确认退回", GrantCommon.SUCCESS,workItemView.getBackReason());
		// 排序
		orderFileIdDeal(workItemView.getLoanCode());
		// 退回到合同审核进行分单
		assistService.updateAssistAddAuditOperator(workItemView.getLoanCode()); 
	}
	
	/**
	 * 待款项确认驳回申请处理：1.更新借款主表 2.历史
	 * 2017年2月9日
	 * By 朱静越
	 * @param workItemView 参数
	 * @param remark 驳回原因
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updBackFrozen(LoanFlowWorkItemView workItemView,String remark){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(workItemView.getApplyId());
		loanInfo.setLoanCode(workItemView.getLoanCode());
		loanInfo.setFrozenReason(" ");
        loanInfo.setFrozenCode(" ");
        loanInfo.setFrozenFlag(YESNO.NO.getCode());
        loanInfo.setFrozenLastApplyTime(new Date());
        applyLoanInfoDao.updateLoanInfo(loanInfo);
        
        historyService.saveLoanStatusHis(loanInfo, ContractConstant.REJECT_FROZEN, GrantCommon.SUCCESS, remark);
        // 排序
        orderFileIdDeal(workItemView.getLoanCode());
	
	}
	
	/**
	 * 大金融驳回申请和退回到合同的时候调用
	 * 2017年3月3日
	 * By 朱静越
	 * @param loanCode 借款编号
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void orderFileIdDeal(String loanCode){
		Contract contractInfo = contractDao.findByLoanCode(loanCode);
		LoanInfo loanInfo = applyLoanInfoDao.getByLoanCode(loanCode);
		String backFlag = contractInfo.getBackFlag();
		String code = "";
		 if(StringUtils.isEmpty(backFlag)){
             backFlag = "00";
         }else{
             backFlag = "0"+backFlag;
         }
		 String frozenFlag = YESNO.YES.getCode().equals(loanInfo.getFrozenFlag())? "01":"00";
		 if (LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(loanInfo.getDictLoanStatus())) {
			 code = loanInfo.getDictLoanStatus()+"-0"+loanInfo.getLoanUrgentFlag()+"-"+frozenFlag+"-"+backFlag+"-05";
		 }else {
			code = loanInfo.getDictLoanStatus()+"-0"+loanInfo.getLoanUrgentFlag()+"-"+frozenFlag+"-"+backFlag;
		 }
		 OrderFiled filed = OrderFiled.parseByCode(code);
         String orderField = filed.getOrderId(); 
		 if (!ObjectHelper.isEmpty(contractInfo.getModifyTime())) {
			 orderField +="-"+DateUtils.formatDate(contractInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
		 }
		 // 更新数据库
         Map<String,Object> loanParam = new HashMap<String,Object>();
         loanParam.put("loanCode", contractInfo.getLoanCode());
         loanParam.put("orderField", orderField);
         applyLoanInfoDao.updOrderField(loanParam);
	}
}
