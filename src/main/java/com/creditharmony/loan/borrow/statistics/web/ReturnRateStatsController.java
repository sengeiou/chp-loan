package com.creditharmony.loan.borrow.statistics.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.statistics.entity.ReturnRateStats;
import com.creditharmony.loan.borrow.statistics.service.ReturnRateStatsService;
import com.creditharmony.loan.borrow.statistics.view.ReturnRateStatsParams;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.service.OrgInfoService;

/**
 * 退回率统计报表
 * 
 * @author 任志远
 * @date 2016年11月16日
 */
@Controller
@RequestMapping("${adminPath}/borrow/statistics")
public class ReturnRateStatsController extends BaseController{
	
	@Autowired
	private CityInfoService cityInfoService;
	
	@Autowired
	private ReturnRateStatsService returnRateStatsService;
	
	@Autowired
	private OrgInfoService orgInfoService;

	@RequestMapping("returnRateStatsList")
	public String returnRateStats(ReturnRateStatsParams returnRateStatsParams, Model model, HttpServletRequest request, HttpServletResponse response){
		
		Page<ReturnRateStats> returnRateStatsPage = returnRateStatsService.findReturnRateStatsPage(new Page<ReturnRateStats>(request, response), returnRateStatsParams);
		model.addAttribute("returnRateStatsParams", returnRateStatsParams);
		model.addAttribute("returnRateStatsPage", returnRateStatsPage);
		
		//省份
		//List<CityInfo> provinceList = cityInfoService.findProvince();
		//model.addAttribute("provinceList", provinceList);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", LoanOrgType.BUISNESS_DEPT.key);
		//业务部
		List<OrgInfo> businessDepartmentList = orgInfoService.queryOrgByType(param);
		model.addAttribute("businessDepartmentList", businessDepartmentList);
		//省分公司
		param.put("type", LoanOrgType.PROVINCE_COMPANY.key);
		List<OrgInfo> provinceBranchList = orgInfoService.queryOrgByType(param);
		model.addAttribute("provinceBranchList", provinceBranchList);
		//城支公司
		param.put("type", LoanOrgType.CITY_COMPANY.key);
		List<OrgInfo> cityBranchList = orgInfoService.queryOrgByType(param);
		model.addAttribute("cityBranchList", cityBranchList);
		//门店
		param.put("type", LoanOrgType.STORE.key);
		List<OrgInfo> storeList = orgInfoService.queryOrgByType(param);
		model.addAttribute("storeList", storeList);
		
		return "borrow/statistics/returnRateStatsList";
	}
	
	@RequestMapping("exportReturnRateStatsExcel")
	public void exportReturnRateStatsExcel(ReturnRateStatsParams returnRateStatsParams, HttpServletResponse response){
		
		List<ReturnRateStats> returnRateStatsList = returnRateStatsService.findExportReturnRateStatsList(returnRateStatsParams);
		
		ExcelUtils excelutil = new ExcelUtils();
		excelutil.exportExcel(returnRateStatsList, FileExtension.RETURN_RATE_STATS + System.currentTimeMillis(), ReturnRateStats.class, FileExtension.XLSX, FileExtension.OUT_TYPE_DATA, response, 1);
	}
	
	@RequestMapping("queryChildOrg")
	@ResponseBody
	public List<OrgInfo> queryChildOrg(String parentId, String type, HttpServletResponse response){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type.split(","));
		param.put("parentId", parentId);
		List<OrgInfo> orgList = orgInfoService.queryOrgByParentIdAndType(param);
		
		return orgList;
	}
	
}
