package com.creditharmony.loan.car.carExtend.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
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
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carExtend.view.CarExtendUploadDataView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarChangerInfoService;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * 展期上传资料
 * @Class Name Consult
 * @author ganquan
 * @Create In 2016年3月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtend/carExtendUpload")
public class CarExtendUploadDataController extends BaseController {
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	
	@Autowired
	private CarContractService carContractService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	@Autowired
	private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private ProvinceCityManager provinceCityManager;
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	@Autowired
	private CarCustomerService carCustomerService;
	@Autowired
	private CityInfoService cityInfoService;
	@Autowired
	private CarCustomerCompanyService carCustomerCompanyService;
	@Autowired
	private CarCustomerContactPersonService carCustomerContactPersonService;
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
    private CarChangerInfoService carChangerInfoService;
	/**
	 * 展期上传资料
	 * 2016年3月8日 
	 * By 甘泉
	 * @param WorkItemView
	 * @param CarExtendUploadDataView
	 * @return redirect 
	 */
	@RequestMapping(value = "uploadData")
	public String uploadData(WorkItemView workItemView,CarExtendUploadDataView bv){
		//走待初审节点
		workItemView.setResponse(CarLoanResponses.TO_FRIST_AUDIT.getCode());
		bv.setApplyStatusCode(CarLoanStatus.PENDING_FIRST_INSTANCE.getCode());
		workItemView.setBv(bv);
		//获取标红置顶属性内容
		WorkItemView workItem = flowService.loadWorkItemView(bv.getApplyId(), workItemView.getWobNum(), workItemView.getToken());
		workItemView.setFlowProperties(workItem.getFlowProperties());
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carExtendWorkItems/fetchTaskItems/extendReceived";
	}
	/**
	 * 展期上传资料放弃
	 * 2016年3月8日 
	 * By 甘泉
	 * @param WorkItemView
	 * @param CarExtendUploadDataView
	 * @return redirect 
	 */
	@RequestMapping(value = "giveUp")
	public String giveUp(WorkItemView workItemView,CarExtendUploadDataView bv){
		//展期放弃
		workItemView.setResponse(CarLoanResponses.CUSTOMER_GIVE_UP.getCode());
		bv.setApplyStatusCode(CarLoanStatus.EXTENDED_GIVE_UP.getCode());
		workItemView.setBv(bv);
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carExtendWorkItems/fetchTaskItems/extendReceived";
	}
	/**
	 * 展期上传资料退回到评估师录入节点
	 * 2016年3月8日 
	 * By 甘泉
	 * @param CarCustomerBase
	 * @param CarVehicleInfo
	 * @return redirect CarLoanAdvisoryBacklog
	 */
	@RequestMapping(value = "sendBack")
	public String sendBack(WorkItemView workItemView,CarExtendUploadDataView bv){
		//上传资料退回评估师录入
		workItemView.setResponse(CarLoanResponses.BACK_ASSESS_ENTER.getCode());
		bv.setApplyStatusCode(CarLoanStatus.APPRAISER.getCode());
		workItemView.setBv(bv);
		//获取标红置顶属性内容
		WorkItemView workItem = flowService.loadWorkItemView(bv.getApplyId(), workItemView.getWobNum(), workItemView.getToken());
		workItemView.setFlowProperties(workItem.getFlowProperties());
		
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carExtendWorkItems/fetchTaskItems/extendReceived";
	}
	
