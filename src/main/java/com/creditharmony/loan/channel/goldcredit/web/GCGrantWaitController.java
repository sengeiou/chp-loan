package com.creditharmony.loan.channel.goldcredit.web;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ChkBackReason;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantAuditEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.ParamEx;
import com.creditharmony.loan.borrow.grant.service.GrantAuditService;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCWaitHelper;
import com.creditharmony.loan.channel.goldcredit.service.GCGrantService;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.google.common.collect.Maps;

/**
 * 金信待放款审核列表
 * 
 * @Class Name GrantSureController
 * @author 张建雄
 * @Create In 2015年11月27日
 */

@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/grantWait/")
public class GCGrantWaitController extends BaseController {
	@Autowired
	private GrantAuditService grantAuditService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private GCGrantService grantService;
	@Autowired
	private ContractDao contractDao;
	
	/**
	 * 查询金信放款审核列表信息
	 * @author songfeng
	 * @Create 2017年2月21日
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAuditGrantInfo")
	protected String getAuditGrantInfo(Model model, LoanFlowQueryParam grtQryParam,HttpServletRequest request,
			HttpServletResponse response) {
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		//从数据库查询待放款审核列表数据
		Page<GrantAuditEx> gcAuditGrantList =loanGrantService.getAuditGrantList(new Page<GrantAuditEx>(request, response), grtQryParam);
		List<GrantAuditEx> workItems = gcAuditGrantList.getList();
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (GrantAuditEx loanFlowWorkItemView : workItems) {
			loanFlowWorkItemView.setUrgentFlag(DictCache.getInstance().getDictLabel("jk_urgent_flag",loanFlowWorkItemView.getUrgentFlag()));
			loanFlowWorkItemView.setCautionerDepositBank(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK, loanFlowWorkItemView.getCautionerDepositBank()));
		}
		//统计金信放款审核页面中的放款笔数和放款金额
		Double totalGrantMoney = 0.0;
		int size = 0;
		if(workItems!=null){
			for (GrantAuditEx workItemView : workItems) {
				totalGrantMoney+=workItemView.getLendingMoney();
			}
			size = workItems.size();
		}
		List<CityInfo> provinceList = cityManager.findProvince();
		if (StringUtils.isNotEmpty(grtQryParam.getProvinceCode())) {
			List<CityInfo> cityList = cityManager.findCity(grtQryParam.getProvinceCode());
			model.addAttribute("cityList", cityList);
		}
		List<LoanGrantEx> productList = loanGrantService.findProduct();
		//提交批次列表
		try {
			Map<String,String> batch = Maps.newHashMap();
			batch.put("loanFlag", ChannelFlag.JINXIN.getCode());
			batch.put("batch", "grantPch");
			batch.put("dictLoanStatus", LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
			List<String> submitBatchList = loanGrantService.findSubmitBatchList(batch);
			//放款批次
			batch.put("batch", "grantBatch");
			List<String> grantBatchList = loanGrantService.findSubmitBatchList(batch);
			model.addAttribute("submitBatchLists", submitBatchList);
			model.addAttribute("grantBatchLists", grantBatchList);
		} catch (Exception e) {
			logger.error("在金信放款审核中，查询提交批次集合失败，原因是："+e.getMessage());
		}
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("totalItem", size);
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", workItems);
		model.addAttribute("page", gcAuditGrantList);
		return "channel/goldcredit/goldCredit_grantWaitList" ;
	}
	
	/**
	 * 显示放款审核详细页面
	 * 2017年3月20日
	 * By 朱静越
	 * @param model
	 * @param checkVal
	 * @return
	 */
	@RequestMapping(value = "showAuditPage")
	public String showAuditPage(Model model, String checkVal) {
		List<GrantAuditEx> resultView = new ArrayList<GrantAuditEx>();
		// 根据applyId查询审核页面中要显示的字段
		String[] applys = null;
		if (StringUtils.isNotEmpty(checkVal)) {
			applys = checkVal.split(",");
			//废除工作流  从数据库查询数据
			LoanFlowQueryParam grtQryParam=new LoanFlowQueryParam();
			grtQryParam.setApplyIds(applys);
			resultView =loanGrantService.getAuditGrantList(grtQryParam);
		}
		
		StringBuffer messageBuffer = new StringBuffer();
		String isExistSplit="";
		messageBuffer.append(GrantCommon.ISSPLIT_DATA_MESSAGE);
		for(GrantAuditEx ex:resultView){
			if(!"0".equals(ex.getIssplit())){
				messageBuffer.append(ex.getCustomerName()+ " " + ex.getContractCode() + ",");
				isExistSplit="1";
			}
		}
		String message = messageBuffer.toString();
		message = message.substring(0, message.length()-1);
		
		model.addAttribute("list", resultView);
		model.addAttribute("messageBuffer", messageBuffer);
		model.addAttribute("isExistSplit", isExistSplit);
		return "channel/goldcredit/goldCredit_loanflow_grantAudit_approve_0";
	}
	
