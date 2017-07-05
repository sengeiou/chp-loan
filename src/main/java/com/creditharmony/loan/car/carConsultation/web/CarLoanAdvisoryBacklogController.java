package com.creditharmony.loan.car.carConsultation.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carConsultation.ex.CarLoanAdvisoryBacklogEx;
import com.creditharmony.loan.car.carConsultation.service.CarLoanAdvisoryBacklogService;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.query.ProcessQueryBuilder;

/**
 * 车借咨询待办、车借客户管理
 * @Class Name CarLoanAdvisoryBacklogController
 * @author ganquan
 * @Create In 2016年1月22日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/car/CarLoanAdvisoryBacklog")
public class CarLoanAdvisoryBacklogController extends BaseController {
	//搜索车借咨询信息service
	@Autowired
    private CarLoanAdvisoryBacklogService carLoanAdvisoryBacklogService;
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	
	/**
     * 车借咨询进入页面/搜索 
     * 2015年1月22日 By 甘泉
     * @param CarLoanAdvisoryBacklogEx
     * @param model
     * @return CarBorrowAdvisoryList.jsp
     */
	@RequestMapping(value = "CarLoanAdvisoryBacklog")
	public String CarLoanAdvisoryBacklog(Model model,@ModelAttribute(value = "carLoanAdvisoryBacklogEx") CarLoanAdvisoryBacklogEx CarLoanAdvisoryBacklogEx,
			HttpServletRequest request,HttpServletResponse response){
		Page<CarLoanAdvisoryBacklogEx> page = new Page<CarLoanAdvisoryBacklogEx>(request, response);
		Org org = UserUtils.getUser().getDepartment();
		String orgType = org != null ? org.getType() : "";
		if(LoanOrgType.STORE.key.equals(orgType) || LoanOrgType.TEAM.key.equals(orgType)){
			if (LoanOrgType.TEAM.key.equals(orgType)) {
				org = OrgCache.getInstance().get(org.getParentId());
			}
			CarLoanAdvisoryBacklogEx.setStoreId(org.getId());
		}
		page=carLoanAdvisoryBacklogService.selectByCarLoanAdvisoryBacklog(page,CarLoanAdvisoryBacklogEx);
		
		//咨询待办和评估师待办二合一，评估师待办所需参数另外从workqueue中取
		if(page.getList()!=null&&page.getList().size()>0)
		{
			for(int i=0;i<page.getList().size();i++)
			{
				String dictLoanStatus = page.getList().get(i).getDictLoanStatus();
				String customerCode = page.getList().get(i).getCustomerCode();
				if(CarLoanStatus.UPLOADED_FILE_BACK.getCode().equals(dictLoanStatus)
					||CarLoanStatus.FIRST_INSTANCE_BACK.getCode().equals(dictLoanStatus))
				{//借款状态为上传退回和初审退回时，加载评估师办理数据
					ProcessQueryBuilder param = new ProcessQueryBuilder();
					String queue = CarLoanWorkQueues.HJ_CAR_APPRAISER.getWorkQueue();
					param.put("customerCode",customerCode);
					TaskBean taskBean = flowService.fetchTaskItems(queue, param,
					CarLoanFlowWorkItemView.class);
					List<CarLoanFlowWorkItemView> itemList = new ArrayList<CarLoanFlowWorkItemView>();
					if (taskBean.getItemList() != null) {
						itemList = (List<CarLoanFlowWorkItemView>) taskBean.getItemList();
					}
					if(!itemList.isEmpty())
					{
						page.getList().get(i).setApplyId(itemList.get(0).getApplyId());
						page.getList().get(i).setWobNum(itemList.get(0).getWobNum());
						page.getList().get(i).setToken(itemList.get(0).getToken());
						page.getList().get(i).setOrderField(itemList.get(0).getOrderField());
					}
				}
			}
			
		}
		
		model.addAttribute("page",page);
		return "car/consultation/carBorrowAdvisoryList";
	}
	
	/**
	 * 车借客户管理列表 
	 * 2016年2月25日
	 * By 陈伟东
	 * @param model
	 * @param CarLoanAdvisoryBacklogEx
	 * @return
	 */
	@RequestMapping(value = "customerManagementList")
	public String customerManagementList(Model model,@ModelAttribute(value = "carLoanAdvisoryBacklogEx") CarLoanAdvisoryBacklogEx CarLoanAdvisoryBacklogEx,HttpServletRequest request,HttpServletResponse response){
		Page<CarLoanAdvisoryBacklogEx> page = new Page<CarLoanAdvisoryBacklogEx>(request, response);
		page = carLoanAdvisoryBacklogService.getCustomerManagementList(page,CarLoanAdvisoryBacklogEx);
		model.addAttribute("page",page);
		return "car/consultation/customerManagementList";
	}
	
}
