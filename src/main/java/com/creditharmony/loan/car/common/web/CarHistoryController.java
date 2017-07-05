/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webHistoryController.java
 * @Create By 朱静越
 * @Create In 2015年12月1日 下午2:39:02
 */
/**
 * @Class Name HistoryController
 * @author 朱静越
 * @Create In 2015年12月1日
 */
package com.creditharmony.loan.car.common.web;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.Reflections;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.dao.OrgDao;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carGrant.ex.CarDisCardEx;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.ex.CarLoanGrantHaveEx;
import com.creditharmony.loan.car.common.entity.ex.CarLoanStatusHisEx;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.query.ProcessQueryBuilder;

@Controller
@Component
@RequestMapping(value="${adminPath}/common/carHistory")
/**
 * 查询借款历史功能控制层
 * @author 张进
 *	
 */
public class CarHistoryController extends BaseController{
	@Autowired
	private CarHistoryService historyService;
	@Autowired
	private MiddlePersonService middlePersonService;
	
	@Autowired
	private CarCustomerService carCustomerService;
	
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	
	private static OrgDao orgDao = SpringContextHolder.getBean(OrgDao.class);
	private Double extensionFee;
	
	/**
	 * 根据LoanCode查找借款历史信息
	 * 2016年1月25日
	 * By 张进
	 * @param request request对象
	 * @param response response对象
	 * @param m Model对象，前后台数据传输载体
	 * @param loanStatusHis 查询条件，获取LoanCode查询
	 * @return
	 */
	@RequestMapping(value="showLoanHisByLoanCode")
	public String showLoanHisByLoanCode(HttpServletRequest request,
				HttpServletResponse response,Model m,CarLoanStatusHis loanStatusHis){
			Page<CarLoanStatusHis> page = new Page<CarLoanStatusHis>(request, response);
			//数据库查询列表数据
			CarLoanInfo info = carLoanInfoService.selectByLoanCode(loanStatusHis.getLoanCode());
			if(null!=info.getDictsourcetype()&&"3".equals(info.getDictsourcetype())){
				page=historyService.findCarLoanStatusHisList(page, loanStatusHis);
			}else{
				page=historyService.findOldCarLoanStatusHisList(page, loanStatusHis);
			}
			//传递数据到前台页面展示
			m.addAttribute("info", loanStatusHis);
			m.addAttribute("page", page);
			return "/car/carHistory";
	  }
	@RequestMapping(value="showLoanHisByApplyId")
	public String showLoanHisByApplyId(HttpServletRequest request,
				HttpServletResponse response,Model m,CarLoanStatusHis loanStatusHis){
			Page<CarLoanStatusHis> page = new Page<CarLoanStatusHis>(request, response);
			//数据库查询列表数据
			CarLoanInfo info = carLoanInfoService.selectByApplyId(loanStatusHis.getApplyId());
			loanStatusHis.setLoanCode(info.getLoanCode());
			if(null!=info.getDictsourcetype()&&"3".equals(info.getDictsourcetype())){
				loanStatusHis.setApplyId(null);
				page=historyService.findCarLoanStatusHisList(page, loanStatusHis);
			}else{
				page=historyService.findOldCarLoanStatusHisList(page, loanStatusHis);
			}
			//传递数据到前台页面展示
			m.addAttribute("info", loanStatusHis);
			m.addAttribute("page", page);
			return "/car/carHistory";
	  }
	
	
	/**
	 * 获取申请已办列表,step 表示 xxxDoneList 中的 xxx，返回jsp页面名为 xxxDoneList.jsp
	 * 2016年2月26日
	 * By 陈伟东
	 * @param request
	 * @param response
	 * @param carLoanStatusHisEx
	 * @param model
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="{step:[a-zA-Z]+}DoneList")
	public String doneList(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("carLoanStatusHisEx")CarLoanStatusHisEx carLoanStatusHisEx,Model model, @PathVariable String step) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String storeCodes = carLoanStatusHisEx.getStoreCode();
		String[] storeCode = null;
		List<String> stroeCodeList = new ArrayList<String>();
		if(storeCodes != null){
			if(storeCodes.indexOf(",")!=-1){
				storeCode = storeCodes.split(",");
			} else{
				storeCode = new String[1];
				storeCode[0] = storeCodes;
			}
			for (String c : storeCode) {
				stroeCodeList.add(c);
			}
			carLoanStatusHisEx.setStoreCodeList(stroeCodeList);
		}
		// 若设置carLoanStatusHisEx.setFilterUser(UserUtils.getUser().getId())，则表示值查询个人已办，否则为所有人的已办
		
		// 是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
		String isQueryAll = null;
		
		// in前是否加not（仅限操作节点查询前），为1，则不加，其他，则在in前加not
		String isIn = null;
		
		// 节点 列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限操作节点查询前），列出要查询或者要摒弃的节点code值，见CarLoanSteps枚举
		String nodeValListStr = null;

		// 是否只筛选通过的记录，为1，则是，其他，则否
		String filterGross = null;
		
		if ("carLoanApply".equals(step)) {
			carLoanStatusHisEx.setFilterUser(UserUtils.getUser().getId()); // 设置个人已办
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.NO.getCode();
			// 去除咨询已办
			nodeValListStr = CarLoanSteps.CONSULTATION.getCode( ) + "," + CarLoanSteps.APPRAISER.getCode();
		} else if ("contractPro".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CONTRACT_PRODUCT.getCode();
			filterGross = YESNO.YES.getCode();
		} else if ("carLoanData".equals(step)) {
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				//String storeCode1 = org != null ?  org.getStoreCode() : "";
//				isQueryAll = YESNO.NO.getCode();
				String orgStoreId = org.getId();
				if(LoanOrgType.TEAM.key.equals(orgType)){
					orgStoreId = org.getParentId();
				}
				carLoanStatusHisEx.setStoreCode(orgStoreId);
			}else{
				isQueryAll = YESNO.YES.getCode();
			}
		} else if ("carLoanSuccess".equals(step)){
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.GRANT_AUDIT.getCode();
			carLoanStatusHisEx.setGrantRecepicResult("0");
		} else if ("draw".equals(step)) {//划扣已办查询条件
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CURRENT_DEDUCTS.getCode()+","
					+CarLoanSteps.PAST_DEDUCTS.getCode()+","
					+CarLoanSteps.STORE_DEDUCTS.getCode();
		} else if ("check".equals(step)) {//查账已办查询条件
			isQueryAll = YESNO.NO.getCode();//是否查询全部
			isIn = YESNO.YES.getCode();//in前是否加not
			nodeValListStr = CarLoanSteps.DEDUCTS_MATCHING.getCode();
		}else if ("carRefundCheck".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.REFUND_AUDIT.getCode();
		} else if ("customerManagement".equals(step)) {
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				//String storeCode1 = org != null ?  org.getStoreCode() : "";
				String orgStoreId = org.getId();
				if(LoanOrgType.TEAM.key.equals(orgType)){
					orgStoreId = org.getParentId();
				}
				carLoanStatusHisEx.setStoreCode(orgStoreId);
			}
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CONSULTATION.getCode()+","
					+CarLoanSteps.APPRAISER.getCode()+","
					+CarLoanSteps.FACE_AUDIT_APPLY.getCode();
			
		} else if ("appraiserEntry".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.APPRAISER.getCode();
		}
		
		
		Page<CarLoanStatusHisEx> page;
		Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
		
		if ("carLoanData".equals(step))
		{
			page = historyService.findLoanDataList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		}else if("check".equals(step)){
			page = historyService.findCheckDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		}else if("draw".equals(step)){
			page = historyService.findDrawDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		}
		else{
			page = historyService.findDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		}
		
		if(page.getList() != null){
			List<CarLoanStatusHisEx> itemList = page.getList();
			Map<String, String> map = new HashMap<String, String>();
			map.put("dictInitiate", "com_use_flag");
			map.put("dictOperStatus", "jk_next_step");
			map.put("telesalesFlag", "jk_telemarketing");
			map.put("loanStatusCode", "jk_car_loan_status");
			map.put("dictDealStatus", "jk_counteroffer_result");
			map.put("auditStatus", "car_refund_status");
			map.put("midBankName", "jk_open_bank");
			map.put("dictDealType", "jk_deduct_plat");  
			map.put("conditionalThroughFlag", "jk_car_loan_result");
			map.put("loanFlag", "jk_car_throuth_flag");
			map.put("visitState", "jk_car_revisit_type");
			for (Map.Entry<String, String> entry : map.entrySet()) {
				for (CarLoanStatusHisEx listItem : itemList) {
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
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		model.addAttribute("carLoanStatusHisEx", carLoanStatusHisEx);
		
		double amount = 0;
		long count = 0;
		if("draw".equals(step)){
			if(page.getList() != null && page.getList().size() > 0){
				if(page.getList().get(0).getTotalUrgeDecuteAmount() != null){
					amount = page.getList().get(0).getTotalUrgeDecuteAmount();
				}
				count = page.getCount();
			}
			model.addAttribute("amount", amount);				
			model.addAttribute("count", count);
			
		}
		if("carLoanSuccess".equals(step)){
			List<CarLoanGrantHaveEx> list = historyService.findDoneListForXls(new Page<CarLoanGrantHaveEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, null);
			if(list != null && list.size() > 0){
				for (CarLoanGrantHaveEx carLoanItem :list) {
					amount+=carLoanItem.getFinalAuditAmount();
				}
				count = page.getCount();
			}
			model.addAttribute("amount", amount);				
			model.addAttribute("count", count);
			
		}
		//增加主借人、共借人的邮箱
		/*if("contractPro".equals(step)){
			if(page.getList() != null && page.getList().size() > 0){
				for (CarLoanStatusHisEx listItem : page.getList()) {
					CarCustomer customer = carCustomerService.selectByLoanCode(listItem.getLoanCode());
					if(null!=customer){
						listItem.setCustomerEmail(customer.getCustomerEmail());
					}
					List<CarLoanCoborrower> cobos = carLoanCoborrowerService.selectByLoanCodeNoConvers(listItem.getLoanCode());
					if(cobos.size()>0){
						String email="";
						for (CarLoanCoborrower cobo : cobos) {
							email+=cobo.getEmail();
						}
						listItem.setCoboEmail(email);
					}
				}
			}
		}*/
		
