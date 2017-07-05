package com.creditharmony.loan.car.common.util;

import java.util.HashMap;
import java.util.Map;

import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;

/**
 * 退回标红置顶业务工具类
 * 
 * @author 陈伟东
 *
 */
public class BackAndRedTopUtil {

	/**
	 * 记录所有提交下一步的源节点-response-目标节点的关系。 Map<源节点名称,Map<源节点的response值,对应的目标节点名称>>
	 */
	@Deprecated
	private static Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

	static {
		// 申请
		Map<String, String> responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_UPLOAD_FILE.getCode(),
				CarLoanSteps.UPLOAD_MATERIAL.getName());
		map.put(CarLoanSteps.FACE_AUDIT_APPLY.getName(), responsMap);
		// 上传资料
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_FRIST_AUDIT.getCode(),
				CarLoanSteps.FIRST_AUDIT.getName());
		map.put(CarLoanSteps.UPLOAD_MATERIAL.getName(), responsMap);
		// 评估报告录入
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_LOAN_APPLY.getCode(),
				CarLoanSteps.FACE_AUDIT_APPLY.getName());
		map.put(CarLoanSteps.APPRAISER.getName(), responsMap);
		// 初审
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_SEC_AUDIT.getCode(),
				CarLoanSteps.RECHECK_AUDIT.getName());
		map.put(CarLoanSteps.FIRST_AUDIT.getName(), responsMap);
		// 复审
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_FINAL_CHECK.getCode(),
				CarLoanSteps.FINAL_AUDIT.getName());
		responsMap.put(CarLoanResponses.TO_FINAL_CHECK_CONDICTION.getCode(),
				CarLoanSteps.FINAL_AUDIT.getName());
		map.put(CarLoanSteps.RECHECK_AUDIT.getName(), responsMap);
		// 终审
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.FINAL_CHECK_PASS.getCode(),
				CarLoanSteps.RATE_CHECK.getName());
		responsMap.put(CarLoanResponses.FINAL_CHECK_PASS_CONDICTION.getCode(),
				CarLoanSteps.RATE_CHECK.getName());
		responsMap.put(CarLoanResponses.TO_AUDIT_CONTRACT.getCode(),
				CarLoanSteps.CONTRACT_CHECK.getName());
		map.put(CarLoanSteps.FINAL_AUDIT.getName(), responsMap);
		// 审核费率
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_SIGN.getCode(),
				CarLoanSteps.SIGN.getName());
		map.put(CarLoanSteps.RATE_CHECK.getName(), responsMap);
		// 审核费率
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_MAKE_CONTRACT.getCode(),
				CarLoanSteps.CONTRACT_PRODUCT.getName());
		map.put(CarLoanSteps.SIGN.getName(), responsMap);
		// 合同制作
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_UPLOAD_CONTRACT.getCode(),
				CarLoanSteps.CONTRACT_UPLOAD.getName());
		map.put(CarLoanSteps.CONTRACT_PRODUCT.getName(), responsMap);
		// 合同签约上传
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.SUPPLY_SEC_AUDIT.getCode(),
				CarLoanSteps.RECHECK_AUDIT.getName());
		responsMap.put(CarLoanResponses.TO_AUDIT_CONTRACT.getCode(),
				CarLoanSteps.CONTRACT_CHECK.getName());
		map.put(CarLoanSteps.CONTRACT_UPLOAD.getName(), responsMap);
		// 合同审核
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_GRANT_CONFIRM.getCode(),
				CarLoanSteps.GRANT_CONFIRMED.getName());
		map.put(CarLoanSteps.CONTRACT_CHECK.getName(), responsMap);
		// 款项确认
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_ALLOT_CARD.getCode(),
				CarLoanSteps.CARD_NUMBER.getName());
		map.put(CarLoanSteps.GRANT_CONFIRMED.getName(), responsMap);
		// 分配卡号
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_GRANT.getCode(),
				CarLoanSteps.GRANT.getName());
		map.put(CarLoanSteps.CARD_NUMBER.getName(), responsMap);
		// 放款
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_GRANT_AUDIT.getCode(),
				CarLoanSteps.GRANT_AUDIT.getName());
		map.put(CarLoanSteps.GRANT.getName(), responsMap);
		// 放款审核
		responsMap = new HashMap<String, String>();
		responsMap.put(CarLoanResponses.TO_CREDIT_SEND.getCode(),
				CarLoanSteps.CREDITOR_RIGHT.getName());
		map.put(CarLoanSteps.GRANT_AUDIT.getName(), responsMap);
	}

	/**
	 * 提交下一步時確定是否需要清空排序标红
	 * 如果当前节点和firstBackSourceStep的值相同则返回true
	 * ，否则false
	 * 
	 * @param sourceStepName
	 *            提交下一步时的操作节点名称
	 * @param response
	 *            提交下一步时选择的response 的code
	 * @param firstBackSourceStep
	 *            PE中存放的第一次退回时源节点的名称
	 * @return
	 */
	public static boolean needCleanSortAndRedFlagWhenCommit(
			String sourceStepName, String response, String firstBackSourceStep) {
		boolean cleanOrnot = false;
		/*
		 * 早期实现：根据当前操作节点名称和response值确定下一步节点的名称，如果该名称和firstBackSourceStep的值相同则返回true 
		Map<String, String> responseMap = map.get(sourceStepName);
		if (responseMap != null) {
			String targetStepName = responseMap.get(response);
			if (targetStepName != null && !"".equals(targetStepName)) {
				if (targetStepName.equals(firstBackSourceStep)) {
					cleanOrnot = true;
				}
			}
		}*/
		if(sourceStepName.equals(firstBackSourceStep)){
			cleanOrnot = true;
		}
		return cleanOrnot;
	}
	/**
	 * 提交下一步時確定是否需要清空排序标红
	 * 如果当前节点和firstBackSourceStep的值相同则返回true，否则false
	 * 
	 * @param sourceStepName
	 *            提交下一步时的操作节点名称
	 * @param firstBackSourceStep
	 *            PE中存放的第一次退回时源节点的名称
	 * @return
	 */
	public static boolean needCleanSortAndRedFlagWhenCommit(
			String sourceStepName, String firstBackSourceStep) {
		boolean cleanOrnot = false;
		
		if(sourceStepName.equals(firstBackSourceStep)){
			cleanOrnot = true;
		}
		return cleanOrnot;
	}

}
