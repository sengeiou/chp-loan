package com.creditharmony.loan.borrow.payback.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.OperateMatching;
import com.creditharmony.core.loan.type.OperateRole;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.file.util.FileUtil;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackUseService;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.borrow.payback.service.PaybackTransferOutService;
import com.creditharmony.loan.common.constants.PaybackConstants;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackBlueAmountService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.service.SystemSetMaterService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.TokenUtils;

/**
 * 待还款匹配列表业务处理Controller
 * 
 * @Class Name DealPaybackController
 * @author zhangfeng
 * @Create In 2015年12月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/dealPayback")
public class DealPaybackController extends BaseController {

	@Autowired
	private DealPaybackService dealPaybackService;
	@Autowired
	private PaybackService applyPayService;
	@Autowired 
	private PaybackBlueAmountService blusAmountService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private DeductUpdateService deductUpdateService;
	@Autowired
	private ApplyPaybackUseService applyPaybackUseService;
	@Autowired
	private SystemSetMaterService sys;
	@Autowired
	private PaybackService paybackService;
	
	/**
	 * 待还款匹配详细页面 
	 * 2016年1月22日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param model
	 * @param paybackApply
	 * @return page
	 */
	@RequestMapping(value = "form")
	public String goPaybackInfoForm(HttpServletRequest request,HttpServletResponse response, 
			Model model, PaybackApply paybackApply,String zhcz) {
		// 初始化入账银行
		MiddlePerson mp = new MiddlePerson();
		// 存入标识
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		
		List<PaybackApply> paybackApplyList = new ArrayList<PaybackApply>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		if(zhcz!=null && !zhcz.equals("")){
			paybackApply.setOperateRole(OperateRole.ZHONGHE.getCode());
		}
		paybackApplyList = applyPayService.findApplyPayback(paybackApply);
		if(ArrayHelper.isNotEmpty(paybackApplyList)){
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getId());
			paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
			if(ArrayHelper.isNotEmpty(paybackTransferInfoList)){
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
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanInfo()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanInfo().getDictLoanStatus())){
				String dictLoanStatus = DictCache.getInstance().getDictLabel("jk_loan_apply_status",paybackApplyList.get(0).getLoanInfo().getDictLoanStatus());
				paybackApplyList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
			}
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanInfo()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanInfo().getLoanTeamOrgId())){
				String loanStoreName = String.valueOf(OrgCache.getInstance().get(paybackApplyList.get(0).getLoanInfo().getLoanTeamOrgId()));
				paybackApplyList.get(0).getLoanInfo().setLoanStoreOrgName(loanStoreName);
			}
			
			if(ObjectHelper.isNotEmpty(paybackApplyList.get(0).getLoanBank()) && StringUtils.isNotEmpty(paybackApplyList.get(0).getLoanBank().getBankName())){
				String bankName = DictCache.getInstance().getDictLabel("jk_open_bank",paybackApplyList.get(0).getLoanBank().getBankName());
				paybackApplyList.get(0).getLoanBank().setBankNameLabel(bankName);
			}
			if(StringUtils.isNotEmpty(paybackApplyList.get(0).getDictDealType())){
				String dictDealType = DictCache.getInstance().getDictLabel("jk_deduct_plat",paybackApplyList.get(0).getDictDealType());
				paybackApplyList.get(0).setDictDealTypeLabel(dictDealType);
			}
			
			// 手动匹配token
			TokenUtils.removeToken(paybackApply.getManualMatchingTokenId());
		    Map<String,String> tokenMap = TokenUtils.createToken();
		    paybackApplyList.get(0).setManualMatchingTokenId(tokenMap.get("tokenId"));
		    paybackApplyList.get(0).setManualMatchingToken(tokenMap.get("token"));
		    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		}
		model.addAttribute("paybackTransferInfoList",paybackTransferInfoList);
		model.addAttribute("middlePersonList", mpList);
		model.addAttribute("paybackApplyList", paybackApply);
		model.addAttribute("paybackApply", paybackApplyList.get(0));
		model.addAttribute("zhcz", zhcz);
		logger.debug("invoke DealPaybackController method: goPaybackInfoForm, contarctCode is: " + paybackApply.getContractCode());
		return "borrow/payback/paybackflow/paybackInfo";
	}

	/**
	 * 手动匹配获取列表
	 * 2016年1月13日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param paybackTransferOut
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "getMatchingList")
	public String getMatchingList(HttpServletRequest request, HttpServletResponse response, PaybackTransferOut paybackTransferOut) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		paybackTransferOut.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		outList = paybackTransferOutService.getNoAuditedList(paybackTransferOut);
		return JsonMapper.nonDefaultMapper().toJson(outList);
	}
	
	/**
	 * 批量匹配
	 * 2016年1月5日
	 * By zhangfeng
	 * @param matchingIds
	 * @param paybackIds
	 * @param contractCodes
	 * @param blueAmounts
	 * @param loanFlags
	 * @param pa
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "matchingPayback")
	public String matchingBatch(String matchingIds, String paybackIds, String contractCodes, String models, PaybackApply pa) {
		String msg = null;
		int matchingCount = 0; // 匹配成功条数
		boolean matchingFlag = false; // 匹配成功标识
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
			
			if (!StringUtils.isEmpty(matchingIds) && !StringUtils.isEmpty(contractCodes) && !StringUtils.isEmpty(paybackIds)) {
				// 匹配选择数据
				String[] rApplyId = matchingIds.split(",");
				String[] paybackId = paybackIds.split(",");
				String[] contractCode = contractCodes.split(",");
				String[] model = models.split(",");
				for (int i = 0; i < rApplyId.length; i++) {
					// TG客户不参与批量匹配
					if (!StringUtils.equals(model[i], LoanModel.TG.getCode())) {
						try {
							matchingFlag = dealPaybackService.matchingRule(contractCode[i], rApplyId[i], paybackId[i]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (matchingFlag) {
							matchingCount++;
						}
					}
				}
			} else {
				// 批量匹配所有数据(汇款失败也可以继续查账)
				List<PaybackApply> paList = new ArrayList<PaybackApply>();
				pa.setDictPaybackStatus("'"
						+ RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode() + "','"
						+ RepayApplyStatus.MATCH_FAILEN.getCode() + "'");
				paList = applyPayService.findApplyPayback(pa);
				if (ArrayHelper.isNotEmpty(paList)) {
					for (PaybackApply p : paList) {
						// TG客户不参与批量匹配
						if (!StringUtils.equals(p.getLoanInfo().getModel(), LoanModel.TG.getCode())) {
							try {
								matchingFlag = dealPaybackService.matchingRule(p.getContractCode(), p.getId(), p.getPayback().getId());
							} catch (Exception e) {
								e.printStackTrace();
							}
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
		}else{
			msg = ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE;
		}
		return msg;
	}
	
	/**
	 * 手动匹配 2016年1月5日 By zhangfeng
	 * @param request
	 * @param response
	 * @param applyId
	 * @param infoId
	 * @param outId
	 * @param contractCode
	 * @param outReallyAmount
	 * @param blueAmount
	 * @param applyReallyAmount
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "matchingSingle")
	public BigDecimal matchingSingle(String applyId, String paybackId, String infoId,
			String outId, String contractCode, String blueAmount,
			String outReallyAmount, String applyReallyAmount) {
		BigDecimal amount = dealPaybackService.saveMatchingSingle(applyId, paybackId,
				infoId, outId, contractCode, blueAmount, outReallyAmount,
				applyReallyAmount);
		return amount;
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
	 * 2016年3月22日
	 * By zhangfeng
	 * @param request
	 * @param response
	 * @param docId
	 * @param fileName
	 * @return none
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
	 * 保存匹配审核结果 
	 * 2016年1月5日
	 * By zhangfeng
	 * @param model
	 * @param paybackApply
	 * @param redirectAttributes
	 * @return redirect page
	 */
	@RequestMapping(value = "save")
	public String saveMatchingPayback(Model model,PaybackApply pa, RedirectAttributes redirectAttributes,String zhcz) {
		String msg = "";
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = pa.getManualMatchingTokenId();
			String curToken = pa.getManualMatchingToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			if(StringUtils.equals(pa.getDictPayResult(), OperateMatching.SUCCESS_MATCHING.getCode())){
				msg = dealPaybackService.saveMatching(pa);
			}else{
				msg = dealPaybackService.returnMatching(pa);
			}
		}else{
			msg = ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE;
		}
		addMessage(redirectAttributes, msg);
		logger.debug("invoke DealPaybackController method: saveMatchingPayback, contarctCode is: " + pa.getContractCode());
		if(zhcz!=null && !zhcz.equals(""))
			return "redirect:" + adminPath + "/borrow/payback/matching/list?zhcz=1";
		else
			return "redirect:" + adminPath + "/borrow/payback/matching/list";
	}
	
	/**
	 * 自动匹配定时任务开始
	 * 2016年3月25日
	 * By zhangfeng
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "startAutoMatching")
	public String startAutoMatching() {
		String msg = null;
		SystemSetting ss = new SystemSetting();
		ss.setSysFlag(GrantCommon.SYS_AUTO_MATCHINGS);
		if(!ObjectHelper.isEmpty(sys.get(ss))){
			if(StringUtils.equals(sys.get(ss).getSysValue(), PaybackConstants.AUTOMATCHING)){
				msg = PaybackConstants.AUTOMATCHING;
			}else if(StringUtils.equals(sys.get(ss).getSysValue(), PaybackConstants.REALMATCHING)){
				msg = PaybackConstants.REALMATCHING;
			}else if(StringUtils.equals(sys.get(ss).getSysValue(), PaybackConstants.NOMATCHING)){
				// 开启匹配数据
				ss.setSysValue(PaybackConstants.AUTOMATCHING);
				sys.updateBySysFlag(ss);
				msg = PaybackConstants.NOMATCHING;
			}
		}
		return msg;
	}
	
	/**
	 * 自动匹配定时任务结束
	 * 2016年3月25日
	 * By zhangfeng
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "endAutoMatching")
	public String endAutoMatching() {
		String msg = null;
		SystemSetting ss = new SystemSetting();
		ss.setSysFlag(GrantCommon.SYS_AUTO_MATCHINGS);
		if(!ObjectHelper.isEmpty(sys.get(ss))){
			if(StringUtils.equals(sys.get(ss).getSysValue(), PaybackConstants.AUTOMATCHING)){
				// 关闭定时任务
				ss.setSysValue(PaybackConstants.NOMATCHING);
				sys.updateBySysFlag(ss);
				msg = PaybackConstants.AUTOMATCHING;
			}else if(StringUtils.equals(sys.get(ss).getSysValue(), PaybackConstants.NOMATCHING)){
				msg = PaybackConstants.NOMATCHING;
			}
		}
		return msg;
	}
}
