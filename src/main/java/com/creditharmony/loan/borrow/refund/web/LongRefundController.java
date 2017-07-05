package com.creditharmony.loan.borrow.refund.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.AppStatus;
import com.creditharmony.core.loan.type.AppType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.refund.entity.AlreadyRefundExportExcel;
import com.creditharmony.loan.borrow.refund.entity.ExamineExportExcel;
import com.creditharmony.loan.borrow.refund.entity.Refund;
import com.creditharmony.loan.borrow.refund.entity.RefundExportExcel;
import com.creditharmony.loan.borrow.refund.entity.RefundImportExcel;
import com.creditharmony.loan.borrow.refund.entity.RefundServiceFee;
import com.creditharmony.loan.borrow.refund.service.LongRefundService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.PaymentSplitService;
import com.creditharmony.loan.common.service.SystemSetMaterService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;

/**
 * 退款
 * 
 * @Class Name GrantDeductsController
 * @author 
 * @Create In 2016年4月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/refund/longRefund")
public class LongRefundController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private LongRefundService longRefundService;
	@Autowired
	private PaymentSplitService paymentSplitService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private HistoryService historyService;
    @Autowired
    private SystemSetMaterService systemSetMaterService;  
    @Autowired 
	private PaybackBlueAmountService blusAmountService;
    @Autowired 
    private ThreePartFileName threePartFileName;
    @Autowired
	private PaybackService applyPayService;
    
    private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);

	/**
	 * 退款(列表、数据管理部)
	 */
	@RequestMapping(value = "dataRefundInfo")
	public String dataRefundInfo(Model model, Refund refund,
			String result, HttpServletRequest request,
			HttpServletResponse response, String returnUrl) {
		if(refund.getAppStatus()==null){
			refund.setAppStatus(AppStatus.WAIT_RETURN.getCode());
		}/*else if("".equals(refund.getAppStatus())||"-1".equals(refund.getAppStatus())){
    			refund.setAppStatus("-1");
    	}else{
    		if("0".equals(refund.getAppStatus())||"1".equals(refund.getAppStatus())||
    					"2".equals(refund.getAppStatus())||"3".equals(refund.getAppStatus())){
    			refund.setAppStatus("?");
    		}
    	}*/
		//不显示zcj数据
		refund.setChannelFlag(ChannelFlag.ZCJ.getCode());
		//查询退款数据列表
		Page<Refund> urgePage = longRefundService
				.selectRefundList(
						new Page<Refund>(request, response),
						refund);
		Refund totalRefund = longRefundService.selectRefundTotal(refund);
		//转换字典值为中文名称
		if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for (Refund ex : urgePage.getList()) {
				ex.setAppTypeName(DictCache.getInstance().getDictLabelTemp("jk_app_type", ex.getAppType()));
				ex.setBankName(DictCache.getInstance().getDictLabelTemp("jk_open_bank", ex.getBankCode()));
				ex.setAppStatusName(DictCache.getInstance().getDictLabelTemp("jk_app_status", ex.getAppStatus()));
				ex.setLoanStatusName(DictCache.getInstance().getDictLabelTemp("jk_loan_apply_status", ex.getLoanStatus()));//jk_loan_status
			}
		}
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("totalMony",totalRefund.getRefundTotalMony());
		model.addAttribute("totalNum", totalRefund.getRefundTotalNum());
		model.addAttribute("refund", refund);
		model.addAttribute("urgeList", urgePage);
		return "borrow/refund/" + returnUrl;
	}

	/**
	 * 获取夏总账号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectMiddlePerson")
	public List<String> selectMiddlePerson(HttpServletRequest request){
		List<String> l = longRefundService.selectMiddlePerson("夏靖");
		return l;
	}
	
	/**
	 * 退款列表
	 * 操作-退票
	 */
	@RequestMapping("editRefundTicket")
	public String editRefundTicket(HttpServletRequest request,
			Refund refund,  HttpServletResponse response ) {
		try {
			longRefundService.editRefundTicket(refund);
		} catch (Exception e) {
			logger.error("退票失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath
				+ "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList";
	}

	/**
	 * 退款列表
	 * 批量退回
	 */
	@RequestMapping("batchReturn")
	public String batchReturn(HttpServletRequest request,
			Refund refund,  HttpServletResponse response ) {
		try {
			refund.setAppStatus(AppStatus.WAIT_RETURN.getCode());
			if(refund.getIds()!=null&&!"".equals(refund.getIds())){
				refund.setIdArray(refund.getIds().split(","));
			}
			/*Page<Refund> urgePage = longRefundService
					.selectRefundList(
							new Page<Refund>(request, response),
							refund);*/
			List<Refund> list = longRefundService.selectRefundById(refund);
			longRefundService.batchReturn(refund,list);
		} catch (Exception e) {
			logger.error("批量退回！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath
				+ "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList";
	}

	/**
	 * 退款列表
	 * 退款成功
	 */
	@RequestMapping("saveRefund")
	public String saveRefund(HttpServletRequest request,
			Refund refund,  HttpServletResponse response ) {
		try {
    		refund.setAppStatus(AppStatus.WAIT_RETURN.getCode());
			refund.setIds(refund.getIdsA());
			if(refund.getIdsA()!=null&&!"".equals(refund.getIdsA())){
				refund.setIdArray(refund.getIdsA().split(","));
				/*Page<Refund> urgePage = longRefundService
						.selectRefundList(
								new Page<Refund>(request, response),
								refund);*/
				List<Refund> list = longRefundService.selectRefundById(refund);
				longRefundService.saveRefund(refund,list);
			}
		} catch (Exception e) {
			logger.error("退款成功！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath
				+ "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList";
	}
	
	/**
	 * 退款列表
	 * 导出Excel
	 */
	@RequestMapping(value = "refundExl")
	public String refundExl(HttpServletRequest request,
			HttpServletResponse response,Refund refund) {
		ExcelUtils excelutil = new ExcelUtils();
		refund.setAppStatus(refund.getAppStatus());
		if(refund.getIds()!=null&&!"".equals(refund.getIds())){
			refund.setIdArray(refund.getIds().split(","));
		}
		List<RefundExportExcel> l= longRefundService.refundExportList(refund);
		String ifCanExport="1";
		for(RefundExportExcel re : l){
			if(re.getLoanStatus().equals("88")){
				ifCanExport="0";
				break;
			}
			re.setIncomeCounty(changeCountyAndCity(re.getIncomeCounty()));
			re.setIncomeCity(changeCountyAndCity(re.getIncomeCity()));
			re.setIncomeBank(changeBankName(re.getIncomeBank()));
		}
		// 如果已退款状态下，则允许直接导出
		if(!AppStatus.WAIT_RETURN.getCode().equals(refund.getAppStatus())) {
			ifCanExport="1";
		}
		// 导出富友平台list
		if(ifCanExport.equals("1")){
			excelutil.exportExcel(l,"退款导出",null,
			        RefundExportExcel.class, FileExtension.XLS,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
			return null;
		}else{
			return "redirect:" + adminPath
					+ "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList&ifCanExport="+ifCanExport;
		}
	}
	/**
	 * 已退款导出
	 * by huowenlong
	 * @param request
	 * @param response
	 * @param refundP
	 */
	@RequestMapping(value = "alreadyRefundDataExcel")
	public String  alreadyRefundDataExcel(HttpServletRequest request,
			HttpServletResponse response,Refund refund) {
		ExcelUtils excelutil = new ExcelUtils();
		refund.setAppStatus(AppStatus.HAS_RETURN.getCode());
		if(refund.getIds()!=null&&!"".equals(refund.getIds())){
			refund.setIdArray(refund.getIds().split(","));
		}
		List<AlreadyRefundExportExcel> l= longRefundService.alreadyRefundDataExcel(refund);
		//借款标识
		//String ifCanExport="1";
		for(AlreadyRefundExportExcel are : l){
			/*if(are.getLoanStatus().equals(LoanStatus.OVERDUE.getCode())){
				ifCanExport="0";
				break;
			}*/
			are.setIncomeBank(changeBankName(are.getIncomeBank()));
		}
		//if(ifCanExport.equals("1")){
			excelutil.exportExcel(l,"已退款导出",null,
					AlreadyRefundExportExcel.class, FileExtension.XLS,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
			return null;
		/*}
		return "redirect:" + adminPath
				+ "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList&ifCanExport="+ifCanExport;*/
	}
	/*@RequestMapping(value = "alreadyRefundDataExcel")
	public void alreadyRefundDataExcel(HttpServletRequest request,
			HttpServletResponse response,Refund refund) {
		ExcelUtils excelutil = new ExcelUtils();
		refund.setAppStatus(AppStatus.HAS_RETURN.getCode());
		if(refund.getIds()!=null&&!"".equals(refund.getIds())){
			refund.setIdArray(refund.getIds().split(","));
		}
		List<AlreadyRefundExportExcel> l= longRefundService.alreadyRefundDataExcel(refund);
		for(AlreadyRefundExportExcel are : l){
			
			are.setIncomeBank(changeBankName(are.getIncomeBank()));
		}
		excelutil.exportExcel(l,"已退款导出",null,
				AlreadyRefundExportExcel.class, FileExtension.XLS,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);
	}*/
	
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
	
	public String changeCountyAndCity(String str){
		if(str != null && !"".equals(str)){
			if( str.lastIndexOf("省") > 0 ){
				str = str.replace("省", "");
			}
			if( str.lastIndexOf("市")> 0){
				str = str.replace("市", "");
			}
		}
		return str;
	}

/**	****************************门店退款已办列表*********************************************\\
	/**
	 * 门店退款已办列表
	 * 门店
	 */
	@RequestMapping(value = "refundAlreadyList")
	public String refundAlreadyList(Model model, Refund refund,
			String result, HttpServletRequest request,
			HttpServletResponse response, String returnUrl) {
		//查询退款数据列表
		
		//数据权限控制
	    String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
	    refund.setQueryRight(queryRight);
		Page<Refund> urgePage = longRefundService
				.selectRefundList(
						new Page<Refund>(request, response),
						refund);
		//转换字典值为中文名称
		if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for (Refund ex : urgePage.getList()) {
				ex.setAppTypeName(DictCache.getInstance().getDictLabel("jk_app_type", ex.getAppType()));
				ex.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank", ex.getBankCode()));
				ex.setAppStatusName(DictCache.getInstance().getDictLabel("jk_app_status", ex.getAppStatus()));
				ex.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", ex.getLoanStatus()));//jk_loan_status
			}
		}
		model.addAttribute("refund", refund);
		model.addAttribute("urgeList", urgePage);
		return "borrow/refund/refundAlreadyList";
	}

	/**
	 * 放款失败催收服务费退款列表
	 * 2016年1月27日
	 * By WJJ
	 */
	@RequestMapping(value = "refundServiceFeeList")
	public String refundServiceFeeList(Model model,HttpServletRequest request,HttpServletResponse response,RefundServiceFee refundServiceFee) {
		//数据权限控制
	    String queryRight = DataScopeUtil.getDataScope("a", SystemFlag.LOAN.value);
		refundServiceFee.setQueryRight(queryRight);
		Page<RefundServiceFee> urgePage = longRefundService.getRfundServiceFeeList(new Page<RefundServiceFee>(request, response), refundServiceFee);
		if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for(RefundServiceFee sf :urgePage.getList()){
				sf.setLoanFlag(DictCache.getInstance().getDictLabel(LoanDictType.CHANNEL_FLAG, sf.getLoanFlag()));
				sf.setClassType(DictCache.getInstance().getDictLabel(LoanDictType.REPAY_TYPE, sf.getClassType()));
			}
		}
		//
		List<CityInfo> provinceList = cityManager.findProvince();
		// 省市初始化
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("grantUrgeList", urgePage);
		model.addAttribute("refundServiceFee", refundServiceFee);
		return "borrow/refund/refundServiceFeeList";
	}

	/**
	 * 退款列表
	 * 上传回执结果
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importExcel")
	public String importExcel(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) throws Exception{

			ExcelUtils excelutil = new ExcelUtils();
			List<RefundImportExcel> lst = new ArrayList<RefundImportExcel>();
			List<?> datalist = excelutil.importExcel(
					LoanFileUtils.multipartFile2File(file), 0, 0,
					RefundImportExcel.class);
			lst = (List<RefundImportExcel>) datalist;
			// 获得列表中的单子的信息，进行更新,根据企业流水号，
			if (ArrayHelper.isNotEmpty(lst)) {
				longRefundService.importExcel(lst);
			}

		return "redirect:" + adminPath
				+ "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList";
	}

	/**
	 * 初始化蓝补退款页面
	 * 2016年4月20日
	 * By WJJ
	 * @param model
	 * @param paybackId
	 * @param certNum
	 * @return String
	 */
    @RequestMapping(value = { "openRefundBlue" })
    public String openRefundBlue(Model model,String paybackId,String refundId) {
    	Payback payback=new Payback();
    	payback.setContractCode(paybackId);
    	
    	//List<Payback> paybackList = new ArrayList<Payback>();
		//paybackList = applyPayService.findPayback(payback);
		List<Map<String, Object>> paybackList = longRefundService.findPayback(paybackId,null);
//		if(!paybackList.isEmpty()){
//    		/*Payback pb = paybackList.get(0);
//    		LoanInfo li = pb.getLoanInfo();
//    		li.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_loan_apply_status", li.getDictLoanStatus()));
//    		pb.setLoanInfo(li);*/
//    		model.addAttribute("payback", paybackList.get(0));
//    	}else{
//    		model.addAttribute("payback", paybackList.get(0));
//    	}
		if(null != paybackList && paybackList.size() > 0) {
			// 划扣日当天申请退款,退款金额=蓝补余额-当期期供未还金额-未还违约金及罚息总额，且门店自行修改的退款金额不得大于此金额。
			// 非划扣日当天,退款金额 = 蓝补金额-逾期期供金额-未还违约金及罚息总额
			// 获取当天日
			String currentDay = DateUtils.getDay();
			HashMap<String, Object> mapObj = (HashMap<String, Object>) paybackList.get(0);
			Integer paybackDay = (Integer) mapObj.get("paybackday");
			// 如果是还款日当天
			if(Integer.valueOf(currentDay).intValue() == paybackDay.intValue()) {
				// 退款金额=蓝补余额-当期期供未还金额-未还违约金及罚息总额
				BigDecimal tkMoney = longRefundService.getTkMoney(paybackId);
				model.addAttribute("tkMoney",tkMoney);
				model.addAttribute("hkrdtFlag", "1");
			} else {
				// 退款金额 = 蓝补金额-逾期期供金额-未还违约金及罚息总额
				BigDecimal tkMoney = longRefundService.getTkMoney2(paybackId);
				model.addAttribute("tkMoney",tkMoney);
				model.addAttribute("hkrdtFlag", "0");
			}
		}
		model.addAttribute("payback", paybackList.get(0));
		Refund refund = new Refund();
		refund.setIdArray(new String[]{refundId});
    	List<Refund> list = longRefundService.selectRefundById(refund);
    	if(!list.isEmpty()){
    		model.addAttribute("refund", list.get(0));
    		List<CityInfo> cityList = cityManager.findCity(list.get(0).getIncomeCity());
    		model.addAttribute("cityList", cityList);
    	}
    	List<CityInfo> provinceList = cityManager.findProvince();
    	model.addAttribute("provinceList", provinceList);
		model.addAttribute("refundId", refundId);
        return "borrow/refund/RefundBlue";
    }
    
    /**
	 * 蓝补退款
	 * 2016年4月20日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleAmountLast
	 * @param paybackBuleReson
	 * @return String
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="saveRefundData")
	public String saveRefundData(Payback payback,BigDecimal paybackBuleAmountLast,String paybackBuleReson,String reson,String refundId,Refund refund,String mt) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		User user = UserUtils.getUser();
		boolean result = false;
		List<Map<String, Object>> paybackList = longRefundService.findPayback(payback.getContractCode(),mt);
		if(!paybackList.isEmpty()){
			BigDecimal paybackBuleAmount = new BigDecimal("0.00");
			Object object = paybackList.get(0).get("paybackbuleamount");
			if(object!=null){
				paybackBuleAmount = new BigDecimal(String.valueOf(object));
			}
			BigDecimal amount = payback.getPaybackBuleAmount();
			if(amount!=null){
				int r = amount.compareTo(BigDecimal.ZERO);
				if(r<=0){
					return BooleanType.FALSE;
				}
				paybackBuleAmountLast = paybackBuleAmount.subtract(amount); 
				double rs = paybackBuleAmountLast.doubleValue();
				if(rs<0){
					return BooleanType.FALSE;
				}
			}else{
				return BooleanType.FALSE;
			}
			
			if(paybackBuleAmountLast!=null){
				int r = paybackBuleAmountLast.compareTo(BigDecimal.ZERO);
				if(r<0){
					return BooleanType.FALSE;
				}
			}
			if("".equals(refundId)){
				
			List<Map<String,Object>> list = longRefundService.getInfo(payback.getContractCode());
			refund.setId(IdGen.uuid());
			refund.setContractCode(payback.getContractCode());
			refund.setMoney(paybackBuleAmountLast);
			refund.setRefundMoney(payback.getPaybackBuleAmount());
			refund.setAppType(AppType.BLUE_RETURN.getCode());
			refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
			refund.setRemark(reson);
			refund.setCreateBy(user.getId());//操作人
			refund.setModifyBy(user.getId());//操作人
			if(!list.isEmpty()){
				if(list.get(0).get("loanteamorgid")!=null){
					refund.setMendianId(String.valueOf(list.get(0).get("loanteamorgid")));//门店t_jk_loan_info.loan_team_orgid
				}
				if(list.get(0).get("loancustomername")!=null){
					refund.setCustomerName(String.valueOf(list.get(0).get("loancustomername")));//借款人t_jk_loan_info.loan_customer_name
				}
				if(list.get(0).get("loanapplyamount")!=null){
					refund.setLoanMoney(new BigDecimal(String.valueOf(list.get(0).get("loanapplyamount"))));//借款金额t_jk_loan_info.loan_apply_amount
				}
				if(list.get(0).get("dictloanstatus")!=null){
					refund.setLoanStatus(String.valueOf(list.get(0).get("dictloanstatus")));//借款状态t_jk_loan_info.dict_loan_status
				}
				if(list.get(0).get("bankname")!=null){
					refund.setBankCode(String.valueOf(list.get(0).get("bankname")));//开户行t_jk_loan_bank.bank_name
				}
				/*if(list.get(0).get("bankbranch")!=null){
					refund.setRefundBank(String.valueOf(list.get(0).get("bankbranch")));//退款银行t_jk_loan_bank.bank_branch
				}*/
				if(list.get(0).get("customercertnum")!=null){
					refund.setCertNum(String.valueOf(list.get(0).get("customercertnum")));//证件号t_jk_contract.loan_cert_num
				}
			}
			
			
			result = longRefundService.refundPaybackBlue(payback,paybackBuleAmountLast,paybackBuleReson,refund);
		}else{
			refund.setId(refundId);
			refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
			refund.setRemark(reson);
			refund.setFkResult("");
			refund.setFkReason("");
			refund.setFkRemark("");
			refund.setZcResult("");
			refund.setZcReason("");
			refund.setZcRemark("");
			refund.setMoney(paybackBuleAmountLast);
			refund.setRefundMoney(payback.getPaybackBuleAmount());
			refund.setCreateTimes(new Date());
			refund.setModifyTimes(new Date());
			refund.setCreateBy(user.getId());
			refund.setModifyBy(user.getId());
			refund.setModifyTime(new Date());
			result = longRefundService.updateRefundServiceFee(refund);
		}
		}
		if(result){
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	
	/**
	 * 初始化催收服务费退款页面
	 * 2016年4月20日
	 * By WJJ
	 * @param model
	 * @param paybackId
	 * @param certNum
	 * @return String
	 */
    @RequestMapping(value = { "openRefundServiceFee" })
    public String openRefundServiceFee(Model model,String paybackId,String refundId,String urgeId,String chargeId) {
    	Payback payback=new Payback();
    	payback.setContractCode(paybackId);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(new Date());
    	List<Map<String, Object>> paybackList = longRefundService.findPayback(paybackId,null);
		if(!paybackList.isEmpty()){
    		/*Payback pb = paybackList.get(0);
    		LoanInfo li = pb.getLoanInfo();
    		li.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_loan_apply_status", li.getDictLoanStatus()));
    		pb.setLoanInfo(li);*/
    		model.addAttribute("payback", paybackList.get(0));
    	}else{
    		model.addAttribute("payback", null);
    	}
		Refund refund = new Refund();
		refund.setIdArray(new String[]{refundId});
    	List<Refund> list = longRefundService.selectRefundById(refund);
    	if(!list.isEmpty()){
    		model.addAttribute("refund", list.get(0));
    		List<CityInfo> cityList = cityManager.findCity(list.get(0).getIncomeCity());
    		model.addAttribute("cityList", cityList);
    	}
    	List<CityInfo> provinceList = cityManager.findProvince();
    	model.addAttribute("provinceList", provinceList);
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("refundId", refundId);
		model.addAttribute("urgeId", urgeId);
		model.addAttribute("chargeId", chargeId);
        return "borrow/refund/RefundServiceFee";
    }
    
    /**
	 * 催收服务费退款
	 * 2016年4月20日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleAmountLast
	 * @param paybackBuleReson
	 * @return String
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody
	@RequestMapping(value="saveRefundServiceFee")
	public String saveRefundServiceFee(Refund refund,BigDecimal grantAmount,BigDecimal backAmount,String reson,String refundId) throws IllegalAccessException, InvocationTargetException, IllegalArgumentException, SecurityException, NoSuchMethodException{
		User user = UserUtils.getUser();
		boolean result = false;
		List serviceInfo = longRefundService.getServiceFeeInfo(refund.getContractCode(), AppType.URGE_RETURN.getCode(), AppStatus.FIRST_CHECK.getCode());
		if(serviceInfo.isEmpty()){
			if("".equals(refundId)){
				List<Map<String,Object>> list = longRefundService.getInfo(refund.getContractCode());
				refund.setId(IdGen.uuid());
				refund.setMoney(grantAmount);
				refund.setRefundMoney(backAmount);
				refund.setAppType(AppType.URGE_RETURN.getCode());
				refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
				refund.setRemark(reson);
				refund.setCreateBy(user.getId());//操作人
				refund.setModifyBy(user.getId());//操作人
				if(!list.isEmpty()){
					if(list.get(0).get("loanteamorgid")!=null){
						refund.setMendianId(String.valueOf(list.get(0).get("loanteamorgid")));//门店t_jk_loan_info.loan_team_orgid
					}
					if(list.get(0).get("loancustomername")!=null){
						refund.setCustomerName(String.valueOf(list.get(0).get("loancustomername")));//借款人t_jk_loan_info.loan_customer_name
					}
					if(list.get(0).get("loanapplyamount")!=null){
						refund.setLoanMoney(new BigDecimal(String.valueOf(list.get(0).get("loanapplyamount"))));//借款金额t_jk_loan_info.loan_apply_amount
					}
					if(list.get(0).get("dictloanstatus")!=null){
						refund.setLoanStatus(String.valueOf(list.get(0).get("dictloanstatus")));//借款状态t_jk_loan_info.dict_loan_status
					}
					if(list.get(0).get("bankname")!=null){
						refund.setBankCode(String.valueOf(list.get(0).get("bankname")));//开户行t_jk_loan_bank.bank_name
					}
					/*if(list.get(0).get("bankbranch")!=null){
						refund.setRefundBank(String.valueOf(list.get(0).get("bankbranch")));//退款银行t_jk_loan_bank.bank_branch
					}*/
					if(list.get(0).get("customercertnum")!=null){
						refund.setCertNum(String.valueOf(list.get(0).get("customercertnum")));//证件号t_jk_contract.loan_cert_num
					}
				}
				
				
				result = longRefundService.refundServiceFee(refund);
			}else{
				refund.setId(refundId);
				refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
				refund.setRemark(reson);
				refund.setFkResult("");
				refund.setFkReason("");
				refund.setFkRemark("");
				refund.setZcResult("");
				refund.setZcReason("");
				refund.setZcRemark("");
				refund.setCreateTimes(new Date());
				refund.setModifyTimes(new Date());
				refund.setCreateBy(user.getId());
				refund.setModifyBy(user.getId());
				result = longRefundService.updateRefundServiceFee(refund);
			}
		}
		
		
		if(result){
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	
	/**
	 * 退款待初审页面
	 * 2016年4月20日
	 * By WJJ
	 */
    @RequestMapping(value = { "firstList" })
    public String firstList(Model model,Refund refund, HttpServletRequest request,
			HttpServletResponse response) {
    	if(refund.getAppStatus()==null){
    		refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
    	}
    	Page<Refund> urgePage = longRefundService
				.selectRefundList(
						new Page<Refund>(request, response),
						refund);
		if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for (Refund ex : urgePage.getList()) {
				ex.setAppTypeName(DictCache.getInstance().getDictLabel("jk_app_type", ex.getAppType()));
				ex.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank", ex.getBankCode()));
				ex.setAppStatusName(DictCache.getInstance().getDictLabel("jk_app_status", ex.getAppStatus()));
				ex.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", ex.getLoanStatus()));//jk_loan_status
				// 渠道
				ex.setLoanFlagLabel(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getLoanFlag()));
				// 模式
				String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel());
				ex.setModelLabel(dictLoanModel);
			}
		}
		model.addAttribute("search", refund);
		model.addAttribute("items", urgePage);
    	
        return "borrow/refund/firstList";
    }
    
    /**
	 * 退款查看
	 * 2016年4月21日
	 * By WJJ
	 */
    @RequestMapping(value = { "view" })
    public String view(Model model,Refund refund) {
    	//Payback payback=new Payback();
    	//payback.setContractCode(refund.getContractCode());
    	
    	//List<Payback> paybackList = applyPayService.findPayback(payback);
    	List<Map<String, Object>> paybackList = longRefundService.findPayback(refund.getContractCode(),null);
    	if(!paybackList.isEmpty()){
    		/*Payback pb = paybackList.get(0);
    		LoanInfo li = pb.getLoanInfo();
    		li.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_loan_apply_status", li.getDictLoanStatus()));
    		pb.setLoanInfo(li);*/
    		model.addAttribute("payback", paybackList.get(0));
    	}
    	refund.setIdArray(new String[]{refund.getId()});
    	List<Refund> list = longRefundService.selectRefundById(refund);
    	if(!list.isEmpty()){
    		Refund r = new Refund();
    		r = list.get(0);
    		r.setAppTypeName(DictCache.getInstance().getDictLabel("jk_app_type", r.getAppType()));
			r.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank", r.getBankCode()));
			r.setAppStatusName(DictCache.getInstance().getDictLabel("jk_app_status", r.getAppStatus()));
			r.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", r.getLoanStatus()));//jk_loan_status
    		model.addAttribute("refund", r);
    	}
    	if("0".equals(refund.getAppType())){
            return "borrow/refund/refundBlueView";
    	}else{
            return "borrow/refund/refundView";
    	}
    }
    
    /**
	 * 初审页面
	 * 2016年4月21日
	 * By WJJ
	 */
    @RequestMapping(value = { "refundExaminePage" })
    public String refundExaminePage(Model model,Refund refund,String type,String mt) {
    	//Payback payback=new Payback();
    	//payback.setContractCode(refund.getContractCode());
    	
    	//List<Payback> paybackList = applyPayService.findPayback(payback);
    	List<Map<String, Object>> paybackList = longRefundService.findPayback(refund.getContractCode(),null);
    	if(!paybackList.isEmpty()){
    		/*Payback pb = paybackList.get(0);
    		LoanInfo li = pb.getLoanInfo();
    		li.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_loan_apply_status", li.getDictLoanStatus()));
    		pb.setLoanInfo(li);*/
    		model.addAttribute("payback", paybackList.get(0));
    	}
    	refund.setIdArray(new String[]{refund.getId()});
    	List<Refund> list = longRefundService.selectRefundById(refund);
    	if(!list.isEmpty()){
    		Refund r = new Refund();
    		r = list.get(0);
    		r.setAppTypeName(DictCache.getInstance().getDictLabel("jk_app_type", r.getAppType()));
			r.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank", r.getBankCode()));
			r.setAppStatusName(DictCache.getInstance().getDictLabel("jk_app_status", r.getAppStatus()));
			r.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", r.getLoanStatus()));//jk_loan_status
    		model.addAttribute("refund", r);
    	}
    	model.addAttribute("modifyTime", mt);
    	if("end".equals(type)){
    		if(AppType.BLUE_RETURN.getCode().equals(refund.getAppType())){
                return "borrow/refund/refundBlueExamineEnd";
        	}else{
                return "borrow/refund/refundExamineEnd";
        	}
    	}else{
    		if(AppType.BLUE_RETURN.getCode().equals(refund.getAppType())){
                return "borrow/refund/refundBlueExamine";
        	}else{
                return "borrow/refund/refundExamine";
        	}
    	}
    	
    }
    
    /**
	 * 初审
	 * 2016年4月21日
	 * By WJJ
	 */
    @ResponseBody
    @RequestMapping(value = { "refundExamine" })
    public String refundExamine(Model model,Refund refund,String type) {
    	boolean result = false;
    	User user = UserUtils.getUser();
    	refund.setModifyBy(user.getId());
    	refund.setCreateBy(user.getId());
    	refund.setModifyTime(new Date());
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		if(refund.getStartTime()!=null && !"".equals(refund.getStartTime()))
    			refund.setCreateTimes(dateFormat.parse(refund.getStartTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try{
    		if("end".equals(type)){
    			if("1".equals(refund.getZcResult())){
            		refund.setAppStatus(AppStatus.WAIT_RETURN.getCode());
            	}
            	if("0".equals(refund.getZcResult())){
            		refund.setAppStatus(AppStatus.FINAL_CHECK_RETURN.getCode());
            	}
    		}else{
    			if("1".equals(refund.getFkResult())){
            		refund.setAppStatus(AppStatus.FINAL_CHECK.getCode());
            	}
            	if("0".equals(refund.getFkResult())){
            		refund.setAppStatus(AppStatus.FIRST_CHECK_RETURN.getCode());
            	}
    		}
        	refund.setIdArray(new String[]{refund.getId()});
        	
    		longRefundService.update(refund,type);
        	result = true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return BooleanType.FALSE;
    	}
    	if(result){
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
    }
    
    /**
	 * 批量操作页面
	 * 2016年4月21日
	 * By WJJ
	 */
    @RequestMapping(value = { "operatePage" })
    public String operatePage(Model model,Refund refund,String type) {
    	
    	model.addAttribute("refund", refund);
    	model.addAttribute("type", type);
    	if("1".equals(refund.getFkResult())){
    		return "borrow/refund/operateY";
    	}else{
    		return "borrow/refund/operateN";
    	}
    }
    
    /**
	 * 批量操作页面
	 * 2016年4月22日
	 * By WJJ
	 */
    @ResponseBody
    @RequestMapping(value = { "operate" })
    public String operate(Model model,Refund refund,String type) {
    	boolean result = false;
    	if("end".equals(type)){
    		refund.setAppStatus(AppStatus.FINAL_CHECK.getCode());
    	}else{
    		refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
    	}
    	
    	refund.setIdArray(refund.getId().split(","));
    	String[] mts = refund.getMt().split(",");
    	Map<String,String> map = this.splitString(refund.getId(), refund.getMt());
    	List<Refund> list = longRefundService.selectRefundById(refund);
    	User user = UserUtils.getUser();
    	//给大金融推送数据时设置申请时间
    	refund.setCreateTimes(new Date());
    	refund.setModifyBy(user.getId());
    	refund.setModifyTime(new Date());
    	if("end".equals(type)){
			if ("1".equals(refund.getFkResult())) {
				refund.setAppStatus(AppStatus.WAIT_RETURN.getCode());
			}
			if ("0".equals(refund.getFkResult())) {
				refund.setAppStatus(AppStatus.FINAL_CHECK_RETURN.getCode());
			}
			refund.setZcResult(refund.getFkResult());
			refund.setFkResult(null);
			refund.setZcRemark(refund.getRemark());
			refund.setRemark(null);
		}else{
			if ("1".equals(refund.getFkResult())) {
				refund.setAppStatus(AppStatus.FINAL_CHECK.getCode());
			}
			if ("0".equals(refund.getFkResult())) {
				refund.setAppStatus(AppStatus.FIRST_CHECK_RETURN.getCode());
			}
			refund.setFkRemark(refund.getRemark());
			refund.setRemark(null);
		}
    	//String ids = "";
    	for(int i=0;i<list.size();i++){
    		//ids += list.get(i).getId() + ",";
        	refund.setId(list.get(i).getId());
        	refund.setMt(map.get(list.get(i).getId()));
        	refund.setContractCode(list.get(i).getContractCode());
    		try {
    			longRefundService.update(refund,type);
    			result = true;
    		} catch (Exception e) {
    			return BooleanType.FALSE;
    		}
    	}
    	/*if(!"".equals(ids)){
    		ids = ids.substring(0,ids.length()-1);
    		refund.setIdArray(ids.split(","));
    		
    	}*/
    	
    	if(result){
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
    }
    
    public Map<String,String> splitString(String ids,String mts) {
    	Map<String,String> map = new HashMap();
		String[] idStr = ids.split(",");
		String[] mtStr = mts.split(",");
		if(idStr.length==mtStr.length){
			for(int i=0;i<idStr.length;i++){
				map.put(idStr[i], mtStr[i]);
			}
		}
		return map;
	}
    
    /**
	 * 退款待终审页面
	 * 2016年4月22日
	 * By WJJ
	 */
    @RequestMapping(value = { "endList" })
    public String endList(Model model,Refund refund, HttpServletRequest request,
			HttpServletResponse response) {
    	if(/*"".equals(refund.getAppStatus())||*/refund.getAppStatus()==null){
    		/*if(AppStatus.FIRST_CHECK.getCode().equals(refund.getAppStatus())||AppStatus.FIRST_CHECK_RETURN.getCode().equals(refund.getAppStatus())){
    			refund.setAppStatus("?");
    		}else{
        		refund.setAppStatus("-2");
    		}*/
    		refund.setAppStatus(AppStatus.FINAL_CHECK.getCode());
    	}
    	Page<Refund> urgePage = longRefundService
				.selectRefundList(
						new Page<Refund>(request, response),
						refund);
		if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for (Refund ex : urgePage.getList()) {
				ex.setAppTypeName(DictCache.getInstance().getDictLabel("jk_app_type", ex.getAppType()));
				ex.setBankName(DictCache.getInstance().getDictLabel("jk_open_bank", ex.getBankCode()));
				ex.setAppStatusName(DictCache.getInstance().getDictLabel("jk_app_status", ex.getAppStatus()));
				ex.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", ex.getLoanStatus()));//jk_loan_status
				// 渠道
				ex.setLoanFlagLabel(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getLoanFlag()));
				// 模式
				String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel());
				ex.setModelLabel(dictLoanModel);
			}
		}
		model.addAttribute("search", refund);
		model.addAttribute("items", urgePage);
    	
        return "borrow/refund/endList";
    }
    
    /**
	 * 终审批量操作页面
	 * 2016年4月21日
	 * By WJJ
	 */
    @RequestMapping(value = { "endOperatePage" })
    public String endOperatePage(Model model,Refund refund,String type) {
    	model.addAttribute("refund", refund);
    	model.addAttribute("type", type);
    	return "borrow/refund/endOperate";
    }
    
    /**
	 * 初审列表
	 * 导出Excel
	 */
	@RequestMapping(value = "firstExl")
	public void firstExl(HttpServletRequest request,
			HttpServletResponse response,Refund refund) {
		ExcelUtils excelutil = new ExcelUtils();
		List<ExamineExportExcel> l = new ArrayList<ExamineExportExcel>();
		//refund.setAppStatus(AppStatus.FIRST_CHECK.getCode());
		if(StringUtils.isNotEmpty(refund.getIds())){
			Refund r = new Refund();
			r.setIdArray(refund.getIds().split(","));
			l= longRefundService.examineExportExcelList(r);
		}else{
			l= longRefundService.examineExportExcelList(refund);
		}
		
		// 导出富友平台list
		excelutil.exportExcel(l,"退款导出",null,
				ExamineExportExcel.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);
	}
	
}
