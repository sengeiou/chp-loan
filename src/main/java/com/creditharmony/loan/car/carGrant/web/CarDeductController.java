package com.creditharmony.loan.car.carGrant.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.dao.OrgDao;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.car.carGrant.ex.CarDeductCostRecoverEx;
import com.creditharmony.loan.car.carGrant.ex.CarPaybackTransferInfo;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesCheckApply;
import com.creditharmony.loan.car.carGrant.service.CarDeductGrantService;
import com.creditharmony.loan.car.carGrant.service.CarGrantDeductsService;
import com.creditharmony.loan.car.common.dao.CarDeductGrantDao;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.mchange.v2.lang.ThreadUtils;

/**
 * 划扣费用  数据待还款匹配
 * @Class Name CarDeductController
 * @author 李静辉
 * @Create In 2016年2月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/grant/deductCost")
public class CarDeductController extends BaseController {

	@Autowired
	private CarDeductGrantService carDeductGrantService;
	@Autowired
	private CarDeductGrantDao carDeductGrantDao;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private CarGrantDeductsService grantDeductsService;
	@Autowired
	private CarHistoryService carHistoryService;
	private static OrgDao orgDao = SpringContextHolder.getBean(OrgDao.class);
	/**
	 * 获取   划扣费用待追回列表
	 * 查询  晚八点后  催收服务费划扣失败数据
	 * 2016年2月29日
	 * By 李静辉
	 * @return
	 */
	@RequestMapping(value = "deductCostStayRecoverList")
	public String deductCostStayRecoverList(HttpServletRequest request,
			HttpServletResponse response,Model model, CarDeductCostRecoverEx carDeductCostRecoverEx){
		GrantUtil.setStoreIdCar(carDeductCostRecoverEx);
		//数据权限控制
	    String queryRight = DataScopeUtil.getDataScope("a", SystemFlag.CARLOAN.value);
	    carDeductCostRecoverEx.setQueryRight(queryRight);
	    
//	    User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
//		String orgId = currentUser.getDepartment().getId();
//		Org org = orgDao.get(orgId);
//		String orgType = org != null ? org.getType() : "";
//		if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
//			//String storeCode1 = org != null ?  org.getStoreCode() : "";
////			isQueryAll = YESNO.NO.getCode();
//			String orgStoreId = org.getId();
//			if(LoanOrgType.TEAM.key.equals(orgType)){
//				orgStoreId = org.getParentId();
//			}
//			carDeductCostRecoverEx.setFlag(orgStoreId);
//		}
	    
	    Org org1 = ((User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO)).getDepartment();
		String orgType1 = org1 != null ? org1.getType() : "";
		if(LoanOrgType.TEAM.key.equals(orgType1)){
			org1 = OrgCache.getInstance().get(org1.getParentId());
		}
		carDeductCostRecoverEx.setFlag(org1.getId());//加入门店筛选
		
		Page<CarDeductCostRecoverEx> urgePage = carDeductGrantService.selectGuaranteeMoneyList(
				new Page<CarDeductCostRecoverEx>(request, response),carDeductCostRecoverEx);
		List<CarDeductCostRecoverEx> urgePageList = urgePage.getList();
		BigDecimal totalDeducts = new BigDecimal(0.00);
		if (ArrayHelper.isNotEmpty(urgePageList)) {
			Map<String,Dict> dictMap = DictCache.getInstance().getMap();
			for (CarDeductCostRecoverEx urgeEx : urgePageList) {
				urgeEx.setSplitBackResult(DictUtils.getLabel(dictMap,LoanDictType.URGE_COUNTEROFFER_RESULT, urgeEx.getSplitBackResult()));
				urgeEx.setDictDealType(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, urgeEx.getDictDealType()));
				urgeEx.setBankName(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, urgeEx.getBankName()));
				urgeEx.setCustomerTelesalesFlag(DictUtils.getLabel(dictMap,LoanDictType.TELEMARKETING, urgeEx.getCustomerTelesalesFlag()));
				urgeEx.setLoanFlag(CarLoanThroughFlag.parseByCode(urgeEx.getLoanFlag()).getName());
				urgeEx.setClassType(DictUtils.getLabel(dictMap,LoanDictType.LOAN_TYPE, urgeEx.getClassType()));
				BigDecimal deducts = urgeEx.getWaitUrgeMoeny();
				if (deducts != null) {
					totalDeducts = totalDeducts.add(deducts);
				}
			}
		}
		model.addAttribute("deductsAmount",totalDeducts.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		model.addAttribute("urgePage", urgePage);
		model.addAttribute("UrgeServicesMoneyEx", carDeductCostRecoverEx);
		return "car/grant/deductCostStayRecoverList";
	}
	
	/**
	 * 划扣追回      点击进去详细页面
	 * 2016年6月17日
	 * By 朱静越
	 * @param model
	 * @param sid 催收id
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "deductCostStayRecoverForm")
	public String deductCostStayRecoverForm(Model model, String sid,
			HttpServletRequest request, HttpServletResponse response) {
		CarUrgeServicesCheckApply  urgeApply = null;
		CarUrgeServicesCheckApply urgeApplyQuery = new CarUrgeServicesCheckApply();
		List<CarPaybackTransferInfo> paybackTransferInfoList = new ArrayList<CarPaybackTransferInfo>();
		CarPaybackTransferInfo payQueryInfo = new CarPaybackTransferInfo();
		CarDeductCostRecoverEx urgeQuery = new CarDeductCostRecoverEx();
		urgeQuery.setUrgeId(sid);
		List<CarDeductCostRecoverEx> urgeBackList = carDeductGrantService.selGuaranteeList(urgeQuery);
		CarDeductCostRecoverEx urgeServicesMoneyEx = new CarDeductCostRecoverEx();
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		MiddlePerson mp = new MiddlePerson ();
		mp.setDepositFlag(YESNO.YES.getCode());
		List<MiddlePerson> mpList = middlePersonService.findPaybackAccount(mp);
		if (ArrayHelper.isNotEmpty(urgeBackList)) {
			if (sid.equals(urgeBackList.get(0).getUrgeId())) {
				urgeServicesMoneyEx = urgeBackList.get(0);
				urgeApplyQuery.setrServiceChargeId(sid);
				urgeApplyQuery.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				urgeApply = carDeductGrantDao.getUrgeApplyById(urgeApplyQuery);
				if (!ObjectHelper.isEmpty(urgeApply)) {
					payQueryInfo.setrPaybackApplyId(urgeApply.getId());
					payQueryInfo.setRelationType(TargetWay.SERVICE_FEE.getCode());
					paybackTransferInfoList = carDeductGrantService.findUrgeTransfer(payQueryInfo);
					// 如果该单子以前进行过查账，需要初始化申请和info
					for (int i = 0; i < paybackTransferInfoList.size(); i++) {
						paybackTransferInfoList.get(i).setUploadName(UserUtils.getUser().getName());
					}
					urgeServicesMoneyEx.setUrgeServicesCheckApply(urgeApply);
					// 获得查账信息
					urgeServicesMoneyEx.setFlag("again");
				}else{
					// 催收服务费查账申请日期
					urgeApply = new CarUrgeServicesCheckApply();
					urgeApply.setUrgeApplyDate(new Date());
					urgeServicesMoneyEx.setUrgeServicesCheckApply(urgeApply);
					// 上传人，上传时间
					CarPaybackTransferInfo info = new CarPaybackTransferInfo();
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
		urgeServicesMoneyEx.setLoanFlagLabel(CarLoanThroughFlag.parseByCode(urgeServicesMoneyEx.getLoanFlag()).getName());
		
		model.addAttribute("paybackTransferInfoList",paybackTransferInfoList);
		model.addAttribute("middlePersonList", mpList);
		model.addAttribute("UrgeServicesMoneyEx", urgeServicesMoneyEx);
		return "car/grant/deductCostStayRecoverForm";
	}
	
	
	/**
	 * 划扣追回   提交查账
	 * 2016年3月3日
	 * By 李静辉
	 * @param request
	 * @param response
	 * @param model
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	@RequestMapping(value = "save")
	public String saveUrgeApply(@RequestParam("files") MultipartFile[] files,
				CarDeductCostRecoverEx urgeServicesMoneyEx,
				RedirectAttributes redirectAttributes) {
			if (("first").equals(urgeServicesMoneyEx.getFlag())) {
				// 发起催收服务费申请
				carDeductGrantService.saveFirstApply(files, urgeServicesMoneyEx);
			} else {
				// 如果为待办时，直接更新申请表
				carDeductGrantService.updateFirstApply(files, urgeServicesMoneyEx);
			}
			return "redirect:" + adminPath +"/car/grant/deductCost/deductCostStayRecoverList";
		}
	
	
	/**
	 * 提交划扣
	 * 2016年3月10日
	 * By 李静辉
	 * @param request
	 * @param response
	 * @param model
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "submitDeducts")
	public String submitDeducts(String rPaybackId, Model model) {
		String flag = "deal";
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		// 根据催收主表id查找该单子是否为划扣失败的或者为待划扣和处理中（导出）的数据，如果为处理中导出的需要对拆分表中该单子的拆分进行删除。
		CarUrgeServicesMoney urgeServicesMoney = grantDeductsService.find(rPaybackId);
		if (UrgeCounterofferResult.PAYMENT_FAILED.getCode().equals(urgeServicesMoney.getDictDealStatus())||
				UrgeCounterofferResult.PROCESSED.getCode().equals(urgeServicesMoney.getDictDealStatus())) {
			map.put("id", "'" + rPaybackId + "'");
			String rule = urgeServicesMoney.getDictDealType()+ ":"
					+ DeductTime.RIGHTNOW.getCode();
			// 使用放款以往待划扣列表中划扣失败的划扣平台再次提交划扣
			deductReqList = grantDeductsService.queryUrgeDeductReq(map,rule);
			if (ArrayHelper.isNotEmpty(deductReqList)) {
				try {
					flag = grantDeductsService.sendUrgeDeduct(deductReqList.get(0), urgeServicesMoney, rPaybackId);
				} catch (Exception e) {
					e.printStackTrace();	
					logger.error("提交划扣失败，发生异常",e);
				}
			}
		}
		return flag;
	}
	
}
