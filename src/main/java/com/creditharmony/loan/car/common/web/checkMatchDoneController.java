/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webHistoryController.java
 * @Create By 朱静越
 * @Create In 2015年12月1日 下午2:39:02
 */
/**
 * @Class Name HistoryController
 * @author 朱静越
 * @Create In 2015年12月1日
 */
package com.creditharmony.loan.car.common.web;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.Reflections;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeCheckDoneEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.ex.CarLoanStatusHisEx;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;


/**
 * 查询借款历史功能控制层
 * @author 张进
 *	
 */
@Controller
@RequestMapping(value="${adminPath}/common/checkMatchDone")
public class checkMatchDoneController extends BaseController{
	@Autowired
	private CarHistoryService historyService;
	@Autowired
	private MiddlePersonService middlePersonService;
	
	
	// 点击查看，催收服务费查账已办的查看
	@RequestMapping(value = "lookCheckDoneInfo")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model,
			CarLoanStatusHisEx carLoanStatusHisEx) {/*
		List<UrgeCheckDoneEx> paybackApplyList = new ArrayList<UrgeCheckDoneEx>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		Page<MiddlePerson> middlePersonPage = middlePersonService
				.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
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
					// 上传人
					paybackTransferInfoList.get(i).setUploadName(UserUtils.getUser().getName());
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
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		// 基本信息
		model.addAttribute("paybackApply", uc);*/
		System.out.println("----------------------------------------------------------");
		return "car/carRefund/pendingRepayMatchDone";
	}
	
	
}