	/**
	 * 复核——到历史展期信息
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toCarContract")
	public String toCarContract(WorkItemView workItemView,String loanCode,Model model){
		List<CarContract> carContract = carContractService.getExtendContractByLoanCode(loanCode);
		model.addAttribute("carContract", carContract);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItemView", workItemView);
		return "car/carExtend/carExtendApply/extendReview/carLoanFlowCarExtendInfo";
	}
	/**
	 * 复核——到借款信息
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toCarExtendInfo")
	public String toCarExtendInfo(WorkItemView workItemView,String loanCode,Model model){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		CarLoanCoborrower carLoanCoborrower = carLoanCoborrowerService.selectName(loanCode);
		CarApplicationInterviewInfo carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
		String rawLoanCode = null;
		if(carLoanInfo != null){
			rawLoanCode = carLoanInfo.getLoanRawcode();
		}
		//获取管辖城市
		if(carLoanInfo != null){
			String storeCode = carLoanInfo.getStoreCode();
			if(!StringUtils.isEmpty(storeCode)){
				String cityIde = orgService.getOrg(storeCode).getCityId();
				if(null!=cityIde&&!"".equals(cityIde)){
					String cityName = provinceCityManager.get(cityIde).getAreaName();
					model.addAttribute("cityName",cityName );
				}
				
			}
		}
		
		//获得合同编号
		if(StringUtils.isNotEmpty(rawLoanCode)){
			String contractNo = carContractService.getExtendContractNo(rawLoanCode, loanCode, YESNO.NO.getCode());
			model.addAttribute("contractNo", contractNo);
		};
		Org org = ((User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO)).getDepartment();
		String orgType = org != null ? org.getType() : "";
		if(LoanOrgType.TEAM.key.equals(orgType)){
			org = OrgCache.getInstance().get(org.getParentId());
		}
		String departmentId = org.getId();
		List<UserInfo> termManagers = userInfoService.getStoreAllTermManager(departmentId);
		model.addAttribute("termManagers", termManagers);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("carApplicationInterviewInfo", carApplicationInterviewInfo);
		model.addAttribute("carLoanCoborrower", carLoanCoborrower);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItemView", workItemView);
		return "car/carExtend/carExtendApply/extendReview/carExtendLoanFlowInfo"; 
	}
	/**
	 * 复核——到个人资料
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toCarLoanFlowCustomer")
	public String toCarLoanFlowCustomer(WorkItemView workItemView,String provinceId,String cityId,String loanCode,Model model){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		if(carLoanInfo != null){
			String customerCode = carLoanInfo.getCustomerCode();
			if(!StringUtils.isEmpty(customerCode)){
				CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
				model.addAttribute("carCustomerBase", carCustomerBase);
			}
		}
		String oldMobile = "";
		String oldEmail = "";
		CarCustomer carCustomer= carCustomerService.selectByLoanCode(loanCode);
		carCustomer.setCustomerHaveChildren(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomer.getCustomerHaveChildren()));
		carCustomer.setCustomerTempPermit(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomer.getCustomerTempPermit()));
		carCustomer.setCustomerHouseHoldProperty(DictCache.getInstance().getDictLabel("jk_house_nature", carCustomer.getCustomerHouseHoldProperty()));
		model.addAttribute("carCustomer", carCustomer);
		if(null!=carLoanInfo.getLoanAdditionalApplyid()){
			CarLoanInfo info = carLoanInfoService.get(carLoanInfo.getLoanAdditionalApplyid());
			if(null!=info){
				CarCustomer lastcarCustomer= carCustomerService.selectByLoanCodeE(info.getLoanCode());
				if(null==lastcarCustomer){
					lastcarCustomer = carCustomerService.selectByLoanCodeE(info.getLoanRawcode());
				}
				if(null!=lastcarCustomer){
					oldMobile = lastcarCustomer.getCustomerPhoneFirst();
					oldEmail = lastcarCustomer.getCustomerEmail();
				}
			}
		}
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("oldMobile", oldMobile);
		model.addAttribute("oldEmail", oldEmail);
		model.addAttribute("workItemView", workItemView);
		return "car/carExtend/carExtendApply/extendReview/carExtendLoanFlowCustomerInfo"; 
	}
	/**
	 * 复核——到工作信息
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toCarExtendCompany")
	public String toCarExtendCompany(WorkItemView workItemView,Model model,
			String provinceId, String cityId, String loanCode, String newLoanCode){
		CarCustomerCompany customerCompany = carCustomerCompanyService.selectCarCompany(loanCode);
		model.addAttribute("customerCompany", customerCompany);
		//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItemView", workItemView);
		return "car/carExtend/carExtendApply/extendReview/carExtendLoanFlowCompany"; 
	}
	/**
	 * 复核——到联系人信息
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toCarExtendContact")
	public String toCarLoanFlowContact(WorkItemView workItemView,Model model,String provinceId, String cityId, String loanCode){
		List<CarCustomerContactPerson> carCustomerContactPerson = carCustomerContactPersonService.selectCarContactPerson(loanCode);
		model.addAttribute("carCustomerContactPerson", carCustomerContactPerson);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("workItemView", workItemView);
		return "car/carExtend/carExtendApply/extendReview/carExtendLoanFlowContact"; 
	}
	/**
	 * 复核——到共借人信息
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toExtendCoborrower") 
	public String toCarLoanCoborrower(WorkItemView workItemView,Model model,String provinceId, String cityId, String loanCode){
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		String dictLoanCommonRepaymentFlag = carLoanInfo.getDictLoanCommonRepaymentFlag();
		if("1".equals(dictLoanCommonRepaymentFlag) || dictLoanCommonRepaymentFlag == "1"){
			List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
			List<CarCustomerContactPerson> carCustomerContactPersons = null;
			if(null!=carLoanInfo.getLoanAdditionalApplyid()){
				CarLoanInfo info = carLoanInfoService.get(carLoanInfo.getLoanAdditionalApplyid());
				if(null!=info){
					List<CarLoanCoborrower> lastcobos= carLoanCoborrowerService.selectCoborrowerByLoanCode(info.getLoanCode());
					if(null==lastcobos&&lastcobos.size()>0){
						lastcobos= carLoanCoborrowerService.selectCoborrowerByLoanCode(info.getLoanRawcode());
					}
					if(null!=lastcobos&&lastcobos.size()>0){
						for (CarLoanCoborrower cobo : lastcobos) {
							for (CarLoanCoborrower borrower : carLoanCoborrowers) {
								if(borrower.getCoboName().equals(cobo.getCoboName())){
									borrower.setOldEmail(cobo.getEmail());
									borrower.setOldMobile(cobo.getMobile());
								}
							}
						}
					}
				}
			}
			if(carLoanCoborrowers != null && carLoanCoborrowers.size() > 0){
				for (CarLoanCoborrower borrower : carLoanCoborrowers) {
					carCustomerContactPersons = carCustomerContactPersonService.selectByCoborrower(borrower.getId());
					borrower.setCarCustomerContactPerson(carCustomerContactPersons);
				}
			}
			model.addAttribute("carLoanCoborrowers", carLoanCoborrowers);
			//获取省份
			List<CityInfo> provinceList = cityInfoService.findProvince();
			model.addAttribute("provinceList", provinceList);
			List<CityInfo> cityList = cityInfoService.findCity(provinceId);
			model.addAttribute("cityList", cityList);
			List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
			model.addAttribute("districtList", districtList);
			model.addAttribute("loanCode", loanCode);
			model.addAttribute("workItemView", workItemView);
			return "car/carExtend/carExtendApply/extendReview/carExtendLoanFlowCoborrower";
		}else{
			return toCarLoanFlowBank(workItemView,loanCode, model, provinceId, cityId);
		}
		
	}
	/**
	 * 复核——到客户开户信息
	 * 2016年3月12日 
	 * By 甘泉
	 * @param String loanCode
	 * @param Model model
	 * @return url 
	 */
	@RequestMapping(value = "toCarLoanFlowBank")
	public String toCarLoanFlowBank(WorkItemView workItemView,String loanCode,Model model,String provinceId, String cityId){
		CarCustomerBankInfo carCustomerBankInfo =carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
		//获取省份
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode);
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("carCustomer", carCustomer);
		model.addAttribute("loanCode", loanCode);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("carCustomerBankInfo", carCustomerBankInfo);
		model.addAttribute("workItemView", workItemView);
		return "car/carExtend/carExtendApply/extendReview/carExtendLoanFlowBank";
	}
	/**
	 * 复核——保存借款信息
	 * 2016年3月14日 
	 * By 甘泉
	 * @param CarLoanInfo 
	 * @param CarApplicationInterviewInfo
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "borrowSubmit")
	public String borrowSubmit(CarLoanInfo carLoanInfo,CarApplicationInterviewInfo carApplicationInterviewInfo){
		//保存借款信息
		if(!(CarLoanProductType.GPS.getCode().equals(carLoanInfo.getDictProductType())&&Global.YES.equals(carLoanInfo.getDictIsGatherFlowFee()))){
			carLoanInfo.setFlowFee(BigDecimal.ZERO);
		}
		carLoanInfoService.borrowSubmit(carLoanInfo,carApplicationInterviewInfo);
		return "已保存";
	}
	/**
	 * 复核——保存个人资料信息
	 * 2016年3月14日 
	 * By 甘泉
	 * @param CarLoanInfo 
	 * @param CarApplicationInterviewInfo
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "customerSave")
	public String customerSave(CarCustomer carCustomer,CarCustomerBase carCustomerBase){
		carCustomerBase.setIsTelephoneModify("on".equals(carCustomerBase.getIsTelephoneModify())?Global.YES:"");
		carCustomerBase.setIsEmailModify("on".equals(carCustomerBase.getIsEmailModify())?Global.YES:"");
		CarCustomer lastCarCustomer = carCustomerService.selectByLoanCode(carCustomer.getLoanCode());
		//保存客户基本信息
		carCustomer.setCustomerPhoneFirst(carCustomerBase.getCustomerMobilePhone());
		carLoanInfoService.customerSave(carCustomer, carCustomerBase);
		carCustomer.setId(lastCarCustomer.getId());
		if(null!=lastCarCustomer){
			carChangerInfoService.insertCustomer(lastCarCustomer, carCustomer, carCustomerBase, lastCarCustomer.getApplyId());
		}
		//保存客户详细信息
		return "已保存";
	}
	/**
	 * 复核——保存工作信息
	 * 2016年3月14日 
	 * By 甘泉
	 * @param CarLoanInfo 
	 * @param CarApplicationInterviewInfo
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "companySave")
	public String companySave(CarCustomerCompany carCustomerCompany){
		carCustomerCompanyService.update(carCustomerCompany);
		return "已保存";
	}
	/**
	 * 复核——保存联系人信息
	 * 2016年3月14日 
	 * By 甘泉
	 * @param carCreditJson 
	 * @param String loanCode
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "contactSave")
	public String contactSave(carCreditJson creditJson,String loanCode){
		carCustomerContactPersonService.contactSave(creditJson,loanCode);
		return "已保存";
	}
	/**
	 * 复核——保存共借人信息
	 * 2016年3月14日 
	 * By 甘泉
	 * @param carCreditJson 
	 * @param String loanCode
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "coborrowerSave")
	public String coborrowerSave(CreditJson creditJson,String loanCode){
		List<CarLoanCoborrower>cobos = carCustomerContactPersonService.coborrowerSave(creditJson,loanCode);
		if(cobos.size()>0){
			for (CarLoanCoborrower carLoanCoborrower : cobos) {
					CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(carLoanCoborrower.getLoanCode());
					carChangerInfoService.insertCoborrower(carLoanCoborrower, carLoanInfo.getApplyId());
			}
		}
		return "已保存";
	}
	/**
	 * 复核——保存銀行卡信息
	 * 2016年3月14日 
	 * By 甘泉
	 * @param CarLoanInfo 
	 * @param CarApplicationInterviewInfo
	 * @return void
	 */
	@ResponseBody
	@RequestMapping(value = "bankSave")
	public String bankSave(CarCustomerBankInfo carCustomerBankInfo){
		CarCustomerBankInfo carCustomerBI = carCustomerBankInfoService.selectCarCustomerBankInfo(carCustomerBankInfo.getLoanCode());
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (!carCustomerBI.getBankCardNo().equals(carCustomerBankInfo.getBankCardNo())) {
			map.put("bankCardNo", carCustomerBankInfo.getBankCardNo());
		}
		if (!carCustomerBI.getCardBank().equals(carCustomerBankInfo.getCardBank())) {
			map.put("cardBank", carCustomerBankInfo.getCardBank());
		}
		if (!map.isEmpty()) {
			CarLoanInfo caLI = carLoanInfoService.selectByLoanCode(carCustomerBankInfo.getLoanCode());
			flowService.saveDataByApplyId(caLI.getApplyId(), map);
		}
		carCustomerBankInfoService.upadteCarCustomerBankInfo(carCustomerBankInfo);
		return "已保存";
	}
}
