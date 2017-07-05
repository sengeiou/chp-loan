package com.creditharmony.loan.car.carConsultation.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.dao.OrgDao;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;

	/**
    * 客户咨询controller
    * 
    * @Class Name CarConsultationController
    * @author 安子帅
    * @Create In 2016年1月22日
    */
@Controller
@Component
@RequestMapping(value = "${adminPath}/car/carConsult")
public class CarConsultationController extends BaseController {

	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	
	@Autowired
    private CarCustomerConsultationService carCustomerConsultationService;
	
	@Autowired
	private CarCustomerService carCustomerService;

	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	
	@Autowired
    private UserInfoService userInfoService;
	
	@Autowired
    private NumberMasterService numberMasterService;
	
	@Autowired
	private CityInfoService cityInfoService;
	
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	@Autowired
	private CarHistoryService carHistoryService;
	@Autowired
	private ProvinceCityManager provinceCityManager;

	private static OrgDao orgDao = SpringContextHolder.getBean(OrgDao.class);

	/**
	 *  2016年1月22日 
	 * By 安子帅
	 * @param carCustomerConsultation
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddCarConsultation")
	public String toAddCarConsultation(Model model,String provinceId,String cityId,CarCustomerConsultation consult,HttpServletRequest request) {
	/*	if(request.getAttribute("message")!=null){
			model.addAttribute("message", request.getAttribute("message").toString());
		}
		*/
		User user = UserUtils.getUser();
		String consTeamEmpName = user.getName();
		consult.setConsTeamEmpName(consTeamEmpName);
		consult.setConsTeamManagerCode(user.getId());
		consult.setConsTelesalesFlag(YESNO.NO.getCode());
		Map<String,String> userMap = new HashMap<String,String>();
        String departmentId = UserUtils.getUser().getDepartment().getId();
        userMap.put("departmentId", departmentId);//部门id
        userMap.put("roleId", LoanRole.FINANCING_MANAGER.id);//团队经理
        List<UserInfo> customerManagers = userInfoService.getRoleUser(userMap);
		model.addAttribute("consult", consult);
		model.addAttribute("customerManagers", customerManagers);
		
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		return "car/consultation/carConsultForm";
	}
	/**
	 * 2016年4月26日
	 * By 李雨
	 * @param model
	 * @param provinceId
	 * @param cityId
	 * @param consult
	 * @return
	 */
	@RequestMapping(value = "toAddCarTelesalesConsultation")
	public String toAddCarTelesalesConsultation(Model model,String provinceId,String cityId,CarCustomerConsultation consult) {
		
		User user = UserUtils.getUser();
		String consTeamEmpName = user.getName();
		consult.setConsTeamEmpName(consTeamEmpName);
		consult.setConsTeamManagerCode(user.getId());
		consult.setConsTelesalesFlag(YESNO.YES.getCode());//电销
		Map<String,String> userMap = new HashMap<String,String>();
        String departmentId = UserUtils.getUser().getDepartment().getId();
        userMap.put("departmentId", departmentId);//部门id
        userMap.put("roleId", LoanRole.FINANCING_MANAGER.id);//团队经理
        List<UserInfo> customerManagers = userInfoService.getRoleUser(userMap);
		model.addAttribute("consult", consult);
		model.addAttribute("customerManagers", customerManagers);
		
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		return "car/consultation/carConsultForm";
	}
	/**
	 * 保存业务数据 2016年1月22日 
	 * By 安子帅
	 * @param carCustomerConsultation
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveCustomerConsult")
	public String saveConsult(CarCustomerConsultation carCustomerConsultation, CarVehicleInfo carVehicleInfo,
			CarCustomer carCustomer, CarCustomerBase carCustomerBase,CarLoanInfo carLoanInfo,HttpServletRequest request) {
/*		
		if("0".equals(carCustomerBase.getDictCertType())){
			String customerCertNum = carCustomerBase.getCustomerCertNum();
			String substring = customerCertNum.substring(customerCertNum.length()-2, customerCertNum.length()-1);
			String customerSex = carCustomerBase.getCustomerSex();
			int sfz=Integer.valueOf(substring).intValue();
	        int sex=Integer.valueOf(customerSex).intValue();
			if(sfz%2!=sex){
				request.setAttribute("message","请重新选择性别！");
				return "car/consultation/carConsultForm";
			}
			
		}*/
		//被转译了临时处理 2016-6-4 何军
		carCustomer.setCustomerName(request.getParameter("customerName"));
		String flag = "false";
		if (flag.equals("false")) {
			//Id 随机生成
			UUID uuid = UUID.randomUUID();
			String id=uuid.toString().replace("-", ""); 
		 	carCustomerConsultation.setId(id);
		 	carLoanInfo.setId(id);
		 	carCustomer.setId(id);
		// 生成客户编码
		 	String customerCode = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER);
			carCustomerBase.setCustomerCode(customerCode);
			carCustomerConsultation.setCustomerCode(customerCode);
			carLoanInfo.setCustomerCode(customerCode);
			carCustomer.setCustomerCode(customerCode);
		//生成借款编号
			String loanCode=numberMasterService.getLoanNumber(SerialNoType.LOAN);
			carLoanInfo.setLoanCode(loanCode);
			carVehicleInfo.setLoanCode(loanCode);
			carCustomerConsultation.setLoanCode(loanCode);
			carCustomer.setLoanCode(loanCode);

			String consTeamManagercode = carCustomerConsultation.getConsTeamManagerCode();
			carCustomerBase.preInsert();
