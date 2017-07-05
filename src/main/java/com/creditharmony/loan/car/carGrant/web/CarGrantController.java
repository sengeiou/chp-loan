package com.creditharmony.loan.car.carGrant.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carGrant.ex.CarGrantEx;
import com.creditharmony.loan.car.carGrant.ex.CarLoanGrantEx;
import com.creditharmony.loan.car.carGrant.service.CarGrantDoneService;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;
import com.creditharmony.loan.car.carGrant.service.CarGrantSureService;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarLoanGrant;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.utils.ExcelMatch;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 放款处理
 * @Class Name CarGrantController
 * @Create In 2016年2月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/grant/grantDeal")
public class CarGrantController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private CarHistoryService historyService;
	@Autowired
	private CarGrantSureService grantSureService;
	@Autowired
	private CarGrantDoneService grantDoneService;
	
	@Autowired
	CarGrantRecepicService carGrantRecepicService;

	/**
	 * 线下放款处理，上传回执结果,同时更新催收服务费表,放款操作人员要进行更新 
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) {
		try {
			if (ExcelMatch.matchResult(file, CarGrantEx.class)) {
				ExcelUtils excelutil = new ExcelUtils();
				ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
				List<CarGrantEx> lst = new ArrayList<CarGrantEx>();
				List<?> datalist = excelutil
						.importExcel(file, 0, 0,
								CarGrantEx.class,null);
				lst = (List<CarGrantEx>) datalist;
				// 根据合同号查询applyId
				// 循环，得到列表中的单子，修改单子中的回执结果为成功，同时将单子的状态更新待放款审核，同时更新流程中的数据
				String applyId = "";
				WorkItemView workItem = new WorkItemView();
				if (ArrayHelper.isNotEmpty(lst)) {
					for (int i = 0; i < lst.size(); i++) {
						applyId = grantDoneService.selApplyId(lst.get(i).getContractCode());
						CarLoanGrant carLoanGrant = new CarLoanGrant();
						carLoanGrant.setContractCode(lst.get(i).getContractCode());
						CarLoanGrantEx carLoanGrantEx = grantSureService.findGrant(carLoanGrant);
						CarLoanInfo carLoanInfo =	grantSureService.selectByApplyId(applyId);
						// 此处通过借款状态进行判断，只处理借款状态为已处理的数据
						if(carLoanGrantEx != null && carLoanInfo != null && 
								PaymentWay.NET_BANK.getCode().equals(carLoanGrantEx.getDictLoanWay()) && 
								CarLoanStatus.LOANING.getCode().equals(carLoanInfo.getDictLoanStatus())){
							queryParam.put("applyId", applyId);
							List<BaseTaskItemView> baseViewList = (List<BaseTaskItemView>) flowService
									.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
											.getWorkQueue(), queryParam,
											BaseTaskItemView.class).getItemList();
							//对baseViewList 判断是否为空
							if (ArrayHelper.isNotEmpty(baseViewList)) {
								BaseTaskItemView baseView = baseViewList.get(0);
								ReflectHandle.copy(baseView, workItem);
								CarGrantDealView gqp = new CarGrantDealView();
								gqp.setApplyId(baseView.getApplyId());
								// 设置合同编号
								gqp.setContractCode(lst.get(i).getContractCode());
								// 设置借款编码，插入历史的时候用
								gqp.setLoanCode(carLoanInfo.getLoanCode());
								// 设置响应值  到待放款审核
								workItem.setResponse(CarLoanResponses.TO_GRANT_AUDIT.getCode());
								// 设置单子状态,从字典表中取值，更改单子的状态,待待放款确认
								gqp.setDictLoanStatus(CarLoanStatus.LOAN_AUDIT.getCode());
								// 放款回执结果，直接从字典表中取值
								gqp.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
										.getCode());
								//设置放款时间
								if (lst.get(i).getLendingTime() != null) {
									gqp.setLendingTime(lst.get(i).getLendingTime());
									
								} else {
									gqp.setLendingTime(new Date());
								}
								workItem.setBv(gqp);
								//放款阶段特殊，通过底层接口获取标红置顶相关参数
								WorkItemView workItemView = flowService.loadWorkItemView(applyId, workItem.getWobNum(), workItem.getToken());
								workItem.setFlowProperties(workItemView.getFlowProperties());
								try {
									flowService.dispatch(workItem);
								} catch(Exception e) {
									
								}
							} else {
								return BooleanType.TRUE;
							}
						}
					}
				}
			} else {
				return BooleanType.FALSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 线下放款处理，手动确认 2016年2月17日 
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "manualSure")
	public String manualSure(HttpServletRequest request,
			HttpServletResponse response, String contractCode) {
			ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
			String[] contractCodes = contractCode.split(",");
			// 根据合同号查询applyId
			// 循环，得到列表中的单子，修改单子中的回执结果为成功，同时将单子的状态更新待放款审核，同时更新流程中的数据
			String applyId;
			WorkItemView workItem = new WorkItemView();
			for (int i = 0; i < contractCodes.length; i++) {
				applyId = grantDoneService.selApplyId(contractCodes[i]);
				CarLoanGrant carLoanGrant = new CarLoanGrant();
				carLoanGrant.setContractCode(contractCodes[i]);
				CarLoanInfo carLoanInfo =	grantSureService.selectByApplyId(applyId);
				CarLoanGrantEx carLoanGrantEx = grantSureService.findGrant(carLoanGrant);
				// 此处通过借款状态进行判断，只处理借款状态已处理的数据
				if (	carLoanInfo != null && carLoanGrantEx != null &&
						CarLoanStatus.LOANING.getCode().equals(carLoanInfo.getDictLoanStatus()) &&
						PaymentWay.NET_BANK.getCode().equals(carLoanGrantEx.getDictLoanWay())) {
					
					queryParam.put("applyId", applyId);
					List<BaseTaskItemView> baseViewList = (List<BaseTaskItemView>) flowService
							.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
									.getWorkQueue(), queryParam,
									BaseTaskItemView.class).getItemList();
					//对baseViewList 判断是否为空
					if (ArrayHelper.isNotEmpty(baseViewList)) {
						BaseTaskItemView baseView = baseViewList.get(0);
						ReflectHandle.copy(baseView, workItem);
						CarGrantDealView gqp = new CarGrantDealView();
						gqp.setApplyId(baseView.getApplyId());
						// 设置合同编号
						gqp.setContractCode(contractCodes[i]);
						// 设置借款编码，插入历史的时候用
						gqp.setLoanCode(carLoanInfo.getLoanCode());
						// 设置响应值  到待放款审核
						workItem.setResponse(CarLoanResponses.TO_GRANT_AUDIT.getCode());
						// 设置单子状态,从字典表中取值，更改单子的状态,待放款审核
						gqp.setDictLoanStatus(CarLoanStatus.LOAN_AUDIT.getCode());
						// 放款回执结果，直接从字典表中取值
						gqp.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
								.getCode());
						//设置放款时间
							gqp.setLendingTime(new Date());
						workItem.setBv(gqp);
						//放款阶段特殊，通过底层接口获取标红置顶相关参数
						WorkItemView workItemView = flowService.loadWorkItemView(applyId, workItem.getWobNum(), workItem.getToken());
						workItem.setFlowProperties(workItemView.getFlowProperties());
					   try{
						flowService.dispatch(workItem);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						return "redirect:"
								+ adminPath
								+ "/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner";
					}
				}
			}
		return "redirect:"
				+ adminPath
				+ "/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner";
	}


}
