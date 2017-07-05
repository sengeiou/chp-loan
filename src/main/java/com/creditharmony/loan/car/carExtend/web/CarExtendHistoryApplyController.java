package com.creditharmony.loan.car.carExtend.web;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.ApplyIdUtils;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
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
import com.creditharmony.loan.car.carExtend.service.CarExtendHistoryApplyService;
import com.creditharmony.loan.car.carExtend.view.CarExtendApplyView;
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
import com.creditharmony.loan.car.common.entity.ex.CarLoanContractEx;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.common.utils.FullHalfWidth;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * 
 * @Class Name CarApplyTaskController
 * @author 安子帅
 * @Create In 2016年3月7日          
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtendHistory")
public class CarExtendHistoryApplyController extends BaseController {

	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	
	//客户基本信息service
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	
	//客户基本信息service
	@Autowired
	private CarExtendHistoryApplyService carExtendHistoryApplyService;
	
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
	private  CarContractService carContractService;
	
	@Autowired
    private NumberMasterService numberMasterService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	/**
	 * 查询合同
	 * 2016年3月28日
	 * By 安子帅
	 * 
	 * @param model
	 * @param contractCode
	 * @param loanCode 原车借loanCode
	 * @param newLoanCode 新loanCode
	 */
	@RequestMapping(value = "queryHistoryExtend")
	public String queryHistory(Model model, String contractCode, String loanCode, String newLoanCode){
		List<CarContract> carContractLists = null;
		if (StringUtils.isNotEmpty(loanCode)) {
			contractCode = carContractService.getByLoanCode(loanCode).getContractCode();
		} else {
			contractCode = FullHalfWidth.half2FullBrackets(contractCode); // 将输入的合同编号里面的 括号 半角转全角
			CarContract carContract = carContractService.selectByContractCode(contractCode);
			if (carContract != null) {
				loanCode = carContract.getLoanCode();
			}
		}
		CarLoanInfo li = carLoanInfoService.selectExtendHistoryByLoanRaw(loanCode);
		if (li != null) {
			newLoanCode = li.getLoanCode();
		}
		carContractLists = carContractService.getExtendByContractCode(contractCode);
		List<CarContract> carContracts = new ArrayList<CarContract>();
		int extendCount = 0; // 加上车借，共已展期次数
		int sumCount = 0; // 共可展期次数
		if (carContractLists != null && carContractLists.size() > 0) {
			extendCount = carContractLists.size();
			loanCode = carContractLists.get(0).getLoanCode();
			int month = carContractLists.get(0).getContractMonths().intValue();
			if (month == 30) {
				sumCount = 5;
			} else if (month == 90) {
				sumCount = 3;
			}
			for (CarContract carContract : carContractLists) { // 把合同编号用-切开，用于回显。
				String conCode = carContract.getContractCode();
				int numIndex = conCode.lastIndexOf("-");
				if (numIndex < 0) {
					carContract.setNumCount(0 + "");
				} else {
					carContract.setNumCount(conCode.substring(numIndex + 1));
				}
				carContract.setContractCode(contractCode);
				carContracts.add(carContract);
			}
		} else {
			sumCount = 5;
		}
		model.addAttribute("newLoanCode", newLoanCode);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("sumCount", sumCount);
		model.addAttribute("extendCount", extendCount);
		model.addAttribute("carContracts", carContracts);
		model.addAttribute("contractCode", contractCode);
		return "car/carExtend/carExtendHistory/carHistoryFlowCarExtendInfo";
	}
	/**
	 * 保存合同，loanCode和newLoanCode在此生成
	 * 2016年3月28日
	 * By 安子帅
	 * @param model
	 * @param carLoanContractEx
	 * @param loanCode
	 * @param newLoanCode
	 */
	@ResponseBody
	@RequestMapping(value = "carExtendContractInfo")
	public Map<String, String> toCarContract(Model model, CarLoanContractEx carLoanContractEx, String loanCode, String newLoanCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("flag", "false");
		if (carLoanContractEx.getCarContract() != null && carLoanContractEx.getCarContract().size() > 0) { // 保存或修改合同信息
			Map<String, String> resultMap = carExtendHistoryApplyService.saveExtendHistoryContracts(carLoanContractEx, loanCode, newLoanCode);
			loanCode = resultMap.get("loanCode");
			newLoanCode = resultMap.get("newLoanCode");
		}
		
		map.put("flag", "true");
		map.put("loanCode", loanCode);
		map.put("newLoanCode", newLoanCode);
		
		return map;
	}
	/**
	 * 查看借款信息
	 * bor
	 */
	@RequestMapping(value = "toCarExtendInfo")
	public String toCarLoanFlowInfo(Model model, String loanCode, String newLoanCode){
		String contractCode = carContractService.getExtendContractNo(loanCode, newLoanCode, YESNO.NO.getCode());
		model.addAttribute("contractCode", contractCode);
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		Org org = user.getDepartment();
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
				model.addAttribute("cityName", cityName);
			}
			
		}
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		CarLoanInfo car = carLoanInfo;
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		model.addAttribute("carCustomer", carCustomer);
		if (carLoanInfo.getLoanApplyAmount() == null) {
			carLoanInfo = carLoanInfoService.getRichCarLoanInfo(loanCode, newLoanCode);
			if (carLoanInfo != null) {
				carLoanInfo.setLoanApplyAmount(null);
			}
		}
		CarContract carContract = carContractService.getByLoanCode(carLoanInfoService.get(car.getLoanAdditionalApplyid()).getLoanCode());
		BigDecimal auditAmount= carContract.getContractAmount();
		CarApplicationInterviewInfo carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(newLoanCode);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("carApplicationInterviewInfo", carApplicationInterviewInfo);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
		model.addAttribute("auditAmount", auditAmount);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowInfo";
	}
	/**
	 * 修改借款信息
	 */
	@RequestMapping(value = "carExtendLoanFlowInfo")
	public String carLoanFlowInfo(Model model, CarLoanInfo carLoanInfo,
			String provinceId, String cityId, String loanCode, String newLoanCode, CarApplicationInterviewInfo carApplicationInterviewInfo){
		Date d = carLoanInfo.getLoanApplyTime();
		if (d == null) {
			d = new Date();
			carLoanInfo.setLoanApplyTime(d);
		}
		carApplicationInterviewInfo.setLoanCode(newLoanCode);
		carLoanInfo.setLoanCode(newLoanCode);
		carExtendHistoryApplyService.saveExtendHistoryLoanInfo(carLoanInfo, newLoanCode, carApplicationInterviewInfo);
		getLoanFlowCustomer(model, provinceId, cityId, loanCode, newLoanCode);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowCustomerInfo";
	}
	/**
	 * 个人资料
	 */
	@RequestMapping(value = "toCarLoanFlowCustomer")
	public String carLoanCustomer(Model model,String provinceId,String cityId, String loanCode, String newLoanCode){
		getLoanFlowCustomer(model, provinceId, cityId, loanCode, newLoanCode);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowCustomerInfo";
	}
	private void getLoanFlowCustomer(Model model, String provinceId, String cityId, String loanCode, String newLoanCode) {
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		String customerCode = carLoanInfo.getCustomerCode();
		CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
		model.addAttribute("carCustomerBase", carCustomerBase);
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		if (carCustomer == null) {
			carCustomer = carCustomerService.selectByLoanCode(loanCode);
		}
		model.addAttribute("carCustomer", carCustomer);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
	}
	/**
	 * 添加个人资料
	 */
	@RequestMapping(value = "addCarExtendCustomer")
	public String addCarCustomer(Model model, CarCustomer carCustomer, String loanCode, String newLoanCode, CarLoanInfo carLoanInfo,
			String provinceId, String cityId, CarCustomerBase carCustomerBase,
			CarCustomerConsultation carCustomerConsultation) {
		carCustomer.setLoanCode(newLoanCode);
		carLoanInfo.setLoanCode(newLoanCode);
		carCustomerBase.setCustomerMobilePhone(carCustomer.getCustomerPhoneFirst());
		carExtendHistoryApplyService.saveExtendHistoryCarCustomer(carLoanInfo, loanCode, newLoanCode, carCustomer, carCustomerBase);;
		getCarLoanFlowCompany(model, loanCode, newLoanCode, provinceId, cityId);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowCompany";
	}
	/**
	 * 查看工作信息
	 */
	@RequestMapping(value = "toCarExtendCompany")
	public String toCarLoanFlowCompany(Model model, String provinceId, String cityId, String loanCode, String newLoanCode){
		getCarLoanFlowCompany(model, loanCode, newLoanCode, provinceId, cityId);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowCompany";
	}
	private void getCarLoanFlowCompany(Model model, String loanCode, String newLoanCode, String provinceId, String cityId) {
		CarCustomerCompany customerCompany = carCustomerCompanyService.selectCarCompany(newLoanCode);
		if (customerCompany == null) {
			customerCompany = carCustomerCompanyService.selectCarCompany(loanCode);
		}
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
	}
	/**
	 * 工作信息
	 */
	@RequestMapping(value = "carLoanFlowCompany")
	public String carLoanCompany(Model model, String loanCode, String newLoanCode, CarCustomerCompany carCustomerCompany,
			String provinceId, String cityId){
		carCustomerCompany.setLoanCode(newLoanCode);
		if (carCustomerCompanyService.selectCarCompany(newLoanCode) == null) {
			carCustomerCompany.preInsert();
			carCustomerCompanyService.saveCarCustomerCompany(carCustomerCompany); 
		}else{
			carCustomerCompanyService.update(carCustomerCompany);
		}
		getCarLoanFlowContact(model, loanCode, newLoanCode, provinceId, cityId);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowContact";
	}

	/**
	 * 查看联系人
	 */
	@RequestMapping(value = "toCarExtendContact")
	public String toCarLoanFlowContact(Model model, String provinceId, String cityId, String loanCode, String newLoanCode){
		getCarLoanFlowContact(model, loanCode, newLoanCode, provinceId, cityId);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowContact";
	}
	private void getCarLoanFlowContact(Model model, String loanCode, String newLoanCode, String provinceId, String cityId) {
		List<CarCustomerContactPerson> carCustomerContactPerson = carCustomerContactPersonService.selectCarContactPerson(newLoanCode);
		if (carCustomerContactPerson == null  || carCustomerContactPerson.size() == 0) {
			carCustomerContactPerson = carCustomerContactPersonService.selectCarContactPerson(loanCode);
		}
		model.addAttribute("carCustomerContactPerson", carCustomerContactPerson);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
	}
	/**
	 * 联系人资料
	 */
	@ResponseBody
	@RequestMapping(value = "carLoanFlowContact")
	public String carLoanContact(Model model, carCreditJson creditJson, String provinceId, String cityId, String loanCode, String newLoanCode){
		carExtendHistoryApplyService.saveExtendHistoryContact(creditJson, newLoanCode);
		return "true";
	}
	/**
	 * 
	 * 查看共借人信息、共借人联系人信息
	 */
	@RequestMapping(value = "toExtendCoborrower") 
	public String toCarLoanCoborrower(Model model, String provinceId, String cityId, String loanCode, String newLoanCode){
		String newF = carLoanInfoService.selectByLoanCode(newLoanCode).getDictLoanCommonRepaymentFlag();
		String dictLoanCommonRepaymentFlag = null;
		if (StringUtils.isEmpty(newF)) {
			CarLoanInfo carLoan = carLoanInfoService.getRichCarLoanInfo(loanCode, newLoanCode);
			if (carLoan != null) {
				dictLoanCommonRepaymentFlag = carLoan.getDictLoanCommonRepaymentFlag();
			}
		}else{
			dictLoanCommonRepaymentFlag = newF;
		}
		if ("1".equals(dictLoanCommonRepaymentFlag)) {
			getCarLoanCoborrower(model, provinceId, cityId, loanCode, newLoanCode);
			return "car/carExtend/carExtendHistory/carExtendHistoryFlowCoborrower";
		}else{
			return toCarLoanFlowBank(model, loanCode, newLoanCode, provinceId, cityId);
		}
	}
	private void getCarLoanCoborrower(Model model, String provinceId, String cityId, String loanCode, String newLoanCode) {
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(newLoanCode);
		if (carLoanCoborrowers == null || carLoanCoborrowers.size() == 0) {
			carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
		}
		List<CarLoanCoborrower> carLoanCoborrowerswithcccp = new ArrayList<CarLoanCoborrower>();
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			List<CarCustomerContactPerson> cccps = carCustomerContactPersonService.selectByCoborrower(carLoanCoborrower.getId());
			carLoanCoborrower.setCarCustomerContactPerson(cccps);
			carLoanCoborrowerswithcccp.add(carLoanCoborrower);
		}
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode);
		if(carCustomer == null){
			carCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		}
		model.addAttribute("carLoanCoborrowers", carLoanCoborrowerswithcccp);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("carCustomer", carCustomer);
		model.addAttribute("newLoanCode", newLoanCode);
	}
	
	/**
	 * 根据loancode删除共借人
	 * @param loanCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="deleteCoboByLoanCode")
	public String deleteCoboByLoanCode (String loanCode){
		carLoanCoborrowerService.deleteCoboByLoanCode(loanCode);
		return "true";
	}
	
	/**
	 * 共借人信息、共借人联系人信息
	 */
	@ResponseBody
	@RequestMapping(value = "carExtendCoborrower")
	public String carLoanCoborrower(Model model, String provinceId, String cityId, CreditJson creditJson){
		List<CarLoanCoborrower> carLoanCoborrower2 = creditJson.getCarLoanCoborrower();
		carExtendHistoryApplyService.saveExtendHistoryCoborrower(creditJson);
		return "t";
	}
		
	/**
	 * 查看客户开户及管辖信息
	 */
	@RequestMapping(value = "toCarLoanFlowBank")
	public String toCarLoanFlowBank(Model model, String loanCode, String newLoanCode, String provinceId, String cityId){
		CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(newLoanCode);
		if (carCustomerBankInfo == null) {
			carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
			if (carCustomerBankInfo != null) {
				carCustomerBankInfo.setIsrare(null);
			}
		}
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		if (carCustomer == null) {
			carCustomer = carCustomerService.selectByLoanCode(loanCode);
		}
		model.addAttribute("carCustomer", carCustomer);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("newLoanCode", newLoanCode);
		model.addAttribute("carCustomerBankInfo", carCustomerBankInfo);
		return "car/carExtend/carExtendHistory/carExtendHistoryFlowBank";
	}

	/**
	 * 客户开户及管辖信息
	 */
	@RequestMapping(value = "carLoanFlowBank")
	public String carLoanFlowBank(CarCustomerBankInfo carCustomerBankInfo, String loanCode, String newLoanCode){
		WorkItemView workItemView = new WorkItemView();
		String applyId = ApplyIdUtils.builderApplyId("HJ0004");
		CarCustomer newCarCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		if (carCustomerBankInfoService.selectCarCustomerBankInfo(newLoanCode) == null) {
			carCustomerBankInfo.setAccountAuthorizerName(newCarCustomer.getCustomerName());
			carCustomerBankInfo.setLoanCode(newLoanCode);
			carCustomerBankInfo.preInsert();
			carCustomerBankInfoService.saveCarCustomerBankInfo(carCustomerBankInfo);
		} else {
			carCustomerBankInfo.preUpdate();
			carCustomerBankInfo.setAccountAuthorizerName(newCarCustomer.getCustomerName());
			carCustomerBankInfoService.upadteCarCustomerBankInfo(carCustomerBankInfo);
		}
		CarExtendApplyView bv = new CarExtendApplyView();
		
		
		CarLoanInfo ca = carLoanInfoService.getRichCarLoanInfo(loanCode, newLoanCode);
		String carNum = "";
		String originalContractCode = null;
		if (ca != null) {
			CarVehicleInfo vi = carVehicleInfoService.selectCarVehicleInfo(ca.getLoanCode());
			if (vi != null) {
				carNum = vi.getPlateNumbers();
			}
			originalContractCode = carContractService.getByLoanCode(ca.getLoanCode()).getContractCode();
		} else {
			originalContractCode = carContractService.getByLoanCode(loanCode).getContractCode();
		}
		// 插入车牌号码
		bv.setPlateNumbers(carNum);
		// 插入上一次合同编号
		bv.setOriginalContractCode(originalContractCode);
		
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
		carLoanInfo.setLoanFlag(CarLoanThroughFlag.HARMONY.getCode());
		carLoanInfo.setApplyId(applyId);
		carLoanInfo.setDictLoanStatus(CarLoanStatus.APPRAISER.getCode());
		carLoanInfoService.updateCarLoanInfo(carLoanInfo);
		
		CarContract carContract = carContractService.getByLoanCode(carLoanInfoService.get(carLoanInfo.getLoanAdditionalApplyid()).getLoanCode());
		// 插入合同到期提醒
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
		String storeCode = carLoanInfo.getStoreCode();
		if(StringUtils.isNotEmpty(storeCode)){
			String provinceId = orgService.getOrg(storeCode).getProvinceId();
			String cityId = orgService.getOrg(storeCode).getCityId();
			if(StringUtils.isNotEmpty(provinceId)){
				String provinceName = provinceCityManager.get(provinceId).getAreaName();
				bv.setAddrProvince(provinceName);
			}
			if(StringUtils.isNotEmpty(cityId)){
				String cityName = provinceCityManager.get(cityId).getAreaName();
				bv.setAddrCity(cityName);
			}
		}
		
		String contractCode = carContractService.getExtendContractNo(loanCode, newLoanCode, YESNO.NO.getCode());
		// 插入本次合同编号
		bv.setContractCode(contractCode);
		// 插入共借人
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(newLoanCode);
		String coboNames = "";
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			String coborrowerName = carLoanCoborrower.getCoboName();
			coboNames += ("," + coborrowerName);
		}
		if (!"".equals(coboNames)) {
			coboNames = coboNames.substring(1);
		}
		bv.setCoborrowerName(coboNames);
		// 插入渠道
		bv.setLoanFlag(CarLoanThroughFlag.HARMONY.getCode());
		// 插入借款状态
		bv.setApplyStatusCode(CarLoanStatus.APPRAISER.getCode());
		String storeId = carLoanInfo.getStoreCode();
		// 插入门店Id
		bv.setStoreId(storeId);
		String extensionFlag = carLoanInfo.getIsExtension();
		// 插入展期标识
		bv.setExtensionFlag(extensionFlag);
		String borrowProductCode = carLoanInfo.getDictProductType();
		// 插入产品类型
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
		int extendNum = carContractService.getExtendContractByLoanCode(newLoanCode).size();
		// 插入展期次数
		bv.setExtendNumber(extendNum - 1);
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(newLoanCode);
		ReflectHandle.copy(carCustomer, bv);
		ReflectHandle.copy(carLoanInfo, bv);
		CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(newLoanCode);
		if(carCustomerConsultation != null){
			bv.setLoanIsPhone(carCustomerConsultation.getConsTelesalesFlag());
		}
		// 插入申请金额
		bv.setLoanApplyAmount(carLoanInfo.getLoanApplyAmount().doubleValue());
		// 插入借款期限
		bv.setLoanMonths(Integer.parseInt(carLoanInfo.getLoanMonths().toString()));
		workItemView.setFlowCode("loanExtendFlow");
		workItemView.setStepName("@launch");
		workItemView.setFlowName("车借展期流程");
		// 插入applyId
		bv.setApplyId(applyId);
		workItemView.setBv(bv);
		flowService.launchFlow(workItemView);
		return "forward:" + adminPath + "/car/carContract/firstDefer/selectDeferList";
	}
	
	@ResponseBody
	@RequestMapping(value = "checkExtendHistoryEntry")
	public String checkExtendHistoryEntry(String contractCode){
		String flag = BooleanType.FALSE;
		CarLoanInfo ca = carLoanInfoService.checkExtendHistoryEntry(contractCode);
		if (ca == null) {
			flag = BooleanType.TRUE;
		}
		return flag;
	}
}