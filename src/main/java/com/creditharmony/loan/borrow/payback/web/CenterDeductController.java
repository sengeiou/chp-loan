package com.creditharmony.loan.borrow.payback.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.DeductsPaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackList;
import com.creditharmony.loan.borrow.payback.facade.CenterPaybackFacade;
import com.creditharmony.loan.borrow.payback.service.CenterDeductService;
import com.creditharmony.loan.common.constants.RepayListStatus;
import com.creditharmony.loan.common.entity.CodeName;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.common.service.PlatformRuleService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * 集中划扣申请
 * 
 * @Class Name CenterDeductController
 * @author zhaojinping
 * @Create In 2015年11月30日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/jzapply")
public class CenterDeductController extends BaseController {

	@Autowired
	private CenterDeductService centerDeductService;

	@Autowired
	private PaymentSplitService paybackSplitService;

	@Autowired
	private PlatformRuleService platService;

	@Autowired
	private PaybackService paybackService;

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RepaymentDateService dateService;
	@Autowired
	private CenterPaybackFacade centerPaybackFacade;

	/**
	 * 显示列表页面 2015年12月11日 By zhaojinping
	 * 
	 * @param model
	 * @param request
	 * @param map
	 * @return 跳转路径
	 */
	@RequestMapping(value = "list")
	public String getAllList(Model model, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) {
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		paramMap.put("status", "'"+YESNO.NO.getCode()+"',"+"'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'");
		String stores = (String) paramMap.get("stores");
		String bank = (String) paramMap.get("bank");
		String dictPayStatus = (String) paramMap.get("dictPayStatus");
		String mark = (String)paramMap.get("mark");
		if (!ObjectHelper.isEmpty(stores)) {
			paramMap.put("storesId", appendString(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			paramMap.put("bankId", appendString(bank));
		}
		if (!ObjectHelper.isEmpty(dictPayStatus)) {
			paramMap.put("status", "'"+dictPayStatus+"'");
		}
		if (!ObjectHelper.isEmpty(mark)) {
			paramMap.put("mark", appendString(mark));
		}
		long b = System.currentTimeMillis();
		Page<PaybackList> paybackPage = centerDeductService.getAllJzApplyList(
				new Page<PaybackList>(request, response),
				paramConvert(paramMap));
		long c = System.currentTimeMillis();
		System.out.println("查询时间"+(c-b));
		NumTotal numTotal = new  NumTotal();
		List<PaybackList> paybacklist = paybackPage.getList();
		if(paybacklist.size()>0){
			//for(PaybackList pay: paybacklist){
				numTotal.setNum(paybackPage.getCount()+"");
				numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont().toString());
			//}
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		List<CodeName> codeName = new ArrayList<CodeName>();
		codeName.add(new CodeName(RepayListStatus.PRE_PAYMENT.getCode(),RepayListStatus.PRE_PAYMENT.getName()));
		codeName.add(new CodeName(RepayListStatus.REPAYMENT_RETURN.getCode(),RepayListStatus.REPAYMENT_RETURN.getName()));
		long d = System.currentTimeMillis();
		System.out.println("转换时间"+(d-c));
		model.addAttribute("dayList", dayList);
		model.addAttribute("codeName", codeName);
		model.addAttribute("numTotal", numTotal);
		model.addAttribute("page", paybackPage);
		model.addAttribute("paramMap", paramMap);
		logger.debug("invoke ApplyPayLaunchController method: findContractData, consult.id is: "
				+ paybackPage);
		return "borrow/payback/centerdeduct/centerDeduct";
	}

	/**
	 * 集中划扣申请 2015年12月11日 By 翁私
	 * @param model
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(value = "centerApply", method = RequestMethod.POST)
	public String centerApply(Model model, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) {
	try{
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "search_");
		paramMap.put("status", "'"+YESNO.NO.getCode()+"',"+"'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'");
		String stores = (String) paramMap.get("stores");
		String bank = (String) paramMap.get("bank");
		String dictPayStatus = (String) paramMap.get("dictPayStatus");
		if (!ObjectHelper.isEmpty(stores)) {
			paramMap.put("storesId", appendString(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			paramMap.put("bankId", appendString(bank));
		}
		// 待还款id
		String ids = (String)paramMap.get("id");
		// 申请操作
		// 拼接id
		if (!StringUtils.isEmpty(ids)) {
			String idstring = FilterHelper.appendIdFilter(ids);
			paramMap.put("id", idstring);
		}
		String mes = "";
		// 划扣平台
		String platform = (String )paramMap.get("platFormId");
		if(StringUtils.isEmpty(platform)){
			mes = "划扣平台不能为空！";
			return mes;
		}
		if (!ObjectHelper.isEmpty(dictPayStatus)) {
			paramMap.put("status", "'"+dictPayStatus+"'");
		}
		paramMap.put("channelFlag", ChannelFlag.ZCJ.getCode());
	   List<DeductsPaybackApply> paybackApplylist = centerDeductService.queryCenterApply(paramConvert(paramMap));
	   if(paybackApplylist != null && paybackApplylist.size()>0){
		/*   for(DeductsPaybackApply apply : paybackApplylist){
			   try{
				   centerDeductService.deductionApply(paramMap,apply);
				   totalAmont= totalAmont.add(apply.getApplyDeductAmount());
				   i++;
			   }catch(Exception e){
				   e.printStackTrace();
			   }
		   }
		   java.text.DecimalFormat myformat=new java.text.DecimalFormat("#.00");
		   String amont = myformat.format(totalAmont);   
		   mes.append("集中划扣申请成功,申请总金额:"+amont+"申请总笔数:"+i);*/
		  mes =  centerPaybackFacade.submitData(paybackApplylist);
	   }else{
		   mes = "没有要发送的数据！";
	   }
		// 清空查询条件
	    String monthPayDay = (String) paramMap.get("monthPayDay");
	    paramMap.clear();
	    paramMap.put("monthPayDay", monthPayDay);
	    paramMap.put("status", "'"+YESNO.NO.getCode()+"',"+"'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'");
		Page<PaybackList> paybackPage = centerDeductService.getAllJzApplyList(
				new Page<PaybackList>(request, response),
				paramConvert(paramMap));
		List<PaybackList> paybacklist = paybackPage.getList();
		NumTotal numTotal = new  NumTotal();
		if(paybacklist.size()>0){
			numTotal.setNum(paybacklist.get(0).getSumNumber());
			numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont().toString());
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("numTotal", numTotal);
		model.addAttribute("page", paybackPage);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("message", mes);
		logger.debug("集中划扣申请处理完成"+ paybackPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	    return "borrow/payback/centerdeduct/centerDeduct";
	}

	/**
	 * 显示卡号信息列表页面 2015年12月11日 By zhaojinping
	 * 
	 * @param model
	 * @param contractCode
	 * @return 借款信息列表
	 */
	@ResponseBody
	@RequestMapping(value = "cardInfo", method = RequestMethod.POST)
	public String getCardInfo(
			Model model,
			@RequestParam(value = "contractCode", required = false) String contractCode) {
		List<LoanBank> itemList = new ArrayList<LoanBank>();
		itemList = centerDeductService.getUserCardInfo(contractCode);
		model.addAttribute("itemList", itemList);
		logger.debug("invoke CenterDeductController method: getCardInfo, consult.id is: "
				+ itemList);
		return JsonMapper.nonDefaultMapper().toJson(itemList);
	}

	/**
	 * 修改处于选中状态的卡号信息的置顶状态 2015年12月11日 By zhaojinping
	 * 
	 * @param request
	 * @return 跳转路径
	 */
	@RequestMapping(value = "modifyCardInfo", method = RequestMethod.POST)
	public String modifyCardInfo(HttpServletRequest request) {
		String id = request.getParameter("selectCard");
		centerDeductService.updateCardInfo(id);
		return "redirect:" + adminPath + "/borrow/payback/jzapply/list";
	}

	/**
	 * 把从页面传递过来的参数转换成与数据库中的字段类型一致 2015年12月11日 By zhaojinping
	 * 
	 * @param paramMap
	 * @return 转化后的map
	 */
	public Map<String, Object> paramConvert(Map<String, Object> paramMap) {
		Date monthPayDayDate = new Date();
		/*
		 * Object object = paramMap.get("monthPayDay");
		 * if(!ObjectHelper.isEmpty(object)){ monthPayDayDate =
		 * DateUtils.parseDate(object);
		 * paramMap.put("monthPayDay",monthPayDayDate); }
		 */
		Object object = paramMap.get("contractReplayDay");
		if (!ObjectHelper.isEmpty(object)) {
			monthPayDayDate = DateUtils.parseDate(paramMap
					.get("contractReplayDay"));
			paramMap.put("contractReplayDay", monthPayDayDate);
		}

		object = paramMap.get("contractEndDay");
		if (!ObjectHelper.isEmpty(object)) {
			monthPayDayDate = DateUtils.parseDate(paramMap
					.get("contractEndDay"));
			paramMap.put("contractEndDay", monthPayDayDate);
		}
		return paramMap;
	}

	/**
	 * 集中划扣申请导出 
	 * 2015年12月25日 By wengsi
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 * @return none
	 */
	@RequestMapping(value = "exportExcel")
	public String exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) {
		String[] ids = null;
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "search_");
		String stores = (String) paramMap.get("stores");
		String bank = (String) paramMap.get("bank");
		if (!ObjectHelper.isEmpty(stores)) {
			paramMap.put("storesId", appendString(stores));
		}
		if (!ObjectHelper.isEmpty(bank)) {
			paramMap.put("bankId", appendString(bank));
		}
		
