package com.creditharmony.loan.borrow.blue.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

//import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.AgainstContent;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlue;
import com.creditharmony.loan.borrow.blue.entity.PaybackBlueAmountEx;
import com.creditharmony.loan.borrow.blue.service.PaybackBlueService;
import com.creditharmony.loan.borrow.blue.util.PaybackBlueExportUtil;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.entity.JkProducts;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.dao.PaybackMonthDao;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.RepaymentDateService;


/**
 * 蓝补信息管理处理Controller
 * @Class Name PaybackBlueController
 * @author 侯志斌
 * @Create In 2016年3月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/blue/PaybackBlue")
public class PaybackBlueController  extends BaseController {
	
	@Autowired
	private PaybackBlueService paybackBlueService;
	Page<PaybackBlue> paybackBluePage ;

	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private RepaymentDateService dateService;
	@Autowired
	private PaybackMonthDao paybackMonthDao;
	
	/**
	 * 蓝补信息管理列表
	 * 2016年3月1日
	 * By 侯志斌
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackBlue
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String goPaybackBlueList(
		HttpServletRequest request,
		HttpServletResponse response, 
		Model model, 
		PaybackBlue params
	) {
		params.setEffectiveFlag(YESNO.YES.getCode());
		if (params.getOrgId() != null && 
			!"".equals(params.getOrgId())
		) {
			params.setOrgList(params.getOrgId().split(","));
		}
		if (params.getPaybackDate() != null && 
			!"".equals(params.getPaybackDate())
		) {
			params.setPaybackDateList(params.getPaybackDate().split(","));
		}
		
//		Page<PaybackBlue> paybackPage = paybackBlueService.findPayback(new Page<PaybackBlue>(request, response), payback);
		
		// 执行数据库查询语句，带条件
		Page<PaybackBlue> page = new Page<PaybackBlue>(request, response);
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
				"com.creditharmony.loan.borrow.blue.dao.PaybackBlueDao.cnt",
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
					"com.creditharmony.loan.borrow.blue.dao.PaybackBlueDao.findPayback",
					params,
					ssf
				);
				ps = cn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				List<PaybackBlue> ls = new ArrayList<PaybackBlue>();
				while (rs.next()) {
					PaybackBlue rec = new PaybackBlue();
					rec.setId(rs.getString("id"));
					rec.setContractCode(rs.getString("contract_code"));						// 合同编号
					rec.setDictPayStatus(rs.getString("dict_pay_status"));					// 还款状态
					rec.setPaybackDay(rs.getString("payback_day"));							// 还款日
					rec.setPaybackBuleAmount(rs.getBigDecimal("payback_bule_amount"));		// 蓝补金额
					rec.setPaybackMonthAmount(rs.getBigDecimal("payback_month_amount"));	// 期供
					rec.setModifyTime(rs.getTimestamp("modify_time"));						// 更新时间
					rec.setPaybackBackAmount(rs.getBigDecimal("payback_back_amount"));		// 返款金额
					rec.setPaybackCurrentMonth(rs.getInt("payback_current_month"));			// 当前第几期
					
					rec.setCustomerCode(rs.getString("customer_code"));						// 客户编号
					LoanCustomer lc = new LoanCustomer();
					lc.setCustomerName(rs.getString("customer_name"));						// 客户姓名
					lc.setCustomerCertNum(rs.getString("customer_cert_num"));				// 证件号码
					lc.setCustomerTelesalesFlag(rs.getString("customer_telesales_flag"));	// 是否电销
					rec.setLoanCustomer(lc);
					
					Contract c = new Contract();
					c.setLoanCode(rs.getString("loan_code"));										// 借款编码
					c.setProductType(rs.getString("product_type"));									// 产品类型
					c.setContractAmount(rs.getBigDecimal("contract_amount"));						// 合同金额
					c.setContractMonths(rs.getBigDecimal("contract_months"));						// 批借期限
					c.setContractMonthRepayAmount(rs.getBigDecimal("contract_month_repay_amount"));	// 月还款本息和 
					c.setContractReplayDay(rs.getDate("contract_replay_day"));						// 起始还款日期
					c.setContractEndDay(rs.getDate("contract_end_day"));							// 合同到期日期
					c.setContractVersion(rs.getString("contract_version"));							// 合同版本号
					c.setModel(rs.getString("model"));												// 模型
					rec.setContract(c);
					
					rec.setOrgName(rs.getString("org_name"));										// 门店名
					
					LoanInfo li = new LoanInfo();
					li.setDictLoanStatus(rs.getString("dict_loan_status"));							// 借款状态
					li.setLoanTeamOrgId(rs.getString("loan_team_orgid"));							// 团队组织机构ID
					li.setLoanStoreOrgId(rs.getString("loan_store_orgid"));							// 组织机构ID 
					li.setLoanFlag(rs.getString("loan_flag"));										// 渠道
					rec.setLoanInfo(li);
					
					LoanBank lb = new LoanBank();
					lb.setId(rs.getString("loanBankId"));											// 
					lb.setLoanCode(rs.getString("loan_code"));										// 借款编码
					lb.setBankAccountName(rs.getString("bank_account_name"));						// 开户姓名
					lb.setBankAccount(rs.getString("bank_account"));								// 账户
					lb.setBankBranch(rs.getString("bank_branch"));									// 具体支行
					lb.setBankName(rs.getString("bank_name"));										// 开户行
					rec.setLoanBank(lb);
					
					UrgeServicesMoney usa = new UrgeServicesMoney();
					usa.setrGrantId(rs.getString("r_grant_id"));									// 关联放款id
					usa.setUrgeMoeny(rs.getBigDecimal("urge_moeny"));								// 催收服务费
					rec.setUrgeServicesMoney(usa);
					
					LoanGrant lg = new LoanGrant();
					lg.setGrantAmount(rs.getBigDecimal("grant_amount"));							// 
					rec.setLoanGrant(lg);
					
					PaybackMonth pm = new PaybackMonth();
					pm.setId(rs.getString("paybackMonthId"));
					pm.setMonthPenaltyShouldSum(rs.getBigDecimal("month_Penalty_Should_sum"));
					pm.setMonthInterestPunishshouldSum(rs.getBigDecimal("month_Interest_Punishshould_Sum"));
					rec.setPaybackMonth(pm);
					
					JkProducts pro = new JkProducts();
					pro.setProductName(rs.getString("product_name"));								// 
					rec.setJkProducts(pro);

					if (!ObjectHelper.isEmpty(rec.getContract()) && 
						StringUtils.isNotEmpty(rec.getContract().getContractVersion())
					) {
						Map<String, Object> map = new HashMap<String, Object>();
						PaybackMonth pbm = new PaybackMonth();
						map.put("contractCode", rec.getContractCode());
						pbm = paybackMonthDao.getPaybackMonthByContractCode(map);
						if (!ObjectHelper.isEmpty(pbm)) {
							String contractVersion = rec.getContract().getContractVersion();
							if (StringUtils.isNotEmpty(contractVersion) && Integer.valueOf(contractVersion) >=4) {
								// 1.4 1.5
								pbm.setMonthPenaltyShouldSum(pm.getMonthLateFeeSum());
							}
							rec.setPaybackMonth(pbm);
						}
					}
					// 模式
					if (!ObjectHelper.isEmpty(rec.getContract()) && 
						StringUtils.isNotEmpty(rec.getContract().getModel())
					) {
						String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model", rec.getContract().getModel());
						rec.getContract().setModel(dictLoanModel);
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
//		if (ArrayHelper.isNotEmpty(page.getList())) {
//			for (PaybackBlue pb : page.getList()) {
//				if (!ObjectHelper.isEmpty(pb.getContract()) && 
//					StringUtils.isNotEmpty(pb.getContract().getContractVersion())
//				) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					PaybackMonth pm = new PaybackMonth();
//					map.put("contractCode", pb.getContractCode());
//					pm = paybackMonthDao.getPaybackMonthByContractCode(map);
//					if (!ObjectHelper.isEmpty(pm)) {
//						String contractVersion = pb.getContract().getContractVersion();
//						if (ContractVer.VER_ONE_FIVE.getCode().equals(contractVersion) || 
//							ContractVer.VER_ONE_FOUR.getCode().equals(contractVersion)
//						) {
//							// 1.4 1.5
//							pm.setMonthPenaltyShouldSum(pm.getMonthLateFeeSum());
//						}
//						pb.setPaybackMonth(pm);
//					}
//				}
//				// 模式
//				if (!ObjectHelper.isEmpty(pb.getContract()) && 
//					StringUtils.isNotEmpty(pb.getContract().getModel())
//				) {
//					String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model", pb.getContract().getModel());
//					pb.getContract().setModel(dictLoanModel);
//				}
//			}
//		}
		model.addAttribute("paybackList", page);
		
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		
		model.addAttribute("search", params);
		
		logger.debug("invoke PaybackBlueController method: goPaybackBlueList, contarctCode is: "+ params.getContractCode());
		return "borrow/blue/paybackBlueList";
	}
	
	/**
	 * 初始化页面
	 * 2016年3月2日
	 * By 侯志斌
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackBlue
	 * @return page
	 */
    @RequestMapping(value = { "openPaybackBlue" })
    public String openPaybackBlue(Model  model) {
    	List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
        return "borrow/blue/paybackBlueList";
    }
    
    
    /**
	 * 初始化转账页面
	 * 2016年3月2日
	 * By 侯志斌
	 * @param model
	 * @param paybackId
	 * @param certNum
	 * @return String
	 */
    @RequestMapping(value = { "openTransferBlue" })
    public String openTransferBlue(Model model,String paybackId,String certNum) {
		PaybackBlue pb = new PaybackBlue();
		pb.setContractCode(paybackId);
		pb = paybackBlueService.getPayback(pb);
    	if (!ObjectHelper.isEmpty(pb.getLoanInfo())) {
    		pb.getLoanInfo().setDictLoanStatus(DictCache.getInstance().getDictLabel(
    				"jk_loan_apply_status", pb.getLoanInfo().getDictLoanStatus()));
		}
    	
    	if (StringUtils.isNotEmpty(pb.getCustomerCode())) {
    		// 根据客户编号查找其他蓝补账户
			String customerCode = pb.getCustomerCode();
			if(StringUtils.isNotEmpty(customerCode)){
				List<Payback> paybackOtherList = paybackBlueService.findPaybackByCustomer(customerCode, pb.getContractCode());
				if (!ObjectHelper.isEmpty(paybackOtherList)) {
					for(Payback p :paybackOtherList){
						if (!ObjectHelper.isEmpty(p.getLoanInfo())) {
				    		p.getLoanInfo().setDictLoanStatus(DictCache.getInstance().getDictLabel(
				    				"jk_loan_apply_status", p.getLoanInfo().getDictLoanStatus()));
						}
					}
					model.addAttribute("paybackOther", paybackOtherList);
				}
			}
		}
		model.addAttribute("payback", pb);
        return "borrow/blue/TransferBlue";
    }
    
    /**
	 * 转账
	 * 2016年3月3日
	 * By 侯志斌
	 * @param paybackList 
	 * @return String
	 * @throws Exception 
	 */
    //TODO exception需要catch
	@ResponseBody
	@RequestMapping(value="saveTransData")
	public String saveTransData(Payback payback) throws Exception{
		boolean result = paybackBlueService.transPaybackBlue(payback);
		if(result){
			return "true";
		}
		return "false";
	}
	

	/**
	 * 初始化退款页面
	 * 2016年3月3日
	 * By 侯志斌
	 * @param model
	 * @param paybackId
	 * @param certNum
	 * @return String
	 */
    @RequestMapping(value = { "openRefundBlue" })
    public String openRefundBlue(Model model,String paybackId,String certNum) {
		Payback payback = new Payback();
		payback.setContractCode(paybackId);
    	List<Payback> paybackList = applyPayService.findPayback(payback);
		model.addAttribute("payback", paybackList.get(0));
        return "borrow/blue/RefundBlue";
    }
    
    /**
	 * 退款
	 * 2016年3月4日
	 * By 侯志斌
	 * @param payback
	 * @param paybackBuleAmountLast
	 * @param paybackBuleReson
	 * @return String
	 * @throws Exception 
	 */
    //TODO exception需要catch
	@ResponseBody
	@RequestMapping(value="saveRefundData")
	public String saveRefundData(Payback payback,
			BigDecimal paybackBuleAmountLast, String paybackBuleReson)
			throws Exception {
		boolean result = paybackBlueService.refundPaybackBlue(payback,
				paybackBuleAmountLast, paybackBuleReson);
		if (result) {
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	    
	/**
	 * 蓝补对账单列表
	 * 2016年3月4日
	 * By 侯志斌
	 * @param request
	 * @param response
	 * @param model
	 * @param payback
	 * @return page
	 */
	@RequestMapping(value = "listBlue")
	public String goAmountBlueList(HttpServletRequest request,
			HttpServletResponse response, Model model,
			PaybackBlueAmountEx payback) {
		PaybackBlue p = new PaybackBlue();
		p.setEffectiveFlag(YESNO.YES.getCode());
		p.setContractCode(payback.getContractCode());
		List<Map<String, Object>> list = paybackBlueService.getCustomer(p);
		model.addAttribute("contractcode", payback.getContractCode());
		model.addAttribute("search", payback);
		if(!list.isEmpty()){
			Date dt = new Date(new Date().getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.add(Calendar.MONTH, -6);//半年前
			String endDate = sdf.format(dt);
			String startDate = sdf.format(calendar.getTime());
			if(payback.getStartDate()==null||"".equals(payback.getStartDate())){
				payback.setStartDate(startDate);
			}
			if(payback.getEndDate()==null||"".equals(payback.getEndDate())){
				payback.setEndDate(endDate);
			}
			Page<PaybackBlueAmountEx> paybackPage = paybackBlueService.findPaybackBlueAmoun(
					new Page<PaybackBlueAmountEx>(request, response), payback);
			for (PaybackBlueAmountEx pb : paybackPage.getList()) {
				if(StringUtils.isNotEmpty(pb.getOperator()) && pb.getOperator().getBytes().length == pb.getOperator().length()){
					if(ObjectHelper.isNotEmpty(UserUtils.get(pb.getOperator())) && StringUtils.isNotEmpty(UserUtils.get(pb.getOperator()).getName())){
						pb.setOperator(UserUtils.get(pb.getOperator()).getName());
					}
				}
				
				if(StringUtils.isNotEmpty(pb.getDictDealUse()) && pb.getDictDealUse().getBytes().length == pb.getDictDealUse().length()){
					pb.setDictDealUse(String.valueOf(AgainstContent.parseByCode(pb.getDictDealUse())));
				}
			}
			model.addAttribute("customer", list.get(0));
			model.addAttribute("paybackList", paybackPage);
		}
		return "borrow/blue/AmountListBlue";
	}
	
	/**
	 * 蓝补对账单导出
	 * 2016年3月4日
	 * By 侯志斌
	 * @param request
	 * @param response
	 * @param cid 要进行导出的单子的id
	 */
	@RequestMapping(value = "listBlueimp")
	public void listBlueimp(HttpServletRequest request,PaybackBlueAmountEx paybackBlueAmountEx,
			HttpServletResponse response, String cid) {
		Date dt = new Date(new Date().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.MONTH, -6);//半年前
		String endDate = sdf.format(dt);
		String startDate = sdf.format(calendar.getTime());
		if(paybackBlueAmountEx.getStartDate()==null||"".equals(paybackBlueAmountEx.getStartDate())){
			paybackBlueAmountEx.setStartDate(startDate);
		}
		if(paybackBlueAmountEx.getEndDate()==null||"".equals(paybackBlueAmountEx.getEndDate())){
			paybackBlueAmountEx.setEndDate(endDate);
		}
		
		PaybackBlueExportUtil.exportData(paybackBlueAmountEx, response, FileExtension.BLUE_XLS
				+ System.currentTimeMillis());
		/*ExcelUtils excelutil = new ExcelUtils();
		List<PaybackBlueAmountEx> urgeList = new ArrayList<PaybackBlueAmountEx>();
		 
		try {
			urgeList = paybackBlueService.selectPaybackBlueAmoun(paybackBlueAmountEx);
			
			// 如果没有进行选择，
			excelutil.exportExcel(urgeList, FileExtension.URGE_SECCESS
					+ System.currentTimeMillis(), PaybackBlueAmountEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 初始化蓝补金额修改页面
	 * 2016年4月18日
	 * By WJJ
	 * @param model
	 * @param paybackId
	 * @param certNum
	 * @return String
	 */
    @RequestMapping(value = { "updateBluePage" })
    public String updateBluePage(Model model,String paybackId,String certNum) {
    	Map<String, Object> map  = new HashMap<String, Object>();
    	map.put("contractCode", paybackId);
		Payback pa = applyPayService.findPaybackByContract(map);
		model.addAttribute("payback", pa);
        return "borrow/blue/updateBlue";
    }
    
    /**
	 * 蓝补金额修改
	 * 2016年4月18日
	 * By WJJ
	 * @param payback
	 * @param paybackBuleAmountLast
	 * @param paybackBuleReson
	 * @return String
	 * @throws Exception 
	 */
    //TODO exception需要catch
	@ResponseBody
	@RequestMapping(value="updateBlue")
	public String updateBlue(Payback payback,String paybackBuleReson) throws Exception{
		boolean result = paybackBlueService.updatePaybackBlue(payback, paybackBuleReson);
		if(result){
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	
}
