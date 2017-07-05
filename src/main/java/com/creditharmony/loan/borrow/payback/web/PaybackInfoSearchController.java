package com.creditharmony.loan.borrow.payback.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.AgainstStatus;
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
import com.creditharmony.loan.borrow.account.service.RepayAccountService;
import com.creditharmony.loan.borrow.account.view.RepayAccountApplyView;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackUseService;
import com.creditharmony.loan.borrow.payback.service.OverdueManageService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.utils.CeUtils;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.utils.WorkDayCheck;

/**
 * 还款管理-还款信息查询列表
 * @Class Name PaybackInfoSearchController
 * @author 朱静越
 * @Create In 2017年4月10日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/paybackInfoSearch")
public class PaybackInfoSearchController extends BaseController {

	@Autowired
	private ApplyPaybackUseService applyPaybackUseService;
	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;
	@Autowired
	private OverdueManageService overdueManageService;
	@Autowired
	private RepayAccountService repayAccountService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private RepaymentDateService dateService;
	
	private static String BLACK_NULL = "2";
	
	private static String NULL_EMERGENCY = "3";
	
	/**
	 * 显示还款信息查询列表信息
	 * 2017年4月10日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param model
	 * @param params
	 * @return
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
			RepayStatus.SETTLE_CONFIRM.getCode() + "','" + 
			RepayStatus.SETTLE.getCode() + "','" + 
			RepayStatus.PRE_SETTLE.getCode() +
			"'"
		);
		String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
		params.setQueryRight(queryRight);
		
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
				"com.creditharmony.loan.common.dao.PaybackInfoSearchDao.selApplyPaybackUseCnt",
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
					"com.creditharmony.loan.common.dao.PaybackInfoSearchDao.selApplyPaybackUse",
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
					c.setLoanCode(rs.getString("loan_code"));	                            // 借款编号
					rec.setContract(c);
					
					LoanInfo li = new LoanInfo();
					li.setDictLoanStatus(rs.getString("dict_loan_status"));					// 借款状态
					li.setLoanStoreOrgName(rs.getString("loanStoreOrgName"));				// 门店
					li.setLoanFlag(rs.getString("loan_flag"));								// 渠道
					li.setModel(rs.getString("model")); 									// 模型
					li.setEmergencyStatus(rs.getString("emergency_status"));               // 紧急诉讼状态
					li.setYesOrNoEmergency(rs.getString("yesorno_emergency"));	           // 是否紧急诉讼
					if (StringHelper.isEmpty(li.getYesOrNoEmergency())) {
						li.setYesOrNoEmergency(YESNO.NO.getCode());
					}
					li.setBlackType(rs.getString("black_type"));	                       // 黑灰名单
					if (StringHelper.isEmpty(li.getBlackType())) {
						li.setBlackType(BLACK_NULL);
					}
					rec.setLoanInfo(li);
					
					LoanBank lb = new LoanBank();
					lb.setBankName(rs.getString("bank_name"));								// 开户行
					rec.setLoanBank(lb);
					
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
					
					if (ObjectHelper.isNotEmpty(rec.getLoanInfo())&&
							StringHelper.isNotEmpty(rec.getLoanInfo().getEmergencyStatus())) {
						String emeygencyStatusLabel = DictCache.getInstance().getDictLabel(
								"jk_emergency_status", rec.getLoanInfo().getEmergencyStatus());
						rec.getLoanInfo().setEmergencyStatusLabel(emeygencyStatusLabel);
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
			logger.error("还款查询信息列表初始化发生异常，"+e);
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("还款查询信息列表初始化发生异常，"+e);
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
		return "borrow/payback/applypay/paybackInfoSearch";
	}
	
	/**
	 * 修改紧急诉讼状态,更新借款主表中的紧急诉讼状态
	 * 2017年4月12日
	 * By 朱静越
	 * @param model
	 * @param payback 参数
	 * @param emergencyRemark 紧急诉讼状态备注
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateEmergency")
	public String updateEmergency(Payback payback,String emergencyRemark) {
		String messageString = null;
		if (ObjectHelper.isNotEmpty(payback) 
				&& ObjectHelper.isNotEmpty(payback.getContract())
						&& ObjectHelper.isNotEmpty(payback.getContract().getLoanCode())) {
			try {
				LoanInfo loanInfo = new LoanInfo();
				logger.info("修改紧急诉讼状态的单子为：" + payback.getContract().getLoanCode());
				loanInfo.setLoanCode(payback.getContract().getLoanCode());
				loanInfo.setEmergencyStatus(payback.getLoanInfo().getEmergencyStatus());
				if (NULL_EMERGENCY.equals(payback.getLoanInfo().getEmergencyStatus())) {
					loanInfo.setYesOrNoEmergency(YESNO.NO.getCode());
				}else {
					loanInfo.setYesOrNoEmergency(YESNO.YES.getCode());
				}
				applyLoanInfoService.updateLoanInfo(loanInfo);
				logger.info("修改紧急诉讼状态成功");
				messageString = "修改成功";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改紧急诉讼状态出现异常",e);
				messageString = "修改紧急诉讼状态异常";
			}
		}else {
			messageString = "修改紧急诉讼状态失败";
		}
		return messageString;
	}
	
	/**
	 * 修改黑灰标记
	 * 2017年4月13日
	 * By 朱静越
	 * @param payback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBlackType")
	public String updateBlackType(Payback payback){
		String returnMes = null;
		if (ObjectHelper.isNotEmpty(payback) 
				&& ObjectHelper.isNotEmpty(payback.getContract())
						&& ObjectHelper.isNotEmpty(payback.getContract().getLoanCode())) {
			try {
				LoanInfo loanInfo = new LoanInfo();
				logger.info("修改黑灰标记的单子为：" + payback.getContract().getLoanCode());
				loanInfo.setLoanCode(payback.getContract().getLoanCode());
				loanInfo.setBlackType(payback.getLoanInfo().getBlackType());
				applyLoanInfoService.updateLoanInfo(loanInfo);
				logger.info("修改黑灰标记成功");
				returnMes = "修改成功";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改黑灰标记出现异常",e);
				returnMes = "修改黑灰标记异常";
			}
		}else {
			returnMes = "修改黑灰标记失败";
		}
		return returnMes;
	}
	
	/**
	 * 还款信息维护历史
	 * 2017年4月14日
	 * By 朱静越
	 * @param model
	 * @param contractCode 合同编号
	 * @return
	 */
	@RequestMapping(value="getPayBackMainHis")
	public String getPayBackMainHis(Model model,String contractCode){
		RepayAccountApplyView repayAccountApplyView = new RepayAccountApplyView();
		repayAccountApplyView.setContractCode(contractCode);
		List<RepayAccountApplyView> list = repayAccountService.getHistoryList(repayAccountApplyView);
		model.addAttribute("list", list);
		return "borrow/repayaccount/showMaintainHistory";
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
}