//			carCustomerBaseService.saveCarCustomerBase(carCustomerBase);
			carCustomerConsultation.preInsert();
//			carCustomerConsultationService.saveCarCustomerConsultation(carCustomerConsultation);
			carCustomer.preInsert();
			carCustomer.setCustomerPhoneFirst(carCustomerBase.getCustomerMobilePhone());
			carCustomer.setCustomerTelesalesFlag(carCustomerConsultation.getConsTelesalesFlag());
//			carCustomerService.saveConsult(carCustomer);
			carVehicleInfo.preInsert();
//			carVehicleInfoService.savecarVehicleInfo(carVehicleInfo);
			carLoanInfo.setConsTeamManagercode(consTeamManagercode);
			carLoanInfo.preInsert();
			//向借款信息中插入客户姓名
			carLoanInfo.setLoanCustomerName(carCustomer.getCustomerName());
			// 添加车借信息门店相关
			Org org = UserUtils.getUser().getDepartment();
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.TEAM.key.equals(orgType)){
				org = OrgCache.getInstance().get(org.getParentId());
			}
			String orgCode = org.getId();
			String orgName = org.getName();
			carLoanInfo.setStoreCode(orgCode);
			carLoanInfo.setStoreName(orgName);
			carLoanInfoService.saveCarLoanInfoAndHis(carLoanInfo, carCustomerConsultation, carCustomerBase, carCustomer, carVehicleInfo);
		}
		return flag;
//					"redirect:"
//			+ adminPath
//			+ "/car/carConsult/toAddCarConsultation";
		
		
	}
	/**
	 * 不符合进件条件的90天内不能再咨询提醒 2016年3月21日 
	 * By 甘泉
	 * @param String 
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkInto")
	public String checkInto(String customerName,String customerMobilePhone,String plateNumbers,String dictOperStatus) {
		String check = "true"; 
		if(Global.NO.equals(dictOperStatus)){
			//7天内不允许再次提交
			if(StringUtils.isNotEmpty(plateNumbers)){
				/*if(carVehicleInfoService.selectPlnum(plateNumbers) > 0 ){
					check = "重复";
					return check;
				}*/
				int num = carVehicleInfoService.checkApply(plateNumbers);
				if(num >= 0 ){
					if(num==90){
						check = "90";
					}else if(num==7){
						check = "7";
					}else{
						if(carVehicleInfoService.selectPlnum(plateNumbers) > 0 ){
							check = "重复";
						}
					}
					return check;
				}
				
			}
			//验证客户姓名(拒绝)
			if(StringUtils.isNotEmpty(customerName)){
				if(carCustomerService.validationByName(customerName) > 0){
					check = "姓名";
				}
			}
			//验证客户电话号码(拒绝)
			if(StringUtils.isNotEmpty(customerName)){
				if(carCustomerService.validationByphone(customerMobilePhone) > 0){
					check = "手机号码";
				}
			}
			//验证客户姓名(不符合进件条件)
			if(StringUtils.isNotEmpty(customerName)){
				if(carCustomerService.validationByName2(customerName) > 0){
					check = "客户姓名";
				}
			}
			//验证客户电话号码(不符合进件条件)
			if(StringUtils.isNotEmpty(customerName)){
				if(carCustomerService.validationByphone2(customerMobilePhone) > 0){
					check = "客户手机号码";
				}
			}
		}
		return check;
	}
	
	/**
	 *  咨询已办
	 * By 甘泉
	 * @param applyId
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "carConsultDone")
	public String carConsultDone(String loanCode,Model model){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode); // 借款信息
    	String customerCode = carLoanInfo.getCustomerCode();
    	CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode); // 客户信息
    	CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
    	CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode); // 车辆信息
    	CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(loanCode);
    	//获取客户经理
    	String managerCode =carLoanInfo.getManagerCode();
    	if(StringUtils.isNotEmpty(managerCode)){
    		String managerName = UserUtils.get(managerCode).getName();
    		model.addAttribute("managerName", managerName);			
    	}
    	//获取团队经理
    	String teamManagerCode = carLoanInfo.getConsTeamManagercode();
    	if(StringUtils.isNotEmpty(teamManagerCode)){
    		String teamManagerName = UserUtils.get(teamManagerCode).getName();			
    		model.addAttribute("teamManagerName", teamManagerName);
    	}
    	//获取省市区地址
    	String address = provinceCityManager.getProvinceCity(carCustomer.getCustomerLiveProvince(), 
    			carCustomer.getCustomerLiveCity(), carCustomer.getCustomerLiveArea());
    	if(StringUtils.isNotEmpty(address)){
    		model.addAttribute("address", address);
    		carCustomer.setCustomerAddress(address);
    	}
    	carCustomerBase.setCustomerSex(DictCache.getInstance().getDictLabel("jk_sex", carCustomerBase.getCustomerSex()));
    	carCustomerBase.setDictCertType(DictCache.getInstance().getDictLabel("jk_certificate_type", carCustomerBase.getDictCertType()));
    	model.addAttribute("carLoanInfo", carLoanInfo);
    	model.addAttribute("carCustomer", carCustomer);
    	model.addAttribute("carCustomerBase", carCustomerBase);
    	model.addAttribute("carVehicleInfo", carVehicleInfo);
    	model.addAttribute("carCustomerConsultation", carCustomerConsultation);
		return "car/done/carConsult_done";
	}
	 
}
