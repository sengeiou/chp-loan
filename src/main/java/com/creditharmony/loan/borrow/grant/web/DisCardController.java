package com.creditharmony.loan.borrow.grant.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.ParamEx;
import com.creditharmony.loan.borrow.grant.service.DisCardService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 分配卡号列表处理事件
 * @Class Name DisCardController
 * @author 朱静越
 * @Create In 2015年11月27日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/disCard")
public class DisCardController extends BaseController {
	
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private DisCardService disCardService;
	@Autowired
	private CityInfoService cityInfoService;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	
	/**
	 * 分配卡号数据列表显示
	 * 2017年1月25日
	 * By 朱静越
	 * @param model
	 * @param loanFlowQueryParam 查询条件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getDisCardList")
	public String getDisCardList(Model model, LoanFlowQueryParam loanFlowQueryParam,
			HttpServletRequest request, HttpServletResponse response) {
		GrantUtil.setStoreOrgIdQuery(loanFlowQueryParam);
		Page<DisCardEx> disCardPage = disCardService.getDisCardList(
				new Page<DisCardEx>(request, response), loanFlowQueryParam);
		Double totalGrantMoney = 0.00;
		if (ObjectHelper.isNotEmpty(disCardPage.getList())) {
			totalGrantMoney = disCardPage.getList().get(0).getTotalGrantMoney();
		}
		// 放款批次的查询，初始化查询下拉列表的
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("LoanFlowQueryParam", loanFlowQueryParam);
		model.addAttribute("workItems", disCardPage);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalGrantCount", disCardPage.getCount());
		model.addAttribute("provinceList", provinceList);
		return "borrow/grant/loanflow_disCardList";
	}
	
	/**
	 * 分配卡号跳转到详细页面：可以进行单笔，可以进行批量操作；
	 * 2017年2月6日
	 * By 朱静越
	 * @param model
	 * @param checkVal applyId 在进行勾选的情况下进行分配
	 * @param grtQryParam 查询条件，在没有勾选的情况下使用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "disCardJump")
	public String disCardJump(Model model, String checkVal,LoanFlowQueryParam grtQryParam) throws Exception {
		//添加token
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    model.addAttribute("deftokenId", tokenMap.get("tokenId"));
	    model.addAttribute("deftoken", tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
	    GrantUtil.setStoreOrgIdQuery(grtQryParam);
	    String[] applyIds = null;
		if (StringUtils.isNotEmpty(checkVal)) {
			applyIds = checkVal.split(",");
			grtQryParam.setApplyIds(applyIds);
		}
		List<DisCardEx> disCardExList = disCardService.getDisCardList(grtQryParam);
		model.addAttribute("list", disCardExList);
		model.addAttribute("flag", "disCard");
		return "borrow/grant/loanflow_disCard_approve_0";
	}

	/**
	 * 显示中间人信息 2015年12月25日 By 朱静越
	 * @param model
	 * @param request
	 * @param response
	 * @param midPerson 中间人实体
	 * @return
	 */
	@RequestMapping(value = "showMiddlePerson")
	public String showMiddlePerson(Model model, HttpServletRequest request,
			HttpServletResponse response, MiddlePerson midPerson) {
		Page<MiddlePerson> middlePage = middlePersonService.selectAllMiddle(
				new Page<MiddlePerson>(request, response), midPerson);
		if (midPerson != null) {
			model.addAttribute("midPerson", midPerson);
		}
		//特殊控制，将中信银行和招商银行6065置顶
		List<MiddlePerson> middlePersonList=middlePage.getList();
		List<MiddlePerson> middlePersonListOne=new ArrayList<MiddlePerson>();
		//单独将特殊的账号置顶
		for(MiddlePerson middleperson:middlePersonList){
			if("6226980701534933".equals(middleperson.getBankCardNo())){
				middlePersonListOne.add(middleperson);
			}
			if("6225880153886065".equals(middleperson.getBankCardNo())){
				middlePersonListOne.add(middleperson);
			}
		}
		for(MiddlePerson middleperson:middlePersonList){
			if(!"6226980701534933".equals(middleperson.getBankCardNo())&&!"6225880153886065".equals(middleperson.getBankCardNo())){
				middlePersonListOne.add(middleperson);
			}
		}
		//重置list
		if(middlePage!=null){
			middlePage.setList(middlePersonListOne);
		}
		model.addAttribute("sureFlag", "disCard");
		model.addAttribute("middlePage", middlePage);
		return "borrow/grant/disCardMiddle";
	}

