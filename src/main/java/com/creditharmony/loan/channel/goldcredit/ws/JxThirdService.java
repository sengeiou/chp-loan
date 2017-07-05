package com.creditharmony.loan.channel.goldcredit.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.jxcredit.JxThirdBaseService;
import com.creditharmony.adapter.service.jxcredit.bean.CreditSelectResultsInBean;
import com.creditharmony.adapter.service.jxcredit.bean.JxThirdInBean;
import com.creditharmony.adapter.service.jxcredit.bean.JxThirdOutBean;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.goldcredit.service.JxSendDataService;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;

@Service
public class JxThirdService extends JxThirdBaseService {
	private Logger logger = LoggerFactory.getLogger(JxThirdService.class);
	@Autowired
	private JxSendDataService jxSendDataService;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;//loanGrantService
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private HistoryService historyService;

	@Override
	public JxThirdOutBean doExec(JxThirdInBean paramBean) {
		List<CreditSelectResultsInBean> csrbList = paramBean.getSelectResults();
		JxThirdOutBean rb = new JxThirdOutBean();
		if (csrbList == null || csrbList.size() == 0) {
			rb.setRetCode(ReturnConstant.ERROR);
			rb.setRetMsg("参数解析出错！参数信息不能够为空。");
			return rb;
		}
		logger.info("【金信第三次调用CHP3.0推送过来的数据总数为:" + csrbList.size() + "】"+"推送过来的信息为："+JSONObject.toJSONString(csrbList));
		// 不要的金信数据信息集合
		List<String> notContractCodesList = Lists.newArrayList();
		// 用来存放合同编号的集合.
		List<String> contractCodeList = Lists.newArrayList();
		// 用来存放所有的合同编号
		List<String> contractCodesList = Lists.newArrayList();

		// 用于保存不同合同编号的操作原因
		Map<String, String> map = new HashMap<String, String>();
		for (CreditSelectResultsInBean csrBean : csrbList) {
			Short acceptResult = csrBean.getAcceptResult();
			String contractNumber = csrBean.getContractNumber();
			// 操作原因
			String operateReason = csrBean.getOperateReason();
			// 判断此条数据信息是不是金信网要的数据信息
			if (ChannelConstants.GOLD_CREDIT_WS_YES.equals(acceptResult)) {
				contractCodeList.add(contractNumber);
			} else {
				// 数据信息时金信网不要的数据信息 ,将不要的数据信息退回到债权退回列表中，用工作流来更新这条数据信息
				notContractCodesList.add(contractNumber);
			}
			contractCodesList.add(contractNumber);
			map.put(contractNumber, operateReason);
		}
		logger.info("【金信第三次调用CHP3.0推送过来的金信同意接受数据总数为:" + contractCodeList.size() + "】"
		+"同意的合同编号为："+JSONObject.toJSONString(contractCodesList));
		logger.info("【金信第三次调用CHP3.0推送过来的金信不同意接受数据总数为:" + notContractCodesList.size() + "】"
				+"不同意的合同编号为："+JSONObject.toJSONString(notContractCodesList));
		List<String> applyIdsList = null;

		if (contractCodesList != null && contractCodesList.size() > 0) {
			applyIdsList = jxSendDataService.findJINXINSendBack(contractCodesList);
		}
		try {
			if (applyIdsList != null && applyIdsList.size() > 0) {
				//废除工作流 数据从数据库中读取
				String[] applyIds = applyIdsList.toArray(new String[applyIdsList.size()]);
				LoanFlowQueryParam queryParam = new LoanFlowQueryParam();
				queryParam.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_WAIT);
				queryParam.setApplyIds(applyIds);
				List<LoanFlowWorkItemView> gcGrantSureList = loanGrantService.getGCGrantSureList(queryParam);
				if (!ObjectHelper.isEmpty(gcGrantSureList)) {
					for (LoanFlowWorkItemView ItemView : gcGrantSureList) {
						LoanInfo loanInfo = new LoanInfo();
						LoanGrant loanGrant = new LoanGrant();
						loanGrant.setLoanCode(ItemView.getLoanCode());
						loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
								.getCode());
						loanInfo.setApplyId(ItemView.getApplyId());
						loanInfo.setLoanCode(ItemView.getLoanCode());
						logger.info("【金信第三次调用CHP3.0推送的债权合同编号为"+ItemView.getContractCode()+"】");
						//根据合同编号,获取原因
						String contractCode = ItemView.getContractCode();
						String grantSureBackReason = map.get(contractCode);
						
						//判断向工作流中取出的数据信息，那条是金信网要的数据信息，要的话就推送到分配卡号队列中，不要的就推送到金信债权退回列表中
						if (contractCodeList.contains(ItemView.getContractCode())) {
							//推送到分配卡号队列
							loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
							loanInfo.setDictLoanStatusLabel(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getName());
							// 设置排序规则
							loanInfo.setOrderField(getOrderField(ItemView.getApplyId(),
									LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode()));
							
						} else {
							//推送到金信债权退回队列
							loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_REVIEW_REJECT.getCode());
							loanInfo.setDictLoanStatusLabel(LoanApplyStatus.GOLDCREDIT_REVIEW_REJECT.getName());
							// 设置排序规则
							loanInfo.setOrderField(getOrderField(ItemView.getApplyId(),
									LoanApplyStatus.GOLDCREDIT_REVIEW_REJECT.getCode()));
						}
						//废除工作流，修改借款主表--借款状态
						applyLoanInfoDao.updateLoanInfo(loanInfo);
						// 更新单子为已认证
						loanGrantDao.updateLoanGrant(loanGrant);
						// 根据合同编号,获取原因  并保存历史表中
						historyService.saveLoanStatusHis(loanInfo, "待款项确认", "成功", grantSureBackReason);
					}
				}
				
			} else {
				throw new Exception("数据库中查询出来的数据信息为空！无法完成相应的操作！");
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			rb.setRetCode(ReturnConstant.ERROR);
			rb.setRetMsg(e.getMessage());
			return rb;
		}
		System.err.println("send--------------------------------------------------------");
		rb.setRetCode(ReturnConstant.SUCCESS);
		return rb;
	}

	/**
	 * 生成相应的排序规则
	 * 
	 * @param applyId
	 *            apply_Id
	 * @param dictLoanStatusCode
	 *            借款状态
	 * @return
	 */
	private String getOrderField(String applyId, String dictLoanStatusCode) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("applyId", applyId);
		LoanInfo loanInfo = loanInfoDao.findLoanLinkedContract(param);
		Contract contract = contractDao.findByApplyId(applyId);
		String frozenFlag = null;
		String backFlag = null;
		String orderField = null;
		String urgentFlag = loanInfo.getLoanUrgentFlag();
		if (ObjectHelper.isEmpty(loanInfo) || StringUtils.isEmpty(loanInfo.getFrozenCode())) {
			frozenFlag = "00";
		} else {
			frozenFlag = "01";
		}
		if (ObjectHelper.isEmpty(contract) || StringUtils.isEmpty(contract.getBackFlag())) {
			backFlag = "00";
		} else {
			backFlag = "01";
		}
		String code = dictLoanStatusCode + "-0" + urgentFlag + "-" + frozenFlag + backFlag;
		OrderFiled filed = OrderFiled.parseByCode(code);
		if (ObjectHelper.isNotEmpty(filed)) {
			orderField = filed.getOrderId();
			if (!ObjectHelper.isEmpty(loanInfo.getModifyTime())) {
				orderField += "-" + DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
			}
		}
		return orderField;
	}
}
