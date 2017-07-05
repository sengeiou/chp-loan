package com.creditharmony.loan.borrow.contractAudit.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.Constant;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.ContractOperateInfoDao;
import com.creditharmony.loan.borrow.contract.dao.PostponeDao;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.service.ContractAndAlreadyService;
import com.creditharmony.loan.borrow.contract.service.ContractAndPersonInfoService;
import com.creditharmony.loan.borrow.contract.service.ContractFeeService;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;
import com.creditharmony.loan.borrow.contract.service.ContractFlagService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.service.CustInfoService;
import com.creditharmony.loan.borrow.contract.service.DelayService;
import com.creditharmony.loan.borrow.contract.service.PaperLessService;
import com.creditharmony.loan.borrow.contract.service.PaperlessPhotoService;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditBanli;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditDatas;
import com.creditharmony.loan.borrow.contractAudit.service.IContractAuditModuleService;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.channel.goldcredit.service.GCCeilingService;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.yunwei.overtime.service.impl.LoanFlowServiceImpl;

/**
 * 合同审核管理页面
 * 2017-2-20
 * zm
 */
@Controller
@RequestMapping(value = "${adminPath}/contractAuditModule")
public class ContractAuditModuleController extends BaseController {

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;

	@Autowired
	private ContractFlagService ctrFlagService;

	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ContractAndPersonInfoService contractPersonService;
	
	@Resource
	private IContractAuditModuleService contractAuditModuleService;
	@Autowired
	private ContractAndAlreadyService contractAndAlreadyService;
	
	//sjw
	@Autowired
	private CityInfoDao cityInfoDao;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private LoanMinuteService lms;
	
	@Autowired
	private ContractFileService contractFileService;
	
	@Autowired
	private LoanInfoService ls;
	
	@Autowired
    private PaperLessService paperLessService;
    
    @Autowired
    private PaperlessPhotoService paperlessPhotoService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GCCeilingService service;
    
    @Autowired
    private FileDiskInfoDao diskInfoDao;
    
    @Autowired
    private RateInfoDao rateInfoDao;
    @Autowired
    private ContractFeeService contractFeeService;
    
    @Autowired
    private LoanInfoService loanInfoService; 
    
    @Autowired
    private LoanCustomerDao loanCustomerDao;
    
    @Autowired
    private PaybackDao paybackDao;
    
    @Autowired
    private ContractFeeDao contractFeeDao;
    
    @Autowired
    private LoanBankDao loanBankDao;
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private CustInfoService custInfoService;
    
    @Autowired
    private ContractOperateInfoDao contractOperateInfoDao;
    
    @Autowired
    private LoanStatusHisDao loanStatusHisDao;
    
    @Autowired
	private HistoryService historyService;
    
    @Autowired
    private DelayService delayService;
    
    @Autowired
    private LoanFlowServiceImpl loanFlowServiceImpl;
    
    @Autowired
    private PostponeDao postponeDao;
    

