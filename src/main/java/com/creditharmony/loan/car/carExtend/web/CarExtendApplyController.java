package com.creditharmony.loan.car.carExtend.web;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.ApplyIdUtils;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.SerialNoType;
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
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carExtend.view.CarExtendApplyView;
import com.creditharmony.loan.car.common.consts.CarSystemConfigConstant;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarChangerInfoService;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.util.Arith;
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 
 * @Class Name CarApplyTaskController
 * @author 安子帅
 * @Create In 2016年3月7日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtendApply")
public class CarExtendApplyController extends BaseController {
	
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
				
		//客户咨询信息
		@Autowired
		private CarCustomerConsultationService carCustomerConsultationService;
		
		//车借借款信息
		@Autowired
		private CarLoanInfoService carLoanInfoService;
		
		//中间人信息service
		@Autowired
		private MiddlePersonService middlePersonService;
		
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
		
		//银行卡
		@Autowired
		CarCustomerBankInfoService carCustomerBankInfoService;
		
		@Autowired
		private LoanPrdMngService loanPrdMngService;
		
		@Autowired   
		private OrgService orgService;
		
		@Autowired
		private ProvinceCityManager provinceCityManager;
	
		@Autowired
		private  CarContractService  carContractService;
		
		@Autowired
	    private NumberMasterService numberMasterService;
		
		@Autowired
	    private UserInfoService userInfoService;
		
		@Autowired
	    private CarChangerInfoService carChangerInfoService;
		
