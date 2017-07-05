package com.creditharmony.loan.channel.goldcredit.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCGrantHelper;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantImportView;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 金信待放款列表
 * @Class Name GoldCreditController
 * @author 路志友
 * @Create In 2016年2月23日
 */

@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/grant/")
public class GCGrantController extends BaseController {
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	
	/**
	 * 金信待放款列表信息，门店申请冻结的数据标红置顶
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGCGrantInfo")
	protected String getGCGrantInfo(Model model, LoanFlowQueryParam grtQryParam,HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = UserUtils.getUser(); // 获取登录人的id
		grtQryParam.setDealUser(user.getId());
		//为了查询，将门店id置空
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
        //废除工作流  从数据库中读取数据
		Page<LoanFlowWorkItemView> gcGrantList = loanGrantService.getGCGrantList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		List<LoanFlowWorkItemView> workItems = gcGrantList.getList();
		//转换开户行
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (LoanFlowWorkItemView tagItem : workItems) {
		    tagItem.setCautionerDepositBank(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK, tagItem.getCautionerDepositBank()));
		}
		//查询城市信息
		List<CityInfo> provinceList = cityManager.findProvince();
		if (StringUtils.isNotEmpty(grtQryParam.getProvinceCode())) {
			List<CityInfo> cityList = cityManager.findCity(grtQryParam.getProvinceCode());
			model.addAttribute("cityList", cityList);
		}
		//查询产品信息
		List<LoanGrantEx> productList = loanGrantService.findProduct();
		//提交批次
		Map<String,String> batch = Maps.newHashMap();
		batch.put("loanFlag", ChannelFlag.JINXIN.getCode());
		batch.put("batch", "grantPch");
		batch.put("dictLoanStatus", LoanApplyStatus.LOAN_TO_SEND.getCode());
		List<String> submitBatchList = loanGrantService.findSubmitBatchList(batch);
		
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", workItems);
		model.addAttribute("submitBatchList", submitBatchList);
		model.addAttribute("gcGrantListPage", gcGrantList);
		return "/channel/goldcredit/goldCredit_grantList";
	}
	
	/**
	 * 退回到合同审核
	 * 2016年3月6日
	 * xiaoniu.hu
	 * @param batchParam 退回数据行
	 * @param backBatchReason 退回原因
	 * @return
	 */
    @RequestMapping(value = "batchBack")
    @ResponseBody
	public Map<String,String> batchBack(String batchParam, String backBatchReason,
			String grantSureBackReasonCode,Model model) {
    	String res = BooleanType.TRUE;// 回退成功，无任何异常
    	Map<String,String> result = new HashMap<String, String>();
		String[] batchParams = null;
		String[] singleParam = null;
		String failCancelContract = StringUtils.EMPTY;
		String failReturnContract = StringUtils.EMPTY;
		
		if (StringUtils.isNotEmpty(batchParam)) {
			batchParams = batchParam.split(";");
		}
		if (batchParams != null) {
			for (String item : batchParams) {
				singleParam = item.split(",");
				try {
					// 回退合同审核，已经签署合同作废
					boolean isCancel = grantCAService
							.caSignCancel(singleParam[2]);
					if (!isCancel) {
						res = BooleanType.FALSE;
						failCancelContract += "," + singleParam[2];
						continue;
					}
				} catch (Exception cancelEx) {
					logger.info("合同作废异常，合同编号：" + singleParam[2]);
					cancelEx.printStackTrace();
					failCancelContract += "," + singleParam[2];
					continue;
				}
				
				try {
					logger.info("退回处理开始,借款编号：" + singleParam[0]);
					if (singleParam != null) {
						//废除工作流  退回到合同审核 修改借款状态  金信处理状态  添加历史
						LoanInfo loanInfo=new LoanInfo();
						loanInfo.setLoanCode(singleParam[0]);
						loanInfo.setApplyId(singleParam[4]);
						loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
						//待放款退回到金信退回  修改orderField 用于排序
						LoanInfo loanInfoOrder=new LoanInfo();
						loanInfoOrder.setApplyId(singleParam[4]);
						loanInfoOrder.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
						String orderField = createOrderFileldService.backJxBackByGrant(loanInfoOrder);
						loanInfo.setOrderField(orderField);
						//查询合同编号
						Contract contract = contractDao.findByLoanCode(singleParam[0]);
						LoanGrant loanGrant=new LoanGrant();
						loanGrant.setContractCode(contract.getContractCode());
						// 退回的场合，重置金信状态
						loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
						//待放款退回到合同审核
						loanGrantService.sendBackGrantInfo(loanInfo, loanGrant,backBatchReason);
					}
				} catch (Exception e) {
					res = BooleanType.FALSE;
					logger.error("金信退回到合同审核方法：batchBack发生异常:"+e.toString());
					failReturnContract +=singleParam[2];
				}
			}
		}
		result.put("res", res);
		
		if (res.equals(BooleanType.FALSE)) {
			if(StringUtils.isNotEmpty(failCancelContract))
			{
				result.put("failCancelContract", failCancelContract.replaceFirst(",", ""));
			}
			if(StringUtils.isNotEmpty(failReturnContract))
			{
				result.put("failReturnContract", failReturnContract.replaceFirst(",", ""));
			}
		}
		return result;
	}
    
