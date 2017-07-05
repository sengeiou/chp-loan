package com.creditharmony.loan.borrow.payback.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.loan.type.Eletric;
import com.creditharmony.core.loan.type.OperateRole;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackUseService;
import com.creditharmony.loan.borrow.payback.service.ConfirmPaybackService;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.borrow.payback.service.DoStoreService;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanDeductService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.TokenUtils;

/**
 * 门店待办业务处理Controller
 * 
 * @Class Name DoStoreController
 * @author zhangfeng
 * @Create In 2015年12月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/doStore")
public class DoStoreController extends BaseController {

	@Autowired
	private DoStoreService doStoreService;
	@Autowired
	private PaybackService applyPayService;
	@Autowired
	private DealPaybackService dealPaybackService;
	@Autowired
	private LoanDeductService loanDeductService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private ApplyPaybackUseService applyPaybackUseService;
	@Autowired
	private ConfirmPaybackService confirmPaybackService;
	@Autowired
	private HistoryService historyService;

	/**
	 * 跳转门店待办页面 2016年1月6日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param paybackApply
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String goDoStoreList(HttpServletRequest request,
			HttpServletResponse response, PaybackApply paybackApply, Model model) {
		paybackApply.setDictPaybackStatus("'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'"+","+"'"+RepayApplyStatus.TO_PAYMENT.getCode()+"'");
		Page<PaybackApply> page = new Page<PaybackApply>(request, response);
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSize)){
			int ps = Integer.parseInt(pageSize);
			if(ps!=0){
				page.setPageSize(ps);
			}
		}
		if(StringUtils.isNotEmpty(pageNo)){
			int pn = Integer.parseInt(pageNo);
			page.setPageNo(pn);
		}
		/*if(zhcz!=null && !zhcz.equals(""))
			paybackApply.setOperateRole(OperateRole.ZHONGHE.getCode());*/
		//  数据权限控制
		String  queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
		paybackApply.setQueryRight(queryRight);
		Page<PaybackApply> paybackApplyPage = applyPayService.findReturnApplyPayback(page, paybackApply);
		OrgCache org = OrgCache.getInstance();
		DictCache dict = DictCache.getInstance();
		for (PaybackApply pa : paybackApplyPage.getList()) {
			// 门店
			if (!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getLoanStoreOrgId())) {
				pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.get(pa.getLoanInfo().getLoanStoreOrgId())));
			}
			//借款状态
			if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getDictLoanStatus())){
				pa.getLoanInfo().setDictLoanStatusLabel(dict.getDictLabel("jk_loan_apply_status",pa.getLoanInfo().getDictLoanStatus()));
			}
			//还款类型
			if(StringUtils.isNotEmpty(pa.getDictPayUse())){
				pa.setDictPayUseLabel(dict.getDictLabel("jk_repay_type", pa.getDictPayUse()));
			}
			//还款状态
			if(!ObjectHelper.isEmpty(pa.getPayback()) && StringUtils.isNotEmpty(pa.getPayback().getDictPayStatus())){
				pa.getPayback().setDictPayStatusLabel(dict.getDictLabel("jk_repay_status", pa.getPayback().getDictPayStatus()));
			}
			// 还款方式
			if(StringUtils.isNotEmpty(pa.getDictRepayMethod())){
				pa.setDictRepayMethodLabel(dict.getDictLabel("jk_repay_way", pa.getDictRepayMethod()));
			}
			//回盘结果
			if(StringUtils.isNotEmpty(pa.getSplitBackResult())){
				pa.setSplitBackResultLabel(dict.getDictLabel("jk_counteroffer_result",pa.getSplitBackResult()));
			}
			//标识
			if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getLoanFlag())){
				pa.getLoanInfo().setLoanFlagLabel(dict.getDictLabel("jk_channel_flag",pa.getLoanInfo().getLoanFlag()));
			}
			//是否电销
			if(!ObjectHelper.isEmpty(pa.getLoanCustomer()) && StringUtils.isNotEmpty(pa.getLoanCustomer().getCustomerTelesalesFlag())){
				pa.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getDictLabel("jk_telemarketing",pa.getLoanCustomer().getCustomerTelesalesFlag()));
			}
			
			// 模式
			if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getModel())){
				String dictLoanModel = dict.getDictLabel("jk_loan_model",pa.getLoanInfo().getModel());
				pa.getLoanInfo().setModelLabel(dictLoanModel);
			}
		}
		
		model.addAttribute("page", page);
		model.addAttribute("paybackApplyList", paybackApplyPage);
		//model.addAttribute("zhcz",zhcz);
		return "borrow/payback/paybackflow/doStore";
	}

	
	/**
	 * 跳转电销门店待办页面 2016年1月6日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param paybackApply
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "phoneSaleList")
	public String phoneSaleStoreList(HttpServletRequest request,
			HttpServletResponse response, PaybackApply paybackApply, Model model) {
		// 1标示电销是
		paybackApply.setPhoneSaleSign("1");
		paybackApply.setDictPaybackStatus("'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'"+","+"'"+RepayApplyStatus.TO_PAYMENT.getCode()+"'");
		Page<PaybackApply> page = new Page<PaybackApply>(request, response);
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSize)){
			int ps = Integer.parseInt(pageSize);
			if(ps!=0){
				page.setPageSize(ps);
			}
		}
		if(StringUtils.isNotEmpty(pageNo)){
			int pn = Integer.parseInt(pageNo);
			page.setPageNo(pn);
		}
		/*if(zhcz!=null && !zhcz.equals(""))
			paybackApply.setOperateRole(OperateRole.ZHONGHE.getCode());*/
		//  数据权限控制
		Page<PaybackApply> paybackApplyPage = applyPayService.findReturnApplyPayback(page, paybackApply);
		OrgCache org = OrgCache.getInstance();
		DictCache dict = DictCache.getInstance();
		for (PaybackApply pa : paybackApplyPage.getList()) {
			// 门店
			if (!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getLoanStoreOrgId())) {
				pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.get(pa.getLoanInfo().getLoanStoreOrgId())));
			}
			//借款状态
			if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getDictLoanStatus())){
				pa.getLoanInfo().setDictLoanStatusLabel(dict.getDictLabel("jk_loan_apply_status",pa.getLoanInfo().getDictLoanStatus()));
			}
			//还款类型
			if(StringUtils.isNotEmpty(pa.getDictPayUse())){
				pa.setDictPayUseLabel(dict.getDictLabel("jk_repay_type", pa.getDictPayUse()));
			}
			//还款状态
			if(!ObjectHelper.isEmpty(pa.getPayback()) && StringUtils.isNotEmpty(pa.getPayback().getDictPayStatus())){
				pa.getPayback().setDictPayStatusLabel(dict.getDictLabel("jk_repay_status", pa.getPayback().getDictPayStatus()));
			}
			// 还款方式
			if(StringUtils.isNotEmpty(pa.getDictRepayMethod())){
				pa.setDictRepayMethodLabel(dict.getDictLabel("jk_repay_way", pa.getDictRepayMethod()));
			}
			//回盘结果
			if(StringUtils.isNotEmpty(pa.getSplitBackResult())){
				pa.setSplitBackResultLabel(dict.getDictLabel("jk_counteroffer_result",pa.getSplitBackResult()));
			}
			//标识
			if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getLoanFlag())){
				pa.getLoanInfo().setLoanFlagLabel(dict.getDictLabel("jk_channel_flag",pa.getLoanInfo().getLoanFlag()));
			}
			//是否电销
			if(!ObjectHelper.isEmpty(pa.getLoanCustomer()) && StringUtils.isNotEmpty(pa.getLoanCustomer().getCustomerTelesalesFlag())){
				pa.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getDictLabel("jk_telemarketing",pa.getLoanCustomer().getCustomerTelesalesFlag()));
			}
			
			// 模式
			if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getModel())){
				String dictLoanModel = dict.getDictLabel("jk_loan_model",pa.getLoanInfo().getModel());
				pa.getLoanInfo().setModelLabel(dictLoanModel);
			}
		}
		
		model.addAttribute("page", page);
		model.addAttribute("paybackApplyList", paybackApplyPage);
		//model.addAttribute("zhcz",zhcz);
		return "borrow/payback/paybackflow/doStorePhoneSale";
	}

	/**
	 * 门店待办跳转详细页面 2016年1月6日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param paybackApply
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "form")
	public String goDoStoreInfoForm(HttpServletRequest request, HttpServletResponse response, 
			PaybackApply paybackApply, Model model,String zhcz) {
		List<PaybackApply> paybackApplyList = new ArrayList<PaybackApply>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		// 初始化存入银行下拉框
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		
		if (ArrayHelper.isNotEmpty(mpList)) {
			model.addAttribute("middlePersonList", mpList);
		} else {
			model.addAttribute("middlePersonList", null);
		}
		paybackApplyList = applyPayService.findReturnApplyPayback(paybackApply);
		if (StringUtils.equals(paybackApplyList.get(0).getDictPayUse(),
				RepayType.EARLY_SETTLE.getCode())) {
			PaybackMonth paybackMonth = new PaybackMonth();
			paybackMonth.setContractCode(paybackApplyList.get(0).getContractCode());
			paybackMonth.setMonths(1);
			Contract contract = paybackApplyList.get(0).getContract();
			String contractVersion = "";
			if(!ObjectHelper.isEmpty(contract)){
				contractVersion = contract.getContractVersion();
			}
			paybackApplyList.get(0).setPaybackMonth(confirmPaybackService.findDefaultConfirmInfo(paybackMonth,contractVersion));
		}
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			if (paybackApplyList.get(0).getDictRepayMethod().equals(RepayChannel.NETBANK_CHARGE.getCode())) {
				PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
				paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getId());
				paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
				if(ArrayHelper.isNotEmpty(paybackTransferInfoList)){
					for (int i = 0; i < paybackTransferInfoList.size(); i++) {
						// 上传人
						String uploadId = paybackTransferInfoList.get(i).getUploadName();
						if(StringUtils.isNotEmpty(uploadId)){
							User uploadUser = UserUtils.get(uploadId);
							if(!ObjectHelper.isEmpty(uploadUser)){
								String uploadName = uploadUser.getName();
								paybackTransferInfoList.get(i).setUploadName(uploadName);
							}
						}
					}
				}
			} else {
				PaybackTransferInfo PaybackTransferInfo = new PaybackTransferInfo();
				PaybackTransferInfo.setUploadName(UserUtils.getUser().getName());
				PaybackTransferInfo.setUploadDate(new Date());
				paybackTransferInfoList.add(PaybackTransferInfo);
			}
			
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanInfo()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanInfo().getDictLoanStatus())){
				String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",paybackApplyList.get(0).getLoanInfo().getDictLoanStatus());
				paybackApplyList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
			}
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanBank()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanBank().getBankName())){
				String bankNameLabel=DictCache.getInstance().getDictLabel("jk_open_bank",paybackApplyList.get(0).getLoanBank().getBankName());
				paybackApplyList.get(0).getLoanBank().setBankNameLabel(bankNameLabel);
			}
		}
		// 添加token 
		TokenUtils.removeToken(paybackApply.getDoStoreTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    paybackApplyList.get(0).setDoStoreTokenId(tokenMap.get("tokenId"));
	    paybackApplyList.get(0).setDoStoreToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		
		model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
		model.addAttribute("paybackApply", paybackApplyList.get(0));
		model.addAttribute("zhcz",zhcz);
		return "borrow/payback/paybackflow/doStoreInfo";
	}
	
	
	/**
	 * 电销门店待办跳转详细页面 2017年3月2日 By 翁私
	 * 
	 * @param request
	 * @param response
	 * @param paybackApply
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "phoneSaleForm")
	public String phoneSaleForm(HttpServletRequest request, HttpServletResponse response, 
			PaybackApply paybackApply, Model model,String zhcz) {
		List<PaybackApply> paybackApplyList = new ArrayList<PaybackApply>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		// 初始化存入银行下拉框
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		
		if (ArrayHelper.isNotEmpty(mpList)) {
			model.addAttribute("middlePersonList", mpList);
		} else {
			model.addAttribute("middlePersonList", null);
		}
		paybackApplyList = applyPayService.findReturnApplyPayback(paybackApply);
		if (StringUtils.equals(paybackApplyList.get(0).getDictPayUse(),
				RepayType.EARLY_SETTLE.getCode())) {
			PaybackMonth paybackMonth = new PaybackMonth();
			paybackMonth.setContractCode(paybackApplyList.get(0).getContractCode());
			paybackMonth.setMonths(1);
			Contract contract = paybackApplyList.get(0).getContract();
			String contractVersion = "";
			if(!ObjectHelper.isEmpty(contract)){
				contractVersion = contract.getContractVersion();
			}
			paybackApplyList.get(0).setPaybackMonth(confirmPaybackService.findDefaultConfirmInfo(paybackMonth,contractVersion));
		}
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			if (paybackApplyList.get(0).getDictRepayMethod().equals(RepayChannel.NETBANK_CHARGE.getCode())) {
				PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
				paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getId());
				paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
				if(ArrayHelper.isNotEmpty(paybackTransferInfoList)){
					for (int i = 0; i < paybackTransferInfoList.size(); i++) {
						// 上传人
						String uploadId = paybackTransferInfoList.get(i).getUploadName();
						if(StringUtils.isNotEmpty(uploadId)){
							User uploadUser = UserUtils.get(uploadId);
							if(!ObjectHelper.isEmpty(uploadUser)){
								String uploadName = uploadUser.getName();
								paybackTransferInfoList.get(i).setUploadName(uploadName);
							}
						}
					}
				}
			} else {
				PaybackTransferInfo PaybackTransferInfo = new PaybackTransferInfo();
				PaybackTransferInfo.setUploadName(UserUtils.getUser().getName());
				PaybackTransferInfo.setUploadDate(new Date());
				paybackTransferInfoList.add(PaybackTransferInfo);
			}
			
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanInfo()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanInfo().getDictLoanStatus())){
				String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",paybackApplyList.get(0).getLoanInfo().getDictLoanStatus());
				paybackApplyList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
			}
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanBank()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanBank().getBankName())){
				String bankNameLabel=DictCache.getInstance().getDictLabel("jk_open_bank",paybackApplyList.get(0).getLoanBank().getBankName());
				paybackApplyList.get(0).getLoanBank().setBankNameLabel(bankNameLabel);
			}
		}
		// 添加token 
		TokenUtils.removeToken(paybackApply.getDoStoreTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    paybackApplyList.get(0).setDoStoreTokenId(tokenMap.get("tokenId"));
	    paybackApplyList.get(0).setDoStoreToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		
		model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
		model.addAttribute("paybackApply", paybackApplyList.get(0));
		model.addAttribute("zhcz",zhcz);
		return "borrow/payback/paybackflow/doStoreInfoPhoneSale";
	}

	/**
	 * 门店待办保存和放弃 2015年12月23日 By zhangfeng
	 * 
	 * @param paybackApply
	 * @param model
	 * @param files
	 * @param redirectAttributes
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public String saveDoStore(@RequestParam("files")MultipartFile[] files, PaybackApply pa, 
			Model model, RedirectAttributes redirectAttributes) {
		Map<String, String> map = new HashMap<String, String>();
		String msg = null;
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = pa.getDoStoreTokenId();
			String curToken = pa.getDoStoreToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			if (StringUtils.equals(pa.getDictPaybackStatus(), RepayApplyStatus.REPAYMENT_GIVEUP.getCode())) {
				// 还款放弃
				msg = doStoreService.giveUpPayback(pa);
			} else {
				if (StringUtils.equals(pa.getDictRepayMethod(), RepayChannel.NETBANK_CHARGE.getCode())) {
						// 同一合同编号，存入银行重复验证
						String storesInAccountMsg = doStoreService.findTransferInfoByStoresInAccount(pa);
						if(StringUtils.isNotEmpty(storesInAccountMsg)){
							pushMap(map, YESNO.YES.getCode(), storesInAccountMsg);
							return JsonMapper.nonDefaultMapper().toJson(map);
						}	
						
					// 不是同一合同编号 汇款单重复验证
					if (StringUtils.equals(pa.getConfrimFlag(), YESNO.NO.getCode())) {
						String infoMsg = doStoreService.findPayBackTransferOut(pa.getPaybackTransferInfo());
						if (StringUtils.isNotEmpty(infoMsg)) {
							pushMap(map, YESNO.NO.getCode(), infoMsg);
							return JsonMapper.nonDefaultMapper().toJson(map);
						}
					}
					// 汇款转账
					msg = doStoreService.transferPayback(pa, files);
				} else {
					// 划扣
					msg = doStoreService.deductPayback(pa);
				}
			}
		}else{
			msg = ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE;
		}
		pushMap(map, "sus", msg);
		return JsonMapper.nonDefaultMapper().toJson(map);
	}
	
		/**
		 * 电催待办页面 2016年2月25日 By liushikang
		 * 
		 * @param request
		 * @param response
		 * @param paybackApply
		 * @param model            findApplyElert
		 * @return page 	paybackApply.setUrgeManage(Eletric.ELETRIC.getCode());
		 */
		@RequestMapping(value = "listElert")
		public String goDoStorelistElert(HttpServletRequest request,
				HttpServletResponse response, PaybackApply paybackApply, Model model) {
			paybackApply.setUrgeManage(Eletric.ELETRIC.getCode());
			paybackApply.setDictPaybackStatus("'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'"+","+"'"+RepayApplyStatus.TO_PAYMENT.getCode()+"'");
			Page<PaybackApply> page = new Page<PaybackApply>(request, response);
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			if(StringUtils.isNotEmpty(pageSize)){
				int ps = Integer.parseInt(pageSize);
				if(ps!=0){
					page.setPageSize(ps);
				}
			}
			if(StringUtils.isNotEmpty(pageNo)){
				int pn = Integer.parseInt(pageNo);
				page.setPageNo(pn);
			}
			Page<PaybackApply> paybackApplyPage = applyPayService.findApplyElert(
					page, paybackApply);
			if (!ObjectHelper.isEmpty(paybackApplyPage.getList())) {
				for (int i = 0; i < paybackApplyPage.getList().size(); i++) {
					if (!ObjectHelper.isEmpty(paybackApplyPage.getList().get(i).getLoanInfo())
							&& StringUtils.isNotEmpty(paybackApplyPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())) {
						// 门店
					paybackApplyPage.getList().get(i).getLoanInfo().setLoanStoreOrgName(
										String.valueOf(OrgCache.getInstance().get(paybackApplyPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())));
					}
				}
			}
			
			
			List<PaybackApply> pageList = paybackApplyPage.getList();
			for (PaybackApply paybackList : pageList) {
				//还款类型
				paybackList.setDictPayUseLabel(DictCache.getInstance().getDictLabel("jk_repay_type", paybackList.getDictPayUse()));
			    //还款状态
				paybackList.getPayback().setDictPayStatusLabel(DictCache.getInstance().getDictLabel("jk_repay_status", paybackList.getPayback().getDictPayStatus()));
				//会盘结果
				paybackList.setSplitBackResultLabel(DictCache.getInstance().getDictLabel("jk_counteroffer_result", paybackList.getSplitBackResult()));
				//借款状态
				paybackList.getLoanInfo().setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_status", paybackList.getLoanInfo().getDictLoanStatus()));
				//是否电销
				paybackList.getLoanCustomer().setCustomerTelesalesFlagLabel(DictCache.getInstance().getDictLabel("jk_telemarketing",paybackList.getLoanCustomer().getCustomerTelesalesFlag()));
				//标识
				paybackList.getLoanInfo().setLoanFlagLabel(DictCache.getInstance().getDictLabel("jk_counteroffer_result",paybackList.getLoanInfo().getLoanFlag()));
			}
			model.addAttribute("page", page);
			model.addAttribute("paybackApplyList", paybackApplyPage);
			return "borrow/payback/paybackflow/doStorelistElert";
		}
		
		/**
		 * 电催待办跳转详细页面 2016年2月25日 By liushikang
		 * 
		 * @param request
		 * @param response
		 * @param paybackApply
		 * @param model
		 * @return page
		 */
		@RequestMapping(value = "formElert")
		public String goDoStoreformElert(HttpServletRequest request, HttpServletResponse response, PaybackApply paybackApply, Model model) {
			List<PaybackApply> paybackApplyList = new ArrayList<PaybackApply>();
			List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
			// 初始化存入银行下拉框
			Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
			if (ArrayHelper.isNotEmpty(middlePersonPage.getList())) {
				model.addAttribute("middlePersonList", middlePersonPage.getList());
			} else {
				model.addAttribute("middlePersonList", null);
			}
			paybackApplyList = applyPayService.findApplyPayback(paybackApply);
			Contract contract = paybackApplyList.get(0).getContract();
			String contractVersion = "";
			if(!ObjectHelper.isEmpty(contract)){
				contractVersion = contract.getContractVersion();
			}
			if (StringUtils.equals(paybackApplyList.get(0).getDictPayUse(),
					RepayType.EARLY_SETTLE.getCode())) {
				
				PaybackMonth paybackMonth = new PaybackMonth();
				paybackMonth.setContractCode(paybackApplyList.get(0).getContractCode());
				paybackMonth.setMonths(1);
				paybackApplyList.get(0).setPaybackMonth(confirmPaybackService.findDefaultConfirmInfo(paybackMonth,contractVersion));
			}
			if (ArrayHelper.isNotEmpty(paybackApplyList)) {
				PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
				paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getId());
				paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
				if (paybackApplyList.get(0).getDictRepayMethod().equals(RepayChannel.NETBANK_CHARGE.getCode())) {
					model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
				} else {
					PaybackTransferInfo PaybackTransferInfo = new PaybackTransferInfo();
					PaybackTransferInfo.setUploadName(UserUtils.getUser().getName());
					PaybackTransferInfo.setUploadDate(new Date());
					paybackTransferInfoList.add(PaybackTransferInfo);
					model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
				}
				logger.debug("invoke ConfirmPaybackController method: list, contarctCode is: "
						+ paybackApplyList.get(0).getContractCode());
			} else {
				logger.debug("invoke ConfirmPaybackController method: gotoConfirmInfo, paybackList is null");
			}
			
			String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",paybackApplyList.get(0).getLoanInfo().getDictLoanStatus());
			paybackApplyList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
			model.addAttribute("paybackApply", paybackApplyList.get(0));
			return "borrow/payback/paybackflow/doStoreElertInfo";
		}
		
		/**
		 * 电催待办保存和放弃 2016年2月25日 By liushikang
		 * 
		 * @param paybackApply
		 * @param model
		 * @param files
		 * @return redirect page
		 */
		@RequestMapping(value = "saveEelert")
		public String saveEelert(@RequestParam("files")MultipartFile[] files, PaybackApply paybackApply, Model model) {
			if (StringUtils.equals(paybackApply.getDictPaybackStatus(), RepayApplyStatus.REPAYMENT_GIVEUP.getCode())) {
				// 还款放弃
				paybackApply.setDictPaybackStatus(RepayApplyStatus.REPAYMENT_GIVEUP.getCode());
				paybackApply.preUpdate();
				applyPayService.updatePaybackApply(paybackApply);

				PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApply.getId(), null,
						RepaymentProcess.DEDECT, TargetWay.PAYMENT, "还款放弃!", "合同编号:"
								+ paybackApply.getContractCode());
				historyService.insertPaybackOpe(paybackOpes);
			} else {
				// 重新发起申请
				if (StringUtils.equals(paybackApply.getDictRepayMethod(), RepayChannel.NETBANK_CHARGE.getCode())) {
					// 汇款转账
					paybackApply.setDictPaybackStatus(RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
					LoanBank loanBank = new LoanBank();
					paybackApply.setLoanBank(loanBank);
					paybackApply.setDictDealType(null);
					paybackApply.setDictRepayMethod(RepayChannel.NETBANK_CHARGE.getCode());
					paybackApply.setApplyAmount(paybackApply.getTransferAmount());
					paybackApply.setDictPaybackStatus(RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
					doStoreService.deleteTransferInfo(paybackApply);
					doStoreService.updateTransferInfo(paybackApply,files);
				} else {
					// 划扣
					paybackApply.setDictPaybackStatus(RepayApplyStatus.PRE_PAYMENT.getCode());
					paybackApply.setPaybackTransferInfo(null);
					paybackApply.setApplyAmount(paybackApply.getDeductAmount());
					paybackApply.setDictRepayMethod(RepayChannel.DEDUCT.getCode());
					paybackApply.setDictPaybackStatus(RepayApplyStatus.PRE_PAYMENT.getCode());
					if (StringUtils.isNotEmpty(paybackApply.getLoanBank().getNewId())) {
						// 更新划扣账号
						paybackApply.getLoanBank().setBankTopFlag(PaybackApply.TOP_FLAG_NO);
						paybackApply.getLoanBank().preUpdate();
						LoanBank loanBank = new LoanBank();
						loanBank.setId(paybackApply.getLoanBank().getNewId());
						loanBank.setBankTopFlag(PaybackApply.TOP_FLAG);
						loanBank.preUpdate();
						loanDeductService.updateTopFlag(paybackApply.getLoanBank());
						loanDeductService.updateTopFlag(loanBank);
					}
				}
				paybackApply.preUpdate();
				doStoreService.updateApplyPayback(paybackApply);

				PaybackOpeEx paybackOpes = new PaybackOpeEx(paybackApply.getId(),
						paybackApply.getPayback().getId(),
						RepaymentProcess.REPAYMENT_APPLY, TargetWay.REPAYMENT,
						PaybackOperate.APPLY_SUCEED.getCode(), "重新发起还款，申请金额："
								+ paybackApply.getApplyAmount());
				historyService.insertPaybackOpe(paybackOpes);
			}
			return "redirect:" + adminPath + "/borrow/payback/doStore/listElert";
		}
		
		/**
		 * 从新获取token 2015年12月12日 By zhangfeng
		 * 
		 * @param loanCode
		 * @return json
		 */
		@ResponseBody
		@RequestMapping(value = "getdoStoreToken", method = RequestMethod.POST)
		public String getApplyToken(PaybackApply pa) {
			// 添加doStoreToken 
			Map<String, String> map = new HashMap<String, String>();
			TokenUtils.removeToken(pa.getDoStoreTokenId());
		    Map<String,String> tokenMap = TokenUtils.createToken();
		    pushTokenMap(map, tokenMap.get("tokenId"), tokenMap.get("token"));
		    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
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
	  private void pushTokenMap(Map<String, String> map, String id, String token) {
			map.put("id", id);
			map.put("token", token);
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
		 * 跳转门店待办页面 2016年1月6日 By zhangfeng
		 * 
		 * @param request
		 * @param response
		 * @param paybackApply
		 * @param model
		 * @return page
		 */
		@RequestMapping(value = "zhlist")
		public String zhgoDoStoreList(HttpServletRequest request,
				HttpServletResponse response, PaybackApply paybackApply, Model model) {
			paybackApply.setDictPaybackStatus("'"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"'"+","+"'"+RepayApplyStatus.TO_PAYMENT.getCode()+"'");
			Page<PaybackApply> page = new Page<PaybackApply>(request, response);
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			if(StringUtils.isNotEmpty(pageSize)){
				int ps = Integer.parseInt(pageSize);
				if(ps!=0){
					page.setPageSize(ps);
				}
			}
			if(StringUtils.isNotEmpty(pageNo)){
				int pn = Integer.parseInt(pageNo);
				page.setPageNo(pn);
			}
			//  数据权限控制
			String  queryRight = DataScopeUtil.getDataScope("li", SystemFlag.LOAN.value);
			paybackApply.setQueryRight(queryRight);
			paybackApply.setOperateRole(OperateRole.ZHONGHE.getCode());
			Page<PaybackApply> paybackApplyPage = applyPayService.findApplyPayback(page, paybackApply);
			OrgCache org = OrgCache.getInstance();
			DictCache dict = DictCache.getInstance();
			for (PaybackApply pa : paybackApplyPage.getList()) {
				// 门店
				if (!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getLoanStoreOrgId())) {
					pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.get(pa.getLoanInfo().getLoanStoreOrgId())));
				}
				//借款状态
				if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getDictLoanStatus())){
					pa.getLoanInfo().setDictLoanStatusLabel(dict.getDictLabel("jk_loan_apply_status",pa.getLoanInfo().getDictLoanStatus()));
				}
				//还款类型
				if(StringUtils.isNotEmpty(pa.getDictPayUse())){
					pa.setDictPayUseLabel(dict.getDictLabel("jk_repay_type", pa.getDictPayUse()));
				}
				//还款状态
				if(!ObjectHelper.isEmpty(pa.getPayback()) && StringUtils.isNotEmpty(pa.getPayback().getDictPayStatus())){
					pa.getPayback().setDictPayStatusLabel(dict.getDictLabel("jk_repay_status", pa.getPayback().getDictPayStatus()));
				}
				// 还款方式
				if(StringUtils.isNotEmpty(pa.getDictRepayMethod())){
					pa.setDictRepayMethodLabel(dict.getDictLabel("jk_repay_way", pa.getDictRepayMethod()));
				}
				//回盘结果
				if(StringUtils.isNotEmpty(pa.getSplitBackResult())){
					pa.setSplitBackResultLabel(dict.getDictLabel("jk_counteroffer_result",pa.getSplitBackResult()));
				}
				//标识
				if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getLoanFlag())){
					pa.getLoanInfo().setLoanFlagLabel(dict.getDictLabel("jk_channel_flag",pa.getLoanInfo().getLoanFlag()));
				}
				//是否电销
				if(!ObjectHelper.isEmpty(pa.getLoanCustomer()) && StringUtils.isNotEmpty(pa.getLoanCustomer().getCustomerTelesalesFlag())){
					pa.getLoanCustomer().setCustomerTelesalesFlagLabel(dict.getDictLabel("jk_telemarketing",pa.getLoanCustomer().getCustomerTelesalesFlag()));
				}
				
				// 模式
				if(!ObjectHelper.isEmpty(pa.getLoanInfo()) && StringUtils.isNotEmpty(pa.getLoanInfo().getModel())){
					String dictLoanModel = dict.getDictLabel("jk_loan_model",pa.getLoanInfo().getModel());
					pa.getLoanInfo().setModelLabel(dictLoanModel);
				}
			}
			
			model.addAttribute("page", page);
			model.addAttribute("paybackApplyList", paybackApplyPage);
			return "borrow/payback/paybackflow/zhdoStore";
		}
}