	@RequestMapping(value = "toCarContract")
	public String toCarContract(String loanCode,Model model,WorkItemView workItemView){
		List<CarContract> carContract = carContractService.getExtendContractByLoanCode(loanCode);
		model.addAttribute("carContract", carContract);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItem", workItemView);
		CarLoanInfo carLoanInfo = carLoanInfoService.selectLoanCodeByLoanRaw(loanCode);
		if (null == carLoanInfo) {
			String newLoanCode = numberMasterService.getLoanNumber(SerialNoType.LOAN);
			model.addAttribute("newLoanCode", newLoanCode);
		}else{
			String newLoanCode = carLoanInfo.getLoanCode();
			model.addAttribute("newLoanCode", newLoanCode);
		}
		System.out.println("---------~~~~~~~~~~+++++++++++%%%%%%%%%%%%%");
		return "car/carExtend/carExtendApply/carLoanFlowCarExtendInfo";
	}
	/**
	 * 查看借款信息
	 * 
	 */
	@RequestMapping(value = "toCarExtendInfo")
	public String toCarLoanFlowInfo(WorkItemView workItemView,CarCustomerConsultation consult,String loanCode,Model model,String newLoanCode,String flag){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		String centerLoanCode = null;
		CarApplicationInterviewInfo carApplicationInterviewInfo = null;
		if (null == carLoanInfo) {
			centerLoanCode = loanCode;
			carLoanInfo = carLoanInfoService.selectByLoanCodeExtend(centerLoanCode);
			carLoanInfo.setCustomerCode(null);
			carLoanInfo.setDictIsGatherFlowFee("2");
			carLoanInfo.setLoanCode(newLoanCode);
			carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
			carApplicationInterviewInfo.setQueryResult(null);
			carLoanInfo.setParkingFee(null);
		}else{
			centerLoanCode = newLoanCode;
			model.addAttribute("lastLastAmount", carLoanInfo.getLoanApplyAmount());
			carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(centerLoanCode);
		}
		
		MiddlePerson middlePerson = new MiddlePerson();
		List<MiddlePerson> middlePersonsList = Lists.newArrayList();
		List<String> name = Lists.newArrayList();
		List<MiddlePerson> middlePersons = middlePersonService.selectMiddlePerson(middlePerson);
		for(MiddlePerson middlePersonN : middlePersons){
			if("夏靖,寇振红".indexOf(middlePersonN.getMiddleName()) > -1 && !name.contains(middlePersonN.getMiddleName())){
				middlePersonsList.add(middlePersonN);
				name.add(middlePersonN.getMiddleName());
			}
		}
		model.addAttribute("middlePersons", middlePersonsList);
		
		CarLoanCoborrower carLoanCoborrower = carLoanCoborrowerService.selectName(centerLoanCode);
		Org org = ((User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO)).getDepartment();
		String orgType = org != null ? org.getType() : "";
		if(LoanOrgType.TEAM.key.equals(orgType)){
			org = OrgCache.getInstance().get(org.getParentId());
		}
		String departmentId = org.getId();
		List<UserInfo> termManagers = userInfoService.getStoreAllTermManager(departmentId);
		model.addAttribute("termManagers", termManagers);
		String storeName = org.getName();
		String storeCode = org.getId();
		model.addAttribute("storeName", storeName);
		model.addAttribute("storeCode", storeCode);
		//获取管辖城市
		if(!StringUtils.isEmpty(departmentId)){
			String cityIde = orgService.getOrg(departmentId).getCityId();
			if(null!=cityIde&&!"".equals(cityIde)){
				String cityName = provinceCityManager.get(cityIde).getAreaName();
				model.addAttribute("cityName",cityName );
			}
			
		}
		String contractNo = "";
		if("1".equals(carLoanInfo.getIsExtension()))
		{
			contractNo = carContractService.getExtendContractNo(carLoanInfo.getLoanRawcode(), newLoanCode, YESNO.NO.getCode());
		}else{
			contractNo = carContractService.getExtendContractNo(loanCode, newLoanCode, YESNO.NO.getCode());
		}
		Double contractAmount = carContractService.getContractAmountByLoanCode(loanCode);
		//查找是否有历史展期放弃的数据,决定合同版本--start
		String sourceLoanInfoVersion = "1.4";
		List<Dict> dlist = DictCache.getInstance().getListByType("jk_car_contract_version");
		Date onLineDate = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE);
		Date onLineDate16 = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE_16);
		if(loanCode!=null && !"".equals(loanCode)){
			CarLoanInfo oldInfo = carLoanInfoService.selectByLoanCode(loanCode);
			if(oldInfo!=null){
				//判断新增数据的合同版本--开始
				String rawLoanCode = oldInfo.getLoanRawcode(); //原借款编码
				if(null==rawLoanCode || rawLoanCode.equals("")){//新增数据，第一次展期没有rawLoanCode
					rawLoanCode = oldInfo.getLoanCode();
				}
				CarLoanInfo sourceLoanInfo = carLoanInfoService.selectByLoanCode(rawLoanCode);//原始借款信息
				Date sourceTime = sourceLoanInfo.getCreateTime();
				if(DateUtils.dateAfter(sourceTime, onLineDate16)){//1.6合同版本
					sourceLoanInfoVersion = dlist.get(dlist.size() - 1).getLabel();//1.6
				}else {
					if (DateUtils.dateAfter(sourceTime, onLineDate)) {
						sourceLoanInfoVersion = dlist.get(dlist.size() - 2).getLabel();//1.5
					}
				}
				//判断新增数据的合同版本--结束
			}
		}
		String contractVersion = CarCommonUtil.getExtendVersionByLoanCode(loanCode, newLoanCode);
		model.addAttribute("sourceLoanInfoVersion", sourceLoanInfoVersion); //新增数据合同版本
		model.addAttribute("contractVersion", contractVersion); //展期合同版本
		//查找是否有历史展期放弃的数据,决定合同版本--end
		//model.addAttribute("contractVersion", CarCommonUtil.getVersionByLoanCode(loanCode));根据上一次数据来确定合同版本
		model.addAttribute("carLoanInfo", carLoanInfo);
		System.err.println("------------------------------------");
		model.addAttribute("carApplicationInterviewInfo", carApplicationInterviewInfo);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("carLoanCoborrower", carLoanCoborrower);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("contractNo", contractNo);
		model.addAttribute("newLoanCode", newLoanCode);
		model.addAttribute("contractAmount", contractAmount);
		return "car/carExtend/carExtendApply/carExtendLoanFlowInfo";
	}
	/**
	 * 保存展期借款信息
	 */
	@RequestMapping(value = "carExtendLoanFlowInfo")
	public String carLoanFlowInfo(WorkItemView workItemView,CarLoanInfo carLoanInfo,Model model,String newLoanCode,
			String provinceId,String cityId,String loanCode,CarApplicationInterviewInfo carApplicationInterviewInfo){
		CarLoanInfo newCarLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		carLoanInfo.setLoanCode(newLoanCode);
		carApplicationInterviewInfo.setLoanCode(newLoanCode);
		if(!(CarLoanProductType.GPS.getCode().equals(carLoanInfo.getDictProductType())&&Global.YES.equals(carLoanInfo.getDictIsGatherFlowFee()))){
			carLoanInfo.setFlowFee(BigDecimal.ZERO);
		}
		if (newCarLoanInfo == null) {
			CarLoanInfo nearestCarLoan = carLoanInfoService.selectByLoanCode(loanCode);
			if (nearestCarLoan.getLoanRawcode() == null) {
				carLoanInfo.setLoanRawcode(loanCode);
			} else {
				carLoanInfo.setLoanRawcode(nearestCarLoan.getLoanRawcode());
			}
			String loanAdditionalApplyid = nearestCarLoan.getId();
			carLoanInfo.setLoanAdditionalApplyid(loanAdditionalApplyid);
			carLoanInfo.setLoanFlag(nearestCarLoan.getLoanFlag()); // 渠道
			carLoanInfo.setIsExtension("1");    //展期标识
			carLoanInfo.setDictLoanStatus("0");  //当展期申请做到一半的时候的借款标识
			carLoanInfo.setLoanCustomerName(CarCommonUtil.replaceSpot(carLoanInfo.getLoanCustomerName()));
			carLoanInfo.setLoanAuthorizer(CarCommonUtil.replaceSpot(carLoanInfo.getLoanAuthorizer()));
			//添加展期的申请时间
			Date date = new Date();
			carLoanInfo.setLoanApplyTime(date);
			carLoanInfo.preInsert();
			carLoanInfo.setCustomerIntoTime(date);
			carLoanInfoService.saveCarLoanInfoAndInterviewInfo(carLoanInfo,carApplicationInterviewInfo);
			//carApplicationInterviewInfoService.saveCarApplicationInterviewInfo(carApplicationInterviewInfo);
		}else {
			carLoanInfo.preUpdate();
			carApplicationInterviewInfo.preUpdate();
			carLoanInfoService.updateCarLoanInfoAndInterviewInfo(carLoanInfo,carApplicationInterviewInfo);
			//carApplicationInterviewInfoService.update(carApplicationInterviewInfo);
		}
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
		getLoanFlowCustomer(workItemView, model, newLoanCode, provinceId, cityId, loanCode);
		return "car/carExtend/carExtendApply/carExtendLoanFlowCustomerInfo";
	}
	/**
	 * 个人资料
	 */
	@RequestMapping(value = "toCarLoanFlowCustomer")
	public String carLoanCustomer(WorkItemView workItemView,String newLoanCode,
			Model model,String provinceId,String cityId,String loanCode){
		getLoanFlowCustomer(workItemView, model, newLoanCode, provinceId, cityId, loanCode);
		System.err.println("------------------------------------");
		return "car/carExtend/carExtendApply/carExtendLoanFlowCustomerInfo";
	}
	private void getLoanFlowCustomer(WorkItemView workItemView, Model model,String newLoanCode,
			String provinceId, String cityId, String loanCode) {
		CarCustomer newCarCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		String centerLoanCode = null;
		boolean isNewExtend = false;
		if (newCarCustomer == null) {
			centerLoanCode = loanCode;
			isNewExtend = true;
		}else{
			centerLoanCode = newLoanCode;
		}
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(centerLoanCode);
		String customerCode = null;
		CarCustomerBase carCustomerBase=null;
		if (carLoanInfo.getCustomerCode() == null) {
			CarLoanInfo carLoanInf = carLoanInfoService.selectByLoanCode(loanCode);
			customerCode = carLoanInf.getCustomerCode();
		}else{
			customerCode = carLoanInfo.getCustomerCode();
		}
		carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
		if(isNewExtend){
			/*int CountCarExtend = carContractService.getExtendCountByLoanCode(loanCode);
			if(CountCarExtend>0){*/
			if (carCustomerBase == null) {
				carCustomerBase = new CarCustomerBase();
			}
			carCustomerBase.setIsEmailModify("0");
			carCustomerBase.setIsTelephoneModify("0");
			/*}*/
		}
		model.addAttribute("carCustomerBase", carCustomerBase);
		CarCustomer carCustomer= carCustomerService.selectByLoanCode(centerLoanCode);
		//字典取中文
		carCustomer.setCustomerHaveChildrenCode(carCustomer.getCustomerHaveChildren());
		List<Dict> dictList = DictCache.getInstance().getList();
		for (Dict dict : dictList) {
			if(null!=dict.getValue()&& "jk_have_or_nohave".equals(dict.getType()) && dict.getValue().equals(carCustomer.getCustomerHaveChildren())){
				carCustomer.setCustomerHaveChildren(dict.getLabel());
			}
			if(null!=dict.getValue()&& "jk_have_or_nohave".equals(dict.getType()) && dict.getValue().equals(carCustomer.getCustomerTempPermit())){
				carCustomer.setCustomerTempPermit(dict.getLabel());
			}
			if(null!=dict.getValue()&& "jk_house_nature".equals(dict.getType()) && dict.getValue().equals(carCustomer.getCustomerHouseHoldProperty())){
				carCustomer.setCustomerHouseHoldProperty(dict.getLabel());
			}
		}
		
		model.addAttribute("carCustomer", carCustomer);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
		
	}
	/**
	 * 添加个人资料
	 */
	@RequestMapping(value = "addCarExtendCustomer")
	public String addCarCustomer(WorkItemView workItemView,CarCustomer carCustomer,String loanCode, Model model,
			String provinceId, String cityId,CarCustomerBase carCustomerBase,
			String applyId,String newLoanCode){
		carCustomer.setLoanCode(newLoanCode);
		CarCustomer newCarCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		carCustomerBase.setIsTelephoneModify("on".equals(carCustomerBase.getIsTelephoneModify())?Global.YES:"");
		carCustomerBase.setIsEmailModify("on".equals(carCustomerBase.getIsEmailModify())?Global.YES:"");
		carCustomer.setCustomerPhoneFirst(carCustomerBase.getCustomerMobilePhone());
		CarCustomer lastCarCustomer = carCustomerService.selectByLoanCode(loanCode);
		CarCustomerConsultation carCons = new CarCustomerConsultation();
		if (newCarCustomer == null) {
			carCustomer.setLoanCode(newLoanCode);
			String customerCode = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER);
			carCustomer.setCustomerCode(customerCode);
			carCustomerBase.setCustomerCode(customerCode);
			carCons.setCustomerCode(customerCode);
			carCons.preInsert();
			carCons.setLoanCode(newLoanCode);
			carCons.setConsTelesalesFlag(lastCarCustomer.getCustomerTelesalesFlag());
			carCustomer.preInsert();
			carCustomerBase.preInsert();
			carCustomer.setCustomerTelesalesFlag(lastCarCustomer.getCustomerTelesalesFlag());
			carCustomerConsultationService.saveCarCustomerConsultation(carCons);
			carCustomerService.saveCarCustomer(carCustomer);
			carCustomerBaseService.saveCarCustomerBase(carCustomerBase);
			CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
			if(null!=carLoanInfo){
				carLoanInfo.setCustomerCode(customerCode);
				carLoanInfoService.updateCarLoanInfo(carLoanInfo);
			}
		} else {
			carCustomerService.update(carCustomer);
			carCustomerBaseService.update(carCustomerBase);
		}
		// 用户信息修改
		carCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		carChangerInfoService.insertCustomer(lastCarCustomer, carCustomer, carCustomerBase, applyId);
		model.addAttribute("newLoanCode", newLoanCode);
		getCarLoanFlowCompany(workItemView, loanCode, model, provinceId, cityId, newLoanCode);
		return "car/carExtend/carExtendApply/carExtendLoanFlowCompany";
	}

	/**
	 * 查看工作信息
	 */
	@RequestMapping(value = "toCarExtendCompany")
	public String toCarLoanFlowCompany(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode, String newLoanCode){
		getCarLoanFlowCompany(workItemView, loanCode, model, provinceId, cityId, newLoanCode);
		return "car/carExtend/carExtendApply/carExtendLoanFlowCompany";
	}
	private void getCarLoanFlowCompany(WorkItemView workItemView,
			String loanCode, Model model, String provinceId, String cityId, String newLoanCode) {
		CarCustomerCompany newCarCustomerCompany = carCustomerCompanyService.selectCarCompany(newLoanCode);
		String centerLoanCode = null;
		if (null == newCarCustomerCompany) {
			centerLoanCode = loanCode;
		}else{
			centerLoanCode = newLoanCode;
		}
		CarCustomerCompany customerCompany = carCustomerCompanyService.selectCarCompany(centerLoanCode);
		model.addAttribute("customerCompany", customerCompany);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
		model.addAttribute("workItem", workItemView);
	}
	/**
	 * 工作信息
	 */
	@RequestMapping(value = "carLoanFlowCompany")
	public String carLoanCompany(WorkItemView workItemView,Model model,String loanCode,CarCustomerCompany carCustomerCompany,
			String provinceId,String cityId,String newLoanCode){
		CarCustomerCompany newCarCustomerCompany = carCustomerCompanyService.selectCarCompany(newLoanCode);
		carCustomerCompany.setLoanCode(newLoanCode);
		if (newCarCustomerCompany == null) {
			carCustomerCompany.preInsert();
			carCustomerCompanyService.saveCarCustomerCompany(carCustomerCompany); 
		}else{
			carCustomerCompanyService.update(carCustomerCompany);
		}
		getCarLoanFlowContact(workItemView, model, loanCode, provinceId, cityId, newLoanCode);
		System.err.println("------------------------------------");
		return "car/carExtend/carExtendApply/carExtendLoanFlowContact";
	}
	/**
	 * 查看联系人
	 */
	@RequestMapping(value = "toCarExtendContact")
	public String toCarLoanFlowContact(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode, String newLoanCode){
		getCarLoanFlowContact(workItemView, model, loanCode, provinceId, cityId, newLoanCode);
		return "car/carExtend/carExtendApply/carExtendLoanFlowContact";
	}
	private void getCarLoanFlowContact(WorkItemView workItemView, Model model,
			String loanCode, String provinceId, String cityId, String newLoanCode) {
		List<CarCustomerContactPerson> newCarCustomerContactPerson = carCustomerContactPersonService.selectCarContactPerson(newLoanCode);
		if (newCarCustomerContactPerson == null || newCarCustomerContactPerson.size() == 0) {
			newCarCustomerContactPerson = carCustomerContactPersonService.selectCarContactPerson(loanCode);
		}
		model.addAttribute("carCustomerContactPerson", newCarCustomerContactPerson);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
	}
	/**
	 * 联系人资料  
	 */
	@ResponseBody
	@RequestMapping(value = "carLoanFlowContact")
	public String carLoanContact(WorkItemView workItem,Model model,carCreditJson creditJson,
			String provinceId,String cityId,String loanCode,String newLoanCode){
		carCustomerContactPersonService.deleteMainContractPerson(newLoanCode);
		for (CarCustomerContactPerson carCustomerContactPerson : creditJson.getCarCustomerContactPerson()) {
			
			carCustomerContactPerson.setIsNewRecord(false);
			carCustomerContactPerson.setLoanCode(newLoanCode);
			carCustomerContactPerson.preInsert();
			carCustomerContactPerson.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
			carCustomerContactPersonService.saveCarCustomerContactPerson(carCustomerContactPerson); 
		}
		System.err.println("------------------------------------");
		return "true";
	}
	/**
	 * 
	 * By 安子帅
	 * 查看共借人信息、共借人联系人信息
	 */
	@RequestMapping(value = "toExtendCoborrower") 
	public String toCarLoanCoborrower(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode, String newLoanCode){
		CarLoanInfo newCarLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		String centerLoanCode = null;
		if (null == newCarLoanInfo) {
			centerLoanCode = loanCode;
		}else{
			centerLoanCode = newLoanCode;
		}
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(centerLoanCode);
		String dictLoanCommonRepaymentFlag = carLoanInfo.getDictLoanCommonRepaymentFlag();
		if ("1".equals(dictLoanCommonRepaymentFlag) || dictLoanCommonRepaymentFlag == "1") {
			getCarLoanCoborrower(workItemView, model, provinceId, cityId, loanCode, newLoanCode);
			return "car/carExtend/carExtendApply/carExtendLoanFlowCoborrower";
		}else{
			return toCarLoanFlowBank(workItemView, newLoanCode, loanCode, model, provinceId, cityId );
		}
	}
	private void getCarLoanCoborrower(WorkItemView workItemView, Model model,
			String provinceId, String cityId, String loanCode, String newLoanCode) {
		List<CarLoanCoborrower> newCarLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(newLoanCode);
		String centerLoanCode = null;
		boolean isNewExtend = false;
		if (newCarLoanCoborrowers == null || newCarLoanCoborrowers.size() == 0) {
			centerLoanCode = loanCode;
			isNewExtend = true;
		}else{
			centerLoanCode = newLoanCode;
		}
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(centerLoanCode);
		if(carLoanCoborrowers!=null && carLoanCoborrowers.size()>0 && carLoanCoborrowers.get(0)!=null){
			List<CarCustomerContactPerson> personList = carCustomerContactPersonService.selectByCoborrower(carLoanCoborrowers.get(0).getId());
			carLoanCoborrowers.get(0).setCarCustomerContactPerson(personList);
			model.addAttribute("personList", personList);
		}
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			if(isNewExtend){
				int CountCarExtend = carContractService.getExtendCountByLoanCode(loanCode);
				if(CountCarExtend>0){
					carLoanCoborrower.setIsemailmodify("0");
					carLoanCoborrower.setIstelephonemodify("0");
				}
			}
		}
		model.addAttribute("carLoanCoborrowers", carLoanCoborrowers);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("provincecount", null!=carLoanCoborrowers?carLoanCoborrowers.size():0);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("newLoanCode", newLoanCode);
	}
	
	/**
	 * 共借人信息、共借人联系人信息
	 */
	@ResponseBody
	@RequestMapping(value = "carExtendCoborrower")
	public String carLoanCoborrower(WorkItemView workItemView,
			Model model,String provinceId,String cityId,CreditJson creditJson){
		List<CarLoanCoborrower> newCobos = carLoanCoborrowerService.carLoanCoborrower(creditJson);
		for (CarLoanCoborrower carLoanCoborrower : newCobos) {
			CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(carLoanCoborrower.getLoanCode());
			if(carLoanInfo != null){
				carChangerInfoService.insertCoborrower(carLoanCoborrower, carLoanInfo.getApplyId());
			}
		}
		System.err.println("------------------------------------");
		return "true";
	}
	

	
	/**
	 * 查看客户开户及管辖信息
	 */
	@RequestMapping(value = "toCarLoanFlowBank")
	public String toCarLoanFlowBank(WorkItemView workItemView,String newLoanCode,
			String loanCode,Model model,String provinceId, String cityId){
		CarCustomerBankInfo newCarCustomerBankInfo =carCustomerBankInfoService.selectCarCustomerBankInfo(newLoanCode);
		String centerLoanCode = null;
		boolean isNewExtend = false;
		if (newCarCustomerBankInfo == null) {
			isNewExtend = true;
			centerLoanCode = loanCode;
		}else{
			centerLoanCode = newLoanCode;
		}
		CarCustomerBankInfo carCustomerBankInfo =carCustomerBankInfoService.selectCarCustomerBankInfo(centerLoanCode);
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(centerLoanCode);
		
		if(isNewExtend){
			int CountCarExtend = carContractService.getExtendCountByLoanCode(loanCode);
			if(CountCarExtend>0){
				carCustomerBankInfo.setIsrare("0");
			}
		}
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		model.addAttribute("workItem", workItemView);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("carCustomerBankInfo", carCustomerBankInfo);
		model.addAttribute("newLoanCode", newLoanCode);
		return "car/carExtend/carExtendApply/carExtendLoanFlowBank";
	}

	/**
	 * 客户开户及管辖信息
	 */
	@RequestMapping(value = "carLoanFlowBank")
	@ResponseBody
	public String carLoanFlowBank(WorkItemView workItemView,CarCustomerBankInfo carCustomerBankInfo,
			String loanCode,String newLoanCode){
		CarCustomerBankInfo newCarCustomerBankInfo =carCustomerBankInfoService.selectCarCustomerBankInfo(newLoanCode);
		CarCustomer newCarCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		if (newCarCustomerBankInfo == null) {
			carCustomerBankInfo.preInsert();
			carCustomerBankInfo.setLoanCode(newLoanCode);
			carCustomerBankInfo.setAccountAuthorizerName(newCarCustomer.getCustomerName());
			carCustomerBankInfoService.saveCarCustomerBankInfo(carCustomerBankInfo);
		}else{
			carCustomerBankInfo.setAccountAuthorizerName(newCarCustomer.getCustomerName());
			carCustomerBankInfoService.upadteCarCustomerBankInfo(newCarCustomerBankInfo);
		}
		String applyId = ApplyIdUtils.builderApplyId(workItemView.getFlowType());
		CarExtendApplyView bv = new CarExtendApplyView();
		CarContract carContract = carContractService.getByLoanCode(loanCode);
		String originalContractCode = carContract.getContractCode();
		// 加入上一次合同編號
		bv.setOriginalContractCode(originalContractCode);
		CarCustomer carCustomer = carCustomerService.selectByLoanCodeE(newLoanCode);
		if(carCustomer==null)
		{
			return "false";
		}
		carCustomer.setApplyId(applyId);
		//carCustomerService.update(carCustomer);
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		carLoanInfo.setDictLoanStatus(CarLoanStatus.APPRAISER.getCode());
		carLoanInfo.setApplyId(applyId);
		carLoanInfo.setLoanFlag(CarLoanThroughFlag.HARMONY.getCode());
		carLoanInfo.setLoanAuthorizer(carCustomer.getCustomerName());
		//获取当前登录人(面审)
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String reviewMeet = user.getName();
		carLoanInfo.setReviewMeet(reviewMeet);
		// 加入 客户经理
		String managerCode = carLoanInfo.getManagerCode();
		if (StringUtils.isNotEmpty(managerCode)) {
			if (UserUtils.get(managerCode) != null) {
				String offendSalesName = UserUtils.get(managerCode).getName();
				bv.setOffendSalesName(offendSalesName);
			}
		}
		carLoanInfoService.updateCarLoanInfoAndCustomer(carLoanInfo,carCustomer);
		String applyStatusCode = carLoanInfo.getDictLoanStatus();
		bv.setApplyStatusCode(applyStatusCode);
		String storeId = carLoanInfo.getStoreCode();
		bv.setStoreId(storeId);
		String extensionFlag = carLoanInfo.getIsExtension();
		bv.setExtensionFlag(extensionFlag);
		// 渠道
		bv.setLoanFlag(CarLoanThroughFlag.HARMONY.getCode());
		String borrowProductCode = carLoanInfo.getDictProductType();
		Integer loanMonths = carLoanInfo.getLoanMonths().intValue();
		//加入借款期限
		bv.setLoanMonths(loanMonths);
		//加入共借人姓名
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(newLoanCode);
		String coborrowerName = "";
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			coborrowerName += ("," + carLoanCoborrower.getCoboName());
		}
		if(!"".equals(coborrowerName)){
			coborrowerName = coborrowerName.substring(1);
		}
		bv.setCoborrowerName(coborrowerName);
		//添加管辖省市信息
		//获取管辖城市
		String storeCode = carLoanInfo.getStoreCode();
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
		//添加是否电销
		CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(newLoanCode);
		if(carCustomerConsultation != null){
			bv.setLoanIsPhone(carCustomerConsultation.getConsTelesalesFlag());
		}
		if(!"".equals(borrowProductCode)){
			bv.setBorrowProductCode(borrowProductCode);
			//向流中添加产品名称
			if(CarLoanProductType.GPS.getCode().equals(borrowProductCode)){
				bv.setBorrowProductName(CarLoanProductType.GPS.getName());
			}
			if(CarLoanProductType.PLEDGE.getCode().equals(borrowProductCode)){
				bv.setBorrowProductName(CarLoanProductType.PLEDGE.getName());
			}
			if(CarLoanProductType.TRANSFER.getCode().equals(borrowProductCode)){
				bv.setBorrowProductName(CarLoanProductType.TRANSFER.getName());
			}
		}
		//加入借款申请日期
		CarLoanInfo rawCarLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		if (rawCarLoanInfo != null) {
			Date loanApplyTime = rawCarLoanInfo.getLoanApplyTime();
			if (loanApplyTime != null) {
				bv.setLoanApplyTime(loanApplyTime);
			}else{
				bv.setLoanApplyTime(new Date());
			}
			BigDecimal loanApplyAmount = rawCarLoanInfo.getLoanApplyAmount();
			if(loanApplyAmount != null){
				bv.setLoanApplyAmount(loanApplyAmount.doubleValue());
			}
		}
		//加人批借金额
		BigDecimal originalAuditAmount = carContract.getContractAmount();
		if(originalAuditAmount != null){
			double auditAmount = originalAuditAmount.doubleValue();
			bv.setOriginalAuditAmount(auditAmount);
		}
		//加入展期合同编号
		String contractNo = carContractService.getExtendContractNo(carLoanInfo.getLoanRawcode(), newLoanCode, YESNO.NO.getCode());
		bv.setContractCode(contractNo);
		//加入车牌号码
		CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		if(carVehicleInfo != null){
			String plateNumbers = carVehicleInfo.getPlateNumbers();
			if(StringUtils.isNotEmpty(plateNumbers)){
				bv.setPlateNumbers(plateNumbers);
			}
		}
		ReflectHandle.copy(carCustomer, bv);
		ReflectHandle.copy(carLoanInfo, bv);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date currDate = sdf.parse(sdf.format(carContract.getContractEndDay()));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currDate);
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			// 添加合同到期提醒日期
			bv.setContractExpirationDate(calendar.getTime());
		} catch (ParseException e) {
			logger.error("设置合同期提醒日期失败",e);
			throw new RuntimeException(e);
		}
		Date date = new Date();
		// 加入进件时间
		bv.setCustomerIntoTime(date);
		// 加入展期次數
		bv.setExtendNumber(carContractService.getExtendContractByLoanCode(newLoanCode).size() - 1);
		workItemView.setBv(bv);
		flowService.launchFlow(workItemView);
		return "true";
	}
	
	/**
	 * 判断此条展期记录汇诚审批金额是否大于3w
	 * 2016年5月26日
	 * By 申诗阔
	 * @param loanCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isAgainExtend")
	public String isAgainExtend(String loanCode) {
		CarLoanInfo carLoanInfo = carLoanInfoService.selectLoanCodeByLoanRaw(loanCode);
		if (null == carLoanInfo) { // 还未开始展期
			CarContract car = carContractService.selectExByLoanCode(loanCode);
			if (car != null && Arith.compareTo(car.getContractAmount().doubleValue(), 30000d) == -1) {
				return BooleanType.FALSE;
			}
		}
		return BooleanType.TRUE;
	}
}
