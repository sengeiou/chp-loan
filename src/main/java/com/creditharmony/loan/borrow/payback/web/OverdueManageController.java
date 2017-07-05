package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.ex.DeductedConstantEx;
import com.creditharmony.loan.borrow.payback.entity.ex.OverdueManageEx;
import com.creditharmony.loan.borrow.payback.service.OverdueManageService;

/**
 * 逾期管理
 * @Class Name OverdueMangeController
 * @author 李强
 * @Create In 2015年12月14日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/overduemanage")
public class OverdueManageController extends BaseController {
	
	public final static String MONTH_ZERO = "0.00";
	
	BigDecimal bgSum = new BigDecimal("0.00");
	
	@Autowired
	private OverdueManageService overdueManageService;
	
	/*@Autowired
	private HistoryService historyService;*/
	
	/**
	 * 查询逾期管理列表
	 * 2015年12月14日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param overdueManageEx
	 * @return overdueManage.jsp
	 */
	@RequestMapping(value = "allOverdueManageList")
	public String allOverdueManageList(
		HttpServletRequest request,
		HttpServletResponse response,
		Model model,
		OverdueManageEx overdueManageEx
	) {
		overdueManageEx.setEnumOne(PeriodStatus.OVERDUE.getCode());
		overdueManageEx.setEnumTwo(PeriodStatus.REPLEVY.getCode());
		overdueManageEx.setId("");
		String orgId = overdueManageEx.getOrgIdyc();
		String bankId = overdueManageEx.getBankyc();
		if (orgId!= null && !"".equals(orgId)) {
			overdueManageEx.setOrgId(appendString(orgId));
		}
		if (bankId!= null && !"".equals(bankId)) {
			overdueManageEx.setBankId(appendString(bankId));
		}
		Date beginDate = overdueManageEx.getBeginDate();
		Date endDate = overdueManageEx.getEndDate();
		if (!ObjectHelper.isEmpty(beginDate)) {
			if (ObjectHelper.isEmpty(endDate)) {
				overdueManageEx.setEndDate(new Date());
			} else {
                String endDateStr = DateUtils.formatDate(overdueManageEx.getEndDate(), "yyyy-MM-dd");
                endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
                overdueManageEx.setEndDate(endDate);
			}
		} else {
			if (!ObjectHelper.isEmpty(endDate)) {
				String endDateStr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
				endDate = DateUtils.convertStringToDate(endDateStr+" 23:59:59");
				overdueManageEx.setEndDate(endDate);
			}
		}
		
		Page<OverdueManageEx> page = new Page<OverdueManageEx>(request, response);
		overdueManageEx.setLimit(page.getPageSize());
		if (page.getPageNo() <= 1) {
			overdueManageEx.setOffset(0);
		} else {
			overdueManageEx.setOffset((page.getPageNo() - 1) * page.getPageSize());
		}
		
		// 执行数据库查询语句，带条件
		SqlSessionFactory ssf = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		SqlSession ss = ssf.openSession();
		Connection cn = ss.getConnection();
		try {
			MyBatisSql sql = MyBatisSqlUtil.getMyBatisSql(
					"com.creditharmony.loan.borrow.payback.dao.OverdueManageDao.cnt",
					overdueManageEx,
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
						"com.creditharmony.loan.borrow.payback.dao.OverdueManageDao.allOverdueManageList",
						overdueManageEx,
						ssf
					);
				ps = cn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				List<OverdueManageEx> ls = new ArrayList<OverdueManageEx>();
				while (rs.next()) {
					OverdueManageEx rec = new OverdueManageEx();
					rec.setId(rs.getString("id"));
					rec.setOrgName(rs.getString("orgName"));										// 门店名称
					rec.setContractCode(rs.getString("contractCode"));								// 合同编号
					rec.setCustomerName(rs.getString("customerName"));								// 客户姓名
					rec.setBankName(rs.getString("bankName"));										// 银行
					rec.setBankNameLabel(rs.getString("bankNameLabel"));
					rec.setMonthPayDay(rs.getDate("monthPayDay"));									// 逾期日期
					rec.setContractMonthRepayAmount(rs.getBigDecimal("contractMonthRepayAmount"));	// 期供金额
					rec.setMonthOverdueDays(rs.getInt("monthOverdueDays"));							// 逾期天数
					
					rec.setPaybackBuleAmount(rs.getBigDecimal("paybackBuleAmount"));				// 蓝补金额	
					rec.setDictLoanStatus(rs.getString("dictLoanStatus"));							// 借款状态
					rec.setDictLoanStatusLabel(rs.getString("dictLoanStatusLabel"));		
					rec.setDictMonthStatus(rs.getString("dictMonthStatus"));						// 期供状态
					rec.setDictMonthStatusLabel(rs.getString("dictMonthStatusLabel"));
					rec.setReductionBy(rs.getString("reductionBy"));								// 减免人
					rec.setMonthReductionDay(rs.getInt("monthReductionDay"));						// 减免天数
					rec.setCustomerTelesalesFlag(rs.getString("customerTelesalesFlag"));			// 是否电销
					rec.setCustomerTelesalesFlagLabel(rs.getString("customerTelesalesFlagLabel"));
					rec.setLoanMark(rs.getString("loanMark"));										// 渠道
					rec.setLoanMarkLabel(rs.getString("loanMarkLabel"));
					rec.setModel(rs.getString("model"));											// 模式
					rec.setModelLabel(rs.getString("modelLabel"));
					rec.setrPaybackId(rs.getString("rPaybackId"));
					rec.setContractVersion(rs.getString("contractVersion"));
					String contractVersion = rec.getContractVersion();
					logger.info("合同比较版本号：" + ContractVer.VER_ONE_FOUR.getCode());
					if(StringUtils.isNotEmpty(contractVersion) 
						&& Integer.valueOf(contractVersion) >= 
						Integer.valueOf(ContractVer.VER_ONE_FOUR.getCode())) {
						// 1.4版本合同
						// 新违约金(滞纳金)及罚息总额(应还滞纳金 + 应还罚息) 
						rec.setPenaltyAndShould(
							rs.getBigDecimal("monthLateFee")
							.add(rs.getBigDecimal("monthInterestPunishshould"))
						);
						// 实还期供金额=实还分期服务费+实还本金 + 实还利息
						BigDecimal alsocontractMonthRepay = 
							rs.getBigDecimal("actualMonthFeeService")
							.add(rs.getBigDecimal("monthCapitalPayactual"))
							.add(rs.getBigDecimal("monthInterestPayactual"));
						rec.setAlsocontractMonthRepay(alsocontractMonthRepay); 
						// 逾期期供金额(期供金额-实还期供金额)
						BigDecimal contractMonthRepayAmountLate = 
							rs.getBigDecimal("contractMonthRepayAmount")
							.subtract(alsocontractMonthRepay);
						if (contractMonthRepayAmountLate.compareTo(bgSum) < 0) {
							contractMonthRepayAmountLate = bgSum;
						}
						rec.setContractMonthRepayAmountLate(contractMonthRepayAmountLate);
						// 实还滞纳金罚息金额=实还滞纳金 + 实还罚息
						rec.setAlsoPenaltyInterest(
							rs.getBigDecimal("actualMonthLateFee")
							.add(rs.getBigDecimal("monthInterestPunishactual"))
						);
						// 新减免违约金(滞纳金)罚息(减免滞纳金+减免罚息)
						rec.setReductionAmount(
							rs.getBigDecimal("monthLateFeeReduction")
							.add(rs.getBigDecimal("monthPunishReduction"))
						);
						// 新未还违约金(滞纳金)及罚息金额(应还滞纳金-实还滞纳金 + 应还罚息-实还罚息 - 减免违约金(滞纳金)罚息) 
						BigDecimal noPenaltyInterest = 
							rs.getBigDecimal("monthLateFee")
							.subtract(rs.getBigDecimal("actualMonthLateFee"))
							.add(rs.getBigDecimal("monthInterestPunishshould"))
							.subtract(rs.getBigDecimal("monthInterestPunishactual"))
							.subtract(rs.getBigDecimal("monthLateFeeReduction"))
							.subtract(rs.getBigDecimal("monthPunishReduction"));
						if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
							noPenaltyInterest.compareTo(bgSum) <= Integer.parseInt(YESNO.NO.getCode())
						) {
							rec.setNoPenaltyInterest(bgSum);
						}
						if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
							noPenaltyInterest.compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())
						) {
							rec.setNoPenaltyInterest(noPenaltyInterest);
						}
					} else {
						// 1.3及之前版本合同
						// 违约金及罚息总额=应还违约金 + 应还罚息
						rec.setPenaltyAndShould(
							rs.getBigDecimal("monthPenaltyShould")
							.add(rs.getBigDecimal("monthInterestPunishshould"))
						);
						// 实还期供金额=实还本金+实还利息
						BigDecimal alsocontractMonthRepay = 
							rs.getBigDecimal("monthCapitalPayactual")
							.add(rs.getBigDecimal("monthInterestPayactual"));
						rec.setAlsocontractMonthRepay(alsocontractMonthRepay);
						// 逾期期供金额(期供金额-实还期供金额)
						BigDecimal contractMonthRepayAmountLate = 
							rs.getBigDecimal("contractMonthRepayAmount")
							.subtract(alsocontractMonthRepay);
						if(contractMonthRepayAmountLate.compareTo(bgSum) < 0){
							contractMonthRepayAmountLate = bgSum;
						}
						rec.setContractMonthRepayAmountLate(contractMonthRepayAmountLate);
						// 实还违约金罚息金额=实还违约金 + 实还罚息
						rec.setAlsoPenaltyInterest(
							rs.getBigDecimal("monthPenaltyActual")
							.add(rs.getBigDecimal("monthInterestPunishactual"))
						);
						// 减免金额=减免违约金 + 减免罚息
						rec.setReductionAmount(
							rs.getBigDecimal("monthPenaltyReduction")
							.add(rs.getBigDecimal("monthPunishReduction"))
						);
						// 未还违约金及罚息
						BigDecimal noPenaltyInterest = 
							rec.getPenaltyAndShould().
							subtract(rec.getAlsoPenaltyInterest()).
							subtract(rec.getReductionAmount());
						if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
							noPenaltyInterest.compareTo(bgSum) <= Integer.parseInt(YESNO.NO.getCode())
						) {
							rec.setNoPenaltyInterest(bgSum);
						}
						if (!ObjectHelper.isEmpty(noPenaltyInterest) && 
							noPenaltyInterest.compareTo(bgSum) > Integer.parseInt(YESNO.NO.getCode())
						) {
							rec.setNoPenaltyInterest(noPenaltyInterest);
						}
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

