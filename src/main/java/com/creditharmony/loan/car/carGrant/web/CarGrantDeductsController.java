
package com.creditharmony.loan.car.carGrant.web;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.DeductWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceHylInputEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceTLInputEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceZJInputEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesMoneyEx;
import com.creditharmony.loan.car.carGrant.ex.CheckDoneEx;
import com.creditharmony.loan.car.carGrant.ex.DrawDoneEx;
import com.creditharmony.loan.car.carGrant.service.CarGrantDeductsService;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;
import com.creditharmony.loan.car.common.entity.CarGrantCommon;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.car.common.entity.ex.CarLoanStatusHisEx;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.consts.SystemSetFlag;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.PlatformRule;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.PlatformRuleService;
import com.creditharmony.loan.common.utils.ExcelMatch;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.google.common.collect.Lists;

/**
 * 放款当日待划扣列表处理
 * @Class Name CarGrantDeductsController
 */
@Controller
@RequestMapping(value = "${adminPath}/car/grant/grantDeducts")
public class CarGrantDeductsController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private CarGrantDeductsService grantDeductsService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private CarHistoryService carHistoryService;
	@Autowired
	private CarGrantRecepicService carGrantRecepicService;
	@Autowired
	private PlatformRuleService platformRuleService;
	private String BEFORE="bef";
	
	/**
	 * 放款当日待划扣列表，同时计算出所有的划扣金额，和总笔数
	 * @param model
	 * @param urgeMoneyEx 催收实体
	 * @param result 根据result区分是当日待划扣或以往待划扣
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "deductsInfo")
	public String deductsInfo(Model model,CarUrgeServicesMoneyEx urgeMoneyEx,String result,
			HttpServletRequest request,HttpServletResponse response,String returnUrl) {
		// 放款当日待划扣列表，从催收主表中查询，检索条件为划扣回盘结果为0,1,3；默认backResult为空时查询0，1，3;暂时不确定待划扣是否需要查询,默认情况下回盘结果为空
		urgeMoneyEx.setSplitBackResult("'"+CounterofferResult.PREPAYMENT.getCode()+"',"+"'"+CounterofferResult.PAYMENT_FAILED.getCode()+"',"+"'"+CounterofferResult.PROCESS.getCode()+"',"+"'"+
				CounterofferResult.PROCESSED.getCode()+"'");
		if (result.equals(BEFORE)) {
			// 如果为以往待划扣,设置时间标识为是
			urgeMoneyEx.setTimeFlag(YESNO.YES.getCode());
		}else{
			urgeMoneyEx.setTimeFlag(YESNO.NO.getCode());
		}
		Page<CarUrgeServicesMoneyEx> urgePage = grantDeductsService.selectUrgeList(new Page<CarUrgeServicesMoneyEx>(request, response), urgeMoneyEx);
		List<Dict> list = DictCache.getInstance().getList();
		if (ArrayHelper.isNotEmpty(urgePage.getList()) && urgePage.getList().size() > 0) {
			for (int i = 0; i < urgePage.getList().size(); i++) {
				CarUrgeServicesMoneyEx carUrgeServicesMoneyEx = urgePage.getList().get(i);
				for(Dict dict:list){
					if(StringUtils.isNotEmpty(dict.getValue())&&"jk_open_bank".equals(dict.getType())&&dict.getValue().equals(carUrgeServicesMoneyEx.getCardBank())){
						carUrgeServicesMoneyEx.setCardBank(dict.getLabel());
					}
					if(StringUtils.isNotEmpty(dict.getValue())&&"jk_deduct_plat".equals(dict.getType())&&dict.getValue().equals(carUrgeServicesMoneyEx.getDictDealType())){
						carUrgeServicesMoneyEx.setDictDealType(dict.getLabel());
					}
					if(StringUtils.isNotEmpty(dict.getValue())&&"jk_counteroffer_result".equals(dict.getType())&&dict.getValue().equals(carUrgeServicesMoneyEx.getSplitBackResult())){
						carUrgeServicesMoneyEx.setSplitBackResult(dict.getLabel());
					}
					if(StringUtils.isNotEmpty(dict.getValue())&&"jk_channel_flag".equals(dict.getType())&&dict.getValue().equals(carUrgeServicesMoneyEx.getDictLoanFlag())){
						carUrgeServicesMoneyEx.setDictLoanFlag(dict.getLabel());
					}
					if(StringUtils.isNotEmpty(dict.getValue())&&"jk_car_throuth_flag".equals(dict.getType())&&dict.getValue().equals(carUrgeServicesMoneyEx.getLoanFlag())){
						carUrgeServicesMoneyEx.setLoanFlag(dict.getLabel());
					}
					if(StringUtils.isNotEmpty(dict.getValue())&&"jk_telemarketing".equals(dict.getType())&&dict.getValue().equals(carUrgeServicesMoneyEx.getCustomerTelesalesFlag())){
						carUrgeServicesMoneyEx.setCustomerTelesalesFlag(dict.getLabel());
					}
				}
			}
		}
		// 查询所有数据，查出累计划扣金额、划扣总笔数
		BigDecimal totalDeducts=new BigDecimal(0.00);
		List<CarUrgeServicesMoneyEx> totalUrgeList = grantDeductsService.selectUrgeListNo(urgeMoneyEx);
		
		if (ArrayHelper.isNotEmpty(totalUrgeList) && totalUrgeList.size() > 0) {
			for (int i = 0; i < totalUrgeList.size(); i++) {
				BigDecimal deducts=totalUrgeList.get(i).getUrgeMoeny();
				if (deducts!=null) {
					totalDeducts=totalDeducts.add(deducts);
				}
			}
		}
		
		
		List<CityInfo> provinceList = cityManager.findProvince();
		
		// 控制当日待划扣页面中 停止自动划扣、开始自动划扣按钮的显示
		SystemSetting ssParam = new SystemSetting();
		ssParam.setSysFlag(SystemSetFlag.CAR_SERVICES_SETTING_FLAG);
		SystemSetting ssResult = grantDeductsService.getSystemSetting(ssParam);
		if (ssResult != null) {
			model.addAttribute("isAutomatic", ssResult.getSysValue());
		}
		
		model.addAttribute("totalNum", totalUrgeList.size());   
		model.addAttribute("deductsAmount", totalDeducts.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
		model.addAttribute("urgeMoneyEx", urgeMoneyEx);
		model.addAttribute("urgeList", urgePage);
		model.addAttribute("provinceList", provinceList);
		return "car/grant/"+returnUrl;
	}
	
	/**
	 * 导出 判断是否有导出的数据
	 * @param urgeServicesMoneyEx
	 * @param result
	 * @param cid
	 */
	@ResponseBody
	@RequestMapping(value = "isExportDeducts" , method = {RequestMethod.POST})
	public String isExportDeducts(CarUrgeServicesMoneyEx urgeServicesMoneyEx,String result,
			String cid){
		int flag = 0;
		String[] id=null;
		if (StringUtils.isEmpty(cid)) {
			// 没有选中的，默认为全部
			List<CarUrgeServicesMoneyEx> urgeList = selDealList(urgeServicesMoneyEx, result);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				for (int i = 0; i < urgeList.size(); i++) {
					if (CounterofferResult.PAYMENT_FAILED.getCode().equals(urgeList.get(i).getSplitBackResult()) || 
							CounterofferResult.PROCESSED.getCode().equals(urgeList.get(i).getSplitBackResult())) {
						
						flag++;
					}
				}
			}
		}else {
			// 如果有选中的单子,将选中的单子导出，id为催收主表id
			id=cid.split(",");
			
			for (int i = 0; i < id.length; i++) {
				CarUrgeServicesMoneyEx urgeEx=new CarUrgeServicesMoneyEx();
				urgeEx.setUrgeId(id[i]);
				List<CarUrgeServicesMoneyEx> urgeList = selDealList(urgeEx, result);
			
				if (ArrayHelper.isNotEmpty(urgeList) && 
						CounterofferResult.PAYMENT_FAILED.getCode().equals(urgeList.get(0).getSplitBackResult()) || 
						CounterofferResult.PROCESSED.getCode().equals(urgeList.get(0).getSplitBackResult())) {
					flag++;
				}
			}
		}
		
		if (flag > 0) {
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	/**
	 * 选中数据是否符合条件
	 * @param cid 选中编号
	 * @param deductsType  划扣平台
	 * @param result  当日/以往
	 * @return
	 */
	@RequestMapping(value = "isConditions")@ResponseBody
	public boolean isConditions(String cid, String deductsType,String result){
		if (cid != null && 0 != cid.length() ) {
			String[] id=null;
			id=cid.split(",");
			CarUrgeServicesMoneyEx urgeEx=new CarUrgeServicesMoneyEx();
			for (int i = 0; i < id.length; i++) {
				urgeEx.setUrgeId(id[i]);
				String cardBank = grantDeductsService.selectUrgeListNo(urgeEx).get(0).getCardBank();
				List<PlatformRule> findAllList = platformRuleService.findRuleByBank(new PlatformRule(deductsType, cardBank, DeductTime.RIGHTNOW.getCode()));
				if (findAllList==null || findAllList.size() == 0) {
					return false;
				}
			}
			
		}
		return true;
	}
	/**
	 * 好易联、通联、中金平台导出，备注为企业流水账号,
	 * @param request
	 * @param response
	 * @param cid 催收id
	 * @param deductsType 划扣平台
	 * @param urgeServicesMoneyEx 
	 */
	@RequestMapping(value = "deductsExl")
	public void deductsHylExl(HttpServletRequest request,
			CarUrgeServicesMoneyEx urgeServicesMoneyEx, String resultString,
			HttpServletResponse response, String cid, String deductsType) {
		
		String[] id=null;
		String titleFlag;
		String idstring = null;
		StringBuilder parameter = new StringBuilder();
		CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
		CarUrgeServicesMoneyEx urgeEx=new CarUrgeServicesMoneyEx();
		List<PaybackApply> payList = new ArrayList<PaybackApply>();
		if (resultString.equals(BEFORE)) {
			// 如果为以往待划扣,设置时间标识为是
			urgeServicesMoneyEx.setTimeFlag(YESNO.YES.getCode());
			titleFlag = "放款以往待划扣";

		}else{
			urgeServicesMoneyEx.setTimeFlag(YESNO.NO.getCode());
			titleFlag = "放款当日待划扣";

		}
		if (StringUtils.isEmpty(cid)) {
			// 没有选中的，默认为全部
			List<CarUrgeServicesMoneyEx> urgeList = selDealList(urgeServicesMoneyEx, resultString);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				for (int i = 0; i < urgeList.size(); i++) {
					if (CounterofferResult.PAYMENT_FAILED.getCode().equals(urgeList.get(i).getSplitBackResult()) || 
							CounterofferResult.PROCESSED.getCode().equals(urgeList.get(i).getSplitBackResult())) {
						parameter.append("'"+urgeList.get(i).getUrgeId()+"',");
					}
				}
			}
		}else {
			// 如果有选中的单子,将选中的单子导出，id为催收主表id
			id=cid.split(",");
			for (int i = 0; i < id.length; i++) {
				urgeEx.setUrgeId(id[i]);
				String result = grantDeductsService.selectUrgeList(new Page<CarUrgeServicesMoneyEx>(request, response), urgeEx).getList().get(0).getSplitBackResult();
				if (CounterofferResult.PAYMENT_FAILED.getCode().equals(result) || 
						CounterofferResult.PROCESSED.getCode().equals(result)) {
					parameter.append("'"+id[i]+"',");
				}
			}
		}
		List<CarUrgeServicesMoneyEx> delList = null;
		// 获得需要进行导出的id
		if(StringUtils.isNotEmpty(parameter)){
			idstring = parameter.toString();
			idstring = idstring.substring(0,parameter.lastIndexOf(","));
			urgeEx.setUrgeId(idstring);
			urgeServicesMoney.setId(idstring);
			payList = grantDeductsService.queryUrgeList(idstring);
			// 删除拆分表中已经存在的单子,拆分表中处理状态为划扣失败的单子
			urgeEx.setSplitResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
			delList = grantDeductsService.selProcess(urgeEx);
		}
		// 根据催收主表id，将选中的单子的划扣平台更新为新的导出平台，同时将处理状态更新为处理中(线下)
		grantDeductsService.deductsHylExl(delList, idstring, payList, deductsType, urgeEx, titleFlag, response, urgeServicesMoney);
}
	/**
	 * 上传好易联，导入回盘结果,根据交易结果进行判断是否成功
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl
	 *            要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "hylImportResult")
	public String hylImportResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file,
			String returnUrl, String result) {
		try {
			if (ExcelMatch.matchResult(file, CarUrgeServiceHylInputEx.class)) {
				ExcelUtils excelutil = new ExcelUtils();
				List<CarUrgeServiceHylInputEx> lst = new ArrayList<CarUrgeServiceHylInputEx>();
				String tracStatus = null;
				String enterpriseSerialno = null;
				StringBuilder parameter = new StringBuilder();
				List<?> datalist = excelutil
						.importExcel(file, 0, 0,
								CarUrgeServiceHylInputEx.class,null);
				lst = (List<CarUrgeServiceHylInputEx>) datalist;
				// 获得列表中的单子的信息，进行更新,根据企业流水号，
				if (ArrayHelper.isNotEmpty(lst)) {
					for (int i = 0; i < lst.size(); i++) {
						CarUrgeServicesMoneyEx urgeMoneyEx = new CarUrgeServicesMoneyEx();
						// 获得表格中的交易状态
						tracStatus = lst.get(i).getDictDealStatus();
						// 获得表格中的企业流水号
						urgeMoneyEx
								.setEnterpriseSerialno(lst.get(i).getEnterpriseSerialno());
						// 设置划扣标识
						urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());
						// 获得原因
						urgeMoneyEx.setSplitFailResult(lst.get(i).getReason());
						// 设置回盘时间
						urgeMoneyEx.setSplitBackDate(new Date());
						if (tracStatus.equals("成功")) {
							urgeMoneyEx
									.setSplitResult(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
						} else {
							urgeMoneyEx
									.setSplitResult(CounterofferResult.PAYMENT_FAILED
											.getCode());
						}
						try{
						// 更新拆分表
							grantDeductsService.updUrgeSplit(urgeMoneyEx);
							parameter.append("'" + lst.get(i).getEnterpriseSerialno() + "',");
						}catch(Exception e){
							
						}
					}
					// 更新划扣主表 t_cj_urge_services_amount
					enterpriseSerialno = parameter.toString().substring(0,
							parameter.lastIndexOf(","));
					List<CarUrgeServicesMoney> updUrgeServices = grantDeductsService
							.selSuccess(enterpriseSerialno);
					for (int i = 0; i < updUrgeServices.size(); i++) {
						// 失败笔数大于0
						if (updUrgeServices.get(i).getSuccessAmount() > 0) {
							updUrgeServices.get(i).setDictDealStatus(CounterofferResult.PAYMENT_FAILED
											.getCode());
							
						} else {
							updUrgeServices.get(i).setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
						}
						updUrgeServices.get(i).setUrgeDecuteDate(new Date());
						try{
							grantDeductsService.updateUrge(updUrgeServices.get(i));
						}catch(Exception e){
							
						}
					}
				}
			return "redirect:" + adminPath
					+ "/car/grant/grantDeducts/deductsInfo?result=" + result
					+ "&returnUrl=" + returnUrl;
			} else {
				return BooleanType.FALSE;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 上传中金，导入回盘结果,根据交易结果进行判断是否成功
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl
	 *            要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "zjImportResult")
	public String zjImportResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file,
			String returnUrl, String result) {
		try {
			if (ExcelMatch.matchResult(file, CarUrgeServiceZJInputEx.class)) {
				ExcelUtils excelutil = new ExcelUtils();
				List<CarUrgeServiceZJInputEx> lst = new ArrayList<CarUrgeServiceZJInputEx>();
				String tracStatus = null;
				String enterpriseSerialno = null;
				StringBuilder parameter = new StringBuilder();
				List<?> datalist = excelutil
						.importExcel(file, 0, 0,
								CarUrgeServiceZJInputEx.class,null);
				lst = (List<CarUrgeServiceZJInputEx>) datalist;
				// 获得列表中的单子的信息，进行更新,根据企业流水号，
				if (ArrayHelper.isNotEmpty(lst)) {
					for (int i = 0; i < lst.size(); i++) {
						CarUrgeServicesMoneyEx urgeMoneyEx = new CarUrgeServicesMoneyEx();
						// 获得表格中的交易状态
						tracStatus = lst.get(i).getDictDealStatus();
						// 获得表格中的企业流水号
						urgeMoneyEx
								.setEnterpriseSerialno(lst.get(i).getEnterpriseSerialno());
						// 设置划扣标识
						urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());

						// 设置回盘时间
						urgeMoneyEx.setSplitBackDate(new Date());
						if (tracStatus.equals("成功")) {
							urgeMoneyEx
									.setSplitResult(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
						} else {
							urgeMoneyEx
									.setSplitResult(CounterofferResult.PAYMENT_FAILED
											.getCode());
						}
						try{
						// 更新拆分表
						 grantDeductsService.updUrgeSplit(urgeMoneyEx);
						 parameter.append("'" + lst.get(i).getEnterpriseSerialno() + "',");
						}catch(Exception e){
						}
					}
					// 更新划扣主表 t_cj_urge_services_amount
					enterpriseSerialno = parameter.toString().substring(0,
							parameter.lastIndexOf(","));
					List<CarUrgeServicesMoney> updUrgeServices = grantDeductsService
							.selSuccess(enterpriseSerialno);
					for (int i = 0; i < updUrgeServices.size(); i++) {
						// 失败笔数大于0
						if (updUrgeServices.get(i).getSuccessAmount() > 0) {
							updUrgeServices.get(i).setDictDealStatus(CounterofferResult.PAYMENT_FAILED
											.getCode());
						} else {
							updUrgeServices.get(i).setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
						}
						updUrgeServices.get(i).setUrgeDecuteDate(new Date());
						try{
							grantDeductsService.updateUrge(updUrgeServices.get(i));
						}catch(Exception e){
						}
					}
				}
			return "redirect:" + adminPath
					+ "/car/grant/grantDeducts/deductsInfo?result=" + result
					+ "&returnUrl=" + returnUrl;
			} else {
				return BooleanType.FALSE;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BooleanType.TRUE;
	}

	/**
	 * 上传中金，导入回盘结果,根据交易结果进行判断是否成功，
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl
	 *            要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "tlImportResult")
	public String tlImportResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file,
			String returnUrl, String result) {
		try {
			if (ExcelMatch.matchResult(file, CarUrgeServiceTLInputEx.class)) {
				ExcelUtils excelutil = new ExcelUtils();
				List<CarUrgeServiceTLInputEx> lst = new ArrayList<CarUrgeServiceTLInputEx>();
				String tracStatus = null;
				String enterpriseSerialno = null;
				StringBuilder parameter = new StringBuilder();
				List<?> datalist = excelutil
						.importExcel(file, 0, 0,
								CarUrgeServiceTLInputEx.class,null);
				lst = (List<CarUrgeServiceTLInputEx>) datalist;
				// 获得列表中的单子的信息，进行更新,根据企业流水号，
				if (ArrayHelper.isNotEmpty(lst)) {
					for (int i = 0; i < lst.size(); i++) {
						CarUrgeServicesMoneyEx urgeMoneyEx = new CarUrgeServicesMoneyEx();
						// 获得表格中的交易状态
						tracStatus = lst.get(i).getDictDealStatus();
						// 获得表格中的企业流水号
						urgeMoneyEx
								.setEnterpriseSerialno(lst.get(i).getEnterpriseSerialno());
						// 设置划扣标识
						urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());
						// 获得原因
						urgeMoneyEx.setSplitFailResult(lst.get(i).getReason());
						// 设置回盘时间
						urgeMoneyEx.setSplitBackDate(new Date());
						if (tracStatus.equals("成功")) {
							urgeMoneyEx
									.setSplitResult(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
						} else {
							urgeMoneyEx
									.setSplitResult(CounterofferResult.PAYMENT_FAILED
											.getCode());
						}
						// 更新拆分表
						try{
							grantDeductsService.updUrgeSplit(urgeMoneyEx);
							parameter.append("'" + lst.get(i).getEnterpriseSerialno() + "',");
						}catch(Exception e){
						}
					}
					// 更新划扣主表 t_cj_urge_services_amount
					enterpriseSerialno = parameter.toString().substring(0,
							parameter.lastIndexOf(","));
					List<CarUrgeServicesMoney> updUrgeServices = grantDeductsService
							.selSuccess(enterpriseSerialno);
					for (int i = 0; i < updUrgeServices.size(); i++) {
						// 失败笔数大于0
						if (updUrgeServices.get(i).getSuccessAmount() > 0) {
							updUrgeServices.get(i).setDictDealStatus(CounterofferResult.PAYMENT_FAILED
											.getCode());
						} else {
							updUrgeServices.get(i).setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED
											.getCode());
						}
						updUrgeServices.get(i).setUrgeDecuteDate(new Date());
						try{
							grantDeductsService.updateUrge(updUrgeServices.get(i));
						}catch(Exception e){
						}
					}
				}
			return "redirect:" + adminPath
					+ "/car/grant/grantDeducts/deductsInfo?result=" + result
					+ "&returnUrl=" + returnUrl;
			}else {
				return BooleanType.FALSE;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 上传中金，导入回盘结果,根据交易结果进行判断是否成功
	 * @param request
	 * @param response
	 * @param file
	 * @param returnUrl
	 *            要进行跳转的页面
	 * @return 处理之后进行重定向
	 */
	@ResponseBody
	@RequestMapping(value = "manualSure")
	public String manualSure(String cid,String deductsType,String splitBackResult,String splitFailResult,String result){
		 
		int flag = 0;
		CarUrgeServicesMoneyEx urgeEx = new CarUrgeServicesMoneyEx();
		urgeEx.setSplitResult(CounterofferResult.PAYMENT_FAILED.getCode());
			String[] cids = null;
			
			if (StringUtils.isNotEmpty(cid)) {
				if (cid.indexOf(",") != CarGrantCommon.ONE) {
					cids = cid.split(",");
				} else {
					cids = new String[1];
					cids[0] = cid;
				}
				
				for (int i = 0; i < cids.length; i++) {
					urgeEx.setUrgeId("'"+cids[i]+"'");
					urgeEx.setSplitResult(CounterofferResult.PROCESSED.getCode());
					List<CarUrgeServicesMoneyEx> delList = grantDeductsService.selProcess(urgeEx);

					// 更新划扣主表 t_cj_urge_services_amount
					// 用于更新的实体类
					// 用于查询的实体类
					CarUrgeServicesMoneyEx carUrgeServicesMoneyEx = new CarUrgeServicesMoneyEx();
					carUrgeServicesMoneyEx.setUrgeId(cids[i]);
					carUrgeServicesMoneyEx.setSplitResult(CounterofferResult.PROCESSED.getCode());
					List<CarUrgeServicesMoneyEx> urgeList = grantDeductsService.selectUrgeListNo(carUrgeServicesMoneyEx);
					try{
						grantDeductsService.manualSure(delList, splitBackResult, splitFailResult, i, urgeList, BEFORE, result);
						//手动确认划扣修改划扣平台
						if(CounterofferResult.PAYMENT_SUCCEED.equals(splitBackResult)){//划扣成功
							CarUrgeServicesMoney updateMoney = new CarUrgeServicesMoney();
							updateMoney.setId("'"+urgeList.get(0).getUrgeId()+"'");
							updateMoney.setDictDealType(deductsType);
							grantDeductsService.updateUrge(updateMoney);
							
						}
						flag++;
					}catch(Exception e){}
				}
			}
			
			if (flag > 0) {
				return BooleanType.TRUE;
			} else {
				return BooleanType.FALSE;
			}
	}
	
	/**
	 * 线上划扣方法
	 * @param cid 催收id
	 * @param deductsType 划扣平台的名称 
	 * @param result 当日以往的标识区分
	 * @param urgeServicesMoneyEx 表单序列化
	 * @return 划扣结果
	 */
	@ResponseBody
	@RequestMapping(value = "onlineGrantDeducts")
	public String onlineGrantDeducts(CarUrgeServicesMoneyEx urgeServicesMoneyEx,String cid,String deductsType,String result) {
		String[] id = null;
		String rule = "";
		String message = "";
		String returnMes = "";
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		StringBuilder parameter = new StringBuilder();
		if (result.equals(BEFORE)) {
			// 如果为以往待划扣,设置时间标识为是
			urgeServicesMoneyEx.setTimeFlag(YESNO.YES.getCode());
		} else {
			urgeServicesMoneyEx.setTimeFlag(YESNO.NO.getCode());
		}
		// 获得划扣规则
		if (DeductPlat.HAOYILIAN.getCode().equals(deductsType)) {
			rule = DeductPlat.HAOYILIAN.getCode() +":"+DeductTime.RIGHTNOW.getCode() ;
		}else if(DeductPlat.ZHONGJIN.getCode().equals(deductsType)){
			rule = DeductPlat.ZHONGJIN.getCode() +":"+DeductTime.RIGHTNOW.getCode() ;
		}else if(DeductPlat.TONGLIAN.getCode().equals(deductsType)){
			rule = DeductPlat.TONGLIAN.getCode() +":"+DeductTime.RIGHTNOW.getCode() ;
		}else if(DeductPlat.FUYOU.getCode().equals(deductsType)){
			rule = DeductPlat.FUYOU.getCode() +":"+DeductTime.RIGHTNOW.getCode() ;
		}
		// 对选中的数据进行处理
		if (StringUtils.isNotEmpty(cid)) {
			id = cid.split(",");
		} else {
			// 默认将查询条件下的所有为失败的进行发送划扣
			List<CarUrgeServicesMoneyEx> urgeList = grantDeductsService
					.selectUrgeListNo(urgeServicesMoneyEx);
			if (ArrayHelper.isNotEmpty(urgeList)) {
				id = new String[urgeList.size()];
				for (int i = 0; i < urgeList.size(); i++) {
					id[i] = urgeList.get(i).getUrgeId();
				}
			}
		}
		if (ArrayHelper.isNotNull(id)) {
			String idString = null;
			CarUrgeServicesMoneyEx queryCarUrge = new CarUrgeServicesMoneyEx();
			for (int i = 0; i < id.length; i++) {
				parameter.append("'" + id[i] + "',");
			}
			idString = parameter.toString();
			idString = idString.substring(0, parameter.lastIndexOf(","));
			queryCarUrge.setUrgeIds(idString);
			queryCarUrge.setSplitBackResult("'"+UrgeCounterofferResult.PAYMENT_FAILED.getCode()+"','"+UrgeCounterofferResult.PREPAYMENT.getCode()+"','"+UrgeCounterofferResult.PROCESSED.getCode()+"'");
			List<CarUrgeServicesMoneyEx> queryUrgeList = grantDeductsService.selectUrgeListNo(queryCarUrge);
			if (ArrayHelper.isNotEmpty(queryUrgeList) && queryUrgeList.size() > 0) {
				for (CarUrgeServicesMoneyEx carurgeItem : queryUrgeList) {
					try {
						returnMes = grantDeductsService.onlineGrantDeducts(carurgeItem, rule,result,deductsType, BEFORE);
						if (StringUtils.isNotEmpty(returnMes)) {
							failedContractCode = carurgeItem.getContractCode();
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("车借催收服务费发送划扣失败",e);
						failedContractCode = carurgeItem.getContractCode();
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
			}else {
				message = "没有要发送的数据";
			}
		}
		return message;
	
	}
	
	/**
	 * 停止或开启滚动划扣方法
	 * @param sysValue 
	 * @return BooleanType
	 */
	@ResponseBody
	@RequestMapping(value = "changeDeductsRule")
	public String changeDeductsRule(String sysValue) {
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		//t_cj_urge_services_amount deduct_jump_rule  update  auto_deduct_flag 0，1
		// 车借服务费自动划扣标识
		hashMap.put("sysFlag", SystemSetFlag.CAR_SERVICES_SETTING_FLAG);
		hashMap.put("sysValue", sysValue);
		grantDeductsService.changeDeductsRule(hashMap);

		return BooleanType.TRUE;
	}
	
	
	
	/**
	 * 根据查询条件查询要进行处理的list
	 * 2016年3月1日
	 * @param urgeServicesMoneyEx 查询条件
	 * @param result 判断为哪个列表
	 * @return 符合条件的list
	 */
	public List<CarUrgeServicesMoneyEx> selDealList(CarUrgeServicesMoneyEx urgeServicesMoneyEx,String result){
		List<CarUrgeServicesMoneyEx> urgeList = null;
		if (result.equals(BEFORE)) {
			// 如果为以往待划扣,设置时间标识为是
			urgeServicesMoneyEx.setTimeFlag(YESNO.YES.getCode());
		}else{
			urgeServicesMoneyEx.setTimeFlag(YESNO.NO.getCode());
		}
		urgeList = grantDeductsService.selectUrgeListNo(urgeServicesMoneyEx);
		return urgeList;
	}
	
	
	//导出划扣已办列表（部分）
	@RequestMapping(value="exportExcel2")
	public void exportExcel2(HttpServletRequest request , HttpServletResponse response , CarLoanStatusHisEx carLoanStatusHisEx,String idVal){
		ExcelUtils excelutil = new ExcelUtils();
		Page<CarLoanStatusHisEx>  page = null;
		List<DrawDoneEx> list = new ArrayList<DrawDoneEx>();
		String isQueryAll = YESNO.NO.getCode();
		String isIn = YESNO.YES.getCode();
		String nodeValListStr = CarLoanSteps.CURRENT_DEDUCTS.getCode()+","
		+CarLoanSteps.PAST_DEDUCTS.getCode()+","
		+CarLoanSteps.STORE_DEDUCTS.getCode();
		//设置为导出excel表
		carLoanStatusHisEx.setIsAllData("1");
		if(idVal == null || "".equals(idVal)){
			//导出所有的已划扣数据
			carLoanStatusHisEx.setIsAllData("1");
			page = carHistoryService.findDrawDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, null);
			int i=0;
			for (CarLoanStatusHisEx c : page.getList()) {
				DrawDoneEx drawDoneEx = new DrawDoneEx();
				ReflectHandle.copy(c, drawDoneEx);
				drawDoneEx.setMidBankName(DictCache.getInstance().getDictLabel("jk_open_bank", drawDoneEx.getMidBankName()));
				drawDoneEx.setDictDealStatus(DictCache.getInstance().getDictLabel("jk_counteroffer_result", drawDoneEx.getDictDealStatus()));
				drawDoneEx.setDictDealType(DictCache.getInstance().getDictLabel("jk_deduct_plat", drawDoneEx.getDictDealType()));
				drawDoneEx.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", page.getList().get(i).getLoanFlag()));
				drawDoneEx.setUrgeMoeny(page.getList().get(i).getUrgeMoeny().doubleValue());
				drawDoneEx.setFirstServiceRate( page.getList().get(i).getFirstServiceTariffing()==null?"0%":page.getList().get(i).getFirstServiceTariffing().toString()+"%");
				drawDoneEx.setInterestMoeny(Double.parseDouble(page.getList().get(i).getInterestAmount() + ""));
				drawDoneEx.setOuterVisitMoeny(Double.parseDouble(page.getList().get(i).getOutVisitFee() + ""));//外放费
				i++;
				list.add(drawDoneEx);
			}
		}else{
			//导出选中的已划扣数据
			String[] id = null;
			id = idVal.split(",");
			for (int i = 0; i < id.length; i++) {
				carLoanStatusHisEx.setApplyId(id[i]);
				page = carHistoryService.findDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, null);
				DrawDoneEx drawDoneEx = new DrawDoneEx();
				ReflectHandle.copy(page.getList().get(0), drawDoneEx);
				drawDoneEx.setMidBankName(DictCache.getInstance().getDictLabel("jk_open_bank", drawDoneEx.getMidBankName()));
				drawDoneEx.setDictDealStatus(DictCache.getInstance().getDictLabel("jk_counteroffer_result", drawDoneEx.getDictDealStatus()));
				drawDoneEx.setDictDealType(DictCache.getInstance().getDictLabel("jk_deduct_plat", drawDoneEx.getDictDealType()));
				drawDoneEx.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", page.getList().get(0).getLoanFlag()));
				drawDoneEx.setUrgeMoeny(page.getList().get(0).getUrgeMoeny().doubleValue());
				drawDoneEx.setFirstServiceRate( page.getList().get(0).getFirstServiceTariffing()==null?"0%":page.getList().get(0).getFirstServiceTariffing().toString()+"%");
				drawDoneEx.setInterestMoeny(Double.parseDouble(page.getList().get(0).getInterestAmount() + ""));
				drawDoneEx.setOuterVisitMoeny(Double.parseDouble(page.getList().get(0).getOutVisitFee() + ""));//外放费
				list.add(drawDoneEx);
			}
		}
		excelutil.exportExcel(list, FileExtension.CAR_URGE_DONE_LIST,
		DrawDoneEx.class, FileExtension.XLSX,
		FileExtension.OUT_TYPE_TEMPLATE, response, null);
	}
	
	//导出还款匹配已办列表（部分）
		@RequestMapping(value="exportExcel3")
		public void exportExcel3(HttpServletRequest request , HttpServletResponse response , CarLoanStatusHisEx carLoanStatusHisEx,String idVal){
			ExcelUtils excelutil = new ExcelUtils();
			Page<CarLoanStatusHisEx>  page = null;
			List<CheckDoneEx> list = new ArrayList<CheckDoneEx>();
			String isQueryAll = YESNO.NO.getCode();
			String isIn = YESNO.YES.getCode();
			String nodeValListStr = CarLoanSteps.CURRENT_DEDUCTS.getCode()+","
			+CarLoanSteps.PAST_DEDUCTS.getCode()+","
			+CarLoanSteps.STORE_DEDUCTS.getCode();
			//设置为导出excel表
			carLoanStatusHisEx.setIsAllData("1");
			if("".equals(idVal) || idVal == null){
				//导出所有的还款匹配数据
				page = carHistoryService.findCheckDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, null);
				for (CarLoanStatusHisEx c : page.getList()) {
					CheckDoneEx checkDoneEx = new CheckDoneEx();
					ReflectHandle.copy(c, checkDoneEx);
					list.add(checkDoneEx);
				}
			}else{
				//导出选中的还款匹配数据
				String[] id = null;
				id = idVal.split(",");
				for (int i = 0; i < id.length; i++) {
					CheckDoneEx checkDoneEx = new CheckDoneEx();
					carLoanStatusHisEx.setApplyId(id[i]);
					page = carHistoryService.findCheckDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, null);
					if(null !=page && page.getList()!=null && page.getList().size()>0){
						ReflectHandle.copy(page.getList().get(0), checkDoneEx);
						checkDoneEx.setConditionalThroughFlag(DictCache.getInstance().getDictLabel("jk_car_loan_result", checkDoneEx.getConditionalThroughFlag()));
						checkDoneEx.setLoanStatusCode(DictCache.getInstance().getDictLabel("jk_car_loan_status", checkDoneEx.getLoanStatusCode()));
						list.add(checkDoneEx);
					}
				}
			}
			excelutil.exportExcel(list, FileExtension.CJ_CHECK_DONE,
			CheckDoneEx.class, FileExtension.XLSX,
			FileExtension.OUT_TYPE_TEMPLATE, response, null);
		}
	
	//导出划扣已办列表
	@RequestMapping(value="{step:[a-zA-Z]+}DoneList")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,CarLoanStatusHisEx carLoanStatusHisEx, @PathVariable String step) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String storeCodes = carLoanStatusHisEx.getStoreCode();
		String[] storeCode = null;
		List<String> stroeCodeList = new ArrayList<String>();
		if(storeCodes != null){
			if(storeCodes.indexOf(",")!=-1){
				storeCode = storeCodes.split(",");
			} else{
				storeCode = new String[1];
				storeCode[0] = storeCodes;
			}
			for (String c : storeCode) {
				stroeCodeList.add(c);
			}
			carLoanStatusHisEx.setStoreCodeList(stroeCodeList);
		}
		// 若设置carLoanStatusHisEx.setFilterUser(UserUtils.getUser().getId())，则表示值查询个人已办，否则为所有人的已办
		
		// 是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
		String isQueryAll = null;
		
		// in前是否加not（仅限操作节点查询前），为1，则不加，其他，则在in前加not
		String isIn = null;
		
		// 节点 列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限操作节点查询前），列出要查询或者要摒弃的节点code值，见CarLoanSteps枚举
		String nodeValListStr = null;

		// 是否只筛选通过的记录，为1，则是，其他，则否
		String filterGross = null;
		
		if ("carLoanApply".equals(step)) {
			carLoanStatusHisEx.setFilterUser(UserUtils.getUser().getId()); // 设置个人已办
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.NO.getCode();
			// 去除咨询已办
			nodeValListStr = CarLoanSteps.CONSULTATION.getCode( ) + "," + CarLoanSteps.APPRAISER.getCode();
		} else if ("contractPro".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CONTRACT_PRODUCT.getCode();
			filterGross = YESNO.YES.getCode();
		} else if ("carLoanData".equals(step)) {
			isQueryAll = YESNO.YES.getCode();
		} else if ("carLoanSuccess".equals(step)){
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.GRANT.getCode();
		} else if ("draw".equals(step)) {//划扣已办查询条件
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CURRENT_DEDUCTS.getCode()+","
					+CarLoanSteps.PAST_DEDUCTS.getCode()+","
					+CarLoanSteps.STORE_DEDUCTS.getCode();
		}else if ("check".equals(step)) {//还款匹配已办查询条件
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CURRENT_DEDUCTS.getCode()+","
					+CarLoanSteps.PAST_DEDUCTS.getCode()+","
					+CarLoanSteps.STORE_DEDUCTS.getCode();
		} 
		else if ("carRefundCheck".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.REFUND_AUDIT.getCode();
		} else if ("customerManagement".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CONSULTATION.getCode()+","
					+CarLoanSteps.APPRAISER.getCode()+","
					+CarLoanSteps.FACE_AUDIT_APPLY.getCode();
		}
		//得到queryMap
		Map<String,Object> queryMap = getQueryMap(carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		if("draw".equals(step)){
			ExportDrawHelper.exportData(queryMap, response);
		}if("check".equals(step)){//还款匹配已办列表导出
			ExportDrawHelper.exportDataCheck(queryMap, response);
		}
		else if ("carLoanSuccess".equals(step)){
			ExportLoanSuccessHelper.exportData(queryMap, response);
		}
		
	}
	//导出得到queryMap
	@SuppressWarnings("unchecked")
	private Map<String,Object> getQueryMap(CarLoanStatusHisEx carLoanStatusHisEx, String isIn, String isQueryAll, String nodeValueLists, String grossFlag){
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists) && nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);
		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		Map<String,Object> filter = JsonMapper.nonDefaultMapper().convertValue(carLoanStatusHisEx, Map.class);
		return filter;
	}
	
}
	
