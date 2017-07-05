package com.creditharmony.loan.borrow.grant.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantUrgeBackEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeCheckDoneEx;
import com.creditharmony.loan.borrow.grant.service.GrantUrgeBackService;
import com.creditharmony.loan.borrow.grant.service.UrgeCheckDoneService;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;


/**
 * 放款失败催收费退回列表
 * 该列表中的单子为放款成功，放款审核退回的单子；数据为催收服务费成功的单子的合并；
 * @Class Name GrantUrgeBackController
 * @author 朱静越
 * @Create In 2016年1月21日
 */
//TODO 催收服务费退回，暂时不做修改，需求在发生变化
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/grantDeductsBack")
public class GrantUrgeBackController extends BaseController {
	
	@Autowired
	private GrantUrgeBackService grantUrgeBackService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private UrgeCheckDoneService urgeCheckDoneService;
	@Autowired
	private DealPaybackService dealPaybackService;
	
	/**
	 * 页面初始化
	 * 2016年1月27日
	 * By 朱静越
	 * @param model
	 * @param grantUrgeBackEx 查询条件
	 * @return 符合条件的列表数据
	 */
	@RequestMapping(value = "grantDeductsBackInfo")
	public String grantDeductsBackInfo(Model model, HttpServletRequest request,
			HttpServletResponse response, GrantUrgeBackEx grantUrgeBackEx) {
	    if(ObjectHelper.isEmpty(grantUrgeBackEx.getStoreOrgIds()) || grantUrgeBackEx.getStoreOrgIds().length<1){
	        grantUrgeBackEx.setStoreOrgIds(null);
	    }
		Page<GrantUrgeBackEx> urgePage = grantUrgeBackService
				.selectUrgeBackList(new Page<GrantUrgeBackEx>(request, response), grantUrgeBackEx);
		if (ArrayHelper.isNotEmpty(urgePage.getList())) {
			for(GrantUrgeBackEx ex :urgePage.getList()){
				ex.setReturnStatus(DictCache.getInstance().getDictLabel(LoanDictType.FEE_RETURN, ex.getReturnStatus()));
				ex.setLoanFlag(DictCache.getInstance().getDictLabel(LoanDictType.CHANNEL_FLAG, ex.getLoanFlag()));
			}
		}
		List<CityInfo> provinceList = cityManager.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("grantUrgeList", urgePage);
		model.addAttribute("GrantUrgeBackEx", grantUrgeBackEx);
		return "borrow/grant/grantUrgeBackList";
	}
	
