package com.creditharmony.loan.borrow.contract.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.Constant;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.approve.type.CheckType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.dao.CoeffReferJYJDao;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.CoeffReferJYJ;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ex.BackResponse;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.CustInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.FeeInfoEx;
import com.creditharmony.loan.borrow.contract.event.LoanFlowLoad;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.service.CustInfoService;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.contract.view.CustomerView;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.reconsider.service.ReconsiderApplyService;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.ReckonFee;
import com.creditharmony.loan.common.utils.ReckonFeeNew;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowQueue;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.query.ProcessQueryBuilder;

/**
 * 合同工具Controller层 
 * @Class Name ContractUtilController
 * @author 张灏 
 * @Create In 2015年12月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/contractUtil")
public class ContractUtilController extends BaseController {


	@Resource(name="appFrame_flowServiceImpl")
	private FlowService flowService;
	
	/**
	 *测试用 
	 * 
	 */
	@Autowired
	private LoanFlowLoad flowLoad;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	
	@Autowired
	private ReconsiderApplyService reconsiderApplyService;
	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	@Autowired
	private CoeffReferJYJDao coeffReferJYJDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private CustInfoService custInfoService;
	/**
	 * 计算费率 
	 * 2015年12月1日
	 * By 张灏 
	 * @param model
	 * @param rateAuditView
	 * @return ContractBusiView
	 */
	@RequestMapping(value="asycReckonFee")
	@ResponseBody
	public ContractBusiView asycReckonAmount(Model model,ContractBusiView rateAuditView) throws Exception{
		Contract base = rateAuditView.getContract();
		ContractFee ctrFee = rateAuditView.getCtrFee();
		Contract con=contractDao.findByContractCode(base.getContractCode());
		LoanInfo loaninfo=loanInfoDao.findStatusByLoanCode(base.getLoanCode());
		List<CoeffReferJYJ> coeffReferJYJList=null;
		CustInfo auditInfo = custInfoService.findAuditInfo(base.getApplyId());
		if(loaninfo.getProductType().equals(ProductUtil.PRODUCT_JYJ.getCode())){	
			CoeffReferJYJ c=new CoeffReferJYJ();
			c.setMonths(loaninfo.getLoanMonths());
			c.setProductRate(auditInfo.getAuditRate());  //待获取  -总利率
			coeffReferJYJList= coeffReferJYJDao.selectCoeffRefer(c);
		}
		ReckonFeeNew.ReckonFeeOneStep(base,ctrFee,con,coeffReferJYJList);
		LoanInfo loanInfo = loanInfoService.selectByLoanCode(base.getLoanCode());
  		String urgentFlag = loanInfo.getLoanUrgentFlag();
		boolean isUrge = false;
		if(YESNO.YES.getCode().equals(urgentFlag)){
			isUrge = true;
		}
		BigDecimal feeExpedited = ReckonFeeNew.urgeFee(isUrge, base, ctrFee);
		ctrFee.setFeePaymentAmount(ctrFee.getFeePaymentAmount().subtract(feeExpedited));
		FeeInfoEx feeInfoEx = ReckonFee.FeeFormat(ctrFee, base, "#,##0.00");
		rateAuditView.setContract(base);
		rateAuditView.setCtrFee(ctrFee);
		rateAuditView.setFeeInfo(feeInfoEx);		
		return rateAuditView;
	}
	
	/**
	 * 由于流程当前没有数据 此方法用于办理页面测试用 
	 * 2015年12月1日
	 * By 张灏 
	 * @param model
	 * @param ctrQryParam 输入查询参数 
	 * @param viewName 视图名称
	 * @return contract.jsp + param:viewName
	 */
	@RequestMapping(value="handleAudit")
	public String handleAudit(Model model,LoanFlowQueryParam ctrQryParam,String viewName){
		ContractBusiView contractView = (ContractBusiView) flowLoad.load(ctrQryParam.getApplyId(),ctrQryParam.getStepName());
		if(contractView.getContract()==null){
			contractView.setContract(new Contract());
		}
		if(contractView.getCtrFee()==null){
			contractView.setCtrFee(new ContractFee());
		}
		contractView.setApplyId(ctrQryParam.getApplyId());
		contractView.getContract().setAuditAmount(new BigDecimal(ctrQryParam.getAuditAmount()));
		contractView.getCtrFee().setFeeExpedited(new BigDecimal(ctrQryParam.getFeeExpedited()));
		contractView.getContract().setContractMonths(ctrQryParam.getContractMonths());
		contractView.getCtrFee().setFeePetition(new BigDecimal(ctrQryParam.getFeePetition()));
		WorkItemView workItem = new WorkItemView();
		workItem.setBv(contractView);
		model.addAttribute("workItem",workItem);
		return "borrow/contract/" + viewName;
	}
	
	/**
	 * 打开合同签署页面 
	 * 2015年12月1日
	 * By 张灏
	 * @param model
	 * @param ctrQryParam 输入查询参数 
	 * @param viewName 视图名称
	 * @return contract + param:viewName
	 */
	@RequestMapping(value="openCtrSign")
	public String openCtrSign(Model model,LoanFlowQueryParam ctrQryParam,String viewName){
		CustomerView customerView = (CustomerView) flowLoad.load(ctrQryParam.getApplyId(),ctrQryParam.getStepName());
	   WorkItemView workItem = new WorkItemView();
		workItem.setBv(customerView);
		model.addAttribute("workItem",workItem);
		return "borrow/contract/" + viewName;
	}
	
	/**
	 * 打开添加标识弹出框
	 * 2015年12月1日
	 * By 张灏
	 * @param model
	 * @param attributeName
	 * @param flowFlag
	 * @param redirectUrl
	 * @param batchColl
	 * @return Flag.jsp
	 */
	@RequestMapping(value="openAddFlagDialog")
	public String openAddFlagDialog(Model model,String attributeName,String flowFlag,String redirectUrl,String batchColl,String targetModel,String errorCount,String sucessCount){
	    model.addAttribute("batchColl", batchColl);
	    model.addAttribute("redirectUrl", redirectUrl);
        model.addAttribute("flowFlag", flowFlag);
        model.addAttribute("attributeName", attributeName);
        List<Map<String,String>> channels = new ArrayList<Map<String,String>>();
        Map<String,String> curMap = null;
        if(LoanModel.TG.getCode().equals(targetModel)){
            curMap = new HashMap<String,String>();
            curMap.put("code", ChannelFlag.CHP.getCode());
            curMap.put("name", ChannelFlag.CHP.getName());
            channels.add(curMap);
            curMap = new HashMap<String,String>();
            curMap.put("code", ChannelFlag.P2P.getCode());
            curMap.put("name", ChannelFlag.P2P.getName());
            channels.add(curMap);
            model.addAttribute("channels", channels);
        }else {
            curMap = new HashMap<String,String>();
            curMap.put("code", ChannelFlag.JINXIN.getCode());
            curMap.put("name", ChannelFlag.JINXIN.getName());
            channels.add(curMap);
            curMap = new HashMap<String,String>();
            curMap.put("code", ChannelFlag.CHP.getCode());
            curMap.put("name", ChannelFlag.CHP.getName());
            channels.add(curMap);
            curMap = new HashMap<String,String>();
            curMap.put("code", ChannelFlag.P2P.getCode());
            curMap.put("name", ChannelFlag.P2P.getName());
            channels.add(curMap);
            if(!ContractConstant.CTR_AUDIT_FLAG.equals(flowFlag)){
            	
            	curMap = new HashMap<String,String>();
            	curMap.put("code", ChannelFlag.ZCJ.getCode());
            	curMap.put("name", ChannelFlag.ZCJ.getName());
            	channels.add(curMap);
            }
            model.addAttribute("channels", channels);
            
        }
        model.addAttribute("targetModel", targetModel);
        model.addAttribute("errorCount",errorCount);
        model.addAttribute("sucessCount",sucessCount);
	    return "/borrow/contract/Flag";
	}
	
	/**
	 * 打开添加退回弹出框
	 * 2015年12月1日
	 * By 张灏
	 * @param model
	 * @param viewName
	 * @param stepName
	 * @param flowFlag
	 * @param redirectUrl
	 * @param applyId
	 * @param workItem
	 * @param contractView
	 * @return contract + param:viewName
	 */
	@RequestMapping(value="openGrantDialog")
	public String openGrantDialog(Model model,String viewName,String stepName,String flowFlag,String redirectUrl,String applyId,WorkItemView workItem,ContractBusiView contractView,String menuId){
	    List<BackResponse> backResponses = new ArrayList<BackResponse>();
        BackResponse backResponse = null;
	    if(ContractConstant.RATE_AUDIT.equals(stepName)){
	        Map<String,Object> param = new HashMap<String,Object>();
            param.put("dictSysFlag", ModuleName.MODULE_APPROVE.value);
            param.put("applyId", applyId);
            List<LoanStatusHis> loanStatus = historyService.findLastApproveNote(param);
            LoanStatusHis loanStatusHis = loanStatus.get(0);
            String lastStatus = loanStatusHis.getOperateStep();
           if(CheckType.XS_SECOND_CREDIT_AUDIT.getName().equals(lastStatus)){
                      backResponse = new BackResponse();
                      backResponse.setBackResponse(LoanFlowRoute.LETTERREVIEW);
                      backResponse.setBackMsg(CheckType.XS_SECOND_CREDIT_AUDIT.getName());
            }else if(CheckType.XS_THRED_CREDIT_AUDIT.getName().equals(lastStatus)){
                      backResponse = new BackResponse();
                      backResponse.setBackResponse(LoanFlowRoute.FINALJUDGTEAM);
                      backResponse.setBackMsg(CheckType.XS_THRED_CREDIT_AUDIT.getName());
            }else if(CheckType.XS_FINAL_CREDIT_AUDIT.getName().equals(lastStatus)){
                      backResponse = new BackResponse();
                      backResponse.setBackResponse(LoanFlowRoute.FINALJUDG);
                      backResponse.setBackMsg(CheckType.XS_FINAL_CREDIT_AUDIT.getName());
            }else if(CheckType.FY_SECOND_CREDIT_AUDIT.getName().equals(lastStatus)){
                      backResponse = new BackResponse();
                      backResponse.setBackResponse(LoanFlowRoute.RECONSIDERREVIEW);
                      backResponse.setBackMsg(CheckType.FY_SECOND_CREDIT_AUDIT.getName());
            }else if(CheckType.FY_FINAL_CREDIT_AUDIT.getName().equals(lastStatus)){
                      backResponse = new BackResponse();
                      backResponse.setBackResponse(LoanFlowRoute.RECONSIDERFINALJUDG);
                      backResponse.setBackMsg(CheckType.FY_FINAL_CREDIT_AUDIT.getName());
            }
            backResponses.add(backResponse);
	    }else if(ContractConstant.CONFIRM_SIGN.equals(stepName)){
	        Map<String,Object> param = new HashMap<String,Object>();
	        param.put("loanCode", contractView.getContract().getLoanCode());
	        ReconsiderApply reconsiderApply = reconsiderApplyService.selectByParam(param);
	        if(ObjectHelper.isEmpty(reconsiderApply)){
	            backResponse = new BackResponse();
	            backResponse.setBackResponse(LoanFlowRoute.BACKSTORE);
	            backResponse.setBackMsg(LoanApplyStatus.BACK_STORE.getName());
	            backResponses.add(backResponse);
	        }else{
	            backResponse = new BackResponse();
	            backResponse.setBackResponse(LoanFlowRoute.RECONSIDERBACKSTORE);
	            backResponse.setBackMsg(LoanApplyStatus.RECONSIDER_BACK_STORE.getName());
	            backResponses.add(backResponse);
	        }
        }else if(ContractConstant.CTR_CREATE.equals(stepName)){
            backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONFIRMSIGN);
            backResponse.setBackMsg(LoanApplyStatus.SIGN_CONFIRM.getName());
            backResponses.add(backResponse);
            backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.RATECHECK);
            backResponse.setBackMsg(LoanApplyStatus.RATE_TO_VERIFY.getName());
            backResponses.add(backResponse); 
        }else if(ContractConstant.CUST_SERVICE_SIGN.equals(stepName)){
	        backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONFIRMSIGN);
            backResponse.setBackMsg(LoanApplyStatus.SIGN_CONFIRM.getName());
            backResponses.add(backResponse);
           /* Map<String,Object> param = new HashMap<String,Object>();
            param.put("loanCode", contractView.getContract().getLoanCode());
            ReconsiderApply reconsiderApply = reconsiderApplyService.selectByParam(param);
            if(ObjectHelper.isEmpty(reconsiderApply)){
                backResponse = new BackResponse();
                backResponse.setBackResponse(LoanFlowRoute.BACKSTORE);
                backResponse.setBackMsg(LoanApplyStatus.BACK_STORE.getName());
                backResponses.add(backResponse);
            }else{
                backResponse = new BackResponse();
                backResponse.setBackResponse(LoanFlowRoute.RECONSIDERBACKSTORE);
                backResponse.setBackMsg(LoanApplyStatus.RECONSIDER_BACK_STORE.getName());
                backResponses.add(backResponse);
            }*/
            Contract tempContract = contractService.findByApplyId(applyId);
            contractView.setContract(tempContract);
	    }else if(ContractConstant.CTR_AUDIT.equals(stepName)){
	        backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONFIRMSIGN);
            backResponse.setBackMsg(LoanApplyStatus.SIGN_CONFIRM.getName());
            backResponses.add(backResponse);
            backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONTRACTSIGN);
            backResponse.setBackMsg(LoanApplyStatus.CONTRACT_UPLOAD.getName());
            backResponses.add(backResponse);
            Contract tempContract = contractService.findByApplyId(applyId);
            contractView.setContract(tempContract);
	    }
	    model.addAttribute("menuId",menuId);
	    model.addAttribute("workItem",workItem);
	    model.addAttribute("redirectUrl", redirectUrl);
	    model.addAttribute("flowFlag", flowFlag);
	    model.addAttribute("applyId", applyId);
	    model.addAttribute("contractView", contractView);
	    model.addAttribute("backResponses", backResponses);
		return "borrow/contract/" + viewName;
	}
	
	/**
	 * 打开批量退回弹窗
	 * @author FuLiXin
	 * @throws Exception 
	 * @date 2017年1月3日 下午1:18:04
	 */
	@RequestMapping(value="openBackList")
	public String openBackList(Model model,String viewName,String stepName,String flowFlag,String redirectUrl,String applyId,WorkItemView workItem,
			ContractBusiView contractView,String menuId1,String param,LoanFlowQueryParam ctrQryParam) throws Exception{
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
		List<BackResponse> backResponses = new ArrayList<BackResponse>();
	    BackResponse backResponse = null;
	    if(param!=null && !"".equals(param)){
			if(param.contains(";")){
				String[] parObj=param.split(";");
				for(int i=0;i<parObj.length;i++){
					String[] parVal=parObj[i].split(",");
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("flowName", parVal[2]);
					map.put("stepName", parVal[4]);
					map.put("applyId", parVal[0]);
					map.put("token", parVal[3]);
					map.put("wobNum", parVal[1]);
					Contract tempContract = contractService.findByApplyId(parVal[0]);
					map.put("contract", tempContract);
					list.add(map);
				}
			}else{
				String[] parVal=param.split(",");
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("flowName", parVal[2]);
				map.put("stepName", parVal[4]);
				map.put("applyId", parVal[0]);
				map.put("token", parVal[3]);
				map.put("wobNum", parVal[1]);
				Contract tempContract = contractService.findByApplyId(parVal[0]);
				map.put("contract", tempContract);
				list.add(map);
			}
	    }else{
	    	flowFlag=flowFlag.split(",")[0];
	    	menuId1=menuId1.split(",")[0];
	    	list =loadLoanBackList(ctrQryParam,flowFlag);
	    }
		if(ContractConstant.RATE_AUDIT_FLAG.equals(flowFlag)){
	      
	    }else if(ContractConstant.CTR_AUDIT_FLAG.equals(flowFlag)){
	        backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONFIRMSIGN);
            backResponse.setBackMsg(LoanApplyStatus.SIGN_CONFIRM.getName());
            backResponses.add(backResponse);
            backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONTRACTSIGN);
            backResponse.setBackMsg(LoanApplyStatus.CONTRACT_UPLOAD.getName());
            backResponses.add(backResponse);
          
	    }else if(ContractConstant.CTR_CREATE_FLAG.equals(flowFlag)){
            backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.CONFIRMSIGN);
            backResponse.setBackMsg(LoanApplyStatus.SIGN_CONFIRM.getName());
            backResponses.add(backResponse);
            backResponse = new BackResponse();
            backResponse.setBackResponse(LoanFlowRoute.RATECHECK);
            backResponse.setBackMsg(LoanApplyStatus.RATE_TO_VERIFY.getName());
            backResponses.add(backResponse); 
        }
		if(list.size()==0){
			model.addAttribute("flag",1);
		}else{
			model.addAttribute("flag",0);
		}
		model.addAttribute("list",list);
	    model.addAttribute("menuId",menuId1);
	    model.addAttribute("workItem",workItem);
	    model.addAttribute("redirectUrl", redirectUrl);
	    model.addAttribute("flowFlag", flowFlag);
	    model.addAttribute("backResponses", backResponses);
		return "borrow/contract/" + viewName;
	}

	private List<Map<String, Object>> loadLoanBackList(
			LoanFlowQueryParam ctrQryParam,String flowFlag) throws Exception {
		String ownerTaskCondition = Constant.FLOW_FRAME_BASKET_FETCH_MODEL_OWNER_TASK_CONDITION;
		ProcessQueryBuilder qryParam = new ProcessQueryBuilder();
		ReflectHandle.copy(ctrQryParam, qryParam);
		
		String queue=null;
		if(ContractConstant.RATE_AUDIT_FLAG.equals(flowFlag)){
			qryParam.put("F_StepName",ContractConstant.RATE_AUDIT);
			queue = LoanFlowQueue.CONTRACT_COMMISSIONER; 
	    }else if(ContractConstant.CTR_AUDIT_FLAG.equals(flowFlag)){
	    	qryParam.put("F_StepName", ContractConstant.CTR_AUDIT);
	    	queue =LoanFlowQueue.CONTRACT_CHECK;
	    }else if (ContractConstant.CTR_CREATE_FLAG.equals(flowFlag)) {
			qryParam.put("F_StepName", ContractConstant.CTR_CREATE);
			queue = LoanFlowQueue.CONTRACT_COMMISSIONER;
		}
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<Role> roleList = user.getRoleList();
		for(Role r:roleList){
		    if(  r.getId().equals(BaseRole.CONTRACT_MAKE_LEADER.id)||                 // 合同制作组长
		         r.getId().equals(BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id)||        // 合同制作组长
		         r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||              // 合同审核组长
                 r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER_ROLE_TYPE.id)||    // 合同审核组长
                 r.getId().equals(BaseRole.LOANER_DEPT_MASTER.id)||
                 r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id) ||
	             r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id) ||
	             r.getId().equals(BaseRole.XINJIEYEWU_LEADER.id)){                   // 部门总监
		        ownerTaskCondition = null;
		        break;   
		    }
		}
		TaskBean taskBean = flowService.fetchTaskItems(queue, qryParam,ownerTaskCondition,LoanFlowWorkItemView.class);
		List<LoanFlowWorkItemView> workItems = null;
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
		if (taskBean != null) {
			workItems = (List<LoanFlowWorkItemView>) taskBean.getItemList();
			for(LoanFlowWorkItemView item : workItems) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("flowName", item.getFlowName());
				map.put("stepName", item.getStepName());
				map.put("applyId", item.getApplyId());
				map.put("token", item.getToken());
				map.put("wobNum", item.getWobNum());
				Contract contract=new Contract();
				contract.setContractCode(item.getContractCode());
				contract.setLoanCode(item.getLoanCode());
				map.put("contract", contract);
				list.add(map);
			}
		}
		
		return list;
	}

}