    private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);
    
    /**
     * 合同审核制度列表
     */
    @RequestMapping(value = "searchContractAuditDatasRead")
    public String searchContractAuditDatasRead(Model model, ContractAuditDatas ctrQryParam,HttpServletRequest request,HttpServletResponse response){
 
	    //当前登陆人
	    User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
	    //登陆人数据权限控制
        String ownerTaskCondition = Constant.FLOW_FRAME_BASKET_FETCH_MODEL_OWNER_TASK_CONDITION;
		List<Role> roleList = user.getRoleList();
		for(Role r:roleList){
		    if(  r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||              // 合同审核组长
                 r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||    // 合同审核组长
                 r.getId().equals(BaseRole.LOANER_DEPT_MASTER.id)||
                 r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){                   // 部门总监
		        ownerTaskCondition = "leader";
		        break;   
		    }
		}
	    
		
		//版本号列表
		List<Dict> dictlist=DictCache.getInstance().getAllListByType("jk_contract_ver");
	    model.addAttribute("dictlist", dictlist);
		//菜单id
		String menuId = (String) request.getParameter("menuId"); //生产用
//		String menuId = "c7916f3749904ce89bd0ded04be94a8e";//测试用
		model.addAttribute("menuId",menuId); //menuId  c7916f3749904ce89bd0ded04be94a8e
		//权限
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		//查询参数回显
		model.addAttribute("ctrQryParam", ctrQryParam);
		//产品列表
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		//金信额度，剩余金信额度
		Map<String,Object> maps = service.getJXCeillingData();
		model.addAttribute("ceiling", maps);
		//列表数据
			//构造参数
				//门店id
		if(ctrQryParam.getStoreOrgId() !=null && ctrQryParam.getStoreOrgId().length() !=0){
			String[] storeOrgIds = null;
			storeOrgIds = ctrQryParam.getStoreOrgId().split(",");
			ctrQryParam.setStoreOrgIds(storeOrgIds);
		}
				//登陆员工编号
		ctrQryParam.setUserCode(user.getId());	
				//权限判断
		ctrQryParam.setOwnerTaskCondition(ownerTaskCondition);
		if(ctrQryParam.getEndContractDueDay() != null){
			String postponeTimeTemp = DateUtils.date2Str(ctrQryParam.getEndContractDueDay(), "yyyy-MM-dd");
			ctrQryParam.setEndContractDueDay(DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		Page<ContractAuditDatas> ps=contractAuditModuleService.searchContractAuditDatas(ctrQryParam,new Page<ContractAuditDatas>(request,response));
		model.addAttribute("page", ps);
		//拆分标识的常量
		String issplit0 = ContractConstant.ISSPLIT_0;//未拆分
		String issplit1 = ContractConstant.ISSPLIT_1;//已拆分
		String issplit2 = ContractConstant.ISSPLIT_2;//拆分后
		model.addAttribute("issplit0", issplit0);
		model.addAttribute("issplit1", issplit1);
		model.addAttribute("issplit2", issplit2);
		//用于旧数据的工作流信息 所属队列标志
		model.addAttribute("flowFlag", ContractConstant.CTR_AUDIT_FLAG);
		return "borrow/contract/loanflow_contractAudit_workItems_new_read";
    }
    
    
    
    @RequestMapping(value = "searchContractAuditDatas")
    public String searchContractAuditDatas(Model model, ContractAuditDatas ctrQryParam,HttpServletRequest request,HttpServletResponse response){
 
	    //当前登陆人
	    User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
	    //登陆人数据权限控制
        String ownerTaskCondition = Constant.FLOW_FRAME_BASKET_FETCH_MODEL_OWNER_TASK_CONDITION;
		List<Role> roleList = user.getRoleList();
		for(Role r:roleList){
		    if(  r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||              // 合同审核组长
                 r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||    // 合同审核组长
                 r.getId().equals(BaseRole.LOANER_DEPT_MASTER.id)||
                 r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){                   // 部门总监
		        ownerTaskCondition = "leader";
		        break;   
		    }
		}
	    
		
		//版本号列表
		List<Dict> dictlist=DictCache.getInstance().getAllListByType("jk_contract_ver");
	    model.addAttribute("dictlist", dictlist);
		//菜单id
		String menuId = (String) request.getParameter("menuId"); //生产用
//		String menuId = "c7916f3749904ce89bd0ded04be94a8e";//测试用
		model.addAttribute("menuId",menuId); //menuId  c7916f3749904ce89bd0ded04be94a8e
		//权限
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		//查询参数回显
		model.addAttribute("ctrQryParam", ctrQryParam);
		//产品列表
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		//金信额度，剩余金信额度
		Map<String,Object> maps = service.getJXCeillingData();
		model.addAttribute("ceiling", maps);
		//列表数据
			//构造参数
				//门店id
		if(ctrQryParam.getStoreOrgId() !=null && ctrQryParam.getStoreOrgId().length() !=0){
			String[] storeOrgIds = null;
			storeOrgIds = ctrQryParam.getStoreOrgId().split(",");
			ctrQryParam.setStoreOrgIds(storeOrgIds);
		}
				//登陆员工编号
		ctrQryParam.setUserCode(user.getId());	
				//权限判断
		ctrQryParam.setOwnerTaskCondition(ownerTaskCondition);
		if(ctrQryParam.getEndContractDueDay() != null){
			String postponeTimeTemp = DateUtils.date2Str(ctrQryParam.getEndContractDueDay(), "yyyy-MM-dd");
			ctrQryParam.setEndContractDueDay(DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		Page<ContractAuditDatas> ps=contractAuditModuleService.searchContractAuditDatas(ctrQryParam,new Page<ContractAuditDatas>(request,response));
		model.addAttribute("page", ps);
		//拆分标识的常量
		String issplit0 = ContractConstant.ISSPLIT_0;//未拆分
		String issplit1 = ContractConstant.ISSPLIT_1;//已拆分
		String issplit2 = ContractConstant.ISSPLIT_2;//拆分后
		model.addAttribute("issplit0", issplit0);
		model.addAttribute("issplit1", issplit1);
		model.addAttribute("issplit2", issplit2);
		//用于旧数据的工作流信息 所属队列标志
		model.addAttribute("flowFlag", ContractConstant.CTR_AUDIT_FLAG);
		return "borrow/contract/loanflow_contractAudit_workItems_new";
    }
    
    
    
    @RequestMapping(value = "openContractAudit")
    public String openContractAudit(Model model, ContractAuditDatas ctrQryParam,HttpServletRequest request,HttpServletResponse response){
    	String menuId = request.getParameter("menuId");
    	ContractAuditBanli view = null;
    	view =contractAuditModuleService.getOneContractAudit(ctrQryParam);
    	model.addAttribute("bview",view);
    	model.addAttribute("menuId",menuId);
    	return "borrow/contract/loanflow_contractAudit_approve_new";
    }
    
    /**
     * 合同审核提交操作
     * @param model
     * @param ctrQryParam
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("commitContractAudit")
    public String commitContractAudit(Model model,RedirectAttributes redirectAttributes, WorkItemView workItem,
			ContractBusiView busiView, String redirectUrl, String flowFlag,String menuId,HttpServletRequest request,HttpServletResponse response){
    	contractAuditModuleService.commitContractAudit(workItem,busiView);
    	addMessage(redirectAttributes, "操作成功"); 
    	return "redirect:" + adminPath + redirectUrl+"?menuId="+menuId;
    }
    
    
    
    /**
	 * 异步更新标识
	 * @param model
	 */
	@RequestMapping(value = "asynChangeFlag")
	@ResponseBody
	public String asynChangeFlag(Model model, String batchColl,String attributName,String statusList,
			String remark,HttpServletRequest request,HttpServletResponse response) {
		String[] params = null;
		String[] statusArray = null;
		boolean result = true;
		if (batchColl.indexOf(";") != ContractConstant.NEGTIVE_ONE) {
			params = batchColl.split(";");
			if(StringUtils.isNotEmpty(statusList)){
			    statusArray = statusList.split(";");
			}
		} else {
			params = new String[1];
			params[0] = batchColl;
			if(StringUtils.isNotEmpty(statusList)){
			    statusArray = new String[1];
			    statusArray[0] = statusList;
			}
		}
		if(!ObjectHelper.isEmpty(statusArray)){
		    for (int i = 0; i < params.length; i++) {
		        result = isFlowAttrChanged(params[i], attributName,statusArray[i],remark);
		        if (!result) {
		            return "0";
		        }
		    }
		}
		return "1";
	}
    
	/**
	 * 更改流程属性
	 *
	 * @Create In 2015年12月1日
	 * @author 张灏
	 * @param param
	 * @param attributName
	 * @param value
	 * @param label
	 * @return Boolean
	 */
	private boolean isFlowAttrChanged(String param, String attributName,String dictLoanStatus,String remark) {
		String[] params = null;
		if (null != param && param.trim().length() > 0) {
			params = param.split(",");
			if (params.length != 7) {
				return false;
			}
			String isSplit = params[6];
			
			ContractBusiView ctrView = new ContractBusiView();
			
			
			ctrView.setApplyId(params[0]);
			ctrView.setLoanCode(params[5]);
			ctrView.setFrozenFlag(YESNO.NO.getCode());
			ctrView.setFrozenReason(" ");
			ctrView.setFrozenCode(" ");
			ctrView.setOperType(YESNO.YES.getCode());
			if(LoanApplyStatus.BIGFINANCE_RETURN.getCode().equals(dictLoanStatus)){
				ctrView.setDictLoanStatusCode(LoanApplyStatus.CONTRACT_AUDIFY.getCode());
				ctrView.setDictLoanStatus(LoanApplyStatus.CONTRACT_AUDIFY.getName());
			}else{
				ctrView.setDictLoanStatusCode(dictLoanStatus);
			}
			ctrView.setRemarks(remark);
			// 1 说明是拆分数据 不处理工作流
			if(ContractConstant.ISSPLIT_1.equals(isSplit)){
				contractAuditModuleService.updateFrozenStatus(ctrView);
				return true;
			}else{
				WorkItemView workItem = new WorkItemView();
				workItem.setWobNum(params[1]);
				workItem.setFlowName(params[2]);
				workItem.setToken(params[3]);
				workItem.setStepName(params[4]);
				ctrView.setAttrName(attributName);
				workItem.setBv(ctrView);
				flowService.saveData(workItem);
				return true;
			}
		}
		return false;
	}

 
 
}
