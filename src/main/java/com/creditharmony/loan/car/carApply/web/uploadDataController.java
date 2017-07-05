package com.creditharmony.loan.car.carApply.web;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.users.remote.OrgService;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerCompanyService;
import com.creditharmony.loan.car.carApply.service.CarCustomerContactPersonService;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carApply.view.UploadView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;

/**
 * 车借上传资料
 * @Class Name uploadDataController
 * @author ganquan
 * @Create In 2016年2月15日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/uploadDataController")
public class uploadDataController extends BaseController{
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	//客户信息
	@Autowired
	private CarCustomerService carCustomerService;
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	//车辆信息
	@Autowired 
	private CarVehicleInfoService carVehicleInfoService;
	//工作信息
	@Autowired
	private CarCustomerCompanyService carCustomerCompanyService;
	//联系人信息
	@Autowired
	private CarCustomerContactPersonService carCustomerContactPersonService;
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	//共借人信息
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	//客户银行卡信息
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;
	@Autowired
	private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private ProvinceCityManager provinceCityManager;
	//中间人信息service
	@Autowired
	private MiddlePersonService middlePersonService;
	
	//上传资料确认
	@RequestMapping(value = "uploadDataConfirm")
	public String uploadDataConfirm(WorkItemView workItemView,String loanCode,FlowProperties flowProperties){
		//标红置顶相关内容
		workItemView.setFlowProperties(flowProperties);
		//走待初审节点
		workItemView.setResponse(CarLoanResponses.TO_FRIST_AUDIT.getCode());
		UploadView bv = new UploadView();
		//加入借款编码
		bv.setLoanCode(loanCode);
		bv.setDictLoanStatus(CarLoanStatus.PENDING_FIRST_INSTANCE.getCode());
		workItemView.setBv(bv);
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carApplyTask/fetchTaskItems";
	}
	
	//客户放弃 giveUp
	@RequestMapping(value = "giveUp")
	public String giveUp(WorkItemView workItemView,String loanCode){
		//走客户放弃
		workItemView.setResponse(CarLoanResponses.CUSTOMER_GIVE_UP.getCode());
		UploadView bv = new UploadView();
		bv.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
		bv.setLoanCode(loanCode);
		workItemView.setBv(bv);
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carApplyTask/fetchTaskItems";
	}
	
	//退回节点
	@RequestMapping(value = "sendBack")
	public String sendBack(WorkItemView workItemView,String loanCode,String backNode,String remark,FlowProperties flowProperties){
		//标红置顶相关内容
		workItemView.setFlowProperties(flowProperties);
		UploadView bv = new UploadView();
		bv.setLoanCode(loanCode);
		if("1".equals(backNode) && loanCode != null){
			//退回车借申请
			workItemView.setResponse(CarLoanResponses.BACK_LOAN_APPLY.getCode());
			bv.setDictLoanStatus(CarLoanStatus.UPLOADED_FILE_BACK.getCode());
			bv.setRemark(remark);
		}else if("0".equals(backNode) && loanCode != null){
			//退回评估师录入
			workItemView.setResponse(CarLoanResponses.BACK_ASSESS_ENTER.getCode());
			bv.setDictLoanStatus(CarLoanStatus.UPLOADED_FILE_BACK.getCode());
			bv.setDictOperStatus(NextStep.CONTINUE_CONFIRM.getCode());
			bv.setRemark(remark);
		}
		workItemView.setBv(bv);
		flowService.dispatch(workItemView);
		return "redirect:" + adminPath
				+ "/car/carApplyTask/fetchTaskItems";
	}
	
	//查看客户借款相关的所有信息
	@RequestMapping(value = "showCarLoanInfo")
	public String showCarLoanInfo(Model model,String loanCode){
		//获得车借借款信息
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
		//获得车借客户信息
		String customerCode = carLoanInfo.getCustomerCode();
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode);
//		if(null != customerCode){
//			customerCode = "";
//		}
		CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
		//获得车借车辆信息
		CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode);
		//获得工作信息
		CarCustomerCompany carCustomerCompany = carCustomerCompanyService.selectCarCompany(loanCode);
		if(carCustomerCompany != null){
			String addressCompany = provinceCityManager.getProvinceCity(carCustomerCompany.getDictCompanyProvince(), 
					carCustomerCompany.getDictCompanyCity(), carCustomerCompany.getDictCompanyArea());
			if(StringUtils.isNotEmpty(addressCompany)){
				model.addAttribute("addressCompany", addressCompany);
			}
		}
		//获得客户(主借人)联系人信息
		List<CarCustomerContactPerson> carCustomerContactPersons = carCustomerContactPersonService.selectCarContactPerson(loanCode);
		//获得共借人信息
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
		if(carLoanCoborrowers.size() <=0){
			carLoanCoborrowers = null;
		}
		List<Dict> dictList = DictCache.getInstance().getList();
		//获得银行卡信息
		CarCustomerBankInfo CarCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
		//获得共借人联系人信息
		if(carLoanCoborrowers != null && carLoanCoborrowers.size() >0){
			Map<CarLoanCoborrower,List<CarCustomerContactPerson>> CoborrowerMap = new LinkedHashMap<CarLoanCoborrower,List<CarCustomerContactPerson>>();
			List<CarCustomerContactPerson> carCoBoCustomerContactPerson = null;
			for(CarLoanCoborrower c : carLoanCoborrowers){
				System.out.println(c.getCreateTime());
				carCoBoCustomerContactPerson = carCustomerContactPersonService.selectByCoborrower(c.getId());
				//c.setDictSex(DictCache.getInstance().getDictLabel("jk_sex", c.getDictSex()));
				//c.setDictMarryStatus(DictCache.getInstance().getDictLabel("jk_marriage", c.getDictMarryStatus()));
				//c.setHaveChildFlag(DictCache.getInstance().getDictLabel("jk_have_or_nohave", c.getHaveChildFlag()));
				for (Dict dict : dictList) {
					if(null != dict.getValue() && "jk_sex".equals(dict.getType()) && dict.getValue().equals(c.getDictSex())){
						c.setDictSex(dict.getLabel());
					}
					if(null != dict.getValue() && "jk_marriage".equals(dict.getType()) && dict.getValue().equals(c.getDictMarryStatus())){
						c.setDictMarryStatus(dict.getLabel());
					}
					if(null != dict.getValue() && "jk_have_or_nohave".equals(dict.getType()) && dict.getValue().equals(c.getHaveChildFlag())){
						c.setHaveChildFlag(dict.getLabel());
					}
				}
				CoborrowerMap.put(c, carCoBoCustomerContactPerson);
			}
			model.addAttribute("CoborrowerMap", CoborrowerMap);
		}
		//抵押权人名
		if(!"".equals(carLoanInfo.getMortgagee())&&carLoanInfo.getMortgagee()!=null)
		{
			MiddlePerson middlePerson = new MiddlePerson();
			middlePerson.setId(carLoanInfo.getMortgagee());
			List<MiddlePerson> middlePersons = middlePersonService.selectMiddlePerson(middlePerson);
			if(middlePersons!=null&&middlePersons.size()>0)
			{
				carLoanInfo.setMortgagee(middlePersons.get(0).getMiddleName());
			}
		}
		//获取客户经理
		String managerCode =carLoanInfo.getManagerCode();
		if(!StringUtils.isEmpty(managerCode)&&null!=UserUtils.get(managerCode)){
			String managerName = UserUtils.get(managerCode).getName();
			model.addAttribute("managerName", managerName);			
		}
		//获取团队经理
		String teamManagerCode = carLoanInfo.getConsTeamManagercode();
		if(!StringUtils.isEmpty(teamManagerCode)){
			String teamManagerName = "";
			if(UserUtils.get(teamManagerCode)!=null)
			{
				teamManagerName = UserUtils.get(teamManagerCode).getName();	
			}
			model.addAttribute("teamManagerName", teamManagerName);
		}
		//获取管辖城市
		String storeCode = carLoanInfo.getStoreCode();
		if(!StringUtils.isEmpty(storeCode)){
			String cityId = orgService.getOrg(storeCode).getCityId();
			if(!StringUtils.isEmpty(cityId)){
				String cityName = provinceCityManager.get(cityId).getAreaName();
				model.addAttribute("cityName",cityName );
			}
		}
		//获得客户人法查询结果
		if(carCustomer!=null)
		{
			for (Dict dict2 : dictList) {
				if(null != dict2.getValue() && "jk_cm_src".equals(dict2.getType()) && dict2.getValue().equals(carCustomer.getDictCustomerSource2())){
					carCustomer.setDictCustomerSource2(dict2.getLabel());
				}
				if(null != dict2.getValue() && "jk_have_or_nohave".equals(dict2.getType()) && dict2.getValue().equals(carCustomer.getCustomerTempPermit())){
					carCustomer.setCustomerTempPermit(dict2.getLabel());
				}
				if(null != dict2.getValue() && "jk_house_nature".equals(dict2.getType()) && dict2.getValue().equals(carCustomer.getCustomerHouseHoldProperty())){
					carCustomer.setCustomerHouseHoldProperty(dict2.getLabel());
				}
				if(null != dict2.getValue() && "jk_have_or_nohave".equals(dict2.getType()) && dict2.getValue().equals(carCustomer.getCustomerHaveChildren())){
					carCustomer.setCustomerHaveChildren(dict2.getLabel());
				}
			}
			//carCustomer.setDictCustomerSource2(DictCache.getInstance().getDictLabel("jk_cm_src", carCustomer.getDictCustomerSource2()));
			//carCustomer.setCustomerTempPermit(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomer.getCustomerTempPermit()));
			//carCustomer.setCustomerHouseHoldProperty(DictCache.getInstance().getDictLabel("jk_house_nature", carCustomer.getCustomerHouseHoldProperty()));
			//carCustomer.setCustomerHaveChildren(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomer.getCustomerHaveChildren()));
		}
		if(carCustomerCompany!=null)
		{
			for (Dict dict : dictList) {
				if(null != dict.getValue() && "jk_job_type".equals(dict.getType()) && dict.getValue().equals(carCustomerCompany.getDictPositionLevel())){
					carCustomerCompany.setDictPositionLevel(dict.getLabel());
				}
				if(null != dict.getValue() && "jk_have_or_nohave".equals(dict.getType()) && dict.getValue().equals(carCustomerCompany.getIsOtherRevenue())){
					carCustomerCompany.setIsOtherRevenue(dict.getLabel());
				}
				if(null != dict.getValue() && "jk_unit_type".equals(dict.getType()) && dict.getValue().equals(carCustomerCompany.getDictUnitNature())){
					carCustomerCompany.setDictUnitNature(dict.getLabel());
				}
				if(null != dict.getValue() && "jk_company_type".equals(dict.getType()) && dict.getValue().equals(carCustomerCompany.getDictEnterpriseNature())){
					carCustomerCompany.setDictEnterpriseNature(dict.getLabel());
				}
			}
			//carCustomerCompany.setDictPositionLevel(DictCache.getInstance().getDictLabel("jk_job_type", carCustomerCompany.getDictPositionLevel()));
			//carCustomerCompany.setIsOtherRevenue(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomerCompany.getIsOtherRevenue()));
			//carCustomerCompany.setDictUnitNature(DictCache.getInstance().getDictLabel("jk_unit_type", carCustomerCompany.getDictUnitNature()));
			//carCustomerCompany.setDictEnterpriseNature(DictCache.getInstance().getDictLabel("jk_company_type", carCustomerCompany.getDictEnterpriseNature()));
		}
		for (Dict dict : dictList) {
			if(null != dict.getValue() && "jk_have_or_nohave".equals(dict.getType()) && dict.getValue().equals(carLoanInfo.getDictLoanCommonRepaymentFlag())){
				carLoanInfo.setDictLoanCommonRepaymentFlag(dict.getLabel());
			}
			if(null != dict.getValue() && "jk_yes_or_no".equals(dict.getType()) && dict.getValue().equals(carLoanInfo.getDictGpsRemaining())){
				carLoanInfo.setDictGpsRemaining(dict.getLabel());
			}
			if(null != dict.getValue() && "jk_yes_or_no".equals(dict.getType()) && dict.getValue().equals(carLoanInfo.getDictIsGatherFlowFee())){
				carLoanInfo.setDictIsGatherFlowFee(dict.getLabel());
			}
			if(null != dict.getValue() && "jk_loan_use".equals(dict.getType()) && dict.getValue().equals(carLoanInfo.getDictLoanUse())){
				carLoanInfo.setDictLoanUse(dict.getLabel());
			}
		}
		//carLoanInfo.setDictLoanCommonRepaymentFlag(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carLoanInfo.getDictLoanCommonRepaymentFlag()));
		//carLoanInfo.setDictGpsRemaining(DictCache.getInstance().getDictLabel("jk_yes_or_no", carLoanInfo.getDictGpsRemaining()));
		//carLoanInfo.setDictIsGatherFlowFee(DictCache.getInstance().getDictLabel("jk_yes_or_no", carLoanInfo.getDictIsGatherFlowFee()));
		//carLoanInfo.setDictLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", carLoanInfo.getDictLoanUse()));
		CarApplicationInterviewInfo carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
		model.addAttribute("carApplicationInterviewInfo", carApplicationInterviewInfo);
		model.addAttribute("carCustomer", carCustomer);
		model.addAttribute("carCustomerBase", carCustomerBase);
		model.addAttribute("carVehicleInfo", carVehicleInfo);
		model.addAttribute("carLoanInfo", carLoanInfo);
		model.addAttribute("carCustomerCompany", carCustomerCompany);
		model.addAttribute("carCustomerContactPersons", carCustomerContactPersons);
		model.addAttribute("CarCustomerBankInfo", CarCustomerBankInfo);
		model.addAttribute("carLoanCoborrowers", carLoanCoborrowers);
		return "car/carApply/uploadDataCheck";
	}
}
