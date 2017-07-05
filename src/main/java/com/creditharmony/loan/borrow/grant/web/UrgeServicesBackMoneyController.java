package com.creditharmony.loan.borrow.grant.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.UrgeHistory;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesBackMoneyEx;
import com.creditharmony.loan.borrow.grant.service.UrgeServicesBackMoneyService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.google.common.collect.Lists;

/**
 * 催收服务费返还申请列表中的各种操作
 * @Class Name UrgeServicesBackMoneyController
 * @author 张振强
 * @Create In 2016年1月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/urgeServicesBack")
public class UrgeServicesBackMoneyController extends BaseController {
	
	@Autowired
	private UrgeServicesBackMoneyService urgeServicesBackMoneyService;
	@Autowired
	private CityInfoDao cityInfoDao;

	/**
	 * 催收服务费返款申请列表，查询条件为列表中的所有，按照条件靠前显示。 
	 * 2015年12月16日 By 张振强
	 * @param model 
	 * @param urgeServicesBackMoneyEx 查询条件
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面地址
	 */
	@RequestMapping(value = "urgeBackInfo")
	public String urgeBackInfo(Model model,
			UrgeServicesBackMoneyEx urgeServicesBackMoneyEx,
			HttpServletRequest request, HttpServletResponse response) {
		//	//数据权限控制
	    String queryRight = DataScopeUtil.getDataScope("i", SystemFlag.LOAN.value);
	    urgeServicesBackMoneyEx.setQueryRight(queryRight);
		
		Page<UrgeServicesBackMoneyEx> urgePage = urgeServicesBackMoneyService
				.selectBackMoneyApply(new Page<UrgeServicesBackMoneyEx>(
						request, response), urgeServicesBackMoneyEx);
		Map<String,Dict> dictMap = null;
		if(ArrayHelper.isNotEmpty(urgePage.getList())){
			dictMap = DictCache.getInstance().getMap();
		}
		for(UrgeServicesBackMoneyEx us:urgePage.getList()){
			us.setBankNameLabel(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK,us.getBankName()));
			us.setDictLoanStatusLabel(DictUtils.getLabel(dictMap, LoanDictType.LOAN_STATUS,us.getDictLoanStatus()));
			us.setDictPayStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.URGE_REPAY_STATUS,us.getDictPayStatus()));
			us.setDictPayResultLabel(DictUtils.getLabel(dictMap,LoanDictType.PAYBACK_FEE_RESULT,us.getDictPayResult()));
			us.setLoanFlagLabel(DictCache.getInstance().getDictLabel("jk_channel_flag",us.getLoanFlag()));
			us.setModelLabel(DictCache.getInstance().getDictLabel("jk_loan_model",us.getModel()));
		}
		model.addAttribute("urgePage", urgePage);
		model.addAttribute("UrgeServicesBackMoneyEx", urgeServicesBackMoneyEx);
		return "borrow/grant/urgeBackApply";
	}

	/**
	 * 发送返款申请
	 * 如果有选中的，将选中的单子进行发送返款申请，
	 * 如果没有选中，将查询条件下的所有为待申请返款和返款退回发送返款申请
	 * 2015年12月21日
	 * By 张振强
	 * @param checkVal 要进行发送返款申请的id
	 * @param request
	 * @param urgeBackRe
	 * @param response
	 * @return
	 */
	//TODO 需要事务测试
	@ResponseBody
	@RequestMapping(value = "sendBackApply")
	public String sendBackApply(String checkVal, HttpServletRequest request,
			UrgeServicesBackMoneyEx urgeBackRe,String channelFlag, HttpServletResponse response) {
		AjaxNotify notify = new AjaxNotify();
		if(channelFlag!=null && !channelFlag.equals(""))
			urgeBackRe.setChannelFlag(channelFlag);
		else
			urgeBackRe.setChannelFlag("-1");
		try {
			if (StringUtils.isNotEmpty(checkVal)) {
				String[] ids = checkVal.split(",");
				UrgeServicesBackMoneyEx urgeBackResult = new UrgeServicesBackMoneyEx();
				UrgeHistory urgeHistory = new UrgeHistory();
				UrgeServicesBackMoney urgeBack = new UrgeServicesBackMoney();
				urgeBack.setDictPayStatus(UrgeRepay.REPAY_TO.getCode());
				urgeBack.setBackApplyPayTime(new Date());
				urgeBack.setBackApplyBy(UserUtils.getUser().getName());
				urgeBack.setBackApplyDepartment(UserUtils.getUser().getDepartment().getName());
				List<String> failedCodeList = Lists.newArrayList();
				String failedContractCode = null;
				int successNum = 0;
				int failNum = 0;
				for (String itemId : ids) {
					urgeBackResult = urgeServicesBackMoneyService.selSendApply(itemId);
					if (UrgeRepay.REPAY_TO_APPLY.getCode().equals(
							urgeBackResult.getDictPayStatus())
							|| UrgeRepay.REPAY_BACK.getCode().equals(
									urgeBackResult.getDictPayStatus())) {
						urgeBack.setId(itemId);
						try {
							urgeServicesBackMoneyService.saveBackApplyAndHis(urgeBackResult, urgeHistory, urgeBack,channelFlag);
						} catch (Exception e) {
							logger.info("催收服务费返款失败"+e.getMessage());
							failedContractCode = urgeBackResult.getContractCode();
						}
						if(StringUtils.isNotEmpty(failedContractCode)){
							failedCodeList.add(failedContractCode);
							failNum++;
						}else{
							successNum++;
						}
					}
				}
				AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
			} else{
				List<UrgeServicesBackMoneyEx> urgeBackList = urgeServicesBackMoneyService.selectBackMoneyApplyNo(urgeBackRe);
				UrgeServicesBackMoneyEx urgeBackResult = new UrgeServicesBackMoneyEx();
				UrgeHistory urgeHistory = new UrgeHistory();
				UrgeServicesBackMoney urgeBack = new UrgeServicesBackMoney();
				urgeBack.setDictPayStatus(UrgeRepay.REPAY_TO.getCode());
				urgeBack.setBackApplyPayTime(new Date());
				urgeBack.setBackApplyBy(UserUtils.getUser().getName());
				urgeBack.setBackApplyDepartment(UserUtils.getUser().getDepartment().getName());
				List<String> failedCodeList = Lists.newArrayList();
				String failedContractCode = null;
				int successNum = 0;
				int failNum = 0;
				for(UrgeServicesBackMoneyEx backMoneyExItem : urgeBackList){
					if (UrgeRepay.REPAY_TO_APPLY.getCode().equals(backMoneyExItem.getDictPayStatus())
							|| UrgeRepay.REPAY_BACK.getCode().equals(backMoneyExItem.getDictPayStatus())) {
						urgeBack.setId(backMoneyExItem.getId());
						urgeBackResult = backMoneyExItem;
						try {
							urgeServicesBackMoneyService.saveBackApplyAndHis(urgeBackResult, urgeHistory, urgeBack,channelFlag);
						} catch (Exception e) {
							e.printStackTrace();
							failedContractCode = urgeBackResult.getContractCode();
						}
						if(StringUtils.isNotEmpty(failedContractCode)){
							failedCodeList.add(failedContractCode);
							failNum++;
						}else{
							successNum++;
						}
					}
				}
				AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
			}
		} catch (Exception e) {
			notify.setMessage("发送返款申请失败");
			notify.setSuccess(BooleanType.FALSE);
		}
		return jsonMapper.toJson(notify);
	}

	/**
	 * 导出催收服务费待申请 
	 * 2015年12月23日 By 张振强
	 * @param request
	 * @param response
	 * @param checkVal
	 * @return none
	 */
	@RequestMapping(value = "exportBackApply")
	public void exportBackApply(HttpServletRequest request,UrgeServicesBackMoneyEx urgeBackRe,
			HttpServletResponse response, String checkVal) {
		ExcelUtils excelutil = new ExcelUtils();
		
		List<UrgeServicesBackMoneyEx> exportUrgeBackList = new ArrayList<UrgeServicesBackMoneyEx>();
	    String queryRight = DataScopeUtil.getDataScope("i", SystemFlag.LOAN.value);
	    urgeBackRe.setQueryRight(queryRight);
		try {
			// 为空，默认为全部
			if (StringUtils.isEmpty(checkVal)) {
				exportUrgeBackList = urgeServicesBackMoneyService.selectBackMoneyApplyNo(urgeBackRe);
			} else {
				String[] ids = checkVal.split(",");
				for (int i = 0; i < ids.length; i++) {
					// 根据催收返款id进行查询要导出的excel
					UrgeServicesBackMoneyEx urge  = new UrgeServicesBackMoneyEx();
					urge.setId(ids[i]);
					exportUrgeBackList.add(urgeServicesBackMoneyService.selectBackMoneyApplyNo(urge).get(0));
				}
			}
			if(ArrayHelper.isNotEmpty(exportUrgeBackList)){
				for(UrgeServicesBackMoneyEx ue :exportUrgeBackList){
					DecimalFormat df = new DecimalFormat("#.00");
					if (StringUtils.isNotEmpty(String.valueOf(ue.getGrantAmount()))) {
						ue.setGrantAmount(new BigDecimal(df.format(ue.getGrantAmount())));
					}else{
						ue.setGrantAmount(BigDecimal.ZERO);
					}
					
					if (StringUtils.isNotEmpty(String.valueOf(ue.getContractAmount()))) {
						ue.setContractAmount(new BigDecimal(df.format(ue.getContractAmount())));
					}else{
						ue.setContractAmount(BigDecimal.ZERO);
					}
					
					if (StringUtils.isNotEmpty(String.valueOf(ue.getFeeUrgedService()))) {
						ue.setFeeUrgedService(new BigDecimal(df.format(ue.getFeeUrgedService())));
					}else{
						ue.setFeeUrgedService(BigDecimal.ZERO);
					}
					
					if (StringUtils.isNotEmpty(String.valueOf(ue.getPaybackBackAmount()))) {
						ue.setPaybackBackAmount(new BigDecimal(df.format(ue.getPaybackBackAmount())));
					}else{
						ue.setPaybackBackAmount(BigDecimal.ZERO);
					}
				}
			}
			
			excelutil.exportExcel(exportUrgeBackList, FileExtension.URGE_BACK_APPLY,
					UrgeServicesBackMoneyEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("催收服务费待申请导出excel出现异常.",e);
		}
	}

	/**
	 * 查看历史 
	 * 2015年12月8日 By 张振强
	 * @param model
	 * @param request
	 * @param response
	 * @param urgeHistory
	 * @return 要进行跳转的页面地址
	 */
	@RequestMapping(value = "grantJump")
	public String grantJump(Model model, HttpServletRequest request,
			HttpServletResponse response, UrgeHistory urgeHistory) {
		Page<UrgeHistory> page = urgeServicesBackMoneyService
				.selectUrgeHistory(new Page<UrgeHistory>(request, response), urgeHistory);
		model.addAttribute("page", page);
		model.addAttribute("urgeHistory", urgeHistory);
		return "borrow/grant/urgeBackApplyHistory";
	}

}
