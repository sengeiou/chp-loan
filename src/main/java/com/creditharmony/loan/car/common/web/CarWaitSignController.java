package com.creditharmony.loan.car.common.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carExtend.consts.CarExtendWorkQueues;
import com.creditharmony.loan.car.carExtend.view.CarExtendFlowQueryView;
import com.creditharmony.loan.car.carExtend.view.CarExtendFlowWorkItemView;
import com.creditharmony.loan.car.common.consts.CarLoanFlowStepName;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

/**
 * 车借及展期待确认列表Controller
 * @Class Name CarWaitSingController
 * @Create In 2016年3月21日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carContract/waitSign")
public class CarWaitSignController extends BaseController {
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	@Autowired
	private CityInfoService cityManager;
	// 借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	// 展期待确认签署列表
	@RequestMapping(value = "contractWaitSignExtend")
	public String fetchTaskItems(
			Model model,
			@ModelAttribute(value = "carExtendFlowQueryView") CarExtendFlowQueryView queryParms,
			FlowPage page, HttpServletRequest request) throws Exception {

		ProcessQueryBuilder param = new ProcessQueryBuilder();

		ReflectHandle.copy(queryParms, param);

		String queue = "";
		String view = "";

		queue = CarExtendWorkQueues.HJ_CAR_EXTEND_RECEIVED.getWorkQueue();

		view = "car/carExtend/workItems/carExtend_wait_sign_workItems";
		// 分页检索 开始
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("pageNo");
		Integer ps = 30;
		Integer pn = 1;
		if (!ObjectHelper.isEmpty(pageSize)) {
			ps = Integer.valueOf(pageSize);
		}
		if (!ObjectHelper.isEmpty(pageNo)) {
			pn = Integer.valueOf(pageNo);
		}
		page.setPageSize(ps);
		page.setPageNo(pn);
		flowService.fetchTaskItems(queue, param, page, null, CarExtendFlowWorkItemView.class);
		
		List<CarExtendFlowWorkItemView> itemList = new ArrayList<CarExtendFlowWorkItemView>();
		CarExtendFlowWorkItemView carExtendFlowWorkItemView = new CarExtendFlowWorkItemView();
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		itemList = this.convertExtendList(sourceWorkItems);
		List<CarExtendFlowWorkItemView> itemLists = new ArrayList<CarExtendFlowWorkItemView>();
		for (int i = 0; i < itemList.size(); i++) {
			carExtendFlowWorkItemView = itemList.get(i);
			if (carExtendFlowWorkItemView.getApplyStatusCode().equals(CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode())
					|| carExtendFlowWorkItemView.getApplyStatusCode().equals(CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode())
					|| carExtendFlowWorkItemView.getApplyStatusCode().equals(CarLoanStatus.PENDING_SIGNED_CONTRAC_BACK.getCode())) {
				carExtendFlowWorkItemView.setApplyStatusCode(DictCache.getInstance().getDictLabel("jk_car_loan_status", carExtendFlowWorkItemView.getApplyStatusCode()));
				itemLists.add(carExtendFlowWorkItemView);
			}
		}
		itemLists = backToPExtend(itemLists);
		model.addAttribute("itemList", itemLists);
		model.addAttribute("page", page);

		return view;
	}

	// 待确认签署列表
	@RequestMapping(value = "contractWaitSign")
	public String carWaitSingList(
			Model model,
			@ModelAttribute(value = "carLoanFlowQueryParam") CarLoanFlowQueryView queryParms,
			FlowPage page, HttpServletRequest request) throws Exception {

		ProcessQueryBuilder param = new ProcessQueryBuilder();

		ReflectHandle.copy(queryParms, param);

		param.put("F_StepName", CarLoanFlowStepName.CONFIRM_SIGNING);

		@SuppressWarnings("unused")
		User user = UserUtils.getUser();
		String queue = "";
		String view = "";

		queue = CarLoanWorkQueues.HJ_CAR_FACE_AUDIT.getWorkQueue();

		// List<CarLoanFlowWorkItemView> itemList = new
		// ArrayList<CarLoanFlowWorkItemView>();

		view = "car/contract/contract_wait_sign_workItems";
		// 分页检索 开始
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("pageNo");
		Integer ps = 30;
		Integer pn = 1;
		if (!ObjectHelper.isEmpty(pageSize)) {
			ps = Integer.valueOf(pageSize);
		}
		if (!ObjectHelper.isEmpty(pageNo)) {
			pn = Integer.valueOf(pageNo);
		}
		page.setPageSize(ps);
		page.setPageNo(pn);
		flowService.fetchTaskItems(queue, param, page, null,
				CarLoanFlowWorkItemView.class);
		/*
		 * TaskBean taskBean = flowService.fetchTaskItems(queue, param,
		 * CarLoanFlowWorkItemView.class); if (taskBean.getItemList() != null) {
		 * itemList = (List<CarLoanFlowWorkItemView>) taskBean.getItemList(); }
		 */
		List<CarLoanFlowWorkItemView> itemList = null;
		List<BaseTaskItemView> sourceWorkItems = page.getList();
		itemList = this.convertList(sourceWorkItems);
		List<CarLoanFlowWorkItemView> itemLists = new ArrayList<CarLoanFlowWorkItemView>();
		CarLoanFlowWorkItemView carLoanFlowWorkItemView = new CarLoanFlowWorkItemView();
		for (int i = 0; i < itemList.size(); i++) {
			carLoanFlowWorkItemView = itemList.get(i);
			if (carLoanFlowWorkItemView.getDictStatus().equals(CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode())
					|| carLoanFlowWorkItemView.getDictStatus().equals(CarLoanStatus.PENDING_PRODUCED_CONTRACT_BACK.getCode())
					|| carLoanFlowWorkItemView.getDictStatus().equals(CarLoanStatus.PENDING_SIGNED_CONTRAC_BACK.getCode())) {
				carLoanFlowWorkItemView.setDictStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", carLoanFlowWorkItemView.getDictStatus()));
				carLoanFlowWorkItemView.setLoanIsPhone(DictCache.getInstance().getDictLabel("jk_telemarketing", carLoanFlowWorkItemView.getLoanIsPhone()));
				carLoanFlowWorkItemView.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", carLoanFlowWorkItemView.getLoanFlag()));
				itemLists.add(carLoanFlowWorkItemView);
			}
		}
		itemLists = backToP(itemLists);
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
	 * 将退回的数据 进行置顶标红
	 * 2016年3月15日
	 * @param srcList
	 * By 张振强
	 * @return
	 */
	public List<CarExtendFlowWorkItemView> backToPExtend(List<CarExtendFlowWorkItemView> srcList){
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
	
    //车借修改P2P标识
	@ResponseBody
	@RequestMapping(value = "updateCarExtendP2PStatu")
	public String updateCarExtendP2PStatu(String loanApplyIds, String type) {
		try {
			if(StringUtils.isNotEmpty(loanApplyIds)&&StringUtils.isNotEmpty(type)){
				if(type.equals("1")){
					//1添加标识
					carLoanInfoService.updateCarExtendP2PStatu(type,loanApplyIds.split(","));

				}else{
					//2取消标识
					type = "";
					carLoanInfoService.updateCarExtendP2PStatu(type,loanApplyIds.split(","));
				}
				
			}else{
				return "false";
			}
		} catch (Exception e) {
			return "date";
		}
		return BooleanType.TRUE;
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
    
    private List<CarExtendFlowWorkItemView> convertExtendList(
            List<BaseTaskItemView> sourceWorkItems) {
        List<CarExtendFlowWorkItemView> targetList = new ArrayList<CarExtendFlowWorkItemView>();
        if (!ObjectHelper.isEmpty(sourceWorkItems)) {
            for (BaseTaskItemView currItem : sourceWorkItems)
                targetList.add((CarExtendFlowWorkItemView) currItem);
        }
        return targetList;
    }
}