    /**
     * 导出Excel
     * 2016年3月6日
     * xiaoniu.hu
     * @param request
     * @param queryParam 页面检索条件
     * @param response 
     * @param loanCodes 画面选中的借款
     */
    @RequestMapping(value = "exportExcel")
	public String exportExcel(HttpServletRequest request,LoanFlowQueryParam queryParam,HttpServletResponse response,
			String loanCodes,RedirectAttributes redirectAttributes) {
    	User user = UserUtils.getUser(); // 获取登录人的id
    	queryParam.setDealUser(user.getId());
    	GrantUtil.setStoreOrgIdQuery(queryParam);
		try {
			Map<String, Object> conditions = Maps.newHashMap();
			List<String> loanCodeList = null;
			if (StringUtils.isEmpty(loanCodes)) {
				loanCodeList=new ArrayList<String>();
				List<LoanFlowWorkItemView> workItems = loanGrantService.getGCGrantList(queryParam);
				for(LoanFlowWorkItemView item : workItems) {
					loanCodeList.add(item.getLoanCode());
				}
			} else {
				loanCodeList = Arrays.asList(loanCodes.split(","));
			}
			conditions.put("list", loanCodeList);
			//TODO 根据借款编号查询，是否存在门店申请冻结的数据，门店申请的数据不允许进行导出
			int frozenInt = loanGrantService.getFrozenByLoanCodes(loanCodeList);
			if (frozenInt <= 0) {
				ExportGCGrantHelper.exportData(conditions, response); 
				return null;
			}else {
				addMessage(redirectAttributes,"门店申请冻结的数据不允许导出");
			}
		} catch (Exception e) {
			addMessage(redirectAttributes,"导出文件异常!");
			e.printStackTrace();
			logger.error("金信放款导出excel发生异常，异常信息为："+e.getMessage());
		}
		return "redirect:" + adminPath + "/channel/goldcredit/grant/getGCGrantInfo";
	}
    /**
     * 待放款列表的回执上传，更新工作流和更新数据库中的放款批次和放款时间
     * @param request
     * @param response
     * @param file 文件流
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file,RedirectAttributes redirectAttributes) {
    	String url = "redirect:" + adminPath + "/channel/goldcredit/grant/getGCGrantInfo";
    	ExcelUtils excelutil = new ExcelUtils();
    	
		List<GCGrantImportView> datalist = (List<GCGrantImportView>)
				excelutil.importExcel(LoanFileUtils.multipartFile2File(file), 1, 0,
				GCGrantImportView.class,1);
		List<String> applyIdList = new ArrayList<String>();
		for (int i = 0; i < datalist.size()-1; i++) {
			if (StringUtils.isEmpty(datalist.get(i).getGrantBatchCode().trim())) {
				addMessage(redirectAttributes,"放款批次不能够为空,上传回执结果失败!");
				return url;
			}
			String selApplyId = grantDoneService.selApplyId(datalist.get(i).getContractCode());
			applyIdList.add(selApplyId);
		}
		if(!ArrayHelper.isNotEmpty(applyIdList))
		{
			addMessage(redirectAttributes,"上传回执结果失败!");
			return url;
		}
		String[] applyId = applyIdList.toArray(new String[applyIdList
				.size()]);
		//废除工作流  从数据库中读取值
		LoanFlowQueryParam grtQryParam=new LoanFlowQueryParam();
		grtQryParam.setApplyIds(applyId);
		List<LoanFlowWorkItemView> baseViewList =loanGrantService.getGCGrantList(grtQryParam);
		List<String> contractCodeList = Lists.newArrayList();
		for (LoanFlowWorkItemView loanFlowWorkItemView : baseViewList) {
			if (ApplyInfoConstant.FROZEN_FLAG.equals(loanFlowWorkItemView.getFrozenFlag()))
				contractCodeList.add(loanFlowWorkItemView.getContractCode());
			break;
		}
		// 上传回执的时候做判断数据是不是有冻结的状态
		if (contractCodeList.size() != 0) {
			addMessage(redirectAttributes,"合同编号为"+contractCodeList.get(0)+"的客户门店已申请冻结！");
		} else {
			// 循环，得到列表中的单子，修改单子中的回执结果为成功，同时将单子的状态更新待放款审核，同时更新流程中的数据
			if (ArrayHelper.isNotEmpty(datalist)) {
				for (int i = 0; i < datalist.size() - 1; i++) {
					//废除工作流 更新放款表 借款表中的借款状态  催收服务费  历史表
					LoanGrant loanGrant=new LoanGrant();
					loanGrant.setContractCode(datalist.get(i).getContractCode());
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					loanGrant.setLendingTime(new Date());
					String grantBatch = datalist.get(i).getGrantBatchCode();
					if (StringUtils.isEmpty(grantBatch))
						grantBatch = "";
					loanGrant.setGrantBatch(grantBatch);
					
					// 更新借款状态
					Contract contract = contractDao.findByContractCode(datalist.get(i).getContractCode());
					LoanInfo loanInfo = new LoanInfo();
					loanInfo.setApplyId(contract.getApplyId());
					loanInfo.setLoanCode(contract.getLoanCode());
					loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY
							.getCode());
					//操作数据库相关信息 更新放款表  借款表中借款状态  历史表   催收服务费
					loanGrantService.grantSureInfo(loanInfo, loanGrant);
				}
			}
		}
		addMessage(redirectAttributes,"回执结果上传成功！");
		return url;
	}
    
    /**
     * 金信手动确认放款处理，通过手动输入的放款批次更新，同时将数据流转到金信待放款审核
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
			String contractCodeItem) {
		String[] contractCodes = null;
		String resultMeassage = null;
		int intSuccess = 0;
		if (StringHelper.isNotEmpty(contractCodeItem)) {
			contractCodes = contractCodeItem.split(";");
			loanFlowQueryParam.setContractCodes(contractCodes);
		}
		User user = UserUtils.getUser(); // 获取登录人的id
		loanFlowQueryParam.setDealUser(user.getId());
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		List<LoanFlowWorkItemView> baseViewList = loanGrantService.getGCGrantList(loanFlowQueryParam);
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
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					loanGrant.setLendingTime(new Date());
					loanGrant.setGrantBatch(grantPch);
					// 更新借款状态
					Contract contract = contractDao.findByContractCode(loanFlowWorkItemView.getContractCode());
					LoanInfo loanInfo = new LoanInfo();
					loanInfo.setApplyId(contract.getApplyId());
					loanInfo.setLoanCode(contract.getLoanCode());
					loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
					// 操作数据库相关信息 更新放款表 借款表中借款状态 历史表 催收服务费
					try {
						logger.debug("金信手动确认放款处理开始，合同编号为："+ loanGrant.getContractCode());
						loanGrantService.grantSureInfo(loanInfo, loanGrant);
						intSuccess++;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("金信手动确认放款：updateGrant发生异常,合同编号为："+ loanGrant.getContractCode(), e);
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