//		Page<OverdueManageEx> page = new Page<OverdueManageEx>(request, response);
//		page = overdueManageService.allOverdueManageList(page, overdueManageEx);

		model.addAttribute("waitPage", page);
		
		model.addAttribute("OverdueManageEx",overdueManageEx);
		
		logger.debug("invoke OverdueMangeController method: allOverdueManageList, consult.id is: " + page);
		return "borrow/payback/repayment/overdueManage";
	}
	
	/**
	 * 逾期管理页面的导出
	 * 2015年12月14日
	 * By 李强
	 * @param request
	 * @param response
	 * @param model
	 * @param overdueManageEx
	 * @return overdueManage.jsp
	 */
	@RequestMapping(value = "excelList")
	public String excelList(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes,OverdueManageEx overdueManageEx){
		overdueManageEx.setEnumOne(PeriodStatus.OVERDUE.getCode());
		overdueManageEx.setEnumTwo(PeriodStatus.REPLEVY.getCode());
		String orgId = overdueManageEx.getOrgIdyc();
		if(orgId!= null && !"".equals(orgId)){
			overdueManageEx.setOrgId(appendString(orgId));
		}
		String idVal = overdueManageEx.getId();
		if(idVal!= null && !"".equals(idVal)){
			overdueManageEx.setId(appendString(idVal));
		}
		Page<OverdueManageEx> overdueList = overdueManageService.allOverdueManageList(new Page<OverdueManageEx>(request, response), overdueManageEx);
		if(overdueList.getCount() <= 200000){
			ExportOverDueHelper.exportData(overdueManageEx, response);
			return null;
		}else{
			addMessage(redirectAttributes, "导出数据最大数为200000，请选择检索条件重新检索！");
			return "redirect:" + adminPath + "/borrow/payback/overduemanage/allOverdueManageList";
		}
	}
	
	/**
	 * 查询进行减免操作页面信息
	 * 2015年12月14日
	 * By 李强
	 * @param model
	 * @param id
	 * @return json数据(减免天数页面数据)
	 */
	@ResponseBody
	@RequestMapping(value = "queryOverdueManage")
	public String queryOverdueManage(Model model,@RequestParam(value="id",required=false)String id){
		OverdueManageEx overdueManage = overdueManageService.queryOverdueManage(id);
		// 设置减免操作页面的减免人为当前登录用户
		overdueManage.setReductionBy(UserUtils.getUser().getName());
		logger.debug("invoke OverdueMangeController method: queryOverdueManage, consult.id is: "+ overdueManage);
		return JsonMapper.nonDefaultMapper().toJson(overdueManage);
	}
	
	/**
	 * 修改逾期天数
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 * @return 修改逾期天数结果 0：成功   1：失败  
	 */
	@ResponseBody
	@RequestMapping(value = "updateOverdueDay")
	public String updateOverdueDay(OverdueManageEx overdueManageEx,@RequestParam(value="reductionDay",required=false) int reductionDay){
		PaybackOpe paybackOpe = new PaybackOpe();
		overdueManageEx.preUpdate();
		paybackOpe.setIsNewRecord(false);
		paybackOpe.preInsert();
		paybackOpe.setOperator(UserUtils.getUser().getId()); // 鎿嶄綔浜�
		paybackOpe.setrPaybackId(overdueManageEx.getrPaybackId());// 杩樻涓昏〃ID
		paybackOpe.setDictLoanStatus(RepaymentProcess.OVERDUE_CUT.getCode());// 鎿嶄綔姝ラ
		paybackOpe.setDictRDeductType(TargetWay.REPAYMENT.getCode());// 鍏宠仈绫诲瀷
		paybackOpe.setOperateResult(PaybackOperate.DERATE_SUCCEED.getCode());// 鎿嶄綔缁撴灉
		paybackOpe.setRemarks(DeductedConstantEx.ERATERESULT + reductionDay);// 澶囨敞
		try {
			overdueManageEx.setMonthReductionDay(overdueManageEx.getMonthReductionDay()+reductionDay);
			overdueManageService.updateOverdueDay(overdueManageEx,paybackOpe);// 鎻掑叆鎿嶄綔娴佹按璁板綍
		} catch (Exception e) {
			return e.getMessage();
		}
		logger.debug("invoke OverdueMangeController method: updateOverdueDay, consult.id is: "+ paybackOpe);
		return DeductedConstantEx.DUCTION_TODO;
	}
	
	/**
	 * 逾期管理列表减免金额计算(累计减免金额)
	 * 2016年4月8日
	 * By zhaojinping
	 * @param overdueManageEx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateReductionAmount")
	public String updateReductionAmount(OverdueManageEx overdueManageEx){
//		overdueManageService.nullJudgment(null, overdueManageEx);
		String reductionBy = UserUtils.getUser().getId();
		overdueManageEx.setReductionBy(reductionBy);
		BigDecimal reductionAmount = overdueManageEx.getReductionAmount();// 减免金额
		BigDecimal monthLateFee = overdueManageEx.getMonthLateFee();// 应还滞纳金
		BigDecimal monthInterestPunishshould = overdueManageEx.getMonthInterestPunishshould();//应还罚息
		BigDecimal monthLateFeeReduction = overdueManageEx.getMonthLateFeeReduction();//减免滞纳金
		BigDecimal monthPunishReduction = overdueManageEx.getMonthPunishReduction();//减免罚息
		// BigDecimal monthInterestPayactual = overdueManageEx.getMonthInterestPayactual();// 实还利息
		BigDecimal monthInterestPunishactual = overdueManageEx.getMonthInterestPunishactual();// 实还罚息
		BigDecimal actualMonthLateFee = overdueManageEx.getActualMonthLateFee();// 实还滞纳金
		BigDecimal monthPenaltyShould = overdueManageEx.getMonthPenaltyShould();//应还违约金
		BigDecimal monthPenaltyActual = overdueManageEx.getMonthPenaltyActual();//实还违约金
		BigDecimal monthPenaltyReduction = overdueManageEx.getMonthPenaltyReduction();//减免违约金
		String contractVersion = overdueManageEx.getContractVersion();//合同版本号
		if(StringUtils.isNotEmpty(contractVersion)  && Integer.valueOf(contractVersion) >= 4){
			// 差值 (应还滞纳金-实还滞纳金-减免滞纳金)
			BigDecimal monthPenaltyShouldDiff = monthLateFee.subtract(monthLateFeeReduction).subtract(actualMonthLateFee);
			// 差值(应还罚息-实还罚息-减免罚息)
			BigDecimal monthInterestDiff = monthInterestPunishshould.subtract(monthInterestPunishactual).subtract(monthPunishReduction);
			if(reductionAmount.compareTo(monthPenaltyShouldDiff) == DeductedConstantEx.CONPARETO || reductionAmount.compareTo(monthPenaltyShouldDiff) == DeductedConstantEx.INIT_ZERO){
			// 减免金额小于或等于差值
			  overdueManageEx.preUpdate();
			  overdueManageEx.setMonthLateFeeReduction(monthLateFeeReduction.add(reductionAmount));
			//  overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
			}else{
			  // 减免金额大于差值
			  // 更新减免滞纳金
			  overdueManageEx.preUpdate();
			  overdueManageEx.setMonthLateFeeReduction(monthLateFeeReduction.add(monthPenaltyShouldDiff));
			  // 将多余的钱放入减免罚息中
			  //剩余的减免金额
			  BigDecimal DeductedDict = reductionAmount.subtract(monthPenaltyShouldDiff);
			  if(DeductedDict.compareTo(monthInterestDiff) == DeductedConstantEx.CONPARETO || DeductedDict.compareTo(monthInterestDiff) == DeductedConstantEx.INIT_ZERO){
			  // 将剩余减免金额和原来的减免罚息相加
			  overdueManageEx.setMonthPunishReduction(monthPunishReduction.add(DeductedDict));
			 // overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
				}
			}
		}else{
			// 差值 (应还违约金-实还违约金-减免违约金)
			BigDecimal monthPenaltyShouldDiff = monthPenaltyShould.subtract(monthPenaltyActual).subtract(monthPenaltyReduction);
			// 差值(应还罚息-实还罚息-减免罚息)
			BigDecimal monthInterestDiff = monthInterestPunishshould.subtract(monthInterestPunishactual).subtract(monthPunishReduction);
			if(reductionAmount.compareTo(monthPenaltyShouldDiff) == DeductedConstantEx.CONPARETO || reductionAmount.compareTo(monthPenaltyShouldDiff) == DeductedConstantEx.INIT_ZERO){
			// 减免金额小于或等于差值
				overdueManageEx.preUpdate();
				overdueManageEx.setMonthPenaltyReduction(monthPenaltyReduction.add(reductionAmount));
				//overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
			}else{
				// 减免金额大于差值
				 // 更新减免违约金
			    overdueManageEx.preUpdate();
				overdueManageEx.setMonthPenaltyReduction(monthPenaltyReduction.add(monthPenaltyShouldDiff));
				// 将多余的钱放入减免罚息中
			    //剩余的减免金额
				BigDecimal DeductedDict = reductionAmount.subtract(monthPenaltyShouldDiff);
			    if(DeductedDict.compareTo(monthInterestDiff) == DeductedConstantEx.CONPARETO || DeductedDict.compareTo(monthInterestDiff) == DeductedConstantEx.INIT_ZERO){
				 // 将剩余减免金额和原来的减免罚息相加
				 overdueManageEx.setMonthPunishReduction(monthPunishReduction.add(DeductedDict));
				// overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
				}
			}
		}
		overdueManageService.updateMonthPenaltyReduction(overdueManageEx,reductionAmount,overdueManageEx.getReductionReson());
		return DeductedConstantEx.DUCTION_TODO;
	}
	
	
	/**
	 * 1.3合同逾期管理列表减免功能(减免金额只保存追后一次减免金额)
	 * 修改减免金额
	 * 2015年12月14日
	 * By 李强
	 * @param overdueManageEx
	 * @return 修改减免金额结果   1：成功   2：失败
	 */
