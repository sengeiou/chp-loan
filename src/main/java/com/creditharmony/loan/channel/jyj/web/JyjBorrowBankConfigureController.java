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
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cms.constants.YesNo;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantBFEx;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantEx;
import com.creditharmony.loan.channel.jyj.entity.JYJLastGrantEx;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.channel.jyj.service.JYJGrantAuditService;
import com.creditharmony.loan.channel.jyj.service.JYJGrantService;
import com.creditharmony.loan.channel.jyj.service.JyjBorrowBankConfigureService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.vo.DefaultServiceVO;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "${adminPath}/channel/jyj/JyjBorrowBankConfigure")
public class JyjBorrowBankConfigureController  extends BaseController{
	
	
	@Autowired
	private JyjBorrowBankConfigureService jyjBorrowBankConfigureService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private JYJGrantService grantService;
	@Autowired
	private JYJGrantAuditService jYJGrantAuditService;
	
	
	/**
	 * 增加银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public Object insert(JyjBorrowBankConfigure record,HttpServletRequest request, HttpServletResponse response, Model model){
		String bankId = record.getBankId();
		String[] bankIds = bankId.split(",");
		BigDecimal firstLoanProportion = record.getFirstLoanProportion();
		BigDecimal endLoanProportion = record.getEndLoanProportion();
		if(!ObjectHelper.isEmpty(bankIds)){
			//将所有的数据改为无效
			jyjBorrowBankConfigureService.updateAllinvalid(record);
			for(String bankCode: bankIds){
				if(!ObjectHelper.isEmpty(bankCode)){
					record.setBankCode(bankCode);
					record.setFlag(Integer.parseInt(YesNo.YES));
					jyjBorrowBankConfigureService.updateByBankCode(record);
					jyjBorrowBankConfigureService.updateProportion(firstLoanProportion, endLoanProportion);
				}
			}
		}
		return DefaultServiceVO.createSuccess("配置成功！");
	}
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryList")
	public String queryPage(JyjBorrowBankConfigure record,HttpServletRequest request, HttpServletResponse response, Model model){
		List<JyjBorrowBankConfigure> list  = jyjBorrowBankConfigureService.queryList(record);
		//放款比例，取第一个启用银行的放款比例
		for(JyjBorrowBankConfigure limit : list){
			if(limit.getFlag() == 1){
				model.addAttribute("limit", limit);
				break;
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("record", record);
		return "channel/jyj/JyjBorrowBankConfigure";
	}
	
	/**
	 * 查询金信简易借待分配卡号信息
	 * @author wjj
	 * @Create 2017年5月6日
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGCDiscardInfo")
	protected String getGCDiscardInfo(Model model, LoanFlowQueryParam grtQryParam,HttpServletRequest request, HttpServletResponse response) throws Exception{
		//将门店id置空
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		//废除工作流  从数据库中查询数据
		Page<DisCardEx> gcDiscardPage =jyjBorrowBankConfigureService.getGCDiscardList(new Page<DisCardEx>(request, response), grtQryParam);
		
		List<DisCardEx> workItems = gcDiscardPage.getList();
		//查询城市信息
		List<CityInfo> provinceList = cityManager.findProvince();
		if (StringUtils.isNotEmpty(grtQryParam.getProvinceCode())) {
			List<CityInfo> cityList = cityManager.findCity(grtQryParam.getProvinceCode());
			model.addAttribute("citiesList", cityList);
		}
		//查询产品信息
		List<LoanGrantEx> productList = loanGrantService.findProduct();
		//获取提交批次
		Map<String,String> batch = Maps.newHashMap();
		batch.put("loanFlag", ChannelFlag.JINXIN.getCode());
		batch.put("batch", "grantPch");
		batch.put("dictLoanStatus", LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
		List<String> submitBatchList = loanGrantService.findSubmitBatchList(batch);
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", workItems);
		model.addAttribute("submitBatchList", submitBatchList);
		model.addAttribute("gcDiscardPage", gcDiscardPage);
		return "channel/jyj/goldCredit_disCardList";
	}
	
	/**
	 * 分配卡号处理，获得多个单子的loanCode说
	 * ,分割，在同一个类中，
	 * 2017年5月6日
	 * By wjj
	 * @param model
	 * @param checkVal
	 * @return String
	 */
	@RequestMapping(value = "disCardJump")
	public String disCardJump(Model model,String checkVal) {
		List<DisCardEx> gcDiscardList =new ArrayList<DisCardEx>();
		// 根据loanCodes查询页面中显示放款确认的字段
		String[] checkeds = null;
		
		// 添加token 
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    model.addAttribute("deftokenId", tokenMap.get("tokenId"));
	    model.addAttribute("deftoken", tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		try {
			if (StringUtils.isNotEmpty(checkVal)) {
				if (checkVal.indexOf(",") != GrantCommon.ONE) {
					checkeds = checkVal.split(",");
				} else {
					checkeds = new String[1];
					checkeds[0] = checkVal;
				}
				for (String item : checkeds) {
					//依据loanCode 查询数据库的数据
					List<DisCardEx> gcDiscard =new ArrayList<DisCardEx>();
					LoanFlowQueryParam grtQryParam=new LoanFlowQueryParam();
					grtQryParam.setLoanCode(item);
					gcDiscard =jyjBorrowBankConfigureService.getGCDiscardList(grtQryParam);
					//将依据loanCode查询的信息都加入到gcDiscardList  
					if(!gcDiscard.isEmpty()&&gcDiscard.size()>0){
						for(DisCardEx discard:gcDiscard){
							gcDiscardList.add(discard);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		model.addAttribute("list", gcDiscardList);
		model.addAttribute("flag","JX_disCard");
		return "channel/jyj/goldcredit_disCard_approve_0";
	}
	
	/**
	 * 金信简易借尾款放款列表信息
	 * @author wjj
	 * @Create 2017年5月6日
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
		Page<LoanFlowWorkItemView> gcGrantList = jyjBorrowBankConfigureService.getGCGrantList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		List<LoanFlowWorkItemView> workItems = gcGrantList.getList();
		//转换开户行
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (LoanFlowWorkItemView tagItem : workItems) {
		    tagItem.setCautionerDepositBank(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK, tagItem.getCautionerDepositBank()));
		    tagItem.setUrgentFlag(DictCache.getInstance().getDictLabel("jk_urgent_flag",tagItem.getUrgentFlag()));
		    tagItem.setTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing",tagItem.getTelesalesFlag()));
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
		return "/channel/jyj/goldCredit_grantList";
	}
	
	/**
     * 尾款放款导出Excel,尾款放款结果为放款失败的数据不允许导出
     * 2016年3月6日
     * xiaoniu.hu
     * @param request
     * @param queryParam 页面检索条件
     * @param response 
     * @param loanCodes 画面选中的借款
     */
    @RequestMapping(value = "exportExcel")
	public String exportExcel(HttpServletRequest request,LoanFlowQueryParam grtQryParam,HttpServletResponse response,
			String idVal,RedirectAttributes redirectAttributes) {
		try {
			logger.debug("放款表导出开始：" + DateUtils.getDate("yyyyMMddHHmmss"));
			ExcelUtils excelutil = new ExcelUtils();
			List<JYJGrantEx> grantList = new ArrayList<JYJGrantEx>();
			List<JYJLastGrantEx> lastGrantExs = new ArrayList<JYJLastGrantEx>();
			BigDecimal totalAmount = new BigDecimal("0.00");
			String[] ids = null;
			User user = UserUtils.getUser(); // 获取登录人的id
			grtQryParam.setDealUser(user.getId());
			if (StringUtils.isEmpty(idVal)) {
				GrantUtil.setStoreOrgIdQuery(grtQryParam);
				List<LoanFlowWorkItemView> grantLists = jyjBorrowBankConfigureService.getGCGrantList(grtQryParam);
				if (ArrayHelper.isNotEmpty(grantLists)) {
				ids = new String[grantLists.size()];
					for (int i = 0; i < grantLists.size(); i++) {
						ids[i] = grantLists.get(i).getApplyId();
					}
				}
			} else {
				ids = idVal.split(",");
			}
			if (!ObjectHelper.isEmpty(ids)) {
				for (int i = 0; i < ids.length; i++) {
					totalAmount = grantService.grantItemDao(grantList, totalAmount, ids[i]);
				}
				for(JYJGrantEx gr : grantList){
					JYJLastGrantEx jyjLastGrantEx = new JYJLastGrantEx();
					gr.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank",gr.getBankName()));
					gr.setBankName(changeBankName(gr.getBankName()));
					BeanUtils.copyProperties(gr, jyjLastGrantEx);
					lastGrantExs.add(jyjLastGrantEx);
				}
				if(!ObjectHelper.isEmpty(lastGrantExs) && lastGrantExs.size()>0){
					JYJLastGrantEx lastTotal = new JYJLastGrantEx();
				    lastTotal.setContractVersion("本次打款总额");
				    lastTotal.setFeeUrgedServiceStr("￥"+totalAmount.toString());
				    lastGrantExs.add(lastTotal);
				}
			}
			excelutil.exportExcel(lastGrantExs, FileExtension.LAST_GRANT_NAME
					+ DateUtils.getDate("yyyyMMddHHmmss"), null, JYJLastGrantEx.class,
					FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,
					response, null);
			logger.info("放款表导出结束：" + DateUtils.getDate("yyyyMMddHHmmss"));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出放款表出错");
			logger.error("导出放款表出错" + DateUtils.getDate("yyyyMMddHHmmss"));
		}
		addMessage(redirectAttributes, "导出放款表出错");
		return "redirect:" + adminPath + "/channel/jyj/JyjBorrowBankConfigure/getGCGrantInfo";
	}
    
    // 导出宝付模板
 	@RequestMapping(value="exportExcelBF")
 	public String exportExcelBF(HttpServletRequest request,HttpServletResponse response,
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
 				List<LoanFlowWorkItemView> grantLists = jyjBorrowBankConfigureService.getGCGrantList(grtQryParam);
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
 				gr.setGrantAmount(gr.getLastGrantAmount());
 			}
 			excelutil.exportExcel(grantList, FileExtension.BF_LAST_GRANT_NAME
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
 		return "redirect:" + adminPath + "/channel/jyj/JyjBorrowBankConfigure/getGCGrantInfo";
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
     * 简易借尾次放款列表的回执上传
     * @param request
     * @param response
     * @param file 文件流
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file,RedirectAttributes redirectAttributes) {
    	String url = "redirect:" + adminPath + "/channel/jyj/JyjBorrowBankConfigure/getGCGrantInfo";
    	ExcelUtils excelutil = new ExcelUtils();
		List<JYJLastGrantEx> datalist = (List<JYJLastGrantEx>) excelutil
				.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
						JYJLastGrantEx.class);
		List<String> applyIdList = new ArrayList<String>();
		List<String> contractCodeList = Lists.newArrayList();
		List<String> grantFailList = Lists.newArrayList();
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
		LoanFlowQueryParam grtQryParam=new LoanFlowQueryParam();
		grtQryParam.setApplyIds(applyId);
		List<LoanFlowWorkItemView> baseViewList =jyjBorrowBankConfigureService.getGCGrantList(grtQryParam);
		for (LoanFlowWorkItemView loanFlowWorkItemView : baseViewList) {
			if (ApplyInfoConstant.FROZEN_FLAG.equals(loanFlowWorkItemView.getFrozenFlag()))
				contractCodeList.add(loanFlowWorkItemView.getContractCode());
			if (LoansendResult.LOAN_SENDED_FAILED.getCode().equals(
					loanFlowWorkItemView.getReceiptResult())) {
				grantFailList.add(loanFlowWorkItemView.getContractCode());
			}
			break;
		}
		// 上传回执的时候做判断数据是不是有冻结的状态
		if (contractCodeList.size() != 0) {
			addMessage(redirectAttributes,"合同编号为"+contractCodeList.get(0)+"的客户门店已申请冻结！");
		}else if (grantFailList.size() != 0) {
			addMessage(redirectAttributes,"合同编号为" + grantFailList.get(0) + "的客户已放款失败！"); 
		}else {
			// 循环，得到列表中的单子，修改单子中的回执结果为成功，同时将单子的状态更新待放款审核，同时更新流程中的数据
			if (ArrayHelper.isNotEmpty(datalist)) {
				for (int i = 0; i < datalist.size() - 1; i++) {
					//废除工作流 更新放款表 借款表中的借款状态  催收服务费  历史表
					LoanGrant loanGrant=new LoanGrant();
					loanGrant.setContractCode(datalist.get(i).getContractCode());
					loanGrant.setCheckResult(VerityStatus.PASS.getCode());
					loanGrant.setCheckTime(new Date());
					loanGrant.setCheckEmpId(UserUtils.getUser().getId());
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
					loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT
							.getCode());
					//操作数据库相关信息 更新放款表  借款表中借款状态  历史表   催收服务费
					jyjBorrowBankConfigureService.grantSureInfo(loanInfo, loanGrant);
				}
				addMessage(redirectAttributes,"回执结果上传成功！");
			}else {
				addMessage(redirectAttributes,"上传的放款数据中的合同编号在数据库中不存在");
			}
		}
		return url;
	}
    
    /**
     * 尾次放款手动确认放款处理，对于手动确认放款失败的单子，不允许进行手动确认放款
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
			String contractCode) {
		String[] contractCodes = null;
		String resultMeassage = "";
		int intSuccess = 0;
		if (StringHelper.isNotEmpty(contractCode)) {
			contractCodes = contractCode.split(";");
			loanFlowQueryParam.setContractCode(null);
			loanFlowQueryParam.setContractCodes(contractCodes);
		}
		User user = UserUtils.getUser(); // 获取登录人的id
		loanFlowQueryParam.setDealUser(user.getId());
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		List<LoanFlowWorkItemView> baseViewList = jyjBorrowBankConfigureService.getGCGrantList(loanFlowQueryParam);
		List<String> contractCodeList = Lists.newArrayList();
		List<String> grantFailList = Lists.newArrayList();
		if (ObjectHelper.isNotEmpty(baseViewList)) {
			for (LoanFlowWorkItemView loanFlowWorkItemView : baseViewList) {
				if (ApplyInfoConstant.FROZEN_FLAG.equals(loanFlowWorkItemView
						.getFrozenFlag()))
					contractCodeList.add(loanFlowWorkItemView.getContractCode());
				if (LoansendResult.LOAN_SENDED_FAILED.getCode().equals(
						loanFlowWorkItemView.getReceiptResult())) {
					grantFailList.add(loanFlowWorkItemView.getContractCode());
				}
				break;
			}
			if (contractCodeList.size() != 0) {
				resultMeassage = "合同编号为" + contractCodeList.get(0) + "的客户门店已申请冻结！";
			}else if (grantFailList.size() != 0) {
				resultMeassage += "合同编号为" + grantFailList.get(0) + "的客户已放款失败！";
			} else {
				for (LoanFlowWorkItemView loanFlowWorkItemView : baseViewList) {
					// 废除工作流 更新放款表 借款表中的借款状态 催收服务费 历史表
					LoanGrant loanGrant = new LoanGrant();
					loanGrant.setContractCode(loanFlowWorkItemView.getContractCode());
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					loanGrant.setCheckResult(VerityStatus.PASS.getCode());
					loanGrant.setCheckEmpId(UserUtils.getUser().getId());
					loanGrant.setCheckTime(new Date());
					loanGrant.setLendingTime(new Date());
					// 更新借款状态
					Contract contract = contractDao.findByContractCode(loanFlowWorkItemView.getContractCode());
					LoanInfo loanInfo = new LoanInfo();
					loanInfo.setApplyId(contract.getApplyId());
					loanInfo.setLoanCode(contract.getLoanCode());
					loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT.getCode());
					// 操作数据库相关信息 更新放款表 借款表中借款状态 历史表 催收服务费
					try {
						logger.debug("金信手动确认放款处理开始，合同编号为："+ loanGrant.getContractCode());
						jyjBorrowBankConfigureService.grantSureInfo(loanInfo, loanGrant);
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
    
    /**
     * 尾次放款 手动确认放款失败
     * 2017年5月16日
     * By 朱静越
     * @param contractCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sureGrantFail")
    public String sureGrantFail(String contractCode){
    	String returnMes = "";
    	if (StringHelper.isNotEmpty(contractCode)) {
    		LoanGrant loanGrant = new LoanGrant();
    		loanGrant.setContractCode(contractCode);
    		loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED
    				.getCode());
			loanGrantService.updateLoanGrant(loanGrant);
			returnMes = "手动确认成功";
		}else {
			returnMes = "合同编号为空，手动确认放款失败";
		}
    	return returnMes;
    }
    
    /**
	 * 尾款放款退回到合同审核
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
					logger.info("退回处理开始,借款编号：" + singleParam[0] + "APPLY_ID:" + singleParam[4]);
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
						loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
						loanGrant.setCheckResult(VerityStatus.RETURN.getCode());
						loanGrant.setGrantFlag(YESNO.NO.getCode());
						//待放款退回到合同审核
						jyjBorrowBankConfigureService.sendBackGrantInfo(loanInfo, loanGrant,backBatchReason);
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
    
}
