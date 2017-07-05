package com.creditharmony.loan.borrow.payback.web;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.ex.EarlySettlementEx;
import com.creditharmony.loan.borrow.payback.service.EarlySettlementService;
import com.creditharmony.loan.common.excel.SXXExcel;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * 提前结清已办
 * @Class Name EarlySettlementController
 * @author 李强
 * @Create In 2015年12月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/earlySement")
public class EarlySettlementController extends BaseController {
	@Autowired
	private EarlySettlementService earlySettlementHaveTodoService;

	/**
	 * 查询提前结清已办列表
	 * 2015年12月1日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param earlySettlement
	 * @return earlySettlement.jsp
	 */
	@RequestMapping(value = "earlySettlementHaveTodoList")
	public String earlySettlementHaveTodoList(HttpServletRequest request,HttpServletResponse response,Model model,EarlySettlementEx earlySettlement) {
		String stores = earlySettlement.getStore();
		if (!ObjectHelper.isEmpty(stores)) {
			earlySettlement.setStoreId(FilterHelper.appendIdFilter(stores));
		}
		earlySettlement.setEnumOne(RepayType.EARLY_SETTLE.getCode());
		earlySettlement.setChargeStatus("'"+ AgainstStatus.AIAINST.getCode() +"','"
				+ AgainstStatus.RIS_PAYBACK_RETURN.getCode()+"'");
		// 设置查询有效的催收服务费金额
		earlySettlement.setReturnLogo(YESNO.NO.getCode());
		Date beginDate = earlySettlement.getBeginDate();
		if(!ObjectHelper.isEmpty(beginDate)){
			if(!ObjectHelper.isEmpty(earlySettlement.getEndDate())){
				String endDateStr = DateUtils.formatDate(earlySettlement.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
				earlySettlement.setEndDate(endDate);
			}else{
				earlySettlement.setEndDate(new Date());
			}
		}else{
			if(!ObjectHelper.isEmpty(earlySettlement.getEndDate())){
				String endDateStr = DateUtils.formatDate(earlySettlement.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
				earlySettlement.setEndDate(endDate);
			}
		}
		Page<EarlySettlementEx> waitPage = earlySettlementHaveTodoService.allEarlySettlementList(new Page<EarlySettlementEx>(request, response),earlySettlement);
		List<Dict> dictList = DictCache.getInstance().getList();
		for(EarlySettlementEx es:waitPage.getList()){
			for(Dict dict:dictList){
				//还款类型
				//还款状态名称
				if("jk_repay_status".equals(dict.getType()) && dict.getValue().equals(es.getDictPayStatus())){
					es.setDictPayStatusLabel(dict.getLabel());
				}
				//借款状态
				if("jk_loan_apply_status".equals(dict.getType()) && dict.getValue().equals(es.getDictLoanStatus())){
					es.setDictLoanStatusLabel(dict.getLabel());
				}
				//标示名称
				if("jk_channel_flag".equals(dict.getType()) && dict.getValue().equals(es.getLoanFlag())){
					es.setLoanFlagLabel(dict.getLabel());
				}
				//是否电销名称
				if("jk_telemarketing".equals(dict.getType()) && dict.getValue().equals(es.getLoanIsPhone())){
					es.setLoanIsPhoneLabel(dict.getLabel());
				}
				//冲抵方式
				if("jk_repay_way".equals(dict.getType()) && dict.getValue().equals(es.getDictOffsetType())){
					es.setDictOffsetTypeLabel(dict.getLabel());
				}
				// 模式
				if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(es.getModel())){
					es.setModelLabel(dict.getLabel());
				}
				// 提前结清冲抵状态 
				if("jk_charge_against_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(es.getChargeStatus())){
					es.setChargeStatusLabel(dict.getLabel());
				}
				
			}
		}
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("EarlySettlementEx", earlySettlement);
		logger.debug("invoke EarlySettlementController method: earlySettlementHaveTodoList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/earlySettlement";
	}

	/**
	 * 查看 提前结清已办列表信息
	 * 2015年12月1日
	 * By 李强
	 * @param model
	 * @param earlySettlement
	 * @return seeEarlySettlement.jsp
	 */
	@RequestMapping(value = "seeEarlySettlementHaveTodo")
	public String seeEarlySettlementHaveTodo(Model model,EarlySettlementEx earlySettlement) {
		//earlySettlement.setEnumOne(PaybackOperate.CONFIRM_SUCCEED.getCode());
		//earlySettlement.setEnumTwo(PaybackOperate.CONFIRM_FAILED.getCode());
		// 设置查询有效的催收服务费金额
		earlySettlement.setReturnLogo(YESNO.NO.getCode());
		EarlySettlementEx earlySettlements = earlySettlementHaveTodoService.seeEarlySettlement(earlySettlement);
		String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status", earlySettlements.getDictLoanStatus());
		earlySettlements.setDictLoanStatusLabel(dictLoanStatus);
		model.addAttribute("earlySettlement", earlySettlements);
		logger.debug("invoke EarlySettlementController method: seeEarlySettlementHaveTodo, consult.id is: "+ earlySettlements);
		return "borrow/payback/repayment/seeEarlySettlement";
	}
	
	/**
	 * 查询提前结清已办列表
	 * 2015年12月1日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param earlySettlement
	 * @return earlySettlement.jsp
	 */
	@RequestMapping(value = "excelList")
	public void excelList(HttpServletRequest request,HttpServletResponse response,Model model,EarlySettlementEx earlySettlement) {
		String stores = earlySettlement.getStore();
		if (!ObjectHelper.isEmpty(stores)) {
			earlySettlement.setStoreId(FilterHelper.appendIdFilter(stores));
		}
		earlySettlement.setEnumOne(RepayType.EARLY_SETTLE.getCode());
		earlySettlement.setChargeStatus("'"+ AgainstStatus.AIAINST.getCode() +"','"
				+ AgainstStatus.RIS_PAYBACK_RETURN.getCode()+"'");
		// 设置查询有效的催收服务费金额
		earlySettlement.setReturnLogo(YESNO.NO.getCode());
		Date beginDate = earlySettlement.getBeginDate();
		if(!ObjectHelper.isEmpty(beginDate)){
			if(!ObjectHelper.isEmpty(earlySettlement.getEndDate())){
				String endDateStr = DateUtils.formatDate(earlySettlement.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
				earlySettlement.setEndDate(endDate);
			}else{
				earlySettlement.setEndDate(new Date());
			}
		}else{
			if(!ObjectHelper.isEmpty(earlySettlement.getEndDate())){
				String endDateStr = DateUtils.formatDate(earlySettlement.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
				earlySettlement.setEndDate(endDate);
			}
		}
		SXXExcel excel = new SXXExcel("提前结清已办列表", new String[]{"门店名称","合同编号","客户姓名","合同金额","已催收服务费金额","放款金额","批借期数","首期还款日","最长逾期天数","提前结清应还款总额","申请还款金额","实际提前结清金额","还款状态","提前结清日期","借款状态","减免人","减免金额","渠道","模式","是否电销"});
		List<EarlySettlementEx> waitPage = earlySettlementHaveTodoService.allEarlySettlementList(earlySettlement);
		List<Dict> dictList = DictCache.getInstance().getList();
		for(EarlySettlementEx es: waitPage){
			for(Dict dict:dictList){
				//还款类型
				//还款状态名称
				if("jk_repay_status".equals(dict.getType()) && dict.getValue().equals(es.getDictPayStatus())){
					es.setDictPayStatusLabel(dict.getLabel());
				}
				//借款状态
				if("jk_loan_apply_status".equals(dict.getType()) && dict.getValue().equals(es.getDictLoanStatus())){
					es.setDictLoanStatusLabel(dict.getLabel());
				}
				//标示名称
				if("jk_channel_flag".equals(dict.getType()) && dict.getValue().equals(es.getLoanFlag())){
					es.setLoanFlagLabel(dict.getLabel());
				}
				//是否电销名称
				if("jk_telemarketing".equals(dict.getType()) && dict.getValue().equals(es.getLoanIsPhone())){
					es.setLoanIsPhoneLabel(dict.getLabel());
				}
				//冲抵方式
				if("jk_repay_way".equals(dict.getType()) && dict.getValue().equals(es.getDictOffsetType())){
					es.setDictOffsetTypeLabel(dict.getLabel());
				}
				// 模式
				if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(es.getModel())){
					es.setModelLabel(dict.getLabel());
				}
				// 提前结清冲抵状态 
				if("jk_charge_against_status".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(es.getChargeStatus())){
					es.setChargeStatusLabel(dict.getLabel());
				}
			}
			
			excel.addSXSSFRow(new Object[]{
					es.getOrgName(),es.getContractCode(),es.getCustomerName(),es.getContractMoney(),es.getUrgeDecuteMoeny(),es.getGrantAmount(),
					es.getContractMonths(),es.getContractReplayDate(),es.getPaybackMaxOverduedays(),es.getModifyYingpaybackBackMoney(),
					es.getApplyAmountPaybac(),es.getActualModifyMoney(),es.getChargeStatusLabel(),es.getModifyDate(),es.getDictLoanStatusLabel(),es.getReductionBy(),
					es.getMonthBeforeReductionAmount(),es.getLoanFlagLabel(),es.getModelLabel(),es.getLoanIsPhoneLabel()
			});
		}
		
		excel.outputExcel(response, "提前结清已办列表_"+DateUtil.DateToString(new Date(), "yyyyMMdd")+".xls");
		
		
	}
}
