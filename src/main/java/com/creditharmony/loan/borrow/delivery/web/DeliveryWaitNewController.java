package com.creditharmony.loan.borrow.delivery.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryCountReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.service.DeliveryService;
import com.creditharmony.loan.borrow.delivery.service.DeliveryTaskNewService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.OrgInfoService;
/**
* @ClassName: DeliveryWaitNewController
* @Description: TODO 交割信息
* @author meiqingzhang
* @date 2017年3月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/delivery")
public class DeliveryWaitNewController extends BaseController {
	
	@Autowired
	private DeliveryService des;
	@Autowired
	private DeliveryTaskNewService deTaskService;
	@Autowired
	private OrgInfoService orgInfoService;
	/**
	* @Title: deliveryWaitList
	* @Description: TODO(待交割列表页面)
	* @param  request
	* @param  response
	* @param  params
	* @param  m
	* @param     设定文件
	* @return String    返回类型
	 */
	@RequestMapping(value = "deliveryWaitList")
	public String deliveryWaitList(HttpServletRequest request,HttpServletResponse response,DeliveryReq params,Model m,RedirectAttributes attr) {
		String custName=request.getParameter("custName");
		String custCode=request.getParameter("custCode");
		String deliveryResult=request.getParameter("deliveryResult");
		String typeRole=request.getParameter("typeRole");
		String message = request.getParameter("message");
		/*if("1".equals(message)){
			m.addAttribute("message","交割成功！");
		}else if("2".equals(message)){
			m.addAttribute("message","交割失败！");
		}else{
			m.addAttribute("message", "");
		}*/
		m.addAttribute("message",message);
		m.addAttribute("params",params);
		// 如果搜索条件不为空 ,将检索条件存到session中
		if ((custName!=null && !"".equals(custName)) || (custCode!=null && !"".equals(custCode)) || 
				(deliveryResult!=null && !"".equals(deliveryResult)) || (typeRole!=null && !"".equals(typeRole))){
			 List<Role> roleList = UserUtils.getRoleList();
			/*
			 * 如使用前线账号对后线人员进行搜索，或者使用后线账号对前线人员进行搜索，系统也需要给出提示“无查询权限，请更换角色！”，交割管理员账号无角色限制；
			 *  --6240000001  团队经理          
			   --6240000010 客户经理
			   --6240000002  客户经理  
			   --6230000004   客服  后线
			 */ 
			 String teamManager=LoanRole.TEAM_MANAGER.id;   //团队经理   前线账号
			 String manager=LoanRole.FINANCING_MANAGER.id;   //客户经理    前线账号
			 String managerOne=LoanRole.STORE_FINANCING_MANAGER.id; //客户经理   前线账号
			 String service=LoanRole.CUSTOMER_SERVICE.id;   //客服  后线账号
			 if(null!=roleList && roleList.size()>0){ 
				
				 if((teamManager.equals(roleList.get(0).getId()) || manager.equals(roleList.get(0).getId()) || managerOne.equals(roleList.get(0).getId())) && "1".equals(typeRole)){
					 m.addAttribute("message","无查询权限，请更换角色！");
					 return "apply/delivery/deliveryWaitNew";
				 }
				 if(service.equals(roleList.get(0).getId())  && ("2".equals(typeRole) || "3".equals(typeRole))){
					 m.addAttribute("message","无查询权限，请更换角色！");
					 return "apply/delivery/deliveryWaitNew";
				 }
			 }
			
			// 获取待交割列表并封装到Modle中
			Page<DeliveryViewExNew> delPage = des.deliveryList(new Page<DeliveryViewExNew>(request, response), params);
			List<DeliveryViewExNew> list = delPage.getList();
			for (DeliveryViewExNew deliveryViewExNew : list) {
				deliveryViewExNew.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_apply_status", deliveryViewExNew.getDictLoanStatus()));
			}
			m.addAttribute("delPage", delPage);	
		}
		return "apply/delivery/deliveryWaitNew";
	}
	
	/**
	* @Title: deliveryWaitOneList
	* @Description: TODO(单条被交割列表页面)
	* @param  request
	* @param  response
	* @param  params
	* @param  m
	* @param     设定文件
	* @return String    返回类型
	 */
	@RequestMapping(value = "deliveryWaitOneList")
	public String deliveryWaitOneList(HttpServletRequest request,HttpServletResponse response,DeliveryReq params,Model m) {
		String custNameOne=request.getParameter("custNameOne");
		String custCodeOne=request.getParameter("custCodeOne");
		String typeRoleOne=request.getParameter("typeRoleOne");
		m.addAttribute("paramsOne",params);
		// 如果搜索条件不为空 ,将检索条件存到session中
		if ((custNameOne!=null && !"".equals(custNameOne)) || (custCodeOne!=null && !"".equals(custCodeOne)) || 
				(typeRoleOne!=null && !"".equals(typeRoleOne))){
			// 获取待交割列表并封装到Modle中
			Page<DeliveryViewExNew> delPage = des.deliveryListSingle(new Page<DeliveryViewExNew>(request, response), params);
			List<DeliveryViewExNew> list = delPage.getList();
			for (DeliveryViewExNew deliveryViewExNew : list) {
				deliveryViewExNew.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_apply_status", deliveryViewExNew.getDictLoanStatus()));
			}
			m.addAttribute("delPageOne", delPage);	
		}
		return "apply/delivery/deliveryWaitNewSingle";
	}
	
	/**
	* @Title: updateLoanInfoDelivery
	* @Description: TODO(单条交割更新loanInfo)
	* @param  dv
	* @param     设定文件
	* @return String    返回类型
	 */
	@RequestMapping(value = "updateLoanInfoDelivery")
	public String updateLoanInfoDelivery(DeliveryViewExNew dv,Model m,DeliveryReq params,RedirectAttributes attr) {
		//成功为1 ，失败为2 点击【办理】按钮完成交割，交割成功与否均需给出提示：“交割成功！/交割失败！”；
		int upLoanDel=des.updateLoanInfoDelivery(dv);
		String message =null;
		if(upLoanDel<0){
			//message="2";
			message="交割失败！";
		}else{
			//message="1";
			message="交割成功！";
		}
		attr.addAttribute("typeRole", params.getTypeRoleP());
		attr.addAttribute("custName", params.getCustNameP());
		attr.addAttribute("custCode", params.getCustCodeP());
		attr.addAttribute("deliveryResult", params.getDeliveryResultP());
		attr.addAttribute("message", message);
		return	"redirect:"+adminPath+"/borrow/delivery/deliveryWaitList";	
	}
	
	/**
	* @Title: deliveryWaitBatchList
	* @Description: TODO(批量被交割列表)
	* @param  request
	* @param  response
	* @param  params
	* @param  loanCodes
	* @param  typeRoleP
	* @param  m
	* @param    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "deliveryWaitBatchList")
	public String deliveryWaitBatchList(HttpServletRequest request,HttpServletResponse response,DeliveryReq params,Model m) {
		String custNameOne=request.getParameter("custNameOne");
		String custCodeOne=request.getParameter("custCodeOne");
		String typeRoleOne=request.getParameter("typeRoleOne");
		m.addAttribute("paramsOne",params);
		// 如果搜索条件不为空 ,将检索条件存到session中
		if ((custNameOne!=null && !"".equals(custNameOne)) || (custCodeOne!=null && !"".equals(custCodeOne)) || 
				(typeRoleOne!=null && !"".equals(typeRoleOne))){
			// 获取待交割列表并封装到Modle中
			Page<DeliveryViewExNew> delPage = des.deliveryListSingle(new Page<DeliveryViewExNew>(request, response), params);
			List<DeliveryViewExNew> list = delPage.getList();
			for (DeliveryViewExNew deliveryViewExNew : list) {
				deliveryViewExNew.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_apply_status", deliveryViewExNew.getDictLoanStatus()));
			}
			m.addAttribute("delPageOne", delPage);	
		}
		return "apply/delivery/deliveryWaitNewBatch";
	}
	
	/**
	* @Title: updateLoanInfoDelBatch
	* @Description: TODO(批量交割更新loanInfo)
	* @param  request
	* @param  m
	* @param  loanCodes
	* @param  dv
	* @param    设定文件
	* @return String    返回类型
	 */
	@RequestMapping(value = "updateLoanInfoDelBatch")
	public String updateLoanInfoDelBatch(HttpServletRequest request,Model m,String[] loanCodes,DeliveryViewExNew dv,DeliveryReq params,RedirectAttributes attr) {
		//成功为1 ，失败为2 点击【办理】按钮完成交割，交割成功与否均需给出提示：“交割成功！/交割失败！”；
		int status = des.updateLoanInfoDelBatch(dv, loanCodes);
		String message =null;
		if(status<0){
			//message="2";
			message="交割失败！";
		}else{
			//message="1";
			message="交割成功！";
		}
		attr.addAttribute("typeRole", params.getTypeRoleP());
		attr.addAttribute("custName", params.getCustNameP());
		attr.addAttribute("custCode", params.getCustCodeP());
		attr.addAttribute("deliveryResult", params.getDeliveryResultP());
		attr.addAttribute("message", message);
		return	"redirect:"+adminPath+"/borrow/delivery/deliveryWaitList";	
	}
	
	/**
	* @Title: deliveryCountView
	* @Description: TODO(交割统计页面)
	* @param  request
	* @param  response
	* @param  params
	* @param  m
	* @param  attr
	* @param     设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "deliveryCountView")
	public String deliveryCountView(HttpServletRequest request,HttpServletResponse response,Model m,RedirectAttributes attr) {
		List<OrgView> orgs = des.orgs();
		m.addAttribute("orgs", orgs);
		return "apply/delivery/deliveryCount";
	}
	
	/**
	* @Title: deliveryCountQuery
	* @Description: TODO(交割统计列表)
	* @param @param request
	* @param @param response
	* @param @param params
	* @param @param m
	* @param @param attr
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "deliveryCountQuery")
	public String deliveryCountQuery(HttpServletRequest request,HttpServletResponse response,DeliveryCountReq params,Model m,RedirectAttributes attr) {
		List<OrgView> orgs = des.orgs();
		m.addAttribute("orgs", orgs);
		
		// 定义日期格式
		String fmt = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		// 如果搜索起始时间不为空,将起始时间转换成字符串另存为startTime,并封装到params中,留在搜索条件中
		if (params.getStartDate() != null) {
			params.setStartTime(sdf.format(params.getStartDate()));
		}
		// 如果搜索结束时间不为空,将结束时间转换成字符串另存为endTime,并封装到params中,留在搜索条件中
		Date endDate = params.getEndDate();
		if (endDate != null) {
			params.setEndTime(sdf.format(endDate));
			SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date parse = sdfNew.parse(params.getEndTime()+" 23:59:59");
				params.setEndDate(parse);
			} catch (ParseException e) {
				logger.error("交割统计列表中时间转换错误",e);
				m.addAttribute("message","时间转换错误。");
			}
		}
		//查询组织机构下所有门店
		List<OrgInfo> orgInfoList=orgInfoService.queryDeliveryByOrgId(params.getOrgCode());
		int count=0;
		for (OrgInfo orgInfo : orgInfoList) {
			params.setLoanStoreOrgid(orgInfo.getId());
			int loanInfoByStoreId = des.loanInfoByStoreId(params);
			count=loanInfoByStoreId+count;
		}
		m.addAttribute("params",params);
		m.addAttribute("count","成功交割："+count+"条");
		return "apply/delivery/deliveryCount";
	}
	/**
	* @Title: importResult
	* @Description: TODO(交割导入excel)
	* @param request
	* @param  model
	* @param  file
	* @param  redirectAttributes
	* @param     设定文件
	* @return String    返回类型
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request, Model model,@RequestParam(value = "file", required = false) MultipartFile file,
			RedirectAttributes redirectAttributes) {
		String message = null;
		try {
			deTaskService.deleteDelivery();
			ExcelUtils excelUtil = new ExcelUtils();
			List<DeliveryViewExNew> dataList = (List<DeliveryViewExNew>) excelUtil.importExcel("",
					LoanFileUtils.multipartFile2File(file), 2, 0,DeliveryViewExNew.class);
			if (ArrayHelper.isNotEmpty(dataList)) {
				/*1、校验待交割数据准确性（系统是否存在以下字段，及以下字段对应关系是否准确）：合同编号 —> 客户姓名 —> 角色姓名 —> 角色员工编号；
	            2、校验被交割数据准确性（系统是否存在以下字段，及以下字段对应关系是否准确）：角色姓名 —> 角色员工编号；
	            3、校验待交割数据与被交割数据是否为同类型角色；
	            4、待交割数据与被交割数据准确，对应关系准确，且为同类型角色，那么匹配成功，否则匹配失败，*/

				for (DeliveryViewExNew deliveryViewExNew : dataList) {
					if("".equals(deliveryViewExNew.getContractCode()) || null==deliveryViewExNew.getContractCode()){
						DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
						deliveryInsert(deliveryViewExNew, deliveryOne);
						deliveryOne.setDeliveryResult("2");
						deliveryOne.setRejectedReason("合同编号不能为空");
						des.insertDelivery(deliveryOne);
						continue;
					}
					if("".equals(deliveryViewExNew.getLoanCustomerName()) || null==deliveryViewExNew.getLoanCustomerName()){
						DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
						deliveryInsert(deliveryViewExNew, deliveryOne);
						deliveryOne.setDeliveryResult("2");
						deliveryOne.setRejectedReason("客户名称不能为空");
						des.insertDelivery(deliveryOne);
						continue;
					}
					String contractCode=deliveryViewExNew.getContractCode().trim();		
					Contract contract= des.findByContractCode(contractCode);
					String loanStoreOrgid="";
					String loanTeamOrgId="";
					
					if(null!=contract){
						if("".equals(contract.getLoanCode()) || null==contract.getLoanCode()){
							DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
							deliveryInsert(deliveryViewExNew, deliveryOne);
							deliveryOne.setDeliveryResult("2");
							deliveryOne.setRejectedReason("查询出数据库中的借款编码为空");
							des.insertDelivery(deliveryOne);
							continue;
						}else{
							
							DeliveryViewExNew loanInfoByLoanCode = des.loanInfoByLoanCode(contract.getLoanCode());
							
							//1、校验待交割数据准确性（系统是否存在以下字段，及以下字段对应关系是否准确）：合同编号 —> 客户姓名 —> 角色姓名 —> 角色员工编号；
							if(!deliveryViewExNew.getLoanCustomerName().equals(loanInfoByLoanCode.getLoanCustomerName())){
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("查询出数据库中的客户名称与文档中的客户姓名不一样。");
								des.insertDelivery(deliveryOne);
								continue;
							}
							//交割前数据都为空
							if(("".equals(deliveryViewExNew.getStoresName()) || null==deliveryViewExNew.getStoresName())
								&& ("".equals(deliveryViewExNew.getTeamManagerCode()) || null==deliveryViewExNew.getTeamManagerCode())
								&& ("".equals(deliveryViewExNew.getManagerCode()) || null==deliveryViewExNew.getManagerCode())
								&& ("".equals(deliveryViewExNew.getCustomerServicesCode()) || null==deliveryViewExNew.getCustomerServicesCode())
							){
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("文档中原所属信息门店、团队经理员工号、客户经理员工号和客服员工号都为空。");
								des.insertDelivery(deliveryOne);
								continue;
							}else{
								//判断借款状态是否是放款成功的'70','87','88','89','90'
								if(!"".equals(loanInfoByLoanCode.getDictLoanStatus()) && null!=loanInfoByLoanCode.getDictLoanStatus() 
										&& !"70".equals(loanInfoByLoanCode.getDictLoanStatus())
										&& !"87".equals(loanInfoByLoanCode.getDictLoanStatus())
										&& !"88".equals(loanInfoByLoanCode.getDictLoanStatus())
										&& !"89".equals(loanInfoByLoanCode.getDictLoanStatus())
										&& !"90".equals(loanInfoByLoanCode.getDictLoanStatus())
								){
									DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
									deliveryInsert(deliveryViewExNew, deliveryOne);
									deliveryOne.setDeliveryResult("2");
									deliveryOne.setRejectedReason("借款状态必须是放款成功的数据才可以交割。");
									des.insertDelivery(deliveryOne);
									continue;
								}
								if(!"".equals(deliveryViewExNew.getStoresName()) && null!=deliveryViewExNew.getStoresName() 
									&& (!deliveryViewExNew.getStoresName().equals(loanInfoByLoanCode.getOrgName()))){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割前所属门店(门店名称)与文档中的 交割前所属门店(门店名称)不一样。");
										des.insertDelivery(deliveryOne);
										continue;
								}
								
								if(!"".equals(deliveryViewExNew.getTeamManagerCode()) && null!=deliveryViewExNew.getTeamManagerCode()){
									if(!deliveryViewExNew.getTeamManagerCode().equals(loanInfoByLoanCode.getTeamManagerCode())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割前团队经理员工号与文档中的交割前团队经理员工号不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}
									if(!"".equals(deliveryViewExNew.getTeamManagerName()) && null!=deliveryViewExNew.getTeamManagerName()){
										if(!deliveryViewExNew.getTeamManagerName().equals(loanInfoByLoanCode.getTeamManagerName())){
											DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
											deliveryInsert(deliveryViewExNew, deliveryOne);
											deliveryOne.setDeliveryResult("2");
											deliveryOne.setRejectedReason("查询出数据库中的交割前团队经理与文档中的交割前团队经理不一样。");
											des.insertDelivery(deliveryOne);
											continue;
										}
									}
								}
								
								if(!"".equals(deliveryViewExNew.getManagerCode()) && null!=deliveryViewExNew.getManagerCode()){
									if(!deliveryViewExNew.getManagerCode().equals(loanInfoByLoanCode.getManagerCode())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割前客户经理员工号与文档中的交割前客户经理员工号不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}
									if(!"".equals(deliveryViewExNew.getManagerName()) && null!=deliveryViewExNew.getManagerName()){
										if(!deliveryViewExNew.getManagerName().equals(loanInfoByLoanCode.getManagerName())){
											DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
											deliveryInsert(deliveryViewExNew, deliveryOne);
											deliveryOne.setDeliveryResult("2");
											deliveryOne.setRejectedReason("查询出数据库中的交割前客户经理与文档中的交割前客户经理不一样。");
											des.insertDelivery(deliveryOne);
											continue;
										}
									}
								}
								
								if(!"".equals(deliveryViewExNew.getCustomerServicesCode()) && null!=deliveryViewExNew.getCustomerServicesCode()){
									if(!deliveryViewExNew.getCustomerServicesCode().equals(loanInfoByLoanCode.getCustomerServicesCode())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割前客服人员员工号与文档中的交割前客服人员员工号不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}
									if(!"".equals(deliveryViewExNew.getCustomerServicesName()) && null!=deliveryViewExNew.getCustomerServicesName()){
										if(!deliveryViewExNew.getCustomerServicesName().equals(loanInfoByLoanCode.getCustomerServicesName())){
											DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
											deliveryInsert(deliveryViewExNew, deliveryOne);
											deliveryOne.setDeliveryResult("2");
											deliveryOne.setRejectedReason("查询出数据库中的交割前客服人员与文档中的交割前客服人员不一样。");
											des.insertDelivery(deliveryOne);
											continue;
										}
									}
								}
							}
							
							//2、校验被交割数据准确性（系统是否存在以下字段，及以下字段对应关系是否准确）：角色姓名 —> 角色员工编号；
							//交割后数据都为空
							if(("".equals(deliveryViewExNew.getNewStoresName()) || null==deliveryViewExNew.getNewStoresName())
								&& ("".equals(deliveryViewExNew.getNewTeamManagerCode()) || null==deliveryViewExNew.getNewTeamManagerCode())
								&& ("".equals(deliveryViewExNew.getNewManagerCode()) || null==deliveryViewExNew.getNewManagerCode())
								&& ("".equals(deliveryViewExNew.getNewCustomerServicesCode()) || null==deliveryViewExNew.getNewCustomerServicesCode())
							){
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("文档中现归属信息门店、团队经理员工号、客户经理员工号和客服员工号都为空。");
								des.insertDelivery(deliveryOne);
								continue;
							}
							
							if(!"".equals(deliveryViewExNew.getNewStoresName()) && null!=deliveryViewExNew.getNewStoresName()){
								OrgInfo orgByStoreName = des.orgByStoreName(deliveryViewExNew.getNewStoresName());
								if(null!=orgByStoreName){
									if(!deliveryViewExNew.getNewStoresName().equals(orgByStoreName.getName())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割后所属门店与文档中的交割后所属门店不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}else{
										loanStoreOrgid=orgByStoreName.getId();
									}
								}else{
									DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
									deliveryInsert(deliveryViewExNew, deliveryOne);
									deliveryOne.setDeliveryResult("2");
									deliveryOne.setRejectedReason("查询出数据库中的交割后所属门店不存在。");
									des.insertDelivery(deliveryOne);
									continue;
								}
							}
							
							if(!"".equals(deliveryViewExNew.getNewTeamManagerCode()) && null!=deliveryViewExNew.getNewTeamManagerCode()){
								UserInfo userByUserCode = des.userByUserCode(deliveryViewExNew.getNewTeamManagerCode());
								if(null!=userByUserCode){
									if(!deliveryViewExNew.getNewTeamManagerCode().equals(userByUserCode.getUserCode())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割后团队经理员工号与文档中的交割后团队经理员工号不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}else{
										loanTeamOrgId=userByUserCode.getDepartmentId();
									}
									if(!"".equals(deliveryViewExNew.getNewTeamManagerName()) && null!=deliveryViewExNew.getNewTeamManagerName()){
										if(!deliveryViewExNew.getNewTeamManagerName().equals(userByUserCode.getName())){
											DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
											deliveryInsert(deliveryViewExNew, deliveryOne);
											deliveryOne.setDeliveryResult("2");
											deliveryOne.setRejectedReason("查询出数据库中的交割后团队经理与文档中的交割后团队经理不一样。");
											des.insertDelivery(deliveryOne);
											continue;
										}
									}
								}else{
									DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
									deliveryInsert(deliveryViewExNew, deliveryOne);
									deliveryOne.setDeliveryResult("2");
									deliveryOne.setRejectedReason("查询出数据库中的交割后团队经理员工号不存在。");
									des.insertDelivery(deliveryOne);
									continue;
								}
							}
							
							if(!"".equals(deliveryViewExNew.getNewManagerCode()) && null!=deliveryViewExNew.getNewManagerCode()){
								UserInfo userByUserCode = des.userByUserCode(deliveryViewExNew.getNewManagerCode());
								if(null!=userByUserCode){
									if(!deliveryViewExNew.getNewManagerCode().equals(userByUserCode.getUserCode())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割后客户经理员工号与文档中的交割后客户经理员工号不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}
									if(!"".equals(deliveryViewExNew.getNewManagerName()) && null!=deliveryViewExNew.getNewManagerName()){
										if(!deliveryViewExNew.getNewManagerName().equals(userByUserCode.getName())){
											DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
											deliveryInsert(deliveryViewExNew, deliveryOne);
											deliveryOne.setDeliveryResult("2");
											deliveryOne.setRejectedReason("查询出数据库中的交割后客户经理与文档中的交割后客户经理不一样。");
											des.insertDelivery(deliveryOne);
											continue;
										}
									}
								}else{
									DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
									deliveryInsert(deliveryViewExNew, deliveryOne);
									deliveryOne.setDeliveryResult("2");
									deliveryOne.setRejectedReason("查询出数据库中的交割后客户经理员工号不存在。");
									des.insertDelivery(deliveryOne);
									continue;
								}
							}
							
							if(!"".equals(deliveryViewExNew.getNewCustomerServicesCode()) && null!=deliveryViewExNew.getNewCustomerServicesCode()){
								UserInfo userByUserCode = des.userByUserCode(deliveryViewExNew.getNewCustomerServicesCode());
								if(null!=userByUserCode){
									if(!deliveryViewExNew.getNewCustomerServicesCode().equals(userByUserCode.getUserCode())){
										DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
										deliveryInsert(deliveryViewExNew, deliveryOne);
										deliveryOne.setDeliveryResult("2");
										deliveryOne.setRejectedReason("查询出数据库中的交割后客服人员员工号与文档中的交割后客服人员员工号不一样。");
										des.insertDelivery(deliveryOne);
										continue;
									}
									if(!"".equals(deliveryViewExNew.getNewCustomerServicesName()) && null!=deliveryViewExNew.getNewCustomerServicesName()){
										if(!deliveryViewExNew.getNewCustomerServicesName().equals(userByUserCode.getName())){
											DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
											deliveryInsert(deliveryViewExNew, deliveryOne);
											deliveryOne.setDeliveryResult("2");
											deliveryOne.setRejectedReason("查询出数据库中的交割后客服人员与文档中的交割后客服人员不一样。");
											des.insertDelivery(deliveryOne);
											continue;
										}
									}
								}else{
									DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
									deliveryInsert(deliveryViewExNew, deliveryOne);
									deliveryOne.setDeliveryResult("2");
									deliveryOne.setRejectedReason("查询出数据库中的交割后客服人员员工号不存在。");
									des.insertDelivery(deliveryOne);
									continue;
								}
							}
							//3、校验待交割数据与被交割数据是否为同类型角色；
							
							//成功匹配更新loanInfo表，插入到交割表记录
							DeliveryViewExNew deliveryTwo = new DeliveryViewExNew();
							//判断交割前后值的各种情况(前后有无填写，交叉填写角色不对应) 暂时未进行门店的交割
							/*if((null!=deliveryViewExNew.getStoresName() && !"".equals(deliveryViewExNew.getStoresName())) && 
									null!=deliveryViewExNew.getNewStoresName() && !"".equals(deliveryViewExNew.getNewStoresName())){
								
								deliveryTwo.setLoanStoreOrgId(loanStoreOrgid);
								
							}else if(((null!=deliveryViewExNew.getStoresName() && !"".equals(deliveryViewExNew.getStoresName())) && 
									(null==deliveryViewExNew.getNewStoresName() || "".equals(deliveryViewExNew.getNewStoresName())))
									|| ((null==deliveryViewExNew.getStoresName() || "".equals(deliveryViewExNew.getStoresName())) && 
									(null!=deliveryViewExNew.getNewStoresName() && !"".equals(deliveryViewExNew.getNewStoresName())))){
								
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("文档交割信息前与后的门店有没值的。");
								des.insertDelivery(deliveryOne);
								continue;
							}*/
							
							if((null!=deliveryViewExNew.getTeamManagerCode() && !"".equals(deliveryViewExNew.getTeamManagerCode())) && 
								null!=deliveryViewExNew.getNewTeamManagerCode() && !"".equals(deliveryViewExNew.getNewTeamManagerCode())){
								
								deliveryTwo.setTeamManagerCode(deliveryViewExNew.getNewTeamManagerCode());
								
								deliveryTwo.setLoanTeamOrgId(loanTeamOrgId);
								
							}else if(((null!=deliveryViewExNew.getTeamManagerCode() && !"".equals(deliveryViewExNew.getTeamManagerCode())) && 
									(null==deliveryViewExNew.getNewTeamManagerCode() || "".equals(deliveryViewExNew.getNewTeamManagerCode())))
									|| ((null==deliveryViewExNew.getTeamManagerCode() || "".equals(deliveryViewExNew.getTeamManagerCode())) && 
									(null!=deliveryViewExNew.getNewTeamManagerCode() && !"".equals(deliveryViewExNew.getNewTeamManagerCode())))){
								
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("文档交割信息前与后的团队经理员工号有没值的。");
								des.insertDelivery(deliveryOne);
								continue;
							}
							
							if((null!=deliveryViewExNew.getManagerCode() && !"".equals(deliveryViewExNew.getManagerCode())) && 
									null!=deliveryViewExNew.getNewManagerCode() && !"".equals(deliveryViewExNew.getNewManagerCode())){
								
								deliveryTwo.setManagerCode(deliveryViewExNew.getNewManagerCode());
								
							}else if(((null!=deliveryViewExNew.getManagerCode() && !"".equals(deliveryViewExNew.getManagerCode())) && 
									(null==deliveryViewExNew.getNewManagerCode() || "".equals(deliveryViewExNew.getNewManagerCode())))
									|| ((null==deliveryViewExNew.getManagerCode() || "".equals(deliveryViewExNew.getManagerCode())) && 
									(null!=deliveryViewExNew.getNewManagerCode() && !"".equals(deliveryViewExNew.getNewManagerCode())))){
								
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("文档交割信息前与后的客户经理员工号有没值的。");
								des.insertDelivery(deliveryOne);
								continue;
							}
							
							if((null!=deliveryViewExNew.getCustomerServicesCode() && !"".equals(deliveryViewExNew.getCustomerServicesCode())) && 
									null!=deliveryViewExNew.getNewCustomerServicesCode() && !"".equals(deliveryViewExNew.getNewCustomerServicesCode())){
								
								deliveryTwo.setCustomerServicesCode(deliveryViewExNew.getNewCustomerServicesCode());
								
							}else if(((null!=deliveryViewExNew.getCustomerServicesCode() && !"".equals(deliveryViewExNew.getCustomerServicesCode())) && 
									(null==deliveryViewExNew.getNewCustomerServicesCode() || "".equals(deliveryViewExNew.getNewCustomerServicesCode())))
									|| ((null==deliveryViewExNew.getCustomerServicesCode() || "".equals(deliveryViewExNew.getCustomerServicesCode())) && 
									(null!=deliveryViewExNew.getNewCustomerServicesCode() && !"".equals(deliveryViewExNew.getNewCustomerServicesCode())))){
								
								DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
								deliveryInsert(deliveryViewExNew, deliveryOne);
								deliveryOne.setDeliveryResult("2");
								deliveryOne.setRejectedReason("文档交割信息前与后的客服员工号有没值的。");
								des.insertDelivery(deliveryOne);
								continue;
							}
							
							deliveryTwo.setLoanCode(contract.getLoanCode());
							des.updateLoanInfoDelivery(deliveryTwo);
							deliveryViewExNew.setDeliveryResult("1");
							des.insertDelivery(deliveryViewExNew);
							
						}
					}else{
						DeliveryViewExNew deliveryOne = new DeliveryViewExNew();
						deliveryInsert(deliveryViewExNew, deliveryOne);
						deliveryOne.setDeliveryResult("2");
						deliveryOne.setRejectedReason("查出数据库中的合同编号不存在。");
						des.insertDelivery(deliveryOne);
						continue;
					}
				}
				message = "导入成功!";
			}else{
				message="请导入非空附件!";
			}
		} catch (Exception e) {
			logger.error("方法importResult：上传数据交割结果,导入错误"+e);
			message = "导入错误";
		} finally{
			redirectAttributes.addAttribute("message", message);
			return	"redirect:"+adminPath+"/borrow/delivery/deliveryWaitList";
		}
	}
	
	/**
	* @Title: deliveryInsert
	* @Description: TODO(插入交割数据方法 交割表)
	* @param @param deliveryViewExNew
	* @param @param deliveryOne    设定文件
	* @return void    返回类型
	 */
	private void deliveryInsert(DeliveryViewExNew deliveryViewExNew,
			DeliveryViewExNew deliveryOne) {
		deliveryOne.setLoanCustomerName(deliveryViewExNew.getLoanCustomerName());
		deliveryOne.setContractCode(deliveryViewExNew.getContractCode());
		deliveryOne.setStoresName(deliveryViewExNew.getStoresName());
		deliveryOne.setCustomerServicesCode(deliveryViewExNew.getCustomerServicesCode());
		deliveryOne.setCustomerServicesName(deliveryViewExNew.getCustomerServicesName());
		deliveryOne.setManagerCode(deliveryViewExNew.getManagerCode());
		deliveryOne.setManagerName(deliveryViewExNew.getManagerName());
		deliveryOne.setTeamManagerCode(deliveryViewExNew.getTeamManagerCode());
		deliveryOne.setTeamManagerName(deliveryViewExNew.getTeamManagerName());
		deliveryOne.setNewStoresName(deliveryViewExNew.getNewStoresName());
		deliveryOne.setNewCustomerServicesCode(deliveryViewExNew.getNewCustomerServicesCode());
		deliveryOne.setNewCustomerServicesName(deliveryViewExNew.getNewCustomerServicesName());
		deliveryOne.setNewManagerCode(deliveryViewExNew.getNewManagerCode());
		deliveryOne.setNewManagerName(deliveryViewExNew.getNewManagerName());
		deliveryOne.setNewTeamManagerCode(deliveryViewExNew.getNewTeamManagerCode());
		deliveryOne.setNewTeamManagerName(deliveryViewExNew.getNewTeamManagerName());
	}
}
