package com.creditharmony.loan.car.carGrant.web;

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
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carGrant.ex.CarPaybackTransferInfo;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeCheckDoneEx;
import com.creditharmony.loan.car.carGrant.service.CarDeductGrantService;
import com.creditharmony.loan.car.carGrant.service.CarUrgeCheckDoneService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.MiddlePerson;
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
@RequestMapping(value = "${adminPath}/car/grant/urgeCheckDone")
public class CarUrgeCheckDoneController extends BaseController {
	
	@Autowired
	private CarUrgeCheckDoneService urgeCheckDoneService;
	@Autowired
	private CarDeductGrantService carDeductGrantService;
	@Autowired
	private MiddlePersonService middlePersonService;
	
	// 催收查账已办列表
	@RequestMapping(value = "urgeCheckDoneInfo")
	public String urgeCheckDoneInfo(HttpServletRequest request,
			HttpServletResponse response, Model model,
			CarUrgeCheckDoneEx urgeCheckDoneEx) {
		setOrgId(urgeCheckDoneEx);
		Page<CarUrgeCheckDoneEx> waitPage = urgeCheckDoneService.selCheckDone(
				new Page<CarUrgeCheckDoneEx>(request, response), urgeCheckDoneEx);
		// 得到中间人信息表中的开户行供页面下拉框显示
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		List<CarUrgeCheckDoneEx> waitList = waitPage.getList();
		// 从缓存中取值
		if (ArrayHelper.isNotEmpty(waitList)) {
			Map<String, Dict> dictMap = DictCache.getInstance().getMap();
			for (CarUrgeCheckDoneEx ex : waitList) {
				ex.setDictLoanStatus(CarLoanStatus.parseByCode(ex.getDictLoanStatus()).getName());
				ex.setUrgeApplyStatus(DictUtils.getLabel(dictMap,
						LoanDictType.URGE_COUNTEROFFER_RESULT,
						ex.getUrgeApplyStatus()));
				ex.setLoanFlag(CarLoanThroughFlag.parseByCode(ex.getLoanFlag()).getName());
			}
		}

		model.addAttribute("urgeCheckDoneEx", urgeCheckDoneEx);
		model.addAttribute("waitPage", waitPage);
		model.addAttribute("middlePersonList", mpList);
		return "car/grant/carUrgeMatchDone";
	}
	
	// 点击查看，催收服务费查账已办的查看
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model,
			CarUrgeCheckDoneEx urgeCheckDoneEx) {
		List<CarUrgeCheckDoneEx> paybackApplyList = new ArrayList<CarUrgeCheckDoneEx>();
		List<CarPaybackTransferInfo> paybackTransferInfoList = new ArrayList<CarPaybackTransferInfo>();
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		CarPaybackTransferInfo paybackTransferInfo = new CarPaybackTransferInfo();
		setOrgId(urgeCheckDoneEx);
		paybackApplyList = urgeCheckDoneService.selCheckDone(
				new Page<CarUrgeCheckDoneEx>(request, response), urgeCheckDoneEx).getList();
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			// 设置转账表的关联id，为催收申请表的id
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getCheckApplyId());
			paybackTransferInfo.setRelationType(TargetWay.SERVICE_FEE.getCode());
			// 转账记录表中的信息
			paybackTransferInfoList = carDeductGrantService.findUrgeTransfer(paybackTransferInfo);
			if (ArrayHelper.isNotEmpty(paybackTransferInfoList)) {
				for (int i = 0; i < paybackTransferInfoList.size(); i++) {
					// 上传人
					paybackTransferInfoList.get(i).setUploadName(UserUtils.getUser().getName());
				}
			}
		}

		CarUrgeCheckDoneEx uc = paybackApplyList.get(0);
		String urgeApplyStatus = DictCache.getInstance().getDictLabel(
				LoanDictType.URGE_COUNTEROFFER_RESULT, uc.getUrgeApplyStatus());
		uc.setUrgeApplyStatusLabel(urgeApplyStatus);
		String dictLoanStatus = CarLoanStatus.parseByCode(uc.getDictLoanStatus()).getName();
		uc.setDictLoanStatusLabel(dictLoanStatus);
		String loanFlag =CarLoanThroughFlag.parseByCode(uc.getLoanFlag()).getName();
		uc.setLoanFlagLabel(loanFlag);

		model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
		model.addAttribute("middlePersonList", mpList);
		// 基本信息
		model.addAttribute("paybackApply", uc);
		return "car/grant/carUrgeMatchDoneInfo";
	}
	
	/**
	 * 设置门店id
	 * 2016年6月21日
	 * By 朱静越
	 * @param loanGrantEx
	 */
	public  void setOrgId(CarUrgeCheckDoneEx loanGrantEx) {
		if (ObjectHelper.isEmpty(loanGrantEx.getOrgId()) || loanGrantEx.getOrgId()[0].equals("")) {	
			loanGrantEx.setOrgId(null);
		}
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
	public void urgeCheckDoneExl(HttpServletRequest request,CarUrgeCheckDoneEx urgeCheckDoneEx,
			HttpServletResponse response, String checkApplyId) {
		List<CarUrgeCheckDoneEx> checkDoneList = new ArrayList<CarUrgeCheckDoneEx>();
		ExcelUtils excelUtil = new ExcelUtils();
		setOrgId(urgeCheckDoneEx);
		try {
			if (StringUtils.isEmpty(checkApplyId)) {
				checkDoneList = urgeCheckDoneService.queryDoneList(urgeCheckDoneEx);
			}else {
				String[] ids = checkApplyId.split(",");
				for (int i = 0; i < ids.length; i++) {
					CarUrgeCheckDoneEx urgeCheckDone = new CarUrgeCheckDoneEx();
					urgeCheckDone.setCheckApplyId(ids[i]);
					setOrgId(urgeCheckDone);
					checkDoneList.add(urgeCheckDoneService.queryDoneList(urgeCheckDone).get(0));
				}
			}
			if (ArrayHelper.isNotEmpty(checkDoneList)) {
				for (int i = 0; i < checkDoneList.size(); i++) {
					checkDoneList.get(i).setNum(String.valueOf(i + 1));
				}
			}
			excelUtil.exportExcel(checkDoneList, FileExtension.CAR_CHECK_DONE
					+ System.currentTimeMillis(), CarUrgeCheckDoneEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：urgeCheckDoneExl发生异常，催收查账已办列表导出信息由有误.");
			throw new ServiceException("方法：urgeCheckDoneExl发生异常，催收查账已办列表导出信息由有误");
		}
	}
	
}
