package com.creditharmony.loan.borrow.grant.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeCheckDoneEx;
import com.creditharmony.loan.borrow.grant.service.UrgeCheckDoneService;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 催收服务费查账已办列表中的各种操作
 * @Class Name UrgeCheckDoneController
 * @author 朱静越
 * @Create In 2016年3月2日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/urgeCheckDone")
public class UrgeCheckDoneController extends BaseController {
	
	@Autowired
	private UrgeCheckDoneService urgeCheckDoneService;
	@Autowired
	private DealPaybackService dealPaybackService ;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	// 催收查账已办列表
	@RequestMapping(value = "urgeCheckDoneInfo")
	public String urgeCheckDoneInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			UrgeCheckDoneEx urgeCheckDoneEx) {
		Page<UrgeCheckDoneEx> waitPage = urgeCheckDoneService.selCheckDone(
				new Page<UrgeCheckDoneEx>(request, response), urgeCheckDoneEx);
		// 得到中间人信息表中的开户行供页面下拉框显示
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		List<UrgeCheckDoneEx> waitList = waitPage.getList();
		// 从缓存中取值
		if (ArrayHelper.isNotEmpty(waitList)) {
			Map<String, Dict> dictMap = DictCache.getInstance().getMap();
			for (UrgeCheckDoneEx ex : waitList) {
				ex.setDictLoanStatus(DictUtils.getLabel(dictMap,
						LoanDictType.LOAN_STATUS, ex.getDictLoanStatus()));
				ex.setUrgeApplyStatus(DictUtils.getLabel(dictMap,
						LoanDictType.URGE_COUNTEROFFER_RESULT,
						ex.getUrgeApplyStatus()));
				ex.setLoanFlag(DictUtils.getLabel(dictMap,
						LoanDictType.CHANNEL_FLAG, ex.getLoanFlag()));
			}
		}
		// 汇金产品获取
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("urgeCheckDoneEx", urgeCheckDoneEx);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("middlePersonList", mpList);
		model.addAttribute("productList", productList);
		return "borrow/grant/urgeMatchDone";
	}
	
	// 点击查看，催收服务费查账已办的查看
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model,
			UrgeCheckDoneEx urgeCheckDoneEx) {
		List<UrgeCheckDoneEx> paybackApplyList = new ArrayList<UrgeCheckDoneEx>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		paybackApplyList = urgeCheckDoneService.selCheckDone(
				new Page<UrgeCheckDoneEx>(request, response), urgeCheckDoneEx).getList();
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			// 设置转账表的关联id，为催收申请表的id
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getCheckApplyId());
			paybackTransferInfo.setRelationType(TargetWay.SERVICE_FEE.getCode());
			// 转账记录表中的信息
			paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
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
		}

		UrgeCheckDoneEx uc = paybackApplyList.get(0);
		String urgeApplyStatus = DictCache.getInstance().getDictLabel(
				LoanDictType.URGE_COUNTEROFFER_RESULT, uc.getUrgeApplyStatus());
		uc.setUrgeApplyStatusLabel(urgeApplyStatus);
		String dictLoanStatus = DictCache.getInstance().getDictLabel(
				LoanDictType.LOAN_STATUS, uc.getDictLoanStatus());
		uc.setDictLoanStatusLabel(dictLoanStatus);
		String loanFlag = DictCache.getInstance().getDictLabel(
				LoanDictType.CHANNEL_FLAG, uc.getLoanFlag());
		uc.setLoanFlagLabel(loanFlag);

		model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
		model.addAttribute("middlePersonList", mpList);
		// 基本信息
		model.addAttribute("paybackApply", uc);
		return "borrow/grant/urgeMatchDoneInfo";
	}
	
	/**
	 * 导出催收查账已办列表中的信息
	 * 2016年3月3日
	 * By 朱静越
	 * @param request
	 * @param urgeCheckDoneEx
	 * @param response
	 * @param checkApplyId
	 */
	@RequestMapping(value = "urgeCheckDoneExl")
	public void urgeCheckDoneExl(HttpServletRequest request,UrgeCheckDoneEx urgeCheckDoneEx,
			HttpServletResponse response, String checkApplyId) {
		List<UrgeCheckDoneEx> checkDoneList = new ArrayList<UrgeCheckDoneEx>();
		ExcelUtils excelUtil = new ExcelUtils();
		try {
			if (StringUtils.isEmpty(checkApplyId)) {
				checkDoneList = urgeCheckDoneService.queryDoneList(urgeCheckDoneEx);
			}else {
				String[] ids = checkApplyId.split(",");
				for (int i = 0; i < ids.length; i++) {
					UrgeCheckDoneEx urgeCheckDone = new UrgeCheckDoneEx();
					urgeCheckDone.setCheckApplyId(ids[i]);
					
					checkDoneList.add(urgeCheckDoneService.queryDoneList(urgeCheckDone).get(0));
				}
			}
			if (ArrayHelper.isNotEmpty(checkDoneList)) {
				for (int i = 0; i < checkDoneList.size(); i++) {
					checkDoneList.get(i).setNum(String.valueOf(i + 1));
				}
			}
			excelUtil.exportExcel(checkDoneList, FileExtension.CHECK_DONE
					+ System.currentTimeMillis(), UrgeCheckDoneEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：urgeCheckDoneExl发生异常，催收查账已办列表导出信息由有误.");
			throw new ServiceException("方法：urgeCheckDoneExl发生异常，催收查账已办列表导出信息由有误");
		}
	}
	
}
