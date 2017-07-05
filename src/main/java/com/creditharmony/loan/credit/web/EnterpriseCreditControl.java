package com.creditharmony.loan.credit.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.constants.CreditBusinessType;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.credit.constants.EnterpriseCreditConstants;
import com.creditharmony.loan.credit.entity.CreditAffiliatedEnterprise;
import com.creditharmony.loan.credit.entity.CreditBasicInfo;
import com.creditharmony.loan.credit.entity.CreditCivilJudgmentRecord;
import com.creditharmony.loan.credit.entity.CreditCreditClearedDetail;
import com.creditharmony.loan.credit.entity.CreditCreditClearedInfo;
import com.creditharmony.loan.credit.entity.CreditCurrentLiabilityDetail;
import com.creditharmony.loan.credit.entity.CreditCurrentLiabilityInfo;
import com.creditharmony.loan.credit.entity.CreditExecutiveInfo;
import com.creditharmony.loan.credit.entity.CreditExternalGuaranteeRecord;
import com.creditharmony.loan.credit.entity.CreditExternalSecurityInfo;
import com.creditharmony.loan.credit.entity.CreditGrade;
import com.creditharmony.loan.credit.entity.CreditInvestorInfo;
import com.creditharmony.loan.credit.entity.CreditLiabilityHis;
import com.creditharmony.loan.credit.entity.CreditLoanCard;
import com.creditharmony.loan.credit.entity.CreditPaidLoan;
import com.creditharmony.loan.credit.entity.CreditPunish;
import com.creditharmony.loan.credit.entity.CreditUnclearedBankAcceptance;
import com.creditharmony.loan.credit.entity.CreditUnclearedFactoring;
import com.creditharmony.loan.credit.entity.CreditUnclearedImproperLoan;
import com.creditharmony.loan.credit.entity.CreditUnclearedLetterCredit;
import com.creditharmony.loan.credit.entity.CreditUnclearedLetterGuarantee;
import com.creditharmony.loan.credit.entity.CreditUnclearedLoan;
import com.creditharmony.loan.credit.entity.CreditUnclearedNotesDiscounted;
import com.creditharmony.loan.credit.entity.CreditUnclearedTradeFinancing;
import com.creditharmony.loan.credit.entity.EnterpriseCredit;
import com.creditharmony.loan.credit.service.CreditAffiliatedEnterpriseService;
import com.creditharmony.loan.credit.service.CreditBasicInfoService;
import com.creditharmony.loan.credit.service.CreditCivilJudgmentRecordService;
import com.creditharmony.loan.credit.service.CreditCreditClearedDetailService;
import com.creditharmony.loan.credit.service.CreditCreditClearedInfoService;
import com.creditharmony.loan.credit.service.CreditCurrentLiabilityDetailService;
import com.creditharmony.loan.credit.service.CreditCurrentLiabilityInfoService;
import com.creditharmony.loan.credit.service.CreditExecutiveInfoService;
import com.creditharmony.loan.credit.service.CreditExternalGuaranteeRecordService;
import com.creditharmony.loan.credit.service.CreditExternalSecurityInfoService;
import com.creditharmony.loan.credit.service.CreditGradeService;
import com.creditharmony.loan.credit.service.CreditInvestorInfoService;
import com.creditharmony.loan.credit.service.CreditLiabilityHisService;
import com.creditharmony.loan.credit.service.CreditLoanCardService;
import com.creditharmony.loan.credit.service.CreditPaidLoanService;
import com.creditharmony.loan.credit.service.CreditPunishService;
import com.creditharmony.loan.credit.service.CreditReportSimpleService;
import com.creditharmony.loan.credit.service.CreditUnclearedBankAcceptanceService;
import com.creditharmony.loan.credit.service.CreditUnclearedFactoringService;
import com.creditharmony.loan.credit.service.CreditUnclearedImproperLoanService;
import com.creditharmony.loan.credit.service.CreditUnclearedLetterCreditService;
import com.creditharmony.loan.credit.service.CreditUnclearedLetterGuaranteeService;
import com.creditharmony.loan.credit.service.CreditUnclearedLoanService;
import com.creditharmony.loan.credit.service.CreditUnclearedNotesDiscountedService;
import com.creditharmony.loan.credit.service.CreditUnclearedTradeFinancingService;
import com.creditharmony.loan.credit.service.EnterpriseCreditService;

