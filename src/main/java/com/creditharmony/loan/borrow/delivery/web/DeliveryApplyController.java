package com.creditharmony.loan.borrow.delivery.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.exception.WorkflowException;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.entity.ex.UserView;
import com.creditharmony.loan.borrow.delivery.service.DeliveryApplyService;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;

/**
 * 发起交割申请
 * @Class Name DeliveryApplyController
 * @author lirui
 * @Create In 2015年12月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/apply")
public class DeliveryApplyController extends BaseController {

	@Autowired
	private DeliveryApplyService das;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * FlowService 查询流程待办列表、提交流程
	 */
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	
	/**
	 * 交割申请列表
	 * 2015年12月14日
	 * By lirui
	 * @param request 获得request
	 * @param response 获得response
	 * @param params 检索参数
	 * @param m Model模型
	 * @return 交割申请列表页面
	 */
	@RequestMapping(value = "deliveryApply")
	public String deliveryApply(HttpServletRequest request,HttpServletResponse response,DeliveryParamsEx params,Model m) {
		// 如果搜索条件不为空 ,将检索条件存到session中
		if (params != null) {
			m.addAttribute("params",params);
		}
		String errorMesCode = request.getParameter("errorMesCode")==null?"":request.getParameter("errorMesCode").toString();
		if("1".equals(errorMesCode)){
			m.addAttribute("errorMesStr", LoanApplyStatus.RULE_ENGINE.getName()+"状态,不允许交割!");
		}else if("2".equals(errorMesCode)){
			m.addAttribute("errorMesStr", "记录处于工作流转中,不允许交割!");
		}else{
			m.addAttribute("errorMesStr", "");
		}
		//数据权限
		   String queryRight = DataScopeUtil.getDataScope("b", SystemFlag.LOAN.value);
		   params.setQueryRight(queryRight);
		// 获取待交割申请列表并封装到Modle中
		Page<DeliveryViewEx> delPage = das.deliveryApplyList(new Page<DeliveryViewEx>(request, response), params);
		List<DeliveryViewEx> list = delPage.getList();
		// huan
		for (DeliveryViewEx deliveryViewEx : list) {
			deliveryViewEx.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_apply_status", deliveryViewEx.getDictLoanStatus()));
		}
		List<OrgView> orgs = das.orgs();
		m.addAttribute("orgs", orgs);
		m.addAttribute("delPage", delPage);		
//		request.getAttribute("errorMesStr").toString();
		
		return "apply/delivery/deliveryApply";
	} 	
	
	/**
	 * 通过借款编码查询办理信息,异步弹出交割申请办理页面
	 * 2015年12月8日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 封装了交割信息的对象
	 */
	@ResponseBody
	@RequestMapping(value = "applyTransact")
	public DeliveryViewEx deliveryApplyTransact(String loanCode) {
		DeliveryViewEx dv = das.applyInfoByloanCode(loanCode);	
        String imageUrl = imageService.getImageUrl(FlowStep.TRANSLATE_APPLY.getName(),loanCode);
        dv.setImageUrl(imageUrl);
		return dv;
	}
	
	/**
	 * 批量申请交割
	 * 2015年12月14日
	 * By lirui
	 * @param m Model模型
	 * @param loanCodes 要交割项的借款编码
	 * @param dv 封装了交割信息
	 * @return 重定向到交割申请列表
	 */
	@RequestMapping(value = "applyBatchTransact",method=RequestMethod.POST)
	public String deliveryApplyBatchTransate(HttpServletRequest request,Model m,String[] loanCodes,DeliveryViewEx dv) {
		/**
		 * 交割条件验证
		 * 如果存在记录状态为2“汇城待审”或者查询到流程信息，不允许进行交割
		 */
		String errorMesCode = "";
		LoanInfo loanInfo = null;
		String applyId = null;
		WorkItemView srcWorkItem = null;
		for(String loanCode : loanCodes){
			loanInfo = loanInfoService.selectByLoanCode(loanCode);
			//如果当前记录状态为2“汇城待审”或者查询到流程信息，不允许进行交割
			if(loanInfo ==null || (LoanApplyStatus.RULE_ENGINE.getCode()).equals(loanInfo.getDictLoanStatus())){
				errorMesCode ="1";
				break;
			}else{
				try{
					/**
					 *  查询流程数据
					 *  1、t_jk_reconsider_apply复议申请表，
					 *  根据loanCode查询复议申请表是否存在记录，如果存在取复议申请表中的applyid，如果不存在取t_jk_loan_info借款表的applyid
					 */
					if(loanInfo != null){
						applyId = areaService.getApplyIdByLoanCode(loanCode);
						if(StringUtils.isNotEmpty(applyId)){
							srcWorkItem = flowService.loadWorkItemViewForAdmin(loanInfo.getApplyId());
							if(srcWorkItem!=null){
								errorMesCode =  "2";
								break;
							}
						}
					}
				}catch(WorkflowException e){
					//工作流查询异常
				}
			}
		}
		request.setAttribute("errorMesStr", errorMesCode);
		if("".equals(errorMesCode)){
			das.batchApply(dv, loanCodes);
		}
		
		return "redirect:"+adminPath+"/borrow/apply/deliveryApply";
	}
	
	/**
	 * 办理交割申请
	 * 2015年12月14日
	 * By lirui
	 * @param dv 要提交的参数
	 * @return 刷新交割申请列表
	 */
	@RequestMapping(value = "updateDelivery")
	public String updateDelivery(HttpServletRequest request,DeliveryViewEx dv) {
		LoanInfo loanInfo = loanInfoService.selectByLoanCode(dv.getLoanCode());
		String errorMesCode = "";
		//如果当前记录状态为2“汇城待审”或者查询到流程信息，不允许进行交割
		if(loanInfo ==null || (LoanApplyStatus.RULE_ENGINE.getCode()).equals(loanInfo.getDictLoanStatus())){
			errorMesCode ="1";
		}else{
			try{
				/**
				 *  查询流程数据
				 *  1、t_jk_reconsider_apply复议申请表，
				 *  根据loanCode查询复议申请表是否存在记录，如果存在取复议申请表中的applyid，如果不存在取t_jk_loan_info借款表的applyid
				 */
				if(loanInfo != null){
					String applyId = areaService.getApplyIdByLoanCode(dv.getLoanCode());
					if(StringUtils.isNotEmpty(applyId)){
						WorkItemView srcWorkItem = flowService.loadWorkItemViewForAdmin(loanInfo.getApplyId());
						if(srcWorkItem!=null){
							errorMesCode =  "2";
						}
					}
				}
			}catch(WorkflowException e){
				//工作流查询异常
			}
		}
		request.setAttribute("errorMesStr", errorMesCode);
		if("".equals(errorMesCode)){
			das.insertDelivery(dv);
		}
		
		return "redirect:"+adminPath+"/borrow/apply/deliveryApply?errorMesCode="+errorMesCode;
//		return deliveryApply(request,response,null,new BindingAwareModelMap());
	}	
	
	/**
	 * 根据门店ID异步获取门店下团队经理,客服,外访
	 * 2015年12月25日
	 * By lirui
	 * @param orgCode 门店ID
	 * @return 异步处理数据集合
	 */
	@ResponseBody
	@RequestMapping(value = "getTeam")
	public Map<String, List<UserView>> getTeam(String orgCode) {
		List<UserView> teams = das.teamManager(orgCode);
		List<UserView> services = das.service(orgCode);
		List<UserView> outBounds = das.outBound(orgCode);
		Map<String, List<UserView>> maps = new HashMap<String, List<UserView>>();
		maps.put("teams", teams);
		maps.put("services", services);
		maps.put("outBounds", outBounds);
		return maps;
	}
	
	/**
	 * 根据团队经理工号异步获取客户经理
	 * 2015年12月25日
	 * By lirui
	 * @param userCode 团队经理工号
	 * @return 客户经理集合
	 */
	@ResponseBody
	@RequestMapping(value = "getManager")	
	public List<UserView> getManager(String userCode){
		List<UserView> managers = das.manager(userCode);
		return managers;
	}

}
