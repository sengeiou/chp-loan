package com.creditharmony.loan.car.carGrant.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.car.carGrant.ex.CarDistachParamEx;
import com.creditharmony.loan.car.carGrant.ex.CarParamEx;
import com.creditharmony.loan.car.carGrant.service.CarGrantDeductsService;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarGrantCommon;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.query.ProcessQueryBuilder;
	
/**
 * 放款审核进行处理
 * @Class Name CarGrantAuditController
 * @Create In 2016年3月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/grant/grantAudit")
public class CarGrantAuditController extends BaseController {

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private CarGrantDeductsService carGrantDeductsService;

	/**
	 * 放款审核跳转,单个或者批量操作都可以进行 2016年2月25日 
	 * 
	 * @param model
	 * @param checkVal
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "grantAuditJump")
	public String granAuditJump(Model model, String checkVal) {
	
		TaskBean taskBean = new TaskBean();
		List<CarLoanFlowWorkItemView> workItems=new ArrayList<CarLoanFlowWorkItemView>();
		// 根据applyId查询页面中显示放款确认的字段
		String[] apply = null;
	
		try {
			if (StringUtils.isNotEmpty(checkVal)) {
				if (checkVal.indexOf(",") != CarGrantCommon.ONE) {
					apply = checkVal.split(",");
				} else {
					apply = new String[1];
					apply[0] = checkVal;
				}
				// 查询待办列表，获得流程属性
	
				ProcessQueryBuilder param = new ProcessQueryBuilder();
				param.put("applyId", apply);
				taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER
						.getWorkQueue(), param,
						CarLoanFlowWorkItemView.class);
				workItems = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
			}else{
				ProcessQueryBuilder param = new ProcessQueryBuilder();
				taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER
						.getWorkQueue(), param,
						CarLoanFlowWorkItemView.class);
				workItems = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
				
			}
			if (ArrayHelper.isNotEmpty(workItems) && workItems.size() > 0) {
				for (int i = 0; i < workItems.size(); i++) {
					//将是否电销的码值转换为汉字
					workItems.get(i).setLoanIsPhone(DictCache.getInstance().getDictLabel("jk_telemarketing", workItems.get(i).getLoanIsPhone()));
					workItems.get(i).setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", workItems.get(i).getLoanFlag()));
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("list", workItems);
		return "car/grant/carLoanflow_grantAudit_approve_0";
	}

	/**
	 * 审核退回处理 2016年2月25日 By 
	 * 
	 * @param apply
	 * @param result
	 * @param responseURL
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "grantAuditBack")
	public String granAuditBack(CarParamEx param, String dictBackMestype,String remark) {
		List<CarDistachParamEx> list = param.getList();
		if (ArrayHelper.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					// 对要进行审核的单子进行判断处理，如果单子的催收服务费的划扣状态为处理中，则给出提示，return
					// false,如果存在就进行return
					String dealStatus = carGrantDeductsService.getDealCount(list.get(i)
							.getContractCode());
					if (UrgeCounterofferResult.PROCESS.getCode().equals(dealStatus)) {
						return CarGrantCommon.URGE_DEAL;
					}
					if (UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode().equals(dealStatus)) {
						return GrantCommon.CHECK_DEAL;
					}
			    	WorkItemView workItem = list.get(i).getWorkItemView();
			    	workItem.setResponse(CarLoanResponses.BACK_GRANT_CONFIRM.getCode());
					CarGrantDealView gqp = new CarGrantDealView();
					gqp.setApplyId(list.get(i).getApplyId());
					gqp.setContractCode(list.get(i).getContractCode());
					gqp.setLoanCode(list.get(i).getLoanCode());
					// 设置单子状态，放款退回
					gqp.setDictLoanStatus(CarLoanStatus.LOAN_BACK.getCode());
					gqp.setDictBackMestype(dictBackMestype);
					gqp.setRemark(remark);
					// 设置审核专员
					gqp.setCheckEmpId(UserUtils.getUser().getId());
					// 设置审核结果

					gqp.setCheckResult(CarLoanResult.BACK.getCode());
					// 设置审核时间
					gqp.setCheckTime(new Date());
					workItem.setBv(gqp);
					//放款审核阶段特殊，通过底层接口获取标红置顶相关参数
					WorkItemView workItemView = flowService.loadWorkItemView(list.get(i).getApplyId(), workItem.getWobNum(), workItem.getToken());
					workItem.setFlowProperties(workItemView.getFlowProperties());
					try {
					flowService.dispatch(workItem);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			return BooleanType.TRUE;
		} else {
			return BooleanType.FALSE;
		}
	}

	/**
	 * 放款审核通过，更新数据 2016年2月25日 
	 * 
	 * @param CarParamEx  用来接收流程属性、借款编码、合同编号
	 *            
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "grantAuditOver")
	public String granAuditOver(CarParamEx param, Date result) {
		List<CarDistachParamEx> list = param.getList();
		if (ArrayHelper.isNotEmpty(list)) {
			
				for (int i = 0; i < list.size(); i++) {
					
			    	WorkItemView workItem = list.get(i).getWorkItemView();
			    	workItem.setResponse(CarLoanResponses.TO_CREDIT_SEND.getCode());
					CarGrantDealView gqp = new CarGrantDealView();
					gqp.setApplyId(list.get(i).getApplyId());
					gqp.setContractCode(list.get(i).getContractCode());
					gqp.setLoanCode(list.get(i).getLoanCode());
					// 设置单子状态，放款退回
					gqp.setDictLoanStatus(CarLoanStatus.REPAYMENT_IN.getCode());
					
					// 跟新放款时间
					gqp.setLendingTime(result);
					// 设置审核专员
					gqp.setCheckEmpId(UserUtils.getUser().getId());
					// 设置审核结果

					gqp.setCheckResult(CarLoanResult.THROUGH.getCode());
					// 设置审核时间
					gqp.setCheckTime(new Date());
					workItem.setBv(gqp);
					//放款审核阶段特殊，通过底层接口获取标红置顶相关参数
					WorkItemView workItemView = flowService.loadWorkItemView(list.get(i).getApplyId(), workItem.getWobNum(), workItem.getToken());
					workItem.setFlowProperties(workItemView.getFlowProperties());
					try {
					flowService.dispatch(workItem);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			return BooleanType.TRUE;
		} else {
			return BooleanType.FALSE;
		}

	}

}
