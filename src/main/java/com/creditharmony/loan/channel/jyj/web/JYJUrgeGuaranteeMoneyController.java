package com.creditharmony.loan.channel.jyj.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.dao.UrgeGuaranteeMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesCheckApply;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.channel.jyj.service.JYJUrgeGuaranteeMoneyService;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 催收服务费费待催收
 * @Class Name UrgeGuaranteeMoneyController
 * @author 张振强
 * @Create In 2016年1月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/jyj/urgeGuaranteeMoney")
public class JYJUrgeGuaranteeMoneyController extends BaseController {

	@Autowired
	private JYJUrgeGuaranteeMoneyService urgeGuaranteeMoneyService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantDeductsService grantDeductsService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private UrgeGuaranteeMoneyDao urgeGuaranteeMoneyDao;
	
	public Page<UrgeServicesMoneyEx> urgePage;
	public List<UrgeServicesMoneyEx> urgeBackList;

	/**
	 * 催收服务费待催收列表，查询条件为列表中的所有，按照条件靠前显示。
	 * 2015年12月30日 By 张振强
	 * @param model
	 * @param urgeServicesMoneyEx
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "urgeGuarantInfo")
	public String urgeGuarantInfo(Model model,
			UrgeServicesMoneyEx urgeServicesMoneyEx,
			HttpServletRequest request, HttpServletResponse response) {
	    GrantUtil.setStoreId(urgeServicesMoneyEx);
	    
		//数据权限控制
	    String queryRight = DataScopeUtil.getDataScope("a", SystemFlag.LOAN.value);
	    urgeServicesMoneyEx.setQueryRight(queryRight);
	    
		// 催收保证金，划扣结果为：处理中，失败，处理中（导出）。门店看到的
		urgePage = urgeGuaranteeMoneyService.selectGuaranteeMoneyList(
				new Page<UrgeServicesMoneyEx>(request, response),
				urgeServicesMoneyEx);
		List<UrgeServicesMoneyEx> urgePageList = urgePage.getList();
		BigDecimal totalDeducts = new BigDecimal(0.00);
		if (ArrayHelper.isNotEmpty(urgePageList)) {
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			for (UrgeServicesMoneyEx urgeEx : urgePageList) {
				urgeEx.setSplitBackResult(DictUtils.getLabel(dictMap,LoanDictType.URGE_COUNTEROFFER_RESULT, urgeEx.getSplitBackResult()));
				urgeEx.setBankName(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, urgeEx.getBankName()));
				urgeEx.setCustomerTelesalesFlag(DictUtils.getLabel(dictMap,LoanDictType.TELEMARKETING, urgeEx.getCustomerTelesalesFlag()));
				urgeEx.setLoanFlag(DictUtils.getLabel(dictMap,LoanDictType.CHANNEL_FLAG, urgeEx.getLoanFlag()));
				urgeEx.setClassType(DictUtils.getLabel(dictMap,LoanDictType.LOAN_TYPE, urgeEx.getClassType()));
				BigDecimal deducts = urgeEx.getWaitUrgeMoeny();
				if (deducts != null) {
					totalDeducts = totalDeducts.add(deducts);
				}
			}
		}
		model.addAttribute("deductsAmount",totalDeducts.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		model.addAttribute("urgePage", urgePage);
		model.addAttribute("UrgeServicesMoneyEx", urgeServicesMoneyEx);
		return "channel/jyj/jyj_urgeGuaranteeList";
	}

	/**
	 * 催收服务费待催收办理页面。 
	 * 2015年12月30日 By 张振强
	 * @param model
	 * @param sid
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "urgeGuarantDeal")
	public String urgeGuarantDeal(Model model, String sid,
			HttpServletRequest request, HttpServletResponse response) {
		UrgeServicesCheckApply  urgeApply = null;
		UrgeServicesCheckApply urgeApplyQuery = new UrgeServicesCheckApply();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		PaybackTransferInfo payQueryInfo = new PaybackTransferInfo();
		UrgeServicesMoneyEx urgeQuery = new UrgeServicesMoneyEx();
		urgeQuery.setUrgeId(sid);
		List<UrgeServicesMoneyEx> urgeBackList = urgeGuaranteeMoneyService.selGuaranteeList(urgeQuery);
		UrgeServicesMoneyEx urgeServicesMoneyEx = new UrgeServicesMoneyEx();
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		if (ArrayHelper.isNotEmpty(urgeBackList)) {
			if (sid.equals(urgeBackList.get(0).getUrgeId())) {
				urgeServicesMoneyEx = urgeBackList.get(0);
				urgeApplyQuery.setrServiceChargeId(sid);
				urgeApplyQuery.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				urgeApply = urgeGuaranteeMoneyDao.getUrgeApplyById(urgeApplyQuery);
				if (!ObjectHelper.isEmpty(urgeApply)) {
					payQueryInfo.setrPaybackApplyId(urgeApply.getId());
					payQueryInfo.setRelationType(TargetWay.JYJ_FEE.getCode());
					paybackTransferInfoList = urgeGuaranteeMoneyService.findUrgeTransfer(payQueryInfo);
					// 如果该单子以前进行过查账，需要初始化申请和info
					for (int i = 0; i < paybackTransferInfoList.size(); i++) {
						paybackTransferInfoList.get(i).setUploadName(UserUtils.getUser().getName());
					}
					urgeServicesMoneyEx.setUrgeServicesCheckApply(urgeApply);
					// 获得查账信息
					urgeServicesMoneyEx.setFlag("again");
				}else{
					// 催收服务费查账申请日期
					urgeApply = new UrgeServicesCheckApply();
					urgeApply.setUrgeApplyDate(new Date());
					urgeServicesMoneyEx.setUrgeServicesCheckApply(urgeApply);
					// 上传人，上传时间
					PaybackTransferInfo info = new PaybackTransferInfo();
					info.setUploadName(UserUtils.getUser().getName());
					info.setUploadDate(new Date());
					urgeServicesMoneyEx.setPaybackTransferInfo(info);
					urgeServicesMoneyEx.setFlag("first");
				}
			}
		}

		urgeServicesMoneyEx.setSplitBackResultLabel(DictUtils.getLabel(dictMap,
				LoanDictType.URGE_COUNTEROFFER_RESULT,
				urgeServicesMoneyEx.getSplitBackResult()));
		urgeServicesMoneyEx.setBankNameLabel(DictUtils.getLabel(dictMap,
				LoanDictType.OPEN_BANK, urgeServicesMoneyEx.getBankName()));
		urgeServicesMoneyEx.setCustomerTelesalesFlagLabel(DictUtils.getLabel(
				dictMap, LoanDictType.TELEMARKETING,
				urgeServicesMoneyEx.getCustomerTelesalesFlag()));
		urgeServicesMoneyEx.setLoanFlagLabel(DictUtils.getLabel(dictMap,
				LoanDictType.CHANNEL_FLAG, urgeServicesMoneyEx.getLoanFlag()));
		
		model.addAttribute("paybackTransferInfoList",paybackTransferInfoList);
		model.addAttribute("middlePersonList", mpList);
		model.addAttribute("UrgeServicesMoneyEx", urgeServicesMoneyEx);
		return "channel/jyj/jyj_urgeGuaranteeDeal";
	}

	
	/**
	 * 催收服务费查账申请保存
	 * 2016年3月1日
	 * By zhangfeng
	 * @param urgeId
	 * @return page
	 */
	@RequestMapping(value = "save")
	public String saveUrgeApply(@RequestParam("files") MultipartFile[] files,
			UrgeServicesMoneyEx urgeServicesMoneyEx,
			RedirectAttributes redirectAttributes) {
		if (("first").equals(urgeServicesMoneyEx.getFlag())) {
			// 发起催收服务费申请
			urgeGuaranteeMoneyService.saveFirstApply(files, urgeServicesMoneyEx);
		} else {
			// 如果为待办时，直接更新申请表
			urgeGuaranteeMoneyService.updateFirstApply(files, urgeServicesMoneyEx);
		}
		return "redirect:" + adminPath +"/channel/jyj/urgeGuaranteeMoney/urgeGuarantInfo";
	}
}
