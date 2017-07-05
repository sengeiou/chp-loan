package com.creditharmony.loan.credit.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.adapter.bean.in.SimpleInInfo;
import com.creditharmony.adapter.bean.in.pbc.PbcGetReportInfo;
import com.creditharmony.adapter.bean.in.pbc.PbcLoginInfo;
import com.creditharmony.adapter.bean.out.pbc.PbcGetLoginPageOutInfo;
import com.creditharmony.adapter.bean.out.pbc.PbcGetReportOutInfo;
import com.creditharmony.adapter.bean.out.pbc.PbcLoginOutInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.credit.constants.EnterpriseCreditConstants;
import com.creditharmony.loan.credit.entity.CreditCardInfo;
import com.creditharmony.loan.credit.entity.CreditLoanInfo;
import com.creditharmony.loan.credit.entity.CreditPaybackInfo;
import com.creditharmony.loan.credit.entity.CreditQueryRecord;
import com.creditharmony.loan.credit.entity.CreditReportSimple;
import com.creditharmony.loan.credit.service.CreditCardInfoService;
import com.creditharmony.loan.credit.service.CreditDetailedInfoService;
import com.creditharmony.loan.credit.service.CreditLoanInfoService;
import com.creditharmony.loan.credit.service.CreditPaybackInfoService;
import com.creditharmony.loan.credit.service.CreditQueryRecordService;
import com.creditharmony.loan.credit.service.CreditReportSimpleService;