		String idVal = (String) paramMap.get("id");
		try {
			if (StringUtils.isEmpty(idVal)) {
				paramMap.put("status", "'"+YESNO.NO.getCode()+"',"+"'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'");
				// grantList = centerDeductService.getCenterDeductList(paramMap);
			} else {
				// 拼接id
				ids = idVal.split(",");
				StringBuilder parameter = new StringBuilder();
				for (String id : ids) {
					if (id != null && !"".equals(id)) {
						parameter.append("'" + id + "',");
					}
				}
				String idstring = null;
				if (parameter != null) {
					idstring = parameter.toString();
					idstring = idstring
							.substring(0, parameter.lastIndexOf(","));
				}
				paramMap.put("status", "'"+YESNO.NO.getCode()+"',"+"'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'");
				paramMap.put("id", idstring);
				// 查询要导出的数据
				//grantList = centerDeductService.getCenterDeductList(paramMap);
			}
			ExportCenterDeductHelper.exportData(paramConvert(paramMap), response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/borrow/payback/jzapply/list";
		}

	}

	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (String id : idArray) {
			if (id != null && !"".equals(id)) {
				parameter.append("'" + id + "',");
			}
		}
		String idstring = null;
		if (parameter != null) {
			idstring = parameter.toString();
			idstring = idstring.substring(0, parameter.lastIndexOf(","));
		}
		return idstring;
	}

	/*
	 * @RequestMapping(value="getPaybackDuce") public void getPaybackDuce(){
	 * centerDeductService.updateRepaymentState(); }
	 */
}
