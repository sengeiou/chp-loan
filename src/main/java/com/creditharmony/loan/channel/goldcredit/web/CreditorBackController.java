package com.creditharmony.loan.channel.goldcredit.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCGrantSureHelper;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Maps;

/**
 * 金信债权退回
 * 
 * @Class Name CreditorBackController
 * @author 张建雄
 * @Create In 2016年2月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/back")
public class CreditorBackController extends BaseController {
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
    private UserManager userManager;
	@Autowired
	private ContractService contractService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;

	/**
	 * 金信债权退回列表查询
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getJXCreditorInfo")
	protected String getJXCreditorInfo(Model model, LoanFlowQueryParam grtQryParam,HttpServletRequest request, HttpServletResponse response) {
		//为了查询，将门店id置空
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		//查询债权退回信息
		Page<LoanFlowWorkItemView> creditorListPage = loanGrantService.getJXCreditorList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		List<LoanFlowWorkItemView> workItems = creditorListPage.getList();
		//字典值的转换
		for (LoanFlowWorkItemView loanFlowWorkItemView : workItems) {
			loanFlowWorkItemView.setUrgentFlag(DictCache.getInstance().getDictLabel("jk_urgent_flag",loanFlowWorkItemView.getUrgentFlag()));
			loanFlowWorkItemView.setTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing",loanFlowWorkItemView.getTelesalesFlag()));
		}
		//查询产品列表
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		
		// 金信债权退回列表权限控制
		String role = YESNO.YES.getCode();
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<Role> roleList = user.getRoleList();
		/**款项统计组长和款项统计专员**/
		for (Role r : roleList) {
			if (r.getId().equals(BaseRole.MONEY_LEADER.id)
					|| r.getId().equals(BaseRole.MONEY_STATISTICIAN.id)
					|| r.getId().equals(BaseRole.LOANER_STATISTIC_MEMBER.id)
					|| r.getId().equals(BaseRole.LOANER_STATISTIC_LEADER.id)) {
				role = YESNO.YES.getCode();
				break;
			}
		}
    	model.addAttribute("role", role);
		model.addAttribute("params", grtQryParam);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", workItems);
		model.addAttribute("page", creditorListPage);
		return "channel/goldcredit/goldCreditBackList";
	}
	
	/**
	 * 从债权退回列表返回到金信待放款列表队列
	 * @param applyIds 借款编号
	 * @return TRUE OR  FALSE
	 */
	@ResponseBody
	@RequestMapping(value = "returnToConfirmation")
	public String returnToConfirmation(String applyIds) {
		String flag = BooleanType.TRUE;
		
		if(StringUtils.isEmpty(applyIds)) 
			return BooleanType.FALSE;
		//去掉工作流   从数据库取值
		LoanFlowQueryParam param=new LoanFlowQueryParam();
		String[] applyId=applyIds.split(",");
		param.setApplyIds(applyId);
		List<LoanFlowWorkItemView> workItems =loanGrantService.getJXCreditorList(param);
		//将查询出来的数据信息返回到金信待放款列表
		for (LoanFlowWorkItemView baseItem : workItems) {
			// 更新金信标志为财富标志并退回到待款项确认列表
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setApplyId(baseItem.getApplyId());
			loanInfo.setLoanCode(baseItem.getLoanCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
			//金信债权退回到待款项确认  修改orderField 用于排序
			LoanInfo loanInfoOrder=new LoanInfo();
			loanInfoOrder.setApplyId(baseItem.getApplyId());
			loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
			String orderField=createOrderFileldService.backGrantSureByJx(loanInfoOrder);
			loanInfo.setOrderField(orderField);
			
			// 更新退回标志
			Contract updContract = new Contract();
			updContract.setContractCode(baseItem.getContractCode());
			updContract.setBackFlag(YESNO.NO.getCode());
			// 处理金信状态
			LoanGrant loanGrant = new LoanGrant();
			loanGrant.setContractCode(baseItem.getContractCode());
			loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
			loanGrantService.cancelJXFlag(loanInfo, updContract, loanGrant, ExportFlagConstants.GOLD_RETURN_TO_COMFIRM,
					LoanApplyStatus.GOLDCREDIT_RIGHT_RETURN.getName());
		}
		return flag;
	}
	
	/**
	 * 办理查询退回的数据信息 2016年2月25日 By 张建雄
	 * 
	 * @param apply
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "creditBackDeal")
	public String creditBackDeal(Model model, String applyId,String loanCode,String backBatchReason) {
		String flag = BooleanType.FALSE;
		// 根据applyId查询页面中显示放款确认的字段
		try {
			if (StringUtils.isNotEmpty(applyId)) {
				//废除工作流   改借款表中借款状态 实现退回到合同审核
				LoanFlowQueryParam param=new LoanFlowQueryParam();
				String[] applyIds={applyId};
				param.setApplyIds(applyIds);
				List<LoanFlowWorkItemView> workItemList =loanGrantService.getJXCreditorList(param);
				LoanFlowWorkItemView itemView=workItemList.get(0);
				if(itemView!=null){
					//修改借款表中借款状态
					LoanInfo loanInfo=new LoanInfo();
					loanInfo.setLoanCode(loanCode);
					loanInfo.setApplyId(applyId);
					loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
					//金信债权退回到合同审核 修改orderField 用于排序
					LoanInfo loanInfoOrder=new LoanInfo();
					loanInfoOrder.setApplyId(applyId);
					loanInfoOrder.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
					String orderField=createOrderFileldService.backContractCheckByJx(loanInfoOrder);
					loanInfo.setOrderField(orderField);
					//重置金信状态
					LoanGrant loanGrant=new LoanGrant();
					loanGrant.setContractCode(itemView.getContractCode());
					loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
					loanGrantService.sendBackCreditorInfo(loanInfo, loanGrant, backBatchReason);
					flag = BooleanType.TRUE;
				}
			}
		} catch (Exception e) {
			flag = BooleanType.FALSE;
			e.printStackTrace();
			logger.error("金信债权退回列表，退回到合同审核：creditBackDeal,发生异常，信息为："+e.toString());
		}
		return flag;
	}

	/**
	 * 批量取消金信标识,也支持单个取消金信标识 2016年2月25日 By 张建雄
	 * 
	 * @param apply
	 * @param loanMarking
	 * @return String
	 */
	@RequestMapping(value = "cancelJINXINFlag")
	public String cancelJINXINFlag(String applyIds, Model model, LoanFlowQueryParam grtQryParam,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(applyIds)) {
			String[] arrayApplyId = applyIds.split(",");
			// 废除查询工作流 改为查询数据库
			grtQryParam.setApplyIds(arrayApplyId);
			//为了查询，将门店id置空
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
			List<LoanFlowWorkItemView> jxCreditorList = loanGrantService.getJXCreditorList(grtQryParam);
			for (LoanFlowWorkItemView baseTaskItemView : jxCreditorList) {
				// 更新金信标志为财富标志并退回到待款项确认列表
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setApplyId(baseTaskItemView.getApplyId());
				loanInfo.setLoanCode(baseTaskItemView.getLoanCode());
				loanInfo.setLoanFlag(ChannelFlag.CHP.getCode());
				loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
				//金信债权退回 取消金信标志 修改orderField 用于排序
				LoanInfo loanInfoOrder=new LoanInfo();
				loanInfoOrder.setApplyId(baseTaskItemView.getApplyId());
				loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
				String orderField=createOrderFileldService.cancelJxFlag(loanInfoOrder);
				loanInfo.setOrderField(orderField);
				
				// 更新退回标志
				Contract updContract = new Contract();
				updContract.setContractCode(baseTaskItemView.getContractCode());
				updContract.setBackFlag(YESNO.NO.getCode());
				updContract.setChannelFlag(ChannelFlag.CHP.getCode());
				// 处理金信状态
				LoanGrant loanGrant = new LoanGrant();
				loanGrant.setContractCode(baseTaskItemView.getContractCode());
				loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
				loanGrantService.cancelJXFlag(loanInfo, updContract, loanGrant, ExportFlagConstants.GOLD_CREDIT_CANCEL,
						LoanApplyStatus.GOLDCREDIT_RIGHT_RETURN.getName());
			}

		}
		return getJXCreditorInfo(model, new LoanFlowQueryParam(), request, response);
	}
	/**
	 * 打款表导出，默认导出查询条件下的全部的单子，有选择的按照选择进行导出 2015年12月22日 By 路志友
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "exportRemit")
	public void exportRemit(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String loanCodes) {
		List<String> loanCodeList = null;
		if (StringUtils.isEmpty(loanCodes)) {
			// 没有选中的，默认为全部,通过
			loanCodeList=this.loadLoanCode(grtQryParam);
		} else {
			// 如果有选中的单子,将选中的单子导出
			loanCodeList=Arrays.asList(loanCodes.split(","));   
		}
		if (loanCodeList != null && loanCodeList.size() != 0) {
			Map<String,Object> list = Maps.newHashMap();
			list.put("list", loanCodeList);
			ExportGCGrantSureHelper.customerCallTableExport(list, response, userManager,threePartFileName.getGoldCreditExportFileName(ExportFlagConstants.GOLD_CREDIT_RETURN));
		}
	}
	/**
	 * 汇总表导出，默认导出查询条件下的全部的单子，有选择的按照选择进行导出
	 * 2015年12月22日
	 * By 张建雄
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "exportSummary")
	public void grantSumExl(HttpServletRequest request,LoanFlowQueryParam grtQryParam,
			HttpServletResponse response, String loanCodes) {
		List<String> loanCodeList = null;
		if (StringUtils.isEmpty(loanCodes)) {
			// 没有选中的，默认为全部,通过
			loanCodeList=this.loadLoanCode(grtQryParam);
		} else {
			// 如果有选中的单子,将选中的单子导出
			loanCodeList=Arrays.asList(loanCodes.split(","));   
		}
		if (loanCodeList != null && loanCodeList.size() != 0) {
			Map<String,Object> map = Maps.newHashMap();
			map.put("list", loanCodeList);
			ExportGCGrantSureHelper.summarySheetExport(map, response, userManager, threePartFileName.getGoldCreditSumExportFileName(ExportFlagConstants.GOLD_CREDIT_RETURN));
		}
	}
	/**
	 * 根据条件去工作流获取所有借款编码
	 * 2016年3月4日
	 * xiaoniu.hu
	 * @param grtQryParam 画面查询条件
	 * @return
	 */
	private List<String> loadLoanCode(LoanFlowQueryParam grtQryParam){
		//废除工作流  从数据库查询数据
		List<String> list=new ArrayList<String>();
		//为了查询，将门店id置空
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		//查询债权退回信息
		List<LoanFlowWorkItemView> workItems = loanGrantService.getJXCreditorList(grtQryParam);
		for(LoanFlowWorkItemView item : workItems) {
			list.add(item.getLoanCode());
		}
		return list;
	}
}
