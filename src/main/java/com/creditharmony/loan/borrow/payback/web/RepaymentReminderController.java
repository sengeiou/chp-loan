package com.creditharmony.loan.borrow.payback.web;




import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;
import com.creditharmony.loan.borrow.payback.facade.RepaymentReminderFacade;
import com.creditharmony.loan.borrow.payback.service.RepaymentReminderService;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * 控制器支持类
 * @Class Name RepaymentReminderController
 * @author 李强
 * @Create In 2015年11月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/overdueManager")
public class RepaymentReminderController extends BaseController {
	@Autowired
	private RepaymentReminderService repaymentReminderService;
	@Autowired
	private RepaymentReminderFacade repaymentReminderFacade;
	
	/**
	 * 发起还款提醒列表
	 * 2015年11月23日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param repaymentReminder
	 * @return
	 */
	@RequestMapping(value = "overdueManagerList")
	public String overdueManagerList(HttpServletRequest request,HttpServletResponse response,Model model,RepaymentReminderEx repaymentReminder,String flag){
		NumTotal numTotal = new  NumTotal();
		if(StringUtils.isNotEmpty(flag)){
			// 设置提醒标识为 ‘未提醒’ 0 
			repaymentReminder.setRemindLogo(YESNO.NO.getCode());
			// 期供状态为‘待还款’
			repaymentReminder.setDictMonthStatus("'"+PeriodStatus.REPAYMENT.getCode()+"'");
			// 查询置顶状态的银行卡信息
			repaymentReminder.setBankTopFlag(Integer.parseInt(YESNO.YES.getCode()));
			//数据权限
			   String queryRight = DataScopeUtil.getDataScope("tji", SystemFlag.LOAN.value);
			   repaymentReminder.setQueryRight(queryRight);
			//=====
			Page<RepaymentReminderEx> waitPage = repaymentReminderService.allRepaymentReminderList(new Page<RepaymentReminderEx>(request, response),repaymentReminder);
			for(RepaymentReminderEx re:waitPage.getList()){
				
				String applyBankName=DictCache.getInstance().getDictLabel("jk_open_bank",re.getApplyBankName());
				re.setApplyBankNameLabel(applyBankName);
				
				String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status", re.getDictLoanStatus());
				re.setDictLoanStatusLabel(dictLoanStatus);
				
				String dictMonthStatus=DictCache.getInstance().getDictLabel("jk_period_status",re.getDictMonthStatus() );
				re.setDictMonthStatusLabel(dictMonthStatus);
				
				String logo=DictCache.getInstance().getDictLabel("jk_channel_flag", re.getLogo());
				re.setLogoLabel(logo);
				
				String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",re.getDictDealType() );
				re.setDictDealTypeLabel(dictDealType);
				
				String modelLoabel = DictCache.getInstance().getDictLabel("jk_loan_model",re.getModel());
				re.setModelLabel(modelLoabel);
			}
			
			if(waitPage.getList().size()>0){
//				numTotal.setNum(waitPage.getList().get(0).getSumNumber());
				numTotal.setTotal(waitPage.getList().get(0).getSumAmont()== null ? "0" :waitPage.getList().get(0).getSumAmont().toString());
		    }else{
			numTotal.setNum("0");
		 	numTotal.setTotal("0");
		   }
			model.addAttribute("waitPage", waitPage);
			model.addAttribute("RepaymentReminderEx", repaymentReminder);
			logger.debug("invoke RepaymentReminderController method: overdueManagerList, consult.id is: "+ waitPage);
		}else{
			numTotal.setNum("0");
		 	numTotal.setTotal("0");
		}
		model.addAttribute("numTotal",numTotal);
		// 如果没有查询条件，列表中不显示数据
		return "borrow/payback/repayment/repaymentReminder";
	}
	
	/**
	 * 发起还款提醒列表
	 * In 2015年11月23日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param checkBoxValue
	 * @return
	 */
	@RequestMapping(value = "informationAlert")
	public String informationAlert(HttpServletRequest request,
			HttpServletResponse response, Model model,RepaymentReminderEx repaymentReminder) {
		String  lstId = repaymentReminder.getId();
		NumTotal numTotal = new  NumTotal();
		if (StringHelper.isNotEmpty(lstId)) {
			repaymentReminder.setId(FilterHelper.appendIdFilter(lstId));
		} else {
			// 设置提醒标识为 ‘未提醒’ 0
			repaymentReminder.setRemindLogo(YESNO.NO.getCode());
			// 期供状态为‘待还款’
			repaymentReminder.setDictMonthStatus("'"
					+ PeriodStatus.REPAYMENT.getCode() + "'");
			// 查询置顶状态的银行卡信息
			repaymentReminder.setBankTopFlag(Integer.parseInt(YESNO.YES
					.getCode()));
		}
		 String queryRight = DataScopeUtil.getDataScope("tji", SystemFlag.LOAN.value);
		 repaymentReminder.setQueryRight(queryRight);
		 List<RepaymentReminderEx> list = repaymentReminderService.allRepaymentReminderList(repaymentReminder);
		 String msg = "";  
		 if(list.size()>0){
		       msg = repaymentReminderFacade.submitData(list);
		    }else{
		    	msg = "没有要发送的数据";
		    }
		 model.addAttribute("message",msg);
		 model.addAttribute("numTotal",numTotal);
		 return "borrow/payback/repayment/repaymentReminder";
	}
	
	/**
	 * 导出发起还款提醒列表中的数据
	 * 2016年3月24日
	 * By zhaojinping
	 * @param request
	 * @param response
	 * @param repaymentReminder
	 * @return
	 */
	@RequestMapping(value = "exportExcel")
	public String exportExcel(HttpServletRequest request,HttpServletResponse response,RepaymentReminderEx repaymentReminder){
		try {
			if(StringUtils.isEmpty(repaymentReminder.getId())){
				//没有选中的数据
				repaymentReminder.setStatus("'"+YESNO.NO.getCode()+"'");
				repaymentReminder.setBankTopFlag(Integer.parseInt(YESNO.YES.getCode()));
				repaymentReminder.setDictMonthStatus("'"
						+ PeriodStatus.REPAYMENT.getCode() + "'");
				//reminderList = repaymentReminderService.exportRemindExcel(repaymentReminder);
			}else{
				repaymentReminder.setId(FilterHelper.appendIdFilter(repaymentReminder.getId()));
				//reminderList = repaymentReminderService.exportRemindExcel(repaymentReminder);
			}
			
			// 为要导出的数据设置门店名称，当期应还金额和当期实还金额
			//for (int i = 0; i < reminderList.size(); i++) {
				//repaymentReminder.setStroeName(String.valueOf(OrgCache.getInstance().get(reminderList.get(i).getStroeName())));
				// 当期应还金额和实还金额
			//}
			String queryRight = DataScopeUtil.getDataScope("tji", SystemFlag.LOAN.value);
			repaymentReminder.setQueryRight(queryRight);
			remindRepaymentHelper.exportData(repaymentReminder, response);
//			excelUtils.exportExcel(reminderList, FileExtension.CENTER_REMIND
//					+ DateUtils.getDate(), RepaymentReminderEx.class,
//					FileExtension.XLSX, FileExtension.OUT_TYPE_TEMPLATE,
//					response, null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/borrow/payback/overdueManager/overdueManagerList";
		}
	}
}
