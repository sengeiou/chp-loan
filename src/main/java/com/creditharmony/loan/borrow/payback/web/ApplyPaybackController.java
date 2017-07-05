package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.deduct.entity.PlatformBankEntity;
import com.creditharmony.core.deduct.service.PlatformBankService;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlatBank;
import com.creditharmony.core.loan.type.Eletric;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.TrustmentStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.payback.dao.DeductPaybackDao;
import com.creditharmony.loan.borrow.payback.dao.EarlyFinishConfirmDao;
import com.creditharmony.loan.borrow.payback.entity.BankPlantPort;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantLimit;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackService;
import com.creditharmony.loan.borrow.payback.service.BankPlantPortService;
import com.creditharmony.loan.borrow.payback.service.PlatformGotoRuleManager;
import com.creditharmony.loan.common.constants.AuditType;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.LoanDeductService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.ReceiveRefundInfoService;
import com.creditharmony.loan.common.service.ReceiveTransferService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.utils.WorkDayCheck;
import com.creditharmony.loan.common.vo.DefaultServiceVO;

/**
 * 发起还款申请业务处理Controller
 * 
 * @Class Name ApplyPaybackController
 * @author zhangfeng
 * @Create In 2015年12月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/applyPayback")
public class ApplyPaybackController extends BaseController {

	@Autowired
	private ApplyPaybackService applyPayback;
	@Autowired
	private PaybackService paybackService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private LoanDeductService loanDeductService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private  PlatformBankService plbs;

	@Autowired
	private BankPlantPortService bankPlantPortService;
	@Autowired
	private PlatformGotoRuleManager platformGotoRuleManager;
	@Autowired
	private DeductPaybackDao deductPaybackDao;
	
	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 还款申请页面初始化存入账户
	 *  2016年1月5日 By zhangfeng
	 * @param model
	 * @param request
	 * @param response
	 * @return page
	 */
	@RequestMapping(value = "form")
	public String loadApplyPaybackForm(Model model, HttpServletRequest request, HttpServletResponse response) {
		MiddlePerson mp = new MiddlePerson ();
		// 存入标识
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		model.addAttribute("middlePersonList", mpList);
		//取的申请类型类型
		String applyType = request.getParameter("applyType");
		//判定下个工作日日期
		int day = WorkDayCheck.getConfirmStartTime(Calendar.getInstance(),1); // 当前天的下一个工作日
		Date d = new Date();
		@SuppressWarnings("deprecation")
		int hour= d.getHours();
		String isfive = YESNO.NO.getCode();
		if (hour > 17) {
			isfive = YESNO.YES.getCode();
		}
		
		model.addAttribute("checkDay", String.valueOf(day));
		model.addAttribute("isfive", isfive);
		model.addAttribute("applyType", applyType);
		
		
		return "borrow/payback/applypay/applyPayback";
	}
	
	/**
	 * 获取
	 * @author 于飞
	 * @Create 2016年11月2日
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMiddlePersonList")
	public String getMiddlePersonList() {
		MiddlePerson mp = new MiddlePerson ();
		// 存入标识
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		return JsonMapper.nonDefaultMapper().toJson(mpList);
	}

	/**
	 * 根据合同编号查询还款信息
	 *  2015年12月17日 By zhangfeng
	 * @param payback
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findApplyByContractCode", method = RequestMethod.POST)
	public String findApplyByContractCode(Payback payback) {
		List<Payback> paybackList = new ArrayList<Payback>();
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		// 判断当前门店的失败率和余额不住率是否已经超过限制
		if(limit(payback)){
			return "{'limit':'已经超过业务设置的失败比例或者余额不足比例！'}";
		}
		// 根据类型设置查询参数
		setParameters(payback);
		paybackList = paybackService.findPayback(payback);
		if (ArrayHelper.isNotEmpty(paybackList)) {
			Map<String, String>  map = bankPlantData();
			for (int i = 0; i < paybackList.size(); i++) {
				//如果是结清或提前结清状态并且是zcj数据，则讲数据清空
				if((paybackList.get(i).getDictPayStatus().equals(RepayStatus.PRE_SETTLE.getCode())
						|| paybackList.get(i).getDictPayStatus().equals(RepayStatus.SETTLE.getCode()))
						&& paybackList.get(i).getContract().getChannelFlag().equals(ChannelFlag.ZCJ.getCode())){
					paybackList.clear();
					break;
				}
				/*if(!ObjectHelper.isEmpty(paybackList.get(i).getLoanBank()) && StringUtils.isNotEmpty(

						paybackList.get(i).getLoanBank().getBankAccount())){
					   String  plat  = paybackList.get(i).getLoanBank().getBankSigningPlatform();
					   if(DeductPlat.HAOYILIAN.getCode().equals(plat)){
						   String loanBank = paybackList.get(i).getLoanBank().getBankAccount();
						   DeductReq req = new DeductReq();
						   req.setAccountNo(loanBank);
						   req.setCreatTime(new Date());
						   BalanceInfo info = deductPaybackDao.queryBalanceInfo(req);
						   if(info != null){
								if(info.getTotal()>=1){
									paybackList.get(i).setZjcnt("2");
								}else{
									   paybackList.get(i).setZjcnt("0");
								   }
							}  
					   }else{
						   paybackList.get(i).setZjcnt("0");
					   }
					
				}*/
				// 借款状态
				if (!ObjectHelper.isEmpty(paybackList.get(i).getLoanInfo()) && StringUtils.isNotEmpty(
						paybackList.get(i).getLoanInfo().getDictLoanStatus())) {
					String label = DictCache.getInstance().getDictLabel("jk_loan_apply_status", paybackList.get(i).getLoanInfo().getDictLoanStatus());
					paybackList.get(i).getLoanInfo().setDictLoanStatusLabel(label);
				} else {
					paybackList.get(i).getLoanInfo().setDictLoanStatus(null);
				}

				// 委托充值状态
				if (!ObjectHelper.isEmpty(paybackList.get(i).getLoanInfo()) && StringUtils.isNotEmpty(
						paybackList.get(i).getLoanInfo().getTrustRecharge())) {
					paybackList.get(i).getLoanInfo().setTrustRecharge(
									TrustmentStatus.parseByCode(paybackList.get(i).getLoanInfo().getTrustRecharge()).getName());
				} else {
					paybackList.get(i).getLoanInfo().setTrustRecharge(null);
				}
				String rule ="";
				String bankSigningPlatform ="";
				String bankName = "";
				// 开户行
				if (!ObjectHelper.isEmpty(paybackList.get(i).getLoanBank()) && StringUtils.isNotEmpty(
						paybackList.get(i).getLoanBank().getBankName())) {
					bankSigningPlatform = paybackList.get(i).getBankSigningPlatform();
					bankName = paybackList.get(i).getLoanBank().getBankName();
					rule = map.get(bankName+bankSigningPlatform);
					if(ObjectHelper.isEmpty(rule)){
						rule = bankSigningPlatform + ":" + "0";
					}
					paybackList.get(i).getLoanBank().setBankCode(paybackList.get(i).getLoanBank().getBankName());
					if (StringUtils.isNotEmpty(DeductPlatBank.getNameByCode(paybackList.get(i).getLoanBank().getBankName()))) {
						paybackList.get(i).getLoanBank().setBankName(
										DeductPlatBank.getNameByCode(paybackList.get(i).getLoanBank().getBankName()));
					} else {
						paybackList.get(i).getLoanBank().setBankName(paybackList.get(i).getLoanBank().getBankName());
					}
				}
				if(!ObjectHelper.isEmpty(rule)){
					long sumDayLimit = getRestSumDayLimit(rule,rule.split(",").length,bankName,DeductWays.HJ_02.getCode());
					if(sumDayLimit != 0){
						sumDayLimit = sumDayLimit/100;
					}
					paybackList.get(i).setSumDayLimit(sumDayLimit);
				}
				// 上传人
				paybackTransferInfo.setUploadName(UserUtils.getUser().getName());
				paybackList.get(i).setPaybackTransferInfo(paybackTransferInfo);
				paybackList.get(i).setApplyType(payback.getApplyType());
				// 添加applyToken 
				TokenUtils.removeToken(payback.getApplyTokenId());
			    Map<String,String> tokenMap = TokenUtils.createToken();
			    paybackList.get(i).setApplyTokenId(tokenMap.get("tokenId"));
			    paybackList.get(i).setApplyToken(tokenMap.get("token"));
			    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
			}
		}
		return JsonMapper.nonDefaultMapper().toJson(paybackList);
	}

	/**
	 * 根据借款编码查询客户账户信息 2015年12月12日 By zhangfeng
	 * 
	 * @param loanCode
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findCustomerByLoanCode", method = RequestMethod.POST)
	public String findCustomerByLoanCode(Model model, String loanCode,String id) {
		List<LoanBank> loanBankList = new ArrayList<LoanBank>();
		loanBankList = applyPayback.findCustomerByLoanCode(loanCode,id);
		if (ArrayHelper.isNotEmpty(loanBankList)) {
			for (int i = 0; i < loanBankList.size(); i++) {
				if (!ObjectHelper.isEmpty(loanBankList.get(i))) {
					// 开户行
					loanBankList.get(i).setBankName(DeductPlatBank.getNameByCode(
							loanBankList.get(i).getBankName())+loanBankList.get(i).getBankBranch());
				}
			}
		}
		return JsonMapper.nonDefaultMapper().toJson(loanBankList);
	}
	
	/**
	 * 从新获取token 2015年12月12日 By zhangfeng
	 * 
	 * @param loanCode
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "getApplyToken", method = RequestMethod.POST)
	public String getApplyToken(Payback payback) {
		// 添加applyToken 
		Map<String, String> map = new HashMap<String, String>();
		TokenUtils.removeToken(payback.getApplyTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    pushTokenMap(map, tokenMap.get("tokenId"), tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		return JsonMapper.nonDefaultMapper().toJson(map);
	}
	
	/**
	 * 保存还款申请信息
	 * 2015年12月17日 By zhangfeng
	 * @param payBack
	 * @param confrimFlag
	 * @return redirect page
	 */
	@ResponseBody
	@RequestMapping(value = "saveApplyPayLaunch")
	public String saveApplyPayLaunch(@RequestParam("files") MultipartFile[] files, Payback p) {
		Map<String, String> map = new HashMap<String, String>();
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = p.getApplyTokenId();
			String curToken = p.getApplyToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		//电催数据
		if("1".equals(p.getApplyType())){
			p.getPaybackApply().setUrgeManage(Eletric.ELETRIC.getCode());
			p.getPaybackApply().setOperateRole(AuditType.DIANCUI.value);
		}
		//电销
		if("2".equals(p.getApplyType())){
			p.getPaybackApply().setUrgeManage(Eletric.PHONESAlE.getCode());
			p.getPaybackApply().setOperateRole(AuditType.DIANXIAO.value);
		}
		if(result){
			try {
				if (!ObjectHelper.isEmpty(p.getPaybackApply())) {
					if (StringUtils.equals(p.getPaybackApply().getDictRepayMethod(), RepayChannel.NETBANK_CHARGE.getCode())) {
						// 同一合同编号，存入银行重复验证
						String storesInAccountMsg = applyPayback.findTransferInfoByStoresInAccount(p);
						if(StringUtils.isNotEmpty(storesInAccountMsg)){
							pushMap(map, YESNO.YES.getCode(), storesInAccountMsg);
							return JsonMapper.nonDefaultMapper().toJson(map);
						}	
						
						if (StringUtils.equals(p.getPaybackApply().getConfrimFlag(), YESNO.NO.getCode())) {
							// 不是同一合同编号 汇款单重复验证
							String infoMsg = applyPayback.findPayBackTransferOut(p.getPaybackTransferInfo());
							if (StringUtils.isNotEmpty(infoMsg)) {
								pushMap(map, YESNO.NO.getCode(), infoMsg);
								return JsonMapper.nonDefaultMapper().toJson(map);
							}
						}
						// 汇款保存信息
						applyPayback.savePaybackMatching(p, files);
					} else if (StringUtils.equals(p.getPaybackApply().getDictRepayMethod(), RepayChannel.DEDUCT.getCode())) {
						// 划扣保存信息
						applyPayback.savePaybackDeduct(p);
					} else if (StringUtils.equals(p.getPaybackApply().getDictRepayMethod(), RepayChannel.POS.getCode())) {
						// POS保存信息
						applyPayback.savePaybackPos(p);
					} else if (StringUtils.equals(p.getPaybackApply().getDictRepayMethod(), RepayChannel.POS_CHECK.getCode())) {
						// POS查账信息
						applyPayback.savePaybackPosCheck(p, files);
					}
					pushMap(map, "sus", "申请成功！");
				}
			} catch (Exception e) {
				pushMap(map, YESNO.YES.getCode(), "发起还款申请异常！");
			}
		}else{
			pushMap(map, "sus", ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE);
		}
		return JsonMapper.nonDefaultMapper().toJson(map);
	}

	/**
	 * 根据合同编号查询是否是逾期或还款中的信息 2016年2月16日 By guanhongchang
	 * 
	 * @param payback
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findApplyByOverdue", method = RequestMethod.POST)
	public String findApplyByOverdue(Payback payback) {
		List<Payback> paybackList = new ArrayList<Payback>();
		// 是否有效
		payback.setEffectiveFlag(YESNO.YES.getCode());
		// 逾期的才能选择POS机刷卡
		payback.setDictPayStatus("'" + LoanStatus.OVERDUE.getCode() + "'");
		// 查询该合同编号是否是还款中或是逾期
		paybackList = paybackService.findApplyByOverdue(payback);
		if (paybackList != null && !paybackList.isEmpty()) {
			return JsonMapper.nonDefaultMapper().toJson(1);
		} else {
			return JsonMapper.nonDefaultMapper().toJson(-1);
		}
	}

	/**
	 * 根据合同编号查询是否在门店代办中有未完成的POS还款信息 2016年2月16日 By guanhongchang
	 * 
	 * @param payback
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findApplyByDealt ", method = RequestMethod.POST)
	public String findApplyByDealt(Payback payback) {
		List<PaybackApply> paybackList = new ArrayList<PaybackApply>();
		PaybackApply paybackApply = new PaybackApply();
		// 查门店代办是否有未完成的POS还款申请
		// 合同编号
		paybackApply.setContractCode(payback.getContractCode());
		// 是否有效
		paybackApply.setEffectiveFlag(YESNO.YES.getCode());
		// 还款申请状态为还款中
		paybackApply.setDictPaybackStatus("'"
				+ RepayApplyStatus.TO_PAYMENT.getCode() + "'");
		// 还款方式为 POS机刷卡 还款dict_payback_status
		paybackApply.setDictRepayMethod("'" + RepayChannel.POS.getCode() + "'");
		// 查询门店代办中是否有未完成的POS还款申请
		paybackList = paybackService.findApplyByDealt(paybackApply);
		if (paybackList != null && !paybackList.isEmpty()) {
			return JsonMapper.nonDefaultMapper().toJson(1);
		} else {
			return JsonMapper.nonDefaultMapper().toJson(-1);
		}
	}

	/**
	 * 电催跳转还款申请页面初始化存入账户 2016年2月25日 By liushikang
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return page
	 */
	@RequestMapping(value = "goEletricPaybackForm")
	public String goEletricPaybackForm(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		if (ArrayHelper.isNotEmpty(mpList)) {
			model.addAttribute("middlePersonList", mpList);
		} else {
			model.addAttribute("middlePersonList", null);
			logger.debug("invoke ApplyPaybackController method: goEletricPaybackForm, middlePersonList.getList() is empty!");
		}
		return "borrow/payback/applypay/electricApplypay";
	}

	/**
	 * 电催根据合同编号查询还款信息 2016年2月25日 By liushikang
	 * 
	 * @param payback
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findEletrBycontractCode", method = RequestMethod.POST)
	public String findEletrBycontractCode(Payback payback) {
		List<Payback> paybackList = new ArrayList<Payback>();
		payback.setEffectiveFlag(YESNO.YES.getCode());
		//逾期
		/*payback.setDictPayStatus("'" + LoanStatus.OVERDUE.getCode() + "'");*/
		payback.setDictPayStatus("'" + RepayStatus.PEND_REPAYMENT.getCode() + "','" + RepayStatus.OVERDUE.getCode() + "','"
				+ RepayStatus.PEND_REPAYMENT.getCode() + "','" + RepayStatus.PEND_REPAYMENT.getCode() + "','"
				+ RepayStatus.SETTLE_FAILED.getCode() + "'");
		LoanInfo   loanInfo =new LoanInfo();
		loanInfo.setDictLoanStatus(LoanStatus.OVERDUE.getCode());
		//只查借款状态是否是逾期的
		payback.setLoanInfo(loanInfo);
	   String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
	   payback.setQueryRight(queryRight);
		paybackList = paybackService.findPayback(payback);
		if (ArrayHelper.isNotEmpty(paybackList)) {
			for (int i = 0; i < paybackList.size(); i++) {
				if (!ObjectHelper.isEmpty(paybackList.get(i).getLoanInfo()) && StringUtils.isNotEmpty(
						paybackList.get(i).getLoanInfo().getDictLoanStatus())) {
					// 借款状态
					paybackList.get(i).getLoanInfo().setDictLoanStatus(
							LoanStatus.getNameByCode(paybackList.get(i).getLoanInfo().getDictLoanStatus()));
				} else {
					paybackList.get(i).getLoanInfo().setDictLoanStatus(null);
				}
				if (!ObjectHelper.isEmpty(paybackList.get(i).getLoanInfo()) && StringUtils.isNotEmpty(
						paybackList.get(i).getLoanInfo().getTrustRecharge())) {
					// 委托充值状态
					paybackList.get(i).getLoanInfo().setTrustRecharge(
									TrustmentStatus.parseByCode(paybackList.get(i).getLoanInfo().getTrustRecharge()).getName());
				} else {
					paybackList.get(i).getLoanInfo().setTrustRecharge(null);
				}
				if (!ObjectHelper.isEmpty(paybackList.get(i).getLoanBank()) && StringUtils.isNotEmpty(paybackList.get(i).getLoanBank().getBankName())) {
					// 开户行
					paybackList.get(i).getLoanBank().setBankCode(paybackList.get(i).getLoanBank().getBankName());
					if (StringUtils.isNotEmpty(DeductPlatBank.getNameByCode(paybackList.get(i).getLoanBank().getBankName()))) {
						paybackList.get(i).getLoanBank().setBankName(
										DeductPlatBank.getNameByCode(paybackList.get(i).getLoanBank().getBankName()));
					} else {
						paybackList.get(i).getLoanBank().setBankName(paybackList.get(i).getLoanBank().getBankName());
					}
				}
				// 上传人
				PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
				paybackTransferInfo.setUploadName(UserUtils.getUser().getName());
				paybackList.get(i).setPaybackTransferInfo(paybackTransferInfo);
			}
		} else {
			logger.debug("invoke ApplyPaybackController method: findApplyBycontractCode, paybackList is empty!");
		}
		return JsonMapper.nonDefaultMapper().toJson(paybackList);
	}

	/**
	 * 保存电催还款申请信息 2016年2月25日 By liushikang
	 * 
	 * @param payBack
	 * @param redirectAttributes
	 * @return redirect page
	 */
	@ResponseBody
	@RequestMapping(value = "saveApplyEletr")
	public String saveApplyEletr(
			@RequestParam("files") MultipartFile[] files, Payback payback,
			String confrimFlag) {
		// 添加催收管辖标识
		payback.getPaybackApply().setUrgeManage(Eletric.ELETRIC.getCode());
		Map<String, String> map = new HashMap<String, String>();
		if (!ObjectHelper.isEmpty(payback.getPaybackApply())) {
			if (StringUtils.equals(payback.getPaybackApply()
					.getDictRepayMethod(), RepayChannel.NETBANK_CHARGE
					.getCode())) {
				if (payback.getPaybackDay() != null) {
					Format f = new SimpleDateFormat("dd");
					String newDate = f.format(new Date());
					payback.setLoanBank(null);
					if (StringUtils.equals(newDate, String.valueOf(payback.getPaybackDay()))) {
						// 还款日还款
						payback.getPaybackApply().setDictPayUse(RepayType.ACCOUNT_CHECK.getCode());
					} else {
						// 正常还款
						payback.getPaybackApply().setDictPayUse(RepayType.PAYBACK_APPLY.getCode());
					}
				}
				payback.getPaybackApply().setDictDealType(null);
				payback.getPaybackApply().setApplyAccountName(null);
				payback.getPaybackApply().setApplyDeductAccount(null);
				payback.getPaybackApply().setApplyBankName(null);
				payback.getPaybackApply().setApplyAmount(
						payback.getPaybackApply().getTransferAmount());
				payback.getPaybackApply().setDictPaybackStatus(
						RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
				payback.getPaybackApply().setDictDepositAccount(
						payback.getPaybackTransferInfo().getStoresInAccount());
			} else if (StringUtils.equals(payback.getPaybackApply()
					.getDictRepayMethod(), RepayChannel.DEDUCT.getCode())) {
				payback.setPaybackTransferInfo(null);
				// 修改划扣账号信息
				if (StringUtils.isNotEmpty(payback.getLoanBank().getNewId())) {
					payback.getLoanBank().setBankTopFlag(
							PaybackApply.TOP_FLAG_NO);
					payback.getLoanBank().preUpdate();
					LoanBank loanBank = new LoanBank();
					loanBank.setId(payback.getLoanBank().getNewId());
					loanBank.setBankTopFlag(PaybackApply.TOP_FLAG);
					loanBank.preUpdate();
					loanDeductService.updateTopFlag(payback.getLoanBank());
					loanDeductService.updateTopFlag(loanBank);
				}
				// 还款方式为 划扣，插入申请表时将回盘结果置为 待划扣 0
				payback.getPaybackApply().setSplitBackResult(
						CounterofferResult.PREPAYMENT.getCode());
				payback.getPaybackApply().setApplyAmount(
						payback.getPaybackApply().getDeductAmount());
				payback.getPaybackApply().setDictPaybackStatus(
						RepayApplyStatus.PRE_PAYMENT.getCode());
				payback.getPaybackApply().setDictDeductType(DeductWays.HJ_02.getCode());
			} else if (StringUtils.equals(payback.getPaybackApply()
					.getDictRepayMethod(), RepayChannel.POS.getCode())) {
				// 还款申请状态 待还款 还款申请状态
				payback.getPaybackApply().setDictPaybackStatus(
						RepayApplyStatus.TO_PAYMENT.getCode());
				// 申请划卡金额
				payback.getPaybackApply().setApplyAmount(
						payback.getPaybackApply().getDeductAmountPosCard());
				// 划扣平台
				payback.getPaybackApply().setDictDealType(null);
				// 帐号姓名
				payback.getPaybackApply().setApplyAccountName(null);
				// 划扣帐号
				payback.getPaybackApply().setApplyDeductAccount(null);
				// 还款类型
				payback.getPaybackApply().setDictPayUse(
						RepayType.PAYBACK_APPLY.getCode());
				// 开户行名称
				payback.getPaybackApply().setApplyBankName(null);
				// POS机订单编号生成
				payback.getPaybackApply().setPosBillCode(
						applyPayback.creatPosOrder(payback));
				
			} else if (StringUtils.equals(payback.getPaybackApply()
					.getDictRepayMethod(), RepayChannel.POS_CHECK.getCode())) {
				// 还款申请状态 待还款 还款申请状态
				payback.getPaybackApply().setDictPaybackStatus(
						RepayApplyStatus.HAS_PAYMENT.getCode());
				// 申请划卡金额
				payback.getPaybackApply().setApplyAmount(
						payback.getPaybackApply().getDeductAmountPosCard());
				
				// 划扣平台
				payback.getPaybackApply().setDictDealType(null);
				// 帐号姓名
				payback.getPaybackApply().setApplyAccountName(null);
				// 划扣帐号
				payback.getPaybackApply().setApplyDeductAccount(null);
				// 还款类型
				payback.getPaybackApply().setDictPayUse(
						RepayType.PAYBACK_APPLY.getCode());

				// 开户行名称
				payback.getPaybackApply().setApplyBankName(null);
			    //蓝补金额修改
			    // applyPayback.updateBlueMon(payback);
			}

			// 保存还款申请
			PaybackApply pa = payback.getPaybackApply();
			pa.setContractCode(payback.getContractCode());
			applyPayback.saveApplyPayback(pa);
			// 保存还款凭条信息
			PaybackTransferInfo info = payback.getPaybackTransferInfo();
			info.setContractCode(payback.getContractCode());
			info.setrPaybackApplyId(pa.getId());
			try {
				applyPayback.savePayBackTransferInfo(files, info);
			} catch (Exception e) {
				e.printStackTrace();
			}
			applyPayback.saveApplyHistory(pa, payback);
			pushMap(map, YESNO.NO.getCode(), "合同编号:" + payback.getContractCode() + "申请成功!");
		}
		logger.debug("invoke ApplyPaybackController method: saveApplyEletr, contarctCode is: "
				+ payback.getContractCode());
		return JsonMapper.nonDefaultMapper().toJson(map);
	}

	
	/**
	 * 将固定返回的信息放入map
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param map
	 * @param code
	 * @param msg
	 */
   private void pushMap(Map<String, String> map, String code, String msg) {
		map.put("code", code);
		map.put("msg", msg);
   }
   
   /**
	 * 将固定返回的信息放入map
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param map
	 * @param code
	 * @param msg
	 */
  private void pushTokenMap(Map<String, String> map, String id, String token) {
		map.put("id", id);
		map.put("token", token);
  }
   /**
    * 算出所有平台单日限额总数
    * @param allRule
    * @param unsplitTimes
    * @param bankId
    * @param sysId
    * @return sumDayLimit
    */
	private Long getRestSumDayLimit(String allRule, int unsplitTimes,
			String bankId, String sysId) {
		Long sumDayLimit = 0L; // 单日限额合计
		PlatformBankEntity recPlat = null; // 平台数据

		String[] arrAllRule = allRule.split(",");
		// 取得平台数
		int maxPlat = arrAllRule.length;
		// 剩余平台可划扣金额合计
		sumDayLimit = 0L;
		String sid = "";
		if (sysId.startsWith(String.valueOf(SystemFlag.FORTUNE.value))) {
			sid = String.valueOf(SystemFlag.FORTUNE.value);
		}
		if (sysId.startsWith(String.valueOf(SystemFlag.LOAN.value))) {
			sid = String.valueOf(SystemFlag.LOAN.value);
		}

		for (int j = maxPlat - unsplitTimes; j < maxPlat; j++) {
			String pfId = arrAllRule[j].split(":")[0];
			String deductType = arrAllRule[j].split(":")[1];
			PlatformBankEntity pl = new PlatformBankEntity();
			pl.setSysId(sid); 				// 系统ID
			pl.setPlatformId(pfId);			// 平台ID
			pl.setDeductFlag(DeductFlagType.COLLECTION.getCode());		// 划扣标识
			pl.setDeductType(deductType);		// 划扣方式
			pl.setBankId(bankId);	
			pl.setStatus("1");
			recPlat = plbs.getPlatformBank(pl);
			if (recPlat.getDayLimitMoney() != null) {
				sumDayLimit += recPlat.getDayLimitMoney();
			}
		}
		return sumDayLimit;
	}
	
	/**
	 *  将银行和平台组装在一起  2015年6月17日 By 翁私
	 * @return map
	 */
	public Map<String, String> bankPlantData() {
		Map<String,String>  map = new HashMap<String,String>();
		PlatformGotoRule entity = new PlatformGotoRule();
		BankPlantPort port = new BankPlantPort();
    	entity.setIsConcentrate(YESNO.NO.getCode());
		port.setIsConcentrate(YESNO.NO.getCode());
		entity.setStatus(YESNO.YES.getCode());
	    // 银行接口
	 	List<BankPlantPort>  pantlist   =   bankPlantPortService.findPlantList(port);
        // 跳转顺序
		List<PlatformGotoRule>  skilist   =  platformGotoRuleManager.findList(entity);
			
		for(BankPlantPort bankplant:pantlist){
			for(PlatformGotoRule plantSkip:skilist){
				StringBuffer rule = new StringBuffer();
				String[]  plants = bankplant.getPlantCode().split(",");
				String[]  ports = bankplant.getBatchFlag().split(",");
				String[]  skipplants = plantSkip.getPlatformRule().split(",");
				
				for(int i=0 ; i<skipplants.length ; i++){
					for(int j=0 ; j<plants.length ; j++){
						if(skipplants[i].equals(plants[j])){
								rule.append(plants[j]+":"+ports[j]+",");
						}
					}
				}
				String rulestring =rule.toString();
				if(!"".equals(rulestring) && rulestring != null){
					rulestring=rulestring.substring(0,rulestring.length()-1);
					map.put(bankplant.getBankCode()+""+plantSkip.getPlatformId(), rulestring);
				}
			}
		}
		return map;
	}
	
	@Autowired
	ReceiveTransferService ser;
	
	@Autowired
	ReceiveRefundInfoService refund;
	
	@Autowired
	EarlyFinishConfirmDao earlyDao;
	
	@RequestMapping(value = "test")
	public String test(Model model, HttpServletRequest request, HttpServletResponse response) {
		//划扣返回结果
		/*DjrReceiveTransferResutInParam in = new DjrReceiveTransferResutInParam();
		in.setApplyid("e50aec50952341d9b48a121e1ab09c2d");
		in.setContractCode("03110101867");
		in.setMoney(new BigDecimal("6259.04"));
		in.setResult(1);
		in.setType("1");
		in.setRemarks("成功");
		ser.updateDeductData(in);*/
		
		//退款返回结果
		/*DjrReceiveRefundInfoInParam in = new DjrReceiveRefundInfoInParam();
		in.setContractCode("03110101875");
		in.setMoney(new BigDecimal("214.00000"));
		in.setOrderId("1350ab215c03493c822f0980aa309336");
		in.setResult(1);
		in.setType("1");
		refund.updateRefundInfo(in);*/
		BigDecimal data = earlyDao.findResiduePayactual("03110101875");
		System.out.println(""+data);
		return null;
	}
	
	/**
	 * 发起还款申请有3中类型 ，然后根据申请的类型设置不同的查询参数。
	 * 
	 *  applyType 为null的时候是 普通门店提的申请
	 *            为 1 的时候电催提的申请
	 *            为 2 的时候是电销的申请
	 * @param payback
	 */
	public void setParameters(Payback payback){
		// 普通门店的数据
		if(ObjectHelper.isEmpty(payback.getApplyType())){
			// 权限控制
			String queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
			payback.setQueryRight(queryRight);
			payback.setEffectiveFlag(YESNO.YES.getCode()); 
			payback.setDictPayStatus("'" + RepayStatus.PEND_REPAYMENT.getCode() + "','" + RepayStatus.OVERDUE.getCode() + "','"
					+ RepayStatus.SETTLE_FAILED.getCode() + "','"+ RepayStatus.SETTLE_CONFIRM.getCode() + "','"
							+ RepayStatus.PRE_SETTLE.getCode() + "','"+ RepayStatus.SETTLE.getCode() + "','"		
					+ RepayStatus.PRE_SETTLE_CONFIRM.getCode()+"','"+RepayStatus.PRE_SETTLE_VERIFY.getCode()+"'");
			
			
		}
		// 电催
		if("1".equals(payback.getApplyType())){
			payback.setDictPayStatus("'" + RepayStatus.PEND_REPAYMENT.getCode() + "','" + RepayStatus.OVERDUE.getCode() + "','"
					+ RepayStatus.SETTLE_FAILED.getCode() + "','"+ RepayStatus.SETTLE_CONFIRM.getCode() + "','"
							+ RepayStatus.PRE_SETTLE.getCode() + "','"+ RepayStatus.SETTLE.getCode() + "','"		
					+ RepayStatus.PRE_SETTLE_CONFIRM.getCode()+"','"+RepayStatus.PRE_SETTLE_VERIFY.getCode()+"'");
			LoanInfo   loanInfo =new LoanInfo();
			payback.setEffectiveFlag(YESNO.YES.getCode()); 
			//loanInfo.setDictLoanStatus(LoanStatus.OVERDUE.getCode());
			//只查借款状态是否是逾期的
			payback.setLoanInfo(loanInfo);
			
		}
		//电销
		if("2".equals(payback.getApplyType())){
			payback.setPhoneSaleSign("1");// 是否电销 1 是
			payback.setEffectiveFlag(YESNO.YES.getCode()); 
			payback.setDictPayStatus("'" + RepayStatus.PEND_REPAYMENT.getCode() + "','" + RepayStatus.OVERDUE.getCode() + "','"
					+ RepayStatus.SETTLE_FAILED.getCode() + "','"+ RepayStatus.SETTLE_CONFIRM.getCode() + "','"
							+ RepayStatus.PRE_SETTLE.getCode() + "','"+ RepayStatus.SETTLE.getCode() + "','"		
					+ RepayStatus.PRE_SETTLE_CONFIRM.getCode()+"','"+RepayStatus.PRE_SETTLE_VERIFY.getCode()+"'");
			
		}
		
	}
	
	/**
	 * 判断当前门店是否已经超过了设置比例
	 * @param payback
	 * @return
	 */
	public boolean limit(Payback payback){
		String orgId = "";
		if(ObjectHelper.isEmpty(payback.getApplyType())){
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			orgId = currentUser.getDepartment().getId();
		}
		// 电催 做的数据
		if("1".equals(payback.getApplyType())){
			orgId = "zhonghedongfang";
		}
		//电销的数据
		if("2".equals(payback.getApplyType())){
			orgId = "dianxiaobu";
		}
		// 查询划扣条件
		Org org = new Org();
		org.setId(orgId);
		List<DeductPlantLimit> conditionList = applyPayback.queryDeductCondition(org);
		if(conditionList.size() == 0){
			return false;
		}
		// 查询划扣统计信息条件
		Date date = new Date();
		String dateString = format.format(date);
		DeductStatistics ts = new DeductStatistics();
		ts.setId(orgId);
		ts.setCreateDate(dateString);
		List<DeductStatistics> statisticsList = applyPayback.queryDeductStatistics(ts);
        if(statisticsList.size() == 0){
        	return false;
        }
        return isLimit(conditionList,statisticsList);
		
	}
	
	
	/**
	 * 判断 是否超过了配置的条件
	 * @param conditionList
	 * @param statisticsList
	 * @return
	 */
	public  boolean isLimit(List<DeductPlantLimit> conditionList,List<DeductStatistics> statisticsList){
		DeductPlantLimit limit  = conditionList.get(0);
		DeductStatistics  stat = statisticsList.get(0);
		if(!ObjectHelper.isEmpty(stat.getDeductNumber()) && !ObjectHelper.isEmpty(limit.getBaseNumber())){
		if(stat.getDeductNumber() > limit.getBaseNumber()){
			logger.info("【门店发起还款申请 】划扣笔数超过基数，当前笔数为:"+stat.getDeductNumber());
			if(!ObjectHelper.isEmpty(stat.getNotEnoughProportion()) && !ObjectHelper.isEmpty(limit.getNotEnoughProportion())){
				if(stat.getNotEnoughProportion().compareTo(limit.getNotEnoughProportion()) > 0){
					logger.info("【门店发起还款申请 】余额不足比例超过配置余额不足比例，当前余额不足比例为:"+stat.getNotEnoughProportion());
	                return true;				
				}
			}
			if(!ObjectHelper.isEmpty(stat.getFailureRate()) && !ObjectHelper.isEmpty(limit.getFailureRate())){
				if(stat.getFailureRate().compareTo(limit.getFailureRate()) > 0){
					logger.info("【门店发起还款申请 】失败比例超过配置失败比例，当前失败比例为:"+stat.getNotEnoughProportion());
	                return true;				
				}
			}
		 }
		}
		return false;
	}
	
}
