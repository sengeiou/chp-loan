package com.creditharmony.loan.borrow.applyinfo.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.Marriage;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.RoleManager;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.service.ConsultService;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.channel.jyj.service.JyjBorrowBankConfigureService;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.LoanConsultDateUtils;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.utils.EncryptUtils;

/**
 * 资料录入DataEntryController
 * @Class Name DataEntryController
 * @author zhangping
 * @Create In 2015年12月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/dataEntry")
public class DataEntryController extends BaseController {

	@Autowired
	private DataEntryService dataEntryService;
	@Autowired
	private CityInfoService cityInfoService;

	@Autowired
	private NumberMasterService numberMasterService;
	
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
    private ImageService imageService;
	
	@Autowired
    private ConsultService consultService;
	   
	@Autowired
	private JyjBorrowBankConfigureService jyjBorrowBankConfigureService;
	/**
	 * 2015年12月25日
	 * By zhangping
	 * @param customerCode
	 * @param model
	 * @return loanflow_launch.jsp
	 */
	@ResponseBody
	@RequestMapping(value = { "form", "" })
	public String form(String customerCode, Model model) {
	    Map<String,String> initParam = new HashMap<String,String>();
	    initParam.put("customerCode", customerCode);
	    initParam.put("flag", ApplyInfoConstant.APPLY_INFO_CUSTOMER);
		ApplyInfoFlagEx applyInfoFlagEx = dataEntryService.getCustumerData(initParam);
		model.addAttribute("customerBaseInfo", applyInfoFlagEx);
		return "borrow/apply/loanflow_launch";
	}

	/**
	 * 
	 * 2015年12月3日
	 * 此方法只适用于旧版申请表，新版申请表请调用saveApplyInfo_new
	 * By zhangping
	 * @param applyInfoFlagEx
	 * @param model
	 * @param redirectAttributes
	 * @param workItem
	 * @return 重定向 getApplyInfo
	 */
