package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.service.CentralizedDeductionService;
import com.creditharmony.loan.borrow.payback.util.CentralizedDeductionExportUtil;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * @Class 控制器支持类
 * @author 李强
 * @version 1.0
 * @Create In 2015年12月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/centraliDeduc")
public class CentralizedDeductionController extends BaseController  {
	@Autowired
	private CentralizedDeductionService centralizedDeductionToService;

	@Autowired
    private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private RepaymentDateService dateService;
	@Autowired
    private UserManager userManager;
	
    private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);

	
	/**
	 * 集中划扣已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackSplit
	 * @return centralizedDeduction.jsp集中划扣已办数据集合
	 */
	@RequestMapping(value = "allCentralizedDeductionList")
	public String allCentralizedDeductionList(
		HttpServletRequest request,
		HttpServletResponse response, 
		Model model, 
		PaybackApply paybackApply
	) {  
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		String orgName = org != null ? org.getName() : "";
		boolean isManager =true;
		
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("menuId",menuId);
		// 如果登录是门店 则门店选择框不可见
		if (LoanOrgType.STORE.key.equals(orgType)) {
			isManager=false;
	    // 如果是门店 则 默认门店框体选项
			paybackApply.setStores(orgId);
			paybackApply.setStoresName(orgName);
		} else if (LoanOrgType.TEAM.key.equals(orgType)) {
		    isManager=false;
	    // 如果是门店 则 默认门店框体选项
		    paybackApply.setStores(orgId);
		    paybackApply.setStoresName(orgName);
		}
		model.addAttribute("isManager", isManager);
		//paybackApply.setChannelFlag(ChannelFlag.ZCJ.getCode());
		try {
			String bank = paybackApply.getBank();
			if (!ObjectHelper.isEmpty(bank)) {
				paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
			}
			String stores = paybackApply.getStores();
			if (!ObjectHelper.isEmpty(stores)) {
				paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
			}
			String dayName = paybackApply.getPaybackDay();
			if (!ObjectHelper.isEmpty(dayName)) {
				paybackApply.setPaybackDay(FilterHelper.appendIdFilter(dayName));
			}
			
			String dictDealTypeId = paybackApply.getDictDealTypeId();
			if (!ObjectHelper.isEmpty(dictDealTypeId)) {
				paybackApply.setDictDealType(FilterHelper.appendIdFilter(dictDealTypeId));
			}
			
			String loanMarkJsp = paybackApply.getLoanMark();
			if (!ObjectHelper.isEmpty(loanMarkJsp)) {
				paybackApply.setLoanMark(FilterHelper.appendIdFilter(loanMarkJsp));
			}

			String queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
			paybackApply.setQueryRight(queryRight);

			
			// 执行数据库查询语句，带条件
			Page<PaybackApply> page = new Page<PaybackApply>(request, response);
			paybackApply.setLimit(page.getPageSize());
			if (page.getPageNo() <= 1) {
				paybackApply.setOffset(0);
			} else {
				paybackApply.setOffset((page.getPageNo() - 1) * page.getPageSize());
			}
			
			SqlSessionFactory ssf = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
			SqlSession ss = ssf.openSession();
			Connection cn = ss.getConnection();
			try {
				MyBatisSql sql = MyBatisSqlUtil.getMyBatisSql(
					"com.creditharmony.loan.borrow.payback.dao.CentralizedDeductionDao.centerDeductionAgencyListCnt",
					paybackApply,
					ssf
				);
				PreparedStatement ps = cn.prepareStatement(sql.toString());
				ResultSet rsCnt = ps.executeQuery();
				int cnt = 0;
				if (rsCnt.next()) {
					cnt = rsCnt.getInt(1); 
				}
				
				if (cnt > 0) {
					sql = MyBatisSqlUtil.getMyBatisSql(
						"com.creditharmony.loan.borrow.payback.dao.CentralizedDeductionDao.centerDeductionAgencyList",
						paybackApply,
						ssf
					);
					ps = cn.prepareStatement(sql.toString());
					ResultSet rs = ps.executeQuery();
					
					List<PaybackApply> ls = new ArrayList<PaybackApply>();
					Map<String,Dict> dictMap = DictCache.getInstance().getMap();
					while (rs.next()) {
						PaybackApply rec = new PaybackApply();
						rec.setId(rs.getString("id"));
						rec.setApplyAmount(rs.getBigDecimal("applyAmount"));
						rec.setModifyTime(rs.getDate("modifyTime"));
						
						rec.setSplitBackResult(rs.getString("splitBackResult"));
						String splitBackResult = DictUtils.getLabel(dictMap, "jk_counteroffer_result", rec.getSplitBackResult());
						rec.setSplitBackResultLabel(splitBackResult);
						
						rec.setApplyBackMes(rs.getString("applyBackMes"));
						rec.setContractCode(rs.getString("contractCode"));
						
						rec.setApplyBankName(rs.getString("applyBankName"));
						String applyBankName = DictUtils.getLabel(dictMap, "jk_open_bank", rec.getApplyBankName());
						rec.setApplyBankName(applyBankName);
						
						rec.setDictDealType(rs.getString("dictDealType"));
						String dictDealType = DictUtils.getLabel(dictMap, "jk_deduct_plat", rec.getDictDealType());
						rec.setDictDealTypeLabel(dictDealType);
						
						rec.setApplyReallyAmount(rs.getBigDecimal("applyReallyAmount"));
						rec.setCustomerName(rs.getString("customerName"));
						rec.setOrgName(rs.getString("orgName"));
						rec.setContractMonths(rs.getInt("contractMonths"));
						rec.setContractReplayDay(rs.getDate("contractReplayDay"));
						rec.setPaybackMonthAmount(rs.getBigDecimal("paybackMonthAmount"));
						
						rec.setDictRepayMethod(RepayChannel.DEDUCT.getName());
						
						rec.setDictPayStatus(rs.getString("dictPayStatus"));
						String dictPayStatus = DictUtils.getLabel(dictMap, "jk_repay_status", rec.getDictPayStatus());
						rec.setDictPayStatusLabel(dictPayStatus);
						
						rec.setMonthPayDay(rs.getDate("monthPayDay"));
						
						rec.setDictLoanStatus(rs.getString("dictLoanStatus"));
						String dictLoanStatus = DictUtils.getLabel(dictMap, "jk_loan_apply_status", rec.getDictLoanStatus());
						rec.setDictLoanStatusLabel(dictLoanStatus);
						
						rec.setDictSourceType(rs.getString("dictSourceType"));
						rec.setPaybackMaxOverduedays(rs.getInt("paybackMaxOverduedays"));
						
						rec.setLoanMark(rs.getString("loanMark"));
						String loanMark = DictUtils.getLabel(dictMap, "jk_channel_flag", rec.getLoanMark());
						rec.setLoanMarkLabel(loanMark);
						
						rec.setPaybackBuleAmount(rs.getBigDecimal("paybackBuleAmount"));
						rec.setRpaybackId(rs.getString("rPaybackId"));
						rec.setFailReason(rs.getString("failReason"));
						rec.setBankAccount(rs.getString("bankAccount"));
						rec.setNotPaybackMonthAmount(rs.getBigDecimal("notPaybackMonthAmount"));
						rec.setAlsoPaybackMonthAmount(rs.getBigDecimal("alsoPaybackMonthAmount"));
						rec.setSumAmont(String.valueOf(rs.getBigDecimal("sumAmont")));
						rec.setSumReallyAmont(String.valueOf(rs.getBigDecimal("sumReallyAmont")));
						
						rec.setLoanManagerName(rs.getString("loanManagerName"));
						User loanManagerName = userManager.get(rec.getLoanManagerName());
						if(loanManagerName != null){
							rec.setLoanManagerName(loanManagerName.getName());
						}
						
						rec.setLoanTeamManagerName(rs.getString("loanTeamManagerName"));
						User loanTeamManagerName = userManager.get(rec.getLoanTeamManagerName());
						if(loanTeamManagerName != null){
							rec.setLoanTeamManagerName(loanTeamManagerName.getName());
						}
						
						rec.setLoanSurveyEmpName(rs.getString("loanSurveyEmpName"));
						User loanSurveyEmpName = userManager.get(rec.getLoanSurveyEmpName());
						if(loanSurveyEmpName != null){
							rec.setLoanSurveyEmpName(loanSurveyEmpName.getName());
						}
						
						rec.setLoanCustomerService(rs.getString("loanCustomerService"));
						User loanCustomerService = userManager.get(rec.getLoanCustomerService());
						if(loanCustomerService != null){
							rec.setLoanCustomerService(loanCustomerService.getName());
						}
						
						rec.setModel(rs.getString("model"));
						String modelLabel = DictUtils.getLabel(dictMap, "jk_loan_model", rec.getModel());
						rec.setModelLabel(modelLabel);
						
						rec.setBillDay(rs.getString("billDay"));
						
						ls.add(rec);
					}
					
					page.setCount(cnt);
					page.setList(ls);
				} else {
					page.setCount(0);
					page.setList(new ArrayList<PaybackApply>());
				}
			} catch (Exception e) { 
				e.printStackTrace(); 
			} finally {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
//			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
//			if (ArrayHelper.isNotEmpty(page.getList())) {
////				nullJudgment(page.getList(), paybackApply);
//				for (PaybackApply pa : page.getList()) {
//					pa.setDictRepayMethod(RepayChannel.DEDUCT.getName());
//					String dictPayStatus = DictUtils.getLabel(dictMap, "jk_repay_status", pa.getDictPayStatus());
//					pa.setDictPayStatusLabel(dictPayStatus);
//	
//					String dictLoanStatus = DictUtils.getLabel(dictMap, "jk_loan_apply_status", pa.getDictLoanStatus());
//					pa.setDictLoanStatusLabel(dictLoanStatus);
//	
//					String splitBackResult = DictUtils.getLabel(dictMap, "jk_counteroffer_result", pa.getSplitBackResult());
//					pa.setSplitBackResultLabel(splitBackResult);
//	
//					String loanMark = DictUtils.getLabel(dictMap, "jk_channel_flag", pa.getLoanMark());
//					pa.setLoanMarkLabel(loanMark);
//	
//					String dictDealType = DictUtils.getLabel(dictMap, "jk_deduct_plat", pa.getDictDealType());
//					pa.setDictDealTypeLabel(dictDealType);
//					
//					String modelLabel=DictUtils.getLabel(dictMap, "jk_loan_model",pa.getModel());
//					pa.setModelLabel(modelLabel);
//					
//					String applyBankName = DictUtils.getLabel(dictMap, "jk_open_bank",pa.getApplyBankName());
//					pa.setApplyBankName(applyBankName);
//					User loanManagerName = userManager.get(pa.getLoanManagerName());
//					User loanTeamManagerName = userManager.get(pa.getLoanTeamManagerName());
//					User loanSurveyEmpName = userManager.get(pa.getLoanSurveyEmpName());
//					User loanCustomerService = userManager.get(pa.getLoanCustomerService());
//					if(loanManagerName != null){
//						pa.setLoanManagerName(loanManagerName.getName());
//					}
//					if(loanTeamManagerName != null){
//						pa.setLoanTeamManagerName(loanTeamManagerName.getName());
//					}
//					if(loanSurveyEmpName != null){
//						pa.setLoanSurveyEmpName(loanSurveyEmpName.getName());
//					}
//					if(loanCustomerService != null){
//						pa.setLoanCustomerService(loanCustomerService.getName());
//					}
//				}
//			}
			model.addAttribute("waitPage", page);
			
			paybackApply.setPaybackDay(dayName);
			paybackApply.setLoanMark(loanMarkJsp);
			model.addAttribute("PaybackApply", paybackApply);
			NumTotal numTotal = new  NumTotal();
			List<PaybackApply> paybacklist = page.getList();
			if (paybacklist.size() > 0) {
					numTotal.setNum(String.valueOf(page.getCount()));
					numTotal.setTotal(paybacklist.get(0).getSumAmont()== null ? "0" :paybacklist.get(0).getSumAmont());
					numTotal.setReallyTotal(paybacklist.get(0).getSumReallyAmont()== null ? "0" :paybacklist.get(0).getSumReallyAmont());
					
			} else {
				numTotal.setNum("0");
				numTotal.setTotal("0");
				numTotal.setReallyTotal("0");
			}
			model.addAttribute("numTotal", numTotal);
			
			// 查询还款日
			List<GlBill> dayList = dateService.getRepaymentDate();
			model.addAttribute("dayList", dayList);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("集中划扣已办列表获取失败\r\n" + e.getMessage());
			model.addAttribute("message", "集中划扣已办列表获取失败");
		}
		return "borrow/payback/repayment/centralizedDeduction";
	}

	/**
	 * 划扣已办列表
	 * 2015年12月17日
	 * By 李强
	 * @param model
	 * @param paybackSplit
	 * @return buckleHasBeenDone.jsp
	 */
	@RequestMapping(value = "buckleHasBeenDoneList")
	public String buckleHasBeenDoneList(HttpServletRequest request,HttpServletResponse response,Model model, PaybackApply paybackApply) {
		/*paybackApply.setEnumOne(CounterofferResult.PAYMENT_FAILED.getCode());// 回盘结果：划扣失败  1
		paybackApply.setEnumTwo(CounterofferResult.PAYMENT_SUCCEED.getCode());// 回盘结果：划扣成功  2
		paybackApply.setEnumFive(CounterofferResult.RETURN.getCode());//回盘结果：退回 6
*/		//回盘结果：划扣失败1、划扣成功2、划扣退回6、门店放弃8
		String[] splitBackResultArray={CounterofferResult.PAYMENT_FAILED.getCode(),CounterofferResult.PAYMENT_SUCCEED.getCode(),
				CounterofferResult.RETURN.getCode(),CounterofferResult.STORE_GIVEUP.getCode(),CounterofferResult.PROCESS.getCode()};
		paybackApply.setSplitBackResultArray(splitBackResultArray);
		
		paybackApply.setEnumThree(OperateType.PAYMENT_DEDUCT.getCode());// 操作类型：待还款划扣 1
		paybackApply.setEnumFour(RepayChannel.DEDUCT.getCode()); // 还款类型 划扣
		String urgeManage =request.getParameter("urgeManage");
		paybackApply.setUrgeManage(urgeManage);
		String bank = paybackApply.getBank();
		if (!ObjectHelper.isEmpty(bank)) {
			paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
		}
		String stores = paybackApply.getStores();
		if (!ObjectHelper.isEmpty(stores)) {
			paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
		}
		String dayName = paybackApply.getPaybackDay();
		if (!ObjectHelper.isEmpty(dayName)) {
			paybackApply.setPaybackDay(FilterHelper.appendIdFilter(dayName));
		}
		
		String dictDealTypeId = paybackApply.getDictDealTypeId();
		if (!ObjectHelper.isEmpty(dictDealTypeId)) {
			paybackApply.setDictDealType(FilterHelper.appendIdFilter(dictDealTypeId));
		}
		String loanMarkJsp = paybackApply.getLoanMark();
		if (!ObjectHelper.isEmpty(loanMarkJsp)) {
			paybackApply.setLoanMark(FilterHelper.appendIdFilter(loanMarkJsp));
		}
		
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			model.addAttribute(resKey, 1);			
		}
		model.addAttribute("menuId",menuId);
		//---------------------------------------
		//数据权限控制
		   String queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
		   paybackApply.setQueryRight(queryRight);
		//---------------------------------------
		//---------------------------------------   
		//数据管理部不显示资产家数据
		//paybackApply.setChannelFlag(ChannelFlag.ZCJ.getCode());
		Page<PaybackApply> waitPage = centralizedDeductionToService.getDeductionListPage(paybackApply,request,response);
		
		if (ArrayHelper.isNotEmpty(waitPage.getList())) {
			nullJudgment(waitPage.getList(),paybackApply);
		}
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		NumTotal numTotal = new  NumTotal();
		for(PaybackApply pa:waitPage.getList()){
			String applyBankName =  DictUtils.getLabel(dictMap,"jk_open_bank",pa.getApplyBankName());
			pa.setApplyBankNameLabel(applyBankName);
			
			String dictPayStatus= DictUtils.getLabel(dictMap,"jk_repay_status",pa.getDictPayStatus());
			pa.setDictPayStatusLabel(dictPayStatus);
			
			String dictRepayMethod= DictUtils.getLabel(dictMap,"jk_repay_way",pa.getDictRepayMethod());
			pa.setDictRepayMethodLabel(dictRepayMethod);
			
			String dictLoanStatus= DictUtils.getLabel(dictMap,"jk_loan_status",pa.getDictLoanStatus());
			pa.setDictLoanStatusLabel(dictLoanStatus);
			
			String dictDealType= DictUtils.getLabel(dictMap,"jk_deduct_plat",pa.getDictDealType());
			pa.setDictDealTypeLabel(dictDealType);
			String splitBackResult= DictUtils.getLabel(dictMap,"jk_counteroffer_result",pa.getSplitBackResult());
			pa.setSplitBackResultLabel(splitBackResult);
			
			String modelLabel= DictUtils.getLabel(dictMap,"jk_loan_model",pa.getModel());
			pa.setModelLabel(modelLabel);
			
			String loanMarkLabel= DictUtils.getLabel(dictMap,"jk_channel_flag",pa.getLoanMark());
			pa.setLoanMarkLabel(loanMarkLabel);
			numTotal.setTotal(pa.getSumAmont());
			
		}
		
		numTotal.setNum(waitPage.getCount()+"");
		paybackApply.setPaybackDay(dayName);
		paybackApply.setLoanMark(loanMarkJsp);
		List<GlBill> dayList = dateService.getRepaymentDate();
		
		//电催管辖标识
		model.addAttribute("urgeManage", urgeManage);
		model.addAttribute("numTotal", numTotal);
		model.addAttribute("dayList", dayList);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("PaybackApply", paybackApply);
		logger.debug("invoke CentralizedDeductionController method: buckleHasBeenDoneList, consult.id is: "+ waitPage);
		return "borrow/payback/repayment/buckleHasBeenDone";
	}

	/**
	 * 查看 划扣已办列表信息
	 * 2015年12月17日
	 * By 李强
	 * @param model
	 * @param bid
	 * @param paybackSplit
	 * @return seeBuckleHasBeenDone.jsp
	 */
	@RequestMapping(value = "seeBuckleHasBeenDone")
	public String seeBuckleHasBeenDone(Model model,String bId, PaybackApply paybackApply) {
		paybackApply = centralizedDeductionToService.seeCentralizedDeduction(bId);
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(paybackApply.getProductType());
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			if(productList.size()>0){
			paybackApply.setProductType(productList.get(DeductedConstantEx.INIT_ZERO).getProductName());
			}
			
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",paybackApply.getDictLoanStatus());
			paybackApply.setDictLoanStatusLabel(dictLoanStatus);
			
			String dictDealType=DictCache.getInstance().getDictLabel("jk_deduct_plat",paybackApply.getDictDealType());
			paybackApply.setDictDealTypeLabel(dictDealType);
			
			String applyBankName=DictCache.getInstance().getDictLabel("jk_open_bank",paybackApply.getApplyBankName());
			paybackApply.setApplyBankNameLabel(applyBankName);
			model.addAttribute("paybackApply", paybackApply);
		logger.debug("invoke CentralizedDeductionController method: seeBuckleHasBeenDone, consult.id is: "+ paybackApply);
		return "borrow/payback/repayment/seeBuckleHasBeenDone";
	}
	
	/**
	 * 导出集中划扣数据列表
	 * 2015年12月25日
	 * By 李强
	 * @param request
	 * @param response
	 * @param idVal 
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,PaybackApply paybackApply){
		String idVal = paybackApply.getId();
		if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			paybackApply.setId(FilterHelper.appendIdFilter(idVal));
		}
		String bank = paybackApply.getBank();
		if (!ObjectHelper.isEmpty(bank)) {
			paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
		}
		String stores = paybackApply.getStores();
		if (!ObjectHelper.isEmpty(stores)) {
			paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
		}
		String dayName = paybackApply.getPaybackDay();
		if (!ObjectHelper.isEmpty(dayName)) {
			paybackApply.setPaybackDay(FilterHelper.appendIdFilter(dayName));
		}
		String dictDealTypeId = paybackApply.getDictDealTypeId();
		if (!ObjectHelper.isEmpty(dictDealTypeId)) {
			paybackApply.setDictDealType(FilterHelper.appendIdFilter(dictDealTypeId));
		}
		String loanMarkJsp = paybackApply.getLoanMark();
		if (!ObjectHelper.isEmpty(loanMarkJsp)) {
			paybackApply.setLoanMark(FilterHelper.appendIdFilter(loanMarkJsp));
		}
		//数据权限导出
		   String queryRight = DataScopeUtil.getDataScope("jli", SystemFlag.LOAN.value);
		   paybackApply.setQueryRight(queryRight);
		ExportCenterDeductHelper.exportCenterTodo(paybackApply, response);
	}
	
	/**
	 * 导出划扣已办数据列表
	 * 2015年12月25日
	 * By 李强
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "redlineExportExcel")
	public void redlineExportExcel(HttpServletRequest request,HttpServletResponse response, String idVal, PaybackApply paybackApply){
		String urgeManage =request.getParameter("urgeManage");
		paybackApply.setUrgeManage(urgeManage);
		//ExcelUtils excelutil = new ExcelUtils();
		String[] id=idVal.split(";");
		//List<PaybackApply> customerList = new ArrayList<PaybackApply>();
		//PaybackApply paybackApply = new PaybackApply();
		paybackApply.setEnumOne(CounterofferResult.PAYMENT_FAILED.getCode());// 回盘结果：划扣失败
		paybackApply.setEnumTwo(CounterofferResult.PAYMENT_SUCCEED.getCode());// 回盘结果：划扣成功
		paybackApply.setEnumSix(CounterofferResult.PROCESS.getCode());// 回盘结果：处理中
		paybackApply.setEnumThree(OperateType.PAYMENT_DEDUCT.getCode());// 操作类型：待集中划扣1
		paybackApply.setEnumFour(RepayChannel.DEDUCT.getCode()); // 还款类型 划扣
			if (StringUtils.isEmpty(idVal)) {
				// 如果没有选中的数据，则导出处全部的数据
				String bank = paybackApply.getBank();
				if (!ObjectHelper.isEmpty(bank)) {
					paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
				}
				String stores = paybackApply.getStores();
				if (!ObjectHelper.isEmpty(stores)) {
					paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
				}
				String dictDealTypeId = paybackApply.getDictDealTypeId();
				if (!ObjectHelper.isEmpty(dictDealTypeId)) {
					paybackApply.setDictDealType(FilterHelper.appendIdFilter(dictDealTypeId));
				}
				String loanMarkJsp = paybackApply.getLoanMark();
				if (!ObjectHelper.isEmpty(loanMarkJsp)) {
					paybackApply.setLoanMark(FilterHelper.appendIdFilter(loanMarkJsp));
				}
				CentralizedDeductionExportUtil.exportData(paybackApply, response, DeductedConstantEx.CENTRALIZEDS + formatDate(new Date()));
				/*Page<PaybackApply> waitPage = centralizedDeductionToService.allCentralizedDeductionList(new Page<PaybackApply>(request, response),paybackApply);
					nullJudgment(waitPage.getList(),paybackApply);
					for (int i = 0; i < waitPage.getList().size(); i++) {
						// 实际到帐金额 (还款申请表中实际到帐金额)
						BigDecimal splitMoeny = waitPage.getList().get(i).getApplyReallyAmount();
						waitPage.getList().get(i).setApplyReallyAmount(splitMoeny);
						// 当期已还期供 = 期供金额-申请金额
						BigDecimal contractMonthRepayAmount = waitPage.getList().get(i).getContractMonthRepayAmount();
						BigDecimal applyAmount = waitPage.getList().get(i).getApplyAmount();
						if(contractMonthRepayAmount.equals(0)){
							BigDecimal alsoPaybackMonthAmount = new BigDecimal(DeductedConstantEx.ALSO_AMOUNT);
							waitPage.getList().get(i).setAlsoPaybackMonthAmount(alsoPaybackMonthAmount);
						}else{
							waitPage.getList().get(i).setAlsoPaybackMonthAmount(contractMonthRepayAmount.subtract(applyAmount));
						}
						// 当期未还期供 
						waitPage.getList().get(i).setNotPaybackMonthAmount(applyAmount);
				
				}
					excelutil.exportExcel(waitPage.getList(), DeductedConstantEx.CENTRALIZEDS
							+ formatDate(new Date()), PaybackApply.class, FileExtension.XLSX,
							FileExtension.OUT_TYPE_TEMPLATE, response,null);*/
			}else{
				// 如果有选中的数据 则导出选中的数据
				paybackApply = new PaybackApply();
				paybackApply.setEnumOne(CounterofferResult.PAYMENT_FAILED.getCode());// 回盘结果：划扣失败
				paybackApply.setEnumTwo(CounterofferResult.PAYMENT_SUCCEED.getCode());// 回盘结果：划扣成功
				paybackApply.setEnumSix(CounterofferResult.PROCESS.getCode());// 回盘结果：处理中
				
				paybackApply.setEnumThree(OperateType.PAYMENT_DEDUCT.getCode());// 操作类型：待集中划扣1
				paybackApply.setIds(id);
				paybackApply.setEnumFour(RepayChannel.DEDUCT.getCode()); // 还款类型 划扣
				String loanMarkJsp = paybackApply.getLoanMark();
				if (!ObjectHelper.isEmpty(loanMarkJsp)) {
					paybackApply.setLoanMark(FilterHelper.appendIdFilter(loanMarkJsp));
				}
				CentralizedDeductionExportUtil.exportData(paybackApply, response, DeductedConstantEx.CENTRALIZEDS + formatDate(new Date()));

				/*for(int i = DeductedConstantEx.INIT_ZERO; i < id.length - DeductedConstantEx.ONE; i++ ){
					List<PaybackApply> nullList = new ArrayList<PaybackApply>();
					nullJudgment(nullList,paybackApply);
					paybackApply = centralizedDeductionToService.redlineExportExcel(id[i + DeductedConstantEx.ONE]);
					// 还款类型
					paybackApply.setHuankType(DeductedConstantEx.HUANGKTYPE);
					
					// 实际到帐金额 (还款申请表中实际到帐金额)
					BigDecimal splitMoeny = paybackApply.getApplyReallyAmount();
					paybackApply.setApplyReallyAmount(splitMoeny);
					// 当期已还期供 = 期供金额-申请金额
					BigDecimal contractMonthRepayAmount = paybackApply.getContractMonthRepayAmount() == null ? BigDecimal.ZERO :  paybackApply.getContractMonthRepayAmount() ;
					BigDecimal applyAmount = paybackApply.getApplyAmount();
					if(contractMonthRepayAmount.equals(BigDecimal.ZERO)){
						BigDecimal alsoPaybackMonthAmount = new BigDecimal(DeductedConstantEx.ALSO_AMOUNT);
						paybackApply.setAlsoPaybackMonthAmount(alsoPaybackMonthAmount);
					}else{
						paybackApply.setAlsoPaybackMonthAmount(contractMonthRepayAmount.subtract(applyAmount));
					}
					// 当期未还期供 
					paybackApply.setNotPaybackMonthAmount(applyAmount);
					customerList.add(paybackApply);
				}*/
				/*excelutil.exportExcel(list, DeductedConstantEx.CENTRALIZEDS
						+formatDate(new Date()), PaybackApply.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE, response, null);*/
			}
	}
	
	/**
	 * 金额运算时非空判断
	 * 2015年12月9日
	 * By 李强
	 * @param slist
	 * @param loanServiceBureau
	 */
	public void nullJudgment(List<PaybackApply> slist,PaybackApply paybackApply) {
		if (ArrayHelper.isNotEmpty(slist)) {
			for (int i = 0; i < slist.size(); i++) {
				if (slist.get(i).getApplyAmount() == null) {// 申请金额(划扣金额)
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setApplyAmount(bigDecimals);
				}
				if (slist.get(i).getApplyReallyAmount() == null) {// 实际到帐金额
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setApplyReallyAmount(bigDecimals);
				}
				if (slist.get(i).getContractMonthRepayAmount() == null) {// 期供金额
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setContractMonthRepayAmount(bigDecimals);
				}
				if (slist.get(i).getPaybackBuleAmount() == null) {// 蓝补总额
					BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
					slist.get(i).setPaybackBuleAmount(bigDecimals);
				}
			}
		}
		if (paybackApply.getApplyAmount() == null) {// 申请金额(划扣金额)
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			paybackApply.setApplyAmount(bigDecimals);
		}
		if (paybackApply.getApplyReallyAmount() == null) {// 实际到帐金额
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			paybackApply.setApplyReallyAmount(bigDecimals);
		}
		if (paybackApply.getContractMonthRepayAmount() == null) {// 期供金额
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			paybackApply.setContractMonthRepayAmount(bigDecimals);
		}
		if (paybackApply.getPaybackBuleAmount() == null) {// 蓝补总额
			BigDecimal bigDecimals = new BigDecimal(DeductedConstantEx.INIT_AMOUNT);
			paybackApply.setPaybackBuleAmount(bigDecimals);
		}
		
		
	}
	
	private String formatDate(Date date){
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
		return format.format(d);
	}
}