	/**
	 * 显示放款人员页面 
	 * 2015年12月25日 By 朱静越
	 * @param request
	 * @param response
	 * @param model
	 * @param userInfo 用户实体
	 * @return 
	 */
	@RequestMapping(value = "showDisPerson")
	public String showDisPerson(HttpServletRequest request,
			HttpServletResponse response, Model model,
			UserInfo userInfo) {
		// 获得放款人员信息,用户类型为放款人员
		Map<String, String> mapUser = new HashMap<String, String>();
		mapUser.put("roleId", BaseRole.DELIVERY_PERSON.id);
		mapUser.put("departmentId", UserUtils.getUser().getDepartment().getId());
		mapUser.put("name", userInfo.getName());
		mapUser.put("userCode", userInfo.getUserCode());
		Page<UserInfo> user = userInfoService.getRoleUser(
				new Page<UserInfo>(request, response), mapUser);
		model.addAttribute("user", user);
		model.addAttribute("userInfo", userInfo);
		return "borrow/grant/disCardPerson";
	}
	
	/**
	 * 显示中间人和放款人员信息
	 * 2016年2月26日
	 * By 朱静越
	 * @return 跳转页面
	 */
	@RequestMapping(value = "showChoose")
	public String showChoose(String wayFlag,Model model){
		model.addAttribute("wayFlag", wayFlag);
		return "borrow/grant/disCardChoose";
	}

