package com.creditharmony.loan.car.carApply.web;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.deduct.type.DeductPlatType;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.remote.OrgService;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.ex.CreditJson;
import com.creditharmony.loan.car.carApply.ex.carCreditJson;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerCompanyService;
import com.creditharmony.loan.car.carApply.service.CarCustomerContactPersonService;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carApply.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.car.carApply.view.ReviewMeetApplyBusinessView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carContract.service.CarAuditResultService;
import com.creditharmony.loan.car.common.consts.CarLoanFlowStepName;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.entity.FirstServiceCharge;
import com.creditharmony.loan.car.common.entity.ex.CarLoanCheckTabEx;
import com.creditharmony.loan.car.common.service.CarChangerInfoService;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.service.FirstServiceChargeService;
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.users.service.UserInfoService;
import com.google.common.collect.Lists;
import com.query.ProcessQueryBuilder;

/**
 * 
 * @Class Name CarApplyTaskController
 * @author 安子帅
 * @Create In 2016年2月2日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/car/carApplyTask")
public class CarApplyTaskController extends BaseController {
	
		@Resource(name="appFrame_flowServiceImpl")
		FlowService flowService;
		
		//客户基本信息service
		@Autowired
		private CarCustomerBaseService carCustomerBaseService;
				
		//客户信息service
		@Autowired
		private CarCustomerService carCustomerService;
				
		//车辆详细信息service
		@Autowired
		private CarVehicleInfoService carVehicleInfoService;
		//中间人信息service
		@Autowired
		private MiddlePersonService middlePersonService;
		//客户咨询信息
		@Autowired
		private CarCustomerConsultationService carCustomerConsultationService;
		
		//车借借款信息
		@Autowired
		private CarLoanInfoService carLoanInfoService;
		
		//车借共借人信息
		@Autowired
		private CarLoanCoborrowerService carLoanCoborrowerService;
		
		//车借联系人信息
		@Autowired
		private CarCustomerContactPersonService carCustomerContactPersonService;
		
		//面审信息
		@Autowired
		private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
		
		//城市信息
		@Autowired
		private CityInfoService cityInfoService;
		
		//工作信息
		@Autowired
		private CarCustomerCompanyService carCustomerCompanyService;
		
		//历史记录
		@Autowired
		private CarHistoryService carHistoryService;
		
		@Autowired
		CarCustomerBankInfoService carCustomerBankInfoService;
		
		@Autowired
		private CarAuditResultService carAuditResultService;
		
		@Autowired
		private LoanPrdMngService loanPrdMngService;
		

		@Autowired
		private OrgService orgService;
		
		@Autowired
		private ProvinceCityManager provinceCityManager;
		
		@Autowired
	    private CarChangerInfoService carChangerInfoService;
		
		@Autowired
	    private UserInfoService userInfoService;
		public String YES ="1";
		public String NO ="2";
		
		@Autowired
		private FirstServiceChargeService firstServiceChargeService;
	
	/**
	 * 车借申请待办--申请待办列表
	 */
	
	@RequestMapping(value = "fetchTaskItems")
	public String fetchTaskItems(
			Model model,
			@ModelAttribute(value = "loanFlowQueryParam") CarLoanFlowWorkItemView carLoanFlowWorkItemView,
			HttpServletRequest request, FlowPage page,
			HttpServletResponse response) throws Exception {
		
		ProcessQueryBuilder param = new ProcessQueryBuilder();
		page = new FlowPage();
		
		ReflectHandle.copy(carLoanFlowWorkItemView, param);
		
		param.put("F_StepName@<>", CarLoanFlowStepName.FIRST_AUDIT);
		Org org = ((User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO)).getDepartment();
		String orgType = org != null ? org.getType() : "";
		if(LoanOrgType.TEAM.key.equals(orgType)){
			org = OrgCache.getInstance().get(org.getParentId());
		}
		param.put("storeCode",org.getId());//加入门店筛选
		String queue = "HJ_CAR_FACE_AUDIT";
		
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
		
		// 将退回到该列表的数据 进行置顶标红
		//itemList = backToP(itemList);

		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("productList", productList);
		for(CarLoanFlowWorkItemView view:itemList){
			view.setDictStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", view.getDictStatus()));
			view.setLoanIsPhone(DictCache.getInstance().getDictLabel("jk_telemarketing", view.getLoanIsPhone()));
			view.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", view.getLoanFlag()));
			//判断合同到期提醒日期的真实性（若等于申请日期则设置该值为空）
			Date contractExpirationDate = view.getContractExpirationDate();
			/*
			Date loanApplyTime = view.getLoanApplyTime();
			if(Math.abs((contractExpirationDate.getTime() - loanApplyTime.getTime())/86400000)  <1 ){
				view.setContractExpirationDate(null);
			}
			*/
			//合同到期提醒日期为流程发起初始值，则展示为空
			if (contractExpirationDate != null
					&& (contractExpirationDate.toString().contains("1970") || contractExpirationDate.toString().contains("1906"))) {
				view.setContractExpirationDate(null);
			}
		}
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page);
		/*Map<String,String> userMap = new HashMap<String,String>();
        String departmentId = UserUtils.getUser().getDepartment().getId();
        userMap.put("departmentId", departmentId);//部门id
        userMap.put("roleId", LoanRole.FINANCING_MANAGER.id);//团队经理
		List<UserInfo> customerManagers = userInfoService.getRoleUser(userMap);
		model.addAttribute("customerManagers", customerManagers);*/
		return "car/carApply/carLoanflow_workItems";
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
	
	/**
	 * 个人资料
	 */
	@RequestMapping(value = "carLoanFlowCustomer")
	public String carLoanCustomer(WorkItemView workItemView,
			Model model,String provinceId,String cityId,String loanCode,HttpServletRequest request){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		String customerCode = carLoanInfo.getCustomerCode();
		CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
		CarCustomer carCustomer = carCustomerService.selectByCustomerCode(customerCode);
		//获取省份
		List<CityInfo> provinceList = new ArrayList<CityInfo>();
		List<CityInfo> provList = cityInfoService.findProvince();
		for (CityInfo ci : provList) {
			if ("820000".equals(ci.getAreaCode()) || "810000".equals(ci.getAreaCode())) {
				continue;
			} else {
				provinceList.add(ci);
			}
		}
		
		ReviewMeetApplyBusinessView reviewMeetApplyBusinessView = new ReviewMeetApplyBusinessView();
        reviewMeetApplyBusinessView.setProvinceList(provinceList);
    	
    	ReflectHandle.copy(carCustomer, reviewMeetApplyBusinessView);
    	ReflectHandle.copy(carCustomerBase, reviewMeetApplyBusinessView);
		
    	workItemView.setBv(reviewMeetApplyBusinessView);
    	model.addAttribute("workItem", workItemView);
    	
    	if( request.getAttribute("message")!=null){
    		String message= request.getAttribute("message").toString();
    		model.addAttribute("message",message );
    	}
    	if( request.getAttribute("sex")!=null){
    		String sex= request.getAttribute("sex").toString();
    		model.addAttribute("sex",sex);
    	}
		System.err.println("------------------------------------");
		return "car/carApply/borrowlist/carLoanFlowCustomer";
	}
	/**
	 * 添加个人资料
	 */
	@RequestMapping(value = "addCarLoanFlowCustomer")
	public String addCarCustomer(WorkItemView workItemView,CarCustomer carCustomer,
			CarCustomerBase carCustomerBase,String applyId,Model model,HttpServletRequest request){
		String customerName = request.getParameter("customerName");
		carCustomer.setCustomerName(customerName);
		carCustomerBase.setCustomerName(customerName);
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectByLoanCodeNoConvers(carCustomer.getLoanCode());
		CarCustomer lastCarCustomer = carCustomerService.selectByLoanCode(carCustomer.getLoanCode());
		carCustomer.setCustomerPhoneFirst(carCustomerBase.getCustomerMobilePhone());
		carCustomerService.updateCarCustomerAndBase(carCustomer,carCustomerBase);
		if(carLoanCoborrowers!=null && carLoanCoborrowers.size()>0){
			for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
				if(carCustomerBase.getCustomerCertNum().equals(carLoanCoborrower.getCertNum())){
					String message="主借人的身份证不能和共借人的身份证相同";
					request.setAttribute("message", message);
					//return "car/carApply/borrowlist/carLoanFlowCustomer";
		  	return "forward:"+adminPath+"/car/carApplyTask/carLoanFlowCustomer";
		
				}
				
			}
			
		}
		//身份证和性别
		String customerSex = carCustomerBase.getCustomerSex();
		String certNum=	carCustomerBase.getCustomerCertNum();
		int num=Integer.valueOf(certNum.substring(certNum.length()-2, certNum.length()-1)).intValue();
		int sexNum=Integer.valueOf(customerSex).intValue();
		if(num%2==sexNum ){
			String sex="请重新选择性别";
			request.setAttribute("sex", sex);
			return "forward:"+adminPath+"/car/carApplyTask/carLoanFlowCustomer";
		}
		
		
		//carCustomerBaseService.update(carCustomerBase);
		getCarVehicleInfo(applyId, model,workItemView);
		if(null!=lastCarCustomer){
			if(null!=lastCarCustomer.getCustomerPhoneFirst()&&!lastCarCustomer.getCustomerPhoneFirst().equals(carCustomerBase.getCustomerMobilePhone())){
				carCustomerBase.setIsTelephoneModify("1");
			}
			if(null!=lastCarCustomer.getCustomerEmail()&&!lastCarCustomer.getCustomerEmail().equals(carCustomer.getCustomerEmail())){
				carCustomerBase.setIsEmailModify("1");
			}
		}
		if(null!=lastCarCustomer){
			//carCustomer.setId(lastCarCustomer.getId());
			carChangerInfoService.insertCustomer(lastCarCustomer, carCustomer, carCustomerBase, applyId);
		}
		
		return "car/carApply/borrowlist/carLoanFlowCarVehicleInfo";
		
	}
	/**
	 * 查看车辆信息
	 */
	@RequestMapping(value = "carLoanFlowCarVehicleInfo" )
	public String carLoanCarVehicleInfo(WorkItemView workItemView,String applyId,Model model,String loanCode){
		CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		model.addAttribute("carVehicleInfo", carVehicleInfo);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
		return "car/carApply/borrowlist/carLoanFlowCarVehicleInfo";
	}

	private void getCarVehicleInfo(String applyId, Model model,WorkItemView workItemView) {
		CarLoanInfo carLoanInfo=carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		model.addAttribute("carVehicleInfo", carVehicleInfo);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
	}
	/**
	 * 查看借款信息
	 * 
	 */
	@RequestMapping(value = "toCarLoanFlowInfo")
	public String toCarLoanFlowInfo(WorkItemView workItemView,String loanCode,Model model){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		CarVehicleInfo vehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		CarCustomerBase customerBase = carCustomerBaseService.selectCarCustomerBase(carLoanInfo.getCustomerCode());
		List<CarLoanInfo> historyCarInfos = carLoanInfoService.selectByCertNumAndVehicleNum(customerBase.getCustomerCertNum(), vehicleInfo.getPlateNumbers());
		String addContractVersion = "1.5"; //新增类型的合同版本
		if(historyCarInfos.size()>0){
			addContractVersion = CarCommonUtil.getVersionByLoanCode(historyCarInfos.get(0).getLoanCode());
		}
		
		if(null != carLoanInfo.getParkingFee()){
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
			String parinkFee = df.format(carLoanInfo.getParkingFee());
			BigDecimal bd = new BigDecimal(parinkFee);
			carLoanInfo.setParkingFee(bd);
		}
		MiddlePerson middlePerson = new MiddlePerson();
		List<MiddlePerson> middlePersonsList = Lists.newArrayList();
		List<String> name = Lists.newArrayList();
		List<MiddlePerson> middlePersons = middlePersonService.selectMiddlePerson(middlePerson);
		for(MiddlePerson middlePersonN:middlePersons){
			if("夏靖,寇振红".indexOf(middlePersonN.getMiddleName())>-1 && !name.contains(middlePersonN.getMiddleName())){
				middlePersonsList.add(middlePersonN);
				name.add(middlePersonN.getMiddleName());
			}
		}
		CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode);
    	if (carAuditResult != null) {
    		model.addAttribute("productTypeEditable", YESNO.NO.getCode());
    	} else {
    		model.addAttribute("productTypeEditable", YESNO.YES.getCode());
    	}
		CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		model.addAttribute("carVehicleInfo", carVehicleInfo);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("middlePersons", middlePersonsList);
		System.err.println("------------------------------------");
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
		//获取首期服务费信息
		FirstServiceCharge firstServiceCharge = new FirstServiceCharge();
		firstServiceCharge.setContractVersion(CarCommonUtil.getVersionByLoanCode(loanCode));
		List<FirstServiceCharge> firstServices = firstServiceChargeService.findFirstServiceChargeList(firstServiceCharge);
		model.addAttribute("firstServiceList", jsonMapper.toJson(firstServices)); 
		model.addAttribute("contractVersion", firstServiceCharge.getContractVersion());
		model.addAttribute("addContractVersion", addContractVersion); //新增类型合同版本
		return "car/carApply/borrowlist/carLoanFlowInfo";
	}
	/**
	 * 修改借款信息
	 */
	@RequestMapping(value = "carLoanFlowInfo")
	public String carLoanFlowInfo(WorkItemView workItemView,CarLoanInfo carLoanInfo,Model model,
			String provinceId,String cityId,String loanCode,CarVehicleInfo carVehicleInfo ,HttpServletRequest request){
		String loanCustomerName = request.getParameter("loanCustomerName");
		carLoanInfo.setLoanCustomerName(loanCustomerName);
		carLoanInfo.setLoanAuthorizer(loanCustomerName);
		if(CarLoanProductType.TRANSFER.getCode().equals(carLoanInfo.getDictProductType())||CarLoanProductType.PLEDGE.getCode().equals(carLoanInfo.getDictProductType())){
			carLoanInfo.setFacilityCharge(BigDecimal.ZERO);
			carLoanInfo.setFlowFee(BigDecimal.ZERO);
			carVehicleInfo.setCommericialCompany("");
			carVehicleInfo.setCommericialNum("");
		}else{
			carLoanInfo.setParkingFee(BigDecimal.ZERO);
			if(!(YES.equals(carLoanInfo.getDictSettleRelend()) && NO.equals(carLoanInfo.getDictGpsRemaining()) && YES.equals(carLoanInfo.getDictIsGatherFlowFee()))){
				carLoanInfo.setFlowFee(BigDecimal.ZERO);
			}
		}
		carLoanInfoService.updateCarLoanInfo(carLoanInfo);
		if(null != carVehicleInfo){
			/*CarVehicleInfo cinfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
			carVehicleInfo.setMileage(cinfo.getMileage());
			carVehicleInfo.setCommericialCompany(cinfo.getCommericialCompany());
			carVehicleInfo.setCommericialNum(cinfo.getCommericialNum());*/
			carVehicleInfoService.update(carVehicleInfo);
		}
		String dictLoanCommonRepaymentFlag = carLoanInfo.getDictLoanCommonRepaymentFlag();
		if ("1".equals(dictLoanCommonRepaymentFlag) || dictLoanCommonRepaymentFlag == "1") {
			getCarLoanCoborrower(workItemView, model, provinceId, cityId, loanCode);
			return "car/carApply/borrowlist/carLoanFlowCoborrower";
		}else{
			getCarLoanFlowCompany(workItemView, loanCode, model, provinceId, cityId);
			return "car/carApply/borrowlist/carLoanFlowCompany";
		}
	}
	/**
	 * 
	 * 2016年2月22日
	 * By 安子帅
	 * 查看共借人信息、共借人联系人信息
	 */
	@RequestMapping(value = "toCarLoanCoborrower")
	public String toCarLoanCoborrower(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		String dictLoanCommonRepaymentFlag = carLoanInfo.getDictLoanCommonRepaymentFlag();
		if ("1".equals(dictLoanCommonRepaymentFlag) || dictLoanCommonRepaymentFlag == "1") {
			getCarLoanCoborrower(workItemView, model, provinceId, cityId, loanCode);
			return "car/carApply/borrowlist/carLoanFlowCoborrower";
		}else{
			getCarLoanFlowCompany(workItemView, loanCode, model, provinceId, cityId);
			return "car/carApply/borrowlist/carLoanFlowCompany";
		}
		
	}
	private void getCarLoanCoborrower(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode) {
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectByLoanCodeNoConvers(loanCode);
		List<CarLoanCoborrower> carLoanCoborrowerswithcccp = new ArrayList<CarLoanCoborrower>();
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			if(null != carLoanCoborrower.getOtherIncome()){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
				String parinkFee = df.format(carLoanCoborrower.getOtherIncome());
				BigDecimal bd = new BigDecimal(parinkFee);
				carLoanCoborrower.setOtherIncome(bd);
			}
			if(null != carLoanCoborrower.getHouseRent()){
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
				String parinkFee = df.format(carLoanCoborrower.getHouseRent());
				BigDecimal bd = new BigDecimal(parinkFee);
				carLoanCoborrower.setHouseRent(bd);
			}
			List<CarCustomerContactPerson> cccps = carCustomerContactPersonService.selectByCoborrower(carLoanCoborrower.getId());
			carLoanCoborrower.setCarCustomerContactPerson(cccps);
			carLoanCoborrowerswithcccp.add(carLoanCoborrower);
		}
		model.addAttribute("carLoanCoborrowers", carLoanCoborrowerswithcccp);
		
		//获取省份
		List<CityInfo> provinceList = new ArrayList<CityInfo>();
		List<CityInfo> provList = cityInfoService.findProvince();
		for (CityInfo ci : provList) {
			if ("820000".equals(ci.getAreaCode()) || "810000".equals(ci.getAreaCode())) {
				continue;
			} else {
				provinceList.add(ci);
			}
		}
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItem", workItemView);
//		model.addAttribute("maps", maps);
	}
	
	/**
	 * 共借人信息、共借人联系人信息
	 */
	@ResponseBody
	@RequestMapping(value = "carLoanFlowCoborrower")
	public String carLoanCoborrower(WorkItemView workItemView,
			Model model,String provinceId,String cityId,CreditJson creditJson, String iptLoanCode){
		List<CarLoanCoborrower> carLoanCoborrower2 = creditJson.getCarLoanCoborrower();
		//判断两个共借人的身份号码是否相同
		if(carLoanCoborrower2!=null &&carLoanCoborrower2.size()>1){
	        for(int i=0;i<(carLoanCoborrower2.size()-1);i++){
	        	 if(carLoanCoborrower2.get(i).getCertNum().equals(carLoanCoborrower2.get(i+1).getCertNum())){
	        		 return "x";
	        	 }
	        }
		}
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrower2) {
			if(Global.NO.equals(carLoanCoborrower.getHaveOtherIncome())){
				carLoanCoborrower.setOtherIncome(BigDecimal.ZERO);
			}
			//判断主借人的身份证和共借人的身份证是否相同
			carLoanCoborrower.setCoboName(carLoanCoborrower.getCoboName().replace("&middot;", "·"));
			CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(carLoanCoborrower.getLoanCode());
			String customerCode = carLoanInfo.getCustomerCode();
			CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
			//主借人的身份证号
			String customerCertNum = carCustomerBase.getCustomerCertNum();
			
			String certNum = carLoanCoborrower.getCertNum();
			if(customerCertNum.equals(certNum)){
				return "f";
			}
			if(null != carLoanCoborrower.getId()){
				carLoanCoborrowerService.deleteCoBorrowAndContractPerson(carLoanCoborrower.getId());
			}
		}
		if(null !=creditJson.getCarLoanCoborrower()){
		for (CarLoanCoborrower carLoanCoborrower : creditJson.getCarLoanCoborrower()) {
			String loanCode = carLoanCoborrower.getLoanCode();
			String id = carLoanCoborrower.getId();
			if ("".equals(id) || id == null) {
			UUID uuid = UUID.randomUUID();
			String idg=uuid.toString().replace("-", ""); 
			carLoanCoborrower.setId(idg);
			carLoanCoborrower.setLoanCode(loanCode);
			carLoanCoborrower.preInsert();
			carLoanCoborrowerService.saveCarLoanCoborrower(carLoanCoborrower);//update(carLoanCoborrower);
			List<CarCustomerContactPerson> carCustomerContactPer =  carLoanCoborrower.getCarCustomerContactPerson();
			if(null != carCustomerContactPer){
				for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPer) {
					UUID uuide = UUID.randomUUID();
					String ide=uuide.toString().replace("-", "");
					carCustomerContactPerson.setId(ide);
					carCustomerContactPerson.setrCustomerCoborrowerCode(carLoanCoborrower.getId());
					carCustomerContactPerson.setLoanCode(loanCode);
					carCustomerContactPerson.preInsert();
					carCustomerContactPersonService.saveCarCustomerContactPerson(carCustomerContactPerson);
				}	
			}
		}else{
			carLoanCoborrower.setIstelephonemodify("1");
			carLoanCoborrower.setIsemailmodify("1");
			carLoanCoborrower.preUpdate();
			carLoanCoborrowerService.update(carLoanCoborrower);
			CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(carLoanCoborrower.getLoanCode());
			carChangerInfoService.insertCoborrower(carLoanCoborrower, carLoanInfo.getApplyId());
			String idg = carLoanCoborrower.getId();
			if(null  != carLoanCoborrower.getCarCustomerContactPerson()){
				List<CarCustomerContactPerson> carCustomerContactPerX =  carLoanCoborrower.getCarCustomerContactPerson();
				for (CarCustomerContactPerson carCustomerContactPerson : carCustomerContactPerX) {
					String PersonId = carCustomerContactPerson.getId();
					if ("".equals(PersonId) || PersonId == null) {
						carCustomerContactPerson.setrCustomerCoborrowerCode(idg);
						carCustomerContactPerson.setLoanCode(loanCode);
						carCustomerContactPerson.preInsert();
						carCustomerContactPersonService.saveCarCustomerContactPerson(carCustomerContactPerson);
					}else{
						carCustomerContactPerson.preUpdate();
						carCustomerContactPersonService.update(carCustomerContactPerson);
					}
				}
			}
		}
	}
	}		
		System.err.println("------------------------------------");
		return "t";
	}
	/**
	 * 查看工作信息
	 */
	@RequestMapping(value = "toCarLoanFlowCompany")
	public String toCarLoanFlowCompany(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode){
		getCarLoanFlowCompany(workItemView, loanCode, model, provinceId, cityId);
		return "car/carApply/borrowlist/carLoanFlowCompany";
	}
	private void getCarLoanFlowCompany(WorkItemView workItemView,
			String loanCode, Model model, String provinceId, String cityId) {
		CarCustomerCompany customerCompany = carCustomerCompanyService.selectCarCompany(loanCode);
		model.addAttribute("customerCompany", customerCompany);
		//获取省份
		List<CityInfo> provinceList = new ArrayList<CityInfo>();
		List<CityInfo> provList = cityInfoService.findProvince();
		for (CityInfo ci : provList) {
			if ("820000".equals(ci.getAreaCode()) || "810000".equals(ci.getAreaCode())) {
				continue;
			} else {
				provinceList.add(ci);
			}
		}
		model.addAttribute("provinceList", provinceList);
		if(customerCompany!=null)
		{
			provinceId = customerCompany.getDictCompanyProvince();
			cityId = customerCompany.getDictCompanyCity();
		}
		List<CityInfo> cityList;
		if(provinceId!=null&&!"".equals(provinceId))
		{
			cityList = cityInfoService.findCity(provinceId);
		}else{
			cityList = new ArrayList<CityInfo>();
		}
		
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList;
		if(cityId!=null&&!"".equals(cityId))
		{
			districtList = cityInfoService.findDistrict(cityId);
		}else{
			districtList = new ArrayList<CityInfo>();
		}
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItem", workItemView);
	}
	/**
	 * 工作信息
	 */
	@RequestMapping(value = "carLoanFlowCompany")
	public String carLoanCompany(WorkItemView workItemView,Model model,String loanCode,CarCustomerCompany carCustomerCompany,
			String provinceId,String cityId){
		String id = carCustomerCompany.getId();
		if ("".equals(id) || id == null) {
			//Id 生成
			UUID uuid = UUID.randomUUID();
			id=uuid.toString().replace("-", ""); 
			carCustomerCompany.setId(id);
			carCustomerCompany.setLoanCode(loanCode);
			carCustomerCompany.setDictCustomerType(LoanManFlag.MAIN_LOAN.getCode());
			carCustomerCompanyService.saveCarCustomerCompany(carCustomerCompany); 
		}else {
			carCustomerCompanyService.update(carCustomerCompany);
		}
		getCarLoanFlowContact(workItemView, model, loanCode, provinceId, cityId);
		System.err.println("------------------------------------");
		return "car/carApply/borrowlist/carLoanFlowContact";
	}

	/**
	 * 查看主借人联系人
	 */
	@RequestMapping(value = "toCarLoanFlowContact")
	public String toCarLoanFlowContact(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode){
		getCarLoanFlowContact(workItemView, model, loanCode, provinceId, cityId);
		return "car/carApply/borrowlist/carLoanFlowContact";
	}
	private void getCarLoanFlowContact(WorkItemView workItemView, Model model,
			String loanCode, String provinceId, String cityId) {
		List<CarCustomerContactPerson> carCustomerContactPerson = carCustomerContactPersonService.selectCarContactPerson(loanCode);
		model.addAttribute("carCustomerContactPerson", carCustomerContactPerson);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
	}
	/**
	 * 主借人联系人资料
	 */
	@ResponseBody
	@RequestMapping(value = "carLoanFlowContact")
	public String carLoanContact(WorkItemView workItem,Model model,carCreditJson creditJson,
			String provinceId,String cityId,String loanCode){
		carCustomerContactPersonService.deleteMainContractPerson(loanCode);
		for (CarCustomerContactPerson carCustomerContactPerson : creditJson.getCarCustomerContactPerson()) {
			carCustomerContactPerson.setIsNewRecord(false);
			carCustomerContactPerson.setLoanCode(loanCode);
			carCustomerContactPerson.preInsert();
			carCustomerContactPersonService.saveCarCustomerContactPerson(carCustomerContactPerson); //update(carCustomerContactPerson);
		}
		
		System.err.println("------------------------------------");
		return "true";
	}
	/**
	 * 查看客户开户及管辖信息
	 */
	@RequestMapping(value = "toCarLoanFlowBank")
	public String toCarLoanFlowBank(WorkItemView workItemView,
			String loanCode,Model model,String provinceId, String cityId){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		String customerCode = carLoanInfo.getCustomerCode();
		CarCustomer carCustomer = carCustomerService.selectByCustomerCode(customerCode);
		CarCustomerBankInfo carCustomerBankInfo =carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
		CarApplicationInterviewInfo carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
		//获取客户经理
		String managerCode =carLoanInfo.getManagerCode();
		String managerName ="";
		if(!StringUtils.isEmpty(managerCode))
		{
			if(UserUtils.get(managerCode)!=null)
			{
				managerName = UserUtils.get(managerCode).getName();
			}
		}
		model.addAttribute("managerName", managerName);
		//获取团队经理
		String teamManagerCode = carLoanInfo.getConsTeamManagercode();
		String teamManagerName ="";
		if(!StringUtils.isEmpty(teamManagerCode))
		{
			if(UserUtils.get(teamManagerCode)!=null)
			{
				teamManagerName = UserUtils.get(teamManagerCode).getName();
			}
		}
		model.addAttribute("teamManagerName", teamManagerName);
		//获取当前登录人(面审)
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String reviewMeet = user.getName();
		carLoanInfo.setReviewMeet(reviewMeet);
		model.addAttribute("loanInfo", carLoanInfo);
		//获取管辖城市
		String storeCode = carLoanInfo.getStoreCode();
		if(!StringUtils.isEmpty(storeCode)){
			String cityIde = orgService.getOrg(storeCode).getCityId();
			String cityName ="";
			if(!StringUtils.isEmpty(cityIde)){
				if(provinceCityManager.get(cityIde)!=null){
					cityName = provinceCityManager.get(cityIde).getAreaName();
				}
			}
			model.addAttribute("cityName",cityName );
		}
		model.addAttribute("carLoanInfo", carLoanInfo);
		//获取省份
		List<CityInfo> provinceList = new ArrayList<CityInfo>();
		List<CityInfo> provList = cityInfoService.findProvince();
		for (CityInfo ci : provList) {
			if ("820000".equals(ci.getAreaCode()) || "810000".equals(ci.getAreaCode())) {
				continue;
			} else {
				provinceList.add(ci);
			}
		}
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("carCustomer", carCustomer);
		model.addAttribute("carApplicationInterviewInfo", carApplicationInterviewInfo);
		model.addAttribute("carCustomerBankInfo", carCustomerBankInfo);
		return "car/carApply/borrowlist/carLoanFlowBank";
	}
	/**
	 * 客户开户及管辖信息
	 */
	@RequestMapping(value = "carLoanFlowBank")
	public String carLoanFlowBank(WorkItemView workItemView,ReviewMeetApplyBusinessView bv,CarApplicationInterviewInfo carApplicationInterviewInfo,
			CarCustomer carCustomer,CarCustomerConsultation carCustomerConsultation,CarLoanInfo carLoanInfo,String loanCode,HttpServletRequest request){
		//标红置顶控制
		bv.setBankAccountName(CarCommonUtil.replaceSpot(bv.getBankAccountName()));
		workItemView.setFlowProperties(request.getSession().getAttribute("flowProperties"));
		CarLoanInfo carInfo = carLoanInfoService.selectByLoanCode(loanCode);
		String customerCode = carInfo.getCustomerCode();
		BigDecimal loanMonths = carInfo.getLoanMonths();
		bv.setLoanMonths(loanMonths.intValue());
		String borrowProductCode = carInfo.getDictProductType();
		bv.setBorrowProductCode(borrowProductCode);
		String cycleBorrowingFlag = carInfo.getDictSettleRelendName();
		bv.setCycleBorrowingFlag(cycleBorrowingFlag);
		bv.setLoanCode(loanCode);
	
	
		//获取管辖省份
		String storeCode = carInfo.getStoreCode();
		if(!StringUtils.isEmpty(storeCode)){
			String provinceId = orgService.getOrg(storeCode).getProvinceId();
			String cityId = orgService.getOrg(storeCode).getCityId();
			if(!StringUtils.isEmpty(provinceId)){
				String provinceName = provinceCityManager.get(provinceId).getAreaName();
				bv.setAddrProvince(provinceName);
			}
			if(!StringUtils.isEmpty(cityId)){
				String cityName = provinceCityManager.get(cityId).getAreaName();
				bv.setAddrCity(cityName);
			}
		}
		
//		carCustomer.setCustomerCode(customerCode);
//		//保存客户来源
//		carCustomerService.update(carCustomer);
		//保存面审信息
//		CarApplicationInterviewInfo carApplicationInterviewInfo2 = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
//		if (carApplicationInterviewInfo2 == null) {
//			carApplicationInterviewInfo.setLoanCode(loanCode);
//			carApplicationInterviewInfo.preInsert();
//			carApplicationInterviewInfoService.saveCarApplicationInterviewInfo(carApplicationInterviewInfo);
//		}else{
//			carApplicationInterviewInfo.preUpdate();
//			carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
//		}
		
		//客户咨询  下一步状态
		carCustomerConsultation.setCustomerCode(customerCode);
		carCustomerConsultationService.updateCarCustomerConsultationAndCarLoanInfo(carCustomerConsultation,carLoanInfo);
		//carLoanInfoService.updateCarLoanInfo(carLoanInfo);
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
		String coboNames = "";
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			String coborrowerName = carLoanCoborrower.getCoboName();
			coboNames += ("," + coborrowerName);
		}
		if (!"".equals(coboNames)) {
			coboNames = coboNames.substring(1);
		}
		bv.setCoborrowerName(coboNames);
		bv.setExtensionFlag("0");
		String firstCheckName = carLoanInfo.getReviewMeet();
		bv.setFirstCheckName(firstCheckName);
		ReflectHandle.copy(carLoanInfo, bv);
		
