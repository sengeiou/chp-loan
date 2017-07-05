package com.creditharmony.loan.borrow.transate.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.adapter.core.utils.DesUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.MenuManager;
import com.creditharmony.core.users.type.BaseDeptOrgType;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.DelayEntity;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.service.DeliveryApplyService;
import com.creditharmony.loan.borrow.stores.dao.AreaPreDao;
import com.creditharmony.loan.borrow.transate.dao.LoanInfoDao;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanEmailEdit;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanInfoExport;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanParamsEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TransateEx;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.borrow.transate.service.LoanSendEmail;
import com.creditharmony.loan.borrow.transate.service.TransateService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 信借数据列表
 * @Class Name LoanInfoController
 * @author lirui
 * @Create In 2015年12月3日
 */
@Controller
@RequestMapping(value="${adminPath}/borrow/transate")
public class LoanInfoController extends BaseController {

	@Autowired
	private LoanInfoService loanInfoService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private TransateService ts;
	
	@Autowired
	private DeliveryApplyService das;
	
	@Autowired
	private AreaPreDao areaPreDao;
	
	@Autowired
	private LoanInfoDao dao;
	
	@Autowired
	private LoanSendEmail loanSendEmail;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	private static MenuManager menuManager = SpringContextHolder.getBean(MenuManager.class);
	
