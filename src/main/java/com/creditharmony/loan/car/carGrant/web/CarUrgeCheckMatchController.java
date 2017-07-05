package com.creditharmony.loan.car.carGrant.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
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
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.service.PaybackTransferOutService;
import com.creditharmony.loan.car.carGrant.ex.CarDeductCostRecoverEx;
import com.creditharmony.loan.car.carGrant.ex.CarPaybackTransferInfo;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesCheckApply;
import com.creditharmony.loan.car.carGrant.service.CarDeductGrantService;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;

/**
 * 催收服务费返还申请列表中的各种操作
 * @Class Name UrgeCheckMatchController
 * @author 张振强
 * @Create In 2016年1月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/grant/urgeCheckMatch")
public class CarUrgeCheckMatchController extends BaseController {
	
	@Autowired
	private CarDeductGrantService carDeductGrantService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	@Autowired 
	private GrantDeductsService grantDeductsService;
	@Autowired
	private HistoryService historyService;

	/**
	 * 查询查账匹配列表
	 * 2016年6月20日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param model
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	@RequestMapping(value = "urgeCheckMatchInfo")
	public String urgeCheckMatchInfo(HttpServletRequest request, HttpServletResponse response, Model model, CarDeductCostRecoverEx urgeServicesMoneyEx){
	    GrantUtil.setStoreIdCar(urgeServicesMoneyEx);
	    Page<CarDeductCostRecoverEx> paybackApplyPage = carDeductGrantService
				.selCheckInfo(new Page<CarDeductCostRecoverEx>(request, response), urgeServicesMoneyEx);
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		List<CarDeductCostRecoverEx> paybackList = paybackApplyPage.getList();
		if (ArrayHelper.isNotEmpty(paybackList)) {
			CarLoanThroughFlag cf = null;
			CarLoanStatus las = null;
			for (CarDeductCostRecoverEx paybackEx : paybackList) {
				if (StringUtils.isNotEmpty(paybackEx.getLoanFlag())) {
				     cf = CarLoanThroughFlag.parseByCode(paybackEx.getLoanFlag());
				     paybackEx.setLoanFlagLabel(cf.getName());
				}
				if (StringUtils.isNotEmpty(paybackEx.getDictLoanStatus())) {
					las = CarLoanStatus.parseByCode(paybackEx.getDictLoanStatus());
					paybackEx.setDictLoanStatusLabel(las.getName());
				}
				if (StringUtils.isNotEmpty(paybackEx.getName())) {
					// 门店
					paybackEx.setName(String.valueOf(OrgCache.getInstance().get(paybackEx.getName())));
				}
			}
		}
		paybackApplyPage.setList(paybackList);
		model.addAttribute("urgeServicesMoneyEx", urgeServicesMoneyEx);
		model.addAttribute("paybackApplyList", paybackApplyPage);
		model.addAttribute("middlePersonList", mpList);
		return "car/grant/carUrgeMatching";
	}
	
	/**
	 * 点击办理，进入办理页面
	 * 2016年6月20日
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
			CarDeductCostRecoverEx urgeServicesMoneyEx) {
		List<CarDeductCostRecoverEx> paybackApplyList = new ArrayList<CarDeductCostRecoverEx>();
		List<CarPaybackTransferInfo> paybackTransferInfoList = new ArrayList<CarPaybackTransferInfo>();
		Page<MiddlePerson> middlePersonPage = middlePersonService
				.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
		CarPaybackTransferInfo paybackTransferInfo = new CarPaybackTransferInfo();
		paybackApplyList = carDeductGrantService.selCheckInfo(
				new Page<CarDeductCostRecoverEx>(request, response),
				urgeServicesMoneyEx).getList();
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			// 设置转账表的关联id，为催收主表的催收id
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getUrgeServicesCheckApply().getId());
			paybackTransferInfo.setRelationType(TargetWay.SERVICE_FEE.getCode());
			paybackTransferInfoList = carDeductGrantService.findUrgeTransfer(paybackTransferInfo);
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

			CarDeductCostRecoverEx us = paybackApplyList.get(0);
			String dictLoanStatus = CarLoanStatus.parseByCode(us.getDictLoanStatus()).getName();
			us.setDictLoanStatus(dictLoanStatus);
			CarLoanThroughFlag loanFlag = CarLoanThroughFlag.parseByCode(us.getLoanFlag());
			if (ObjectHelper.isNotEmpty(loanFlag)) {
				us.setLoanFlag(loanFlag.getName());
			}
			model.addAttribute("paybackApply", us);
		}
		model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		return "car/grant/carUrgeMatchingInfo";
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
			String contractCodes, String urgeIds, CarDeductCostRecoverEx urgeServicesMoneyEx) {
		List<CarUrgeServicesCheckApply> urgeApplyList = new ArrayList<CarUrgeServicesCheckApply>();
		String msg = null;
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		int matchingCount = 0; // 匹配成功条数
		// 选择数据匹配,申请表中查账失败和待查账的状态都可以进行匹配
		if (StringUtils.isNotEmpty(matchingIds) && StringUtils.isNotEmpty(contractCodes)) {
			
			String[] applyIds = matchingIds.split(",");
			String[] contractCode= contractCodes.split(",");
			String[] urgeId = urgeIds.split(",");
			for (int i = 0; i < applyIds.length; i++) {
				try {
					urgeApply.setId(applyIds[i]);
					urgeApply.setUrgeApplyStatus("'"+UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()+"','"+UrgeCounterofferResult.VERIFIED_FAILED.getCode()+"'");
					urgeApplyList = carDeductGrantService.findUrgeApplyList(urgeApply);
					if (ArrayHelper.isNotEmpty(urgeApplyList)) {
						boolean matchingFlag = carDeductGrantService.matchingRule(urgeId[i],applyIds[i], contractCode[i]);
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
			GrantUtil.setStoreIdCar(urgeServicesMoneyEx);
			urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeApplyStatus("'"+UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()+"','"+UrgeCounterofferResult.VERIFIED_FAILED.getCode()+"'");
			List<CarDeductCostRecoverEx> paybackApplyList = carDeductGrantService.getUrgeList(urgeServicesMoneyEx);
			if (ArrayHelper.isNotEmpty(paybackApplyList)) {
				for (CarDeductCostRecoverEx uc : paybackApplyList) {
					boolean matchingFlag = carDeductGrantService.matchingRule(uc.getUrgeId(), uc.getId(), uc.getContractCode());
					if (matchingFlag) {
						matchingCount++;
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
	 * 半自动匹配
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
			carDeductGrantService.saveSingleAutoMatch(applyId, infoId, outId,
					contractCode, outReallyAmount, applyReallyAmount);
			notify.setSuccess(BooleanType.TRUE);
			notify.setAmount(new BigDecimal(applyReallyAmount).add(new BigDecimal(outReallyAmount)));
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
			CarDeductCostRecoverEx urgeServicesMoneyEx,
			RedirectAttributes redirectAttributes) {
		carDeductGrantService.saveMatchAuditResult(urgeServicesMoneyEx);
		addMessage(redirectAttributes, "保存匹配审核结果成功");
		return "redirect:" + adminPath + "/car/grant/urgeCheckMatch/urgeCheckMatchInfo";
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
	public String urgeMatchingBatchBack(String applyIds, CarDeductCostRecoverEx urgeServicesMoneyEx,
			String contractCodes, String urgeIds) {
		List<CarPaybackTransferInfo> paybackTransferInfoList = new ArrayList<CarPaybackTransferInfo>();
		CarPaybackTransferInfo paybackTransferInfo = new CarPaybackTransferInfo();
		String msg = null;
		int backCount = 0;
		if (StringUtils.isNotEmpty(applyIds)) {
			String[] ids = applyIds.split(",");
			String[] contractCode = contractCodes.split(",");
			String[] urgeId = urgeIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				paybackTransferInfo.setrPaybackApplyId(ids[i]);
				paybackTransferInfoList = carDeductGrantService.findUrgeTransfer(paybackTransferInfo);
				if (ArrayHelper.isNotEmpty(paybackTransferInfoList)) {
					try {
						boolean returnMathingFlag = carDeductGrantService.urgeMatchingBack(ids[i], contractCode[i], urgeId[i]);
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
			GrantUtil.setStoreIdCar(urgeServicesMoneyEx);
			CarUrgeServicesCheckApply urgeServicesCheckApply = urgeServicesMoneyEx.getUrgeServicesCheckApply();
			urgeServicesCheckApply.setUrgeApplyStatus("'"
					+ UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode()
					+ "','" + UrgeCounterofferResult.VERIFIED_FAILED.getCode() + "'");
			urgeServicesMoneyEx.setUrgeServicesCheckApply(urgeServicesCheckApply);
			List<CarDeductCostRecoverEx> paybackApplyList = carDeductGrantService.getUrgeList(urgeServicesMoneyEx);
			
			if (ArrayHelper.isNotEmpty(paybackApplyList)) {
				for(CarDeductCostRecoverEx ur:paybackApplyList){
					CarUrgeServicesCheckApply uc = ur.getUrgeServicesCheckApply();
					try {
						Boolean returnMathingFlag = carDeductGrantService.urgeMatchingBack(uc.getId(), uc.getContractCode(), uc.getrServiceChargeId());
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
