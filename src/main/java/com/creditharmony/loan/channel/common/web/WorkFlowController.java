package com.creditharmony.loan.channel.common.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.query.ProcessQueryBuilder;

/**
 * 金信工作流处理共通
 * @Class Name WorkFlowController
 * @author xiaoniu.hu
 * @Create In 2016年2月22日
 */
public abstract class WorkFlowController extends BaseController{
	/**
	 * FlowService 查询流程待办列表、提交流程 
	 */
	@Resource(name="appFrame_flowServiceImpl")
	protected FlowService flowService;
	
	/**
	 * 获取待办列表
	 * 2016年2月22日
	 * xiaoniu.hu
	 * @param model
	 * @param queryParam 查询条件
	 * @return
	 */
	protected abstract String fetchTaskItems(Model model,LoanFlowQueryParam queryParam,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据条件查询指定队列下所有的借款
	 * 2016年3月6日
	 * xiaoniu.hu
	 * @param queryParam 查询条件
	 * @param queueName 队列名称
	 * @return 借款编号集合
	 */
	@SuppressWarnings("unchecked")
	protected List<String> getAllLoanCodes(ProcessQueryBuilder queryParam,String queueName){
		List<String> list=new ArrayList<String>();
		List<LoanFlowWorkItemView> workItems = null;
		TaskBean taskBean = flowService.fetchTaskItems(queueName, queryParam,LoanFlowWorkItemView.class);
		if (taskBean != null) {
			workItems = (List<LoanFlowWorkItemView>) taskBean.getItemList();
			for(LoanFlowWorkItemView item : workItems) {
				list.add(item.getLoanCode());
			}
		}
		return list;
	}
	/**
     *将流程中查询出来的数据类型进行转封装 
     *@author zhanghao
     *@Create In 2016年2月1日
     *@param  sourceWorkItems
     *@return List<LoanFlowWorkItemView> 
     */
	protected List<LoanFlowWorkItemView> convertList(List<BaseTaskItemView> sourceWorkItems) {
		List<LoanFlowWorkItemView> targetList = new ArrayList<LoanFlowWorkItemView>();
		if (!ObjectHelper.isEmpty(sourceWorkItems)) {
			for (BaseTaskItemView currItem : sourceWorkItems)
				targetList.add((LoanFlowWorkItemView) currItem);
		}
		return targetList;
	}
}
