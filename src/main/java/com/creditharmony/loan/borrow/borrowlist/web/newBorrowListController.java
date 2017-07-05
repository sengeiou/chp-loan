package com.creditharmony.loan.borrow.borrowlist.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.event.LoanFlowLoad;
import com.creditharmony.loan.borrow.reconsider.event.ReconsiderLoad;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;


/**
 * 获取信借待办列表controller
 * @Class Name BorrowListController
 * @author zhangping
 * @Create In 2015年11月24日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/newBorrow/borrowlist")
public class newBorrowListController extends BaseController {

	

	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	@Autowired
	private LoanFlowLoad loanFlowLoad;
	@Autowired
	private ReconsiderLoad reconsiderLoad;
	
	
	/**
	 * 2015年11月24日 
	 * By zhangping
	 * @param model
	 * @param loanFlowQueryParam
	 * @param request
	 * @param page
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
		
		if(StringUtils.isNotEmpty(loanFlowQueryParam.getCustomerName())){
		    loanFlowQueryParam.setCustomerName(StringEscapeUtils.unescapeHtml4(loanFlowQueryParam.getCustomerName()));   
		}
        FlowPage page = new FlowPage();
		String viewName = null;
	    String roleType="";
		if (StringUtils.isEmpty(flowFlag)) {
			//要查询的工作流队列
			viewName = "loanflow_newBorrowlist_workItems";
			User user = UserUtils.getUser();
			Org org = user.getDepartment();
			List<Role> roles = user.getRoleList();
			for (Role r : roles) {
				//客服或者电销录入专员
				if (LoanRole.CUSTOMER_SERVICE.id.equals(r.getId()) || LoanRole.MOBILE_SALE_RECORDER.id.equals(r.getId())) {
					roleType = "1";
					if( LoanRole.MOBILE_SALE_RECORDER.id.equals(r.getId())){
						roleType = "4";
						loanFlowQueryParam.setTelesalesFlag(YESNO.YES.getCode());
					}
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
			String  agentView = "'"+LoanApplyStatus.CONTRACT_UPLOAD.getCode()+"','"+LoanApplyStatus.CONTRACT_MAKING.getCode()+"'";
			
			//客服或者电销录入专员
			if ("1".equals(roleType)) {
				loanFlowQueryParam.setStoreOrgId(org.getId());
				loanFlowQueryParam.setLoanStatusCode(agentView);
				// 门店副理查询
			} else if ("2".equals(roleType)) {

				loanFlowQueryParam.setStoreOrgId(org.getId());
				loanFlowQueryParam.setLoanStatusCode(agentView);
			} else if ("3".equals(roleType)) { // 部门总监
				loanFlowQueryParam.setLoanStatusCode(agentView);
			}else if("4".equals(roleType)){
				loanFlowQueryParam.setUserCode(user.getUserCode());
				loanFlowQueryParam.setLoanStatusCode(agentView);
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
        
        Page<LoanFlowWorkItemView> ps2=loanInfoService.findBorrow(loanFlowQueryParam,new Page<LoanFlowWorkItemView>(request,response));
        for(LoanFlowWorkItemView lf:ps2.getList()){
			String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",lf.getModel());
			lf.setModelLabel(modelLabel);
			String loanStatusName = DictCache.getInstance().getDictLabel("jk_loan_apply_status",lf.getLoanStatusCode());
			lf.setLoanStatusName(loanStatusName);
			String channelName = DictCache.getInstance().getDictLabel("jk_channel_flag",lf.getChannelName());
			lf.setChannelName(channelName);
		}
		//查询产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("itemList",ps2.getList());
		model.addAttribute("productList", productList);
		model.addAttribute("page", ps2);
		model.addAttribute("queryParam", loanFlowQueryParam);
		model.addAttribute("teleFlag", YESNO.NO.getCode());
		return "borrow/borrowlist/" + viewName;
	}
	/**
	 * 合同签订办理页面
	 * @param model
	 * @param request
	 * @param response
	 * @param loanCode
	 * @return
	 * @author FuLiXin
	 * @date 2017年2月21日 下午5:52:29
	 */
	@RequestMapping(value="openFrom")
	public String openFrom(Model model,HttpServletRequest request, 
			HttpServletResponse response,String applyId){
		BaseBusinessView view ;
			if("HJ0002".equals(applyId.substring(0, 6))){
				view = reconsiderLoad.load(applyId, ContractConstant.CUST_SERVICE_SIGN);
			}else{
				view = loanFlowLoad.load(applyId, ContractConstant.CUST_SERVICE_SIGN);
			}
			WorkItemView workItemView=new WorkItemView();
			workItemView.setBv(view);
			model.addAttribute("workItem",workItemView);
		return "borrow/contract/loanflow_custServiceSign_approve_0";
	}
	/**
	 * 客户放弃、门店拒绝
	 * @param model
	 * @param request
	 * @param response
	 * @param loanCode
	 * @param type
	 * @return
	 * @author FuLiXin
	 * @date 2017年2月22日 下午2:23:22
	 */
	@RequestMapping(value="dateEnd")
	public String dateEnd(Model model,HttpServletRequest request, 
			HttpServletResponse response,String loanCode,String type,String redirectUrl,String stepName,String applyId){
		
		loanInfoService.dateEnd(loanCode,type,applyId,stepName);
		
		return "redirect:" + adminPath + redirectUrl;
	}
    
}
