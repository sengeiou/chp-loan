package com.creditharmony.loan.channel.bigfinance.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.zcj.service.BigFinanceCreditBackService;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCGrantSureHelper;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Maps;

/**
 * 大金融债权列表
 * @Class Name BigFinanceCreditorBackController
 * @author 朱静越
 * @Create In 2016年8月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/bigfinance/back")
public class BigFinanceCreditorBackController extends BaseController{
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
    private UserManager userManager;
	@Autowired
	private ContractService contractService;
	@Autowired
	private BigFinanceCreditBackService bigFinanceCreditBackService;

	/**
	 * 大金融债权退回列表初始化
	 */
	@RequestMapping(value = "fetchTaskItems")
	protected String fetchTaskItems(Model model, LoanFlowQueryParam grtQryParam,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		// 门店申请冻结查询
		if (YESNO.YES.getCode().equals(grtQryParam.getLoanStatusCode())) {
			grtQryParam.setFrozenFlag(YESNO.YES.getCode());
		}else if(LoanApplyStatus.BIGFINANCE_REJECT.getCode().equals(grtQryParam.getLoanStatusCode())){
			grtQryParam.setFrozenFlag(YESNO.NO.getCode());
		}
		Page<LoanFlowWorkItemView> page = bigFinanceCreditBackService.getCreditBackList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		for (LoanFlowWorkItemView loanFlowWorkItemView : page.getList()) {
			loanFlowWorkItemView.setUrgentFlag(DictCache.getInstance().getDictLabel("jk_urgent_flag",
					loanFlowWorkItemView.getUrgentFlag()));
			loanFlowWorkItemView.setTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing",
					loanFlowWorkItemView.getTelesalesFlag()));
			loanFlowWorkItemView.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", 
					loanFlowWorkItemView.getLoanStatusName()));
			loanFlowWorkItemView.setChannelName(DictCache.getInstance().getDictLabel("jk_channel_flag", 
					loanFlowWorkItemView.getChannelName()));
			loanFlowWorkItemView.setDepositBank(DictCache.getInstance().getDictLabel("jk_open_bank", 
					loanFlowWorkItemView.getDepositBank()));
		}
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		
		// 债权退回列表权限控制
		String role = YESNO.NO.getCode();
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
		model.addAttribute("workItems", page);
		return "channel/bigfinance/bigFinnaceBackList";
	}
	
	/**
	 * 大金融债权返回到大金融待款项确认,状态为【待款项确认】
	 * 2016年8月23日
	 * By 朱静越
	 * @param applyIds 主键id进行区分
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "returnToConfirmation")
	public String returnToConfirmation(String applyIds) {
		String flag = BooleanType.TRUE;
		if (StringUtils.isEmpty(applyIds))
			return BooleanType.FALSE;
		String []arrayApplyId = applyIds.split(",");
		// 将查询出来的数据信息返回到大金融待款项确认列表
		try {
			for (String strApplyId:arrayApplyId) {
				Contract contract = contractService.findByApplyId(strApplyId);
				if (ObjectHelper.isNotEmpty(contract)) {
					bigFinanceCreditBackService.backToGrantSure(contract);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：returnToConfirmation，大金融债权返回到待款项确认发生异常，",e);
			flag = BooleanType.FALSE;
		}
		return flag;
	}
	
	/**
	 * 大金融债权退回到合同审核的处理，状态为【大金融拒绝】,大金融债权退回的操作是单笔操作
	 * 2016年8月24日
	 * By 朱静越
	 * @param model
	 * @param applyId 接收到的申请id
	 * @param loanCode 接收到的借款编号
	 * @param backBatchReason 退回原因
	 * @return 提示信息
	 */
	@ResponseBody
	@RequestMapping(value = "creditBackDeal")
	public String creditBackDeal(Model model, String applyId,String loanCode,String backBatchReason) {
		String retunMeassage = "单子退回成功";
		try {
			bigFinanceCreditBackService.backToContractAudit(loanCode, applyId, backBatchReason);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("大金融债权退回到合同审核失败，发生异常",e);
			retunMeassage = "大金额债权退回到合同审核失败，失败原因"+e.getMessage();
		}
		return retunMeassage;
	}
	
	/**
	 * 大金融驳回申请，更新工作流中的冻结标识为1,驳回申请需要进行排序
	 * 2016年10月12日
	 * By 朱静越
	 * @param applyId applyId
	 * @param loanCode 借款编码
	 * @param rejectReason 冻结原因
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="cancleFrozen")
	public String cancleFrozen(String applyId,String loanCode,String rejectReason){
		String retunMeassage = "驳回成功";
		try {
			logger.debug("方法：cancleFrozen，大金融债权退回列表驳回申请处理，借款编号为："+loanCode);
			bigFinanceCreditBackService.bigFinanceBackFrozen(applyId, loanCode, rejectReason);
			logger.debug("方法：cancleFrozen，大金融债权退回列表驳回申请处理结束，借款编号为："+loanCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("大金融驳回申请失败，发生异常",e);
			retunMeassage = "大金融驳回申请失败，失败原因"+e.getMessage();
		}
		return retunMeassage;
	}
	
	/**
	 * 打款表导出
	 * 2016年8月24日
	 * By 朱静越
	 * @param request
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param loanCodes 借款编号
	 */
	@RequestMapping(value = "exportRemit")
	public String exportRemit(HttpServletRequest request,RedirectAttributes redirectAttributes,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String loanCodes) {
		try {
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
				return null;
			}else {
				addMessage(redirectAttributes, "没有符合条件的需要导出的数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "打款表导出失败，信息："+e.getMessage());
			logger.error("打款导出发生失败，发生异常"+e.getMessage());
		}
		return "redirect:"
		+ adminPath
		+ "/channel/bigfinance/back/fetchTaskItems?menuId="+request.getParameter("menuId");
	}

	/**
	 * 汇总表导出
	 * 2016年8月24日
	 * By 朱静越
	 * @param request
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param loanCodes 借款编号
	 */
	@RequestMapping(value = "exportSummary")
	public String grantSumExl(HttpServletRequest request,LoanFlowQueryParam grtQryParam,
			HttpServletResponse response, String loanCodes,RedirectAttributes redirectAttributes) {
		try {
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
				return null;
			}else {
				addMessage(redirectAttributes, "没有符合条件的需要导出的数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "汇总表导出错误，原因："+e.getMessage());
			logger.error("汇总表导出错误，发生异常"+e.getMessage());
		}
		return "redirect:"
		+ adminPath
		+ "/channel/bigfinance/back/fetchTaskItems?menuId="+request.getParameter("menuId");
	}
	
	/**
	 * 根据条件去工作流获取所有借款编码
	 * 2016年9月19日
	 * By 朱静越
	 * @param grtQryParam
	 * @return
	 * @throws Exception
	 */
	private List<String> loadLoanCode(LoanFlowQueryParam grtQryParam) throws Exception{
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		List<LoanFlowWorkItemView> workItems = bigFinanceCreditBackService.getCreditBackList(grtQryParam);
		List<String> list = new ArrayList<String>();
		if (workItems != null) {
			for(LoanFlowWorkItemView item : workItems) {
				list.add(item.getLoanCode());
			}
		}
		return list;
	}
}