/**
 * 简版信用报告
 * @Class Name CreditReportSimpleController
 * @author zhanghu
 * @Create In 2016年1月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditReportSimple")
public class CreditReportSimpleController extends BaseController {
	
	@Autowired
	private CreditReportSimpleService creditReportSimpleService;
	
	@Autowired
	private CreditCardInfoService creditCardInfoService;
	
	@Autowired
	private CreditLoanInfoService creditLoanInfoService;
	
	@Autowired
	private CreditQueryRecordService creditQueryRecordService;
	
	@Autowired
	private CreditDetailedInfoService creditDetailedInfoService;
	
	@Autowired
	private CreditPaybackInfoService creditPaybackInfoService;
	
	/**
	 * 进入征信个人简版
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return String 征信个人简版页面地址
	 */
	@RequestMapping(value = "form")
	public String initForm(Model model, CreditReportSimple creditReportSimple, String customerType, String version) {			
		
		model.addAttribute("loanCode", creditReportSimple.getLoanCode());
		model.addAttribute("dictCustomerType",creditReportSimple.getDictCustomerType());
		model.addAttribute("rId", creditReportSimple.getrCustomerCoborrowerId());
		
		// 如果为共借人，获取共借人身份证号
		if(LoanManFlag.COBORROWE_LOAN.getCode().equals(creditReportSimple.getDictCustomerType())){
			LoanCoborrower result = creditDetailedInfoService.selectCoboNameAndCertNum(
					creditReportSimple.getrCustomerCoborrowerId());
			if(result != null){
				model.addAttribute("applyCertNum", result.getCoboCertNum());
			}
		}
		// 如果为主借人，获取主借人身份证号
		if(LoanManFlag.MAIN_LOAN.getCode().equals(creditReportSimple.getDictCustomerType())){
			LoanCustomer result = creditDetailedInfoService.getCustomer(
					creditReportSimple.getrCustomerCoborrowerId());
			if(result != null){
				model.addAttribute("applyCertNum", result.getCustomerCertNum());
			}
		}
		
		//初始化信息
		simpInit(model, creditReportSimple);
		return "credit/creditReportSimpleForm";		
	}
	
	/**
	 * 判断借款人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return String 征信个人简版页面地址
	 */
	@RequestMapping(value = "checkLoanMan")
	public String checkLoanMan(Model model, CreditReportSimple creditReportSimple) {
		
		//获取主借人信息
	  	LoanCustomer loanCustomer = creditReportSimpleService.selectByApplyId(creditReportSimple.getApplyId());
	  	creditReportSimple.setLoanCode(loanCustomer.getLoanCode());//借款编号
	  	creditReportSimple.setCustomerId(loanCustomer.getId());//主借人编号
	  	
	    //获取共借人信息
		List<LoanCoborrower> LoanCoborrowerList = creditReportSimpleService.selectByLoanCode(creditReportSimple.getLoanCode());
		//共借人信息为空
		if (!ArrayHelper.isNotEmpty(LoanCoborrowerList)) {
			// 直接录入主借人信息
			creditReportSimple.setrCustomerCoborrowerId(loanCustomer.getCustomerCode());
			
			// 初始化信息
			initCreditReportSimple(model, creditReportSimple);
			// 跳转到简版征信录入界面
			return "credit/creditReportSimpleForm";	
		}
		
		model.addAttribute("loanCustomer", loanCustomer);
		model.addAttribute("creditReportSimple", creditReportSimple);
		model.addAttribute("LoanCoborrowerList", LoanCoborrowerList);
		//弹出临时界面
		return "borrow/borrowlist/checkCustomer";
		
	}
	
	/**
	 * 初始化信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditReportSimple
	 */
	private void initCreditReportSimple(Model model, CreditReportSimple creditReportSimple) {
		
		String loanCode = creditReportSimple.getLoanCode();
		String dictCustomerType = creditReportSimple.getDictCustomerType();
		String rId = creditReportSimple.getrCustomerCoborrowerId();
		
		//征信信息初始化
		CreditReportSimple creditReportSimpleNew = creditReportSimpleService.initCreditReportSimple(creditReportSimple);
		
		// 根据征信id获取信用卡征信记录
		List<CreditCardInfo> creditCardInfoList = creditCardInfoService.selectByCreditCardInfo(creditReportSimpleNew.getId());
		// 根据征信id获取贷款记录
		List<CreditLoanInfo> creditLoanInfoList = creditLoanInfoService.selectByCreditLoanInfo(creditReportSimpleNew.getId());
		// 根据征信id获取查询记录
		List<CreditQueryRecord> creditQueryRecordList = creditQueryRecordService.selectByCreditQueryRecord(creditReportSimpleNew.getId());
		// 根据征信id获取保证人代偿信息
		List<CreditPaybackInfo> creditPaybackInfoList = creditPaybackInfoService.selectByCreditPaybackInfo(creditReportSimpleNew.getId());
		
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("rId", rId);
		model.addAttribute("dictCustomerType", dictCustomerType);
		
		model.addAttribute("creditReportSimple", creditReportSimpleNew);
		model.addAttribute("creditCardInfoList", creditCardInfoList);
		model.addAttribute("creditLoanInfoList", creditLoanInfoList);
		model.addAttribute("creditQueryRecordList", creditQueryRecordList);
		model.addAttribute("creditPaybackInfoList", creditPaybackInfoList);
		
	}
	
	/**
	 * 初始化信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditReportSimple
	 */
	private void simpInit(Model model, CreditReportSimple creditReportSimple) {
		
		//征信信息初始化
		CreditReportSimple creditReportSimpleNew = creditReportSimpleService.simpInit(creditReportSimple);
		
		if(creditReportSimpleNew != null){
			// 根据征信id获取信用卡征信记录
			List<CreditCardInfo> creditCardInfoList = creditCardInfoService.selectByCreditCardInfo(creditReportSimpleNew.getId());
			// 根据征信id获取贷款记录
			List<CreditLoanInfo> creditLoanInfoList = creditLoanInfoService.selectByCreditLoanInfo(creditReportSimpleNew.getId());
			// 根据征信id获取查询记录
			List<CreditQueryRecord> creditQueryRecordList = creditQueryRecordService.selectByCreditQueryRecord(creditReportSimpleNew.getId());
			// 根据征信id获取保证人代偿信息
			List<CreditPaybackInfo> creditPaybackInfoList = creditPaybackInfoService.selectByCreditPaybackInfo(creditReportSimpleNew.getId());

			
			model.addAttribute("creditReportSimple", creditReportSimpleNew);
			model.addAttribute("creditCardInfoList", creditCardInfoList);
			model.addAttribute("creditLoanInfoList", creditLoanInfoList);
			model.addAttribute("creditQueryRecordList", creditQueryRecordList);
			model.addAttribute("creditPaybackInfoList", creditPaybackInfoList);
		}else{
			CreditReportSimple creditRepor = new CreditReportSimple();
			creditRepor.preInsert();
			model.addAttribute("creditReportSimple", creditRepor);
			model.addAttribute("creditCardInfoList", new ArrayList<CreditCardInfo>());
			model.addAttribute("creditLoanInfoList", new ArrayList<CreditLoanInfo>());
			model.addAttribute("creditQueryRecordList", new ArrayList<CreditQueryRecord>());
			model.addAttribute("creditPaybackInfoList", new ArrayList<CreditPaybackInfo>());
		}
		
	}
	
	/**
	 * 保存信用卡信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditReportSimple
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCardInfo")
	public CreditCardInfo saveCardInfo(CreditReportSimple creditReportSimple){
		CreditCardInfo result = new CreditCardInfo();
		try {
			// 保存现有数据
			List<CreditCardInfo> creditCardInfoList = creditReportSimple.getCreditCardInfoList();
			if (ArrayHelper.isNotEmpty(creditCardInfoList)) {
				for (CreditCardInfo creditCardInfo : creditCardInfoList) {
					result = creditCardInfoService.insertCreditCardInfo(creditCardInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 保存贷款信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditReportSimple
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveLoanInfo")
	public CreditLoanInfo saveLoanInfo(CreditReportSimple creditReportSimple){
		CreditLoanInfo result = new CreditLoanInfo();
		try {
			// 保存现有数据
			List<CreditLoanInfo> creditLoanInfoList = creditReportSimple.getCreditLoanInfoList();
			if (ArrayHelper.isNotEmpty(creditLoanInfoList)) {
				for (CreditLoanInfo creditLoanInfo : creditLoanInfoList) {
					result = creditLoanInfoService.insertCreditLoanInfo(creditLoanInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 保存查询信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditReportSimple
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveQueryInfo")
	public String saveQueryInfo(CreditReportSimple creditReportSimple){

		try {
			// 清空原有数据
			creditQueryRecordService.deleteByRelationId(creditReportSimple.getId());
			// 保存现有数据
			List<CreditQueryRecord> creditQueryRecordList = creditReportSimple.getCreditQueryRecordList();
			if (ArrayHelper.isNotEmpty(creditQueryRecordList)) {
				for (CreditQueryRecord creditQueryRecord : creditQueryRecordList) {
					creditQueryRecordService.insertCreditQueryRecord(creditQueryRecord);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存保证人代偿信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditReportSimple
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="savePaybackInfo")
	public String savePaybackInfo(CreditReportSimple creditReportSimple){

		try {
			// 清空原有数据
			creditPaybackInfoService.deleteByRelationId(creditReportSimple.getId());
			// 保存现有数据
			List<CreditPaybackInfo> creditPaybackInfoList = creditReportSimple.getCreditPaybackInfoList();
			if (ArrayHelper.isNotEmpty(creditPaybackInfoList)) {
				for (CreditPaybackInfo creditPaybackInfo : creditPaybackInfoList) {
					creditPaybackInfoService.insertCreditPaybackInfo(creditPaybackInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 删除信用卡信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditCardInfo
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteCardInfoById")
	public String deleteCardInfoById(CreditCardInfo creditCardInfo){

		try {
			// 删除信用卡信息
			creditCardInfoService.deleteCardInfoById(creditCardInfo.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 删除贷款信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditLoanInfo
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteLoanInfoById")
	public String deleteLoanInfoById(CreditLoanInfo creditLoanInfo){

		try {
			// 删除贷款信息
			creditLoanInfoService.deleteLoanInfoById(creditLoanInfo.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 删除查询信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditLoanInfo
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteQueryInfoById")
	public String deleteQueryInfoById(CreditQueryRecord creditQueryRecord){

		try {
			// 删除查询信息
			creditQueryRecordService.deleteQueryInfoById(creditQueryRecord.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 删除查询信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditLoanInfo
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deletePaybackInfoById")
	public String deletePaybackInfoById(CreditPaybackInfo creditPaybackInfo){

		try {
			// 删除查询信息
			creditPaybackInfoService.deleteCreditPaybackInfoById(creditPaybackInfo.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化信用卡信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return  信用卡信息地址
	 */
	@RequestMapping(value = "initCardInfo")
	public String initCardInfo(Model model, CreditReportSimple creditReportSimple) {			
		
		// 根据征信id获取信用卡征信记录
		List<CreditCardInfo> creditCardInfoList = creditCardInfoService.selectByCreditCardInfo(creditReportSimple.getId());
		model.addAttribute("creditCardInfoList", creditCardInfoList);
		return "credit/creditReportSimpleCardInfoForm";		
	}
	
	/**
	 * 初始化贷款信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return 贷款信息地址
	 */
	@RequestMapping(value = "initLoanInfo")
	public String initLoanInfo(Model model, CreditReportSimple creditReportSimple) {			
		
		// 根据征信id获取贷款记录
		List<CreditLoanInfo> creditLoanInfoList = creditLoanInfoService.selectByCreditLoanInfo(creditReportSimple.getId());
		model.addAttribute("creditLoanInfoList", creditLoanInfoList);
		return "credit/creditReportSimpleLoanInfoForm";		
	}
	
	/**
	 * 初始化查询信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return 查询信息地址
	 */
	@RequestMapping(value = "initQueryInfo")
	public String initQueryInfo(Model model, CreditReportSimple creditReportSimple) {			
		
		// 根据征信id获取查询信息记录
		List<CreditQueryRecord> creditQueryRecordList = creditQueryRecordService.selectByCreditQueryRecord(creditReportSimple.getId());
		model.addAttribute("creditQueryRecordList", creditQueryRecordList);
		return "credit/creditReportSimpleQueryInfoForm";		
	}
	
	/**
	 * 初始化保证人代偿信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return 查询信息地址
	 */
	@RequestMapping(value = "initCreditPaybackInfo")
	public String initCreditPaybackInfo(Model model, CreditReportSimple creditReportSimple) {			
		
		// 根据征信id获取保证人代偿信息记录
		List<CreditPaybackInfo> creditPaybackInfoList = creditPaybackInfoService.selectByCreditPaybackInfo(creditReportSimple.getId());
		model.addAttribute("creditPaybackInfoList", creditPaybackInfoList);
		return "credit/creditReportSimplePaybackInfoForm";		
	}
	
	/**
	 * 保存基本信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditLoanInfo
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveQueryTime")
	public String saveQueryTime(CreditReportSimple creditReportSimple){
		try {
			if(creditReportSimple != null 
					&& StringUtils.isNotEmpty(creditReportSimple.getLoanCode())
					&& StringUtils.isNotEmpty(creditReportSimple.getDictCustomerType())
					&& StringUtils.isNotEmpty(creditReportSimple.getrCustomerCoborrowerId())){
				// 更新简版基本信息
				creditReportSimpleService.updateCreditReportSimple(creditReportSimple);
				return "true";
			}else{
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
	 * 进入网络查询系统登录页面
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return String 征信个人网络登录页面地址
	 */
	@RequestMapping(value = "initWeb")
	public String initWeb(Model model, CreditReportSimple creditReportSimple , String applyCertNum) {			
		
		String id = creditReportSimple.getId();
		String loanCode = creditReportSimple.getLoanCode();
		String dictCustomerType = creditReportSimple.getDictCustomerType();
		String rId = creditReportSimple.getrCustomerCoborrowerId();
		
		// 根据关联ID(主借人，共借人) R_CUSTOMER_COBORROWER_ID查询征信信息
		creditReportSimple = creditReportSimpleService.selectByCreditReportSimple(creditReportSimple);
		if(creditReportSimple == null){
			creditReportSimple = new CreditReportSimple();
			creditReportSimple.setId(id);
			creditReportSimple.setLoanCode(loanCode);
			creditReportSimple.setDictCustomerType(dictCustomerType);
			creditReportSimple.setrCustomerCoborrowerId(rId);
		}
		
		//调用接口获取验证码
	    ClientPoxy service = new ClientPoxy(ServiceType.Type.PBC_GETLOGINPAGE);
	    SimpleInInfo spi = new SimpleInInfo();
	    
	    //获取登录页信息
	    PbcGetLoginPageOutInfo pbcGetLoginPageOutInfo = new PbcGetLoginPageOutInfo();
	    pbcGetLoginPageOutInfo = (PbcGetLoginPageOutInfo) service.callService(spi);
	    creditReportSimple.setPbcGetLoginPageOutInfo(pbcGetLoginPageOutInfo);
	    
	    //登陆信息
	    PbcLoginInfo pbcLoginInfo = new PbcLoginInfo();
	    pbcLoginInfo.setCookies(pbcGetLoginPageOutInfo.getCookies());
	    creditReportSimple.setPbcLoginInfo(pbcLoginInfo);
	    
	    PbcLoginOutInfo pbcLoginOutInfo = new PbcLoginOutInfo();
	    creditReportSimple.setPbcLoginOutInfo(pbcLoginOutInfo);
	    
	    model.addAttribute("applyCertNum", applyCertNum);
		model.addAttribute("creditReportSimple", creditReportSimple);
		
		return "credit/creditReportSimpleWebLoad";		
	}
	
	/**
	 * 进入网络查询系统登录页面
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return String 征信个人网络登录页面地址
	 */
	@RequestMapping(value = "login")
	public String login(Model model, CreditReportSimple creditReportSimple , String applyCertNum) {			
		
		PbcLoginInfo pbcLoginInfo = creditReportSimple.getPbcLoginInfo();
		if (StringUtils.isNotEmpty(pbcLoginInfo.getCookies())) {
			//转换cookies
			pbcLoginInfo.setCookies(pbcLoginInfo.getCookies().replace("&quot;", "\""));
		    //登录
		    ClientPoxy service = new ClientPoxy(ServiceType.Type.PBC_LOGIN);
		    PbcLoginOutInfo pbcLoginOutInfo = new PbcLoginOutInfo();
		    pbcLoginOutInfo = (PbcLoginOutInfo) service.callService(pbcLoginInfo);
		    creditReportSimple.setPbcLoginOutInfo(pbcLoginOutInfo);
		    UserUtils.getSession().setAttribute(EnterpriseCreditConstants.SESSION_PBC_LOGINOUT_COOKIE,  pbcLoginOutInfo.getCookies());		    
		    // 汇金填写的身份证号
		    model.addAttribute("applyCertNum", applyCertNum);
		    
			//判断登录结果
			if (StringUtils.isNotEmpty(pbcLoginOutInfo.getRetCode()) && 
					pbcLoginOutInfo.getRetCode().equals("0000")) {
				PbcGetReportInfo pbcGetReportInfo = new PbcGetReportInfo();
				pbcGetReportInfo.setCookies(pbcLoginOutInfo.getCookies());
				//pbcGetReportInfo.setTradeCode("yc6agd");
				model.addAttribute("creditReportSimple", creditReportSimple);
				
				//成功
				return "credit/creditReportSimpleGetReport";	
			}
		}		

		//调用接口获取验证码
	    ClientPoxy serviceGetloginpage = new ClientPoxy(ServiceType.Type.PBC_GETLOGINPAGE);
	    SimpleInInfo spi = new SimpleInInfo();
	    
	    //获取登录页信息
	    PbcGetLoginPageOutInfo pbcGetLoginPageOutInfo = new PbcGetLoginPageOutInfo();
	    pbcGetLoginPageOutInfo = (PbcGetLoginPageOutInfo) serviceGetloginpage.callService(spi);
	    creditReportSimple.setPbcGetLoginPageOutInfo(pbcGetLoginPageOutInfo);
	    
	    //登陆信息
	    pbcLoginInfo.setCookies(pbcGetLoginPageOutInfo.getCookies());
	    creditReportSimple.setPbcLoginInfo(pbcLoginInfo);
	    
		model.addAttribute("creditReportSimple", creditReportSimple);
		
		//失败
		return "credit/creditReportSimpleWebLoad";	
		
	}
	
	/**
	 * 进入网络查询系统登录页面
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return String 征信个人网络登录页面地址
	 */
	@RequestMapping(value = "getReport")
	public String getReport(Model model, CreditReportSimple creditReportSimple , String applyCertNum) {			
		
		String id = creditReportSimple.getId();
		String loanCode = creditReportSimple.getLoanCode();
		String dictCustomerType = creditReportSimple.getDictCustomerType();
		String rId = creditReportSimple.getrCustomerCoborrowerId();
		
		// 获取网页版报告
		ClientPoxy service3 = new ClientPoxy(ServiceType.Type.PBC_GETCREDIT);
		PbcGetReportOutInfo pbcGetReportOutInfo = null;
		PbcGetReportInfo pbcGetReportInfo = creditReportSimple.getPbcGetReportInfo();
		// 如果cookie为空，取出之前保存在session中的从人行网站获取的cookie
		String pbcLoginOutCookie = StringUtils.isNotEmpty(pbcGetReportInfo.getCookies())
					? pbcGetReportInfo.getCookies()
					: (String) UserUtils.getSession().getAttribute(EnterpriseCreditConstants.SESSION_PBC_LOGINOUT_COOKIE);
		// 去除这个session值
		UserUtils.getSession().removeAttribute(EnterpriseCreditConstants.SESSION_PBC_LOGINOUT_COOKIE);
		if (StringUtils.isNotEmpty(pbcLoginOutCookie)) {
			//转换cookies
			pbcGetReportInfo.setCookies(pbcLoginOutCookie.replace("&quot;", "\""));
				    
			pbcGetReportOutInfo = (PbcGetReportOutInfo) service3.callService(pbcGetReportInfo);
			//String s = pbcGetReportOutInfo.getReport().toString();
			//System.out.println(s);	
			// 征信信息来源，人行征信网站(非手工录入)
			creditReportSimple.setCreditSource(EnterpriseCreditConstants.CREDIT_SOURCE_PBC);
			creditReportSimple.setPbcGetReportOutInfo(pbcGetReportOutInfo);
			
			// 汇金填写的身份证号
		    model.addAttribute("applyCertNum", applyCertNum);
			
			//判断登录结果
			if (StringUtils.isNotEmpty(pbcGetReportOutInfo.getRetCode()) && 
					pbcGetReportOutInfo.getRetCode().equals("0000")) {
				//成功
				creditReportSimpleService.playReport(pbcGetReportOutInfo, creditReportSimple);
				
				creditReportSimple.setId(id);
				creditReportSimple.setLoanCode(loanCode);
				creditReportSimple.setrCustomerCoborrowerId(rId);
				creditReportSimple.setDictCustomerType(dictCustomerType);
				// 简版征信爬虫完，相关操作（更新risk表，更新详版表）
				creditReportSimpleService.correlatSave(creditReportSimple);
				//初始化信息
				initCreditReportSimple(model, creditReportSimple);
				return "credit/creditReportSimpleForm";	
			}
		} else {
			pbcGetReportOutInfo = new PbcGetReportOutInfo();
			pbcGetReportOutInfo.setRetMsg(EnterpriseCreditConstants.COOKIES_EMPTY);
		}
		creditReportSimple.setPbcGetReportOutInfo(pbcGetReportOutInfo);
		creditReportSimple.setPbcGetReportInfo(pbcGetReportInfo);
		creditReportSimple.setId(id);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("rId", rId);
		model.addAttribute("dictCustomerType", dictCustomerType);
		model.addAttribute("creditReportSimple", creditReportSimple);
		
		//失败
		return "credit/creditReportSimpleGetReport";
		
	}
	
	/**
	 * 进入静态网页
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param creditReportSimple
	 * @return String 征信个人网络登录页面地址
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "initHtmlUrl")
	public String initHtmlUrl(Model model, CreditReportSimple creditReportSimple) throws UnsupportedEncodingException {			
		
		creditReportSimple = creditReportSimpleService.selectByCreditReportSimple(creditReportSimple);
		if(creditReportSimple != null){
			String htmTxt = creditReportSimple.getHtmlUrl();
			if(StringUtils.isNotEmpty(htmTxt)) {
				String htmlUrl = new String(htmTxt.getBytes(),"utf-8");
				model.addAttribute("htmlUrl",  htmlUrl);
				return "credit/creditReportSimpleHtmlUrl";
			} else {
				return "credit/creditReportSimpleHtmlUrl";
			}		
		}else{
			return "credit/creditReportSimpleHtmlUrl";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="laodCodeImageUrl")
	public PbcGetLoginPageOutInfo laodCodeImageUrl(String loanCode){
		//调用接口获取验证码
	    ClientPoxy serviceGetloginpage = new ClientPoxy(ServiceType.Type.PBC_GETLOGINPAGE);
	    SimpleInInfo spi = new SimpleInInfo();
	    //获取登录页信息
	    PbcGetLoginPageOutInfo pbcGetLoginPageOutInfo = new PbcGetLoginPageOutInfo();
	    pbcGetLoginPageOutInfo = (PbcGetLoginPageOutInfo) serviceGetloginpage.callService(spi);

	    return pbcGetLoginPageOutInfo;
		
	}
}
