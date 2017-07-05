package com.creditharmony.loan.borrow.revisit.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.util.ExportGrantSureHelper;
import com.creditharmony.loan.borrow.revisit.service.ReVisitService;
import com.creditharmony.loan.borrow.revisit.view.RevisitAndPersonInfo;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 
 * @Create In 2016年10月27日
 * @version:1.0
 * @author songfeng
 */
@Controller
@RequestMapping(value = "${adminPath}/revisit/revisitfail")
public class ReVisitController extends BaseController {


	@Autowired
	private ReVisitService reVisitService;

	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private UserManager userManager;
	
	private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);

    /**
     *查询回访失败列表
     *@author songfeng
     *@Create In 2016年10月28日
     *@param  ctrPersonInfo
     *@return  jsp页面
     */
	@RequestMapping(value="findContract")
	public String findContractAndPerson(Model model,HttpServletRequest request, 
			HttpServletResponse response,RevisitAndPersonInfo revPersonInfo ){
		
		//查询所有产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		if(ObjectHelper.isEmpty(revPersonInfo.getStoreOrgId()) || revPersonInfo.getStoreOrgId().length==0){
			revPersonInfo.setStoreName(null);
			revPersonInfo.setStoreOrgId(null);
		}
		if(ObjectHelper.isEmpty(revPersonInfo.getMonthRateAll()) || revPersonInfo.getMonthRateAll().length==0){
			revPersonInfo.setMonthRateAll(null);
		}
		Page<RevisitAndPersonInfo> ps=reVisitService.findRevisitAndPersonInfo(revPersonInfo, new Page<RevisitAndPersonInfo>(request,response));
		for(RevisitAndPersonInfo cp:ps.getList()){
			 if(cp.getContractVersion()!=null && !cp.getContractVersion().equals("0ZCJ")){
				 cp.setContractVersion(ContractVer.parseByCode(cp.getContractVersion()).getName());
			 }			 
			 //转换模型
			 String modelLabel=DictCache.getInstance().getDictLabel("jk_loan_model",cp.getModelLabel());
			 cp.setModelLabel(modelLabel);
			 //转换渠道 
			 String channel=DictCache.getInstance().getDictLabel("jk_channel_flag",cp.getChannelName());
			 cp.setChannelName(channel);
			 //转换开户行
			 String bank=DictCache.getInstance().getDictLabel("tz_open_bank",cp.getDepositBank());
			 cp.setDepositBank(bank);
		 }
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("page",ps);
		model.addAttribute("revPersonInfo",revPersonInfo);
		return "borrow/revisit/revisitList";
	}
	
	/**
	 * 导出Excel
	 * @param request
	 * @param listFlag
	 * @param revPersonInfo
	 * @param response
	 * @param idVal
	 * @throws Exception
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,String listFlag,
			RevisitAndPersonInfo revPersonInfo, HttpServletResponse response,
			String idVal,RedirectAttributes redirectAttributes) throws Exception {
		ExcelUtils excelutil = new ExcelUtils();
		List<RevisitAndPersonInfo> revisitList = new ArrayList<RevisitAndPersonInfo>();
		if(ObjectHelper.isEmpty(revPersonInfo.getStoreOrgId()) || revPersonInfo.getStoreOrgId().length==0){
			revPersonInfo.setStoreName(null);
			revPersonInfo.setStoreOrgId(null);
		}
		if(ObjectHelper.isEmpty(revPersonInfo.getMonthRateAll()) || revPersonInfo.getMonthRateAll().length==0){
			revPersonInfo.setMonthRateAll(null);
		}
		if (StringUtils.isEmpty(idVal)) {
			List<RevisitAndPersonInfo> ps=reVisitService.findExportRevisit(revPersonInfo);
			if(ps!=null&&ps.size()>0){
				revisitList=ps;
				for(int i=0;i<revisitList.size();i++){
					RevisitAndPersonInfo personInfo=revisitList.get(i);
					personInfo.setRowId((i+1)+"");
				}
			}
		} else {
			// 如果有选中的单子,将选中的单子导出
			String[] ids = idVal.split(",");
			for (int i = 0; i < ids.length; i++) {
				
				revPersonInfo.setLoanCode(ids[i]);
				List<RevisitAndPersonInfo> ps=reVisitService.findExportRevisit(revPersonInfo);
				if(ps!=null&&ps.size()>0){
					List<RevisitAndPersonInfo> list=ps;
					if(list!=null&&list.size()>0){
						RevisitAndPersonInfo personinfo=list.get(0);
						personinfo.setRowId((i+1)+"");
						revisitList.add(personinfo);		
					}
				}
			}
		}
		//转化合同版本号
		if(revisitList!=null&&revisitList.size()>0){
			for(RevisitAndPersonInfo info:revisitList){
				if(info.getContractVersion()!=null && !info.getContractVersion().equals("0ZCJ")){
					info.setContractVersion(ContractVer.parseByCode(info.getContractVersion()).getName());
				 }	
			}
		}
		//转换精度
		if(revisitList!=null&&revisitList.size()>0){
			for(RevisitAndPersonInfo info:revisitList){
				DecimalFormat df= new DecimalFormat("######0.00");
				if(info.getContractMoney()!=null){
					double moneyCon=info.getContractMoney().doubleValue();
					info.setContractMoneyStr(df.format(moneyCon));
				}
				if(info.getLendingMoney()!=null){
					double moneyLen=info.getLendingMoney().doubleValue();
					info.setLendingMoneyStr(df.format(moneyLen));
				}
				if(info.getUrgeServiceFee()!=null){
					double moneyUrg=info.getUrgeServiceFee().doubleValue();
					info.setUrgeServiceFeeStr(df.format(moneyUrg));
				}
			}
		}
		excelutil.exportExcel(revisitList,
				FileExtension.REVISIT_FAIL_XLS + DateUtils.getDate("yyyyMMdd"),
				RevisitAndPersonInfo.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, 1);
	}
	
	
	/**
	 * 导出客户信息表
	 * @param request
	 * @param listFlag
	 * @param revPersonInfo
	 * @param response
	 * @param idVal
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportCustomerExcel")
	public String exportCustomerExcel(HttpServletRequest request,String listFlag,
			RevisitAndPersonInfo revPersonInfo, HttpServletResponse response,
			String idVal,RedirectAttributes redirectAttributes) throws Exception {
		if(revPersonInfo!=null&&revPersonInfo.getCustomerName()!=null){
			String paramsTrans = new String(revPersonInfo.getCustomerName().getBytes("ISO-8859-1"),"UTF-8");
			paramsTrans = java.net.URLDecoder.decode(paramsTrans , "UTF-8");
			revPersonInfo.setCustomerName(paramsTrans);
		}
		String[] ids = null;
		Map<String, Object> idMap = new HashMap<String, Object>();
		if(ObjectHelper.isEmpty(revPersonInfo.getStoreOrgId()) || revPersonInfo.getStoreOrgId().length==0){
			revPersonInfo.setStoreName(null);
			revPersonInfo.setStoreOrgId(null);
		}
		if(ObjectHelper.isEmpty(revPersonInfo.getMonthRateAll()) || revPersonInfo.getMonthRateAll().length==0){
			revPersonInfo.setMonthRateAll(null);
		}
		try {
			List<RevisitAndPersonInfo> revisitList = new ArrayList<RevisitAndPersonInfo>();
			if (StringUtils.isEmpty(idVal)) {
				List<RevisitAndPersonInfo> ps=reVisitService.findExportRevisit(revPersonInfo);
				if(ps!=null&&ps.size()>0){
					revisitList=ps;
					ids=new String[revisitList.size()];
					for(int i=0;i<revisitList.size();i++){
						ids[i]=revisitList.get(i).getApplyId();
					}
				}
				
			} else {
				// 如果有选中的单子,将选中的单子导出
				ids = idVal.split(",");
				
			}
			
			if(ObjectHelper.isEmpty(ids))
			{
				addMessage(redirectAttributes, "无可导出的客户信息！");
				return "redirect:"
						+ adminPath
						+ "/revisit/revisitfail/findContract";
						
			}
			idMap.put("ids", ids);
			String fileName = FileExtension.GRANT_CUSTOMER + DateUtils.getDate("yyyyMMdd");
			String[] header = {"客户姓名","身份证号码","性别","产品种类","信用合同编号","所在地","还款银行账号","开户行","实放金额","合同金额","月还款金额","期数","审核日期","首期还款日"
	        		,"合同到期日","账单日","模式","渠道","合同签订日期","是否电销","是否加急","划扣平台","合同版本号",
	        		"催收服务费","外访费","审核次数","最后一次退回原因","审核人","风险等级","回访状态"};
			ExportGrantSureHelper.customerExport(idMap, header, fileName, response, userManager);
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "客户信息表导出失败，请重试！");
			logger.error("方法:grantCustomerExl,客户信息表导出失败.");
			e.printStackTrace();
		} 
		return "redirect:"
		+ adminPath
		+ "/revisit/revisitfail/findContract";
	}
}
