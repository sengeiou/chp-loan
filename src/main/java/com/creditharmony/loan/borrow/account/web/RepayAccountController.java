package com.creditharmony.loan.borrow.account.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.adapter.bean.BaseOutInfo;
import com.creditharmony.adapter.bean.in.mail.MailInfo;
import com.creditharmony.adapter.bean.in.thirdpay.KalianCertificationSingleInBean;
import com.creditharmony.adapter.bean.in.thirdpay.KalianSignReqSingleInBean;
import com.creditharmony.adapter.bean.in.thirdpay.PayCertificationSingleInBean;
import com.creditharmony.adapter.bean.in.thirdpay.PaySignReqSingleInBean;
import com.creditharmony.adapter.bean.out.mail.MailOutInfo;
import com.creditharmony.adapter.bean.out.thirdpay.KalianCertificationSingleOutBean;
import com.creditharmony.adapter.bean.out.thirdpay.KalianSignReqSingleOutBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.adapter.core.utils.DesUtils;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.fortune.type.OpenBankKL;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.MaintainStatus;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.RepaymentFlag;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.file.util.Zip;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.account.constants.AccountConstants;
import com.creditharmony.loan.borrow.account.constants.DownLoadFileType;
import com.creditharmony.loan.borrow.account.constants.FileType;
import com.creditharmony.loan.borrow.account.dao.LoanBankEditDao;
import com.creditharmony.loan.borrow.account.entity.KingAccountChangeExport;
import com.creditharmony.loan.borrow.account.entity.LoanBankEditEntity;
import com.creditharmony.loan.borrow.account.service.RepayAccountService;
import com.creditharmony.loan.borrow.account.service.kingExportHelper;
import com.creditharmony.loan.borrow.account.view.RepayAccountApplyView;
import com.creditharmony.loan.borrow.certification.dao.BankDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.KaLianBankService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.type.OpenBankCJ;
import com.creditharmony.loan.common.type.OpenBankKLCode;
import com.creditharmony.loan.common.utils.CeUtils;
import com.creditharmony.loan.common.utils.TokenUtils;

