package com.creditharmony.loan.borrow.contract.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmByResultInBean;
import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmBySendInBean;
import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmByUpdateInBean;
import com.creditharmony.adapter.bean.in.img.Img_GetExistImgBarCodeListInBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByResultDetailOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByResultOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmBySendOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByUpdateOutBean;
import com.creditharmony.adapter.bean.out.img.Img_GetExistImgBarCodeListOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.Constant;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.approve.type.ApproveCheckType;
import com.creditharmony.core.approve.type.CheckType;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractLog;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.file.util.Zip;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.ContractOperateInfoDao;
import com.creditharmony.loan.borrow.contract.dao.PostponeDao;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractAndAlready;
import com.creditharmony.loan.borrow.contract.entity.ContractAndContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractAndPersonInfo;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.entity.ContractOperateInfo;
import com.creditharmony.loan.borrow.contract.entity.DelayEntity;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.borrow.contract.entity.PostponeEntity;
import com.creditharmony.loan.borrow.contract.entity.PostponeResult;
import com.creditharmony.loan.borrow.contract.entity.Split;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractAmountSummaryEx;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.service.ContractAndAlreadyService;
import com.creditharmony.loan.borrow.contract.service.ContractAndPersonInfoService;
import com.creditharmony.loan.borrow.contract.service.ContractFeeService;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;
import com.creditharmony.loan.borrow.contract.service.ContractFlagService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.service.ContractTempService;
import com.creditharmony.loan.borrow.contract.service.CustInfoService;
import com.creditharmony.loan.borrow.contract.service.DelayService;
import com.creditharmony.loan.borrow.contract.service.PaperLessService;
import com.creditharmony.loan.borrow.contract.service.PaperlessPhotoService;
import com.creditharmony.loan.borrow.contract.service.SplitService;
import com.creditharmony.loan.borrow.contract.util.FileCategoryUtil;
import com.creditharmony.loan.borrow.contract.util.PdfAndWord2SWFUtil;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanMinuteEx;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.channel.goldcredit.service.GCCeilingService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ImageUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowQueue;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.creditharmony.loan.yunwei.overtime.entity.LoanWorkItemView;
import com.creditharmony.loan.yunwei.overtime.service.impl.LoanFlowServiceImpl;
import com.query.ProcessQueryBuilder;

