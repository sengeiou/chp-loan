package com.creditharmony.loan.borrow.payback.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.service.DoAdvanceSettledService;
import com.creditharmony.loan.borrow.payback.service.EarlyFinishConfirmService;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * 提前结清待办Controller
 * 
 * @Class Name DoAdvanceSettledController
 * @author zhangfeng
 * @Create In 2015年12月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/doAdvanceSettled")
public class DoAdvanceSettledController extends BaseController {

	@Autowired
	private DoAdvanceSettledService doAdvanceSettledService;
	@Autowired
	private EarlyFinishConfirmService earlyFinishConfirmService;
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 提前结清待办页面
	 * 2015年12月12日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackCharge
	 * @return list
	 */
	@RequestMapping(value = "list")
	public String goDoAdvanceSettledList(HttpServletRequest request,HttpServletResponse response,Model model,PaybackCharge paybackCharge) {
		//权限控制 如果登录人是门店人员   则门店选择框不可见
		//----------------------------------------------------------------------
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		String orgName = org != null ? org.getName() : "";
		boolean isManager =false;
		//如果登录是门店 则门店选择框不可见
		if (LoanOrgType.STORE.key.equals(orgType)){
			isManager=true;
	        //如果是门店 则 默认门店框体选项
			LoanInfo  loanInfo =new LoanInfo();
			paybackCharge.setLoanInfo(loanInfo);
			paybackCharge.getLoanInfo().setLoanStoreOrgName(orgName);
			paybackCharge.getLoanInfo().setLoanStoreOrgId(orgId);
		}
		model.addAttribute("isManager", isManager);
		//--------------------------------------------------------------------
		// 设置冲抵申请表中的冲抵方式为 提前结清 3
	    paybackCharge.setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
		// 设置冲抵申请表中的冲抵状态为提前结清待确认 2
		paybackCharge.setChargeStatus("'"+AgainstStatus.PAYBACK_RETURN.getCode()+"','"+AgainstStatus.RIS_PAYBACK_RETURN.getCode()+"'");
		
		//数据权限
	   String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
		paybackCharge.setQueryRight(queryRight);
		
		Page<PaybackCharge> waitPage = doAdvanceSettledService.findPaybackCharge(new Page<PaybackCharge>(request, response),paybackCharge);
//		if(ArrayHelper.isNotEmpty(waitPage.getList())){
//			for (int i = 0; i < waitPage.getList().size(); i++) {
//				Payback payback = waitPage.getList().get(i).getPayback();
//				if(!ObjectHelper.isEmpty(payback)){
//					PaybackMonth paybackMonth = earlyFinishConfirmService.findPaybackMonth(payback);
//				    if(!ObjectHelper.isEmpty(paybackMonth)){
//				    	waitPage.getList().get(i).setPaybackMonth(paybackMonth);
//				    }
//				}
//				if (!ObjectHelper.isEmpty(waitPage.getList().get(i).getLoanInfo()) && StringUtils.isNotEmpty(waitPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())) {
//					// 门店
//					waitPage.getList().get(i).getLoanInfo().setLoanStoreOrgName(
//									String.valueOf(OrgCache.getInstance().get(waitPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())));
//			    }
//			}
//		}
//	
		List<Dict> dictList = DictCache.getInstance().getList();
		for(PaybackCharge pc:waitPage.getList()){
			for(Dict dict : dictList){
				if("jk_repay_type".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(pc.getDictOffsetType())){
					pc.setDictOffsetTypeLabel(dict.getLabel());
				}
				if("jk_repay_status".equals(dict.getType()) && pc.getPayback()!=null && dict.getValue().equals(pc.getPayback().getDictPayStatus())){
					pc.getPayback().setDictPayStatusLabel(dict.getLabel());
				}
				if("jk_loan_apply_status".equals(dict.getType()) && pc.getLoanInfo()!=null && dict.getValue().equals(pc.getLoanInfo().getDictLoanStatus())){
					pc.getLoanInfo().setDictLoanStatusLabel(dict.getLabel());
				}
				if("jk_channel_flag".equals(dict.getType()) && pc.getLoanInfo()!=null && dict.getValue().equals(pc.getLoanInfo().getLoanFlag())){
					pc.getLoanInfo().setLoanFlagLabel(dict.getLabel());
				}
				if("jk_telemarketing".equals(dict.getType()) && pc.getLoanCustomer()!=null && dict.getValue().equals(pc.getLoanCustomer().getCustomerTelesalesFlag())){
					pc.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getLabel());
				}
			}
		}
		model.addAttribute("waitPage", waitPage);
		return "borrow/payback/paybackflow/doAdvanceSettled";
	}
	
	/**
	 * 提前结清待办详细页面 
	 * 2016年1月22日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackCharge
	 * @return page
	 */
	@RequestMapping(value = "form")
	public String goPaybackInfoForm(HttpServletRequest request,HttpServletResponse response, Model model, PaybackCharge paybackCharge) {
	    if(!ObjectHelper.isEmpty(paybackCharge) && StringUtils.isNotEmpty(paybackCharge.getChargeStatus())){
	    	paybackCharge.setChargeStatus("'"+paybackCharge.getChargeStatus()+"'");
	    }
		PaybackCharge paybackChargeInfo = doAdvanceSettledService.findPaybackCharge(paybackCharge);
//		if(ArrayHelper.isNotEmpty(waitPage.getList())){
//			for (int i = 0; i < waitPage.getList().size(); i++) {
//				Payback payback = waitPage.getList().get(i).getPayback();
//				if(!ObjectHelper.isEmpty(payback)){
//					PaybackMonth paybackMonth = earlyFinishConfirmService.findPaybackMonth(payback);
//				    if(!ObjectHelper.isEmpty(paybackMonth)){
//				    	waitPage.getList().get(i).setPaybackMonth(paybackMonth);
//				    }
//				}
//			}
//		}
		if(!ObjectHelper.isEmpty(paybackChargeInfo.getLoanInfo())){
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",paybackChargeInfo.getLoanInfo().getDictLoanStatus());
			paybackChargeInfo.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
		}
		model.addAttribute("paybackCharge", paybackChargeInfo);
		return "borrow/payback/paybackflow/doAdvanceSettledInfo";
	}
	
	/**
	 * 提前结清待办页面 
	 * 2016年2月27日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackCharge
	 * @return redirect page
	 */
	@RequestMapping(value = "save")
	public String saveDoAdvanceSettledInfo(@RequestParam("files") MultipartFile[] files,HttpServletRequest request,HttpServletResponse response, Model model, PaybackCharge paybackCharge) {
		// 修改还款冲抵申请表中的冲抵状态为‘冲抵待审核’
		paybackCharge.setChargeStatus(AgainstStatus.AGAINST_VERIFY.getCode());
		doAdvanceSettledService.updatePaybackCharge(files,paybackCharge);
		// 记录提前结清申请历史
		PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackCharge.getId(),
				null, RepaymentProcess.REPAYMENT_APPLY,
				TargetWay.REPAYMENT, PaybackOperate.APPLY_SUCEED.getCode(), "合同编号："+paybackCharge.getContractCode()+"申请提前结清金额："+paybackCharge.getApplyBuleAmount());
		historyService.insertPaybackOpe(paybackOpes);
		
		// 修改还款主表中的还款状态为 ‘提前结清待审核’
		Payback payback = new Payback();
		payback.setContractCode(paybackCharge.getContractCode());
		payback.setDictPayStatus(RepayStatus.PRE_SETTLE_VERIFY.getCode());
		doAdvanceSettledService.updatePaybackStatus(payback);
		return "redirect:" + adminPath + "/borrow/payback/doAdvanceSettled/list";
	}
	
	@ResponseBody
	@RequestMapping(value="giveUpStatus")
	public String giveUpStatus(@RequestParam(value ="contractCode",required=false) String contractCode,@RequestParam(value="chargeId",required=false) String chargeId,@RequestParam(value="paybackId",required=false) String paybackId){
		boolean flag = true;
		try {
			doAdvanceSettledService.giveUpStatus(contractCode, chargeId,paybackId);
		} catch (ServiceException e) {
			flag = false;
		}
		return JsonMapper.nonDefaultMapper().toJson(flag);
	}
}
