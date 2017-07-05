package com.creditharmony.loan.car.carApply.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.carApply.ex.CarOcrEx;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.car.carConsultation.ex.CarLoanAdvisoryBacklogEx;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanAdvisoryBacklogService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
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
import com.query.ProcessQueryBuilder;


/**
 * 
 * @Class Name CarOcrController
 * @author gezhichao	
 * @Create In 2016年8月22日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carOcr")
public class CarOcrController {

	//搜索车借咨询信息service
	@Autowired
    private CarLoanAdvisoryBacklogService carLoanAdvisoryBacklogService;
	
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
			
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	
	//获取银行账户信息
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;  
	
	@Autowired
	private CityInfoService cityInfoService;
	
	@Autowired
    private NumberMasterService numberMasterService;
	
	@Autowired
    private UserInfoService userInfoService;
	/**
	 * 车借申请待办--申请待办列表
	 */
	
	@RequestMapping(value = "fetchTaskItems")
	public String FetchTaskItems(Model model,@ModelAttribute(value = "carLoanAdvisoryBacklogEx") CarLoanAdvisoryBacklogEx CarLoanAdvisoryBacklogEx,
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
		page=carLoanAdvisoryBacklogService.selectOcrByCarLoanAdvisoryBacklog(page,CarLoanAdvisoryBacklogEx);
		
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
					List<CarLoanFlowWorkItemView> itemList = new ArrayList<CarLoanFlowWorkItemView>();
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
		return "car/consultation/carBorrowOcrList";
	}
	
	@RequestMapping(value = "carOcrQue")
	public String carOcrQue(Model model,String provinceId, String cityId, String id){
    	//公共获取信息
    	CarLoanInfo carLoanInfo = carLoanInfoService.get(id); // 借款信息
    	CarCustomerBase carCustomerBase = carCustomerBaseService.get(id);
    	CarCustomer carCustomer = carCustomerService.get(id); // 客户信息
    	CarVehicleInfo carVehicleInfo = carVehicleInfoService.get(id); // 车辆信息
    	CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.get(id);
    	CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.get(id);
		model.addAttribute("carCustomerConsultation", carCustomerConsultation);
    	model.addAttribute("carLoanInfo", carLoanInfo);
    	model.addAttribute("carCustomerBase", carCustomerBase);
    	model.addAttribute("loanCustomer", carCustomer);
    	model.addAttribute("carVehicleInfo", carVehicleInfo);
    	model.addAttribute("carCustomerBankInfo", carCustomerBankInfo);
    	model.addAttribute("id", id);
    	
    	User user = UserUtils.getUser();
		String consTeamEmpName = user.getName();
		model.addAttribute("consTeamEmpName", consTeamEmpName);
		model.addAttribute("consTeamManagerCode", user.getId());
    	Org org = ((User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO)).getDepartment();
		String orgType = org != null ? org.getType() : "";
		if(LoanOrgType.TEAM.key.equals(orgType)){
			org = OrgCache.getInstance().get(org.getParentId());
		}
		String departmentId = org.getId();
		List<UserInfo> termManagers = userInfoService.getStoreAllTermManager(departmentId);
		model.addAttribute("termManagers", termManagers);
		
    	//获取省份
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		List<CityInfo> cityList = cityInfoService.findCity(provinceId);
		model.addAttribute("cityList", cityList);
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		model.addAttribute("districtList", districtList);
		return "car/consultation/carOcrForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "savrCarOcrQue")
	public String savrCarOcrQue(CarOcrEx carOcrEx,HttpServletRequest request) throws ParseException{
		String id = carOcrEx.getId();
		CarLoanInfo carLoanInfo = carLoanInfoService.get(id); // 借款信息
    	CarCustomerBase carCustomerBase = carCustomerBaseService.get(id);
    	CarCustomer carCustomer = carCustomerService.get(id); // 客户信息
    	CarVehicleInfo carVehicleInfo = carVehicleInfoService.get(id); // 车辆信息
    	CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.get(id);
    	CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.get(id);
		    //被转译了临时处理 2016-6-4 何军
			carCustomer.setCustomerName(request.getParameter("customerName"));
			String flag = "false";
			if (flag.equals("false")) {
			    // 生成客户编码hai
			 	String customerCode = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER);
				carCustomerBase.setCustomerCode(customerCode);
				carCustomerConsultation.setCustomerCode(customerCode);
				carLoanInfo.setCustomerCode(customerCode);
				carCustomer.setCustomerCode(customerCode);
				//
				carCustomerBase.setCustomerMobilePhone(carOcrEx.getCustomerMobilePhone());
				carCustomer.setCustomerPhoneFirst(carOcrEx.getCustomerMobilePhone());
				carCustomer.setCustomerCertNum(carOcrEx.getCustomerCertNum());
				carCustomer.setCustomerSex(carOcrEx.getCustomerSex());
				carCustomer.setDictCertType(carOcrEx.getDictCertType());
				carCustomer.setCustomerLiveArea(carOcrEx.getCustomerLiveArea());
				carCustomer.setCustomerLiveCity(carOcrEx.getCustomerLiveCity());
				carCustomer.setCustomerLiveProvince(carOcrEx.getCustomerLiveProvince());
				carCustomer.setDictEducationCode(carOcrEx.getDictEducation());
				carCustomer.setDictMarryStatusCode(carOcrEx.getDictMarryStatus());
				carLoanInfo.setDictLoanStatus(carOcrEx.getDictOperStatus());
				//
				carCustomer.setCustomerAddress(carOcrEx.getCustomerAddress());
				
				carVehicleInfo.setPlateNumbers(carOcrEx.getPlateNumbers());
				carVehicleInfo.setVehicleBrandModel(carOcrEx.getVehicleBrandModel());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				carCustomerConsultation.setPlanArrivalTime(sdf.parse(carOcrEx.getPlanArrivalTime()));
				/*carOcrEx.getConsLoanRemarks();
				carOcrEx.getConsTeamManagerCode();
				carOcrEx.getCustomerAddress();
				carOcrEx.getCustomerCertNum();
				carOcrEx.getCustomerLiveArea();
				carOcrEx.getCustomerLiveCity();
				carOcrEx.getCustomerLiveProvince();
				carOcrEx.getCustomerMobilePhone();
				carOcrEx.getCustomerName();
				carOcrEx.getDictCertType();
				carOcrEx.getDictOperStatus();
				carOcrEx.getManagerCode();
				carOcrEx.getPlanArrivalTime();
				carOcrEx.getPlateNumbers();
				carOcrEx.getVehicleBrandModel();*/
			   //生成借款编号
				String loanCode=numberMasterService.getLoanNumber(SerialNoType.LOAN);
				carLoanInfo.setLoanCode(loanCode);
				carVehicleInfo.setLoanCode(loanCode);
				carCustomerConsultation.setLoanCode(loanCode);
				carCustomer.setLoanCode(loanCode);

				carCustomerBase.preUpdate();
				carCustomerConsultation.preUpdate();
				carCustomer.preUpdate();
				if(null!=carCustomerBankInfo){
					carCustomerBankInfo.setLoanCode(loanCode);
					carCustomerBankInfo.preUpdate();
					carCustomerBankInfoService.upadteCarCustomerBankInfoById(carCustomerBankInfo);
				}
				carVehicleInfo.preUpdate();
				carLoanInfo.setConsTeamManagercode(carOcrEx.getConsTeamManagerCode());
				carLoanInfo.preUpdate();
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
				carCustomerConsultation.setDictOperStatus(carOcrEx.getDictOperStatus());
				carCustomerConsultation.setConsLoanRemarks(carOcrEx.getConsLoanRemarks());
				carLoanInfoService.updateCarLoanInfoAndHis(carLoanInfo, carCustomerConsultation, carCustomerBase, carCustomer, carVehicleInfo);
			}
			return flag;
				
	}
	
	/**
	 * 删除指定的ocr图片，docId
	 * @param request
	 * @return
	 * @throws ParseException
	 * @author zhangqingan
	 */
	@ResponseBody
	@RequestMapping(value = "delDocId")
	public String delDocId(HttpServletRequest request) throws ParseException{
		String loanCode = request.getParameter("loanCode");
		String btnId = request.getParameter("btnId");
		CarCustomerBase carCustomerBase = new CarCustomerBase();
		carCustomerBase.setCustomerCode(loanCode);
		if("delNameOcr".equals(btnId)){
			//删除客户姓名ocr地址
			carCustomerBase.setCustomerNameOcr("");
		}else if("delCretOcr".equals(btnId)){
			//删除客户身份证ocr地址
			carCustomerBase.setCustomerCretOcr("");
		}
		carCustomerBaseService.update(carCustomerBase);
		return "true";
	}
}
