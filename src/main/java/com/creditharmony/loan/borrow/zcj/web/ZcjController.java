package com.creditharmony.loan.borrow.zcj.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.ex.SendMoneyEx;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.grant.util.GrantCallUtil;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.zcj.excel.ExportZcjHelper;
import com.creditharmony.loan.borrow.zcj.service.ZcjGrantSureService;
import com.creditharmony.loan.borrow.zcj.service.ZcjService;
import com.creditharmony.loan.borrow.zcj.view.ZcjEntity;
import com.creditharmony.loan.channel.bigfinance.service.BfSendDataService;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCGrantSureHelper;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 大金融资产家
 * 
 * @Class Name ZcjController
 * @author 
 * @Create In 2016年8月24日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/zcj")
public class ZcjController extends BaseController{

	@Autowired
	private ZcjService zcjService;
	
	@Autowired
    private LoanPrdMngService loanPrdMngService;

	@Autowired
	private BfSendDataService bfSendDataService;
	
	@Autowired
    private UserManager userManager;
	
	@Autowired
	private ThreePartFileName threePartFileName;
	
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private GrantSureService grantSureService;
	
	@Autowired
	private ZcjGrantSureService zcjGrantSureService;
	
	@RequestMapping(value = "getFinanceInfo")
	public String getFinanceInfo(Model model, ZcjEntity zcj,
			HttpServletRequest request,
			HttpServletResponse response) {
		if(zcj.getStoreId()!=null&&zcj.getStoreId().length()>0){
			zcj.setOrgIds(zcj.getStoreId().split(","));
		}
		//查询退款数据列表
		Page<ZcjEntity> urgePage = zcjService.getFinanceInfo(new Page<ZcjEntity>(request, response), zcj);
		List<ZcjEntity> amountSum = zcjService.getAmountSum(zcj);
		if(!amountSum.isEmpty()&&amountSum.get(0)!=null){
			model.addAttribute("amountSum", amountSum.get(0));
		}else{
			ZcjEntity zcjE = new ZcjEntity();
			zcjE.setContractAmountSum(new BigDecimal("0.00"));
			zcjE.setGrantAmountSum(new BigDecimal("0.00"));
			zcjE.setLoanAuditAmountSum(new BigDecimal("0.00"));
			model.addAttribute("amountSum", zcjE);
		}
		//转换字典值为中文名称
		/*if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for (ZcjEntity ex : urgePage.getList()) {
				ex.setAppTypeName(DictCache.getInstance().getDictLabel("jk_app_type", ex.getAppType()));
			}
		}*/
		model.addAttribute("zcj", zcj);
		model.addAttribute("urgeList", urgePage);
		return "borrow/zcj/financeInfoList";
	}
	
