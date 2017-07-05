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
import com.creditharmony.core.loan.type.OperateRole;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackAuditEx;
import com.creditharmony.loan.borrow.payback.service.PaybackAuditService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * 控制器支持类 还款查账已办
 * @Class Name PaybackAuditController
 * @author 李强
 * @Create In 2015年12月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/paybackAudit")
public class PaybackAuditController extends BaseController {
	
	@Autowired
	private PaybackAuditService paybackAuditHavaTodoService;
	
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private RepaymentDateService dateService;
	
	/**
	 * 还款查账已办列表
	 * 2015年12月8日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackAudit
	 * @return 还款查账已办数据集合
	 */
	@RequestMapping(value = "allpaybackAuditHavaTodoList")
	public String allpaybackAuditHavaTodoList(HttpServletRequest request,HttpServletResponse response,
			Model model,PaybackAuditEx paybackAudit,String zhcz){
		//记录还款状态最初查询条件
		String dictPayStatusOri=paybackAudit.getDictPayStatus();
		if(ObjectHelper.isEmpty(paybackAudit.getDictPayStatus())){
			paybackAudit.setDictPayStatus("'"+RepayApplyStatus.HAS_PAYMENT.getCode()+"','"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"','"+RepayApplyStatus.REPAYMENT_GIVEUP.getCode()+"'");
		}else{
			paybackAudit.setDictPayStatus("'"+paybackAudit.getDictPayStatus()+"'");
		}
		if(zhcz!=null && !zhcz.equals("")){
			paybackAudit.setOperateRole(OperateRole.ZHONGHE.getCode());
		}
		String stores = paybackAudit.getStoreId();
		if (!ObjectHelper.isEmpty(stores)) {
			stores = stores.trim();
			paybackAudit.setStoreId(FilterHelper.appendIdFilter(stores));
		}
		Date beginDate = paybackAudit.getBeginDate();
		if(null != beginDate){
			if(null == paybackAudit.getEndDate()){
				paybackAudit.setEndDate(new Date());
			}else{
				String endDateStr = DateUtils.formatDate(paybackAudit.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr + " 23:59:59");
				paybackAudit.setEndDate(endDate);
			}
		}else{
			if(null != paybackAudit.getEndDate()){
				String endDateStr = DateUtils.formatDate(paybackAudit.getEndDate(), "yyyy-MM-dd");
				Date endDate = DateUtils.convertStringToDate(endDateStr + " 23:59:59");
				paybackAudit.setEndDate(endDate);
			}
		}
		
		Page<PaybackAuditEx> waitPage = paybackAuditHavaTodoService.allPaybackAuditHavaTodoList(new Page<PaybackAuditEx>(request, response),paybackAudit);
			
		// 得到中间人信息表中的开户行供页面下拉框显示
		String depositFlag = YESNO.YES.getCode();
		List<MiddlePerson> waitPages = paybackAuditHavaTodoService.selectAllMiddle(depositFlag);
		List<PaybackAuditEx> resultList = new ArrayList<PaybackAuditEx>();
		for(PaybackAuditEx pa:waitPage.getList()){
			if(pa.getStoresInAccount()!=null && !pa.getStoresInAccount().equals("")
					&& pa.getStoresInAccount().equals(OperateRole.ZHONGHEGONGSHANG.getCode())){
				continue;
			}
			String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_apply_status",pa.getDictPayStatus());
			pa.setDictPayStatusLabel(dictPayStatus);
			
			String dictRepayMethod=DictCache.getInstance().getDictLabel("jk_repay_way",pa.getDictRepayMethod());
			pa.setDictRepayMethodLabel(dictRepayMethod);
			
			String dictPayUse=DictCache.getInstance().getDictLabel("jk_repay_type",pa.getDictPayUse());
			pa.setDictPayUseLabel(dictPayUse);
			
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_apply_status",pa.getDictLoanStatus());
			pa.setDictLoanStatusLabel(dictLoanStatus);
			
			String dictPayResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getDictPayResult());
			pa.setDictPayResultLabel(dictPayResult);
			
			String dictPayResultLabel=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pa.getDictPayResult());
			pa.setDictPayResultLabel(dictPayResultLabel);
			resultList.add(pa);
		}
		waitPage.setList(resultList);
		paybackAudit.setStoreId(stores);
		paybackAudit.setDictPayStatus(dictPayStatusOri);
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
	    model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("PaybackAuditEx", paybackAudit);
		model.addAttribute("MiddlePerson", waitPages);
		model.addAttribute("zhcz", zhcz);
		return "borrow/payback/repayment/paybackAudit";
	}
	
	/**
	 * 还款查账 查看页面
	 * 2015年12月8日
	 * By 李强
	 * @param model
	 * @param paybackAudit
	 * @return 还款查账已办单条数据
	 */
	@RequestMapping(value = "seePaybackAuditHavaTodo")
	public String seePaybackAuditHavaTodo(Model model,PaybackAuditEx paybackAudit){
		PaybackAuditEx paybackAudits = paybackAuditHavaTodoService.seePaybackAuditHavaTodo(paybackAudit);
		
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		loanPrd.setProductCode(paybackAudits.getProductType());
	    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
	    paybackAudits.setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
	    //查询汇款详细数据
	    List<PaybackAuditEx> remittanceList = paybackAuditHavaTodoService.seePaybackAuditHavaList(paybackAudit.getPayBackId());
	    //查询POS刷卡列表详细数据
	    List<PaybackAuditEx> remittanceListPos = paybackAuditHavaTodoService.seePaybackAuditHavaListPos(paybackAudit.getPayBackId());
		  
	    if(remittanceList!=null)
	    {
	    String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",paybackAudits.getDictLoanStatus());
	    paybackAudits.setDictLoanStatusLabel(dictLoanStatus);
	    String loanMark=DictCache.getInstance().getDictLabel("jk_channel_flag",paybackAudits.getLoanMark());
	    paybackAudits.setLoanMarkLabel(loanMark);
	    String dictRepayMethod=DictCache.getInstance().getDictLabel("jk_repay_way",paybackAudits.getDictRepayMethod());
	    paybackAudits.setDictRepayMethodLabel(dictRepayMethod);
	    //划扣平台
	    String dictDealTypeLable=DictCache.getInstance().getDictLabel("jk_deduct_plat",paybackAudits.getDictDealType());
	    paybackAudits.setDictDealTypeLable(dictDealTypeLable);
	    //存入账户
	    String dictPosTypeLable=DictCache.getInstance().getDictLabel("jk_account",paybackAudits.getDictPosType());
	    paybackAudits.setDictPosTypeLable(dictPosTypeLable);
	    //POS平台
	    String posAccountLable=DictCache.getInstance().getDictLabel("jk_pos",paybackAudits.getPosAccount());
	    paybackAudits.setPosAccountLable(posAccountLable);
	    }
	
		model.addAttribute("remittanceListPos", remittanceListPos);
		model.addAttribute("paybackAudit", paybackAudits);
		model.addAttribute("remittanceList", remittanceList);
		logger.debug("invoke PaybackAuditController method: seePaybackAuditHavaTodo, consult.id is: "+ paybackAudits);
		return "borrow/payback/repayment/seePaybackAudit";
	}
	
	/**
	 * 导出还款查账已办数据表
	 * 2015年12月25日
	 * By 李强
	 * @param request
	 * @param response
	 * @param idVal 
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response, String idVal,PaybackAuditEx paybackAuditEx){
		String[] id = idVal.split(";");
		
				// 如果没有选中的数据，则导出处全部的数据
				paybackAuditEx.setDictPayStatus("'"+RepayApplyStatus.HAS_PAYMENT.getCode()+"','"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"','"+RepayApplyStatus.REPAYMENT_GIVEUP.getCode()+"'");
		
				if (StringUtils.isNotEmpty(idVal)) {	
				    StringBuffer ids = new StringBuffer();
	                for(String i:id){
	                    if(StringUtils.isNotEmpty(i)){
	                        ids.append("'").append(i).append("',"); 
	                    }
	                }
	                ids.deleteCharAt(ids.length()-1);
	                paybackAuditEx.setIds(ids.toString());
				}else{
				    paybackAuditEx.setIds(null);
				}
        	String[] header={"","合同编号","客户姓名","门店名称","批借期数","首期还款日","还款状态","存入银行","还款方式","申请还款金额","实还金额",
			        "还款类型","查账日期","还款日","借款状态","回盘结果","蓝补金额"};
			String fileName=DeductedConstantEx.STORE_NAMES+ System.currentTimeMillis();
			if(paybackAuditEx.getOperateRole()!=null && !paybackAuditEx.getOperateRole().equals(""))
				fileName = DeductedConstantEx.ZH_STORE_NAMES+ System.currentTimeMillis();
			ExportPaybackAuditHelper.exportPayback(paybackAuditEx, header, fileName, response);
	}
	
}
