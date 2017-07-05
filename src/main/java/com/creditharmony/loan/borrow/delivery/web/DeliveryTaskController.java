package com.creditharmony.loan.borrow.delivery.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.service.DeliveryApplyService;
import com.creditharmony.loan.borrow.delivery.service.DeliveryTaskService;
import com.creditharmony.loan.borrow.stores.service.AreaService;
/**
 * 交割已办列表
 * @Class Name DeliveryTaskController
 * @author lirui
 * @Create In 2015年12月7日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/taskDeliveryInfo")
public class DeliveryTaskController extends BaseController{

	@Autowired
	private DeliveryTaskService tds;
	
	@Autowired
	private DeliveryApplyService das;
	
	@Autowired
	private AreaService areaService;
	
	/** 
	 * 查询交割已办列表
	 * 2015年12月14日
	 * By lirui
	 * @param request 获得request
	 * @param response 获得response
	 * @param params 检索参数
	 * @param m Model模型
	 * @return 交割已办列表页面
	 */
	@RequestMapping(value = "queryTaskDelivery")
	public String queryTaskDelivery(HttpServletRequest request,HttpServletResponse response,DeliveryParamsEx params,Model m) {
			String fmt = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			// 如果搜索条件不为空,将条件回显
			if (params!=null) {
				// 如果搜索起始时间不为空,将起始时间转换成字符串另存为startTime,并封装到params中,留在搜索条件中
				if (params.getStartDate() != null) {
					params.setStartTime(sdf.format(params.getStartDate()));
				}
				// 如果搜索结束时间不为空,将结束时间转换成字符串另存为endTime,并封装到params中,留在搜索条件中
				if (params.getEndDate() != null) {
					params.setEndTime(sdf.format(params.getEndDate()));
				}
				m.addAttribute("params", params);
			}
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = OrgCache.getInstance().get(orgId);
			String orgType = org != null ? org.getType() : "";
			boolean isManager =true;
			//如果登录是门店 则门店选择框不可见
			if (LoanOrgType.STORE.key.equals(orgType)){
				isManager=false;
				//如果是门店 则 默认门店框体选项
				params.setStrote(org.getId());
				m.addAttribute("orgNametemp", org.getName());
			}
			m.addAttribute("isManager", isManager);
			// 获得交割已办列表
			Page<DeliveryViewEx> taskPage = tds.taskList(new Page<DeliveryViewEx>(request, response), params);	
			List<DeliveryViewEx> list = taskPage.getList();
			// 缓存取码值
			for (DeliveryViewEx deliveryViewEx : list) {
				deliveryViewEx.setDeliveryResultLabel(DictCache.getInstance().getDictLabel("jk_delivery_result", deliveryViewEx.getDeliveryResult()));
			}
			for (int i = 0; i < taskPage.getList().size(); i++) {
				if (StringUtils.isNotEmpty(taskPage.getList().get(i).getEntrustMan())) {
					String entrustManName = areaService.selectNameByCode(taskPage.getList().get(i).getEntrustMan());
					taskPage.getList().get(i).setEntrustManName(entrustManName);
				}
			}
			// 获得所有门店
			List<OrgView> orgs = das.orgs();
			m.addAttribute("orgs", orgs);
			m.addAttribute("taskPage", taskPage);			
		return "apply/delivery/deliveryTask";
	}
	
}
