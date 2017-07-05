package com.creditharmony.loan.channel.goldcredit.web;

import java.text.SimpleDateFormat;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.bean.in.jxcreditor.CreditFirstReqInDetailInfo;
import com.creditharmony.adapter.bean.in.jxcreditor.CreditFirstReqInInfo;
import com.creditharmony.adapter.bean.out.jxcreditor.CreditFirstReqOutInfo;
//import com.creditharmony.adapter.bean.in.jxcreditor.CreditSectionFieldsBean;
//import com.creditharmony.adapter.bean.out.jxcreditor.ReturnBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.SendMoneyEx;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantCallUtil;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.channel.goldcredit.excel.ExportGCGrantSureHelper;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 金信待放款确认列表
 * 
 * @Class Name GoldCreditController
 * @author 路志友
 * @Create In 2016年2月23日
 */

@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/grantSure/")
public class GCGrantSureController extends BaseController {
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	@Autowired
	private ThreePartFileName threePartFileName;
	@Autowired
    private UserManager userManager;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	
	/**
	 * 查询金信待款项确认信息
	 * @author songfeng
	 * @Create 2017年2月14日
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getGCGrantInfo")
	public String getGCGrantInfo(Model model, LoanFlowQueryParam grtQryParam, HttpServletRequest request
			,HttpServletResponse response) {
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		if (ObjectHelper.isEmpty(grtQryParam.getRevisitStatus()) || grtQryParam.getRevisitStatus()[0].equals("")) {	
			grtQryParam.setRevisitStatus(null);
		}
		//回访状态的查询
		String[] revisitStatus=grtQryParam.getRevisitStatus();
		if(revisitStatus!=null&&revisitStatus.length>0){
			for(String status:revisitStatus){
				if(GrantCommon.REVISIT_STATUS_NULL_CODE.equals(status)){
					grtQryParam.setRevisitQueryName(GrantCommon.REVISIT_STATUS_NULL_CODE);
				}
			}
		}
		// 查询金信待款项列表信息
		Page<LoanFlowWorkItemView> gcGrantSureList = loanGrantService
				.getGCGrantSureList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		// 查询城市信息
		List<CityInfo> provinceList = cityManager.findProvince();
		// 查询产品信息
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		// 不是合同审核操作人员 默认为1
		String notContractAudit = YESNO.YES.getCode();
		// 款项确认权限控制
		List<Role> roleList = user.getRoleList();
		for (Role r : roleList) {
			if (/*r.getId().equals(BaseRole.CONTRACT_APPROVE_LEADER.id)
					|| r.getId().equals(BaseRole.LOANER_CONTRACT_APPROVER.id)
					|| BaseRole.CONTRACT_MAKE_LEADER.id.equals(r.getId())
					|| BaseRole.LOANER_CONTRACT_MAKER.id.equals(r.getId())
					|| BaseRole.RATE_APPROVE_LEADER_ROLE_TYPE.id.equals(r.getId())*/
					r.getId().equals(BaseRole.XINJIEYEWU_CHARGE.id)
					|| r.getId().equals(BaseRole.XINJIEYEWU_MANAGER.id)
					|| BaseRole.XINJIEYEWU_LEADER.id.equals(r.getId())) {
				notContractAudit = YESNO.NO.getCode();
				break;
			}
		}
		model.addAttribute("notContractAudit", notContractAudit);
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", gcGrantSureList.getList());
		model.addAttribute("gcGrantSureList", gcGrantSureList);
		return "channel/goldcredit/goldCredit_grantSureList";
	}
	
	 /**
	  * 第一次推送金信债权
	  * 2017年3月10日
	  * By 朱静越
	  * @param model
	  * @param gqp 封装的参数
	  * @param workItem 参数
	  * @param redirectAttributes
	  * @param response
	  * @return
	  */
    @ResponseBody
	@RequestMapping(value="dispatchFlowStatus")
	public String dispatchFlowStatus(Model model,GrantDealView gqp,WorkItemView workItem,
			RedirectAttributes redirectAttributes,String response){
    	
    	List<SendMoneyEx> datalist = Lists.newArrayList();
    	SendMoneyEx ex = new SendMoneyEx();
    	ex.setContractCode(gqp.getContractCode());
    	datalist.add(ex);
    	
    	return JxFirstSendData(datalist, redirectAttributes);
	}
	
	/**
	 * 更新单子的标识   2015年12月3日 By 路志友
	 * 
	 * @param model
	 * @param checkVal 从前台传送过来的参数，流程参数
	 * @param borrowFlag 要进行修改的标识
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "asycUpdateFlowAttr")
	public String asycUpdateFlowAttr(Model model, String checkVal,
			String borrowFlag,String flagStatus) {
		// 遍历checkVal,以';'把，每一个放到apply中
		String[] apply = null;
		String flagString = BooleanType.TRUE;
		if (StringUtils.isNotEmpty(checkVal)) {
			if (checkVal.indexOf(";") != GrantCommon.ONE) {
				apply = checkVal.split(";");
			} else {
				apply = new String[1];
				apply[0] = checkVal;
			}
			for (int i = 0; i < apply.length; i++) {
				String f = updateFlagParam(apply[i], borrowFlag,flagStatus);
				if (f.equals("false")) {
					flagString = BooleanType.FALSE;
				}
			}
		}
		return flagString;
	}
	/**
	 * 批量修改金信取消
	 * @param model
	 * @param checkVal
	 * @param borrowFlag
	 * @param flagStatus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateContractLoanInfo")
	public String updateContractLoanInfo(Model model, String checkVal,
			String borrowFlag,String flagStatus) {
		// 遍历checkVal,以';'把，每一个放到apply中
		String[] apply = null;
		String[] applyParam = null;
		String flagString = BooleanType.TRUE;
		if (StringUtils.isNotEmpty(checkVal)) {
			if (checkVal.indexOf(";") != GrantCommon.ONE) {
				apply = checkVal.split(";");
			} else {
				apply = new String[1];
				apply[0] = checkVal;
			}
			for (int i = 0; i < apply.length; i++) {
				applyParam = apply[i].split(",");
				if (applyParam.length != GrantCommon.SERVEN) {
					flagString = BooleanType.FALSE;
				}
				try{
					loanGrantService.updateFlagForLoanCode(applyParam[6], ChannelFlag.CHP.getCode());
					Contract contract = contractService.findByLoanCode(applyParam[6]);
					contract.setChannelFlag(ChannelFlag.CHP.getCode());
					contractService.updateContract(contract);
				}catch(Exception e){
					flagString = BooleanType.FALSE;
					logger.error("修改合同渠道、放款渠道失败:"+e.getMessage());
				}
			}
		}
		return flagString;
	}
	
	/**
	 * 修改单条金信取消
	 * @param model
	 * @param checkVal
	 * @param borrowFlag
	 * @param flagStatus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateContractLoanInfoForOnly")
	public String updateContractLoanInfoForOnly(WorkItemView workItem,GrantDealView gqp,String loanMarking) {
		String flagString = BooleanType.TRUE;
		try{
			String loanCode = gqp.getLoanCode();
			loanGrantService.updateFlagForLoanCode(loanCode, ChannelFlag.CHP.getCode());
			Contract contract = contractService.findByLoanCode(loanCode);
			if(contract!=null){
				contract.setChannelFlag(ChannelFlag.CHP.getCode());
				contractService.updateContract(contract);
			}
		}catch(Exception e){
			flagString = BooleanType.FALSE;
			logger.error("修改合同渠道、放款渠道失败:"+e.getMessage());
		}
		return flagString;
	}
	 /**
     * 更新标识
     * 2016年5月26日
     * By wudongyue
     * @param workItem
     * @param gqp
     * @param loanMarking
     * @return
     */
    @ResponseBody
   	@RequestMapping(value="grantUpdFlag")
    public String grantUpdFlag(WorkItemView workItem,GrantDealView gqp,String loanMarking){
    	String message = BooleanType.TRUE;
		try {
			//更新金信标志为财富标志
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setApplyId(gqp.getApplyId());
			loanInfo.setLoanCode(gqp.getLoanCode());
			loanInfo.setLoanFlag(ChannelFlag.CHP.getCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
			//更新退回标志
			Contract updContract = new Contract();
			updContract.setContractCode(gqp.getContractCode());
			updContract.setBackFlag(YESNO.NO.getCode());
			updContract.setChannelFlag(ChannelFlag.CHP.getCode());
			loanGrantService.cancelJXFlag(loanInfo, updContract,null, gqp.getGrantSureBackReason(),LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：grantUpdFlag发生异常，标识更改失败", e);
			message = "标识修改失败：" + e.getMessage();
		}
    	return message;
    }
	/**
	 * 将流程中的属性进行分隔，设置。 2015年12月3日 By 路志友
	 * 
	 * @param apply 流程需要的参数
	 * @param loanMarking 借款标识
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "updateFlagParam")
	public String updateFlagParam(String apply, String loanMarking,String flagStatus) {
		String flag = null;
		String[] applyParam = null;
		try {
			if (StringUtils.isNotEmpty(apply)) {
				//废除工作流 改借款表中的借款标识
				applyParam = apply.split(",");
				if (applyParam.length != GrantCommon.SERVEN) {
					flag = BooleanType.FALSE;
				}
				//更新金信标志为财富标志
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setApplyId(applyParam[0]);
				loanInfo.setLoanCode(applyParam[6]);
				loanInfo.setLoanFlag(ChannelFlag.CHP.getCode());
				loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
				//更新退回标志
				Contract updContract = new Contract();
				updContract.setContractCode(applyParam[5]);
				updContract.setBackFlag(YESNO.NO.getCode());
				updContract.setChannelFlag(ChannelFlag.CHP.getCode());
				loanGrantService.cancelJXFlag(loanInfo, updContract,null, flagStatus,LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
				flag = BooleanType.TRUE;
			} else {
				flag = BooleanType.FALSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：updateFlagParam，标识更改出现异常："+e.toString());
		}
		return flag;
	}
	
	/**
	 * 放款确认导入回执结果,向金信发送债权，待回访的数据不能进行发送
	 * 同时刷新列表 2016年3月7日 xiaoniu.hu
	 * 
	 * @param request
	 * @param model
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "importResult")
	public String importResult(MultipartRequest request, ModelMap model,
		@RequestParam(value = "file", required = false) MultipartFile file,RedirectAttributes redirectAttributes) {
	
		ExcelUtils excelutil = new ExcelUtils();
		
		@SuppressWarnings("unchecked")
		List<SendMoneyEx> datalist = (List<SendMoneyEx>)excelutil.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
				SendMoneyEx.class);
		//保存待回访的数据
		List<SendMoneyEx> revisitStatusWaitContractCodeList = Lists.newArrayList();
		if (datalist != null && datalist.size() != 0) {
			int i = 0;
			for (SendMoneyEx sendMoneyEx : datalist) {
				if (StringUtils.isNotEmpty(sendMoneyEx.getContractCode())) {
					i ++;
					Contract contract = contractService.findByContractCode(sendMoneyEx.getContractCode());
					if(contract != null && (GrantCommon.REVISIT_STATUS_WAIT_CODE.equals(contract.getRevisitStatus())||
							GrantCommon.REVISIT_STATUS_FAIL_CODE.equals(contract.getRevisitStatus()))){
						revisitStatusWaitContractCodeList.add(sendMoneyEx);
					}
				}
			}
			if (i == 0) {
				addMessage(redirectAttributes, "上传回执结果失败,合同编号不能为空!");
				return "redirect:" + adminPath
						+ "/channel/goldcredit/grantSure/getGCGrantInfo";
			}
			if(revisitStatusWaitContractCodeList.size() > 0){
				StringBuffer messageBuffer = new StringBuffer();
				messageBuffer.append(GrantCommon.REVISIT_STATUS_INFO_TIP);
				for(SendMoneyEx sme : revisitStatusWaitContractCodeList){
					messageBuffer.append(sme.getCustomerName()+ " " + sme.getContractCode() + ",");
				}
				String message = messageBuffer.toString();
				message = message.substring(0, message.length()-1);
				addMessage(redirectAttributes, message);
				return "redirect:" + adminPath
						+ "/channel/goldcredit/grantSure/getGCGrantInfo";
			}
			JxFirstSendData(datalist, redirectAttributes);
		} 
		return "redirect:" + adminPath
				+ "/channel/goldcredit/grantSure/getGCGrantInfo";
	}
	
	/**
	 * 放款确认导入退回结果,修改单子的状态
	 * 因为通过拆分的数据允许退回，所以在上传的时候需要进行判断
	 * 
	 * @param request
	 * @param model
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "backImportResult")
	public String backImportResult(MultipartRequest request, ModelMap model,
		@RequestParam(value = "file", required = false) MultipartFile file,RedirectAttributes redirectAttributes) {
	
		ExcelUtils excelutil = new ExcelUtils();
		
		@SuppressWarnings("unchecked")
		List<SendMoneyEx> datalist = (List<SendMoneyEx>)excelutil.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
				SendMoneyEx.class);
		//保存拆分数据
		List<SendMoneyEx> splitContractCodeList = Lists.newArrayList();
		if (datalist != null && datalist.size() != 0) {
			int x = 0;
			for(int i=0;i<datalist.size();i++){
				if (StringUtils.isNotEmpty(datalist.get(i).getContractCode())) {
					x ++;
					Contract contract = contractService.findByContractCode(datalist.get(i).getContractCode());
					if(contract!=null){
						datalist.get(i).setLoanCode(contract.getLoanCode());
					}
					//查询已经拆分的数据
					LoanInfo loanInfo=loanInfoService.selectByLoanCode(datalist.get(i).getContractCode());
					if(loanInfo!=null&&!GrantCommon.ISSPLIT_DATA_CODE.equals(loanInfo.getIssplit())){
						splitContractCodeList.add(datalist.get(i));
					}
				}
			}
			if (x == 0) {
				addMessage(redirectAttributes, "上传退回结果失败,合同编号不能为空!");
				return "redirect:" + adminPath
						+ "/channel/goldcredit/grantSure/getGCGrantInfo";
			}
			if(splitContractCodeList.size() > 0){
				StringBuffer messageBuffer = new StringBuffer();
				messageBuffer.append(GrantCommon.ISSPLIT_DATA_MESSAGE);
				for(SendMoneyEx sme : splitContractCodeList){
					messageBuffer.append(sme.getCustomerName()+ " " + sme.getContractCode() + ",");
				}
				String message = messageBuffer.toString();
				message = message.substring(0, message.length()-1);
				addMessage(redirectAttributes, message);
				return "redirect:" + adminPath
						+ "/channel/goldcredit/grantSure/getGCGrantInfo";
			}
			String contractCode="";
			try{
				for(int i=0;i<datalist.size();i++){
					contractCode = datalist.get(i).getContractCode();
					Contract contract=contractDao.findByContractCode(contractCode);
		    		if (contract!=null) {
						//废除工作流 操作数据库从待款项确认到合同审核
						LoanInfo loanInfo=new LoanInfo();
						loanInfo.setLoanCode(contract.getLoanCode());
						loanInfo.setApplyId(contract.getApplyId());
						loanInfo.setDictLoanStatus(LoanApplyStatus.PAYMENT_BACK.getCode());
						//金信待款项确认退回到合同审核  修改orderField 用于排序
						LoanInfo loanInfoOrder=new LoanInfo();
						loanInfoOrder.setApplyId(contract.getApplyId());
						loanInfoOrder.setDictLoanStatus(LoanApplyStatus.PAYMENT_BACK.getCode());
						String orderField=createOrderFileldService.backCheckContractByGrantSure(loanInfoOrder);
						loanInfo.setOrderField(orderField);
						//修改退回标识
						contract.setBackFlag(YESNO.YES.getCode());
						loanGrantService.sendBackToContractCheck(loanInfo, contract, "批量退回");
						//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
						GrantCallUtil.grantToCallUpdateRevisitStatus(contractCode,GrantCommon.GRANTCALL_CUSTOMER_STATUS_MODIFY);
		    		}
				}
			}catch(Exception e){
				addMessage(redirectAttributes, "合同号:" + contractCode + ",上传退回结果失败!");
			}
		} 
		return "redirect:" + adminPath
				+ "/channel/goldcredit/grantSure/getGCGrantInfo";
	}
	
	/**
	 * 第一次向金信接口推送数据信息
	 * @param datalist
	 * @return 返回数据的URL
	 */
	private String JxFirstSendData(List<SendMoneyEx> datalist,RedirectAttributes redirectAttributes){
		
		List<String> contractCodesList = Lists.newArrayList();
		String contractCodes = "";
		for (int i = 0; i < datalist.size(); i++) {
			String contractCode = datalist.get(i).getContractCode();
			if (StringUtils.isNotEmpty(contractCode)){
				contractCodesList.add(contractCode);
				contractCodes += contractCode + ",";
			}
		}
		logger.error("向金信网推送债权Excel解析合同个数："+contractCodesList.size()+"个,合同号为"+contractCodes);
		//门店申请冻结的贷款表信息
		List<SendMoneyEx> sendMoneyList = null;
		//打款表中存在门店冻结的数据信息
		List<SendMoneyEx> storeFrozenList = Lists.newArrayList();
		String storeFrozenFlag = BooleanType.FALSE;
		if (contractCodesList != null && contractCodesList.size() > 0){
			sendMoneyList = loanGrantService.findStoreFrozenList(contractCodesList);
			contractCodes = "";
			//判断从数据库中查出的数据信息是不是包含门店冻结的数据信息
			for (SendMoneyEx item : sendMoneyList) {
				//用来判断查询出来的数据列表是不是存在门店冻结的数据
				contractCodes += item.getContractCode() + ",";
				if (StringUtils.isNotEmpty(item.getFrozenCode()) && item.getFrozenCode().trim().length() != 0){
					storeFrozenList.add(item);
				}
			}
			if (storeFrozenList.size() != 0)
			{
				storeFrozenFlag = BooleanType.TRUE;//有门店冻结数据
			}
		} 
		String flag = BooleanType.TRUE;

		try {
			//用来判断是不是要向金信网发送数据信息
			if (BooleanType.FALSE.equals(storeFrozenFlag)){
				logger.error("向金信网推送债权查询合同个数："+sendMoneyList.size()+"个");
				logger.error("向金信网推送债权查询合同号："+contractCodes);
				//如果集合的长度未零的话，就说明此次上传的打款表信息中不包含门店申请冻结的列表信息
				String sendResultFlag = sendJINXINData(sendMoneyList);
				if (BooleanType.TRUE.equals(sendResultFlag)) {
					String curLetter = threePartFileName.getJXLoanCur();
			        String curUrgentFlag = threePartFileName.getUrgeCur();
			        Date submitTime = new Date();
			        
					for (SendMoneyEx sendMoneyEx : sendMoneyList) {
						LoanGrant loanGrant = new LoanGrant();
						loanGrant.setSubmissionsDate(submitTime);
						loanGrant.setContractCode(sendMoneyEx.getContractCode());
						if ("0".equals(sendMoneyEx.getLoanUrgentFlag())) {
							loanGrant.setGrantPch(curLetter);
						}else if ("1".equals(sendMoneyEx.getLoanUrgentFlag())) {
							loanGrant.setGrantPch(curUrgentFlag);
						}
						//保存金信处理状态
						loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_WAIT);
						//更新放款表
						loanGrantService.updateLoanGrant(loanGrant);
						//增加债权状态变化流水
						saveLoanStatusHis(sendMoneyEx);
						logger.error("合同号："+sendMoneyEx.getContractCode()+"发送成功！");
					}
					addMessage(redirectAttributes, "第一次向金信网推送债权数据信息成功！");
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss:SSS");  
					logger.info("工作流执行完时间为："+sdf.format(new Date()));
					logger.error("第一次向金信网推送债权数据信息成功！");
				}else {
					addMessage(redirectAttributes, "第一次向金信网推送债权数据信息失败！");
					logger.error("第一次向金信网推送债权数据信息失败！");
					flag = BooleanType.FALSE;
				}
			}else {
				if (storeFrozenList.size() > 0) {
					String forzenCustomer = "";
					for (SendMoneyEx sendMoneyEx : storeFrozenList) {
						logger.error(sendMoneyEx.getContractCode()+"为冻结数据！");
						forzenCustomer += "(客户姓名:"+sendMoneyEx.getCustomerName() + ",合同编号:"+ sendMoneyEx.getContractCode()+")、</br>";
					}
					if (StringUtils.isNotEmpty(forzenCustomer)) {
						forzenCustomer = forzenCustomer.substring(0, forzenCustomer.length() - 6);
						addMessage(redirectAttributes, forzenCustomer + " 的门店已申请冻结！");
					}
					flag = BooleanType.FALSE;
				}
			}
		} catch (Exception e) {
			logger.error("第一次向金信网推送债权数据信息失败,具体错误信息如下:"+e.toString());
			addMessage(redirectAttributes, "向金信网推送债权数据信息失败！");
			flag = BooleanType.FALSE;
		}
		return flag;
	}
	/**
	 * 添加债权状态变化流水
	 * @param sendMoneyEx
	 */
	private void saveLoanStatusHis(SendMoneyEx sendMoneyEx) {
		
		if(sendMoneyEx==null){
			return;
		}
		//工作流id
		String applyId = sendMoneyEx.getApplyId();
		//借款编号
		String loanCode = sendMoneyEx.getLoanCode();
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		record.setLoanCode(loanCode);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep( LoanApplyStatus.LOAN_SEND_CONFIRM.getName());
		// 操作结果
		record.setOperateResult("成功");
		// 备注
		record.setRemark("第一次向金信推送债权");
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		
		//创建时间
		record.setCreateTime(record.getCreateTime());
		
		record.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			//创建人
			record.setCreateBy(user.getName());
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		loanStatusHisDao.insertSelective(record);
	}
	// 获取当前可用批次号
		private Map<String, Object> getCurrGrantPch() {
			Date submitTime = new Date();
			Map<String, String> param = new HashMap<String, String>();
			Map<String, Object> result = new HashMap<String, Object>();
			param.put("letter", ChannelConstants.GOLD_CREDIT);
			param.put("urgentFlag", GrantCommon.BASEURGENTFLAG);
			param.put("queryTime", DateUtils.formatDate(submitTime,"yyyy-MM-dd"));
			param.put("loanStatus", "0");
			param.put("flag", "0");
			List<LoanGrantEx> maxPchs = loanGrantService.findMaxGrantPch(param);
			String curLetter = null;
			String curUrgentFlag = null;
			String baseLetter = null;
			String baseUrgentFlag = null;
			
			String submitStrTime = DateUtils.formatDate(submitTime,
					"yyyy-MM-dd HH:mm:ss");
			if (ObjectHelper.isEmpty(maxPchs)) {
				curLetter = ChannelConstants.GOLD_CREDIT + GrantCommon.BASEINDEX;
				curUrgentFlag = GrantCommon.BASEURGENTFLAG + GrantCommon.BASEINDEX;
			} else {
				for (LoanGrantEx curGrant : maxPchs) {
					if (StringUtils.isNotEmpty(curGrant.getGrantPch())) {
						if (curGrant.getGrantPch().indexOf(ChannelConstants.GOLD_CREDIT) != -1) {
							baseLetter = curGrant.getGrantPch();
						} else if (curGrant.getGrantPch().indexOf(
								GrantCommon.BASEURGENTFLAG) != -1) {
							baseUrgentFlag = curGrant.getGrantPch();
						}
					}
				}
				if (StringUtils.isEmpty(baseLetter)) {
					curLetter = ChannelConstants.GOLD_CREDIT + GrantCommon.BASEINDEX;
				} else {
					curLetter = ChannelConstants.GOLD_CREDIT
							+ (Integer.valueOf(baseLetter.substring(2)) + 1);
				}
				if (StringUtils.isEmpty(baseUrgentFlag)) {
					curUrgentFlag = GrantCommon.BASEURGENTFLAG
							+ GrantCommon.BASEINDEX;
				} else {
					curUrgentFlag = GrantCommon.BASEURGENTFLAG
							+ (Integer.valueOf(baseUrgentFlag.substring(2)) + 1);
				}
			}
			result.put("curLetter", curLetter);
			result.put("curUrgentFlag", curUrgentFlag);
			result.put("submitTime", submitTime);
			result.put("submitStrTime", submitStrTime);
			return result;
		}
	/**
	 * 此方法用来给金信王发送数据，最终来判断进行网是不是要这笔数据信息
	 * @param moneyList 打款表数据信息
	 */
	private String sendJINXINData(List<SendMoneyEx> moneyList) {
		//金信网债权第一次推送
		String flag = BooleanType.TRUE;

		CreditFirstReqInInfo payParam = new CreditFirstReqInInfo();
		List<CreditFirstReqInDetailInfo> creditSectionFields = Lists.newArrayList();
		for (SendMoneyEx item : moneyList) {
			CreditFirstReqInDetailInfo fieldBean = new CreditFirstReqInDetailInfo();
			fieldBean.setContractNumber(item.getContractCode());
			Double contractAmount = Double.parseDouble(item.getContractAmount());
			fieldBean.setContractAmountNew(contractAmount);
			fieldBean.setLoanTerm(Long.valueOf(item.getContractMonths()));
			fieldBean.setPutAmount(Double.parseDouble(item.getGrantAmount()));
			fieldBean.setStoreNumber(item.getStoresId());
			fieldBean.setStoreName(item.getStoresName());
			fieldBean.setCustomerName(item.getCustomerName());
			fieldBean.setIdentitycardNumber(item.getCustomerCertNum());
			
			// 甲方邮件
			fieldBean.setLoanEmail(item.getLoanEmail()); 
			
			String GuarantorPeople = item.getGuarantorPeople(); 
			// 丙方保证人姓名
			fieldBean.setGuarantorName(GuarantorPeople);
			
			
		    
		    String guarantorEmail = item.getGuarantorEmail();
		    // 丙方email
		    fieldBean.setGuarantorEmail(guarantorEmail);
		    
		    
		    /*
			 * 
			// 信用咨询及管理服务协议 1.5版本新增字段  begin
			String collectioneServiceFee = item.getCollectioneServiceFee();
			// 催收服务费
			fieldBean.setCollectioneServiceFee(Double.parseDouble(collectioneServiceFee)); 
			
			String preConsultationFee = item.getPreConsultationFee();
			// 前期咨询费
			fieldBean.setPreConsultationFee(Double.parseDouble(preConsultationFee)); 
			
			String preAuditFee = item.getPreAuditFee();
			// 前期审核费
			fieldBean.setPreAuditFee(Double.parseDouble(preAuditFee)); 
			
			String preHouseServiceFee = item.getPreHouseServiceFee();
			// 前期居间服务费
			fieldBean.setPreHouseServiceFee(Double.parseDouble(preHouseServiceFee)); 
			
			String preInfoServiceFee = item.getPreInfoServiceFee();
			// 前期信息服务费
			fieldBean.setPreInfoServiceFee(Double.parseDouble(preInfoServiceFee)); 
			
			String preInfoTotalServiceFee = item.getPreInfoTotalServiceFee();
			// 前期综合服务费
			fieldBean.setPreInfoTotalServiceFee(Double.parseDouble(preInfoTotalServiceFee)); 
			
			String installmentConsultationFee = item.getInstallmentConsultationFee();
			// 分期咨询费
			fieldBean.setInstallmentConsultationFee(Double.parseDouble(installmentConsultationFee)); 
			
			// 还款起止日期开始时间
			fieldBean.setPaymentDateStart(item.getPaymentDateStart()); 
			
			// 还款起止日期结束时间
			fieldBean.setPaymentDateEnd(item.getPaymentDateEnd()); 
			
			// 还本付息方式
			fieldBean.setPaymentMode(item.getPaymentMode());
			
			String monthConsultationFee = item.getMonthConsultationFee();
			
			// 分期服务费下:每期支付咨询费
			fieldBean.setMonthConsultationFee(Double.parseDouble(monthConsultationFee)); 
		    String guarantorPeople = item.getGuarantorPeople(); 
		    // 法定代表人
		    fieldBean.setGuarantorPeople(guarantorPeople);
		    
		    
		  
		    String guarantorTel = item.getGuarantorTel(); 
		    // 丙方电话	
		    fieldBean.setGuarantorTel(guarantorTel);
		    
		    fieldBean.setSysIdentify(item.getSysIdentify());//区分债权来自2.0还是3.0的标识
		    
		     String feePetition = item.getFeePetition(); 
		    if(StringUtils.isEmpty(feePetition)){
		    	feePetition = "0";
		    }
		    // 外访费	
		    fieldBean.setTravelExpenses(Double.parseDouble(feePetition));
		    
		    String itemDistance = item.getItemDistance(); 
		    if(StringUtils.isEmpty(itemDistance)){
		    	itemDistance = "0";
		    }
		    // 外访距离	
		    fieldBean.setRoundTrip(Double.parseDouble(itemDistance));
			*/
			
		    /******************2016/6/29根据金信要求增加字段 start**************************/
		    
		    String guarantorAddress = item.getGuarantorAddress();
		    // 法定代表地址
		    fieldBean.setGuarantorAddress(guarantorAddress);
		    
		    String naturalPersonName = item.getNaturalPersonName();
		    //自然人姓名
		    fieldBean.setNaturalPersonName(naturalPersonName);
		    
		    String naturalPersonCardNumber = item.getNaturalPersonCardNumber();
		    //自然人省份证号
		    fieldBean.setNaturalPersonCardNumber(naturalPersonCardNumber);
		    
		    String naturalPersonAddress = item.getNaturalPersonAddress();
		    //自然人现住址
		    fieldBean.setNaturalPersonAddress(naturalPersonAddress);
		    /******************2016/6/29根据金信要求增加字段 end**************************/
		    
		    
		    String unSociCreditCode = item.getUnSociCreditCode(); 
		    // 签章标识：统一社会信用代码	
		    fieldBean.setGuarantorUnSociCreditCode(unSociCreditCode);
		    
		    String guarantorEmailCom = item.getGuarantorEmail();//丙方公司email
		    fieldBean.setGuarantorCompanyEmail(guarantorEmailCom);
		    
		    //丙方公司名
		    String companyName = item.getCompanyName();
		    fieldBean.setGuarantorCompanyName(companyName);
		    
		    //丙方法人身份证
		    String guaranteeIdnum = item.getGuaranteeIdnum();
		   
		    fieldBean.setGuarantorCardNumber(guaranteeIdnum);
		   
			// 信用咨询及管理服务协议 1.5版本新增字段  end
			creditSectionFields.add(fieldBean);
			logger.info("推送的合同编号为："+fieldBean.getContractNumber()+",合同金额为："+fieldBean.getContractAmountNew());
			payParam.setCreditSectionFields(creditSectionFields);
		}
		// 客户端服务代理申明																										
		ClientPoxy service = new ClientPoxy(ServiceType.Type.JINXIN_CREDIT_FIRST_REQ_SERVICE);																										
		// 请求发送	
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss:SSS");  
		logger.info("向金信推送数据开始，时间为："+sdf.format(new Date())+"，推送的数据信息为："+JSONObject.toJSONString(payParam));
		CreditFirstReqOutInfo retInfo =  (CreditFirstReqOutInfo) service.callService(payParam);	
		logger.info("向金信推送数据结束，时间为："+sdf.format(new Date()));
		// 请求成功判断							
		if (ReturnConstant.SUCCESS.equals(retInfo.getRetCode())) {
			logger.info("金信网债权第一次推送成功"+moneyList.size()+"条！" + retInfo.getRetMsg());
		}else {
			// 取得错误信息
			String retMsg = retInfo.getRetMsg();
			logger.error("金信网债权第一次推送失败！" + retMsg);
			flag = BooleanType.FALSE;
		}
		return flag;
	}
	/**
	 * 退回处理，单子退回到合同，退回成功之后单子返回到列表，同时修改合同列表 成功失败应该有回应,退回的单子状态 2015年12月4日 By 路志友
	 * 
	 * @param apply
	 *            传送过来的流程属性
	 * @param responseUrl
	 *            要进行退回的节点的Code
	 * @param grantSureBackReason
	 *            退回原因
	 * @param contractCode
	 *            合同编号,根据合同编号进行处理
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "grantSureBack")
	public String grantSureBack(WorkItemView workItem, GrantDealView gqp,
			String grantSureBackReason, String response) {
		//废除工作流   操作数据库 退回到合同审核
		Contract contract=contractDao.findByContractCode(gqp.getContractCode());
		if(contract!=null){
			LoanInfo loanInfo=new LoanInfo();
			loanInfo.setLoanCode(contract.getLoanCode());
			loanInfo.setApplyId(contract.getApplyId());
			loanInfo.setDictLoanStatus(LoanApplyStatus.PAYMENT_BACK.getCode());
			//合同退回标志
			contract.setContractBackResult(grantSureBackReason);
			contract.setBackFlag(YESNO.YES.getCode());
			//待款项确认退回到合同审核 修改orderField 用于排序
			LoanInfo loanInfoOrder=new LoanInfo();
			loanInfoOrder.setApplyId(contract.getApplyId());
			loanInfoOrder.setDictLoanStatus(LoanApplyStatus.PAYMENT_BACK.getCode());
			String orderField=createOrderFileldService.backCheckContractByGrantSure(loanInfoOrder);
			loanInfo.setOrderField(orderField);
			loanGrantService.sendBackToContractCheck(loanInfo, contract, grantSureBackReason);
		}
		
		try {
			//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
			GrantCallUtil.grantToCallUpdateRevisitStatus(gqp.getContractCode(),GrantCommon.GRANTCALL_CUSTOMER_STATUS_MODIFY);
		} catch (Exception e) {
			logger.error("方法：grantSureBack 异常，金信贷款项确认退回，给呼叫中心推送数据失败",e);
		}
		return BooleanType.TRUE;
	}

	/**
	 * 驳回申请
	 * 
	 * @param apply
	 *            传送过来的流程属性
	 * @param responseUrl
	 *            要进行退回的节点的Code
	 * @param grantSureBackReason
	 *            退回原因
	 * @param contractCode
	 *            合同编号,根据合同编号进行处理
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "grantSureRejectBack")
	public String grantSureRejectBack(WorkItemView workItem, GrantDealView gqp) {
		//取消工作流的
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setApplyId(gqp.getApplyId());
		loanInfo.setLoanCode(gqp.getLoanCode());
		loanInfo.setFrozenFlag(YESNO.NO.getCode());
		loanInfo.setFrozenCode("");
		loanInfo.setFrozenReason("");
		loanInfo.setFrozenLastApplyTime(new Date());
		loanInfo.setRemark(gqp.getAutoGrantResult());//拒绝原因
		//待款项确认 驳回申请  修改orderField 用于排序
		Contract contract=contractDao.findByLoanCode(gqp.getLoanCode());
		LoanInfo loanInfoOrder=new LoanInfo();
		loanInfoOrder.setApplyId(contract.getApplyId());
		loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		String orderField=createOrderFileldService.backFrozenCodeByGrantSure(loanInfoOrder);
		loanInfo.setOrderField(orderField);
		loanGrantService.dealBackFrozen(loanInfo);
		try {
			//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
			GrantCallUtil.grantToCallUpdateRevisitStatus(gqp.getContractCode(),GrantCommon.GRANTCALL_CUSTOMER_STATUS_GRANTBEFORE);
		} catch (Exception e) {
			logger.error("方法：grantSureRejectBack 异常，驳回申请，给呼叫中心推送数据失败",e);
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 客户信息表导出，默认导出查询条件下的全部的单子，有选择的按照选择进行导出
	 * 2016年3月4日
	 * xiaoniu.hu
	 * @param request
	 * @param grtQryParam
	 * @param response
	 * @param loanCodes
	 */
	@RequestMapping(value = "exportCustomer")
	public void exportCustomer(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String loanCodes) {
		String[] loanCodeList = null;
		if (StringUtils.isNotEmpty(loanCodes)) {
			loanCodeList= loanCodes.split(",");
			grtQryParam.setLoanCodes(loanCodeList);
		}else {
			grtQryParam.setLoanCodes(null);
		}
		grtQryParam.setChannelCode(ChannelFlag.JINXIN.getCode());
		grtQryParam.setGoldCreditStatus(YESNO.NO.getCode());
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		ExportGCGrantSureHelper.customerExport(grtQryParam, response,userManager);
	}
	/**
	 * 打款表导出，默认导出查询条件下的全部的单子，有选择的按照选择进行导出 2015年12月22日 By 路志友
	 * 
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "exportRemit")
	public void exportRemit(HttpServletRequest request,
			LoanFlowQueryParam grtQryParam, HttpServletResponse response,
			String loanCodes) {
		List<String> loanCodeList = null;
		if (StringUtils.isEmpty(loanCodes)) {
			// 没有选中的，默认为全部,通过
			loanCodeList=this.loadLoanCode(grtQryParam);
		} else {
			// 如果有选中的单子,将选中的单子导出
			loanCodeList=Arrays.asList(loanCodes.split(","));   
		}
		if (loanCodeList != null && loanCodeList.size() != 0) {
			Map<String,Object> list = Maps.newHashMap();
			list.put("list", loanCodeList);
			ExportGCGrantSureHelper.customerCallTableExport(list, response, userManager,threePartFileName.getGoldCreditExportFileName(ExportFlagConstants.GOLD_CREDIT_SURE));
		}else{
			Map<String,Object> list = Maps.newHashMap();
			loanCodeList.add("");
			list.put("list", loanCodeList);
			ExportGCGrantSureHelper.customerCallTableExport(list, response, userManager,threePartFileName.getGoldCreditExportFileName(ExportFlagConstants.GOLD_CREDIT_SURE));
		}
	}
	/**
	 * 汇总表导出，默认导出查询条件下的全部的单子，有选择的按照选择进行导出，如果共借人为多个？？
	 * 2015年12月22日
	 * By 张建雄
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "exportSummary")
	public void grantSumExl(HttpServletRequest request,LoanFlowQueryParam grtQryParam,
			HttpServletResponse response, String loanCodes) {
		List<String> loanCodeList = null;
		if (StringUtils.isEmpty(loanCodes)) {
			// 没有选中的，默认为全部,通过
			loanCodeList=this.loadLoanCode(grtQryParam);
		} else {
			// 如果有选中的单子,将选中的单子导出
			loanCodeList=Arrays.asList(loanCodes.split(","));   
		}
		if (loanCodeList != null && loanCodeList.size() != 0) {
			Map<String,Object> map = Maps.newHashMap();
			map.put("list", loanCodeList);
			ExportGCGrantSureHelper.summarySheetExport(map, response, userManager, threePartFileName.getGoldCreditSumExportFileName(ExportFlagConstants.GOLD_CREDIT_SURE));
		}else{
			Map<String,Object> list = Maps.newHashMap();
			loanCodeList.add("");
			list.put("list", loanCodeList);
			ExportGCGrantSureHelper.summarySheetExport(list, response, userManager,threePartFileName.getGoldCreditSumExportFileName(ExportFlagConstants.GOLD_CREDIT_SURE));
		}
	}
	/**
	 * 根据条件去工作流获取所有借款编码
	 * 2016年3月4日
	 * xiaoniu.hu
	 * @param grtQryParam 画面查询条件
	 * @return
	 */
	private List<String> loadLoanCode(LoanFlowQueryParam grtQryParam){
		//废除工作流 从数据库中读取loan_code
		List<String> list=new ArrayList<String>();
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		if (ObjectHelper.isEmpty(grtQryParam.getRevisitStatus()) || grtQryParam.getRevisitStatus()[0].equals("")) {	
			grtQryParam.setRevisitStatus(null);
		}
		//回访状态的查询
		String[] revisitStatus=grtQryParam.getRevisitStatus();
		if(revisitStatus!=null&&revisitStatus.length>0){
			for(String status:revisitStatus){
				if(GrantCommon.REVISIT_STATUS_NULL_CODE.equals(status)){
					grtQryParam.setRevisitQueryName(GrantCommon.REVISIT_STATUS_NULL_CODE);
				}
			}
		}
		// 查询金信待款项列表信息
		List<LoanFlowWorkItemView> gcGrantSureList = loanGrantService.getGCGrantSureList(grtQryParam);
		for(LoanFlowWorkItemView item : gcGrantSureList) {
			list.add(item.getLoanCode());
		}
		return list;
	}
}
