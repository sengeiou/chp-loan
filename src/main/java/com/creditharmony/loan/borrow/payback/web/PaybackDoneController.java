package com.creditharmony.loan.borrow.payback.web;

import java.util.ArrayList;
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
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.service.PaybackDoneService;
import com.creditharmony.loan.common.excel.SXXExcel;
import com.creditharmony.loan.common.utils.NumberFormatUtil;
import com.creditharmony.loan.common.utils.StringUtil;

/**
 * 结清已办Controller
 * 
 * @Class Name PaybackDoneController
 * @author zhangfeng
 * @Create In 2015年12月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/paybackDone")
public class PaybackDoneController extends BaseController {

	@Autowired
	public PaybackDoneService paybackDoneService;
	
	/**
	 * 结清已办页面
	 * 2015年12月12日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param model
	 * @param payback
	 * @return
	 */
	@RequestMapping(value = "list")
	public String goPaybackDone(HttpServletRequest request,HttpServletResponse response,Model model,Payback payback) {
		String status = payback.getDictPayStatus();
		payback.setDictPayStatus("'"+RepayStatus.SETTLE.getCode()+"','" + RepayStatus.SETTLE_FAILED.getCode() + "'");
		// 设置查询有效的催收服务费金额
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
		payback.setUrgeServicesMoney(urgeServicesMoney);
		Date beginDate = payback.getBeginDate();
		if(!ObjectHelper.isEmpty(beginDate)){
			if(ObjectHelper.isEmpty(payback.getEndDate())){
				payback.setEndDate(new Date());
			}else{
				String endDateStr = DateUtils.formatDate(payback.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+ " 23:59:59");
				payback.setEndDate(endDate);
			}
		}else{
			if(!ObjectHelper.isEmpty(payback.getEndDate())){
				String endDateStr = DateUtils.formatDate(payback.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr+ " 23:59:59");
				payback.setEndDate(endDate);
			}
		}
		Page<Payback> paybackPage = paybackDoneService.findPaybackList(new Page<Payback>(request, response), payback);
		// 用于查询条件回显
		if(!(ObjectHelper.isEmpty(status)&& StringUtils.isEmpty(status))){
				payback.setDictPayStatus(status);
		}
		List<Dict> dictList = DictCache.getInstance().getList();
		for(Payback pa:paybackPage.getList()){
			for(Dict dict:dictList){
				if("jk_repay_status".equals(dict.getType()) && dict.getValue().equals(pa.getDictPayStatus())){
					pa.setDictPayStatusLabel(dict.getLabel());
				}
				if("jk_channel_flag".equals(dict.getType()) &&  pa.getLoanInfo()!=null && dict.getValue().equals(pa.getLoanInfo().getLoanFlag())){
					pa.getLoanInfo().setLoanFlagLabel(dict.getLabel());
				}
				if("jk_telemarketing".equals(dict.getType()) && pa.getLoanCustomer() !=null &&dict.getValue().equals(pa.getLoanCustomer().getCustomerTelesalesFlag())){
					pa.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getLabel());
				}
				// 模式
				if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && pa.getLoanInfo()!=null && dict.getValue().equals(pa.getLoanInfo().getModel())){
					pa.getLoanInfo().setModelLabel(dict.getLabel());
				}
				/*if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && pa.getLoanInfo()!=null && dict.getValue().equals(pa.getLoanInfo().getModel())){
					String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",pa.getLoanInfo().getModel());
					pa.getLoanInfo().setModelLabel(dictLoanModel);
				}*/
			}
		}
		model.addAttribute("paybackList", paybackPage);
		model.addAttribute("payback", payback);
		return "borrow/payback/paybackflow/paybackDone";
	}
	
	/**
	 * 获取结清已办详情页面信息
	 * 2016年3月2日
	 * By zhaojinping
	 * @param contarctCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "confirmed")
	public String goConfirmPaybackFormInfo(String contarctCode, Model model) {
		List<Payback> paybackList = new ArrayList<Payback>();
		paybackList = paybackDoneService.goConfirmPaybackFormInfo(contarctCode);
		for(Payback pa:paybackList){
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",pa.getLoanInfo().getDictLoanStatus());
			pa.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
		}
		model.addAttribute("payback", paybackList.get(0));
		return "borrow/payback/paybackflow/paybackDoneDetails";
	}
	
	
	/**
	 * 结清确认列表导出
	 * 2016年08月04日
	 * By zhaowg
	 * @param request
	 * @param response
	 * @param model
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "excelList")
	public void excelList(HttpServletRequest request,
		HttpServletResponse response,Payback payback){
		try {
			// 将ID转化为String类型
			if(StringUtils.isNotEmpty(payback.getIds())) {
				payback.setIds(StringUtil.appendString(payback.getIds()));
			}
			payback.setDictPayStatus("'"+RepayStatus.SETTLE.getCode()+"','" + RepayStatus.SETTLE_FAILED.getCode() + "'");
			// 设置查询有效的催收服务费金额
			UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
			urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
			payback.setUrgeServicesMoney(urgeServicesMoney);
			Date beginDate = payback.getBeginDate();
			if(!ObjectHelper.isEmpty(beginDate)){
				if(ObjectHelper.isEmpty(payback.getEndDate())){
					payback.setEndDate(new Date());
				}else{
					String endDateStr = DateUtils.formatDate(payback.getEndDate(), "yyyy-MM-dd");
					Date endDate = DateUtils.convertStringToDate(endDateStr+ " 23:59:59");
					payback.setEndDate(endDate);
				}
			}else{
				if(!ObjectHelper.isEmpty(payback.getEndDate())){
					String endDateStr = DateUtils.formatDate(payback.getEndDate(), "yyyy-MM-dd");
					Date endDate = DateUtils.convertStringToDate(endDateStr+ " 23:59:59");
					payback.setEndDate(endDate);
				}
			}
			// 查询要导出的数据
			List<Payback> dataList = paybackDoneService.findPaybackList(payback);
			// 获取字典值
			List<Dict> dictList = DictCache.getInstance().getList();
			for(Payback pa : dataList){
				for(Dict dict:dictList){
					if("jk_repay_status".equals(dict.getType()) && dict.getValue().equals(pa.getDictPayStatus())){
						pa.setDictPayStatusLabel(dict.getLabel());
					}
					if("jk_channel_flag".equals(dict.getType()) &&  pa.getLoanInfo()!=null && dict.getValue().equals(pa.getLoanInfo().getLoanFlag())){
						pa.getLoanInfo().setLoanFlagLabel(dict.getLabel());
					}
					if("jk_telemarketing".equals(dict.getType()) && pa.getLoanCustomer() !=null &&dict.getValue().equals(pa.getLoanCustomer().getCustomerTelesalesFlag())){
						pa.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getLabel());
					}
					// 模式
					if("jk_loan_model".equals(dict.getType()) && dict.getValue()!=null && pa.getLoanInfo()!=null && dict.getValue().equals(pa.getLoanInfo().getModel())){
						pa.getLoanInfo().setModelLabel(dict.getLabel());
					}
				}
			}
			// 导出excel名称
			String exportName = "结清已办列表_" + DateUtils.getDate("yyyyMMdd");
			// 标题
			String[] titles = {"合同编号","客户姓名","门店名称","合同金额","已催收服务费金额",
				"放款金额","批借期数","首期还款日","最长逾期天数","未还违约金（滞纳金）及罚息金额","结清日期",
				"还款状态","减免金额","渠道","模式"};
			SXXExcel excel = new SXXExcel("结清已办列表",titles);
			StringBuffer sb = null;
			if (null != dataList && dataList.size() > 0) {
				for (Payback paybackObj : dataList) {
					sb = new StringBuffer();
					sb.append(StringUtil.convertStrEmptyToBlank(paybackObj.getContractCode())).append(",");// 合同编号
					sb.append(StringUtil.convertStrEmptyToBlank(paybackObj.getLoanCustomer().getCustomerName())).append(",");// 客户姓名
					sb.append(StringUtil.convertStrEmptyToBlank(paybackObj.getLoanInfo().getLoanStoreOrgName())).append(",");// 门店名称
					NumberFormatUtil.notNullAppend(sb,paybackObj.getContract().getContractAmount());// 合同金额
					NumberFormatUtil.notNullAppend(sb,paybackObj.getUrgeServicesMoney().getUrgeDecuteMoeny());// 已催收服务费金额
					NumberFormatUtil.notNullAppend(sb,paybackObj.getLoanGrant().getGrantAmount());// 放款金额
					NumberFormatUtil.notNullAppend(sb,paybackObj.getContract().getContractMonths());// 批借期数
					NumberFormatUtil.notNullAppend(sb,paybackObj.getContract().getContractReplayDay());// "首期还款日",
					NumberFormatUtil.notNullAppend(sb,paybackObj.getPaybackMaxOverduedays());//"最长逾期天数",
					NumberFormatUtil.notNullAppend(sb,paybackObj.getPaybackMonth().getPenaltyInterest());//"未还违约金（滞纳金）及罚息金额"
					NumberFormatUtil.notNullAppend(sb,paybackObj.getModifyTime());//"结清日期",
					sb.append(StringUtil.convertStrEmptyToBlank(paybackObj.getDictPayStatusLabel())).append(",");// "还款状态"
					// sb.append(paybackObj.getLoanInfo().getDictLoanStatusLabel()).append(",");//"借款状态"
					NumberFormatUtil.notNullAppend(sb,paybackObj.getPaybackMonth().getCreditAmount());//"减免金额"
					sb.append(StringUtil.convertStrEmptyToBlank(paybackObj.getLoanInfo().getLoanFlagLabel())).append(",");//"渠道"
					sb.append(StringUtil.convertStrEmptyToBlank(paybackObj.getLoanInfo().getModelLabel()));//"模式"
					//sb.append(paybackObj.getLoanCustomer().getCustomerTelesalesFlagLabel());//"是否电销"
					excel.addSXSSFRow(sb.toString().split(","));
				}
			}
			excel.outSXSSFFile(response, exportName + ".xls");
		} catch (Exception e) {
			logger.error("结清确认列表导出出现异常。",e);
		}
	}
	
}
