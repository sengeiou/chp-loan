package com.creditharmony.loan.car.carExtend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.remote.OrgService;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerCompanyService;
import com.creditharmony.loan.car.carApply.service.CarCustomerContactPersonService;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerCompany;
import com.creditharmony.loan.car.common.entity.CarCustomerContactPerson;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarImageService;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;

/**
 * 查看展期详情
 * @Class Name CarExtendDetailsController
 * @author ganquan
 * @Create In 2016年3月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtend/carExtendDetails")
public class CarExtendDetailsController extends BaseController {
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	//合同信息
	@Autowired
	private CarContractService carContractService;
	//客户信息
	@Autowired
	private CarCustomerService carCustomerService;
	//客户基本信息
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
	//工作信息
	@Autowired
	private CarCustomerCompanyService carCustomerCompanyService;
	//联系人信息
	@Autowired
	private CarCustomerContactPersonService  carCustomerContactPersonService;
	//账户信息
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;
	//共借人信息
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	//中间人信息
	@Autowired
	private MiddlePersonService middlePersonService;
	//机构信息
	@Autowired
	private OrgService orgService;
	//省市信息
	@Autowired
	private ProvinceCityManager provinceCityManager;
	//面审信息
	@Autowired
	private CarApplicationInterviewInfoService carApplicationInterviewInfoService;
	
	@Autowired
    private  CarImageService  carImageService;
	/**
	 * 展期详细信息查看  
	 * 2016年3月8日 
	 * By 甘泉
	 * @param String
	 * @return String
	 */
	@RequestMapping(value = "extendCheck")
	public String extendCheck(String applyId,Model model){
		//得到借款信息
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		//得到历史展期合同信息
		List<CarContract> carContracts = carContractService.getExtendContractByLoanCode(loanCode);
		if(carContracts.size() < 1){
			carContracts = null;
		}
		String midName = "";
		String midId = "";
		//得到合同信息
		CarContract carConTractThis = carContractService.getByLoanCode(loanCode);
		if (carConTractThis != null) {
			midId = carConTractThis.getMidId();
		} else {
			CarLoanInfo car = carLoanInfoService.getRichCarLoanInfo(carLoanInfo.getLoanRawcode(), loanCode);
			if (car != null) {
				midId = carContractService.getByLoanCode(car.getLoanCode()).getMidId();
			}
		}
		if(StringUtils.isNotEmpty(midId)){
			MiddlePerson person = middlePersonService.selectById(midId) ;
			if(person != null){
				midName = person.getMiddleName();
			}
		}
		//中间人
		model.addAttribute("middleman", midName);
		//得到客户信息
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode);
		String customerCode = carCustomer.getCustomerCode();
		//得到客户基本信息
		if(StringUtils.isNotEmpty(customerCode)){
			CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
			model.addAttribute("carCustomerBase", carCustomerBase);
		}
		//获得共借人信息
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
		String coboNames = "";
		for (CarLoanCoborrower carLoanCoborrower : carLoanCoborrowers) {
			String coborrowerName = carLoanCoborrower.getCoboName();
			coboNames += ("," + coborrowerName);
		}
		if (!"".equals(coboNames)) {
			coboNames = coboNames.substring(1);
		}
		//得到客户工作信息
		CarCustomerCompany carCustomerCompany = carCustomerCompanyService.selectCarCompany(loanCode);
		//联系人信息carCustomerContactPersons
		List<CarCustomerContactPerson> carCustomerContactPersons = carCustomerContactPersonService.selectCarContactPerson(loanCode);
		//查询开户行信息
		CarCustomerBankInfo CarCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
		//获取客户经理
		String managerCode =carLoanInfo.getManagerCode();
		if(!StringUtils.isEmpty(managerCode)){
			String managerName ="";
			if(UserUtils.get(managerCode)!=null){
				managerName = UserUtils.get(managerCode).getName();
			}
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
			if(null!=cityId){
				String cityName = provinceCityManager.get(cityId).getAreaName();
				model.addAttribute("cityName",cityName );
			}
		}
		//获取展期的合同编号
		if(StringUtils.isNotEmpty(carLoanInfo.getLoanRawcode())){
			String rawCarLoan = carLoanInfo.getLoanRawcode();
			if(StringUtils.isNotEmpty(rawCarLoan)){
				String contractNo = carContractService.getExtendContractNo(rawCarLoan, loanCode, "3");
				model.addAttribute("contractNo", contractNo);
			}
		}
		//获得面审信息
		CarApplicationInterviewInfo carApplicationInterviewInfo = carApplicationInterviewInfoService.selectCarApplicationInterviewInfo(loanCode);
		carLoanInfo.setDictLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", carLoanInfo.getDictLoanUse()));
		carCustomer.setDictCustomerSource2(DictCache.getInstance().getDictLabel("jk_cm_src", carCustomer.getDictCustomerSource2()));
		carCustomer.setCustomerTempPermit(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomer.getCustomerTempPermit()));
		carCustomer.setCustomerHouseHoldProperty(DictCache.getInstance().getDictLabel("jk_house_nature", carCustomer.getCustomerHouseHoldProperty()));
		carCustomer.setCustomerHaveChildren(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomer.getCustomerHaveChildren()));
		carCustomerCompany.setIsOtherRevenue(DictCache.getInstance().getDictLabel("jk_have_or_nohave", carCustomerCompany.getIsOtherRevenue()));
		carCustomerCompany.setDictUnitNature(DictCache.getInstance().getDictLabel("jk_unit_type", carCustomerCompany.getDictUnitNature()));
		carCustomerCompany.setDictEnterpriseNature(DictCache.getInstance().getDictLabel("jk_company_type", carCustomerCompany.getDictEnterpriseNature()));
		carCustomerCompany.setDictPositionLevel(DictCache.getInstance().getDictLabel("jk_job_type", carCustomerCompany.getDictPositionLevel()));
		model.addAttribute("carApplicationInterviewInfo",carApplicationInterviewInfo );
		model.addAttribute("carLoanInfo",carLoanInfo );
		model.addAttribute("carContracts",carContracts );
		
		model.addAttribute("carCustomer",carCustomer);
		model.addAttribute("carLoanCoborrowers", coboNames);
		model.addAttribute("carCustomerCompany",carCustomerCompany );
		model.addAttribute("carCustomerContactPersons",carCustomerContactPersons );
		model.addAttribute("CarCustomerBankInfo",CarCustomerBankInfo );
		String imageurl = carImageService.getExendImageUrl("合同制作", loanCode);
		model.addAttribute("imageurl",imageurl );
		return "car/carExtend/carExtendApply/carExtendDetailsCheck";
	}
}