	/**
	 * 大金融待款项确认页面查询
	 */
	@RequestMapping(value = "fetchTaskItems")
	protected String fetchTaskItems(Model model, LoanFlowQueryParam grtQryParam,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		Page<LoanFlowWorkItemView> page = zcjGrantSureService.getZcjGrantSureList(
				new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		List<LoanFlowWorkItemView> workItems = page.getList();
		for (LoanFlowWorkItemView loanFlowWorkItemView : workItems) {
			loanFlowWorkItemView.setTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing",
					loanFlowWorkItemView.getTelesalesFlag()));
			loanFlowWorkItemView.setContractVersion(DictCache.getInstance().getDictLabel("jk_contract_ver", 
					loanFlowWorkItemView.getContractVersion()));
			loanFlowWorkItemView.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", 
					loanFlowWorkItemView.getLoanStatusName()));
			loanFlowWorkItemView.setDepositBank(DictCache.getInstance().getDictLabel("jk_open_bank", 
					loanFlowWorkItemView.getDepositBank()));
		}
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		User user =  (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
        // 不是合同审核操作人员 默认为1
        String notContractAudit = YESNO.YES.getCode();
        // 款项确认权限控制
        List<Role> roleList = user.getRoleList();
        for(Role r:roleList){
          if(r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)||
           r.getId().equals(BaseRole.LOANER_CONTRACT_APPROVER.id)||
           BaseRole.CONTRACT_MAKE_LEADER.id.equals(r.getId())||
           BaseRole.LOANER_CONTRACT_MAKER.id.equals(r.getId())||
           BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id.equals(r.getId())){
           notContractAudit=YESNO.NO.getCode();
           break;   
           }
        } 
        model.addAttribute("notContractAudit", notContractAudit);
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", workItems);
		model.addAttribute("zcjPage", page);
		return "borrow/zcj/confirmInfoList";
	}

    /**
     * 大金融待款项确认操作：会将特定的信息推送到大金融，推送成功之后，数据流转到分配卡号
     * 2017年2月17日
     * By 朱静越
     * @param model
     * @param gqp
     * @param workItem
     * @param redirectAttributes
     * @param response
     * @param request
     * @return
     */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="dispatchFlowStatus")
	public String dispatchFlowStatus(Model model,LoanFlowQueryParam loanFlowQueryParam,
			RedirectAttributes redirectAttributes,String response,HttpServletRequest request){
		ZcjEntity zcj = new ZcjEntity();
		zcj.setContractCode(loanFlowQueryParam.getContractCode());
		zcj.setLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		List infoList = zcjService.getConfirmInfo(zcj);
		if (infoList.isEmpty()) {
			return "该数据不是"+LoanApplyStatus.LOAN_SEND_CONFIRM.getName()+"数据";
		}
		List frozenList = zcjService.getIsFrozenInfo(loanFlowQueryParam.getContractCode());
		if (frozenList.isEmpty()) {
			return "该数据已被冻结";
		}
		String strUrgeFlag = loanFlowQueryParam.getUrgentFlag();
    	String curBigFinanceLetter = threePartFileName.getBigFinanceCur();
        String curUrgentFlag = threePartFileName.getUrgeCur();
    	//向大金融推送数据
    	logger.info("大金额推送---->开始");
    	Map<String, String> resut = null;
    	try {
    		resut = bfSendDataService.sendExchangeDebtInfo(loanFlowQueryParam.getContractCode(),
    				curBigFinanceLetter,curUrgentFlag,strUrgeFlag,new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("大金融推送债券发生异常，合同编号为："+loanFlowQueryParam.getContractCode()+",异常信息为："+e);
		}
		return resut.get(loanFlowQueryParam.getContractCode());
	}
    
    /**
     * 上传回执结果，列表中的每一条数据都需要向大金融推送债券，推送成功之后数据进行流转
     * 2017年2月17日
     * By 朱静越
     * @param request
     * @param response
     * @param model
     * @param file
     * @param redirectAttributes
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "importResult")
	public String importResult(MultipartRequest request,HttpServletResponse response, ModelMap model,
		@RequestParam(value = "file", required = false) MultipartFile file,RedirectAttributes redirectAttributes) {
	
		ExcelUtils excelutil = new ExcelUtils();
		
		List<SendMoneyEx> datalist = (List<SendMoneyEx>)excelutil.importExcel(
				LoanFileUtils.multipartFile2File(file), 0, 0,
				SendMoneyEx.class);
		ZcjEntity zcj = new ZcjEntity();
		//保存待回访的数据
		List<SendMoneyEx> revisitStatusWaitContractCodeList = Lists.newArrayList();
		if (datalist != null && datalist.size() != 0) {
			//验证合同号是否存在
			for (int i=1;i<datalist.size()-1;i++) {
				zcj.setContractCode(datalist.get(i).getContractCode());
				zcj.setLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
				zcj.setLoanCustomerName(datalist.get(i).getCustomerName());
				zcj.setCustomerCertNum(datalist.get(i).getCustomerCertNum());
				List infoList = zcjService.getConfirmInfo(zcj);
				if (infoList.isEmpty()) {
					addMessage(redirectAttributes, "上传回执结果失败,"+zcj.getContractCode()+"合同编号不存在!");
					return "redirect:" + adminPath
							+ "/borrow/zcj/fetchTaskItems";
				}
				List frozenList = zcjService.getIsFrozenInfo(datalist.get(i).getContractCode());
				if (frozenList.isEmpty()) {
					addMessage(redirectAttributes, "上传回执结果失败,"+zcj.getContractCode()+"合同已被冻结!");
					return "redirect:" + adminPath
							+ "/borrow/zcj/fetchTaskItems";
				}
				//验证是否有待回访的数据
				Contract contract = contractService.findByContractCode(datalist.get(i).getContractCode());
				if(contract != null && (GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(contract.getRevisitStatus())||
						GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(contract.getRevisitStatus()))){
					revisitStatusWaitContractCodeList.add(datalist.get(i));
				}
			}
			if(revisitStatusWaitContractCodeList.size() > 0){
				StringBuffer messageBuffer = new StringBuffer();
				messageBuffer.append(GrantCommon.REVISIT_STATUS_INFO_TIP);
				for(SendMoneyEx sme : revisitStatusWaitContractCodeList){
					messageBuffer.append(sme.getCustomerName()+ " " + sme.getContractCode() + ",");
				}
				String message = messageBuffer.toString();
				message = message.substring(0, message.length()-1);
				addMessage(redirectAttributes,message);
				return "redirect:" + adminPath + "/borrow/zcj/fetchTaskItems";
			}
			/** 获取加急与不加急当天最大批次号 */
			String curBigFinanceLetter = threePartFileName.getBigFinanceCur();
	        String curUrgentFlag = threePartFileName.getUrgeCur();
	        Date submitDate = new Date();
			// 发送给大金融
			for (int i=1;i<datalist.size()-1;i++) {
				zcj.setContractCode(datalist.get(i).getContractCode());
				zcj.setLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
				zcj.setLoanCustomerName(datalist.get(i).getCustomerName());
				zcj.setCustomerCertNum(datalist.get(i).getCustomerCertNum());
				String strUrgeFlag = YESNO.YES.getName().equals(datalist.get(i).getLoanUrgentFlag())?"1":"";
				Map<String, String> resut = null;
				try {
					logger.debug("方法：importResult处理，发送债券到大金融系统开始："
							+ "数据合同编号为："+datalist.get(i).getContractCode());
					resut = bfSendDataService.sendExchangeDebtInfo(datalist.get(i).getContractCode(),
							curBigFinanceLetter,curUrgentFlag,strUrgeFlag,submitDate);
					logger.debug("方法：importResult处理，发送债券到大金融系统结束：数据合同编号为："
							+datalist.get(i).getContractCode());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("上传回执结果，推送大金融债券出现异常，合同编号为："
					+datalist.get(i).getContractCode()+",异常信息为："+e);
				}
		    	if(!BooleanType.TRUE.equals(resut.get(datalist.get(i).getContractCode()))){
		    		addMessage(redirectAttributes, "上传回执结果失败,"+datalist.get(i).getContractCode()+"上传失败!");
		    		return "redirect:" + adminPath
		    				+ "/borrow/zcj/fetchTaskItems";
		    	}
			}
		} 
		return "redirect:" + adminPath
				+ "/borrow/zcj/fetchTaskItems";
	}
	
    /**
     * 大金融退回到合同审核处理
     * 2017年2月21日
     * By 朱静越
     * @param workItem 参数包括：contractCode applyId loanCode 退回原因
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "grantSureBack")
	public String grantSureBack(LoanFlowWorkItemView workItem) {
		try{
			logger.debug("方法：grantSureBack，大金融待款项确认退回到合同审核处理开始，合同编号为："+workItem.getContractCode());
			grantSureService.updGrantSureBack(workItem);
			logger.debug("方法：grantSureBack，大金融待款项确认退回到合同审核处理结束，合同编号为："+workItem.getContractCode());
	        try {
	        	logger.debug("方法：grantSureBack，给呼叫中心推送数据开始");
				//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
				GrantCallUtil.grantToCallUpdateRevisitStatus(workItem.getContractCode(),
						GrantCommon.GRANTCALL_CUSTOMER_STATUS_MODIFY);
				logger.debug("方法：grantSureBack，给呼叫中心推送数据结束");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("方法：grantSureBack 异常，退回时，给呼叫中心推送数据失败,合同编号为："+workItem.getContractCode(),e);
			}
	        
		}catch(Exception e){
			e.printStackTrace();
			logger.error("方法：grantSureBack，大金融待款项确认退回到合同审核发生异常，合同编号为："+workItem.getContractCode(),e);
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 驳回申请
	 * 2017年2月21日
	 * By 朱静越
	 * @param workItem 参数 contractCode applyId loanCode
	 * @param autoGrantResult 驳回申请原因
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value = "grantSureRejectBack")
	public String grantSureRejectBack(LoanFlowWorkItemView workItem, String autoGrantResult) {
    	try {
    		logger.debug("方法grantSureRejectBack：大金融待款项确认驳回申请处理开始，合同编号为："+workItem.getContractCode());
    		grantSureService.updBackFrozen(workItem, autoGrantResult);
    		logger.debug("方法grantSureRejectBack：大金融待款项确认驳回申请处理结束，合同编号为："+workItem.getContractCode());
    		try {
    			//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
    			logger.debug("方法grantSureRejectBack：大金融待款项确认驳回申请向呼叫中心推送数据开始，"
    					+ "合同编号为："+workItem.getContractCode());
    			GrantCallUtil.grantToCallUpdateRevisitStatus(workItem.getContractCode(),
    					GrantCommon.GRANTCALL_CUSTOMER_STATUS_GRANTBEFORE);
    			logger.debug("方法grantSureRejectBack：大金融待款项确认驳回申请向呼叫中心推送数据结束，"
    					+ "合同编号为："+workItem.getContractCode());
    		} catch (Exception e) {
    			e.printStackTrace();
    			logger.error("方法：grantSureRejectBack 异常，驳回申请，给呼叫中心推送数据失败",e);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法 grantSureRejectBack，驳回申请处理发生异常",e);
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 客户信息表导出 
	 * 2017年2月21日
	 * By 朱静越
	 * @param request
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param loanCodes 借款编码
	 */
	@RequestMapping(value = "exportCustomer")
	public void exportCustomer(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String loanCodes) {
		String[] loanCodeList = null;
		if (StringUtils.isNotEmpty(loanCodes)) {
			loanCodeList= loanCodes.split(",");
			grtQryParam.setLoanCodes(loanCodeList);
		}else {
			grtQryParam.setLoanCodes(null);
		}
		grtQryParam.setChannelCode(ChannelFlag.ZCJ.getCode());
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		ExportGCGrantSureHelper.customerExport(grtQryParam, response,userManager);
	}
	
	/**
	 * 查询导出数据:根据查询条件
	 * 2017年2月21日
	 * By 朱静越
	 * @param grtQryParam
	 * @return
	 */
	private List<String> loadLoanCode(LoanFlowQueryParam grtQryParam){
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		List<LoanFlowWorkItemView> workItems = zcjGrantSureService.getZcjGrantSureList(grtQryParam);
		List<String> list = new ArrayList<String>();
		for(LoanFlowWorkItemView item : workItems) {
			list.add(item.getLoanCode());
		}
		return list;
	}
	
	/**
	 * 打款表导出
	 * 2017年2月21日
	 * By 朱静越
	 * @param request
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param loanCodes 借款编号
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
			ExportZcjHelper.customerCallTableExport(list, response, userManager,
					threePartFileName.getGoldZcjExportFileName(ExportFlagConstants.GOLD_ZCJ_MONEY));
		}else{
			Map<String,Object> list = Maps.newHashMap();
			loanCodeList.add("");
			list.put("list", loanCodeList);
			ExportZcjHelper.customerCallTableExport(list, response, userManager,
					threePartFileName.getGoldZcjExportFileName(ExportFlagConstants.GOLD_ZCJ_MONEY));
		}
	}

	/**
	 * 汇总表导出
	 * 2017年2月21日
	 * By 朱静越
	 * @param request
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param loanCodes 借款编号
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
			ExportZcjHelper.summarySheetExport(map, response, userManager, 
					threePartFileName.getGoldZcjExportFileName(ExportFlagConstants.GOLD_ZCJ_SUMMARY));
		}else{
			Map<String,Object> map = Maps.newHashMap();
			loanCodeList.add("");
			map.put("list", loanCodeList);
			ExportZcjHelper.summarySheetExport(map, response, userManager, 
					threePartFileName.getGoldZcjExportFileName(ExportFlagConstants.GOLD_ZCJ_SUMMARY));
		}
	}
}