//      工作流中ReviewMeetApplyEx.java 已做添加
//		CarCustomerBankInfo bankInfo=carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
//		
//		if(bankInfo==null){
//			bankInfo=new CarCustomerBankInfo();
//			ReflectHandle.copy(bv, bankInfo);
//			bankInfo.preInsert();
//			carCustomerBankInfoService.saveCarCustomerBankInfo(bankInfo);
//		}else{
//			ReflectHandle.copy(bv, bankInfo);
//			carCustomerBankInfoService.upadteCarCustomerBankInfo(bankInfo);
//		}

		
		System.err.println("------------------------------------");
		workItemView.setResponse(CarLoanResponses.TO_UPLOAD_FILE.getCode());
		workItemView.setBv(bv);
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carApplyTask/fetchTaskItems";
	}
	
	@RequestMapping(value = "giveUp")
	public String giveUp(WorkItemView workItemView,String loanCode,CarLoanInfo carLoanInfo){
		//走客户放弃
		workItemView.setResponse(CarLoanResponses.CUSTOMER_GIVE_UP.getCode());
		ReviewMeetApplyBusinessView bv = new ReviewMeetApplyBusinessView();
		bv.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		bv.setDictOperStatus(NextStep.CUSTOMER_GIVEUP.getCode());
		bv.setLoanCode(loanCode);
		workItemView.setBv(bv);
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carApplyTask/fetchTaskItems";
	}
	/**
	 * 检查每个tab页中是否有未填写的必填信息
	 * 2016年4月22日
	 * By 高远
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@ResponseBody
	@RequestMapping(value = "checkAllTab")
	public String checkAllTab(CarApplicationInterviewInfo carApplicationInterviewInfo,CarCustomer carCustomer,ReviewMeetApplyBusinessView bv) throws Exception {
		
		CarCustomerBankInfo carCustomerBankInfo=new CarCustomerBankInfo();
		bv.setBankAccountName(CarCommonUtil.replaceSpot(bv.getBankAccountName()));
		ReflectHandle.copy(bv, carCustomerBankInfo);
		carCustomerBankInfo.setIsrare("on".equals(bv.getIsrare())?Global.YES:"");
		CarCustomerBankInfo carCustomerBankInfoM = carCustomerBankInfoService.selectCarCustomerBankInfo(bv.getLoanCode());
		if (carCustomerBankInfoM == null) {
			carCustomerBankInfo.setIsNewRecord(false);
			carCustomerBankInfo.preInsert();
			carCustomerBankInfo.setTop("1");
			carCustomerBankInfo.setBankSigningPlatform(DeductPlatType.HYL.getCode());//默认签约平台好易联
			carCustomerBankInfoService.saveCarCustomerBankInfo(carCustomerBankInfo);
		}else{
			carCustomerBankInfoService.upadteCarCustomerBankInfo(carCustomerBankInfo);
		}
		
		CarApplicationInterviewInfo carApplicationInterviewInfo2 = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(bv.getLoanCode());
		if (carApplicationInterviewInfo2 == null) {
			carApplicationInterviewInfo.setLoanCode(bv.getLoanCode());
			carApplicationInterviewInfo.preInsert();
			carApplicationInterviewInfoService.saveCarApplicationInterviewInfo(carApplicationInterviewInfo);
		}else{
			carApplicationInterviewInfo.preUpdate();
			carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
		}
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(bv.getLoanCode());
		String customerCode = carLoanInfo.getCustomerCode();
		carCustomer.setCustomerCode(customerCode);
		//保存客户来源
		carCustomerService.update(carCustomer);
		String str = "";
		List<String> person = Arrays.asList("customerName","customerAddress","customerSex","dictMarryStatus",
				"customerPhoneFirst","dictEducation","customerCertNum","idStartDate","customerTempPermit",
				"customerRegisterProvince","customerRegisterCity","customerRegisterArea","customerRegisterAddress",
				"customerLiveProvince","customerLiveCity","customerLiveArea","customerHouseHoldProperty",
				"customerFirtArriveYear","customerFamilySupport","customerEmail","creditLine");
		List<String> loanInfo = Arrays.asList("loanApplyAmount","loanMonths","loanApplyTime","dictLoanCommonRepaymentFlag",
				"dictProductType","dictLoanUse","mortgagee","loanAuthorizer","dictSettleRelend");
		CarLoanCheckTabEx carLoancheck = carLoanInfoService.checkAllTab(bv.getLoanCode());
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(
				carLoancheck.getClass()).getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				if (readMethod != null) {
					Object result = readMethod.invoke(carLoancheck,
							new Object[0]);
					if (person.contains(propertyName) && result == null) {
						str = "个人信息有必填项未填写";
						break;
					} else if (loanInfo.contains(propertyName)
							&& result == null) {
						str = "借款信息有必填项未填写";
						break;
					} else if (propertyName.equals("ccId") && (result == null || "".equals(result))) {
						str = "工作信息未填写";
						break;
					} else if (String.valueOf(propertyName ).equals("cpCount") 
							&& Integer.parseInt(result.toString()) < 3) {
						str = "联系人未填写或联系人个数少于3";
						break;
					} else if (String.valueOf(propertyName).equals("clcCount")) {
						if ("1".equals(carLoancheck
								.getDictLoanCommonRepaymentFlag())
								&& Integer.parseInt(result.toString()) < 1) {
							str = "共借人未填写";
							break;
						}
					}
				}
			}
		}
		return str;
	}
	/**
	 * 将退回的数据 进行置顶标红
	 * 2016年3月15日
	 * @param srcList
	 * By 张振强
	 * @return
	 */
	@Deprecated
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
	
	@ResponseBody
	@RequestMapping(value = "checkDealUser")
	public String checkDealUser(String applyId) {
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		if(null != carLoanInfo && null != user ){
			if(StringUtils.isEmpty(carLoanInfo.getDealUser())){
				carLoanInfoService.updateCarLoanInfoDealUser(user.getId(), carLoanInfo.getLoanCode());
				return BooleanType.TRUE;
			}
			if(user.getId().equals(carLoanInfo.getDealUser())){
				return BooleanType.TRUE;
			}else{
				return carLoanInfo.getDealUser();
			}
		}
		return BooleanType.FALSE;
	}
}
