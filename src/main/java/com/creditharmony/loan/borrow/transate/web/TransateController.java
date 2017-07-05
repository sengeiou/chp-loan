package com.creditharmony.loan.borrow.transate.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.service.DeliveryApplyService;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanMinuteEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TraParamsEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TransateEx;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.borrow.transate.service.TransateService;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
/**
 * 信借已办列表Controller
 * @Class Name TransateController
 * @author lirui
 * @Create In 2015年12月2日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/transate")
public class TransateController extends BaseController{

	@Autowired
	private TransateService ts;
	
	@Autowired
	private LoanInfoService lis;
	
	@Autowired
	private DeliveryApplyService das;

	@Autowired
	private CityInfoDao cityInfoDao;
	
	@Autowired
	private DataEntryService dataEntryService;
	 
	@Autowired
	private AreaService areaService;
	 
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private LoanMinuteService lms;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ContractFileService contractFileService;
	
	@Autowired
	private ContractService contractService;
	
	/**
	 * 获得已办列表
	 * 2015年12月2日
	 * By lirui
	 * @param request HttpServletRequest对象
	 * @param traParams 
	 * @param m model模型
	 * @return 已办列表页面
	 */
	@RequestMapping(value = "transateInfo")
	public String transateInf(HttpServletRequest request,HttpServletResponse response,TraParamsEx traParams,Model m) {	
		//权限控制 如果登录人是门店人员   则门店选择框不可见
		//----------------------------------------------------------------------
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		String orgName = org != null ? org.getName() : "";
		boolean isManager =true;
		//如果登录是门店 则门店选择框不可见
		if (LoanOrgType.STORE.key.equals(orgType)){
			isManager=false;
	    //如果是门店 则 默认门店框体选项
		    traParams.setOrgCode(orgId);
			traParams.setOrgName(orgName);
		   }else if (LoanOrgType.TEAM.key.equals(orgType)){
		    isManager=false;
	    //如果是门店 则 默认门店框体选项
		    traParams.setOrgCode(orgId);
			traParams.setOrgName(orgName);
		   }
		m.addAttribute("isManager", isManager);
		//--------------------------------------------------------------------
		
		//如果是客服登陆 ，则没有申请冻结按钮的权限
		//--------------------------------------------------------------------
	     boolean isCanSe=false;
		 User user = UserUtils.getUser();
    	  List<Role> roleList = user.getRoleList();
  	    if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
	    	/*    CITY_MANAGER("6230000001","汇金门店-客服"), */
	    	     if(LoanRole.CUSTOMER_SERVICE.id.equals(role.getId())){
	    	    	 isCanSe = true;
	                 break;
	             }  
	    
            }
         }
  		m.addAttribute("isCanSe", isCanSe);  
		//--------------------------------------------------------------------
		
		// 保留查询数据
			if (traParams != null) {		
				m.addAttribute("t",traParams);
				// 查询已办列表			
				Page<TransateEx> traPage = ts.loanInfo(new Page<TransateEx>(request,response), traParams);
				List<TransateEx> list = traPage.getList();
				List<Dict> dictList = DictCache.getInstance().getList();
				for (TransateEx transateEx : list) {
					for(Dict dict:dictList){
						if(null!=dict.getValue() && "jk_loan_apply_status".equals(dict.getType()) && dict.getValue().equals(transateEx.getDictLoanStatus())){
							transateEx.setDictLoanStatusLabel(dict.getLabel());
						}
						if(null!=dict.getValue() && "jk_raise_flag".equals(dict.getType()) && dict.getValue().equals(transateEx.getLoanIsRaise())){
							transateEx.setLoanIsRaiseLable(dict.getLabel());
						}
						if(null!=dict.getValue() && "jk_telemarketing".equals(dict.getType()) && dict.getValue().equals(transateEx.getLoanIsPhone())){
							transateEx.setLoanIsPhoneLabel(dict.getLabel());
						}
						if(null!=dict.getValue() && "jk_channel_flag".equals(dict.getType()) && dict.getValue().equals(transateEx.getLoanMarking())){
							transateEx.setLoanMarkingLable(dict.getLabel());
						}
					}
				}
				// 获得所有产品列表(检索条件)
				List<String> products = lis.products();
				m.addAttribute("traPage", traPage);			
				m.addAttribute("products",products);
			}
			// 获得所有门店
			List<OrgView> orgs = das.orgs();
			List<Dict> dictlist=DictCache.getInstance().getAllListByType("jk_contract_ver");
		    m.addAttribute("dictlist", dictlist);
			m.addAttribute("query", "tra");
			m.addAttribute("orgs", orgs);
		return "transate/transateInfo";
	}
	
	/**
	 * 获得客户信息
	 * 2016年2月24日
	 * By lirui
	 * @param loanCode 借款编码
	 * @param applyId 流程id
	 * @param m Model模型
	 * @param status 还款状态
	 * @return 信借已办详情页面
	 */
	@RequestMapping(value = "transateDetails")
	public String transateDetails(String loanCode,String coboNames,String applyId,Model m,String status,String query,Boolean isManager,String loanInfoOldOrNewFlag) {
		LaunchView launchView = new LaunchView();
    	Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", CityInfoConstant.ROOT_ID);
        List<CityInfo> provinceList = cityInfoDao.findByParams(params);
        launchView.setProvinceList(provinceList);
    	ApplyInfoFlagEx applyInfo = areaService.getAllInfo(loanCode);
		BeanUtils.copyProperties(applyInfo, launchView);
		// 根据产品编号查询产品类型
		if (!ObjectHelper.isEmpty(launchView.getLoanInfo())) {
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(launchView.getLoanInfo().getProductType());
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			if (productList.size() != 0) {
				launchView.setProductName(productList.get(0).getProductName());
			}						
		}
		// 查询产品列表	
		LoanPrdMngEntity loanPrd1 = new LoanPrdMngEntity();	
		List<LoanPrdMngEntity> productList1 = loanPrdMngService.selPrd(loanPrd1);
		launchView.setProductList(productList1);
		launchView.setApplyId(applyId);
		areaService.coboAreaChange(launchView);
		// 房产省市区数据更换成名字
		areaService.houseAreaChange(launchView);
		// 开户行省市数据添加
		areaService.bankAreaChange(launchView);
		// 申请信息,管辖城市
		areaService.applyAreaChange(launchView);
		// 公司地址省市id与name转换
		areaService.companyAreaChange(launchView);
		//配偶地址省市id 与name转换
		areaService.mateAddressChange(launchView);
		//经营地址省市id与name转换
		areaService.loanCompManageAddressChange(launchView);
		//证件信息户主页地址省市id与name转换
		areaService.masterCertAddressChange(launchView);
		// 共借人
		launchView.setCoborrowerNames(coboNames);
		// 借款信息-录入人
		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanCustomerService())) {
			String service = areaService.getUserName(launchView.getLoanInfo().getLoanCustomerService());
			launchView.getLoanInfo().setLoanCustomerServiceName(service);
		} 
		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanManagerCode())) {
			launchView.getLoanInfo().setLoanManagerName(areaService.getUserName(launchView.getLoanInfo().getLoanManagerCode()));
		}
		LoanMinuteEx loanMinute = lms.loanMinute(loanCode);
		List<Map<String, Object>> emailStatusMap = lms.getSendEMail(loanCode,"0","7");
		List<Map<String, Object>> sendStatusMap = lms.getSend(loanCode,"0");
		List<Map<String, Object>> JQemailStatusMap = lms.getSendEMail(loanCode,"1","8");
		List<Map<String, Object>> JQsendStatusMap = lms.getSend(loanCode,"1");
		loanMinute.setLoanCode(loanCode);
		// 缓存码表取值
      	// 客户信息
        LoanCustomer lc = launchView.getLoanCustomer();
      	lc.setCustomerSexLabel(DictCache.getInstance().getDictLabel("jk_sex", lc.getCustomerSex()));
       	lc.setDictCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", lc.getDictCertType()));
      	lc.setDictMarryLabel(DictCache.getInstance().getDictLabel("jk_marriage", lc.getDictMarry()));
       	lc.setDictEducationLabel(DictCache.getInstance().getDictLabel("jk_degree", lc.getDictEducation()));
       	lc.setDictCustomerSourceLabel(DictCache.getInstance().getDictLabel("jk_cm_src", lc.getDictCustomerSource()));
       	// 申请信息
       	LoanInfo loanInfo = launchView.getLoanInfo();
       	loanInfo.setDictLoanUserLabel(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
       	loanInfo.setLoanUrgentFlagLabel(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfo.getLoanUrgentFlag()));   
       	loanInfo.setDictLoanSource(DictCache.getInstance().getDictLabel("jk_repay_source_new", loanInfo.getDictLoanSource()));
       	loanInfo.setDictLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
       	loanInfo.setLoanUrgentFlagLabel(DictCache.getInstance().getDictLabel("yes_no", loanInfo.getLoanUrgentFlag()));
       	// 信用资料
       	List<LoanCreditInfo> list = launchView.getLoanCreditInfoList();
       	for (LoanCreditInfo loanCreditInfo : list) {
       		loanCreditInfo.setDictMortgageTypeLabel(DictCache.getInstance().getDictLabel("jk_pledge_flag", loanCreditInfo.getDictMortgageType()));
		}
       	// 职业信息/公司资料
       	LoanCompany loanCompany = launchView.getCustomerLoanCompany();
       	loanCompany.setCompPostLabel(DictCache.getInstance().getDictLabel("jk_job_type", loanCompany.getCompPost()));
       	loanCompany.setDictCompTypeLabel(DictCache.getInstance().getDictLabel("jk_unit_type", loanCompany.getDictCompType()));
       	//所属行业
       	loanCompany.setDictCompIndustry(DictCache.getInstance().getDictLabel("jk_industry_type", loanCompany.getDictCompIndustry()));
       	loanCompany.setDictSalaryPay(DictCache.getInstance().getDictLabel("jk_paysalary_way", loanCompany.getDictSalaryPay()));
    	loanCompany.setCompPostLevel(DictCache.getInstance().getDictLabel("jk_job_type", loanCompany.getCompPostLevel()));
    	// 房产资料
       	List<LoanHouse> hostList = launchView.getCustomerLoanHouseList();
       	for (LoanHouse loanHouse : hostList) {
       		loanHouse.setHouseBuywayLabel(DictCache.getInstance().getDictLabel("jk_house_buywayg", loanHouse.getHouseBuyway()));
       		loanHouse.setHousePledgeFlagLabel(DictCache.getInstance().getDictLabel("jk_pledge_flag", loanHouse.getHousePledgeFlag()));
		}
       	// 联系人资料
       	List<Contact> contactList = launchView.getCustomerContactList();
       	//将联系人分类
       	Map<String,List<Contact>> contactMap=new HashMap<String,List<Contact>>();
       	//将联系人分为小类，家庭联系人
       	List<Contact>  familyList=new ArrayList<Contact>();
       	//将联系人分为小类，工作证明人
       	List<Contact>  workmateList=new ArrayList<Contact>();
       	//将联系人分为小类，其他联系人
       	List<Contact>  otherRelationList=new ArrayList<Contact>();
       	for (Contact contact : contactList) {
       		contact.setRelationTypeLabel(DictCache.getInstance().getDictLabel("jk_relation_type", contact.getRelationType()));
       		if ("0".equals(contact.getRelationType())) {
       			contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_family_relation", contact.getContactRelation()));		
       			//将家属联系人单独列出
       			familyList.add(contact);
       			contactMap.put("family_relation", familyList);
			}
       		if ("1".equals(contact.getRelationType())) {
       			contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_workmate_relation", contact.getContactRelation()));	
       			//将工作证明人单独列出
       			workmateList.add(contact);
       			contactMap.put("workmate_relation", workmateList);
			}
       		if ("2".equals(contact.getRelationType())) {
       			//contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_other_relation", contact.getContactRelation()));	
       			//直接取备注里的值
       			contact.getRemarks();
       			//将其他联系人单独列出
       			otherRelationList.add(contact);
       			contactMap.put("other_relation", otherRelationList);
			}
		}
       	//将分类好的联系人加载进实体launchView中
       	launchView.setContactMap(contactMap);
       	// 银行卡资料
       	LoanBank loanBank = launchView.getLoanBank();
       	loanBank.setBankNameLabel(DictCache.getInstance().getDictLabel("jk_open_bank", loanBank.getBankName()));
       	loanBank.setBankSigningPlatformName(DictCache.getInstance().getDictLabel("jk_deduct_plat",loanBank.getBankSigningPlatform()));
       	String stepName = "";
        User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
        String orgId = currentUser.getDepartment().getId();
        Org org = OrgCache.getInstance().get(orgId);
        String orgType = org != null ? org.getType() : "";
        if (LoanOrgType.TEAM.key.equals(orgType)){
            stepName = FlowStep.TEAM_MANAGER_IMAGE_VIEW.getName();
        }else{
            stepName = FlowStep.IMAGE_VIEW.getName();
        }
        String loanCodeUrl = loanCode;
        if(loanMinute.getOldLoanCode() !=null && loanMinute.getOldLoanCode() !=""){
        	loanCodeUrl = loanMinute.getOldLoanCode();
        }
       	String url = imageService.getImageUrl(stepName, loanCodeUrl);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("loanCode", loanInfo.getLoanCode());
		List<ContractFile> files = contractFileService.getContractFileByParam(param);
		if(!ObjectHelper.isEmpty(files)){
		   String protoColUrl = imageService.getImageUrl(FlowStep.PROTOCOL_VIEW.getName(), loanCodeUrl);
		   m.addAttribute("protoColUrl", protoColUrl);
		   m.addAttribute("procBtn", "1"); 
		}else{
		    m.addAttribute("procBtn", "0");  
		}
		if(!emailStatusMap.isEmpty()){
			m.addAttribute("emailStatus", emailStatusMap.get(0).get("num"));
		}else{
			m.addAttribute("emailStatus", "0");
		}
		if(!sendStatusMap.isEmpty()){
			m.addAttribute("sendStatus", sendStatusMap.get(0).get("num"));
		}else{
			m.addAttribute("sendStatus", "0");
		}
		if(!JQemailStatusMap.isEmpty()){
			m.addAttribute("JQemailStatus", JQemailStatusMap.get(0).get("num"));
		}else{
			m.addAttribute("JQemailStatus", "0");
		}
		if(!JQsendStatusMap.isEmpty()){
			m.addAttribute("JQsendStatus", JQsendStatusMap.get(0).get("num"));
		}else{
			m.addAttribute("JQsendStatus", "0");
		}
		//查询合同对应渠道
		Contract contract = contractService.findByLoanCode(loanCode);
		m.addAttribute("isManager", isManager);
		m.addAttribute("query", query);
		m.addAttribute("url", url);
		m.addAttribute("lm", loanMinute);
		m.addAttribute("bview", launchView);
		m.addAttribute("status",status);
		m.addAttribute("loanCode",loanCode);
		m.addAttribute("contactMap", contactMap);
		m.addAttribute("contract", contract);
		m.addAttribute("dictLoanStatus", loanInfo.getDictLoanStatus());
		//信借申请标志为0，跳转到旧版，为1或空跳转到新版
		if(StringUtils.isNotEmpty(loanInfoOldOrNewFlag)&&loanInfoOldOrNewFlag.equals("0")){
			return "transate/transateDetails";
		}else{
			return "transate/transateDetails_new";
		}
	}
	
	/**
	 * 查询数据是否通过利率审核
	 * 2016年4月13日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return loanCode
	 */
	@ResponseBody
	@RequestMapping(value="checkUrl")
	public String checkUrl(String loanCode) {
		String code = null;
		if (StringUtils.isNotEmpty(loanCode)) {
			code = ts.checkUrl(loanCode);
		}
		return code;
	}
}