/**
 * 获取审核利率待办列表跟审批信息
 *
 * @Create In 2015年12月1日
 * @version:1.0
 * @author 张灏
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/contractAudit")
public class ContractAuditController extends BaseController {

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
    @Autowired
  	private ApplyLoanInfoService applyloanInfoService;
    
    @Autowired
	private SplitService splitService;
	
	@Autowired
	private ContractTempService contractTempService;
	
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;

    private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);
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
	private boolean isFlowAttrChanged(String param, String attributName,
			String value, String label,String dictLoanStatus,String remark) {
		String[] params = null;
		if (null != param && param.trim().length() > 0) {
			params = param.split(",");
			if (params.length != ContractConstant.SIX) {
				return false;
			}
			WorkItemView workItem = new WorkItemView();
			ContractBusiView ctrView = new ContractBusiView();
			ctrView.setApplyId(params[0]);
			workItem.setWobNum(params[1]);
			workItem.setFlowName(params[2]);
			workItem.setToken(params[3]);
			workItem.setStepName(params[4]);
			ctrView.setLoanCode(params[5]);
			
			String zcjversion = Global.getConfig("zcjversion");
			String contractVersion = Global.getConfig("contractVersion");
			String jxcontractVersion = Global.getConfig("jxcontractVersion");
			if ("loanFlag".equals(attributName)) {
			    if(label==null || StringUtils.isEmpty(label)){
			        ctrView.setLoanFlag(ChannelFlag.CHP.getName());
                    ctrView.setLoanFlagCode(ChannelFlag.CHP.getCode());
                    ctrView.setChannelFlagAdd(YESNO.NO.getCode());
//                    Contract contract=contractService.findByLoanCode(ctrView.getLoanCode());
                    		 ctrView.setContractVersion(contractVersion);
//                    		 contract.setContractHistoryVersion(c'c);
//                    		 contractService.updateContract(contract);
			    }else{
			        ctrView.setLoanFlag(label);
			        ctrView.setLoanFlagCode(value);			        
			        Contract contract=contractService.findByLoanCode(ctrView.getLoanCode());
			       
			         //判断是否更改渠道为资产家
			        
			        if(label.equals(ChannelFlag.ZCJ.getName())){	
//		        	    if(contract.getContractVersion().equals(ContractVer.VER_ONE_EIGHT.getCode())){
//		        	    	ctrView.setContractVersion(ContractVer.VER_ONE_TWO_ZCJ.getCode());
//		        	    }else{
//		        	    	ctrView.setContractVersion(zcjversion);
//		        	    }
			        	ctrView.setContractVersion(ContractVer.VER_ONE_TWO_ZCJ.getCode());
		            	 
//			            	 contract.setContractHistoryVersion(contract.getContractVersion());
//			            	 contractService.updateContract(contract);
			        //	判断是否更改渠道为金信 
			        }else if(label.equals(ChannelFlag.JINXIN.getName())){
//			        	LoanInfo loaninfo=loanInfoService.findStatusByLoanCode(ctrView.getLoanCode());
//			        	if(loaninfo.getLoanCustomerSource().equals("1")){
//			        		ctrView.setContractVersion(ContractVer.VER_ONE_EIGHT.getCode());
//			        	}else{
			        		ctrView.setContractVersion(jxcontractVersion);
//			        	}
			        			 
			        }else{
			        	 ctrView.setContractVersion(contractVersion);
			        }    
			        ctrView.setChannelFlagAdd(YESNO.YES.getCode());
			    }
				ctrView.setOperType(YESNO.NO.getCode()); // 表示只更新属性，不做其它操作
			}else if("frozenFlag".equals(attributName)){
			    ctrView.setFrozenFlag(YESNO.NO.getCode());
			    ctrView.setFrozenReason(" ");
			    ctrView.setFrozenCode(" ");
			    ctrView.setOperType(YESNO.YES.getCode());
			    if(LoanApplyStatus.BIGFINANCE_RETURN.getCode().equals(dictLoanStatus)){
			    	ctrView.setDictLoanStatusCode(LoanApplyStatus.CONTRACT_AUDIFY.getCode());
			    	ctrView.setDictLoanStatus(LoanApplyStatus.CONTRACT_AUDIFY.getName());
			    	contractService.updateLoanStatus(ctrView);
			    }else{
			    	ctrView.setDictLoanStatusCode(dictLoanStatus);
			    }
			    ctrView.setRemarks(remark);
			}else if("paperLessFlag".equals(attributName)){
			    ctrView.setPaperLessFlag(value);
			    ctrView.setOperType(YESNO.NO.getCode()); // 表示只更新属性，不做其它操作
			}
			ctrView.setAttrName(attributName);
			
			workItem.setBv(ctrView);
			flowService.saveData(workItem);
			return true;
		}
		return false;
	}

	/**
	 * 获取审核利率待办列表
	 *
	 * @param qryParam
	 *            检索条件封装类
	 * @param queue
	 *            流程属性队列名
	 * @param viewName
	 *            返回的视图名称
	 * @throws Exception
	 *             流程待办模糊查询 示例qryParam.put("F_StepName@like", "%审核利率%");
	 * 
	 */
	@RequestMapping(value = "fetchTaskItems")
	public String fetchTaskItems(Model model, LoanFlowQueryParam ctrQryParam,
			HttpServletRequest request,HttpServletResponse response, String queue, String viewName, String flowFlag)
			throws Exception {
		ProcessQueryBuilder qryParam = new ProcessQueryBuilder();
		FlowPage page = new FlowPage();
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        String ownerTaskCondition = Constant.FLOW_FRAME_BASKET_FETCH_MODEL_OWNER_TASK_CONDITION;
        Integer ps = 30;
        Integer pn = 0;
        if(StringUtils.isNotEmpty(pageSize)){
            ps = Integer.valueOf(pageSize);
        }
        if(StringUtils.isNotEmpty(pageNo)){
            pn = Integer.valueOf(pageNo); 
        }
        page.setPageSize(ps);
        page.setPageNo(pn);
		ReflectHandle.copy(ctrQryParam, qryParam);
		Map<String,Object> versionParam = new HashMap<String,Object>();
        versionParam.put("status", YESNO.YES.getCode());  // 1 启用
        versionParam.put("dictFlag",YESNO.NO.getCode());  // 0 主合同
        List<Dict> dictlist=DictCache.getInstance().getAllListByType("jk_contract_ver");
        model.addAttribute("dictlist", dictlist);
        String curVersion = Global.getConfig("presentversion");
        model.addAttribute("curVersion", curVersion);
		if (ContractConstant.RATE_AUDIT_FLAG.equals(flowFlag)) {
			qryParam.put("F_StepName",ContractConstant.RATE_AUDIT);
			queue = LoanFlowQueue.CONTRACT_COMMISSIONER;
			viewName = "loanflow_rateAudit_workItems";
			if(StringUtils.isNotEmpty(ctrQryParam.getContractCode())){
				qryParam.put("contractCode@like","%" + ctrQryParam.getContractCode() + "%");
			}	
			String[] riskLevels = {"A","B","C","D","E"};
			model.addAttribute("riskLevels", riskLevels);
			model.addAttribute("tgCode", LoanModel.TG.getCode());
			model.addAttribute("tgName", LoanModel.TG.getName());
			model.addAttribute("response", LoanFlowRoute.CONFIRMSIGN);
		 
		} else if (ContractConstant.CTR_AUDIT_FLAG.equals(flowFlag)) {
			qryParam.put("F_StepName", ContractConstant.CTR_AUDIT);
			queue =LoanFlowQueue.CONTRACT_CHECK;
			viewName = "loanflow_contractAudit_workItems";
			Map<String,Object> maps = service.getJXCeillingData();
			model.addAttribute("ceiling", maps);
			model.addAttribute("response", LoanFlowRoute.PAYMENT);
		} else if (ContractConstant.CTR_CREATE_FLAG.equals(flowFlag)) {
			qryParam.put("F_StepName", ContractConstant.CTR_CREATE);
			queue = LoanFlowQueue.CONTRACT_COMMISSIONER;
			viewName = "loanflow_contractCreate_workItems";
			if(StringUtils.isNotEmpty(ctrQryParam.getContractCode())){
				qryParam.put("contractCode@like","%" + ctrQryParam.getContractCode() + "%");
			}
		 
			model.addAttribute("response", LoanFlowRoute.CONTRACTSIGN);
		}
//		model.addAttribute("ZCJversion", ZCJversion);
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<Role> roleList = user.getRoleList();
		for(Role r:roleList){
		    if(  r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||                 // 合同制作组长
		         r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||        // 合同制作组长
		         r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||              // 合同审核组长
                 r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||    // 合同审核组长
                 r.getId().equals(BaseRole.LOANER_DEPT_MASTER.id)||                   // 部门总监
                 r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){                   
		        ownerTaskCondition = null;
		        model.addAttribute("isRateLeader", "1");
		        break;   
		    }
		}
		versionParam.put("status", null);  // 1 启用
	    versionParam.put("dictFlag",YESNO.NO.getCode());  // 0 主合同
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		model.addAttribute("flowFlag", flowFlag);
		flowService.fetchTaskItems(queue, qryParam, page,
		        ownerTaskCondition,
				LoanFlowWorkItemView.class);

		List<BaseTaskItemView> sourceWorkItems = page.getList();

		List<LoanFlowWorkItemView> workItems = this
				.convertList(sourceWorkItems);
		if (ContractConstant.CTR_AUDIT_FLAG.equals(flowFlag)){
			for(LoanFlowWorkItemView lfw : workItems){
				if(lfw !=null && (lfw.getContractMoney()==null || lfw.getContractMoney() == 0)){
					ContractAndContractFee cacp = new ContractAndContractFee();
					cacp.setContractCode(lfw.getContractCode());
					ContractAndContractFee cac = contractService.selectContractAmountAndfeePaymentAmount(cacp);
					if(ObjectHelper.isNotEmpty(cac)){
						lfw.setContractMoney(cac.getContractAmount());
						lfw.setLendingMoney(cac.getFeePaymentAmount());
					}
				}
			}
		}
		if (ArrayHelper.isNotEmpty(workItems)) {
			// 设置行列标记
			ctrFlagService.setCtrFlag(workItems);
		}
		
		for (LoanFlowWorkItemView bt : workItems) {
			if (StringUtils.isNotEmpty(bt.getModel())) {
				String modelLabel = LoanModel.parseByCode(bt.getModel()).getName();
				bt.setModelLabel(modelLabel);
			}
			//判断最优自然保证人历史数据
			if(StringHelper.isNotEmpty(Global.getConfig("bestCoborrowerTime"))&&bt.getIntoLoanTime().getTime() - DateUtils.convertDate(Global.getConfig("bestCoborrowerTime")).getTime() > 0){
				bt.setBestCoborrowerFlag("1");
			}else{
				bt.setBestCoborrowerFlag("0");
			}
		}
		String menuId = (String) request.getParameter("menuId");
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("menuId",menuId);
		model.addAttribute("ctrQryParam", ctrQryParam);
		model.addAttribute("workItems", workItems);
		model.addAttribute("page", page);
		return "borrow/contract/" + viewName;
	}
	
	/**
	 * 借款人服务部部门总监以只读方式获取费率审核和合同制作两个节点的所有待办列表信息
	 *
	 * @param qryParam
	 *            检索条件封装类
	 * @param queue
	 *            流程属性队列名
	 * @throws Exception
	 *             流程待办模糊查询 示例qryParam.put("F_StepName@like", "%审核利率%");
	 * 
	 */
	@RequestMapping(value = "fetchTaskItems4ReadOnly")
	public String fetchTaskItems4ReadOnly(Model model,
			LoanFlowQueryParam ctrQryParam, HttpServletRequest request,
			String flowFlag) throws Exception {
		String queue = "";
		String viewName = "";
		ProcessQueryBuilder qryParam = new ProcessQueryBuilder();
		FlowPage page = new FlowPage();
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("pageNo");
		String ownerTaskCondition = Constant.FLOW_FRAME_BASKET_FETCH_MODEL_OWNER_TASK_CONDITION;
		Integer ps = 30;
		Integer pn = 0;
		if (StringUtils.isNotEmpty(pageSize)) {
			ps = Integer.valueOf(pageSize);
		}
		if (StringUtils.isNotEmpty(pageNo)) {
			pn = Integer.valueOf(pageNo);
		}
		page.setPageSize(ps);
		page.setPageNo(pn);
		ReflectHandle.copy(ctrQryParam, qryParam);
		Map<String, Object> versionParam = new HashMap<String, Object>();
		versionParam.put("status", YESNO.YES.getCode()); // 1 启用
		versionParam.put("dictFlag", YESNO.NO.getCode()); // 0 主合同
		List<String> versions = contractService
				.getContractVersion(versionParam);
		if (ContractConstant.RATE_AUDIT_FLAG.equals(flowFlag)) {
			qryParam.put("F_StepName", ContractConstant.RATE_AUDIT);
			queue = LoanFlowQueue.CONTRACT_COMMISSIONER;
			viewName = "loanflow_rateAudit_readonly_workItems";
			String[] riskLevels = { "A", "B", "C", "D", "E" };
			model.addAttribute("riskLevels", riskLevels);
			model.addAttribute("tgCode", LoanModel.TG.getCode());
			model.addAttribute("tgName", LoanModel.TG.getName());
			model.addAttribute("response", LoanFlowRoute.CONFIRMSIGN);
			if (!ObjectHelper.isEmpty(versions)) {
				model.addAttribute("curVersion", versions.get(0));
			}
		} else if (ContractConstant.CTR_CREATE_FLAG.equals(flowFlag)) {
			qryParam.put("F_StepName", ContractConstant.CTR_CREATE);
			queue = LoanFlowQueue.CONTRACT_COMMISSIONER;
			viewName = "loanflow_contractCreate_readonly_workItems";
			if (!ObjectHelper.isEmpty(versions)) {
				model.addAttribute("curVersion", versions.get(0));
			}
			model.addAttribute("response", LoanFlowRoute.CONTRACTSIGN);
		}else if(ContractConstant.CTR_AUDIT_FLAG.equals(flowFlag)) {
			//合同审核待办
			qryParam.put("F_StepName", ContractConstant.CTR_AUDIT);
			queue =LoanFlowQueue.CONTRACT_CHECK;
			viewName = "loanflow_contractAudit_readonly_workItems";
			Map<String,Object> maps = service.getJXCeillingData();
			model.addAttribute("ceiling", maps);
			model.addAttribute("response", LoanFlowRoute.PAYMENT);
		}
		User user = (User) UserUtils.getSession().getAttribute(
				SystemConfigConstant.SESSION_USER_INFO);
		List<Role> roleList = user.getRoleList();
		for (Role r : roleList) {
			if (r.getId().equals(BaseRole.LOANER_DEPT_MASTER.id)) {
				ownerTaskCondition = null;
				model.addAttribute("isRateLeader", "1");
				break;
			}
		}
		versionParam.put("status", null); // 1 启用
		versionParam.put("dictFlag", YESNO.NO.getCode()); // 0 主合同
		versions = contractService.getContractVersion(versionParam);
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		model.addAttribute("flowFlag", flowFlag);
		flowService.fetchTaskItems(queue, qryParam, page, ownerTaskCondition,
				LoanFlowWorkItemView.class);

		List<BaseTaskItemView> sourceWorkItems = page.getList();

		List<LoanFlowWorkItemView> workItems = this
				.convertList(sourceWorkItems);

		if (ArrayHelper.isNotEmpty(workItems)) {
			// 设置行列标记
			ctrFlagService.setCtrFlag(workItems);
		}

		for (LoanFlowWorkItemView bt : workItems) {
			if (StringUtils.isNotEmpty(bt.getModel())) {
				String modelLabel = LoanModel.parseByCode(bt.getModel())
						.getName();
				bt.setModelLabel(modelLabel);
			}
		}
		model.addAttribute("versions", versions);
		model.addAttribute("ctrQryParam", ctrQryParam);
		model.addAttribute("workItems", workItems);
		model.addAttribute("page", page);
		return "borrow/contract/readonly/" + viewName;
	}

	/**
	 * 异步更新标识
	 * @param batchColl 目标数据集合 格式(((,,,,)1(;)?)?)
	 * @param batchValue 更改的值
	 */
	@RequestMapping(value = "asynChangeFlag")
	@ResponseBody
	public String asynChangeFlag(Model model, String batchColl,
			String attributName, String batchValue, String batchLabel,String statusList,String remark) {
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
		        result = isFlowAttrChanged(params[i], attributName, batchValue,
		                batchLabel,statusArray[i],remark);
		        if (!result) {
		            return "0";
		        }
		    }
		}else{
		    for (int i = 0; i < params.length; i++) {
                result = isFlowAttrChanged(params[i], attributName, batchValue,
                        batchLabel,null,remark);
                if (!result) {
                    return "0";
                }
            }  
		}
		return "1";
	}

	/**
	 * 流程提交
	 *
	 * @param redirectAttributes
	 *            重定向
	 * @param WorkItemView
	 *            流程工作项
	 * @param busiView
	 *            业务视图
	 * @param redirectUrl
	 *            重定向URL
	 * @param queue
	 *            流程属性队列
	 * @param viewName
	 *            重定向之后返回的视图名字
	 * @throws IOException 
	 */
	
	
	
	
	@RequestMapping("dispatchFlow")
	public String dispatchFlow(Model model,
			RedirectAttributes redirectAttributes, WorkItemView workItem,
			ContractBusiView busiView, String redirectUrl, String flowFlag,String menuId,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdFor=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (ObjectHelper.isEmpty(workItem.getResponse())) {
			workItem.setResponse(null);
		}
		if("TO_PAY_CONFIRM".equals(workItem.getResponse())||"TO_KING_OPEN".equals(workItem.getResponse())){
			
			//当合同审核提交时,往呼叫中心推送数据，同时将工作流和数据库的回访状态改为待回访
			//判断时间，旧数据不走流程
			Contract contract=busiView.getContract();
			String loancode=busiView.getLoanCode();
			if(loancode==null){
				loancode=contract.getLoanCode();
			}
			//查询相关信息插入到呼叫中心回访表
			Contract contractOld=contractService.findByContractCode(contract.getContractCode());
			LoanInfo loanInfoOld=loanInfoService.findStatusByLoanCode(loancode);
			Map<String,Object> maploancode=new HashMap<String,Object>();
			maploancode.put("loanCode", loancode);
			LoanCustomer loanCustomerOld=loanCustomerDao.selectByLoanCode(maploancode);
			Map<String,Object> mapcontractcode=new HashMap<String,Object>();
			mapcontractcode.put("contractCode", contract.getContractCode());
			Map<String,Object> mapPay=new HashMap<String,Object>();
			mapPay.put("contractCode", contract.getContractCode());
			Payback payback=paybackDao.selectpayBack(mapPay);
			ContractFee contractFee=contractFeeDao.findByContractCode(contract.getContractCode());
			LoanBank loanBank=loanBankDao.selectByLoanCode(loancode);
			//比对时间，旧的数据不走放款前回访流程
			String compareTime=Global.getConfig("loan.compareTime");
			long customerIntoTime=0l;
			long compareTimeLon=0l;
			long  differTime=0l;
			if(loanInfoOld!=null&&loanInfoOld.getCustomerIntoTime()!=null){
				customerIntoTime=loanInfoOld.getCustomerIntoTime().getTime();
			}
			if(compareTime!=null){
				try{
					Date dateCompare=sdFor.parse(compareTime);
					compareTimeLon=dateCompare.getTime();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			differTime=customerIntoTime-compareTimeLon;
			//信易借产品  不推送数据到呼叫中心
			boolean isFlag=true;
			if(contractOld!=null&&"A013".equals(contractOld.getProductType())){
				isFlag=false;
			}
			if(differTime>0&&ContractConstant.CTR_AUDIT.equals(workItem.getStepName())&&isFlag){
				logger.info("调用放款前回访查询接口>>>开始");
				
				//调用接口查询  
				CshLoanConfirmByResultInBean resInBean = new CshLoanConfirmByResultInBean(); 
				List<String> param = new ArrayList<String>(); 
				param.add(contract.getContractCode()); 
				resInBean.setContractCodeList(param); 
				ClientPoxy serviceQue = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_RESULT); 
				CshLoanConfirmByResultOutBean outBean = (CshLoanConfirmByResultOutBean) serviceQue.callService(resInBean); 
				System.out.println(outBean.getParam()); 
				if(outBean.getParam().contains("0000")&&outBean.getItems()==null){//查询结果为空，表示首次插入
					//调接口插入数据 更新回访状态为待回访，推送次数为一，客户状态为放款前，推送时间 为当前时间
					logger.info("调用放款前回访插入接口");
					CshLoanConfirmBySendInBean inBean=new CshLoanConfirmBySendInBean();
					if(contractFee!=null){
						inBean.setOutboundFee(contractFee.getFeePetition()); 
					}
					if(payback!=null){
						if(payback.getPaybackDay()!=null){
							inBean.setReimbursementDate(payback.getPaybackDay());
						}
					}
					if(loanBank!=null){
						
						inBean.setReimbursementBank(DictCache.getInstance().getDictLabel("tz_open_bank", loanBank.getBankName())); 
						inBean.setBankAccount(loanBank.getBankAccount()); 
					}
					if(loanCustomerOld!=null){
						inBean.setSex(DictCache.getInstance().getDictLabel("jk_sex", loanCustomerOld.getCustomerSex())); 
						inBean.setMobilePhone(loanCustomerOld.getCustomerPhoneFirst());
					}
					if(loanInfoOld!=null){
						inBean.setLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfoOld.getDictLoanUse())); 
						User loanManager = userManager.get(loanInfoOld.getLoanManagerCode());
						if(loanManager!=null){
							inBean.setCustomerManager(loanManager.getName()); 
						}
						
						if(loanInfoOld.getCustomerIntoTime()!=null){
							inBean.setNewTime(sdf.format(loanInfoOld.getCustomerIntoTime())); 
						}
						inBean.setIsUrgent(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfoOld.getLoanUrgentFlag())); 
						OrgCache orgCache = OrgCache.getInstance();
						Org storeOrg = orgCache.get(loanInfoOld.getLoanStoreOrgId());
						if(storeOrg!=null){
							inBean.setStoreName(storeOrg.getName()); 
						}
						LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
						List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
						for(LoanPrdMngEntity entity:productList){
							if(entity.getProductCode().equals(loanInfoOld.getProductType())){
								inBean.setProductType(entity.getProductName()); 
							}
						}
						
					}
					if(contractOld!=null){
						inBean.setContractCode(contractOld.getContractCode()); 
						inBean.setCustomerName(contractOld.getLoanName()); 
						inBean.setCertNum(contractOld.getLoanCertNum()); 
						inBean.setMonthPayment(contractOld.getMonthPayTotalAmount()); 
						if(contractOld.getContractMonths()!=null){
							inBean.setLoanPeriods(contractOld.getContractMonths().intValue()); 
						}
						if(contractOld.getContractFactDay()!=null){
							inBean.setContractSignDate(sdf.format(contractOld.getContractFactDay()));
						}
						inBean.setContractFee(contractOld.getContractAmount()); 
						
						inBean.setMark(DictCache.getInstance().getDictLabel("jk_channel_flag", contractOld.getChannelFlag())); 
						inBean.setCorborrow(contractOld.getCoboName()); 
					}
					
					//inBean.setAdFee("1"); //征信费
					//调用插入接口
					ClientPoxy serviceIns = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_SEND); 
					CshLoanConfirmBySendOutBean snedOutBean = (CshLoanConfirmBySendOutBean) serviceIns.callService(inBean); 
					System.out.println(snedOutBean.getParam());
					if(contract!=null){
						//0表示待回访
						contract.setRevisitStatus("0");
						contract.setPushTime(new Date());
						contract.setRevisitReason("");
						if(contract.getPushNumber()!=null){
							//推送次数，为之前的次数加一
							contract.setPushNumber(contract.getPushNumber()+1);
						}else{
							//首次推送
							contract.setPushNumber(1);
						}
					}
				}else if(outBean.getItems()!=null){
					CshLoanConfirmByResultDetailOutBean detailOutBean=outBean.getItems().get(0);
					//查询回访失败且回访次数小于3 或待回访数据
					if((detailOutBean.getRevisitStatus().equals("0"))||(detailOutBean.getRevisitStatus().equals("-1")&&detailOutBean.getRevisitNumber()<3)){
						//调接口update 更新回访状态为待回访，推送次数加一，客户状态为放款前，推送时间 更新
						logger.info("调用放款前回访修改接口");
						CshLoanConfirmByUpdateInBean updateInBean = new CshLoanConfirmByUpdateInBean(); 
						if(contractFee!=null){
							updateInBean.setOutboundFee(contractFee.getFeePetition()); 
						}
						if(payback!=null){
							if(payback.getPaybackDay()!=null){
								updateInBean.setReimbursementDate(payback.getPaybackDay());
							}
						}
						if(loanBank!=null){
							updateInBean.setReimbursementBank(DictCache.getInstance().getDictLabel("tz_open_bank", loanBank.getBankName())); 
							updateInBean.setBankAccount(loanBank.getBankAccount()); 
						}
						if(loanCustomerOld!=null){//jk_sex
							updateInBean.setSex(DictCache.getInstance().getDictLabel("jk_sex", loanCustomerOld.getCustomerSex())); 
							updateInBean.setMobilePhone(loanCustomerOld.getCustomerPhoneFirst());
							
						}
						if(loanInfoOld!=null){
							updateInBean.setLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfoOld.getDictLoanUse())); 
							User loanManager = userManager.get(loanInfoOld.getLoanManagerCode());
							if(loanManager!=null){
								updateInBean.setCustomerManager(loanManager.getName()); 
							}
							if(loanInfoOld.getCustomerIntoTime()!=null){
								updateInBean.setNewTime(sdf.format(loanInfoOld.getCustomerIntoTime())); 
							}
							updateInBean.setIsUrgent(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfoOld.getLoanUrgentFlag())); 
							OrgCache orgCache = OrgCache.getInstance();
							Org storeOrg = orgCache.get(loanInfoOld.getLoanStoreOrgId());
							if(storeOrg!=null){
								updateInBean.setStoreName(storeOrg.getName()); 
							}
							LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
							List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
							for(LoanPrdMngEntity entity:productList){
								if(entity.getProductCode().equals(loanInfoOld.getProductType())){
									updateInBean.setProductType(entity.getProductName()); 
								}
							}
						}
						if(contractOld!=null){
							updateInBean.setContractCode(contractOld.getContractCode()); 
							updateInBean.setCustomerName(contractOld.getLoanName()); 
							updateInBean.setCertNum(contractOld.getLoanCertNum()); 
							updateInBean.setMonthPayment(contractOld.getMonthPayTotalAmount()); 
							if(contractOld.getContractMonths()!=null){
								updateInBean.setLoanPeriods(contractOld.getContractMonths().intValue()); 
							}
							if(contractOld.getContractFactDay()!=null){
								updateInBean.setContractSignDate(sdf.format(contractOld.getContractFactDay()));
							}
							updateInBean.setContractFee(contractOld.getContractAmount()); 
							updateInBean.setMark(DictCache.getInstance().getDictLabel("jk_channel_flag", contractOld.getChannelFlag())); 
							updateInBean.setCorborrow(contractOld.getCoboName()); 
						}
						//更新回访状态为待回访
						updateInBean.setRevisitStatus("0");
						//更新客户状态为放款前
						updateInBean.setCustomerStatus("0");
						//更新推送次数+1
						Contract contractByNum=contractService.findByContractCode(contract.getContractCode());
						if(contractByNum!=null){
							Integer number=contractByNum.getPushNumber();
							if(number!=null){
								updateInBean.setPushNumber(number.intValue()+1);
							}
							
						}
						//更新推送时间
						updateInBean.setPushTime(new Date());
						ClientPoxy service = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_UPDATE); 
						CshLoanConfirmByUpdateOutBean updateOBean = (CshLoanConfirmByUpdateOutBean) service.callService(updateInBean); 
						System.out.println(updateOBean.getParam()); 
						if(contract!=null){
							//0表示待回访
							contract.setRevisitStatus("0");
							contract.setPushTime(new Date());
							contract.setRevisitReason("");
							if(contract.getPushNumber()!=null){
								//推送次数，为之前的次数加一
								contract.setPushNumber(contract.getPushNumber()+1);
							}else{
								//首次推送
								contract.setPushNumber(1);
							}
						}
					}
				}
				
				
				
			}
		}
		String zcjversion = Global.getConfig("zcjversion");
		String jxcontractVersion = Global.getConfig("jxcontractVersion");
		if(ContractConstant.CTR_CREATE.equals(workItem.getStepName())){
			Contract c=contractService.findByContractCode(busiView.getContract().getContractCode());
			//5 -ZCJ
			if(busiView.getLoanFlagCode()!=null && "5".equals(busiView.getLoanFlagCode())){
				if(!c.getContractVersion().equals(ContractVer.VER_ONE_TWO_ZCJ.getCode())){
					busiView.setContractVersion(zcjversion);
				}
			}
			//0 金信
			if(busiView.getLoanFlagCode()!=null && "0".equals(busiView.getLoanFlagCode())){
				busiView.setContractVersion(jxcontractVersion);
			}
		}
		workItem.setBv(busiView);
		if(ContractConstant.CTR_CREATE.equals(workItem.getStepName())
			    ||ContractConstant.RATE_AUDIT.equals(workItem.getStepName())||
			      ContractConstant.CTR_AUDIT.equals(workItem.getStepName())){
			    User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		        List<Role> roleList = user.getRoleList();
		        for(Role r:roleList){
		            if(r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||
		                r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||
		                r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
	                    r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||
	                    r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
		                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
		                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)
	                    ){
		                workItem.setCheckDealUser(false);
		                break;   
		            }
		        }
			}
		// 合同签订阶段,图片合成
        if(ContractConstant.CUST_SERVICE_SIGN.equals(workItem.getStepName())
                 && ContractResult.CONTRACT_SUCCEED.getCode().equals(busiView.getDictOperateResult())
                 && YESNO.YES.getCode().equals(busiView.getPaperLessFlag())){
            String webPath = request.getSession().getServletContext().getRealPath("/");
            logger.info("dispatchFlow--->图片合成路径: "  + webPath);
            List<PaperlessPhoto> PaperlessPhotoList =  paperlessPhotoService.getByLoanCode(busiView.getOldLoanCode()); 
            DmService dmService = DmService.getInstance();
            InputStream input1 = null;
            InputStream input2 =null;
            OutputStream output1 = null;
            OutputStream output2 = null;
            File file1 = null;
            File file2 = null;
            File file3 = null;
            InputStream ceInput = null;
            for(PaperlessPhoto photo:PaperlessPhotoList){
            	logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+photo.getIdPhotoId()+".jpg");
                file1 = new File(webPath+photo.getIdPhotoId()+".jpg");
                file2 = new File(webPath+photo.getSpotPhotoId()+".jpg");
                file3 = new File(webPath+"COMPOSE_"+System.currentTimeMillis()+".jpg");
                logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+photo.getIdPhotoId()+".jpg");
                logger.info("dispatchFlow ->图片合成 loan_code = " +photo.getLoanCode());
                logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+"COMPOSE_"+System.currentTimeMillis()+".jpg");
                int c;  
                if(!file1.exists()){
                	output1 = new FileOutputStream(file1);
                    file1.createNewFile();
                    logger.info("photo1 id is " + photo.getIdPhotoId());
                    input1 = dmService.downloadDocument(photo.getIdPhotoId());
                    while ((c = input1.read()) != -1) {  
                    	output1.write(c);  
                    } 
                    input1.close();
                    output1.close();
                }else{
                	logger.info("本地读取file1："+webPath+photo.getIdPhotoId()+".jpg");
                }
                if(!file2.exists()){
                	output2 = new FileOutputStream(file2);
                    file2.createNewFile();
                    logger.info("photo2 id is " + photo.getSpotPhotoId());
                    input2 = dmService.downloadDocument(photo.getSpotPhotoId());
                    while ((c = input2.read()) != -1) {  
                    	output2.write(c);  
                    } 
                    input2.close();
                    output2.close();
                }else{
                	logger.info("本地读取file2："+webPath+photo.getSpotPhotoId()+".jpg");
                }
                logger.info(file1.getAbsolutePath());
                ImageUtils.xPic(file2.getAbsolutePath(),file1.getAbsolutePath(),file3.getAbsolutePath());
                ceInput = new FileInputStream(file3);
                DocumentBean doc = dmService.createDocument("COMPOSE_"+System.currentTimeMillis()+".jpg",
                        ceInput, DmService.BUSI_TYPE_LOAN,CeFolderType.SIGN_UPLOAD.getName(),
                        busiView.getContract().getContractCode(), UserUtils.getUser().getId());
                paperLessService.updateComposeDocId(photo.getRelationId(),doc.getDocId(),photo.getCustomerType());
                
                
                ceInput.close();
                file1.deleteOnExit();
                file2.deleteOnExit();
                file3.deleteOnExit();
            }
        }
        
      //客户更新最新邮箱
        String loancode=busiView.getLoanCode();
        Contract contract=busiView.getContract();
		if(loancode==null){
			loancode=contract.getLoanCode();
		}
        HashMap<String, Object> param=new HashMap<String, Object>();
    	param.put("loanCode", loancode);
    	LoanCustomer temp=loanCustomerDao.selectByLoanCode(param);
    	if(temp!=null){
	        LoanCustomer cus = new LoanCustomer();
	        cus.setCustomerEmail(busiView.getEmail());
	        cus.setId(temp.getId());
	        loanCustomerDao.update(cus);
    	}
        
        logger.info("流程response is: " + workItem.getResponse());
        String issplit=busiView.getContract().getIssplit();
        if((!"0".equals(issplit)) && ContractConstant.CUST_SERVICE_SIGN.equals(workItem.getStepName())){
        	applyloanInfoService.contractUpload(workItem);
        }else{
        	flowService.dispatch(workItem);
        }
		if (flowFlag != null && flowFlag.trim().length() != 0) {
			redirectAttributes.addAttribute("flowFlag", flowFlag);
		}
		addMessage(redirectAttributes, "操作成功"); 
		return "redirect:" + adminPath + redirectUrl+"?menuId="+menuId;
	}
	
	/**
	 * 手动验证提交 退回
	 *
	 * @param redirectAttributes
	 *            重定向
	 * @param WorkItemView
	 *            流程工作项
	 * @param busiView
	 *            业务视图
	 * @param redirectUrl
	 *            重定向URL
	 * @param queue
	 *            流程属性队列
	 * @param viewName
	 *            重定向之后返回的视图名字
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("dispatchFlowVer")
	public String dispatchFlowVer(Model model,
			RedirectAttributes redirectAttributes, WorkItemView workItem,
			ContractBusiView busiView, String redirectUrl, String flowFlag,String menuId,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		AjaxNotify notify = new AjaxNotify();
		//依据手动验证结果判断
		String verification=null;
		if(busiView!=null){
			verification=busiView.getVerification();
		}
		try{
			if(verification!=null&&ContractResult.CONTRACT_SUCCEED.getCode().equals(verification)){
				//如果手动验证通过 将结果插入到合同表操作记录表
				ContractOperateInfo contractOperateInfo = new ContractOperateInfo();
				contractOperateInfo.setDictOperateType(ContractLog.CONTRACT_AUDIFY.getCode());
				 // 审核意见
				contractOperateInfo.setReturnReason("手动验证通过");
				contractOperateInfo.setVerification(busiView.getVerification());
				if(busiView!=null&&busiView.getContract()!=null){
					contractOperateInfo.setContractCode(busiView.getContract().getContractCode());
					contractOperateInfo.setLoanCode(busiView.getContract().getLoanCode());
				}
				User user = UserUtils.getUser();
				//操作人相关信息
				contractOperateInfo.setOperator(user.getUserCode());
				contractOperateInfo.setOperateTime(new Date());
				contractOperateInfo.setOperateOrgCode(user.getOrgIds());
				contractOperateInfo.setIsNewRecord(false);
				contractOperateInfo.preInsert();
				contractOperateInfoDao.insertSelective(contractOperateInfo);
				 
				LoanStatusHis record = new LoanStatusHis();
				// APPLY_ID
				if(busiView!=null){
					record.setApplyId(busiView.getApplyId());
				}
				
				// 借款Code
				if (!ObjectHelper.isEmpty(busiView.getContract())) {
					if (StringUtils.isNotEmpty(busiView.getContract()
							.getLoanCode()))
						record.setLoanCode(busiView.getContract()
								.getLoanCode());
				}
				// 状态
				record.setDictLoanStatus(LoanApplyStatus.CONTRACT_AUDIFY.getCode());
				// 操作步骤(回退,放弃,拒绝 等)
				record.setOperateStep(ContractConstant.CTR_AUDIT);
				// 操作结果
				ContractResult contractResult = ContractResult.parseByCode(verification);
				record.setOperateResult(contractResult.getName());

				// 备注
				record.setRemark("手动验证通过");
				// 系统标识
				record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
				// 设置Crud属性值
				record.preInsert();
				// 操作时间
				record.setOperateTime(record.getCreateTime());
				// 操作人记录当前登陆系统用户名称
				record.setOperator(user.getName());
				if (!ObjectHelper.isEmpty(user.getRole())) {
					// 操作人角色
					record.setOperateRoleId(user.getRole().getId());
				}
				if (!ObjectHelper.isEmpty(user.getDepartment())) {
					// 机构编码
					record.setOrgCode(user.getDepartment().getId());
				}
				loanStatusHisDao.insertSelective(record);
				notify.setSuccess(BooleanType.TRUE);
				notify.setMessage("手动验证通过");
			}else if(verification!=null&&ContractResult.CONTRACT_REBACK.getCode().equals(verification)){
				//手动验证不通过 退回到（待上传合同或待确认签署）节点 走工作流
				if(ContractConstant.CTR_CREATE.equals(workItem.getStepName())
					    ||ContractConstant.RATE_AUDIT.equals(workItem.getStepName())||
					      ContractConstant.CTR_AUDIT.equals(workItem.getStepName())){
					    User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
				        List<Role> roleList = user.getRoleList();
				        for(Role r:roleList){
				            if(r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||
				                r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||
				                r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
			                    r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||
			                    r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
				                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
				                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){
				                workItem.setCheckDealUser(false);
				                break;   
				            }
				        }
					}
				workItem.setBv(busiView);
		        logger.info("流程response is: " + workItem.getResponse());
				flowService.dispatch(workItem);
				if (flowFlag != null && flowFlag.trim().length() != 0) {
					redirectAttributes.addAttribute("flowFlag", flowFlag);
				}
				notify.setSuccess(BooleanType.TRUE);
				notify.setMessage("手动验证不通过操作成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			notify.setSuccess(BooleanType.TRUE);
			notify.setMessage("手动验证异常");
		}
		
		return jsonMapper.toJson(notify); 
		
	}
	
	@ResponseBody
	@RequestMapping("backFlow")
	public String backFlow(Model model,
            RedirectAttributes redirectAttributes, WorkItemView workItem,
            ContractBusiView busiView, String redirectUrl, String flowFlag,
            HttpServletRequest request,HttpServletResponse response){
	    if (ObjectHelper.isEmpty(workItem.getResponse())) {
            workItem.setResponse(null);
        }
        workItem.setBv(busiView);
        if(ContractConstant.CTR_CREATE.equals(workItem.getStepName())
                ||ContractConstant.RATE_AUDIT.equals(workItem.getStepName())||
                  ContractConstant.CTR_AUDIT.equals(workItem.getStepName())){
                User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
                List<Role> roleList = user.getRoleList();
                for(Role r:roleList){
                    if(r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||
                        r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||
                        r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
                        r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||
                        r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
    	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
    	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){
                        workItem.setCheckDealUser(false);
                        break;   
                    }
                }
            }
        logger.info("流程response is: " + workItem.getResponse());
        flowService.dispatch(workItem);
        return "success";
	}
	
	/**
	 * 批量退回
	 * @author FuLiXin
	 * @date 2017年1月3日 下午6:11:16
	 */
	@ResponseBody
	@RequestMapping("backListFlow")
	public String backListFlow(Model model, WorkItemView workItemList,
            ContractBusiView busiViewList,HttpServletRequest request,HttpServletResponse response,String flowFlag){
		int error=0;
		int count=1;
		if(workItemList.getFlowName().contains(",")){
			count = workItemList.getFlowName().split(",").length;
			for(int i=0;i<workItemList.getFlowName().split(",").length;i++){
			    try{
					WorkItemView workItem=new WorkItemView();
					workItem.setFlowId(workItemList.getFlowName().split(",")[i]);
					if(ContractConstant.RATE_AUDIT_FLAG.equals(flowFlag)){
						Map<String,Object> param = new HashMap<String,Object>();
			            param.put("dictSysFlag", ModuleName.MODULE_APPROVE.value);
			            param.put("applyId", busiViewList.getApplyId().split(",")[i]);
			            List<LoanStatusHis> loanStatus = historyService.findLastApproveNote(param);
			            LoanStatusHis loanStatusHis = loanStatus.get(0);
			            String lastStatus = loanStatusHis.getOperateStep();
			            if(CheckType.XS_SECOND_CREDIT_AUDIT.getName().equals(lastStatus)){
			            	workItem.setResponse(LoanFlowRoute.LETTERREVIEW);
			            }else if(CheckType.XS_THRED_CREDIT_AUDIT.getName().equals(lastStatus)){
			            	workItem.setResponse(LoanFlowRoute.FINALJUDGTEAM);
			            }else if(CheckType.XS_FINAL_CREDIT_AUDIT.getName().equals(lastStatus)){
			            	workItem.setResponse(LoanFlowRoute.FINALJUDG);
			            }else if(CheckType.FY_SECOND_CREDIT_AUDIT.getName().equals(lastStatus)){
			            	workItem.setResponse(LoanFlowRoute.RECONSIDERREVIEW);
			            }else if(CheckType.FY_FINAL_CREDIT_AUDIT.getName().equals(lastStatus)){
			            	workItem.setResponse(LoanFlowRoute.RECONSIDERFINALJUDG);
			            }
					}else{
						workItem.setResponse(workItemList.getResponse());
					}
					workItem.setStepName(workItemList.getStepName().split(",")[i]);
					workItem.setToken(workItemList.getToken().split(",")[i]);
					workItem.setWobNum(workItemList.getWobNum().split(",")[i]);
					ContractBusiView busiView = new ContractBusiView();
					busiView.setApplyId(busiViewList.getApplyId().split(",")[i]);
					busiView.setRemarks(busiViewList.getRemarks());
					busiView.setDictOperateResult(busiViewList.getDictOperateResult());
					String contractCode=busiViewList.getContract().getContractCode();
					String loanCode=busiViewList.getContract().getLoanCode();
					Contract contract=new Contract();
					contract.setContractCode(contractCode.split(",")[i]);
					contract.setLoanCode(loanCode.split(",")[i]);
					busiView.setContract(contract);
					workItem.setBv(busiView);
			        if(ContractConstant.CTR_CREATE.equals(workItem.getStepName())
			                ||ContractConstant.RATE_AUDIT.equals(workItem.getStepName())||
			                  ContractConstant.CTR_AUDIT.equals(workItem.getStepName())){
			                User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			                List<Role> roleList = user.getRoleList();
			                for(Role r:roleList){
			                    if(r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||
			                        r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||
			                        r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
			                        r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||
			                        r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
			    	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
			    	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){
			                        workItem.setCheckDealUser(false);
			                        break;   
			                    }
			                }
			            }
			        logger.info("流程response is: " + workItem.getResponse());
			        flowService.dispatch(workItem);
				}catch(Exception e){
					error++;
				}
			}
		}else{
			try{
				if(ContractConstant.RATE_AUDIT.equals(workItemList.getStepName())){
				 	Map<String,Object> param = new HashMap<String,Object>();
		            param.put("dictSysFlag", ModuleName.MODULE_APPROVE.value);
		            param.put("applyId", busiViewList.getApplyId());
		            List<LoanStatusHis> loanStatus = historyService.findLastApproveNote(param);
		            LoanStatusHis loanStatusHis = loanStatus.get(0);
		            String lastStatus = loanStatusHis.getOperateStep();
		            if(CheckType.XS_SECOND_CREDIT_AUDIT.getName().equals(lastStatus)){
		            	workItemList.setResponse(LoanFlowRoute.LETTERREVIEW);
		            }else if(CheckType.XS_THRED_CREDIT_AUDIT.getName().equals(lastStatus)){
		                workItemList.setResponse(LoanFlowRoute.FINALJUDGTEAM);
		            }else if(CheckType.XS_FINAL_CREDIT_AUDIT.getName().equals(lastStatus)){
		                workItemList.setResponse(LoanFlowRoute.FINALJUDG);
		            }else if(CheckType.FY_SECOND_CREDIT_AUDIT.getName().equals(lastStatus)){
		                workItemList.setResponse(LoanFlowRoute.RECONSIDERREVIEW);
		            }else if(CheckType.FY_FINAL_CREDIT_AUDIT.getName().equals(lastStatus)){
		                workItemList.setResponse(LoanFlowRoute.RECONSIDERFINALJUDG);
		            }
				}
				workItemList.setBv(busiViewList);
		        if(ContractConstant.CTR_CREATE.equals(workItemList.getStepName())
		                ||ContractConstant.RATE_AUDIT.equals(workItemList.getStepName())||
		                  ContractConstant.CTR_AUDIT.equals(workItemList.getStepName())){
		                User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		                List<Role> roleList = user.getRoleList();
		                for(Role r:roleList){
		                    if(r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||
		                        r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||
		                        r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
		                        r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||
		                        r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
		    	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
		    	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){
		                    	workItemList.setCheckDealUser(false);
		                        break;   
		                    }
		                }
		            }
		        logger.info("流程response is: " + workItemList.getResponse());
		        flowService.dispatch(workItemList);
			}catch(Exception e){
				error++;
			}
		}
		
        return "退回成功"+(count-error)+"条,失败"+error+"条";
	}

	@RequestMapping("initPreviewContract")
	public String initPreviewContract(Model model ,String contractCode){
	    List<ContractFile> files = contractFileService.findContractFileByContractCode(contractCode);
	    String role = YESNO.NO.getCode();
	    model.addAttribute("files", files);
	    model.addAttribute("role", role);
	    return "borrow/contract/contractFilePreview";
	}  
	@RequestMapping("initPreviewSignContract")
	public String initPreviewSignContract(Model model,String contractCode){
	    List<ContractFile> files = contractFileService.findContractFileByContractCode(contractCode);
	    String role = YESNO.NO.getCode();
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<Role> roleList = user.getRoleList();
		/**合同审核组长、合同制作组长**/
		for (Role r : roleList) {			
			if (r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
	                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)) {
				role = YESNO.YES.getCode();
				break;
			}
		}
    	model.addAttribute("role", role);
        model.addAttribute("files", files);
        model.addAttribute("signed", "1");
        return "borrow/contract/contractFilePreview"; 
	}
	@RequestMapping(value = "contractFilePreviewShow")
	public String contractFilePreviewShow(HttpServletRequest request, HttpServletResponse response,String docId,Model model){
		return "borrow/contract/contractFilePreviewShow";
	}
	@RequestMapping("initSummary")
	public String initSummary(Model model){
		    List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
	        Map<String,String> queryParam = null;
	        queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.SIGN_CONFIRM.getCode());
	        queryParam.put("name",ContractLog.SIGN_CONFIRM.getName());
	        statusList.add(queryParam);
	        queryParam = new HashMap<String,String>();
            queryParam.put("code",ContractLog.CONTRACT_MAKE.getCode());
            queryParam.put("name",ContractLog.CONTRACT_MAKE.getName());
            statusList.add(queryParam);
	        queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.CONTRACT_MAKING.getCode());
	        queryParam.put("name",ContractLog.CONTRACT_MAKING.getName());
	        statusList.add(queryParam);
	        queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.CONTRACT_UPLOAD.getCode());
	        queryParam.put("name",ContractLog.CONTRACT_UPLOAD.getName());
	        statusList.add(queryParam);
	        queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.CONTRACT_AUDIFY.getCode());
	        queryParam.put("name",ContractLog.CONTRACT_AUDIFY.getName());
	        statusList.add(queryParam);  
	        model.addAttribute("statusList", statusList);
	        return "borrow/contract/contractSumary";
		}
		
		@RequestMapping("getSummary")
		public String getSummary(Model model,String status){
		    
		    List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
		    Map<String,String> queryParam = null;
		    queryParam = new HashMap<String,String>();
		    queryParam.put("code",ContractLog.SIGN_CONFIRM.getCode());
		    queryParam.put("name",ContractLog.SIGN_CONFIRM.getName());
		    statusList.add(queryParam);
		    queryParam = new HashMap<String,String>();
            queryParam.put("code",ContractLog.CONTRACT_MAKE.getCode());
            queryParam.put("name",ContractLog.CONTRACT_MAKE.getName());
            statusList.add(queryParam);
		    queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.CONTRACT_MAKING.getCode());
	        queryParam.put("name",ContractLog.CONTRACT_MAKING.getName());
	        statusList.add(queryParam);
	        queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.CONTRACT_UPLOAD.getCode());
	        queryParam.put("name",ContractLog.CONTRACT_UPLOAD.getName());
	        statusList.add(queryParam);
	        queryParam = new HashMap<String,String>();
	        queryParam.put("code",ContractLog.CONTRACT_AUDIFY.getCode());
	        queryParam.put("name",ContractLog.CONTRACT_AUDIFY.getName());
	        statusList.add(queryParam);
		    Map<String,String> checkStatus = new HashMap<String,String>();
		    checkStatus.put("status", status);
		    List<ContractAmountSummaryEx> summaryList = contractService.getSummary(checkStatus);
		    model.addAttribute("summaryList", summaryList);
		    model.addAttribute("statusList", statusList);
		    model.addAttribute("status", status);
		    return "borrow/contract/contractSumary";
		}
	/**
	 *合同下载 
	 *@author zhanghao
	 *@create In 2016年2月16日
	 *@param request
	 *@param response
	 *@param applyId
	 *@return none 
	 * 
	 */
	@RequestMapping("downLoadContract")
    public String downLoadContract(HttpServletRequest request, HttpServletResponse response,String docId,String fileName,String applyId,String wobNum,String token){
	  //  String url = "/bpm/flowController/openForm?applyId="+applyId+"&wobNum="+wobNum+"&dealType=0&token="+token;
	    try {
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "ISO-8859-1"));
            DmService dmService = DmService.getInstance();
            InputStream  docInput = dmService.downloadDocument(docId);
            OutputStream out = response.getOutputStream();
	        com.creditharmony.dm.file.util.FileUtil.writeFile(out, docInput);
	        out.flush();
	        out.close();
	        return null;
	       } catch (UnsupportedEncodingException e1) {
	            
	            e1.printStackTrace();
	             //"redirect:" + adminPath + url;
	            return "文件编码异常";
	        } catch (IOException e) {
	       
	            e.printStackTrace();
	            return "文件读取异常";//"redirect:" + adminPath + url;
	        }
    }
	
	/**
	 *单个合同下载 
	 *@author shenawei
	 *@create In 2016年9月14日
	 *@param request
	 *@param response
	 *@return none 
	 * 
	 */
	@RequestMapping("downLoadContractsingle")
    public String downLoadContractsingle(HttpServletRequest request, HttpServletResponse response,String docId,String fileName){
	    try {
            response.setContentType("application/pdf");
            SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmss");
            fileName=fileName+"_"+f.format(new Date())+".pdf";
            response.addHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "ISO-8859-1"));
            DmService dmService = DmService.getInstance();
            InputStream  docInput = dmService.downloadDocument(docId);
            OutputStream out = response.getOutputStream();
	        com.creditharmony.dm.file.util.FileUtil.writeFile(out, docInput);
	        out.flush();
	        out.close();
	        return null;
	       } catch (UnsupportedEncodingException e1) {
	            
	            e1.printStackTrace();
	             //"redirect:" + adminPath + url;
	            return "文件编码异常";
	        } catch (IOException e) {
	       
	            e.printStackTrace();
	            return "文件读取异常";//"redirect:" + adminPath + url;
	        }
    }
	
	/**
	 *合同下载 通过loanCode
	 *@author shangjunwei
	 *@create In 2016年3月9日
	 *@param request
	 *@param response
	 *@param loanCode
	 *@return none 
	 * 
	 */
  
	@RequestMapping(value="downLoadContractByLoanCode")
    public String downLoadContractByLoanCode(HttpServletRequest request, HttpServletResponse response,String loanCode){
	    String url = "/apply/contractAudit/contractAndPersonDetails?loanCode="+loanCode;
	    try {
	            Contract contract = contractService.findByLoanCode(loanCode);
	            String docIds = contract.getDocId();
	            String[] docId = null;
	            if(!StringUtils.isEmpty(docIds)){
	                if(docIds.indexOf(",")!=-1){
	                    docId = docIds.split(",");
	                } else{
	                    docId = new String[1];
	                    docId[0] = docIds;
	                }
	                response.setContentType("application/zip");
	                response.addHeader("Content-disposition", "attachment;filename=" + new String((loanCode+".zip").getBytes("gbk"), "ISO-8859-1"));
	                DmService dmService = DmService.getInstance();
	                if(docId != null && docId.length >= 1){
	                    List<String> documentIds = new ArrayList<String>();
	                    CollectionUtils.addAll(documentIds, docId);
	                    Map<String, InputStream> map = dmService.downloadDocuments(documentIds);
	                    Zip.zipFiles(response.getOutputStream(), map);
	                }
	                return null;
	              }
	           
	        } catch (UnsupportedEncodingException e1) {	            
	            e1.printStackTrace();
	            return "redirect:" + adminPath + url;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "redirect:" + adminPath + url;
	        }
	    return "redirect:" + adminPath + url;
    }
	
	
	/**
	 *合同预览 
	 *@author shangjunwei
	 *@create In 2016年3月10日
	 *@param request
	 *@param response
	 *@param loanCode
	 *@return none 
	 * 
	 */
  
	@RequestMapping(value="yuLanContractByLoanCode")
    public String yuLanContractByLoanCode(HttpServletRequest request, HttpServletResponse response,Model m){
	    String loanCode=request.getParameter("loanCode");
		m.addAttribute("loanCode",loanCode);
	    return "borrow/contract/yulan";
    }
	
	
	/**
	 *合同预览 时获得转化成swf文件的文件名
	 *@author shangjunwei
	 *@create In 2016年3月10日
	 *@param request
	 *@param response
	 *@param loanCode
	 *@return  JSONObject
	 * 
	 */

	@RequestMapping(value="getSwf")
	@ResponseBody
	public JSONObject getSwf(HttpServletRequest request, HttpServletResponse response,String loanCode){
		
		String webPath = request.getSession().getServletContext().getRealPath("/")+"static/flash/flexpaper/s.doc";
        String swf=webPath.substring(0, webPath.lastIndexOf("."))+".swf";
        String pf=webPath.substring(0, webPath.lastIndexOf("."))+".pdf";
		File file=new File(webPath);
        File pdfFile=new File(pf);
        File swfFile=new File(swf);
		try {
	            Contract contract = contractService.findByLoanCode(loanCode);
	            String docIds = contract.getDocId();
	            String[] docId = null;
	            if(!StringUtils.isEmpty(docIds)){
	                if(docIds.indexOf(",")!=-1){
	                    docId = docIds.split(",");
	                } else{
	                    docId = new String[1];
	                    docId[0] = docIds;
	                }
	                response.setContentType("application/zip");
	                response.addHeader("Content-disposition", "attachment;filename=" + new String((loanCode+".zip").getBytes("gbk"), "ISO-8859-1"));
	                DmService dmService = DmService.getInstance();
	                
	                if(docId != null && docId.length >= 1){
	                    List<String> documentIds = new ArrayList<String>();
	                    CollectionUtils.addAll(documentIds, docId);
	                    Map<String,InputStream> map = dmService.downloadDocuments(documentIds);
	                    FileOutputStream ops= new FileOutputStream(file);

	                    //word20160130034832_2
	                    //pdf20160130034829_1
	                    InputStream in;
	                    for(String key:map.keySet()){
	                    	if(key.startsWith("word")){
	                    		in=map.get(key);
	  	                      byte[] b = new byte[1024];
	  	                      while((in.read(b)) != -1){
	  	                    	  try {
									ops.write(b);
								} catch (Exception e) {
									e.printStackTrace();
								}
	  	                      }
                
	                      PdfAndWord2SWFUtil.src2pdf(file, pdfFile);
	                    try {
							PdfAndWord2SWFUtil.pdf2swf(swfFile,pdfFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
	                    in.close();
	                   ops.flush();
	                   if(ops!=null)
	                   {ops.close();}
	                       }
	                }}}
	            }catch (UnsupportedEncodingException e1) {	            
	            e1.printStackTrace();	            
	        } catch (IOException e) {
	            e.printStackTrace();	            
	        }
	    JSONObject js=new JSONObject();
	    String fn=swfFile.getName();
	    js.put("file",fn);
	   return js;
	}
	
	
	
	/**
	 *协议查看 时获得转化成swf文件的文件名
	 *@author shangjunwei
	 *@create In 2016年3月10日
	 *@param request
	 *@param response
	 *@param docId
	 *@return  JSONObject
	 * 
	 */
	@RequestMapping(value="getFile")
	@ResponseBody
	public JSONObject getContext(HttpServletRequest request, HttpServletResponse response,String loanCode,String name){
		String webPath = request.getSession().getServletContext().getRealPath("/")+"static/flash/flexpaper/"+name+".pdf";
        File pdfFile=new File(webPath);
		try {
            Contract contract = contractService.findByLoanCode(loanCode);
            String docIds = contract.getDocId();
            String[] docId = null;
            if(!StringUtils.isEmpty(docIds)){
                if(docIds.indexOf(",")!=-1){
                    docId = docIds.split(",");
                } else{
                    docId = new String[1];
                    docId[0] = docIds;
                }
                response.setContentType("application/zip");
                response.addHeader("Content-disposition", "attachment;filename=" + new String((name+".zip").getBytes("gbk"), "ISO-8859-1"));
                DmService dmService = DmService.getInstance();
                
                if(docId != null && docId.length >= 1){
                    List<String> documentIds = new ArrayList<String>();
                    CollectionUtils.addAll(documentIds, docId);
                    Map<String,InputStream> map = dmService.downloadDocuments(documentIds);
                    FileOutputStream ops= new FileOutputStream(pdfFile);

                    InputStream in;
                    for(String key:map.keySet()){
                    	String k=key.substring(0,key.indexOf("_"));                  
                    	if(k == name || k.equals(name)){
                    		in=map.get(key);
  	                      byte[] b = new byte[1024];
  	                      while((in.read(b)) != -1){
  	                    	  try {
								ops.write(b);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.toString());
							} 
  	                      }
                       in.close();
	                   ops.flush();
	                   if(ops!=null)
	                   {ops.close();}
                  }
                }
             }
           }
        }catch (UnsupportedEncodingException e1) {	            
        e1.printStackTrace();
        logger.error(e1.toString());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
	    JSONObject js=new JSONObject();
	    String fn=pdfFile.getName();
	    js.put("file",fn);
	   return js;
	}
	
	/**
     *合同预览页面的合同下载后在页面上写出
     *@author shangjunwei
     *@Create In 2016年2月1日
     *@param  docId
     *@return void 
     */
	@RequestMapping(value = "writeTo")
	public void contractPreviewOne(HttpServletRequest request, HttpServletResponse response,String docId,Model model){
	     OutputStream os = null;
	     try {
	         os = response.getOutputStream();
	         DmService dmService = DmService.getInstance();
	         InputStream in = dmService.downloadDocument(docId);
	         com.creditharmony.dm.file.util.FileUtil.writeFile(os, in);
	         os.flush();
	         os.close();
	         os=null;
	         response.flushBuffer();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	
	//获取pdf
	@RequestMapping(value = "getPdf")
	@ResponseBody
	public String contractPdf(HttpServletRequest request, HttpServletResponse response,String docId,Model model,String fileName){
		InputStream in=null;
		String pPath=request.getSession().getServletContext().getRealPath("/")+"static/pdf/web/"+fileName;
		FileOutputStream ops=null;
		File pdf=new File(pPath);
		try {
			 ops= new FileOutputStream(pdf);
	         DmService dmService = DmService.getInstance();
	          in = dmService.downloadDocument(docId);
	          byte[] b = new byte[1024];
                while((in.read(b)) != -1){
              	  try {
					ops.write(b);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.toString());
				} 
                }
                in.close();
                ops.flush();
                if(ops!=null)
                {ops.close();}
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
		
	     return pdf.getName();
	}
	
	
	@RequestMapping(value="getPreView")
	public String getPreSwfView(String docId,String fileName,String downloadFlag,Model model){
	    if(fileName.indexOf("_")!=-1){
	        fileName = fileName.substring(fileName.indexOf("_"));
	    }
	    boolean downloadEnabled = false;
	    model.addAttribute("docId", docId);
	    model.addAttribute("fileName", fileName);
	    if(YESNO.YES.getCode().equals(downloadFlag)){
	        downloadEnabled = true;
	    }
	    model.addAttribute("downloadEnabled",downloadEnabled);
	    return "borrow/contract/swfPreView";
	}
	/**
	 *通过docId获取pdf文档，转换为swf文件 
	 *@param  request
	 *@param  response
	 *@param  docId
	 *@param model
	 *@return none
	 */
	@ResponseBody
	@RequestMapping(value="getSwfByDocId")
	public String getSwfByDocId(HttpServletRequest request, HttpServletResponse response,String docId,String fileName,Model model){
        OutputStream os = null;
        InputStream swfInput = null ;
        OutputStream swfOut = null;
        String webPath = request.getSession().getServletContext().getRealPath("/")+"static/flash/flexpaper/";
        String swf=webPath+fileName+".swf";
        String pf=webPath+fileName+".pdf";
        File pdfFile=new File(pf);
        File swfFile=new File(swf);
        //
        try {
            os = new FileOutputStream(pdfFile);
            DmService dmService = DmService.getInstance();
            InputStream in = dmService.downloadDocument(docId);
            com.creditharmony.dm.file.util.FileUtil.writeFile(os, in);
          /*  byte[] b = new byte[1024];
            while((in.read(b)) != -1){
                try {
                  os.write(b);
              } catch (Exception e) {
                  e.printStackTrace();
              }
            }*/
            os.flush();
            os.close();
            os=null;
            PdfAndWord2SWFUtil.pdf2swf(swfFile,pdfFile);
            swfInput = new FileInputStream(swfFile);
            swfOut = response.getOutputStream();
            com.creditharmony.dm.file.util.FileUtil.writeFile(swfOut, swfInput);
            swfOut.flush();
            swfOut.close();
            swfOut = null;
            response.flushBuffer();
           } catch (Exception e) {
               e.printStackTrace();
           }
       return pdfFile.getName();
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
			for (BaseTaskItemView currItem : sourceWorkItems)
				targetList.add((LoanFlowWorkItemView) currItem);
		}
		return targetList;
	}
	
	
	/**
     *查询已制作合同用户信息列表 
     *@author shangjunwei
     *@Create In 2016年3月3日
     *@param  ctrPersonInfo
     *@return  jsp页面
     */
	@RequestMapping(value="findContract")
	public String findContractAndPerson(Model model,HttpServletRequest request, 
			HttpServletResponse response,ContractAndPersonInfo ctrPersonInfo){
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		if(StringUtils.isNotEmpty(ctrPersonInfo.getLoanCustomerName())){
		    ctrPersonInfo.setLoanCustomerName(StringEscapeUtils.unescapeHtml4(ctrPersonInfo.getLoanCustomerName())); 
		}
		if(ObjectHelper.isEmpty(ctrPersonInfo.getStoreOrgId()) || ctrPersonInfo.getStoreOrgId().length==0){
		    ctrPersonInfo.setStoreName(null);
		    ctrPersonInfo.setStoreOrgId(null);
		}
		if(ObjectHelper.isEmpty(ctrPersonInfo.getRevisitStatuss()) || ctrPersonInfo.getRevisitStatuss().length==0){
		    ctrPersonInfo.setRevisitStatuss(null);
		}else{
			String revisitStatuss[] = ctrPersonInfo.getRevisitStatuss();
			ctrPersonInfo.setRevisitStatussParm(makeRevisitStatusParam(revisitStatuss));
		}
		
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		Page<ContractAndPersonInfo> ps=contractPersonService.findContractAndPerson(ctrPersonInfo,new Page<ContractAndPersonInfo>(request,response));
		 for(ContractAndPersonInfo cp:ps.getList()){
//			 if(cp.getContractVersion()!=null && !cp.getContractVersion().equals("0ZCJ")){
//				 cp.setContractVersionLabel(ContractVer.parseByCode(cp.getContractVersion()).getName());
//			 }			 
			 String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",cp.getModel());
			 cp.setModelLabel(modelLabel);
		 }
		LoanApplyStatus[] statusList = LoanApplyStatus.values();
		List<Map<String,String>> queryList = new ArrayList<Map<String,String>>();
		Map<String,String> curMap=null;
		for(LoanApplyStatus status:statusList){
		    if(YESNO.YES.getCode().equals(status.getSysFlag())){
		        curMap = new HashMap<String,String>();
		        curMap.put("code", status.getCode());
		        curMap.put("name", status.getName());
		        queryList.add(curMap);
		    }
		}
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		
		model.addAttribute("queryList", queryList);
		model.addAttribute("page",ps);
		return "borrow/contract/personList";
	}
	
	/**
     *查询我的已办列表 
     *@author shenawei
     *@Create In 2016年11月2日
     *@param  contractndalready
     *@return  jsp页面
     */
	@RequestMapping(value="findContractAndalready")
	public String findContractAndalready(Model model,HttpServletRequest request, 
			HttpServletResponse response,ContractAndAlready contractndalready){
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		 User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		 contractndalready.setUser(user.getLoginName());
		if(StringUtils.isNotEmpty(contractndalready.getLoanCustomerName())){
			contractndalready.setLoanCustomerName(StringEscapeUtils.unescapeHtml4(contractndalready.getLoanCustomerName())); 
		}
		if(ObjectHelper.isEmpty(contractndalready.getStoreOrgId()) || contractndalready.getStoreOrgId().length==0){
			contractndalready.setStoreName(null);
			contractndalready.setStoreOrgId(null);
		}
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		Page<ContractAndAlready> ps=contractAndAlreadyService.findContractAndAlready(contractndalready,new Page<ContractAndAlready>(request,response));
		 for(ContractAndAlready cp:ps.getList()){
			 if(cp.getContractVersion()!=null && !cp.getContractVersion().equals("0ZCJ")){
				 cp.setContractVersionLabel(ContractVer.parseByCode(cp.getContractVersion()).getName());
			 }			 
			 String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",cp.getModel());
			 cp.setModelLabel(modelLabel);
		 }
		LoanApplyStatus[] statusList = LoanApplyStatus.values();
		List<Map<String,String>> queryList = new ArrayList<Map<String,String>>();
		Map<String,String> curMap=null;
		for(LoanApplyStatus status:statusList){
		    if(YESNO.YES.getCode().equals(status.getSysFlag())){
		        curMap = new HashMap<String,String>();
		        curMap.put("code", status.getCode());
		        curMap.put("name", status.getName());
		        queryList.add(curMap);
		    }
		}
		model.addAttribute("queryList", queryList);
		model.addAttribute("page",ps);
		return "borrow/contract/alreadyList";
	}
	
	
	@RequestMapping(value="contractAndPersonDetails")
	public String loanMinute(String loanCode,String status,Model m,String docId,String contractCode) {	
		 User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
	        List<Role> roleList = user.getRoleList();
	        for(Role r:roleList){
	            if( r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
		                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
		                r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){
	                m.addAttribute("isRateLeader", "1");
	                break;   
	            }
	        }
		LoanMinuteEx loanMinute = lms.loanMinute(loanCode);
		loanMinute.setLoanCode(loanCode);
		// 缓存取码值
		loanMinute.setCustomerCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", loanMinute.getCustomerCertType()));
		loanMinute.setCoroCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", loanMinute.getCoroCertType()));
		loanMinute.setDictRepayMethodLabel(DictCache.getInstance().getDictLabel("jk_repay_interest_way", loanMinute.getDictRepayMethod()));	
		loanMinute.setModel(DictCache.getInstance().getDictLabel("jk_loan_model",loanMinute.getModel()));
		
        ContractFee contractFee = contractFeeService.findByContractCode(contractCode);
        if(null!=contractFee){
        	loanMinute.setComprehensiveServiceRate(contractFee.getComprehensiveServiceRate());
        	loanMinute.setMonthRateService(contractFee.getMonthRateService());
        }
        //获取共借人信息
        List<Map<String,Object>> loancoborrower = lms.getCoborrower(loanCode,loanMinute.getLoanInfoOldOrNewFlag());
        
        //查询外访距离，外访标志
        String outSideLoanCode = "";
        if(StringHelper.isNotEmpty(loanMinute.getOldLoanCode())){
        	outSideLoanCode = loanMinute.getOldLoanCode();
        }else{
        	outSideLoanCode = loanCode;
        }
        Map<String,String> outSide=custInfoService.findOutSide(outSideLoanCode);
        if(outSide!=null){
        	loanMinute.setOutside_flag(outSide.get("outside_flag"));
        	if(!"1".equals(outSide.get("outside_flag")) || null == outSide.get("item_distance") || "".equals(outSide.get("item_distance"))){
        		loanMinute.setItem_distance("0公里");
        	}else{
        		loanMinute.setItem_distance(outSide.get("item_distance")+"公里");
        	}
        }else{
        	loanMinute.setItem_distance("0公里");
        }
        // 存入Model
 		m.addAttribute("status", status);
 		m.addAttribute("lm", loanMinute);
 		m.addAttribute("lb", loancoborrower);
		// TODO 借款状态获取
		LoanInfo lf=this.ls.findStatusByLoanCode(loanCode);	
		String url = imageService.getImageUrl(FlowStep.CONTRACT_AUDIT_CONTRACT.getName(), loanMinute.getOldLoanCode());
		//m.addAttribute("query", query);
		m.addAttribute("url",url);
		m.addAttribute("dictLoanStatus", lf.getDictLoanStatus());
		m.addAttribute("ld",loanCode);
		m.addAttribute("docId",docId);
		m.addAttribute("contractCode",contractCode);
		
		String zcjversion = Global.getConfig("zcjversion");
		String jxcontractVersion = Global.getConfig("jxcontractVersion");
		m.addAttribute("zcjversion", zcjversion);
		m.addAttribute("jxcontractVersion",jxcontractVersion);
		//m.addAttribute("trustCashEnable", this.isEnableTrustCash(lf.getDictLoanStatus()));
		//m.addAttribute("trustRechargeEnable", this.isEnableTrustRecharge(lf.getDictLoanStatus()));
		return "borrow/contract/contractDetails";
	}
	
	/**
     *查询协议
     *@author shangjunwei
     *@Create In 2016年3月3日
     *@param  
     *@return  jsp页面
     */
	@RequestMapping(value="xieYiList")
	public String findXieYi(String docId,String loanCode,String contractCode,String type,Model model){
		List<ContractFile> files=this.contractFileService.findContractFileByContractCode(contractCode);
		String role = YESNO.NO.getCode();
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<Role> roleList = user.getRoleList();
		if("1".equals(type)){
			for(ContractFile contractFile : files){
				if(contractFile.getFileName().contains("还款管理服务说明")){
					files.clear();
					files.add(contractFile);
					break;
				}
			}
			role = YESNO.YES.getCode();
		}else{
			/**合同制作组长**/
			for (Role r : roleList) {
				if (r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
		                r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id)) {
					role = YESNO.YES.getCode();
					break;
				}
			}
		}
    	model.addAttribute("role", role);
		model.addAttribute("files",files);
		model.addAttribute("docId",docId);
		model.addAttribute("contractCode",contractCode);
		model.addAttribute("loanCode",loanCode);
		return "borrow/contract/xieYiList1";
	}
	
	
	/**
	 * 获取金信上限列表信息界面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCeiling")
	public String getCeiling(HttpServletRequest request,
			HttpServletResponse response,
			Model model) {
		Map<String,Object> maps = service.getJXCeillingData();
		model.addAttribute("ceiling", maps);
		return "channel/goldcredit/settingCeiling";
	}
	/**
	 * 清除金信上限信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "clearCeilling")
	public String jinxin(HttpServletRequest request,
			HttpServletResponse response) {
		service.clearCeilling();
		return BooleanType.TRUE;
	}	
	/**
	 * 设置合同审核中的上限数据
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "setJXCeiling")
	public String setJXCeiling(HttpServletRequest request,
			HttpServletResponse response,BigDecimal ceilingMoney) {
		service.setJXCeiling(ceilingMoney);
		return BooleanType.TRUE;
	}	
	
	/**
	 * 判断信雅达插件中信用合同中的文件是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "uploadSunyardValid")
	public String uploadSunyardValid(HttpServletRequest request,
			HttpServletResponse response, String loanCode) {
		String customeIntoTime = ls.findCustomerIntoTime(loanCode);	
		Map<String, String> diskMap = diskInfoDao.getIndexComponentByQueryTime(
				customeIntoTime, ApproveCheckType.XS_APPROVE_CHECK_TYPE.getCode());
		ClientPoxy service = new ClientPoxy(
				ServiceType.Type.IMG_GET_EXIST_IMG_BARCODE);

		Img_GetExistImgBarCodeListInBean inParam = new Img_GetExistImgBarCodeListInBean();
		inParam.setIndex(diskMap.get("image_index_hj"));
		inParam.setParts(diskMap.get("image_component_hj"));
		inParam.setBatchNo(loanCode);
		try {
			if (StringUtils.isNotEmpty(customeIntoTime)) {
				inParam.setSerachDate(DateUtils.parseDate(
						customeIntoTime.replace("-", "").substring(0, 8), "yyyyMMdd"));
			} else {
				// 进件时间为空，截取借款编号中的年月日
				inParam.setSerachDate(DateUtils.parseDate(
						loanCode.substring(2, 10), "yyyyMMdd"));
			}

			Img_GetExistImgBarCodeListOutBean outParam = (Img_GetExistImgBarCodeListOutBean) service.callService(inParam);
			if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) {
				String barCodesString = ArrayHelper.isNotEmpty(outParam.getBarCodeList()) 
						? outParam.getBarCodeList().toString() : "";
				// 委托划扣文件夹与其他文件夹有一个不为空
				if (StringUtils.isNotEmpty(barCodesString)
						&& (barCodesString.indexOf(FileCategoryUtil.TRUSTS_LOAN.getCode()) > -1 
								|| barCodesString.indexOf(FileCategoryUtil.OTHER_CONTRACT.getCode()) > -1)) {
					return BooleanType.TRUE;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);			
		}
		return BooleanType.FALSE;
	}
		
	/**判断借款状态*/
	@RequestMapping(value = "getStatus")
	public void getStatus(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("applyId");
		List<Map<String,String>> list = ls.getStatus(applyId);
		PrintWriter out = response.getWriter();
		if(list.isEmpty()){
			out.print("{\"msg\":\"false\"}");
		}else{
			if (LoanApplyStatus.CONTRACT_AUDIFY.getCode().equals(
					list.get(0).get("loanstatus"))
					|| LoanApplyStatus.GOLDCREDIT_RETURN.getCode().equals(
							list.get(0).get("loanstatus"))
					|| LoanApplyStatus.PAYMENT_BACK.getCode().equals(
							list.get(0).get("loanstatus"))
					|| LoanApplyStatus.LOAN_SEND_RETURN.getCode().equals(
							list.get(0).get("loanstatus"))) {
				Object json = JSON.toJSON("{\"msg\":\"true\"}");
				out.print(json);
			}else{
				Object json = JSON.toJSON("{\"msg\":\"false\"}");
				out.print(json);
			}
		}
	}
	
	/**
	 * 校验额度上限
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkLimit")
	public String checkLimit(HttpServletRequest request,
			HttpServletResponse response, String jxId,Double feePaymentAmount) {
		String upLimit="0";
		if(jxId!=null && !"".equals(jxId)){
			ContractBusiView contractBusiView=new ContractBusiView();
			contractBusiView.setJxId(jxId);
			contractBusiView=contractFeeService.checkLimit(contractBusiView);
			if(contractBusiView!=null){
				if(contractBusiView.getKinnobuQuota()<contractBusiView.getKinnobuUsed()+feePaymentAmount){
					upLimit="1";
				}
			}
		}
		
		
		return upLimit;
	}
	/**
	 * 校验是否手动验证
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verification")
	public String verification(HttpServletRequest request,
			HttpServletResponse response, String loanCode) {
		String verfication="";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("loanCode", loanCode);
		List<ContractOperateInfo> operateInfoList=contractOperateInfoDao.findInfoByLoanCode(param);
		for(ContractOperateInfo operateInfo:operateInfoList){
			if(operateInfo!=null&&operateInfo.getReturnReason()!=null&&"手动验证通过".equals(operateInfo.getReturnReason())){
				verfication=operateInfo.getVerification();
			}
		}
		return verfication;
	}
	
	public String makeRevisitStatusParam(String revisitStatuss[]){
		String strOr = " or t.revisit_status = ";
		String revisitStatusParam = " AND (t.revisit_status = ";
		String revisitStatusNull ="null";
		for(String str:revisitStatuss){
			if(!"".equals(str)){
				revisitStatusParam = revisitStatusParam + "'"+ str +"'" + strOr;
			}else{
				revisitStatusNull = str;
			}
		}
		if(revisitStatusParam.lastIndexOf(strOr) != -1 && "".equals(revisitStatusNull)){
			revisitStatusParam = revisitStatusParam.substring(0, revisitStatusParam.lastIndexOf(strOr));
			revisitStatusParam = revisitStatusParam + " or t.revisit_status is null)";
		}else if(revisitStatusParam.lastIndexOf(strOr) != -1 && !"".equals(revisitStatusNull)){
			revisitStatusParam = revisitStatusParam.substring(0, revisitStatusParam.lastIndexOf(strOr)) + ")";
		}else if("".equals(revisitStatusNull)){
			revisitStatusParam = " AND t.revisit_status is null";
		}else{
			revisitStatusParam  = revisitStatusParam + ")";
		}
		return revisitStatusParam;
	}
	
	/**
	 * 延期列表
	·* 2017年1月12日
	·* by Huowenlong
	 * @param model
	 * @param request
	 * @param response
	 * @param delayEntity
	 * @return
	 */
	@RequestMapping(value="delayList")
	public String delayList(Model model,HttpServletRequest request, 
			HttpServletResponse response,DelayEntity delayEntity){
		if(StringHelper.isNotEmpty(delayEntity.getStoreOrgIds())){
			delayEntity.setStoreOrgIdArray(delayEntity.getStoreOrgIds().split(","));
		}
		if(StringHelper.isNotEmpty(delayEntity.getDictLoanStatus())){
			delayEntity.setDictLoanStatusArray(null);
		}
		if(delayEntity.getEndContractTime() != null){
			String postponeTimeTemp = DateUtils.date2Str(delayEntity.getEndContractTime(), "yyyy-MM-dd");
			delayEntity.setEndContractTime(DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(delayEntity.getEndSignconfirmTime() != null){
			String postponeTimeTemp = DateUtils.date2Str(delayEntity.getEndSignconfirmTime(), "yyyy-MM-dd");
			delayEntity.setEndSignconfirmTime(DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(delayEntity.getEndTateTime() != null){
			String postponeTimeTemp = DateUtils.date2Str(delayEntity.getEndTateTime(), "yyyy-MM-dd");
			delayEntity.setEndTateTime((DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		Page<DelayEntity> ps=delayService.findDelayList(delayEntity,new Page<DelayEntity>(request,response));
		for(DelayEntity de : ps.getList()){
			 de.setContractVersion(DictCache.getInstance().getDictLabel("jk_contract_ver",de.getContractVersion()));		 
			 de.setModel(DictCache.getInstance().getDictLabel("jk_loan_model",de.getModel()));
			 de.setDictLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status",de.getDictLoanStatus()));
			 de.setLoanFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",de.getLoanFlag()));
		}
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		List statusList = makeStatusList();
		model.addAttribute("statusList", statusList);
		model.addAttribute("productList", productList);
		model.addAttribute("page",ps);
		return "borrow/contract/delayList";
	}
	
	/**
	 * 延期
	·* 2017年1月12日
	·* by Huowenlong
	 * @param model
	 * @param request
	 * @param response
	 * @param applyId
	 * @param postponeTime
	 * @param contractCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="postpone")
	public PostponeResult postpone(Model model,HttpServletRequest request, 
			HttpServletResponse response,String loanCode ,String applyId,Date postponeTime,String contractCode,String dictLoanStatus){
		PostponeResult pr = new PostponeResult();
		pr.setResult(BooleanType.TRUE);
		pr.setMsg(ContractConstant.POSTPONETIP_SUCCESS);
		try {
			//LoanWorkItemView workItem = (LoanWorkItemView) loanFlowServiceImpl.loadWorkItemView(applyId);
			Map param = new HashMap();
			param.put("loanCode", loanCode);
			LoanInfo loanInfo = applyLoanInfoDao.selectByLoanCode(param);
			String postponeTimeTemp = DateUtils.date2Str(postponeTime, "yyyy-MM-dd");
			postponeTime = DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
			if(loanInfo.getTimeOut() == null){
				loanInfo.setTimeOut(new Date());
			}
			if(DateUtils.getDistanceOfTwoDate(postponeTime, loanInfo.getTimeOut() )>=0 ){
				pr.setResult(BooleanType.FALSE);
				pr.setMsg(ContractConstant.POSTPONETIP_1);
				return pr;
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("timeOutPoint", postponeTime);
			//flowService.saveDataByApplyId(applyId, map);
			LoanInfo loanInfoParam = new LoanInfo();
			loanInfoParam.setLoanCode(loanCode);
			loanInfoParam.setTimeOut(postponeTime);
			applyLoanInfoDao.updateLoanInfo(loanInfoParam);
			PostponeEntity pe = new PostponeEntity();
			pe.preInsert();
			pe.setApplyId(applyId);
			pe.setContractCode(contractCode);
			pe.setStepName(DictCache.getInstance().getDictLabel("jk_loan_apply_status",dictLoanStatus));
			pe.setTimeoutpointTime(loanInfo.getTimeOut());
			pe.setPostponeTime(postponeTime);
			postponeDao.insert(pe);
			LoanStatusHis his = new LoanStatusHis();
			his.preInsert();
			 // APPLY_ID
            his.setApplyId(applyId);
            // 借款Code
            his.setLoanCode(loanCode);
            // 状态
            his.setDictLoanStatus(dictLoanStatus);
            // 操作步骤(回退,放弃,拒绝 等)
            his.setOperateStep("延期");
            // 操作结果
            his.setOperateResult("成功");
            // 备注
            his.setRemark("延期到:" + DateUtils.date2Str(postponeTime, "yyyy-MM-dd HH:mm:ss"));
            // 系统标识
            his.setDictSysFlag(ModuleName.MODULE_LOAN.value);
            
            // 操作时间
            his.setOperateTime(his.getCreateTime());
            User user = UserUtils.getUser();
            // 操作人记录当前登陆系统用户名称
            his.setOperator(user.getName());
            if (!ObjectHelper.isEmpty(user.getRole())) {
                // 操作人角色
                his.setOperateRoleId(user.getRole().getId());
            }
            if (!ObjectHelper.isEmpty(user.getDepartment())) {
                // 机构编码
                his.setOrgCode(user.getDepartment().getId());
            }
			loanStatusHisDao.insert(his);
		} catch (Exception e) {
			pr.setResult(BooleanType.FALSE);
			pr.setMsg(ContractConstant.POSTPONETIP_FAILE);
			return pr;
		}
		return pr;
	}
	/**
	 * 延期导出
	·* 2017年1月12日
	·* by Huowenlong
	 * @param request
	 * @param response
	 * @param delayEntity
	 * @return
	 */
	@RequestMapping(value = "postponeExl")
	public String postponeExl(HttpServletRequest request,HttpServletResponse response,
			DelayEntity delayEntity) {
		ExcelUtils excelutil = new ExcelUtils();
		if(StringHelper.isNotEmpty(delayEntity.getIds())){
			delayEntity.setContractCodeArray(delayEntity.getIds().split(","));
		}
		if(StringHelper.isNotEmpty(delayEntity.getStoreOrgIds())){
			delayEntity.setStoreOrgIdArray(delayEntity.getStoreOrgIds().split(","));
		}
		if(StringHelper.isNotEmpty(delayEntity.getDictLoanStatus())){
			delayEntity.setDictLoanStatusArray(null);
		}
		if(delayEntity.getEndContractTime() != null){
			String postponeTimeTemp = DateUtils.date2Str(delayEntity.getEndContractTime(), "yyyy-MM-dd");
			delayEntity.setEndContractTime(DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(delayEntity.getEndSignconfirmTime() != null){
			String postponeTimeTemp = DateUtils.date2Str(delayEntity.getEndSignconfirmTime(), "yyyy-MM-dd");
			delayEntity.setEndSignconfirmTime(DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if(delayEntity.getEndTateTime() != null){
			String postponeTimeTemp = DateUtils.date2Str(delayEntity.getEndTateTime(), "yyyy-MM-dd");
			delayEntity.setEndTateTime((DateUtil.StringToDate(postponeTimeTemp + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
		}
		List<DelayEntity> list =  delayService.postPoneListExport(delayEntity);
		for(DelayEntity de : list){
			 if(de.getTimeOutPointTime() != null && !"".equals(de.getTimeOutPointTime())&& de.getPostponeTime() != null && !"".equals(de.getPostponeTime())){
				de.setPostponeDays(String.valueOf(DateUtils.getDistanceOfTwoDate(de.getTimeOutPointTime(),de.getPostponeTime())));
			 }
			 de.setContractVersion(DictCache.getInstance().getDictLabel("jk_contract_ver",de.getContractVersion()));
			 de.setModel(DictCache.getInstance().getDictLabel("jk_loan_model",de.getModel()));
			 de.setDictLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status",de.getDictLoanStatus()));
			 de.setLoanFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",de.getLoanFlag()));
		}
		excelutil.exportExcel(list,"延期列表",null,DelayEntity.class,FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,response, null);
		return null;
	}
	
	public List makeStatusList(){
		List<Map<String,String>> queryList = new ArrayList<Map<String,String>>();
		Map<String,String> curMap = new HashMap<String,String>();
        curMap.put("code", LoanApplyStatus.SIGN_CONFIRM.getCode());
        curMap.put("name", LoanApplyStatus.SIGN_CONFIRM.getName());
        queryList.add(curMap);
        Map<String,String> curMap1 = new HashMap<String,String>();
        curMap1.put("code", LoanApplyStatus.CONTRACT_MAKE.getCode());
        curMap1.put("name", LoanApplyStatus.CONTRACT_MAKE.getName());
        queryList.add(curMap1);
        Map<String,String> curMap3 = new HashMap<String,String>();
        curMap3.put("code", LoanApplyStatus.CONTRACT_UPLOAD.getCode());
        curMap3.put("name", LoanApplyStatus.CONTRACT_UPLOAD.getName());
        queryList.add(curMap3);
        Map<String,String> curMap4 = new HashMap<String,String>();
        curMap4.put("code", LoanApplyStatus.CONTRACT_AUDIFY.getCode());
        curMap4.put("name", LoanApplyStatus.CONTRACT_AUDIFY.getName());
        queryList.add(curMap4);
        Map<String,String> curMap5 = new HashMap<String,String>();
        curMap5.put("code", LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
        curMap5.put("name", LoanApplyStatus.LOAN_SEND_CONFIRM.getName());
        queryList.add(curMap5);
      /*  Map<String,String> curMap6 = new HashMap<String,String>();
        curMap6.put("code", LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
        curMap6.put("name", LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getName());
        queryList.add(curMap6);
        Map<String,String> curMap2 = new HashMap<String,String>();
        curMap2.put("code", LoanApplyStatus.CONTRACT_MAKING.getCode());
        curMap2.put("name", LoanApplyStatus.CONTRACT_MAKING.getName());
        queryList.add(curMap2);
        Map<String,String> curMap7 = new HashMap<String,String>();
        curMap7.put("code", LoanApplyStatus.LOAN_TO_SEND.getCode());
        curMap7.put("name", LoanApplyStatus.LOAN_TO_SEND.getName());
        queryList.add(curMap7);
        Map<String,String> curMap8 = new HashMap<String,String>();
        curMap8.put("code", LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
        curMap8.put("name", LoanApplyStatus.LOAN_SEND_AUDITY.getName());
        queryList.add(curMap8);
        Map<String,String> curMap9 = new HashMap<String,String>();
        curMap9.put("code", LoanApplyStatus.KING_RETURN.getCode());
        curMap9.put("name", LoanApplyStatus.KING_RETURN.getName());
        queryList.add(curMap9);
        Map<String,String> curMap10 = new HashMap<String,String>();
        curMap10.put("code", LoanApplyStatus.BIGFINANCE_TO_SNED.getCode());
        curMap10.put("name", LoanApplyStatus.BIGFINANCE_TO_SNED.getName());
        queryList.add(curMap10);
        Map<String,String> curMap11 = new HashMap<String,String>();
        curMap11.put("code", LoanApplyStatus.KING_TO_OPEN.getCode());
        curMap11.put("name", LoanApplyStatus.KING_TO_OPEN.getName());
        queryList.add(curMap11);
        Map<String,String> curMap12 = new HashMap<String,String>();
        curMap12.put("code", LoanApplyStatus.GOLDCREDIT_RIGHT_RETURN.getCode());
        curMap12.put("name", LoanApplyStatus.GOLDCREDIT_RIGHT_RETURN.getName());
        queryList.add(curMap12);
        Map<String,String> curMap13 = new HashMap<String,String>();
        curMap13.put("code", LoanApplyStatus.GOLDCREDIT_FIRST_REJECT.getCode());
        curMap13.put("name", LoanApplyStatus.GOLDCREDIT_FIRST_REJECT.getName());
        queryList.add(curMap13);
        Map<String,String> curMap14 = new HashMap<String,String>();
        curMap14.put("code", LoanApplyStatus.GOLDCREDIT_REVIEW_REJECT.getCode());
        curMap14.put("name", LoanApplyStatus.GOLDCREDIT_REVIEW_REJECT.getName());
        queryList.add(curMap14);*/
        return queryList;
	}
	/**
	 * 信息平台占比分配视图 ·* 2017年02月20日 ·* by shenawei
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "splitView")
	public String splitView(HttpServletRequest request, HttpServletResponse response, Model model, String param) {
		if (!param.equals("") && param != null) {
			model.addAttribute("params", param);
		} else {
			Split split = splitService.findBySplit();
			if (split != null) {
				model.addAttribute("zcj", split.getZcj());
				model.addAttribute("jinxin", split.getJinxin());
				model.addAttribute("params", "");
			}
		}
		return "borrow/contract/splitView";
	}

	/**
	 * 信息平台占比分配 2017年02月21日 by shenawei
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "SaveSplit")
	public void SaveSplit(HttpServletRequest request, HttpServletResponse response, Model model, String zcj,
			String jinxin, String param) {
		try {
			if (!param.equals("") && param != null) {
				contractTempService.ContractSplit(zcj, jinxin, param);		
			} else {
				contractTempService.ContractSplit(zcj, jinxin);
			}
			PrintWriter w=response.getWriter();
			if(w!=null){
				w.write(true+"");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("信息平台占比分配异常",e);
		}
		
	}

	/**
	 * 占比历史 ·* 2017年02月20日 ·* by shenawei
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "splitViewHis")
	public String splitViewHis(HttpServletRequest request, HttpServletResponse response, Model model) {
		Split split = new Split();
		Page<Split> ps = splitService.findBySplitHis(split, new Page<Split>(request, response));
		model.addAttribute("page", ps);
		return "borrow/contract/splitViewHis";
	}
	/**
	 * 合同占比拆分验证
	 * 2017年03月01日 by shenawei
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "yzSplit")
	public void yzSplit(HttpServletRequest request, HttpServletResponse response, Model model, String loanCode) {
		try {
			LoanInfo loaninfo=applyLoanInfoDao.findStatusByLoanCode(loanCode);
			Boolean ble=false;
			if(loaninfo.getIssplit().equals(ContractConstant.ISSPLIT_1)){
				ble=false;
			}else{
				ble=true;
			}
			PrintWriter w=response.getWriter();
			w.write(ble+"");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("合同占比拆分验证异常",e);
		}
		
	}
}
