package com.creditharmony.loan.credit.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.CreditReportRisk;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.credit.entity.CreditLiveInfo;
import com.creditharmony.loan.credit.entity.CreditOccupationInfo;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.CreditReportSimple;
import com.creditharmony.loan.credit.entity.ex.DetailedParamEx;
import com.creditharmony.loan.credit.service.CreditDetailedInfoService;
import com.creditharmony.loan.credit.service.CreditReportSimpleService;

/**
 * 详版个人信息
 * @Class Name CreditDetailedInfoController
 * @author 李文勇
 * @Create In 2016年2月16日
 */
@Controller
@RequestMapping(value = "${adminPath}/creditdetailed/info")
public class CreditDetailedInfoController extends BaseController{
	
	@Autowired
	private CityInfoService cityInfoService;
	@Autowired
	private CreditDetailedInfoService creditDetailedInfoService;
	@Autowired
	private CreditReportSimpleService creditReportSimpleService;
	@Autowired
	ReconsiderApplyDao reconsiderApplyDao;
	@Autowired
	ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	LoanCoborrowerDao loanCoborrowerDao;
	
	/**
	 * 显示画面
	 * 2016年2月16日
	 * By 李文勇
	 * @return
	 */
	@RequestMapping(value="detail")
	public String detail(Model model,String rId , String customerType , String loanCode , String version){
		
		CreditReportRisk creditReportRisk = new CreditReportRisk();
		creditReportRisk.setLoanCode(loanCode);
		creditReportRisk.setrId(rId);
		creditReportRisk.setDictCustomerType(customerType);
		creditReportRisk.setRiskCreditVersion(version);
		model.addAttribute("param", creditReportRisk);
		// 如果为共借人，获取共借人身份证号
		if(LoanManFlag.COBORROWE_LOAN.getCode().equals(customerType)){
			LoanCoborrower result = creditDetailedInfoService.selectCoboNameAndCertNum(rId);
			if(result != null){
				model.addAttribute("applyCertNum", result.getCoboCertNum());
			}
		}
		// 如果为主借人，获取主借人身份证号
		if(LoanManFlag.MAIN_LOAN.getCode().equals(customerType)){
			LoanCustomer result = creditDetailedInfoService.getCustomer(rId);
			if(result != null){
				model.addAttribute("applyCertNum", result.getCustomerCertNum());
			}
		}
		
		return "credit/detail";
	}
	
	/**
	 * 2016年3月14日
	 * By 李文勇
	 * @param model
	 * @param creditReportSimple
	 * @return
	 */
	@RequestMapping(value = "checkLoanMan")
	public String checkLoanMan(Model model, CreditReportSimple creditReportSimple, String loanInfoOldOrNewFlag) {
		
		ReconsiderApply reconsider = reconsiderApplyDao.selectByApply(creditReportSimple.getApplyId());
		if(reconsider != null){
			String loanCode = reconsider.getLoanCode();
			LoanInfo info = applyLoanInfoDao.getByLoanCode(loanCode);
			creditReportSimple.setApplyId(info.getApplyId());
		}
		
		// 获取主借人信息
		LoanCustomer loanCustomer = creditReportSimpleService.selectByApplyId(creditReportSimple.getApplyId());
		creditReportSimple.setLoanCode(loanCustomer.getLoanCode());//借款编号
		creditReportSimple.setCustomerId(loanCustomer.getId());//主借人编号
		
		// 获取共借人信息
		List<LoanCoborrower> LoanCoborrowerList = creditReportSimpleService.selectByLoanCode(creditReportSimple.getLoanCode());
		
		model.addAttribute("loanCode", loanCustomer.getLoanCode());
		model.addAttribute("loanCustomer", loanCustomer);
		model.addAttribute("creditReportSimple", creditReportSimple);
		model.addAttribute("LoanCoborrowerList", LoanCoborrowerList);
		model.addAttribute("loanCode", loanCustomer.getLoanCode());
		model.addAttribute("loanInfoOldOrNewFlag", loanInfoOldOrNewFlag);
		// 弹出临时界面
		return "borrow/borrowlist/checkCustomer";
		
	}
	
	/**
	 * 获取征信核查列表
	 * 2016年3月16日
	 * By 李文勇
	 * @param loanCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getRiskList")
	public List<CreditReportRisk> getRiskList(String loanCode){
		
		CreditReportRisk creditReportRisk = new CreditReportRisk();
		creditReportRisk.setLoanCode(loanCode);
		List<CreditReportRisk> riskList = creditDetailedInfoService.getCreditReportDetailedByCode(creditReportRisk);
		
		return riskList;
		
	}
	
	/**
	 * 显示画面
	 * 2016年2月16日
	 * By 李文勇
	 * @return
	 */
	@RequestMapping(value="initPage")
	public String initPage(Model model){
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList",provinceList);
		return "credit/personalIdentityInformation";
	}
	
	/**
	 * 显示数据
	 * 2016年2月18日
	 * By 李文勇
	 * @return DetailedParamEx
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value="showData")
	public DetailedParamEx showData(CreditReportDetailed param) throws IllegalAccessException, InvocationTargetException{
		DetailedParamEx detailedParamEx = new DetailedParamEx();
		if(param != null && StringUtils.isNotEmpty(param.getLoanCode())
				&& StringUtils.isNotEmpty(param.getrCustomerCoborrowerId())
				&& StringUtils.isNotEmpty(param.getDictCustomerType())){
			DetailedParamEx result = creditDetailedInfoService.showData(param);
			if(result != null){
				detailedParamEx = result;
			}
		}
		return detailedParamEx;
	}
	
	/**
	 * 保存数据
	 * 2016年2月17日
	 * By 李文勇
	 * @return String
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value="saveData")
	public String savaData(DetailedParamEx param) throws IllegalAccessException, InvocationTargetException{
		if(param != null && StringUtils.isNotEmpty(param.getLoanCode())
				&& StringUtils.isNotEmpty(param.getDictCustomerType())
				&& StringUtils.isNotEmpty(param.getrCustomerCoborrowerId())){
			creditDetailedInfoService.saveData(param);
			return "true";
		}else{
			return "false";
		}
	}
	
	/**
	 * 删除居住信息
	 * 2016年2月19日
	 * By 李文勇
	 * @param param
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="removeReportHouse")
	public String removeReportHouse(CreditLiveInfo param){
		int result = creditDetailedInfoService.removeReportHouse(param);
		if(result > 0){
			return "true";
		}
		return "false";
	}
	
	/**
	 * 删除职业信息
	 * 2016年2月19日
	 * By 李文勇
	 * @param param
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="removeReportWork")
	public String removeReportWork(CreditOccupationInfo param){
		int result = creditDetailedInfoService.removeReportWork(param);
		if(result > 0){
			return "true";
		}
		return "false";
	}
}