//	@RequestMapping(value = "saveApplyInfo")
	public String saveApplyInfo(ApplyInfoFlagEx applyInfoFlagEx,
		Model model, RedirectAttributes redirectAttributes,WorkItemView workItem) {
	    applyInfoFlagEx.getLoanInfo().setLoanCustomerName(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanInfo().getLoanCustomerName()));
	    applyInfoFlagEx.getLoanCustomer().setCustomerName(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanCustomer().getCustomerName()));
	    //Object clock = new Object();
	    int targetFlag ;
	    String flag = "" ;
	    boolean result  =false;
	    String url = "";
	    synchronized(this){  
    	    String curTokenId = applyInfoFlagEx.getDefTokenId();
    	    String curToken  = applyInfoFlagEx.getDefToken();
    	    result = TokenUtils.validToken(curTokenId, curToken);
    	    TokenUtils.removeToken(curTokenId);
	    } 
	      if(result){
	          boolean insert = false;
	          //  初始生成loanCode并设置全局字段loanCode值
	          if (ObjectHelper.isEmpty(applyInfoFlagEx.getLoanInfo())
				|| StringUtils.isEmpty(applyInfoFlagEx.getLoanInfo()
						.getLoanCode())) {
	              String loanCode = numberMasterService
					.getLoanNumber(SerialNoType.LOAN);
	              applyInfoFlagEx.getLoanInfo().setLoanCode(loanCode);
	              insert = true;
	          }
	        // 防止同一笔单子被多人同时操作
	        try {
                dataEntryService.saveApplyInfo(applyInfoFlagEx, insert);
            } catch (Exception e) {
                redirectAttributes.addAttribute("message",e.getMessage());  
                e.printStackTrace();
                return "redirect:" + adminPath
                        + "/borrow/borrowlist/fetchItemsForLaunch";
            }
	   
	          String sigin = applyInfoFlagEx.getFlag();
	          int sigins = Integer.valueOf(sigin);
	          targetFlag = sigins + 1;
	          flag = String.valueOf(targetFlag);
	          if(ApplyInfoConstant.APPLY_INFO_CUSTOMER.equals(sigin)){
	              String dictMarry = applyInfoFlagEx.getLoanCustomer().getDictMarry();
	              if(!Marriage.MARRIED.getCode().equals(dictMarry)){
	                  targetFlag = sigins+2;
	                  flag = String.valueOf(targetFlag); 
	              }
	          }
	          // 缓存银行信息
	          if(ApplyInfoConstant.APPLY_INFO_BANK.equals(sigin)){
	              flag = sigin;
	              targetFlag = sigins;
	          }
	      }else{
	          String sigin = applyInfoFlagEx.getFlag();
              int sigins = Integer.valueOf(sigin);
              targetFlag = sigins;
              flag = sigin;
              redirectAttributes.addAttribute("message",ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE);
	      }
	    if(targetFlag!=1){  
	        String viewName = ApplyInfoConstant.VIEWS_NAME[targetFlag-1];
	        redirectAttributes.addAttribute("loanInfo.loanCode", applyInfoFlagEx.getLoanInfo().getLoanCode());
	        redirectAttributes.addAttribute("loanCustomer.id", applyInfoFlagEx.getLoanCustomer().getId());
	        redirectAttributes.addAttribute("loanCustomer.customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
	        redirectAttributes.addAttribute("customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
	        redirectAttributes.addAttribute("consultId", applyInfoFlagEx.getConsultId());
	        redirectAttributes.addAttribute("flowCode", workItem.getFlowCode());
		    redirectAttributes.addAttribute("flowType", workItem.getFlowType());
		    redirectAttributes.addAttribute("flag",flag);
		    redirectAttributes.addAttribute("viewName",viewName);
		    try {
		    	redirectAttributes.addAttribute("loanCustomer.customerName", URLEncoder.encode(applyInfoFlagEx.getLoanCustomer().getCustomerName(), "UTF-8"));
		    	redirectAttributes.addAttribute("loanInfo.loanCustomerName", URLEncoder.encode(applyInfoFlagEx.getLoanInfo().getLoanCustomerName(), "UTF-8"));
		    	redirectAttributes.addAttribute("flowName", URLEncoder.encode(workItem.getFlowName(), "UTF-8"));
		    	redirectAttributes.addAttribute("stepName", URLEncoder.encode(workItem.getStepName(), "UTF-8"));
		    } catch (UnsupportedEncodingException e) {
		    	logger.error("旧版申请资料页面get传参中文处理传入参数异常", e);
		    	e.printStackTrace();
		    }
		    url = "/apply/dataEntry/getApplyInfo";
	    }else{
	        redirectAttributes.addAttribute("consultId", applyInfoFlagEx.getConsultId());
            redirectAttributes.addAttribute("flowCode", workItem.getFlowCode());
            redirectAttributes.addAttribute("customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
            redirectAttributes.addAttribute("defTokenId", applyInfoFlagEx.getDefTokenId());
	        url = "/bpm/flowController/openLaunchForm";
	    }
		return "redirect:" + adminPath+url;
	}
	/**
	 * saveApplyInfo_new适用于新版申请表
	 */
	@RequestMapping(value = "saveApplyInfo_new")
	public String saveApplyInfo_new(ApplyInfoFlagEx applyInfoFlagEx,
			Model model, RedirectAttributes redirectAttributes,WorkItemView workItem) {
		applyInfoFlagEx.getLoanInfo().setLoanCustomerName(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanInfo().getLoanCustomerName()));
	    applyInfoFlagEx.getLoanCustomer().setCustomerName(StringEscapeUtils.unescapeHtml4(applyInfoFlagEx.getLoanCustomer().getCustomerName()));
	    //Object clock = new Object();
	    int targetFlag ;
	    String flag = "" ;
	    boolean result  =false;
	    String url = "";
	    synchronized(this){  
    	    String curTokenId = applyInfoFlagEx.getDefTokenId();
    	    String curToken  = applyInfoFlagEx.getDefToken();
    	    result = TokenUtils.validToken(curTokenId, curToken);
    	    TokenUtils.removeToken(curTokenId);
	    } 
	    if(result){
			boolean insert = false;
			// 初始生成loanCode并设置全局字段loanCode值
			if(ObjectHelper.isEmpty(applyInfoFlagEx.getLoanInfo())
					|| StringUtils.isEmpty(applyInfoFlagEx.getLoanInfo().getLoanCode())) {
				
				String loanCode = numberMasterService.getLoanNumber(SerialNoType.LOAN);
				applyInfoFlagEx.getLoanInfo().setLoanCode(loanCode);
				insert = true;
			}
			logger.info("新版申请表保存前借款编号："+ applyInfoFlagEx.getLoanInfo().getLoanCode());
	        // 防止同一笔单子被多人同时操作
	        try {
                dataEntryService.saveApplyInfo_new(applyInfoFlagEx, insert);
            } catch (Exception e) {
                redirectAttributes.addAttribute("message",e.getMessage());  
                e.printStackTrace();
                return "redirect:" + adminPath + "/borrow/borrowlist/fetchItemsForLaunch";
            }
			String sigin = applyInfoFlagEx.getFlag();
			int sigins = Integer.valueOf(sigin);
			targetFlag = sigins + 1;
			flag = String.valueOf(targetFlag);
	          
			// 缓存银行信息
			if (ApplyInfoConstant.NEW_APPLY_INFO_BANK.equals(sigin)) {
				flag = sigin;
				targetFlag = sigins;
			}
		} else {
			String sigin = applyInfoFlagEx.getFlag();
			int sigins = Integer.valueOf(sigin);
			targetFlag = sigins;
			flag = sigin;
			redirectAttributes.addAttribute("message", ApplyInfoConstant.REPEAT_SUBMIT_MESSAGE);
		}
	    if(targetFlag!=1){  
	        String viewName = ApplyInfoConstant.VIEWS_NAME_NEW[targetFlag-1];
	        
	        logger.info("新版申请表保存后跳转前借款编号："+ applyInfoFlagEx.getLoanInfo().getLoanCode());
	        
	        redirectAttributes.addAttribute("loanInfo.loanCode", applyInfoFlagEx.getLoanInfo().getLoanCode());
	        redirectAttributes.addAttribute("loanCustomer.id", applyInfoFlagEx.getLoanCustomer().getId());
	        redirectAttributes.addAttribute("loanCustomer.customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
	        redirectAttributes.addAttribute("customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
	        redirectAttributes.addAttribute("consultId", applyInfoFlagEx.getConsultId());
	        redirectAttributes.addAttribute("flowCode", workItem.getFlowCode());
		    redirectAttributes.addAttribute("flowType", workItem.getFlowType());
		    redirectAttributes.addAttribute("flag",flag);
		    redirectAttributes.addAttribute("viewName",viewName);
		    try {
		    	redirectAttributes.addAttribute("loanCustomer.customerName", StringUtils.isNotEmpty(applyInfoFlagEx.getLoanCustomer().getCustomerName())?(URLEncoder.encode(applyInfoFlagEx.getLoanCustomer().getCustomerName(), "UTF-8")):applyInfoFlagEx.getLoanCustomer().getCustomerName());
		    	redirectAttributes.addAttribute("loanInfo.loanCustomerName", StringUtils.isNotEmpty(applyInfoFlagEx.getLoanInfo().getLoanCustomerName())?(URLEncoder.encode(applyInfoFlagEx.getLoanInfo().getLoanCustomerName(), "UTF-8")):applyInfoFlagEx.getLoanInfo().getLoanCustomerName());
		    	redirectAttributes.addAttribute("flowName", URLEncoder.encode(workItem.getFlowName(), "UTF-8"));
		    	redirectAttributes.addAttribute("stepName", URLEncoder.encode(workItem.getStepName(), "UTF-8"));
		    } catch (UnsupportedEncodingException e) {
		    	logger.error("新版申请资料页面get传参中文处理传入参数异常", e);
		    	e.printStackTrace();
		    }
		    url = "/apply/dataEntry/getApplyInfo_new";
	    }else{
	        redirectAttributes.addAttribute("consultId", applyInfoFlagEx.getConsultId());
            redirectAttributes.addAttribute("flowCode", workItem.getFlowCode());
            redirectAttributes.addAttribute("customerCode", applyInfoFlagEx.getLoanCustomer().getCustomerCode());
            redirectAttributes.addAttribute("defTokenId", applyInfoFlagEx.getDefTokenId());
	        url = "/bpm/flowController/openLaunchForm?dealType="+ApplyInfoConstant.LOANINFO_OLDORNEW_FLAG_NEW;
	    }
		return "redirect:" + adminPath+url;
	}

	/**
	 * 2015年12月3日
	 * 此方法只适用于旧版申请表，新版申请表调用getApplyInfo_new
	 * By zhangping
	 * @param model
	 * @param flag
	 * @param viewName
	 * @param customerCode
	 * @param consultId
	 * @param workItem
	 * @param launchView
	 * @return apply.jsp/? + viewName
	 */
//	@RequestMapping(value = "getApplyInfo")
	public String getApplyInfo(Model model,String flag,
			 String viewName,String customerCode,String consultId,WorkItemView workItem,LaunchView launchView) {
	    TokenUtils.removeToken(launchView.getDefTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    launchView.setDefTokenId(tokenMap.get("tokenId"));
	    launchView.setDefToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
	   	String loanCode = launchView.getLoanInfo().getLoanCode();
		Map<String,String> initParam = new HashMap<String,String>();
		initParam.put("customerCode", customerCode);
		initParam.put("flag", flag);
		initParam.put("loanCode", loanCode);
		initParam.put("consultId", consultId);
		ApplyInfoFlagEx applyInfoEx = dataEntryService.getCustumerData(initParam);
		model.addAttribute("applyInfoEx", applyInfoEx);
		if(ApplyInfoConstant.APPLY_INFO_LOANINFO.equals(flag)){
		    LoanInfo loanInfo = applyInfoEx.getLoanInfo();
		    String isBorrow = loanInfo.getIsBorrow();
		    User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
            Org org = user.getDepartment();
            OrgCache orgCache = OrgCache.getInstance();
            Org storeOrg = orgCache.get(org.getId());
            loanInfo.setLoanCustomerService(user.getId());
            loanInfo.setLoanCustomerServiceName(user.getName());
            loanInfo.setStoreProviceCode(storeOrg.getProvinceId());
            logger.info("门店省份ID："+loanInfo.getStoreProviceCode());
            loanInfo.setStoreCityCode(storeOrg.getCityId());
            logger.info("门店城市ID："+storeOrg.getCityId());
            if(StringUtils.isNotEmpty(loanInfo.getLoanManagerCode())){
                User loanManager = userManager.get(loanInfo.getLoanManagerCode());
                if(!ObjectHelper.isEmpty(loanManager)){
                    loanInfo.setLoanManagerName(loanManager.getName());
                }
            }
            applyInfoEx.setLoanInfo(loanInfo);
		    LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		    
		    Iterator<LoanPrdMngEntity> iterator = productList.iterator();
		    while(iterator.hasNext()){//信易借的产品处理
		    	LoanPrdMngEntity lpme = iterator.next();
		    	if("1".equals(isBorrow)){//信易借
		    		if(!LoanProductCode.PRO_XYJ.equals(lpme.getProductCode())){//如果不是信易借，则全部删除
		    			iterator.remove();
		    		}
		    	}else{
		    		if(LoanProductCode.PRO_XYJ.equals(lpme.getProductCode())){//移除信易借
		    			iterator.remove();
		    		}
		    	}
		    }		    
		    
		    model.addAttribute("productList", productList);
		    Date consultTime = LoanConsultDateUtils.findTimeByLoanCode(loanInfo.getLoanCode());
		    if(ObjectHelper.isEmpty(consultTime)){
		    	Consult consult = consultService.get(consultId);
		    	consultTime=consult.getCreateTime();
		    }
		    model.addAttribute("consultTime", consultTime);
		}
		// 填写房产信息 查询婚姻状况
        if(ApplyInfoConstant.APPLY_INFO_HOUSE.equals(flag)){
            initParam.put("flag", ApplyInfoConstant.APPLY_INFO_CUSTOMER);
            ApplyInfoFlagEx tempApply = dataEntryService.getCustumerData(initParam);
            if(!ObjectHelper.isEmpty(tempApply.getLoanCustomer())){
               model.addAttribute("dictMarry", tempApply.getLoanCustomer().getDictMarry());
            }
        }
		// 省份获取
     	//	viewName    _loanFlowBank   银行卡资料专用省市
		if (StringUtils.equals("_loanFlowBank",viewName)) {
		    logger.info("获取银行所在省份。。。。。");
			List<CityInfo> provinceListCmb = cityInfoService.findProvinceCmb();
			model.addAttribute("provinceList", provinceListCmb);
		}else{
		  List<CityInfo> provinceList = cityInfoService.findProvince();
		  model.addAttribute("provinceList", provinceList);
		}
		model.addAttribute("workItem", workItem);
	    //自然人保证人遗留问题，-1表示共借人（已取消自然人保证人需求）
		launchView.setOneedition("-1");
        //解决get传参中文乱码
  		try {
  			launchView.getLoanInfo().setLoanCustomerName(URLDecoder.decode(launchView.getLoanInfo().getLoanCustomerName(), "UTF-8"));
  			launchView.getLoanCustomer().setCustomerName(URLDecoder.decode(launchView.getLoanCustomer().getCustomerName(),"UTF-8"));
  			workItem.setFlowName(URLDecoder.decode(workItem.getFlowName(),"UTF-8"));
  			workItem.setStepName(URLDecoder.decode(workItem.getStepName(),"UTF-8"));
  		} catch (UnsupportedEncodingException e) {
  			logger.error("旧版申请资料页面get传参中文处理接收参数异常", e);
  			e.printStackTrace();
  		}
		workItem.setBv(launchView);
		return "borrow/apply/" + viewName;
	}
	/**
	 * getApplyInfo_new适用于新版申请表
	 */
	@RequestMapping(value = "getApplyInfo_new")
	public String getApplyInfo_new(Model model,String flag,
			 String viewName,String customerCode,String consultId,WorkItemView workItem,LaunchView launchView) {
	    TokenUtils.removeToken(launchView.getDefTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    launchView.setDefTokenId(tokenMap.get("tokenId"));
	    launchView.setDefToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
	   	String loanCode = launchView.getLoanInfo().getLoanCode();
	   	logger.info("新版申请表查询时借款编号："+ loanCode);
		Map<String,String> initParam = new HashMap<String,String>();
		initParam.put("customerCode", customerCode);
		initParam.put("flag", flag);
		initParam.put("loanCode", loanCode);
		initParam.put("consultId", consultId);
		ApplyInfoFlagEx applyInfoEx = dataEntryService.getCustumerData_new(initParam);
		model.addAttribute("applyInfoEx", applyInfoEx);
		if(ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO.equals(flag)){
		    LoanInfo loanInfo = applyInfoEx.getLoanInfo();
		    String isBorrow = loanInfo.getIsBorrow();
		    User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
            Org org = user.getDepartment();
            OrgCache orgCache = OrgCache.getInstance();
            Org storeOrg = orgCache.get(org.getId());
            loanInfo.setLoanCustomerService(user.getId());
            loanInfo.setLoanCustomerServiceName(user.getName());
            loanInfo.setStoreProviceCode(storeOrg.getProvinceId());
            logger.info("门店省份ID："+loanInfo.getStoreProviceCode());
            loanInfo.setStoreCityCode(storeOrg.getCityId());
            logger.info("门店城市ID："+storeOrg.getCityId());
            if(StringUtils.isNotEmpty(loanInfo.getLoanManagerCode())){
                User loanManager = userManager.get(loanInfo.getLoanManagerCode());
                if(!ObjectHelper.isEmpty(loanManager)){
                    loanInfo.setLoanManagerName(loanManager.getName());
                }
            }
            applyInfoEx.setLoanInfo(loanInfo);
		    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(isBorrow);
		    
		    model.addAttribute("productList", productList);
		    Date consultTime = LoanConsultDateUtils.findTimeByLoanCode(loanInfo.getLoanCode());
		    if(ObjectHelper.isEmpty(consultTime)){
		    	Consult consult = consultService.get(consultId);
		    	consultTime=consult.getCreateTime();
		    }
		    model.addAttribute("consultTime", consultTime);
		}
		//自然人保证人页
		if(ApplyInfoConstant.NATURAL_GUARANTOR.equals(flag)){
			//查询产品信息
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(applyInfoEx.getLoanInfo().getIsBorrow());
			model.addAttribute("productList", productList);
			//咨询信息
			Consult consult = dataEntryService.get(consultId);
		    model.addAttribute("consultTime", consult.getCreateTime());
		}
		// 填写房产信息 查询婚姻状况
        if(ApplyInfoConstant.NEW_APPLY_INFO_HOUSE.equals(flag)){
            initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);
            ApplyInfoFlagEx tempApply = dataEntryService.getCustumerData_new(initParam);
            if(!ObjectHelper.isEmpty(tempApply.getLoanCustomer())){
               model.addAttribute("dictMarry", tempApply.getLoanCustomer().getDictMarry());
            }
        }
        
		if (ApplyInfoConstant.NEW_APPLY_INFO_BANK.equals(flag)) { //此if是银行卡信息专用省市
		    logger.info("获取银行所在省份。。。。。");
			List<CityInfo> provinceListCmb = cityInfoService.findProvinceCmb();
			model.addAttribute("provinceList", provinceListCmb);

			//查询简易借启用的开户行
			initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO);
            ApplyInfoFlagEx tempApply = dataEntryService.getCustumerData_new(initParam);
			if(tempApply.getLoanInfo() != null){
				if(LoanProductCode.PRO_JIAN_YI_JIE.equals(tempApply.getLoanInfo().getProductType())){
					List<JyjBorrowBankConfigure> jyjBorrowBankList = jyjBorrowBankConfigureService.queryList(1, LoanProductCode.PRO_JIAN_YI_JIE);
					model.addAttribute("borrowBankList", jyjBorrowBankList);
				}
				if(LoanProductCode.PRO_NONG_XIN_JIE.equals(tempApply.getLoanInfo().getProductType())){
					List<JyjBorrowBankConfigure> nyjBorrowBankList = jyjBorrowBankConfigureService.queryList(1, LoanProductCode.PRO_NONG_XIN_JIE);
					model.addAttribute("borrowBankList", nyjBorrowBankList);
				}
			}
		}else{
		  List<CityInfo> provinceList = cityInfoService.findProvince();
		  model.addAttribute("provinceList", provinceList);
		}
		//借么标识
		if(!ObjectHelper.isEmpty(applyInfoEx.getIsBorrow())){
			launchView.setIsBorrow(applyInfoEx.getIsBorrow());
	    }
		//解决get传参中文乱码
		try {
			launchView.getLoanInfo().setLoanCustomerName(StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanCustomerName()) ? URLDecoder.decode(launchView.getLoanInfo().getLoanCustomerName(),"UTF-8") : launchView.getLoanInfo().getLoanCustomerName());
			launchView.getLoanCustomer().setCustomerName(StringUtils.isNotEmpty(launchView.getLoanCustomer().getCustomerName()) ? URLDecoder.decode(launchView.getLoanCustomer().getCustomerName(),"UTF-8") : launchView.getLoanCustomer().getCustomerName());
			workItem.setFlowName(URLDecoder.decode(workItem.getFlowName(),"UTF-8"));
			workItem.setStepName(URLDecoder.decode(workItem.getStepName(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("新版申请资料页面get传参中文处理接收参数异常", e);
			e.printStackTrace();
		}
        workItem.setBv(launchView);
        model.addAttribute("workItem", workItem);
		return "borrow/apply/" + viewName;
	}
	/**
	 * getApplyInfo_new_storeView适用于新版申请表的门店复核页面
	 */
	@RequestMapping(value = "getApplyInfo_new_storeView")
	public String getApplyInfo_new_storeView(Model model,String flag,
			 String viewName,String customerCode,String consultId,String loanCode,WorkItemView workItem,LaunchView launchView) {
		Map<String,String> initParam = new HashMap<String,String>();
		initParam.put("customerCode", customerCode);
		initParam.put("flag", flag);
		initParam.put("loanCode", loanCode);
		initParam.put("consultId", consultId);
		ApplyInfoFlagEx applyInfoEx = dataEntryService.getCustumerData_new(initParam);
		model.addAttribute("applyInfoEx", applyInfoEx);
		if(ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO.equals(flag)){
		    LoanInfo loanInfo = applyInfoEx.getLoanInfo();
		    User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
            Org org = user.getDepartment();
            OrgCache orgCache = OrgCache.getInstance();
            Org storeOrg = orgCache.get(org.getId());
            loanInfo.setLoanCustomerService(user.getId());
            loanInfo.setLoanCustomerServiceName(user.getName());
            loanInfo.setStoreProviceCode(storeOrg.getProvinceId());
            logger.info("门店省份ID："+loanInfo.getStoreProviceCode());
            loanInfo.setStoreCityCode(storeOrg.getCityId());
            logger.info("门店城市ID："+storeOrg.getCityId());
            if(StringUtils.isNotEmpty(loanInfo.getLoanManagerCode())){
                User loanManager = userManager.get(loanInfo.getLoanManagerCode());
                if(!ObjectHelper.isEmpty(loanManager)){
                    loanInfo.setLoanManagerName(loanManager.getName());
                }
            }
            applyInfoEx.setLoanInfo(loanInfo);
		    List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(applyInfoEx.getLoanInfo().getIsBorrow());
		    
		    model.addAttribute("productList", productList);
		    Date consultTime = LoanConsultDateUtils.findTimeByLoanCode(loanInfo.getLoanCode());
		    model.addAttribute("consultTime", consultTime);
		}
		//自然人保证人页
		if(ApplyInfoConstant.NATURAL_GUARANTOR.equals(flag)){
			//查询产品信息
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(applyInfoEx.getLoanInfo().getIsBorrow());
			model.addAttribute("productList", productList);
			//咨询信息
			Consult consult = dataEntryService.get(consultId);
		    model.addAttribute("consultTime", consult.getCreateTime());
		}
		// 填写房产信息 查询婚姻状况
        if(ApplyInfoConstant.NEW_APPLY_INFO_HOUSE.equals(flag)){
            initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);
            ApplyInfoFlagEx tempApply = dataEntryService.getCustumerData_new(initParam);
            if(!ObjectHelper.isEmpty(tempApply.getLoanCustomer())){
               model.addAttribute("dictMarry", tempApply.getLoanCustomer().getDictMarry());
            }
        }
        
		//省份获取
		if (ApplyInfoConstant.NEW_APPLY_INFO_BANK.equals(flag)) { //此if是银行卡信息专用省市
		    logger.info("获取银行所在省份。。。。。");
			List<CityInfo> provinceListCmb = cityInfoService.findProvinceCmb();
			model.addAttribute("provinceList", provinceListCmb);
			
			//查询简易借启用的开户行
			initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO);
            ApplyInfoFlagEx tempApply = dataEntryService.getCustumerData_new(initParam);
            if(tempApply.getLoanInfo() != null){
				if(LoanProductCode.PRO_JIAN_YI_JIE.equals(tempApply.getLoanInfo().getProductType())){
					List<JyjBorrowBankConfigure> jyjBorrowBankList = jyjBorrowBankConfigureService.queryList(1, LoanProductCode.PRO_JIAN_YI_JIE);
					model.addAttribute("borrowBankList", jyjBorrowBankList);
				}
				if(LoanProductCode.PRO_NONG_XIN_JIE.equals(tempApply.getLoanInfo().getProductType())){
					List<JyjBorrowBankConfigure> nyjBorrowBankList = jyjBorrowBankConfigureService.queryList(1, LoanProductCode.PRO_NONG_XIN_JIE);
					model.addAttribute("borrowBankList", nyjBorrowBankList);
				}
			}
		}else{
		  List<CityInfo> provinceList = cityInfoService.findProvince();
		  model.addAttribute("provinceList", provinceList);
		}
		//借么标识
		if(!ObjectHelper.isEmpty(applyInfoEx.getIsBorrow())){
			launchView.setIsBorrow(applyInfoEx.getIsBorrow());
	    }
		//解决get传参中文乱码
		try {
			workItem.setFlowName(URLDecoder.decode(workItem.getFlowName(),"UTF-8"));
			workItem.setStepName(URLDecoder.decode(workItem.getStepName(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("新版门店复核页面get传参中文处理接收参数异常", e);
			e.printStackTrace();
		}
		//设置影响地址
		String imageUrl = imageService.getImageUrl(workItem.getStepName(),launchView.getLoanCode());
		launchView.setImageUrl(imageUrl);
        workItem.setBv(launchView);
        model.addAttribute("workItem", workItem);
		return "borrow/storereview/" + viewName;
	}
	
	/**
	 * 2015年12月3日
	 * By zhanghao
	 * @param model
	 * @param viewName
	 * @param loanCode
	 * @param workItem
	 * @param launchView
	 * @return reconsider.jsp
	 */
	@RequestMapping(value = "getAllApplyInfo")
    public String getAllApplyInfo(Model model,
             String viewName,String loanCode,WorkItemView workItem,LaunchView launchView) {
	    logger.info("加载所有的申请信息！！");
        ApplyInfoFlagEx applyInfoEx = dataEntryService.getAllInfoByLoanCode(loanCode);
        model.addAttribute("applyInfoEx", applyInfoEx);
        BeanUtils.copyProperties(applyInfoEx, launchView);
        String dictLoanUser=DictCache.getInstance().getDictLabel("jk_loan_use",launchView.getLoanInfo().getDictLoanUse());
        launchView.getLoanInfo().setDictLoanUserLabel(dictLoanUser);
        workItem.setBv(launchView);
        
        model.addAttribute("workItem", workItem);
        return "borrow/reconsider/" + viewName;
    }
	
	/**
	 * 2015年12月3日
	 * By zhangping
	 * @param model
	 * @param viewName
	 * @param parentIndex
	 * @param currIndex
	 * @return apply.jsp
	 */
	@RequestMapping(value = "additionItem")
	public String additionItem(Model model, String viewName,
			String parentIndex, String currIndex,String dictMarry) {
		model.addAttribute("parentIndex", parentIndex);
		model.addAttribute("currIndex", currIndex);
		// 省份获取
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("dictMarry", dictMarry);
		return "borrow/apply/" + viewName;
	}
	
	
	/**
	 * 2015年12月3日
	 * By zhangping
	 * @param model
	 * @param viewName
	 * @param parentIndex
	 * @param currIndex
	 * @return apply.jsp
	 */
	@RequestMapping(value = "oneedition")
	public String oneedition(Model model, String viewName,
			String parentIndex, String currIndex,String dictMarry,String oneedition,String isBorrow) {
		model.addAttribute("parentIndex", parentIndex);
		model.addAttribute("currIndex", currIndex);
		model.addAttribute("oneedition", oneedition);
		// 省份获取
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("dictMarry", dictMarry);
		model.addAttribute("isBorrow", isBorrow);
		return "borrow/apply/" + viewName;
	}


	/**
	 * 2015年12月3日 此方法适用于旧版申请表，新版申请表请参见delAdditionItem_new方法
	 * By zhangping
	 * @param model
	 * @param delType
	 * @param tagId
	 * @return 字符串true
	 */
	@RequestMapping(value = "delAdditionItem")
	@ResponseBody
	public String delAdditionItem(Model model, String delType, String tagId) {
		dataEntryService.deleteItem(delType, tagId);
		return BooleanType.TRUE;
	}
	/**
	 * 此方法适用于新版申请表
	 */
	@RequestMapping(value = "delAdditionItem_new")
	@ResponseBody
	public String delAdditionItem_new(Model model, String delType, String tagId) {
		dataEntryService.deleteItem_new(delType, tagId);
		return BooleanType.TRUE;
	}
    
	/**
	 *动态获取本人关系 
	 *by zhanghao 
	 *Create In 2015年1月2日
	 *@param model
	 *@param  parentValue
	 *@return List<Dict> 
	 *
	 */
	@RequestMapping(value = "getRelationDict")
	@ResponseBody
	public List<Dict> getRelationDict(Model model,Integer parentValue){
	    DictCache dictCache = new DictCache();
	    List<Dict> dicts  = null;
	    if(!ObjectHelper.isEmpty(parentValue)){
	        dicts = dictCache.getListByType(ContractConstant.SUB_RELATION_TYPE[parentValue]);
	        for(int i=0;i<dicts.size();i++){
	            if(ContractConstant.MATE.equals(dicts.get(i).getLabel())){
	                dicts.remove(i);
	                i--;
	            }
	        }
	    }
        return dicts;
	}
	
	/**
	 *手机号码重复检测
	 *@author zhanghao
	 *@Create In 2016年2月16日
	 *@param model 
	 *@param loanCode
	 *@param customerCode
	 *@param consultId
	 *@param phoneNum
	 *@param  currType
	 *@return Map<String,Object> 
	 * 
	 */
	@RequestMapping(value= "phoneNumCheck")
	@ResponseBody
	public Map<String,Object> phoneNumCheck(Model model,
	        String loanCode,String customerCode,
	        String consultId,String phoneNum,String currType){
	    boolean result = true;
	    Map<String,String> initParam = new HashMap<String,String>();
	    Map<String,Object> resultMap = new HashMap<String,Object>();
	    resultMap.put("result", result);
	    initParam.put("customerCode", customerCode);
	    initParam.put("loanCode", loanCode);
	    initParam.put("consultId", consultId);
	    logger.info("电话号码重复性检测，当前人员类型："+currType);
	    if(ApplyInfoConstant.MAIN_BORROWER.equals(currType)){
	        // 判断主借人的手机号跟共借人的手机号是否相同
	          result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum,ApplyInfoConstant.APPLY_INFO_COBORROWER);
            // 判断主借人跟配偶的手机号是否重复
            if(result){
              result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_MATE); 
            }
            // 判断主借人跟配偶的手机号是否重复
            if(result){
              result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CONTACT);   
            }
        // 共借人手机号码查重
	    }else if(ApplyInfoConstant.COBO_BORROWER.equals(currType)){
	        // 判断共借人的手机号跟主借人的手机号码是否重复
	        result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CUSTOMER);   
            /*// 判断共借人跟配偶的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_MATE);  
            }*/
            // 判断共借人跟联系人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CONTACT);   
            }
 	    }else if(ApplyInfoConstant.MAIN_CONTACT.equals(currType)){
 	        // 判断主借人的联系人的手机号跟主借人的手机号码是否重复
            result = this.judgePhoneNumDiff(initParam,resultMap, phoneNum, ApplyInfoConstant.APPLY_INFO_CUSTOMER);   
            // 判断主借人的联系人跟配偶的手机号是否重复
           /* if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_MATE);  
            }*/
            // 判断主借人的联系人跟共借人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_COBORROWER);   
            }
        }else if(ApplyInfoConstant.COBO_CONTACT.equals(currType)){
            // 判断共借人的联系人的手机号跟主借人的手机号码是否重复
            result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CUSTOMER);   
            // 判断共借人的联系人跟配偶的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_MATE);  
            }
            // 判断共借人的联系人跟主借人联系人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CONTACT);   
            }
        }else if(ApplyInfoConstant.MATE.equals(currType)){
            // 判断配偶的手机号跟主借人的手机号码是否重复
            result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CUSTOMER);   
          /*  // 判断配偶跟共借人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_COBORROWER);  
            }
            // 判断配偶跟主借人联系人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiff(initParam,resultMap,phoneNum, ApplyInfoConstant.APPLY_INFO_CONTACT);   
            }*/
        }   
	    return resultMap;
	}
	// 判断五类人（主、共借人，主、共借人的联系人，配偶）的手机号码是否重复
	private boolean judgePhoneNumDiff(Map<String,String> initParam,Map<String,Object> resultMap,String phoneNum,String flag){
	    boolean result = true;
	    ApplyInfoFlagEx applyInfo = null;
        String curPhone;
	    initParam.put("flag",flag);
	    if(ApplyInfoConstant.APPLY_INFO_COBORROWER.equals(flag)){
	        applyInfo = dataEntryService.getCustumerData(initParam);
            List<LoanCoborrower> coborrowers = applyInfo.getLoanCoborrower();
            for(LoanCoborrower coborrower :coborrowers){
                if(!ObjectHelper.isEmpty(coborrower)){
                  curPhone = coborrower.getCoboMobile();
                  if(phoneNum.equals(curPhone)){
                     result = false;
                     resultMap.put("result", result);
                     resultMap.put("resultMsg", ApplyInfoConstant.COBO_BORROWER_PHONE_MSG);
                     break;
                  }else{
                      curPhone = coborrower.getCoboMobile2();
                      if(phoneNum.equals(curPhone)){
                          result = false;
                          resultMap.put("result", result);
                          resultMap.put("resultMsg", ApplyInfoConstant.COBO_BORROWER_PHONE_MSG);
                          break;
                       }else{
                           List<Contact> contacts = coborrower.getCoborrowerContactList();  
                           for(Contact contact:contacts){
                               if(ObjectHelper.isEmpty(contact)){
                                   curPhone = contact.getContactMobile();
                                   if(phoneNum.equals(curPhone)){
                                       result = false;
                                       resultMap.put("result", result);
                                       resultMap.put("resultMsg", ApplyInfoConstant.COBO_CONTACT_PHONE_MSG);
                                       break;
                                   }
                               }
                           }
                       }
                  }
                }
            }  
	    }else if(ApplyInfoConstant.APPLY_INFO_MATE.equals(flag)){
	        applyInfo = dataEntryService.getCustumerData(initParam);
            LoanMate loanMate = applyInfo.getLoanMate();
            if(!ObjectHelper.isEmpty(loanMate)){
                curPhone = loanMate.getMateTel();
                if(phoneNum.equals(curPhone)){
                    result = false;
                    resultMap.put("result", result);
                    resultMap.put("resultMsg", ApplyInfoConstant.MATE_PHONE_MSG);
                }  
            } 
	    }else if(ApplyInfoConstant.APPLY_INFO_CONTACT.equals(flag)){
	        applyInfo = dataEntryService.getCustumerData(initParam);
            List<Contact> contacts = applyInfo.getCustomerContactList();
            if(!ObjectHelper.isEmpty(contacts)){
                for(Contact contact:contacts){
                    curPhone = contact.getContactMobile();
                    if(phoneNum.equals(curPhone)){
                        result = false;
                        resultMap.put("result", result);
                        resultMap.put("resultMsg", ApplyInfoConstant.COBO_BORROWER_PHONE_MSG);
                        break;
                    } 
                }
            } 
	    }else if(ApplyInfoConstant.APPLY_INFO_CUSTOMER.equals(flag)){
	        applyInfo = dataEntryService.getCustumerData(initParam);
            LoanCustomer loanCustomer = applyInfo.getLoanCustomer();
            if(!ObjectHelper.isEmpty(loanCustomer)){
                curPhone = loanCustomer.getCustomerPhoneFirst();
                if(phoneNum.equals(curPhone)){
                    result = false;
                    resultMap.put("result", result);
                    resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_PHONE_MSG);
                }else{
                    curPhone = loanCustomer.getCustomerPhoneSecond();  
                    if(phoneNum.equals(curPhone)){
                        result = false;
                        resultMap.put("result", result);
                        resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_PHONE_MSG);
                    }
                }
            }  
	    }
	    return result;
	}
	
	/**
	 *手机号码重复检测
	 *@author zhanghao
	 *@Create In 2016年2月16日
	 *@param model 
	 *@param loanCode
	 *@param customerCode
	 *@param consultId
	 *@param phoneNum
	 *@param  currType
	 *@return Map<String,Object> 
	 * 
	 */
	@RequestMapping(value= "phoneNumCheckNew")
	@ResponseBody
	public Map<String,Object> phoneNumCheckNew(Model model, String loanCode, String customerCode, String consultId,String phoneNum,String currType){
		boolean result = true;
	    Map<String,String> initParam = new HashMap<String,String>();
	    Map<String,Object> resultMap = new HashMap<String,Object>();
	    resultMap.put("result", result);
	    initParam.put("customerCode", customerCode);
	    initParam.put("loanCode", loanCode);
	    initParam.put("consultId", consultId);
	    logger.info("电话号码重复性检测，当前人员类型："+currType);
	    //主借人
	    if(ApplyInfoConstant.MAIN_BORROWER.equals(currType)){
	        // 判断主借人的手机号跟共借人的手机号是否相同
	    	result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum,ApplyInfoConstant.NATURAL_GUARANTOR);
            // 判断主借人跟配偶的手机号是否重复
	    	if(result){
	    		result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_MATE);
            }
            // 判断主借人跟联系人的手机号是否重复
            if(result){
            	result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CONTACT);   
            }
        // 共借人手机号码查重
	    }else if(ApplyInfoConstant.COBO_BORROWER.equals(currType)){
	        // 判断共借人的手机号跟主借人的手机号码是否重复
	    	result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);   
            // 判断共借人跟联系人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CONTACT);   
            }
 	    }else if(ApplyInfoConstant.MAIN_CONTACT.equals(currType)){
 	        // 判断主借人的联系人的手机号跟主借人的手机号码是否重复
            result = this.judgePhoneNumDiffNew(initParam,resultMap, phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);   
            // 判断主借人的联系人跟共借人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NATURAL_GUARANTOR);   
            }
        }else if(ApplyInfoConstant.COBO_CONTACT.equals(currType)){
            // 判断共借人的联系人的手机号跟主借人的手机号码是否重复
            result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);   
            // 判断共借人的联系人跟配偶的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_MATE);  
            }
            // 判断共借人的联系人跟主借人联系人的手机号是否重复
            if(result){
               result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CONTACT);   
            }
        }else if(ApplyInfoConstant.MATE.equals(currType)){
            // 判断配偶的手机号跟主借人的手机号码是否重复
            result = this.judgePhoneNumDiffNew(initParam,resultMap,phoneNum, ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);   
        }   
	    return resultMap;
	}
	
	// 判断五类人（主、共借人，主、共借人的联系人，配偶）的手机号码是否重复
	private boolean judgePhoneNumDiffNew(Map<String,String> initParam,Map<String,Object> resultMap,String phoneNum,String flag){
	    boolean result = true;
	    ApplyInfoFlagEx applyInfo = null;
        String curPhone;
	    initParam.put("flag",flag);
	    if(ApplyInfoConstant.NATURAL_GUARANTOR.equals(flag)){
	        applyInfo = dataEntryService.getCustumerData_new(initParam);
            List<LoanCoborrower> coborrowers = applyInfo.getLoanCoborrower();
            for(LoanCoborrower coborrower :coborrowers){
                if(!ObjectHelper.isEmpty(coborrower)){
                	curPhone = coborrower.getCoboMobile();
                	if(phoneNum.equals(curPhone)){
						result = false;
						resultMap.put("result", result);
						resultMap.put("resultMsg", ApplyInfoConstant.NATURAL_GUARANTOR_PHONE_MSG);
						break;
                	}else{
                		List<Contact> contacts = coborrower.getCoborrowerContactList();  
                		for(Contact contact:contacts){
                			if(ObjectHelper.isEmpty(contact)){
                				curPhone = contact.getContactMobile();
                				if(phoneNum.equals(curPhone)){
                					result = false;
                					resultMap.put("result", result);
                					resultMap.put("resultMsg", ApplyInfoConstant.NATURAL_GUARANTOR_CONTACT_PHONE_MSG);
                					break;
                				}
                			}
                		}
                	}
                }
            }  
	    }else if(ApplyInfoConstant.NEW_APPLY_INFO_MATE.equals(flag)){
	    	initParam.put("flag",ApplyInfoConstant.NEW_APPLY_INFO_CONTACT);
	    	applyInfo = dataEntryService.getCustumerData_new(initParam);
	    	LoanMate loanMate = applyInfo.getLoanMate();
	    	if(!ObjectHelper.isEmpty(loanMate)){
	    		curPhone = loanMate.getMateTel();
	    		if(phoneNum.equals(curPhone)){
	    			result = false;
	    			resultMap.put("result", result);
	    			resultMap.put("resultMsg", ApplyInfoConstant.MAIN_CONTACT_MATE_PHONE_MSG_NEW);
	    		}  
	    	} 
	    }else if(ApplyInfoConstant.NEW_APPLY_INFO_CONTACT.equals(flag)){
	    	applyInfo = dataEntryService.getCustumerData_new(initParam);
            List<Contact> contacts = applyInfo.getCustomerContactList();
            if(!ObjectHelper.isEmpty(contacts)){
            	for(Contact contact:contacts){
            		curPhone = contact.getContactMobile();
                    if(phoneNum.equals(curPhone)){
                        result = false;
                        resultMap.put("result", result);
                        resultMap.put("resultMsg", ApplyInfoConstant.MAIN_CONTACT_PHONE_MSG_NEW);
                        break;
                    } 
                }
            } 
	    }else if(ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER.equals(flag)){
	        applyInfo = dataEntryService.getCustumerData_new(initParam);
            LoanCustomer loanCustomer = applyInfo.getLoanCustomer();
            if(!ObjectHelper.isEmpty(loanCustomer)){
                curPhone = loanCustomer.getCustomerPhoneFirst();
                if(phoneNum.equals(curPhone)){
                    result = false;
                    resultMap.put("result", result);
                    resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_PHONE_MSG_NEW);
                }else{
                    curPhone = loanCustomer.getCustomerPhoneSecond();  
                    if(phoneNum.equals(curPhone)){
                        result = false;
                        resultMap.put("result", result);
                        resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_PHONE_MSG_NEW);
                    }
                }
            }  
	    }
	    return result;
	}
	
	@RequestMapping(value= "certNumCheck")
    @ResponseBody
	public Map<String,Object> certNumCheck(String certType,String certNum,String inputType, String loanCode,String customerCode,
            String consultId){
	    boolean result = true;
        Map<String,String> initParam = new HashMap<String,String>();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result", result);
        initParam.put("customerCode", customerCode);
        initParam.put("loanCode", loanCode);
        initParam.put("consultId", consultId); 
        logger.info("证件号码重复性检测，当前人员类型："+inputType);
        if(ApplyInfoConstant.MAIN_BORROWER.equals(inputType)){
              result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COBORROWER);
           if(result){
              result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_MATE);  
            }
        }else if(ApplyInfoConstant.MATE.equals(inputType)){
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_CUSTOMER);
            /*if(result){
                result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COBORROWER);  
            } */
        }else if(ApplyInfoConstant.COBO_BORROWER.equals(inputType)){
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_CUSTOMER);
            /*
            if(result){
                result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COBORROWER);  
            }*/
        }
        //自然人保证人 校验 
        else if (ApplyInfoConstant.COBO_BORROWER_PERSON.equals(inputType)){
          //申请金额和配偶的关系
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_PERSION);
	      //校验身份证号是不是 22--55周岁
            if(result){
	    	    result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO);
	       }
        
        }
        //	//产品类型为老板借 或小微企业借 且申请额度在30W以上（不含） 法人代表姓名，法人代表身份证号，法人代表手机号，企业邮箱， 必填   // 其他可不填
  /*  
        else if (ApplyInfoConstant.COBO_LOANFLOW_COMPANY.equals(inputType)){
          //
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COMPANY);
        }*/
        //法人身份证号码 egalperson
        else if (ApplyInfoConstant.COBO_EGALPERSON.equals(inputType)){
            //
              result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.COBO_EGALPERSON);
          }
        else if(ApplyInfoConstant.COMLEGAL_MAN.equals(inputType)){
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COMPANY);
        } 
        else if(ApplyInfoConstant.COMLEGAL_MANNUM.equals(inputType)){
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COMPANY);
        } 
        else if(ApplyInfoConstant.COMLEGAL_SMANMOBLIE.equals(inputType)){
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COMPANY);
        }
        else if(ApplyInfoConstant.COMLEGALMANCOME_MAIL.equals(inputType)){
            result = this.judgeCertNumDiff(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_COMPANY);
        }
        
        return resultMap;
	    
	}
	
	/**
	 *  判断五类人（主、共借人，主、共借人的联系人，配偶）的手机号码是否重复
	 * 2016年5月19日
	 * By 王彬彬
	 * @param initParam
	 * @param resultMap
	 * @param certNum
	 * @param certType
	 * @param flag
	 * @return
	 */
    private boolean judgeCertNumDiff(Map<String,String> initParam,Map<String,Object> resultMap,String certNum,String certType,String flag){
        boolean result = true;
        ApplyInfoFlagEx applyInfo = null;
        String curCertType;
        String curCertNum;
        initParam.put("flag",flag);
        if(ApplyInfoConstant.APPLY_INFO_CUSTOMER.equals(flag)){       // 主借人
            applyInfo = dataEntryService.getCustumerData(initParam); 
            LoanCustomer loanCustomer = applyInfo.getLoanCustomer();
            curCertType = loanCustomer.getDictCertType();
            curCertNum = loanCustomer.getCustomerCertNum();
            if(certType.equals(curCertType) && certNum.equals(curCertNum)){
                result = false;
                resultMap.put("result", result);
                resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_CERTNUM_MSG);  
            }
        }else if(ApplyInfoConstant.APPLY_INFO_MATE.equals(flag)){   // 配偶
            applyInfo = dataEntryService.getCustumerData(initParam); 
            LoanMate loanMate = applyInfo.getLoanMate();
            curCertType = loanMate.getDictCertType();
            curCertNum = loanMate.getMateCertNum();
            if(certType.equals(curCertType) && certNum.equals(curCertNum)){
                result = false;
                resultMap.put("result", result);
                resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_CERTNUM_MSG);  
            }
        }else if(ApplyInfoConstant.APPLY_INFO_COBORROWER.equals(flag)){  // 共借人
            applyInfo = dataEntryService.getCustumerData(initParam); 
            List<LoanCoborrower> coborrowers = applyInfo.getLoanCoborrower();
            for(LoanCoborrower coborrower:coborrowers){
                curCertType = coborrower.getDictCertType();
                curCertNum = coborrower.getCoboCertNum();
                if(certType.equals(curCertType) && certNum.equals(curCertNum)){
                    result = false;
                    resultMap.put("result", result);
                    resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_CERTNUM_MSG);  
                    break;
                }
            }
        }else if(ApplyInfoConstant.APPLY_INFO_PERSION.equals(flag)){ 
        	// 配偶  和自然人保证人关系校验
          	initParam.put("flag","3");
        	applyInfo = dataEntryService.getCustumerData(initParam); 
        	LoanInfo loanInfo = applyInfo.getLoanInfo();
            //大于15W  且小于等于 30W  配偶不能做第三方保证人
            BigDecimal a= 	loanInfo.getLoanApplyAmount();
            
            BigDecimal b=BigDecimal.valueOf(150000);
        	if(a != null && a.compareTo(b)==1){
        	       initParam.put("flag","2");
                   applyInfo = dataEntryService.getCustumerData(initParam); 
                   LoanMate loanMate = applyInfo.getLoanMate();
                   curCertType = loanMate.getDictCertType();
                   curCertNum = loanMate.getMateCertNum();
                   if(certType.equals(curCertType) && certNum.equals(curCertNum)){
                       result = false;
                       resultMap.put("result", result);
                       resultMap.put("idNum", "1");
                       resultMap.put("resultMsg",ApplyInfoConstant.REPEAT_PERSION);  
                   }
    	    }
            //身份证22-55校验
           }else if(ApplyInfoConstant.APPLY_INFO.equals(flag)){ 
               //校验身份证输入年龄验证 
               String cardNumber =certNum;
               String birthDay = this.getBirthDateFromCard(cardNumber);
               Date birthDate = null;
               try {
                   birthDate = DateUtils.parseDate(birthDay, "yyyy-MM-dd");
                   result = validateAge(birthDate);
                   if(!result){//如果不符合条件，给出提示
                       resultMap.put("result", result);
                       resultMap.put("idNum", "1");
                       resultMap.put("resultMsg",ApplyInfoConstant.COBORROWER_NEEDED_PERSION); 
                   }
   	            } catch (ParseException e) {
   	            	   result = false;
   	                   resultMap.put("result", result);
   	                    resultMap.put("idNum", "1");
   	                   resultMap.put("resultMsg",ApplyInfoConstant.COBORROWER_NEEDED_PERSION); 
   	            }
        //	   
        }else if(ApplyInfoConstant.APPLY_INFO_COMPANY.equals(flag)){ 
        	//查询申请信息
        	initParam.put("flag","3");
        	applyInfo = dataEntryService.getCustumerData(initParam); 
        	LoanInfo loanInfo = applyInfo.getLoanInfo();
            BigDecimal a= 	loanInfo.getLoanApplyAmount();
            BigDecimal b=BigDecimal.valueOf(300000);
            //产品类型  是老板借  A005  A006或者小微企业借  
             String type=     loanInfo.getProductType();
            if( ("A005".equals(type) ||  "A006".equals(type) )  &&  (a.compareTo(b)==1) ==true ){
            	  if("".equals(certNum)){
           	           result = false;
	                   resultMap.put("result", result);
	                   resultMap.put("resultMsg","必填信息"); 
            	  }
            }
        }
		    else if(ApplyInfoConstant.COBO_EGALPERSON.equals(flag)){ 
		    	//查询主借人 的身份证号  手机号1
		    	 initParam.put("flag","1");
		    	  applyInfo = dataEntryService.getCustumerData(initParam); 
		          LoanCustomer loanCustomer = applyInfo.getLoanCustomer();
		    	 //主借人 证件号码
		        String   curtNum = loanCustomer.getCustomerCertNum();
		        //主借人 姓名
		        String   customerName=loanCustomer.getCustomerName();
		        
		         //主借人 手机好1
		        String  iphoneOne = loanCustomer.getCustomerPhoneFirst();
		        
		    	//查询配偶的身份证号 手机号
		        initParam.put("flag","2");
		          applyInfo = dataEntryService.getCustumerData(initParam); 
		          LoanMate loanMate = applyInfo.getLoanMate();
		          //配偶姓名
		         String    mateName  =loanMate.getMateName();
		        //配偶身份证号码  
			       String mateNum=    loanMate.getMateCertNum();
			       //配偶手机号
			       String mateIphone= loanMate.getMateTel();
			       
		       if(certNum.equals(curtNum)){
	      	       result = false;
                   resultMap.put("result", result);
                   resultMap.put("resultMsg","和主借人身份证号相同"); 
             
                   resultMap.put("customerName", customerName);
                   resultMap.put("iphoneOne", iphoneOne);
                   
		       }else if(certNum.equals(mateNum)){
		    	   result = false;
                   resultMap.put("result", result);
                   resultMap.put("resultMsg","和配偶身份证号码相同"); 
                   
                   resultMap.put("customerName", mateName);
                   resultMap.put("iphoneOne", mateIphone);
		       }
		    }
        
        
        return result;
    }
	@RequestMapping(value= "certNumCheck_new")
    @ResponseBody
	public Map<String,Object> certNumCheck_new(String certType,String certNum,String inputType, String loanCode,String customerCode,
            String consultId){
	    boolean result = true;
        Map<String,String> initParam = new HashMap<String,String>();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result", result);
        initParam.put("customerCode", customerCode);
        initParam.put("loanCode", loanCode);
        initParam.put("consultId", consultId); 
        logger.info("证件号码重复性检测，当前人员类型："+inputType);
        if(ApplyInfoConstant.MAIN_BORROWER.equals(inputType)){
        	  //主借人的身份证号和自然人保证人的身份证号比较
              result = this.judgeCertNumDiff_new(initParam, resultMap, certNum, certType, ApplyInfoConstant.NATURAL_GUARANTOR);
           if(result){
        	  //主借人的身份证号和联系人的配偶身份证号比较
              result = this.judgeCertNumDiff_new(initParam, resultMap, certNum, certType, ApplyInfoConstant.NEW_APPLY_INFO_CONTACT);  
            }
        }else if(ApplyInfoConstant.MATE.equals(inputType)){
        	//联系人的配偶身份证号和主借人的身份证号比较
            result = this.judgeCertNumDiff_new(initParam, resultMap, certNum, certType, ApplyInfoConstant.APPLY_INFO_CUSTOMER);
        }else if(ApplyInfoConstant.COBO_BORROWER.equals(inputType)){ //对于新版申请表而言就是自然人保证人
        	//自然人保证人的身份证号和主借人的身份证号比较
            result = this.judgeCertNumDiff_new(initParam, resultMap, certNum, certType, ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);
            //校验自然人保证人年龄22到50岁之间
            result = this.judgeCertNumDiff_new(initParam, resultMap, certNum, certType, ApplyInfoConstant.NATURAL_GUARANTOR_CERT_AGE);
        }
        return resultMap;
	    
	}
	
    private boolean judgeCertNumDiff_new(Map<String,String> initParam,Map<String,Object> resultMap,String certNum,String certType,String flag){
        boolean result = true;
        ApplyInfoFlagEx applyInfo = null;
        String curCertType;
        String curCertNum;
        initParam.put("flag",flag);
        if(ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER.equals(flag)){       // 主借人
            applyInfo = dataEntryService.getCustumerData_new(initParam); 
            LoanCustomer loanCustomer = applyInfo.getLoanCustomer();
            curCertType = loanCustomer.getDictCertType();
            curCertNum = loanCustomer.getCustomerCertNum();
            if(certType.equals(curCertType) && certNum.equals(curCertNum)){
                result = false;
                resultMap.put("result", result);
                resultMap.put("resultMsg", ApplyInfoConstant.MAIN_BORROWER_CERTNUM_MSG_NEW);  
            }
        }else if(ApplyInfoConstant.NEW_APPLY_INFO_CONTACT.equals(flag)){   // 配偶
            applyInfo = dataEntryService.getCustumerData_new(initParam); 
            LoanMate loanMate = applyInfo.getLoanMate();
            if(loanMate!=null){
            	curCertType = loanMate.getDictCertType();
            	curCertNum = loanMate.getMateCertNum();
            	if(certType.equals(curCertType) && certNum.equals(curCertNum)){
            		result = false;
            		resultMap.put("result", result);
            		resultMap.put("resultMsg", ApplyInfoConstant.CONTACT_CERTNUM_MSG_NEW);  
            	}
            }
        }else if(ApplyInfoConstant.NATURAL_GUARANTOR.equals(flag)){
            applyInfo = dataEntryService.getCustumerData_new(initParam); 
            List<LoanCoborrower> coborrowers = applyInfo.getLoanCoborrower();
            for(LoanCoborrower coborrower:coborrowers){
                curCertType = coborrower.getDictCertType();
                curCertNum = coborrower.getCoboCertNum();
                if(certType.equals(curCertType) && certNum.equals(curCertNum)){
                    result = false;
                    resultMap.put("result", result);
                    resultMap.put("resultMsg", ApplyInfoConstant.NATURAL_GUARANTOR_CERTNUM_MSG_NEW);  
                    break;
                }
            }
        }else if(ApplyInfoConstant.NATURAL_GUARANTOR_CERT_AGE.equals(flag)){ //身份证22-55校验
            //校验身份证输入年龄验证 
            String cardNumber =certNum;
            String birthDay = this.getBirthDateFromCard(cardNumber);
            Date birthDate = null;
			try {
				birthDate = DateUtils.parseDate(birthDay, "yyyy-MM-dd");
				result = validateAge(birthDate);
				if (!result) {// 如果不符合条件，给出提示
					resultMap.put("result", result);
					resultMap.put("idNum", "1");
					resultMap.put("resultMsg", ApplyInfoConstant.COBORROWER_NEEDED_PERSION);
				}
			} catch (ParseException e) {
				result = false;
				resultMap.put("result", result);
				resultMap.put("idNum", "1");
				resultMap.put("resultMsg", ApplyInfoConstant.COBORROWER_NEEDED_PERSION);
			}
	     }
        return result;
    }
    /**
     *根据身份证号获取客户的出生日期
     *@author zhangho
     *@create in 2016年1月29日 
     *@param cardNumber 
     *@return  String 
     */
    private String getBirthDateFromCard(String cardNumber){
        String card = cardNumber.trim();
        String year;
        String month;
        String day;
        if(card.length()==18){
            year = card.substring(6, 10);
            month = card.substring(10, 12);
            day = card.substring(12, 14);
        }else{
            year = card.substring(6, 8);
            month = card.substring(8, 10);
            day = card.substring(10, 12);
            year = "19"+year;
        }
        if(month.length()==1){
            month="0"+month;
        }
        if(day.length()==1){
            day="0"+day;
        }
        return year+"-"+month+"-"+day;
    }
	
    private boolean validateAge(Date birthday){
    	Calendar c = Calendar.getInstance();
    	c.setTime(birthday);//出生日期
    	Calendar c1 = (Calendar) c.clone();
    	Calendar c2 = (Calendar) c.clone();
    	c1.add(Calendar.YEAR, 22);//获取22岁生日
    	c2.add(Calendar.YEAR, 51);//获取51岁生日
    	long start = c1.getTimeInMillis();
    	long end = c2.getTimeInMillis();
    	long time = new Date().getTime();
    	if(time >= start && time < end){//满22岁，且不到51岁
    		return true;
    	}
    	return false;
    }
    /**
     * 判断身份证号与主借人，配偶的是否相同，相同显示对应的姓名和手机号
     * @param loanCode
     * @param corporateCertNum
     * @return
     */
    @RequestMapping(value= "corporateCertNumCheck")
    @ResponseBody
	public Map<String,Object> corporateCertNumCheck(String loanCode,String corporateCertNum){
        Map<String,Object> param = new HashMap<String,Object>();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        param.put("loanCode", loanCode);
        logger.info("证件号码校验是否一样");
        //主借人信息
        LoanCustomer loanCustomer = dataEntryService.queryLoanCustomer(param);
        EncryptUtils.decrypt(loanCustomer);
        //配偶信息
        LoanMate loanMate = dataEntryService.queryLoanMate(loanCode);
        EncryptUtils.decrypt(loanMate);
       
        resultMap.put("loanCustomer",loanCustomer);
        resultMap.put("loanMate",loanMate);
        resultMap.put("corporateCertNum", corporateCertNum);
        return resultMap;
    }
}
