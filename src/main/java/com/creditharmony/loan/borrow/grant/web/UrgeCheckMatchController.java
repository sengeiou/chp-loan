package com.creditharmony.loan.borrow.grant.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.file.util.FileUtil;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.grant.dto.ManualMatchNotify;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesCheckApply;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.service.UrgeGuaranteeMoneyService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.service.PaybackTransferOutService;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 催收服务费查账匹配列表中的各种操作
 * @Class Name UrgeCheckMatchController
 * @author 张振强
 * @Create In 2016年1月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/urgeCheckMatch")
public class UrgeCheckMatchController extends BaseController {
	
	@Autowired
	private UrgeGuaranteeMoneyService urgeGuaranteeMoneyService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	@Autowired 
	private GrantDeductsService grantDeductsService;
	@Autowired
	private PaybackService applyPayService;
	@Autowired 
	private PaybackBlueAmountService blusAmountService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PaybackDao paybackDao;

	/**
	 * 查账匹配列表
	 * 2016年8月18日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param model
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	@RequestMapping(value = "urgeCheckMatchInfo")
	public String urgeCheckMatchInfo(HttpServletRequest request, HttpServletResponse response, Model model, UrgeServicesMoneyEx urgeServicesMoneyEx){
	    GrantUtil.setStoreId(urgeServicesMoneyEx);
	    Page<UrgeServicesMoneyEx> paybackApplyPage = urgeGuaranteeMoneyService
				.selCheckInfo(new Page<UrgeServicesMoneyEx>(request, response), urgeServicesMoneyEx);
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		List<UrgeServicesMoneyEx> paybackList = paybackApplyPage.getList();
		if (ArrayHelper.isNotEmpty(paybackList)) {
			Map<String, Dict> dictMap = DictCache.getInstance().getMap();
			ChannelFlag cf = null;
			LoanApplyStatus las = null;
			for (UrgeServicesMoneyEx paybackEx : paybackList) {
				if (StringUtils.isNotEmpty(paybackEx.getLoanFlag())) {
				     cf = ChannelFlag.parseByCode(paybackEx.getLoanFlag());
				     paybackEx.setLoanFlagLabel(cf.getName());
				}
				if (StringUtils.isNotEmpty(paybackEx.getDictLoanStatus())) {
					las = LoanApplyStatus.parseByCode(paybackEx.getDictLoanStatus());
					paybackEx.setDictLoanStatusLabel(las.getName());
				}
				if (StringUtils.isNotEmpty(paybackEx.getName())) {
					// 门店
					paybackEx.setName(String.valueOf(OrgCache.getInstance().get(paybackEx.getName())));
					paybackEx.setLoanFlag(DictUtils.getLabel(dictMap,
									LoanDictType.CHANNEL_FLAG,
									paybackEx.getLoanFlag()));
				}
			}
		}
		// 获得汇金产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		paybackApplyPage.setList(paybackList);
		model.addAttribute("urgeServicesMoneyEx", urgeServicesMoneyEx);
		model.addAttribute("paybackApplyList", paybackApplyPage);
		model.addAttribute("middlePersonList", mpList);
		model.addAttribute("productList", productList);
		return "borrow/grant/urgeMatching";
	}
	
	/**
	 * 点击办理，进入办理页面
	 * 2016年8月18日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param model
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	@RequestMapping(value = "form")
	public String goUrgeCheckDealForm(HttpServletRequest request,
			HttpServletResponse response, Model model,
			UrgeServicesMoneyEx urgeServicesMoneyEx) {
		List<UrgeServicesMoneyEx> paybackApplyList = new ArrayList<UrgeServicesMoneyEx>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		Map<String, Dict> dictMap = DictCache.getInstance().getMap();
		paybackApplyList = urgeGuaranteeMoneyService.selCheckInfo(
				new Page<UrgeServicesMoneyEx>(request, response),
				urgeServicesMoneyEx).getList();
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			// 设置转账表的关联id，为催收主表的催收id
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getUrgeServicesCheckApply().getId());
			paybackTransferInfo.setRelationType(TargetWay.SERVICE_FEE.getCode());
			paybackTransferInfoList = urgeGuaranteeMoneyService.findUrgeTransfer(paybackTransferInfo);
			if (ArrayHelper.isNotEmpty(paybackTransferInfoList)) {
				for (int i = 0; i < paybackTransferInfoList.size(); i++) {
					String uploadId = paybackTransferInfoList.get(i).getUploadName();
					if(StringUtils.isNotEmpty(uploadId)){
						User uploadUser = UserUtils.get(uploadId);
						if(!ObjectHelper.isEmpty(uploadUser)){
							// 上传人
							String uploadName = uploadUser.getName();
							paybackTransferInfoList.get(i).setUploadName(uploadName);
						}
					}
				}
			}

			UrgeServicesMoneyEx us = paybackApplyList.get(0);
			String dictLoanStatus = DictUtils.getLabel(dictMap,
					LoanDictType.LOAN_STATUS, us.getDictLoanStatus());
			us.setDictLoanStatusLabel(dictLoanStatus);
			String loanFlag = DictUtils.getLabel(dictMap,
					LoanDictType.CHANNEL_FLAG, us.getLoanFlag());
			us.setLoanFlagLabel(loanFlag);
			model.addAttribute("paybackApply", us);
		}
		model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
		model.addAttribute("middlePersonList", mpList);
		return "borrow/grant/urgeMatchingInfo";
	}
	
	/**
	 * 批量匹配
	 * 2016年1月5日
	 * By zhangfeng
	 * @param id
	 * @param matchingIds
	 * @param contractCodes
	 * @param blueAmounts
	 * @param urgeIds
	 * @param urgeServicesMoneyEx
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "matchingPayback")
	public String matchingPayback(String matchingIds,
			String contractCodes, String blueAmounts, String urgeIds, UrgeServicesMoneyEx urgeServicesMoneyEx) {
		List<UrgeServicesCheckApply> urgeApplyList = new ArrayList<UrgeServicesCheckApply>();
		String msg = null;
		UrgeServicesCheckApply urgeApply = new UrgeServicesCheckApply();
		int matchingCount = 0; // 匹配成功条数
		// 选择数据匹配,申请表中查账失败和待查账的状态都可以进行匹配
		if (StringUtils.isNotEmpty(matchingIds) && StringUtils.isNotEmpty(contractCodes)) {
			
			String[] applyIds = matchingIds.split(",");
			String[] contractCode= contractCodes.split(",");
			String[] amount= blueAmounts.split(",");
			String[] urgeId = urgeIds.split(",");
			for (int i = 0; i < applyIds.length; i++) {
				try {
					urgeApply.setId(applyIds[i]);
					urgeApply.setUrgeApplyStatus("'"+UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()+"','"+UrgeCounterofferResult.VERIFIED_FAILED.getCode()+"'");
					urgeApplyList = urgeGuaranteeMoneyService.findUrgeApplyList(urgeApply);
					if (ArrayHelper.isNotEmpty(urgeApplyList)) {
						boolean matchingFlag = urgeGuaranteeMoneyService.matchingRule(urgeId[i],applyIds[i], contractCode[i], new BigDecimal(amount[i]));
						if (matchingFlag) {
							matchingCount++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("批量匹配时，单笔发生异常，applyId={},contractCode={}",
							new Object[] { applyIds[i], contractCode[i] });
				}
			}
		}else{
			// 匹配所有数据
			GrantUtil.setStoreId(urgeServicesMoneyEx);
			urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeApplyStatus("'"+UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()+"','"+UrgeCounterofferResult.VERIFIED_FAILED.getCode()+"'");
			List<UrgeServicesMoneyEx> paybackApplyList = urgeGuaranteeMoneyService.getUrgeList(urgeServicesMoneyEx);
			if (ArrayHelper.isNotEmpty(paybackApplyList)) {
				for (UrgeServicesMoneyEx uc : paybackApplyList) {
					// 通过合同编号查询还款主表信息
					Map<String,Object> paybackParam = new HashMap<String,Object>();
			        paybackParam.put("contractCode", uc.getContractCode());
					Payback p = paybackDao.selectpayBack(paybackParam);
					if(!ObjectHelper.isEmpty(p)){
						BigDecimal blueAmount = p.getPaybackBuleAmount();
						if(blueAmount == null){
							blueAmount = BigDecimal.ZERO;
						}
						boolean matchingFlag = urgeGuaranteeMoneyService.matchingRule(uc.getUrgeId(), uc.getId(), uc.getContractCode(), blueAmount);
						if (matchingFlag) {
							matchingCount++;
						}
					}
				}
			}
		}
		if (matchingCount > 0) {
			msg = "批量匹配成功，匹配条数： " + matchingCount;
		} else {
			msg = "匹配失败";
		}
		return msg;
	}
	
	/**
	 * 手动匹配获取列表
	 * 2016年1月13日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "getMatchingList")
	public String getMatchingList(PaybackTransferOut paybackTransferOut) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		paybackTransferOut.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		outList = paybackTransferOutService.getNoAuditedList(paybackTransferOut);
		return JsonMapper.nonDefaultMapper().toJson(outList);
	}
	
	/**
	 * 预览图片
	 * 2016年1月25日
	 * By zhangfeng
	 */
	@RequestMapping(value = "previewPng")
	public void previewPng(HttpServletRequest request,HttpServletResponse response, String docId){
		DmService dmService = DmService.getInstance();
		InputStream is = dmService.downloadDocument(docId);
		try {
			FileUtil.writeFile(response.getOutputStream(), is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片下载
	 * 2016年1月25日
	 * By zhangfeng
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "downPng")
	public void downPng(HttpServletRequest request, HttpServletResponse response, String docId, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "iso8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.debug("invoke DealPaybackController method: downPng, 图片下载有误!");
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName.replace(" ", ""));
			DmService dmService = DmService.getInstance();
			dmService.download(response.getOutputStream(), docId);
		} catch (UnsupportedEncodingException e1) {
			logger.debug("invoke DealPaybackController method: downPng, 图片下载有误!");
			e1.printStackTrace();
		} catch (IOException e) {
			logger.debug("invoke DealPaybackController method: downPng, 图片下载有误!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 半自动匹配,手动匹配
	 * 2016年1月5日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param applyId
	 * @param infoId
	 * @param outId
	 * @param contractCode
	 * @param outReallyAmount
	 * @param applyReallyAmount
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "matchingSingle")
	public String matchingSingle(HttpServletRequest request,
			HttpServletResponse response, String applyId, String infoId,
			String outId, String contractCode, String outReallyAmount,
			String applyReallyAmount) {
		ManualMatchNotify notify = new ManualMatchNotify();
		try {
			boolean flag = urgeGuaranteeMoneyService.saveSingleAutoMatch(applyId, infoId, outId,
					contractCode, outReallyAmount, applyReallyAmount);
			if (flag) {
				notify.setSuccess(BooleanType.TRUE);
				notify.setAmount(new BigDecimal(applyReallyAmount).add(new BigDecimal(outReallyAmount)));
			}else {
				notify.setSuccess(BooleanType.FALSE);
				notify.setMessage("手动匹配失败");
				notify.setAmount(BigDecimal.ZERO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("手动匹配失败, applyId={}", new Object[] { applyId });
			notify.setSuccess(BooleanType.FALSE);
			notify.setMessage("手动匹配失败");
			throw new ServiceException("手动匹配失败");
		}
		return jsonMapper.toJson(notify);
	}
	
	/**
	 * 保存匹配审核结果 
	 * 2016年1月5日
	 * By zhangfeng
	 * @param model
	 * @param paybackApply
	 * @param redirectAttributes
	 * @return redirect page
	 */
	@RequestMapping(value = "save")
	public String savePaybackOpe(Model model,
			UrgeServicesMoneyEx urgeServicesMoneyEx,
			RedirectAttributes redirectAttributes) {
		String messageString = urgeGuaranteeMoneyService.saveMatchAuditResult(urgeServicesMoneyEx);
		addMessage(redirectAttributes, messageString);
		return "redirect:" + adminPath + "/borrow/grant/urgeCheckMatch/urgeCheckMatchInfo";
	}
	
	/**
	 * 匹配批量退回 
	 * 2015年12月28日 By zhangfeng
	 * 需要将该笔单子的转账记录表中的转账状态全部改为查账失败，
	 * 申请表中的单子改为查账失败，同时将实际到账金额更新
	 * 主表中的单子改为查账失败
	 * @param applyIds 转账记录表中的关联id，申请表的id
	 * @param contractCodes 合同编号
	 * @param applyBackMsg 批量退回原因
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "urgeMatchingBatchBack")
	public String urgeMatchingBatchBack(String applyIds, UrgeServicesMoneyEx urgeServicesMoneyEx,
			String contractCodes, String urgeIds) {
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		String msg = null;
		int backCount = 0;
		if (StringUtils.isNotEmpty(applyIds)) {
			String[] ids = applyIds.split(",");
			String[] contractCode = contractCodes.split(",");
			String[] urgeId = urgeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				paybackTransferInfo.setrPaybackApplyId(ids[i]);
				paybackTransferInfoList = urgeGuaranteeMoneyService.findUrgeTransfer(paybackTransferInfo);
				if (ArrayHelper.isNotEmpty(paybackTransferInfoList)) {
					try {
						boolean returnMathingFlag = urgeGuaranteeMoneyService.urgeMatchingBack(ids[i], contractCode[i], urgeId[i]);
						if(!returnMathingFlag){
							backCount++;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("匹配批量退回发生异常，contractCode={}", new Object[] { contractCode[i] });
					}
				}
			}
		} else {
			GrantUtil.setStoreId(urgeServicesMoneyEx);
			UrgeServicesCheckApply urgeServicesCheckApply = urgeServicesMoneyEx.getUrgeServicesCheckApply();
			urgeServicesCheckApply.setUrgeApplyStatus("'"
					+ UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()
					+ "','" + UrgeCounterofferResult.VERIFIED_FAILED.getCode() + "'");
			urgeServicesMoneyEx.setUrgeServicesCheckApply(urgeServicesCheckApply);
			List<UrgeServicesMoneyEx> paybackApplyList = urgeGuaranteeMoneyService.getUrgeList(urgeServicesMoneyEx);
			
			if (ArrayHelper.isNotEmpty(paybackApplyList)) {
				for(UrgeServicesMoneyEx ur:paybackApplyList){
					UrgeServicesCheckApply uc = ur.getUrgeServicesCheckApply();
					try {
						Boolean returnMathingFlag = urgeGuaranteeMoneyService.urgeMatchingBack(uc.getId(), uc.getContractCode(), uc.getrServiceChargeId());
						if(!returnMathingFlag){
							backCount++;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("匹配批量退回发生异常，contractCode={}", new Object[] { uc.getContractCode() });
					}
				}
			}
		}
		if(backCount > 0){
			msg = "退回成功，退回条数：" + backCount +"！";;
		}else {
			msg = "没有可以退回的数据！";
		}
		return msg;
	}
}