	/**
	 * 展示信借数据列表
	 * 2015年12月3日
	 * By lirui
	 * @param request
	 * @param response 
	 * @param params 检索参数
	 * @param m Modle模型
	 * @return 信借数据列表页面
	 */
	@RequestMapping(value = "loanInfo")
	public String loanInfo(HttpServletRequest request,HttpServletResponse response,LoanParamsEx params,Model m) {
		boolean isManager = false;
		//借款人服务部用，如果登陆人是借款人服务部，则查看按钮不可见
		boolean isCanSe =false;
		//申请冻结按钮查看权限，true 可以看，false 不可以看
		boolean seeApplyFrozen = true;
		
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		//是否是借款人服务部或者合同审核小组
		if(!isManager){
			if (BaseDeptOrgType.LOANER_DEPT.key.equals(orgType)
					|| BaseDeptOrgType.CONTRACT_APPROVE_TEAM.key.equals(orgType)
					|| BaseDeptOrgType.CONTRACT_RATEAPPROVE_TEAM.key.equals(orgType)){
				isManager=true;
	    	}
			 if(isManager){
				 //如果是借款人服务部，则查看 申请冻结不可见
				 isCanSe=true;	 
			 }
	     }
		    	
		// 是否是数据管理部
		// 数据管理部 能看见全部的 但不可编辑
		if (!isManager) {
			if (BaseDeptOrgType.DATA_DEPT.key.equals(orgType)) {
				isManager = true;
			}
		}
		//是否是省分公司下  汇金业务部
		if(!isManager){
			//省分公司
	        if (LoanOrgType.PROVINCE_COMPANY.key.equals(orgType)){
	        	isManager=true;
	    	}
	        //汇金业务部
	        if (LoanOrgType.BUISNESS_DEPT.key.equals(orgType)){
	        	isManager=true;
	    	}
			// 区域
			if (LoanOrgType.DISTRICT.key.equals(orgType)) {
				isManager = true;
				List<Role> roleList = UserUtils.getRoleList();
				for (Role role : roleList) {
					//区域业管，查询按钮和申请冻结按钮不可见
					if (LoanRole.DISTRICT_BIZ_MANAGER.id.equals(role.getId())) {
						isCanSe = true;
						seeApplyFrozen = false;
						break;
					}
				}
			}
		}         
    	//是否是门店
		if(!isManager){
	        if (LoanOrgType.STORE.key.equals(orgType)){
	        	isManager=true;
	    	}
	        //如果前几个都不是，那么就一定是门店 ，就只能看见自己的
	        isManager=false;
		}  
		 //---------------------------
		// 检索条件回显
		if (params != null) {
			 if (LoanOrgType.TEAM.key.equals(orgType)){
				 params.setOrgCode(null);
			   }
			m.addAttribute("params",params);
		}
		//数据权限控制
		   String queryRight = DataScopeUtil.getDataScope("z", SystemFlag.LOAN.value);
	       params.setQueryRight(queryRight);
		
	       
	       //如果登陆人是电销团队机构，则增加电销标识
	   	if (LoanOrgType.MOBILE_SALE.key.equals(orgType)   ||  LoanOrgType.MOBILE_SALE_TEAM.key.equals(orgType) ){
	   		//此处是放了一个任意值，确保此此段不是为空，然后SQL判断 T_JK_LOAN_INFO 的组织机构ID不为空 ， 则查询所有的电销菜单
	   		params.setConsTelesalesOrgcode("1");
		   }
	       
	       
//		Page<TransateEx> loanPage = loanInfoService.loanInfo(new Page<TransateEx>(request,response), params);
	   	/* 回退此功能
		//电话号码参数加密
	   	if(StringUtils.isNotEmpty(params.getCustomerPhoneFirstTransate())){
	   		String customerPhoneFirstTransate=(String)EncryptUtils.encrypt(params.getCustomerPhoneFirstTransate(), EncryptTableCol.LOAN_CUSTOMER_MOBILE_1);
	   		params.setCustomerPhoneFirstTransate(customerPhoneFirstTransate);
	   	}
	   	*/
		// 执行数据库查询语句，带条件
		Page<TransateEx> page = new Page<TransateEx>(request, response);
		params.setLimit(page.getPageSize());
		if (page.getPageNo() <= 1) {
			params.setOffset(0);
		} else {
			params.setOffset((page.getPageNo() - 1) * page.getPageSize());
		}
		
		SqlSessionFactory ssf = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		SqlSession ss = ssf.openSession();
		Connection cn = ss.getConnection();
		try {
			MyBatisSql sql = MyBatisSqlUtil.getMyBatisSql(
				"com.creditharmony.loan.borrow.transate.dao.LoanInfoDao.cnt",
				params,
				ssf
			);
			PreparedStatement ps = cn.prepareStatement(sql.toString());
			ResultSet rsCnt = ps.executeQuery();
			int cnt = 0;
			if (rsCnt.next()) {
				cnt = rsCnt.getInt(1); 
			}
			
			if (cnt > 0) {
				sql = MyBatisSqlUtil.getMyBatisSql(
					"com.creditharmony.loan.borrow.transate.dao.LoanInfoDao.loanInfo",
					params,
					ssf
				);
				ps = cn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				
				List<TransateEx> ls = new ArrayList<TransateEx>();
				while (rs.next()) {
					TransateEx rec = new TransateEx();
					rec.setId(rs.getString("id"));
					rec.setLoanCode(rs.getString("LOAN_CODE"));
					rec.setApplyId(rs.getString("APPLY_ID"));
					rec.setFrozenApplyTimes(rs.getInt("frozen_apply_times"));
					rec.setFrozenCode(rs.getString("frozen_code"));
					rec.setFrozenLastApplyTime(DateUtil.StringToDate(rs.getString("frozen_last_apply_time"), "yyyy-MM-dd HH:mm:ss"));
					//省份
					rec.setProvinceId(rs.getString("PROVINCE_ID"));
					rec.setProvinceName(rs.getString("PROVINCE_NAME"));
					//城市
					rec.setCityId(rs.getString("CITY_ID"));
					rec.setCityName(rs.getString("CITY_NAME"));
					
					rec.setLoanTeamOrgid(rs.getString("LOAN_TEAM_ORGID"));
					//门店
					rec.setStoreCode(rs.getString("STORE_CODE"));
					//借款组织机构ID
					rec.setLoanStoreOrgId(rs.getString("LOAN_STORE_ORGID"));
					rec.setLoanStoreOrgName(rs.getString("LOAN_STORE_ORG_NAME"));
					
					rec.setModel(rs.getString("MODEL"));
					rec.setDictPayStatus(rs.getString("DICT_PAY_STATUS"));
					
					rec.setContractCode(rs.getString("CONTRACT_CODE"));						// 合同编号
					rec.setContractVersion(rs.getString("CONTRACT_VERSION_SHOW")); //合同版本号
					
					
					rec.setLoanCustomerName(rs.getString("LOAN_CUSTOMER_NAME"));			// 客户姓名						
					rec.setCoroName(rs.getString("CORO_NAME"));					//共借人或自然人保证人
					rec.setProductName(rs.getString("PRODUCT_NAME"));						// 产品
					rec.setAuditProductName(rs.getString("AUDIT_PRODUCT_NAME"));						// 产品
					rec.setDictLoanStatus(rs.getString("DICT_LOAN_STATUS"));				// 状态
					rec.setDictLoanStatusLabel(rs.getString("dictLoanStatusLabel"));		// 状态
					
					rec.setMoney(rs.getBigDecimal("LOAN_AUDIT_AMOUNT"));					// 批复金额	
					rec.setContractMonths(rs.getBigDecimal("LOAN_AUDIT_MONTHS"));			// 批复分期
					
					rec.setLoanIsUrgent(rs.getString("LOAN_URGENT_FLAG"));					// 加急标识
					
					rec.setLoanIsPhone(rs.getString("CUSTOMER_TELESALES_FLAG"));			// 是否电销	
					rec.setLoanIsPhoneLabel(rs.getString("loanIsPhoneLabel"));				// 是否电销
					rec.setPaperless(rs.getString("PAPERLESS_FLAG"));                       //是否无纸化
					
					rec.setTeamManagerName(rs.getString("TEAM_NAME"));						// 团队经理
					
					rec.setUserName(rs.getString("MANAGER_NAME"));							// 销售人员
//					if (StringUtils.isNotEmpty(rs.getString("MANAGER_NAME"))) {
//						String manageName = areaPreDao.getUserName(rs.getString("MANAGER_NAME"));
//						rec.setUserName(manageName);
//					}
//					rec.setLoanTeamManagercode(rs.getString("LOAN_TEAM_MANAGERCODE"));
					
					rec.setLoanApplyTime(rs.getDate("loan_apply_time"));					// 申请时间
					rec.setCustomerIntoTime(rs.getDate("customer_into_time"));				// 进件时间	
					
					rec.setLoanSurveyEmpName(rs.getString("empname"));						// 外访人员
//					rec.setLoanSurveyEmpId(rs.getString("loan_survey_emp_id"));	
					
					rec.setLoanCustomerServiceName(rs.getString("servicename"));			// 客服
					
					rec.setLoanIsRaise(rs.getString("LOAN_RAISE_FLAG"));					// 上调标识
					rec.setLoanIsRaiseLable(rs.getString("loanIsRaiseLable"));				// 上调标识
					
					rec.setLoanMarking(rs.getString("LOAN_FLAG"));							// 渠道
					rec.setLoanMarkingLable(rs.getString("loanMarkingLable"));				// 渠道
					rec.setLoanInfoOldOrNewFlag(rs.getString("LOANINFO_OLDORNEW_FLAG"));   //新旧版本查看切换标识
					rec.setRevisitStatus(rs.getString("revisit_status"));                   // 回访状态
					rec.setCustomerPhoneFirstTransate(rs.getString("customer_phone_first")); //手机号
					rec.setBestCoborrower(rs.getString("bestCoborrower"));
					rec.setSendStatus(rs.getString("dict_mail_status"));
					rec.setSendEmailStatus(rs.getString("send_email_status"));
					rec.setIssplit(rs.getString("issplit"));
					rec.setZcjRejectFlag(rs.getString("zcjRejectFlag"));
					rec.setCustomerEmail(rs.getString("customer_email")); 
					rec.setEmailIfConfirm(rs.getString("email_if_confirm"));
					ls.add(rec);
				}
				/* 回退此功能
				EncryptUtils.decryptMulti(ls); //手机号批量解密
				//手机号中间四位替换成*
				if(ls!=null && ls.size()>0){
					for (int i = 0; i < ls.size(); i++) {
						TransateEx transateEx=ls.get(i);
						String customerPhoneFirstTransate=transateEx.getCustomerPhoneFirstTransate();
						if(StringUtils.isNotEmpty(customerPhoneFirstTransate)){
							customerPhoneFirstTransate=customerPhoneFirstTransate.replaceAll(customerPhoneFirstTransate.substring(3,7),"****");
							transateEx.setCustomerPhoneFirstTransate(customerPhoneFirstTransate);
						}
					}
				}
				*/
				
				page.setCount(cnt);
				page.setList(ls);
			} else {
				page.setCount(0);
				page.setList(null);
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	   	
		// 缓存取码值
//		List<TransateEx> list = page.getList();
//		for (TransateEx transateEx : list) {
//			transateEx.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_apply_status", transateEx.getDictLoanStatus()));
//			transateEx.setLoanIsPhoneLabel(DictCache.getInstance().getDictLabel("jk_telemarketing", transateEx.getLoanIsPhone()));
//			transateEx.setLoanIsRaiseLable(DictCache.getInstance().getDictLabel("jk_raise_flag", transateEx.getLoanIsRaise()));
//			transateEx.setLoanMarkingLable(DictCache.getInstance().getDictLabel("jk_channel_flag", transateEx.getLoanMarking()));
//		}		
		
		// 获得所有产品列表(检索条件)
		List<String> products = loanInfoService.products();
		
		com.creditharmony.loan.borrow.transate.web.LoanApplyStatus[] statusList = com.creditharmony.loan.borrow.transate.web.LoanApplyStatus.values();
		List<Map<String,String>> queryList = new ArrayList<Map<String,String>>();
		Map<String,String> curMap=null;
		for(LoanApplyStatus status:statusList){
	        curMap = new HashMap<String,String>();
	        curMap.put("code", status.getCode());
	        curMap.put("name", status.getName());
	        queryList.add(curMap);
		}
		//-----------------------------------
		String orgName = org != null ? org.getName() : "";
		boolean isManagerMd =true;
		//如果登录是门店 则门店选择框不可见
		if (LoanOrgType.STORE.key.equals(orgType)){
			isManager=false;
	    //如果是门店 则 默认门店框体选项
			params.setOrgCode(orgId);
			params.setOrgName(orgName);
		   }else if (LoanOrgType.TEAM.key.equals(orgType)){
			   isManagerMd=false;
	    //如果是门店 则 默认门店框体选项
			   params.setOrgCode(orgId);
			   params.setOrgName(orgName);
		   }
		//---------------------------------------
		   Map<String,Object> versionParam = new HashMap<String,Object>();
			versionParam.put("status", YESNO.YES.getCode());  // 1 启用
			versionParam.put("dictFlag",YESNO.NO.getCode());  // 0 主合同
//	        List<String> versions = contractService.getContractVersion(versionParam);
//	        if(!ObjectHelper.isEmpty(versions)){
//	            m.addAttribute("curVersion", versions.get(0));
//	        }
		
		//---------------------------------------
	    /* 回退此功能
	    //电话号码参数解密
		if(StringUtils.isNotEmpty(params.getCustomerPhoneFirstTransate())){
		   	String customerPhoneFirstTransate=(String)EncryptUtils.decrypt(params.getCustomerPhoneFirstTransate(), EncryptTableCol.LOAN_CUSTOMER_MOBILE_1);
		   	params.setCustomerPhoneFirstTransate(customerPhoneFirstTransate);
		}
		*/
		
		m.addAttribute("isManagerMd", isManagerMd);
		//-----------------------------------
		// 获得所有门店
		List<OrgView> orgs = das.orgs();
		
		String menuId = (String) request.getParameter("menuId");
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		
		List<String> roleIds = user.getRoleIdList();
		
		String role=YESNO.NO.getCode();
		if (!ObjectHelper.isEmpty(roleIds)) {
			for (String roleId : roleIds) {
				if (LoanRole.STORE_ASSISTANT.id.equals(roleId) || LoanRole.STORE_MANAGER.id.equals(roleId)) {
					role = YESNO.YES.getCode();
					break;
				}
			}
		
		}
		m.addAttribute("role", role);
		List<String> resKeyList = menuManager.findResourceAuthNotIn(user.getId(), user.getDepartment().getId(), menuId);
		for (String resKey:resKeyList) {
			m.addAttribute(resKey, 1);			
		}
//		String ZCJversion=ContractVer.VER_ONE_ZERO_ZCJ.getName();
		m.addAttribute("queryList", queryList);
		m.addAttribute("isCanSe", isCanSe);
		m.addAttribute("isManager", isManager);
		m.addAttribute("seeApplyFrozen", seeApplyFrozen);
		m.addAttribute("query", "loan");
		m.addAttribute("orgs", orgs);
		m.addAttribute("loanPage", page);
		m.addAttribute("products",products);
//		m.addAttribute("ZCJversion",ZCJversion);
		String curVersion = Global.getConfig("presentversion");
		m.addAttribute("curVersion", curVersion);
		return "transate/loanInfo";
	}
	
//	
//	
//	
//	public void bir(){
//		
//		
//		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
//		String orgId = currentUser.getDepartment().getId();
//		Org org = OrgCache.getInstance().get(orgId);
//		String orgType = org != null ? org.getType() : "";
//		//是否是借款人服务部
//		    	if (BaseDeptOrgType.LOANER_DEPT.key.equals(orgType)){
//		    	
//		    	}
//	    //是否是数据管理部   	
//		    	if (BaseDeptOrgType.DATA_DEPT.key.equals(orgType)){
//			    	
//		    	}
//		//是否是门店
//	            if (LoanOrgType.STORE.key.equals(orgType)){
//			    	
//		    	}
//		//是否是省分公司下  汇金业务部
//                if (LoanOrgType.BUISNESS_DEPT.key.equals(orgType)){
//			    	
//		    	}
//	            
//	            
//	}
//	
//	
	@ResponseBody
	@RequestMapping(value = "customerEmailInfo")
	public LoanEmailEdit customerEmailInfo(HttpServletRequest request,HttpServletResponse response,String loanCode) {
		LoanEmailEdit loanEmailEdit = dao.selectEmailByLoanCode(loanCode);
		return loanEmailEdit;
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
	public String sendEmail(LoanEmailEdit loanEmailEdit) {
		LoanCustomer loanCustomer = new LoanCustomer();
		loanCustomer.setId(loanEmailEdit.getId());
		loanCustomer.setEmailIfConfirm("0");
		loanCustomerDao.updateCustomerEmailConfirm(loanCustomer);
		String url = makeEmailParam(loanEmailEdit.getId());
		String returnStr = loanSendEmail.sendEmailMothed(loanEmailEdit.getEmail(), url, loanEmailEdit.getCustomerName());
		return returnStr;
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
	public String confirmEmail(String id) {
		String str = "";
		LoanCustomer loanCustomer = loanCustomerDao.checkEmailConfirm(id);
		if(loanCustomer != null && "1".equals(loanCustomer.getEmailIfConfirm())){
			str = "confirmEmail";
		}
		return str;
	}
	
	
	@RequestMapping(value = "customerEmailEdit")
	public String customerEmailEdit(HttpServletRequest request,HttpServletResponse response,LoanEmailEdit editInfo) {
		dao.updateCustomer(editInfo);
		return "redirect:" + adminPath + "/borrow/transate/loanInfo?menuId="+editInfo.getMenuId();
	}
	
	public String makeEmailParam(String emailId){
		String contentj = "";
		String url = Global.getConfig("loan.email.confirm");
		String secretKey = Global.getConfig("loan.email.key"); 
		String secretparmKey = Global.getConfig("loan.email.paramKey");
		try {
			//String key =  DesUtils.encrypt(secretKey , secretKey);
			//String paramKey = DesUtils.encrypt(emailId , secretparmKey);
			String content="{'key':'"+secretKey+"', 'type':'"+"1"+"' , 'paramKey':'"+emailId+"','businessType':'4','sendEmailTime':'"+new Date().getTime()+"'} ";
			contentj = url + DesUtils.encrypt(content,secretparmKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentj;
	}
	
	
	@RequestMapping(value = "loanInfoExcelExport")
	public void loanInfoExcelExport(HttpServletRequest request,HttpServletResponse response,LoanParamsEx params,Model m) {
		boolean isManager = false;
		//借款人服务部用，如果登陆人是借款人服务部，则查看按钮不可见
		boolean isCanSe =false;
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		String orgId = currentUser.getDepartment().getId();
		Org org = OrgCache.getInstance().get(orgId);
		String orgType = org != null ? org.getType() : "";
		//是否是借款人服务部或者合同审核小组
		if(!isManager){
			if (BaseDeptOrgType.LOANER_DEPT.key.equals(orgType)
					|| BaseDeptOrgType.CONTRACT_APPROVE_TEAM.key.equals(orgType)
					|| BaseDeptOrgType.CONTRACT_RATEAPPROVE_TEAM.key.equals(orgType)){
				isManager=true;
	    	}
			 if(isManager){
				 //如果是借款人服务部，则查看 申请冻结不可见
				 isCanSe=true;	 
			 }
	     }
		    	
		// 是否是数据管理部
		// 数据管理部 能看见全部的 但不可编辑
		if (!isManager) {
			if (BaseDeptOrgType.DATA_DEPT.key.equals(orgType)) {
				isManager = true;
			}
		}
		//是否是省分公司下  汇金业务部
		if(!isManager){
			//省分公司
	        if (LoanOrgType.PROVINCE_COMPANY.key.equals(orgType)){
	        	isManager=true;
	    	}
	        //汇金业务部
	        if (LoanOrgType.BUISNESS_DEPT.key.equals(orgType)){
	        	isManager=true;
	    	}
		}         
    	//是否是门店
		if(!isManager){
	        if (LoanOrgType.STORE.key.equals(orgType)){
	        	isManager=true;
	    	}
	        //如果前几个都不是，那么就一定是门店 ，就只能看见自己的
	        isManager=false;
		}  
		 //---------------------------
		// 检索条件回显
		if (params != null) {
			 if (LoanOrgType.TEAM.key.equals(orgType)){
				 params.setOrgCode(null);
			   }
			m.addAttribute("params",params);
		}
		//数据权限控制
		   String queryRight = DataScopeUtil.getDataScope("z", SystemFlag.LOAN.value);
	       params.setQueryRight(queryRight);
		
	       
	       //如果登陆人是电销团队机构，则增加电销标识
	   	if (LoanOrgType.MOBILE_SALE.key.equals(orgType)   ||  LoanOrgType.MOBILE_SALE_TEAM.key.equals(orgType) ){
	   		//此处是放了一个任意值，确保此此段不是为空，然后SQL判断 T_JK_LOAN_INFO 的组织机构ID不为空 ， 则查询所有的电销菜单
	   		params.setConsTelesalesOrgcode("1");
		   }
		SqlSessionFactory ssf = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		SqlSession ss = ssf.openSession();
		Connection cn = ss.getConnection();
		try {
			final String fileName = "邮箱补录信息表";
			List<LoanInfoExport> list = dao.loanInfoEmailExport(params);
			ExcelUtils excelutil = new ExcelUtils();
			excelutil.exportExcel(list,fileName,null,LoanInfoExport.class,FileExtension.XLS, FileExtension.OUT_TYPE_TEMPLATE,response, null);
			
			/*final int MAXCOLUMN = 6;
			
			SXSSFWorkbook wb = new SXSSFWorkbook();
			//String fileSheetName = threePartFileName.getGoldCreditSumExportFileName();
			Sheet dataSheet = wb.createSheet();
			summarySheet(wb,dataSheet,fileName,MAXCOLUMN);	
			
			MyBatisSql sql = MyBatisSqlUtil.getMyBatisSql(
				"com.creditharmony.loan.borrow.transate.dao.LoanInfoDao.loanInfoEmailExport",
				params,
				ssf
			);
			PreparedStatement ps = cn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			
			List<TransateEx> ls = new ArrayList<TransateEx>();
			int row = 1;
			CellStyle style = wb.createCellStyle();
		        style.setBorderBottom((short) 1);   
		        style.setBorderTop((short) 1);
		        style.setBorderLeft((short) 1);
		        style.setBorderRight((short) 1);
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        Font font = wb.createFont();
		        font.setFontHeightInPoints((short)10);
		        style.setFont(font);
			
			Row dataRow;
			while (rs.next()) {
				int i = 0;
				dataRow = dataSheet.createRow(row);
				Cell contractCode = dataRow.createCell(i++);
				contractCode.setCellStyle(textStyle(wb));
				contractCode.setCellValue(rs.getString("CONTRACT_CODE"));
				
				dataRow = dataSheet.createRow(row);
				Cell customerName = dataRow.createCell(i++);
				customerName.setCellStyle(textStyle(wb));
				customerName.setCellValue(rs.getString("LOAN_CUSTOMER_NAME"));
				
				dataRow = dataSheet.createRow(row);
				Cell storeName = dataRow.createCell(i++);
				storeName.setCellStyle(textStyle(wb));
				storeName.setCellValue(rs.getString("LOAN_STORE_ORG_NAME"));
				
				dataRow = dataSheet.createRow(row);
				Cell contractEndDay = dataRow.createCell(i++);
				contractEndDay.setCellStyle(textStyle(wb));
				contractEndDay.setCellValue(rs.getString("contract_end_day"));
				
				dataRow = dataSheet.createRow(row);
				Cell dictLoanStatusLabel = dataRow.createCell(i++);
				dictLoanStatusLabel.setCellStyle(textStyle(wb));
				dictLoanStatusLabel.setCellValue(rs.getString("dictLoanStatusLabel"));
				
				dataRow = dataSheet.createRow(row);
				Cell emailFalg = dataRow.createCell(i++);
				emailFalg.setCellStyle(textStyle(wb));
				emailFalg.setCellValue(rs.getString("emailFalg"));
				
				row = row + 1;
			}
			
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName+ FileExtension.XLSX)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName+ FileExtension.XLSX));
			wb.write(response.getOutputStream());
			wb.dispose();*/
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void summarySheet(SXSSFWorkbook wb,Sheet dataSheet,String fileName,int MAXCOLUMN) {
		Row titleRow = dataSheet.createRow(0);
		titleRow.setHeight((short) (15.625*40)); 
		Cell titleCell = titleRow.createCell(0);
		dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,MAXCOLUMN));
		//创建样式
		CellStyle style = wb.createCellStyle(); 
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平
		
        //创建字体
        Font font = wb.createFont();
        //字体位置  上 下 左 右
        //font.setTypeOffset((short)0);
        //字体宽度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //字体高度
        font.setFontHeightInPoints((short)16);
        style.setFont(font);
        titleCell.setCellValue(fileName);
        titleCell.setCellStyle(style);
        
        style = wb.createCellStyle();
        style.setBorderBottom((short) 1);   
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setFillPattern(CellStyle.FINE_DOTS);
        style.setFillBackgroundColor(new HSSFColor.DARK_BLUE().getIndex());
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(new HSSFColor.WHITE().getIndex());
        font.setFontHeightInPoints((short)10);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        
        String[] header = {"合同编号","客户姓名","门店名称","合同到期日","有无邮箱"};
		Row headerRow = dataSheet.createRow(1);
		for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
	}
	
	private static CellStyle textStyle(SXSSFWorkbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom((short) 1);   
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
		DataFormat format = wb.createDataFormat();  
        cellStyle.setDataFormat(format.getFormat("@"));
        return cellStyle;
	}
	
}
