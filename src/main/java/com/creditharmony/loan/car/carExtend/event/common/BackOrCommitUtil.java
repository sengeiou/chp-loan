package com.creditharmony.loan.car.carExtend.event.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.car.carExtend.view.CarExtendBaseBusinessView;
import com.creditharmony.loan.car.common.util.BackAndRedTopUtil;
import com.creditharmony.loan.car.common.view.FlowProperties;

/**
 * 展期流程提交或者退回时处理标红置顶工具类
 * 
 * @author chenwd
 * 
 */
public class BackOrCommitUtil {

	/**
	 * 退回时处理标红置顶业务内容
	 * 更新排序字段和相应操作时间，如果为初次退回操作则记录原始退回节点
	 * @param workItem
	 * @param flowView
	 * @param currStepName	当前节点名称
	 */	
	public static void redTopBack(WorkItemView workItem,
			CarExtendBaseBusinessView flowView,String currStepName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
					|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
							.equals(flowProperties.getFirstBackSourceStep())) {
				flowView.setFirstBackSourceStep(currStepName);
			}
			flowView.setOrderField("0," + sdf.format(new Date()));
		}
	}

	/**
	 * 提交时处理标红置顶业务内容
	 * 回到原退回节点则重置FirstBackSourceStep，同时设置为提交排序和提交时间，
	 * 否则根据FirstBackSourceStep是否为初始值确定排序和操作时间
	 * @param workItem
	 * @param flowView
	 * @param currStepName	当前节点名称
	 */
	public static void redTopCommit(WorkItemView workItem,
			CarExtendBaseBusinessView flowView,String currStepName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Object object = workItem.getFlowProperties();
		if (object != null) {
			FlowProperties flowProperties = (FlowProperties) object;
			if (BackAndRedTopUtil.needCleanSortAndRedFlagWhenCommit(
					currStepName,
					flowProperties.getFirstBackSourceStep())) {
				flowView.setFirstBackSourceStep(FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE);
				flowView.setOrderField("1," + sdf.format(new Date()));
			} else {
				if (StringUtils.isEmpty(flowProperties.getFirstBackSourceStep())
						|| FlowProperties.FIRST_BACK_SOURCE_STEP_DEAULT_VALUE
								.equals(flowProperties.getFirstBackSourceStep())) {
					flowView.setOrderField("1," + sdf.format(new Date()));
				} else {
					flowView.setOrderField("0," + sdf.format(new Date()));
				}
			}
		}
	}
}