	/**
	 * 导出Excel文件
	 * 2016年3月7日
	 * xiaoniu.hu
	 * @param request
	 * @param queryParam 页面检索条件
	 * @param response
	 * @param loanCodes 选中的数据行
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,LoanFlowQueryParam queryParam,HttpServletResponse response,
			String loanCodes) {
		GrantUtil.setStoreOrgIdQuery(queryParam);
		List<String> loanCodeList = null;
		if (StringUtils.isEmpty(loanCodes)) {
			// 没有选中的，默认为全部,通过
			//loanCodeList= super.getAllLoanCodes(getWorkFlowQueryCondtion(queryParam), LoanFlowQueue.LOAN_BALANCE_MANAGER);
			//废除工作流  从数据库查询数据
			loanCodeList=new ArrayList<String>();
			List<GrantAuditEx> workItems =loanGrantService.getAuditGrantList(queryParam);
			for(GrantAuditEx item : workItems) {
				loanCodeList.add(item.getLoanCode() );
			}
		} else {
			// 如果有选中的单子,将选中的单子导出
			loanCodeList=Arrays.asList(loanCodes.split(","));   
		}
		//if (loanCodeList != null && loanCodeList.size() != 0) {
			//重写SQL按金信的格式获取数据
			Map<String,Object> queryMap = Maps.newHashMap();
			queryMap.put("list", loanCodeList);
			ExportGCWaitHelper.exportData(queryMap, response);
		//}
	}
	
    /**
	 * 审核退回处理,退回节点，更新表数据，审核结果，审核专员，退回原因 2015年12月11日 By 朱静越 修改单子的状态，修改放款记录表
	 * 
	 * @param apply
	 *            流程属性
	 * @param result
	 *            审核退回原因
	 * @param responseURL
	 *            退回的节点
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "grantAuditBack")
	public String granAuditBack(ParamEx param, String result) {
		String resultName = null;
		List<DistachParamEx> list = param.getList();
		if (ArrayHelper.isNotEmpty(list)) {
			try {
				for (int i = 0; i < list.size(); i++) {
					//废除工作流  放款审核退回处理:1.判断：处理中和待查账的数据不允许退回 2.更新放款表 
					//3.更新借款状态  4.审核退回，催收服务费的处理 5.插入历史
					LoanGrant loanGrant=new LoanGrant();
					loanGrant.setContractCode(list.get(i).getContractCode());
					loanGrant.setGrantBackMes(result);// 更新退回原因
					loanGrant.setCheckTime(new Date());
					loanGrant.setCheckEmpId(UserUtils.getUser().getId());
					loanGrant.setCheckResult(VerityStatus.RETURN.getCode()); // 审核结果为审核退回
					loanGrant.setGrantFailResult(""); // 放款审核退回，将失败原因更新为空
					loanGrant.setGrantRecepicResult(""); // 放款审核退回，将回盘结果更新为空
					LoanInfo loanInfo=new LoanInfo();
					loanInfo.setLoanCode(list.get(i).getLoanCode());
					loanInfo.setApplyId(list.get(i).getApplyId());
					loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode());
					// 获得放款审核退回原因name,插入历史
					for (ChkBackReason s : ChkBackReason.values()) {
						if (s.getCode().equals(result)) {
							resultName = s.getName();
						}
					}
					//操作放款审核退回
					loanGrantService.aduitGrantBackInfo(loanInfo, loanGrant, resultName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("method:grantAuditBack发生异常，异常信息为："+e.toString());
			}
			return BooleanType.TRUE;
		} else {
			return BooleanType.FALSE;
		}
	}
    

	/**
	 * 放款审核通过，更新放款时间，放款状态，审核结果， 2015年12月11日 By 朱静越 修改单子状态，修改放款记录表
	 * 
	 * @param apply
	 *            流程属性
	 * @param result
	 *            放款时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "grantAuditOver")
	public String granAuditOver(ParamEx param, Date result) {
		List<DistachParamEx> list = param.getList();
		String flag = BooleanType.FALSE;
		int treated = 0;
		if (ArrayHelper.isNotEmpty(list)) {
			try {
				logger.info("批量金信待放款审核start【"+list.size()+"】条数据");
				for (int i = 0; i < list.size(); i++) {
					DistachParamEx distach = list.get(i);
					logger.info("批量金信待放款审核【"+distach.getApplyId()+"】");
					//废除工作流  操作数据库 更新放款信息
					LoanGrant loanGrant=new LoanGrant();
					loanGrant.setContractCode(distach.getContractCode());
					User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
					loanGrant.setCheckEmpId(user.getId());
					loanGrant.setCheckResult(VerityStatus.PASS.getCode());
					loanGrant.setCheckTime(new Date());
					loanGrant.setLendingTime(result);
					//更新借款表中借款状态
					LoanInfo loanInfo=new LoanInfo();
					loanInfo.setApplyId(distach.getApplyId());
					loanInfo.setLoanCode(list.get(i).getLoanCode());
					loanInfo.setDictLoanStatus(LoanApplyStatus.REPAYMENT.getCode());
					Contract contract=contractDao.findByLoanCode(list.get(i).getLoanCode());
					//放款审核通过
					loanGrantService.aduitGrantSureInfo(loanInfo, loanGrant,contract);
					this.addArchives(distach.getContractCode());
					treated++;
				}
				flag = BooleanType.TRUE;
			} catch (Exception e) {
				logger.error("method:grantAuditOver放款审核通过处理，发生异常，信息为："+e.getMessage());
				e.printStackTrace();
				flag = BooleanType.FALSE;
			}
		} else {
			flag = BooleanType.FALSE;
		}
		logger.info("批量金信待放款审核end处理【"+treated+"】条数据");
		return flag;
	}
    
	public void addArchives(String contractCode){
		Map map1 = new HashMap();
		map1.put("contractCode", contractCode);
		map1.put("fileNum", "309110122|309110125");
		map1.put("fileName", "委托划扣|其它材料");
		contractDao.addArchives(map1);
	}
	
}
