package com.creditharmony.loan.borrow.payback.web;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.OperateRole;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.dao.UrgeGuaranteeMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesCheckApply;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.service.UrgeGuaranteeMoneyService;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.borrow.payback.service.PaybackTransferOutService;
import com.creditharmony.loan.common.consts.SortConst;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ListSortUtil;

/**
 * 查账账款列表业务处理Controller
 * @Class Name PaybackTransferOutController
 * @author zhangfeng
 * @Create In 2015年11月9日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/zhaudited")
public class ZhAuditedController extends BaseController {

	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private DealPaybackService dealPaybackService;
	@Autowired
	private PaybackService paybackService;
	@Autowired
	private UrgeGuaranteeMoneyService urgeGuaranteeMoneyService;
	@Autowired 
	private GrantDeductsService grantDeductsService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private UrgeGuaranteeMoneyDao urgeGuaranteeMoneyDao;
	
	private static String CODE = "code";
	private static String MSG = "msg";
	private static String SUCCESS_STATUS_CODE = "0";
	private static String ERROR_STATUS_CODE = "1";
	private static String NULL_STATUS_CODE = "2";
	private static String REPEAT_STATUS_CODE = "3";
	private final static String MONTH_ZERO = "0.00";
	/**
	 * 跳转查账账款页面查询列表
	 * 2016年1月5日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param model
	 * @param payBackTransferOut
	 * @return form
	 */
	@RequestMapping(value = "list")
	public String loadAuditedList(HttpServletRequest request,HttpServletResponse response, Model model,
			PaybackTransferOut payBackTransferOut) {
		payBackTransferOut.setOperateRole(OperateRole.ZHONGHE.getCode());
		Page<PaybackTransferOut> paybackApplyPage = paybackTransferOutService.queryList(new Page<PaybackTransferOut>(request, response),payBackTransferOut);
		// 初始化入账银行
		MiddlePerson mp = new MiddlePerson();
		// 存入标识
		mp.setDepositFlag(YESNO.YES.getCode());
		mp.setWayFlag("Y");
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		for (PaybackTransferOut pt : paybackApplyPage.getList()) {
			if(StringUtils.isNotEmpty(pt.getOutAuditStatus())){
				String outAuditStatus = DictCache.getInstance().getDictLabel("jk_bankserial_check", pt.getOutAuditStatus());
				pt.setOutAuditStatusLabel(outAuditStatus);
			}
		}
		model.addAttribute("middlePersonList", mpList);
		model.addAttribute("paybackTransferOutList", paybackApplyPage);
		logger.debug("invoke AuditedController method: getAuditedList");
		return "borrow/payback/paybackflow/zhaudited";
	}
	
	/**
	 * 更新查账状态（第二版）
	 * 2016年1月5日
	 * By zhangfeng
	 * @param model
	 * @param redirectAttributes
	 * @param PaybackTransferOut
	 * @return redirect list
	 */
	@RequestMapping(value = "updateAudited")
	public String updateAudited(Model model, RedirectAttributes redirectAttributes, PaybackTransferOut out) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut paybackTransferOut = new PaybackTransferOut();
		String msg = null;
		paybackTransferOut.setId(out.getId());
		paybackTransferOut.setOperateRole(OperateRole.ZHONGHE.getCode());
		outList = paybackTransferOutService.findList(paybackTransferOut);
		if (ArrayHelper.isNotEmpty(outList)) {
			if (StringUtils.equals(out.getOutAuditStatus(), outList.get(0).getOutAuditStatus())) {
				msg = "请修改查账状态!";
			} else {
				if (StringUtils.equals(out.getOutAuditStatus(), BankSerialCheck.CHECKE_SUCCEED.getCode())) {
					
					if (StringUtils.isNotEmpty(out.getContractCode()) && StringUtils.isNotEmpty(
							String.valueOf(out.getOutTimeCheckAccount()))) {
						msg = "修改未查账，请清空合同编号和查账日期";
					}else{
						out.setrPaybackApplyId(null);
						out.setTransferAccountsId(null);
						paybackTransferOutService.updateOutStatuById(out);
						
						// 操作历史
				    	PaybackOpeEx paybackOpeTask = new PaybackOpeEx(out.getId(),null,
								RepaymentProcess.CHANGE_CHECK_STATUS, TargetWay.REPAYMENT,
								PaybackOperate.CHANGE_SUCCESS.getCode(), "手动修改查账匹配数据，查账状态为：未查账！"); 
				    	historyService.insertPaybackOpe(paybackOpeTask);
						msg = "修改为未查账!";
					}
				} else {
					if (StringUtils.isEmpty(out.getContractCode()) || StringUtils.isEmpty(
							String.valueOf(out.getOutTimeCheckAccount()))) {
						msg = "修改已查账，请填写合同编号和查账日期";
					}else{
						paybackTransferOutService.updateOutStatuById(out);
						
						PaybackOpeEx paybackOpes = new PaybackOpeEx(out.getId(), null,
								RepaymentProcess.CHANGE_CHECK_STATUS, TargetWay.REPAYMENT,
								PaybackOperate.CHANGE_SUCCESS.getCode(), "查账状态为：已查账！"); 
				    	historyService.insertPaybackOpe(paybackOpes);
						msg = "修改为已查账!";
					}
				}
			}
		}else{
			msg="您输入的合同编号无对应的银行流水";
		}
		
		addMessage(redirectAttributes, msg);
		return "redirect:" + adminPath +"/borrow/payback/zhaudited/list";
	}
	
	/**
	 * 更新查账状态(第一版)
	 * 2016年1月5日
	 * By zhangfeng
	 * @param model
	 * @param redirectAttributes
	 * @param PaybackTransferOut
	 * @return redirect list
	 */
	@RequestMapping(value = "updateAuditeds")
	public String updateAuditeds(Model model, RedirectAttributes redirectAttributes, PaybackTransferOut out) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferOut paybackTransferOut = new PaybackTransferOut();
		PaybackTransferInfo info = new PaybackTransferInfo();
		String msg = null;
		paybackTransferOut.setId(out.getId());
		outList = paybackTransferOutService.findList(paybackTransferOut);
		if (ArrayHelper.isNotEmpty(outList)) {
			if (StringUtils.equals(out.getOutAuditStatus(), outList.get(0).getOutAuditStatus())) {
				msg = "请修改查账状态!";
			} else {
				if (StringUtils.equals(out.getOutAuditStatus(), BankSerialCheck.CHECKE_SUCCEED.getCode())) {
					// 修改为未查账  判断手动查账和匹配查账
					if(StringUtils.isNotEmpty(out.getrPaybackApplyId())){
						info.setId(null);
						info.setAuditStatus("'" + BankSerialCheck.CHECKE_OVER.getCode() + "'");
						info.setrPaybackApplyId(out.getrPaybackApplyId());
						infoList = dealPaybackService.findTransfer(info);
						if (ArrayHelper.isNotEmpty(infoList)) {
							String orderNumber = null;
							for (PaybackTransferInfo in : infoList) {
								if (StringUtils.equals(in.getAuditStatus(), BankSerialCheck.CHECKE_OVER.getCode())) {
									if (!StringUtils.equals(in.getId(), out.getTransferAccountsId())) {
										List<PaybackTransferOut> oList = new ArrayList<PaybackTransferOut>();
										PaybackTransferOut po = new PaybackTransferOut();
										po.setTransferAccountsId(in.getId());
										oList = paybackTransferOutService.findList(po);
										if(ArrayHelper.isNotEmpty(oList)){
											if (orderNumber != null) {
												orderNumber += oList.get(0).getOrderNumber();
											} else {
												orderNumber = oList.get(0).getOrderNumber();
											}
										}
									}
								}
							}
							this.updateSingleAudited(out);
							if (infoList.size() > 1) {
								msg = "序号:" + out.getOrderNumber() + "修改成功!该汇款还存在其他流水,请修改序号:" + orderNumber;
							} else {
								BigDecimal bg = this.updateApplyAudited(out);
								if(bg.compareTo(BigDecimal.ZERO) > 0){
									msg = "修改成功,蓝补余额:"+String.valueOf(new java.text.DecimalFormat("#.00").format(bg))+"!";
								}else{
									msg = "修改成功,蓝补余额: 0.00";
								}
							}
						}
					}else{
						List<Payback> paybackList = new ArrayList<Payback>();
						Payback pa = new Payback();
						pa.setContractCode(out.getContractCodeTemp());
					    paybackList = paybackService.findPayback(pa);
					    if(ArrayHelper.isNotEmpty(paybackList)){
						    pa.setPaybackBuleAmount(paybackList.get(0).getPaybackBuleAmount().subtract(out.getOutReallyAmount()));
							pa.preUpdate();
							paybackService.updatePayback(pa);
							this.updateSingleAudited(out);
							msg = "修改成功,蓝补余额:"+String.valueOf(new java.text.DecimalFormat("#.00").format(paybackList.get(0).getPaybackBuleAmount().subtract(out.getOutReallyAmount())))+"!";
					    }
					}
				} else {
					// 修改为已查账
					this.updateAppyAudited(out);
					msg = "修改为已查账!";
				}
			}
		}
		addMessage(redirectAttributes, msg);
		return "redirect:" + adminPath +"/borrow/payback/zhaudited/list";
	}
	
	/**
	 * 未查帐修改为已查账
	 * 2016年1月27日
	 * By zhangfeng
	 * @param out
	 */
	private void updateAppyAudited(PaybackTransferOut out) {
		PaybackTransferOut paybackTransferOut = new PaybackTransferOut();
		paybackTransferOut.setId(out.getId());
		paybackTransferOut.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		paybackTransferOut.setContractCode(out.getContractCode());
		paybackTransferOut.setOutTimeCheckAccount(new Date());
		paybackTransferOut.setRelationType(out.getRelationType());
		paybackTransferOut.preUpdate();
		paybackTransferOutService.updateOutStatuById(paybackTransferOut);
		
		// 区分催收服务费表还是还款表
		if (StringUtils.equals(out.getRelationType(), TargetWay.SERVICE_FEE.getCode())) {
			// 修改催收主表
			UrgeServicesMoney urgeSerMoney = new UrgeServicesMoney();
			urgeSerMoney.setContractCode(out.getContractCode());
			urgeSerMoney.setAuditAmount(out.getOutReallyAmount());
			urgeSerMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
			grantDeductsService.updateUrgeByCont(urgeSerMoney);
			
			UrgeServicesMoney urgeMoney = new UrgeServicesMoney();
			urgeMoney = grantDeductsService.find(grantDeductsService.selUrgeId(out.getContractCode()));
			
			// 判断由已查账更改为未查账时，该笔单子是否在蓝补中有金额，有的话，多余的金额进蓝补
			String checkResult = grantDeductsService.selGrant(urgeMoney.getrGrantId()).getCheckResult();
			if (VerityStatus.PASS.getCode().equals(checkResult)) {
				BigDecimal bg = urgeMoney.getUrgeDecuteMoeny().add(urgeMoney.getAuditAmount()).subtract(urgeMoney.getUrgeMoeny());
				if(bg.compareTo(BigDecimal.ZERO) > 0){
					Payback payback = new Payback();
					payback.setPaybackBuleAmount(payback.getPaybackBuleAmount().subtract(bg));
					payback.preUpdate();
					paybackService.updatePayback(payback);
				}
			}
			
		}else{
			List<Payback> paybackList = new ArrayList<Payback>();
			Payback payback = new Payback();
			payback.setContractCode(out.getContractCode());
			paybackList = paybackService.findPayback(payback);
			payback.setPaybackBuleAmount(paybackList.get(0).getPaybackBuleAmount().add(out.getOutReallyAmount()));
			payback.preUpdate();
			paybackService.updatePayback(payback);
			PaybackOpeEx paybackOpes = new PaybackOpeEx(out.getId(), null,
					RepaymentProcess.CHANGE_CHECK_STATUS, TargetWay.REPAYMENT,
					PaybackOperate.CHANGE_SUCCESS.getCode(), "查账状态为：已查账！"); 
	    	historyService.insertPaybackOpe(paybackOpes);
		}
		
				
	}

	/**
	 * 修改查账状态更改蓝补
	 * 2016年1月27日
	 * By zhangfeng
	 * @param out
	 * @param applyId 
	 * @param applyId
	 * @return none
	 */
	private BigDecimal updateApplyAudited(PaybackTransferOut out) {
		List<PaybackTransferInfo> infoList = new ArrayList<PaybackTransferInfo>();
		List<Payback> paybackList = new ArrayList<Payback>();
		List<PaybackApply> applyList = new ArrayList<PaybackApply>();
		PaybackTransferInfo info = new PaybackTransferInfo();
		BigDecimal amount = new BigDecimal(MONTH_ZERO);
		BigDecimal blueAmount = BigDecimal.ZERO;
		info.setrPaybackApplyId(out.getrPaybackApplyId());
		infoList = dealPaybackService.findTransfer(info);
		if(ArrayHelper.isNotEmpty(infoList)){
			for(PaybackTransferInfo in:infoList){
				amount = amount.add(in.getReallyAmount());
			}
		}

		Payback payback = new Payback();
		payback.setContractCode(out.getContractCodeTemp());
	    paybackList = paybackService.findPayback(payback);
	    
		// 区别还款和催收查账
		if (StringUtils.equals(out.getRelationType(), TargetWay.SERVICE_FEE.getCode())) {
			
			// 更新催收申请表
			UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
			urgeApply.setId(out.getrPaybackApplyId());
			urgeApply.setUrgeReallyAmount(BigDecimal.ZERO);
			urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
			urgeApply.preUpdate();
			urgeGuaranteeMoneyService.updateUrgeApply(urgeApply);
			
			// 查询(已划金额,查账金额,催收金额) 判断是否更新蓝补
			// 根据申请表ID查询主表ID
			UrgeServicesCheckApply  urgeSer = urgeGuaranteeMoneyDao.getUrgeApplyById(urgeApply);
			if(!ObjectHelper.isEmpty(urgeSer)){
				UrgeServicesMoney urgeMoney = new UrgeServicesMoney();
				urgeMoney = grantDeductsService.find(urgeSer.getrServiceChargeId());
				BigDecimal bg = urgeMoney.getUrgeDecuteMoeny().add(urgeMoney.getAuditAmount()).subtract(urgeMoney.getUrgeMoeny());
				
				// 判断由已查账更改为未查账时，该笔单子是否在蓝补中有金额，有的话，进行修改
				String checkResult = grantDeductsService.selGrant(urgeMoney.getrGrantId()).getCheckResult();
				if (VerityStatus.PASS.getCode().equals(checkResult)) {
					if(bg.compareTo(BigDecimal.ZERO) > 0){
						payback.setPaybackBuleAmount(payback.getPaybackBuleAmount().subtract(bg));
						payback.preUpdate();
						paybackService.updatePayback(payback);
						blueAmount = payback.getPaybackBuleAmount().subtract(bg);
					}
				}

				// 修改催收服务费主表
				UrgeServicesMoney urgeSerMoney = new UrgeServicesMoney();
				urgeSerMoney.setId("'"+urgeMoney.getId()+"'");
				urgeSerMoney.setAuditAmount(urgeMoney.getAuditAmount().subtract(out.getOutReallyAmount()));
				urgeSerMoney.setDictDealStatus(UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
				grantDeductsService.updateUrge(urgeSerMoney);
			}
		} else {
			// 修改还款申请表状态和实际到账金额
			PaybackApply apply = new PaybackApply();
			apply.setId(out.getrPaybackApplyId());
			applyList = paybackService.findApplyPayback(apply);
			apply.setApplyReallyAmount(applyList.get(0).getApplyReallyAmount().subtract(amount));
			apply.setDictPaybackStatus(RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
			apply.preUpdate();
			paybackService.updatePaybackApply(apply);
			
			payback.setPaybackBuleAmount(paybackList.get(0).getPaybackBuleAmount().subtract(amount));
			payback.preUpdate();
			paybackService.updatePayback(payback);
			blueAmount = payback.getPaybackBuleAmount();
			
	    	// 操作历史
	    	PaybackOpeEx paybackOpeTask = new PaybackOpeEx(out.getrPaybackApplyId(), paybackList.get(0).getId(),
					RepaymentProcess.CHANGE_CHECK_STATUS, TargetWay.REPAYMENT,
					PaybackOperate.CHANGE_SUCCESS.getCode(), "手动修改查账匹配数据，查账状态为：未查账！"); 
	    	historyService.insertPaybackOpe(paybackOpeTask);
		}
	    return blueAmount;
	}

	/**
	 * 修改查账状态单条数据
	 * 2016年1月27日
	 * By zhangfeng
	 * @param out
	 * @param applyId
	 * @return none
	 */
	private void updateSingleAudited(PaybackTransferOut out) {
		PaybackTransferOut paybackTransferOut = new PaybackTransferOut();
		paybackTransferOut.setId(out.getId());
		paybackTransferOut.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		paybackTransferOut.setContractCode(null);
		paybackTransferOut.setTransferAccountsId(null);
		paybackTransferOut.setrPaybackApplyId(null);
		paybackTransferOut.setOutTimeCheckAccount(null);
		paybackTransferOut.preUpdate();
		paybackTransferOutService.updateOutStatuById(paybackTransferOut);

		PaybackTransferInfo info = new PaybackTransferInfo();
		info.setId(out.getTransferAccountsId());
		info.preUpdate();
		info.setAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		dealPaybackService.updateInfoStatus(info);
		
		// 流水历史
		PaybackOpeEx paybackOpes = new PaybackOpeEx(out.getId(), null,
				RepaymentProcess.CHANGE_CHECK_STATUS, TargetWay.REPAYMENT,
				PaybackOperate.CHANGE_SUCCESS.getCode(), "手动修改查账匹配数据，查账状态为：未查账！"); 
    	historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 查账导出
	 * 2016年1月5日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param audited
	 * @param out
	 * @return none
	 */
	@RequestMapping(value = "derivedAuditedExl")
	public void derivedAuditedExl(HttpServletRequest request, HttpServletResponse response, String audited,PaybackTransferOut out) {
		try {
			if (StringUtils.isNotEmpty(audited)) {
				out.setId(appendString(audited));
			}
			out.setOperateRole(OperateRole.ZHONGHE.getCode());
			ExportAuditedHelper.exportData(out, response);
		} catch (Exception e) {
			logger.debug("invoke AuditedController method: derivedAuditedExl, 查账导出有误!");
		}
	}
	
	/**
	 * 导入银行流水
	 * 2016年1月6日
	 * By zhangfeng
	 * @param file
	 * @param redirectAttributes
	 * @param confirmFlag
	 * @return redirect list
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importAuditedExl", method = RequestMethod.POST)
	public String importAuditedExl(MultipartFile file, RedirectAttributes redirectAttributes, String confrimFlag) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		List<PaybackTransferOut> insertList = new ArrayList<PaybackTransferOut>();
		ExcelUtils excelutil = new ExcelUtils();
		Map<String, String> map = new HashMap<String, String>();
		String msg = null;
		String code = null;
		String valiDateFlag = null;
		String dates = null;
		Boolean importFlag = true;
		
		// Excel数据
		try {
			outList = (List<PaybackTransferOut>) excelutil.importExcel(file, 0, 0, PaybackTransferOut.class);
			// 排序
			ListSortUtil<PaybackTransferOut> sortList = new ListSortUtil<PaybackTransferOut>();
			sortList.sort(outList, SortConst.ASC, new String[] { "outDepositTimeStr"});
			
			if (ArrayHelper.isNotEmpty(outList)) {
				for (int i = 0; i < outList.size(); i++) {
					valiDateFlag = paybackTransferOutService.checkNullForAuditedExl(outList.get(i));
					if (StringUtils.isNotEmpty(valiDateFlag)) {
						if (StringUtils.equals(valiDateFlag, "continue")) {
							outList.remove(i);
							i--;
							continue;
						} else {
							code = NULL_STATUS_CODE;
							msg = valiDateFlag;
							importFlag = false;
							break;
						}
					}

					// 类型转换
					paybackTransferOutService.importDate(outList.get(i));
					//判断导入的是否是中和-中国工商银行或中和-招商银行数据
					if(!outList.get(i).getOutEnterBankAccount().equals(OperateRole.ZHONGHEGONGSHANGBANK.getCode())
							&& !outList.get(i).getOutEnterBankAccount().equals(OperateRole.ZHONGHEZHAOSHANGBANK.getCode())){
						msg = "只能导入中和-中国工商银行及中和-招商银行数据！";
						importFlag = false;
						break;
					}

					// 序号唯一性验证
					List<PaybackTransferOut> outValiDateList = new ArrayList<PaybackTransferOut>();
					PaybackTransferOut  pto = new PaybackTransferOut();
					pto.setOrderNumber(outList.get(i).getOrderNumber());
					pto.setOperateRole(OperateRole.ZHONGHE.getCode());
					outValiDateList = paybackTransferOutService.findList(pto);
					if(ArrayHelper.isNotEmpty(outValiDateList)){
						code = NULL_STATUS_CODE;
						msg = "序号已经存在，确定是否已经导入！";
						importFlag = false;
						break;
					};
					
					// 到账日期不是同一天的数据继续导入
					if (StringUtils.equals(confrimFlag, YESNO.YES.getCode())) {
						code = SUCCESS_STATUS_CODE;
						msg = "导入成功!";
					} else {
						// 多条记录判断序号和存入日期是否重复
						if (i + 1 < outList.size()) {
							if (StringUtils.equals(outList.get(i).getOrderNumber().trim(), new BigDecimal(outList.get(i + 1).getOrderNumber()).toPlainString().trim())) {
								dates = outList.get(i).getOrderNumber();
								code = REPEAT_STATUS_CODE;
								msg = "导入的序号相同不能导入数据,序号:"+ dates;
								importFlag = false;
								break;
							}
							
							if (!StringUtils.equals(outList.get(i).getOutDepositTimeStr().trim(), outList.get(i + 1).getOutDepositTimeStr().trim())) {
								if (dates != null) {
									dates += "," + outList.get(i + 1).getOutDepositTimeStr();
								} else {
									dates = outList.get(i).getOutDepositTimeStr() + "," + outList.get(i + 1).getOutDepositTimeStr();
								}
								code = ERROR_STATUS_CODE;
								msg = "导入的到账日期不是同一天的数据,导入日期为:" + dates + ",是否继续导入?";
								importFlag = false;
								break;
							}
						} else {
							code = SUCCESS_STATUS_CODE;
							msg = "导入成功!";
						}
					}
					//标识数据为中和数据
					PaybackTransferOut out = outList.get(i);
					out.setOperateRole(OperateRole.ZHONGHE.getCode());
					insertList.add(outList.get(i));
				}
				
				if (ArrayHelper.isNotEmpty(insertList) && importFlag) {
					// 批量插入数据
					paybackTransferOutService.batchInsertAudited(insertList);
					msg = "导入成功!";
					// 删除临时文件
					File f = new File(file.getOriginalFilename());
					f.delete();
				}
			} else {
				code = NULL_STATUS_CODE;
				msg = "列表没有数据!";
			}
		} catch (Exception e) {
			code = NULL_STATUS_CODE;
			msg = e.getLocalizedMessage();
		}
		
		map.put(CODE, code);
		map.put(MSG, msg);
		return JsonMapper.nonDefaultMapper().toJson(map);
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
