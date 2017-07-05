package com.creditharmony.loan.channel.goldcredit.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.jxcredit.JxSecondBaseService;
import com.creditharmony.adapter.service.jxcredit.bean.CreditCreditorsRightsOutBean;
import com.creditharmony.adapter.service.jxcredit.bean.CreditNodesOutBean;
import com.creditharmony.adapter.service.jxcredit.bean.CreditSelectResultsInBean;
import com.creditharmony.adapter.service.jxcredit.bean.JxSecondInBean;
import com.creditharmony.adapter.service.jxcredit.bean.JxSecondOutBean;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.goldcredit.service.JxSendDataService;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;

@Service
public class JxSecondService extends JxSecondBaseService {
	private Logger logger = LoggerFactory.getLogger(JxSecondService.class);
	@Autowired
	private JxSendDataService jxSendDataService;
	@Resource(name = "appFrame_flowServiceImpl")
	protected FlowService flowService;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private HistoryService historyService;

	@Override
	public JxSecondOutBean doExec(JxSecondInBean paramBean) {
		List<CreditSelectResultsInBean> csrbList = paramBean.getSelectResults();
		JxSecondOutBean rb = new JxSecondOutBean();
		if (csrbList == null || csrbList.size() == 0) {
			rb.setRetCode(ReturnConstant.ERROR);
			rb.setRetMsg("参数解析出错！参数信息不能够为空。");
			return rb;
		}
		logger.info("【金信第二次调用CHP3.0推送过来的数据总数为:" + csrbList.size() + "】,传过来的参数为："+JSONObject.toJSONString(csrbList));
		// 用来存放合同编号的集合.
		List<String> contractCodesList = Lists.newArrayList();
		// 不要的金信数据信息集合
		List<String> notContractCodesList = Lists.newArrayList();

		// 向数据库中查询金信网要的数据信息
		List<CreditCreditorsRightsOutBean> jxSendList = null;
		// 向数据库中查询金信网退回的数据信息
		List<String> applyIdsList = Lists.newArrayList();

		// 用于保存不同合同编号的操作原因
		Map<String, String> map = new HashMap<String, String>();
		for (CreditSelectResultsInBean csrBean : csrbList) {
			Short acceptResult = csrBean.getAcceptResult();
			String contractNumber = csrBean.getContractNumber();
			String operateReason = csrBean.getOperateReason();
			// 判断此条数据信息是不是金信网要的数据信息
			if (ChannelConstants.GOLD_CREDIT_WS_YES.equals(acceptResult)) {
				contractCodesList.add(contractNumber);
			} else {
				// 数据信息时金信网不要的数据信息 ,将不要的数据信息退回到债权退回列表中，用工作流来更新这条数据信息
				notContractCodesList.add(contractNumber);
			}
			map.put(contractNumber, operateReason);
		}

		logger.info("【金信第二次调用CHP3.0推送过来的金信同意接受数据总数为:" + contractCodesList.size() + "】"
		+"同意接受的合同编号为："+JSONObject.toJSONString(contractCodesList));
		logger.info("【金信第二次调用CHP3.0推送过来的金信不同意接受数据总数为:" + notContractCodesList.size() + "】"
				+"不同意的合同编号为："+JSONObject.toJSONString(notContractCodesList));
		

		// 向数据库查询金信网需要的数据信息
		if (contractCodesList != null && contractCodesList.size() > 0) {
			try {
				logger.info("【金信第二次调用CHP3.0推送过来的金信同意接受数据查询详细数据start");
				jxSendList = jxSendDataService.findJINXINPush(contractCodesList);
				logger.info("第二次推送到金信的信息为："+JSONObject.toJSONString(jxSendList));
				if (jxSendList != null && jxSendList.size() > 0) {
					for (int i = 0; i < jxSendList.size(); i++) {
						if (StringUtils.isNotEmpty(jxSendList.get(i).getRiskLevel())) {
							String riskLevel = getRiskLevel(Integer.parseInt(jxSendList.get(i).getRiskLevel()));
							jxSendList.get(i).setRiskLevel(riskLevel);
						}
					}
				}
				logger.info("【金信第二次调用CHP3.0推送过来的金信同意接受数据查询详细数据end");
			} catch (Exception e) {
				logger.info("【金信第二次调用CHP3.0推送过来的金信同意接受数据查询详细数据异常" + e.getMessage());
				e.printStackTrace();
				throw e;

			}
		}
		// 查询金信网不需要的applyId信息
		if (notContractCodesList != null && notContractCodesList.size() > 0) {
			logger.info("【金信第二次调用CHP3.0推送过来的金信拒绝数据查询详细数据start");
			applyIdsList = jxSendDataService.findJINXINSendBack(notContractCodesList);
			logger.info("【金信第二次调用CHP3.0推送过来的金信拒绝数据查询详细数据end");
		}

		if (applyIdsList != null && applyIdsList.size() > 0) {
			// 废除工作流 改从数据库查询数据
			String[] applyIds = (String[]) applyIdsList.toArray(new String[applyIdsList.size()]);
			LoanFlowQueryParam queryParam = new LoanFlowQueryParam();
			queryParam.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_WAIT);
			queryParam.setApplyIds(applyIds);
			List<LoanFlowWorkItemView> gcGrantSureList = loanGrantService.getGCGrantSureList(queryParam);
			
			try {
				if (gcGrantSureList.size() > 0) {
					for (LoanFlowWorkItemView workItem : gcGrantSureList) {
						// 废除工作流，存到数据库中
						logger.info("【金信第二次调用CHP3.0推送的债权合同编号为" + workItem.getContractCode() + "】");
						// 更新借款主表信息--借款状态
						LoanInfo loanInfo = new LoanInfo();
						loanInfo.setApplyId(workItem.getApplyId());
						loanInfo.setLoanCode(workItem.getLoanCode());
						loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_FIRST_REJECT.getCode());
						loanInfo.setDictLoanStatusLabel(LoanApplyStatus.GOLDCREDIT_FIRST_REJECT.getName());
						// 设置排序规则
						loanInfo.setOrderField(getOrderField(workItem.getApplyId(),
								LoanApplyStatus.GOLDCREDIT_FIRST_REJECT.getCode()));
						applyLoanInfoDao.updateLoanInfo(loanInfo);
						// 根据合同编号,获取原因  并保存历史表中
						String contractCode = workItem.getContractCode();
						String grantSureBackReason = map.get(contractCode);
						historyService.saveLoanStatusHis(loanInfo, "待款项确认", "成功", grantSureBackReason);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				rb.setRetCode(ReturnConstant.ERROR);
				rb.setRetMsg("数据在拼接过程中出错！");
				return rb;
			}
		}

		// 判断向数据库中取出来的数据是不是为空,拼接起来后向金信网推送回去
		if (jxSendList != null && jxSendList.size() > 0) {
			logger.info("【金信第二次调用CHP3.0推送过来的金信接受数据详细数据返回start");
			CreditNodesOutBean cnBean = new CreditNodesOutBean();
			cnBean.setRights(jxSendList);
			rb.setCreditNodes(cnBean);
			rb.setRetCode(ReturnConstant.SUCCESS);
			logger.info("【金信第二次调用CHP3.0推送过来的金信接受数据详细数据返回end");
		} else {
			rb.setRetCode(ReturnConstant.SUCCESS);
			rb.setRetMsg("数据执行成功,没有发现金信网需要的数据信息！");
		}
		System.err.println("send--------------------------------------------------------");
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
		String frozenFlag = null;
		String orderField = null;
		String urgentFlag = loanInfo.getLoanUrgentFlag();
		if (ObjectHelper.isEmpty(loanInfo) || StringUtils.isEmpty(loanInfo.getFrozenCode())) {
			frozenFlag = "00";
		} else {
			frozenFlag = "01";
		}
		String code = dictLoanStatusCode + "-0" + urgentFlag + "-" + frozenFlag + "-01";
		OrderFiled filed = OrderFiled.parseByCode(code);
		if (ObjectHelper.isNotEmpty(filed)) {
			orderField = filed.getOrderId();
			if (!ObjectHelper.isEmpty(loanInfo.getModifyTime())) {
				orderField += "-" + DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
			}
		}
		return orderField;
	}

	/**
	 * 通过评分获得评分等级
	 * 
	 * @param score
	 * @return
	 */
	private String getRiskLevel(int score) {
		String riskLevel = "";

		if (score > 750) {
			riskLevel = "A1";
		} else if (score > 720) {
			riskLevel = "A2";
		} else if (score > 700) {
			riskLevel = "A3";
		} else if (score > 690) {
			riskLevel = "B1";
		} else if (score > 680) {
			riskLevel = "B2";
		} else if (score > 670) {
			riskLevel = "B3";
		} else if (score > 660) {
			riskLevel = "C1";
		} else if (score > 650) {
			riskLevel = "C2";
		} else if (score > 640) {
			riskLevel = "C3";
		} else if (score > 630) {
			riskLevel = "C4";
		} else if (score > 620) {
			riskLevel = "D1";
		} else if (score > 610) {
			riskLevel = "D2";
		} else if (score > 600) {
			riskLevel = "D3";
		} else if (score > 590) {
			riskLevel = "D4";
		} else if (score > 580) {
			riskLevel = "E1";
		} else if (score > 570) {
			riskLevel = "E2";
		} else if (score > 560) {
			riskLevel = "E3";
		} else {
			riskLevel = "F";
		}

		return riskLevel;
	}
}
