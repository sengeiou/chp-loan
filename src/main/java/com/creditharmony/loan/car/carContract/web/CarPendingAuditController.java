package com.creditharmony.loan.car.carContract.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carContract.ex.CarExportMoneyEx;
import com.creditharmony.loan.car.carContract.service.CarCheckRateService;
import com.creditharmony.loan.car.carContract.view.CarPendingAuditView;
import com.creditharmony.loan.car.carGrant.service.CarGrantDoneService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 待确认放款相关
 * 
 * @Class Name pendingAuditController
 * @author ganquan
 * @Create In 2016年2月22日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carContract/pendingAudit")
public class CarPendingAuditController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	FlowService flowService;

	// 借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;

	// 卡
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;

	// 合同费率
	@Autowired
	private CarCheckRateService carCheckRateService;

	@Autowired
	private CarGrantDoneService grantDoneService;
	
	@Autowired
	private ProvinceCityManager provinceCityManager;

	// 退回节点
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "sendBack")
	public String sendBack(String applyId, String dictBackMestype, String remark,FlowProperties flowProperties,RedirectAttributes redirectAttributes) {
		// 获取workItem中的数据
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		queryParam.put("applyId", applyId);
		TaskBean taskBean = flowService
				.fetchTaskItems(
						CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER
								.getWorkQueue(), queryParam,
						BaseTaskItemView.class);
		List<BaseTaskItemView> workItems = (List<BaseTaskItemView>) taskBean
				.getItemList();
		BaseTaskItemView workItem = workItems.get(0);
		WorkItemView wi = new WorkItemView();
		ReflectHandle.copy(workItem, wi);
		CarPendingAuditView bv = new CarPendingAuditView();
		bv.setDictLoanStatus(CarLoanStatus.PENDING_LOAN_CONFIRMATION_BACK
				.getCode());
		bv.setDictBackMestype(dictBackMestype);
		bv.setApplyId(applyId);
		bv.setContractBackResultCode(dictBackMestype);
		bv.setRemark(remark);
		wi.setBv(bv);
		//标红置顶业务所需
		WorkItemView workItemView = flowService.loadWorkItemView(applyId, workItem.getWobNum(), workItem.getToken());
		wi.setFlowProperties(workItemView.getFlowProperties());
		wi.setResponse(CarLoanResponses.BACK_AUDIT_CONTRACT.getCode());
		try{
			flowService.dispatch(wi);
		}catch(Exception e){
			addMessage(redirectAttributes, "退回失败，原因：" + e.getMessage() + "");
		}
		return "redirect:" + adminPath
				+ "/car/carLoanWorkItems/fetchTaskItems/statisticsCommissioner";
	}

	// 确认到分配卡号
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "loanConfirm")
	public String loanConfirm(String applyId) {
		// 获取workItem中的数据
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		queryParam.put("applyId", applyId);
		TaskBean taskBean = flowService
				.fetchTaskItems(
						CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER
								.getWorkQueue(), queryParam,
						BaseTaskItemView.class);
		List<BaseTaskItemView> workItems = (List<BaseTaskItemView>) taskBean
				.getItemList();
		BaseTaskItemView workItem = workItems.get(0);
		WorkItemView wi = new WorkItemView();
		ReflectHandle.copy(workItem, wi);
		CarPendingAuditView bv = new CarPendingAuditView();
		bv.setDictLoanStatus(CarLoanStatus.PENDING_ASSIGNED_CARD_NUMBER
				.getCode());
		bv.setApplyId(applyId);
		wi.setBv(bv);
		//标红置顶业务所需
		WorkItemView workItemView = flowService.loadWorkItemView(applyId, workItem.getWobNum(), workItem.getToken());
		wi.setFlowProperties(workItemView.getFlowProperties());
		wi.setResponse(CarLoanResponses.TO_ALLOT_CARD.getCode());
		flowService.dispatch(wi);
		return "redirect:" + adminPath
				+ "/car/carLoanWorkItems/fetchTaskItems/statisticsCommissioner";
	}

	// 导出打款表
	@RequestMapping(value = "exportWatch")
	public String exportWatch(HttpServletResponse response, String idVal,
			CarLoanFlowQueryView carLoanFlowQueryView) {
		ExcelUtils excelutil = new ExcelUtils();
		List<CarExportMoneyEx> lst = new ArrayList<CarExportMoneyEx>();
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
		TaskBean taskBean = null;
		try {
			ReflectHandle.copy(carLoanFlowQueryView, queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"".equals(idVal)) {
			queryParam.put("applyId", idVal.split(","));
		}
		taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER.getWorkQueue(), queryParam, CarLoanFlowWorkItemView.class);
		int sumCount = taskBean.getItemList().size();
		FlowPage page = new FlowPage();
		page.setPageSize(sumCount);
	    page.setPageNo(1);
		flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER.getWorkQueue(), queryParam, page, null, CarLoanFlowWorkItemView.class);
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		int i = 1;
		for (BaseTaskItemView currItem : sourceWorkItems) {
			CarLoanFlowWorkItemView carView = (CarLoanFlowWorkItemView) currItem;
			CarExportMoneyEx carExportMoneyEx = new CarExportMoneyEx();
			ReflectHandle.copy(carView, carExportMoneyEx);
			carExportMoneyEx.setNumber(i);
			carExportMoneyEx.setLoanMonths(carView.getAuditLoanMonths());
			carExportMoneyEx.setBorrowProductName(carView.getAuditBorrowProductName());
			carExportMoneyEx.setContractAmount(carView.getContractAmount());
			carExportMoneyEx.setBorrowAmount(carExportMoneyEx.getContractAmount());
			carExportMoneyEx.setGrossRate(new Double(carView.getGrossRate()).toString());
			carExportMoneyEx.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", carView.getLoanFlag()));
			// 得到开卡省市
			String loanCode = carView.getLoanCode();
			CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
			if (carCustomerBankInfo != null) {
				String bankProvinceCode = carCustomerBankInfo.getBankProvince();
				String bankCityCode = carCustomerBankInfo.getBankCity();
				if(StringUtils.isNotEmpty(bankProvinceCode) && StringUtils.isNotEmpty(bankCityCode) ){
					String bankProvince = provinceCityManager.get(bankProvinceCode).getAreaName();
					String bankCity = provinceCityManager.get(bankCityCode).getAreaName();
					if(StringUtils.isNotEmpty(bankProvince) && StringUtils.isNotEmpty(bankCity)){
						if(("上海市".equals(bankProvince) && "上海市".equals(bankCity))||
						   ("北京市".equals(bankProvince) && "北京市".equals(bankCity))||
						   ("重庆市".equals(bankProvince) && "重庆市".equals(bankCity))||
						   ("天津市".equals(bankProvince) && "天津市".equals(bankCity))){
							carExportMoneyEx.setBankProvinceCity(bankProvince);
						} else {
							carExportMoneyEx.setBankProvinceCity(bankProvince+bankCity);
						}
					}
				}
				// 得到开卡支行
				String cardBank = carCustomerBankInfo.getCardBank();
				String applyBankName = carCustomerBankInfo.getApplyBankName();
				carExportMoneyEx.setCardBank(cardBank + applyBankName);
			}
			//借款类型转换
			
			// 得到利息率
			CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
			if (carCheckRate != null) {
				BigDecimal interestRate = carCheckRate.getInterestRate();
				BigDecimal feeAmount = carCheckRate.getFeePaymentAmount();
				carExportMoneyEx.setInterestRate(new Double(interestRate.doubleValue()).toString());
				carExportMoneyEx.setFeePaymentAmount(feeAmount.doubleValue());
				//首期服务费
				carExportMoneyEx.setFirstServiceTariffing(carCheckRate.getFirstServiceTariffing() == null ? 0 : carCheckRate.getFirstServiceTariffing().doubleValue());
				//外访费
				carExportMoneyEx.setOutVisitFee(carCheckRate.getOutVisitFee() == null ? 0 : carCheckRate.getOutVisitFee().doubleValue());
			}
			//设备使用费
			//carExportMoneyEx.setDeviceUsedFee(carExportCustomerDataExColumn.getDeviceUsedFee());

			lst.add(carExportMoneyEx);
			i++;
        }
		
		excelutil.exportExcel(lst, FileExtension.SEND_MONEY,null,
				CarExportMoneyEx.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);
		return null;
	}

	// 上传回执,导入打款表
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "uploadReceipt")
	public String uploadReceipt(@RequestParam(required=false) MultipartFile file) {
		String fileContentType = file.getContentType();
		List<String> acceptFileType = Arrays.asList("application/msexcel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (!acceptFileType.contains(fileContentType)) {
			return BooleanType.FALSE;
		}else{
			try {
				ExcelUtils excelutil = new ExcelUtils();
				ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
				List<CarExportMoneyEx> lst = null;
				List<?> datalist = excelutil.importExcel(file, 0, 0,
						CarExportMoneyEx.class,null);
				lst = (List<CarExportMoneyEx>) datalist;
				// 导入时多一条空记录，此处将其删除
				if (ArrayHelper.isNotEmpty(lst)) {
					for (int i = 0; i < lst.size(); i++) {
						if (StringUtils.isEmpty(lst.get(i).getContractCode())) {
							lst.remove(i);
						}
					}
				}
				// 根据合同号查询applyId
				String applyId = null;
				if (ArrayHelper.isNotEmpty(lst)) {
					for (int i = 0; i < lst.size(); i++) {
						applyId = grantDoneService.selApplyId(lst.get(i)
								.getContractCode());
						// 获取workItem中的数据
						queryParam.put("applyId", applyId);
						TaskBean taskBean = flowService.fetchTaskItems(
								CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER
										.getWorkQueue(), queryParam,
								BaseTaskItemView.class);
						List<BaseTaskItemView> workItems = (List<BaseTaskItemView>) taskBean
								.getItemList();
						BaseTaskItemView workItem = workItems.get(0);
						WorkItemView wi = new WorkItemView();
						ReflectHandle.copy(workItem, wi);
						CarPendingAuditView bv = new CarPendingAuditView();
						bv.setDictLoanStatus(CarLoanStatus.PENDING_ASSIGNED_CARD_NUMBER
								.getCode());
						bv.setApplyId(applyId);
						wi.setBv(bv);
						wi.setResponse(CarLoanResponses.TO_ALLOT_CARD.getCode());
						flowService.dispatch(wi);
					}
				}
			} catch (Exception e) {
				return "date";
			}
			return BooleanType.TRUE;
		}
	}
	
	
	/**
	 * 流程处理，更改流程属性，不提交流程节点，更新列表中的标识,需要在业务表中进行更新，方法在service中。 2015年12月3日 By 路志友
	 * 
	 * @param model
	 * @param checkVal 从前台传送过来的参数，流程参数
	 * @param borrowFlag 要进行修改的标识
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "updateCarP2PStatu")
	public String updateCarP2PStatu(Model model, String checkVal,String handleFlag) {
		// 遍历checkVal,以';'把，每一个放到apply中
		String flagString = BooleanType.TRUE;
		if (StringUtils.isNotEmpty(checkVal) && checkVal.split(",") !=null && checkVal.split(",").length>0) {
			for (String applyId:checkVal.split(",")) {
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("loanFlag", handleFlag);
				flowService.saveDataByApplyId(applyId, paramMap);
			}
			carLoanInfoService.updateCarP2PStatu(handleFlag,checkVal.split(","));
		}
		return flagString;
	}
}