//	@ResponseBody
//	@RequestMapping(value = "updateReductionAmount")
//	public String updateReductionAmount(OverdueManageEx overdueManageEx){
//		BigDecimal reductionAmount = overdueManageEx.getReductionAmount();// 减免金额
//		BigDecimal monthPenaltyShould = overdueManageEx.getMonthPenaltyShould();// 应还违约金
//		BigDecimal monthPenaltyReduction = overdueManageEx.getMonthPenaltyReduction();//减免违约金
//		BigDecimal monthPunishReduction = overdueManageEx.getMonthPunishReduction();//减免罚息
//		 // if减免违约金小于应还违约金，计算应还违约金和减免违约金的差值
//		
//			// 减免违约金小于应还违约金
//			// 差值
//			BigDecimal monthPenaltyShouldDiff = monthPenaltyShould.subtract(monthPenaltyReduction);
//			if(reductionAmount.compareTo(monthPenaltyShouldDiff) == DeductedConstantEx.CONPARETO){
//				// 减免金额小于差值(应还违约金-减免违约金)
//				overdueManageEx.preUpdate();
//				overdueManageEx.setMonthPenaltyReduction(monthPenaltyReduction.add(reductionAmount));
//				overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
//			}else if(reductionAmount.compareTo(monthPenaltyShould) == DeductedConstantEx.INIT_ZERO){
//				// 减免金额等于差值(应还违约金-减免违约金)
//				overdueManageEx.preUpdate();
//				overdueManageEx.setMonthPenaltyReduction(monthPenaltyShould);
//				overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
//			}else{
//				// 减免金额大于差值(应还违约金-减免违约金)
//				// 把减免违约金更新为应还违约金
//				overdueManageEx.preUpdate();
//				overdueManageEx.setMonthPenaltyReduction(monthPenaltyShould);
//				overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
//				// 将多余的钱放入减免罚息中
//				   //剩余的减免金额
//				BigDecimal DeductedDict = reductionAmount.subtract(monthPenaltyShouldDiff);
//				// 将剩余减免金额和原来的减免罚息相加
//				overdueManageEx.preUpdate();
//				overdueManageEx.setMonthPenaltyReduction(monthPunishReduction.add(DeductedDict));
//				overdueManageService.updateMonthPenaltyReduction(overdueManageEx);
//			}
//		
//		return DeductedConstantEx.DUCTION_TODO;
//	}
//	
	 /**
	  * 批量减免
	  * 2016年3月5日
	  * By zhaojinping
	  * @param pmId
	  */
	@ResponseBody
	@RequestMapping(value = "batchRedce")
	public String  batchReduce(@RequestParam(value="pmId",required=false) String pmId,@RequestParam(value="batchReson",required=false) String batchReson){
		BigDecimal bgSum = new BigDecimal(MONTH_ZERO);
	   return overdueManageService.batchReduce(pmId,bgSum,batchReson);
	}
	
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (int i =0;i<idArray.length;i++){
			String id  = idArray[i];
				if (i == 0){
					parameter.append("'" +id +"'");
				}else {
					parameter.append(",'" +id + "'");
				}
		}
		return parameter.toString();
	}
}