	/**
	 * 选择放款人员，回显中间人数据信息 
	 * 2015年12月15日 By 朱静越
	 * @param userCode 用户编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "disPersonSure")
	public MiddlePerson disPersonSure(String middleId) {
		MiddlePerson middlePerson = middlePersonService.selectById(middleId);
		return middlePerson;
	}

	/**
	 * 分配卡号节点结束，调用流程更新单子的借款状态，
	 * 更新放款记录表，放款途径。在提交节点，设置dealUser
	 * 2015年12月15日 By 朱静越
	 * @param model
	 * @param apply
	 * @param userCode 用户编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "disCardCommit")
	public String disCardCommit(Model model, String userCode,String middleId, ParamEx param,String deftokenId,String deftoken) {
		List<DistachParamEx> list = param.getList();
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		boolean result = false;
		synchronized(this){  
    	    result = TokenUtils.validToken(deftokenId, deftoken);
    	    TokenUtils.removeToken(deftokenId);
		} 
		if(result){
			if (ArrayHelper.isNotEmpty(list)) {
				for (DistachParamEx distachParamItem : list) {
					try {
						LoanInfo searchLoanInfo = new LoanInfo();
						searchLoanInfo.setLoanCode(distachParamItem.getLoanCode());
						searchLoanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
						searchLoanInfo.setFrozenFlag(YESNO.YES.getCode());
						int frozenInt = applyLoanInfoDao.findFrozenInt(searchLoanInfo);
						if (frozenInt==0) {
							failedContractCode = disCardService.disCardDeal(userCode, distachParamItem, middleId);
						}else {
							failedContractCode = distachParamItem.getContractCode();
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("分配卡号失败，发生异常",e);
					}
					if(StringUtils.isNotEmpty(failedContractCode)){
						failedCodeList.add(failedContractCode);
						failNum++;
					}else{
						successNum++;
					}
				}
				// 表示单笔处理
				if (list.size()==1) {
					if (successNum == 1) {
						notify.setSuccess(BooleanType.TRUE);
						notify.setMessage("分配成功");
					}else{
						notify.setSuccess(BooleanType.FALSE);
						notify.setMessage("分配失败");
					}
					// 多笔处理
				}else{
					if (ArrayHelper.isNotEmpty(failedCodeList)) {
						AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
					}else{
						notify.setSuccess(BooleanType.TRUE);
						notify.setMessage("全部分配成功");
					}
				}
			}else{
				notify.setSuccess(BooleanType.FALSE);
				notify.setMessage("分配失败，没有要处理的数据");
			}
		}else{
			notify.setSuccess(BooleanType.FALSE);
			notify.setMessage("已经提交，请不要重复提交");
		}
		
		return jsonMapper.toJson(notify);
	}

	/**
	 * 分配卡号单笔或批量退回，需要更新借款状态，退回原因，同时插入历史
	 * 2016年2月25日
	 * By 朱静越
	 * @param checkVal applyId
	 * @param backReason 退回原因Code
	 * @param remark 备注
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "backDeal")
	public String backDeal(String checkVal, String backReason,String backReasonCode) {
		String[] applyIds = null;
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		if (StringUtils.isNotEmpty(checkVal)) {
			applyIds = checkVal.split(";");
			for (String applyId:applyIds) {
				String contractCode = null;
				try {
					contractCode = disCardService.disCardBack(applyId, backReason, backReasonCode);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("分配卡号退回失败，发生异常");
					failedContractCode = contractCode;
				}
				if(StringUtils.isNotEmpty(failedContractCode)){
					failedCodeList.add(failedContractCode);
					failNum++;
				}else{
					successNum++;
				}
			}
			// 表示单笔处理
			if (applyIds.length == 1) {
				if (successNum == 1) {
					notify.setSuccess(BooleanType.TRUE);
					notify.setMessage("分配卡号退回成功");
				}else{
					notify.setSuccess(BooleanType.FALSE);
					notify.setMessage("分配卡号退回失败");
				}
				// 多笔处理
			}else{
				if (ArrayHelper.isNotEmpty(failedCodeList)) {
					AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
				}else{
					notify.setSuccess(BooleanType.TRUE);
					notify.setMessage("全部分配卡号退回成功");
				}
			}			
			}else{
				notify.setSuccess(BooleanType.FALSE);
				notify.setMessage("没有要处理的单子");
			}
		return jsonMapper.toJson(notify);
	}
	
	/**
	 * 分配卡号导出
	 * 2016年6月1日
	 * By 朱静越
	 * @param request
	 * @param grtQryParam 查询条件
	 * @param response
	 * @param idVal applyId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "disCardExl")
	public String disCardExl(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String idVal, RedirectAttributes redirectAttributes) {
		try {
			ExcelUtils excelutil = new ExcelUtils();
			List<DisCardEx> list = new ArrayList<DisCardEx>();
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
			if (StringUtils.isNotEmpty(idVal)) {
				String[] applyIds = idVal.split(",");
				grtQryParam.setApplyIds(applyIds);
			}
			list = disCardService.getDisCardList(grtQryParam);
			for (DisCardEx tempItem : list) {
				Double contractMoney1=tempItem.getContractMoney();
				BigDecimal bd=new BigDecimal(contractMoney1);
				Double contractMoney=bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				tempItem.setContractMoney(contractMoney);
				
				Double lendingMoney1=tempItem.getLendingMoney();
				BigDecimal bd1=new BigDecimal(lendingMoney1);
				Double lendingMoney=bd1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				tempItem.setLendingMoney(lendingMoney);
				
				tempItem.setLoanType("信借");
			}
			excelutil.exportExcel(list, FileExtension.DISCAD_NAME
					+ DateUtils.getDate("yyyyMMdd"),
					DisCardEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, 1);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "分配卡号导出失败，请重试！");
		}
		return "redirect:"
				+ adminPath
				+ "/borrow/grant/disCard/getDisCardList";
	}
	
	/**
	 * 驳回申请
	 * 2017年2月7日
	 * By 朱静越
	 * @param model
	 * @param checkVal 所选单子的参数
	 * @param remark 驳回申请的理由
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "backFrozen")
	public String backFrozen(Model model, String checkVal,
			String borrowFlag,String remark) {
		// 遍历checkVal,以';'把，每一个放到apply中
		String[] applyIds = null;
		String[] applyParam = null;
		String failContractCode = null;
		int successNum = 0;
		int failNum = 0;
		String message = "";
		AjaxNotify ajaxNotify = new AjaxNotify();
		List<String> failedCodeList = Lists.newArrayList();
		if (StringUtils.isNotEmpty(checkVal)) {
			applyIds = checkVal.split(";");
			for (int i = 0; i < applyIds.length; i++) {
				applyParam = applyIds[i].split(",");
				try {
					disCardService.backFlozen(applyParam,remark);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(
							"backFrozen:分配卡号驳回申请发生异常,apply={}",
							new Object[] { applyParam});
					failContractCode = applyParam[1];
				}
				if (StringUtils.isNotEmpty(failContractCode)) {
					failedCodeList.add(failContractCode);
					failNum++;
				}else{
					successNum++;
				}
			}
		}
		// 单笔修改
		if (null!= applyIds && applyIds.length == 1) {
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				message = "修改失败";
			}else{
				message = "修改成功";
			}
		// 多笔修改
		}else {
			if (ArrayHelper.isNotEmpty(failedCodeList)) {
				AjaxNotifyHelper.wrapperNotifyInfo(ajaxNotify, failedCodeList, successNum, failNum);
				message = ajaxNotify.getMessage();
			}else{
				message = "修改成功";
			}
		}		
		return message;
	}
}