/**
 * 企业征信报告
 * @Class Name EnterpriseCreditControl
 * @author zhanghu
 * @Create In 2016年1月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/enterpriseCredit")
public class EnterpriseCreditControl extends BaseController {
	
	@Autowired
	private EnterpriseCreditService enterpriseCreditService;
	
	@Autowired
	private CreditInvestorInfoService creditInvestorInfoService;	
	
	@Autowired
	private CreditExecutiveInfoService creditExecutiveInfoService;

	@Autowired
	private CreditPunishService creditPunishService;
	
	@Autowired
	private CreditGradeService creditGradeService;
	
	@Autowired
	private CreditLoanCardService creditLoanCardService;
	
	@Autowired
	private CreditCivilJudgmentRecordService civilJudgmentService;
	
	@Autowired
	private CreditExternalGuaranteeRecordService externalGuaranteeService;
	
	@Autowired
	private CreditAffiliatedEnterpriseService creditAffiliatedEnterpriseService;
	
	@Autowired
	private CreditBasicInfoService creditBasicInfoService;
	
	@Autowired
	private CreditCurrentLiabilityInfoService creditCurrentLiabilityInfoService;	
	
	@Autowired
	private CreditCurrentLiabilityDetailService creditCurrentLiabilityDetailService;	
	
	@Autowired
	private CreditExternalSecurityInfoService creditExternalSecurityInfoService;
	
	@Autowired
	private CreditCreditClearedInfoService creditCreditClearedInfoService;
	
	@Autowired
	private CreditCreditClearedDetailService creditCreditClearedDetailService;

	@Autowired
	private CreditLiabilityHisService creditLiabilityHisService;
	
	@Autowired
	private CreditPaidLoanService paidLoanService;
	
	@Autowired
	private CreditUnclearedLoanService creditUnclearedLoanService;
	
	@Autowired
	private CreditUnclearedTradeFinancingService creditUnclearedTradeFinancingService;
	
	@Autowired
	private CreditUnclearedFactoringService creditUnclearedFactoringService;
	
	@Autowired
	private CreditUnclearedNotesDiscountedService creditUnclearedNotesDiscountedService;
	
	@Autowired
	private CreditUnclearedBankAcceptanceService creditUnclearedBankAcceptanceService;
	
	@Autowired
	private CreditUnclearedLetterCreditService creditUnclearedLetterCreditService;
	
	@Autowired
	private CreditUnclearedLetterGuaranteeService creditUnclearedLetterGuaranteeService;
	
	@Autowired
	private CreditUnclearedImproperLoanService unclearedImproperLoanService;
	
	@Autowired
	private CityInfoService cityInfoService;
	@Autowired
	private CreditReportSimpleService creditReportSimpleService;
	@Autowired
	ReconsiderApplyDao reconsiderApplyDao;
	@Autowired
	ApplyLoanInfoDao applyLoanInfoDao;
	
	/**
	 * 进入企业征信报告
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param EnterpriseCredit
	 * @return String 征信个人简版页面地址
	 */
	@RequestMapping(value = "form")
	public String initForm(Model model, EnterpriseCredit enterpriseCredit) {			
		String loanCode = enterpriseCredit.getLoanCode();
		
		//借款编码为空，根据applyId查找loanCode
		if (StringUtils.isEmpty(loanCode)) {
			// 查找复议，如果不为空，将applyId更新为信审时applyId
			ReconsiderApply reconsider = reconsiderApplyDao.selectByApply(enterpriseCredit.getApplyId());
			if (reconsider != null) {
				loanCode = reconsider.getLoanCode();
				LoanInfo loanInfo = applyLoanInfoDao.getByLoanCode(loanCode);
				enterpriseCredit.setApplyId(loanInfo.getApplyId());
			} else {
				//获取主借人信息
			  	LoanCustomer loanCustomer = creditReportSimpleService.selectByApplyId(enterpriseCredit.getApplyId());
			  	loanCode = loanCustomer.getLoanCode(); //借款编号			  				  	
			}								
		}
		
		if (StringUtils.isNotEmpty(loanCode)) {
			enterpriseCredit.setLoanCode(loanCode);
		}		
		// 根据关联ID(主借人，共借人) R_CUSTOMER_COBORROWER_ID查询征信信息
		enterpriseCredit = enterpriseCreditService.selectByEnterpriseCredit(enterpriseCredit);
		
		// 判断有无征信信息
		if (!(null != enterpriseCredit && StringUtils.isNotEmpty(enterpriseCredit.getLoanCode()))) {
			//征信信息初始化
			enterpriseCredit = new EnterpriseCredit();
			enterpriseCredit.setLoanCode(loanCode);
			enterpriseCredit.setCreditVersion(EnterpriseCreditConstants.CREDIT_VERSION_0);
			enterpriseCredit.setReportDate(new Date());
//			enterpriseCreditService.insertEnterpriseCredit(enterpriseCredit);
		}
		
		// 根据借款编码获取出资人征信记录
		List<CreditInvestorInfo> creditInvestorInfoList = creditInvestorInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());

		// 根据借款编码获取高管人员征信记录
		List<CreditExecutiveInfo> creditExecutiveInfoList = creditExecutiveInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());		
		
		// 已结清负债明细
		CreditPaidLoan paidLoan = new CreditPaidLoan();
		paidLoan.setLoanCode(enterpriseCredit.getLoanCode());
		List<CreditPaidLoan> paidLoanList = paidLoanService.selectByPaidLoanInfo(paidLoan);
		
		// 外部担保记录
		List<CreditExternalGuaranteeRecord> externalGuaranteeList = externalGuaranteeService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 民事判决记录
		List<CreditCivilJudgmentRecord> civilJudgmentList = civilJudgmentService.selectByLoanCode(enterpriseCredit.getLoanCode());		
		
		// 贷款卡记录
		List<CreditLoanCard> creditLoanCardList = creditLoanCardService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 信用评级记录
		List<CreditGrade> creditGradeList = creditGradeService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 处罚记录
		List<CreditPunish> creditPunishList = creditPunishService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 直接关联企业
		List<CreditAffiliatedEnterprise> creditAffiliatedEnterpriseList = creditAffiliatedEnterpriseService.selectByLoanCode(enterpriseCredit.getLoanCode());
				
		// 基础信息
		CreditBasicInfo creditBasicInfo = creditBasicInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 当前负债信息概要
		List<CreditCurrentLiabilityInfo> creditCurrentLiabilityInfoList = creditCurrentLiabilityInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		// 初始化
		if (!ArrayHelper.isNotEmpty(creditCurrentLiabilityInfoList)) {
			creditCurrentLiabilityInfoService.initByEnterpriseCredit(enterpriseCredit);
			// 查询List
			creditCurrentLiabilityInfoList = creditCurrentLiabilityInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		}
		
		// 当前负债信息明细
		List<CreditCurrentLiabilityDetail> creditCurrentLiabilityDetailList = creditCurrentLiabilityDetailService.selectByLoanCode(enterpriseCredit.getLoanCode());
		// 初始化
		if (!ArrayHelper.isNotEmpty(creditCurrentLiabilityDetailList)) {
			creditCurrentLiabilityDetailService.initByEnterpriseCredit(enterpriseCredit);
			// 查询List
			creditCurrentLiabilityDetailList = creditCurrentLiabilityDetailService.selectByLoanCode(enterpriseCredit.getLoanCode());
		}
		
		// 对外担保信息概要
		List<CreditExternalSecurityInfo> creditExternalSecurityInfoList = creditExternalSecurityInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		// 初始化
		if (!ArrayHelper.isNotEmpty(creditExternalSecurityInfoList)) {
			creditExternalSecurityInfoService.initByEnterpriseCredit(enterpriseCredit);
			// 查询List
			creditExternalSecurityInfoList = creditExternalSecurityInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());

		}
		
		// 企业征信_已结清信贷信息
		List<CreditCreditClearedInfo> creditCreditClearedInfoList = creditCreditClearedInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		// 初始化
		if (!ArrayHelper.isNotEmpty(creditCreditClearedInfoList)) {
			creditCreditClearedInfoService.initByEnterpriseCredit(enterpriseCredit);
			// 查询List
			creditCreditClearedInfoList = creditCreditClearedInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		}
		
		// 企业征信_已结清信贷明细
		List<CreditCreditClearedDetail> creditCreditClearedDetailList = creditCreditClearedDetailService.selectByLoanCode(enterpriseCredit.getLoanCode());
		// 初始化
		if (!ArrayHelper.isNotEmpty(creditCreditClearedDetailList)) {
			creditCreditClearedDetailService.initByEnterpriseCredit(enterpriseCredit);
			// 查询List
			creditCreditClearedDetailList = creditCreditClearedDetailService.selectByLoanCode(enterpriseCredit.getLoanCode());
		}
		
		// 企业征信_负债历史变化
		List<CreditLiabilityHis> creditLiabilityHisList = creditLiabilityHisService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清贷款
		List<CreditUnclearedLoan> creditUnclearedLoanList =  creditUnclearedLoanService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清贸易融资
		List<CreditUnclearedTradeFinancing> creditUnclearedTradeFinancingList =  creditUnclearedTradeFinancingService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清保理
		List<CreditUnclearedFactoring> creditUnclearedFactoringList =  creditUnclearedFactoringService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清票据贴现
		List<CreditUnclearedNotesDiscounted> creditUnclearedNotesDiscountedList =  creditUnclearedNotesDiscountedService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清银行承兑汇票
		List<CreditUnclearedBankAcceptance> creditUnclearedBankAcceptanceList =  creditUnclearedBankAcceptanceService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清信用证
		List<CreditUnclearedLetterCredit> creditUnclearedLetterCreditList =  creditUnclearedLetterCreditService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		// 企业征信_未结清保函
		List<CreditUnclearedLetterGuarantee> creditUnclearedLetterGuaranteeList =  creditUnclearedLetterGuaranteeService.selectByLoanCode(enterpriseCredit.getLoanCode());
		
		//未结清业务:不良、关注类
		CreditUnclearedImproperLoan unclearedImproperLoan=new CreditUnclearedImproperLoan();
		unclearedImproperLoan.setLoanCode(enterpriseCredit.getLoanCode());
		List<CreditUnclearedImproperLoan> unclearedImproperLoanList=unclearedImproperLoanService.selectByCreditUnclearedImproperLoan(unclearedImproperLoan);
		
		model.addAttribute("enterpriseCredit", enterpriseCredit);
		model.addAttribute("creditInvestorInfoList", creditInvestorInfoList);//出资人征信记录
		model.addAttribute("creditExecutiveInfoList", creditExecutiveInfoList);//高管人员征信记录
		model.addAttribute("creditAffiliatedEnterpriseList", creditAffiliatedEnterpriseList);//直接关联企业
		
		model.addAttribute("paidLoanList", paidLoanList); 
		model.addAttribute("externalGuaranteeList", externalGuaranteeList);
		model.addAttribute("civilJudgmentList", civilJudgmentList);
		model.addAttribute("creditLoanCardList", creditLoanCardList);
		model.addAttribute("creditGradeList", creditGradeList);
		model.addAttribute("creditPunishList", creditPunishList);

		model.addAttribute("creditAffiliatedEnterpriseList", creditAffiliatedEnterpriseList);//直接关联企业
		model.addAttribute("creditBasicInfo", creditBasicInfo);//基础信息
		model.addAttribute("creditCurrentLiabilityInfoList", creditCurrentLiabilityInfoList);//当前负债信息概要
		model.addAttribute("creditCurrentLiabilityDetailList", creditCurrentLiabilityDetailList);//当前负债信息明细
		model.addAttribute("creditExternalSecurityInfoList", creditExternalSecurityInfoList);//对外担保信息概要
		model.addAttribute("creditCreditClearedInfoList", creditCreditClearedInfoList);// 企业征信_已结清信贷信息
		
		model.addAttribute("creditCreditClearedDetailList", creditCreditClearedDetailList);// 企业征信_已结清信贷明细
		model.addAttribute("creditLiabilityHisList", creditLiabilityHisList);// 企业征信_负债历史变化
		
		// 企业征信_未结清贷款
		model.addAttribute("creditUnclearedLoanList", creditUnclearedLoanList);
		// 企业征信_未结清贸易融资
		model.addAttribute("creditUnclearedTradeFinancingList", creditUnclearedTradeFinancingList);
		// 企业征信_未结清保理
		model.addAttribute("creditUnclearedFactoringList", creditUnclearedFactoringList);
		// 企业征信_未结清票据贴现
		model.addAttribute("creditUnclearedNotesDiscountedList", creditUnclearedNotesDiscountedList);
		// 企业征信_未结清银行承兑汇票
		model.addAttribute("creditUnclearedBankAcceptanceList", creditUnclearedBankAcceptanceList);
		// 企业征信_未结清信用证
		model.addAttribute("creditUnclearedLetterCreditList", creditUnclearedLetterCreditList);
		// 企业征信_未结清保函
		model.addAttribute("creditUnclearedLetterGuaranteeList", creditUnclearedLetterGuaranteeList);
		// 未结清非正常贷款
		model.addAttribute("unclearedImproperLoanList", unclearedImproperLoanList); 
		
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList",provinceList);
		
		return "credit/enterpriseCreditForm";		
	}
	
	/**
	 * 保存出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditInvestorInfo")
	public String saveCreditInvestorInfo(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditInvestorInfoService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditInvestorInfo> creditInvestorInfoList = enterpriseCredit.getCreditInvestorInfoList();
			if (ArrayHelper.isNotEmpty(creditInvestorInfoList)) {
				for (CreditInvestorInfo creditInvestorInfo : creditInvestorInfoList) {
					creditInvestorInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditInvestorInfoService.insertCreditInvestorInfo(creditInvestorInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  出资人信息地址
	 */
	@RequestMapping(value = "initCreditInvestorInfo")
	public String initCreditInvestorInfo(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取出资人信息记录
		List<CreditInvestorInfo> creditInvestorInfoList = creditInvestorInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditInvestorInfoList", creditInvestorInfoList);
		return "credit/creditInvestorInfoForm";		
	}
	
	/**
	 * 删除出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditInvestorInfo
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditInvestorInfoById")
	public String deleteCreditInvestorInfoById(CreditInvestorInfo creditInvestorInfo){

		try {
			// 删除信息
			creditInvestorInfoService.deleteByPrimaryKey(creditInvestorInfo.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditExecutiveInfo")
	public String saveCreditExecutiveInfo(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditExecutiveInfoService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditExecutiveInfo> creditExecutiveInfoList = enterpriseCredit.getCreditExecutiveInfoList();
			if (ArrayHelper.isNotEmpty(creditExecutiveInfoList)) {
				for (CreditExecutiveInfo creditExecutiveInfo : creditExecutiveInfoList) {
					creditExecutiveInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditExecutiveInfoService.insertCreditExecutiveInfo(creditExecutiveInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  出资人信息地址
	 */
	@RequestMapping(value = "initCreditExecutiveInfo")
	public String initCreditExecutiveInfo(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取出资人信息记录
		List<CreditExecutiveInfo> creditExecutiveInfoList = creditExecutiveInfoService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditExecutiveInfoList", creditExecutiveInfoList);
		return "credit/creditExecutiveInfoForm";		
	}
	
	/**
	 * 删除出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param CreditExecutiveInfo
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditExecutiveInfoById")
	public String deleteCreditExecutiveInfoById(CreditExecutiveInfo creditExecutiveInfo){

		try {
			// 删除信息
			creditExecutiveInfoService.deleteByPrimaryKey(creditExecutiveInfo.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存已结清负债明细信息
	 * 2016年2月22日
	 * By 王浩
	 * @param enterpriseCredit
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="savePaidLoan")
	public String savePaidLoan(EnterpriseCredit enterpriseCredit, String businessType){
		try {			
			List<CreditPaidLoan> paidLoanList = enterpriseCredit.getPaidLoanList();
			if (ArrayHelper.isNotEmpty(paidLoanList)) {
				businessType = paidLoanList.get(0).getBusinessType();
				CreditPaidLoan creditPaidLoan = new CreditPaidLoan();
				creditPaidLoan.setLoanCode(enterpriseCredit.getLoanCode());
				creditPaidLoan.setBusinessType(businessType);
				// 清空原有数据
				paidLoanService.deleteByPaidLoanInfo(creditPaidLoan);
				// 保存现有数据
				for (CreditPaidLoan paidLoan : paidLoanList) {
					paidLoan.setLoanCode(enterpriseCredit.getLoanCode());
					paidLoanService.savePaidLoan(paidLoan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		} 
		
		if (StringUtils.isNotEmpty(businessType)) {
			return businessType;
		} else {
			return BooleanType.TRUE;
		}		
	}
	
	/**
	 * 删除已结清负债明细信息
	 * 2016年2月22日
	 * By 王浩
	 * @param paidLoan
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deletePaidLoanById")
	public String deleteGuaranteeById(CreditPaidLoan paidLoan){
		try {
			// 删除处罚信息
			paidLoanService.deletePaidLoanById(paidLoan.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上已结清负债明细信息
	 * 2016年2月22日
	 * By 王浩
	 * @param model
	 * @param paidLoan
	 * @return none
	 */
	@RequestMapping(value="initPaidLoan")
	public String initPaidLoan(Model model, CreditPaidLoan paidLoan) {
		List<CreditPaidLoan> paidLoanList = paidLoanService.selectByPaidLoanInfo(paidLoan);
		model.addAttribute("paidLoanList", paidLoanList);
		
		if (paidLoan.getBusinessType().equals(CreditBusinessType.LOAN)) {
			return "credit/creditPaidLoanLoanForm";
		} else if (paidLoan.getBusinessType().equals(CreditBusinessType.TRADE_FINANCING)) {
			return "credit/creditPaidLoanTradeForm";
		} else if (paidLoan.getBusinessType().equals(CreditBusinessType.FACTORING)) {
			return "credit/creditPaidLoanFactorForm";
		} else if (paidLoan.getBusinessType().equals(CreditBusinessType.NOTES_DISCOUNTED)) {
			return "credit/creditPaidLoanNotesForm";
		} else if (paidLoan.getBusinessType().equals(CreditBusinessType.BANK_ACCEPTANCE)) {
			return "credit/creditPaidLoanBankForm";
		} else if (paidLoan.getBusinessType().equals(CreditBusinessType.LETTER_CREDIT)) {
			return "credit/creditPaidLoanCreditForm";
		} else {
			return "credit/creditPaidLoanGuaranteeForm";
		}	
		
	}
	
	/**
	 * 保存外部担保信息
	 * 2016年2月22日
	 * By 王浩
	 * @param enterpriseCredit
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="saveExternalGuarantee")
	public String saveExternalGuarantee(EnterpriseCredit enterpriseCredit){
		try {
			// 清空原有数据
			externalGuaranteeService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditExternalGuaranteeRecord> externalGuaranteeList = enterpriseCredit.getExternalGuaranteeList();
			if (ArrayHelper.isNotEmpty(externalGuaranteeList)) {
				for (CreditExternalGuaranteeRecord externalGuarantee : externalGuaranteeList) {
					externalGuarantee.setLoanCode(enterpriseCredit.getLoanCode());
					externalGuaranteeService.saveExternalGuarantee(externalGuarantee);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;		
		
	}
	
	/**
	 * 删除外部担保信息
	 * 2016年2月22日
	 * By 王浩
	 * @param externalGuarantee
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteGuaranteeById")
	public String deleteGuaranteeById(CreditExternalGuaranteeRecord externalGuarantee){
		try {
			// 删除处罚信息
			externalGuaranteeService.deleteGuaranteeById(externalGuarantee.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上外部担保信息
	 * 2016年2月22日
	 * By 王浩
	 * @param model
	 * @param externalGuarantee
	 * @return none
	 */
	@RequestMapping(value="initExternalGuarantee")
	public String initExternalGuarantee(Model model, CreditExternalGuaranteeRecord externalGuarantee) {
		List<CreditExternalGuaranteeRecord> externalGuaranteeList = externalGuaranteeService
				.selectByLoanCode(externalGuarantee.getLoanCode());
		model.addAttribute("externalGuaranteeList", externalGuaranteeList);
		return "credit/creditExternalGuaranteeForm";
	}
	
	/**
	 * 保存民事判决信息
	 * 2016年2月22日
	 * By 王浩
	 * @param enterpriseCredit
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="saveCivilJudgment")
	public String saveCivilJudgment(EnterpriseCredit enterpriseCredit){
		try {
			// 清空原有数据
			civilJudgmentService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditCivilJudgmentRecord> civilJudgmentList = enterpriseCredit.getCivilJudgmentList();
			if (ArrayHelper.isNotEmpty(civilJudgmentList)) {
				for (CreditCivilJudgmentRecord civilJudgment : civilJudgmentList) {
					civilJudgment.setLoanCode(enterpriseCredit.getLoanCode());
					civilJudgmentService.saveCivilJudgment(civilJudgment);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;		
		
	}
	
	/**
	 * 删除民事判决信息
	 * 2016年2月22日
	 * By 王浩
	 * @param civilJudgment
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteJudgmentById")
	public String deleteJudgmentById(CreditCivilJudgmentRecord civilJudgment){
		try {
			// 删除处罚信息
			civilJudgmentService.deleteJudgeById(civilJudgment.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上民事判决信息
	 * 2016年2月22日
	 * By 王浩
	 * @param model
	 * @param civilJudgment
	 * @return none
	 */
	@RequestMapping(value="initCivilJudgment")
	public String initCivilJudgment(Model model, CreditCivilJudgmentRecord civilJudgment) {
		List<CreditCivilJudgmentRecord> civilJudgmentList = civilJudgmentService
				.selectByLoanCode(civilJudgment.getLoanCode());
		model.addAttribute("civilJudgmentList", civilJudgmentList);
		return "credit/creditCivilJudgmentForm";
	}
	
	/**
	 * 保存贷款卡信息
	 * 2016年2月22日
	 * By 王浩
	 * @param enterpriseCredit
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditLoanCard")
	public String saveCreditLoanCard(EnterpriseCredit enterpriseCredit){
		try {
			// 清空原有数据
			creditLoanCardService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditLoanCard> creditLoanCardList = enterpriseCredit.getCreditLoanCardList();
			if (ArrayHelper.isNotEmpty(creditLoanCardList)) {
				for (CreditLoanCard creditLoanCard : creditLoanCardList) {
					creditLoanCard.setLoanCode(enterpriseCredit.getLoanCode());
					creditLoanCardService.saveCreditLoanCard(creditLoanCard);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;		
		
	}
	
	/**
	 * 删除贷款卡信息
	 * 2016年2月22日
	 * By 王浩
	 * @param creditLoanCard
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteLoanCardById")
	public String deleteCreditLoanCardById(CreditLoanCard creditLoanCard){
		try {
			// 删除处罚信息
			creditLoanCardService.deleteLoanCardById(creditLoanCard.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上贷款卡信息
	 * 2016年2月22日
	 * By 王浩
	 * @param model
	 * @param creditLoanCard
	 * @return none
	 */
	@RequestMapping(value="initCreditLoanCard")
	public String initCreditLoanCard(Model model, CreditLoanCard creditLoanCard) {
		List<CreditLoanCard> creditLoanCardList = creditLoanCardService
				.selectByLoanCode(creditLoanCard.getLoanCode());
		model.addAttribute("creditLoanCardList", creditLoanCardList);
		return "credit/creditLoanCardForm";
	}
	
	/**
	 * 保存评级信息
	 * 2016年2月22日
	 * By 王浩
	 * @param creditPunishList
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditGrade")
	public String saveCreditGrade(EnterpriseCredit enterpriseCredit){
		try {
			// 清空原有数据
			creditGradeService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditGrade> creditGradeList = enterpriseCredit.getCreditGradeList();
			if (ArrayHelper.isNotEmpty(creditGradeList)) {
				for (CreditGrade creditGrade : creditGradeList) {
					creditGrade.setLoanCode(enterpriseCredit.getLoanCode());
					creditGradeService.saveCreditGrade(creditGrade);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;		
		
	}
	
	/**
	 * 删除评级信息
	 * 2016年2月22日
	 * By 王浩
	 * @param creditGrade
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteGradeById")
	public String deleteCreditGradeById(CreditGrade creditGrade){
		try {
			// 删除处罚信息
			creditGradeService.deleteGradeById(creditGrade.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上评级信息
	 * 2016年2月22日
	 * By 王浩
	 * @param model
	 * @param creditGrade
	 * @return none
	 */
	@RequestMapping(value="initCreditGrade")
	public String initCreditGrade(Model model, CreditGrade creditGrade) {
		List<CreditGrade> creditGradeList = creditGradeService
				.selectByLoanCode(creditGrade.getLoanCode());
		model.addAttribute("creditGradeList", creditGradeList);
		return "credit/creditGradeForm";
	}
	
	/**
	 * 保存处罚信息
	 * 2016年2月22日
	 * By 王浩
	 * @param enterpriseCredit
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditPunish")
	public String saveCreditPunish(EnterpriseCredit enterpriseCredit){
		try {
			// 清空原有数据
			creditPunishService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditPunish> creditPunishList = enterpriseCredit.getCreditPunishList();
			if (ArrayHelper.isNotEmpty(creditPunishList)) {
				for (CreditPunish creditPunish : creditPunishList) {
					creditPunish.setLoanCode(enterpriseCredit.getLoanCode());
					creditPunishService.saveCreditPunish(creditPunish);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;		
		
	}
	
	/**
	 * 删除处罚信息
	 * 2016年2月22日
	 * By 王浩
	 * @param creditPunish
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deletePunishById")
	public String deleteCreditPunishById(CreditPunish creditPunish){
		try {
			// 删除处罚信息
			creditPunishService.deletePunishById(creditPunish.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上处罚信息
	 * 2016年2月22日
	 * By 王浩
	 * @param model
	 * @param creditPunish
	 * @return none
	 */
	@RequestMapping(value="initCreditPunish")
	public String initCreditPunish(Model model, CreditPunish creditPunish) {
		List<CreditPunish> creditPunishList = creditPunishService
				.selectByLoanCode(creditPunish.getLoanCode());
		model.addAttribute("creditPunishList", creditPunishList);
		return "credit/creditPunishForm";
	}
	
	/**
	 * 保存出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditAffiliatedEnterprise")
	public String saveCreditAffiliatedEnterprise(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditAffiliatedEnterpriseService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditAffiliatedEnterprise> creditAffiliatedEnterpriseList = enterpriseCredit.getCreditAffiliatedEnterpriseList();
			if (ArrayHelper.isNotEmpty(creditAffiliatedEnterpriseList)) {
				for (CreditAffiliatedEnterprise creditAffiliatedEnterprise : creditAffiliatedEnterpriseList) {
					creditAffiliatedEnterprise.setLoanCode(enterpriseCredit.getLoanCode());
					creditAffiliatedEnterpriseService.insertCreditAffiliatedEnterprise(creditAffiliatedEnterprise);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  出资人信息地址
	 */
	@RequestMapping(value = "initCreditAffiliatedEnterprise")
	public String initCreditAffiliatedEnterprise(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取出资人信息记录
		List<CreditAffiliatedEnterprise> creditAffiliatedEnterpriseList = creditAffiliatedEnterpriseService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditAffiliatedEnterpriseList", creditAffiliatedEnterpriseList);
		return "credit/creditAffiliatedEnterpriseForm";		
	}
	
	/**
	 * 删除出资人信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditAffiliatedEnterprise
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditAffiliatedEnterpriseById")
	public String deleteCreditAffiliatedEnterpriseById(CreditAffiliatedEnterprise creditAffiliatedEnterprise){

		try {
			// 删除信息
			creditAffiliatedEnterpriseService.deleteByPrimaryKey(creditAffiliatedEnterprise.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存企业征信信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveEnterpriseCredit")
	public String saveEnterpriseCredit(EnterpriseCredit enterpriseCredit){

		try {
			//设置借款编码
			String loanCode = enterpriseCredit.getLoanCode();
			loanCode = loanCode.split(",")[0];
			enterpriseCredit.setLoanCode(loanCode);
			
			EnterpriseCredit eCreditFromDB = enterpriseCreditService.selectByEnterpriseCredit(enterpriseCredit);
			// 数据库中已有该记录
			if (eCreditFromDB != null && StringUtils.isNotEmpty(eCreditFromDB.getLoanCode())) {
				// 更新记录
				enterpriseCreditService.updateEnterpriseCredit(enterpriseCredit);
			} else {
				// 插入新纪录
				enterpriseCreditService.insertEnterpriseCredit(enterpriseCredit);
			}			

			//保存基础信息
			creditBasicInfoService.saveCreditBasicInfo(enterpriseCredit.getCreditBasicInfo());

			//保存当前负债信息概要
			List<CreditCurrentLiabilityInfo> creditCurrentLiabilityInfoList = enterpriseCredit.getCreditCurrentLiabilityInfoList();
			if (ArrayHelper.isNotEmpty(creditCurrentLiabilityInfoList)) {
				for (CreditCurrentLiabilityInfo creditCurrentLiabilityInfo : creditCurrentLiabilityInfoList) {
					creditCurrentLiabilityInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditCurrentLiabilityInfoService.saveCreditCurrentLiabilityInfo(creditCurrentLiabilityInfo);
				}
			}
			
			//当前负债信息明细
			List<CreditCurrentLiabilityDetail> creditCurrentLiabilityDetailList = enterpriseCredit.getCreditCurrentLiabilityDetailList();
			if (ArrayHelper.isNotEmpty(creditCurrentLiabilityDetailList)) {
				for (CreditCurrentLiabilityDetail creditCurrentLiabilityDetail : creditCurrentLiabilityDetailList) {
					creditCurrentLiabilityDetail.setLoanCode(enterpriseCredit.getLoanCode());

					creditCurrentLiabilityDetailService.saveCreditCurrentLiabilityDetail(creditCurrentLiabilityDetail);
				}
			}
			
			//对外担保信息概要
			List<CreditExternalSecurityInfo> creditExternalSecurityInfoList = enterpriseCredit.getCreditExternalSecurityInfoList();
			if (ArrayHelper.isNotEmpty(creditExternalSecurityInfoList)) {
				for (CreditExternalSecurityInfo creditExternalSecurityInfo : creditExternalSecurityInfoList) {
					creditExternalSecurityInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditExternalSecurityInfoService.saveCreditExternalSecurityInfo(creditExternalSecurityInfo);
				}
			}
			
			//企业征信_已结清信贷信息
			List<CreditCreditClearedInfo> creditCreditClearedInfoList = enterpriseCredit.getCreditCreditClearedInfoList();
			if (ArrayHelper.isNotEmpty(creditCreditClearedInfoList)) {
				for (CreditCreditClearedInfo creditCreditClearedInfo : creditCreditClearedInfoList) {
					creditCreditClearedInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditCreditClearedInfoService.saveCreditCreditClearedInfo(creditCreditClearedInfo);
				}
			}
			
			//企业征信_已结清信贷信息
			List<CreditCreditClearedDetail> creditCreditClearedDetailList = enterpriseCredit.getCreditCreditClearedDetailList();
			if (ArrayHelper.isNotEmpty(creditCreditClearedDetailList)) {
				for (CreditCreditClearedDetail creditCreditClearedDetail : creditCreditClearedDetailList) {
					creditCreditClearedDetail.setLoanCode(enterpriseCredit.getLoanCode());
					creditCreditClearedDetailService.saveCreditCreditClearedDetail(creditCreditClearedDetail);
				}
			}
			
			//企业征信_出资人信息
			// 清空原有数据
			creditInvestorInfoService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditInvestorInfo> creditInvestorInfoList = enterpriseCredit.getCreditInvestorInfoList();
			if (ArrayHelper.isNotEmpty(creditInvestorInfoList)) {
				for (CreditInvestorInfo creditInvestorInfo : creditInvestorInfoList) {
					creditInvestorInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditInvestorInfoService.insertCreditInvestorInfo(creditInvestorInfo);
				}
			}
			
			// 处罚信息		
			// 清空原有数据
			creditPunishService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditPunish> creditPunishList = enterpriseCredit.getCreditPunishList();
			if (ArrayHelper.isNotEmpty(creditPunishList)) {
				for (CreditPunish creditPunish : creditPunishList) {
					creditPunish.setLoanCode(enterpriseCredit.getLoanCode());
					creditPunishService.saveCreditPunish(creditPunish);
				}
			}
			
			// 评级信息
			// 清空原有数据
			creditGradeService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditGrade> creditGradeList = enterpriseCredit.getCreditGradeList();
			if (ArrayHelper.isNotEmpty(creditGradeList)) {
				for (CreditGrade creditGrade : creditGradeList) {
					creditGrade.setLoanCode(enterpriseCredit.getLoanCode());
					creditGradeService.saveCreditGrade(creditGrade);
				}
			}
			
			// 贷款卡信息		
			// 清空原有数据
			creditLoanCardService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditLoanCard> creditLoanCardList = enterpriseCredit.getCreditLoanCardList();
			if (ArrayHelper.isNotEmpty(creditLoanCardList)) {
				for (CreditLoanCard creditLoanCard : creditLoanCardList) {
					creditLoanCard.setLoanCode(enterpriseCredit.getLoanCode());

					creditLoanCardService.saveCreditLoanCard(creditLoanCard);
				}
			}
			
			// 民事判决信息拼接查询信息卡		
			// 清空原有数据
			civilJudgmentService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditCivilJudgmentRecord> civilJudgmentList = enterpriseCredit.getCivilJudgmentList();
			if (ArrayHelper.isNotEmpty(civilJudgmentList)) {
				for (CreditCivilJudgmentRecord civilJudgment : civilJudgmentList) {
					civilJudgment.setLoanCode(enterpriseCredit.getLoanCode());
					civilJudgmentService.saveCivilJudgment(civilJudgment);
				}
			}
			
			// 外部担保信息		
			// 清空原有数据
			externalGuaranteeService.deleteByRelationId(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditExternalGuaranteeRecord> externalGuaranteeList = enterpriseCredit.getExternalGuaranteeList();
			if (ArrayHelper.isNotEmpty(externalGuaranteeList)) {
				for (CreditExternalGuaranteeRecord externalGuarantee : externalGuaranteeList) {
					externalGuarantee.setLoanCode(enterpriseCredit.getLoanCode());
					externalGuaranteeService.saveExternalGuarantee(externalGuarantee);
				}
			}
			
			//高管人员信息
			// 清空原有数据
			creditExecutiveInfoService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditExecutiveInfo> creditExecutiveInfoList = enterpriseCredit.getCreditExecutiveInfoList();
			if (ArrayHelper.isNotEmpty(creditExecutiveInfoList)) {
				for (CreditExecutiveInfo creditExecutiveInfo : creditExecutiveInfoList) {
					creditExecutiveInfo.setLoanCode(enterpriseCredit.getLoanCode());

					creditExecutiveInfoService.insertCreditExecutiveInfo(creditExecutiveInfo);
				}
			}
			
			//直接关联企业
			// 清空原有数据
			creditAffiliatedEnterpriseService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditAffiliatedEnterprise> creditAffiliatedEnterpriseList = enterpriseCredit.getCreditAffiliatedEnterpriseList();
			if (ArrayHelper.isNotEmpty(creditAffiliatedEnterpriseList)) {
				for (CreditAffiliatedEnterprise creditAffiliatedEnterprise : creditAffiliatedEnterpriseList) {
					creditAffiliatedEnterprise.setLoanCode(enterpriseCredit.getLoanCode());

					creditAffiliatedEnterpriseService.insertCreditAffiliatedEnterprise(creditAffiliatedEnterprise);
				}
			}
			
			//企业征信_负债历史变化
			// 清空原有数据
			creditLiabilityHisService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditLiabilityHis> creditLiabilityHisList = enterpriseCredit.getCreditLiabilityHisList();
			if (ArrayHelper.isNotEmpty(creditLiabilityHisList)) {
				for (CreditLiabilityHis creditLiabilityHis : creditLiabilityHisList) {
					creditLiabilityHis.setLoanCode(enterpriseCredit.getLoanCode());

					creditLiabilityHisService.insertCreditLiabilityHis(creditLiabilityHis);
				}
			}
			
			//企业征信_未结清贷款
			// 清空原有数据
			creditUnclearedLoanService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedLoan> creditUnclearedLoanList = enterpriseCredit.getCreditUnclearedLoanList();
			if (ArrayHelper.isNotEmpty(creditUnclearedLoanList)) {
				for (CreditUnclearedLoan creditUnclearedLoan : creditUnclearedLoanList) {
					creditUnclearedLoan.setLoanCode(enterpriseCredit.getLoanCode());

					creditUnclearedLoanService.insertCreditUnclearedLoan(creditUnclearedLoan);
				}
			}
			
			//企业征信_未结清贸易融资
			// 清空原有数据
			creditUnclearedTradeFinancingService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedTradeFinancing> creditUnclearedTradeFinancingList = enterpriseCredit.getCreditUnclearedTradeFinancingList();
			if (ArrayHelper.isNotEmpty(creditUnclearedTradeFinancingList)) {
				for (CreditUnclearedTradeFinancing creditUnclearedTradeFinancing : creditUnclearedTradeFinancingList) {
					creditUnclearedTradeFinancing.setLoanCode(enterpriseCredit.getLoanCode());

					creditUnclearedTradeFinancingService.insertCreditUnclearedTradeFinancing(creditUnclearedTradeFinancing);
				}
			}
			
			//企业征信_未结清贸易融资
			// 清空原有数据
			creditUnclearedFactoringService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedFactoring> creditUnclearedFactoringList = enterpriseCredit.getCreditUnclearedFactoringList();
			if (ArrayHelper.isNotEmpty(creditUnclearedFactoringList)) {
				for (CreditUnclearedFactoring creditUnclearedFactoring : creditUnclearedFactoringList) {
					creditUnclearedFactoring.setLoanCode(enterpriseCredit.getLoanCode());

					creditUnclearedFactoringService.insertCreditUnclearedFactoring(creditUnclearedFactoring);
				}
			}
			
			//企业征信_未结清贸易融资
			// 清空原有数据
			creditUnclearedNotesDiscountedService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedNotesDiscounted> creditUnclearedNotesDiscountedList = enterpriseCredit.getCreditUnclearedNotesDiscountedList();
			if (ArrayHelper.isNotEmpty(creditUnclearedNotesDiscountedList)) {
				for (CreditUnclearedNotesDiscounted creditUnclearedNotesDiscounted : creditUnclearedNotesDiscountedList) {
					creditUnclearedNotesDiscounted.setLoanCode(enterpriseCredit.getLoanCode());

					creditUnclearedNotesDiscountedService.insertCreditUnclearedNotesDiscounted(creditUnclearedNotesDiscounted);
				}
			}
			
			//企业征信_未结清银行承兑汇票
			// 清空原有数据
			creditUnclearedBankAcceptanceService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedBankAcceptance> creditUnclearedBankAcceptanceList = enterpriseCredit.getCreditUnclearedBankAcceptanceList();
			if (ArrayHelper.isNotEmpty(creditUnclearedBankAcceptanceList)) {
				for (CreditUnclearedBankAcceptance creditUnclearedBankAcceptance : creditUnclearedBankAcceptanceList) {
					creditUnclearedBankAcceptance.setLoanCode(enterpriseCredit.getLoanCode());

					creditUnclearedBankAcceptanceService.insertCreditUnclearedBankAcceptance(creditUnclearedBankAcceptance);
				}
			}			
			
			//企业征信_未结清信用证
			// 清空原有数据
			creditUnclearedLetterCreditService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedLetterCredit> creditUnclearedLetterCreditList = enterpriseCredit.getCreditUnclearedLetterCreditList();
			if (ArrayHelper.isNotEmpty(creditUnclearedLetterCreditList)) {
				for (CreditUnclearedLetterCredit creditUnclearedLetterCredit : creditUnclearedLetterCreditList) {
					creditUnclearedLetterCredit.setLoanCode(enterpriseCredit.getLoanCode());

					creditUnclearedLetterCreditService.insertCreditUnclearedLetterCredit(creditUnclearedLetterCredit);
				}
			}
			
			//企业征信_未结清保函
			// 清空原有数据
			creditUnclearedLetterGuaranteeService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedLetterGuarantee> creditUnclearedLetterGuaranteeList = enterpriseCredit.getCreditUnclearedLetterGuaranteeList();
			if (ArrayHelper.isNotEmpty(creditUnclearedLetterGuaranteeList)) {
				for (CreditUnclearedLetterGuarantee creditUnclearedLetterGuarantee : creditUnclearedLetterGuaranteeList) {
					creditUnclearedLetterGuarantee.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedLetterGuaranteeService.insertCreditUnclearedLetterGuarantee(creditUnclearedLetterGuarantee);
				}
			}
			
			//企业征信_未结清非正常贷款
			List<CreditUnclearedImproperLoan> unclearedImproperLoanList = enterpriseCredit.getCreditUnclearedImproperLoanList();
			if (ArrayHelper.isNotEmpty(unclearedImproperLoanList)) {
				CreditUnclearedImproperLoan unclearedImproperLoan = new CreditUnclearedImproperLoan();
				unclearedImproperLoan.setLoanCode(enterpriseCredit.getLoanCode());
				// 清空原有数据
				unclearedImproperLoanService.deleteByUnclearedImproperLoan(unclearedImproperLoan);
				// 保存现有数据
				for (CreditUnclearedImproperLoan uncleared : unclearedImproperLoanList) {
					uncleared.setLoanCode(enterpriseCredit.getLoanCode());
					unclearedImproperLoanService.saveUnclearedImproperLoan(uncleared);
				}
			}
			
			//企业征信_已结清贷款
			List<CreditPaidLoan> paidLoanList = enterpriseCredit.getPaidLoanList();
			if (ArrayHelper.isNotEmpty(paidLoanList)) {
				CreditPaidLoan creditPaidLoan = new CreditPaidLoan();
				creditPaidLoan.setLoanCode(enterpriseCredit.getLoanCode());
				// 清空原有数据
				paidLoanService.deleteByPaidLoanInfo(creditPaidLoan);
				// 保存现有数据
				for (CreditPaidLoan paidLoan : paidLoanList) {
					paidLoanService.savePaidLoan(paidLoan);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存负债历史变化信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditLiabilityHis")
	public String saveCreditLiabilityHis(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditLiabilityHisService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditLiabilityHis> creditLiabilityHisList = enterpriseCredit.getCreditLiabilityHisList();
			if (ArrayHelper.isNotEmpty(creditLiabilityHisList)) {
				for (CreditLiabilityHis creditLiabilityHis : creditLiabilityHisList) {
					creditLiabilityHis.setLoanCode(enterpriseCredit.getLoanCode());
					creditLiabilityHisService.insertCreditLiabilityHis(creditLiabilityHis);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化负债历史变化信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  负债历史变化信息地址
	 */
	@RequestMapping(value = "initCreditLiabilityHis")
	public String initCreditLiabilityHis(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取负债历史变化信息记录
		List<CreditLiabilityHis> creditLiabilityHisList = creditLiabilityHisService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditLiabilityHisList", creditLiabilityHisList);
		return "credit/creditLiabilityHisForm";		
	}
	
	/**
	 * 删除负债历史变化信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param CreditLiabilityHis
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditLiabilityHisById")
	public String deleteCreditLiabilityHisById(CreditLiabilityHis creditLiabilityHis){

		try {
			// 删除信息
			creditLiabilityHisService.deleteByPrimaryKey(creditLiabilityHis.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清贷款信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedLoan")
	public String saveCreditUnclearedLoan(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedLoanService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedLoan> creditUnclearedLoanList = enterpriseCredit.getCreditUnclearedLoanList();
			if (ArrayHelper.isNotEmpty(creditUnclearedLoanList)) {
				for (CreditUnclearedLoan creditUnclearedLoan : creditUnclearedLoanList) {
					creditUnclearedLoan.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedLoanService.insertCreditUnclearedLoan(creditUnclearedLoan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清贷款信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清贷款信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedLoan")
	public String initCreditUnclearedLoan(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取未结清贷款信息记录
		List<CreditUnclearedLoan> creditUnclearedLoanList = creditUnclearedLoanService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedLoanList", creditUnclearedLoanList);
		return "credit/creditUnclearedLoanForm";		
	}
	
	/**
	 * 删除未结清贷款信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedLoan
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedLoanById")
	public String deleteCreditUnclearedLoanById(CreditUnclearedLoan creditUnclearedLoan){

		try {
			// 删除信息
			creditUnclearedLoanService.deleteByPrimaryKey(creditUnclearedLoan.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清贸易融资信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedTradeFinancing")
	public String saveCreditUnclearedTradeFinancing(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedTradeFinancingService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedTradeFinancing> creditUnclearedTradeFinancingList = enterpriseCredit.getCreditUnclearedTradeFinancingList();
			if (ArrayHelper.isNotEmpty(creditUnclearedTradeFinancingList)) {
				for (CreditUnclearedTradeFinancing creditUnclearedTradeFinancing : creditUnclearedTradeFinancingList) {
					creditUnclearedTradeFinancing.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedTradeFinancingService.insertCreditUnclearedTradeFinancing(creditUnclearedTradeFinancing);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清贸易融资信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清贸易融资信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedTradeFinancing")
	public String initCreditUnclearedTradeFinancing(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取未结清贸易融资信息记录
		List<CreditUnclearedTradeFinancing> creditUnclearedTradeFinancingList = creditUnclearedTradeFinancingService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedTradeFinancingList", creditUnclearedTradeFinancingList);
		return "credit/creditUnclearedTradeFinancingForm";		
	}
	
	/**
	 * 删除未结清贸易融资信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedTradeFinancing
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedTradeFinancingById")
	public String deleteCreditUnclearedTradeFinancingById(CreditUnclearedTradeFinancing creditUnclearedTradeFinancing){

		try {
			// 删除信息
			creditUnclearedTradeFinancingService.deleteByPrimaryKey(creditUnclearedTradeFinancing.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清保理信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedFactoring")
	public String saveCreditUnclearedFactoring(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedFactoringService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedFactoring> creditUnclearedFactoringList = enterpriseCredit.getCreditUnclearedFactoringList();
			if (ArrayHelper.isNotEmpty(creditUnclearedFactoringList)) {
				for (CreditUnclearedFactoring creditUnclearedFactoring : creditUnclearedFactoringList) {
					creditUnclearedFactoring.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedFactoringService.insertCreditUnclearedFactoring(creditUnclearedFactoring);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清保理信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清保理信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedFactoring")
	public String initCreditUnclearedFactoring(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取未结清保理信息记录
		List<CreditUnclearedFactoring> creditUnclearedFactoringList = creditUnclearedFactoringService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedFactoringList", creditUnclearedFactoringList);
		return "credit/creditUnclearedFactoringForm";		
	}
	
	/**
	 * 删除未结清保理信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedFactoring
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedFactoringById")
	public String deleteCreditUnclearedFactoringById(CreditUnclearedFactoring creditUnclearedFactoring){

		try {
			// 删除信息
			creditUnclearedFactoringService.deleteByPrimaryKey(creditUnclearedFactoring.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清票据贴现信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedNotesDiscounted")
	public String saveCreditUnclearedNotesDiscounted(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedNotesDiscountedService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedNotesDiscounted> creditUnclearedNotesDiscountedList = enterpriseCredit.getCreditUnclearedNotesDiscountedList();
			if (ArrayHelper.isNotEmpty(creditUnclearedNotesDiscountedList)) {
				for (CreditUnclearedNotesDiscounted creditUnclearedNotesDiscounted : creditUnclearedNotesDiscountedList) {
					creditUnclearedNotesDiscounted.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedNotesDiscountedService.insertCreditUnclearedNotesDiscounted(creditUnclearedNotesDiscounted);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清票据贴现信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清票据贴现信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedNotesDiscounted")
	public String initCreditUnclearedNotesDiscounted(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取未结清票据贴现信息记录
		List<CreditUnclearedNotesDiscounted> creditUnclearedNotesDiscountedList = creditUnclearedNotesDiscountedService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedNotesDiscountedList", creditUnclearedNotesDiscountedList);
		return "credit/creditUnclearedNotesDiscountedForm";		
	}
	
	/**
	 * 删除未结清票据贴现信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedNotesDiscounted
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedNotesDiscountedById")
	public String deleteCreditUnclearedNotesDiscountedById(CreditUnclearedNotesDiscounted creditUnclearedNotesDiscounted){

		try {
			// 删除信息
			creditUnclearedNotesDiscountedService.deleteByPrimaryKey(creditUnclearedNotesDiscounted.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清银行承兑汇票信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedBankAcceptance")
	public String saveCreditUnclearedBankAcceptance(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedBankAcceptanceService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedBankAcceptance> creditUnclearedBankAcceptanceList = enterpriseCredit.getCreditUnclearedBankAcceptanceList();
			if (ArrayHelper.isNotEmpty(creditUnclearedBankAcceptanceList)) {
				for (CreditUnclearedBankAcceptance creditUnclearedBankAcceptance : creditUnclearedBankAcceptanceList) {
					creditUnclearedBankAcceptance.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedBankAcceptanceService.insertCreditUnclearedBankAcceptance(creditUnclearedBankAcceptance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清银行承兑汇票信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清银行承兑汇票信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedBankAcceptance")
	public String initCreditUnclearedBankAcceptance(Model model, EnterpriseCredit enterpriseCredit) {					
		// 根据借款编码获取未结清银行承兑汇票信息记录
		List<CreditUnclearedBankAcceptance> creditUnclearedBankAcceptanceList = creditUnclearedBankAcceptanceService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedBankAcceptanceList", creditUnclearedBankAcceptanceList);
		return "credit/creditUnclearedBankAcceptanceForm";		
	}
	
	/**
	 * 删除未结清银行承兑汇票信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedBankAcceptance
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedBankAcceptanceById")
	public String deleteCreditUnclearedBankAcceptanceById(CreditUnclearedBankAcceptance creditUnclearedBankAcceptance){
		try {
			// 删除信息
			creditUnclearedBankAcceptanceService.deleteByPrimaryKey(creditUnclearedBankAcceptance.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清信用证信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedLetterCredit")
	public String saveCreditUnclearedLetterCredit(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedLetterCreditService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedLetterCredit> creditUnclearedLetterCreditList = enterpriseCredit.getCreditUnclearedLetterCreditList();
			if (ArrayHelper.isNotEmpty(creditUnclearedLetterCreditList)) {
				for (CreditUnclearedLetterCredit creditUnclearedLetterCredit : creditUnclearedLetterCreditList) {
					creditUnclearedLetterCredit.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedLetterCreditService.insertCreditUnclearedLetterCredit(creditUnclearedLetterCredit);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清信用证信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清信用证信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedLetterCredit")
	public String initCreditUnclearedLetterCredit(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取未结清信用证信息记录
		List<CreditUnclearedLetterCredit> creditUnclearedLetterCreditList = creditUnclearedLetterCreditService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedLetterCreditList", creditUnclearedLetterCreditList);
		return "credit/creditUnclearedLetterCreditForm";		
	}
	
	/**
	 * 删除未结清信用证信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedLetterCredit
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedLetterCreditById")
	public String deleteCreditUnclearedLetterCreditById(CreditUnclearedLetterCredit creditUnclearedLetterCredit){

		try {
			// 删除信息
			creditUnclearedLetterCreditService.deleteByPrimaryKey(creditUnclearedLetterCredit.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存未结清保函信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param enterpriseCredit
	 * @return String 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="saveCreditUnclearedLetterGuarantee")
	public String saveCreditUnclearedLetterGuarantee(EnterpriseCredit enterpriseCredit){

		try {
			// 清空原有数据
			creditUnclearedLetterGuaranteeService.deleteByLoanCode(enterpriseCredit.getLoanCode());
			// 保存现有数据
			List<CreditUnclearedLetterGuarantee> creditUnclearedLetterGuaranteeList = enterpriseCredit.getCreditUnclearedLetterGuaranteeList();
			if (ArrayHelper.isNotEmpty(creditUnclearedLetterGuaranteeList)) {
				for (CreditUnclearedLetterGuarantee creditUnclearedLetterGuarantee : creditUnclearedLetterGuaranteeList) {
					creditUnclearedLetterGuarantee.setLoanCode(enterpriseCredit.getLoanCode());
					creditUnclearedLetterGuaranteeService.insertCreditUnclearedLetterGuarantee(creditUnclearedLetterGuarantee);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 初始化未结清保函信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param model
	 * @param enterpriseCredit
	 * @return  未结清保函信息地址
	 */
	@RequestMapping(value = "initCreditUnclearedLetterGuarantee")
	public String initCreditUnclearedLetterGuarantee(Model model, EnterpriseCredit enterpriseCredit) {			
		
		// 根据借款编码获取未结清保函信息记录
		List<CreditUnclearedLetterGuarantee> creditUnclearedLetterGuaranteeList = creditUnclearedLetterGuaranteeService.selectByLoanCode(enterpriseCredit.getLoanCode());
		model.addAttribute("creditUnclearedLetterGuaranteeList", creditUnclearedLetterGuaranteeList);
		return "credit/creditUnclearedLetterGuaranteeForm";		
	}
	
	/**
	 * 删除未结清保函信息
	 * 2016年1月29日
	 * By zhanghu
	 * @param creditUnclearedLetterGuarantee
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="deleteCreditUnclearedLetterGuaranteeById")
	public String deleteCreditUnclearedLetterGuaranteeById(CreditUnclearedLetterGuarantee creditUnclearedLetterGuarantee){

		try {
			// 删除信息
			creditUnclearedLetterGuaranteeService.deleteByPrimaryKey(creditUnclearedLetterGuarantee.getId());

		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 保存
	 * 2016年2月25日
	 * By 侯志斌
	 * @param enterpriseCredit
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="saveUnclearedImproperLoan")
	public String saveUnclearedImproperLoan(EnterpriseCredit enterpriseCredit, String businessType){
		try {			
			List<CreditUnclearedImproperLoan> unclearedImproperLoanList = enterpriseCredit.getCreditUnclearedImproperLoanList();
			if (ArrayHelper.isNotEmpty(unclearedImproperLoanList)) {
				businessType = unclearedImproperLoanList.get(0).getBusinessType();
				CreditUnclearedImproperLoan unclearedImproperLoan = new CreditUnclearedImproperLoan();
				unclearedImproperLoan.setLoanCode(enterpriseCredit.getLoanCode());
				unclearedImproperLoan.setBusinessType(businessType);
				// 清空原有数据
				unclearedImproperLoanService.deleteByUnclearedImproperLoan(unclearedImproperLoan);
				// 保存现有数据
				for (CreditUnclearedImproperLoan uncleared : unclearedImproperLoanList) {
					 
					unclearedImproperLoanService.saveUnclearedImproperLoan(uncleared);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		} 
		
		if (StringUtils.isNotEmpty(businessType)) {
			return businessType;
		} else {
			return BooleanType.TRUE;
		}		
	}
	
	
	/**
	 * 删除未结清业务:不良、关注类信息
	 * 2016年2月25日
	 * By 侯志斌
	 * @param unclearedImproperLoan
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping(value="deleteUnclearedImproperLoanById")
	public String deleteUnclearedImproperLoanById(CreditUnclearedImproperLoan unclearedImproperLoan){
		try {
			unclearedImproperLoanService.deleteUnclearedImproperLoanById(unclearedImproperLoan.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 刷新页面上未结清业务:不良、关注类信息
	 * 2016年2月25日
	 * By 侯志斌
	 * @param model
	 * @param paidLoan
	 * @return none
	 */
	@RequestMapping(value="initUnclearedImproperLoan")
	public String initUnclearedImproperLoan(Model model, CreditUnclearedImproperLoan unclearedImproperLoan) {
		List<CreditUnclearedImproperLoan> unclearedImproperLoanList = unclearedImproperLoanService.selectByCreditUnclearedImproperLoan(unclearedImproperLoan);
		model.addAttribute("unclearedImproperLoanList", unclearedImproperLoanList);
		
		if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.LOAN)) {
			return "credit/creditUnclearLoanLoanForm";
		} else if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.TRADE_FINANCING)) {
			return "credit/creditUnclearLoanTradeForm";
		} else if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.FACTORING)) {
			return "credit/creditUnclearLoanFactorForm";
		} else if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.NOTES_DISCOUNTED)) {
			return "credit/creditUnclearLoanNotesForm";
		} else if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.BANK_ACCEPTANCE)) {
			return "credit/creditUnclearLoanBankForm";
		} else if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.LETTER_CREDIT)) {
			return "credit/creditUnclearLoanGuaranteeForm";
		} else if (unclearedImproperLoan.getBusinessType().equals(CreditBusinessType.LETTER_GUARANTEE)) {
			return "credit/creditUnclearLoanLetterForm";
		} else {
			return "credit/creditUnclearLoanGuaranteeForm";
		}			
	}	
	
}
