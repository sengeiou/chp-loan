package com.creditharmony.loan.borrow.trusteeship.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.adapter.bean.BaseOutInfo;
import com.creditharmony.adapter.bean.in.jzh.JzhRegisterInfo;
import com.creditharmony.adapter.bean.in.thirdpay.ProtocolLibraryInfo;
import com.creditharmony.adapter.bean.out.jzh.JzhRegisterOutInfo;
import com.creditharmony.adapter.bean.out.thirdpay.ProtocolLibraryReturnInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.fortune.type.FuYouAccountBackState;
import com.creditharmony.core.loan.type.LoanTrustState;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.service.ContractAndPersonInfoService;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.KingImportAccountInfo;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipAccount;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipConstant;
import com.creditharmony.loan.borrow.trusteeship.service.KingOpenAccountService;
import com.creditharmony.loan.borrow.trusteeship.util.ExcelExportAccountExlHelper;
import com.creditharmony.loan.borrow.trusteeship.util.ExcelExportProtocolExlHelper;
import com.creditharmony.loan.borrow.trusteeship.view.TrusteeshipView;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.google.common.collect.Lists;

/**
 * 金账户开户Controller
 * @Class Name KingOpenAccountController
 * @author 王浩
 * @Create In 2016年2月27日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/king/openAccount")
public class KingOpenAccountController extends BaseController {

	@Autowired
	private KingOpenAccountService openAccountService;
	
	@Autowired
    private ContractDao contractDao;
	
	@Autowired
    private GrantCAService grantCAService;
	
	@Autowired
	private ContractAndPersonInfoService contractAndPersonInfoService;
	
	@Autowired
	private ApplyLoanInfoService applyLoanInfoService;
		
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 金账户列表初始化
	 * 2017年2月13日
	 * By 朱静越
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @param redirectAttributes
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getTaskItems")
	public String getTaskItems(Model model, LoanFlowQueryParam grtQryParam,HttpServletRequest request,
			RedirectAttributes redirectAttributes,HttpServletResponse response) {
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		Page<LoanFlowWorkItemView> pageList = openAccountService.getKingOpenList(new Page<LoanFlowWorkItemView>(request, response), grtQryParam);
		List<LoanFlowWorkItemView> list = pageList.getList();
		for (LoanFlowWorkItemView lf : list) {
			String kingStatus = DictCache.getInstance().getDictLabel("jk_king_state", lf.getKingStatus());
			lf.setKingStatusLabel(kingStatus);
			lf.setLoanStatusName(DictCache.getInstance().getDictLabel("jk_loan_apply_status", lf.getLoanStatusName()));
		}
		model.addAttribute("loanQueryParam", grtQryParam);
		model.addAttribute("workItems", pageList);
		return "/borrow/trusteeship/king_openAccountList";
	}
	
	/**
	 * 查找开户数据并导出
	 * 2016年2月29日
	 * By 王浩
	 * @param model
	 * @param request
	 * @param grtQryParam
	 * @param response
	 * @param idVal 
	 */
	@RequestMapping(value = "exportAccountExl")
	public void accountExl(Model model, HttpServletRequest request, 
			HttpServletResponse response, String idVal) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> loanCodeList = Lists.newArrayList();
		
		if(StringUtils.isNotEmpty(idVal)){
			loanCodeList = Arrays.asList(idVal.split(","));
		}
		// 根据applyId查询要导出的数据
		if(ArrayHelper.isNotEmpty(loanCodeList)){
	        map.put("list",loanCodeList);
			ExcelExportAccountExlHelper.exportData(map, response);
		}		
	}
	
	/**
	 * 导出协议
	 * 2016年2月29日
	 * By 王浩
	 * @param model
	 * @param request
	 * @param grtQryParam
	 * @param response
	 * @param idVal 
	 */
	@RequestMapping(value = "exportProtocolExl")
	public void protocolExl(Model model, HttpServletRequest request, 
			HttpServletResponse response, String idVal) {
		List<String> loanCodeList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(idVal)){
			loanCodeList = Arrays.asList(idVal.split(","));
		}
		// 根据applyId查询要导出的数据
		if(ArrayHelper.isNotEmpty(loanCodeList)){
			map.put("list",loanCodeList);
			ExcelExportProtocolExlHelper.exportData(map, response);
		}		
	}	
	
	/**
	 * 上传文件
	 * 2016年2月27日
	 * By 王浩
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "importExl")
	public String trusteeshipAccountImportExl(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file){
		ExcelUtils excelutil = new ExcelUtils();
		List<KingImportAccountInfo> lst = new ArrayList<KingImportAccountInfo>();
		try{
			List<?> datalist = excelutil.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
						KingImportAccountInfo.class);
			lst = (List<KingImportAccountInfo>) datalist;
			if (ArrayHelper.isNotEmpty(lst)) {
				for (KingImportAccountInfo info : lst) {
					if (StringUtils.isNotEmpty(info.getReturnCode())) {
						// 根据身份证号，查出借款申请的applyId
						TrusteeshipView trustee = openAccountService.getApplyIdByIdentityNo(info);
						if (trustee != null && StringUtils.isNotEmpty(trustee.getApplyId())) {
							if (info.getReturnCode().equals(FuYouAccountBackState.JYCG.value)) {
								logger.debug("importExl上传回执结果，"+info.getCustomerName()+"开户成功处理开始");
								openAccountService.openSuccess(trustee,trustee.getLoanCode());	
								logger.debug("importExl上传回执结果，"+info.getCustomerName()+"开户成功处理结束");
							} else {
								logger.debug("importExl上传回执结果，"+info.getCustomerName()+"开户失败处理开始");
								// 返回码代表开户失败，保存失败原因
								LoanInfo loanInfo = new LoanInfo();
								loanInfo.setLoanCode(trustee.getLoanCode());
								loanInfo.setKingStatus(LoanTrustState.KHSB.value);
								// 拼接返回码与返回原因
								String respMsg = (StringUtils.isNotEmpty(info.getReturnCode()) ? info.getReturnCode() : "")
									+ "," + (StringUtils.isNotEmpty(info.getReturnMsg()) ? info .getReturnMsg() : "");
								loanInfo.setKingOpenRespReason(respMsg);
								applyLoanInfoService.updateLoanInfo(loanInfo);
								logger.debug("importExl上传回执结果，"+info.getCustomerName()+"开户失败处理结束");
							}
						}
					} 
				}
				return jsonMapper.toJson("导入成功");
			} else {
				return jsonMapper.toJson("请检查数据或者文件格式！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return jsonMapper.toJson("导入失败：请联系系统管理员");
		}
	}	
	
	/**
	 * 更新金账户开户信息
	 * 2016年2月27日
	 * By 王浩
	 * @param model
	 * @param view
	 * @param paramStr
	 * @param flowFlag
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "updateKingAccount")
	public String updateKingAccount(Model model, TrusteeshipView view, String paramStr, String operateType) {
		WorkItemView workItem = null;
		String[] batchParams = null;
		String[] singleParam = null;
		//paramStr格式：loanCode,applyId,;loanCode,applyId,
		if (paramStr != null && paramStr.indexOf(";") != -1) {
			batchParams = paramStr.split(";");
		} else {
			batchParams = new String[1];
			batchParams[0] = paramStr;
		}
		String rtnMsg = "";
		try {
			for (String str : batchParams) {
				if (str != null && str.indexOf(",") != -1) {
					singleParam = str.split(",");					
					String loanCode = singleParam[0];
					
					view.setLoanCode(loanCode);
					view.setApplyId(singleParam[1]);				
					
					// 金账户自动开户操作，调用接口，
					if (TrusteeshipConstant.ACCOUNT_TRY_OPEN.equals(operateType)) {
						logger.debug("方法updateKingAccount，处理金账户自动开户，处理开始");
						rtnMsg += this.callOpenAccountWS(workItem, view,loanCode);	
						logger.debug("方法updateKingAccount，处理金账户自动开户，处理结束");
					} else if (TrusteeshipConstant.ACCOUNT_SUCCESS.equals(operateType)) {
						// 点击金账户开户成功						
						rtnMsg += openAccountService.openSuccess(view,loanCode);
					} else if (TrusteeshipConstant.ACCOUNT_ERROR.equals(operateType)) { 
						// 点击 开户失败 按钮，保存开户状态，开户失败原因
						LoanInfo loanInfo = new LoanInfo();
						loanInfo.setLoanCode(loanCode);
						loanInfo.setKingStatus(LoanTrustState.KHSB.value);
						loanInfo.setKingOpenRespReason(view.getKingBackReason());
						applyLoanInfoService.updateLoanInfo(loanInfo);
						rtnMsg += "开户失败完成";
					} else if (TrusteeshipConstant.ACCOUNT_UNDO.equals(operateType)) {
						// 点击 金账户退回 按钮，流程跳转，到合同审核
						// kingAccountState="ACCOUNT_UNDO", flowFlag="CTR_AUDIT" 具体参见ContractConstant
						// 退回，状态更新为【金账户退回】
						logger.debug("方法updateKingAccount，处理金账户退回到合同审核开始，"+view.getLoanCode());
						openAccountService.kingAccountBack(view);
						logger.debug("方法updateKingAccount，处理金账户退回到合同审核结束，"+view.getLoanCode());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法updateKingAccount在处理"+TrusteeshipConstant.ACCOUNT_UNDO+
					"出现异常,借款编码"+view.getLoanCode(),e);
			rtnMsg = "处理异常"+e.getMessage();
		}
		// 返回开户列表
		return jsonMapper.toJson(rtnMsg);
	}
	
	/**
	 * 调用金账户开户接口，如果提示协议库日期超过7天，更新协议库之后重新开户
	 * 2016年3月3日
	 * By 王浩
	 * @param workItem
	 * @param view
	 * @param loanCode 
	 * @return none
	 */
	private String callOpenAccountWS(WorkItemView workItem, TrusteeshipView view,String loanCode){	
		TrusteeshipAccount account = openAccountService.getAccountInfo(loanCode);
		
		ClientPoxy serviceOpen = new ClientPoxy(ServiceType.Type.JZH_REGISTER);
		JzhRegisterInfo registerInfo = openAccountService.getJzhRegisterInfo(account);
		BaseOutInfo baseOut = serviceOpen.callService(registerInfo);
		log.info("开户响应码：" + baseOut.getRetCode() + ",开户响应消息："
				+ baseOut.getRetMsg());
		// 开户成功						
		if (baseOut.getRetCode().equals(FuYouAccountBackState.JYCG.value)) {
			logger.debug("方法callOpenAccountWS：金账户自动开户成功处理："+loanCode);
			return openAccountService.openSuccess(view,loanCode);
		}else if(baseOut.getRetCode().equals(FuYouAccountBackState.XYKYZRQCGQT.value)){
			// 协议库验证日期超过7天，则更新协议库
			ClientPoxy service = new ClientPoxy(ServiceType.Type.FY_SIGN_REQ);	
			ProtocolLibraryInfo info = openAccountService.setLibraryInfo(account);
			ProtocolLibraryReturnInfo plrInfo=(ProtocolLibraryReturnInfo) service.callService(info);
			if(plrInfo.getRetCode().equals(FuYouAccountBackState.JYCG.value)){
				//更新协议库成功，再次开户
				baseOut = (JzhRegisterOutInfo)serviceOpen.callService(registerInfo);
				if(baseOut.getRetCode().equals(FuYouAccountBackState.JYCG.value)){
					// 更新协议库成功之后，再次开户成功
					return openAccountService.openSuccess(view, loanCode);
				} else {
					//开户失败更新开户状态，开户失败原因，协议库返回信息
					LoanInfo loanInfo = new LoanInfo();
					loanInfo.setLoanCode(loanCode);
					loanInfo.setKingStatus(LoanTrustState.KHSB.value);
					loanInfo.setKingOpenRespReason(baseOut.getRetMsg());
					loanInfo.setKingProctolRespReason(baseOut.getRetMsg());
					applyLoanInfoService.updateLoanInfo(loanInfo);
					
					return "【"+loanCode+"】" + ":" + baseOut.getRetCode() + ""+ baseOut.getRetMsg() + "<br>";
				}
			}
			return "【"+loanCode+"】" + ":协议库对接失败<br>";
		} else {
			//开户失败，将开户状态更新到工作流
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setLoanCode(loanCode);
			loanInfo.setKingStatus(LoanTrustState.KHSB.value);
			loanInfo.setKingOpenRespReason(baseOut.getRetMsg());
			loanInfo.setKingProctolRespReason(baseOut.getRetMsg());
			applyLoanInfoService.updateLoanInfo(loanInfo);
			return loanCode + ":" + baseOut.getRetCode() + ""+ baseOut.getRetMsg() + "<br>";
		}
	}
	

	/**
	 * 协议库更新
	 * 2016年3月4日
	 * By 王浩
	 * @param model
	 * @param params
	 * @return 返回每一条记录的协议库返回code与msg
	 */
	@ResponseBody
	@RequestMapping(value = "updateProtocol")
	public String refreshProtocol(Model model, String params){
		StringBuffer rtnMsg = new StringBuffer();
		List<String> loanCodeList = null;
		List<TrusteeshipAccount> accountList = null;
		if(StringUtils.isNotEmpty(params)){
			String[] loanCodes = params.split(",");
			loanCodeList = Arrays.asList(loanCodes);
		}
		if(ArrayHelper.isNotEmpty(loanCodeList)){
			accountList = openAccountService.getAllAccount(loanCodeList);
		}
		// 根据查询出的数据，调用协议库更新接口
		if(ArrayHelper.isNotEmpty(accountList)){
			ClientPoxy service = new ClientPoxy(ServiceType.Type.FY_SIGN_REQ);
			for (TrusteeshipAccount account : accountList) {					
				ProtocolLibraryInfo info = openAccountService.setLibraryInfo(account);
				BaseOutInfo plrInfo = service.callService(info);
				
				
				if (!plrInfo.getRetCode().equals(FuYouAccountBackState.JYCG.value)) {
					rtnMsg.append("【" + account.getCustomerName() + "】："
							+ plrInfo.getRetCode() + "," + plrInfo.getRetMsg()+"<br>");
					// 协议库对接失败，更新协议库对接返回原因
					LoanInfo loanInfo = new LoanInfo();
					loanInfo.setLoanCode(account.getLoanCode());
					loanInfo.setKingProctolRespReason(plrInfo.getRetMsg());
					applyLoanInfoService.updateLoanInfo(loanInfo);
				}else{
					rtnMsg.append("【" + account.getCustomerName() + "】：协议库对接成功<br>");
				}
			}
		}
		return jsonMapper.toJson(rtnMsg.toString());
	}
}
