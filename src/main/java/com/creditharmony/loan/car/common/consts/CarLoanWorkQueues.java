package com.creditharmony.loan.car.common.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 车借WorkQueue
 * @Class Name CarLoanWorkQueues
 * @author 陈伟东
 * @Create In 2016年2月17日
 */
public enum CarLoanWorkQueues {

	HJ_CAR_APPRAISER("appraiser","HJ_CAR_APPRAISER"),															//评估师
	HJ_CAR_FACE_AUDIT("faceAudit","HJ_CAR_FACE_AUDIT"),															//面审(车)门店--申请
	HJ_CAR_FACE_AUDIT_FIRST_AUDIT("faceFirstAudit","HJ_CAR_FACE_AUDIT"),										//面审(车)门店--初审
	HJ_CAR_RATE_CHECK("rateCheck","HJ_CAR_RATE_CHECK"),															//利率审核(车)
	HJ_CAR_CONTRACT_COMMISSIONER("contractCommissioner","HJ_CAR_CONTRACT_COMMISSIONER"),						//合同制作专员(车)借款人服务部
	HJ_CAR_CONTRACT_CHECK("contractCheck","HJ_CAR_CONTRACT_CHECK"),												//合同审核(车)
	HJ_CAR_STATISTICS_COMMISSIONER("statisticsCommissioner","HJ_CAR_STATISTICS_COMMISSIONER"),					//款项统计专员(车)借款人服务部
	HJ_CAR_LOAN_BALANCE_COMMISSIONER("balanceCommissioner","HJ_CAR_LOAN_BALANCE_COMMISSIONER"),					//放款结算专员(车)数据管理部
	HJ_CAR_DEDUCTION_COMMISSIONER("deductionCommissioner", "HJ_CAR_DEDUCTION_COMMISSIONER"), // 划扣专员(车)数据管理部
	HJ_CAR_LOAN_BALANCE_MANAGER("balanceManager", "HJ_CAR_LOAN_BALANCE_MANAGER"), // 面审(车)门店--初审
	HJ_CAR_EXTEND_RATE_CHECK("rateCheck", "HJ_CAR_RATE_CHECK"), // 利率审核(车)
	HJ_CAR_EXTEND_CONTRACT_COMMISSIONER("contractCommissioner", "HJ_CAR_CONTRACT_COMMISSIONER"), // 合同制作专员(车)借款人服务部
	HJ_CAR_EXTEND_CONTRACT_CHECK("contractCheck", "HJ_CAR_CONTRACT_CHECK");						//放款结算主管(车)

	private static Map<String, CarLoanWorkQueues> workQueueMap = new HashMap<String, CarLoanWorkQueues>(
			100);
	private static Map<String, CarLoanWorkQueues> codeMap = new HashMap<String, CarLoanWorkQueues>(
			100);

	static {
		CarLoanWorkQueues[] allValues = CarLoanWorkQueues.values();
		for (CarLoanWorkQueues obj : allValues) {
			workQueueMap.put(obj.getWorkQueue(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String workQueue;
	private String code;

	private CarLoanWorkQueues(String code, String workQueue) {
		this.workQueue = workQueue;
		this.code = code;

	}

	public String getWorkQueue() {
		return workQueue;
	}
	public void setWorkQueue(String workQueue) {
		this.workQueue = workQueue;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public static CarLoanWorkQueues parseByWorkqueueName(String workQueue) {
		return workQueueMap.get(workQueue);
	}

	public static CarLoanWorkQueues parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.workQueue;
	}
}
