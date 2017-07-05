package com.creditharmony.loan.borrow.grant.web;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceHylInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceTlInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceZJInputEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.ExportGrantDeductBefHelper;
import com.creditharmony.loan.borrow.grant.util.GrantDeductsFYExportUtil;
import com.creditharmony.loan.borrow.grant.util.GrantDeductsHYLExportUtil;
import com.creditharmony.loan.borrow.grant.util.GrantDeductsTLExportUtil;
import com.creditharmony.loan.borrow.grant.util.GrantDeductsZJExportUtil;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.common.service.SystemSetMaterService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.FilterHelper;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.google.common.collect.Lists;

/**
 * 放款当日待划扣列表处理
 * @Class Name GrantDeductsController
 * @author 朱静越
 * @Create In 2016年1月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/grantDeducts")
public class GrantDeductsController extends BaseController {
	
	@Autowired
	private GrantDeductsService grantDeductsService;
	@Autowired
	private PaymentSplitService paymentSplitService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private LoanGrantService loanGrantService;
    @Autowired
    private SystemSetMaterService systemSetMaterService;  
    @Autowired 
	private PaybackBlueAmountService blusAmountService;
    @Autowired 
    private ThreePartFileName threePartFileName;
    
	private String BEFORE = "bef";
	@Autowired
	private UrgeServicesMoneyDao urgeServicesMoneyDao;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	/*//导出通联EXCEL初始编号
	private static int tlFileNameNo = 1;*/

	/**
	 * 放款当日待划扣列表，同时计算出所有的划扣金额，和总笔数 
	 * 2016年1月6日 By 朱静越
	 * @param model
	 * @param urgeMoneyEx 催收实体
	 * @param result 根据result区分是当日待划扣或以往待划扣
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "deductsInfo")
	public String deductsInfo(Model model, UrgeServicesMoneyEx urgeMoneyEx,
			String result, HttpServletRequest request,
			HttpServletResponse response, String returnUrl) {
		// 放款当日待划扣列表，从催收主表中查询，检索条件为划扣回盘结果为0,1,3；默认backResult为空时查询0，1，3;
		// 暂时不确定待划扣是否需要查询,默认情况下回盘结果为空
		if (result.equals(BEFORE)) {
			// 如果为以往待划扣,设置时间标识为是
			urgeMoneyEx.setTimeFlag(YESNO.YES.getCode());
		} else {
			urgeMoneyEx.setTimeFlag(YESNO.NO.getCode());
			SystemSetting param = new SystemSetting();
			param.setSysFlag(GrantCommon.SYS_AUTO_DEDUCTS);
			SystemSetting autoDeducts = systemSetMaterService.get(param);
			model.addAttribute("autoDeducts", autoDeducts);
			//  查找放款批次
			List<LoanGrant> grantBatchList = loanGrantService.selGrantPch();
			model.addAttribute("grantBatchList", grantBatchList);
		}
		
		GrantUtil.setStoreId(urgeMoneyEx);
		if(urgeMoneyEx.getBankName()!=null && !"".equals(urgeMoneyEx.getBankName())){
			String[] nameArray = urgeMoneyEx.getBankName().split(",");
			urgeMoneyEx.setBankNameArray(nameArray);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<nameArray.length;i++){
				String bankNameLabel=DictCache.getInstance().getDictLabel("jk_open_bank",nameArray[i]);
				sb.append(bankNameLabel+",");
			}
			
			urgeMoneyEx.setBankNameLabel(sb.substring(0,sb.length()-1));
		}
		Page<UrgeServicesMoneyEx> urgePage = grantDeductsService
				.selectUrgeList(
						new Page<UrgeServicesMoneyEx>(request, response),
						urgeMoneyEx);
		BigDecimal totalDeducts = new BigDecimal("0.00");
		long size = 0;
		if(ArrayHelper.isNotEmpty(urgePage.getList())){
			List<UrgeServicesMoneyEx> urgeList = urgePage.getList();
			totalDeducts = urgeList.get(0).getSumDeductAmount();
			size = urgePage.getCount();
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			for (UrgeServicesMoneyEx ex : urgeList) {
				ex.setDictDealType(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, ex.getDictDealType()));
				ex.setBankName(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, ex.getBankName()));
				ex.setSplitBackResult(DictUtils.getLabel(dictMap,LoanDictType.URGE_COUNTEROFFER_RESULT, ex.getSplitBackResult()));
				ex.setCustomerTelesalesFlag(DictUtils.getLabel(dictMap,LoanDictType.TELEMARKETING, ex.getCustomerTelesalesFlag()));
				ex.setLoanFlag(DictUtils.getLabel(dictMap,LoanDictType.CHANNEL_FLAG, ex.getLoanFlag()));
				// 签约平台使用的就是划扣平台的字典
				ex.setBankSigningPlatform(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, ex.getBankSigningPlatform()));
			}
		}
		// 汇金产品获取
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		List<CityInfo> provinceList = cityManager.findProvince();
		model.addAttribute("totalNum", size);
		model.addAttribute("deductsAmount",totalDeducts.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		model.addAttribute("urgeMoneyEx", urgeMoneyEx);
		model.addAttribute("urgeList", urgePage);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		return "borrow/grant/" + returnUrl;
	}

	/**
	 * 根据查询条件查询要进行处理的list 
	 * 2016年2月23日 By 朱静越
	 * @param urgeServicesMoneyEx 查询条件
	 * @param result 判断为哪个列表
	 * @return 符合条件的list
	 */
	public List<UrgeServicesMoneyEx> queryDealList(
			UrgeServicesMoneyEx urgeServicesMoneyEx, String result) {
		List<UrgeServicesMoneyEx> urgeList = null;
		if (result.equals(BEFORE)) {
			// 如果为以往待划扣,设置时间标识为是
			urgeServicesMoneyEx.setTimeFlag(YESNO.YES.getCode());
		} else {
			urgeServicesMoneyEx.setTimeFlag(YESNO.NO.getCode());
		}
		urgeList = grantDeductsService.selectUrgeListNo(urgeServicesMoneyEx);
		return urgeList;
	}
	
	/**
	 * 开启或者停止自动划扣，单子的自动划扣规则更新
	 * 如果是开启自动划扣，则将选中的单子进行发送到批处理
	 * 2016年5月9日
	 * By 朱静越
	 * @param cid
	 * @param autoFlag 0:停止自动划扣，1：开启自动划扣
	 * @param urgeMoneyEx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAutoFlag")
	public String updateAutoFlag(String cid,String autoFlag,UrgeServicesMoneyEx urgeMoneyEx){
		UrgeServicesMoney urge = new UrgeServicesMoney();
		String urgeId = null;
		String resultString = "success";
		try {
			if (StringUtils.isNotEmpty(cid)) {
				urgeId = FilterHelper.appendIdFilter(cid);
			}else{
				// 根据查询条件，获得id
				StringBuilder parameter = new StringBuilder();
				GrantUtil.setStoreId(urgeMoneyEx);
				urgeMoneyEx.setTimeFlag(YESNO.NO.getCode());
				List<UrgeServicesMoneyEx> urgeList = grantDeductsService.selectUrgeListNo(urgeMoneyEx);
				if (ArrayHelper.isNotEmpty(urgeList)) {
					for (int i = 0; i < urgeList.size(); i++) {
						if (i != 0) {
							parameter.append(",'" + urgeList.get(i).getUrgeId() + "'");
						}
						else{
							parameter.append("'" + urgeList.get(i).getUrgeId() + "'");
						}
					}
					urgeId = parameter.toString();
				}
			}
			urge.setId(urgeId);
			urge.setAutoDeductFlag(autoFlag);
			// 更新
			grantDeductsService.updateUrge(urge);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("自动划扣操作失败",e);
			resultString = e.getMessage();
		}
		return resultString;
	}
	
	/**
	 * 发送划扣,默认为回盘结果为失败和处理中（线下）的单子,如果有选中，将选中的单子进行判断为失败或者处理中（线下）之后传送到后台
	 * 同时如果单子和点击发送的单子平台不一致，需要进行平台转换，所以要进行催收服务费平台的更新
	 * 如果发送划扣为处理中（线下）的单子，点击划扣之后，对拆分表中的该单子进行删除
	 * 2015年12月30日 By 朱静越
	 * @param type 类型
	 * @param cid  要进行划扣的单子的催收主表id
	 * @return 要进行跳转的页面
	 */
	@ResponseBody
	@RequestMapping(value = "sendDeduct")
	public String sendDeduct(String type, String cid, String rule,
			UrgeServicesMoneyEx urgeServicesMoneyEx, String result) {
		String[] ids = null;
		String idString = null;
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		String message = "";
		String returnMes = "";
		RepaymentProcess dictStep = null;
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		UrgeServicesMoneyEx urgeServices = new UrgeServicesMoneyEx();
		StringBuilder parameter = new StringBuilder();
		// 同时获得要插入历史的平台
		if (DeductPlat.FUYOU.getCode().equals(type)) {
			dictStep = RepaymentProcess.SEND_FUYOU;
		} else if (DeductPlat.HAOYILIAN.getCode().equals(type)) {
			dictStep = RepaymentProcess.SEND_HYL;
		} else if(DeductPlat.TONGLIAN.getCode().equals(type)){
			dictStep = RepaymentProcess.SEND_TONGLIAN;
		}else if(DeductPlat.ZHONGJIN.getCode().equals(type)){
			dictStep = RepaymentProcess.SEND_ZHONGJIN;
		}
		// 对选中的单子进行发送划扣，
		if (StringUtils.isNotEmpty(cid)) {
			ids = cid.split(";");
		} else {
			GrantUtil.setStoreId(urgeServicesMoneyEx);
			List<UrgeServicesMoneyEx> urgeList = queryDealList(urgeServicesMoneyEx, result);
			// 默认将查询条件下的所有为失败的进行发送划扣
			if (ArrayHelper.isNotEmpty(urgeList)) {
				ids = new String[urgeList.size()];
				for (int i = 0; i < urgeList.size(); i++) {
					ids[i] = urgeList.get(i).getUrgeId();
				}
			}
		}
		if (ArrayHelper.isNotNull(ids)) {
			for (int i = 0; i < ids.length; i++) {
				String status = grantDeductsService.find(ids[i]).getDictDealStatus();
				if (judgeDeal(status)) {
					parameter.append("'" + ids[i] + "',");
				}
			}
			if (StringUtils.isNotEmpty(parameter)) {
				idString = parameter.toString();
				idString = idString.substring(0, parameter.lastIndexOf(","));
				// 通过催收主表id获得要传送到批处理的list,在SQL中控制要传送给批处理的单子只能为处理中（线下）或者失败的单子
				urgeServices.setUrgeId(idString);
				urgeServices.setRule(rule);
				deductReqList = grantDeductsService.selectSendList(urgeServices);
				if (ArrayHelper.isNotEmpty(deductReqList)) {
					for(int i=0; i<deductReqList.size(); i++){
						try {
							returnMes = grantDeductsService.sendDeductDeal(deductReqList.get(i), dictStep, type);
							if (StringUtils.isNotEmpty(returnMes)) {
								failedContractCode = deductReqList.get(i).getBusinessId();
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("催收服务费发送划扣失败",e);
							failedContractCode = deductReqList.get(i).getBusinessId();
						}
						if(StringUtils.isNotEmpty(failedContractCode)){
							failedCodeList.add(failedContractCode);
							failNum++;
						}else{
							successNum++;
						}
					}
					//  处理
					if (ArrayHelper.isNotEmpty(failedCodeList)) {
						AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
						message = notify.getMessage();
					}else{
						message = "发送成功" + successNum + "条单子";
					}
				} else {
					message = "没有要发送的数据";
				}
			}else {
				message = "选择符合条件的单子进行发送划扣";
			}
		} 
		return message;
	}
	
	/**
	 * 放款以往待划扣导出excel
	 * 2016年10月25日
	 * By 朱静越
	 * @param cid 催收id
	 * @param urgeServicesMoneyEx 查询条件
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "daoExcelBef")
	public String daoExcelBef(String cid,UrgeServicesMoneyEx urgeServicesMoneyEx,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes){
		boolean success = true;
		try {
			GrantUtil.setStoreId(urgeServicesMoneyEx);
			// 如果有进行选择，获得选中单子的list
			if (StringUtils.isNotEmpty(cid)) {
				String[] urgeIds = cid.split(",");
				urgeServicesMoneyEx.setUrgeIds(urgeIds);
			}
			urgeServicesMoneyEx.setTimeFlag(YESNO.YES.getCode());
			String[] header = {"合同编号","门店名称","客户姓名","账户名字","借款产品","合同金额","放款金额","催收服务费","未划金额","划扣金额","划扣平台","批借期限","开户行","放款日期","最新划扣日期","划扣回盘结果","失败原因","是否电销","渠道","征信费","信访费","费用总计"};
			ExportGrantDeductBefHelper.exportData(urgeServicesMoneyEx, header, response);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			logger.error("放款以往待划扣导出发生异常", e);
			addMessage(redirectAttributes, "放款以往待划扣导出发生异常");
		} finally{
			if(success){
				return null;
			}else{
				return "redirect:"
						+ adminPath
						+ "/borrow/grant/grantDeducts/deductsInfo?returnUrl=grantDedcutsBefList&result=bef";
			}
		}
	}

	/**
	 * 富友平台导出,将选中的或者默认为全部的进行拆分导出，
	 * 同时更新催收主表中的划扣平台并进行拆分表的插入，企业流水账号 导出的单子为失败的单子
	 * 线下导出，需要进行拆分，需要进行更换平台。 
	 * 2016年1月6日 By 朱静越
	 * @param request
	 * @param response
	 * @param cid 选中过的单子的id，为催收主表id
	 */
	@RequestMapping(value = "deductsFyExl")
	public String deductsFyExl(HttpServletRequest request,
			HttpServletResponse response, String cid,RedirectAttributes redirectAttributes,
			UrgeServicesMoneyEx urgeServicesMoneyEx, String resultString,String returnUrl) {
		StringBuilder parameter = new StringBuilder();
		UrgeServicesMoneyEx urgeEx = new UrgeServicesMoneyEx();
		GrantUtil.setStoreId(urgeServicesMoneyEx);
		// 默认查询条件下的全部
		if (StringUtils.isEmpty(cid)) {
			List<UrgeServicesMoneyEx> urgeList = queryDealList(
					urgeServicesMoneyEx, resultString);
			// 没有选中的，默认为全部，根据催收主表id查询拆分表中为处理中（线下）的进行删除
			for(UrgeServicesMoneyEx moneyExItem : urgeList){
				if (judgeDeal(moneyExItem.getSplitBackResult())) {
					parameter.append("'" + moneyExItem.getUrgeId()+ "',");
				}
			}
		} else {
			// 如果有选中的单子,将选中的单子导出，id为催收主表id
			String[] ids = cid.split(",");
			urgeEx.setDictRDeductType(DeductWays.HJ_04.getCode());
		    for(String idItem : ids){
		    	String result = grantDeductsService.find(idItem).getDictDealStatus();
		    	// 将选中的单子进行判断回盘结果为失败的
		    	if (judgeDeal(result)) {
		    		parameter.append("'" + idItem + "',");
		    	}
		    }
		}
		// 获得需要进行导出的id
		if (StringUtils.isNotEmpty(parameter)) {
			try {
				urgeEx = grantDeductsService.saveExportFY(parameter);
				GrantDeductsFYExportUtil.exportData(urgeEx, response,threePartFileName.getFyDsExportFileName());
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出富友发生异常",e);
				addMessage(redirectAttributes, "富友平台导出失败"+e.getMessage());
			}
		}else {
			addMessage(redirectAttributes, "没有符合条件的需要导出数据");
		}
		return "redirect:"
		+ adminPath
		+ "/borrow/grant/grantDeducts/deductsInfo?returnUrl="+returnUrl+"&result="+resultString;
	}

	/**
	 * 处理判断
	 * 2016年4月27日
	 * By 朱静越
	 * @param result
	 * @return
	 */
	private boolean judgeDeal(String result) {
		return UrgeCounterofferResult.PAYMENT_FAILED.getCode().equals(result)||
				UrgeCounterofferResult.PROCESSED.getCode().equals(result)||
				UrgeCounterofferResult.PREPAYMENT.getCode().equals(result);
	}

	/**
	 * 好易联平台导出，备注为企业流水账号,
	 * 因为已经进行拆分过后，所以直接从拆分表中取值就可以，对拆分表中的回盘结果为失败的单子进行合并，完成之后删除这些单子
	 * ，之后进行拆分，更新新插入的单子的拆分的回盘结果为处理中
	 * 导出之后不用进行拆分，如果不属于该平台的数据，更换为该平台进行导出 
	 * 2016年1月6日 By 朱静越
	 * @param request
	 * @param response
	 * @param cid 催收id  
	 */
	@RequestMapping(value = "deductsHylExl")
	public String deductsHylExl(HttpServletRequest request,RedirectAttributes redirectAttributes,
			UrgeServicesMoneyEx urgeServicesMoneyEx, String resultString,String returnUrl,
			HttpServletResponse response, String cid) {
		String[] ids = null;
		StringBuilder parameter = new StringBuilder();
		GrantUtil.setStoreId(urgeServicesMoneyEx);
		if (StringUtils.isEmpty(cid)) {
			// 没有选中的，默认为全部
			List<UrgeServicesMoneyEx> urgeList = queryDealList(
					urgeServicesMoneyEx, resultString);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				for (UrgeServicesMoneyEx urgeItem : urgeList) {
					if (judgeDeal(urgeItem.getSplitBackResult())) {
						parameter.append("'" + urgeItem.getUrgeId()+ "',");
					}
				}
			}
		} else {
			// 如果有选中的单子,将选中的单子导出，id为催收主表id
			ids = cid.split(",");
			for (int i = 0; i < ids.length; i++) {
				String result = grantDeductsService.find(ids[i]).getDictDealStatus();
				if (judgeDeal(result)) {
					parameter.append("'" + ids[i] + "',");
				}
			}
		}
		// 获得需要进行导出的id
		if (StringUtils.isNotEmpty(parameter)) {
			// 导出好易联平台list
			try {
				UrgeServicesMoneyEx urgeEx = grantDeductsService.saveExportHYL(parameter);
				GrantDeductsHYLExportUtil.exportData(urgeEx, response,threePartFileName.getHylDsExportFileName());
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出好易联发生异常",e);
				addMessage(redirectAttributes, "好易联平台导出失败"+e.getMessage());
			}
		}else {
			addMessage(redirectAttributes, "没有符合条件的需要导出数据");
		}
		return "redirect:"
		+ adminPath
		+ "/borrow/grant/grantDeducts/deductsInfo?returnUrl="+returnUrl+"&result="+resultString;
	}

	/**
	 * 富友平台导入,导入富友平台之后，获得企业流水号，交易状态，根据企业流水号更改拆分表 
	 * 2016年1月7日 By 朱静越
	 * @param request
	 * @param response
	 * @param file 接收到的上传的文件
	 * @param returnUrl 要进行返回的页面
	 * @return 导入结果进行处理之后重定向
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "fyImportResult")
	public String fyImportResult(HttpServletRequest request,RedirectAttributes redirectAttributes,
			HttpServletResponse response, @RequestParam MultipartFile file,
			String returnUrl, String result) {
		ExcelUtils excelUtil = new ExcelUtils();
		try {
			List<UrgeServiceFyInputEx> list = (List<UrgeServiceFyInputEx>) excelUtil.importExcel(
					LoanFileUtils.multipartFile2File(file), 0, 0,
					UrgeServiceFyInputEx.class);
			// 获得列表中的单子的信息，进行更新,根据企业流水号，
			if (ArrayHelper.isNotEmpty(list)) {
				grantDeductsService.importFY(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入富友平台发生异常",e);
			addMessage(redirectAttributes, "导入富友失败"+e.getMessage());
		}
		return "redirect:" + adminPath
				+ "/borrow/grant/grantDeducts/deductsInfo?result=" + result
				+ "&returnUrl=" + returnUrl;
	}

	/**
	 * 上传好易联，导入回盘结果,根据交易结果进行判断是否成功，同时根据清算日期进行更新
	 * 2016年1月7日 By 朱静越
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl 要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "hylImportResult")
	public String hylImportResult(HttpServletRequest request,RedirectAttributes redirectAttributes,
			HttpServletResponse response, @RequestParam MultipartFile file,
			String returnUrl, String result) {
		try {
			ExcelUtils excelutil = new ExcelUtils();
			List<UrgeServiceHylInputEx> list = (List<UrgeServiceHylInputEx>) excelutil.importExcel(
					LoanFileUtils.multipartFile2File(file), 1, 1,
					UrgeServiceHylInputEx.class);
			// 获得列表中的单子的信息，进行更新,根据企业流水号，
			if (ArrayHelper.isNotEmpty(list)) {
				grantDeductsService.saveImportHYL(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入好易联发生异常",e);
			addMessage(redirectAttributes, "导入富友失败"+e.getMessage());
		}
		return "redirect:" + adminPath
				+ "/borrow/grant/grantDeducts/deductsInfo?result=" + result
				+ "&returnUrl=" + returnUrl;
	}

	/**
	 * 提交追回平台，更改划扣平台，划扣回盘结果 ,回盘时间 更新催收主表中的回盘结果，最新划扣日期，已划金额为催收金额
	 * 如果选择的划扣平台为【蓝补冲抵】，需要获得未划金额和蓝补金额进行比较，如果追回成功需要更新蓝补金额；如果蓝补金额比较少，给出提示框；
	 * 在追回的时候要进行筛选，催收主表中的回盘结果为划扣失败的 2015年12月22日 By 朱静越
	 * @param checkVal 催收主表id
	 * @param splitFailResult 追回原因
	 * @return 批量追回的时候，成功的个数
	 */
	@ResponseBody
	@RequestMapping(value = "deductsBackSure")
	public String deductsBackSure(String checkVal, String backPlat,String result,
			UrgeServicesMoneyEx urgeServicesMoneyEx) {
		String[] ids = null;
		int flag = 0; // 蓝补冲抵的时候进行提示
		String returnMes = null;
		String message = null;
		int failNum = 0;
		int successNum = 0;
		GrantUtil.setStoreId(urgeServicesMoneyEx);
		// 如果进行了选择
		if (StringUtils.isNotEmpty(checkVal)) {
			ids = checkVal.split(",");
		} else { // 没有进行选择
			List<UrgeServicesMoneyEx> urgeList = queryDealList(
					urgeServicesMoneyEx, result);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				ids = new String[urgeList.size()];
				for (int i = 0; i < urgeList.size(); i++) {
					ids[i] = urgeList.get(i).getUrgeId();
				}
			}
		}
		// 追回处理，根据选择的不同进行不同的
		if(null != ids){
			for (int i = 0; i < ids.length; i++) {
				try {
					if (BEFORE.equals(result)) {
						returnMes = grantDeductsService.deductBackDeal(ids[i], backPlat);
					}else {
						returnMes = grantDeductsService.deductBackDealToday(ids[i], backPlat);
					}
					if (StringUtils.isNotEmpty(returnMes)) {
						failNum++;
					}else {
						successNum++;
					}
					if (GrantCommon.DEDUCT_BANCK_FAIL.equals(returnMes)) {
						flag++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("催收服务费追回发生异常",e);
					failNum ++;
				}
			}
		}
		if (flag > 0) {
			message = "追回成功"+successNum+"个单子，失败"+failNum+"个单子，有"+flag+"个单子"+GrantCommon.DEDUCT_BANCK_FAIL;
		}else{
			message = "追回成功"+successNum+"个单子，失败"+failNum+"个单子";
		}
		return message;
	}
	
	/**
	 * 通联平台导出
	 * 因为已经进行拆分过后，所以直接从拆分表中取值就可以，对拆分表中的回盘结果为失败的单子进行合并，
	 * 完成之后删除这些单子，之后进行拆分，拆分完成的状态就为【处理中导出】
	 * 导出之后不用进行拆分，如果不属于该平台的数据，更换为该平台进行导出
	 * @param request
	 * @param response
	 * @param cid 催收id
	 */
	@RequestMapping(value = "deductsTLExl")
	public String deductsTLExl(HttpServletRequest request,RedirectAttributes redirectAttributes,
			UrgeServicesMoneyEx urgeServicesMoneyEx, String resultString,
			HttpServletResponse response, String cid,String returnUrl) {
		StringBuilder parameter = new StringBuilder();
		GrantUtil.setStoreId(urgeServicesMoneyEx);
		if (StringUtils.isEmpty(cid)) {
			// 没有选中的，默认为全部
			List<UrgeServicesMoneyEx> urgeList = queryDealList(urgeServicesMoneyEx, resultString);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				for (UrgeServicesMoneyEx urgeItem : urgeList) {
					if (judgeDeal(urgeItem.getSplitBackResult())) {
						parameter.append("'"+urgeItem.getUrgeId()+"',");
					}
				}
			}
		}else {
			// 如果有选中的单子,将选中的单子导出，id为催收主表id
			String[] ids = cid.split(",");
			for (int i = 0; i < ids.length; i++) {
				String status = grantDeductsService.find(ids[i]).getDictDealStatus();
				if (judgeDeal(status)) {
					parameter.append("'"+ids[i]+"',");
				}
			}
		}
		// 获得需要进行导出的id
		if(StringUtils.isNotEmpty(parameter)){
			try {
				UrgeServicesMoneyEx urgeEx = grantDeductsService.saveExportTL(parameter);
				GrantDeductsTLExportUtil.exportData(urgeEx, response, threePartFileName.getTlDsExportFileName());
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出通联发生异常",e);
				addMessage(redirectAttributes, "导出通联失败"+e.getMessage());
			}
		}else {
			addMessage(redirectAttributes, "没有符合条件的需要导出数据");
		}
		return "redirect:" + adminPath
				+ "/borrow/grant/grantDeducts/deductsInfo?result=" + resultString
				+ "&returnUrl=" + returnUrl;
	}

	/**
	 * 上传通联，导入回盘结果,根据交易结果进行判断是否成功，同时根据清算日期进行更新
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl 要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "tlImportResult")
	public String tlImportResult(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes,
			@RequestParam MultipartFile file,String returnUrl,String result){
		try {
			ExcelUtils excelutil = new ExcelUtils();
			List<UrgeServiceTlInputEx> lst = (List<UrgeServiceTlInputEx>) excelutil.importExcel(
					LoanFileUtils.multipartFile2File(file), 0, 0, UrgeServiceTlInputEx.class);
			// 获得列表中的单子的信息，进行更新,根据企业流水号，
			if (ArrayHelper.isNotEmpty(lst)) {
				grantDeductsService.saveImportTL(lst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入通联发生异常",e);
			addMessage(redirectAttributes, "导入通联出错"+e.getMessage());
		}
		return "redirect:"+adminPath+"/borrow/grant/grantDeducts/deductsInfo?result="+result+"&returnUrl="+returnUrl;
	}

	
	/**
	 * 中金平台导出
	 * 因为已经进行拆分过后，所以直接从拆分表中取值就可以，对拆分表中的回盘结果为失败的单子进行合并，
	 * 完成之后删除这些单子，之后进行拆分，更新新插入的单子的拆分的回盘结果为处理中
	 * 导出之后不用进行拆分，如果不属于该平台的数据，更换为该平台进行导出
	 * @param request
	 * @param response
	 * @param cid 催收id
	 */
	@RequestMapping(value = "deductsZJExl")
	public String deductsZJExl(HttpServletRequest request,UrgeServicesMoneyEx urgeServicesMoneyEx,String resultString,
			HttpServletResponse response, String cid,RedirectAttributes redirectAttributes,String returnUrl){
		StringBuilder parameter = new StringBuilder();
		GrantUtil.setStoreId(urgeServicesMoneyEx);
		if (StringUtils.isEmpty(cid)) {
			// 没有选中的，默认为全部
			List<UrgeServicesMoneyEx> urgeList = queryDealList(urgeServicesMoneyEx, resultString);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				for (UrgeServicesMoneyEx urgeItem : urgeList) {
					if (judgeDeal(urgeItem.getSplitBackResult())) {
						parameter.append("'"+urgeItem.getUrgeId()+"',");
					}
				}
			}
		}else {
			// 如果有选中的单子,将选中的单子导出，id为催收主表id
			String[] ids = cid.split(",");
			for (int i = 0; i < ids.length; i++) {
				String result = grantDeductsService.find(ids[i]).getDictDealStatus();
				if (judgeDeal(result)) {
					parameter.append("'" + ids[i] + "',");
				}
			}
		}
		// 获得需要进行导出的id
		if(StringUtils.isNotEmpty(parameter)){
			try {
				UrgeServicesMoneyEx urgeEx = grantDeductsService.saveExportZJ(parameter);
				GrantDeductsZJExportUtil.exportData(urgeEx, response, threePartFileName.getZjDsExportFileName());
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出中金错误",e);
				addMessage(redirectAttributes, "导出中金错误"+e.getMessage());
			}
		}else {
			addMessage(redirectAttributes, "没有符合条件的需要导出数据");
		}
		return "redirect:"
		+ adminPath
		+ "/borrow/grant/grantDeducts/deductsInfo?returnUrl="+returnUrl+"&result="+resultString;
	}
	

	/**
	 * 上传中金，导入回盘结果,根据交易结果进行判断是否成功，同时根据清算日期进行更新
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl 要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "zjImportResult")
	public String zjImportResult(HttpServletRequest request,HttpServletResponse response,
			@RequestParam MultipartFile file,String returnUrl,String result,RedirectAttributes redirectAttributes){
		try {
			ExcelUtils excelutil = new ExcelUtils();
			List<UrgeServiceZJInputEx> dataList = (List<UrgeServiceZJInputEx>) excelutil.importExcel(
					LoanFileUtils.multipartFile2File(file), 1, 0, UrgeServiceZJInputEx.class);
			// 获得列表中的单子的信息，进行更新,根据协议用户编号
			if (ArrayHelper.isNotEmpty(dataList)) {
				grantDeductsService.saveImportZJ(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入中金错误",e);
			addMessage(redirectAttributes, "导入中金出现错误"+e.getMessage());
		}
		return "redirect:"+adminPath+"/borrow/grant/grantDeducts/deductsInfo?result="+result+"&returnUrl="+returnUrl;
	}
	
}
