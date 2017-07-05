package com.creditharmony.loan.car.carExtend.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * 车借WorkQueue
 * @Class Name CarExtendWorkQueues
 * @author 陈伟东
 * @Create In 2016年2月17日
 */
public enum CarExtendWorkQueues{

	HJ_CAR_EXTEND_RECEIVED("extendReceived","HJ_CAR_EXTEND_RECEIVED"),											//展期--回款岗
	HJ_CAR_EXTEND_APPRAISER("extendAppraiser","HJ_CAR_EXTEND_APPRAISER"),										//展期--评估师
	HC_CAR_EXTEND_RECHECK("extendRecheck","HC_CAR_EXTEND_RECHECK"),												//展期--汇诚复审
	HC_CAR_EXTEND_FINAL_CHECK("extendFinalRecheck","HC_CAR_EXTEND_FINAL_CHECK"),								//展期--汇诚终审
	HJ_CAR_EXTEND_CONTRACT_COMMISSIONER("extendContractCommissioner","HJ_CAR_EXTEND_CONTRACT_COMMISSIONER"),	//展期--合同制作专员(车)借款人服务部-合同制作
	HJ_CAR_EXTEND_CONTRACT_COMMISSIONER_RATE_CHECK("extendContractCommissionerRateCheck","HJ_CAR_EXTEND_CONTRACT_COMMISSIONER"),	//展期--合同制作专员(车)借款人服务部-利率审核
	HJ_CAR_EXTEND_CONTRACT_CHECK("extendContractCheck","HJ_CAR_EXTEND_CONTRACT_CHECK");							//展期--合同审核(车)

	private static Map<String, CarExtendWorkQueues> workQueueMap = new HashMap<String, CarExtendWorkQueues>(
			100);
	private static Map<String, CarExtendWorkQueues> codeMap = new HashMap<String, CarExtendWorkQueues>(
			100);

	static {
		CarExtendWorkQueues[] allValues = CarExtendWorkQueues.values();
		for (CarExtendWorkQueues obj : allValues) {
			workQueueMap.put(obj.getWorkQueue(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String workQueue;
	private String code;

	private CarExtendWorkQueues(String code, String workQueue) {
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

	public static CarExtendWorkQueues parseByWorkqueueName(String workQueue) {
		return workQueueMap.get(workQueue);
	}

	public static CarExtendWorkQueues parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.workQueue;
	}
}