		return "/car/done/" + step + "DoneList";
	}
	
	/**
	 * 测试用的方法
	 * 2015年12月9日
	 * By 朱静越
	 * @return
	 */
	@RequestMapping(value="insert")
	public String insertHistory(){
//		historyService.saveCarLoanStatusHis("CJ20160122", "测试添加", "成功", "这里是备注信息");
		return "redirect:"+adminPath+"/common/carHistory/showLoanHisByLoanCode?loanCode=CJ20160122";
	}
	
	/**
	 * 获取车借展期已办列表,step 表示 xxxExtendDoneList 中的 xxx，返回jsp页面名为 xxxExtendDoneList.jsp
	 * 2016年3月11日
	 * By 申诗阔
	 * @param request
	 * @param response
	 * @param carLoanStatusHisEx
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="{step:[a-zA-Z]+}DoneListExtend")
	public String doneExtendList(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("carLoanStatusHisEx")CarLoanStatusHisEx carLoanStatusHisEx,Model model, @PathVariable String step) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, Exception{
		
		// 若设置carLoanStatusHisEx.setFilterUser(UserUtils.getUser().getId())，则表示值查询个人已办，否则为所有人的已办
		
		// 是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
		String isQueryAll = null;
		
		// in前是否加not（仅限操作节点查询前），为1，则不加，其他，则在in前加not
		String isIn = null;
		
		// 节点 列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限操作节点查询前），列出要查询或者要摒弃的节点code值，见CarLoanSteps枚举
		String nodeValListStr = null;

		// 是否只筛选通过的记录，为1，则是，其他，则否
		String filterGross = null;
		
		if ("carLoanApply".equals(step)) {
//			carLoanStatusHisEx.setFilterUser(UserUtils.getUser().getId()); // 设置个人已办
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.NO.getCode();
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				//String storeCode1 = org != null ?  org.getStoreCode() : "";
				String orgStoreId = org.getId();
				if(LoanOrgType.TEAM.key.equals(orgType)){
					orgStoreId = org.getParentId();
				}
				carLoanStatusHisEx.setStoreCode(orgStoreId);
			}
			// 去除咨询已办
			nodeValListStr = CarLoanSteps.CONSULTATION.getCode();
		} else if ("contractPro".equals(step)) {
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.YES.getCode();
			nodeValListStr = CarLoanSteps.CONTRACT_PRODUCT.getCode();
			filterGross = YESNO.YES.getCode();
		}
		Page<CarLoanStatusHisEx> page = null;
		if("contractPro".equals(step)){
			 page = historyService.findExtendContractDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		}else{
			 page = historyService.findExtendDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
		}
		
		
		if(null != page.getList()){
			List<CarLoanStatusHisEx> itemList = page.getList();
			Map<String, String> map = new HashMap<String, String>();
			map.put("isExtendsion", "yes_no");
			map.put("loanStatusCode", "jk_car_loan_status");
			map.put("telesalesFlag", "jk_telemarketing");
			map.put("loanFlag", "jk_car_throuth_flag");
			for (Map.Entry<String, String> entry : map.entrySet()) {
				for (CarLoanStatusHisEx listItem : itemList) {
					Double flowFee = listItem.getFlowFee()==null?0:listItem.getFlowFee();
					Double parkingFee = listItem.getParkingFee()==null?0:listItem.getParkingFee();
					Double derate = listItem.getDerate()==null?0:listItem.getDerate().doubleValue();
					Double extensionFee = listItem.getExtensionFee()==null?0:listItem.getExtensionFee();
					if("CJ01".equals(listItem.getProductTypeContract())){
						if(flowFee != null){
							listItem.setExtendRepayMoney(flowFee + extensionFee + derate );
						} else {
							listItem.setExtendRepayMoney(extensionFee + derate);
						}
					} else if ("CJ02".equals(listItem.getProductTypeContract())|| "CJ03".equals(listItem.getProductTypeContract())){
						listItem.setExtendRepayMoney(extensionFee + parkingFee);
					}
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
			}
		}
		//增加主借人、共借人的邮箱
		if("contractPro".equals(step)){
			if(page.getList() != null && page.getList().size() > 0){
				for (CarLoanStatusHisEx listItem : page.getList()) {
					CarCustomer customer = carCustomerService.selectByLoanCode(listItem.getLoanCode());
					if(null!=customer){
						listItem.setCustomerEmail(customer.getCustomerEmail());
					}
					List<CarLoanCoborrower> cobos = carLoanCoborrowerService.selectByLoanCodeNoConvers(listItem.getLoanCode());
					if(cobos.size()>0){
						String email="";
						for (CarLoanCoborrower cobo : cobos) {
							email+=cobo.getEmail();
						}
						listItem.setCoboEmail(email);
					}
				}
			}
		}
		model.addAttribute("page", page);    
		return "/car/carExtend/carExtendDone/carExtend" + step + "DoneList";
	}
	
	/**
	 * 展期已办列表数据导出
	 * @param request
	 * @param response
	 * @param carLoanStatusHisEx
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/exportzqExcel")
	public void exportzqExcel(HttpServletRequest request,
			HttpServletResponse response,CarLoanStatusHisEx carLoanStatusHisEx,Model model){
		try {
			// 是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
			String isQueryAll = null;
			// in前是否加not（仅限操作节点查询前），为1，则不加，其他，则在in前加not
			String isIn = null;
			// 节点 列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限操作节点查询前），列出要查询或者要摒弃的节点code值，见CarLoanSteps枚举
			String nodeValListStr = null;
			// 是否只筛选通过的记录，为1，则是，其他，则否
			String filterGross = null;
			isQueryAll = YESNO.NO.getCode();
			isIn = YESNO.NO.getCode();
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				//String storeCode1 = org != null ?  org.getStoreCode() : "";
				String orgStoreId = org.getId();
				if(LoanOrgType.TEAM.key.equals(orgType)){
					orgStoreId = org.getParentId();
				}
				carLoanStatusHisEx.setStoreCode(orgStoreId);
			}
			// 去除咨询已办
			nodeValListStr = CarLoanSteps.CONSULTATION.getCode();
			Page<CarLoanStatusHisEx> page = null;
		    page = historyService.findExtendDoneList(new Page<CarLoanStatusHisEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, filterGross);
			ExcelUtils excelutil = new ExcelUtils();
			excelutil.exportExcel(page.getList(),FileExtension.ZQYB_XLS,null,CarLoanStatusHisEx.class,FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 车借申请列表
	 * 申请电子协议
	 * 显示客户基本信息
	 */
	@ResponseBody
	@RequestMapping(value = "/showCutomerMsg")
	public CarLoanStatusHisEx showCutomerMsg(Model model, CarLoanStatusHisEx info){
		CarLoanStatusHisEx customerMsg = historyService.getCustomerMsg(info);
		model.addAttribute("customerMsg", customerMsg);
		return customerMsg;
	}

	/**
	 * 车借申请列表
	 * 申请电子协议
	 * 修改协议状态
	 */
	@RequestMapping(value = "/updateContractArgType")
	public String updateContractArgType(Model model, CarLoanStatusHisEx info){
		info.setPaperLessFlag(CarLoanSteps.FIRST_AUDIT.getCode());
		historyService.updateContractArgType(info);
		return "redirect:" + adminPath + "/common/carHistory/carLoanApplyDoneListExtend";
	}
	

	/**
	 * 电子协议已办列表
	 * @param req
	 * @param model
	 * @param CarLoanStatusHisEx
	 * @return
	 */
	@RequestMapping(value = "/applyAgreementHandleList")
	public String applyAgreementHandleList(HttpServletRequest request, HttpServletResponse response,
				Model model, CarLoanStatusHisEx ex){
		ex.setAgreementType(CarLoanSteps.RECHECK_AUDIT.getCode());
		Page<CarLoanStatusHisEx> eleList = historyService.getEleAgreementList(
				new Page<CarLoanStatusHisEx>(request, response), ex);
//		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		for (CarLoanStatusHisEx e : eleList.getList()) {
			e.setDictLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.CAR_LOAN_STATUS, e.getDictLoanStatus()));
		}
		model.addAttribute("eleList", eleList);
		model.addAttribute("ex", ex);
		return "car/done/eleAgreeHandleList";
	}
	
	/**
	 * 电子协议申请列表
	 * @param req
	 * @param model
	 * @param CarLoanStatusHisEx
	 * @return
	 */
	@RequestMapping(value = "/applyAgreementList")
	public String applyAgreementList(HttpServletRequest request, HttpServletResponse response,
				Model model, CarLoanStatusHisEx ex){
		ex.setAgreementType(CarLoanSteps.FIRST_AUDIT.getCode());
		Page<CarLoanStatusHisEx> eleList = historyService.getEleAgreementList(
				new Page<CarLoanStatusHisEx>(request, response), ex);
//		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		for (CarLoanStatusHisEx e : eleList.getList()) {
			e.setDictLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.CAR_LOAN_STATUS, e.getDictLoanStatus()));
		}
		model.addAttribute("eleList", eleList);
		model.addAttribute("ex", ex);
		return "car/done/eleAgreeList";
	}

	/**
	 * 电子协议申请列表
	 * 发送/退回
	 */
	@RequestMapping(value = "/updateSendOrReturn")
	public String updateSendOrReturn(Model model, HttpServletRequest request,CarLoanStatusHisEx info){
		historyService.updateSendOrReturn(info);
		return "redirect:" + adminPath + "/common/carHistory/applyAgreementList";
	}

	/**
	 * 显示电子协议维护历史
	 */
	@RequestMapping(value = "/showContractAgrHistory")
	public String showContractAgrHistory(Model model, CarLoanStatusHisEx ex){
		List<CarLoanStatusHisEx> list = historyService.selectActAgrLogList(ex.getContractCode());
		model.addAttribute("list", list);
		return "car/done//showContractAgrHistory";
	}
}