/**
 * 还款账号管理
 * 
 * @Class Name RepayAccountController
 * @author 王俊杰
 * @Create In 2016年2月22日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/account/repayaccount")
public class RepayAccountController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RepayAccountService repayAccountService;
	@Autowired
	CityInfoService cityInfoService;
	@Autowired
	LoanBankEditDao loanBankEditDao;
	@Autowired
	private BankDao bankDao; 
	
	
	@Autowired
	private KaLianBankService kaLianBankService;
	
	SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 获取账户列表信息
	 * 还款账号变更列表
	 * 2016年5月19日
	 * By 王彬彬
	 * @param req
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/accountApplyList")
	public String accountApplyList(HttpServletRequest req,Model model, RepayAccountApplyView repayAccountApplyView){
		   String phone=req.getParameter("phone");
		   if(StringUtils.isNotEmpty(phone)){
		      repayAccountApplyView.setPhone(phone);
		   }
		List<String> versionList = repayAccountService.selectVersionList();
		if (StringUtils.isNotEmpty(repayAccountApplyView.getContractCode()) || 
					StringUtils.isNotEmpty(repayAccountApplyView.getCustomerName()) ||
					StringUtils.isNotEmpty(repayAccountApplyView.getVersion())){
		    String queryRight = DataScopeUtil.getDataScope("lo", SystemFlag.LOAN.value);
	        repayAccountApplyView.setQueryRight(queryRight);
	        repayAccountApplyView.setPhone(phone);
			List<RepayAccountApplyView> applyList = repayAccountService.selectAccountList(repayAccountApplyView);
			if (StringUtils.isNotEmpty(repayAccountApplyView.getContractCode())){
				repayAccountApplyView.setNewAccountFlag(MaintainStatus.TO_REFUSE.getCode());
				repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_CHECK.getCode());
				repayAccountApplyView.setJzhMaintainStatus(MaintainStatus.TO_FIRST.getCode());
				repayAccountApplyView.setStatus(MaintainStatus.TO_WAITUPDATE.getCode());
				RepayAccountApplyView repay = repayAccountService.selectLoanStatusAndFlag(repayAccountApplyView.getContractCode());
				if(!ObjectHelper.isEmpty(repay))
				{
					repayAccountApplyView.setLoanStatus(repay.getLoanStatus());
					repayAccountApplyView.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",repay.getFlag()));
				}
				List<String> list = repayAccountService.selectMaintainStatus(repayAccountApplyView);
				if (list == null || list.size() == 0){
					repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_REFUSE.getCode());
				} else {
					repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_MAINTAIN.getCode());
				}
			}
			model.addAttribute("applyList", applyList);
			//页面转码
			List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
			Map<String,Dict> dictMap = null;
			if (ArrayHelper.isNotEmpty(applyList)) {
				dictMap = DictCache.getInstance().getMap();
				for (RepayAccountApplyView ex : applyList) {
					ex.setMaintainStatusName(DictUtils.getLabel(dictMap, LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
					ex.setBankNames(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK, ex.getBankName()));
					ex.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag", ex.getFlag()));
					ras.add(ex);
				}
				model.addAttribute("model", ras.get(0).getModel());
			}
			for(RepayAccountApplyView ra:ras){
				String versionLabel=DictCache.getInstance().getDictLabel("jk_contract_ver",ra.getVersion());
				ra.setVersionLabel(versionLabel);
			}
			model.addAttribute("rasList", ras);
		}
		List<CityInfo> provinceList = cityInfoService.findProvinceCmb();
		
		// 添加token 
		TokenUtils.removeToken(repayAccountApplyView.getRepayAccountTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    repayAccountApplyView.setRepayAccountTokenId(tokenMap.get("tokenId"));
	    repayAccountApplyView.setRepayAccountToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("versionList", versionList);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/accountApplyList";
	}
	
	
	
	/**
	 * 获取账户列表信息
	 * 电销还款账号变更列表
	 * 2017年3月2日
	 * By 翁私
	 * @param req
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/accountApplyPhoneSaleList")
	public String accountApplyPhoneSaleList(HttpServletRequest req,Model model, RepayAccountApplyView repayAccountApplyView){
		   String phone=req.getParameter("phone");
		   if(StringUtils.isNotEmpty(phone)){
		      repayAccountApplyView.setPhone(phone);
		   }
		   //设置电销
		   repayAccountApplyView.setPhoneSaleSign("1");
		List<String> versionList = repayAccountService.selectVersionList();
		if (StringUtils.isNotEmpty(repayAccountApplyView.getContractCode()) || 
					StringUtils.isNotEmpty(repayAccountApplyView.getCustomerName()) ||
					StringUtils.isNotEmpty(repayAccountApplyView.getVersion())){
	        repayAccountApplyView.setPhone(phone);
			List<RepayAccountApplyView> applyList = repayAccountService.selectAccountList(repayAccountApplyView);
			if (StringUtils.isNotEmpty(repayAccountApplyView.getContractCode())){
				repayAccountApplyView.setNewAccountFlag(MaintainStatus.TO_REFUSE.getCode());
				repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_CHECK.getCode());
				repayAccountApplyView.setJzhMaintainStatus(MaintainStatus.TO_FIRST.getCode());
				repayAccountApplyView.setStatus(MaintainStatus.TO_WAITUPDATE.getCode());
				RepayAccountApplyView repay = repayAccountService.selectLoanStatusAndFlag(repayAccountApplyView.getContractCode());
				if(!ObjectHelper.isEmpty(repay))
				{
					repayAccountApplyView.setLoanStatus(repay.getLoanStatus());
					repayAccountApplyView.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",repay.getFlag()));
				}
				List<String> list = repayAccountService.selectMaintainStatus(repayAccountApplyView);
				if (list == null || list.size() == 0){
					repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_REFUSE.getCode());
				} else {
					repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_MAINTAIN.getCode());
				}
			}
			model.addAttribute("applyList", applyList);
			//页面转码
			List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
			Map<String,Dict> dictMap = null;
			if (ArrayHelper.isNotEmpty(applyList)) {
				dictMap = DictCache.getInstance().getMap();
				for (RepayAccountApplyView ex : applyList) {
					ex.setMaintainStatusName(DictUtils.getLabel(dictMap, LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
					ex.setBankNames(DictUtils.getLabel(dictMap, LoanDictType.OPEN_BANK, ex.getBankName()));
					ex.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag", ex.getFlag()));
					ras.add(ex);
				}
				model.addAttribute("model", ras.get(0).getModel());
			}
			for(RepayAccountApplyView ra:ras){
				String versionLabel=DictCache.getInstance().getDictLabel("jk_contract_ver",ra.getVersion());
				ra.setVersionLabel(versionLabel);
			}
			model.addAttribute("rasList", ras);
		}
		List<CityInfo> provinceList = cityInfoService.findProvinceCmb();
		
		// 添加token 
		TokenUtils.removeToken(repayAccountApplyView.getRepayAccountTokenId());
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    repayAccountApplyView.setRepayAccountTokenId(tokenMap.get("tokenId"));
	    repayAccountApplyView.setRepayAccountToken(tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("versionList", versionList);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/accountApplyPhoneSaleList";
	}
	
	/**
	 * 获取账户列表基本信息
	 * 还款账号管理列表
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountManageList")
	public String accountManageList(Model model, RepayAccountApplyView repayAccountApplyView,
					HttpServletRequest request, HttpServletResponse response){
		repayAccountApplyView.setUptedaType(AccountConstants.UPDATETYPE_BANKCARD_AND_PHONE);//不查询邮箱的修改
	    String queryRight = DataScopeUtil.getDataScope("lo", SystemFlag.LOAN.value);
        repayAccountApplyView.setQueryRight(queryRight);
		Page<RepayAccountApplyView> page = repayAccountService.selectAccountList(
				new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		model.addAttribute("page", page);
		List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(page.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (RepayAccountApplyView ex : page.getList()) {
					ex.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
					ex.setLoanStatusName(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, ex.getLoanStatus()));
					ex.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getMaintainType()));
					
					//标识
					if(StringUtils.isNotEmpty(ex.getFlag())){
						ex.setFlag(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getFlag()));
					}
					
					// 模式
					if(StringUtils.isNotEmpty(ex.getModel())){
						String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel());
						ex.setModel(dictLoanModel);
					}
					
					// TG客户 待审核状态 在金账户列表显示 by zhangfeng
					if(StringUtils.equals(ex.getModel(), LoanModel.TG.getName())){
						if(!StringUtils.equals(ex.getMaintainStatus(), MaintainStatus.TO_FIRST.getCode())){
							ras.add(ex);
						}
					}else{
						ras.add(ex);
					}
				}
		}
		for(RepayAccountApplyView ra:ras){
			String versionLabel=DictCache.getInstance().getDictLabel("jk_contract_ver",ra.getVersion());
			ra.setVersionLabel(versionLabel);
		}
		model.addAttribute("rasList",ras);
		model.addAttribute("billDayList", billDay);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/accountManageList";
	}
	
	/**
	 * 邮箱管理列表
	 * 2016年11月19日
	 * By huowenlong
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/emailManageList")
	public String emailManageList(Model model, RepayAccountApplyView repayAccountApplyView,
					HttpServletRequest request, HttpServletResponse response){
		repayAccountApplyView.setUptedaType(AccountConstants.UPDATETYPE_EMAIL);
	    String queryRight = DataScopeUtil.getDataScope("lo", SystemFlag.LOAN.value);
        repayAccountApplyView.setQueryRight(queryRight);
		Page<RepayAccountApplyView> page = repayAccountService.selectAccountList(
				new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		model.addAttribute("page", page);
		List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(page.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (RepayAccountApplyView ex : page.getList()) {
					ex.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
					ex.setLoanStatusName(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, ex.getLoanStatus()));
					ex.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getMaintainType()));
					//标识
					if(StringUtils.isNotEmpty(ex.getFlag())){
						ex.setFlag(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getFlag()));
					}
					// 模式
					if(StringUtils.isNotEmpty(ex.getModel())){
						String dictLoanModel = DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel());
						ex.setModel(dictLoanModel);
					}
					ras.add(ex);
				}
		}
		for(RepayAccountApplyView ra:ras){
			String versionLabel=DictCache.getInstance().getDictLabel("jk_contract_ver",ra.getVersion());
			ra.setVersionLabel(versionLabel);
		}
		model.addAttribute("rasList",ras);
		model.addAttribute("billDayList", billDay);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/emailManageList";
	}
	
	/**
	 * 账户维护信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountHandleList")
	public String accountHandleList(Model model, RepayAccountApplyView repayAccountApplyView,
					HttpServletRequest request, HttpServletResponse response){
		
		
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
			repayAccountApplyView.setIds(orgId);
			repayAccountApplyView.setStoreName(orgName);
		   }else if (LoanOrgType.TEAM.key.equals(orgType)){
		    isManager=false;
	    //如果是门店 则 默认门店框体选项
		    repayAccountApplyView.setIds(orgId);
		    repayAccountApplyView.setStoreName(orgName);
		   }
		model.addAttribute("isManager", isManager);
	    String queryRight = DataScopeUtil.getDataScope("lo", SystemFlag.LOAN.value);
	    repayAccountApplyView.setQueryRight(queryRight);
	    String maintainStatus = "'"+MaintainStatus.TO_REFUSE.getCode()+"','"+MaintainStatus.TO_MAINTAIN.getCode()+"'";
	    repayAccountApplyView.setMaintainStatusArray(maintainStatus);
		Page<RepayAccountApplyView> page = repayAccountService.selectAccountList(
				new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		model.addAttribute("page", page);
		List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
		Map<String,Dict> dictMap = null;
		LoanModel loanModel = null;
		String modelStr = null;
		if (ArrayHelper.isNotEmpty(page.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (RepayAccountApplyView ex : page.getList()) {
				ex.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
				//ex.setLoanStatusName(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, ex.getLoanStatus()));
				modelStr = ex.getModel();
				if(StringUtils.isNotEmpty(modelStr)){
				    loanModel = LoanModel.parseByCode(modelStr);
				    ex.setModelName(loanModel.getName());
				}
				ex.setLoanStatusName(DictUtils.getLabel(dictMap,"jk_loan_apply_status", ex.getLoanStatus()));
				ex.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getMaintainType()));
				ex.setVersionLabel(DictCache.getInstance().getDictLabel("jk_contract_ver",ex.getVersion()));
				ex.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getFlag()));
				ex.setModelName(DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel()));
				ras.add(ex);
				}
		}
		
		model.addAttribute("rasList",ras);
		model.addAttribute("billDayList", billDay);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/accountHandleList";
	}
	
	
	/**
	 *还款账号已办
	 * 2017年3月2日
	 * By 翁私
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountHandlePhoneSaleList")
	public String accountHandlePhoneSaleList(Model model, RepayAccountApplyView repayAccountApplyView,
					HttpServletRequest request, HttpServletResponse response){
		
		//设置是否电销标示
		repayAccountApplyView.setPhoneSaleSign("1");
	    String maintainStatus = "'"+MaintainStatus.TO_REFUSE.getCode()+"','"+MaintainStatus.TO_MAINTAIN.getCode()+"'";
	    repayAccountApplyView.setMaintainStatusArray(maintainStatus);
		Page<RepayAccountApplyView> page = repayAccountService.selectAccountList(
				new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		model.addAttribute("page", page);
		List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
		Map<String,Dict> dictMap = null;
		LoanModel loanModel = null;
		String modelStr = null;
		if (ArrayHelper.isNotEmpty(page.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (RepayAccountApplyView ex : page.getList()) {
				ex.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
				//ex.setLoanStatusName(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, ex.getLoanStatus()));
				modelStr = ex.getModel();
				if(StringUtils.isNotEmpty(modelStr)){
				    loanModel = LoanModel.parseByCode(modelStr);
				    ex.setModelName(loanModel.getName());
				}
				ex.setLoanStatusName(DictUtils.getLabel(dictMap,"jk_loan_apply_status", ex.getLoanStatus()));
				ex.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getMaintainType()));
				ex.setVersionLabel(DictCache.getInstance().getDictLabel("jk_contract_ver",ex.getVersion()));
				ex.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getFlag()));
				ex.setModelName(DictCache.getInstance().getDictLabel("jk_loan_model",ex.getModel()));
				ras.add(ex);
				}
		}
		
		model.addAttribute("rasList",ras);
		model.addAttribute("billDayList", billDay);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/accountHandlePhoneSaleList";
	}
	
	/**
	 * 账户新增页面跳转
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newAccount")
	public RepayAccountApplyView newAccount(Model model, RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = repayAccountService.selectAddAccountMassage(repayAccountApplyView);
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		model.addAttribute("provinceList", provinceList);
		return repayAccountApplyView;
	}
	
	/**
	 * 账户编辑页面
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editAccount")
	public RepayAccountApplyView editAccount(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		repayAccountApplyView = repayAccountService.selectEditAccountMassage(repayAccountApplyView);
		HttpSession session = request.getSession();
		session.setAttribute("oldData", repayAccountApplyView.getOldData());
		repayAccountApplyView.setOldData("");
		return repayAccountApplyView;
	}
	
	/**
	 * 保存账户变更信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/saveAccountMessage")
	public String saveAccountMessage(RepayAccountApplyView repayAccountApplyView, HttpServletRequest 
				request, @RequestParam(value = "file", required = true)MultipartFile file){
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = repayAccountApplyView.getRepayAccountTokenId();
			String curToken = repayAccountApplyView.getRepayAccountToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			DmService dmService = DmService.getInstance();
			InputStream in = null;
			try {
				in = file.getInputStream();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			DocumentBean documentBean = dmService.createDocument(file
					.getOriginalFilename(), in, DmService.BUSI_TYPE_LOAN,
					CeFolderType.ACCOUNT_CHANGE.getName(), repayAccountApplyView
							.getContractCode(), UserUtils.getUser().getId());
			HttpSession session = request.getSession();
			repayAccountApplyView.setFileId(documentBean.getDocId());
			repayAccountApplyView.setFileName(file.getOriginalFilename());
			repayAccountApplyView.setApplyTime(new Date());
			repayAccountApplyView.preInsert();
			repayAccountApplyView.setTopFlag(0);
			//设置维护状态为"待审核"
			repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_CHECK.getCode());
			if (StringUtils.isEmpty(repayAccountApplyView.getUptedaType())){
				repayAccountApplyView.setMaintainType(MaintainType.ADD.getCode());
				repayAccountApplyView.setOperateStep("新增卡号");
				//账户修改记录表（修改类型(新增：0，修改：1)）  
				repayAccountApplyView.setChangeType("0");
				repayAccountApplyView.setPhone("");
				repayAccountApplyView.setRefId(repayAccountApplyView.getId());
				repayAccountApplyView.setBankNo(OpenBankKLCode.getOpenBankByKL(repayAccountApplyView.getBankName()));
				repayAccountApplyView.setHdloanBankbrId(repayAccountApplyView.getBankNo());
				repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());  
				repayAccountService.insertAccount(repayAccountApplyView);
			} else if (AccountConstants.UPDATETYPE_PHONE.equals(repayAccountApplyView.getUptedaType())){
				repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
				repayAccountApplyView.setOperateStep("修改手机号");
				String oldData = (String) session.getAttribute("oldData");
				session.removeAttribute("oldData");
				repayAccountApplyView.setOldData(oldData);
				//账户修改记录表（修改类型(新增：0，修改：1)）  
				repayAccountApplyView.setChangeType("1");;
				LoanBankEditEntity loanBankEditEntity = repayAccountService.selectLoanBankByPrimaryKey(repayAccountApplyView.getOldAccountId());
				loanBankEditEntity.setDictMaintainType(MaintainType.CHANGE.getCode());
				loanBankEditEntity.setUpdatetype(repayAccountApplyView.getUptedaType());
				loanBankEditEntity.setFileId(documentBean.getDocId());
				loanBankEditEntity.setFileName(file.getOriginalFilename());
				loanBankEditEntity.setApplyTime(new Date());
				loanBankEditEntity.preInsert();
				loanBankEditEntity.setBankTopFlag(0);
				loanBankEditEntity.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
				loanBankEditEntity.setUpdatecontent(repayAccountApplyView.getCustomerPhone());
				loanBankEditEntity.setOldBankAccountId(repayAccountApplyView.getOldAccountId());
				repayAccountApplyView.setHdloanBankbrId(loanBankEditEntity.getBankNo());
				repayAccountApplyView.setRefId(loanBankEditEntity.getId());
				repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
				repayAccountService.updateMobilePhone(repayAccountApplyView,loanBankEditEntity);
				
			} else if (AccountConstants.UPDATETYPE_ACCOUNT.equals(repayAccountApplyView.getUptedaType())){
				repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
				repayAccountApplyView.setOperateStep("修改卡号");
				String oldData = (String) session.getAttribute("oldData");
				session.removeAttribute("oldData");
				repayAccountApplyView.setOldData(oldData);
				repayAccountApplyView.setBankNo(OpenBankKLCode.getOpenBankByKL(repayAccountApplyView.getBankName()));
				repayAccountApplyView.setHdloanBankbrId(repayAccountApplyView.getBankNo());
				//账户修改记录表（修改类型(新增：0，修改：1)）  
				repayAccountApplyView.setChangeType("1");
				repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());	
				repayAccountApplyView.setUpdatecontent(repayAccountApplyView.getAccount());
				repayAccountApplyView.setRefId(repayAccountApplyView.getId());
				repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
				repayAccountService.updateAccount(repayAccountApplyView);
			} else if (AccountConstants.UPDATETYPE_EMAIL.equals(repayAccountApplyView.getUptedaType())){
				LoanBankEditEntity loanBankEditEntity = repayAccountService.selectLoanBankByPrimaryKey(repayAccountApplyView.getOldAccountId());
				loanBankEditEntity.setDictMaintainType(MaintainType.CHANGE.getCode());
				loanBankEditEntity.setUpdatetype(repayAccountApplyView.getUptedaType());
				loanBankEditEntity.setFileId(documentBean.getDocId());
				loanBankEditEntity.setFileName(file.getOriginalFilename());
				loanBankEditEntity.setApplyTime(new Date());
				loanBankEditEntity.preInsert();
				loanBankEditEntity.setBankTopFlag(0);
				loanBankEditEntity.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
				loanBankEditEntity.setUpdatecontent(repayAccountApplyView.getUpdatecontent());
				loanBankEditEntity.setOldBankAccountId(repayAccountApplyView.getOldAccountId());
				
				RepayAccountApplyView newRepayAccountApplyView = new RepayAccountApplyView();
				//t_jk_account_change_log 表信息
				newRepayAccountApplyView.setId(loanBankEditEntity.getId());
				newRepayAccountApplyView.setRefId(loanBankEditEntity.getId());
				newRepayAccountApplyView.setChangeType("1");
				newRepayAccountApplyView.setFileName(loanBankEditEntity.getFileName());
				newRepayAccountApplyView.setFileId(loanBankEditEntity.getFileId());
				//t_jk_payback_change_his 表信息
				newRepayAccountApplyView.setContractCode(repayAccountApplyView.getContractCode());
				newRepayAccountApplyView.setOperateStep("修改邮箱");
				String oldData = getEmailDataToString(repayAccountApplyView,"old",MaintainStatus.TO_MAINTAIN.getCode());
				repayAccountApplyView.setCustomerEmail(loanBankEditEntity.getUpdatecontent());
				String newData = getEmailDataToString(repayAccountApplyView,"new",MaintainStatus.TO_CHECK.getCode());
				newRepayAccountApplyView.setOldData(oldData);
				newRepayAccountApplyView.setNewData(newData);
				newRepayAccountApplyView.setRemarks(repayAccountApplyView.getRemarks());
				newRepayAccountApplyView.preUpdate();
				newRepayAccountApplyView.setCreateBy(newRepayAccountApplyView.getModifyBy());
				newRepayAccountApplyView.setCreateTime(newRepayAccountApplyView.getModifyTime());
				newRepayAccountApplyView.setRefId(loanBankEditEntity.getId());
				newRepayAccountApplyView.setUptedaType(repayAccountApplyView.getUptedaType());
				repayAccountService.updateEmail(newRepayAccountApplyView,loanBankEditEntity);
			}
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/accountApplyList?"
				+ "contractCode=" + repayAccountApplyView.getContractCode();
	}
	
	
	/**
	 * 电销保存账户变更信息
	 * 2017年3月2日
	 * By 翁私
	 * @param repayAccountApplyView
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/savePhoneSaleAccountMessage")
	public String savePhoneSaleAccountMessage(RepayAccountApplyView repayAccountApplyView, HttpServletRequest 
				request, @RequestParam(value = "file", required = true)MultipartFile file){
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = repayAccountApplyView.getRepayAccountTokenId();
			String curToken = repayAccountApplyView.getRepayAccountToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			DmService dmService = DmService.getInstance();
			InputStream in = null;
			try {
				in = file.getInputStream();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			DocumentBean documentBean = dmService.createDocument(file
					.getOriginalFilename(), in, DmService.BUSI_TYPE_LOAN,
					CeFolderType.ACCOUNT_CHANGE.getName(), repayAccountApplyView
							.getContractCode(), UserUtils.getUser().getId());
			HttpSession session = request.getSession();
			repayAccountApplyView.setFileId(documentBean.getDocId());
			repayAccountApplyView.setFileName(file.getOriginalFilename());
			repayAccountApplyView.setApplyTime(new Date());
			repayAccountApplyView.preInsert();
			repayAccountApplyView.setTopFlag(0);
			//设置维护状态为"待审核"
			repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_CHECK.getCode());
			if (StringUtils.isEmpty(repayAccountApplyView.getUptedaType())){
				repayAccountApplyView.setMaintainType(MaintainType.ADD.getCode());
				repayAccountApplyView.setOperateStep("新增卡号");
				//账户修改记录表（修改类型(新增：0，修改：1)）  
				repayAccountApplyView.setChangeType("0");
				repayAccountApplyView.setPhone("");
				repayAccountApplyView.setRefId(repayAccountApplyView.getContractCode());
				repayAccountApplyView.setBankNo(OpenBankKLCode.getOpenBankByKL(repayAccountApplyView.getBankName()));
				repayAccountApplyView.setHdloanBankbrId(repayAccountApplyView.getBankNo());
				repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());			
				repayAccountService.insertAccount(repayAccountApplyView);
			} else if (AccountConstants.UPDATETYPE_PHONE.equals(repayAccountApplyView.getUptedaType())){
				repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
				repayAccountApplyView.setOperateStep("修改手机号");
				String oldData = (String) session.getAttribute("oldData");
				session.removeAttribute("oldData");
				repayAccountApplyView.setOldData(oldData);
				//账户修改记录表（修改类型(新增：0，修改：1)）  
				repayAccountApplyView.setChangeType("1");;
				LoanBankEditEntity loanBankEditEntity = repayAccountService.selectLoanBankByPrimaryKey(repayAccountApplyView.getOldAccountId());
				loanBankEditEntity.setDictMaintainType(MaintainType.CHANGE.getCode());
				loanBankEditEntity.setUpdatetype(repayAccountApplyView.getUptedaType());
				loanBankEditEntity.setFileId(documentBean.getDocId());
				loanBankEditEntity.setFileName(file.getOriginalFilename());
				loanBankEditEntity.setApplyTime(new Date());
				loanBankEditEntity.preInsert();
				loanBankEditEntity.setBankTopFlag(0);
				loanBankEditEntity.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
				loanBankEditEntity.setUpdatecontent(repayAccountApplyView.getCustomerPhone());
				loanBankEditEntity.setOldBankAccountId(repayAccountApplyView.getOldAccountId());
				repayAccountApplyView.setHdloanBankbrId(loanBankEditEntity.getBankNo());
				repayAccountService.updateMobilePhone(repayAccountApplyView,loanBankEditEntity);
				
			} else if (AccountConstants.UPDATETYPE_ACCOUNT.equals(repayAccountApplyView.getUptedaType())){
				repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
				repayAccountApplyView.setOperateStep("修改卡号");
				String oldData = (String) session.getAttribute("oldData");
				session.removeAttribute("oldData");
				repayAccountApplyView.setOldData(oldData);
				repayAccountApplyView.setBankNo(OpenBankKLCode.getOpenBankByKL(repayAccountApplyView.getBankName()));
				repayAccountApplyView.setHdloanBankbrId(repayAccountApplyView.getBankNo());
				//账户修改记录表（修改类型(新增：0，修改：1)）  
				repayAccountApplyView.setChangeType("1");
				repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());	
				repayAccountApplyView.setUpdatecontent(repayAccountApplyView.getAccount());
				repayAccountService.updateAccount(repayAccountApplyView);
			} else if (AccountConstants.UPDATETYPE_EMAIL.equals(repayAccountApplyView.getUptedaType())){
				LoanBankEditEntity loanBankEditEntity = repayAccountService.selectLoanBankByPrimaryKey(repayAccountApplyView.getOldAccountId());
				loanBankEditEntity.setDictMaintainType(MaintainType.CHANGE.getCode());
				loanBankEditEntity.setUpdatetype(repayAccountApplyView.getUptedaType());
				loanBankEditEntity.setFileId(documentBean.getDocId());
				loanBankEditEntity.setFileName(file.getOriginalFilename());
				loanBankEditEntity.setApplyTime(new Date());
				loanBankEditEntity.preInsert();
				loanBankEditEntity.setBankTopFlag(0);
				loanBankEditEntity.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
				loanBankEditEntity.setUpdatecontent(repayAccountApplyView.getUpdatecontent());
				loanBankEditEntity.setOldBankAccountId(repayAccountApplyView.getOldAccountId());
				
				RepayAccountApplyView newRepayAccountApplyView = new RepayAccountApplyView();
				//t_jk_account_change_log 表信息
				newRepayAccountApplyView.setId(loanBankEditEntity.getId());
				newRepayAccountApplyView.setRefId(loanBankEditEntity.getId());
				newRepayAccountApplyView.setChangeType("1");
				newRepayAccountApplyView.setFileName(loanBankEditEntity.getFileName());
				newRepayAccountApplyView.setFileId(loanBankEditEntity.getFileId());
				//t_jk_payback_change_his 表信息
				newRepayAccountApplyView.setContractCode(repayAccountApplyView.getContractCode());
				newRepayAccountApplyView.setOperateStep("修改邮箱");
				String oldData = getEmailDataToString(repayAccountApplyView,"old",MaintainStatus.TO_MAINTAIN.getCode());
				repayAccountApplyView.setCustomerEmail(loanBankEditEntity.getUpdatecontent());
				String newData = getEmailDataToString(repayAccountApplyView,"new",MaintainStatus.TO_CHECK.getCode());
				newRepayAccountApplyView.setOldData(oldData);
				newRepayAccountApplyView.setNewData(newData);
				newRepayAccountApplyView.setRemarks(repayAccountApplyView.getRemarks());
				newRepayAccountApplyView.preUpdate();
				newRepayAccountApplyView.setCreateBy(newRepayAccountApplyView.getModifyBy());
				newRepayAccountApplyView.setCreateTime(newRepayAccountApplyView.getModifyTime());
				repayAccountService.updateEmail(newRepayAccountApplyView,loanBankEditEntity);
			}
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/accountApplyPhoneSaleList?"
				+ "contractCode=" + repayAccountApplyView.getContractCode();
	}
	
	@ResponseBody
	@RequestMapping(value = "/editMobilePhone")
	public RepayAccountApplyView editMobilePhone(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		repayAccountApplyView = repayAccountService.selectEditAccountMassage(repayAccountApplyView);
		HttpSession session = request.getSession();
		session.setAttribute("oldData", repayAccountApplyView.getOldData());
		repayAccountApplyView.setOldData("");
		return repayAccountApplyView;
	}
	
	/**
	 * 邮箱信息
	·* 2016年11月18日
	·* by Huowenlong
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editCustomerEmail")
	public RepayAccountApplyView editCustomerEmail(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		repayAccountApplyView = repayAccountService.selectEditAccountMassage(repayAccountApplyView);
		return repayAccountApplyView;
	}
	
	/**
	 * 账户维护
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/examineMessage")
	public RepayAccountApplyView examineMessage(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		repayAccountApplyView = repayAccountService.selectExamineMessage(repayAccountApplyView);
		HttpSession session = request.getSession();
		session.setAttribute("oldData", repayAccountApplyView.getOldData());
		repayAccountApplyView.setOldData("");
		return repayAccountApplyView;
	}
	
	/**
	 * 账户维护信息保存
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveExamineResult")
	public String saveExamineResult(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		HttpSession session = request.getSession();
		String oldData = (String) session.getAttribute("oldData");
		repayAccountApplyView.setOldData(oldData);
		session.removeAttribute("oldData");
		String newId  = repayAccountApplyView.getId();
		repayAccountApplyView.setOperateStep("审核");
		repayAccountApplyView.preUpdate();
		//先判断审核结果是通过还是拒绝，然后判断审核的是手机号还是账号,0表示账号，1表示手机号
		if (MaintainStatus.TO_MAINTAIN.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			
			repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());
			repayAccountApplyView.setTopFlag(1);
			if ("1".equals(repayAccountApplyView.getPhone())){
				repayAccountApplyView.setMaintainTime(new Date());
				//更新账号审核通过
				if (StringUtils.isNotEmpty(repayAccountApplyView.getOldAccountId())){
					repayAccountService.throughEditAccount(repayAccountApplyView);
				} else {
					repayAccountService.throughAddAccount(repayAccountApplyView);
				}
				cjRealSingle(newId);
				
			} else if("0".equals(repayAccountApplyView.getPhone())) {
				repayAccountApplyView.setMaintainTime(new Date());
				//更新手机号审核通过
				repayAccountService.throughPhone(repayAccountApplyView);
				
			}
			// realSingle(repayAccountApplyView);
			
			
		} else if (MaintainStatus.TO_REFUSE.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			if ("1".equals(repayAccountApplyView.getPhone())){
				//更新账号审核拒绝
				repayAccountService.refuseAccount(repayAccountApplyView);
			} else if("0".equals(repayAccountApplyView.getPhone())) {
				//更新手机号审核拒绝
				repayAccountService.refusePhone(repayAccountApplyView);
			}
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/accountManageList";
	}
	
	/**
	 * 下载文件
	 * 2016年5月19日
	 * By 王彬彬
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		try {
			name = new String(name.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("iso8859-1");
		response.addHeader("Content-Disposition", "attachment;filename=" + name);
		DmService dmService = DmService.getInstance();
		try {
			dmService.download(response.getOutputStream(), id);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
 	}
	/**
     * 下载文件
     * 2016年5月19日
     * By 王彬彬
     * @param request
     * @param response
     */
	@RequestMapping(value="/goldDownloadFile")
	public void goldDownloadFile(HttpServletRequest request, HttpServletResponse response){
	    String id = request.getParameter("id");
        String name = request.getParameter("name");
        if(StringUtils.isEmpty(name)){
            name = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        }
        id = id.replace(",", "','");
        String ids = "'"+id+"'";
         Map<String,Object> map = new HashMap<String,Object>();
         map.put("ids", ids);
         List<KingAccountChangeExport> accountList = repayAccountService.getTGdownload(map);
         List<String> documentIds = new ArrayList<String>();
         if(!ObjectHelper.isEmpty(accountList)){
             for (KingAccountChangeExport k:accountList) {
                 documentIds.add(k.getFileId());
                 String mobile = repayAccountService.mobileDisDecrypt(k.getLoanCode());
                 k.setOldCustomerPhone(mobile);
            } 
         }
         kingExportHelper kingExportHelper = SpringContextHolder.getBean(kingExportHelper.class);
         Map<String, InputStream> excelInfo = kingExportHelper.excelCreate(accountList);
         Map<String, InputStream> ceDownload = null; 
         response.setContentType("application/zip");
         response.setCharacterEncoding("UTF-8");
         response.addHeader("Content-disposition", "attachment;filename=" + name+".zip");
         DmService dmService = DmService.getInstance();
          ceDownload = dmService.downloadDocuments(documentIds);
          ceDownload.putAll(excelInfo);
             try {
                Zip.zipFiles(response.getOutputStream(), ceDownload);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }  
	}
	/**
	 * 显示维护历史
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/showMaintainHistory")
	public String showMaintainHistory(Model model, RepayAccountApplyView repayAccountApplyView){
		List<RepayAccountApplyView> list = repayAccountService.getHistoryList(repayAccountApplyView);
		model.addAttribute("list", list);
		return "borrow/repayaccount/showMaintainHistory";
	}
	
	/**
	 * 账户信息查看
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/showMessage")
	public String showMessage(Model model, RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = repayAccountService.selectAccountInfoById(repayAccountApplyView);
		Map<String,Dict> dictMap =  DictCache.getInstance().getMap();
		repayAccountApplyView.setBankNames(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, repayAccountApplyView.getBankName()));
		repayAccountApplyView.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, repayAccountApplyView.getMaintainStatus()));
		repayAccountApplyView.setDeductPlatName(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, repayAccountApplyView.getDeductPlat()));
		repayAccountApplyView.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, repayAccountApplyView.getMaintainType()));
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		if(AccountConstants.UPDATETYPE_EMAIL.equals(repayAccountApplyView.getUptedaType())){
			return "borrow/repayaccount/showEmailMessage";
		}else{
			return "borrow/repayaccount/showMessage";
		}
		
	}
	
	/**
	 * 账户置顶
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@ResponseBody
	@RequestMapping(value = "/setTop")
	public void setTop(RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView.setOperateStep("置顶");
		repayAccountApplyView.setNewData("置顶");
		repayAccountApplyView.preUpdate();
		repayAccountService.updateAccountTop(repayAccountApplyView);
	}
	
	/**
	 * 金账户编辑
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editGoldAccount")
	public RepayAccountApplyView editGoldAccount(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		repayAccountApplyView = repayAccountService.selectGoldAccountMassage(repayAccountApplyView);
		HttpSession session = request.getSession();
		session.setAttribute("oldData", repayAccountApplyView.getOldData());
		repayAccountApplyView.setOldData("");
		if (repayAccountApplyView.getProvinceId() != null){
			repayAccountApplyView.setCityList(cityInfoService.findCityCmb(repayAccountApplyView.getProvinceId()));
		}
		repayAccountApplyView.setBankList(DictUtils.getDictList("jk_open_bank"));
		return repayAccountApplyView;
	}
	
	/**
	 * 金账户编辑保存页面
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/saveGoldAccount")
	public String saveGoldAccount(RepayAccountApplyView repayAccountApplyView, HttpServletRequest 
				request, @RequestParam(value = "file", required = true)MultipartFile file){
		//判断token
		boolean result = false;
		Object clock = new Object();
		synchronized (clock) {
			String curTokenId = repayAccountApplyView.getRepayAccountTokenId();
			String curToken = repayAccountApplyView.getRepayAccountToken();
			result = TokenUtils.validToken(curTokenId, curToken);
			TokenUtils.removeToken(curTokenId);
		} 
		if(result){
			DmService dmService = DmService.getInstance();
			InputStream in = null;
			try {
				in = file.getInputStream();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			DocumentBean documentBean = dmService.createDocument(file
					.getOriginalFilename(), in, DmService.BUSI_TYPE_LOAN,
					CeFolderType.ACCOUNT_CHANGE.getName(), repayAccountApplyView
							.getContractCode(), UserUtils.getUser().getId());
			HttpSession session = request.getSession();
			repayAccountApplyView.setFileId(documentBean.getDocId());
			repayAccountApplyView.setFileName(file.getOriginalFilename());
			repayAccountApplyView.setApplyTime(new Date());
			repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_FIRST.getCode());
			repayAccountApplyView.preInsert();
			repayAccountApplyView.setTopFlag(0);
			repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());
			repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
			
			//同步信息
			repayAccountApplyView.setBankName(repayAccountApplyView.getpMsgBankName());
			repayAccountApplyView.setBankBranch(repayAccountApplyView.getpMsgBankBranch());
			repayAccountApplyView.setAccount(repayAccountApplyView.getpMsgAccount());
			repayAccountApplyView.setCustomerPhone(repayAccountApplyView.getpMsgCustomerPhone());
			repayAccountApplyView.setProvinceId(repayAccountApplyView.getpMsgProvinceId());
			repayAccountApplyView.setCityId(repayAccountApplyView.getpMsgCityId());
			//repayAccountApplyView.setOperateStep("修改金账户卡号");
			repayAccountApplyView.setRefId(repayAccountApplyView.getId());
			String oldData = (String) session.getAttribute("oldData");
			session.removeAttribute("oldData");
			repayAccountApplyView.setOldData(oldData);
			repayAccountApplyView.setUptedaType(repayAccountApplyView.getPhone());
			repayAccountApplyView.setChangeType("1");
			repayAccountApplyView.setMaintainType(MaintainType.CHANGE.getCode());
			//如果修改金账户手机号及卡号页面选择的是'变更银行卡号' 则设置值，进行保存操作
			if(repayAccountApplyView.getPhone().equals("1")){
				repayAccountApplyView.setOperateStep("修改金账户卡号");
				repayAccountApplyView.setProvinceId(repayAccountApplyView.getpMsgProvinceId());
				repayAccountApplyView.setCityId(repayAccountApplyView.getpMsgCityId());
				repayAccountApplyView.setCustomerId("");
				repayAccountApplyView.setBankName(repayAccountApplyView.getpMsgBankName());
				repayAccountApplyView.setBankBranch(repayAccountApplyView.getpMsgBankBranch());
				repayAccountApplyView.setBankNo(OpenBankKLCode.getOpenBankByKL(repayAccountApplyView.getBankName()));
				repayAccountApplyView.setHdloanBankbrId(repayAccountApplyView.getBankNo());
				repayAccountApplyView.setUpdatecontent(repayAccountApplyView.getAccount());
				repayAccountService.editGoldAccount(repayAccountApplyView);
			}else if(repayAccountApplyView.getPhone().equals("0")){
				repayAccountApplyView.setOperateStep("修改金账户手机号");
				repayAccountApplyView.setCustomerPhone(repayAccountApplyView.getpMsgCustomerPhone());
				LoanBankEditEntity loanBankEditEntity = repayAccountService.selectLoanBankByPrimaryKey(repayAccountApplyView.getOldAccountId());
				loanBankEditEntity.setDictMaintainType(MaintainType.CHANGE.getCode());
				loanBankEditEntity.setUpdatetype(repayAccountApplyView.getPhone());
				loanBankEditEntity.setFileId(documentBean.getDocId());
				loanBankEditEntity.setFileName(file.getOriginalFilename());
				loanBankEditEntity.setApplyTime(new Date());
				loanBankEditEntity.preInsert();
				loanBankEditEntity.setBankTopFlag(0);
				loanBankEditEntity.setDictMaintainStatus(MaintainStatus.TO_FIRST.getCode());
				loanBankEditEntity.setUpdatecontent(repayAccountApplyView.getCustomerPhone());
				loanBankEditEntity.setOldBankAccountId(repayAccountApplyView.getOldAccountId());
				repayAccountApplyView.setHdloanBankbrId(loanBankEditEntity.getBankNo());
				repayAccountApplyView.setDeductPlat(loanBankEditEntity.getBankSigningPlatform());
				repayAccountApplyView.setRefId(loanBankEditEntity.getId());
				repayAccountService.updateGoldPhone(repayAccountApplyView,loanBankEditEntity);
			}
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/accountApplyList?"
				+ "contractCode=" + repayAccountApplyView.getContractCode()+"&phone="+repayAccountApplyView.getPhone();
	}
	
	/**
	 * 金账户初审历史
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goldAccountCheckList")
	public String goldAccountCheckList(Model model, RepayAccountApplyView repayAccountApplyView,
				HttpServletRequest request, HttpServletResponse response){
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_FIRST.getCode());
		//设置标识为"TG"
		repayAccountApplyView.setModel(LoanModel.TG.getCode());
		repayAccountApplyView.setModelName(LoanModel.TG.getName());
		Page<RepayAccountApplyView> page = repayAccountService.selectAccountList(
				new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		model.addAttribute("page", page);
		List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(page.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (RepayAccountApplyView ex : page.getList()) {
				ex.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
				ex.setLoanStatusName(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, ex.getLoanStatus()));
				ex.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getMaintainType()));
				ex.setVersionLabel(DictUtils.getLabel(dictMap,LoanDictType.CONTRACT_VER,ex.getVersion()));
				ex.setFlag(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getFlag()));
				ras.add(ex);
				}
		}
		model.addAttribute("rasList",ras);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/goldAccountCheckList";
	}
	
	/**
	 * 金账户初审
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goldAccountVerifyList")
	public String goldAccountVerifyList(Model model, RepayAccountApplyView repayAccountApplyView,
				HttpServletRequest request, HttpServletResponse response){
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_WAITUPDATE.getCode());
		repayAccountApplyView.setModel(LoanModel.TG.getCode());
		repayAccountApplyView.setModelName(LoanModel.TG.getName());
		Page<RepayAccountApplyView> page = repayAccountService.selectAccountList(
				new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		List<String> versionList = repayAccountService.selectVersionList();
		model.addAttribute("page", page);
		List<RepayAccountApplyView> ras=new ArrayList<RepayAccountApplyView>();
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(page.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for (RepayAccountApplyView ex : page.getList()) {
				ex.setMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getMaintainStatus()));
				ex.setLoanStatusName(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, ex.getLoanStatus()));
				ex.setMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getMaintainType()));
				ex.setFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag",ex.getFlag()));
				ex.setVersionLabel(DictUtils.getLabel(dictMap,LoanDictType.CONTRACT_VER,ex.getVersion()));
				ras.add(ex);
				}
		}
		model.addAttribute("rasList",ras);
		model.addAttribute("versionList", versionList);
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/goldAccountVerifyList";
	}
	
	
	/**
	 * 确认成功
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goldConfirmSuccess")
	public String goldConfirmSuccess(Model model, RepayAccountApplyView repayAccountApplyView,
				HttpServletRequest request, HttpServletResponse response){
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_WAITUPDATE.getCode());
		repayAccountApplyView.setModel(LoanModel.TG.getCode());
		repayAccountApplyView.setModelName(LoanModel.TG.getName());
		if(StringUtils.isNotEmpty(repayAccountApplyView.getBankIds())){
			repayAccountService.editConfirmSuccess(
					new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		}
		
		return "redirect:" + adminPath + "/borrow/account/repayaccount/goldAccountVerifyList";
	}
	
	/**
	 * 失败/退回
	 * @param model
	 * @param repayAccountApplyView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goldFailReturn")
	public String goldFailReturn(Model model, RepayAccountApplyView repayAccountApplyView,
				HttpServletRequest request, HttpServletResponse response){
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_WAITUPDATE.getCode());
		repayAccountApplyView.setModel(LoanModel.TG.getCode());
		repayAccountApplyView.setModelName(LoanModel.TG.getName());
		if(StringUtils.isNotEmpty(repayAccountApplyView.getBankIds())){
			repayAccountService.editFailReturn(
					new Page<RepayAccountApplyView>(request, response), repayAccountApplyView);
		}
		
		return "redirect:" + adminPath + "/borrow/account/repayaccount/goldAccountVerifyList";
	}
	
	
	/**
	 * 金账户维护页面
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 * 添加字段ct.loan_code,bk.updatetype,bk.bank_no
	 */
	@ResponseBody
	@RequestMapping(value = "/examineGoldAccount")
	public RepayAccountApplyView examineGoldAccount(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		repayAccountApplyView = repayAccountService.selectGoldExamine(repayAccountApplyView);
		HttpSession session = request.getSession();
		session.setAttribute("oldData", repayAccountApplyView.getOldData());
		repayAccountApplyView.setOldData("");
		return repayAccountApplyView;
	}
	
	/**
	 * 金账户维护历史保存(初审)
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveGoldExamineResult")
	public String saveGoldExamineResult(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		HttpSession session = request.getSession();
		String oldData = (String) session.getAttribute("oldData");
		repayAccountApplyView.setOldData(oldData);
		session.removeAttribute("oldData");
		repayAccountApplyView.setOperateStep("审核");
		repayAccountApplyView.preUpdate();
		repayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());
		//先判断审核结果是通过还是拒绝，然后判断审核的是手机号还是账号,1表示账号，0表示手机号
		//如果通过，判断是"TG"还是其它标识
		//MaintainStatus.TO_CHECK.getCode()代表金账户通过审核状态
		if (MaintainStatus.TO_MAINTAIN.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			if ("1".equals(repayAccountApplyView.getPhone())){
				repayAccountApplyView.setMaintainTime(new Date());
				//更新账号审核通过
				repayAccountService.throughEditAccount(repayAccountApplyView);
			} else if("0".equals(repayAccountApplyView.getPhone())) {
				repayAccountApplyView.setMaintainTime(new Date());
				//更新手机号审核通过
				repayAccountService.throughPhone(repayAccountApplyView);
			}
		} else if (MaintainStatus.TO_REFUSE.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			if ("1".equals(repayAccountApplyView.getPhone())){
				//更新账号审核拒绝
				repayAccountService.refuseAccount(repayAccountApplyView);
			} else if("0".equals(repayAccountApplyView.getPhone())) {
				//更新手机号审核拒绝
				repayAccountService.refusePhone(repayAccountApplyView);
			}
		}else if (MaintainStatus.TO_WAITUPDATE.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			repayAccountService.throughAddAccount(repayAccountApplyView);
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/goldAccountCheckList";
	}

	/**
	 * 终审信息保存
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveGoldFirstCheck")
	public String saveGoldFirstCheck(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request){
		HttpSession session = request.getSession();
		String oldData = (String) session.getAttribute("oldData");
		repayAccountApplyView.setOldData(oldData);
		session.removeAttribute("oldData");
		repayAccountApplyView.setOperateStep("审核");
		repayAccountApplyView.preUpdate();
		//先判断审核结果是通过还是拒绝，然后判断审核的是手机号还是账号,1表示账号，0表示手机号
		if (MaintainStatus.TO_MAINTAIN.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			if ("1".equals(repayAccountApplyView.getPhone())){
				repayAccountApplyView.setMaintainTime(new Date());
				//更新账号审核通过
				if (StringUtils.isNotEmpty(repayAccountApplyView.getOldAccountId())){
					repayAccountService.editBankAccount(repayAccountApplyView);
				} else {
					repayAccountService.throughAddAccount(repayAccountApplyView);
				}
			} else if("0".equals(repayAccountApplyView.getPhone())) {
				repayAccountApplyView.setMaintainTime(new Date());
				//更新手机号审核通过
				repayAccountService.throughPhone(repayAccountApplyView);
			}
		} else if (MaintainStatus.TO_REFUSE.getCode().equals(repayAccountApplyView.getMaintainStatus())){
			if ("1".equals(repayAccountApplyView.getPhone())){
				//更新账号审核拒绝
				repayAccountService.refuseAccount(repayAccountApplyView);
			} else if("0".equals(repayAccountApplyView.getPhone())) {
				//更新手机号审核拒绝
				repayAccountService.refusePhone(repayAccountApplyView);
			}
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/goldAccountVerifyList";
	}
	
	/**
	 * 查看金账户审核信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/goldShowCheckMessage")
	public String goldShowCheckMessage(Model model, RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = repayAccountService.selectGoldExamine(repayAccountApplyView);
		String bankName=DictCache.getInstance().getDictLabel("jk_open_bank",repayAccountApplyView.getBankName());
		repayAccountApplyView.setBankNames(bankName);
		String maintainStatus=DictCache.getInstance().getDictLabel("jk_maintain_status",repayAccountApplyView.getMaintainStatus());
		repayAccountApplyView.setMaintainStatusName(maintainStatus);
		String deductPlat=DictCache.getInstance().getDictLabel("jk_deduct_plat",repayAccountApplyView.getDeductPlat());
		repayAccountApplyView.setDeductPlatName(deductPlat);
		if("0".equals(repayAccountApplyView.getUptedaType())){
			repayAccountApplyView.setOldAccount(repayAccountApplyView.getAccount());
			repayAccountApplyView.setAccount("");
		}
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/goldShowCheckMessage";
	}
	
	/**
	 * 金账户待审核信息显示
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/goldShowMessage")
	public String goldShowMessage(Model model, RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		repayAccountApplyView = repayAccountService.selectGoldExamine(repayAccountApplyView);
		
		String bankName=DictCache.getInstance().getDictLabel("jk_open_bank",repayAccountApplyView.getBankName());
		repayAccountApplyView.setBankNames(bankName);
		
		String maintainStatus=DictCache.getInstance().getDictLabel("jk_maintain_status",repayAccountApplyView.getMaintainStatus());
		repayAccountApplyView.setMaintainStatusName(maintainStatus);
		
		model.addAttribute("repayAccountApplyView", repayAccountApplyView);
		return "borrow/repayaccount/goldShowMessage";
	}
	
	/**
	 * 资料包下载
	 * 2016年6月15日
	 * By 王彬彬
	 * @param fileType
	 * @return
	 */
	@RequestMapping(value = "/fileDownLoad")
	public String fileDownLoad(String fileType, HttpServletResponse response) {
		 List<DocumentBean> lst = new ArrayList<DocumentBean>();
		 String fileName = "";
		 Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		try {
			// 财富&P2P卡号手机号变更模板
			if (DownLoadFileType.CF.equals(fileType)) {
				lst = CeUtils.downFileBySubType("BusinessFile",
						FileType.CF.getCode(), null, response);
				fileName = FileType.CF.getName();
			}
			// 金信卡号手机号变更模板
			if (DownLoadFileType.JX.equals(fileType)) {
				lst =CeUtils.downFileBySubType("BusinessFile",
						FileType.JX.getCode(), null, response);
				fileName = FileType.JX.getName();
			}
			// 金账户（TG)卡号手机号变更模板
			if (DownLoadFileType.TG.equals(fileType)) {
				lst = CeUtils.downFileBySubType("BusinessFile",
						FileType.TG.getCode(), null, response);
				fileName = FileType.TG.getName();
			}
			// 信托卡号手机号变更模板
			if (DownLoadFileType.XT.equals(fileType)) {
				lst = CeUtils.downFileBySubType("BusinessFile",
						FileType.XT.getCode(), null, response);
				fileName = FileType.XT.getName();
			}
			
			if (DownLoadFileType.JCRBG.equals(fileType)) {
				lst = CeUtils.downFileBySubType("BusinessFile",
						FileType.JCRBG.getCode(), null, response);
				fileName = FileType.JCRBG.getName();
			}
			
			try {
				fileMap.put(lst.get(0).getDocTitle(), lst.get(0).getInputStream());
				
				response.setContentType("application/zip");
				response.addHeader("Content-disposition", "attachment;filename="
						+ new String((fileName + ".zip").getBytes("gbk"),
								"ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			if (!ObjectHelper.isEmpty(fileMap)) {
				try {
					Zip.zipFiles(response.getOutputStream(), fileMap);
				} catch (IOException e) {
					logger.error("文件下载失败");
					e.printStackTrace();
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 调用实名认证 签约接口（卡联）
	 */
	public void  realSingle(RepayAccountApplyView repayAccountApplyView){
	         List<LoanBank>  list = kaLianBankService.queryCertificationById(repayAccountApplyView.getId());
	        if(list != null && list.size()>0){
	        	ClientPoxy service = new ClientPoxy(ServiceType.Type.KL_CERTIFICATION_SINGLE);
	        	LoanBank bank = list.get(0);
	        	// 如果没有实名认证  去调用接口
	        	if(!"1".equals(bank.getRealAuthen())){
	        	String bankName = null;
	        	bankName = OpenBankKL.getOpenBankByKL(bank.getBankName());
	            KalianCertificationSingleInBean detail = new KalianCertificationSingleInBean();
	        	detail.setAccName(bank.getBankAccountName());
	        	detail.setAccBank(bankName);
	            detail.setCardType(bank.getCardType());
	            detail.setAccNo(bank.getBankAccount());
	            detail.setBankCode(bank.getBankNo());
	            detail.setBankName(bankName);
	            detail.setCertType(changeNum(bank.getIdType()));
	            detail.setCertNo(bank.getIdNo());
	            detail.setPhone(bank.getMobile());
 	            detail.setTrSerialNo(randomNumStr());
 	            detail.setSubmitDate(new Date());
 	        try {
 	        	  logger.info("【卡号变更】卡联实名认证开始！流水号："+detail.getTrSerialNo());
 	        	  KalianCertificationSingleOutBean  outInfo = (KalianCertificationSingleOutBean ) service.callService(detail);
 	             // 如果提交成功则 更新流水号 提交日期 和实名认证状态
 	             if("0000".equals(outInfo.getRetCode())){
 	            	LoanBank bankquery = new LoanBank();
 	            	bankquery.setTrSerialNo(detail.getTrSerialNo());
 	            	bankquery.setTransDate(new Date());
 	            	bankquery.setRealAuthen("1");
 	            	bankquery.setId(repayAccountApplyView.getId());
 	            	kaLianBankService.updateBankById(bankquery);
 	            	logger.info("【卡号变更】 卡联实名认证结束！流水号："+detail.getTrSerialNo());
 	            	ClientPoxy kaLiservice = new ClientPoxy(ServiceType.Type.KL_SIGNREQ_SINGLE);
 	                KalianSignReqSingleInBean inParam = new KalianSignReqSingleInBean();
 	                inParam.setTrSerialNo(randomNumStr());
 	                inParam.setSubmitDate(new Date());
 	                inParam.setAccName(detail.getAccName());
 	                inParam.setCardType("0");
 	                inParam.setAccNo(detail.getAccNo());
 	                inParam.setBankCode(detail.getBankCode());
 	                inParam.setBankName(detail.getBankName());
 	                inParam.setCertType(detail.getCertType());
 	                inParam.setCertNo(detail.getCertNo());
 	                inParam.setPhone(detail.getPhone());
 	                inParam.setBeginDate(format.format(new Date()));
 	                inParam.setEndDate("20170701");
 	            	logger.info("【卡号变更】 卡联签约开始！流水号："+inParam.getTrSerialNo());
 	                     // 获取开始时间
 	                   KalianSignReqSingleOutBean siInfo = (KalianSignReqSingleOutBean) kaLiservice.callService(inParam);
 	                    if("0000".equals(siInfo.getRetCode())){
 	                    	LoanBank bankSikl  = new LoanBank();
 	                    	bankSikl.setTrSerialNo(detail.getTrSerialNo());
 	                    	bankSikl.setTransDate(new Date());
 	                    	bankSikl.setKlSign("1");
 	                    	bankSikl.setId(repayAccountApplyView.getId());
         	            	kaLianBankService.updateBankById(bankSikl);
 	                      }else{
 	                    	LoanBank bankSikl  = new LoanBank();
 	                    	bankSikl.setTrSerialNo(detail.getTrSerialNo());
 	                    	bankSikl.setTransDate(new Date());
 	                    	bankSikl.setKlSign("2");
 	                    	bankSikl.setId(repayAccountApplyView.getId());
         	            	kaLianBankService.updateBankById(bankSikl); 
 	                    	  
 	                      logger.error("【卡号变更 】卡联签约处理异常！"+siInfo.getRetMsg());
 	                     }
 	                   logger.info("【卡号变更】 卡联签约结束！流水号："+inParam.getTrSerialNo());
 	            	
 	              }else{
 	            	    logger.error("【卡号变更 】卡联实名认证处理异常！"+outInfo.getRetMsg());
 	            	    LoanBank bankquery = new LoanBank();
     	            	bankquery.setTrSerialNo(detail.getTrSerialNo());
     	            	bankquery.setTransDate(new Date());
     	            	bankquery.setRealAuthen("2");
     	            	bankquery.setId(repayAccountApplyView.getId());
     	            	kaLianBankService.updateBankById(bankquery);
 	            }
 	           } catch (Exception e) {
 	            e.printStackTrace();
 	        }
	        	}
	        }
	}
	
	/**
	 * 将系统的证件类型转为上传的证件类型
	 * 2016年3月14日
	 * By 翁私
	 * @param cum
	 * @return
	 */
	public String changeNum(String cum){
		//0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
		if(cum.equals(CertificateType.SFZ.getCode())){
			return "ZR01";
		}else if(cum.equals(CertificateType.JGZ.getCode())){
			return "ZR04";
		}else if(cum.equals(CertificateType.HZ.getCode())){
			return "ZR13";
		}else if(cum.equals(CertificateType.HKB.getCode())){
			return "ZR03";
		}else if(cum.equals(CertificateType.GAJMLWNDTXZ.getCode())){
			return "ZR09";
		}
		return "";
	}
	
	/**
	 * 随机产生指定长度的数据字符串
	 * @param length 指定长度
	 * @return 结果
	 */
	public String randomNumStr(){
		  UUID uuid= UUID.randomUUID();
		  String batchNo=uuid.toString().replaceAll("-","");
		  batchNo=batchNo.substring(0, 16);
		  return batchNo;
	}
	
	/**
	 * 查询金账户待审核的数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGoldAccountCount")
	public String getGoldAccountCount(){
		String userId = UserUtils.getUser().getId();
		int rolecount = repayAccountService.selectRoleCount(userId);
		if(rolecount == 0 ){
			return rolecount+"";
		}
		RepayAccountApplyView repayAccountApplyView = new RepayAccountApplyView();
		repayAccountApplyView.setMaintainStatus(MaintainStatus.TO_WAITUPDATE.getCode());
		repayAccountApplyView.setModel(LoanModel.TG.getCode());
		repayAccountApplyView.setModelName(LoanModel.TG.getName());
		String count = repayAccountService.selectAccountCount(repayAccountApplyView);
		if(count == null || "".equals(count)){
			count ="0";
		}
		return count;
	}
	
	
	
	/**
	 * 畅捷实名认证 
	 */
	public void  cjRealSingle(String  id){
             List<com.creditharmony.loan.borrow.certification.entity.LoanBank>  list = bankDao.queryCjById(id);
	        if(list != null && list.size()>0){
 			    ClientPoxy service = new ClientPoxy(ServiceType.Type.CJ_CERTIFICATION);
	        	com.creditharmony.loan.borrow.certification.entity.LoanBank bank = list.get(0);
	        	// 如果没有实名认证  去调用接口
	        	if(!"1".equals(bank.getCjSign())){
				PayCertificationSingleInBean cjInfo = new PayCertificationSingleInBean();
				 String bankFullName = OpenBankCJ.getOpenBank(bank.getBankFullName());
    	         cjInfo.setTrSerialNo(getFlowNo());
				 cjInfo.setSubmitDate(new Date());
				 cjInfo.setSn(getFlowNo());
				 cjInfo.setBankGeneralName(bankFullName);
				 cjInfo.setBankName(bankFullName);
				 cjInfo.setBankCode(bank.getBankCode());
				 cjInfo.setAccountType("00");
				 cjInfo.setAccountName(bank.getAccName());
				 cjInfo.setAccountNo(bank.getAccNo());
				 cjInfo.setIdType(bank.getCertType());
				 //BeanUtils.copyProperties(bank, cjInfo);
				 cjInfo.setId(bank.getCertNo());
				 cjInfo.setTel(bank.getPhone());
 	        try {
 	        	  logger.info("【卡号变更】畅捷实名认证开始！流水号："+cjInfo.getTrSerialNo());
	        	  BaseOutInfo outInfo = (BaseOutInfo) service.callService(cjInfo);
 	             // 如果提交成功则 更新流水号 提交日期 和实名认证状态
 	             if("0000".equals(outInfo.getRetCode())){
 	            	com.creditharmony.loan.borrow.certification.entity.LoanBank bankquery = new com.creditharmony.loan.borrow.certification.entity.LoanBank();
	            	bankquery.setTrSerialNo(cjInfo.getTrSerialNo());
	            	bankquery.setCjAuthen("1");
	            	bankquery.setAccNo(cjInfo.getAccountNo());
	            	bankDao.updateByAccNo(bankquery);
	            	logger.info("【卡号变更】 畅捷实名认证结束！流水号："+cjInfo.getTrSerialNo());
	            	ClientPoxy serviceSign = new ClientPoxy(ServiceType.Type.CJ_SIGNREQ);
	    			PaySignReqSingleInBean inParam = new PaySignReqSingleInBean();
	    			BeanUtils.copyProperties(cjInfo, inParam);
	    			inParam.setTrSerialNo(getFlowNo());
	    			inParam.setSubmitDate(new Date());
	    			inParam.setSn(getIds());
	    			inParam.setProtocolNo(getFlowNo());
	    			inParam.setBeginDate(new Date());
	    			inParam.setEndDate(new Date());
	            	logger.info("【卡号变更】 畅捷签约开始！流水号："+inParam.getTrSerialNo());
	                     // 获取开始时间
	            	BaseOutInfo cyInfo = (BaseOutInfo) serviceSign.callService(inParam);
 	                if("0000".equals(cyInfo.getRetCode())){
   	                    	  bankquery.setProtocolNo(inParam.getProtocolNo());
   	                    	  bankquery.setCjQyNo(inParam.getTrSerialNo());
   	                    	  bankquery.setCjSign("1");
   	                    	  bankDao.updateByAccNo(bankquery);
   	                      }else{
   	                    	 // bankquery.setProtocolNo(inParam.getProtocolNo());
       	                      bankquery.setCjQyNo(inParam.getTrSerialNo());
       	                      bankquery.setCjSign("2");
       	                      String retMsg ="";
               	              if(!ObjectHelper.isEmpty(cyInfo.getRetMsg())){
	               	            	int i = cyInfo.getRetMsg().length();
	        	            		if(i >= 200){
	        	            			retMsg = cyInfo.getRetMsg().substring(0,198);
	        	            		}else{
	        	            			retMsg = cyInfo.getRetMsg();
	        	            		}
               	              }
       	                      bankquery.setCjSignFailure(retMsg);
       	                      bankDao.updateByAccNo(bankquery);
   	                    	  
   	                      logger.error("【卡号变更 】畅捷签约处理异常！"+cyInfo.getRetMsg());
   	                     }
   	                      logger.info("【卡号变更】 畅捷签约结束！流水号："+inParam.getTrSerialNo());
 	              }else{
 	            	   logger.error("【卡号变更 】畅捷实名认证处理异常！"+outInfo.getRetMsg());
	            	   // 设置流程属性值
	            	    
 	 	            	com.creditharmony.loan.borrow.certification.entity.LoanBank bankquery = new com.creditharmony.loan.borrow.certification.entity.LoanBank();
    	            	bankquery.setTrSerialNo(cjInfo.getTrSerialNo());
    	            	bankquery.setCjAuthen("2");
    	            	bankquery.setAccNo(cjInfo.getAccountNo());
    	            	String retMsg ="";
    	            	if(!ObjectHelper.isEmpty(outInfo.getRetMsg())){
    	            		int i = outInfo.getRetMsg().length();
    	            		if(i >= 200){
    	            			retMsg = outInfo.getRetMsg().substring(0,198);
    	            		}else{
    	            			retMsg = outInfo.getRetMsg();
    	            		}
    	            	}
    	            	bankquery.setCjAuthenFailure(retMsg);
    	            	bankDao.updateByAccNo(bankquery);
 	             }
 	            } catch (Exception e) {
 	            e.printStackTrace();
 	        }
	      }
	    }
	}
	
	
	 /**
     * 获得流水号
     * @param no
     * @return
     */
    public String getFlowNo(){
			return "xinhe-chp"+format.format(new Date())+getId();
    }
    /**
	 * 随机产生指定长度的数据字符串
	 * @param length 指定长度
	 * @return 结果
	 */
     public String getId(){
		 UUID uuid= UUID.randomUUID();
     	 String batchNo=uuid.toString().replaceAll("-","");
        batchNo=batchNo.substring(0, 10);
     	 return batchNo;
	 }
     
     /**
  	 * 随机产生指定长度的数据字符串
  	 * @param length 指定长度
  	 * @return 结果
  	 */
       public String getIds(){
  		 UUID uuid= UUID.randomUUID();
       	 String batchNo=uuid.toString().replaceAll("-","");
       	 return batchNo;
  	  }
    
	/**
	 * 生成保存的数据 
	 * 2016年11月19日 
	 * by Huowenlong
	 * 
	 * @param repayAccountApplyView
	 * @param emaliFlag
	 * @return
	 */
	private String getEmailDataToString(RepayAccountApplyView repayAccountApplyView, String emaliFlag, String maintainStauts) {
		DictCache dict = DictCache.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append("<p>客户名:").append(repayAccountApplyView.getCustomerName() == null ? "" : repayAccountApplyView.getCustomerName()).append("</p>");
		sb.append("<p>合同编号:").append(repayAccountApplyView.getContractCode() == null ? "" : repayAccountApplyView.getContractCode()).append("</p>");
		sb.append("<p>客户身份证号:").append(repayAccountApplyView.getCustomerCard() == null ? "" : repayAccountApplyView.getCustomerCard()).append("</p>");
		if ("new".equals(emaliFlag)) {
			sb.append("<p>新邮箱地址:").append(repayAccountApplyView.getUpdatecontent() == null ? "" : repayAccountApplyView.getUpdatecontent()).append("</p>");
		} else if ("old".equals(emaliFlag)) {
			sb.append("<p>旧邮箱地址:").append(repayAccountApplyView.getCustomerEmail() == null ? "" : repayAccountApplyView.getCustomerEmail()).append("</p>");
		}
		sb.append("<p>维护状态:").append(dict.getDictLabel("jk_maintain_status", maintainStauts)).append("</p>");
		return sb.toString();
	}
  
	/**
	 * 邮箱维护信息审核
	 * 2016年11月21日 
	 * By huowenlong 
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveEmailExamineResult")
	public String saveEmailExamineResult(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request) {
		RepayAccountApplyView oldRepayAccountApplyView = repayAccountService.selectAccountInfoById(repayAccountApplyView);
		//获取oldData
		RepayAccountApplyView tepayAccountApplyViewtemp = new RepayAccountApplyView();
		tepayAccountApplyViewtemp.setId(oldRepayAccountApplyView.getOldAccountId());
		tepayAccountApplyViewtemp = repayAccountService.selectAccountInfoById(tepayAccountApplyViewtemp);
		String oldData = getEmailDataToString(tepayAccountApplyViewtemp,"old",oldRepayAccountApplyView.getMaintainStatus());
		oldRepayAccountApplyView.setOldData(oldData);
		oldRepayAccountApplyView.setOperateStep("审核");
		oldRepayAccountApplyView.preUpdate();
		oldRepayAccountApplyView.setMaintainStatus(repayAccountApplyView.getMaintainStatus());
		oldRepayAccountApplyView.setRemarks(repayAccountApplyView.getRemarks());
		String newData = getEmailDataToString(oldRepayAccountApplyView,"new",oldRepayAccountApplyView.getMaintainStatus());
		oldRepayAccountApplyView.setNewData(newData);
		if (MaintainStatus.TO_MAINTAIN.getCode().equals(repayAccountApplyView.getMaintainStatus())) {
			oldRepayAccountApplyView.setRepaymentFlag(RepaymentFlag.REPAYMENT.getCode());
			oldRepayAccountApplyView.setTopFlag(1);
			oldRepayAccountApplyView.setMaintainTime(new Date());
			repayAccountService.throughEmail(oldRepayAccountApplyView);
		} else if (MaintainStatus.TO_REFUSE.getCode().equals(repayAccountApplyView.getMaintainStatus())) {
			repayAccountService.refuseEmail(oldRepayAccountApplyView);
		}
		return "redirect:" + adminPath + "/borrow/account/repayaccount/emailManageList";
	}
	
	/**
	 * 发送邮箱验证
	·* 2016年11月22日
	·* by Huowenlong
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public String sendEmail(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request) {
		String str = "";
		//修改状态
		LoanBankEditEntity lee = new LoanBankEditEntity();
		lee.setId(repayAccountApplyView.getId());
		lee.setEmailFlag("");
		loanBankEditDao.updateByPrimaryKeySelective(lee);
		//链接
		String url = makeEmailParam(repayAccountApplyView.getId());
		//发送邮件
		str = sendEmailMothed(repayAccountApplyView.getCustomerEmail(),url,repayAccountApplyView.getCustomerName());
		return str;
	}
	
	/**
	 * 确认邮箱验证
	·* 2016年11月22日
	·* by Huowenlong
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmEmail")
	public String confirmEmail(RepayAccountApplyView repayAccountApplyView, HttpServletRequest request) {
		String str = "";
		LoanBankEditEntity lee = loanBankEditDao.selectByPrimaryKey(repayAccountApplyView.getId());
		if(lee != null){
			if("1".equals(lee.getEmailFlag())){
				str = "confirmEmail";
			}
		}
		return str;
	}
	
	public static void main(String[] args) {
		RepayAccountController rc = new RepayAccountController();
		rc.makeEmailParam("fd39170e8bb4477cab5b02deb07235e4");
	}
	
	/**
	 * 发送邮件
	·* 2016年11月30日
	·* by Huowenlong
	 * @param email
	 * @param url
	 * @return
	 */
	public String sendEmailMothed(String email,String url,String name){
		String returnStr = "";
		String startImg = "</br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/hjlogo.jpg'></br></br>"; 
		String endImg = "<br></br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/ranmeizhiji.jpg'>";
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_MAIL); 
		MailInfo mailParam = new MailInfo(); 
		String[] toAddrArray = {email}; 
		mailParam.setSubject("信和汇金预留邮箱更改验证");
		mailParam.setToAddrArray(toAddrArray); 
		mailParam.setContent(startImg + "</br></br>尊敬的"+name+"客户您好：</br>"+
		"请您点击下方链接进行邮箱验证，此链接仅用于信和汇金预留邮箱更改验证，请勿回复或转发。</br>"+
		//url+ "\n"+
		"<a href = \""+  url + "\">"+url+"</a></br>" +
		"如有疑问请详询400-090-1199。祝您生活愉快！</br></br>" + endImg);		
		try {
			MailOutInfo out = (MailOutInfo) service.callService(mailParam);
			if("0000".equals(out.getRetCode())){
				returnStr = "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件失败  "+ e.getMessage());
		}
		return returnStr;
	}
	
	/**
	 * 邮箱确认的链接
	·* 2016年11月30日
	·* by Huowenlong
	 * @param emailId
	 * @return   

	 */
	public String makeEmailParam(String emailId){
		String contentj = "";
		String url = Global.getConfig("loan.email.confirm");
		String secretKey = Global.getConfig("loan.email.key"); 
		String secretparmKey = Global.getConfig("loan.email.paramKey");
		try {
			//String key =  DesUtils.encrypt(secretKey , secretKey);
			//String paramKey = DesUtils.encrypt(emailId , secretparmKey);
			String content="{'key':'"+secretKey+"', 'type':'"+"1"+"' , 'paramKey':'"+emailId+"','businessType':'1','sendEmailTime':'"+new Date().getTime()+"'} ";
			contentj = url + DesUtils.encrypt(content,secretparmKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentj;
	}
}
