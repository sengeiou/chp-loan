package com.creditharmony.loan.common.workFlow.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;

/**
 * CHP 通过AOP登录 调用工作流
 * @author 任志远
 * @date 2016年12月8日
 */
@Service
public class FlowServiceCall {

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;

	public void launchFlow(WorkItemView workItem) {
		flowService.launchFlow(workItem);
	}
}
