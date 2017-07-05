package com.creditharmony.loan.borrow.borrowlist.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creditharmony.loan.common.type.LoanProductCode;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.adapter.bean.in.mail.MailInfo;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendFreezeInfoOutBean;
import com.creditharmony.adapter.bean.out.mail.MailOutInfo;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.adapter.core.utils.DesUtils;
import com.creditharmony.adapter.service.email.bean.EmailConfirmInParam;
import com.creditharmony.adapter.service.email.bean.EmailConfirmOutParam;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.ApplyIdUtils;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.loan.type.RsStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.ProvinceCity;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.event.FileUploadEx;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.service.DataValidateService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.applyinfo.view.UploadView;
import com.creditharmony.loan.borrow.consult.service.ConsultService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.LoanCustomerEx;
import com.creditharmony.loan.borrow.contract.event.LoanFlowUpdCtrEx;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.util.GrantCallUtil;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.reconsider.service.ReconsiderApplyService;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.channel.bigfinance.ws.BigFinanceExFreezeService;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.event.CreateOrderFileIdEx;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowQueue;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowStepName;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.service.OrgInfoService;
import com.query.ProcessOrQueryBuilderContainer;
import com.query.ProcessQueryBuilder;

/**
 * 获取信借待办列表controller
 * @Class Name BorrowListController
 * @author zhangping
 * @Create In 2015年11月24日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/borrowlist")
public class BorrowListController extends BaseController {

	/**
	 * FlowService 查询流程待办列表、提交流程
	 */
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;

	@Autowired
	private ConsultService consultService;

	@Autowired
	private CustomerManagementService customerManagementService;

	@Autowired
	private DataEntryService dataEntryService;

	@Autowired
	private LoanPrdMngService loanPrdMngService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private LoanStatusHisDao loanStatusHisDao;

	@Autowired
	private DataValidateService dataValidateService;
	
	@Autowired
    private UserManager userManager;
	
	@Autowired
	private ProvinceCityManager provinceCityManager;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	
	@Autowired
	private ReconsiderApplyService reconsiderApplyService;
	
	@Autowired
	private BigFinanceExFreezeService bigFinanceExFreezeService;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private LoanCustomerDao customerDao;
	@Autowired
	private CreateOrderFileIdEx createOrderFileIdEx;
	
	@Autowired
	private LoanFlowUpdCtrEx loanFlowUpdCtrEx;
	

	@Autowired
	private OrgInfoService orgInfoService;

	/**
	 * 获取待办列表 custId表示从页面上获取的查询条件，此处没有做实质性的传值
	 * ProcessQueryBuilder是一个map结构类，构造查询条件 其中key值的定义查看 《开发变量名一览.xlsx》文档
	 * 2015年11月24日 By zhangping
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param loanFlowQueryParam
	 * @return borrowlist.jsp
	 */
	@RequestMapping(value = "fetchItemsForLaunch")
	public String fetchTaskItems(Model model, HttpServletRequest request, HttpServletResponse response, LoanFlowQueryParam loanFlowQueryParam) {
		// 获取业务表咨询表中 下一步状态为 继续跟踪 的记录
		List<LoanFlowWorkItemView> itemList = new ArrayList<LoanFlowWorkItemView>();
		ConsultSearchView consultSearchView = new ConsultSearchView();
		loanFlowQueryParam.setCustomerName(StringEscapeUtils.unescapeHtml4(loanFlowQueryParam.getCustomerName()));
		User user = UserUtils.getUser();
		List<Role> roles = user.getRoleList();
		String roleType = "";
		//取单按钮可见权限操作
		
		/** 
		 * 判断当前登录者是电销录单专员还是客服
		 * 如果是客服则可取信借跟电销录单
		 * 如果是电销录单专员则只能录自己取过的单
		 * 
		 */
		for (Role r : roles) {
			if (LoanRole.CUSTOMER_SERVICE.id.equals(r.getId())) { // 客服
				roleType = LoanRole.CUSTOMER_SERVICE.id;
				Org org = user.getDepartment();
				consultSearchView.setStoreOrgId(org.getId());
				break;
			} else if (LoanRole.MOBILE_SALE_RECORDER.id.equals(r.getId())) { // 电销
				roleType = LoanRole.MOBILE_SALE_RECORDER.id;
				break;
			}
		}
		if (!ObjectHelper.isEmpty(loanFlowQueryParam)) {
			consultSearchView.setCustomerName(StringEscapeUtils.unescapeHtml4(loanFlowQueryParam.getCustomerName()));
			consultSearchView.setLoanTeamEmpcode(loanFlowQueryParam.getTeamManagerCode());
			consultSearchView.setConsTelesalesFlag(loanFlowQueryParam.getTelesalesFlag());
			//客服
			if (LoanRole.CUSTOMER_SERVICE.id.equals(roleType)) {
				//电销标识为空
				if (StringUtils.isEmpty(loanFlowQueryParam.getTelesalesFlag())) {
					//下一步状态为继续跟踪
					consultSearchView.setDictCreditOperStatus(NextStep.CONTINUE_CONFIRM.getCode());
					//信借非电销标识
					consultSearchView.setConsCreditNotTelFlag(YESNO.NO.getCode());
					//已去单
					consultSearchView.setDictTelOperStatus(RsStatus.GET_ORDER.getCode());
					//电销标识
					consultSearchView.setDictTelFlag(YESNO.YES.getCode());
				//非电销	
				} else if (YESNO.NO.getCode().equals(loanFlowQueryParam.getTelesalesFlag())) {
					consultSearchView.setDictCreditOperStatus(NextStep.CONTINUE_CONFIRM.getCode());
					consultSearchView.setConsCreditNotTelFlag(YESNO.NO.getCode());
				//电销
				} else {
					consultSearchView.setDictTelOperStatus(RsStatus.GET_ORDER.getCode());
					consultSearchView.setDictTelFlag(YESNO.YES.getCode());
				}
			//电销	
			} else if (LoanRole.MOBILE_SALE_RECORDER.id.equals(roleType)) {
				consultSearchView.setDictTelOperStatus(RsStatus.GET_ORDER.getCode());
				consultSearchView.setDictTelFlag(YESNO.YES.getCode());
				consultSearchView.setConsServiceUserCode(user.getId());
			}
			consultSearchView.setMateCertNum(loanFlowQueryParam.getIdentityCode());
			consultSearchView.setManagerCode(loanFlowQueryParam.getCustomerManagerCode());
		}
		
		Page<ConsultSearchView> tracePage = customerManagementService.findApplyPage(new Page<ConsultSearchView>(request, response), consultSearchView);
		if (tracePage.getList() != null) {
			String name = null;
			String id = null;
			User tempUser = null;
			for (ConsultSearchView consultSev : tracePage.getList()) {
				LoanFlowWorkItemView borrowListItems = new LoanFlowWorkItemView();
				borrowListItems.setCustomerName(consultSev.getCustomerName());
				borrowListItems.setLoanStatusCode(consultSev.getDictOperStatus());
				//电销咨询状态
				String loanStatusCodeName = DictCache.getInstance().getDictLabel("jk_rs_status", borrowListItems.getLoanStatusCode());
				borrowListItems.setLoanStatusCodeName(loanStatusCodeName);
				//设置团队经理
				id = consultSev.getLoanTeamEmpcode();
				if (StringUtils.isNotEmpty(id)) {
					tempUser = userManager.get(id);
					if (!ObjectHelper.isEmpty(tempUser)) {
						name = tempUser.getName();
						if (StringUtils.isNotEmpty(name)) {
							borrowListItems.setTeamManagerName(name);
						}
					}
				}
				//设置客户经理
				id = consultSev.getManagerCode();
				if (StringUtils.isNotEmpty(id)) {
					tempUser = userManager.get(id);
					if (!ObjectHelper.isEmpty(tempUser)) {
						name = tempUser.getName();
						if (StringUtils.isNotEmpty(name)) {
							borrowListItems.setCustomerManagerName(name);
						}
					}
				}
				borrowListItems.setTelesalesFlag(consultSev.getConsTelesalesFlag());
				//是不是电销
				String telesalesFlagName = DictCache.getInstance().getDictLabel("yes_no", borrowListItems.getTelesalesFlag());
				borrowListItems.setTelesalesFlagName(telesalesFlagName);
				//进件时间
				borrowListItems.setIntoLoanTime(consultSev.getCreateTime());
				borrowListItems.setCustomerCode(consultSev.getCustomerCode());
				borrowListItems.setConsultId(consultSev.getId());
				//申请表新版和旧版标识（0是旧版，1是新版）
				borrowListItems.setLoanInfoOldOrNewFlag(StringUtils.isEmpty(consultSev.getLoanInfoOldOrNewFlag()) ? ApplyInfoConstant.LOANINFO_OLDORNEW_FLAG_NEW : consultSev.getLoanInfoOldOrNewFlag());
				itemList.add(borrowListItems);
			}
		}
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		Page<LoanFlowWorkItemView> targetPage  = new Page<LoanFlowWorkItemView>();
		targetPage.setList(itemList);
		targetPage.setCount(tracePage.getCount());
	
		targetPage.setPageNo(tracePage.getPageNo());
		targetPage.setPageSize(tracePage.getPageSize());
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("lfqp", loanFlowQueryParam);
		model.addAttribute("productList", productList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", tracePage);
		return "borrow/borrowlist/workItemsForLaunch";
	}
	
	
	

	/**
	 * 2015年11月24日 
	 * By zhangping
	 * @param model
	 * @param loanFlowQueryParam
	 * @param request
	 * @param response
	 * @param flowFlag
	 * @return borrowlist.jsp + param:viewName
	 * @throws Exception
	 */
	@RequestMapping(value = "fetchTaskItems")
	public String fetchTaskItems(
			Model model,
			@ModelAttribute(value = "loanFlowQueryParam") LoanFlowQueryParam loanFlowQueryParam,
			HttpServletRequest request,
			HttpServletResponse response, String flowFlag) throws Exception {
		
		ProcessQueryBuilder paramFirst = new ProcessQueryBuilder();
		ProcessQueryBuilder paramSecond = new ProcessQueryBuilder();
		ProcessQueryBuilder paramThird = new ProcessQueryBuilder();
        ProcessQueryBuilder paramFourth = new ProcessQueryBuilder();
        ProcessQueryBuilder paramFifth = new ProcessQueryBuilder();
        ProcessQueryBuilder paramSixth = new ProcessQueryBuilder();
		ProcessOrQueryBuilderContainer paramContainer = new ProcessOrQueryBuilderContainer();
		if(StringUtils.isNotEmpty(loanFlowQueryParam.getCustomerName())){
		    loanFlowQueryParam.setCustomerName(StringEscapeUtils.unescapeHtml4(loanFlowQueryParam.getCustomerName()));   
		}
		String customerName = loanFlowQueryParam.getCustomerName();
        FlowPage page = new FlowPage();
		String queue = null;
		String viewName = null;
	    String roleType="";
		if (StringUtils.isEmpty(flowFlag)) {
			//要查询的工作流队列
			queue = LoanFlowQueue.CUSTOMER_AGENT;
			viewName = "loanflow_borrowlist_workItems";
			User user = UserUtils.getUser();
			Org org = user.getDepartment();
			List<Role> roles = user.getRoleList();
			for (Role r : roles) {
				//客服或者电销录入专员
				if (LoanRole.CUSTOMER_SERVICE.id.equals(r.getId()) || LoanRole.MOBILE_SALE_RECORDER.id.equals(r.getId())) {
					roleType = "1";
					break;
				//门店副理
				} else if (LoanRole.STORE_ASSISTANT.id.equals(r.getId())) {
					roleType = "2";
					break;
				//惠民_借款人服务部_部门总监	
				} else if (BaseRole.LOANER_DEPT_MASTER.id.equals(r.getId())) {
					roleType = "3";
				}
			}
			//客服或者电销录入专员
			if ("1".equals(roleType)) {
				String storeReview = LoanApplyStatus.STORE_REVERIFY.getCode();
				String[] agentView = { LoanApplyStatus.BACK_STORE.getCode(), LoanApplyStatus.APPLY_ENGINE_BACK.getCode(), 
						LoanApplyStatus.RECONSIDER_BACK_STORE.getCode(), LoanApplyStatus.SIGN_CONFIRM.getCode() };

				//paramFirst，paramThird，paramFifth，如果条件中客户名称不为空，则根据主借人名称查询
				
				//查询登录员工组织机构内，门店复核状态，客服编号不是登录账户的记录。
				//因为待门店复核状态的记录，需要其他客服进行复核，所以登录客服看不到自己的记录
				ReflectHandle.copy(loanFlowQueryParam, paramFirst);
				paramFirst.put("storeOrgId", org.getId());
				paramFirst.put("agentCode@<>", user.getUserCode());
				paramFirst.put("loanStatusCode", storeReview);

				//查询登录员工组织机构内，agentView内状态的记录
				ReflectHandle.copy(loanFlowQueryParam, paramThird);
				paramThird.put("storeOrgId", org.getId());
				// paramThird.put("agentCode", user.getUserCode());
				paramThird.put("loanStatusCode", agentView);
				
				//查询查询登录员工组织机构内，资料上传的记录，资料上传后agentCode才会复制，所以这里查空
				ReflectHandle.copy(loanFlowQueryParam, paramFifth);
				paramFifth.put("storeOrgId", org.getId());
				paramFifth.put("agentCode", "");
				paramFifth.put("loanStatusCode", LoanApplyStatus.INFORMATION_UPLOAD.getCode());
				
				//根据共借人（现自然人保证人）进行模糊查询
				if (StringUtils.isNotEmpty(customerName)) {
					paramSecond = new ProcessQueryBuilder();
					loanFlowQueryParam.setCustomerName(null);

					ReflectHandle.copy(loanFlowQueryParam, paramSecond);
					paramSecond.put("storeOrgId", org.getId());
					paramSecond.put("agentCode@<>", user.getUserCode());
					paramSecond.put("coborrowerName@like", "%" + customerName + "%");
					paramSecond.put("loanStatusCode", storeReview);
					paramContainer.addQueryBuilder(paramSecond);

					paramFourth = new ProcessQueryBuilder();
					ReflectHandle.copy(loanFlowQueryParam, paramFourth);
					paramFourth.put("storeOrgId", org.getId());
					// paramFourth.put("agentCode", user.getUserCode());
					paramFourth.put("coborrowerName@like", "%" + customerName + "%");
					paramFourth.put("loanStatusCode", agentView);
					paramContainer.addQueryBuilder(paramFourth);

					ReflectHandle.copy(loanFlowQueryParam, paramSixth);
					paramSixth.put("storeOrgId", org.getId());
					paramSixth.put("agentCode", "");
					paramSixth.put("loanStatusCode", LoanApplyStatus.INFORMATION_UPLOAD.getCode());
					paramSixth.put("coborrowerName@like", "%" + customerName + "%");
					paramContainer.addQueryBuilder(paramSixth);
				}
				paramContainer.addQueryBuilder(paramFifth);
				paramContainer.addQueryBuilder(paramThird);
				paramContainer.addQueryBuilder(paramFirst);
				// 门店副理查询
			} else if ("2".equals(roleType)) {
				
				//查询组织机构内所有记录，如果条件中客户名称不为空，则查询主借人
				ReflectHandle.copy(loanFlowQueryParam, paramFirst);
				paramFirst.put("storeOrgId", org.getId());
				
				//根据共借人（现自然人保证人）进行模糊查询
				if (StringUtils.isNotEmpty(customerName)) {
					paramSecond = new ProcessQueryBuilder();
					loanFlowQueryParam.setCustomerName(null);
					ReflectHandle.copy(loanFlowQueryParam, paramSecond);
					paramSecond.put("storeOrgId", org.getId());
					paramSecond.put("coborrowerName@like", "%" + customerName + "%");
					paramContainer.addQueryBuilder(paramSecond);
				}
				paramContainer.addQueryBuilder(paramFirst);
			} else if ("3".equals(roleType)) { // 部门总监
				//查待确认签署状态记录，如果条件中客户名称不为空，则查询主借人
				ReflectHandle.copy(loanFlowQueryParam, paramFirst);
				paramFirst.put("loanStatusCode", LoanApplyStatus.SIGN_CONFIRM.getCode());
				
				//根据共借人（现自然人保证人）进行模糊查询
				if (StringUtils.isNotEmpty(customerName)) {
					paramSecond = new ProcessQueryBuilder();
					loanFlowQueryParam.setCustomerName(null);
					ReflectHandle.copy(loanFlowQueryParam, paramSecond);
					paramSecond.put("coborrowerName@like", "%" + customerName + "%");
					paramSecond.put("loanStatusCode", LoanApplyStatus.SIGN_CONFIRM.getCode());
					paramContainer.addQueryBuilder(paramSecond);
				}
				paramContainer.addQueryBuilder(paramFirst);

			} else {
				return "error/403";
			}
		}
	  	
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        Integer ps = 30;
        Integer pn = 1;
        if(!ObjectHelper.isEmpty(pageSize)){
            ps = Integer.valueOf(pageSize);
        }
        if(!ObjectHelper.isEmpty(pageNo)){
            pn = Integer.valueOf(pageNo); 
        }
        page.setPageSize(ps);
        page.setPageNo(pn);
		flowService.fetchTaskItems(queue, paramContainer, page, null, LoanFlowWorkItemView.class);
		
		loanFlowQueryParam.setCustomerName(customerName);
		List<LoanFlowWorkItemView> itemList = null;
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		itemList = this.convertList(sourceWorkItems);
		//查询产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		for(LoanFlowWorkItemView lf:itemList){
			String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",lf.getModel());
			lf.setModelLabel(modelLabel);
			if(StringHelper.isNotEmpty(Global.getConfig("bestCoborrowerTime"))&&lf.getIntoLoanTime().getTime() - DateUtils.convertDate(Global.getConfig("bestCoborrowerTime")).getTime() > 0){
				lf.setBestCoborrowerFlag("1");
			}else{
				lf.setBestCoborrowerFlag("0");
			}
		}
		model.addAttribute("productList", productList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		model.addAttribute("queryParam", loanFlowQueryParam);
		model.addAttribute("teleFlag", YESNO.NO.getCode());
		return "borrow/borrowlist/" + viewName;
	}

	/**
	 * redirectAttributes 2015年11月24日 By zhangping
	 * 此方法现在只适用于旧版申请表，新版申请表请参见launchFlow_new方法
	 * @param model
	 * @param launchView
	 * @param redirectAttributes
	 * @param itemView
	 * @param applyInfoFlagEx
	 * @return 重定向 fetchTaskItems
	 */
	@RequestMapping(value = "launchFlow")
	public String launchFlow(Model model, LaunchView launchView,
			RedirectAttributes redirectAttributes, WorkItemView itemView,
			ApplyInfoFlagEx applyInfoFlagEx) {
	    applyInfoFlagEx.getLoanBank().setBankAccountName(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanBank().getBankAccountName()));
	    applyInfoFlagEx.getLoanBank().setBankAuthorizer(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanBank().getBankAuthorizer()));
	    Object clock = new Object();
        boolean result  =false;
        synchronized(clock){  
            String curTokenId = applyInfoFlagEx.getDefTokenId();
            String curToken  = applyInfoFlagEx.getDefToken();
            result = TokenUtils.validToken(curTokenId, curToken);
            TokenUtils.removeToken(curTokenId);
        }
         if(result){
		    logger.info("-------信借流程发起 systemtime =  " + new Date() );
		    try {
		        dataEntryService.saveApplyInfo(applyInfoFlagEx, false);
		    } catch (Exception e) {
		        redirectAttributes.addAttribute("message",e.getMessage());  
		        e.printStackTrace();
		        return "redirect:" + adminPath
                    + "/borrow/borrowlist/fetchItemsForLaunch";
		    }
		    String applyId = ApplyIdUtils.builderApplyId(itemView.getFlowType());
		    String loanCode = applyInfoFlagEx.getLoanInfo().getLoanCode();
		    ApplyInfoFlagEx tempApplyInfo = dataEntryService
				.getAllInfoByLoanCode(loanCode);
		    tempApplyInfo.getLoanInfo().setCustomerIntoTime(new Date());
		    tempApplyInfo.setFlag(ApplyInfoConstant.APPLY_INFO_LOANINFO);
		    tempApplyInfo.setConsultId(applyInfoFlagEx.getConsultId());
		    // 如果个人资料页签中的客户姓名跟借款主表中的客户姓名不一致，那么将借款主表中的客户姓名更新成个人资料页中的客户姓名
		    if(StringUtils.isNotEmpty(tempApplyInfo.getLoanCustomer().getCustomerName()) &&
		            !tempApplyInfo.getLoanCustomer().getCustomerName().equals(tempApplyInfo.getLoanInfo().getLoanCustomerName())){
		        tempApplyInfo.getLoanInfo().setLoanCustomerName(tempApplyInfo.getLoanCustomer().getCustomerName());
		    }
		    try {
		        dataEntryService.saveApplyInfo(tempApplyInfo, false);
		    } catch (Exception e) {
		        redirectAttributes.addAttribute("message",e.getMessage());  
		        e.printStackTrace();
		        return "redirect:" + adminPath
                    + "/borrow/borrowlist/fetchItemsForLaunch";
		    }
		    BeanUtils.copyProperties(tempApplyInfo, launchView); // 将发起页签的所有数据全部查询出来
		    LoanInfo loanInfo = tempApplyInfo.getLoanInfo();
		    if (!ObjectHelper.isEmpty(loanInfo)) {
		        String productCode = loanInfo.getProductType();
		        LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		        loanPrd.setProductCode(productCode);
		        List<LoanPrdMngEntity> productList = loanPrdMngService
					.selPrd(loanPrd);
			// 设置申请产品信息
			if (!ObjectHelper.isEmpty(productList)) {
				launchView.setProductName(productList.get(0).getProductName());
			}
			launchView.setProductCode(productCode);
			String teamManagerId = loanInfo.getLoanTeamManagerCode(); 
			Integer bankIsRareword = launchView.getLoanBank().getBankIsRareword();
			if(ObjectHelper.isNotEmpty(bankIsRareword) && bankIsRareword.intValue()==1){
			    launchView.setBankIsRareword(YESNO.YES.getCode());
			}else{
			    launchView.setBankIsRareword(YESNO.NO.getCode());
			}
			// 设置团队经理信息
			User user = userManager.get(teamManagerId);
			if(!ObjectHelper.isEmpty(user)){
			    String userName = user.getName();
			    launchView.setLoanTeamManagerName(userName);
			}
			// 设置客户经理信息
			launchView.setLoanTeamManagerCode(teamManagerId);
			String customerManagerId = loanInfo.getLoanManagerCode();
			if(StringUtils.isNotEmpty(customerManagerId)){
			    user = userManager.get(customerManagerId); 
			    if(!ObjectHelper.isEmpty(user)){
	                String userName = user.getName();
	                launchView.setLoanManagerName(userName);
	            }
			}
			launchView.setLoanManagerCode(customerManagerId);
		}
		    BigDecimal loanApplyAmount = launchView.getLoanInfo().getLoanApplyAmount();
		    if (!ObjectHelper.isEmpty(loanApplyAmount)) {
		        launchView.getLoanInfo().setLoanApplyAmountf(loanApplyAmount.floatValue());
		}
		// 设置共借人信息
		String url ="";
		this.setCoborrowers(launchView);  
		OrgCache orgCache = OrgCache.getInstance();
        Org storeOrg = null;
        User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
        if(YESNO.YES.getCode().equals(launchView.getLoanCustomer().getCustomerTelesalesFlag())){
            storeOrg = orgCache.get(launchView.getLoanInfo().getLoanStoreOrgId());
             url = "/borrow/borrowlist/fetchItemsForLaunch";
        }else{
            Org org = user.getDepartment();
    		storeOrg = orgCache.get(org.getId());
    		 url = "/borrow/borrowlist/fetchItemsForLaunch";
        }
        //电销团队组织机构编码
        launchView.setConsTelesalesOrgcode(loanInfo.getConsTelesalesOrgcode());

		launchView.setApplyId(applyId);
	    // 设置门店信息
	    if (!ObjectHelper.isEmpty(storeOrg)) {
	        String provinceId = storeOrg.getProvinceId();
	        String cityId = storeOrg.getCityId();
	        ProvinceCity provinceCity = null;
	        launchView.setOrgProvinceCode(provinceId);
	        provinceCity = provinceCityManager.get(provinceId);
	        if(!ObjectHelper.isEmpty(provinceCity)){
	            launchView.setOrgProvince(provinceCity.getAreaName());
	        }
	        launchView.setOrgCityCode(cityId);
	        provinceCity = provinceCityManager.get(cityId);
	        if(!ObjectHelper.isEmpty(provinceCity)){
	            launchView.setOrgCity(provinceCity.getAreaName());
	        }
	        launchView.setOrgCode(storeOrg.getStoreCode());
	        launchView.setOrgName(storeOrg.getName());
	        launchView.setStoreOrgId(storeOrg.getId());
	    }
	    // 是否追加借设置为默认值  否-0
	    launchView.getLoanInfo().setDictIsAdditional(YESNO.NO.getCode());
	    launchView.setDictLoanStatus(LoanApplyStatus.INFORMATION_UPLOAD
	        .getName());
	    // 下一结点处理人
	    launchView.setDealUser(user.getId());
	    launchView.setDictLoanStatusCode(LoanApplyStatus.INFORMATION_UPLOAD
	            .getCode());
	    if(StringUtils.isEmpty(launchView.getLoanFlag())){
	        launchView.setLoanFlag(ChannelFlag.CHP.getName());
	        launchView.setLoanFlagCode(ChannelFlag.CHP.getCode());
	    }else{
	        ChannelFlag flag = ChannelFlag.parseByName(launchView.getLoanFlag());
	        launchView.setLoanFlagCode(flag.getCode());
	    }
		    /*launchView.setAgentCode(user.getUserCode());
		    launchView.setAgentName(user.getName());*/
	     itemView.setBv(launchView);
	     flowService.launchFlow(itemView);
	     return "redirect:" + adminPath + url;
         }else{
             String sigin = applyInfoFlagEx.getFlag();
             int sigins = Integer.valueOf(sigin);
             int targetFlag = sigins ;
             String flag = sigin;
             redirectAttributes.addAttribute("message",ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE);
             String viewName = ApplyInfoConstant.VIEWS_NAME[targetFlag-1];
             redirectAttributes.addAttribute("loanInfo.loanCode", applyInfoFlagEx.getLoanInfo().getLoanCode());
             redirectAttributes.addAttribute("loanCustomer.id", applyInfoFlagEx.getLoanCustomer().getId());
             redirectAttributes.addAttribute("loanCustomer.customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
             redirectAttributes.addAttribute("loanCustomer.customerName", applyInfoFlagEx.getLoanCustomer().getCustomerName());
             redirectAttributes.addAttribute("customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
             redirectAttributes.addAttribute("loanInfo.loanCustomerName", applyInfoFlagEx.getLoanInfo().getLoanCustomerName());
             redirectAttributes.addAttribute("consultId", applyInfoFlagEx.getConsultId());
             redirectAttributes.addAttribute("flowCode", itemView.getFlowCode());
             redirectAttributes.addAttribute("flowName", itemView.getFlowName());
             redirectAttributes.addAttribute("stepName", itemView.getStepName());
             redirectAttributes.addAttribute("flowType", itemView.getFlowType());
             redirectAttributes.addAttribute("flag",flag);
             redirectAttributes.addAttribute("viewName",viewName);
             return "redirect:" + adminPath
                     + "/apply/dataEntry/getApplyInfo";
         }
	}
	/**
	 * 此方法适用于新版申请表
	 */
	@RequestMapping(value = "launchFlow_new")
	public String launchFlow_new(Model model, LaunchView launchView,RedirectAttributes redirectAttributes, 
									WorkItemView itemView,ApplyInfoFlagEx applyInfoFlagEx) {
	    applyInfoFlagEx.getLoanBank().setBankAccountName(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanBank().getBankAccountName()));
	    applyInfoFlagEx.getLoanBank().setBankAuthorizer(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanBank().getBankAuthorizer()));
	    //Object clock = new Object();
        boolean result  =false;
        synchronized(this){  
            String curTokenId = applyInfoFlagEx.getDefTokenId();
            String curToken  = applyInfoFlagEx.getDefToken();
            result = TokenUtils.validToken(curTokenId, curToken);
            TokenUtils.removeToken(curTokenId);
        }
         if(result){
		    logger.info("-------信借流程发起 systemtime =  " + new Date() );
		    try {
		        dataEntryService.saveApplyInfo_new(applyInfoFlagEx, false);
		    } catch (Exception e) {
		        redirectAttributes.addAttribute("message",e.getMessage());  
		        e.printStackTrace();
		        return "redirect:" + adminPath
                    + "/borrow/borrowlist/fetchItemsForLaunch";
		    }
		    String applyId = ApplyIdUtils.builderApplyId(itemView.getFlowType());
		    String loanCode = applyInfoFlagEx.getLoanInfo().getLoanCode();
		    ApplyInfoFlagEx tempApplyInfo = dataEntryService.getAllInfoByLoanCode_new(loanCode);
		    tempApplyInfo.getLoanInfo().setCustomerIntoTime(new Date());
		    tempApplyInfo.setFlag(ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO);
		    tempApplyInfo.setConsultId(applyInfoFlagEx.getConsultId());
		    // 如果个人基本信息页签中的客户姓名跟借款主表中的客户姓名不一致，那么将借款主表中的客户姓名更新成个人基本信息页中的客户姓名
		    if(StringUtils.isNotEmpty(tempApplyInfo.getLoanCustomer().getCustomerName()) &&
		            !tempApplyInfo.getLoanCustomer().getCustomerName().equals(tempApplyInfo.getLoanInfo().getLoanCustomerName())){
		        tempApplyInfo.getLoanInfo().setLoanCustomerName(tempApplyInfo.getLoanCustomer().getCustomerName());
		    }
		    try {
		        dataEntryService.saveApplyInfo_new(tempApplyInfo, false);
		    } catch (Exception e) {
		        redirectAttributes.addAttribute("message",e.getMessage());  
		        e.printStackTrace();
		        return "redirect:" + adminPath
                    + "/borrow/borrowlist/fetchItemsForLaunch";
		    }
		    BeanUtils.copyProperties(tempApplyInfo, launchView); // 将发起页签的所有数据全部查询出来
		    LoanInfo loanInfo = tempApplyInfo.getLoanInfo();
		    if (!ObjectHelper.isEmpty(loanInfo)) {
		        String productCode = loanInfo.getProductType();
		        LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		        loanPrd.setProductCode(productCode);
		        List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			// 设置申请产品信息
			if (!ObjectHelper.isEmpty(productList)) {
				launchView.setProductName(productList.get(0).getProductName());
			}
			launchView.setProductCode(productCode);
			String teamManagerId = loanInfo.getLoanTeamManagerCode(); 
			Integer bankIsRareword = launchView.getLoanBank().getBankIsRareword();
			if(ObjectHelper.isNotEmpty(bankIsRareword) && bankIsRareword.intValue()==1){
			    launchView.setBankIsRareword(YESNO.YES.getCode());
			}else{
			    launchView.setBankIsRareword(YESNO.NO.getCode());
			}
			// 设置团队经理信息
			User user = userManager.get(teamManagerId);
			if(!ObjectHelper.isEmpty(user)){
			    String userName = user.getName();
			    launchView.setLoanTeamManagerName(userName);
			}
			// 设置客户经理信息
			launchView.setLoanTeamManagerCode(teamManagerId);
			String customerManagerId = loanInfo.getLoanManagerCode();
			if(StringUtils.isNotEmpty(customerManagerId)){
			    user = userManager.get(customerManagerId); 
			    if(!ObjectHelper.isEmpty(user)){
	                String userName = user.getName();
	                launchView.setLoanManagerName(userName);
	            }
			}
			launchView.setLoanManagerCode(customerManagerId);
		}
		    BigDecimal loanApplyAmount = launchView.getLoanInfo().getLoanApplyAmount();
		    if (!ObjectHelper.isEmpty(loanApplyAmount)) {
		        launchView.getLoanInfo().setLoanApplyAmountf(loanApplyAmount.floatValue());
		}
		//设置自然人保证人信息,自然人保证人姓名字段就是原来的共借人姓名字段
		this.setCoborrowers(launchView); 
		String url ="";
		OrgCache orgCache = OrgCache.getInstance();
        Org storeOrg = null;
        User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
        if(YESNO.YES.getCode().equals(launchView.getLoanCustomer().getCustomerTelesalesFlag())){
            storeOrg = orgCache.get(launchView.getLoanInfo().getLoanStoreOrgId());
             url = "/borrow/borrowlist/fetchItemsForLaunch";
        }else{
            Org org = user.getDepartment();
    		storeOrg = orgCache.get(org.getId());
    		 url = "/borrow/borrowlist/fetchItemsForLaunch";
        }
        //电销团队组织机构编码
        launchView.setConsTelesalesOrgcode(loanInfo.getConsTelesalesOrgcode());

		launchView.setApplyId(applyId);
	    // 设置门店信息
	    if (!ObjectHelper.isEmpty(storeOrg)) {
	        String provinceId = storeOrg.getProvinceId();
	        String cityId = storeOrg.getCityId();
	        ProvinceCity provinceCity = null;
	        launchView.setOrgProvinceCode(provinceId);
	        provinceCity = provinceCityManager.get(provinceId);
	        if(!ObjectHelper.isEmpty(provinceCity)){
	            launchView.setOrgProvince(provinceCity.getAreaName());
	        }
	        launchView.setOrgCityCode(cityId);
	        provinceCity = provinceCityManager.get(cityId);
	        if(!ObjectHelper.isEmpty(provinceCity)){
	            launchView.setOrgCity(provinceCity.getAreaName());
	        }
	        launchView.setOrgCode(storeOrg.getStoreCode());
	        launchView.setOrgName(storeOrg.getName());
	        launchView.setStoreOrgId(storeOrg.getId());
	    }
	    // 是否追加借设置为默认值  否-0
	    launchView.getLoanInfo().setDictIsAdditional(YESNO.NO.getCode());
	    launchView.setDictLoanStatus(LoanApplyStatus.INFORMATION_UPLOAD
	        .getName());
	    // 下一结点处理人
	    launchView.setDealUser(user.getId());
	    launchView.setDictLoanStatusCode(LoanApplyStatus.INFORMATION_UPLOAD
	            .getCode());
	    if(StringUtils.isEmpty(launchView.getLoanFlag())){
	    	if(!LoanProductCode.PRO_NONG_XIN_JIE.equals(tempApplyInfo.getLoanInfo().getProductType())){
				launchView.setLoanFlag(ChannelFlag.CHP.getName());
				launchView.setLoanFlagCode(ChannelFlag.CHP.getCode());
			}
	    }else{
	        ChannelFlag flag = ChannelFlag.parseByName(launchView.getLoanFlag());
	        launchView.setLoanFlagCode(flag.getCode());
	    }
	     itemView.setBv(launchView);
	     flowService.launchFlow(itemView);
	     return "redirect:" + adminPath + url;
         }else{
             String sigin = applyInfoFlagEx.getFlag();
             int sigins = Integer.valueOf(sigin);
             int targetFlag = sigins ;
             String flag = sigin;
             redirectAttributes.addAttribute("message",ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE);
             String viewName = ApplyInfoConstant.VIEWS_NAME_NEW[targetFlag-1];
             redirectAttributes.addAttribute("loanInfo.loanCode", applyInfoFlagEx.getLoanInfo().getLoanCode());
             redirectAttributes.addAttribute("loanCustomer.id", applyInfoFlagEx.getLoanCustomer().getId());
             redirectAttributes.addAttribute("loanCustomer.customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
             redirectAttributes.addAttribute("loanCustomer.customerName", applyInfoFlagEx.getLoanCustomer().getCustomerName());
             redirectAttributes.addAttribute("customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
             redirectAttributes.addAttribute("loanInfo.loanCustomerName", applyInfoFlagEx.getLoanInfo().getLoanCustomerName());
             redirectAttributes.addAttribute("consultId", applyInfoFlagEx.getConsultId());
             redirectAttributes.addAttribute("flowCode", itemView.getFlowCode());
             redirectAttributes.addAttribute("flowName", itemView.getFlowName());
             redirectAttributes.addAttribute("stepName", itemView.getStepName());
             redirectAttributes.addAttribute("flowType", itemView.getFlowType());
             redirectAttributes.addAttribute("flag",flag);
             redirectAttributes.addAttribute("viewName",viewName);
             return "redirect:" + adminPath
                     + "/apply/dataEntry/getApplyInfo_new";
         }
	}
	
	/**
	 *异步完成申请冻结 
	 *@author zhanghao
	 *@Create In 2016年03月02日
	 *@param model
	 *@param loanInfo 
	 *@retrun Map<String,Object>
	 * 
	 */
   @RequestMapping("asynApplyFrozen")
   public String asynApplyFrozen(Model model,LoanInfo loanInfo,RedirectAttributes redirectAttributes,String menuId){
       String dictLoanStatus = loanInfo.getDictLoanStatus();
       String queueName = null;
       BaseBusinessView businessView = null;
       String stepName = null;
       String msg = "";
       String operateResult = "";
       String applyId = loanInfo.getApplyId();
      if(!ObjectHelper.isEmpty(loanInfo)){
          
          ProcessQueryBuilder param = new ProcessQueryBuilder();
          param.put("loanCode", loanInfo.getLoanCode());
          Map<String,Object> queryParam = new HashMap<String,Object>();
          queryParam.put("loanCode", loanInfo.getLoanCode());
          ReconsiderApply  reconsiderApply = reconsiderApplyService.selectByParam(queryParam);
          if(!ObjectHelper.isEmpty(reconsiderApply)){
              applyId = reconsiderApply.getApplyId();
          }
          // 合同审核、 放款退回、待放款确认退回
          if(LoanApplyStatus.CONTRACT_AUDIFY.getCode().equals(dictLoanStatus)||
               LoanApplyStatus.LOAN_SEND_RETURN.getCode().equals(dictLoanStatus) ||
                 LoanApplyStatus.PAYMENT_BACK.getCode().equals(dictLoanStatus)
                 || LoanApplyStatus.GOLDCREDIT_RETURN.getCode().equals(dictLoanStatus)){
              queueName = LoanFlowQueue.CONTRACT_CHECK;
              if(StringUtils.isNotEmpty(loanInfo.getFrozenCode())){
                  businessView = contractDeal(loanInfo, dictLoanStatus);
              }
              
           // 待放款确认,分配卡号，大金融待放款
          }else if(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(dictLoanStatus)||
                  LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode().equals(dictLoanStatus) ||
                  LoanApplyStatus.BIGFINANCE_TO_SNED.getCode().equals(dictLoanStatus)
                  ){
              if(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(dictLoanStatus)){
                  queueName = LoanFlowQueue.STATISTICS_COMMISSIONER;
              }else if(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode().equals(dictLoanStatus)){
                  queueName = LoanFlowQueue.LOAN_BALANCE_MANAGER;
              }else if (LoanApplyStatus.BIGFINANCE_TO_SNED.getCode().equals(dictLoanStatus)) {
            	  queueName = LoanFlowQueue.LOAN_BALANCE_COMMISSIONER;
              }
              businessView = new GrantDealView();
              if(StringUtils.isNotEmpty(loanInfo.getFrozenCode())){
                  businessView = grantDeal(loanInfo, dictLoanStatus);
              }
            //TODO 大金融拒绝的状态  
          }else if(LoanApplyStatus.BIGFINANCE_REJECT.getCode().equals(dictLoanStatus)){ 
        	  WorkItemView srcWorkItem = flowService.loadWorkItemViewForAdmin(applyId);
        	  if (ObjectHelper.isEmpty(srcWorkItem)) {
        		  queueName = null;
			}else {
				if (LoanFlowStepName.CONTRACT_AUDIT.equals(srcWorkItem.getStepName())) {
					queueName = LoanFlowQueue.CONTRACT_CHECK;
					 if(StringUtils.isNotEmpty(loanInfo.getFrozenCode())){
		                  businessView = contractDeal(loanInfo, dictLoanStatus);
		              }
				}else if(LoanFlowStepName.GOLD_CREDIT_RETURN.equals(srcWorkItem.getStepName())){
					queueName = LoanFlowQueue.LIABILITIES_RETURN;
					businessView = new GrantDealView();
		              if(StringUtils.isNotEmpty(loanInfo.getFrozenCode())){
		                  businessView = grantDeal(loanInfo, dictLoanStatus);
		              }
				}
			}
          }
          if(StringUtils.isNotEmpty(queueName)){
        	  WorkItemView srcWorkItem=new WorkItemView();
        	  String step="";
        	  	if("1".equals(loanInfo.getIssplit())){
        	  		if( "75".equals(loanInfo.getDictLoanStatus()) || 
        	  				"95".equals(loanInfo.getDictLoanStatus()) || "105".equals(loanInfo.getDictLoanStatus()) ||
        	  				"64".equals(loanInfo.getDictLoanStatus()) || "72".equals(loanInfo.getDictLoanStatus())
        	  				){
        	  			step=ContractConstant.CTR_AUDIT;
        	  		}
        	  		if("1".equals(loanInfo.getZcjRejectFlag()) &&  "104".equals(loanInfo.getDictLoanStatus())){
        	  			step=ContractConstant.CTR_AUDIT;
        	  		}else if("104".equals(loanInfo.getDictLoanStatus())){
        	  			step=ContractConstant.GOLDCREDIT_RETURN;
        	  		}
        	  		if("66".equals(loanInfo.getDictLoanStatus())){
        	  			step=ContractConstant.DIS_CARD;
        	  		}else if("65".equals(loanInfo.getDictLoanStatus())){
        	  			step=GrantCommon.GRANT_SURE_NAME;
        	  		}
        	  		 
        	  		srcWorkItem.setStepName(step);
        	  	}else if("104".equals(loanInfo.getDictLoanStatus()) && (!"1".equals(loanInfo.getZcjRejectFlag()))){
        	  		step=ContractConstant.GOLDCREDIT_RETURN;
        	  	}else{
        	  		srcWorkItem = flowService.loadWorkItemViewForAdmin(applyId);
        	  	}
                // 查询流程数据
                loanInfo.setFrozenLastApplyTime(new Date());
                
               //TODO 门店申请冻结成功，进行大金融接口的调用，传入参数进去，loanInfo,如果标识为【大金融】,借款状态要改
                // 目前只有大金融待放款的状态门店申请冻结的时候，需要向大金融推送冻结债权。
                if (LoanApplyStatus.BIGFINANCE_TO_SNED.getCode().equals(dictLoanStatus) && 
                    ChannelFlag.ZCJ.getName().equals(loanInfo.getLoanFlagLabel())) {
               	 DjrSendFreezeInfoOutBean outBean = bigFinanceExFreezeService.exchangeDebtFreeze(loanInfo);
               	 if (!("0000".equals(outBean.getRetCode()))) {
						stepName = ApplyInfoConstant.STORE_APPLY_FROZEN;
						operateResult = "失败";
						String remark = null;
						if (StringUtils.isNotEmpty(loanInfo.getFrozenName()) && (ApplyInfoConstant.OTHER1.equals(loanInfo.getFrozenName()) || ApplyInfoConstant.OTHER2.equals(loanInfo.getFrozenName()))) {
							remark = loanInfo.getFrozenReason();
						} else {
							remark = loanInfo.getFrozenName();
						}
						historyService.saveLoanStatusHis(applyId,
								loanInfo.getLoanCode(), stepName,
								operateResult, "失败原因："+outBean.getRetMsg()+",冻结原因："+remark);
						msg = "门店申请冻结失败，原因：" + outBean.getRetMsg();
						addMessage(redirectAttributes, msg);
						return "redirect:" + adminPath
								+ "/borrow/transate/loanInfo";
					}
				}
                // 查询该笔数据在数据库中是否为这个状态
                LoanInfo searchLoanInfo = new LoanInfo();
                searchLoanInfo.setLoanCode(loanInfo.getLoanCode());
                searchLoanInfo.setDictLoanStatus(dictLoanStatus);
                int statusInt = applyLoanInfoDao.findFrozenInt(searchLoanInfo);
                if (statusInt > 0) {
                	loanInfo.setFrozenFlag(YESNO.YES.getCode());
                	loanInfo.setDictLoanStatus(null);
                	loanInfoService.updateFrozen(loanInfo); 
                	if(!ObjectHelper.isEmpty(srcWorkItem)){
                		WorkItemView tagWorkItem = new WorkItemView();
                		BeanUtils.copyProperties(srcWorkItem, tagWorkItem);
                		if(!ObjectHelper.isEmpty(businessView)){
                			tagWorkItem.setBv(businessView);
                			businessView.setApplyId(applyId);
                			if((!"1".equals(loanInfo.getIssplit())) && (!"1".equals(loanInfo.getZcjRejectFlag()))){
                				flowService.saveDataForAdmin(tagWorkItem);
                			}else{
                				loanFlowUpdCtrEx.invoke(tagWorkItem);
                				createOrderFileIdEx.invoke(tagWorkItem);
                			}
                		}
                		stepName = ApplyInfoConstant.STORE_APPLY_FROZEN;
                		operateResult ="成功";
                		String remark = null;
                		if(StringUtils.isNotEmpty(loanInfo.getFrozenName()) && (ApplyInfoConstant.OTHER1.equals(loanInfo.getFrozenName())||ApplyInfoConstant.OTHER2.equals(loanInfo.getFrozenName()))){
                			remark = loanInfo.getFrozenReason();
                		}else{
                			remark = loanInfo.getFrozenName();
                		}
                		historyService.saveLoanStatusHis(applyId, loanInfo.getLoanCode(), stepName, operateResult, remark);
                	}
                	msg = "申请冻结成功";
				}else {
					msg = "申请冻结失败";
				}
          }else{
               msg = "申请冻结失败";
          }
          //待款项确认，冻结时，向呼叫中心推送数据
          if(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(dictLoanStatus)){
        	  try {
        			//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
        			GrantCallUtil.grantToCallUpdateRevisitStatus(loanInfo.getContractCode(),GrantCommon.GRANTCALL_CUSTOMER_STATUS_MODIFY);
      			} catch (Exception e) {
      				logger.error("方法：asynApplyFrozen 异常，申请冻结时，给呼叫中心推送数据失败",e);
      			}
          }
      }else{
          msg = "申请冻结失败";
      } 
      addMessage(redirectAttributes, msg);
      if(StringHelper.isNotEmpty(menuId)){
    	  return "redirect:" + adminPath
                  +"/borrow/transate/loanInfo?menuId="+menuId;
      }else{
    	  return "redirect:" + adminPath
                  +"/borrow/transate/loanInfo";
      }
      
   }

   /**
    * 放款各个状态申请冻结的处理
    * 2016年11月2日
    * By 朱静越
    * @param loanInfo
    * @param dictLoanStatus
    * @return
    */
	private BaseBusinessView grantDeal(LoanInfo loanInfo, String dictLoanStatus) {
		BaseBusinessView businessView;
		GrantDealView grantDealView = new GrantDealView();
		  grantDealView.setFrozenFlag(ApplyInfoConstant.FROZEN_FLAG);
		  if(StringUtils.isNotEmpty(loanInfo.getFrozenName()) && (ApplyInfoConstant.OTHER1.equals(loanInfo.getFrozenName())||ApplyInfoConstant.OTHER2.equals(loanInfo.getFrozenName()))){
		      
		      grantDealView.setFrozenReason(loanInfo.getFrozenReason());
		  }else{
		  
		      grantDealView.setFrozenReason(loanInfo.getFrozenName());
		      loanInfo.setFrozenReason(loanInfo.getFrozenName());
		  }
		  grantDealView.setLoanCode(loanInfo.getLoanCode());
		  grantDealView.setDictLoanStatusCode(dictLoanStatus);
		  grantDealView.setLoanCode(loanInfo.getLoanCode());
		  grantDealView.setOperateType(YESNO.YES.getCode());
		  businessView = grantDealView;
		return businessView;
	}

	/**
	 * 合同审核节点的处理
	 * 2016年11月2日
	 * By 朱静越
	 * @param loanInfo
	 * @param dictLoanStatus
	 * @return
	 */
	private BaseBusinessView contractDeal(LoanInfo loanInfo,
			String dictLoanStatus) {
		BaseBusinessView businessView;
		ContractBusiView contractBusiView = new ContractBusiView();
		  contractBusiView.setFrozenFlag(ApplyInfoConstant.FROZEN_FLAG);
		  if(StringUtils.isNotEmpty(loanInfo.getFrozenName()) && (ApplyInfoConstant.OTHER1.equals(loanInfo.getFrozenName())||ApplyInfoConstant.OTHER2.equals(loanInfo.getFrozenName()))){
		  
		      contractBusiView.setFrozenReason(loanInfo.getFrozenReason());
		  }else{
		  
		      contractBusiView.setFrozenReason(loanInfo.getFrozenName());
		      loanInfo.setFrozenReason(loanInfo.getFrozenName());
		  }
		  contractBusiView.setDictLoanStatusCode(dictLoanStatus);
		  contractBusiView.setOperType(YESNO.YES.getCode());
		  businessView = contractBusiView;
		return businessView;
	}
	
	/**
	 * 检测填写的发起信息是否符合要求 2015年11月24日 By zhangping
	 * 此方法适用于旧版申请表，新版申请表请见asynCheckInput_new方法
	 * @param model
	 * @param loanCode
	 *            贷款编号
	 * @return Map<String,Object> result
	 */
	@RequestMapping("asynCheckInput")
	@ResponseBody
	public Map<String, Object> asynCheckInput(Model model, String loanCode,String oneedition) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(loanCode)) {
			result.put("success", false);
			result.put("message", "借款编号为空，请先填写相应的业务数据！");
			return result;
		}
		
		
		
		

		ApplyInfoFlagEx tempApplyInfo = dataEntryService
				.getAllInfoByLoanCode(loanCode);
		tempApplyInfo.setOneedition(oneedition);
		result = dataValidateService.validate(tempApplyInfo);
		return result;
	}
	/**
	 * 此方法适用于新版申请表
	 */
	@RequestMapping("asynCheckInput_new")
	@ResponseBody
	public Map<String, Object> asynCheckInput_new(Model model, String loanCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(loanCode)) {
			result.put("success", false);
			result.put("message", "借款编号为空，请先填写相应的业务数据！");
			return result;
		}

		ApplyInfoFlagEx tempApplyInfo = dataEntryService.getAllInfoByLoanCode_new(loanCode);
		result = dataValidateService.validate_new(tempApplyInfo);
		return result;
	}
	
	/**
	 * 服务调用：检查影像资料、个人信用体检报告费用
	 */
	@RequestMapping("checkService")
	@ResponseBody
	public Map<String, Object> checkService(Model model, String loanCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(loanCode)) {
			result.put("success", false);
			result.put("message", "借款编号为空，请先填写相应的业务数据！");
			return result;
		}

		result = dataValidateService.checkService(loanCode);
		return result;
	}
	
	/**
	 * 待确认签署办理页面提交时检查客户姓名和银行账户名的一致性
	 */
	@RequestMapping("confirmSignCheckAccountName")
	@ResponseBody
	public Map<String, Object> confirmSignCheckAccountName(Model model, String loanCode, Integer bankIsRareword,String bankAccountName) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(loanCode)) {
			result.put("success", false);
			result.put("message", "借款编号为空，数据错误！");
			return result;
		}
		
		result = dataValidateService.validateConfirmSignAccountName(loanCode,bankIsRareword,bankAccountName);
		return result;
	}

	/**
	 * 流程办理提交 2015年11月24日 By zhangping
	 * 
	 * @param model
	 * @param redirectAttributes
	 *            重定向
	 * @param itemView
	 * @param uploadView
	 *            返回的业务视图
	 * @param queue
	 *            工作队列
	 * @param viewName
	 * @return string 返回重定向路径
	 */
	@RequestMapping(value = "dispatchFlow")
	public String dispatchFlow(Model model,
			RedirectAttributes redirectAttributes, WorkItemView itemView,
			UploadView uploadView,LaunchView launchView,String queue, String viewName , String teleFlag) {
		String stepName = itemView.getStepName();
		if (LoanCustomerEx.INFORMATION_UPLOAD.equals(stepName)) {
			User user = UserUtils.getUser();
			uploadView.setAgentCode(user.getUserCode());
			uploadView.setAgentName(user.getName());
			itemView.setBv(uploadView);
		} else if (LoanCustomerEx.STORE_REVERIFY.equals(stepName) || LoanCustomerEx.APPLY_ENGINE_BACK.equals(stepName)) {
			itemView.setBv(launchView);
		} else if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(stepName)) {
			itemView.setBv(areaService.copyView(launchView));
		}
		if ("TO_GIVEUP".equals(itemView.getResponse())) {
		    String lastLoanStatus = launchView.getLastLoanStatus();
			if (LoanCustomerEx.INFORMATION_UPLOAD.equals(stepName)) {
				flowService.dispatch(itemView);			
			} else {
			    if(LoanApplyStatus.BACK_STORE.getCode().equals(lastLoanStatus)
			           || LoanApplyStatus.RECONSIDER_BACK_STORE.getCode().equals(lastLoanStatus)){
			        flowService.dispatch(itemView);  
			    }else{
			        flowService.terminate(itemView,SpringContextHolder.getBean(FileUploadEx.class));	
			    }
			}
		}else {
			flowService.dispatch(itemView);							
		}
		// 更新流程
		if (queue != null && queue.trim().length() != 0) {
			redirectAttributes.addAttribute("queue", queue);
		}
		if (viewName != null && viewName.trim().length() != 0) {
			redirectAttributes.addAttribute("viewName", viewName);
		}
		addMessage(redirectAttributes, "操作成功"); 
		if("1".equals(teleFlag)){
			return "redirect:" + adminPath + "/borrow/borrowlist/fetchTaskTelesales";
		}else{
			return "redirect:" + adminPath + "/borrow/borrowlist/fetchTaskItems";
		}
		
	}

	/**
	 * 设置共借人信息 2015年11月24日 By zhanghao
	 * 
	 * @param launchView
	 * @return none
	 */
	private void setCoborrowers(LaunchView launchView) {
		List<LoanCoborrower> coborrowers = launchView.getLoanCoborrower();
		StringBuffer coborrowerBuffer = new StringBuffer();
		for (LoanCoborrower cur : coborrowers) {
			if (coborrowerBuffer.length() == 0) {
				coborrowerBuffer.append(cur.getCoboName()!=null?cur.getCoboName():"");
			} else {
				coborrowerBuffer.append("," + cur.getCoboName());
			}
		}
		if (coborrowerBuffer.length() == 0) {
			coborrowerBuffer.append("");
		}
		launchView.setCoborrowerNames(coborrowerBuffer.toString());
	}

	/**
     *将流程中查询出来的数据类型进行转封装 
     *@author zhanghao
     *@Create In 2016年2月1日
     *@param  sourceWorkItems
     *@return List<LoanFlowWorkItemView> 
     */
    private List<LoanFlowWorkItemView> convertList(
            List<BaseTaskItemView> sourceWorkItems) {
        List<LoanFlowWorkItemView> targetList = new ArrayList<LoanFlowWorkItemView>();
        if (!ObjectHelper.isEmpty(sourceWorkItems)) {
            for (BaseTaskItemView currItem : sourceWorkItems){
                targetList.add((LoanFlowWorkItemView) currItem);
            }
        }
        return targetList;
    }
    
    /**
	 * 2015年11月24日 
	 * By zhangping
	 * @param model
	 * @param loanFlowQueryParam  电销用
	 * @param request
	 * @param response
	 * @param flowFlag
	 * @return borrowlist.jsp + param:viewName
	 * @throws Exception
	 */
	@RequestMapping(value = "fetchTaskTelesales")
	public String fetchTaskTelesales(
			Model model,
			@ModelAttribute(value = "loanFlowQueryParam") LoanFlowQueryParam loanFlowQueryParam,
			HttpServletRequest request,
			HttpServletResponse response, String flowFlag) throws Exception {
		ProcessQueryBuilder paramFirst = new ProcessQueryBuilder();
		ProcessQueryBuilder paramSecond = new ProcessQueryBuilder();
		ProcessQueryBuilder paramThird = new ProcessQueryBuilder();
        ProcessQueryBuilder paramFourth = new ProcessQueryBuilder();
        ProcessQueryBuilder paramFifth = new ProcessQueryBuilder();
        ProcessQueryBuilder paramSixth = new ProcessQueryBuilder();
		ProcessOrQueryBuilderContainer paramContainer = new ProcessOrQueryBuilderContainer();
		if(StringUtils.isNotEmpty(loanFlowQueryParam.getCustomerName())){
		    loanFlowQueryParam.setCustomerName(StringEscapeUtils.unescapeHtml4(loanFlowQueryParam.getCustomerName()));
		}
		String customerName = loanFlowQueryParam.getCustomerName();
		 FlowPage page = new FlowPage();
		String queue = null;
		String viewName = null;
	    String roleType="";
  	  	if (StringUtils.isEmpty(flowFlag)) {
	         queue = LoanFlowQueue.CUSTOMER_AGENT;
	         viewName = "loanflow_Telesales_workItems";
	         User user =  UserUtils.getUser();
	         Org org = user.getDepartment();
	         //查询组织机构下对应机构类型下的所有机构
			 List<OrgInfo> orgInfoList=orgInfoService.selectDeptByOrgidAndOrgtype(org.getId(), LoanOrgType.MOBILE_SALE.key);
			 Set<String> deptIdSet=new HashSet<String>();
			 for (OrgInfo orgInfo : orgInfoList) {
				 deptIdSet.add(orgInfo.getId());
			 }
	         List<Role> roles = user.getRoleList();
	         for(Role r:roles){
	             if(LoanRole.CUSTOMER_SERVICE.id.equals(r.getId())||
	                     LoanRole.MOBILE_SALE_RECORDER.id.equals(r.getId())){
	                 roleType = "1";
	                 break;
	             }else if(LoanRole.STORE_ASSISTANT.id.equals(r.getId())){
	                 roleType = "2";
	                 break;
	             }
	         }
	         if("1".equals(roleType)){
	        	 
	             String  storeReview = LoanApplyStatus.STORE_REVERIFY.getCode();
	             String[] agentView ={LoanApplyStatus.BACK_STORE.getCode(),LoanApplyStatus.APPLY_ENGINE_BACK.getCode(),
	                     LoanApplyStatus.RECONSIDER_BACK_STORE.getCode(),
	                     LoanApplyStatus.SIGN_CONFIRM.getCode()}; 
	             ReflectHandle.copy(loanFlowQueryParam, paramFirst);
	             paramFirst.put("consTelesalesOrgcode",deptIdSet.toArray(new String[deptIdSet.size()]));
	             paramFirst.put("agentCode@<>", user.getUserCode());
	             paramFirst.put("loanStatusCode", storeReview);
	             ReflectHandle.copy(loanFlowQueryParam, paramThird);
	             paramThird.put("consTelesalesOrgcode",deptIdSet.toArray(new String[deptIdSet.size()]));
	            // paramThird.put("agentCode", user.getUserCode());
	             paramThird.put("loanStatusCode", agentView);
	             ReflectHandle.copy(loanFlowQueryParam, paramFifth);
	             paramFifth.put("consTelesalesOrgcode",deptIdSet.toArray(new String[deptIdSet.size()]));
	             paramFifth.put("agentCode", "");
	             paramFifth.put("loanStatusCode", LoanApplyStatus.INFORMATION_UPLOAD.getCode());
	             if(StringUtils.isNotEmpty(customerName)){
	                 paramSecond = new ProcessQueryBuilder();
	                 loanFlowQueryParam.setCustomerName(null);
	                 ReflectHandle.copy(loanFlowQueryParam, paramSecond);
	                 
	                 paramSecond.put("consTelesalesOrgcode",deptIdSet.toArray(new String[deptIdSet.size()]));
	                 paramSecond.put("agentCode@<>", user.getUserCode());
	                 paramSecond.put("coborrowerName@like", "%"+customerName+"%");
	                 paramSecond.put("loanStatusCode", storeReview);
	                 paramContainer.addQueryBuilder(paramSecond);
	             
	                 paramFourth = new ProcessQueryBuilder();
	                 ReflectHandle.copy(loanFlowQueryParam, paramFourth);
	                 paramFourth.put("consTelesalesOrgcode",deptIdSet.toArray(new String[deptIdSet.size()]));
	                // paramFourth.put("agentCode", user.getUserCode());
	                 paramFourth.put("coborrowerName@like", "%"+customerName+"%");
	                 paramFourth.put("loanStatusCode", agentView);
	                 paramContainer.addQueryBuilder(paramFourth);
	             
	                 ReflectHandle.copy(loanFlowQueryParam, paramSixth);
	                 paramSixth.put("consTelesalesOrgcode",deptIdSet.toArray(new String[deptIdSet.size()]));
	                 paramSixth.put("agentCode","");
	                 paramSixth.put("loanStatusCode", LoanApplyStatus.INFORMATION_UPLOAD.getCode());
	                 paramSixth.put("coborrowerName@like", "%"+customerName+"%");
	                 paramContainer.addQueryBuilder(paramSixth);
	             }
	             paramContainer.addQueryBuilder(paramFifth);
	             paramContainer.addQueryBuilder(paramThird);
	             paramContainer.addQueryBuilder(paramFirst);
	             // 门店副理查询
	         }else if("2".equals(roleType)){
	             ReflectHandle.copy(loanFlowQueryParam, paramFirst);
                 paramFirst.put("storeOrgId",org.getId());
                 if(StringUtils.isNotEmpty(customerName)){
                     paramSecond = new ProcessQueryBuilder();
                     loanFlowQueryParam.setCustomerName(null);
                     ReflectHandle.copy(loanFlowQueryParam, paramSecond);
                     paramSecond.put("storeOrgId",org.getId());
                     paramSecond.put("coborrowerName@like", "%"+customerName+"%");
                     paramContainer.addQueryBuilder(paramSecond);
                 }
                 paramContainer.addQueryBuilder(paramFirst);
	         }else{
	             return "error/403";
	         }
		}
	  	
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        Integer ps = 30;
        Integer pn = 1;
        if(!ObjectHelper.isEmpty(pageSize)){
            ps = Integer.valueOf(pageSize);
        }
        if(!ObjectHelper.isEmpty(pageNo)){
            pn = Integer.valueOf(pageNo); 
        }
        page.setPageSize(ps);
        page.setPageNo(pn);
		flowService.fetchTaskItems(queue, paramContainer, page,null,
		        LoanFlowWorkItemView.class);
		loanFlowQueryParam.setCustomerName(customerName);
		List<LoanFlowWorkItemView> itemList = null;
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		itemList = this.convertList(sourceWorkItems);
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		for(LoanFlowWorkItemView lf:itemList){
			String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",lf.getModel());
			lf.setModelLabel(modelLabel);
			if(StringHelper.isNotEmpty(Global.getConfig("bestCoborrowerTime"))&&lf.getIntoLoanTime().getTime() - DateUtils.convertDate(Global.getConfig("bestCoborrowerTime")).getTime() > 0){
				lf.setBestCoborrowerFlag("1");
			}else{
				lf.setBestCoborrowerFlag("0");
			}
		}
		model.addAttribute("productList", productList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		model.addAttribute("queryParam", loanFlowQueryParam);
		model.addAttribute("teleFlag", YESNO.YES.getCode());
		return "borrow/borrowlist/" + viewName;
	}
	
	/**
	 * 发送邮件
	 * @author 于飞
	 * @Create 2017年1月5日
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public String sendEmail(HttpServletRequest request,LoanCustomer customer){
		LoanCustomer temp = customerDao.checkIfEmailConfirm(customer);
		Map<String,Object> map = new HashMap<String,Object>();

		if(temp!=null && temp.getCustomerEmail()!=null  && temp.getEmailIfConfirm()!=null //如果之前没有进过件则直接发送邮件
				&& ((temp.getEmailIfConfirm().equals("1") //如果修改的是之前进过件的数据，且最新邮箱与之前一样则无需验证
				&& temp.getCustomerEmail().equals(customer.getCustomerEmail())
				&& customer.getLoanCode()!=null && !customer.getLoanCode().equals(""))
				||(temp.getTempEmailIfConfirm()!=null && !temp.getTempEmailIfConfirm().equals("")//如果之前进过件，但此次是重新进件，则传递的参数中借款编号为空，判断临时邮箱是否已验证
				&& temp.getTempEmailIfConfirm().equals("1")
						&& (customer.getLoanCode()==null || customer.getLoanCode().equals("")) ))
						){
			map.put("code", "3");
			map.put("msg", "邮箱并未变更，无需再次验证！");
		}else{
			String subject="信和汇金预留邮箱验证";
			String startImg = "</br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/hjlogo.jpg'></br></br>"; 
			String endImg = "<br></br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/ranmeizhiji.jpg'>";
			String url=null;
			if(temp.getId()==null || temp.getId().equals("") 
					|| temp.getLoanCode()==null || "".equals(temp.getLoanCode())){
				url=makeEmailParam(temp.getCustomerCode());
			}else{
				url=makeEmailParam(temp.getId());
			}
			String content=startImg + "</br></br>尊敬的"+temp.getCustomerName()+"客户您好：</br>"+
			"请您点击下方链接进行邮箱验证，此链接仅用于信和汇金预留邮箱验证，请勿回复或转发。</br>"+
			//url+ "\n"+
			"<a href = \""+  url + "\">"+url+"</a></br>" +
			"如有疑问请详询400-090-1199。祝您生活愉快！</br></br>" + endImg;
			//将最新邮箱赋值给temp
			temp.setCustomerEmail(customer.getCustomerEmail());
			String code = sendEmailMothed(temp,content,subject);
			map.put("code", code);
			if(code.equals("1")){
				map.put("msg", "发送成功！");
			}else if(code.equals("2")){
				map.put("msg", "发送失败！");
			}
		}

		return JsonMapper.nonDefaultMapper().toJson (map);
	}
	

	/**
	 * 发送邮件
	 * @author 于飞
	 * @Create 2017年1月5日
	 * @param content 发送内容
	 * @return
	 */
	public String sendEmailMothed(LoanCustomer customer,String content,String subject){
		String email = customer.getCustomerEmail();
		String returnStr = "2";
		ClientPoxy service = new ClientPoxy(ServiceType.Type.CONFIRM_MAIL); 
		MailInfo mailParam = new MailInfo(); 
		String[] toAddrArray = {email}; 
		mailParam.setSubject(subject);
		mailParam.setToAddrArray(toAddrArray); 
		mailParam.setContent(content);		
		try {
			MailOutInfo out = (MailOutInfo) service.callService(mailParam);
			if("0000".equals(out.getRetCode())){
				//邮件发送成功后将邮箱设置为未验证
				customer.setEmailIfConfirm(null);
				customerDao.updateEmailConfirm(customer);
				returnStr = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件失败  "+ e.getMessage());
		}
		return returnStr;
	}
	
	/**
	 * 邮箱确认的链接
	·* 2016年11月30日
	·* by Huowenlong
	 * @param emailId
	 * @return   

	 */
	public String makeEmailParam(String emailId){
		String contentj = "";
		String url = Global.getConfig("loan.email.confirm");
		String secretKey = Global.getConfig("loan.email.key"); 
		String secretparmKey = Global.getConfig("loan.email.paramKey");
		try {
			String content="{'key':'"+secretKey+"', 'type':'"+"1"+"' , 'paramKey':'"+emailId+"','businessType':'2','sendEmailTime':'"+new Date().getTime()+"'} ";
			contentj = url + DesUtils.encrypt(content,secretparmKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentj;
	}
	
	/**
	 * 检查邮件是否验证过
	 * @author 于飞
	 * @Create 2017年1月6日
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkIfEmailConfirm")
	public String checkIfEmailConfirm(HttpServletRequest request,LoanCustomer customer){
		LoanCustomer temp = customerDao.checkIfEmailConfirm(customer);
		if(temp.getCustomerEmail()!=null && customer.getCustomerEmail()!=null 
				&& temp.getCustomerEmail().equals(customer.getCustomerEmail())
				&& customer.getLoanCode()!=null && !customer.getLoanCode().equals("")){
			return temp.getEmailIfConfirm();
		}else if(temp!=null && (temp.getCustomerEmail()==null || "".equals(temp.getCustomerEmail())
				|| customer.getLoanCode() == null || customer.getLoanCode().equals(""))){
			return temp.getTempEmailIfConfirm();
		}else if(temp!=null && temp.getEmailIfConfirm()!=null 
				&& temp.getEmailIfConfirm().equals("1") && temp.getTempEmailIfConfirm()!=null
				&& temp.getTempEmailIfConfirm().equals("1")){
			return "1";
		}
		return null;
	}
	
	/**
	 * 检查邮件是否验证过
	 * @author 于飞
	 * @Create 2017年1月6日
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEmailIfConfirm")
	public String updateEmailIfConfirm(HttpServletRequest request,String customerCode){
		try{
			LoanCustomer customer = new LoanCustomer();
			customer.setCustomerCode(customerCode);
			customer.setEmailIfConfirm(null);
			customerDao.updateEmailConfirm(customer);
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			return "2";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/emailUpdateConfirm2")
	public EmailConfirmOutParam emailUpdateConfirm2(String key){
		EmailConfirmOutParam ep = new EmailConfirmOutParam();
		EmailConfirmInParam paramBean = new EmailConfirmInParam();
		paramBean.setParamKey(key);
		try {
			//如果有借款编号，查找对应的借款用户信息
			LoanCustomer cus = customerDao.getById(paramBean.getParamKey());
			LoanCustomer customer = new LoanCustomer();
			if(ObjectHelper.isNotEmpty(cus)){
				//根据合同编号获取客户基本信息表数据
				customer.setCustomerCode(cus.getCustomerCode());
				customer.setLoanCode(cus.getLoanCode());
				customer = customerDao.checkIfEmailConfirm(customer);
				if("1".equals(cus.getEmailIfConfirm()) && customer.getTempEmailIfConfirm()!=null
						&& "1".equals(customer.getTempEmailIfConfirm())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}else{
				//根据合同编号获取客户基本信息表数据
				customer.setCustomerCode(paramBean.getParamKey());
				customer = customerDao.checkIfEmailConfirm(customer);
				if(customer!=null && "1".equals(customer.getEmailIfConfirm())
						&& customer.getTempEmailIfConfirm()!=null
						&& "1".equals(customer.getTempEmailIfConfirm())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}
			/*Long sendTime = Long.valueOf(paramBean.getSendEmailTime());
			Long nowTime = new Date().getTime();
			if(nowTime-sendTime < 150000){*/
				
				LoanCustomer temp = new LoanCustomer();
				if(cus!=null){
					temp.setId(paramBean.getParamKey());
					temp.setEmailIfConfirm("1");
					customerDao.updateCustomerEmailConfirm(temp);
					//插入历史
					insertLoanStatusHis(cus.getLoanCode());
				}
				//更新客户基本信息表中邮箱验证临时标识
				temp.setCustomerCode(customer.getCustomerCode());
				temp.setEmailIfConfirm("1");
				customerDao.updateEmailConfirm(temp);
				
				ep.setRetCode(ReturnConstant.SUCCESS);
				ep.setRetMsg("确认成功");
			/*}else{
				ep.setRetCode(ReturnConstant.FAIL);
				ep.setRetMsg("邮箱确认回调链接超时");
			}*/
		} catch (Exception e) {
			logger.error("邮箱确认回调失败"+e.getMessage());
			ep.setRetCode(ReturnConstant.FAIL);
			ep.setRetMsg("邮箱确认回调失败"+e.getMessage());
		}
		return ep;
	}
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	public void insertLoanStatusHis(String loanCode){
		if(loanCode!=null && !"".equals(loanCode)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("loanCode", loanCode);
			LoanInfo loanInfo = loanInfoDao.selectByLoanCode(map);
			// 插入历史
			String operateStep ="邮箱变更验证"; //LoanApplyStatus.SIGN_CONFIRM.getName();
			String operateResult = "成功";
			String remark = operateStep;
			historyService.saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
		}
	}

}


