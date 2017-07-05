package com.creditharmony.loan.channel.jyj.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.GrantUrgeBackService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantBFEx;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantEx;
import com.creditharmony.loan.channel.jyj.service.JYJGrantService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;

/**
 * 放款处理
 * @Class Name GrantController
 * @author 朱静越
 * @Create In 2015年12月7日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/jyj")
public class JYJGrantController extends BaseController {
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantDeductsService grantDeductsService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
	private GrantUrgeBackService grantUrgeBackService;
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private JYJGrantService grantService;
	@Autowired
	private ContractDao contractDao;
	
	/**
	 * 查询放款列表页面数据
	 * 2017年1月20日
	 * By 朱静越
	 * @param model
	 * @param loanFlowQueryParam
	 * @param request
	 * @param response
	 * @param listFlag
	 * @return
	 */
	@RequestMapping(value = "grantItem")
	public String grantDone(Model model, LoanFlowQueryParam loanFlowQueryParam,
			HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser(); // 获取登录人的id
		loanFlowQueryParam.setDealUser(user.getId());
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		Page<LoanFlowWorkItemView> grantPage = grantService.getJyjGrantLists(
				new Page<LoanFlowWorkItemView>(request, response), loanFlowQueryParam);
		Double totalGrantMoney = 0.00;
		List<LoanFlowWorkItemView> grantLists = grantPage.getList();
		if (ObjectHelper.isNotEmpty(grantLists)) {
			totalGrantMoney = grantLists.get(0).getTotalGrantMoney();
		}
		// 放款批次的查询，初始化查询下拉列表的
		model.addAttribute("LoanFlowQueryParam", loanFlowQueryParam);
		model.addAttribute("workItems", grantPage);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalItem", grantPage.getCount());
		return "channel/jyj/jyj_grantList";
	}

	/**
	 * 首次放款表导出
	 * 默认导出查询条件下的全部的单子
	 * 有选择的按照选择进行导出 只对网银的单子进行导出 
	 * 2015年12月22日 
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "grantExl")
	public String grantExl(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal,String isBreakCode, RedirectAttributes redirectAttributes) {
		try {
			logger.debug("放款表导出开始：" + DateUtils.getDate("yyyyMMddHHmmss"));
			ExcelUtils excelutil = new ExcelUtils();
			List<JYJGrantEx> grantList = new ArrayList<JYJGrantEx>();
			BigDecimal totalAmount = new BigDecimal("0.00");
			String[] ids = null;
			User user = UserUtils.getUser(); // 获取登录人的id
			grtQryParam.setDealUser(user.getId());
			if (StringUtils.isEmpty(idVal)) {
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
			} else {
				ids = idVal.split(",");
				grtQryParam.setApplyIds(ids);
			}
			List<LoanFlowWorkItemView> grantLists = grantService.getJyjGrantLists(grtQryParam);
			if (ArrayHelper.isNotEmpty(grantLists)) {
				List<String> contractCodeList = new ArrayList<String>();
				for (LoanFlowWorkItemView loanFlowWorkItemView : grantLists) {
					if (ApplyInfoConstant.FROZEN_FLAG.equals(loanFlowWorkItemView
							.getFrozenFlag()))
						contractCodeList.add(loanFlowWorkItemView.getContractCode());
					break;
				}
				if (contractCodeList.size() != 0) {
					addMessage(redirectAttributes, "合同编号为" + contractCodeList.get(0) + "的客户门店已申请冻结！");
					return "redirect:" + adminPath + "/channel/jyj/grantItem";
				}else {
					ids = new String[grantLists.size()];
					for (int i = 0; i < grantLists.size(); i++) {
						totalAmount = grantService.grantItemDao(grantList, totalAmount,grantLists.get(i).getApplyId());
					}
					if(!ObjectHelper.isEmpty(grantList) && grantList.size()>0){
						JYJGrantEx lastTotal = new JYJGrantEx();
					    lastTotal.setContractVersion("本次打款总额");
					    lastTotal.setFeeUrgedServiceStr("￥"+totalAmount.toString());
			            grantList.add(lastTotal);
					}
					//isBreakCode=0 时表示需要进行拆分，拆分金额为5万元
					if("0".equals(isBreakCode)){
						grantItemSplit(response, excelutil, grantList);
					}else{
						for(JYJGrantEx gr : grantList){
							gr.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank",gr.getBankName()));
							gr.setBankName(changeBankName(gr.getBankName()));
						}
						excelutil.exportExcel(grantList, FileExtension.FIRST_GRANT_NAME
								+ DateUtils.getDate("yyyyMMddHHmmss"), null, JYJGrantEx.class,
								FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,
								response, null);
					}
					logger.info("放款表导出结束：" + DateUtils.getDate("yyyyMMddHHmmss"));
					return null;
				}
			}else {
				addMessage(redirectAttributes, "导出放款表为空，没有符合条件的数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出放款表出错");
			logger.error("导出放款表出错" + DateUtils.getDate("yyyyMMddHHmmss"));
		}
		return "redirect:" + adminPath + "/channel/jyj/grantItem/?repage";
	}
	
	// 导出宝付模板
	@RequestMapping(value="offLineBFDeal")
	public String offLineBFDeal(HttpServletRequest request,HttpServletResponse response,
			LoanFlowQueryParam grtQryParam,String idVal,
			RedirectAttributes redirectAttributes){
		try {
			logger.debug("宝付导出开始：" + DateUtils.getDate("yyyyMMddHHmmss"));
			ExcelUtils excelutil = new ExcelUtils();
			List<JYJGrantBFEx> grantList = new ArrayList<JYJGrantBFEx>();
			String[] applyIds = null;
			JYJGrantBFEx jyjGrantBFEx = new JYJGrantBFEx();
			User user = UserUtils.getUser(); // 获取登录人的id
			grtQryParam.setDealUser(user.getId());
			if (StringUtils.isEmpty(idVal)) {
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> grantLists = grantService.getJyjGrantLists(grtQryParam);
				if (ArrayHelper.isNotEmpty(grantLists)) {
					applyIds = new String[grantLists.size()];
					for (int i = 0; i < grantLists.size(); i++) {
						applyIds[i] = grantLists.get(i).getApplyId();
					}
				}
			} else {
				applyIds = idVal.split(",");
			}
			if (!ObjectHelper.isEmpty(applyIds)) {
				jyjGrantBFEx.setApplyIds(applyIds);
				grantList = grantService.getBFGrantList(jyjGrantBFEx);
			}
			for(JYJGrantBFEx gr : grantList){
				gr.setBankName(changeBankName(gr.getBankName()));
				gr.setUserBankName(gr.getBankName()+gr.getBankBranch());
				gr.setGrantAmount(gr.getFirstGrantAmount());
			}
			excelutil.exportExcel(grantList, FileExtension.BF_FIRST_GRANT_NAME
					+ DateUtils.getDate("yyyyMMddHHmmss"), null, JYJGrantBFEx.class,
					FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,
					response, null);
			logger.info("宝付放款表导出结束：" + DateUtils.getDate("yyyyMMddHHmmss"));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出放款表出错");
			logger.error("宝付放款表导出结束" + DateUtils.getDate("yyyyMMddHHmmss"));
		}
		addMessage(redirectAttributes, "导出放款表出错");
		return "redirect:" + adminPath + "/channel/jyj/grantItem/?repage";
	}

	/**
	 * 放款表导出按5万元金额拆分
	 * 2017年1月20日
	 * By 朱静越
	 * @param response
	 * @param excelutil
	 * @param grantList
	 */
	private void grantItemSplit(HttpServletResponse response,
			ExcelUtils excelutil, List<JYJGrantEx> grantList) {
		List<JYJGrantEx> grantListNew = new ArrayList<JYJGrantEx>();
		for(JYJGrantEx grantEx:grantList){
			if(grantEx.getGrantAmount()!=null&&grantEx.getGrantAmount()!=""){
				String grantAmount=grantEx.getGrantAmount();
				Double grantAmountDou=Double.parseDouble(grantAmount);
				//获取拆分后的条数
				Double grantSize=grantAmountDou/50000;
				int isBreakSize=grantSize.intValue()+1;
				//将一条拆分成多条
				for(int i=0;i<isBreakSize;i++){
					JYJGrantEx grantExNew=new JYJGrantEx();
					BeanUtils.copyProperties(grantEx, grantExNew);
					grantExNew.setGrantAmount("50000.00");
					if(i==(isBreakSize-1)){
						//算出最后一条数据的值
						Double finalGrantAmount=(grantAmountDou-(50000*(isBreakSize-1)));
						BigDecimal bigDecimal = new BigDecimal(finalGrantAmount);
						//保留2位小数
						bigDecimal = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP); 
						grantExNew.setGrantAmount(bigDecimal.toString());
					}
					grantListNew.add(grantExNew);
				}
			}else{
				grantListNew.add(grantEx);
			}
		}
		for(JYJGrantEx gr : grantListNew){
			gr.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank",gr.getBankName()));
			gr.setBankName(changeBankName(gr.getBankName()));
		}
		excelutil.exportExcel(grantListNew, FileExtension.GRANT_NAME
				+ DateUtils.getDate("yyyyMMddHHmmss"), null, JYJGrantEx.class,
				FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,
				response, null);
	}

	/**
	 * 银行名称修改
	 * @param bankName
	 * @return
	 */
	public String changeBankName(String bankName){
		if(bankName != null && !"".equals(bankName)){
			if("中国光大银行".equals(bankName)){
				bankName = "光大银行";
			}else if("平安银行股份有限公司".equals(bankName)){
				bankName = "平安银行";
			}else if("中国邮政储蓄银行股份有限公司".equals(bankName)){
				bankName = "中国邮政储蓄银行";
			}
		}
		return bankName;
	}
	
	/**
     * 放款退回,退回到合同审核
     * 2016年02月24日 By 张灏
     * @param batchParam 合同编号
     * @param backBatchReasonCode 退回原因code
     * @param backBatchReason 退回原因
     * @return 操作结果
     */
    @RequestMapping(value = "batchBack")
    @ResponseBody
    public String batchBack(String batchParam, String backBatchReason,String backBatchReasonCode) {
        String[] batchParams = null;
        String failContractCode = null;
        String message = "";
        List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
        if(batchParam!=null){
            batchParams = batchParam.split(";");
            for(String str:batchParams){
            	try {
            		grantService.grantBackDeal(str, backBatchReason, backBatchReasonCode);
            	} catch (Exception e) {
            		e.printStackTrace();
            		logger.error("放款退回失败，发生异常",e);
            		failContractCode = str;
            	}
            	if(StringUtils.isNotEmpty(failContractCode)){
            		failedCodeList.add(failContractCode);
            		failNum++;
            	}else{
            		successNum++;
            	}
            }
        }
        if (ArrayHelper.isNotEmpty(failedCodeList)) {
			AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
			message = notify.getMessage();
		}else{
			message = "放款退回成功";
		}
        return message;
    }

	/**
	 * 线下放款处理，上传回执结果,同时更新催收服务费表
	 * 2016年1月6日 By 朱静越
	 * @param request
	 * @param response
	 * @param file
	 * @param returnCheckFlag 是否校验退款标识   0为不校验   1为校验
	 * @return 要进行跳转的页面
	 */
    @ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request,
			String returnCheckFlag, HttpServletResponse response,
			@RequestParam MultipartFile file,
			RedirectAttributes redirectAttributes) {
		ExcelUtils excelutil = new ExcelUtils();
		String failContractCode = null;
        String message = "";
        List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		List<JYJGrantEx> grantList = new ArrayList<JYJGrantEx>();
		List<?> datalist = excelutil
				.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
						JYJGrantEx.class);
		if (ArrayHelper.isNotEmpty(datalist)) {
			grantList = (List<JYJGrantEx>) datalist;
			// 进行退款标识的校验
			if (YESNO.YES.getCode().equals(returnCheckFlag)) {
				String checkReturn = grantService.returnStatusCheck(datalist);
				if (StringUtils.isNotEmpty(checkReturn)) {
					return "returning";
				}
			}
			// 放款批次的校验
			String pchCheckReturn = grantService.grantPchCheck(datalist);
			if (StringUtils.isNotEmpty(pchCheckReturn)) {
				return "放款批次不能为空";
			}
			// 合同编号为空校验
			for (JYJGrantEx grantExItem : grantList) {
				if (StringUtils.isEmpty(grantExItem.getContractCode()) && StringUtils.isNotEmpty(grantExItem.getBankAccount())) {
					return "导入表格的合同编号不能为空";
				}
			}
			// 放款处理
			for (JYJGrantEx grantExItem : grantList) {
				if (StringUtils.isNotEmpty(grantExItem.getContractCode()) && isGrantAudit(grantExItem.getContractCode())) {
					try {
						grantService.offLineGrantDeal(grantExItem);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("线下放款导入有误，出现异常，合同编号为："+grantExItem.getContractCode());
						failContractCode = grantExItem.getContractCode();
					}
					if(StringUtils.isNotEmpty(failContractCode)){
						failedCodeList.add(failContractCode);
						failNum++;
					}else{
						successNum++;
					}
				}
			}
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
				message = notify.getMessage();
			}else{
				message = "导入成功";
			}		
		}else{
			message = "导入的表格不能为空";
		}
		return message;
	}
	
	/**
	 * 根据合同编号判断借款状态
	 * 2016年12月20日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public boolean isGrantAudit(String contractCode){
		Map<String, String> param = new HashMap<String, String>();
		param.put("contractCode", contractCode);
		String dictLoanStatus = loanGrantService.findLoanLinkedContract(param).getDictLoanStatus();
		if (LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(dictLoanStatus)) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
     * 手动确认放款处理，通过手动输入的放款批次更新，同时将数据流转到金信待放款审核
     * 2017年3月8日
     * By 朱静越
     * @param request
     * @param loanFlowQueryParam 查询条件
     * @param grantPch 放款批次
     * @param contractCode 勾选数据的合同编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value= "updateGrant")
    private String updateGrant(HttpServletRequest request,
    		LoanFlowQueryParam loanFlowQueryParam,
    		String grantPch,
			String idVal) {
		String[] contractCodes = null;
		String resultMeassage = null;
		int intSuccess = 0;
		if (StringHelper.isNotEmpty(idVal)) {
			contractCodes = idVal.split(";");
			loanFlowQueryParam.setContractCodes(contractCodes);
		}
		User user = UserUtils.getUser(); // 获取登录人的id
		loanFlowQueryParam.setDealUser(user.getId());
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		List<LoanFlowWorkItemView> baseViewList = grantService.getJyjGrantLists(loanFlowQueryParam);
		List<String> contractCodeList = Lists.newArrayList();
		if (ObjectHelper.isNotEmpty(baseViewList)) {
			for (LoanFlowWorkItemView loanFlowWorkItemView : baseViewList) {
				if (ApplyInfoConstant.FROZEN_FLAG.equals(loanFlowWorkItemView
						.getFrozenFlag()))
					contractCodeList.add(loanFlowWorkItemView.getContractCode());
				break;
			}
			if (contractCodeList.size() != 0) {
				resultMeassage = "合同编号为" + contractCodeList.get(0) + "的客户门店已申请冻结！";
			} else {
				for (LoanFlowWorkItemView loanFlowWorkItemView : baseViewList) {
					// 废除工作流 更新放款表 借款表中的借款状态 催收服务费 历史表
					LoanGrant loanGrant = new LoanGrant();
					loanGrant.setContractCode(loanFlowWorkItemView.getContractCode());
					loanGrant.setGrantFlag(YESNO.YES.getCode()); // 标识该单子在首次待放款审核可以看到
					loanGrant.setLendingTime(new Date());
					loanGrant.setGrantBatch(grantPch);
					// 更新借款状态
					Contract contract = contractDao.findByContractCode(loanFlowWorkItemView.getContractCode());
					LoanInfo loanInfo = new LoanInfo();
					loanInfo.setApplyId(contract.getApplyId());
					loanInfo.setLoanCode(contract.getLoanCode());
					// 操作数据库相关信息 更新放款表 借款表中借款状态 历史表 催收服务费
					try {
						logger.debug("简易借首次手动确认放款处理开始，合同编号为："+ loanGrant.getContractCode());
						grantService.grantSureInfo(loanInfo, loanGrant);
						intSuccess++;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("简易借首次手动确认放款：updateGrant发生异常,合同编号为："+ loanGrant.getContractCode(), e);
					}
					resultMeassage = "手动确认放款成功" + intSuccess + "笔";
				}
			}
		}else {
			resultMeassage = "没有需要处理的单子";
		}
		return resultMeassage;
	}
}
