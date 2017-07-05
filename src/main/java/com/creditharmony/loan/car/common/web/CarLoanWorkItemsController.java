package com.creditharmony.loan.car.common.web;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.Reflections;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanFlowStepName;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 车借流程待办列表Controller
 * @Class Name CarLoanWorkItemsController
 * @author 陈伟东
 * @Create In 2016年2月13日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/car/carLoanWorkItems")
public class CarLoanWorkItemsController extends BaseController {
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private CityInfoService cityManager;
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	// 卡
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;

	@RequestMapping(value = "fetchTaskItems/{workQueue}")
	public String fetchTaskItems(Model model,
			@ModelAttribute(value = "carLoanFlowQueryParam") CarLoanFlowQueryView queryParms,@PathVariable("workQueue")String workQueue,
			FlowPage page,HttpServletRequest request) throws Exception{
		
		ProcessQueryBuilder param = new ProcessQueryBuilder();
	
		//申请日期查询条件组装	开始
		Date applyStartTime=queryParms.getLoanApplyTimeStart();
		Date applyEndTime=queryParms.getLoanApplyTimeEnd();
		queryParms.setLoanApplyTimeStart(null);
		queryParms.setLoanApplyTimeEnd(null);
		if(applyStartTime!=null)
		{
			param.put("loanApplyTime@>=", applyStartTime.getTime()/1000);
		}
		if(applyEndTime!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("loanApplyTime@<=", applyEndTime.getTime()/1000 + (24*60*60-1));
		}
		//申请日期查询条件组装	结束		
		
		//放款日期查询条件组装	开始
		Date lendingTimeStar=queryParms.getLendingTimeStart();
		Date lendingTimeEnd=queryParms.getLendingTimeEnd();
		queryParms.setLendingTimeStart(null);
		queryParms.setLendingTimeEnd(null);
		if(lendingTimeStar!=null)
		{
			param.put("lendingTime@>=", lendingTimeStar.getTime()/1000);
		}
		if(lendingTimeEnd!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("lendingTime@<=", lendingTimeEnd.getTime()/1000 + (24*60*60-1));
		}
		//放款日期查询条件组装	结束
		
		//终审日期查询条件组装	开始
		Date startTime=queryParms.getFinalCheckTimeStart();
		Date endTime=queryParms.getFinalCheckTimeEnd();
		queryParms.setFinalCheckTimeStart(null);
		queryParms.setFinalCheckTimeEnd(null);
		if(startTime!=null)
		{
			param.put("auditTime@>=", startTime.getTime()/1000);
		}
		if(endTime!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("auditTime@<=", endTime.getTime()/1000 + (24*60*60-1));
		}
		//终审日期查询条件组装	结束
		
		//签约日期查询条件组装	开始
		Date startDay=queryParms.getContractFactDayStart();
		Date endDay=queryParms.getContractFactDayEnd();
		queryParms.setContractFactDayStart(null);
		queryParms.setContractFactDayEnd(null);
		if(startDay!=null)
		{
			param.put("contractFactDay@>=", startDay.getTime()/1000);
		}
		if(endDay!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("contractFactDay@<=", endDay.getTime()/1000 + (24*60*60-1));
		}
		//签约日期查询条件组装	结束
		
		//分配卡号 开户行
		String cardBankName = queryParms.getCardBankName();
		queryParms.setCardBankName(null);
		
		
		ReflectHandle.copy(queryParms, param);
		
		//开户行名称保留值
		queryParms.setCardBankName(cardBankName);
		

		//申请日期查询条件保留值
		queryParms.setLoanApplyTimeStart(applyStartTime);
		queryParms.setLoanApplyTimeEnd(applyEndTime);
		
		//放款日期查询条件保留值
		queryParms.setLendingTimeStart(lendingTimeStar);
		queryParms.setLendingTimeEnd(lendingTimeEnd);
		
		//终审日期查询条件保留值
		queryParms.setFinalCheckTimeStart(startTime);
		queryParms.setFinalCheckTimeEnd(endTime);
		
		//签约日期查询条件保留值
		queryParms.setContractFactDayStart(startDay);
		queryParms.setContractFactDayEnd(endDay);
		
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String queue = "";
		String view = "";
		if (CarLoanWorkQueues.HJ_CAR_APPRAISER.getCode().equalsIgnoreCase(
				workQueue)) { // 评估师录入报告
			queue = CarLoanWorkQueues.HJ_CAR_APPRAISER.getWorkQueue();
			view = "car/carApply/appraiser_workItems";
			
		} else if (CarLoanWorkQueues.HJ_CAR_FACE_AUDIT.getCode()
				.equalsIgnoreCase(workQueue)) { // 面审申请,上传资料 ,签署,合同签约上传（不包含初审）
			queue = CarLoanWorkQueues.HJ_CAR_FACE_AUDIT.getWorkQueue();
			view = "car/carApply/carLoanflow_workItems";
			param.put("F_StepName@<>", CarLoanFlowStepName.FIRST_AUDIT);
			Org org = user.getDepartment();
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.TEAM.key.equals(orgType)){
				org = OrgCache.getInstance().get(org.getParentId());
			}
			param.put("storeCode",org.getId());//加入门店筛选
		} else if (CarLoanWorkQueues.HJ_CAR_FACE_AUDIT_FIRST_AUDIT.getCode()
				.equalsIgnoreCase(workQueue)) { // 面审初审
			queue = CarLoanWorkQueues.HJ_CAR_FACE_AUDIT_FIRST_AUDIT.getWorkQueue();
			view = "car/carApply/reviewMeet_firstAudit_workItems";
			param.put("F_StepName", CarLoanFlowStepName.FIRST_AUDIT);
			Org org = user.getDepartment();
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.TEAM.key.equals(orgType)){
				org = OrgCache.getInstance().get(org.getParentId());
			}
			param.put("storeCode",org.getId());//加入门店筛选
		} else if (CarLoanWorkQueues.HJ_CAR_RATE_CHECK.getCode()
				.equalsIgnoreCase(workQueue)) { // 审核费率
			queue = CarLoanWorkQueues.HJ_CAR_RATE_CHECK.getWorkQueue();
			if (CarLoanThroughFlag.HARMONY.getCode().equals(queryParms.getLoanFlag())) {
				String[] str = {CarLoanThroughFlag.HARMONY.getCode(), ""};
				param.put("loanFlag", str);
			}
			view = "car/contract/check_rate_workItems";
		} else if (CarLoanWorkQueues.HJ_CAR_CONTRACT_COMMISSIONER.getCode()
				.equalsIgnoreCase(workQueue)) { // 合同制作
			queue = CarLoanWorkQueues.HJ_CAR_CONTRACT_COMMISSIONER
					.getWorkQueue();
			if (CarLoanThroughFlag.HARMONY.getCode().equals(queryParms.getLoanFlag())) {
				String[] str = {CarLoanThroughFlag.HARMONY.getCode(), ""};
				param.put("loanFlag", str);
			}
			view = "car/contract/contract_production_workItems";
		} else if (CarLoanWorkQueues.HJ_CAR_CONTRACT_CHECK.getCode()
				.equalsIgnoreCase(workQueue)) { // 合同审核
			queue = CarLoanWorkQueues.HJ_CAR_CONTRACT_CHECK.getWorkQueue();
			view = "car/contract/contract_check_workItems";
		} else if (CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER.getCode()
				.equalsIgnoreCase(workQueue)) { // 放款确认
			queue = CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER
					.getWorkQueue();
			view = "car/contract/pending_loan_audit_workItems";
		} else if (CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER.getCode()
				.equalsIgnoreCase(workQueue)) { // 分配卡号
			queue = CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER
					.getWorkQueue();
			view = "car/grant/carLoanflow_disCardList";
			List<CityInfo> provinceList = cityManager.findProvince();
			model.addAttribute("provinceList", provinceList);
		} else if (CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER.getCode()
				.equalsIgnoreCase(workQueue)) { // 放款
			queue = CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
					.getWorkQueue();
		
			param.put("grantPersons@like","%"+UserUtils.getUser().getId()+"%");
			view = "car/grant/grantList_workItems";
		} else if (CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER.getCode()
				.equalsIgnoreCase(workQueue)) { // 放款审核
			queue = CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER
					.getWorkQueue();
			view = "car/grant/grantAudit_workItems";
		}
		//分页检索	开始
		String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        Integer ps = 30;
        Integer pn = 1;
        if(!ObjectHelper.isEmpty(pageSize)){
            ps = Integer.valueOf(pageSize);
        }
        if(!ObjectHelper.isEmpty(pageNo)){
            pn = Integer.valueOf(pageNo); 
        }
        page.setPageSize(ps);
        page.setPageNo(pn);
		flowService.fetchTaskItems(queue, param,page,null,
				CarLoanFlowWorkItemView.class);
		List<CarLoanFlowWorkItemView> itemList = null;
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		itemList = this.convertList(sourceWorkItems);
		//分页检索	结束	
		
		if (itemList != null) {
			// 借款状态为 退回 的数据进行置顶
			//itemList = backToP(itemList);
			// dictStatus--jk_car_loan_status  contractBackResultCode--jk_chk_back_reason  loanIsPhone--jk_telemarketing
			Map<String, String> map = new HashMap<String, String>();
			map.put("dictStatus", "jk_car_loan_status");
			map.put("applyStatusCode", "jk_car_loan_status");
			map.put("contractBackResultCode", "jk_chk_back_reason");
			map.put("loanIsPhone", "jk_telemarketing");
			map.put("cardBank", "jk_open_bank");
			map.put("grantBackResultCode", "jk_chk_back_reason");
			map.put("grantRecepicResult", "jk_loansend_result");
			map.put("dictLoanWay", "jk_payment_way");
			map.put("extensionFlag", "jk_extend_loan_flag");
			map.put("loanFlag", "jk_car_throuth_flag");
			for (CarLoanFlowWorkItemView listItem : itemList) {
				Date contractExpirationDate = listItem.getContractExpirationDate();
	            Date loanApplyTime = listItem.getLoanApplyTime();
	            if(null!= contractExpirationDate && null != loanApplyTime && Math.abs((contractExpirationDate.getTime() - loanApplyTime.getTime()) / 86400000) < 1){
	            	listItem.setContractExpirationDate(null);
	            }
				for (Map.Entry<String, String> entry : map.entrySet()) {
					PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(listItem.getClass()).getPropertyDescriptors();
					for (int i = 0; i < propertyDescriptors.length; i++) {
		                PropertyDescriptor descriptor = propertyDescriptors[i];
		                String propertyName = descriptor.getName();
		                if (!propertyName.equals("class")) {
		                    Method readMethod = descriptor.getReadMethod();
		                    if (readMethod != null) {
		                    	if (entry.getKey().equalsIgnoreCase(propertyName)) {
		                    		Object result = readMethod.invoke(listItem, new Object[0]);
		                    		if (result != null) {
		                    			Reflections.setFieldValue(listItem, propertyName, DictCache.getInstance().getDictLabel(entry.getValue(), result.toString()));
		                    		}
		                    	}
		                    }
		                }
		            }
					//临时解决共借人多逗号问题 
					if(listItem.getCoborrowerName()!=null&&listItem.getCoborrowerName().length()>1&&",".equals(listItem.getCoborrowerName().substring(0, 1))){
						listItem.setCoborrowerName(listItem.getCoborrowerName().substring(1));
					}
				}
			}
			
			// 分配卡号、待放款、待放款审核  节点算出放款累计金额(放款金额取合同金额的值)
			double totalGrantMoney = 0;
			if (CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER.getCode().equalsIgnoreCase(workQueue) || 
				CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER.getCode().equalsIgnoreCase(workQueue)  ||
				CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_MANAGER.getCode().equalsIgnoreCase(workQueue)
					) {
				for (int i = 0; i < itemList.size(); i++) {
					totalGrantMoney= totalGrantMoney +itemList.get(i).getContractAmount();
				}
				
				model.addAttribute("totalGrantMoney", totalGrantMoney);
			}
			if(CarLoanWorkQueues.HJ_CAR_STATISTICS_COMMISSIONER.getCode().equalsIgnoreCase(workQueue)){
				for(CarLoanFlowWorkItemView listItem : itemList){
					String loanCode = listItem.getLoanCode();
					CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
					if (carCustomerBankInfo != null) {
						// 得到开卡支行
						String cardBank = carCustomerBankInfo.getCardBank();
						String applyBankName = carCustomerBankInfo.getApplyBankName();
						listItem.setCardBank(cardBank + applyBankName);
					}
				}
			}
//			for(CarLoanFlowWorkItemView listItem : itemList){
//				CarLoanInfo info = carLoanInfoService.selectByApplyId(listItem.getApplyId());
//				if(info !=null && info.getLoanFlag()!=null&&!"".equals(info.getLoanFlag())){
//					if(info.getLoanFlag().equals("1")){
//						listItem.setLoanIsPhone("P2P");
//					}else{
//						listItem.setLoanIsPhone("");
//					}
//				}else{
//					listItem.setLoanIsPhone("");
//				}
//			}
		}
	
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		//TODO 此处临时加入评估师录入页面
//		ProcessQueryBuilder param1 = new ProcessQueryBuilder();
//		ReflectHandle.copy(queryParms, param1);
//		taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_APPRAISER.getWorkQueue(), param1,
//				CarLoanFlowWorkItemView.class);
//		if (taskBean.getItemList() != null) {
//			itemList = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
//		}
//		model.addAttribute("pitemList", itemList);
		
		return view;
	}
	
	/**
	 * 将退回的数据 进行置顶标红
	 * 2016年3月15日
	 * @param srcList
	 * By 张振强
	 * @return
	 */
	public List<CarLoanFlowWorkItemView> backToP(List<CarLoanFlowWorkItemView> srcList){
		List<CarLoanFlowWorkItemView> tempList = new ArrayList<CarLoanFlowWorkItemView>();
		if (ArrayHelper.isNotEmpty(srcList)) {
			for (int i = srcList.size() - 1; i >= 0; i--) {
				String dictStatus = srcList.get(i).getDictStatus();
				if (CarLoanStatus.FIRST_INSTANCE_BACK.getCode().equals(dictStatus) || CarLoanStatus.REVIEW_BACK.getCode().equals(dictStatus) ||
					CarLoanStatus.FINAL_AUDIT_BACK.getCode().equals(dictStatus) || CarLoanStatus.PENDING_AUDIT_INTEREST_RATE_BACK.getCode().equals(dictStatus) ||
					CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode().equals(dictStatus) || CarLoanStatus.PENDING_SIGNED_CONTRAC_BACK.getCode().equals(dictStatus) || 
					CarLoanStatus.PENDING_CONTRACT_REVIEW_BACK.getCode().equals(dictStatus) || CarLoanStatus.PENDING_LOAN_CONFIRMATION_BACK.getCode().equals(dictStatus) || 
					CarLoanStatus.LOAN_BACK.getCode().equals(dictStatus) || CarLoanStatus.UPLOADED_FILE_BACK.getCode().equals(dictStatus) ||
					CarLoanStatus.PENDING_CONFIRMED_SIGN_BACK.getCode().equals(dictStatus)) {
					
					tempList.add(srcList.get(i));
					srcList.remove(i);
	
				}
			}
			if (ArrayHelper.isNotEmpty(tempList)) {
				for (int i = 0; i < tempList.size(); i++) {
					tempList.get(i).setBackTop("0");
					srcList.add(0, tempList.get(i));
				}
			}
			
		}
		return srcList;
	}
	
	/**
     *将流程中查询出来的数据类型进行转封装 
     *@author jiangli
     *@Create In 2016年4月15日
     *@param  sourceWorkItems
     *@return List<CarLoanFlowWorkItemView> 
     */
    private List<CarLoanFlowWorkItemView> convertList(
            List<BaseTaskItemView> sourceWorkItems) {
        List<CarLoanFlowWorkItemView> targetList = new ArrayList<CarLoanFlowWorkItemView>();
        if (!ObjectHelper.isEmpty(sourceWorkItems)) {
            for (BaseTaskItemView currItem : sourceWorkItems)
                targetList.add((CarLoanFlowWorkItemView) currItem);
        }
        return targetList;
    }
}
