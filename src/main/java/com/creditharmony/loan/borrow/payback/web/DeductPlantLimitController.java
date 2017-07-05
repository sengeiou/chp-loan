package com.creditharmony.loan.borrow.payback.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Area;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantLimit;
import com.creditharmony.loan.borrow.payback.service.DeductPlantLimitService;
import com.google.common.collect.Lists;

/**
 * 平台接口
 * 
 * @Class Name DeductPlantLimitController
 * @author 翁私
 * @Create In 2017年4月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/deductPlantLimit")
public class DeductPlantLimitController extends BaseController{
	
	@Autowired
	private DeductPlantLimitService deductPlantLimitService;
	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 增加银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "save")
	public String insert(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		if(ObjectHelper.isEmpty(record.getId())){
			deductPlantLimitService.insert(record);
			addMessage(model, "新增规则成功");
		}else{
			deductPlantLimitService.update(record);
			addMessage(model, "修改规则成功");
		}
		return queryPage(new DeductPlantLimit(),request,response,model);
	}
	
	/**
	 * 修改银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "update")
	public String update(DeductPlantLimit record){
		deductPlantLimitService.update(record);
		return "sccess";
	}

	/**
	 * 删除接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "delete")
	public String delete(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			deductPlantLimitService.delete(record);
			addMessage(model, "删除成功");
		   }catch(Exception e){
			e.printStackTrace();
			addMessage(model, "删除失败");
		}
		return queryPage(new DeductPlantLimit(),request,response,model);
		
	}
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryPage")
	public String queryPage(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		Page<DeductPlantLimit> bankPage  = deductPlantLimitService.queryPage(new Page<DeductPlantLimit>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		return "borrow/payback/centerdeduct/deductPlantLimit";
	}
	
    /**
     * 跳转到保存和修改页面
     * @param record
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping(value = "goSaveOrEdit")
	public String goSaveOrEdit(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		if(!ObjectHelper.isEmpty(record)){
			if(!ObjectHelper.isEmpty(record.getId())){
				record = deductPlantLimitService.selectByPrimaryKey(record);
				model.addAttribute("limit",record);
			}else{
				model.addAttribute("limit",new DeductPlantLimit());
			}
		}
		return "borrow/payback/centerdeduct/deductPlantLimitDetail";
	}
	
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (String id : idArray) {
			if (id != null && !"".equals(id)) {
				parameter.append("'" + id + "',");
			}
		}
		String idstring = null;
		if (parameter != null) {
			idstring = parameter.toString();
			idstring = idstring.substring(0, parameter.lastIndexOf(","));
		}
		return idstring;
	}
	
	/**
	 * 添加平台时校验平台的重复性
	 * 2017年4月14日
	 * By 翁私
	 * @param platformId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/querydataByPlant")
	@ResponseBody
	public String querydataByPlant(DeductPlantLimit record){
		try {
			record = deductPlantLimitService.querydataByPlant(record);
		} catch (Exception e) {
			e.printStackTrace();
			return BooleanType.FALSE;
		}
		if (ObjectHelper.isEmpty(record)) {
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	
	/** ================================================================= 门店发起还款申请控制  */
	/**
	 * 分页查询门店划扣条件配置接口  Create In 2017年5月5日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryOrgPage")
	public String queryOrgPage(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		if (StringUtils.isNotEmpty(record.getOrgId()) && record.getOrgId().split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			record.setOrgIds(Arrays.asList(record.getOrgId().split(",")));
		}
		Page<DeductPlantLimit> bankPage  = deductPlantLimitService.queryOrgPage(new Page<DeductPlantLimit>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		return "borrow/payback/centerdeduct/deductOrgLimit";
	}
	
	
	/**
	 * 增加银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "saveOrg")
	public String saveOrg(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		//如果是全选的时候，则更新所有的门店
		if(!ObjectHelper.isEmpty(record.getOrgId())){
			if(record.getOrgId().contains("all")){
				Org porg = new Org();
				porg.setId("all");
				List<Org> list = deductPlantLimitService.findStores(porg);
				for(Org org : list)
				{
					List<DeductPlantLimit> limitList = deductPlantLimitService.queryOrgLimit(org);
					if(limitList.size() > 0){
						//修改
						record.setId(limitList.get(0).getId());
						record.setOrgId(org.getId());
						deductPlantLimitService.saveOrg(record);
					}else{
						// 保存
						record.setId("");
						record.setOrgId(org.getId());
						deductPlantLimitService.saveOrg(record);
					}
				}
				addMessage(model,"门店设置信息维护成功");
			}else{
				String orgId = record.getOrgId();
				if(!ObjectHelper.isEmpty(orgId)){
				String[] orgIds = orgId.split(",");
				Org org = new Org();
				 for(String id :orgIds){
					    org.setId(id);
						List<DeductPlantLimit> limitList = deductPlantLimitService.queryOrgLimit(org);
						if(limitList.size() > 0){
							//修改
							record.setId(limitList.get(0).getId());
							record.setOrgId(org.getId());
							deductPlantLimitService.saveOrg(record);
						}else{
							// 保存
							record.setOrgId(org.getId());
							deductPlantLimitService.saveOrg(record);
						}
				   }
				}
				addMessage(model,"门店设置信息维护成功");
		  }
		}else{
			addMessage(model, "没有要设置的数据");
		}
		return queryOrgPage(new DeductPlantLimit(),request,response,model);
	}
	
	/**
	 * 删除发起还款申请配置  Create In 2017年5月05日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "deleteOrg")
	public String deleteOrg(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			deductPlantLimitService.deleteOrg(record);
			addMessage(model, "删除成功");
		   }catch(Exception e){
			e.printStackTrace();
			addMessage(model, "删除失败");
		}
		return queryOrgPage(new DeductPlantLimit(),request,response,model);
		
	}
	
	@RequestMapping(value = "selectOrgList")
	public String selectStorePage(Org org, HttpServletRequest request,
			HttpServletResponse response, String isSingle, Model model) {
		if (null == org.getArea()) {
			Area area = new Area();
			org.setArea(area);
		}
		String orgName = org.getName();
		if(null !=org.getName() && org.getName().indexOf(",") >-1){
			String[] names = org.getName().split(",");
			List<Org> orgList = Lists.newArrayList();
			for(String name:names){
				Org orgN = new Org();
				orgN.setName(name);
				orgList.add(orgN);
			}
			org.setChildren(orgList);
			org.setName(null);
		}
		Page<Org> orgs = deductPlantLimitService.findStoresPage(new Page<Org>(request,
				response), org);
		org.setName(orgName);
		model.addAttribute("page", orgs);
		model.addAttribute("storesSelected", org.getStoresSelected());
		model.addAttribute("queryURL", "selectOrgList");
		return "modules/single/storesLimitList";
	}
	
	
	/**
	 * 门店划扣统计数据
	 * @param record
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "queryOrgStatisticsPage")
	public String queryOrgStatisticsPage(DeductPlantLimit record,HttpServletRequest request, HttpServletResponse response, Model model){
		if (StringUtils.isNotEmpty(record.getOrgId()) && record.getOrgId().split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			record.setOrgIds(Arrays.asList(record.getOrgId().split(",")));
		}
		
		if(!ObjectHelper.isEmpty(record.getStatisticsDate())){
			record.setCreateDate(format.format(record.getStatisticsDate()));
		}
		Page<DeductPlantLimit> bankPage  = deductPlantLimitService.queryOrgStatisticsPage(new Page<DeductPlantLimit>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		return "borrow/payback/centerdeduct/deductOrgStatisticsLimit";
	}
	
}

