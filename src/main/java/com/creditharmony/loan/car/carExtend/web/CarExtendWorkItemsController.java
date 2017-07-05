package com.creditharmony.loan.car.carExtend.web;

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
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carExtend.consts.CarExtendWorkQueues;
import com.creditharmony.loan.car.carExtend.view.CarExtendFlowQueryView;
import com.creditharmony.loan.car.carExtend.view.CarExtendFlowWorkItemView;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 展期流程待办列表Controller
 * @Class Name CarExtendWorkItemsController
 * @author 陈伟东
 * @Create In 2016年3月2日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/car/carExtendWorkItems")
public class CarExtendWorkItemsController extends BaseController {
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;

	@RequestMapping(value = "fetchTaskItems/{workQueue}")
	public String fetchTaskItems(Model model,
			@ModelAttribute(value = "carExtendFlowQueryView") CarExtendFlowQueryView queryView,@PathVariable("workQueue")String workQueue,
			FlowPage page, HttpServletRequest request) throws Exception{
	
		ProcessQueryBuilder param = new ProcessQueryBuilder();
		//申请日期查询条件组装	开始
		Date applyStartTime=queryView.getLoanApplyTimeStart();
		Date applyEndTime=queryView.getLoanApplyTimeEnd();
		queryView.setLoanApplyTimeStart(null);
		queryView.setLoanApplyTimeEnd(null);
		if(applyStartTime!=null)
		{
			param.put("loanApplyTime@>=", applyStartTime.getTime()/1000);
		}
		if(applyEndTime!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("loanApplyTime@<=", applyEndTime.getTime()/1000 + (24*60*60-1));
		}
		//申请日期查询条件组装	结束
		
		//终审日期查询条件组装	开始
		Date startTime=queryView.getFinalCheckTimeStart();
		Date endTime=queryView.getFinalCheckTimeEnd();
		queryView.setFinalCheckTimeStart(null);
		queryView.setFinalCheckTimeEnd(null);
		if(startTime!=null)
		{
			param.put("finalCheckTime@>=", startTime.getTime()/1000);
		}
		if(endTime!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("finalCheckTime@<=", endTime.getTime()/1000 + (24*60*60-1));
		}
		//终审日期查询条件组装	结束
		
		

		//签约日期查询条件组装	开始
		Date startDay=queryView.getContractFactDayStart();
		Date endDay=queryView.getContractFactDayEnd();
		queryView.setContractFactDayStart(null);
		queryView.setContractFactDayEnd(null);
		if(startDay!=null)
		{
			param.put("contractFactDay@>=", startDay.getTime()/1000);
		}
		if(endDay!=null)
		{//查询结束时间补充成 当天 23:59:59
			param.put("contractFactDay@<=", endDay.getTime()/1000 + (24*60*60-1));
		}
		//签约日期查询条件组装	结束
		
		ReflectHandle.copy(queryView, param);
		
		//申请日期查询条件保留值
		queryView.setLoanApplyTimeStart(applyStartTime);
		queryView.setLoanApplyTimeEnd(applyEndTime);
		//终审日期查询条件保留值
		queryView.setFinalCheckTimeStart(startTime);
		queryView.setFinalCheckTimeEnd(endTime);
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String queue = "";
		String view = "";
		if (CarExtendWorkQueues.HJ_CAR_EXTEND_RECEIVED.getCode().equalsIgnoreCase(
				workQueue)) { // 回款岗
			queue = CarExtendWorkQueues.HJ_CAR_EXTEND_RECEIVED.getWorkQueue();
			view = "car/carExtend/workItems/extendReceived_workItems";
//			param.put("storeId",user.getDepartment().getId());//加入门店筛选
			Org org = user.getDepartment();
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.TEAM.key.equals(orgType)){
				org = OrgCache.getInstance().get(org.getParentId());
			}
			param.put("storeId",org.getId());//加入门店筛选
		} else if (CarExtendWorkQueues.HJ_CAR_EXTEND_APPRAISER.getCode()
				.equalsIgnoreCase(workQueue)) { // 评估师
			queue = CarExtendWorkQueues.HJ_CAR_EXTEND_APPRAISER.getWorkQueue();
			view = "car/carExtend/workItems/appraiser_workItems";
//			param.put("storeId",user.getDepartment().getId());//加入门店筛选
			Org org = user.getDepartment();
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.TEAM.key.equals(orgType)){
				org = OrgCache.getInstance().get(org.getParentId());
			}
			param.put("storeId",org.getId());//加入门店筛选
		} else if (CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_COMMISSIONER_RATE_CHECK.getCode()
				.equalsIgnoreCase(workQueue)) { // 利率审核待办列表
			queue = CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_COMMISSIONER_RATE_CHECK.getWorkQueue();
			view = "car/carExtend/workItems/rateCheck_workItems";
			param.put("F_StepName", CarLoanSteps.RATE_CHECK.getName());
			
		} else if (CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_COMMISSIONER.getCode()
				.equalsIgnoreCase(workQueue)) { // 合同制作待办列表
			queue = CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_COMMISSIONER.getWorkQueue();
			view = "car/carExtend/workItems/contractProduct_workItems";
			param.put("F_StepName", CarLoanSteps.CONTRACT_PRODUCT.getName());
			
		} else if (CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_CHECK.getCode()
				.equalsIgnoreCase(workQueue)) { // 合同审核待办列表
			queue = CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_CHECK.getWorkQueue();
			view = "car/carExtend/workItems/contractCheck_workItems";
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
		flowService.fetchTaskItems(queue, param, page, null, CarExtendFlowWorkItemView.class);
		List<CarExtendFlowWorkItemView> itemList = null;
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		itemList = this.convertList(sourceWorkItems);
		//分页检索	结束
		
		if (itemList != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("applyStatusCode", "jk_car_loan_status");
			map.put("loanIsPhone", "jk_telemarketing");
			map.put("extensionFlag", "jk_extend_loan_flag");
			map.put("loanFlag", "jk_car_throuth_flag");
			for (CarExtendFlowWorkItemView listItem : itemList) {
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
				}
				//判断合同到期提醒日期的真实性（若等于申请日期则设置该值为空）
				Date contractExpirationDate = listItem.getContractExpirationDate();
				Date loanApplyTime = listItem.getLoanApplyTime();
				if(contractExpirationDate != null && loanApplyTime != null){
					if(Math.abs((contractExpirationDate.getTime() - loanApplyTime.getTime())/86400000)  <1 ){
						listItem.setContractExpirationDate(null);
					}
				}
			}
		}
		itemList = backToP(itemList);
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		
		return view;
	}
	
	/**
	 * 将退回的数据 进行置顶标红
	 * 2016年3月15日
	 * @param srcList
	 * By 张振强
	 * @return
	 */
	@Deprecated
	public List<CarExtendFlowWorkItemView> backToP(List<CarExtendFlowWorkItemView> srcList){
		List<CarExtendFlowWorkItemView> tempList = new ArrayList<CarExtendFlowWorkItemView>();
		if (ArrayHelper.isNotEmpty(srcList)) {
			for (int i = srcList.size() - 1; i >= 0; i--) {
				String dictStatus = srcList.get(i).getApplyStatusCode();
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
	 * 将流程中查询出来的数据类型进行转封装
	 * 2016年4月21日
	 * By 申诗阔
	 * @param sourceWorkItems
	 * @return
	 */
    private List<CarExtendFlowWorkItemView> convertList(
            List<BaseTaskItemView> sourceWorkItems) {
        List<CarExtendFlowWorkItemView> targetList = new ArrayList<CarExtendFlowWorkItemView>();
        if (!ObjectHelper.isEmpty(sourceWorkItems)) {
            for (BaseTaskItemView currItem : sourceWorkItems)
                targetList.add((CarExtendFlowWorkItemView) currItem);
        }
        return targetList;
    }
}