	/**
	 * 根据退回表的id进行查询跳转
	 * 2016年2月22日
	 * By 朱静越
	 * @param model 
	 * @param id 退回的id
	 * @return 要跳转的页面
	 */
	@RequestMapping(value = "toDeal")
	public String toDeal(Model model,HttpServletRequest request,HttpServletResponse response,String urgeId){
		UrgeCheckDoneEx urgeCheckDoneEx = new UrgeCheckDoneEx();
		List<UrgeCheckDoneEx> paybackApplyList = new ArrayList<UrgeCheckDoneEx>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response),null);
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
		urgeCheckDoneEx.setrServiceChargeId(urgeId);
		paybackApplyList = urgeCheckDoneService.selCheckDone(new Page<UrgeCheckDoneEx>(request, response), urgeCheckDoneEx).getList();
		if(ArrayHelper.isNotEmpty(paybackApplyList)){
			// 设置转账表的关联id，为催收申请表的id
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getCheckApplyId());
			paybackTransferInfo.setRelationType(TargetWay.SERVICE_FEE.getCode());
			// 转账记录表中的信息
			paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
			if(ArrayHelper.isNotEmpty(paybackTransferInfoList)){
				for (int i = 0; i < paybackTransferInfoList.size(); i++) {
					paybackTransferInfoList.get(i).setUploadName(UserUtils.getUser().getName());
				}
			}
		}
		UrgeCheckDoneEx uc = paybackApplyList.get(0);
		String urgeApplyStatus = DictCache.getInstance().getDictLabel(LoanDictType.URGE_COUNTEROFFER_RESULT,uc.getUrgeApplyStatus());
		uc.setUrgeApplyStatusLabel(urgeApplyStatus);
		String dictLoanStatus = DictCache.getInstance().getDictLabel(LoanDictType.LOAN_STATUS,uc.getDictLoanStatus());
		uc.setDictLoanStatusLabel(dictLoanStatus);
		String loanFlag = DictCache.getInstance().getDictLabel(LoanDictType.CHANNEL_FLAG,uc.getLoanFlag());
		uc.setLoanFlagLabel(loanFlag);
		model.addAttribute("paybackTransferInfoList",paybackTransferInfoList);
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		model.addAttribute("paybackApply",uc);
		return "borrow/grant/urgeMatchDoneInfo";
	}
	
	/**
	 * 对选中的数据进行退款确认，查找出中间人信息
	 * 2016年2月22日
	 * By 朱静越
	 * @param model
	 * @param request
	 * @param response
	 * @param midPerson 中间人查询条件
	 * @return 中间人list
	 */
	@RequestMapping(value = "backSure")
	public String backSure(Model model,HttpServletRequest request,HttpServletResponse response,MiddlePerson midPerson){
		Page<MiddlePerson> middlePage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request,
				response), midPerson);
		if (midPerson != null) {
			model.addAttribute("midPerson", midPerson);
		}
		model.addAttribute("middlePage", middlePage);
		return "borrow/grant/grantUrgeBackSure";
	}
	
	/**
	 * 根据合同编号查找单子历史，查找借款状态变更表，还款操作流水表，如果使用共通的，则要使用applyId进行查询
	 * 2016年2月16日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @param request
	 * @param response
	 * @param model
	 * @return 获得的历史
	 */
	@RequestMapping(value = "history")
	public String history(String contractCode,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<GrantUrgeBackEx> page = grantUrgeBackService.selHistory(
				new Page<GrantUrgeBackEx>(request, response), contractCode);
		model.addAttribute("page", page);
		return "borrow/grant/grantUrgeBackHistory";
	}
	
	/**
	 * 确定要进行处理的单子的id，确认退款按钮操作的单子，如果没有进行选中，则为查询条件下的所有状态为“待退款”的单子
	 * 2016年2月15日
	 * By 朱静越
	 * @param backId 要进行处理的单子的id
	 * @return 要进行处理的单子的id
	 */
	@ResponseBody
	@RequestMapping(value = "sureDeal")
	public String sureDeal(String backId,GrantUrgeBackEx grantUrgeBackEx){
		if (StringUtils.isEmpty(backId)) {
		    if(ObjectHelper.isEmpty(grantUrgeBackEx.getStoreOrgIds()) || grantUrgeBackEx.getStoreOrgIds().length<1){
	            grantUrgeBackEx.setStoreOrgIds(null);
	        }
			List<GrantUrgeBackEx> grantUrgeList = grantUrgeBackService.selectUrgeBackListNo(grantUrgeBackEx);
			if (ArrayHelper.isNotEmpty(grantUrgeList)) {
				for (int i = 0; i < grantUrgeList.size(); i++) {
					if (grantUrgeList.get(i).getReturnStatus().equals(FeeReturn.RETURNING.getCode())) {
						backId +=","+grantUrgeList.get(i).getUrgeId();
					}
				}
			}
		}
		return backId;
	}
	
	/**
	 * 查看已收记录页面，根据催收主表id，进行查询
	 * 2016年2月16日
	 * By 朱静越
	 * @param model 
	 * @param backDoneId 列表中的关联催收服务费id
	 * @return 要进行查看的页面
	 */
	@RequestMapping(value = "urgeDone")
	public String urgeDone(String backDoneId,Model model){
		List<GrantUrgeBackEx> grantUrgeList = grantUrgeBackService.backDone(backDoneId);
		for (GrantUrgeBackEx grantUrgeBackEx:grantUrgeList) {
			grantUrgeBackEx.setSplitBackResult(DictCache.getInstance().getDictLabel(LoanDictType.URGE_COUNTEROFFER_RESULT,grantUrgeBackEx.getSplitBackResult()));
		}
		model.addAttribute("grantUrgeList", grantUrgeList);
		return "borrow/grant/grantUrgeBackDone";
	}
	
	/**
	 * 确认退款，点击确认，根据选中的单子的催收id，进行更改单子的状态以及退回时间，退回中间人id
	 * 2016年2月1日
	 * By 朱静越
	 * @param middleCode 中间人编码
	 * @param backDate 确认退款日期
	 * @param backId 要进行退款的id
	 * @return 退款的结果
	 */
	@ResponseBody
	@RequestMapping(value = "grantUrgeSure")
	public String grantUrgeSure(String middleCode,Date backDate,String backId){
		GrantUrgeBackEx grantUrgeBackEx = new GrantUrgeBackEx();
		String[] id = null;
		String idstring = null;
		StringBuilder parameter = new StringBuilder();
		try {
			if (StringUtils.isNotEmpty(backId)) {
				id=backId.split(",");
				for (int i = 0; i < id.length; i++) {
					parameter.append("'"+id[i]+"',");
				}
			}
			if(StringUtils.isNotEmpty(parameter)){
				idstring = parameter.toString();
				idstring = idstring.substring(0,parameter.lastIndexOf(","));
			}
			grantUrgeBackEx.setUrgeId(idstring);
			grantUrgeBackEx.setReturnIntermediaryId(middleCode);
			grantUrgeBackEx.setReturnStatus(FeeReturn.RETURNED.getCode());
			grantUrgeBackEx.setReturnTime(backDate);
			// 调用方法进行更新
			grantUrgeBackService.updUrgeBack(grantUrgeBackEx);
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 导出要进行处理的单子，因为没有给出条件，默认导出的数据为退款状态为待退款的数据
	 * 2016年1月28日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param urgeId 要进行导出的单子的信息
	 * @return null
	 */
	@RequestMapping(value = "importBack")
	public void importBack(HttpServletRequest request,
			HttpServletResponse response, String urgeId,
			GrantUrgeBackEx grantUrgeRe) {
		ExcelUtils excelutil = new ExcelUtils();
		StringBuilder parameter = new StringBuilder();
		String[] id=null;
		 if(ObjectHelper.isEmpty(grantUrgeRe.getStoreOrgIds()) || grantUrgeRe.getStoreOrgIds().length<1){
		     grantUrgeRe.setStoreOrgIds(null);
	     }
		List<GrantUrgeBackEx> grantUrgeBackList=new ArrayList<GrantUrgeBackEx>();
		GrantUrgeBackEx grantUrgeBackEx = new GrantUrgeBackEx();
		try {
			if (StringUtils.isEmpty(urgeId)) {
				// 没有选中的，默认为全部,待退款
				List<GrantUrgeBackEx> grantUrgeList = grantUrgeBackService.selectUrgeBackListNo(grantUrgeRe);
				if (ArrayHelper.isNotEmpty(grantUrgeList)) {
					for (int i = 0; i < grantUrgeList.size(); i++) {
						if (grantUrgeList.get(i).getReturnStatus().equals(FeeReturn.RETURNING.getCode())) {
							// 对符合条件的单子进行导出
							grantUrgeBackList.add(grantUrgeList.get(i));
						}
					}
				}
			}else {
				// 如果有选中的单子,将选中的单子导出
				id=urgeId.split(",");
				for (int i = 0; i < id.length; i++) {
					parameter.append("'"+id[i]+"',");
				}
				String idstring = null;
				if(StringUtils.isNotEmpty(parameter)){
				idstring = parameter.toString();
				idstring = idstring.substring(0,parameter.lastIndexOf(","));
				}
				// 根据催收id进行查询，返回为list
				grantUrgeBackEx.setUrgeId(idstring);
				grantUrgeBackList = grantUrgeBackService.selectUrgeBackListNo(grantUrgeBackEx);
			}
		/*	for(GrantUrgeBackEx gu:grantUrgeBackList){
				BigDecimal   contractAmount=new BigDecimal(gu.getContractAmount());
				gu.setContractAmount( contractAmount.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue());
				BigDecimal   grantAmount=new BigDecimal(gu.getGrantAmount());
				gu.setGrantAmount(grantAmount.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue());
				BigDecimal   urgeMoeny=new BigDecimal(gu.getUrgeMoeny());
				gu.setUrgeMoeny(urgeMoeny.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue());
				BigDecimal   returnAmount=new BigDecimal(gu.getReturnAmount());
				gu.setReturnAmount(returnAmount.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue());
			}*/
			excelutil.exportExcel(grantUrgeBackList, FileExtension.GRANT_URGE_BACK
					+ System.currentTimeMillis(), GrantUrgeBackEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
