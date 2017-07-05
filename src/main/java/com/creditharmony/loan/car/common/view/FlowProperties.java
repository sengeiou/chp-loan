package com.creditharmony.loan.car.common.view;

import java.io.Serializable;

/**
 * 定制加载的流程属性，参见WorkItemView中flowProperties的用法。
 * @author chenwd
 *
 */
public class FlowProperties implements Serializable{
	
	private static final long serialVersionUID = -4040092645920508344L;

	public static final String FIRST_BACK_SOURCE_STEP_DEAULT_VALUE = "NO";

	/**
	 * 合同超时关闭业务所需
	 * 合同超时检测阶段
	 * 0：没有进行利率审核；
	 * 1：完成第一次利率审核
	 */
	private Integer timeoutCheckStage;
	
	/**
	 * 退回标红置顶业务所需
	 * 第一次退回的源节点名称
	 * 默认值NO
	 */
	private String firstBackSourceStep;
	

	public Integer getTimeoutCheckStage() {
		return timeoutCheckStage;
	}

	public void setTimeoutCheckStage(Integer timeoutCheckStage) {
		this.timeoutCheckStage = timeoutCheckStage;
	}

	public String getFirstBackSourceStep() {
		return firstBackSourceStep;
	}
	public void setFirstBackSourceStep(String firstBackSourceStep) {
		this.firstBackSourceStep = firstBackSourceStep;
	}
	
}
