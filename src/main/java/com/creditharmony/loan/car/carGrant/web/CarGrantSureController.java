package com.creditharmony.loan.car.carGrant.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.fortune.type.DeductPlat;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.service.GrantAuditService;
import com.creditharmony.loan.car.carGrant.ex.CarDisCardEx;
import com.creditharmony.loan.car.carGrant.ex.CarGrantAuditEx;
import com.creditharmony.loan.car.carGrant.ex.CarGrantEx;
import com.creditharmony.loan.car.carGrant.service.CarGrantDoneService;
import com.creditharmony.loan.car.carGrant.service.CarGrantSureService;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 放款各列表处理事件
 * 
 * @Class Name CarGrantSureController
 */

@Controller
@RequestMapping(value = "${adminPath}/car/grant/grantSure")
public class CarGrantSureController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private GrantAuditService grantAuditService;
	@Autowired
	private CarGrantSureService grantSureService;
	@Autowired
	private CarGrantDoneService grantDoneService;
	@Autowired
	private CarHistoryService historyService;
	@Autowired
	private CityInfoService cityManager;


	/**
	 * 线上放款处理
	 * @param checkVal 
	 * @param grantWay 线上放款方式
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "onlineGrantDeal")
	@ResponseBody
	public String onlineGrantDeal(String checkVal,String grantWay) {
		logger.info("线上放款：平台---" + grantWay);
		int flag = 0;
		DeductReq deductReq;
		String[] contract = null;
		String rule = "";
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		
		hashMap.put("dictLoanStatus", CarLoanStatus.PENDING_LOAN.getCode());
		hashMap.put("dictLoanStatusFail", CarLoanStatus.LENDING_FAILURE.getCode());
		if (PaymentWay.ZHONGJIN.getCode().equals(grantWay)) {
			rule = DeductPlat.ZJPT.value +":"+DeductTime.BATCH.getCode() ;
			hashMap.put("dictLoanWay", PaymentWay.ZHONGJIN.getCode());
		}else if (PaymentWay.TONG_LIAN.getCode().equals(grantWay)) {
			rule = DeductPlat.TL.value +":"+DeductTime.BATCH.getCode();
			hashMap.put("dictLoanWay", PaymentWay.TONG_LIAN.getCode());
		}
		if (StringUtils.isEmpty(checkVal)) {
			// 无可放款数据
			
		}else {			
			contract = checkVal.split(";");
			for (int i = 0; i < contract.length; i++) {
				hashMap.put("contractCode", contract[i]);
				deductReq =  grantSureService.queryDeductReq(hashMap,rule);
		
					if (deductReq != null) {
						DeResult t = TaskService.addTask(deductReq);
						flag++;
						try {
							
							if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
								
						    	// 根据applyId更新借款状态为已处理,并跟新流程中的借款状态
								ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
								queryParam.put("applyId", deductReq.getBatId());
								TaskBean utaskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
										.getWorkQueue(), queryParam,
										BaseTaskItemView.class);
								List<BaseTaskItemView> workItemsList=new ArrayList<BaseTaskItemView>();
								workItemsList = (List<BaseTaskItemView>) utaskBean.getItemList();
								CarGrantDealView gqp=new CarGrantDealView();
								WorkItemView workItem = new WorkItemView();
								if (ArrayHelper.isNotEmpty(workItemsList)) {
									BaseTaskItemView baseView=workItemsList.get(0);
									ReflectHandle.copy(baseView, workItem);
								}
								gqp.setDictLoanStatus(CarLoanStatus.LOANING.getCode());
								gqp.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
								gqp.setApplyId(deductReq.getBatId());
								workItem.setBv(gqp);
								flowService.saveData(workItem);
								TaskService.commit(t.getDeductReq());
								
							}else{
								TaskService.rollBack(t.getDeductReq());

							}
						} catch (Exception e) {
							e.printStackTrace();
							TaskService.rollBack(t.getDeductReq());
							return  BooleanType.FALSE;
						}
						
						
					}
			}
		}
		if (flag > 0) {
			return  BooleanType.TRUE;
		}else{
			// 没有可放款数据    
			return  "2";
		}
	}
	
	/**
	 * 导出放款审核列表 
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "grantAuditExl")
	public void grantAuditExl(HttpServletRequest request,
			HttpServletResponse response, String idVal,CarLoanFlowQueryView carLoanFlowQueryView) {
		
		ProcessQueryBuilder param = new ProcessQueryBuilder();
		ExcelUtils excelutil = new ExcelUtils();
		String[] id=null;
		List<CarGrantAuditEx> auditList=new ArrayList<CarGrantAuditEx>();
		
		TaskBean taskBean = new TaskBean();
		List<CarLoanFlowWorkItemView> workItems=new ArrayList<CarLoanFlowWorkItemView>();
		try {
			
			ReflectHandle.copy(carLoanFlowQueryView, param);
			if (!StringUtils.isEmpty(idVal)) {// 没有选中的，默认为全部
				param.put("applyId", idVal.split(","));
			}
			taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER
					.getWorkQueue(), param,
					CarLoanFlowWorkItemView.class);

			 workItems = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
			
			if (ArrayHelper.isNotEmpty(workItems)){
				
				for (int i = 0; i < workItems.size(); i++) {
					CarGrantAuditEx carGrantAuditEx=new CarGrantAuditEx();
						ReflectHandle.copy(workItems.get(i), carGrantAuditEx);
						carGrantAuditEx.setLoanIsPhone(DictCache.getInstance().getDictLabel("jk_telemarketing", carGrantAuditEx.getLoanIsPhone()));
						carGrantAuditEx.setGrantRecepicResult(DictCache.getInstance().getDictLabel("jk_loansend_result", carGrantAuditEx.getGrantRecepicResult()));
						auditList.add(carGrantAuditEx);
				}
			}
			
			excelutil.exportExcel(auditList,"数据放款确认列表",null,CarGrantAuditEx.class,FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
			
		} catch (Exception e) {
			
		}
		
	}
	


	/**
	 * 放款表导出,默认导出查询条件下的全部的单子，有选择的按照选择进行导出
	 * 只对网银的单子进行导出
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "grantExl")
	public void grantExl(HttpServletRequest request,
			HttpServletResponse response, String idVal,CarLoanFlowQueryView carLoanFlowQueryView) {
		int index = 0;
		ProcessQueryBuilder param = new ProcessQueryBuilder();
		ExcelUtils excelutil = new ExcelUtils();
		String[] id=null;
		List<CarGrantEx> grantList=new ArrayList<CarGrantEx>();
		// cList用于跟新借款信息状态为已处理
		List<CarLoanInfo> cList = new ArrayList<CarLoanInfo>();
		TaskBean taskBean = new TaskBean();
		List<CarLoanFlowWorkItemView> workItems=new ArrayList<CarLoanFlowWorkItemView>();
		try {
			if (StringUtils.isEmpty(idVal)) {
				ReflectHandle.copy(carLoanFlowQueryView, param);
				param.put("grantPersons@like","%"+UserUtils.getUser().getId()+"%");
				 taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
						.getWorkQueue(), param,
						CarLoanFlowWorkItemView.class);
				 workItems = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
				if (ArrayHelper.isNotEmpty(workItems)) {
					
					for (int i = 0; i < workItems.size(); i++) {
						// 判断方式为网银放款
						
						if (workItems.get(i).getDictLoanWay().equals(PaymentWay.NET_BANK.getCode())) {
							
								HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
								hashMap.put("applyId", workItems.get(i).getApplyId());
								CarGrantEx gse=grantSureService.getGrantList(hashMap);
							
								if (gse!=null) {
									++index;
									CarLoanInfo uCarLoanInfo = new CarLoanInfo();
									//门店备注
									gse.setStoreName(gse.getStoreName()+"车辆款项");
									//产品类型转码
									gse.setAuditBorrowProductName(CarLoanProductType.parseByCode(gse.getAuditBorrowProductName()).getName());
									// 设置款项匹配时间
									gse.setLendingTime(new Date());
									// 设置序号
									gse.setIndex(new Integer(index).toString());
									grantList.add(gse);
									uCarLoanInfo.setApplyId(workItems.get(i).getApplyId());
									// 状态设为已处理
									uCarLoanInfo.setDictLoanStatus(CarLoanStatus.LOANING.getCode());
									cList.add(uCarLoanInfo);
								}
						}
					}
					
				}
			
			}else {
				id=idVal.split(",");
				for (int i = 0; i < id.length; i++) {
					
					CarLoanInfo uCarLoanInfo =	grantSureService.selectByApplyId(id[i]);		
						HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
						hashMap.put("applyId", id[i]);
						hashMap.put("dictLoanWay", PaymentWay.NET_BANK.getCode());
							CarGrantEx gse=grantSureService.getGrantList(hashMap);
							if (gse!=null) {
						
								++index;
								//门店备注
								gse.setStoreName(gse.getStoreName()+"车辆款项");
								// 设置款项匹配时间
								gse.setLendingTime(new Date());
								//产品类型转码
								gse.setAuditBorrowProductName(CarLoanProductType.parseByCode(gse.getAuditBorrowProductName()).getName());
								// 设置序号
								gse.setIndex(new Integer(index).toString());
								grantList.add(gse);
								uCarLoanInfo.setApplyId(id[i]);
								// 状态设为已处理
								uCarLoanInfo.setDictLoanStatus(CarLoanStatus.LOANING.getCode());
								cList.add(uCarLoanInfo);
							}
						
					
					
				}
			}
			excelutil.exportExcel(grantList, "待放款",null,CarGrantEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_DATA, response, null);
			
			
			// 导出数据后 将借款状态更改为已处理,并更改流程中的借款状态
			if (ArrayHelper.isNotEmpty(cList)) {
				for (int i = 0; i < cList.size(); i++) {
					ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
					queryParam.put("applyId", cList.get(i).getApplyId());
					TaskBean utaskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
							.getWorkQueue(), queryParam,
							BaseTaskItemView.class);
					List<BaseTaskItemView> workItemsList=new ArrayList<BaseTaskItemView>();
					workItemsList = (List<BaseTaskItemView>) utaskBean.getItemList();
					CarGrantDealView gqp=new CarGrantDealView();
					WorkItemView workItem = new WorkItemView();
					if (ArrayHelper.isNotEmpty(workItemsList)) {
						BaseTaskItemView baseView=workItemsList.get(0);
						ReflectHandle.copy(baseView, workItem);
						
					}
					gqp.setDictLoanStatus(cList.get(i).getDictLoanStatus());
					gqp.setApplyId(cList.get(i).getApplyId());
					
					workItem.setBv(gqp);
					flowService.saveData(workItem);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 待分配卡号数据导出
	 * @param request
	 * @param response
	 * @param idVal
	 * @param carLoanFlowQueryView 查询条件
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "expDisCardExl")
	public void expDisCardExl(HttpServletRequest request,
			HttpServletResponse response, String idVal, CarLoanFlowQueryView carLoanFlowQueryView) {
		
		ProcessQueryBuilder param = new ProcessQueryBuilder();
		ExcelUtils excelutil = new ExcelUtils();
		String[] id=null;
		List<CarDisCardEx> carDisCardExList=new ArrayList<CarDisCardEx>();
		TaskBean taskBean = new TaskBean();
		List<CarLoanFlowWorkItemView> workItems=new ArrayList<CarLoanFlowWorkItemView>();
	
		try {
				//查询出所有待分配卡号数据
			if (StringUtils.isEmpty(idVal)) {
				
				ReflectHandle.copy(carLoanFlowQueryView, param);
				 taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER
						.getWorkQueue(), param,
						CarLoanFlowWorkItemView.class);
	
				 workItems = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
				// 导出所有待分配卡号数据
				if (ArrayHelper.isNotEmpty(workItems)) {
					for (int i = 0; i < workItems.size(); i++) {
						if (CarLoanStatus.PENDING_ASSIGNED_CARD_NUMBER.getCode().equals(workItems.get(i).getDictStatus())) {
							CarDisCardEx  carDisCardEx = new CarDisCardEx();
							ReflectHandle.copy(workItems.get(i), carDisCardEx);
							// 设置合同编号
							carDisCardEx.setContractAmount(new Double(workItems.get(i).getContractAmount()).toString());
							//设置批借期限
							carDisCardEx.setAuditLoanMonths(new Integer(workItems.get(i).getAuditLoanMonths()).toString());
							// 设置划扣金额
							carDisCardEx.setDeductsAmount(new Double(workItems.get(i).getDeductsAmount()).toString());
							// 设置总费率
							carDisCardEx.setGrossRate(new Double(workItems.get(i).getGrossRate()).toString());
							carDisCardExList.add(carDisCardEx);
						}
	
						
					}

				}
			}else {
				// 查询 选中 的 待分配卡号数据
				id = idVal.split(",");
				ReflectHandle.copy(carLoanFlowQueryView, param);
				param.put("applyId", id);
				taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER
						.getWorkQueue(), param,
						CarLoanFlowWorkItemView.class);

				workItems = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
	
				for (int i = 0; i < id.length; i++) {
					for (int j = 0; j < workItems.size(); j++) {
						if (id[i].equals(workItems.get(j).getApplyId())) {
							
							if (CarLoanStatus.PENDING_ASSIGNED_CARD_NUMBER.getCode().equals(workItems.get(i).getDictStatus())) {
								CarDisCardEx  carDisCardEx = new CarDisCardEx();
								ReflectHandle.copy(workItems.get(i), carDisCardEx);
								// 设置合同编号
								carDisCardEx.setContractAmount(BigDecimal.valueOf(workItems.get(i).getContractAmount()).toString());
								//设置批借期限
								carDisCardEx.setAuditLoanMonths(new Integer(workItems.get(i).getAuditLoanMonths()).toString());
								// 设置划扣金额
								carDisCardEx.setDeductsAmount(new Double(workItems.get(i).getDeductsAmount()).toString());
								// 设置总费率
								carDisCardEx.setGrossRate(new Double(workItems.get(i).getGrossRate()).toString());
								carDisCardExList.add(carDisCardEx);
							}
				
						}
					}
				}
			}
			excelutil.exportExcel(carDisCardExList,"待分配卡号",null,CarDisCardEx.class,FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 放款退回,退回到合同审核,更新放款记录表，同时设置状态为放款退回,退回原因自动封装到gqp中
	 * @param applyId
	 * @param loanCode
	 * @param contractCode
	 * @param dictBackMestype  		退回原因
	 * @param remark				备注
	 * @param carLoanWorkQueues		工作队列
	 * @return 操作结果
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "backTo")
	@ResponseBody
	public String backTo(String applyId,String loanCode,String contractCode,String dictBackMestype,String remark,
			String carLoanWorkQueues) {
		
		List<BaseTaskItemView> workItems = new ArrayList<BaseTaskItemView>();
		WorkItemView workItem = new WorkItemView();
		CarGrantDealView gqp=new CarGrantDealView();
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		queryParam.put("applyId", applyId);
		TaskBean taskBean = flowService.fetchTaskItems(carLoanWorkQueues, queryParam,
				BaseTaskItemView.class);
		workItems = (List<BaseTaskItemView>) taskBean.getItemList();
		ReflectHandle.copy(workItems.get(0), workItem);
		gqp.setApplyId(applyId);
		gqp.setLoanCode(loanCode);
		gqp.setContractCode(contractCode);
		gqp.setDictBackMestype(dictBackMestype);
		gqp.setGrantBackResultCode(dictBackMestype);
		gqp.setRemark(remark);
		// 将放款回执结果置空
		gqp.setGrantRecepicResult("");
		// 将线上放款失败，失败原因置空
		gqp.setGrantFailResult("");
		if ( CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER
				.getWorkQueue().equals(carLoanWorkQueues)) {
			// 设置审核专员
			gqp.setCheckEmpId(UserUtils.getUser().getId());
			// 设置审核结果
			gqp.setCheckResult(CarLoanResult.BACK.getCode());
			// 设置审核时间
			gqp.setCheckTime(new Date());
			
		}
		gqp.setDictLoanStatus(CarLoanStatus.LOAN_BACK.getCode());
		workItem.setResponse(CarLoanResponses.BACK_GRANT_CONFIRM.getCode());
		//放款阶段特殊，通过底层接口获取标红置顶相关参数
		WorkItemView workItemView = flowService.loadWorkItemView(applyId, workItem.getWobNum(), workItem.getToken());
		workItem.setFlowProperties(workItemView.getFlowProperties());
		workItem.setBv(gqp);
		try {
			flowService.dispatch(workItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BooleanType.TRUE;

	}
	

	
}
