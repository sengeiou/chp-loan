package com.creditharmony.loan.borrow.payback.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.Eletric;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.file.util.Zip;
import com.creditharmony.loan.borrow.account.constants.DownLoadFileType;
import com.creditharmony.loan.borrow.account.constants.FileType;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.payback.entity.EletricPage;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackUseService;
import com.creditharmony.loan.borrow.payback.service.OverdueManageService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.CeUtils;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.utils.WorkDayCheck;
import com.google.common.collect.Lists;
import com.creditharmony.loan.common.utils.DateUtil;

/**
 * 还款冲抵列表-Controller
 * 
 * @Class Name ApplyPaybackUseController
 * @author zhangfeng
 * @Create In 2015年12月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/applyPaybackUse")
public class ApplyPaybackUseController extends BaseController {

	@Autowired
	private ApplyPaybackUseService applyPaybackUseService;
	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private OverdueManageService overdueManageService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private RepaymentDateService dateService;
	
	/**
	 * 检索还款冲抵列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String goApplyPaybackUseList(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Model model, 
		Payback params
	) {
		params.setEffectiveFlag(YESNO.YES.getCode());
		params.setDictPayStatus(
			"'" + 
			RepayStatus.OVERDUE.getCode() + "','" + 
			RepayStatus.PEND_REPAYMENT.getCode() + "','" + 
			RepayStatus.PRE_SETTLE_VERIFY.getCode() + "','" + 
			RepayStatus.SETTLE_CONFIRM.getCode() + "','" + 
			RepayStatus.PRE_SETTLE_CONFIRM.getCode() + "','" + 
			RepayStatus.SETTLE.getCode() + "','" + 
			RepayStatus.PRE_SETTLE.getCode() + "','" +
			RepayStatus.SETTLE_FAILED.getCode() + 
			"'"
		);
		String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
		params.setQueryRight(queryRight);
//		Page<Payback> paybackPage = applyPayService.selApplyPaybackUse(new Page<Payback>(request, response), params);
		
		// 执行数据库查询语句，带条件
		Page<Payback> page = new Page<Payback>(request, response);
		params.setLimit(page.getPageSize());
		if (page.getPageNo() <= 1) {
			params.setOffset(0);
		} else {
			params.setOffset((page.getPageNo() - 1) * page.getPageSize());
		}
		
		SqlSessionFactory ssf = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		SqlSession ss = ssf.openSession();
		Connection cn = ss.getConnection();
		try {
			MyBatisSql sql = MyBatisSqlUtil.getMyBatisSql(
				"com.creditharmony.loan.common.dao.PaybackDao.selApplyPaybackUseCnt",
				params,
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
					"com.creditharmony.loan.common.dao.PaybackDao.selApplyPaybackUse",
					params,
					ssf
				);
				ps = cn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				List<Payback> ls = new ArrayList<Payback>();
				while (rs.next()) {
					Payback rec = new Payback();
					rec.setContractCode(rs.getString("contract_code"));						// 合同编号
					rec.setDictPayStatus(rs.getString("dict_pay_status"));					// 还款状态
//					rec.setDictPayStatusLabel(rs.getString("dictPayStatusLabel")); 			// 还款状态Label
					rec.setPaybackDay(rs.getInt("payback_day"));							// 还款日
					rec.setPaybackBuleAmount(rs.getBigDecimal("payback_bule_amount"));		// 蓝补金额
					rec.setPaybackMonthAmount(rs.getBigDecimal("payback_month_amount"));	// 期供
					rec.setPaybackBackAmount(rs.getBigDecimal("payback_back_amount"));		// 返款金额
					rec.setOverdueCount(rs.getString("overdue_count"));		// 总逾期次数

					LoanCustomer lc = new LoanCustomer();
					lc.setCustomerName(rs.getString("customer_name"));						// 客户姓名
					rec.setLoanCustomer(lc);
					
					Contract c = new Contract();
					c.setContractMonths(rs.getBigDecimal("contract_months"));				// 批借期限
					c.setContractReplayDay(rs.getDate("contract_replay_day"));				// 起始还款日期
					c.setContractEndDay(rs.getDate("contract_end_day"));					// 合同到期日期
					rec.setContract(c);
					
					LoanInfo li = new LoanInfo();
					li.setDictLoanStatus(rs.getString("dict_loan_status"));					// 借款状态
//					li.setDictLoanStatusLabel(rs.getString("dictLoanStatusLabel")); 		// 借款状态Label
//					li.setLoanStoreOrgId(rs.getString("loan_store_orgid"));					// 组织机构ID
					li.setLoanStoreOrgName(rs.getString("loanStoreOrgName"));				// 门店
					li.setLoanFlag(rs.getString("loan_flag"));								// 渠道
//					li.setLoanFlagLabel(rs.getString("loanFlagLabel")); 					// 渠道Label
					li.setModel(rs.getString("model")); 									// 模型
//					li.setModelLabel(rs.getString("modelLabel")); 							// 模型Label
					rec.setLoanInfo(li);
					
					LoanBank lb = new LoanBank();
					lb.setBankName(rs.getString("bank_name"));								// 开户行
//					lb.setBankNameLabel(rs.getString("bankNameLabel"));						// 开户行
					rec.setLoanBank(lb);
					
//					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
//						StringUtils.isNotEmpty(rec.getLoanInfo().getLoanStoreOrgId())) {
//						rec.getLoanInfo().setLoanStoreOrgName(
//							String.valueOf(OrgCache.getInstance().get(rec.getLoanInfo().getLoanStoreOrgId())));
//			    	}
					
					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
						StringUtils.isNotEmpty(rec.getLoanInfo().getLoanFlag())) {
						String channelFlagLabel = DictCache.getInstance().getDictLabel(
							"jk_channel_flag", rec.getLoanInfo().getLoanFlag());
						rec.getLoanInfo().setLoanFlagLabel(channelFlagLabel);
			    	}
					
					if(StringUtils.isNotEmpty(rec.getDictPayStatus())){
						String repayStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_repay_status", rec.getDictPayStatus());
						rec.setDictPayStatusLabel(repayStatusLabel);
					}
					
					if (!ObjectHelper.isEmpty(rec.getLoanBank()) && 
						StringUtils.isNotEmpty(rec.getLoanBank().getBankName())) {
						String dictOpenBankLabel = DictCache.getInstance().getDictLabel(
							"jk_open_bank", rec.getLoanBank().getBankName());
						rec.getLoanBank().setBankNameLabel(dictOpenBankLabel);
			    	}
					
					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
						StringUtils.isNotEmpty(rec.getLoanInfo().getDictLoanStatus())) {
						String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_loan_apply_status", rec.getLoanInfo().getDictLoanStatus());
						rec.getLoanInfo().setDictLoanStatusLabel(dictLoanStatusLabel);
			    	}
					
					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
						StringUtils.isNotEmpty(rec.getLoanInfo().getModel())) {
						String dictLoanModel = DictCache.getInstance().getDictLabel(
							"jk_loan_model", rec.getLoanInfo().getModel());
						rec.getLoanInfo().setModelLabel(dictLoanModel);
			    	}
					rec.setOverdueDays(rs.getInt("overdue_days"));
					String overdueLevel = rs.getString("overdue_level");
					if(overdueLevel!=null && !overdueLevel.equals("") 
							&& !rec.getDictPayStatus().equals(RepayStatus.SETTLE.getCode())
							&& !rec.getDictPayStatus().equals(RepayStatus.PRE_SETTLE.getCode())
							&& !rec.getDictPayStatus().equals(RepayStatus.SETTLE_CONFIRM.getCode())){
						SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
						int level = DateUtil.getMonthSpaceByDate(df.parse(overdueLevel),new Date());
						if(level<6 && level>=0){
							rec.setOverdueLevel("M"+(level+1));
						}else if(level>=6){
							rec.setOverdueLevel("M6+");
						}else if(rec.getDictPayStatus().equals(RepayStatus.PEND_REPAYMENT.getCode())){
							rec.setOverdueLevel("C");
						}
					}else if(overdueLevel!=null && overdueLevel.equals("") 
							&& rec.getDictPayStatus().equals(RepayStatus.PEND_REPAYMENT.getCode())){
							rec.setOverdueLevel("C");
					}
					

					ls.add(rec);
				}
				
				page.setCount(cnt);
				page.setList(ls);
			} else {
				page.setCount(0);
				page.setList(null);
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
		
//		if(ArrayHelper.isNotEmpty(page.getList())){
//			for (Payback p : page.getList()) {
//				if (!ObjectHelper.isEmpty(p.getLoanInfo()) && StringUtils.isNotEmpty(p.getLoanInfo().getLoanStoreOrgId())) {
//					p.getLoanInfo().setLoanStoreOrgName(
//									String.valueOf(OrgCache.getInstance().get(p.getLoanInfo().getLoanStoreOrgId())));
//		    	}
//				
//				if (!ObjectHelper.isEmpty(p.getLoanInfo()) && StringUtils.isNotEmpty(p.getLoanInfo().getLoanFlag())) {
//					String channelFlagLabel = DictCache.getInstance().getDictLabel("jk_channel_flag",
//							p.getLoanInfo().getLoanFlag());
//					p.getLoanInfo().setLoanFlagLabel(channelFlagLabel);
//		    	}
//				
//				if(StringUtils.isNotEmpty(p.getDictPayStatus())){
//					String repayStatusLabel = DictCache.getInstance().getDictLabel("jk_repay_status",
//							p.getDictPayStatus());
//					p.setDictPayStatusLabel(repayStatusLabel);
//				}
//				
//				if (!ObjectHelper.isEmpty(p.getLoanBank()) && StringUtils.isNotEmpty(p.getLoanBank().getBankName())) {
//
//					String dictOpenBankLabel = DictCache.getInstance().getDictLabel("jk_open_bank",
//									p.getLoanBank().getBankName());
//					p.getLoanBank().setBankNameLabel(dictOpenBankLabel);
//		    	}
//				
//				if (!ObjectHelper.isEmpty(p.getLoanInfo()) && StringUtils.isNotEmpty(p.getLoanInfo().getDictLoanStatus())) {
//					String dictLoanStatusLabel = DictCache.getInstance().getDictLabel("jk_loan_apply_status",
//									p.getLoanInfo().getDictLoanStatus());
//					p.getLoanInfo().setDictLoanStatusLabel(dictLoanStatusLabel);
//		    	}
//				
//				if (!ObjectHelper.isEmpty(p.getLoanInfo()) && StringUtils.isNotEmpty(p.getLoanInfo().getModel())) {
//					String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model", p.getLoanInfo().getModel());
//					 p.getLoanInfo().setModelLabel(dictLoanModel);
//		    	}
//	 		}
//		}
		
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		int day = WorkDayCheck.getConfirmStartTime(Calendar.getInstance(),1);
		
		Date d = new Date();
		@SuppressWarnings("deprecation")
		int hour= d.getHours();
		String isValidate = YESNO.NO.getCode();
		if (hour >= 16) {
			isValidate = YESNO.YES.getCode();
		}
		
		model.addAttribute("checkDay", String.valueOf(day));
		model.addAttribute("isfour", isValidate);
		model.addAttribute("dayList", dayList);
		model.addAttribute("paybackList", page);
		return "borrow/payback/applypay/applyPaybackUse";
	}
	
	/**
	 * 还款用途申请详细页面
	 * 2015年12月9日
	 * By zhangfeng
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "form")
	public String goApplyPaybackInfoForm(Model model, Payback payback) {
		List<Payback> paybackList = new ArrayList<Payback>();
		paybackList = applyPayService.findPayback(payback);
		if(StringUtils.equals(payback.getPaybackApply().getDictPayUse(), RepayType.EARLY_SETTLE.getCode())){
			// 提前结清
			applyPaybackUseService.findCurrentmonthAmount(paybackList.get(0));
		}else{
			// 提前还款
			applyPaybackUseService.findOverdueMonthAmount(paybackList.get(0));
		}
		// 设置借款状态
		if(!(ObjectHelper.isEmpty(paybackList.get(0)) && ObjectHelper.isEmpty(paybackList.get(0).getLoanInfo()))){
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			String dictLoanStatusLabel = DictUtils.getLabel(dictMap,LoanDictType.LOAN_APPLY_STATUS,paybackList.get(0).getLoanInfo().getDictLoanStatus());
			paybackList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatusLabel);
		}
		// 还款申请用途区分提前结清和提前还款
		PaybackApply paybackApply = new PaybackApply();
		paybackApply.setDictPayUse(payback.getPaybackApply().getDictPayUse());
		paybackList.get(0).setPaybackApply(paybackApply);
		
		// 添加token 
		TokenUtils.removeToken(payback.getFinishTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    paybackList.get(0).setFinishTokenId(tokenMap.get("tokenId"));
	    paybackList.get(0).setFinishToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		model.addAttribute("payback", paybackList.get(0));		
		logger.debug("invoke ApplyPaybackUseController method: goApplyPaybackUseList, contarctCode is: "+ payback.getContractCode());
		return "borrow/payback/applypay/applyPaybackInfo";
	}
	
	/**
	 * 还款用途申请详细页面
	 * 2015年12月9日
	 * By zhangfeng
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "formPhoneSale")
	public String formPhoneSale(Model model, Payback payback) {
		List<Payback> paybackList = new ArrayList<Payback>();
		paybackList = applyPayService.findPayback(payback);
		if(StringUtils.equals(payback.getPaybackApply().getDictPayUse(), RepayType.EARLY_SETTLE.getCode())){
			// 提前结清
			applyPaybackUseService.findCurrentmonthAmount(paybackList.get(0));
		}else{
			// 提前还款
			applyPaybackUseService.findOverdueMonthAmount(paybackList.get(0));
		}
		// 设置借款状态
		if(!(ObjectHelper.isEmpty(paybackList.get(0)) && ObjectHelper.isEmpty(paybackList.get(0).getLoanInfo()))){
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			String dictLoanStatusLabel = DictUtils.getLabel(dictMap,LoanDictType.LOAN_APPLY_STATUS,paybackList.get(0).getLoanInfo().getDictLoanStatus());
			paybackList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatusLabel);
		}
		// 还款申请用途区分提前结清和提前还款
		PaybackApply paybackApply = new PaybackApply();
		paybackApply.setDictPayUse(payback.getPaybackApply().getDictPayUse());
		paybackList.get(0).setPaybackApply(paybackApply);
		
		// 添加token 
		TokenUtils.removeToken(payback.getFinishTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    paybackList.get(0).setFinishTokenId(tokenMap.get("tokenId"));
	    paybackList.get(0).setFinishToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		model.addAttribute("payback", paybackList.get(0));		
		logger.debug("invoke ApplyPaybackUseController method: goApplyPaybackUseList, contarctCode is: "+ payback.getContractCode());
		return "borrow/payback/applypay/applyPaybackPhoneSaleInfo";
	}
	
	/**
	 * 保存还款用途申请
	 * 2016年1月6日
	 * By zhangfeng
	 *  @param files
	 * @param payback
	 * @return redirect page
	 */
	@RequestMapping(value = "save")
	public String saveApplyPaybackUse(@RequestParam("files") MultipartFile[] files, Payback payback,Model model,RedirectAttributes redirectAttributes) {
        String msg = "";
        String redirectPath ="";
        
        //判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = payback.getFinishTokenId();
			String curToken = payback.getFinishToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			try {
				// 判断提前结清和提前还款
				if(StringUtils.equals(payback.getPaybackApply().getDictPayUse(), RepayType.EARLY_SETTLE.getCode())){
					// 提前结清保存主表
					payback.setDictPayStatus(RepayStatus.PRE_SETTLE_VERIFY.getCode());
					payback.preUpdate();
					applyPayService.updatePayback(payback);
					// 保存冲抵申请信息
					payback.getPaybackCharge().setChargeStatus(AgainstStatus.AGAINST_VERIFY.getCode());
					payback.getPaybackCharge().setContractCode(payback.getContractCode());
					payback.getPaybackCharge().setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
					payback.getPaybackCharge().setIsNewRecord(false);
					payback.getPaybackCharge().preInsert();
					applyPaybackUseService.insertOffset(files,payback.getPaybackCharge());
					// 提前结清保存历史
					payback.getPaybackCharge().setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
					payback.setDictPayStatus(RepayStatus.PRE_SETTLE_VERIFY.getCode());
					applyPaybackUseService.beforeConfirmPayback(payback);
					redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/list";
				}else{
					// 提前还款和逾期还款
					payback.getPaybackCharge().setDictOffsetType(RepayType.PAYBACK_APPLY.getCode());
					msg = applyPaybackUseService.overdueOrNormalPayback(payback);
					if(StringUtils.equals(msg, YESNO.YES.getCode())){
						redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/list";
				   }else{
						model.addAttribute("message", msg);
						redirectPath= "borrow/payback/applypay/applyPaybackInfo";
					}
				}
			} catch (Exception e) {
				redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/list";
			}
		}else{
			redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/list";
		}
		return redirectPath;
	}
	
	
	/**
	 * 电销数据保存还款用途申请
	 * 2016年1月6日
	 * By zhangfeng
	 *  @param files
	 * @param payback
	 * @return redirect page
	 */
	@RequestMapping(value = "savePhoneSale")
	public String savePhoneSale(@RequestParam("files") MultipartFile[] files, Payback payback,Model model,RedirectAttributes redirectAttributes) {
        String msg = "";
        String redirectPath ="";
        
        //判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = payback.getFinishTokenId();
			String curToken = payback.getFinishToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			try {
				// 判断提前结清和提前还款
				if(StringUtils.equals(payback.getPaybackApply().getDictPayUse(), RepayType.EARLY_SETTLE.getCode())){
					// 提前结清保存主表
					payback.setDictPayStatus(RepayStatus.PRE_SETTLE_VERIFY.getCode());
					payback.preUpdate();
					applyPayService.updatePayback(payback);
					// 保存冲抵申请信息
					payback.getPaybackCharge().setChargeStatus(AgainstStatus.AGAINST_VERIFY.getCode());
					payback.getPaybackCharge().setContractCode(payback.getContractCode());
					payback.getPaybackCharge().setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
					payback.getPaybackCharge().setIsNewRecord(false);
					payback.getPaybackCharge().preInsert();
					applyPaybackUseService.insertOffset(files,payback.getPaybackCharge());
					// 提前结清保存历史
					payback.getPaybackCharge().setDictOffsetType(RepayType.EARLY_SETTLE.getCode());
					payback.setDictPayStatus(RepayStatus.PRE_SETTLE_VERIFY.getCode());
					applyPaybackUseService.beforeConfirmPayback(payback);
					redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/goPhoneSaleList";
				}else{
					// 提前还款和逾期还款
					payback.getPaybackCharge().setDictOffsetType(RepayType.PAYBACK_APPLY.getCode());
					msg = applyPaybackUseService.overdueOrNormalPayback(payback);
					if(StringUtils.equals(msg, YESNO.YES.getCode())){
						redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/goPhoneSaleList";
				   }else{
						model.addAttribute("message", msg);
						redirectPath= "borrow/payback/applypay/applyPaybackPhoneSaleInfo";
					}
				}
			} catch (Exception e) {
				redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/goPhoneSaleList";
			}
		}else{
			redirectPath = "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/goPhoneSaleList";
		}
		return redirectPath;
	}
	
	/**
	 * 返回页面
	 * 2015年12月9日
	 * By zhangfeng
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "returnPage")
	public String returnPage(Model model) {
		return "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/list";
	}
	
	/**
	 * 返回页面
	 * 2015年12月9日
	 * By zhangfeng
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "returnPhoneSalePage")
	public String returnPhoneSalePage(Model model) {
		return "redirect:" + adminPath +"/borrow/payback/applyPaybackUse/goPhoneSaleList";
	}
	

	/**
	 * 跳转电催还款用途申请 2016年1月6日 By zhaojunlei
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "goElectricApplyPaybackUseList")
	public String goElectricApplyPaybackUseList(HttpServletRequest request,
			HttpServletResponse response, Model model, Payback payback) {
		//------------------
		payback.setUrgeManage(Eletric.ELETRIC.getCode());
		payback.setEffectiveFlag(YESNO.YES.getCode());
		payback.setDictPayStatus("'" + RepayStatus.OVERDUE.getCode() + "','" + RepayStatus.PEND_REPAYMENT.getCode() + "','"+
		RepayStatus.SETTLE_CONFIRM.getCode() + "','" + RepayStatus.PRE_SETTLE_CONFIRM.getCode()+"','"+RepayStatus.PEND_REPAYMENT.getCode() + "','"+RepayStatus.PEND_REPAYMENT.getCode() + "','"+
		RepayStatus.SETTLE_FAILED.getCode() + "'");
		String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
		payback.setQueryRight(queryRight);
		Page<Payback> paybackPage = applyPayService.findElectricPayback(
				new Page<Payback>(request, response), payback);
		if(ArrayHelper.isNotEmpty(paybackPage.getList())){
			for (int i = 0; i < paybackPage.getList().size(); i++) {
				if (!ObjectHelper.isEmpty(paybackPage.getList().get(i).getLoanInfo()) && StringUtils.isNotEmpty(paybackPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())) {
					// 门店
					paybackPage.getList().get(i).getLoanInfo().setLoanStoreOrgName(
									String.valueOf(OrgCache.getInstance().get(paybackPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())));
		    	}
				List<Dict> dictList = DictCache.getInstance().getList();
				for(Dict dict:dictList){
					if("jk_open_bank".equals(dict.getType()) && !ObjectHelper.isEmpty(paybackPage.getList().get(i).getLoanBank()) && dict.getValue().equals(paybackPage.getList().get(i).getLoanBank().getBankName())){
						paybackPage.getList().get(i).getLoanBank().setBankNameLabel(dict.getLabel());
					}
					if("jk_repay_status".equals(dict.getType()) && dict.getValue().equals(paybackPage.getList().get(i).getDictPayStatus())){
						paybackPage.getList().get(i).setDictPayStatusLabel(dict.getLabel());
					}
					if("jk_channel_flag".equals(dict.getType())  && !ObjectHelper.isEmpty(paybackPage.getList().get(i).getLoanInfo()) && dict.getValue().equals(paybackPage.getList().get(i).getLoanInfo().getLoanFlag())){
						paybackPage.getList().get(i).getLoanInfo().setLoanFlagLabel(dict.getLabel());
					}
				}
	 		}
		}
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("paybackList", paybackPage);
		logger.debug("invoke ApplyPaybackUseController method: goApplyPaybackUseList, contarctCode is: "+ payback.getContractCode());
		return "borrow/payback/applypay/electricApplyPaybackUse";
	}
	
	/**
	 * 还款用途申请详细页面 2015年12月9日 By zhaojunlei
	 * 
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "goElectricApplyPaybackInfoForm")
	public String goElectricApplyPaybackInfoForm(Model model, Payback payback) {
		System.out.println("点击办理确定来到此处！");
		List<Payback> paybackList = new ArrayList<Payback>();
		paybackList = applyPayService.findPayback(payback);
		PaybackApply paybackApply = new PaybackApply();
		paybackApply.setDictPayUse(payback.getPaybackApply().getDictPayUse());
		paybackList.get(0).setPaybackApply(paybackApply);
		if (StringUtils.equals(payback.getPaybackApply().getDictPayUse(),
				RepayType.EARLY_SETTLE.getCode())) {
			applyPaybackUseService.findCurrentmonthAmount(paybackList.get(0));
		} else {
			applyPaybackUseService.findOverdueMonthAmount(paybackList.get(0));
		}
		model.addAttribute("payback", paybackList.get(0));
		logger.debug("invoke ApplyPaybackUseController method: goApplyPaybackInfoForm, contarctCode is: "
				+ payback.getContractCode());
		return "borrow/payback/applypay/electricApplyPaybackInfo";
	}
	
	/**
	 * 保存还款用途申请 2016年1月6日 By zhaojunlei
	 * 
	 * @param model
	 * @param payback
	 * @param redirectAttributes
	 * @return redirect page
	 */
	@RequestMapping(value = "saveElectricApplyPaybackUse")
	public String saveElectricApplyPaybackUse(
			@RequestParam("files") MultipartFile[] files, Model model,
			Payback payback, RedirectAttributes redirectAttributes) {
		if (StringUtils.equals(payback.getPaybackApply().getDictPayUse(),
				RepayType.EARLY_SETTLE.getCode())) {
			// 提前结清
			payback.getPaybackCharge().setDictOffsetType(
					RepayType.EARLY_SETTLE.getCode());
			payback.setDictPayStatus(RepayStatus.PRE_SETTLE_VERIFY.getCode());
			applyPaybackUseService.beforeConfirmPayback(payback);
			payback.setDictPayStatus(RepayStatus.PRE_SETTLE.getCode());
			payback.preUpdate();
			applyPayService.updatePayback(payback);
		} else {
			// 提前还款
			payback.getPaybackCharge().setDictOffsetType(
					RepayType.PREPAYMENT.getCode());
			payback.setDictPayStatus(RepayStatus.NORMAL_PAYMENT.getCode());
			//applyPaybackUseService.beforePayback(payback);
		}
		payback.getPaybackCharge().setChargeStatus(
				AgainstStatus.AGAINST_VERIFY.getCode());
		payback.getPaybackCharge().setContractCode(payback.getContractCode());
		payback.getPaybackCharge().setIsNewRecord(false);
		payback.getPaybackCharge().preInsert();
		// 保存冲抵申请信息
		applyPaybackUseService.insertOffset(files, payback.getPaybackCharge());
		return "redirect:" + adminPath + "/borrow/payback/applyPaybackUse/goElectricApplyPaybackUseList";
	}
	
	/**
	 * 导出電催后台数据列表
	 * 2016年2月29日
	 * By liushikang
	 * @param request
	 * @param response
	 * @param checkVal
	 * @return none
	 */
	@RequestMapping(value="exportElectricList")
	public void exportElectricList(HttpServletRequest request,
			HttpServletResponse response, String checkVal) {
		ExcelUtils excelutil = new ExcelUtils();
		
//		List<Payback> eletricPageList = paybackPage.getList();
		List<Payback> eletricPageList = Lists.newArrayList();
		
		List<EletricPage> eletricPageDataList = new ArrayList<EletricPage>();
		String[] ids = null;
		try {
			if (StringUtils.isEmpty(checkVal)) {
				for (Payback eletric : eletricPageList) {
					EletricPage eletricPage = new EletricPage();
					// 合同编号
					eletricPage.setContractCode(eletric.getContractCode());
					// 还款日
					if (eletric.getPaybackMonth() != null) {
						eletricPage.setPaybackDay(eletric.getPaybackMonth()
								.getMonthPayDay());
					}
					// 还款状态
					eletricPage.setDictPayStatus(eletric.getDictPayStatus());
					// 蓝补金额
					eletricPage.setPaybackBuleAmount(eletric.getPaybackBuleAmount());
					// 月还期供金额
					eletricPage.setPaybackMonthAmount(eletric.getPaybackMonthAmount());
					if (eletric.getLoanCustomer() != null) {
						if (eletric.getLoanCustomer().getCustomerName() != null) {
							// 客户姓名
							eletricPage.setCustomerName(eletric.getLoanCustomer().getCustomerName());
						}else {
							eletricPage.setCustomerName("");
						}
					}else {
						eletricPage.setCustomerName("");
					}
					
					if (eletric.getLoanBank() != null) {
						if (eletric.getLoanBank().getBankName() != null) {
							// 开户行名称
							eletricPage.setBankName(eletric.getLoanBank().getBankName());
						}else {
							eletricPage.setBankName("");
						}
					}else {
						eletricPage.setBankName("");
					}
					if (eletric.getContract() != null) {
						if (eletric.getContract().getContractEndDay() != null) {
							// 合同到期日
							eletricPage.setContractEndDay(eletric.getContract().getContractEndDay());
						}
						if (eletric.getContract().getContractMonths() != null) {
							// 批复期限
							eletricPage.setContractMonths(eletric.getContract().getContractMonths());
						}
					}
					if (eletric.getPaybackMonth() != null) {
						if (eletric.getPaybackMonth().getDictMonthStatus() != null) {
							// 期供状态
							eletricPage.setDictMonthStatus(eletric.getPaybackMonth().getDictMonthStatus());
						}else {
							eletricPage.setDictMonthStatus("");
						}
					}else {
						eletricPage.setDictMonthStatus("");
					}
					if (eletric.getPaybackMonth() != null) {
						if (eletric.getPaybackMonth().getMonthsAomuntPaybacked() != null) {
							// 
							eletricPage.setMonthsAomuntPaybacked(eletric.getPaybackMonth().getMonthsAomuntPaybacked());
						}else {
							eletricPage.setMonthsAomuntPaybacked(new BigDecimal(0));
						}
					}else {
						eletricPage.setMonthsAomuntPaybacked(new BigDecimal(0));
					}
					if (eletric.getLoanInfo() != null) {
						if (eletric.getLoanInfo().getLoanFlag() != null) {
							// 
							eletricPage.setLoanFlag(eletric.getLoanInfo().getLoanFlag());
						}else {
							eletricPage.setLoanFlag("");
						}
						if (eletric.getLoanInfo().getLoanTeamOrgId() != null) {
							// 
							eletricPage.setLoanTeamOrgId(eletric.getLoanInfo().getLoanTeamOrgId());
						}else {
							eletricPage.setLoanTeamOrgId("");
						}
						if (eletric.getLoanInfo().getDictLoanStatus() != null) {
							// 
							eletricPage.setDictLoanStatus(eletric.getLoanInfo().getDictLoanStatus());
						}else {
							eletricPage.setDictLoanStatus("");
						}
					}else {
						eletricPage.setLoanFlag("");
						eletricPage.setLoanTeamOrgId("");
						eletricPage.setDictLoanStatus("");
					}
					eletricPageDataList.add(eletricPage);
				}
			}else {
				ids = checkVal.split(",");
				for (int i = 0; i < ids.length; i++) {
					for (int j = 0; j < eletricPageList.size(); j++) {
						if (ids[i].equals(eletricPageList.get(j).getContractCode())) {
							for (Payback eletric : eletricPageList) {
								EletricPage eletricPage = new EletricPage();
								eletricPage.setContractCode(eletric.getContractCode());
								if (eletric.getPaybackMonth() != null) {
									eletricPage
											.setPaybackDay(eletric
													.getPaybackMonth()
													.getMonthPayDay());
								}
								eletricPage.setPaybackMonthAmount(eletric.getPaybackMonthAmount());
								if (eletric.getLoanCustomer() != null) {
									if (eletric.getLoanCustomer().getCustomerName() != null) {
										eletricPage.setCustomerName(eletric.getLoanCustomer().getCustomerName());
									}else {
										eletricPage.setCustomerName("");
									}
								}else {
									eletricPage.setCustomerName("");
								}
								
								if (eletric.getLoanBank() != null) {
									if (eletric.getLoanBank().getBankName() != null) {
										eletricPage.setBankName(eletric.getLoanBank().getBankName());
									}else {
										eletricPage.setBankName("");
									}
								}else {
									eletricPage.setBankName("");
								}
								if (eletric.getContract() != null) {
									if (eletric.getContract().getContractEndDay() != null) {
										eletricPage.setContractEndDay(eletric.getContract().getContractEndDay());
									}
									if (eletric.getContract().getContractMonths() != null) {
										eletricPage.setContractMonths(eletric.getContract().getContractMonths());
									}
								}
								if (eletric.getPaybackMonth() != null) {
									if (eletric.getPaybackMonth().getDictMonthStatus() != null) {
										eletricPage.setDictMonthStatus(eletric.getPaybackMonth().getDictMonthStatus());
									}else {
										eletricPage.setDictMonthStatus("");
									}
								}else {
									eletricPage.setDictMonthStatus("");
								}
								eletricPage.setDictPayStatus(eletric.getDictPayStatus());
								eletricPage.setPaybackBuleAmount(eletric.getPaybackBuleAmount());
								if (eletric.getPaybackMonth() != null) {
									if (eletric.getPaybackMonth().getMonthsAomuntPaybacked() != null) {
										eletricPage.setMonthsAomuntPaybacked(eletric.getPaybackMonth().getMonthsAomuntPaybacked());
									}else {
										eletricPage.setMonthsAomuntPaybacked(new BigDecimal(0));
									}
								}else {
									eletricPage.setMonthsAomuntPaybacked(new BigDecimal(0));
								}
								if (eletric.getLoanInfo() != null) {
									if (eletric.getLoanInfo().getLoanFlag() != null) {
										eletricPage.setLoanFlag(eletric.getLoanInfo().getLoanFlag());
									}else {
										eletricPage.setLoanFlag("");
									}
									if (eletric.getLoanInfo().getLoanTeamOrgId() != null) {
										eletricPage.setLoanTeamOrgId(eletric.getLoanInfo().getLoanTeamOrgId());
									}else {
										eletricPage.setLoanTeamOrgId("");
									}
									if (eletric.getLoanInfo().getDictLoanStatus() != null) {
										eletricPage.setDictLoanStatus(eletric.getLoanInfo().getDictLoanStatus());
									}else {
										eletricPage.setDictLoanStatus("");
									}
								}else {
									eletricPage.setLoanFlag("");
									eletricPage.setLoanTeamOrgId("");
									eletricPage.setDictLoanStatus("");
								}
								eletricPageDataList.add(eletricPage);
							}
						}
					}
					
				}
			}
			excelutil.exportExcel(eletricPageDataList,FileExtension.ELETRIC_NAME, EletricPage.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_DATA, response, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 资料包下载
	 * 2016年6月15日
	 * By 王彬彬
	 * @param fileType
	 * @return
	 */
	@RequestMapping(value = "/fileDownLoad")
	public String fileDownLoad(String fileType, HttpServletResponse response) {
		 List<DocumentBean> lst = new ArrayList<DocumentBean>();
		 String fileName = "";
		 Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		try {
			// 提前结清模板
			if (DownLoadFileType.SETTLED.equals(fileType)) {
				lst = CeUtils.downFileBySubType("BusinessFile",
						FileType.SETTLED.getCode(), null, response);
				fileName = FileType.SETTLED.getName();
			}
			
			try {
				fileMap.put(lst.get(0).getDocTitle(), lst.get(0).getInputStream());
				
				response.setContentType("application/zip");
				response.addHeader("Content-disposition", "attachment;filename="
						+ new String((fileName + ".zip").getBytes("gbk"),
								"ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			if (!ObjectHelper.isEmpty(fileMap)) {
				try {
					Zip.zipFiles(response.getOutputStream(), fileMap);
				} catch (IOException e) {
					logger.error("文件下载失败");
					e.printStackTrace();
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 检索电销还款冲抵列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "goPhoneSaleList")
	public String goPhoneSaleList(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Model model, 
		Payback params
	) {
		// 是否电销标识
		params.setPhoneSaleSign("1");
		params.setEffectiveFlag(YESNO.YES.getCode());
		params.setDictPayStatus(
			"'" + 
			RepayStatus.OVERDUE.getCode() + "','" + 
			RepayStatus.PEND_REPAYMENT.getCode() + "','" + 
			RepayStatus.PRE_SETTLE_VERIFY.getCode() + "','" + 
			RepayStatus.SETTLE_CONFIRM.getCode() + "','" + 
			RepayStatus.PRE_SETTLE_CONFIRM.getCode() + "','" + 
			RepayStatus.SETTLE.getCode() + "','" + 
			RepayStatus.PRE_SETTLE.getCode() + "','" +
			RepayStatus.SETTLE_FAILED.getCode() + 
			"'"
		);
		
		// 执行数据库查询语句，带条件
		Page<Payback> page = new Page<Payback>(request, response);
		params.setLimit(page.getPageSize());
		if (page.getPageNo() <= 1) {
			params.setOffset(0);
		} else {
			params.setOffset((page.getPageNo() - 1) * page.getPageSize());
		}
		
		SqlSessionFactory ssf = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		SqlSession ss = ssf.openSession();
		Connection cn = ss.getConnection();
		try {
			MyBatisSql sql = MyBatisSqlUtil.getMyBatisSql(
				"com.creditharmony.loan.common.dao.PaybackDao.selApplyPaybackUseCnt",
				params,
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
					"com.creditharmony.loan.common.dao.PaybackDao.selApplyPaybackUse",
					params,
					ssf
				);
				ps = cn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				List<Payback> ls = new ArrayList<Payback>();
				while (rs.next()) {
					Payback rec = new Payback();
					rec.setContractCode(rs.getString("contract_code"));						// 合同编号
					rec.setDictPayStatus(rs.getString("dict_pay_status"));					// 还款状态
//					rec.setDictPayStatusLabel(rs.getString("dictPayStatusLabel")); 			// 还款状态Label
					rec.setPaybackDay(rs.getInt("payback_day"));							// 还款日
					rec.setPaybackBuleAmount(rs.getBigDecimal("payback_bule_amount"));		// 蓝补金额
					rec.setPaybackMonthAmount(rs.getBigDecimal("payback_month_amount"));	// 期供
					rec.setPaybackBackAmount(rs.getBigDecimal("payback_back_amount"));		// 返款金额

					LoanCustomer lc = new LoanCustomer();
					lc.setCustomerName(rs.getString("customer_name"));						// 客户姓名
					rec.setLoanCustomer(lc);
					
					Contract c = new Contract();
					c.setContractMonths(rs.getBigDecimal("contract_months"));				// 批借期限
					c.setContractReplayDay(rs.getDate("contract_replay_day"));				// 起始还款日期
					c.setContractEndDay(rs.getDate("contract_end_day"));					// 合同到期日期
					rec.setContract(c);
					
					LoanInfo li = new LoanInfo();
					li.setDictLoanStatus(rs.getString("dict_loan_status"));					// 借款状态
//					li.setDictLoanStatusLabel(rs.getString("dictLoanStatusLabel")); 		// 借款状态Label
//					li.setLoanStoreOrgId(rs.getString("loan_store_orgid"));					// 组织机构ID
					li.setLoanStoreOrgName(rs.getString("loanStoreOrgName"));				// 门店
					li.setLoanFlag(rs.getString("loan_flag"));								// 渠道
//					li.setLoanFlagLabel(rs.getString("loanFlagLabel")); 					// 渠道Label
					li.setModel(rs.getString("model")); 									// 模型
//					li.setModelLabel(rs.getString("modelLabel")); 							// 模型Label
					rec.setLoanInfo(li);
					
					LoanBank lb = new LoanBank();
					lb.setBankName(rs.getString("bank_name"));								// 开户行
//					lb.setBankNameLabel(rs.getString("bankNameLabel"));						// 开户行
					rec.setLoanBank(lb);
					
//					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
//						StringUtils.isNotEmpty(rec.getLoanInfo().getLoanStoreOrgId())) {
//						rec.getLoanInfo().setLoanStoreOrgName(
//							String.valueOf(OrgCache.getInstance().get(rec.getLoanInfo().getLoanStoreOrgId())));
//			    	}
					
					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
						StringUtils.isNotEmpty(rec.getLoanInfo().getLoanFlag())) {
						String channelFlagLabel = DictCache.getInstance().getDictLabel(
							"jk_channel_flag", rec.getLoanInfo().getLoanFlag());
						rec.getLoanInfo().setLoanFlagLabel(channelFlagLabel);
			    	}
					
					if(StringUtils.isNotEmpty(rec.getDictPayStatus())){
						String repayStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_repay_status", rec.getDictPayStatus());
						rec.setDictPayStatusLabel(repayStatusLabel);
					}
					
					if (!ObjectHelper.isEmpty(rec.getLoanBank()) && 
						StringUtils.isNotEmpty(rec.getLoanBank().getBankName())) {
						String dictOpenBankLabel = DictCache.getInstance().getDictLabel(
							"jk_open_bank", rec.getLoanBank().getBankName());
						rec.getLoanBank().setBankNameLabel(dictOpenBankLabel);
			    	}
					
					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
						StringUtils.isNotEmpty(rec.getLoanInfo().getDictLoanStatus())) {
						String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_loan_apply_status", rec.getLoanInfo().getDictLoanStatus());
						rec.getLoanInfo().setDictLoanStatusLabel(dictLoanStatusLabel);
			    	}
					
					if (!ObjectHelper.isEmpty(rec.getLoanInfo()) && 
						StringUtils.isNotEmpty(rec.getLoanInfo().getModel())) {
						String dictLoanModel = DictCache.getInstance().getDictLabel(
							"jk_loan_model", rec.getLoanInfo().getModel());
						rec.getLoanInfo().setModelLabel(dictLoanModel);
			    	}

					ls.add(rec);
				}
				
				page.setCount(cnt);
				page.setList(ls);
			} else {
				page.setCount(0);
				page.setList(null);
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
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		int day = WorkDayCheck.getConfirmStartTime(Calendar.getInstance(),1);
		
		Date d = new Date();
		@SuppressWarnings("deprecation")
		int hour= d.getHours();
		String isValidate = YESNO.NO.getCode();
		if (hour >= 16) {
			isValidate = YESNO.YES.getCode();
		}
		
		model.addAttribute("checkDay", String.valueOf(day));
		model.addAttribute("isfour", isValidate);
		model.addAttribute("dayList", dayList);
		model.addAttribute("paybackList", page);
		return "borrow/payback/applypay/applyPaybackUsePhoneSale";
	}
	
}
