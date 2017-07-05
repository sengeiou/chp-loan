package com.creditharmony.loan.borrow.payback.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.loan.type.OperateRole;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.service.MatchingPaybackService;
import com.creditharmony.loan.borrow.payback.service.OverdueManageService;
import com.creditharmony.loan.common.constants.PaybackConstants;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.service.SystemSetMaterService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.FilterHelper;
import com.creditharmony.loan.common.utils.TokenUtils;

/**
 * 待还款匹配处理Controller
 * 
 * @Class Name MatchingPaybackController
 * @author zhangfeng
 * @Create In 2015年12月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/matching")
public class MatchingPaybackController extends BaseController {

	@Autowired
	private PaybackService paybackService;

	@Autowired
	private MiddlePersonService middlePersonService;
	
	@Autowired
	private MatchingPaybackService matchingPaybackService;

	@Autowired
	private PaybackService applyPayService;

	@Autowired
	private OverdueManageService overdueManageService;

	@Autowired
	private SystemSetMaterService sys;
	
	@Autowired
	private RepaymentDateService dateService;

	/**
	 * 跳转待还款匹配页面 2016年1月5日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String goMatchingPaybackList(HttpServletRequest request,
			HttpServletResponse response, Model model, PaybackApply paybackApply,String zhcz) {
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		if(zhcz!=null && !zhcz.equals(""))
			paybackApply.setOperateRole(OperateRole.ZHONGHE.getCode());
		model.addAttribute("zhcz", zhcz);
		paybackApply.setDictPaybackStatus("'" + RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode() + "'");
		
		String storeId = null;
		LoanInfo loanInfo = paybackApply.getLoanInfo();
		if(loanInfo!=null){
			storeId=paybackApply.getLoanInfo().getLoanStoreOrgId().trim();
		}
		if(storeId!=null && !"".equals(storeId)){
			loanInfo.setLoanStoreOrgId(FilterHelper.appendIdFilter(storeId));
			paybackApply.setLoanInfo(loanInfo);
		}
		Page<PaybackApply> paybackApplyPage = paybackService.findApplyPayback(
				new Page<PaybackApply>(request, response), paybackApply);

		// 初始化入账银行
		MiddlePerson mp = new MiddlePerson();
		// 存入标识
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		
		// 查询是否开启自动匹配
		SystemSetting ss = new SystemSetting();
		ss.setSysFlag(GrantCommon.SYS_AUTO_MATCHINGS);
		if (!ObjectHelper.isEmpty(sys.get(ss))) {
			model.addAttribute("sys", sys.get(ss));
		}
		DictCache dict =  DictCache.getInstance();
		OrgCache org = OrgCache.getInstance();
		//List<PaybackApply> resultList = new ArrayList<PaybackApply>();
		if (ArrayHelper.isNotEmpty(paybackApplyPage.getList())) {
			for (PaybackApply pa : paybackApplyPage.getList()) {
				/*if(pa.getMiddlePerson().getMidBankName()!=null && !pa.getMiddlePerson().getMidBankName().equals("")
						&&((pa.getMiddlePerson().getMidBankName().equals(OperateRole.ZHONGHEGONGSHANG.getCode())&&(zhcz==null || zhcz.equals("")))
								|| (!pa.getMiddlePerson().getMidBankName().equals(OperateRole.ZHONGHEGONGSHANG.getCode())&& zhcz!=null && !zhcz.equals("")))){
					continue;
				}*/
				if (!ObjectHelper.isEmpty(pa.getPayback())) {
					if (!ObjectHelper.isEmpty(pa.getPayback().getDictPayStatus())) {
						//String dictPayStatus = dict.getDictLabel("jk_repay_status", pa.getPayback().getDictPayStatus());
						String dictPayStatus = dict.getDictLabelTemp("jk_repay_status", pa.getPayback().getDictPayStatus());
						pa.getPayback().setDictPayStatusLabel(dictPayStatus);
					}
				}

				if (!ObjectHelper.isEmpty(pa.getLoanInfo())) {
					if (!ObjectHelper.isEmpty(pa.getLoanInfo().getDictLoanStatus())) {
						String dictLoanStatus = dict.getDictLabelTemp("jk_loan_status", pa.getLoanInfo().getDictLoanStatus());
						pa.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
					}
					if (!ObjectHelper.isEmpty(pa.getLoanInfo().getLoanFlag())) {
						String loanFlag = dict.getDictLabelTemp("jk_channel_flag", pa.getLoanInfo().getLoanFlag());
						pa.getLoanInfo().setLoanFlagLabel(loanFlag);
					}
					if(StringUtils.isNotEmpty(pa.getLoanInfo().getLoanStoreOrgId())){
						// 门店
						//pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.get(pa.getLoanInfo().getLoanStoreOrgId())));
						pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.getByTypeIdAndId(pa.getLoanInfo().getLoanStoreOrgType(),pa.getLoanInfo().getLoanStoreOrgId())));
					}
					if(StringUtils.isNotEmpty(pa.getLoanInfo().getModel())){
						// 模式
						pa.getLoanInfo().setModelLabel(dict.getDictLabelTemp("jk_loan_model",pa.getLoanInfo().getModel()));
					}
					
				}
				//resultList.add(pa);
			}
			//paybackApplyPage.setList(resultList);
		}
		if(loanInfo!=null){
			loanInfo.setLoanStoreOrgId(storeId);
			paybackApply.setLoanInfo(loanInfo);
		}
		// 添加token 
		TokenUtils.removeToken(paybackApply.getMatchingTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    paybackApply.setMatchingTokenId(tokenMap.get("tokenId"));
	    paybackApply.setMatchingToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
	    
		model.addAttribute("paybackApplyList", paybackApplyPage);
		model.addAttribute("middlePersonList", mpList);
		logger.debug("invoke MatchingPaybackController method: goMatchingPaybackList, contarctCode is: "
				+ paybackApply.getContractCode());

		return "borrow/payback/paybackflow/matchingPayback";
	}

	/**
	 * 匹配批量退回 2015年12月28日 By zhangfeng
	 * 
	 * @param applyIds
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "matchingBatchBack")
	public String matchingBatchBack(String applyIds, PaybackApply pa) {
		List<PaybackApply> paList = new ArrayList<PaybackApply>();
		String msg = null;
		int backCount = 0;
		
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = pa.getMatchingTokenId();
			String curToken = pa.getMatchingToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			// 查询是否开启自动匹配
			SystemSetting ss = new SystemSetting();
			ss.setSysFlag(GrantCommon.SYS_AUTO_MATCHINGS);
			if(!ObjectHelper.isEmpty(sys.get(ss))){
				if(StringUtils.equals(sys.get(ss).getSysValue(), PaybackConstants.AUTOMATCHING)){
					msg = "数据正在自动匹配中，请关闭自动匹配！";
					return msg;
				}
			}
			if (StringUtils.isNotEmpty(applyIds)) {
				String[] id = applyIds.split(",");
				for (int i = 0; i < id.length; i++) {
					// 查询数据批量退回
					pa.setId(id[i]);
					paList = applyPayService.findApplyPayback(pa);
					if(ArrayHelper.isNotEmpty(paList)){
						boolean returnMathingFlag = matchingPaybackService.matchingSingleBack(paList.get(0));
						if(!returnMathingFlag){
							backCount++;
						}
					}
				}
			} else {
				// 查询所有查帐数据
				pa.setDictPaybackStatus("'" + RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode() + "','" + RepayApplyStatus.MATCH_FAILEN.getCode() + "'");
				paList = applyPayService.findApplyPayback(pa);
				if(ArrayHelper.isNotEmpty(paList)){
					for(PaybackApply p:paList){
						boolean returnMathingFlag = matchingPaybackService.matchingSingleBack(p);
						if(!returnMathingFlag){
							backCount++;
						}
					}
				}
			}
			if(backCount > 0){
				msg = "退回成功，退回条数：" + backCount +"！";
			}else {
				msg = "没有可以退回的数据！";
			}
		}else{
			msg = ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE;
		}
		return msg;
	}
	
	/**
	 * 跳转待还款匹配页面 2016年1月5日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return page
	 */
	@RequestMapping(value = "zhlist")
	public String goZhMatchingPaybackList(HttpServletRequest request,
			HttpServletResponse response, Model model, PaybackApply paybackApply) {
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		
		paybackApply.setOperateRole(OperateRole.ZHONGHE.getCode());
		paybackApply.setDictPaybackStatus("'" + RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode() + "'");
		Page<PaybackApply> paybackApplyPage = paybackService.findApplyPayback(
				new Page<PaybackApply>(request, response), paybackApply);

		// 初始化入账银行
		MiddlePerson mp = new MiddlePerson();
		// 存入标识
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		
		// 查询是否开启自动匹配
		SystemSetting ss = new SystemSetting();
		ss.setSysFlag(GrantCommon.SYS_AUTO_MATCHINGS);
		if (!ObjectHelper.isEmpty(sys.get(ss))) {
			model.addAttribute("sys", sys.get(ss));
		}
		DictCache dict =  DictCache.getInstance();
		OrgCache org = OrgCache.getInstance();
		List<PaybackApply> resultList = new ArrayList<PaybackApply>();
		if (ArrayHelper.isNotEmpty(paybackApplyPage.getList())) {
			for (PaybackApply pa : paybackApplyPage.getList()) {
				if(!pa.getMiddlePerson().getMidBankName().equals(OperateRole.ZHONGHEGONGSHANG.getCode())){
					continue;
				}
				if (!ObjectHelper.isEmpty(pa.getPayback())) {
					if (!ObjectHelper.isEmpty(pa.getPayback().getDictPayStatus())) {
						//String dictPayStatus = dict.getDictLabel("jk_repay_status", pa.getPayback().getDictPayStatus());
						String dictPayStatus = dict.getDictLabelTemp("jk_repay_status", pa.getPayback().getDictPayStatus());
						pa.getPayback().setDictPayStatusLabel(dictPayStatus);
					}
				}

				if (!ObjectHelper.isEmpty(pa.getLoanInfo())) {
					if (!ObjectHelper.isEmpty(pa.getLoanInfo().getDictLoanStatus())) {
						String dictLoanStatus = dict.getDictLabelTemp("jk_loan_status", pa.getLoanInfo().getDictLoanStatus());
						pa.getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
					}
					if (!ObjectHelper.isEmpty(pa.getLoanInfo().getLoanFlag())) {
						String loanFlag = dict.getDictLabelTemp("jk_channel_flag", pa.getLoanInfo().getLoanFlag());
						pa.getLoanInfo().setLoanFlagLabel(loanFlag);
					}
					if(StringUtils.isNotEmpty(pa.getLoanInfo().getLoanStoreOrgId())){
						// 门店
						//pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.get(pa.getLoanInfo().getLoanStoreOrgId())));
						pa.getLoanInfo().setLoanStoreOrgName(String.valueOf(org.getByTypeIdAndId(pa.getLoanInfo().getLoanStoreOrgType(),pa.getLoanInfo().getLoanStoreOrgId())));
					}
					if(StringUtils.isNotEmpty(pa.getLoanInfo().getModel())){
						// 模式
						pa.getLoanInfo().setModelLabel(dict.getDictLabelTemp("jk_loan_model",pa.getLoanInfo().getModel()));
					}
					
				}
				resultList.add(pa);
			}
			paybackApplyPage.setList(resultList);
		}
		// 添加token 
		TokenUtils.removeToken(paybackApply.getMatchingTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    paybackApply.setMatchingTokenId(tokenMap.get("tokenId"));
	    paybackApply.setMatchingToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
	    
		model.addAttribute("paybackApplyList", paybackApplyPage);
		model.addAttribute("middlePersonList", mpList);
		logger.debug("invoke MatchingPaybackController method: goMatchingPaybackList, contarctCode is: "
				+ paybackApply.getContractCode());
		return "borrow/payback/paybackflow/zhmatchingPayback";
	}
}
