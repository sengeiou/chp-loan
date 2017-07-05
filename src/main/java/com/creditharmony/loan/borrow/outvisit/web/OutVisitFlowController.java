package com.creditharmony.loan.borrow.outvisit.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.RejectDepartment;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Area;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.service.OrgManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.file.util.Zip;
import com.creditharmony.loan.borrow.applyinfo.entity.Customer;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.OutsideTaskList;
import com.creditharmony.loan.borrow.outvisit.service.OutVisitService;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowQueue;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.common.workFlow.view.LoanFlowDictEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.creditharmony.loan.common.workFlow.view.OutsideCheckWFInfo;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.OrgInfoService;
import com.creditharmony.loan.users.service.UserInfoService;
import com.creditharmony.loan.utils.EncryptUtils;
import com.google.common.collect.Lists;
import com.query.ProcessAndQueryBuilderContainer;
import com.query.ProcessQueryBuilder;

/**
 * 借款工作流 控制层
 * 
 * @Class Name LoanWorkFlowController
 * @author 张进
 * @Create In 2015年12月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/workFlow")
public class OutVisitFlowController extends BaseController {

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;

	@Autowired
	private LoanPrdMngService svc;

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private OrgInfoService orgInfoService;

	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;

	@Autowired
	private OutVisitService outVisitService;

	@Autowired
	private UserManager userManager;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private OrgManager orgManager;
	
	public static String homeVisitFlag = "0";// 外访信息包含家庭
	public static String workVisitFlag = "1";// 外访信息包含工作单位
	public static String companyVisitFlag = "2"; // 外访信息包含企业
	

	/**
	 * 获取待办任务列表 (外访) 2016年1月22日 By 王彬彬
	 * 
	 * @param model
	 *            传输model
	 * @param queryParam
	 *            查询条件
	 * @param queue
	 *            工作队列
	 * @param viewName
	 *            视图名称
	 * @param redirectAttributes
	 *            重定向属性
	 * @return 返回页面
	 */
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "getTaskItems")
	public String getTaskItems(Model model, LoanFlowQueryParam queryParam,
			String queue, String viewName, RedirectAttributes redirectAttributes) {
		ProcessQueryBuilder qryParam = new ProcessQueryBuilder();
		
		if (LoanFlowQueue.VISIT_COMMISSIONER.equals(queue)) {
			queryParam.setVisitUserId(UserUtils.getUser().getId());
		}

		try {
			List workItems = new ArrayList();

			if (!ObjectHelper.isEmpty(queryParam)) {
				ReflectHandle.copy(queryParam, qryParam); // 对象转换
			}
			qryParam.put("storeOrgId", UserUtils.getUser().getDepartment().getId());
			
			if (LoanFlowQueue.SUB_MANAGER.equals(queue)
					|| LoanFlowQueue.VISIT_COMMISSIONER.equals(queue)) {
				logger.info("world 查询外访分配待办或外访任务待办时的departmentId："+UserUtils.getUser().getDepartment().getId());
				TaskBean taskBean = flowService.fetchTaskItems(queue, qryParam,
						LoanFlowWorkItemView.class);
				logger.info("world 查询外访分配待办或外访任务待办时的departmentId："+UserUtils.getUser().getDepartment().getId());
				workItems = (List<LoanFlowWorkItemView>) taskBean.getItemList();
				for(LoanFlowWorkItemView lf: (List<LoanFlowWorkItemView>)workItems){
					String channelCodeLabel=DictCache.getInstance()
							.getDictLabel("jk_channel_flag",lf.getChannelCode());
					lf.setChannelCodeLabel(channelCodeLabel);
				}
			}
			model.addAttribute("workItems", workItems);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "外访任务获取失败");
			logger.error("外访任务获取失败");
		}

		try {
			List<LoanPrdMngEntity> prdList = new ArrayList<LoanPrdMngEntity>();
			LoanPrdMngEntity selParam = new LoanPrdMngEntity();
			prdList = svc.selPrd(selParam);
			model.addAttribute("prdList", prdList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品列表获取失败");
		}

		model.addAttribute("viewName", viewName);
		model.addAttribute("queue", queue);
		model.addAttribute("queryParam", queryParam);

		return "borrow/workFlow/" + viewName;
	}*/
	/**
	 * 重写外访分派待办列表和外访任务待办列表controller
	 */
	@RequestMapping(value = "getTaskItems")
	public String getTaskItems(Model model, LoanFlowQueryParam queryParam,
			String queue, String viewName, RedirectAttributes redirectAttributes) {
		ProcessQueryBuilder qryParamFirst = new ProcessQueryBuilder();
		ProcessQueryBuilder qryParamSecond = new ProcessQueryBuilder();
		ProcessAndQueryBuilderContainer paramContainer = new ProcessAndQueryBuilderContainer();
		if (!ObjectHelper.isEmpty(queryParam)) {
			try {
				ReflectHandle.copy(queryParam, qryParamFirst);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "外访待办列表查询条件失效");
				logger.error("外访待办列表查询条件失效");
			}
		}
		
		if(LoanFlowQueue.SUB_MANAGER.equals(queue)){ //外访分派待办列表
			String orgId=UserUtils.getUser().getDepartment().getId();
			//查询组织机构下所有门店
			List<OrgInfo> orgInfoList=orgInfoService.queryDepartmentByOrgId(orgId);
			Set<String> set=new HashSet<String>();
			for (OrgInfo orgInfo : orgInfoList) {
				set.add(orgInfo.getId());
			}
			String[] str=set.toArray(new String[set.size()]);
			if(str!=null && str.length>0){
				qryParamSecond.put("storeOrgId", str);
			}else{
				qryParamSecond.put("storeOrgId", "");
			}
		}else if (LoanFlowQueue.VISIT_COMMISSIONER.equals(queue)) { //外访任务待办列表
			qryParamSecond.put("visitUserId", UserUtils.getUser().getId());
		}
		
		paramContainer.addQueryBuilder(qryParamFirst);
		paramContainer.addQueryBuilder(qryParamSecond);
		
		TaskBean taskBean = flowService.fetchTaskItems(queue, paramContainer,LoanFlowWorkItemView.class);
		@SuppressWarnings("unchecked")
		List<LoanFlowWorkItemView> workItems = (List<LoanFlowWorkItemView>) taskBean.getItemList();
		for(LoanFlowWorkItemView lf: (List<LoanFlowWorkItemView>)workItems){
			String channelCodeLabel=DictCache.getInstance().getDictLabel("jk_channel_flag",lf.getChannelCode());
			lf.setChannelCodeLabel(channelCodeLabel);
		}
		model.addAttribute("workItems", workItems);
		
		try {
			List<LoanPrdMngEntity> prdList = new ArrayList<LoanPrdMngEntity>();
			LoanPrdMngEntity selParam = new LoanPrdMngEntity();
			prdList = svc.selPrd(selParam);
			model.addAttribute("prdList", prdList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品列表获取失败");
		}

		model.addAttribute("viewName", viewName);
		model.addAttribute("queue", queue);
		model.addAttribute("queryParam", queryParam);

		return "borrow/workFlow/" + viewName;
	}
	/**
	 * 分配外访任务 2016年1月21日 By 王彬彬
	 * 
	 * @param model
	 * @param applyIds
	 *            申请ID
	 * @param outSideUserCode
	 *            外访员ID
	 * @param redirectAttributes
	 *            重定向
	 * @return 返回页面
	 */
	@RequestMapping(value = "allocationOutTask")
	public String allocationOutTask(Model model, String applyIds,
			String outSideUserCode, RedirectAttributes redirectAttributes)
			throws Exception {
		//判断是否有已经分配的单子，如果有则提示并刷新列表
		for (String applyIdsList : applyIds.split(",")) {
			String[] param = applyIdsList.split(";");
			String loanCode = param[3];
			LoanInfo loanInfo = applyLoanInfoService.selectByLoanCode(loanCode);
			if(!loanInfo.getDictLoanStatus().equals(LoanApplyStatus.STORE_ALLOT_VISIT.getCode()) && !loanInfo.getDictLoanStatus().equals(LoanApplyStatus.RECONSIDER_STORE_VISIT.getCode())){
				addMessage(redirectAttributes, "有外访任务已被其他人分配，请重新选择外访任务！");
				return "redirect:"+ adminPath+ "/loan/workFlow/getTaskItems?queue=HJ_SUB_MANAGER&viewName=loanflow_outside_workItems";
			}
		}
		
		logger.info("分配外访任务开始");

		try {
			WorkItemView workItem = new WorkItemView();
			LaunchView bv = new LaunchView();
			for (String applyIdsList : applyIds.split(",")) {
				String[] param = applyIdsList.split(";");
				workItem = new WorkItemView();
				bv = new LaunchView();

				workItem.setWobNum(param[0]);
				workItem.setToken(param[2]);

				bv.setApplyId(param[1]);
				bv.setOutSideUserCode(outSideUserCode);
				bv.setOutSideUserName(userManager.get(outSideUserCode)
						.getName());
				bv.setDictLoanStatus(LoanApplyStatus.STORE_VISIT.getName());
				bv.setDictLoanStatusCode(LoanApplyStatus.STORE_VISIT.getCode());

				LoanInfo info = new LoanInfo();
				info.setDictLoanStatus(LoanApplyStatus.STORE_VISIT.getCode());
				info.setLoanCode(param[3]);
				info.setLoanSurveyEmpId(outSideUserCode);// 外访人员
				logger.info("world 分配外访任务的外访专员id："+outSideUserCode);
				logger.info("world 分配外访任务的外访专员id："+bv.getOutSideUserCode());
				logger.info("world 分配外访任务的外访专员id："+info.getLoanSurveyEmpId());

				bv.setLoanInfo(info);
				workItem.setBv(bv);

				// 调用流程方法
				flowService.dispatch(workItem);

				addMessage(redirectAttributes, "外访分配成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "外访分配异常，请重新分配！");
			logger.error("分配外访任务异常");
		}
		return "redirect:"+ adminPath+ "/loan/workFlow/getTaskItems?queue=HJ_SUB_MANAGER&viewName=loanflow_outside_workItems";
	}
	/**
	 * 查看外访任务信息 
	 */
	@RequestMapping(value = "loadOutVisitTaskList")
	public String loadOutVisitTaskList(Model model, String loanCode)
			throws Exception {
		OutsideTaskList outsideTaskList = outVisitService.selectOutsideTaskListByLoanCode(loanCode);
		LoanOutsideTaskInfo loanOutsideTaskInfo = outVisitService.selectOutsideTaskInfoByTaskId(outsideTaskList.getId());
		model.addAttribute("taskInfo", loanOutsideTaskInfo);
		return "borrow/workFlow/outVisitTaskList";
	}

	/**
	 * 分配外访任务
	 * 
	 * @param model
	 *            Model对象
	 * @param applyIds
	 *            任务信息拼装字符串
	 * @param outSideUserCode
	 *            外访员员工编号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loadOutTaskInfo")
	public String loadOutTaskInfo(Model model, WorkItemView workItem)
			throws Exception {
		logger.info("分配外访任务");
		String applyId = workItem.getFlowId();
		LoanInfo loanInfo = applyLoanInfoService.selectByApplyId(applyId);
		model.addAttribute("workItem", workItem);
		model.addAttribute("loanInfo", loanInfo);
		return "borrow/apply/outsideTaskUploadInfo";
	}

	/**
	 * 外访任务上传
	 * 
	 * @param model
	 *            Model对象
	 * @param workItem
	 *            任务工作项视图类对象
	 * @param bv
	 *            工作流传递参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "outTaskUpload")
	public String outTaskUpload(Model model, WorkItemView workItem,
			LaunchView bv) {
		logger.info("外访任务上传");
		try {
			LoanInfo info = new LoanInfo();
			info.setDictLoanStatus(LoanApplyStatus.VISIT_FINISH_CHECK.getCode());
			bv.setLoanInfo(info);
			workItem.setBv(bv);

			// 调用流程方法
			flowService.dispatch(workItem);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("", "");
			logger.error("外访任务上传失败");
		}

		return "redirect:"
				+ adminPath
				+ "/loan/workFlow/getTaskItems?queue=HJ_VISIT_COMMISSIONER&viewName=loanflow_outsideUp_workItems";
	}

	/**
	 * 加载门店外访员分配外访任务及查询
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @param model
	 *            Model对象
	 * @param user
	 *            查询
	 * @return 外访人员列表
	 */
	@RequestMapping(value = "getOutPeopleList")
	public String getOutPeopleList(HttpServletRequest request,
			HttpServletResponse response, Model model, UserInfo user) {
		try {
			Map<String, String> userMap = new HashMap<String, String>();
			String departmentId = UserUtils.getUser().getDepartment().getId();
			userMap.put("departmentId", departmentId);// 部门id
			userMap.put("roleId", LoanRole.VISIT_PERSON.id);// 外访

			userMap.put("name", user.getName());
			userMap.put("userCode", user.getUserCode());
			userMap.put("departmentName", user.getDepartmentName());

			// 传递数据到前台页面展示
			Page<UserInfo> userlist = userInfoService.getOutPeopleList(
					new Page<UserInfo>(request, response), userMap);
			model.addAttribute("page", userlist);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "外访人员获取失败，请重新检索！！");
			logger.error("外访人员获取失败，系统异常！");
		}
		model.addAttribute("user", user);
		return "borrow/workFlow/outPeopleList";
	}

	/**
	 * 获取客户基本信息 2016年1月25日 By 王彬彬
	 * 
	 * @param model
	 *            model对象
	 * @param applyId
	 *            申请ID
	 * @param loanCode
	 *            借款编号
	 * @param wobNum
	 *            工作流用WobNum
	 * @param token
	 *            工作流令牌
	 * @return 外访办理页面
	 */
	@RequestMapping(value = "getLoanDetail")
	public String getLoanInfo(Model model, String applyId, String loanCode, String wobNum, String token, String queue, String viewName) {
		try {
			Map<String, String> mapCob = new HashMap<String, String>();
			Map<String, String> mapCustomer = new HashMap<String, String>();
			mapCustomer.put("loanCode", loanCode);
			mapCob.put("loanCode", loanCode);
			List<LoanInfo> loanList = outVisitService.findLoanByLoanCode(loanCode);
			for (LoanInfo loanInfo : loanList) {
				loanInfo.setDictLoanUserLabel(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
				loanInfo.setLoanUrgentFlagLabel(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfo.getLoanUrgentFlag()));
			}
			List<Customer> customerList = outVisitService.findCustomerByLoanCode(mapCustomer);
			for (Customer customer : customerList) {
				customer.setDictCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", customer.getDictCertType()));
				customer.setDictCustomerLoanTypeLabel(DictCache.getInstance().getDictLabel("jk_customer_diff", customer.getDictCustomerLoanType()));
				customer.setDictEducationLabel(DictCache.getInstance().getDictLabel("jk_degree", customer.getDictEducation()));
				customer.setDictMarryLabel(DictCache.getInstance().getDictLabel("jk_marriage", customer.getDictMarry()));
				customer.setCustomerSexLabel(DictCache.getInstance().getDictLabel("jk_sex", customer.getCustomerSex()));
			}
			List<LoanCoborrower> cobList = outVisitService.findCoBorrowerByApplyId(mapCob);
			for (LoanCoborrower loanCoborrower : cobList) {
				loanCoborrower.setCoboSexName(DictCache.getInstance().getDictLabel("jk_sex", loanCoborrower.getCoboSex()));
				loanCoborrower.setDictCertTypeName(DictCache.getInstance().getDictLabel("com_certificate_type", loanCoborrower.getDictCertType()));
			}
			//自然人保证人解密
			EncryptUtils.decryptMulti(cobList);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("loanCode", loanCode);
			List<OutsideTaskList> outsideTaskList = outVisitService.findFinishOutsideTaskListByLoanCode(param);
			
			model.addAttribute("outsideTaskList", outsideTaskList);
			model.addAttribute("customerList", customerList);
			model.addAttribute("loanList", loanList);
			model.addAttribute("cobList", cobList);
			String imageUrl = imageService.getImageUrl(FlowStep.OUT_VISIT.getName(), loanCode);
			model.addAttribute("imageUrl", imageUrl);
			model.addAttribute("loanInfoOldOrNewFlag", loanList.get(0).getLoanInfoOldOrNewFlag());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("外访详细获取失败");
		}

		model.addAttribute("applyId", applyId);
		model.addAttribute("wobNum", wobNum);
		model.addAttribute("token", token);
		model.addAttribute("queue", queue);
		model.addAttribute("viewName", viewName);
		model.addAttribute("loanCode", loanCode);

		return "borrow/workFlow/loanflow_outside_todo";
	}

	/**
	 * 外访办理 (完成、放弃) 2016年1月25日 By 王彬彬
	 * 
	 * @param model
	 * @param applyId
	 *            工作流ID
	 * @param wobNum
	 *            工作流用wobNum
	 * @param token
	 *            工作流令牌
	 * @param queue
	 *            工作流工作队列
	 * @param viewName
	 *            试图名
	 * @param loanCode
	 *            借款编号
	 * @param redirectAttributes
	 *            重定向试图
	 * @return 待办页面试图
	 */
	@RequestMapping(value = "doOutTask")
	public String doOutTask(Model model, String applyId, String wobNum,
			String token, String queue, String viewName, String loanCode,
			String itemDistance, String response, String remark,
			RedirectAttributes redirectAttributes) {
		logger.info("外访任务处理开始");
		try {
			LoanInfo info = new LoanInfo();

			WorkItemView workItem = new WorkItemView();
			LaunchView bv = new LaunchView();

			workItem.setWobNum(wobNum);
			workItem.setToken(token);
			if (LoanFlowRoute.COMPLETEOUTVISIT.equals(response)) {
				info.setDictLoanStatus(LoanApplyStatus.VISIT_FINISH_CHECK
						.getCode());
				// 外访完成（放弃，拒绝返回汇诚）
				workItem.setResponse(LoanFlowRoute.COMPLETEOUTVISIT);
			} else if (LoanFlowRoute.GIVEUP.equals(response)) {
				info.setDictLoanStatus(LoanApplyStatus.CUSTOMER_GIVEUP
						.getCode());
				info.setVisitFlag(RejectDepartment.LOAN_GIVE.getCode());
				// 外访完成（放弃，拒绝返回汇诚）
				workItem.setResponse(LoanFlowRoute.GIVEUP);
			} else if (LoanFlowRoute.REJECT.equals(response)) {
				info.setDictLoanStatus(LoanApplyStatus.STORE_REJECT.getCode());
				info.setRemark(remark == null ? "" : remark);
				info.setVisitFlag(RejectDepartment.LOAN_REJECT.getCode());
				// 外访完成（放弃，拒绝返回汇诚）
				workItem.setResponse(LoanFlowRoute.GIVEUP);
				
			}

			bv.setApplyId(applyId);
			bv.setDictLoanStatus(LoanApplyStatus.VISIT_FINISH_CHECK.getName());
			bv.setDictLoanStatusCode(LoanApplyStatus.VISIT_FINISH_CHECK
					.getCode());
			bv.setVisitFinishTime(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			bv.setOutSideUserCode(UserUtils.getUser().getUserCode());
			bv.setOutSideUserName(UserUtils.getUser().getName());
			bv.setItemDistance(new BigDecimal(itemDistance));

			info.setLoanCode(loanCode);

			bv.setLoanInfo(info);
			bv.setTimeOutFlag(YESNO.NO.getCode());
			
			workItem.setBv(bv);

			// 调用流程方法
			flowService.dispatch(workItem);

			addMessage(redirectAttributes, "外访办理成功！");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "外访任务处理异常，请重新办理！");
			logger.error("外访任务处理异常");
		}
		return "redirect:" + adminPath + "/loan/workFlow/getTaskItems?queue="
				+ queue + "&viewName=" + viewName;
	}

	/**
	 * 客户放弃(外访任务办理合并) 2016年3月3日 By 王彬彬
	 * 
	 * @param model
	 *            页面model
	 * @param applyId
	 *            工作流申请id
	 * @param wobNum
	 *            工作流用wobNum
	 * @param token
	 *            工作流用令牌
	 * @param queue
	 *            工作队列
	 * @param viewName
	 *            试图名
	 * @param loanCode
	 *            借款编号
	 * @param itemDistance
	 *            外放距离
	 * @param response
	 *            工作流路由
	 * @param redirectAttributes
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "customergiveup")
	public String customerGiveUp(Model model, String applyId, String wobNum,
			String token, String queue, String viewName, String loanCode,
			String itemDistance, String response,
			RedirectAttributes redirectAttributes) {
		logger.info("外访客户放弃");
		try {
			WorkItemView workItem = new WorkItemView();
			LaunchView bv = new LaunchView();
			workItem.setResponse(LoanFlowRoute.GIVEUP);
			workItem.setWobNum(wobNum);
			workItem.setToken(token);

			bv.setApplyId(applyId);
			bv.setDictLoanStatus(LoanApplyStatus.CUSTOMER_GIVEUP_CHECK
					.getName());
			bv.setDictLoanStatusCode(LoanApplyStatus.CUSTOMER_GIVEUP_CHECK
					.getCode());
			bv.setVisitFinishTime(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			bv.setOutSideUserCode(UserUtils.getUser().getUserCode());
			bv.setOutSideUserName(UserUtils.getUser().getName());
			bv.setItemDistance(new BigDecimal(itemDistance));

			LoanInfo info = new LoanInfo();
			info.setDictLoanStatus(LoanApplyStatus.CUSTOMER_GIVEUP_CHECK
					.getCode());
			info.setLoanCode(loanCode);

			bv.setLoanInfo(info);
			workItem.setBv(bv);

			// 调用流程方法
			flowService.dispatch(workItem);

			addMessage(redirectAttributes, "外访客户放弃成功！");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "外访客户放弃异常，请重新办理！");
			logger.error("外访客户放弃异常");
		}
		return "redirect:" + adminPath + "/loan/workFlow/getTaskItems?queue="
				+ queue + "&viewName=" + viewName;
	}

	/**
	 * 下载外访清单 2016年3月7日 By 王彬彬
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param loanCode
	 * @return
	 */
	@RequestMapping(value = "downLoadVisitTask")
	public void downLoadVisitTask(HttpServletRequest request,
			HttpServletResponse response, Model model, String applyId,
			String loanCode, String wobNum, String token, String queue,
			String viewName) {
		String type = outVisitService.selectVisitType(loanCode);
		
		if (StringHelper.isNotEmpty(type)) {
			String msg = StringUtils.EMPTY;
			String workUrl = Global.getConfig("loan.outvisit.workdetail.file"); // 工作单位现场考察表
			workUrl = workUrl.replace("{0}", loanCode);

			String homeUrl = Global.getConfig("loan.outvisit.homedetail.file"); // 实名家庭现场考察表
			homeUrl = homeUrl.replace("{0}", loanCode);

			String companyUrl = Global
					.getConfig("loan.outvisit.companydetail.file");// 实名企业现场考察表
			companyUrl = companyUrl.replace("{0}", loanCode);

			Map<String, InputStream> fileMap = new HashMap<String, InputStream>();

			InputStream workStream = null;
			InputStream homeStream = null;
			InputStream companyStrean = null;

			URL pUrl;
			URLConnection urlcon;
			if (type.contains(workVisitFlag)) {
				try {

					pUrl = new URL(workUrl);
					urlcon = pUrl.openConnection();
					urlcon.connect();// 获取连接
					workStream = urlcon.getInputStream();// 获取流
					fileMap.put("工作单位现场考察表.doc", workStream);
				} catch (IOException e1) {
					logger.error("工作单位现场考察表下载失败");
					msg = "工作单位现场考察表下载失败";
					e1.printStackTrace();
				}
			}

			if (type.contains(homeVisitFlag)) {
				try {
					pUrl = new URL(homeUrl);
					urlcon = pUrl.openConnection();
					urlcon.connect();// 获取连接
					homeStream = urlcon.getInputStream();// 获取流
					fileMap.put("实名家庭现场考察表.doc", homeStream);
				} catch (IOException e1) {
					logger.error("实名家庭现场考察表下载失败");
					if (StringUtils.isNotEmpty(msg)) {
						msg = msg + "<br>" + "实名家庭现场考察表下载失败";
					} else {
						msg = "实名家庭现场考察表下载失败";
					}
					e1.printStackTrace();
				}
			}

			if (type.contains(companyVisitFlag)) {
				try {
					pUrl = new URL(companyUrl);
					urlcon = pUrl.openConnection();
					urlcon.connect();// 获取连接
					companyStrean = urlcon.getInputStream();// 获取流
					fileMap.put("实名企业现场考察表.doc", companyStrean);
				} catch (IOException e1) {
					logger.error("实名企业现场考察表下载失败");
					if (StringUtils.isNotEmpty(msg)) {
						msg = msg + "<br>" + "实名企业现场考察表下载失败";
					} else {
						msg = "实名企业现场考察表下载失败";
					}
					e1.printStackTrace();
				}
			}

			try {
				response.setContentType("application/zip");
				response.addHeader(
						"Content-disposition",
						"attachment;filename="
								+ new String((loanCode + ".zip")
										.getBytes("gbk"), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			if (!ObjectHelper.isEmpty(fileMap)) {
				try {
					Zip.zipFiles(response.getOutputStream(), fileMap);
				} catch (IOException e) {
					logger.error("文件下载失败");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 跳转至查看外访清单页面
	 * 2016年5月24日
	 * @param model
	 * @param relationId 关联ID(变更历史表)
	 * @return 查看历史外访清单页面
	 */
	@RequestMapping(value = "wfHistoryShow")
	public String wfHistoryShow(Model model,String relationId){
		List<LoanFlowDictEx> dicts = outVisitService.getVisitDicts();
		List<OutsideCheckWFInfo> checkInfos = outVisitService.getInfosByRid(relationId);
		model.addAttribute("dicts",dicts);
		model.addAttribute("checkInfos",checkInfos);		
		model.addAttribute("loanManType", outVisitService.getLoanManFlag());
		return "borrow/workFlow/outVisitView";		
	}
	/**
	 * 查询所属机构下的门店
	 */
	@RequestMapping(value = "selectOrgListByDepartmentId")
	public String selectStorePageByDepartmentId(Org org, HttpServletRequest request,
			HttpServletResponse response, String isSingle, Model model) {
		if (null == org.getArea()) {
			Area area = new Area();
			org.setArea(area);
		}
		String orgName = org.getName();
		if(null !=org.getName() && org.getName().indexOf(",") >-1){
			String[] names = org.getName().split(",");
			List<Org> orgList = Lists.newArrayList();
			for(String name:names){
				Org orgN = new Org();
				orgN.setName(name);
				orgList.add(orgN);
			}
			org.setChildren(orgList);
			org.setName(null);
		}
		//设置当前用户所属机构
		String departmentId=UserUtils.getUser().getDepartment().getId();
		org.setDepartmentId(departmentId);
		
		Page<Org> orgs = orgManager.findStoresPage(new Page<Org>(request,
				response), org);
		org.setName(orgName);
		model.addAttribute("page", orgs);
		model.addAttribute("storesSelected", org.getStoresSelected());

		model.addAttribute("queryURL", "selectOrgListByDepartmentId");
		return "modules/single/storesListByDepartmentId";
	}
}
